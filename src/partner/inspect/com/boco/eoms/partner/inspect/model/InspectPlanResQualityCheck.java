package com.boco.eoms.partner.inspect.model;

import java.util.Date;

/** 
 * Description: 巡检任务质检
 * Copyright:   Copyright (c)2013
 * Company:     BOCO
 * @author:     LiuChang
 * @version:    1.0
 * Create at:   Apr 19, 2013 3:18:14 PM
 */
public class InspectPlanResQualityCheck {
	private String id;
	private String planId;         //计划ID
	private Long planResId;        //巡检任务Id
    private Float scoreInTime;     //相应及时率
    private Float scoreStandard;   //通报规范性
    private Float scoreFinish;     //任务完成情况
    private Float scoreStatisfied; //客户满意度
    private Float scoreTotal;      //总分
	private String satisfaction;   //满意度
	private String remark;         //文本描述
	private Date checkTime;        //质检时间
	private String checkUser;      //质检人
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public Long getPlanResId() {
		return planResId;
	}
	public void setPlanResId(Long planResId) {
		this.planResId = planResId;
	}
	public Float getScoreInTime() {
		return scoreInTime;
	}
	public void setScoreInTime(Float scoreInTime) {
		this.scoreInTime = scoreInTime;
	}
	public Float getScoreStandard() {
		return scoreStandard;
	}
	public void setScoreStandard(Float scoreStandard) {
		this.scoreStandard = scoreStandard;
	}
	public Float getScoreFinish() {
		return scoreFinish;
	}
	public void setScoreFinish(Float scoreFinish) {
		this.scoreFinish = scoreFinish;
	}
	public Float getScoreStatisfied() {
		return scoreStatisfied;
	}
	public void setScoreStatisfied(Float scoreStatisfied) {
		this.scoreStatisfied = scoreStatisfied;
	}
	public Float getScoreTotal() {
		return scoreTotal;
	}
	public void setScoreTotal(Float scoreTotal) {
		this.scoreTotal = scoreTotal;
	}
	public String getSatisfaction() {
		return satisfaction;
	}
	public void setSatisfaction(String satisfaction) {
		this.satisfaction = satisfaction;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public String getCheckUser() {
		return checkUser;
	}
	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}
	
	
}
