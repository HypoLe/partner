package com.boco.eoms.partner.assess.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

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
 * Date:Nov 29, 2010 9:46:47 AM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
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


	/**
	 * 根据月份得到当前季度
	 * 
	 * @param date
	 * @return
	 */
	public static Integer getQuarter(int month) {
		int quarter  = 1 ;
		if(month<=3){
			quarter  = 1 ;
		}else if (month<=6) {
			quarter  = 2 ;
		}else if (month<=9) {
			quarter  = 3 ;
		}else if (month<=12) {
			quarter  = 4 ;
		}
		return Integer.valueOf(quarter);
	}	
	
	/**
	 * 根据周期获得上一周期内的第一天
	 * 
	 * @param cycle
	 *            周期
	 * @return
	 */
	public static String getStartTimeByCycleAfter(String cycle) {
		String startTime = StaticMethod.getLocalString();
		Date date = null;
		if (AssConstants.CYCLE_YEAR.equals(cycle)) {
			int year = Integer.parseInt(startTime.substring(0, 4));
			date = DateTimeUtil.getFirstDayOfYear(year-1);
		} else if (AssConstants.CYCLE_QUARTER.equals(cycle)) {
			int year = Integer.parseInt(startTime.substring(0, 4));
			int month = Integer.parseInt(startTime.substring(5, 7));
			int quarter = 0;
			if (month <= 3) {
				year = year - 1 ;
				quarter = 4;
			} else if (month <= 6) {
				quarter = 1;
			} else if (month <= 9) {
				quarter = 2;
			} else if (month <= 12) {
				quarter = 3;
			}
			date = DateTimeUtil.getFirstDayOfQuarter(year, quarter);
		} else if (AssConstants.CYCLE_MONTH.equals(cycle)) {
			int year = Integer.parseInt(startTime.substring(0, 4));
			int month = Integer.parseInt(startTime.substring(5, 7));
			date = DateTimeUtil.getFirstDayOfMonth(year, month-1);
		} else if (AssConstants.CYCLE_WEEK.equals(cycle)) {
			Calendar calendar = new GregorianCalendar();
			int day = calendar.get(Calendar.DATE);
			calendar.set(Calendar.DATE, day - 7);
			calendar.setFirstDayOfWeek(Calendar.MONDAY);
			calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
			date = getSqlDate(calendar.getTime());
		}
		return dateToString(date)+" 00:00:00";
	}

	/**
	 * 根据周期获得本一周期内的第一天
	 * 
	 * @param cycle
	 * @return
	 */
	public static String getEndTimeByCycleAfter(String cycle) {
		String startTime = StaticMethod.getLocalString();
		Date date = null;		
		if (AssConstants.CYCLE_YEAR.equals(cycle)) {
			int year = Integer.parseInt(startTime.substring(0, 4));
			date = DateTimeUtil.getFirstDayOfYear(year);
		} else if (AssConstants.CYCLE_QUARTER.equals(cycle)) {
			int year = Integer.parseInt(startTime.substring(0, 4));
			int month = Integer.parseInt(startTime.substring(5, 7));
			int quarter = 0;
			if (month <= 3) {
				quarter = 1;
			} else if (month <= 6) {
				quarter = 2;
			} else if (month <= 9) {
				quarter = 3;
			} else if (month <= 12) {
				quarter = 4;
			}
			date = DateTimeUtil.getFirstDayOfQuarter(year, quarter);
		} else if (AssConstants.CYCLE_MONTH.equals(cycle)) {
			int year = Integer.parseInt(startTime.substring(0, 4));
			int month = Integer.parseInt(startTime.substring(5, 7));
			date = DateTimeUtil.getFirstDayOfMonth(year, month);
		} else if (AssConstants.CYCLE_WEEK.equals(cycle)) {
			Calendar calendar = new GregorianCalendar();
			calendar.setFirstDayOfWeek(Calendar.MONDAY);
			calendar.setTime(StaticMethod.getLocalTime());
			calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
			date = getSqlDate(calendar.getTime());
		}		
		return dateToString(date)+" 00:00:00";
	}	
	
}
