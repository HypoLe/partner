package com.boco.eoms.partner.netresource.service;

import java.util.List;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.netresource.model.bs.AccessNetworkQuipment;

/** 
 * Description: 
 * Copyright:   Copyright (c)2013
 * Company:     BOCO 
 * @author:     chenbing 
 * @version:    1.0 

 */ 
public interface AccessNetworkQuipmentMgr extends CommonGenericService<AccessNetworkQuipment> {
	/**
		 * 通过姓名获取AccessNetworkQuipment对象
		 * @param deviceNumber
		 * @return List
		 */
		public List<AccessNetworkQuipment> getQuipmentByName(String deviceNumber);
		/**
		 * 同步接入网下 没有同步的设备
		 * @throws Exception
		 */
		public void synchronousAnQuitment() throws Exception;
	
}
