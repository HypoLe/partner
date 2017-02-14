package com.boco.eoms.eva.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.eva.mgr.IEvaKpiMgr;
import com.boco.eoms.eva.mgr.IEvaTemplateMgr;
import com.boco.eoms.eva.mgr.IEvaTreeMgr;
import com.boco.eoms.eva.model.EvaKpi;
import com.boco.eoms.eva.model.EvaTemplate;
import com.boco.eoms.eva.model.EvaTree;
import com.boco.eoms.eva.util.EvaConstants;
import com.boco.eoms.eva.webapp.form.EvaKpiForm;

public final class EvaKpiAction extends BaseAction {

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
		IEvaTreeMgr treeMgr = (IEvaTreeMgr) getBean("IevaTreeMgr");
		IEvaKpiMgr kpiMgr = (IEvaKpiMgr) getBean("IevaKpiMgr");
		String parentNodeId = "";
		parentNodeId = StaticMethod.null2String(request
				.getParameter("parentNodeId"));
		if ("".equals(parentNodeId)) {
			parentNodeId = StaticMethod.nullObject2String(request
					.getAttribute("parentNodeId"));
		}
		List kpiList = new ArrayList();
		// 查询下级指标
		List nodeList = treeMgr.listNextLevelNode(parentNodeId,
				EvaConstants.NODETYPE_KPI);
		for (Iterator nodeIt = nodeList.iterator(); nodeIt.hasNext();) {
			EvaTree node = (EvaTree) nodeIt.next();
			EvaKpi kpi = kpiMgr.getKpi(node.getObjectId());
			kpi.setWeight(node.getWeight());
			kpiList.add(kpi);
		}
		Iterator kpiIt = kpiList.iterator();
		request.setAttribute("kpiIt", kpiIt);

		// 计算可分配权重
		Float minWt = kpiMgr.getMinWt(parentNodeId, "");
		Float maxWt = kpiMgr.getMaxWt(parentNodeId, "");
		
