package cn.com.xiaofabo.tylaw.fundcontrast.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFStyles;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocument1;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFonts;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import cn.com.xiaofabo.tylaw.fundcontrast.entity.FundDoc;
import cn.com.xiaofabo.tylaw.fundcontrast.entity.PatchDto;
import cn.com.xiaofabo.tylaw.fundcontrast.entity.RevisedDto;

/**
 * Created on @ 17.01.18
 *
 * @author 杨敏 email ddl-15 at outlook.com
 *
 *         Modified on 2018-02-14 by G. Chen
 * 
 *         Modified on 2018-10-07 by G. Chen
 */
public class GenerateCompareDoc {

	public static final BigInteger A4_WIDTH = BigInteger.valueOf(16840L);
	public static final BigInteger A4_LENGTH = BigInteger.valueOf(11900L);
	public static final BigInteger TABLE_WIDTH = BigInteger.valueOf(13040L);

	public static final String FONT_FAMILY_TIME_NEW_ROMAN = "Times New Roman";
	public static final String FONT_FAMILY_SONG = "宋体";

	public static final int EFFECT_ADD_BOLD = 0;
	public static final int EFFECT_ADD_UNDERLINE = 1;
	public static final int EFFECT_ADD_BOLD_UNDERLINE = 2;
	public static final int EFFECT_DELETE_STRIKE = 0;
	public static final int EFFECT_DELETE_BOLD_STRIKE = 1;

	public static final String TABLE_HEADER_BGCOLOR = "C5C5C5";

	/// 1.0cm ~= 567L
	public static final BigInteger TABLE_COLUMN_1_WIDTH = BigInteger.valueOf(1133L); /// ~2.0cm
	public static final BigInteger TABLE_COLUMN_2_WIDTH = BigInteger.valueOf(4820L); /// ~8.5cm
	public static final BigInteger TABLE_COLUMN_3_WIDTH = BigInteger.valueOf(4820L); /// ~8.5cm

	public static final BigInteger TABLE_COLUMN_2_WIDTH_NO_REASON = BigInteger.valueOf(5670L); /// ~10cm
	public static final BigInteger TABLE_COLUMN_3_WIDTH_NO_REASON = BigInteger.valueOf(5670L); /// ~10cm

	public static final BigInteger TABLE_COLUMN_4_WIDTH = BigInteger.valueOf(1985L); /// ~3.5cm

	public static final int TABLE_CELL_MARGIN_TOP = 100;
	public static final int TABLE_CELL_MARGIN_LEFT = 100;
	public static final int TABLE_CELL_MARGIN_BOTTOM = 100;
	public static final int TABLE_CELL_MARGIN_RIGHT = 100;

	public static final int TABLE_COLUMN_1 = 0;
	public static final int TABLE_COLUMN_2 = 1;
	public static final int TABLE_COLUMN_3 = 2;
	public static final int TABLE_COLUMN_4 = 3;

	public static final String TABLE_TITLE_LINE_2_TEXT = "前后条文对照表";

	public static final String TABLE_COLUMN_1_TEXT = "章节";
	public static final String TABLE_COLUMN_2_TEXT = "《指引》条款";
	public static final String TABLE_COLUMN_3_TEXT = "《基金合同》条款";
	public static final String TABLE_COLUMN_4_TEXT = "修改理由";

	public static final int FONT_SIZE_LEADING_TEXT = 11;

	/**
	 * Defined Constants.
	 */
	String headerContent = "条文对照表测试文本";

