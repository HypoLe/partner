package com.boco.eoms.summary.model;

import com.boco.eoms.base.model.BaseObject;

/**
 * This class is used to generate the Struts Validator Form as well as the This
 * class is used to generate Spring Validation rules as well as the Hibernate
 * mapping file.
 * 
 * <p>
 * <a href="TawzjWeek.java.html"><i>View Source</i></a>
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a> Updated by
 *         Dan Kibler (dan@getrolling.com) Extended to implement Acegi
 *         UserDetails interface by David Carter david@carter.net
 * 
 * @struts.form include-all="true" extends="BaseForm"
 * @hibernate.class table="sp_submit"
 */
/**
 * @author 龚玉峰
 *
 */
public class TawzjWeek extends BaseObject  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String weekid;

	private String description; //工作描述

	private String schedule;// 工作进度

	private String remark; // 备注

	private String question; //存在问题

	private String state;	// 状态

	private String auditer; // 当前操作人
	
	private String cruser ;// 申请人
	
	private String name ; 
	
	private String crtime;
	

	public String getCrtime() {
		return crtime;
	}

	public void setCrtime(String crtime) {
		this.crtime = crtime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCruser() {
		return cruser;
	}

	public void setCruser(String cruser) {
		this.cruser = cruser;
	}

	public String getAuditer() {
		return auditer;
	}

	public void setAuditer(String auditer) {
		this.auditer = auditer;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getWeekid() {
		return weekid;
	}

	public void setWeekid(String weekid) {
		this.weekid = weekid;
	}

	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	 

}
