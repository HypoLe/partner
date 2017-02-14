package com.boco.eoms.partner.deviceAssess.mgr;

import java.util.List;
import java.util.Map;
import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.deviceAssess.model.FacilityQuantity;

/**

 * 
 */
 public interface FacilityQuantityMgr  extends
                           CommonGenericService<FacilityQuantity>{
 
	
	 public List getMapObjList(String nameOrId);//得到装载页面字段信息的List
	 
	 
	 
	 /**
		 * 根据页面上信息存储设备量信息
		 * @param map 从页面取得
	*/
	 public void saveFacilityQuantity(Map map);
    
	
			
}