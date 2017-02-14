<%@ include file="/common/header_tpl_json.jsp"%>
<%@ page pageEncoding="UTF-8" %>
<%@page import="com.boco.eoms.commons.system.dept.model.TawSystemDept"%>
<%@page import="com.boco.eoms.commons.system.user.model.TawSystemUser"%>
<%@page import="com.boco.eoms.commons.system.user.service.bo.impl.TawSystemUserRoleBo"%>
<%@page import="com.boco.eoms.commons.system.dept.service.bo.TawSystemDeptBo"%>
<%@page import="com.boco.eoms.commons.system.role.util.RoleIdList"%>
<%@page import="com.boco.eoms.commons.system.role.model.TawSystemSubRole"%>
<%@page import="com.boco.eoms.commons.system.area.model.TawSystemArea"%>
<%@page import="com.boco.eoms.commons.system.area.service.ITawSystemAreaManager"%>
<%@ page import="com.boco.eoms.base.util.ApplicationContextHolder"%>
<%@ page import="com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr"%>
<%@ page import="com.boco.eoms.partner.baseinfo.model.PartnerUser"%>
<%@ page import="com.boco.eoms.partner.baseinfo.mgr.PartnerUserMgr"%>


<%
	List deptlist = (List)request.getAttribute("deptlist");
	List userlist = (List)request.getAttribute("userlist");
	JSONArray json = new JSONArray();
			
	TawSystemUserRoleBo userrolebo = TawSystemUserRoleBo.getInstance();
	TawSystemDeptBo deptbo = TawSystemDeptBo.getInstance();
		
	RoleIdList roleIdList = (RoleIdList)request.getAttribute("roleIdList");
	List roleList = (List)request.getAttribute("roleList");
	String userid = (String)request.getAttribute("userid");
	String hasRight = "0";
    String parentHasRight = (String)request.getAttribute("hasRight");
	//if(userid.equals("admin")){
	//	hasRight = "1";
	//}
	hasRight = "1";
	if(parentHasRight.equals("1"))hasRight = "1";
	
	ITawSystemAreaManager areaManager = (ITawSystemAreaManager)request.getAttribute("areaManager");
	if (deptlist.size() > 0){
		TawSystemDept dept = null;
    	for (int i = 0; i < deptlist.size(); i++) {

			dept = (TawSystemDept) deptlist.get(i);	
			
			
			
			
			String deptId = dept.getDeptId();
			
			String deptName = dept.getDeptName();
			String isPartners = dept.getIsPartners();
			
			if(parentHasRight!=null&&parentHasRight.equals("0")){//父部门没有权限，那么判断当前部门是否有权限
			TawSystemSubRole tempRole = null;	
			TawSystemArea area = null;
			String areaId = null;//用于循环地域
			for(int k=0;k<roleList.size();k++){
				tempRole = (TawSystemSubRole)roleList.get(k);
				if(tempRole.getRoleId() == roleIdList.getUserAdminRoleId().intValue()){
				    if((tempRole.getDeptId()!=null&&!tempRole.getDeptId().equals(""))&&(tempRole.getArea()==null||tempRole.getArea().equals(""))){//子角色 只存了地市id，没存部门id
				       area = areaManager.getAreaByAreaId(tempRole.getDeptId());
				       if(tempRole.getDeptId().equals(dept.getAreaid())){//属于这个地市的部门都有权限
							hasRight = "1";
							break;
						}
						else if(area!=null&&area.getId()!=null&&!area.getLeaf().equals("1")){//如果地域有子地域（如广西下有南宁），那么得判断部门是不是在子地域，在父地域下有权限那么在子地域下肯定有权限
						    areaId = dept.getAreaid();
						    while(areaId!=null&&!areaId.equals("-1")){
						       area = areaManager.getAreaByAreaId(areaId);
						       if(area.getParentAreaid().equals(tempRole.getDeptId())){
						           	hasRight = "1";
							        break;
						       }
						       else {
						           areaId = area.getParentAreaid();
						           hasRight = "0";
						       }
						    }
						    if(hasRight.equals("1")) break;
						}
						else hasRight = "0";
					}
				    else if((tempRole.getArea()!=null&&!tempRole.getArea().equals(""))&&(tempRole.getDeptId()==null||tempRole.getDeptId().equals(""))){//子角色 只存了部门id，没存地市id
				        if(tempRole.getArea().equals(dept.getDeptId())){//部门是子角色所选部门，或者部门是子角色所选部门的子部门，给其权限
			            	hasRight = "1";
					        break;
				        }
				        else hasRight = "0";
				    }
				    else if((tempRole.getDeptId()!=null&&!tempRole.getDeptId().equals(""))&&(tempRole.getArea()!=null&&!tempRole.getArea().equals(""))){//子角色既存了部门id，又存了地市id
				        areaId = dept.getAreaid();
			            String isArea = "0";
			            while(areaId!=null&&!areaId.equals("-1")){//判断当前部门是否在子角色规定的地域和子地域里，如果在就判断部门是否是子角色规定的部门或子部门
					       area = areaManager.getAreaByAreaId(areaId);
					       if(area.getAreaid().equals(tempRole.getDeptId())){
					           	isArea = "1";
						        break;
					       }
					       else {
					           areaId = area.getParentAreaid();
					           isArea = "0";
					       }
				        }
				        if(isArea.equals("1")){//当前部门在子角色规定的地域或子地域里，那么判断当前部门是否是子角色规定的部门或子部门
				            if(tempRole.getArea().equals(dept.getDeptId())){
				            	hasRight = "1";
						        break;
					        }
					        else hasRight = "0";
				        }					       
				        else hasRight = "0";
				    }
					else hasRight = "0";
				}
			} 
			}
				
			PartnerDeptMgr pdm = (PartnerDeptMgr)ApplicationContextHolder.getInstance().getBean("partnerDeptMgr");
			List pdList = pdm.getPartnerDeptsByHql("partnerDept.deptMagId='"+deptId+"'");
			
			
			JSONObject jitem = new JSONObject();
			jitem.put(UIConstants.JSON_ID, dept.getDeptId());
			jitem.put(UIConstants.JSON_TEXT, deptName);
			jitem.put(UIConstants.JSON_NODETYPE, "1".equals(isPartners) ? "partner-dept" : "dept");
			if(hasRight.equals("1")){
			    jitem.put("allowChild", true);
			    jitem.put("iconCls", "dept");
			}
			else {
			    jitem.put("allowChild", false);
			    jitem.put("iconCls", "dept-readonly");
			}
			jitem.put("hasRight", hasRight);
			jitem.put("allowDelete", false);
			jitem.put("allowEdit", false);
			
			String deptRootId = dept.getParentDeptid();
			int isPartnerRoot = 0;
			if(dept.getDeptName() != null && !"".equals(dept.getDeptName())) {
				isPartnerRoot = dept.getDeptName().toString().indexOf("合作");
			}
			if(pdList!=null && pdList.size()>0) {
				jitem.put("isPartnerDept",true);
			} else {
				if("-1".equals(deptRootId) && isPartnerRoot!=-1) {
					jitem.put("isPartnerDept",true);
				} else {
					jitem.put("isPartnerDept",false);
				}
			}
//			if("1".equals(isPartners)){
//			  jitem.put("iconCls", "partner-dept");
//			}
//		    else{
//				jitem.put("iconCls", "dept");
//			}
				 
			//判断是否还有子节点
			List flaguser = userrolebo.getUserBydeptids(deptId);
			List flagdept = deptbo.getNextLevecDepts(deptId,"0");
			if (flagdept == null || flagdept.size() <= 0) {
				if (flaguser == null || flaguser.size() <= 0) {
					jitem.put("leaf", 1);
				}
			} else {
				jitem.put("leaf", 0);
			}	
			json.put(jitem);
		}
	}
		
	if (userlist.size() > 0) {
	    TawSystemUser user = null;
		for (int j = 0; j < userlist.size(); j++) {
			user = (TawSystemUser) userlist.get(j);

			JSONObject jitem = new JSONObject();
			jitem.put(UIConstants.JSON_ID, user.getId());
			jitem.put(UIConstants.JSON_TEXT, user.getUsername());
			jitem.put(UIConstants.JSON_NODETYPE, "user");
			jitem.put("mobile", user.getMobile());
			jitem.put("iconCls", "user");
			jitem.put("leaf", 1);
			jitem.put("hasRight",parentHasRight);
			if(parentHasRight.equals("1")){
			    jitem.put("allowDelete", true);
			    jitem.put("allowEdit", true);
			    jitem.put("allowsubroleList-mi", true);
			    jitem.put("allowadjustsubrole-mi", true);
			}
			else {
			    jitem.put("allowDelete", false);
			    jitem.put("allowEdit", false);
			    jitem.put("allowsubroleList-mi", false);
			    jitem.put("allowadjustsubrole-mi", false);
			}
			jitem.put("allowChild", false);
			
			PartnerUserMgr pum = (PartnerUserMgr)ApplicationContextHolder.getInstance().getBean("partnerUserMgr");
			PartnerUser pu = pum.getPartnerUserByUserId(user.getUserid());
			if(pu != null) {
				jitem.put("isPartnerUser",true);
			} else {
				jitem.put("isPartnerUser",false);
			}
			
			json.put(jitem);
		}
	}
	out.print(json);
 %>

