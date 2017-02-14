package com.boco.activiti.partner.process.dao;

import java.util.Map;

import com.boco.activiti.partner.process.model.RoomDemolitionHandle;

public interface INetResInspectHandleJDBCDao {
	
	/**
	 * 查询单条回复信息
	 * @param tempMap 查询条件
	 * @return
	 */
	public RoomDemolitionHandle getOneTransferHandle(Map<String, String> tempMap);
	
}
