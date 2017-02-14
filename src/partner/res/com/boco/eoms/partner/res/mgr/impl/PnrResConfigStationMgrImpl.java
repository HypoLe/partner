package com.boco.eoms.partner.res.mgr.impl;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.res.dao.PnrResConfigStationDao;
import com.boco.eoms.partner.res.mgr.PnrResConfigStationMgr;
import com.boco.eoms.partner.res.model.PnrResConfigStation;

/** 
 * Description: 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liaojiming 
 * @version:    1.0 
 */ 
public class PnrResConfigStationMgrImpl extends CommonGenericServiceImpl<PnrResConfigStation> implements PnrResConfigStationMgr {

	private PnrResConfigStationDao  pnrResConfigStationDao;

	public PnrResConfigStationDao getPnrResConfigStationDao() {
		return pnrResConfigStationDao;
	}

	public void setPnrResConfigStationDao(
			PnrResConfigStationDao pnrResConfigStationDao) {
		this.pnrResConfigStationDao = pnrResConfigStationDao;
		this.setCommonGenericDao(pnrResConfigStationDao);
	}
	
	
}
