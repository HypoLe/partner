package com.boco.eoms.partner.inspect.webapp.action;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import utils.PartnerPrivUtils;

import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.dept.service.bo.TawSystemDeptBo;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.model.PartnerUser;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.deviceInspect.service.PnrInspectTaskLinkService;
import com.boco.eoms.partner.deviceInspect.switchcfg.PnrDeviceInspectSwitchConfig;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanApproveMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanChangeMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanExecuteMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanItemMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanMainMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanResChangeMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanResMgr;
import com.boco.eoms.partner.inspect.model.InspectPlanApprove;
import com.boco.eoms.partner.inspect.model.InspectPlanChange;
import com.boco.eoms.partner.inspect.model.InspectPlanItem;
import com.boco.eoms.partner.inspect.model.InspectPlanMain;
import com.boco.eoms.partner.inspect.model.InspectPlanRes;
import com.boco.eoms.partner.inspect.model.InspectPlanResChange;
import com.boco.eoms.partner.inspect.util.InspectConstants;
import com.boco.eoms.partner.inspect.util.InspectUtils;
import com.boco.eoms.partner.inspect.webapp.form.InspectPlanItemForm;
import com.boco.eoms.partner.inspect.webapp.form.InspectPlanMainForm;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

/**
 * Description: 巡检计划 Copyright: Copyright (c)2012 Company: BOCO
 * 
 * @author: Liuchang
 * @version: 1.0 Create at: Jul 9, 2012 9:54:52 AM
 */
public class InspectPlanAction extends BaseAction {

	/**
	 * 查询巡检主体列表
	 */
	public ActionForward findInspectPlanMainList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String sheet = request.getParameter("sheet");

