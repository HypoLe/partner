package com.boco.eoms.partner.process.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	/** 
	* 获得本季度第一天 
	* 
	* @param month 
	* @return 
	*/ 
	/**
	 * @param years
	 * @param month
	 * @return
	 */
	public static String getThisSeasonFirstTime(String years,int month) { 
	int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } }; 
	int season = 1; 
	if (month >= 1 && month <= 3) { 
	season = 1; 
	} 
	if (month >= 4 && month <= 6) { 
	season = 2; 
	} 
	if (month >= 7 && month <= 9) { 
	season = 3; 
	} 
	if (month >= 10 && month <= 12) { 
	season = 4; 
	} 
	int start_month = array[season - 1][0]; 
	int end_month = array[season - 1][2]; 

	 
	int years_value = Integer.parseInt(years); 

	int start_days = 1;// years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month); 
	@SuppressWarnings("unused") 
//	int end_days = getLastDayOfMonth(years_value, end_month); 
	int start_month_len =(""+start_month).length();
	
	String monthstr =(""+start_month).length()==1?"0"+start_month:""+start_month;
	
	String seasonDate = years_value + "-" + monthstr + "-0" + start_days; 
	return seasonDate; 

	} 

	/** 
	* 获得本季度最后一天 
	* 
	* @param month 
	* @return 
	*/ 
	@SuppressWarnings("unused") 
	public static String getThisSeasonFinallyTime(String years,int month) { 
	int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } }; 
	int season = 1; 
	if (month >= 1 && month <= 3) { 
	season = 1; 
	} 
	if (month >= 4 && month <= 6) { 
	season = 2; 
	} 
	if (month >= 7 && month <= 9) { 
	season = 3; 
	} 
	if (month >= 10 && month <= 12) { 
	season = 4; 
	} 
	int start_month = array[season - 1][0]; 
	int end_month = array[season - 1][2]; 


	int years_value = Integer.parseInt(years); 

	int start_days = 1;// years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month); 
	int end_days = getLastDayOfMonth(years_value, end_month); 
	
	String monthstr =(""+end_month).length()==1?"0"+end_month:""+end_month;

	String seasonDate = years_value + "-" + monthstr + "-" + end_days; 
	return seasonDate; 

	} 

	/** 
	* 获取某年某月的最后一天 
	* 
	* @param year 
	*            年 
	* @param month 
	*            月 
	* @return 最后一天 
	*/ 
	private static int getLastDayOfMonth(int year, int month) { 
	if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 
	|| month == 10 || month == 12) { 
	return 31; 
	} 
	if (month == 4 || month == 6 || month == 9 || month == 11) { 
	return 30; 
	} 
	if (month == 2) { 
	if (isLeapYear(year)) { 
	return 29; 
	} else { 
	return 28; 
	} 
	} 
	return 0; 
	} 

	/** 
	* 是否闰年 
	* 
	* @param year 
	*            年 
	* @return 
	*/ 
	public static boolean isLeapYear(int year) { 
	return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0); 
	} 
	
	public static Date getNextDay(Date date) {		
		Calendar calendar = Calendar.getInstance();	
		calendar.setTime(date);	
		calendar.add(Calendar.DAY_OF_MONTH, -1);	
		date = calendar.getTime();	
		return date;	
	}
	
	/**
	 * 通过月份转换对应的季度
	 * @param month
	 * @return
	 */
	public static String getQuarterByMonth(String month) {
		String quarterStr = "";
		if ("01".equals(month) || "02".equals(month) || "03".equals(month)) {
			quarterStr = "01";
		} else if ("04".equals(month) || "05".equals(month)
				|| "06".equals(month)) {
			quarterStr = "04";
		} else if ("07".equals(month) || "08".equals(month)
				|| "09".equals(month)) {
			quarterStr = "07";
		} else if ("10".equals(month) || "11".equals(month)
				|| "12".equals(month)) {
			quarterStr = "10";
		}
		return quarterStr;
	}
	
	/**
	 * 通过月份转换对应的季度
	 * @param month
	 * @return
	 */
	public static String getQuarterByMonthTwo(String month) {
		String quarterStr = "";
		if ("1".equals(month) || "2".equals(month) || "3".equals(month)) {
			quarterStr = "01";
		} else if ("4".equals(month) || "5".equals(month)
				|| "6".equals(month)) {
			quarterStr = "04";
		} else if ("7".equals(month) || "8".equals(month)
				|| "9".equals(month)) {
			quarterStr = "07";
		} else if ("10".equals(month) || "11".equals(month)
				|| "12".equals(month)) {
			quarterStr = "10";
		}
		return quarterStr;
	}
	
	/**
	 * 判断月在季度中是第几个月
	 * 
	 * @param quarter
	 * @param month
	 * @return
	 */
	public static String getIndexMonthByQuarter(String quarter,String month){
		String index="";
		if("01".equals(quarter)){
			if("1".equals(month)){
				index="one";
			}else if("2".equals(month)){
				index="two";
			}else if("3".equals(month)){
				index="three";
			}
		}else if("04".equals(quarter)){
			if("4".equals(month)){
				index="one";
			}else if("5".equals(month)){
				index="two";
			}else if("6".equals(month)){
				index="three";
			}
		}else if("07".equals(quarter)){
			if("7".equals(month)){
				index="one";
			}else if("8".equals(month)){
				index="two";
			}else if("9".equals(month)){
				index="three";
			}
		}else if("10".equals(quarter)){
			if("10".equals(month)){
				index="one";
			}else if("11".equals(month)){
				index="two";
			}else if("12".equals(month)){
				index="three";
			}
		}
		
		return index;
	}
	
	public static String getYearAndMonthByQuarter(String year,String quarter,String index){
		String ym="";
		if("01".equals(quarter)){
			if("one".equals(index)){
				ym=year+"-01";
			}else if("two".equals(index)){
				ym=year+"-02";
			}else if("three".equals(index)){
				ym=year+"-03";
			}
		}else if("04".equals(quarter)){
			if("one".equals(index)){
				ym=year+"-04";
			}else if("two".equals(index)){
				ym=year+"-05";
			}else if("three".equals(index)){
				ym=year+"-06";
			}
		}else if("07".equals(quarter)){
			if("one".equals(index)){
				ym=year+"-07";
			}else if("two".equals(index)){
				ym=year+"-08";
			}else if("three".equals(index)){
				ym=year+"-09";
			}
		}else if("10".equals(quarter)){
			if("one".equals(index)){
				ym=year+"-10";
			}else if("two".equals(index)){
				ym=year+"-11";
			}else if("three".equals(index)){
				ym=year+"-12";
			}
		}
		
		return ym;
	}
	
	public static String getStartTimeOfQuarter(String budgetYear,String quarter){
		String startTime="";
		if("01".equals(quarter)){	//第一个季度
			startTime=budgetYear+"-01-01 00:00:00";
		}else if("04".equals(quarter)){	//第二个季度
			startTime=budgetYear+"-04-01 00:00:00";
		}else if("07".equals(quarter)){	//第三个季度
			startTime=budgetYear+"-07-01 00:00:00";
		}else if("10".equals(quarter)){	//第四个季度
			startTime=budgetYear+"-10-01 00:00:00";
		}
		
		return startTime;
	}
}
