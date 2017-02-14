package com.boco.eoms.partner.tranEva.mgr.impl;

import java.util.List;

import com.boco.eoms.base.api.EOMSMgr;
import com.boco.eoms.base.util.StaticVariable;
import com.boco.eoms.commons.system.dept.dao.TawSystemDeptDao;
import com.boco.eoms.commons.system.role.util.RoleConstants;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.model.TawSystemUserRefRole;
import com.boco.eoms.partner.tranEva.dao.ITranEvaReportInfoDao;
import com.boco.eoms.partner.tranEva.mgr.ITranEvaReportInfoMgr;
import com.boco.eoms.partner.tranEva.model.TranEvaReportInfo;

public class TranEvaReportInfoMgrImpl implements ITranEvaReportInfoMgr {

	private ITranEvaReportInfoDao ReportInfoDao;
 
	public ITranEvaReportInfoDao getTranEvaReportInfoDao() {
		return ReportInfoDao;
	}

	public void setTranEvaReportInfoDao(ITranEvaReportInfoDao ReportInfoDao) {
		this.ReportInfoDao = ReportInfoDao;
	}

	public void saveTranEvaReportInfo(TranEvaReportInfo tranEvaReportInfo) {
		ReportInfoDao.saveTranEvaReportInfo(tranEvaReportInfo);
	}

	public TranEvaReportInfo getTranEvaReportInfo(String id) {
		return ReportInfoDao.getTranEvaReportInfo(id);
	}

	public void removeTranEvaReportInfo(TranEvaReportInfo tranEvaReportInfo) {
		ReportInfoDao.removeTranEvaReportInfo(tranEvaReportInfo);
	}

	public List getReportInfoByCondition(String conditions) {
		return ReportInfoDao.getReportInfoByCondition(conditions);
	}
}