		String maxWt2String = maxWt.toString();
		//判断是否为第一层KPI，如是则maxWt不加限定
		EvaTree et = treeMgr.getTreeNodeByNodeId(parentNodeId);
		IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
		EvaTemplate template = templateMgr.getTemplate(et.getObjectId());
		String type = et.getNodeType();
		if(!EvaConstants.NODETYPE_KPI.equals(type)){
			maxWt = Float.valueOf(-1);
			maxWt2String = "任意";
		}
		request.setAttribute("activated", template.getActivated());
		request.setAttribute("maxWt2String", maxWt2String);
		request.setAttribute("minWt", minWt);
		request.setAttribute("maxWt", maxWt);
		
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
		request.setAttribute("parentNodeId", nodeId);
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
		IEvaTreeMgr treeMgr = (IEvaTreeMgr) getBean("IevaTreeMgr");
		IEvaKpiMgr kpiMgr = (IEvaKpiMgr) getBean("IevaKpiMgr");
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
			parentNodeId = nodeId.substring(0, nodeId.length()-EvaConstants.NODEID_BETWEEN_LENGTH);
		}
		
		
		// 查询下级指标
		List kpiList = new ArrayList();
		List nodeList = treeMgr.listNextLevelNode(parentNodeId,
				EvaConstants.NODETYPE_KPI);
		for (Iterator nodeIt = nodeList.iterator(); nodeIt.hasNext();) {
			EvaTree node = (EvaTree) nodeIt.next();
			EvaKpi kpi = kpiMgr.getKpi(node.getObjectId());
			kpi.setWeight(node.getWeight());
			kpiList.add(kpi);
		}
		Iterator kpiIt = kpiList.iterator();
		request.setAttribute("kpiIt", kpiIt);
		request.setAttribute("parentNodeId", parentNodeId);

		String kpiId = StaticMethod.null2String(request.getParameter("kpiId"));
		EvaKpi kpi = new EvaKpi();
		Float maxWt = EvaConstants.DEFAULT_MAX_WT;
		Float minWt = EvaConstants.DEFAULT_MIN_WT;
		if (!"".equals(kpiId)) { // 通过列表页面修改指标
			kpi = kpiMgr.getKpi(kpiId);
			EvaTree node = treeMgr.getNodeByObjId(nodeId, kpiId);
			kpi.setWeight(node.getWeight());
			// 计算剩余可分配权重范围
			maxWt = kpiMgr.getMaxWt(nodeId, kpiId);
			minWt = kpiMgr.getMinWt(nodeId, kpiId);
		} else { // 通过树节点修改指标
			EvaTree node = treeMgr.getTreeNodeByNodeId(nodeId);
			kpi = kpiMgr.getKpi(node.getObjectId());
			kpi.setWeight(node.getWeight());
			// 计算剩余可分配权重范围，此时parentNodeId是要修改节点的nodeId
			maxWt = kpiMgr.getMaxWt(node.getParentNodeId(), node.getObjectId());
			minWt = kpiMgr.getMinWt(node.getParentNodeId(), node.getObjectId());
		}
		EvaKpiForm kpiForm = (EvaKpiForm) convert(kpi);
		updateFormBean(mapping, request, kpiForm);
		
		String maxWt2String = maxWt.toString();
		//判断是否为第一层KPI，如是则maxWt不加限定
		if(treeMgr.isFirstKpiLevel(parentNodeId)){
			maxWt = Float.valueOf(-1);
			maxWt2String = "任意";
		}
		request.setAttribute("maxWt2String", maxWt2String);
		request.setAttribute("maxWt", maxWt);
		request.setAttribute("minWt", minWt);
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
		IEvaKpiMgr kpiMgr = (IEvaKpiMgr) getBean("IevaKpiMgr");
		String parentNodeId = StaticMethod.null2String(request
				.getParameter("parentNodeId"));

		EvaKpi kpi = new EvaKpi();
		EvaKpiForm kpiForm = (EvaKpiForm) form;
		if (null == kpiForm.getId() || "".equals(kpiForm.getId())) {
			IEvaTreeMgr treeMgr = (IEvaTreeMgr) ApplicationContextHolder.getInstance().getBean("IevaTreeMgr");
			IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
			EvaTree node = treeMgr.getTreeNodeByNodeId(parentNodeId);
			EvaTemplate temp = templateMgr.getTemplate(node.getObjectId());
			String isActived = temp.getActivated();
			if(temp==null||temp.getActivated()==null){
				EvaTree tt = null; 
				String nodeId = node.getParentNodeId();
				while(!nodeId.equals("-1")){
					tt = treeMgr.getTreeNodeByNodeId(nodeId);
					if(tt.getNodeType().equals(EvaConstants.NODETYPE_TEMPLATE)){//模版，用于多级指标
						temp = templateMgr.getTemplate(tt.getObjectId());
						isActived = temp.getActivated();
						break;
					}
					else nodeId = tt.getParentNodeId();
				}
			}
			  			
			kpi = (EvaKpi) convert(kpiForm);
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

			if(temp.getActivated().equals(EvaConstants.TEMPLATE_ACTIVATED)){//模版激活后，再新增或修改指标，模版得新增一个。
				//新增模版
				templateMgr.newTemplateWithTask(temp, request, parentNodeId);
			}		
			//----------------------------------
			
			
		} else {
			kpi = kpiMgr.getKpi(kpiForm.getId(), EvaConstants.UNDELETED);
			IEvaTreeMgr treeMgr = (IEvaTreeMgr) ApplicationContextHolder.getInstance().getBean("IevaTreeMgr");
			IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
			EvaTree node = treeMgr.getTreeNodeByNodeId(parentNodeId);
			EvaTemplate temp = templateMgr.getTemplate(node.getObjectId());
			String isActived  = temp.getActivated();
			if(kpi.getKpiName().equals(kpiForm.getKpiName())){
				kpi.setKpiName(kpiForm.getKpiName());
				kpi.setRemark(kpiForm.getRemark());
				kpi.setWeight(kpiForm.getWeight());
				kpi.setAlgorithm(kpiForm.getAlgorithm());
				kpi.setCycle(kpiForm.getCycle());
				kpi.setEvaStartTime(kpiForm.getEvaStartTime());
				kpi.setEvaEndTime(kpiForm.getEvaEndTime());
				kpi.setEvaSource(kpiForm.getEvaSource());
				kpiMgr.saveKpiAndNode(kpi, parentNodeId,isActived);
				kpiForm.reset(mapping, request);
			}
			else if(kpiMgr.isunique(kpiForm.getKpiName(),parentNodeId).booleanValue()){
				kpi.setKpiName(kpiForm.getKpiName());
				kpi.setRemark(kpiForm.getRemark());
				kpi.setWeight(kpiForm.getWeight());
				kpi.setAlgorithm(kpiForm.getAlgorithm());
				kpi.setCycle(kpiForm.getCycle());
				kpi.setEvaStartTime(kpiForm.getEvaStartTime());
				kpi.setEvaEndTime(kpiForm.getEvaEndTime());
				kpi.setEvaSource(kpiForm.getEvaSource());
				kpiMgr.saveKpiAndNode(kpi, parentNodeId,isActived);
				kpiForm.reset(mapping, request);
			}
			else{
				updateFormBean(mapping, request, kpiForm);
				request.setAttribute("fallure", " 指标名称已存在");
			}
			
			//若模版已经激活，则必须新增一个模版，这个模版状态没有激活，作为当前激活时间后考核执行时使用。旧模版在激活时间之前使用。2009-8-5

			if(temp.getActivated().equals(EvaConstants.TEMPLATE_ACTIVATED)){//模版激活后，再新增或修改指标，模版得新增一个。
				//新增模版
				templateMgr.newTemplateWithTask(temp, request, parentNodeId);
			}		
			//----------------------------------

		}
		request.setAttribute("parentNodeId", parentNodeId);
		
		
		//页面控制需要的模板对应权值
		IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
