package com.boco.eoms.role_tease.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.role.model.TawSystemRole;
import com.boco.eoms.commons.system.role.model.TawSystemSubRole;
import com.boco.eoms.commons.system.role.service.ITawSystemRoleManager;
import com.boco.eoms.commons.system.role.service.ITawSystemSubRoleManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.bo.impl.TawSystemUserRoleBo;
import com.boco.eoms.commons.ui.util.UIConstants;
import com.boco.eoms.roleWorkflow.mgr.ITawSystemRoleWorkflowManager;
import com.boco.eoms.roleWorkflow.model.TawSystemRoleWorkflow;
import com.boco.eoms.role_tease.mgr.TawSystemRoleDescMgr;
import com.boco.eoms.role_tease.mgr.TawSystemRoleteaseMgr;
import com.boco.eoms.role_tease.model.TawSystemRoleDesc;
import com.boco.eoms.role_tease.model.TawSystemRoletease;
import com.boco.eoms.role_tease.util.TawSystemRoleteaseConstants;
import com.boco.eoms.role_tease.webapp.form.TawSystemRoleteaseForm;

/**
 * <p>
 * Title:角色梳理
 * </p>
 * <p>
 * Description:角色梳理
 * </p>
 * <p>
 * Tue Aug 04 11:38:53 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() fengshaohong
 * @moudle.getVersion() 3.5
 * 
 */
public final class TawSystemRoleteaseAction extends BaseAction {

