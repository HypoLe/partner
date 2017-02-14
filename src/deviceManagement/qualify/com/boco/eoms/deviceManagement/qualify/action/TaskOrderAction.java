package com.boco.eoms.deviceManagement.qualify.action;



import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.deviceManagement.faultInfo.utils.CommonConstants;
import com.boco.eoms.deviceManagement.faultInfo.utils.CommonUtils;
import com.boco.eoms.deviceManagement.qualify.model.TaskOrder;
import com.boco.eoms.deviceManagement.qualify.model.TaskOrderForm;
import com.boco.eoms.deviceManagement.qualify.service.TaskOrderService;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

public class TaskOrderAction extends BaseAction {

	public ActionForward goToList(ActionMapping mapping) {
		ActionForward forward = mapping.findForward("goToList");
		String path = forward.getPath();
		return new ActionForward(path, false);
	}

	public ActionForward goToAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("goToAdd");
	}
	
	public ActionForward goToChart(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("goToChart");
	}
	
	public ActionForward goToDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id =  request.getParameter("id");
		TaskOrderService taskOrderService = (TaskOrderService) getBean("taskOrderService");
		TaskOrder taskOrder = taskOrderService.findById(id);
		request.setAttribute("taskOrder", taskOrder);
		return mapping.findForward("goToDetail");
	}

	public ActionForward goToOperate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		TaskOrderService taskOrderService = (TaskOrderService) getBean("taskOrderService");
		TaskOrder taskOrder = taskOrderService.findById(id);
		request.setAttribute("taskOrder", taskOrder);
		if(taskOrder.getNextOperId().equals(getUser(request).getUserid())||taskOrder.getNextOperId().startsWith(getUser(request).getDeptid())) {
			if (taskOrder.getStatus().equals("已派发"))
				return mapping.findForward("goToReply");
			if (taskOrder.getStatus().equals("已回复"))
				return mapping.findForward("goToEnd");
			if (taskOrder.getStatus().equals("未派发"))
				return mapping.findForward("goToSend");
		}
		return mapping.findForward("goToDetail");
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TaskOrderService taskOrderService = (TaskOrderService) getBean("taskOrderService");
		TaskOrderForm taskOrderForm = (TaskOrderForm) form;
		TaskOrder taskOrder = new TaskOrder();
		taskOrder.setTopic(taskOrderForm.getTopic());
		taskOrder.setType(taskOrderForm.getType());
		taskOrder.setNetGroup(taskOrderForm.getNetGroup());
		taskOrder.setAttachment(taskOrderForm.getAttachment());
		taskOrder.setDescription(StaticMethod.fromHtml(taskOrderForm.getDescription()));
		taskOrder.setRemarks1(taskOrderForm.getRemarks1());
		taskOrder.setStatus("未派发");
		taskOrder.setCreateUserId(getUserId(request));
		taskOrder.setCreateUserRole(getUser(request).getRolename());
		taskOrder.setCreateDeptId(getUser(request).getDeptname());
		taskOrder.setCreateTime(CommonUtils.toEomsStandardDate(new Date()));
		taskOrder.setNextOperId(getUserId(request));
		boolean success = taskOrderService.newAdd(taskOrder);
		if (success)
			return mapping.findForward("success");
		else
			return mapping.findForward("failure");
	}

	public ActionForward send(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TaskOrderService taskOrderService = (TaskOrderService) getBean("taskOrderService");
		TaskOrderForm taskOrderForm = (TaskOrderForm) form;
		String id = request.getParameter("id");
		TaskOrder taskOrder = taskOrderService.findById(id);
		taskOrder.setSendTo(taskOrderForm.getSendTo());
		taskOrder.setSendTo2(taskOrderForm.getSendTo2());
		taskOrder.setDeadline(taskOrderForm.getDeadline());
		if(taskOrder.getSendTo().matches("[0-9]+")) {
			taskOrder.setSendType("0");   //0为部门
		}else {
			taskOrder.setSendType("1");   //1为个人
		}
		if(taskOrder.getSendTo2().matches("[0-9]+")) {
			taskOrder.setSend2Type("0");   //0为部门
		}else {
			taskOrder.setSend2Type("1");   //1为个人
		}
		taskOrder.setStatus("已派发");
		taskOrder.setSendUserId(getUserId(request));
		taskOrder.setSendTime(CommonUtils.toEomsStandardDate(new Date()));
		taskOrder.setCreateUserId(getUserId(request));
		taskOrder.setCreateUserRole(getUser(request).getRolename());
		taskOrder.setCreateDeptId(getUser(request).getDeptname());
		taskOrder.setCreateTime(CommonUtils.toEomsStandardDate(new Date()));
		taskOrder.setNextOperId(taskOrder.getSendTo());
		taskOrderService.send(taskOrder);
		return mapping.findForward("success");
	}
	
	public ActionForward reply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		TaskOrderService taskOrderService = (TaskOrderService) getBean("taskOrderService");
		TaskOrderForm taskOrderForm = (TaskOrderForm) form;
		TaskOrder taskOrder = taskOrderService.findById(id);
		request.setAttribute("taskOrder", taskOrder);
		taskOrder.setReplyMsg(taskOrderForm.getReplayMsg());
		taskOrder.setReplyTime(CommonUtils.toEomsStandardDate(new Date()));
		if(CommonUtils.toEomsStandardDate(taskOrder.getReplyTime()).after(CommonUtils.toEomsStandardDate(taskOrder.getDeadline())))
			taskOrder.setDelay("1");   //1为延时回复
		else
			taskOrder.setDelay("0");   //0为按时回复
		taskOrder.setStatus("已回复");
		taskOrder.setNextOperId(taskOrder.getCreateUserId());
		taskOrder.setReplyUserId(getUserId(request));
		taskOrderService.reply(taskOrder);
		return mapping.findForward("success");
	}

	public ActionForward end(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		TaskOrderService taskOrderService = (TaskOrderService) getBean("taskOrderService");
		TaskOrderForm taskOrderForm = (TaskOrderForm) form;
		TaskOrder taskOrder = taskOrderService.findById(id);
		request.setAttribute("taskOrder", taskOrder);
		System.out.println("-----------"+taskOrderForm.getSatisfiedLevel());
		taskOrder.setSatisfiedLevel(taskOrderForm.getSatisfiedLevel());
		taskOrder.setEndMsg(taskOrderForm.getEndMsg());
		taskOrder.setStatus("已归档");
		taskOrder.setNextOperId("");
		taskOrder.setEndTime(CommonUtils.toEomsStandardDate(new Date()));		
		taskOrder.setEndUserId(getUserId(request));
		taskOrderService.end(taskOrder);
		return mapping.findForward("success");
	}

	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TaskOrderService taskOrderService = (TaskOrderService) getBean("taskOrderService");
		TaskOrderForm taskOrderForm = (TaskOrderForm) form;
		String pageIndexString = request
		.getParameter((new org.displaytag.util.ParamEncoder(
				"taskOrderList")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		int start = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
		.valueOf(pageIndexString).intValue() - 1;
		List<TaskOrder> taskOrderList = taskOrderService.search(getUser(request),taskOrderForm.getTopic(),taskOrderForm.getStatus(), taskOrderForm.getType(), start*CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE);
		System.out.println(taskOrderForm.getTopic());
		System.out.println(taskOrderForm.getStatus());
		System.out.println(taskOrderForm.getType());
		request.setAttribute("taskOrderList",taskOrderList);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", taskOrderService.count(getUser(request), taskOrderForm.getTopic(), taskOrderForm.getStatus(), taskOrderForm.getType()));
		return mapping.findForward("list");
	}
	
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TaskOrderService taskOrderService = (TaskOrderService) getBean("taskOrderService");
		String pageIndexString = request
		.getParameter((new org.displaytag.util.ParamEncoder(
				"taskOrderList")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		int start = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
		.valueOf(pageIndexString).intValue() - 1;
		List<TaskOrder> taskOrderList = taskOrderService.listAll(getUser(request), start*CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE);
		String uid=getUserId(request);
		request.setAttribute("uid", uid);
		request.setAttribute("taskOrderList",taskOrderList);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", taskOrderService.countAll(getUser(request)));
		return mapping.findForward("list");
	}
	
	public ActionForward listDraft(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		TaskOrderService taskOrderService = (TaskOrderService) getBean("taskOrderService");
		String pageIndexString = request
		.getParameter((new org.displaytag.util.ParamEncoder(
				"taskOrderList")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		int start = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
		.valueOf(pageIndexString).intValue() - 1;
		List<TaskOrder> taskOrderList = taskOrderService.listDraft(getUser(request), start*CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE);
		request.setAttribute("taskOrderList",taskOrderList);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", taskOrderService.countDraft(getUser(request)));
		return mapping.findForward("draft");
	}
	
	public ActionForward listHistory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TaskOrderService taskOrderService = (TaskOrderService) getBean("taskOrderService");
		String pageIndexString = request
		.getParameter((new org.displaytag.util.ParamEncoder(
				"taskOrderList")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		int start = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
		.valueOf(pageIndexString).intValue() - 1;
		List<TaskOrder> taskOrderList = taskOrderService.listHistory(start*CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE);
		request.setAttribute("taskOrderList",taskOrderList);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", taskOrderService.countHistory());
		return mapping.findForward("history");
	}
	
	public ActionForward searchHis(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TaskOrderService taskOrderService = (TaskOrderService) getBean("taskOrderService");
		TaskOrderForm taskOrderForm = (TaskOrderForm) form;
		String pageIndexString = request
		.getParameter((new org.displaytag.util.ParamEncoder(
				"taskOrderList")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		int start = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
		.valueOf(pageIndexString).intValue() - 1;
		List<TaskOrder> taskOrderList = taskOrderService.searchHis(taskOrderForm.getTopic(), taskOrderForm.getType(), start*CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE);
		System.out.println(taskOrderForm.getTopic());
		System.out.println(taskOrderForm.getStatus());
		System.out.println(taskOrderForm.getType());
		request.setAttribute("taskOrderList",taskOrderList);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", taskOrderService.countHis(taskOrderForm.getTopic(), taskOrderForm.getType()));
		return mapping.findForward("history");
	}
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		TaskOrderService taskOrderService = (TaskOrderService) getBean("taskOrderService");
		try {
			if(taskOrderService.delete(id)) {
				CommonUtils.printJsonSuccessMsg(response);
			}else {
				CommonUtils.printJsonFailureMsg(response);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}			
		return null;
	}
	
	public ActionForward importRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		TaskOrderService taskOrderService = (TaskOrderService) getBean("taskOrderService");
		TaskOrderForm taskOrderForm = (TaskOrderForm) form;
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();		
			String result = taskOrderService.importRecord(taskOrderForm.getFormFile().getInputStream(), taskOrderForm.getFormFile().getFileName(), getUserId(request), getUser(request).getRoleid(), getUser(request).getContactMobile(), getUser(request).getDeptname());
			writer.write(
					new Gson().toJson(new ImmutableMap.Builder<String, String>()
							.put("success", "true")
							.put("msg", "ok")
							.put("infor", "导入成功:"+result).build()));
		} catch (Exception e) {
			writer.write(
					new Gson().toJson(new ImmutableMap.Builder<String, String>()
							.put("success", "failure")
							.put("msg", "failure")
							.put("infor", "导入失败").build()));
			e.printStackTrace();
		} finally {
			if(writer != null) {
				writer.close();
			}
		}
		return null;
	}
	
	public ActionForward createChart(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setHeader("Cache-Control", "no-cache");   
        response.setContentType("image/png"); 
        String chartType = request.getParameter("chartType");
        TaskOrderService taskOrderService = (TaskOrderService) getBean("taskOrderService");
        JFreeChart chart = taskOrderService.createChart(chartType);
        if(chart!=null) {
        	 java.io.OutputStream ous = response.getOutputStream();
        	 ChartUtilities.writeChartAsPNG(ous, chart, 500, 300);  
        	 ous.flush();   
             ous.close();
        }
		return null;		
	}

}
