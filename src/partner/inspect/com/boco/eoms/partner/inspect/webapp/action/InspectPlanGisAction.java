package com.boco.eoms.partner.inspect.webapp.action;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.LocalDate;

import utils.PartnerPrivUtils;

import com.boco.eoms.arcGis.util.ArcGisConstacts;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcServiceImpl;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.model.PartnerUser;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanApproveMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanGisMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanGisPnrMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanMainMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanResMgr;
import com.boco.eoms.partner.inspect.mgr.IPnrInspectTrackMgr;
import com.boco.eoms.partner.inspect.model.InspectPlanApprove;
import com.boco.eoms.partner.inspect.model.InspectPlanGis;
import com.boco.eoms.partner.inspect.model.InspectPlanGisPnr;
import com.boco.eoms.partner.inspect.model.InspectPlanMain;
import com.boco.eoms.partner.inspect.model.InspectPlanRes;
import com.boco.eoms.partner.inspect.model.PnrInspectTrack;
import com.boco.eoms.partner.inspect.scheduler.InspectPlanGisScheduler;
import com.boco.eoms.partner.inspect.util.InspectConstants;
import com.boco.eoms.partner.inspect.webapp.form.InspectPlanMainToListForm;
import com.boco.eoms.partner.res.mgr.PnrResConfigMgr;
import com.boco.eoms.partner.res.model.PnrResConfig;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
/**
* @ClassName: InspectPlanGisAction
* @Description: TODO
* @author liaojiming
* @date 2012-11-23 上午11:31:37
 */
public class InspectPlanGisAction extends BaseAction {

	/**
	 * 统计任务完成情况 for baiduGis
	* @Title: inspectPlanMainFindList
	* @author liaojiming
	* @Description: TODO
	* @param @param mapping
	* @param @param form
	* @param @param request
	* @param @param response
	* @param @return
	* @param @throws IOException    
	* @return ActionForward    
	* @throws
	 */
	public ActionForward inspectPlanMainFindList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		IInspectPlanGisMgr inspectPlanGisMgr = this.getInspectPlanGisMgr();
		IInspectPlanGisPnrMgr inspectPlanGisPnrMgr = (IInspectPlanGisPnrMgr)getBean("inspectPlanGisPnrMgr");
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String city = StaticMethod.nullObject2String(request.getParameter("city"));
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		String deptid = sessionForm.getDeptid();
		
