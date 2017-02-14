package com.boco.eoms.deviceManagement.busi.protectline.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.baseInfo.model.CheckSegment;

//import com.boco.eoms.deviceManagement.busi.protectline.model.ArmyPoliceCivilian;
import com.boco.eoms.deviceManagement.busi.protectline.model.ArmyPoliceCivilian;
import com.boco.eoms.deviceManagement.busi.protectline.model.MechanicalArmManagement;
import com.boco.eoms.deviceManagement.busi.protectline.service.ArmyPoliceCivilianService;
import com.boco.eoms.deviceManagement.busi.protectline.service.MechanicalArmManagementService;
import com.boco.eoms.deviceManagement.busi.protectline.util.TableHelper;
import com.boco.eoms.deviceManagement.faultInfo.utils.CommonConstants;
import com.boco.eoms.deviceManagement.faultInfo.utils.CommonUtils;
import com.boco.eoms.partner.baseinfo.model.TdObjModel;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public class ArmyPoliceCivilianAction extends BaseAction {

	public ActionForward gotoAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("goToAdd");
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ArmyPoliceCivilianService armyPoliceCivilianService = (ArmyPoliceCivilianService) getBean("armyPoliceCivilianService");
		ArmyPoliceCivilian armyPoliceCivilian = new ArmyPoliceCivilian();
		armyPoliceCivilian.setApprovingPerson(request
				.getParameter("approvingPerson"));
		armyPoliceCivilian.setApprovingresult("待审核");
		armyPoliceCivilian.setState("待审核");
		armyPoliceCivilian.setCooperativeContent(request
				.getParameter("cooperativeContent"));
		armyPoliceCivilian.setProjectName(request.getParameter("projectName"));

		armyPoliceCivilian.setLengthOfOpticalCableRoutes(request
				.getParameter("lengthOfOpticalCableRoutes"));
		armyPoliceCivilian.setNameOfOrganization(request
				.getParameter("nameOfOrganization"));
		armyPoliceCivilian.setRealEffect(request.getParameter("realEffect"));
		armyPoliceCivilian.setRemarks(request.getParameter("remarks"));
		TawSystemSessionForm sessionform = this.getUser(request);
		String writePerson = sessionform.getUserid();
		String writeTime = CommonUtils.toEomsStandardDate(new Date());
		String belongTheLocal = sessionform.getAreaId();
	//	String nimei=sessionform.getAreaName();
		armyPoliceCivilian.setWritePerson(writePerson);
		armyPoliceCivilian.setWriteTime(writeTime);
		armyPoliceCivilian.setBelongTheLocal(belongTheLocal);
	//	String mmgg=armyPoliceCivilian.getBelongTheLocal();
		Boolean aaa = armyPoliceCivilianService.add(armyPoliceCivilian);
		if (aaa != true) {
			return mapping.findForward("failure");
		}
		return mapping.findForward("success");
	}

	// 条件查询
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectName = request.getParameter("projectName");
		String nameOfOrganization = request.getParameter("nameOfOrganization");
		String state = request.getParameter("bystate");

		ArmyPoliceCivilianService armyPoliceCivilianService = (ArmyPoliceCivilianService) getBean("armyPoliceCivilianService");
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"armyPoliceCivilian")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		// SearchResult<ArmyPoliceCivilian> searchResult1 =
		// armyPoliceCivilianService
		// .listAll(firstResult * CommonConstants.PAGE_SIZE,
		// CommonConstants.PAGE_SIZE);
		SearchResult<ArmyPoliceCivilian> searchresult = armyPoliceCivilianService
				.findByCondition(projectName, nameOfOrganization, state,
						firstResult, CommonConstants.PAGE_SIZE);
		List<ArmyPoliceCivilian> armyPoliceCivilian = searchresult.getResult();
		request.setAttribute("armyPoliceCivilian", armyPoliceCivilian);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", searchresult.getTotalCount());

		return mapping.findForward("gotoList");
	}

	public ActionForward listArchived(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectName = request.getParameter("projectName");
		String nameOfOrganization = request.getParameter("nameOfOrganization");
		ArmyPoliceCivilianService armyPoliceCivilianService = (ArmyPoliceCivilianService) getBean("armyPoliceCivilianService");
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"armyPoliceCivilian")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		// SearchResult<ArmyPoliceCivilian> searchResult1 =
		// armyPoliceCivilianService
		// .listAll(firstResult * CommonConstants.PAGE_SIZE,
		// CommonConstants.PAGE_SIZE);
		SearchResult<ArmyPoliceCivilian> searchResult = armyPoliceCivilianService
				.findArchied(projectName, nameOfOrganization, firstResult
						* CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE);
		List<ArmyPoliceCivilian> armyPoliceCivilian = searchResult.getResult();
		request.setAttribute("armyPoliceCivilian", armyPoliceCivilian);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("gotoList");
	}

	public ActionForward gotoList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectName = request.getParameter("projectName");
		String nameOfOrganization = request.getParameter("nameOfOrganization");
		String state = request.getParameter("state");
		String approvingPerson = request.getParameter("approvingPerson");
		ArmyPoliceCivilianService armyPoliceCivilianService = (ArmyPoliceCivilianService) getBean("armyPoliceCivilianService");
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"armyPoliceCivilian")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		TawSystemSessionForm sessionform = this.getUser(request);
		String userid = sessionform.getUserid();
		
		SearchResult<ArmyPoliceCivilian> searchresult = armyPoliceCivilianService
				.find(state,approvingPerson,projectName, nameOfOrganization,userid, firstResult
						* CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE);
		if(0!=searchresult.getTotalCount()){
			List<ArmyPoliceCivilian> armyPoliceCivilian = searchresult.getResult();
			request.setAttribute("armyPoliceCivilian", armyPoliceCivilian);
			request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
			request.setAttribute("size", searchresult.getTotalCount());
		}else{
			List<ArmyPoliceCivilian> armyPoliceCivilian = searchresult.getResult();
			request.setAttribute("armyPoliceCivilian", armyPoliceCivilian);
			request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
			request.setAttribute("size", searchresult.getTotalCount());
		}
