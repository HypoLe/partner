package com.boco.eoms.partner.chanEva.mgr;

import java.util.List;

import com.boco.eoms.partner.chanEva.dao.IChanEvaOrgDao;
import com.boco.eoms.partner.chanEva.model.ChanEvaOrg;

public interface IChanEvaOrgMgr {

	public IChanEvaOrgDao getChanEvaOrgDao();

	public void setChanEvaOrgDao(IChanEvaOrgDao orgDao);

	public void saveChanEvaOrg(ChanEvaOrg chanEvaOrg);

	public ChanEvaOrg getChanEvaOrg(String id);

	public void removeChanEvaOrg(ChanEvaOrg chanEvaOrg);

	public List getOrgsByTempletId(String templateId);

	public void removeOrgOfTemplate(String templateId);

	public List getTempletByUserId(String userId, String actionType,
			String status);
	
	public List getTaskByConditions(String conditions);
	
	public ChanEvaOrg getLatestTaskByTaskId(String taskId);

}
