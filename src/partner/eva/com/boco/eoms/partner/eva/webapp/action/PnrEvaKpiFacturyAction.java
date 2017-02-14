package com.boco.eoms.partner.eva.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.partner.baseinfo.mgr.AreaDeptTreeMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaKpiFacturyMgr;
import com.boco.eoms.partner.eva.model.PnrEvaKpiFactury;
import com.boco.eoms.partner.eva.util.PnrEvaRoleIdList;
import com.boco.eoms.partner.eva.webapp.form.PnrEvaKpiFacturyForm;

public final class PnrEvaKpiFacturyAction extends BaseAction {

	/**
	 * 修改厂商关联
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editFactury(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IPnrEvaKpiFacturyMgr facturyMgr = (IPnrEvaKpiFacturyMgr) getBean("iPnrEvaKpiFacturyMgr");
		String partnerNodeType = StaticMethod.nullObject2String(request.getParameter("partnerNodeType"));
		String nodeId = StaticMethod.nullObject2String(request.getParameter("nodeId"));
		// 取出templateId,当厂家设在叶子模板下时记录模板id
		String templateId ="";
		if(!"templateType".equals(partnerNodeType)){
			 templateId = facturyMgr.findTemplateId(nodeId);
		}
		PnrEvaKpiFacturyForm pnrEvaKpiFacturyForm = (PnrEvaKpiFacturyForm) form ;
		pnrEvaKpiFacturyForm.setNodeId(nodeId);
		pnrEvaKpiFacturyForm.setTemplateId(templateId);

		PnrEvaRoleIdList roleIdList = (PnrEvaRoleIdList)getBean("pnrEvaRoleIdList");
		//定义取出所有厂家的集合，使用公共字典取得数据
//		ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");		
//		ArrayList tawSystemDictType = mgr.getDictSonsByDictid(roleIdList.getFactoryDictType());
//		request.setAttribute("tawSystemDictType", tawSystemDictType);
		//定义取出所有厂家的集合，使用基本信息树图得到数据
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
		List partnerList = areaDeptTreeMgr.getNextLevelAreaDeptTrees(roleIdList.getFactoryDictType());
		request.setAttribute("partnerList", partnerList);
		
		//取出数据库中改模板下的所有记录
		List allFactury = facturyMgr.getAllKpiFacturyByNodeId(nodeId);
		Map allFacturyMap = new HashMap();
		for(int i=0;i<allFactury.size();i++){
			PnrEvaKpiFactury pnrEvaKpiFactury = (PnrEvaKpiFactury)allFactury.get(i);
			allFacturyMap.put(pnrEvaKpiFactury.getFactury(),"true");
		}
		request.setAttribute("allFacturyMap", allFacturyMap);
		request.setAttribute("pnrEvaKpiFacturyForm", pnrEvaKpiFacturyForm);
		return mapping.findForward("editKpiFactury");
	}
	/**
	 * 保存厂商关联
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveFactory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PnrEvaKpiFacturyForm pnrEvaKpiFacturyForm = (PnrEvaKpiFacturyForm) form ;
		IPnrEvaKpiFacturyMgr facturyMgr = (IPnrEvaKpiFacturyMgr) getBean("iPnrEvaKpiFacturyMgr");
		ID2NameService mgr = (ID2NameService) getBean("id2nameService");
		
		//删除之前模板节点下的所有记录
		List allKpiFactury = facturyMgr.getAllKpiFacturyByNodeId(pnrEvaKpiFacturyForm.getNodeId());
		
		Iterator iter = allKpiFactury.iterator(); 
		while (iter.hasNext()) { 
			PnrEvaKpiFactury pnrEvaKpiFactury = (PnrEvaKpiFactury) iter.next(); 
			facturyMgr.removeKpiFactury(pnrEvaKpiFactury);
		}	
		if(pnrEvaKpiFacturyForm.getFactury()==null||"".equals(pnrEvaKpiFacturyForm.getFactury())){
			//如果用户没有选择厂家，则直接返回成功页面
			return mapping.findForward("success");
		}
		//循环插入用户所选择的厂家id和厂家名称
		String[] allFactury = request.getParameterValues("factury");
		for(int i = 0 ;i<allFactury.length;i++){
			//用户选择厂家，重新插入新数据
			PnrEvaKpiFactury pnrEvaKpiFactury = new PnrEvaKpiFactury();	
			pnrEvaKpiFactury.setFacturyName(mgr.id2Name(allFactury[i],"areaDeptTreeDao"));
			pnrEvaKpiFactury.setNodeId(pnrEvaKpiFacturyForm.getNodeId());
			pnrEvaKpiFactury.setTemplateId(pnrEvaKpiFacturyForm.getTemplateId());
			pnrEvaKpiFactury.setFactury(allFactury[i]);
			facturyMgr.saveKpiFactury(pnrEvaKpiFactury);
		}
		return mapping.findForward("success");
	}
	
}
