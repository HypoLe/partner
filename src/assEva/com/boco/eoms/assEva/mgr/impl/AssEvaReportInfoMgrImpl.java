package com.boco.eoms.assEva.mgr.impl;

import java.util.List;

import com.boco.eoms.base.api.EOMSMgr;
import com.boco.eoms.base.util.StaticVariable;
import com.boco.eoms.commons.system.dept.dao.TawSystemDeptDao;
import com.boco.eoms.commons.system.role.util.RoleConstants;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.model.TawSystemUserRefRole;
import com.boco.eoms.assEva.dao.IAssEvaReportInfoDao;
import com.boco.eoms.assEva.mgr.IAssEvaReportInfoMgr;
import com.boco.eoms.assEva.model.AssEvaReportInfo;

public class AssEvaReportInfoMgrImpl implements IAssEvaReportInfoMgr {

	private IAssEvaReportInfoDao ReportInfoDao;
 
	public IAssEvaReportInfoDao getAssEvaReportInfoDao() {
		return ReportInfoDao;
	}

	public void setAssEvaReportInfoDao(IAssEvaReportInfoDao ReportInfoDao) {
		this.ReportInfoDao = ReportInfoDao;
	}

	public void saveAssEvaReportInfo(AssEvaReportInfo assEvaReportInfo) {
		ReportInfoDao.saveAssEvaReportInfo(assEvaReportInfo);
	}

	public AssEvaReportInfo getAssEvaReportInfo(String id) {
		return ReportInfoDao.getAssEvaReportInfo(id);
	}

	public void removeAssEvaReportInfo(AssEvaReportInfo assEvaReportInfo) {
		ReportInfoDao.removeAssEvaReportInfo(assEvaReportInfo);
	}

	public List getReportInfoByCondition(String conditions) {
		return ReportInfoDao.getReportInfoByCondition(conditions);
	}
}
