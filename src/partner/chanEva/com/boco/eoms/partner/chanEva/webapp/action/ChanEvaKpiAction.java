package com.boco.eoms.partner.chanEva.webapp.action;

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
import com.boco.eoms.partner.chanEva.mgr.IChanEvaKpiMgr;
import com.boco.eoms.partner.chanEva.mgr.IChanEvaTemplateMgr;
import com.boco.eoms.partner.chanEva.mgr.IChanEvaTreeMgr;
import com.boco.eoms.partner.chanEva.model.ChanEvaKpi;
import com.boco.eoms.partner.chanEva.model.ChanEvaTemplate;
import com.boco.eoms.partner.chanEva.model.ChanEvaTree;
import com.boco.eoms.partner.chanEva.util.ChanEvaConstants;
import com.boco.eoms.partner.chanEva.webapp.form.ChanEvaKpiForm;

public final class ChanEvaKpiAction extends BaseAction {

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
		IChanEvaTreeMgr treeMgr = (IChanEvaTreeMgr) getBean("IchanEvaTreeMgr");
		IChanEvaKpiMgr kpiMgr = (IChanEvaKpiMgr) getBean("IchanEvaKpiMgr");
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
				ChanEvaConstants.NODETYPE_KPI);
		for (Iterator nodeIt = nodeList.iterator(); nodeIt.hasNext();) {
			ChanEvaTree node = (ChanEvaTree) nodeIt.next();
			ChanEvaKpi kpi = kpiMgr.getKpi(node.getObjectId());
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
		ChanEvaTree et = treeMgr.getTreeNodeByNodeId(parentNodeId);
		String type = et.getNodeType();
		if(!ChanEvaConstants.NODETYPE_KPI.equals(type)){
			maxWt = Float.valueOf(-1);
			maxWt2String = "任意";
		}
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
		IChanEvaTreeMgr treeMgr = (IChanEvaTreeMgr) getBean("IchanEvaTreeMgr");
		IChanEvaKpiMgr kpiMgr = (IChanEvaKpiMgr) getBean("IchanEvaKpiMgr");
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
			parentNodeId = nodeId.substring(0, nodeId.length()-ChanEvaConstants.NODEID_BETWEEN_LENGTH);
		}
		
		
		// 查询下级指标
		List kpiList = new ArrayList();
		List nodeList = treeMgr.listNextLevelNode(parentNodeId,
				ChanEvaConstants.NODETYPE_KPI);
		for (Iterator nodeIt = nodeList.iterator(); nodeIt.hasNext();) {
			ChanEvaTree node = (ChanEvaTree) nodeIt.next();
			ChanEvaKpi kpi = kpiMgr.getKpi(node.getObjectId());
			kpi.setWeight(node.getWeight());
			kpiList.add(kpi);
		}
		Iterator kpiIt = kpiList.iterator();
		request.setAttribute("kpiIt", kpiIt);
		request.setAttribute("parentNodeId", parentNodeId);

		String kpiId = StaticMethod.null2String(request.getParameter("kpiId"));
		ChanEvaKpi kpi = new ChanEvaKpi();
		Float maxWt = ChanEvaConstants.DEFAULT_MAX_WT;
		Float minWt = ChanEvaConstants.DEFAULT_MIN_WT;
		if (!"".equals(kpiId)) { // 通过列表页面修改指标
			kpi = kpiMgr.getKpi(kpiId);
			ChanEvaTree node = treeMgr.getNodeByObjId(nodeId, kpiId);
			kpi.setWeight(node.getWeight());
			// 计算剩余可分配权重范围
			maxWt = kpiMgr.getMaxWt(nodeId, kpiId);
			minWt = kpiMgr.getMinWt(nodeId, kpiId);
		} else { // 通过树节点修改指标
			ChanEvaTree node = treeMgr.getTreeNodeByNodeId(nodeId);
			kpi = kpiMgr.getKpi(node.getObjectId());
			kpi.setWeight(node.getWeight());
			// 计算剩余可分配权重范围，此时parentNodeId是要修改节点的nodeId
			maxWt = kpiMgr.getMaxWt(node.getParentNodeId(), node.getObjectId());
			minWt = kpiMgr.getMinWt(node.getParentNodeId(), node.getObjectId());
		}
		ChanEvaKpiForm kpiForm = (ChanEvaKpiForm) convert(kpi);
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
		IChanEvaKpiMgr kpiMgr = (IChanEvaKpiMgr) getBean("IchanEvaKpiMgr");
		String parentNodeId = StaticMethod.null2String(request
				.getParameter("parentNodeId"));

		ChanEvaKpi kpi = new ChanEvaKpi();
		ChanEvaKpiForm kpiForm = (ChanEvaKpiForm) form;
		if (null == kpiForm.getId() || "".equals(kpiForm.getId())) {
			IChanEvaTreeMgr treeMgr = (IChanEvaTreeMgr) ApplicationContextHolder.getInstance().getBean("IchanEvaTreeMgr");
			IChanEvaTemplateMgr templateMgr = (IChanEvaTemplateMgr) getBean("IchanEvaTemplateMgr");
			ChanEvaTree node = treeMgr.getTreeNodeByNodeId(parentNodeId);
			ChanEvaTemplate temp = templateMgr.getTemplate(node.getObjectId());
			String isActived = temp.getActivated();
			if(temp==null||temp.getActivated()==null){
				ChanEvaTree tt = null; 
				String nodeId = node.getParentNodeId();
				while(!nodeId.equals("-1")){
					tt = treeMgr.getTreeNodeByNodeId(nodeId);
					if(tt.getNodeType().equals(ChanEvaConstants.NODETYPE_TEMPLATE)){//模版，用于多级指标
						temp = templateMgr.getTemplate(tt.getObjectId());
						isActived = temp.getActivated();
						break;
					}
					else nodeId = tt.getParentNodeId();
				}
			}
			  			
			kpi = (ChanEvaKpi) convert(kpiForm);
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

			if(temp.getActivated().equals(ChanEvaConstants.TEMPLATE_ACTIVATED)){//模版激活后，再新增或修改指标，模版得新增一个。
				//新增模版
				templateMgr.newTemplateWithTask(temp, request, parentNodeId);
			}		
			//----------------------------------
			
			
		} else {
			kpi = kpiMgr.getKpi(kpiForm.getId(), ChanEvaConstants.UNDELETED);
			IChanEvaTreeMgr treeMgr = (IChanEvaTreeMgr) ApplicationContextHolder.getInstance().getBean("IchanEvaTreeMgr");
			IChanEvaTemplateMgr templateMgr = (IChanEvaTemplateMgr) getBean("IchanEvaTemplateMgr");
			ChanEvaTree node = treeMgr.getTreeNodeByNodeId(parentNodeId);
			ChanEvaTemplate temp = templateMgr.getTemplate(node.getObjectId());
			String isActived  = temp.getActivated();
			if(kpi.getKpiName().equals(kpiForm.getKpiName())){
				kpi.setKpiName(kpiForm.getKpiName());
				kpi.setRemark(kpiForm.getRemark());
				kpi.setWeight(kpiForm.getWeight());
				kpi.setAlgorithm(kpiForm.getAlgorithm());
				kpiMgr.saveKpiAndNode(kpi, parentNodeId,isActived);
				kpiForm.reset(mapping, request);
			}
			else if(kpiMgr.isunique(kpiForm.getKpiName(),parentNodeId).booleanValue()){
				kpi.setKpiName(kpiForm.getKpiName());
				kpi.setRemark(kpiForm.getRemark());
				kpi.setWeight(kpiForm.getWeight());
				kpi.setAlgorithm(kpiForm.getAlgorithm());
				kpiMgr.saveKpiAndNode(kpi, parentNodeId,isActived);
				kpiForm.reset(mapping, request);
			}
			else{
				updateFormBean(mapping, request, kpiForm);
				request.setAttribute("fallure", " 指标名称已存在");
			}
			
			//若模版已经激活，则必须新增一个模版，这个模版状态没有激活，作为当前激活时间后考核执行时使用。旧模版在激活时间之前使用。2009-8-5

			if(temp.getActivated().equals(ChanEvaConstants.TEMPLATE_ACTIVATED)){//模版激活后，再新增或修改指标，模版得新增一个。
				//新增模版
				templateMgr.newTemplateWithTask(temp, request, parentNodeId);
			}		
			//----------------------------------

		}
		request.setAttribute("parentNodeId", parentNodeId);
		
		
		//页面控制需要的模板对应权值
		IChanEvaTemplateMgr templateMgr = (IChanEvaTemplateMgr) getBean("IchanEvaTemplateMgr");
