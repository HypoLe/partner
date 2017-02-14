package com.boco.eoms.deviceManagement.faultSheet.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcService;
import com.boco.eoms.deviceManagement.faultInfo.utils.CommonConstants;
import com.boco.eoms.deviceManagement.faultInfo.utils.CommonUtils;
import com.boco.eoms.deviceManagement.faultSheet.model.FaultSheetManagement;
import com.boco.eoms.deviceManagement.faultSheet.model.FaultSheetManagementForm;
import com.boco.eoms.deviceManagement.faultSheet.model.FaultSheetResponse;
import com.boco.eoms.deviceManagement.faultSheet.model.FaultSheetResponseForm;
import com.boco.eoms.deviceManagement.faultSheet.service.FaultSheetManagementService;
import com.boco.eoms.deviceManagement.faultSheet.service.FaultSheetResponseService;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public final class FaultSheetManagementAction extends BaseAction {

	public FaultSheetManagementService getMainBean() {
		String source = FaultSheetManagementService.class.getSimpleName();
		return (FaultSheetManagementService) getBean(source.substring(0, 1)
				.toLowerCase().concat(source.substring(1)));
	}

	public CommonSpringJdbcService getJdbcBean() {
		String source = CommonSpringJdbcService.class.getSimpleName();
		return (CommonSpringJdbcService) getBean(source.substring(0, 1)
				.toLowerCase().concat(source.substring(1)));
	}

	public ActionForward forwardlist(ActionMapping mapping) {
		ActionForward forward = mapping.findForward("forwardlist");
		String path = forward.getPath();
		return new ActionForward(path, false);
	}

	// 添加页面跳转
	public ActionForward addForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("addForm");
	}

	public ActionForward responseAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(),
				search);
		String work_OrderNo = request.getParameter("work_OrderNo");
		String themess = request.getParameter("themess");
		if (null != work_OrderNo && null != themess) {
			search.addFilterILike("operatePerson", "%" + themess + "%")
					.addFilterILike("operatePerson_Department",
							"%" + themess + "%");
		}
		search.addFilterLike("faultState", "派发");
		search.addSort("operateTime", true);
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
		String operatePerson_Department1 = sessionform.getDeptid();
		search.addFilterIn("detailment_Object", userId,
				operatePerson_Department1);
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"faultSheetListResponse")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		FaultSheetManagementService fs = (FaultSheetManagementService) getBean("FaultSheetManagementService");
		SearchResult<FaultSheetManagement> searchResult = fs
				.searchAndCount(search);
		List<FaultSheetManagement> faultSheetList = searchResult.getResult();

		String timenow = CommonUtils.toEomsStandardDate(new Date());
		for (int i = 0; i < faultSheetList.size(); i++) {
			// faultSheetList.get(i).setResponsereceivedtime(timenow);
			String idd = faultSheetList.get(i).getId();
			FaultSheetManagement fsm = fs.find(idd);
			String limitetime = fsm.getReceivedtimelimited();
			if (timenow != null && limitetime != null) {
				if (CommonUtils.toEomsStandardDate(timenow).after(
						CommonUtils.toEomsStandardDate(fsm
								.getReceivedtimelimited()))) {
					fsm.setAreInTimeReceived("0");// 0为延时接收
				} else {
					fsm.setAreInTimeReceived("1");// 1为按时接收
				}
			}
			String sendperson = faultSheetList.get(i).getDetailment_Object();
			if (sendperson.matches("[0-9]+")) {
				fs.save(fsm);
				request.setAttribute("faultSheetListResponse", faultSheetList);
				request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
				request.setAttribute("bsfrList", searchResult.getResult());
				request.setAttribute("size", searchResult.getTotalCount());
			} else if (sendperson.equals(userId)) {
				fs.save(fsm);
				request.setAttribute("faultSheetListResponse", faultSheetList);
				request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
				request.setAttribute("bsfrList", searchResult.getResult());
				request.setAttribute("size", searchResult.getTotalCount());
			}
		}

		return mapping.findForward("responseAddForm");
	}

	// 查看回复详细信息
	public ActionForward detailAa(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		FaultSheetResponseService fs = (FaultSheetResponseService) getBean("FaultSheetResponseService");
		FaultSheetResponse frs = fs.find(id);
		request.setAttribute("responsedetailaa", frs);
		// return mapping.findForward("displaydetail");
		return mapping.findForward("detailaa");
	}

	// 归档
	public ActionForward archiving(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String work_OrderNo = request.getParameter("work_OrderNo");
		String id = request.getParameter("id");
		Search search = new Search();
		search.addFilterEqual("work_OrderNo", work_OrderNo);
		search.addSort("operateTime", true);
		FaultSheetManagementService fs = (FaultSheetManagementService) getBean("FaultSheetManagementService");
		List<FaultSheetManagement> frs = fs.search(search);
		for (FaultSheetManagement Management : frs) {
			Management.setFaultState("归档");
			fs.save(Management);
		}
		FaultSheetResponseService fss = (FaultSheetResponseService) getBean("FaultSheetResponseService");
		FaultSheetResponse fsr = fss.find(id);
		fsr.setDealstata("已处理");
		fss.save(fsr);
		return mapping.findForward("success");
	}

	// 回复页面跳转
	public ActionForward goToResponseDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		FaultSheetManagementService fs = (FaultSheetManagementService) getBean("FaultSheetManagementService");
		FaultSheetManagement frs = fs.find(id);
		request.setAttribute("faultSheetDetail", frs);
		return mapping.findForward("responseListForm");
	}

	// 跳转到详情页面
	public ActionForward goToDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		FaultSheetManagementService fs = (FaultSheetManagementService) getBean("FaultSheetManagementService");
		FaultSheetManagement frs = fs.find(id);
		request.setAttribute("faultSheetDetail", frs);
		return mapping.findForward("firstForm");
	}

	public ActionForward goToDetailHistory(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		FaultSheetManagementService fs = (FaultSheetManagementService) getBean("FaultSheetManagementService");
		FaultSheetManagement frs = fs.find(id);
		request.setAttribute("faultSheetDetail", frs);
		return mapping.findForward("historydetailresponse");
	}

	public ActionForward listForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return this.list(mapping, form, request, response);
	}

	public ActionForward gotoSuccess1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return mapping.findForward("successfully");
	}

	public ActionForward gotoFail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return mapping.findForward("faill");
	}

	// 回复添加处理
	public ActionForward responseAddDeal(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
		String operatePerson_Department = sessionform.getDeptid();
		String operatePerson_Contact = sessionform.getContactMobile();
		String roleId = sessionform.getRoleid();
		FaultSheetResponseService fs = (FaultSheetResponseService) getBean("FaultSheetResponseService");
		String operateTime = CommonUtils.toEomsStandardDate(new Date());
		FaultSheetResponseForm fr = (FaultSheetResponseForm) form;
		FaultSheetResponse fsr = (FaultSheetResponse) convert(fr);
		fsr.setOperatePerson(userId);
		fsr.setOperatePerson_Department(operatePerson_Department);
		fsr.setOperatePerson_Rule(roleId);
		fsr.setOperatePerson_Contact(operatePerson_Contact);
		fsr.setOperateTime(operateTime);
		String work_OrderNo = request.getParameter("work_OrderNo"); // 传递需要处理的信息的工单编号.
		String themess = request.getParameter("themess");
		String completedtime = request.getParameter("processLimited");
		fsr.setCompletedtime(completedtime);
		fsr.setWork_OrderNo(work_OrderNo);
		fsr.setThemess(themess);
		FaultSheetManagementService fms = (FaultSheetManagementService) getBean("FaultSheetManagementService");
		String id = request.getParameter("id");
		String operatePerson = request.getParameter("operatePerson");
		String operatePersonDept = request
				.getParameter("operatePerson_Department");
		fsr.setDetailment_Object(operatePerson);
		fsr.setDetailment_dept(operatePersonDept);
		boolean resultsave = fs.save(fsr);
		if (resultsave == false) {
			return mapping.findForward("faill");
		}
		FaultSheetManagement fsm = fms.find(id);
		fsm.setFaultState("处理中");
		boolean right = fms.save(fsm);
		if (right != false) {
			return mapping.findForward("faill");
		}
		// return this.responseAdd(mapping, form, request, response);
		return mapping.findForward("successfully");
	}

	// 添加故障管理信息
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BocoLog.info(this, "协议添加开始");

		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
		// String username=sessionform.getUsername();
		String operatePerson_Department = sessionform.getDeptid();
		// String operatePerson_Department = sessionform.getDeptname();
		String operatePerson_Contact = sessionform.getContactMobile();
		String operatePerson_Rule = sessionform.getRolename();
		String operateTime = CommonUtils.toEomsStandardDate(new Date());
		FaultSheetManagementForm fsmForm = (FaultSheetManagementForm) form;
		FaultSheetManagement bsfr = (FaultSheetManagement) convert(fsmForm);
		bsfr.setOperatePerson(userId);
		bsfr.setOperatePerson_Department(operatePerson_Department);
		bsfr.setOperatePerson_Rule(operatePerson_Rule);
		bsfr.setOperatePerson_Contact(operatePerson_Contact);
		bsfr.setOperateTime(operateTime);
		FaultSheetManagementService fs = (FaultSheetManagementService) getBean("FaultSheetManagementService");
		bsfr.setWork_OrderNo(fs.Work_Order_No());// 此条添加工单编号
		String detailment_dept = request.getParameter("detailment_Object");
		if (detailment_dept.matches("[0-9]+")) {
			bsfr.setDetailment_dept(detailment_dept);
		} else {
		}

		bsfr.setFaultState("派发");
		// list.add(bsfr);
		bsfr.setIdDeleted("0");
		// request.setAttribute("faultsheetmanagement", bsfr);
		boolean aa = fs.save(bsfr);

		if (aa == false) {
			return mapping.findForward("faill");
		}
		// 设置跳转页面
		BocoLog.info(this, "协议添加结束");

		return mapping.findForward("success2");
	}

	// 查询全部
	public ActionForward listAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(),
				search);
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"faultSheetList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.addSort("operateTime", true);
		FaultSheetManagementService fs = (FaultSheetManagementService) getBean("FaultSheetManagementService");
		SearchResult<FaultSheetManagement> searchResult = fs
				.searchAndCount(search);
		List<FaultSheetManagement> faultSheetList = searchResult.getResult();
		request.setAttribute("faultSheetList", faultSheetList);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("bsfrList", searchResult.getResult());
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("list");
	}

	// 故障工单查询
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(),
				search);
		String work_OrderNo = request.getParameter("work_OrderNo");
		String themess = request.getParameter("themess");

		if (null != work_OrderNo && null != themess) {
			search.addFilterILike("work_OrderNo", "%" + work_OrderNo + "%")
					.addFilterILike("themess", "%" + themess + "%");
		}
		search.addFilterEqual("idDeleted", "0");

		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"faultSheetList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.addSort("operateTime", true);
		FaultSheetManagementService fs = (FaultSheetManagementService) getBean("FaultSheetManagementService");
		SearchResult<FaultSheetManagement> searchResult = fs
				.searchAndCount(search);
		List<FaultSheetManagement> faultSheetList = searchResult.getResult();
		request.setAttribute("faultSheetList", faultSheetList);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("bsfrList", searchResult.getResult());
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("list");
	}

	// 跳转到故障工单编辑
	public ActionForward goToEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");

		FaultSheetManagementService fs = (FaultSheetManagementService) getBean("FaultSheetManagementService");
		FaultSheetManagement frs = fs.find(id);
		request.setAttribute("faultSheet", frs);
		return mapping.findForward("goToEdit");
	}

	// 编辑故障工单
	public ActionForward edit1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FaultSheetManagementForm fault = (FaultSheetManagementForm) form;
		FaultSheetManagement fsm = (FaultSheetManagement) convert(fault);
		// String id=request.getParameter("id");
		FaultSheetManagementService fs = (FaultSheetManagementService) getBean("FaultSheetManagementService");
		// fsm=fs.find(id);
		// fsm.setFaultState("派发");
		fs.save(fsm);
		return this.list(mapping, form, request, response);
	}

	// 删除信息
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		FaultSheetManagementForm fault = (FaultSheetManagementForm) form;
		FaultSheetManagement fsm = (FaultSheetManagement) convert(fault);
		FaultSheetManagementService fs = (FaultSheetManagementService) getBean("FaultSheetManagementService");
		fsm = fs.find(id);
		fsm.setIdDeleted("1");
		boolean right = fs.save(fsm);
		if (right == false) {
			return mapping.findForward("faill");
		}
		return this.list(mapping, form, request, response);
	}

	// 故障回复工单查看
	public ActionForward listResponse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Search search = new Search();
		search.addFilterEmpty("dealstata");
		String work_OrderNo = request.getParameter("work_OrderNo");

		String operateTime = request.getParameter("operateTime");
		if (null != work_OrderNo || null != operateTime) {
			search.addFilterILike("work_OrderNo", "%" + work_OrderNo + "%")
					.addFilterILike("operateTime", "%" + operateTime + "%");
		}
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
		String dept = sessionform.getDeptid();
		search.addFilterIn("detailment_Object", userId,dept);
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(),
				search);
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"faultSheetResponse")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.addSort("operateTime", true);
		FaultSheetResponseService fs = (FaultSheetResponseService) getBean("FaultSheetResponseService");
		SearchResult<FaultSheetResponse> searchResult = fs
				.searchAndCount(search);
		List<FaultSheetResponse> faultSheetResponse = searchResult.getResult();
		
		FaultSheetManagementService fsss = (FaultSheetManagementService) getBean("FaultSheetManagementService");
		for (FaultSheetResponse SheetResponse : faultSheetResponse) {
			String work_OrderNo1=SheetResponse.getWork_OrderNo();
			Search search11=new Search();
		search11.addFilterEqual("work_OrderNo", work_OrderNo1);  
			List<FaultSheetManagement> faultSheetManagement =fsss.search(search11);
			for(FaultSheetManagement fsmg:faultSheetManagement){
				String processLimited= fsmg.getProcessLimited();	
			String faultDealEndTime=SheetResponse.getFaultDealEndTime();
			String detailment_Object = SheetResponse.getDetailment_Object();
			if (faultDealEndTime != null && processLimited != null) {
			if (CommonUtils.toEomsStandardDate(faultDealEndTime).after(
				CommonUtils.toEomsStandardDate(
						processLimited))) {	
				fsmg.setAreInTimeComplete("0");
				// 0为延时接收
		} else {
				
			fsmg.setAreInTimeComplete("1");// 1为按时接收
			}
			}
			if (detailment_Object.equals(userId)
					|| detailment_Object.equals(dept)) {
				request.setAttribute("faultSheetResponse", faultSheetResponse);
				request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
				request.setAttribute("bsfrList", searchResult.getResult());
				request.setAttribute("size", searchResult.getTotalCount());
			}
			}
		}
		return mapping.findForward("resultlist");
	}

	// 派发给的对象接收查询
	public ActionForward listThem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(),
				search);
		String work_OrderNo = request.getParameter("work_OrderNo");
		String themess = request.getParameter("themess");
		TawSystemSessionForm sessionform = this.getUser(request);
		String deptid = sessionform.getDeptid();
		String userid = sessionform.getUserid();

		if (null != work_OrderNo || null != themess || "" != work_OrderNo
				|| "" != themess) {
			search.addFilterILike("work_OrderNo", "%" + work_OrderNo + "%")
					.addFilterILike("themess", "%" + themess + "%");
		}
		// here must be carefull
		search.addFilterEqual("faultState", "派发");

		search.addFilterIn("detailment_Object", deptid, userid);
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"faultSheetListResponse")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		FaultSheetManagementService fs = (FaultSheetManagementService) getBean("FaultSheetManagementService");
		SearchResult<FaultSheetManagement> searchResult = fs
				.searchAndCount(search);
		List<FaultSheetManagement> faultSheetList = searchResult.getResult();

		request.setAttribute("faultSheetListResponse", faultSheetList);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("bsfrList", searchResult.getResult());
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("responseAddFormf");
	}

	// 接收派发的工单的人查看处理的工单
	public ActionForward listHistory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(),
				search);
		
		search.addSortDesc("operateTime");
		String work_OrderNo = request.getParameter("work_OrderNo");
		String themess = request.getParameter("themess");
		TawSystemSessionForm sessionform = this.getUser(request);
		String deptid = sessionform.getDeptid();
		String userid = sessionform.getUserid();

		if (null != work_OrderNo || null != themess) {
			search.addFilterILike("work_OrderNo", "%" + work_OrderNo + "%")
					.addFilterILike("themess", "%" + themess + "%");
		}
		// here must be carefull
		// search.addFilterEqual("faultState", "派发");
		search.addFilterIn("faultState", "处理中", "归档");
		search.addFilterIn("detailment_Object", deptid, userid);
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"faultSheetListResponse")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		FaultSheetManagementService fs = (FaultSheetManagementService) getBean("FaultSheetManagementService");
		SearchResult<FaultSheetManagement> searchResult = fs
				.searchAndCount(search);
		List<FaultSheetManagement> faultSheetList = searchResult.getResult();

		request.setAttribute("faultSheetListResponse", faultSheetList);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("bsfrList", searchResult.getResult());
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("responsehistory");
	}
}
