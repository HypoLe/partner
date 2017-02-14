package com.boco.eoms.partner.eva.webapp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.partner.eva.mgr.IPnrEvaTreeMgr;
import com.boco.eoms.partner.eva.model.PnrEvaTree;
import com.boco.eoms.partner.eva.util.PnrEvaConstants;
import com.boco.eoms.partner.eva.util.PnrEvaDateUtil;
import com.boco.eoms.partner.eva.webapp.form.PnrEvaTreeForm;

public final class PnrEvaTemplateTypeAction extends BaseAction {

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
		String areaId = StaticMethod.null2String(request.getParameter("areaId"));
		String nodeId = request.getParameter("nodeId");
		PnrEvaTreeForm evaTreeForm = (PnrEvaTreeForm) form;
		evaTreeForm.setCreatArea(areaId);
		evaTreeForm.setParentNodeId(nodeId);
		updateFormBean(mapping, request, evaTreeForm);
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
		IPnrEvaTreeMgr evaTreeMgr = (IPnrEvaTreeMgr) getBean("iPnrEvaTreeMgr");
		String nodeId = request.getParameter("nodeId");
		PnrEvaTree evaTree = evaTreeMgr.getTreeNodeByNodeId(nodeId);
		PnrEvaTreeForm evaTreeForm = (PnrEvaTreeForm) convert(evaTree);
		updateFormBean(mapping, request, evaTreeForm);
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
		IPnrEvaTreeMgr evaTreeMgr = (IPnrEvaTreeMgr) getBean("iPnrEvaTreeMgr");
		String nodeId = request.getParameter("nodeId");
		evaTreeMgr.removeTreeNodeByNodeId(nodeId);
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
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		IPnrEvaTreeMgr evaTreeMgr = (IPnrEvaTreeMgr) getBean("iPnrEvaTreeMgr");
		PnrEvaTree evaTree = new PnrEvaTree();
		PnrEvaTreeForm evaTreeForm = (PnrEvaTreeForm) form;
		if (null == evaTreeForm.getId() || "".equals(evaTreeForm.getId())) {
			evaTreeForm.setCreateTime(PnrEvaDateUtil.getTimestamp());
			evaTree = (PnrEvaTree) convert(evaTreeForm);
			String newNodeId = evaTreeMgr.getMaxNodeId(evaTree
					.getParentNodeId());
			evaTree.setNodeId(newNodeId);
			evaTree.setCreateUser(userId);
			evaTree.setNodeType(PnrEvaConstants.NODETYPE_TEMPLATETYPE);
			evaTree.setIsLock(PnrEvaConstants.UNLOCK);
			evaTree.setWeight(0);
			evaTree.setLeaf(PnrEvaConstants.NODE_LEAF);
			evaTreeMgr.saveTreeNode(evaTree);
		} else {
			evaTree = evaTreeMgr.getTreeNode(evaTreeForm.getId());
			evaTree.setNodeName(evaTreeForm.getNodeName());
			evaTree.setNodeRemark(evaTreeForm.getNodeRemark());
			evaTreeMgr.saveTreeNode(evaTree);
		}
		return mapping.findForward("success");
	}

}
