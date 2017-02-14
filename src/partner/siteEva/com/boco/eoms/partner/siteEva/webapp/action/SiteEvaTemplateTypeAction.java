package com.boco.eoms.partner.siteEva.webapp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.partner.siteEva.mgr.ISiteEvaTreeMgr;
import com.boco.eoms.partner.siteEva.model.SiteEvaTree;
import com.boco.eoms.partner.siteEva.util.SiteEvaConstants;
import com.boco.eoms.partner.siteEva.webapp.form.SiteEvaTreeForm;

public final class SiteEvaTemplateTypeAction extends BaseAction {

	/**
	 * 新建考核模板分类
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward newTemplateType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String nodeId = request.getParameter("nodeId");
		SiteEvaTreeForm siteEvaTreeForm = (SiteEvaTreeForm) form;
		siteEvaTreeForm.setParentNodeId(nodeId);
		updateFormBean(mapping, request, siteEvaTreeForm);
		return mapping.findForward("newTemplateType");
	}

	/**
	 * 修改模板分类
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editTemplateType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ISiteEvaTreeMgr siteEvaTreeMgr = (ISiteEvaTreeMgr) getBean("IsiteEvaTreeMgr");
		String nodeId = request.getParameter("nodeId"); 
		SiteEvaTree siteEvaTree = siteEvaTreeMgr.getTreeNodeByNodeId(nodeId);
		SiteEvaTreeForm siteEvaTreeForm = (SiteEvaTreeForm) convert(siteEvaTree);
		updateFormBean(mapping, request, siteEvaTreeForm);
		return mapping.findForward("editTemplateType");
	}

	/**
	 * 删除模板分类
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removeTemplateType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ISiteEvaTreeMgr IsiteEvaTreeMgr = (ISiteEvaTreeMgr) getBean("IsiteEvaTreeMgr");
		String nodeId = request.getParameter("nodeId");
		IsiteEvaTreeMgr.removeTreeNodeByNodeId(nodeId);
		return mapping.findForward("success");
	}
	
	/**
	 * 保存模板分类
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveTemplateType(ActionMapping mapping, ActionForm form,
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
			siteEvaTree.setNodeType(SiteEvaConstants.NODETYPE_TEMPLATETYPE);
			siteEvaTree.setLeaf(SiteEvaConstants.NODE_LEAF);
			siteEvaTreeMgr.saveTreeNode(siteEvaTree);
		} else {
			siteEvaTree = siteEvaTreeMgr.getTreeNode(siteEvaTreeForm.getId());
			siteEvaTree.setNodeName(siteEvaTreeForm.getNodeName());
			siteEvaTree.setNodeRemark(siteEvaTreeForm.getNodeRemark());
			siteEvaTreeMgr.saveTreeNode(siteEvaTree);
		}
		return mapping.findForward("success");
	}

}
