package com.boco.eoms.netresource.line.util;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class LinesCommonsUtil {
	
	/**
	 * 
	 * 转化Data类型的值为eoms标准时间，例如2011-01-01 12:12:12
	 * 
	 * @author Steve
	 * @since August,2011
	 * @param date
	 * @return
	 */
	public static String toEomsStandardDate(Date date) {
		return new DateTime(date).toString("yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 
	 * 转化Data类型的值为eoms标准时间，例如2011-01-01 12:12:12
	 * 
	 * @author Steve
	 * @since August,2011
	 * @param date
	 * @return
	 */
	public static String toEomsStandardDate2(Date date) {
		return new DateTime(date).toString("yyyy-MM-dd");
	}

	/**
	 * 
	 * 转化String类型的时间值为eoms标准时间Date型,即java.util.Date
	 * 
	 * @author Steve
	 * @since August,2011
	 * @param date
	 * @return
	 */
	public static Date toEomsStandardDate(String date) {
		return DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(
				date).toDate();
	}

}
