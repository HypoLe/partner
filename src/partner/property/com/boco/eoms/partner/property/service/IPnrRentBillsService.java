package com.boco.eoms.partner.property.service;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.property.model.PnrRentBills;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;

/**
 * 类说明：物业合同管理-租赁费用 Service接口类
 * 创建人： fengguangping
 * 创建时间：2012-08-27 16:57:47
 */
public interface IPnrRentBillsService extends CommonGenericService<PnrRentBills> {
	//excel数据导入
	public ImportResult importFromFile(FormFile formFile) throws Exception;
}
