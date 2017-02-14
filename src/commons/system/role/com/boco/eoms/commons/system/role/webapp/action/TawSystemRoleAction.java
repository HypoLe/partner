package com.boco.eoms.commons.system.role.webapp.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
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
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.dict.model.DictItemXML;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.commons.system.dict.service.impl.ID2NameServiceFactory;
import com.boco.eoms.commons.system.dict.util.DictMgrLocator;
import com.boco.eoms.commons.system.role.model.TawSystemRole;
import com.boco.eoms.commons.system.role.model.TawSystemSubRole;
import com.boco.eoms.commons.system.role.service.IRoleMgr;
import com.boco.eoms.commons.system.role.service.ITawSystemRoleManager;
import com.boco.eoms.commons.system.role.service.ITawSystemSubRoleManager;
import com.boco.eoms.commons.system.role.util.RoleConstants;
import com.boco.eoms.commons.system.role.util.RoleFilter;
import com.boco.eoms.commons.system.role.util.RoleMapSchema;
import com.boco.eoms.commons.system.role.webapp.form.TawSystemRoleForm;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.commons.system.user.service.ITawSystemUserRefRoleManager;
import com.boco.eoms.commons.ui.taglib.ChooserTag;
import com.boco.eoms.commons.ui.util.JSONUtil;
import com.boco.eoms.commons.ui.util.UIConstants;

/**
 * Action class to handle CRUD on a TawSystemRole object
 * 
 * @struts.action name="tawSystemRoleForm" path="/tawSystemRoles"
 *                scope="request" validate="false" parameter="method"
 *                input="mainMenu"
 * @struts.action name="tawSystemRoleForm" path="/editTawSystemRole"
 *                scope="request" validate="false" parameter="method"
 *                input="list"
 * @struts.action name="tawSystemRoleForm" path="/saveTawSystemRole"
 *                scope="request" validate="true" parameter="method"
 *                input="edit"
 * @struts.action-set-property property="cancellable" value="true"
 * @struts.action-forward name="edit"
 *                        path="/WEB-INF/pages/tawSystemRole/tawSystemRoleForm.jsp"
 * @struts.action-forward name="list"
 *                        path="/WEB-INF/pages/tawSystemRole/tawSystemRoleList.jsp"
 * @struts.action-forward name="search" path="/tawSystemRoles.html"
 *                        redirect="true"
 */
