package com.boco.eoms.partner.eva.model;

import com.boco.eoms.base.model.BaseObject;

/**
 * 
 * <p>
 * Title:考核任务
 * </p>
 * <p>
 * Description:记录模板和组织的关系
 * </p>
 * <p>
 * Date:2009-1-6 下午05:02:45
 * </p>
 * 
 * @author 李秋野
 * @version 3.5.1
 *
 */
public class PnrEvaTask extends BaseObject {
	/**
	 * 主键
	 */
	private String id;

	/**
	 * 模板id
	 */
	private String templateId;

	/**
	 * 组织id
	 */
	private String orgId;

	/**
	 * 组织类型
	 */
	private String orgType;

	/**
	 * 任务生成时间
	 */
	private String createTime;

	/**
	 * 任务生成操作人
	 */
	private String creator;
	
	/**
	 * 任务生成操作人
	 */
	private String nodeId;

	/**
	 *评分执行者 0-各地域；1-网络中心；2-网络部
	 */
	private String executeOrg ;
	
	public String getExecuteOrg() {
		return executeOrg;
	}

	public void setExecuteOrg(String executeOrg) {
		this.executeOrg = executeOrg;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final PnrEvaTask other = (PnrEvaTask) obj;
		if (createTime == null) {
			if (other.createTime != null)
				return false;
		} else if (!createTime.equals(other.createTime))
			return false;
		if (creator == null) {
			if (other.creator != null)
				return false;
		} else if (!creator.equals(other.creator))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nodeId == null) {
			if (other.nodeId != null)
				return false;
		} else if (!nodeId.equals(other.nodeId))
			return false;
		if (orgId == null) {
			if (other.orgId != null)
				return false;
		} else if (!orgId.equals(other.orgId))
			return false;
		if (orgType == null) {
			if (other.orgType != null)
				return false;
		} else if (!orgType.equals(other.orgType))
			return false;
		if (templateId == null) {
			if (other.templateId != null)
				return false;
		} else if (!templateId.equals(other.templateId))
			return false;
		return true;
	}

	public String toString() {
		return null;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + ((creator == null) ? 0 : creator.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nodeId == null) ? 0 : nodeId.hashCode());
		result = prime * result + ((orgId == null) ? 0 : orgId.hashCode());
		result = prime * result + ((orgType == null) ? 0 : orgType.hashCode());
		result = prime * result
				+ ((templateId == null) ? 0 : templateId.hashCode());
		return result;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
}
