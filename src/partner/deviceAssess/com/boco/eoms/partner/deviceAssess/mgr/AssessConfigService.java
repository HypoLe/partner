package com.boco.eoms.partner.deviceAssess.mgr;

import java.util.List;
import java.util.Map;

import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.deviceAssess.model.AssessConfig;

public interface AssessConfigService extends CommonGenericService<AssessConfig>{
	public  Map  scoreMap(List factoryList,Map<String,Integer> quantityMap,String deviceType,String indicatorName,String id,String startTime,String endTime);
	public List creatTableList(List<List> tabelList,List factoryList, Map<String,Integer> quantityMap,String deviceType,String indicatorType,String startTime,String endTime);
	public Map resultMap(String specilId,String startTime,String endTime ,String...strings );
}