//		List<ArmyPoliceCivilian> armyPoliceCivilian = searchresult.getResult();
//		
//		for (ArmyPoliceCivilian apc : armyPoliceCivilian) {
//			if (apc.getWritePerson().equals(userid)) {
//				request.setAttribute("armyPoliceCivilian", armyPoliceCivilian);
//			} else {
//				Search search = new Search();
//				search.addFilterIn("state", "归档", "待审栄1�7");
//				SearchResult<ArmyPoliceCivilian> searchResult1 = armyPoliceCivilianService
//						.find(projectName, nameOfOrganization,userid, firstResult
//								* CommonConstants.PAGE_SIZE,
//								CommonConstants.PAGE_SIZE);
//				List<ArmyPoliceCivilian> armyPoliceCivilian1 = searchResult1
//						.getResult();
//				request.setAttribute("armyPoliceCivilian", armyPoliceCivilian1);
//			}
//		}
		// request.setAttribute("armyPoliceCivilian", armyPoliceCivilian);
//		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
//		request.setAttribute("size", searchresult.getTotalCount());

		return mapping.findForward("gotoList");
	}

	public ActionForward gotoDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		ArmyPoliceCivilianService armyPoliceCivilianService = (ArmyPoliceCivilianService) getBean("armyPoliceCivilianService");
		ArmyPoliceCivilian armyPoliceCivilian = armyPoliceCivilianService
				.findDetail(id);
		request.setAttribute("armyPoliceCivilian", armyPoliceCivilian);

		return mapping.findForward("goToDetail");
	}

	// 审核
	public ActionForward approveList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
		String projectName = request.getParameter("projectName");
		String nameOfOrganization = request.getParameter("nameOfOrganization");

		ArmyPoliceCivilianService armyPoliceCivilianService = (ArmyPoliceCivilianService) getBean("armyPoliceCivilianService");
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"armyPoliceCivilian")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		SearchResult<ArmyPoliceCivilian> searchresult = armyPoliceCivilianService
				.approvingfind(projectName, nameOfOrganization,userId, firstResult
						* CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE);
		List<ArmyPoliceCivilian> armyPoliceCivilian = searchresult.getResult();
