<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.boco.eoms.partner.geo.client.*" %>
<%@page import="com.boco.eoms.partner.geo.model.*" %>
<%@page import="com.boco.eoms.partner.geo.parse.*" %>

<%@page import="com.google.gson.*"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="com.boco.eoms.partner.geo.util.DateTypeAdapter"%>
<%@page import="com.boco.eoms.partner.geo.util.TimestampTypeAdapter"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>

<%
	String resourceId = ParseUtils.nullObject2String( request.getParameter("resourceId"));
	String resourceType = ParseUtils.nullObject2String( request.getParameter("resourceType"));
	Map querryMap = null;
	if(!"".equals(resourceId)&&!"".equals(resourceType)){
		querryMap = new HashMap();
		querryMap.put("a_srcId",resourceId);
		querryMap.put("a_srcType",resourceType);
		querryMap.put("a_startTime",ParseUtils.currentDate()+" 00:00:00");
		querryMap.put("a_endTime",ParseUtils.currentDate()+" 23:59:59");
		
		ResouceInfoLocationsInfo info = new ResouceInfoLocationsInfo();
	//	info.updateResourceStatus(resourceId,resourceType);
		List list = info.querryResouceInfoHistoryLocus(querryMap);
		
		List xArray = new ArrayList();
		List yArray = new ArrayList();
		ResourceGeoInfo temp = null;
		for(int i=0;i<list.size();i++){
			temp = (ResourceGeoInfo)list.get(i);
			if(temp.getLongitude()!= null&&!"".equals(temp.getLongitude())){
				xArray.add(temp.getLongitude());
				yArray.add(temp.getLatitude());
			}
		}
		
		Map listMap = new HashMap();
		listMap.put("xArray",xArray);
		listMap.put("yArray",yArray);
		
		Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class,new  TimestampTypeAdapter()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	
		String jsonString = gson.toJson(listMap,Map.class);
		System.out.println(jsonString);
		out.print(jsonString);
		
	}
	


%>
