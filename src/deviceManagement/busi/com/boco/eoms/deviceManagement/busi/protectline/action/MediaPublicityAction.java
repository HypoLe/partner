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
import com.boco.eoms.deviceManagement.busi.protectline.form.MediaPublicityApprovalForm;
import com.boco.eoms.deviceManagement.busi.protectline.form.MediaPublicityForm;
import com.boco.eoms.deviceManagement.busi.protectline.model.MediaPublicity;
import com.boco.eoms.deviceManagement.busi.protectline.model.MediaPublicityApproval;
import com.boco.eoms.deviceManagement.busi.protectline.service.MediaPublicityApprovalService;
import com.boco.eoms.deviceManagement.busi.protectline.service.MediaPublicityService;
import com.boco.eoms.deviceManagement.busi.protectline.util.MediaPublicityType;
import com.boco.eoms.deviceManagement.busi.protectline.util.TableHelper;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcService;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcServiceImpl;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public final class MediaPublicityAction extends BaseAction {

	public MediaPublicityService getMainBean() {
		String source = MediaPublicityService.class.getSimpleName();
		return (MediaPublicityService) getBean(source.substring(0, 1)
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

	/**
	 * @author huhao
	 * @time 2011-10 查看待审核的详细页面
	 * @return
	 * @throws Exception
	 */
	public ActionForward goToDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		MediaPublicity mediaPublicity = this.getMainBean().find(id);
		MediaPublicityApprovalService mediaPublicityApprovalService = (MediaPublicityApprovalService) ApplicationContextHolder
				.getInstance().getBean("mediaPublicityApprovalService");
		Search search = new Search();
		search.addFilterEqual("projectNameID", id);
		SearchResult<MediaPublicityApproval> searchResult = mediaPublicityApprovalService
				.searchAndCount(search);
		request.setAttribute("MediaPublicityType",
				MediaPublicityType.characterId2TYPEMap);
		request.setAttribute("mediaPublicityList",mediaPublicity);
		request.setAttribute("mediaPublicityListApproval", searchResult
				.getResult());
		request
				.setAttribute("approvalResultSize", searchResult
						.getTotalCount());
		return mapping.findForward("goToDetail");
	}


	/**
	 * @author huhao
	 * @time 2010-10 跳转修改信息页面
	 * @return
	 * @throws Exception
	 */
	public ActionForward goToEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		MediaPublicity mediaPublicity = this.getMainBean().find(id);
		request.setAttribute("mediaPublicityType",
				MediaPublicityType.characterId2TYPEMap);
		request.setAttribute("mediaPublicityList", mediaPublicity);
		return mapping.findForward("goToEdit");
	}

	/**
	 * @author huhao
	 * @time 2010-10 修改信息
	 * @return
	 * @throws Exception
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		String id = request.getParameter("id");
		MediaPublicityForm mediaPublicityForm = (MediaPublicityForm) form;
		MediaPublicity mediaPublicity = new MediaPublicity();
		BeanUtils.copyProperties(mediaPublicity, mediaPublicityForm);
		String a = mediaPublicity.getCreatTime();
		mediaPublicity.setModifyTime(CommonUtils.toEomsStandardDate(new Date()));
		mediaPublicity.setModifyUser(userId);
		mediaPublicity.setModifyDept(deptId);
		mediaPublicity.setApprovalType(MediaPublicityType.AD_TYPE1);

		this.getMainBean().save(mediaPublicity);

		// setting forward
		return forwardlist(mapping);
	}

	/**
	 * @author huhao
	 * @time 2010-10 删除指定的未通过审核信息
	 * @return
	 * @throws Exception
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			TawSystemSessionForm sessionform = this.getUser(request);
			String userId = sessionform.getUserid();
			String deptId = sessionform.getDeptid();
			String id = request.getParameter("id");
			MediaPublicity ad = this.getMainBean().find(id);
			ad.setModifyTime(CommonUtils.toEomsStandardDate(new Date()));
			ad.setModifyUser(userId);
			ad.setModifyUser(deptId);
			ad.setDeleted(MediaPublicityType.DELETE_TRUE);
			this.getMainBean().save(ad);

		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			return forwardlist(mapping);
		}
	}

	/**
	 * @author huhao
	 * @time 2010-10 删除所有所选信息
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String myDealingList[] = StaticMethod.nullObject2String(
				request.getParameter("ids"), "").split(";");
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		for (String id : myDealingList) {
			MediaPublicity ad = getMainBean().find(id);
			ad.setDeleted(MediaPublicityType.DELETE_TRUE);
			ad.setModifyTime(CommonUtils.toEomsStandardDate(new Date()));
			ad.setModifyUser(userId);
			ad.setModifyUser(deptId);
			this.getMainBean().save(ad);
		}
		return forwardlist(mapping);
	}

	/**
	 * @author huaho
	 * @time 2010-10 新增
	 * @return
	 * @throws Exception
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MediaPublicityService mediaPublicityService = (MediaPublicityService) ApplicationContextHolder
				.getInstance().getBean("mediaPublicityService");
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		String creatTime = CommonUtils.toEomsStandardDate(new Date());
		MediaPublicityForm mediaPublicityForm = (MediaPublicityForm) form;
		MediaPublicity mediaPublicity = new MediaPublicity();
		BeanUtils.copyProperties(mediaPublicity, mediaPublicityForm);
		mediaPublicity.setApprovalType(MediaPublicityType.AD_TYPE1);
		mediaPublicity.setCreatUser(userId);
		mediaPublicity.setCreatDept(deptId);
		mediaPublicity.setCreatTime(creatTime);
		mediaPublicity.setDeleted(MediaPublicityType.DELETE_FALSE);
		mediaPublicityService.save(mediaPublicity);
		return forwardlist(mapping);
	}

	/**
	 * @author huhao
	 * @time 2010-10 待审核列表
	 * @BUG 未控制权限，待需求明确
	 * @return
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		Search search = new Search();
		Map searchMap = request.getParameterMap();
		Set keySet = searchMap.keySet();
		Iterator iterator = keySet.iterator();
		CommonUtils.getSqlFromRequestMapWithHidden(searchMap,
				MediaPublicity.class, search);
		search.addFilterEqual("deleted", MediaPublicityType.DELETE_FALSE);
		search.addFilterNotEqual("approvalType", MediaPublicityType.AD_TYPE2);
		if (!"".equals(request.getParameter("finishTimeA"))) {
			search.addFilterGreaterThan("finishTime", request
					.getParameter("finishTimeA"));
		}
		if (!"".equals(request.getParameter("finishTimeB"))) {
			search.addFilterLessThan("finishTime", request
					.getParameter("finishTimeB"));
		}
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"mediaPublicityList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.addSortDesc("creatTime");
		SearchResult<MediaPublicity> searchResult = this.getMainBean()
				.searchAndCount(search);
		request.setAttribute("mediaPublicityType",
				MediaPublicityType.characterId2TYPEMap);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("mediaPublicityList", searchResult.getResult());
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("list");
	}

	/**
	 * @author huhao
	 * @time 2010-10 审核
	 * @return
	 * @throws Exception
	 */
	public ActionForward approval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		if (!"".equals(request.getParameter("id"))) {
			String approvalType = request.getParameter("approvalType");
			MediaPublicityService mediaPublicityService = (MediaPublicityService) ApplicationContextHolder
					.getInstance().getBean("mediaPublicityService");
			MediaPublicity advertisement = mediaPublicityService.find(id);
			advertisement.setApprovalType(approvalType);
			mediaPublicityService.save(advertisement);
		}
		/**
		 * 添加审批备注信息
		 */
		MediaPublicityApprovalService mediaPublicityApprovalService = (MediaPublicityApprovalService) ApplicationContextHolder
				.getInstance().getBean("mediaPublicityApprovalService");
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		String creatTime = CommonUtils.toEomsStandardDate(new Date());
		MediaPublicityApprovalForm mediaPublicityApprovalForm = (MediaPublicityApprovalForm) form;
		MediaPublicityApproval mediaPublicityApproval = new MediaPublicityApproval();
		BeanUtils.copyProperties(mediaPublicityApproval,
				mediaPublicityApprovalForm);
		mediaPublicityApproval.setProjectNameID(id);
		mediaPublicityApproval.setApprovalTime(creatTime);
		mediaPublicityApproval.setApprovalUser(userId);
		mediaPublicityApprovalService.save(mediaPublicityApproval);
		return forwardApprovalList(mapping);
	}

	/**
	 * @author huhao
	 * @time 2010-10 审核列表
	 * @return
	 */
	public ActionForward approvalList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		Search search = new Search();
		Map searchMap = request.getParameterMap();
		Set keySet = searchMap.keySet();
		Iterator iterator = keySet.iterator();
		CommonUtils.getSqlFromRequestMapWithHidden(searchMap,
				MediaPublicity.class, search);
		if (!"".equals(request.getParameter("mediaTimeA"))&&request.getParameter("mediaTimeA")!=null) {
			search.addFilterGreaterThan("mediaTime", request
					.getParameter("mediaTimeA"));
		}
		if (!"".equals(request.getParameter("mediaTimeB"))&&request.getParameter("mediaTimeB")!=null) {
			search.addFilterLessThan("mediaTime", request
					.getParameter("mediaTimeB"));
		}
		search.addFilterEqual("deleted", MediaPublicityType.DELETE_FALSE);
		search.addFilterEqual("approvalType", MediaPublicityType.AD_TYPE1);
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"mediaPublicityApprovalList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.addSortDesc("creatTime");
		SearchResult<MediaPublicity> searchResult = this.getMainBean()
				.searchAndCount(search);
		request.setAttribute("mediaPublicityType",
				MediaPublicityType.characterId2TYPEMap);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("mediaPublicityApprovalList", searchResult
				.getResult());
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("approvalList");
	}

	/**
	 * @author huaho
	 * @time 2010-10 已审核通过列表
	 * @return
	 */
	public ActionForward approvaledList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		Search search = new Search();
		Map searchMap = request.getParameterMap();
		Set keySet = searchMap.keySet();
		Iterator iterator = keySet.iterator();
		CommonUtils.getSqlFromRequestMapWithHidden(searchMap,
				MediaPublicity.class, search);
		if (!"".equals(request.getParameter("finishTimeA"))&&request.getParameter("mediaTimeA")!=null) {
			search.addFilterGreaterThan("finishTime", request
					.getParameter("finishTimeA"));
		}
		if (!"".equals(request.getParameter("finishTimeB"))&&request.getParameter("mediaTimeB")!=null) {
			search.addFilterLessThan("finishTime", request
					.getParameter("finishTimeB"));
		}
		search.addFilterEqual("deleted", MediaPublicityType.DELETE_FALSE);
		search.addFilterEqual("approvalType", MediaPublicityType.AD_TYPE2);
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"mediaPublicityApprovalList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.addSortDesc("creatTime");
		SearchResult<MediaPublicity> searchResult = this.getMainBean()
				.searchAndCount(search);
		request.setAttribute("mediaPublicityType",
				MediaPublicityType.characterId2TYPEMap);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("mediaPublicityApprovalList", searchResult
				.getResult());
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("approvaledList");
	}
	/**
	 * @author huhao
	 * @time 2010-10 在详细页面中tab页中查看审批信息
	 * @return
	 * @throws Exception
	 */
	public ActionForward goToApproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		MediaPublicity mediaPublicity = this.getMainBean().find(id);
		MediaPublicityApprovalService mediaPublicityApprovalService = (MediaPublicityApprovalService) ApplicationContextHolder
				.getInstance().getBean("mediaPublicityApprovalService");
		Search search = new Search();
		search.addFilterEqual("projectNameID", id);
		SearchResult<MediaPublicityApproval> searchResult = mediaPublicityApprovalService
				.searchAndCount(search);
		request.setAttribute("mediaPublicityType",
				MediaPublicityType.characterId2TYPEMap);
		request.setAttribute("mediaPublicityList", mediaPublicity);
		request.setAttribute("mediaPublicityListApproval", searchResult
				.getResult());
		request
				.setAttribute("approvalResultSize", searchResult
						.getTotalCount());
		return mapping.findForward("goToApproval");

	}

	/**
	 * @author huhao
	 * @time 2010-10 查看
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 查看通过审批的信息详情
	 * @throws Exception
	 */
	public ActionForward approvaledDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		MediaPublicity mediaPublicity = this.getMainBean().find(id);
		MediaPublicityApprovalService mediaPublicityApprovalService = (MediaPublicityApprovalService) ApplicationContextHolder
				.getInstance().getBean("mediaPublicityApprovalService");
		Search search = new Search();
		search.addFilterEqual("projectNameID", id);
		SearchResult<MediaPublicityApproval> searchResult = mediaPublicityApprovalService
				.searchAndCount(search);
		request.setAttribute("mediaPublicityType",
				MediaPublicityType.characterId2TYPEMap);
		request.setAttribute("mediaPublicityList", mediaPublicity);
		request.setAttribute("mediaPublicityListApproval", searchResult
				.getResult());
		request
				.setAttribute("approvalResultSize", searchResult
						.getTotalCount());
		return mapping.findForward("approvaledDetail");
	}
	/**
	 * @author huhao
	 * @time 2010-10 多为统计跳转
	 */
	public ActionForward goToStaffMediaPublicity(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("staffMediaPublicity");
	}
	
	
	/**
	 * @author huhao
	 * @time 2010-10 多为统计
	 */
	public ActionForward staffMediaPublicity(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ss = StaticMethod.nullObject2String(
				request.getParameter("deleteIdss"), "");		
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
			} else if (rows[i].contains("TypeLikeArea")) {
				row = rows[i].substring(0, rows[i].length()
						- "TypeLikeArea".length());
				row = row.replace("0", ".");
			} else if (rows[i].contains("TypeLikeUser")) {
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
		// get the text to where condition.
		String whereCondition = " ";
		String decreatdept = request.getParameter("de0creatdeptT");
		String mediaName = request.getParameter("de0medianameT");
		String mediacontent = request
				.getParameter("de0mediacontentT");
		String mediaStyle = request
				.getParameter("de0mediastyleT");
		String mediaTime = request.getParameter("de0mediatimeT");
		String mediaAssess = request.getParameter("de0mediaassessT");
		String approvaluser = request.getParameter("de0approvaluserT");
		if (!"".equals(decreatdept)) {
			whereCondition += " and de.creatdept =" +"'"+ decreatdept + "'";
		}
		if (!"".equals(mediaName)) {
			whereCondition += " and de.mediaName like '%" + mediaName + "%'";
		}
		if (!"".equals(mediacontent)) {
			whereCondition += " and de.mediacontent like '%"
					+ mediacontent + "%'";
		}
//		if (!"".equals(mediaAssess)) {
//			whereCondition += " and de.mediaAssess like '%"
//					+ mediaAssess + "%'";
//		}
		if (!"".equals(approvaluser)) {
			whereCondition += " and de.approvaluser like '%"
					+ approvaluser + "%'";
		}

		/*
        *原始SQL
		select creatdept,approvaluser,medianame,mediacontent,mediastyle,mediatime,mediaassess,count(id)
		from dm_protectline_mediapublicity
		where deleted='0'and  approvaltype='2'
		group by creatdept,approvaluser,medianame,mediacontent,mediastyle,mediatime,mediaassess
		order by creatdept,approvaluser,medianame,mediacontent,mediastyle,mediatime,mediaassess
 
		 */
		String searchSql = "select "
				+ search
				+ " ,count(de.id) "
				+ "from  dm_protectline_mediapublicity de "
				+ " where deleted='0' and  approvaltype='2' "
				+ ""
				+ whereCondition
				+ ""
				+ "group by "
				+ " "
				+ group
				+ " "
				+ "  order by "
				+ " " + group;
		List<String> headList = new ArrayList<String>();
		for (int i = 0; i < rows.length; i++) {
			if ("de0creatdeptTypeLikeArea".equals(rows[i])) {
				headList.add("所属地市");
			}
			if ("de0medianame".equals(rows[i])) {
				headList.add("项目名称");
			}
			if ("de0mediacontent".equals(rows[i])) {
				headList.add("媒体宣传内容");
			}
			if ("de0mediastyle".equals(rows[i])) {
				headList.add("媒体宣传形式");
			}
			if ("de0mediatime".equals(rows[i])) {
				headList.add("媒体宣传时间");
			}
			if ("de0mediaassess".equals(rows[i])) {
				headList.add("宣传效果评估");
			}
			if ("de0approvaluser".equals(rows[i])) {
				headList.add("审批人");
			}
		}
		headList.add("总数");
		List tempList = TableHelper
				.verticalGrowp(rows, searchSql,
						"/deviceManagement/mediaPublicity/mediaPublicity.do?method=searchInto");
		request.setAttribute("tableList", tempList);
		request.setAttribute("headList", headList);
		request.setAttribute("checkList", checkString);

		return mapping.findForward("staffMediaPublicity");
	}
