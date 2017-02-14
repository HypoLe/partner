package com.boco.eoms.gz.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.boco.eoms.base.util.DateUtil;

public class TimeUtil
{
	static Logger logger = Logger.getLogger("");

	public static long getTwoDay(String start, String end)
	{
		return getTwoDayhours(start,end)/24;
	}

	public static long getTwoDayhours(String start, String end)
	{
		return getTwoTimeSecond(start,end)/(60*60);
	}
	public static long getTwoTimeSecond(String start,String end)
	{
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		long tmp=0;
		try
		{
			java.util.Date date = myFormatter.parse(end);
			java.util.Date mydate = myFormatter.parse(start);
			 tmp = (date.getTime() - mydate.getTime())/1000;
			 tmp=tmp>0?tmp:0;
		}
		catch (Exception e)
		{
			return 0;
		}
		return tmp;
	}
	/**
	 * 取给定时间(如:"2008-07-03 21:12:36")的小时值,如21
	 * @param date
	 * @return
	 */
	public static int getHourTime(String date)
	{
		int h = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh");
		//SimpleDateFormat format = new SimpleDateFormat("hh");
		try
		{
			Date startDate = format.parse(date);
			h = startDate.getHours();
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		return h;
	}

	/**
	* @Title: getNextDayHours
	* Description: 取下一天的时间，精确到小时
	* @param datestr
	* @return  
	*/
	public static String getNextDayHours(String datestr)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh");
		Date d = null;
		try
		{
			d = format.parse(datestr);
		}
		catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(d);
		calendar.add(Calendar.DATE, 1);
		return format.format(calendar.getTime());
	}
	/**
	 * @param star
	 * @param end
	 * @return
	 */
	public static int dateCompare(String star, String end)
	{
		DateFormat df = new SimpleDateFormat("yyyy-mm-dd hh");
		int re = 0;
		try
		{
			re = (df.parse(star)).compareTo(df.parse(end));
			Calendar calendar = Calendar.getInstance(),calendar1 = Calendar.getInstance();
			calendar1.setTime(df.parse(end));
			calendar.setTime(df.parse(star));
			calendar.compareTo(calendar1);
		}
		catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return re;
	}
	public static long timeCompareto(String src,String taget)
	{
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long tmp=0;
		try
		{
			java.util.Date edate = myFormatter.parse(src);
			java.util.Date sdate = myFormatter.parse(taget);
			 tmp = (edate.getTime() - sdate.getTime());
			// tmp=tmp>0?tmp:0;
		}
		catch (Exception e)
		{
			return 0;
		}
		return tmp;
	}
	/**
	 * 取month的最后时间,即下月一号的零时零点零分零秒之前
	 * @param month
	 * @return
	 */
	public static String getEndMonthTime(String year, String month) {

		Calendar a = Calendar.getInstance();
		a.setTime(new Date());
		String currentMonth = String.valueOf(a.get(Calendar.MONTH) + 1);
		String currentYear = String.valueOf(a.get(Calendar.YEAR));
		int imonth = Integer.parseInt(month);
		int icurrentMonth = Integer.parseInt(currentMonth);

		// 当月
		if (currentYear.equals(year) && icurrentMonth == imonth) {
			return DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss", new Date());
		} else {

			int nextMonth = Integer.parseInt(month) + 1;
			String snextmonth = nextMonth + "";
			if (nextMonth < 10) {
				snextmonth = "0" + nextMonth;
			}

			return year + "-" + snextmonth + "-01" + " 00:00:00";
		}

	}
	/**
	 * 取指定日期的下的天
	 * @param datestr
	 * @return
	 */
	public static String  getNextDay(String datestr) 
    { 
        Calendar cal = Calendar.getInstance(); 
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
        try 
        { 
        	Date date = sdf.parse(datestr); 
            cal.setTime(date); 
            cal.add(Calendar.DATE, 1); 
           return sdf.format(cal.getTime()).toString(); 
        } 
        catch (Exception e) 
        { 
            e.printStackTrace(); 
            return "";
        } 
    } 
	public static String  getNextDayTime(String datestr) 
	{
		String tmpstr=getNextDay(datestr);
		return tmpstr+" 00:00:00";
	}
	public static void main(String[] args) throws ParseException
	{
		//getMillisecondTime();
  System.out.println(getTwoDayhours("2008-07-03 21:11:36","2008-07-05 21:12:36")); 
  //System.out.println(getTwoDayhours("2008-06-30 21:12:36","2008-07-03 21:12:36")); 
		 //System.out.println(getTwoTime("2008-06-30","2008-07-03")); 
          // System.out.println(getHourTime("2008-07-03 21:12:36")); 
		/*String star = "2007-05-09 09";
		String end = "2007-07-01 02";
		logger.debug("      " + end.compareTo(star));*/
		//logger.info(getEndDate("2006","4"));

	}
}
