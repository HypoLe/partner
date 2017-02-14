package com.boco.eoms.partner.eva.mgr.impl;

import java.util.List;

import com.boco.eoms.partner.eva.dao.IPnrEvaTaskDetailDao;
import com.boco.eoms.partner.eva.mgr.IPnrEvaTaskDetailMgr;
import com.boco.eoms.partner.eva.model.PnrEvaTaskDetail;

public class PnrEvaTaskDetailMgrImpl implements IPnrEvaTaskDetailMgr {

	private IPnrEvaTaskDetailDao pnrEvaTaskDetailDao;

	public IPnrEvaTaskDetailDao getPnrEvaTaskDetailDao() {
		return pnrEvaTaskDetailDao;
	}

	public void setPnrEvaTaskDetailDao(IPnrEvaTaskDetailDao pnrEvaTaskDetailDao) {
		this.pnrEvaTaskDetailDao = pnrEvaTaskDetailDao;
	}

	public int getMaxListNoOfTask(String taskId) {
		return pnrEvaTaskDetailDao.getMaxListNoOfTask(taskId);
	}

	public List listDetailOfTaskByListNo(String taskId, String listNo) {
		return pnrEvaTaskDetailDao.listDetailOfTaskByListNo(taskId, listNo);
	}

	public void saveTask(PnrEvaTaskDetail taskDetail) {
		pnrEvaTaskDetailDao.saveTask(taskDetail);
	}
	
	public PnrEvaTaskDetail getTaskDetailById(String id){
		return pnrEvaTaskDetailDao.getTaskDetailById(id);
	}

	public List listDetailOfTaskId(String taskId) {
		return pnrEvaTaskDetailDao.listDetailOfTaskId(taskId);
	}
	
}
