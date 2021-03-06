package com.boco.activiti.partner.process.service;

import java.util.Map;

import com.boco.activiti.partner.process.model.RoomDemolitionHandle;
import com.boco.eoms.deviceManagement.common.service.CommonGenericService;

/**
 
 */
public interface IRoomDemolitionHandleService extends CommonGenericService<RoomDemolitionHandle> {
	
	/**
	 * 查询单条回复信息
	 * @param tempMap 查询条件
	 * @return
	 */
	public RoomDemolitionHandle getOneTransferHandle(Map<String, String> tempMap);
}
