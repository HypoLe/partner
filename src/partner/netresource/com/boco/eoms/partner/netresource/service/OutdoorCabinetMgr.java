package com.boco.eoms.partner.netresource.service;

import java.util.List;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.netresource.model.bs.OutdoorCabinet;
import com.boco.eoms.partner.netresource.util.ImportResult;

/** 
 * Description: 
 * Copyright:   Copyright (c)2013
 * Company:     BOCO 

 * @author:     chenbing 
 * @version:    1.0 
 */ 
public interface OutdoorCabinetMgr extends CommonGenericService<OutdoorCabinet> {
		

		/**
		 * 通过姓名获取OutdoorCabinet对象
		 * @param ocName
		 * @return List
		 */
		public List<OutdoorCabinet> getOCByName(String ocName);
		/**
		 * 资源导入-室外箱体资源
		 * @param formFile
		 * @param province
		 * @param specialty
		 * @param creatorId
		 * @return
		 */
		public ImportResult importOCFromFile( FormFile formFile,String province,String specialty,String creatorId,String osPath) throws Exception;  //资源的导入
		
}
