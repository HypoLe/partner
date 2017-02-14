package com.boco.eoms.partner.baseinfo.dao;




import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;

import com.boco.eoms.partner.baseinfo.model.TawPartnerCar;


public class TawParCarServiceImpl extends
		CommonGenericServiceImpl<TawPartnerCar> implements
		TawParCarService {

	public void setTawParCarDao(
			TawParCarDao tawParCarDao) {
		this.setCommonGenericDao(tawParCarDao);
	}	
}
