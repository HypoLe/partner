package com.boco.eoms.partner.process.service;

import javax.servlet.http.HttpServletRequest;

import com.boco.eoms.deviceManagement.common.dao.CommonGenericDao;
import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.process.model.PartnerProcessMain;
import com.boco.eoms.partner.process.util.PnrProcessHandler;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;

public interface PartnerProcessMainService  extends CommonGenericService<PartnerProcessMain>{
	public ImportResult validateXLSFile(PartnerProcessMain ppMain) throws Exception;
	public  boolean storeXLSFileData2DB(PartnerProcessMain ppMain,HttpServletRequest request) throws Exception;
	public  boolean restoreXLSFileData2DB(PartnerProcessMain ppMain,HttpServletRequest request) throws Exception;
	public void deletePPmainAndPPlinkByPPmainid(String idMain) throws Exception;
}
