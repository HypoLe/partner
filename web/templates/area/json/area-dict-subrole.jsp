<%@ include file="/common/header_tpl_json.jsp"%>
<%@ page import="com.boco.eoms.commons.system.area.model.TawSystemArea"%>
<%@ page import="com.boco.eoms.commons.system.dict.model.TawSystemDictType"%>
<%@ page import="com.boco.eoms.commons.system.role.model.TawSystemSubRole"%>
<%@ page import="com.boco.eoms.commons.system.role.service.IRoleMgr"%>
<%@ page import="com.boco.eoms.base.util.ApplicationContextHolder"%>
<% 
	List arealist = (List)request.getAttribute("list");
	List dictlist = (List)request.getAttribute("dictlist");
	List subrolelist = (List)request.getAttribute("subrolelist");
	JSONArray json = new JSONArray();
	JSONObject jitem = null;
	int size = 0;
	
	if (arealist.size() > 0 ){
		size = arealist.size();
		TawSystemArea area = null;
		
		for (int a = 0; a < size; a++){
			area = (TawSystemArea) arealist.get(a);
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
	
	if (dictlist.size() > 0){
		size = dictlist.size();
		TawSystemDictType dict = null;
		
    	for (int i = 0; i < size; i++) {
			dict = (TawSystemDictType) dictlist.get(i);			
			jitem = new JSONObject();
			jitem.put(UIConstants.JSON_ID, dict.getDictId());
			jitem.put(UIConstants.JSON_TEXT, dict.getDictName());
			jitem.put(UIConstants.JSON_NODETYPE, UIConstants.NODETYPE_DICT);
			jitem.put(UIConstants.JSON_ICONCLS, UIConstants.NODETYPE_DICT);	 
			json.put(jitem);
		}
	}
	
	if(subrolelist.size()>0){	
		IRoleMgr subroleMgr = (IRoleMgr) ApplicationContextHolder.getInstance().getBean("RoleMgrFlush");
		size = subrolelist.size();
		TawSystemSubRole subrole = null;
		
		for (int j = 0; j < size; j++) {		
			subrole = (TawSystemSubRole) subrolelist.get(j);
			jitem = new JSONObject();
			jitem.put(UIConstants.JSON_ID, subrole.getId());
			jitem.put(UIConstants.JSON_TEXT, subrole.getSubRoleName());
			jitem.put(UIConstants.JSON_NODETYPE, UIConstants.NODETYPE_SUBROLE);
			jitem.put(UIConstants.JSON_ICONCLS, UIConstants.NODETYPE_SUBROLE);
			
			String[] leaderInfo = subroleMgr.getRoleLeaderBySubRoleid(subrole.getId());
			if(leaderInfo != null){
				jitem.put("leaderId", leaderInfo[0]);
				jitem.put("leaderName", leaderInfo[1]);
			}
			json.put(jitem);
		}
	}
	out.print(json);
%>
