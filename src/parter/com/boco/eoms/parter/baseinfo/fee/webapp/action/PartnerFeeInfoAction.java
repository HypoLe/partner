package com.boco.eoms.parter.baseinfo.fee.webapp.action;

import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.abdera.Abdera;
import org.apache.abdera.factory.Factory;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.model.Person;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.parter.baseinfo.fee.mgr.PartnerFeeInfoMgr;
import com.boco.eoms.parter.baseinfo.fee.mgr.PartnerFeePlanMgr;
import com.boco.eoms.parter.baseinfo.fee.model.PartnerFeeInfo;
import com.boco.eoms.parter.baseinfo.fee.model.PartnerFeePlan;
import com.boco.eoms.parter.baseinfo.fee.util.PartnerFeeInfoConstants;
import com.boco.eoms.parter.baseinfo.fee.util.PartnerFeePlanConstants;
import com.boco.eoms.parter.baseinfo.fee.webapp.form.PartnerFeeInfoForm;
import com.boco.eoms.partner.baseinfo.util.StatisticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.util.StaticMethod;

/**
 * <p>
 * Title:合作伙伴付费信息
 * </p>
 * <p>
 * Description:合作伙伴付费信息
 * </p>
 * <p>
 * Wed Sep 09 11:22:35 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() lvweihua
 * @moudle.getVersion() 3.5
 * 
 */
public final class PartnerFeeInfoAction extends BaseAction {

