package com.boco.eoms.partner.deviceAssess.webapp.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import atg.taglib.json.util.JSONArray;
import atg.taglib.json.util.JSONObject;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.partner.deviceAssess.mgr.AssessConfigService;
import com.boco.eoms.partner.deviceAssess.mgr.AssessIndicatorService;
import com.boco.eoms.partner.deviceAssess.mgr.AssessIndicatorSubService;
import com.boco.eoms.partner.deviceAssess.mgr.BigFaultMgr;
import com.boco.eoms.partner.deviceAssess.mgr.FacilityQuantityMgr;
import com.boco.eoms.partner.deviceAssess.mgr.FacilityinfoMgr;
import com.boco.eoms.partner.deviceAssess.mgr.SoftApplyRecordMgr;
import com.boco.eoms.partner.deviceAssess.mgr.SoftupinfoMgr;
import com.boco.eoms.partner.deviceAssess.model.AssessConfig;
import com.boco.eoms.partner.deviceAssess.model.AssessIndicator;
import com.boco.eoms.partner.deviceAssess.model.AssessIndicatorSub;
import com.boco.eoms.partner.deviceAssess.model.FacilityQuantity;
import com.boco.eoms.partner.deviceAssess.util.BigFaultConstants;
import com.boco.eoms.partner.deviceAssess.util.FacilityinfoConstants;
import com.boco.eoms.partner.deviceAssess.util.SearchUtil;
import com.boco.eoms.partner.deviceAssess.util.SoftupinfoConstants;
import com.boco.eoms.partner.deviceAssess.util.StatisticModel;
import com.google.common.base.Charsets;
import com.googlecode.genericdao.search.Search;

/**
 * Description: 后评估模块统计 Copyright: Copyright (c)2011 Company: BOCO
 * 
 * @author: Wuchunhui
 * @version: 1.0 Create at: Nov. 2011
 */
public class StatisticAction extends BaseAction {

	/**
	 * 添加指标页面的跳转
	 */
	public ActionForward goToAddIndicaor(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward("goToAddIndicaor");
	}
	
	/**
	 * the ajax method ,this method return JSON  to detail the data.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward detai(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		AssessConfigService assessConfigService = (AssessConfigService) ApplicationContextHolder
				.getInstance().getBean("assessConfigService");
		AssessIndicatorService assessIndicatorService = (AssessIndicatorService) ApplicationContextHolder
				.getInstance().getBean("assessIndicatorService");
		AssessIndicatorSubService assessIndicatorSubService = (AssessIndicatorSubService) ApplicationContextHolder
				.getInstance().getBean("assessIndicatorSubService");
		response.setCharacterEncoding(Charsets.UTF_8.toString());
		//AssessConfig model's  
		String typeId = request.getParameter("typeId");
		Search aconfigSearch = new Search();
		aconfigSearch.addFilterEqual("devicetype", typeId);
		AssessConfig assessConfig = (AssessConfig) assessConfigService
				.searchUnique(aconfigSearch);
		if(assessConfig!=null){
		PrintWriter  writer = response.getWriter();
		JSONObject obj = new JSONObject();
		//AssessIndicator model's JSON
		String aConfigid = StaticMethod.null2String(assessConfig.getId());
		if("".equals(aConfigid))
			return null;
		Search indicatorSearch = new Search();
		indicatorSearch.addFilterEqual("configid", aConfigid);
		List<AssessIndicator>  indicatorList = assessIndicatorService.search(indicatorSearch);
		JSONArray indicatorResult = new JSONArray();
		if(indicatorList!=null){
		for (AssessIndicator assessIndicator : indicatorList) {
			JSONObject indicatorData = new JSONObject();
			indicatorData.put(assessIndicator.getIndicatorType(), assessIndicator.getTypeScore());
			indicatorData.put(assessIndicator.getIndicatorName(), assessIndicator.getNameScor());
			String indicatorId = assessIndicator.getId();
			Search indicatorSubSearch = new Search();
			indicatorSubSearch.addFilterEqual("indicatorid", indicatorId);
			List<AssessIndicatorSub> indicatorSubList = assessIndicatorSubService.search(indicatorSubSearch);
			for (AssessIndicatorSub sub : indicatorSubList) {
				indicatorData.put(sub.getIndicatorName()+"score", sub.getScore());
			}
			indicatorResult.put(indicatorData);
		}
		obj.put("size", indicatorList.size());
		obj.put("indicatorResult", indicatorResult);
		writer.print(indicatorResult.toString());
		}
		}
		//
		
		
		return null;
	}

	/**
	 * 保存指标或者更新 
	 */
	public ActionForward saveIndicaor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		
		
