package com.boco.eoms.partner.inspect.mgr.impl;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.inspect.dao.IErrorDistanceDao;
import com.boco.eoms.partner.inspect.dao.IInspectLineTrackDao;
import com.boco.eoms.partner.inspect.dao.IInspectPlanApproveDao;
import com.boco.eoms.partner.inspect.mgr.IErrorDistanceMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectLineTrackMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanApproveMgr;
import com.boco.eoms.partner.inspect.model.ErrorDistance;
import com.boco.eoms.partner.inspect.model.InspectLineTrack;
import com.boco.eoms.partner.inspect.model.InspectPlanApprove;

/** 
 * Description:  
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     LEE 
 * @version:    1.0 
 * Create at:   Jul 18, 2012 5:36:12 PM 
 */
public class InspectLineTrackMgrImpl extends CommonGenericServiceImpl<InspectLineTrack> 
							implements IInspectLineTrackMgr {
	private IInspectLineTrackDao lineTrackDao;

	public IInspectLineTrackDao getLineTrackDao() {
		return lineTrackDao;
	}

	public void setLineTrackDao(IInspectLineTrackDao lineTrackDao) {
		this.lineTrackDao = lineTrackDao;
		this.setCommonGenericDao(lineTrackDao);
	}

}
