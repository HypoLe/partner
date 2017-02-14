package com.boco.eoms.summary.webapp.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import com.boco.eoms.base.webapp.form.BaseForm;
 
/**
 * @author 龚玉峰
 *
 */
public class TawzjWeekForm extends BaseForm implements java.io.Serializable {

	 
	private String id;

	private String weekid;

	private String description; //工作描述

	private String schedule;// 工作进度

	private String remark; // 备注

	private String question; //存在问题

	private String state;	// 状态

	private String auditer; // 审核人
	
	private String cruser;
	
	private String auditerName;
	
	private String cruserName;
	 
	public String getAuditerName() {
		return auditerName;
	}

	public void setAuditerName(String auditerName) {
		this.auditerName = auditerName;
	}

	public String getCruser() {
		return cruser;
	}

	public void setCruser(String cruser) {
		this.cruser = cruser;
	}

	public String getCruserName() {
		return cruserName;
	}

	public void setCruserName(String cruserName) {
		this.cruserName = cruserName;
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

	/** Default empty constructor. */
	 

}
