<%@ include file="/common/header_tpl_json.jsp"%>
<%@page import="com.boco.eoms.partner.baseinfo.model.PartnerDept"%>
<%@page import="com.boco.eoms.commons.system.dept.service.bo.TawSystemDeptBo"%>
<% 
	List deptlist = (List)request.getAttribute("deptlist");
	String popedom = (String)request.getAttribute("popedom");
	String userdeptid = (String)request.getAttribute("userdeptid");
	String showPartnerLevelType = (String)request.getAttribute("showPartnerLevelType");
	String selectType = (String)request.getAttribute("selectType");
	Integer isPartner =(Integer)request.getAttribute("isPartner");
	if(isPartner==null){
		isPartner=-1;
	}
	TawSystemDeptBo deptbo = TawSystemDeptBo.getInstance();
	JSONArray json = new JSONArray();
	if (deptlist.size() > 0){
    	for (int i = 0; i < deptlist.size(); i++) {
    		PartnerDept subDept = (PartnerDept) deptlist.get(i);			
			JSONObject jitem = new JSONObject();
			jitem.put(UIConstants.JSON_ID, subDept.getId());
			jitem.put(UIConstants.JSON_TEXT, subDept.getName());
			jitem.put(UIConstants.JSON_NODETYPE, UIConstants.NODETYPE_DEPT);
			jitem.put("deptMagId", subDept.getDeptMagId());
			jitem.put("deptLevel", subDept.getDeptLevel());
			jitem.put("iconCls", "dept");
			List flagdept = null;
			if("true".equals(popedom)){
				flagdept = (ArrayList) deptbo.getNextLevecComp(subDept.getId(), "0",userdeptid,isPartner);
			}
			else
				flagdept = (ArrayList) deptbo.getNextLevecComp(subDept.getId(), "0","",isPartner);
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

