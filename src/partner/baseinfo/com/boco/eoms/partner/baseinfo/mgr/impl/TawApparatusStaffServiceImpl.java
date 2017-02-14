package com.boco.eoms.partner.baseinfo.mgr.impl;




import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.baseinfo.dao.TawApparatusStaffDao;
import com.boco.eoms.partner.baseinfo.mgr.TawApparatusStaffService;
import com.boco.eoms.partner.baseinfo.model.TawApparatus;


public class TawApparatusStaffServiceImpl extends
		CommonGenericServiceImpl<TawApparatus> implements
		TawApparatusStaffService {

	public void setTawApparatusStaffDao(
			TawApparatusStaffDao tawApparatusStaffDao) {
		this.setCommonGenericDao(tawApparatusStaffDao);
	}	
}
