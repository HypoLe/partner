package com.boco.eoms.mobile.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class AndroidAppInfoForm extends ActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String appName;//应用名称
	private String packageName;//包名
	private String fileName;//文件名称
	private String versionName;//版本名称
	private String versionCode;//版本号
	private String introduction;//版本介绍
	private String fileSize;//文件大小(该大小为自动计算)
	private String addTime;//添加时间
	private String addUser;//添加人
	private String downloadStatus;//是否可以发布该应用  0:可以,1 否
	private FormFile appFile;  
	public String getDownloadStatus() {
		return downloadStatus;
	}
	public void setDownloadStatus(String downloadStatus) {
		this.downloadStatus = downloadStatus;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public String getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
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
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getAddUser() {
		return addUser;
	}
	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}
	public FormFile getAppFile() {
		return appFile;
	}
	public void setAppFile(FormFile appFile) {
		this.appFile = appFile;
	}

	
}
