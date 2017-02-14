/**
 * 
 */
package com.boco.eoms.partner.assess.AssRight.util;

/**
 * <p>
 * Title:后评估系统操作角色集合类
 * </p>
 * <p>
 * Description:后评估系统操作角色集合类
 * </p>
 * <p>
 * Date:Nov 30, 2010 3:49:45 PM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public class AssRoleIdList {


	/**
	 * 考核模型管理角色
	 */
	public String treeConfigRoleId;

	public String getTreeConfigRoleId() {
		return treeConfigRoleId;
	}

	public void setTreeConfigRoleId(String treeConfigRoleId) {
		this.treeConfigRoleId = treeConfigRoleId;
	}
	
	/**
	 * 考核模板审核角色
	 */
	public String templateAuditRoleId;

	public String getTemplateAuditRoleId() {
		return templateAuditRoleId;
	}

	public void setTemplateAuditRoleId(String templateAuditRoleId) {
		this.templateAuditRoleId = templateAuditRoleId;
	}
	
	/**
	 * 考核记录审核角色
	 */
	public String reportAuditRoleId;

	public String getReportAuditRoleId() {
		return reportAuditRoleId;
	}

	public void setReportAuditRoleId(String reportAuditRoleId) {
		this.reportAuditRoleId = reportAuditRoleId;
	}
	
	
	/**
	 * 考核表执行角色
	 */
	public String reportExecuteRoleId;

	public String getReportExecuteRoleId() {
		return reportExecuteRoleId;
	}

	public void setReportExecuteRoleId(String reportExecuteRoleId) {
		this.reportExecuteRoleId = reportExecuteRoleId;
	}
	
	/**
	 * 考核表查看角色
	 */
	public String reportShowRoleId;

	public String getReportShowRoleId() {
		return reportShowRoleId;
	}

	public void setReportShowRoleId(String reportShowRoleId) {
		this.reportShowRoleId = reportShowRoleId;
	}
	
	/**
	 * 厂家确认角色（目前只支持一个厂家）
	 */
	public String factoryAdminRoleId;

	public String getFactoryAdminRoleId() {
		return factoryAdminRoleId;
	}

	public void setFactoryAdminRoleId(String factoryAdminRoleId) {
		this.factoryAdminRoleId = factoryAdminRoleId;
	}	
	/**
	 * 省公司地域id
	 */
	public String rootAreaId;

	public String getRootAreaId() {
		return rootAreaId;
	}

	public void setRootAreaId(String rootAreaId) {
		this.rootAreaId = rootAreaId;
	}
	
	/**
	 * 厂家字典类型id
	 */
	public String factoryDictType;

	public String getFactoryDictType() {
		return factoryDictType;
	}

	public void setFactoryDictType(String factoryDictType) {
		this.factoryDictType = factoryDictType;
	}

	/**
	 * 短信服务id
	 */
	public String serverId;

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}	
}
