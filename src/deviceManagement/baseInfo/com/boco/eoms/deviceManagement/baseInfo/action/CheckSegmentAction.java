package com.boco.eoms.deviceManagement.baseInfo.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.deviceManagement.baseInfo.model.CheckPoint;
import com.boco.eoms.deviceManagement.baseInfo.model.CheckSegment;
import com.boco.eoms.deviceManagement.baseInfo.service.CheckPointService;
import com.boco.eoms.deviceManagement.baseInfo.service.CheckSegmentService;
import com.boco.eoms.deviceManagement.faultInfo.utils.CommonConstants;
import com.boco.eoms.deviceManagement.faultInfo.utils.CommonUtils;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public class CheckSegmentAction extends BaseAction {

	public ActionForward goToAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("goToAdd");
	}

	public ActionForward goToEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CheckSegmentService checkSegmentService = (CheckSegmentService) getBean("checkSegmentService");
		String id = request.getParameter("id");
		request.setAttribute("checkSegment", checkSegmentService.findById(id));
		return mapping.findForward("goToEdit");
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CheckSegmentService checkSegmentService = (CheckSegmentService) getBean("checkSegmentService");
		CheckSegment checkSegment = new CheckSegment();
		checkSegment.setSegmentName(request.getParameter("name"));
		checkSegment.setLineLevel(request.getParameter("lineLevel"));
		checkSegment.setLineType(request.getParameter("lineType"));
		checkSegment.setStartFlag(request.getParameter("startFlag"));
		checkSegment.setEndFlag(request.getParameter("endFlag"));
		checkSegment.setSegmentType(request.getParameter("segmentType"));
		checkSegment.setDepartment(request.getParameter("department"));
		checkSegment.setDeleteFlag("0");
		checkSegmentService.add(checkSegment);
		return mapping.findForward("success");

	}

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CheckSegmentService checkSegmentService = (CheckSegmentService) getBean("checkSegmentService");
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"checkSegmentList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		SearchResult<CheckSegment> searchResult = checkSegmentService.listAll(
				firstResult * CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE);
		request.setAttribute("checkSegmentList", searchResult.getResult());
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("list");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CheckSegmentService checkSegmentService = (CheckSegmentService) getBean("checkSegmentService");
		String id = request.getParameter("id");
		checkSegmentService.delete(id);
		CommonUtils.printJsonSuccessMsg(response);
		return null;
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CheckSegmentService checkSegmentService = (CheckSegmentService) getBean("checkSegmentService");
		checkSegmentService.edit(request.getParameter("id"), request
				.getParameter("segmentName"), request.getParameter("lineType"),
				request.getParameter("lineLevel"), request
						.getParameter("segmentType"), request
						.getParameter("startFlag"), request
						.getParameter("endFlag"));
		return mapping.findForward("success");
	}

	public ActionForward find(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CheckSegmentService checkSegmentService = (CheckSegmentService) getBean("checkSegmentService");
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"checkSegmentList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		SearchResult<CheckSegment> searchResult = checkSegmentService.find2(
				request.getParameter("segmentName"), request
						.getParameter("segmentType"), request.getParameter("department"), firstResult * CommonConstants.PAGE_SIZE,
				CommonConstants.PAGE_SIZE);
		request.setAttribute("checkSegmentList", searchResult.getResult());
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("list");
	}
	
	public ActionForward deleteSome(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CheckSegmentService checkSegmentService = (CheckSegmentService) getBean("checkSegmentService");
		String ids = request.getParameter("ids");
		String[] id = ids.substring(0, ids.lastIndexOf(";")).split(";");
		checkSegmentService.deltetSome(id);
		CommonUtils.printJsonSuccessMsg(response);
		return null;
	}

	
	public ActionForward checkpoint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String id = request.getParameter("id");
		CheckPointService checkPointService = (CheckPointService) getBean("checkPointService");
		Search search = new Search();
		search.addFilterEqual("checkPointSegmentId", id) ;
		
		
		SearchResult<CheckPoint> searchResult = checkPointService
		.searchAndCount(search);
		request.setAttribute("checkPointList", searchResult.getResult());
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("checkpoint");
	}
}
