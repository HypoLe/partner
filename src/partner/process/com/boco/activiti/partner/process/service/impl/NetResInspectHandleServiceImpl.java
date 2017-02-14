package com.boco.activiti.partner.process.service.impl;


import java.util.Map;

import com.boco.activiti.partner.process.dao.INetResInspectHandleDao;
import com.boco.activiti.partner.process.dao.INetResInspectHandleJDBCDao;
import com.boco.activiti.partner.process.model.NetResInspectHandle;
import com.boco.activiti.partner.process.model.RoomDemolitionHandle;
import com.boco.activiti.partner.process.service.INetResInspectHandleService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;

/**
 
 */
public class NetResInspectHandleServiceImpl extends CommonGenericServiceImpl<NetResInspectHandle> implements INetResInspectHandleService {

    private INetResInspectHandleDao netResInspectHandleDao;
    
    private INetResInspectHandleJDBCDao netResInspectHandleJDBCDao;

	public INetResInspectHandleDao getNetResInspectHandleDao() {
		return netResInspectHandleDao;
	}

	public void setNetResInspectHandleDao(
			INetResInspectHandleDao netResInspectHandleDao) {
		this.netResInspectHandleDao = netResInspectHandleDao;
		this.setCommonGenericDao(netResInspectHandleDao);
	}

	public INetResInspectHandleJDBCDao getNetResInspectHandleJDBCDao() {
		return netResInspectHandleJDBCDao;
	}

	public void setNetResInspectHandleJDBCDao(
			INetResInspectHandleJDBCDao netResInspectHandleJDBCDao) {
		this.netResInspectHandleJDBCDao = netResInspectHandleJDBCDao;
	}



	/**
	 * 查询单条回复信息
	 * @param tempMap 查询条件
	 * @return
	 */
	@Override
	public RoomDemolitionHandle getOneTransferHandle(
			Map<String, String> tempMap) {
		return netResInspectHandleJDBCDao. getOneTransferHandle(tempMap);
	}

	
	
	
  
}
