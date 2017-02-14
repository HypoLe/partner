package com.boco.eoms.partner.deviceAssess.mgr;



import java.util.List;
import java.util.Map;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.deviceAssess.util.MapObj;

public interface AllStatisticMgr extends
               CommonGenericService<MapObj> {
	public List<MapObj>  calculateList(String special,String factoryDictId,String startTime,String endTime);
	public List<String> factorySearchList(String factoryDictId );
	public List<String> finallyScore(List<MapObj> list,List<String> factorySearchList);
	public List<String> finallyRank(List<String> finallyScore,List<String> factorySearchList);
}	
 