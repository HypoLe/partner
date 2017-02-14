package com.boco.activiti.partner.process.service.impl;


import com.boco.activiti.partner.process.dao.IMasteSelCopyTaskDao;
import com.boco.activiti.partner.process.model.MasteSelCopyTask;
import com.boco.activiti.partner.process.service.IMasteSelCopyTaskService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;

/**
 
 */
public class MasteSelCopyTaskServiceImpl extends CommonGenericServiceImpl<MasteSelCopyTask> implements IMasteSelCopyTaskService {

    private IMasteSelCopyTaskDao masteSelCopyTaskDao;

	public IMasteSelCopyTaskDao getMasteSelCopyTaskDao() {
		return masteSelCopyTaskDao;
	}

	public void setMasteSelCopyTaskDao(IMasteSelCopyTaskDao masteSelCopyTaskDao) {
		this.masteSelCopyTaskDao = masteSelCopyTaskDao;
		this.setCommonGenericDao(masteSelCopyTaskDao);
	}
    
}
