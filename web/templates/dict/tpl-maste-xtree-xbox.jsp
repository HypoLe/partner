<%@ include file="/common/header_tpl_json.jsp"%>
<%@page import="org.apache.commons.collections.map.ListOrderedMap"%>

<%-- According to the department of some key did not change, also didn't study to understand the meaning of some of the key--%>
<% 
	List<ListOrderedMap> dictslist = (List)request.getAttribute("list");
	String popedom = (String)request.getAttribute("popedom");
	String userdeptid = (String)request.getAttribute("userdeptid");
	String showPartnerLevelType = (String)request.getAttribute("showPartnerLevelType");
	String selectType = (String)request.getAttribute("selectType");
	Integer isPartner =(Integer)request.getAttribute("isPartner");
	if(isPartner==null){
		isPartner=-1;
	}

	JSONArray json = new JSONArray();
	
	for (ListOrderedMap listOrderedMap : dictslist) {
		
		JSONObject jitem = new JSONObject();
		System.out.println("--------copy_no:"+((String)listOrderedMap.get("copy_no")));
		jitem.put(UIConstants.JSON_ID, ((String)listOrderedMap.get("copy_no")));
		jitem.put(UIConstants.JSON_TEXT, ((String)listOrderedMap.get("copy_name")));
		jitem.put(UIConstants.JSON_NODETYPE, UIConstants.NODETYPE_DEPT);
		jitem.put("deptLevel","4");

		jitem.put("iconCls", "dict");
		jitem.put("leaf", 1);

		if(!"".equals(showPartnerLevelType)&&null != showPartnerLevelType){
			jitem.put("showPartnerLevelType", showPartnerLevelType);
		}
		json.put(jitem);
	}
	
	
	out.print(json);
%>
