package com.boco.eoms.deviceManagement.common.utils;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import bsh.This;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.util.HibernateConfigurationHelper;
import com.boco.eoms.deviceManagement.common.pojo.EomsSearch;
import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.googlecode.genericdao.search.Search;

public class CommonUtils {
	
	/**
	 * 获取分页PageIndex
	 * @param request
	 * @param tableId jsp页面display:table的id
	 * @return
	 */
	public static String getPageIndexWithDisplaytag(HttpServletRequest request,String tableId){
		return 	request.getParameter((new org.displaytag.util.ParamEncoder(
				tableId).encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
	}
	
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
	@SuppressWarnings("unchecked")
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
			} else if (clause.indexOf("GreaterOrEqual") != -1) {
				clause = clause.substring(0, clause.indexOf("GreaterOrEqual"));
				search.addFilterGreaterOrEqual(clause, clauseValue.trim());
			} else if (clause.indexOf("LessOrEqual") != -1) {
				clause = clause.substring(0, clause.indexOf("LessOrEqual"));
				search.addFilterLessOrEqual(clause, clauseValue.trim());
			} else {

			}
		}
		return search;
	}
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
	@SuppressWarnings("unchecked")
	public static EomsSearch getSqlFromRequestMap(Map requestMap, EomsSearch search) {
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
			} else if (clause.indexOf("GreaterOrEqual") != -1) {
				clause = clause.substring(0, clause.indexOf("GreaterOrEqual"));
				search.addFilterGreaterOrEqual(clause, clauseValue.trim());
			} else if (clause.indexOf("LessOrEqual") != -1) {
				clause = clause.substring(0, clause.indexOf("LessOrEqual"));
				search.addFilterLessOrEqual(clause, clauseValue.trim());
			} else {

			}
		}
		return search;
	}
	/**
	 * 拼接查询条件的泛型方法,并设置查询值到request attribute
	 * 
	 * An immutable java.util.Map containing parameter names as keys and
	 * parameter values as map values. The keys in the parameter map are of type
	 * String. The values in the parameter map are of type String array.
	 * 
	 * @author Steve
	 * @since August,2011
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static EomsSearch getSqlFromRequestMap(HttpServletRequest request , EomsSearch search) {
		Map requestMap = request.getParameterMap();
		String clause;
		String clauseValue;
		for (Object keyObject : requestMap.keySet()) {
			clause = String.valueOf(keyObject);
			// This operation is safe, view j2ee5 API please.
			clauseValue = ((String[]) requestMap.get(clause))[0].toString();
			// 设置查询条件到request attribute中。
			request.setAttribute(clause, clauseValue);
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
			} else if (clause.indexOf("GreaterOrEqual") != -1) {
				clause = clause.substring(0, clause.indexOf("GreaterOrEqual"));
				search.addFilterGreaterOrEqual(clause, clauseValue.trim());
			} else if (clause.indexOf("LessOrEqual") != -1) {
				clause = clause.substring(0, clause.indexOf("LessOrEqual"));
				search.addFilterLessOrEqual(clause, clauseValue.trim());
			} else {

			}
		}
		return search;
	}
	/**
	 * 拼接查询条件的泛型方法,并设置查询值到request attribute
	 * 
	 * An immutable java.util.Map containing parameter names as keys and
	 * parameter values as map values. The keys in the parameter map are of type
	 * String. The values in the parameter map are of type String array.
	 * 
	 * @author Steve
	 * @since August,2011
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static Search getSqlFromRequestMap(HttpServletRequest request , Search search) {
		Map requestMap = request.getParameterMap();
		String clause;
		String clauseValue;
		for (Object keyObject : requestMap.keySet()) {
			clause = String.valueOf(keyObject);
			// This operation is safe, view j2ee5 API please.
			clauseValue = ((String[]) requestMap.get(clause))[0].toString();
			// 设置查询条件到request attribute中。
			request.setAttribute(clause, clauseValue);
			if (clauseValue.equals("")) {
				continue;
			} else if (clause.indexOf("StringEqual") != -1) {
				clause = clause.substring(0, clause.indexOf("StringEqual"));
				search.addFilterEqual(clause, clauseValue.trim());
			} else if (clause.indexOf("StringLike") != -1) {
				clause = clause.substring(0, clause.indexOf("StringLike"));
				search.addFilterLike(clause, "%" + clauseValue.trim() + "%");
			}else if (clause.indexOf("DateGreaterThan") != -1) {//日期为Date类型
				clause = clause.substring(0, clause.indexOf("DateGreaterThan"));
				String greateDate="";
				if (clauseValue.trim().length()>11) {
					greateDate=CommonSqlHelper.formatDateTime(clauseValue.trim());//日期格式为"yyyy-mm-dd hh:mm:ss"时；
				}else {
					greateDate= CommonSqlHelper.formatDate(clauseValue.trim());
				}
				search.addFilterGreaterThan(clause, greateDate);
			} else if (clause.indexOf("DateLessThan") != -1) {//日期为Date类型
				clause = clause.substring(0, clause.indexOf("DateLessThan"));
				String lessDate="";
				if (clauseValue.trim().length()>10) {
					lessDate=CommonSqlHelper.formatDateTime(clauseValue.trim());//日期格式为"yyyy-mm-dd hh:mm:ss"时；
				}else {
					lessDate=CommonSqlHelper.formatDate(clauseValue.trim());
				}
				search.addFilterLessThan(clause,lessDate);
			}else if (clause.indexOf("StringGreaterThan") != -1) {//日期为String类型
				clause = clause.substring(0, clause.indexOf("StringGreaterThan"));
				search.addFilterGreaterThan(clause, clauseValue.trim());
			} else if (clause.indexOf("StringLessThan") != -1) {//日期为String类型
				clause = clause.substring(0, clause.indexOf("StringLessThan"));
				search.addFilterLessThan(clause, clauseValue.trim());
			} else if (clause.indexOf("StringGreaterOrEqual") != -1) {
				clause = clause.substring(0, clause.indexOf("StringGreaterOrEqual"));
				search.addFilterGreaterOrEqual(clause, clauseValue.trim());
			} else if (clause.indexOf("StringLessOrEqual") != -1) {
				clause = clause.substring(0, clause.indexOf("StringLessOrEqual"));
				search.addFilterLessOrEqual(clause, clauseValue.trim());
			} else if (clause.indexOf("DateGreaterOrEqual") != -1) {//日期为Date类型
				clause = clause.substring(0, clause.indexOf("DateGreaterOrEqual"));
				String greateDate="";
				if (clauseValue.trim().length()>11) {
					greateDate=CommonSqlHelper.formatDateTime(clauseValue.trim());//日期格式为"yyyy-mm-dd hh:mm:ss"时；
				}else {
					greateDate= CommonSqlHelper.formatDate(clauseValue.trim());
				}
				search.addFilterGreaterThan(clause, greateDate);
			} else if (clause.indexOf("DateLessOrEqual") != -1) {//日期为Date类型
				clause = clause.substring(0, clause.indexOf("DateLessOrEqual"));
				String lessDate="";
				if (clauseValue.trim().length()>10) {
					lessDate=CommonSqlHelper.formatDateTime(clauseValue.trim());
				}else {
					lessDate=CommonSqlHelper.formatDate(clauseValue.trim());
				}
				search.addFilterLessThan(clause,lessDate);
			}
		}
		return search;
	}
	/**
	 * 
	 *@Description:多表查询条件拼接sql
	 *@date May 16, 2013 11:57:19 AM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@param request
	 *@param whereStr
	 *@return
	 */
	@SuppressWarnings("unchecked")
	public static String getSqlFromRequestMap(HttpServletRequest request , String whereStr) {
		Map requestMap = request.getParameterMap();
		String clause;
		String clauseValue;
		for (Object keyObject : requestMap.keySet()) {
			clause = String.valueOf(keyObject);
			// This operation is safe, view j2ee5 API please.
			clauseValue = ((String[]) requestMap.get(clause))[0].toString();
			// 设置查询条件到request attribute中。
			if (clause.contains(".")) {
				int i=clause.indexOf(".");
				String attr=clause.substring(i+1, clause.length());
				request.setAttribute(attr, clauseValue);
			}else{
				request.setAttribute(clause, clauseValue);
			}
			if (clauseValue.equals("")) {
				continue;
			} else if (clause.indexOf("StringEqual") != -1) {
				clause = clause.substring(0, clause.indexOf("StringEqual"));
				whereStr+=" and "+clause+"='"+clauseValue.trim()+"' ";
			} else if (clause.indexOf("StringLike") != -1) {
				clause = clause.substring(0, clause.indexOf("StringLike"));
				whereStr+=" and "+clause+" like '%"+clauseValue.trim()+"%' ";
			}else if (clause.indexOf("StringLikeBefore")!=-1) {
				clause = clause.substring(0, clause.indexOf("StringLikeBefore"));
				whereStr+=" and "+clause+" like '%"+clauseValue.trim()+"' ";
			}else if (clause.indexOf("StringLikeEnd")!=-1) {
				clause = clause.substring(0, clause.indexOf("StringLikeEnd"));
				whereStr+=" and "+clause+" like '"+clauseValue.trim()+"%' ";
			} else if (clause.indexOf("DateGreaterThan") != -1) {//日期为Date类型
				clause = clause.substring(0, clause.indexOf("DateGreaterThan"));
				String greateDate="";
				if (clauseValue.trim().length()>11) {
					greateDate=CommonSqlHelper.formatDateTime(clauseValue.trim());//日期格式为"yyyy-mm-dd hh:mm:ss"时；
				}else {
					greateDate=CommonSqlHelper.formatDate(clauseValue.trim());
				}
				whereStr+=" and "+clause+" > "+greateDate;
			} else if (clause.indexOf("DateLessThan") != -1) {//日期为Date类型
				clause = clause.substring(0, clause.indexOf("DateLessThan"));
				String lessDate="";
				if (clauseValue.trim().length()>11) {
					lessDate=CommonSqlHelper.formatDateTime(clauseValue.trim());//日期格式为"yyyy-mm-dd hh:mm:ss"时；
				}else {
					lessDate=CommonSqlHelper.formatDate(clauseValue.trim());
				}
				whereStr+=" and "+clause+" < "+lessDate;
			}else if (clause.indexOf("StringGreaterThan") != -1) {//日期为String类型
				clause = clause.substring(0, clause.indexOf("StringGreaterThan"));
				whereStr+=" and "+clause+" > '"+clauseValue.trim()+"' ";
			} else if (clause.indexOf("StringLessThan") != -1) {//日期为String类型
				clause = clause.substring(0, clause.indexOf("StringLessThan"));
				whereStr+=" and "+clause+" < '"+clauseValue.trim()+"' ";
			} else if (clause.indexOf("StringGreaterOrEqual") != -1) {
				clause = clause.substring(0, clause.indexOf("StringGreaterOrEqual"));
				whereStr+=" and "+clause+" > = '"+clauseValue.trim()+"' ";
			} else if (clause.indexOf("StringLessOrEqual") != -1) {
				clause = clause.substring(0, clause.indexOf("StringLessOrEqual"));
				whereStr+=" and "+clause+" < = '"+clauseValue.trim()+"' ";
			} else if (clause.indexOf("DateGreaterOrEqual") != -1) {//日期为Date类型
				clause = clause.substring(0, clause.indexOf("DateGreaterOrEqual"));
				String greateDate="";
				if (clauseValue.trim().length()>11) {
					greateDate=CommonSqlHelper.formatDateTime(clauseValue.trim());//日期格式为"yyyy-mm-dd hh:mm:ss"时；
				}else {
					greateDate=CommonSqlHelper.formatDate(clauseValue.trim());
				}
				whereStr+=" and "+clause+" > = "+greateDate;
			} else if (clause.indexOf("DateLessOrEqual") != -1) {//日期为Date类型
				clause = clause.substring(0, clause.indexOf("DateLessOrEqual"));
				String lessDate="";
				if (clauseValue.trim().length()>11) {
					lessDate=CommonSqlHelper.formatDateTime(clauseValue.trim());//日期格式为"yyyy-mm-dd hh:mm:ss"时；
				}else {
					lessDate=CommonSqlHelper.formatDate(clauseValue.trim());
				}
				whereStr+=" and "+clause+" < ="+lessDate;
			}
		}
		return whereStr;
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
	
	
	
	/**
	 * 根据model的属性，结合hidden里配置的filter条件，组合search
	 * 
	 * @author chenyuanshu
	 * 页面中为了能够进行查询语句的匹配，需要增加一个hidden，hidden的命名规则为model中的属性名+下列的表达式:例如titleStringLike
	 * 目前支持的表达式为以下几种
	 * StringLike,前后模糊查询的即%value%
	 * StringLikeBefore 往前like的%value
	 * StringLikeEnd 往后likealue%
	 * 页面中的h字段名，例如日期的开始和结束，在map中可以放入已日期字段为开头，Begin和End结尾的key即可
	 * DateGreaterThan 日期类型的大于
	 * DateLessThan 日期类型的小于
	 * GreaterThan  除日期类型以外的其他类型的大于
	 * LessThan     除日期类型以外的其他类型的大于
	 * GreaterThanWithEqual  除日期类型以外的其他类型的大于
	 * LessThanWithEqual     除日期类型以外的其他类型的小于 
	 * @param requestMap
	 * @param modelClass
	 * @param
	 * @return
	 */
	public static Search getSqlFromRequestMapWithHidden(Map requestMap,
			Class modelClass, Search search) {
		List<String> propsList = HibernateConfigurationHelper
				.getPropNamesWithoutPK(modelClass, "");
		for (int i = 0; propsList != null && i < propsList.size(); i++) {
			String propName = propsList.get(i);
			if (requestMap.containsKey(propName)
					&& !requestMap.containsKey(propName + "Begin")
					&& !requestMap.containsKey(propName + "End")) {
				addSearchCondition(requestMap, propName, propName, search);
			}
			if (requestMap.containsKey(propName + "Begin")) {
				addSearchCondition(requestMap, propName, propName + "Begin",
						search);
			}
			if (requestMap.containsKey(propName + "End")) {
				addSearchCondition(requestMap, propName, propName + "End",
						search);
			}
		}
		return search;
	}
	/**
	 * 根据model的属性，结合hidden里配置的filter条件，组合search
	 * 
	 * @author chenyuanshu
	 * @param requestMap map对象，key为字段属性名，value为值
	 * @param propName  字段属性名 与model中的属性名相同
	 * @param pageName 页面中的h字段名，例如日期的开始和结束，在map中可以放入已日期字段为开头，Begin和End结尾的key即可
	 * 目前支持的表达式为以下几种
	 * StringLike,前后模糊查询的即%value%
	 * StringLikeBefore 往前like的%value
	 * StringLikeEnd 往后likealue%
	 * DateGreaterThan 日期类型的大于
	 * DateLessThan 日期类型的小于
	 * GreaterThan  其他类型的大于
	 * LessThan     其他类型的大于
	 * GreaterThanWithEqual  其他类型的大于等于
	 * LessThanWithEqual     其他类型的小于等于 
	 * @param  search search对象，由于是java的类地址调用，所以该方法无需返回值          
	 * @return
	 */
	private static void addSearchCondition(Map requestMap, String propName,
			String pageName, Search search) {
		Object[] obj;
		Object tempObj = requestMap.get(pageName);
		if (tempObj.getClass().isArray()) {
			obj = (Object[]) tempObj;
			tempObj = obj[0];
		}
		String propValue = StaticMethod.nullObject2String(tempObj).trim();
		if (!propValue.equals("")) {
			if (requestMap.containsKey(pageName + "StringEqual")) {
				search.addFilterEqual(propName, propValue);
			} else if (requestMap.containsKey(pageName + "StringLike")) {
				search.addFilterLike(propName, "%" + propValue + "%");
			}else if (requestMap.containsKey(pageName + "StringLikeBefore")) {
				search.addFilterLike(propName, "%" + propValue + "");
			}else if (requestMap.containsKey(pageName + "StringLikeEnd")) {
				search.addFilterLike(propName, "" + propValue + "%");
			} else if (requestMap.containsKey(pageName + "DateGreaterThan")) {
				search.addFilterGreaterThan(propName, stringToDate(propValue));
			} else if (requestMap.containsKey(pageName + "DateLessThan")) {
				search.addFilterLessThan(propName, stringToDate(propValue));
			} else if (requestMap.containsKey(pageName + "GreaterThanWithEqual")) {
				search.addFilterGreaterOrEqual(propName, propValue);
			} else if (requestMap.containsKey(pageName + "LessThanWithEqual")) {
				search.addFilterLessOrEqual(propName, propValue);
			}else if (requestMap.containsKey(pageName + "GreaterThan")) {
				search.addFilterGreaterThan(propName, propValue);
			} else if (requestMap.containsKey(pageName + "LessThan")) {
				search.addFilterLessThan(propName, propValue);
			}
		}
	}
	
	public static Date stringToDate(String s) {
		Date date = new Date();
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (!s.equals("")) {
				date = df.parse((String) s);
			} else {
				date = null;
			}
		} catch (ParseException e) {
			;
		}
		return date;
	}

	/**
	 * 支持泛型的获取Service的方式
	 * @param <T>
	 * @param t
	 * @param beanName
	 * @return
	 */
	public static <T> T getService(Class<T> t, String... beanName) {
        if (beanName != null && beanName.length == 1) {
               return (T) ApplicationContextHolder.getInstance().getBean((beanName[0]));
        } else {
               String source = t.getSimpleName();
               return (T) ApplicationContextHolder.getInstance().getBean(
                            (source.substring(0, 1).toLowerCase().concat(source.substring(1))));
        }
	}


	public static int getFirstResultOfDisplayTag(HttpServletRequest request,
			String displayTableName) {
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						displayTableName)
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		return firstResult;
	}
	
	public static String upperFirst(String str) {
		return str =str.replaceFirst(str.substring(0, 1),str.substring(0, 1).toUpperCase());
	}
	public static String lowerFirst(String str) {
		return str =str.replaceFirst(str.substring(0, 1),str.substring(0, 1).toLowerCase());
	}
	/**
	 * 
	* @Title: getDisDays 
	* @Description: 获得2个String类型格式为"yyyy-mm-dd"日期相差的天数;
	* @param 
	* @Time:Nov 25, 2012-6:22:49 PM
	* @Author:fengguangping
	* @return long    返回类型 
	* @throws
	 */
	public static long getDisDays(String startTime,String endTime){
		Date startDate;
		Date endDate;
		long totalDate = 0;   
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");    
			startDate = sdf.parse(startTime);      
			endDate = sdf.parse(endTime);
			Calendar calendar = Calendar.getInstance();   
			calendar.setTime(startDate);   
			long timestart = calendar.getTimeInMillis();   
			calendar.setTime(endDate);   
			long timeend = calendar.getTimeInMillis();   
			totalDate = Math.abs((timeend - timestart))/(1000*60*60*24);  
		} catch (ParseException e) {
			throw new RuntimeException("格式转换异常，要求时间格式为\"yyyy-mm-dd\"");
		}
        return totalDate;
	}
	
	/**
	 * 将jdbc查出的ResultSet转换为List<Map<String,Object>> by zhangkeqi 2013-01-17
	 * @param rs
	 * @return
	 */
	public static List<Map<String,Object>> resultSet2ListMap(ResultSet rs) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<Integer,String> columnNames = new HashMap<Integer,String>();
		try {
			int columnCount = rs.getMetaData().getColumnCount();
			ResultSetMetaData rsmd = rs.getMetaData();
			for(int i = 1;i<=columnCount;i++) {
				columnNames.put(i, rsmd.getColumnName(i));
			}
			while(rs.next()) {
				Map<String,Object> rowMap = new HashMap<String,Object>();
				for(int j = 1;j<=columnCount;j++) {
					rowMap.put(columnNames.get(j), rs.getObject(j));
				}
				list.add(rowMap);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
