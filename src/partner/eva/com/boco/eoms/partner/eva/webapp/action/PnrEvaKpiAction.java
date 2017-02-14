package com.boco.eoms.partner.eva.webapp.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.partner.eva.mgr.IPnrEvaKpiMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaTemplateMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaTreeMgr;
import com.boco.eoms.partner.eva.model.PnrEvaKpi;
import com.boco.eoms.partner.eva.model.PnrEvaTemplate;
import com.boco.eoms.partner.eva.model.PnrEvaTree;
import com.boco.eoms.partner.eva.util.PnrEvaConstants;
import com.boco.eoms.partner.eva.webapp.form.PnrEvaKpiForm;

public final class PnrEvaKpiAction extends BaseAction {

	/**
	 * 未指定方法
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return null;
	}

	/**
	 * 查询某模板选择的所有指标
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listNextLevelKpi(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		IPnrEvaTreeMgr treeMgr = (IPnrEvaTreeMgr) getBean("iPnrEvaTreeMgr");
		IPnrEvaKpiMgr kpiMgr = (IPnrEvaKpiMgr) getBean("iPnrEvaKpiMgr");
		String parentNodeId = "";
		parentNodeId = StaticMethod.null2String(request
				.getParameter("parentNodeId"));
		String areaId = StaticMethod.null2String(request
				.getParameter("areaId"));
		if("".equals(areaId)){
			areaId = StaticMethod.nullObject2String(request.getAttribute("areaId"));
		}
		if ("".equals(parentNodeId)) {
			parentNodeId = StaticMethod.nullObject2String(request
					.getAttribute("parentNodeId"));
		}
		List kpiList = new ArrayList();
		// 查询下级指标
		List nodeList = treeMgr.listNextLevelNode(parentNodeId,
				PnrEvaConstants.NODETYPE_KPI);
		for (Iterator nodeIt = nodeList.iterator(); nodeIt.hasNext();) {
			PnrEvaTree node = (PnrEvaTree) nodeIt.next();
			if(areaId.indexOf(node.getCreatArea())==0){
				PnrEvaKpi kpi = kpiMgr.getKpi(node.getObjectId());
				kpi.setWeight(node.getWeight());
				kpiList.add(kpi);
			}
		}
		Iterator kpiIt = kpiList.iterator();
		request.setAttribute("kpiIt", kpiIt);

		// 计算可分配权重
		float maxWt = treeMgr.getMaxWt(parentNodeId, areaId,PnrEvaConstants.NODETYPE_KPI);
		
		request.setAttribute("maxWt", String.valueOf(maxWt));
		request.setAttribute("minWt", String.valueOf(PnrEvaConstants.DEFAULT_MIN_WT));
		request.setAttribute("areaId", areaId);
		request.setAttribute("parentNodeId", parentNodeId);
		return mapping.findForward("templateKpiList");
	}

	/**
	 * 新建考核指标
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */ 
	public ActionForward newKpi(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String nodeId = StaticMethod
				.null2String(request.getParameter("nodeId"));
		String areaId = StaticMethod.null2String(request
				.getParameter("areaId"));
		//确定权重的范围
		IPnrEvaTreeMgr treeMgr = (IPnrEvaTreeMgr) getBean("iPnrEvaTreeMgr");
		PnrEvaTree tplNode = treeMgr.getTreeNodeByNodeId(nodeId);
		float maxWt = treeMgr.getMaxWt(tplNode.getParentNodeId(), areaId,PnrEvaConstants.NODETYPE_KPI) ;
		request.setAttribute("maxWt", String.valueOf(maxWt));
		float minWt = treeMgr.getMinWt(tplNode.getNodeId(), areaId);
		request.setAttribute("minWt", String.valueOf(minWt));
		
		request.setAttribute("parentNodeId", nodeId);
		request.setAttribute("areaId", areaId);
		return mapping.findForward("newKpi");
	}

