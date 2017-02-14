package com.boco.eoms.deviceManagement.busi.protectline.action;



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
import com.boco.eoms.deviceManagement.busi.protectline.form.AdvertisementApprovalForm;
import com.boco.eoms.deviceManagement.busi.protectline.form.PublicizeArticleApprovalForm;
import com.boco.eoms.deviceManagement.busi.protectline.form.PublicizeArticleForm;
import com.boco.eoms.deviceManagement.busi.protectline.model.Advertisement;
import com.boco.eoms.deviceManagement.busi.protectline.model.AdvertisementApproval;
import com.boco.eoms.deviceManagement.busi.protectline.model.PublicizeArticle;
import com.boco.eoms.deviceManagement.busi.protectline.model.PublicizeArticleApproval;
import com.boco.eoms.deviceManagement.busi.protectline.service.AdvertisementApprovalService;
import com.boco.eoms.deviceManagement.busi.protectline.service.AdvertisementService;
import com.boco.eoms.deviceManagement.busi.protectline.service.PublicizeArticleApprovalService;
import com.boco.eoms.deviceManagement.busi.protectline.service.PublicizeArticleService;
import com.boco.eoms.deviceManagement.busi.protectline.util.AdvertisementType;
import com.boco.eoms.deviceManagement.busi.protectline.util.TableHelper;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcService;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcServiceImpl;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public final class PublicizeArticleAction extends BaseAction {

	public PublicizeArticleService getMainBean() {
		String source = PublicizeArticleService.class.getSimpleName();
		return (PublicizeArticleService) getBean(source.substring(0, 1)
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
		PublicizeArticle publicizeArticle = this.getMainBean().find(id);
		
		
		PublicizeArticleApprovalService publicizeArticleApprovalService=(PublicizeArticleApprovalService)ApplicationContextHolder.getInstance().getBean("publicizeArticleApprovaService");
		Search search=new Search();
		search.addFilterEqual("projectNameID", id);
		SearchResult<PublicizeArticleApproval> searchResult=publicizeArticleApprovalService.searchAndCount(search);
		request.setAttribute("publicizeArticleType",AdvertisementType.characterId2TYPEMap);
		request.setAttribute("publicizeArticle", publicizeArticle);
		request.setAttribute("publicizeArticleApprovalList", searchResult.getResult());
		request.setAttribute("approvalResultSize", searchResult.getTotalCount());
		return mapping.findForward("goToDetail");
	}

	public ActionForward goToEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id =  request.getParameter("id");
		PublicizeArticle publicizeArticle = this.getMainBean().find(id);
		request.setAttribute("publicizeArticleType",AdvertisementType.characterId2TYPEMap);
		request.setAttribute("publicizeArticle", publicizeArticle);
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
		PublicizeArticleForm publicizeArticleForm = (PublicizeArticleForm) form;
		PublicizeArticle publicizeArticle = new PublicizeArticle();
		BeanUtils.copyProperties(publicizeArticle, publicizeArticleForm);
		String a=publicizeArticle.getCreatTime();
		String b=publicizeArticle.getFinishTime();
		publicizeArticle.setModifyTime(CommonUtils.toEomsStandardDate(new Date()));
		publicizeArticle.setModifyUser(userId);
		publicizeArticle.setModifyDept(deptId);
		publicizeArticle.setPublicizeArticlePlace(publicizeArticlePlace);
		publicizeArticle.setApprovalType(AdvertisementType.AD_TYPE1);
		this.getMainBean().save(publicizeArticle);
		
//		return mapping.findForward("list");
		// 设置跳转页面
		return forwardlist(mapping);
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
			PublicizeArticle publicizeArticle = this.getMainBean().find(id);
			publicizeArticle.setModifyTime(CommonUtils.toEomsStandardDate(new Date()));
			publicizeArticle.setModifyUser(userId);
			publicizeArticle.setModifyUser(deptId);
			publicizeArticle.setDeleted(AdvertisementType.DELETE_TRUE);
			this.getMainBean().save(publicizeArticle);
			
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			return forwardlist(mapping);
		}
	}

	// Delete all selected records.Afterward forward request. Notice the
	// difference with delete() method.
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
			PublicizeArticle publicizeArticle=getMainBean().find(id);
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
		String publicizeArticleArea=request.getParameter("publicizeArticleArea");
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		String creatTime = CommonUtils.toEomsStandardDate(new Date());
		PublicizeArticleForm publicizeArticleForm = (PublicizeArticleForm) form;
		PublicizeArticle publicizeArticle=new PublicizeArticle();
		BeanUtils.copyProperties(publicizeArticle, publicizeArticleForm);
		publicizeArticle.setApprovalType(AdvertisementType.AD_TYPE1);
		publicizeArticle.setCreatUser(userId);
		publicizeArticle.setcreatDept(deptId);
		publicizeArticle.setCreatTime(creatTime);
		publicizeArticle.setDeleted(AdvertisementType.DELETE_FALSE);
		publicizeArticle.setPublicizeArticlePlace(publicizeArticleArea);
