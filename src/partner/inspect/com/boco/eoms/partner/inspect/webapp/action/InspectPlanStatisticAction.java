package com.boco.eoms.partner.inspect.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.LocalDate;

import utils.PartnerPrivUtils;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.area.service.IAreaMgr;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanStatisticMgr;
import com.boco.eoms.partner.inspect.model.InspectStatisticArea;
import com.boco.eoms.partner.inspect.model.InspectStatisticPartner;
import com.boco.eoms.partner.inspect.webapp.form.InspectPlanResCountFromNew;
import com.boco.eoms.partner.inspect.webapp.form.InspectPlanResCountFromSpecialty;
import com.boco.eoms.partner.process.util.CommonUtils;
import com.boco.eoms.partner.property.util.CheckingStatisticsResultExport;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

/** 
 * Description: 巡检计划统计 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Aug 1, 2012 10:56:06 AM 
 */
public class InspectPlanStatisticAction extends BaseAction {
	/**
	 *  按地市统计
	 */
	public ActionForward findCityStatistic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		IInspectPlanStatisticMgr inspectPlanStatisticMgr = (IInspectPlanStatisticMgr) getBean("inspectPlanStatisticMgr");
		String year = StaticMethod.nullObject2String(request.getParameter("year"));
		String month = StaticMethod.nullObject2String(request.getParameter("month"));
		
		LocalDate date  = new LocalDate();
		
		if(StringUtils.isEmpty(year)){
			year = date.getYear()+"";
			month = date.getMonthOfYear()+"";
		}
		
//		InspectStatisticScheduler ps = new InspectStatisticScheduler();
//		ps.saveStatisticAreaData(date);
//		ps.saveStatisticPartnerData(date);
		
