package com.boco.eoms.partner.tranEva.webapp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.partner.tranEva.mgr.ITranEvaTreeMgr;
import com.boco.eoms.partner.tranEva.model.TranEvaTree;
import com.boco.eoms.partner.tranEva.util.TranEvaConstants;
import com.boco.eoms.partner.tranEva.webapp.form.TranEvaTreeForm;

public final class TranEvaTemplateTypeAction extends BaseAction {

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
		TranEvaTreeForm tranEvaTreeForm = (TranEvaTreeForm) form;
		tranEvaTreeForm.setParentNodeId(nodeId);
		updateFormBean(mapping, request, tranEvaTreeForm);
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
		ITranEvaTreeMgr tranEvaTreeMgr = (ITranEvaTreeMgr) getBean("ItranEvaTreeMgr");
		String nodeId = request.getParameter("nodeId"); 
		TranEvaTree tranEvaTree = tranEvaTreeMgr.getTreeNodeByNodeId(nodeId);
		TranEvaTreeForm tranEvaTreeForm = (TranEvaTreeForm) convert(tranEvaTree);
		updateFormBean(mapping, request, tranEvaTreeForm);
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
		ITranEvaTreeMgr ItranEvaTreeMgr = (ITranEvaTreeMgr) getBean("ItranEvaTreeMgr");
		String nodeId = request.getParameter("nodeId");
		ItranEvaTreeMgr.removeTreeNodeByNodeId(nodeId);
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
		ITranEvaTreeMgr tranEvaTreeMgr = (ITranEvaTreeMgr) getBean("ItranEvaTreeMgr");
		TranEvaTree tranEvaTree = new TranEvaTree();
		TranEvaTreeForm tranEvaTreeForm = (TranEvaTreeForm) form;
		if (null == tranEvaTreeForm.getId() || "".equals(tranEvaTreeForm.getId())) {
			tranEvaTree = (TranEvaTree) convert(tranEvaTreeForm);
			String newNodeId = tranEvaTreeMgr.getMaxNodeId(tranEvaTree
					.getParentNodeId());
			tranEvaTree.setNodeId(newNodeId);
			tranEvaTree.setNodeType(TranEvaConstants.NODETYPE_TEMPLATETYPE);
			tranEvaTree.setLeaf(TranEvaConstants.NODE_LEAF);
			tranEvaTreeMgr.saveTreeNode(tranEvaTree);
		} else {
			tranEvaTree = tranEvaTreeMgr.getTreeNode(tranEvaTreeForm.getId());
			tranEvaTree.setNodeName(tranEvaTreeForm.getNodeName());
			tranEvaTree.setNodeRemark(tranEvaTreeForm.getNodeRemark());
			tranEvaTreeMgr.saveTreeNode(tranEvaTree);
		}
		return mapping.findForward("success");
	}

}
