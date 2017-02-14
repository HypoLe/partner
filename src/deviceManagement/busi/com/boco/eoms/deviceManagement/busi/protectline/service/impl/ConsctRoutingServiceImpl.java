package com.boco.eoms.deviceManagement.busi.protectline.service.impl;


import java.util.List;

import com.boco.eoms.deviceManagement.busi.protectline.dao.ConsctRoutingDao;
import com.boco.eoms.deviceManagement.busi.protectline.model.ConsctRouting;
import com.boco.eoms.deviceManagement.busi.protectline.model.ProteclineLink;
import com.boco.eoms.deviceManagement.busi.protectline.service.ConsctRoutingService;
import com.boco.eoms.deviceManagement.busi.protectline.service.ProteclineLinkService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.googlecode.genericdao.search.Search;

public class ConsctRoutingServiceImpl extends
		CommonGenericServiceImpl<ConsctRouting> implements
		ConsctRoutingService {

	ProteclineLinkService linkService;
	
	public void setConsctRoutingDao(
			ConsctRoutingDao consctRoutingDao) {
		this.setCommonGenericDao(consctRoutingDao);
	}
	
	public void setLinkService(ProteclineLinkService linkService) {
		this.linkService = linkService;
	}
	
	public void save(ConsctRouting route,ProteclineLink link ) {
		this.save(route);
		link.setMainId(route.getId());
		link.setMainType("1");
		linkService.save(link);
	}

	public void delete(ConsctRouting route) {
		String id = route.getId();
		Search search = new Search();
		search.addFilterEqual("mainId", id);
		List<ProteclineLink> pls = linkService.search(search);
		for(ProteclineLink pl : pls) {
			linkService.remove(pl);
		}
		this.remove(route);
	}
	
}
