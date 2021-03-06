package com.boco.eoms.partner.eva.model;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:指标考核实例
 * </p>
 * <p>
 * Description:指标考核实例
 * </p>
 * <p>
 * Date:2008-11-26 下午10:28:10
 * </p>
 * 
 * @author 李秋野
 * @version 3.5.1
 * 
 */

public class PnrEvaKpiInstance extends BaseObject {

	private String id;
	private String taskDetailId;  //任务详细信息ID
	private String time;			//选择时间
	private String timeType; 		//时间类型
	private String partnerId;		//合作伙伴部门ID
	private String partnerName;		//合作伙伴部门NAME	
	private float realScore;		//实际得分
	private String reduceReason;	//扣分原因
	private String remark;			//备注
	private String isPublish;		//上报标志
	private String createTime;		//记录创建时间
	private String auditFlag;		//任务审核标记
	
	private String taskId; //任务ID
	private String taskName;//任务NAME 
	
	private float maintenanceRatio;//维护比例
	private String assessUserId;//评定人
	private String assessDeptId;//评定部门

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

	public float getRealScore() {
		return realScore;
	}

	public void setRealScore(float realScore) {
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
		return false;
	}

	public String toString() {
		return null;
	}
	
	

	public String getAuditFlag() {
		return auditFlag;
	}

	public void setAuditFlag(String auditFlag) {
		this.auditFlag = auditFlag;
	}

	public int hashCode() {
		return 0;
	}

	public float getMaintenanceRatio() {
		return maintenanceRatio;
	}

	public void setMaintenanceRatio(float maintenanceRatio) {
		this.maintenanceRatio = maintenanceRatio;
	}

	public String getAssessUserId() {
		return assessUserId;
	}

	public void setAssessUserId(String assessUserId) {
		this.assessUserId = assessUserId;
	}

	public String getAssessDeptId() {
		return assessDeptId;
	}

	public void setAssessDeptId(String assessDeptId) {
		this.assessDeptId = assessDeptId;
	}

}
