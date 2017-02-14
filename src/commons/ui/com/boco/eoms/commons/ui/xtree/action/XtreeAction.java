package com.boco.eoms.commons.ui.xtree.action;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.abdera.Abdera;
import org.apache.abdera.factory.Factory;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import utils.PartnerPrivUtils;

import com.boco.activiti.partner.process.service.ISceneTemplateService;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.EOMSAttributes;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.StaticVariable;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;
import com.boco.eoms.commons.system.cptroom.bo.TawSystemCptroomBo;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.dept.service.bo.TawSystemDeptBo;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.commons.system.priv.a_fsh.Ip_Double_choice;
import com.boco.eoms.commons.system.priv.a_fsh.Ipconfig;
import com.boco.eoms.commons.system.priv.a_fsh.Ipconfig_creat;
import com.boco.eoms.commons.system.priv.bo.TawSystemAssignBo;
import com.boco.eoms.commons.system.priv.model.TawSystemPrivOperation;
import com.boco.eoms.commons.system.priv.model.TawSystemPrivRegion;
import com.boco.eoms.commons.system.priv.service.ITawSystemPrivOperationManager;
import com.boco.eoms.commons.system.priv.util.PrivConstants;
import com.boco.eoms.commons.system.priv.util.PrivMgrLocator;
import com.boco.eoms.commons.system.role.model.TawSystemRole;
import com.boco.eoms.commons.system.role.model.TawSystemSubRole;
import com.boco.eoms.commons.system.role.model.TawSystemWorkflow;
import com.boco.eoms.commons.system.role.service.ITawSystemPostManager;
import com.boco.eoms.commons.system.role.service.ITawSystemRoleManager;
import com.boco.eoms.commons.system.role.service.ITawSystemRoleRefWorkflowManager;
import com.boco.eoms.commons.system.role.service.ITawSystemSubRoleManager;
import com.boco.eoms.commons.system.role.util.RoleConstants;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.ITawSystemUserRefRoleManager;
import com.boco.eoms.commons.system.user.service.bo.ITawSystemUserBo;
import com.boco.eoms.commons.system.user.service.bo.impl.TawSystemUserRoleBo;
import com.boco.eoms.commons.ui.util.JSONUtil;
import com.boco.eoms.commons.ui.util.UIConstants;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcService;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerUser;
import com.boco.eoms.workbench.contact.service.bo.TawWorkbenchContactBO;

/**
 * <p>
 * Title:XtreeAction
 * </p>
 * <p>
 * Description:ajax树图类暂时入口，各action应该放到各自的模块的action中，避免耦合
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007 0514
 * </p>
 * <p>
 * Company: BOCO
 * </p>
 * 
 */

public final class XtreeAction extends BaseAction {

	/**
	 * 未定义method时，转向到main
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return main(mapping, form, request, response);
	}

	public ActionForward main(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("main");
	}

	/**
	 * 导航树
	 * 
	 * @template tpl-priv-xtree
	 */
	public ActionForward nav(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		// 获取当前用户
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		boolean _bIsAdmin = "admin".equals(sessionform.getUserid());

		String node = StaticMethod.null2String(request.getParameter("node"));
		String template = StaticMethod.null2String(request.getParameter("tpl"),
				"tpl-priv-xtree");
		List list = null;

		// TODO 重构代码，将业务写入mgr中，by leo
		EOMSAttributes attr = (EOMSAttributes) ApplicationContextHolder
				.getInstance().getBean("eomsAttributes");
		String menu_ip = attr.getMenu_ipOpen();
		if (menu_ip.equals("on")) {
			String ip = request.getServerName();
			int port = request.getServerPort();
			String type = Ip_Double_choice.checkIp(ip + ":" + port);
			Ipconfig ipconfig = Ipconfig_creat.getIpconfig();
			// TODO 重构代码，将业务写入mgr中，by leo
			if (_bIsAdmin) {
				ITawSystemPrivOperationManager operationMgr = (ITawSystemPrivOperationManager) getBean("ItawSystemPrivOperationManager");
				list = Ip_Double_choice.ip2DoubleIpFromOperation(operationMgr
						.getTawSystemPrivOerationsByParentCode(node), ipconfig,
						type);
			} else {
				list = Ip_Double_choice
						.ip2DoubleIpFromOperation(
								PrivMgrLocator
										.getPrivMgr()
										.listOpertion(
												sessionform.getUserid(),
												sessionform.getDeptid(),
												sessionform.getRolelist(),
												PrivConstants.LIST_OPERATION_TYPE_MOUDLE_FUNCTION,
												node), ipconfig, type);
			}
		} else {
			if (_bIsAdmin) {
				ITawSystemPrivOperationManager operationMgr = (ITawSystemPrivOperationManager) getBean("ItawSystemPrivOperationManager");
				list = operationMgr.getTawSystemPrivOerationsByParentCode(node);
			} else {
				list = PrivMgrLocator
						.getPrivMgr()
						.listOpertion(
								sessionform.getUserid(),
								sessionform.getDeptid(),
								sessionform.getRolelist(),
								PrivConstants.LIST_OPERATION_TYPE_MOUDLE_FUNCTION,
								node);
			}
		}

		request.setAttribute("list", list);
		return mapping.findForward(template);
	}

	/**
	 * 部门树
	 * 
	 * @template tpl-dept-xtree
	 */
	public ActionForward dept(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		String node = StaticMethod.null2String(request.getParameter("node"),
				StaticVariable.ProvinceID + "");
		String template = StaticMethod.null2String(request.getParameter("tpl"),
				"tpl-dept-xtree");
		ArrayList list = new ArrayList();

		try {
			TawSystemDeptBo deptbo = TawSystemDeptBo.getInstance();
			TawSystemDept sept = deptbo.getDeptinfobydeptid(node, "0");

			if (sept.getTmpdeptid() != null && !"".equals(sept.getTmpdeptid())) {
				list = (ArrayList) deptbo.getNextLevecDepts(
						sept.getTmpdeptid(), "0");
			} else {
				list = (ArrayList) deptbo.getNextLevecDepts(node, "0");
			}
		} catch (Exception ex) {
			BocoLog.error(this, "生成部门树图时报错：" + ex);
		}

		request.setAttribute("list", list);
		return mapping.findForward(template);
	}

	/**
	 * 部门树 值班用
	 * 
	 * @template tpl-dept-xtree
	 */
	public ActionForward deptForDuty(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		TawSystemSessionForm saveSessionBeanForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = saveSessionBeanForm.getUserid();

		String node = StaticMethod.null2String(request.getParameter("node"),
				StaticVariable.ProvinceID + "");
		String template = StaticMethod.null2String(request.getParameter("tpl"),
				"tpl-dept-xtree");

		ArrayList list = new ArrayList();

		try {
			if (userId.equals(StaticVariable.ADMIN)) {
				TawSystemDeptBo deptbo = TawSystemDeptBo.getInstance();
				list = (ArrayList) deptbo.getNextLevecDepts(node, "0");

			} else {
				TawSystemAssignBo privBO = TawSystemAssignBo.getInstance();
				if (node.equals("-1")) {// add by lixiaoming
					List privList = privBO.getPermissions(userId,
							StaticVariable.PRIV_ASSIGNTYPE_USER,
							StaticVariable.PRIV_TYPE_REGION_DEPT);

					for (Iterator it = privList.iterator(); it.hasNext();) {

						TawSystemPrivRegion tawSystemPrivRegion = (TawSystemPrivRegion) it
								.next();

						String deptId = tawSystemPrivRegion.getRegionid();
						ITawSystemDeptManager mgr = (ITawSystemDeptManager) ApplicationContextHolder
								.getInstance().getBean("ItawSystemDeptManager");
						TawSystemDept tawSystemDept = mgr.getDeptinfobydeptid(
								deptId, "0");
						tawSystemDept.setLeaf("1");
						list.add(tawSystemDept);
					}
				} else {// add by lixiaoming
					TawSystemDeptBo deptbo = TawSystemDeptBo.getInstance();// add
																			// by
																			// lixiaoming
					list = (ArrayList) deptbo.getNextLevecDepts(node, "0");// add
																			// by
																			// lixiaoming
				}

			}

		} catch (Exception ex) {
			BocoLog.error(this, "生成部门值班树图时报错：" + ex);
		}
		request.setAttribute("list", list);
		return mapping.findForward(template);
	}