//		HashMap evaTWHashMap = new HashMap();
//		evaTWHashMap.put(parentNodeId, templateMgr.getTotalWtOfTemplate(parentNodeId).floatValue()+"");
//		request.getSession().setAttribute("evaTWHashMap", evaTWHashMap);
		request.getSession().setAttribute("evaTW", templateMgr.getTotalWtOfTemplate(parentNodeId).floatValue()+"");
	
		// 保存后跳转回列表页面
		return listNextLevelKpi(mapping, kpiForm, request, response);
	}

	/**
	 * 删除考核指标（暂时停用）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removeKpi(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IEvaKpiMgr kpiMgr = (IEvaKpiMgr) getBean("IevaKpiMgr");
		String nodeId = request.getParameter("nodeId");
		EvaKpi kpi = kpiMgr.getKpiByNodeId(nodeId);
		kpiMgr.removeKpi(kpi);
		return mapping.findForward("success");
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
		IEvaKpiMgr kpiMgr = (IEvaKpiMgr) getBean("IevaKpiMgr");
		IEvaTreeMgr treeMgr = (IEvaTreeMgr) getBean("IevaTreeMgr");
		String kpiId = StaticMethod.null2String(request.getParameter("kpiId"));
		if ("".equals(kpiId)) { // 从树图执行的删除操作
			String nodeId = StaticMethod.null2String(request
					.getParameter("nodeId"));
			treeMgr.removeTreeNodeByNodeId(nodeId);
			return mapping.findForward("success");
		} else { // 从指标列表页面执行的删除操作
			String parentNodeId = StaticMethod.null2String(request
					.getParameter("parentNodeId"));
			EvaTree delNode = treeMgr.getNodeByObjId(parentNodeId, kpiId);
			treeMgr.removeTreeNodeByNodeId(delNode.getNodeId());
			// 查询下级指标
			List kpiList = new ArrayList();
			List nodeList = treeMgr.listNextLevelNode(parentNodeId,
					EvaConstants.NODETYPE_KPI);
			for (Iterator nodeIt = nodeList.iterator(); nodeIt.hasNext();) {
				EvaTree node = (EvaTree) nodeIt.next();
				EvaKpi kpi = kpiMgr.getKpi(node.getObjectId());
				kpi.setWeight(node.getWeight());
				kpiList.add(kpi);
			}
			Iterator kpiIt = kpiList.iterator();
			request.setAttribute("kpiIt", kpiIt);
			request.setAttribute("parentNodeId", parentNodeId);
			
			// 计算可分配权重
			Float minWt = kpiMgr.getMinWt(parentNodeId, "");
			Float maxWt = kpiMgr.getMaxWt(parentNodeId, "");
			
			String maxWt2String = maxWt.toString();
			//判断是否为第一层KPI，如是则maxWt不加限定
			if(treeMgr.isFirstKpiLevel(parentNodeId)){
				maxWt = Float.valueOf(-1);
				maxWt2String = "任意";
			}
			request.setAttribute("maxWt2String", maxWt2String);
			request.setAttribute("minWt", minWt);
			request.setAttribute("maxWt", maxWt);

			return mapping.findForward("templateKpiList");
		}
	}
}