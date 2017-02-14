<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.boco.eoms.partner.geo.client.*" %>
<%@page import="com.boco.eoms.partner.geo.model.*" %>
<%@page import="com.boco.eoms.partner.geo.parse.*" %>

<%@page import="com.google.gson.*"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="com.boco.eoms.partner.geo.util.DateTypeAdapter"%>
<%@page import="com.boco.eoms.partner.geo.util.TimestampTypeAdapter"%>

<%
	String resourceId = ParseUtils.nullObject2String( request.getParameter("resourceId"));
	String resourceType = ParseUtils.nullObject2String( request.getParameter("resourceType"));
	Map querryMap = null;
	if(!"".equals(resourceId)&&!"".equals(resourceType)){
		querryMap = new HashMap();
		querryMap.put("a_srcId",resourceId);
		querryMap.put("a_srcType",resourceType);
		ResouceInfoLocationsInfo info = new ResouceInfoLocationsInfo();
		info.updateResourceStatus(resourceId,resourceType);
		ResourceGeoInfo resourceInfo = info.querryResouceInfoLocus(querryMap);

		Gson gson = new GsonBuilder().registerTypeAdapter(Timestamp.class,new  TimestampTypeAdapter()).setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String jsonString = gson.toJson(resourceInfo,ResourceGeoInfo.class);
		System.out.println(jsonString);
	
		out.print(jsonString);
		
	}
	
%>
