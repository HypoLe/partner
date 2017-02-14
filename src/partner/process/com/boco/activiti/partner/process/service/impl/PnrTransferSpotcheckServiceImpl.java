package com.boco.activiti.partner.process.service.impl;


import com.boco.activiti.partner.process.dao.IPnrTransferOfficeHandleDao;
import com.boco.activiti.partner.process.dao.IPnrTransferSpotcheckDao;
import com.boco.activiti.partner.process.model.PnrTransferSpotcheck;
import com.boco.activiti.partner.process.service.IPnrTransferSpotcheckService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;

/**
 
 */
public class PnrTransferSpotcheckServiceImpl extends CommonGenericServiceImpl<PnrTransferSpotcheck> implements IPnrTransferSpotcheckService {

    private IPnrTransferSpotcheckDao pnrTransferSpotcheckDao;

	public IPnrTransferSpotcheckDao getPnrTransferSpotcheckDao() {
		return pnrTransferSpotcheckDao;
	}

	public void setPnrTransferSpotcheckDao(
			IPnrTransferSpotcheckDao pnrTransferSpotcheckDao) {
		this.pnrTransferSpotcheckDao = pnrTransferSpotcheckDao;
		
		setCommonGenericDao(pnrTransferSpotcheckDao);
	}

	

	
  
}
