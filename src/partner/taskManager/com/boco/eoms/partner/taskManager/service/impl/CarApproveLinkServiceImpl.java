package com.boco.eoms.partner.taskManager.service.impl;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.taskManager.dao.ICarApproveLinkDao;
import com.boco.eoms.partner.taskManager.model.CarApproveLink;
import com.boco.eoms.partner.taskManager.service.ICarApproveLinkService;

public class CarApproveLinkServiceImpl extends CommonGenericServiceImpl<CarApproveLink>  implements ICarApproveLinkService {

	private ICarApproveLinkDao carApproveLinkDao;

	public ICarApproveLinkDao getCarApproveLinkDao() {
		return carApproveLinkDao;
	}

	public void setCarApproveLinkDao(ICarApproveLinkDao carApproveLinkDao) {
		this.setCommonGenericDao(carApproveLinkDao);
		this.carApproveLinkDao = carApproveLinkDao;
	}
	
}