//		List<ArmyPoliceCivilian> armyPoliceCivilian = new ArrayList<ArmyPoliceCivilian>();
//		for (ArmyPoliceCivilian armypc : armyPoliceCivilian1) {
//			if (armypc.getApprovingPerson().equals(userId)) {
//				armyPoliceCivilian.add(armypc);
//			}
//		}
		request.setAttribute("armyPoliceCivilian", armyPoliceCivilian);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", searchresult.getTotalCount());
		// return mapping.findForward("gotoList");

		return mapping.findForward("gotoApprovingList");
	}

	public ActionForward gotoApprovedDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		ArmyPoliceCivilianService armyPoliceCivilianService = (ArmyPoliceCivilianService) getBean("armyPoliceCivilianService");
		ArmyPoliceCivilian armyPoliceCivilian = armyPoliceCivilianService
				.findDetail(id);
		request.setAttribute("armyPoliceCivilian", armyPoliceCivilian);
		String type = request.getParameter("type");
		request.setAttribute("type", type);
		return mapping.findForward("goToApprovedDetail");
	}

	// 审核人添加审核信恄1�7
	public ActionForward approveing(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		ArmyPoliceCivilianService armyPoliceCivilianService = (ArmyPoliceCivilianService) getBean("armyPoliceCivilianService");
		ArmyPoliceCivilian armyPoliceCivilian = armyPoliceCivilianService
				.findDetail(id);
		TawSystemSessionForm sessionform = this.getUser(request);
		String approveperson = sessionform.getUserid();
		armyPoliceCivilian.setApprovingPerson(approveperson);
		String approvingresult = request.getParameter("approvingresult");
		if (approvingresult.equals("1")) {
			armyPoliceCivilian.setApprovingresult("通过审核");
			armyPoliceCivilian.setState("归档");

		} else {
			armyPoliceCivilian.setApprovingresult("驳回");
			armyPoliceCivilian.setState("驳回");
		}

		armyPoliceCivilian.setSuggest(request.getParameter("suggest"));
		armyPoliceCivilian.setApprovingtime(CommonUtils
				.toEomsStandardDate(new Date()));
		Boolean aaa = armyPoliceCivilianService.add(armyPoliceCivilian);
		if (aaa == true) {
			return mapping.findForward("failure");
		}
		return mapping.findForward("success");

	}

	// 获取审核结果
	public ActionForward approvedResultList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String projectName = request.getParameter("projectName");
		String nameOfOrganization = request.getParameter("nameOfOrganization");
		ArmyPoliceCivilianService armyPoliceCivilianService = (ArmyPoliceCivilianService) getBean("armyPoliceCivilianService");
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"armyPoliceCivilian")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		SearchResult<ArmyPoliceCivilian> searchResult1 = armyPoliceCivilianService
				.approvedfind(projectName, nameOfOrganization, firstResult
						* CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE);
		List<ArmyPoliceCivilian> armyPoliceCivilian1 = searchResult1
				.getResult();
		TawSystemSessionForm sessionForm = this.getUser(request);
		String writepersona = sessionForm.getUserid();
		List<ArmyPoliceCivilian> armyPoliceCivilian = new ArrayList<ArmyPoliceCivilian>();
		for (ArmyPoliceCivilian armyPolice : armyPoliceCivilian1) {
			if (armyPolice.getWritePerson().equals(writepersona)) {
				armyPoliceCivilian.add(armyPolice);
			}
		}
		request.setAttribute("armyPoliceCivilian", armyPoliceCivilian);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", searchResult1.getTotalCount());
		return mapping.findForward("gotoList");

	}

	public ActionForward gotoEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		ArmyPoliceCivilianService armyPoliceCivilianService = (ArmyPoliceCivilianService) getBean("armyPoliceCivilianService");
		ArmyPoliceCivilian armyPoliceCivilian = armyPoliceCivilianService
				.findDetail(id);
		request.setAttribute("armyPoliceCivilian", armyPoliceCivilian);
		return mapping.findForward("goToEdit");
	}

	// 重新编辑
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		ArmyPoliceCivilianService armyPoliceCivilianService = (ArmyPoliceCivilianService) getBean("armyPoliceCivilianService");
		ArmyPoliceCivilian armyPoliceCivilian = armyPoliceCivilianService
				.findDetail(id);
		armyPoliceCivilian.setApprovingresult("待审栄1�7");
		armyPoliceCivilian.setState("待审栄1�7");
		armyPoliceCivilian.setCooperativeContent(request
				.getParameter("cooperativeContent"));
		armyPoliceCivilian.setProjectName(request.getParameter("projectName"));

		armyPoliceCivilian.setLengthOfOpticalCableRoutes(request
				.getParameter("lengthOfOpticalCableRoutes"));
		armyPoliceCivilian.setNameOfOrganization(request
				.getParameter("nameOfOrganization"));
		armyPoliceCivilian.setRealEffect(request.getParameter("realEffect"));
		armyPoliceCivilian.setRemarks(request.getParameter("remarks"));
		boolean wawa = armyPoliceCivilianService.add(armyPoliceCivilian);
		if (wawa == true) {
			return mapping.findForward("failure");
		}
		return mapping.findForward("success");

	}
	
