package com.boco.activiti.partner.process.service.impl;

import com.boco.activiti.partner.process.dao.IOperationReturnResultDao;
import com.boco.activiti.partner.process.model.OperationReturnResult;
import com.boco.activiti.partner.process.service.IOperationReturnResultService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;

/**
 
 */
public class OperationReturnResultServiceImpl extends CommonGenericServiceImpl<OperationReturnResult> implements IOperationReturnResultService {
    
	 private IOperationReturnResultDao operationReturnResultDao;

	public IOperationReturnResultDao getOperationReturnResultDao() {
		return operationReturnResultDao;
	}

	public void setOperationReturnResultDao(
			IOperationReturnResultDao operationReturnResultDao) {
		this.operationReturnResultDao = operationReturnResultDao;
		setCommonGenericDao(operationReturnResultDao);
	}

	 
}