	public int generate(String title, String headerText, String leadingText, List<List<PatchDto>> compactList,
			FundDoc templateDoc, FundDoc sampleDoc, String outputPath, int reasonColumn) throws IOException {

		System.out.println("Start contrast document generation");
		// StringBuilder sb = new StringBuilder();
		//
		// for (int i = 0; compactList != null && i < compactList.size(); i++) {
		// List<PatchDto> pdtList = (List<PatchDto>) compactList.get(i);
		// for (int j = 0; j < pdtList.size(); j++) {
		// PatchDto pdt = (PatchDto) pdtList.get(j);
		// sb.append(pdt.templatePartIndexStr() + "(" + pdt.getChangeType() + ");");
		// }
		// sb.append("\n");
		// }

		int compactListSize = compactList.size();

		System.out.println("Lines generated: " + compactListSize + ".");
		int nRow = compactListSize + 1;
		XWPFDocument document = new XWPFDocument();
		FileOutputStream out = new FileOutputStream(new File(outputPath));

		/// Document page setup
		pageSetup(document);
		/// Generate title text
		generateTitle(document, title);
		/// Generate leading text
		generateLeadingText(document, leadingText);

		/// Generate contrast table
		int nColumn = 4 - (1 - reasonColumn);
		XWPFTable table = document.createTable(nRow, nColumn);
		table.setCellMargins(TABLE_CELL_MARGIN_TOP, TABLE_CELL_MARGIN_LEFT, TABLE_CELL_MARGIN_BOTTOM,
				TABLE_CELL_MARGIN_RIGHT);
		CTTblWidth width = table.getCTTbl().addNewTblPr().addNewTblW();
		width.setType(STTblWidth.DXA);
		width.setW(TABLE_WIDTH);
		CTTblLayoutType type = table.getCTTbl().getTblPr().addNewTblLayout();
		type.setType(STTblLayoutType.FIXED);

		/// Generate table header
		XWPFTableRow tableRowOne = table.getRow(0);
		tableRowOne.setRepeatHeader(true);
		for (int i = 0; i < nColumn; ++i) {
			XWPFTableCell cell = tableRowOne.getCell(i);
			cell.getCTTc().addNewTcPr().addNewShd().setFill(TABLE_HEADER_BGCOLOR);
			BigInteger columnWidth = null;
			String columnText = null;
			switch (i) {
			case 0:
				columnWidth = TABLE_COLUMN_1_WIDTH;
				columnText = TABLE_COLUMN_1_TEXT;
				break;
			case 1:
				columnWidth = TABLE_COLUMN_2_WIDTH;
				columnText = TABLE_COLUMN_2_TEXT;
				break;
			case 2:
				columnWidth = TABLE_COLUMN_3_WIDTH;
				columnText = TABLE_COLUMN_3_TEXT;
				break;
			case 3:
				columnWidth = TABLE_COLUMN_4_WIDTH;
				columnText = TABLE_COLUMN_4_TEXT;
				break;
			default:
				break;
			}
			cell.getCTTc().addNewTcPr().addNewTcW().setW(columnWidth);
			cell.removeParagraph(0);
			XWPFParagraph paragraph = cell.addParagraph();
			XWPFRun run = paragraph.createRun();
			addEffect(run, EFFECT_ADD_BOLD);
			run.setText(columnText);
		}

		/// Generate table content
		for (int i = 0; i < compactListSize; ++i) {
			List<PatchDto> compactListItem = compactList.get(i);
			XWPFTableRow tableRow = table.getRow(i + 1);
			PatchDto firstItem = compactListItem.get(0);

			/// 1st column
			int chapterNumber = (firstItem.getTemplatePartIndex() != null) ? firstItem.getTemplatePartIndex().get(0)
					: firstItem.getSamplePartIndex().get(0);
			List<Integer> tempIndexList = new LinkedList<Integer>();
			tempIndexList.add(chapterNumber);
			String chapterTitle = templateDoc.getPartTitle(tempIndexList);
			// String chapterTitle = compactListItem.get(0).getChapterTitle();

			tableRow.getCell(TABLE_COLUMN_1).removeParagraph(0);
			XWPFParagraph p1 = tableRow.getCell(TABLE_COLUMN_1).addParagraph();
			XWPFRun r1 = p1.createRun();
			addEffect(r1, EFFECT_ADD_BOLD);
			r1.setText(chapterTitle);

			/// 2nd column
			XWPFTableCell cell2 = tableRow.getCell(TABLE_COLUMN_2);
			cell2.removeParagraph(0);
			XWPFParagraph p2 = cell2.addParagraph();
			p2.setAlignment(ParagraphAlignment.LEFT);

			/// If index depth >= 3, then parent title should be displayed
			if (firstItem.getPartIndexDepth() > 2) {
				/// Get title from 2nd level until 2nd last level and display
				List<Integer> partIndex = firstItem.getTemplatePartIndex();
				for (int idx = 1; idx < partIndex.size() - 1; ++idx) {
					List<Integer> tmpIndex = partIndex.subList(0, idx + 1);

					XWPFRun r2 = p2.createRun();
					r2.setText(templateDoc.getPartWholeTitle(tmpIndex));
					r2.addBreak();
				}
			}

			/// 3rd column
			XWPFTableCell cell3 = tableRow.getCell(TABLE_COLUMN_3);
			cell3.removeParagraph(0);
			XWPFParagraph p3 = cell3.addParagraph();
			p3.setAlignment(ParagraphAlignment.LEFT);

			/// If index depth >= 3, then parent title should be displayed
			if (firstItem.getPartIndexDepth() > 2) {
				/// Get title from 2nd level until 2nd last level and display
				List<Integer> partIndex = firstItem.getTemplatePartIndex();
				if(firstItem.getSamplePartIndex() != null) {
					partIndex = firstItem.getSamplePartIndex();
				}
				for (int idx = 1; idx < partIndex.size() - 1; ++idx) {
					List<Integer> tmpIndex = partIndex.subList(0, idx + 1);

					XWPFRun r3 = p3.createRun();
					r3.setText(sampleDoc.getPartWholeTitle(tmpIndex));
					r3.addBreak();
				}
			}

			for (int itemIdx = 0; itemIdx < compactListItem.size(); ++itemIdx) {
				PatchDto contrastItem = compactListItem.get(itemIdx);
				List<Integer> partIndex = contrastItem.getTemplatePartIndex();
				List<Integer> samplePartIndex = contrastItem.getSamplePartIndex();
				String changeType = contrastItem.getChangeType();

				if (changeType.equalsIgnoreCase("delete")) {
					/// Column 2
					XWPFParagraph paragraph2 = cell2.addParagraph();
					paragraph2.setAlignment(ParagraphAlignment.LEFT);
					String deleteText = contrastItem.getOrignalText();
					XWPFRun run = paragraph2.createRun();
					deleteEffect(run, EFFECT_DELETE_BOLD_STRIKE);
					run.setText(deleteText);
					run.addBreak();
					/// Column 3
					// Do nothing
				}

				if (changeType.equalsIgnoreCase("add")) {
					/// Column 2
					// Do nothing

					/// Column 3
					XWPFParagraph paragraph3 = cell3.addParagraph();
					paragraph3.setAlignment(ParagraphAlignment.LEFT);

					String addText = contrastItem.getRevisedDto().getRevisedText();
					XWPFRun run = paragraph3.createRun();
					addEffect(run, EFFECT_ADD_BOLD_UNDERLINE);
					run.setText(addText);
					run.addBreak();
				}

				if (changeType.equalsIgnoreCase("change")) {
					RevisedDto rdt = contrastItem.getRevisedDto();
					if (rdt == null) {
						continue;
					}

					/// Only delete. No add.
					if (rdt.getDeleteData() != null && rdt.getAddData() == null) {
						Set deleteSet = rdt.getDeleteData().keySet();

						XWPFParagraph c2Para = cell2.addParagraph();
						c2Para.setAlignment(ParagraphAlignment.LEFT);
						for (int j = 0; j < contrastItem.getOrignalText().length(); j++) {
							XWPFRun letterRun = c2Para.createRun();
							if (contrastItem.getOrignalText().charAt(j) == '\n') {
								letterRun.addBreak();
								continue;
							}

							String currentLetter = Character.toString(contrastItem.getOrignalText().charAt(j));
							if (deleteSet.contains(j)) {
								deleteEffect(letterRun, EFFECT_DELETE_BOLD_STRIKE);
							}
							letterRun.setText(currentLetter);
						}
						c2Para.createRun().addBreak();

						XWPFParagraph c3Para = cell3.addParagraph();
						c3Para.setAlignment(ParagraphAlignment.LEFT);
						for (int j = 0; j < rdt.getRevisedText().length(); j++) {
							XWPFRun letterRun = c3Para.createRun();
							if (rdt.getRevisedText().charAt(j) == '\n') {
								letterRun.addBreak();
								continue;
							}
							String currentLetter = Character.toString(rdt.getRevisedText().charAt(j));
							if (deleteSet.contains(j)) {
								deleteEffect(letterRun, EFFECT_DELETE_BOLD_STRIKE);
							}
							letterRun.setText(currentLetter);
						}
						c3Para.createRun().addBreak();
					}

					/// Only add. No delete.
					if (rdt.getRevisedText() != null && rdt.getAddData() != null && rdt.getDeleteData() == null) {
						Set addSet = rdt.getAddData().keySet();

						XWPFParagraph c2Para = cell2.addParagraph();
						c2Para.setAlignment(ParagraphAlignment.LEFT);
						XWPFRun run = c2Para.createRun();
						run.setText(contrastItem.getOrignalText());
						run.addBreak();

						XWPFParagraph c3Para = cell3.addParagraph();
						c3Para.setAlignment(ParagraphAlignment.LEFT);
						for (int j = 0; j < rdt.getRevisedText().length(); j++) {
							XWPFRun letterRun = c3Para.createRun();
							if (rdt.getRevisedText().charAt(j) == '\n') {
								letterRun.addBreak();
								continue;
							}
							String currentLetter = Character.toString(rdt.getRevisedText().charAt(j));
							if (addSet.contains(j)) {
								addEffect(letterRun, EFFECT_ADD_BOLD_UNDERLINE);
							}
							letterRun.setText(currentLetter);
						}
						c3Para.createRun().addBreak();
					}

					/// change: add + delete
					if (rdt.getDeleteData() != null && rdt.getAddData() != null) {
						Set deleteSet = rdt.getDeleteData().keySet();

						XWPFParagraph c2Para = cell2.addParagraph();
						c2Para.setAlignment(ParagraphAlignment.LEFT);
						for (int j = 0; j < contrastItem.getOrignalText().length(); j++) {
							XWPFRun letterRun = c2Para.createRun();
							if (contrastItem.getOrignalText().charAt(j) == '\n') {
								letterRun.addBreak();
								continue;
							}
							String currentLetter = Character.toString(contrastItem.getOrignalText().charAt(j));
							if (deleteSet.contains(j)) {
								deleteEffect(letterRun, EFFECT_DELETE_BOLD_STRIKE);
							}
							letterRun.setText(currentLetter);
						}
						c2Para.createRun().addBreak();

						Set addSet = rdt.getAddData().keySet();

						XWPFParagraph c3Para = cell3.addParagraph();
						c3Para.setAlignment(ParagraphAlignment.LEFT);
						for (int j = 0; j < rdt.getRevisedText().length(); j++) {
							XWPFRun letterRun = c3Para.createRun();
							if (rdt.getRevisedText().charAt(j) == '\n') {
								letterRun.addBreak();
								continue;
							}
							String currentLetter = Character.toString(rdt.getRevisedText().charAt(j));
							if (addSet.contains(j)) {
								addEffect(letterRun, EFFECT_ADD_BOLD_UNDERLINE);
							}
							letterRun.setText(currentLetter);
						}
						c3Para.createRun().addBreak();
					}
				}
			}
		}

		createHeader(document, headerText);
		createFooter(document);
		document.write(out);
		out.close();

		System.out.println("Finish contrast document generation");
		return 0;
	}

