package com.boco.eoms.partner.eva.util;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
public class PnrEvaDateUtil {

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
	public static Date stringToDate(String param) {
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
	public static String dateToString(Date date) {
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
	 * @see 得到当前时刻的时间字符串
	 * @return String para的标准时间格式的串,例如：返回'2003-08-09 16:00:00'
	 */
	public static String getLocalString() {
		java.util.Date currentDate = new java.util.Date();
		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String date = dateFormat.format(currentDate);

		return date;
	}

	/**
	 * 时间处理方法
	 * 
	 * @param disday
	 * @return
	 */
	public static Timestamp getTimestamp(int disday) {
		Calendar c;
		c = Calendar.getInstance();
		long realtime = c.getTimeInMillis();
		realtime += 86400000 * disday;
		return new Timestamp(realtime);
	}
	/**
	 * 将Timestamp类型转换成String
	 * 
	 * @param disday
	 * @return
	 */
	public static String getTimestampString(Timestamp sta) {
		String ret = "";
		try {
			String str = sta.toString();
			ret = str.substring(0, str.lastIndexOf('.'));
		} catch (Exception e) {
			ret = "";
		}
		return ret;
	}
	/**
	 * 将String类型转换成Timestamp
	 * 
	 * @param str
	 * @return
	 */
	public static Timestamp getTimestamp(String str) {
		Timestamp ret = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");

			Date date = dateFormat.parse(str);
			long datelong = date.getTime();
			ret = new Timestamp(datelong);

		} catch (Exception e) {
		}
		return ret;
	}
	/**
	 * 得到当前时间
	 * 
	 * @param disday
	 * @return
	 */
	public static Timestamp getTimestamp() {
		return getTimestamp(0);
	}
	
	/**
	 * Date类型转换为String类型（yyyy-MM-dd HH:mm:ss）
	 * @param date
	 * @return
	 */
	
	public static String date2String(Date date) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (date == null) {
			return "";
		} else {
			try {
				df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				returnValue = df.format(date);
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
		}
		return (returnValue);
	}
	
}
