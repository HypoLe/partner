package com.boco.eoms.partner.assiEva.mgr.impl;

import java.util.List;

import com.boco.eoms.partner.assiEva.dao.IAssiEvaTaskDetailDao;
import com.boco.eoms.partner.assiEva.mgr.IAssiEvaTaskDetailMgr;
import com.boco.eoms.partner.assiEva.model.AssiEvaTaskDetail;

public class AssiEvaTaskDetailMgrImpl implements IAssiEvaTaskDetailMgr {

	private IAssiEvaTaskDetailDao assiEvaTaskDetailDao;

	public IAssiEvaTaskDetailDao getAssiEvaTaskDetailDao() {
		return assiEvaTaskDetailDao;
	}

	public void setAssiEvaTaskDetailDao(IAssiEvaTaskDetailDao assiEvaTaskDetailDao) {
		this.assiEvaTaskDetailDao = assiEvaTaskDetailDao;
	}

	public int getMaxListNoOfTask(String taskId) {
		return assiEvaTaskDetailDao.getMaxListNoOfTask(taskId);
	}

	public List listDetailOfTaskByListNo(String taskId, String listNo) {
		return assiEvaTaskDetailDao.listDetailOfTaskByListNo(taskId, listNo);
	}

	public void saveTask(AssiEvaTaskDetail taskDetail) {
		assiEvaTaskDetailDao.saveTask(taskDetail);
	}
	
	public AssiEvaTaskDetail getTaskDetailById(String id){
		return assiEvaTaskDetailDao.getTaskDetailById(id);
	}
}
