package com.boco.eoms.mobile.model;

import java.io.Serializable;

public class AndroidDeveloper implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String developerName;
	private String serialNumber;//该开发者的APK密钥序列号
	private String publicKey;//该开发者的APK密钥
	private String remark;
	
	private boolean isAllow;//该开发者的应用是否能进入大厅,被大厅管理  true可以放入大厅 ,false不可以
	private String addUser;
	private String addTime;
	private int deleteFlag;   //删除标示,0正常,1删除,
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
	public int getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getDeveloperName() {
		return developerName;
	}
	public void setDeveloperName(String developerName) {
		this.developerName = developerName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public boolean getIsAllow() {
		return isAllow;
	}
	public void setIsAllow(boolean isAllow) {
		this.isAllow = isAllow;
	}
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	
}