		IInspectPlanMainMgr inspectPlanMainMgr = getInspectPlanMainMgr();
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(),
				search);
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "list");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.setMaxResults(CommonConstants.PAGE_SIZE);

		// 查询条件为当前年当前月
		// search.addFilterEqual("year", new LocalDate().getYear());
		// search.addFilterEqual("month", new LocalDate().getMonthOfYear());

		// 查询巡检工单草稿状态
		if ("true".equals(sheet)) {
			search.addFilterNotEqual("approveStatus", 1);
		}

		search.addFilterEqual("status", 1);
		search.addSortDesc("createTime");

		// 权限控制
		TawSystemSessionForm sessionForm = this.getUser(request);
		String userDeptId = sessionForm.getDeptid();
		String userId = sessionForm.getUserid();
		// PartnerUserMgr partnerUserMgr =
		// (PartnerUserMgr)getBean("partnerUserMgr");
		// PartnerUser pnrUser = partnerUserMgr.getPartnerUserByUserId(userId);
		/*String rootPartnerId = sessionForm.getRootPnrDeptId();
		if (!"true".equals(sheet)
				&& (!"admin".equals(userId) && userDeptId
						.startsWith(rootPartnerId))) {
			search.addFilterLike("deptMagId", userDeptId + "%");
		}*/
		
		//新增权限 -201404-17
		PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
        PartnerUser user = partnerUserMgr.getPartnerUserByUserId(userId);
		//关于代维与自维的权限增加-20140416---根据自维公司的deptid开头是201
        boolean isMaintain=true;
        if(null!=user&&user.getDeptId().startsWith("201")){
        	isMaintain=false;
        }
        //关于代维与自维的权限增加-20140416
        
        if (null != user&&isMaintain) {//不为空说明是代维公司人查看，默认为只能查看自己部门以及其下部门的，所以应该是deptMagId like
            String deptMagId = sessionForm.getDeptid();
            if (deptMagId.length() > 6) {
                search.addFilterLike("deptMagId", deptMagId.substring(0, deptMagId.length() - 2) + "%");
            } else {
                search.addFilterLike("deptMagId", deptMagId + "%");
            }
        } else if (!"admin".equals(userId)) {//如果不是代维公司人员，则应该根据用户所属部门的所属地域来查看巡检任务，所以应该是根据areaId like

            ITawSystemDeptManager mgr = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
            TawSystemDept dept = mgr.getDeptinfobydeptid(userDeptId, "0");
            String areaid = dept.getAreaid();
            PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
            List partnerList = partnerDeptMgr.getPartnerDepts(" and countyId like '" + areaid + "%'");
            List<String> deptMagIdList = new ArrayList<String>();

            for (int i = 0; partnerList != null && i < partnerList.size(); i++) {
                PartnerDept partnerDept = (PartnerDept) partnerList.get(i);

                String deptMagId = StaticMethod.nullObject2String(partnerDept.getDeptMagId());
                if (!deptMagId.equals("")) {
                    deptMagIdList.add(deptMagId);
                }
            }
            if (deptMagIdList != null && deptMagIdList.isEmpty() == false) {
                search.addFilterIn("deptMagId", deptMagIdList);
            } else {//如果没有管理代维公司，则不允许看该界面
                return mapping.findForward("inspectnopriv");
            }
        }
        //新增权限 -201404-17 -end 
        
		if ("true".equals(sheet)) {
			search.addFilterEqual("creatorDeptId", userDeptId);
		}

		SearchResult<InspectPlanMain> searchResult = inspectPlanMainMgr
				.searchAndCount(search);
		request.setAttribute("yesOrNoMap", InspectConstants.yesOrNoMap);
		request.setAttribute("approveStatusMap",
				InspectConstants.approveStatusMap);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("list", searchResult.getResult());
		request.setAttribute("size", searchResult.getTotalCount());
		request.setAttribute("sheet", sheet);
		return mapping.findForward("inspectPlanMainList");
	}

	/**
	 * 新增跳转到编辑页面
	 */
	public ActionForward toSaveInspectPlanMain(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("year", new LocalDate().getYear());
		request.setAttribute("month", new LocalDate().getMonthOfYear());
		request.setAttribute("sheet", request.getParameter("sheet")); // 是否工单菜单进入
		return mapping.findForward("inspectPlanMainForm");
	}

	/**
	 * 修改跳转到编辑页面
	 */
	public ActionForward toEditInspectPlanMain(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		IInspectPlanMainMgr inspectPlanMainMgr = getInspectPlanMainMgr();
		String id = request.getParameter("id");
		InspectPlanMain planMain = inspectPlanMainMgr.find(id);
		request.setAttribute("planMain", planMain);
		request.setAttribute("id", id);
		request.setAttribute("year", new LocalDate().getYear());
		request.setAttribute("month", new LocalDate().getMonthOfYear());
		request.setAttribute("sheet", request.getParameter("sheet")); // 是否工单菜单进入
		return mapping.findForward("inspectPlanMainForm");
	}

	/**
	 * 删除巡检计划
	 */
	public ActionForward removeInspectPlanMain(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String sheet = request.getParameter("sheet");
		IInspectPlanMainMgr inspectPlanMainMgr = getInspectPlanMainMgr();
		IInspectPlanResMgr inspectPlanResMgr = getInspectPlanResMgr();
		String id = request.getParameter("id");
		if (StringUtils.isEmpty(id)) {
			return mapping.findForward("failure");
		} else {
			InspectPlanMain planMain = inspectPlanMainMgr.find(id);
			planMain.setStatus(0);
			inspectPlanMainMgr.save(planMain);

			String hql = "update InspectPlanRes set planId='',planStartTime=null,planEndTime=null,forceAssign=0 where planId='"
					+ id + "'";
			inspectPlanResMgr.updateInspectPlanResByHql(hql);
		}
		ActionForward actionForward = new ActionForward();
		actionForward
				.setPath("/inspectPlan.do?method=findInspectPlanMainList&sheet="
						+ sheet);
		actionForward.setRedirect(false);
		return actionForward;
	}

	/**
	 * 查看详情
	 */
	@SuppressWarnings("unchecked")
	public ActionForward getInspectPlanMainDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		IInspectPlanMainMgr inspectPlanMainMgr = getInspectPlanMainMgr();
		IInspectPlanResMgr inspectPlanResMgr = getInspectPlanResMgr();
		String id = request.getParameter("id");
		InspectPlanMain planMain = inspectPlanMainMgr.find(id);
		request.setAttribute("planMain", planMain);

		// 关联资源列表
		final Integer pageSize = CommonConstants.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "list");
		final int pageIndex = Strings.isNullOrEmpty(pageIndexString) ? 0
				: Integer.valueOf(pageIndexString).intValue() - 1;
		String whereStr = " where 1=1 and planId='" + id + "'";
		Map map = (Map) inspectPlanResMgr.findInspectPlanResList(pageIndex,
				pageSize, whereStr);
		List list = (List) map.get("result");
		request.setAttribute("list", list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("cycleMap", InspectConstants.cycleMap);

		// 审批历史列表
		IInspectPlanApproveMgr inspectPlanApproveMgr = (IInspectPlanApproveMgr) getBean("inspectPlanApproveMgr");
		Search search = new Search();
		search.addFilterEqual("planId", id);
		search.addSortDesc("approveTime");
		List<InspectPlanApprove> approveList = inspectPlanApproveMgr
				.search(search);
		request.setAttribute("approveList", approveList);
		request.setAttribute("approveStatusMap",
				InspectConstants.approveStatusMap);
		request.setAttribute("planTypeMap", InspectConstants.planTypeMap);

		// 本月第一天
		request.setAttribute("currentMonthStart", new LocalDate().dayOfMonth()
				.withMinimumValue().toString());
		return mapping.findForward("inspectPlanMainDetail");
	}

	/**
	 * 保存巡检主体
	 */
	public ActionForward saveInspectPlanMain(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String sheet = request.getParameter("sheet");
		IInspectPlanMainMgr inspectPlanMainMgr = getInspectPlanMainMgr();
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
		InspectPlanMainForm planMainForm = (InspectPlanMainForm) form;
		String id = planMainForm.getId();
		InspectPlanMain planMain = null;

		if (StringUtils.isEmpty(id)) { // 新增
			planMain = new InspectPlanMain();
			BeanUtils.copyProperties(planMain, planMainForm);
			LocalDate date = new LocalDate();
			String month = date.getMonthOfYear() + "";
			String year = date.getYear() + "";

			// 如果是工单巡检
			if (!InspectConstants.getSheetInspectSwitch()) {
				// 相同专业、代维公司、巡检年月的计划不能重复制定
				Search search = new Search();
				search.addFilterEqual("specialty", planMain.getSpecialty())
						.addFilterEqual("partnerDeptId",
								planMain.getPartnerDeptId())
						.addFilterEqual("status", 1)
						.addFilterEqual("year", year)
						.addFilterEqual("month", month);
				List<InspectPlanMain> planMainList = inspectPlanMainMgr
						.search(search);
				if (planMainList.size() > 0) {
					request.setAttribute("msg", "相同专业、代维公司、巡检年月的计划不能重复制定");
					return mapping.findForward("failure");
				}
			}

			TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
					.getSession().getAttribute("sessionform");
			planMain.setCreator(sessionForm.getUserid());
			planMain.setCreatorDeptId(sessionForm.getDeptid());
			planMain.setCreateTime(new Date());
			planMain.setApproveStatus(3);
			planMain.setResConfig(0);
			planMain.setResNum(0);
			planMain.setResDoneNum(0);
			planMain.setYear(new LocalDate().getYear() + "");
			planMain.setMonth(new LocalDate().getMonthOfYear() + "");
			planMain.setStatus(1);
		} else { // 修改
			planMain = inspectPlanMainMgr.find(id);
			planMain.setPlanName(planMainForm.getPlanName());
			planMain.setCopyFlag(planMainForm.getCopyFlag());
			// planMain.setPartnerDeptId(planMainForm.getPartnerDeptId());
			planMain.setContent(planMainForm.getContent());
		}

		String deptMagId = partnerDeptMgr.getPartnerDept(
				planMain.getPartnerDeptId()).getDeptMagId();
		planMain.setDeptMagId(deptMagId);
		inspectPlanMainMgr.save(planMain);
		ActionForward actionForward = new ActionForward();
		actionForward
				.setPath("/inspectPlan.do?method=findInspectPlanMainList&sheet="
						+ sheet);
		actionForward.setRedirect(false);
		return actionForward;
	}

	/**
	 * 查询巡检计划关联巡检资源列表
	 */
	@SuppressWarnings("unchecked")
	public ActionForward findPlanResAssignList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		IInspectPlanResMgr inspectPlanResMgr = getInspectPlanResMgr();
		IInspectPlanMainMgr inspectPlanMainMgr = getInspectPlanMainMgr();
		String planId = request.getParameter("id");
		String assignFlag = request.getParameter("assignFlag"); // 资源关联情况
		InspectPlanMain planMain = inspectPlanMainMgr.find(planId);
		String specialty = planMain.getSpecialty();
		String deptMagId = planMain.getDeptMagId();
		final Integer pageSize = CommonConstants.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "list");
		final int pageIndex = Strings.isNullOrEmpty(pageIndexString) ? 0
				: Integer.valueOf(pageIndexString).intValue() - 1;

		// 查询与巡检计划专业相同的已关联的资源和没有关联计划的资源，且资源周期结束时间必须大于计划所在月的第一天
		String beginMonthDay = planMain.getYear() + "-" + planMain.getMonth()
				+ "-01 00:00:00";
		String whereStr = " where ";
		String transLine = StaticMethod.null2String(request
				.getParameter("transLine"));
		PnrDeviceInspectSwitchConfig transLineswitch = (PnrDeviceInspectSwitchConfig) request
				.getSession().getServletContext()
				.getAttribute("pnrInspect2SwitchConfig");
		if (planMain.getSpecialty().equals("1122502")
				&& transLineswitch.isOpenTransLineInspect()) {
			transLine = "yes";
			whereStr += "tlInspectFlag='1' and ";
		}
		// 基础查询语句
		beginMonthDay = CommonSqlHelper.formatDateTime(beginMonthDay);
		String baseQueryStr = " specialty='" + specialty
				+ "' and executeDept like '" + deptMagId + "%' ";

		// 如果开启工单巡检则可以关联未在变更中的临时元任务
		String cycleEndTimeEmpty = "(cycleEndTime is null)";
		String planStartTimeNotEmpty = " planStartTime is not null ";
		String planEndTimeNotEmpty = " planEndTime is not null";
		if (InspectConstants.getSheetInspectSwitch()) {// 突发任务周期开始结束时间为空
			baseQueryStr = baseQueryStr + " and (cycleEndTime>" + beginMonthDay
					+ " or (" + cycleEndTimeEmpty + " and "
					+ planStartTimeNotEmpty + " and " + planEndTimeNotEmpty
					+ "))";
		} else {
			baseQueryStr = baseQueryStr + " and cycleEndTime>" + beginMonthDay;
		}
		baseQueryStr = baseQueryStr + " and changeState=0"; // 非变更中的

		// 根据页面条件组装的查询语句，前台会根据此语句判断是否进行过查询
		String queryStr = "";
		queryStr = createResQueryHql(request, queryStr);

		// 拼接关联情况语句
		if (StringUtils.isEmpty(assignFlag)) {// 未关联和已关联
			baseQueryStr += " and (planId='" + planId
					+ "' or planId is null or planId='') ";
		} else if ("0".equals(assignFlag)) { // 未关联
			queryStr += " and (planId is null or planId='') ";
		} else if ("1".equals(assignFlag)) { // 已关联
			baseQueryStr += " and (planId='" + planId + "') ";
		}

		whereStr = whereStr + baseQueryStr + queryStr;
		Map map = (Map) inspectPlanResMgr.findInspectPlanResList(pageIndex,
				pageSize, whereStr);
		List list = (List) map.get("result");
		request.setAttribute("list", list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("planId", planId);
		request.setAttribute("specialty", specialty);
		request.setAttribute("planMain", planMain);
		request.setAttribute("cycleMap", InspectConstants.cycleMap);
		request.setAttribute("queryStr", queryStr);
		request.setAttribute("baseQueryStr", baseQueryStr);
		// 根据省ID获取地市
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		List citys = PartnerCityByUser.getCityByProvince(province);
		request.setAttribute("city", citys);

		// 保留页面查询条件
		request.setAttribute("resType", request.getParameter("resType"));
		request.setAttribute("resLevel", request.getParameter("resLevel"));
		request.setAttribute("inspectCycle",
				request.getParameter("inspectCycle"));
		request.setAttribute("assignFlag", request.getParameter("assignFlag"));
		request.setAttribute("transLine", transLine);
		return mapping.findForward("inspectPlanResAssignList");
	}

	/**
	 * 为巡检计划分配巡检资源
	 */
	@SuppressWarnings("unchecked")
	public ActionForward assignInspectPlanRes(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String planId = request.getParameter("planId");
		IInspectPlanResMgr inspectPlanResMgr = getInspectPlanResMgr();
		IInspectPlanMainMgr inspectPlanMainMgr = getInspectPlanMainMgr();
		InspectPlanMain planMain = inspectPlanMainMgr.find(planId);
		String assignType = request.getParameter("assignType");
		Map map = request.getParameterMap();
		Object[] idArray = (Object[]) map.get("checkboxId");

		// 如果是关联勾选项
		if ("check".equals(assignType)) {
			for (int i = 0; i < idArray.length; i++) {
				Long id = Long.parseLong(idArray[i].toString());
				InspectPlanRes inspectPlanRes = inspectPlanResMgr.get(id);
				inspectPlanRes.setPlanId(planId);
				inspectPlanResMgr.save(inspectPlanRes);
			}
		}
		// 如果是按查询条件关联
		else if ("query".equals(assignType)) {
			// 将查询条件作为更新条件
			String queryStr = request.getParameter("queryStr");
			String baseQueryStr = request.getParameter("baseQueryStr");
			String whereStr = baseQueryStr + queryStr;
			inspectPlanResMgr.updateInspectPlanResByHql(planId, whereStr);
		}
		// 如果是取消关联
		else if ("cancel".equals(assignType)) {
			for (int i = 0; i < idArray.length; i++) {
				Long id = Long.parseLong(idArray[i].toString());
				InspectPlanRes inspectPlanRes = inspectPlanResMgr.get(id);
				inspectPlanRes.setPlanId(null);
				// 如果已经细化了巡检日期后又取消关联，需要把巡检日期重新清空
				inspectPlanRes.setPlanStartTime(null);
				inspectPlanRes.setPlanEndTime(null);
				inspectPlanResMgr.save(inspectPlanRes);
			}
		}

		// 更新计划关联的巡检资源数目以及是否关联资源标识
		Integer count = inspectPlanResMgr.getPlanResAssignCount(planId);
		if (count > 0) {
			planMain.setResConfig(InspectConstants.YES);
		} else {
			planMain.setResConfig(InspectConstants.NO);
		}
		planMain.setResNum(count);
		inspectPlanMainMgr.save(planMain);

		ActionForward actionForward = new ActionForward();
		actionForward
				.setPath("/inspectPlan.do?method=findPlanResAssignList&id="
						+ planId);
		actionForward.setRedirect(false);
		return actionForward;
	}

	/**
	 * 查询需细化巡检资源列表(包含常规任务和突发任务)
	 */
	@SuppressWarnings("unchecked")
	public ActionForward findInspectPlanResDetailAllList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("id", request.getParameter("id"));
		request.setAttribute("sheet", request.getParameter("sheet")); // 是否工单菜单进入
		return mapping.findForward("inspectPlanResDetailAllList");
	}

	/**
	 * 查询需细化巡检资源列表(突发任务)
	 */
	@SuppressWarnings("unchecked")
	public ActionForward findInspectPlanResDetailBurstList(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		IInspectPlanResMgr inspectPlanResMgr = getInspectPlanResMgr();
		IInspectPlanMainMgr inspectPlanMainMgr = getInspectPlanMainMgr();
		String planId = request.getParameter("id");
		InspectPlanMain planMain = inspectPlanMainMgr.find(planId);
		String specialty = planMain.getSpecialty();
		final Integer pageSize = CommonConstants.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "list");
		final int pageIndex = Strings.isNullOrEmpty(pageIndexString) ? 0
				: Integer.valueOf(pageIndexString).intValue() - 1;
		PnrDeviceInspectSwitchConfig transLineswitch = (PnrDeviceInspectSwitchConfig) request
				.getSession().getServletContext()
				.getAttribute("pnrInspect2SwitchConfig");
		String transLine = "";
		if (planMain.getSpecialty().equals("1122502")
				&& transLineswitch.isOpenTransLineInspect()) {
			transLine = "yes";
		}
		String whereStr = " where 1=1 and planId='" + planId
				+ "' and burstFlag=1 ";
		whereStr = createResQueryHql(request, whereStr);
		Map map = (Map) inspectPlanResMgr.findInspectPlanResList(pageIndex,
				pageSize, whereStr);
		List list = (List) map.get("result");
		request.setAttribute("list", list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("planId", planId);
		request.setAttribute("planName", planMain.getPlanName());
		request.setAttribute("specialty", specialty);
		request.setAttribute("cycleMap", InspectConstants.cycleMap);
		request.setAttribute("sheet", request.getParameter("sheet")); // 是否工单菜单进入
		// 本月第一天
		String currentMonthStart = new LocalDate().dayOfMonth()
				.withMinimumValue().toString();
		// 本月最后一天
		String currentMonthEnd = new LocalDate().dayOfMonth()
				.withMaximumValue().toString();
		request.setAttribute("currentMonthStart", currentMonthStart);
		request.setAttribute("currentMonthEnd", currentMonthEnd);

		// 根据省ID获取地市
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		List citys = PartnerCityByUser.getCityByProvince(province);
		request.setAttribute("city", citys);
		request.setAttribute("transLine", transLine);
		return mapping.findForward("inspectPlanResDetailBurstList");
	}

	/**
	 * 查询需细化巡检资源列表(如果开启工单巡检则只查询常规周期任务)
	 */
	@SuppressWarnings("unchecked")
	public ActionForward findInspectPlanResDetailList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		IInspectPlanResMgr inspectPlanResMgr = getInspectPlanResMgr();
		IInspectPlanMainMgr inspectPlanMainMgr = getInspectPlanMainMgr();
		String planId = request.getParameter("id");
		InspectPlanMain planMain = inspectPlanMainMgr.find(planId);
		String specialty = planMain.getSpecialty();
		final Integer pageSize = CommonConstants.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "list");
		final int pageIndex = Strings.isNullOrEmpty(pageIndexString) ? 0
				: Integer.valueOf(pageIndexString).intValue() - 1;
		String transLine = "";
		PnrDeviceInspectSwitchConfig transLineswitch = (PnrDeviceInspectSwitchConfig) request
				.getSession().getServletContext()
				.getAttribute("pnrInspect2SwitchConfig");
		String whereStr = " where planId='" + planId + "' ";
		if (InspectConstants.getSheetInspectSwitch()) {// 如果开启工单巡检
			whereStr = whereStr + " and burstFlag=0 ";
		}
		if (planMain.getSpecialty().equals("1122502")
				&& transLineswitch.isOpenTransLineInspect()) {
			transLine = "yes";
		}
		whereStr = createResQueryHql(request, whereStr);
		Map map = (Map) inspectPlanResMgr.findInspectPlanResList(pageIndex,
				pageSize, whereStr);
		List list = (List) map.get("result");
		request.setAttribute("list", list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("planId", planId);
		request.setAttribute("planName", planMain.getPlanName());
		request.setAttribute("specialty", specialty);
		request.setAttribute("cycleMap", InspectConstants.cycleMap);
		request.setAttribute("sheet", request.getParameter("sheet")); // 是否工单菜单进入
		// 本月第一天
		String currentMonthStart = new LocalDate().dayOfMonth()
				.withMinimumValue().toString();
		// 本月最后一天
		String currentMonthEnd = new LocalDate().dayOfMonth()
				.withMaximumValue().toString();
		request.setAttribute("currentMonthStart", currentMonthStart);
		request.setAttribute("currentMonthEnd", currentMonthEnd);

		// 根据省ID获取地市
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		List citys = PartnerCityByUser.getCityByProvince(province);
		request.setAttribute("city", citys);
		request.setAttribute("transLine", transLine);
		return mapping.findForward("inspectPlanResDetailList");
	}

	/**
	 * 查询所有巡检资源列表
	 */
	@SuppressWarnings("unchecked")
	public ActionForward findInspectPlanResList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		IInspectPlanResMgr inspectPlanResMgr = getInspectPlanResMgr();
		String specialty = request.getParameter("resSpecialty");// 获取巡检计划所属专业
		final Integer pageSize = CommonConstants.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "list");
		final int pageIndex = Strings.isNullOrEmpty(pageIndexString) ? 0
				: Integer.valueOf(pageIndexString).intValue() - 1;
		String whereStr = " where 1=1 ";
		String transLine = StaticMethod.null2String(request
				.getParameter("transLine"));
		if ("yes".equals(transLine)) {
			whereStr += " and specialty = '1122502'";
		} else {
			if (InspectConstants.getSwitchConfig().isOpenTransLineInspect()) { // 打开线路巡检
				whereStr += " and specialty != '1122502'";
			}
		}
		
