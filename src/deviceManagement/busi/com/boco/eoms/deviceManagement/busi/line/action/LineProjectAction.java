package com.boco.eoms.deviceManagement.busi.line.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.deviceManagement.busi.line.model.ProjectBaseInfo;
import com.boco.eoms.deviceManagement.busi.line.service.LineProjectService;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.deviceManagement.faultInfo.utils.CommonConstants;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.SearchResult;

public class LineProjectAction extends BaseAction {

	public ActionForward goToAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("goToAdd");
	}

	public ActionForward apply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LineProjectService lineProjectService = (LineProjectService) getBean("lineProjectService");
		lineProjectService.applyProject(request.getParameter("id"), request.getParameter("projectName"),
				request.getParameter("projectType"), request
						.getParameter("projectLocation"), request
						.getParameter("networkType"), getUserId(request),
				getUser(request).getCompanyId(),
				request.getParameter("effect"), request.getParameter("length1"),
				request.getParameter("attachment"), request
						.getParameter("startTime"), request
						.getParameter("lastTime"), request
						.getParameter("allower"), request
						.getParameter("remarks"));
		return mapping.findForward("success");
	}

	public ActionForward listCheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LineProjectService lineProjectService = (LineProjectService) getBean("lineProjectService");
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"projectBaseInfoList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		int start = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		SearchResult<ProjectBaseInfo> searchResult = lineProjectService
				.listCheck(getUserId(request), start
						* CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE);
		request.setAttribute("projectBaseInfoList", searchResult.getResult());
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("listCheck");

	}

	public ActionForward goToCheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LineProjectService lineProjectService = (LineProjectService) getBean("lineProjectService");
		String id = request.getParameter("id");
		request
				.setAttribute("projectBaseInfo", lineProjectService
						.findById(id));
		return mapping.findForward("goToCheck");
	}

	public ActionForward check(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LineProjectService lineProjectService = (LineProjectService) getBean("lineProjectService");
		String id = request.getParameter("id");
		String passStr = request.getParameter("allowResult");
		boolean pass = "0".equals(passStr) ? true : false;
		lineProjectService.checkProject(getUserId(request), id, request
				.getParameter("checkerId"), request.getParameter("checkDate"),
				request.getParameter("projectPay"), request
						.getParameter("checkattachment"), request
						.getParameter("checkRemarks"), request
						.getParameter("consigneeId"), request
						.getParameter("allowRemarks"), pass);
		return mapping.findForward("success");
	}

	public ActionForward listCommis(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LineProjectService lineProjectService = (LineProjectService) getBean("lineProjectService");
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"projectBaseInfoList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		int start = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		SearchResult<ProjectBaseInfo> searchResult = lineProjectService
				.listCommission(getUserId(request), start
						* CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE);
		request.setAttribute("projectBaseInfoList", searchResult.getResult());
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("listCommis");

	}

	public ActionForward goToCommis(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LineProjectService lineProjectService = (LineProjectService) getBean("lineProjectService");
		String id = request.getParameter("id");
		request
				.setAttribute("projectBaseInfo", lineProjectService
						.findById(id));
		return mapping.findForward("goToCommis");
	}

	public ActionForward commis(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LineProjectService lineProjectService = (LineProjectService) getBean("lineProjectService");
		String id = request.getParameter("id");
		lineProjectService.commisProject(getUserId(request), id, request
				.getParameter("constructor"), request
				.getParameter("constructorLocation"), request
				.getParameter("constructorPhone"), request
				.getParameter("constructorContacter"), request
				.getParameter("constructorType"), request
				.getParameter("constructPay"), request
				.getParameter("constructStartTime"), request
				.getParameter("constructRemarks"), request
				.getParameter("constructattachment"));
		return mapping.findForward("success");
	}

	public ActionForward listConstruct(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LineProjectService lineProjectService = (LineProjectService) getBean("lineProjectService");
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"projectBaseInfoList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		int start = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		SearchResult<ProjectBaseInfo> searchResult = lineProjectService
				.listConstruct(getUserId(request), start
						* CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE);
		request.setAttribute("projectBaseInfoList", searchResult.getResult());
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("listConstruct");
	}

	public ActionForward goToConsturct(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LineProjectService lineProjectService = (LineProjectService) getBean("lineProjectService");
		String id = request.getParameter("id");
		request
				.setAttribute("projectBaseInfo", lineProjectService
						.findById(id));
		return mapping.findForward("goToConsturct");
	}

	public ActionForward consturct(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LineProjectService lineProjectService = (LineProjectService) getBean("lineProjectService");
		String id = request.getParameter("id");
		lineProjectService.construtProject(getUserId(request), id, request
				.getParameter("constructor2"), request
				.getParameter("constructorLocation2"), request
				.getParameter("constructorPhone2"), request
				.getParameter("constructorContacter2"), request
				.getParameter("constructStartTime2"), request
				.getParameter("constructEndTime2"), request
				.getParameter("constructRemarks2"), request
				.getParameter("constructReview"));
		return mapping.findForward("success");
	}

	public ActionForward listAccept(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LineProjectService lineProjectService = (LineProjectService) getBean("lineProjectService");
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"projectBaseInfoList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		int start = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		SearchResult<ProjectBaseInfo> searchResult = lineProjectService
				.listAccept(getUserId(request), start
						* CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE);
		request.setAttribute("projectBaseInfoList", searchResult.getResult());
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("listAccept");
	}

	public ActionForward goToAccept(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LineProjectService lineProjectService = (LineProjectService) getBean("lineProjectService");
		String id = request.getParameter("id");
		request
				.setAttribute("projectBaseInfo", lineProjectService
						.findById(id));
		return mapping.findForward("goToAccept");
	}

	public ActionForward accept(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LineProjectService lineProjectService = (LineProjectService) getBean("lineProjectService");
		String id = request.getParameter("id");
		lineProjectService.acceptProject(getUserId(request), id, request
				.getParameter("acceptResult"), request
				.getParameter("acceptRemarks"), request
				.getParameter("acceptattachment"));
		return mapping.findForward("success");
	}
	
	public ActionForward listUncheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LineProjectService lineProjectService = (LineProjectService) getBean("lineProjectService");
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"projectBaseInfoList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		int start = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		SearchResult<ProjectBaseInfo> searchResult = lineProjectService
				.listUncheck(getUserId(request), start
						* CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE);
		request.setAttribute("projectBaseInfoList", searchResult.getResult());
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("listUncheck");		
	} 
	
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LineProjectService lineProjectService = (LineProjectService) getBean("lineProjectService");
		String id = request.getParameter("id");
		lineProjectService.delete(id);
		CommonUtils.printJsonSuccessMsg(response);
		return null;
	}
	
	public ActionForward goToReapply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LineProjectService lineProjectService = (LineProjectService) getBean("lineProjectService");
		String id = request.getParameter("id");
		ProjectBaseInfo projectBaseInfo = lineProjectService.findById(id);
		request.setAttribute("projectBaseInfo", projectBaseInfo);
		if("申请驳回".equals(projectBaseInfo.getStatus())) {
			request.setAttribute("projectOperInfoList", lineProjectService.findReject(id));
			return mapping.findForward("goToReapply1");	
		}else {
			return mapping.findForward("goToReapply2");
		}			
	}
	
	public ActionForward listFind(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LineProjectService lineProjectService = (LineProjectService) getBean("lineProjectService");
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"projectBaseInfoList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		int start = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		SearchResult<ProjectBaseInfo> searchResult = lineProjectService
				.listFind(request.getParameter("projectName"), request.getParameter("networkType"), request.getParameter("status"), start
						* CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE);
		request.setAttribute("projectBaseInfoList", searchResult.getResult());
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("listFind");
	}
	
	public ActionForward goToDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LineProjectService lineProjectService = (LineProjectService) getBean("lineProjectService");
		String id = request.getParameter("id");
		request.setAttribute("projectBaseInfo", lineProjectService.findById(id));
		request.setAttribute("projectOperInfo", lineProjectService.findOperInfoById(id));
		return mapping.findForward("goToDetail");
	}

}
