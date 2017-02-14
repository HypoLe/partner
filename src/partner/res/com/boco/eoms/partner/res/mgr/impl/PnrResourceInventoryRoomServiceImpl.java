package com.boco.eoms.partner.res.mgr.impl;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.res.dao.IPnrResourceInventoryRoomDao;
import com.boco.eoms.partner.res.mgr.IPnrResourceInventoryRoomService;
import com.boco.eoms.partner.res.model.PnrResourceInventoryRoom;

public class PnrResourceInventoryRoomServiceImpl extends CommonGenericServiceImpl<PnrResourceInventoryRoom> implements IPnrResourceInventoryRoomService{
	
	private IPnrResourceInventoryRoomDao pnrResourceInventoryRoomDao;
	

	public IPnrResourceInventoryRoomDao getPnrResourceInventoryRoomDao() {
		return pnrResourceInventoryRoomDao;
	}

	public void setPnrResourceInventoryRoomDao(
			IPnrResourceInventoryRoomDao pnrResourceInventoryRoomDao) {
		this.pnrResourceInventoryRoomDao = pnrResourceInventoryRoomDao;
		this.setCommonGenericDao(pnrResourceInventoryRoomDao);
	}
}
