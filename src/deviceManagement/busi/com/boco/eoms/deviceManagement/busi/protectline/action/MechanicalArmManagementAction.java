package com.boco.eoms.deviceManagement.busi.protectline.action;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.busi.protectline.form.MechaicalArmManagementForm;
import com.boco.eoms.deviceManagement.busi.protectline.model.MechanicalArmManagement;
import com.boco.eoms.deviceManagement.busi.protectline.service.MechanicalArmManagementService;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcService;
import com.boco.eoms.deviceManagement.faultInfo.utils.CommonConstants;
import com.boco.eoms.deviceManagement.faultInfo.utils.CommonUtils;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public class MechanicalArmManagementAction extends BaseAction {
	public ActionForward gotoAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("gotoAdd");
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		TawSystemSessionForm sessionform = this.getUser(request);
		String writePerson = sessionform.getUserid();
		String writeTime = CommonUtils.toEomsStandardDate(new Date());
		String belongTheLocal = sessionform.getAreaId();
		
		MechanicalArmManagementService fs = (MechanicalArmManagementService) getBean("MechanicalArmManagementService");
		MechaicalArmManagementForm mechanicalArmManagementform = (MechaicalArmManagementForm) form;
		MechanicalArmManagement mechanicalArmManagement = new MechanicalArmManagement();
		BeanUtils.copyProperties(mechanicalArmManagement,
				mechanicalArmManagementform);
		mechanicalArmManagement.setWritePerson(writePerson);
		mechanicalArmManagement.setWriteTime(writeTime);
		mechanicalArmManagement.setBelongTheLocal(belongTheLocal);
		mechanicalArmManagement.setState("待审核");
		mechanicalArmManagement.setApprovingresult("待审核");
		boolean ok = fs.save(mechanicalArmManagement);
		if (ok == true) {
			
		} else {
			return mapping.findForward("faill");
		}

		return mapping.findForward("success");
	}

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(),
				search);
		String projectName = request.getParameter("projectName");
		String constructionMachineryName = request
				.getParameter("constructionMachineryName");
		if (null != projectName && !"".equals(projectName)) {
			search.addFilterLike("projectName", "%" + projectName + "%");
		}
		if (null != constructionMachineryName
				&& !"".equals(constructionMachineryName)) {
			search.addFilterLike("constructionMachineryName", "%"
					+ constructionMachineryName + "%");
		}
		String state = request.getParameter("state");
		if (null != state
				&& !"".equals(state)) {
			if("1".equals(state)){
				search.addFilterEqual("state", 
						 "待审核" );
			}else if("2".equals(state)){
				search.addFilterEqual("state", 
				 "驳回" );
			}else if("3".equals(state)){
				search.addFilterEqual("state", 
				 "归档" );
			}
			
		}
		String approvingPerson = request.getParameter("approvingPerson");
		if (null != approvingPerson
				&& !"".equals(approvingPerson)) {
			search.addFilterLike("approvingPerson", "%"
					+ approvingPerson + "%");
		}
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"MechanicalArmList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.addSort("writeTime", true);
		MechanicalArmManagementService fs = (MechanicalArmManagementService) getBean("MechanicalArmManagementService");
		TawSystemSessionForm sessionform = this.getUser(request);
		String userid = sessionform.getUserid();
		search.addFilterEqual("writePerson", userid);
		SearchResult<MechanicalArmManagement> searchResult = fs
				.searchAndCount(search);
		if(0!=searchResult.getTotalCount()){
			
		List<MechanicalArmManagement> mechanicalArmList1 = searchResult
				.getResult();
		
				request.setAttribute("MechanicalArmList", mechanicalArmList1);
				request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
				request.setAttribute("size", searchResult.getTotalCount());
		}
				
		return mapping.findForward("gotoList");
	}

	// 查询归档的信息
	public ActionForward listAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(),
				search);
		String projectName = request.getParameter("projectName");
		String constructionMachineryName = request
				.getParameter("constructionMachineryName");
		String approvingPerson=request.getParameter("approvingPerson");
		if(null != approvingPerson && !"".equals(approvingPerson)){
			search.addFilterLike("approvingPerson", "%" + approvingPerson + "%");
		}
		if (null != projectName && !"".equals(projectName)) {
			search.addFilterLike("projectName", "%" + projectName + "%");
		}
		if (null != constructionMachineryName
				&& !"".equals(constructionMachineryName)) {
			search.addFilterLike("constructionMachineryName", "%"
					+ constructionMachineryName + "%");
		}
		search.addFilterEqual("state", "归档");
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"MechanicalArmList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.addSort("writeTime", true);
		MechanicalArmManagementService fs = (MechanicalArmManagementService) getBean("MechanicalArmManagementService");
		SearchResult<MechanicalArmManagement> searchResult = fs
				.searchAndCount(search);
		List<MechanicalArmManagement> mechanicalArmList = searchResult
				.getResult();
		request.setAttribute("MechanicalArmList", mechanicalArmList);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("bsfrList", searchResult.getResult());
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("gotoList1");
	}

	// 审核
	public ActionForward approveList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(),
				search);
		String projectName = request.getParameter("projectName");
		String constructionMachineryName = request
				.getParameter("constructionMachineryName");
		if (null != projectName && !"".equals(projectName)) {
			search.addFilterLike("projectName", "%" + projectName + "%");
		}
		if (null != constructionMachineryName
				&& !"".equals(constructionMachineryName)) {
			search.addFilterLike("constructionMachineryName", "%"
					+ constructionMachineryName + "%");
		}
		search.addFilterEqual("state", "待审核");
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
//		String approvingPersondept = sessionform.getDeptid();
//		search.addFilterIn("approvingPerson", userId, approvingPersondept);
		search.addFilterEqual("approvingPerson", userId);
		// search.addFilterIn("state", "待审核");
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"MechanicalArmList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.addSort("writeTime", true);
		MechanicalArmManagementService fs = (MechanicalArmManagementService) getBean("MechanicalArmManagementService");
		SearchResult<MechanicalArmManagement> searchResult = fs
				.searchAndCount(search);
		List<MechanicalArmManagement> mechanicalArmList = searchResult
				.getResult();
