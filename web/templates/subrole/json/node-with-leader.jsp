<%-- 输出子角色的JSON，如果配置了组长，子角色节点中也带有组长的id和名称 --%>
<%@ include file="/common/header_tpl_json.jsp"%>
<%@ page import="com.boco.eoms.commons.system.role.model.TawSystemSubRole"%>
<%@ page import="com.boco.eoms.commons.system.role.service.IRoleMgr"%>
<%@ page import="com.boco.eoms.base.util.ApplicationContextHolder"%>
<% 
	List subrolelist = (List)request.getAttribute("list");
	JSONArray json = new JSONArray();
	IRoleMgr subroleMgr = (IRoleMgr) ApplicationContextHolder.getInstance().getBean("RoleMgrFlush");	
	if (subrolelist.size() > 0) {
		for (int j = 0; j < subrolelist.size(); j++) {
			TawSystemSubRole subrole = (TawSystemSubRole) subrolelist.get(j);
			JSONObject obj = new JSONObject();
			obj.put(UIConstants.JSON_ID, subrole.getId());
			obj.put(UIConstants.JSON_TEXT, subrole.getSubRoleName());
			obj.put(UIConstants.JSON_NODETYPE, UIConstants.NODETYPE_SUBROLE);
			obj.put(UIConstants.JSON_ICONCLS, UIConstants.NODETYPE_SUBROLE);
			obj.put("leaf", 0);
			
			String[] leaderInfo = subroleMgr.getRoleLeaderBySubRoleid(subrole.getId());
			if(leaderInfo != null){
				obj.put("leaderId", leaderInfo[0]);
				obj.put("leaderName", leaderInfo[1]);
			}
			json.put(obj);
		}
	}
	out.print(json);
%>
