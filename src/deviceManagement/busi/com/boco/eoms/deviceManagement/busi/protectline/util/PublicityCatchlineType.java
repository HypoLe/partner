package com.boco.eoms.deviceManagement.busi.protectline.util;

public class PublicityCatchlineType {

	/**
	 * 删除标示1为已删除
	 */
	public final static String DELETE_TRUE = new String("1");
	
	/**
	 * 删除标示0为未删除
	 */
	public final static String DELETE_FALSE = new String("0");
	
	
	/**
	 * 有效标示0为草稿
	 */
	public final static String AD_TYPE0 = new String("0");	
	/**
	 * 有效标示1为待审核
	 */
	public final static String AD_TYPE1 = new String("1");
	/**
	 * 有效标示2为通过审核
	 */
	public final static String AD_TYPE2 = new String("2");
	/**
	 * 有效标示3为驳回
	 */
	public final static String AD_TYPE3 = new String("3");
	

	
	/**
	 * 前台取值
	 */

	public final static java.util.HashMap<String,String> characterId2TYPEMap = new java.util.HashMap<String,String>();
	
	static{
		characterId2TYPEMap.put(AD_TYPE0, "草稿");
		characterId2TYPEMap.put(AD_TYPE1, "待审核");
		characterId2TYPEMap.put(AD_TYPE2, "通过审核");
		characterId2TYPEMap.put(AD_TYPE3, "驳回");
	}
	public final static java.util.HashMap<String,String> characterId2DELETEMap = new java.util.HashMap<String,String>();
	
	static{
		characterId2DELETEMap.put(DELETE_TRUE, "已删除");
		characterId2DELETEMap.put(DELETE_FALSE, "未删除");

	}
}
