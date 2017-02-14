package com.boco.eoms.arcGis.util;

import org.jfree.data.general.DefaultPieDataset;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;


import com.boco.eoms.base.util.StaticMethod;


/**
* @ClassName: ArcGisConstacts
* @Description: TODO
* @author huhao
* @date 2012-11-23 上午11:22:05
 */
public class ArcGisConstacts {
	
	/**
	 * @author huhao
	 * @description \n to <br/>
	 * @param source
	 * @param toReplace
	 * @param replacement
	 * @return String
	 */
	public static String replaceAll(String source, String toReplace,
			String replacement) {
		int idx = source.lastIndexOf(toReplace);
		if (idx != -1) {
			StringBuffer ret = new StringBuffer(source);
			ret.replace(idx, idx + toReplace.length(), replacement);
			while ((idx = source.lastIndexOf(toReplace, idx - 1)) != -1) {
				ret.replace(idx, idx + toReplace.length(), replacement);
			}
			source = ret.toString();
		}

		return source;
	}

    
	//在网上找的，所说是谷歌的算法，我把参数和返回值的单位改了，传入经纬度的毫秒，返回距离毫米

/**
 * @author pointatyou
 * @param Point
 * @return  number
 */
	private static  double EARTH_RADIUS = 6378.137;//一般的认为，地球的赤道半径是6378137米
		
		
	private static double rad(double d)
	{
	  return d * Math.PI / 180.0;
	}

	public static int GetDistance(double lat1, double lng1, double lat2, double lng2)
	{
	        double radLat1 = rad(lat1);  
	        double radLat2 = rad(lat2);  
	        double a = radLat1 - radLat2;  
	        double b = rad(lng1) - rad(lng2);  
	        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)  
	                + Math.cos(radLat1) * Math.cos(radLat2)  
	                * Math.pow(Math.sin(b / 2), 2)));  
	        s = s * EARTH_RADIUS;  
	        s = Math.round(s * 10000);  
	        s = s / 10000;  
	        return  (int) (s*1000) ;//计算单位：米 
	}
//	public static void main(String[] args) {
//		104.175091,30.83904,104.175675,30.838575
//	System.out.println(ArcGisConstacts.GetDistance(104.175091,30.839040,104.175675,30.838575));
//	System.out.println(ArcGisConstacts.formatObject("104.175",6));
	
//	}
	
	
	
	/**
	 * 强制保留小数位
	 * @author pointatyou
	 * @param format 传入要强制保留小数的字符串
	 * @param formatNum 传入要强制保留的小数位数
	 * @return
	 */
	public static String formatObject(String format,int formatNum){
		format=StaticMethod.null2String(format);
		String[] newformat=format.split("\\u002E");
		String returnFormat=format;
		if(!"".equals(format)){
		String end="";
		if(newformat.length==2){
			 end=newformat[1];
			 int sum=formatNum-end.length();
			if(end.length()<formatNum){
				if(sum>0){
					for(int i=0;i<sum;i++){
						returnFormat=returnFormat+"0";
					}
				}
			}
			else if(end.length()>formatNum){
				if(sum<0){
				returnFormat=newformat[0]+"."+newformat[1].substring(0, formatNum);
				}
			}
		}else if(newformat.length==1){
			 end=newformat[0];
			 returnFormat=returnFormat+".";
			 for(int i=0;i<formatNum;i++){
				 returnFormat=returnFormat+"0";
			 }
		}
		}
				return returnFormat;
		
		
	}
}





