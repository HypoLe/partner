package com.boco.eoms.partner.inspect.webapp.action;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.LocalDate;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.deviceInspect.scheduler.DeviceInspectScheduler;
import com.boco.eoms.partner.deviceInspect.scheduler.TransLineInspectScheduler;
import com.boco.eoms.partner.deviceInspect.service.IDeviceInspectService;
import com.boco.eoms.partner.deviceInspect.service.ITransLineService;
import com.boco.eoms.partner.deviceInspect.switchcfg.PnrDeviceInspectSwitchConfig;
import com.boco.eoms.partner.inspect.scheduler.InspectPlanScheduler;
import com.boco.eoms.partner.inspect.scheduler.InspectStatisticScheduler;
import com.boco.eoms.partner.inspect.util.InspectConstants;
import com.boco.eoms.partner.res.mgr.PnrResConfigMgr;
import com.boco.eoms.partner.res.model.PnrResConfig;
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
public class InspectGenerateAction extends BaseAction {
	
	/**
	 *  产生全部资源
	 */
	public ActionForward generateInspectPlanResAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		LocalDate now = new LocalDate();
		int year = now.getYear();
		String monthStr = request.getParameter("resMonth");
		LocalDate date = new LocalDate(year,Integer.parseInt(monthStr),1);
		
