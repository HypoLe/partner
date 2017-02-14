/**
 * 
 */
package com.boco.eoms.partner.assess.AssFlow.webapp.form;

/**
 * <p>
 * Title:report和Step的信息
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Date:Dec 13, 2010 9:47:35 AM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public class AssReportAndStepForm {
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
	
	/**
	 * 考核report表id
	 */
	private String reportId;
	
	/**
	 * 任务执行者
	 */
	private String operateOrgId;

	/**
	 * 任务执行者类型
	 */
	private String operateOrgType;

	/**
	 * 实际任务执行人
	 */
	private String operateUser;

	/**
	 * 操作时间
	 */
	private String operateTime;

	/**
	 * 任务执行人联系方式
	 */
	private String operateUserContact;

	/**
	 * 操作意见
	 */
	private String operateOpinion;
	
	/**
	 * 附件
	 */
	private String accessoriesId;
	
	/**
	 * 操作阶段
	 */
	private String state;
	
	/**
	 * 状态
	 */
	private String operateFlag;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getTimeType() {
		return timeType;
	}

	public void setTimeType(String timeType) {
		this.timeType = timeType;
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

	public String getIsPublish() {
		return isPublish;
	}

	public void setIsPublish(String isPublish) {
		this.isPublish = isPublish;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
	}

	public String getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(String totalScore) {
		this.totalScore = totalScore;
	}

	public String getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getOperateOrgId() {
		return operateOrgId;
	}

	public void setOperateOrgId(String operateOrgId) {
		this.operateOrgId = operateOrgId;
	}

	public String getOperateOrgType() {
		return operateOrgType;
	}

	public void setOperateOrgType(String operateOrgType) {
		this.operateOrgType = operateOrgType;
	}

	public String getOperateUser() {
		return operateUser;
	}

	public void setOperateUser(String operateUser) {
		this.operateUser = operateUser;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public String getOperateUserContact() {
		return operateUserContact;
	}

	public void setOperateUserContact(String operateUserContact) {
		this.operateUserContact = operateUserContact;
	}

	public String getOperateOpinion() {
		return operateOpinion;
	}

	public void setOperateOpinion(String operateOpinion) {
		this.operateOpinion = operateOpinion;
	}

	public String getAccessoriesId() {
		return accessoriesId;
	}

	public void setAccessoriesId(String accessoriesId) {
		this.accessoriesId = accessoriesId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getOperateFlag() {
		return operateFlag;
	}

	public void setOperateFlag(String operateFlag) {
		this.operateFlag = operateFlag;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
}
