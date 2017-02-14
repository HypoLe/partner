package com.boco.eoms.partner.siteEva.mgr.impl;

import com.boco.eoms.partner.siteEva.dao.ISiteEvaEntityRelDao;
import com.boco.eoms.partner.siteEva.model.SiteEvaEntityRel;

public class SiteEvaEntityRelMgrImpl {

	private ISiteEvaEntityRelDao entityRelDao;

	public ISiteEvaEntityRelDao getSiteEvaEntityRelDao() {
		return entityRelDao;
	} 

	public void setSiteEvaEntityRelDao(ISiteEvaEntityRelDao entityRelDao) {
		this.entityRelDao = entityRelDao;
	}

	public SiteEvaEntityRel getSiteEvaEntityRel(String id){
		return entityRelDao.getSiteEvaEntityRel(id);
	}
	
	public void saveSiteEvaEntityRel(SiteEvaEntityRel siteEvaEntityRel){
		entityRelDao.saveSiteEvaEntityRel(siteEvaEntityRel);
	}

	public SiteEvaEntityRel getSiteEvaEntityRelByTemplateId(String templateId){
		return entityRelDao.getSiteEvaEntityRelByTemplateId(templateId);
	}
}
