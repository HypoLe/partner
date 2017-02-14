package com.boco.eoms.assEva.mgr.impl;

import java.util.List;

import com.boco.eoms.assEva.dao.IAssEvaTaskDetailDao;
import com.boco.eoms.assEva.mgr.IAssEvaTaskDetailMgr;
import com.boco.eoms.assEva.model.AssEvaTaskDetail;

public class AssEvaTaskDetailMgrImpl implements IAssEvaTaskDetailMgr {

	private IAssEvaTaskDetailDao assEvaTaskDetailDao;

	public IAssEvaTaskDetailDao getAssEvaTaskDetailDao() {
		return assEvaTaskDetailDao;
	}

	public void setAssEvaTaskDetailDao(IAssEvaTaskDetailDao assEvaTaskDetailDao) {
		this.assEvaTaskDetailDao = assEvaTaskDetailDao;
	}

	public int getMaxListNoOfTask(String taskId) {
		return assEvaTaskDetailDao.getMaxListNoOfTask(taskId);
	}

	public List listDetailOfTaskByListNo(String taskId, String listNo) {
		return assEvaTaskDetailDao.listDetailOfTaskByListNo(taskId, listNo);
	}

	public void saveTask(AssEvaTaskDetail taskDetail) {
		assEvaTaskDetailDao.saveTask(taskDetail);
	}
	
	public AssEvaTaskDetail getTaskDetailById(String id){
		return assEvaTaskDetailDao.getTaskDetailById(id);
	}
}
