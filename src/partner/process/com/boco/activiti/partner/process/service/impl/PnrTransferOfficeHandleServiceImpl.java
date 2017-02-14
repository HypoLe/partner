package com.boco.activiti.partner.process.service.impl;


import java.util.Map;

import com.boco.activiti.partner.process.dao.IPnrTransferOfficeHandleDao;
import com.boco.activiti.partner.process.dao.IPnrTransferOfficeHandleJDBCDao;
import com.boco.activiti.partner.process.model.PnrTransferOfficeHandle;
import com.boco.activiti.partner.process.po.TransferOfficeHandleModel;
import com.boco.activiti.partner.process.service.IPnrTransferOfficeHandleService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;

/**
 
 */
public class PnrTransferOfficeHandleServiceImpl extends CommonGenericServiceImpl<PnrTransferOfficeHandle> implements IPnrTransferOfficeHandleService {

    private IPnrTransferOfficeHandleDao pnrTransferOfficeHandleDao;
    
    private IPnrTransferOfficeHandleJDBCDao pnrTransferOfficeHandleJDBCDao;

    /**
	 * 查询单条回复信息
	 * @param tempMap 查询条件
	 * @return
	 */
	@Override
	public TransferOfficeHandleModel getOneTransferHandle(
			Map<String, String> tempMap) {
		return pnrTransferOfficeHandleJDBCDao. getOneTransferHandle(tempMap);
	}
	
	public IPnrTransferOfficeHandleDao getPnrTransferOfficeHandleDao() {
		return pnrTransferOfficeHandleDao;
	}

	public void setPnrTransferOfficeHandleDao(
			IPnrTransferOfficeHandleDao pnrTransferOfficeHandleDao) {
		this.pnrTransferOfficeHandleDao = pnrTransferOfficeHandleDao;
		this.setCommonGenericDao(pnrTransferOfficeHandleDao);
	}

	public IPnrTransferOfficeHandleJDBCDao getPnrTransferOfficeHandleJDBCDao() {
		return pnrTransferOfficeHandleJDBCDao;
	}

	public void setPnrTransferOfficeHandleJDBCDao(
			IPnrTransferOfficeHandleJDBCDao pnrTransferOfficeHandleJDBCDao) {
		this.pnrTransferOfficeHandleJDBCDao = pnrTransferOfficeHandleJDBCDao;
	}

	
  
}
