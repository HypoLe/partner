package com.boco.eoms.summary.util;

import java.util.Calendar;

/**
 * @author 龚玉峰
 *
 */
public class TawzjUtil {
	
	/**
	 * @param year
	 * @param week
	 * @param day
	 * @return
	 */
	public static String GetWeekSAndE(int year,int week,int day){//返回周开始日期和结束日期 用逗号隔开 
		String sdate=""; 
		String edate=""; 
		Calendar cal = Calendar.getInstance(); 
		cal.clear(); 
		cal.set(Calendar.YEAR, year); 
		cal.set(Calendar.WEEK_OF_YEAR, week); 
		cal.add(Calendar.DAY_OF_YEAR, day); 
		sdate=cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DATE);

		cal.clear(); 
		cal.set(Calendar.YEAR, year); 
		cal.set(Calendar.WEEK_OF_YEAR, week); 
		cal.add(Calendar.WEEK_OF_YEAR, 1); 
		edate=cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.DATE);

		return sdate; 
		}

	/**
	 * @param week
	 * @return
	 */
	public static String GetWeekStr(int week) {
		String weekStr = "";
		switch (week) {
		case 1:
			weekStr = "星期一";
			break;
		case 2:
			weekStr = "星期二";
			break;
		case 3:
			weekStr = "星期三";
			break;
		case 4:
			weekStr = "星期四";
			break;
		case 5:
			weekStr = "星期五";
			break;
		case 6:
			weekStr = "星期六";
			break;
		default:
			weekStr = "星期日";
			break;
		}
		return weekStr;
	}
}