//		publicizeArticle.setPublicizeArticlePlanAmount("123");
//		publicizeArticle.setModifyUser("ModifyUser");
//		publicizeArticle.setModifyTime("123");
//		publicizeArticle.setModifyDept("21");
//		publicizeArticle.setRemark("Remark");
//		publicizeArticle.setRemark1("Remark1");
//		publicizeArticle.setRemark2("Remark2");
//		publicizeArticle.setRemark3("Remark3");
//		publicizeArticle.setRemark4("remark4");
		PublicizeArticleService pas=CommonUtils.getService(PublicizeArticleService.class);
//		this.getMainBean().save(publicizeArticle);
		
		pas.save(publicizeArticle);
		//advertisementService.save(advertisement);

		// 设置跳转页面
		return mapping.findForward("forwardlist");
	}

	
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String finishTimeStringEqual=StaticMethod.nullObject2String(request.getParameter("finishTimeStringEqual"));
		String projectName=StaticMethod.nullObject2String(request.getParameter("projectName"));
		Search search = new Search();
		Map searchMap = request.getParameterMap();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		CommonUtils.getSqlFromRequestMapWithHidden(searchMap,
				PublicizeArticle.class, search);
		search.addFilterEqual("deleted", AdvertisementType.DELETE_FALSE);
		if(finishTimeStringEqual!=null&&!"".equals(finishTimeStringEqual))
		{
			search.addFilterEqual("finishTime", finishTimeStringEqual);	
		}
		if(projectName!=null&&!"".equals(projectName))
		{
			search.addFilterILike("projectName", projectName);
		}
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
		search.addFilterEqual("creatUser", userId);
		search.addFilterNotEqual("approvalType", "2");
		search.addSortDesc("creatTime");
		SearchResult<PublicizeArticle> searchResult = this.getMainBean()
				.searchAndCount(search);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("publicizeArticleList", searchResult.getResult());
		request.setAttribute("size", searchResult.getTotalCount());
		request.setAttribute("characterId2TYPEMap", AdvertisementType.characterId2TYPEMap);
		return mapping.findForward("list");
	}
	
	
	public ActionForward approval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		if(!"".equals(request.getParameter("id"))){
			String approvalType=request.getParameter("approvalType");
			PublicizeArticleService publicizeArticleService=(PublicizeArticleService)ApplicationContextHolder.getInstance().getBean("publicizeArticleService");
			PublicizeArticle publicizeArticle=publicizeArticleService.find(id);
			publicizeArticle.setApprovalType(approvalType);
			publicizeArticleService.save(publicizeArticle);
		}
		PublicizeArticleApprovalService publicizeArticleApprovalService=(PublicizeArticleApprovalService)ApplicationContextHolder.getInstance().getBean("publicizeArticleApprovaService");
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		String creatTime = CommonUtils.toEomsStandardDate(new Date());
		PublicizeArticleApprovalForm publicizeArticleApprovalForm = (PublicizeArticleApprovalForm) form;
		PublicizeArticleApproval publicizeArticleApproval = new PublicizeArticleApproval();
		BeanUtils.copyProperties(publicizeArticleApproval, publicizeArticleApprovalForm);
		publicizeArticleApproval.setProjectNameID(id);
		publicizeArticleApproval.setApprovalTime(creatTime);
		publicizeArticleApproval.setApprovalUser(userId);
		publicizeArticleApprovalService.save(publicizeArticleApproval);

		// 设置跳转页面
		return forwardApprovalList(mapping);
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
						"publicizeArticleApprovalList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
		search.addFilterEqual("approvalUser", userId);
		search.addSortDesc("creatTime");
		SearchResult<PublicizeArticle> searchResult = this.getMainBean()
				.searchAndCount(search);
		request.setAttribute("publicizeArticleType",AdvertisementType.characterId2TYPEMap);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("publicizeArticleApprovalList", searchResult.getResult());
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("approvalList");
	}
	
	public ActionForward approvaledList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		Search search = new Search();
		Map searchMap = request.getParameterMap();
		Set keySet = searchMap.keySet();
		Iterator iterator = keySet.iterator();
		CommonUtils.getSqlFromRequestMapWithHidden(searchMap,
				Advertisement.class, search);
