package com.boco.eoms.partner.chanEva.mgr.impl;

import java.util.List;

import com.boco.eoms.partner.chanEva.dao.IChanEvaTaskDetailDao;
import com.boco.eoms.partner.chanEva.mgr.IChanEvaTaskDetailMgr;
import com.boco.eoms.partner.chanEva.model.ChanEvaTaskDetail;

public class ChanEvaTaskDetailMgrImpl implements IChanEvaTaskDetailMgr {

	private IChanEvaTaskDetailDao chanEvaTaskDetailDao;

	public IChanEvaTaskDetailDao getChanEvaTaskDetailDao() {
		return chanEvaTaskDetailDao;
	}

	public void setChanEvaTaskDetailDao(IChanEvaTaskDetailDao chanEvaTaskDetailDao) {
		this.chanEvaTaskDetailDao = chanEvaTaskDetailDao;
	}

	public int getMaxListNoOfTask(String taskId) {
		return chanEvaTaskDetailDao.getMaxListNoOfTask(taskId);
	}

	public List listDetailOfTaskByListNo(String taskId, String listNo) {
		return chanEvaTaskDetailDao.listDetailOfTaskByListNo(taskId, listNo);
	}

	public void saveTask(ChanEvaTaskDetail taskDetail) {
		chanEvaTaskDetailDao.saveTask(taskDetail);
	}
	
	public ChanEvaTaskDetail getTaskDetailById(String id){
		return chanEvaTaskDetailDao.getTaskDetailById(id);
	}
}
