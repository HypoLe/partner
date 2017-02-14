package com.boco.eoms.partner.res.mgr.impl;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.res.dao.IPnrResourceSuccessLogDao;
import com.boco.eoms.partner.res.mgr.IPnrResourceSuccessLogService;
import com.boco.eoms.partner.res.model.PnrResourceSuccessLog;

public class PnrResourceSuccessLogServiceImpl extends CommonGenericServiceImpl<PnrResourceSuccessLog> implements IPnrResourceSuccessLogService{
	
	private IPnrResourceSuccessLogDao pnrResourceSuccessLogDao;

	public IPnrResourceSuccessLogDao getPnrResourceSuccessLogDao() {
		return pnrResourceSuccessLogDao;
	}

	public void setPnrResourceSuccessLogDao(
			IPnrResourceSuccessLogDao pnrResourceSuccessLogDao) {
		this.pnrResourceSuccessLogDao = pnrResourceSuccessLogDao;
		this.setCommonGenericDao(pnrResourceSuccessLogDao);
	}
	
}
