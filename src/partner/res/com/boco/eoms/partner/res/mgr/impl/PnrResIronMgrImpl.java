package com.boco.eoms.partner.res.mgr.impl;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.res.dao.PnrResIronDao;
import com.boco.eoms.partner.res.mgr.PnrResIronMgr;
import com.boco.eoms.partner.res.model.PnrResIron;

/** 
 * Description: 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liaojiming 
 * @version:    1.0 
 */ 
public class PnrResIronMgrImpl extends CommonGenericServiceImpl<PnrResIron> implements PnrResIronMgr {

	private PnrResIronDao pnrResIronDao;

	public PnrResIronDao getPnrResIronDao() {
		return pnrResIronDao;
	}

	public void setPnrResIronDao(PnrResIronDao pnrResIronDao) {
		this.pnrResIronDao = pnrResIronDao;
		this.setCommonGenericDao(pnrResIronDao);
	}
}
