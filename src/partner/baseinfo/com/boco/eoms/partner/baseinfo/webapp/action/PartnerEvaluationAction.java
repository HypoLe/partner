package com.boco.eoms.partner.baseinfo.webapp.action;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.abdera.Abdera;
import org.apache.abdera.factory.Factory;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.model.Person;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.partner.baseinfo.mgr.PartnerEvaluationMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerEvaluation;
import com.boco.eoms.partner.baseinfo.util.PartnerEvaluationConstants;
import com.boco.eoms.partner.baseinfo.webapp.form.PartnerEvaluationForm;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.util.StaticMethod;

/**
 * <p>
 * Title:合作伙伴服务评价
 * </p>
 * <p>
 * Description:合作伙伴服务评价
 * </p>
 * <p>
 * Tue Apr 27 16:50:24 CST 2010
 * </p>
 * 
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
public final class PartnerEvaluationAction extends BaseAction {
 
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
	 * 新增合作伙伴服务评价
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
    	//当前用户名
    	String username = this.getUser(request).getUsername();
    	String dept =  this.getUser(request).getDeptname();
    	String time = StaticMethod.date2String(StaticMethod.getLocalTime());
    	PartnerEvaluationForm partnerEvaluationForm = (PartnerEvaluationForm) form;
    	partnerEvaluationForm.setEvaTime(time);
    	partnerEvaluationForm.setEvaUser(username);
    	partnerEvaluationForm.setEvaDept(dept);
		return mapping.findForward("edit");
	}
	
	/**
	 * 修改合作伙伴服务评价
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
		PartnerEvaluationMgr partnerEvaluationMgr = (PartnerEvaluationMgr) getBean("partnerEvaluationMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		if(!"".equals(id)){
			PartnerEvaluation partnerEvaluation = partnerEvaluationMgr.getPartnerEvaluation(id);
			PartnerEvaluationForm partnerEvaluationForm = (PartnerEvaluationForm) convert(partnerEvaluation);
			updateFormBean(mapping, request, partnerEvaluationForm);
		}
		return mapping.findForward("edit");
	}
	/**
	 * 修改合作伙伴服务评价
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
		PartnerEvaluationMgr partnerEvaluationMgr = (PartnerEvaluationMgr) getBean("partnerEvaluationMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		if(!"".equals(id)){
			PartnerEvaluation partnerEvaluation = partnerEvaluationMgr.getPartnerEvaluation(id);
			PartnerEvaluationForm partnerEvaluationForm = (PartnerEvaluationForm) convert(partnerEvaluation);
			updateFormBean(mapping, request, partnerEvaluationForm);
		}
		return mapping.findForward("detail");
	}	
	/**
	 * 保存合作伙伴服务评价
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
		PartnerEvaluationMgr partnerEvaluationMgr = (PartnerEvaluationMgr) getBean("partnerEvaluationMgr");
		PartnerEvaluationForm partnerEvaluationForm = (PartnerEvaluationForm) form;
		boolean isNew = (null == partnerEvaluationForm.getId() || "".equals(partnerEvaluationForm.getId()));
		PartnerEvaluation partnerEvaluation = (PartnerEvaluation) convert(partnerEvaluationForm);
		if (isNew) {
			partnerEvaluationMgr.savePartnerEvaluation(partnerEvaluation);
		} else {
			partnerEvaluationMgr.savePartnerEvaluation(partnerEvaluation);
		}
		return search(mapping, partnerEvaluationForm, request, response);
	}
	
	/**
	 * 删除合作伙伴服务评价
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
		PartnerEvaluationMgr partnerEvaluationMgr = (PartnerEvaluationMgr) getBean("partnerEvaluationMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		partnerEvaluationMgr.removePartnerEvaluation(id);
		return search(mapping, form, request, response);
	}
	
	/**
	 * 分页显示合作伙伴服务评价列表
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
				PartnerEvaluationConstants.PARTNEREVALUATION_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		PartnerEvaluationMgr partnerEvaluationMgr = (PartnerEvaluationMgr) getBean("partnerEvaluationMgr");
		Map map = (Map) partnerEvaluationMgr.getPartnerEvaluations(pageIndex, pageSize, "");
		List list = (List) map.get("result");
		request.setAttribute(PartnerEvaluationConstants.PARTNEREVALUATION_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}
	
}