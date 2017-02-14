package utils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import com.google.common.collect.Lists;

/**
 * 
 * @author Steve
 * @since August,2011
 * 
 * utils.Nop3Util类的日期辅助类
 */
class Nop3UtilsDateHelper {

	/*
	 * 获得自然季度,1~3月为第一季度,以此类推
	 */
	public static int getQuarterOfYear(DateTime dateTime) {
		int monthOfYear = dateTime.getMonthOfYear();
		if (monthOfYear >= 1 && monthOfYear <= 3) {
			return 1;
		} else if (monthOfYear >= 4 && monthOfYear <= 6) {
			return 2;
		} else if (monthOfYear >= 7 && monthOfYear <= 9) {
			return 3;
		} else {
			return 4;
		}
	}

	/*
	 * 获得某一个自然季度的起始时间,1第一季度的开始时间应该为yyyy-01-01 00:00:00
	 */
	public static DateTime getStartOfQuarter(DateTime dateTime) {
		int quarterOfYear = getQuarterOfYear(dateTime);
		System.out.println(quarterOfYear);
		if (quarterOfYear == 1) {
			return dateTime.withMonthOfYear(1).withDayOfMonth(1)
					.withMillisOfDay(0);
		} else if (quarterOfYear == 2) {
			return dateTime.withMonthOfYear(4).withDayOfMonth(1)
					.withMillisOfDay(0);
		} else if (quarterOfYear == 3) {
			return dateTime.withMonthOfYear(7).withDayOfMonth(1)
					.withMillisOfDay(0);
		} else {
			return dateTime.withMonthOfYear(10).withDayOfMonth(1)
					.withMillisOfDay(0);
		}
	}
	
	public static List<Date> getTimeWithPeriod(String period,Date startTime,Date endTime) {
		List<Date> startAndEndList = Lists.newArrayList();
		DateTime startDateTime = new DateTime(startTime);
		DateTime endDateTime= new DateTime(endTime);
		//周期为周
		if("week".equals(period)){
			while(startDateTime.plusWeeks(1).isBefore(endDateTime)){
				startAndEndList.add(startDateTime.toDate());
				startDateTime = startDateTime.plusWeeks(1);
				startAndEndList.add(startDateTime.toDate());
			}
			startAndEndList.add(startDateTime.toDate());
			startAndEndList.add(endTime);
		}
		//周期为月
		else if("month".equals(period)){
			while(startDateTime.plusMonths(1).isBefore(endDateTime)){
				startAndEndList.add(startDateTime.toDate());
				startDateTime = startDateTime.plusMonths(1);
				startAndEndList.add(startDateTime.toDate());
			}
			startAndEndList.add(startDateTime.toDate());
			startAndEndList.add(endTime);
		}
		else if("quarter".equals(period)){
			while(startDateTime.plusMonths(4).isBefore(endDateTime)){
				startAndEndList.add(startDateTime.toDate());
				startDateTime = startDateTime.plusMonths(4);
				startAndEndList.add(startDateTime.toDate());
			}
			startAndEndList.add(startDateTime.toDate());
			startAndEndList.add(endTime);
		}
		//周期为半年
		else if("halfYear".equals(period)){
			while(startDateTime.plusMonths(6).isBefore(endDateTime)){
				startAndEndList.add(startDateTime.toDate());
				startDateTime = startDateTime.plusMonths(6);
				startAndEndList.add(startDateTime.toDate());
			}
			startAndEndList.add(startDateTime.toDate());
			startAndEndList.add(endTime);
		}
		//周期为年
		else if("year".equals(period)){
			while(startDateTime.plusYears(1).isBefore(endDateTime)){
				startAndEndList.add(startDateTime.toDate());
				startDateTime = startDateTime.plusYears(1);
				startAndEndList.add(startDateTime.toDate());
			}
			startAndEndList.add(startDateTime.toDate());
			startAndEndList.add(endTime);
		}
		return startAndEndList;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(new String("合作伙伴统一管理平台".getBytes(), "utf-8")
				.toString());
	}
}
