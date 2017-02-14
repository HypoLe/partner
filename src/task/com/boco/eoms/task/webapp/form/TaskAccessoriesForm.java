package com.boco.eoms.task.webapp.form;

import java.io.Serializable;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * 任务附件表单类
 * 
 * @author qiuzi
 * 
 */
public class TaskAccessoriesForm extends BaseForm implements Serializable {

	/**
	 * 自动生成序列号
	 */
	private static final long serialVersionUID = 1743547981383670661L;

	/**
	 * 主键
	 */
	protected String id;

	/**
	 * 附件名
	 */
	protected String fileName;

	/**
	 * 映射文件名
	 */
	protected String mappingName;

	/**
	 * 文件大小
	 */
	protected String fileSize;

	/**
	 * 模块名
	 */
	protected String filePath;

	/**
	 * 上传人
	 */
	protected String uploader;

	/**
	 * 上传时间
	 */
	protected String uploadTime;

	/**
	 * 上传的文件
	 */
	protected FormFile uploadFile;

	/**
	 * 任务Id
	 */
	protected String taskId;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
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

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
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

	public FormFile getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(FormFile uploadFile) {
		this.uploadFile = uploadFile;
	}

}