package com.boco.eoms.partner.assess.AssTree.model;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title: 考核模板
 * </p>
 * <p>
 * Description:考核模板
 * </p>
 * <p>
 * Date:Nov 23, 2010 2:02:35 PM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */

public class AssTemplate extends BaseObject {

	/**
	 * 主键
	 */
	private String id;

	/**
	 * 模板名称
	 */
	private String templateName;

	/**
	 * 模板激活状态
	 */
	private String activated;

	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 创建时间
	 */
	private String createTime;

	/**
	 * 创建人组织
	 */
	private String creatorOrgId;

	/**
	 * 周期
	 */
	private String cycle;

	/**
	 * 起始时间
	 */
	private String startTime;

	/**
	 * 结束时间
	 */
	private String endTime;

	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 模板类型ID
	 */
	private String templateTypeId;

	/**
	 * 删除标志
	 */
	private String deleted;

	/**
	 * 模板处理单位类型（AssConstants.ORG_ROLE, AssConstants.ORG_DEPT,
	 * AssConstants.ORG_USER）
	 */
	private String orgType;
	
	/**
	 * 模板总分（仅作为页面显示总分用，数据库中不存在此字段）
	 */
	private Float totalScore;
	
	private String professional;
	
	/**
	 * 由于树图结构中，模版节点对应的模版会有多个（激活后的模版修改即新增），这样需要一个字段来标示模版属于哪一个树节点.
	 * @return
	 */
	private String belongNode;
	
	
	public String getBelongNode() {
		return belongNode;
	}

	public void setBelongNode(String belongNode) {
		this.belongNode = belongNode;
	}

	public String getProfessional() {
		return professional;
	}

	public void setProfessional(String professional) {
		this.professional = professional;
	}

	public String getTemplateTypeId() {
		return templateTypeId;
	}

	public void setTemplateTypeId(String templateTypeId) {
		this.templateTypeId = templateTypeId;
	}

	public Float getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Float totalScore) {
		this.totalScore = totalScore;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreatorOrgId() {
		return creatorOrgId;
	}

	public void setCreatorOrgId(String creatorOrgId) {
		this.creatorOrgId = creatorOrgId;
	}

	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getActivated() {
		return activated;
	}

	public void setActivated(String activated) {
		this.activated = activated;
	}

	public boolean equals(Object o) {
		return this == o;
	}

	public int hashCode() {
		return 0;
	}
	
	private String deviceType;

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

}
