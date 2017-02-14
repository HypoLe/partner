package com.boco.eoms.partner.res.mgr.impl;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.res.dao.PnrResJiekeDao;
import com.boco.eoms.partner.res.mgr.PnrResJiekeMgr;
import com.boco.eoms.partner.res.model.PnrResJieke;

/** 
 * Description: 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liaojiming 
 * @version:    1.0 
 */ 
public class PnrResJiekeMgrImpl extends CommonGenericServiceImpl<PnrResJieke> implements PnrResJiekeMgr {

	private PnrResJiekeDao pnrResJiekeDao;

	public PnrResJiekeDao getPnrResJiekeDao() {
		return pnrResJiekeDao;
	}

	public void setPnrResJiekeDao(PnrResJiekeDao pnrResJiekeDao) {
		this.pnrResJiekeDao = pnrResJiekeDao;
		this.setCommonGenericDao(pnrResJiekeDao);
	}
	
	
}
