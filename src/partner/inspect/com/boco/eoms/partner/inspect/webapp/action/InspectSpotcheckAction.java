package com.boco.eoms.partner.inspect.webapp.action;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.LocalDate;

import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanMainMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectSpotcheckMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectTemplateMgr;
import com.boco.eoms.partner.inspect.model.InspectPlanMain;
import com.boco.eoms.partner.inspect.model.InspectPlanRes;
import com.boco.eoms.partner.inspect.model.InspectTemplate;
import com.boco.eoms.partner.inspect.model.SpotcheckItem;
import com.boco.eoms.partner.inspect.model.SpotcheckRes;
import com.boco.eoms.partner.inspect.model.SpotcheckTemplate;
import com.boco.eoms.partner.inspect.util.InspectConstants;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

/** 
 * Description: 巡检抽检
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Aug 23, 2012 10:28:33 AM 
 */
public class InspectSpotcheckAction extends BaseAction {
	
	/**
	 *  查询巡检模板列表
	 */
	@SuppressWarnings("unchecked")
	public ActionForward findInspectTemplateList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		String year =StaticMethod.nullObject2String(request.getParameter("year"));
		String month =StaticMethod.nullObject2String(request.getParameter("month"));
		String templateName = StaticMethod.nullObject2String(request.getParameter("templateName"));
		String specialty = StaticMethod.nullObject2String(request.getParameter("specialty"));
		IInspectSpotcheckMgr inspectSpotcheckMgr = (IInspectSpotcheckMgr)getBean("inspectSpotcheckMgr");
		Integer pageSize = CommonConstants.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"inspectTemplateList");
		int pageIndex = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		List list = new ArrayList();
		LocalDate date = new LocalDate();
		String whereStr="";
		if(!"".equals(templateName)){
			whereStr += " and templatename like'%"+templateName+"%'";
		}
		if(!"".equals(specialty)){
			whereStr += " and specialty='"+specialty+"'";
		}
		
		if("".equals(year)){
			year = date.getYear()+"";
		}
		if("".equals(month)){
			month = date.getMonthOfYear()+"";
		}
		
		list = inspectSpotcheckMgr.getInspectTemplateHisList(year, month, pageIndex*pageSize, pageSize,whereStr);
		request.setAttribute("resultSize", list.get(0));
		request.setAttribute("inspectTemplateList", list.get(1));
		
//		if(!"".equals(year)){
//			list = inspectSpotcheckMgr.getInspectTemplateHisList(year, month, pageIndex*pageSize, pageSize,whereStr);
//			request.setAttribute("resultSize", list.get(0));
//			request.setAttribute("inspectTemplateList", list.get(1));
//		}else{
//			request.setAttribute("resultSize", 0);
//			request.setAttribute("inspectTemplateList", list);
//		}
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		return mapping.findForward("inspectTemplateList");
	}
	
	/**
	 *  巡检抽检模板列表
	 */
	public ActionForward findSpotcheckTemplateList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		String inspectTemplateId = request.getParameter("inspectTemplateId");//巡检模板ID
		String year = StaticMethod.nullObject2String(request.getParameter("year"));
		String month = StaticMethod.nullObject2String(request.getParameter("month"));
		IInspectSpotcheckMgr inspectSpotcheckMgr = (IInspectSpotcheckMgr)getBean("inspectSpotcheckMgr");
		List<Map<String,Object>> list = inspectSpotcheckMgr.findSpotcheckTemplateList(inspectTemplateId,year,month);
		request.setAttribute("list", list);
		request.setAttribute("inspectTemplateId", inspectTemplateId);
		request.setAttribute("year", year);
		request.setAttribute("month",month);
		return mapping.findForward("spotcheckTemplateList");
	}
	
	/**
	 *  跳转到巡检抽检模板编辑页面
	 */
	public ActionForward toEidtSpotcheckTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		IInspectSpotcheckMgr inspectSpotcheckMgr = (IInspectSpotcheckMgr)getBean("inspectSpotcheckMgr");
		String spotcheckTemplateId = StaticMethod.nullObject2String(request.getParameter("spottmpid")); //抽检模板ID
		String inspectTemplateId = StaticMethod.nullObject2String(request.getParameter("inspectTemplateId"));//巡检模板ID
		String year = StaticMethod.nullObject2String(request.getParameter("year"));
		String month = StaticMethod.nullObject2String(request.getParameter("month"));
