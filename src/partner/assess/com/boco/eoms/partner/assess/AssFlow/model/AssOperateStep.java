package com.boco.eoms.partner.assess.AssFlow.model;

import com.boco.eoms.base.model.BaseObject;
/**
 * <p>
 * Title:后评估步骤表信息
 * </p>
 * <p>
 * Description:后评估步骤表信息
 * </p>
 * <p>
 * Fri Sep 10 11:04:32 CST 2010
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public class AssOperateStep extends BaseObject {

	/**
	 * 主键
	 */
	private String id;
	
	/**
	 * 考核main表id
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
	
	/**
	 * 状态
	 */
	private String createTime;
	
	
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

	public boolean equals(Object o) {
		return this == o;
	}

	public int hashCode() {
		return 0;
	}
}
