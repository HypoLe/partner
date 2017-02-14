package com.boco.eoms.partner.inspect.model;

import java.io.Serializable;

public class ErrorDistance implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String id;// 主建
	String speciality;// 专业
	String resource;//资源点
	int rule;  //规则
	int intervalTime;  //时间间隔
	String addTime;// 添加时间
	String addUser;//添加人员
	int delete;//0正常,1逻辑删除
	
	
	public int getIntervalTime() {
		return intervalTime;
	}
	public void setIntervalTime(int intervalTime) {
		this.intervalTime = intervalTime;
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
	public int getDelete() {
		return delete;
	}
	public void setDelete(int delete) {
		this.delete = delete;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	
	public int getRule() {
		return rule;
	}
	public void setRule(int rule) {
		this.rule = rule;
	}
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	
}
