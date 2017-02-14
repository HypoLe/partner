package com.boco.eoms.partner.deviceInspect.service.impl;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.deviceInspect.dao.PnrInspectTaskLinkDao;
import com.boco.eoms.partner.deviceInspect.model.PnrInspectTaskLink;
import com.boco.eoms.partner.deviceInspect.service.PnrInspectTaskLinkService;

public class PnrInspectTaskLinkServiceImpl extends CommonGenericServiceImpl<PnrInspectTaskLink> implements PnrInspectTaskLinkService {

	private PnrInspectTaskLinkDao pnrInspectTaskLinkDao;

	public PnrInspectTaskLinkDao getPnrInspectTaskLinkDao() {
		return pnrInspectTaskLinkDao;
	}

	public void setPnrInspectTaskLinkDao(PnrInspectTaskLinkDao pnrInspectTaskLinkDao) {
		this.setCommonGenericDao(pnrInspectTaskLinkDao);
		this.pnrInspectTaskLinkDao = pnrInspectTaskLinkDao;
	}
	
	
}
