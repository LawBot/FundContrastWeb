package cn.com.xiaofabo.tylaw.fundcontrast.utils;

import java.util.*;

import com.github.difflib.DiffUtils;
import com.github.difflib.patch.Delta;
import com.github.difflib.patch.DeltaType;
import com.github.difflib.patch.Patch;

import cn.com.xiaofabo.tylaw.fundcontrast.entity.DocPart;
import cn.com.xiaofabo.tylaw.fundcontrast.entity.FundDoc;
import cn.com.xiaofabo.tylaw.fundcontrast.entity.PartMatch;
import cn.com.xiaofabo.tylaw.fundcontrast.entity.PatchDto;
import cn.com.xiaofabo.tylaw.fundcontrast.entity.RevisedDto;
import cn.com.xiaofabo.tylaw.fundcontrast.textprocessor.DocProcessor;

public class CompareUtils {

	// sort patchDtoList
	List<String> sortIdList = new ArrayList<>();

	/// Core comparison method
	public List<PatchDto> getPatchDtoList(String templatePath, String samplePath) throws Exception {
		System.out.println("Start getPatchDtoList");

		List<PatchDto> patchDtoList = new LinkedList();

		
		/// Read in and structure 2 files (template file and sample file)
		DocProcessor templateProcessor = new DocProcessor(templatePath);
		FundDoc templateDoc = templateProcessor.process();

		DocProcessor sampleProcessor = new DocProcessor(samplePath);
		FundDoc sampleDoc = sampleProcessor.process();
		
//		System.out.println(sampleProcessor.getText());

		/// Compare first level part
		List<String> templateTitles = new LinkedList<String>();
		List<String> sampleTitles = new LinkedList<String>();

		for (int i = 0; i < templateDoc.getParts().size(); ++i) {
			String title = ((DocPart) templateDoc.getParts().get(i)).getTitle();
			templateTitles.add(title);
		}
		for (int i = 0; i < sampleDoc.getParts().size(); ++i) {
			String title = ((DocPart) sampleDoc.getParts().get(i)).getTitle();
			sampleTitles.add(title);
		}

		/// Finding which chapter is added or deleted.
		PartMatch partMatch = StringSimUtils.findBestMatch(templateTitles, sampleTitles);
		List addList = partMatch.getAddList();
		List deleteList = partMatch.getDeleteList();
		Map matchList = partMatch.getMatchList();
		

		for (int i = 0; i < deleteList.size(); ++i) {
			int chapterIndex = (int) deleteList.get(i);
			/// Delete means exists in template but not in sample
			DocPart part = templateDoc.getParts().get(chapterIndex);
			deletePartRecursive(patchDtoList, part);
		}

		for (int i = 0; i < addList.size(); ++i) {
			int chapterIndex = (int) addList.get(i);
			/// Add means exists in sample but not in template
			DocPart part = sampleDoc.getParts().get(chapterIndex);
			addPartRecursive(patchDtoList, part);
		}

		Iterator it = matchList.keySet().iterator();
		while (it.hasNext()) {
			int templateIndex = (int) it.next();
			int sampleIndex = (int) matchList.get(templateIndex);
			PatchDto pdt = new PatchDto();
			// pdt.setChapterIndex(sampleIndex);
//			pdt.setChangeType("change");
			DocPart templatePart = templateDoc.getParts().get(templateIndex);
			DocPart samplePart = sampleDoc.getParts().get(sampleIndex);
//			pdt.setPartId(samplePart.getPartCount());
//			pdt.setPartIndex(samplePart.getPartIndex());
//			pdt.setChapterTitle(templateDoc.getParts().get(templatePart.getPartIndex().get(0)).getTitle());

			/// Then compare children parts
			compareParts(patchDtoList, templateDoc, sampleDoc, templatePart, samplePart);
		}
		
		System.out.println("1");
		System.out.println(pdtList2Str(patchDtoList));
		System.out.println("2");

		Collections.sort(patchDtoList);
		System.out.println("3");
		
		

		List<PatchDto> cleanList = cleanListEntries(patchDtoList);
		
		System.out.println("Finish getPatchDtoList");
		return null;
	}

