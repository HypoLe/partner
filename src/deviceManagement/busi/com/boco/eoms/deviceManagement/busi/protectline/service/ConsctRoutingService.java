package com.boco.eoms.deviceManagement.busi.protectline.service;


import com.boco.eoms.deviceManagement.busi.protectline.model.ConsctRouting;
import com.boco.eoms.deviceManagement.busi.protectline.model.ProteclineLink;
import com.boco.eoms.deviceManagement.common.service.CommonGenericService;


public interface ConsctRoutingService extends CommonGenericService<ConsctRouting> {
	public void save(ConsctRouting route,ProteclineLink link );

	public void delete(ConsctRouting route);
}
