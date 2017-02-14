package com.boco.eoms.partner.chanEva.mgr.impl;

import java.util.Map;


import com.boco.eoms.partner.chanEva.dao.IChanEvaAuditInfoDao;
import com.boco.eoms.partner.chanEva.model.ChanEvaAuditInfo;
import com.boco.eoms.partner.chanEva.mgr.IChanEvaAuditInfoMgr;

public class ChanEvaAuditInfoMgrImpl implements IChanEvaAuditInfoMgr{

	private IChanEvaAuditInfoDao auditInfoDao;
	
	public IChanEvaAuditInfoDao getChanEvaAuditInfoDao(){
		return auditInfoDao;
	}

	public void setChanEvaAuditInfoDao(IChanEvaAuditInfoDao auditInfoDao) {
		this.auditInfoDao = auditInfoDao;
	}

	public ChanEvaAuditInfo getChanEvaAuditInfo(String id) {
		return auditInfoDao.getChanEvaAuditInfo(id);
	}
	
	public void saveChanEvaAuditInfo(ChanEvaAuditInfo chanEvaAuditInfo) {
		auditInfoDao.saveChanEvaAuditInfo(chanEvaAuditInfo);
	}

	public Map getAuditInfoByTempateId(final Integer curPage,
			final Integer pageSize, final String whereStr){
		return auditInfoDao.getAuditInfoByTempateId(curPage,pageSize,whereStr);
	}
}