//查询已归档文件的查看
	public ActionForward approvedSuccessedList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("zheshiege gantamen de a ''''''''''''''''''''''''''");
		String projectName = request.getParameter("projectName");
		String nameOfOrganization = request.getParameter("nameOfOrganization");
		String approvingPerson=request.getParameter("approvingPerson");
		ArmyPoliceCivilianService armyPoliceCivilianService = (ArmyPoliceCivilianService) getBean("armyPoliceCivilianService");
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"armyPoliceCivilian")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		SearchResult<ArmyPoliceCivilian> searchResult1 = armyPoliceCivilianService
				.approvedFindSuccessed(projectName, nameOfOrganization,approvingPerson,
						firstResult * CommonConstants.PAGE_SIZE,
						CommonConstants.PAGE_SIZE);
		TawSystemSessionForm sessionForm = this.getUser(request);
		//String writepersona = sessionForm.getUserid();
		List<ArmyPoliceCivilian> armyPoliceCivilian1 = searchResult1
				.getResult();
		
//		List<ArmyPoliceCivilian> armyPoliceCivilian = new ArrayList<ArmyPoliceCivilian>();
	//for (ArmyPoliceCivilian armyPolice : armyPoliceCivilian1) {
		//armyPolice.setRemark1("55");
			//}
