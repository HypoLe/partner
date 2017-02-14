package com.boco.eoms.partner.deviceAssess.webapp.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.partner.deviceAssess.mgr.FacilityNumMgr;
import com.boco.eoms.partner.deviceAssess.model.FacilityNum;
import com.boco.eoms.partner.deviceAssess.util.FacilityNumConstants;
import com.boco.eoms.partner.deviceAssess.webapp.form.FacilityNumForm;

/**
 * <p>
 * Title:设备量信息
 * </p>
 * <p>
 * Description:设备量信息
 * </p>
 * <p>
 * Wed Sep 29 11:28:40 CST 2010
 * </p>
 * 
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
public final class FacilityNumAction extends BaseAction {
 
	/**
	 * 未指定方法时默认调用的方法
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
	 * 新增设备量信息
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
	 * 修改设备量信息
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
		FacilityNumMgr facilityNumMgr = (FacilityNumMgr) getBean("facilityNumMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		FacilityNum facilityNum = facilityNumMgr.getFacilityNum(id);
		FacilityNumForm facilityNumForm = (FacilityNumForm) convert(facilityNum);
		updateFormBean(mapping, request, facilityNumForm);
		return mapping.findForward("edit");
	}
	
	/**
	 * 保存设备量信息
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
		FacilityNumMgr facilityNumMgr = (FacilityNumMgr) getBean("facilityNumMgr");
		FacilityNumForm facilityNumForm = (FacilityNumForm) form;
		boolean isNew = (null == facilityNumForm.getId() || "".equals(facilityNumForm.getId()));
		FacilityNum facilityNum = (FacilityNum) convert(facilityNumForm);
//		if (isNew) {
			facilityNumMgr.saveFacilityNum(facilityNum);
//		} else {
//			facilityNumMgr.saveFacilityNum(facilityNum);
//		}
		return search(mapping, facilityNumForm, request, response);
	}
	
	/**
	 * 删除设备量信息
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
		FacilityNumMgr facilityNumMgr = (FacilityNumMgr) getBean("facilityNumMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		facilityNumMgr.removeFacilityNum(id);
		return search(mapping, form, request, response);
	}
	
	/**
	 * 分页显示设备量信息列表
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
				FacilityNumConstants.FACILITYNUM_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = Integer.valueOf(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		FacilityNumMgr facilityNumMgr = (FacilityNumMgr) getBean("facilityNumMgr");
		Map map = (Map) facilityNumMgr.getFacilityNums(pageIndex, pageSize, "");
		List list = (List) map.get("result");
		request.setAttribute(FacilityNumConstants.FACILITYNUM_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}
	
}