<%@ include file="/common/header_tpl_json.jsp"%>
<%@page import="com.boco.eoms.base.util.ApplicationContextHolder"%>
<%@page import="com.boco.eoms.commons.system.dict.model.TawSystemDictType"%>
<%@page import="com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager"%>
<%-- According to the department of some key did not change, also didn't study to understand the meaning of some of the key--%>
<% 
	List dictslist = (List)request.getAttribute("list");
	String popedom = (String)request.getAttribute("popedom");
	String userdeptid = (String)request.getAttribute("userdeptid");
	String showPartnerLevelType = (String)request.getAttribute("showPartnerLevelType");
	String selectType = (String)request.getAttribute("selectType");
	Integer isPartner =(Integer)request.getAttribute("isPartner");
	if(isPartner==null){
		isPartner=-1;
	}
	ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) ApplicationContextHolder.getInstance().getBean("ItawSystemDictTypeManager");

	JSONArray json = new JSONArray();
	if (dictslist.size() > 0){
    	for (int i = 0; i < dictslist.size(); i++) {
    		TawSystemDictType subDept = (TawSystemDictType) dictslist.get(i);			
			JSONObject jitem = new JSONObject();
			jitem.put(UIConstants.JSON_ID, subDept.getDictId());
			jitem.put(UIConstants.JSON_TEXT, subDept.getDictName());
			jitem.put(UIConstants.JSON_NODETYPE, UIConstants.NODETYPE_DEPT);
			jitem.put("deptMagId", subDept.getParentDictId());
			jitem.put("deptLevel", subDept.getSysType());
			jitem.put("iconCls", "dict");
			List flagdept = null;
			if("true".equals(popedom)){
				flagdept = (ArrayList) mgr.getDictSonsByDictid(subDept.getDictId());
			}
			else
				flagdept = (ArrayList) mgr.getDictSonsByDictid(subDept.getDictId());
				if (flagdept == null || flagdept.size() <= 0 || "inspect".equals(selectType)) {
					jitem.put("leaf", 1);
				} else {
				jitem.put("leaf", 0);
			}
			if(!"".equals(showPartnerLevelType)&&null != showPartnerLevelType){
				jitem.put("showPartnerLevelType", showPartnerLevelType);
			}
			json.put(jitem);				
		}
	}
	out.print(json);
%>
