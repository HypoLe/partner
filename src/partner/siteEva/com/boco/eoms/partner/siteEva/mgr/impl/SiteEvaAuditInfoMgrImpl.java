package com.boco.eoms.partner.siteEva.mgr.impl;

import java.util.Map;


import com.boco.eoms.partner.siteEva.dao.ISiteEvaAuditInfoDao;
import com.boco.eoms.partner.siteEva.model.SiteEvaAuditInfo;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaAuditInfoMgr;

public class SiteEvaAuditInfoMgrImpl implements ISiteEvaAuditInfoMgr{

	private ISiteEvaAuditInfoDao auditInfoDao;
	
	public ISiteEvaAuditInfoDao getSiteEvaAuditInfoDao(){
		return auditInfoDao;
	} 

	public void setSiteEvaAuditInfoDao(ISiteEvaAuditInfoDao auditInfoDao) {
		this.auditInfoDao = auditInfoDao;
	}

	public SiteEvaAuditInfo getSiteEvaAuditInfo(String id) {
		return auditInfoDao.getSiteEvaAuditInfo(id);
	}
	
	public void saveSiteEvaAuditInfo(SiteEvaAuditInfo siteEvaAuditInfo) {
		auditInfoDao.saveSiteEvaAuditInfo(siteEvaAuditInfo);
	}

	public Map getAuditInfoByTempateId(final Integer curPage,
			final Integer pageSize, final String whereStr){
		return auditInfoDao.getAuditInfoByTempateId(curPage,pageSize,whereStr);
	}
}
