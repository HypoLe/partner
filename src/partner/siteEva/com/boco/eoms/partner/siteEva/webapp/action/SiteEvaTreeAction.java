package com.boco.eoms.partner.siteEva.webapp.action;

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
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaKpiMgr;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaTemplateMgr;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaTreeMgr;
import com.boco.eoms.partner.siteEva.model.SiteEvaKpi;
import com.boco.eoms.partner.siteEva.model.SiteEvaTree;
import com.boco.eoms.partner.siteEva.util.SiteEvaConstants;
import com.boco.eoms.partner.siteEva.webapp.form.SiteEvaTreeForm;

public final class SiteEvaTreeAction extends BaseAction {

	/**
	 * 未指定方法（暂时停用）
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return siteEvaKpiTree(mapping, form, request, response);
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
	public ActionForward siteEvaKpiTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("siteEvaKpiTree");
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
		ISiteEvaTreeMgr siteEvaTreeMgr = (ISiteEvaTreeMgr) getBean("IsiteEvaTreeMgr");
		// 所有子节点
		List list = siteEvaTreeMgr.listNextLevelNode(nodeId, "");
		for (Iterator nodeIter = list.iterator(); nodeIter.hasNext();) {
			SiteEvaTree siteEvaTree = (SiteEvaTree) nodeIter.next();
			JSONObject jitem = new JSONObject();
			jitem.put("id", siteEvaTree.getNodeId());
			jitem.put("text", siteEvaTree.getNodeName());
			jitem.put("allowEdit", false);
			jitem.put("allowDelete", false);
			// 指标类型
			if (SiteEvaConstants.NODETYPE_KPITYPE.equals(siteEvaTree.getNodeType())) {
				// 判断是否有子节点，决定是否leaf
				boolean leafFlag = true;
				List subNodeList = siteEvaTreeMgr.listNextLevelNode(siteEvaTree
						.getNodeId(), "");
				if (null != subNodeList && 0 < subNodeList.size()) {
					leafFlag = false;
				}
				jitem.put(UIConstants.JSON_NODETYPE,
						SiteEvaConstants.NODETYPE_KPITYPE);
				jitem.put("allowChild", true);
				jitem.put("allowNewKpi", true);
				jitem.put("allowEditKpiType", true);
				jitem.put("allowDelKpiType", true);
				jitem.put("qtip", SiteEvaConstants.QTIP_KPITYPE);
				jitem.put("leaf", leafFlag);
				jitem.put(UIConstants.JSON_ICONCLS, "kpiType");
			}
			// 指标
			else if (SiteEvaConstants.NODETYPE_KPI.equals(siteEvaTree.getNodeType())) {
				jitem.put(UIConstants.JSON_NODETYPE, SiteEvaConstants.NODETYPE_KPI);
				jitem.put("allowChild", false);
				jitem.put("allowNewKpi", false);
				jitem.put("allowEditKpi", true);
				jitem.put("allowDelKpi", true);
				jitem.put("qtip", SiteEvaConstants.QTIP_KPI);
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
		SiteEvaTreeForm siteEvaTreeForm = (SiteEvaTreeForm) form;
		siteEvaTreeForm.setParentNodeId(nodeId);
		updateFormBean(mapping, request, siteEvaTreeForm);
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
		ISiteEvaTreeMgr siteEvaTreeMgr = (ISiteEvaTreeMgr) getBean("IsiteEvaTreeMgr");

		if (SiteEvaConstants.TREE_ROOT_ID.equals(nodeId)) {// 如果是根结点，取所有模板分类
			List tplTypeList = siteEvaTreeMgr.listNextLevelNode(nodeId,
					SiteEvaConstants.NODETYPE_TEMPLATETYPE);
			for (Iterator tplTypeIt = tplTypeList.iterator(); tplTypeIt
					.hasNext();) {
				JSONObject jitem = new JSONObject();
				SiteEvaTree siteEvaTree = (SiteEvaTree) tplTypeIt.next();
				jitem.put("id", siteEvaTree.getNodeId());
				jitem.put("text", siteEvaTree.getNodeName());
				jitem.put(UIConstants.JSON_NODETYPE,
						SiteEvaConstants.NODETYPE_TEMPLATETYPE);
				jitem.put("qtip", "【" + SiteEvaConstants.QTIP_TEMPLATETYPE + "】");
				jitem.put(UIConstants.JSON_ICONCLS, "templateType");
				// 根据下面是否有子节点设置是否叶子节点
				if (siteEvaTreeMgr.listNextLevelNode(siteEvaTree.getNodeId(), "")
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
		} else if (SiteEvaConstants.NODETYPE_TEMPLATETYPE.equals(nodeType)) {// 如果是模板分类，取所有模板（目前确定模板只有一级分类）
			ISiteEvaTemplateMgr tplMgr = (ISiteEvaTemplateMgr) getBean("IsiteEvaTemplateMgr");
			List tplList = siteEvaTreeMgr.listNextLevelNode(nodeId,
					SiteEvaConstants.NODETYPE_TEMPLATE);
			for (Iterator tplIt = tplList.iterator(); tplIt.hasNext();) {
				JSONObject jitem = new JSONObject();
				SiteEvaTree siteEvaTree = (SiteEvaTree) tplIt.next();
				jitem.put("id", siteEvaTree.getNodeId());
				jitem.put("text", tplMgr.id2Name(siteEvaTree.getObjectId()) + "模板");
				jitem.put(UIConstants.JSON_NODETYPE,
						SiteEvaConstants.NODETYPE_TEMPLATE);
				jitem.put("qtip", "【" + SiteEvaConstants.QTIP_TEMPLATE + "】");
				jitem.put(UIConstants.JSON_ICONCLS, "template");
				// 根据下面是否有子节点设置是否叶子节点
				if (siteEvaTreeMgr.listNextLevelNode(siteEvaTree.getNodeId(), "")
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
			ISiteEvaKpiMgr kpiMgr = (ISiteEvaKpiMgr) getBean("IsiteEvaKpiMgr");
			List kpiList = siteEvaTreeMgr.listNextLevelNode(nodeId,
					SiteEvaConstants.NODETYPE_KPI);
			for (Iterator kpiIt = kpiList.iterator(); kpiIt.hasNext();) {
				JSONObject jitem = new JSONObject();
				SiteEvaTree siteEvaTree = (SiteEvaTree) kpiIt.next();
				jitem.put("id", siteEvaTree.getNodeId());
				jitem.put("text", kpiMgr.id2Name(siteEvaTree.getObjectId()) + "("
						+ siteEvaTree.getWeight() + "分)");
				jitem.put(UIConstants.JSON_NODETYPE, SiteEvaConstants.NODETYPE_KPI);
				jitem.put("qtip", "【" + SiteEvaConstants.QTIP_KPI + "】");
				// 根据下面是否有子节点设置是否叶子节点
				if (siteEvaTreeMgr.listNextLevelNode(siteEvaTree.getNodeId(), "")
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
		ISiteEvaTreeMgr siteEvaTreeMgr = (ISiteEvaTreeMgr) getBean("IsiteEvaTreeMgr");
		String nodeId = request.getParameter("nodeId");
		SiteEvaTree siteEvaTree = siteEvaTreeMgr.getTreeNodeByNodeId(nodeId);
		SiteEvaTreeForm siteEvaTreeForm = (SiteEvaTreeForm) convert(siteEvaTree);
		updateFormBean(mapping, request, siteEvaTreeForm);
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
		ISiteEvaTreeMgr siteEvaTreeMgr = (ISiteEvaTreeMgr) getBean("IsiteEvaTreeMgr");
		String nodeId = request.getParameter("nodeId");
		siteEvaTreeMgr.removeTreeNodeByNodeId(nodeId);
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
		ISiteEvaTreeMgr siteEvaTreeMgr = (ISiteEvaTreeMgr) getBean("IsiteEvaTreeMgr");
		SiteEvaTree siteEvaTree = new SiteEvaTree();
		SiteEvaTreeForm siteEvaTreeForm = (SiteEvaTreeForm) form;
		if (null == siteEvaTreeForm.getId() || "".equals(siteEvaTreeForm.getId())) {
			siteEvaTree = (SiteEvaTree) convert(siteEvaTreeForm);
			String newNodeId = siteEvaTreeMgr.getMaxNodeId(siteEvaTree
					.getParentNodeId());
			siteEvaTree.setNodeId(newNodeId);
			siteEvaTree.setNodeType(SiteEvaConstants.NODETYPE_KPITYPE);
			siteEvaTree.setLeaf(SiteEvaConstants.NODE_LEAF);
			siteEvaTreeMgr.saveTreeNode(siteEvaTree);
		} else {
			siteEvaTree = siteEvaTreeMgr.getTreeNode(siteEvaTreeForm.getId());
			siteEvaTree.setNodeName(siteEvaTreeForm.getNodeName());
			siteEvaTreeMgr.saveTreeNode(siteEvaTree);
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
		ISiteEvaTreeMgr siteEvaTreeMgr = (ISiteEvaTreeMgr) getBean("IsiteEvaTreeMgr");
		ISiteEvaKpiMgr kpiMgr = (ISiteEvaKpiMgr) getBean("IsiteEvaKpiMgr");
		// 所有子节点
		List list = siteEvaTreeMgr.listNextLevelNode(nodeId, "");
		for (Iterator nodeIter = list.iterator(); nodeIter.hasNext();) {
			SiteEvaTree siteEvaTree = (SiteEvaTree) nodeIter.next();
			JSONObject jitem = new JSONObject();
			// 指标类型
			if (SiteEvaConstants.NODETYPE_KPITYPE.equals(siteEvaTree.getNodeType())) {
				// 判断是否有子节点，决定是否leaf
				boolean leafFlag = true;
				List subNodeList = siteEvaTreeMgr.listNextLevelNode(siteEvaTree
						.getNodeId(), "");
				if (null != subNodeList && 0 < subNodeList.size()) {
					leafFlag = false;
				}
				jitem.put("id", siteEvaTree.getNodeId());
				jitem.put("text", siteEvaTree.getNodeName());
				jitem.put("leaf", leafFlag);
				jitem.put(UIConstants.JSON_ICONCLS, "kpiType");
			}
			// 指标
			else if (SiteEvaConstants.NODETYPE_KPI.equals(siteEvaTree.getNodeType())) {
				SiteEvaKpi kpi = kpiMgr.getKpiByNodeId(siteEvaTree.getNodeId());
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
