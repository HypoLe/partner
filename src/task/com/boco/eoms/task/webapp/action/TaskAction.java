package com.boco.eoms.task.webapp.action;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.ui.util.JSONUtil;
import com.boco.eoms.task.mgr.IEomsTask;
import com.boco.eoms.task.mgr.ITaskMgr;
import com.boco.eoms.task.model.Eoms_Task_User;
import com.boco.eoms.task.util.TaskAppUtil;

/**
 * <p>
 * Title:任务action
 * </p>
 * <p>
 * Description:任务action
 * </p>
 * <p>
 * Sun Jul 05 17:19:32 CST 2009
 * </p>
 * 
 * @author qiuzi
 * @version 3.5
 * 
 */
public final class TaskAction extends BaseAction {

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
		return drafterPage(mapping, form, request, response);
	}

	/**
	 * 打开任务起草人工作页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward drafterPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// --- 获取查询条件 ---
		String sName = StaticMethod.null2String(request.getParameter("sName")); // 任务名称
		String sPrincipal = StaticMethod.null2String(request
				.getParameter("sPrincipal")); // 负责人
		String sStartTime = StaticMethod.null2String(request
				.getParameter("sStartTime")); // 开始时间
		String sEndTime = StaticMethod.null2String(request
				.getParameter("sEndTime")); // 结束时间
		String sStatus = StaticMethod.null2String(request
				.getParameter("sStatus")); // 完成状态

		// --- 查询任务 ---
		String taskData = "<Grid><Body><B></B></Body></Grid>";
		ITaskMgr taskMgr = (ITaskMgr) getBean("taskMgr");
		String drafter = ((TawSystemSessionForm) request.getSession()
				.getAttribute("sessionform")).getUserid();
		if ("".equals(sName) && "".equals(sPrincipal) && "".equals(sStartTime)
				&& "".equals(sEndTime) && "".equals(sStatus)) { // 没有传入查询条件或是从链接访问
			taskData = taskMgr.listTasksByDrafter(drafter);
			request.setAttribute("sStartTime", TaskAppUtil
					.getFirstDayOfMonth("yyyy-MM-dd"));
			request.setAttribute("sEndTime", TaskAppUtil
					.getLastDayOfMonth("yyyy-MM-dd"));
		} else {
			StringBuffer whereStr = new StringBuffer(" where task.drafter='" + drafter + "' ");
			if (!"".equals(sName)) {
				whereStr.append(" and task.name like '%" + sName + "%'");
				request.setAttribute("sName", sName);
			}
			if (!"".equals(sPrincipal)) {
				whereStr.append(" and task.principal = '" + sPrincipal + "'");
				request.setAttribute("sPrincipal", sPrincipal);
			}
			if (!"".equals(sStartTime) && !"".equals(sEndTime)) {
				sStartTime = TaskAppUtil.changeTime2Long(sStartTime
						+ " 00:00:00");
				sEndTime = TaskAppUtil.changeTime2Long(sEndTime + " 23:59:59");
				whereStr.append(" and ((task.startTime >= '" + sStartTime
						+ "' and task.startTime <= '" + sEndTime
						+ "') or (task.endTime >= '" + sStartTime
						+ "' and task.endTime <= '" + sEndTime + "'))");
				request.setAttribute("sStartTime", TaskAppUtil
						.changeTime(sStartTime));
				request.setAttribute("sEndTime", TaskAppUtil
						.changeTime(sEndTime));
			}
			if ("1".equals(sStatus)) {
				whereStr.append(" and task.progress = '100'");
			} else if ("0".equals(sStatus)) {
				whereStr.append(" and task.progress <> '100'");
			}
			request.setAttribute("sStatus", sStatus);
			taskData = taskMgr.searchTasksByDrafter(whereStr.toString());
		}
		request.setAttribute("taskData", taskData);

		// --- 获取当前用户可选择的负责人下拉列表 ---
		String principalEnum = "|";
		String principalEnumKey = "|";
		IEomsTask eomstask = (IEomsTask) getBean("IEomsTaskManager");
		List userList = eomstask.getEomsTaskUser(drafter);
		for (Iterator it = userList.iterator(); it.hasNext();) {
			Eoms_Task_User user = (Eoms_Task_User) it.next();
			principalEnum = principalEnum + "|" + user.getUsername();
			principalEnumKey = principalEnumKey + "|" + user.getUserid();
		}
		request.setAttribute("principalEnum", principalEnum);
		request.setAttribute("principalEnumKey", principalEnumKey);
		request.setAttribute("userList", userList);

		return mapping.findForward("drafterPage");
	}

	/**
	 * 打开任务接收人工作页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward principalPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// --- 获取查询条件 ---
		String sName = StaticMethod.null2String(request.getParameter("sName")); // 任务名称
		String sDrafter = StaticMethod.null2String(request
				.getParameter("sDrafter")); // 派发人
		String sStartTime = StaticMethod.null2String(request
				.getParameter("sStartTime")); // 开始时间
		String sEndTime = StaticMethod.null2String(request
				.getParameter("sEndTime")); // 结束时间
		String sStatus = StaticMethod.null2String(request
				.getParameter("sStatus")); // 完成状态

		// --- 查询任务 ---
		String taskData = "<Grid><Body><B></B></Body></Grid>";
		ITaskMgr taskMgr = (ITaskMgr) getBean("taskMgr");
		String principal = ((TawSystemSessionForm) request.getSession()
				.getAttribute("sessionform")).getUserid();
		if ("".equals(sName) && "".equals(sDrafter) && "".equals(sStartTime)
				&& "".equals(sEndTime) && "".equals(sStatus)) { // 没有传入查询条件或是从链接访问
			taskData = taskMgr.listTasksByPrincipal(principal);
			request.setAttribute("sStartTime", TaskAppUtil
					.getFirstDayOfMonth("yyyy-MM-dd"));
			request.setAttribute("sEndTime", TaskAppUtil
					.getLastDayOfMonth("yyyy-MM-dd"));
		} else {
			StringBuffer whereStr = new StringBuffer(" where task.principal='" + principal + "' ");
			if (!"".equals(sName)) {
				whereStr.append(" and task.name like '%" + sName + "%'");
				request.setAttribute("sName", sName);
			}
			if (!"".equals(sDrafter)) {
				whereStr.append(" and task.drafter = '" + sDrafter + "'");
				request.setAttribute("sDrafter", sDrafter);
			}
			if (!"".equals(sStartTime) && !"".equals(sEndTime)) {
				sStartTime = TaskAppUtil.changeTime2Long(sStartTime
						+ " 00:00:00");
				sEndTime = TaskAppUtil.changeTime2Long(sEndTime + " 23:59:59");
				whereStr.append(" and ((task.startTime >= '" + sStartTime
						+ "' and task.startTime <= '" + sEndTime
						+ "') or (task.endTime >= '" + sStartTime
						+ "' and task.endTime <= '" + sEndTime + "'))");
				request.setAttribute("sStartTime", TaskAppUtil
						.changeTime(sStartTime));
				request.setAttribute("sEndTime", TaskAppUtil
						.changeTime(sEndTime));
			}
			if ("1".equals(sStatus)) {
				whereStr.append(" and task.progress = '100'");
			} else if ("0".equals(sStatus)) {
				whereStr.append(" and task.progress <> '100'");
			}
			request.setAttribute("sStatus", sStatus);
			taskData = taskMgr.searchTasksByPrincipal(principal, whereStr.toString());
		}
		request.setAttribute("taskData", taskData);

		// --- 获取当前用户可选择的负责人下拉列表 ---
		String principalEnum = "|";
		String principalEnumKey = "|";
		IEomsTask eomstask = (IEomsTask) getBean("IEomsTaskManager");
		List userList = eomstask.getEomsTaskUser(principal);
		for (Iterator it = userList.iterator(); it.hasNext();) {
			Eoms_Task_User user = (Eoms_Task_User) it.next();
			principalEnum = principalEnum + "|" + user.getUsername();
			principalEnumKey = principalEnumKey + "|" + user.getUserid();
		}
		principalEnum = principalEnum + "|" + ((TawSystemSessionForm) request.getSession()
				.getAttribute("sessionform")).getUsername();
		principalEnumKey = principalEnumKey + "|" + ((TawSystemSessionForm) request.getSession()
				.getAttribute("sessionform")).getUserid();
		
		request.setAttribute("principalEnum", principalEnum);
		request.setAttribute("principalEnumKey", principalEnumKey);
		
		// --- 获取选择了当前用户作为负责人的派发人列表
		List drafterList = eomstask.listTaskDrafter(principal);
		request.setAttribute("userList", drafterList);
		
		return mapping.findForward("principalPage");
	}

	/**
	 * 保存起草页面改动数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveDrafterChanges(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String xmlData = StaticMethod.null2String(request.getParameter("grid"));
		String drafter = ((TawSystemSessionForm) request.getSession()
				.getAttribute("sessionform")).getUserid();
		ITaskMgr taskMgr = (ITaskMgr) getBean("taskMgr");
		taskMgr.saveChangedTasks(xmlData, drafter);
		return drafterPage(mapping, form, request, response);
	}

	/**
	 * 保存待处理页面改动数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward savePrincipalChanges(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String xmlData = StaticMethod.null2String(request.getParameter("grid"));
		String drafter = ((TawSystemSessionForm) request.getSession()
				.getAttribute("sessionform")).getUserid();
		ITaskMgr taskMgr = (ITaskMgr) getBean("taskMgr");
		taskMgr.saveChangedTasks(xmlData, drafter);
		return principalPage(mapping, form, request, response);
	}

	/**
	 * ajax任务冲突校验，返回
	 */
	public void checkTaskConflict(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String taskId = StaticMethod
				.null2String(request.getParameter("taskId"));
		String principal = StaticMethod.null2String(request
				.getParameter("principal"));
		String startTime = StaticMethod.null2String(request
				.getParameter("startTime"));
		String endTime = StaticMethod.null2String(request
				.getParameter("endTime"));
		String conflict = "";
		if (!"".equals(principal) && !"".equals(startTime)
				&& !"".equals(endTime)) {
			startTime = TaskAppUtil.changeTime2Long(startTime + " 00:00:00");
			endTime = TaskAppUtil.changeTime2Long(endTime + " 00:00:00");
			ITaskMgr taskMgr = (ITaskMgr) getBean("taskMgr");
			conflict = taskMgr.listTaskConflict(principal, startTime, endTime,
					taskId);
		}
		JSONUtil.print(response, conflict);
	}

}