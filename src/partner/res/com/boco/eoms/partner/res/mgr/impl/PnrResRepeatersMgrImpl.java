package com.boco.eoms.partner.res.mgr.impl;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.res.dao.PnrResRepeatersDao;
import com.boco.eoms.partner.res.mgr.PnrResRepeatersMgr;
import com.boco.eoms.partner.res.model.PnrResRepeaters;

/** 
 * Description: 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liaojiming 
 * @version:    1.0 
 */ 
public class PnrResRepeatersMgrImpl extends CommonGenericServiceImpl<PnrResRepeaters> implements PnrResRepeatersMgr {

	private PnrResRepeatersDao pnrResRepeatersDao;

	public PnrResRepeatersDao getPnrResRepeatersDao() {
		return pnrResRepeatersDao;
	}

	public void setPnrResRepeatersDao(PnrResRepeatersDao pnrResRepeatersDao) {
		this.pnrResRepeatersDao = pnrResRepeatersDao;
		this.setCommonGenericDao(pnrResRepeatersDao);
	}
	
	
}
