package com.boco.eoms.partner.siteEva.mgr;

import java.util.List;

import com.boco.eoms.partner.siteEva.dao.ISiteEvaOrgDao;
import com.boco.eoms.partner.siteEva.model.SiteEvaOrg;

public interface ISiteEvaOrgMgr {

	public ISiteEvaOrgDao getSiteEvaOrgDao();

	public void setSiteEvaOrgDao(ISiteEvaOrgDao orgDao);

	public void saveSiteEvaOrg(SiteEvaOrg siteEvaOrg);

	public SiteEvaOrg getSiteEvaOrg(String id);

	public void removeSiteEvaOrg(SiteEvaOrg siteEvaOrg);

	public List getOrgsByTempletId(String templateId);

	public void removeOrgOfTemplate(String templateId);

	public List getTempletByUserId(String userId, String actionType,
			String status);
	
	public List getTaskByConditions(String conditions);
	
	public SiteEvaOrg getLatestTaskByTaskId(String taskId);

}
