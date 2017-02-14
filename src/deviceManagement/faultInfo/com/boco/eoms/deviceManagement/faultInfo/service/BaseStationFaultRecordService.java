package com.boco.eoms.deviceManagement.faultInfo.service;


import java.io.InputStream;
import java.util.Map;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.deviceManagement.faultInfo.model.BaseStationFaultRecord;
import com.boco.eoms.deviceManagement.faultInfo.model.ImportResult;


public interface BaseStationFaultRecordService extends CommonGenericService<BaseStationFaultRecord> {
	public ImportResult importFromFile(InputStream is, String fileName, Map<String, String> params) throws Exception;
}
