package com.boco.eoms.deviceManagement.busi.line.action;



import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.busi.line.from.MaintainApprovalForm;
import com.boco.eoms.deviceManagement.busi.line.from.MaintainFinishForm;
import com.boco.eoms.deviceManagement.busi.line.from.MaintainForm;
import com.boco.eoms.deviceManagement.busi.line.from.MaintainReportForm;
import com.boco.eoms.deviceManagement.busi.line.model.Maintain;
import com.boco.eoms.deviceManagement.busi.line.model.MaintainApproval;
import com.boco.eoms.deviceManagement.busi.line.model.MaintainFinish;
import com.boco.eoms.deviceManagement.busi.line.model.MaintainReport;
import com.boco.eoms.deviceManagement.busi.line.service.MaintainApprovalService;
import com.boco.eoms.deviceManagement.busi.line.service.MaintainFinishService;
import com.boco.eoms.deviceManagement.busi.line.service.MaintainReportService;
import com.boco.eoms.deviceManagement.busi.line.service.MaintainService;
import com.boco.eoms.deviceManagement.busi.protectline.model.Advertisement;
import com.boco.eoms.deviceManagement.busi.protectline.model.PublicizeArticle;
import com.boco.eoms.deviceManagement.busi.protectline.util.AdvertisementType;
import com.boco.eoms.deviceManagement.busi.protectline.util.LineMap;
import com.boco.eoms.deviceManagement.busi.protectline.util.TableHelper;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcService;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcServiceImpl;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public final class MaintainAction extends BaseAction {

	public MaintainService getMainBean() {
		String source = MaintainService.class.getSimpleName();
		return (MaintainService) getBean(source.substring(0, 1)
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
		return new ActionForward(path, true);
	}
	public ActionForward forwardApprovalList(ActionMapping mapping) {
		ActionForward forward = mapping.findForward("forwardApprovalList");
		String path = forward.getPath();
		return new ActionForward(path, false);
	}

	public ActionForward goToAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("goToAdd");
	}
	public ActionForward goToAddMaintainReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String id =  request.getParameter("id");
		Maintain maintain = this.getMainBean().find(id);
		request.setAttribute("maintainType",AdvertisementType.characterId2TYPEMap);
		request.setAttribute("maintain", maintain);
		return mapping.findForward("addMaintainReport");
	}

	public ActionForward goToImport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("goToImport");
	}

	@SuppressWarnings("unchecked")
	public ActionForward goToDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id =  request.getParameter("id");
		Maintain maintain = this.getMainBean().find(id);
		MaintainApprovalService MaintainApprovalService=(MaintainApprovalService)ApplicationContextHolder.getInstance().getBean("maintainApprovalService");
		MaintainFinishService maintainFinishService=(MaintainFinishService)ApplicationContextHolder.getInstance().getBean("maintainFinishService");
		
		Search search=new Search();
		Search maintainFinishSearch=new Search();
		search.addFilterEqual("projectNameID", id);
		maintainFinishSearch.addFilterEqual("maintainNameId", id);
		
		SearchResult<MaintainApproval> searchResult=MaintainApprovalService.searchAndCount(search);
		SearchResult<MaintainFinish> maintainFinishsearchResult=maintainFinishService.searchAndCount(maintainFinishSearch);
		
		
		request.setAttribute("maintainApprovalType",AdvertisementType.characterId2TYPEMap);
		request.setAttribute("maintain", maintain);
		request.setAttribute("maintainApprovalApprovalList", searchResult.getResult());
		request.setAttribute("maintainFinishList", maintainFinishsearchResult.getResult());
		request.setAttribute("approvalResultSize", searchResult.getTotalCount());
		return mapping.findForward("goToDetail");
	}

	public ActionForward goToEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id =  request.getParameter("id");
		Maintain maintain = this.getMainBean().find(id);
		request.setAttribute("maintainType",AdvertisementType.characterId2TYPEMap);
		request.setAttribute("maintain", maintain);
		return mapping.findForward("goToEdit");
	}
	
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String publicizeArticlePlace=request.getParameter("publicizeArticleArea");
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		String id = request.getParameter("id");
		MaintainForm maintainForm = (MaintainForm) form;
		Maintain maintain = new Maintain();
		BeanUtils.copyProperties(maintain, maintainForm);
		maintain.setModifyTime(CommonUtils.toEomsStandardDate(new Date()));
		String modifyDept = sessionform.getDeptid();
		maintain.setModifyDept(modifyDept);
		maintain.setApprovalType(AdvertisementType.AD_TYPE1);