	/**
	 * @param doc
	 */
	private void createFooter(XWPFDocument doc) {
		XWPFParagraph paragraph = doc.createParagraph();
		XWPFRun run = paragraph.createRun();
		XWPFFooter footer;
		footer = doc.createFooter(HeaderFooterType.FIRST);
		paragraph = footer.createParagraph();
		paragraph.setAlignment(ParagraphAlignment.CENTER);
		footer = doc.createFooter(HeaderFooterType.DEFAULT);
		paragraph = footer.createParagraph();
		paragraph.setAlignment(ParagraphAlignment.CENTER);
		run = paragraph.createRun();
		paragraph.getCTP().addNewFldSimple().setInstr("PAGE \\* MERGEFORMAT");
		CTDocument1 document = doc.getDocument();
		CTBody body = document.getBody();
		if (!body.isSetSectPr()) {
			body.addNewSectPr();
		}
		CTSectPr section = body.getSectPr();
		if (!section.isSetPgSz()) {
			section.addNewPgSz();
		}
		CTPageNumber pageNumber = section.getPgNumType();
		if (pageNumber == null) {
			pageNumber = section.addNewPgNumType();
		}
		pageNumber.setStart(BigInteger.valueOf(0L));
		XWPFHeaderFooterPolicy headerFooterPolicy = doc.getHeaderFooterPolicy();
		if (headerFooterPolicy == null) {
			headerFooterPolicy = doc.createHeaderFooterPolicy();
		}
	}

