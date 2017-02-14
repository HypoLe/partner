package com.boco.eoms.deviceManagement.busi.protectline.util;

import java.util.Map;

import com.google.common.collect.Maps;

public class LineMap {
	
	public final static String FINISHED="1";
	public final static String NOFINISHED="0";
	
	//验收状态
	public final static Map<String, String> statusMap = Maps.newHashMap();
	static {
		statusMap.put(FINISHED, "已经完成");
		statusMap.put(NOFINISHED, "尚未完成");
	}
	public final static String CHECKYES="1";
	public final static String CHECKNO="0";
	
	//验收状态
	public final static Map<String, String> checkMap = Maps.newHashMap();
	static {
		checkMap.put(CHECKYES, "验收通过");
		checkMap.put(CHECKNO, "验收未通过");
	}
}
