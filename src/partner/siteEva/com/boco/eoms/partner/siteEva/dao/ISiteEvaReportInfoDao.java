package com.boco.eoms.partner.siteEva.dao;

import java.util.List;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.siteEva.model.SiteEvaReportInfo;

public interface ISiteEvaReportInfoDao extends Dao{

	public SiteEvaReportInfo getSiteEvaReportInfo(String id);
	
	public void saveSiteEvaReportInfo(SiteEvaReportInfo siteEvaReportInfo);
	
	public void removeSiteEvaReportInfo(SiteEvaReportInfo siteEvaReportInfo);
	// 根据条件获取模板列表
	public List getReportInfoByCondition(String where); 
}