	/**
	 * @param doc
	 * @param headerContent
	 */
	private void createHeader(XWPFDocument doc, String headerContent) {
		XWPFHeader header = doc.createHeader(HeaderFooterType.DEFAULT);
		XWPFParagraph paragraph = header.createParagraph();
		XWPFRun run = paragraph.createRun();
		paragraph.setAlignment(ParagraphAlignment.LEFT);
		run.setText(headerContent);
	}

	/**
	 * @param document
	 */
	private void pageSetup(XWPFDocument document) {
		CTDocument1 doc = document.getDocument();
		CTBody body = doc.getBody();
		if (!body.isSetSectPr()) {
			body.addNewSectPr();
		}
		CTSectPr section = body.getSectPr();
		if (!section.isSetPgSz()) {
			section.addNewPgSz();
		}
		CTPageSz pageSize = section.getPgSz();
		pageSize.setW(A4_WIDTH);
		pageSize.setH(A4_LENGTH);
		pageSize.setOrient(STPageOrientation.LANDSCAPE);

		XWPFStyles styles = document.createStyles();
		CTFonts fonts = CTFonts.Factory.newInstance();
		fonts.setEastAsia(FONT_FAMILY_SONG);
		fonts.setAscii(FONT_FAMILY_TIME_NEW_ROMAN);
		styles.setDefaultFonts(fonts);
	}

