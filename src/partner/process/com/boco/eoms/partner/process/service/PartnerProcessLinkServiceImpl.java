package com.boco.eoms.partner.process.service;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.process.dao.IPartnerProcessLinkDao;
import com.boco.eoms.partner.process.model.PartnerProcessLink;

public class PartnerProcessLinkServiceImpl  extends CommonGenericServiceImpl<PartnerProcessLink>  
	implements PartnerProcessLinkService{
	private IPartnerProcessLinkDao partnerProcessLinkDao;

	public IPartnerProcessLinkDao getPartnerProcessLinkDao() {
		return partnerProcessLinkDao;
	}

	public void setPartnerProcessLinkDao(	IPartnerProcessLinkDao partnerProcessLinkDao) {
		this.setCommonGenericDao(partnerProcessLinkDao);
	}
	
}