	/**
	 * 大角色树图 roleTypeId=1 显示流程大角色 others 显示系统大角色
	 * 
	 * @template tpl-role-xtree
	 */
	public ActionForward role(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		long node = StaticMethod.null2Long(request.getParameter("node"));
		String template = StaticMethod.null2String(request.getParameter("tpl"),
				"tpl-role-xtree");
		String roleTypeId = request.getParameter("roleTypeId");
		ArrayList list = new ArrayList();

		try {

			ITawSystemRoleManager roleMgr = (ITawSystemRoleManager) getBean("ItawSystemRoleManager");
			if (RoleConstants.flowRole.equals(roleTypeId)) {
				// roleTypeId=1,则按流程取角色，将nodeId视为workFlowFlag
				int workflowFlag = (int) node;
				list = (ArrayList) roleMgr
						.getFlwRolesByWorkflowFlag(workflowFlag);
			} else {
				// 否则取系统角色
				list = (ArrayList) roleMgr.getSysRolesByRoleId(node);
			}

		} catch (Exception ex) {
			BocoLog.error(this, "生成角色树图时报错：" + ex);
		}

		request.setAttribute("list", list);
		return mapping.findForward(template);
	}

	/**
	 * 流程大角色和子角色树图
	 * 
	 * @template tpl-role-xtree,tpl-subrole-xtree
	 * 
	 */
	public ActionForward flowRoleSubrole(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		long node = StaticMethod.null2Long(request.getParameter("node"));
		String nodeType = StaticMethod.null2String(request
				.getParameter("nodeType"));
		String template = StaticMethod.null2String(request.getParameter("tpl"),
				"tpl-role-xtree");
		List list = new ArrayList();
		ITawSystemSubRoleManager subRoleMgr = (ITawSystemSubRoleManager) getBean("ItawSystemSubRoleManager");

		try {
			if ("workflow".equals(nodeType)) { // 如果父节点是流程，取此流程下的大角色
				ITawSystemRoleManager roleMgr = (ITawSystemRoleManager) getBean("ItawSystemRoleManager");
				list = (ArrayList) roleMgr
						.getFlwRolesByWorkflowFlag((int) node); // 取流程大角色，将node视为workFlowFlag

				// 修正leaf 属性 有子角色则leaf=0
				for (int i = 0; i < list.size(); i++) {
					TawSystemRole role = (TawSystemRole) list.get(i);
					List subRoleList = subRoleMgr.getTawSystemSubRoles(role
							.getRoleId());
					if (null == subRoleList || 0 >= subRoleList.size()) {
						role.setLeaf(new Integer(StaticVariable.LEAF));
					} else {
						role.setLeaf(new Integer(StaticVariable.NOTLEAF));
					}
					list.set(i, role);
				}
				template = "tpl-role-xtree";
			} else if (UIConstants.NODETYPE_ROLE.equals(nodeType)) { // 如果父节点是大角色，取此大角色下的子角色
				list = (ArrayList) subRoleMgr.getTawSystemSubRoles(node);

				// 修正leaf 属性 全设为1
				for (int i = 0; i < list.size(); i++) {
					TawSystemSubRole subrole = (TawSystemSubRole) list.get(i);
					subrole.setLeaf(new Integer(StaticVariable.LEAF));
					list.set(i, subrole);
				}
				template = "tpl-subrole-xtree";
			}

		} catch (Exception ex) {
			BocoLog.error(this, "生成流程大角色和子角色树图时报错：" + ex);
		}

		request.setAttribute("list", list);
		return mapping.findForward(template);
	}

	/**
	 * 联系人树
	 * 
	 * @template tpl-contact-xtree
	 */
	public ActionForward getContactTree(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");

		String userId = sessionform.getUserid();
		String node = StaticMethod.null2String(request.getParameter("node"),
				StaticVariable.ProvinceID + "");

		String template = StaticMethod.null2String(request.getParameter("tpl"),
				"tpl-contact-xtree");
		TawWorkbenchContactBO contactbo = TawWorkbenchContactBO.getInstance();
		ArrayList grouplist = new ArrayList();
		ArrayList contactlist = new ArrayList();
		try {
			grouplist = (ArrayList) contactbo.getNextLevecGroups(node, userId,
					"0");
			contactlist = (ArrayList) contactbo.getNextLevecContact(userId,
					node, "0");
		} catch (Exception ex) {
			BocoLog.error(this, "生成联系人树图时报错：" + ex);
		}
		request.setAttribute("grouplist", grouplist);
		request.setAttribute("contactlist", contactlist);
		return mapping.findForward(template);
	}

	/**
	 * 用户树(某部门下)
	 * 
	 * @template tpl-user-xtree
	 */
	public ActionForward user(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		String node = StaticMethod.null2String(request.getParameter("node"),
				StaticVariable.ProvinceID + "");
		String template = StaticMethod.null2String(request.getParameter("tpl"),
				"tpl-user-xtree");
		ArrayList userlist = new ArrayList();
		TawSystemUserRoleBo userrolebo = TawSystemUserRoleBo.getInstance();
		try {
			userlist = (ArrayList) userrolebo.getUserBydeptids(node);
		} catch (Exception ex) {
			BocoLog.error(this, "生成部门用户树图时报错：" + ex);
		}
		request.setAttribute("list", userlist);
		return mapping.findForward(template);
	}

	/**
	 * 用户树
	 */
	public ActionForward userByDept(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return this.user(mapping, actionForm, request, response);
	}

	/**
	 * 用户树
	 */
	public ActionForward userByDeptForTaskplan(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return this.user(mapping, actionForm, request, response);
	}

	/**
	 * 部门和用户树(所有部门以及用户)
	 * 
	 * @template tpl-user-xtree-fromdept
	 */
	public ActionForward userFromDept(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String node = StaticMethod.null2String(request.getParameter("node"),
				StaticVariable.ProvinceID + "");
		
		//传输局区分发：0-》1,1-》2,2-》3	
		String level = StaticMethod.null2String(request.getParameter("level"), "");
		
		return userFromDeptCommon(node, request, mapping,level);
	}
	/**
	 * 部门和用户树(所有部门以及用户)---故障工单
	 * 
	 * @template tpl-user-xtree-fromdept
	 */
	public ActionForward userFromDeptTask(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//dh 为了设置自维人员的权限；自维人员进来能看见同一组织结构的人及部门；
		String dh = request.getParameter("dh");
		//传输局区分发：0-》1,1-》2,2-》3	
		String level = StaticMethod.null2String(request.getParameter("level"), "");
		
		String node = StaticMethod.null2String(request.getParameter("node"),
				StaticVariable.ProvinceID + "");
//		JOptionPane.showMessageDialog(null,"node"+node+";dh--"+dh);
		return userFromDeptCommonTask(node, request, mapping,dh,level);
	}

