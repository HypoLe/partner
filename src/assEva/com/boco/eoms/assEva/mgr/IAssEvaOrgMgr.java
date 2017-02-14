package com.boco.eoms.assEva.mgr;

import java.util.List;

import com.boco.eoms.assEva.dao.IAssEvaOrgDao;
import com.boco.eoms.assEva.model.AssEvaOrg;

public interface IAssEvaOrgMgr {

	public IAssEvaOrgDao getAssEvaOrgDao();

	public void setAssEvaOrgDao(IAssEvaOrgDao orgDao);

	public void saveAssEvaOrg(AssEvaOrg assEvaOrg);

	public AssEvaOrg getAssEvaOrg(String id);

	public void removeAssEvaOrg(AssEvaOrg assEvaOrg);

	public List getOrgsByTempletId(String templateId);

	public void removeOrgOfTemplate(String templateId);

	public List getTempletByUserId(String userId, String actionType,
			String status);
	
	public List getTaskByConditions(String conditions);
	
	public AssEvaOrg getLatestTaskByTaskId(String taskId);

}
