package com.boco.eoms.partner.resourceInfo.service;

import java.util.List;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.resourceInfo.model.OilEngine;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;

public interface OilEngineService  extends CommonGenericService<OilEngine>{
	public ImportResult importFromFile(FormFile formFile) throws Exception;
	public OilEngine find(String id);
	public List getOilEngineChangeList(String whereStr);
}
