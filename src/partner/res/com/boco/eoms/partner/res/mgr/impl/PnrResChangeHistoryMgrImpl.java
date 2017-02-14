package com.boco.eoms.partner.res.mgr.impl;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.res.dao.PnrResChangeHistoryDao;
import com.boco.eoms.partner.res.dao.PnrResLineDao;
import com.boco.eoms.partner.res.mgr.IPnrResChangeHistoryMgr;
import com.boco.eoms.partner.res.model.PnrResChangeHistory;


/**
 * @author wangyue
 *
 */
public class PnrResChangeHistoryMgrImpl extends CommonGenericServiceImpl<PnrResChangeHistory> implements IPnrResChangeHistoryMgr {

	private PnrResChangeHistoryDao pnrResChangeHistoryDao;


	public PnrResChangeHistoryDao getPnrResChangeHistoryDao() {
		return pnrResChangeHistoryDao;
	}

	public void setPnrResChangeHistoryDao(
			PnrResChangeHistoryDao pnrResChangeHistoryDao) {
		this.pnrResChangeHistoryDao = pnrResChangeHistoryDao;
		this.setCommonGenericDao(pnrResChangeHistoryDao);
	}
	
	
}
