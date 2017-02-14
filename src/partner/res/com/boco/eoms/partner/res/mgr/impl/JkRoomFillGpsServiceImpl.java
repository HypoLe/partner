package com.boco.eoms.partner.res.mgr.impl;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.res.dao.IJkRoomFillGpsDao;
import com.boco.eoms.partner.res.mgr.IJkRoomFillGpsService;
import com.boco.eoms.partner.res.model.JkRoomFillGps;

public class JkRoomFillGpsServiceImpl extends CommonGenericServiceImpl<JkRoomFillGps> implements IJkRoomFillGpsService{
	
	private IJkRoomFillGpsDao jkRoomFillGpsDao;

	public IJkRoomFillGpsDao getJkRoomFillGpsDao() {
		return jkRoomFillGpsDao;
	}

	public void setJkRoomFillGpsDao(IJkRoomFillGpsDao jkRoomFillGpsDao) {
		this.jkRoomFillGpsDao = jkRoomFillGpsDao;
		this.setCommonGenericDao(jkRoomFillGpsDao);
	}
}
