<%@ include file="/common/header_tpl_json.jsp"%>
<%@page import="com.boco.eoms.partner.appops.model.IPnrPartnerAppOpsDept"%>
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
    		IPnrPartnerAppOpsDept subDept = (IPnrPartnerAppOpsDept) deptlist.get(i);			
			JSONObject jitem = new JSONObject();
			jitem.put(UIConstants.JSON_ID, subDept.getId());
			jitem.put(UIConstants.JSON_TEXT, subDept.getName());
			
			jitem.put("deptMagId", subDept.getDeptMagId());
			jitem.put("deptLevel", subDept.getDeptLevel());
			jitem.put("iconCls", "dept");
			List flagdept = null;
			if("true".equals(popedom)){
				flagdept = (ArrayList) deptbo.getNextLevecCompDW(subDept.getId(), "0",userdeptid,isPartner);
			}
			else
				flagdept = (ArrayList) deptbo.getNextLevecCompDW(subDept.getId(), "0","",isPartner);
				if (flagdept == null || flagdept.size() <= 0 || "inspect".equals(selectType)) {
					jitem.put("leaf", 1);
					if(subDept.getDeptLevel().equals("4")){
						jitem.put(UIConstants.JSON_NODETYPE, "dept");
					}else{
						jitem.put(UIConstants.JSON_NODETYPE, "");
					}
				} else {
				if(!subDept.getDeptLevel().equals("3")){
					jitem.put("leaf", 0);
					jitem.put(UIConstants.JSON_NODETYPE, "");
				}else{
					jitem.put("leaf", 1);
					jitem.put(UIConstants.JSON_NODETYPE, UIConstants.NODETYPE_DEPT);
				}
				
			}
			if(!"".equals(showPartnerLevelType)&&null != showPartnerLevelType){
				jitem.put("showPartnerLevelType", showPartnerLevelType);
			}
			
			
			json.put(jitem);				
		}
	}
	out.print(json);
%>