//		maintain.setCreatUser(userId);
//		maintain.setCreatDept(deptId);
//		maintain.setDeleted(AdvertisementType.DELETE_FALSE);
		maintain.setMaintainFinish("0");
		this.getMainBean().save(maintain);
		return mapping.findForward("maintainforwardlist");
	}

	// Delete a record.Afterward forward request.
	@SuppressWarnings("finally")
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		try {
			TawSystemSessionForm sessionform = this.getUser(request);
			String userId = sessionform.getUserid();
			String deptId = sessionform.getDeptid();
			String id = request.getParameter("id");
			Maintain publicizeArticle = this.getMainBean().find(id);
			publicizeArticle.setModifyTime(CommonUtils.toEomsStandardDate(new Date()));
			publicizeArticle.setModifyUser(userId);
			publicizeArticle.setModifyUser(deptId);
			publicizeArticle.setDeleted(AdvertisementType.DELETE_TRUE);
//			this.getMainBean().save(publicizeArticle);
			
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			return forwardlist(mapping);
		}
	}
	public ActionForward deleteAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String myDealingList[] = StaticMethod.nullObject2String(
				request.getParameter("ids"), "").split(";");
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		// Remove all records.
		for (String id : myDealingList) {
			Maintain publicizeArticle=getMainBean().find(id);
			publicizeArticle.setDeleted(AdvertisementType.DELETE_TRUE);
			publicizeArticle.setModifyTime(CommonUtils.toEomsStandardDate(new Date()));
			publicizeArticle.setModifyUser(userId);
			publicizeArticle.setModifyUser(deptId);
			this.getMainBean().save(publicizeArticle);
		}
		return forwardlist(mapping);
	}
	
	
	// Add a record.
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		String creatTime = CommonUtils.toEomsStandardDate(new Date());
		MaintainForm maintainForm = (MaintainForm) form;
		Maintain maintain=new Maintain();
		BeanUtils.copyProperties(maintain, maintainForm);
		maintain.setApprovalType(AdvertisementType.AD_TYPE1);
		maintain.setCreatUser(userId);
		maintain.setCreatDept(deptId);
		maintain.setCreatTime(creatTime);
		maintain.setDeleted(AdvertisementType.DELETE_FALSE);
		maintain.setMaintainFinish("0");
		MaintainService ms=CommonUtils.getService(MaintainService.class);
		ms.save(maintain);
		// 设置跳转页面
		return mapping.findForward("maintainforwardlist");
	}
	
	public ActionForward addMaintainReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
