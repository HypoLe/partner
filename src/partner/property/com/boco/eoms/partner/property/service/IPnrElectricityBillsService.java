package com.boco.eoms.partner.property.service;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.property.model.PnrElectricityBills;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;

/**
 * 类说明：物业合同管理-电费费用记录 Service接口类
 * 创建人： fengguangping
 * 创建时间：2012-08-27 16:57:45
 */
public interface IPnrElectricityBillsService extends CommonGenericService<PnrElectricityBills> {
	//excel数据导入
	public ImportResult importFromFile(FormFile formFile) throws Exception;
}
