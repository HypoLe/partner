<%@ page pageEncoding="UTF-8" %>
<%@ include file="/common/header_tpl_json.jsp"%>
<%@ page import="com.boco.eoms.commons.system.area.model.TawSystemArea"%>
<% 
	List arealist = (List)request.getAttribute("list");
	JSONArray json = new JSONArray();
	JSONObject jitem = null;
	int size = 0;
	
	if (arealist.size() > 0 ){
		size = arealist.size();
		TawSystemArea area = null;
		
		for (int i = 0; i < size; i++){
			area = (TawSystemArea) arealist.get(i);
			jitem = new JSONObject();
			jitem.put(UIConstants.JSON_ID, area.getAreaid());
			jitem.put(UIConstants.JSON_TEXT, area.getAreaname());
			jitem.put(UIConstants.JSON_NODETYPE, UIConstants.NODETYPE_AREA);
			jitem.put(UIConstants.JSON_ICONCLS, UIConstants.NODETYPE_AREA);
			jitem.put("areacode", area.getAreacode());
			jitem.put("capital", area.getCapital());
			json.put(jitem);		
		}
	}
	
	jitem = new JSONObject();
	jitem.put(UIConstants.JSON_ID, "other");
	jitem.put(UIConstants.JSON_TEXT, "其他");
	jitem.put(UIConstants.JSON_NODETYPE, "other");
	jitem.put(UIConstants.JSON_ICONCLS, "other");
	json.put(jitem);
	
	out.print(json);
%>