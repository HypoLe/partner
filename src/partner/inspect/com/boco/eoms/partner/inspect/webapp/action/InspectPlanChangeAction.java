package com.boco.eoms.partner.inspect.webapp.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.deviceInspect.switchcfg.PnrDeviceInspectSwitchConfig;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanApproveMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanChangeMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanMainMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanResChangeMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanResMgr;
import com.boco.eoms.partner.inspect.model.InspectPlanApprove;
import com.boco.eoms.partner.inspect.model.InspectPlanChange;
import com.boco.eoms.partner.inspect.model.InspectPlanMain;
import com.boco.eoms.partner.inspect.model.InspectPlanRes;
import com.boco.eoms.partner.inspect.model.InspectPlanResChange;
import com.boco.eoms.partner.inspect.util.InspectConstants;
import com.boco.eoms.partner.inspect.util.InspectUtils;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

/** 
 * Description: 巡检计划变更 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Jul 23, 2012 3:24:00 PM 
 */
public class InspectPlanChangeAction extends BaseAction {
	
	/**
	 *  查询巡检计划列表
	 */
	public ActionForward findInspectPlanMainList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		search.addFilterEqual("approveStatus", 1); //查询审批通过的计划
		search.addFilterEqual("status", 1); //查询可用的计划
		IInspectPlanMainMgr inspectPlanMainMgr = (IInspectPlanMainMgr)getBean("inspectPlanMainMgr");
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		search.addSortDesc("createTime");
		
		//权限控制
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String userDeptId = sessionForm.getDeptid();
		String userId = sessionForm.getUserid();
		if(!"admin".equals(userId) || !userDeptId.startsWith("1")){
			search.addFilterLike("deptMagId",userDeptId + "%");
		}
		
		SearchResult<InspectPlanMain> searchResult = inspectPlanMainMgr.searchAndCount(search);
		
		request.setAttribute("approveStatusMap",InspectConstants.approveStatusMap);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("list", searchResult.getResult());
		request.setAttribute("size", searchResult.getTotalCount());
		
