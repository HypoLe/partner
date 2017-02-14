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
import com.boco.eoms.deviceManagement.busi.protectline.form.PublicityCatchlineApprovalForm;
import com.boco.eoms.deviceManagement.busi.protectline.form.PublicityCatchlineForm;
import com.boco.eoms.deviceManagement.busi.protectline.model.PublicityCatchline;
import com.boco.eoms.deviceManagement.busi.protectline.model.PublicityCatchlineApproval;
import com.boco.eoms.deviceManagement.busi.protectline.service.PublicityCatchlineApprovalService;
import com.boco.eoms.deviceManagement.busi.protectline.service.PublicityCatchlineService;
import com.boco.eoms.deviceManagement.busi.protectline.util.PublicityCatchlineType;
import com.boco.eoms.deviceManagement.busi.protectline.util.TableHelper;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcService;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcServiceImpl;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public final class PublicityCatchlineAction extends BaseAction {

	public PublicityCatchlineService getMainBean() {
		String source = PublicityCatchlineService.class.getSimpleName();
		return (PublicityCatchlineService) getBean(source.substring(0, 1)
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
		PublicityCatchline publicityCatchline = this.getMainBean().find(id);
		PublicityCatchlineApprovalService publicityCatchlineApprovalService = (PublicityCatchlineApprovalService) ApplicationContextHolder
				.getInstance().getBean("publicityCatchlineApprovalService");
		Search search = new Search();
		search.addFilterEqual("projectNameID", id);
		SearchResult<PublicityCatchlineApproval> searchResult = publicityCatchlineApprovalService
				.searchAndCount(search);
		request.setAttribute("publicityCatchlineType",
				PublicityCatchlineType.characterId2TYPEMap);
		request.setAttribute("publicityCatchlineList",publicityCatchline);
		request.setAttribute("publicityCatchlineListApproval", searchResult
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
		PublicityCatchline publicityCatchline = this.getMainBean().find(id);
		request.setAttribute("publicityCatchlineType",
				PublicityCatchlineType.characterId2TYPEMap);
		request.setAttribute("publicityCatchlineList", publicityCatchline);
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
		PublicityCatchlineForm publicityCatchlineForm = (PublicityCatchlineForm) form;
		PublicityCatchline publicityCatchline = new PublicityCatchline();
		BeanUtils.copyProperties(publicityCatchline, publicityCatchlineForm);
		String a = publicityCatchline.getCreatTime();
		publicityCatchline.setModifyTime(CommonUtils.toEomsStandardDate(new Date()));
		publicityCatchline.setModifyUser(userId);
		publicityCatchline.setModifyDept(deptId);
		publicityCatchline.setApprovalType(PublicityCatchlineType.AD_TYPE1);

		this.getMainBean().save(publicityCatchline);

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
			PublicityCatchline ad = this.getMainBean().find(id);
			ad.setModifyTime(CommonUtils.toEomsStandardDate(new Date()));
			ad.setModifyUser(userId);
			ad.setModifyUser(deptId);
			ad.setDeleted(PublicityCatchlineType.DELETE_TRUE);
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
			PublicityCatchline ad = getMainBean().find(id);
			ad.setDeleted(PublicityCatchlineType.DELETE_TRUE);
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
		PublicityCatchlineService publicityCatchlineService = (PublicityCatchlineService) ApplicationContextHolder
				.getInstance().getBean("publicityCatchlineService");
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
		PublicityCatchlineForm publicityCatchlineForm = (PublicityCatchlineForm) form;
		PublicityCatchline publicityCatchline = new PublicityCatchline();
		BeanUtils.copyProperties(publicityCatchline, publicityCatchlineForm);
		publicityCatchline.setApprovalType(PublicityCatchlineType.AD_TYPE1);
		publicityCatchline.setCreatUser(userId);
		publicityCatchline.setCreatArea(creatDept);
		publicityCatchline.setCreatTime(creatTime);
		publicityCatchline.setDeleted(PublicityCatchlineType.DELETE_FALSE);
		publicityCatchlineService.save(publicityCatchline);
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
				PublicityCatchline.class, search);
		search.addFilterEqual("deleted", PublicityCatchlineType.DELETE_FALSE);
		search.addFilterNotEqual("approvalType", PublicityCatchlineType.AD_TYPE2);
		if (!"".equals(request.getParameter("creatTimeA"))) {
			search.addFilterGreaterThan("creatTime", request
					.getParameter("creatTimeA"));
		}
		if (!"".equals(request.getParameter("creatTimeB"))) {
			search.addFilterLessThan("creatTime", request
					.getParameter("creatTimeB"));
		}
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"publicityCatchlineList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.addSortDesc("creatTime");
		SearchResult<PublicityCatchline> searchResult = this.getMainBean()
				.searchAndCount(search);
		request.setAttribute("publicityCatchlineType",
				PublicityCatchlineType.characterId2TYPEMap);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("publicityCatchlineList", searchResult.getResult());
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
			PublicityCatchlineService publicityCatchlineService = (PublicityCatchlineService) ApplicationContextHolder
					.getInstance().getBean("publicityCatchlineService");
			PublicityCatchline publicityCatchline = publicityCatchlineService.find(id);
			publicityCatchline.setApprovalType(approvalType);
			publicityCatchlineService.save(publicityCatchline);
		}
		/**
		 * 添加审批备注信息
		 */
		PublicityCatchlineApprovalService publicityCatchlineApprovalService = (PublicityCatchlineApprovalService) ApplicationContextHolder
				.getInstance().getBean("publicityCatchlineApprovalService");
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		String creatTime = CommonUtils.toEomsStandardDate(new Date());
		PublicityCatchlineApprovalForm publicityCatchlineApprovalForm = (PublicityCatchlineApprovalForm) form;
		PublicityCatchlineApproval publicityCatchlineApproval = new PublicityCatchlineApproval();
		BeanUtils.copyProperties(publicityCatchlineApproval,
				publicityCatchlineApprovalForm);
		publicityCatchlineApproval.setProjectNameID(id);
		publicityCatchlineApproval.setApprovalTime(creatTime);
		publicityCatchlineApproval.setApprovalUser(userId);
		publicityCatchlineApprovalService.save(publicityCatchlineApproval);
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
				PublicityCatchline.class, search);
		if (!"".equals(request.getParameter("mediaTimeA"))&&request.getParameter("mediaTimeA")!=null) {
			search.addFilterGreaterThan("mediaTime", request
					.getParameter("mediaTimeA"));
		}
		if (!"".equals(request.getParameter("mediaTimeB"))&&request.getParameter("mediaTimeB")!=null) {
			search.addFilterLessThan("mediaTime", request
					.getParameter("mediaTimeB"));
		}
		search.addFilterEqual("deleted", PublicityCatchlineType.DELETE_FALSE);
		search.addFilterEqual("approvalType", PublicityCatchlineType.AD_TYPE1);
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"publicityCatchlineApprovalList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.addSortDesc("creatTime");
		SearchResult<PublicityCatchline> searchResult = this.getMainBean()
				.searchAndCount(search);
		request.setAttribute("publicityCatchlineType",
				PublicityCatchlineType.characterId2TYPEMap);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("publicityCatchlineApprovalList", searchResult
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
				PublicityCatchline.class, search);
		if (!"".equals(request.getParameter("creatTimeA"))&&request.getParameter("creatTimeA")!=null) {
			search.addFilterGreaterThan("creatTime", request
					.getParameter("creatTimeA"));
		}
		if (!"".equals(request.getParameter("creatTimeB"))&&request.getParameter("creatTimeB")!=null) {
			search.addFilterLessThan("creatTime", request
					.getParameter("creatTimeB"));
		}
		search.addFilterEqual("deleted", PublicityCatchlineType.DELETE_FALSE);
		search.addFilterEqual("approvalType", PublicityCatchlineType.AD_TYPE2);
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"publicityCatchlineApprovalList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.addSortDesc("creatTime");
		SearchResult<PublicityCatchline> searchResult = this.getMainBean()
				.searchAndCount(search);
		request.setAttribute("publicityCatchlineType",
				PublicityCatchlineType.characterId2TYPEMap);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("publicityCatchlineApprovalList", searchResult
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
		PublicityCatchline publicityCatchline = this.getMainBean().find(id);
		PublicityCatchlineApprovalService publicityCatchlineApprovalService = (PublicityCatchlineApprovalService) ApplicationContextHolder
				.getInstance().getBean("publicityCatchlineApprovalService");
		Search search = new Search();
		search.addFilterEqual("projectNameID", id);
		SearchResult<PublicityCatchlineApproval> searchResult = publicityCatchlineApprovalService
				.searchAndCount(search);
		request.setAttribute("publicityCatchlineType",
				PublicityCatchlineType.characterId2TYPEMap);
		request.setAttribute("publicityCatchlineList", publicityCatchline);
		request.setAttribute("publicityCatchlineListApproval", searchResult
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
		PublicityCatchline publicityCatchline = this.getMainBean().find(id);
		PublicityCatchlineApprovalService publicityCatchlineApprovalService = (PublicityCatchlineApprovalService) ApplicationContextHolder
				.getInstance().getBean("publicityCatchlineApprovalService");
		Search search = new Search();
		search.addFilterEqual("projectNameID", id);
		SearchResult<PublicityCatchlineApproval> searchResult = publicityCatchlineApprovalService
				.searchAndCount(search);
		request.setAttribute("publicityCatchlineType",
				PublicityCatchlineType.characterId2TYPEMap);
		request.setAttribute("publicityCatchlineList", publicityCatchline);
		request.setAttribute("publicityCatchlineListApproval", searchResult
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
	public ActionForward goToStaffPublicityCatchline(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward("staffPublicityCatchline");
	}
	
	
	/**
	 * @author huhao
	 * @time 2010-10 多为统计
	 */
	public ActionForward staffPublicityCatchline(ActionMapping mapping,
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
		String creatarea = request.getParameter("de0creatareaT");
		String publicitycatchline = request.getParameter("de0publicitycatchlineT");
		String approvaluser = request.getParameter("de0approvaluserT");
		if (!"".equals(creatarea)) {
			whereCondition += " and de.creatarea =" +"'"+ creatarea + "'";
		}
		if (!"".equals(publicitycatchline)) {
			whereCondition += " and de.publicitycatchline like '%" + publicitycatchline + "%'";
		}

		if (!"".equals(approvaluser)) {
			whereCondition += " and de.approvaluser = '"
					+ approvaluser + "'";
		}

		/*
        *原始SQL
		select creatArea,approvaluser,publicityCatchline,count(id)
		from dm_protectline_publicityCarchline
		where deleted='0'and  approvaltype='2'
		group by creatArea,approvaluser,publicityCatchline
		order by creatArea,approvaluser,publicityCatchline
 
		 */
		String searchSql = "select "
				+ search
				+ " ,count(de.publicityCatchline) "
				+ "from  dm_protectline_publicityCarchline de "
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
			if ("de0creatareaTypeLikeArea".equals(rows[i])) {
				headList.add("所属地市");
			}
			if ("de0publicitycatchline".equals(rows[i])) {
				headList.add("媒体宣传标语");
			}
			if ("de0approvaluser".equals(rows[i])) {
				headList.add("审批人");
			}
		}
		headList.add("总数");
		List tempList = TableHelper
				.verticalGrowp(rows, searchSql,
						"/deviceManagement/publicityCatchline/publicityCatchline.do?method=searchInto");
		request.setAttribute("tableList", tempList);
		request.setAttribute("headList", headList);
		request.setAttribute("checkList", checkString);

		return mapping.findForward("staffPublicityCatchline");
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
		String creatArea = request.getParameter("de0creatareatypelikearea");
		if (creatArea!=null) {
			creatArea = new String(creatArea.toString().trim().getBytes(
					"ISO-8859-1"), "utf-8");
			search=search+"and de.creatArea="+"'"+creatArea+"' ";
		}
		String publicityCatchline = request.getParameter("de0publicitycatchline");
		if (publicityCatchline!=null) {
			publicityCatchline = new String(publicityCatchline.toString().trim().getBytes("ISO-8859-1"),
					"utf-8");
			search=search+"and de.publicityCatchline="+"'"+publicityCatchline+"' ";
		}
		String approvaluser = request.getParameter("de0approvaluser");
		if (approvaluser!=null) {
			approvaluser = new String(approvaluser.toString().trim()
					.getBytes("ISO-8859-1"), "utf-8");
			search=search+"and de.approvaluser="+"'"+approvaluser+"'  ";
		}

			sql = "select de.* from  dm_protectline_publicityCarchline de where deleted='0' and  approvaltype='2'  "+search;
			
			List<ListOrderedMap> resultList  = csjsi.queryForList(sql);
			List list=new ArrayList();
			for (ListOrderedMap listOrderedMap : resultList) {
				PublicityCatchline staff= new PublicityCatchline();
				staff.setId((String)listOrderedMap.get("id"));
				staff.setApprovalUser((String)listOrderedMap.get("approvalUser"));
				staff.setApprovalType((String)listOrderedMap.get("approvalType"));
				staff.setCreatTime((String)listOrderedMap.get("creatTime"));
				staff.setPublicityCatchline((String)listOrderedMap.get("publicityCatchline"));
				staff.setRemark((String)listOrderedMap.get("remark"));
				list.add(staff);
			}
			request.setAttribute("publicityCatchlineType",
					PublicityCatchlineType.characterId2TYPEMap);
			request.setAttribute("publicityCatchlineApprovalList", list);
			request.setAttribute("resultSize", list.size());
			request.setAttribute("size", list.size());
		String next = "searchInto";
		return mapping.findForward(next);
	}
	
}
