package com.boco.eoms.partner.eva.mgr.impl;

import com.boco.eoms.partner.eva.dao.IPnrEvaWeightDao;
import com.boco.eoms.partner.eva.mgr.IPnrEvaWeightMgr;
import com.boco.eoms.partner.eva.model.PnrEvaWeight;

public class PnrEvaWeightMgrImpl implements IPnrEvaWeightMgr {

	private IPnrEvaWeightDao pnrEvaWeightDao;

	public IPnrEvaWeightDao getPnrEvaWeightDao() {
		return pnrEvaWeightDao;
	}

	public void setPnrEvaWeightDao(IPnrEvaWeightDao pnrEvaWeightDao) {
		this.pnrEvaWeightDao = pnrEvaWeightDao;
	}

	public PnrEvaWeight getWeight(String area, String nodeId) {
		// TODO Auto-generated method stub
		return pnrEvaWeightDao.getWeight(area, nodeId);
	}

	public void removeWeight(PnrEvaWeight weight) {
		pnrEvaWeightDao.removeWeight(weight);
		
	}

	public void saveWeight(PnrEvaWeight weight) {
		// TODO Auto-generated method stub
		pnrEvaWeightDao.saveWeight(weight);
	}

	public void updateWeight(PnrEvaWeight weight) {
		pnrEvaWeightDao.saveWeight(weight);
		
	}

	

}