/**
 * @author huhao
 * 墙体广告统计钻取
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
		// TawSystemSessionForm sessionform = this.getUser(request);
		// String operateUserId = sessionform.getUserid();
		// String operateDeptId = sessionform.getDeptid();
		CommonSpringJdbcServiceImpl csjsi = (CommonSpringJdbcServiceImpl) ApplicationContextHolder
		.getInstance().getBean("commonSpringJdbcService");
		String search ="";
		String sql="";
		String creatDept = request.getParameter("de0creatdepttypelikearea");
		if (creatDept!=null) {
			creatDept = new String(creatDept.toString().trim().getBytes(
					"ISO-8859-1"), "utf-8");
			search=search+"and de.creatDept="+"'"+creatDept+"' ";
		}
		String medianame = request.getParameter("de0medianame");
		if (medianame!=null) {
			medianame = new String(medianame.toString().trim().getBytes("ISO-8859-1"),
					"utf-8");
			search=search+"and de.medianame="+"'"+medianame+"' ";
		}
		String mediacontent = request.getParameter("de0mediacontent");
		if (mediacontent!=null) {
			mediacontent = new String(mediacontent.toString().trim()
					.getBytes("ISO-8859-1"), "utf-8");
			search=search+"and de.mediacontent="+"'"+mediacontent+"' ";
		}
//		String finishTime = request.getParameter("de0finishTime");
//		if (finishTime!=null) {
//			finishTime = new String(finishTime.toString().trim().getBytes(
//					"ISO-8859-1"), "utf-8");
//			search=search+"and de.finishTime="+"'"+finishTime+"'  ";
//		}
		String approvaluser = request.getParameter("de0approvaluser");
		if (approvaluser!=null) {
			approvaluser = new String(approvaluser.toString().trim()
					.getBytes("ISO-8859-1"), "utf-8");
			search=search+"and de.approvaluser="+"'"+approvaluser+"'  ";
		}
//		String incompleteCause = request.getParameter("de0incompleteCause");
//		if (incompleteCause!=null) {
//			incompleteCause = new String(incompleteCause.toString().trim()
//					.getBytes("ISO-8859-1"), "utf-8");
//			search=search+"and de.incompleteCause="+"'"+incompleteCause+"'  ";
//		}

			sql = "select de.* from  dm_protectline_mediapublicity de where deleted='0' and  approvaltype='2'  "+search;
			
			List<ListOrderedMap> resultList  = csjsi.queryForList(sql);
			List list=new ArrayList();
			for (ListOrderedMap listOrderedMap : resultList) {
				MediaPublicity staff= new MediaPublicity();
				staff.setId((String)listOrderedMap.get("id"));
				staff.setCreatDept((String)listOrderedMap.get("creatDept"));
				staff.setMediaName((String)listOrderedMap.get("mediaName"));
				staff.setMediaContent((String)listOrderedMap.get("mediaContent"));
				staff.setMediaTime((String)listOrderedMap.get("mediaTime"));
				staff.setApprovalUser((String)listOrderedMap.get("approvalUser"));
				staff.setApprovalType((String)listOrderedMap.get("approvalType"));
				staff.setMediaStyle((String)listOrderedMap.get("mediaStyle"));
				staff.setRemark((String)listOrderedMap.get("remark"));
				list.add(staff);
			}
			request.setAttribute("mediaPublicityType",
					MediaPublicityType.characterId2TYPEMap);
			request.setAttribute("mediaPublicityApprovalList", list);
			request.setAttribute("resultSize", list.size());
			request.setAttribute("size", list.size());
		String next = "searchInto";
		return mapping.findForward(next);
	}
	
}
