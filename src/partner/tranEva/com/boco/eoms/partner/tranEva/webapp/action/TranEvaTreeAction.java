package com.boco.eoms.partner.tranEva.webapp.action;

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
import com.boco.eoms.partner.tranEva.mgr.ITranEvaKpiMgr;
import com.boco.eoms.partner.tranEva.mgr.ITranEvaTemplateMgr;
import com.boco.eoms.partner.tranEva.mgr.ITranEvaTreeMgr;
import com.boco.eoms.partner.tranEva.model.TranEvaKpi;
import com.boco.eoms.partner.tranEva.model.TranEvaTree;
import com.boco.eoms.partner.tranEva.util.TranEvaConstants;
import com.boco.eoms.partner.tranEva.webapp.form.TranEvaTreeForm;

public final class TranEvaTreeAction extends BaseAction {

	/**
	 * 未指定方法（暂时停用）
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return tranEvaKpiTree(mapping, form, request, response);
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
	public ActionForward tranEvaKpiTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("tranEvaKpiTree");
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
		ITranEvaTreeMgr tranEvaTreeMgr = (ITranEvaTreeMgr) getBean("ItranEvaTreeMgr");
		// 所有子节点
		List list = tranEvaTreeMgr.listNextLevelNode(nodeId, "");
		for (Iterator nodeIter = list.iterator(); nodeIter.hasNext();) {
			TranEvaTree tranEvaTree = (TranEvaTree) nodeIter.next();
			JSONObject jitem = new JSONObject();
			jitem.put("id", tranEvaTree.getNodeId());
			jitem.put("text", tranEvaTree.getNodeName());
			jitem.put("allowEdit", false);
			jitem.put("allowDelete", false);
			// 指标类型
			if (TranEvaConstants.NODETYPE_KPITYPE.equals(tranEvaTree.getNodeType())) {
				// 判断是否有子节点，决定是否leaf
				boolean leafFlag = true;
				List subNodeList = tranEvaTreeMgr.listNextLevelNode(tranEvaTree
						.getNodeId(), "");
				if (null != subNodeList && 0 < subNodeList.size()) {
					leafFlag = false;
				}
				jitem.put(UIConstants.JSON_NODETYPE,
						TranEvaConstants.NODETYPE_KPITYPE);
				jitem.put("allowChild", true);
				jitem.put("allowNewKpi", true);
				jitem.put("allowEditKpiType", true);
				jitem.put("allowDelKpiType", true);
				jitem.put("qtip", TranEvaConstants.QTIP_KPITYPE);
				jitem.put("leaf", leafFlag);
				jitem.put(UIConstants.JSON_ICONCLS, "kpiType");
			}
			// 指标
			else if (TranEvaConstants.NODETYPE_KPI.equals(tranEvaTree.getNodeType())) {
				jitem.put(UIConstants.JSON_NODETYPE, TranEvaConstants.NODETYPE_KPI);
				jitem.put("allowChild", false);
				jitem.put("allowNewKpi", false);
				jitem.put("allowEditKpi", true);
				jitem.put("allowDelKpi", true);
				jitem.put("qtip", TranEvaConstants.QTIP_KPI);
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
		TranEvaTreeForm tranEvaTreeForm = (TranEvaTreeForm) form;
		tranEvaTreeForm.setParentNodeId(nodeId);
		updateFormBean(mapping, request, tranEvaTreeForm);
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
		ITranEvaTreeMgr tranEvaTreeMgr = (ITranEvaTreeMgr) getBean("ItranEvaTreeMgr");

		if (TranEvaConstants.TREE_ROOT_ID.equals(nodeId)) {// 如果是根结点，取所有模板分类
			List tplTypeList = tranEvaTreeMgr.listNextLevelNode(nodeId,
					TranEvaConstants.NODETYPE_TEMPLATETYPE);
			for (Iterator tplTypeIt = tplTypeList.iterator(); tplTypeIt
					.hasNext();) {
				JSONObject jitem = new JSONObject();
				TranEvaTree tranEvaTree = (TranEvaTree) tplTypeIt.next();
				jitem.put("id", tranEvaTree.getNodeId());
				jitem.put("text", tranEvaTree.getNodeName());
				jitem.put(UIConstants.JSON_NODETYPE,
						TranEvaConstants.NODETYPE_TEMPLATETYPE);
				jitem.put("qtip", "【" + TranEvaConstants.QTIP_TEMPLATETYPE + "】");
				jitem.put(UIConstants.JSON_ICONCLS, "templateType");
				// 根据下面是否有子节点设置是否叶子节点
				if (tranEvaTreeMgr.listNextLevelNode(tranEvaTree.getNodeId(), "")
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
		} else if (TranEvaConstants.NODETYPE_TEMPLATETYPE.equals(nodeType)) {// 如果是模板分类，取所有模板（目前确定模板只有一级分类）
			ITranEvaTemplateMgr tplMgr = (ITranEvaTemplateMgr) getBean("ItranEvaTemplateMgr");
			List tplList = tranEvaTreeMgr.listNextLevelNode(nodeId,
					TranEvaConstants.NODETYPE_TEMPLATE);
			for (Iterator tplIt = tplList.iterator(); tplIt.hasNext();) {
				JSONObject jitem = new JSONObject();
				TranEvaTree tranEvaTree = (TranEvaTree) tplIt.next();
				jitem.put("id", tranEvaTree.getNodeId());
				jitem.put("text", tplMgr.id2Name(tranEvaTree.getObjectId()) + "模板");
				jitem.put(UIConstants.JSON_NODETYPE,
						TranEvaConstants.NODETYPE_TEMPLATE);
				jitem.put("qtip", "【" + TranEvaConstants.QTIP_TEMPLATE + "】");
				jitem.put(UIConstants.JSON_ICONCLS, "template");
				// 根据下面是否有子节点设置是否叶子节点
				if (tranEvaTreeMgr.listNextLevelNode(tranEvaTree.getNodeId(), "")
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
			ITranEvaKpiMgr kpiMgr = (ITranEvaKpiMgr) getBean("ItranEvaKpiMgr");
			List kpiList = tranEvaTreeMgr.listNextLevelNode(nodeId,
					TranEvaConstants.NODETYPE_KPI);
			for (Iterator kpiIt = kpiList.iterator(); kpiIt.hasNext();) {
				JSONObject jitem = new JSONObject();
				TranEvaTree tranEvaTree = (TranEvaTree) kpiIt.next();
				jitem.put("id", tranEvaTree.getNodeId());
				jitem.put("text", kpiMgr.id2Name(tranEvaTree.getObjectId()) + "("
						+ tranEvaTree.getWeight() + "分)");
				jitem.put(UIConstants.JSON_NODETYPE, TranEvaConstants.NODETYPE_KPI);
				jitem.put("qtip", "【" + TranEvaConstants.QTIP_KPI + "】");
				// 根据下面是否有子节点设置是否叶子节点
				if (tranEvaTreeMgr.listNextLevelNode(tranEvaTree.getNodeId(), "")
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
		ITranEvaTreeMgr tranEvaTreeMgr = (ITranEvaTreeMgr) getBean("ItranEvaTreeMgr");
		String nodeId = request.getParameter("nodeId");
		TranEvaTree tranEvaTree = tranEvaTreeMgr.getTreeNodeByNodeId(nodeId);
		TranEvaTreeForm tranEvaTreeForm = (TranEvaTreeForm) convert(tranEvaTree);
		updateFormBean(mapping, request, tranEvaTreeForm);
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
		ITranEvaTreeMgr tranEvaTreeMgr = (ITranEvaTreeMgr) getBean("ItranEvaTreeMgr");
		String nodeId = request.getParameter("nodeId");
		tranEvaTreeMgr.removeTreeNodeByNodeId(nodeId);
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
		ITranEvaTreeMgr tranEvaTreeMgr = (ITranEvaTreeMgr) getBean("ItranEvaTreeMgr");
		TranEvaTree tranEvaTree = new TranEvaTree();
		TranEvaTreeForm tranEvaTreeForm = (TranEvaTreeForm) form;
		if (null == tranEvaTreeForm.getId() || "".equals(tranEvaTreeForm.getId())) {
			tranEvaTree = (TranEvaTree) convert(tranEvaTreeForm);
			String newNodeId = tranEvaTreeMgr.getMaxNodeId(tranEvaTree
					.getParentNodeId());
			tranEvaTree.setNodeId(newNodeId);
			tranEvaTree.setNodeType(TranEvaConstants.NODETYPE_KPITYPE);
			tranEvaTree.setLeaf(TranEvaConstants.NODE_LEAF);
			tranEvaTreeMgr.saveTreeNode(tranEvaTree);
		} else {
			tranEvaTree = tranEvaTreeMgr.getTreeNode(tranEvaTreeForm.getId());
			tranEvaTree.setNodeName(tranEvaTreeForm.getNodeName());
			tranEvaTreeMgr.saveTreeNode(tranEvaTree);
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
		ITranEvaTreeMgr tranEvaTreeMgr = (ITranEvaTreeMgr) getBean("ItranEvaTreeMgr");
		ITranEvaKpiMgr kpiMgr = (ITranEvaKpiMgr) getBean("ItranEvaKpiMgr");
		// 所有子节点
		List list = tranEvaTreeMgr.listNextLevelNode(nodeId, "");
		for (Iterator nodeIter = list.iterator(); nodeIter.hasNext();) {
			TranEvaTree tranEvaTree = (TranEvaTree) nodeIter.next();
			JSONObject jitem = new JSONObject();
			// 指标类型
			if (TranEvaConstants.NODETYPE_KPITYPE.equals(tranEvaTree.getNodeType())) {
				// 判断是否有子节点，决定是否leaf
				boolean leafFlag = true;
				List subNodeList = tranEvaTreeMgr.listNextLevelNode(tranEvaTree
						.getNodeId(), "");
				if (null != subNodeList && 0 < subNodeList.size()) {
					leafFlag = false;
				}
				jitem.put("id", tranEvaTree.getNodeId());
				jitem.put("text", tranEvaTree.getNodeName());
				jitem.put("leaf", leafFlag);
				jitem.put(UIConstants.JSON_ICONCLS, "kpiType");
			}
			// 指标
			else if (TranEvaConstants.NODETYPE_KPI.equals(tranEvaTree.getNodeType())) {
				TranEvaKpi kpi = kpiMgr.getKpiByNodeId(tranEvaTree.getNodeId());
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