	/**
	 * 部门和用户树（合作伙伴）
	 * 
	 * @template tpl-user-xtree-fromdept
	 */
	public ActionForward userFromDeptPartner(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String node = StaticMethod.null2String(request.getParameter("node"),
				StaticVariable.ProvinceID + "");
		if ("-1".equals(node)) {
			TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
					.getSession().getAttribute("sessionform");
			node = sessionForm.getRootPnrDeptId();

			String userDept = sessionForm.getDeptid();
			if (node != null && !"".equals(node) && userDept.startsWith(node)) {
				node = userDept;
			}
		}
		return userFromDeptCommon(node, request, mapping);
	}

	/**
	 * 部门和用户树（通用方法）---工单
	 */
	public ActionForward userFromDeptCommonTask(String node,
			HttpServletRequest request, ActionMapping mapping,String rootId,String level)
	throws IOException {
		String selfFlag = StaticMethod.null2String(
				request.getParameter("noself"), "");
		TawSystemDeptBo deptbo = TawSystemDeptBo.getInstance();
		TawSystemUserRoleBo userrolebo = TawSystemUserRoleBo.getInstance();
		String template = StaticMethod.null2String(request.getParameter("tpl"),
		"tpl-user-xtree-fromdept");
		ArrayList userlist = new ArrayList();
		ArrayList deptlist = new ArrayList();
		try {
			deptlist = (ArrayList) deptbo.getNextLevecDeptsTask(node, "0",rootId);
			if (selfFlag.equals("true")) {// 不包括自己的人员list
				TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
				userlist = (ArrayList) userrolebo.getUserBydeptidsNoSelfTask(node,
						sessionform.getUserid(),rootId,level);
			} else {
				userlist = (ArrayList) userrolebo.getUserBydeptidsTask(node,rootId,level);
			}
			
		} catch (Exception ex) {
			BocoLog.error(this, "生成部门用户树图时报错：" + ex);
		}
		request.setAttribute("deptlist", deptlist);
		request.setAttribute("userlist", userlist);
		return mapping.findForward(template);
	}
	/**
	 * 部门和用户树（通用方法）
	 */
	public ActionForward userFromDeptCommon(String node,
			HttpServletRequest request, ActionMapping mapping)
	throws IOException {
		String selfFlag = StaticMethod.null2String(
				request.getParameter("noself"), "");
		TawSystemDeptBo deptbo = TawSystemDeptBo.getInstance();
		TawSystemUserRoleBo userrolebo = TawSystemUserRoleBo.getInstance();
		String template = StaticMethod.null2String(request.getParameter("tpl"),
		"tpl-user-xtree-fromdept");
		ArrayList userlist = new ArrayList();
		ArrayList deptlist = new ArrayList();
		try {
			deptlist = (ArrayList) deptbo.getNextLevecDepts(node, "0");
			if (selfFlag.equals("true")) {// 不包括自己的人员list
				TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
				userlist = (ArrayList) userrolebo.getUserBydeptidsNoSelf(node,
						sessionform.getUserid());
			} else {
				userlist = (ArrayList) userrolebo.getUserBydeptids(node);
			}
			
		} catch (Exception ex) {
			BocoLog.error(this, "生成部门用户树图时报错：" + ex);
		}
		request.setAttribute("deptlist", deptlist);
		request.setAttribute("userlist", userlist);
		return mapping.findForward(template);
	}
	/**
	 * 部门和用户树（通用方法）--140808改造之后的-增加派发-筛选人条件
	 */
	public ActionForward userFromDeptCommon(String node,
			HttpServletRequest request, ActionMapping mapping,String level)
			throws IOException {
		String selfFlag = StaticMethod.null2String(
				request.getParameter("noself"), "");
		TawSystemDeptBo deptbo = TawSystemDeptBo.getInstance();
		TawSystemUserRoleBo userrolebo = TawSystemUserRoleBo.getInstance();
		String template = StaticMethod.null2String(request.getParameter("tpl"),
				"tpl-user-xtree-fromdept");
		ArrayList userlist = new ArrayList();
		ArrayList deptlist = new ArrayList();
		try {
			deptlist = (ArrayList) deptbo.getNextLevecDepts(node, "0");
			if (selfFlag.equals("true")) {// 不包括自己的人员list
				TawSystemSessionForm sessionform = (TawSystemSessionForm) request
						.getSession().getAttribute("sessionform");
				userlist = (ArrayList) userrolebo.getUserBydeptidsNoSelf(node,
						sessionform.getUserid(),level);
			} else {
				userlist = (ArrayList) userrolebo.getUserBydeptids(node,level);
			}

		} catch (Exception ex) {
			BocoLog.error(this, "生成部门用户树图时报错：" + ex);
		}
		request.setAttribute("deptlist", deptlist);
		request.setAttribute("userlist", userlist);
		return mapping.findForward(template);
	}

	/**
	 * 公司和用户树
	 * 
	 * @template tpl-user-xtree-fromdept
	 */
	public ActionForward userFromDeptComp(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// String node = "1014";
		String node = StaticMethod.null2String(request.getParameter("node"),
				StaticVariable.ProvinceID + "");
		String selfFlag = StaticMethod.null2String(
				request.getParameter("noself"), "");
		TawSystemDeptBo deptbo = TawSystemDeptBo.getInstance();

		TawSystemUserRoleBo userrolebo = TawSystemUserRoleBo.getInstance();
		String template = StaticMethod.null2String(request.getParameter("tpl"),
				"tpl-user-xtree-fromcomp");
		ArrayList userlist = new ArrayList();
		ArrayList deptlist = new ArrayList();
		int isPartner = -1;
		try {
			deptlist = (ArrayList) deptbo.getNextLevecComp(node, "0", "",isPartner);
			if (selfFlag.equals("true")) {// 不包括自己的人员list
				TawSystemSessionForm sessionform = (TawSystemSessionForm) request
						.getSession().getAttribute("sessionform");
				userlist = (ArrayList) userrolebo.getUserByCompsNoSelf(node,
						sessionform.getUserid());
			} else {
				userlist = (ArrayList) userrolebo.getUserByCompids(node);
			}

		} catch (Exception ex) {
			BocoLog.error(this, "生成部门用户树图时报错：" + ex);
		}
		request.setAttribute("deptlist", deptlist);
		request.setAttribute("userlist", userlist);
		return mapping.findForward("tpl-user-xtree-fromcomp");
		// return mapping.findForward(template);
	}

