package com.boco.eoms.partner.netresource.service;

import java.util.List;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.netresource.model.bs.BsBtAp;
import com.boco.eoms.partner.netresource.util.ImportResult;

/** 
 * Description: 
 * Copyright:   Copyright (c)2013
 * Company:     BOCO 

 * @author:     chenbing 
 * @version:    1.0 
 */ 
public interface BsBtApMgr extends CommonGenericService<BsBtAp> {
		
	/**通过 apName 判断是否存在
		 * 
		 * @return int
		 */
		public  int isExitByName(String apName);
		/**
		 * 通过姓名获取BsBtAp对象
		 * @param apName
		 * @return List
		 */
		public List<BsBtAp> getApName(String apName);
		/**
		 * 资源导入-机房AP资源
		 * @param formFile
		 * @param province
		 * @param specialty
		 * @param creatorId
		 * @return
		 */
		public ImportResult importBsBtApFromFile( FormFile formFile,String province,String specialty,String creatorId,String osPath) throws Exception;  //资源的导入
		
		
		
}
