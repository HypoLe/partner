package com.boco.eoms.partner.lineEva.mgr;

import java.util.List;

import com.boco.eoms.partner.lineEva.dao.ILineEvaOrgDao;
import com.boco.eoms.partner.lineEva.model.LineEvaOrg;

public interface ILineEvaOrgMgr {

	public ILineEvaOrgDao getLineEvaOrgDao();

	public void setLineEvaOrgDao(ILineEvaOrgDao orgDao);

	public void saveLineEvaOrg(LineEvaOrg lineEvaOrg);

	public LineEvaOrg getLineEvaOrg(String id);

	public void removeLineEvaOrg(LineEvaOrg lineEvaOrg);

	public List getOrgsByTempletId(String templateId);

	public void removeOrgOfTemplate(String templateId);

	public List getTempletByUserId(String userId, String actionType,
			String status);
	
	public List getTaskByConditions(String conditions);
	
	public LineEvaOrg getLatestTaskByTaskId(String taskId);

}