	/**
	 * 公司树
	 * 
	 * @template tpl-user-xtree-fromdept
	 */
	public ActionForward userFromComp(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String node = StaticMethod.null2String(request.getParameter("node"),
				StaticVariable.ProvinceID + "");
		String popedom = StaticMethod.null2String(
				request.getParameter("popedom"), "false");
		String showPartnerLevelType = StaticMethod.nullObject2String(request
				.getParameter("showPartnerLevelType"));
		// String selfFlag =
		// StaticMethod.null2String(request.getParameter("noself"),"");
		TawSystemDeptBo deptbo = TawSystemDeptBo.getInstance();
		String selectType = StaticMethod.nullObject2String(request
				.getParameter("selectType"));
		List deptlist = new ArrayList();
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userdeptid = sessionform.getDeptid();
		PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
		PartnerUser pnrUser = partnerUserMgr.getPartnerUserByUserId(sessionform
				.getUserid());
		int isPartner = -1;
		try {
			if (!"inspect".equals(selectType)) {// 如果不是巡检使用
				if ("true".equals(popedom)) {
					deptlist = deptbo.getNextLevecComp(node, "0", userdeptid,isPartner);
				} else {
					deptlist = deptbo.getNextLevecComp(node, "0", "",isPartner);
				}
			} else {// 如果是巡检使用
				if (pnrUser != null && !"".equals(pnrUser.getId())) {// 代维公司人员
					if ("true".equals(popedom)) {
						deptlist = deptbo.getNextLevecComp(node, "0",
								userdeptid,isPartner);
					} else {
						deptlist = deptbo.getNextLevecComp(node, "0", "",isPartner);
					}
				} else {// 移动公司人员 add by chenyuanshu ,由于移动人员也可以进行计划制定，所以增加这下面的代码
					ITawSystemDeptManager deptManager = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
					TawSystemDept dept1 = deptManager.getDeptinfobydeptid(
							sessionform.getDeptid(), "0");
					if (dept1 != null) {
						PartnerDeptMgr deptmag = (PartnerDeptMgr) ApplicationContextHolder
								.getInstance().getBean("partnerDeptMgr");
						String areaId = dept1.getAreaid();
						String sql = "";
						areaId = StaticMethod.null2String(dept1.getAreaid());
						if (areaId != null && !areaId.equals("")) {// 进入省公司list页面
							sql = " and areaId  like '" + areaId
									+ "%' and  deptLevel = 3";
							deptlist = deptmag.getPartnerDepts(sql);
						} else {
							sql = " and  deptLevel = 3";
							deptlist = deptmag.getPartnerDepts(sql);
						}
						request.setAttribute("selectType", "inspect");
					}
				}
			}
		} catch (Exception ex) {
			BocoLog.error(this, "生成部门用户树图时报错：" + ex);
		}
		request.setAttribute("deptlist", deptlist);
		request.setAttribute("popedom", popedom);
		request.setAttribute("userdeptid", userdeptid);
		if (null != showPartnerLevelType && !"".equals(showPartnerLevelType)) {
			request.setAttribute("showPartnerLevelType", showPartnerLevelType);
		}
		return mapping.findForward("tpl-user-xtree-comp");
	}

	/**
	 * 
	 * @Title: getPnrDeptWithRightDW
	 * @Description: 更加登录人员（admin/移动人员/代维人员）来获取部门信息-运维人员管理
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @throws IOException
	 * @return ActionForward
	 * @throws
	 */
	public ActionForward getPnrDeptWithRightDW(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String node = StaticMethod.null2String(request.getParameter("node"),
				StaticVariable.ProvinceID + "");
		// showPartnerLevelType为勾选的级别:1|2|3|4,1|2|3|4看626行注释;
		// showLevelControl是否需要控制勾选的级别true/false之一,默认值为false,
		// 当为true时且showPartnerLevelType有值时才有效果,缺一个条件将根据实际情况判断
		String isPartnerTemp = request.getParameter("isPartner");
		int isPartner = -1;
		try {
			isPartner = Integer.parseInt(isPartnerTemp);
		} catch (NumberFormatException e) {
		}
		request.setAttribute("isPartner", isPartner);
		String showPartnerLevelType = StaticMethod.nullObject2String(request
				.getParameter("showPartnerLevelType"));// 用于强行控制树可选的级别
		String showLevelControl = StaticMethod.nullObject2String(
				request.getParameter("showLevelControl"), "false");// 是否需要强行控制显示的级别
		TawSystemUserRoleBo userrolebo = TawSystemUserRoleBo.getInstance();
		TawSystemDeptBo deptbo = TawSystemDeptBo.getInstance();
		String template = StaticMethod.null2String(request.getParameter("tpl"),
		"tpl-user-xtree-comp-dw");
		List deptlist = new ArrayList();
		try {
			Map map = PartnerPrivUtils.userIsPersonnel(request);
			String isPersonnel = map.get("isPersonnel").toString();// 身份标识;
			if ("admin".equals(isPersonnel)) {// 管理员身份;
				deptlist = (ArrayList) deptbo.getNextLevecCompDW(node, "0", "",
						isPartner);
			} else if ("y".equals(isPersonnel)) {// 代维人员
				String deptid = map.get("deptMagId").toString();
				// 当控制显示级别的标志为true,同时有控制的showPartnerLevelType时才不会根据实际人员的deptid来控制显示的级别
				if (!("true".equals(showLevelControl) && !""
						.equals(showPartnerLevelType))) {
					if (deptid.length() == PartnerPrivUtils
							.getProvinceDeptLength()) {
						showPartnerLevelType = "1|2|3|4";// 4个级别都可以勾选
					} else if (deptid.length() == PartnerPrivUtils
							.getCityDeptLength()) {
						showPartnerLevelType = "2|3|4";// 3个级别可以勾选
					} else if (deptid.length() == PartnerPrivUtils
							.getCountyDeptLength()) {
						showPartnerLevelType = "3|4";// 2个级别可以勾选
					} else if (deptid.length() == PartnerPrivUtils
							.getGroupDeptLength()) {
						showPartnerLevelType = "4";// 1个级别可以勾选
					}
				}
				deptlist = (ArrayList) deptbo.getNextLevecCompDW(node, "0",
						deptid,isPartner);
			} else if ("n".equals(isPersonnel)) {// 移动人员
				
				String areaId = map.get("areaId").toString();
				// 当控制显示级别的标志为true,同时有控制的showPartnerLevelType时才不会根据实际人员的areaid来控制显示的级别
				if (!("true".equals(showLevelControl) && !""
						.equals(showPartnerLevelType))) {
					if (areaId.length() == PartnerPrivUtils.AreaId_length_Province) {
						showPartnerLevelType = "1|2|3|4";// 4个级别都可以勾选
					} else if (areaId.length() == PartnerPrivUtils.AreaId_length_City) {
						showPartnerLevelType = "2|3|4";// 3个级别可以勾选
					} else if (areaId.length() == PartnerPrivUtils.AreaId_length_County) {
						showPartnerLevelType = "3|4";// 2个级别可以勾选
					}
				}
				deptlist = (ArrayList) deptbo.getNextLevecCompByAreaidDW(node,
						"0", areaId,isPartner);
				;
			}
		} catch (Exception e) {
			BocoLog.error(this, "生成部门用户树图时报错：" + e);
		}
		request.setAttribute("deptlist", deptlist);
		if (!"".equals(showPartnerLevelType)) {
			request.setAttribute("showPartnerLevelType", showPartnerLevelType);
		}
		
		return mapping.findForward("tpl-user-xtree-comp-dw");
	}
	/**
	 * 
	 * @Title: getPnrDeptWithRight
	 * @Description: 更加登录人员（admin/移动人员/代维人员）来获取部门信息
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @throws IOException
	 * @return ActionForward
	 * @throws
	 */
	public ActionForward getPnrDeptWithRight(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String node = StaticMethod.null2String(request.getParameter("node"),
				StaticVariable.ProvinceID + "");
		// showPartnerLevelType为勾选的级别:1|2|3|4,1|2|3|4看626行注释;
		// showLevelControl是否需要控制勾选的级别true/false之一,默认值为false,
		// 当为true时且showPartnerLevelType有值时才有效果,缺一个条件将根据实际情况判断
		String isPartnerTemp = request.getParameter("isPartner");
		int isPartner = -1;
		try {
			isPartner = Integer.parseInt(isPartnerTemp);
		} catch (NumberFormatException e) {
		}
		request.setAttribute("isPartner", isPartner);
		String showPartnerLevelType = StaticMethod.nullObject2String(request
				.getParameter("showPartnerLevelType"));// 用于强行控制树可选的级别
		String showLevelControl = StaticMethod.nullObject2String(
				request.getParameter("showLevelControl"), "false");// 是否需要强行控制显示的级别
		TawSystemUserRoleBo userrolebo = TawSystemUserRoleBo.getInstance();
		TawSystemDeptBo deptbo = TawSystemDeptBo.getInstance();
		String template = StaticMethod.null2String(request.getParameter("tpl"),
				"tpl-user-xtree-comp");
		List deptlist = new ArrayList();
		try {
			Map map = PartnerPrivUtils.userIsPersonnel(request);
			String isPersonnel = map.get("isPersonnel").toString();// 身份标识;
			if ("admin".equals(isPersonnel)) {// 管理员身份;
				deptlist = (ArrayList) deptbo.getNextLevecComp(node, "0", "",
						isPartner);
			} else if ("y".equals(isPersonnel)) {// 代维人员
				String deptid = map.get("deptMagId").toString();
				// 当控制显示级别的标志为true,同时有控制的showPartnerLevelType时才不会根据实际人员的deptid来控制显示的级别
				if (!("true".equals(showLevelControl) && !""
						.equals(showPartnerLevelType))) {
					if (deptid.length() == PartnerPrivUtils
							.getProvinceDeptLength()) {
						showPartnerLevelType = "1|2|3|4";// 4个级别都可以勾选
					} else if (deptid.length() == PartnerPrivUtils
							.getCityDeptLength()) {
						showPartnerLevelType = "2|3|4";// 3个级别可以勾选
					} else if (deptid.length() == PartnerPrivUtils
							.getCountyDeptLength()) {
						showPartnerLevelType = "3|4";// 2个级别可以勾选
					} else if (deptid.length() == PartnerPrivUtils
							.getGroupDeptLength()) {
						showPartnerLevelType = "4";// 1个级别可以勾选
					}
				}
				deptlist = (ArrayList) deptbo.getNextLevecComp(node, "0",
						deptid,isPartner);
			} else if ("n".equals(isPersonnel)) {// 移动人员
				PartnerDeptMgr deptmag = (PartnerDeptMgr) ApplicationContextHolder
						.getInstance().getBean("partnerDeptMgr");
				String areaId = map.get("areaId").toString();
				// 当控制显示级别的标志为true,同时有控制的showPartnerLevelType时才不会根据实际人员的areaid来控制显示的级别
				if (!("true".equals(showLevelControl) && !""
						.equals(showPartnerLevelType))) {
					if (areaId.length() == PartnerPrivUtils.AreaId_length_Province) {
						showPartnerLevelType = "1|2|3|4";// 4个级别都可以勾选
					} else if (areaId.length() == PartnerPrivUtils.AreaId_length_City) {
						showPartnerLevelType = "2|3|4";// 3个级别可以勾选
					} else if (areaId.length() == PartnerPrivUtils.AreaId_length_County) {
						showPartnerLevelType = "3|4";// 2个级别可以勾选
					}
				}
				deptlist = (ArrayList) deptbo.getNextLevecCompByAreaid(node,
						"0", areaId,isPartner);
				;
			}
		} catch (Exception e) {
			BocoLog.error(this, "生成部门用户树图时报错：" + e);
		}
		request.setAttribute("deptlist", deptlist);
		if (!"".equals(showPartnerLevelType)) {
			request.setAttribute("showPartnerLevelType", showPartnerLevelType);
		}

		return mapping.findForward("tpl-user-xtree-comp");
	}

