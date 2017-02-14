package com.boco.eoms.partner.tranEva.mgr;

import java.util.List;

import com.boco.eoms.partner.tranEva.dao.ITranEvaOrgDao;
import com.boco.eoms.partner.tranEva.model.TranEvaOrg;

public interface ITranEvaOrgMgr {

	public ITranEvaOrgDao getTranEvaOrgDao();

	public void setTranEvaOrgDao(ITranEvaOrgDao orgDao);

	public void saveTranEvaOrg(TranEvaOrg tranEvaOrg);

	public TranEvaOrg getTranEvaOrg(String id);

	public void removeTranEvaOrg(TranEvaOrg tranEvaOrg);

	public List getOrgsByTempletId(String templateId);

	public void removeOrgOfTemplate(String templateId);

	public List getTempletByUserId(String userId, String actionType,
			String status);
	
	public List getTaskByConditions(String conditions);
	
	public TranEvaOrg getLatestTaskByTaskId(String taskId);

}
