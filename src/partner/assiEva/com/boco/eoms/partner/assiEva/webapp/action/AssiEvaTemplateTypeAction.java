package com.boco.eoms.partner.assiEva.webapp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.partner.assiEva.mgr.IAssiEvaTreeMgr;
import com.boco.eoms.partner.assiEva.model.AssiEvaTree;
import com.boco.eoms.partner.assiEva.util.AssiEvaConstants;
import com.boco.eoms.partner.assiEva.webapp.form.AssiEvaTreeForm;

public final class AssiEvaTemplateTypeAction extends BaseAction {

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
		AssiEvaTreeForm assiEvaTreeForm = (AssiEvaTreeForm) form;
		assiEvaTreeForm.setParentNodeId(nodeId);
		updateFormBean(mapping, request, assiEvaTreeForm);
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
		IAssiEvaTreeMgr assiEvaTreeMgr = (IAssiEvaTreeMgr) getBean("IassiEvaTreeMgr");
		String nodeId = request.getParameter("nodeId"); 
		AssiEvaTree assiEvaTree = assiEvaTreeMgr.getTreeNodeByNodeId(nodeId);
		AssiEvaTreeForm assiEvaTreeForm = (AssiEvaTreeForm) convert(assiEvaTree);
		updateFormBean(mapping, request, assiEvaTreeForm);
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
		IAssiEvaTreeMgr IassiEvaTreeMgr = (IAssiEvaTreeMgr) getBean("IassiEvaTreeMgr");
		String nodeId = request.getParameter("nodeId");
		IassiEvaTreeMgr.removeTreeNodeByNodeId(nodeId);
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
		IAssiEvaTreeMgr assiEvaTreeMgr = (IAssiEvaTreeMgr) getBean("IassiEvaTreeMgr");
		AssiEvaTree assiEvaTree = new AssiEvaTree();
		AssiEvaTreeForm assiEvaTreeForm = (AssiEvaTreeForm) form;
		if (null == assiEvaTreeForm.getId() || "".equals(assiEvaTreeForm.getId())) {
			assiEvaTree = (AssiEvaTree) convert(assiEvaTreeForm);
			String newNodeId = assiEvaTreeMgr.getMaxNodeId(assiEvaTree
					.getParentNodeId());
			assiEvaTree.setNodeId(newNodeId);
			assiEvaTree.setNodeType(AssiEvaConstants.NODETYPE_TEMPLATETYPE);
			assiEvaTree.setLeaf(AssiEvaConstants.NODE_LEAF);
			assiEvaTreeMgr.saveTreeNode(assiEvaTree);
		} else {
			assiEvaTree = assiEvaTreeMgr.getTreeNode(assiEvaTreeForm.getId());
			assiEvaTree.setNodeName(assiEvaTreeForm.getNodeName());
			assiEvaTree.setNodeRemark(assiEvaTreeForm.getNodeRemark());
			assiEvaTreeMgr.saveTreeNode(assiEvaTree);
		}
		return mapping.findForward("success");
	}

}
