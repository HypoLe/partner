package com.boco.eoms.aggregation.service;


import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;

import com.boco.eoms.aggregation.model.Aggregation;
import com.boco.eoms.deviceManagement.common.service.CommonGenericService;


public interface AggregationService extends CommonGenericService<Aggregation> {
	public List<String> specialityDictList(String fatherDictId);

	public List<ListOrderedMap> allResultList(String userId);

	public List<ListOrderedMap> commendList(String userId, String commend);
}
