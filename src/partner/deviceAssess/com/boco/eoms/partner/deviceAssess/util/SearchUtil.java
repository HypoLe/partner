package com.boco.eoms.partner.deviceAssess.util;

import java.util.HashMap;
import java.util.Map;

public class SearchUtil {
	
	/**
	 * 拼接查询条件
	 * @author Zhangkeqi
	 * @param prefix 
	 * @since August,2011
	 * 
	 */
	public static String getSqlFromRequestMap(Map requestMap, String prefix) {
		Map<String,String> whereMap = new HashMap<String,String>();
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
				if(prefix != null && !"".equals(prefix)) {
					whereMap.put(prefix+"."+clause+"=", clauseValue.trim());
				} else {
					whereMap.put(clause+"=", clauseValue.trim());
				}
			} else if (clause.indexOf("StringLike") != -1) {
				clause = clause.substring(0, clause.indexOf("StringLike"));
				if(prefix != null && !"".equals(prefix)) {
					whereMap.put(prefix+"."+clause+" like ","%" + clauseValue.trim() + "%");
				} else {
					whereMap.put(clause+" like ","%" + clauseValue.trim() + "%");
				}
			} else if (clause.indexOf("DateGreaterThan") != -1) {
				clause = clause.substring(0, clause.indexOf("DateGreaterThan"));
				if(prefix != null && !"".equals(prefix)) {
					whereMap.put(prefix+"."+clause+">","\'"+clauseValue.trim()+"\'");
				} else {
					whereMap.put(clause+">","\'"+clauseValue.trim()+"\'");
				}
			} else if (clause.indexOf("DateLessThan") != -1) {
				clause = clause.substring(0, clause.indexOf("DateLessThan"));
				if(prefix != null && !"".equals(prefix)) {
					whereMap.put(prefix+"."+clause+"<","\'"+clauseValue.trim()+"\'");
				} else {
					whereMap.put(clause+"<","\'"+clauseValue.trim()+"\'");
				}
			} else {

			}
		}
		
		String whereStr = "";
		String value="";
		int size = whereMap.size();
		int i = 1;
		if(!whereMap.isEmpty()) {
			whereStr = " where ";
			for(String key : whereMap.keySet()) {
				if(i != size) {
					whereStr += key + whereMap.get(key);
					whereStr += " and ";
				} else {
					whereStr += key + whereMap.get(key);
				}
				i++;
			}
		}
		return whereStr;
	}
}
