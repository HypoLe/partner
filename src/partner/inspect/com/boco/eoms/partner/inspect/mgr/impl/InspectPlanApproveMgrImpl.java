package com.boco.eoms.partner.inspect.mgr.impl;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.inspect.dao.IInspectPlanApproveDao;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanApproveMgr;
import com.boco.eoms.partner.inspect.model.InspectPlanApprove;

/** 
 * Description:  
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Jul 18, 2012 5:36:12 PM 
 */
public class InspectPlanApproveMgrImpl extends CommonGenericServiceImpl<InspectPlanApprove> 
							implements IInspectPlanApproveMgr {
	private IInspectPlanApproveDao inspectPlanApproveDao;

	public IInspectPlanApproveDao getInspectPlanApproveDao() {
		return inspectPlanApproveDao;
	}

	public void setInspectPlanApproveDao(
			IInspectPlanApproveDao inspectPlanApproveDao) {
		this.inspectPlanApproveDao = inspectPlanApproveDao;
		this.setCommonGenericDao(inspectPlanApproveDao);
	}
	
	

}