//		for (MechanicalArmManagement mechan : mechanicalArmList) {
//			if (mechan.getApprovingPerson().matches("[0-9]+")) {
				request.setAttribute("MechanicalArmList", mechanicalArmList);
				request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
				request.setAttribute("bsfrList", searchResult.getResult());
				request.setAttribute("size", searchResult.getTotalCount());
//			} else if (mechan.getApprovingPerson().equals(userId)) {
//				request.setAttribute("MechanicalArmList", mechanicalArmList);
//				request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
//				request.setAttribute("bsfrList", searchResult.getResult());
//				request.setAttribute("size", searchResult.getTotalCount());
//			}
//		}
		// request.setAttribute("MechanicalArmList", mechanicalArmList);
		// request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		// request.setAttribute("bsfrList", searchResult.getResult());
		// request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("gotoApprovingList");
	}

	public ActionForward gotoEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		MechanicalArmManagementService fs = (MechanicalArmManagementService) getBean("MechanicalArmManagementService");
		MechanicalArmManagement frs = fs.find(id);
		request.setAttribute("MechanicalArmList", frs);
		return mapping.findForward("goToEdit");
	}

	public ActionForward approvingAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		MechanicalArmManagementService fs = (MechanicalArmManagementService) getBean("MechanicalArmManagementService");
		MechanicalArmManagement mm = fs.find(id);
		String suggest = request.getParameter("suggest");
		String approvingresult = request.getParameter("approvingresult");
		String approvingtime = CommonUtils.toEomsStandardDate(new Date());
		mm.setApprovingtime(approvingtime);
		mm.setSuggest(suggest);
		if (approvingresult.equals("1")) {
			mm.setState("归档");
			mm.setApprovingresult("通过审核");
		} else {
			mm.setState("驳回");
			mm.setApprovingresult("驳回");
		}

		boolean gg = fs.save(mm);
		if (!gg) {
			System.out.println("ok");
		} else {
			return mapping.findForward("faill");
		}
		return mapping.findForward("success");
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");

		TawSystemSessionForm sessionform = this.getUser(request);
		String writePerson = sessionform.getUserid();
		String writeTime = CommonUtils.toEomsStandardDate(new Date());
		String belongTheLocal = sessionform.getAreaId();
		MechanicalArmManagementService fs = (MechanicalArmManagementService) getBean("MechanicalArmManagementService");
		MechanicalArmManagement mm = fs.find(id);
		MechaicalArmManagementForm mechanicalArmManagementform = (MechaicalArmManagementForm) form;
		BeanUtils.copyProperties(mm, mechanicalArmManagementform);
		mm.setWritePerson(writePerson);
		mm.setWriteTime(writeTime);
		mm.setBelongTheLocal(belongTheLocal);
		mm.setState("待审核");
		boolean gg = fs.save(mm);
		if (!gg) {
			System.out.println("ok");
		} else {
			return mapping.findForward("faill");
		}

		return mapping.findForward("success");
	}

	public ActionForward gotoArchive(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		MechanicalArmManagementService fs = (MechanicalArmManagementService) getBean("MechanicalArmManagementService");
		boolean mm = fs.removeById(id);
		if (!mm) {
			return mapping.findForward("faill");
		}

		return mapping.findForward("success");
	}

	public ActionForward gotoDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		TawSystemSessionForm sessionform = this.getUser(request);
		String writePerson = sessionform.getUserid();
		MechanicalArmManagementService fs = (MechanicalArmManagementService) getBean("MechanicalArmManagementService");
		MechanicalArmManagement frs = fs.find(id);
		MechanicalArmManagement frss = new MechanicalArmManagement();
		frss.setWritePerson(writePerson);
		request.setAttribute("MechanicalArmList", frs);
		request.setAttribute("loginperson", frss);
		return mapping.findForward("goToDetail");
	}

	public ActionForward gotoApprovedDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");

		MechanicalArmManagementService fs = (MechanicalArmManagementService) getBean("MechanicalArmManagementService");
		MechanicalArmManagement frs = fs.find(id);
		request.setAttribute("MechanicalArmList", frs);
		return mapping.findForward("goToDetailApp");
	}

	public ActionForward gotoArchivedDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");

		MechanicalArmManagementService fs = (MechanicalArmManagementService) getBean("MechanicalArmManagementService");
		MechanicalArmManagement frs = fs.find(id);
		request.setAttribute("MechanicalArmList", frs);
		return mapping.findForward("goToDetailArch");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");

		MechanicalArmManagementService fs = (MechanicalArmManagementService) getBean("MechanicalArmManagementService");
		boolean mm = fs.removeById(id);
		if (!mm) {
			return mapping.findForward("faill");
		}

		return mapping.findForward("success");
	}

	public ActionForward approvedDeal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(),
				search);
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
		// String operatePerson_Department1 = sessionform.getDeptid();
		search.addFilterEqual("writePerson", userId);
		String projectName = request.getParameter("projectName");
		String constructionMachineryName = request
				.getParameter("constructionMachineryName");
		if (null != projectName && !"".equals(projectName)) {
			search.addFilterLike("projectName", "%" + projectName + "%");
		}
		if (null != constructionMachineryName
				&& !"".equals(constructionMachineryName)) {
			search.addFilterLike("constructionMachineryName", "%"
					+ constructionMachineryName + "%");
		}
		search.addFilterEqual("approvingresult", "驳回");
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"MechanicalArmList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.addSort("writeTime", true);
		MechanicalArmManagementService fs = (MechanicalArmManagementService) getBean("MechanicalArmManagementService");
		SearchResult<MechanicalArmManagement> searchResult = fs
				.searchAndCount(search);
		List<MechanicalArmManagement> mechanicalArmList = searchResult
				.getResult();
		request.setAttribute("MechanicalArmList", mechanicalArmList);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("bsfrList", searchResult.getResult());
		request.setAttribute("size", searchResult.getTotalCount());
		// return mapping.findForward("gotoarchiveList");
		return mapping.findForward("gotoList");
	}

	public ActionForward archiveList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(),
				search);
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
		// String operatePerson_Department1 = sessionform.getDeptid();
		search.addFilterEqual("writePerson", userId);
		String projectName = request.getParameter("projectName");
		String constructionMachineryName = request
				.getParameter("constructionMachineryName");
		if (null != projectName && !"".equals(projectName)) {
			search.addFilterLike("projectName", "%" + projectName + "%");
		}
		if (null != constructionMachineryName
				&& !"".equals(constructionMachineryName)) {
			search.addFilterLike("constructionMachineryName", "%"
					+ constructionMachineryName + "%");
		}
		search.addFilterIn("state", "通过审核", "审核未通过");
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"MechanicalArmList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.addSort("writeTime", true);
		MechanicalArmManagementService fs = (MechanicalArmManagementService) getBean("MechanicalArmManagementService");
		SearchResult<MechanicalArmManagement> searchResult = fs
				.searchAndCount(search);
		List<MechanicalArmManagement> mechanicalArmList = searchResult
				.getResult();
		request.setAttribute("MechanicalArmList", mechanicalArmList);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("bsfrList", searchResult.getResult());
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("gotoarchiveList");
	}

	// 归档
	// public ActionForward overIt(ActionMapping mapping, ActionForm form,
	// HttpServletRequest request, HttpServletResponse response)
	// throws Exception {
	// String id = request.getParameter("id");
	//
	// MechanicalArmManagementService fs = (MechanicalArmManagementService)
	// getBean("MechanicalArmManagementService");
	// MechanicalArmManagement frs = fs.find(id);
	// frs.setState("归档");
	// boolean fuck = fs.save(frs);
	// if (fuck == true) {
	// return mapping.findForward("faill");
	// }
	// request.setAttribute("MechanicalArmList", frs);
	// return mapping.findForward("success");
	// }

	// 批量删除
	public ActionForward deleteSome(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MechanicalArmManagementService fs = (MechanicalArmManagementService) getBean("MechanicalArmManagementService");
		String ids = request.getParameter("ids");
		String[] id = ids.substring(0, ids.lastIndexOf(";")).split(";");
		fs.removeByIds(id);
		CommonUtils.printJsonSuccessMsg(response);
		return mapping.findForward("success");
	}

	public MechanicalArmManagementService getMainBean() {
		String source = MechanicalArmManagementService.class.getSimpleName();
		return (MechanicalArmManagementService) getBean(source.substring(0, 1)
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

	public ActionForward listByCondition(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(),
				search);
		String projectName = request.getParameter("projectName");
		String constructionMachineryName = request
				.getParameter("constructionMachineryName");
		String state = request.getParameter("state");
		// search.addFilterEqual("state", state);

		String approvingPerson = request.getParameter("approvingPerson");
		if (null != state && !"".equals(state)) {
			if (state.equals("1")) {
				search.addFilterEqual("state", "待审核");
			} else if (state.equals("2")) {
				search.addFilterEqual("state", "通过审核");
			} else if (state.equals("3")) {
				search.addFilterEqual("state", "审核未通过");
			} else if (state.equals("4")) {
				search.addFilterEqual("state", "归档");
			}
		}
		if (null != approvingPerson && !"".equals(approvingPerson)) {
			search
					.addFilterLike("approvingPerson", "%" + approvingPerson
							+ "%");
		}
		if (null != projectName && !"".equals(projectName)) {
			search.addFilterLike("projectName", "%" + projectName + "%");
		}
		if (null != constructionMachineryName
				&& !"".equals(constructionMachineryName)) {
			search.addFilterLike("constructionMachineryName", "%"
					+ constructionMachineryName + "%");
		}
		// search.addFilterEqual("state", "待审核");
		// search.addFilterIn("state", "待审核");
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"MechanicalArmList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.addSort("writeTime", true);
		MechanicalArmManagementService fs = (MechanicalArmManagementService) getBean("MechanicalArmManagementService");
		SearchResult<MechanicalArmManagement> searchResult = fs
				.searchAndCount(search);
		List<MechanicalArmManagement> mechanicalArmList = searchResult
				.getResult();
		request.setAttribute("MechanicalArmList", mechanicalArmList);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("bsfrList", searchResult.getResult());
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("gotoList");
	}
}
