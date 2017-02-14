package com.boco.activiti.partner.process.webapp.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.activiti.partner.process.model.PnrActCityBudgetAmount;
import com.boco.activiti.partner.process.po.BudgetAmountModel;
import com.boco.activiti.partner.process.service.IPnrActCityBudgetAmountService;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.sheet.base.webapp.action.SheetAction;

public class PnrActCityBudgetAmountAction extends SheetAction {

	/**
	 * 查询地市预算金额
	 * WANGJUN
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ActionForward queryPnrActCityBudgetAmount(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				"pnrActCityBudgetAmountList")// 要改
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();

		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));

		IPnrActCityBudgetAmountService pnrActCityBudgetAmountService = (IPnrActCityBudgetAmountService) getBean("pnrActCityBudgetAmountService");
		String whereStr = " where 1=1";
		
		String budgetQuarter =request.getParameter("budgetQuarter");
		String budgetYear =request.getParameter("budgetYear");
		String cityId =request.getParameter("cityId");
		String cityName =request.getParameter("cityName");
		// 组装查询条件
		if (budgetQuarter!= null
				&& !budgetQuarter.equals("")) {
			whereStr += " and budgetQuarter = '"
					+ budgetQuarter + "'";
		}
		if (budgetYear != null
				&& !budgetYear.equals("")) {
			whereStr += " and budgetYear = '"
					+ budgetYear + "'";
		}
		if (cityId != null
				&& !cityId.equals("")) {
			whereStr += " and cityId = '"
				+ cityId + "'";
		}

		
		request.setAttribute("budgetYear", budgetYear);
		request.setAttribute("budgetQuarter", budgetQuarter);
		request.setAttribute("cityId", cityId);
		request.setAttribute("cityName", cityName);
		
		Map map = (Map) pnrActCityBudgetAmountService.queryPnrActCityBudgetAmount(
				pageIndex, pageSize, whereStr);
		List list = (List) map.get("result");
		request.setAttribute("pnrActCityBudgetAmountList", list);
		request.setAttribute("total", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}

	/**
	 * 打开新增地市预算金额页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addPnrActCityBudgetAmount(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		return mapping.findForward("editPnrActCityBudgetAmount");
	}

	/**
	 * 打开修改地市预算金额页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward updatePnrActCityBudgetAmount(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		IPnrActCityBudgetAmountService pnrActCityBudgetAmountService = (IPnrActCityBudgetAmountService) getBean("pnrActCityBudgetAmountService");
		String id = request.getParameter("id");
		PnrActCityBudgetAmount pnrActCityBudgetAmount = pnrActCityBudgetAmountService
				.getPnrActCityBudgetAmount(id);
		request.setAttribute("pnrActCityBudgetAmount", pnrActCityBudgetAmount);
		return mapping.findForward("updatePnrActCityBudgetAmount");
	}

	/**
	 * 新增地市预算金额保存
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward savePnrActCityBudgetAmount(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		IPnrActCityBudgetAmountService pnrActCityBudgetAmountService = (IPnrActCityBudgetAmountService) getBean("pnrActCityBudgetAmountService");
	//	String id = request.getParameter("id");
		String cityId = request.getParameter("cityId");
		String cityName = request.getParameter("cityName");
		String budgetYear = request.getParameter("budgetYear");
		String budgetQuarter = request.getParameter("budgetQuarter");
		String budgetAmount = request.getParameter("budgetAmount");
		
//		PnrActCityBudgetAmount pnrActCityBudgetAmount = null;
//		if (id == null || id.equals("")) { // 新增
//			pnrActCityBudgetAmount = new PnrActCityBudgetAmount();
//		} else { // 修改
//			pnrActCityBudgetAmount = pnrActCityBudgetAmountService
//					.getPnrActCityBudgetAmount(id);
//		}
		
		if (cityId != null && !"".equals(cityId)) {
			String[] tempCityIds = cityId.split(",");
			String[] tempcityNames = cityName.split(",");
			for (int i = 0; i < tempCityIds.length; i++) {
				PnrActCityBudgetAmount pnrActCityBudgetAmount = new PnrActCityBudgetAmount();
				pnrActCityBudgetAmount.setCityId(tempCityIds[i]);
				pnrActCityBudgetAmount.setCityName(tempcityNames[i]);
				pnrActCityBudgetAmount.setBudgetYear(budgetYear);
				pnrActCityBudgetAmount.setBudgetQuarter(budgetQuarter);
				pnrActCityBudgetAmount.setBudgetAmount(Double
						.valueOf(budgetAmount));
				pnrActCityBudgetAmountService
						.savePnrActCityBudgetAmount(pnrActCityBudgetAmount);
			}

		}
		
		return mapping.findForward("queryView");
	}
	
	/**
	 * 更新地市预算金额保存
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveUpdatePnrActCityBudgetAmount(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		IPnrActCityBudgetAmountService pnrActCityBudgetAmountService = (IPnrActCityBudgetAmountService) getBean("pnrActCityBudgetAmountService");
		String id = request.getParameter("id");
		String budgetAmount = request.getParameter("budgetAmount");
		PnrActCityBudgetAmount	pnrActCityBudgetAmount = pnrActCityBudgetAmountService
					.getPnrActCityBudgetAmount(id);
		pnrActCityBudgetAmount.setBudgetAmount(Double.valueOf(budgetAmount));
		
		pnrActCityBudgetAmountService.savePnrActCityBudgetAmount(pnrActCityBudgetAmount);
		
		return mapping.findForward("queryView");
	}

	/**
	 * 校验唯一性
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward validateUnique(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String cityId = request.getParameter("cityId");
		String cityName = request.getParameter("cityName");
		String budgetYear = request.getParameter("budgetYear");
		String budgetQuarter = request.getParameter("budgetQuarter");

		String returnJson="";//返回JSON结果
		IPnrActCityBudgetAmountService pnrActCityBudgetAmountService = (IPnrActCityBudgetAmountService) getBean("pnrActCityBudgetAmountService");
		try {
			returnJson = pnrActCityBudgetAmountService.validateUnique(cityId,cityName,budgetYear,budgetQuarter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("-----------------returnJson="+returnJson);

		try {
			PrintWriter writer = response.getWriter();
			writer.print(returnJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 打开新增地市预算金额页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward selectAddPnrActCityBudgetAmountList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				"addPnrActCityBudgetAmountList")// 要改
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		Integer pageSize = 17*4;

		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		IPnrActCityBudgetAmountService pnrActCityBudgetAmountService = (IPnrActCityBudgetAmountService) getBean("pnrActCityBudgetAmountService");
		String whereStr = " where 1=1";
		
		String budgetQuarter ="";
		String budgetYear="";
		budgetYear =request.getParameter("year");
		String flag =request.getParameter("flag");
		String cityId =request.getParameter("cityId");
		String cityName =request.getParameter("cityName");
		// 组装查询条件
	/*	if (budgetQuarter!= null
				&& !budgetQuarter.equals("")) {
			whereStr += " and budgetQuarter = '"
					+ budgetQuarter + "'";
		}*/
		if (budgetYear != null
				&& !budgetYear.equals("")) {
			whereStr += " and budgetYear = '"
					+ budgetYear + "'";
		}
		if (cityId != null
				&& !cityId.equals("")) {
			whereStr += " and cityId = '"
				+ cityId + "'";
		}
    if(flag == null){
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
	    Calendar now = Calendar.getInstance();
	    System.out.println(now.get(Calendar.MONTH)+1);
	    budgetYear=String.valueOf(now.get(Calendar.YEAR));
	    int month = now.get(Calendar.MONTH) + 1;
	    if(month == 1 || month ==2 || month ==3){
	    	budgetQuarter="01";
	    }
	    else if(month == 4 || month ==5 || month ==6){
	    	budgetQuarter="04";
	    }
	    else if(month == 7 || month ==8 || month ==9){
	    	budgetQuarter="07";
	    }
	    else if(month == 10 || month ==11 || month ==12){
	    	budgetQuarter="10";
	    }
	    whereStr += " and budgetYear = '"
			+ budgetYear + "'";
	    whereStr += " and budgetQuarter = '"
			+ budgetQuarter + "'";
}
		
