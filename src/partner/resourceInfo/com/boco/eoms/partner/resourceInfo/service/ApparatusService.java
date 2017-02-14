package com.boco.eoms.partner.resourceInfo.service;

import java.util.List;
import java.util.Map;
import org.apache.struts.upload.FormFile;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.resourceInfo.model.Apparatus;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;
import com.googlecode.genericdao.search.Search;

public interface ApparatusService extends CommonGenericService<Apparatus>{
	public ImportResult importFromFile(FormFile formFile) throws Exception;
	public Apparatus find(String id);//通过主键id查找model
}