	/**
	 * 修改考核指标
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editKpi(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IPnrEvaTreeMgr treeMgr = (IPnrEvaTreeMgr) getBean("iPnrEvaTreeMgr");
		IPnrEvaKpiMgr kpiMgr = (IPnrEvaKpiMgr) getBean("iPnrEvaKpiMgr");
		String nodeIdValue = "";
		String parentNodeId = "";
		String nodeId = "";
		if(request.getParameter("parentNodeId")!=null&&!request.getParameter("parentNodeId").equals("")){
			nodeIdValue = request.getParameter("parentNodeId");
			nodeId = StaticMethod.null2String(nodeIdValue);
			parentNodeId = nodeId;
		}
		else if(request.getParameter("nodeId")!=null&&!request.getParameter("nodeId").equals("")){
			nodeIdValue = request.getParameter("nodeId");
			nodeId = StaticMethod.null2String(nodeIdValue);
			parentNodeId = nodeId.substring(0, nodeId.length()-PnrEvaConstants.NODEID_BETWEEN_LENGTH);
		}
		
		
		// 查询下级指标
		List kpiList = new ArrayList();
		List nodeList = treeMgr.listNextLevelNode(parentNodeId,
				PnrEvaConstants.NODETYPE_KPI);
		for (Iterator nodeIt = nodeList.iterator(); nodeIt.hasNext();) {
			PnrEvaTree node = (PnrEvaTree) nodeIt.next();
			PnrEvaKpi kpi = kpiMgr.getKpi(node.getObjectId());
			kpi.setWeight(node.getWeight());
			kpiList.add(kpi);
		}
		Iterator kpiIt = kpiList.iterator();
		request.setAttribute("kpiIt", kpiIt);
		request.setAttribute("parentNodeId", parentNodeId);

		String kpiId = StaticMethod.null2String(request.getParameter("kpiId"));
		PnrEvaKpi kpi = new PnrEvaKpi();
		
		if (!"".equals(kpiId)) { // 通过列表页面修改指标
			kpi = kpiMgr.getKpi(kpiId);
			PnrEvaTree node = treeMgr.getNodeByObjId(nodeId, kpiId);
			kpi.setWeight(node.getWeight());
		} else { // 通过树节点修改指标
			PnrEvaTree node = treeMgr.getTreeNodeByNodeId(nodeId);
			kpi = kpiMgr.getKpi(node.getObjectId());
			kpi.setWeight(node.getWeight());
		}
		//得到某地域的个性权重
		String areaId = StaticMethod.null2String(request.getParameter("areaId"));
		Map map = treeMgr.listWeightByPNodeIdAndArea(parentNodeId,parentNodeId,PnrEvaConstants.NEXT_CHILD_NODE);
		if(map.get(nodeId)!=null){
			float weight = Float.parseFloat(StaticMethod.nullObject2String((map.get(nodeId))));
			kpi.setWeight(weight);
		}
		PnrEvaKpiForm kpiForm = (PnrEvaKpiForm) convert(kpi);
		updateFormBean(mapping, request, kpiForm);
		//确定指标权重范围

		request.setAttribute("areaId", areaId);
		float maxWt = treeMgr.getMaxWt(parentNodeId, areaId,PnrEvaConstants.NODETYPE_KPI)+kpi.getWeight();
		request.setAttribute("maxWt", String.valueOf(maxWt));
		request.setAttribute("minWt", String.valueOf(PnrEvaConstants.DEFAULT_MIN_WT));
		return mapping.findForward("templateKpiList");
	}

	/**
	 * 保存考核指标
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveKpi(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IPnrEvaKpiMgr kpiMgr = (IPnrEvaKpiMgr) getBean("iPnrEvaKpiMgr");
		String parentNodeId = StaticMethod.null2String(request
				.getParameter("parentNodeId"));

		PnrEvaKpi kpi = new PnrEvaKpi();
		PnrEvaKpiForm kpiForm = (PnrEvaKpiForm) form;
		if (null == kpiForm.getId() || "".equals(kpiForm.getId())) {
			IPnrEvaTreeMgr treeMgr = (IPnrEvaTreeMgr) ApplicationContextHolder.getInstance().getBean("iPnrEvaTreeMgr");
			IPnrEvaTemplateMgr templateMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
			PnrEvaTree node = treeMgr.getTreeNodeByNodeId(parentNodeId);
			PnrEvaTemplate temp = templateMgr.getTemplate(node.getObjectId());
			String isActived = temp.getActivated();
			if(temp==null||temp.getActivated()==null){
				PnrEvaTree tt = null; 
				String nodeId = node.getParentNodeId();
				while(!nodeId.equals("-1")){
					tt = treeMgr.getTreeNodeByNodeId(nodeId);
					if(tt.getNodeType().equals(PnrEvaConstants.NODETYPE_TEMPLATE)){//模版，用于多级指标
						temp = templateMgr.getTemplate(tt.getObjectId());
						isActived = temp.getActivated();
						break;
					}
					else nodeId = tt.getParentNodeId();
				}
			}
			  			
			kpi = (PnrEvaKpi) convert(kpiForm);
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request
					.getSession().getAttribute("sessionform");
			kpi.setCreator(sessionform.getUserid());
			kpi.setCreateTime(StaticMethod.getCurrentDateTime());
			if(kpiMgr.isunique(kpiForm.getKpiName(),parentNodeId).booleanValue()){
				kpiMgr.saveKpiAndNode(kpi, parentNodeId,isActived);
				kpiForm.reset(mapping, request);
			}
			
			else {
				updateFormBean(mapping, request, kpiForm);
				request.setAttribute("fallure", " 指标名称已存在");
			}
			
			
			//若模版已经激活，则必须新增一个模版，这个模版状态没有激活，作为当前激活时间后考核执行时使用。旧模版在激活时间之前使用。2009-8-5

			if(temp.getActivated().equals(PnrEvaConstants.TEMPLATE_ACTIVATED)){//模版激活后，再新增或修改指标，模版得新增一个。
				//新增模版
				templateMgr.newTemplateWithTask(temp, request, parentNodeId);
			}		
			//----------------------------------
			
			
		} else {
			kpi = kpiMgr.getKpi(kpiForm.getId(), PnrEvaConstants.UNDELETED);
			IPnrEvaTreeMgr treeMgr = (IPnrEvaTreeMgr) ApplicationContextHolder.getInstance().getBean("iPnrEvaTreeMgr");
			IPnrEvaTemplateMgr templateMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
			PnrEvaTree node = treeMgr.getTreeNodeByNodeId(parentNodeId);
			PnrEvaTemplate temp = templateMgr.getTemplate(node.getObjectId());
			String isActived  = temp.getActivated();
			if(kpi.getKpiName().equals(kpiForm.getKpiName())){
				kpi.setKpiName(kpiForm.getKpiName());
				kpi.setRemark(kpiForm.getRemark());
				kpi.setWeight(kpiForm.getWeight());				
				kpi.setAlgorithm(kpiForm.getAlgorithm());
				//二期添加的 指标标准值
				kpi.setDefaultValue(kpiForm.getDefaultValue());
				kpiMgr.saveKpiAndNode(kpi, parentNodeId,isActived);
				kpiForm.reset(mapping, request);
			}
			else if(kpiMgr.isunique(kpiForm.getKpiName(),parentNodeId).booleanValue()){
				kpi.setKpiName(kpiForm.getKpiName());
				kpi.setRemark(kpiForm.getRemark());
				kpi.setWeight(kpiForm.getWeight());
				kpi.setAlgorithm(kpiForm.getAlgorithm());
				//二期添加的 指标标准值
				kpi.setDefaultValue(kpiForm.getDefaultValue());
				kpiMgr.saveKpiAndNode(kpi, parentNodeId,isActived);
				kpiForm.reset(mapping, request);
			}
			else{
				updateFormBean(mapping, request, kpiForm);
				request.setAttribute("fallure", " 指标名称已存在");
			}
			
			//若模版已经激活，则必须新增一个模版，这个模版状态没有激活，作为当前激活时间后考核执行时使用。旧模版在激活时间之前使用。2009-8-5

			if(temp.getActivated().equals(PnrEvaConstants.TEMPLATE_ACTIVATED)){//模版激活后，再新增或修改指标，模版得新增一个。
				//新增模版
				templateMgr.newTemplateWithTask(temp, request, parentNodeId);
			}		
			//----------------------------------

		}
		request.setAttribute("parentNodeId", parentNodeId);
		String areaId = StaticMethod.null2String(request.getParameter("area"));
		request.setAttribute("areaId", areaId);
		
		//页面控制需要的模板对应权值
		IPnrEvaTemplateMgr templateMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
//		HashMap evaTWHashMap = new HashMap();
//		evaTWHashMap.put(parentNodeId, templateMgr.getTotalWtOfTemplate(parentNodeId).floatValue()+"");
//		request.getSession().setAttribute("evaTWHashMap", evaTWHashMap);
		request.getSession().setAttribute("evaTW", templateMgr.getTotalWtOfTemplate(parentNodeId).floatValue()+"");
	
		// 保存后跳转回列表页面
		return listNextLevelKpi(mapping, kpiForm, request, response);
	}

	/**
	 * 从树图中删除指标（不删除指标，仅去掉树图中的显示）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delKpiFromTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IPnrEvaKpiMgr kpiMgr = (IPnrEvaKpiMgr) getBean("iPnrEvaKpiMgr");
		IPnrEvaTreeMgr treeMgr = (IPnrEvaTreeMgr) getBean("iPnrEvaTreeMgr");
		String kpiId = StaticMethod.null2String(request.getParameter("kpiId"));
		if ("".equals(kpiId)) { // 从树图执行的删除操作
			String nodeId = StaticMethod.null2String(request
					.getParameter("nodeId"));
			treeMgr.removeTreeNodeByNodeId(nodeId);
			return mapping.findForward("success");
		} else { // 从指标列表页面执行的删除操作
			String parentNodeId = StaticMethod.null2String(request
					.getParameter("parentNodeId"));
			PnrEvaTree delNode = treeMgr.getNodeByObjId(parentNodeId, kpiId);
			treeMgr.removeTreeNodeByNodeId(delNode.getNodeId());
			// 查询下级指标
			List kpiList = new ArrayList();
			List nodeList = treeMgr.listNextLevelNode(parentNodeId,
					PnrEvaConstants.NODETYPE_KPI);
			for (Iterator nodeIt = nodeList.iterator(); nodeIt.hasNext();) {
				PnrEvaTree node = (PnrEvaTree) nodeIt.next();
				PnrEvaKpi kpi = kpiMgr.getKpi(node.getObjectId());
				kpi.setWeight(node.getWeight());
				kpiList.add(kpi);
			}
			Iterator kpiIt = kpiList.iterator();
			request.setAttribute("kpiIt", kpiIt);
			request.setAttribute("parentNodeId", parentNodeId);
			
			// 计算可分配权重
			float minWt = kpiMgr.getMinWt(parentNodeId, "");
			float maxWt = kpiMgr.getMaxWt(parentNodeId, "");
			
			String maxWt2String = String.valueOf(maxWt);
			//判断是否为第一层KPI，如是则maxWt不加限定
			if(treeMgr.isFirstKpiLevel(parentNodeId)){
				maxWt = -1;
				maxWt2String = "任意";
			}
			request.setAttribute("maxWt2String", maxWt2String);
			request.setAttribute("minWt", Float.valueOf(minWt));
			request.setAttribute("maxWt", Float.valueOf(maxWt));

			return mapping.findForward("templateKpiList");
		}
	}
}
