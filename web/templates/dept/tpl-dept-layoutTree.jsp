<%@ page pageEncoding="UTF-8" %>
<%@ include file="/common/header_tpl_json.jsp"%>
<%@page import="com.boco.eoms.commons.system.dept.model.TawSystemDept"%>
<%@page import="com.boco.eoms.commons.system.role.util.RoleIdList"%>
<%@page import="com.boco.eoms.commons.system.role.model.TawSystemSubRole"%>
<%@page import="com.boco.eoms.commons.system.area.model.TawSystemArea"%>
<%@page import="com.boco.eoms.commons.system.area.service.ITawSystemAreaManager"%>

 
 <% //节点传了参数hasRight的时候
 	List deptlist = (List)request.getAttribute("list");
	RoleIdList roleIdList = (RoleIdList)request.getAttribute("roleIdList");
	List roleList = (List)request.getAttribute("roleList");
	String userid = (String)request.getAttribute("userid");
    
	ITawSystemAreaManager areaManager = (ITawSystemAreaManager)request.getAttribute("areaManager");
    String hasRight = "0";
    String parentHasRight = (String)request.getAttribute("hasRight");
	//if(userid.equals("admin")){
	//	hasRight = "1";
	//}
	hasRight = "1";
	if(parentHasRight.equals("1"))hasRight = "1";

	JSONArray json = new JSONArray();
	if (deptlist.size() > 0){
	    TawSystemDept dept = null;

    	for (int i = 0; i < deptlist.size(); i++) {

			dept = (TawSystemDept) deptlist.get(i);				
			String deptName = dept.getDeptName();
			String isPartners = dept.getIsPartners();
			
			if(parentHasRight!=null&&parentHasRight.equals("0")){//父部门没有权限，那么判断当前部门是否有权限
			TawSystemSubRole tempRole = null;
			TawSystemArea area = null;
			String areaId = null;//用于循环地域
			for(int k=0;k<roleList.size();k++){
				tempRole = (TawSystemSubRole)roleList.get(k);
				if(tempRole.getRoleId() == roleIdList.getDeptAdminRoleId().intValue()){
				    if((tempRole.getDeptId()!=null&&!tempRole.getDeptId().equals(""))&&(tempRole.getArea()==null||tempRole.getArea().equals(""))){//子角色 只存了地市id，没存部门id
				        area = areaManager.getAreaByAreaId(tempRole.getDeptId());
				        if(tempRole.getDeptId().equals(dept.getAreaid())){//属于这个地市的部门都有权限；因为父部门没有权限，那么父部门所在地域应该包含子角色地域()
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
				        if(tempRole.getArea().equals(dept.getDeptId())){//部门是子角色所选部门，给其权限;父部门没有权限，不用考虑子部门继承父部门权限的问题。
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
				        else hasRight = "0";//当前部门不在子角色规定的地域或子地域里
				    }
				    else hasRight = "0";
				}
			} 
			}
			System.out.println(deptName+".hasRight = "+hasRight);			
			JSONObject jitem = new JSONObject();
			jitem.put(UIConstants.JSON_ID, dept.getDeptId());
			jitem.put(UIConstants.JSON_TEXT, deptName);
			jitem.put("hasRight", hasRight);
			if(hasRight.equals("1")){
			    jitem.put("allowChild", true);
				jitem.put("allowDelete", true);
			    jitem.put("allowEdit", true);
			    jitem.put("iconCls", "dept");
			}
			else {
				jitem.put("allowChild", false);
				jitem.put("allowDelete", false);
				jitem.put("allowEdit", false);
				jitem.put("iconCls", "dept-readonly");
			//	jitem.put("allowSearch",true);//没有权限的时候只能查询
			}
			jitem.put("isPartners",isPartners);
			jitem.put("leaf", dept.getLeaf().equals("0")?0:1);
			jitem.put("parentDeptid",dept.getParentDeptid());
			
			if("1".equals(isPartners)){
			//	jitem.put("iconCls", "partner-dept");
				jitem.put("nodeType", "partner-dept");
			}
			else{
			//	jitem.put("iconCls", "dept");
				jitem.put("nodeType", "dept");
			}
			jitem.put("qtipTitle", deptName);
			jitem.put("qtip","部门负责人:"+dept.getDeptmanager()+"<br/>部门电话:"+dept.getDeptphone()+"<br/>备注:"+dept.getRemark()+"<br/>");
			json.put(jitem);
		}
	}
	out.print(json);
  %>
