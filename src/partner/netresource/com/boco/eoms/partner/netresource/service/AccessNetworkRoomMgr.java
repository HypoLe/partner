package com.boco.eoms.partner.netresource.service;

import java.util.List;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.netresource.model.bs.AccessNetworkRoom;
import com.boco.eoms.partner.netresource.util.ImportResult;

/** 
 * Description: 
 * Copyright:   Copyright (c)2013
 * Company:     BOCO 

 * @author:     chenbing 
 * @version:    1.0 
 */ 
public interface AccessNetworkRoomMgr extends CommonGenericService<AccessNetworkRoom> {
		
	
		/**
		 * 通过姓名获取AccessNetWorkRoom对象
		 * @param anrName
		 * @return List
		 */
		public List<AccessNetworkRoom> getAnryName(String anrName);
		
		/**
		 * 资源导入-接入网资源
		 * @param formFile
		 * @param province
		 * @param specialty
		 * @param creatorId
		 * @return
		 * @throws Exception importBsBtEqFile
		 */
		public ImportResult importAnrFile( FormFile formFile,String province,String specialty,String creatorId,String osPath) throws Exception;  //资源的导入
		/**
		 * 资源导入-接入网资源--设备
		 * @param formFile
		 * @param province
		 * @param specialty
		 * @param creatorId
		 * @return
		 * @throws Exception 
		 */
		public ImportResult importAnrEqFile( FormFile formFile,String province,String specialty,String creatorId,String osPath) throws Exception;  //资源的导入
		
	
	
}
