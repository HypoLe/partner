package com.boco.eoms.aggregation.util;


public class AggregationType {

	/**
	 * 删除标示1为已删除
	 */
	public final static String DELETE_TRUE = new String("1");

	/**
	 * 删除标示0为未删除
	 */
	public final static String DELETE_FALSE = new String("0");

	/**
	 * 前台取值
	 */

	public final static java.util.HashMap<String, String> characterId2DELETEMap = new java.util.HashMap<String, String>();

	static {
		characterId2DELETEMap.put(DELETE_TRUE, "已删除");
		characterId2DELETEMap.put(DELETE_FALSE, "未删除");

	}
}
