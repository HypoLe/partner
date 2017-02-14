package com.boco.eoms.partner.deviceInspect.service.impl;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.deviceInspect.dao.PnrInspectLinkDao;
import com.boco.eoms.partner.deviceInspect.model.PnrInspectLink;
import com.boco.eoms.partner.deviceInspect.service.PnrInspectLinkService;

public class PnrInspectLinkServiceImpl extends CommonGenericServiceImpl<PnrInspectLink> implements PnrInspectLinkService {

	private PnrInspectLinkDao pnrInspectLinkDao;

	public PnrInspectLinkDao getPnrInspectLinkDao() {
		return pnrInspectLinkDao;
	}

	public void setPnrInspectLinkDao(PnrInspectLinkDao pnrInspectLinkDao) {
		this.pnrInspectLinkDao = pnrInspectLinkDao;
		this.setCommonGenericDao(pnrInspectLinkDao);
	}
	
	
}
