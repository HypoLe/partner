package com.boco.eoms.task.webapp.action;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.task.mgr.IEomsTask;
import com.boco.eoms.task.mgr.ITaskMgr;
import com.boco.eoms.task.model.Eoms_Task_User;
import com.boco.eoms.task.model.Task;
import com.boco.eoms.task.util.TaskAppUtil;
import com.boco.eoms.task.util.TaskConstants;
import com.boco.eoms.task.webapp.form.TaskForm;

public class TaskAppAction extends BaseAction{
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
		return searchApp(mapping, form, request, response);
	}
	/**
	 * 任务统计初始化
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchApp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {	
		return mapping.findForward("searchApp");
	}
	/**
	 * 任务统计(某时间某人参与的任务，以及任务完成率)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchApp_person(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				TaskConstants.TASK_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		ITaskMgr taskMgr = (ITaskMgr) getBean("taskMgr");
		TaskForm taskForm = (TaskForm) form;
		Task task = new Task();
		task.setPrincipal(taskForm.getPrincipal()) ;
		task.setStartTime(TaskAppUtil.changeTime2Long(taskForm.getStartTime())) ;
		task.setEndTime(TaskAppUtil.changeTime2Long(taskForm.getEndTime())) ;
		
		String queryStr="";
		String userId=sessionform.getUserid();
		queryStr+= "  and (task.principal='"+userId+"' or task.drafter='"+userId+"')";
		if(task.getStartTime()!= null && !"".equals(task.getStartTime())&&task.getEndTime()!= null && !"".equals(task.getEndTime())){
			queryStr += " and (( task.startTime >='"+task.getStartTime()+"' and task.startTime <= '" + task.getEndTime()
							+"') or ( task.endTime >= '"+task.getStartTime()+"' and task.endTime <= '"+task.getEndTime()+"'))";
		}if(task.getPrincipal()!= null && !"".equals(task.getPrincipal())){
			queryStr += " and task.principal like '%"+task.getPrincipal()+"%'";
		}	


		Map map = (Map) taskMgr.getTasks(pageIndex, pageSize, queryStr);
		List list = (List) map.get("result");
		//更改时间的现实格式为“yyyy-MM-dd”
		Iterator it = list.iterator() ;
		while(it.hasNext()){
			Task ta = (Task)it.next();
			ta.setEndTime(TaskAppUtil.changeTime(ta.getEndTime())) ;
			ta.setStartTime(TaskAppUtil.changeTime(ta.getStartTime())) ;
		}
		request.setAttribute(TaskConstants.TASK_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);

		return mapping.findForward("searchApp_person");
	}
	/**
	 * 任务统计（某个大任务的所有及时率和完成率）初始化
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchApp_task(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				TaskConstants.TASK_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		ITaskMgr taskMgr = (ITaskMgr) getBean("taskMgr");
		
		String queryStr="";
		String userId=sessionform.getUserid();
		queryStr+= " and (task.principal='"+userId+"' or task.drafter='"+userId+"') and task.parentTaskId='-1'";
		
		Map map = (Map) taskMgr.getTasks(pageIndex, pageSize, queryStr);
		List list = (List) map.get("result");
		//更改时间的现实格式为“yyyy-MM-dd”
		Iterator it = list.iterator() ;
		while(it.hasNext()){
			Task task = (Task)it.next();
			task.setEndTime(TaskAppUtil.changeTime(task.getEndTime())) ;
			task.setStartTime(TaskAppUtil.changeTime(task.getStartTime())) ;
		}
		request.setAttribute(TaskConstants.TASK_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);

		return mapping.findForward("searchApp_task");
	}
	/**
	 * 查询某大任务下所有任务的完成率和及时率
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchApp_taskOne(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
								.getPageSize();
		ITaskMgr taskMgr = (ITaskMgr) getBean("taskMgr");
		TaskForm taskForm = (TaskForm)form ;
		Task task = taskMgr.getTask(taskForm.getTaskId()) ;
		TaskAppUtil tu = new TaskAppUtil() ;
		tu.taskSon(task) ;
		List list = tu.li;
		request.setAttribute(TaskConstants.TASK_LIST, list);
		request.setAttribute("resultSize", Integer.valueOf(list.size()));
		request.setAttribute("pageSize", pageSize);

		return mapping.findForward("searchApp_taskOne");
	}
}
