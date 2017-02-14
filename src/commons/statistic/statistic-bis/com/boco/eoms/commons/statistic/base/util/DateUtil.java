package com.boco.eoms.commons.statistic.base.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

	// 用来全局控制 上一周，本周，下一周的周数变化
    private static int weeks = 0;
   
    // 获得当前日期与本周一相差的天数
    private int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            return -6;
        } else {
            return 2 - dayOfWeek;
        }
    }

    // 获得上周星期一的日期
    public String getPreviousMonday() {
        weeks--;
        int mondayPlus = this.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    // 获得本周星期一的日期
    public String getCurrentMonday() {
        weeks = 0;
        int mondayPlus = this.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    // 获得下周星期一的日期
    public String getNextMonday() {
        weeks++;
        int mondayPlus = this.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    // 获得相应周的周日的日期
    public String getSunday() {
        int mondayPlus = this.getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks + 6);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    } 
    
    /**
	 * 得到当月的第一天和最后一天
	 * @param calendar
	 * @return
	 */
	private static Date[] getMonthStartAndEndDate(Calendar calendar) 
	{    
		Date[] dates = new Date[2];
		Date firstDateOfMonth, lastDateOfMonth;
		// 得到当天是这月的第几天
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		// 减去dayOfMonth,得到第一天的日期，因为Calendar用０代表每月的第一天，所以要减一
		calendar.add(Calendar.DAY_OF_MONTH, -(dayOfMonth - 1));
		firstDateOfMonth = calendar.getTime();
		// calendar.getActualMaximum(Calendar.DAY_OF_MONTH)得到这个月有几天
		calendar.add(Calendar.DAY_OF_MONTH, calendar
				.getActualMaximum(Calendar.DAY_OF_MONTH) - 1);
		lastDateOfMonth = calendar.getTime();
		dates[0] = firstDateOfMonth;
		dates[1] = lastDateOfMonth;
		return dates;  
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DateUtil du = new DateUtil();
		System.out.println(du.getCurrentMonday());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		try {
			Date d = sdf.parse(du.getCurrentMonday());
			System.out.println(d);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
