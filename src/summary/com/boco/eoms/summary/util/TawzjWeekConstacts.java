package com.boco.eoms.summary.util;

/**
 * @author 龚玉峰
 * 
 */
public class TawzjWeekConstacts { 

	public static final String TAWZJWEEK_LIST = "TawzjWeekList";

	static public String[] CHECKSTATE = { "新建", "通过", "驳回", "审阅中", "归档", "抄送" };

	static public String[] WEEK = { "星期一", "星期二", "星期三", "星期四", "星期五", "星期六",
			"星期日" };

	// 月份数组
	static public String[] MONTH = { "00", "01", "02", "03", "04", "05", "06",
			"07", "08", "09", "10", "11", "12" };
	// 组长权限
	static public final String GROUPLEADER = "/summary/tawzjweek.do?method=Groupleader";
	//主任权限
	static public final String DIRECTOR = "/summary/tawzjweek.do?method=Director";
	
	

}