	private void compareParts(List<PatchDto> patchDtoList, FundDoc templateDoc, FundDoc sampleDoc, DocPart templatePart,
			DocPart samplePart) throws Exception {

		String tPoint = templatePart.getPoint();
		String sPoint = samplePart.getPoint();

		String tIndex = templatePart.getIndex();
		String sIndex = samplePart.getIndex();

		String tText = tIndex + tPoint;
		String sText = sIndex + sPoint;

		/// Compare templateText and sampleText
		/// In case they are different, patchDtoList.add
		if (!TextUtils.getPureText(tPoint).equalsIgnoreCase(TextUtils.getPureText(sPoint))) {

			Patch<String> patch = DiffUtils.diffInline(tPoint, sPoint);

			List<Delta<String>> deltaList = patch.getDeltas();
			Map<Integer, Character> deleteMap = new HashMap();
			Map<Integer, Character> addMap = new HashMap();

			for (Delta<String> delta : deltaList) {
				if (delta.getType().equals(DeltaType.CHANGE)) {
					for (int i = delta.getOriginal().getPosition(); i < delta.getOriginal().getPosition()
							+ delta.getOriginal().getLines().get(0).length(); i++) {
						deleteMap.put(i + tIndex.length(), tPoint.charAt(i));
					}
					for (int i = delta.getRevised().getPosition(); i < delta.getRevised().getPosition()
							+ delta.getRevised().getLines().get(0).length(); i++) {
						addMap.put(i + sIndex.length(), sPoint.charAt(i));
					}
				}
				if (delta.getType().equals(DeltaType.DELETE)) {
					for (int i = delta.getOriginal().getPosition(); i < delta.getOriginal().getPosition()
							+ delta.getOriginal().getLines().get(0).length(); i++) {
						deleteMap.put(i + tIndex.length(), tPoint.charAt(i));
					}
				}
				if (delta.getType().equals(DeltaType.INSERT)) {
					for (int i = delta.getRevised().getPosition(); i < delta.getRevised().getPosition()
							+ delta.getRevised().getLines().get(0).length(); i++) {
						addMap.put(i + sIndex.length(), sPoint.charAt(i));
					}
				}
			}

			RevisedDto revisedDto = new RevisedDto();
			revisedDto.setAddData(addMap);
			revisedDto.setDeleteData(deleteMap);
			revisedDto.setRevisedText(sText);
			PatchDto pdt = new PatchDto();
			pdt.setRevisedDto(revisedDto);
			pdt.setOrignalText(tText);
			pdt.setIndexType("orginal");
			pdt.setChangeType("change");
			pdt.setPartId(templatePart.getPartCount());
			pdt.setTempaltePartIndex(templatePart.getPartIndex());
			pdt.setSamplePartIndex(samplePart.getPartIndex());
			pdt.setChapterTitle(templateDoc.getParts().get(templatePart.getPartIndex().get(0)).getTitle());
			patchDtoList.add(pdt);
		}

		/// Neither template nor sample part has child parts, stop comparing
		if (!templatePart.hasChildParts() && !samplePart.hasChildParts()) {
			return;
		}

		List templateTitles = new LinkedList();
		List sampleTitles = new LinkedList();

		for (int i = 0; templatePart.hasChildParts() && i < templatePart.getChildPart().size(); ++i) {
			String title = ((DocPart) templatePart.getChildPart().get(i)).getTitle();
			templateTitles.add(title);
		}
		for (int i = 0; samplePart.hasChildParts() && i < samplePart.getChildPart().size(); ++i) {
			String title = ((DocPart) samplePart.getChildPart().get(i)).getTitle();
			sampleTitles.add(title);
		}

		PartMatch partMatch = StringSimUtils.findBestMatch(templateTitles, sampleTitles);
		List addList = partMatch.getAddList();
		List deleteList = partMatch.getDeleteList();
		Map matchList = partMatch.getMatchList();

		for (int i = 0; i < deleteList.size(); ++i) {
			int chapterIndex = (int) deleteList.get(i);
			/// Delete means exists in template but not in sample
			DocPart part = templatePart.getChildPart().get(chapterIndex);
			deletePartRecursive(patchDtoList, part);
		}

		for (int i = 0; i < addList.size(); ++i) {
			int chapterIndex = (int) addList.get(i);
			/// add means exists in sample but not in template
			DocPart part = samplePart.getChildPart().get(chapterIndex);
			addPartRecursive(patchDtoList, part);
		}

		Iterator it = matchList.keySet().iterator();

		while (it.hasNext()) {
			int templateIndex = (int) it.next();
			int sampleIndex = (int) matchList.get(templateIndex);
//			PatchDto pdt = new PatchDto();
//			// pdt.setChapterIndex(sampleIndex);
//			pdt.setChangeType("change");
			DocPart tPart = templatePart.getChildPart().get(templateIndex);
			DocPart sPart = samplePart.getChildPart().get(sampleIndex);
//			pdt.setPartId(tPart.getPartCount());
//			pdt.setPartIndex(tPart.getPartIndex());

			/// Then compare children parts
			compareParts(patchDtoList, templateDoc, sampleDoc, tPart, sPart);
		}
	}
	
	private boolean deletePartRecursive(List<PatchDto> patchDtoList, DocPart part) {
		deletePart(patchDtoList, part);
		if(part.hasChildParts()) {
			for(int index = 0; index < part.getChildPart().size(); ++index) {
				DocPart childPart = part.getChildPart().get(index);
				deletePartRecursive(patchDtoList, childPart);
			}
		}
		return true;
	}
	
	private boolean addPartRecursive(List<PatchDto> patchDtoList, DocPart part) {
		addPart(patchDtoList, part);
		if(part.hasChildParts()) {
			for(int index = 0; index < part.getChildPart().size(); ++index) {
				DocPart childPart = part.getChildPart().get(index);
				addPartRecursive(patchDtoList, childPart);
			}
		}
		return true;
	}
	
