package com.boco.eoms.partner.assess.AssFee.webapp.action;

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
import com.boco.eoms.partner.assess.AssFee.mgr.IFeeDetailMgr;
import com.boco.eoms.partner.assess.AssFee.model.FeeDetail;
import com.boco.eoms.partner.assess.AssFee.util.FeeDetailConstants;
import com.boco.eoms.partner.assess.AssFee.webapp.form.FeeDetailForm;

/**
 * <p>
 * Title:光缆线路付费信息
 * </p>
 * <p>
 * Description:光缆线路付费信息
 * </p> 
 * <p>
 * Tue Nov 23 16:04:36 CST 2010
 * </p>
 * 
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
public final class FeeDetailAction extends BaseAction {
 
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
	 * 新增光缆线路付费信息
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
	 * 修改光缆线路付费信息
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
		IFeeDetailMgr feeDetailMgr = (IFeeDetailMgr) getBean("feeDetailMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		FeeDetail feeDetail = feeDetailMgr.getFeeDetail(id);
		FeeDetailForm feeDetailForm = (FeeDetailForm) convert(feeDetail);
		updateFormBean(mapping, request, feeDetailForm);
		return mapping.findForward("edit");
	}
	
	/**
	 * 保存光缆线路付费信息
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
		IFeeDetailMgr feeDetailMgr = (IFeeDetailMgr) getBean("feeDetailMgr");
		FeeDetailForm feeDetailForm = (FeeDetailForm) form;
		FeeDetail feeDetail = (FeeDetail) convert(feeDetailForm);
		feeDetailMgr.saveFeeDetail(feeDetail);
		return search(mapping, feeDetailForm, request, response);
	}
	
	/**
	 * 删除光缆线路付费信息
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
		IFeeDetailMgr feeDetailMgr = (IFeeDetailMgr) getBean("feeDetailMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		feeDetailMgr.removeFeeDetail(id);
		return search(mapping, form, request, response);
	}
	
	/**
	 * 分页显示光缆线路付费信息列表
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
				FeeDetailConstants.FEEDETAIL_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = Integer.valueOf((GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1)));
		IFeeDetailMgr feeDetailMgr = (IFeeDetailMgr) getBean("feeDetailMgr");
		Map map = (Map) feeDetailMgr.getFeeDetails(pageIndex, pageSize, "");
		List list = (List) map.get("result"); 
		request.setAttribute(FeeDetailConstants.FEEDETAIL_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}
	
}