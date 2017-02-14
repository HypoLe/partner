package com.boco.eoms.deviceManagement.externalForceCheck.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
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
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.deviceManagement.externalForceCheck.model.ExternalForceCheckModel;
import com.boco.eoms.deviceManagement.externalForceCheck.model.ExternalForceCheckSublist;
import com.boco.eoms.deviceManagement.externalForceCheck.service.ExternalForceCheckService;
import com.boco.eoms.deviceManagement.externalForceCheck.service.ExternalForceCheckSublistService;
import com.boco.eoms.deviceManagement.externalForceCheck.util.ReportModel;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public class ExternalForceCheckAction extends BaseAction {
	/*
	 * go to add page **/
	public ActionForward goToAddPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("goToAdd");
	}
	/*
	 * go to add sublist page **/
	public ActionForward goToAddSubPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		Search search = new Search();
		search.addFilterEqual("planId", id);
		search.addSortAsc("inOrder");
		ExternalForceCheckSublistService  forceSubService = (ExternalForceCheckSublistService)ApplicationContextHolder.getInstance().getBean("externalForceCheckSublistService");
		SearchResult  searchResult  = forceSubService.searchAndCount(search);
		request.setAttribute("modelList", searchResult.getResult());
		request.setAttribute("size", searchResult.getTotalCount());
		request.setAttribute("planId", id);
		return mapping.findForward("goToAddSub");
	}
	
	/*
	 * go to edit page **/
	public ActionForward goToEditPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		ExternalForceCheckService  forceService = (ExternalForceCheckService)ApplicationContextHolder.getInstance().getBean("externalForceCheckService");
		ExternalForceCheckModel   forceModel =forceService.find(id);
		String useId=forceModel.getOwnerUser();
		ID2NameService service = (ID2NameService) ApplicationContextHolder.getInstance().getBean("ID2NameGetServiceCatch");
		String userName  = service.id2Name(useId, "tawSystemUserDao");
		JSONArray sendUserAndRoles = new JSONArray();
		JSONObject data = new JSONObject();
		data.put("mobile", "");
		data.put("text", userName);
		data.put("name", userName);
		data.put("nodeType","user");
		data.put("id",useId);
		sendUserAndRoles.put(data);
		request.setAttribute("sendUserAndRoles", sendUserAndRoles);
		request.setAttribute("forceModel", forceModel);
		return mapping.findForward("goToEdit");
	}
	
	/*
	 * this method  is save the Entity **/
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String place=request.getParameter("place");
		String ownerUser=request.getParameter("ownerUser");
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		String route=request.getParameter("route");
		String routeStage=request.getParameter("routeStage");
		String gpsFacility=request.getParameter("gpsFacility");
		ExternalForceCheckModel   forceModel = new ExternalForceCheckModel();
		forceModel.setPlace(place);
		forceModel.setOwnerUser(ownerUser);
		forceModel.setStartDate(startDate);
		forceModel.setEndDate(endDate);
		forceModel.setRoute(route);
		forceModel.setRouteStage(routeStage);
		forceModel.setGpsFacility(gpsFacility);
		forceModel.setStatus("0");
		forceModel.setDeleted("0");
		ExternalForceCheckService  forceService = (ExternalForceCheckService)ApplicationContextHolder.getInstance().getBean("externalForceCheckService");
		forceService.save(forceModel);
		
		
		return mapping.findForward("success");
	}
	
	/*
	 * this method  is edit the plan if the status is '0'  **/
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		ExternalForceCheckService  forceService = (ExternalForceCheckService)ApplicationContextHolder.getInstance().getBean("externalForceCheckService");
		String id = request.getParameter("id");
		ExternalForceCheckModel  forceModel = forceService.find(id);
		String place=request.getParameter("place");
		String ownerUser=request.getParameter("ownerUser");
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		String route=request.getParameter("route");
		String routeStage=request.getParameter("routeStage");
		String gpsFacility=request.getParameter("gpsFacility");
		forceModel.setPlace(place);
		forceModel.setOwnerUser(ownerUser);
		forceModel.setStartDate(startDate);
		forceModel.setEndDate(endDate);
		forceModel.setRoute(route);
		forceModel.setRouteStage(routeStage);
		forceModel.setGpsFacility(gpsFacility);
		forceService.save(forceModel);
		
		
		return this.list(mapping, form, request, response);
	}
	
	
	
	
	/*
	 * delete the plan model **/
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExternalForceCheckService  forceService = (ExternalForceCheckService)ApplicationContextHolder.getInstance().getBean("externalForceCheckService");
		String deleteIds = request.getParameter("deleteIds");
		String [] deleteid = deleteIds.split(";");
		Search search = new Search();
		search.addFilterIn("id", deleteid);
		List modelList = forceService.search(search);
		for (Object object : modelList) {
			ExternalForceCheckModel model = (ExternalForceCheckModel)object;
			model.setDeleted("1");
			model.setStatus("-1");
			forceService.save(model);
			
		}
		
		return this.list(mapping, form, request, response);
	}
	
	/*
	 * this method  is show the plan list	  **/
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		ExternalForceCheckService  forceService = (ExternalForceCheckService)ApplicationContextHolder.getInstance().getBean("externalForceCheckService");
		Search search = new Search();
		//search.addFilterEqual("deleted", "0");
		String  placeStringLike= StaticMethod.null2String(request.getParameter("placeStringLike"));
		String  status= StaticMethod.null2String(request.getParameter("status")); 
		if(!"".endsWith(placeStringLike))
		search.addFilterLike("place", "%"+placeStringLike+"%");
		if(!"".endsWith(status))
		search.addFilterEqual("status", status);
		search.addFilterEqual("deleted", "0");
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				"externalForceCheckList")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		Integer pageSize = UtilMgrLocator.getEOMSAttributes().getPageSize();
		Integer pageIndex = new Integer(GenericValidator.isBlankOrNull(request
				.getParameter(pageIndexName)) ? 0 : (Integer.parseInt(request
				.getParameter(pageIndexName)) - 1));
		
		String exportType = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								"externalForceCheckList")
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_EXPORTTYPE)));
		if (!exportType.equals("")) {
			pageSize = new Integer(-1);
		}
		String order = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								"externalForceCheckList")
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_ORDER)));
		String sort = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								"externalForceCheckList")
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_SORT)));
		if(pageSize.intValue()!=-1){
			search.setFirstResult(pageIndex.intValue()*pageSize.intValue());
			search.setMaxResults(pageSize.intValue());
		}
		
		if (!order.equals("")) {
			if (order.equals("1")) {
				search.addSortAsc(sort);
			} else {
				search.addSortDesc(sort);
			}
		}
		SearchResult<ExternalForceCheckModel> searchResult = forceService.searchAndCount(search);
		request.setAttribute("pagesize", pageSize);
		request.setAttribute("size", searchResult.getTotalCount());
		request.setAttribute("externalForceCheckList",searchResult.getResult());
		
		return mapping.findForward("goToList");
	}
	
	
	/**
	 * this method  is save the Entity 
	 * */
	public ActionForward saveSub(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String executeUser=StaticMethod.null2String(request.getParameter("executeUser"));
		String dutyStartTime=StaticMethod.null2String(request.getParameter("startDate"));
		String dutyEndTime=StaticMethod.null2String(request.getParameter("endDate"));
		String dutyDiary=StaticMethod.null2String(request.getParameter("dutyDiary"));
		String safety=StaticMethod.null2String(request.getParameter("safety"));
		String planid = StaticMethod.null2String(request.getParameter("planId"));
		int inOrder = StaticMethod.null2int(request.getParameter("inOrder"));
		String type=request.getParameter("type");
		
		ExternalForceCheckSublist   forceModel = new ExternalForceCheckSublist();
		ExternalForceCheckSublistService  forceService = (ExternalForceCheckSublistService)ApplicationContextHolder.getInstance().getBean("externalForceCheckSublistService");
		if(!"".equals(executeUser)&&!"".equals(dutyStartTime)&&!"".equals(dutyEndTime)&&!"".equals(dutyDiary)&&!"".equals(safety)){
		if(inOrder==0){
			forceModel.setInOrder(1);
			forceModel.setDutySpace("第一次无间隔");
			}
		else{
			forceModel.setInOrder(inOrder+1);
			Search search = new Search();
			search.addFilterEqual("inOrder", inOrder);
			search.addFilterEqual("planId", planid);
			ExternalForceCheckSublist previousMode = forceService.searchUnique(search);
			Date endDate = CommonUtils.toEomsStandardDate(previousMode.getDutyEndTime());
			Date starDate = CommonUtils.toEomsStandardDate(dutyStartTime);
			long space = starDate.getTime()-endDate.getTime();
			String dutySpace = String.valueOf(space/3600000)+"小时" +String.valueOf((253723%3600000)/60000)+"分钟";
			forceModel.setDutySpace(dutySpace);
		}
		forceModel.setExecuteUser(executeUser); 
		forceModel.setDutyStartTime(dutyStartTime);
		forceModel.setDutyEndTime(dutyEndTime);
		forceModel.setDutyDiary(dutyDiary);
		forceModel.setSafety(safety);
		forceModel.setPlanId(planid);
		
		forceModel.setDutyStatus("2");
		
		forceService.save(forceModel);
		}
		ExternalForceCheckService  Service = (ExternalForceCheckService)ApplicationContextHolder.getInstance().getBean("externalForceCheckService");
		ExternalForceCheckModel model =Service.find(planid);
		model.setStatus("1");
		if("final".equals(type)){
			model.setStatus("2");
		}
		Service.save(model);
		return mapping.findForward("success");
	}
	
	/**
	 * this method  detail the plan and the dutyDiary 
	 * */
	public ActionForward detail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		ExternalForceCheckService  forceService = (ExternalForceCheckService)ApplicationContextHolder.getInstance().getBean("externalForceCheckService");
		ExternalForceCheckSublistService  forceServiceSub = (ExternalForceCheckSublistService)ApplicationContextHolder.getInstance().getBean("externalForceCheckSublistService");
		String id = request.getParameter("id");
		ExternalForceCheckModel model= forceService.find(id);
		Search search = new Search();
		search .addFilterEqual("planId", id);
		List<ExternalForceCheckSublist> subModelList = forceServiceSub.search(search);
		request.setAttribute("forceModel", model);
		request.setAttribute("subModelList", subModelList);
		String useId=model.getOwnerUser();
		ID2NameService service = (ID2NameService) ApplicationContextHolder.getInstance().getBean("ID2NameGetServiceCatch");
		String userName  = service.id2Name(useId, "tawSystemUserDao");
		JSONArray sendUserAndRoles = new JSONArray();
		JSONObject data = new JSONObject();
		data.put("mobile", "");
		data.put("text", userName);
		data.put("name", userName);
		data.put("nodeType","user");
		data.put("id",useId);
		sendUserAndRoles.put(data);
		request.setAttribute("sendUserAndRoles", sendUserAndRoles);
		
		return mapping.findForward("goToDetail");
	}
	
	/**
	 * reportFrom list  
	 * */
	public ActionForward reportList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		ExternalForceCheckService  forceService = (ExternalForceCheckService)ApplicationContextHolder.getInstance().getBean("externalForceCheckService");
		ExternalForceCheckSublistService  forceServiceSub = (ExternalForceCheckSublistService)ApplicationContextHolder.getInstance().getBean("externalForceCheckSublistService");
		Search search = new Search();
		//search.addFilterEqual("deleted", "0");
		String  placeStringLike= StaticMethod.null2String(request.getParameter("placeStringLike"));
		String  status= StaticMethod.null2String(request.getParameter("status")); 
		if(!"".endsWith(placeStringLike))
		search.addFilterLike("place", "%"+placeStringLike+"%");
