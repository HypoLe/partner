package com.boco.activiti.partner.process.service.impl;

import com.boco.activiti.partner.process.dao.IOperationReturnResultDetailDao;
import com.boco.activiti.partner.process.model.OperationReturnResultDetail;
import com.boco.activiti.partner.process.service.IOperationReturnResultDetailService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;

/**
 
 */
public class OperationReturnResultDetailServiceImpl extends CommonGenericServiceImpl<OperationReturnResultDetail> implements IOperationReturnResultDetailService {
    
	 private IOperationReturnResultDetailDao operationReturnResultDetailDao;

	public IOperationReturnResultDetailDao getOperationReturnResultDetailDao() {
		return operationReturnResultDetailDao;
	}

	public void setOperationReturnResultDetailDao(
			IOperationReturnResultDetailDao operationReturnResultDetailDao) {
		this.operationReturnResultDetailDao = operationReturnResultDetailDao;
		this.setCommonGenericDao(operationReturnResultDetailDao);
	}

	 
}
