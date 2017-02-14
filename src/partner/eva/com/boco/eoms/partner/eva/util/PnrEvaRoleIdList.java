package com.boco.eoms.partner.eva.util;

public class PnrEvaRoleIdList {
	/**
	 * 考核模板区负责人(一期保留)
	 */
	private Integer provinceAdminRoleId;

	public Integer getProvinceAdminRoleId() {
		return provinceAdminRoleId;
	}

	public void setProvinceAdminRoleId(Integer provinceAdminRoleId) {
		this.provinceAdminRoleId = provinceAdminRoleId;
	}
	
	/**
	 * 考核模型管理角色
	 */
	private String treeConfigRoleId;

	public String getTreeConfigRoleId() {
		return treeConfigRoleId;
	}

	public void setTreeConfigRoleId(String treeConfigRoleId) {
		this.treeConfigRoleId = treeConfigRoleId;
	}
	
	/**
	 * 考核模板审核角色
	 */
	private String templateAuditRoleId;

	public String getTemplateAuditRoleId() {
		return templateAuditRoleId;
	}

	public void setTemplateAuditRoleId(String templateAuditRoleId) {
		this.templateAuditRoleId = templateAuditRoleId;
	}
	
	/**
	 * 考核记录审核角色
	 */
	private String reportAuditRoleId;

	public String getReportAuditRoleId() {
		return reportAuditRoleId;
	}

	public void setReportAuditRoleId(String reportAuditRoleId) {
		this.reportAuditRoleId = reportAuditRoleId;
	}
	
	
	/**
	 * 考核表执行角色
	 */
	private String reportExecuteRoleId;

	public String getReportExecuteRoleId() {
		return reportExecuteRoleId;
	}

	public void setReportExecuteRoleId(String reportExecuteRoleId) {
		this.reportExecuteRoleId = reportExecuteRoleId;
	}
	
	/**
	 * 考核表查看角色
	 */
	private String reportShowRoleId;

	public String getReportShowRoleId() {
		return reportShowRoleId;
	}

	public void setReportShowRoleId(String reportShowRoleId) {
		this.reportShowRoleId = reportShowRoleId;
	}
	
	/**
	 * 厂家字典类型id
	 */
	private String factoryDictType;

	public String getFactoryDictType() {
		return factoryDictType;
	}

	public void setFactoryDictType(String factoryDictType) {
		this.factoryDictType = factoryDictType;
	}
	
	/**
	 * 省公司地域id
	 */
	private String rootAreaId;

	public String getRootAreaId() {
		return rootAreaId;
	}

	public void setRootAreaId(String rootAreaId) {
		this.rootAreaId = rootAreaId;
	}
	
	/**
	 * 省网络部部门id
	 */
	private String ndDept;

	public String getNdDept() {
		return ndDept;
	}

	public void setNdDept(String ndDept) {
		this.ndDept = ndDept;
	}
	
	/**
	 * 省网络中心部门id
	 */
	private String nmcDept;

	public String getNmcDept() {
		return nmcDept;
	}

	public void setNmcDept(String nmcDept) {
		this.nmcDept = nmcDept;
	}
}
