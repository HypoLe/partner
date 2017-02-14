package com.boco.eoms.summary.webapp.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.boco.eoms.summary.model.TawzjWeek;
import com.boco.eoms.summary.model.TawzjWeekCheck;
import com.boco.eoms.summary.model.TawzjWeekSub;
import com.boco.eoms.summary.service.ITawzjWeekManager;
import com.boco.eoms.summary.util.TawzjUtil;
import com.boco.eoms.summary.util.TawzjWeekConstacts;
import com.boco.eoms.summary.webapp.form.TawzjWeekForm;
import com.boco.eoms.taskplan.model.Taskplan;
import com.boco.eoms.taskplan.service.ITaskplanManager;
import com.boco.eoms.taskplan.webapp.form.TaskplanForm;

import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.commons.system.dict.util.DictMgrLocator;
import com.boco.eoms.commons.system.dict.util.Util;
import com.boco.eoms.commons.system.priv.util.PrivMgrLocator;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.commons.ui.util.JSONUtil;
import com.boco.eoms.duty.util.DutyConstacts;
import com.boco.eoms.taskplan.util.TaksplanConstacts;

/**
 * Action class to handle CRUD on a Taskplan object
 * 
 * @struts.action name="taskplanForm" path="/taskplans" scope="request"
 *                validate="false" parameter="method" input="main"
 * @struts.action-set-property property="cancellable" value="true"
 * 
 * @struts.action-forward name="main"
 *                        path="/WEB-INF/pages/taskplan/taskplanTree.jsp"
 *                        contextRelative="true"
 * 
 * @struts.action-forward name="edit"
 *                        path="/WEB-INF/pages/taskplan/taskplanForm.jsp"
 *                        contextRelative="true"
 * 
 * @struts.action-forward name="list"
 *                        path="/WEB-INF/pages/taskplan/taskplanList.jsp"
 *                        contextRelative="true"
 */