		request.setAttribute("budgetYear", budgetYear);
		request.setAttribute("budgetQuarter", budgetQuarter);
		request.setAttribute("cityId", cityId);
		request.setAttribute("cityName", cityName);	
		
		String orderStr=" order by to_number(budget_year),to_number(budget_quarter),to_number(order_code)";
		Map map=null;
		try{
			map = (Map) pnrActCityBudgetAmountService.selectPnrActCityBudgetAmountList(pageIndex, pageSize, whereStr, orderStr);
		}catch(Exception e){e.printStackTrace();}
		
		List list = (List) map.get("result");
		request.setAttribute("addPnrActCityBudgetAmountList", list);
		request.setAttribute("total", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("addPnrActCityBudgetAmountList");
	}
	
	/**
	 * 通过AJAX方式更新地市金额
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward updatePnrActCityBudgetAmountByAjax(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String returnJson = "";
		IPnrActCityBudgetAmountService pnrActCityBudgetAmountService = (IPnrActCityBudgetAmountService) getBean("pnrActCityBudgetAmountService");
		String id = request.getParameter("id");
		String budgetAmount = request.getParameter("budgetAmount");
		PnrActCityBudgetAmount pnrActCityBudgetAmount = pnrActCityBudgetAmountService
				.getPnrActCityBudgetAmount(id);
		if (budgetAmount != null && !"".equals(budgetAmount)) {
			pnrActCityBudgetAmount.setBudgetAmount(Double.valueOf(budgetAmount
					.trim()));
		}
		try {
			pnrActCityBudgetAmountService
			.savePnrActCityBudgetAmount(pnrActCityBudgetAmount);
			returnJson = "{\"result\":\"success\",\"content\":\"地市金额更新成功\"}";
		} catch (Exception e) {
			returnJson = "{\"result\":\"error\",\"content\":\"地市金额更新失败\"}";
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		out.print(returnJson);
		return null;
	}
	
	/**
	 * 通过AJAX方式更新地市金额
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward updateCountyBudgetAmountByAjax(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String returnJson = "";
		IPnrActCityBudgetAmountService pnrActCityBudgetAmountService = (IPnrActCityBudgetAmountService) getBean("pnrActCityBudgetAmountService");
		String id = StaticMethod.nullObject2String(request.getParameter("id"));
		String budgetAmount = StaticMethod.nullObject2String(request.getParameter("budgetAmount"));
		try {
			pnrActCityBudgetAmountService.updateCountyBudgetAmount(id,budgetAmount);
			returnJson = "{\"result\":\"success\",\"content\":\"区县金额更新成功\"}";
		} catch (Exception e) {
			returnJson = "{\"result\":\"error\",\"content\":\"区县金额更新失败\"}";
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();
		out.print(returnJson);
		return null;
	}
	
	/**
	 * 打开新增区县预算金额页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward selectAddPnrActCountyBudgetAmountList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String pId = StaticMethod.nullObject2String(request.getParameter("pId"));//父ID(地市表中的主键ID)
//		String cityId = StaticMethod.nullObject2String(request.getParameter("cityId"));//地市ID
		String budgetYear = StaticMethod.nullObject2String(request.getParameter("budgetYear"));//年份
		String budgetQuarter = StaticMethod.nullObject2String(request.getParameter("budgetQuarter"));//季度
		String budgetAmount = StaticMethod.nullObject2String(request.getParameter("budgetAmount"));//地市金额
	
		IPnrActCityBudgetAmountService pnrActCityBudgetAmountService = (IPnrActCityBudgetAmountService) getBean("pnrActCityBudgetAmountService");
		List<BudgetAmountModel> list = pnrActCityBudgetAmountService.getCountyAmountByCityId(pId);
		
		request.setAttribute("addPnrActCountyBudgetAmountList", list);
		request.setAttribute("total", list.size());
		request.setAttribute("budgetYear", budgetYear);
		request.setAttribute("budgetQuarter", budgetQuarter);
		request.setAttribute("cityBudgetAmount", budgetAmount);
		return mapping.findForward("addPnrActCountyBudgetAmountList");
	}
	
}
