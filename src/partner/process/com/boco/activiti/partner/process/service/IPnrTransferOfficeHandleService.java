package com.boco.activiti.partner.process.service;

import java.util.Map;

import com.boco.activiti.partner.process.model.PnrTransferOfficeHandle;
import com.boco.activiti.partner.process.po.TransferOfficeHandleModel;
import com.boco.eoms.deviceManagement.common.service.CommonGenericService;

/**
 
 */
public interface IPnrTransferOfficeHandleService extends CommonGenericService<PnrTransferOfficeHandle> {
	
	/**
	 * 查询单条回复信息
	 * @param tempMap 查询条件
	 * @return
	 */
	public TransferOfficeHandleModel getOneTransferHandle(Map<String, String> tempMap);
}