		AssessConfigService assessConfigService = (AssessConfigService) ApplicationContextHolder
				.getInstance().getBean("assessConfigService");
		AssessIndicatorService assessIndicatorService = (AssessIndicatorService) ApplicationContextHolder
				.getInstance().getBean("assessIndicatorService");
		AssessIndicatorSubService assessIndicatorSubService = (AssessIndicatorSubService) ApplicationContextHolder
				.getInstance().getBean("assessIndicatorSubService");
		//判断是否已有数据信息
		String deviceType= request.getParameter("devicetype");
		Search search = new Search();
		search.addFilterEqual("devicetype", deviceType);
		List<AssessConfig> typeList = assessConfigService.search(search);
		if(typeList.size()!=0){
			assessConfigService.removeById(typeList.get(0).getId());
		}
		// assessConfig 表
		AssessConfig assessConfig = new AssessConfig();
		assessConfig.setSpecial((String) request.getParameter("special"));
		assessConfig.setDevicetype((String) request.getParameter("devicetype"));
		assessConfigService.save(assessConfig);
		// AssessIndicator 表
		AssessIndicator assessIndicator = new AssessIndicator();
		assessIndicator.setConfigid(assessConfig.getId());
		assessIndicator.setIndicatorType("设备质量");
		assessIndicator.setTypeScore(Float.valueOf(request
				.getParameter("deviceMass")));
		assessIndicator.setIndicatorName("设备故障率");
		assessIndicator.setNameScor(Float.valueOf(request
				.getParameter("deviceFault")));
		assessIndicatorService.save(assessIndicator);
		// AssessIndicatorSub 表
		AssessIndicatorSub assessIndicatorSub = new AssessIndicatorSub();
		assessIndicatorSub.setIndicatorid(assessIndicator.getId());
		assessIndicatorSub.setIndicatorName(assessIndicator.getIndicatorName());
		String a = request.getParameter("baseScore1");
		String b = request.getParameter("compareScore1");
		String score = "a=" + a + ";b=" + b;
		assessIndicatorSub.setScore(score);
		assessIndicatorSub.setArithmetic("a+min/self*b");
		assessIndicatorSubService.save(assessIndicatorSub);
		// 重构下条得分指标数据
		// AssessIndicator 表
		 assessIndicator = new AssessIndicator();
		assessIndicator.setConfigid(assessConfig.getId());
		assessIndicator.setIndicatorType("设备质量");
		assessIndicator.setTypeScore(Float.valueOf(request
				.getParameter("deviceMass")));
		assessIndicator.setIndicatorName("坏板率");
		assessIndicator.setNameScor(Float.valueOf(request
				.getParameter("boardFaule")));
		assessIndicatorService.save(assessIndicator);
		// AssessIndicatorSub 表
		 assessIndicatorSub = new AssessIndicatorSub();
		assessIndicatorSub.setIndicatorid(assessIndicator.getId());
		assessIndicatorSub.setIndicatorName(assessIndicator.getIndicatorName());
		 a = request.getParameter("baseScore2");
		 b = request.getParameter("compareScore2");
		 String k  = request.getParameter("k");
		 score = "a=" + a + ";b=" + b+";k="+k;
		assessIndicatorSub.setScore(score);
		assessIndicatorSub.setArithmetic("a+min/self*b");
		assessIndicatorSubService.save(assessIndicatorSub);
//		creatNewData(assessConfig, "设备质量", "坏板率", "deviceMass", "boardFaule",
//				"a+min/self*b", "baseScore2", "compareScore2", request);

