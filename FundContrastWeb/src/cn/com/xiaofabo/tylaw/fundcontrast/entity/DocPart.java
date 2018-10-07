/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.com.xiaofabo.tylaw.fundcontrast.entity;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Guangxi Chen
 */
public class DocPart implements Comparable<DocPart> {

	String title;
	String text;
	String index;
	String partCount;
	List<DocPart> childParts;
	List<Integer> partId;
	List<Integer> partIndex;

	public DocPart() {
		partIndex = new LinkedList<Integer>();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPoint() {
		return text == null ? (title) : (title + "\n" + text);
	}

	public String getWholePoint() {
		return text == null ? (index + title) : (index + title + "\n" + text);
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public boolean addPart(DocPart part) {
		if (childParts == null) {
			childParts = new LinkedList();
		}
		return childParts.add(part);
	}

	public String getPartCount() {
		return partCount;
	}

	public void setPartCount(String partCount) {
		this.partCount = partCount;
	}

	public List<Integer> getPartId() {
		return partId;
	}

	public void setPartId(List<Integer> partId) {
		this.partId = partId;
	}

	public List<Integer> getPartIndex() {
		return partIndex;
	}

	public String getPartIndexStr() {
		if (partIndex == null)
			return null;
		String indexStr = "";
		for (int index = 0; index < partIndex.size() - 1; ++index) {
			indexStr += partIndex.get(index) + "-";
		}
		indexStr += partIndex.get(partIndex.size() - 1);
		return indexStr;
	}

	public void setPartIndex(List<Integer> partIndex) {
		this.partIndex = partIndex;
	}

	public List<DocPart> getChildPart() {
		return childParts;
	}

	public void setChildPart(List<DocPart> childPart) {
		this.childParts = childPart;
	}

	/// This doc part has child part
	public boolean hasChildParts() {
		if (childParts == null) {
			return false;
		}
		if (childParts.isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder toReturn = new StringBuilder();
		toReturn.append("Title: ").append(title).append("\n");
		toReturn.append("Text: ").append(text).append("\n");
		if (childParts != null) {
			for (int i = 0; i < childParts.size(); ++i) {
				toReturn.append(childParts.get(i).toString());
			}
		}
		return toReturn.toString();
	}

	@Override
	public int compareTo(DocPart dp) {
		for (int i = 0; i < partIndex.size(); ++i) {
			if (i > dp.getPartIndex().size() - 1) {
				return 1;
			}
			int thisIndex = partIndex.get(i);
			int compareIndex = dp.getPartIndex().get(i);
			int diff = thisIndex - compareIndex;
			if (diff != 0) {
				return diff;
			}
		}
		return 0;
	}
}
