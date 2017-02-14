package com.boco.eoms.partner.lineEva.webapp.action;

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
import com.boco.eoms.partner.lineEva.mgr.ILineEvaKpiMgr;
import com.boco.eoms.partner.lineEva.mgr.ILineEvaTemplateMgr;
import com.boco.eoms.partner.lineEva.mgr.ILineEvaTreeMgr;
import com.boco.eoms.partner.lineEva.model.LineEvaKpi;
import com.boco.eoms.partner.lineEva.model.LineEvaTree;
import com.boco.eoms.partner.lineEva.util.LineEvaConstants;
import com.boco.eoms.partner.lineEva.webapp.form.LineEvaTreeForm;

public final class LineEvaTreeAction extends BaseAction {

	/**
	 * 未指定方法（暂时停用）
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return lineEvaKpiTree(mapping, form, request, response);
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
	public ActionForward lineEvaKpiTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("lineEvaKpiTree");
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
		ILineEvaTreeMgr lineEvaTreeMgr = (ILineEvaTreeMgr) getBean("IlineEvaTreeMgr");
		// 所有子节点
		List list = lineEvaTreeMgr.listNextLevelNode(nodeId, "");
		for (Iterator nodeIter = list.iterator(); nodeIter.hasNext();) {
			LineEvaTree lineEvaTree = (LineEvaTree) nodeIter.next();
			JSONObject jitem = new JSONObject();
			jitem.put("id", lineEvaTree.getNodeId());
			jitem.put("text", lineEvaTree.getNodeName());
			jitem.put("allowEdit", false);
			jitem.put("allowDelete", false);
			// 指标类型
			if (LineEvaConstants.NODETYPE_KPITYPE.equals(lineEvaTree.getNodeType())) {
				// 判断是否有子节点，决定是否leaf
				boolean leafFlag = true;
				List subNodeList = lineEvaTreeMgr.listNextLevelNode(lineEvaTree
						.getNodeId(), "");
				if (null != subNodeList && 0 < subNodeList.size()) {
					leafFlag = false;
				}
				jitem.put(UIConstants.JSON_NODETYPE,
						LineEvaConstants.NODETYPE_KPITYPE);
				jitem.put("allowChild", true);
				jitem.put("allowNewKpi", true);
				jitem.put("allowEditKpiType", true);
				jitem.put("allowDelKpiType", true);
				jitem.put("qtip", LineEvaConstants.QTIP_KPITYPE);
				jitem.put("leaf", leafFlag);
				jitem.put(UIConstants.JSON_ICONCLS, "kpiType");
			}
			// 指标
			else if (LineEvaConstants.NODETYPE_KPI.equals(lineEvaTree.getNodeType())) {
				jitem.put(UIConstants.JSON_NODETYPE, LineEvaConstants.NODETYPE_KPI);
				jitem.put("allowChild", false);
				jitem.put("allowNewKpi", false);
				jitem.put("allowEditKpi", true);
				jitem.put("allowDelKpi", true);
				jitem.put("qtip", LineEvaConstants.QTIP_KPI);
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
		LineEvaTreeForm lineEvaTreeForm = (LineEvaTreeForm) form;
		lineEvaTreeForm.setParentNodeId(nodeId);
		updateFormBean(mapping, request, lineEvaTreeForm);
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
		ILineEvaTreeMgr lineEvaTreeMgr = (ILineEvaTreeMgr) getBean("IlineEvaTreeMgr");

		if (LineEvaConstants.TREE_ROOT_ID.equals(nodeId)) {// 如果是根结点，取所有模板分类
			List tplTypeList = lineEvaTreeMgr.listNextLevelNode(nodeId,
					LineEvaConstants.NODETYPE_TEMPLATETYPE);
			for (Iterator tplTypeIt = tplTypeList.iterator(); tplTypeIt
					.hasNext();) {
				JSONObject jitem = new JSONObject();
				LineEvaTree lineEvaTree = (LineEvaTree) tplTypeIt.next();
				jitem.put("id", lineEvaTree.getNodeId());
				jitem.put("text", lineEvaTree.getNodeName());
				jitem.put(UIConstants.JSON_NODETYPE,
						LineEvaConstants.NODETYPE_TEMPLATETYPE);
				jitem.put("qtip", "【" + LineEvaConstants.QTIP_TEMPLATETYPE + "】");
				jitem.put(UIConstants.JSON_ICONCLS, "templateType");
				// 根据下面是否有子节点设置是否叶子节点
				if (lineEvaTreeMgr.listNextLevelNode(lineEvaTree.getNodeId(), "")
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
		} else if (LineEvaConstants.NODETYPE_TEMPLATETYPE.equals(nodeType)) {// 如果是模板分类，取所有模板（目前确定模板只有一级分类）
			ILineEvaTemplateMgr tplMgr = (ILineEvaTemplateMgr) getBean("IlineEvaTemplateMgr");
			List tplList = lineEvaTreeMgr.listNextLevelNode(nodeId,
					LineEvaConstants.NODETYPE_TEMPLATE);
			for (Iterator tplIt = tplList.iterator(); tplIt.hasNext();) {
				JSONObject jitem = new JSONObject();
				LineEvaTree lineEvaTree = (LineEvaTree) tplIt.next();
				jitem.put("id", lineEvaTree.getNodeId());
				jitem.put("text", tplMgr.id2Name(lineEvaTree.getObjectId()) + "模板");
				jitem.put(UIConstants.JSON_NODETYPE,
						LineEvaConstants.NODETYPE_TEMPLATE);
				jitem.put("qtip", "【" + LineEvaConstants.QTIP_TEMPLATE + "】");
				jitem.put(UIConstants.JSON_ICONCLS, "template");
				// 根据下面是否有子节点设置是否叶子节点
				if (lineEvaTreeMgr.listNextLevelNode(lineEvaTree.getNodeId(), "")
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
			ILineEvaKpiMgr kpiMgr = (ILineEvaKpiMgr) getBean("IlineEvaKpiMgr");
			List kpiList = lineEvaTreeMgr.listNextLevelNode(nodeId,
					LineEvaConstants.NODETYPE_KPI);
			for (Iterator kpiIt = kpiList.iterator(); kpiIt.hasNext();) {
				JSONObject jitem = new JSONObject();
				LineEvaTree lineEvaTree = (LineEvaTree) kpiIt.next();
				jitem.put("id", lineEvaTree.getNodeId());
				jitem.put("text", kpiMgr.id2Name(lineEvaTree.getObjectId()) + "("
						+ lineEvaTree.getWeight() + "分)");
				jitem.put(UIConstants.JSON_NODETYPE, LineEvaConstants.NODETYPE_KPI);
				jitem.put("qtip", "【" + LineEvaConstants.QTIP_KPI + "】");
				// 根据下面是否有子节点设置是否叶子节点
				if (lineEvaTreeMgr.listNextLevelNode(lineEvaTree.getNodeId(), "")
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
		ILineEvaTreeMgr lineEvaTreeMgr = (ILineEvaTreeMgr) getBean("IlineEvaTreeMgr");
		String nodeId = request.getParameter("nodeId");
		LineEvaTree lineEvaTree = lineEvaTreeMgr.getTreeNodeByNodeId(nodeId);
		LineEvaTreeForm lineEvaTreeForm = (LineEvaTreeForm) convert(lineEvaTree);
		updateFormBean(mapping, request, lineEvaTreeForm);
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
		ILineEvaTreeMgr lineEvaTreeMgr = (ILineEvaTreeMgr) getBean("IlineEvaTreeMgr");
		String nodeId = request.getParameter("nodeId");
		lineEvaTreeMgr.removeTreeNodeByNodeId(nodeId);
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
		ILineEvaTreeMgr lineEvaTreeMgr = (ILineEvaTreeMgr) getBean("IlineEvaTreeMgr");
		LineEvaTree lineEvaTree = new LineEvaTree();
		LineEvaTreeForm lineEvaTreeForm = (LineEvaTreeForm) form;
		if (null == lineEvaTreeForm.getId() || "".equals(lineEvaTreeForm.getId())) {
			lineEvaTree = (LineEvaTree) convert(lineEvaTreeForm);
			String newNodeId = lineEvaTreeMgr.getMaxNodeId(lineEvaTree
					.getParentNodeId());
			lineEvaTree.setNodeId(newNodeId);
			lineEvaTree.setNodeType(LineEvaConstants.NODETYPE_KPITYPE);
			lineEvaTree.setLeaf(LineEvaConstants.NODE_LEAF);
			lineEvaTreeMgr.saveTreeNode(lineEvaTree);
		} else {
			lineEvaTree = lineEvaTreeMgr.getTreeNode(lineEvaTreeForm.getId());
			lineEvaTree.setNodeName(lineEvaTreeForm.getNodeName());
			lineEvaTreeMgr.saveTreeNode(lineEvaTree);
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
		ILineEvaTreeMgr lineEvaTreeMgr = (ILineEvaTreeMgr) getBean("IlineEvaTreeMgr");
		ILineEvaKpiMgr kpiMgr = (ILineEvaKpiMgr) getBean("IlineEvaKpiMgr");
		// 所有子节点
		List list = lineEvaTreeMgr.listNextLevelNode(nodeId, "");
		for (Iterator nodeIter = list.iterator(); nodeIter.hasNext();) {
			LineEvaTree lineEvaTree = (LineEvaTree) nodeIter.next();
			JSONObject jitem = new JSONObject();
			// 指标类型
			if (LineEvaConstants.NODETYPE_KPITYPE.equals(lineEvaTree.getNodeType())) {
				// 判断是否有子节点，决定是否leaf
				boolean leafFlag = true;
				List subNodeList = lineEvaTreeMgr.listNextLevelNode(lineEvaTree
						.getNodeId(), "");
				if (null != subNodeList && 0 < subNodeList.size()) {
					leafFlag = false;
				}
				jitem.put("id", lineEvaTree.getNodeId());
				jitem.put("text", lineEvaTree.getNodeName());
				jitem.put("leaf", leafFlag);
				jitem.put(UIConstants.JSON_ICONCLS, "kpiType");
			}
			// 指标
			else if (LineEvaConstants.NODETYPE_KPI.equals(lineEvaTree.getNodeType())) {
				LineEvaKpi kpi = kpiMgr.getKpiByNodeId(lineEvaTree.getNodeId());
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
