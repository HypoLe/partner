package com.boco.eoms.partner.res.mgr.impl;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.res.dao.PnrResLineDao;
import com.boco.eoms.partner.res.mgr.PnrResLineMgr;
import com.boco.eoms.partner.res.model.PnrResLine;

/** 
 * Description: 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liaojiming 
 * @version:    1.0 
 */ 
public class PnrResLineMgrImpl extends CommonGenericServiceImpl<PnrResLine> implements PnrResLineMgr {

	private PnrResLineDao pnrResLineDao;

	public PnrResLineDao getPnrResLineDao() {
		return pnrResLineDao;
	}

	public void setPnrResLineDao(PnrResLineDao pnrResLineDao) {
		this.pnrResLineDao = pnrResLineDao;
		this.setCommonGenericDao(pnrResLineDao);
	}
	
	
}
