package com.boco.eoms.partner.deviceInspect.webapp.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.LocalDate;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanMgr;
import com.boco.eoms.partner.inspect.scheduler.InspectPlanScheduler;
import com.boco.eoms.partner.inspect.scheduler.InspectStatisticScheduler;
import com.boco.eoms.partner.inspect.util.InspectConstants;
import com.boco.eoms.partner.res.mgr.PnrResConfigMgr;
import com.boco.eoms.partner.res.model.PnrResConfig;
import com.boco.eoms.partner.res.webapp.form.PnrResConfigForm;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

/** 
 * Description: 巡检手动生成 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Aug 2, 2012 4:35:19 PM 
 */
public class DeviceInspectGenerateAction extends BaseAction {
	
	/**
	 *  产生全部资源
	 */
	public ActionForward generateInspectPlanResAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		LocalDate now = new LocalDate();
		int year = now.getYear();
		String monthStr = request.getParameter("resMonth");
		LocalDate date = new LocalDate(year,Integer.parseInt(monthStr),1);
		InspectPlanScheduler ps = new InspectPlanScheduler();
		ps.generateInspectPlanRes(date);
		request.setAttribute("cycleMap", InspectConstants.cycleMap);
		return mapping.findForward("inspectGenerate");
	}
	
	/**
	 *  分周期产生资源
	 */
	public ActionForward generateInspectPlanRes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		InspectPlanScheduler ps = new InspectPlanScheduler();
		String cycle = request.getParameter("cycle");
		String cycleStartTime= request.getParameter("cycleStartTime");
		String cycleEndTime = request.getParameter("cycleEndTime");
		ps.generateInspectPlanRes(cycle, cycleStartTime+" 00:00:00", cycleEndTime+" 23:59:59");
		request.setAttribute("cycleMap", InspectConstants.cycleMap);
		return mapping.findForward("inspectGenerate");
	}

	/**
	 *  产生巡检项
	 */
	public ActionForward generateInspectPlanItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		long startTime=System.currentTimeMillis();   //获取开始时间
		InspectPlanScheduler ps = new InspectPlanScheduler();
		ps.generateInspectPlanItem();
		long endTime=System.currentTimeMillis(); //获取结束时间
		System.out.println("为今天产生的所有地市的巡检资源生成巡检项产生共耗时:"+(endTime-startTime)+"ms");  
		request.setAttribute("cycleMap", InspectConstants.cycleMap);
		return mapping.findForward("inspectGenerate");
	}
	
	/**
	 *  产生巡检计划
	 */
	public ActionForward generateInspectPlan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		InspectPlanScheduler ps = new InspectPlanScheduler();
		LocalDate now = new LocalDate();
		long startTime1=System.currentTimeMillis();   //获取开始时间
		//生成巡检计划
		ps.generateInspectPlan();
		long endTime1=System.currentTimeMillis(); //获取结束时间
		System.out.println("生成巡检计划共耗时:"+(endTime1-startTime1)+"ms");  
		
		long startTime2=System.currentTimeMillis();   //获取开始时间
		//将本月必须执行的巡检资源关联到合适的计划上
		ps.updatePlanResForceAssignMatchedPlan(now);
		long endTime2=System.currentTimeMillis(); //获取结束时间
		System.out.println("将本月必须执行的巡检资源关联到合适的计划上共耗时:"+(endTime2-startTime2)+"ms");  
		
		long startTime3=System.currentTimeMillis();   //获取开始时间
		//更新巡检计划关联的资源数目
		ps.updatePlanResNum(now);
		long endTime3=System.currentTimeMillis(); //获取结束时间
		System.out.println("更新巡检计划关联的资源数目共耗时:"+(endTime3-startTime3)+"ms");  
		request.setAttribute("cycleMap", InspectConstants.cycleMap);
		return mapping.findForward("inspectGenerate");
	}
	
	
	/**
	 *  采集区域巡检统计数据
	 */
	public ActionForward saveStatisticAreaData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		request.setAttribute("cycleMap", InspectConstants.cycleMap);
		InspectStatisticScheduler ps = new InspectStatisticScheduler();
		LocalDate today = new LocalDate();
		ps.saveStatisticAreaData(today);
		return mapping.findForward("inspectGenerate");
	}
	
	/**
	 * 采集代维公司巡检统计数据
	 */
	public ActionForward saveStatisticPartnerData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		request.setAttribute("cycleMap", InspectConstants.cycleMap);
		InspectStatisticScheduler ps = new InspectStatisticScheduler();
		LocalDate today = new LocalDate();
		ps.saveStatisticPartnerData(today);
		return mapping.findForward("inspectGenerate");
	}
	
	/**
	 *  跳转到巡检资源页面
	 */
	public ActionForward toPnrResConfigGenerate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	List city = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", city);
    	PnrResConfigMgr pnrResConfiMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
    	String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		CommonSqlHelper.formatSearchNotEmpty(search, "executeObject","inspectCycle");
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		SearchResult<PnrResConfig> result = pnrResConfiMgr.searchAndCount(search);
		request.setAttribute("cycleMap", InspectConstants.cycleMap);
    	request.setAttribute("list",result.getResult());
		request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
		request.setAttribute("resultSize", result.getTotalCount());
		
		PnrResConfigForm pnrResConfigForm = new PnrResConfigForm();
		
		
		String resName = StaticMethod.nullObject2String(request.getParameter("resNameStringLike"));
		String specialty = StaticMethod.nullObject2String(request.getParameter("specialtyStringEqual"));
		String resType = StaticMethod.nullObject2String(request.getParameter("resTypeStringEqual"));
		String resLevel = StaticMethod.nullObject2String(request.getParameter("resLevelStringEqual"));
		String cityString = StaticMethod.nullObject2String(request.getParameter("cityStringEqual"));
		String countryString = StaticMethod.nullObject2String(request.getParameter("countryStringEqual"));
		pnrResConfigForm.setResName(resName);
		pnrResConfigForm.setSpecialty(specialty);
		pnrResConfigForm.setResType(resType);
		pnrResConfigForm.setCity(cityString);
		pnrResConfigForm.setCountry(countryString);
		pnrResConfigForm.setResLevel(resLevel);
		request.setAttribute("pnrResConfigForm", pnrResConfigForm);
		return mapping.findForward("toPnrResConfigGenerate");
	}
	
	public void generateInspectPlanres(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		IInspectPlanMgr inspectPlanMgr   = (IInspectPlanMgr) getBean("inspectPlanMgr");
		PrintWriter pw =  response.getWriter();
		
		String ids = StaticMethod.null2String(request
				.getParameter("generate"));
		
		inspectPlanMgr.generatePlanRes(ids.substring(0, ids.length()-1));
		
		System.out.println(ids.substring(0, ids.length()-1));
		try {
			pw.print("{'success': true, 'msg': '分配成功！'}");
		} catch (Exception e) {
			pw.print("{'faild': true, 'msg': '分配失败！'}");
		}
		
	}
	
	public ActionForward toInspectGenerate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		request.setAttribute("cycleMap", InspectConstants.cycleMap);
		return mapping.findForward("inspectGenerate");
	}
	
}
