package com.boco.eoms.mobile.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.boco.eoms.common.util.StaticMethod;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MobileCommonUtils {
	public static String compressEncode = "ISO-8859-1";
	public static String unCompressEncode = "ISO-8859-1";
	
	public static String toJson(Object obj){
		Gson gson = new GsonBuilder().serializeNulls().create();
	    return "["+gson.toJson(obj)+"]";
	}
	public static String replaceToJson(Object obj){
		return replaceStr2(toJson(obj));
	}
	
	public static void responseWrite(HttpServletResponse response,String value,String encode){
		response.setCharacterEncoding(encode);
		response.setContentType("text/plain");
//		System.out.println(value);
		try {
			response.getOutputStream().write(value.getBytes(encode));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void responseZipWrite(HttpServletResponse response,String value,String encode){
		response.setCharacterEncoding(encode);
		response.setContentType("text/plain");
		try {
			response.getOutputStream().write(compress(value,compressEncode).getBytes(encode));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String uncompress(String str,String encode) throws IOException {
		if (str == null || str.length() == 0){
			return str;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes(encode));
		GZIPInputStream gunzip = new GZIPInputStream(in);
		byte[] buffer = new byte[256];
		int n;
		while ((n = gunzip.read(buffer)) >= 0) {
			out.write(buffer, 0, n);
		}
		// toString()使用平台默认编码，也可以显式的指定如toString("GBK")
		return out.toString();
	}
	
	public static String compress(String str,String encode) throws IOException {
		if (str == null || str.length() == 0) {
			return str;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		gzip.write(str.getBytes());
		gzip.close();
		return out.toString(encode);
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
		s = Math.round(s * 1000);
		s = s/1000;
		return s;
	}
	
	public static boolean isAndroidRequest(HttpServletRequest request){
		if(null != request.getHeader("User-Agent")&&StaticMethod.nullObject2String(request.getHeader("User-Agent")).contains("Android_client")&&"android".equals(StaticMethod.nullObject2String(request.getParameter("type")))){
			return true;
		}
		return false;
	}
	
	public static String replaceStr(String json){
		 StringBuffer sb = new StringBuffer();        
	        for (int i=0; i<json.length(); i++) {
	            char c = json.charAt(i);    
	             switch (c){  
//	             case '\"':        
//	                 sb.append("\\\"");        
//	                 break;        
	             case '\\':        
	                 sb.append("\\\\");        
	                 break;        
	             case '/':        
	                 sb.append("\\/");        
	                 break;        
	             case '\b':        
	                 sb.append("\\b");        
	                 break;        
	             case '\f':        
	                 sb.append("\\f");        
	                 break;        
	             case '\n':        
	                 sb.append("\\n");        
	                 break;        
	             case '\r':        
	                 sb.append("\\r");        
	                 break;        
	             case '\t':        
	                 sb.append("\\t");        
	                 break;        
	             default:        
	                 sb.append(c);     
	             }  
	         }      
	       return sb.toString();     
	}
	public static String replaceStr2(String json){
		 StringBuffer sb = new StringBuffer();        
	        for (int i=0; i<json.length(); i++) {  
	            char c = json.charAt(i);    
	             switch (c){  
//	             case '\"':        
//	                 sb.append("\\\"");        
//	                 break;        
	             case '\\':        
	                 sb.append("\\\\");        
	                 break;        
	             case '/':        
	                 sb.append("\\/");        
	                 break;        
	             case '\b':        
	                 sb.append("\\b");        
	                 break;        
	             case '\f':        
	                 sb.append("\\f");        
	                 break;        
	             case '\n':        
	                 sb.append("\\n");        
	                 break;        
	             case '\r':        
	                 sb.append("\\r");        
	                 break;        
	             case '\t':        
	                 sb.append("\\t");        
	                 break;        
	             default:        
	                 sb.append(c);     
	             }  
	         }      
	       return sb.toString();     
	}
}
