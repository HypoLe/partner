package com.boco.eoms.deviceManagement.GPSManagement.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.GPSManagement.model.GpsManagementModel;
import com.boco.eoms.deviceManagement.GPSManagement.service.GpsManagementService;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public class GpsManagementAction extends BaseAction {
	/*
	 * go to add page **/
	public ActionForward goToAddPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("goToAdd");
	}
	
	/*
	 * go to edit page **/
	public ActionForward goToEditPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GpsManagementService gpsManagement = (GpsManagementService)ApplicationContextHolder.getInstance().getBean("gpsManagementService");
		String id = request.getParameter("id");
		GpsManagementModel gpsModel =gpsManagement.find(id);
		request.setAttribute("gpsModel", gpsModel);
		return mapping.findForward("goToEdit");
	}
	
	
	/*
	 * go to search list page **/
	public ActionForward goToListPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("goToList");
	}
	
	/*
	 * save the GPS final fancility model **/
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GpsManagementService gpsManagement = (GpsManagementService)ApplicationContextHolder.getInstance().getBean("gpsManagementService");
//		Map paramMap =request.getParameterMap();
//		Search search = new Search();
//		search=CommonUtils.getSqlFromRequestMapWithHidden(paramMap, GpsManagementModel.class, search);
		String gsmid = request.getParameter("gsmid");
		String type = request.getParameter("type");
		String factory = request.getParameter("factory");
		String gpsmodel = request.getParameter("gpsmodel");
		String gpsselfnumber = request.getParameter("gpsselfnumber");
		String remark = StaticMethod.null2String(request.getParameter("remark")) ;
		TawSystemSessionForm sessionform = this.getUser(request);
		String deptid = sessionform.getDeptid();
		GpsManagementModel model = new GpsManagementModel();
		model.setGsmid(gsmid);
		model.setGpstype(type);
		model.setFactory(factory);
		model.setGpsmodel(gpsmodel);
		model.setGpsselfnumber(gpsselfnumber);
		model.setRemark(remark);
		model.setDeptid(deptid);
		model.setDeleted("0");
		gpsManagement.save(model);
		return mapping.findForward("success");
	}
	
	/*
	 * edit the GPS final fancility model **/
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GpsManagementService gpsManagement = (GpsManagementService)ApplicationContextHolder.getInstance().getBean("gpsManagementService");
		String id = request.getParameter("id");
		String gsmid = request.getParameter("gsmid");
		String type = request.getParameter("type");
		String factory = request.getParameter("factory");
		String gpsmodel = request.getParameter("gpsmodel");
		String gpsselfnumber = request.getParameter("gpsselfnumber");
		String remark = StaticMethod.null2String(request.getParameter("remark")) ;
		GpsManagementModel model = gpsManagement.find(id);
		model.setGsmid(gsmid);
		model.setGpstype(type);
		model.setFactory(factory);
		model.setGpsmodel(gpsmodel);
		model.setGpsselfnumber(gpsselfnumber);
		model.setRemark(remark);
		gpsManagement.save(model);
		return mapping.findForward("goToList");
	}
	

	/*
	 * delete the GPS final fancility model **/
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GpsManagementService gpsManagement = (GpsManagementService)ApplicationContextHolder.getInstance().getBean("gpsManagementService");
		String deleteIds = request.getParameter("deleteIds");
		String [] deleteid = deleteIds.split(";");
		Search search = new Search();
		search.addFilterIn("id", deleteid);
		List modelList = gpsManagement.search(search);
		for (Object object : modelList) {
			GpsManagementModel model = (GpsManagementModel)object;
			model.setDeleted("1");
			gpsManagement.save(model);
		}
		
		return this.list(mapping, form, request, response);
	}
	
	/*
	 * search list for  the GPS final fancility model **/
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map map = request.getParameterMap();
		GpsManagementService gpsManagement = (GpsManagementService)ApplicationContextHolder.getInstance().getBean("gpsManagementService");
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(map,search);
		search.addFilterEqual("deleted", "0");

		String pageIndexName = new org.displaytag.util.ParamEncoder(
				"gpsManagementList")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		Integer pageSize = UtilMgrLocator.getEOMSAttributes().getPageSize();
		Integer pageIndex = new Integer(GenericValidator.isBlankOrNull(request
				.getParameter(pageIndexName)) ? 0 : (Integer.parseInt(request
				.getParameter(pageIndexName)) - 1));

		String exportType = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								"gpsManagementList")
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_EXPORTTYPE)));
		if (!exportType.equals("")) {
			pageSize = new Integer(-1);
		}
		String order = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								"gpsManagementList")
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_ORDER)));
		String sort = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								"gpsManagementList")
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
		
		SearchResult<GpsManagementModel> searchResult = gpsManagement.searchAndCount(search);
		request.setAttribute("pagesize", pageSize);
		request.setAttribute("size", searchResult.getTotalCount());
		request.setAttribute("gpsManagementList",searchResult.getResult());
		return  mapping.findForward("goToList");
	}
	
	
	
	/*
	 * search detail for  the GPS final fancility model **/
	public ActionForward goToDetailPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		GpsManagementService gpsManagement = (GpsManagementService)ApplicationContextHolder.getInstance().getBean("gpsManagementService");
		String id = request.getParameter("id");
		GpsManagementModel gpsModel =gpsManagement.find(id);
		request.setAttribute("gpsModel", gpsModel);
		return mapping.findForward("goToDetail");
	}
	
	
	/**
	 * 单独拿出来的统计模块*/
	public ActionForward publicList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map map = request.getParameterMap();
		GpsManagementService gpsManagement = (GpsManagementService)ApplicationContextHolder.getInstance().getBean("gpsManagementService");
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(map,search);
		search.addFilterEqual("deleted", "0");

		String pageIndexName = new org.displaytag.util.ParamEncoder(
				"")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		Integer pageSize = UtilMgrLocator.getEOMSAttributes().getPageSize();
		Integer pageIndex = new Integer(GenericValidator.isBlankOrNull(request
				.getParameter(pageIndexName)) ? 0 : (Integer.parseInt(request
				.getParameter(pageIndexName)) - 1));

		String exportType = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								"gpsManagementList")
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_EXPORTTYPE)));
		if (!exportType.equals("")) {
			pageSize = new Integer(-1);
		}
		String order = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								"gpsManagementList")
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_ORDER)));
		String sort = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								"gpsManagementList")
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
		
		SearchResult<GpsManagementModel> searchResult = gpsManagement.searchAndCount(search);
		request.setAttribute("pagesize", pageSize);
		request.setAttribute("size", searchResult.getTotalCount());
		request.setAttribute("gpsManagementList",searchResult.getResult());
		return  mapping.findForward("publicList");
	}
	
}
