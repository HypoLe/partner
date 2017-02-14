package com.boco.eoms.deviceManagement.busi.network.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.partner.baseinfo.model.PartnerUser;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.baseInfo.model.CheckPoint;
import com.boco.eoms.deviceManagement.baseInfo.service.CheckPointService;
import com.boco.eoms.deviceManagement.busi.network.model.HiddenTrouble;
import com.boco.eoms.deviceManagement.busi.network.service.HiddenTroubleService;
import com.boco.eoms.deviceManagement.busi.network.util.HiddenTroubleConst;
import com.boco.eoms.deviceManagement.busi.protectline.model.ConsctRouting;
import com.boco.eoms.deviceManagement.busi.protectline.model.ProteclineLink;
import com.boco.eoms.deviceManagement.busi.protectline.service.ConsctRoutingService;
import com.boco.eoms.deviceManagement.busi.protectline.service.ProteclineLinkService;
import com.boco.eoms.deviceManagement.busi.protectline.util.ConstructionMap;
import com.boco.eoms.deviceManagement.common.pojo.TdObjModel;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcService;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public final class HiddenTroubleAction extends BaseAction {

	public ConsctRoutingService getConsctRoutingServiceBean() {
		String source = ConsctRoutingService.class.getSimpleName();
		return (ConsctRoutingService) getBean(source.substring(0, 1)
				.toLowerCase().concat(source.substring(1)));
	}

	public CheckPointService getCheckPointBean() {
		String source = CheckPointService.class.getSimpleName();
		return (CheckPointService) getBean(source.substring(0, 1)
				.toLowerCase().concat(source.substring(1)));
	}
	
	public ProteclineLinkService getProteclineLinkServiceBean() {
		String source = ProteclineLinkService.class.getSimpleName();
		return (ProteclineLinkService) getBean(source.substring(0, 1)
				.toLowerCase().concat(source.substring(1)));
	}
	
	public HiddenTroubleService getHiddenTroubleServiceBean() {
		String source = HiddenTroubleService.class.getSimpleName();
		return (HiddenTroubleService) getBean(source.substring(0, 1)
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

	public ActionForward goToAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CheckPointService cks = this.getCheckPointBean();
		
		List<CheckPoint> cpList = cks.findAll();
		
		request.setAttribute("cpList", cpList);
		
		return mapping.findForward("goToAdd");
	}
	
	/**
	 * 添加
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "finally" })
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		try{
			Map<String,String> messageMap = new HashMap<String,String>();
			
			HiddenTroubleService htService = (HiddenTroubleService) this
			.getHiddenTroubleServiceBean();
			Map requestMap = request.getParameterMap();
			
			HiddenTrouble ht = new HiddenTrouble();
			BeanUtils.populate(ht, requestMap);
			
			TawSystemSessionForm sessionform = this.getUser(request);
			String userId = sessionform.getUserid();
			String reportTime = CommonUtils.toEomsStandardDate(new Date());
			
//			//设置巡检员和其代维公司
//			PartnerUserMgr pum = (PartnerUserMgr)ApplicationContextHolder.getInstance().getBean("partnerUserMgr");
//			List puList = pum.getPartnerUsers(" and userId='"+ht.getCheckUser()+"'");
//			
//			if(puList != null && puList.size()>0) {
//				PartnerUser pu = (PartnerUser)puList.get(0);
//				PartnerDeptMgr pdm = (PartnerDeptMgr)ApplicationContextHolder.getInstance().getBean("partnerDeptMgr");
//				PartnerDept pd = (PartnerDept)pdm.getPartnerDept(pu.getPartnerid());
//				if(pd != null) {
//					ht.setCheckUserDept(pd.getId());
//				}
//			} else {
//				messageMap.put("infor", "选择的人员不是代维人员，请选择代维人员！");
//				this.responseJSONMessage(response, messageMap, false);
//				return null;
//			}
			
			ht.setReportUser(userId);
			ht.setReportTime(reportTime);
			ht.setDeleted(HiddenTroubleConst.NOT_DELETE);
			ht.setProcessStatus(HiddenTroubleConst.NOT_PROCESS);
			
			htService.save(ht);
			
			messageMap.put("infor", "添加成功！");
			this.responseJSONMessage(response, messageMap, true);

		} catch (RuntimeException e) {
			Map<String,String> messageMap = new HashMap<String,String>();
			e.printStackTrace();
			messageMap.put("infor", "添加出错！");
			this.responseJSONMessage(response, messageMap, false);
		} finally {
			return null;
		}
	}


	/**
	 * 详情转向
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward goToDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		HiddenTrouble hiddenTrouble = this.getHiddenTroubleServiceBean().find(id);
		request.setAttribute("hiddenTrouble", hiddenTrouble);
		return mapping.findForward("goToDetail");
	}
	/**
	 * 处理页面转向
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward goToProcess(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String id = request.getParameter("id");
		HiddenTrouble hiddenTrouble = this.getHiddenTroubleServiceBean().find(id);
		request.setAttribute("hiddenTrouble", hiddenTrouble);
		return mapping.findForward("goToProcess");
	}
	/**
	 * 隐患处理
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward troubleProcess(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		HiddenTroubleService htService = (HiddenTroubleService) this
		.getHiddenTroubleServiceBean();
		
		Map requestMap = request.getParameterMap();
		HiddenTrouble ht = new HiddenTrouble();
		BeanUtils.populate(ht, requestMap);
		
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
		
		ht.setProcessStatus(HiddenTroubleConst.PROCESSED);//处理状态
		ht.setProcessUser(userId);//处理人
		ht.setHandlTime(CommonUtils.toEomsStandardDate(new Date()));//处理时间
		
		htService.save(ht);
		
		return this.handleList(mapping, form, request, response);
	}

	
	/**
	 * 编辑转向
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward goToEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		HiddenTrouble hiddenTrouble = this.getHiddenTroubleServiceBean().find(id);
		request.setAttribute("hiddenTrouble", hiddenTrouble);
		
		CheckPointService cks = this.getCheckPointBean();
		List<CheckPoint> cpList = cks.findAll();
		request.setAttribute("cpList", cpList);
		return mapping.findForward("goToEdit");
	}

	/**
	 * 编辑
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
		HiddenTroubleService htService = (HiddenTroubleService) this
		.getHiddenTroubleServiceBean();
		Map requestMap = request.getParameterMap();
		
		HiddenTrouble ht = new HiddenTrouble();
		BeanUtils.populate(ht, requestMap);

		//重置巡检员和其代维公司
		PartnerUserMgr pum = (PartnerUserMgr)ApplicationContextHolder.getInstance().getBean("partnerUserMgr");
		PartnerUser pu = pum.getPartnerUser(ht.getCheckUser());
		
		if(pu != null) {
			PartnerDeptMgr pdm = (PartnerDeptMgr)ApplicationContextHolder.getInstance().getBean("partnerDeptMgr");
			PartnerDept pd = (PartnerDept)pdm.getPartnerDept(pu.getPartnerid());
			if(pd != null) {
				ht.setCheckUserDept(pd.getId());
			}
		}
		
		return this.forwardlist(mapping);
	}

	/**
	 * 删除
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("finally")
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		response.setCharacterEncoding("utf-8");
		try {
			String id = request.getParameter("id");
			HiddenTrouble hiddenTrouble = this.getHiddenTroubleServiceBean().find(id);
			this.getHiddenTroubleServiceBean().remove(hiddenTrouble);

			response.getWriter().write(
					new Gson()
							.toJson(new ImmutableMap.Builder<String, String>()
									.put("success", "true").put("msg", "ok")
									.put("infor", "删除成功！").build()));

		} catch (RuntimeException e) {
			BocoLog.info(this, "删除出错！");
			e.printStackTrace();
			response.getWriter().write(
					new Gson()
							.toJson(new ImmutableMap.Builder<String, String>()
									.put("success", "true").put("msg", "ok")
									.put("infor", "删除出错！").build()));
		} finally {
			return null;
		}
	}


	/**
	 * 列表页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Search search = new Search();
		
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(),
				search);

		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"hiddenTroubleList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);

		search.addSortDesc("reportTime");
		SearchResult<HiddenTrouble> searchResult = this
				.getHiddenTroubleServiceBean().searchAndCount(search);

		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("hiddenTroubleList", searchResult.getResult());
		request.setAttribute("size", searchResult.getTotalCount());
		
		return mapping.findForward("list");
	}
	/**
	 * 待处理列表页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward handleList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Search search = new Search();
		
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(),
				search);
		
		search.addFilterEqual("processStatus", HiddenTroubleConst.NOT_PROCESS);
		
		String pageIndexString = request
		.getParameter((new org.displaytag.util.ParamEncoder(
		"hiddenTroubleList")
		.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		
		search.addSortDesc("reportTime");
		SearchResult<HiddenTrouble> searchResult = this
		.getHiddenTroubleServiceBean().searchAndCount(search);
		
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("hiddenTroubleList", searchResult.getResult());
		request.setAttribute("size", searchResult.getTotalCount());
		
		return mapping.findForward("handleList");
	}

	/**
	 * 转向至审核页
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward goToApproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		ConsctRouting route = this.getConsctRoutingServiceBean().find(id);

		ProteclineLinkService linkService = (ProteclineLinkService) this
				.getProteclineLinkServiceBean();
		Search search = new Search();
		search.addFilterEqual("mainId", id);
		search.addFilterEqual("mainType", "1");
		search.addSortDesc("actionHappenTime");
		SearchResult<ProteclineLink> searchResult = linkService
				.searchAndCount(search);

		request.setAttribute("consctRouting", route);
		request.setAttribute("consctRoutingApprovalList", searchResult
				.getResult());
		request.setAttribute("resultSize", searchResult.getTotalCount());

		return mapping.findForward("goToApproval");
	}

	/**
	 * 审核
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward approval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
		String creatTime = CommonUtils.toEomsStandardDate(new Date());

		ConsctRoutingService routeService = (ConsctRoutingService) this
				.getConsctRoutingServiceBean();

		ProteclineLink link = new ProteclineLink();
		BeanUtils.populate(link, request.getParameterMap());
		link.setActionSendUser(userId);
		link.setActionHappenTime(creatTime);

		String currentStatus = request.getParameter("currentStatus");
		// 审核通过
		if ("2".equals(currentStatus)) {
			link.setCurrentStatus(ConstructionMap.STATUS_2);
			link.setActionStepExplain("审核通过，归档！");

			String id = request.getParameter("id");
			if (!"".equals(request.getParameter("id"))) {
				ConsctRouting route = this.getConsctRoutingServiceBean().find(
						id);
				route.setApprovalUser(userId);
				route.setStatus(ConstructionMap.STATUS_2);

				routeService.save(route, link);
			}
		}
		// 驳回
		if ("3".equals(currentStatus)) {
			String id = request.getParameter("id");
			ConsctRouting route = this.getConsctRoutingServiceBean().find(id);

			link.setCurrentStatus(ConstructionMap.STATUS_3);
			link.setActionStepExplain("审核不通过，驳回！");
			link.setActionReceiveUser(route.getCreatUser());

			if (!"".equals(request.getParameter("id"))) {
				route.setApprovalUser(userId);
				route.setStatus(ConstructionMap.STATUS_3);

				routeService.save(route, link);
			}
		}
		return forwardlist(mapping);
	}

	/**
	 * 草稿列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward draftList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();

		Search search = new Search();
		search.addFilterEqual("creatUser", userId);
		search.addFilterEqual("status", "0");
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(),
				search);
		search.addSortDesc("creatTime");

		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"consctRoutingList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);

		SearchResult<ConsctRouting> searchResult = this
				.getConsctRoutingServiceBean().searchAndCount(search);

		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("consctRoutingList", searchResult.getResult());
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("draftList");
	}

	/**
	 * 待审核列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward approvalList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();

		Search search = new Search();
		search.addFilterEqual("approvalUser", userId);
		search.addFilterEqual("status", "1");
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(),
				search);
		search.addSortDesc("creatTime");

		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"consctRoutingList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);

		SearchResult<ConsctRouting> searchResult = this
				.getConsctRoutingServiceBean().searchAndCount(search);

		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("consctRoutingList", searchResult.getResult());
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("approvalList");
	}

	/**
	 * 已审核列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward approvaledList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();

		Search search = new Search();
		search.addFilterEqual("creatUser", userId);
		search.addFilterEqual("status", "2");
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(),
				search);
		search.addSortDesc("creatTime");

		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"consctRoutingList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);

		SearchResult<ConsctRouting> searchResult = this
				.getConsctRoutingServiceBean().searchAndCount(search);

		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("consctRoutingList", searchResult.getResult());
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("approvaledList");
	}

	/**
	 * 审核驳回列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward approvalRebuteList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();

		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(),
				search);
		search.addFilterEqual("creatUser", userId);
		search.addFilterEqual("status", "3");
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(),
				search);
		search.addSortDesc("creatTime");

		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"consctRoutingList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);

		SearchResult<ConsctRouting> searchResult = this
				.getConsctRoutingServiceBean().searchAndCount(search);

		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("consctRoutingList", searchResult.getResult());
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("approvalRebuteList");
	}

	/**
	 * 转向至驳回再提交页
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward goToRebuteSubmit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		ConsctRouting route = this.getConsctRoutingServiceBean().find(id);

		ProteclineLinkService linkService = (ProteclineLinkService) this
				.getProteclineLinkServiceBean();
		Search search = new Search();
		search.addFilterEqual("mainId", id);
		search.addFilterEqual("mainType", "1");
		search.addSortDesc("actionHappenTime");
		SearchResult<ProteclineLink> searchResult = linkService
				.searchAndCount(search);

		request.setAttribute("consctRouting", route);
		request.setAttribute("consctRoutingApprovalList", searchResult
				.getResult());
		request.setAttribute("resultSize", searchResult.getTotalCount());

		return mapping.findForward("goToRebuteSubmit");
	}

	/**
	 * 统计页转向
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward goToStatistics(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		return mapping.findForward("goToStatistics");
	}
	
	@SuppressWarnings("unchecked")
	public ActionForward statistics(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ss = request.getParameter("deleteIds");
		String rows[] = StaticMethod.nullObject2String(ss, "").split(";");
		String colConvertCondiction[] = new String[]{"","","","","","","","","",""};
		String search = "";
		String group = "";
		for (int i = 0; i < rows.length; i++) {
			String row = "";
			if (rows[i].contains("TypeLikedict")) {
				row = rows[i].substring(0, rows[i].length()
						- "TypeLikedict".length());
				colConvertCondiction[i] = "TypeLikedict";
			} else if (rows[i].contains("TypeLikeArea")) {
				row = rows[i].substring(0, rows[i].length()
						- "TypeLikeArea".length());
				colConvertCondiction[i] = "TypeLikeArea";
			} else if (rows[i].contains("TypeLikeUser")) {
				row = rows[i].substring(0, rows[i].length()
						- "TypeLikeUser".length());
				colConvertCondiction[i] = "TypeLikeUser";
			} else {
				row = rows[i];
			}

			if (i == rows.length - 1) {
				search += row;
				group += row;
			} else {
				search += row + ",";
				group += row + ",";
			}
		}
		
		// get the text to where condition.
		String whereCondition = " ";
//		String projectName = request.getParameter("projectName");
//		String location = request.getParameter("location");
//		if (!"".equals(projectName)) {
//			whereCondition += "and projectName like '%" + projectName + "%'";
//		}
//		if (!"".equals(location)) {
//			whereCondition += " and location like '%" + location + "%'";
//		}
		
		String searchSql = "select " + search
				+ ",count(id) " 
				+ "from dm_network_hiddentrouble "
				+ "where 1=1 "
				+ whereCondition
				+ " group by " + group
				+ " order by " + group;
		
		// set table header title
		List headList = new ArrayList();
		for (int i = 0; i < rows.length; i++) {
			if ("areaIdTypeLikeArea".equals(rows[i])) {
				headList.add("地区");
			}
			if ("hiddenTroubleTypeTypeLikedict".equals(rows[i])) {
				headList.add("隐患类型");
			}
			if ("isImportantTypeLikedict".equals(rows[i])) {
				headList.add("是否重要隐患");
			}
			if ("majorTypeTypeLikedict".equals(rows[i])) {
				headList.add("专业类型");
			}
			if ("processStatus".equals(rows[i])) {
				headList.add("处理状态");
				colConvertCondiction[i] = "processStatus";
			}
		}
		headList.add("总数");

		List<List<TdObjModel>> tempList = this.verticalGrowp(colConvertCondiction,searchSql,"consctRouting.do?method=statistics");
		
		request.setAttribute("tableList", tempList);
		request.setAttribute("headList", headList);
		request.setAttribute("time", rows.length);
		request.setAttribute("checkList", request.getParameter("checks"));

		return mapping.findForward("goToStatistics");
	}
	
	/**
	 * @param
	 * 			 rows    the search  colums.
	 * 			 searchSqL	 the search Sql.
	 * 			 url	the amount wil be acquire .set to the tag <a>
	 * @return 	
	 * 			 List 	the table list to show.*/
	@SuppressWarnings("unchecked")
	private List<List<TdObjModel>> verticalGrowp(String[] colConvertCondiction,String searchSql,String url) {
		String urlTemp=url;
		// 查询出结果集
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
		List<ListOrderedMap> resultList = jdbcService.queryForList(searchSql);
		
		// 查询结果列名结合
		List<String> colKeyList = null;
		Map<Integer, String> colKeyMap = new HashMap<Integer, String>();
		if(!resultList.isEmpty()) {
			colKeyList = resultList.get(0).keyList();
		} else {
			return null;
		}
		for (int j = 0; j < colKeyList.size(); j++) {
			colKeyMap.put(j, colKeyList.get(j));
		}
		
		int colKeySize = colKeyList.size();
		// 循环查询数据库的结果集
		List<List<TdObjModel>> tableList = new ArrayList<List<TdObjModel>>();
		List<TdObjModel> trList;
		TdObjModel tdModel;
		HashMap<String, Integer> counttdMap = new HashMap<String, Integer>();
		//判断TD的显示，如果相同的显示一次则之后不会再显示了。
		HashMap<String, Boolean> counttdBoolMap = new HashMap<String, Boolean>();
		for (int i = 0; i < resultList.size(); i++) {
			ListOrderedMap trMap = resultList.get(i);
			trList = new ArrayList<TdObjModel>();
			String tdNames = "";
			//循环每一个行
			for (int j = 0;j < colKeySize; j++) {
				String tdName = StaticMethod.nullObject2String(trMap
						.get(colKeyMap.get(j)));
				tdModel = new TdObjModel();
				 if(colConvertCondiction[j].contains("TypeLikedict")){
					ID2NameService service = (ID2NameService) ApplicationContextHolder
					.getInstance().getBean("ID2NameGetServiceCatch");
					tdName =service.id2Name(tdName, "tawSystemDictTypeDao");
				}
				 if(colConvertCondiction[j].contains("TypeLikeArea")){
						ID2NameService service = (ID2NameService) ApplicationContextHolder
						.getInstance().getBean("ID2NameGetServiceCatch");
						tdName =service.id2Name(tdName, "tawSystemAreaDao");
					}
				 if(colConvertCondiction[j].contains("TypeLikeUser")){
						ID2NameService service = (ID2NameService) ApplicationContextHolder
						.getInstance().getBean("ID2NameGetServiceCatch");
						tdName =service.id2Name(tdName, "tawSystemUserDao");
					}
				 if(colConvertCondiction[j].contains("processStatus")){
					 if("0".equals(tdName)) {
						 tdName = "未处理";
					 } else {
						 tdName = "已处理";
					 }
				 }
				tdModel.setName(tdName);
				
				if (!tdNames.equals("")) {
					tdNames = tdNames + "_";

				}
				tdNames = tdNames + "&"+tdName;
				//判断是否为最后的统计数字如果不是将进行比较合并
				if (j != colKeySize) {
					int count = 1;
					if (counttdMap.containsKey(tdNames)) {
						count += counttdMap.get(tdNames);
					}
					counttdMap.put(tdNames, count);
					counttdBoolMap.put(tdNames, true);
				}else{
					tdModel.setShow(true);
				}
				trList.add(tdModel);
			}
			tableList.add(trList);
		}
		//构建最终显示的List
		for (List<TdObjModel> trListTemp : tableList) {
			String tdNames = "";
			int i = 0;
			for (TdObjModel tdObj : trListTemp) {
				
				if(i==colKeySize)
					continue;
				
				String tdName = tdObj.getName();
				if (!tdNames.equals("")) {
					tdNames = tdNames + "_";
				}
				tdNames = tdNames + "&"+tdName;
				int rowspan = counttdMap.get(tdNames);
				tdObj.setRowspan(rowspan);
				if(counttdBoolMap.containsKey(tdNames)){
					counttdBoolMap.remove(tdNames);
					tdObj.setShow(true);
				}else{
					tdObj.setShow(false);
				}
				i+=1;
			}
		}

		return tableList;
	}
	
	@SuppressWarnings({ "unused", "unchecked","static-access" })
	private void responseJSONMessage(HttpServletResponse response,Map<String,String> messageMap,boolean success)throws Exception {
		response.setCharacterEncoding("utf-8");
		if(success) {
			messageMap.put("success", "true");
			messageMap.put("msg", "ok");
		} else {
			messageMap.put("success", "false");
			messageMap.put("msg", "failed");
			
		}
		response.getWriter().write(
				new Gson()
						.toJson(messageMap));
	}
}