	/**
	 * 公司和用户树
	 * 
	 * @template tpl-user-xtree-comp
	 */
	@Deprecated
	public ActionForward userFromAreaComp(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// String node = "1014";
		String node = StaticMethod.null2String(request.getParameter("node"),
				StaticVariable.ProvinceID + "");
		String selfFlag = StaticMethod.null2String(
				request.getParameter("noself"), "");
		TawSystemDeptBo bo = TawSystemDeptBo.getInstance();

		// TawSystemUserRoleBo userrolebo = TawSystemUserRoleBo.getInstance();
		String template = StaticMethod.null2String(request.getParameter("tpl"),
				"tpl-user-xtree-comp");
		// ArrayList userlist = new ArrayList();
		ArrayList deptlist = new ArrayList();
		try {
			deptlist = (ArrayList) bo.getNextArea(node, "0");
			// if (selfFlag.equals("true")) {//不包括自己的人员list
			// TawSystemSessionForm sessionform = (TawSystemSessionForm) request
			// .getSession().getAttribute("sessionform");
			// userlist = (ArrayList)
			// userrolebo.getUserByCompsNoSelf(node,sessionform.getUserid());
			// } else {
			// userlist = (ArrayList) userrolebo.getUserByCompids(node);
			// }

		} catch (Exception ex) {
			BocoLog.error(this, "生成部门用户树图时报错：" + ex);
		}
		request.setAttribute("deptlist", deptlist);
		// request.setAttribute("userlist", userlist);
		return mapping.findForward(template);
		// return mapping.findForward(template);
	}

	/**
	 * 用户树（某子角色下） 目前用于工单选择人员
	 * 
	 * @template tpl-user-xtree
	 */
	public ActionForward userFromRole(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String node = StaticMethod.null2String(request.getParameter("node"),
				StaticVariable.ProvinceID + "");
		String template = StaticMethod.null2String(request.getParameter("tpl"),
				"tpl-user-xtree");
		ITawSystemUserRefRoleManager mgr = (ITawSystemUserRefRoleManager) ApplicationContextHolder
				.getInstance().getBean("itawSystemUserRefRoleManager");

		List list = new ArrayList();
		try {
			list = (ArrayList) mgr.getUserbyroleid(node);
		} catch (Exception ex) {
			BocoLog.error(this, "生成子角色用户树图时报错：" + ex);
		}
		request.setAttribute("list", list);
		return mapping.findForward(template);
	}

	public ActionForward getSubRoleUserTree(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		return this.userFromRole(mapping, actionForm, request, response);
	}

