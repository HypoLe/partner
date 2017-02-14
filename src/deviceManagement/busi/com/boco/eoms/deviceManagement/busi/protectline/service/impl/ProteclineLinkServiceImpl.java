package com.boco.eoms.deviceManagement.busi.protectline.service.impl;


import com.boco.eoms.deviceManagement.busi.protectline.dao.ProteclineLinkDao;
import com.boco.eoms.deviceManagement.busi.protectline.model.ProteclineLink;
import com.boco.eoms.deviceManagement.busi.protectline.service.ProteclineLinkService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;

public class ProteclineLinkServiceImpl extends
		CommonGenericServiceImpl<ProteclineLink> implements
		ProteclineLinkService {

	public void setProteclineLinkDao(
			ProteclineLinkDao proteclineLinkDao) {
		this.setCommonGenericDao(proteclineLinkDao);
	}
}
