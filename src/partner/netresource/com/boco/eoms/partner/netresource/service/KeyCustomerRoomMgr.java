package com.boco.eoms.partner.netresource.service;

import java.util.List;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.netresource.model.bs.KeyCustomerRoom;
import com.boco.eoms.partner.netresource.util.ImportResult;

/** 
 * Description: 
 * Copyright:   Copyright (c)2013
 * Company:     BOCO 

 * @author:     chenbing 
 * @version:    1.0 
 */ 
public interface KeyCustomerRoomMgr extends CommonGenericService<KeyCustomerRoom> {
		
		/**通过 keyCustomerRoomName 判断是否存在
		 * 
		 * @return int
		 */
		public  int isExitByName(String keyCustomerRoomName);
		/**
		 * 通过姓名获取KeyCustomerRoom对象
		 * @param apName
		 * @return List
		 */
		public List<KeyCustomerRoom> getKeyCustomerRoomName(String keyCustomerRoomName);
		/**
		 * 资源导入-重点客户机房资源
		 * @param formFile
		 * @param province
		 * @param specialty
		 * @param creatorId
		 * @return
		 */
		public ImportResult importKeyCustomerRoomFromFile( FormFile formFile,String province,String specialty,String creatorId,String osPath) throws Exception;  //资源的导入
		
		
		
}
