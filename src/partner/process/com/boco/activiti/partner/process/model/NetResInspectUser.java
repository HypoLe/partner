package com.boco.activiti.partner.process.model;

import java.io.Serializable;

/**
 * 
 * 根据众筹巡检需要新注册的用户信息表
 * 主要用来保存所属组织，地市，工作属性
 * 
 * @author WANGJUN
 * 
 */
public class NetResInspectUser implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String id; //主键
	
	private String userid;//用户ID
	
	private String groupname;//所属组织
	
	private String cityId;//地市ID
	
	private String jobAttributesId;//工作属性

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getJobAttributesId() {
		return jobAttributesId;
	}

	public void setJobAttributesId(String jobAttributesId) {
		this.jobAttributesId = jobAttributesId;
	}
}
