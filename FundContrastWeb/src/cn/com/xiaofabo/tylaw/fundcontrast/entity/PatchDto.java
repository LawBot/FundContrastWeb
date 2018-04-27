package cn.com.xiaofabo.tylaw.fundcontrast.entity;

import java.util.List;

public class PatchDto implements Comparable<PatchDto> {

    private String orignalText;
    private int chapterIndex;
    private RevisedDto revisedDto;
    private String indexType;//(代表原文的序号则为“orginal”,代表新条文的序号则为"revised"，当新增加原文没有的条文，则类型为"revised")
    private String changeType;//(add:新增 delete:删减 change:更改)
    private String partId;
    private List<Integer> partIndex;
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

    public List<Integer> getPartIndex() {
        return partIndex;
    }

    public void setPartIndex(List<Integer> partIndex) {
        this.partIndex = partIndex;
    }
    
    public int getPartIndexDepth() {
    	if(partIndex == null || partIndex.isEmpty()) {
    		return 0;
    	}
    	return partIndex.size();
    }
    
    public List<Integer> getSamplePartIndex(){
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
    
    public String partIndexInStr() {
    	if(partIndex == null)
    		return null;
    	StringBuilder sb = new StringBuilder();
    	for(int i = 0; i < partIndex.size(); ++i) {
    		if(i != partIndex.size() - 1) {
    			sb.append(partIndex.get(i)).append("-");
    		}else {
    			sb.append(partIndex.get(i));
    		}
    	}
    	return sb.toString();
    }

    @Override
    public int compareTo(PatchDto pdt) {
        for (int i = 0; i < partIndex.size(); ++i) {
            if (i > pdt.getPartIndex().size() - 1) {
                return 1;
            }
            int thisIndex = partIndex.get(i);
            int compareIndex = pdt.getPartIndex().get(i);
            int diff = thisIndex - compareIndex;
            if (diff != 0) {
                return diff;
            }
        }
        return 0;
    }

}
