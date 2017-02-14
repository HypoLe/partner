package com.boco.eoms.partner.lineEva.mgr.impl;

import java.util.List;

import com.boco.eoms.base.api.EOMSMgr;
import com.boco.eoms.base.util.StaticVariable;
import com.boco.eoms.commons.system.dept.dao.TawSystemDeptDao;
import com.boco.eoms.commons.system.role.util.RoleConstants;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.model.TawSystemUserRefRole;
import com.boco.eoms.partner.lineEva.dao.ILineEvaOrgDao;
import com.boco.eoms.partner.lineEva.mgr.ILineEvaOrgMgr;
import com.boco.eoms.partner.lineEva.model.LineEvaOrg;

public class LineEvaOrgMgrImpl implements ILineEvaOrgMgr {

	private ILineEvaOrgDao orgDao;

	private TawSystemDeptDao deptDao;

	public ILineEvaOrgDao getLineEvaOrgDao() {
		return orgDao;
	}

	public void setLineEvaOrgDao(ILineEvaOrgDao orgDao) {
		this.orgDao = orgDao;
	}

	public TawSystemDeptDao getTawSystemDeptDao() {
		return deptDao;
	}

	public void setTawSystemDeptDao(TawSystemDeptDao deptDao) {
		this.deptDao = deptDao;
	}

	public void saveLineEvaOrg(LineEvaOrg lineEvaOrg) {
		orgDao.saveLineEvaOrg(lineEvaOrg);
	}

	public LineEvaOrg getLineEvaOrg(String id) {
		return orgDao.getLineEvaOrg(id);
	}

	public void removeLineEvaOrg(LineEvaOrg lineEvaOrg) {
		orgDao.removeLineEvaOrg(lineEvaOrg);
	}

	public List getOrgsByTempletId(String templateId) {
		return orgDao.getOrgsByTempletId(templateId);
	}

	public void removeOrgOfTemplate(String templateId) {
		orgDao.getOrgsByTempletId(templateId);
	}

	public void removeOrgOfTemplateAndActionType(String templateId,
			String actionType) {
		orgDao.removeOrgOfTemplateAndActionType(templateId, actionType);
	}

	// 根据用户信息获取用户可用的模板ID列表
	public List getTempletByUserId(String userId, String actionType,
			String status) {
		String where = " where ";
		TawSystemUser user = EOMSMgr.getOrgMgrs().getUserMgr().getUser(userId);
		List roles = EOMSMgr.getOrgMgrs().getRoleMgr().listSubroleOfUser(
				userId, RoleConstants.ALL_ROLE);
		String tempRole = " org.orgId in (";
		for (int i = 0; i < roles.size(); i++) {
			TawSystemUserRefRole role = (TawSystemUserRefRole) roles.get(i);
			tempRole += "'" + role.getRoleid() + "',";
		}
		tempRole = tempRole.substring(0, tempRole.length() - 1) + ")";
		where += " ((org.orgId='" + user.getUserid() + "' and org.orgType='"
				+ StaticVariable.PRIV_ASSIGNTYPE_USER + "')";
		where += " or (org.orgId='" + user.getDeptid() + "' and org.orgType='"
				+ StaticVariable.PRIV_ASSIGNTYPE_DEPT + "'))";
		if (roles.size() > 0) {
			where += " or (" + tempRole + " and org.orgType='"
					+ StaticVariable.PRIV_ASSIGNTYPE_ROLE + "')";
		}
		if (!actionType.equals("")) {
			where += " and org.actionType='" + actionType + "'";
		}
		if (!status.equals("")) {
			where += " and org.status='" + status + "'";
		}
		return orgDao.getTempletByOrgId(where);
	}

	public List getTaskByConditions(String conditions) {
		return orgDao.getTempletByOrgId(conditions);
	}

	// 根据任务ID获取最新的任务状态
	public LineEvaOrg getLatestTaskByTaskId(String taskId) {
		String where = " where org.templateId = '" + taskId
				+ "' order by org.operateTime desc";
		List list = orgDao.getTempletByOrgId(where);
		return (LineEvaOrg) list.get(0);
	}

}
