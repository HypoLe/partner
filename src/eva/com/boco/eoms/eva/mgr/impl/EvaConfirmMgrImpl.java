package com.boco.eoms.eva.mgr.impl;

import java.util.List;
import java.util.Map;

import com.boco.eoms.eva.dao.IEvaConfirmDao;
import com.boco.eoms.eva.mgr.IEvaConfirmMgr;
import com.boco.eoms.eva.model.EvaConfirm;

public class EvaConfirmMgrImpl implements IEvaConfirmMgr {

	private IEvaConfirmDao evaConfirmDao;

	public IEvaConfirmDao getEvaConfirmDao() {
		return evaConfirmDao;
	}

	public void setEvaConfirmDao(IEvaConfirmDao evaConfirmDao) {
		this.evaConfirmDao = evaConfirmDao;
	}
	
	public void removeEvaConfirm(EvaConfirm evaConfirm){
		evaConfirmDao.removeEvaConfirm(evaConfirm);
	}

	public void saveEvaConfirm(EvaConfirm evaConfirm){
		evaConfirmDao.saveEvaConfirm(evaConfirm);
	}
	
	public Map getTemplateUnConfirms(final Integer curPage,final Integer pageSize,final String userId,final String deptId) {
		return evaConfirmDao.getTemplateUnConfirms(curPage, pageSize, userId, deptId);
	}

	public List getTemplateConfirms(final String evaTemplateId){
		return evaConfirmDao.getTemplateConfirms(evaTemplateId);
	}	
	public EvaConfirm getTemplateConfirmById(final String id) {
		return evaConfirmDao.getTemplateConfirmById(id);
	}
}
