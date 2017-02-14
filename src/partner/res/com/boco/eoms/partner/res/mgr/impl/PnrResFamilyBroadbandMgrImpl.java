package com.boco.eoms.partner.res.mgr.impl;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.res.dao.PnrResFamilyBroadbandDao;
import com.boco.eoms.partner.res.mgr.PnrResFamilyBroadbandMgr;
import com.boco.eoms.partner.res.model.PnrResFamilyBroadband;

/** 
 * Description: 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liaojiming 
 * @version:    1.0 
 */ 
public class PnrResFamilyBroadbandMgrImpl extends CommonGenericServiceImpl<PnrResFamilyBroadband> implements PnrResFamilyBroadbandMgr {

	private PnrResFamilyBroadbandDao pnrResFamilyBroadbandDao;

	public PnrResFamilyBroadbandDao getPnrResFamilyBroadbandDao() {
		return pnrResFamilyBroadbandDao;
	}

	public void setPnrResFamilyBroadbandDao(PnrResFamilyBroadbandDao pnrResFamilyBroadbandDao) {
		this.setCommonGenericDao(pnrResFamilyBroadbandDao);
		this.pnrResFamilyBroadbandDao = pnrResFamilyBroadbandDao;
	}

}
