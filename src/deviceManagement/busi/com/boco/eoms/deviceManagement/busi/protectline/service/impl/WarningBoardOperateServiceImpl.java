package com.boco.eoms.deviceManagement.busi.protectline.service.impl;


import com.boco.eoms.deviceManagement.busi.protectline.dao.WarningBoardOperateDao;
import com.boco.eoms.deviceManagement.busi.protectline.model.WarningBoardOperate;
import com.boco.eoms.deviceManagement.busi.protectline.service.WarningBoardOperateService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;


 

public class WarningBoardOperateServiceImpl extends
           CommonGenericServiceImpl<WarningBoardOperate> implements
           WarningBoardOperateService {

public void setWarningBoardOperateDao(WarningBoardOperateDao warningBoardOperateDao) {
           this.setCommonGenericDao(warningBoardOperateDao);
}
}




