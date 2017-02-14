package com.boco.eoms.partner.siteEva.mgr.impl;

import java.util.List;

import com.boco.eoms.base.api.EOMSMgr;
import com.boco.eoms.base.util.StaticVariable;
import com.boco.eoms.commons.system.dept.dao.TawSystemDeptDao;
import com.boco.eoms.commons.system.role.util.RoleConstants;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.model.TawSystemUserRefRole;
import com.boco.eoms.partner.siteEva.dao.ISiteEvaReportInfoDao;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaReportInfoMgr;
import com.boco.eoms.partner.siteEva.model.SiteEvaReportInfo;

public class SiteEvaReportInfoMgrImpl implements ISiteEvaReportInfoMgr {

	private ISiteEvaReportInfoDao ReportInfoDao;
 
	public ISiteEvaReportInfoDao getSiteEvaReportInfoDao() {
		return ReportInfoDao;
	}

	public void setSiteEvaReportInfoDao(ISiteEvaReportInfoDao ReportInfoDao) {
		this.ReportInfoDao = ReportInfoDao;
	}
 
	public void saveSiteEvaReportInfo(SiteEvaReportInfo siteEvaReportInfo) {
		ReportInfoDao.saveSiteEvaReportInfo(siteEvaReportInfo);
	}

	public SiteEvaReportInfo getSiteEvaReportInfo(String id) {
		return ReportInfoDao.getSiteEvaReportInfo(id);
	}

	public void removeSiteEvaReportInfo(SiteEvaReportInfo siteEvaReportInfo) {
		ReportInfoDao.removeSiteEvaReportInfo(siteEvaReportInfo);
	}

	public List getReportInfoByCondition(String conditions) {
		return ReportInfoDao.getReportInfoByCondition(conditions);
	}
}
