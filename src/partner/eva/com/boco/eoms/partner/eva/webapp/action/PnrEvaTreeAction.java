package com.boco.eoms.partner.eva.webapp.action;

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
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;
import com.boco.eoms.commons.ui.util.JSONUtil;
import com.boco.eoms.commons.ui.util.UIConstants;
import com.boco.eoms.partner.eva.mgr.IPnrEvaKpiMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaTemplateMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaTreeMgr;
import com.boco.eoms.partner.eva.mgr.IPnrEvaWeightMgr;
import com.boco.eoms.partner.eva.model.PnrEvaKpi;
import com.boco.eoms.partner.eva.model.PnrEvaTemplate;
import com.boco.eoms.partner.eva.model.PnrEvaTree;
import com.boco.eoms.partner.eva.model.PnrEvaWeight;
import com.boco.eoms.partner.eva.util.PnrEvaConstants;
import com.boco.eoms.partner.eva.webapp.form.PnrEvaKpiForm;
import com.boco.eoms.partner.eva.webapp.form.PnrEvaTemplateForm;
import com.boco.eoms.partner.eva.webapp.form.PnrEvaTreeForm;

public final class PnrEvaTreeAction extends BaseAction {

	/**
	 * 未指定方法（暂时停用）
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return newNode(mapping, form, request, response);
	}


	/**
	 * 新建考核树节点（指标分类）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward newNode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String areaId = StaticMethod.null2String(request.getParameter("areaId"));
		String nodeId = request.getParameter("nodeId");
		PnrEvaTreeForm evaTreeForm = (PnrEvaTreeForm) form;
		evaTreeForm.setParentNodeId(nodeId);
		evaTreeForm.setCreatArea(areaId);
		updateFormBean(mapping, request, evaTreeForm);
		return mapping.findForward("newNode");
	}
	/**
	 * 生成模板树JSON数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getTemplateNodes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		float sumWeight = 0;
		String partnerNodeType = StaticMethod.null2String(request.getParameter("partnerNodeType"));
		String nodeId = StaticMethod.null2String(request.getParameter("node"));
		String areaId = StaticMethod.null2String(request.getParameter("areaId"));
		JSONArray jsonRoot = new JSONArray();
		IPnrEvaTreeMgr evaTreeMgr = (IPnrEvaTreeMgr) getBean("iPnrEvaTreeMgr");

		if (PnrEvaConstants.TREE_ROOT_ID.equals(nodeId)) {// 如果是根结点，取所有模板分类
			List tplTypeList = evaTreeMgr.listNextLevelNode(nodeId,
					PnrEvaConstants.NODETYPE_TEMPLATETYPE);
			for (Iterator tplTypeIt = tplTypeList.iterator(); tplTypeIt
					.hasNext();) {
				JSONObject jitem = new JSONObject();
				PnrEvaTree evaTree = (PnrEvaTree) tplTypeIt.next();
				jitem.put("id", evaTree.getNodeId());
				jitem.put("text", evaTree.getNodeName());
				jitem.put(UIConstants.JSON_NODETYPE,
						PnrEvaConstants.NODETYPE_TEMPLATETYPE);
				jitem.put("qtip", "【" + PnrEvaConstants.QTIP_TEMPLATETYPE + "】");
				jitem.put(UIConstants.JSON_ICONCLS, "templateType");
				// 根据下面是否有子节点设置是否叶子节点
				if (evaTreeMgr.listNextLevelNode(evaTree.getNodeId(), "")
						.iterator().hasNext()) {
					jitem.put("leaf", false);
				} else {
					jitem.put("leaf", true);
				}
				jitem.put("allowClick", true);
				// 设置右键菜单
				//非节点的建立地域的人员屏蔽新增,修改和删除模板分类菜单,屏蔽新建指标模板
				if(areaId.equals(evaTree.getCreatArea())){
					//如果设置厂家在模板类型上设置,则只能有一级模板类型
					if("templateType".equals(partnerNodeType)){
						jitem.put("alloweditFactury", true);
					}else{
						jitem.put("allownewTemplateType", true);
					}
					jitem.put("alloweditTemplateType", true);
					jitem.put("allownewTemplate", true);
					jitem.put("allowdelTemplateType", true);
				}
				jsonRoot.put(jitem);
			}
		} else {// 如果是模板分类，取所有模板
			IPnrEvaTemplateMgr tplMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
			IPnrEvaKpiMgr kpiMgr = (IPnrEvaKpiMgr) getBean("iPnrEvaKpiMgr");	
			List tplList = evaTreeMgr.listNextNodeByPNodeIdAndArea(nodeId,areaId);
			for (Iterator tplIt = tplList.iterator(); tplIt.hasNext();) {
				JSONObject jitem = new JSONObject();
				PnrEvaTree evaTree = (PnrEvaTree) tplIt.next();
				jitem.put("id", evaTree.getNodeId());
				if(PnrEvaConstants.NODETYPE_TEMPLATETYPE.equals(evaTree.getNodeType())){
					jitem.put("text", evaTree.getNodeName());
					jitem.put(UIConstants.JSON_NODETYPE,
							PnrEvaConstants.NODETYPE_TEMPLATETYPE);
					jitem.put("qtip", "【" + PnrEvaConstants.QTIP_TEMPLATETYPE + "】");
					jitem.put(UIConstants.JSON_ICONCLS, "templateType");
					jitem.put("allowClick", true);
					// 设置右键菜单
					//非节点的建立地域的人员屏蔽新增,修改和删除模板分类菜单,屏蔽新建指标模板
					if(areaId.equals(evaTree.getCreatArea())){
						jitem.put("allownewTemplateType", true);
						jitem.put("alloweditTemplateType", true);
						jitem.put("allownewTemplate", true);
					}	
					jitem.put("allowdelTemplateType", true);
				}else if(PnrEvaConstants.NODETYPE_TEMPLATE.equals(evaTree.getNodeType())){
					PnrEvaTemplate pnrEvaTemplate = tplMgr.getTemplate(evaTree.getObjectId());
					
					if(evaTree.getIsLock().equals(PnrEvaConstants.LOCKED)){
						jitem.put("text", pnrEvaTemplate.getTemplateName() + "模板【"+evaTree.getWeight()+"锁定】");
						sumWeight += evaTree.getWeight();
					}else{
						jitem.put("text", pnrEvaTemplate.getTemplateName() + "模板【"+evaTree.getWeight()+"】");
						sumWeight += evaTree.getWeight();
					}
					jitem.put(UIConstants.JSON_NODETYPE,
							PnrEvaConstants.NODETYPE_TEMPLATE);
					jitem.put("qtip", "【" + PnrEvaConstants.QTIP_TEMPLATE + "】");
					if(pnrEvaTemplate.getActivated().equals(PnrEvaConstants.TEMPLATE_ACTIVATED)){
						jitem.put(UIConstants.JSON_ICONCLS, "templateActive");
					}else{
						jitem.put(UIConstants.JSON_ICONCLS, "template");
					}
					jitem.put("allowClick", true);
					// 设置右键菜单
					//非节点的建立地域的人员屏蔽新增,修改和删除模板菜单,屏蔽新建指标菜单
					if(areaId.equals(evaTree.getCreatArea())){
						

//						jitem.put("allownewKpi", true);
						jitem.put("alloweditTemplate", true);
						jitem.put("allowdelTemplate", true);
						//如果是叶子模板并且是该模板的建立地域则可以配置厂家
						if(pnrEvaTemplate.getLeaf().equals(PnrEvaConstants.NODE_LEAF)){
							jitem.put("allownewKpi", true);
							//如果设置厂家不在模板类型上设置,则设置在叶子模板上
							if(!"templateType".equals(partnerNodeType)){
								jitem.put("alloweditFactury", true);
							}
							//判断下级节点已经建立指标则不能建立模板
							List nextNodeList = evaTreeMgr.listNextNodeByPNodeIdAndArea(evaTree.getNodeId(),areaId);
							if(nextNodeList.size()==0){
								jitem.put("allownewTemplate", true);
							}
						}else{
							jitem.put("allownewTemplate", true);
						}
						
					}
					//如果是该模板建立地域的子地域,当该节点没有锁定时可以修改该节点权重
					ITawSystemAreaManager areaMgr = (ITawSystemAreaManager) getBean("ItawSystemAreaManager");
					TawSystemArea tawSystemArea = areaMgr.getAreaByAreaId(areaId);
					if(tawSystemArea.getParentAreaid().equals(evaTree.getCreatArea())
							&&evaTree.getIsLock().equals(PnrEvaConstants.UNLOCK)
							&&pnrEvaTemplate.getActivated().equals(PnrEvaConstants.TEMPLATE_NOTACTIVATED)){
						jitem.put("alloweditWeight", true);
					}

				}else if(PnrEvaConstants.NODETYPE_KPI.equals(evaTree.getNodeType())){
					if(evaTree.getIsLock().equals(PnrEvaConstants.LOCKED)){
						jitem.put("text", kpiMgr.id2Name(evaTree.getObjectId()) + "指标【"+evaTree.getWeight()+"锁定】");
						sumWeight += evaTree.getWeight();
					}else{
						jitem.put("text", kpiMgr.id2Name(evaTree.getObjectId()) + "指标【"+evaTree.getWeight()+"】");
						sumWeight += evaTree.getWeight();
					}
					jitem.put(UIConstants.JSON_NODETYPE,
							PnrEvaConstants.NODETYPE_KPI);
					jitem.put("qtip", "【" + PnrEvaConstants.QTIP_KPI + "】");
					jitem.put(UIConstants.JSON_ICONCLS, "kpi");
					jitem.put("allowClick", true);
					// 设置右键菜单

					//如果是该模板建立地域的子地域,当该节点没有锁定时可以修改该节点权重
					ITawSystemAreaManager areaMgr = (ITawSystemAreaManager) getBean("ItawSystemAreaManager");
					TawSystemArea tawSystemArea = areaMgr.getAreaByAreaId(areaId);
					PnrEvaTree parentTemplateTree = evaTreeMgr.getLeafNodeBySubNodeIdAndNodeType(nodeId, PnrEvaConstants.NODETYPE_TEMPLATE);
					PnrEvaTemplate parentTemplate = tplMgr.getTemplate(parentTemplateTree.getObjectId());
					if(tawSystemArea.getParentAreaid().equals(evaTree.getCreatArea())
							&&evaTree.getIsLock().equals(PnrEvaConstants.UNLOCK)
							&&parentTemplate.getActivated().equals(PnrEvaConstants.TEMPLATE_NOTACTIVATED)){
						jitem.put("allownewKpi", true);
						jitem.put("alloweditWeight", true);
					}
					//节点的建立地域的人员增加修改和删除指标菜单,所属的叶子模板是未激活状态.
					if(areaId.equals(evaTree.getCreatArea())
							&&parentTemplate.getActivated().equals(PnrEvaConstants.TEMPLATE_NOTACTIVATED)){
						jitem.put("allownewKpi", true);
						jitem.put("alloweditKpi", true);
						jitem.put("allowdelKpi", true);
					}
				}
				// 根据下面是否有子节点设置是否叶子节点
				if (evaTreeMgr.listNextLevelNode(evaTree.getNodeId(), "")
						.iterator().hasNext()) {
					jitem.put("leaf", false);
				} else {
					jitem.put("leaf", true);
				}
				jsonRoot.put(jitem);
			}
			if(sumWeight>100){
				JSONObject jitem = new JSONObject();
				jitem.put("text","！权重和:"+sumWeight+",超过100");
				jitem.put("qtip","您所在地域该级节点权重和超过100，为避免数据错误，请在模板激活前及时通知管理者调整");
				jitem.put(UIConstants.JSON_ICONCLS, "user");
				jitem.put("leaf", true);
				jsonRoot.put(jitem);
			}
		} 
		JSONUtil.print(response, jsonRoot.toString());
		return null;
	}

	/**
	 * 修改节点（指标分类）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editNode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IPnrEvaTreeMgr evaTreeMgr = (IPnrEvaTreeMgr) getBean("iPnrEvaTreeMgr");
		String nodeId = request.getParameter("nodeId");
		PnrEvaTree evaTree = evaTreeMgr.getTreeNodeByNodeId(nodeId);
		PnrEvaTreeForm evaTreeForm = (PnrEvaTreeForm) convert(evaTree);
		updateFormBean(mapping, request, evaTreeForm);
		return mapping.findForward("editNode");
	}

	/**
	 * 显示节点的信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward detailNode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IPnrEvaTreeMgr evaTreeMgr = (IPnrEvaTreeMgr) getBean("iPnrEvaTreeMgr");
		IPnrEvaTemplateMgr evaTemplateMgr = (IPnrEvaTemplateMgr) getBean("iPnrEvaTemplateMgr");
		IPnrEvaWeightMgr evaWeightMgr = (IPnrEvaWeightMgr) getBean("iPnrEvaWeightMgr");
		IPnrEvaKpiMgr evaKpiMgr = (IPnrEvaKpiMgr) getBean("iPnrEvaKpiMgr");
		String nodeId = StaticMethod.null2String(request.getParameter("node"));
		String areaId = StaticMethod.null2String(request.getParameter("areaId"));
		PnrEvaTree evaTree = evaTreeMgr.getTreeNodeByNodeId(nodeId);
		PnrEvaWeight pnrEvaWeight =  evaWeightMgr.getWeight(areaId, nodeId);
		float weight = evaTree.getWeight();
		if(pnrEvaWeight.getId()!=null){
			weight = pnrEvaWeight.getWeight();
		}
		if(PnrEvaConstants.NODETYPE_TEMPLATETYPE.equals(evaTree.getNodeType())){
			PnrEvaTreeForm pnrEvaTreeForm = (PnrEvaTreeForm) convert(evaTree);
			request.setAttribute("pnrEvaTreeForm", pnrEvaTreeForm);
			return mapping.findForward("templateTypeDetail");
		}else if(PnrEvaConstants.NODETYPE_TEMPLATE.equals(evaTree.getNodeType())){
			PnrEvaTemplate template = evaTemplateMgr.getTemplate(evaTree.getObjectId());
			//将tree表信息取出用于修改页面的呈现
			template.setIsLock(evaTree.getIsLock());
			template.setWeight(weight);
			PnrEvaTemplateForm pnrEvaTemplateForm = (PnrEvaTemplateForm) convert(template);
			request.setAttribute("pnrEvaTemplateForm", pnrEvaTemplateForm);
			return mapping.findForward("templateDetail");
		}else if(PnrEvaConstants.NODETYPE_KPI.equals(evaTree.getNodeType())){
			PnrEvaKpi kpi = evaKpiMgr.getKpi(evaTree.getObjectId());
			kpi.setWeight(weight);
			PnrEvaKpiForm pnrEvaKpiForm = (PnrEvaKpiForm) convert(kpi);
			request.setAttribute("pnrEvaKpiForm", pnrEvaKpiForm);
			return mapping.findForward("kpiDetail");
		}
		return mapping.findForward("success");
	}

	/**
	 * 删除节点（包括指标分类下的指标）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removeNode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IPnrEvaTreeMgr evaTreeMgr = (IPnrEvaTreeMgr) getBean("iPnrEvaTreeMgr");
		String nodeId = request.getParameter("nodeId");
		evaTreeMgr.removeTreeNodeByNodeId(nodeId);
		return mapping.findForward("success");
	}
	
	/**
	 * 保存节点（指标分类）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveNode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IPnrEvaTreeMgr evaTreeMgr = (IPnrEvaTreeMgr) getBean("iPnrEvaTreeMgr");
		PnrEvaTree evaTree = new PnrEvaTree();
		PnrEvaTreeForm evaTreeForm = (PnrEvaTreeForm) form;
		if (null == evaTreeForm.getId() || "".equals(evaTreeForm.getId())) {
			evaTree = (PnrEvaTree) convert(evaTreeForm);
			String newNodeId = evaTreeMgr.getMaxNodeId(evaTree
					.getParentNodeId());
			evaTree.setNodeId(newNodeId);
			evaTree.setNodeType(PnrEvaConstants.NODETYPE_KPITYPE);
			evaTree.setIsLock(PnrEvaConstants.UNLOCK);
			evaTree.setLeaf(PnrEvaConstants.NODE_LEAF);
			evaTreeMgr.saveTreeNode(evaTree);
		} else {
			evaTree = evaTreeMgr.getTreeNode(evaTreeForm.getId());
			evaTree.setNodeName(evaTreeForm.getNodeName());
			evaTreeMgr.saveTreeNode(evaTree);
		}
		return mapping.findForward("success");
	}
	
	
}
