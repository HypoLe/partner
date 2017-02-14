package com.boco.eoms.partner.eva.webapp.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.webapp.form.BaseForm;
import com.boco.eoms.partner.eva.util.PnrEvaConstants;

public class PnrEvaTemplateForm extends BaseForm implements java.io.Serializable {

	/**
	 * 主键
	 */
	protected String id;

	/**
	 * 模板名称
	 */
	protected String templateName;

	/**
	 * 模板激活状态
	 */
	protected String activated;

	/**
	 * 创建人
	 */
	protected String creator;

	/**
	 * 创建时间
	 */
	protected String createTime;

	/**
	 * 创建人组织
	 */
	protected String creatorOrgId;

	/**
	 * 周期
	 */
	protected String cycle;

	/**
	 * 起始时间
	 */
	protected String startTime;

	/**
	 * 结束时间
	 */
	protected String endTime;

	/**
	 * 接收组织
	 */
	protected String recieverOrgId;

	/**
	 * 备注
	 */
	protected String remark;

	/**
	 * 模板处理组织类型
	 */
	protected String orgType;

	/**
	 * 所属专业
	 */
	protected String professional;
	
	/**
	 * 地域信息
	 */
	private String area;
	
	/**
	 * 汇总方式
	 */
	private String  sumType ;
	
	/**
	 *考核内容 
	 */
	private String content ;
	
	/**
	 * 数据来源
	 */
	private String dataSource ;
	
	/**
	 *是否评价 
	 */
	private String isImpersonal ;
	
	/**
	 *评估阶段 
	 */
	private String evaluationPhase ;
	
	/**
	 *模板审核状态,让其默认状态为未送审
	 */
	private String auditFlag = PnrEvaConstants.AUDIT_UNDO;
	
	/**
	 *叶子节点标识 
	 */
	private String leaf ;
	
	/**
	 *评估角色 
	 */
	private String evaluationRole ;
	
	/**
	 *节点所对应的权重值 
	 */
	private float weight ;
	
	/**
	 *考核层面 
	 */
	private String executeType ;
	
	/**
	 *评分执行者 0-各地域；1-网络中心；2-网络部
	 */
	private String executeOrg ;
	
	/**
	 * 是否锁定：开放，锁定,只对权重控制
	 */
	private String isLock;	
	
	public String getIsLock() {
		return isLock;
	}

	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}

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

	public String getLeaf() {
		return leaf;
	}

	public void setLeaf(String leaf) {
		this.leaf = leaf;
	}

	public String getEvaluationRole() {
		return evaluationRole;
	}

	public void setEvaluationRole(String evaluationRole) {
		this.evaluationRole = evaluationRole;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getProfessional() {
		return professional;
	}

	public void setProfessional(String professional) {
		this.professional = professional;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
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

	/**
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		// reset any boolean data types to false

	}

	public String getRecieverOrgId() {
		return recieverOrgId;
	}

	public void setRecieverOrgId(String recieverOrgId) {
		this.recieverOrgId = recieverOrgId;
	}

	public String getExecuteOrg() {
		return executeOrg;
	}

	public void setExecuteOrg(String executeOrg) {
		this.executeOrg = executeOrg;
	}
	

}
