package com.boco.eoms.deviceManagement.faultSheet.service;


import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.deviceManagement.faultSheet.model.FaultSheetManagement;

public interface FaultSheetManagementService extends CommonGenericService<FaultSheetManagement>{
	public String  Work_Order_No();
}
