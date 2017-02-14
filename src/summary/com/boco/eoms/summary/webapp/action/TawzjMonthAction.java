package com.boco.eoms.summary.webapp.action;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.summary.model.TawzjMonth;
import com.boco.eoms.summary.service.TawzjMonthMgr;
import com.boco.eoms.summary.util.TawzjMonthConstants;
import com.boco.eoms.summary.util.TawzjWeekConstacts;
import com.boco.eoms.summary.webapp.form.TawzjMonthForm;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.priv.util.PrivMgrLocator;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;

/**
 * <p>
 * Title:月工作总结
 * </p>
 * <p>
 * Description:true
 * </p>
 * <p>
 * Wed Jun 17 14:24:09 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() hanlu
 * @moudle.getVersion() 3.5
 * 
 */
public final class TawzjMonthAction extends BaseAction {
 
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
	 * 新增月工作总结
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
    	TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
    	if (sessionform == null) {
    		return mapping.findForward("timeout");
    	}
    	Calendar cal = Calendar.getInstance();
		// 当前月
		int month = cal.get(Calendar.MONTH)+1;
		int nextMonth = cal.get(Calendar.MONTH)+2;
		ArrayList checkUserList = new ArrayList();
		Set users = PrivMgrLocator.getPrivMgr().listUserByUrl(
				TawzjMonthConstants.TAWZJMONTH_APP); // 根据部门信息，获取部门中符合指定权限的人员列表
		if (users != null) {
			for (Iterator it = users.iterator(); it.hasNext();) {
				TawSystemUser user = (TawSystemUser) it.next();
				if (user.getId() != null && !user.getId().equals("")) {
					checkUserList.add(user);
				}
			}
		}
		request.setAttribute("checkUserList", checkUserList);
		request.setAttribute("month", month + "");
		request.setAttribute("nextMonth", nextMonth + "");
		return mapping.findForward("edit");
	}
	
	/**
	 * 修改月工作总结
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
		TawzjMonthMgr tawzjMonthMgr = (TawzjMonthMgr) getBean("tawzjMonthMgr");
		ITawSystemUserManager usermgr = (ITawSystemUserManager) getBean("itawSystemUserManager");
		String id = StaticMethod.null2String(request.getParameter("id"));
		TawzjMonth tawzjMonth = tawzjMonthMgr.getTawzjMonth(id);
		TawzjMonthForm tawzjMonthForm = (TawzjMonthForm) convert(tawzjMonth);
		tawzjMonthForm.setAuditerName(usermgr.getUserByuserid(tawzjMonthForm.getAuditer()).getUsername());
		String dateTime=tawzjMonthForm.getDateTime();
		// 当前月
		String month=dateTime.substring(6,7);		
		int nextMonth = Integer.parseInt(month)+1;
		ArrayList checkUserList = new ArrayList();
		Set users = PrivMgrLocator.getPrivMgr().listUserByUrl(
				TawzjMonthConstants.TAWZJMONTH_APP); // 根据部门信息，获取部门中符合指定权限的人员列表
		if (users != null) {
			for (Iterator it = users.iterator(); it.hasNext();) {
				TawSystemUser user = (TawSystemUser) it.next();
				if (user.getId() != null && !user.getId().equals("")) {
					checkUserList.add(user);
				}
			}
		}
		request.setAttribute("checkUserList", checkUserList);
		request.setAttribute("month", month + "");
		request.setAttribute("nextMonth", nextMonth + "");
		updateFormBean(mapping, request, tawzjMonthForm);
		return mapping.findForward("edit");
	}
    /**
	 * 修改月工作总结
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawzjMonthMgr tawzjMonthMgr = (TawzjMonthMgr) getBean("tawzjMonthMgr");
		ITawSystemUserManager usermgr = (ITawSystemUserManager) getBean("itawSystemUserManager");
		String id = StaticMethod.null2String(request.getParameter("id"));
		TawzjMonth tawzjMonth = tawzjMonthMgr.getTawzjMonth(id);
		
		TawzjMonthForm tawzjMonthForm = (TawzjMonthForm) convert(tawzjMonth);
		tawzjMonthForm.setAuditerName(usermgr.getUserByuserid(tawzjMonthForm.getAuditer()).getUsername());
		updateFormBean(mapping, request, tawzjMonthForm);
		String dateTime=tawzjMonthForm.getDateTime();
		// 当前月
		String month=dateTime.substring(6,7);		
		int nextMonth = Integer.parseInt(month)+1;
		request.setAttribute("month", month + "");
		request.setAttribute("nextMonth", nextMonth + "");
		return mapping.findForward("view");
	}
	
	/**
	 * 保存月工作总结
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
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		if (sessionform == null) {
			return mapping.findForward("timeout");
		}
		TawzjMonthMgr tawzjMonthMgr = (TawzjMonthMgr) getBean("tawzjMonthMgr");
		TawzjMonthForm tawzjMonthForm = (TawzjMonthForm) form;
		String status=tawzjMonthForm.getStatus();
		Calendar cal = Calendar.getInstance();
		// 当前月
		int m = cal.get(Calendar.MONTH);
		boolean isNew = (null == tawzjMonthForm.getId() || "".equals(tawzjMonthForm.getId()));
		if(status.equals("3")){	
			String id = StaticMethod.null2String(request.getParameter("id"));
			TawzjMonth tawzjMonth = tawzjMonthMgr.getTawzjMonth(id);
			tawzjMonth.setStatus("3");
			tawzjMonthMgr.saveTawzjMonth(tawzjMonth);
			return searchStatus(mapping, form, request, response);
		}
		if(status.equals("2")){		
			String id = StaticMethod.null2String(request.getParameter("id"));
			TawzjMonth tawzjMonth = tawzjMonthMgr.getTawzjMonth(id);
			tawzjMonth.setStatus("2");
			tawzjMonthMgr.saveTawzjMonth(tawzjMonth);
			return searchStatus(mapping, form, request, response);
		}
		if(status.equals("1")){
			String dateTime = StaticMethod.getCurrentDateTime("yyyy-MM-dd");
			String userId=sessionform.getUserid();
			tawzjMonthForm.setDateTime(dateTime);
			tawzjMonthForm.setUseId(userId);
			tawzjMonthForm.setStatus("1");
			tawzjMonthForm.setMonth(Integer.toString(m));
			TawzjMonth tawzjMonth  = (TawzjMonth) convert(tawzjMonthForm);
			tawzjMonthMgr.saveTawzjMonth(tawzjMonth);
			return search(mapping, form, request, response);
		}else{
			String dateTime = StaticMethod.getCurrentDateTime("yyyy-MM-dd");
			String userId=sessionform.getUserid();
			tawzjMonthForm.setDateTime(dateTime);
			tawzjMonthForm.setUseId(userId);
			tawzjMonthForm.setStatus("0");
			tawzjMonthForm.setMonth(Integer.toString(m));
			TawzjMonth tawzjMonth  = (TawzjMonth) convert(tawzjMonthForm);
			if (isNew) {
				tawzjMonthMgr.saveTawzjMonth(tawzjMonth);
			}else {
				tawzjMonthMgr.saveTawzjMonth(tawzjMonth);
			}
			return searchNo(mapping, form, request, response);
		}
	}
	
	/**
	 * 删除月工作总结
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
		TawzjMonthMgr tawzjMonthMgr = (TawzjMonthMgr) getBean("tawzjMonthMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		tawzjMonthMgr.removeTawzjMonth(id);
		return search(mapping, form, request, response);
	}
	/**
	 * 查询页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	   public ActionForward searchForm(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
	    	TawSystemSessionForm sessionform = (TawSystemSessionForm) request
			.getSession().getAttribute("sessionform");
	    	if (sessionform == null) {
	    		return mapping.findForward("timeout");
	    	}
			return mapping.findForward("search");
		}
	
	/**
	 * 分页显示月工作总结列表
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
				TawzjMonthConstants.TAWZJMONTH_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		TawzjMonthMgr tawzjMonthMgr = (TawzjMonthMgr) getBean("tawzjMonthMgr");
		TawzjMonthForm tawzjMonthForm = (TawzjMonthForm) form;
		String queryStr = "";
		if(tawzjMonthForm.getTeam() != null && tawzjMonthForm.getTeam().length()>0){
			queryStr += " and tawzjMonth.team like '%"+tawzjMonthForm.getTeam()+"%' ";
		}
		if(tawzjMonthForm.getYear() !=null && tawzjMonthForm.getYear().length()>0){
			queryStr += " and tawzjMonth.dateTime like '%" +tawzjMonthForm.getYear()+"%' ";
		}
		if(tawzjMonthForm.getMonth()!=null&& tawzjMonthForm.getMonth().length()>0){
			queryStr += " and tawzjMonth.month='" +tawzjMonthForm.getMonth() + "'";
		}
		if(tawzjMonthForm.getStatus()!=null&& tawzjMonthForm.getStatus().length()>0){
			queryStr += " and tawzjMonth.status='" +tawzjMonthForm.getStatus() + "'";
		}
		Map map = (Map) tawzjMonthMgr.getTawzjMonths(pageIndex, pageSize, queryStr);
		List list = (List) map.get("result");
		request.setAttribute(TawzjMonthConstants.TAWZJMONTH_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("listAll");
	}
	/**
	 * 分页显示月工作总结列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchNo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				TawzjMonthConstants.TAWZJMONTH_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		TawzjMonthMgr tawzjMonthMgr = (TawzjMonthMgr) getBean("tawzjMonthMgr");
		String queryStr;
		String userId=sessionform.getUserid();
		queryStr="and tawzjMonth.status='0' and tawzjMonth.useId='"+userId+"'";
		Map map = (Map) tawzjMonthMgr.getTawzjMonths(pageIndex, pageSize, queryStr);
		List list = (List) map.get("result");
		request.setAttribute(TawzjMonthConstants.TAWZJMONTH_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}
	/**
	 * 分页显示月工作总结列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				TawzjMonthConstants.TAWZJMONTH_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		TawzjMonthMgr tawzjMonthMgr = (TawzjMonthMgr) getBean("tawzjMonthMgr");
		String userId=sessionform.getUserid();
		String queryStr;
		queryStr="and tawzjMonth.status='1' and tawzjMonth.auditer='"+userId+"'";
		Map map = (Map) tawzjMonthMgr.getTawzjMonths(pageIndex, pageSize, queryStr);
		List list = (List) map.get("result");
		request.setAttribute(TawzjMonthConstants.TAWZJMONTH_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("status");
	}
	
}