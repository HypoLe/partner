package com.boco.eoms.partner.process.service;

import javax.servlet.http.HttpServletRequest;

import clover.org.apache.velocity.context.h;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcServiceImpl;
import com.boco.eoms.partner.process.dao.IPartnerProcessMainDao;
import com.boco.eoms.partner.process.model.PartnerProcessMain;
import com.boco.eoms.partner.process.util.PnrProcessHandler;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;

public class PartnerProcessMainServiceImpl extends CommonGenericServiceImpl<PartnerProcessMain>
		implements PartnerProcessMainService {
	private IPartnerProcessMainDao partnerProcessMainDao;
	public IPartnerProcessMainDao getPartnerProcessMainDao() {
		return partnerProcessMainDao;
	}
	public void setPartnerProcessMainDao(IPartnerProcessMainDao partnerProcessMainDao) {
		this.setCommonGenericDao(partnerProcessMainDao);
	}
	public ImportResult validateXLSFile(PartnerProcessMain ppMain) throws Exception {
		PnrProcessHandler handler=new PnrProcessHandler();
		return handler.validateXLSFile(ppMain);
	}
	public  boolean storeXLSFileData2DB(PartnerProcessMain ppMain,HttpServletRequest request) throws Exception{
		PnrProcessHandler handler=new PnrProcessHandler();
		 return handler.storeXLSFileData2DB(ppMain,request);
	}
	public  boolean restoreXLSFileData2DB(PartnerProcessMain ppMain,HttpServletRequest request) throws Exception{
		PnrProcessHandler handler=new PnrProcessHandler();
		 return handler.restoreXLSFileData2DB(ppMain,request);
	}
	public void deletePPmainAndPPlinkByPPmainid(String idMain) throws Exception {
		String sql1="delete from pnr_process_main where id='"+idMain+"'";
		String sql2="delete from pnr_process_link where reference_id='"+idMain+"'";
		CommonSpringJdbcServiceImpl jdbcService = (CommonSpringJdbcServiceImpl)
		ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
		jdbcService.executeProcedure(sql1);
		jdbcService.executeProcedure(sql2);
	}
	
}