//		if(!"".endsWith(status))
//		search.addFilterEqual("status", status);
		search.addFilterEqual("status", "2");
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				"externalForceCheckList")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		Integer pageSize = UtilMgrLocator.getEOMSAttributes().getPageSize();
		Integer pageIndex = new Integer(GenericValidator.isBlankOrNull(request
				.getParameter(pageIndexName)) ? 0 : (Integer.parseInt(request
				.getParameter(pageIndexName)) - 1));
		
		String exportType = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								"externalForceCheckList")
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_EXPORTTYPE)));
		if (!exportType.equals("")) {
			pageSize = new Integer(-1);
		}
		String order = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								"externalForceCheckList")
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_ORDER)));
		String sort = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								"externalForceCheckList")
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_SORT)));
		if(pageSize.intValue()!=-1){
			search.setFirstResult(pageIndex.intValue()*pageSize.intValue());
			search.setMaxResults(pageSize.intValue());
		}
		
		if (!order.equals("")) {
			if (order.equals("1")) {
				search.addSortAsc(sort);
			} else {
				search.addSortDesc(sort);
			}
		}
		SearchResult<ExternalForceCheckModel> searchResult = forceService.searchAndCount(search);
		List  list = searchResult.getResult();
		List<ReportModel> externalForceCheckList = new ArrayList<ReportModel> ();
		
		for (int i = 0; i < list.size(); i++) {
			ExternalForceCheckModel mode = (ExternalForceCheckModel)list.get(i);
			ReportModel  reModel = new ReportModel();
			BeanUtils.copyProperties(reModel, mode);
			mode.getOwnerUser();
			Search subSearch = new Search();
			subSearch.addFilterEqual("planId", mode.getId());
			SearchResult result=forceServiceSub.searchAndCount(subSearch);
			subSearch.addFilterEqual("executeUser", mode.getOwnerUser());
			SearchResult resultUser=forceServiceSub.searchAndCount(subSearch);
			Float ownerRate=new Float(resultUser.getTotalCount())/new Float(result.getTotalCount())*100;
			Float executeRate = 100-ownerRate;
			if(ownerRate!=0)
			reModel.setOwnerRate(String.valueOf(ownerRate).substring(0,5)+"%");
			else
				reModel.setOwnerRate("0%");
			if(executeRate!=0)
			reModel.setExecuteRate(String.valueOf(executeRate).substring(0,5)+"%");
			else
				reModel.setExecuteRate("0%");
			externalForceCheckList.add(reModel);
			
		}
		
		request.setAttribute("pagesize", pageSize);
		request.setAttribute("size", searchResult.getTotalCount());
		request.setAttribute("externalForceCheckList",externalForceCheckList);
		return mapping.findForward("reportList");
	}
	
}
