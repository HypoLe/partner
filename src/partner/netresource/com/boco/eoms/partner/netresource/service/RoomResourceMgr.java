package com.boco.eoms.partner.netresource.service;

import java.util.List;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.netresource.model.bs.RoomResource;
import com.boco.eoms.partner.netresource.util.ImportResult;

/** 
 * Description: 
 * Copyright:   Copyright (c)2013
 * Company:     BOCO 

 * @author:     chenbing 
 * @version:    1.0 
 */ 
public interface RoomResourceMgr extends CommonGenericService<RoomResource> {
		
		/**
		 * 通过姓名获取RoomResource对象
		 * @param reName
		 * @return List
		 */
		public List<RoomResource> getRerByName(String reName);
		/**
		 * 资源导入-室分资源
		 * @param formFile
		 * @param province
		 * @param specialty
		 * @param creatorId
		 * @return
		 */
		public ImportResult importRerFromFile( FormFile formFile,String province,String specialty,String creatorId,String osPath) throws Exception;  //资源的导入
		
		
}