//		}
		request.setAttribute("armyPoliceCivilian", armyPoliceCivilian1);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", searchResult1.getTotalCount());
		return mapping.findForward("gotoList1");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");

		ArmyPoliceCivilianService armyPoliceCivilianService = (ArmyPoliceCivilianService) getBean("armyPoliceCivilianService");

		boolean mm = armyPoliceCivilianService.deleteWa(id);
		if (!mm) {
			return mapping.findForward("faill");
		}

		return mapping.findForward("success");
	}

	// 查询已归档的信息
	public ActionForward archived(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		ArmyPoliceCivilianService armyPoliceCivilianService = (ArmyPoliceCivilianService) getBean("armyPoliceCivilianService");

		ArmyPoliceCivilian mm = armyPoliceCivilianService.findDetail(id);

		mm.setState("归档");
		boolean wawa = armyPoliceCivilianService.add(mm);
		if (wawa) {
			return mapping.findForward("failure");
		}

		return mapping.findForward("success");
	}
	
	// 批量删除
	public ActionForward deleteSome(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ArmyPoliceCivilianService armyPoliceCivilianService = (ArmyPoliceCivilianService) getBean("armyPoliceCivilianService");

		String ids = request.getParameter("ids");
		String[] id = ids.substring(0, ids.lastIndexOf(";")).split(";");
		armyPoliceCivilianService.deltetAll(id);
		CommonUtils.printJsonSuccessMsg(response);
		return mapping.findForward("success");
	}

	// 跳转到统计页靄1�7
	public ActionForward gotoStatistics(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return mapping.findForward("gotoStatistics");
	}

	// 军警民共建管理统讄1�7
	public ActionForward statisticsArmyPoliceCivilianManagementList(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String search = "";
		String group = "";

		String[] rows = StaticMethod.nullObject2String(
				request.getParameter("deleteIds"), "").split(";");
		String checkString = StaticMethod.nullObject2String(request
				.getParameter("checks"), "");
		for (int i = 0; i < rows.length; i++) {
			String row = "";
			if (i == rows.length - 1) {
				search += rows[i];
				group += rows[i];
			} else {
				search += rows[i] + ",";
				group += rows[i] + ",";
			}
		}

		String wherecondition = "";
		// 扄1�7属地帄1�7
		String belongTheLocal = request.getParameter("belongTheLocal");
		// 军警民共建单位名秄1�7

		String nameOfOrganization = request.getParameter("nameOfOrganization");

		String projectName = request.getParameter("projectName");

		if (null != belongTheLocal && !"".equals(belongTheLocal)) {
			wherecondition += "and" + " " + "belongTheLocal" + " " + "like"
					+ "'" + "%" + belongTheLocal + "%" + "'";
		}
		if (null != nameOfOrganization && !"".equals(nameOfOrganization)) {
			wherecondition += " " + "and" + " " + "nameOfOrganization" + " "
					+ "like" + "'" + "%" + nameOfOrganization + "%" + "'";
		}
		if (null != projectName && !"".equals(projectName)) {
			wherecondition += " " + "and" + " " + "projectName" + " " + "like"
					+ "'" + "%" + projectName + "%" + "'";
		}
		String sql = "select"
				+ " "
				+ search
				+ ",count(id) from dm_armypolicecivilian  where 1=1 and state='归档'"
				+ " " + wherecondition + " group by " + group + " order by  "
				+ group;
		List<String> headList = new ArrayList<String>();
		for (int i = 0; i < rows.length; i++) {
			if ("belongTheLocal".equals(rows[i])) {
				headList.add("所属地市");
			}
			if ("nameOfOrganization".equals(rows[i])) {
				headList.add("军警民共建单位名称");
			}
			if ("projectName".equals(rows[i])) {
				headList.add("项目名称");
			}
			if ("approvingPerson".equals(rows[i])) {
				headList.add("审核人");
			}
		}
		headList.add("总数");
		List<List<TdObjModel>> tempList = TableHelper
				.verticalGrowp(rows, sql,
						"/armypolicecivilian/armypolicecivilian.do?method=statisticList");
		request.setAttribute("tableList", tempList);
		request.setAttribute("headList", headList);
		request.setAttribute("checkList", checkString);
		return mapping
				.findForward("statisticsArmyPoliceCivilianManagementList");
	}

	// 跳转到统计出来的页面
	public ActionForward statisticList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectName = request.getParameter("projectname");
		String nameOfOrganization = request.getParameter("nameOforganization");
		String belongTheLocal = request.getParameter("belongthelocal");
		ArmyPoliceCivilianService armyPoliceCivilianService = (ArmyPoliceCivilianService) getBean("armyPoliceCivilianService");
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"armyPoliceCivilian")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		SearchResult<ArmyPoliceCivilian> searchResult1 = armyPoliceCivilianService
				.staticsFindwa(projectName, nameOfOrganization, belongTheLocal,
						firstResult * CommonConstants.PAGE_SIZE,
						CommonConstants.PAGE_SIZE);
		List<ArmyPoliceCivilian> armyPoliceCivilian = searchResult1.getResult();
		request.setAttribute("armyPoliceCivilian", armyPoliceCivilian);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", searchResult1.getTotalCount());
		return mapping.findForward("goTostaticsList");
	}

	public ActionForward gotostaticsDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		ArmyPoliceCivilianService armyPoliceCivilianService = (ArmyPoliceCivilianService) getBean("armyPoliceCivilianService");
		ArmyPoliceCivilian armyPoliceCivilian = armyPoliceCivilianService
				.findDetail(id);
		request.setAttribute("armyPoliceCivilian", armyPoliceCivilian);

		return mapping.findForward("goTostaticsedDetail");
	}

	// 统计的条件查评1�7
	public ActionForward liststaticsss(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectName = request.getParameter("projectName");
		String nameOfOrganization = request.getParameter("nameOfOrganization");
		String state = request.getParameter("bystate");
		ArmyPoliceCivilianService armyPoliceCivilianService = (ArmyPoliceCivilianService) getBean("armyPoliceCivilianService");
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"armyPoliceCivilian")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		// SearchResult<ArmyPoliceCivilian> searchResult1 =
		// armyPoliceCivilianService
		// .listAll(firstResult * CommonConstants.PAGE_SIZE,
		// CommonConstants.PAGE_SIZE);
		SearchResult<ArmyPoliceCivilian> searchresult = armyPoliceCivilianService
				.findByCondition(projectName, nameOfOrganization, state,
						firstResult, CommonConstants.PAGE_SIZE);
		List<ArmyPoliceCivilian> armyPoliceCivilian = searchresult.getResult();
		request.setAttribute("armyPoliceCivilian", armyPoliceCivilian);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", searchresult.getTotalCount());

		return mapping.findForward("liststaticsss");
	}

}
