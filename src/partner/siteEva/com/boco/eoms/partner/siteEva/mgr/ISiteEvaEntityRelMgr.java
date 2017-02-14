package com.boco.eoms.partner.siteEva.mgr;

import com.boco.eoms.partner.siteEva.dao.ISiteEvaEntityRelDao;
import com.boco.eoms.partner.siteEva.model.SiteEvaEntityRel;

public interface ISiteEvaEntityRelMgr {

	public ISiteEvaEntityRelDao getSiteEvaEntityDao();
	
	public void setSiteEvaEntityDao(ISiteEvaEntityRelDao entityDao);
	
	public SiteEvaEntityRel getSiteEvaEntityRel(String id);
	
	public void saveSiteEvaEntityRel(SiteEvaEntityRel siteEvaEntityRel);

	public SiteEvaEntityRel getSiteEvaEntityRelByTemplateId(String templateId);
}
