package com.boco.activiti.partner.process.service.impl;


import com.boco.activiti.partner.process.dao.IMasteDataTaskDao;
import com.boco.activiti.partner.process.dao.IMasteRelTaskItemDao;
import com.boco.activiti.partner.process.model.MasteDataTask;
import com.boco.activiti.partner.process.service.IMasteDataTaskService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;

/**
 
 */
public class MasteDataTaskServiceImpl extends CommonGenericServiceImpl<MasteDataTask> implements IMasteDataTaskService {

    private IMasteDataTaskDao masteDataTaskDao;

	public IMasteDataTaskDao getMasteDataTaskDao() {
		return masteDataTaskDao;
	}

	public void setMasteDataTaskDao(IMasteDataTaskDao masteDataTaskDao) {
		this.masteDataTaskDao = masteDataTaskDao;
		this.setCommonGenericDao(masteDataTaskDao);
	}

	
	
}
