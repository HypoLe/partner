package com.boco.activiti.partner.process.service.impl;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.activiti.partner.process.dao.IPnrTaskTicketHandleDao;
import com.boco.activiti.partner.process.model.PnrTaskTicketHandle;
import com.boco.activiti.partner.process.service.IPnrTaskTicketHandleService;

/**
 
 */
public class PnrTaskTicketHandleServiceImpl extends CommonGenericServiceImpl<PnrTaskTicketHandle> implements IPnrTaskTicketHandleService {

    private IPnrTaskTicketHandleDao pnrTaskTicketHandleDao;

	public IPnrTaskTicketHandleDao getPnrTaskTicketHandleDao() {
		return pnrTaskTicketHandleDao;
	}

	public void setPnrTaskTicketHandleDao(
			IPnrTaskTicketHandleDao pnrTaskTicketHandleDao) {
		this.pnrTaskTicketHandleDao = pnrTaskTicketHandleDao;
		this.setCommonGenericDao(pnrTaskTicketHandleDao);
	}

	

    


  
}
