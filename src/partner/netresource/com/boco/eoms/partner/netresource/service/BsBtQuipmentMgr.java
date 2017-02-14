package com.boco.eoms.partner.netresource.service;

import java.util.List;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.netresource.model.bs.BsBtQuipment;

/** 
 * Description: 
 * Copyright:   Copyright (c)2013
 * Company:     BOCO 
 * @author:     chenbing 
 * @version:    1.0 

 */ 
public interface BsBtQuipmentMgr extends CommonGenericService<BsBtQuipment> {
	/**
		 * 通过姓名获取BsbtQuipment对象
		 * @param deviceNumber
		 * @return List
		 */
		public List<BsBtQuipment> getQuipmentByName(String deviceNumber);
		/**
		 * 用于同步功能 设备关联表
		 * 1 索取所有的数据 
		 * 2 mapping 中查看没有关系
		 */
		public void synchronousBsBtQuitment() throws Exception;
	
}