		return mapping.findForward("inspectPlanMainList");
	}
	
	/**
	 *  查询巡检计划变更列表
	 */
	public ActionForward findInspectPlanChangeList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		String planId = request.getParameter("id");
		Search search = new Search();
		search.addFilterEqual("planId", planId);
		
		IInspectPlanChangeMgr inspectPlanChangeMgr = (IInspectPlanChangeMgr)getBean("inspectPlanChangeMgr");
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		search.addSortDesc("changeTime");
		SearchResult<InspectPlanChange> searchResult = inspectPlanChangeMgr.searchAndCount(search);
		
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("list", searchResult.getResult());
		request.setAttribute("size", searchResult.getTotalCount());
		request.setAttribute("approveStatusMap",InspectConstants.approveStatusMap);
		request.setAttribute("planId", planId);
		
		//只能为本年度本月份的巡检计划新增变更
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		IInspectPlanMainMgr inspectPlanMainMgr = (IInspectPlanMainMgr)getBean("inspectPlanMainMgr");
		InspectPlanMain planMain = inspectPlanMainMgr.find(planId);
		int year = new LocalDate().getYear();
		int month = new LocalDate().getMonthOfYear();
		if(year == Integer.parseInt(planMain.getYear()) && month == Integer.parseInt(planMain.getMonth()) 
				&& userId.equals(planMain.getCreator())){
			request.setAttribute("haveAddRight", true);
		}
		return mapping.findForward("inspectPlanChangeList");
	}
	
	/**
	 *  跳转到巡检计划变更编辑页面
	 */
	public ActionForward toAddInspectPlanChange(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		IInspectPlanChangeMgr inspectPlanChangeMgr = (IInspectPlanChangeMgr)getBean("inspectPlanChangeMgr");
		String id = request.getParameter("id");
		String planId = request.getParameter("planId");
		if(!StringUtils.isEmpty(id)){//修改变更
			InspectPlanChange planChange = inspectPlanChangeMgr.find(id);
			request.setAttribute("plan", planChange);
			request.setAttribute("id", id);
		}
		request.setAttribute("planId", planId);
		return mapping.findForward("inspectPlanChangeForm");
	}
	
	/**
	 *  巡检计划变更新增
	 */
	public ActionForward addInspectPlanChange(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		IInspectPlanChangeMgr inspectPlanChangeMgr = (IInspectPlanChangeMgr)getBean("inspectPlanChangeMgr");
		String id = request.getParameter("id");
		String planId = request.getParameter("planId");
		InspectPlanChange inspectPlanChange = null;
		if(StringUtils.isEmpty(id)){ //新增
			inspectPlanChange = new InspectPlanChange();
			inspectPlanChange.setPlanId(planId);
			inspectPlanChange.setChangeTitle(request.getParameter("changeTitle"));
			inspectPlanChange.setChangeOption(request.getParameter("changeOption"));
			//todo
			inspectPlanChange.setApproveObject("");
			inspectPlanChange.setApproveObjectType("");
			inspectPlanChange.setState(InspectConstants.APPROVE_STATUS_NOCOMMIT);
		}else{//修改
			inspectPlanChange = inspectPlanChangeMgr.find(id);
			inspectPlanChange.setChangeTitle(request.getParameter("changeTitle"));
			inspectPlanChange.setChangeOption(request.getParameter("changeOption"));
		}
		inspectPlanChange.setChangeTime(new Date());
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		inspectPlanChange.setCreator(sessionForm.getUserid());
		inspectPlanChangeMgr.save(inspectPlanChange);
		ActionForward actionForward = new ActionForward();
		actionForward.setPath("/inspectPlanChange.do?method=findInspectPlanChangeList&id="+planId);
		actionForward.setRedirect(false);
		return actionForward;
	}
	
	/**
	 *  删除巡检计划变更
	 */
	public ActionForward removeInspectPlanChange(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		String planChgId = request.getParameter("id");
		String planId = request.getParameter("planId");
		IInspectPlanChangeMgr inspectPlanChangeMgr = (IInspectPlanChangeMgr)getBean("inspectPlanChangeMgr");
		IInspectPlanResChangeMgr inspectPlanResChangeMgr = (IInspectPlanResChangeMgr)getBean("inspectPlanResChangeMgr");
		IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
		if(StringUtils.isEmpty(planChgId)){
			return mapping.findForward("failure");
		}else{
			Search search = new Search();
			search.addFilterEqual("planId", planId);
			search.addFilterEqual("planChangeId", planChgId);
			List<InspectPlanResChange> resChgList = inspectPlanResChangeMgr.search(search);
			for (InspectPlanResChange resChg : resChgList) {
				//删除变更资源
				inspectPlanResChangeMgr.remove(resChg);
				//将变更中的巡检资源改为未在变更中
				InspectPlanRes res = inspectPlanResMgr.get(resChg.getPlanResId());
				res.setChangeState(0);
				res.setPlanChangeId(null);
				inspectPlanResMgr.save(res);
			}
			inspectPlanChangeMgr.removeById(planChgId);
		}
		ActionForward actionForward = new ActionForward();
		actionForward.setPath("/inspectPlanChange.do?method=findInspectPlanChangeList&id="+planId);
		actionForward.setRedirect(false);
		return actionForward;
	}
	
	
	/**
	 *  巡检资源变更列表
	 */
	@SuppressWarnings("unchecked")
	public ActionForward findInspectPlanResChangeList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
		IInspectPlanMainMgr inspectPlanMainMgr = (IInspectPlanMainMgr)getBean("inspectPlanMainMgr");
		String planId = request.getParameter("planId");
		String planChgId = request.getParameter("id");
		InspectPlanMain planMain = inspectPlanMainMgr.find(planId);
		String specialty = planMain.getSpecialty();
		String deptMagId = planMain.getDeptMagId();
		final Integer pageSize = CommonConstants.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		final int pageIndex = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		PnrDeviceInspectSwitchConfig transLineswitch = (PnrDeviceInspectSwitchConfig) request.getSession().getServletContext().getAttribute("pnrInspect2SwitchConfig");
		if(specialty.equals("1122502") && transLineswitch.isOpenTransLineInspect()){
			request.setAttribute("transLine", "yes");
		}
		//资源周期结束时间必须大于计划所在月的第一天
		String beginMonthDay = planMain.getYear()+"-"+planMain.getMonth()+"-01 00:00:00";
		beginMonthDay = CommonSqlHelper.formatDateTime(beginMonthDay);
		
		String whereStr = " where 1=1 and (planId='"+planId+"' or planId is null or planId='') and specialty='"+specialty
			+"' and executeDept like '"+deptMagId+"%'"
			+" and (planChangeId='"+planChgId+"' or planChangeId is null or planChangeId='') " 
			+"and inspectState=0";
		
		String cycleEndTimeEmpty = CommonSqlHelper.formatEmpty("cycleEndTime");
		String planStartTimeNotEmpty = CommonSqlHelper.formatNotEmpty("planStartTime");
		String planEndTimeNotEmpty = CommonSqlHelper.formatNotEmpty("planEndTime");
		
		
		if(InspectConstants.getSheetInspectSwitch()){//突发任务周期开始结束时间为空
			whereStr = whereStr + " and (cycleEndTime>"+beginMonthDay+" or ("
				+cycleEndTimeEmpty+" and "+planStartTimeNotEmpty+" and "+planEndTimeNotEmpty+"))";
		}else{
			whereStr = whereStr + " and cycleEndTime>"+beginMonthDay;
		}
		
		if(!StringUtils.isEmpty(request.getParameter("resType")) ){
			whereStr += " and resType='"+request.getParameter("resType")+"'";
		}
		if(!StringUtils.isEmpty(request.getParameter("resLevel")) ){
			whereStr += " and resLevel='"+request.getParameter("resLevel")+"'";
		}
		if(!StringUtils.isEmpty(request.getParameter("city")) ){
			whereStr += " and city='"+request.getParameter("city")+"'";
		}
		if(!StringUtils.isEmpty(request.getParameter("country")) ){
			whereStr += " and country='"+request.getParameter("country")+"'";
		}
		if(!StringUtils.isEmpty(request.getParameter("inspectCycle")) ){
			whereStr += " and inspectCycle='"+request.getParameter("inspectCycle")+"'";
		}
		if(!StringUtils.isEmpty(request.getParameter("resName")) ){
			whereStr += " and resName like '%"+request.getParameter("resName")+"%'";
		}
		
		if(!StringUtils.isEmpty(request.getParameter("tlDis")) ){
			whereStr += " and tlDis like '"+request.getParameter("tlDis")+"%'";
		}
		if(!StringUtils.isEmpty(request.getParameter("tlWire")) ){
			whereStr += " and tlWire like '"+request.getParameter("tlWire")+"%'";
		}
		if(!StringUtils.isEmpty(request.getParameter("tlSeg")) ){
			whereStr += " and tlSeg like '"+request.getParameter("tlSeg")+"%'";
		}
		Map map = (Map) inspectPlanResMgr.findPlanResWithChangeList(pageIndex, pageSize, whereStr);
		List list = (List) map.get("result");
		request.setAttribute("list", list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("planId", planId);
		request.setAttribute("planChgId", planChgId);
		request.setAttribute("planName", planMain.getPlanName());
		request.setAttribute("specialty", planMain.getSpecialty());
		request.setAttribute("cycleMap", InspectConstants.cycleMap);
		
		//根据省ID获取地市
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	List citys = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", citys);
		return mapping.findForward("inspectPlanResChangeList");
	}
	
	/**
	 *  跳转到巡检资源变更页面
	 */
	public ActionForward toEditInspectPlanResChange(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
		IInspectPlanResChangeMgr inspectPlanResChangeMgr = (IInspectPlanResChangeMgr)getBean("inspectPlanResChangeMgr");
		String resId = request.getParameter("resId");
		String planChgId = request.getParameter("planChgId");
		InspectPlanRes res = inspectPlanResMgr.get(Long.parseLong(resId));
		
		Search search = new Search();
		search.addFilterEqual("planResId", resId);
		search.addFilterEqual("planChangeId", planChgId);
		
		InspectPlanResChange resChg = inspectPlanResChangeMgr.searchUnique(search);
		if(resChg == null){
			resChg = new InspectPlanResChange();
		}
		request.setAttribute("res", res);
		request.setAttribute("resChg", resChg);
		request.setAttribute("planChgId", planChgId);
		request.setAttribute("cycleMap", InspectConstants.cycleMap);
		request.setAttribute("planId", request.getParameter("planId"));
		
		//如果是工单巡检且是突发任务
		if(InspectConstants.getSheetInspectSwitch() && res.getBurstFlag()==1){
			return mapping.findForward("inspectPlanResChangeBurstForm");
		}
		
		//能设置的巡检开始、结束日期最大、最小值
		Date minConfigDate = null;
		Date maxConfigDate = null;
		boolean isWeekCycle = false; //巡检资源周期是否为周
		String inspectCycle = res.getInspectCycle();
		if(inspectCycle.equals(InspectConstants.PERIOD_OF_WEEK)){
			isWeekCycle = true;
			LocalDate cycleEndDate = new LocalDate(res.getCycleEndTime());
			LocalDate currentDate = new LocalDate();
			if(cycleEndDate.getMonthOfYear() > currentDate.getMonthOfYear()){
				maxConfigDate = new DateTime(currentDate.dayOfMonth().withMaximumValue().toString()+"T23:59:59").toDate();
			}else{
				maxConfigDate = res.getCycleEndTime();
			}
			minConfigDate = res.getCycleStartTime();
		}
		request.setAttribute("minConfigDate", InspectUtils.getMinConfigDate(isWeekCycle,minConfigDate));
		request.setAttribute("maxConfigDate", InspectUtils.getMaxConfigDate(isWeekCycle,maxConfigDate));
		
		//如果巡检周期结束日期所在月份在本月以后，那么可以变更为不在本月执行
		if(new LocalDate(res.getCycleEndTime()).getMonthOfYear() > new LocalDate().getMonthOfYear()){
			request.setAttribute("changeTypeRight",true);
		}

		return mapping.findForward("inspectPlanResChangeForm");
	}
	
	/**
	 *  巡检资源变更编辑
	 */
	public ActionForward editInspectPlanResChange(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
		IInspectPlanResChangeMgr inspectPlanResChangeMgr = (IInspectPlanResChangeMgr)getBean("inspectPlanResChangeMgr");
		IInspectPlanChangeMgr inspectPlanChangeMgr = (IInspectPlanChangeMgr)getBean("inspectPlanChangeMgr");
		
		String resId = request.getParameter("resId");
		String resChgId = request.getParameter("resChgId");
		String planChgId = request.getParameter("planChgId");
		String planId = StaticMethod.null2String( request.getParameter("planId"));
		String plan = StaticMethod.null2String(request.getParameter("plan"));   //在之前没有关联到计划上的资源，此时作为计划的id
		InspectPlanChange planChange = inspectPlanChangeMgr.find(planChgId);
		
		//变更后属性
		String chgPlanStartTime = request.getParameter("chgPlanStartTime");
		String chgPlanEndTime = request.getParameter("chgPlanEndTime");
		String chgExecuteObject = request.getParameter("chgExecuteObject");
		String chgChangeType = request.getParameter("changeType");

		//变更前属性
		InspectPlanRes res = inspectPlanResMgr.get(Long.parseLong(resId));
		Date prePlanStartTime = res.getPlanStartTime();
		Date prePlanEndTime = res.getPlanEndTime();
		String preExecuteObject = res.getExecuteObject();
		if(!"".equals(planId)){    //表示当前资源已经关联到计划上
			//变更前是否本月执行
			Integer preChangeType = null;
			if(StringUtils.isEmpty(res.getPlanId())){ //未与计划关联的资源代表本月不执行
				preChangeType = 0;
			}else{
				preChangeType = 1;
			}
			
			InspectPlanResChange resChg = inspectPlanResChangeMgr.find(resChgId);
			if(resChg == null){
				resChg = new InspectPlanResChange();
				resChg.setPlanId(res.getPlanId());
				resChg.setPlanResId(Long.parseLong(resId));
				resChg.setPlanChangeId(planChgId);
			}
			
			boolean isChange = false; //计划是否变更
			if(0 == preChangeType){//如果变更前本月不执行
				if("1".equals(chgChangeType)){//变更为本月执行
					isChange = true;
					if(!StringUtils.isEmpty(chgPlanStartTime)){
						resChg.setPlanStartTime(new DateTime(chgPlanStartTime+"T00:00:00").toDate());
					}
					if(!StringUtils.isEmpty(chgPlanEndTime)){
						resChg.setPlanEndTime(new DateTime(chgPlanEndTime+"T23:59:59").toDate());
					}
					resChg.setChangeType(Integer.parseInt(chgChangeType));
				}else{//恢复为本月不执行
					resChg.setPlanStartTime(null);
					resChg.setPlanEndTime(null);
					resChg.setChangeType(null);
				}
			}else{//如果变更前本月要执行
				if(!StringUtils.isEmpty(chgPlanStartTime) && !(new LocalDate(prePlanStartTime).toString()).equals(chgPlanStartTime)){
					isChange = true;
					resChg.setPlanStartTime(new DateTime(chgPlanStartTime+"T00:00:00").toDate());
				}else{
					resChg.setPlanStartTime(null);
				}
				if(!StringUtils.isEmpty(chgPlanEndTime) && !(new LocalDate(prePlanEndTime).toString()).equals(chgPlanEndTime)){
					isChange = true;
					resChg.setPlanEndTime(new DateTime(chgPlanEndTime+"T23:59:59").toDate());
				}else{
					resChg.setPlanEndTime(null);
				}
				if(!StringUtils.isEmpty(chgChangeType) && !"-1".equals(chgChangeType) 
						&& !chgChangeType.equals(preChangeType.toString())){
					isChange = true;
					resChg.setChangeType(Integer.parseInt(chgChangeType));
				}else{
					resChg.setChangeType(null);
				}
			}
			
			//是否变更执行对象
			if(!StringUtils.isEmpty(chgExecuteObject) && !preExecuteObject.equals(chgExecuteObject)){
				isChange = true;
				resChg.setExecuteObject(chgExecuteObject);
				PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr)getBean("partnerDeptMgr");
				String deptMagId = partnerDeptMgr.getPartnerDept(chgExecuteObject).getDeptMagId();
				resChg.setExecuteDept(deptMagId);
			}else{
				resChg.setExecuteObject(null);
				resChg.setExecuteDept(null);
			}
			
			//保存改变前属性
			resChg.setPrePlanStartTime(prePlanStartTime);
			resChg.setPrePlanEndTime(prePlanEndTime);
			resChg.setPreExecuteObject(preExecuteObject);
			resChg.setPreChangeType(preChangeType);
			inspectPlanResChangeMgr.save(resChg);
			
			if(isChange){
				res.setChangeState(1);
				res.setPlanChangeId(planChgId);
			}else{
				res.setChangeState(0);
				res.setPlanChangeId(null);
			}
			inspectPlanResMgr.save(res);
		}else{  //当前资源没有关联到计划上，得先在变更表里面加入一条数据，然后再更新资源
			InspectPlanResChange resChg = inspectPlanResChangeMgr.find(resChgId);
			if(resChg == null){
				resChg = new InspectPlanResChange();
				resChg.setPlanId(plan);
				resChg.setPlanResId(Long.parseLong(resId));
				resChg.setPlanChangeId(planChgId);
			}
			if("1".equals(chgChangeType)){//变更为本月执行
				if(!StringUtils.isEmpty(chgPlanStartTime)){
					resChg.setPlanStartTime(new DateTime(chgPlanStartTime+"T00:00:00").toDate());
				}
				if(!StringUtils.isEmpty(chgPlanEndTime)){
					resChg.setPlanEndTime(new DateTime(chgPlanEndTime+"T23:59:59").toDate());
				}
				resChg.setChangeType(Integer.parseInt(chgChangeType));
			}else{//变更为本月不执行
				resChg.setPlanStartTime(null);
				resChg.setPlanEndTime(null);
				resChg.setChangeType(null);
			}
			//是否变更执行对象
			if(!StringUtils.isEmpty(chgExecuteObject) && !preExecuteObject.equals(chgExecuteObject)){
				resChg.setExecuteObject(chgExecuteObject);
				PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr)getBean("partnerDeptMgr");
				String deptMagId = partnerDeptMgr.getPartnerDept(chgExecuteObject).getDeptMagId();
				resChg.setExecuteDept(deptMagId);
			}else{
				resChg.setExecuteObject(null);
				resChg.setExecuteDept(null);
			}
			//保存改变前属性
			resChg.setPrePlanStartTime(prePlanStartTime);
			resChg.setPrePlanEndTime(prePlanEndTime);
			resChg.setPreExecuteObject(preExecuteObject);
			resChg.setPreChangeType(0);   //没有关联资源的时候，变更前一定是本月不执行
			inspectPlanResChangeMgr.save(resChg);
			
