package com.boco.eoms.partner.assess.AssAlgorithm.webapp.action;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.boco.eoms.partner.assess.AssAlgorithm.mgr.IAssKpiConfigMgr;
import com.boco.eoms.partner.assess.AssAlgorithm.mgr.IAssSelectedInstanceMgr;
import com.boco.eoms.partner.assess.AssAlgorithm.model.AssKpiConfig;
import com.boco.eoms.partner.assess.AssAlgorithm.model.AssSelectedInstance;
import com.boco.eoms.partner.assess.AssAlgorithm.webapp.form.AssKpiConfigForm;
import com.boco.eoms.partner.assess.AssAutoCollection.mgr.AssAutoCollectionMgr;
import com.boco.eoms.partner.assess.AssTree.mgr.IAssKpiMgr;
import com.boco.eoms.partner.assess.AssTree.model.AssKpi;
import com.boco.eoms.partner.assess.util.AssConstants;
import com.boco.eoms.partner.assess.util.AssStaticMethod;

/**
 * <p>
 * Title:指标打分配置
 * </p>
 * <p>
 * Description:指标打分配置
 * </p>
 * <p>
 * Tue Nov 16 09:08:10 CST 2010
 * </p>
 * 
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
 
 public abstract class AssKpiConfigAction extends BaseAction {
	 
	 protected String beenNameForKpiConfigMgr = "";
	 protected String beenNameForSelectedInstanceMgr = "";
	 protected String beenNameForKpiMgr = "";
 
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
	 * 指标打分配置树页面
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
		String kpiId = StaticMethod.null2String(request.getParameter("kpiId"));
		request.setAttribute("kpiId", kpiId);
		return mapping.findForward("tree");
	}
 	
 	/**
	 * 生成指标打分配置树JSON数据
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
		String kpiId = StaticMethod.null2String(request.getParameter("kpiId"));
		String nodeId = StaticMethod.null2String(request.getParameter("node"));
		JSONArray jsonRoot = new JSONArray();
		IAssKpiConfigMgr assKpiConfigMgr = (IAssKpiConfigMgr) getBean(beenNameForKpiConfigMgr);
		// 取下级节点
		StringBuffer whereStr = new StringBuffer();
		whereStr.append(" where 1=1 ");
		whereStr.append(" and  parentNodeId='");
		whereStr.append(nodeId);
		whereStr.append("' and kpiId = '");
		whereStr.append(kpiId);
		whereStr.append("'");
		List list = assKpiConfigMgr.getAssKpiConfigs(whereStr.toString());
		for (Iterator nodeIter = list.iterator(); nodeIter.hasNext();) {
			AssKpiConfig assKpiConfig = (AssKpiConfig) nodeIter.next();
			JSONObject jitem = new JSONObject();
			jitem.put("id", assKpiConfig.getNodeId());
			jitem.put("text", AssStaticMethod.changeCharater(assKpiConfig.getRemark()));
			// 设置右键菜单
			if(assKpiConfig.getNodeId().length()<8){
				jitem.put("allowChild", true);
			}
			jitem.put("allowEdit", true);
			jitem.put("allowDelete", true);
			// 设置左键点击可触发action
			jitem.put("allowClick", true);
			// 设置是否为叶子节点
			boolean leafFlag = true;
			if (assKpiConfigMgr.isHasNextLevel(assKpiConfig.getNodeId())) {
				leafFlag = false;
			}
			jitem.put("leaf", leafFlag);
			// TODO 设置鼠标悬浮提示
//			jitem.put("qtip", your tips here);
			jsonRoot.put(jitem);
		}
		JSONUtil.print(response, jsonRoot.toString());
		return null;
	}
 	
 	/**
	 * 新增指标打分配置
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
		String kpiId = StaticMethod.null2String(request.getParameter("kpiId"));
		AssKpiConfigForm assKpiConfigForm = (AssKpiConfigForm) form;
		assKpiConfigForm.setKpiId(kpiId);
		assKpiConfigForm.setParentNodeId(nodeId);
		IAssKpiConfigMgr assKpiConfigMgr = (IAssKpiConfigMgr) getBean(beenNameForKpiConfigMgr);
		if(!"1".equals(nodeId)){
			AssKpiConfig assKpiConfig = assKpiConfigMgr.getAssKpiConfigByNodeId(nodeId);
			request.setAttribute("algorithmValue", assKpiConfig.getAlgorithm());
			request.setAttribute("parentWeight", assKpiConfig.getWeight());
		}else{
			request.setAttribute("parentWeight", "-1");
		}
		AssAutoCollectionMgr assAutoCollectionMgr = (AssAutoCollectionMgr) getBean("assAutoCollectionMgr");
		List list = (List)assAutoCollectionMgr.getNextLevelAssAutoCollections(AssConstants.TREE_ROOT_ID);
		request.setAttribute("autoTypelist", list);
		updateFormBean(mapping, request, assKpiConfigForm);
		
		return mapping.findForward("edit");
	}
	
	/**
	 * 修改指标打分配置
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
		IAssKpiConfigMgr assKpiConfigMgr = (IAssKpiConfigMgr) getBean(beenNameForKpiConfigMgr);
		AssKpiConfig assKpiConfig = assKpiConfigMgr.getAssKpiConfigByNodeId(nodeId);
		if(nodeId.length()>7){
			AssKpiConfig assKpiConfigParent = assKpiConfigMgr.getAssKpiConfigByNodeId(assKpiConfig.getParentNodeId());
			request.setAttribute("algorithmValue", assKpiConfigParent.getAlgorithm());
			request.setAttribute("parentWeight", assKpiConfigParent.getWeight());
		}else{
			request.setAttribute("parentWeight", "-1");
		}
		AssAutoCollectionMgr assAutoCollectionMgr = (AssAutoCollectionMgr) getBean("assAutoCollectionMgr");
		List list = (List)assAutoCollectionMgr.getNextLevelAssAutoCollections(AssConstants.TREE_ROOT_ID);
		request.setAttribute("autoTypelist", list);		
		AssKpiConfigForm assKpiConfigForm = (AssKpiConfigForm) convert(assKpiConfig);
		updateFormBean(mapping, request, assKpiConfigForm);
		return mapping.findForward("edit");
	}
	
	/**
	 * 保存指标打分配置
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
		IAssKpiConfigMgr assKpiConfigMgr = (IAssKpiConfigMgr) getBean(beenNameForKpiConfigMgr);
		AssKpiConfigForm assKpiConfigForm = (AssKpiConfigForm) form;
		AssKpiConfig assKpiConfig = (AssKpiConfig) convert(assKpiConfigForm);
		assKpiConfigMgr.saveAssKpiConfig(assKpiConfig);
		if("collection".equals(assKpiConfig.getIsCollection())){
			IAssKpiMgr kpiMgr = (IAssKpiMgr) getBean(beenNameForKpiMgr);
			AssKpi assKpi = kpiMgr.getKpi(assKpiConfig.getKpiId());
			assKpi.setIsCollection("collection"); 
			kpiMgr.saveKpi(assKpi);
		}
		return mapping.findForward("success");
	}
	
	/**
	 * 删除指标打分配置
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
		IAssKpiConfigMgr assKpiConfigMgr = (IAssKpiConfigMgr) getBean(beenNameForKpiConfigMgr);
		assKpiConfigMgr.removeAssKpiConfigByNodeId(nodeId);
		return mapping.findForward("success");
	}
	
	/**
	 * 得到某指标对应的指标算法集合
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getConfigsByKpiId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IAssKpiConfigMgr assKpiConfigMgr = (IAssKpiConfigMgr) getBean(beenNameForKpiConfigMgr);
		IAssSelectedInstanceMgr instanceMgr = (IAssSelectedInstanceMgr) getBean(beenNameForSelectedInstanceMgr);
		String nodeId = StaticMethod.null2String(request.getParameter("nodeId"));
		String kpiId = StaticMethod.null2String(request.getParameter("kpiId"));
		String taskId = StaticMethod.null2String(request.getParameter("taskId"));
		String partnerId = StaticMethod.null2String(request.getParameter("partnerId"));
		String time = StaticMethod.null2String(request.getParameter("time"));
		String areaId = StaticMethod.null2String(request.getParameter("areaId"));
		String parNum = StaticMethod.null2String(request.getParameter("parNum"));
		Map instanceMap = instanceMgr.getAssSelectedInstanceMap(kpiId, taskId, areaId, time, partnerId);
		List list = assKpiConfigMgr.getConfigsByKpiId(kpiId,"");
		JSONArray jsonRoot = new JSONArray();
		String tempAlgorithm = "";
		String radioName = "";
		AssSelectedInstance instance = null;
		for(int i=0;i<list.size();i++){
			AssKpiConfig config = (AssKpiConfig)list.get(i);
			JSONObject jitem = new JSONObject();
			if("group".equals(config.getNodeType())){
				String[] numRelation = config.getNumRelation().split(",");
				jitem.put("xtype", "label");
				jitem.put("name", config.getNodeId());
				tempAlgorithm = config.getAlgorithm();
				//判断如果是设备折算时设备数为空则提示错误
//				if("2".equals(tempAlgorithm)&&"".equals(parNum)){
				if("".equals(parNum)){
					JSONArray jsonRootErr = new JSONArray();
					jitem.put("boxLabel", "网络基础信息填写有误或未填写");
					jsonRootErr.put(jitem);
					JSONUtil.print(response, jsonRootErr.toString());
					return null;
				}
				
				radioName = "";
				//判断填写值是否在值域范围内
				boolean showGroup = true;
				if(!"".equals(parNum)){
					showGroup = AssStaticMethod.isInBound(parNum,config.getNumRelation(),config.getNumConfig());
				}
				if("".equals(parNum)&&config.getNumConfig()!=null){
					radioName = config.getId();
				}else if(showGroup){					
					radioName = config.getId();
					jitem.put("id", config.getNodeId());
					jitem.put("kpiId", config.getKpiId());
					jitem.put("boxLabel", config.getRemark());
					jitem.put("style", "font-size:10pt;font-weight:bold");
					jsonRoot.put(jitem);
				}
			}else if("1".equals(tempAlgorithm)&&!"".equals(radioName)){
				jitem.put("xtype", "numberfield");
				jitem.put("name", radioName);
				if(instanceMap.get(radioName)!=null){
					instance = (AssSelectedInstance)instanceMap.get(radioName);
					jitem.put("value", instance.getWeight());
				}
				jitem.put("id", config.getId());
				jitem.put("kpiId", config.getKpiId());
				jitem.put("boxLabel", AssStaticMethod.changeCharater(config.getRemark()));
				jsonRoot.put(jitem);
			}else if("3".equals(tempAlgorithm)&&!"".equals(radioName)){
				jitem.put("xtype", "radio");
				jitem.put("name", radioName);
				if(instanceMap.get(config.getId())!=null){
					jitem.put("checked", "true");
				}
				jitem.put("inputValue", config.getId()+"_"+config.getWeight());
				jitem.put("id", config.getId());
				jitem.put("kpiId", config.getKpiId());
				jitem.put("boxLabel", AssStaticMethod.changeCharater(config.getRemark()));
				jsonRoot.put(jitem);
			}else if("4".equals(tempAlgorithm)&&!"".equals(radioName)){
				jitem.put("xtype", "checkbox");
				jitem.put("name", radioName);
				if(instanceMap.get(config.getId())!=null){
					jitem.put("checked", "true");
				}
				jitem.put("inputValue", config.getId()+"_"+config.getWeight());
				jitem.put("id", config.getId());
				jitem.put("kpiId", config.getKpiId());
				jitem.put("boxLabel", AssStaticMethod.changeCharater(config.getRemark()));
				jsonRoot.put(jitem);
			}else if("6".equals(tempAlgorithm)&&!"".equals(radioName)){
				jitem.put("xtype", "radio");
				jitem.put("name", radioName);
				if(instanceMap.get(config.getId())!=null){
					jitem.put("checked", "true");
				}
				jitem.put("inputValue", config.getId()+"_"+config.getWeight());
				jitem.put("id", config.getId());
				jitem.put("kpiId", config.getKpiId());
				jitem.put("boxLabel", AssStaticMethod.changeCharater(config.getRemark()));
				jsonRoot.put(jitem);
			}else if("7".equals(tempAlgorithm)&&!"".equals(radioName)){
				jitem.put("xtype", "radio");
				jitem.put("name", radioName);
				if(instanceMap.get(config.getId())!=null){
					jitem.put("checked", "true");
				}
				jitem.put("inputValue", config.getId()+"_"+config.getWeight());
				jitem.put("id", config.getId());
				jitem.put("kpiId", config.getKpiId());
				jitem.put("boxLabel", AssStaticMethod.changeCharater(config.getRemark()));
				jsonRoot.put(jitem);
			}else if("8".equals(tempAlgorithm)&&!"".equals(radioName)){
				jitem.put("xtype", "checkbox");
				jitem.put("name", radioName);
				if(instanceMap.get(config.getId())!=null){
					jitem.put("checked", "true");
				}
				jitem.put("inputValue", config.getId()+"_"+config.getWeight());
				jitem.put("id", config.getId());
				jitem.put("kpiId", config.getKpiId());
				jitem.put("boxLabel", AssStaticMethod.changeCharater(config.getRemark()));
				jsonRoot.put(jitem);
			}
			if("group".equals(config.getNodeType())&&("2".equals(tempAlgorithm)||"5".equals(tempAlgorithm))&&!"".equals(radioName)){
				List childNodeslist = assKpiConfigMgr.getChildNodes(config.getNodeId());
				for(int j=0;j<childNodeslist.size();j++){
					AssKpiConfig childConfig = (AssKpiConfig)childNodeslist.get(j);
					JSONObject childItem = new JSONObject();
					childItem.put("xtype", "label");
					childItem.put("name", childConfig.getNodeId());
					childItem.put("id", childConfig.getId());
					childItem.put("kpiId", childConfig.getKpiId());
					childItem.put("boxLabel", childConfig.getRemark());
					childItem.put("style", "font-size:8pt");
					if(!"".equals(childConfig.getRemark())){
						jsonRoot.put(childItem);
					}
				}
				
				JSONObject jitemConvert   = new JSONObject();
				jitemConvert.put("xtype", "numberfield");
				jitemConvert.put("name", radioName);
				if(instanceMap.get(radioName)!=null){
					instance = (AssSelectedInstance)instanceMap.get(radioName);
					java.text.DecimalFormat df =new java.text.DecimalFormat("#.00");
					jitemConvert.put("value", df.format(instance.getWeight()));
				}
				jitemConvert.put("id", config.getId());
				jitemConvert.put("kpiId", config.getKpiId());
				jitemConvert.put("boxLabel", AssStaticMethod.changeCharater(config.getRemark()));
				jsonRoot.put(jitemConvert);
			}
		}
		//增加几个隐含属性
		JSONObject nodeIdItem = new JSONObject();
		nodeIdItem.put("xtype", "textField");
		nodeIdItem.put("id", "nodeId");
		nodeIdItem.put("name", "nodeId");
		nodeIdItem.put("hidden", "true");
		nodeIdItem.put("value", nodeId);
		jsonRoot.put(nodeIdItem);
		JSONObject kpiIdItem = new JSONObject();
		kpiIdItem.put("xtype", "textField");
		kpiIdItem.put("id", "kpiId");
		kpiIdItem.put("name", "kpiId");
		kpiIdItem.put("hidden", "true");
		kpiIdItem.put("value", kpiId);
		jsonRoot.put(kpiIdItem);
		JSONObject taskIdItem = new JSONObject();
		taskIdItem.put("xtype", "textField");
		taskIdItem.put("id", "taskId");
		taskIdItem.put("name", "taskId");
		taskIdItem.put("hidden", "true");
		taskIdItem.put("value", taskId);
		jsonRoot.put(taskIdItem);
		JSONObject partnerIdItem = new JSONObject();
		partnerIdItem.put("xtype", "textField");
		partnerIdItem.put("id", "partnerId");
		partnerIdItem.put("name", "partnerId");
		partnerIdItem.put("hidden", "true");
		partnerIdItem.put("value", partnerId);
		jsonRoot.put(partnerIdItem);
		JSONObject timeItem = new JSONObject();
		timeItem.put("xtype", "textField");
		timeItem.put("id", "time");
		timeItem.put("name", "time");
		timeItem.put("hidden", "true");
		timeItem.put("value", time);
		jsonRoot.put(timeItem);
		JSONObject areaIdItem = new JSONObject();
		areaIdItem.put("xtype", "textField");
		areaIdItem.put("id", "areaId");
		areaIdItem.put("name", "areaId");
		areaIdItem.put("hidden", "true");
		areaIdItem.put("value", areaId);
		jsonRoot.put(areaIdItem);
		JSONObject parNumItem = new JSONObject();
		parNumItem.put("xtype", "textField");
		parNumItem.put("id", "parNum");
		parNumItem.put("name", "parNum");
		parNumItem.put("hidden", "true");
		parNumItem.put("value", parNum);
		jsonRoot.put(parNumItem);
//		System.out.println(jsonRoot.toString());
		JSONUtil.print(response, jsonRoot.toString());
		return null;
	}
}