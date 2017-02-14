package com.boco.eoms.partner.siteEva.mgr;

import java.util.Map;

import com.boco.eoms.partner.siteEva.dao.ISiteEvaAuditInfoDao;
import com.boco.eoms.partner.siteEva.model.SiteEvaAuditInfo;

public interface ISiteEvaAuditInfoMgr {

	public ISiteEvaAuditInfoDao getSiteEvaAuditInfoDao();
	
	public void setSiteEvaAuditInfoDao(ISiteEvaAuditInfoDao auditInfoDao);
	
	public SiteEvaAuditInfo getSiteEvaAuditInfo(String id);
	
	public void saveSiteEvaAuditInfo(SiteEvaAuditInfo siteEvaAuditInfo);
	
	public Map getAuditInfoByTempateId(final Integer curPage,
			final Integer pageSize, final String whereStr);
}
