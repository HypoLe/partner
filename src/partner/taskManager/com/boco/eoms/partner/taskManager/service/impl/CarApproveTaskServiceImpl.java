package com.boco.eoms.partner.taskManager.service.impl;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.taskManager.dao.ICarApproveTaskDao;
import com.boco.eoms.partner.taskManager.model.CarApproveTask;
import com.boco.eoms.partner.taskManager.service.ICarApproveTaskService;

public class CarApproveTaskServiceImpl extends CommonGenericServiceImpl<CarApproveTask> implements ICarApproveTaskService {

	private ICarApproveTaskDao carApproveTaskDao;

	public ICarApproveTaskDao getCarApproveTaskDao() {
		return carApproveTaskDao;
	}

	public void setCarApproveTaskDao(ICarApproveTaskDao carApproveTaskDao) {
		this.setCommonGenericDao(carApproveTaskDao);
		this.carApproveTaskDao = carApproveTaskDao;
	}
	
	
}
