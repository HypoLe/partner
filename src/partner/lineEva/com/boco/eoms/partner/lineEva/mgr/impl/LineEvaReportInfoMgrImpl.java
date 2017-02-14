package com.boco.eoms.partner.lineEva.mgr.impl;

import java.util.List;

import com.boco.eoms.base.api.EOMSMgr;
import com.boco.eoms.base.util.StaticVariable;
import com.boco.eoms.commons.system.dept.dao.TawSystemDeptDao;
import com.boco.eoms.commons.system.role.util.RoleConstants;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.model.TawSystemUserRefRole;
import com.boco.eoms.partner.lineEva.dao.ILineEvaReportInfoDao;
import com.boco.eoms.partner.lineEva.mgr.ILineEvaReportInfoMgr;
import com.boco.eoms.partner.lineEva.model.LineEvaReportInfo;

public class LineEvaReportInfoMgrImpl implements ILineEvaReportInfoMgr {

	private ILineEvaReportInfoDao ReportInfoDao;
 
	public ILineEvaReportInfoDao getLineEvaReportInfoDao() {
		return ReportInfoDao;
	}

	public void setLineEvaReportInfoDao(ILineEvaReportInfoDao ReportInfoDao) {
		this.ReportInfoDao = ReportInfoDao;
	}

	public void saveLineEvaReportInfo(LineEvaReportInfo lineEvaReportInfo) {
		ReportInfoDao.saveLineEvaReportInfo(lineEvaReportInfo);
	}

	public LineEvaReportInfo getLineEvaReportInfo(String id) {
		return ReportInfoDao.getLineEvaReportInfo(id);
	}

	public void removeLineEvaReportInfo(LineEvaReportInfo lineEvaReportInfo) {
		ReportInfoDao.removeLineEvaReportInfo(lineEvaReportInfo);
	}

	public List getReportInfoByCondition(String conditions) {
		return ReportInfoDao.getReportInfoByCondition(conditions);
	}
}
