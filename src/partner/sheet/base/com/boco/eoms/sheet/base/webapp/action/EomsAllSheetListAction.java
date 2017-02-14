package com.boco.eoms.sheet.base.webapp.action;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.abdera.model.Feed;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.sheet.base.atom.AtomUtilForTask;
import com.boco.eoms.sheet.base.model.EomsUndoSheetView;
import com.boco.eoms.sheet.base.service.IEomsAllSheetListService;
import com.boco.eoms.sheet.base.util.SheetAttributes;
import com.boco.eoms.sheet.overtimetip.util.OvertimeTipUtil;

public class EomsAllSheetListAction extends BaseAction {

	/**
	 * 显示所有待处理任务列表(portal)
	 */
	@SuppressWarnings("unchecked")
	public void getUndoAllListsForPortal(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		/** 获取登陆信息，add by qinmin* */
		String userId = StaticMethod.nullObject2String(request
				.getParameter("userName"));
		/** 若无法从URL中获取userid，则从session中获取，modify by 秦敏 2009-08-12 begin */
		if (userId.equals("")) {
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request
					.getSession().getAttribute("sessionform");
			userId = sessionform.getUserid();
		}
		ITawSystemUserManager userManager = (ITawSystemUserManager) ApplicationContextHolder
				.getInstance().getBean("ItawSystemUserSaveManagerFlush");
		TawSystemUser user = new TawSystemUser();
		user = userManager.getUserByuserid(userId);
		String deptId = StaticMethod.null2String(user.getDeptid());
		String szcurPage = StaticMethod.nullObject2String(request.getParameter("curPage"), "0");
		String szpageSize = StaticMethod.nullObject2String(request.getParameter("pageSize"), "5");
		final Integer pageIndex = new Integer(szcurPage);
		Integer pageSize = new Integer(szpageSize);

		// 增加map对象，存放main，传入taskService.getUndoTask
		Map condition = new HashMap();

		IEomsAllSheetListService taskService = (IEomsAllSheetListService) getBean("IEomsAllSheetListService");
		HashMap taskListMap = null;
		if (pageSize != null && pageSize.intValue() != 0) {
			taskListMap = taskService.getUndoAllSheetTask(condition, userId,
					deptId, "", pageIndex, pageSize);
		} else {
			System.out.println("===========" + pageSize);
			try {
				taskListMap = taskService.getUndoAllSheetTask(condition,
						userId, deptId, "", new Integer(0), new Integer(-1));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Feed feed = AtomUtilForTask.showUndoAllSheetListForPortal(taskListMap,
				request);
		OutputStream os = response.getOutputStream();
		PrintStream ps = new PrintStream(os);
		feed.getDocument().writeTo(ps);
	}

	/**
	 * 显示所有待处理任务列表(portal)
	 */
	@SuppressWarnings("unchecked")
	public ActionForward getUndoAllLists(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userId = StaticMethod.nullObject2String(request
				.getParameter("userName"));
		if (userId.equals("")) {
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request
					.getSession().getAttribute("sessionform");
			userId = sessionform.getUserid();
		}

		String deptId = "";

		// 获取每页显示条数
		Integer pageSize = ((SheetAttributes) ApplicationContextHolder
				.getInstance().getBean("SheetAttributes")).getPageSize();

		String pageIndexName = new org.displaytag.util.ParamEncoder("taskList")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		// 当前页数
		Integer pageIndex = new Integer(GenericValidator.isBlankOrNull(request
				.getParameter(pageIndexName)) ? 0 : (Integer.parseInt(request
				.getParameter(pageIndexName)) - 1));
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		userId = sessionform.getUserid();
		deptId = sessionform.getDeptid();

		String exportType = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								"taskList")
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_EXPORTTYPE)));
		if (!exportType.equals("")) {
			pageSize = new Integer(-1);
		}
		String order = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								"taskList")
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_ORDER)));
		String sort = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								"taskList")
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_SORT)));
		String orderCondition = "";
		if (!order.equals("")) {
			if (order.equals("1")) {
				order = " asc";
			} else {
				order = " desc";
			}
		}
		if (!sort.equals("")) {
			orderCondition = " " + sort + order;
		}
		IEomsAllSheetListService taskService = (IEomsAllSheetListService) getBean("IEomsAllSheetListService");

		Map condition = new HashMap();
		condition.put("orderCondition", orderCondition);
		HashMap taskListMap = null;
		// if (!userId.equals("")pageSize != null && pageSize.intValue() != 0) {
		taskListMap = taskService.getUndoAllSheetTask(condition, userId,
				deptId, "", pageIndex, pageSize);
		int total = ((Integer) taskListMap.get("taskTotal")).intValue();
		request.setAttribute("total", new Integer(total));
		request.setAttribute("pageSize", pageSize);
		List<EomsUndoSheetView> tasList = (List<EomsUndoSheetView>) taskListMap.get("taskList");
		
		if ("".equals(exportType)) {//不是导出
			for (EomsUndoSheetView eomsUndoSheetView : tasList) { //设置超时标识
				Date sheetCompleteLimitDate = eomsUndoSheetView.getCompleteTimeLimit();
				String overtimeFlag = OvertimeTipUtil.setOvertimeTipFlag(sheetCompleteLimitDate, 30);
				eomsUndoSheetView.setOvertimeType(overtimeFlag);
			}
		}
		request.setAttribute("taskList", tasList);
		if (taskListMap.get("taskCountList") != null) {
			List taskCountList = (List) taskListMap.get("taskCountList");
			List list = new ArrayList();
			for (int i = 0; taskCountList != null && i < taskCountList.size(); i++) {
				Object[] obj = (Object[]) taskCountList.get(i);
				HashMap map = new HashMap();
				

				String sheetType = StaticMethod.nullObject2String(obj[0]);
				if(!sheetType.equals("")){
					sheetType = sheetType.trim();
				}
				map.put("sheetType", sheetType);
				String url = "";
				url = sheetType + ".do";
				// 由于新业务试点工单的特殊性，所以必须将之replace掉
				if (sheetType.equals("businesspilot")) {
					sheetType = "business";
				}
				url = "../" + sheetType + "/" + url;
				map.put("sheetTypeUrl", url);
				String sheetTypeName = StaticMethod.nullObject2String( obj[1]);
				if(!sheetTypeName.equals("")){
					sheetTypeName = sheetTypeName.trim();
				}
				map.put("sheetTypeName", sheetTypeName);
				String count = StaticMethod.nullObject2String(obj[2]);
				if (!count.equals("")) {
					map.put("count", count.trim());
				}
				list.add(map);
			}
			request.setAttribute("taskCountList", list);
		}
		
		/**
		 * modify by chenyuanshu 2012-04-19 begin
		 * 由于某省直接把eoms里的url放到门户里，所以需要将token取出供后续页面中使用
		 */
		String Token = StaticMethod.nullObject2String(request
				.getParameter("Token"));
		if(!Token.equals("")){
			Token = "&Token="+Token;
		}
		request.setAttribute("Token", Token);
		/**
		 * modify by chenyuanshu 2012-04-19 end
		 */
		
		return mapping.findForward("undoAllList");
	}
	
	/**
	 * 显示所有待处理任务列表(portal)
	 */
	@SuppressWarnings("unchecked")
	public void getAllListsStartedBymeForPortal(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		/** 获取登陆信息，add by qinmin* */
		String userId = StaticMethod.nullObject2String(request
				.getParameter("userName"));
		/** 若无法从URL中获取userid，则从session中获取，modify by 秦敏 2009-08-12 begin */
		if (userId.equals("")) {
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request
					.getSession().getAttribute("sessionform");
			userId = sessionform.getUserid();
		}
		ITawSystemUserManager userManager = (ITawSystemUserManager) ApplicationContextHolder
				.getInstance().getBean("ItawSystemUserSaveManagerFlush");
		TawSystemUser user = new TawSystemUser();
		user = userManager.getUserByuserid(userId);
		String deptId = StaticMethod.null2String(user.getDeptid());
		// --------------用于分页，得到当前页号-------------
		final Integer pageIndex = new Integer(request.getParameter("curPage"));
		Integer pageSize = new Integer(request.getParameter("pageSize"));

		// 增加map对象，存放main，传入taskService.getUndoTask

		Map condition = new HashMap();
		condition.put("ifAtom", "true");
		IEomsAllSheetListService taskService = (IEomsAllSheetListService) getBean("IEomsAllSheetListService");
		HashMap taskListMap = null;
		if (pageSize != null && pageSize.intValue() != 0) {
			taskListMap = taskService.getAllSheetStartedByMe(condition, userId,
					deptId, "", pageIndex, pageSize);
		} else {
			try {
				taskListMap = taskService.getAllSheetStartedByMe(condition,
						userId, deptId, "", new Integer(0), new Integer(-1));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Feed feed = AtomUtilForTask.showStartedByMeAllSheetListForPortal(taskListMap,
				request);
		OutputStream os = response.getOutputStream();
		PrintStream ps = new PrintStream(os);
		feed.getDocument().writeTo(ps);
	}

	/**
	 * 显示所有由我启动的工单列表
	 */
	@SuppressWarnings("unchecked")
	public ActionForward getAllListsStartedByme(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		/** 获取登陆信息，add by qinmin* */
		String userId = StaticMethod.nullObject2String(request
				.getParameter("userName"));
		/** 若无法从URL中获取userid，则从session中获取，modify by 秦敏 2009-08-12 begin */
		if (userId.equals("")) {
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request
					.getSession().getAttribute("sessionform");
			userId = sessionform.getUserid();
		}

		String deptId = "";

		// 获取每页显示条数
		Integer pageSize = ((SheetAttributes) ApplicationContextHolder
				.getInstance().getBean("SheetAttributes")).getPageSize();

		String pageIndexName = new org.displaytag.util.ParamEncoder("taskList")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		// 当前页数
		Integer pageIndex = new Integer(GenericValidator.isBlankOrNull(request
				.getParameter(pageIndexName)) ? 0 : (Integer.parseInt(request
				.getParameter(pageIndexName)) - 1));
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		userId = sessionform.getUserid();
		deptId = sessionform.getDeptid();

		String exportType = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								"taskList")
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_EXPORTTYPE)));
		if (!exportType.equals("")) {
			pageSize = new Integer(-1);
		}
		String order = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								"taskList")
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_ORDER)));
		String sort = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								"taskList")
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_SORT)));
		String orderCondition = "";
		if (!order.equals("")) {
			if (order.equals("1")) {
				order = " asc";
			} else {
				order = " desc";
			}
		}
		if (!sort.equals("")) {
			orderCondition = " " + sort + order;
		}
		IEomsAllSheetListService taskService = (IEomsAllSheetListService) getBean("IEomsAllSheetListService");

		Map condition = new HashMap();
		String ifAtom = StaticMethod.nullObject2String(request.getParameter("ifAtom"));
		condition.put("ifAtom", ifAtom);
		condition.put("orderCondition", orderCondition);
		HashMap taskListMap = null;
		// if (!userId.equals("")pageSize != null && pageSize.intValue() != 0) {
		taskListMap = taskService.getAllSheetStartedByMe(condition, userId,
				deptId, "", pageIndex, pageSize);
		int total = ((Integer) taskListMap.get("taskTotal")).intValue();
		request.setAttribute("total", new Integer(total));
		request.setAttribute("pageSize", pageSize);
		List tasList = (List) taskListMap.get("taskList");
		request.setAttribute("taskList", tasList);

		if (taskListMap.get("taskCountList") != null) {
			List taskCountList = (List) taskListMap.get("taskCountList");
			List list = new ArrayList();
			for (int i = 0; taskCountList != null && i < taskCountList.size(); i++) {
				Object[] obj = (Object[]) taskCountList.get(i);
				HashMap map = new HashMap();
				

				String sheetType = StaticMethod.nullObject2String(obj[0]);
				if(!sheetType.equals("")){
					sheetType = sheetType.trim();
				}
				map.put("sheetType", sheetType);
				String url = "";
				url = sheetType + ".do";
				// 由于新业务试点工单的特殊性，所以必须将之replace掉
				if (sheetType.equals("businesspilot")) {
					sheetType = "business";
				}
				url = "../" + sheetType + "/" + url;
				map.put("sheetTypeUrl", url);
				String sheetTypeName = StaticMethod.nullObject2String( obj[1]);
				if(!sheetTypeName.equals("")){
					sheetTypeName = sheetTypeName.trim();
				}
				map.put("sheetTypeName", sheetTypeName);
				String count = StaticMethod.nullObject2String(obj[2]);
				if (!count.equals("")) {
					map.put("count", count.trim());
				}
				list.add(map);
			}
			request.setAttribute("taskCountList", list);
		}
		/**
		 * modify by chenyuanshu 2012-04-19 begin
		 * 由于某省直接把eoms里的url放到门户里，所以需要将token取出供后续页面中使用
		 */
		String Token = StaticMethod.nullObject2String(request
				.getParameter("Token"));
		if(!Token.equals("")){
			Token = "&Token="+Token;
		}
		request.setAttribute("Token", Token);
		return mapping.findForward("startedByMe");
	}
	
	/**
	 * 显示所有待处理任务列表(portal)
	 */
	@SuppressWarnings("unchecked")
	public void getAllDoneSheetForPortal(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		/** 获取登陆信息，add by qinmin* */
		String userId = StaticMethod.nullObject2String(request
				.getParameter("userName"));
		/** 若无法从URL中获取userid，则从session中获取，modify by 秦敏 2009-08-12 begin */
		if (userId.equals("")) {
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request
					.getSession().getAttribute("sessionform");
			userId = sessionform.getUserid();
		}
		ITawSystemUserManager userManager = (ITawSystemUserManager) ApplicationContextHolder
				.getInstance().getBean("ItawSystemUserSaveManagerFlush");
		TawSystemUser user = new TawSystemUser();
		user = userManager.getUserByuserid(userId);
		String deptId = StaticMethod.null2String(user.getDeptid());
		// --------------用于分页，得到当前页号-------------
		final Integer pageIndex = new Integer(request.getParameter("curPage"));
		Integer pageSize = new Integer(request.getParameter("pageSize"));

		// 增加map对象，存放main，传入taskService.getUndoTask

		Map condition = new HashMap();
		condition.put("ifAtom", "true");
		IEomsAllSheetListService taskService = (IEomsAllSheetListService) getBean("IEomsAllSheetListService");
		HashMap taskListMap = null;
		if (pageSize != null && pageSize.intValue() != 0) {
			taskListMap = taskService.getAllDoneSheet(condition, userId,
					deptId, "", pageIndex, pageSize);

		} else {
			try {
				taskListMap = taskService.getAllDoneSheet(condition,
						userId, deptId, "", new Integer(0), new Integer(-1));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Feed feed = AtomUtilForTask.showStartedByMeAllSheetListForPortal(taskListMap,
				request);
		OutputStream os = response.getOutputStream();
		PrintStream ps = new PrintStream(os);
		feed.getDocument().writeTo(ps);
	}

	/**
	 * 显示所有由我启动的工单列表
	 */
	@SuppressWarnings("unchecked")
	public ActionForward getAllDoneSheet(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String userId = StaticMethod.nullObject2String(request
				.getParameter("userName"));
		if (userId.equals("")) {
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request
					.getSession().getAttribute("sessionform");
			userId = sessionform.getUserid();
		}

		String deptId = "";

		// 获取每页显示条数
		Integer pageSize = ((SheetAttributes) ApplicationContextHolder
				.getInstance().getBean("SheetAttributes")).getPageSize();

		String pageIndexName = new org.displaytag.util.ParamEncoder("taskList")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		// 当前页数
		Integer pageIndex = new Integer(GenericValidator.isBlankOrNull(request
				.getParameter(pageIndexName)) ? 0 : (Integer.parseInt(request
				.getParameter(pageIndexName)) - 1));
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		userId = sessionform.getUserid();
		deptId = sessionform.getDeptid();

		String exportType = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								"taskList")
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_EXPORTTYPE)));
		if (!exportType.equals("")) {
			pageSize = new Integer(-1);
		}
		String order = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								"taskList")
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_ORDER)));
		String sort = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								"taskList")
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_SORT)));
		String orderCondition = "";
		if (!order.equals("")) {
			if (order.equals("1")) {
				order = " asc";
			} else {
				order = " desc";
			}
		}
		if (!sort.equals("")) {
			orderCondition = " " + sort + order;
		}
		IEomsAllSheetListService taskService = (IEomsAllSheetListService) getBean("IEomsAllSheetListService");

		Map condition = new HashMap();
		String ifAtom = StaticMethod.nullObject2String(request.getParameter("ifAtom"));
		condition.put("ifAtom", ifAtom);
		condition.put("orderCondition", orderCondition);
		HashMap taskListMap = null;
		// if (!userId.equals("")pageSize != null && pageSize.intValue() != 0) {
		taskListMap = taskService.getAllDoneSheet(condition, userId,
				deptId, "", pageIndex, pageSize);
		int total = ((Integer) taskListMap.get("taskTotal")).intValue();
		request.setAttribute("total", new Integer(total));
		request.setAttribute("pageSize", pageSize);
		List tasList = (List) taskListMap.get("taskList");
		request.setAttribute("taskList", tasList);

		if (taskListMap.get("taskCountList") != null) {
			List taskCountList = (List) taskListMap.get("taskCountList");
			List list = new ArrayList();
			for (int i = 0; taskCountList != null && i < taskCountList.size(); i++) {
				Object[] obj = (Object[]) taskCountList.get(i);
				HashMap map = new HashMap();
				

				String sheetType = StaticMethod.nullObject2String(obj[0]);
				if(!sheetType.equals("")){
					sheetType = sheetType.trim();
				}
				map.put("sheetType", sheetType);
				String url = "";
				url = sheetType + ".do";
				// 由于新业务试点工单的特殊性，所以必须将之replace掉
				if (sheetType.equals("businesspilot")) {
					sheetType = "business";
				}
				url = "../" + sheetType + "/" + url;
				map.put("sheetTypeUrl", url);
				String sheetTypeName = StaticMethod.nullObject2String( obj[1]);
				if(!sheetTypeName.equals("")){
					sheetTypeName = sheetTypeName.trim();
				}
				map.put("sheetTypeName", sheetTypeName);
				String count = StaticMethod.nullObject2String(obj[2]);
				if (!count.equals("")) {
					map.put("count", count.trim());
				}
				list.add(map);
			}
			request.setAttribute("taskCountList", list);
			request.setAttribute("ifDoneList", "done");
		}
		/**
		 * modify by chenyuanshu 2012-04-19 begin
		 * 由于某省直接把eoms里的url放到门户里，所以需要将token取出供后续页面中使用
		 */
		String Token = StaticMethod.nullObject2String(request
				.getParameter("Token"));
		if(!Token.equals("")){
			Token = "&Token="+Token;
		}
		request.setAttribute("Token", Token);
		return mapping.findForward("startedByMe");
	}

}
