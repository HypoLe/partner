package com.boco.eoms.partner.eva.model;

import com.boco.eoms.base.model.BaseObject;

/**
 * <p>
 * Title:考核模板
 * </p>
 * <p>
 * Description:考核模板
 * </p>
 * <p>
 * Date:2008-11-20 上午11:02:55
 * </p>
 * 
 * @author 李秋野
 * @version 3.5.1
 * 
 */

public class PnrEvaTemplate extends BaseObject {

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
	 * 模板处理单位类型（EvaConstants.ORG_ROLE, EvaConstants.ORG_DEPT,
	 * EvaConstants.ORG_USER）
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
	
	/**
	 * 地域信息
	 */
	
	private String area;

	/**
	 *汇总方式 
	 */
	
	private String sumType ;
	
	/**
	 *考核内容 
	 */
	
	private String content ;
	
	/**
	 *数据来源
	 */
	
	private String dataSource ;
	
	/**
	 *数据类型 
	 */
	
	private String dataType ;
	
	/**
	 *是否客观评价 
	 */
	private String isImpersonal ;
	
	/**
	 *评估阶段 
	 */
	private String evaluationPhase ;
	
	/**
	 *模板审核状态 
	 */
	private String auditFlag ;
	
	/**
	 *叶子节点标识 
	 */
	private String leaf ;
	
	/**
	 *评估角色 
	 */
	private String evaluationRole ;
	
	/**
	 *指标节点对应的权值，模板节点默认为100
	 */
	private float weight ;
	
	
	/**
	 * 是否锁定：开放，锁定,只对权重控制
	 */
	private String isLock;	
	
	/**
	 *考核层面 
	 */
	private String executeType ;
	
	/**
	 *评分执行者 0-各地域；1-网络中心；2-网络部
	 */
	private String executeOrg ;
	
	
	public String getExecuteType() {
		return executeType;
	}

	public void setExecuteType(String executeType) {
		this.executeType = executeType;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public String getSumType() {
		return sumType;
	}

	public void setSumType(String sumType) {
		this.sumType = sumType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getIsImpersonal() {
		return isImpersonal;
	}

	public void setIsImpersonal(String isImpersonal) {
		this.isImpersonal = isImpersonal;
	}

	public String getEvaluationPhase() {
		return evaluationPhase;
	}

	public void setEvaluationPhase(String evaluationPhase) {
		this.evaluationPhase = evaluationPhase;
	}

	public String getAuditFlag() {
		return auditFlag;
	}

	public void setAuditFlag(String auditFlag) {
		this.auditFlag = auditFlag;
	}


	public String getEvaluationRole() {
		return evaluationRole;
	}

	public void setEvaluationRole(String evaluationRole) {
		this.evaluationRole = evaluationRole;
	}

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
		return false;
	}

	public String toString() {
		return null;
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

	public String getLeaf() {
		return leaf;
	}

	public void setLeaf(String leaf) {
		this.leaf = leaf;
	}

	public String getIsLock() {
		return isLock;
	}

	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}

	public String getExecuteOrg() {
		return executeOrg;
	}

	public void setExecuteOrg(String executeOrg) {
		this.executeOrg = executeOrg;
	}

}