//		MaintainReportService maintainReportService=(MaintainReportService)ApplicationContextHolder.getInstance().getBean("maintainReportService");
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		String creatTime = CommonUtils.toEomsStandardDate(new Date());
		MaintainReportForm maintainReportForm = (MaintainReportForm) form;
		MaintainReport maintainReport=new MaintainReport();
		BeanUtils.copyProperties(maintainReport, maintainReportForm);
		
		Maintain maintain = this.getMainBean().find(maintainReport.getMaintainNameId());
		maintain.setMaintainFinish(maintainReport.getStatus());
		maintain.setIsReport("1");
		 this.getMainBean().save(maintain);
		maintainReport.setAddMan(userId);
		maintainReport.setAddTime(CommonUtils.toEomsStandardDate(new Date()));
		MaintainReportService maintainReportService=CommonUtils.getService(MaintainReportService.class);
		maintainReportService.save(maintainReport);
		// 设置跳转页面
		return mapping.findForward("maintainforwardlist");
	}

	
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String finishTimeStringEqual=StaticMethod.nullObject2String(request.getParameter("finishTimeStringEqual"));
		String projectName=StaticMethod.nullObject2String(request.getParameter("projectName"));
		Search search = new Search();
		Map searchMap = request.getParameterMap();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"maintainList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		TawSystemSessionForm sessionform = this.getUser(request);
		search.addFilterEqual("creatUser", sessionform.getUserid());
		search.addSortDesc("creatTime");
		SearchResult<Maintain> searchResult = this.getMainBean()
				.searchAndCount(search);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", searchResult.getTotalCount());
		request.setAttribute("maintainList", searchResult.getResult());
		request.setAttribute("characterId2TYPEMap", AdvertisementType.characterId2TYPEMap);
		return mapping.findForward("list");
	}
	/**
	 * 验收管理LIST
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward maintainFinishlist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String finishTimeStringEqual=StaticMethod.nullObject2String(request.getParameter("finishTimeStringEqual"));
		String projectName=StaticMethod.nullObject2String(request.getParameter("projectName"));
		Search search = new Search();
		Map searchMap = request.getParameterMap();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		CommonUtils.getSqlFromRequestMapWithHidden(searchMap,
				PublicizeArticle.class, search);
		search.addFilterEqual("deleted", AdvertisementType.DELETE_FALSE);
		search.addFilterEqual("isReport", "1");
		String pageIndexString = request
		.getParameter((new org.displaytag.util.ParamEncoder(
		"publicizeArticleList")
		.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
//		search.addFilterEqual("creatUser", userId);
		search.addFilterEqual("approvalUser", userId);
		search.addSortDesc("creatTime");
		SearchResult<Maintain> searchResult = this.getMainBean()
		.searchAndCount(search);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("maintainList", searchResult.getResult());
		request.setAttribute("size", searchResult.getTotalCount());
		request.setAttribute("finished", LineMap.statusMap);
		return mapping.findForward("maintainFinishlist");
	}
	public ActionForward maintainFinishAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception  {
		MaintainFinishService maintainFinishService=(MaintainFinishService)ApplicationContextHolder.getInstance().getBean("maintainFinishService");
		MaintainFinishForm maintainFinishForm=(MaintainFinishForm)form;
		MaintainFinish maintainFinish = new MaintainFinish();
		BeanUtils.copyProperties(maintainFinish, maintainFinishForm);
		String addTime=CommonUtils.toEomsStandardDate(new Date());
		TawSystemSessionForm sessionform = this.getUser(request);
		String addMan=sessionform.getUserid();
		maintainFinish.setAddTime(addTime);
		maintainFinish.setAddMan(addMan);
		Maintain maintain = this.getMainBean().find(maintainFinish.getMaintainNameId());
		maintain.setMaintainFinish(maintainFinish.getFinishResult());
		maintain.setIsCheck(maintainFinishForm.getFinishResult());
		this.getMainBean().save(maintain);
		maintainFinishService.save(maintainFinish);
		return mapping.findForward("maintainFinishRedlist");
	}
	public ActionForward goToAddMaintainfinish(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String id=request.getParameter("id");
		Maintain maintain=this.getMainBean().find(id);
		MaintainReportService maintainReportService=(MaintainReportService)ApplicationContextHolder.getInstance().getBean("maintainReportService");
		Search search=new Search();
		search.addFilterEqual("maintainNameId", maintain.getId());
		search.addSortDesc("addTime");
		SearchResult<MaintainReport> searchResult = maintainReportService.searchAndCount(search);
		request.setAttribute("maintainReport", searchResult.getResult());
		request.setAttribute("maintain", maintain);
		request.setAttribute("finished", LineMap.statusMap);
		return mapping.findForward("goToMaintainFinishedAdd");
	}
	public ActionForward goToDetailMaintainfinish(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaintainApprovalService maintainReportService=(MaintainApprovalService)ApplicationContextHolder.getInstance().getBean("maintainApprovalService");
		String id=request.getParameter("id");
		Maintain maintain=this.getMainBean().find(id);
		Search search=new Search();
		search.addFilterEqual("projectNameID", id);
		SearchResult<MaintainApproval> searchResult = maintainReportService.searchAndCount(search);
		request.setAttribute("maintain", maintain);
		request.setAttribute("characterId2TYPEMap", AdvertisementType.characterId2TYPEMap);
		request.setAttribute("maintainApprovalList", searchResult.getResult());
		request.setAttribute("size", searchResult.getTotalCount());
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		return mapping.findForward("goToMaintainFinishDetail");
	}
	
	public ActionForward goToMaintainFinishRecordList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String finishTimeStringEqual=StaticMethod.nullObject2String(request.getParameter("finishTimeStringEqual"));
		String projectName=StaticMethod.nullObject2String(request.getParameter("projectName"));
		Search search = new Search();
		Map searchMap = request.getParameterMap();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		CommonUtils.getSqlFromRequestMapWithHidden(searchMap,
				PublicizeArticle.class, search);
		search.addFilterEqual("deleted", AdvertisementType.DELETE_FALSE);
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"maintainList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.addSortDesc("creatTime");
		search.addFilterEqual("maintainFinish","1");
//		search.addFilterEqual("isCheck","0");
//		search.addFilterNotEqual("isRecord", "1");
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
		search.addFilterEqual("approvalUser", userId);
//		search.addFilterEqual("creatUser", userId);
		SearchResult<Maintain> searchResult = this.getMainBean()
				.searchAndCount(search);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("maintainList", searchResult.getResult());
		request.setAttribute("size", searchResult.getTotalCount());
		request.setAttribute("characterId2TYPEMap", LineMap.checkMap);
		return mapping.findForward("goToMaintainFinishRecordList");
	}
	public ActionForward goToAddMaintainFinishRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaintainReportService maintainReportService=(MaintainReportService)ApplicationContextHolder.getInstance().getBean("maintainReportService");
		MaintainFinishService maintainFinishService=(MaintainFinishService)ApplicationContextHolder.getInstance().getBean("maintainFinishService");
		Search search=new Search();
		Search maintainFinishsearch=new Search();
		search.addSortDesc("addTime");
		String id=request.getParameter("id");
		Maintain maintain  = this.getMainBean().find(id);
		search.addFilterEqual("maintainNameId", maintain.getId());
		SearchResult<MaintainReport> searchResult = maintainReportService.searchAndCount(search);
		maintainFinishsearch.addFilterEqual("maintainNameId",id);
		SearchResult<MaintainFinish> maintainFinishsearchResult=maintainFinishService.searchAndCount(maintainFinishsearch);
		request.setAttribute("maintain", maintain);
		request.setAttribute("finished", LineMap.statusMap);
		request.setAttribute("maintainReport", searchResult.getResult());
		request.setAttribute("checkresult", LineMap.checkMap);
		request.setAttribute("maintainFinishsearchResult", maintainFinishsearchResult.getResult());
		return mapping.findForward("goToMaintainFinishRecordForm");
	}
	public ActionForward goToMaintainFinishRecordDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		MaintainReportService maintainReportService=(MaintainReportService)ApplicationContextHolder.getInstance().getBean("maintainReportService");
		MaintainFinishService maintainFinishService=(MaintainFinishService)ApplicationContextHolder.getInstance().getBean("maintainFinishService");
		MaintainApprovalService maintainApprovalService=(MaintainApprovalService)ApplicationContextHolder.getInstance().getBean("maintainApprovalService");
		
		String id = request.getParameter("id");
		Maintain maintain = this.getMainBean().find(id);
		
		Search maintainApprovalSearch = new Search();
		maintainApprovalSearch.addFilterEqual("projectNameID", id);
		SearchResult<MaintainApproval> maintainApprovalList = maintainApprovalService.searchAndCount(maintainApprovalSearch);
		
		Search maintainFinishSearch = new Search();
		maintainApprovalSearch.addFilterEqual("maintainNameId", id);
		SearchResult<MaintainFinish> maintainFinishList = maintainFinishService.searchAndCount(maintainFinishSearch);
		
		Search maintainReportSearch = new Search();
		maintainApprovalSearch.addFilterEqual("maintainNameId", id);
		SearchResult<MaintainReport> maintainReportList = maintainReportService.searchAndCount(maintainReportSearch);
		
		
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("maintainApprovalListsize", maintainApprovalList.getTotalCount());
		request.setAttribute("maintainFinishListsize", maintainFinishList.getTotalCount());
		request.setAttribute("maintainReportListsize", maintainReportList.getTotalCount());
		
		request.setAttribute("maintain", maintain);
		request.setAttribute("finished", LineMap.statusMap);
		request.setAttribute("checkresult", LineMap.checkMap);
		request.setAttribute("ApprovaType",AdvertisementType.characterId2TYPEMap);
		
		request.setAttribute("maintainApprovalList", maintainApprovalList.getResult());
		request.setAttribute("maintainFinishList", maintainFinishList.getResult());
		request.setAttribute("maintainReportList", maintainReportList.getResult());
		return mapping.findForward("goTomaintainfinishrecordDetail");
	}
	public ActionForward addMaintainFinishRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaintainFinishForm maintainFinishForm=(MaintainFinishForm)form;
		String maintainid = request.getParameter("maintainid");
		String isRecord = request.getParameter("isRecord");
		String isModify = request.getParameter("isModify");
		
		Maintain maintain  = this.getMainBean().find(maintainid);
		maintain.setIsRecord(isRecord);
		maintain.setIsModify(isModify);
		this.getMainBean().save(maintain);
		return mapping.findForward("maintainfinishrecordRedList");
	}
	
	
	public ActionForward approval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		if(!"".equals(request.getParameter("id"))){
			String approvalType=request.getParameter("approvalType");
			MaintainService maintainService=(MaintainService)ApplicationContextHolder.getInstance().getBean("maintainService");
			Maintain maintain=maintainService.find(id);
			maintain.setApprovalType(approvalType);
			maintainService.save(maintain);
		}
		MaintainApprovalService maintainApprovalService=(MaintainApprovalService)ApplicationContextHolder.getInstance().getBean("maintainApprovalService");
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		String creatTime = CommonUtils.toEomsStandardDate(new Date());
		MaintainApprovalForm publicizeArticleApprovalForm = (MaintainApprovalForm) form;
		MaintainApproval maintainApproval = new MaintainApproval();
		BeanUtils.copyProperties(maintainApproval, publicizeArticleApprovalForm);
		maintainApproval.setProjectNameID(id);
		maintainApproval.setApprovalTime(creatTime);
		maintainApproval.setApprovalUser(userId);
		maintainApprovalService.save(maintainApproval);
		// 设置跳转页面
//		return mapping.findForward("approvaleList");
		return mapping.findForward("forwardApprovalList");
	}
	
	public ActionForward approvalList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		Search search = new Search();
		Map searchMap = request.getParameterMap();
		Set keySet = searchMap.keySet();
		Iterator iterator = keySet.iterator();
		CommonUtils.getSqlFromRequestMapWithHidden(searchMap,
				Advertisement.class, search);
		search.addFilterEqual("deleted", AdvertisementType.DELETE_FALSE);
		search.addFilterEqual("approvalType",  AdvertisementType.AD_TYPE1);
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"maintainApprovalList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		TawSystemSessionForm sessionform = this.getUser(request);
		search.addFilterEqual("approvalUser", sessionform.getUserid());
		search.addSortDesc("creatTime");
		SearchResult<Maintain> searchResult = this.getMainBean()
				.searchAndCount(search);
		request.setAttribute("publicizeArticleType",AdvertisementType.characterId2TYPEMap);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("maintainApprovalList", searchResult.getResult());
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("approvalList");
	}
	
	public ActionForward maintainFinishRecordedList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String maintainNameStringLike=request.getParameter("maintainNameStringLike");
		String influenceSystemStringLike=request.getParameter("influenceSystemStringLike");
		Search search = new Search();
		Map searchMap = request.getParameterMap();
		Set keySet = searchMap.keySet();
		Iterator iterator = keySet.iterator();
		CommonUtils.getSqlFromRequestMapWithHidden(searchMap,
				Advertisement.class, search);
		if(!"".equals(maintainNameStringLike) && maintainNameStringLike !=null){
			search.addFilterLike("maintainName", maintainNameStringLike);
		}
		if(!"".equals(influenceSystemStringLike) && influenceSystemStringLike !=null){
			search.addFilterLike("influenceSystem", influenceSystemStringLike);
		}
		search.addFilterEqual("deleted", AdvertisementType.DELETE_FALSE);
		search.addFilterEqual("approvalType",  AdvertisementType.AD_TYPE2);
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"maintainApprovadlList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
		search.addFilterEqual("isRecord", "1");
//		search.addFilterEqual("creatUser", userId);
		Filter filters = new Filter();
		filters.equal("approvalUser", userId);
		Filter filter2 = new Filter();
		filter2.equal("creatUser", userId);
//		filters.or(filter2);
		filters.and(filter2);
//		search.addFilter(filters);
		search.addFilterOr(filters);
		search.addSortDesc("creatTime");
		SearchResult<Maintain> searchResult = this.getMainBean()
				.searchAndCount(search);
		request.setAttribute("maintainType",AdvertisementType.characterId2TYPEMap);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("maintainList", searchResult.getResult());
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("maintainfinishrecordedList");
	}
	
	public ActionForward goToApproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id =  request.getParameter("id");
		Maintain maintain = this.getMainBean().find(id);
		MaintainApprovalService maintainApprovalService=(MaintainApprovalService)ApplicationContextHolder.getInstance().getBean("maintainApprovalService");
		Search search=new Search();
		search.addFilterEqual("projectNameID", id);
		SearchResult<MaintainApproval> searchResult=maintainApprovalService.searchAndCount(search);
		request.setAttribute("maintainType",AdvertisementType.characterId2TYPEMap);
		request.setAttribute("maintain", maintain);
		request.setAttribute("maintainApprovalList", searchResult.getResult());
		request.setAttribute("approvalResultSize", searchResult.getTotalCount());
		return mapping.findForward("goToApproval");
	
	}
	
	public ActionForward approvaledDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id =  request.getParameter("id");
		Maintain maintain = this.getMainBean().find(id);
		MaintainApprovalService maintainApprovalService=(MaintainApprovalService)ApplicationContextHolder.getInstance().getBean("maintainApprovalService");
		Search search=new Search();
		search.addFilterEqual("projectNameID", id);
		SearchResult<MaintainApproval> searchResult=maintainApprovalService.searchAndCount(search);
		request.setAttribute("maintainType",AdvertisementType.characterId2TYPEMap);
		request.setAttribute("maintain", maintain);
		request.setAttribute("maintainApprovalList", searchResult.getResult());
		request.setAttribute("approvalResultSize", searchResult.getTotalCount());
		return mapping.findForward("approvaledDetail");
	}
	
	
	/**
	 * @author lee
	 * @time 2010-10 多为统计跳转
	 */
	public ActionForward goToShowPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("goToShowPage");
	}

	/**
	 * @author lee
	 * @time 2010-10 多为统计
	 */
	public ActionForward staffContent(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TawSystemSessionForm sessionform = this.getUser(request);
		String ss = StaticMethod.nullObject2String(
				request.getParameter("deleteIdss"), "");
		ss+="de0publicizeArticlePlanAmount;de0publicizeArticleActualAmount;";
		String rows[] = ss.split(";");
		String checkString = StaticMethod.nullObject2String(request
				.getParameter("checks"), "");
		String search = "";
		String group = "";
		for (int i = 0; i < rows.length; i++) {
			String row = "";
			if (rows[i].contains("TypeLikedict")) {
				row = rows[i].substring(0, rows[i].length()
						- "TypeLikedict".length());
				row = row.replace("0", ".");
			}
			else if (rows[i].contains("TypeLikeArea")) {
				row = rows[i].substring(0, rows[i].length()
						- "TypeLikeArea".length());
				row = row.replace("0", ".");
			}else if (rows[i].contains("TypeLikeUser")) {
				row = rows[i].substring(0, rows[i].length()
						- "TypeLikeUser".length());
				row = row.replace("0", ".");
			} else {
				row = rows[i].replace("0", ".");
			}

			if (i == rows.length - 1) {
				search += row + " as " + rows[i];
				group += row;
			} else {
				search += row + " as " + rows[i] + ",";
				group += row + ",";
			}
		}
		search=search+",de.finishtime as de0finishtime,de.publicizeArticleActualAmount as de0publicizeArticleActualAmount";
		String whereCondition = " ";
		String decreatdept = sessionform.getDeptid();
		String deprojectname = StaticMethod.nullObject2String(request.getParameter("de0projectnameT"));
		String depublicizeArticlePlace = StaticMethod.nullObject2String(request
				.getParameter("de0publicizeArticlePlaceT"));
		String depublicizeArticleType = StaticMethod.nullObject2String(request
				.getParameter("de0publicizeArticleTypeT"));
		String deapprovaluser = StaticMethod.nullObject2String(request.getParameter("de0approvaluserT"));
		if (!"".equals(decreatdept)) {
			whereCondition += " and de.creatdept like '%" + decreatdept + "%'";
		}
		if (!"".equals(deprojectname)) {
			whereCondition += " and de.projectname like '%" + deprojectname + "%'";
		}
		if (!"".equals(depublicizeArticlePlace)) {
			whereCondition += " and de.publicizeArticlePlace like '%"
					+ depublicizeArticlePlace + "%'";
		}
		if (!"".equals(depublicizeArticleType)) {
			whereCondition += " and de.publicizeArticleType like '%"
					+ depublicizeArticleType + "%'";
		}
		if (!"".equals(deapprovaluser)) {
			whereCondition += " and de.approvaluser =  " + deapprovaluser;
		}
		String searchSql = "select "
				+ search
				+ " ,count(de.id) "
				+ "from dm_protectline_publicizearticle de "
				+ " where de.deleted='0' and de.approvaltype='2' "
				+ ""
				+ whereCondition
				+ ""
				+ "group by de.finishtime,de.publicizeArticleActualAmount,"
				+ " "
				+ group
				+ " "
				+ "  order by de.finishtime,de.publicizeArticleActualAmount,"
				+ " " + group;
		
		List<String> headList = new ArrayList<String>();
		for (int i = 0; i < rows.length; i++) {
			if ("de0projectname".equals(rows[i])) {
				headList.add("项目名称");
			}
			if ("de0publicizeArticlePlaceTypeLikeArea".equals(rows[i])) {
				headList.add("宣传品发放地点");
			}
			if ("de0publicizeArticleTypeTypeLikedict".equals(rows[i])) {
				headList.add("宣传品种类名称");
			}
			if ("de0approvaluser".equals(rows[i])) {
				headList.add("审批人");
			}
		}
		headList.add("宣传品计划发放数量");
		headList.add("实际发放数量");
		headList.add("总数");
		List tempList = TableHelper
				.verticalGrowp(rows, searchSql,
						"/deviceManagement/publicizeArticle/publicizeArticle.do?method=searchInto");
		request.setAttribute("tableList", tempList);
		request.setAttribute("headList", headList);
		request.setAttribute("checkList", checkString);
		
		return mapping.findForward("goToShowPage");
	}
