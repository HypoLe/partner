package com.boco.eoms.assEva.webapp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.assEva.mgr.IAssEvaTreeMgr;
import com.boco.eoms.assEva.model.AssEvaTree;
import com.boco.eoms.assEva.util.AssEvaConstants;
import com.boco.eoms.assEva.webapp.form.AssEvaTreeForm;

public final class AssEvaTemplateTypeAction extends BaseAction {

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
		AssEvaTreeForm assEvaTreeForm = (AssEvaTreeForm) form;
		assEvaTreeForm.setParentNodeId(nodeId);
		updateFormBean(mapping, request, assEvaTreeForm);
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
		IAssEvaTreeMgr assEvaTreeMgr = (IAssEvaTreeMgr) getBean("IassEvaTreeMgr");
		String nodeId = request.getParameter("nodeId"); 
		AssEvaTree assEvaTree = assEvaTreeMgr.getTreeNodeByNodeId(nodeId);
		AssEvaTreeForm assEvaTreeForm = (AssEvaTreeForm) convert(assEvaTree);
		updateFormBean(mapping, request, assEvaTreeForm);
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
		IAssEvaTreeMgr IassEvaTreeMgr = (IAssEvaTreeMgr) getBean("IassEvaTreeMgr");
		String nodeId = request.getParameter("nodeId");
		IassEvaTreeMgr.removeTreeNodeByNodeId(nodeId);
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
		IAssEvaTreeMgr assEvaTreeMgr = (IAssEvaTreeMgr) getBean("IassEvaTreeMgr");
		AssEvaTree assEvaTree = new AssEvaTree();
		AssEvaTreeForm assEvaTreeForm = (AssEvaTreeForm) form;
		if (null == assEvaTreeForm.getId() || "".equals(assEvaTreeForm.getId())) {
			assEvaTree = (AssEvaTree) convert(assEvaTreeForm);
			String newNodeId = assEvaTreeMgr.getMaxNodeId(assEvaTree
					.getParentNodeId());
			assEvaTree.setNodeId(newNodeId);
			assEvaTree.setNodeType(AssEvaConstants.NODETYPE_TEMPLATETYPE);
			assEvaTree.setLeaf(AssEvaConstants.NODE_LEAF);
			assEvaTreeMgr.saveTreeNode(assEvaTree);
		} else {
			assEvaTree = assEvaTreeMgr.getTreeNode(assEvaTreeForm.getId());
			assEvaTree.setNodeName(assEvaTreeForm.getNodeName());
			assEvaTree.setNodeRemark(assEvaTreeForm.getNodeRemark());
			assEvaTreeMgr.saveTreeNode(assEvaTree);
		}
		return mapping.findForward("success");
	}

}
