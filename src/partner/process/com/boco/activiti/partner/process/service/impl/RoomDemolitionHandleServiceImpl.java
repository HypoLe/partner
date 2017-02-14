package com.boco.activiti.partner.process.service.impl;


import java.util.Map;

import com.boco.activiti.partner.process.dao.IRoomDemolitionHandleDao;
import com.boco.activiti.partner.process.dao.IRoomDemolitionHandleJDBCDao;
import com.boco.activiti.partner.process.model.RoomDemolitionHandle;
import com.boco.activiti.partner.process.service.IRoomDemolitionHandleService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;

/**
 
 */
public class RoomDemolitionHandleServiceImpl extends CommonGenericServiceImpl<RoomDemolitionHandle> implements IRoomDemolitionHandleService {

    private IRoomDemolitionHandleDao roomDemolitionHandleDao;
    
    private IRoomDemolitionHandleJDBCDao roomDemolitionHandleJDBCDao;

    /**
	 * 查询单条回复信息
	 * @param tempMap 查询条件
	 * @return
	 */
	@Override
	public RoomDemolitionHandle getOneTransferHandle(
			Map<String, String> tempMap) {
		return roomDemolitionHandleJDBCDao. getOneTransferHandle(tempMap);
	}

	public IRoomDemolitionHandleDao getRoomDemolitionHandleDao() {
		return roomDemolitionHandleDao;
	}

	public void setRoomDemolitionHandleDao(
			IRoomDemolitionHandleDao roomDemolitionHandleDao) {
		this.roomDemolitionHandleDao = roomDemolitionHandleDao;
		this.setCommonGenericDao(roomDemolitionHandleDao);
	}

	public IRoomDemolitionHandleJDBCDao getRoomDemolitionHandleJDBCDao() {
		return roomDemolitionHandleJDBCDao;
	}

	public void setRoomDemolitionHandleJDBCDao(
			IRoomDemolitionHandleJDBCDao roomDemolitionHandleJDBCDao) {
		this.roomDemolitionHandleJDBCDao = roomDemolitionHandleJDBCDao;
	}
	
	
	
  
}
