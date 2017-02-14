package com.boco.eoms.partner.inspect.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 根据月份获取相应的周期时间
 * @author Administrator
 *
 */
public class DateByMonth {
	
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 根据传来的月份获取周期时间
	 * @return
	 */
	public String[] getDateByMonth(String month,String year,String type){
		//周期类型type:month;doubleMonth;quarter;halfOfYear;year
		//周期开始时间
		String beginTime="";
		//周期结束时间
		String endTime = "";
		//周期开始月份
		String startMonth="";
		//周期结束月份
		String endMonth = "";
		
		int numberMonth =1;
		try{
			
			numberMonth = Integer.parseInt(month);
		}catch(Exception e){
			e.printStackTrace();
		}
		if("month".equals(type)){
			startMonth = month;
			endMonth = month;
		}else if("doubleMonth".equals(type)){
			if(numberMonth%2==0){
				startMonth = String.valueOf(numberMonth-1);
				endMonth= month;
			}else{
				startMonth = month;
				endMonth = String.valueOf(numberMonth+1);
			}
		}else if("quarter".equals(type)){
			if(numberMonth<4){
				startMonth = "1";
				endMonth = "3";
				
			}else if(numberMonth<7){
				startMonth = "4";
				endMonth = "6";
			}else if(numberMonth<10){
				startMonth = "7";
				endMonth = "9";
			}else if(numberMonth<=12){
				startMonth = "10";
				endMonth = "12";
			}
		}else if("halfOfYear".equals(type)){
			if(numberMonth<7){
				startMonth = "1";
				endMonth = "6";
			}else{
				startMonth = "7";
				endMonth = "12";
			}
		}else if("year".equals(type)){
			startMonth = "1";
			endMonth = "12";
		}
		
		beginTime = getCurrentMonthStartTime(startMonth,year);
		endTime = getCurrentMonthEndTime(endMonth,year);
		
		String[] times = new String[2];
		times[0]=beginTime;
		times[1]=endTime;
		return times;
	}
	/**
     * 获得指定月的开始时间，即2012-01-01 
     * 
     * @return
     */
    public   String getCurrentMonthStartTime(String month,String year) {
    	int numberMonth= 1;
    	try{
    		
    		numberMonth = Integer.parseInt(month);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	String returnTime = "";
    	if(numberMonth<10){
    		returnTime =  year+"/0"+month+"/01";
    	}else{
    		returnTime =  year+"/"+month+"/01";
    	}
        return returnTime;
    }
	
	 /**
     * 指定月的结束时间，即2012-01-31 
     * 
     * @return
     */
    public   String getCurrentMonthEndTime(String month,String year) {
        String now = "";
        int numberMonth= 1;
    	try{
    		numberMonth = Integer.parseInt(month);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        if(numberMonth==1 ||numberMonth==3||numberMonth==5||numberMonth==7||numberMonth==8){
        	now = year+"/0"+month+"/31";
        }else if(numberMonth==10||numberMonth==12){
        	now = year+"/"+month+"/31";
        }else if(numberMonth==2){
        	now = year+"/0"+month+"/29";
        }else if(numberMonth==4||numberMonth==6||numberMonth==9){
        	now = year+"/0"+month+"/30";
        }else if(numberMonth==11){
        	now = year+"/"+month+"/30";
        }
        return now;
    }
}
