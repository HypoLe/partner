package com.boco.eoms.partner.baseinfo.webapp.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.partner.baseinfo.mgr.AreaDeptTreeMgr;
import com.boco.eoms.partner.baseinfo.mgr.IResumeMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerUser;
import com.boco.eoms.partner.baseinfo.model.Resume;
import com.boco.eoms.partner.baseinfo.util.AptitudeConstants;
import com.boco.eoms.partner.baseinfo.util.ResumeConstants;
import com.boco.eoms.partner.baseinfo.util.RoleIdList;
import com.boco.eoms.partner.baseinfo.webapp.form.ResumeForm;

/**
 * <p>
 * Title:工作简历
 * </p>
 * <p>
 * Description:工作简历
 * </p>
 * <p>
 * Fri Dec 18 16:50:43 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
public final class ResumeAction extends BaseAction {

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
	 * 新增人员信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward newExpert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String isPartnerTemp = request.getParameter("isPartner");
		int isPartner = -1;
		try {
			isPartner = Integer.parseInt(isPartnerTemp);
		} catch (NumberFormatException e) {
		}

		request.setAttribute("isPartner", isPartner);
		String nodeId = StaticMethod
				.null2String(request.getParameter("nodeId"));// nodeId
																// 是地市的nodeId
		request.setAttribute("nodeId", nodeId);
		String type = request.getParameter("pnrType");
		request.setAttribute("pnrType", type);
		String id = StaticMethod.null2String(request.getParameter("id"));
		request.setAttribute("id", id);
		PartnerUserMgr partnerUserMgr;
		PartnerUser partnerUser;
		String personCardNo1 = "";
		if (!id.equals("")) {
			partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
			partnerUser = partnerUserMgr.getPartnerUser(id);
			personCardNo1 = partnerUser.getPersonCardNo();
			request.setAttribute("personCardNo1", personCardNo1);
		}

		return mapping.findForward("newExpert");
	}

	/**
	 * 新增工作简历
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
	 * 修改工作简历
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
		String personCardNo = StaticMethod.null2String(request
				.getParameter("personCardNo"));
		if ("".equals(personCardNo)) {
			personCardNo = StaticMethod.nullObject2String(request
					.getAttribute("personCardNo"));
		}

		IResumeMgr resumeMgr = (IResumeMgr) getBean("iResumeMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		if (!id.equals("")) {
			Resume resume = resumeMgr.getResume(id);
			ResumeForm resumeForm = (ResumeForm) convert(resume);
			updateFormBean(mapping, request, resumeForm);
		} else {
			PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
			List list = partnerUserMgr.getPartnerUsers(" and personCardNo = '"
					+ personCardNo + "'");
			if (list.size() > 0) {
				PartnerUser partnerUser = (PartnerUser) list.get(0);
				request.setAttribute("personnelName", partnerUser.getName());
				request.setAttribute("deptid", partnerUser.getDeptId());
				request.setAttribute("postState", partnerUser.getPostState());
			}
			request.setAttribute("idCardNo", personCardNo);
		}

		String nodeId = StaticMethod
				.null2String(request.getParameter("nodeId"));// nodeId
																// 是地市的nodeId
		// 取出所有厂家的集合
		RoleIdList roleIdList = (RoleIdList) getBean("roleIdList");
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
		List partnerList = areaDeptTreeMgr.getNextLevelAreaDeptTrees(String
				.valueOf(roleIdList.getFactoryDictType()));
		request.setAttribute("partnerList", partnerList);

		request.setAttribute("nodeId", nodeId);
		request.setAttribute("personCardNo", personCardNo);
		return mapping.findForward("edit");
	}

	/**
	 * 查看工作简历详细信息 author:wangjunfeng 2010-03-02
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward detail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IResumeMgr resumeMgr = (IResumeMgr) getBean("iResumeMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));

		Resume resume = resumeMgr.getResume(id);
		ResumeForm resumeForm = (ResumeForm) convert(resume);

		updateFormBean(mapping, request, resumeForm);
		return mapping.findForward("detail");
	}

	/**
	 * 修改工作简历
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editNor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String personCardNo = StaticMethod.null2String(request
				.getParameter("personCardNo"));

		IResumeMgr resumeMgr = (IResumeMgr) getBean("iResumeMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		if (!id.equals("")) {
			Resume resume = resumeMgr.getResume(id);
			ResumeForm resumeForm = (ResumeForm) convert(resume);
			updateFormBean(mapping, request, resumeForm);
		} else {
			PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
			List list = partnerUserMgr.getPartnerUsers(" and personCardNo = '"
					+ personCardNo + "'");
			if (list.size() > 0) {
				PartnerUser partnerUser = (PartnerUser) list.get(0);
				request.setAttribute("personnelName", partnerUser.getName());
			}
			request.setAttribute("idCardNo", personCardNo);
		}

		String nodeId = StaticMethod
				.null2String(request.getParameter("nodeId"));// nodeId
																// 是地市的nodeId
		request.setAttribute("nodeId", nodeId);
		request.setAttribute("personCardNo", personCardNo);
		return mapping.findForward("editNor");
	}

	/**
	 * 保存工作简历
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
		String userId = this.getUser(request).getUserid();
		String personCardNo = StaticMethod.null2String(request
				.getParameter("personCardNo"));
		String personid = StaticMethod.null2String(request.getParameter("id"));
		IResumeMgr resumeMgr = (IResumeMgr) getBean("iResumeMgr");
		ResumeForm resumeForm = (ResumeForm) form;
		boolean isNew = (null == resumeForm.getId() || "".equals(resumeForm
				.getId()));
		Resume resume = (Resume) convert(resumeForm);
		resume.setIsDel(Integer.valueOf(0));
		if (isNew) {
			resume.setAddTime(StaticMethod.getLocalTime());
			resume.setAddUser(userId);
			resumeMgr.saveResume(resume);
		} else {
			resume.setUpdateUser(userId);
			resume.setUpdateTime(StaticMethod.getLocalTime());
			resumeMgr.saveResume(resume);
		}
		request.setAttribute("operType", "save");
		if (personCardNo.equals("")) {
			request.setAttribute("personCardNo", resume.getIdCardNo());
		} else {
			request.setAttribute("personCardNo", personCardNo);
		}
		String nodeId = StaticMethod
				.null2String(request.getParameter("nodeId"));// nodeId
																// 是地市的nodeId
		request.setAttribute("nodeId", nodeId);
		return edit(mapping, form, request, response);
	}

	/**
	 * 保存工作简历
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveNor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userId = this.getUser(request).getUserid();
		String personCardNo = StaticMethod.null2String(request
				.getParameter("personCardNo"));
		IResumeMgr resumeMgr = (IResumeMgr) getBean("iResumeMgr");
		ResumeForm resumeForm = (ResumeForm) form;
		boolean isNew = (null == resumeForm.getId() || "".equals(resumeForm
				.getId()));
		Resume resume = (Resume) convert(resumeForm);
		resume.setIsDel(Integer.valueOf(0));
		if (isNew) {
			resume.setAddTime(StaticMethod.getLocalTime());
			resume.setAddUser(userId);
			resumeMgr.saveResume(resume);
		} else {
			resume.setUpdateUser(userId);
			resume.setUpdateTime(StaticMethod.getLocalTime());
			resumeMgr.saveResume(resume);
		}
		request.setAttribute("operType", "save");
		if (personCardNo.equals("")) {
			request.setAttribute("personCardNo", resume.getIdCardNo());
		} else {
			request.setAttribute("personCardNo", personCardNo);
		}
		String nodeId = StaticMethod
				.null2String(request.getParameter("nodeId"));// nodeId
																// 是地市的nodeId
		request.setAttribute("nodeId", nodeId);
		return mapping.findForward("search");
	}

	/**
	 * 删除工作简历
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
		String userId = this.getUser(request).getUserid();
		IResumeMgr resumeMgr = (IResumeMgr) getBean("iResumeMgr");
		String personCardNo = StaticMethod.null2String(request
				.getParameter("personCardNo"));
		String id = StaticMethod.null2String(request.getParameter("id"));
		if (!id.equals("")) {
			Resume resume = resumeMgr.getResume(id);
			resume.setIsDel(Integer.valueOf(1));
			resume.setDelTime(StaticMethod.getLocalTime());
			resume.setDelUser(userId);
			personCardNo = resume.getIdCardNo();
			resumeMgr.saveResume(resume);
		}
		String ids[] = request.getParameterValues("ids");
		if (ids != null) {
			for (int i = 0; i < ids.length; i++) {
				Resume resume = resumeMgr.getResume(ids[i]);
				personCardNo = resume.getIdCardNo();
				resume.setIsDel(Integer.valueOf(1));
				resume.setDelTime(StaticMethod.getLocalTime());
				resume.setDelUser(userId);
				resumeMgr.saveResume(resume);
			}
		}
		request.getSession().setAttribute("personCardNo", personCardNo);
		request.setAttribute("personCardNo", personCardNo);
		String nodeId = StaticMethod
				.null2String(request.getParameter("nodeId"));// nodeId
																// 是地市的nodeId
		request.setAttribute("nodeId", nodeId);
		return searchOne(mapping, form, request, response);
	}

	/**
	 * 分页显示工作简历列表
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
				ResumeConstants.RESUME_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(
				GenericValidator.isBlankOrNull(request
						.getParameter(pageIndexName)) ? 0 : (Integer
						.parseInt(request.getParameter(pageIndexName)) - 1));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String personnelName = StaticMethod.null2String(request
				.getParameter("personnelName"));
		String idCardNo = StaticMethod.null2String(request
				.getParameter("idCardNo"));
		String provider = StaticMethod.null2String(request
				.getParameter("provider"));
		String state = StaticMethod.null2String(request.getParameter("state"));
		String post = StaticMethod.null2String(request.getParameter("post"));
		String ccdsString = StaticMethod.null2String(request
				.getParameter("ccds"));
		String ccdeString = StaticMethod.null2String(request
				.getParameter("ccde"));
		String ddsString = StaticMethod
				.null2String(request.getParameter("dds"));
		String ddeString = StaticMethod
				.null2String(request.getParameter("dde"));
		StringBuffer where = new StringBuffer();
		Date ccds = null;
		Date ccde = null;
		Date dds = null;
		Date dde = null;
		where.append(" where isDel=0 ");
		if (!"".equals(personnelName)) {
			where.append(" and personnelName like '%");
			where.append(personnelName);
			where.append("%' ");
		}
		if (!"".equals(idCardNo)) {
			where.append(" and idCardNo = '");
			where.append(idCardNo);
			where.append("' ");
		}
		if (!"".equals(provider)) {
			where.append(" and provider = '");
			where.append(provider);
			where.append("' ");
		}
		if (!"".equals(state)) {
			where.append(" and state = '");
			where.append(state);
			where.append("' ");
		}
		if (!"".equals(post)) {
			where.append(" and post = '");
			where.append(post);
			where.append("' ");
		}
		if (!"".equals(ccdeString) && !"".equals(ccdsString)) {
			request.setAttribute("ccde", ccdeString);
			request.setAttribute("ccds", ccdsString);
			ccde = sdf.parse(ccdeString);
			ccds = sdf.parse(ccdsString);
		}
		if (!"".equals(ddeString) && !"".equals(ddsString)) {
			request.setAttribute("dds", ddsString);
			request.setAttribute("dde", ddeString);
			dds = sdf.parse(ddsString);
			dde = sdf.parse(ddeString);
		}
		IResumeMgr resumeMgr = (IResumeMgr) getBean("iResumeMgr");
		Map map = (Map) resumeMgr.getResumes(pageIndex, pageSize,
				where.toString(), ccds, ccde, dds, dde);
		List list = (List) map.get("result");
		request.setAttribute(ResumeConstants.RESUME_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}

	/**
	 * 判断工作简历的身份证号是否存在ajax
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward validationResume(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String reId = request.getParameter("reId");
		String idCardNo = request.getParameter("idCardNo");
		IResumeMgr resumeMgr = (IResumeMgr) getBean("iResumeMgr");
		Resume resume = resumeMgr.getResume(reId);
		StringBuffer where = new StringBuffer();
		where.append(" where isDel=0 and idCardNo = '");
		where.append(idCardNo);
		where.append("'");
		List list = resumeMgr.getResumes(where.toString());

		// 设置返回页面的信息
		JSONArray json = new JSONArray();
		JSONObject jitem = new JSONObject();
		if (list.size() > 0) {
			if (StaticMethod.null2String(resume.getIdCardNo()).equals(idCardNo)) {
				jitem.put("message", true);
			} else {
				jitem.put("message", true);
			}
		} else {
			jitem.put("message", true);
		}
		json.put(jitem);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(json.toString());
		return null;
	}

	/**
	 * 分页显示人员简历(one)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchOne(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				AptitudeConstants.APTITUDE_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(
				GenericValidator.isBlankOrNull(request
						.getParameter(pageIndexName)) ? 0 : (Integer
						.parseInt(request.getParameter(pageIndexName)) - 1));
		IResumeMgr resumeMgr = (IResumeMgr) getBean("iResumeMgr");
		StringBuffer where = new StringBuffer();
		String personCardNo = StaticMethod.null2String(request
				.getParameter("personCardNo"));
		Object removePersonCardNo = request.getSession().getAttribute(
				"personCardNo");
		where.append(" where isDel=0");
		if (!"".equals(removePersonCardNo) && null != removePersonCardNo) {
			where.append(" and idCardNo = '");
			where.append(removePersonCardNo.toString());
			where.append("'");
		}
		String personCardNo1 = StaticMethod.null2String(request
				.getParameter("personCardNo1"));
		if (personCardNo.equals("")) {
			personCardNo = personCardNo1;
		}
		if (!personCardNo.equals("")) {
			where.append(" and idCardNo = '");
			where.append(personCardNo);
			where.append("'");
		}
		Map map = (Map) resumeMgr.getResumes(pageIndex, pageSize,
				where.toString());
		List list = (List) map.get("result");
		request.setAttribute(ResumeConstants.RESUME_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("personCardNo", personCardNo);
		String nodeId = StaticMethod
				.null2String(request.getParameter("nodeId"));// nodeId
																// 是地市的nodeId
		request.setAttribute("nodeId", nodeId);
		return mapping.findForward("listone");
	}

	public ActionForward commonNewExpert(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String nodeId = StaticMethod
				.null2String(request.getParameter("nodeId"));// nodeId
																// 是地市的nodeId
		request.setAttribute("nodeId", nodeId);
		String id = StaticMethod.null2String(request.getParameter("id"));
		request.setAttribute("id", id);
		if (!id.equals("")) {
			PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
			PartnerUser partnerUser = partnerUserMgr.getPartnerUser(id);
			request.setAttribute("personCardNo1", partnerUser.getPersonCardNo());
		}
		return mapping.findForward("commonNewExpert");
	}

	/**
	 * 公共查询
	 * 
	 */
	public ActionForward commonsearchOne(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				AptitudeConstants.APTITUDE_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(
				GenericValidator.isBlankOrNull(request
						.getParameter(pageIndexName)) ? 0 : (Integer
						.parseInt(request.getParameter(pageIndexName)) - 1));
		IResumeMgr resumeMgr = (IResumeMgr) getBean("iResumeMgr");
		StringBuffer where = new StringBuffer();
		String personCardNo = StaticMethod.null2String(request
				.getParameter("personCardNo"));
		String personCardNo1 = StaticMethod.null2String(request
				.getParameter("personCardNo1"));
		if (personCardNo.equals("")) {
			personCardNo = personCardNo1;
		}
		where.append(" where isDel=0");
		if (!personCardNo.equals("")) {
			where.append(" and idCardNo = '");
			where.append(personCardNo);
			where.append("'");
		}
		Map map = (Map) resumeMgr.getResumes(pageIndex, pageSize,
				where.toString());
		List list = (List) map.get("result");
		request.setAttribute(ResumeConstants.RESUME_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("personCardNo", personCardNo);
		String nodeId = StaticMethod
				.null2String(request.getParameter("nodeId"));// nodeId
																// 是地市的nodeId
		request.setAttribute("nodeId", nodeId);
		return mapping.findForward("commonResumeListOne");
	}
}