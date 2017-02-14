/**
 * 
 */
package com.boco.eoms.partner.assess.AssTree.webapp.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.log4j.Logger;
import com.boco.eoms.partner.assess.AssTree.mgr.IAssKpiMgr;
import com.boco.eoms.partner.assess.AssTree.mgr.IAssTemplateMgr;
import com.boco.eoms.partner.assess.AssTree.mgr.IAssTreeMgr;
import com.boco.eoms.partner.assess.AssTree.model.AssKpi;
import com.boco.eoms.partner.assess.AssTree.model.AssTemplate;
import com.boco.eoms.partner.assess.AssTree.model.AssTree;
import com.boco.eoms.partner.assess.AssTree.webapp.form.AssKpiForm;
import com.boco.eoms.partner.assess.AssTree.webapp.form.AssTemplateForm;
import com.boco.eoms.partner.assess.AssTree.webapp.form.AssTreeForm;
import com.boco.eoms.partner.assess.AssWeight.mgr.IAssWeightMgr;
import com.boco.eoms.partner.assess.AssWeight.model.AssWeight;
import com.boco.eoms.partner.assess.util.AssConstants;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.ui.util.JSONUtil;
import com.boco.eoms.commons.ui.util.UIConstants;

/**
 * <p>
 * Title:模板管理树图相关处理
 * </p>
 * <p>
 * Description:模板管理树图相关处理
 * </p>
 * <p>
 * Date:Nov 24, 2010 2:29:58 PM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public abstract class AssTreeAction extends BaseAction {
	
	protected String beenNameForTreeMgr = "";
	protected String beenNameForTemplateMgr = "";
	protected String beenNameForKpiMgr = "";
	protected String beenNameForWeightMgr = "IassWeightMgr";
	protected String beenNameForTaskMgr = "";
	protected String templatetypeNodeId = "";
	
	/**
	 * 未指定方法（暂时停用）
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("tree");
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

		IAssTreeMgr assTreeMgr = (IAssTreeMgr) getBean(beenNameForTreeMgr);
		
		String nodeId = StaticMethod.null2String(request.getParameter("node"));
		String nodeType = StaticMethod.null2String(request.getParameter("nodeType"));
		
		JSONArray jsonRoot = new JSONArray();
		if (AssConstants.TREE_ROOT_ID.equals(nodeId)) {// 如果是根结点，取所有模板分类
			List tplTypeList = assTreeMgr.listNextLevelNode(nodeId,
					AssConstants.NODETYPE_TEMPLATETYPE);
			for (Iterator tplTypeIt = tplTypeList.iterator(); tplTypeIt
					.hasNext();) {
				JSONObject jitem = new JSONObject();
				AssTree assEvaTree = (AssTree) tplTypeIt.next();
				if((templatetypeNodeId).equals(assEvaTree.getNodeId())){
				jitem.put("id", assEvaTree.getNodeId());
				jitem.put("text", assEvaTree.getNodeName());
				jitem.put(UIConstants.JSON_NODETYPE,
						AssConstants.NODETYPE_TEMPLATETYPE);
				jitem.put("qtip", "【" + AssConstants.QTIP_TEMPLATETYPE + "】");
				jitem.put(UIConstants.JSON_ICONCLS, "templateType");
				// 根据下面是否有子节点设置是否叶子节点
				if (assTreeMgr.listNextLevelNode(assEvaTree.getNodeId(), "")
						.iterator().hasNext()) {
					jitem.put("leaf", false);
				} else {
					jitem.put("leaf", true);
				}
				// 设置右键菜单
				jitem.put("allownewTemplate", true);
				jitem.put("alloweditTemplateType", true);
				jsonRoot.put(jitem);
				}
			}
		} else if (AssConstants.NODETYPE_TEMPLATETYPE.equals(nodeType)) {// 如果是模板分类，取所有模板（目前确定模板只有一级分类）
			IAssTemplateMgr tplMgr = (IAssTemplateMgr) getBean(beenNameForTemplateMgr);
			List tplList = assTreeMgr.listNextLevelNode(nodeId,
					AssConstants.NODETYPE_TEMPLATE);
			for (Iterator tplIt = tplList.iterator(); tplIt.hasNext();) {
				JSONObject jitem = new JSONObject();
				AssTree assEvaTree = (AssTree) tplIt.next();
				jitem.put("id", assEvaTree.getNodeId());
				jitem.put("text", tplMgr.id2Name(assEvaTree.getObjectId()) + "模板");
				jitem.put(UIConstants.JSON_NODETYPE,
						AssConstants.NODETYPE_TEMPLATE);
				jitem.put("qtip", "【" + AssConstants.QTIP_TEMPLATE + "】");
				jitem.put(UIConstants.JSON_ICONCLS, "template");
				// 根据下面是否有子节点设置是否叶子节点
				if (assTreeMgr.listNextLevelNode(assEvaTree.getNodeId(), "")
						.iterator().hasNext()) {
					jitem.put("leaf", false);
				} else {
					jitem.put("leaf", true);
				}
				// 设置右键菜单
				jitem.put("alloweditFactory", true);
				jitem.put("allownewKpi", true);
				jitem.put("alloweditTemplate", true);
				jitem.put("allowdelTemplate", true);
				jitem.put("alloweditCityWeight", true);
				jsonRoot.put(jitem);
			}
		} else {// 如果是模板或者指标，取所有直属下级指标
			IAssKpiMgr kpiMgr = (IAssKpiMgr) getBean(beenNameForKpiMgr);
			List kpiList = assTreeMgr.listNextLevelNode(nodeId,
					AssConstants.NODETYPE_KPI);
			for (Iterator kpiIt = kpiList.iterator(); kpiIt.hasNext();) {
				JSONObject jitem = new JSONObject();
				AssTree assEvaTree = (AssTree) kpiIt.next();
				jitem.put("id", assEvaTree.getNodeId());
				jitem.put("text", kpiMgr.id2Name(assEvaTree.getObjectId()) + "("
						+ assEvaTree.getWeight() + "分)");
				jitem.put(UIConstants.JSON_NODETYPE, AssConstants.NODETYPE_KPI);
				jitem.put("qtip", "【" + AssConstants.QTIP_KPI + "】");
				// 根据下面是否有子节点设置是否叶子节点
				if (assTreeMgr.listNextLevelNode(assEvaTree.getNodeId(), "")
						.iterator().hasNext()) {
					jitem.put(UIConstants.JSON_ICONCLS, "kpiType");
					jitem.put("leaf", false);
				} else {
					jitem.put(UIConstants.JSON_ICONCLS, "kpi");
					jitem.put("leaf", true);
				}
				// 设置右键菜单
				jitem.put("allownewKpi", true);
				jitem.put("alloweditKpi", true);
				jitem.put("allowdelKpi", true);
				jsonRoot.put(jitem);
			}
		}
		JSONUtil.print(response, jsonRoot.toString());
		return null;
	}

	/**
	 * 新建评估模板分类
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
		AssTreeForm assTreeForm = (AssTreeForm) form;
		assTreeForm.setParentNodeId(nodeId);
		updateFormBean(mapping, request, assTreeForm);
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
		IAssTreeMgr assTreeMgr = (IAssTreeMgr) getBean(beenNameForTreeMgr);
		String nodeId = request.getParameter("nodeId"); 
		AssTree assTree = assTreeMgr.getTreeNodeByNodeId(nodeId);
		AssTreeForm assTreeForm = (AssTreeForm) convert(assTree);
		updateFormBean(mapping, request, assTreeForm);
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
		IAssTreeMgr IassTreeMgr = (IAssTreeMgr) getBean(beenNameForTreeMgr);
		String nodeId = request.getParameter("nodeId");
		IassTreeMgr.removeTreeNodeByNodeId(nodeId);
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
		IAssTreeMgr assTreeMgr = (IAssTreeMgr) getBean(beenNameForTreeMgr);
		AssTree assTree = null;
		AssTreeForm assTreeForm = (AssTreeForm) form;
		if (null == assTreeForm.getId() || "".equals(assTreeForm.getId())) {
			assTree = (AssTree) convert(assTreeForm);
			String newNodeId = assTreeMgr.getMaxNodeId(assTree
					.getParentNodeId());
			assTree.setNodeId(newNodeId);
			assTree.setNodeType(AssConstants.NODETYPE_TEMPLATETYPE);
			assTree.setLeaf(AssConstants.NODE_LEAF);
			assTreeMgr.saveTreeNode(assTree);
		} else {
			assTree = assTreeMgr.getTreeNode(assTreeForm.getId());
			assTree.setNodeName(assTreeForm.getNodeName());
			assTree.setNodeRemark(assTreeForm.getNodeRemark());
			assTreeMgr.saveTreeNode(assTree);
		}
		return mapping.findForward("success");
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
		IAssTreeMgr assTreeMgr = (IAssTreeMgr) getBean(beenNameForTreeMgr);
		IAssTemplateMgr assTemplateMgr = (IAssTemplateMgr) getBean(beenNameForTemplateMgr);
		IAssWeightMgr assWeightMgr = (IAssWeightMgr) getBean(beenNameForWeightMgr);
		IAssKpiMgr assKpiMgr = (IAssKpiMgr) getBean(beenNameForKpiMgr);
		String nodeId = StaticMethod.null2String(request.getParameter("node"));
		String areaId = StaticMethod.null2String(request.getParameter("areaId"));
		AssTree assTree = assTreeMgr.getTreeNodeByNodeId(nodeId);
		AssWeight assWeight =  assWeightMgr.getWeight(areaId, nodeId);
		float weight = assTree.getWeight();
		if(assWeight.getId()!=null){
			weight = assWeight.getWeight();
		}
		if(AssConstants.NODETYPE_TEMPLATETYPE.equals(assTree.getNodeType())){
			AssTreeForm assTreeForm = (AssTreeForm) convert(assTree);
			request.setAttribute("assTreeForm", assTreeForm);
			return mapping.findForward("templateTypeDetail");
		}else if(AssConstants.NODETYPE_TEMPLATE.equals(assTree.getNodeType())){
			AssTemplate template = assTemplateMgr.getTemplate(assTree.getObjectId());
			AssTemplateForm assTemplateForm = (AssTemplateForm) convert(template);
			request.setAttribute("assTemplateForm", assTemplateForm);
			return mapping.findForward("templateDetail");
		}else if(AssConstants.NODETYPE_KPI.equals(assTree.getNodeType())){
			AssKpi kpi = assKpiMgr.getKpi(assTree.getObjectId());
			kpi.setWeight(weight);
			AssKpiForm assKpiForm = (AssKpiForm) convert(kpi);
			request.setAttribute("assKpiForm", assKpiForm);
			return mapping.findForward("kpiDetail");
		}
		return mapping.findForward("success");
	}
	

	/**
	 * 新建评估模板
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward newTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String parentNodeId = StaticMethod.null2String(request
				.getParameter("nodeId"));
		if (parentNodeId == null || "".equals(parentNodeId)) {
			parentNodeId = StaticMethod.null2String(request
					.getParameter("parentNodeId"));
		}

		// 复制需要的信息 add:wangsixuan 2009-2-5
		IAssTreeMgr treeMgr = (IAssTreeMgr) getBean(beenNameForTreeMgr);
		List ttList = treeMgr.listNextLevelNode("1",
				AssConstants.NODETYPE_TEMPLATETYPE);
		request.setAttribute("ttList", ttList);
		String templateTypeId = StaticMethod.null2String(request
				.getParameter("templateTypeId"));
		List tList = new ArrayList();
		if (templateTypeId != null && !"".equals(templateTypeId)) {
			tList = treeMgr.listNextLevelNode(templateTypeId,
					AssConstants.NODETYPE_TEMPLATE);
		}
		request.setAttribute("tList", tList);
		request.setAttribute("templateTypeId", templateTypeId);
		request.setAttribute("parentNodeId", parentNodeId);
		return mapping.findForward("newTemplate");
	}

	/**
	 * 修改评估模板
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AssTemplate template = null;
		String nodeId = StaticMethod
				.null2String(request.getParameter("nodeId"));
		IAssTemplateMgr templateMgr = (IAssTemplateMgr) getBean(beenNameForTemplateMgr);
		if ("".equals(nodeId)) { // 没有取到nodeId说明不是以右键菜单操作为入口
			nodeId = StaticMethod.nullObject2String(request
					.getAttribute("nodeId"));
			AssTemplateForm templateForm = (AssTemplateForm) form;
			updateFormBean(mapping, request, templateForm);
//			template = (AssTemplate) convert(templateForm);
		} else {
			IAssTreeMgr treeMgr = (IAssTreeMgr) getBean(beenNameForTreeMgr);
			AssTree tplNode = treeMgr.getTreeNodeByNodeId(nodeId);
			template = templateMgr.getTemplate(tplNode.getObjectId());
			AssTemplateForm templateForm = (AssTemplateForm) convert(template);
			updateFormBean(mapping, request, templateForm);
		}
		
		request.setAttribute("parentNodeId", nodeId);
		request.getSession().setAttribute("assTW", templateMgr.getTotalWtOfTemplate(nodeId).floatValue()+"");
		return mapping.findForward("editTemplate");
	}

	/**
	 * 保存评估模板
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IAssTemplateMgr templateMgr = (IAssTemplateMgr) getBean(beenNameForTemplateMgr);
		AssTemplate template = null;
		AssTemplateForm templateForm = (AssTemplateForm) form;
		String parentNodeId = StaticMethod.null2String(request
				.getParameter("parentNodeId"));
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		if (null == templateForm.getId() || "".equals(templateForm.getId())) {// 新增模板
			template = (AssTemplate) convert(templateForm);
			template.setCreator(sessionform.getUserid());
			template.setCreateTime(StaticMethod.getCurrentDateTime());
			template.setCreatorOrgId(sessionform.getDeptid());
			// template.setStartTime(AssStaticMethod.getStartTimeByCycle(template
			// .getCycle(), DateTimeUtil
			// .getCurrentDateTime(AssConstants.DATE_FORMAT)));
			// template.setEndTime(AssStaticMethod.getEndTimeByCycle(template
			// .getCycle(), DateTimeUtil
			// .getCurrentDateTime(AssConstants.DATE_FORMAT)));
			template.setOrgType(AssConstants.ORG_DEPT); // orgType暂时作为预留字段，全部设置为dept
			template.setActivated(AssConstants.TEMPLATE_NOTACTIVATED);
			template.setDeleted(AssConstants.UNDELETED);
			template.setProfessional(templateForm.getProfessional());
			template.setDeviceType(templateForm.getDeviceType());
			
			// 所属地市
			String orgIds = StaticMethod.null2String(request
					.getParameter("orgIds"));
			if ("".equals(orgIds)) {
				// 页面上有选择树是否选择的判断，为防止页面控制失效，此处多判断一次
				return mapping.findForward("fail");
			}
			String[] ids = orgIds.split(",");
			templateMgr
					.saveTemplateWithNodeAndTask(template, parentNodeId, ids);
			IAssTreeMgr treeMgr = (IAssTreeMgr) getBean(beenNameForTreeMgr);
			request.setAttribute("nodeId", treeMgr.getNodeByObjId(parentNodeId,
					template.getId()).getNodeId());
		} else { // 修改模板
			template = templateMgr.getTemplate(templateForm.getId());
			template.setTemplateName(templateForm.getTemplateName());
			template.setCycle(templateForm.getCycle());
			// template.setStartTime(AssStaticMethod.getStartTimeByCycle(
			// templateForm.getCycle(), DateTimeUtil
			// .getCurrentDateTime(AssConstants.DATE_FORMAT)));
			// template.setEndTime(AssStaticMethod.getEndTimeByCycle(templateForm
			// .getCycle(), DateTimeUtil
			// .getCurrentDateTime(AssConstants.DATE_FORMAT)));
			template.setRemark(templateForm.getRemark());
			template.setDeviceType(templateForm.getDeviceType());
			// template.setOrgType(templateForm.getOrgType());
			template.setOrgType(AssConstants.ORG_DEPT); // orgType暂时作为预留字段，全部设置为dept
			template.setProfessional(templateForm.getProfessional());

			// 所属地市
			String orgIds = StaticMethod.null2String(request
					.getParameter("orgIds"));
			if ("".equals(orgIds)) {
				// 页面上有选择树是否选择的判断，为防止页面控制失效，此处多判断一次
				return mapping.findForward("fail");
			}
			String[] ids = orgIds.split(",");
			templateMgr.updateTemplate(template, parentNodeId,request, ids);
			request.setAttribute("nodeId", parentNodeId);
			
		}
//		form = (AssTemplateForm) convert(template);

		// 复制模板指标 add:wangsixuan 2009-2-5
		// 先找出tree中以templateId开头的nodeID集合list
		// 根据每个list对应objId在kpi表中查到相关记录，修改用户名和时间并保存
		// 根据新的kpi主键修改tree的list并保存
		String templateId = StaticMethod.null2String(request.getParameter("templateId"));
		if (templateId != null && !"".equals(templateId)) {
			IAssTreeMgr treeMgr2 = (IAssTreeMgr) getBean(beenNameForTreeMgr);
			IAssKpiMgr kpiMgr = (IAssKpiMgr) getBean(beenNameForKpiMgr);

			// 找到新增的节点ID-nodeIdNewSave
			AssTree etree = treeMgr2.getNodeByObjId(parentNodeId, template.getId());
			String nodeIdNewSave = etree.getNodeId();

			// 找到要复制的信息list
			List treeKpiList = treeMgr2.listChildNodes(templateId,
					AssConstants.NODETYPE_KPI, "");
			for (int i = 0; i < treeKpiList.size(); i++) {
				AssTree et = (AssTree) treeKpiList.get(i);
				AssKpi ek = kpiMgr.getKpi(et.getObjectId());

				// 更新指标			
				Logger logger = Logger.getLogger(this.getClass());
			
				ek.setWeight(et.getWeight());
				ek.setCreator(sessionform.getUserid());
				ek.setDeleted(AssConstants.UNDELETED);
				ek.setCreateTime(StaticMethod.getCurrentDateTime());

				AssTree assTree = new AssTree();
				// 生成新节点ID，用nodeIdNewSave替换对应节点ID的前部分
				int nodeLength = nodeIdNewSave.length();
				String newNodeId = nodeIdNewSave
						+ et.getNodeId().substring(nodeLength);
				String newParentNodeId = nodeIdNewSave
						+ et.getParentNodeId().substring(nodeLength);

				// 保存树节点
				assTree.setNodeId(newNodeId);
				assTree.setParentNodeId(newParentNodeId);
				assTree.setNodeName(ek.getKpiName());
				assTree.setNodeType(AssConstants.NODETYPE_KPI);
				assTree.setLeaf(AssConstants.NODE_LEAF);
				assTree.setObjectId(ek.getId());
				assTree.setWeight(ek.getWeight());
				treeMgr2.saveTreeNode(assTree);

				// 更新父节点leaf
				treeMgr2.updateParentNodeLeaf(newParentNodeId,
						AssConstants.NODE_NOTLEAF);
				logger.info("\n进入kpiMgr.saveKpiAndNode");
				kpiMgr.saveKpiAndNode(ek, newParentNodeId,AssConstants.TEMPLATE_ACTIVATED,et.getIsTotal(),et.getOneTotal());//复制模版指标，则新建
			}
		}

		// 保存完成后返回模板编辑页面
		return mapping.findForward("success");
	}

	/**
	 * 删除评估模板（已激活则逻辑删除，仅从树图上删除；未激活则物理删除）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removeTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IAssTemplateMgr tplMgr = (IAssTemplateMgr) getBean(beenNameForTemplateMgr);
		IAssTreeMgr treeMgr = (IAssTreeMgr) getBean(beenNameForTreeMgr);
		String nodeId = request.getParameter("nodeId");
		AssTree tplNode = treeMgr.getTreeNodeByNodeId(nodeId);
		AssTemplate tpl = tplMgr.getTemplate(tplNode.getObjectId());
		if (AssConstants.TEMPLATE_ACTIVATED.equals(tpl.getActivated())) {// 如果模板已激活则逻辑删除
			tplMgr.removeTplLogical(nodeId);
		} else {// 未激活则物理删除
			tplMgr.removeTplPhysical(nodeId);
		}
		return mapping.findForward("success");
	}
	

	/**
	 * 激活模板
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward activeTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String templateId = StaticMethod.null2String(request
				.getParameter("templateId"));
		String tplNodeId = StaticMethod.null2String(request
				.getParameter("nodeId"));
		IAssTemplateMgr templateMgr = (IAssTemplateMgr) getBean(beenNameForTemplateMgr);
		AssTemplate tpl = templateMgr.getTemplate(templateId);
		if ("".equals(templateId) || "".equals(tplNodeId)) {
			String failInfo = "获取模板信息错误！";
			request.setAttribute("failInfo", failInfo);
		} else if (AssConstants.TEMPLATE_ACTIVATED.equals(tpl.getActivated())) {
			String failInfo = "模板已经激活！";
			request.setAttribute("failInfo", failInfo);
		} 
//去掉满分100的限制，富强提出的需求，修改王思轩 20009-2-9
//		else if (PnrEvaConstants.DEFAULT_MAX_WT.floatValue() != templateMgr
//				.getTotalWtOfTemplate(tplNodeId).floatValue()) {
//			String failInfo = "模板下属指标权重和不等于" + PnrEvaConstants.DEFAULT_MAX_WT
//					+ "，请调整后再激活模板！";
//			request.setAttribute("failInfo", failInfo);
//		} 
		else {
			templateMgr.activeTemplate(templateId, tplNodeId);
		}
//		IAssTreeMgr treeMgr = (IAssTreeMgr) getBean(beenNameForTreeMgr);
//		AssTree tplNode = treeMgr.getTreeNodeByNodeId(tplNodeId);
		form = (AssTemplateForm) convert(tpl);
		
		// 操作完成后返回模板编辑页面
		return editTemplate(mapping, form, request, response);
	}

	/**
	 * 新建评估指标
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
	 * 修改评估指标
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
		IAssTreeMgr treeMgr = (IAssTreeMgr) getBean(beenNameForTreeMgr);
		IAssKpiMgr kpiMgr = (IAssKpiMgr) getBean(beenNameForKpiMgr);
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
			parentNodeId = nodeId.substring(0, nodeId.length()-AssConstants.NODEID_BETWEEN_LENGTH);
		}
		
		
		// 查询下级指标
		List kpiList = new ArrayList();
		List nodeList = treeMgr.listNextLevelNode(parentNodeId,
				AssConstants.NODETYPE_KPI);
		for (Iterator nodeIt = nodeList.iterator(); nodeIt.hasNext();) {
			AssTree node = (AssTree) nodeIt.next();
			AssKpi kpi = kpiMgr.getKpi(node.getObjectId());
			kpi.setWeight(node.getWeight());
			kpiList.add(kpi);
		}
		Iterator kpiIt = kpiList.iterator();
		request.setAttribute("kpiIt", kpiIt);
		request.setAttribute("parentNodeId", parentNodeId);

		String kpiId = StaticMethod.null2String(request.getParameter("kpiId"));
		AssKpi kpi = new AssKpi();
		Float maxWt = AssConstants.DEFAULT_MAX_WT;
		Float minWt = AssConstants.DEFAULT_MIN_WT;
		AssTree node = null;
		if (!"".equals(kpiId)) { // 通过列表页面修改指标
			kpi = kpiMgr.getKpi(kpiId);
			node = treeMgr.getNodeByObjId(nodeId, kpiId);
			kpi.setWeight(node.getWeight());
			// 计算剩余可分配权重范围
			maxWt = kpiMgr.getMaxWt(nodeId, kpiId);
			minWt = kpiMgr.getMinWt(nodeId, kpiId);
		} else { // 通过树节点修改指标
			node = treeMgr.getTreeNodeByNodeId(nodeId);
			kpi = kpiMgr.getKpi(node.getObjectId());
			kpi.setWeight(node.getWeight());
			// 计算剩余可分配权重范围，此时parentNodeId是要修改节点的nodeId
			maxWt = kpiMgr.getMaxWt(node.getParentNodeId(), node.getObjectId());
			minWt = kpiMgr.getMinWt(node.getParentNodeId(), node.getObjectId());
		}
		request.setAttribute("oneTotal", kpi.getOneTotal());
		request.setAttribute("isQuote", kpi.getIsQuote());
		request.setAttribute("isTotal", node.getIsTotal());
		AssKpiForm kpiForm = (AssKpiForm) convert(kpi);
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
	 * 保存评估指标
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
		IAssKpiMgr kpiMgr = (IAssKpiMgr) getBean(beenNameForKpiMgr);
		IAssTreeMgr treeMgr = (IAssTreeMgr) ApplicationContextHolder.getInstance().getBean(beenNameForTreeMgr);
		IAssTemplateMgr templateMgr = (IAssTemplateMgr) getBean(beenNameForTemplateMgr);
		String parentNodeId = StaticMethod.null2String(request
				.getParameter("parentNodeId"));
		String isTotal = StaticMethod.null2String(request
				.getParameter("isTotal"));
		String oneTotal = StaticMethod.null2String(request
				.getParameter("oneTotal"));		
		String isQuote = StaticMethod.null2String(request
				.getParameter("isQuote"));		
		String templateNodeId = parentNodeId;
		AssTree node = treeMgr.getTreeNodeByNodeId(parentNodeId);
		for(;!(AssConstants.NODETYPE_TEMPLATE).equals(node.getNodeType())&&!node.getId().equals("-1");){
			node = treeMgr.getTreeNodeByNodeId(node.getParentNodeId());
		}
		AssTemplate temp = templateMgr.getTemplate(node.getObjectId());
		String isActived  = temp.getActivated();
		templateNodeId = node.getNodeId();
		
		AssKpi kpi = null;
		AssKpiForm kpiForm = (AssKpiForm) form;
		if (null == kpiForm.getId() || "".equals(kpiForm.getId())) { 			
			kpi = (AssKpi) convert(kpiForm);
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request
					.getSession().getAttribute("sessionform");
			kpi.setCreator(sessionform.getUserid());
			kpi.setCreateTime(StaticMethod.getCurrentDateTime());
			kpi.setIsQuote(isQuote);
			if(kpiMgr.isunique(kpiForm.getKpiName(),parentNodeId).booleanValue()){
				kpiMgr.saveKpiAndNode(kpi, parentNodeId,isActived,isTotal,oneTotal);
				kpiForm.reset(mapping, request);
			}
			
			else {
				updateFormBean(mapping, request, kpiForm);
				request.setAttribute("fallure", " 指标名称已存在");
			}
			
			
			//若模版已经激活，则必须新增一个模版，这个模版状态没有激活，作为当前激活时间后评估执行时使用。旧模版在激活时间之前使用。2009-8-5

			if(temp.getActivated().equals(AssConstants.TEMPLATE_ACTIVATED)){//模版激活后，再新增或修改指标，模版得新增一个。
				//新增模版
				templateMgr.newTemplateWithTask(temp, request, templateNodeId);
			}		
			//----------------------------------
			
			
		} else {
			kpi = kpiMgr.getKpi(kpiForm.getId(), AssConstants.UNDELETED);	
			if(kpi.getKpiName().equals(kpiForm.getKpiName())){
				kpi.setKpiName(kpiForm.getKpiName());
				kpi.setRemark(kpiForm.getRemark());
				kpi.setWeight(kpiForm.getWeight());
				kpi.setAlgorithm(kpiForm.getAlgorithm());
				kpi.setKpiType(kpiForm.getKpiType());
				kpi.setIsQuote(isQuote);
				kpiMgr.saveKpiAndNode(kpi, parentNodeId,isActived,isTotal,oneTotal);
				kpiForm.reset(mapping, request);
			}
			else if(kpiMgr.isunique(kpiForm.getKpiName(),parentNodeId).booleanValue()){
				kpi.setKpiName(kpiForm.getKpiName());
				kpi.setRemark(kpiForm.getRemark());
				kpi.setWeight(kpiForm.getWeight());
				kpi.setAlgorithm(kpiForm.getAlgorithm());
				kpi.setKpiType(kpiForm.getKpiType());
				kpi.setIsQuote(isQuote);
				kpiMgr.saveKpiAndNode(kpi, parentNodeId,isActived,isTotal,oneTotal);
				kpiForm.reset(mapping, request);
			}
			else{
				updateFormBean(mapping, request, kpiForm);
				request.setAttribute("fallure", " 指标名称已存在");
			}
			
			//若模版已经激活，则必须新增一个模版，这个模版状态没有激活，作为当前激活时间后评估执行时使用。旧模版在激活时间之前使用。2009-8-5

			if(temp.getActivated().equals(AssConstants.TEMPLATE_ACTIVATED)){//模版激活后，再新增或修改指标，模版得新增一个。
				//新增模版
				templateMgr.newTemplateWithTask(temp, request, templateNodeId);
			}		
			//----------------------------------

		}
		request.setAttribute("parentNodeId", parentNodeId);
		
		
		//页面控制需要的模板对应权值
//		HashMap assTWHashMap = new HashMap();
//		assTWHashMap.put(parentNodeId, templateMgr.getTotalWtOfTemplate(parentNodeId).floatValue()+"");
//		request.getSession().setAttribute("assTWHashMap", assTWHashMap);
		request.getSession().setAttribute("assTW", templateMgr.getTotalWtOfTemplate(parentNodeId).floatValue()+"");
	
		// 保存后跳转回列表页面
		return listNextLevelKpi(mapping, kpiForm, request, response);
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
		IAssTreeMgr treeMgr = (IAssTreeMgr) getBean(beenNameForTreeMgr);
		IAssKpiMgr kpiMgr = (IAssKpiMgr) getBean(beenNameForKpiMgr);
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
				AssConstants.NODETYPE_KPI);
		for (Iterator nodeIt = nodeList.iterator(); nodeIt.hasNext();) {
			AssTree node = (AssTree) nodeIt.next();
			AssKpi kpi = kpiMgr.getKpi(node.getObjectId());
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
		AssTree et = treeMgr.getTreeNodeByNodeId(parentNodeId);
		String type = et.getNodeType();
		if(!AssConstants.NODETYPE_KPI.equals(type)){
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
		IAssTreeMgr treeMgr = (IAssTreeMgr) getBean(beenNameForTreeMgr);
		IAssKpiMgr kpiMgr = (IAssKpiMgr) getBean(beenNameForKpiMgr);
		IAssTemplateMgr templateMgr = (IAssTemplateMgr) getBean(beenNameForTemplateMgr);
		String kpiId = StaticMethod.null2String(request.getParameter("kpiId"));
		if ("".equals(kpiId)) { // 从树图执行的删除操作
			String nodeId = StaticMethod.null2String(request
					.getParameter("nodeId"));
			
			AssTree node = treeMgr.getTreeNodeByNodeId(nodeId);
			for(;!(AssConstants.NODETYPE_TEMPLATE).equals(node.getNodeType())&&!node.getId().equals("-1");){
				node = treeMgr.getTreeNodeByNodeId(node.getParentNodeId());
			}			
			AssTemplate temp = templateMgr.getTemplate(node.getObjectId());
			String templateNodeId = node.getNodeId();
			//若模版已经激活，则必须新增一个模版，这个模版状态没有激活，作为当前激活时间后评估执行时使用。旧模版在激活时间之前使用。2009-8-5

			if(temp.getActivated().equals(AssConstants.TEMPLATE_ACTIVATED)){//模版激活后，再新增或修改指标，模版得新增一个。
				//新增模版
				templateMgr.newTemplateWithTask(temp, request, templateNodeId);
			}				
			treeMgr.removeTreeNodeByNodeId(nodeId);
			return mapping.findForward("success");
		} else { // 从指标列表页面执行的删除操作
			String parentNodeId = StaticMethod.null2String(request
					.getParameter("parentNodeId"));

			AssTree nodeAss = treeMgr.getTreeNodeByNodeId(parentNodeId);
			for(;!(AssConstants.NODETYPE_TEMPLATE).equals(nodeAss.getNodeType())&&!nodeAss.getId().equals("-1");){
				nodeAss = treeMgr.getTreeNodeByNodeId(nodeAss.getParentNodeId());
			}			
			AssTemplate temp = templateMgr.getTemplate(nodeAss.getObjectId());
			String templateNodeId = nodeAss.getNodeId();
			//若模版已经激活，则必须新增一个模版，这个模版状态没有激活，作为当前激活时间后评估执行时使用。旧模版在激活时间之前使用。2009-8-5

			if(temp.getActivated().equals(AssConstants.TEMPLATE_ACTIVATED)){//模版激活后，再新增或修改指标，模版得新增一个。
				//新增模版
				templateMgr.newTemplateWithTask(temp, request, templateNodeId);
			}
			
			AssTree delNode = treeMgr.getNodeByObjId(parentNodeId, kpiId);
			treeMgr.removeTreeNodeByNodeId(delNode.getNodeId());
			// 查询下级指标
			List kpiList = new ArrayList();
			List nodeList = treeMgr.listNextLevelNode(parentNodeId,
					AssConstants.NODETYPE_KPI);
			for (Iterator nodeIt = nodeList.iterator(); nodeIt.hasNext();) {
				AssTree node = (AssTree) nodeIt.next();
				AssKpi kpi = kpiMgr.getKpi(node.getObjectId());
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
