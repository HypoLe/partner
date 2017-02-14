package com.boco.eoms.partner.eva.mgr.impl;

import java.util.List;
import java.util.Map;


import com.boco.eoms.partner.eva.dao.IPnrEvaAuditInfoDao;
import com.boco.eoms.partner.eva.model.PnrEvaAuditInfo;
import com.boco.eoms.partner.eva.mgr.IPnrEvaAuditInfoMgr;

public class PnrEvaAuditInfoMgrImpl implements IPnrEvaAuditInfoMgr{

	private IPnrEvaAuditInfoDao pnrEvaAuditInfoDao;
	
	public IPnrEvaAuditInfoDao getPnrEvaAuditInfoDao(){
		return pnrEvaAuditInfoDao;
	}

	public void setPnrEvaAuditInfoDao(IPnrEvaAuditInfoDao pnrEvaAuditInfoDao) {
		this.pnrEvaAuditInfoDao = pnrEvaAuditInfoDao;
	}

	public PnrEvaAuditInfo getPnrEvaAuditInfo(String id) {
		return pnrEvaAuditInfoDao.getPnrEvaAuditInfo(id);
	}
	
	public void savePnrEvaAuditInfo(PnrEvaAuditInfo evaAuditInfo) {
		pnrEvaAuditInfoDao.savePnrEvaAuditInfo(evaAuditInfo);
	}

	public Map getAuditInfoByCondition(final Integer curPage,
			final Integer pageSize, final String whereStr){
		return pnrEvaAuditInfoDao.getAuditInfoByCondition(curPage,pageSize,whereStr);
	}

	public List getPnrEvaAudit(final String templateId) {
		return pnrEvaAuditInfoDao.getPnrEvaAudit(templateId);
	}
}
