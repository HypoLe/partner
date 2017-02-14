package com.boco.eoms.partner.resourceInfo.service;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.resourceInfo.model.MobileTerminal;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;

public interface MobileTerminalService extends CommonGenericService<MobileTerminal> {
	//excel数据导入
	public ImportResult importFromFile(FormFile formFile) throws Exception;
	public MobileTerminal  find(String id);
}
