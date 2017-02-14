package com.boco.eoms.partner.inspect.mgr.impl;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.inspect.dao.IErrorDistanceDao;
import com.boco.eoms.partner.inspect.dao.IInspectPlanApproveDao;
import com.boco.eoms.partner.inspect.mgr.IErrorDistanceMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanApproveMgr;
import com.boco.eoms.partner.inspect.model.ErrorDistance;
import com.boco.eoms.partner.inspect.model.InspectPlanApprove;

/** 
 * Description:  
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     LEE 
 * @version:    1.0 
 * Create at:   Jul 18, 2012 5:36:12 PM 
 */
public class ErrorDistanceMgrImpl extends CommonGenericServiceImpl<ErrorDistance> 
							implements IErrorDistanceMgr {
	private IErrorDistanceDao errorDistanceDao;


	public IErrorDistanceDao getErrorDistanceDao() {
		return errorDistanceDao;
	}

	public void setErrorDistanceDao(IErrorDistanceDao errorDistanceDao) {
		this.errorDistanceDao = errorDistanceDao;
		this.setCommonGenericDao(errorDistanceDao);
	}
	
	

}
