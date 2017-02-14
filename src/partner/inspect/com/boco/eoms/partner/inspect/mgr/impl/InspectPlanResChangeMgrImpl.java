package com.boco.eoms.partner.inspect.mgr.impl;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.inspect.dao.IInspectPlanResChangeDao;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanResChangeMgr;
import com.boco.eoms.partner.inspect.model.InspectPlanResChange;

/** 
 * Description:  
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Jul 23, 2012 3:40:49 PM 
 */
public class InspectPlanResChangeMgrImpl extends CommonGenericServiceImpl<InspectPlanResChange> 
									implements IInspectPlanResChangeMgr{
	private IInspectPlanResChangeDao inspectPlanResChangeDao;

	public IInspectPlanResChangeDao getInspectPlanResChangeDao() {
		return inspectPlanResChangeDao;
	}

	public void setInspectPlanResChangeDao(IInspectPlanResChangeDao inspectPlanResChangeDao) {
		this.setCommonGenericDao(inspectPlanResChangeDao);
		this.inspectPlanResChangeDao = inspectPlanResChangeDao;
	}
	
	
}