	/**
	 * @param document
	 * @param title
	 */
	private void generateTitle(XWPFDocument document, String title) {
		XWPFParagraph paragraph = document.createParagraph();
		paragraph.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun run = paragraph.createRun();
		run.setFontSize(12);
		run.setBold(true);
		run.setText(title);
		run.addBreak();
		run.addBreak();
		run.setText(TABLE_TITLE_LINE_2_TEXT);
		run.addBreak();
		run.addBreak();
	}

	/**
	 * @param document
	 * @param leadingText
	 */
	private void generateLeadingText(XWPFDocument document, String leadingText) {
		XWPFParagraph paragraphText = document.createParagraph();
		paragraphText.setAlignment(ParagraphAlignment.LEFT);
		XWPFRun runText = paragraphText.createRun();
		runText.setFontSize(FONT_SIZE_LEADING_TEXT);
		runText.setText(leadingText);
		runText.addBreak();
		runText.addBreak();
	}

	/// style:
	/// 0: strickthrough
	private void deleteEffect(XWPFRun run, int style) {
		switch (style) {
		case EFFECT_DELETE_STRIKE:
			run.setStrikeThrough(true);
			break;
		case EFFECT_DELETE_BOLD_STRIKE:
			run.setStrikeThrough(true);
			run.setBold(true);
			break;
		default:
			break;
		}
	}

