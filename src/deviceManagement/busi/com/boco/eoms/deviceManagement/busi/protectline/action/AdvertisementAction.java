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
import com.boco.eoms.deviceManagement.busi.protectline.form.AdvertisementForm;
import com.boco.eoms.deviceManagement.busi.protectline.model.Advertisement;
import com.boco.eoms.deviceManagement.busi.protectline.model.AdvertisementApproval;
import com.boco.eoms.deviceManagement.busi.protectline.service.AdvertisementApprovalService;
import com.boco.eoms.deviceManagement.busi.protectline.service.AdvertisementService;
import com.boco.eoms.deviceManagement.busi.protectline.util.AdvertisementType;
import com.boco.eoms.deviceManagement.busi.protectline.util.TableHelper;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcService;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcServiceImpl;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public final class AdvertisementAction extends BaseAction {

	public AdvertisementService getMainBean() {
		String source = AdvertisementService.class.getSimpleName();
		return (AdvertisementService) getBean(source.substring(0, 1)
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
		Advertisement advertisement = this.getMainBean().find(id);
		AdvertisementApprovalService advertisementApprovalService = (AdvertisementApprovalService) ApplicationContextHolder
				.getInstance().getBean("advertisementApprovalService");
		Search search = new Search();
		search.addFilterEqual("projectNameID", id);
		SearchResult<AdvertisementApproval> searchResult = advertisementApprovalService
				.searchAndCount(search);
		request.setAttribute("advertisementType",
				AdvertisementType.characterId2TYPEMap);
		request.setAttribute("advertisementList", advertisement);
		request.setAttribute("advertisementListApproval", searchResult
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
		Advertisement advertisement = this.getMainBean().find(id);
		request.setAttribute("advertisementType",
				AdvertisementType.characterId2TYPEMap);
		request.setAttribute("advertisementList", advertisement);
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
		AdvertisementForm advertisementForm = (AdvertisementForm) form;
		Advertisement advertisement = new Advertisement();
		BeanUtils.copyProperties(advertisement, advertisementForm);
		String a = advertisement.getCreatTime();
		String b = advertisement.getFinishTime();
		advertisement.setModifyTime(CommonUtils.toEomsStandardDate(new Date()));
		advertisement.setModifyUser(userId);
		advertisement.setModifyDept(deptId);
		advertisement.setApprovalType(AdvertisementType.AD_TYPE1);

		this.getMainBean().save(advertisement);

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
			Advertisement ad = this.getMainBean().find(id);
			ad.setModifyTime(CommonUtils.toEomsStandardDate(new Date()));
			ad.setModifyUser(userId);
			ad.setModifyUser(deptId);
			ad.setDeleted(AdvertisementType.DELETE_TRUE);
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
			Advertisement ad = getMainBean().find(id);
			ad.setDeleted(AdvertisementType.DELETE_TRUE);
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
		AdvertisementService advertisementService = (AdvertisementService) ApplicationContextHolder
				.getInstance().getBean("advertisementService");
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		String creatDept = "";
		// get user areaid Model's creatDept=areaid
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
		String where = " and dept_mag_id= " + deptId;
		List parentDeptList = partnerDeptMgr.getPartnerDepts(where);
		if (!(parentDeptList.equals(null) || parentDeptList.size() == 0)) {
			PartnerDept parentDept = (PartnerDept) parentDeptList.get(0);
			creatDept = parentDept.getAreaId();
		}
		String creatTime = CommonUtils.toEomsStandardDate(new Date());
		AdvertisementForm advertisementForm = (AdvertisementForm) form;
		Advertisement advertisement = new Advertisement();
		BeanUtils.copyProperties(advertisement, advertisementForm);
		advertisement.setApprovalType(AdvertisementType.AD_TYPE1);
		advertisement.setCreatUser(userId);
		advertisement.setCreatDept(creatDept);
		advertisement.setCreatTime(creatTime);
		advertisement.setDeleted(AdvertisementType.DELETE_FALSE);
		advertisementService.save(advertisement);
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
				Advertisement.class, search);
		search.addFilterEqual("deleted", AdvertisementType.DELETE_FALSE);
		//search.addFilterNotEqual("approvalType", AdvertisementType.AD_TYPE2);
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
						"advertisementList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.addSortDesc("creatTime");
		SearchResult<Advertisement> searchResult = this.getMainBean()
				.searchAndCount(search);
		request.setAttribute("advertisementType",
				AdvertisementType.characterId2TYPEMap);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("advertisementList", searchResult.getResult());
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
			AdvertisementService advertisementService = (AdvertisementService) ApplicationContextHolder
					.getInstance().getBean("advertisementService");
			Advertisement advertisement = advertisementService.find(id);
			advertisement.setApprovalType(approvalType);
			advertisementService.save(advertisement);
		}
		/**
		 * 添加审批备注信息
		 */
		AdvertisementApprovalService advertisementApprovalService = (AdvertisementApprovalService) ApplicationContextHolder
				.getInstance().getBean("advertisementApprovalService");
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		String creatTime = CommonUtils.toEomsStandardDate(new Date());
		AdvertisementApprovalForm advertisementApprovalForm = (AdvertisementApprovalForm) form;
		AdvertisementApproval advertisementApproval = new AdvertisementApproval();
		BeanUtils.copyProperties(advertisementApproval,
				advertisementApprovalForm);
		advertisementApproval.setProjectNameID(id);
		advertisementApproval.setApprovalTime(creatTime);
		advertisementApproval.setApprovalUser(userId);
		advertisementApprovalService.save(advertisementApproval);
		return forwardApprovalList(mapping);
	}

	/**
	 * @author huhao
	 * @time 2010-10 审核列表
	 * @return
	 */
	public ActionForward approvalList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TawSystemSessionForm sessionform=this.getUser(request);
	String approvalUser=sessionform.getUserid();
		Search search = new Search();
		search.addFilterEqual("approvalUser", approvalUser);
		Map searchMap = request.getParameterMap();
		Set keySet = searchMap.keySet();
		Iterator iterator = keySet.iterator();
		CommonUtils.getSqlFromRequestMapWithHidden(searchMap,
				Advertisement.class, search);
		if (!"".equals(request.getParameter("finishTimeA"))) {
			search.addFilterGreaterThan("finishTime", request
					.getParameter("finishTimeA"));
		}
		if (!"".equals(request.getParameter("finishTimeB"))) {
			search.addFilterLessThan("finishTime", request
					.getParameter("finishTimeB"));
		}
		search.addFilterEqual("deleted", AdvertisementType.DELETE_FALSE);
		search.addFilterEqual("approvalType", AdvertisementType.AD_TYPE1);
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"advertisementApprovalList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.addSortDesc("creatTime");
		SearchResult<Advertisement> searchResult = this.getMainBean()
				.searchAndCount(search);
		request.setAttribute("advertisementType",
				AdvertisementType.characterId2TYPEMap);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("advertisementApprovalList", searchResult
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
				Advertisement.class, search);
		if (!"".equals(request.getParameter("finishTimeA"))) {
			search.addFilterGreaterThan("finishTime", request
					.getParameter("finishTimeA"));
		}
		if (!"".equals(request.getParameter("finishTimeB"))) {
			search.addFilterLessThan("finishTime", request
					.getParameter("finishTimeB"));
		}
		search.addFilterEqual("deleted", AdvertisementType.DELETE_FALSE);
		search.addFilterEqual("approvalType", AdvertisementType.AD_TYPE2);
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"advertisementApprovalList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.addSortDesc("creatTime");
		SearchResult<Advertisement> searchResult = this.getMainBean()
				.searchAndCount(search);
		request.setAttribute("advertisementType",
				AdvertisementType.characterId2TYPEMap);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("advertisementApprovalList", searchResult
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
		Advertisement advertisement = this.getMainBean().find(id);
		AdvertisementApprovalService advertisementApprovalService = (AdvertisementApprovalService) ApplicationContextHolder
				.getInstance().getBean("advertisementApprovalService");
		Search search = new Search();
		search.addFilterEqual("projectNameID", id);
		SearchResult<AdvertisementApproval> searchResult = advertisementApprovalService
				.searchAndCount(search);
		request.setAttribute("advertisementType",
				AdvertisementType.characterId2TYPEMap);
		request.setAttribute("advertisementList", advertisement);
		request.setAttribute("advertisementListApproval", searchResult
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
		Advertisement advertisement = this.getMainBean().find(id);
		AdvertisementApprovalService advertisementApprovalService = (AdvertisementApprovalService) ApplicationContextHolder
				.getInstance().getBean("advertisementApprovalService");
		Search search = new Search();
		search.addFilterEqual("projectNameID", id);
		SearchResult<AdvertisementApproval> searchResult = advertisementApprovalService
				.searchAndCount(search);
		request.setAttribute("advertisementType",
				AdvertisementType.characterId2TYPEMap);
		request.setAttribute("advertisementList", advertisement);
		request.setAttribute("advertisementListApproval", searchResult
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
	public ActionForward goToStaffAdivertisement(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("staffAdvertisement");
	}

	/**
	 * @author huhao
	 * @time 2010-10 多为统计
	 */
	public ActionForward staffAdivertisement(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String ss = StaticMethod.nullObject2String(
				request.getParameter("deleteIdss"), "");		
		ss+="de0finishtime;de0advertisementamount;de0incompletecause;";
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
		search=search+",de.finishtime as de0finishtime,de.advertisementamount as de0advertisementamount,de.incompletecause as de0incompletecause";
		// get the text to where condition.
		String whereCondition = " ";
		String decreatdept = request.getParameter("de0creatdeptT");
		String deprojectname = request.getParameter("de0projectnameT");
		String deadvertisementcontent = request
				.getParameter("de0advertisementcontentT");
		String deadvertisementarea = request
				.getParameter("de0advertisementareaT");
		String deadvertisementamount = request
				.getParameter("de0advertisementamountT");
		String definishtime = request.getParameter("de0finishtimeT");
		String deincompletecause = request.getParameter("de0incompletecauseT");
		String deapprovaluser = request.getParameter("de0approvaluserT");
		if (!"".equals(decreatdept)) {
			whereCondition += " and de.creatdept = '" + decreatdept + "'";
		}
		if (!"".equals(deprojectname)) {
			whereCondition += " and de.projectname like '%" + deprojectname + "%'";
		}
		if (!"".equals(deadvertisementcontent)) {
			whereCondition += " and de.advertisementcontent like '%"
					+ deadvertisementcontent + "%'";
		}
		if (!"".equals(deadvertisementarea)) {
			whereCondition += " and de.advertisementarea like '%"
					+ deadvertisementarea + "%'";
		}
		if (!"".equals(deadvertisementarea)) {
			whereCondition += " and de.advertisementarea like '%"
					+ deadvertisementarea + "%'";
		}
		// if (!"".equals(deadvertisementamount)) {
		// whereCondition += " and de.advertisementamount = " +
		// deadvertisementamount;
		// }
		// if (!"".equals(definishtime)) {
		// whereCondition += " and de.finishtime like '%" + definishtime + "%'";
		// }
		// if (!"".equals(deincompletecause)) {
		// whereCondition += " and de.incompletecause like '%" +
		// deincompletecause + "%'";
		// }
		if (!"".equals(deapprovaluser)) {
			whereCondition += " and de.approvaluser =  '" + deapprovaluser+"'";
		}
		/**
		 * 原始sql select
		 * de.creatdept,de.projectname,de.advertisementcontent,de.advertisementarea,de.advertisementamount,de.finishtime,de.incompletecause,de.approvaluser,count(de.id)
		 * from dm_protectline_advertisement de where de.deleted='0' and
		 * de.approvaltype='2' group by
		 * de.creatdept,de.projectname,de.advertisementcontent,de.advertisementarea,de.advertisementamount,de.finishtime,de.incompletecause,de.approvaluser
		 * order by
		 * de.creatdept,de.projectname,de.advertisementcontent,de.advertisementarea,de.advertisementamount,de.finishtime,de.incompletecause,de.approvaluser
		 */
		String searchSql = "select "
				+ search
				+ " ,count(de.id) "
				+ "from dm_protectline_advertisement de "
				+ " where de.deleted='0' and de.approvaltype='2'  "
				+ " "
				+ whereCondition
				+ " "
				+ "  group by de.finishtime,de.advertisementamount,de.incompletecause,"
				+ " "
				+ group
				+ " "
				+ "  order by de.finishtime,de.advertisementamount,de.incompletecause,"
				+ " " + group;
		List<String> headList = new ArrayList<String>();
		for (int i = 0; i < rows.length; i++) {
			if ("de0creatdeptTypeLikeArea".equals(rows[i])) {
				headList.add("所属地市");
			}
			if ("de0projectname".equals(rows[i])) {
				headList.add("项目名称");
			}
			if ("de0advertisementarea".equals(rows[i])) {
				headList.add("广告地点");
			}
			if ("de0advertisementcontent".equals(rows[i])) {
				headList.add("广告内容");
			}
			if ("de0finishtime".equals(rows[i])) {
				headList.add("完成时间");
			}
			if ("de0incompletecause".equals(rows[i])) {
				headList.add("未完成原因");
			}
			if ("de0advertisementamount".equals(rows[i])) {
				headList.add("广告数量");
			}
			if ("de0approvaluserTypeLikeUser".equals(rows[i])) {
				headList.add("审批人");
			}
		}
		headList.add("总数");
		List tempList = TableHelper
				.verticalGrowp(rows, searchSql,
						"/deviceManagement/advertisement/advertisement.do?method=searchInto");
		request.setAttribute("tableList", tempList);
		request.setAttribute("headList", headList);
		request.setAttribute("checkList", checkString);

		return mapping.findForward("staffAdvertisement");
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
		String projectName = request.getParameter("de0projectname");
		if (projectName!=null) {
			projectName = new String(projectName.toString().trim().getBytes("ISO-8859-1"),
					"utf-8");
			search=search+"and de.projectName="+"'"+projectName+"' ";
		}
		String advertisementArea = request.getParameter("de0advertisementarea");
		if (advertisementArea!=null) {
			advertisementArea = new String(advertisementArea.toString().trim()
					.getBytes("ISO-8859-1"), "utf-8");
			search=search+"and de.advertisementArea="+"'"+advertisementArea+"' ";
		}
		String advertisementContent = request.getParameter("de0advertisementcontent");
		if (advertisementContent!=null) {
			advertisementContent = new String(advertisementContent.toString().trim().getBytes("ISO-8859-1"),
					"utf-8");
			search=search+"and de.advertisementContent="+"'"+advertisementContent+"' ";
		}
		String finishTime = request.getParameter("de0finishtime");
		if (finishTime!=null) {
			finishTime = new String(finishTime.toString().trim().getBytes(
					"ISO-8859-1"), "utf-8");
			search=search+"and de.finishTime="+"'"+finishTime+"'  ";
		}
		String approvalUser = request.getParameter("de0approvalusertypelikeuser");
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

			sql = "select de.* from dm_protectline_advertisement de where de.deleted='0' and de.approvaltype='2'  "+search;
			
			List<ListOrderedMap> resultList  = csjsi.queryForList(sql);
			List list=new ArrayList();
			for (ListOrderedMap listOrderedMap : resultList) {
				Advertisement staff= new Advertisement();
				staff.setId((String)listOrderedMap.get("id"));
				staff.setCreatDept((String)listOrderedMap.get("creatDept"));
				staff.setProjectName((String)listOrderedMap.get("projectName"));
				staff.setAdvertisementArea((String)listOrderedMap.get("advertisementArea"));
				staff.setAdvertisementContent((String)listOrderedMap.get("advertisementContent"));
				staff.setFinishTime((String)listOrderedMap.get("finishTime"));
				staff.setApprovalUser((String)listOrderedMap.get("approvalUser"));
				staff.setIncompleteCause((String)listOrderedMap.get("incompleteCause"));
				staff.setApprovalType((String)listOrderedMap.get("approvalType"));
				staff.setAdvertisementAmount((String)listOrderedMap.get("advertisementAmount"));
				staff.setRemark((String)listOrderedMap.get("remark"));
				list.add(staff);
			}
			request.setAttribute("advertisementType",
					AdvertisementType.characterId2TYPEMap);
			request.setAttribute("advertisementApprovalList", list);
			request.setAttribute("resultSize", list.size());
			request.setAttribute("size", list.size());
		String next = "searchInto";
		return mapping.findForward(next);
	}
	/**
	 * @author huhao
	 * @time 2010-10 批量导入
	 * @BUG 未测试，未使用
	 */
	// public ActionForward importRecord(ActionMapping mapping, ActionForm form,
	// HttpServletRequest request, HttpServletResponse response) {
	// response.setCharacterEncoding("utf-8");
	// AdvertisementForm uploadForm = (AdvertisementForm) form;
	// FormFile formFile = uploadForm.getImportFile();
	//		
	// TawSystemSessionForm sessionform = this.getUser(request);
	// String userId = sessionform.getUserid();
	// String deptId = sessionform.getDeptid();
	// String phone = sessionform.getContactMobile();
	// String roleId = sessionform.getRoleid();
	//		
	// Map<String,String> params = new HashMap<String,String>();
	// params.put("userId", userId);
	// params.put("deptId", deptId);
	// params.put("phone", phone);
	// params.put("roleId", roleId);
	//		
	// PrintWriter writer = null;
	// try{
	// writer = response.getWriter();
	// ImportResult result =
	// this.getMainBean().importFromFile(formFile.getInputStream(),formFile.getFileName(),params);
	// if(result.getResultCode().equals("200")) {
	// writer.write(
	// new Gson().toJson(new ImmutableMap.Builder<String, String>()
	// .put("success", "true")
	// .put("msg", "ok")
	// .put("infor", "'导入成功,共计导入"+result.getImportCount()+"条记录'").build()));
	// }
	// }catch(Exception e){
	// e.printStackTrace();
	// writer.write(
	// new Gson().toJson(new ImmutableMap.Builder<String, String>()
	// .put("success", "false")
	// .put("msg", "failure")
	// .put("infor", e.getMessage()).build()));
	// }finally{
	// if(writer != null){
	// writer.close();
	// }
	// }
	// return null;
	// }
	//	
}
