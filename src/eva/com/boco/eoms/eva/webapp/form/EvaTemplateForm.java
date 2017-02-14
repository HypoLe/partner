package com.boco.eoms.eva.webapp.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.webapp.form.BaseForm;
import com.boco.eoms.commons.system.dict.service.ID2NameService;

public class EvaTemplateForm extends BaseForm implements java.io.Serializable {

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
	 * 模板处理单位
	 */
	private String orgId;

	/**
	 * 模板处理组织类型
	 */
	protected String orgType;

	/**
	 * 所属专业
	 */
	protected String professional;
	
	/**
	 * 关联合同分类
	 */
	protected String themeName;	
	/**
	 * 关联合同分类
	 */
	protected String themeId;
	/**
	 * 关联合同
	 */
	protected String contractId;

	/**
	 * 关联协议(临时工作任务)
	 */
	protected String agreementId;
	/**
	 * 合作伙伴部门id
	 */
	protected String partnerDept;
	/**
	 * 合作伙伴名称
	 */
	protected String partnerDeptName;
	
	/**
	 * 来自协议or临时工作任务
	 */
	protected String agrwor;		

	public String getPartnerDept() {
		return partnerDept;
	}

	public void setPartnerDept(String partnerDept) {
		this.partnerDept = partnerDept;
	}

	public String getPartnerDeptName() {
		return partnerDeptName;
	}

	public void setPartnerDeptName(String partnerDeptName) {
		this.partnerDeptName = partnerDeptName;
	}

	public String getAgreementId() {
		return agreementId;
	}

	public void setAgreementId(String agreementId) {
		this.agreementId = agreementId;
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

	public String getThemeId() {
		return themeId;
	}

	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}

	public String getThemeName() {
		ID2NameService mgr = (ID2NameService) ApplicationContextHolder.getInstance().getBean("id2nameService");
		themeName = mgr.id2Name(themeId,"ctTableThemeDao");
		return themeName;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getAgrwor() {
		return agrwor;
	}

	public void setAgrwor(String agrwor) {
		this.agrwor = agrwor;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
}
