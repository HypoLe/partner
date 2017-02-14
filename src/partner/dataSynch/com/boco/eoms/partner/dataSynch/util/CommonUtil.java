package com.boco.eoms.partner.dataSynch.util;

import java.util.UUID;

/**
 * 类说明：通用工具
 * 创建人： zhangkeqi
 * 创建时间：2012-12-01 18:05:58
 */
public class CommonUtil {
	
	/**
	 * 表名转类名
	 * @param sqlTable
	 * @return
	 */
	public static String sqlTable2JavaClass(String sqlTable) {
		char[] sqlChar = sqlTable.toCharArray();
		char c;
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<sqlChar.length;i++) {
			c = sqlChar[i];
			if(i==0) {
				c = Character.toUpperCase(c);
				sb.append(c);
			} else if(c == '_') {
				if(i != (sqlChar.length - 1)) {
					c = sqlChar[++i];
					c = Character.toUpperCase(c);
					sb.append(c);
					continue;
				}
			} else {
				sb.append(c);
			}
		}
		
		return sb.toString();
	}
	
	
	/**
	 * 大写首字母
	 * @param str
	 * @return
	 */
	public static String upperFirst(String str) {
		return str =str.replaceFirst(str.substring(0, 1),str.substring(0, 1).toUpperCase());
	}
	/**
	 * 小写首字母
	 * @param str
	 * @return
	 */
	public static String lowerFirst(String str) {
		return str =str.replaceFirst(str.substring(0, 1),str.substring(0, 1).toLowerCase());
	}
	
	/**
	 * 驼峰类名转换成大写字线变下划线加小写字母
	 * @param className
	 * @return
	 */
	public static String className2DBTable(String className) {
		char[] sqlChar = className.toCharArray();
		char c;
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<sqlChar.length;i++) {
			c = sqlChar[i];
			if(i==0) {
				c = Character.toLowerCase(c);
				sb.append(c);
			} else if(Character.isUpperCase(c)) {
				if(i != (sqlChar.length - 1)) {
					c = sqlChar[i];
					c = Character.toLowerCase(c);
					sb.append("_");
					sb.append(c);
					continue;
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	public static String generateUUID() {
		String str = UUID.randomUUID().toString();
		String uuid = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);
		return uuid;
	}
	
	public static void main(String[]args) {
		String str = "taw_system_user";
		
		str = upperFirst(str);
		
		System.out.println(str);
	}
}