public final class TawSystemRoleAction extends BaseAction {
	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("search");
	}

	public ActionForward addNew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		TawSystemRoleForm tawSystemRoleForm = (TawSystemRoleForm) form;
		request.setAttribute("RoleId", new Long(tawSystemRoleForm.getRoleId()));

		try {

			// TawSystemRole tawSystemRole = (TawSystemRole)
			// convert(tawSystemRoleForm);

			// String levelList = orgLevelDAO.getLevelListString();
			// String roletypeList = orgRoletypeDAO.getRoleypeListString();
			//
			// request.setAttribute("LEVELLIST", levelList);
			// request.setAttribute("ROLETYPELIST", roletypeList);

			long parentId = tawSystemRoleForm.getRoleId();
			// tawSystemRoleForm.setParentId(parentId);
			// OrgRoleBO orgRoleBO = new OrgRoleBO();

			tawSystemRoleForm.setParentId(parentId);

		} catch (Exception e) {
			return mapping.findForward("failure");
		}
		return mapping.findForward("add");

	}

	public ActionForward subRoleList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		TawSystemRoleForm tawSystemRoleForm = (TawSystemRoleForm) form;
		long roleId = tawSystemRoleForm.getRoleId();

		String pageIndexName = new org.displaytag.util.ParamEncoder(
				"tawSystemSubRoleList")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = new Integer(25);
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));

		ITawSystemSubRoleManager mgr = (ITawSystemSubRoleManager) getBean("ItawSystemSubRoleManager");
		Map map = (Map) mgr.getTawSystemSubRoles(pageIndex, pageSize, "roleId="
				+ roleId);
		List list = (List) map.get("result");
		request.setAttribute(Constants.TAWSYSTEMSUBROLE_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("roleId", new Long(roleId));

		return mapping.findForward("subRolelist");

	}

	public ActionForward addSubRole(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		TawSystemRoleForm tawSystemRoleForm = (TawSystemRoleForm) form;
		request.setAttribute("roleId", new Long(tawSystemRoleForm.getRoleId()));

		return mapping.findForward("addSubRole");

	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ActionMessages messages = new ActionMessages();
		TawSystemRoleForm tawSystemRoleForm = (TawSystemRoleForm) form;

		// Exceptions are caught by ActionExceptionHandler
		ITawSystemRoleManager mgr = (ITawSystemRoleManager) getBean("ItawSystemRoleManager");
		mgr.removeTawSystemRole(tawSystemRoleForm.getRoleId());

		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"tawSystemRole.deleted"));

		// save messages in session, so they'll survive the redirect
		saveMessages(request.getSession(), messages);

		request.setAttribute("lastEditId", new Long(tawSystemRoleForm
				.getRoleId()));

		return mapping.findForward("search");
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemRoleForm tawSystemRoleForm = (TawSystemRoleForm) form;

		// if an id is passed in, look up the user - otherwise
		// don't do anything - user is doing an add
		long id = StaticMethod.null2Long(request.getParameter("id"));
		if (id > 0) {
			ITawSystemRoleManager mgr = (ITawSystemRoleManager) getBean("ItawSystemRoleManager");
			TawSystemRole tawSystemRole = mgr.getTawSystemRole(id);
			tawSystemRoleForm = (TawSystemRoleForm) convert(tawSystemRole);
			updateFormBean(mapping, request, tawSystemRoleForm);

			// List tawRmUsers =
			// refrolebo.getUsersbyroleids(String.valueOf(tawSystemRole.getRoleId()));
			//			
			// StringBuffer userIdArray = new StringBuffer();
			// StringBuffer userNameArray = new StringBuffer();
			// if (tawRmUsers.iterator().hasNext()) {
			// for (Iterator it = tawRmUsers.iterator(); it.hasNext();) {
			// TawSystemUser user = (TawSystemUser) it.next();
			// userIdArray.append(user.getUserid()).append(",");
			// userNameArray.append(user.getUsername()).append(",");
			// }
			// userIdArray.deleteCharAt(userIdArray.lastIndexOf(","));
			// userNameArray.deleteCharAt(userNameArray.lastIndexOf(","));
			// }
			//
			//
			// request.setAttribute("userIdArray", userIdArray.toString());
			// request.setAttribute("userNameArray", userNameArray.toString());
			request.setAttribute("workflowFlag", String.valueOf(tawSystemRole
					.getWorkflowFlag()));
			request.setAttribute("roleId", String.valueOf(tawSystemRole
					.getParentId()));
		}

		return mapping.findForward("edit");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// Extract attributes and parameters we will need
		ActionMessages messages = new ActionMessages();
		TawSystemRoleForm tawSystemRoleForm = (TawSystemRoleForm) form;
		boolean isNew = (tawSystemRoleForm.getRoleId() == 0);

		ITawSystemRoleManager mgr = (ITawSystemRoleManager) getBean("ItawSystemRoleManager");
		TawSystemRole tawSystemRole = (TawSystemRole) convert(tawSystemRoleForm);
		tawSystemRole.setDeleted(Integer.valueOf("0"));
		if (tawSystemRole.getLeaf() == null)
			tawSystemRole.setLeaf(Integer.valueOf("1"));
		// tawSystemRole.setLeaf(Integer.valueOf("1"));
		mgr.saveTawSystemRole(tawSystemRole);
		mgr.setLeaf(tawSystemRole.getParentId(), Integer.valueOf("0"));

		// add success messages
		if (isNew) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"tawSystemRole.added"));

			// save messages in session to survive a redirect
			saveMessages(request.getSession(), messages);

			request.setAttribute("lastNewId", new Long(tawSystemRoleForm
					.getRoleId()));
			return mapping.findForward("search");
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"tawSystemRole.updated"));
			saveMessages(request, messages);

			request.setAttribute("lastEditId", new Long(tawSystemRoleForm
					.getRoleId()));
			return mapping.findForward("search");
		}
	}

	public ActionForward saveNew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// Extract attributes and parameters we will need
		ActionMessages messages = new ActionMessages();
		TawSystemRoleForm tawSystemRoleForm = (TawSystemRoleForm) form;
		boolean isNew = (tawSystemRoleForm.getRoleId() == 0);

		ITawSystemRoleManager mgr = (ITawSystemRoleManager) getBean("ItawSystemRoleManager");
		TawSystemRole tawSystemRole = (TawSystemRole) convert(tawSystemRoleForm);
		tawSystemRole.setDeleted(Integer.valueOf("0"));
		tawSystemRole.setSingleId(StaticMethod.getCurrentDateTime());
		tawSystemRole.setLeaf(Integer.valueOf("1"));
		if (tawSystemRole.getParentId() > 0)
			tawSystemRole.setStructureFlag(mgr
					.getNewStructureFlag(tawSystemRole.getParentId()));
		mgr.saveTawSystemRole(tawSystemRole);

		mgr.setLeaf(tawSystemRole.getParentId(), Integer.valueOf("0"));

		// add success messages
		if (isNew) {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"tawSystemRole.added"));

			// save messages in session to survive a redirect
			saveMessages(request.getSession(), messages);

			request.setAttribute("lastNewId", new Long(tawSystemRoleForm
					.getRoleId()));
			return mapping.findForward("search");
		} else {
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"tawSystemRole.updated"));
			saveMessages(request, messages);

			request.setAttribute("lastEditId", new Long(tawSystemRoleForm
					.getRoleId()));
			return mapping.findForward("search");
		}
	}

	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// String pageIndexName = new
		// org.displaytag.util.ParamEncoder("tawDemoMytableList").encodeParameterName(
		// org.displaytag.tags.TableTagParameters.PARAMETER_PAGE); //
		// ҳ��Ĳ�����,����"tawDemoMytableList"��ҳ����displayTag��id
		// final Integer pageSize = 25; //ÿҳ��ʾ������
		// final Integer pageIndex =
		// GenericValidator.isBlankOrNull(request.getParameter(pageIndexName))?0:(Integer.parseInt(request.getParameter(pageIndexName))
		// - 1); //��ǰҳ��
		//
		// ITawSystemRoleManager mgr = (ITawSystemRoleManager)
		// getBean("ItawSystemRoleManager");
		// Map map = (Map)mgr.getTawSystemRoles(pageIndex,pageSize);
		// //map����}��keyֵ��һ����"total",�����ܼ�¼������һ����"result"������Ҫ��ʾҳ���list
		// List list = (List)map.get("result");
		// request.setAttribute(Constants.TAWSYSTEMRole_LIST, list);
		// request.setAttribute("resultSize", map.get("total"));
		//
		// return mapping.findForward("list");

		TawSystemRoleForm actionform = (TawSystemRoleForm) form;
		try {
			int postId = StaticMethod.null2int(request.getParameter("dept"));
			ITawSystemRoleManager mgr = (ITawSystemRoleManager) getBean("ItawSystemRoleManager");
			TawSystemRole orgRole = mgr.getTawSystemRole(postId,
					StaticVariable.UNDELETED);
			if (orgRole == null) {
				actionform.setStrutsAction(4);
			}
		} catch (Exception e) {
			return mapping.findForward("failure");
		}
		return mapping.findForward("list");
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return search(mapping, form, request, response);
	}

	/**
	 * 根据父系统角色的roleId得到下级系统角色的JSON数据， 或根据workFlowFlag得到相应流程的角色JSON数据
	 */
	public void getNodes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		long nodeId = StaticMethod.null2Long(request.getParameter("node"));
		String roleTypeId = request.getParameter("roleTypeId");

		ITawSystemRoleManager roleMgr = (ITawSystemRoleManager) getBean("ItawSystemRoleNoCacheManager");

		ArrayList list = new ArrayList();
		if (RoleConstants.flowRole.equals(roleTypeId)) {
			// roleTypeId=1,则按流程取角色，将nodeId视为workFlowFlag
			int workflowFlag = (int) nodeId;
			list = (ArrayList) roleMgr.getFlwRolesByWorkflowFlag(workflowFlag);
		} else {
			// 否则取系统角色
			list = (ArrayList) roleMgr.getSysRolesByRoleId(nodeId);
		}

		JSONArray jsonRoot = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			TawSystemRole role = (TawSystemRole) list.get(i);
			String roleName = String.valueOf(role.getRoleName());
			String note = StaticMethod.null2String(role.getNotes());

			// 获取角色的树图JSON对象
			JSONObject jitem = roleMgr.getJSON4TreeNode(role);
			jitem.put("qtip", "角色ID: " + role.getRoleId()
					+ ("".equals(note) ? "" : "<br/>备注:" + note));
			jitem.put("qtipTitle", roleName);
			jsonRoot.put(jitem);
		}
		JSONUtil.print(response, jsonRoot.toString());
	}

	public void xsave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemRoleForm tawSystemRoleForm = (TawSystemRoleForm) form;
		ITawSystemRoleManager mgr = (ITawSystemRoleManager) getBean("ItawSystemRoleManager");
		TawSystemRole tawSystemRole = (TawSystemRole) convert(tawSystemRoleForm);

		tawSystemRole.setSingleId(StaticMethod.getCurrentDateTime());
		long parentId = tawSystemRole.getParentId();
		String roleTypeId = String.valueOf(tawSystemRole.getRoleTypeId());

		if ((RoleConstants.systemRole.equals(roleTypeId) || RoleConstants.ROLETYPE_VIRTUAL
				.equals(roleTypeId))
				&& parentId > 0) {
			// 设置树形结构标志位
			tawSystemRole.setStructureFlag(mgr.getNewStructureFlag(parentId));
		}

		mgr.saveTawSystemRole(tawSystemRole);

		if ((RoleConstants.systemRole.equals(roleTypeId) || RoleConstants.ROLETYPE_VIRTUAL
				.equals(roleTypeId))
				&& parentId > 0) {
			// 设置父角色leaf属性为1
			mgr.setLeaf(parentId, new Integer(StaticVariable.NOTLEAF));
		}

		JSONUtil.success(response, "success");
	}

	public void xdel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		long id = StaticMethod.null2Long(request.getParameter("id"));
		ITawSystemRoleManager mgr = (ITawSystemRoleManager) getBean("ItawSystemRoleManager");
		mgr.removeTawSystemRole(id);
		JSONUtil.success(response, "success");
	}

	public void xget(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		long id = StaticMethod.null2Long(request.getParameter("id"));
		ITawSystemRoleManager mgr = (ITawSystemRoleManager) getBean("ItawSystemRoleManager");
		ITawSystemUserRefRoleManager refMgr = (ITawSystemUserRefRoleManager) getBean("itawSystemUserRefRoleManager");
		ITawSystemUserManager ugr = (ITawSystemUserManager) getBean("itawSystemUserManager");
		TawSystemRole tawSystemRole = mgr.getTawSystemRole(id);

		// 获取用户列表
		Map map = refMgr.getGroupUserbyroleid(String.valueOf(id));

		JSONArray userList = new JSONArray();

		Iterator it = map.keySet().iterator();

		while (it.hasNext()) {
			String userId = it.next().toString();
			TawSystemUser user = ugr.getUserByuserid(userId);
			String userName = user.getUsername();
			String groupType = (String) map.get(userId);
			String text = RoleConstants.groupType_leader.equals(groupType) ? userName
					+ "(" + RoleConstants.TEXT_LEADER + ")"
					: userName;

			JSONObject jitem = new JSONObject();
			jitem.put("id", user.getUserid());
			jitem.put("text", text);
			jitem.put("name", userName);
			jitem.put("grouptype", groupType);
			userList.put(jitem);
		}

		JSONObject jsonRoot = new JSONObject();
		jsonRoot = JSONObject.fromObject(tawSystemRole);
		jsonRoot.put("users", userList);
		// TODO:mios why the leaf(=1) trans to "true"?
		jsonRoot.remove("leaf");
		jsonRoot.put("leaf", tawSystemRole.getLeaf().equals(
				new Integer(StaticVariable.LEAF)) ? 1 : 0);
		JSONUtil.print(response, jsonRoot.toString());
	}

	public void xedit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemRoleForm tawSystemRoleForm = (TawSystemRoleForm) form;
		ITawSystemRoleManager mgr = (ITawSystemRoleManager) getBean("ItawSystemRoleManager");
		TawSystemRole tawSystemRole = (TawSystemRole) convert(tawSystemRoleForm);
		// TODO:mios why leaf=1 > leaf=null?
		tawSystemRole.setLeaf(tawSystemRoleForm.getLeaf());
		mgr.saveTawSystemRole(tawSystemRole);
		JSONUtil.success(response, "success");
	}

	/**
	 * 含分页的子角色列表,用于角色管理的子角色列表
	 */
	public void xGetSubRoleList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String roleId = StaticMethod.null2String(request.getParameter("id"));
		String deptId = StaticMethod
				.null2String(request.getParameter("deptId"));
		String type1 = StaticMethod.null2String(request.getParameter("class1"));
		String type2 = StaticMethod.null2String(request.getParameter("class2"));
		String type3 = StaticMethod.null2String(request.getParameter("class3"));
		String type4 = StaticMethod.null2String(request.getParameter("class4"));
		String type5 = StaticMethod.null2String(request.getParameter("class5"));
		String type6 = StaticMethod.null2String(request.getParameter("class6"));
		String type7 = StaticMethod.null2String(request.getParameter("class7"));
		String type8 = StaticMethod.null2String(request.getParameter("class8"));
		String query = StaticMethod.null2String(request.getParameter("q"));
		String isShowUser = StaticMethod.null2String(request
				.getParameter("isShowUser"));
		Integer start = new Integer(StaticMethod.nullObject2int(request
				.getParameter("start")));
		Integer limit = new Integer(StaticMethod.nullObject2int(request
				.getParameter("limit")));

		String whereStr = "";
		if (!"".equals(roleId)) {
			whereStr += "roleId=" + roleId;
			if (!"".equals(deptId))
				whereStr += " and deptId='" + deptId + "'";
			if (!"".equals(type1))
				whereStr += " and type1='" + type1 + "'";
			if (!"".equals(type2))
				whereStr += " and type2='" + type2 + "'";
			if (!"".equals(type3))
				whereStr += " and type3='" + type3 + "'";
			if (!"".equals(type4))
				whereStr += " and type4='" + type4 + "'";
			if (!"".equals(type5))
				whereStr += " and type5='" + type5 + "'";
			if (!"".equals(type6))
				whereStr += " and type6='" + type6 + "'";
			if (!"".equals(type7))
				whereStr += " and type7='" + type7 + "'";
			if (!"".equals(type8))
				whereStr += " and type8='" + type8 + "'";
			if (!"".equals(query))
				whereStr += " and UPPER(subRoleName) like UPPER('%" + query
						+ "%')";
		}

		ITawSystemSubRoleManager mgr = (ITawSystemSubRoleManager) getBean("ItawSystemSubRoleManager");
		Map map = (Map) mgr.mapTawSystemSubRoles(start, limit, whereStr);
		List list = (List) map.get("result");
		Integer total = (Integer) map.get("total");

		ITawSystemUserRefRoleManager userMgr = (ITawSystemUserRefRoleManager) getBean("itawSystemUserRefRoleManager");

		JSONArray jsonList = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			TawSystemSubRole subrole = (TawSystemSubRole) list.get(i);
			String deptName = ID2NameServiceFactory.getId2nameServiceDB()
					.id2Name(subrole.getDeptId(), "tawSystemDeptDao");
			JSONObject jitem = new JSONObject();
			jitem.put("id", subrole.getId());
			jitem.put("subRoleName", subrole.getSubRoleName());
			jitem.put("deptId", subrole.getDeptId());
			jitem.put("deptName", deptName);
			jitem.put("roleId", subrole.getRoleId());
			jitem.put("type1", subrole.getType1());
			jitem.put("type2", subrole.getType2());
			jitem.put("type3", subrole.getType3());
			jitem.put("type4", subrole.getType4());
			jitem.put("type5", subrole.getType5());
			jitem.put("type6", subrole.getType6());
			jitem.put("type7", subrole.getType7());
			jitem.put("type8", subrole.getType8());

			if ("true".equals(isShowUser)) {
				List userList = userMgr.getUserbyroleid(subrole.getId());
				JSONArray userArray = new JSONArray();
				for (int j = 0; j < userList.size(); j++) {
					TawSystemUser user = (TawSystemUser) userList.get(j);
					JSONObject useritem = new JSONObject();

					useritem.put("userid", user.getUserid());
					useritem.put("username", user.getUsername());

					userArray.put(useritem);
				}
				jitem.put("userList", userArray);
			}

			jsonList.put(jitem);
		}

		JSONObject jsonRoot = new JSONObject();
		jsonRoot.put("total", total);
		jsonRoot.put("rows", jsonList);

		JSONUtil.print(response, jsonRoot.toString());
	}

	/**
	 * 选择派发中改变roleId后获取新roleName和区分度html
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void resetChooserRoleId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String chooserId = StaticMethod.null2String(request
				.getParameter("chooserId"));
		long roleId = StaticMethod.null2Long(request.getParameter("roleId"));

		// 获取新roleName
		ITawSystemRoleManager mgr = (ITawSystemRoleManager) getBean("ItawSystemRoleManager");
		String roleName = mgr.getRoleNameById(roleId);

		// 获取区分度html
		String html = ChooserTag.getDeptList(chooserId, roleId);
		String data = "{newRoleName:'" + roleName + "',filterHTML:'" + html
				+ "'}";
		JSONUtil.print(response, data);
	}

	/**
	 * 根据参数搜索某大角色下的子角色列表,子角色下可查询该子角色的人员 用于工单派发窗口中，搜索子角色
	 */
	public ActionForward xSearchSubRoleNodes(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String roleId = StaticMethod.null2String(request.getParameter("roleId"));
		String deptId = StaticMethod.null2String(request.getParameter("deptId"));
		String type1 = StaticMethod.null2String(request.getParameter("class1"));
		String type2 = StaticMethod.null2String(request.getParameter("class2"));
		String type3 = StaticMethod.null2String(request.getParameter("class3"));
		String type4 = StaticMethod.null2String(request.getParameter("class4"));
		String type5 = StaticMethod.null2String(request.getParameter("class5"));
    	String type6 = StaticMethod.null2String(request.getParameter("class6"));
    	String type7 = StaticMethod.null2String(request.getParameter("class7"));
    	String type8 = StaticMethod.null2String(request.getParameter("class8"));
		String node = StaticMethod.null2String(request.getParameter("node"));
		String nodeType = StaticMethod.null2String(request.getParameter("nodeType"));
		String template = StaticMethod.null2String(request.getParameter("tpl"));

		IRoleMgr roleMgr = (IRoleMgr) getBean("RoleMgrFlush");
		TawSystemRole role = roleMgr.getRole(roleId);
		boolean isVirtual = String.valueOf(role.getRoleTypeId()).equals(RoleConstants.ROLETYPE_VIRTUAL);
		
		String whereStr = "";
		
		if (!"".equals(roleId)) {
			whereStr += "roleId=" + roleId;
			if (!"".equals(deptId) && request.getParameter("allDept") == null)
				whereStr += " and deptId in(" + deptId + ")";
			if (!"".equals(type1) && request.getParameter("allClass") == null)
				whereStr += " and type1='" + type1 + "'";
			if (!"".equals(type2) && request.getParameter("allClass") == null)
				whereStr += " and type2='" + type2 + "'";
			if (!"".equals(type3) && request.getParameter("allClass") == null)
				whereStr += " and type3='" + type3 + "'";
			if (!"".equals(type4) && request.getParameter("allClass") == null)
				whereStr += " and type4='" + type4 + "'";
			if(!"".equals(type5) && request.getParameter("allClass")==null)
    			whereStr += " and type5='"+type5+"'";
    		if(!"".equals(type6) && request.getParameter("allClass")==null)
    			whereStr += " and type6='"+type6+"'";
    		if(!"".equals(type7) && request.getParameter("allClass")==null)
    			whereStr += " and type7='"+type7+"'";
    		if(!"".equals(type8) && request.getParameter("allClass")==null)
    			whereStr += " and type8='"+type8+"'";
		}

		List list = new ArrayList();

		if (UIConstants.NODETYPE_ROOT.equals(nodeType)) {
			ITawSystemSubRoleManager mgr = (ITawSystemSubRoleManager) getBean("ItawSystemSubRoleManager");
			list = mgr.getTawSystemSubRoles(whereStr);
			template = "tpl-subrole-xtree";
			
			//如果是虚拟组，则输出组长信息
			if(isVirtual){
				request.setAttribute("nodeTemplate", "node-with-leader");
			}
		} else if (UIConstants.NODETYPE_SUBROLE.equals(nodeType)) {
			
			// 取出子角色下的人员
			ITawSystemUserRefRoleManager userMgr = (ITawSystemUserRefRoleManager) getBean("itawSystemUserRefRoleManager");
			list = userMgr.getUserbyroleid(node);
			
			
			// 如果是虚拟组，取出组长
			if(isVirtual){
				String[]leader = roleMgr.getRoleLeaderBySubRoleid(node);
				if(leader != null && leader.length >0){
					
					//按组长排序
					list = leaderSorter(list, leader[0]);					
					request.setAttribute("nodeTemplate", "node-with-leader");
					request.setAttribute("leaderId", leader[0]);									
				}
			}
			
			template = "tpl-user-xtree";
		}

		request.setAttribute("list", list);
		return mapping.findForward(template);
	}

	/**
	 * 根据流程ID显示流程下的大角色-地域-子角色-人员，用于工单查询
	 */
	public ActionForward xGetSubRoleNodesFromFlow(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String node = StaticMethod.null2String(request.getParameter("node"));
		String parentNode = StaticMethod.null2String(request
				.getParameter("parentNode"));
		String nodeType = StaticMethod.null2String(request
				.getParameter("nodeType"));
		String template = StaticMethod.null2String(request.getParameter("tpl"));
		List list = new ArrayList();
		ITawSystemRoleManager roleManager = (ITawSystemRoleManager) getBean("ItawSystemRoleManager");

		if (UIConstants.NODETYPE_ROOT.equals(nodeType)) {
			// 取出流程下的大角色
			int workflowFlag = StaticMethod.null2int(node);
			list = (ArrayList) roleManager
					.getFlwRolesByWorkflowFlag(workflowFlag);
			// 将leaf值统一设置为0
			request.setAttribute(UIConstants.DEFAULT_LEAF, new Integer(0));
			template = "tpl-role-xtree";
		} else if (UIConstants.NODETYPE_ROLE.equals(nodeType)) {
			// 先取出地域分类
			list = roleManager.getAreaByRoleId(StaticMethod.null2Long(node));
			TawSystemArea areanull = new TawSystemArea();
			// 添加一个特殊的地域,用于查找字段值为null的数据
			areanull.setAreaid("null");
			areanull.setAreaname("其他");
			areanull.setLeaf("0");
			list.add(areanull);
			template = "tpl-area-xtree";

		} else if ("area".equals(nodeType)) {
			// 取出地域下的子角色,此时,在deptId中查询地域id
			String whereStr = "deptId =" + node;
			// roleId为parentNode
			if (!"".equals(parentNode)) {
				whereStr += " and roleId=" + parentNode;
			}

			ITawSystemSubRoleManager subrolemgr = (ITawSystemSubRoleManager) getBean("ItawSystemSubRoleManager");
			list = subrolemgr.getTawSystemSubRoles(whereStr);
			template = "tpl-subrole-xtree";
		} else if (UIConstants.NODETYPE_SUBROLE.equals(nodeType)) {
			// 取出子角色下的人员
			ITawSystemUserRefRoleManager userMgr = (ITawSystemUserRefRoleManager) getBean("itawSystemUserRefRoleManager");
			list = userMgr.getUserbyroleid(node);
			template = "tpl-user-xtree";
		}
		request.setAttribute("list", list);
		return mapping.findForward(template);
	}

	/**
	 * 根据roleId显示大角色下的地域-子角色-人员，用于工单选择
	 */
	public ActionForward xGetSubRoleNodesFromArea(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String node = StaticMethod.null2String(request.getParameter("node"));
		String nodeType = StaticMethod.null2String(request.getParameter("nodeType"));
		String template = StaticMethod.null2String(request.getParameter("tpl"));
		String roleId = StaticMethod.null2String(request.getParameter("roleId"));
		String flowId = StaticMethod.null2String(request.getParameter("flowId"));
		
        //过滤flowid前面的"0" 例如：若取出为047，则返回47
		if (flowId != null ) {
			int iflowId=Integer.parseInt(flowId);
			flowId = iflowId+"";
		}
		
		List list = new ArrayList();
		
		ITawSystemRoleManager roleManager = (ITawSystemRoleManager) getBean("ItawSystemRoleManager");
		TawSystemRole role = roleManager.getTawSystemRole(StaticMethod.null2Long(roleId));
		boolean isVirtual = String.valueOf(role.getRoleTypeId()).equals(RoleConstants.ROLETYPE_VIRTUAL);

		List filters = RoleMapSchema.getInstance().getRoleMappingListById(flowId);
		
		//node为根节点，输出大角色对应的所有地域列表
		if (UIConstants.NODETYPE_ROOT.equals(nodeType)) {
			
			list = roleManager.getAreaByRoleId(StaticMethod.null2Long(roleId));
			
			request.setAttribute("nodeTemplate", "node-with-other");
			template = "tpl-area-xtree";
		} 
		
		//node为地域节点
		else if (UIConstants.NODETYPE_AREA.equals(nodeType)) {
			
			// 先列出含有子角色的字典项
			list = this.listDictFolders(roleId, filters, node);
			
			// 再列出没配字典项的子角色
			List subrolelist = this.listDirectSubroles(roleId, filters, node);
					
			request.setAttribute("subrolelist", subrolelist);
			
			// 转向到字典项、子角色混合模板
			template = "tpl-dict-xtree-subrole";
		} 
		
		//如果有未配任何地域的子角色，则先列出字典项列表
		else if ("other".equals(nodeType)){
			
			// 列出没配地域的含有子角色的字典项
			list = this.listDictFolders(roleId, filters, null);
			
			// 列出没配地域且没配字典项的子角色
			List subrolelist = this.listDirectSubroles(roleId, filters, null);

			request.setAttribute("subrolelist", subrolelist);
			
			//转向到字典项、子角色混合模板
			template = "tpl-dict-xtree-subrole";
			
		}
		
		//node为字典项节点
		else if (UIConstants.NODETYPE_DICT.equals(nodeType)) {
			
			String areaId = "";
			if (UIConstants.NODETYPE_AREA.equals(request.getParameter("parentNodeType"))) {
				areaId = StaticMethod.null2String(request.getParameter("parentNode"));
			}
			
			// 取出字典项下的子角色
			list = this.listSubrolesByAreaDict(roleId, areaId, node);
			template = "tpl-subrole-xtree";
			
			//如果是虚拟组，则输出组长信息
			if(isVirtual){
				request.setAttribute("nodeTemplate", "node-with-leader");
			}
		} 
		
		//node为子角色节点
		else if (UIConstants.NODETYPE_SUBROLE.equals(nodeType)) {
			
			// 取出子角色下的人员
			ITawSystemUserRefRoleManager userMgr = (ITawSystemUserRefRoleManager) getBean("itawSystemUserRefRoleManager");
			list = userMgr.getUserbyroleid(node);
			
			// 如果是虚拟组，取出组长
			if(isVirtual){
				IRoleMgr subroleMgr = (IRoleMgr) getBean("RoleMgrFlush");
				String[]leader = subroleMgr.getRoleLeaderBySubRoleid(node);
				if(leader != null && leader.length >0){
					
					//按组长排序
					list = leaderSorter(list, leader[0]);			
					request.setAttribute("nodeTemplate", "node-with-leader");
					request.setAttribute("leaderId", leader[0]);									
				}
			}
					
			template = "tpl-user-xtree";
		}

		request.setAttribute("list", list);
		return mapping.findForward(template);
	}

	/**
	 * 根据roleId返回大角色下的多级地域-字典项-子角色的树形节点列表，用于工单选择
	 */
	public ActionForward xTreeSubRoleByArea(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String node = StaticMethod.null2String(request.getParameter("node"));
		String nodeType = StaticMethod.null2String(request.getParameter("nodeType"));
		String template = StaticMethod.null2String(request.getParameter("tpl"));
		String roleId = StaticMethod.null2String(request.getParameter("roleId"));
		String flowId = StaticMethod.null2String(request.getParameter("flowId"));
		
        //过滤flowid前面的"0" 例如：若取出为047，则返回47
		if (flowId != null ) {
			int iflowId=Integer.parseInt(flowId);
			flowId = iflowId+"";
		}
		
		List list = new ArrayList();
		
		ITawSystemRoleManager roleManager = (ITawSystemRoleManager) getBean("ItawSystemRoleManager");
		TawSystemRole role = roleManager.getTawSystemRole(StaticMethod.null2Long(roleId));
		boolean isVirtual = String.valueOf(role.getRoleTypeId()).equals(RoleConstants.ROLETYPE_VIRTUAL);

		//取出roleconfig中的字典项列表
		List filters = RoleMapSchema.getInstance().getRoleMappingListById(flowId);
		
		//node为根节点，输出大角色对应的一级地域列表
		if (UIConstants.NODETYPE_ROOT.equals(nodeType)) {

			list = roleManager.listFirstLevelAreaByRoleId(StaticMethod.null2Long(roleId));

			request.setAttribute("nodeTemplate", "node-with-other");
			template = "tpl-area-xtree";
		}
		
		// 如果有未配任何地域的子角色，则先列出字典项列表
		else if ("other".equals(nodeType)){
			
			// 列出没配地域的含有子角色的字典项
			list = this.listDictFolders(roleId, filters, null);
			
			// 列出没配地域且没配字典项的子角色
			List subrolelist = this.listDirectSubroles(roleId, filters, null);

			request.setAttribute("subrolelist", subrolelist);
			
			//转向到字典项、子角色混合模板
			template = "tpl-dict-xtree-subrole";
			
		}
		//node为某一级地域节点，输出下级地域、字典项或子角色列表
		else if (UIConstants.NODETYPE_AREA.equals(nodeType)) {
			
			//根据node取下级地域
			list = roleManager.listSubLevelAreaByRoleId(StaticMethod.null2Long(roleId), node);
			
			// 先列出含有子角色的字典项
			List dictlist = this.listDictFolders(roleId, filters, node);
			
			// 再列出没配字典项的子角色
			List subrolelist = this.listDirectSubroles(roleId, filters, node);

			request.setAttribute("dictlist", dictlist);
			request.setAttribute("subrolelist", subrolelist);
			
			//转向到地域-字典-子角色混合模板
			request.setAttribute("nodeTemplate", "area-dict-subrole");
			template = "tpl-area-xtree";
		}
		
		//node为字典项，输出该字典项对应的子角色列表
		else if (UIConstants.NODETYPE_DICT.equals(nodeType)) {
			
			String areaId = "";
			if (UIConstants.NODETYPE_AREA.equals(request.getParameter("parentNodeType"))) {
				areaId = StaticMethod.null2String(request.getParameter("parentNode"));
			}
			
			// 取出字典项下的子角色
			list = this.listSubrolesByAreaDict(roleId, areaId, node);
			template = "tpl-subrole-xtree";
			
			//如果是虚拟组，则输出组长信息
			if(isVirtual){
				request.setAttribute("nodeTemplate", "node-with-leader");
			}
		} 
		
		//node为子角色，输出该子角色下的人员列表
		else if (UIConstants.NODETYPE_SUBROLE.equals(nodeType)) {
			
			//取出子角色下的人员
			ITawSystemUserRefRoleManager userMgr = (ITawSystemUserRefRoleManager) getBean("itawSystemUserRefRoleManager");
			list = userMgr.getUserbyroleid(node);
			
			// 如果是虚拟组，取出组长
			if(isVirtual){
				IRoleMgr subroleMgr = (IRoleMgr) getBean("RoleMgrFlush");
				String[]leader = subroleMgr.getRoleLeaderBySubRoleid(node);
				if(leader != null && leader.length >0){
					
					//按组长排序
					list = leaderSorter(list, leader[0]);
					request.setAttribute("nodeTemplate", "node-with-leader");
					request.setAttribute("leaderId", leader[0]);									
				}
			}
					
			template = "tpl-user-xtree";
		}

		request.setAttribute("list", list);
		return mapping.findForward(template);
	}
	
	/**
	 * 鐢ㄤ簬chooserTag閫氳繃filter鏌ユ壘鍖归厤鐨勫瓙瑙掕壊
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	// from unicom 09-5-5
	public void xGetSubRoleNodes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String roleId = StaticMethod
				.null2String(request.getParameter("roleId"));
		String deptId = StaticMethod
				.null2String(request.getParameter("deptId"));
		String type1 = StaticMethod.null2String(request.getParameter("class1"));
		String type2 = StaticMethod.null2String(request.getParameter("class2"));
		String type3 = StaticMethod.null2String(request.getParameter("class3"));
		String type4 = StaticMethod.null2String(request.getParameter("class4"));
    	String type5 = StaticMethod.null2String(request.getParameter("class5"));
		String type6 = StaticMethod.null2String(request.getParameter("class6"));
		String type7 = StaticMethod.null2String(request.getParameter("class7"));
		String type8 = StaticMethod.null2String(request.getParameter("class8"));
		String isShowUser = StaticMethod.null2String(request
				.getParameter("isShowUser"));

		String whereStr = "";
		if (!"".equals(roleId)) {
			whereStr += "roleId=" + roleId;
			if (!"".equals(deptId) && request.getParameter("allDept") == null)
				whereStr += " and deptId in(" + deptId + ")";
			if (!"".equals(type1) && request.getParameter("allClass") == null)
				whereStr += " and type1='" + type1 + "'";
			if (!"".equals(type2) && request.getParameter("allClass") == null)
				whereStr += " and type2='" + type2 + "'";
			if (!"".equals(type3) && request.getParameter("allClass") == null)
				whereStr += " and type3='" + type3 + "'";
			if (!"".equals(type4) && request.getParameter("allClass") == null)
				whereStr += " and type4='" + type4 + "'";
			if(!"".equals(type5) && request.getParameter("allClass")==null)
    			whereStr += " and type5='"+type5+"'";
    		if(!"".equals(type6) && request.getParameter("allClass")==null)
    			whereStr += " and type6='"+type6+"'";
    		if(!"".equals(type7) && request.getParameter("allClass")==null)
    			whereStr += " and type7='"+type7+"'";
    		if(!"".equals(type8) && request.getParameter("allClass")==null)
    			whereStr += " and type8='"+type8+"'";
		}

		ITawSystemSubRoleManager mgr = (ITawSystemSubRoleManager) getBean("ItawSystemSubRoleManager");
		List list = mgr.getTawSystemSubRoles(whereStr);

		ITawSystemUserRefRoleManager userMgr = (ITawSystemUserRefRoleManager) getBean("itawSystemUserRefRoleManager");
		JSONArray jsonList = new JSONArray();

		int listSize = list.size();
		for (int i = 0; i < listSize; i++) {
			TawSystemSubRole subrole = (TawSystemSubRole) list.get(i);
			JSONObject jitem = new JSONObject();
			jitem.put("id", subrole.getId());
			jitem.put("text", subrole.getSubRoleName());
			jitem.put("name", subrole.getSubRoleName());
			jitem.put("iconCls", "subrole");
			jitem.put("nodeType", "subrole");

			if ("true".equals(isShowUser)) {
				List userList = userMgr.getUserbyroleid(subrole.getId());
				Map map = userMgr.getGroupUserbyroleid(subrole.getId());
				JSONArray userArray = new JSONArray();

				int userListSize = userList.size();
				for (int j = 0; j < userListSize; j++) {
					TawSystemUser user = (TawSystemUser) userList.get(j);
					JSONObject useritem = new JSONObject();
					String userId = user.getUserid();
					String userName = user.getUsername();
					useritem.put("id", userId);
					useritem.put("name", userName);
					useritem.put("text", userName);

					useritem.put("groupType", map.get(userId));
					String groupType = StaticMethod.nullObject2String(map
							.get(userId), RoleConstants.groupType_common);
					if (groupType.equals(RoleConstants.groupType_leader)) {
						jitem.put("leaderId", userId);
						jitem.put("leaderName", userName);
						jitem.put("info", "(" + RoleConstants.TEXT_LEADER + ":"
								+ userName + ")");
					}

					useritem.put("iconCls", "user");
					useritem.put("nodeType", "user");
					useritem.put("leaf", true);

					userArray.put(useritem);
				}
				jitem.put("user", userArray);
				if (userList.size() == 0) {
					jitem.put("leaf", true);
				}
			}

			jsonList.put(jitem);
		}

		JSONUtil.print(response, jsonList.toString());
	}

	/**
	 * 流程角色管理节点下出所有工单列表
	 * 
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	// from unicom 09-5-5
	public void getAllWorkflow(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONArray jsonRoot = new JSONArray();
		List list = new ArrayList();
		try {
			list = DictMgrLocator.getDictService().getDictItems(
					"dict-wfworksheet#allworkflow");
		} catch (Exception e) {

		}
		for (Iterator it = list.iterator(); it.hasNext();) {
			DictItemXML workflow = (DictItemXML) it.next();
			String workflowId = workflow.getId();
			String workflowName = workflow.getName();
			JSONObject j = new JSONObject();
			j.put(UIConstants.JSON_ID, workflowId);
			j.put(UIConstants.JSON_TEXT, workflowName);
			j.put(UIConstants.JSON_NODETYPE, "workflow");
			jsonRoot.put(j);
		}
		JSONUtil.print(response, jsonRoot.toString());
	}

	// from unicom 09-5-5
	public void xGetSubRoleListMatch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String roleId = StaticMethod.null2String(request.getParameter("id"));
		String deptId = StaticMethod
				.null2String(request.getParameter("deptId"));
		String type1 = StaticMethod.null2String(request.getParameter("class1"));
		String isShowUser = StaticMethod.null2String(request
				.getParameter("isShowUser"));
		Integer pageIndex = new Integer(StaticMethod.nullObject2int(request
				.getParameter("start")));
		Integer pageSize = new Integer(StaticMethod.nullObject2int(request
				.getParameter("limit")));

		String whereStr = "";
		if (!"".equals(roleId)) {
			whereStr += "roleId=" + roleId;
			if (!"".equals(deptId))
				whereStr += " and deptId in(" + deptId + ")";
			if (!"".equals(type1))
				whereStr += " and type1 in(" + type1 + ")";
		}

		ITawSystemSubRoleManager mgr = (ITawSystemSubRoleManager) getBean("ItawSystemSubRoleManager");
		Map map = (Map) mgr.getTawSystemSubRoles(pageIndex, pageSize, whereStr);

		List list = (List) map.get("result");
		Integer total = (Integer) map.get("total");

		ITawSystemUserRefRoleManager userMgr = (ITawSystemUserRefRoleManager) getBean("itawSystemUserRefRoleManager");

		JSONArray jsonList = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			TawSystemSubRole subrole = (TawSystemSubRole) list.get(i);
			String deptName = ID2NameServiceFactory.getId2nameServiceDB()
					.id2Name(subrole.getDeptId(), "tawSystemDeptDao");
			JSONObject jitem = new JSONObject();
			jitem.put("id", subrole.getId());
			jitem.put("subRoleName", subrole.getSubRoleName());
			jitem.put("deptId", subrole.getDeptId());
			jitem.put("deptName", deptName);
			jitem.put("roleId", subrole.getRoleId());
			jitem.put("type1", subrole.getType1());
			jitem.put("type2", subrole.getType2());
			jitem.put("type3", subrole.getType3());
			jitem.put("type4", subrole.getType4());
			jitem.put("type5", subrole.getType5());
			jitem.put("type6", subrole.getType6());
			jitem.put("type7", subrole.getType7());
			jitem.put("type8", subrole.getType8());

			if ("true".equals(isShowUser)) {
				List userList = userMgr.getUserbyroleid(subrole.getId());
				JSONArray userArray = new JSONArray();
				for (int j = 0; j < userList.size(); j++) {
					TawSystemUser user = (TawSystemUser) userList.get(j);
					JSONObject useritem = new JSONObject();

					useritem.put("id", user.getUserid());
					useritem.put("name", user.getUsername());
					useritem.put("text", user.getUsername());
					useritem.put("leader", false);
					useritem.put("iconCls", "user");
					useritem.put("nodeType", "user");
					useritem.put("leaf", true);

					userArray.put(useritem);
				}
				jitem.put("user", userArray);
			}

			jsonList.put(jitem);
		}

		JSONObject jsonRoot = new JSONObject();
		jsonRoot.put("total", total);
		jsonRoot.put("rows", jsonList);

		JSONUtil.print(response, jsonRoot.toString());
	}
	
	/**
	 * 将人员列表按组长排序，组长排在第一位
	 * 用于在工单派发树上显示组长
	 * @param userlist 人员列表
	 * @param leaderId 组长id
	 * @return
	 */
	private List leaderSorter(List userlist, String leaderId) throws Exception {
		final String _leaderId = leaderId;
		java.util.Collections.sort(userlist, new java.util.Comparator() {
			public int compare(Object a, Object b) {
				String ida = ((TawSystemUser)a).getUserid();
				String idb = ((TawSystemUser)b).getUserid();
				if(_leaderId.equals(ida) && !_leaderId.equals(idb)){
				  return -1;
				}
				if(!_leaderId.equals(ida) && _leaderId.equals(idb)){
				  return 1;
				}
				return 0;
			}
		});
		return userlist;
	}
	
	/**
	 * 列出含有子角色的字典项
	 * @param roleId 大角色id
	 * @param filters 该大角色配置中的字典项
	 * @param areaId 地域对象id
	 * @return
	 * @throws Exception
	 */
	private List listDictFolders(String roleId, List filters, String areaId) throws Exception {
		List list = new ArrayList();
		if (filters != null && filters.size() > 0) {
			RoleFilter filter = (RoleFilter) filters.get(0);
			String dictId = filter.getDictId();
			ITawSystemDictTypeManager _objDictManager = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
			list = _objDictManager.listDict(dictId, roleId, areaId);
		}
		return list;
	}
	
	/**
	 * 列出大角色下的子角色
	 * 如果该大角色配置中配有字典项，列出未关联字典项的子角色，如果该大角色配置中没有配字典项，则列出所有子角色
	 * @param roleId 大角色id
	 * @param filters 该大角色配置中的字典项
	 * @param areaId 地域对象id
	 * @return
	 * @throws Exception
	 */
	private List listDirectSubroles(String roleId, List filters, String areaId) throws Exception {
		List list = null;
		IRoleMgr mgr = (IRoleMgr) getBean("RoleMgrImpl");
		if (filters != null) {
			list = mgr.listSubRoleWithType1Null(areaId, roleId);
		} else {
			list = mgr.listSubRole(areaId, Integer.parseInt(roleId));
		}
		return list;
	}	
	
	/**
	 * 列出大角色下指定地域和字典项下的子角色列表
	 * @param roleId 大角色id
	 * @param areaId 地域对象id
	 * @param dictId 该子角色配置中的一级字典项，即一级网络分类type1
	 * @return
	 * @throws Exception
	 */
	private List listSubrolesByAreaDict(String roleId, String areaId, String dictId) throws Exception {
		List list = null;
		StringBuffer whereSb = new StringBuffer();
		if (!areaId.equals("") && !areaId.equals("null")) {
			whereSb.append("deptId='" + areaId + "'");
		} else {
			whereSb.append(" (deptId='' or deptId is null or deptId='null') ");
		}
		if (!dictId.equals("") && !dictId.equals("null")) {
			whereSb.append(" and type1='" + dictId + "' and roleId=" + roleId);
		} else {
			whereSb.append(" and (type1='' or type1 is null or type1='null') and roleId=" + roleId);
		}
		ITawSystemSubRoleManager mgr = (ITawSystemSubRoleManager) getBean("ItawSystemSubRoleManager");
		list = mgr.getTawSystemSubRoles(whereSb.toString());
		return list;
	}
}
