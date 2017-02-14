package com.boco.eoms.task.model;

import com.boco.eoms.base.model.BaseObject;

/**
 * 任务附件
 * 
 * @author qiuzi
 * 
 */
public class TaskAccessories extends BaseObject {

	/**
	 * 自动生成序列号
	 */
	private static final long serialVersionUID = -6975023945093244192L;

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 附件名
	 */
	private String fileName;

	/**
	 * 映射文件名
	 */
	private String mappingName;

	/**
	 * 文件大小
	 */
	private String fileSize;

	/**
	 * 模块名
	 */
	private String filePath;

	/**
	 * 上传人
	 */
	private String uploader;

	/**
	 * 上传时间
	 */
	private String uploadTime;

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getMappingName() {
		return mappingName;
	}

	public void setMappingName(String mappingName) {
		this.mappingName = mappingName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getUploader() {
		return uploader;
	}

	public void setUploader(String uploader) {
		this.uploader = uploader;
	}

	public String getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}

	public boolean equals(Object o) {
		return false;
	}

}