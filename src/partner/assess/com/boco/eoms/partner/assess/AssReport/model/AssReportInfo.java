package com.boco.eoms.partner.assess.AssReport.model;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:考核报表信息
 * </p>
 * <p>
 * Description:考核报表信息
 * </p>
 * <p>
 * Date:Nov 30, 2010 2:11:22 PM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public class AssReportInfo extends BaseObject {

	private String id;
	private String taskId;
	private String taskName;
	private String time;
	private String area;
	private String timeType;
	private String partnerId;
	private String partnerName;
	private String isPublish;
	private String createTime;
	private String totalWeight;
	private String totalScore;
	private String totalMoney;
	private String state;
	private String createUser;
	private String createDept;
	private String deviceNum;
	private String treeNodeId;
	private String templateTypeNode;
		
	public String getTreeNodeId() {
		return treeNodeId;
	}

	public void setTreeNodeId(String treeNodeId) {
		this.treeNodeId = treeNodeId;
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

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
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

	public String getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(String totalScore) {
		this.totalScore = totalScore;
	}

	public String getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
	}

	public boolean equals(Object o) {
		return this == o;
	}

	public int hashCode() {
		return 0;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateDept() {
		return createDept;
	}

	public void setCreateDept(String createDept) {
		this.createDept = createDept;
	}

	public String getDeviceNum() {
		return deviceNum;
	}

	public void setDeviceNum(String deviceNum) {
		this.deviceNum = deviceNum;
	}

	public String getTemplateTypeNode() {
		return templateTypeNode;
	}

	public void setTemplateTypeNode(String templateTypeNode) {
		this.templateTypeNode = templateTypeNode;
	}

}