	/**
	 * 字典树
	 * 
	 * @template tpl-dict-xtree
	 * 
	 */
	public ActionForward dict(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String node = StaticMethod.null2String(request.getParameter("node"));
		String template = StaticMethod.null2String(request.getParameter("tpl"),
				"tpl-dict-xtree");
		List list = new ArrayList();

		try {
			ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
			list = mgr.getDictSonsByDictid(node);

		} catch (Exception ex) {
			BocoLog.error(this, "生成字典树图时报错：" + ex);
		}

		request.setAttribute("list", list);
		return mapping.findForward(template);
	}
	/**
	 * 字典树--设置只能选择某一级别的字典项
	 * 
	 * @template tpl-dict-xtree-xbox
	 * 
	 */
	public ActionForward dictXbox(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String node = StaticMethod.null2String(request.getParameter("node"));		
		String markChooseLevel = StaticMethod.null2String(request.getParameter("level"));
		
		String template = StaticMethod.null2String(request.getParameter("tpl"),
				"tpl-dict-xtree-xbox");
		
		String showPartnerLevelType = StaticMethod.nullObject2String(request
				.getParameter("showPartnerLevelType"));// 用于强行控制树可选的级别
		
		String showLevelControl = StaticMethod.nullObject2String(
				request.getParameter("showLevelControl"), "false");// 是否需要强行控制显示的级别
		List list = new ArrayList();

		try {
			ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
			list = mgr.getDictSonsByDictid(node);

		} catch (Exception ex) {
			BocoLog.error(this, "生成字典树图时报错：" + ex);
		}
		
	
	    if (1==1) {
				showPartnerLevelType = markChooseLevel;
		}

		
		if (!"".equals(showPartnerLevelType)) {
			request.setAttribute("showPartnerLevelType", showPartnerLevelType);
		}

		request.setAttribute("list", list);
		return mapping.findForward(template);
	}
	/**
	 * 模板树--设置只能选择某一级别的字典项
	 * 
	 * @template tpl-maste-xtree-xbox
	 * 
	 */
	public ActionForward masteXbox(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException {
		String node = StaticMethod.null2String(request.getParameter("node"));
		String[] nodeStrs = node.split(",");
		String nodes ="";
		for(int i=0;i<nodeStrs.length;i++){
			nodes=nodes.length()<=0?("'"+nodeStrs[i]+"'"):(nodes+",'"+nodeStrs[i]+"'");
		}
		String markChooseLevel = StaticMethod.null2String(request.getParameter("level"));
		
		String template = StaticMethod.null2String(request.getParameter("tpl"),
		"tpl-maste-xtree-xbox");
		
		String showPartnerLevelType = StaticMethod.nullObject2String(request
				.getParameter("showPartnerLevelType"));// 用于强行控制树可选的级别
		
		String showLevelControl = StaticMethod.nullObject2String(
				request.getParameter("showLevelControl"), "false");// 是否需要强行控制显示的级别
		List<ListOrderedMap> resultList=null;
		try {
			
			CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) getBean("commonSpringJdbcService");
			
		
				
		    String tabFieldSql = "select cy.copy_no,cy.copy_name from maste_copy_scene cy where cy.scene_no in ("+nodes+") order by cy.scene_no,cy.seq";
		    resultList = jdbcService.queryForList(tabFieldSql);			
				
				
			
		} catch (Exception ex) {
			BocoLog.error(this, "生成字典树图时报错：" + ex);
		}
		
		
		if (1==1) {
			showPartnerLevelType = markChooseLevel;
		}
		
		
		if (!"".equals(showPartnerLevelType)) {
			request.setAttribute("showPartnerLevelType", showPartnerLevelType);
		}
		
		request.setAttribute("list", resultList);
		return mapping.findForward(template);
	}
	
