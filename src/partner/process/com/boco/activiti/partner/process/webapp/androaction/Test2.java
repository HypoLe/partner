package com.boco.activiti.partner.process.webapp.androaction;

import java.util.Calendar;

public class Test2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		JSONArray citys = new JSONArray();
//		JSONArray cityIds = new JSONArray();
//		cityIds.put("");
//		JSONArray cityNames = new JSONArray();
//		cityNames.put("请选择");
//		
//		cityIds.put("22333");
//		cityNames.put("济南");
//		
//		cityIds.put("111");
//		cityNames.put("淄博");
//		
//		citys.put(cityNames);
//		citys.put(cityIds);
		
//		String ar="2011";
//		String rejectState="workOrderState#rollback";
//		
//		
//		
//		System.out.println("返回数组---------" +rejectState.substring(0,rejectState.indexOf("#")));
//		System.out.println("返回数组---------" +rejectState.substring(rejectState.indexOf("#")+1));
		
		Calendar now = Calendar.getInstance();  
        System.out.println("年: " + now.get(Calendar.YEAR));  
        System.out.println("月: " + (now.get(Calendar.MONTH) + 1) + "");  
        System.out.println("日: " + now.get(Calendar.DAY_OF_MONTH));  
        System.out.println("时: " + now.get(Calendar.HOUR_OF_DAY));  
        System.out.println("分: " + now.get(Calendar.MINUTE));  
        System.out.println("秒: " + now.get(Calendar.SECOND));  
        System.out.println("当前时间毫秒数：" + now.getTimeInMillis());  
		
		
		
		
	}

}
