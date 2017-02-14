package com.boco.eoms.partner.chanEva.mgr.impl;

import com.boco.eoms.partner.chanEva.dao.IChanEvaEntityRelDao;
import com.boco.eoms.partner.chanEva.model.ChanEvaEntityRel;

public class ChanEvaEntityRelMgrImpl {

	private IChanEvaEntityRelDao entityRelDao;

	public IChanEvaEntityRelDao getChanEvaEntityRelDao() {
		return entityRelDao;
	}

	public void setChanEvaEntityRelDao(IChanEvaEntityRelDao entityRelDao) {
		this.entityRelDao = entityRelDao;
	}

	public ChanEvaEntityRel getChanEvaEntityRel(String id){
		return entityRelDao.getChanEvaEntityRel(id);
	}
	
	public void saveChanEvaEntityRel(ChanEvaEntityRel chanEvaEntityRel){
		entityRelDao.saveChanEvaEntityRel(chanEvaEntityRel);
	}

	public ChanEvaEntityRel getChanEvaEntityRelByTemplateId(String templateId){
		return entityRelDao.getChanEvaEntityRelByTemplateId(templateId);
	}
}
