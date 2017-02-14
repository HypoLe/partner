package com.boco.eoms.deviceManagement.busi.network.service.impl;


import com.boco.eoms.deviceManagement.busi.network.dao.HiddenTroubleDao;
import com.boco.eoms.deviceManagement.busi.network.model.HiddenTrouble;
import com.boco.eoms.deviceManagement.busi.network.service.HiddenTroubleService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;

public class HiddenTroubleServiceImpl extends
		CommonGenericServiceImpl<HiddenTrouble> implements
		HiddenTroubleService {

	public void setHiddenTroubleDao(
			HiddenTroubleDao hiddenTroubleDao) {
		this.setCommonGenericDao(hiddenTroubleDao);
	}
	
	
}
