package com.boco.activiti.partner.process.service.impl;


import com.boco.activiti.partner.process.dao.IMasteRelTaskItemDao;
import com.boco.activiti.partner.process.model.MasteRelTaskItem;
import com.boco.activiti.partner.process.service.IMasteRelTaskItemService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;

/**
 
 */
public class MasteRelTaskItemServiceImpl extends CommonGenericServiceImpl<MasteRelTaskItem> implements IMasteRelTaskItemService {

    private IMasteRelTaskItemDao masteRelTaskItemDao;

	public IMasteRelTaskItemDao getMasteRelTaskItemDao() {
		return masteRelTaskItemDao;
	}

	public void setMasteRelTaskItemDao(IMasteRelTaskItemDao masteRelTaskItemDao) {
		this.masteRelTaskItemDao = masteRelTaskItemDao;
		this.setCommonGenericDao(masteRelTaskItemDao);
	}
	
}
