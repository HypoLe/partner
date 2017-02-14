package com.boco.eoms.deviceManagement.fiber.service.impl;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.deviceManagement.fiber.dao.FiberInformationDao;
import com.boco.eoms.deviceManagement.fiber.model.FiberInformation;
import com.boco.eoms.deviceManagement.fiber.service.FiberInformationService;

public class FiberInformationServiceImpl extends
CommonGenericServiceImpl<FiberInformation>  implements FiberInformationService{
	public void setFiberInformationDao(FiberInformationDao fiberInformationDao) {
		this.setCommonGenericDao(fiberInformationDao);
	}

}
