package com.boco.eoms.deviceManagement.faultInfo.utils;


import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.googlecode.genericdao.search.Search;

public class CommonUtils {
	/**
	 * 拼接查询条件的泛型方法
	 * 
	 * An immutable java.util.Map containing parameter names as keys and
	 * parameter values as map values. The keys in the parameter map are of type
	 * String. The values in the parameter map are of type String array.
	 * 
	 * @author Steve
	 * @since August,2011
	 * 
	 */
	public static Search getSqlFromRequestMap(Map requestMap, Search search) {
		String clause;
		String clauseValue;
		for (Object keyObject : requestMap.keySet()) {
			clause = String.valueOf(keyObject);
			// This operation is safe, view j2ee5 API please.
			clauseValue = ((String[]) requestMap.get(clause))[0].toString();
			if (clauseValue.equals("")) {
				continue;
			} else if (clause.indexOf("StringEqual") != -1) {
				clause = clause.substring(0, clause.indexOf("StringEqual"));
				search.addFilterEqual(clause, clauseValue.trim());
			} else if (clause.indexOf("StringLike") != -1) {
				clause = clause.substring(0, clause.indexOf("StringLike"));
				search.addFilterLike(clause, "%" + clauseValue.trim() + "%");
			} else if (clause.indexOf("DateGreaterThan") != -1) {
				clause = clause.substring(0, clause.indexOf("DateGreaterThan"));
				search.addFilterGreaterThan(clause, clauseValue.trim());
			} else if (clause.indexOf("DateLessThan") != -1) {
				clause = clause.substring(0, clause.indexOf("DateLessThan"));
				search.addFilterLessThan(clause, clauseValue.trim());
			} else {

			}
		}
		return search;
	}
	
	/** added by lvzhongqian
	 * @param dfStr 指定日期/日期时间格式，默认为eoms标准时间格式yyyy-MM-dd HH:mm:ss
	 * */
	public static Search getSqlFromRequestMap(Map requestMap, Search search,String dfStr) {
		String clause;
		String clauseValue;
		for (Object keyObject : requestMap.keySet()) {
			clause = String.valueOf(keyObject);
			// This operation is safe, view j2ee5 API please.
			clauseValue = ((String[]) requestMap.get(clause))[0].toString();
			if (clauseValue.equals("")) {
				continue;
			} else if (clause.indexOf("StringEqual") != -1) {
				clause = clause.substring(0, clause.indexOf("StringEqual"));
				search.addFilterEqual(clause, clauseValue.trim());
			} else if (clause.indexOf("StringLike") != -1) {
				clause = clause.substring(0, clause.indexOf("StringLike"));
				search.addFilterLike(clause, "%" + clauseValue.trim() + "%");
			} else if (clause.indexOf("DateGreaterThan") != -1) {
				clause = clause.substring(0, clause.indexOf("DateGreaterThan"));
				if("".equals(Strings.nullToEmpty(dfStr).trim())){
					search.addFilterGreaterThan(clause, toEomsStandardDate(clauseValue.trim()));
				}else{
					
					search.addFilterGreaterThan(clause,
							DateTimeFormat.forPattern(dfStr).parseDateTime(clauseValue.trim()).toDate());
				}				
			} else if (clause.indexOf("DateLessThan") != -1) {
				clause = clause.substring(0, clause.indexOf("DateLessThan"));
				if("".equals(Strings.nullToEmpty(dfStr).trim())){
					search.addFilterLessThan(clause, toEomsStandardDate(clauseValue.trim()));
				}else{			
					search.addFilterLessThan(clause,
							DateTimeFormat.forPattern(dfStr).parseDateTime(clauseValue.trim()).toDate());
				}
			} else {

			}
		}
		return search;
	}

	/**
	 * 
	 * 转化Data类型的值为eoms标准时间，例如2011-01-01 12:12:12
	 * 
	 * @author Steve
	 * @since August,2011
	 * @param date
	 * @return
	 */
	public static String toEomsStandardDate(Date date) {
		return new DateTime(date).toString("yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 
	 * 转化String类型的时间值为eoms标准时间Date型,即java.util.Date
	 * 
	 * @author Steve
	 * @since August,2011
	 * @param date
	 * @return
	 */
	public static Date toEomsStandardDate(String date) {
		return DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(
				date).toDate();
	}

	/**
	 * 读入HttpServletResponse,输出成功的Json字符串到前台,全浏览器兼容
	 * 
	 * @author Steve
	 * @since August,2011
	 * @param response
	 * @throws IOException
	 */
	public static void printJsonSuccessMsg(HttpServletResponse response)
			throws IOException {
		response.setCharacterEncoding(Charsets.UTF_8.toString());
		response.getWriter().write(
				new Gson().toJson(new ImmutableMap.Builder<String, String>()
						.put("success", "true").put("msg", "ok").build()));
	}

	/**
	 * 读入HttpServletResponse,输出失败的Json字符串到前台,全浏览器兼容
	 * 
	 * @author Steve
	 * @since August,2011
	 * @param response
	 * @throws IOException
	 */
	public static void printJsonFailureMsg(HttpServletResponse response)
			throws IOException {
		response.setCharacterEncoding(Charsets.UTF_8.toString());
		response
				.getWriter()
				.write(
						new Gson()
								.toJson(new ImmutableMap.Builder<String, String>()
										.put("success", "false").put("msg",
												"failure").build()));
	}

	/**
	 * 
	 * 读入HttpServletResponse,将Json格式写出对象到HttpServletResponse
	 * 
	 * 只传一个参数将直接把对象写出到HttpServletResponse，传二个参数则会把对象以键值对的方式写出到HttpServletResponse，
	 * 
	 * @author Steve
	 * @since August,2011
	 * 
	 * @param response
	 * @param object,可变参数，限制于2个参数之内
	 * @throws IOException
	 */
	public static void printJsonObject(HttpServletResponse response,
			Object... object) throws IOException {
		boolean myObjectLength = object.length >= 1 && object.length <= 2;
		Preconditions.checkArgument(myObjectLength,
				"Object Length should be between 1 and 2");
		response.setCharacterEncoding(Charsets.UTF_8.toString());
		if (object.length == 2) {
			Map<String, Object> myResultMap = Maps.newHashMap();
			myResultMap.put(object[0].toString(), object[1]);
			response.getWriter().write(new Gson().toJson(myResultMap));
		} else {
			response.getWriter().write(new Gson().toJson(object[0]));
		}
	}

}
