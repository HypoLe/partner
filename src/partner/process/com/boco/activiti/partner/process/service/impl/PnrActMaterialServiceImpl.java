package com.boco.activiti.partner.process.service.impl;

import org.activiti.engine.HistoryService;

import com.boco.activiti.partner.process.dao.IPnrActMaterialDao;
import com.boco.activiti.partner.process.dao.IPnrActMaterialJDBCDao;
import com.boco.activiti.partner.process.model.PnrActMaterial;
import com.boco.activiti.partner.process.service.IPnrActMaterialService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
/**
 * 
 * @author WANGJUN
 *
 */
public class PnrActMaterialServiceImpl extends
		CommonGenericServiceImpl<PnrActMaterial> implements
		IPnrActMaterialService {
	
	private IPnrActMaterialDao pnrActMaterialDao;
	
	private IPnrActMaterialJDBCDao pnrActMaterialJDBCDao;


	private HistoryService historyService;


	public IPnrActMaterialDao getPnrActMaterialDao() {
		return pnrActMaterialDao;
	}


	public void setPnrActMaterialDao(IPnrActMaterialDao pnrActMaterialDao) {
		this.pnrActMaterialDao = pnrActMaterialDao;
		this.setCommonGenericDao(pnrActMaterialDao);
	}
	
	


	public IPnrActMaterialJDBCDao getPnrActMaterialJDBCDao() {
		return pnrActMaterialJDBCDao;
	}


	public void setPnrActMaterialJDBCDao(
			IPnrActMaterialJDBCDao pnrActMaterialJDBCDao) {
		this.pnrActMaterialJDBCDao = pnrActMaterialJDBCDao;
	}


	public HistoryService getHistoryService() {
		return historyService;
	}


	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

	
	/******************************************实现方法区域*************************************************/

	public void deletePnrActMaterialsByProcessInstanceId(String processInstanceId){
		pnrActMaterialJDBCDao.deletePnrActMaterialsByProcessInstanceId(processInstanceId);
	}
}