public final class TawzjWeekAction extends BaseAction {
	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// return mapping.findForward("search");
		return null;
	}

	public ActionForward main(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("main");
	}

	/**
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
		String forward = "";
		TawzjWeekSub tawzjWeekSub = null;
		TawzjWeek tawzjWeek = null;
		List weekList = new ArrayList();
		List weeksubList = new ArrayList();
		List weekCheckList = new ArrayList();
		// 注入
		ITawzjWeekManager mgr = (ITawzjWeekManager) getBean("ITawzjWeekManager");
		ITawSystemUserManager usermgr = (ITawSystemUserManager) getBean("itawSystemUserManager");
		Calendar cal = Calendar.getInstance();
		// 当前年
		int year = cal.get(Calendar.YEAR);

		// 当前周数
		int yearweek = cal.get(Calendar.WEEK_OF_YEAR);
		String userid = sessionform.getUserid();
		// int dayweek = cal.get(Calendar.DAY_OF_WEEK);
		String date = "";
		String week = "";
		try {
			tawzjWeek = mgr.getTawzjWeeks(year + "" + yearweek, userid);
			if (tawzjWeek == null) {
				for (int i = 1; i <= 7; i++) {
					tawzjWeekSub = new TawzjWeekSub();
					date = TawzjUtil.GetWeekSAndE(year, yearweek, i);
					tawzjWeekSub.setDatetime(StaticMethod.getAddZero(date));
					week = TawzjUtil.GetWeekStr(i);
					tawzjWeekSub.setWeekStr(week);
					weekList.add(tawzjWeekSub);
				}
				tawzjWeek = new TawzjWeek();
			} else {
				weeksubList = mgr.getTawzjWeekSub(tawzjWeek.getId());
				for (int i = 0; i < weeksubList.size(); i++) {
					tawzjWeekSub = (TawzjWeekSub) weeksubList.get(i);
					week = TawzjUtil.GetWeekStr(Integer.parseInt(tawzjWeekSub
							.getWeek()));
					tawzjWeekSub.setWeekStr(week);
					weekList.add(tawzjWeekSub);
				}
			}
			List checkUserList = new ArrayList();
			Set users = PrivMgrLocator.getPrivMgr().listUserByUrl(
					TawzjWeekConstacts.GROUPLEADER); // 根据部门信息，获取部门中符合指定权限的人员列表
			if (users != null) {
				for (Iterator it = users.iterator(); it.hasNext();) {
					TawSystemUser user = (TawSystemUser) it.next();
					if (user.getId() != null && !user.getId().equals("")) {
						checkUserList.add(user);
					}
				}
			}
			//ITawSystemUserManager usermgrr = (ITawSystemUserManager) getBean("ItawSystemUserManagerFlush");
			request.setAttribute("checkUserList", checkUserList);
			request.setAttribute("CHECKUSERLIST", checkUserList);
			request.setAttribute("tawzjWeek", tawzjWeek);
			request.setAttribute("dataList", weekList);
			request.setAttribute("year", year + "");
			request.setAttribute("yearWeek", yearweek + "");
			//System.out.println("0000000000000000000000000000000000");
			request.setAttribute("userid", sessionform.getUsername());
			//System.out.println("0000000000000000000000000000000000");
			// startStr = (String)
			// DictMgrLocator.getDictService().itemId2name(Util.constituteDictId("dict-file","startsWith"),
			// "");

			if (tawzjWeek.getState() == null || "".equals(tawzjWeek.getState())
					|| "0".equals(tawzjWeek.getState())
					|| "3".equals(tawzjWeek.getState())
					|| "5".equals(tawzjWeek.getState())) {
				forward = "add";
			} else {
				// 审核内容
				weekCheckList = mgr.getTawzjWeekCheck(tawzjWeek.getId());
				List checkList = new ArrayList();
				for (Iterator it = weekCheckList.iterator(); it.hasNext();) {
					TawzjWeekCheck check = (TawzjWeekCheck) it.next();
					check.setSender(usermgr.getUserByuserid(check.getSender())
							.getUsername());
					check.setAuditer(usermgr
							.getUserByuserid(check.getAuditer()).getUsername());
					check.setState((String) DictMgrLocator.getDictService()
							.itemId2name(
									Util.constituteDictId("dict-summary",
											"state"), check.getState()));
					checkList.add(check);
				}
				request.setAttribute("weekCheckList", checkList);
				forward = "view";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return mapping.findForward("false");
		}
		return mapping.findForward(forward);
	}

	/**
	 * 保存
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
		TawzjWeekSub tawzjWeekSub = null;
		try {
			ITawzjWeekManager mgr = (ITawzjWeekManager) getBean("ITawzjWeekManager");
			TawzjWeekForm tawzjWeekForm = (TawzjWeekForm) form;
			TawzjWeek tawzjWeek = (TawzjWeek) convert(tawzjWeekForm);
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int yearweek = cal.get(Calendar.WEEK_OF_YEAR);
			String weekid = year + "" + yearweek;
			tawzjWeek.setWeekid(weekid);
			tawzjWeek.setCruser(sessionform.getUserid());
			//String state = request.getParameter("state");
			tawzjWeek.setState("0"); // x新增
			String id = mgr.saveTawzjWeek(tawzjWeek);
			String weekContent = "";
			String subid = "";
			for (int i = 0; i < 7; i++) {
				tawzjWeekSub = new TawzjWeekSub();
				tawzjWeekSub.setWeekid(id);
				tawzjWeekSub.setWeek(i + 1 + "");
				// 日期
				tawzjWeekSub.setDatetime(StaticMethod.getAddZero(TawzjUtil
						.GetWeekSAndE(year, yearweek, i + 1)));
				// 執行內容
				weekContent = request.getParameter("work" + i);
				// id
				subid = request.getParameter("subid" + i);
				if (!"".equals(subid)) {
					tawzjWeekSub.setId(subid);
				}
				tawzjWeekSub.setWork(weekContent);
				System.out.print(weekContent);
				mgr.saveTawzjWeekSub(tawzjWeekSub);

				subid = "";
				weekContent = "";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return mapping.findForward("false");
		}
		return add(mapping, form, request, response);
	}

	/**
	 * 送审
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward send(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		if (sessionform == null) {
			return mapping.findForward("timeout");
		}
		TawzjWeekSub tawzjWeekSub = null;
		TawzjWeekCheck tawzjWeekCheck = new TawzjWeekCheck();
		try {
			ITawzjWeekManager mgr = (ITawzjWeekManager) getBean("ITawzjWeekManager");
			TawzjWeekForm tawzjWeekForm = (TawzjWeekForm) form;
			TawzjWeek tawzjWeek = (TawzjWeek) convert(tawzjWeekForm);
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int yearweek = cal.get(Calendar.WEEK_OF_YEAR);
			String weekid = year + "" + yearweek;
			tawzjWeek.setWeekid(weekid);
			tawzjWeek.setCruser(sessionform.getUserid());
			String state = request.getParameter("state");
			String auditer = request.getParameter("auditer");
			tawzjWeek.setAuditer(auditer);
			tawzjWeek.setState(state); // x新增
			tawzjWeek.setCrtime(StaticMethod.getLocalString());
			String id = mgr.saveTawzjWeek(tawzjWeek);
			String weekContent = "";
			String subid = "";
			for (int i = 0; i < 7; i++) {
				tawzjWeekSub = new TawzjWeekSub();
				tawzjWeekSub.setWeekid(id);
				tawzjWeekSub.setWeek(i + 1 + "");
				// 日期
				tawzjWeekSub.setDatetime(StaticMethod.getAddZero(TawzjUtil
						.GetWeekSAndE(year, yearweek, i + 1)));
				// 執行內容
				weekContent = request.getParameter("work" + i);
				// id
				subid = request.getParameter("subid" + i);
				if (!"".equals(subid)) {
					tawzjWeekSub.setId(subid);
				}
				tawzjWeekSub.setWork(weekContent);
				mgr.saveTawzjWeekSub(tawzjWeekSub);
				subid = "";
				weekContent = "";
			}
			tawzjWeekCheck.setState(state);
			tawzjWeekCheck.setWeekid(id);
			tawzjWeekCheck.setFlag("1");
			tawzjWeekCheck.setSender(sessionform.getUserid());
			tawzjWeekCheck.setAuditer(auditer);
			mgr.saveTawzjWeekCheck(tawzjWeekCheck);

		} catch (Exception e) {
			e.printStackTrace();
			return mapping.findForward("false");
		}
		return this.add(mapping, form, request, response);
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
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		if (sessionform == null) {
			return mapping.findForward("timeout");
		}
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int yearweek = cal.get(Calendar.WEEK_OF_YEAR);

		request.setAttribute("year", year + "");
		request.setAttribute("yearweek", yearweek + "");
		return mapping.findForward("search");
	}

	/**查询结果
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawzjWeekForm weekFrom =(TawzjWeekForm)form;
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		if (sessionform == null) {
			return mapping.findForward("timeout");
		}
		try {
			String pageIndexName = new org.displaytag.util.ParamEncoder(
					"TawzjWeekList")
					.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
			final Integer pageIndex = new Integer(
					GenericValidator.isBlankOrNull(request
							.getParameter(pageIndexName)) ? 0 : (Integer
							.parseInt(request.getParameter(pageIndexName)) - 1));

			String year1 = StaticMethod.null2String(request
					.getParameter("yearFlag1"));
			String year2 = StaticMethod.null2String(request
					.getParameter("yearFlag2"));
			String week1 = StaticMethod.null2String(request
					.getParameter("week1"));
			String week2 = StaticMethod.null2String(request
					.getParameter("week2"));

			String yearweek1 = year1 + week1.trim();
			String yearweek2 = year2 + week2.trim();

			String cruser = StaticMethod.null2String(request
					.getParameter("cruser"));
			String state = StaticMethod.null2String(request
					.getParameter("state"));
			String auditer = StaticMethod.null2String(request
					.getParameter("auditer"));
			StringBuffer sql = new StringBuffer();
			sql.append(" where 1=1 ");
			if (!"".equals(week1)) {
				sql.append(" and weekid >= '" + yearweek1 + "'");
			}
			if (!"".equals(week2)) {
				sql.append(" and weekid <= '" + yearweek2 + "'");
			}
			if (!"".equals(cruser)) {
				sql.append(" and cruser = '" + cruser + "'");
			}
			if (!"".equals(state)) {
				sql.append(" and state = '" + state + "'");
			}
			if (!"".equals(auditer)) {
				sql.append(" and auditer = '" + auditer + "'");
			}
			final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
					.getPageSize();
			ITawSystemUserManager usermgr = (ITawSystemUserManager) getBean("itawSystemUserManager");
			ITawzjWeekManager mgr = (ITawzjWeekManager) getBean("ITawzjWeekManager");
			Map map = (Map) mgr.getTawzjWeeks(pageIndex, pageSize, sql
					.toString());
			List list = (List) map.get("result");
			List weekList = new ArrayList();
			for(Iterator it = list.iterator();it.hasNext();){
				TawzjWeek tawzjWeek = (TawzjWeek)it.next();
				tawzjWeek.setName(usermgr.getUserByuserid(tawzjWeek.getCruser())
						.getUsername()
						+ "-" + tawzjWeek.getWeekid() + "周周报");
				tawzjWeek.setState((String) DictMgrLocator.getDictService().itemId2name(
						Util.constituteDictId("dict-summary", "state"),
						tawzjWeek.getState()));
				tawzjWeek.setAuditer(usermgr.getUserByuserid(tawzjWeek.getAuditer())
						.getUsername());
				tawzjWeek.setCruser(usermgr.getUserByuserid(tawzjWeek.getCruser())
						.getUsername());
				weekList.add(tawzjWeek);
				
			}

			request.setAttribute("TawzjWeekList", weekList);
			request.setAttribute("resultSize", map.get("total"));
			request.setAttribute("pageSize", pageSize);
		} catch (Exception ee) {
			ee.printStackTrace();
		}
		return mapping.findForward("searchList");
	}

	/**
	 * 查询出来本人待审核列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward weekCheckList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		ITawzjWeekManager mgr = (ITawzjWeekManager) getBean("ITawzjWeekManager");
		ITawSystemUserManager usermgr = (ITawSystemUserManager) getBean("itawSystemUserManager");
		if (sessionform == null) {
			return mapping.findForward("timeout");
		}
		String userid = sessionform.getUserid();
		List list = mgr.getTawzjWeekCheckList(userid);
		List checkList = new ArrayList();
		for (Iterator it = list.iterator(); it.hasNext();) {
			TawzjWeek week = (TawzjWeek) it.next();
			week.setName(usermgr.getUserByuserid(week.getCruser())
					.getUsername()
					+ "-" + week.getWeekid() + "周周报");
			week.setState((String) DictMgrLocator.getDictService().itemId2name(
					Util.constituteDictId("dict-summary", "state"),
					week.getState()));
			checkList.add(week);
		}
		request.setAttribute("checkList", checkList);

		return mapping.findForward("weekCheckList");
	}

	/**
	 * 审核页面
	 */
	public ActionForward audit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String tawzjweekid = request.getParameter("id");
		ITawzjWeekManager mgr = (ITawzjWeekManager) getBean("ITawzjWeekManager");
		ITawSystemUserManager usermgr = (ITawSystemUserManager) getBean("itawSystemUserManager");
		TawzjWeek tawzjWeek = mgr.getTawzjWeek(tawzjweekid);
		// TawzjWeekSub tawzjWeekSub = null;
		List weeksubList = mgr.getTawzjWeekSub(tawzjWeek.getId());
		List weekList = new ArrayList();
		for (int i = 0; i < weeksubList.size(); i++) {
			TawzjWeekSub tawzjWeekSub = (TawzjWeekSub) weeksubList.get(i);
			String week = TawzjUtil.GetWeekStr(Integer.parseInt(tawzjWeekSub
					.getWeek()));
			tawzjWeekSub.setWeekStr(week);
			weekList.add(tawzjWeekSub);
		}
		List checkUserList = new ArrayList();
		Set users = PrivMgrLocator.getPrivMgr().listUserByUrl(
				TawzjWeekConstacts.GROUPLEADER); // 根据部门信息，获取部门中符合指定权限的人员列表
		if (users != null) {
			for (Iterator it = users.iterator(); it.hasNext();) {
				TawSystemUser user = (TawSystemUser) it.next();
				if (user.getId() != null && !user.getId().equals("")) {
					checkUserList.add(user);
				}
			}
		}
		tawzjWeek.setCruser(usermgr.getUserByuserid(tawzjWeek.getCruser())
				.getUsername());
		request.setAttribute("checkUserList", checkUserList);
		request.setAttribute("tawzjWeek", tawzjWeek);
		request.setAttribute("dataList", weekList);

		List weekCheckList = mgr.getTawzjWeekCheck(tawzjWeek.getId());
		List checkList = new ArrayList();
		for (Iterator it = weekCheckList.iterator(); it.hasNext();) {
			TawzjWeekCheck check = (TawzjWeekCheck) it.next();
			check.setSender(usermgr.getUserByuserid(check.getSender())
					.getUsername());
			check.setAuditer(usermgr.getUserByuserid(check.getAuditer())
					.getUsername());
			check.setState((String) DictMgrLocator.getDictService()
					.itemId2name(
							Util.constituteDictId("dict-summary", "state"),
							check.getState()));
			checkList.add(check);
		}
		request.setAttribute("weekCheckList", checkList);

		return mapping.findForward("auditview");
	}
	
	
	/**
	 * 查询页面的时候选择详细
	 */
	public ActionForward listview(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String tawzjweekid = request.getParameter("id");
		ITawzjWeekManager mgr = (ITawzjWeekManager) getBean("ITawzjWeekManager");
		ITawSystemUserManager usermgr = (ITawSystemUserManager) getBean("itawSystemUserManager");
		TawzjWeek tawzjWeek = mgr.getTawzjWeek(tawzjweekid);
		// TawzjWeekSub tawzjWeekSub = null;
		List weeksubList = mgr.getTawzjWeekSub(tawzjWeek.getId());
		List weekList = new ArrayList();
		for (int i = 0; i < weeksubList.size(); i++) {
			TawzjWeekSub tawzjWeekSub = (TawzjWeekSub) weeksubList.get(i);
			String week = TawzjUtil.GetWeekStr(Integer.parseInt(tawzjWeekSub
					.getWeek()));
			tawzjWeekSub.setWeekStr(week);
			weekList.add(tawzjWeekSub);
		}
		 
		tawzjWeek.setCruser(usermgr.getUserByuserid(tawzjWeek.getCruser())
				.getUsername());
	 
		request.setAttribute("tawzjWeek", tawzjWeek);
		request.setAttribute("dataList", weekList);

		List weekCheckList = mgr.getTawzjWeekCheck(tawzjWeek.getId());
		List checkList = new ArrayList();
		for (Iterator it = weekCheckList.iterator(); it.hasNext();) {
			TawzjWeekCheck check = (TawzjWeekCheck) it.next();
			check.setSender(usermgr.getUserByuserid(check.getSender())
					.getUsername());
			check.setAuditer(usermgr.getUserByuserid(check.getAuditer())
					.getUsername());
			check.setState((String) DictMgrLocator.getDictService()
					.itemId2name(
							Util.constituteDictId("dict-summary", "state"),
							check.getState()));
			checkList.add(check);
		}
		request.setAttribute("weekCheckList", checkList);

		return mapping.findForward("listview");
	}
	
	/**
	 * 填写审核原因
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward sendoperation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		if (sessionform == null) {
			return mapping.findForward("timeout");
		}

		try {
			String state = StaticMethod.null2String(request
					.getParameter("state"));
			String tawzjweekId = StaticMethod.null2String(request
					.getParameter("id"));
			String stateStr = "";
			if ("3".equals(state) || "5".equals(state)) {
				stateStr = "驳回原因";
			} else if ("2".equals(state)) {
				stateStr = "审核意见";
				List checkUserList = new ArrayList();
				Set users = PrivMgrLocator.getPrivMgr().listUserByUrl(
						TawzjWeekConstacts.DIRECTOR); // 根据部门信息，获取部门中符合指定权限的人员列表
				if (users != null) {
					for (Iterator it = users.iterator(); it.hasNext();) {
						TawSystemUser user = (TawSystemUser) it.next();
						if (user.getId() != null && !user.getId().equals("")) {
							checkUserList.add(user);
						}
					}
				}
				request.setAttribute("checkUserList", checkUserList);
			} else if ("4".equals(state)) {
				stateStr = "审核意见";
			}
			request.setAttribute("stateStr", stateStr);
			request.setAttribute("state", state);
			request.setAttribute("id", tawzjweekId);
		} catch (Exception e) {
			e.printStackTrace();
			return mapping.findForward("false");
		}
		return mapping.findForward("sendoperation");
	}

	/** s审核保存
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward auditSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		ITawzjWeekManager mgr = (ITawzjWeekManager) getBean("ITawzjWeekManager");
		if (sessionform == null) {
			return mapping.findForward("timeout");
		}
		TawzjWeek tawzjweek = null;
		String state = "";
		String tawzjweekId = "";
		String operation = "";
		String auditUser = "";
		try {
			state = StaticMethod.null2String(request.getParameter("state"));
			tawzjweekId = StaticMethod.null2String(request.getParameter("id"));
			operation = StaticMethod.null2String(request
					.getParameter("operation"));
			auditUser = StaticMethod.null2String(request
					.getParameter("auditer"));
			if ("".equals(auditUser)) {
				auditUser = mgr.findauditer(sessionform.getUserid(),
						tawzjweekId);
			}
			tawzjweek = mgr.getTawzjWeek(tawzjweekId);

			if ("3".equals(state) || "5".equals(state) || "4".equals(state)
					|| "6".equals(state)) {
				TawzjWeekCheck tawzjWeekCheck = new TawzjWeekCheck();
				tawzjWeekCheck.setAuditer(sessionform.getUserid());
				tawzjWeekCheck.setOpinion(operation);
				tawzjWeekCheck.setState(state);
				tawzjWeekCheck.setWeekid(tawzjweekId);
				mgr.saveTawzjWeekCheck(tawzjWeekCheck);
			} else if ("2".equals(state)) {
				TawzjWeekCheck tawzjWeekCheckpass = new TawzjWeekCheck();
				tawzjWeekCheckpass.setAuditer(sessionform.getUserid());
				tawzjWeekCheckpass.setOpinion(operation);
				tawzjWeekCheckpass.setState(state);
				tawzjWeekCheckpass.setWeekid(tawzjweekId);
				mgr.saveTawzjWeekCheck(tawzjWeekCheckpass);

				TawzjWeekCheck tawzjWeekChecksend = new TawzjWeekCheck();
				tawzjWeekChecksend.setSender(sessionform.getUserid());
				tawzjWeekChecksend.setAuditer(auditUser);
				tawzjWeekChecksend.setOpinion("");
				tawzjWeekChecksend.setState("1");
				tawzjWeekChecksend.setFlag("2");
				tawzjWeekChecksend.setWeekid(tawzjweekId);
				mgr.saveTawzjWeekCheck(tawzjWeekChecksend);
			}
			if ("5".equals(state)) {
				auditUser = tawzjweek.getCruser();
			} else if ("6".equals(state)) {
				if ("".equals(auditUser)) {
					auditUser = mgr.findauditer(sessionform.getUserid(),
							tawzjweekId);
				}
			}else if( "4".equals(state)){
				auditUser = "";
			}
			tawzjweek.setAuditer(auditUser);
			tawzjweek.setState(state);
			mgr.saveTawzjWeek(tawzjweek);
		} catch (Exception e) {
			e.printStackTrace();
			return mapping.findForward("false");
		}
		return mapping.findForward("weekCheckList");
	}
}
