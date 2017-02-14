package com.boco.activiti.partner.process.service.impl;

import com.boco.activiti.partner.process.dao.IPnrAccreditOrderDao;
import com.boco.activiti.partner.process.model.AccreditOrder;
import com.boco.activiti.partner.process.service.IPnerAccreditOrderService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;

public class PnrAccreditOrderServiceImpl extends CommonGenericServiceImpl<AccreditOrder> implements IPnerAccreditOrderService{
	
private IPnrAccreditOrderDao pnrAccreditOrderDao;

public IPnrAccreditOrderDao getPnrAccreditOrderDao() {
	return pnrAccreditOrderDao;
}

public void setPnrAccreditOrderDao(IPnrAccreditOrderDao pnrAccreditOrderDao) {
	this.pnrAccreditOrderDao = pnrAccreditOrderDao;
	this.setCommonGenericDao(pnrAccreditOrderDao);
}
}
