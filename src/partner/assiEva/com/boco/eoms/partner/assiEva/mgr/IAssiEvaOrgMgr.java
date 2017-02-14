package com.boco.eoms.partner.assiEva.mgr;

import java.util.List;

import com.boco.eoms.partner.assiEva.dao.IAssiEvaOrgDao;
import com.boco.eoms.partner.assiEva.model.AssiEvaOrg;

public interface IAssiEvaOrgMgr {

	public IAssiEvaOrgDao getAssiEvaOrgDao();

	public void setAssiEvaOrgDao(IAssiEvaOrgDao orgDao);

	public void saveAssiEvaOrg(AssiEvaOrg assiEvaOrg);

	public AssiEvaOrg getAssiEvaOrg(String id);

	public void removeAssiEvaOrg(AssiEvaOrg assiEvaOrg);

	public List getOrgsByTempletId(String templateId);

	public void removeOrgOfTemplate(String templateId);

	public List getTempletByUserId(String userId, String actionType,
			String status);
	
	public List getTaskByConditions(String conditions);
	
	public AssiEvaOrg getLatestTaskByTaskId(String taskId);

}
