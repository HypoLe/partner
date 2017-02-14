package com.boco.eoms.partner.eva.webapp.action;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.ui.util.UIConstants;
import com.boco.eoms.partner.eva.mgr.IPnrEvaAuditInfoMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaKpiMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaTaskMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaTemplateMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaTreeMgr;
import com.boco.eoms.partner.eva.model.PnrEvaAuditInfo;
import com.boco.eoms.partner.eva.model.PnrEvaKpi;
import com.boco.eoms.partner.eva.model.PnrEvaTask;
import com.boco.eoms.partner.eva.model.PnrEvaTemplate;
import com.boco.eoms.partner.eva.model.PnrEvaTree;
import com.boco.eoms.partner.eva.util.PnrEvaDateUtil;
import com.boco.eoms.partner.eva.util.PnrEvaConstants;
import com.boco.eoms.partner.eva.util.PnrEvaStaticMethod;
import com.boco.eoms.partner.eva.webapp.form.PnrEvaTemplateForm;
import com.boco.eoms.log4j.Logger;

public final class PnrEvaTemplateAction extends BaseAction {

	/**
	 * 未指定方法
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return templateTree(mapping, form, request, response);
	}

	/**
	 * 考核模板树
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward templateTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//得到登陆人的考核操作角色,用以确定地域信息
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		Map returnStrs = PnrEvaStaticMethod.getRoleAreaByUserId(userId,PnrEvaConstants.OPERATE_TREE_CONFIG);
		String areaId = StaticMethod.nullObject2String(returnStrs.get("areaId"));
		String errMessage = StaticMethod.nullObject2String(returnStrs.get("errMessage"));
		String partnerNodeType = StaticMethod.nullObject2String(request.getParameter("partnerNodeType"));
		if(!errMessage.equals("")){
			request.setAttribute("errMessage", errMessage);
			return mapping.findForward("nopriv");
		}
		request.setAttribute("partnerNodeType", partnerNodeType);
		request.setAttribute("areaId", areaId);
		return mapping.findForward("templateTree");
	}
	
	/**
	 * 考核模板下发树
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward distributeTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("distributeTree");
	}
	
	/**
	 * 考核模板已处理树
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward dealtTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("dealtTree");
	}

	/**
	 * 考核模板已审核树
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward auditedTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("auditedTree");
	}

	/**
	 * 考核模板审核树
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward auditTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("auditTree");
	}

	/**
	 * 新建考核模板
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward newTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String parentNodeId = StaticMethod.null2String(request
				.getParameter("nodeId"));
		String areaId = StaticMethod.null2String(request
				.getParameter("areaId"));
		if (parentNodeId == null || "".equals(parentNodeId)) {
			parentNodeId = StaticMethod.null2String(request
					.getParameter("parentNodeId"));
		}
		// 复制需要的信息 add:wangsixuan 2009-2-5
		IPnrEvaTreeMgr treeMgr = (IPnrEvaTreeMgr) getBean("iPnrEvaTreeMgr");
		PnrEvaTree parentNode = treeMgr.getTreeNodeByNodeId(parentNodeId);
		PnrEvaTemplate parentTemplate = new PnrEvaTemplate();
		if(parentNode.getNodeType().equals(PnrEvaConstants.NODETYPE_TEMPLATE)){
			IPnrEvaTemplateMgr templateMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
			parentTemplate = templateMgr.getTemplate(parentNode.getObjectId());
		}
		float maxWt = treeMgr.getMaxWt(parentNodeId, areaId,PnrEvaConstants.NODETYPE_TEMPLATE);
		request.setAttribute("maxWt", String.valueOf(maxWt));
		request.setAttribute("minWt", String.valueOf(PnrEvaConstants.DEFAULT_MIN_WT));
		request.setAttribute("parentNodeId", parentNodeId);
		request.setAttribute("areaId", areaId);
		request.setAttribute("parentTemplate", parentTemplate);
		return mapping.findForward("newTemplate");
	}

	/**
	 * 修改考核模板
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PnrEvaTemplate template = new PnrEvaTemplate();
		String nodeId = StaticMethod
				.null2String(request.getParameter("nodeId"));
		String areaId = StaticMethod.null2String(request.getParameter("areaId"));
		IPnrEvaTreeMgr treeMgr = (IPnrEvaTreeMgr) getBean("iPnrEvaTreeMgr");
		if("".equals(areaId)){
			areaId = StaticMethod.nullObject2String(request.getAttribute("areaId"));	
		}
		ITawSystemAreaManager areaMgr = (ITawSystemAreaManager) ApplicationContextHolder
		.getInstance().getBean("ItawSystemAreaManager");
		TawSystemArea tawSystemArea = areaMgr.getAreaByAreaId(areaId);
		String rootAreaId = tawSystemArea.getParentAreaid(); 
		IPnrEvaTemplateMgr templateMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
		if ("".equals(nodeId)) { // 没有取到nodeId说明不是以右键菜单操作为入口
			nodeId = StaticMethod.nullObject2String(request
					.getAttribute("nodeId"));
			PnrEvaTemplateForm templateForm = (PnrEvaTemplateForm) form;
			updateFormBean(mapping, request, templateForm);
			template = (PnrEvaTemplate) convert(templateForm);
		} else {
			PnrEvaTree tplNode = treeMgr.getTreeNodeByNodeId(nodeId);
			template = templateMgr.getTemplate(tplNode.getObjectId());
			//将tree表信息取出用于修改页面的呈现
			template.setIsLock(tplNode.getIsLock());
			template.setWeight(tplNode.getWeight());
			PnrEvaTemplateForm templateForm = (PnrEvaTemplateForm) convert(template);
			updateFormBean(mapping, request, templateForm);
			float maxWt = treeMgr.getMaxWt(tplNode.getParentNodeId(), areaId,PnrEvaConstants.NODETYPE_TEMPLATE) + tplNode.getWeight();
			request.setAttribute("maxWt", String.valueOf(maxWt));
			request.setAttribute("minWt", String.valueOf(PnrEvaConstants.DEFAULT_MIN_WT));
			PnrEvaTree parentNode = treeMgr.getTreeNodeByNodeId(tplNode.getParentNodeId());
			PnrEvaTemplate parentTemplate = new PnrEvaTemplate();
			if(parentNode.getNodeType().equals(PnrEvaConstants.NODETYPE_TEMPLATE)){
				parentTemplate = templateMgr.getTemplate(parentNode.getObjectId());
			}
			request.setAttribute("parentTemplate", parentTemplate);
		}
		
		// 所属地市，目前仅为部门，可扩展为角色和用户
		IPnrEvaTaskMgr taskMgr = (IPnrEvaTaskMgr) getBean("iPnrEvaTaskMgr");
		List taskList = taskMgr.listTaskOfTpl(template.getId());
		JSONArray jsonRoot = new JSONArray();
		for (Iterator taskIt = taskList.iterator(); taskIt.hasNext();) {
			PnrEvaTask task = (PnrEvaTask) taskIt.next();
			JSONObject jitem = new JSONObject();
			jitem.put(UIConstants.JSON_ID, task.getOrgId());
			jitem.put(UIConstants.JSON_NAME, PnrEvaStaticMethod.orgId2Name(task
					.getOrgId(), task.getOrgType()));
			jsonRoot.put(jitem);
		}

		//查询此模板的审核信息
		IPnrEvaAuditInfoMgr auditInfoMgr = (IPnrEvaAuditInfoMgr) getBean("iPnrEvaAuditInfoMgr");
		List auditInfoList = auditInfoMgr.getPnrEvaAudit(template.getId());
		request.setAttribute("auditInfoList", auditInfoList);
		request.setAttribute("orgIds", jsonRoot.toString());
		request.setAttribute("parentNodeId", nodeId);
		request.setAttribute("rootAreaId", rootAreaId);
		//页面控制需要的模板对应权值
		request.getSession().setAttribute("evaTW", String.valueOf(templateMgr.getTotalWtOfTemplateByArea(nodeId,areaId)));
		return mapping.findForward("editTemplate");
	}

	/**
	 * 保存考核模板
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IPnrEvaTemplateMgr templateMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
		PnrEvaTemplate template = new PnrEvaTemplate();
		PnrEvaTemplateForm templateForm = (PnrEvaTemplateForm) form;
		String parentNodeId = StaticMethod.null2String(request
				.getParameter("parentNodeId"));
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		if (null == templateForm.getId() || "".equals(templateForm.getId())) {// 新增模板
			template = (PnrEvaTemplate) convert(templateForm);
			template.setCreator(sessionform.getUserid());
			template.setCreateTime(StaticMethod.getCurrentDateTime());
			template.setCreatorOrgId(sessionform.getDeptid());
			template.setOrgType(PnrEvaConstants.ORG_AREA); // orgType暂时作为预留字段，全部设置为area
			template.setActivated(PnrEvaConstants.TEMPLATE_NOTACTIVATED);
			template.setDeleted(PnrEvaConstants.UNDELETED);
			template.setProfessional(templateForm.getProfessional());
			
			//二期增加的属性
			
			template.setContent(templateForm.getContent());
			template.setSumType(templateForm.getSumType());
			template.setDataSource(templateForm.getDataSource());
			template.setIsImpersonal(templateForm.getIsImpersonal());
			template.setEvaluationPhase(templateForm.getEvaluationPhase());
			template.setAuditFlag(templateForm.getAuditFlag());
			template.setWeight(templateForm.getWeight());
			template.setExecuteType(templateForm.getExecuteType());
			template.setLeaf(PnrEvaConstants.NODE_LEAF);
			template.setIsLock(templateForm.getIsLock());
			template.setExecuteOrg(templateForm.getExecuteOrg());
			//根据考核层面得到执行的所有地域以添加任务
			String[] orgIds = PnrEvaStaticMethod.getAreasByexecuteType(templateForm.getExecuteType());
			
			
			
//			// 所属地市
//			String orgIds = StaticMethod.null2String(request
//					.getParameter("orgIds"));
//			if ("".equals(orgIds)) {
//				// 页面上有选择树是否选择的判断，为防止页面控制失效，此处多判断一次
//				return mapping.findForward("fail");
//			}
//			String[] ids = orgIds.split(",");
			templateMgr
					.saveTemplateWithNodeAndTask(template, parentNodeId, orgIds);
			
			
			///数据添加成功，修改上父节点的叶子节点标识，标记为不是叶子节点
			templateMgr.changeLeaf(parentNodeId);
			
			
			IPnrEvaTreeMgr treeMgr = (IPnrEvaTreeMgr) getBean("iPnrEvaTreeMgr");
			request.setAttribute("nodeId", treeMgr.getNodeByObjId(parentNodeId,
					template.getId()).getNodeId());
		} else { // 修改模板
			template = templateMgr.getTemplate(templateForm.getId());
			template.setTemplateName(templateForm.getTemplateName());
			// template.setCycle(templateForm.getCycle());
			// template.setStartTime(PnrEvaStaticMethod.getStartTimeByCycle(
			// templateForm.getCycle(), PnrEvaDateUtil
			// .getCurrentDateTime(PnrEvaConstants.DATE_FORMAT)));
			// template.setEndTime(PnrEvaStaticMethod.getEndTimeByCycle(templateForm
			// .getCycle(), PnrEvaDateUtil
			// .getCurrentDateTime(PnrEvaConstants.DATE_FORMAT)));
			//二期增加的属性
			template.setContent(templateForm.getContent());
			template.setSumType(templateForm.getSumType());
			template.setDataSource(templateForm.getDataSource());
			template.setIsImpersonal(templateForm.getIsImpersonal());
			template.setEvaluationPhase(templateForm.getEvaluationPhase());
			template.setAuditFlag(templateForm.getAuditFlag());
//			template.setLeaf(templateForm.getLeaf());
			template.setWeight(templateForm.getWeight());
			template.setExecuteType(templateForm.getExecuteType());
			template.setIsLock(templateForm.getIsLock());
			
			template.setRemark(templateForm.getRemark());
			// template.setOrgType(templateForm.getOrgType());
			template.setOrgType(PnrEvaConstants.ORG_AREA); // orgType暂时作为预留字段，全部设置为area
			template.setProfessional(templateForm.getProfessional());
			//市考核层面时评分执行者 0-各地市；1-网络中心；2-网络部
			template.setExecuteOrg(templateForm.getExecuteOrg());
			//根据考核层面得到执行的所有地域以添加任务
			String[] orgIds = PnrEvaStaticMethod.getAreasByexecuteType(templateForm.getExecuteType());
			templateMgr.updateTemplate(template, parentNodeId,request, orgIds);
			request.setAttribute("nodeId", parentNodeId);
			
		}
		form = (PnrEvaTemplateForm) convert(template);

		// 复制模板指标 add:wangsixuan 2009-2-5
		// 先找出tree中以templateId开头的nodeID集合list
		// 根据每个list对应objId在kpi表中查到相关记录，修改用户名和时间并保存
		// 根据新的kpi主键修改tree的list并保存
		String templateId = StaticMethod.null2String(request.getParameter("templateId"));
		if (templateId != null && !"".equals(templateId)) {
			IPnrEvaTreeMgr treeMgr2 = (IPnrEvaTreeMgr) getBean("iPnrEvaTreeMgr");
			IPnrEvaKpiMgr kpiMgr = (IPnrEvaKpiMgr) getBean("iPnrEvaKpiMgr");

			// 找到新增的节点ID-nodeIdNewSave
			PnrEvaTree etree = treeMgr2.getNodeByObjId(parentNodeId, template.getId());
			String nodeIdNewSave = etree.getNodeId();

			// 找到要复制的信息list
			List treeKpiList = treeMgr2.listChildNodes(templateId,
					PnrEvaConstants.NODETYPE_KPI, "");
			for (int i = 0; i < treeKpiList.size(); i++) {
				PnrEvaTree et = (PnrEvaTree) treeKpiList.get(i);
				PnrEvaKpi ek = kpiMgr.getKpi(et.getObjectId());

				// 更新指标			
				Logger logger = Logger.getLogger(this.getClass());
			
				ek.setWeight(et.getWeight());
				ek.setCreator(sessionform.getUserid());
				ek.setDeleted(PnrEvaConstants.UNDELETED);
				ek.setCreateTime(StaticMethod.getCurrentDateTime());

				PnrEvaTree evaTree = new PnrEvaTree();
				// 生成新节点ID，用nodeIdNewSave替换对应节点ID的前部分
				int nodeLength = nodeIdNewSave.length();
				String newNodeId = nodeIdNewSave
						+ et.getNodeId().substring(nodeLength);
				String newParentNodeId = nodeIdNewSave
						+ et.getParentNodeId().substring(nodeLength);
				//得到当前人的地域信息
				String areaId = StaticMethod.null2String(request.getParameter("areaId"));
				// 保存树节点
				evaTree.setNodeId(newNodeId);
				evaTree.setParentNodeId(newParentNodeId);
				evaTree.setNodeName(ek.getKpiName());
				evaTree.setNodeType(PnrEvaConstants.NODETYPE_KPI);
				evaTree.setLeaf(PnrEvaConstants.NODE_LEAF);
				evaTree.setObjectId(ek.getId());
				evaTree.setWeight(ek.getWeight());
				evaTree.setCreatArea(areaId);
				treeMgr2.saveTreeNode(evaTree);

				// 更新父节点leaf
				treeMgr2.updateParentNodeLeaf(newParentNodeId,
						PnrEvaConstants.NODE_NOTLEAF);
				logger.info("\n进入kpiMgr.saveKpiAndNode");
				kpiMgr.saveKpiAndNode(ek, newParentNodeId,PnrEvaConstants.TEMPLATE_ACTIVATED);//复制模版指标，则新建
			}
		}

		// 保存完成后返回模板编辑页面
		return mapping.findForward("success");
	}

	/**
	 * 删除考核模板（已激活则逻辑删除，仅从树图上删除；未激活则物理删除）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removeTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IPnrEvaTemplateMgr tplMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
		IPnrEvaTreeMgr treeMgr = (IPnrEvaTreeMgr) getBean("iPnrEvaTreeMgr");
		String nodeId = request.getParameter("nodeId");
		PnrEvaTree tplNode = treeMgr.getTreeNodeByNodeId(nodeId);
		PnrEvaTemplate tpl = tplMgr.getTemplate(tplNode.getObjectId());
		if (PnrEvaConstants.TEMPLATE_ACTIVATED.equals(tpl.getActivated())) {// 如果模板已激活则逻辑删除
			tplMgr.removeTplLogical(nodeId);
		} else {// 未激活则物理删除
			tplMgr.removeTplPhysical(nodeId);
		}
		return mapping.findForward("success");
	}
	
	/**
	 * 查看模板详细信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String templateId = StaticMethod.null2String(request
				.getParameter("nodeId"));
		// 如果是action内部调用
		if (null == templateId || "".equals(templateId)) {
			templateId = ((PnrEvaTemplateForm) form).getId();
		}
		IPnrEvaTemplateMgr templateMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
		PnrEvaTemplate template = templateMgr.getTemplate(templateId);
		PnrEvaTemplateForm templateForm = (PnrEvaTemplateForm) convert(template);
		updateFormBean(mapping, request, templateForm);
		return mapping.findForward("viewTemplate");
	}

	/**
	 * 模板查询页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("query");
	}

	/**
	 * 从树图中删除模板（非物理删除，仅去掉树图中的显示）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delTemplateFromTree(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		IPnrEvaTreeMgr treeMgr = (IPnrEvaTreeMgr) getBean("iPnrEvaTreeMgr");
		String nodeId = StaticMethod
				.null2String(request.getParameter("nodeId"));
		treeMgr.removeTreeNodeByNodeId(nodeId);
		return mapping.findForward("success");
	}

	/**
	 * 激活模板
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward activeTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String templateId = StaticMethod.null2String(request
				.getParameter("templateId"));
		String tplNodeId = StaticMethod.null2String(request
				.getParameter("nodeId"));
		IPnrEvaTemplateMgr templateMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
		PnrEvaTemplate tpl = templateMgr.getTemplate(templateId);
		if ("".equals(templateId) || "".equals(tplNodeId)) {
			String failInfo = "获取模板信息错误！";
			request.setAttribute("failInfo", failInfo);
		} else if (PnrEvaConstants.TEMPLATE_ACTIVATED.equals(tpl.getActivated())) {
			String failInfo = "模板已经激活！";
			request.setAttribute("failInfo", failInfo);
		} 
//去掉满分100的限制，富强提出的需求，修改王思轩 20009-2-9
//		else if (PnrEvaConstants.DEFAULT_MAX_WT.floatValue() != templateMgr
//				.getTotalWtOfTemplate(tplNodeId).floatValue()) {
//			String failInfo = "模板下属指标权重和不等于" + PnrEvaConstants.DEFAULT_MAX_WT
//					+ "，请调整后再激活模板！";
//			request.setAttribute("failInfo", failInfo);
//		} 
		else {
			templateMgr.activeTemplate(templateId, tplNodeId);
		}
		IPnrEvaTreeMgr treeMgr = (IPnrEvaTreeMgr) getBean("iPnrEvaTreeMgr");
		PnrEvaTree tplNode = treeMgr.getTreeNodeByNodeId(tplNodeId);
		request.setAttribute("areaId",tplNode.getCreatArea());//因为只有该节点建立者才能激活模板,所以这里设为模板建立者地域
		form = (PnrEvaTemplateForm) convert(tpl);
		
		// 操作完成后返回模板编辑页面
		return editTemplate(mapping, form, request, response);
	}
	
	/**
	 *	用户提交审核，新建记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveAuditInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		
		//当用户先建模板或修改模板并直接提交送审时，先做保存操作，再在审核表记录数据

			IPnrEvaTemplateMgr templateMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
			PnrEvaTemplate template = new PnrEvaTemplate();
			PnrEvaTemplateForm templateForm = (PnrEvaTemplateForm) form;
			String parentNodeId = StaticMethod.null2String(request
					.getParameter("parentNodeId"));
			if (null == templateForm.getId() || "".equals(templateForm.getId())) {// 新增模板
				template = (PnrEvaTemplate) convert(templateForm);
				template.setCreator(sessionform.getUserid());
				template.setCreateTime(StaticMethod.getCurrentDateTime());
				template.setCreatorOrgId(sessionform.getDeptid());
				template.setOrgType(PnrEvaConstants.ORG_AREA); // orgType暂时作为预留字段，全部设置为area
				template.setActivated(PnrEvaConstants.TEMPLATE_NOTACTIVATED);
				template.setDeleted(PnrEvaConstants.UNDELETED);
				template.setProfessional(templateForm.getProfessional());
				
				//二期增加的属性
				
				template.setContent(templateForm.getContent());
				template.setSumType(templateForm.getSumType());
				template.setDataSource(templateForm.getDataSource());
				template.setIsImpersonal(templateForm.getIsImpersonal());
				template.setEvaluationPhase(templateForm.getEvaluationPhase());
				//记录新模板数据，定义初始审核状态为“待审核”
				template.setAuditFlag(PnrEvaConstants.AUDIT_WAIT);
				template.setWeight(templateForm.getWeight());
				template.setExecuteType(templateForm.getExecuteType());
				template.setLeaf(PnrEvaConstants.NODE_LEAF);
				template.setIsLock(templateForm.getIsLock());
				// 所属地市
				String orgIds = StaticMethod.null2String(request
						.getParameter("orgIds"));
				if ("".equals(orgIds)) {
					// 页面上有选择树是否选择的判断，为防止页面控制失效，此处多判断一次
					return mapping.findForward("fail");
				}
				String[] ids = orgIds.split(",");
				templateMgr
						.saveTemplateWithNodeAndTask(template, parentNodeId, ids);
				
				
				///数据添加成功，修改上父节点的叶子节点标识，标记为不是叶子节点
				templateMgr.changeLeaf(parentNodeId);
				
				
				IPnrEvaTreeMgr treeMgr = (IPnrEvaTreeMgr) getBean("iPnrEvaTreeMgr");
				request.setAttribute("nodeId", treeMgr.getNodeByObjId(parentNodeId,
						template.getId()).getNodeId());
			} else { // 修改模板
				template = templateMgr.getTemplate(templateForm.getId());
				template.setTemplateName(templateForm.getTemplateName());
				//二期增加的属性
				template.setContent(templateForm.getContent());
				template.setSumType(templateForm.getSumType());
				template.setDataSource(templateForm.getDataSource());
				template.setIsImpersonal(templateForm.getIsImpersonal());
				template.setEvaluationPhase(templateForm.getEvaluationPhase());
				//记录新模板数据，定义初始审核状态为“待审核”
				template.setAuditFlag(PnrEvaConstants.AUDIT_WAIT);
				template.setWeight(templateForm.getWeight());
				template.setExecuteType(templateForm.getExecuteType());
				template.setIsLock(templateForm.getIsLock());
				
				template.setRemark(templateForm.getRemark());
				template.setOrgType(PnrEvaConstants.ORG_AREA); // orgType暂时作为预留字段，全部设置为area
				template.setProfessional(templateForm.getProfessional());
				//根据考核层面得到执行的所有地域以添加任务
				String[] orgIds = PnrEvaStaticMethod.getAreasByexecuteType(templateForm.getExecuteType());
				templateMgr.updateTemplate(template, parentNodeId,request, orgIds);
				request.setAttribute("nodeId", parentNodeId);
				
			}
			form = (PnrEvaTemplateForm) convert(template);
	
			// 复制模板指标 add:wangsixuan 2009-2-5
			// 先找出tree中以templateId开头的nodeID集合list
			// 根据每个list对应objId在kpi表中查到相关记录，修改用户名和时间并保存
			// 根据新的kpi主键修改tree的list并保存
			String templateId = StaticMethod.null2String(request.getParameter("templateId"));
			if (templateId != null && !"".equals(templateId)) {
				IPnrEvaTreeMgr treeMgr2 = (IPnrEvaTreeMgr) getBean("iPnrEvaTreeMgr");
				IPnrEvaKpiMgr kpiMgr = (IPnrEvaKpiMgr) getBean("iPnrEvaKpiMgr");
	
				// 找到新增的节点ID-nodeIdNewSave
				PnrEvaTree etree = treeMgr2.getNodeByObjId(parentNodeId, template.getId());
				String nodeIdNewSave = etree.getNodeId();
	
				// 找到要复制的信息list
				List treeKpiList = treeMgr2.listChildNodes(templateId,
						PnrEvaConstants.NODETYPE_KPI, "");
				for (int i = 0; i < treeKpiList.size(); i++) {
					PnrEvaTree et = (PnrEvaTree) treeKpiList.get(i);
					PnrEvaKpi ek = kpiMgr.getKpi(et.getObjectId());
	
					// 更新指标			
					Logger logger = Logger.getLogger(this.getClass());
				
					ek.setWeight(et.getWeight());
					ek.setCreator(sessionform.getUserid());
					ek.setDeleted(PnrEvaConstants.UNDELETED);
					ek.setCreateTime(StaticMethod.getCurrentDateTime());
	
					PnrEvaTree evaTree = new PnrEvaTree();
					// 生成新节点ID，用nodeIdNewSave替换对应节点ID的前部分
					int nodeLength = nodeIdNewSave.length();
					String newNodeId = nodeIdNewSave
							+ et.getNodeId().substring(nodeLength);
					String newParentNodeId = nodeIdNewSave
							+ et.getParentNodeId().substring(nodeLength);
					//得到当前人的地域信息
					String areaId = StaticMethod.null2String(request.getParameter("areaId"));
					// 保存树节点
					evaTree.setNodeId(newNodeId);
					evaTree.setParentNodeId(newParentNodeId);
					evaTree.setNodeName(ek.getKpiName());
					evaTree.setNodeType(PnrEvaConstants.NODETYPE_KPI);
					evaTree.setLeaf(PnrEvaConstants.NODE_LEAF);
					evaTree.setObjectId(ek.getId());
					evaTree.setWeight(ek.getWeight());
					evaTree.setCreatArea(areaId);
					treeMgr2.saveTreeNode(evaTree);
	
					// 更新父节点leaf
					treeMgr2.updateParentNodeLeaf(newParentNodeId,
							PnrEvaConstants.NODE_NOTLEAF);
					logger.info("\n进入kpiMgr.saveKpiAndNode");
					kpiMgr.saveKpiAndNode(ek, newParentNodeId,PnrEvaConstants.TEMPLATE_ACTIVATED);//复制模版指标，则新建
				}
			}
		//新建模板审核记录
			
		String userId = sessionform.getUserid();
		Map map0 = (Map) PnrEvaStaticMethod.getRoleAreaByUserId(userId,PnrEvaConstants.OPERATE_TREE_CONFIG);
		String areaId = StaticMethod.nullObject2String(map0.get("areaId"));
		
		IPnrEvaAuditInfoMgr auditInfoMgr = (IPnrEvaAuditInfoMgr) getBean("iPnrEvaAuditInfoMgr");
		String AuditTemplateId = null ;
		if(null==templateForm.getId()||"".equals(templateForm.getId())){
			//得到当前的模板id
			AuditTemplateId = StaticMethod.null2String(request.getParameter("templateId"));
		}
		else{
			AuditTemplateId = templateForm.getId();
		}
		Map map = PnrEvaStaticMethod.getSubroleByAreaId(areaId, PnrEvaConstants.OPERATE_TEMPLATE_AUDIT);
		//subRoleId指定审核人
		String subRoleId = StaticMethod.nullObject2String(map.get("subRoleId"));
		//取得不合法信息提示
		//String errMessage = StaticMethod.nullObject2String(map.get("errMessage"));
		//取出指定角色下的所有用户
		//List user = (List)map.get("errMessage");
		
		PnrEvaAuditInfo evaAuditInfo = new PnrEvaAuditInfo();
		evaAuditInfo.setTemplateId(AuditTemplateId);
		//设置系统当前时间为送审提交时间
		evaAuditInfo.setCreateTime(PnrEvaDateUtil.getSqlDate(new java.util.Date()));
		//设置审核类型表示新增
		evaAuditInfo.setAuditType(PnrEvaConstants.AUDIT_NEW);
		//指定审核者为admin
		evaAuditInfo.setAuditOrg(subRoleId);
		//指定审核者类型为person
		evaAuditInfo.setAuditOrgType(PnrEvaConstants.ORG_SUBROLE);
		//指定初始审核结果为“未送审”
		evaAuditInfo.setAuditResult(PnrEvaConstants.AUDIT_WAIT);
		// 保存完成后返回模板编辑页面
		auditInfoMgr.savePnrEvaAuditInfo(evaAuditInfo);
		
		return mapping.findForward("success");
	}
	
	/**
	 * 产看模板审核信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward auditInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PnrEvaTemplateForm templateForm = (PnrEvaTemplateForm) form;
		IPnrEvaAuditInfoMgr auditInfoMgr = (IPnrEvaAuditInfoMgr) getBean("iPnrEvaAuditInfoMgr");
		List auditInfoList = auditInfoMgr.getPnrEvaAudit(templateForm.getId());
		request.setAttribute("auditInfoList", auditInfoList);
		return mapping.findForward("showAuditInfo");
	}
}
