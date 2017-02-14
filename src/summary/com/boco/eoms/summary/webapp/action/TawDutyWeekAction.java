package com.boco.eoms.summary.webapp.action;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Calendar;
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
import com.boco.eoms.summary.mgr.TawDutyWeekMgr;
import com.boco.eoms.summary.model.TawDutyWeek;
import com.boco.eoms.summary.util.TawDutyWeekConstants;
import com.boco.eoms.summary.webapp.form.TawDutyWeekForm;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;

/**
 * <p>
 * Title:值周数据
 * </p>
 * <p>
 * Description:true
 * </p>
 * <p>
 * Tue Jun 16 17:25:37 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() LXM
 * @moudle.getVersion() 3.5
 * 
 */
public final class TawDutyWeekAction extends BaseAction {
 
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
	 * 新增值周数据
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
    public ActionForward select(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("select");
	}
	
	/**
	 * 修改值周数据
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
		TawDutyWeekMgr tawDutyWeekMgr = (TawDutyWeekMgr) getBean("tawDutyWeekMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		TawDutyWeek tawDutyWeek = tawDutyWeekMgr.getTawDutyWeek(id);
		TawDutyWeekForm tawDutyWeekForm = (TawDutyWeekForm) convert(tawDutyWeek);
		updateFormBean(mapping, request, tawDutyWeekForm);
		return mapping.findForward("edit");
	}
	
	/**
	 * 保存值周数据
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
		TawDutyWeekMgr tawDutyWeekMgr = (TawDutyWeekMgr) getBean("tawDutyWeekMgr");
		Calendar cal = Calendar.getInstance();
		TawSystemSessionForm sessionForm = this.getUser(request);
		String time = StaticMethod.getLocalString();
		String userId = sessionForm.getUserid();
		String username = sessionForm.getUsername();
		String deptId = sessionForm.getDeptid();
		int yearweek = cal.get(Calendar.WEEK_OF_YEAR);
		TawDutyWeekForm tawDutyWeekForm = (TawDutyWeekForm) form;
		boolean isNew = (null == tawDutyWeekForm.getId() || "".equals(tawDutyWeekForm.getId()));
		TawDutyWeek tawDutyWeek = (TawDutyWeek) convert(tawDutyWeekForm);
		tawDutyWeek.setInsertTime(time);
		tawDutyWeek.setUserId(userId);
		tawDutyWeek.setUserName(username);
		tawDutyWeek.setDeptId(deptId);
		tawDutyWeek.setDeleted("0");
		tawDutyWeek.setWeekFlag(yearweek+"");
		if (isNew) {
			tawDutyWeekMgr.saveTawDutyWeek(tawDutyWeek);
		} else {
			tawDutyWeekMgr.saveTawDutyWeek(tawDutyWeek);
		}
		return mapping.findForward("success");
	}
	
	/**
	 * 删除值周数据
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
		TawDutyWeekMgr tawDutyWeekMgr = (TawDutyWeekMgr) getBean("tawDutyWeekMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		tawDutyWeekMgr.removeTawDutyWeek(id);
		return search(mapping, form, request, response);
	}
	
	/**
	 * 分页显示值周数据列表
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
				TawDutyWeekConstants.TAWDUTYWEEK_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		String title = request.getParameter("title");
		String weekFlag = request.getParameter("weekFlag");
		String userName = request.getParameter("userName");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		TawDutyWeekMgr tawDutyWeekMgr = (TawDutyWeekMgr) getBean("tawDutyWeekMgr");
		Map map = (Map) tawDutyWeekMgr.getTawDutyWeeks(pageIndex, pageSize, "",title,weekFlag,userName,startTime,endTime);
		List list = (List) map.get("result");
		TawSystemSessionForm sessionForm = this.getUser(request);
		String user = sessionForm.getUsername();
		request.setAttribute("user", user);
		request.setAttribute("list", list);
		request.setAttribute(TawDutyWeekConstants.TAWDUTYWEEK_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}
	public ActionForward searchedit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				TawDutyWeekConstants.TAWDUTYWEEK_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
				TawDutyWeekMgr tawDutyWeekMgr = (TawDutyWeekMgr) getBean("tawDutyWeekMgr");
		TawSystemSessionForm sessionForm = this.getUser(request);
		String userid = sessionForm.getUserid();
		Map map = (Map) tawDutyWeekMgr.getTawDutyWeeks(pageIndex, pageSize, "",userid);
		List list = (List) map.get("result");
		request.setAttribute(TawDutyWeekConstants.TAWDUTYWEEK_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("editlist");
	}
}