	private boolean deletePart(List<PatchDto> patchDtoList, DocPart part) {
//		System.out.println("Delete part " + part.getPartIndexStr() + ": " + part.getTitle());
		PatchDto pdt = new PatchDto();
		pdt.setChangeType("delete");
		String pointText = part.getWholePoint();
		pdt.setOrignalText(pointText);
		RevisedDto rdt = new RevisedDto();
		for (int j = 0; j < pointText.length(); ++j) {
			Character c = pointText.charAt(j);
			rdt.deleteData(j, c);
		}
		pdt.setPartId(part.getPartCount());
		pdt.setTempaltePartIndex(part.getPartIndex());
		// pdt.setChapterTitle(templateDoc.getParts().get(dp.getPartIndex().get(0)).getTitle());
		pdt.setChapterTitle(part.getTitle());
		pdt.setRevisedDto(rdt);
		patchDtoList.add(pdt);
		return true;
	}
	
	private boolean addPart(List<PatchDto> patchDtoList, DocPart part) {
//		System.out.println("Add part " + part.getPartIndexStr() + ": " + part.getTitle());
		PatchDto pdt = new PatchDto();
		pdt.setChangeType("add");
		String pointText = part.getWholePoint();
		RevisedDto rdt = new RevisedDto();
		rdt.setRevisedText(pointText);
		for (int j = 0; j < pointText.length(); ++j) {
			Character c = pointText.charAt(j);
			rdt.addData(j, c);
		}
		pdt.setRevisedDto(rdt);
		pdt.setPartId(part.getPartCount());
		pdt.setSamplePartIndex(part.getPartIndex());
//		pdt.setChapterTitle(sampleDoc.getParts().get(dp.getPartIndex().get(0)).getTitle());
		pdt.setChapterTitle(part.getTitle());
		patchDtoList.add(pdt);
		return true;
	}
	
	/// Helper function
	private String pdtList2Str(List<PatchDto> contrastList) {
		StringBuilder sb = new StringBuilder();
		for(int index = 0; index < contrastList.size(); ++index) {
			PatchDto pdt = contrastList.get(index);
			sb.append(index + ": ");
			sb.append("[" + pdt.getTemplatePartIndex() + "]");
			sb.append("[" + pdt.getSamplePartIndex() + "]");
			sb.append("[" + pdt.getChangeType() + "]");
			sb.append("\n");
		}
		System.out.println("Done");
		return sb.toString();
	}
	
	private List<PatchDto> cleanListEntries(List<PatchDto> contrastList) {
		/// Define different exclude list for different change types
		List<String> excludeIndex = new ArrayList<String>();
		excludeIndex.add("1-0");
		excludeIndex.add("1-1");
		excludeIndex.add("1-2");
		excludeIndex.add("1-3");
		excludeIndex.add("1-4");
		excludeIndex.add("1-5");
		excludeIndex.add("1-6");
		excludeIndex.add("1-23");
		excludeIndex.add("1-28");
		excludeIndex.add("1-35");
		excludeIndex.add("1-42");

		excludeIndex.add("2-0");
//		excludeIndex.add("2-2");
//		excludeIndex.add("2-3");
		excludeIndex.add("2-4");
		excludeIndex.add("2-5");

		excludeIndex.add("6-0-0");
		excludeIndex.add("6-1-0");

		excludeIndex.add("10-0");

		List<String> excludeSection = new ArrayList<String>();
		excludeSection.add("10-2");
		excludeSection.add("10-4");
		excludeSection.add("10-5");
		excludeSection.add("22");
		excludeSection.add("23");

		List<String> deleteSection = new ArrayList<String>();
		deleteSection.add("1-51");
		deleteSection.add("2-7");
		deleteSection.add("3-2-3");
		deleteSection.add("3-5-6");
		deleteSection.add("12-2-4");
		deleteSection.add("13-4");

		List<PatchDto> cleanList = new LinkedList<PatchDto>();
		for (int i = 0; i < contrastList.size(); ++i) {
			boolean isAdd = true;
			PatchDto pdt = contrastList.get(i);
			String indexInStr = pdt.partIndexInStr();

			boolean isDelete = pdt.getChangeType().equalsIgnoreCase("delete");
			if (isAdd && isDelete) {
				for (int index = 0; index < deleteSection.size(); ++index) {
					String dSection = deleteSection.get(index);
					if (indexInStr.startsWith(dSection)) {
						isAdd = false;
						break;
					}
				}
			}

			if (isAdd) {
				for (int index = 0; index < excludeSection.size(); ++index) {
					String eSection = excludeSection.get(index);
					if (indexInStr.startsWith(eSection)) {
						isAdd = false;
						break;
					}
				}
			}

			if (isAdd) {
				for (int index = 0; index < excludeIndex.size(); ++index) {
					String eIndex = excludeIndex.get(index);
					if (indexInStr.equals(eIndex)) {
						isAdd = false;
						break;
					}
				}
			}
			if (isAdd) {
				cleanList.add(pdt);
			}
		}
		return cleanList;
	}
}
