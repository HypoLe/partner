package com.boco.activiti.partner.process.service.impl;

import com.boco.activiti.partner.process.dao.IPnrTransferMaleGuestInformDao;
import com.boco.activiti.partner.process.model.TransferMaleGuestInform;
import com.boco.activiti.partner.process.service.IPnrTransferMaleGuestInformService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;

public class PnrTransferMaleGuestInformServiceImpl extends CommonGenericServiceImpl<TransferMaleGuestInform> implements IPnrTransferMaleGuestInformService {
	
	private IPnrTransferMaleGuestInformDao pnrTransferMaleGuestInformDao;


	public IPnrTransferMaleGuestInformDao getPnrTransferMaleGuestInformDao() {
		return pnrTransferMaleGuestInformDao;
	}

	public void setPnrTransferMaleGuestInformDao(
			IPnrTransferMaleGuestInformDao pnrTransferMaleGuestInformDao) {
		this.pnrTransferMaleGuestInformDao = pnrTransferMaleGuestInformDao;
		this.setCommonGenericDao(pnrTransferMaleGuestInformDao);
	}

	@Override
	public void reminder(String workNumber) {
		
		
	}
	
	
	

}
