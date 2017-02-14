package com.boco.eoms.partner.lineEva.webapp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.partner.lineEva.mgr.ILineEvaTreeMgr;
import com.boco.eoms.partner.lineEva.model.LineEvaTree;
import com.boco.eoms.partner.lineEva.util.LineEvaConstants;
import com.boco.eoms.partner.lineEva.webapp.form.LineEvaTreeForm;

public final class LineEvaTemplateTypeAction extends BaseAction {

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
		LineEvaTreeForm lineEvaTreeForm = (LineEvaTreeForm) form;
		lineEvaTreeForm.setParentNodeId(nodeId);
		updateFormBean(mapping, request, lineEvaTreeForm);
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
		ILineEvaTreeMgr lineEvaTreeMgr = (ILineEvaTreeMgr) getBean("IlineEvaTreeMgr");
		String nodeId = request.getParameter("nodeId"); 
		LineEvaTree lineEvaTree = lineEvaTreeMgr.getTreeNodeByNodeId(nodeId);
		LineEvaTreeForm lineEvaTreeForm = (LineEvaTreeForm) convert(lineEvaTree);
		updateFormBean(mapping, request, lineEvaTreeForm);
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
		ILineEvaTreeMgr IlineEvaTreeMgr = (ILineEvaTreeMgr) getBean("IlineEvaTreeMgr");
		String nodeId = request.getParameter("nodeId");
		IlineEvaTreeMgr.removeTreeNodeByNodeId(nodeId);
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
		ILineEvaTreeMgr lineEvaTreeMgr = (ILineEvaTreeMgr) getBean("IlineEvaTreeMgr");
		LineEvaTree lineEvaTree = new LineEvaTree();
		LineEvaTreeForm lineEvaTreeForm = (LineEvaTreeForm) form;
		if (null == lineEvaTreeForm.getId() || "".equals(lineEvaTreeForm.getId())) {
			lineEvaTree = (LineEvaTree) convert(lineEvaTreeForm);
			String newNodeId = lineEvaTreeMgr.getMaxNodeId(lineEvaTree
					.getParentNodeId());
			lineEvaTree.setNodeId(newNodeId);
			lineEvaTree.setNodeType(LineEvaConstants.NODETYPE_TEMPLATETYPE);
			lineEvaTree.setLeaf(LineEvaConstants.NODE_LEAF);
			lineEvaTreeMgr.saveTreeNode(lineEvaTree);
		} else {
			lineEvaTree = lineEvaTreeMgr.getTreeNode(lineEvaTreeForm.getId());
			lineEvaTree.setNodeName(lineEvaTreeForm.getNodeName());
			lineEvaTree.setNodeRemark(lineEvaTreeForm.getNodeRemark());
			lineEvaTreeMgr.saveTreeNode(lineEvaTree);
		}
		return mapping.findForward("success");
	}

}
