package com.boco.eoms.partner.assess.AssFee.webapp.action;

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
import com.boco.eoms.partner.assess.AssFee.mgr.IFeeTreeMgr;
import com.boco.eoms.partner.assess.AssFee.model.FeeTree;
import com.boco.eoms.partner.assess.AssFee.webapp.form.FeeTreeForm;

/**
 * <p>
 * Title:考核线路信息树
 * </p>
 * <p>
 * Description:考核线路信息树
 * </p>
 * <p>
 * Tue Nov 23 08:36:47 CST 2010
 * </p> 
 * 
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
 
 public final class FeeTreeAction extends BaseAction {
 
	/**
	 * 未指定方法时默认调用的方法
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return tree(mapping, form, request, response);
	}
	
	/**
	 * 考核线路信息树树页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward tree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("tree");
	}
 	
 	/**
	 * 生成考核线路信息树树JSON数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getNodes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String nodeId = StaticMethod.null2String(request.getParameter("node"));
		JSONArray jsonRoot = new JSONArray();
		IFeeTreeMgr feeTreeMgr = (IFeeTreeMgr) getBean("feeTreeMgr");
		// 取下级节点
		List list = feeTreeMgr.getNextLevelFeeTrees(nodeId);
		for (Iterator nodeIter = list.iterator(); nodeIter.hasNext();) {
			FeeTree feeTree = (FeeTree) nodeIter.next();
			JSONObject jitem = new JSONObject();
			jitem.put("id", feeTree.getNodeId());
			// 添加节点名称
			jitem.put("text", feeTree.getNodeName());
			// 设置右键菜单
			jitem.put("allowChild", true);
			jitem.put("allowEdit", true);
			jitem.put("allowDelete", true);
			// 设置左键点击可触发action
			jitem.put("allowClick", true);
			// 设置是否为叶子节点
			boolean leafFlag = true;
			if (feeTreeMgr.isHasNextLevel(feeTree.getNodeId())) {
				leafFlag = false;
			}
			jitem.put("leaf", leafFlag);
			// 设置鼠标悬浮提示
//			jitem.put("qtip", your tips here);
			jsonRoot.put(jitem);
		}
		JSONUtil.print(response, jsonRoot.toString());
		return null;
	}
 	
 	/**
	 * 新增考核线路信息树
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String nodeId = StaticMethod.null2String(request.getParameter("node"));
		FeeTreeForm feeTreeForm = (FeeTreeForm) form;
		feeTreeForm.setParentNodeId(nodeId);
		if("1".equals(nodeId)){
			feeTreeForm.setNodeType("sort");
		}else{
			feeTreeForm.setNodeType("lineName");
		}
		updateFormBean(mapping, request, feeTreeForm);
		return mapping.findForward("edit");
	}
	
	/**
	 * 修改考核线路信息树
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String nodeId = StaticMethod.null2String(request.getParameter("node"));
		IFeeTreeMgr feeTreeMgr = (IFeeTreeMgr) getBean("feeTreeMgr");
		FeeTree feeTree = feeTreeMgr.getFeeTreeByNodeId(nodeId);
		FeeTreeForm feeTreeForm = (FeeTreeForm) convert(feeTree);
		updateFormBean(mapping, request, feeTreeForm);
		return mapping.findForward("edit");
	}
	
	/**
	 * 保存考核线路信息树
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IFeeTreeMgr feeTreeMgr = (IFeeTreeMgr) getBean("feeTreeMgr");
		FeeTreeForm feeTreeForm = (FeeTreeForm) form;
		FeeTree feeTree = (FeeTree) convert(feeTreeForm);
		feeTreeMgr.saveFeeTree(feeTree);
		return mapping.findForward("success");
	}
	
	/**
	 * 删除考核线路信息树
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward remove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String nodeId = StaticMethod.null2String(request.getParameter("nodeId"));
		IFeeTreeMgr feeTreeMgr = (IFeeTreeMgr) getBean("feeTreeMgr");
		feeTreeMgr.removeFeeTreeByNodeId(nodeId);
		return mapping.findForward("success");
	}
}