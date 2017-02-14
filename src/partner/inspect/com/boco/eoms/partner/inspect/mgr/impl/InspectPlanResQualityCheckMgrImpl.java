package com.boco.eoms.partner.inspect.mgr.impl;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.inspect.dao.IInspectPlanResQualityCheckDao;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanResQualityCheckMgr;
import com.boco.eoms.partner.inspect.model.InspectPlanResQualityCheck;

/** 
 * Description: 
 * Copyright:   Copyright (c)2013
 * Company:     BOCO
 * @author:     LiuChang
 * @version:    1.0
 * Create at:   Apr 19, 2013 4:14:42 PM
 */
public class InspectPlanResQualityCheckMgrImpl extends CommonGenericServiceImpl<InspectPlanResQualityCheck>  
					implements IInspectPlanResQualityCheckMgr {
	private IInspectPlanResQualityCheckDao inspectPlanResQualityCheckDao;

	public IInspectPlanResQualityCheckDao getInspectPlanResQualityCheckDao() {
		return inspectPlanResQualityCheckDao;
	}

	public void setInspectPlanResQualityCheckDao(
			IInspectPlanResQualityCheckDao inspectPlanResQualityCheckDao) {
		this.setCommonGenericDao(inspectPlanResQualityCheckDao);
		this.inspectPlanResQualityCheckDao = inspectPlanResQualityCheckDao;
	}
	
	
}
