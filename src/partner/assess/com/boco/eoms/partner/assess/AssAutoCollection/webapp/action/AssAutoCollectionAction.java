package com.boco.eoms.partner.assess.AssAutoCollection.webapp.action;

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

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.commons.ui.util.JSONUtil;
import com.boco.eoms.commons.ui.util.UIConstants;
import com.boco.eoms.partner.assess.AssAlgorithm.mgr.IAssKpiConfigMgr;
import com.boco.eoms.partner.assess.AssAlgorithm.mgr.IAssSelectedInstanceMgr;
import com.boco.eoms.partner.assess.AssAlgorithm.model.AssKpiConfig;
import com.boco.eoms.partner.assess.AssAlgorithm.model.AssSelectedInstance;
import com.boco.eoms.partner.assess.AssAutoCollection.mgr.AssAutoCollectionMgr;
import com.boco.eoms.partner.assess.AssAutoCollection.mgr.AssCollectionConfigMgr;
import com.boco.eoms.partner.assess.AssAutoCollection.mgr.AssCollectionQueryJdbcMgr;
import com.boco.eoms.partner.assess.AssAutoCollection.mgr.AssCollectionResultMgr;
import com.boco.eoms.partner.assess.AssAutoCollection.mgr.AssCollectionTypeMgr;
import com.boco.eoms.partner.assess.AssAutoCollection.model.AssAutoCollection;
import com.boco.eoms.partner.assess.AssAutoCollection.model.AssCollectionConfig;
import com.boco.eoms.partner.assess.AssAutoCollection.model.AssCollectionResult;
import com.boco.eoms.partner.assess.AssAutoCollection.model.AssCollectionType;
import com.boco.eoms.partner.assess.AssAutoCollection.webapp.form.AssAutoCollectionForm;
import com.boco.eoms.partner.assess.AssExecute.mgr.IAssKpiInstanceMgr;
import com.boco.eoms.partner.assess.AssExecute.mgr.IAssTaskDetailMgr;
import com.boco.eoms.partner.assess.AssExecute.mgr.IAssTaskMgr;
import com.boco.eoms.partner.assess.AssExecute.model.AssKpiInstance;
import com.boco.eoms.partner.assess.AssExecute.model.AssTask;
import com.boco.eoms.partner.assess.AssExecute.model.AssTaskDetail;
import com.boco.eoms.partner.assess.AssFactory.mgr.IAssFactoryMgr;
import com.boco.eoms.partner.assess.AssReport.mgr.IAssReportInfoMgr;
import com.boco.eoms.partner.assess.AssReport.model.AssReportInfo;
import com.boco.eoms.partner.assess.AssRight.util.AssRoleIdList;
import com.boco.eoms.partner.assess.AssTree.mgr.IAssKpiMgr;
import com.boco.eoms.partner.assess.AssTree.mgr.IAssTemplateMgr;
import com.boco.eoms.partner.assess.AssTree.mgr.IAssTreeMgr;
import com.boco.eoms.partner.assess.AssTree.model.AssKpi;
import com.boco.eoms.partner.assess.AssTree.model.AssTemplate;
import com.boco.eoms.partner.assess.AssTree.model.AssTree;
import com.boco.eoms.partner.assess.util.AssConstants;
import com.boco.eoms.partner.assess.util.AssStaticMethod;
import com.boco.eoms.partner.assess.util.DateTimeUtil;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.deviceAssess.util.BackEstimateConstants;
import com.sun.org.apache.xpath.internal.operations.And;

