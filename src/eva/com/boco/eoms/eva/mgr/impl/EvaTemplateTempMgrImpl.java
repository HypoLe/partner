package com.boco.eoms.eva.mgr.impl;

import com.boco.eoms.eva.dao.IEvaTemplateTempDao;
import com.boco.eoms.eva.mgr.IEvaTemplateTempMgr;
import com.boco.eoms.eva.model.EvaTemplateTemp;

public class EvaTemplateTempMgrImpl implements IEvaTemplateTempMgr {

	private IEvaTemplateTempDao evaTemplateTempDao;

	public IEvaTemplateTempDao getEvaTemplateTempDao() {
		return evaTemplateTempDao;
	}

	public void setEvaTemplateTempDao(IEvaTemplateTempDao evaTemplateTempDao) {
		this.evaTemplateTempDao = evaTemplateTempDao;
	}

	public void saveEvaTemplateTemp(EvaTemplateTemp template){
		evaTemplateTempDao.saveEvaTemplateTemp(template);
	}

	public void removeEvaTemplateTemp(EvaTemplateTemp template){
		evaTemplateTempDao.removeEvaTemplateTemp(template);
	}

	public EvaTemplateTemp getEvaTemplateTemp(final String id){
		return evaTemplateTempDao.getEvaTemplateTemp(id);
	}
}
