package com.boco.eoms.partner.netresource.service;

import java.util.List;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.netresource.model.bs.BsBtResource;
import com.boco.eoms.partner.netresource.util.ImportResult;

/** 
 * Description: 
 * Copyright:   Copyright (c)2013
 * Company:     BOCO 

 * @author:     chenbing 
 * @version:    1.0 
 */ 
public interface BsBtConfigMgr extends CommonGenericService<BsBtResource> {
		
	/**通过 bsbtName 判断是否存在
		 * 
		 * @return int
		 */
		public  int isExitByName(String bsbtName);
		/**
		 * 通过姓名获取SdBsBtResource对象
		 * @param bsbtName
		 * @return List
		 */
		public List<BsBtResource> getBsBtByName(String bsbtName);
		/**
		 * 资源导入-基站机房资源
		 * @param formFile
		 * @param province
		 * @param specialty
		 * @param creatorId
		 * @return
		 * @throws Exception importBsBtEqFile
		 */
		public ImportResult importBsBtFromFile( FormFile formFile,String province,String specialty,String creatorId,String osPath) throws Exception;  //资源的导入
		
		/**
		 * 资源导入-基站机房资源--设备
		 * @param formFile
		 * @param province
		 * @param specialty
		 * @param creatorId
		 * @return
		 * @throws Exception 
		 */
		public ImportResult importBsBtEqFile( FormFile formFile,String province,String specialty,String creatorId,String osPath) throws Exception;  //资源的导入
		
		//public boolean judgeName();
}