		if(deptid.startsWith("1")){
			//此时按地市进行统计，统计的是所有地市的任务完成情况
			SearchResult<InspectPlanGis> result = inspectPlanGisMgr.searchAndCount(search);
			List list = inspectPlanGisMgr.findInspectplanGis(city);
			request.setAttribute("list", result.getResult());
			request.setAttribute("citylist", list);
			request.setAttribute("resultSize",result.getTotalCount());
			request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
			request.setAttribute("isDept", "no");
			if("".equals(city)){
				request.setAttribute("iscity", "yes");
			}else{
				request.setAttribute("iscity", "no");
			}
		}else{
			//此时按代维公司进行统计任务的完成情况
			search.addFilterLike("pnrDept", deptid+ "%");
			SearchResult<InspectPlanGisPnr> result = inspectPlanGisPnrMgr.searchAndCount(search);
			request.setAttribute("list", result.getResult());
			request.setAttribute("resultSize",result.getTotalCount());
			request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
			request.setAttribute("isDept", "yes");
		}
		
//		SearchResult<InspectPlanGisPnr> result = inspectPlanGisPnrMgr.searchAndCount(search);
		
//		SearchResult<InspectPlanGis> result = inspectPlanGisMgr.searchAndCount(search);
//		request.setAttribute("list", result.getResult());
//		request.setAttribute("resultSize",result.getTotalCount());
//		request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
//		InspectPlanGisScheduler ps = new InspectPlanGisScheduler();
//		ps.saveInspectPlanGis();
//		ps.saveInspectPlanGisPnrDept();
		return mapping.findForward("inspectPlanMainFindList");
	}
	
	/**
	 * 统计任务完成情况 for arcGis
	* @Title: inspectPlanMainFindListForArcGis
	* @author liaojiming
	* @Description: TODO
	* @param @param mapping
	* @param @param form
	* @param @param request
	* @param @param response
	* @param @return
	* @param @throws IOException    
	* @return ActionForward    
	* @throws
	 */
	public ActionForward inspectPlanMainFindListForArcGis(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		IInspectPlanGisMgr inspectPlanGisMgr = this.getInspectPlanGisMgr();
		IInspectPlanGisPnrMgr inspectPlanGisPnrMgr = (IInspectPlanGisPnrMgr)getBean("inspectPlanGisPnrMgr");
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.setMaxResults(CommonConstants.PAGE_SIZE);
//		InspectPlanGisScheduler ps = new InspectPlanGisScheduler();
//		ps.saveInspectPlanGis();
		
		HashMap<String, String> usermap = (HashMap<String, String>)PartnerPrivUtils.userIsPersonnel(request);
		String flag = usermap.get("isPersonnel");
		TawSystemSessionForm sysuser = this.getUser(request);
		if(flag.equals("y")){  //是代维人员
			//此时按代维公司进行统计任务的完成情况
			search.addFilterLike("pnrDept", sysuser.getDeptid()+ "%");
			SearchResult<InspectPlanGisPnr> result = inspectPlanGisPnrMgr.searchAndCount(search);
			List<InspectPlanGisPnr> list = result.getResult();
			if(list!=null && !list.isEmpty()){
				request.setAttribute("list", result.getResult());
			}
			request.setAttribute("resultSize",result.getTotalCount());
			request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
			request.setAttribute("isDept", "yes");
			
			
		}else{//移动公司人
			
			//此时按地市进行统计，统计的是所有地市的任务完成情况
			String city = StaticMethod.nullObject2String(request.getParameter("city"));
			SearchResult<InspectPlanGis> result = inspectPlanGisMgr.searchAndCount(search);
			List<InspectPlanGis> cityList = inspectPlanGisMgr.findInspectplanGis(city);
			List<InspectPlanGis> list = result.getResult();
			request.setAttribute("list", list);
			request.setAttribute("citylist", cityList);
			request.setAttribute("resultSize",result.getTotalCount());
			request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
			request.setAttribute("isDept", "no");
			if("".equals(city)){
				request.setAttribute("iscity", "yes");
			}else{
				request.setAttribute("iscity", "no");
				
			}
			if(!"".equals(city)){
			request.setAttribute("backShow", "backShow");
			}
			
		}
		
		return mapping.findForward("inspectPlanMainFindListForArcGis");
	}
	
	
	/**
	 * 测试轮询的方法，用来手动统计所有城市任务完成情况
	* @Title: saveInspectPlanGis
	* @author liaojiming
	* @Description: TODO
	* @param @param mapping
	* @param @param form
	* @param @param request
	* @param @param response
	* @param @return
	* @param @throws IOException    
	* @return ActionForward    
	* @throws
	 */
	public ActionForward saveInspectPlanGis(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		InspectPlanGisScheduler ps = new InspectPlanGisScheduler();
		ps.saveInspectPlanGis();     //按地市进行统计
		ps.saveInspectPlanGisPnrDept();
		return null;
	}
	
	/**
	 * 查询满足条件的任务 for baiduGis
	* @Title: inspectPlanGisTaskList
	* @author liaojiming
	* @Description: TODO
	* @param @param mapping
	* @param @param form
	* @param @param request
	* @param @param response
	* @param @return
	* @param @throws IOException    
	* @return ActionForward    
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public ActionForward inspectPlanGisTaskList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		final Integer pageSize = CommonConstants.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		final int pageIndex = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		String whereStr =" where 1=1 ";
		String city = StaticMethod.null2String(request.getParameter("city"));
		String dept = StaticMethod.null2String(request.getParameter("pnrDept"));
		String isDone = StaticMethod.null2String(request.getParameter("isDone"));
		if("yes".equals(isDone)){
			whereStr += " and inspectState=1";
			whereStr += " and inspectTime<=current";
		}
		if(!Strings.isNullOrEmpty(city)){
			whereStr += " and country='"+city+"' ";
		}
		if(!Strings.isNullOrEmpty(dept)){
			whereStr += " and executeDept='"+dept+"' ";
		}
		whereStr +=" and planStartTime<current and planEndTime>=current";
		IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
		Map map = (Map) inspectPlanResMgr.findInspectPlanResList(pageIndex, pageSize, whereStr);
		List list = (List) map.get("result");
		request.setAttribute("list", list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("inspectPlanGisTaskList");
	}
	
	
	/**
	 * 查询满足条件的任务 for arcGis
	* @Title: inspectPlanGisTaskListArcGis
	* @author liaojiming
	* @Description: TODO
	* @param @param mapping
	* @param @param form
	* @param @param request
	* @param @param response
	* @param @return
	* @param @throws IOException    
	* @return ActionForward    
	* @throws
	 */
	public ActionForward inspectPlanGisTaskListArcGis(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		final Integer pageSize = CommonConstants.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		final int pageIndex = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		String whereStr =" where 1=1 ";
		String city = StaticMethod.null2String(request.getParameter("city"));
		String dept = StaticMethod.null2String(request.getParameter("pnrDept"));
		String isDone = StaticMethod.null2String(request.getParameter("isDone"));
		String userId = getUserId(request);
		TawSystemSessionForm sysuser = this.getUser(request);
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd");
		String datetime = tempDate.format(new java.util.Date())+" 00:00:00";
		String nowDateTime= CommonSqlHelper.formatDateTime(datetime);
		HashMap<String, String> usermap = (HashMap<String, String>)PartnerPrivUtils.userIsPersonnel(request);
		String flag = usermap.get("isPersonnel");
		
		if(flag.equals("y")){//说明是代维公司人查看，默认为只能查看自己部门以及其下部门的，所以应该是executeDept like
			whereStr += "  and executeDept like '"+sysuser.getDeptid()+"%'"; 
		}else if(!"admin".equals(userId)){//如果不是代维公司人员，则应该根据用户所属部门的所属地域来查看巡检任务，所以应该是根据areaId like
			
			ITawSystemDeptManager mgr = (ITawSystemDeptManager)getBean("ItawSystemDeptManager");
			
			TawSystemDept systemdept = mgr.getDeptinfobydeptid(sysuser.getDeptid(), "0");
			String areaid = systemdept.getAreaid();
			if(areaid!=null && !areaid.trim().equals("")){
				whereStr += "  and country like '"+areaid+"%'"; 
			}else {//对应对应部门没有低于，则不允许查看数据
				return mapping.findForward("inspectnoprivforarcgis");
			} 
		}
		if("yes".equals(isDone)){
			whereStr += " and inspectState=1";
//			if(CommonSqlHelper.isOracleDialect()){
//			whereStr += " and inspectTime<="+nowDateTime;
//			}else{
//			whereStr += " and inspectTime<=current";	
//			}
		}
		if(!Strings.isNullOrEmpty(city)){
			whereStr += " and country='"+city+"' ";
		}
//		if(!Strings.isNullOrEmpty(dept)){
//			whereStr += " and executeDept='"+dept+"' ";
//		}
		if(CommonSqlHelper.isOracleDialect()){
			
			whereStr +=" and planStartTime<"+nowDateTime+" and planEndTime>="+nowDateTime;	
		}
		else{
		whereStr +=" and planStartTime<current and planEndTime>=current";
		}
		IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
		Map map = (Map) inspectPlanResMgr.findInspectPlanResList(pageIndex, pageSize, whereStr);
		List list = (List) map.get("result");
		request.setAttribute("list", list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("backShow", "backShow");
		if(city.length()>=4){
			city = city.substring(0, 4);
		}
		request.setAttribute("city", city);
		return mapping.findForward("inspectPlanGisTaskListForArcGis");
	}
	
	/**
	 * 代维误点资源
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward findInspectPlanErrorRes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	List city = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", city);
		final Integer pageSize = CommonConstants.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		final int pageIndex = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		String whereStr =" where inspectState=1 and signStatus>0";
		String city1 = StaticMethod.null2String(request.getParameter("region"));
		String country = StaticMethod.null2String(request.getParameter("country"));
		String specialty = StaticMethod.null2String(request.getParameter("specialty"));
		
		String userId = getUserId(request);
		TawSystemSessionForm sysuser = this.getUser(request);
		HashMap<String, String> usermap = (HashMap<String, String>)PartnerPrivUtils.userIsPersonnel(request);
		String flag = usermap.get("isPersonnel");
		
		if(flag.equals("y")){//不为空说明是代维公司人查看，默认为只能查看自己部门以及其下部门的，所以应该是executeDept like
			whereStr += "  and execute_Dept like '"+sysuser.getDeptid()+"%'"; 
		}else if(!"admin".equals(userId)){//如果不是代维公司人员，则应该根据用户所属部门的所属地域来查看巡检任务，所以应该是根据areaId like
			
			ITawSystemDeptManager mgr = (ITawSystemDeptManager)getBean("ItawSystemDeptManager");
			
			TawSystemDept dept = mgr.getDeptinfobydeptid(sysuser.getDeptid(), "0");
			String areaid = dept.getAreaid();
			if(areaid!=null && !areaid.trim().equals("")){
				whereStr += "  and country like '"+areaid+"%'"; 
			}else {//对应对应部门没有低于，则不允许查看数据
				return mapping.findForward("inspectnoprivforarcgis");
			} 
		}
		if(!Strings.isNullOrEmpty(city1)){
			whereStr += " and city='"+city1+"' ";
		}
		if(!Strings.isNullOrEmpty(country)){
			whereStr += " and country='"+country+"' ";
		}
		if(!Strings.isNullOrEmpty(specialty)){
			whereStr += " and specialty='"+specialty+"' ";
		}
		whereStr +=" and planStartTime<current and planEndTime>=current";
		IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
//		List errorlist = inspectPlanResMgr.getPlanErrorDistanceRes(whereStr, pageIndex*pageSize, pageSize);
//		request.setAttribute("list", errorlist.get(1));
//		request.setAttribute("resultSize", errorlist.get(0));
		
		Map map = (Map) inspectPlanResMgr.findInspectPlanResList(pageIndex, pageSize, whereStr);
		List list = (List) map.get("result");
		request.setAttribute("list", list);
		request.setAttribute("resultSize", map.get("total"));
		
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("inspectPlanGisErrorRes");
	}
	
/**
 * 查询满足条件的任务 for arcGis	
 *@deprecated 本方法没找到用处
* @Title: inspectPlanGisTaskListForArcGis
* @author liaojiming
* @Description: TODO
* @param @param mapping
* @param @param form
* @param @param request
* @param @param response
* @param @return
* @param @throws IOException    
* @return ActionForward    
* @throws
 */
	@SuppressWarnings("unchecked")
	public ActionForward inspectPlanGisTaskListForArcGis(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		final Integer pageSize = CommonConstants.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		final int pageIndex = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		String whereStr =" where 1=1 ";
		String city = StaticMethod.null2String(request.getParameter("city"));
		String dept = StaticMethod.null2String(request.getParameter("pnrDept"));
		String isDone = StaticMethod.null2String(request.getParameter("isDone"));
		LocalDate date = new LocalDate();
		int year = date.getYear();
		int month = date.getMonthOfYear();
		int day = date.getDayOfMonth();
		String currentDay = year+"-"+month+"-"+day+" 00:00:00";
		if("yes".equals(isDone)){
			whereStr += " and inspectState=1";
			whereStr += " and inspectTime<='"+currentDay+"'";
		}
		if(!Strings.isNullOrEmpty(city)){
			whereStr += " and city='"+city+"' ";
		}
		if(!Strings.isNullOrEmpty(dept)){
			whereStr += " and executeDept='"+dept+"' ";
		}
		whereStr +=" and planStartTime<'"+currentDay+"' and planEndTime>='"+currentDay+"'";
		IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
		Map map = (Map) inspectPlanResMgr.findInspectPlanResList(pageIndex, pageSize, whereStr);
		List list = (List) map.get("result");
		request.setAttribute("list", list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("inspectPlanGisTaskListForArcGis");
	}
	
	
	/**
	 * 任务查询 for baiduGis
	* @Title: inspectPlanGisTaskFindList
	* @author liaojiming
	* @Description: TODO
	* @param @param mapping
	* @param @param form
	* @param @param request
	* @param @param response
	* @param @return
	* @param @throws IOException    
	* @return ActionForward    
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public ActionForward inspectPlanGisTaskFindList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	List city = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", city);
    	String resName = StaticMethod.null2String(request.getParameter("resName"));
    	String cityRes = StaticMethod.null2String(request.getParameter("region"));
    	String country = StaticMethod.null2String(request.getParameter("country"));
    	String specialty = StaticMethod.null2String(request.getParameter("specialty"));
    	String executeObject = StaticMethod.null2String(request.getParameter("executeObject"));
    	//在gis地图中默认为点击则加载列表
    	//String find =StaticMethod.null2String( request.getParameter("find"));
    	String find="find";
    	final Integer pageSize = CommonConstants.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		final int pageIndex = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		String whereStr =" where 1=1 ";
		
		String userId = getUserId(request);
		TawSystemSessionForm sysuser = this.getUser(request);
		PartnerUserMgr  partnerUserMgr = (PartnerUserMgr)getBean("partnerUserMgr");
		PartnerUser user = partnerUserMgr.getPartnerUserByUserId(userId);
		HashMap<String, String> usermap = (HashMap<String, String>)PartnerPrivUtils.userIsPersonnel(request);
		String flag = usermap.get("isPersonnel");
		
		if(flag.equals("y")){//不为空说明是代维公司人查看，默认为只能查看自己部门以及其下部门的，所以应该是executeDept like
			whereStr += "  and executeDept like '"+user.getDeptId()+"%'"; 
		}else if(!"admin".equals(userId)){//如果不是代维公司人员，则应该根据用户所属部门的所属地域来查看巡检任务，所以应该是根据areaId like
			
			ITawSystemDeptManager mgr = (ITawSystemDeptManager)getBean("ItawSystemDeptManager");
			
			TawSystemDept dept = mgr.getDeptinfobydeptid(sysuser.getDeptid(), "0");
			String areaid = dept.getAreaid();
			if(areaid!=null && !areaid.trim().equals("")){
				whereStr += "  and country like '"+areaid+"%'"; 
			}else{//如果移动人员所在部门没有地域，则不允许查看
				return mapping.findForward("inspectnoprivforarcgis");
			}
		}
    	
		if(!Strings.isNullOrEmpty(resName)){
			whereStr += " and resName like '%"+resName+"%'";
		}
		if(!Strings.isNullOrEmpty(cityRes)){
			whereStr += " and city='"+cityRes+"'";
		}
		if(!Strings.isNullOrEmpty(country)){
			whereStr += " and country='"+country+"'";		
		}
		if(!Strings.isNullOrEmpty(specialty)){
			whereStr += " and specialty='"+specialty+"'";
		}
		if(!Strings.isNullOrEmpty(executeObject)){
			whereStr += " and executeObject='"+executeObject+"'";
		}
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		whereStr +=" and planStartTime<'"+dateFormat.format(date)+"' and planEndTime>='"+dateFormat.format(date)+"'";
		if("find".equals(find)){
			IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
			Map map = (Map) inspectPlanResMgr.findInspectPlanResList(pageIndex, pageSize, whereStr);
			List list = (List) map.get("result");
			request.setAttribute("list", list);
			request.setAttribute("resultSize", map.get("total"));
			request.setAttribute("pageSize", pageSize);
		}else{
			//当不是通过点击查询的时候，这时候不去查询
			List list = new ArrayList();
			request.setAttribute("list", list);
			request.setAttribute("resultSize", 0);
			request.setAttribute("pageSize", pageSize);
		}
		return mapping.findForward("inspectPlanGisTaskFindList");
	}
	
	
	/**
	 * 任务查询 for arcGis
	* @Title: inspectPlanGisTaskFindListForArcGis
	* @author liaojiming
	* @Description: TODO
	* @param @param mapping
	* @param @param form
	* @param @param request
	* @param @param response
	* @param @return
	* @param @throws IOException    
	* @return ActionForward    
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public ActionForward inspectPlanGisTaskFindListForArcGis(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	List city = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", city);
    	String resName = StaticMethod.null2String(request.getParameter("resName"));
    	String cityRes = StaticMethod.null2String(request.getParameter("region"));
    	String country = StaticMethod.null2String(request.getParameter("country"));
    	String specialty = StaticMethod.null2String(request.getParameter("specialty"));
    	String executeObject = StaticMethod.null2String(request.getParameter("executeObject"));
    	
    	PnrResConfig res = new PnrResConfig();
    	res.setResName(resName);
    	res.setCity(cityRes);
    	res.setCountry(country);
    	res.setSpecialty(specialty);
    	res.setExecuteObject(executeObject);
    	
    	//在gis地图中默认为点击则加载列表
    	//String find =StaticMethod.null2String( request.getParameter("find"));
    	String find="find";
    	final Integer pageSize = CommonConstants.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		final int pageIndex = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		String whereStr =" where 1=1 ";
		String userId = getUserId(request);
		TawSystemSessionForm sysuser = this.getUser(request);
		PartnerUserMgr  partnerUserMgr = (PartnerUserMgr)getBean("partnerUserMgr");
		PartnerUser user = partnerUserMgr.getPartnerUserByUserId(userId);
		HashMap<String, String> usermap = (HashMap<String, String>)PartnerPrivUtils.userIsPersonnel(request);
		String flag = usermap.get("isPersonnel");
		
		if(flag.equals("y")){//不为空说明是代维公司人查看，默认为只能查看自己部门以及其下部门的，所以应该是executeDept like
			whereStr += "  and executeDept like '"+user.getDeptId()+"%'"; 
		}else if(!"admin".equals(userId)){//如果不是代维公司人员，则应该根据用户所属部门的所属地域来查看巡检任务，所以应该是根据areaId like
			
			ITawSystemDeptManager mgr = (ITawSystemDeptManager)getBean("ItawSystemDeptManager");
			
			TawSystemDept dept = mgr.getDeptinfobydeptid(sysuser.getDeptid(), "0");
			String areaid = dept.getAreaid();
			if(areaid!=null && !areaid.trim().equals("")){
				whereStr += "  and country like '"+areaid+"%'"; 
			}else{
				return mapping.findForward("inspectnoprivforarcgis");
			}
		}
		if(!Strings.isNullOrEmpty(resName)){
			whereStr += " and resName like '%"+resName+"%'";
		}
		if(!Strings.isNullOrEmpty(cityRes)){
			whereStr += " and city='"+cityRes+"'";
		}
		if(!Strings.isNullOrEmpty(country)){
			whereStr += " and country='"+country+"'";		
		}
		if(!Strings.isNullOrEmpty(specialty)){
			whereStr += " and specialty='"+specialty+"'";
		}
		if(!Strings.isNullOrEmpty(executeObject)){
			whereStr += " and executeObject='"+executeObject+"'";
		}
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString=dateFormat.format(date);
		dateString=CommonSqlHelper.formatDateTime(dateString);
		whereStr +=" and planStartTime<"+dateString+" and planEndTime>="+dateString;
		if("find".equals(find)){
			IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
			Map map = (Map) inspectPlanResMgr.findInspectPlanResList(pageIndex, pageSize, whereStr);
			String aaaString="AAA";
			aaaString.toLowerCase();
			List list = (List) map.get("result");
			
			CommonSpringJdbcServiceImpl jdbcService = (CommonSpringJdbcServiceImpl) ApplicationContextHolder
			.getInstance().getBean("commonSpringJdbcService");
	ID2NameService service = (ID2NameService) ApplicationContextHolder
			.getInstance().getBean("ID2NameGetServiceCatch");
	String ids[]=new String[15];
	for(int i=0;i<list.size();i++){
		InspectPlanRes ipr=new InspectPlanRes();
		ipr=(InspectPlanRes) list.get(i);
		ids[i]=ipr.getResCfgId().toString();
	}
	PnrResConfigMgr pnrResConfiMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
	PnrResConfig[] pnrResConfig = pnrResConfiMgr.find(ids);
	
	List<Map<String, String>> jsonList = new ArrayList<Map<String, String>>();
	HashMap<String, String> jsonMap;
	for(int ii=0;ii<pnrResConfig.length;ii++){
		PnrResConfig prc=(PnrResConfig)pnrResConfig[ii];
		if(prc!=null){
//	String subResTable = prc.getSubResTable();
	jsonMap=new HashMap();
//	String endResLongitude="";
//	String endResLatitude="";
	//传输线路
//	if("pnr_res_line".equals(subResTable)){
//		PnrResLineMgr PnrResLineMgr = (PnrResLineMgr)getBean("PnrResLineMgr");
//		if(!StringUtils.isEmpty(pnrResConfig.getSubResId())){
//			 endResLongitude=StaticMethod.nullObject2String(pnrResConfig.getEndLongitude());
//			 endResLatitude=StaticMethod.nullObject2String(pnrResConfig.getEndLatitude());
//			jsonMap.put("endResLongitude",endResLongitude);
//			jsonMap.put("endResLatitude",endResLatitude);
//			jsonMap.put("type","line");
//		}
//	}
	String resNameFor=StaticMethod.nullObject2String(prc.getResName());
	String specialtyFor=StaticMethod.nullObject2String(prc.getSpecialty());
		   specialtyFor= service.id2Name(specialtyFor, "tawSystemDictTypeDao");
    String resTypeFor=StaticMethod.nullObject2String(prc.getResType());
    	   resTypeFor= service.id2Name(resTypeFor, "tawSystemDictTypeDao");
	String resLongitude=StaticMethod.nullObject2String(prc.getResLongitude());
	String resLatitude=StaticMethod.nullObject2String(prc.getResLatitude());
	String cityFor=StaticMethod.nullObject2String(prc.getCity());
	 	   cityFor=service.id2Name(cityFor, "tawSystemAreaDao");
	String countryFor=StaticMethod.nullObject2String(prc.getCountry());
	 	   country=service.id2Name(countryFor, "tawSystemAreaDao");
	String streetFor=StaticMethod.nullObject2String(prc.getStreet());
	String companyNameFor=StaticMethod.nullObject2String(prc.getCompanyName());
	String contactNameFor=StaticMethod.nullObject2String(prc.getContactName());
	String executeObjectFor=StaticMethod.nullObject2String(prc.getExecuteObject());
			executeObjectFor=service.id2Name(executeObject, "partnerDeptDao");
	String phoneFor =StaticMethod.nullObject2String(prc.getPhone());
	
	if (!"".equals(phoneFor)) {
		phoneFor = ArcGisConstacts.replaceAll(phoneFor, "\n",
				"<br/>");
	}else{
		phoneFor="";
	}
	String resUrlFor="/partner/res/PnrResConfig.do?method=detial&gisType=gisType&seldelcar="+prc.getId();
	jsonMap.put("resUrl", resUrlFor);
	jsonMap.put("resName", resNameFor);
	jsonMap.put("resLongitude",resLongitude);
	jsonMap.put("resLatitude",resLatitude);
	jsonMap.put("specialty",specialtyFor);
	jsonMap.put("resType",resTypeFor);
	jsonMap.put("city",cityFor);
	jsonMap.put("country", countryFor);
	jsonMap.put("executeObject", executeObjectFor);
	jsonMap.put("street",streetFor);
	jsonMap.put("companyName",companyNameFor);
	jsonMap.put("contactName",contactNameFor);
	jsonMap.put("phone",phoneFor);
	if(!"".equals(resLongitude)&&!"".equals(resLatitude)){
	jsonList.add(jsonMap);
	}
		}
	}
	Gson gson=new Gson();
	String jsonString=gson.toJson(jsonList);
	request.setAttribute("jsonString", jsonString);
	
	
			request.setAttribute("list", list);
			request.setAttribute("resultSize", map.get("total"));
			request.setAttribute("pageSize", pageSize);
		}else{
			//当不是通过点击查询的时候，这时候不去查询
			List list = new ArrayList();
			request.setAttribute("list", list);
			request.setAttribute("resultSize", 0);
			request.setAttribute("pageSize", pageSize);
		}
		request.setAttribute("res", res);
		return mapping.findForward("inspectPlanGisTaskFindListForArcGis");
	}
	
	/**
	 * @deprecated 老的巡检轨迹方法，已经无用，请勿调用
	* @Title: inspectPlanGisLocusFindList
	* @author liaojiming
	* @Description: TODO
	* @param @param mapping
	* @param @param form
	* @param @param request
	* @param @param response
	* @param @return
	* @param @throws IOException    
	* @return ActionForward    
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public ActionForward inspectPlanGisLocusFindList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	List city = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", city);
    	String planStartTime = StaticMethod.null2String(request.getParameter("planStartTime"))+" 00:00:00";
    	String planEndTime = StaticMethod.null2String(request.getParameter("planEndTime"))+" 23:59:59";
    	String cityRes = StaticMethod.null2String(request.getParameter("region"));
    	String country = StaticMethod.null2String(request.getParameter("country"));
    	String specialty = StaticMethod.null2String(request.getParameter("specialty"));
    	String executeObject =StaticMethod.null2String(request.getParameter("executeObject"));
    	//在gis地图中默认为点击则加载列表
    	//String find = StaticMethod.null2String(request.getParameter("find"));
    	String find="find";
    	final Integer pageSize = CommonConstants.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		final int pageIndex = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		String whereStr =" where 1=1 ";
    	
		if(!Strings.isNullOrEmpty(cityRes)){
			whereStr += " and city='"+cityRes+"'";
		}
		if(!Strings.isNullOrEmpty(country)){
			whereStr += " and country='"+country+"'";		
		}
		if(!Strings.isNullOrEmpty(specialty)){
			whereStr += " and specialty='"+specialty+"'";
		}
		if(!Strings.isNullOrEmpty(executeObject)){
			whereStr += " and executeObject='"+executeObject+"'";
		}
		if(!" 00:00:00".equals(planStartTime)){
			whereStr += " and  planStartTime<='"+planStartTime+"' and planEndTime>='"+planStartTime+"'";
		}
		if(!" 23:59:59".equals(planEndTime)){
			whereStr += " and planEndTime>='"+planEndTime+"' and planStartTime<='"+planEndTime+"'";
		}
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		whereStr +=" and planStartTime<'"+dateFormat.format(date)+"' and planEndTime>='"+dateFormat.format(date)+"'";
		if("find".equals(find)){
			IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
			Map map = (Map) inspectPlanResMgr.findInspectPlanResList(pageIndex, pageSize, whereStr);
			List list = (List) map.get("result");
			request.setAttribute("list", list);
			request.setAttribute("resultSize", map.get("total"));
			request.setAttribute("pageSize", pageSize);
		}else{
			//当不是通过点击查询的时候，这时候不去查询
			List list = new ArrayList();
			request.setAttribute("list", list);
			request.setAttribute("resultSize", 0);
			request.setAttribute("pageSize", pageSize);
		}
		
		return mapping.findForward("inspectPlanGisLocusFindList");
	}
	
	/**
	 * 巡检轨迹
	 *
	* @Title: inspectPlanGisTrack
	* @author liaojiming
	* @Description: TODO
	* @param @param mapping
	* @param @param form
	* @param @param request
	* @param @param response
	* @param @return
	* @param @throws IOException    
	* @return ActionForward    
	* @throws
	 */
	public ActionForward inspectPlanGisTrack(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		IPnrInspectTrackMgr inspectTrackMgr= (IPnrInspectTrackMgr) this.getBean("pnrInspectTrackMgrImpl");
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		
		String resCfgId = StaticMethod.null2String(request.getParameter("resCfgId"));
		
		search.addFilterEqual("planResId", resCfgId);
		search.addSort("signTime",true );
		
		SearchResult<PnrInspectTrack> searchResult = inspectTrackMgr.searchAndCount(search);
		
		List<PnrInspectTrack> list = searchResult.getResult();
		
		request.setAttribute("resName", StaticMethod.null2String(request.getParameter("resName")));
		request.setAttribute("list",list);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", searchResult.getTotalCount());
		
		return mapping.findForward("inspectPlanGisTrack");
	}
	
	
	/**
	 * 展现当月的所有计划 for baiduGis
	* @Title: getAllInspectMainPlan
	* @author liaojiming
	* @Description: TODO
	* @param @param mapping
	* @param @param form
	* @param @param request
	* @param @param response
	* @param @return
	* @param @throws IOException    
	* @return ActionForward    
	* @throws
	 */
	public ActionForward getAllInspectMainPlan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		IInspectPlanMainMgr inspectPlanMainMgr = (IInspectPlanMainMgr)getBean("inspectPlanMainMgr");
		IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		search.addFilterEqual("status", 1);
		search.addSortDesc("createTime");
		search.addFilterEqual("approveStatus", 1);
//		if(!"admin".equals(sessionForm.getUserid())){
//			String deptMagId = sessionForm.getDeptid();
//			if(deptMagId.length()>6){
//				search.addFilterLike("deptMagId", deptMagId.substring(0, 7)+ "%");
//			}else{
//				search.addFilterLike("deptMagId", deptMagId+ "%");
//			}
//		}
		
		String userId = getUserId(request);
		TawSystemSessionForm sysuser = this.getUser(request);
		PartnerUserMgr  partnerUserMgr = (PartnerUserMgr)getBean("partnerUserMgr");
		PartnerUser user = partnerUserMgr.getPartnerUserByUserId(userId);
		HashMap<String, String> usermap = (HashMap<String, String>)PartnerPrivUtils.userIsPersonnel(request);
		String flag = usermap.get("isPersonnel");
		
		if(flag.equals("y")){//不为空说明是代维公司人查看，默认为只能查看自己部门以及其下部门的，所以应该是deptMagId like
			String deptMagId = sessionForm.getDeptid();
			if(deptMagId.length()>6){
				search.addFilterLike("deptMagId", deptMagId.substring(0, deptMagId.length()-2)+ "%");
			}else{
				search.addFilterLike("deptMagId", deptMagId+ "%");
			}
		}else if(!"admin".equals(userId)){//如果不是代维公司人员，则应该根据用户所属部门的所属地域来查看巡检任务，所以应该是根据areaId like
			
			ITawSystemDeptManager mgr = (ITawSystemDeptManager)getBean("ItawSystemDeptManager");
			TawSystemDept dept = mgr.getDeptinfobydeptid(sysuser.getDeptid(), "0");
			String areaid = dept.getAreaid();
			PartnerDeptMgr  partnerDeptMgr = (PartnerDeptMgr)getBean("partnerDeptMgr");
			List partnerList = partnerDeptMgr.getPartnerDepts(" and countyId like '"+areaid+"%'");
			List<String> deptMagIdList = new ArrayList<String>();
			
			for(int i=0;partnerList!=null && i<partnerList.size();i++){
				PartnerDept partnerDept = (PartnerDept)partnerList.get(i);
				
				String deptMagId = StaticMethod.nullObject2String(partnerDept.getDeptMagId());
				if(!deptMagId.equals("")){
					deptMagIdList.add(deptMagId);
				}
			}
			if(deptMagIdList!=null && deptMagIdList.isEmpty()==false){
				search.addFilterIn("deptMagId", deptMagIdList);
			}else{//如果没有他管理的代维公司，则不允许看到数据
				return mapping.findForward("inspectnoprivforarcgis");
			}
		}
		
		SearchResult<InspectPlanMain> searchResult = inspectPlanMainMgr.searchAndCount(search);
		List<InspectPlanMain> list = searchResult.getResult();
		InspectPlanMainToListForm inspectPlanMainToListForm;
		List<InspectPlanMainToListForm> returnList = new ArrayList<InspectPlanMainToListForm>();
		for(int i = 0;i<list.size();i++){
			inspectPlanMainToListForm = new InspectPlanMainToListForm();
			try {
				list.get(i).setCreateTime(null);
				BeanUtilsBean2.getInstance().copyProperties(inspectPlanMainToListForm, list.get(i));
//				inspectPlanMainToListForm.setHasDone(list.get(i).getResDoneNum());
//				inspectPlanMainToListForm.setResNumber(list.get(i).getResNum());
//				inspectPlanMainToListForm.setPlanNumber(list.get(i).getResPlanDoneNum());
				returnList.add(inspectPlanMainToListForm);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		request.setAttribute("yesOrNoMap",InspectConstants.yesOrNoMap);
		request.setAttribute("approveStatusMap",InspectConstants.approveStatusMap);
		request.setAttribute("list",returnList);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("bsfrList", searchResult.getResult());
		request.setAttribute("size", searchResult.getTotalCount());
		
		return mapping.findForward("getAllInspectMainPlan");
	}
	
	/**
	 *  展现当月的所有计划 for arcGis
	* @Title: getAllInspectMainPlanForArcGis
	* @author liaojiming
	* @Description: TODO
	* @param @param mapping
	* @param @param form
	* @param @param request
	* @param @param response
	* @param @return
	* @param @throws IOException    
	* @return ActionForward    
	* @throws
	 */
	public ActionForward getAllInspectMainPlanForArcGis(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		IInspectPlanMainMgr inspectPlanMainMgr = (IInspectPlanMainMgr)getBean("inspectPlanMainMgr");
		IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		search.addFilterEqual("status", 1);
		search.addSortDesc("createTime");
		search.addFilterEqual("approveStatus", 1);

		String userId = getUserId(request);
		TawSystemSessionForm sysuser = this.getUser(request);
		
		HashMap<String, String> usermap = (HashMap<String, String>)PartnerPrivUtils.userIsPersonnel(request);
		String flag = usermap.get("isPersonnel");
		
		if(flag.equals("y")){//不为空说明是代维公司人查看，默认为只能查看自己部门以及其下部门的，所以应该是deptMagId like
			String deptMagId = sessionForm.getDeptid();
			if(deptMagId.length()>6){
				search.addFilterLike("deptMagId", deptMagId.substring(0, deptMagId.length()-2)+ "%");
			}else{
				search.addFilterLike("deptMagId", deptMagId+ "%");
			}
		}else if(!"admin".equals(userId)){//如果不是代维公司人员，则应该根据用户所属部门的所属地域来查看巡检任务，所以应该是根据areaId like
			
			ITawSystemDeptManager mgr = (ITawSystemDeptManager)getBean("ItawSystemDeptManager");
			TawSystemDept dept = mgr.getDeptinfobydeptid(sysuser.getDeptid(), "0");
			String areaid = dept.getAreaid();
			PartnerDeptMgr  partnerDeptMgr = (PartnerDeptMgr)getBean("partnerDeptMgr");
			List partnerList = partnerDeptMgr.getPartnerDepts(" and countyId like '"+areaid+"%'");
			List<String> deptMagIdList = new ArrayList<String>();
			
			for(int i=0;partnerList!=null && i<partnerList.size();i++){
				PartnerDept partnerDept = (PartnerDept)partnerList.get(i);
				
				String deptMagId = StaticMethod.nullObject2String(partnerDept.getDeptMagId());
				if(!deptMagId.equals("")){
					deptMagIdList.add(deptMagId);
				}
			}
			if(deptMagIdList!=null && deptMagIdList.isEmpty()==false){
				search.addFilterIn("deptMagId", deptMagIdList);
			}else{//如果没有他管理的代维公司，则不允许看到数据
				return mapping.findForward("inspectnoprivforarcgis");
			}
		}
		SearchResult<InspectPlanMain> searchResult = inspectPlanMainMgr.searchAndCount(search);
		List<InspectPlanMain> list = searchResult.getResult();
		InspectPlanMainToListForm inspectPlanMainToListForm;
		List<InspectPlanMainToListForm> returnList = new ArrayList<InspectPlanMainToListForm>();
		for(int i = 0;i<list.size();i++){
			inspectPlanMainToListForm = new InspectPlanMainToListForm();
			try {
				list.get(i).setCreateTime(null);
				BeanUtilsBean2.getInstance().copyProperties(inspectPlanMainToListForm, list.get(i));
//				inspectPlanMainToListForm.setHasDone(list.get(i).getResDoneNum());
//				inspectPlanMainToListForm.setResNumber(list.get(i).getResNum());
//				inspectPlanMainToListForm.setPlanNumber(list.get(i).getResPlanDoneNum());
				returnList.add(inspectPlanMainToListForm);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		request.setAttribute("planNameStringLike", StaticMethod.null2String(request.getParameter("planNameStringLike")));
		request.setAttribute("specialtyStringEqual", StaticMethod.null2String(request.getParameter("specialtyStringEqual")));
		request.setAttribute("yearStringEqual", StaticMethod.null2String(request.getParameter("yearStringEqual")));
		request.setAttribute("monthStringEqual", StaticMethod.null2String(request.getParameter("monthStringEqual")));
		request.setAttribute("partnerDeptIdStringEqual", StaticMethod.null2String(request.getParameter("partnerDeptIdStringEqual")));
		request.setAttribute("yesOrNoMap",InspectConstants.yesOrNoMap);
		request.setAttribute("approveStatusMap",InspectConstants.approveStatusMap);
		request.setAttribute("list",returnList);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("bsfrList", searchResult.getResult());
		request.setAttribute("size", searchResult.getTotalCount());
		
		return mapping.findForward("getAllInspectMainPlanForArcGis");
	}
	
	/**
	 * 查看计划下的巡检资源 for baiduGis
	* @Title: getInspectPlanMainDetail
	* @author liaojiming
	* @Description: TODO
	* @param @param mapping
	* @param @param form
	* @param @param request
	* @param @param response
	* @param @return    
	* @return ActionForward    
	 * @throws Exception 
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public ActionForward getInspectPlanMainDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		IInspectPlanMainMgr inspectPlanMainMgr = (IInspectPlanMainMgr)getBean("inspectPlanMainMgr");
		IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
		ID2NameService service = (ID2NameService) ApplicationContextHolder
		.getInstance().getBean("ID2NameGetServiceCatch");
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String id = request.getParameter("id");
		String resName = request.getParameter("resName");
		String inspectCycle = request.getParameter("inspectCycle");
		String executeObject = request.getParameter("executeObject");
		String inspectState = request.getParameter("inspectState");
		InspectPlanMain planMain = inspectPlanMainMgr.find(id);
		request.setAttribute("planMain", planMain);
		
		final Integer pageSize = CommonConstants.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		final int pageIndex = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		//String whereStr =" where 1=1 and planStartTime<='"+new DateTime().toString("yyyy-MM-dd HH:mm:ss")+"' and planEndTime>='"+new DateTime().toString("yyyy-MM-dd HH:mm:ss")+"'";
		PartnerUserMgr  partnerUserMgr = (PartnerUserMgr)getBean("partnerUserMgr");
		PartnerUser user = partnerUserMgr.getPartnerUserByUserId(sessionForm.getUserid());
		String whereStr =" where ";
		whereStr += " planId='"+id+"'";
		HashMap<String, String> usermap = (HashMap<String, String>)PartnerPrivUtils.userIsPersonnel(request);
		String flag = usermap.get("isPersonnel");
		
		if(flag.equals("y")){//不为空说明是代维公司人查看，默认为只能查看自己部门以及其下部门的，所以应该是executeDept like
			whereStr += "  and executeDept like '"+sessionForm.getDeptid()+"%'"; 
		}else if(!"admin".equals(sessionForm.getUserid())){//如果不是代维公司人员，则应该根据用户所属部门的所属地域来查看巡检任务，所以应该是根据areaId like
			
			ITawSystemDeptManager mgr = (ITawSystemDeptManager)getBean("ItawSystemDeptManager");
			TawSystemDept dept = mgr.getDeptinfobydeptid(sessionForm.getDeptid(), "0");
			String areaid = dept.getAreaid();
			if(areaid!=null && !areaid.trim().equals("")){
				whereStr += "  and country like '"+areaid+"%'"; 
			}else {
				return mapping.findForward("inspectnoprivforarcgis");
			}
		}
		if(!StringUtils.isEmpty(resName)){
			whereStr+="  and  resName like '%"+resName+"%'";
		}
		if(!StringUtils.isEmpty(inspectCycle)){
			whereStr+="  and  inspectCycle='"+inspectCycle+"'";
		}
		if(!StringUtils.isEmpty(executeObject)){
			whereStr+="  and  executeObject='"+executeObject+"'";
		}
		if(!StringUtils.isEmpty(inspectState)){
			whereStr+="  and  inspectState='"+inspectState+"'";
		}
		whereStr+=" and res_Longitude is not null and res_Latitude is not null ";
		Map map = (Map) inspectPlanResMgr.findInspectPlanResList(pageIndex, pageSize, whereStr);
		List list = (List) map.get("result");
		request.setAttribute("list", list);
		//对当前页面进行json封装
		List jsonList=new ArrayList();
		for(int j=0;j<list.size();j++){
			InspectPlanRes pr=new InspectPlanRes();
			Map jsonMap=new HashMap();
			pr=(InspectPlanRes) list.get(j);
			jsonMap.put("resName", pr.getResName());
			String specialty= service.id2Name(pr.getSpecialty(), "tawSystemDictTypeDao");
			jsonMap.put("specialty", specialty);
			jsonMap.put("city", service.id2Name(pr.getCity(), "tawSystemAreaDao"));
			jsonMap.put("country", service.id2Name(pr.getCountry(), "tawSystemAreaDao"));
			jsonMap.put("resLevel", service.id2Name(pr.getResLevel(), "tawSystemDictTypeDao"));
			
//			String street=StaticMethod.nullObject2String(pr.getStreet());
//			jsonMap.put("street", street);
//			String companyName=StaticMethod.nullObject2String(pr.getCompanyName());
//			jsonMap.put("companyName",companyName);
//			String contactName=StaticMethod.nullObject2String(pr.getContactName());
//			jsonMap.put("contactName", contactName);
//			String phone=StaticMethod.nullObject2String(pr.getPhone());
//			if (!"".equals(phone)) {
//				phone = GisBaiduConstacts.replaceAll(phone, "\n",
//						"<br/>");
//			}else{
//				phone="";
//			}
//			jsonMap.put("phone", phone);
			jsonMap.put("resLongitude",pr.getResLongitude());
			jsonMap.put("resLatitude",pr.getResLatitude());
			jsonMap.put("endLongitude",pr.getEndLongitude());
			jsonMap.put("endLatitude(",pr.getEndLatitude());
			//去除脏数据
			if("1122502".equals(pr.getSpecialty())&&!"0".equals(pr.getResLatitude())&&!"0".equals(pr.getResLongitude())&&!"0".equals(pr.getEndLongitude())&&!"0".equals(pr.getEndLatitude())){
				jsonList.add(jsonMap);
			}
			else if(!"1122502".equals(pr.getSpecialty())&&!"0".equals(pr.getResLatitude())&&!"0".equals(pr.getResLongitude())){
			jsonList.add(jsonMap);
			}
		}
		Gson gson=new Gson();
		String jsonString=gson.toJson(jsonList);
		request.setAttribute("jsonString", jsonString);
		
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		IInspectPlanApproveMgr inspectPlanApproveMgr = (IInspectPlanApproveMgr)getBean("inspectPlanApproveMgr");
		Search search = new Search();
		search.addFilterEqual("planId", id);
		List<InspectPlanApprove> approveList = inspectPlanApproveMgr.search(search);
		request.setAttribute("approveList", approveList);
		request.setAttribute("cycleMap", InspectConstants.cycleMap);
		return mapping.findForward("inspectPlanGisResList");
	}
	
	
	/**
	 * 查看计划下的巡检资源 for arcGis
	* @Title: getInspectPlanMainDetailForArcGis
	* @author liaojiming
	* @Description: TODO
	* @param @param mapping
	* @param @param form
	* @param @param request
	* @param @param response
	* @param @return    
	* @return ActionForward    
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public ActionForward getInspectPlanMainDetailForArcGis(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		IInspectPlanMainMgr inspectPlanMainMgr = (IInspectPlanMainMgr)getBean("inspectPlanMainMgr");
		IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
		ID2NameService service = (ID2NameService) ApplicationContextHolder
		.getInstance().getBean("ID2NameGetServiceCatch");
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String id = StaticMethod.null2String(request.getParameter("id"));
		
		String typeSpecialty = StaticMethod.null2String(request.getParameter("specialty"));
		String resName = StaticMethod.null2String(request.getParameter("resName"));
		String inspectCycle = StaticMethod.null2String(request.getParameter("inspectCycle"));
		String executeObject = StaticMethod.null2String(request.getParameter("executeObject"));
		String inspectState = StaticMethod.null2String(request.getParameter("inspectState"));
		if(!"".equals(id)){
		InspectPlanMain planMain = inspectPlanMainMgr.find(id);
		request.setAttribute("planMain", planMain);
		}
		
		final Integer pageSize = CommonConstants.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		final int pageIndex = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		//String whereStr =" where 1=1 and planStartTime<='"+new DateTime().toString("yyyy-MM-dd HH:mm:ss")+"' and planEndTime>='"+new DateTime().toString("yyyy-MM-dd HH:mm:ss")+"'";
		String whereStr =" where ";
		whereStr += " planId='"+id+"'";
		HashMap<String, String> usermap = (HashMap<String, String>)PartnerPrivUtils.userIsPersonnel(request);
		String flag = usermap.get("isPersonnel");
		
		if(flag.equals("y")){//不为空说明是代维公司人查看，默认为只能查看自己部门以及其下部门的，所以应该是executeDept like
			whereStr += "  and executeDept like '"+sessionForm.getDeptid()+"%'"; 
		}else if(!"admin".equals(sessionForm.getUserid())){//如果不是代维公司人员，则应该根据用户所属部门的所属地域来查看巡检任务，所以应该是根据areaId like
			
			ITawSystemDeptManager mgr = (ITawSystemDeptManager)getBean("ItawSystemDeptManager");
			TawSystemDept dept = mgr.getDeptinfobydeptid(sessionForm.getDeptid(), "0");
			String areaid = dept.getAreaid();
			if(areaid!=null && !areaid.trim().equals("")){
				whereStr += "  and country like '"+areaid+"%'"; 
			}else {
				return mapping.findForward("inspectnoprivforarcgis");
			}
		}
		if(!StringUtils.isEmpty(resName)){
			whereStr+="  and  resName like '%"+resName+"%'";
		}
		if(!StringUtils.isEmpty(inspectCycle)){
			whereStr+="  and  inspectCycle='"+inspectCycle+"'";
		}
		if(!StringUtils.isEmpty(executeObject)){
			whereStr+="  and  executeObject='"+executeObject+"'";
		}
		if(!StringUtils.isEmpty(inspectState)){
			whereStr+="  and  inspectState='"+inspectState+"'";
		}
//		whereStr+=" and res_Longitude is not null and res_Latitude is not null ";
		Map map = (Map) inspectPlanResMgr.findInspectPlanResList(pageIndex, pageSize, whereStr);
		List list = (List) map.get("result");
		request.setAttribute("list", list);
		//对当前页面进行json封装
		List jsonList=new ArrayList();
		if(!"1122502".equals(typeSpecialty)){
		for(int j=0;j<list.size();j++){
			InspectPlanRes pr=new InspectPlanRes();
			Map jsonMap=new HashMap();
			pr=(InspectPlanRes) list.get(j);
			String resUrl="/partner/res/PnrResConfig.do?method=detial&gisType=gisType&seldelcar="+pr.getResCfgId();
			jsonMap.put("resUrl", resUrl);
			jsonMap.put("resName", pr.getResName());
			String specialty= service.id2Name(pr.getSpecialty(), "tawSystemDictTypeDao");
			jsonMap.put("specialty", specialty);
			jsonMap.put("city", service.id2Name(pr.getCity(), "tawSystemAreaDao"));
			jsonMap.put("country", service.id2Name(pr.getCountry(), "tawSystemAreaDao"));
			jsonMap.put("resType", service.id2Name(pr.getResType(), "tawSystemDictTypeDao"));
			
			String planStartTime=StaticMethod.nullObject2String(pr.getPlanStartTime());
			jsonMap.put("planStartTime", planStartTime);
			String planEndTime=StaticMethod.nullObject2String(pr.getPlanEndTime());
			jsonMap.put("planEndTime",planEndTime);
			 executeObject=StaticMethod.nullObject2String(pr.getExecuteObject());
			jsonMap.put("executeObject",  service.id2Name(executeObject, "partnerDeptDao"));
			 inspectState=StaticMethod.nullObject2String(pr.getInspectState());
			//巡检状态(0待完成 1已完成 2超时未关联未完成 3超时已关联未完成)
			if ("1".equals(inspectState)) {
				inspectState="已完成";
			}else if("0".equals(inspectState)){
				inspectState="未完成";
			}else if("2".equals(inspectState)){
				inspectState="超时未关联未完成";
			}else if("3".equals(inspectState)){
				inspectState="超时已关联未完成";
			}
			else{
				inspectState="无法获取数据";
			}
			
			jsonMap.put("inspectState", inspectState);
			jsonMap.put("resLongitude",pr.getResLongitude());
			jsonMap.put("resLatitude",pr.getResLatitude());
			jsonMap.put("endLongitude",pr.getEndLongitude());
			jsonMap.put("endLatitude",pr.getEndLatitude());
			String signTime =StaticMethod.nullObject2String(pr.getSignTime());
			if("".equals(signTime)){
				signTime="未签到";
			}
			jsonMap.put("signTime",signTime);
			String inspectTime =StaticMethod.nullObject2String(pr.getInspectTime());
			if("".equals(inspectTime)){
				inspectTime="未签到";
			}
			jsonMap.put("inspectTime",inspectTime);
			//去除脏数据
			if("1122502".equals(pr.getSpecialty())&&!"0".equals(pr.getResLatitude())&&!"0".equals(pr.getResLongitude())&&!"0".equals(pr.getEndLongitude())&&!"0".equals(pr.getEndLatitude())){
				jsonList.add(jsonMap);
			}
			else if(!"1122502".equals(pr.getSpecialty())&&!"0".equals(pr.getResLatitude())&&!"0".equals(pr.getResLongitude())){
			jsonList.add(jsonMap);
			}
		}
		}
		Gson gson=new Gson();
		String jsonString=gson.toJson(jsonList);
		request.setAttribute("jsonString", jsonString);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		IInspectPlanApproveMgr inspectPlanApproveMgr = (IInspectPlanApproveMgr)getBean("inspectPlanApproveMgr");
		Search search = new Search();
		search.addFilterEqual("planId", id);
		List<InspectPlanApprove> approveList = inspectPlanApproveMgr.search(search);
		request.setAttribute("approveList", approveList);
		request.setAttribute("cycleMap", InspectConstants.cycleMap);
		return mapping.findForward("inspectPlanGisResListForArcGis");
	}
	
	
	/**
	* @Title: getInspectPlanGisMgr
	* @author liaojiming
	* @Description: TODO
	* @param @return    
	* @return IInspectPlanGisMgr    
	* @throws
	 */
	private IInspectPlanGisMgr getInspectPlanGisMgr(){
		
		return (IInspectPlanGisMgr)getBean("inspectPlanGisMgr");
	}
}
