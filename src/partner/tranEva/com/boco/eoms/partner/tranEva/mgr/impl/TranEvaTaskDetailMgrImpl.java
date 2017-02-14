package com.boco.eoms.partner.tranEva.mgr.impl;

import java.util.List;

import com.boco.eoms.partner.tranEva.dao.ITranEvaTaskDetailDao;
import com.boco.eoms.partner.tranEva.mgr.ITranEvaTaskDetailMgr;
import com.boco.eoms.partner.tranEva.model.TranEvaTaskDetail;

public class TranEvaTaskDetailMgrImpl implements ITranEvaTaskDetailMgr {

	private ITranEvaTaskDetailDao tranEvaTaskDetailDao;

	public ITranEvaTaskDetailDao getTranEvaTaskDetailDao() {
		return tranEvaTaskDetailDao;
	}

	public void setTranEvaTaskDetailDao(ITranEvaTaskDetailDao tranEvaTaskDetailDao) {
		this.tranEvaTaskDetailDao = tranEvaTaskDetailDao;
	}

	public int getMaxListNoOfTask(String taskId) {
		return tranEvaTaskDetailDao.getMaxListNoOfTask(taskId);
	}

	public List listDetailOfTaskByListNo(String taskId, String listNo) {
		return tranEvaTaskDetailDao.listDetailOfTaskByListNo(taskId, listNo);
	}

	public void saveTask(TranEvaTaskDetail taskDetail) {
		tranEvaTaskDetailDao.saveTask(taskDetail);
	}
	
	public TranEvaTaskDetail getTaskDetailById(String id){
		return tranEvaTaskDetailDao.getTaskDetailById(id);
	}
}
