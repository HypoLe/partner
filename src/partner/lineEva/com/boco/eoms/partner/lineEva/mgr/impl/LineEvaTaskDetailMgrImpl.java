package com.boco.eoms.partner.lineEva.mgr.impl;

import java.util.List;

import com.boco.eoms.partner.lineEva.dao.ILineEvaTaskDetailDao;
import com.boco.eoms.partner.lineEva.mgr.ILineEvaTaskDetailMgr;
import com.boco.eoms.partner.lineEva.model.LineEvaTaskDetail;

public class LineEvaTaskDetailMgrImpl implements ILineEvaTaskDetailMgr {

	private ILineEvaTaskDetailDao lineEvaTaskDetailDao;

	public ILineEvaTaskDetailDao getLineEvaTaskDetailDao() {
		return lineEvaTaskDetailDao;
	}

	public void setLineEvaTaskDetailDao(ILineEvaTaskDetailDao lineEvaTaskDetailDao) {
		this.lineEvaTaskDetailDao = lineEvaTaskDetailDao;
	}

	public int getMaxListNoOfTask(String taskId) {
		return lineEvaTaskDetailDao.getMaxListNoOfTask(taskId);
	}

	public List listDetailOfTaskByListNo(String taskId, String listNo) {
		return lineEvaTaskDetailDao.listDetailOfTaskByListNo(taskId, listNo);
	}

	public void saveTask(LineEvaTaskDetail taskDetail) {
		lineEvaTaskDetailDao.saveTask(taskDetail);
	}
	
	public LineEvaTaskDetail getTaskDetailById(String id){
		return lineEvaTaskDetailDao.getTaskDetailById(id);
	}
}
