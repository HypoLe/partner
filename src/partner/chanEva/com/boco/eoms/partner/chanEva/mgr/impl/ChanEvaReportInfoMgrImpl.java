package com.boco.eoms.partner.chanEva.mgr.impl;

import java.util.List;

import com.boco.eoms.base.api.EOMSMgr;
import com.boco.eoms.base.util.StaticVariable;
import com.boco.eoms.commons.system.dept.dao.TawSystemDeptDao;
import com.boco.eoms.commons.system.role.util.RoleConstants;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.model.TawSystemUserRefRole;
import com.boco.eoms.partner.chanEva.dao.IChanEvaReportInfoDao;
import com.boco.eoms.partner.chanEva.mgr.IChanEvaReportInfoMgr;
import com.boco.eoms.partner.chanEva.model.ChanEvaReportInfo;

public class ChanEvaReportInfoMgrImpl implements IChanEvaReportInfoMgr {

	private IChanEvaReportInfoDao ReportInfoDao;
 
	public IChanEvaReportInfoDao getChanEvaReportInfoDao() {
		return ReportInfoDao;
	}

	public void setChanEvaReportInfoDao(IChanEvaReportInfoDao ReportInfoDao) {
		this.ReportInfoDao = ReportInfoDao;
	}

	public void saveChanEvaReportInfo(ChanEvaReportInfo chanEvaReportInfo) {
		ReportInfoDao.saveChanEvaReportInfo(chanEvaReportInfo);
	}

	public ChanEvaReportInfo getChanEvaReportInfo(String id) {
		return ReportInfoDao.getChanEvaReportInfo(id);
	}

	public void removeChanEvaReportInfo(ChanEvaReportInfo chanEvaReportInfo) {
		ReportInfoDao.removeChanEvaReportInfo(chanEvaReportInfo);
	}

	public List getReportInfoByCondition(String conditions) {
		return ReportInfoDao.getReportInfoByCondition(conditions);
	}
}
