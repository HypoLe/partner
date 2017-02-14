package com.boco.eoms.partner.assiEva.webapp.action;

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
import com.boco.eoms.partner.assiEva.mgr.IAssiEvaKpiMgr;
import com.boco.eoms.partner.assiEva.mgr.IAssiEvaTemplateMgr;
import com.boco.eoms.partner.assiEva.mgr.IAssiEvaTreeMgr;
import com.boco.eoms.partner.assiEva.model.AssiEvaKpi;
import com.boco.eoms.partner.assiEva.model.AssiEvaTree;
import com.boco.eoms.partner.assiEva.util.AssiEvaConstants;
import com.boco.eoms.partner.assiEva.webapp.form.AssiEvaTreeForm;

public final class AssiEvaTreeAction extends BaseAction {

	/**
	 * 未指定方法（暂时停用）
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return assiEvaKpiTree(mapping, form, request, response);
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
	public ActionForward assiEvaKpiTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("assiEvaKpiTree");
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
		IAssiEvaTreeMgr assiEvaTreeMgr = (IAssiEvaTreeMgr) getBean("IassiEvaTreeMgr");
		// 所有子节点
		List list = assiEvaTreeMgr.listNextLevelNode(nodeId, "");
		for (Iterator nodeIter = list.iterator(); nodeIter.hasNext();) {
			AssiEvaTree assiEvaTree = (AssiEvaTree) nodeIter.next();
			JSONObject jitem = new JSONObject();
			jitem.put("id", assiEvaTree.getNodeId());
			jitem.put("text", assiEvaTree.getNodeName());
			jitem.put("allowEdit", false);
			jitem.put("allowDelete", false);
			// 指标类型
			if (AssiEvaConstants.NODETYPE_KPITYPE.equals(assiEvaTree.getNodeType())) {
				// 判断是否有子节点，决定是否leaf
				boolean leafFlag = true;
				List subNodeList = assiEvaTreeMgr.listNextLevelNode(assiEvaTree
						.getNodeId(), "");
				if (null != subNodeList && 0 < subNodeList.size()) {
					leafFlag = false;
				}
				jitem.put(UIConstants.JSON_NODETYPE,
						AssiEvaConstants.NODETYPE_KPITYPE);
				jitem.put("allowChild", true);
				jitem.put("allowNewKpi", true);
				jitem.put("allowEditKpiType", true);
				jitem.put("allowDelKpiType", true);
				jitem.put("qtip", AssiEvaConstants.QTIP_KPITYPE);
				jitem.put("leaf", leafFlag);
				jitem.put(UIConstants.JSON_ICONCLS, "kpiType");
			}
			// 指标
			else if (AssiEvaConstants.NODETYPE_KPI.equals(assiEvaTree.getNodeType())) {
				jitem.put(UIConstants.JSON_NODETYPE, AssiEvaConstants.NODETYPE_KPI);
				jitem.put("allowChild", false);
				jitem.put("allowNewKpi", false);
				jitem.put("allowEditKpi", true);
				jitem.put("allowDelKpi", true);
				jitem.put("qtip", AssiEvaConstants.QTIP_KPI);
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
		AssiEvaTreeForm assiEvaTreeForm = (AssiEvaTreeForm) form;
		assiEvaTreeForm.setParentNodeId(nodeId);
		updateFormBean(mapping, request, assiEvaTreeForm);
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
		IAssiEvaTreeMgr assiEvaTreeMgr = (IAssiEvaTreeMgr) getBean("IassiEvaTreeMgr");

		if (AssiEvaConstants.TREE_ROOT_ID.equals(nodeId)) {// 如果是根结点，取所有模板分类
			List tplTypeList = assiEvaTreeMgr.listNextLevelNode(nodeId,
					AssiEvaConstants.NODETYPE_TEMPLATETYPE);
			for (Iterator tplTypeIt = tplTypeList.iterator(); tplTypeIt
					.hasNext();) {
				JSONObject jitem = new JSONObject();
				AssiEvaTree assiEvaTree = (AssiEvaTree) tplTypeIt.next();
				jitem.put("id", assiEvaTree.getNodeId());
				jitem.put("text", assiEvaTree.getNodeName());
				jitem.put(UIConstants.JSON_NODETYPE,
						AssiEvaConstants.NODETYPE_TEMPLATETYPE);
				jitem.put("qtip", "【" + AssiEvaConstants.QTIP_TEMPLATETYPE + "】");
				jitem.put(UIConstants.JSON_ICONCLS, "templateType");
				// 根据下面是否有子节点设置是否叶子节点
				if (assiEvaTreeMgr.listNextLevelNode(assiEvaTree.getNodeId(), "")
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
		} else if (AssiEvaConstants.NODETYPE_TEMPLATETYPE.equals(nodeType)) {// 如果是模板分类，取所有模板（目前确定模板只有一级分类）
			IAssiEvaTemplateMgr tplMgr = (IAssiEvaTemplateMgr) getBean("IassiEvaTemplateMgr");
			List tplList = assiEvaTreeMgr.listNextLevelNode(nodeId,
					AssiEvaConstants.NODETYPE_TEMPLATE);
			for (Iterator tplIt = tplList.iterator(); tplIt.hasNext();) {
				JSONObject jitem = new JSONObject();
				AssiEvaTree assiEvaTree = (AssiEvaTree) tplIt.next();
				jitem.put("id", assiEvaTree.getNodeId());
				jitem.put("text", tplMgr.id2Name(assiEvaTree.getObjectId()) + "模板");
				jitem.put(UIConstants.JSON_NODETYPE,
						AssiEvaConstants.NODETYPE_TEMPLATE);
				jitem.put("qtip", "【" + AssiEvaConstants.QTIP_TEMPLATE + "】");
				jitem.put(UIConstants.JSON_ICONCLS, "template");
				// 根据下面是否有子节点设置是否叶子节点
				if (assiEvaTreeMgr.listNextLevelNode(assiEvaTree.getNodeId(), "")
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
			IAssiEvaKpiMgr kpiMgr = (IAssiEvaKpiMgr) getBean("IassiEvaKpiMgr");
			List kpiList = assiEvaTreeMgr.listNextLevelNode(nodeId,
					AssiEvaConstants.NODETYPE_KPI);
			for (Iterator kpiIt = kpiList.iterator(); kpiIt.hasNext();) {
				JSONObject jitem = new JSONObject();
				AssiEvaTree assiEvaTree = (AssiEvaTree) kpiIt.next();
				jitem.put("id", assiEvaTree.getNodeId());
				jitem.put("text", kpiMgr.id2Name(assiEvaTree.getObjectId()) + "("
						+ assiEvaTree.getWeight() + "分)");
				jitem.put(UIConstants.JSON_NODETYPE, AssiEvaConstants.NODETYPE_KPI);
				jitem.put("qtip", "【" + AssiEvaConstants.QTIP_KPI + "】");
				// 根据下面是否有子节点设置是否叶子节点
				if (assiEvaTreeMgr.listNextLevelNode(assiEvaTree.getNodeId(), "")
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
		IAssiEvaTreeMgr assiEvaTreeMgr = (IAssiEvaTreeMgr) getBean("IassiEvaTreeMgr");
		String nodeId = request.getParameter("nodeId");
		AssiEvaTree assiEvaTree = assiEvaTreeMgr.getTreeNodeByNodeId(nodeId);
		AssiEvaTreeForm assiEvaTreeForm = (AssiEvaTreeForm) convert(assiEvaTree);
		updateFormBean(mapping, request, assiEvaTreeForm);
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
		IAssiEvaTreeMgr assiEvaTreeMgr = (IAssiEvaTreeMgr) getBean("IassiEvaTreeMgr");
		String nodeId = request.getParameter("nodeId");
		assiEvaTreeMgr.removeTreeNodeByNodeId(nodeId);
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
		IAssiEvaTreeMgr assiEvaTreeMgr = (IAssiEvaTreeMgr) getBean("IassiEvaTreeMgr");
		AssiEvaTree assiEvaTree = new AssiEvaTree();
		AssiEvaTreeForm assiEvaTreeForm = (AssiEvaTreeForm) form;
		if (null == assiEvaTreeForm.getId() || "".equals(assiEvaTreeForm.getId())) {
			assiEvaTree = (AssiEvaTree) convert(assiEvaTreeForm);
			String newNodeId = assiEvaTreeMgr.getMaxNodeId(assiEvaTree
					.getParentNodeId());
			assiEvaTree.setNodeId(newNodeId);
			assiEvaTree.setNodeType(AssiEvaConstants.NODETYPE_KPITYPE);
			assiEvaTree.setLeaf(AssiEvaConstants.NODE_LEAF);
			assiEvaTreeMgr.saveTreeNode(assiEvaTree);
		} else {
			assiEvaTree = assiEvaTreeMgr.getTreeNode(assiEvaTreeForm.getId());
			assiEvaTree.setNodeName(assiEvaTreeForm.getNodeName());
			assiEvaTreeMgr.saveTreeNode(assiEvaTree);
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
		IAssiEvaTreeMgr assiEvaTreeMgr = (IAssiEvaTreeMgr) getBean("IassiEvaTreeMgr");
		IAssiEvaKpiMgr kpiMgr = (IAssiEvaKpiMgr) getBean("IassiEvaKpiMgr");
		// 所有子节点
		List list = assiEvaTreeMgr.listNextLevelNode(nodeId, "");
		for (Iterator nodeIter = list.iterator(); nodeIter.hasNext();) {
			AssiEvaTree assiEvaTree = (AssiEvaTree) nodeIter.next();
			JSONObject jitem = new JSONObject();
			// 指标类型
			if (AssiEvaConstants.NODETYPE_KPITYPE.equals(assiEvaTree.getNodeType())) {
				// 判断是否有子节点，决定是否leaf
				boolean leafFlag = true;
				List subNodeList = assiEvaTreeMgr.listNextLevelNode(assiEvaTree
						.getNodeId(), "");
				if (null != subNodeList && 0 < subNodeList.size()) {
					leafFlag = false;
				}
				jitem.put("id", assiEvaTree.getNodeId());
				jitem.put("text", assiEvaTree.getNodeName());
				jitem.put("leaf", leafFlag);
				jitem.put(UIConstants.JSON_ICONCLS, "kpiType");
			}
			// 指标
			else if (AssiEvaConstants.NODETYPE_KPI.equals(assiEvaTree.getNodeType())) {
				AssiEvaKpi kpi = kpiMgr.getKpiByNodeId(assiEvaTree.getNodeId());
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
