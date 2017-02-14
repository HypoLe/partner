package com.boco.eoms.partner.assiEva.mgr.impl;

import java.util.List;

import com.boco.eoms.base.api.EOMSMgr;
import com.boco.eoms.base.util.StaticVariable;
import com.boco.eoms.commons.system.dept.dao.TawSystemDeptDao;
import com.boco.eoms.commons.system.role.util.RoleConstants;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.model.TawSystemUserRefRole;
import com.boco.eoms.partner.assiEva.dao.IAssiEvaReportInfoDao;
import com.boco.eoms.partner.assiEva.mgr.IAssiEvaReportInfoMgr;
import com.boco.eoms.partner.assiEva.model.AssiEvaReportInfo;

public class AssiEvaReportInfoMgrImpl implements IAssiEvaReportInfoMgr {

	private IAssiEvaReportInfoDao ReportInfoDao;
 
	public IAssiEvaReportInfoDao getAssiEvaReportInfoDao() {
		return ReportInfoDao;
	}

	public void setAssiEvaReportInfoDao(IAssiEvaReportInfoDao ReportInfoDao) {
		this.ReportInfoDao = ReportInfoDao;
	}

	public void saveAssiEvaReportInfo(AssiEvaReportInfo assiEvaReportInfo) {
		ReportInfoDao.saveAssiEvaReportInfo(assiEvaReportInfo);
	}

	public AssiEvaReportInfo getAssiEvaReportInfo(String id) {
		return ReportInfoDao.getAssiEvaReportInfo(id);
	}

	public void removeAssiEvaReportInfo(AssiEvaReportInfo assiEvaReportInfo) {
		ReportInfoDao.removeAssiEvaReportInfo(assiEvaReportInfo);
	}

	public List getReportInfoByCondition(String conditions) {
		return ReportInfoDao.getReportInfoByCondition(conditions);
	}
}
