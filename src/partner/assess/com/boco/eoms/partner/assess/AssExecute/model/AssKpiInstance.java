package com.boco.eoms.partner.assess.AssExecute.model;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:指标考核实例
 * </p>
 * <p>
 * Description:指标考核实例
 * </p>
 * <p>
 * Date:Nov 26, 2010 5:52:06 PM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */

public class AssKpiInstance extends BaseObject {

	private String id;
	private String taskDetailId;  //任务详细信息ID
	private String reportId;  //打分汇总记录id
	private String time;			//选择时间
	private String timeType; 		//时间类型
	private String partnerId;		//合作伙伴部门ID
	private String partnerName;		//合作伙伴部门NAME	
	private String realScore;		//实际得分
	private String reduceReason;	//扣分原因
	private String remark;			//备注
	private String isPublish;		//上报标志
	private String createTime;		//记录创建时间
	
	private String taskId; //任务ID
	private String taskName;//任务NAME 
	private String money;//扣款金额 

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIsPublish() {
		return isPublish;
	}

	public void setIsPublish(String isPublish) {
		this.isPublish = isPublish;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getRealScore() {
		return realScore;
	}

	public void setRealScore(String realScore) {
		this.realScore = realScore;
	}

	public String getReduceReason() {
		return reduceReason;
	}

	public void setReduceReason(String reduceReason) {
		this.reduceReason = reduceReason;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTaskDetailId() {
		return taskDetailId;
	}

	public void setTaskDetailId(String taskDetailId) {
		this.taskDetailId = taskDetailId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
	}

	public boolean equals(Object o) {
		return this == o;
	}


	public int hashCode() {
		return 0;
	}
	
	protected String city;		//所属地市

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}
}
