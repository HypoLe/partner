package com.boco.eoms.partner.siteEva.dao;
import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.partner.siteEva.model.SiteEvaEntityRel;

public interface ISiteEvaEntityRelDao extends Dao {
	
	public SiteEvaEntityRel getSiteEvaEntityRel(String id);
	
	public void saveSiteEvaEntityRel(SiteEvaEntityRel siteEvaEntityRel);

	public SiteEvaEntityRel getSiteEvaEntityRelByTemplateId(String templateId);
}
  