//			res.setPlanId(planId);
			res.setChangeState(1);
//			res.setPlanStartTime(new DateTime(chgPlanStartTime+"T00:00:00").toDate());
//			res.setPlanEndTime(new DateTime(chgPlanEndTime+"T00:00:00").toDate());
			res.setPlanChangeId(planChgId);
			inspectPlanResMgr.save(res);
		}
		
		ActionForward actionForward = new ActionForward();
		actionForward.setPath("/inspectPlanChange.do?method=findInspectPlanResChangeList&id="+planChgId+"&planId="+planChange.getPlanId());
		actionForward.setRedirect(false);
		return actionForward;
	}
	
	/**
	 * 判断2个日期时间是否一致
	 * @param prePlanTime 变更前
	 * @param chgPlanTime 变更后
	 * @return
	 */
	private boolean isPlanTimeChange(Date prePlanTime,String chgPlanTime){
		return !StringUtils.isEmpty(chgPlanTime) && !(new LocalDate(prePlanTime).toString()).equals(chgPlanTime);
	}
	
	/**
	 *  开启工单巡检后的突发巡检资源变更编辑保存
	 */
	public ActionForward editInspectPlanResChangeBurst(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
		IInspectPlanResChangeMgr inspectPlanResChangeMgr = (IInspectPlanResChangeMgr)getBean("inspectPlanResChangeMgr");
		IInspectPlanChangeMgr inspectPlanChangeMgr = (IInspectPlanChangeMgr)getBean("inspectPlanChangeMgr");
		
		String resId = request.getParameter("resId");
		String resChgId = request.getParameter("resChgId");   //资源变更id
		String planChgId = request.getParameter("planChgId"); //计划变更id
//		String resPlanId = StaticMethod.null2String( request.getParameter("planId"));//资源关联的计划id
		String planId = StaticMethod.null2String(request.getParameter("plan"));   //进行变更的计划id
		InspectPlanChange planChange = inspectPlanChangeMgr.find(planChgId);
		InspectPlanRes res = inspectPlanResMgr.get(Long.parseLong(resId));
		
		//变更前属性
		Date prePlanStartTime = res.getPlanStartTime();
		Date prePlanEndTime = res.getPlanEndTime();
		String preExecuteObject = res.getExecuteObject();
		Integer preChangeType = StringUtils.isEmpty(res.getPlanId()) ? 0 : 1; //变更前是否与计划关联（0否 1是）
		
		//变更后属性
		String chgPlanStartTime = request.getParameter("chgPlanStartTime");
		String chgPlanEndTime = request.getParameter("chgPlanEndTime");
		String chgExecuteObject = request.getParameter("chgExecuteObject");
		String chgChangeType = request.getParameter("changeType"); //是否与计划关联（0是 1否）
		
		//首先判断是否进行了变更
		boolean isChange = false; 
		boolean isPlanStartTimeChange = isPlanTimeChange(prePlanStartTime,chgPlanStartTime); //开始时间是否变更
		boolean isPlanEndTimeChange = isPlanTimeChange(prePlanEndTime,chgPlanEndTime);//结束时间是否变更
		boolean isChangeType = !StringUtils.isEmpty(chgChangeType) && !"-1".equals(chgChangeType) 
						&& !chgChangeType.equals(preChangeType.toString()); //与计划关联是否变更
		boolean isExecuteObjectChange = !StringUtils.isEmpty(chgExecuteObject) && !preExecuteObject.equals(chgExecuteObject);
		
		
		if(isPlanStartTimeChange || isPlanEndTimeChange || isChangeType || isExecuteObjectChange){
			isChange = true; 
		}
		
		InspectPlanResChange resChg = inspectPlanResChangeMgr.find(resChgId);
		if(resChg == null){
			resChg = new InspectPlanResChange();
			resChg.setPlanId(planId);
			resChg.setPlanResId(Long.parseLong(resId));
			resChg.setPlanChangeId(planChgId);
		}

		if(isChange){//进行了变更
			if(!StringUtils.isEmpty(chgPlanStartTime)){
				resChg.setPlanStartTime(new DateTime(chgPlanStartTime+"T00:00:00").toDate());
			}
			if(!StringUtils.isEmpty(chgPlanEndTime)){
				resChg.setPlanEndTime(new DateTime(chgPlanEndTime+"T23:59:59").toDate());
			}
			if(!StringUtils.isEmpty(chgExecuteObject) && !preExecuteObject.equals(chgExecuteObject)){
				resChg.setExecuteObject(chgExecuteObject);
				resChg.setExecuteDept(((PartnerDeptMgr)getBean("partnerDeptMgr")).getPartnerDept(chgExecuteObject).getDeptMagId());
			}else{
				resChg.setExecuteObject(null);
				resChg.setExecuteDept(null);
			}
			if(!StringUtils.isEmpty(chgChangeType) && !"-1".equals(chgChangeType)){
				resChg.setChangeType(Integer.parseInt(chgChangeType));
			}
			resChg.setPrePlanStartTime(prePlanStartTime);
			resChg.setPrePlanEndTime(prePlanEndTime);
			resChg.setPreExecuteObject(preExecuteObject);
			resChg.setPreChangeType(preChangeType);
			inspectPlanResChangeMgr.save(resChg);
			
			res.setChangeState(1);
			res.setPlanChangeId(planChgId);
		}else{//未进行变更
			
			res.setChangeState(0);
			res.setPlanChangeId(null);
			
			if(!StringUtils.isEmpty(resChgId)){
				inspectPlanResChangeMgr.removeById(resChgId);
			}
		}
		inspectPlanResMgr.save(res);
		

		ActionForward actionForward = new ActionForward();
		actionForward.setPath("/inspectPlanChange.do?method=findInspectPlanResChangeList&id="+planChgId+"&planId="+planChange.getPlanId());
		actionForward.setRedirect(false);
		return actionForward;
	}
	
	/**
	 *  巡检计划变更待审批列表
	 */
	public ActionForward findInspectPlanChangeApproveList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		IInspectPlanChangeMgr inspectPlanChangeMgr = (IInspectPlanChangeMgr)getBean("inspectPlanChangeMgr");
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		final Integer pageSize = CommonConstants.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		final int pageIndex = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		String hql = "from InspectPlanChange m where m.state=2 ";
		if(!"admin".equals(userId)){
			hql += " and (m.approveObject='"+userId+"' or m.approveObject in (select r.subRoleid from TawSystemUserRefRole r where r.userid='"+userId+"')) ";
		}
		hql += " order by m.changeTime desc";
		PageModel pm = inspectPlanChangeMgr.findInspectPlanChangeApproveList(hql, new Object[]{}, pageIndex*pageSize, pageSize);
		request.setAttribute("list", pm.getDatas());
		request.setAttribute("size", pm.getTotal());
		request.setAttribute("pagesize", pageSize);
		
		request.setAttribute("approveStatusMap",InspectConstants.approveStatusMap);
		return mapping.findForward("inspectPlanChangeApproveList");
	}
	
	/**
	 *  巡检资源变更详情列表
	 */
	@SuppressWarnings("unchecked")
	public ActionForward findInspectPlanResChangeApproveList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
		IInspectPlanMainMgr inspectPlanMainMgr = (IInspectPlanMainMgr)getBean("inspectPlanMainMgr");
		String planId = request.getParameter("planId");
		String planChgId = request.getParameter("id");
		InspectPlanMain planMain = inspectPlanMainMgr.find(planId);
		final Integer pageSize = CommonConstants.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		final int pageIndex = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		InspectPlanRes res = new  InspectPlanRes();
		res.setResType(request.getParameter("resType"));
		res.setResLevel(request.getParameter("resLevel"));
		res.setCity(request.getParameter("city"));
		res.setCountry(request.getParameter("country"));
		res.setInspectCycle(request.getParameter("inspectCycle"));
		
		String hql = "select res from InspectPlanRes res,InspectPlanResChange resChg where resChg.planResId=res.id and resChg.planChangeId='"+planChgId+"'";
		if(!StringUtils.isEmpty(request.getParameter("resType")) ){
			hql += " and res.resType='"+request.getParameter("resType")+"'";
		}
		if(!StringUtils.isEmpty(request.getParameter("resLevel")) ){
			hql += " and res.resLevel='"+request.getParameter("resLevel")+"'";
		}
		if(!StringUtils.isEmpty(request.getParameter("city")) ){
			hql += " and res.city='"+request.getParameter("city")+"'";
		}
		if(!StringUtils.isEmpty(request.getParameter("country")) ){
			hql += " and res.country='"+request.getParameter("country")+"'";
		}
		if(!StringUtils.isEmpty(request.getParameter("inspectCycle")) ){
			hql += " and res.inspectCycle='"+request.getParameter("inspectCycle")+"'";
		}
		PageModel pm = inspectPlanResMgr.findPlanResWithChangeList(hql, new Object[]{}, pageIndex*pageSize, pageSize);
		request.setAttribute("list", pm.getDatas());
		request.setAttribute("resultSize", pm.getTotal());
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("planId", planId);
		request.setAttribute("planChgId", planChgId);
		request.setAttribute("planName", planMain.getPlanName());
		request.setAttribute("specialty", planMain.getSpecialty());
		request.setAttribute("cycleMap", InspectConstants.cycleMap);
		request.setAttribute("res", res);
		
		
		//审批历史列表
		IInspectPlanApproveMgr inspectPlanApproveMgr = (IInspectPlanApproveMgr)getBean("inspectPlanApproveMgr");
		Search search = new Search();
		search.addFilterEqual("planChangeId", planChgId);
		search.addSortDesc("approveTime");
		List<InspectPlanApprove> approveList = inspectPlanApproveMgr.search(search);
		request.setAttribute("approveList", approveList);
		request.setAttribute("approveStatusMap", InspectConstants.approveStatusMap);
		request.setAttribute("planTypeMap", InspectConstants.planTypeMap);
		
		//根据省ID获取地市
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	List citys = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", citys);
		return mapping.findForward("inspectPlanResChangeApproveList");
	}
	
	/**
	 *  单个巡检资源变更详情
	 */
	public ActionForward showInspectPlanResChangeDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
		IInspectPlanResChangeMgr inspectPlanResChangeMgr = (IInspectPlanResChangeMgr)getBean("inspectPlanResChangeMgr");
		String resId = request.getParameter("resId");
		String planChgId = request.getParameter("planChgId");
		InspectPlanRes res = inspectPlanResMgr.get(Long.parseLong(resId));
		
		Search search = new Search();
		search.addFilterEqual("planResId", resId);
		search.addFilterEqual("planChangeId", planChgId);
		
		InspectPlanResChange resChg = inspectPlanResChangeMgr.searchUnique(search);
		if(resChg == null){
			resChg = new InspectPlanResChange();
		}
		request.setAttribute("res", res);
		request.setAttribute("resChg", resChg);
		request.setAttribute("planChgId", planChgId);
		request.setAttribute("cycleMap", InspectConstants.cycleMap);
		
		return mapping.findForward("inspectPlanResChangeDetail");
	}
	
	/**
	 *  跳转到巡检计划变更审批页面
	 */
	public ActionForward toApproveInspectPlanChange(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		IInspectPlanChangeMgr inspectPlanChangeMgr = (IInspectPlanChangeMgr)getBean("inspectPlanChangeMgr");
		String planChgId = request.getParameter("planChgId");
		String planId = request.getParameter("planId");
		request.setAttribute("planChgId", planChgId);
		request.setAttribute("planId", planId);
		InspectPlanChange planChange = inspectPlanChangeMgr.find(planChgId);
		request.setAttribute("planChange", planChange);
		return mapping.findForward("inspectPlanChangeApproveForm");
	}
	
	/**
	 *  审批巡检计划变更
	 */
	public ActionForward approveInspectPlanChange(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		IInspectPlanMainMgr inspectPlanMainMgr = (IInspectPlanMainMgr)getBean("inspectPlanMainMgr");
		IInspectPlanApproveMgr inspectPlanApproveMgr = (IInspectPlanApproveMgr)getBean("inspectPlanApproveMgr");
		IInspectPlanChangeMgr inspectPlanChangeMgr = (IInspectPlanChangeMgr)getBean("inspectPlanChangeMgr");
		IInspectPlanResChangeMgr inspectPlanResChangeMgr = (IInspectPlanResChangeMgr)getBean("inspectPlanResChangeMgr");
		IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
		String planId = request.getParameter("planId");
		String planChgId = request.getParameter("planChgId");
		String approveStatusStr = request.getParameter("approveStatus");
		Integer approveStatus = Integer.parseInt(approveStatusStr);
		String approveIdea = request.getParameter("approveIdea");
		
		InspectPlanChange planChange = inspectPlanChangeMgr.find(planChgId);
		planChange.setState(approveStatus);
		inspectPlanChangeMgr.save(planChange);
		
		//如果审批通过，将变更更新到巡检计划和巡检元任务上
		if(approveStatus.equals(InspectConstants.APPROVE_STATUS_PASSED)){
			Search search = new Search();
			search.addFilterEqual("planChangeId", planChgId);
			List<InspectPlanResChange> resChgList = inspectPlanResChangeMgr.search(search);
			for (InspectPlanResChange resChg : resChgList) {
				Long planResId = resChg.getPlanResId();
				InspectPlanRes res = inspectPlanResMgr.get(planResId);
				if(resChg.getPlanStartTime() != null){
					res.setPlanStartTime(resChg.getPlanStartTime());
				}
				if(resChg.getPlanEndTime() != null){
					res.setPlanEndTime(resChg.getPlanEndTime());
				}
				if(resChg.getExecuteObject() != null && resChg.getExecuteDept() != null){
					res.setExecuteObject(resChg.getExecuteObject());
					res.setExecuteDept(resChg.getExecuteDept());
				}
				if(resChg.getChangeType() != null){//变更是否本月执行
					if(resChg.getChangeType() == 1){ //本月执行
						res.setPlanId(planId);
					}else if(resChg.getChangeType() == 0){//本月不执行
						res.setPlanId(null);
//						res.setPlanStartTime(null);
//						res.setPlanEndTime(null);
					}
				}
				res.setChangeState(0);
				res.setPlanChangeId(null);
				inspectPlanResMgr.save(res);
			}
		}
		
		//更新计划是否关联资源标识以及关联资源数目
		InspectPlanMain planMain = inspectPlanMainMgr.find(planId);
		Integer count = inspectPlanResMgr.getPlanResAssignCount(planId);
		if(count > 0){
			planMain.setResConfig(InspectConstants.YES);
		}else{
			planMain.setResConfig(InspectConstants.NO);
		}
		planMain.setResNum(count);
		inspectPlanMainMgr.save(planMain);
		
		InspectPlanApprove approve = new InspectPlanApprove();
		approve.setPlanId(planId);
		approve.setPlanChangeId(planChgId);
		approve.setApproveIdea(approveIdea);
		approve.setApproveStatus(approveStatus);
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform"); 
		approve.setApprover(sessionForm.getUserid());
		approve.setApproverDept(sessionForm.getDeptid());
		approve.setApproveTime(new Date());
		approve.setPlanType(0);
		inspectPlanApproveMgr.save(approve);
		
//		ActionForward actionForward = new ActionForward();
//		actionForward.setPath("/inspectPlanChange.do?method=findInspectPlanChangeApproveList");
//		actionForward.setRedirect(false);
//		return actionForward;
		return mapping.findForward("success");
	}
	
	/**
	 *  查询巡检计划新增以及变更待审批列表(巡检工单专用)
	 */
	public ActionForward findInspectPlanApproveListSheet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
