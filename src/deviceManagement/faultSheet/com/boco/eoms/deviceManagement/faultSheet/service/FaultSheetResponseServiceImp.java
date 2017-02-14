package com.boco.eoms.deviceManagement.faultSheet.service;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.deviceManagement.faultSheet.dao.FaultSheetResponseDao;
import com.boco.eoms.deviceManagement.faultSheet.model.FaultSheetResponse;

public class FaultSheetResponseServiceImp extends
		CommonGenericServiceImpl<FaultSheetResponse> implements
		FaultSheetResponseService {
	public void setFaultSheetResponseDao(
			FaultSheetResponseDao faultSheetResponseDao) {
		this.setCommonGenericDao(faultSheetResponseDao);

	}
	
	

	
}