package com.boco.activiti.partner.process.service.impl;


import com.boco.activiti.partner.process.dao.IRoomDemolitionSpotcheckDao;
import com.boco.activiti.partner.process.model.RoomDemolitionSpotcheck;
import com.boco.activiti.partner.process.service.IRoomDemolitionSpotcheckService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;

/**
 
 */
public class RoomDemolitionSpotcheckServiceImpl extends CommonGenericServiceImpl<RoomDemolitionSpotcheck> implements IRoomDemolitionSpotcheckService {

    private IRoomDemolitionSpotcheckDao roomDemolitionSpotcheckDao;

	public IRoomDemolitionSpotcheckDao getRoomDemolitionSpotcheckDao() {
		return roomDemolitionSpotcheckDao;
	}

	public void setRoomDemolitionSpotcheckDao(
			IRoomDemolitionSpotcheckDao roomDemolitionSpotcheckDao) {
		this.roomDemolitionSpotcheckDao = roomDemolitionSpotcheckDao;
		this.setCommonGenericDao(roomDemolitionSpotcheckDao);
	}

	

	
  
}