	/// style:
	/// 0: bold
	/// 1: underline
	/// 2: bold + underline
	private void addEffect(XWPFRun run, int style) {
		switch (style) {
		case EFFECT_ADD_BOLD:
			run.setBold(true);
			break;
		case EFFECT_ADD_UNDERLINE:
			run.setUnderline(UnderlinePatterns.SINGLE);
			break;
		case EFFECT_ADD_BOLD_UNDERLINE:
			run.setUnderline(UnderlinePatterns.SINGLE);
			run.setBold(true);
			break;
		default:
			break;
		}
	}

	private List<List<PatchDto>> combineListEntries(List<PatchDto> contrastList, int endLevel) {
		if (contrastList == null || contrastList.isEmpty()) {
			return null;
		}

		List<List<PatchDto>> compactList = new LinkedList<List<PatchDto>>();
		List<PatchDto> tmpList = new LinkedList<PatchDto>();

		tmpList.add((PatchDto) contrastList.get(0));

		for (int i = 1; i < contrastList.size(); i++) {
			PatchDto curPdt = contrastList.get(i);
			PatchDto prePdt = contrastList.get(i - 1);

			List<Integer> curPdtIdx = curPdt.getTemplatePartIndex();
			List<Integer> prePdtIdx = prePdt.getTemplatePartIndex();

			if (getIndexDepth(curPdtIdx) <= endLevel || isIdxListIdentical(curPdtIdx, prePdtIdx)) {
				if (!tmpList.isEmpty()) {
					compactList.add(tmpList);
				}
				tmpList = new LinkedList<PatchDto>();
				tmpList.add(curPdt);
				if (!tmpList.isEmpty()) {
					compactList.add(tmpList);
				}
				tmpList = new LinkedList<PatchDto>();
			} else {
				if (isIdxListIdentical(getParentIndex(curPdtIdx), getParentIndex(prePdtIdx))
				// || isIdxListIdentical(getParentIndex(curPdtIdx), prePdtIdx)
				// || isIdxListIdentical(curPdtIdx, getParentIndex(prePdtIdx))
				) {
					tmpList.add(curPdt);
				} else {
					if (!tmpList.isEmpty()) {
						compactList.add(tmpList);
					}
					tmpList = new LinkedList<PatchDto>();
					tmpList.add(curPdt);
				}
			}

			if (i == contrastList.size() - 1) {
				if (!tmpList.isEmpty()) {
					compactList.add(tmpList);
				}
			}
		}
		return compactList;
	}

	private boolean isIdxListIdentical(List<Integer> idxList1, List<Integer> idxList2) {
		if (idxList1 == null || idxList2 == null) {
			return false;
		}
		if (idxList1.size() != idxList2.size()) {
			return false;
		}
		for (int i = 0; i < idxList1.size(); ++i) {
			int i1 = (int) idxList1.get(i);
			int i2 = (int) idxList2.get(i);
			if (i1 != i2) {
				return false;
			}
		}
		return true;
	}

	private List<Integer> getParentIndex(List<Integer> idxList) {
		if (idxList.size() <= 1) {
			return null;
		} else {
			return idxList.subList(0, idxList.size() - 1);
		}
	}

	private int getIndexDepth(List<Integer> idxList) {
		if (idxList == null || idxList.isEmpty()) {
			return 0;
		} else {
			return idxList.size();
		}
	}
}
