package com.boco.eoms.deviceManagement.busi.protectline.util;

import java.util.Map;

import com.google.common.collect.Maps;

public class ConstructionMap {

	/**草稿*/
	public final static String STATUS_0 = "0";
	/**待审核*/
	public final static String STATUS_1 = "1";
	/**审核通过*/
	public final static String STATUS_2 = "2";
	/**驳回*/
	public final static String STATUS_3 = "3";
	
	public final static String ACTION_0 = "0";
	public final static String ACTION_1 = "1";
	public final static String ACTION_2 = "2";
	public final static String ACTION_3 = "3";
	public final static String ACTION_4 = "4";
	
	
	
	private static Map<String, String> statusMap = Maps.newHashMap();
	private static Map<String, String> actionMap = Maps.newHashMap();

	public static Map<String, String> getActionMap() {
		return actionMap;
	}
	
	public static Map<String,String> getStatusMap() {
		return statusMap;
	}

	static {
		statusMap.put(STATUS_0, "草稿");
		statusMap.put(STATUS_1, "待审核");
		statusMap.put(STATUS_2, "审核通过");
		statusMap.put(STATUS_3, "驳回");
	}
	static {
	}
}
