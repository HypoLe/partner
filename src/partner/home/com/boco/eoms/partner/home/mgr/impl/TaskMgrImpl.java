package com.boco.eoms.partner.home.mgr.impl;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.home.dao.TaskDao;
import com.boco.eoms.partner.home.mgr.TaskMgr;
import com.boco.eoms.partner.home.model.PublishTask;
/**
 * <p>
 * Title:Task信息
 * </p>
 * <p>
 * Description:Task信息
 * </p>
 * <p>
 * Aug 24, 2012 10:53:19 AM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public class TaskMgrImpl extends CommonGenericServiceImpl<PublishTask> implements TaskMgr {

	private TaskDao taskDao;
	public TaskDao getTaskDao() {
		return taskDao;
	}
	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
		this.setCommonGenericDao(taskDao);
	}
}

