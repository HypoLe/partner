package com.boco.eoms.commons.log.model;

public class ClickService {

	String systemid;
	String systemname;
	String clicktime;
	String userid;
	String username;
	String userip;
	String userdeptid;
	String userdept;
	String busiModuleID;
	String busiModuleName;
	
	
	public String getUserdept() {
		return userdept;
	}
	public void setUserdept(String userdept) {
		this.userdept = userdept;
	}
	
	public String getSystemid() {
		return systemid;
	}
	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}
	public String getSystemname() {
		return systemname;
	}
	public void setSystemname(String systemname) {
		this.systemname = systemname;
	}
	public String getClicktime() {
		return clicktime;
	}
	public void setClicktime(String clicktime) {
		this.clicktime = clicktime;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserip() {
		return userip;
	}
	public void setUserip(String userip) {
		this.userip = userip;
	}
	public String getUserdeptid() {
		return userdeptid;
	}
	public void setUserdeptid(String userdeptid) {
		this.userdeptid = userdeptid;
	}
	public String getBusiModuleID() {
		return busiModuleID;
	}
	public void setBusiModuleID(String busiModuleID) {
		this.busiModuleID = busiModuleID;
	}
	public String getBusiModuleName() {
		return busiModuleName;
	}
	public void setBusiModuleName(String busiModuleName) {
		this.busiModuleName = busiModuleName;
	}
}
