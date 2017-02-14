package com.boco.eoms.partner.chanEva.webapp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.partner.chanEva.mgr.IChanEvaTreeMgr;
import com.boco.eoms.partner.chanEva.model.ChanEvaTree;
import com.boco.eoms.partner.chanEva.util.ChanEvaConstants;
import com.boco.eoms.partner.chanEva.webapp.form.ChanEvaTreeForm;

public final class ChanEvaTemplateTypeAction extends BaseAction {

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
		ChanEvaTreeForm chanEvaTreeForm = (ChanEvaTreeForm) form;
		chanEvaTreeForm.setParentNodeId(nodeId);
		updateFormBean(mapping, request, chanEvaTreeForm);
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
		IChanEvaTreeMgr chanEvaTreeMgr = (IChanEvaTreeMgr) getBean("IchanEvaTreeMgr");
		String nodeId = request.getParameter("nodeId"); 
		ChanEvaTree chanEvaTree = chanEvaTreeMgr.getTreeNodeByNodeId(nodeId);
		ChanEvaTreeForm chanEvaTreeForm = (ChanEvaTreeForm) convert(chanEvaTree);
		updateFormBean(mapping, request, chanEvaTreeForm);
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
		IChanEvaTreeMgr IchanEvaTreeMgr = (IChanEvaTreeMgr) getBean("IchanEvaTreeMgr");
		String nodeId = request.getParameter("nodeId");
		IchanEvaTreeMgr.removeTreeNodeByNodeId(nodeId);
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
		IChanEvaTreeMgr chanEvaTreeMgr = (IChanEvaTreeMgr) getBean("IchanEvaTreeMgr");
		ChanEvaTree chanEvaTree = new ChanEvaTree();
		ChanEvaTreeForm chanEvaTreeForm = (ChanEvaTreeForm) form;
		if (null == chanEvaTreeForm.getId() || "".equals(chanEvaTreeForm.getId())) {
			chanEvaTree = (ChanEvaTree) convert(chanEvaTreeForm);
			String newNodeId = chanEvaTreeMgr.getMaxNodeId(chanEvaTree
					.getParentNodeId());
			chanEvaTree.setNodeId(newNodeId);
			chanEvaTree.setNodeType(ChanEvaConstants.NODETYPE_TEMPLATETYPE);
			chanEvaTree.setLeaf(ChanEvaConstants.NODE_LEAF);
			chanEvaTreeMgr.saveTreeNode(chanEvaTree);
		} else {
			chanEvaTree = chanEvaTreeMgr.getTreeNode(chanEvaTreeForm.getId());
			chanEvaTree.setNodeName(chanEvaTreeForm.getNodeName());
			chanEvaTree.setNodeRemark(chanEvaTreeForm.getNodeRemark());
			chanEvaTreeMgr.saveTreeNode(chanEvaTree);
		}
		return mapping.findForward("success");
	}

}
