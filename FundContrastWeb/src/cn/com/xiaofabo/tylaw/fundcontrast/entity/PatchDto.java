package cn.com.xiaofabo.tylaw.fundcontrast.entity;

import java.util.List;

public class PatchDto implements Comparable<PatchDto> {

	private String orignalText;
	private int chapterIndex;
	private RevisedDto revisedDto;
	private String indexType;// (代表原文的序号则为“orginal”,代表新条文的序号则为"revised"，当新增加原文没有的条文，则类型为"revised")
	private String changeType;// (add:新增 delete:删减 change:更改)
	private String partId;
	private List<Integer> templatePartIndex;
	private List<Integer> samplePartIndex;
	private String chapterTitle;

	public String getPartId() {
		return partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
	}

	public String getOrignalText() {
		return orignalText;
	}

	public void setOrignalText(String orignalText) {
		this.orignalText = orignalText;
	}

	public int getChapterIndex() {
		return chapterIndex;
	}

	public void setChapterIndex(int chapterIndex) {
		this.chapterIndex = chapterIndex;
	}

	public RevisedDto getRevisedDto() {
		return revisedDto;
	}

	public void setRevisedDto(RevisedDto revisedDto) {
		this.revisedDto = revisedDto;
	}

	public String getIndexType() {
		return indexType;
	}

	public void setIndexType(String indexType) {
		this.indexType = indexType;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public List<Integer> getTemplatePartIndex() {
		return templatePartIndex;
	}

	public void setTempaltePartIndex(List<Integer> partIndex) {
		this.templatePartIndex = partIndex;
	}

	public int getPartIndexDepth() {
		if (templatePartIndex == null || templatePartIndex.isEmpty()) {
			return 0;
		}
		return templatePartIndex.size();
	}

	public List<Integer> getSamplePartIndex() {
		return samplePartIndex;
	}

	public void setSamplePartIndex(List<Integer> samplePartIndex) {
		this.samplePartIndex = samplePartIndex;
	}

	public String getChapterTitle() {
		return chapterTitle;
	}

	public void setChapterTitle(String chapterTitle) {
		this.chapterTitle = chapterTitle;
	}

	public String templatePartIndexStr() {
		if (templatePartIndex == null)
			return null;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < templatePartIndex.size(); ++i) {
			if (i != templatePartIndex.size() - 1) {
				sb.append(templatePartIndex.get(i)).append("-");
			} else {
				sb.append(templatePartIndex.get(i));
			}
		}
		return sb.toString();
	}
	
	public String samplePartIndexStr() {
		if (samplePartIndex == null)
			return null;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < samplePartIndex.size(); ++i) {
			if (i != samplePartIndex.size() - 1) {
				sb.append(samplePartIndex.get(i)).append("-");
			} else {
				sb.append(samplePartIndex.get(i));
			}
		}
		return sb.toString();
	}

	@Override
	public int compareTo(PatchDto pdt) {
		
		/// Should never happen
		if (this.templatePartIndex == null && this.samplePartIndex == null) {
			return 0;
		}
		/// Should never happen
		if (pdt.getTemplatePartIndex() == null && pdt.getSamplePartIndex() == null) {
			return 0;
		}
		
		if (this.templatePartIndex != null && pdt.getTemplatePartIndex() != null) {
			for (int i = 0; i < this.templatePartIndex.size(); ++i) {
				if (i > pdt.getTemplatePartIndex().size() - 1) {
					return 1;
				}
				int thisIndex = this.templatePartIndex.get(i);
				int compareIndex = pdt.getTemplatePartIndex().get(i);
				int diff = thisIndex - compareIndex;
				if (diff != 0) {
					return diff;
				}
			}
		}
		
		if (this.templatePartIndex != null && pdt.getTemplatePartIndex() == null) {
			for (int i = 0; i < this.templatePartIndex.size(); ++i) {
				if (i > pdt.getSamplePartIndex().size() - 1) {
					return 1;
				}
				int thisIndex = this.templatePartIndex.get(i);
				int compareIndex = pdt.getSamplePartIndex().get(i);
				int diff = thisIndex - compareIndex;
				if (diff != 0) {
					return diff;
				}
			}
		}
		
		if(this.templatePartIndex == null && pdt.getTemplatePartIndex() != null) {
			for (int i = 0; i < this.samplePartIndex.size(); ++i) {
				if (i > pdt.getTemplatePartIndex().size() - 1) {
					return 1;
				}
				int thisIndex = this.samplePartIndex.get(i);
				int compareIndex = pdt.getTemplatePartIndex().get(i);
				int diff = thisIndex - compareIndex;
				if (diff != 0) {
					return diff;
				}
			}
		}
		
		if(this.templatePartIndex == null && pdt.getTemplatePartIndex() == null) {
			for (int i = 0; i < this.samplePartIndex.size(); ++i) {
				if (i > pdt.getSamplePartIndex().size() - 1) {
					return 1;
				}
				int thisIndex = this.samplePartIndex.get(i);
				int compareIndex = pdt.getSamplePartIndex().get(i);
				int diff = thisIndex - compareIndex;
				if (diff != 0) {
					return diff;
				}
			}
		}

		return 0;
	}

}
