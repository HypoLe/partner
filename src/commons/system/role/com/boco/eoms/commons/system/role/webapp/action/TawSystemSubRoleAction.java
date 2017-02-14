package com.boco.eoms.commons.system.role.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.boco.eoms.base.Constants;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.StaticVariable;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.role.model.TawSystemRole;
import com.boco.eoms.commons.system.role.model.TawSystemSubRole;
import com.boco.eoms.commons.system.role.service.ITawSystemRoleManager;
import com.boco.eoms.commons.system.role.service.ITawSystemSubRoleManager;
import com.boco.eoms.commons.system.role.util.RoleConstants;
import com.boco.eoms.commons.system.role.util.RoleMapSchema;
import com.boco.eoms.commons.system.role.util.RoleStaticMethod;
import com.boco.eoms.commons.system.role.util.RoleXmlSchema;
import com.boco.eoms.commons.system.role.webapp.form.TawSystemSubRoleForm;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.commons.system.user.service.ITawSystemUserRefRoleManager;
import com.boco.eoms.commons.ui.util.JSONUtil;
import com.boco.eoms.commons.ui.util.UIConstants;

/**
 * Action class to handle CRUD on a TawSystemSubRole object
 * 
 * @struts.action name="tawSystemSubRoleForm" path="/tawSystemSubRoles"
 *                scope="request" validate="false" parameter="method"
 *                input="mainMenu"
 * @struts.action name="tawSystemSubRoleForm" path="/editTawSystemSubRole"
 *                scope="request" validate="false" parameter="method"
 *                input="list"
 * @struts.action name="tawSystemSubRoleForm" path="/saveTawSystemSubRole"
 *                scope="request" validate="true" parameter="method"
 *                input="edit"
 * @struts.action-set-property property="cancellable" value="true"
 * @struts.action-forward name="edit"
 *                        path="/WEB-INF/pages/tawSystemSubRole/tawSystemSubRoleForm.jsp"
 * @struts.action-forward name="list"
 *                        path="/WEB-INF/pages/tawSystemSubRole/tawSystemSubRoleList.jsp"
 * @struts.action-forward name="search" path="/tawSystemSubRoles.html"
 *                        redirect="true"
 */
public final class TawSystemSubRoleAction extends BaseAction {
	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("search");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages messages = new ActionMessages();
		TawSystemSubRoleForm tawSystemSubRoleForm = (TawSystemSubRoleForm) form;