/**
 * @author lee
 * 宣传用品统计
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
	
	
	public ActionForward searchInto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		 TawSystemSessionForm sessionform = this.getUser(request);
		// String operateUserId = sessionform.getUserid();
		// String operateDeptId = sessionform.getDeptid();
		CommonSpringJdbcServiceImpl csjsi = (CommonSpringJdbcServiceImpl) ApplicationContextHolder
		.getInstance().getBean("commonSpringJdbcService");
		String search ="";
		String sql="";
		
		String creatDept = sessionform.getDeptid();
//		String creatDept = request.getParameter("de0creatdepttypelikearea");
		if (creatDept!=null) {
			creatDept = new String(creatDept.toString().trim().getBytes(
					"ISO-8859-1"), "utf-8");
			search=search+"and de.creatDept="+"'"+creatDept+"' ";
		}
		String projectName = request.getParameter("de0projectName");
		if (projectName!=null) {
			projectName = new String(projectName.toString().trim().getBytes("ISO-8859-1"),
					"utf-8");
			search=search+"and de.projectName="+"'"+projectName+"' ";
		}
		String publicizeArticlePlace = request.getParameter("de0publicizeArticlePlace");
		if (publicizeArticlePlace!=null) {
			publicizeArticlePlace = new String(publicizeArticlePlace.toString().trim()
					.getBytes("ISO-8859-1"), "utf-8");
			search=search+"and de.publicizeArticlePlace="+"'"+publicizeArticlePlace+"' ";
		}
		String publicizeArticleType = request.getParameter("de0publicizeArticleType");
		if (publicizeArticleType!=null) {
			publicizeArticleType = new String(publicizeArticleType.toString().trim().getBytes("ISO-8859-1"),
					"utf-8");
			search=search+"and de.publicizeArticleType="+"'"+publicizeArticleType+"' ";
		}
//		String finishTime = request.getParameter("de0finishTime");
//		if (finishTime!=null) {
//			finishTime = new String(finishTime.toString().trim().getBytes(
//					"ISO-8859-1"), "utf-8");
//			search=search+"and de.finishTime="+"'"+finishTime+"'  ";
//		}
		String approvalUser = request.getParameter("de0approvalUser");
		if (approvalUser!=null) {
			approvalUser = new String(approvalUser.toString().trim()
					.getBytes("ISO-8859-1"), "utf-8");
			search=search+"and de.approvalUser="+"'"+approvalUser+"'  ";
		}
//		String incompleteCause = request.getParameter("de0incompleteCause");
//		if (incompleteCause!=null) {
//			incompleteCause = new String(incompleteCause.toString().trim()
//					.getBytes("ISO-8859-1"), "utf-8");
//			search=search+"and de.incompleteCause="+"'"+incompleteCause+"'  ";
//		}
			sql = "select de.* from dm_protectline_publicizearticle de where de.deleted='0' and de.approvaltype='2'  "+search;
			List<ListOrderedMap> resultList  = csjsi.queryForList(sql);
			List list=new ArrayList();
			for (ListOrderedMap listOrderedMap : resultList) {
				PublicizeArticle staff= new PublicizeArticle();
				staff.setId((String)listOrderedMap.get("id"));
				staff.setCreatDept((String)listOrderedMap.get("creatDept"));
				staff.setProjectName((String)listOrderedMap.get("projectName"));
				staff.setPublicizeArticlePlace((String)listOrderedMap.get("publicizeArticlePlace"));
				staff.setFinishTime((String)listOrderedMap.get("finishTime"));
				staff.setApprovalUser((String)listOrderedMap.get("approvalUser"));
				staff.setIncompleteCause((String)listOrderedMap.get("incompleteCause"));
//				staff.setAdvertisementAmount((String)listOrderedMap.get("advertisementAmount"));
				staff.setRemark((String)listOrderedMap.get("remark"));
				list.add(staff);
			}
			request.setAttribute("publicizeArticleApprovalList", list);
			request.setAttribute("resultSize", list.size());
			request.setAttribute("size", list.size());
		String next = "searchInto";
		return mapping.findForward(next);
	}
	
	
	
	
	
	
	
	
	
	
	
	
//	public ActionForward importRecord(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response) {
//		response.setCharacterEncoding("utf-8");
//		AdvertisementForm uploadForm = (AdvertisementForm) form;
//		FormFile formFile = uploadForm.getImportFile();
//		
//		TawSystemSessionForm sessionform = this.getUser(request);
//		String userId = sessionform.getUserid();
//		String deptId = sessionform.getDeptid();
//		String phone = sessionform.getContactMobile();
//		String roleId = sessionform.getRoleid();
//		
//		Map<String,String> params = new HashMap<String,String>();
//		params.put("userId", userId);
//		params.put("deptId", deptId);
//		params.put("phone", phone);
//		params.put("roleId", roleId);
//		
//		PrintWriter writer = null;
//		try{
//			writer = response.getWriter();
//			ImportResult result = this.getMainBean().importFromFile(formFile.getInputStream(),formFile.getFileName(),params);
//			if(result.getResultCode().equals("200")) {
//				writer.write(
//						new Gson().toJson(new ImmutableMap.Builder<String, String>()
//								.put("success", "true")
//								.put("msg", "ok")
//								.put("infor", "'导入成功,共计导入"+result.getImportCount()+"条记录'").build()));
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//			writer.write(
//					new Gson().toJson(new ImmutableMap.Builder<String, String>()
//							.put("success", "false")
//							.put("msg", "failure")
//							.put("infor", e.getMessage()).build()));
//		}finally{
//			if(writer != null){
//				writer.close();
//			}
//		}
//		return null;
//	}
//	
}
