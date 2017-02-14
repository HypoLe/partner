package com.boco.eoms.deviceManagement.faultSheet.service;

import java.util.Date;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.deviceManagement.faultSheet.dao.FaultSheetManagementDao;
import com.boco.eoms.deviceManagement.faultSheet.model.FaultSheetManagement;
import com.boco.eoms.deviceManagement.qualify.util.NumberUtil;

public class FaultSheetManagementServiceImp extends
		CommonGenericServiceImpl<FaultSheetManagement> implements
		FaultSheetManagementService {
	public void setFaultSheetManagementDao(
			FaultSheetManagementDao faultSheetManagementDao) {
		this.setCommonGenericDao(faultSheetManagementDao);}
    public String  Work_Order_No(){
	   
	   return  NumberUtil.generateNumber("HL", "051",new Date(),"faultSheetProcessSheetId", "t_taskorder_dual");
	
}
	
}
