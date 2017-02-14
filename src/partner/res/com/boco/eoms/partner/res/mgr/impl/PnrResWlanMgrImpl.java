package com.boco.eoms.partner.res.mgr.impl;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.res.dao.PnrResWlanDao;
import com.boco.eoms.partner.res.mgr.PnrResWlanMgr;
import com.boco.eoms.partner.res.model.PnrResWlan;

public class PnrResWlanMgrImpl extends CommonGenericServiceImpl<PnrResWlan> implements PnrResWlanMgr {

	private PnrResWlanDao pnrResWlanDao;

	public PnrResWlanDao getPnrResWlanDao() {
		return pnrResWlanDao;
	}

	public void setPnrResWlanDao(PnrResWlanDao pnrResWlanDao) {
		this.pnrResWlanDao = pnrResWlanDao;
		this.setCommonGenericDao(pnrResWlanDao);
	}
	
	
}
