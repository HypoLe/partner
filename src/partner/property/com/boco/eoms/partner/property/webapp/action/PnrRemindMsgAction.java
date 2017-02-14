package com.boco.eoms.partner.property.webapp.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.activiti.partner.process.model.PnrTransferOffice;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.activiti.partner.process.service.IPnrTransferNewPrecheckService;
import com.boco.activiti.partner.process.service.IPnrTransferOfficeService;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.common.fileupload.Request;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.property.model.PnrElectricityBills;
import com.boco.eoms.partner.property.model.PnrPropertyAgreement;
import com.boco.eoms.partner.property.model.PnrRemindMsg;
import com.boco.eoms.partner.property.model.PnrRentBills;
import com.boco.eoms.partner.property.service.IPnrElectricityBillsService;
import com.boco.eoms.partner.property.service.IPnrPropertyAgreementService;
import com.boco.eoms.partner.property.service.IPnrRemindMsgService;
import com.boco.eoms.partner.property.service.IPnrRentBillsService;
import com.boco.eoms.partner.property.util.PnrPropertyAgreementConstant;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public class PnrRemindMsgAction extends BaseAction{
	
	/**
	 * 提醒列表
	 *fengguangping
	 * Sep 11, 2012-10:13:20 AM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward remindMsgList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		String type="agreement";
		String type1="rentBills";
		String type2="electricityBills";
		String userId=this.getUserId(request);
		IPnrRemindMsgService pnrRemindMsgService=(IPnrRemindMsgService)getBean("pnrRemindMsgService");
		Search agreementSearch=new Search();
		Search rentBillsSearch=new Search();
		Search electricityBillsSearch=new Search();
		agreementSearch.addFilterLike("type", type);
		rentBillsSearch.addFilterLike("type", type1);
		electricityBillsSearch.addFilterLike("type", type2);
		agreementSearch.addFilterNotEqual("isRead", "1");
		rentBillsSearch.addFilterNotEqual("isRead", "1");
		electricityBillsSearch.addFilterNotEqual("isRead", "1");
		agreementSearch.addFilterLike("remindObject", "%"+userId+"%");//对用户id过滤,不同用户的id看到的信息不相同；
		rentBillsSearch.addFilterLike("remindObject", "%"+userId+"%");
		electricityBillsSearch.addFilterLike("remindObject", "%"+userId+"%");
		SearchResult agreementSearchResult=pnrRemindMsgService.searchAndCount(agreementSearch);
		SearchResult rentBillsSearchResult=pnrRemindMsgService.searchAndCount(rentBillsSearch);
		SearchResult electricityBillsSearchResult=pnrRemindMsgService.searchAndCount(electricityBillsSearch);
		List agreementRemindMsgList=agreementSearchResult.getResult();
		List rentBillsRemindMsgList=rentBillsSearchResult.getResult();
		List electricityBillsRemindMsgList=electricityBillsSearchResult.getResult();
		StringBuffer msgStr=new StringBuffer();
		//待接口调用工单提醒 start
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String processDefinitionKey1 ="transferArteryPrecheckProcess";//干线
		String processDefinitionKey2 ="overHaulNewProcess";//大修改造
		String processDefinitionKey3 ="transferNewPrechechProcess";//本地网
		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService)getBean("pnrTransferNewPrecheckService");
		int count=pnrTransferNewPrecheckService.workOrderRemindCount(processDefinitionKey1,userId,"","","","","");
		int count1=pnrTransferNewPrecheckService.workOrderRemindCount(processDefinitionKey2,userId,"","","","","");
		int count2=pnrTransferNewPrecheckService.workOrderRemindCount(processDefinitionKey3,userId,"","","","","");
		System.out.println("干线待接口调用工单数===="+count);
		System.out.println("大修改造待接口调用工单数===="+count1);
		System.out.println("本地网待接口调用工单数===="+count2);
		
		//待接口调用工单提醒 end
		if(count>0){
			msgStr.append("<font style='font-size:12px;'>您有<font style='color:red;font-size:12px;'>"+count+"</font>条干线预检预修工单处于接口待调用状态!<a href='"+request.getContextPath()+"/partner/property/remind.do?method=workOrderRemind&processDefinitionKey=transferArteryPrecheckProcess' target='_blank'>查看详情</a></font><br>");
		}
		if(count1>0){
			msgStr.append("<font style='font-size:12px;'>您有<font style='color:red;font-size:12px;'>"+count1+"</font>条大修改造工单处于接口待调用状态!<a href='"+request.getContextPath()+"/partner/property/remind.do?method=workOrderRemind&processDefinitionKey=overHaulNewProcess' target='_blank'>查看详情</a></font><br>");
		}
		if(count2>0){
			msgStr.append("<font style='font-size:12px;'>您有<font style='color:red;font-size:12px;'>"+count2+"</font>条本地网预检预修工单处于接口待调用状态!<a href='"+request.getContextPath()+"/partner/property/remind.do?method=workOrderRemind&processDefinitionKey=transferNewPrechechProcess' target='_blank'>查看详情</a></font><br>");
		}
		if(agreementRemindMsgList.size()>0){
			msgStr.append("<font style='font-size:12px;'>您有<font style='color:red;font-size:12px;'>"+agreementRemindMsgList.size()+"</font>条合同即将到期!<a href='"+request.getContextPath()+"/partner/property/remind.do?method=goToUnreadMsgPage&msgType=agreement' target='_blank'>查看详情</a></font><br>");
		}
		if(rentBillsRemindMsgList.size()>0){
			msgStr.append("<font style='font-size:12px;'>您有<font style='color:red;font-size:12px;'>"+rentBillsRemindMsgList.size()+"</font>条电费合同需要支付!<a href='"+request.getContextPath()+"/partner/property/remind.do?method=goToUnreadMsgPage&msgType=rentBills' target='_blank'>查看详情</a></font><br>");
		}
		if(electricityBillsRemindMsgList.size()>0){
			msgStr.append("<font style='font-size:12px;'>您有<font style='color:red;font-size:12px;'>"+electricityBillsRemindMsgList.size()+"</font>条租赁合同需要支付!<a href='"+request.getContextPath()+"/partner/property/remind.do?method=goToUnreadMsgPage&msgType=electricityBills' target='_blank'>查看详情</a></font><br>");
		}
		if ("".equals(msgStr.toString())) {
			return null;//如果没有提示信息将不弹出窗口；
		}
		request.setAttribute("msg", msgStr.toString());
		return mapping.findForward("msgWin");
	}
	/**
	 * 阅读电费支付提醒信息,阅读后信息后，本次提醒完成不再发送提醒
	 *fengguangping
	 * Sep 11, 2012-10:17:56 AM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exc 
	 */
	public ActionForward readMsg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("utf-8");
		PrintWriter writer=null;
		try {
			writer=response.getWriter();
			String id=request.getParameter("id");
			IPnrRemindMsgService pnrRemindMsgService=(IPnrRemindMsgService)getBean("pnrRemindMsgService");
			PnrRemindMsg pnrRemindMsg=pnrRemindMsgService.find(id);
			pnrRemindMsg.setReadTime(CommonUtils.toEomsStandardDate(new Date()));
			pnrRemindMsg.setIsRead("1");
			pnrRemindMsg.setRemark("已阅");
			pnrRemindMsg.setReadUser(this.getUserId(request));
			String refId=pnrRemindMsg.getRefId();
			IPnrElectricityBillsService pnrElectricityBillsService=(IPnrElectricityBillsService)getBean("pnrElectricityBillsService");
			PnrElectricityBills pnrElectricityBills=pnrElectricityBillsService.find(id);
			pnrRemindMsgService.save(pnrRemindMsg);
			writer.write(
					new Gson().toJson(new ImmutableMap.Builder<String, String>()
							.put("success", "true")
							.put("msg", "ok")
							.put("infor", "批阅成功,该提醒不再提示！").build()));
		} catch (Exception e) {
			BocoLog.info(this, "批阅出错！");
			e.printStackTrace();
			writer.write(
					new Gson().toJson(new ImmutableMap.Builder<String, String>()
							.put("success", "true")
							.put("msg", "ok")
							.put("infor", "批阅出错！").build()));
		} finally {
			if (writer!=null) {
				writer.close();
			}
			return null;
		}
	}
	 /**
	  * 批量批阅提醒信息
	  *fengguangping
	  * Sep 19, 2012-4:40:30 PM
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception
	  */
	 public ActionForward readAllmsg(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception{
		 	response.setCharacterEncoding("utf-8");
			PrintWriter writer=null;
			try {
				writer=response.getWriter();
				String ids=request.getParameter("id");
				String[] idsAarray=ids.split(";");
				IPnrRemindMsgService pnrRemindMsgService=(IPnrRemindMsgService)getBean("pnrRemindMsgService");
				for (int i = 0; i < idsAarray.length; i++) {
					String id = idsAarray[i];
					PnrRemindMsg pnrRemindMsg=pnrRemindMsgService.find(id);
					pnrRemindMsg.setReadTime(CommonUtils.toEomsStandardDate(new Date()));
					pnrRemindMsg.setIsRead("1");
					pnrRemindMsg.setRemark("已阅");
					pnrRemindMsg.setReadUser(this.getUserId(request));
					pnrRemindMsgService.save(pnrRemindMsg);
				}
				writer.write(
						new Gson().toJson(new ImmutableMap.Builder<String, String>()
								.put("success", "true")
								.put("msg", "ok")
								.put("infor", "批阅成功！").build()));
			} catch (Exception e) {
				BocoLog.info(this, "批阅出错！");
				e.printStackTrace();
				writer.write(
						new Gson().toJson(new ImmutableMap.Builder<String, String>()
								.put("success", "true")
								.put("msg", "ok")
								.put("infor", "批阅出错！").build()));
			} finally {
				if (writer!=null) {
					writer.close();
				}
				return null;
			}
	 }
	 /**
		 * 未读提醒列表
		 *fengguangping
		 * Sep 19, 2012-4:39:38 PM
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
	public ActionForward goToUnreadMsgPage (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		IPnrRemindMsgService pnrRemindMsgService=(IPnrRemindMsgService)getBean("pnrRemindMsgService");
		Search search = new Search();
		String msgType=StaticMethod.null2String(request.getParameter("msgType"));
		if (!"".equals(msgType)) {
			search.addFilterEqual("type", msgType);
		}
		search.addFilterNotEqual("isRead", "1");
		String userId=this.getUserId(request);
		search.addFilterLike("remindObject", "%"+userId+"%");
		int firstResult = CommonUtils.getFirstResultOfDisplayTag(request, "unreadMsgList");
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		search = CommonUtils.getSqlFromRequestMap(request, search);
		SearchResult<PnrRemindMsg> searchResult = pnrRemindMsgService.searchAndCount(search);
		List<PnrRemindMsg> unreadMsgList = searchResult.getResult();
		request.setAttribute("unreadMsgList",unreadMsgList);
		request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
		request.setAttribute("resultSize", searchResult.getTotalCount());
		return mapping.findForward("goToUnreadMsgPage");
	}
	/**
	 * 已读提醒列表
	 *fengguangping
	 * Sep 19, 2012-4:39:38 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward goToReadedMsgPage (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		IPnrRemindMsgService pnrRemindMsgService=(IPnrRemindMsgService)getBean("pnrRemindMsgService");
		Search search = new Search();
		search.addFilterEqual("isRead", "1");
		String userId=this.getUserId(request);
		search.addFilterLike("remindObject", "%"+userId+"%");
		int firstResult = CommonUtils.getFirstResultOfDisplayTag(request, "readedMsgList");
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		search = CommonUtils.getSqlFromRequestMap(request, search);
		SearchResult<PnrRemindMsg> searchResult = pnrRemindMsgService.searchAndCount(search);
		List<PnrRemindMsg> readedMsgList = searchResult.getResult();
		request.setAttribute("readedMsgList",readedMsgList);
		request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
		request.setAttribute("resultSize", searchResult.getTotalCount());
		return mapping.findForward("goToReadedMsgPage");
	}
	
	 /**
	 * 工单提醒查看详细列表
	 *fengguangping
	 * Sep 19, 2012-4:39:38 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward workOrderRemind (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		String processDefinitionKey=StaticMethod.null2String(request.getParameter("processDefinitionKey"));
		int pageSize = CommonConstants.PAGE_SIZE;
		String tempPageSize = request.getParameter("pagesize");
		if (tempPageSize != null && !"".equals(tempPageSize)) {
			pageSize = Integer.parseInt(tempPageSize);
		}
		
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();
	//	HistoryService historyService = (HistoryService) getBean("historyService");
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		
		String sendStartTime = request.getParameter("sheetAcceptLimit");
		String sendEndTime = request.getParameter("sheetCompleteLimit");
		String wsNum = request.getParameter("wsNum");
		String wsTitle = request.getParameter("wsTitle");
		String status = request.getParameter("status");
		
		//IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
//		IPnrTransferPrecheckService pnrTransferPrecheckService = (IPnrTransferPrecheckService) getBean("pnrTransferPrecheckService");
		IPnrTransferNewPrecheckService pnrTransferNewPrecheckService = (IPnrTransferNewPrecheckService)getBean("pnrTransferNewPrecheckService");
		
		//待接口调用工单条数
		int total = pnrTransferNewPrecheckService.workOrderRemindCount(processDefinitionKey,userId,sendStartTime,
				sendEndTime,wsNum,wsTitle,status);
		
		//待接口调用工单明细
		List<TaskModel> rPnrTicketList = pnrTransferNewPrecheckService.workOrderRemind(processDefinitionKey,userId, sendStartTime,
				sendEndTime, wsNum, wsTitle, status, firstResult,
				endResult, pageSize);
		
		request.setAttribute("taskList", rPnrTicketList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);
		request.setAttribute("startTime", sendStartTime);
		request.setAttribute("endTime", sendEndTime);
		request.setAttribute("wsNum", wsNum);
		request.setAttribute("wsTitle", wsTitle);
		request.setAttribute("wsStatus", status);
		request.setAttribute("processDefinitionKey", processDefinitionKey);
		return mapping.findForward("workOrderRemind");
	}
	
	/**
	 * 本次提醒完成不再发送提醒
	 *chujingang
	 * Sep 11, 2012-10:17:56 AM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exc 
	 */
	public ActionForward readWorkOrderRemind(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("utf-8");
		PrintWriter writer=null;
		try {
			writer=response.getWriter();
			String processInstanceId=request.getParameter("processInstanceId");
			// 获取该处理工单
			Search search = new Search();
			search.addFilterEqual("processInstanceId",
					processInstanceId);
			List<PnrTransferOffice> pnrTicketList = null;
			PnrTransferOffice pnrTicket =null;
			IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
			pnrTicketList = pnrTransferOfficeService.search(search);
			if (pnrTicketList != null && pnrTicketList.size() > 0) {
				pnrTicket = pnrTicketList.get(0);
				pnrTicket.setIsRemind(1);
			}	
			// 保存主表
			pnrTransferOfficeService.save(pnrTicket);
			writer.write(
					new Gson().toJson(new ImmutableMap.Builder<String, String>()
							.put("success", "true")
							.put("msg", "ok")
							.put("infor", "批阅成功,该提醒不再提示！").build()));
		} catch (Exception e) {
			BocoLog.info(this, "批阅出错！");
			e.printStackTrace();
			writer.write(
					new Gson().toJson(new ImmutableMap.Builder<String, String>()
							.put("success", "true")
							.put("msg", "ok")
							.put("infor", "批阅出错！").build()));
		} finally {
			if (writer!=null) {
				writer.close();
			}
			return null;
		}
	}
	 /**
	  * 批量批阅线路工单提醒信息
	  *chujingang
	  * Sep 19, 2012-4:40:30 PM
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception
	  */
	 public ActionForward readAllWorkOrderRemind(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception{
		 	response.setCharacterEncoding("utf-8");
			PrintWriter writer=null;
			try {
				writer=response.getWriter();
				String ids=request.getParameter("id");
				String[] idsAarray=ids.split(";");
				IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
				
				for (int i = 0; i < idsAarray.length; i++) {
					String processInstanceId = idsAarray[i];
					// 获取该处理工单
					Search search = new Search();
					search.addFilterEqual("processInstanceId",
							processInstanceId);
					List<PnrTransferOffice> pnrTicketList = null;
					PnrTransferOffice pnrTicket =null;
					pnrTicketList = pnrTransferOfficeService.search(search);
					if (pnrTicketList != null && pnrTicketList.size() > 0) {
						pnrTicket = pnrTicketList.get(0);
						pnrTicket.setIsRemind(1);
					}	
					// 保存主表
					pnrTransferOfficeService.save(pnrTicket);
				}
				writer.write(
						new Gson().toJson(new ImmutableMap.Builder<String, String>()
								.put("success", "true")
								.put("msg", "ok")
								.put("infor", "批阅成功！").build()));
			} catch (Exception e) {
				BocoLog.info(this, "批阅出错！");
				e.printStackTrace();
				writer.write(
						new Gson().toJson(new ImmutableMap.Builder<String, String>()
								.put("success", "true")
								.put("msg", "ok")
								.put("infor", "批阅出错！").build()));
			} finally {
				if (writer!=null) {
					writer.close();
				}
				return null;
			}
	 }
}
