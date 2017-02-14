package com.boco.eoms.partner.chanEva.webapp.action;

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
import com.boco.eoms.partner.chanEva.mgr.IChanEvaKpiMgr;
import com.boco.eoms.partner.chanEva.mgr.IChanEvaTemplateMgr;
import com.boco.eoms.partner.chanEva.mgr.IChanEvaTreeMgr;
import com.boco.eoms.partner.chanEva.model.ChanEvaKpi;
import com.boco.eoms.partner.chanEva.model.ChanEvaTree;
import com.boco.eoms.partner.chanEva.util.ChanEvaConstants;
import com.boco.eoms.partner.chanEva.webapp.form.ChanEvaTreeForm;

public final class ChanEvaTreeAction extends BaseAction {

	/**
	 * 未指定方法（暂时停用）
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return chanEvaKpiTree(mapping, form, request, response);
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
	public ActionForward chanEvaKpiTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("chanEvaKpiTree");
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
		IChanEvaTreeMgr chanEvaTreeMgr = (IChanEvaTreeMgr) getBean("IchanEvaTreeMgr");
		// 所有子节点
		List list = chanEvaTreeMgr.listNextLevelNode(nodeId, "");
		for (Iterator nodeIter = list.iterator(); nodeIter.hasNext();) {
			ChanEvaTree chanEvaTree = (ChanEvaTree) nodeIter.next();
			JSONObject jitem = new JSONObject();
			jitem.put("id", chanEvaTree.getNodeId());
			jitem.put("text", chanEvaTree.getNodeName());
			jitem.put("allowEdit", false);
			jitem.put("allowDelete", false);
			// 指标类型
			if (ChanEvaConstants.NODETYPE_KPITYPE.equals(chanEvaTree.getNodeType())) {
				// 判断是否有子节点，决定是否leaf
				boolean leafFlag = true;
				List subNodeList = chanEvaTreeMgr.listNextLevelNode(chanEvaTree
						.getNodeId(), "");
				if (null != subNodeList && 0 < subNodeList.size()) {
					leafFlag = false;
				}
				jitem.put(UIConstants.JSON_NODETYPE,
						ChanEvaConstants.NODETYPE_KPITYPE);
				jitem.put("allowChild", true);
				jitem.put("allowNewKpi", true);
				jitem.put("allowEditKpiType", true);
				jitem.put("allowDelKpiType", true);
				jitem.put("qtip", ChanEvaConstants.QTIP_KPITYPE);
				jitem.put("leaf", leafFlag);
				jitem.put(UIConstants.JSON_ICONCLS, "kpiType");
			}
			// 指标
			else if (ChanEvaConstants.NODETYPE_KPI.equals(chanEvaTree.getNodeType())) {
				jitem.put(UIConstants.JSON_NODETYPE, ChanEvaConstants.NODETYPE_KPI);
				jitem.put("allowChild", false);
				jitem.put("allowNewKpi", false);
				jitem.put("allowEditKpi", true);
				jitem.put("allowDelKpi", true);
				jitem.put("qtip", ChanEvaConstants.QTIP_KPI);
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
		ChanEvaTreeForm chanEvaTreeForm = (ChanEvaTreeForm) form;
		chanEvaTreeForm.setParentNodeId(nodeId);
		updateFormBean(mapping, request, chanEvaTreeForm);
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
		IChanEvaTreeMgr chanEvaTreeMgr = (IChanEvaTreeMgr) getBean("IchanEvaTreeMgr");

		if (ChanEvaConstants.TREE_ROOT_ID.equals(nodeId)) {// 如果是根结点，取所有模板分类
			List tplTypeList = chanEvaTreeMgr.listNextLevelNode(nodeId,
					ChanEvaConstants.NODETYPE_TEMPLATETYPE);
			for (Iterator tplTypeIt = tplTypeList.iterator(); tplTypeIt
					.hasNext();) {
				JSONObject jitem = new JSONObject();
				ChanEvaTree chanEvaTree = (ChanEvaTree) tplTypeIt.next();
				jitem.put("id", chanEvaTree.getNodeId());
				jitem.put("text", chanEvaTree.getNodeName());
				jitem.put(UIConstants.JSON_NODETYPE,
						ChanEvaConstants.NODETYPE_TEMPLATETYPE);
				jitem.put("qtip", "【" + ChanEvaConstants.QTIP_TEMPLATETYPE + "】");
				jitem.put(UIConstants.JSON_ICONCLS, "templateType");
				// 根据下面是否有子节点设置是否叶子节点
				if (chanEvaTreeMgr.listNextLevelNode(chanEvaTree.getNodeId(), "")
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
		} else if (ChanEvaConstants.NODETYPE_TEMPLATETYPE.equals(nodeType)) {// 如果是模板分类，取所有模板（目前确定模板只有一级分类）
			IChanEvaTemplateMgr tplMgr = (IChanEvaTemplateMgr) getBean("IchanEvaTemplateMgr");
			List tplList = chanEvaTreeMgr.listNextLevelNode(nodeId,
					ChanEvaConstants.NODETYPE_TEMPLATE);
			for (Iterator tplIt = tplList.iterator(); tplIt.hasNext();) {
				JSONObject jitem = new JSONObject();
				ChanEvaTree chanEvaTree = (ChanEvaTree) tplIt.next();
				jitem.put("id", chanEvaTree.getNodeId());
				jitem.put("text", tplMgr.id2Name(chanEvaTree.getObjectId()) + "模板");
				jitem.put(UIConstants.JSON_NODETYPE,
						ChanEvaConstants.NODETYPE_TEMPLATE);
				jitem.put("qtip", "【" + ChanEvaConstants.QTIP_TEMPLATE + "】");
				jitem.put(UIConstants.JSON_ICONCLS, "template");
				// 根据下面是否有子节点设置是否叶子节点
				if (chanEvaTreeMgr.listNextLevelNode(chanEvaTree.getNodeId(), "")
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
			IChanEvaKpiMgr kpiMgr = (IChanEvaKpiMgr) getBean("IchanEvaKpiMgr");
			List kpiList = chanEvaTreeMgr.listNextLevelNode(nodeId,
					ChanEvaConstants.NODETYPE_KPI);
			for (Iterator kpiIt = kpiList.iterator(); kpiIt.hasNext();) {
				JSONObject jitem = new JSONObject();
				ChanEvaTree chanEvaTree = (ChanEvaTree) kpiIt.next();
				jitem.put("id", chanEvaTree.getNodeId());
				jitem.put("text", kpiMgr.id2Name(chanEvaTree.getObjectId()) + "("
						+ chanEvaTree.getWeight() + "分)");
				jitem.put(UIConstants.JSON_NODETYPE, ChanEvaConstants.NODETYPE_KPI);
				jitem.put("qtip", "【" + ChanEvaConstants.QTIP_KPI + "】");
				// 根据下面是否有子节点设置是否叶子节点
				if (chanEvaTreeMgr.listNextLevelNode(chanEvaTree.getNodeId(), "")
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
		IChanEvaTreeMgr chanEvaTreeMgr = (IChanEvaTreeMgr) getBean("IchanEvaTreeMgr");
		String nodeId = request.getParameter("nodeId");
		ChanEvaTree chanEvaTree = chanEvaTreeMgr.getTreeNodeByNodeId(nodeId);
		ChanEvaTreeForm chanEvaTreeForm = (ChanEvaTreeForm) convert(chanEvaTree);
		updateFormBean(mapping, request, chanEvaTreeForm);
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
		IChanEvaTreeMgr chanEvaTreeMgr = (IChanEvaTreeMgr) getBean("IchanEvaTreeMgr");
		String nodeId = request.getParameter("nodeId");
		chanEvaTreeMgr.removeTreeNodeByNodeId(nodeId);
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
		IChanEvaTreeMgr chanEvaTreeMgr = (IChanEvaTreeMgr) getBean("IchanEvaTreeMgr");
		ChanEvaTree chanEvaTree = new ChanEvaTree();
		ChanEvaTreeForm chanEvaTreeForm = (ChanEvaTreeForm) form;
		if (null == chanEvaTreeForm.getId() || "".equals(chanEvaTreeForm.getId())) {
			chanEvaTree = (ChanEvaTree) convert(chanEvaTreeForm);
			String newNodeId = chanEvaTreeMgr.getMaxNodeId(chanEvaTree
					.getParentNodeId());
			chanEvaTree.setNodeId(newNodeId);
			chanEvaTree.setNodeType(ChanEvaConstants.NODETYPE_KPITYPE);
			chanEvaTree.setLeaf(ChanEvaConstants.NODE_LEAF);
			chanEvaTreeMgr.saveTreeNode(chanEvaTree);
		} else {
			chanEvaTree = chanEvaTreeMgr.getTreeNode(chanEvaTreeForm.getId());
			chanEvaTree.setNodeName(chanEvaTreeForm.getNodeName());
			chanEvaTreeMgr.saveTreeNode(chanEvaTree);
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
		IChanEvaTreeMgr chanEvaTreeMgr = (IChanEvaTreeMgr) getBean("IchanEvaTreeMgr");
		IChanEvaKpiMgr kpiMgr = (IChanEvaKpiMgr) getBean("IchanEvaKpiMgr");
		// 所有子节点
		List list = chanEvaTreeMgr.listNextLevelNode(nodeId, "");
		for (Iterator nodeIter = list.iterator(); nodeIter.hasNext();) {
			ChanEvaTree chanEvaTree = (ChanEvaTree) nodeIter.next();
			JSONObject jitem = new JSONObject();
			// 指标类型
			if (ChanEvaConstants.NODETYPE_KPITYPE.equals(chanEvaTree.getNodeType())) {
				// 判断是否有子节点，决定是否leaf
				boolean leafFlag = true;
				List subNodeList = chanEvaTreeMgr.listNextLevelNode(chanEvaTree
						.getNodeId(), "");
				if (null != subNodeList && 0 < subNodeList.size()) {
					leafFlag = false;
				}
				jitem.put("id", chanEvaTree.getNodeId());
				jitem.put("text", chanEvaTree.getNodeName());
				jitem.put("leaf", leafFlag);
				jitem.put(UIConstants.JSON_ICONCLS, "kpiType");
			}
			// 指标
			else if (ChanEvaConstants.NODETYPE_KPI.equals(chanEvaTree.getNodeType())) {
				ChanEvaKpi kpi = kpiMgr.getKpiByNodeId(chanEvaTree.getNodeId());
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
