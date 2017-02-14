package com.boco.eoms.partner.property.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import junit.framework.TestCase;

import org.joda.time.DateTime;

import com.boco.eoms.base.util.StaticMethod;

public class timeHandle extends TestCase{
	public void testtimeHand(){
		Calendar cal222=new GregorianCalendar();
		 cal222=StaticMethod.String2Cal("2012-08-09 10:34");
		TimeZone tz = TimeZone.getTimeZone("ETC/GMT-8"); 
		TimeZone.setDefault(tz); 
		Date date=new Date();
	    Calendar ca = Calendar.getInstance();
	    ca.add(Calendar.MONTH, 4);
	    
	    int year = ca.get(Calendar.YEAR);//获取年份
	    int month = ca.get(Calendar.MONTH)+1;//获取月份 
	    int day = ca.get(Calendar.DATE);//获取日
	    int dayOfWeek = ca.get(Calendar.DAY_OF_WEEK)-1; 
	    DateTime dt = new DateTime (ca);
	    DateTime yearDtMin = dt.dayOfYear().withMinimumValue();
	    DateTime yearDtMax = dt.dayOfYear().withMaximumValue();
	    DateTime monthDtMin = dt.dayOfMonth().withMinimumValue();
	    DateTime monthDtMax = dt.dayOfMonth().withMaximumValue();
	    DateTime weekDtMin = dt.dayOfWeek().withMinimumValue();
	    DateTime weekDtMax = dt.dayOfWeek().withMaximumValue();
	    
	    String[] rangOfYear = {new DateTime(yearDtMin.getYear(),yearDtMin.getMonthOfYear(),yearDtMin.getDayOfMonth(),0,0,0,0).toString("yyyy-MM-dd HH:mm:ss"),
	    					   new DateTime(yearDtMax.getYear(),yearDtMax.getMonthOfYear(),yearDtMax.getDayOfMonth(),23,59,59,0).toString("yyyy-MM-dd HH:mm:ss")};
	    String[] rangOfMonth = {new DateTime(monthDtMin.getYear(),monthDtMin.getMonthOfYear(),monthDtMin.getDayOfMonth(),0,0,0,0).toString("yyyy-MM-dd HH:mm:ss"),
	    						new DateTime(monthDtMax.getYear(),monthDtMax.getMonthOfYear(),monthDtMax.getDayOfMonth(),23,59,59,0).toString("yyyy-MM-dd HH:mm:ss")};
	    String[] rangOfWeek = {new DateTime(weekDtMin.getYear(),weekDtMin.getMonthOfYear(),weekDtMin.getDayOfMonth(),0,0,0,0).toString("yyyy-MM-dd HH:mm:ss"),
	    						new DateTime(weekDtMax.getYear(),weekDtMax.getMonthOfYear(),weekDtMax.getDayOfMonth(),23,59,59,0).toString("yyyy-MM-dd HH:mm:ss")};
	    
	    String[] rangOfhalfOfMonth = new String[2] ;
	    if(day>=1 && day<16) {
	    	rangOfhalfOfMonth[0] = new DateTime(monthDtMin.getYear(),monthDtMin.getMonthOfYear(),monthDtMin.getDayOfMonth(),0,0,0,0).toString("yyyy-MM-dd HH:mm:ss");
	    	rangOfhalfOfMonth[1] = new DateTime(monthDtMax.getYear(),monthDtMax.getMonthOfYear(),15,23,59,59,0).toString("yyyy-MM-dd HH:mm:ss");
	    } else {
	    	rangOfhalfOfMonth[0] = new DateTime(monthDtMin.getYear(),monthDtMin.getMonthOfYear(),16,0,0,0,0).toString("yyyy-MM-dd HH:mm:ss");
	    	rangOfhalfOfMonth[1] = new DateTime(monthDtMax.getYear(),monthDtMax.getMonthOfYear(),monthDtMax.getDayOfMonth(),23,59,59,0).toString("yyyy-MM-dd HH:mm:ss");
	    }
	    
	    String[] rangOfhalfOfYear = new String[2];
	    if(month>=1 && month<7) {
	    	rangOfhalfOfYear[0] = new DateTime(yearDtMin.getYear(),yearDtMin.getMonthOfYear(),yearDtMin.getDayOfMonth(),0,0,0,0).toString("yyyy-MM-dd HH:mm:ss");
	    	rangOfhalfOfYear[1] = new DateTime(yearDtMax.getYear(),6,30,23,59,59,0).toString("yyyy-MM-dd HH:mm:ss");
	    } else {
	    	rangOfhalfOfYear[0] = new DateTime(yearDtMin.getYear(),7,1,0,0,0,0).toString("yyyy-MM-dd HH:mm:ss");
	    	rangOfhalfOfYear[1] = new DateTime(yearDtMax.getYear(),yearDtMax.getMonthOfYear(),yearDtMax.getDayOfMonth(),23,59,59,0).toString("yyyy-MM-dd HH:mm:ss");
	    }
	    
	    String[] rangOfhalfOfQuarter = new String[2];
	    if(month>=1 && month<4) {
	    	rangOfhalfOfQuarter[0] = new DateTime(yearDtMin.getYear(),1,1,0,0,0,0).toString("yyyy-MM-dd HH:mm:ss");
	    	rangOfhalfOfQuarter[1] = new DateTime(yearDtMax.getYear(),3,31,23,59,59,0).toString("yyyy-MM-dd HH:mm:ss");
	    } else if(month>=4 && month<7){
	    	rangOfhalfOfQuarter[0] = new DateTime(yearDtMin.getYear(),4,1,0,0,0,0).toString("yyyy-MM-dd HH:mm:ss");
	    	rangOfhalfOfQuarter[1] = new DateTime(yearDtMax.getYear(),6,30,23,59,59,0).toString("yyyy-MM-dd HH:mm:ss");
	    } else if(month>=7 && month<10) {
	    	rangOfhalfOfQuarter[0] = new DateTime(yearDtMin.getYear(),7,1,0,0,0,0).toString("yyyy-MM-dd HH:mm:ss");
	    	rangOfhalfOfQuarter[1] = new DateTime(yearDtMax.getYear(),9,30,23,59,59,0).toString("yyyy-MM-dd HH:mm:ss");
	    } else {
	    	rangOfhalfOfQuarter[0] = new DateTime(yearDtMin.getYear(),10,1,0,0,0,0).toString("yyyy-MM-dd HH:mm:ss");
	    	rangOfhalfOfQuarter[1] = new DateTime(yearDtMax.getYear(),yearDtMax.getMonthOfYear(),yearDtMax.getDayOfMonth(),23,59,59,0).toString("yyyy-MM-dd HH:mm:ss");
	    }
	}
	
	public static void main(String[] args) {
		
	}
}
