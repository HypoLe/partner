package com.boco.eoms.partner.siteEva.dao;

import java.util.Map;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.siteEva.model.SiteEvaAuditInfo;

public interface ISiteEvaAuditInfoDao extends Dao{

	public SiteEvaAuditInfo getSiteEvaAuditInfo(String id);
	
	public void saveSiteEvaAuditInfo(SiteEvaAuditInfo siteEvaAuditInfo);
	
	public Map getAuditInfoByTempateId(final Integer curPage,
			final Integer pageSize, final String whereStr);
}
 