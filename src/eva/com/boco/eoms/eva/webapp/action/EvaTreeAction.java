package com.boco.eoms.eva.webapp.action;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.ui.util.JSONUtil;
import com.boco.eoms.commons.ui.util.UIConstants;
import com.boco.eoms.eva.mgr.IEvaKpiMgr;
import com.boco.eoms.eva.mgr.IEvaReportInfoMgr;
import com.boco.eoms.eva.mgr.IEvaTaskMgr;
import com.boco.eoms.eva.mgr.IEvaTemplateMgr;
import com.boco.eoms.eva.mgr.IEvaTreeMgr;
import com.boco.eoms.eva.model.EvaKpi;
import com.boco.eoms.eva.model.EvaReportInfo;
import com.boco.eoms.eva.model.EvaTask;
import com.boco.eoms.eva.model.EvaTemplate;
import com.boco.eoms.eva.model.EvaTree;
import com.boco.eoms.eva.util.EvaConstants;
import com.boco.eoms.eva.webapp.form.EvaTreeForm;

public final class EvaTreeAction extends BaseAction {

	/**
	 * 未指定方法（暂时停用）
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return evaKpiTree(mapping, form, request, response);
	}

	/**
	 * 考核树（暂时停用）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward evaKpiTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("evaKpiTree");
	}

	/**
	 * 生成考核树JSON数据（暂时停用）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getKpiNodes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String nodeId = request.getParameter("node");
		JSONArray jsonRoot = new JSONArray();
		IEvaTreeMgr evaTreeMgr = (IEvaTreeMgr) getBean("IevaTreeMgr");
		// 所有子节点
		List list = evaTreeMgr.listNextLevelNode(nodeId, "");
		for (Iterator nodeIter = list.iterator(); nodeIter.hasNext();) {
			EvaTree evaTree = (EvaTree) nodeIter.next();
			JSONObject jitem = new JSONObject();
			jitem.put("id", evaTree.getNodeId());
			jitem.put("text", evaTree.getNodeName());
			jitem.put("allowEdit", false);
			jitem.put("allowDelete", false);
			// 指标类型
			if (EvaConstants.NODETYPE_KPITYPE.equals(evaTree.getNodeType())) {
				// 判断是否有子节点，决定是否leaf
				boolean leafFlag = true;
				List subNodeList = evaTreeMgr.listNextLevelNode(evaTree
						.getNodeId(), "");
				if (null != subNodeList && 0 < subNodeList.size()) {
					leafFlag = false;
				}
				jitem.put(UIConstants.JSON_NODETYPE,
						EvaConstants.NODETYPE_KPITYPE);
				jitem.put("allowChild", true);
				jitem.put("allowNewKpi", true);
				jitem.put("allowEditKpiType", true);
				jitem.put("allowDelKpiType", true);
				jitem.put("qtip", EvaConstants.QTIP_KPITYPE);
				jitem.put("leaf", leafFlag);
				jitem.put(UIConstants.JSON_ICONCLS, "kpiType");
			}
			// 指标
			else if (EvaConstants.NODETYPE_KPI.equals(evaTree.getNodeType())) {
				jitem.put(UIConstants.JSON_NODETYPE, EvaConstants.NODETYPE_KPI);
				jitem.put("allowChild", false);
				jitem.put("allowNewKpi", false);
				jitem.put("allowEditKpi", true);
				jitem.put("allowDelKpi", true);
				jitem.put("qtip", EvaConstants.QTIP_KPI);
				jitem.put("leaf", true);
				jitem.put(UIConstants.JSON_ICONCLS, "kpi");
			}
			jsonRoot.put(jitem);
		}
		JSONUtil.print(response, jsonRoot.toString());
		return null;
	}
	/**
	 * 新建考核树节点（指标分类）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward newNode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String nodeId = request.getParameter("nodeId");
		EvaTreeForm evaTreeForm = (EvaTreeForm) form;
		evaTreeForm.setParentNodeId(nodeId);
		updateFormBean(mapping, request, evaTreeForm);
		return mapping.findForward("newNode");
	}
	/**
	 * 生成模板树JSON数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getTemplateNodes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String nodeId = StaticMethod.null2String(request.getParameter("node"));
		String nodeType = StaticMethod.null2String(request
				.getParameter("nodeType"));
		JSONArray jsonRoot = new JSONArray();
		IEvaTreeMgr evaTreeMgr = (IEvaTreeMgr) getBean("IevaTreeMgr");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String userid = sessionform.getUserid();
		String deptid = sessionform.getDeptid();
		if (EvaConstants.TREE_ROOT_ID.equals(nodeId)) {// 如果是根结点，取所有模板分类
			List tplTypeList = evaTreeMgr.listNextLevelNode(nodeId,
					EvaConstants.NODETYPE_TEMPLATETYPE);
			for (Iterator tplTypeIt = tplTypeList.iterator(); tplTypeIt
					.hasNext();) {
				JSONObject jitem = new JSONObject();
				EvaTree evaTree = (EvaTree) tplTypeIt.next();
				jitem.put("id", evaTree.getNodeId());
				jitem.put("text", evaTree.getNodeName());
				jitem.put(UIConstants.JSON_NODETYPE,
						EvaConstants.NODETYPE_TEMPLATETYPE);
				jitem.put("qtip", "【" + EvaConstants.QTIP_TEMPLATETYPE + "】");
				jitem.put(UIConstants.JSON_ICONCLS, "templateType");
				// 根据下面是否有子节点设置是否叶子节点
				if (evaTreeMgr.listNextLevelNode(evaTree.getNodeId(), "")
						.iterator().hasNext()) {
					jitem.put("leaf", false);
				} else {
					jitem.put("leaf", true);
				}
				// 设置右键菜单
				jitem.put("allownewTemplate", true);
				jitem.put("alloweditTemplateType", true);
				jsonRoot.put(jitem);
			}
		} else if (EvaConstants.NODETYPE_TEMPLATETYPE.equals(nodeType)) {// 如果是模板分类，取所有模板（目前确定模板只有一级分类）
			IEvaTemplateMgr tplMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
			List tplList = evaTreeMgr.listNextLevelNode(nodeId,
					EvaConstants.NODETYPE_TEMPLATE);
				EvaTemplate template = null;
			for (Iterator tplIt = tplList.iterator(); tplIt.hasNext();) {
				EvaTree evaTree = (EvaTree) tplIt.next();
				template = tplMgr.getTemplate(evaTree.getObjectId());
				if(!template.getActivated().equals(EvaConstants.TEMPLATE_CLOSED)){//树图中去掉已关闭的模板
				JSONObject jitem = new JSONObject();
				jitem.put("id", evaTree.getNodeId());
				jitem.put("text", template.getTemplateName() + "模板");
				jitem.put(UIConstants.JSON_NODETYPE,
						EvaConstants.NODETYPE_TEMPLATE);
				jitem.put("qtip", "【" + EvaConstants.QTIP_TEMPLATE + "】");
				jitem.put(UIConstants.JSON_ICONCLS, "template");
				// 根据下面是否有子节点设置是否叶子节点
				if (evaTreeMgr.listNextLevelNode(evaTree.getNodeId(), "")
						.iterator().hasNext()) {
					jitem.put("leaf", false);
				} else {
					jitem.put("leaf", true);
				}
				IEvaTaskMgr taskMgr = (IEvaTaskMgr) getBean("IevaTaskMgr");
				EvaTask task = taskMgr.getTaskByTplAndOrg(template.getId(), deptid);
				String taskId = task.getId();
				IEvaReportInfoMgr reportInfoMgr = (IEvaReportInfoMgr) getBean("IevaReportInfoMgr");
				List AllReport = reportInfoMgr.getReportInfoByTaskId(taskId);	
				EvaReportInfo reportInfo = null;
				boolean addEdit = true;
//				判断任务是否被执行，未执行则增加修改考核信息的右键菜单
				for(int i=0;i<AllReport.size();i++){
					reportInfo = (EvaReportInfo)AllReport.get(i);
					if(!"0".equals(reportInfo.getIsPublish())){
						addEdit = false;
						break;
					}
				}
//				设定是否修改指标右键菜单
				if(addEdit){
					jitem.put("alloweditTemplateMess", true);
				}
				// 设置右键菜单
				if(EvaConstants.TEMPLATE_ACTIVATED.equals(template.getActivated())){
					jitem.put("allownewKpi", false);
					jitem.put("alloweditTemplate", false);
					jitem.put("allowdetailTemplate", true);
					jitem.put("allowdelTemplate", false);
					jitem.put("allowexecute", false);
					jitem.put("allowClick", true);
				} else {
					jitem.put("allownewKpi", true);
					jitem.put("alloweditTemplate", true);
					jitem.put("allowdelTemplate", true);
					jitem.put("allowexecute", false);
					jitem.put("allowClick", true);
				}
				jsonRoot.put(jitem);
				}
			}
		} else {// 如果是模板或者指标，取所有直属下级指标
			IEvaTemplateMgr tplMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
			IEvaKpiMgr kpiMgr = (IEvaKpiMgr) getBean("IevaKpiMgr");
			List kpiList = evaTreeMgr.listNextLevelNode(nodeId,
					EvaConstants.NODETYPE_KPI);
//			EvaTemplate template = null;
			EvaTree evaParentTree = null;
			for (Iterator kpiIt = kpiList.iterator(); kpiIt.hasNext();) {
				JSONObject jitem = new JSONObject();
				EvaTree evaTree = (EvaTree) kpiIt.next();
				jitem.put("id", evaTree.getNodeId());
				jitem.put("text", kpiMgr.id2Name(evaTree.getObjectId()) + "("
						+ evaTree.getWeight() + "分)");
				jitem.put(UIConstants.JSON_NODETYPE, EvaConstants.NODETYPE_KPI);
				jitem.put("qtip", "【" + EvaConstants.QTIP_KPI + "】");
				// 根据下面是否有子节点设置是否叶子节点
				if (evaTreeMgr.listNextLevelNode(evaTree.getNodeId(), "")
						.iterator().hasNext()) {
					jitem.put(UIConstants.JSON_ICONCLS, "kpiType");
					jitem.put("leaf", false);
				} else {
					jitem.put(UIConstants.JSON_ICONCLS, "kpi");
					jitem.put("leaf", true);
				}
				// 设置右键菜单
					jitem.put("allownewKpi", false);
					jitem.put("alloweditKpi", false);
					jitem.put("allowdelKpi", false);
					jitem.put("allowClick", false);
				jsonRoot.put(jitem);
			}
		}
		JSONUtil.print(response, jsonRoot.toString());
		return null;
	}

	/**
	 * 生成模板树JSON数据(可执行)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getTemplateNodesByExcute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String nodeId = StaticMethod.null2String(request.getParameter("node"));
		String nodeType = StaticMethod.null2String(request
				.getParameter("nodeType"));
		JSONArray jsonRoot = new JSONArray();
		IEvaTreeMgr evaTreeMgr = (IEvaTreeMgr) getBean("IevaTreeMgr");

		if (EvaConstants.TREE_ROOT_ID.equals(nodeId)) {// 如果是根结点，取所有模板分类
			List tplTypeList = evaTreeMgr.listNextLevelNode(nodeId,
					EvaConstants.NODETYPE_TEMPLATETYPE);
			for (Iterator tplTypeIt = tplTypeList.iterator(); tplTypeIt
					.hasNext();) {
				JSONObject jitem = new JSONObject();
				EvaTree evaTree = (EvaTree) tplTypeIt.next();
				jitem.put("id", evaTree.getNodeId());
				jitem.put("text", evaTree.getNodeName());
				jitem.put(UIConstants.JSON_NODETYPE,
						EvaConstants.NODETYPE_TEMPLATETYPE);
				jitem.put("qtip", "【" + EvaConstants.QTIP_TEMPLATETYPE + "】");
				jitem.put(UIConstants.JSON_ICONCLS, "templateType");
				// 根据下面是否有子节点设置是否叶子节点
				if (evaTreeMgr.listNextLevelNode(evaTree.getNodeId(), "")
						.iterator().hasNext()) {
					jitem.put("leaf", false);
				} else {
					jitem.put("leaf", true);
				}
				// 设置右键菜单
				jsonRoot.put(jitem);
			}
		} else if (EvaConstants.NODETYPE_TEMPLATETYPE.equals(nodeType)) {// 如果是模板分类，取所有模板（目前确定模板只有一级分类）
			IEvaTemplateMgr tplMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
			List tplList = evaTreeMgr.listNextLevelNode(nodeId,
					EvaConstants.NODETYPE_TEMPLATE);
				EvaTemplate template = null;
			for (Iterator tplIt = tplList.iterator(); tplIt.hasNext();) {
				EvaTree evaTree = (EvaTree) tplIt.next();
				template = tplMgr.getTemplate(evaTree.getObjectId());
				if(template.getActivated().equals(EvaConstants.TEMPLATE_ACTIVATED)){//树图中显示已激活的
				JSONObject jitem = new JSONObject();
				jitem.put("id", evaTree.getNodeId());
				jitem.put("text", template.getTemplateName() + "模板");
				jitem.put(UIConstants.JSON_NODETYPE,
						EvaConstants.NODETYPE_TEMPLATE);
				jitem.put("qtip", "【" + EvaConstants.QTIP_TEMPLATE + "】");
				jitem.put(UIConstants.JSON_ICONCLS, "template");
				// 根据下面是否有子节点设置是否叶子节点
				jitem.put("leaf", true);
				jitem.put("allowClick", true);
				jitem.put("allowclosedTemplate", true);
				jsonRoot.put(jitem);
				}
			}
		} 
		JSONUtil.print(response, jsonRoot.toString());
		return null;
	}

	/**
	 * 生成模板树JSON数据(历史)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getTemplateNodesByHistory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String nodeId = StaticMethod.null2String(request.getParameter("node"));
		String nodeType = StaticMethod.null2String(request
				.getParameter("nodeType"));
		JSONArray jsonRoot = new JSONArray();
		IEvaTreeMgr evaTreeMgr = (IEvaTreeMgr) getBean("IevaTreeMgr");

		if (EvaConstants.TREE_ROOT_ID.equals(nodeId)) {// 如果是根结点，取所有模板分类
			List tplTypeList = evaTreeMgr.listNextLevelNode(nodeId,
					EvaConstants.NODETYPE_TEMPLATETYPE);
			for (Iterator tplTypeIt = tplTypeList.iterator(); tplTypeIt
					.hasNext();) {
				JSONObject jitem = new JSONObject();
				EvaTree evaTree = (EvaTree) tplTypeIt.next();
				jitem.put("id", evaTree.getNodeId());
				jitem.put("text", evaTree.getNodeName());
				jitem.put(UIConstants.JSON_NODETYPE,
						EvaConstants.NODETYPE_TEMPLATETYPE);
				jitem.put("qtip", "【" + EvaConstants.QTIP_TEMPLATETYPE + "】");
				jitem.put(UIConstants.JSON_ICONCLS, "templateType");
				// 根据下面是否有子节点设置是否叶子节点
				if (evaTreeMgr.listNextLevelNode(evaTree.getNodeId(), "")
						.iterator().hasNext()) {
					jitem.put("leaf", false);
				} else {
					jitem.put("leaf", true);
				}
				jitem.put("allowClick", true);
				// 设置右键菜单
				jsonRoot.put(jitem);
			}
		} else if (EvaConstants.NODETYPE_TEMPLATETYPE.equals(nodeType)) {// 如果是模板分类，取所有模板（目前确定模板只有一级分类）
			IEvaTemplateMgr tplMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
			List tplList = evaTreeMgr.listNextLevelNode(nodeId,
					EvaConstants.NODETYPE_TEMPLATE);
				EvaTemplate template = null;
			for (Iterator tplIt = tplList.iterator(); tplIt.hasNext();) {
				EvaTree evaTree = (EvaTree) tplIt.next();
				template = tplMgr.getTemplate(evaTree.getObjectId());
				if(!template.getActivated().equals(EvaConstants.TEMPLATE_NOTACTIVATED)){//树图中显示已激活的
				JSONObject jitem = new JSONObject();
				jitem.put("id", evaTree.getNodeId());
				jitem.put("text", template.getTemplateName() + "模板");
				jitem.put(UIConstants.JSON_NODETYPE,
						EvaConstants.NODETYPE_TEMPLATE);
				jitem.put("qtip", "【" + EvaConstants.QTIP_TEMPLATE + "】");
				jitem.put(UIConstants.JSON_ICONCLS, "template");
				// 根据下面是否有子节点设置是否叶子节点
				jitem.put("leaf", true);
				jitem.put("allowexecuteTemplate", false);
				jitem.put("allowClick", true);
				jsonRoot.put(jitem);
				}
			}
		} 
		JSONUtil.print(response, jsonRoot.toString());
		return null;
	}		
	/**
	 * 修改节点（指标分类）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editNode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IEvaTreeMgr evaTreeMgr = (IEvaTreeMgr) getBean("IevaTreeMgr");
		String nodeId = request.getParameter("nodeId");
		EvaTree evaTree = evaTreeMgr.getTreeNodeByNodeId(nodeId);
		EvaTreeForm evaTreeForm = (EvaTreeForm) convert(evaTree);
		updateFormBean(mapping, request, evaTreeForm);
		return mapping.findForward("editNode");
	}
	
	/**
	 * 删除节点（包括指标分类下的指标）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removeNode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IEvaTreeMgr evaTreeMgr = (IEvaTreeMgr) getBean("IevaTreeMgr");
		String nodeId = request.getParameter("nodeId");
		evaTreeMgr.removeTreeNodeByNodeId(nodeId);
		return mapping.findForward("success");
	}
	
	/**
	 * 保存节点（指标分类）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveNode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IEvaTreeMgr evaTreeMgr = (IEvaTreeMgr) getBean("IevaTreeMgr");
		EvaTree evaTree = new EvaTree();
		EvaTreeForm evaTreeForm = (EvaTreeForm) form;
		if (null == evaTreeForm.getId() || "".equals(evaTreeForm.getId())) {
			evaTree = (EvaTree) convert(evaTreeForm);
			String newNodeId = evaTreeMgr.getMaxNodeId(evaTree
					.getParentNodeId());
			evaTree.setNodeId(newNodeId);
			evaTree.setNodeType(EvaConstants.NODETYPE_KPITYPE);
			evaTree.setLeaf(EvaConstants.NODE_LEAF);
			evaTreeMgr.saveTreeNode(evaTree);
		} else {
			evaTree = evaTreeMgr.getTreeNode(evaTreeForm.getId());
			evaTree.setNodeName(evaTreeForm.getNodeName());
			evaTreeMgr.saveTreeNode(evaTree);
		}
		return mapping.findForward("success");
	}
	
	
	/**
	 * 生成指标选择树JSON数据（暂时停用）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String kpiTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String nodeId = request.getParameter("node");
		JSONArray jsonRoot = new JSONArray();
		IEvaTreeMgr evaTreeMgr = (IEvaTreeMgr) getBean("IevaTreeMgr");
		IEvaKpiMgr kpiMgr = (IEvaKpiMgr) getBean("IevaKpiMgr");
		// 所有子节点
		List list = evaTreeMgr.listNextLevelNode(nodeId, "");
		for (Iterator nodeIter = list.iterator(); nodeIter.hasNext();) {
			EvaTree evaTree = (EvaTree) nodeIter.next();
			JSONObject jitem = new JSONObject();
			// 指标类型
			if (EvaConstants.NODETYPE_KPITYPE.equals(evaTree.getNodeType())) {
				// 判断是否有子节点，决定是否leaf
				boolean leafFlag = true;
				List subNodeList = evaTreeMgr.listNextLevelNode(evaTree
						.getNodeId(), "");
				if (null != subNodeList && 0 < subNodeList.size()) {
					leafFlag = false;
				}
				jitem.put("id", evaTree.getNodeId());
				jitem.put("text", evaTree.getNodeName());
				jitem.put("leaf", leafFlag);
				jitem.put(UIConstants.JSON_ICONCLS, "kpiType");
			}
			// 指标
			else if (EvaConstants.NODETYPE_KPI.equals(evaTree.getNodeType())) {
				EvaKpi kpi = kpiMgr.getKpiByNodeId(evaTree.getNodeId());
				jitem.put("id", kpi.getId());
				jitem.put("text", kpi.getKpiName());
				jitem.put("leaf", true);
				jitem.put(UIConstants.JSON_ICONCLS, "kpi");
				jitem.put("checked", false);
			}
			jsonRoot.put(jitem);
		}
		JSONUtil.print(response, jsonRoot.toString());
		return null;
	}

}
