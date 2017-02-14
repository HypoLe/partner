package com.boco.eoms.partner.assess.AssWeight.mgr.impl;

import com.boco.eoms.partner.assess.AssWeight.dao.IAssWeightDao;
import com.boco.eoms.partner.assess.AssWeight.mgr.IAssWeightMgr;
import com.boco.eoms.partner.assess.AssWeight.model.AssWeight;

public class AssWeightMgrImpl implements IAssWeightMgr {

	private IAssWeightDao assWeightDao;

	public IAssWeightDao getAssWeightDao() {
		return assWeightDao;
	}

	public void setAssWeightDao(IAssWeightDao assWeightDao) {
		this.assWeightDao = assWeightDao;
	}

	public AssWeight getWeight(String area, String nodeId) {
		// TODO Auto-generated method stub
		return assWeightDao.getWeight(area, nodeId);
	}

	public void removeWeight(AssWeight weight) {
		assWeightDao.removeWeight(weight);
		
	}

	public void saveWeight(AssWeight weight) {
		// TODO Auto-generated method stub
		assWeightDao.saveWeight(weight);
	}

	public void updateWeight(AssWeight weight) {
		assWeightDao.saveWeight(weight);
		
	}

	

}