		// Exceptions are caught by ActionExceptionHandler
		ITawSystemSubRoleManager mgr = (ITawSystemSubRoleManager) getBean("ItawSystemSubRoleManager");
		mgr.removeTawSystemSubRole(tawSystemSubRoleForm.getId());

		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"tawSystemSubRole.deleted"));

		// save messages in session, so they'll survive the redirect
		saveMessages(request.getSession(), messages);

		return mapping.findForward("search");
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSubRoleForm tawSystemSubRoleForm = (TawSystemSubRoleForm) form;

		// if an id is passed in, look up the user - otherwise
		// don't do anything - user is doing an add
		if (tawSystemSubRoleForm.getId() != null) {
			ITawSystemSubRoleManager mgr = (ITawSystemSubRoleManager) getBean("ItawSystemSubRoleManager");
			TawSystemSubRole tawSystemSubRole = mgr
					.getTawSystemSubRole(tawSystemSubRoleForm.getId());
			tawSystemSubRoleForm = (TawSystemSubRoleForm) convert(tawSystemSubRole);
			updateFormBean(mapping, request, tawSystemSubRoleForm);
		}

		request.setAttribute("roleId", new Long(tawSystemSubRoleForm
				.getRoleId()));
		return mapping.findForward("edit");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// Extract attributes and parameters we will need
		ActionMessages messages = new ActionMessages();
		TawSystemSubRoleForm tawSystemSubRoleForm = (TawSystemSubRoleForm) form;
		boolean isNew = (tawSystemSubRoleForm.getId() == null);

		ITawSystemSubRoleManager mgr = (ITawSystemSubRoleManager) getBean("ItawSystemSubRoleManager");
		TawSystemSubRole tawSystemSubRole = (TawSystemSubRole) convert(tawSystemSubRoleForm);
		Map userMap = new HashMap();
		String[] users = tawSystemSubRoleForm.getUserId().split(",");
		for (int i = 0; i < users.length; i++) {
			userMap.put(users[i], RoleConstants.groupType_common);
		}
		mgr.saveTawSystemSubRole(tawSystemSubRole, userMap);

		ActionForward forward = null;
		// add success messages
		if (isNew) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"tawSystemSubRole.added"));

			// save messages in session to survive a redirect
			saveMessages(request.getSession(), messages);

			forward = mapping.findForward("search");
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"tawSystemSubRole.updated"));
			saveMessages(request, messages);

			forward = mapping.findForward("search");
		}

		String path = forward.getPath() + "?method=search&roleId="
				+ tawSystemSubRole.getRoleId();// 加参数
		return new ActionForward(path, false);
	}

	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String roleId = request.getParameter("roleId");
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				"tawDemoMytableList")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE); // ҳ��Ĳ�����,����"tawDemoMytableList"��ҳ����displayTag��id
		final Integer pageSize = new Integer(25); // ÿҳ��ʾ������
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1)); // ��ǰҳ��

		ITawSystemSubRoleManager mgr = (ITawSystemSubRoleManager) getBean("ItawSystemSubRoleManager");
		Map map = (Map) mgr.getTawSystemSubRoles(pageIndex, pageSize, "roleId="
				+ roleId); // map����}��keyֵ��һ����"total",�����ܼ�¼������һ����"result"������Ҫ��ʾҳ���list
		List list = (List) map.get("result");
		request.setAttribute(Constants.TAWSYSTEMSUBROLE_LIST, list);
		request.setAttribute("resultSize", map.get("total"));

		return mapping.findForward("list");
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return search(mapping, form, request, response);
	}

	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String roleId = request.getParameter("roleId");
		request.setAttribute("roleId", roleId);
		return mapping.findForward("create");
	}

	/**
	 * 根绝部门批量生成子角色
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward createSubRoles(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSubRoleForm tawSystemSubRoleForm = (TawSystemSubRoleForm) form;
		// String roleId = request.getParameter("roleId");
		long roleId = tawSystemSubRoleForm.getRoleId();

		ITawSystemSubRoleManager smgr = (ITawSystemSubRoleManager) getBean("ItawSystemSubRoleManager");

		smgr.createSubRolesByDept(roleId, String.valueOf(tawSystemSubRoleForm
				.getDeptId()));

		ActionForward forward = mapping.findForward("search");
		String path = forward.getPath() + "?method=search&roleId=" + roleId;// 加参数
		return new ActionForward(path, false);
	}

	/**
	 * ajax方式保存单个子角色
	 */
	public void xsave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		TawSystemSubRoleForm tawSystemSubRoleForm = (TawSystemSubRoleForm) form;
		ITawSystemSubRoleManager mgr = (ITawSystemSubRoleManager) getBean("ItawSystemSubRoleManager");
		TawSystemSubRole tawSystemSubRole = (TawSystemSubRole) convert(tawSystemSubRoleForm);
		Map userMap = new HashMap();
		String[] users = tawSystemSubRoleForm.getUserId().split(",");
		for (int i = 0; i < users.length; i++) {
			userMap.put(users[i], RoleConstants.groupType_common);
		}
		mgr.saveTawSystemSubRole(tawSystemSubRole, userMap);
		JSONUtil.success(response, "success");
	}

	/**
	 * 保存以JSON格式传入的多个子角色
	 * JSON格式为：[{"dept":"1","subRoleName":"admin","class1":"jifei","class2":"jifei2",...},...]
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void jsonSubRoles(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSubRoleForm tawSystemSubRoleForm = (TawSystemSubRoleForm) form;
		String subRolesJSON = StaticMethod.null2String(request
				.getParameter("subRoles"));
		try {
			JSONArray subRoleJSONList = new JSONArray();
			subRoleJSONList = JSONArray.fromString(subRolesJSON);

			List subRoleList = new ArrayList();

			for (int i = 0; i < subRoleJSONList.length(); i++) {
				JSONObject jsonObj = (JSONObject) subRoleJSONList.get(i);

				TawSystemSubRole subRole = (TawSystemSubRole) convert(tawSystemSubRoleForm);

				if (jsonObj.has(RoleConstants.SUBROLE_DEPT))
					subRole.setDeptId(jsonObj
							.getString(RoleConstants.SUBROLE_DEPT));
				
				if(jsonObj.has(RoleConstants.SUBROLE_AREA))
					subRole.setArea(jsonObj
							.getString(RoleConstants.SUBROLE_AREA));
				
				String buzinessName = RoleXmlSchema.getInstance()
						.getBusinessName(RoleConstants.SUBROLE_TYPE1);
				if (jsonObj.has("class1"))
					subRole.setType1(jsonObj.getString(buzinessName));

				buzinessName = RoleXmlSchema.getInstance().getBusinessName(
						RoleConstants.SUBROLE_TYPE2);
				if (jsonObj.has("class2"))
					subRole.setType2(jsonObj.getString(buzinessName));

				buzinessName = RoleXmlSchema.getInstance().getBusinessName(
						RoleConstants.SUBROLE_TYPE3);
				if (jsonObj.has("class3"))
					subRole.setType3(jsonObj.getString(buzinessName));

				buzinessName = RoleXmlSchema.getInstance().getBusinessName(
						RoleConstants.SUBROLE_TYPE4);
				if (jsonObj.has("class4"))
					subRole.setType4(jsonObj.getString(buzinessName));
				if (jsonObj.has("class5"))
					subRole.setType5(jsonObj.getString("class5"));
			
				if (jsonObj.has("class6"))
					subRole.setType6(jsonObj.getString("class6"));
								
				if (jsonObj.has("class7"))
					subRole.setType7(jsonObj.getString("class7"));
			
				if (jsonObj.has("class8"))
					subRole.setType8(jsonObj.getString("class8"));

				if (jsonObj.has("subRoleName"))
					subRole.setSubRoleName(jsonObj.getString("subRoleName"));

				subRoleList.add(subRole);
			}

			if (subRoleList.size() > 0) {
				TawSystemSubRole subRole = (TawSystemSubRole) subRoleList
						.get(0);
				long roleId = subRole.getRoleId();
				ITawSystemRoleManager mgr = (ITawSystemRoleManager) getBean("ItawSystemRoleManager");
				TawSystemRole role = mgr.getTawSystemRole(roleId);

				ITawSystemSubRoleManager smgr = (ITawSystemSubRoleManager) getBean("ItawSystemSubRoleManager");
				boolean b = true;

				if (String.valueOf(role.getRoleTypeId()).equals(
						RoleConstants.ROLETYPE_VIRTUAL))
					b = false;
				smgr.saveTawSystemSubRoles(subRoleList, b);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		JSONUtil.success(response, "success");
	}

	 
    /**
     * 从子角色ID取得其用户列表，返回JSON格式
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @throws Exception
     */
    public void xGetUsers(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
    throws Exception {

    	String subRoleId = StaticMethod.null2String(request.getParameter("id"));

    	ITawSystemUserRefRoleManager mgr = (ITawSystemUserRefRoleManager) getBean("itawSystemUserRefRoleManager");
    	ITawSystemUserManager ugr = (ITawSystemUserManager) getBean("itawSystemUserManager");
    	
    	Map map = mgr.getGroupUserbyroleid(subRoleId);
		
    	JSONArray jsonRoot = new JSONArray();
    	
    	Iterator it = map.keySet().iterator();

    	while(it.hasNext()){
    		String userId=it.next().toString();
    		TawSystemUser user =ugr.getUserByuserid(userId); 
    		JSONObject jitem = new JSONObject();
    		String userName = user.getUsername();
    		String groupType = (String)map.get(userId);
    		String text = RoleConstants.groupType_leader.equals(groupType)
    			? userName+"("+RoleConstants.TEXT_LEADER+")" : userName;
    		
    		jitem.put(UIConstants.JSON_ID,user.getUserid());
    		jitem.put(UIConstants.JSON_TEXT,text);
    		jitem.put(UIConstants.JSON_NAME,userName);
    		jitem.put("grouptype",groupType);
    		jitem.put(UIConstants.JSON_ICONCLS, 
    				RoleConstants.groupType_leader.equals(groupType)
    				? "leader"
    				: UIConstants.NODETYPE_USER);
    		
    		jsonRoot.put(jitem);
    	}
    	JSONUtil.print(response, jsonRoot.toString());
    }

	/**
	 * 根据传入的以逗号隔开的多个userId，替换原子角色的用户列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void xUpdateUsers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String subRoleId = StaticMethod.null2String(request.getParameter("id"));
		String userIds = StaticMethod.null2String(request
				.getParameter("userId"));
		String roleType = StaticMethod.null2String(request
				.getParameter("roleType"));

		if ("" == roleType)
			roleType = RoleConstants.ROLETYPE_SUBROLE;

		ITawSystemUserRefRoleManager mgr = (ITawSystemUserRefRoleManager) getBean("itawSystemUserRefRoleManager");

		mgr.updateUsers2SubRole(subRoleId, userIds, roleType);
		JSONUtil.success(response, "success");
	}

	/**
	 * 根据传入的以逗号隔开的多个子角色ID,删除这些子角色
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void xDelete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String subRoleIds = StaticMethod.null2String(request
				.getParameter("subRoleIds"));
		ITawSystemSubRoleManager mgr = (ITawSystemSubRoleManager) getBean("ItawSystemSubRoleManager");
		// mgr.removeTawSystemSubRole(subRoleId);
		mgr.deleteSubRoles(subRoleIds);
		JSONUtil.success(response, "success");
	}

	/**
	 * 根据模块ID，获取子角色区分度信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public ActionForward getRoleFiler(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String modelId = StaticMethod.null2String(request.getParameter("id"));
		List filters = RoleMapSchema.getInstance().getRoleMappingListById(
				modelId);
		request.setAttribute("filter", filters);
		return mapping.findForward("roleFilter");
	}

	public void reflushWpsUserList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		RoleStaticMethod.reFlushWpsUser();
	}

	/**
	 * 设置虚拟组的组长
	 */
	public void xSetLeader(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String groupId = StaticMethod.null2String(request.getParameter("id"));
		String userId = StaticMethod
				.null2String(request.getParameter("userId"));

		ITawSystemSubRoleManager mgr = (ITawSystemSubRoleManager) getBean("ItawSystemSubRoleManager");
		mgr.saveLeader(groupId, userId);
		JSONUtil.success(response, "虚拟组组长设置成功");
	}

	/**
	 * 修改子角色名称
	 */
	public void xSetName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = StaticMethod.null2String(request.getParameter("id"));
		String newName = StaticMethod.null2String(java.net.URLDecoder.decode(request.getParameter("newName"),"UTF-8") );
		ITawSystemSubRoleManager mgr = (ITawSystemSubRoleManager) getBean("ItawSystemSubRoleManager");
		TawSystemSubRole subrole = mgr.getTawSystemSubRole(id);
		subrole.setSubRoleName(newName);
		mgr.saveTawSystemSubRole(subrole);
		JSONUtil.success(response, "修改子角色名称成功");
	}
	
	/**
	 * 根据当前用户ID和当前子角色取得所其下所有用户列表(不包括当前用户),返回JSON格式
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void xgetUsersBySubRole(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String subRoleId = StaticMethod.null2String(request.getParameter("subRoleId"));
		//取当前用户
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String currentUserId = sessionform.getUserid();
		
		ITawSystemUserRefRoleManager userRefRoleMgr = (ITawSystemUserRefRoleManager) getBean("itawSystemUserRefRoleManager");
		JSONArray jsonRoot = new JSONArray();
		//取所属子角色人员列表
		List userList = userRefRoleMgr.getUserbyroleid(subRoleId);
		for (Iterator it = userList.iterator(); it.hasNext();) {
			JSONObject jitem = new JSONObject();
			TawSystemUser user = (TawSystemUser) it.next();
			if (!currentUserId.equals(user.getUserid())) {
				jitem.put("id", user.getUserid());
				jitem.put("text", user.getUsername());
				jitem.put("leaf", true);
				jitem.put("iconCls", "user");
				jitem.put(UIConstants.JSON_NODETYPE, UIConstants.NODETYPE_USER);
				jsonRoot.put(jitem);
			}
		}
		JSONUtil.print(response, jsonRoot.toString());
	}
	
	/**
	 * 根据当前用户ID和当前大角色取得所其下所有子角色和用户列表(不包括当前用户),返回JSON格式
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void xgetUsersByRole(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = StaticMethod.null2String(request.getParameter("node"));
		String roleId = StaticMethod.null2String(request.getParameter("roleId"));
		ITawSystemUserRefRoleManager userRefRoleMgr = (ITawSystemUserRefRoleManager) getBean("itawSystemUserRefRoleManager");
		//取当前用户
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String currentUserId = sessionform.getUserid();
		JSONArray jsonRoot = new JSONArray();
		if ("-1".equals(id) || "".equals(id)) {
			ITawSystemSubRoleManager subRoleMgr = (ITawSystemSubRoleManager) getBean("ItawSystemSubRoleManager");
			List subRoleList = subRoleMgr.getTawSystemSubRoles(Long.parseLong(roleId));
			for (Iterator iter = subRoleList.iterator(); iter.hasNext();) {
				JSONObject jitem = new JSONObject();
				TawSystemSubRole subRole = (TawSystemSubRole) iter.next();
				List userList = userRefRoleMgr.getUserbyroleid(subRole.getId());
				jitem.put("id", subRole.getId());
				jitem.put("text", subRole.getSubRoleName());
				if (null == userList || 0 >= userList.size()) {
					jitem.put("leaf", true);
				}
				else {
					jitem.put("leaf", false);
				}
				jitem.put("iconCls", "subrole");
				jitem.put(UIConstants.JSON_NODETYPE, UIConstants.NODETYPE_SUBROLE);
				jsonRoot.put(jitem);
			}
		}
		else {
			//取所属子角色人员列表
			List userList = userRefRoleMgr.getUserbyroleid(id);
			for (Iterator it = userList.iterator(); it.hasNext();) {
				JSONObject jitem = new JSONObject();
				TawSystemUser user = (TawSystemUser) it.next();
				if (!currentUserId.equals(user.getUserid())) {
					jitem.put("id", user.getUserid());
					jitem.put("text", user.getUsername());
					jitem.put("leaf", true);
					jitem.put("iconCls", "user");
					jitem.put(UIConstants.JSON_NODETYPE, UIConstants.NODETYPE_USER);
					jsonRoot.put(jitem);
				}
			}
		}
		JSONUtil.print(response, jsonRoot.toString());
	}
	
	/**
	 * 取得当前系统中所有大角色和子角色(子角色可勾选),返回JSON格式
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void xgetAllRolesAndSubRoles(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = StaticMethod.null2String(request.getParameter("node"));
		String nodeType = StaticMethod.null2String(request.getParameter(UIConstants.JSON_NODETYPE));
		ITawSystemRoleManager roleMgr = (ITawSystemRoleManager) getBean("ItawSystemRoleManager");
		ITawSystemSubRoleManager subRoleMgr = (ITawSystemSubRoleManager) getBean("ItawSystemSubRoleManager");
		JSONArray jsonRoot = new JSONArray();
		if ("-1".equals(id) && UIConstants.NODETYPE_ROOT.equals(nodeType)) {
			JSONObject systemRole = new JSONObject();
			systemRole.put(UIConstants.JSON_ID, RoleConstants.systemRole);
			systemRole.put(UIConstants.JSON_TEXT, "系统角色");
			systemRole.put(UIConstants.JSON_NODETYPE, "systemRole");
			systemRole.put(UIConstants.JSON_ICONCLS, "folder");
			systemRole.put("leaf", false);
			jsonRoot.put(systemRole);
			JSONObject flowRole = new JSONObject();
			flowRole.put(UIConstants.JSON_ID, RoleConstants.flowRole);
			flowRole.put(UIConstants.JSON_TEXT, "流程角色");
			flowRole.put(UIConstants.JSON_NODETYPE, "flowRole");
			flowRole.put(UIConstants.JSON_ICONCLS, "folder");
			flowRole.put("leaf", false);
			jsonRoot.put(flowRole);
		}
		else if (RoleConstants.flowRole.equals(id)) {
			List roleList = (ArrayList) roleMgr.getFlwRolesByWorkflowFlag(0);
			for (Iterator roleIt = roleList.iterator(); roleIt.hasNext();) {
				JSONObject jitem = new JSONObject();
				TawSystemRole role = (TawSystemRole) roleIt.next();
				jitem.put(UIConstants.JSON_ID, role.getRoleId());
				jitem.put(UIConstants.JSON_TEXT, role.getRoleName());
				jitem.put(UIConstants.JSON_NODETYPE, UIConstants.NODETYPE_ROLE);
				jitem.put(UIConstants.JSON_ICONCLS, "folder");
				if (role.getLeaf().equals(new Integer(StaticVariable.LEAF))) {
					List subRoleList = subRoleMgr.getTawSystemSubRoles(role.getRoleId());
					if (null == subRoleList || 0 >= subRoleList.size()) {
						jitem.put("leaf", true);
					}
					else {
						jitem.put("leaf", false);
					}
				}
				else {
					jitem.put("leaf", false);
				}
				jsonRoot.put(jitem);
			}
		}
		else if(RoleConstants.systemRole.equals(id)){
			List roleList = (ArrayList) roleMgr.getChildrenByRoleId(1);
			for (Iterator roleIt = roleList.iterator(); roleIt.hasNext();) {
				JSONObject jitem = new JSONObject();
				TawSystemRole role = (TawSystemRole) roleIt.next();
				jitem.put(UIConstants.JSON_ID, role.getRoleId());
				jitem.put(UIConstants.JSON_TEXT, role.getRoleName());
				jitem.put(UIConstants.JSON_NODETYPE, UIConstants.NODETYPE_ROLE);
				jitem.put(UIConstants.JSON_ICONCLS, "folder");
				if (role.getLeaf().equals(new Integer(StaticVariable.LEAF))) {
					List subRoleList = subRoleMgr.getTawSystemSubRoles(role.getRoleId());
					if (null == subRoleList || 0 >= subRoleList.size()) {
						jitem.put("leaf", true);
					}
					else {
						jitem.put("leaf", false);
					}
				}
				else {
					jitem.put("leaf", false);
				}
				jsonRoot.put(jitem);
			}
		}
		else if (UIConstants.NODETYPE_ROLE.equals(nodeType)) {
			List roleList = (ArrayList) roleMgr.getChildrenByRoleId(Long.parseLong(id));
			if (null == roleList || 0 >= roleList.size()) {
				List subRoleList = subRoleMgr.getTawSystemSubRoles(Long.parseLong(id));
				for (Iterator iter = subRoleList.iterator(); iter.hasNext();) {
					JSONObject jitem = new JSONObject();
					TawSystemSubRole subRole = (TawSystemSubRole) iter.next();
					jitem.put(UIConstants.JSON_ID, subRole.getId());
					jitem.put(UIConstants.JSON_TEXT, subRole.getSubRoleName());
					jitem.put(UIConstants.JSON_NODETYPE, UIConstants.NODETYPE_SUBROLE);
					jitem.put(UIConstants.JSON_ICONCLS, UIConstants.NODETYPE_SUBROLE);
					jitem.put("leaf", true);
					jitem.put("checked", false);
					jsonRoot.put(jitem);
				}
			}
			else {
				for (Iterator roleIt = roleList.iterator(); roleIt.hasNext();) {
					JSONObject jitem = new JSONObject();
					TawSystemRole role = (TawSystemRole) roleIt.next();
					jitem.put(UIConstants.JSON_ID, role.getRoleId());
					jitem.put(UIConstants.JSON_TEXT, role.getRoleName());
					jitem.put(UIConstants.JSON_NODETYPE, UIConstants.NODETYPE_ROLE);
					jitem.put(UIConstants.JSON_ICONCLS, "folder");
					if (role.getLeaf().equals(new Integer(StaticVariable.LEAF))) {
						List subRoleList = subRoleMgr.getTawSystemSubRoles(role.getRoleId());
						if (null == subRoleList || 0 >= subRoleList.size()) {
							jitem.put("leaf", true);
						}
						else {
							jitem.put("leaf", false);
						}
					}
					else {
						jitem.put("leaf", false);
					}
					jsonRoot.put(jitem);
				}
			}
		}
		JSONUtil.print(response, jsonRoot.toString());
	}
}
