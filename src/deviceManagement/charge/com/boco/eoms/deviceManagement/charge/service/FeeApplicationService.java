package com.boco.eoms.deviceManagement.charge.service;



import java.io.InputStream;
import java.util.Map;

import com.boco.eoms.base.Constants;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.deviceManagement.charge.model.FeeAppliImportResult;
import com.boco.eoms.deviceManagement.charge.model.FeeApplicationMain;
import com.boco.eoms.deviceManagement.common.service.CommonGenericService;


public interface FeeApplicationService extends
               CommonGenericService<FeeApplicationMain> {
	public String importRecord(InputStream is, String fileName, Map<String, String> params) throws Exception;

	public String getBigRole();
	
	public String getParterRole();
}
 