package com.boco.eoms.partner.assiEva.mgr.impl;

import com.boco.eoms.partner.assiEva.dao.IAssiEvaEntityRelDao;
import com.boco.eoms.partner.assiEva.model.AssiEvaEntityRel;

public class AssiEvaEntityRelMgrImpl {

	private IAssiEvaEntityRelDao entityRelDao;

	public IAssiEvaEntityRelDao getAssiEvaEntityRelDao() {
		return entityRelDao;
	}

	public void setAssiEvaEntityRelDao(IAssiEvaEntityRelDao entityRelDao) {
		this.entityRelDao = entityRelDao;
	}

	public AssiEvaEntityRel getAssiEvaEntityRel(String id){
		return entityRelDao.getAssiEvaEntityRel(id);
	}
	
	public void saveAssiEvaEntityRel(AssiEvaEntityRel assiEvaEntityRel){
		entityRelDao.saveAssiEvaEntityRel(assiEvaEntityRel);
	}

	public AssiEvaEntityRel getAssiEvaEntityRelByTemplateId(String templateId){
		return entityRelDao.getAssiEvaEntityRelByTemplateId(templateId);
	}
}
