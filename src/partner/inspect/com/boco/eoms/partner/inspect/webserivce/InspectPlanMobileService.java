package com.boco.eoms.partner.inspect.webserivce;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.partner.inspect.mgr.impl.InspectPlanExecuteMgrImpl;
import com.boco.eoms.partner.inspect.mgr.impl.InspectPlanResMgrImpl;
import com.boco.eoms.partner.inspect.model.InspectPlanMain;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.googlecode.genericdao.search.Search;

/** 
 * Description:  
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     LEE 
 * @version:    1.0 
 * Create at:   Jul 18, 2012 3:39:30 PM 
 */
public class InspectPlanMobileService {

	
	@Test
	public void testQueryInspectPlanMain(){
		
		String where = "[{\"value\":\"value1\",\"row\":\"first\",\"col\":\"2\"}]";
		queryInspectPlanMain(where);
	}
	public String queryInspectPlanMain(String where){
//		InspectPlanResMgrImpl inspectPlanResMgrImpl = (InspectPlanResMgrImpl) ApplicationContextHolder.getInstance().getBean("inspectPlanResMgrImpl");
		InspectPlanExecuteMgrImpl inspectPlanExecuteMgrImpl = (InspectPlanExecuteMgrImpl) ApplicationContextHolder.getInstance().getBean("inspectPlanExecuteMgrImpl");
//		Map<String,Object> queryInspectPlanMain(Map<String, String> queryMap,Search search)
		Map<String,String> queryMap = new HashMap<String,String>();
		JSONArray array = new JSONArray(where);
		for(int i = 0;i<array.length();i++){
			JSONObject obj = array.getJSONObject(i);
			Iterator it0 = obj.keys();
			for (Iterator it = it0; it.hasNext();) {
				String key = it.next()+"";
	            queryMap.put(key, obj.get(key)+"");
	            System.out.println("json value    "+queryMap.get(key));
	        }
		}
//		Iterator keys()

		Map<String,Object> map = inspectPlanExecuteMgrImpl.queryInspectPlanMain(queryMap,null);
		List<InspectPlanMain> returnList = null;
		if(null != map&&!map.isEmpty()){
			returnList = (List<InspectPlanMain>)map.get("list");
		}
		Gson gson = new Gson();
		String retunString = "";
		if(null != returnList&&!returnList.isEmpty()){
			retunString = gson.toJson(returnList);
		}
		return retunString;
		
	}
	public String queryInspectPlanRes(String where){
//		InspectPlanExecuteMgrImpl inspectPlanExecuteMgrImpl = (InspectPlanExecuteMgrImpl) ApplicationContextHolder.getInstance().getBean("InspectPlanExecuteMgrImpl");
		InspectPlanResMgrImpl inspectPlanResMgrImpl = (InspectPlanResMgrImpl) ApplicationContextHolder.getInstance().getBean("inspectPlanResMgrImpl");
		InspectPlanExecuteMgrImpl inspectPlanExecuteMgrImpl = (InspectPlanExecuteMgrImpl) ApplicationContextHolder.getInstance().getBean("inspectPlanExecuteMgrImpl");
//		inspectPlanResMgrImpl.queryInspectPlanMain();
		return "queryInspectPlanRes";
		
	}
	public String testSend(){
		return "hello this is a test";
		
	}
	
}