//		if(!"".equals(request.getParameter("finishTimeA"))){
//		search.addFilterGreaterThan("finishTime",request.getParameter("finishTimeA"));
//		}
//		if(!"".equals(request.getParameter("finishTimeB"))){
//		search.addFilterLessThan("finishTime", request.getParameter("finishTimeB"));
//		}
		search.addFilterEqual("deleted", AdvertisementType.DELETE_FALSE);
		search.addFilterEqual("approvalType",  AdvertisementType.AD_TYPE2);
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"publicizeArticleApprovalList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.addSortDesc("creatTime");
		SearchResult<PublicizeArticle> searchResult = this.getMainBean()
				.searchAndCount(search);
		request.setAttribute("publicizeArticleType",AdvertisementType.characterId2TYPEMap);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("publicizeArticleApprovalList", searchResult.getResult());
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("approvaledList");
	}
	
	public ActionForward goToApproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id =  request.getParameter("id");
		PublicizeArticle publicizeArticle = this.getMainBean().find(id);
		PublicizeArticleApprovalService advertisementApprovalService=(PublicizeArticleApprovalService)ApplicationContextHolder.getInstance().getBean("publicizeArticleApprovaService");
		Search search=new Search();
		search.addFilterEqual("projectNameID", id);
		SearchResult<PublicizeArticleApproval> searchResult=advertisementApprovalService.searchAndCount(search);
		request.setAttribute("publicizeArticleType",AdvertisementType.characterId2TYPEMap);
		request.setAttribute("publicizeArticle", publicizeArticle);
		request.setAttribute("publicizeArticleApprovalList", searchResult.getResult());
		request.setAttribute("approvalResultSize", searchResult.getTotalCount());
		return mapping.findForward("goToApproval");
	
	}
	
	
	public ActionForward approvaledDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id =  request.getParameter("id");
		PublicizeArticle publicizeArticle = this.getMainBean().find(id);
		PublicizeArticleApprovalService publicizeArticleApprovalService=(PublicizeArticleApprovalService)ApplicationContextHolder.getInstance().getBean("publicizeArticleApprovaService");
		Search search=new Search();
		search.addFilterEqual("projectNameID", id);
		SearchResult<PublicizeArticleApproval> searchResult=publicizeArticleApprovalService.searchAndCount(search);
		request.setAttribute("publicizeArticleType",AdvertisementType.characterId2TYPEMap);
		request.setAttribute("publicizeArticle", publicizeArticle);
		request.setAttribute("publicizeArticleApprovalList", searchResult.getResult());
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
		// get the text to where condition.
		String whereCondition = " ";
		String decreatdept = sessionform.getDeptid();
		String deprojectname = StaticMethod.nullObject2String(request.getParameter("de0projectnameT"));
//		String deadvertisementcontent = request
//				.getParameter("de0advertisementcontentT");
		String depublicizeArticlePlace = StaticMethod.nullObject2String(request
				.getParameter("de0publicizeArticlePlaceT"));
		String depublicizeArticleType = StaticMethod.nullObject2String(request
				.getParameter("de0publicizeArticleTypeT"));
//		String definishtime = request.getParameter("de0finishtimeT");
//		String deincompletecause = request.getParameter("de0incompletecauseT");
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
