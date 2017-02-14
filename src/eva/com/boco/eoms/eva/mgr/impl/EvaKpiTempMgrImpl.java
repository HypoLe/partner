package com.boco.eoms.eva.mgr.impl;

import java.util.List;

import com.boco.eoms.eva.dao.IEvaKpiTempDao;
import com.boco.eoms.eva.mgr.IEvaKpiTempMgr;
import com.boco.eoms.eva.model.EvaKpiTemp;

public class EvaKpiTempMgrImpl implements IEvaKpiTempMgr {

	private IEvaKpiTempDao evaKpiTempDao;

	public IEvaKpiTempDao getEvaKpiTempDao() {
		return evaKpiTempDao;
	}

	public void setEvaKpiTempDao(IEvaKpiTempDao evaKpiTempDao) {
		this.evaKpiTempDao = evaKpiTempDao;
	}

	public void saveKpiTemp(EvaKpiTemp kpi){
		evaKpiTempDao.saveKpiTemp(kpi);
	}
	
	public void removeKpiTemp(EvaKpiTemp kpi){
		evaKpiTempDao.removeKpiTemp(kpi);
	}	
	public List getEvaKpiTemps(final String evaTemplateId){
		return evaKpiTempDao.getEvaKpiTemps(evaTemplateId);
	}	
}
