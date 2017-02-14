package com.boco.activiti.partner.process.service.impl;


import com.boco.activiti.partner.process.dao.IPnrTroubleTicketHandleDao;
import com.boco.activiti.partner.process.model.PnrTroubleTicketHandle;
import com.boco.activiti.partner.process.service.IPnrTroubleTicketHandleService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;

/**
 
 */
public class PnrTroubleTicketHandleServiceImpl extends CommonGenericServiceImpl<PnrTroubleTicketHandle> implements IPnrTroubleTicketHandleService {

    private IPnrTroubleTicketHandleDao pnrTroubleTicketHandleDao;

	public IPnrTroubleTicketHandleDao getPnrTroubleTicketHandleDao() {
		return pnrTroubleTicketHandleDao;
	}

	public void setPnrTroubleTicketHandleDao(
			IPnrTroubleTicketHandleDao pnrTroubleTicketHandleDao) {
		this.pnrTroubleTicketHandleDao = pnrTroubleTicketHandleDao;
		this.setCommonGenericDao(pnrTroubleTicketHandleDao);
	}




  
}