//		IInspectPlanChangeMgr inspectPlanChangeMgr = (IInspectPlanChangeMgr)getBean("inspectPlanChangeMgr");
//		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
//		String userId = sessionForm.getUserid();
//		final Integer pageSize = CommonConstants.PAGE_SIZE;
//		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
//		final int pageIndex = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
//		PageModel pm = inspectPlanChangeMgr.findInspectPlanApproveListSheet(userId, pageIndex*pageSize, pageSize);
//		request.setAttribute("list", pm.getDatas());
//		request.setAttribute("size", pm.getTotal());
//		request.setAttribute("pagesize", pageSize);
		
		
		IInspectPlanMainMgr inspectPlanMainMgr = (IInspectPlanMainMgr)getBean("inspectPlanMainMgr");
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		
		final Integer pageSize = CommonConstants.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		final int pageIndex = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		
		String hql = "from InspectPlanMain m where m.approveStatus=2 ";
		
		if(!"admin".equals(userId)){
			hql += " and (m.approveObject='"+userId+"' or m.approveObject in (select r.subRoleid from TawSystemUserRefRole r where r.userid='"+userId+"')) ";
		}
		if(!StringUtils.isEmpty(request.getParameter("planName")) ){
			hql += " and m.planName like '%"+request.getParameter("planName")+"%'";
		}
		if(!StringUtils.isEmpty(request.getParameter("specialty")) ){
			hql += " and m.specialty='"+request.getParameter("specialty")+"'";
		}
		if(!StringUtils.isEmpty(request.getParameter("partnerDeptId")) ){
			hql += " and m.partnerDeptId='"+request.getParameter("partnerDeptId")+"'";
		}
		hql += " order by m.createTime desc";
		
		PageModel pm = inspectPlanMainMgr.findInspectPlanMainApproveList(hql, new Object[]{}, pageIndex*pageSize, pageSize);
		request.setAttribute("list", pm.getDatas());
		request.setAttribute("size", pm.getTotal());
		request.setAttribute("pagesize", pageSize);
		
		request.setAttribute("yesOrNoMap",InspectConstants.yesOrNoMap);
		request.setAttribute("approveStatusMap",InspectConstants.approveStatusMap);
		
		
		IInspectPlanChangeMgr inspectPlanChangeMgr = (IInspectPlanChangeMgr)getBean("inspectPlanChangeMgr");
		String chgpageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"chglist");
		final int chgpageIndex = Strings.isNullOrEmpty(chgpageIndexString) ? 0 : Integer.valueOf(chgpageIndexString).intValue() - 1;
		String chghql = "from InspectPlanChange m where m.state=2 ";
		if(!"admin".equals(userId)){
			chghql += " and (m.approveObject='"+userId+"' or m.approveObject in (select r.subRoleid from TawSystemUserRefRole r where r.userid='"+userId+"')) ";
		}
		hql += " order by m.changeTime desc";
		PageModel chgpm = inspectPlanChangeMgr.findInspectPlanChangeApproveList(chghql, new Object[]{}, chgpageIndex*pageSize, pageSize);
		request.setAttribute("chglist", chgpm.getDatas());
		request.setAttribute("chgsize", chgpm.getTotal());
		
		
		return mapping.findForward("inspectPlanApproveListSheet");
	}
	
}