//		SpotcheckTemplate spotcheckTemplate = null;
//		Float usableScore = inspectSpotcheckMgr.getSpotcheckTemplateUsableScore(inspectTemplateId, spotcheckTemplateId);
//		if(StringUtils.isEmpty(spotcheckTemplateId)){//新增
//			
//		}else{ //修改
//			spotcheckTemplate = inspectSpotcheckMgr.getSpotcheckTemplate(spotcheckTemplateId);
//		}
//		List<Map<String,String>> list = inspectSpotcheckMgr.findInspectPlanItemList(inspectTemplateId, spotcheckTemplateId);
		
		//查询巡检检模板所有大项
		List<String> bitItemList = inspectSpotcheckMgr.findInspectTemplateAllBigItem(inspectTemplateId,year,month);
		request.setAttribute("bitItemList", bitItemList);
		request.setAttribute("bitItemListSize", bitItemList.size());
//		request.setAttribute("list", list);
//		request.setAttribute("resultSize", list.size());
//		request.setAttribute("pageSize", 0);
		request.setAttribute("spotcheckTemplateId",spotcheckTemplateId);
		request.setAttribute("inspectTemplateId",inspectTemplateId);
		request.setAttribute("year", year);
		request.setAttribute("month",month);
//		request.setAttribute("spotcheckTemplate",spotcheckTemplate);
//		request.setAttribute("usableScore",usableScore);
		return mapping.findForward("spotcheckTemplateEdit");
	}
	
	/**
	 * 查询当前抽检项下所有的模板大项
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward spotcheckTemplateList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		IInspectSpotcheckMgr inspectSpotcheckMgr = (IInspectSpotcheckMgr)getBean("inspectSpotcheckMgr");
		String spotcheckTemplateId = StaticMethod.nullObject2String(request.getParameter("spottmpid")); //抽检模板ID
		String inspectTemplateId = StaticMethod.nullObject2String(request.getParameter("inspectTemplateId"));//巡检模板ID
		String year = StaticMethod.nullObject2String(request.getParameter("year"));
		String month = StaticMethod.nullObject2String(request.getParameter("month"));
		List<String> bitItemList = inspectSpotcheckMgr.findInspectTemplateAllBigItem(inspectTemplateId,year,month);
		request.setAttribute("bitItemList", bitItemList);
		request.setAttribute("spotcheckTemplateId",spotcheckTemplateId);
		request.setAttribute("inspectTemplateId",inspectTemplateId);
		request.setAttribute("year", year);
		request.setAttribute("month",month);
		return mapping.findForward("spotcheckTemplateList");
	}
	
	/**
	 * 查询当前模板大项下所有模板项
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward checkTemplateItemList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		IInspectSpotcheckMgr inspectSpotcheckMgr = (IInspectSpotcheckMgr)getBean("inspectSpotcheckMgr");
		String spotcheckTemplateId = StaticMethod.nullObject2String(request.getParameter("spottmpid")); //抽检模板ID
		String inspectTemplateId = StaticMethod.nullObject2String(request.getParameter("inspectTemplateId"));//巡检模板ID
		String year = StaticMethod.nullObject2String(request.getParameter("year"));
		String month = StaticMethod.nullObject2String(request.getParameter("month"));
		List<String> bitItemList = inspectSpotcheckMgr.findInspectTemplateAllBigItem(inspectTemplateId,year,month);
		request.setAttribute("bitItemList", bitItemList);
		request.setAttribute("spotcheckTemplateId",spotcheckTemplateId);
		request.setAttribute("inspectTemplateId",inspectTemplateId);
		request.setAttribute("year", year);
		request.setAttribute("month",month);
		return mapping.findForward("checkTemplateItemList");
	}
	
	/**
	 * 保存抽检模板项
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward savecheckTemplateItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		IInspectSpotcheckMgr inspectSpotcheckMgr = (IInspectSpotcheckMgr)getBean("inspectSpotcheckMgr");
		String spotcheckTemplateId = StaticMethod.nullObject2String(request.getParameter("spottmpid")); //抽检模板ID
		String inspectTemplateId = StaticMethod.nullObject2String(request.getParameter("inspectTemplateId"));//巡检模板ID
		String year = StaticMethod.nullObject2String(request.getParameter("year"));
		String month = StaticMethod.nullObject2String(request.getParameter("month"));
		List<String> bitItemList = inspectSpotcheckMgr.findInspectTemplateAllBigItem(inspectTemplateId,year,month);
		request.setAttribute("bitItemList", bitItemList);
		request.setAttribute("spotcheckTemplateId",spotcheckTemplateId);
		request.setAttribute("inspectTemplateId",inspectTemplateId);
		request.setAttribute("year", year);
		request.setAttribute("month",month);
		return mapping.findForward("savecheckTemplateItem");
	}
	
	/**
	 *  巡检抽检模板编辑保存(新增和修改)
	 */
	@SuppressWarnings("unchecked")
	public ActionForward eidtSpotcheckTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		IInspectSpotcheckMgr inspectSpotcheckMgr = (IInspectSpotcheckMgr)getBean("inspectSpotcheckMgr");
		String inspectItemGroup = request.getParameter("inspectItemGroup");
		String score = request.getParameter("score");
		String markRule = request.getParameter("markRule");
		String spotcheckTemplateId = request.getParameter("spotcheckTemplateId");
		String inspectTemplateId = request.getParameter("inspectTemplateId");
		SpotcheckTemplate spotcheckTemplate = null;
		if(StringUtils.isEmpty(spotcheckTemplateId)){//新增
			spotcheckTemplate = new SpotcheckTemplate();
			spotcheckTemplate.setTemplateId(inspectTemplateId);
		}else{ //修改
			spotcheckTemplate = inspectSpotcheckMgr.getSpotcheckTemplate(spotcheckTemplateId);
		}
		spotcheckTemplate.setBigitemName(inspectItemGroup);
		spotcheckTemplate.setScore(Float.parseFloat(score));
		spotcheckTemplate.setMarkRule(markRule);
		
		Map map = request.getParameterMap();
		Object[] idArray = (Object[])map.get("checkboxId");
		List<String> itemIdList = Lists.newArrayList();
		if(idArray != null){
			for(int i=0;i<idArray.length;i++){
				String inspectTemplateItemId = idArray[i].toString();
				itemIdList.add(inspectTemplateItemId);
			}
		}
		
		inspectSpotcheckMgr.saveSpotcheckTemplate(spotcheckTemplate, itemIdList);
		
		ActionForward actionForward = new ActionForward();
		actionForward.setPath("/inspectSpotcheckAction.do?method=findSpotcheckTemplateList&inspectTemplateId="+inspectTemplateId);
		actionForward.setRedirect(false);
		return actionForward;
	}
	
	/**
	 * 查询所有的抽检模板项
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void getAllInspectTemplateItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		IInspectSpotcheckMgr inspectSpotcheckMgr = (IInspectSpotcheckMgr)getBean("inspectSpotcheckMgr");
		String templateBigItemId = StaticMethod.nullObject2String(request.getParameter("bigItemId"));
		String inspectTemplateId = StaticMethod.nullObject2String(request.getParameter("inspectTemplateId"));
		String year = StaticMethod.nullObject2String(request.getParameter("year"));
		String month = StaticMethod.nullObject2String(request.getParameter("month"));
		List itemList = inspectSpotcheckMgr.getAllInspectTemplateItem(templateBigItemId, inspectTemplateId,year,month);
		List listUper = new ArrayList();
		for(int i= 0 ;i<itemList.size();i++){
			Map map =  (Map) itemList.get(i);
			Map mapUper = new HashMap();
			Iterator it = map.keySet().iterator();
			while(it.hasNext()){
				String key = it.next().toString();
				mapUper.put(key.toUpperCase(), map.get(key));
			}
			listUper.add(mapUper);
		}
		JSONArray json = JSONArray.fromObject(listUper); 
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * 保存抽检模板
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveSpotTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		String scores[] = request.getParameterValues("bigItemScore");
		//String bigIds[] = request.getParameterValues("bigId");
		String isdeletes[] = request.getParameterValues("isdelete");
		String itemNum[] = request.getParameterValues("itemNum");
		String checkBoxs[] = request.getParameterValues("checkBox");
		String bigItemNames[] = request.getParameterValues("bigItemname");
		String spotTemplateIds[] = request.getParameterValues("spotTemplateId");
		String markRules[] = request.getParameterValues("markRule");
		String year = StaticMethod.nullObject2String(request.getParameter("year"));
		String month = StaticMethod.nullObject2String(request.getParameter("month"));
		String inspectTemplateId = StaticMethod.nullObject2String(request.getParameter("inspectTemplateId"));
		IInspectSpotcheckMgr inspectSpotcheckMgr = (IInspectSpotcheckMgr)getBean("inspectSpotcheckMgr");
		String data = StaticMethod.getCurrentDateTime();
		
		String spotTemlateNames ="(";
		String spotTemlatItemeids ="(";
		for(int i=0;i<isdeletes.length;i++){
			if("yes".equals(isdeletes[i])){ //表示当前模板和模板项都的作废
				spotTemlateNames += "'"+bigItemNames[i]+"',";
				spotTemlatItemeids +="'"+spotTemplateIds[i]+"',";
			}
		}
		if(spotTemlateNames.endsWith(",")){
			spotTemlateNames =spotTemlateNames.substring(0, spotTemlateNames.length()-1)+")";
			String invalid_time = CommonSqlHelper.formatEmpty("invalid_time");
			String spotTemplatesql ="update pnr_spotcheck_template set invalid_time='"+data+"' where "+invalid_time+" and " +
					"template_id='"+inspectTemplateId+"' and bigitem_name in "+spotTemlateNames+"";
			inspectSpotcheckMgr.excuteSql(spotTemplatesql);
			spotTemlatItemeids =spotTemlatItemeids.substring(0, spotTemlatItemeids.length()-1)+")";
			String spotTemplateItemSql ="update pnr_spotcheck_template_item set invalid_time='"+data+"' where "+invalid_time+" " +
					" and spotcheck_template_id in "+spotTemlatItemeids+"";
			inspectSpotcheckMgr.excuteSql(spotTemplateItemSql);
		}
		for(int i=0;i<bigItemNames.length;i++){
			SpotcheckTemplate template = new SpotcheckTemplate();
			template.setBigitemName(bigItemNames[i]);
			template.setMarkRule(markRules[i]);
			template.setScore(Float.parseFloat(scores[i]));
			template.setTemplateId(inspectTemplateId);
			String check[] = checkBoxs[i].split("\\|");
			template.setSelectItemNum(check.length);
			if(check[0]!=null && check[0]!=""){
				List<String> itemIdList = new ArrayList<String>();
				//3.保存当前抽检模板下的模板项
				for(int j=0;j<check.length;j++){
					itemIdList.add(check[j]);
				}
				template.setItemNum(check.length);
				inspectSpotcheckMgr.saveSpotcheckTemplate(template, itemIdList);
			}else{//此时没有展开抽检模板项，当该抽检模板项下有抽检模板的时候应该更新
				int num = Integer.parseInt(itemNum[i]);
				if(num!=0){
					String sql = "update pnr_spotcheck_template set mark_rule='"+markRules[i]+"',score="+scores[i]+" where id='"+spotTemplateIds[i]+"'";
					inspectSpotcheckMgr.excuteSql(sql);
				}
			}
		}
		
		ActionForward actionForward = new ActionForward();
		actionForward.setPath("/inspectSpotcheckAction.do?method=findSpotcheckTemplateList&inspectTemplateId="+inspectTemplateId+"&year="+year+"&month="+month);
		actionForward.setRedirect(false);
		return actionForward;
	}
	
	/**
	 *  删除巡检抽检模板
	 */
	public ActionForward deleteSpotcheckTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		IInspectSpotcheckMgr inspectSpotcheckMgr = (IInspectSpotcheckMgr)getBean("inspectSpotcheckMgr");
		String spotcheckTemplateId = request.getParameter("spotcheckTemplateId");
		String inspectTemplateId = request.getParameter("inspectTemplateId");
		inspectSpotcheckMgr.deleteSpotcheckTemplate(spotcheckTemplateId);
		ActionForward actionForward = new ActionForward();
		actionForward.setPath("/inspectSpotcheckAction.do?method=findSpotcheckTemplateList&inspectTemplateId="+inspectTemplateId);
		actionForward.setRedirect(false);
		return actionForward;
	}
	
	/**
	 *  查询巡检计划列表
	 */
	public ActionForward findInspectPlanMainList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		String device = request.getParameter("device");
		String ischeck = StaticMethod.nullObject2String(request.getParameter("ischeck"));
		//是否是手机端在访问请求
		boolean isMobile = !StringUtils.isEmpty(device)&&"mobile".equals(device);
		LocalDate date = new LocalDate();
		LocalDate date2 = date.minusMonths(1);
		String year = StaticMethod.nullObject2String(request.getParameter("yearStringEqual"));
		String month = StaticMethod.nullObject2String(request.getParameter("monthStringEqual"));
		
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		search.addFilterEqual("approveStatus", 1); //查询审批通过的计划
		search.addFilterEqual("status", 1); //查询可用的计划
		
		/* 默认查询上个月的数据(计划) */
		if("".equals(year)){
			search.addFilterEqual("year", date2.getYear()); 
		}
		if("".equals(month)){
			search.addFilterEqual("month",date2.getMonthOfYear());
		}
		
		
		IInspectPlanMainMgr inspectPlanMainMgr = (IInspectPlanMainMgr)getBean("inspectPlanMainMgr");
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		
		if(isMobile){//如果是手机请求
			pageIndexString = StaticMethod.nullObject2String(request.getParameter("pageIndexString"));
		}
		
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		if(isMobile){
			search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE10);
			search.setMaxResults(CommonConstants.PAGE_SIZE10);
		}
		search.addSortDesc("createTime");
		
		SearchResult<InspectPlanMain> searchResult = inspectPlanMainMgr.searchAndCount(search);
		request.setAttribute("approveStatusMap",InspectConstants.approveStatusMap);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("list",searchResult.getResult() );
		request.setAttribute("size", searchResult.getTotalCount());
		request.setAttribute("year", date2.getYear());
		request.setAttribute("month", date2.getMonthOfYear());
		if(isMobile){//手机数据请求
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/plain");
			if(!searchResult.getResult().isEmpty()){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("returnList", searchResult.getResult());
				map.put("count",searchResult.getTotalCount());
				
				JSONObject jsonObject = JSONObject.fromObject(map);
				System.out.println( jsonObject.toString() ); 
				response.getWriter().write(jsonObject.toString());	
			}else{
				response.getWriter().write("".toString());	
			}
			return null;
		}
		return mapping.findForward("inspectPlanMainSpotcheckList");
	}
	
	/**
	 *  查询巡检资源抽检列表
	 */
	@SuppressWarnings("unchecked")
	public ActionForward findInspectPlanResSpotcheckList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		String device = request.getParameter("device");
		//是否是手机端在访问请求
		boolean isMobileRequest = !StringUtils.isEmpty(device)&&"mobile".equals(device);
		
		IInspectSpotcheckMgr inspectSpotcheckMgr = (IInspectSpotcheckMgr)getBean("inspectSpotcheckMgr");
		IInspectPlanMainMgr inspectPlanMainMgr = (IInspectPlanMainMgr)getBean("inspectPlanMainMgr");
		String planId = request.getParameter("planId");
		InspectPlanMain planMain = inspectPlanMainMgr.find(planId);
		String specialty = planMain.getSpecialty();
		
		InspectPlanRes res = new InspectPlanRes();
		res.setResName(request.getParameter("resName"));
		res.setResType(request.getParameter("resType"));
		res.setCity(request.getParameter("city"));
		res.setCountry(request.getParameter("country"));
		if(!StringUtils.isEmpty(StaticMethod.nullObject2String(request.getParameter("inspectState")))){
			res.setInspectState(Integer.parseInt(StaticMethod.nullObject2String(request.getParameter("inspectState"))));
		}
		
		Integer pageSize = CommonConstants.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		int pageIndex = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		if(isMobileRequest){
			
		}
		if(isMobileRequest){//
			pageSize = CommonConstants.PAGE_SIZE10;
			pageIndex = StaticMethod.nullObject2int(request.getParameter("pageIndex"));
		}
		PageModel pm;
		if(isMobileRequest){
			String cycleQuery = StaticMethod.nullObject2String(request.getParameter("cycleQuery"));
			String radiusQuery = StaticMethod.nullObject2String(request.getParameter("radiusQuery"));
			String res_longitude = StaticMethod.nullObject2String(request.getParameter("res_longitude"));
			String res_latitude = StaticMethod.nullObject2String(request.getParameter("res_latitude"));
			String resNameQuery = new String(java.net.URLDecoder.decode(StaticMethod.nullObject2String(request.getParameter("resNameQuery"))).getBytes("iso-8859-1"),"utf-8");
			String completeQuery = StaticMethod.nullObject2String(request.getParameter("completeQuery"));
			String userId = this.getUserId(request);
			HashMap<String, String> queryMap = new HashMap<String, String>();
			queryMap.put("completeQuery", completeQuery);
			queryMap.put("userId", userId);
			pm = inspectSpotcheckMgr.findInspectPlanResSpotcheckListMobile(planId, pageIndex*pageSize, pageSize,res,queryMap);
		}else{
			pm = inspectSpotcheckMgr.findInspectPlanResSpotcheckList(planId, pageIndex*pageSize, pageSize,res);
		}
		request.setAttribute("list", pm.getDatas());
		request.setAttribute("resultSize", pm.getTotal());
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("specialty", specialty);
		request.setAttribute("planId", planId);
		request.setAttribute("planMain", planMain);
		
		//根据省ID获取地市
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	List citys = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", citys);
    	if(isMobileRequest){
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/plain");
			if(!pm.getDatas().isEmpty()){
				JSONArray array = JSONArray.fromObject(pm.getDatas());
				response.getWriter().write(array.toString());	
			}else{
				response.getWriter().write("".toString());	
			}
			return null;
		}
		return mapping.findForward("inspectPlanResSpotcheckList");
	}
	
	/**
	 *  查询巡检项抽检列表
	 */
	@SuppressWarnings("unchecked")
	public ActionForward findInspectPlanItemSpotcheckList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		String device = request.getParameter("device");
		String detail = request.getParameter("detail"); //是否为详情查看
		//是否是手机端在访问请求
		boolean androidRequest = !StringUtils.isEmpty(device) && "mobile".equals(device);
		
		String inspectPlanResId = request.getParameter("inspectPlanResId");
		String resType = request.getParameter("resType");
		String planId = request.getParameter("planId");
		IInspectTemplateMgr inspectTemplateMgr = (IInspectTemplateMgr) getBean("inspectTemplateMgr");
		
		//查询资源对应的巡检模板
		Search search = new Search();
		search.addFilterEqual("resType", resType);
		search.addFilterEqual("status", 1);
		InspectTemplate inspectTemplate = inspectTemplateMgr.searchUnique(search);
		if(inspectTemplate == null && !androidRequest){
			request.setAttribute("msg", "该资源所属资源类型的巡检模板不存在");
			return mapping.findForward("failure");
		}else if(inspectTemplate == null && androidRequest){
			request.setAttribute("msg", "该资源所属资源类型的巡检模板不存在");
			return mapping.findForward("mobileError");
		}
		String inspectTemplateId = inspectTemplate.getId();
		
		IInspectSpotcheckMgr inspectSpotcheckMgr = (IInspectSpotcheckMgr)getBean("inspectSpotcheckMgr");
		List<Map<String,Object>> list = inspectSpotcheckMgr.findInspectPlanItemSpotcheckList(inspectTemplateId,inspectPlanResId);
		request.setAttribute("list", list);
		request.setAttribute("inspectPlanResId", inspectPlanResId);
		request.setAttribute("planId", planId);
		
		if(androidRequest){
			if(list.isEmpty()){
				request.setAttribute("msg", "<div align='center' style='font-size:30'>该资源无抽检项<div>");
				return mapping.findForward("mobileError");
			}else{
				return mapping.findForward("inspectPlanItemSpotcheckListByMobile");
			}
		}else{
			request.setAttribute("detail", detail);
			return mapping.findForward("inspectPlanItemSpotcheckList");
		}
	}
	
	/**
	 *  保存抽检结果
	 */
	@SuppressWarnings("unchecked")
	public ActionForward saveInspectSpotcheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		IInspectSpotcheckMgr inspectSpotcheckMgr = (IInspectSpotcheckMgr)getBean("inspectSpotcheckMgr");
		String inspectPlanResId = request.getParameter("inspectPlanResId");
		String planId = request.getParameter("planId");
		String userId = getUserId(request);
		
		//先保存抽检资源并获取抽检资源ID
		SpotcheckRes spotcheckRes = new SpotcheckRes();
		spotcheckRes.setPlanId(planId);
		spotcheckRes.setPlanResId(inspectPlanResId);
		spotcheckRes.setSpotcheckUser(userId);
		spotcheckRes.setSpotcheckTime(new Date());
		inspectSpotcheckMgr.saveSpotcheckRes(spotcheckRes);
		String spotcheckResId = spotcheckRes.getId();
		
		Map<String,Float> spotTmpMap = Maps.newHashMap();//key:抽检模板ID，value:该模板下扣分后的剩余分
		Map<String,Object[]> parameterMap = request.getParameterMap();//前台参数
		for(String key : parameterMap.keySet()){
			//key型如:score_2c9e9d89397004ba0139709938fd0060_873(score_抽检模板ID_巡检项ID)
			if(key.startsWith("score")){//如果是扣分分数
				String[] keyArray = key.split("_");
				String spotTmpId = keyArray[1]; //抽检模板ID
				String planItemId = keyArray[2];//巡检项ID
				SpotcheckTemplate spotTmp = null;
				if(!spotTmpMap.containsKey(spotTmpId)){
					spotTmp = inspectSpotcheckMgr.getSpotcheckTemplate(spotTmpId);
					spotTmpMap.put(spotTmpId, spotTmp.getScore());
				}
				String scoreStr = parameterMap.get(key)[0].toString();
				Float reduceScore = new Float(0); //扣分分数
				if(!StringUtils.isEmpty(scoreStr)){//如果填写了扣分分数
					reduceScore = Float.parseFloat(scoreStr);
					spotTmpMap.put(spotTmpId, spotTmpMap.get(spotTmpId)- reduceScore);
				}
				SpotcheckItem spotcheckItem = new SpotcheckItem();
				spotcheckItem.setPlanItemId(planItemId);
				spotcheckItem.setPlanResId(inspectPlanResId);
				spotcheckItem.setScore(reduceScore);
				spotcheckItem.setSpotcheckResId(spotcheckResId);
				inspectSpotcheckMgr.saveSpotcheckItem(spotcheckItem);
			}
		}
		Float remainTotalScore = 0f;//扣分后剩余总分数
		for(String key : spotTmpMap.keySet()){
			Float remainScore = spotTmpMap.get(key);
			if(remainScore < 0 ){
				remainScore = 0f;
			}
			remainTotalScore += remainScore;
		}
		//更新抽检资源得分
		spotcheckRes.setScore(remainTotalScore);
		inspectSpotcheckMgr.saveSpotcheckRes(spotcheckRes);
		
		ActionForward actionForward = new ActionForward();
		actionForward.setPath("/inspectSpotcheckAction.do?method=findInspectPlanResSpotcheckList&planId="+planId);
		actionForward.setRedirect(false);
		return actionForward;
	}
	/**
	 *  保存抽检结果(手机端)
	 */
	@SuppressWarnings("unchecked")
	public ActionForward saveInspectSpotcheckMobile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		IInspectSpotcheckMgr inspectSpotcheckMgr = (IInspectSpotcheckMgr)getBean("inspectSpotcheckMgr");
		String inspectPlanResId = request.getParameter("inspectPlanResId");
		String planId = request.getParameter("planId");
		String userId = getUserId(request);
		//先保存抽检资源并获取抽检资源ID
		SpotcheckRes spotcheckRes = new SpotcheckRes();
		spotcheckRes.setPlanId(planId);
		spotcheckRes.setPlanResId(inspectPlanResId);
		spotcheckRes.setSpotcheckUser(userId);
		spotcheckRes.setSpotcheckTime(new Date());
		inspectSpotcheckMgr.saveSpotcheckRes(spotcheckRes);
		String spotcheckResId = spotcheckRes.getId();
		
		Map<String,Float> spotTmpMap = Maps.newHashMap();//key:抽检模板ID，value:该模板下扣分后的剩余分
		Map<String,Object[]> parameterMap = request.getParameterMap();//前台参数
		for(String key : parameterMap.keySet()){
			//key型如:score_2c9e9d89397004ba0139709938fd0060_873(score_抽检模板ID_巡检项ID)
			if(key.startsWith("score")){//如果是扣分分数
				String[] keyArray = key.split("_");
				String spotTmpId = keyArray[1]; //抽检模板ID
				String planItemId = keyArray[2];//巡检项ID
				SpotcheckTemplate spotTmp = null;
				if(!spotTmpMap.containsKey(spotTmpId)){
					spotTmp = inspectSpotcheckMgr.getSpotcheckTemplate(spotTmpId);
					spotTmpMap.put(spotTmpId, spotTmp.getScore());
				}
				String scoreStr = parameterMap.get(key)[0].toString();
				Float reduceScore = new Float(0); //扣分分数
				if(!StringUtils.isEmpty(scoreStr)){//如果填写了扣分分数
					reduceScore = Float.parseFloat(scoreStr);
					spotTmpMap.put(spotTmpId, spotTmpMap.get(spotTmpId)- reduceScore);
				}
				SpotcheckItem spotcheckItem = new SpotcheckItem();
				spotcheckItem.setPlanItemId(planItemId);
				spotcheckItem.setPlanResId(inspectPlanResId);
				spotcheckItem.setScore(reduceScore);
				spotcheckItem.setSpotcheckResId(spotcheckResId);
				inspectSpotcheckMgr.saveSpotcheckItem(spotcheckItem);
			}
		}
		Float remainTotalScore = 0f;//扣分后剩余总分数
		for(String key : spotTmpMap.keySet()){
			Float remainScore = spotTmpMap.get(key);
			if(remainScore < 0 ){
				remainScore = 0f;
			}
			remainTotalScore += remainScore;
		}
		//更新抽检资源得分
		spotcheckRes.setScore(remainTotalScore);
		inspectSpotcheckMgr.saveSpotcheckRes(spotcheckRes);
		return null;
	}
}
