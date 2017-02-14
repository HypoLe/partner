package com.boco.eoms.partner.siteEva.mgr.impl;

import java.util.List;

import com.boco.eoms.partner.siteEva.dao.ISiteEvaTaskDetailDao;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaTaskDetailMgr;
import com.boco.eoms.partner.siteEva.model.SiteEvaTaskDetail;

public class SiteEvaTaskDetailMgrImpl implements ISiteEvaTaskDetailMgr {

	private ISiteEvaTaskDetailDao siteEvaTaskDetailDao;

	public ISiteEvaTaskDetailDao getSiteEvaTaskDetailDao() {
		return siteEvaTaskDetailDao;
	}

	public void setSiteEvaTaskDetailDao(ISiteEvaTaskDetailDao siteEvaTaskDetailDao) {
		this.siteEvaTaskDetailDao = siteEvaTaskDetailDao;
	} 

	public int getMaxListNoOfTask(String taskId) {
		return siteEvaTaskDetailDao.getMaxListNoOfTask(taskId);
	}

	public List listDetailOfTaskByListNo(String taskId, String listNo) {
		return siteEvaTaskDetailDao.listDetailOfTaskByListNo(taskId, listNo);
	}

	public void saveTask(SiteEvaTaskDetail taskDetail) {
		siteEvaTaskDetailDao.saveTask(taskDetail);
	}
	
	public SiteEvaTaskDetail getTaskDetailById(String id){
		return siteEvaTaskDetailDao.getTaskDetailById(id);
	}
}