//		权限增加-start
		whereStr += " and executeObject != null and inspectCycle !=null";
		
		HashMap<String, String> mapfMap = (HashMap<String, String>)PartnerPrivUtils.userIsPersonnel(request);
		String flag = mapfMap.get("isPersonnel");
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		if(flag.equals("y")){  //是代维人员
			PartnerDeptMgr partnerDeptMgr=(PartnerDeptMgr)getBean("partnerDeptMgr");
			List<PartnerDept> dept = partnerDeptMgr.getPartnerDepts("and deptMagId='"+sessionForm.getDeptid()+"'");
			whereStr += " and country like '"+dept.get(0).getAreaId()+"%'";
			whereStr += " and executeDept like '"+sessionForm.getDeptid()+"%'";

		}else if(!"admin".equals(sessionForm.getUserid())){//是移动人员
			String aa=sessionForm.getDeptid();
			TawSystemDeptBo deptbo = TawSystemDeptBo.getInstance();
			TawSystemDept sept = deptbo.getDeptinfobydeptid(aa, "0");
			whereStr += " and country like '"+sept.getAreaid()+"%'";//由于移动人员没有权限分配执行对象，当前不能查出数据
		}
//		权限增加-end
		whereStr = createResQueryHql(request, whereStr);
		Map map = (Map) inspectPlanResMgr.findInspectPlanResList(pageIndex,
				pageSize, whereStr);
		List list = (List) map.get("result");
		request.setAttribute("list", list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("specialty", specialty);
		request.setAttribute("cycleMap", InspectConstants.cycleMap);
		// 根据省ID获取地市
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		List citys = PartnerCityByUser.getCityByProvince(province);
		request.setAttribute("city", citys);

		request.setAttribute("resName", request.getParameter("resName"));
		request.setAttribute("resType", request.getParameter("resType"));
		request.setAttribute("resLevel", request.getParameter("resLevel"));
		request.setAttribute("country", request.getParameter("country"));
		request.setAttribute("inspectCycle",
				request.getParameter("inspectCycle"));
		request.setAttribute("mycity", request.getParameter("city"));
		request.setAttribute("resCreateTime",
				request.getParameter("resCreateTime"));
		request.setAttribute("transLine", transLine);
		return mapping.findForward("inspectPlanResList");
	}

	/**
	 * 查看巡检项详情
	 */
	public ActionForward goToInspectPlanMainItemList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		IInspectPlanItemMgr inspectPlanItemMgr = (IInspectPlanItemMgr) getBean("inspectPlanItemMgrImpl");
		String plan_res_id = request.getParameter("planResId");

		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(),
				search);
		String detail = request.getParameter("detail");
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"inspectPlanItemList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.addSortAsc("exceptionFlag");
		search.addSortDesc("saveTime");
		search.addFilterEqual("planResId", plan_res_id);
		/* 查看公共巡检项 add by zhangkeqi 2013-02-26 begin */
		search.addFilter(new Filter().or(
				new Filter().notEqual("deviceInspectFlag", 1),
				new Filter().isNull("deviceInspectFlag")));
		/* 查看公共巡检项 add by zhangkeqi 2013-02-26 end */

		SearchResult<InspectPlanItem> returnSearch = inspectPlanItemMgr
				.searchAndCount(search);

		List<InspectPlanItem> inspectPlanItemList = returnSearch.getResult();
		IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
		InspectPlanRes res = inspectPlanResMgr.get(Long.parseLong(plan_res_id));
		if (inspectPlanItemList.isEmpty()) {
			request.setAttribute("resultSize", 0);
		} else {
			InspectPlanItemForm inspectPlanItemForm;
			List<InspectPlanItemForm> returnList = new ArrayList<InspectPlanItemForm>();
			for (int i = 0; i < inspectPlanItemList.size(); i++) {
				inspectPlanItemForm = new InspectPlanItemForm();
				try {
					BeanUtils.copyProperties(inspectPlanItemForm,
							inspectPlanItemList.get(i));
					if(res.getPlanEndTime()!=null){
					inspectPlanItemForm.setEndTime((res.getPlanEndTime() + "")
							.substring(0,
									(res.getPlanEndTime() + "").length() - 2));
					}else{
						inspectPlanItemForm.setEndTime("");
					}
					// String dictIdTemp =
					// inspectPlanItemList.get(i).getItemResult();
					returnList.add(inspectPlanItemForm);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			request.setAttribute("inspectPlanItemList", returnList);
			request.setAttribute("resultSize", returnSearch.getTotalCount());
		}
		IInspectPlanExecuteMgr executeMgr = (IInspectPlanExecuteMgr) getBean("inspectPlanExecuteMgrImpl");
		request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
		request.setAttribute("planResId", plan_res_id);
		request.setAttribute("planId", res.getPlanId());
		request.setAttribute("InspectPlanRes", res);
		request.setAttribute(
				"isCheckUser",
				executeMgr.isCheckUser(getUser(request).getDeptid(),
						res.getExecuteDept()));
		if ("detail".equals(detail)) {
			return mapping.findForward("inspectPlanItemListByCheckUserDetai");
		} else {
			return mapping.findForward("inspectPlanItemListByCheckUser");
		}
	}

	public ActionForward goToDeviceInspectList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, Exception {
		String detail = StaticMethod
				.null2String(request.getParameter("detail"));

		PnrInspectTaskLinkService pitls = (PnrInspectTaskLinkService) getBean("pnrInspectTaskLinkService");
		String planResId = StaticMethod.null2String(request
				.getParameter("planResId"));

		Search search = new Search();
		int firstResult = CommonUtils.getFirstResultOfDisplayTag(request,
				"deviceInspectList");
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		search = CommonUtils.getSqlFromRequestMap(request, search);
		search.addFilterEqual("planResId", planResId);

		SearchResult searchResult = pitls.searchAndCount(search);
		List deviceInspectList = searchResult.getResult();

		request.setAttribute("deviceInspectList", deviceInspectList);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", searchResult.getTotalCount());
		/*
		 * if("detail".equals(detail)){ return
		 * mapping.findForward("inspectPlanItemListByCheckUserDetai"); }
		 */

		IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
		InspectPlanRes inspectPlanRes = inspectPlanResMgr.get(Long
				.parseLong(planResId));
		if (inspectPlanRes.getSpecialty().equals("1122502")) {
			request.setAttribute("transLine", "yes");
		}
		request.setAttribute("detail", detail);
		return mapping.findForward("goToDeviceInspectList");

	}

	/**
	 * 根据页面查询条件拼装HQL语句
	 * 
	 * @param request
	 * @return
	 */
	private String createResQueryHql(HttpServletRequest request, String hql) {
		if (!StringUtils.isEmpty(request.getParameter("resName"))) {
			hql += " and resName like '%" + request.getParameter("resName")
					+ "%'";
		}
		if (!StringUtils.isEmpty(request.getParameter("resType"))) {
			hql += " and resType='" + request.getParameter("resType") + "'";
		}
		if (!StringUtils.isEmpty(request.getParameter("resLevel"))) {
			hql += " and resLevel='" + request.getParameter("resLevel") + "'";
		}
		if (!StringUtils.isEmpty(request.getParameter("city"))) {
			hql += " and city='" + request.getParameter("city") + "'";
		}
		if (!StringUtils.isEmpty(request.getParameter("country"))) {
			hql += " and country='" + request.getParameter("country") + "'";
		}
		if (!StringUtils.isEmpty(request.getParameter("inspectCycle"))) {
			hql += " and inspectCycle='" + request.getParameter("inspectCycle")
					+ "'";
		}
		if (!StringUtils.isEmpty(request.getParameter("resSpecialty"))) {
			hql += " and specialty='" + request.getParameter("resSpecialty")
					+ "'";
		}
		if (!StringUtils.isEmpty(request.getParameter("resCreateTime"))) {
			hql += " and createTime like '"
					+ request.getParameter("resCreateTime") + "%'";
		}
		if (!StringUtils.isEmpty(request.getParameter("tlDis"))) {
			hql += " and tlDis like '" + request.getParameter("tlDis") + "%'";
		}
		if (!StringUtils.isEmpty(request.getParameter("tlWire"))) {
			hql += " and tlWire like '" + request.getParameter("tlWire") + "%'";
		}
		if (!StringUtils.isEmpty(request.getParameter("tlSeg"))) {
			hql += " and tlSeg like '" + request.getParameter("tlSeg") + "%'";
		}
		return hql;
	}

	/**
	 * 任务细化，跳转到设置巡检资源巡检起始结束时间页面
	 */
	@SuppressWarnings("unchecked")
	public ActionForward toInspectPlanResTimeCfg(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String resId = request.getParameter("resId");
		request.setAttribute("resId", resId);
		String[] resIdArr = resId.split("\\u0024"); // \\u0024为$符号的转义符
		boolean isWeekCycle = false; // 默认巡检资源周期不为周
		InspectPlanRes inspectPlanRes = null;
		// 能设置的巡检开始、结束日期最大、最小值
		Date minConfigDate = null;
		Date maxConfigDate = null;
		// 由于周期为周的资源不能进行批量设置，所以如果是多个资源周期一定不为周，只有一个资源需要进行判断
		if (resIdArr.length == 1) {
			IInspectPlanResMgr inspectPlanResMgr = getInspectPlanResMgr();
			inspectPlanRes = inspectPlanResMgr.get(Long.valueOf(Long
					.valueOf(resId)));			
			/*String inspectCycle = inspectPlanRes.getInspectCycle();
			if (inspectCycle.equals(InspectConstants.PERIOD_OF_WEEK)) {
				isWeekCycle = true;
				LocalDate cycleEndDate = new LocalDate(
						inspectPlanRes.getCycleEndTime());
				LocalDate currentDate = new LocalDate();
				if (cycleEndDate.getMonthOfYear() > currentDate
						.getMonthOfYear()) {
					maxConfigDate = new DateTime(currentDate.dayOfMonth()
							.withMaximumValue().toString()
							+ "T23:59:59").toDate();
				} else {
					maxConfigDate = inspectPlanRes.getCycleEndTime();
				}
				minConfigDate = inspectPlanRes.getCycleStartTime();

			}*/
			
			
			//细化日期时的限制改动 2013-08-27
			minConfigDate = inspectPlanRes.getCycleStartTime();
			maxConfigDate = inspectPlanRes.getCycleEndTime();
		}else
		{
			LocalDate currentDate = new LocalDate();

			minConfigDate=new DateTime(currentDate.toString()).toDate();
			maxConfigDate=new DateTime(currentDate.dayOfMonth().withMaximumValue().toString()).toDate();
			
		}
		
		//  细化日期时的限制改动 2013-08-27
		 
		request.setAttribute("minConfigDate",minConfigDate);
		request.setAttribute("maxConfigDate",maxConfigDate);
/*		request.setAttribute("minConfigDate",
				InspectUtils.getMinConfigDate(isWeekCycle, minConfigDate));
		request.setAttribute("maxConfigDate",
				InspectUtils.getMaxConfigDate(isWeekCycle, maxConfigDate));
*/		return mapping.findForward("inspectPlanResTimeCfg");
	}

	/**
	 * 突发任务任务细化，跳转到设置巡检资源巡检起始结束时间页面
	 */
	@SuppressWarnings("unchecked")
	public ActionForward toInspectPlanResTimeCfgBurst(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String resId = request.getParameter("resId");
		request.setAttribute("resId", resId);
		return mapping.findForward("inspectPlanResTimeCfgBurst");
	}

	/**
	 * 设置巡检资源巡检起始结束时间
	 */
	@SuppressWarnings("unchecked")
	public ActionForward cfgInspectPlanResTime(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		IInspectPlanResMgr inspectPlanResMgr = getInspectPlanResMgr();
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String resId = request.getParameter("resId");
		String[] resIdArr = resId.split("\\u0024"); // \\u0024为$符号的转义符
		InspectPlanRes inspectPlanRes;
		for (String id : resIdArr) {
			inspectPlanRes = inspectPlanResMgr.get(Long.valueOf(id));
			inspectPlanRes.setPlanStartTime(new DateTime(startTime
					+ "T00:00:00").toDate());
			inspectPlanRes.setPlanEndTime(new DateTime(endTime + "T23:59:59")
					.toDate());
			inspectPlanResMgr.save(inspectPlanRes);
		}

		return mapping.findForward("success");
	}

	/**
	 * 跳转到审批对象选择页面
	 */
	public ActionForward toInspectPlanApproveObject(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String planId = request.getParameter("planId");
		String isChangePlan = request.getParameter("isChangePlan"); // 是否是变更审批
		String planChgId = request.getParameter("planChgId"); // 是否是变更审批
		IInspectPlanMgr inspectPlanMgr = (IInspectPlanMgr) ApplicationContextHolder
				.getInstance().getBean("inspectPlanMgr");
		String approveObjectJson = inspectPlanMgr.getApproveObjectJson(planId);
		request.setAttribute("approveObjectJson", approveObjectJson);
		request.setAttribute("planId", planId);
		request.setAttribute("isChangePlan", isChangePlan);
		request.setAttribute("planChgId", planChgId);
		return mapping.findForward("inspectPlanApproveObjectForm");
	}

	/**
	 * 提交巡检计划到审批对象
	 */
	@SuppressWarnings("unchecked")
	public ActionForward commitInspectPlanToApprover(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		IInspectPlanMainMgr inspectPlanMainMgr = getInspectPlanMainMgr();
		String planId = request.getParameter("planId");
		InspectPlanMain planMain = inspectPlanMainMgr.find(planId);
		String approveObject = request.getParameter("approveObject");
		String approveObjectTypeStr = request.getParameter("approveObjectType");
		String isChangePlan = request.getParameter("isChangePlan");
		String planChgId = request.getParameter("planChgId");

		String approveObjectType = "";
		if ("user".equals(approveObjectTypeStr)) {
			approveObjectType = "0";
		} else if ("role".equals(approveObjectTypeStr)) {
			approveObjectType = "1";
		}

		if (StringUtils.isEmpty(isChangePlan)) {
			if (0 == planMain.getResNum()) {
				response.getWriter().print("false");
				return null;
			}
			planMain.setApproveObject(approveObject);
			planMain.setApproveObjectType(approveObjectType);
			planMain.setApproveStatus(2);
			inspectPlanMainMgr.save(planMain);
		} else {
			IInspectPlanChangeMgr inspectPlanChangeMgr = (IInspectPlanChangeMgr) getBean("inspectPlanChangeMgr");
			IInspectPlanResChangeMgr inspectPlanResChangeMgr = (IInspectPlanResChangeMgr) getBean("inspectPlanResChangeMgr");
			Search search = new Search();
			search.addFilterEqual("planChangeId", planChgId);
			List<InspectPlanResChange> resChgList = inspectPlanResChangeMgr
					.search(search);
			if (resChgList == null || resChgList.size() == 0) {
				response.getWriter().print("false");
				return null;
			}
			InspectPlanChange inspectPlanChange = inspectPlanChangeMgr
					.find(planChgId);
			inspectPlanChange.setApproveObject(approveObject);
			inspectPlanChange.setApproveObjectType(approveObjectType);
			inspectPlanChange.setState(2);
			inspectPlanChangeMgr.save(inspectPlanChange);
		}

		return null;
	}

	/**
	 * 查询巡检计划审批列表
	 */
	public ActionForward findInspectPlanMainApproveList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		IInspectPlanMainMgr inspectPlanMainMgr = getInspectPlanMainMgr();
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();

		final Integer pageSize = CommonConstants.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "list");
		final int pageIndex = Strings.isNullOrEmpty(pageIndexString) ? 0
				: Integer.valueOf(pageIndexString).intValue() - 1;

		String hql = "from InspectPlanMain m where m.approveStatus=2 ";

		if (!"admin".equals(userId)) {
			hql += " and (m.approveObject='"
					+ userId
					+ "' or m.approveObject in (select r.subRoleid from TawSystemUserRefRole r where r.userid='"
					+ userId + "')) ";
		}
		if (!StringUtils.isEmpty(request.getParameter("planName"))) {
			hql += " and m.planName like '%" + request.getParameter("planName")
					+ "%'";
		}
		if (!StringUtils.isEmpty(request.getParameter("specialty"))) {
			hql += " and m.specialty='" + request.getParameter("specialty")
					+ "'";
		}
		if (!StringUtils.isEmpty(request.getParameter("partnerDeptId"))) {
			hql += " and m.partnerDeptId='"
					+ request.getParameter("partnerDeptId") + "'";
		}
		hql += " order by m.createTime desc";

		PageModel pm = inspectPlanMainMgr.findInspectPlanMainApproveList(hql,
				new Object[] {}, pageIndex * pageSize, pageSize);
		request.setAttribute("list", pm.getDatas());
		request.setAttribute("size", pm.getTotal());
		request.setAttribute("pagesize", pageSize);

		request.setAttribute("yesOrNoMap", InspectConstants.yesOrNoMap);
		request.setAttribute("approveStatusMap",
				InspectConstants.approveStatusMap);

		return mapping.findForward("inspectPlanApproveList");
	}

	/**
	 * 跳转到巡检计划审批页面
	 */
	public ActionForward toApproveInspectPlan(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		IInspectPlanMainMgr inspectPlanMainMgr = getInspectPlanMainMgr();
		String id = request.getParameter("id");
		InspectPlanMain planMain = inspectPlanMainMgr.find(id);
		request.setAttribute("planMain", planMain);
		return mapping.findForward("inspectPlanApproveForm");
	}

	/**
	 * 审批巡检计划
	 */
	public ActionForward approveInspectPlan(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		IInspectPlanApproveMgr inspectPlanApproveMgr = (IInspectPlanApproveMgr) getBean("inspectPlanApproveMgr");
		IInspectPlanMainMgr inspectPlanMainMgr = getInspectPlanMainMgr();
		IInspectPlanResMgr inspectPlanResMgr = getInspectPlanResMgr();
		String planId = request.getParameter("id");
		InspectPlanMain planMain = inspectPlanMainMgr.find(planId);

		String approveStatusStr = request.getParameter("approveStatus");
		Integer approveStatus = Integer.parseInt(approveStatusStr);
		String approveIdea = request.getParameter("approveIdea");
		planMain.setApproveStatus(approveStatus);

		// 如果审批通过
		if (approveStatus.equals(InspectConstants.APPROVE_STATUS_PASSED)) {
			int currentMonth = new LocalDate().getMonthOfYear(); // 当前月
			// 本月第一天
			String currentMonthStart = new LocalDate().dayOfMonth()
					.withMinimumValue().toString()
					+ " 00:00:00";
			// 本月最后一天
			String currentMonthEnd = new LocalDate().dayOfMonth()
					.withMaximumValue().toString()
					+ " 23:59:59";
			inspectPlanResMgr.updateInspectPlanResPlanTime(planId,
					currentMonthStart, currentMonthEnd, currentMonth + "");
		}
		inspectPlanMainMgr.save(planMain);

		InspectPlanApprove approve = new InspectPlanApprove();
		approve.setPlanId(planId);
		approve.setApproveIdea(approveIdea);
		approve.setApproveStatus(approveStatus);
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		approve.setApprover(sessionForm.getUserid());
		approve.setApproverDept(sessionForm.getDeptid());
		approve.setApproveTime(new Date());
		approve.setPlanType(1);
		inspectPlanApproveMgr.save(approve);

		// ActionForward actionForward = new ActionForward();
		// actionForward.setPath("/inspectPlan.do?method=findInspectPlanMainApproveList");
		// actionForward.setRedirect(false);
		// return actionForward;
		return mapping.findForward("success");
	}

	/**
	 * 审批巡检计划（直接审批）
	 */
	public ActionForward approveInspectPlan2(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		IInspectPlanApproveMgr inspectPlanApproveMgr = (IInspectPlanApproveMgr) getBean("inspectPlanApproveMgr");
		IInspectPlanMainMgr inspectPlanMainMgr = getInspectPlanMainMgr();
		IInspectPlanResMgr inspectPlanResMgr = getInspectPlanResMgr();
		String planId = request.getParameter("id");
		InspectPlanMain planMain = inspectPlanMainMgr.find(planId);

		String approveStatusStr = request.getParameter("approveStatus");
		Integer approveStatus = Integer.parseInt(StringUtils
				.isEmpty(approveStatusStr) ? "1" : approveStatusStr);
		planMain.setApproveStatus(approveStatus);

		String approveIdea = request.getParameter("approveIdea");

		// 如果审批通过
		if (approveStatus.equals(InspectConstants.APPROVE_STATUS_PASSED)) {
			int currentMonth = new LocalDate().getMonthOfYear(); // 当前月
			// 本月第一天
			String currentMonthStart = new LocalDate().dayOfMonth()
					.withMinimumValue().toString()
					+ " 00:00:00";
			// 本月最后一天
			String currentMonthEnd = new LocalDate().dayOfMonth()
					.withMaximumValue().toString()
					+ " 23:59:59";
			// 更改之后currentMonthStart，currentMonthEnd无作用。2013-08-27
			inspectPlanResMgr.updateInspectPlanResPlanTime(planId,
					currentMonthStart, currentMonthEnd, currentMonth + "");
		}
		inspectPlanMainMgr.save(planMain);

		InspectPlanApprove approve = new InspectPlanApprove();
		approve.setPlanId(planId);
		approve.setApproveIdea(StringUtils.isEmpty(approveIdea) ? "审批通过"
				: approveIdea);
		approve.setApproveStatus(approveStatus);
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		approve.setApprover(sessionForm.getUserid());
		approve.setApproverDept(sessionForm.getDeptid());
		approve.setApproveTime(new Date());
		approve.setPlanType(1);
		inspectPlanApproveMgr.save(approve);

		// ActionForward actionForward = new ActionForward();
		// actionForward.setPath("/inspectPlan.do?method=findInspectPlanMainApproveList");
		// actionForward.setRedirect(false);
		// return actionForward;
		// String strP=request.getParameter((new
		// ParamEncoder("list").encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		// String pageIndexString =
		// CommonUtils.getPageIndexWithDisplaytag(request,"list");
		// ActionForward actionForward = new ActionForward();
		// actionForward.setPath("../inspect/inspectPlan.do?method=findInspectPlanMainList");
		// actionForward.setRedirect(true);
		// return actionForward;
		response.getWriter().print("true");
		return null;
		// return mapping.findForward("success");
	}

	private IInspectPlanMainMgr getInspectPlanMainMgr() {
		return (IInspectPlanMainMgr) getBean("inspectPlanMainMgr");
	}

	private IInspectPlanResMgr getInspectPlanResMgr() {
		return (IInspectPlanResMgr) getBean("inspectPlanResMgr");
	}
}
