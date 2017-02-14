package com.boco.eoms.eva.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.boco.eoms.base.util.StaticMethod;

/**
 * 
 * <p>
 * Title:日期处理工具类
 * </p>
 * <p>
 * Description:日期处理工具类
 * </p>
 * <p>
 * Date:2008-12-9 下午07:43:42
 * </p>
 * 
 * @author 李秋野
 * @version 3.5.1
 * 
 */
public class DateTimeUtil {

	/**
	 * 由java.util.Date到java.sql.Date的类型转换
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date getSqlDate(java.util.Date date) {
		return new Date(date.getTime());
	}

	public static Date nowDate() {
		Calendar calendar = Calendar.getInstance();
		return getSqlDate(calendar.getTime());
	}

	/**
	 * 获得某一日期的后一天
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date getNextDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DATE);
		calendar.set(Calendar.DATE, day + 1);
		return getSqlDate(calendar.getTime());
	}

	/**
	 * 获得某一日期的前一天
	 * 
	 * @param date
	 * @return Date
	 */
	public static Date getPreviousDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DATE);
		calendar.set(Calendar.DATE, day - 1);
		return getSqlDate(calendar.getTime());
	}

	/**
	 * 获得某年某月第一天的日期
	 * 
	 * @param year
	 * @param month
	 * @return Date
	 */
	public static Date getFirstDayOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DATE, 1);
		return getSqlDate(calendar.getTime());
	}

	/**
	 * 获得某年某月最后一天的日期
	 * 
	 * @param year
	 * @param month
	 * @return Date
	 */
	public static Date getLastDayOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, 1);
		return getPreviousDate(getSqlDate(calendar.getTime()));
	}

	/**
	 * 由年月日构建java.sql.Date类型
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return Date
	 */
	public static Date buildDate(int year, int month, int date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, date);
		return getSqlDate(calendar.getTime());
	}

	/**
	 * 取得某月的天数
	 * 
	 * @param year
	 * @param month
	 * @return int
	 */
	public static int getDayCountOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, 0);
		return calendar.get(Calendar.DATE);
	}

	/**
	 * 获得某年某季度的最后一天的日期
	 * 
	 * @param year
	 * @param quarter
	 * @return Date
	 */
	public static Date getLastDayOfQuarter(int year, int quarter) {
		int month = 0;
		if (quarter > 4) {
			return null;
		} else {
			month = quarter * 3;
		}
		return getLastDayOfMonth(year, month);

	}

	/**
	 * 获得某年某季度的第一天的日期
	 * 
	 * @param year
	 * @param quarter
	 * @return Date
	 */
	public static Date getFirstDayOfQuarter(int year, int quarter) {
		int month = 0;
		if (quarter > 4) {
			return null;
		} else {
			month = (quarter - 1) * 3 + 1;
		}
		return getFirstDayOfMonth(year, month);
	}

	/**
	 * 获得某年的第一天的日期
	 * 
	 * @param year
	 * @return Date
	 */
	public static Date getFirstDayOfYear(int year) {
		return getFirstDayOfMonth(year, 1);
	}

	/**
	 * 获得某年的最后一天的日期
	 * 
	 * @param year
	 * @return Date
	 */
	public static Date getLastDayOfYear(int year) {
		return getLastDayOfMonth(year, 12);
	}

	/**
	 * String到java.sql.Date的类型转换
	 * 
	 * @param param
	 * @return Date
	 */
	public static java.sql.Date stringToDate(String param) {
		if (null == param) {
			return null;
		} else {
			java.util.Date date = null;
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				date = sdf.parse(param);
				return new Date(date.getTime());
			} catch (ParseException ex) {
				ex.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * java.sql.Date到String的类型转换
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToString(java.sql.Date date) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (date == null) {
			return null;
		} else {
			try {
				df = new SimpleDateFormat("yyyy-MM-dd");
				returnValue = df.format(date);
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
		}

		return (returnValue);
	}

	/**
	 * 得到当前系统日期（时间）
	 * 
	 * @param _dtFormat
	 * @return
	 */
	public static String getCurrentDateTime(String _dtFormat) {
		String currentdatetime = "";
		try {
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat dtFormat = new SimpleDateFormat(_dtFormat);
			currentdatetime = dtFormat.format(date);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
		return currentdatetime;
	}

	/**
	 * 取得当前日期所在周的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		return getSqlDate(c.getTime());
	}

	/**
	 * 取得当前日期所在周的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
		return getSqlDate(c.getTime());
	}
	//自然周期所用方法
	/**
	 * 取得时间段内所有月份任务标识字符串
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 
	public static List getMonthsForTimes(String startTime,String endTime) {
		List list = new ArrayList();
		int startYear = Integer.parseInt(startTime.substring(0, 4));
		int startMonth = Integer.parseInt(startTime.substring(5, 7));
		int endYear = Integer.parseInt(endTime.substring(0, 4));
		int endMonth = Integer.parseInt(endTime.substring(5, 7));
		String MonthsStr = "";
		for(;endYear-startYear>=0;startYear++,startMonth=1){
			for(int i=startMonth;(startYear!=endYear&&i<=12)||(startYear==endYear&&i<=endMonth);i++){
				if(i<10){
					list.add(startYear+"-0"+i+"-month");
				}else{
					list.add(startYear+"-"+i+"-month");
				}
			}
			
		}
		return list;
	}
	*/
	/**
	 * 取得时间段内所有年份任务标识字符串
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 
	public static List getYearsForTimes(String startTime,String endTime) {
		List list = new ArrayList();
		int startYear = Integer.parseInt(startTime.substring(0, 4));
		int endYear = Integer.parseInt(endTime.substring(0, 4));
		for(;endYear-startYear>=0;startYear++){
			list.add(startYear+"-1-year");
		}
		return list;
	}
	*/
	/**
	 * 取得时间段内所有半年任务标识字符串
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	
	public static List getHalfYearsForTimes(String startTime,String endTime) {
		List list = new ArrayList();
		int startYear = Integer.parseInt(startTime.substring(0, 4));
		int startMonth = Integer.parseInt(startTime.substring(5, 7));
		int endYear = Integer.parseInt(endTime.substring(0, 4));
		int endMonth = Integer.parseInt(endTime.substring(5, 7));
		int halfYear = 0;
		if (startMonth <= 6) {
			halfYear = 1;
		} else if (startMonth <= 12) {
			halfYear = 2;
		} 
		for(;endYear-startYear>=0;startYear++){
			for(;endYear-startYear>=0;startYear++,startMonth=1,halfYear=1){
				for(int i=startMonth,j=halfYear;(startYear!=endYear&&i<=12)||(startYear==endYear&&i<=endMonth);i=i+6,j++){
					list.add(startYear+"-"+j+"-halfYear");
				}
			}
		}
		return list;
	}
	 */
	/**
	 * 取得时间段内所有季度任务标识字符串
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	
	public static List getQuartersForTimes(String startTime,String endTime) {
		List list = new ArrayList();
		int startYear = Integer.parseInt(startTime.substring(0, 4));
		int startMonth = Integer.parseInt(startTime.substring(5, 7));
		int endYear = Integer.parseInt(endTime.substring(0, 4));
		int endMonth = Integer.parseInt(endTime.substring(5, 7));
		int quarter = 0;
		if (startMonth <= 3) {
			quarter = 1;
		} else if (startMonth <= 6) {
			quarter = 2;
		} else if (startMonth <= 9) {
			quarter = 3;
		} else if (startMonth <= 12) {
			quarter = 4;
		}
		for(;endYear-startYear>=0;startYear++){
			for(;endYear-startYear>=0;startYear++,startMonth=1,quarter=1){
				for(int i=startMonth,j=quarter;(startYear!=endYear&&i<=12)||(startYear==endYear&&i<=endMonth);i=i+3,j++){
					list.add(startYear+"-"+j+"-quarter");
				}
			}
		}
		return list;
	}
	 */
	/**
	 * 根据周期和起止时间取得时间段内所有任务标识字符串
	 * @param cycle
	 * @param startTime
	 * @param endTime
	 * @return
	 
	public static List getAllStrByTimes(String cycle,String startTime,String endTime) {
		List list =null;
		if("month".equals(cycle)){
			list = getMonthsForTimes(startTime,endTime);
		}else if("quarter".equals(cycle)){
			list = getQuartersForTimes(startTime,endTime);
		}else if("halfYear".equals(cycle)){
			list = getHalfYearsForTimes(startTime,endTime);
		}else if("year".equals(cycle)){
			list = getYearsForTimes(startTime,endTime);
		}
		return list;
	}
	*/
	/**
	 * 根据任务标识字符串得到页面显示字符串
	 * @param cycle
	 * @param startTime
	 * @param endTime
	 * @return
	 
	public static String getShowTimeStrByTime(String time) {
		String showTimeStr = "";
		String[] timeStr = time.split("-");
		String year = timeStr[0];
		String index = timeStr[1];
		String cycle = timeStr[2];
		if("month".equals(cycle)){
			showTimeStr = year+"年"+index+"月";
		}else if("quarter".equals(cycle)){
			showTimeStr = year+"年"+index+"季度";
		}else if("halfYear".equals(cycle)){
			if("1".equals(index)){
				showTimeStr = year+"年上半年";
			}else{
				showTimeStr = year+"年下半年";
			}
		}else if("year".equals(cycle)){
			showTimeStr = year+"年";
		}
		return showTimeStr;
	}
	*/
	//根据实际时间计算周期
	/**
	 * 取得时间段内所有月份任务标识字符串
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	*/
	 
	public static List getMonthsForTimes(String startTime,String endTime) {
		List list = new ArrayList();
		String evaTime ="";
//		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.util.Date startDate = null;
		java.util.Date endDate = null;
		try { 
			startDate = dateFormat.parse(startTime);
			endDate = dateFormat.parse(endTime);
		}catch(Exception e){
			e.printStackTrace();
		}
		
        Calendar startCalender = Calendar.getInstance();
        Calendar endCalender = Calendar.getInstance();
        startCalender.setTime(startDate);
        endCalender.setTime(endDate);
        startCalender.add(Calendar.MONTH, 1);
        endCalender.add(Calendar.MONTH, 1);
		for(int i=1;startCalender.compareTo(endCalender)<=0;i++,startCalender.add(Calendar.MONTH, 1)){
			Calendar evaCalender = Calendar.getInstance();
	        evaCalender.setTime(startDate);
	        evaCalender.add(Calendar.MONTH, i);
			evaTime = dateFormat.format(evaCalender.getTime());
			list.add(evaTime);
		}
		return list;
	}
	/**
	 * 取得时间段内所有年份任务标识字符串
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	*/
	 
	public static List getYearsForTimes(String startTime,String endTime) {
		List list = new ArrayList();
		String evaTime ="";
		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.util.Date startDate = null;
		java.util.Date endDate = null;
		try { 
			startDate = dateFormat.parse(startTime);
			endDate = dateFormat.parse(endTime);
		}catch(Exception e){
			e.printStackTrace();
		}
        Calendar startCalender = Calendar.getInstance();
        Calendar endCalender = Calendar.getInstance();
        startCalender.setTime(startDate);
        endCalender.setTime(endDate);
        startCalender.add(Calendar.MONTH, 12);
        endCalender.add(Calendar.MONTH, 12);
		for(int i =0;startCalender.compareTo(endCalender)<=0;startCalender.add(Calendar.MONTH, 12)){
			evaTime = dateFormat.format(startCalender.getTime());
			list.add(evaTime);
		}
		return list;
	}
	/**
	 * 取得时间段内所有半年任务标识字符串
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	
	public static List getHalfYearsForTimes(String startTime,String endTime) {
		List list = new ArrayList();
		String evaTime ="";
		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.util.Date startDate = null;
		java.util.Date endDate = null;
		try { 
			startDate = dateFormat.parse(startTime);
			endDate = dateFormat.parse(endTime);
		}catch(Exception e){
			e.printStackTrace();
		}
        Calendar startCalender = Calendar.getInstance();
        Calendar endCalender = Calendar.getInstance();
        startCalender.setTime(startDate);
        endCalender.setTime(endDate);
        startCalender.add(Calendar.MONTH, 6);
        endCalender.add(Calendar.MONTH, 6);
		for(int i =0;startCalender.compareTo(endCalender)<=0;startCalender.add(Calendar.MONTH, 6)){
			evaTime = dateFormat.format(startCalender.getTime());
			list.add(evaTime);
		}
		return list;
	}
	/**
	 * 取得时间段内所有季度任务标识字符串
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static List getQuartersForTimes(String startTime,String endTime) {
		List list = new ArrayList();
		String evaTime ="";
//		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.util.Date startDate = null;
		java.util.Date endDate = null;
		try { 
			startDate = dateFormat.parse(startTime);
			endDate = dateFormat.parse(endTime);
		}catch(Exception e){
			e.printStackTrace();
		}
        Calendar startCalender = Calendar.getInstance();
        Calendar endCalender = Calendar.getInstance();
        startCalender.setTime(startDate);
        endCalender.setTime(endDate);
        startCalender.add(Calendar.MONTH, 3);
        endCalender.add(Calendar.MONTH, 3);
		for(int i =0;startCalender.compareTo(endCalender)<=0;startCalender.add(Calendar.MONTH, 3)){
			evaTime = dateFormat.format(startCalender.getTime());
			list.add(evaTime);
		}
		return list;
	}
	/**
	 * 根据周期和起止时间取得时间段内所有任务标识字符串
	 * @param cycle
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static List getAllStrByTimes(String cycle,String startTime,String endTime) {
		List list = new ArrayList();
		if("month".equals(cycle)){
			list = getMonthsForTimes(startTime,endTime);
		}else if("quarter".equals(cycle)){
			list = getQuartersForTimes(startTime,endTime);
		}else if("halfYear".equals(cycle)){
			list = getHalfYearsForTimes(startTime,endTime);
		}else if("year".equals(cycle)){
			list = getYearsForTimes(startTime,endTime);
		}else if ("times".equals(cycle)){
			java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
			java.util.Date evaDate = null;
			try {
				evaDate = dateFormat.parse(endTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(evaDate);
			int day = calendar.get(Calendar.DATE);
			calendar.set(Calendar.DATE, day + 1);
			list.add(dateFormat.format(calendar.getTime()));
		}
		return list;
	}
	/**
	 * 根据周期和起始时间和执行时间得到该执行实现可以执行的考核
	 * @param cycle
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static List getAllStrByTimesForEva(String cycle,String startTime,String evaTime) {
		List list =null;
		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date evaDate = null;
		try { 
			evaDate = dateFormat.parse(evaTime);
		}catch(Exception e){
			e.printStackTrace();
		}
        Calendar evaCalender = Calendar.getInstance();
        evaCalender.setTime(evaDate);		
		if("month".equals(cycle)){
			evaCalender.add(Calendar.MONTH, -1);
			evaTime = dateFormat.format(evaCalender.getTime());
			list = getMonthsForTimes(startTime,evaTime);
		}else if("quarter".equals(cycle)){
			evaCalender.add(Calendar.MONTH, -3);
			evaTime = dateFormat.format(evaCalender.getTime());
			list = getQuartersForTimes(startTime,evaTime);
		}else if("halfYear".equals(cycle)){
			evaCalender.add(Calendar.MONTH, -6);
			evaTime = dateFormat.format(evaCalender.getTime());
			list = getHalfYearsForTimes(startTime,evaTime);
		}else if("year".equals(cycle)){
			evaCalender.add(Calendar.MONTH, -12);
			evaTime = dateFormat.format(evaCalender.getTime());
			list = getYearsForTimes(startTime,evaTime);
		}
		return list;
	}
	/**
	 * 根据任务标识字符串得到页面显示字符串
	 * @param time
	 * @return
	 */
	public static String getShowTimeStrByTime(String time) {
		String showTimeStr = "";
		String[] timeStr = time.split("-");
		String year = timeStr[0];
		String index = timeStr[1];
		String cycle = timeStr[2];
		if("month".equals(cycle)){
			showTimeStr = year+"年"+index+"月";
		}else if("quarter".equals(cycle)){
			showTimeStr = year+"年"+index+"季度";
		}else if("halfYear".equals(cycle)){
			if("1".equals(index)){
				showTimeStr = year+"年上半年";
			}else{
				showTimeStr = year+"年下半年";
			}
		}else if("year".equals(cycle)){
			showTimeStr = year+"年";
		}
		return showTimeStr;
	}
	/**
	 * 根据任务标识字符串得到页面显示字符串
	 * @param time
	 * @return
	 */
	public static String getKpiScope(String kpiStartTime,String kpiEndTime, String kpiCycle,String templateStartTime,String templateCycle,String time) {
		String scopeStr = "";
		if("times".equals(kpiCycle)||"times".equals(templateCycle)){
			scopeStr = "1-1";
			return scopeStr;
		}
		int timeNum = Integer.parseInt(time);
		int kpiCycleNum =0;
		if("month".equals(kpiCycle)){
			kpiCycleNum = 1;
		}else if("quarter".equals(kpiCycle)){
			kpiCycleNum = 3;
		}else if("halfYear".equals(kpiCycle)){
			kpiCycleNum = 6;
		}else if("year".equals(kpiCycle)){
			kpiCycleNum = 12;
		}
		int templateCycleNum =0;
		if("month".equals(templateCycle)){
			templateCycleNum = 1;
		}else if("quarter".equals(templateCycle)){
			templateCycleNum = 3;
		}else if("halfYear".equals(templateCycle)){
			templateCycleNum = 6;
		}else if("year".equals(templateCycle)){
			templateCycleNum = 12;
		}
		int startTimeNum = (((templateCycleNum*timeNum)/kpiCycleNum)-1)*(kpiCycleNum/templateCycleNum)+1;
		int endTimeNum = ((templateCycleNum*timeNum)/kpiCycleNum)*(kpiCycleNum/templateCycleNum);
		if(startTimeNum<0){
			startTimeNum = 0;
		}
		scopeStr =startTimeNum+"-"+endTimeNum;
		//判断没有到考核时间的
		String localTime = StaticMethod.getLocalString();
		
		if(StaticMethod.getTimeDistance(getTimesByCycle(kpiCycle,kpiStartTime,1)+" 00:00:00", getTimesByCycle(templateCycle,templateStartTime,timeNum)+" 00:00:00")<0){
			scopeStr = "0-0";
		}
		
		System.out.println(scopeStr);

		return scopeStr;
	}
	/**
	 * 将时间增加或减少n个周期
	 * @param cycle
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static String getTimesByCycle(String cycle,String time,int cycleNum) {
		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		String lastTime = "";
		try { 
			date = dateFormat.parse(time);
		}catch(Exception e){
			e.printStackTrace();
		}
        Calendar calender = Calendar.getInstance();
        calender.setTime(date);		
		if("month".equals(cycle)){
			calender.add(Calendar.MONTH, 1*cycleNum);
			lastTime = dateFormat.format(calender.getTime());
		}else if("quarter".equals(cycle)){
			calender.add(Calendar.MONTH, 3*cycleNum);
			lastTime = dateFormat.format(calender.getTime());
		}else if("halfYear".equals(cycle)){
			calender.add(Calendar.MONTH, 6*cycleNum);
			lastTime = dateFormat.format(calender.getTime());
		}else if("year".equals(cycle)){
			calender.add(Calendar.MONTH, 12*cycleNum);
			lastTime = dateFormat.format(calender.getTime());
		}
		return lastTime;
	}
	
	/**
	 * 时间处理方法
	 * 
	 * @param str
	 * @return
	 */
	public static String timestamp2String(Timestamp date) {
		String timeStr = StaticMethod.getTimestampString(date);
		int s = timeStr.indexOf(" ");
		return timeStr.substring(0, s);
	}	
	public static void main(String[] args) {
		List list =getMonthsForTimes("2008-05-30 22:33:44","2010-01-30 22:33:44");
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i));
		}
	}
	
}