		PnrDeviceInspectSwitchConfig cfg = (PnrDeviceInspectSwitchConfig)this.getServlet().getServletContext()
			.getAttribute("pnrInspect2SwitchConfig");
		if(cfg.isOpenMainSwitch()){
			DeviceInspectScheduler dis = new DeviceInspectScheduler();
			dis.generateInspectPlanRes(date);
		}else{
			InspectPlanScheduler ps = new InspectPlanScheduler();
			ps.generateInspectPlanRes(date);
		}
		
		
		request.setAttribute("cycleMap", InspectConstants.cycleMap);
		return mapping.findForward("inspectGenerate");
	}
	/**
	 *  产生线路巡检元任务
	 */
	public ActionForward generateTLPlanRes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		LocalDate now = new LocalDate();
		int year = now.getYear();
		String monthStr = request.getParameter("resMonth");
		LocalDate date = new LocalDate(year,Integer.parseInt(monthStr),1);
		
		TransLineInspectScheduler tlis = new TransLineInspectScheduler();
		tlis.generateInspectPlanRes(date);
		
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
		
		PnrDeviceInspectSwitchConfig cfg = (PnrDeviceInspectSwitchConfig)this.getServlet().getServletContext()
			.getAttribute("pnrInspect2SwitchConfig");
		if(cfg.isOpenMainSwitch()){
			DeviceInspectScheduler dis = new DeviceInspectScheduler();
			dis.generateInspectPlanItem();
		}else{
			InspectPlanScheduler ps = new InspectPlanScheduler();
			ps.generateInspectPlanItem();
		}
		
		long endTime=System.currentTimeMillis(); //获取结束时间
		System.out.println("为今天产生的所有地市的巡检资源生成巡检项产生共耗时:"+(endTime-startTime)+"ms");  
		request.setAttribute("cycleMap", InspectConstants.cycleMap);
		return mapping.findForward("inspectGenerate");
	}
	/**
	 *  产生线路巡检项
	 */
	public ActionForward generateTLPlanItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		long startTime=System.currentTimeMillis();   //获取开始时间
		
			DeviceInspectScheduler dis = new DeviceInspectScheduler();
			dis.generateInspectPlanItem();
		
		long endTime=System.currentTimeMillis(); //获取结束时间
		System.out.println("为今天产生的所有地市的线路巡检资源生成巡检项产生共耗时:"+(endTime-startTime)+"ms");  
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
	 * 构造生成临时元任务列表查询的search
	 * @param request
	 * @return
	 */
	public Search markPnrResConfigSearch(HttpServletRequest request){
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String transLine = StaticMethod.null2String(request.getParameter("transLine"));
		PnrDeviceInspectSwitchConfig transLineswitch = (PnrDeviceInspectSwitchConfig) request.getSession().getServletContext().getAttribute("pnrInspect2SwitchConfig");
		ITawSystemDeptManager deptMgr = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
		//不管是移动还是代维，在taw_system_dept中都有记录，也能查到对应的地域
		TawSystemDept dept = deptMgr.getTawSystemDept(sessionForm.getDeptpriid());
		
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		
		//如果是工单巡检且是突发任务
		if(InspectConstants.getSheetInspectSwitch()){
			CommonSqlHelper.formatSearchNotEmpty(search, "executeObject");
		}else{
			
			CommonSqlHelper.formatSearchNotEmpty(search, "executeObject","inspectCycle");
			//系统，自维人员区县以上级人员
			if((dept.getDeptId().startsWith("201")||dept.getDeptId().startsWith("101"))&&dept.getDeptId().length()<=7){
				search.addFilterLike("country", dept.getAreaid()+"%");				
			}else{				
				search.addFilterLike("executeDept", dept.getDeptId()+"%");				

			}
		}
		
		//区分是不是线路专业的临时元任务新增
		if("yes".equals(transLine)){
			if(transLineswitch.isOpenTransLineInspect()){
				search.addFilterEqual("specialty", "1122502");
				request.setAttribute("transLine", transLine);
			}else{
				search.addFilterEqual("specialty", "1122a502");
			}
		}else{
			if(transLineswitch.isOpenTransLineInspect()){
				search.addFilterNotEqual("specialty", "1122502");
			}else{
				
			}
		}
		
//		//区分是不是线路专业的临时元任务新增
//		if("yes".equals(transLine) && transLineswitch.isOpenTransLineInspect()){
//			search.addFilterEqual("specialty", "1122502");
////			CommonSqlHelper.formatSearchNotEmpty(search, "tlArrivedRate");
////			CommonSqlHelper.formatSearchNotEmpty(search, "tlReportInterval");
//			request.setAttribute("transLine", transLine);
//		}else{
//			search.addFilterNotEqual("specialty", "1122502");
//		}
		
		/*
		if(!"admin".equals(sessionForm.getUserid())){
			search.addFilterLike("country", dept.getAreaid()+"%");
		}
		*/
		return search;
	}
	
	/**
	 *  跳转到巡检资源页面
	 */
	@SuppressWarnings("unchecked")
	public ActionForward toPnrResConfigGenerate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		PnrDeviceInspectSwitchConfig cfg = (PnrDeviceInspectSwitchConfig)this.getServlet()
				.getServletContext().getAttribute("pnrInspect2SwitchConfig");
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	List city = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", city);
    	PnrResConfigMgr pnrResConfiMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
    	String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
    	Search search = this.markPnrResConfigSearch(request);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		SearchResult<PnrResConfig> result = pnrResConfiMgr.searchAndCount(search);
		request.setAttribute("cycleMap", InspectConstants.cycleMap);
    	request.setAttribute("list",result.getResult());
		request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
		request.setAttribute("resultSize", result.getTotalCount());
		
		PnrResConfig pnrResConfigForm = new PnrResConfig();
		String resName = StaticMethod.nullObject2String(request.getParameter("resNameStringLike"));
		String specialty = StaticMethod.nullObject2String(request.getParameter("specialtyStringEqual"));
		String resType = StaticMethod.nullObject2String(request.getParameter("resTypeStringEqual"));
		String resLevel = StaticMethod.nullObject2String(request.getParameter("resLevelStringEqual"));
		String cityString = StaticMethod.nullObject2String(request.getParameter("cityStringEqual"));
		String countryString = StaticMethod.nullObject2String(request.getParameter("countryStringEqual"));
		String tlDisStringLike = StaticMethod.nullObject2String(request.getParameter("tlDisStringLike"));
		String tlWireStringLike = StaticMethod.nullObject2String(request.getParameter("tlWireStringLike"));
		String tlSegStringLike = StaticMethod.nullObject2String(request.getParameter("tlSegStringLike"));
		pnrResConfigForm.setResName(resName);
		pnrResConfigForm.setSpecialty(specialty);
		pnrResConfigForm.setResType(resType);
		pnrResConfigForm.setCity(cityString);
		pnrResConfigForm.setCountry(countryString);
		pnrResConfigForm.setResLevel(resLevel);
		
		if(cfg.isOpenTransLineInspect()){
			pnrResConfigForm.setTlDis(tlDisStringLike);
			pnrResConfigForm.setTlSeg(tlSegStringLike);
			pnrResConfigForm.setTlWire(tlWireStringLike);
		}
		
		request.setAttribute("pnrResConfigForm", pnrResConfigForm);
		
		//判断是否是点击了查询按钮进行的查询
		String isQuery = StaticMethod.nullObject2String(request.getParameter("isQuery"));
		request.setAttribute("isQuery", isQuery);
		
