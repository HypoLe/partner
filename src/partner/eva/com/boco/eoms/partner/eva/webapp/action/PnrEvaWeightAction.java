package com.boco.eoms.partner.eva.webapp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;
import com.boco.eoms.partner.eva.mgr.IPnrEvaTreeMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaWeightMgr;
import com.boco.eoms.partner.eva.model.PnrEvaTree;
import com.boco.eoms.partner.eva.model.PnrEvaWeight;
import com.boco.eoms.partner.eva.util.PnrEvaConstants;
import com.boco.eoms.partner.eva.webapp.form.PnrEvaWeightForm;

public final class PnrEvaWeightAction extends BaseAction {

	/**
	 * 保存地市个性权重
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveWeight(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PnrEvaWeightForm pnrEvaWeightForm = (PnrEvaWeightForm) form;
		IPnrEvaWeightMgr weightMgr = (IPnrEvaWeightMgr) getBean("iPnrEvaWeightMgr");
		if (pnrEvaWeightForm.getId() == null || "".equals(pnrEvaWeightForm.getId())) {
			PnrEvaWeight weight = new PnrEvaWeight();
			weight.setWeight(pnrEvaWeightForm.getWeight());
			weight.setArea(pnrEvaWeightForm.getArea());
			weight.setNodeId(pnrEvaWeightForm.getNodeId());
			weightMgr.saveWeight(weight);
		} else {
			//先查询出已经存在的记录，做修改操作
			PnrEvaWeight pnrEvaWeight = weightMgr.getWeight(pnrEvaWeightForm.getArea(),pnrEvaWeightForm.getNodeId());
			pnrEvaWeight.setWeight(pnrEvaWeightForm.getWeight());
			weightMgr.updateWeight(pnrEvaWeight);
		}
		return mapping.findForward("success");
	}

	/**
	 * 修改地市个性权重
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editWeight(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IPnrEvaWeightMgr weightMgr = (IPnrEvaWeightMgr) getBean("iPnrEvaWeightMgr");
		ITawSystemAreaManager TawSystemAreaMgr = (ITawSystemAreaManager) getBean("ItawSystemAreaManager");
		String areaId = StaticMethod.nullObject2String(request.getParameter("areaId"));
		String nodeId = StaticMethod.nullObject2String(request.getParameter("nodeId"));
		// 取出地域名称
		String areaName = null;
		TawSystemArea tawSystemArea = TawSystemAreaMgr.getAreaByAreaId(areaId);
		areaName = tawSystemArea.getAreaname();

		PnrEvaWeight pnrEvaWeight = weightMgr.getWeight(areaId,nodeId);
		PnrEvaWeightForm pnrEvaWeightForm = new PnrEvaWeightForm();
		if(pnrEvaWeight.getId()==null){
			pnrEvaWeightForm.setNodeId(nodeId);
			pnrEvaWeightForm.setArea(areaId);
		}else{
			pnrEvaWeightForm = (PnrEvaWeightForm) convert(pnrEvaWeight);
		}
		//增加权重的范围校验
		IPnrEvaTreeMgr treeMgr = (IPnrEvaTreeMgr) getBean("iPnrEvaTreeMgr");
		PnrEvaTree pnrEvaTree = treeMgr.getTreeNodeByNodeId(nodeId);
		String parentNodeId = pnrEvaTree.getParentNodeId();
		float maxWt = 0;
		String nodeType = pnrEvaTree.getNodeType();
		if(pnrEvaWeight.getId()==null){
			maxWt = treeMgr.getMaxWt(parentNodeId, areaId,nodeType) + pnrEvaTree.getWeight();
			pnrEvaWeightForm.setWeight(pnrEvaTree.getWeight());
		}else{
			maxWt = treeMgr.getMaxWt(parentNodeId, areaId,nodeType) + pnrEvaWeight.getWeight();
		}
		request.setAttribute("oldWeight", String.valueOf(pnrEvaTree.getWeight()));
		request.setAttribute("maxWt", String.valueOf(maxWt));
		request.setAttribute("minWt", String.valueOf(PnrEvaConstants.DEFAULT_MIN_WT));
		request.setAttribute("areaName", areaName);
		updateFormBean(mapping, request, pnrEvaWeightForm);
		return mapping.findForward("editWeight");
	}
	
	//此方法暂时预留
	/**
	 * 删除地市个性权重
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removeWeight(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IPnrEvaWeightMgr weightMgr = (IPnrEvaWeightMgr) getBean("iPnrEvaWeightMgr");
		PnrEvaWeightForm pnrEvaWeightForm = (PnrEvaWeightForm) form;
		PnrEvaWeight weight = new PnrEvaWeight();
		weight.setId(pnrEvaWeightForm.getId());
		weightMgr.removeWeight(weight);
		return mapping.findForward("success");
	}

}
