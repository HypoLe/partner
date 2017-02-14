package com.boco.eoms.partner.lineEva.mgr.impl;

import com.boco.eoms.partner.lineEva.dao.ILineEvaEntityRelDao;
import com.boco.eoms.partner.lineEva.model.LineEvaEntityRel;

public class LineEvaEntityRelMgrImpl {

	private ILineEvaEntityRelDao entityRelDao;

	public ILineEvaEntityRelDao getLineEvaEntityRelDao() {
		return entityRelDao;
	}

	public void setLineEvaEntityRelDao(ILineEvaEntityRelDao entityRelDao) {
		this.entityRelDao = entityRelDao;
	}

	public LineEvaEntityRel getLineEvaEntityRel(String id){
		return entityRelDao.getLineEvaEntityRel(id);
	}
	
	public void saveLineEvaEntityRel(LineEvaEntityRel lineEvaEntityRel){
		entityRelDao.saveLineEvaEntityRel(lineEvaEntityRel);
	}

	public LineEvaEntityRel getLineEvaEntityRelByTemplateId(String templateId){
		return entityRelDao.getLineEvaEntityRelByTemplateId(templateId);
	}
}
