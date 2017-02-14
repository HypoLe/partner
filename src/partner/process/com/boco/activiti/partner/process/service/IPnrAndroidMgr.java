package com.boco.activiti.partner.process.service;

import java.sql.SQLException;
import java.util.Map;

import com.boco.activiti.partner.process.model.PnrAndroidPhotoFile;
import com.boco.activiti.partner.process.model.PnrTaskTicket;
import com.boco.eoms.deviceManagement.common.service.CommonGenericService;

public interface IPnrAndroidMgr extends CommonGenericService<PnrAndroidPhotoFile> {
	
	
	
	
	
	public void saveOrUpdateTaskFile(PnrAndroidPhotoFile photoFile,String data) throws SQLException;
	/**
	 * 根据图片的类型进行分页查询
	 * */
	public Map getResources(Integer curPage, Integer pageSize,
			final String whereStr);
}
