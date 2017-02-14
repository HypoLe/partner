package com.boco.activiti.partner.process.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TheadModel implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String proChineseName ;	
	private String proType ;
	private String proEnglishName ;
	private List<MasterScene> proValue ;
	private String isQuota ;
	private String className;
	private String isRequire;
	
	
	
	public String getIsRequire() {
		return isRequire;
	}
	public void setIsRequire(String isRequire) {
		this.isRequire = isRequire;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getProChineseName() {
		return proChineseName;
	}
	public void setProChineseName(String proChineseName) {
		this.proChineseName = proChineseName;
	}
	public String getProType() {
		return proType;
	}
	public void setProType(String proType) {
		this.proType = proType;
	}
	public String getProEnglishName() {
		return proEnglishName;
	}
	public void setProEnglishName(String proEnglishName) {
		this.proEnglishName = proEnglishName;
	}
	public List<MasterScene> getProValue() {
		return proValue;
	}
	public void setProValue(List<MasterScene> proValue) {
		this.proValue = proValue;
	}
	public String getIsQuota() {
		return isQuota;
	}
	public void setIsQuota(String isQuota) {
		this.isQuota = isQuota;
	}
	
	

}
