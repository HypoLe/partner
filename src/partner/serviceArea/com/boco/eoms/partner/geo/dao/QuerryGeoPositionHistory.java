package com.boco.eoms.partner.geo.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.geo.model.ResourceGeoInfo;

public interface QuerryGeoPositionHistory {

	public List<ResourceGeoInfo> querryGeoHistoryGeoInfoList(Map<String,String> querryMap);
	public List<ResourceGeoInfo> querryGeoHistoryGeoInfo(Map<String,String> querryMap);
	
}
