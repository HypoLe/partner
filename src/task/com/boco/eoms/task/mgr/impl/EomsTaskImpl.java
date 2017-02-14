package com.boco.eoms.task.mgr.impl;

import java.util.List;

import com.boco.eoms.base.service.impl.BaseManager;
import com.boco.eoms.task.dao.IEomsTaskDao;
import com.boco.eoms.task.mgr.IEomsTask;

public  class EomsTaskImpl extends BaseManager implements IEomsTask {

	 private IEomsTaskDao eomsTaskDao;

	public IEomsTaskDao getEomsTaskDao() {
		return eomsTaskDao;
	}

	public void setEomsTaskDao(IEomsTaskDao eomsTaskDao) {
		this.eomsTaskDao = eomsTaskDao;
	}

	public void savaEomsTaskUser(List eomstaskuserlist,String userid) {
		
		eomsTaskDao.saveEomsTask(eomstaskuserlist,userid);
		
	}

	public List getEomsTaskUser(String userid) {
		return eomsTaskDao.getEomsTaskUserByUserid(userid);
	}

	public List listTaskDrafter(String principal) {
		// TODO Auto-generated method stub
		return eomsTaskDao.listTaskDrafter(principal);
	}
	 
}
