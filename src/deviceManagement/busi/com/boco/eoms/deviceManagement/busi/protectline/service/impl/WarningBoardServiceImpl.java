package com.boco.eoms.deviceManagement.busi.protectline.service.impl;

import com.boco.eoms.deviceManagement.busi.protectline.dao.WarningBoardDao;
import com.boco.eoms.deviceManagement.busi.protectline.model.WarningBoard;
import com.boco.eoms.deviceManagement.busi.protectline.service.WarningBoardService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;



public class WarningBoardServiceImpl extends
              CommonGenericServiceImpl<WarningBoard> implements
              WarningBoardService {

	public void setWarningBoardDao(WarningBoardDao warningBoardDao) {
		this.setCommonGenericDao(warningBoardDao);
	}
	
	
	
	
}
