package com.boco.eoms.deviceManagement.busi.protectline.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;

import com.boco.eoms.deviceManagement.busi.protectline.model.MechanicalArmManagement;
import com.boco.eoms.deviceManagement.busi.protectline.service.MechanicalArmManagementService;
import com.boco.eoms.deviceManagement.busi.protectline.util.TableHelper;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcService;
import com.boco.eoms.deviceManagement.faultInfo.utils.CommonConstants;
import com.boco.eoms.partner.baseinfo.model.TdObjModel;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public class StatisticsAction extends BaseAction {
	// 大型机械手统计
	public ActionForward mechanicalArmList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
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

		String belongTheLocal = request.getParameter("belongTheLocal");
		

		String constructionMachineryName = request
				.getParameter("constructionMachineryName");
		
		String projectName = request.getParameter("projectName");
		
		if (null != belongTheLocal && !"".equals(belongTheLocal)) {
			wherecondition += "and" + " " + "belongTheLocal"+" " +"like"+ "'"
					+"%"+ belongTheLocal +"%"+ "'";
		}

		if (null != constructionMachineryName
				&& !"".equals(constructionMachineryName)) {
			wherecondition += " " + "and" + " " + "constructionMachineryName" +" "+"like"
					+ "'" +"%"+ constructionMachineryName + "%"+"'";
		}
		if (null != projectName && !"".equals(projectName)) {
			wherecondition += " " + "and" + " " + "projectName" +" "+ "like"+"'"
					+"%"+ projectName + "%"+"'";
		}
		String sql = "select" + " " + search
				+ ",count(id) from dm_mechanicalArmManagement  where 1=1 and state='归档'" + " "
				+ wherecondition + " group by " + group + " order by  " + group;
//		System.out
//				.println(sql
//						+ "wawawawawaawawawawaawawawawaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		List<String> headList = new ArrayList<String>();
		for (int i = 0; i < rows.length; i++) {
			if ("mechanicalArm_workyard".equals(rows[i])) {
				headList.add("大型机械手施工地点");
			}
			if ("constructionMachineryName".equals(rows[i])) {
				headList.add("大型施工机械名字");
			}
			if ("projectName".equals(rows[i])) {
				headList.add("项目名称");
			}
			if ("belongTheLocal".equals(rows[i])) {
				headList.add("所属地市");
			}
		}
		headList.add("总数");
		List<List<TdObjModel>> tempList = TableHelper
				.verticalGrowp(rows, sql,
						"/mechanicalArm/mechanicalArmManagementStatistic.do?method=statisticList");
		request.setAttribute("tableList", tempList);
		request.setAttribute("headList", headList);
		request.setAttribute("checkList", checkString);
		return mapping.findForward("mechanicalArmManagementlist");
	}

	public ActionForward statisticList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String projectName = request.getParameter("projectname");
		String constructionMachineryName = request
				.getParameter("constructionmachineryname");
		String mechanicalArm_workyard = request
				.getParameter("mechanicalarm_workyard");
		Search search = new Search();
		if (null != projectName) {
			projectName = new String(projectName.toString().trim().getBytes(
					"ISO-8859-1"), "utf-8");
			search.addFilterEqual("projectName", projectName);
		}
		if (null != constructionMachineryName) {
			constructionMachineryName = new String(constructionMachineryName
					.toString().trim().getBytes("ISO-8859-1"), "utf-8");
			search.addFilterEqual("constructionMachineryName",
					constructionMachineryName);
		}
		if (null != mechanicalArm_workyard) {
			mechanicalArm_workyard = new String(mechanicalArm_workyard
					.toString().trim().getBytes("ISO-8859-1"), "utf-8");
			search.addFilterEqual("mechanicalArm_workyard",
					mechanicalArm_workyard);
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
		SearchResult<MechanicalArmManagement> searchResult = fs
				.searchAndCount(search);
		List<MechanicalArmManagement> mechanicalArmList = searchResult
				.getResult();
		request.setAttribute("MechanicalArmList", mechanicalArmList);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("bsfrList", searchResult.getResult());
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("statisticslist");
	}

	public ActionForward gotoStatistics(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return mapping.findForward("toStatistics");
	}
	
	public ActionForward gotoDetailAa(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	String id = request.getParameter("id");
	TawSystemSessionForm sessionform = this.getUser(request);
	String writePerson = sessionform.getUserid();
	MechanicalArmManagementService fs = (MechanicalArmManagementService) getBean("MechanicalArmManagementService");
	MechanicalArmManagement frs = fs.find(id);
	MechanicalArmManagement frss=new MechanicalArmManagement();
	frss.setWritePerson(writePerson);
	request.setAttribute("MechanicalArmList", frs);
	request.setAttribute("loginperson", frss);
	return mapping.findForward("goToDetailqqq");
	}
}
