package com.boco.eoms.partner.siteEva.mgr;

import java.util.List;

import com.boco.eoms.partner.siteEva.dao.ISiteEvaOrgDao;
import com.boco.eoms.partner.siteEva.dao.ISiteEvaReportInfoDao;
import com.boco.eoms.partner.siteEva.model.SiteEvaOrg;
import com.boco.eoms.partner.siteEva.model.SiteEvaReportInfo;

public interface ISiteEvaReportInfoMgr {

	public ISiteEvaReportInfoDao getSiteEvaReportInfoDao();

	public void setSiteEvaReportInfoDao(ISiteEvaReportInfoDao ReportInfoDao);

	public void saveSiteEvaReportInfo(SiteEvaReportInfo siteEvaReportInfo);

	public SiteEvaReportInfo getSiteEvaReportInfo(String id);

	public void removeSiteEvaReportInfo(SiteEvaReportInfo siteEvaReportInfo);
	
	public List getReportInfoByCondition(String conditions);

}
