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
			PatchDto pdt = new PatchDto();
			// pdt.setChapterIndex(chapterIndex);
			pdt.setChangeType("delete");
			/// TODO: should be recursive
			/// Delete means exists in template but not in sample
			DocPart dp = templateDoc.getParts().get(chapterIndex);
			String pointText = dp.getWholePoint();
			pdt.setOrignalText(pointText);
			RevisedDto rdt = new RevisedDto();
			for (int j = 0; j < pointText.length(); ++j) {
				Character c = pointText.charAt(j);
				rdt.deleteData(j, c);
			}
			pdt.setPartId(dp.getPartCount());
			pdt.setPartIndex(dp.getPartIndex());
			pdt.setChapterTitle(templateDoc.getParts().get(dp.getPartIndex().get(0)).getTitle());
			pdt.setRevisedDto(rdt);
			patchDtoList.add(pdt);
		}

		for (int i = 0; i < addList.size(); ++i) {
			int chapterIndex = (int) addList.get(i);
			PatchDto pdt = new PatchDto();
			// pdt.setChapterIndex(chapterIndex);
			pdt.setChangeType("add");
			/// TODO: should be recursive
			/// Add means exists in sample but not in template
			DocPart dp = sampleDoc.getParts().get(chapterIndex);

			String pointText = dp.getWholePoint();
			RevisedDto rdt = new RevisedDto();
			rdt.setRevisedText(pointText);
			for (int j = 0; j < pointText.length(); ++j) {
				Character c = pointText.charAt(j);
				rdt.addData(j, c);
			}
			// pdt.setPartId(dp.getPartCount());
			pdt.setPartIndex(dp.getPartIndex());
			pdt.setChapterTitle(sampleDoc.getParts().get(dp.getPartIndex().get(0)).getTitle());
			pdt.setRevisedDto(rdt);
			patchDtoList.add(pdt);

			int childIndex = 0;
			while (true) {
				List<Integer> childPartIndex = new ArrayList<Integer>(dp.getPartIndex());
				childPartIndex.add(childIndex++);
				DocPart cp = sampleDoc.getPart(childPartIndex);
				if (cp != null) {
					PatchDto cdt = new PatchDto();
					cdt.setChangeType("add");
					String cpText = cp.getWholePoint();
					RevisedDto crdt = new RevisedDto();
					crdt.setRevisedText(cpText);
					for(int j = 0; j < cpText.length(); ++j) {
						Character c = cpText.charAt(j);
						crdt.addData(j, c);
					}
					cdt.setPartIndex(childPartIndex);
					cdt.setChapterTitle(sampleDoc.getParts().get(childPartIndex.get(0)).getTitle());
					cdt.setRevisedDto(crdt);
					patchDtoList.add(cdt);
				} else {
					break;
				}
			}
		}

		Iterator it = matchList.keySet().iterator();
		while (it.hasNext()) {
			int templateIndex = (int) it.next();
			int sampleIndex = (int) matchList.get(templateIndex);
			PatchDto pdt = new PatchDto();
			// pdt.setChapterIndex(sampleIndex);
			pdt.setChangeType("change");
			DocPart templatePart = templateDoc.getParts().get(templateIndex);
			DocPart samplePart = sampleDoc.getParts().get(sampleIndex);
			pdt.setPartId(samplePart.getPartCount());
			pdt.setPartIndex(samplePart.getPartIndex());
			pdt.setChapterTitle(templateDoc.getParts().get(templatePart.getPartIndex().get(0)).getTitle());

			/// Then compare children parts
			compareParts(patchDtoList, templateDoc, sampleDoc, templatePart, samplePart);
		}

		Collections.sort(patchDtoList);

		List<PatchDto> cleanList = cleanListEntries(patchDtoList);
		return cleanList;
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
			pdt.setPartIndex(templatePart.getPartIndex());
			pdt.setSamplePartIndex(samplePart.getPartIndex());
			pdt.setChapterTitle(templateDoc.getParts().get(templatePart.getPartIndex().get(0)).getTitle());
			patchDtoList.add(pdt);
		}

		if (!templatePart.hasPart() && !samplePart.hasPart()) {
			return;
		}

		List templateTitles = new LinkedList();
		List sampleTitles = new LinkedList();

		for (int i = 0; templatePart.hasPart() && i < templatePart.getChildPart().size(); ++i) {
			String title = ((DocPart) templatePart.getChildPart().get(i)).getTitle();
			templateTitles.add(title);
		}
		for (int i = 0; samplePart.hasPart() && i < samplePart.getChildPart().size(); ++i) {
			String title = ((DocPart) samplePart.getChildPart().get(i)).getTitle();
			sampleTitles.add(title);
		}

		PartMatch partMatch = StringSimUtils.findBestMatch(templateTitles, sampleTitles);
		List addList = partMatch.getAddList();
		List deleteList = partMatch.getDeleteList();
		Map matchList = partMatch.getMatchList();

		for (int i = 0; i < deleteList.size(); ++i) {
			int chapterIndex = (int) deleteList.get(i);
			PatchDto pdt = new PatchDto();
			// pdt.setChapterIndex(chapterIndex);
			pdt.setChangeType("delete");
			/// TODO: should be recursive
			/// Delete means exists in template but not in sample
			DocPart dp = templatePart.getChildPart().get(chapterIndex);
			String pointText = dp.getWholePoint();
			pdt.setOrignalText(pointText);
			RevisedDto rdt = new RevisedDto();
			for (int j = 0; j < pointText.length(); ++j) {
				Character c = pointText.charAt(j);
				rdt.deleteData(j, c);
			}
			pdt.setPartId(dp.getPartCount());
			pdt.setPartIndex(dp.getPartIndex());
			pdt.setChapterTitle(templateDoc.getParts().get(dp.getPartIndex().get(0)).getTitle());
			pdt.setRevisedDto(rdt);
			patchDtoList.add(pdt);
		}

		for (int i = 0; i < addList.size(); ++i) {
			int chapterIndex = (int) addList.get(i);
			PatchDto pdt = new PatchDto();
			// pdt.setChapterIndex(chapterIndex);
			pdt.setChangeType("add");
			/// TODO: should be recursive
			/// Add means exists in sample but not in template
			DocPart dp = samplePart.getChildPart().get(chapterIndex);
			String pointText = dp.getWholePoint();
			RevisedDto rdt = new RevisedDto();
			rdt.setRevisedText(pointText);
			for (int j = 0; j < pointText.length(); ++j) {
				Character c = pointText.charAt(j);
				rdt.addData(j, c);
			}
			pdt.setRevisedDto(rdt);
			pdt.setPartId(dp.getPartCount());
			pdt.setPartIndex(dp.getPartIndex());
			pdt.setChapterTitle(sampleDoc.getParts().get(dp.getPartIndex().get(0)).getTitle());
			patchDtoList.add(pdt);
			
			int childIndex = 0;
			while (true) {
				List<Integer> childPartIndex = new ArrayList<Integer>(dp.getPartIndex());
				childPartIndex.add(childIndex++);
				DocPart cp = sampleDoc.getPart(childPartIndex);
				if (cp != null) {
					PatchDto cdt = new PatchDto();
					cdt.setChangeType("add");
					String cpText = cp.getWholePoint();
					RevisedDto crdt = new RevisedDto();
					crdt.setRevisedText(cpText);
					for(int j = 0; j < cpText.length(); ++j) {
						Character c = cpText.charAt(j);
						crdt.addData(j, c);
					}
					cdt.setPartIndex(childPartIndex);
					cdt.setChapterTitle(sampleDoc.getParts().get(childPartIndex.get(0)).getTitle());
					cdt.setRevisedDto(crdt);
					patchDtoList.add(cdt);
				} else {
					break;
				}
			}
		}

		Iterator it = matchList.keySet().iterator();

		while (it.hasNext()) {
			int templateIndex = (int) it.next();
			int sampleIndex = (int) matchList.get(templateIndex);
			PatchDto pdt = new PatchDto();
			// pdt.setChapterIndex(sampleIndex);
			pdt.setChangeType("change");
			DocPart tPart = templatePart.getChildPart().get(templateIndex);
			DocPart sPart = samplePart.getChildPart().get(sampleIndex);
			pdt.setPartId(tPart.getPartCount());
			pdt.setPartIndex(tPart.getPartIndex());

			/// Then compare children parts
			compareParts(patchDtoList, templateDoc, sampleDoc, tPart, sPart);
		}
	}

	private List<PatchDto> cleanListEntries(List<PatchDto> contrastList) {
		/// Define different exclude list for differnt change types
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
		excludeIndex.add("1-42");

		excludeIndex.add("2-2");
		excludeIndex.add("2-3");

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

	public List<String> getSortIdList() {
		return sortIdList;
	}
}
