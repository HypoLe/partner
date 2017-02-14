package com.boco.eoms.partner.inspect.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.LocalDate;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/** 
 * Description: 巡检公用方法 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Jul 30, 2012 10:23:38 AM 
 */
public class InspectUtils {
	
	/**
	 * 获取能设置的巡检日期最小值
	 * @param isWeekCycle    是否巡检周期为周
	 * @param cycleStartTime 周期开始日期
	 * @return
	 */
	public static String getMinConfigDate(boolean isWeekCycle,Date cycleStartTime){
		if(isWeekCycle){//如果是周则以周期默认开始时间作为能设置的巡检日期最小值
			return new LocalDate(cycleStartTime).toString();
		}else{//如果不是周则以本月月初作为能设置的巡检日期最小值
			return new LocalDate().dayOfMonth().withMinimumValue().toString();
		}
	}
	
	/**
	 * 获取能设置的巡检日期最大值
	 * @param isWeekCycle   是否巡检周期为周
	 * @param cycleEndTime  周期结束日期
	 * @return
	 */
	public static String getMaxConfigDate(boolean isWeekCycle,Date cycleEndTime){
		if(isWeekCycle){//如果是周则以周期默认结束时间作为能设置的巡检日期最大值
			return new LocalDate(cycleEndTime).toString();
		}else{//如果不是周则以本月月末作为能设置的巡检日期最大值
			return new LocalDate().dayOfMonth().withMaximumValue().toString();
		}
	}
	/**
	 * 判断两个时间是否是同一天
	 * author LEE
	 */
	@SuppressWarnings("deprecation")
	public static boolean isCurrentDay(String firstTime,String secondTime){
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date firstData = formatter.parse(firstTime);
			Date secondData = formatter.parse(secondTime);
			if(firstData.getDate() == secondData.getDate()){
				return true;
			}else
			return false;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 计算两个时间的时间差,返回分钟
	 * author LEE
	 */
	public static Long getTimeDifference(String firstTime,String secondTime){
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date firstData = formatter.parse(firstTime);
			Date secondData = formatter.parse(secondTime);
			long timeDifference = secondData.getTime() - firstData.getTime();
			long day = timeDifference / (24 * 60 * 60 * 1000);
			long hour = (timeDifference / (60 * 60 * 1000) - day * 24);
			long min = ((timeDifference / (60 * 1000)) - day * 24 * 60 - hour * 60);
			long s = (timeDifference / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
			System.out.println("min2  "+(s/60+min+hour*60+day*24*60));
			return s/60+min+hour*60+day*24*60;//返回分钟数
		} catch (ParseException e) {
			e.printStackTrace();
			return 0l;
		}
	}
	/**
	 * 将 BASE64 编码的字符串 s 进行解码
	 * @param s
	 * @return
	 *  author LEE
	 */
	public static byte[] getFromBASE64(String s) {
		if (s == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(s);
			
			return b;
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * //进行Base64编码 
	 * @param filePath
	 * @return
	 * @throws IOException
	 *  author LEE
	 */
	public static String convertImageDataToBASE64(String filePath) throws IOException {
		File file = new File(filePath);
		FileInputStream fs = new FileInputStream(file);
		
		BASE64Encoder c1 = new BASE64Encoder();
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
		c1.encodeBuffer(fs,baos);
		String uploadData = new String(baos.toByteArray()); 
		
		baos.close();
		fs.close();
		return uploadData;
	}
	private static double EARTH_RADIUS = 6378.137;
	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}
	
	/**
	 * 计算经纬度距离  误差距离,单位公里
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return
	 * author LEE
	 */
	public static double getDistance(double lat1, double lng1, double lat2,double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
		+ Math.cos(radLat1) * Math.cos(radLat2)
		* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 100000) / 100000;
		return s;
	}
}