/**
 * <p>
 * Title:kpi自动采集 
 * </p>
 * <p>
 * Description:kpi自动采集
 * </p> 
 * <p>
 * Tue Mar 29 17:39:54 CST 2011
 * </p>
 * 
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
 
 public final class AssAutoCollectionAction extends BaseAction { 
 
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
	 * kpi自动采集树页面
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
	 * 生成kpi自动采集树JSON数据
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
		String nodeType = StaticMethod.null2String(request.getParameter("nodeType"));
		JSONArray jsonRoot = new JSONArray();
		AssAutoCollectionMgr assAutoCollectionMgr = (AssAutoCollectionMgr) getBean("assAutoCollectionMgr");
		if (AssConstants.TREE_ROOT_ID.equals(nodeId)) {// 如果是根结点，取所有采集指标分类
			// 取下级节点
			List list = assAutoCollectionMgr.getNextLevelAssAutoCollections(nodeId);
			for (Iterator nodeIter = list.iterator(); nodeIter.hasNext();) {
				AssAutoCollection assAutoCollection = (AssAutoCollection) nodeIter.next();
				JSONObject jitem = new JSONObject();
				jitem.put("id", assAutoCollection.getNodeId());
				// 添加节点名称
				jitem.put("text", assAutoCollection.getNodeName());
				// 设置右键菜单delnode
				jitem.put("allownewCollection", true);
				jitem.put("alloweditNode", true);
//				jitem.put("allowdelNode", true);
				
				jitem.put(UIConstants.JSON_NODETYPE,"type");
				// 设置是否为叶子节点
				boolean leafFlag = true;
				if (assAutoCollectionMgr.isHasNextLevel(assAutoCollection.getNodeId())) {
					leafFlag = false;
				}
				jitem.put("leaf", leafFlag);
				jsonRoot.put(jitem);
			}
		} else if("type".equals(nodeType)){// 如果是指标分类，取所有采集指标配置
			// 取下级节点
			List list = assAutoCollectionMgr.getNextLevelAssAutoCollections(nodeId);
			for (Iterator nodeIter = list.iterator(); nodeIter.hasNext();) {
				AssAutoCollection assAutoCollection = (AssAutoCollection) nodeIter.next();
				JSONObject jitem = new JSONObject();
				jitem.put("id", assAutoCollection.getNodeId());
				// 添加节点名称
				jitem.put("text", assAutoCollection.getNodeName());
				// 设置右键菜单
				
				jitem.put("alloweditCollection", true);
//				jitem.put("allowdelCollection", true);
				jitem.put(UIConstants.JSON_NODETYPE,"sqlConfig");
				// 设置是否为叶子节点
				boolean leafFlag = true;
				if (assAutoCollectionMgr.isHasNextLevel(assAutoCollection.getNodeId())) {
					leafFlag = false;
				}
				jitem.put("leaf", leafFlag);
				// 设置鼠标悬浮提示
//				jitem.put("qtip", your tips here);
				jsonRoot.put(jitem);
			}
		} 
		
		JSONUtil.print(response, jsonRoot.toString());
		return null;
	}
 	
 	/**
	 * 新增kpi自动采集
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
		AssAutoCollectionForm assAutoCollectionForm = (AssAutoCollectionForm) form;
		assAutoCollectionForm.setParentNodeId(nodeId);
		updateFormBean(mapping, request, assAutoCollectionForm);
		return mapping.findForward("edit");
	}
	
	/**
	 * 修改kpi自动采集
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
		AssAutoCollectionMgr assAutoCollectionMgr = (AssAutoCollectionMgr) getBean("assAutoCollectionMgr");
		AssAutoCollection assAutoCollection = assAutoCollectionMgr.getAssAutoCollectionByNodeId(nodeId);
		AssAutoCollectionForm assAutoCollectionForm = (AssAutoCollectionForm) convert(assAutoCollection);
		updateFormBean(mapping, request, assAutoCollectionForm);
		return mapping.findForward("edit");
	}
	
	/**
	 * 保存kpi自动采集
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
		AssAutoCollectionMgr assAutoCollectionMgr = (AssAutoCollectionMgr) getBean("assAutoCollectionMgr");
		AssAutoCollectionForm assAutoCollectionForm = (AssAutoCollectionForm) form;
		AssAutoCollection assAutoCollection = (AssAutoCollection) convert(assAutoCollectionForm);
		assAutoCollectionMgr.saveAssAutoCollection(assAutoCollection);
		return mapping.findForward("success");
	}
	
	/**
	 * 删除kpi自动采集
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
		AssAutoCollectionMgr assAutoCollectionMgr = (AssAutoCollectionMgr) getBean("assAutoCollectionMgr");
		assAutoCollectionMgr.removeAssAutoCollectionByNodeId(nodeId);
		return mapping.findForward("success");
	}
	
	/**
	 * 根据采集类型节点选择采集配置
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeConfig(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		JSONArray json = new JSONArray();
		JSONObject jitem = new JSONObject();

		String type = request.getParameter("type");
    	
		AssAutoCollectionMgr assAutoCollectionMgr = (AssAutoCollectionMgr) getBean("assAutoCollectionMgr");
		List list = (List)assAutoCollectionMgr.getNextLevelAssAutoCollections(type);		
    	
		StringBuffer config_list = new StringBuffer();
		config_list.append("," + "");
		config_list.append("," + "--请选择采集配置--");
		for(int i = 0; i < list.size(); i++){
			AssAutoCollection assAutoCollection =(AssAutoCollection)list.get(i);
			config_list.append("," + assAutoCollection.getNodeId());
			config_list.append("," + assAutoCollection.getNodeName());
		}
		String configBuffer = config_list.toString();
		configBuffer = configBuffer.substring(1, configBuffer.length());
		
		jitem.put("configBuffer", configBuffer);
		json.put(jitem);
		response.setCharacterEncoding("utf-8");		
		response.getWriter().write(json.toString());

		return null;
	}	
	
	/**
	 * 自动采集
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward autoCollection(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AssCollectionTypeMgr assCollectionTypeMgr = (AssCollectionTypeMgr) getBean("assCollectionTypeMgr");
		AssCollectionConfigMgr assCollectionConfigMgr = (AssCollectionConfigMgr) getBean("assCollectionConfigMgr");
		AssCollectionQueryJdbcMgr assCollectionQueryJdbcMgr = (AssCollectionQueryJdbcMgr) getBean("assCollectionQueryJdbcMgr");
		AssCollectionResultMgr assCollectionResultMgr = (AssCollectionResultMgr) getBean("assCollectionResultMgr");
		
		IAssKpiConfigMgr assKpiConfigMgr = (IAssKpiConfigMgr) getBean("ItranAssKpiConfigMgr");
		IAssTreeMgr assTreeMgr = (IAssTreeMgr) getBean("ItranAssTreeMgr");
		IAssSelectedInstanceMgr instanceMgr = (IAssSelectedInstanceMgr) getBean("ItranAssSelectedInstanceMgr");
		IAssTemplateMgr assTemplateMgr = (IAssTemplateMgr) getBean("ItranAssTemplateMgr");
		IAssFactoryMgr assFactoryMgr = (IAssFactoryMgr) getBean("ItranAssFactoryMgr");
		IAssTaskMgr taskMgr = (IAssTaskMgr) getBean("ItranAssTaskMgr");
		IAssTaskDetailMgr taskDetailMgr = (IAssTaskDetailMgr) getBean("ItranAssTaskDetailMgr");
		IAssKpiInstanceMgr assKpiInstanceMgr = (IAssKpiInstanceMgr) getBean("ItranAssKpiInstanceMgr");
		IAssKpiMgr assKpiMgr = (IAssKpiMgr) getBean("ItranAssKpiMgr");
		IAssReportInfoMgr assReportInfoMgr = (IAssReportInfoMgr) getBean("ItranAssReportInfoMgr");
		List assKpiList = assKpiMgr.getAssKpis("  and isCollection ='collection' ");
//		取得地市
		AssRoleIdList roleIdList = (AssRoleIdList)ApplicationContextHolder.getInstance().getBean("lineAssRoleIdList");
		String rootAreaId = roleIdList.getRootAreaId();
		List city = PartnerCityByUser.getCityByProvince(rootAreaId);
		
		AssSelectedInstance instance = null;
		
		String localTime = StaticMethod.getLocalString();
		String month = localTime.substring(5, 7);
		String year = localTime.substring(0, 4);
		String nowTime = year+month;
		if("01".equals(month)){
			year = String.valueOf(Integer.parseInt(year)-1);
			month = "12";
		}else{
			if(Integer.parseInt(month)>10){
				month = String.valueOf(Integer.parseInt(month)-1);
			}else{
				month = "0"+String.valueOf(Integer.parseInt(month)-1);
			}
		}
		int monthInt = Integer.parseInt(month);
		String quarter = "01";
		if(monthInt<4){
			quarter = "04";
		}else if(monthInt<7){
			quarter = "01";
		}else if(monthInt<10){
			quarter = "02";
		}else if(monthInt<13){
			quarter = "03";
		}
		String save = "yes";
//		循环地市 ，合作伙伴
		for(int k=0;k<city.size();k++){
//			得到地市
			TawSystemArea tawSystemArea = (TawSystemArea)city.get(k);
//			根据地市取得合作伙伴
			List partnerList = assFactoryMgr.getPartnerDepts(tawSystemArea.getAreaid(), "112131101");
			for(int f=0;f<partnerList.size();f++){
				for(int i=0;i<assKpiList.size();i++){
					String time = year ;
					String afterTime = "";
					AssKpi assKpi = (AssKpi)assKpiList.get(i);
					AssTree assTree = assTreeMgr.getNodeByObjId("",assKpi.getId());
					AssTree assTree1 = assTreeMgr.getTreeNodeByNodeId(assTree.getNodeId().substring(0, 7));
					AssTemplate assTemplate = assTemplateMgr.getTemplate(assTree1.getObjectId());
					List assKpiConfigList = assKpiConfigMgr.getAssKpiConfigs(" where kpiId = '"+assKpi.getId()+"' and node_type ='group'");
					List taskList = taskMgr.listTaskOfTpl(assTemplate.getId());
					AssTask assTask = (AssTask)taskList.get(0);
					if("quarter".equals(assTemplate.getCycle())){ 
						time = time + quarter; 
						if("01".equals(quarter)){
							afterTime = String.valueOf(Integer.parseInt(year)-1)+"04";
						}else{
							afterTime = year+"0"+String.valueOf(Integer.parseInt(quarter)-1);
						}
					} else if ("month".equals(assTemplate.getCycle())) {
						time = time + month;
						if("01".equals(month)){
							afterTime = String.valueOf(Integer.parseInt(year)-1)+"12";
						}else {
							if(Integer.valueOf(month)<11){
								afterTime = year+"0"+String.valueOf(Integer.parseInt(month)-1);
							}else {
								afterTime = year+String.valueOf(Integer.parseInt(month)-1);
							}
						}
					}
					float allWeight = 0;
					float kpiWeight = assTree.getWeight();
					AssTaskDetail assTaskDetail = taskDetailMgr.getTaskDetail(" and kpiId = '"+assKpi.getId()+"' and taskId ='"+assTask.getId()+"'");
					AssReportInfo report = assReportInfoMgr.getReportInfoByTreeNodeId(assTemplate.getBelongNode(), tawSystemArea.getAreaid(), assTemplate.getCycle(), time, String.valueOf(partnerList.get(f)), "1");
//					上月数据以执行 才进行采集
					String deviceNum = "1";
					if(!"".equals(report.getId())&&report.getId()!=null){
						deviceNum = report.getDeviceNum();
					}
					for(int j=0;j<assKpiConfigList.size();j++){
						AssKpiConfig assKpiConfig = (AssKpiConfig)assKpiConfigList.get(j);
						if("collection".equals(assKpiConfig.getIsCollection())){
							AssCollectionType assCollectionType = assCollectionTypeMgr.getAssCollectionTypeByNodeId(assKpiConfig.getCollectionType());
							AssCollectionConfig assCollectionConfig = assCollectionConfigMgr.getAssCollectionConfigByNodeId(assKpiConfig.getCollectionConfig());
	//						进行判断,如果 周期是年并且不是一月不进行保存，如果周期是季度并且不是1月份4月份7月份10月份不进行保存
							if("quarter".equals(assTemplate.getCycle())&&(!"01".equals(month)||!"04".equals(month)||!"07".equals(month)||!"10".equals(month))){
								save = "no";
							} else if ("year".equals(assTemplate.getCycle())&&!"01".equals(month)){
								save = "no";
							}
							if("yes".equals(save)){
								//判断填写值是否在值域范围内
								boolean showGroup = AssStaticMethod.isInBound(deviceNum,assKpiConfig.getNumRelation(),assKpiConfig.getNumConfig());
								if(showGroup||assKpiConfig.getNumConfig()==null){
	//								拼写查询的字符串
									StringBuffer sqlsb = new StringBuffer();
									sqlsb.append(assCollectionConfig.getSql());
									sqlsb.append(" where 1=1 ");
									if(!"".equals(assCollectionConfig.getAreaColumn())){
	//									增加地市查询
										sqlsb.append(" and ");
										sqlsb.append(assCollectionConfig.getAreaColumn());
										sqlsb.append(" = '");
										sqlsb.append(tawSystemArea.getAreaid());
										sqlsb.append("'");
									}
									if(!"".equals(assCollectionConfig.getPartnerColumn())){
	//									增加合作伙伴查询
										sqlsb.append(" and ");
										sqlsb.append(assCollectionConfig.getPartnerColumn());
										sqlsb.append(" = '");
										sqlsb.append(partnerList.get(f));
										sqlsb.append("'");						
									}
									if(!"".equals(assCollectionConfig.getSpecialtyColumn())){
	//									增加设备专业查询
										sqlsb.append(" and ");
										sqlsb.append(assCollectionConfig.getSpecialtyColumn());
										sqlsb.append(" = '");
										sqlsb.append(assTemplate.getDeviceType());
										sqlsb.append("'");						
									}	
									if(!"".equals(assCollectionConfig.getTimeColumn())){
	//									增加时间查询
										sqlsb.append(" and ");
										sqlsb.append(assCollectionConfig.getTimeColumn());
										sqlsb.append(" > '");
	//									获得上一周期的开始时间
										sqlsb.append(DateTimeUtil.getStartTimeByCycleAfter(assTemplate.getCycle()));
										sqlsb.append("' and ");
										sqlsb.append(assCollectionConfig.getTimeColumn() );
										sqlsb.append("< '");
	//									获得上一周期的结束时间
										sqlsb.append(DateTimeUtil.getEndTimeByCycleAfter(assTemplate.getCycle()));
										sqlsb.append("'");
									}					
									float result = assCollectionQueryJdbcMgr.getAssCollectionResult(assCollectionType.getServiceAddr(), assCollectionType.getUserName(),
											assCollectionType.getPassword(), "com.informix.jdbc.IfxDriver", sqlsb.toString());
									//如果是直接折算则不除以设备数，如果是设备折算则除以设备数
									if("2".equals(assKpiConfig.getAlgorithm())){
										result = result/Float.parseFloat(deviceNum);
									}
									AssCollectionResult assCollectionResult = new AssCollectionResult();
									assCollectionResult.setArea(tawSystemArea.getAreaid());
									assCollectionResult.setCycle(assTemplate.getCycle());
									assCollectionResult.setPartner(String.valueOf(partnerList.get(f)));
									assCollectionResult.setResult(String.valueOf(result));
									assCollectionResult.setTime(nowTime);
									assCollectionResult.setTreeNodeId(assKpiConfig.getId());
									assCollectionResult.setType(assTemplate.getDeviceType());
									assCollectionResult.setKpiId(assKpiConfig.getKpiId());
									assCollectionResult.setCreatTime(StaticMethod.getLocalString());
									assCollectionResultMgr.saveAssCollectionResult(assCollectionResult);
									instance = new AssSelectedInstance();
									instance.setKpiId(assKpiConfig.getKpiId());
									instance.setTaskId(assTask.getId());
									instance.setPartnerId(String.valueOf(partnerList.get(f)));
									instance.setTime(nowTime);
									instance.setConfigId(assKpiConfig.getId());
									instance.setWeight(result);
									instance.setAreaId(tawSystemArea.getAreaid());
									instanceMgr.saveAssSelectedInstance(instance);				
									float lastWeight = assKpiConfigMgr.getLastWeightByRange(assKpiConfig.getNodeId(), result);
									allWeight = allWeight + lastWeight*kpiWeight/100;
								}
							}
						}								
					}
						
					AssKpiInstance assKpiInstance = new AssKpiInstance();
					assKpiInstance.setCity(tawSystemArea.getAreaid());
					assKpiInstance.setCreateTime(StaticMethod.getLocalString());
					assKpiInstance.setPartnerId(String.valueOf(partnerList.get(f)));
					assKpiInstance.setTime(nowTime);
					assKpiInstance.setTimeType(assTemplate.getCycle());
					assKpiInstance.setTaskId(assTask.getId());
					assKpiInstance.setTaskDetailId(assTaskDetail.getId());
					ID2NameService mgr = (ID2NameService) getBean("id2nameService");
					assKpiInstance.setPartnerName(mgr.id2Name(String.valueOf(partnerList.get(f)),"partnerDeptDao"));
					assKpiInstance.setRealScore(String.valueOf(allWeight));
					assKpiInstanceMgr.saveKpiInstance(assKpiInstance);						
				}
			}
		}
		return null;
	}	
	 
	/**
	 * 查询对应节点的采集结果
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchCollectionResult(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String partner = StaticMethod.null2String(request.getParameter("partner"));
		String time = StaticMethod.null2String(request.getParameter("time"));
		String cycle = StaticMethod.null2String(request.getParameter("cycle"));
		String kpiId = StaticMethod.null2String(request.getParameter("kpiId"));
		String area = StaticMethod.null2String(request.getParameter("area"));
		
		AssCollectionResultMgr assCollectionResultMgr = (AssCollectionResultMgr) getBean("assCollectionResultMgr");
		StringBuffer sb = new StringBuffer();
		sb.append(" where 1=1 and area='");
		sb.append(area);
		sb.append("' and partner ='");
		sb.append(partner);
		sb.append("' and time ='");
		sb.append(time);
		sb.append("' and cycle ='");
		sb.append(cycle);
		sb.append("' and kpiId ='");
		sb.append(kpiId);
		sb.append("' ");
		Map map = assCollectionResultMgr.getAssCollectionResults(Integer.parseInt("0"),Integer.parseInt("100"), sb.toString());
		List list = assCollectionResultMgr.getAssCollectionResults(sb.toString());
		request.setAttribute("resultList", list);
		request.setAttribute("list", list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", Integer.parseInt("100"));
		return mapping.findForward("showResult");
	}	
}