	/**
	 * 
	 * 场景模板---加载其他费用（手工填写）子场景的树
	 * 用在工单处理环节和抽查环节
	 * 
 	 * @author 你的名字
 	 * @title: masteXbox
 	 * @date Oct 19, 2015 9:01:24 AM
 	 * @param mapping
 	 * @param actionForm
 	 * @param request
 	 * @param response
 	 * @return
 	 * @throws IOException ActionForward
	 */
	public ActionForward loadOtherCostsSubScene(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
	throws IOException {
		String processInstanceId = StaticMethod.null2String(request.getParameter("processInstanceId"));//工单号
		String node = StaticMethod.null2String(request.getParameter("mainSceneId"));//主场景id
		String[] nodeStrs = node.split(",");
		String nodes ="";
		for(int i=0;i<nodeStrs.length;i++){
			nodes=nodes.length()<=0?("'"+nodeStrs[i]+"'"):(nodes+",'"+nodeStrs[i]+"'");
		}
		
		String linkType = StaticMethod.null2String(request.getParameter("linkType"));//场景类型：新建派单；施工队处理；抽查
		
		String markChooseLevel = StaticMethod.null2String(request.getParameter("level"));//树的级别
		
		String template = StaticMethod.null2String(request.getParameter("tpl"),
		"tpl-maste-xtree-xbox");
		
		String showPartnerLevelType = StaticMethod.nullObject2String(request
				.getParameter("showPartnerLevelType"));// 用于强行控制树可选的级别
		
		String showLevelControl = StaticMethod.nullObject2String(
				request.getParameter("showLevelControl"), "false");// 是否需要强行控制显示的级别
		List<ListOrderedMap> resultList=null;
		try {
			ISceneTemplateService sceneTemplateService = (ISceneTemplateService)getBean("sceneTemplateService");
			Map<String,String> param=new HashMap<String,String>();
			param.put("mainSceneIds", nodes);
			Map<String, Object> resultMap = sceneTemplateService.loadNotChosenOtherCostsSubScene(processInstanceId, linkType, param);
			resultList =(List)resultMap.get("resultList");
		} catch (Exception ex) {
			BocoLog.error(this, "生成字典树图时报错：" + ex);
		}
		
		
		if (1==1) {
			showPartnerLevelType = markChooseLevel;
		}
		
		
		if (!"".equals(showPartnerLevelType)) {
			request.setAttribute("showPartnerLevelType", showPartnerLevelType);
		}
		
		request.setAttribute("list", resultList);
		return mapping.findForward(template);
	}
	

	/**
	 * 地域树
	 * 
	 * @template tpl-area-xtree
	 */
	public ActionForward area(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		String node = StaticMethod.null2String(request.getParameter("node"),
				StaticVariable.ProvinceID + "");
		String template = StaticMethod.null2String(request.getParameter("tpl"),
				"tpl-area-xtree");
		ArrayList list = new ArrayList();

		// modify by lvzhongqian,增加最多显示多少层控制,<=0表示无限制
		Integer maxLevel = 0;
		try {
			maxLevel = Integer.valueOf("".equals(request
					.getParameter("areaMaxLevel")) ? "0" : request
					.getParameter("areaMaxLevel"));
		} catch (Exception e) {
			maxLevel = 0;
			// TODO: handle exception
		}

		System.out.println("========area node:" + node + ",maxLevel:"
				+ maxLevel);

		try {
			ITawSystemAreaManager mgr = (ITawSystemAreaManager) ApplicationContextHolder
					.getInstance().getBean("ItawSystemAreaManager");

			if (maxLevel <= 0) {// <=0表示无限制
				list = (ArrayList) mgr.getSonAreaByAreaId(node);
			} else {

				Integer currLevel = mgr.getAreaByAreaId(node).getOrdercode();
				if (currLevel >= maxLevel) {
					list = new ArrayList(); // 传递一个size=0的list
				} else {
					list = (ArrayList) mgr.getSonAreaByAreaId(node);
				}
			}

		} catch (Exception ex) {
			BocoLog.error(this, "生成地域树图时报错：" + ex);
		}

		request.setAttribute("list", list);
		return mapping.findForward(template);
	}

	public ActionForward areaTree(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		return this.area(mapping, actionForm, request, response);
	}

	/**
	 * 部门岗位树
	 * 
	 * @template tpl-post-xtree-fromdept
	 */
	public ActionForward postFromDept(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String id = StaticMethod.null2String(request.getParameter("node"),
				StaticVariable.ProvinceID + "");
		String template = StaticMethod.null2String(request.getParameter("tpl"),
				"tpl-post-xtree-fromdept");
		ArrayList deptlist = new ArrayList();
		ArrayList postlist = new ArrayList();
		try {
			TawSystemDeptBo deptbo = TawSystemDeptBo.getInstance();
			deptlist = (ArrayList) deptbo.getNextLevecDepts(id, "0");
			ITawSystemPostManager mgr = (ITawSystemPostManager) ApplicationContextHolder
					.getInstance().getBean("ItawSystemPostManager");
			postlist = (ArrayList) mgr.getPostsByDeptId(id);
		} catch (Exception ex) {
			BocoLog.error(this, "生成部门岗位树图时报错：" + ex);
		}
		request.setAttribute("deptlist", deptlist);
		request.setAttribute("postlist", postlist);
		return mapping.findForward(template);
	}

	/**
	 * 部门子角色树，根据部门ID、大角色ID和流程ID获取子角色
	 * 
	 * @paramter systemId 流程ID
	 * @paramter roleId 大角色ID
	 * @template tpl-subrole-xtree-fromdept
	 */
	public ActionForward subroleFromDept(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String deptid = StaticMethod.null2String(request.getParameter("node"),
				StaticVariable.ProvinceID + "");
		String systemId = StaticMethod.null2String(request
				.getParameter("systemId"));
		String roleId = StaticMethod
				.null2String(request.getParameter("roleId"));
		String template = StaticMethod.null2String(request.getParameter("tpl"),
				"tpl-subrole-xtree-fromdept");
		ArrayList deptlist = new ArrayList();
		ArrayList rolelist = new ArrayList();
		try {
			TawSystemDeptBo deptbo = TawSystemDeptBo.getInstance();
			deptlist = (ArrayList) deptbo.getNextLevecDepts(deptid, "0");
			ITawSystemSubRoleManager mgr = (ITawSystemSubRoleManager) ApplicationContextHolder
					.getInstance().getBean("ItawSystemSubRoleManager");
			rolelist = (ArrayList) mgr.getSubRolesByDeptId(deptid, systemId,
					roleId);
		} catch (Exception ex) {
			BocoLog.error(this, "生成部门子角色树图时报错：" + ex);
		}
		request.setAttribute("deptlist", deptlist);
		request.setAttribute("rolelist", rolelist);
		return mapping.findForward(template);
	}

	/**
	 * 部门、子角色、用户树
	 * 
	 * @template tpl-dept-xtree-subroleuser
	 */
	public ActionForward getDeptSubRoleUserTree(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String deptid = StaticMethod.null2String(request.getParameter("node"),
				StaticVariable.ProvinceID + "");
		String template = StaticMethod.null2String(request.getParameter("tpl"),
				"tpl-dept-xtree-subroleuser");
		ArrayList userlist = new ArrayList();
		ArrayList subrolelist = new ArrayList();
		ArrayList deptlist = new ArrayList();
		try {
			TawSystemDeptBo deptbo = TawSystemDeptBo.getInstance();
			deptlist = (ArrayList) deptbo.getNextLevecDepts(deptid, "0");
			ITawSystemSubRoleManager mgr = (ITawSystemSubRoleManager) ApplicationContextHolder
					.getInstance().getBean("ItawSystemSubRoleManager");
			subrolelist = (ArrayList) mgr.getSubRolesByDeptId(deptid);
			ITawSystemUserBo sysuserbo = (ITawSystemUserBo) ApplicationContextHolder
					.getInstance().getBean("iTawSystemUserBo");
			userlist = (ArrayList) sysuserbo.getUserBydeptids(deptid);
		} catch (Exception ex) {
			BocoLog.error(this, "生成部门子角色用户树图时报错：" + ex);
		}
		request.setAttribute("deptlist", deptlist);
		request.setAttribute("subrolelist", subrolelist);
		request.setAttribute("userlist", userlist);
		return mapping.findForward(template);
	}

	/**
	 * 机房组织树
	 * 
	 * @template tpl-cptroom-xtree
	 */
	public ActionForward cptroom(ActionMapping mapping, ActionForm actionForm,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		String id = StaticMethod.null2String(request.getParameter("node"),
				StaticVariable.ProvinceID + "");
		String template = StaticMethod.null2String(request.getParameter("tpl"),
				"tpl-cptroom-xtree");
		ArrayList list = new ArrayList();
		TawSystemCptroomBo cptroombo = TawSystemCptroomBo.getInstance();

		try {
			list = (ArrayList) cptroombo.getNextLevelCptrooms(id, "0");
		} catch (Exception ex) {
			BocoLog.error(this, "生成机房组织树图时报错：" + ex);
		}
		request.setAttribute("list", list);
		return mapping.findForward(template);

	}

	public ActionForward getCptroomTree(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return this.cptroom(mapping, actionForm, request, response);

	}

	/**
	 * 创建atom源
	 */
	public ActionForward getAtomTreeLists(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String node = StaticMethod.null2String(request.getParameter("node"),
				StaticVariable.ProvinceID + "");

		List list = new ArrayList();
		// 获取当前用户
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		boolean _bIsAdmin = "admin".equals(sessionform.getUserid());

		try {
			TawSystemPrivOperation tawSystemPrivOperation = null;
			if (_bIsAdmin) {
				ITawSystemPrivOperationManager operationMgr = (ITawSystemPrivOperationManager) getBean("ItawSystemPrivOperationManager");
				list = (ArrayList) operationMgr.getAllEnableSubObjects(node);
			} else {
				list = PrivMgrLocator
						.getPrivMgr()
						.listOperationAll(
								sessionform.getUserid(),
								sessionform.getDeptid(),
								sessionform.getRolelist(),
								PrivConstants.LIST_OPERATION_TYPE_MOUDLE_FUNCTION,
								node);
			}

			String path = request.getScheme() + "://" + request.getLocalAddr()
					+ ":" + request.getServerPort() + request.getContextPath()
					+ "/";

			// 创建ATOM源
			Factory factory = Abdera.getNewFactory();
			Feed feed = factory.newFeed();
			// 分页
			for (int i = 0; i < list.size(); i++) {
				tawSystemPrivOperation = (TawSystemPrivOperation) list.get(i);

				Entry entry = feed.insertEntry();
				entry.setId(tawSystemPrivOperation.getCode());
				entry.setTitle(tawSystemPrivOperation.getName());
				entry.setContent(path + tawSystemPrivOperation.getUrl());
				entry.setSummary(tawSystemPrivOperation.getParentcode());
				entry.setLanguage(tawSystemPrivOperation.getOrderby());
				entry.setDraft(tawSystemPrivOperation.getIsApp() == "1" ? true
						: false);

			}
			// 显示的总条数
			feed.setText(String.valueOf(list.size()));

			OutputStream os = response.getOutputStream();
			PrintStream ps = new PrintStream(os);
			feed.getDocument().writeTo(ps);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ActionForward auditUserList(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String priv = "/WORKBENCH/INFOPUB/PERMISSION/THREADAUDIT";
		String node = StaticMethod.null2String(request.getParameter("node"),
				StaticVariable.ProvinceID + "");
		String selfFlag = StaticMethod.null2String(
				request.getParameter("noself"), "");
		TawSystemDeptBo deptbo = TawSystemDeptBo.getInstance();
		TawSystemUserRoleBo userrolebo = TawSystemUserRoleBo.getInstance();
		String template = StaticMethod.null2String(request.getParameter("tpl"),
				"tpl-user-xtree-fromdept");
		ArrayList userlist = new ArrayList();
		ArrayList deptlist = new ArrayList();
		ArrayList backList = new ArrayList();
		try {
			deptlist = (ArrayList) deptbo.getNextLevecDepts(node, "0");
			if (selfFlag.equals("true")) {// 不包括自己的人员list
				TawSystemSessionForm sessionform = (TawSystemSessionForm) request
						.getSession().getAttribute("sessionform");
				userlist = (ArrayList) userrolebo.getUserBydeptidsNoSelf(node,
						sessionform.getUserid());
				int len = userlist.size();
				for (int i = 0; i < len; i++) {
					TawSystemUser tawSystemUser = (TawSystemUser) userlist
							.get(i);
					boolean b = PrivMgrLocator.getPrivMgr().hasPriv(
							tawSystemUser.getId(), priv);
					if (b) {
						backList.add(tawSystemUser);
					}
				}
			} else {
				userlist = (ArrayList) userrolebo.getUserBydeptids(node);
				int len = userlist.size();
				for (int i = 0; i < len; i++) {
					TawSystemUser tawSystemUser = (TawSystemUser) userlist
							.get(i);
					boolean b = PrivMgrLocator.getPrivMgr().hasPriv(
							tawSystemUser.getUserid(), priv);
					if (b) {
						backList.add(tawSystemUser);
					}
				}
			}
		} catch (Exception ex) {
			BocoLog.error(this, "生成部门用户树图时报错：" + ex);
		}
		request.setAttribute("deptlist", deptlist);
		request.setAttribute("userlist", backList);
		return mapping.findForward(template);
	}

	/**
	 * 从流程平台移植过来,主要实现流程子角色树
	 */
	public void getAllWorkflow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONArray jsonRoot = new JSONArray();

		ITawSystemRoleRefWorkflowManager workflow = (ITawSystemRoleRefWorkflowManager) ApplicationContextHolder
				.getInstance().getBean("ItawSystemRoleRefWorkflowManager");
		List workflows = workflow.getTawSystemWorkflows();

		for (Iterator it = workflows.iterator(); it.hasNext();) {
			TawSystemWorkflow systemWorkflow = (TawSystemWorkflow) it.next();
			String workflowId = systemWorkflow.getFlowId();
			String workflowName = systemWorkflow.getRemark();
			JSONObject j = new JSONObject();
			j.put(UIConstants.JSON_ID, workflowId);
			j.put(UIConstants.JSON_TEXT, workflowName);
			j.put(UIConstants.JSON_NODETYPE, "workflow");
			jsonRoot.put(j);
		}
		JSONUtil.print(response, jsonRoot.toString());
	}
	/**
	 * 
	 * @Title: getPnrDeptNumber
	 * @Description: 更加登录人员（admin/移动人员/代维人员）来获取部门编码信息-运维人员管理
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @throws IOException
	 * @return ActionForward
	 * @throws
	 */
	public ActionForward getPnrDeptNumber(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String node = StaticMethod.null2String(request.getParameter("node"),
				StaticVariable.ProvinceID + "");
		// showPartnerLevelType为勾选的级别:1|2|3|4,1|2|3|4看626行注释;
		// showLevelControl是否需要控制勾选的级别true/false之一,默认值为false,
		// 当为true时且showPartnerLevelType有值时才有效果,缺一个条件将根据实际情况判断
		String isPartnerTemp = request.getParameter("isPartner");
		int isPartner = -1;
		try {
			isPartner = Integer.parseInt(isPartnerTemp);
		} catch (NumberFormatException e) {
		}
		request.setAttribute("isPartner", isPartner);
		String showPartnerLevelType = StaticMethod.nullObject2String(request
				.getParameter("showPartnerLevelType"));// 用于强行控制树可选的级别
		String showLevelControl = StaticMethod.nullObject2String(
				request.getParameter("showLevelControl"), "false");// 是否需要强行控制显示的级别
		TawSystemUserRoleBo userrolebo = TawSystemUserRoleBo.getInstance();
		TawSystemDeptBo deptbo = TawSystemDeptBo.getInstance();
		String template = StaticMethod.null2String(request.getParameter("tpl"),
		"tpl-user-dept-number-xtree");
		List deptlist = new ArrayList();
		try {
			Map map = PartnerPrivUtils.userIsPersonnel(request);
			String isPersonnel = map.get("isPersonnel").toString();// 身份标识;
			if ("admin".equals(isPersonnel)) {// 管理员身份;
				deptlist = (ArrayList) deptbo.getNextLevecCompDW(node, "0", "",
						isPartner);
			} else if ("y".equals(isPersonnel)) {// 代维人员
				String deptid = map.get("deptMagId").toString();
				// 当控制显示级别的标志为true,同时有控制的showPartnerLevelType时才不会根据实际人员的deptid来控制显示的级别
				if (!("true".equals(showLevelControl) && !""
						.equals(showPartnerLevelType))) {
					if (deptid.length() == PartnerPrivUtils
							.getProvinceDeptLength()) {
						showPartnerLevelType = "1|2|3|4";// 4个级别都可以勾选
					} else if (deptid.length() == PartnerPrivUtils
							.getCityDeptLength()) {
						showPartnerLevelType = "2|3|4";// 3个级别可以勾选
					} else if (deptid.length() == PartnerPrivUtils
							.getCountyDeptLength()) {
						showPartnerLevelType = "3|4";// 2个级别可以勾选
					} else if (deptid.length() == PartnerPrivUtils
							.getGroupDeptLength()) {
						showPartnerLevelType = "4";// 1个级别可以勾选
					}
				}
				deptlist = (ArrayList) deptbo.getNextLevecCompDW(node, "0",
						deptid,isPartner);
			} else if ("n".equals(isPersonnel)) {// 移动人员
				
				String areaId = map.get("areaId").toString();
				// 当控制显示级别的标志为true,同时有控制的showPartnerLevelType时才不会根据实际人员的areaid来控制显示的级别
				if (!("true".equals(showLevelControl) && !""
						.equals(showPartnerLevelType))) {
					if (areaId.length() == PartnerPrivUtils.AreaId_length_Province) {
						showPartnerLevelType = "1|2|3|4";// 4个级别都可以勾选
					} else if (areaId.length() == PartnerPrivUtils.AreaId_length_City) {
						showPartnerLevelType = "2|3|4";// 3个级别可以勾选
					} else if (areaId.length() == PartnerPrivUtils.AreaId_length_County) {
						showPartnerLevelType = "3|4";// 2个级别可以勾选
					}
				}
				deptlist = (ArrayList) deptbo.getNextLevecCompByAreaidDW(node,
						"0", areaId,isPartner);
				;
			}
		} catch (Exception e) {
			BocoLog.error(this, "生成部门编码树图时报错：" + e);
		}
		request.setAttribute("deptlist", deptlist);
		if (!"".equals(showPartnerLevelType)) {
			request.setAttribute("showPartnerLevelType", showPartnerLevelType);
		}
		
		return mapping.findForward("tpl-user-dept-number-xtree");
	}
}
