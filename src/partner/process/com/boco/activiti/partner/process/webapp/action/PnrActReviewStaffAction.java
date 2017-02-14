package com.boco.activiti.partner.process.webapp.action;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.activiti.partner.process.model.PnrActReviewStaff;
import com.boco.activiti.partner.process.service.PnrActReviewStaffService;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.sheet.base.webapp.action.SheetAction;

public class PnrActReviewStaffAction extends SheetAction {

	/**
	 * 查询会审人员
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ActionForward queryPnrActReviewStaff(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				"pnrActReviewStaffList")// 要改
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();

		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));

		PnrActReviewStaffService pnrActReviewStaffService = (PnrActReviewStaffService) getBean("pnrActReviewStaffService");
		String whereStr = " where 1=1";
		// 组装查询条件
		if (request.getParameter("nameSearch") != null
				&& !request.getParameter("nameSearch").equals("")) {
			whereStr += " and name like '%"
					+ request.getParameter("nameSearch") + "%'";
		}
		if (request.getParameter("userIdSearch") != null
				&& !request.getParameter("userIdSearch").equals("")) {
			whereStr += " and userId like '%"
					+ request.getParameter("userIdSearch") + "%'";
		}

		Map map = (Map) pnrActReviewStaffService.queryPnrActReviewStaff(
				pageIndex, pageSize, whereStr);
		List list = (List) map.get("result");
		request.setAttribute("pnrActReviewStaffList", list);
		request.setAttribute("total", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}

	/**
	 * 新增会审人员
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addPnrActReviewStaff(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		return mapping.findForward("editPnrActReviewStaff");
	}

	/**
	 * 修改会审人员
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward updatePnrActReviewStaff(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PnrActReviewStaffService pnrActReviewStaffService = (PnrActReviewStaffService) getBean("pnrActReviewStaffService");
		String id = request.getParameter("id");
		PnrActReviewStaff pnrActReviewStaff = pnrActReviewStaffService
				.getPnrActReviewStaff(id);
		request.setAttribute("pnrActReviewStaff", pnrActReviewStaff);
		return mapping.findForward("editPnrActReviewStaff");
	}

	/**
	 * 保存会审人员
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward savePnrActReviewStaff(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		PnrActReviewStaffService pnrActReviewStaffService = (PnrActReviewStaffService) getBean("pnrActReviewStaffService");
		String id = request.getParameter("id");
		String cityId = request.getParameter("cityId");
		String cityName = request.getParameter("cityName");
		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");

		PnrActReviewStaff pnrActReviewStaff = null;
		if (id == null || id.equals("")) { // 新增
			pnrActReviewStaff = new PnrActReviewStaff();
		} else { // 修改
			pnrActReviewStaff = pnrActReviewStaffService
					.getPnrActReviewStaff(id);
		}

		pnrActReviewStaff.setUserId(userId);
		pnrActReviewStaff.setUserName(userName);
		pnrActReviewStaff.setCityId(cityId);
		pnrActReviewStaff.setCityName(cityName);

		pnrActReviewStaffService.savePnrActReviewStaff(pnrActReviewStaff);

		return mapping.findForward("queryView");
	}

	/**
	 * 验证地市的审核人员是否已存在
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward checkCityId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String cityId = request.getParameter("cityId");
		String id = request.getParameter("id");
		int check = 1;// 验证标识
		PnrActReviewStaffService pnrActReviewStaffService = (PnrActReviewStaffService) getBean("pnrActReviewStaffService");
		try {
			check = pnrActReviewStaffService.checkCityIdUnique(cityId, id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("---check------" + check);
		try {
			PrintWriter writer = response.getWriter();
			writer.print(check);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
