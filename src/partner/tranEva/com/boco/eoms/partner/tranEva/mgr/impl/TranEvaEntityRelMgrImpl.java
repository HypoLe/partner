package com.boco.eoms.partner.tranEva.mgr.impl;

import com.boco.eoms.partner.tranEva.dao.ITranEvaEntityRelDao;
import com.boco.eoms.partner.tranEva.model.TranEvaEntityRel;

public class TranEvaEntityRelMgrImpl {

	private ITranEvaEntityRelDao entityRelDao;

	public ITranEvaEntityRelDao getTranEvaEntityRelDao() {
		return entityRelDao;
	}

	public void setTranEvaEntityRelDao(ITranEvaEntityRelDao entityRelDao) {
		this.entityRelDao = entityRelDao;
	}

	public TranEvaEntityRel getTranEvaEntityRel(String id){
		return entityRelDao.getTranEvaEntityRel(id);
	}
	
	public void saveTranEvaEntityRel(TranEvaEntityRel tranEvaEntityRel){
		entityRelDao.saveTranEvaEntityRel(tranEvaEntityRel);
	}

	public TranEvaEntityRel getTranEvaEntityRelByTemplateId(String templateId){
		return entityRelDao.getTranEvaEntityRelByTemplateId(templateId);
	}
}
