package com.boco.eoms.deviceManagement.busi.protectline.service.impl;


import com.boco.eoms.deviceManagement.busi.protectline.dao.MechanicalArmManagementDao;
import com.boco.eoms.deviceManagement.busi.protectline.model.MechanicalArmManagement;
import com.boco.eoms.deviceManagement.busi.protectline.service.MechanicalArmManagementService;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;

public class MechanicalArmManagementServiceImp extends
		CommonGenericServiceImpl<MechanicalArmManagement> implements
		MechanicalArmManagementService {
	
		public void setMechanicalArmManagementDao(
			MechanicalArmManagementDao mechanicalArmManagementDao) {
		this.setCommonGenericDao(mechanicalArmManagementDao);
	}
	
}