//		if("yes".equals(transLine)){
//			return mapping.findForward("pnrTransLineGenerateList");
//		}else{
//			return mapping.findForward("toPnrResConfigGenerate");
//		}
		return mapping.findForward("toPnrResConfigGenerate");
	}
	
	/**
	 * 生成临时元任务
	 */
	public void generateInspectPlanres(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		PrintWriter pw =  response.getWriter();
		String queryGenerate = request.getParameter("queryGenerate"); //是否是按查询条件生成
		String ids = "";
		if("true".equals(queryGenerate)){ //按查询条件生成
			Search search = this.markPnrResConfigSearch(request);
			PnrResConfigMgr pnrResConfiMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
			List<PnrResConfig> list = pnrResConfiMgr.search(search);
			for (PnrResConfig pnrResConfig : list) {
				ids += "'" + pnrResConfig.getId() + "',";
			}
		}else{//按勾选项生成
			ids = StaticMethod.null2String(request.getParameter("generate"));
		}
		if(!"".equals(ids)){
			ids = ids.substring(0,ids.length()-1);
		}else{
			pw.print("failure");
			return;
		}
		
		IDeviceInspectService deviceInspectService = (IDeviceInspectService)ApplicationContextHolder.getInstance().getBean("deviceInspectService");
		String transLine = StaticMethod.null2String(request.getParameter("transLine"));
		
		//如果是工单巡检且是突发任务
		if(InspectConstants.getSheetInspectSwitch()){
			LocalDate date = new LocalDate();
			String planStartTime = "";
			String planEndTime = "";
			planStartTime =  date+" 00:00:00";
			planEndTime = date.plusDays(InspectConstants.BURST_RES_CYCLE_DAY)+" 23:59:59";
			
			if("yes".equals(transLine)){
				ITransLineService transLineService = (ITransLineService)ApplicationContextHolder.getInstance().getBean("transLineService");
				transLineService.generateBurstTransLine(ids, planStartTime, planEndTime);
			}else{
				deviceInspectService.generateBurstDeviceInspect(ids,planStartTime,planEndTime);
			}
		}else{
			if("yes".equals(transLine)){
				ITransLineService transLineService = (ITransLineService)ApplicationContextHolder.getInstance().getBean("transLineService");
				transLineService.generateBurstTransLine(ids);
			}else{
				//设定巡检周期
				String[] idStrings =ids.split(",");
				
				for(int i=0;i<idStrings.length;i++){
					String id = idStrings[i].replace("'", "");
					//生成巡检周期
					List<Map<String,String>> list =deviceInspectService.generateCycleTime(id);
					
					Map<String,String> m = list.get(0); 
					
					for(String key : m.keySet()){ 	
						
					    deviceInspectService.generateBurstDeviceInspect(idStrings[i],key,m.get(key),"待用参数");					
					} 
				}
				
			}
		}
		pw.print("success");
	}
	
	public ActionForward toInspectGenerate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		request.setAttribute("cycleMap", InspectConstants.cycleMap);
		return mapping.findForward("inspectGenerate");
	}
	
}
