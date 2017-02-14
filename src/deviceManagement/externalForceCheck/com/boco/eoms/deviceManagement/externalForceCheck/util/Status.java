package com.boco.eoms.deviceManagement.externalForceCheck.util;

import java.util.Map;

import com.google.common.collect.Maps;

public class Status {
	//外力盯防计划状态
	private static Map<String, String> StatusMap = Maps.newHashMap(); 
	//外力盯防值班状态
	private static Map<String, String> StatusMapSublist = Maps.newHashMap(); 
	
	static{
//		外力盯防计划状态
		StatusMap.put("0", "新建状态");
		StatusMap.put("1", "计划执行中");
		StatusMap.put("2", "计划归档");
		StatusMap.put("-1", "计划终止或撤销");
//		外力盯防值班状态	
		StatusMapSublist.put("0", "待值班");
		StatusMapSublist.put("1", "值班中");
		StatusMapSublist.put("2", "值班结束");
	}

	public static Map<String, String> getStatusMap() {
		return StatusMap;
	}

	public static void setStatusMap(Map<String, String> statusMap) {
		StatusMap = statusMap;
	}

	public static Map<String, String> getStatusMapSublist() {
		return StatusMapSublist;
	}

	public static void setStatusMapSublist(Map<String, String> statusMapSublist) {
		StatusMapSublist = statusMapSublist;
	}
	
}
