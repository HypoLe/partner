package com.boco.eoms.assEva.webapp.action;

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
import com.boco.eoms.commons.ui.util.JSONUtil;
import com.boco.eoms.commons.ui.util.UIConstants;
import com.boco.eoms.assEva.mgr.IAssEvaKpiMgr;
import com.boco.eoms.assEva.mgr.IAssEvaTemplateMgr;
import com.boco.eoms.assEva.mgr.IAssEvaTreeMgr;
import com.boco.eoms.assEva.model.AssEvaKpi;
import com.boco.eoms.assEva.model.AssEvaTree;
import com.boco.eoms.assEva.util.AssEvaConstants;
import com.boco.eoms.assEva.webapp.form.AssEvaTreeForm;

public final class AssEvaTreeAction extends BaseAction {

	/**
	 * 未指定方法（暂时停用）
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return assEvaKpiTree(mapping, form, request, response);
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
	public ActionForward assEvaKpiTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("assEvaKpiTree");
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
		IAssEvaTreeMgr assEvaTreeMgr = (IAssEvaTreeMgr) getBean("IassEvaTreeMgr");
		// 所有子节点
		List list = assEvaTreeMgr.listNextLevelNode(nodeId, "");
		for (Iterator nodeIter = list.iterator(); nodeIter.hasNext();) {
			AssEvaTree assEvaTree = (AssEvaTree) nodeIter.next();
			JSONObject jitem = new JSONObject();
			jitem.put("id", assEvaTree.getNodeId());
			jitem.put("text", assEvaTree.getNodeName());
			jitem.put("allowEdit", false);
			jitem.put("allowDelete", false);
			// 指标类型
			if (AssEvaConstants.NODETYPE_KPITYPE.equals(assEvaTree.getNodeType())) {
				// 判断是否有子节点，决定是否leaf
				boolean leafFlag = true;
				List subNodeList = assEvaTreeMgr.listNextLevelNode(assEvaTree
						.getNodeId(), "");
				if (null != subNodeList && 0 < subNodeList.size()) {
					leafFlag = false;
				}
				jitem.put(UIConstants.JSON_NODETYPE,
						AssEvaConstants.NODETYPE_KPITYPE);
				jitem.put("allowChild", true);
				jitem.put("allowNewKpi", true);
				jitem.put("allowEditKpiType", true);
				jitem.put("allowDelKpiType", true);
				jitem.put("qtip", AssEvaConstants.QTIP_KPITYPE);
				jitem.put("leaf", leafFlag);
				jitem.put(UIConstants.JSON_ICONCLS, "kpiType");
			}
			// 指标
			else if (AssEvaConstants.NODETYPE_KPI.equals(assEvaTree.getNodeType())) {
				jitem.put(UIConstants.JSON_NODETYPE, AssEvaConstants.NODETYPE_KPI);
				jitem.put("allowChild", false);
				jitem.put("allowNewKpi", false);
				jitem.put("allowEditKpi", true);
				jitem.put("allowDelKpi", true);
				jitem.put("qtip", AssEvaConstants.QTIP_KPI);
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
		AssEvaTreeForm assEvaTreeForm = (AssEvaTreeForm) form;
		assEvaTreeForm.setParentNodeId(nodeId);
		updateFormBean(mapping, request, assEvaTreeForm);
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
		IAssEvaTreeMgr assEvaTreeMgr = (IAssEvaTreeMgr) getBean("IassEvaTreeMgr");

		if (AssEvaConstants.TREE_ROOT_ID.equals(nodeId)) {// 如果是根结点，取所有模板分类
			List tplTypeList = assEvaTreeMgr.listNextLevelNode(nodeId,
					AssEvaConstants.NODETYPE_TEMPLATETYPE);
			for (Iterator tplTypeIt = tplTypeList.iterator(); tplTypeIt
					.hasNext();) {
				JSONObject jitem = new JSONObject();
				AssEvaTree assEvaTree = (AssEvaTree) tplTypeIt.next();
				jitem.put("id", assEvaTree.getNodeId());
				jitem.put("text", assEvaTree.getNodeName());
				jitem.put(UIConstants.JSON_NODETYPE,
						AssEvaConstants.NODETYPE_TEMPLATETYPE);
				jitem.put("qtip", "【" + AssEvaConstants.QTIP_TEMPLATETYPE + "】");
				jitem.put(UIConstants.JSON_ICONCLS, "templateType");
				// 根据下面是否有子节点设置是否叶子节点
				if (assEvaTreeMgr.listNextLevelNode(assEvaTree.getNodeId(), "")
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
		} else if (AssEvaConstants.NODETYPE_TEMPLATETYPE.equals(nodeType)) {// 如果是模板分类，取所有模板（目前确定模板只有一级分类）
			IAssEvaTemplateMgr tplMgr = (IAssEvaTemplateMgr) getBean("IassEvaTemplateMgr");
			List tplList = assEvaTreeMgr.listNextLevelNode(nodeId,
					AssEvaConstants.NODETYPE_TEMPLATE);
			for (Iterator tplIt = tplList.iterator(); tplIt.hasNext();) {
				JSONObject jitem = new JSONObject();
				AssEvaTree assEvaTree = (AssEvaTree) tplIt.next();
				jitem.put("id", assEvaTree.getNodeId());
				jitem.put("text", tplMgr.id2Name(assEvaTree.getObjectId()) + "模板");
				jitem.put(UIConstants.JSON_NODETYPE,
						AssEvaConstants.NODETYPE_TEMPLATE);
				jitem.put("qtip", "【" + AssEvaConstants.QTIP_TEMPLATE + "】");
				jitem.put(UIConstants.JSON_ICONCLS, "template");
				// 根据下面是否有子节点设置是否叶子节点
				if (assEvaTreeMgr.listNextLevelNode(assEvaTree.getNodeId(), "")
						.iterator().hasNext()) {
					jitem.put("leaf", false);
				} else {
					jitem.put("leaf", true);
				}
				// 设置右键菜单
				jitem.put("allownewKpi", true);
				jitem.put("alloweditTemplate", true);
				jitem.put("allowdelTemplate", true);
				jsonRoot.put(jitem);
			}
		} else {// 如果是模板或者指标，取所有直属下级指标
			IAssEvaKpiMgr kpiMgr = (IAssEvaKpiMgr) getBean("IassEvaKpiMgr");
			List kpiList = assEvaTreeMgr.listNextLevelNode(nodeId,
					AssEvaConstants.NODETYPE_KPI);
			for (Iterator kpiIt = kpiList.iterator(); kpiIt.hasNext();) {
				JSONObject jitem = new JSONObject();
				AssEvaTree assEvaTree = (AssEvaTree) kpiIt.next();
				jitem.put("id", assEvaTree.getNodeId());
				jitem.put("text", kpiMgr.id2Name(assEvaTree.getObjectId()) + "("
						+ assEvaTree.getWeight() + "分)");
				jitem.put(UIConstants.JSON_NODETYPE, AssEvaConstants.NODETYPE_KPI);
				jitem.put("qtip", "【" + AssEvaConstants.QTIP_KPI + "】");
				// 根据下面是否有子节点设置是否叶子节点
				if (assEvaTreeMgr.listNextLevelNode(assEvaTree.getNodeId(), "")
						.iterator().hasNext()) {
					jitem.put(UIConstants.JSON_ICONCLS, "kpiType");
					jitem.put("leaf", false);
				} else {
					jitem.put(UIConstants.JSON_ICONCLS, "kpi");
					jitem.put("leaf", true);
				}
				// 设置右键菜单
				jitem.put("allownewKpi", true);
				jitem.put("alloweditKpi", true);
				jitem.put("allowdelKpi", true);
				jsonRoot.put(jitem);
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
		IAssEvaTreeMgr assEvaTreeMgr = (IAssEvaTreeMgr) getBean("IassEvaTreeMgr");
		String nodeId = request.getParameter("nodeId");
		AssEvaTree assEvaTree = assEvaTreeMgr.getTreeNodeByNodeId(nodeId);
		AssEvaTreeForm assEvaTreeForm = (AssEvaTreeForm) convert(assEvaTree);
		updateFormBean(mapping, request, assEvaTreeForm);
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
		IAssEvaTreeMgr assEvaTreeMgr = (IAssEvaTreeMgr) getBean("IassEvaTreeMgr");
		String nodeId = request.getParameter("nodeId");
		assEvaTreeMgr.removeTreeNodeByNodeId(nodeId);
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
		IAssEvaTreeMgr assEvaTreeMgr = (IAssEvaTreeMgr) getBean("IassEvaTreeMgr");
		AssEvaTree assEvaTree = new AssEvaTree();
		AssEvaTreeForm assEvaTreeForm = (AssEvaTreeForm) form;
		if (null == assEvaTreeForm.getId() || "".equals(assEvaTreeForm.getId())) {
			assEvaTree = (AssEvaTree) convert(assEvaTreeForm);
			String newNodeId = assEvaTreeMgr.getMaxNodeId(assEvaTree
					.getParentNodeId());
			assEvaTree.setNodeId(newNodeId);
			assEvaTree.setNodeType(AssEvaConstants.NODETYPE_KPITYPE);
			assEvaTree.setLeaf(AssEvaConstants.NODE_LEAF);
			assEvaTreeMgr.saveTreeNode(assEvaTree);
		} else {
			assEvaTree = assEvaTreeMgr.getTreeNode(assEvaTreeForm.getId());
			assEvaTree.setNodeName(assEvaTreeForm.getNodeName());
			assEvaTreeMgr.saveTreeNode(assEvaTree);
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
		IAssEvaTreeMgr assEvaTreeMgr = (IAssEvaTreeMgr) getBean("IassEvaTreeMgr");
		IAssEvaKpiMgr kpiMgr = (IAssEvaKpiMgr) getBean("IassEvaKpiMgr");
		// 所有子节点
		List list = assEvaTreeMgr.listNextLevelNode(nodeId, "");
		for (Iterator nodeIter = list.iterator(); nodeIter.hasNext();) {
			AssEvaTree assEvaTree = (AssEvaTree) nodeIter.next();
			JSONObject jitem = new JSONObject();
			// 指标类型
			if (AssEvaConstants.NODETYPE_KPITYPE.equals(assEvaTree.getNodeType())) {
				// 判断是否有子节点，决定是否leaf
				boolean leafFlag = true;
				List subNodeList = assEvaTreeMgr.listNextLevelNode(assEvaTree
						.getNodeId(), "");
				if (null != subNodeList && 0 < subNodeList.size()) {
					leafFlag = false;
				}
				jitem.put("id", assEvaTree.getNodeId());
				jitem.put("text", assEvaTree.getNodeName());
				jitem.put("leaf", leafFlag);
				jitem.put(UIConstants.JSON_ICONCLS, "kpiType");
			}
			// 指标
			else if (AssEvaConstants.NODETYPE_KPI.equals(assEvaTree.getNodeType())) {
				AssEvaKpi kpi = kpiMgr.getKpiByNodeId(assEvaTree.getNodeId());
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