		assessIndicator = new AssessIndicator();
		assessIndicator.setConfigid(assessConfig.getId());
		assessIndicator.setIndicatorType("设备质量");
		assessIndicator.setTypeScore(Float.valueOf(request
				.getParameter("deviceMass")));
		assessIndicator.setIndicatorName("设备重大故障数");
		assessIndicator.setNameScor(null);
		assessIndicatorService.save(assessIndicator);
		assessIndicatorSub = new AssessIndicatorSub();
		assessIndicatorSub.setIndicatorid(assessIndicator.getId());
		assessIndicatorSub.setIndicatorName(assessIndicator.getIndicatorName());
		String x = request.getParameter("bigFaultScore");
		score = "x=" + x;
		assessIndicatorSub.setScore(score);
		assessIndicatorSub.setArithmetic("x*time");
		assessIndicatorSubService.save(assessIndicatorSub);

		assessIndicator = new AssessIndicator();
		assessIndicator.setConfigid(assessConfig.getId());
		assessIndicator.setIndicatorType("设备质量");
		assessIndicator.setTypeScore(Float.valueOf(request
				.getParameter("deviceMass")));
		assessIndicator.setIndicatorName("设备问题数");
		assessIndicator.setNameScor(null);
		assessIndicatorService.save(assessIndicator);
		assessIndicatorSub = new AssessIndicatorSub();
		assessIndicatorSub.setIndicatorid(assessIndicator.getId());
		assessIndicatorSub.setIndicatorName(assessIndicator.getIndicatorName());
		x = request.getParameter("proScore");
		score = "x=" + x;
		assessIndicatorSub.setScore(score);
		assessIndicatorSub.setArithmetic("x*time");
		assessIndicatorSubService.save(assessIndicatorSub);
		// 调用方法存入数据
		creatNewData(assessConfig, "服务质量", "软件申请及升级问题", "serveMass",
				"updateFault", "a*timeA+b*timeB", "baseScore3",
				"compareScore3", request);
		creatNewData(assessConfig, "服务质量", "故障处理平均时长", "serveMass",
				"faultTalkTime", "a+min/self*b", "baseScore4", "compareScore4",
				request);
		creatNewData(assessConfig, "服务质量", "业务恢复处理平均时长", "serveMass",
				"busiTalkTime", "a+min/self*b", "baseScore5", "compareScore5",
				request);
		creatNewData(assessConfig, "服务质量", "板件返修平均时长", "serveMass",
				"boardTalkTime", "a+min/self*b", "baseScore6", "compareScore6",
				request);
		creatNewData(assessConfig, "服务质量", "咨询服务反馈平均时长", "serveMass",
				"referTalkTime", "a+min/self*b", "baseScore7", "compareScore7",
				request);
		// 调用方法存入数据
		creatNewData(assessConfig, "服务满意度", "技术服务满意度", "servesatisfact",
				"skillServe", "a+self/max*b", "baseScore8", "compareScore8",
				request);
		creatNewData(assessConfig, "服务满意度", "工程服务满意度", "servesatisfact",
				"projectServe", "a+self/max*b", "baseScore9", "compareScore9",
				request);
		creatNewData(assessConfig, "服务满意度", "培训服务满意度", "servesatisfact",
				"trainServe", "a+self/max*b", "baseScore10", "compareScore10",
				request);
		return mapping.findForward("success");
	}

	/**
	 * 调用方法重复的数据存入
	 * 
	 * @param assessConfig
	 *            专业和设备类型Model
	 * @param indicatorType
	 *            指标分类
	 * @param indicatorName
	 *            指标名字
	 * @param indicatorTypeId
	 *            指标分类Id
	 * @param indicatorNameId
	 *            指标名字Id
	 * @param arithmetic
	 *            得分算法
	 * @param baseScore
	 *            基础分
	 * @param CompareScore
	 *            比较分
	 * @param request
	 *            HTTP的request 对象
	 * 
	 * @return
	 */
	public boolean creatNewData(AssessConfig assessConfig,
			String indicatorType, String indicatorName, String indicatorTypeId,
			String indicatorNameId, String arithmetic, String baseScore,
			String CompareScore, HttpServletRequest request) {
		AssessIndicatorService assessIndicatorService = (AssessIndicatorService) ApplicationContextHolder
				.getInstance().getBean("assessIndicatorService");
		AssessIndicatorSubService assessIndicatorSubService = (AssessIndicatorSubService) ApplicationContextHolder
				.getInstance().getBean("assessIndicatorSubService");

		AssessIndicator assessIndicator = new AssessIndicator();
		assessIndicator.setConfigid(assessConfig.getId());
		assessIndicator.setIndicatorType(indicatorType);
		assessIndicator.setTypeScore(Float.valueOf(request
				.getParameter(indicatorTypeId)));
		assessIndicator.setIndicatorName(indicatorName);
		assessIndicator.setNameScor(Float.valueOf(request
				.getParameter(indicatorNameId)));
		assessIndicatorService.save(assessIndicator);
		AssessIndicatorSub assessIndicatorSub = new AssessIndicatorSub();
		assessIndicatorSub.setIndicatorid(assessIndicator.getId());
		assessIndicatorSub.setIndicatorName(assessIndicator.getIndicatorName());
		String a = request.getParameter(baseScore);
		String b = request.getParameter(CompareScore);
		String score = "a=" + a + ";b=" + b;
		assessIndicatorSub.setScore(score);
		assessIndicatorSub.setArithmetic(arithmetic);
		return assessIndicatorSubService.save(assessIndicatorSub);
	}
	
	
	/**
	 * this method mapping to the statistic.jsp  page.
	 * @param mapping
	 * @param form
	 * @param request the HTTP request.
	 * @param response the HTTP response.
	 * @return
	 * @throws Exception
	 */
	public ActionForward goToStatistic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		return mapping.findForward("goToStatistic");
	}

	/**
	 * 统计报表的显示
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward statistic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String statisticType=request.getParameter("statisticType");
	
		String[] factory = request.getParameterValues("factory");
		AssessConfigService assessConfigService = (AssessConfigService) ApplicationContextHolder
		.getInstance().getBean("assessConfigService");
		ID2NameService service = (ID2NameService) ApplicationContextHolder.getInstance().getBean("ID2NameGetServiceCatch");
		String deviceType = request.getParameter("devicetype");
		String statisType = request.getParameter("statisType");
		String startTime="";
		String endTime="";
		//判断统计时间的模式.根据年或者季度区分不同开始和结束时间
		 if("byTime".equals(statisType)){
			  startTime = request.getParameter("startTime");
			  endTime = request.getParameter("endTime");
		 }
		 else if("byYear".equals(statisType)){
			 String  year = request.getParameter("year");
			 startTime=year+"-1-1 00:00:00";
			 endTime=year+"-12-31 23:59:59";
			 
		 }
		 else if("byQuarter".equals(statisType)){
			 String year = request.getParameter("yearAndQuarter");
			 String  quarter = request.getParameter("quarter");
			 if("1".equals(quarter)){
				 startTime=year+"-1-1 00:00:00";
				 endTime=year+"-3-31 23:59:59";
			 }
			 else  if("2".equals(quarter)){
				 startTime=year+"-4-1 00:00:00";
				 endTime=year+"-6-30 23:59:59";
			 }
			 else  if("3".equals(quarter)){
				 startTime=year+"-7-1 00:00:00";
				 endTime=year+"-9-30 23:59:59";
			 }
			 else  if("4".equals(quarter)){
				 startTime=year+"-10-1 00:00:00";
				 endTime=year+"-12-31 23:59:59";
			 }
		 }
		 else {
			 startTime = request.getParameter("startTime");
			  endTime = request.getParameter("endTime");
		 }
			
		 List<String> factoryList = new ArrayList<String>();
		 FacilityQuantityMgr facilityQuantityService = (FacilityQuantityMgr)ApplicationContextHolder.getInstance().getBean("facilityQuantityMgr");
		 Search quantitySearch = new Search();
		 quantitySearch.addFilterEqual("deviceType", deviceType);
		 quantitySearch.addFilterGreaterThan("quantity", 0);
		 //the quantity num Map .
		 Map<String,Integer> quantityMap = new HashMap<String,Integer> ();
		 List<FacilityQuantity> quantityList =facilityQuantityService.search(quantitySearch);
		for (FacilityQuantity quantity : quantityList) {
			factoryList.add(quantity.getFactory());
			quantityMap.put(quantity.getFactory(), quantity.getQuantity());
		}
		//判断选择的厂家是否有这个设备

	
		if(factory!=null){
			for (int i = 0; i < factory.length; i++) {
				if(!quantityMap.containsKey(factory[i])){
					ID2NameService nameService = (ID2NameService) ApplicationContextHolder
					.getInstance().getBean("ID2NameGetServiceCatch");
					String Name =nameService.id2Name(factory[i], "tawSystemDictTypeDao");
					request.setAttribute("unIdName", Name);
					return mapping.findForward("goToStatistic");
				}
			}
			factoryList.clear();
			for (int i = 0; i < factory.length; i++) {
				factoryList.add(factory[i]);
			}
		}
		//判断需要统计的专业
		if(deviceType!=null){
			AssessConfig assessConfig = assessConfigService.searchUnique(new Search().addFilterEqual("devicetype", deviceType));
			if(assessConfig==null){
				ID2NameService nameService = (ID2NameService) ApplicationContextHolder
				.getInstance().getBean("ID2NameGetServiceCatch");
				String Name =nameService.id2Name(deviceType, "tawSystemDictTypeDao");
				request.setAttribute("deviceTypeName", Name);
				return mapping.findForward("goToStatistic");
			}
		}
		if("completely".equals(statisticType)){
			String special= request.getParameter("special");
			ITawSystemDictTypeManager manager = (ITawSystemDictTypeManager)ApplicationContextHolder.getInstance().getBean("ItawSystemDictTypeManager");
			List<TawSystemDictType> specilList = manager.getDictSonsByDictid(special);
			for (TawSystemDictType type : specilList) {
				AssessConfig assessConfig = assessConfigService.searchUnique(new Search().addFilterEqual("devicetype", type.getDictId()));
				if(assessConfig==null){
					ID2NameService nameService = (ID2NameService) ApplicationContextHolder
					.getInstance().getBean("ID2NameGetServiceCatch");
					String Name =nameService.id2Name(type.getDictId(), "tawSystemDictTypeDao");
					request.setAttribute("deviceTypeName", Name);
					return mapping.findForward("goToStatistic");
				}
			}
			//判断如果是全专业统计跳转胡浩的全专业统计
			request.setAttribute("startTime", startTime);
			request.setAttribute("endTime", endTime);
			String year=request.getParameter("year");
			String quarter=request.getParameter("quarter");
			ActionForward af=new ActionForward("/allStatistic.do?method=search&startTime="+startTime+"&endTime="+endTime+"&special="+special+"&statisType="+statisType+"&year"+year+"&quarter"+quarter,true);
			return af;
		}
		
		//根据厂家的多少，构建表头显示 
		List headList = new ArrayList();
		StatisticModel  headModelS = new StatisticModel();
		headModelS.setDisplay("指标项");
		headModelS.setRowspan("2");
		headList.add(headModelS);
		for (int i = 0; i < factoryList.size(); i++) {
			StatisticModel  headModel = new StatisticModel();
			String facId = factoryList.get(i);
			String facName = service.id2Name(facId, "tawSystemDictTypeDao");
			headModel.setDisplay(facName);
			headModel.setRowspan("1");
			headList.add(headModel);
		}
		//调用展示的图标的构造方法. 此处分3项大项目统计分别调用,如果以后增加.方便修改和增加
		List<List> tabelList = new ArrayList<List> ();
		tabelList =assessConfigService.creatTableList(tabelList,factoryList,quantityMap,deviceType,"设备质量",startTime,endTime);
		tabelList = assessConfigService.creatTableList(tabelList,factoryList,quantityMap,deviceType,"服务质量",startTime,endTime);
		tabelList = assessConfigService.creatTableList(tabelList,factoryList,quantityMap,deviceType,"服务满意度",startTime,endTime);
		
		request.setAttribute("tabelList", tabelList);
		request.setAttribute("headList", headList);
		return mapping.findForward("SigleStatistic");
	}
	
	
	/**
	 * 单设备类型统计钻取方法. 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public  ActionForward detailSum(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		//取得统计类型
		String type =new String(request.getParameter("type").toString().getBytes("ISO-8859-1"),"utf-8");
		String deviceType=request.getParameter("deviceType");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		if("设备问题数".equals(type)){
			String pageIndexName = new org.displaytag.util.ParamEncoder(
					FacilityinfoConstants.FACILITYINFO_LIST)
					.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
			final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
					.getPageSize();
			final Integer pageIndex = Integer.valueOf(GenericValidator
					.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
					: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
			FacilityinfoMgr facilityinfoMgr = (FacilityinfoMgr) ApplicationContextHolder.getInstance().getBean("facilityinfoMgr");
			String whereCoundition = " where equipmentType='"+deviceType+"' and occurTime>'"+startTime+"' and occurTime<'"+endTime+"'";
			Map map = (Map) facilityinfoMgr.getFacilityinfos(pageIndex, pageSize, whereCoundition);
			request.setAttribute("resultSize", map.get("total"));
			request.setAttribute("pageSize", pageSize);
			request.setAttribute("facilityinfoList", map.get("result"));
			return mapping.findForward("deviceFulat");
		}
		else if("设备重大故障数".equals(type)){
			String pageIndexName = new org.displaytag.util.ParamEncoder(
					BigFaultConstants.BIGFAULT_LIST)
					.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
			final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
					.getPageSize();
			final Integer pageIndex = Integer.valueOf(GenericValidator
					.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
					: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
			BigFaultMgr bigFaultMgr = (BigFaultMgr) ApplicationContextHolder.getInstance().getBean("bigFaultMgr");
			String whereCoundition = " where equipmentType='"+deviceType+"' and createTime>'"+startTime+"' and createTime<'"+endTime+"'";
			Map map = (Map) bigFaultMgr.getBigFaults(pageIndex, pageSize, whereCoundition);
			List list = (List) map.get("result");
			request.setAttribute("bigFaultList", list);
			request.setAttribute("resultSize", map.get("total"));
			request.setAttribute("pageSize", pageSize);
			return mapping.findForward("bigFulat");
		}
		else if("软件申请及升级问题".equals(type)){
			//软件升级问题
			String pageIndexName = new org.displaytag.util.ParamEncoder(
					SoftupinfoConstants.SOFTUPINFO_LIST)
					.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
			final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
					.getPageSize();
			final Integer pageIndex = Integer.valueOf(GenericValidator
					.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
					: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
			SoftupinfoMgr softupinfoMgr = (SoftupinfoMgr) ApplicationContextHolder.getInstance().getBean("softupinfoMgr");
			String whereCoundition = " where equipmentType='"+deviceType+"' and createTime>'"+startTime+"' and createTime<'"+endTime+"'";
			Map map = (Map) softupinfoMgr.getSoftupinfos(pageIndex, pageSize, whereCoundition);
			List list = (List) map.get("result");
			request.setAttribute(SoftupinfoConstants.SOFTUPINFO_LIST, list);
			request.setAttribute("resultSize", map.get("total"));
			request.setAttribute("pageSize", pageSize);
			//软件申请问题
			SoftApplyRecordMgr softApplyRecordMgr = (SoftApplyRecordMgr) getBean("softApplyRecordMgr");
			
			Map reqMap = request.getParameterMap();
			String whereStr = " where equipmentType='"+deviceType+"' and createTime>'"+startTime+"' and createTime<'"+endTime+"'";
			
			Map map1;
			String stateStr = request.getParameter("state");
			if(stateStr != null && !"".equals(stateStr)) {
				String hql = "select sar from SoftApplyRecord sar,DeviceAssessApprove daa ";
				whereStr = SearchUtil.getSqlFromRequestMap(reqMap,"sar");
				hql += whereStr;
				if(whereStr != null && !"".equals(whereStr)) {
					hql += " and sar.id=daa.assessId order by sar.createTime";
				} else {
					hql += " where sar.id=daa.assessId order by sar.createTime ";
				}
				map1 = (Map) softApplyRecordMgr.getSoftApplyRecordsWithHQL(pageIndex, pageSize, hql);
			} else {
				whereStr = SearchUtil.getSqlFromRequestMap(reqMap,"");
				map1 = (Map) softApplyRecordMgr.getSoftApplyRecords(pageIndex, pageSize, whereStr);
			}
			
			List list1 = (List) map1.get("result");
			request.setAttribute("softApplyRecordList", list1);
			request.setAttribute("resultSize", map1.get("total"));
			request.setAttribute("pageSize", pageSize);
			
			
			return mapping.findForward("softUpdateInfo");
		}
		return null;
	}
	
	
	
	
	
	

	
	

}