	/**
	 * 未指定方法时默认调用的方法
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return search(mapping, form, request, response);
	}

	/**
	 * 新增角色梳理
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("edit");
	}

	/**
	 * 修改角色梳理
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemRoleteaseMgr tawSystemRoleteaseMgr = (TawSystemRoleteaseMgr) getBean("tawSystemRoleteaseMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		TawSystemRoletease tawSystemRoletease = tawSystemRoleteaseMgr
				.getTawSystemRoletease(id);
		TawSystemRoleteaseForm tawSystemRoleteaseForm = (TawSystemRoleteaseForm) convert(tawSystemRoletease);
		updateFormBean(mapping, request, tawSystemRoleteaseForm);
		return mapping.findForward("edit");
	}

	/**
	 * 保存角色梳理
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemRoleteaseMgr tawSystemRoleteaseMgr = (TawSystemRoleteaseMgr) getBean("tawSystemRoleteaseMgr");
		TawSystemRoleteaseForm tawSystemRoleteaseForm = (TawSystemRoleteaseForm) form;
		boolean isNew = (null == tawSystemRoleteaseForm.getId() || ""
				.equals(tawSystemRoleteaseForm.getId()));
		TawSystemRoletease tawSystemRoletease = (TawSystemRoletease) convert(tawSystemRoleteaseForm);
		if (isNew) {
			tawSystemRoleteaseMgr.saveTawSystemRoletease(tawSystemRoletease);
		} else {
			tawSystemRoleteaseMgr.saveTawSystemRoletease(tawSystemRoletease);
		}
		return search(mapping, tawSystemRoleteaseForm, request, response);
	}

	/**
	 * 删除角色梳理
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward remove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemRoleteaseMgr tawSystemRoleteaseMgr = (TawSystemRoleteaseMgr) getBean("tawSystemRoleteaseMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		tawSystemRoleteaseMgr.removeTawSystemRoletease(id);
		return search(mapping, form, request, response);
	}

	/**
	 * 分页显示角色梳理列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				TawSystemRoleteaseConstants.TAWSYSTEMROLETEASE_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		TawSystemRoleteaseMgr tawSystemRoleteaseMgr = (TawSystemRoleteaseMgr) getBean("tawSystemRoleteaseMgr");
		Map map = (Map) tawSystemRoleteaseMgr.getTawSystemRoleteases(pageIndex,
				pageSize, "");
		List list = (List) map.get("result");
		request.setAttribute(
				TawSystemRoleteaseConstants.TAWSYSTEMROLETEASE_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}

	public ActionForward selfDeptView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String tempid = "";
		String orgs = "[]";
		ITawSystemRoleWorkflowManager workflowService = (ITawSystemRoleWorkflowManager) getBean("ITawSystemRoleWorkflowManager");
		ITawSystemRoleManager roleMgr = (ITawSystemRoleManager) getBean("ItawSystemRoleManager");
		List workflows = workflowService.getTawSystemWorkflows();
		List workflowMaplist = new ArrayList();
		List roleList = new ArrayList();
		for (int i = 0; i < workflows.size(); i++) {
			HashMap map = new HashMap();
			TawSystemRoleWorkflow systemWorkflow = (TawSystemRoleWorkflow) workflows
					.get(i);
			String workflowId = systemWorkflow.getFlowId();
			long workflowFlag = StaticMethod.null2Long(workflowId);
			List role = (ArrayList) roleMgr
					.getFlwRolesByWorkflowFlag((int) workflowFlag);
			map.put("count", new Integer(role.size()));
			map.put("workflow", systemWorkflow.getRemark());
			workflowMaplist.add(map);
			roleList.add(role);
		}
		String deptId = request.getParameter("deptId");
		if (deptId == null || deptId.equals("[]") || "".equals(deptId)) {
			TawSystemSessionForm sessionForm = this.getUser(request);
			deptId = sessionForm.getDeptid();
		} else {
			orgs = deptId;
			JSONArray jsonOrgs = JSONArray.fromString(deptId);

			for (Iterator it = jsonOrgs.iterator(); it.hasNext();) {
				JSONObject org = (JSONObject) it.next();
				tempid = org.getString(UIConstants.JSON_ID);
			}
			deptId = tempid;
		}
		ArrayList userList = new ArrayList();
		TawSystemUserRoleBo userrolebo = TawSystemUserRoleBo.getInstance();
		userList = (ArrayList) userrolebo.getUserBydeptids(deptId);
		TawSystemRoleteaseMgr tawSystemRoleteaseMgr = (TawSystemRoleteaseMgr) getBean("tawSystemRoleteaseMgr");
		List userRoleList = new ArrayList();
		for (int n = 0; n < userList.size(); n++) {
			List subRolesList = tawSystemRoleteaseMgr
					.getRoleidByuserid(((TawSystemUser) userList.get(n))
							.getUserid());
			List rolesList = tawSystemRoleteaseMgr
					.getRoleidsBysubRoleIds(subRolesList);
			for (int m = 0; m < rolesList.size(); m++) {
				userRoleList.add(((TawSystemUser) userList.get(n)).getUserid()
						+ rolesList.get(m));
			}
		}
		request.setAttribute("orgs", orgs);
		request.setAttribute("deptId", deptId);
		request.setAttribute("userRoleList", userRoleList);
		request.setAttribute("userList", userList);
		request.setAttribute("workflowMaplist", workflowMaplist);
		request.setAttribute("roleList", roleList);
		return mapping.findForward("deptView");
	}

	public ActionForward selfUserView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String orgs = "[]";
		String tempid = "";
		int count = 0;
	//	ITawSystemWorkflowManager workflowService = (ITawSystemWorkflowManager) getBean("ITawSystemWorkflowManager");
		ITawSystemRoleManager roleMgr = (ITawSystemRoleManager) getBean("ItawSystemRoleManager");
		ITawSystemSubRoleManager subRoleMgr = (ITawSystemSubRoleManager) getBean("ItawSystemSubRoleManager");
		TawSystemRoleteaseMgr tawSystemRoleteaseMgr = (TawSystemRoleteaseMgr) getBean("tawSystemRoleteaseMgr");
		TawSystemRoleDescMgr tawSystemRoleDescMgr = (TawSystemRoleDescMgr) getBean("tawSystemRoleDescMgr");
		
		List tawSystemRoleteaseFormList = new ArrayList();
		
		String userId = request.getParameter("userId");
		if (userId == null || userId.equals("[]") || "".equals(userId)) {
			TawSystemSessionForm sessionForm = this.getUser(request);
			userId = sessionForm.getUserid();
		} else {
			orgs = userId;
			JSONArray jsonOrgs = JSONArray.fromString(userId);

			for (Iterator it = jsonOrgs.iterator(); it.hasNext();) {
				JSONObject org = (JSONObject) it.next();
				tempid = org.getString(UIConstants.JSON_ID);
			}
			userId = tempid;
		}
		List subRolesList = tawSystemRoleteaseMgr.getRoleidByuserid(userId);
		for(int i=0;i<subRolesList.size();i++){
			TawSystemSubRole tawSystemSubRole = subRoleMgr.getTawSystemSubRole((String)subRolesList.get(i));
			if(tawSystemSubRole!=null){
			long roleId = tawSystemSubRole.getRoleId();
			TawSystemRole tawSystemRole = roleMgr.getTawSystemRole(roleId);
			String workflowFlag = tawSystemRole.getWorkflowFlag()+"";
			if(!workflowFlag.equals("0")){
				TawSystemRoleWorkflow tawSystemWorkflow = tawSystemRoleteaseMgr.getTawSystemWorkflowByFlowId( workflowFlag);
				if(tawSystemWorkflow!=null&&!"".equals(tawSystemWorkflow.getId())){
					TawSystemRoleDesc tawSystemRoleDesc =tawSystemRoleDescMgr.getTawSystemRoleDescByRoleId(roleId);
					
					TawSystemRoleteaseForm tawSystemRoleteaseForm = new TawSystemRoleteaseForm();
					tawSystemRoleteaseForm.setWorkflowId(tawSystemWorkflow.getFlowId());
					tawSystemRoleteaseForm.setWorkflowName(tawSystemWorkflow
							.getRemark());
					tawSystemRoleteaseForm.setRoleId(roleId + "");
					tawSystemRoleteaseForm.setRoleName(tawSystemRole
							.getRoleName());
					tawSystemRoleteaseForm.setSubRole_id(tawSystemSubRole
							.getId());
					tawSystemRoleteaseForm.setSubRoleName(tawSystemSubRole
							.getSubRoleName());
                    if(tawSystemRoleDesc!=null){
                    	tawSystemRoleteaseForm.setWorkTemple(tawSystemRoleDesc.getTemplateName());
                    	tawSystemRoleteaseForm.setTimeLimite(tawSystemRoleDesc.getTimeLimit());
                    	tawSystemRoleteaseForm.setSkillRequire(tawSystemRoleDesc.getSkill());
                    	tawSystemRoleteaseForm.setCheckLine(tawSystemRoleDesc.getKpi());
					}
					tawSystemRoleteaseFormList.add(tawSystemRoleteaseForm);
					count++;
				}
			}
		}
		}
		//下列代码是查询全部角色的视图
/*		List tawSystemRoleteaseFormList = new ArrayList();
		int count = 0;
		List workflows = workflowService.getTawSystemWorkflows();
		for (int i = 0; i < workflows.size(); i++) {
			TawSystemWorkflow systemWorkflow = (TawSystemWorkflow) workflows
					.get(i);
			String workflowId = systemWorkflow.getFlowId();
			long workflowFlag = StaticMethod.null2Long(workflowId);
			List roleList = (ArrayList) roleMgr
					.getFlwRolesByWorkflowFlag((int) workflowFlag);
			for (int k = 0; k < roleList.size(); k++) {
				TawSystemRole tawSystemRole = (TawSystemRole) roleList.get(k);
				long roleId = tawSystemRole.getRoleId();
				List subRoleList = subRoleMgr.getTawSystemSubRoles(roleId);
				for (int m = 0; m < subRoleList.size(); m++) {
					TawSystemSubRole tawSystemSubRole = (TawSystemSubRole) subRoleList
							.get(m);
					TawSystemRoleteaseForm tawSystemRoleteaseForm = new TawSystemRoleteaseForm();
					tawSystemRoleteaseForm.setWorkflowId(workflowId);
					tawSystemRoleteaseForm.setWorkflowName(systemWorkflow
							.getRemark());
					tawSystemRoleteaseForm.setRoleId(roleId + "");
					tawSystemRoleteaseForm.setRoleName(tawSystemRole
							.getRoleName());
					tawSystemRoleteaseForm.setSubRole_id(tawSystemSubRole
							.getId());
					tawSystemRoleteaseForm.setSubRoleName(tawSystemSubRole
							.getSubRoleName());
					tawSystemRoleteaseFormList.add(tawSystemRoleteaseForm);
					count++;
				}

			}
		}
*/      request.setAttribute("orgs", orgs);
		request.setAttribute("tawSystemRoleteaseList",
				tawSystemRoleteaseFormList);
		request.setAttribute("count", new Integer(count));
		return mapping.findForward("userView");
	}
	
	
	/**
	 * 角色工具
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * xiangyibiao
	 */
	public ActionForward roletool(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ITawSystemRoleWorkflowManager workflowService = (ITawSystemRoleWorkflowManager) getBean("ITawSystemRoleWorkflowManager");
		ITawSystemRoleManager roleMgr = (ITawSystemRoleManager) getBean("ItawSystemRoleManager");
		TawSystemRoleDescMgr tawSystemRoleDescMgr = (TawSystemRoleDescMgr) getBean("tawSystemRoleDescMgr");
		TawSystemRoleteaseMgr tawSystemRoleteaseMgr = (TawSystemRoleteaseMgr) getBean("tawSystemRoleteaseMgr");

		String pageIndexName = new org.displaytag.util.ParamEncoder("tawSystemRoleToolList")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);

		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		// 每页显示条数
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		
		
		
		//默认列出所有大角色的信息
		
		List tawSystemRoleteaseFormList = new ArrayList();
		//int count = 0;
		//获取所有流程
		List workflows = workflowService.getTawSystemWorkflows();
		for (int i = 0; i < workflows.size(); i++) {
			TawSystemRoleWorkflow systemWorkflow = (TawSystemRoleWorkflow) workflows
					.get(i);
			String workflowId = systemWorkflow.getFlowId();
			long workflowFlag = StaticMethod.null2Long(workflowId);
			//根据workflowFlag获取某个流程下的流程角色
			List roleList = (ArrayList) roleMgr
					.getFlwRolesByWorkflowFlag((int) workflowFlag);
			for (int k = 0; k < roleList.size(); k++) {
				TawSystemRole tawSystemRole = (TawSystemRole) roleList.get(k);
				long roleId = tawSystemRole.getRoleId();
				//TawSystemRole tawSystemRole = roleMgr.getTawSystemRole(roleId);
				String workflowflag = tawSystemRole.getWorkflowFlag()+"";
				if(!workflowflag.equals("0")){
					TawSystemRoleWorkflow tawSystemWorkflow = tawSystemRoleteaseMgr.getTawSystemWorkflowByFlowId(workflowflag);
					if(tawSystemWorkflow!=null&&!"".equals(tawSystemWorkflow.getId())){
						TawSystemRoleDesc tawSystemRoleDesc =tawSystemRoleDescMgr.getTawSystemRoleDescByRoleId(roleId);
						
						TawSystemRoleteaseForm tawSystemRoleteaseForm = new TawSystemRoleteaseForm();
						tawSystemRoleteaseForm.setWorkflowId(tawSystemWorkflow.getFlowId());
						tawSystemRoleteaseForm.setWorkflowName(tawSystemWorkflow
								.getRemark());
						tawSystemRoleteaseForm.setRoleId(roleId + "");
						tawSystemRoleteaseForm.setRoleName(tawSystemRole
								.getRoleName());
						
	                    if(tawSystemRoleDesc!=null){
	                    	tawSystemRoleteaseForm.setWorkTemple(tawSystemRoleDesc.getTemplateName());
	                    	tawSystemRoleteaseForm.setTimeLimite(tawSystemRoleDesc.getTimeLimit());
	                    	tawSystemRoleteaseForm.setSkillRequire(tawSystemRoleDesc.getSkill());
	                    	tawSystemRoleteaseForm.setCheckLine(tawSystemRoleDesc.getKpi());
						}
						tawSystemRoleteaseFormList.add(tawSystemRoleteaseForm);
						//count++;
					}
				}
				
			}
		}
		
		int resultSize = tawSystemRoleteaseFormList.size();
		request.setAttribute("offset", new Integer(pageSize.intValue()*pageIndex.intValue()+1));//displaytag的offset是从1开始算的
		request.setAttribute("resultSize", new Integer(resultSize));
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("tawSystemRoleToolList",
				tawSystemRoleteaseFormList);
		request.getSession().setAttribute("workflows", workflows);
		
		return mapping.findForward("toolView");
	}
	
	
	/**
	 * 跳转到修改界面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * xiangyibiao
	 */
	public ActionForward roletooledit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		ITawSystemRoleManager roleMgr = (ITawSystemRoleManager) getBean("ItawSystemRoleManager");
		
		TawSystemRoleDescMgr tawSystemRoleDescMgr = (TawSystemRoleDescMgr) getBean("tawSystemRoleDescMgr");
		TawSystemRoleteaseMgr tawSystemRoleteaseMgr = (TawSystemRoleteaseMgr) getBean("tawSystemRoleteaseMgr");
		
		TawSystemRoleteaseForm tawSystemRoleteaseForm = new TawSystemRoleteaseForm();
		String roleId = request.getParameter("roleId");
		
		long roleIdLong = StaticMethod.nullObject2Long(roleId);
		//TawSystemRole tawSystemRole = roleMgr.getTawSystemRole(roleIdLong);
		TawSystemRoleDesc tawSystemRoleDesc =tawSystemRoleDescMgr.getTawSystemRoleDescByRoleId(roleIdLong);
		TawSystemRole tawSystemRole = roleMgr.getTawSystemRole(Long.parseLong(roleId));
		
		TawSystemRoleWorkflow tawSystemWorkflow = tawSystemRoleteaseMgr.getTawSystemWorkflowByFlowId(tawSystemRole.getWorkflowFlag().toString());
		
		tawSystemRoleteaseForm.setRoleName(tawSystemRole.getRoleName());
		tawSystemRoleteaseForm.setRoleId(roleId);
		tawSystemRoleteaseForm.setWorkflowName(tawSystemWorkflow.getRemark());
		tawSystemRoleteaseForm.setWorkflowId(tawSystemWorkflow.getFlowId());
		tawSystemRoleteaseForm.setWorkTemple(tawSystemRoleDesc.getTemplateName());
		tawSystemRoleteaseForm.setTimeLimite(tawSystemRoleDesc.getTimeLimit());
		tawSystemRoleteaseForm.setSkillRequire(tawSystemRoleDesc.getSkill());
		tawSystemRoleteaseForm.setCheckLine(tawSystemRoleDesc.getKpi());
		
		
		request.setAttribute("tawSystemRoleteaseForm", tawSystemRoleteaseForm);
		return mapping.findForward("toolViewForEdit");
	}
	
	
	
	
	

	/**
	 * 修改一条角色
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * xiangyibiao
	 */
	public ActionForward roletoolupdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemRoleteaseForm tform = (TawSystemRoleteaseForm)form;
		TawSystemRoleDescMgr tawSystemRoleDescMgr = (TawSystemRoleDescMgr) getBean("tawSystemRoleDescMgr");
		TawSystemRoleDesc tawSystemRoleDesc = tawSystemRoleDescMgr.getTawSystemRoleDescByRoleId(Long.parseLong(tform.getRoleId()));
		if(null!=tawSystemRoleDesc && "".equals(tawSystemRoleDesc.getId())){
			tawSystemRoleDesc.setTemplateName(tform.getWorkTemple());
			tawSystemRoleDesc.setTimeLimit(tform.getTimeLimite());
			tawSystemRoleDesc.setSkill(tform.getSkillRequire());
			tawSystemRoleDesc.setKpi(tform.getCheckLine());
			
			tawSystemRoleDescMgr.saveTawSystemRoleDesc(tawSystemRoleDesc);
						
			return mapping.findForward("success");
		}
		TawSystemRoleDesc tawSystemRoleDescNew = new TawSystemRoleDesc();
		tawSystemRoleDescNew.setRoleId(Long.parseLong(tform.getRoleId()));
		tawSystemRoleDescNew.setRoleName(tform.getRoleName());
		tawSystemRoleDescNew.setWorkflowFlag(Long.parseLong(tform.getWorkflowId()));
		tawSystemRoleDescNew.setWorkflowName(tform.getWorkflowName());
		tawSystemRoleDescNew.setTemplateName(tform.getWorkTemple());
		tawSystemRoleDescNew.setTimeLimit(tform.getTimeLimite());
		tawSystemRoleDescNew.setSkill(tform.getSkillRequire());
		tawSystemRoleDescNew.setKpi(tform.getCheckLine());
		
		tawSystemRoleDescMgr.saveTawSystemRoleDesc(tawSystemRoleDescNew);
				
		return mapping.findForward("success");
	}
	
	
	/**
	 * 根据流程获取角色集合
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * xiangyibiao
	 */
	public ActionForward roletoolGetRoleList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String workflowid = request.getParameter("workflowid");
		//ITawSystemRoleWorkflowManager workflowService = (ITawSystemRoleWorkflowManager) getBean("ITawSystemRoleWorkflowManager");
		ITawSystemRoleManager roleMgr = (ITawSystemRoleManager) getBean("ItawSystemRoleManager");
		TawSystemRoleDescMgr tawSystemRoleDescMgr = (TawSystemRoleDescMgr) getBean("tawSystemRoleDescMgr");
		TawSystemRoleteaseMgr tawSystemRoleteaseMgr = (TawSystemRoleteaseMgr) getBean("tawSystemRoleteaseMgr");
		
		String pageIndexName = new org.displaytag.util.ParamEncoder("tawSystemRoleToolList")
		.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);

		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		// 每页显示条数
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
			.getPageSize();

		
		List tawSystemRoleteaseFormList = new ArrayList();
		int count = 0;
	
		long workflowFlag = StaticMethod.null2Long(workflowid);
		//根据workflowFlag获取某个流程下的流程角色
		List roleList = (ArrayList) roleMgr
				.getFlwRolesByWorkflowFlag((int) workflowFlag);
		for (int k = 0; k < roleList.size(); k++) {
			TawSystemRole tawSystemRole = (TawSystemRole) roleList.get(k);
			long roleId = tawSystemRole.getRoleId();
			//TawSystemRole tawSystemRole = roleMgr.getTawSystemRole(roleId);
			String workflowflag = tawSystemRole.getWorkflowFlag()+"";
			if(!workflowflag.equals("0")){
				TawSystemRoleWorkflow tawSystemWorkflow = tawSystemRoleteaseMgr.getTawSystemWorkflowByFlowId(workflowflag);
				if(tawSystemWorkflow!=null&&!"".equals(tawSystemWorkflow.getId())){
					TawSystemRoleDesc tawSystemRoleDesc =tawSystemRoleDescMgr.getTawSystemRoleDescByRoleId(roleId);
					
					TawSystemRoleteaseForm tawSystemRoleteaseForm = new TawSystemRoleteaseForm();
					tawSystemRoleteaseForm.setWorkflowId(tawSystemWorkflow.getFlowId());
					tawSystemRoleteaseForm.setWorkflowName(tawSystemWorkflow
							.getRemark());
					tawSystemRoleteaseForm.setRoleId(roleId + "");
					tawSystemRoleteaseForm.setRoleName(tawSystemRole
							.getRoleName());
					
                    if(tawSystemRoleDesc!=null){
                    	tawSystemRoleteaseForm.setWorkTemple(tawSystemRoleDesc.getTemplateName());
                    	tawSystemRoleteaseForm.setTimeLimite(tawSystemRoleDesc.getTimeLimit());
                    	tawSystemRoleteaseForm.setSkillRequire(tawSystemRoleDesc.getSkill());
                    	tawSystemRoleteaseForm.setCheckLine(tawSystemRoleDesc.getKpi());
					}
					tawSystemRoleteaseFormList.add(tawSystemRoleteaseForm);
					count++;
				}
			}
			
		}
		
		request.setAttribute("tawSystemRoleToolList",
				tawSystemRoleteaseFormList);
		int resultSize = tawSystemRoleteaseFormList.size();
		request.setAttribute("offset", new Integer(pageSize.intValue()*pageIndex.intValue()+1));//displaytag的offset是从1开始算的
		request.setAttribute("resultSize", new Integer(resultSize));
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("tawSystemRoleToolList",
				tawSystemRoleteaseFormList);
		return mapping.findForward("toolView");
	}
	/**
	 * 分页显示角色梳理列表，支持Atom方式接入Portal
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * 
	 * public ActionForward search4Atom(ActionMapping mapping, ActionForm form,
	 * HttpServletRequest request, HttpServletResponse response) throws
	 * Exception { try { // --------------用于分页，得到当前页号------------- final Integer
	 * pageIndex = new Integer(request .getParameter("pageIndex")); final
	 * Integer pageSize = new Integer(request .getParameter("pageSize"));
	 * TawSystemRoleteaseMgr tawSystemRoleteaseMgr = (TawSystemRoleteaseMgr)
	 * getBean("tawSystemRoleteaseMgr"); Map map = (Map)
	 * tawSystemRoleteaseMgr.getTawSystemRoleteases(pageIndex, pageSize, "");
	 * List list = (List) map.get("result"); TawSystemRoletease
	 * tawSystemRoletease = new TawSystemRoletease();
	 * 
	 * //创建ATOM源 Factory factory = Abdera.getNewFactory(); Feed feed =
	 * factory.newFeed();
	 *  // 分页 for (int i = 0; i < list.size(); i++) { tawSystemRoletease =
	 * (TawSystemRoletease) list.get(i);
	 *  // TODO 请按照下面的实例给entry赋值 Entry entry = feed.insertEntry();
	 * entry.setTitle("<a href='" + request.getScheme() + "://" +
	 * request.getServerName() + ":" + request.getServerPort() +
	 * request.getContextPath() +
	 * "/tawSystemRoletease/tawSystemRoleteases.do?method=edit&id=" +
	 * tawSystemRoletease.getId() + "' target='_blank'>" + display name for list + "</a>");
	 * entry.setSummary(summary); entry.setContent(content);
	 * entry.setLanguage(language); entry.setText(text);
	 * entry.setRights(tights);
	 *  // 以下三项需要传入Date类型或String类型（yyyy-MM-dd）的参数 entry.setUpdated(new
	 * java.util.Date()); entry.setPublished(new java.util.Date());
	 * entry.setEdited(new java.util.Date());
	 *  // 为person的name属性赋值，entry.addAuthor可以随意赋值 Person person =
	 * entry.addAuthor(userId); person.setName(userName); }
	 *  // 每页显示条数 feed.setText(map.get("total").toString()); OutputStream os =
	 * response.getOutputStream(); PrintStream ps = new PrintStream(os);
	 * feed.getDocument().writeTo(ps); } catch (Exception e) {
	 * e.printStackTrace(); } return null; }
	 */
}