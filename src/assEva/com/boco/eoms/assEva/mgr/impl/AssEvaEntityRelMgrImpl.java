package com.boco.eoms.assEva.mgr.impl;

import com.boco.eoms.assEva.dao.IAssEvaEntityRelDao;
import com.boco.eoms.assEva.model.AssEvaEntityRel;

public class AssEvaEntityRelMgrImpl {

	private IAssEvaEntityRelDao entityRelDao;

	public IAssEvaEntityRelDao getAssEvaEntityRelDao() {
		return entityRelDao;
	}

	public void setAssEvaEntityRelDao(IAssEvaEntityRelDao entityRelDao) {
		this.entityRelDao = entityRelDao;
	}

	public AssEvaEntityRel getAssEvaEntityRel(String id){
		return entityRelDao.getAssEvaEntityRel(id);
	}
	
	public void saveAssEvaEntityRel(AssEvaEntityRel assEvaEntityRel){
		entityRelDao.saveAssEvaEntityRel(assEvaEntityRel);
	}

	public AssEvaEntityRel getAssEvaEntityRelByTemplateId(String templateId){
		return entityRelDao.getAssEvaEntityRelByTemplateId(templateId);
	}
}
