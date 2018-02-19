package cn.com.xiaofabo.tylaw.fundcontrast.entity;

import java.io.File;

public class FileParam {
	private File file;
	private String fileName;
	private String fileType;
	private String filePath;
	private long size;
	
	public FileParam(){}
	
	public FileParam(String fileName, String fileType, String filePath, long size) {
		super();
		this.fileName = fileName;
		this.fileType = fileType;
		this.filePath = filePath;
		this.size = size;
	}

	public FileParam(File file, String fileName, String fileType, String filePath, long size) {
		this.file = file;
		this.fileName = fileName;
		this.fileType = fileType;
		this.filePath = filePath;
		this.size =size;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "FileParam [fileName=" + fileName + ", fileType=" + fileType
				+ ", filePath=" + filePath + ", size=" + size + "]";
	}
	
}
