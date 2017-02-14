package com.boco.eoms.partner.geo.parse;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.boco.eoms.partner.geo.model.ResourceGeoInfo;

public final class ParseUtils {
	
	/**
	 * 解析（资源标识#资源类型#上报时间#位置时间#经度#纬度#定位状态#错误描述） 这种格式串.
	 * @param interfaceResultString
	 * @return
	 */
	public static ResourceGeoInfo parsInterfaceReturnResult(String interfaceResultString){
		
		if(interfaceResultString == null || interfaceResultString.indexOf("#")<=0){
			return null;
		}
		
		String[] result = interfaceResultString.split("#");
		ResourceGeoInfo resourceGeoInfo = generateGeoInfo(result);
		
		
		
		return resourceGeoInfo;
	}

	/**
	 * 根据解析结果集生成GeoInfo对象.
	 * @param result
	 * @return
	 */
	private static ResourceGeoInfo generateGeoInfo(String[] result) {
		
		ResourceGeoInfo resourceGeoInfo = null;
		if(result.length >= 7 ){
			resourceGeoInfo = new ResourceGeoInfo();
			resourceGeoInfo.setResourceId(result[0]);
			resourceGeoInfo.setResourceType(result[1]);
			resourceGeoInfo.setReportTime(getTimestamp(result[2],null));
			resourceGeoInfo.setPositionTime(getTimestamp(result[3],null));
			resourceGeoInfo.setLongitude(result[4]);
			resourceGeoInfo.setLatitude(result[5]);
			resourceGeoInfo.setPositionStatus(result[6]);
		}
		//状态定位不准.
		if(result.length == 8 ){
			resourceGeoInfo.setPositionStatus(result[6]);
			resourceGeoInfo.setErroContext(result[7]);
		}
		if(result.length <7){
			//记录结果状态.
		}
		
		return resourceGeoInfo;
	}
	
	
	
	public static Timestamp getTimestamp(String date,String dateFormat){
		if(dateFormat == null || "".equals(dateFormat)){
			dateFormat ="yyyy-MM-dd HH:mm:ss";
		}
		DateFormat dateFormat2;
		SimpleDateFormat formater = new SimpleDateFormat(dateFormat);
		Date resultDate = new Date(System.currentTimeMillis());
		try {
			resultDate = formater.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Timestamp(resultDate.getTime());
	}
	
	public static String nullObject2String(Object o){
		if(o == null){
			return "";
		}
		return o.toString();
	}
	
	public static String currentDate(){
		DateFormat dateFormate = new SimpleDateFormat("yyyy-MM-dd");
		String curentDate = dateFormate.format(new Date(System.currentTimeMillis()));
		return curentDate;
	}
	
}