	/**
	 * 未指定方法时默认调用的方法
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return search(mapping, form, request, response);
	}

	/**
	 * 新增合作伙伴付费信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("edit");
	}

	/**
	 * 增加有付款计划付费信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addPlanFee(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("editPlanFee");
	}

	/**
	 * 修改有付款计划付费信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editPlanFee(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PartnerFeeInfoMgr partnerFeeInfoMgr = (PartnerFeeInfoMgr) getBean("partnerFeeInfoMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		PartnerFeeInfo partnerFeeInfo = partnerFeeInfoMgr.getPartnerFeeInfo(id);
		PartnerFeeInfoForm partnerFeeInfoForm = (PartnerFeeInfoForm) convert(partnerFeeInfo);
		updateFormBean(mapping, request, partnerFeeInfoForm);
		return mapping.findForward("planFeeDetail");
	}
	
	/**
	 * 保存有计划份的付费信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward savePlanFee(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PartnerFeeInfoMgr partnerFeeInfoMgr = (PartnerFeeInfoMgr) getBean("partnerFeeInfoMgr");
		PartnerFeePlanMgr partnerFeePlanMgr = (PartnerFeePlanMgr) getBean("partnerFeePlanMgr");
		PartnerFeeInfoForm partnerFeeInfoForm = (PartnerFeeInfoForm) form;
		boolean isNew = (null == partnerFeeInfoForm.getId() || ""
				.equals(partnerFeeInfoForm.getId()));
		PartnerFeeInfo partnerFeeInfo = (PartnerFeeInfo) convert(partnerFeeInfoForm);
		String planId = StaticMethod.null2String(partnerFeeInfo.getPlanId());
		if (isNew) {
			String user = this.getUser(request).getUserid();
			String dept = this.getUser(request).getDeptid(); 
			Date date = StaticMethod.getLocalTime();
			// 设置对应的付款计划的付款金额等相关信息
			PartnerFeePlan pfp = new PartnerFeePlan();
			pfp = partnerFeePlanMgr.getPartnerFeePlan(planId);
			pfp.setFactPayFee(StaticMethod.nullObject2int(pfp.getFactPayFee())+StaticMethod.nullObject2int(partnerFeeInfo.getPayFee())+"");
			pfp.setFactPayDate(partnerFeeInfo.getPayDate());
			pfp.setPayStatus("1");
			pfp.setPayUser(partnerFeeInfo.getPayUser());
			pfp.setPayWay(partnerFeeInfo.getPayWay());
			partnerFeePlanMgr.savePartnerFeePlan(pfp);

			// 保存有付款计划的付费信息
			partnerFeeInfo.setCompactNo(StaticMethod.null2String(pfp.getCompactNo()));
			partnerFeeInfo.setIsPlan("1");
			partnerFeeInfo.setCreateUser(user);
			partnerFeeInfo.setCreateDept(dept);
			partnerFeeInfo.setCreateDate(date);
			partnerFeeInfoMgr.savePartnerFeeInfo(partnerFeeInfo);
		} else {
			//改付费信息对应的原付款计划id
			String plan = StaticMethod.null2String(request.getParameter("plan"));
			if (!planId.equals(plan)) {
				// 将原对应的付款计划修改为未付款
				PartnerFeePlan partnerFeePlan1 = partnerFeePlanMgr
						.getPartnerFeePlan(plan);
				partnerFeePlan1.setPayStatus("0");
				partnerFeePlan1.setFactPayFee("");
				// partnerFeePlan1.setFactPayDate();
				partnerFeePlan1.setPayUser("");
				partnerFeePlan1.setPayWay("");
				partnerFeePlanMgr.savePartnerFeePlan(partnerFeePlan1);

			}
			// 设置对应的付款计划的付款金额等相关信息
			PartnerFeePlan pfp = new PartnerFeePlan();
			pfp = partnerFeePlanMgr.getPartnerFeePlan(planId);
			pfp.setFactPayFee(partnerFeeInfo.getPayFee());
			pfp.setFactPayDate(partnerFeeInfo.getPayDate());
			pfp.setPayStatus("1");
			pfp.setPayUser(partnerFeeInfo.getPayUser());
			pfp.setPayWay(partnerFeeInfo.getPayWay());
			partnerFeePlanMgr.savePartnerFeePlan(pfp);
			System.out.println("============PartnerFeePlan============="+pfp.getId()+"==============================");
			System.out.println("============partnerFeeInfo============="+partnerFeeInfo.getId()+"==============================");
			partnerFeeInfo.setCompactNo(StaticMethod.null2String(pfp.getCompactNo()));
			System.out.println("============CompactNo============="+partnerFeeInfo.getCompactNo()+"==============================");
			partnerFeeInfo.setIsPlan("1");
			partnerFeeInfoMgr.savePartnerFeeInfo(partnerFeeInfo);
		}
		return mapping.findForward("success");
	}

	/**
	 * 删除有付款计划的付费信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removePlanFee(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PartnerFeeInfoMgr partnerFeeInfoMgr = (PartnerFeeInfoMgr) getBean("partnerFeeInfoMgr");
		PartnerFeePlanMgr partnerFeePlanMgr = (PartnerFeePlanMgr) getBean("partnerFeePlanMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		PartnerFeeInfo partnerFeeInfo = partnerFeeInfoMgr.getPartnerFeeInfo(id);
		//对应的付款计划的id
		String planId = StaticMethod.null2String(partnerFeeInfo.getPlanId());
		PartnerFeePlan partnerFeePlan = partnerFeePlanMgr.getPartnerFeePlan(planId);
		partnerFeePlan.setPayStatus("0");
		partnerFeeInfoMgr.removePartnerFeeInfo(id);
		return mapping.findForward("success");
	}
	
	
	/**
	 * 查看付费信息详情
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PartnerFeeInfoMgr partnerFeeInfoMgr = (PartnerFeeInfoMgr) getBean("partnerFeeInfoMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		PartnerFeeInfo partnerFeeInfo = partnerFeeInfoMgr.getPartnerFeeInfo(id);
		PartnerFeeInfoForm partnerFeeInfoForm = (PartnerFeeInfoForm) convert(partnerFeeInfo);
		updateFormBean(mapping, request, partnerFeeInfoForm);
		if("1".equals(partnerFeeInfoForm.getIsPlan())){
			return mapping.findForward("planFeeDetail");
		}
		return mapping.findForward("detail");
	}

	/**
	 * 修改合作伙伴付费信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PartnerFeeInfoMgr partnerFeeInfoMgr = (PartnerFeeInfoMgr) getBean("partnerFeeInfoMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		PartnerFeeInfo partnerFeeInfo = partnerFeeInfoMgr.getPartnerFeeInfo(id);
		PartnerFeeInfoForm partnerFeeInfoForm = (PartnerFeeInfoForm) convert(partnerFeeInfo);
		updateFormBean(mapping, request, partnerFeeInfoForm);
		return mapping.findForward("edit");
	}

	/**
	 * 保存无付款计划的合作伙伴付费信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PartnerFeeInfoMgr partnerFeeInfoMgr = (PartnerFeeInfoMgr) getBean("partnerFeeInfoMgr");
		PartnerFeeInfoForm partnerFeeInfoForm = (PartnerFeeInfoForm) form;
		boolean isNew = (null == partnerFeeInfoForm.getId() || ""
				.equals(partnerFeeInfoForm.getId()));
		PartnerFeeInfo partnerFeeInfo = (PartnerFeeInfo) convert(partnerFeeInfoForm);
		if (isNew) {
			String user = this.getUser(request).getUserid();
			String dept = this.getUser(request).getDeptid();
			Date date = StaticMethod.getLocalTime();
			partnerFeeInfo.setIsPlan("0");
			partnerFeeInfo.setCreateUser(user);
			partnerFeeInfo.setCreateDept(dept);
			partnerFeeInfo.setCreateDate(date);
			partnerFeeInfoMgr.savePartnerFeeInfo(partnerFeeInfo);
		} else {
			partnerFeeInfo.setIsPlan("0");
			partnerFeeInfoMgr.savePartnerFeeInfo(partnerFeeInfo);
		}
		return mapping.findForward("success");
	}

	/**
	 * 删除合作伙伴付费信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward remove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PartnerFeeInfoMgr partnerFeeInfoMgr = (PartnerFeeInfoMgr) getBean("partnerFeeInfoMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		partnerFeeInfoMgr.removePartnerFeeInfo(id);
		return mapping.findForward("success");
	}

	/**
	 * 分页显示合作伙伴付费信息列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				PartnerFeeInfoConstants.PARTNERFEEINFO_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		PartnerFeeInfoMgr partnerFeeInfoMgr = (PartnerFeeInfoMgr) getBean("partnerFeeInfoMgr");
		String whereStr = " where partnerFeeInfo.isPlan='0'";
		Map map = (Map) partnerFeeInfoMgr.getPartnerFeeInfos(pageIndex,
				pageSize, whereStr);
		List list = (List) map.get("result");
		request.setAttribute(PartnerFeeInfoConstants.PARTNERFEEINFO_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}

	/**
	 * 进入付款信息主页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("main");
	}

	/**
	 * 查询有计划付费信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchPlanFee(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				PartnerFeeInfoConstants.PARTNERFEEINFO_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		PartnerFeeInfoMgr partnerFeeInfoMgr = (PartnerFeeInfoMgr) getBean("partnerFeeInfoMgr");
		String whereStr = " where partnerFeeInfo.isPlan='1'";
		Map map = (Map) partnerFeeInfoMgr.getPartnerFeeInfos(pageIndex,
				pageSize, whereStr);
		List list = (List) map.get("result");
		request.setAttribute(PartnerFeeInfoConstants.PARTNERFEEINFO_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("planfee");
	}

	/**
	 * 某单位收款金额详情列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchInfoByCollect(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				PartnerFeeInfoConstants.PARTNERFEEINFO_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		String collectUnit = StaticMethod.null2String(request
				.getParameter("collectUnit"));
		String beginDate = StaticMethod.null2String(request
				.getParameter("beginDate"));
		String endDate = StaticMethod.null2String(request
				.getParameter("endDate"));
		request.setAttribute("collectUnit", collectUnit);
		request.setAttribute("beginDate", beginDate);
		request.setAttribute("endDate", endDate);
		PartnerFeeInfoMgr partnerFeeInfoMgr = (PartnerFeeInfoMgr) getBean("partnerFeeInfoMgr");
		List list = partnerFeeInfoMgr.getPartnerFeeInfosByCollectUnit(
				collectUnit, beginDate, endDate);
		request.setAttribute(PartnerFeeInfoConstants.PARTNERFEEINFO_LIST, list);
		request.setAttribute("resultSize", new Integer(list.size()));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("listCollect");
	}

	/**
	 * 某单位付款金额详情列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchInfoByPay(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				PartnerFeeInfoConstants.PARTNERFEEINFO_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		String payUnit = StaticMethod.null2String(request
				.getParameter("payUnit"));
		String beginDate = StaticMethod.null2String(request
				.getParameter("beginDate"));
		String endDate = StaticMethod.null2String(request
				.getParameter("endDate"));
		request.setAttribute("payUnit", payUnit);
		request.setAttribute("beginDate", beginDate);
		request.setAttribute("endDate", endDate);
		PartnerFeeInfoMgr partnerFeeInfoMgr = (PartnerFeeInfoMgr) getBean("partnerFeeInfoMgr");
		List list = partnerFeeInfoMgr.getPartnerFeeInfosByPayUnit(payUnit,
				beginDate, endDate);
		request.setAttribute(PartnerFeeInfoConstants.PARTNERFEEINFO_LIST, list);
		request.setAttribute("resultSize", new Integer(list.size()));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("listPay");
	}

	/**
	 * 某合同编号的付款详情列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchInfoByCompact(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				PartnerFeeInfoConstants.PARTNERFEEINFO_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		String compactNo = StaticMethod.null2String(request
				.getParameter("compactNo"));
		String beginDate = StaticMethod.null2String(request
				.getParameter("beginDate"));
		String endDate = StaticMethod.null2String(request
				.getParameter("endDate"));
		request.setAttribute("compactNo", compactNo);
		request.setAttribute("beginDate", beginDate);
		request.setAttribute("endDate", endDate);
		PartnerFeeInfoMgr partnerFeeInfoMgr = (PartnerFeeInfoMgr) getBean("partnerFeeInfoMgr");
		List list = partnerFeeInfoMgr.getPartnerFeeInfosByCompactNo(compactNo,
				beginDate, endDate);
		request.setAttribute(PartnerFeeInfoConstants.PARTNERFEEINFO_LIST, list);
		request.setAttribute("resultSize", new Integer(list.size()));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("listCompact");
	}

	/**
	 * 收款单位收款金额统计
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchFeeCollectStatistics(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
//		String pageIndexName = new org.displaytag.util.ParamEncoder(
//				TrainRecordConstants.TRAINRECORD_LIST)
//				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
//		final Integer pageIndex = new Integer(GenericValidator
//				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
//				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		String startDate = StaticMethod.null2String(request
				.getParameter("startDate"));
		String endDate = StaticMethod.null2String(request
				.getParameter("endDate"));
		String collectUnit = StaticMethod.null2String(request
				.getParameter("collectUnit"));

		// 默认统计最近一周的记录
		if ("".equals(endDate)) {
			Date endDateTime = new Date(System.currentTimeMillis());
			java.util.Date startDateTime = StatisticMethod.countDate(
					endDateTime, -6);

			endDate = StatisticMethod.dateToString(endDateTime, "yyyy-MM-dd");
			startDate = StatisticMethod.dateToString(startDateTime,
					"yyyy-MM-dd");
		}
		PartnerFeeInfoMgr partnerFeeInfoMgr = (PartnerFeeInfoMgr) getBean("partnerFeeInfoMgr");
		Map map = (Map) partnerFeeInfoMgr.getFeeCollectStatistics(collectUnit,
				startDate, endDate);
		List list = (List) map.get("result");
		request.setAttribute(PartnerFeePlanConstants.PARTNERFEEPLAN_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);

		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);
		request.setAttribute("collectUnit", collectUnit);
		return mapping.findForward("collectStatistics");
	}

	/**
	 * 按付款单位付款金额统计
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchFeePayStatistics(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
//		String pageIndexName = new org.displaytag.util.ParamEncoder(
//				TrainRecordConstants.TRAINRECORD_LIST)
//				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
//		final Integer pageIndex = new Integer(GenericValidator
//				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
//				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		String startDate = StaticMethod.null2String(request
				.getParameter("startDate"));
		String endDate = StaticMethod.null2String(request
				.getParameter("endDate"));
		String payUnit = StaticMethod.null2String(request
				.getParameter("payUnit"));

		// 默认统计最近一周的记录
		if ("".equals(endDate)) {
			Date endDateTime = new Date(System.currentTimeMillis());
			java.util.Date startDateTime = StatisticMethod.countDate(
					endDateTime, -6);

			endDate = StatisticMethod.dateToString(endDateTime, "yyyy-MM-dd");
			startDate = StatisticMethod.dateToString(startDateTime,
					"yyyy-MM-dd");
		}
		PartnerFeeInfoMgr partnerFeeInfoMgr = (PartnerFeeInfoMgr) getBean("partnerFeeInfoMgr");
		Map map = (Map) partnerFeeInfoMgr.getFeePayStatistics(payUnit,
				startDate, endDate);
		List list = (List) map.get("result");
		request.setAttribute(PartnerFeePlanConstants.PARTNERFEEPLAN_LIST, list);

		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);

		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);
		request.setAttribute("payUnit", payUnit);
		return mapping.findForward("payStatistics");
	}

	/**
	 * 按合同统计 付款金额
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchFeeCompactStatistics(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
//		String pageIndexName = new org.displaytag.util.ParamEncoder(
//				TrainRecordConstants.TRAINRECORD_LIST)
//				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
//		final Integer pageIndex = new Integer(GenericValidator
//				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
//				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		String startDate = StaticMethod.null2String(request
				.getParameter("startDate"));
		String endDate = StaticMethod.null2String(request
				.getParameter("endDate"));
		String compactNo = StaticMethod.null2String(request
				.getParameter("compactNo"));

		// 默认统计最近一周的记录
		if ("".equals(endDate)) {
			Date endDateTime = new Date(System.currentTimeMillis());
			java.util.Date startDateTime = StatisticMethod.countDate(
					endDateTime, -6);

			endDate = StatisticMethod.dateToString(endDateTime, "yyyy-MM-dd");
			startDate = StatisticMethod.dateToString(startDateTime,
					"yyyy-MM-dd");
		}
		PartnerFeeInfoMgr partnerFeeInfoMgr = (PartnerFeeInfoMgr) getBean("partnerFeeInfoMgr");
		Map map = (Map) partnerFeeInfoMgr.getFeeCompactStatistics(compactNo,
				startDate, endDate);
		List list = (List) map.get("result");
		request.setAttribute(PartnerFeePlanConstants.PARTNERFEEPLAN_LIST, list);

		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);

		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);
		request.setAttribute("compactNo", compactNo);
		return mapping.findForward("compactStatistics");
	}

	/**
	 * 分页显示合作伙伴付费信息列表，支持Atom方式接入Portal
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	// public ActionForward search4Atom(ActionMapping mapping, ActionForm form,
	// HttpServletRequest request, HttpServletResponse response)
	// throws Exception {
	// try {
	// // --------------用于分页，得到当前页号-------------
	// final Integer pageIndex = new Integer(request
	// .getParameter("pageIndex"));
	// final Integer pageSize = new Integer(request
	// .getParameter("pageSize"));
	// PartnerFeeInfoMgr partnerFeeInfoMgr = (PartnerFeeInfoMgr)
	// getBean("partnerFeeInfoMgr");
	// Map map = (Map) partnerFeeInfoMgr.getPartnerFeeInfos(pageIndex, pageSize,
	// "");
	// List list = (List) map.get("result");
	// PartnerFeeInfo partnerFeeInfo = new PartnerFeeInfo();
	//			
	// //创建ATOM源
	// Factory factory = Abdera.getNewFactory();
	// Feed feed = factory.newFeed();
	//			
	// // 分页
	// for (int i = 0; i < list.size(); i++) {
	// partnerFeeInfo = (PartnerFeeInfo) list.get(i);
	//				
	// // TODO 请按照下面的实例给entry赋值
	// Entry entry = feed.insertEntry();
	// entry.setTitle("<a href='" + request.getScheme() + "://"
	// + request.getServerName() + ":"
	// + request.getServerPort()
	// + request.getContextPath()
	// + "/partnerFeeInfo/partnerFeeInfos.do?method=edit&id="
	// + partnerFeeInfo.getId() + "' target='_blank'>"
	// + display name for list + "</a>");
	// entry.setSummary(summary);
	// entry.setContent(content);
	// entry.setLanguage(language);
	// entry.setText(text);
	// entry.setRights(tights);
	//				
	// // 以下三项需要传入Date类型或String类型（yyyy-MM-dd）的参数
	// entry.setUpdated(new java.util.Date());
	// entry.setPublished(new java.util.Date());
	// entry.setEdited(new java.util.Date());
	//				
	// // 为person的name属性赋值，entry.addAuthor可以随意赋值
	// Person person = entry.addAuthor(userId);
	// person.setName(userName);
	// }
	//			
	// // 每页显示条数
	// feed.setText(map.get("total").toString());
	// OutputStream os = response.getOutputStream();
	// PrintStream ps = new PrintStream(os);
	// feed.getDocument().writeTo(ps);
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		}
	//		return null;
	//	}
}