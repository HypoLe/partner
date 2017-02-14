package com.boco.eoms.partner.assess.AssFactory.webapp.action;

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
import com.boco.eoms.partner.assess.AssFactory.mgr.IAssFactoryMgr;
import com.boco.eoms.partner.assess.AssFactory.model.AssFactory;
import com.boco.eoms.partner.assess.AssFactory.webapp.form.AssFactoryForm;
import com.boco.eoms.partner.assess.AssRight.util.AssRoleIdList;
import com.boco.eoms.partner.baseinfo.mgr.AreaDeptTreeMgr;


public abstract class AssFactoryAction extends BaseAction {
	
	protected String beenNameForFactoryMgr = "";
	protected String beenNameForRoleIdList = "lineAssRoleIdList";
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
	public ActionForward editFactory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IAssFactoryMgr factoryMgr = (IAssFactoryMgr) getBean(beenNameForFactoryMgr);
		String partnerNodeType = StaticMethod.nullObject2String(request.getParameter("partnerNodeType"));
		String nodeId = StaticMethod.nullObject2String(request.getParameter("nodeId"));
		// 取出templateId,当厂家设在叶子模板下时记录模板id
		String templateId ="";
		if(!"templateType".equals(partnerNodeType)){
			 templateId = factoryMgr.findTemplateId(nodeId);
		}
		AssFactoryForm assFactoryForm = (AssFactoryForm) form ;
		assFactoryForm.setNodeId(nodeId);
		assFactoryForm.setTemplateId(templateId);

		AssRoleIdList roleIdList = (AssRoleIdList)getBean(beenNameForRoleIdList);
		//定义取出所有厂家的集合，使用公共字典取得数据
//		ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");		
//		ArrayList tawSystemDictType = mgr.getDictSonsByDictid(roleIdList.getFactoryDictType());
//		request.setAttribute("tawSystemDictType", tawSystemDictType);
		//定义取出所有厂家的集合，使用基本信息树图得到数据
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
		List partnerList = areaDeptTreeMgr.getNextLevelAreaDeptTrees(roleIdList.getFactoryDictType());
		request.setAttribute("partnerList", partnerList);
		
		//取出数据库中改模板下的所有记录
		List allFactory = factoryMgr.getAllKpiFactoryByNodeId(nodeId);
		Map allFactoryMap = new HashMap();
		for(int i=0;i<allFactory.size();i++){
			AssFactory assFactory = (AssFactory)allFactory.get(i);
			allFactoryMap.put(assFactory.getFactory(),"true");
		}
		request.setAttribute("allFactoryMap", allFactoryMap);
		request.setAttribute("assFactoryForm", assFactoryForm);
		return mapping.findForward("assFactory");
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
		AssFactoryForm assFactoryForm = (AssFactoryForm) form ;
		IAssFactoryMgr factoryMgr = (IAssFactoryMgr) getBean(beenNameForFactoryMgr);
		ID2NameService mgr = (ID2NameService) getBean("id2nameService");
		
		//删除之前模板节点下的所有记录
		List allKpiFactory = factoryMgr.getAllKpiFactoryByNodeId(assFactoryForm.getNodeId());
		
		Iterator iter = allKpiFactory.iterator(); 
		while (iter.hasNext()) { 
			AssFactory assFactory = (AssFactory) iter.next(); 
			factoryMgr.removeKpiFactory(assFactory);
		}	
		if(assFactoryForm.getFactory()==null||"".equals(assFactoryForm.getFactory())){
			//如果用户没有选择厂家，则直接返回成功页面
			return mapping.findForward("success");
		}
		//循环插入用户所选择的厂家id和厂家名称
		String[] allFactory = request.getParameterValues("factory");
		for(int i = 0 ;i<allFactory.length;i++){
			//用户选择厂家，重新插入新数据
			AssFactory assFactory = new AssFactory();	
			assFactory.setFactoryName(mgr.id2Name(allFactory[i],"partnerDeptDao"));
			assFactory.setNodeId(assFactoryForm.getNodeId());
			assFactory.setTemplateId(assFactoryForm.getTemplateId());
			assFactory.setFactory(allFactory[i]);
			factoryMgr.saveKpiFactory(assFactory);
		}
		return mapping.findForward("success");
	}
	
}