		List<InspectStatisticArea> list = Lists.newArrayList();
		if(!StringUtils.isEmpty(year) && !StringUtils.isEmpty(month) ){
			if(date.getYear() == Integer.parseInt(year) && date.getMonthOfYear() ==  Integer.parseInt(month)){
				list = inspectPlanStatisticMgr.findInspectPlanStatisticCity();
			}else{
				list = inspectPlanStatisticMgr.findInspectPlanStatisticCityHis(year, month);
			}
		}
		request.setAttribute("list", list);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		return mapping.findForward("cityStatistic");
	}
	/**
	 *  巡检统计  按区域统计
	 */
	public ActionForward findCityStatisticNew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
 		IInspectPlanStatisticMgr inspectPlanStatisticMgr = (IInspectPlanStatisticMgr) getBean("inspectPlanStatisticMgr");
 		//查询年份
		String year = "";
		//查询月份
		String month = "";
		//当查询不是当月时，获得当月的下一个月份
		String newMonth = "";
		boolean isNowMonth = true;
				//获得点击信息，1 查询步骤；2  返回步骤
				String buttonFlag = request.getParameter("buttonFlag");
				 if("2".equals(buttonFlag)){
					year = (String)request.getSession().getAttribute("inspectYear");
					month = (String)request.getSession().getAttribute("inspectMonth");
				}else{
					year = StaticMethod.nullObject2String(request.getParameter("year"));
					month = StaticMethod.nullObject2String(request.getParameter("month"));
				}
				
		
		
		LocalDate date  = new LocalDate();
		if(StringUtils.isEmpty(year)){
			year = date.getYear()+"";
			month = date.getMonthOfYear()+"";
		}
	/*	HashMap<String, String> usermap = (HashMap<String, String>) PartnerPrivUtils
				.userIsPersonnel(request);*/
		IAreaMgr iAreaMgr = (IAreaMgr) getBean("AreaMgrImpl");
		
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String deptId = sessionform.getDeptid();
		ITawSystemDeptManager systemDeptManager = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
        TawSystemDept tawSystemDept = systemDeptManager.getDeptinfobydeptid(deptId, "0");
        String AreaId = tawSystemDept.getAreaid();
        
		TawSystemArea tawSystemArea = iAreaMgr.getArea(AreaId);
		String ordercode = null;
		//flag用于判断是否是钻取点入还是 帐号登陆
		String flag = request.getParameter("flag");
		if(flag == null){
			ordercode = tawSystemArea.getOrdercode()+"";
		}else{
				if("1".equals(buttonFlag)){//查询处理
					ordercode = request.getParameter("ordercode");
					AreaId = request.getParameter("rootAreaId");
				}else if("2".equals(buttonFlag)){//回退处理
					ordercode = (String)request.getSession().getAttribute("ordercode");
					//对ordercode进行判断，0 获取city中的AreaId；1 获取country中的AreaId；2 获取doperson中的AreaId；
					if("3".equals(ordercode)){
						AreaId = (String)request.getSession().getAttribute("country");
					}else if("2".equals(ordercode)){
						AreaId = (String)request.getSession().getAttribute("city");
					}
					ordercode = Integer.toString((Integer.parseInt(ordercode)-1));
				}
			}
		//设置参数月份
		String one = date.getMonthOfYear()+"";
		String two = date.getYear()+"";
		if(month.equals(one)&&year.equals(two)){
			isNowMonth = true;
		}else if(month.equals("12")){
			isNowMonth = true;
		}else{
			isNowMonth = false;
			newMonth = Integer.toString((Integer.parseInt(month)+1));
		}
		
		System.out.println(year+"  "+month+"-----"+ordercode+"--------"+AreaId);
		
		List<InspectPlanResCountFromNew> resultList = 
		inspectPlanStatisticMgr.findInspectPlanStatisticCityHisNew(isNowMonth,ordercode,AreaId,year, month,newMonth);
		//根据钻取层次不同分别存上ordercode
		if("1".equals(ordercode)){
			request.getSession().setAttribute("city", AreaId);
		}else if("2".equals(ordercode)){
			request.getSession().setAttribute("country", AreaId);
		}else if("3".equals(ordercode)){
			request.getSession().setAttribute("doperson", AreaId);
		}
		//将查询条件存在session中，点击返回时用
		request.getSession().setAttribute("inspectYear", year);
		request.getSession().setAttribute("inspectMonth", month);
	    request.getSession().setAttribute("ordercode", ordercode);
		request.getSession().setAttribute("resultList", resultList);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		return mapping.findForward("cityStatisticNew");
	}
	
	/**
	 * 巡检统计  待巡检 各个专业 钻取
	 * 
	 * 
	 * @param
	 */
	public ActionForward findCityStatisticNewSpecialty(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 分页
		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();

		String ordercode = request.getParameter("ordercode");
		String AreaId = request.getParameter("rootAreaId");
		String Specialty = request.getParameter("specialty");
		String inspectState = request.getParameter("inspectState");
		String person = request.getParameter("name");
		person=new String(person.getBytes("ISO-8859-1"),"UTF-8"); 
		String year = request.getParameter("year");
		String month = request.getParameter("month");
				
        IInspectPlanStatisticMgr inspectPlanStatisticMgr = (IInspectPlanStatisticMgr) getBean("inspectPlanStatisticMgr");
		
		//List<InspectPlanResCountFromSpecialty> countList = 
	    Map<String, Object> map;
		map = inspectPlanStatisticMgr
		.findInspectPlanStatisticSpecialtyNew(inspectState,year,month,ordercode, AreaId ,Specialty,person, firstResult
				* pageSize, endResult
				* pageSize);
		
		int total = Integer.parseInt(map.get("size").toString());
		List<InspectPlanResCountFromSpecialty> workOrder;
		workOrder = (List<InspectPlanResCountFromSpecialty>) map.get("list");
		
		
		request.getSession().setAttribute("ordercode", ordercode);
		
		request.setAttribute("taskList", workOrder);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);
		return mapping.findForward("cityStatisticSpecialtyNew");
		
	}
	
	/**
	 *  按区县统计
	 */
	public ActionForward findCountryStatistic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		IInspectPlanStatisticMgr inspectPlanStatisticMgr = (IInspectPlanStatisticMgr) getBean("inspectPlanStatisticMgr");
		String city = request.getParameter("city");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		List<InspectStatisticArea> list = Lists.newArrayList();
		if(!StringUtils.isEmpty(year) && !StringUtils.isEmpty(month) ){
			if(new LocalDate().getYear() == Integer.parseInt(year) && new LocalDate().getMonthOfYear() ==  Integer.parseInt(month)){
				list = inspectPlanStatisticMgr.findInspectPlanStatisticCountry(city);
			}else{
				list = inspectPlanStatisticMgr.findInspectPlanStatisticCountryHis(year, month,city);
			}
		}
		request.setAttribute("city", city);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("list", list);
		return mapping.findForward("countryStatistic");
	}
	
	/**
	 *  按总代维公司统计
	 */
	public ActionForward findHearPartnerStatistic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		IInspectPlanStatisticMgr inspectPlanStatisticMgr = (IInspectPlanStatisticMgr) getBean("inspectPlanStatisticMgr");
		String year = StaticMethod.nullObject2String(request.getParameter("year"));
		String month = StaticMethod.nullObject2String(request.getParameter("month"));
		LocalDate date = new LocalDate();
		if(StringUtils.isEmpty(year)){
			year = date.getYear()+"";
			month= date.getMonthOfYear()+"";
		}
		List<InspectStatisticPartner> list = Lists.newArrayList();
		if(!StringUtils.isEmpty(year) && !StringUtils.isEmpty(month) ){
			if(date.getYear() == Integer.parseInt(year) && date.getMonthOfYear() ==  Integer.parseInt(month)){
				list = inspectPlanStatisticMgr.findInspectPlanStatisticHeadPnr();
			}else{
				list = inspectPlanStatisticMgr.findInspectPlanStatisticHeadPnrHis(year, month);
			}
		}
		request.setAttribute("list", list);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		return mapping.findForward("partnerHeadStatistic");
	}
	/**
	 *  按地市代维公司统计
	 */
	public ActionForward findCityPartnerStatistic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		IInspectPlanStatisticMgr inspectPlanStatisticMgr = (IInspectPlanStatisticMgr) getBean("inspectPlanStatisticMgr");
		String partnerId = request.getParameter("partnerId");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		List<InspectStatisticPartner> list = Lists.newArrayList();
		if(!StringUtils.isEmpty(year) && !StringUtils.isEmpty(month) ){
			if(new LocalDate().getYear() == Integer.parseInt(year) && new LocalDate().getMonthOfYear() ==  Integer.parseInt(month)){
				list = inspectPlanStatisticMgr.findInspectPlanStatisticCityPnr(partnerId);
			}else{
				list = inspectPlanStatisticMgr.findInspectPlanStatisticCityPnrHis(partnerId,year, month);
			}
		}
		request.setAttribute("partnerId", partnerId);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("list", list);
		return mapping.findForward("partnerCityStatistic");
	}
	/**
	 *  按区县代维公司统计
	 */
	public ActionForward findCountryPartnerStatistic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		IInspectPlanStatisticMgr inspectPlanStatisticMgr = (IInspectPlanStatisticMgr) getBean("inspectPlanStatisticMgr");
		String partnerId = request.getParameter("partnerId");
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		List<InspectStatisticPartner> list = Lists.newArrayList();
		if(!StringUtils.isEmpty(year) && !StringUtils.isEmpty(month) ){
			if(new LocalDate().getYear() == Integer.parseInt(year) && new LocalDate().getMonthOfYear() ==  Integer.parseInt(month)){
				list = inspectPlanStatisticMgr.findInspectPlanStatisticCountryPnr(partnerId);
			}else{
				list = inspectPlanStatisticMgr.findInspectPlanStatisticCountryPnrHis(partnerId,year, month);
			}
		}
		request.setAttribute("partnerId", partnerId);
		request.setAttribute("year", year);
		request.setAttribute("month", month);
		request.setAttribute("list", list);
		return mapping.findForward("partnerCountryStatistic");
	}
	/**
	 *  按区县代维公司统计
	 */
	public ActionForward exportTablePartnerStatistic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		String flag = request.getParameter("flag");
		List<InspectPlanResCountFromNew> resultList  = (List<InspectPlanResCountFromNew>)request.getSession().getAttribute("resultList");
		
		List<String> specialtyList = new ArrayList<String>();
		specialtyList.add("基站");
		specialtyList.add("接入网");
		specialtyList.add("铁塔");
		specialtyList.add("室分");
		specialtyList.add("WLAN");
		specialtyList.add("重点机房");
		List<String> levelList = new ArrayList<String>();
		levelList.add("基站VIP");levelList.add("基站A类");levelList.add("基站B类");levelList.add("基站C类");
		levelList.add("接入网A类");levelList.add("接入网B类");levelList.add("接入网C类");
		levelList.add("铁塔季度");levelList.add("铁塔月标准");
		levelList.add("室分标准");levelList.add("室分VIP");levelList.add("室分A类");levelList.add("室分B类");
		levelList.add("WLANA类");levelList.add("WLANB类");levelList.add("WLANC类");
		levelList.add("重点机房VIP");levelList.add("重点机房A类");levelList.add("重点机房B类");levelList.add("重点机房C类");
		String fileName="巡检区域统计表";
		CheckingStatisticsResultExport.exportStatisticsResultToXLSFile(flag,resultList,specialtyList,levelList,fileName,response,request);
		return null;
	}
	
}