//		HashMap chanEvaTWHashMap = new HashMap();
//		chanEvaTWHashMap.put(parentNodeId, templateMgr.getTotalWtOfTemplate(parentNodeId).floatValue()+"");
//		request.getSession().setAttribute("chanEvaTWHashMap", chanEvaTWHashMap);
		request.getSession().setAttribute("chanEvaTW", templateMgr.getTotalWtOfTemplate(parentNodeId).floatValue()+"");
	
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
		IChanEvaKpiMgr kpiMgr = (IChanEvaKpiMgr) getBean("IchanEvaKpiMgr");
		String nodeId = request.getParameter("nodeId");
		ChanEvaKpi kpi = kpiMgr.getKpiByNodeId(nodeId);
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
		IChanEvaKpiMgr kpiMgr = (IChanEvaKpiMgr) getBean("IchanEvaKpiMgr");
		IChanEvaTreeMgr treeMgr = (IChanEvaTreeMgr) getBean("IchanEvaTreeMgr");
		String kpiId = StaticMethod.null2String(request.getParameter("kpiId"));
		if ("".equals(kpiId)) { // 从树图执行的删除操作
			String nodeId = StaticMethod.null2String(request
					.getParameter("nodeId"));
			treeMgr.removeTreeNodeByNodeId(nodeId);
			return mapping.findForward("success");
		} else { // 从指标列表页面执行的删除操作
			String parentNodeId = StaticMethod.null2String(request
					.getParameter("parentNodeId"));
			ChanEvaTree delNode = treeMgr.getNodeByObjId(parentNodeId, kpiId);
			treeMgr.removeTreeNodeByNodeId(delNode.getNodeId());
			// 查询下级指标
			List kpiList = new ArrayList();
			List nodeList = treeMgr.listNextLevelNode(parentNodeId,
					ChanEvaConstants.NODETYPE_KPI);
			for (Iterator nodeIt = nodeList.iterator(); nodeIt.hasNext();) {
				ChanEvaTree node = (ChanEvaTree) nodeIt.next();
				ChanEvaKpi kpi = kpiMgr.getKpi(node.getObjectId());
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
