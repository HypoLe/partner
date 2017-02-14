package com.boco.activiti.partner.process.service.impl;

import org.activiti.engine.HistoryService;

import com.boco.activiti.partner.process.dao.IPnrPrecheckPhotoDao;
import com.boco.activiti.partner.process.model.PnrPrecheckPhoto;
import com.boco.activiti.partner.process.service.PnrPrecheckPhotoService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
/**
 * 
   * @author wangyue
   * @ClassName: PnrTransferNewPrecheckServiceImpl
   * @Version 版本 
   * @Copyright boco
   * @date Feb 4, 2015 10:19:10 AM
   * @description 本地网和干线预检预修工单与图片关系--service接口实现
 */
public class PnrPrecheckPhotoServiceImpl extends
		CommonGenericServiceImpl<PnrPrecheckPhoto> implements
		PnrPrecheckPhotoService {
	
	private IPnrPrecheckPhotoDao pnrPrecheckPhotoDao;


	private HistoryService historyService;

	public IPnrPrecheckPhotoDao getPnrPrecheckPhotoDao() {
		return pnrPrecheckPhotoDao;
	}

	public void setPnrPrecheckPhotoDao(IPnrPrecheckPhotoDao pnrPrecheckPhotoDao) {
		this.pnrPrecheckPhotoDao = pnrPrecheckPhotoDao;
		this.setCommonGenericDao(pnrPrecheckPhotoDao);
	}

	public HistoryService getHistoryService() {
		return historyService;
	}

	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}
	/******************************************实现方法区域*************************************************/

}
