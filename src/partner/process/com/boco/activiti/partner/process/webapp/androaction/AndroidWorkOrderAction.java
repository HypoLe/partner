package com.boco.activiti.partner.process.webapp.androaction;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.activiti.engine.FormService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.boco.activiti.partner.process.model.PnrAndroidPhotoFile;
import com.boco.activiti.partner.process.model.PnrAndroidWorkOderPhotoFile;
import com.boco.activiti.partner.process.model.PnrTaskTicket;
import com.boco.activiti.partner.process.model.PnrTaskTicketHandle;
import com.boco.activiti.partner.process.model.PnrTransferOffice;
import com.boco.activiti.partner.process.model.PnrTransferOfficeHandle;
import com.boco.activiti.partner.process.model.PnrTransferSpotcheck;
import com.boco.activiti.partner.process.model.PnrTroubleTicket;
import com.boco.activiti.partner.process.model.PnrTroubleTicketHandle;
import com.boco.activiti.partner.process.model.RoomDemolition;
import com.boco.activiti.partner.process.model.RoomDemolitionHandle;
import com.boco.activiti.partner.process.model.RoomDemolitionSpotcheck;
import com.boco.activiti.partner.process.po.AndroidQuery;
import com.boco.activiti.partner.process.po.AndroidWorkOrderTask;
import com.boco.activiti.partner.process.po.ParameterModel;
import com.boco.activiti.partner.process.po.PnrOltBbuOfficeMainModel;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.activiti.partner.process.po.TransferMaleGuestReturn;
import com.boco.activiti.partner.process.service.IPnrAndroidMgr;
import com.boco.activiti.partner.process.service.IPnrAndroidWorkOderPhotoFileService;
import com.boco.activiti.partner.process.service.IPnrOltBbuOfficeRelationService;
import com.boco.activiti.partner.process.service.IPnrTaskTicketHandleService;
import com.boco.activiti.partner.process.service.IPnrTaskTicketService;
import com.boco.activiti.partner.process.service.IPnrTransferOfficeHandleService;
import com.boco.activiti.partner.process.service.IPnrTransferOfficeService;
import com.boco.activiti.partner.process.service.IPnrTransferSpotcheckService;
import com.boco.activiti.partner.process.service.IPnrTroubleTicketHandleService;
import com.boco.activiti.partner.process.service.IPnrTroubleTicketService;
import com.boco.activiti.partner.process.service.IProcessTaskService;
import com.boco.activiti.partner.process.service.IRoomDemolitionHandleService;
import com.boco.activiti.partner.process.service.IRoomDemolitionService;
import com.boco.activiti.partner.process.service.IRoomDemolitionSpotcheckService;
import com.boco.activiti.partner.process.service.ISceneTemplateService;
import com.boco.activiti.partner.process.webapp.action.PnrTransferOfficeAction;
import com.boco.activiti.partner.process.webapp.action.RoomDemolitionAction;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.common.log.BocoLog;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.dept.service.bo.TawSystemDeptBo;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.bo.impl.TawSystemUserRoleBo;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcService;
import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;
import com.boco.eoms.mobile.util.MobileCommonUtils;
import com.boco.eoms.mobile.util.MobileConstants;
import com.boco.eoms.partner.inspect.dao.hibernate.PnrInspectTaskFileDaoHibernate;
import com.boco.eoms.partner.process.util.CommonUtils;
import com.boco.eoms.partner.process.util.MaleGuestReturnThead;
import com.boco.eoms.partner.process.util.SendMsgThread;
import com.boco.eoms.sheet.base.util.SheetAttributes;
import com.boco.eoms.sheet.base.webapp.action.NewSheetAction;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

/**
 * Created with IntelliJ IDEA. User: informix Date: 13-9-30 Time: 上午11:31 To
 * change this template use File | Settings | File Templates.
 */
public class AndroidWorkOrderAction extends NewSheetAction {
	private static Logger logger = Logger
			.getLogger(AndroidWorkOrderAction.class);
	private IPnrAndroidWorkOderPhotoFileService androidMgr;
    private IPnrTransferOfficeService pnrTransferOfficeService;
    private IProcessTaskService processTaskService = (IProcessTaskService) ApplicationContextHolder.getInstance().getBean("processTaskService");
    
	private final DateFormat sFormat = new SimpleDateFormat(
	"yyyy-MM-dd HH:mm:ss");
		
	/**
	 * 回退任务
	 * 
	 * @return
	 */
	public void rollback(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		
		//stepState 0:传输局派发 ; 1：外包派发
		String stepState = request.getParameter("stepState");
		String hjflag="";
		if(stepState.equals("0")){
			hjflag="传输局回退";
		}else if(stepState.equals("1")){
			hjflag="外包回退";			
		}else{
			hjflag="回退（未知）";
		}
		
		
		String rejectMan = "";
		String processInstanceId = request.getParameter("processInstanceId");
		String taskId = request.getParameter("taskId");
		
		pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId",processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
				.search(search);
		
		FormService formService = (FormService) getBean("formService");
		
		Map<String, String> map = new HashMap<String, String>();
		if(pnrTicketList != null && pnrTicketList.size()>0){
		
			if(stepState.equals("0")){
				rejectMan =  pnrTicketList.get(0).getOneInitiator();
				
				map.put("transferState", "rollback");
				map.put("initiator", rejectMan);
				
			}else if(stepState.equals("1")){
				rejectMan =  pnrTicketList.get(0).getSecondInitiator();

				map.put("epibolyState", "rollback");
				map.put("initiator", rejectMan);
			}
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		logger.info("当前的操作人："+userId+";当前的流程:抢修工单;当前的操作环节:"+hjflag+";当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--start");
		formService.submitTaskFormData(taskId, map);
//		logger.info("当前的操作人："+userId+";当前的流程:抢修工单;当前的操作环节:"+hjflag+";当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--end");
	}

	/**
	 * 自维任务审核保存
	 */
	public void goTransferCheck(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		
		// 工单ID
		String themeId = "";
		// 工单号
		String processInstanceId = request.getParameter("processInstanceId");
		// 工单主题
		String theme = "";

		// 审核时间
		Date createTime = new Date();

		// 审核描述
		String mainRemark = StaticMethod.nullObject2String(request
				.getParameter("mainRemark"));

		// 审核意见
		String auditState = StaticMethod.nullObject2String(request
				.getParameter("auditState"));
		// 审核人
		String auditor = sessionform.getUserid();

		String taskId = request.getParameter("taskId");
		
		String backTaskAssignee ="";

		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId",processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
				.search(search);
		PnrTransferOffice pnrTicket =null;
		String nextPerson="";
		Boolean isOver = true;
		if (pnrTicketList != null) {
			pnrTicket = pnrTicketList.get(0);
			if (auditState.equals(PnrTransferOfficeAction.AUDIT_STATE_DISMISS)) {
					pnrTicket.setLastReplyTime(null);
					//pnrTransferOfficeService.save(pnrTicket);
					backTaskAssignee = pnrTicket.getSecondInitiator();
			} else if (auditState.equals(PnrTransferOfficeAction.AUDIT_STATE_THROUGH)) {
					pnrTicket.setArchivingTime(new Date());
					pnrTicket.setState(PnrTransferOfficeAction.ARCHIVE_STATE);
					//pnrTransferOfficeService.save(pnrTicket);
			}
			FormService formService = (FormService) getBean("formService");
			Map<String, String> map = new HashMap<String, String>();
			   map.put("auditState", auditState);
			
			if(auditState.equals(PnrTransferOfficeAction.AUDIT_STATE_DISMISS)){
				map.put("taskAssignee", backTaskAssignee);
				nextPerson=backTaskAssignee;
				isOver=false;
			}

			String hjflag="";
			if(auditState.equals(PnrTransferOfficeAction.AUDIT_STATE_THROUGH)){
				hjflag="审核（手机端）";
			}else if(auditState.equals(PnrTransferOfficeAction.AUDIT_STATE_DISMISS)){
				hjflag="审核（手机端）回退";
			}else{
				hjflag="审核（手机端）未知";
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			logger.info("当前的操作人："+auditor+";当前的流程:抢修工单;当前的操作环节:"+hjflag+";当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--start");
			formService.submitTaskFormData(taskId, map);
//			logger.info("当前的操作人："+auditor+";当前的流程:抢修工单;当前的操作环节:"+hjflag+";当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--end");
			
			processTaskService.setTvalueOfTask(processInstanceId, nextPerson, pnrTicket, PnrTransferOffice.class, null, null,isOver);
			pnrTransferOfficeService.save(pnrTicket);
			
			// 保存审核意见
			IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
			PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();

			entity.setThemeId(themeId);
			entity.setTheme(theme);
			entity.setCheckTime(createTime);
			entity.setOpinion(mainRemark);
			entity.setState(auditState);
			entity.setAuditor(auditor);
			transferHandleService.save(entity);
			// 保存审核意见--end
			
//			 需要更改
			String returnJson ="[{\"sign\":\"examine\",\"status\":\"true\"}]";
			
			MobileCommonUtils.responseWrite(response, returnJson,"UTF-8");	
		}
	}
	
	/**
	 * 任务审核保存---传输局质检
	 * 
	 */
	public void secondAudit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");

		// 发短信
		String processInstanceId = "", deadlineTime = "", contact = "";
		boolean isSend = true;

		String userId = sessionform.getUserid();
		// 工单ID
		String themeId = "";
		// 工单号
		processInstanceId = request.getParameter("processInstanceId");
		// 工单主题
		String theme = "";

		// 审核时间
		Date createTime = new Date();

		// 审核描述
		String mainRemark = StaticMethod.nullObject2String(request
				.getParameter("mainRemark"));

		// 审核意见
		String auditState = StaticMethod.nullObject2String(request
				.getParameter("transferAudit"));
		// 审核人
		String auditor = "";

		String taskId = request.getParameter("taskId");
	
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
		.search(search);
		PnrTransferOffice pnrTicket = null;
		if (pnrTicketList != null && pnrTicketList.size()>0) {
			// 设置回复时间问题
			pnrTicket = pnrTicketList.get(0);
			theme = pnrTicket.getTheme();
			themeId = pnrTicket.getId();
			auditor = pnrTicket.getOneInitiator();
			
			if (auditState.equals("rollback")) {
				if (pnrTicket != null) {
					
					pnrTicket.setLastReplyTime(null);
					//pnrTransferOfficeService.save(pnrTicket);
					
					auditor = pnrTicket.getInitiator();// 回退到二次处理人
				}
				isSend = false;
				
			} else if (auditState.equals("handle")) {
				if (pnrTicket != null) {
					deadlineTime = pnrTicket.getEndTime() == null ? "" : sFormat
							.format(pnrTicket.getEndTime());
					contact = pnrTicket.getConnectPerson();
				}
			}

			IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
			PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();

			entity.setThemeId(themeId);
			entity.setTheme(theme);
			entity.setCheckTime(createTime);
			entity.setOpinion(mainRemark);
			entity.setState(auditState);
			entity.setAuditor(userId);
			transferHandleService.save(entity);
			

			FormService formService = (FormService) getBean("formService");
			Map<String, String> map = new HashMap<String, String>();
				map.put("transferAudit", auditState);
				map.put("teskAssignee", auditor);
			
			if (auditState.equals("rollback")) {// 回退时的说法
				map.put("auditor", auditor);
			}
			
//			System.out.println("-------传输局质检（手机端）auditState的值是："+auditState);
			String hjflag="";
			if(auditState.equals("handle")){
				hjflag="传输局质检（手机端）";
			}else if(auditState.equals("rollback")){
				hjflag="传输局质检（手机端）回退";
			}else{
				hjflag="传输局质检（手机端）未知";
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			logger.info("当前的操作人："+userId+";当前的流程:抢修工单;当前的操作环节:"+hjflag+";当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--start");
			formService.submitTaskFormData(taskId, map);
//			logger.info("当前的操作人："+userId+";当前的流程:抢修工单;当前的操作环节:"+hjflag+";当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--end");
			
			processTaskService.setTvalueOfTask(processInstanceId, auditor, pnrTicket, PnrTransferOffice.class, null, null,false);
			pnrTransferOfficeService.save(pnrTicket);
			
			// 发短信
			if (isSend) {

				String msg = PnrTransferOfficeAction.TASK_NO_STR + processInstanceId + ";" + PnrTransferOfficeAction.TASK_TITLE_STR
						+ theme + ";";
				msg += PnrTransferOfficeAction.TASK_DEADLINE_STR + deadlineTime + ";" + PnrTransferOfficeAction.TASK_CONTACT_STR
						+ contact + ";已处理请审核";
				CommonUtils.sendMsg(msg, auditor);
			}
			
//			 需要更改
			String returnJson ="[{\"sign\":\"examine\",\"status\":\"true\"}]";
			
			MobileCommonUtils.responseWrite(response, returnJson,"UTF-8");	
		}
	}
	
	/**
	 * 任务审核保存---外包质检
	 * 
	 */
	public void epibolyAudit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		
		// 发短信
		String processInstanceId = "", deadlineTime = "", contact = "";
		boolean isSend = true;
		
		String userId = sessionform.getUserid();
		// 工单ID
		String themeId = "";
		// 工单号
		processInstanceId = request.getParameter("processInstanceId");
		// 工单主题
		String theme = "";
		
		// 审核时间
		Date createTime = new Date();
		
		// 审核描述
		String mainRemark = StaticMethod.nullObject2String(request
				.getParameter("mainRemark"));
		
		// 审核意见
		String auditState = StaticMethod.nullObject2String(request
				.getParameter("epibolyState"));
		// 审核人
		String auditor = "";
		
		String taskId = request.getParameter("taskId");
		
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId",processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
		.search(search);
		PnrTransferOffice pnrTicket = null;
		
		if (pnrTicketList != null && pnrTicketList.size()>0) {
			// 设置回复时间问题
			pnrTicket = pnrTicketList.get(0);
			themeId = pnrTicket.getId();
			theme = pnrTicket.getTheme();
			auditor = pnrTicket.getSecondInitiator();
//			System.out.println("1---------auditor="+auditor);
			if (auditState.equals("rollback")) {
				if (pnrTicket != null) {
					
					pnrTicket.setLastReplyTime(null);
					//pnrTransferOfficeService.save(pnrTicket);
					
					auditor = pnrTicket.getTaskAssignee();
//					System.out.println("2---------auditor="+auditor);
					
				}
				isSend = false;
				
			} else if (auditState.equals("handle")) {
				if (pnrTicket != null) {
					deadlineTime = pnrTicket.getEndTime() == null ? "" : sFormat
							.format(pnrTicket.getEndTime());
					contact = pnrTicket.getConnectPerson();
				}
			}
			
			
			IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
			PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
			
			entity.setThemeId(themeId);
			entity.setTheme(theme);
			entity.setCheckTime(createTime);
			entity.setOpinion(mainRemark);
			entity.setState(auditState);
			entity.setAuditor(userId);
			transferHandleService.save(entity);
			
			
			FormService formService = (FormService) getBean("formService");
			Map<String, String> map = new HashMap<String, String>();
				map.put("epibolyState", auditState);
				map.put("auditor", auditor);
//				System.out.println("3---------auditor="+auditor);
			
			if (auditState.equals("rollback")) {// 回退时的说法
				map.put("worker", auditor);
//				System.out.println("4---------auditor="+auditor);
				map.remove("auditor");
			}
			
			
//			System.out.println("-------外包质检（手机端）auditState的值是："+auditState);
			String hjflag="";
			if(auditState.equals("handle")){
				hjflag="外包质检（手机端）";
			}else if(auditState.equals("rollback")){
				hjflag="外包质检（手机端）回退";
			}else{
				hjflag="外包质检（手机端）未知";
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			logger.info("当前的操作人："+userId+";当前的流程:抢修工单;当前的操作环节:"+hjflag+";当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--start");
			formService.submitTaskFormData(taskId, map);
//			logger.info("当前的操作人："+userId+";当前的流程:抢修工单;当前的操作环节:"+hjflag+";当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--end");
			
			processTaskService.setTvalueOfTask(processInstanceId, auditor, pnrTicket, PnrTransferOffice.class, null, null,false);
			pnrTransferOfficeService.save(pnrTicket);
			
			// 发短信
			if (isSend) {
				
				String msg = PnrTransferOfficeAction.TASK_NO_STR + processInstanceId + ";" + PnrTransferOfficeAction.TASK_TITLE_STR
				+ theme + ";";
				msg += PnrTransferOfficeAction.TASK_DEADLINE_STR + deadlineTime + ";" + PnrTransferOfficeAction.TASK_CONTACT_STR
				+ contact + ";已处理请审核";
				
				Thread thread = new Thread(new SendMsgThread(msg,auditor));
				thread.start();
			}
			
//			 需要更改
			String returnJson ="[{\"sign\":\"examine\",\"status\":\"true\"}]";
			
			MobileCommonUtils.responseWrite(response, returnJson,"UTF-8");
		}
	}
	
	/**
	 * mainEpiboly 外包派发
	 */
	public void  mainEpiboly(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		// 派发人
		
		String initiator = sessionForm.getUserid();
		// 派单时间
		Date createTime = new Date();
		// 接收人
		String taskAssignee = request.getParameter("worker");
		//操作-驳回、转派
		String epiloyState = request.getParameter("epiloyState");
		// 接收人字符串
		String taskAssigneeJSON = "[{id:'"+initiator+"',nodeType:'user',categoryId:'taskAssignee'}]";
		
		// 工单ID
		String themeId = request.getParameter("themeId");
		// taskId
		String taskId = request.getParameter("taskId");
		
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		
		PnrTransferOffice entity = null;
		String backTaskAssignee = "";
		if (themeId != null && themeId.length() > 0) {
			entity = pnrTransferOfficeService.find(themeId);
			backTaskAssignee = entity.getInitiator();
			
			if(entity != null){
				if(epiloyState.equals("handle")){
					//存入第三派发时间
					entity.setThirdSendTime(sFormat.parse(sFormat.format(createTime)));
					// 接收人
					entity.setTaskAssignee(taskAssignee);
					// 处理人
					entity.setTaskAssigneeJSON(taskAssigneeJSON);
					// 派发人更改
					entity.setInitiator(initiator);
					
					//pnrTransferOfficeService.save(entity);
				}
				
				// 流程开始
				FormService formService = (FormService) getBean("formService");
				String nextPerson="";
				Map<String, String> map = new HashMap<String, String>();
					map.put("epiloyState", epiloyState);			
					if(epiloyState.equals("handle")){
							map.put("worker", taskAssignee);
							nextPerson=taskAssignee;
							entity.setProjectStartPoint(taskAssignee); //暂时保存施工队环节的处理人
					}else{
						    map.put("taskAssignee", backTaskAssignee);
						    nextPerson=backTaskAssignee;
						    taskAssignee = backTaskAssignee;
					}
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				logger.info("当前的操作人："+initiator+";当前的流程:抢修工单;当前的操作环节:外包公司（手机端）;当前的工单号:"+entity.getProcessInstanceId()+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--start");
				formService.submitTaskFormData(taskId, map);
//				logger.info("当前的操作人："+initiator+";当前的流程:抢修工单;当前的操作环节:外包公司（手机端）;当前的工单号:"+entity.getProcessInstanceId()+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--end");
				
				// 流程结束
				processTaskService.setTvalueOfTask(entity.getProcessInstanceId(), nextPerson, entity, PnrTransferOffice.class, null, null,false);
				pnrTransferOfficeService.save(entity);
				
				// 发短信
				String deadlineTime = entity.getEndTime() == null ? "" : sFormat
						.format(entity.getEndTime());
				
				String msg = PnrTransferOfficeAction.TASK_NO_STR + entity.getProcessInstanceId() + ";"
				+ PnrTransferOfficeAction.TASK_TITLE_STR + entity.getTheme() + ";";
				msg += PnrTransferOfficeAction.TASK_DEADLINE_STR + deadlineTime + ";" + PnrTransferOfficeAction.TASK_CONTACT_STR
				+ entity.getConnectPerson();
				
				Thread thread = new Thread(new SendMsgThread(msg,taskAssignee));
				thread.start();
				
		    	String returnJson ="[{\"sign\":\"mainSecond\",\"status\":\"true\"}]";
				
				MobileCommonUtils.responseWrite(response, returnJson,"UTF-8");	
			}
		}
	}
	/**
	 * mainSecond -传输局派发
	 */
	public void mainSecond(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String deptId = sessionForm.getDeptid();
		// 派发人
		String initiator = sessionForm.getUserid();
		// 派单时间
		Date createTime = new Date();
		// 接收人
		String taskAssignee = request.getParameter("auditor");
		//处理状态
		String transferState = request.getParameter("transferState");
		
		// 接收人字符串
		String taskAssigneeJSON = "[{id:'"+initiator+"',nodeType:'user',categoryId:'taskAssignee'}]";

		// 工单ID
		String themeId = request.getParameter("themeId");
		// taskId
		String taskId = request.getParameter("taskId");
		
				
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		
		PnrTransferOffice entity =null;
		String backInitiator = "";
		if (themeId != null && themeId.length() > 0) {
			entity = pnrTransferOfficeService.find(themeId);
			if(entity != null){
				backInitiator = entity.getInitiator();
				if(!transferState.equals("rollback")){		
					entity.setSecondSendTime(sFormat.parse(sFormat.format(createTime)));
					// 接收人
					entity.setTaskAssignee(taskAssignee);
					// 处理人
					entity.setTaskAssigneeJSON(taskAssigneeJSON);
					// 派发人更改
					entity.setInitiator(initiator);
					//存入第二派单人
					entity.setSecondInitiator(initiator);
				}
				// 流程开始
				FormService formService = (FormService) getBean("formService");
				String nextPerson="";
				Map<String, String> map = new HashMap<String, String>();
						map.put("transferState", transferState);
						
						if(transferState.equals("rollback")){				 
							map.put("initiator", backInitiator);
							nextPerson=backInitiator;
						}else{
							map.put("auditor", taskAssignee);
							nextPerson=taskAssignee;
							taskAssignee = backInitiator;
						}
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
	//			logger.info("当前的操作人："+initiator+";当前的流程:抢修工单;当前的操作环节:传输局（手机端）;当前的工单号:"+entity.getProcessInstanceId()+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--start");		
				formService.submitTaskFormData(taskId, map);
	//			logger.info("当前的操作人："+initiator+";当前的流程:抢修工单;当前的操作环节:传输局（手机端）;当前的工单号:"+entity.getProcessInstanceId()+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--end");
				
				// 流程结束
				processTaskService.setTvalueOfTask(entity.getProcessInstanceId(), nextPerson, entity, PnrTransferOffice.class, null, null,false);
				pnrTransferOfficeService.save(entity);
				// 发短信
	
				String deadlineTime = entity.getEndTime() == null ? "" : sFormat
						.format(entity.getEndTime());
	
				String msg = PnrTransferOfficeAction.TASK_NO_STR + entity.getProcessInstanceId() + ";"
						+ PnrTransferOfficeAction.TASK_TITLE_STR + entity.getTheme() + ";";
				msg += PnrTransferOfficeAction.TASK_DEADLINE_STR + deadlineTime + ";" + PnrTransferOfficeAction.TASK_CONTACT_STR
						+ entity.getConnectPerson();
				Thread thread = new Thread(new SendMsgThread(msg,taskAssignee));
				thread.start();
				
				String returnJson ="[{\"sign\":\"mainSecond\",\"status\":\"true\"}]";
				
				MobileCommonUtils.responseWrite(response, returnJson,"UTF-8");
			}
		}
	}
	/**
	 * 1、传输局工单-传输局转派、外包转派，提供手机端显示的组织及人员;
	 * 2、业务逻辑：新建派发-传输局 0-->1 ;传输局转派 1-->2 ;外包转派 2--->3
	 * 3、目前人员权限，限制在地市（即可以看见所在地市的组织）
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void getDepartmentAndStaff(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		TawSystemDeptBo deptbo = TawSystemDeptBo.getInstance();
		TawSystemUserRoleBo userrolebo = TawSystemUserRoleBo.getInstance();
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		
		String deptId = sessionForm.getDeptid();
		String country = deptId;
		
		if (country.length() > 5) {
			country = country.substring(0, 5);
		}
		
		String stepState = StaticMethod.null2String(request.getParameter("stepState"), "");
		
//		stepState="0";
		
		//传输局转派 level为2；外包转派 level为3 ;level为5时查不到人。
		String level = "";
		if (stepState.equals("0")){
			level = "2";
		}else if (stepState.equals("1")){
			level = "3";
		}else {
			level = "5";
		}		
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		List<TawSystemUser> cityUserList = new ArrayList();
		List<TawSystemUser> classUserList = new ArrayList();
		List<TawSystemUser> groupUserList = new ArrayList();
		
		List<TawSystemDept> classDeptList = new ArrayList();
		List<TawSystemDept> groupDeptList = new ArrayList();
		//班
		Map<String,Map<String,String[]>> one = new HashMap<String, Map<String,String[]>>();
		//组
		Map<String,Map<String,String[]>> two = new HashMap<String, Map<String,String[]>>();
		//人
		Map<String,Map<String,String[]>> three = new HashMap<String, Map<String,String[]>>();

				
		if(level.equals("2")){
			
			classDeptList = (ArrayList) deptbo.getNextLevecDeptsTask(country, "0",country);
			
			cityUserList = (ArrayList) userrolebo.getUserBydeptidsTask(country,country,level);
			//市-人员--start
		    int cityUserListSize = cityUserList.size();
		    Map<String,String[]> tempthree = new HashMap<String, String[]>();
		    String[] cityUserNames = new String[cityUserListSize];
		    String[] cityUserIds = new String[cityUserListSize];
		  
		    for(int i=0; i < cityUserListSize; i++){		    	
		    	TawSystemUser cityUser = cityUserList.get(i);		    	
		    	
		    	cityUserNames[i] = cityUser==null?"":cityUser.getUsername();
		    	cityUserIds[i] = cityUser==null?"":cityUser.getUserid();
		    }
		    
			tempthree.put("value", cityUserNames);
			tempthree.put("id", cityUserIds);
			
			three.put("group", tempthree);
			//市-人员--end
			
			int classDeptListSize = classDeptList.size();
			
			Map<String,String[]> tempone = new HashMap<String, String[]>();
			String[] classValueNames = new String[classDeptListSize];
			String[] classValueIds = new String[classDeptListSize];
			//班
			for(int i=0 ;i < classDeptListSize ;i++){
				TawSystemDept dept = classDeptList.get(i);
				String classDeptName = dept==null?"":dept.getDeptName();
				String classDeptId = dept==null?"":dept.getDeptId();
				
				classValueNames[i] = classDeptName;
				classValueIds[i] = classDeptId;
				
					//班--每个班下的人--start
				classUserList = (ArrayList) userrolebo.getUserBydeptidsTask(classDeptId,country,level);
					int classUserSize = classUserList.size();
					Map<String,String[]> tempthree2 = new HashMap<String, String[]>();
					String[] classUserNames = new String[classUserSize];
					String[] classUserIds = new String[classUserSize];
					
					for(int j=0; j < classUserSize; j++){
						TawSystemUser tuser = classUserList.get(j);
						String classUserName = tuser==null?"":tuser.getUsername();
						String classUserId = tuser==null?"":tuser.getUserid();
						
						classUserNames[j] = classUserName;
						classUserIds[j] = classUserId;
					}
					
					tempthree2.put("value", classUserNames);
					tempthree2.put("id", classUserIds);	
					
					three.put(classDeptId, tempthree2);
					//班--每个班下的人--end
					
					//组-start
					
					//组---每个班下的组织;
					groupDeptList = (ArrayList) deptbo.getNextLevecDeptsTask(classDeptId, "0",country);
					int groupDeptListSize = groupDeptList.size();
					String[] groupDeptNames = new String[groupDeptListSize];
					String[] groupDeptIds = new String[groupDeptListSize];
					
					Map<String,String[]> temptwo = new HashMap<String, String[]>();

					for(int j=0 ;j < groupDeptListSize; j++){
						TawSystemDept groupDept = groupDeptList.get(j);
						String groupDeptName = groupDept==null?"":groupDept.getDeptName(); 
						String groupDeptId = groupDept==null?"":groupDept.getDeptId();
						
						groupDeptNames[j] = groupDeptName;
						groupDeptIds[j] = groupDeptId;
						
							//组-每个组下人--start
						groupUserList = (ArrayList) userrolebo.getUserBydeptidsTask(groupDeptId,country,level);
							int groupUserSize = groupUserList.size();
							Map<String,String[]> tempthree3 = new HashMap<String, String[]>();
							String[] groupUserNames = new String[groupUserSize];
							String[] groupUserIds = new String[groupUserSize];
							
							for(int k=0; k < groupUserSize; k++){
								TawSystemUser tuser = groupUserList.get(k);
								String groupUserName = tuser==null?"":tuser.getUsername();
								String groupUserId = tuser==null?"":tuser.getUserid();
								
								groupUserNames[j] = groupUserName;
								groupUserIds[j] = groupUserId;
							}
							
							tempthree3.put("value", groupUserNames);
							tempthree3.put("id", groupUserIds);	
							
							three.put(groupDeptId, tempthree3);
						    //组-每个组下人--end
					}
			
					temptwo.put("value", groupDeptNames);
					temptwo.put("id", groupDeptIds);
					
					two.put(classDeptId, temptwo);	
					
					//组-end
			}
			
			tempone.put("value", classValueNames);
			tempone.put("id", classValueIds);
			
			one.put("city", tempone);
			
			
		}else if(level.equals("3")){
			classDeptList = (ArrayList) deptbo.getNextLevecDepts(country, "0");
			
			cityUserList = (ArrayList) userrolebo.getUserBydeptids(country,level);
			//市-人员--start
		    int cityUserListSize = cityUserList.size();
		    Map<String,String[]> tempthree = new HashMap<String, String[]>();
		    String[] cityUserNames = new String[cityUserListSize];
		    String[] cityUserIds = new String[cityUserListSize];
		    
		    for(int i=0; i < cityUserListSize; i++){		    	
		    	TawSystemUser cityUser = cityUserList.get(i);		    	
		    	
		    	cityUserNames[i] = cityUser==null?"":cityUser.getUsername();
		    	cityUserIds[i] = cityUser==null?"":cityUser.getUserid();
		    }
		    
			tempthree.put("value", cityUserNames);
			tempthree.put("id", cityUserIds);
			
			three.put("group", tempthree);
			//市-人员--end
			
			int classDeptListSize = classDeptList.size();
			
			Map<String,String[]> tempone = new HashMap<String, String[]>();
			String[] classValueNames = new String[classDeptListSize];
			String[] classValueIds = new String[classDeptListSize];
			//班
			for(int i=0 ;i < classDeptListSize ;i++){
				TawSystemDept dept = classDeptList.get(i);
				String classDeptName = dept==null?"":dept.getDeptName();
				String classDeptId = dept==null?"":dept.getDeptId();
				
				classValueNames[i] = classDeptName;
				classValueIds[i] = classDeptId;
				
					//班--每个班下的人--start
				classUserList = (ArrayList) userrolebo.getUserBydeptids(classDeptId,level);
					int classUserSize = classUserList.size();
					Map<String,String[]> tempthree2 = new HashMap<String, String[]>();
					String[] classUserNames = new String[classUserSize];
					String[] classUserIds = new String[classUserSize];
					
					for(int j=0; j < classUserSize; j++){
						TawSystemUser tuser = classUserList.get(j);
						String classUserName = tuser==null?"":tuser.getUsername();
						String classUserId = tuser==null?"":tuser.getUserid();
						
						classUserNames[j] = classUserName;
						classUserIds[j] = classUserId;
					}
					
					tempthree2.put("value", classUserNames);
					tempthree2.put("id", classUserIds);	
					
					three.put(classDeptId, tempthree2);
					//班--每个班下的人--end
					
					//组-start
					
					//组---每个班下的组织;
					groupDeptList = (ArrayList) deptbo.getNextLevecDepts(classDeptId, "0");
					int groupDeptListSize = groupDeptList.size();
					String[] groupDeptNames = new String[groupDeptListSize];
					String[] groupDeptIds = new String[groupDeptListSize];
					
					Map<String,String[]> temptwo = new HashMap<String, String[]>();

					for(int j=0 ;j < groupDeptListSize; j++){
						TawSystemDept groupDept = groupDeptList.get(j);
						String groupDeptName = groupDept==null?"":groupDept.getDeptName(); 
						String groupDeptId = groupDept==null?"":groupDept.getDeptId();
						
						groupDeptNames[j] = groupDeptName;
						groupDeptIds[j] = groupDeptId;
						
							//组-每个组下人--start
						groupUserList = (ArrayList) userrolebo.getUserBydeptids(groupDeptId,level);
							int groupUserSize = groupUserList.size();
							Map<String,String[]> tempthree3 = new HashMap<String, String[]>();
							String[] groupUserNames = new String[groupUserSize];
							String[] groupUserIds = new String[groupUserSize];
							
							for(int k=0; k < groupUserSize; k++){
								TawSystemUser tuser = groupUserList.get(k);
								String groupUserName = tuser==null?"":tuser.getUsername();
								String groupUserId = tuser==null?"":tuser.getUserid();
								
								groupUserNames[j] = groupUserName;
								groupUserIds[j] = groupUserId;
							}
							
							tempthree3.put("value", groupUserNames);
							tempthree3.put("id", groupUserIds);	
							
							three.put(groupDeptId, tempthree3);
						    //组-每个组下人--end
					}
			
					temptwo.put("value", groupDeptNames);
					temptwo.put("id", groupDeptIds);
					
					two.put(classDeptId, temptwo);	
					
					//组-end
			}
			
			tempone.put("value", classValueNames);
			tempone.put("id", classValueIds);
			
			one.put("city", tempone);
		}
		

		Map<String ,Map<String,Map<String,String[]>>> mapData = new HashMap<String, Map<String,Map<String,String[]>>>();
		mapData.put("classData", one);
		mapData.put("groupData", two);
		mapData.put("peopleData", three);

		returnMap.put("sign","circle");
		returnMap.put("data",mapData);
		
		JSONArray returnJson = JSONArray.fromObject(returnMap);
		MobileCommonUtils.responseWrite(response, returnJson.toString(),"UTF-8");	
		
//		JSONArray returnJsonOne = JSONArray.fromObject(one);
//		MobileCommonUtils.responseWrite(response, "returnJsonOne-班："+returnJsonOne.toString(),"UTF-8");	
		
//		JSONArray returnJsonTwo = JSONArray.fromObject(two);
//	    MobileCommonUtils.responseWrite(response, "returnJsonTwo-组："+returnJsonTwo.toString(),"UTF-8");	
		
//		JSONArray returnJsonThree = JSONArray.fromObject(three);
//		MobileCommonUtils.responseWrite(response, "returnJsonThree-人："+returnJsonThree.toString(),"UTF-8");	
		
	}
	/**
	 * 手机工单主页面查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCheckInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		
		String userId = sessionform.getUserid();
		AndroidQuery androidQuery = new AndroidQuery();
		androidQuery.setUserId(userId);

		TaskService taskService = (TaskService) getBean("taskService");
		// 任务工单代办总数
		long totalTask = taskService.createTaskQuery().taskAssignee(userId)
				.processDefinitionKey("taskWorkOrder").taskDefinitionKey(
						"processingReply").active().count();
		// 故障工单代办总数
	/*	long totalTrouble = taskService.createTaskQuery().taskDefinitionKey(
				"troubleShooting").taskDefinitionKey("twoHandle").taskAssignee(userId).processDefinitionKey(
				"troubleTicketProcess").active().count();*/
		long totalTrouble = 0L;
		totalTrouble=	taskService
		.createNativeTaskQuery()
		.sql("select t.proc_inst_id_ from ACT_RU_TASK t left join  act_re_procdef f on t.proc_def_id_=f.id_  left join pnr_act_transfer_office_main m on t.proc_inst_id_ = m.process_instance_id "
			+ " where  f.key_= #{processDefinitionKey} and m.STATE!=6 and (t.TASK_DEF_KEY_=#{t1} or t.TASK_DEF_KEY_=#{t2}) and t.ASSIGNEE_=#{userid}")
		.parameter("userid", userId)
		.parameter("processDefinitionKey", "troubleTicketProcess")
		.parameter("t1", "troubleShooting")
		.parameter("t2", "twoHandle").list().size();
		
	
		/*
		 * 传输局故障工单数量
		 * DENGYANMING@BOCO.COM.CN 2014.06.17 ADD
		 * */
		long totalTranFault = 0L;
		//抢修工单总数
		/*totalTranFault=	taskService
		.createNativeTaskQuery()
		.sql("select t.proc_inst_id_ from ACT_RU_TASK t left join  act_re_procdef f on t.proc_def_id_=f.id_ "
			+ " where t.ASSIGNEE_=#{userid} and f.key_= #{processDefinitionKey} and (t.TASK_DEF_KEY_=#{t1} or t.TASK_DEF_KEY_=#{t2} or t.TASK_DEF_KEY_=#{t3} or t.TASK_DEF_KEY_=#{t4} or t.TASK_DEF_KEY_=#{t5} or t.TASK_DEF_KEY_=#{t6})") 
		.parameter("userid", userId)
		.parameter("processDefinitionKey", "myTransferProcess")
		.parameter("t1", "transferHandle")
		.parameter("t2", "transferTask")
		.parameter("t3", "epibolyTask")
		.parameter("t4", "epibolyAudit")
		.parameter("t5", "transferAudit")
		.parameter("t6", "audit")
		.list().size();*/
		
		androidQuery.setWorkOrderType("transferOffice");	
		totalTranFault = pnrTransferOfficeService.getAndroidTaskCount(androidQuery);
		
		//公客故障工单总数
		long maleGuestFault = 0L;
		/*String processMaleGuestKey = "myMaleGuestProcess";
		maleGuestFault=	taskService
		.createNativeTaskQuery()
		.sql("select tk.proc_inst_id_  from  ACT_RU_TASK tk left join PNR_ACT_TRANSFER_OFFICE_MAIN mm on mm.process_instance_id = tk.proc_inst_id_ left join  act_re_procdef f on tk.proc_def_id_=f.id_"+
					" where  f.key_ = #{processMaleGuestKey} and mm.STATE!=8 and mm.do_flag is null and tk.ASSIGNEE_=#{userid} and tk.TASK_DEF_KEY_=#{t7}")
		.parameter("userid", userId)
		.parameter("processMaleGuestKey", processMaleGuestKey)
		.parameter("t7", "auditor")
		.list().size();*/
		
		androidQuery.setWorkOrderType("maleGuest");	
		maleGuestFault = pnrTransferOfficeService.getAndroidTaskCount(androidQuery);
				
		/*//新预检预修工单总数
		long interfaceFault = 0L;
		String processInterfaceKey = "newTwoTransferPrecheck";
		 interfaceFault=	taskService
		.createNativeTaskQuery()
		.sql("select tk.proc_inst_id_  from ACT_RU_TASK tk left join  act_re_procdef f on tk.proc_def_id_=f.id_ left join pnr_act_transfer_office_main m on tk.proc_inst_id_ = m.process_instance_id"+
					" where  f.key_ = #{processNewPrecheckKey} and m.state<>10 and tk.ASSIGNEE_=#{userid} and (tk.TASK_DEF_KEY_=#{t7} or tk.TASK_DEF_KEY_=#{t2})")
		.parameter("userid", userId)
		.parameter("processNewPrecheckKey", processInterfaceKey)
		.parameter("t7", "worker")
		.parameter("t2", "orderAudit")
		.list().size();
				
		//旧预检预修工单总数
		 long interfaceFault_old = 0L;
		String oldPrecheckKey = "newTransferPrecheck";
		interfaceFault_old=	taskService
		.createNativeTaskQuery()
		.sql("select tk.*  from ACT_RU_TASK tk left join  act_re_procdef f on tk.proc_def_id_=f.id_ left join pnr_act_transfer_office_main m on tk.proc_inst_id_ = m.process_instance_id"+
					" where  f.key_ = #{processOldPrecheckKey} and m.state<>10 and tk.ASSIGNEE_=#{userid} and tk.TASK_DEF_KEY_=#{t7}")
		.parameter("userid", userId)
		.parameter("processOldPrecheckKey", oldPrecheckKey)
		.parameter("t7", "worker")
		.list().size();

		//第三版预检预修工单总数
		long interfaceFault_three = 0L;
		String threePrecheckKey = "transferNewPrechechProcess";
		interfaceFault_three=	taskService
		.createNativeTaskQuery()
		.sql("select tk.*  from ACT_RU_TASK tk left join  act_re_procdef f on tk.proc_def_id_=f.id_ left join pnr_act_transfer_office_main m on tk.proc_inst_id_ = m.process_instance_id"+
					" where f.key_ = #{processNewPrecheckKey} and m.state<>10 and nvl(m.worker_scene_handle_flag,'-1')!= '1' and tk.ASSIGNEE_=#{userid} and (tk.TASK_DEF_KEY_=#{t7} or tk.TASK_DEF_KEY_=#{t2})")
		.parameter("userid", userId)
		.parameter("processNewPrecheckKey", threePrecheckKey)
		.parameter("t7", "worker")
		.parameter("t2", "orderAudit")
		.list().size();*/
		
		//新旧预检预修工单总数，并将新旧预检预修查询集合制空
//		interfaceFault=interfaceFault+interfaceFault_old+interfaceFault_three;
	    
		long interfaceFault = 0L;			
		androidQuery.setWorkOrderType("transferInterface");	
		interfaceFault = pnrTransferOfficeService.getAndroidTaskCount(androidQuery);
	
		//干线预检预修工单总数
	/*	long ArteryFault = 0L;
		String processArteryPrecheckKey = "transferArteryPrecheckProcess";
		ArteryFault=	taskService
		.createNativeTaskQuery()
		.sql("select tk.proc_inst_id_ from ACT_RU_TASK tk left join  act_re_procdef f on tk.proc_def_id_=f.id_ left join pnr_act_transfer_office_main m on tk.proc_inst_id_ = m.process_instance_id"+
					" where f.key_=#{processArteryPrecheckKey} and (tk.TASK_DEF_KEY_=#{t7} or tk.TASK_DEF_KEY_=#{t2}) and m.state<>10 and tk.ASSIGNEE_=#{userid}")
		.parameter("userid", userId)
		.parameter("processArteryPrecheckKey", processArteryPrecheckKey)
		.parameter("t7", "worker")
		.parameter("t2", "orderAudit")
		.list().size();*/
		
		long ArteryFault = 0L;
		androidQuery.setWorkOrderType("arteryPrecheck");	
		ArteryFault = pnrTransferOfficeService.getAndroidTaskCount(androidQuery);
		
	
		//大修改造工单总数----开始
		/*long overHaulFault = 0L;
		String overHaulNewProcessKey = "overHaulNewProcess";
		overHaulFault=taskService
		.createNativeTaskQuery()
		.sql("select tk.proc_inst_id_ from ACT_RU_TASK tk left join  act_re_procdef f on tk.proc_def_id_=f.id_  left join pnr_act_transfer_office_main m on tk.proc_inst_id_ = m.process_instance_id"+
					" where  f.key_ = #{processArteryPrecheckKey}  and m.state<>10 and tk.ASSIGNEE_=#{userid} and (tk.TASK_DEF_KEY_=#{t7} or tk.TASK_DEF_KEY_=#{t2})")
		.parameter("userid", userId)
		.parameter("processArteryPrecheckKey", overHaulNewProcessKey)
		.parameter("t7", "worker")
		.parameter("t2", "orderAudit")
		.list().size();*/
		
		
		long overHaulFault = 0L;
		androidQuery.setWorkOrderType("overHaul");	
		overHaulFault = pnrTransferOfficeService.getAndroidTaskCount(androidQuery);
		
		//大修改造工单总数----结束
		
		//机房拆除工单总数----开始
		long roomDemolitionNum = 0L;
/*		String roomDemolitionProcessKey = "roomDemolition";
		roomDemolitionNum=taskService
		.createNativeTaskQuery()
		.sql("select tk.proc_inst_id_ from ACT_RU_TASK tk left join  act_re_procdef f on tk.proc_def_id_=f.id_ "+
					" where f.key_ = #{processArteryPrecheckKey} and tk.ASSIGNEE_=#{userid} and (tk.TASK_DEF_KEY_=#{t7} or tk.TASK_DEF_KEY_=#{t2})")
		.parameter("userid", userId)
		.parameter("processArteryPrecheckKey", roomDemolitionProcessKey)
		.parameter("t7", "worker")
		.parameter("t2", "orderAudit")
		.list().size();*/
		
		androidQuery.setWorkOrderType("RoomDemolition");	
		roomDemolitionNum = pnrTransferOfficeService.getAndroidTaskCount(androidQuery);
	
		//机房拆除工单总数----结束
		
		//OLT-BBU机房优化申请工单总数---开始
		/*long oltBbuNum = 0L;
		String oltBbuProcesskKey = "oltBbuProcess";
		oltBbuNum=	taskService
		.createNativeTaskQuery()
		.sql("select tk.proc_inst_id_ from ACT_RU_TASK tk left join  act_re_procdef f on tk.proc_def_id_=f.id_ left join pnr_act_transfer_office_main m on tk.proc_inst_id_ = m.process_instance_id"+
					" where f.key_=#{oltBbuProcesskKey} and m.state <> 10 and nvl(m.worker_scene_handle_flag,'-1')!= '1' and nvl(m.worker_scene_order_audit_flag,'-1')!= '1' and (tk.TASK_DEF_KEY_=#{t7} or tk.TASK_DEF_KEY_=#{t2}) and tk.ASSIGNEE_=#{userid}")
		.parameter("userid", userId)
		.parameter("oltBbuProcesskKey", oltBbuProcesskKey)
		.parameter("t7", "worker")	//施工队施工回单
		.parameter("t2", "orderAudit")	//区县公司验收
		.list().size();*/
		
		long oltBbuNum = 0L;
		androidQuery.setWorkOrderType("oltBbu");	
		oltBbuNum = pnrTransferOfficeService.getAndroidTaskCount(androidQuery);
		
		//OLT-BBU机房优化申请工单总数---结束	
		
		
		// 所有工单数量
		long transferOfficeTotal = totalTranFault+maleGuestFault+interfaceFault+ArteryFault+overHaulFault+oltBbuNum;
		long total = totalTask + totalTrouble + transferOfficeTotal+roomDemolitionNum;
		HashMap map = new HashMap();
		HashMap map2 = new HashMap();
		HashMap map3 = new HashMap();
		HashMap map4 = new HashMap();
		HashMap map5 = new HashMap();
		HashMap map6 = new HashMap();
		HashMap map7 = new HashMap();
		HashMap map8 = new HashMap();//大修改造
		HashMap map9 = new HashMap();//机房拆除
		HashMap map10 = new HashMap();//oltbbu机房
		List taskCountList = new ArrayList();
		Map<String, String> returnMap = new HashMap<String, String>();
		map.put("sheetType", "taskDealLab");
		map.put("count", totalTask);
		taskCountList.add(map);
		map2.put("sheetType", "faultDealLab");
		map2.put("count", totalTrouble);
		taskCountList.add(map2);
		map3.put("sheetType", "tranFaultLab");
		map3.put("count", transferOfficeTotal);
		taskCountList.add(map3);
		map4.put("sheetType", "tranOfficeLab");
		map4.put("count", totalTranFault);
		taskCountList.add(map4);
		map5.put("sheetType", "tranInterfaceLab");
		map5.put("count", interfaceFault);
		taskCountList.add(map5);
		map6.put("sheetType", "maleGuestLab");
		map6.put("count", maleGuestFault);
		taskCountList.add(map6);
		map7.put("sheetType", "arteryLab");
		map7.put("count", ArteryFault);
		taskCountList.add(map7);
		map8.put("sheetType", "overHaulLab");//大修改造
		map8.put("count", overHaulFault);
		taskCountList.add(map8);		
		map9.put("sheetType", "RoomDemolitionLab");//机房拆除
		map9.put("count", roomDemolitionNum);
		taskCountList.add(map9);		
		map10.put("sheetType", "oltBbuLab");//oltbbu机房
		map10.put("count", oltBbuNum);
		taskCountList.add(map10);		
		returnMap.put("taskCountList", JSONArray.fromObject(taskCountList) + "");
		returnMap.put("total", total + "");
		JSONArray returnJson = JSONArray.fromObject(returnMap);
//		 System.out.println("AndroidWorkOrderAction--getCheckInfo--returnJson:"+returnJson.toString());
		MobileCommonUtils.responseWrite(response, returnJson.toString(),
				"UTF-8");
		return null;
	}

	/**
	 * 获取所有未办工单
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void getUndoAllListsNew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		TaskService taskService = (TaskService) getBean("taskService");
		// 任务工单
		List<Task> taskList = taskService.createTaskQuery()
				.taskAssignee(userId).processDefinitionKey("taskWorkOrder").taskDefinitionKey("processingReply")
				.active().list();
		List<TaskModel> rPnrTaskTicketList = new ArrayList<TaskModel>();
		IPnrTaskTicketService pnrTaskTicketService = (IPnrTaskTicketService) getBean("pnrTaskTicketService");
		for (Task task : taskList) {
			Search search = new Search();
			search.addFilterEqual("processInstanceId", task
					.getProcessInstanceId());
			SearchResult t = pnrTaskTicketService.searchAndCount(search);
			List<PnrTaskTicket> list = t.getResult();
			if (list != null && list.size() != 0) {
				PnrTaskTicket pnrTaskTicket = list.get(0);
				TaskModel taskTicketModel = new TaskModel();
				taskTicketModel.setTaskId(task.getId());
				taskTicketModel.setStatusName("task"); // linshi qufen
				taskTicketModel.setProcessInstanceId(pnrTaskTicket
						.getProcessInstanceId());
				taskTicketModel.setTheme(pnrTaskTicket.getTheme());
				rPnrTaskTicketList.add(taskTicketModel);
			}
		}

		// 故障工单
		List<Task> faultList =taskService
		.createNativeTaskQuery()
		.sql("select t.* from ACT_RU_TASK t left join  act_re_procdef f on t.proc_def_id_=f.id_ "
			+ " where t.ASSIGNEE_=#{userid} and f.key_ = #{processDefinitionKey} and (t.TASK_DEF_KEY_=#{t1} or t.TASK_DEF_KEY_=#{t2}) ")
		.parameter("userid", userId)
		.parameter("processDefinitionKey", "troubleTicketProcess")
		.parameter("t1", "troubleShooting")
		.parameter("t2", "twoHandle").list();
	
		/*List<Task> faultList = taskService.createTaskQuery().taskAssignee(
				userId).processDefinitionKey("troubleTicketProcess").taskDefinitionKey("troubleShooting").taskDefinitionKey("twoHandle").active()
				.list();*/
		List<TaskModel> rPnrTroubleTicketList = new ArrayList<TaskModel>();
		IPnrTroubleTicketService pnrTroubleTicketService = (IPnrTroubleTicketService) getBean("pnrTroubleTicketService");
		for (Task task : faultList) {
			Search search = new Search();
			search.addFilterEqual("processInstanceId", task
					.getProcessInstanceId());
			SearchResult t = pnrTroubleTicketService.searchAndCount(search);
			List<PnrTroubleTicket> list = t.getResult();
			if (list != null && list.size() != 0) {
				PnrTroubleTicket pnrTroubleTicket = list.get(0);
				TaskModel troubleTicketModel = new TaskModel();
				troubleTicketModel.setTaskId(task.getId());
				troubleTicketModel.setStatusName("faultDeal");
				troubleTicketModel.setProcessInstanceId(pnrTroubleTicket
						.getProcessInstanceId());
				troubleTicketModel.setTheme(pnrTroubleTicket.getTheme());
				if(task.getTaskDefinitionKey().equals("troubleShooting")&&pnrTroubleTicket.getState()==6){
					troubleTicketModel=null;
				}else{
					rPnrTroubleTicketList.add(troubleTicketModel);
				}
			}
		}

		/*
		 * 传输局故障工单
		 * DENGYANMING@BOCO.COM.CN 2014.06.17 ADD
		 * */
		List<Task> tranFaultList =taskService
		.createNativeTaskQuery()
		.sql("select t.* from ACT_RU_TASK t left join  act_re_procdef f on t.proc_def_id_=f.id_ "
			+ " where t.ASSIGNEE_=#{userid} and f.key_ = #{processDefinitionKey} and (t.TASK_DEF_KEY_=#{t1} or t.TASK_DEF_KEY_=#{t2} or t.TASK_DEF_KEY_=#{t3} or t.TASK_DEF_KEY_=#{t4} or t.TASK_DEF_KEY_=#{t5} or t.TASK_DEF_KEY_=#{t6} ) ")
		.parameter("userid", userId)
		.parameter("processDefinitionKey", "myTransferProcess")
		.parameter("t1", "transferHandle")
		.parameter("t2", "transferTask")
		.parameter("t3", "epibolyTask")
		.parameter("t4", "epibolyAudit")
		.parameter("t5", "transferAudit")
		.parameter("t6", "audit")
		.list();
		
		List<TaskModel> pnrTransferOfficeList = new ArrayList<TaskModel>();
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		for (Task task : tranFaultList) {
			Search search = new Search();
			search.addFilterEqual("processInstanceId", task
					.getProcessInstanceId());
			SearchResult t = pnrTransferOfficeService.searchAndCount(search);
			List<PnrTransferOffice> list = t.getResult();
			if (list != null && list.size() != 0) {
				PnrTransferOffice pnrTransferOffice = list.get(0);
				TaskModel transferOfficeModel = new TaskModel();
				transferOfficeModel.setTaskId(task.getId());
				transferOfficeModel.setStatusName("tranFault");
				transferOfficeModel.setProcessInstanceId(pnrTransferOffice
						.getProcessInstanceId());
				transferOfficeModel.setTheme(pnrTransferOffice.getTheme());
				pnrTransferOfficeList.add(transferOfficeModel);
			}
		}
		
		List<TaskModel> allList = new ArrayList<TaskModel>();
		allList = rPnrTaskTicketList;
		allList.addAll(rPnrTroubleTicketList);
		//ADD TRANFAULT
		allList.addAll(pnrTransferOfficeList);
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("datas", allList);
		returnMap.put("approver", "");
		JSONArray returnArray = JSONArray.fromObject(returnMap);
		String returnStr = returnArray.toString();
		// System.out.println("所有工单查询-----" + returnStr);
		MobileCommonUtils.responseWrite(response, returnStr, "UTF-8");
		return;
	}

	/**
	 * 相应工单类型查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void showListsendundo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("utf-8");
		
		//回单距离限制
		 String handleDistance = "";
	    //验收或抽查距离限制
		 String spotcheckDistance = "";
		
		//总页数
		long count = 0;
		// 获取每页显示条数
		Integer pageSize = ((SheetAttributes) ApplicationContextHolder
				.getInstance().getBean("SheetAttributes")).getPageSize();

		// 当前页数
		String pageIndexTemp = StaticMethod.nullObject2String((request
				.getParameter("pageIndex")));
		Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(pageIndexTemp) ? 0 : (Integer
				.parseInt(pageIndexTemp) - 1));
		String beanName = request.getParameter("beanName");
		String sheetType = request.getParameter("sheetType");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		
		//2015-7-1手机工单条件查询-start
		//接收查询页面传递的字段值
		//工单主题
		String androidWorkOrderTheme = request.getParameter("androidWorkOrderTheme");
		System.out.println("AndroidWorkOrderAction-showListsendundo--androidWorkOrderTheme="+androidWorkOrderTheme);
		//工单派单时间
		String androidSendTime = request.getParameter("androidSendTime");
		System.out.println("AndroidWorkOrderAction-showListsendundo--androidSendTime="+androidSendTime);
		//工单状态:处理、抽查
		String androidWorkOrderState = request.getParameter("androidWorkOrderState");
		System.out.println("AndroidWorkOrderAction-showListsendundo--androidWorkOrderState="+androidWorkOrderState);
		//地市
		String city = request.getParameter("city");
		System.out.println("AndroidWorkOrderAction-showListsendundo--地市city="+city);
		//区县
		String country = request.getParameter("country");
		System.out.println("AndroidWorkOrderAction-showListsendundo--区县country="+country);
			
		AndroidQuery androidQuery = new AndroidQuery();
		androidQuery.setTheme(androidWorkOrderTheme);
		androidQuery.setSendTime(androidSendTime);
		androidQuery.setWorkOrderState(androidWorkOrderState);
		androidQuery.setUserId(userId);
		androidQuery.setCity(city);//地市
		androidQuery.setCountry(country);//区县
		
		//2015-7-1手机工单条件查询-end
		TaskService taskService = (TaskService) getBean("taskService");
		String processDefinitionKey = "";
		List<Task> workList = new ArrayList<Task>();
		List<AndroidWorkOrderTask> workOrderList = new ArrayList<AndroidWorkOrderTask>();
		List<Task> workOldList = new ArrayList<Task>();
		List<Task> workNewList = new ArrayList<Task>();
		List<TaskModel> PnrList = new ArrayList<TaskModel>();
		if ("task".equals(sheetType)) {
			processDefinitionKey = "taskWorkOrder";
			count = taskService.createTaskQuery().taskAssignee(userId)
			.processDefinitionKey(processDefinitionKey)
			.taskDefinitionKey("processingReply").active().list().size();
			
			workList = taskService.createTaskQuery().taskAssignee(userId)
					.processDefinitionKey(processDefinitionKey)
					.taskDefinitionKey("processingReply").active().listPage(
							pageIndex * pageSize, pageSize);
			IPnrTaskTicketService pnrTaskTicketService = (IPnrTaskTicketService) getBean("pnrTaskTicketService");
			for (Task task : workList) {
				Search search = new Search();
				search.addFilterEqual("processInstanceId", task
						.getProcessInstanceId());
				search.addSortAsc("createTime");//按派单时间倒序
				SearchResult t = pnrTaskTicketService.searchAndCount(search);
				List<PnrTaskTicket> list = t.getResult();
				if (list != null && list.size() != 0) {
					PnrTaskTicket pnrTaskTicket = list.get(0);
					TaskModel taskTicketModel = new TaskModel();
					taskTicketModel.setTaskId(task.getId());
					taskTicketModel.setProcessInstanceId(pnrTaskTicket
							.getProcessInstanceId());
					taskTicketModel.setTheme(pnrTaskTicket.getTheme());

					PnrList.add(taskTicketModel);
				}
			}
		}else if ("faultDeal".equals(sheetType)) {
			processDefinitionKey = "troubleTicketProcess";
			count = taskService
			.createNativeTaskQuery()
			.sql("select t.* from ACT_RU_TASK t left join  act_re_procdef f on t.proc_def_id_=f.id_ "
					+ " where t.ASSIGNEE_=#{userid} and f.key_ = #{processDefinitionKey} and (t.TASK_DEF_KEY_=#{t1} or t.TASK_DEF_KEY_=#{t2}) ")
					.parameter("userid", userId)
					.parameter("processDefinitionKey", processDefinitionKey)
					.parameter("t1", "troubleShooting")
					.parameter("t2", "twoHandle").list().size();
			workList = taskService
					.createNativeTaskQuery()
					.sql("select t.* from ACT_RU_TASK t left join  act_re_procdef f on t.proc_def_id_=f.id_ "
						+ " where t.ASSIGNEE_=#{userid} and f.key_ = #{processDefinitionKey} and (t.TASK_DEF_KEY_=#{t1} or t.TASK_DEF_KEY_=#{t2}) ")
					.parameter("userid", userId)
					.parameter("processDefinitionKey", processDefinitionKey)
					.parameter("t1", "troubleShooting")
					.parameter("t2", "twoHandle")
					.listPage(pageIndex * pageSize,pageSize);
			
			IPnrTroubleTicketService pnrTroubleTicketService = (IPnrTroubleTicketService) getBean("pnrTroubleTicketService");
			for (Task task : workList) {
				Search search = new Search();
				search.addFilterEqual("processInstanceId", task
						.getProcessInstanceId());
				search.addSortAsc("sendTime");//按派单时间倒序
				SearchResult t = pnrTroubleTicketService.searchAndCount(search);
				List<PnrTroubleTicket> list = t.getResult();
				if (list != null && list.size() != 0) {
					PnrTroubleTicket pnrTroubleTicket = list.get(0);
					TaskModel troubleTicketModel = new TaskModel();
					troubleTicketModel.setTaskId(task.getId());
					troubleTicketModel.setProcessInstanceId(pnrTroubleTicket
							.getProcessInstanceId());
					troubleTicketModel.setTheme(pnrTroubleTicket.getTheme());
					if(task.getTaskDefinitionKey().equals("troubleShooting")&&pnrTroubleTicket.getState()!=null && pnrTroubleTicket.getState()==6){
						troubleTicketModel=null;
					}else{
						PnrList.add(troubleTicketModel);
					}
					
				}
			}
			
			//--以下工单：抢修工单、本地网预检预修工单、干线预检预修工单、大修改造工单、公客故障工单、机房拆除工单增加了条件查询
			/*
			 * 传输局故障工单查询--抢修工单
			 * DENGYANMING@BOCO.COM.CN 2014.06.17 ADD
			 * */
		}else if("transferOffice".equals(sheetType)){
			
			androidQuery.setProcessDefinitionKey("myTransferProcess");
			androidQuery.setWorkOrderType(sheetType);
			
			processDefinitionKey = "myTransferProcess";
			
			//jdbc查询待处理工单-start 2015-12-22抢修
		    count = pnrTransferOfficeService.getAndroidTaskCount(androidQuery);
		    workOrderList = pnrTransferOfficeService.getAndroidTaskList(androidQuery,pageIndex,pageSize);
		    //jdbc查询待处理工单-end
						
			 //流程引擎查询待处理工单-start  2015-12-22 增加
			/*count=	taskService
			.createNativeTaskQuery()
			.sql("select t.proc_inst_id_ from ACT_RU_TASK t left join  act_re_procdef f on t.proc_def_id_=f.id_ "
				+ " where t.ASSIGNEE_=#{userid} and f.key_= #{processDefinitionKey} and (t.TASK_DEF_KEY_=#{t1} or t.TASK_DEF_KEY_=#{t2} or t.TASK_DEF_KEY_=#{t3} or t.TASK_DEF_KEY_=#{t4} or t.TASK_DEF_KEY_=#{t5} or t.TASK_DEF_KEY_=#{t6})") 
			.parameter("userid", userId)
			.parameter("processDefinitionKey", "myTransferProcess")
			.parameter("t1", "transferHandle")
			.parameter("t2", "transferTask")
			.parameter("t3", "epibolyTask")
			.parameter("t4", "epibolyAudit")
			.parameter("t5", "transferAudit")
			.parameter("t6", "audit")
			.list().size();
			
			workList = taskService
			.createNativeTaskQuery()
			.sql("select t.* from ACT_RU_TASK t left join  act_re_procdef f on t.proc_def_id_=f.id_ "
				+ " where t.ASSIGNEE_=#{userid} and f.key_= #{processDefinitionKey} and (t.TASK_DEF_KEY_=#{t1} or t.TASK_DEF_KEY_=#{t2} or t.TASK_DEF_KEY_=#{t3} or t.TASK_DEF_KEY_=#{t4} or t.TASK_DEF_KEY_=#{t5} or t.TASK_DEF_KEY_=#{t6})") 
			.parameter("userid", userId)
			.parameter("processDefinitionKey", "myTransferProcess")
			.parameter("t1", "transferHandle")
			.parameter("t2", "transferTask")
			.parameter("t3", "epibolyTask")
			.parameter("t4", "epibolyAudit")
			.parameter("t5", "transferAudit")
			.parameter("t6", "audit")
			.listPage(pageIndex * pageSize,pageSize);*/
		  //流程引擎查询待处理工单-end
			
		  
			IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
//			for (Task workOrderTask : workList) {
			for (AndroidWorkOrderTask workOrderTask : workOrderList) { //2015-12-22抢修
				Search search = new Search();
				search.addFilterEqual("processInstanceId", workOrderTask
						.getProcessInstanceId());
//				search.addSortAsc("sendTime");//按派单时间倒序
				SearchResult t = pnrTransferOfficeService.searchAndCount(search);
				List<PnrTransferOffice> list = t.getResult();
				if (list != null && list.size() != 0) {
					PnrTransferOffice pnrTransferOffice = list.get(0);
					TaskModel tranferOfficeModel = new TaskModel();
					tranferOfficeModel.setTaskId(workOrderTask.getTaskWworkOrderId());
//					tranferOfficeModel.setTaskId(workOrderTask.getId());
					tranferOfficeModel.setProcessInstanceId(pnrTransferOffice
							.getProcessInstanceId());
					tranferOfficeModel.setTcountry(workOrderTask.getTcountry());
					tranferOfficeModel.setTdate(workOrderTask.getTdate());
					
					tranferOfficeModel.setTheme(pnrTransferOffice.getTheme());
					if(workOrderTask.getTaskDefinitionKey().equals("transferHandle")){
						tranferOfficeModel.setStepState("5");
					}else if (workOrderTask.getTaskDefinitionKey().equals("transferTask")){
						tranferOfficeModel.setStepState("0");
					}else if (workOrderTask.getTaskDefinitionKey().equals("epibolyTask")){
						tranferOfficeModel.setStepState("1");
					}else if (workOrderTask.getTaskDefinitionKey().equals("twoSpotChecks")){
						tranferOfficeModel.setStepState("23");//二次抽查

					}
//					}else if (workOrderTask.getTaskDefinitionKey().equals("epibolyAudit")){
//						tranferOfficeModel.setStepState("2");
//					}else if (workOrderTask.getTaskDefinitionKey().equals("transferAudit")){
//						tranferOfficeModel.setStepState("3");
//					}else if (workOrderTask.getTaskDefinitionKey().equals("audit")){
//						tranferOfficeModel.setStepState("4");
					
					
					PnrList.add(tranferOfficeModel);
					
				}
			}
		
		}else if("maleGuest".equals(sheetType)){//公客故障工单查询
			
			androidQuery.setProcessDefinitionKey("myMaleGuestProcess");
			androidQuery.setWorkOrderType(sheetType);
			
			String processMaleGuestKey = "myMaleGuestProcess";
			
			//jdbc查询待处理工单-start 2015-12-22注释
		    count = pnrTransferOfficeService.getAndroidTaskCount(androidQuery);
		    workOrderList = pnrTransferOfficeService.getAndroidTaskList(androidQuery, pageIndex, pageSize);
		    //jdbc查询待处理工单-end
		 
		    //流程引擎查询待处理工单-start  2015-12-22 增加
			/*count=taskService
			.createNativeTaskQuery()
			.sql("select tk.proc_inst_id_  from  ACT_RU_TASK tk left join PNR_ACT_TRANSFER_OFFICE_MAIN mm on mm.process_instance_id = tk.proc_inst_id_ left join  act_re_procdef f on tk.proc_def_id_=f.id_"+
						" where  f.key_ = #{processMaleGuestKey} and mm.STATE!=8 and mm.do_flag is null and tk.ASSIGNEE_=#{userid} and tk.TASK_DEF_KEY_=#{t7}")
			.parameter("userid", userId)
			.parameter("processMaleGuestKey", processMaleGuestKey)
			.parameter("t7", "auditor")
			.list().size();
			
			workList = taskService
			.createNativeTaskQuery()
			.sql("select tk.*  from  ACT_RU_TASK tk left join PNR_ACT_TRANSFER_OFFICE_MAIN mm on mm.process_instance_id = tk.proc_inst_id_ left join  act_re_procdef f on tk.proc_def_id_=f.id_"+
						" where  f.key_ = #{processMaleGuestKey} and mm.STATE!=8 and mm.do_flag is null and tk.ASSIGNEE_=#{userid} and tk.TASK_DEF_KEY_=#{t7}")
			.parameter("userid", userId)
			.parameter("processMaleGuestKey", processMaleGuestKey)
			.parameter("t7", "auditor")
			.listPage(pageIndex * pageSize,pageSize);*/
		  //流程引擎查询待处理工单-end
			
			IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
//			for (Task workOrderTask : workList) {
			for (AndroidWorkOrderTask workOrderTask : workOrderList) {// 2015-12-22备份
				Search search = new Search();
				search.addFilterEqual("processInstanceId", workOrderTask
						.getProcessInstanceId());
//				search.addSortAsc("sendTime");//按派单时间倒序
				SearchResult t = pnrTransferOfficeService.searchAndCount(search);
				List<PnrTransferOffice> list = t.getResult();
				if (list != null && list.size() != 0) {
					PnrTransferOffice pnrTransferOffice = list.get(0);
					TaskModel tranferOfficeModel = new TaskModel();
					tranferOfficeModel.setTaskId(workOrderTask.getTaskWworkOrderId());
//					tranferOfficeModel.setTaskId(workOrderTask.getId());
					tranferOfficeModel.setProcessInstanceId(pnrTransferOffice
							.getProcessInstanceId());
					tranferOfficeModel.setTheme(pnrTransferOffice.getTheme());
					tranferOfficeModel.setTcountry(workOrderTask.getTcountry());
					tranferOfficeModel.setTdate(workOrderTask.getTdate());
					if (workOrderTask.getTaskDefinitionKey().equals("auditor")){
						tranferOfficeModel.setStepState("12");
						
						if("1".equals(pnrTransferOffice.getMaleGuestState())){
							tranferOfficeModel.setLineType("1");
						}else{
							tranferOfficeModel.setLineType("0");
						}						
					}else if(workOrderTask.getTaskDefinitionKey().equals("twoSpotChecks")){
						tranferOfficeModel.setStepState("22");

					}
					
					PnrList.add(tranferOfficeModel);
					
					}
				}
			}else if("arteryPrecheck".equals(sheetType)){//干线预检预修工单查询
				
				androidQuery.setProcessDefinitionKey("transferArteryPrecheckProcess");
				androidQuery.setWorkOrderType(sheetType);
				
				String processArteryPrecheckKey = "transferArteryPrecheckProcess";
			   
				 //距离的限制
				pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
			    Map<String, String> distanceMap = pnrTransferOfficeService.getAndroidLimitDistance(processArteryPrecheckKey);
			    spotcheckDistance = distanceMap.get("spotcheck_distance")==null?"":distanceMap.get("spotcheck_distance");
			    handleDistance =distanceMap.get("handle_distance")==null?"":distanceMap.get("spotcheck_distance");
				//距离的限制-end
				
			    //jdbc查询待处理工单-start
			    count = pnrTransferOfficeService.getAndroidTaskCount(androidQuery);
			    workOrderList = pnrTransferOfficeService.getAndroidTaskList(androidQuery, pageIndex, pageSize);
			    //jdbc查询待处理工单-end			    
	
				IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
				for (AndroidWorkOrderTask workOrderTask : workOrderList) {
					Search search = new Search();
					search.addFilterEqual("processInstanceId", workOrderTask
							.getProcessInstanceId());
//					search.addSortAsc("sendTime");//按派单时间倒序
					SearchResult t = pnrTransferOfficeService.searchAndCount(search);
					List<PnrTransferOffice> list = t.getResult();
					if (list != null && list.size() != 0) {
						PnrTransferOffice pnrTransferOffice = list.get(0);
						TaskModel tranferOfficeModel = new TaskModel();
						tranferOfficeModel.setTaskId(workOrderTask.getTaskWworkOrderId());
						tranferOfficeModel.setProcessInstanceId(pnrTransferOffice
								.getProcessInstanceId());
						tranferOfficeModel.setTheme(pnrTransferOffice.getTheme());
						
						if (workOrderTask.getTaskDefinitionKey().equals("worker")){
							tranferOfficeModel.setStepState("14");
						}else if(workOrderTask.getTaskDefinitionKey().equals("daiweiAudit")){ //160824添加区县抽检
							tranferOfficeModel.setStepState("25");
						}else if(workOrderTask.getTaskDefinitionKey().equals("orderAudit")){
							tranferOfficeModel.setStepState("15");							
						}
						
						
						PnrList.add(tranferOfficeModel);
						
						}
					}
			}else if("transferInterface".equals(sheetType)){//本地网预检预修工单查询
				String processThreePrecheckKey = "transferNewPrechechProcess";
				androidQuery.setProcessDefinitionKey("'newTransferPrecheck','newTwoTransferPrecheck','transferNewPrechechProcess'");
				androidQuery.setWorkOrderType(sheetType);

				 //距离的限制
				pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
			    Map<String, String> distanceMap = pnrTransferOfficeService.getAndroidLimitDistance(processThreePrecheckKey);
			    spotcheckDistance = distanceMap.get("spotcheck_distance")==null?"":distanceMap.get("spotcheck_distance");
			    handleDistance =distanceMap.get("handle_distance")==null?"":distanceMap.get("spotcheck_distance");
				//距离的限制-end
				
				//新预检预修工单--总数
			    count = pnrTransferOfficeService.getAndroidTaskCount(androidQuery);
			    workOrderList = pnrTransferOfficeService.getAndroidTaskList(androidQuery, pageIndex, pageSize);
				for (AndroidWorkOrderTask workOrderTask : workOrderList) {
					Search search = new Search();
					search.addFilterEqual("processInstanceId", workOrderTask
							.getProcessInstanceId());
//					search.addSortAsc("sendTime");//按派单时间倒序
					SearchResult t = pnrTransferOfficeService.searchAndCount(search);
					List<PnrTransferOffice> list = t.getResult();
					if (list != null && list.size() != 0) {
						PnrTransferOffice pnrTransferOffice = list.get(0);
						TaskModel tranferOfficeModel = new TaskModel();
						tranferOfficeModel.setTaskId(workOrderTask.getTaskWworkOrderId());
						tranferOfficeModel.setProcessInstanceId(pnrTransferOffice
								.getProcessInstanceId());
						tranferOfficeModel.setTheme(pnrTransferOffice.getTheme());
						
						if (workOrderTask.getTaskDefinitionKey().equals("worker")){
							tranferOfficeModel.setStepState("8");
						}else if(workOrderTask.getTaskDefinitionKey().equals("daiweiAudit")){ //160824添加区县抽检
							tranferOfficeModel.setStepState("24");
						}else if(workOrderTask.getTaskDefinitionKey().equals("orderAudit")){
							tranferOfficeModel.setStepState("13");							
						}
						
						PnrList.add(tranferOfficeModel);
						
					}
				}
		}else if("overHaul".equals(sheetType)){//大修改造---开始
			
			androidQuery.setProcessDefinitionKey("overHaulNewProcess");
			androidQuery.setWorkOrderType(sheetType);
			
			String overHaulNewProcessKey = "overHaulNewProcess";
			
			 //距离的限制
			pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		    Map<String, String> distanceMap = pnrTransferOfficeService.getAndroidLimitDistance(overHaulNewProcessKey);
		    spotcheckDistance = distanceMap.get("spotcheck_distance")==null?"":distanceMap.get("spotcheck_distance");
		    handleDistance =distanceMap.get("handle_distance")==null?"":distanceMap.get("spotcheck_distance");
			//距离的限制-end
			
		    //jdbc查询待处理工单-start
		    count = pnrTransferOfficeService.getAndroidTaskCount(androidQuery);
		    workOrderList = pnrTransferOfficeService.getAndroidTaskList(androidQuery, pageIndex, pageSize);
		    //jdbc查询待处理工单-end
			
			IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
			
			for (AndroidWorkOrderTask workOrderTask : workOrderList) {
				Search search = new Search();
				search.addFilterEqual("processInstanceId", workOrderTask
						.getProcessInstanceId());
//				search.addSortAsc("sendTime");//按派单时间倒序
				SearchResult t = pnrTransferOfficeService.searchAndCount(search);
				List<PnrTransferOffice> list = t.getResult();
				if (list != null && list.size() != 0) {
					PnrTransferOffice pnrTransferOffice = list.get(0);
					TaskModel tranferOfficeModel = new TaskModel();
					tranferOfficeModel.setTaskId(workOrderTask.getTaskWworkOrderId());
					tranferOfficeModel.setProcessInstanceId(pnrTransferOffice
							.getProcessInstanceId());
					tranferOfficeModel.setTheme(pnrTransferOffice.getTheme());
					
					if (workOrderTask.getTaskDefinitionKey().equals("worker")){
						tranferOfficeModel.setStepState("16");//大修改造处理
					}else if(workOrderTask.getTaskDefinitionKey().equals("orderAudit")){
						tranferOfficeModel.setStepState("17");//大修改造抽查							
					}
					
					PnrList.add(tranferOfficeModel);
					
					}
				}
			
		}//大修改造---结束
		else if("RoomDemolition".equals(sheetType)){//机房拆除---开始
			androidQuery.setProcessDefinitionKey("roomDemolition");
			androidQuery.setWorkOrderType(sheetType);
			
			String roomDemolitionProcessKey = "roomDemolition";
			
			 //距离的限制
			pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		    Map<String, String> distanceMap = pnrTransferOfficeService.getAndroidLimitDistance(roomDemolitionProcessKey);
		    spotcheckDistance = distanceMap.get("spotcheck_distance")==null?"":distanceMap.get("spotcheck_distance");
		    handleDistance =distanceMap.get("handle_distance")==null?"":distanceMap.get("spotcheck_distance");
			//距离的限制-end
		    
		    //jdbc查询待处理工单-start 暂时先挪用原来的逻辑操作
		    count = pnrTransferOfficeService.getAndroidTaskCount(androidQuery);
		    workOrderList = pnrTransferOfficeService.getAndroidTaskList(androidQuery, pageIndex, pageSize);
		    //jdbc查询待处理工单-end
		    
		    //流程引擎查询待处理工单-start  2015-12-22打开
			/*count = taskService
			.createNativeTaskQuery()
			.sql("select * from ACT_RU_TASK tk left join  act_re_procdef f on tk.proc_def_id_=f.id_ "+
							" where tk.ASSIGNEE_=#{userid} and f.key_ = #{processKey} and (tk.TASK_DEF_KEY_=#{t1} or tk.TASK_DEF_KEY_=#{t2})")
					.parameter("userid", userId)
					.parameter("processKey", roomDemolitionProcessKey)
					.parameter("t1", "worker")
					.parameter("t2", "orderAudit").list().size();
			
			workList = taskService
			.createNativeTaskQuery()
			.sql("select * from ACT_RU_TASK tk left join  act_re_procdef f on tk.proc_def_id_=f.id_ "+
						" where tk.ASSIGNEE_=#{userid} and f.key_ = #{processKey} and (tk.TASK_DEF_KEY_=#{t1} or tk.TASK_DEF_KEY_=#{t2})")
			.parameter("userid", userId)
			.parameter("processKey", roomDemolitionProcessKey)
			.parameter("t1", "worker")
			.parameter("t2", "orderAudit")
			.listPage(pageIndex * pageSize,pageSize);*/
		  //流程引擎查询待处理工单-end
		    
		    
			IRoomDemolitionService roomDemolitionService = (IRoomDemolitionService)getBean("roomDemolitionService");
			
//			for (Task workOrderTask : workList) {
			for (AndroidWorkOrderTask workOrderTask : workOrderList) {
				Search search = new Search();
				search.addFilterEqual("processInstanceId", workOrderTask
						.getProcessInstanceId());
//				search.addSortAsc("sendTime");//按派单时间
				SearchResult t = roomDemolitionService.searchAndCount(search);
				List<RoomDemolition> list = t.getResult();
				if (list != null && list.size() != 0) {
					RoomDemolition roomDemolition = list.get(0);
					TaskModel tranferOfficeModel = new TaskModel();
					tranferOfficeModel.setTaskId(workOrderTask.getTaskWworkOrderId());
//					tranferOfficeModel.setTaskId(workOrderTask.getId());
					tranferOfficeModel.setProcessInstanceId(roomDemolition
							.getProcessInstanceId());
					tranferOfficeModel.setTheme(roomDemolition.getTheme());
					
					if (workOrderTask.getTaskDefinitionKey().equals("worker")){
						tranferOfficeModel.setStepState("18");//机房拆除处理
					}else if(workOrderTask.getTaskDefinitionKey().equals("orderAudit")){
						tranferOfficeModel.setStepState("19");//机房拆除验收							
					}
					
					PnrList.add(tranferOfficeModel);
					
					}
				}
			
		}//机房拆除---结束
		else if("oltBbu".equals(sheetType)){//oltbbu机房查询---开始
			androidQuery.setProcessDefinitionKey("oltBbuProcess");
			androidQuery.setWorkOrderType(sheetType);
			
			String oltBbuProcessKey = "oltBbuProcess";
		   
			 //距离的限制
			pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		    Map<String, String> distanceMap = pnrTransferOfficeService.getAndroidLimitDistance(oltBbuProcessKey);
		    spotcheckDistance = distanceMap.get("spotcheck_distance")==null?"":distanceMap.get("spotcheck_distance");
		    handleDistance =distanceMap.get("handle_distance")==null?"":distanceMap.get("spotcheck_distance");
			//距离的限制-end
			
		    //jdbc查询待处理工单-start
		    count = pnrTransferOfficeService.getAndroidTaskCount(androidQuery);
		    workOrderList = pnrTransferOfficeService.getAndroidTaskList(androidQuery, pageIndex, pageSize);
		    //jdbc查询待处理工单-end
			
			IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
			for (AndroidWorkOrderTask workOrderTask : workOrderList) {
				Search search = new Search();
				search.addFilterEqual("processInstanceId", workOrderTask
						.getProcessInstanceId());
//				search.addSortAsc("sendTime");//按派单时间倒序
				SearchResult t = pnrTransferOfficeService.searchAndCount(search);
				List<PnrTransferOffice> list = t.getResult();
				if (list != null && list.size() != 0) {
					PnrTransferOffice pnrTransferOffice = list.get(0);
					TaskModel tranferOfficeModel = new TaskModel();
					tranferOfficeModel.setTaskId(workOrderTask.getTaskWworkOrderId());
					tranferOfficeModel.setProcessInstanceId(pnrTransferOffice
							.getProcessInstanceId());
					tranferOfficeModel.setTheme(pnrTransferOffice.getTheme());
					
					if (workOrderTask.getTaskDefinitionKey().equals("worker")){
						tranferOfficeModel.setStepState("20");//施工队施工回单
					}else if(workOrderTask.getTaskDefinitionKey().equals("orderAudit")){
						tranferOfficeModel.setStepState("21");	//区县公司验收						
					}
					
					PnrList.add(tranferOfficeModel);
					
					}
				}
		
		}//oltbbu机房查询---结束

		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		returnMap.put("datas", PnrList);
		returnMap.put("count", count);
		returnMap.put("handleDistance", handleDistance);
		returnMap.put("spotcheckDistance", spotcheckDistance);
		returnMap.put("approver", "");
		JSONArray returnArray = JSONArray.fromObject(returnMap);
		String returnStr = returnArray.toString();
		System.out.println("AndroidWorkOrderAction-showListendundo:returnStr:"+returnStr);
		MobileCommonUtils.responseWrite(response, returnStr, "UTF-8");
		return;
	}

	/**
	 * 工单详情
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void showDetailPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String processInstanceId = StaticMethod.nullObject2String(request
				.getParameter("processInstanceId")); // 派单流程Id
		String sheetType = StaticMethod.nullObject2String(request
				.getParameter("sheetType")); // 工单类型
		String allSheetType = StaticMethod.nullObject2String(request
				.getParameter("allSheetType"));
		List<TaskModel> PnrList = new ArrayList<TaskModel>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if ("task".equals(sheetType) || "task".equals(allSheetType)) {	//任务工单
			IPnrTaskTicketService pnrTaksTicketService = (IPnrTaskTicketService) getBean("pnrTaskTicketService");
			Search search = new Search();
			search.addFilterEqual("processInstanceId", processInstanceId);
			SearchResult t = pnrTaksTicketService.searchAndCount(search);
			List<PnrTaskTicket> list = t.getResult();
			List<PnrTaskTicket> list2  = new ArrayList<PnrTaskTicket>();
			Map<String, Object> returnMap = new HashMap<String, Object>();
			// 将id转化成name --start
			for (int i = 0; i < list.size(); i++) {
				PnrTaskTicket pnrTaskTicket = new PnrTaskTicket();
				
				pnrTaskTicket.setId(list.get(i).getId());
				pnrTaskTicket.setProcessInstanceId(processInstanceId);
				pnrTaskTicket.setTheme(list.get(i).getTheme());
				pnrTaskTicket.setSite(list.get(i).getSite());
				pnrTaskTicket.setContent(list.get(i).getContent());
				
				
				String strings = CommonUtils.getId2NameString(list.get(i)
						.getSpecialty(), 1, ",");
				pnrTaskTicket.setSpecialty(strings);
				pnrTaskTicket.setCity(
						CommonUtils.getId2NameString(list.get(i).getCity(), 2,
								","));
				pnrTaskTicket.setCountry(
						CommonUtils.getId2NameString(list.get(i).getCountry(),
								2, ","));
				pnrTaskTicket.setSubType(
						CommonUtils.getId2NameString(list.get(i).getSubType(),
								1, ","));
				pnrTaskTicket.setCandidateGroup(
						CommonUtils.getId2NameString(list.get(i)
								.getCandidateGroup(), 3, ","));
				pnrTaskTicket.setAndroidCreateTime(list.get(i).getCreateTime()==null?null:
						dateFormat.format(list.get(i).getCreateTime()));
				pnrTaskTicket.setAndroidStartTime(list.get(i).getStartTime()==null?null:
						dateFormat.format(list.get(i).getStartTime()));
				pnrTaskTicket.setAndroidEndTime(list.get(i).getEndTime()==null?null:
						dateFormat.format(list.get(i).getEndTime()));
				pnrTaskTicket.setInitiator(
						CommonUtils.getId2NameString(
								list.get(i).getInitiator(), 4, ","));
				list2.add(pnrTaskTicket);
			}
			// 将id转化成name --end
			returnMap.put("datas", list2);
			returnMap.put("approver", CommonUtils.getAuditorByUserid(
					CommonUtils.taskRoleId, request));
			JSONArray returnArray = JSONArray.fromObject(returnMap);
			String returnStr = returnArray.toString();
			//System.out.println("工单信息" + returnStr);
			MobileCommonUtils.responseWrite(response, returnStr, "UTF-8");
			return;

		}else if ("transferOffice".equals(sheetType) || "transferOffice".equals(allSheetType)) {	//抢修工单
			/*
			 * 传输局故障工单详情
			 * DENGYANMING@BOCO.COM.CN 2014.06.17 ADD
			 * */
			TaskService taskService = (TaskService) getBean("taskService");
			List<Task> workList = new ArrayList<Task>();
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request
			.getSession().getAttribute("sessionform");
			String userId = sessionform.getUserid();
			workList = taskService.createTaskQuery().taskAssignee(userId)
						.processDefinitionKey("myTransferProcess").processInstanceId(processInstanceId).active().list();
			String taskDefinitionKey = "";
			String stepState ="";
			if(workList != null && workList.size() > 0){
				taskDefinitionKey= workList.get(0).getTaskDefinitionKey();
			}
			
			if(taskDefinitionKey.equals("transferHandle")){
				stepState = "5";
			}else if (taskDefinitionKey.equals("transferTask")){
				stepState = "0";
			}else if (taskDefinitionKey.equals("epibolyTask")){
				stepState = "1";
			}else if (taskDefinitionKey.equals("twoSpotChecks")){
				stepState = "23";
			}
			/*	
			else if (taskDefinitionKey.equals("epibolyAudit")){
				stepState = "2";
			}else if (taskDefinitionKey.equals("transferAudit")){
				stepState = "3";
			}else if (taskDefinitionKey.equals("audit")){
				stepState = "4";
			}*/
				
			IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
			Search search = new Search();
			search.addFilterEqual("processInstanceId", processInstanceId);
			SearchResult t = pnrTransferOfficeService.searchAndCount(search);
			List<PnrTransferOffice> list = t.getResult();
			List<PnrTransferOffice> list2 = new ArrayList<PnrTransferOffice>();
			String userIdString = "";
			if (list.size() > 0) {

				userIdString = list.get(0).getInitiator();

			}
			// 将id转化成name --start
			for (int i = 0; i < list.size(); i++) {
				PnrTransferOffice pnrTransferOffice = new PnrTransferOffice();
//				设置一些公共的属性
				PnrTransferOffice obj = list.get(i);
				pnrTransferOffice = setValue(obj,pnrTransferOffice);
				
				pnrTransferOffice.setAndroidCreateTime(list.get(i).getCreateTime()==null?null:dateFormat.format(list.get(i).getCreateTime()));
				pnrTransferOffice.setAndroidEndTime(list.get(i).getEndTime()==null?null:dateFormat.format(list.get(i).getEndTime()));
				pnrTransferOffice.setAndroidSendTime(list.get(i).getSendTime()==null?null:dateFormat.format(list.get(i).getSendTime()));
				pnrTransferOffice.setInitiator(CommonUtils.getId2NameString(list.get(i).getInitiator(), 4, ","));
				pnrTransferOffice.setTaskAssignee(CommonUtils.getId2NameString(list.get(i).getTaskAssignee(), 4, ","));
				pnrTransferOffice.setSubType(CommonUtils.getId2NameString(list.get(i).getSubType(),1, ","));
				pnrTransferOffice.setStepState(stepState);
				list2.add(pnrTransferOffice);
			}
			// 将id转化成name --end
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap.put("datas", list2);
			String areaid=com.boco.eoms.partner.process.util.CommonUtils.getAreaIdByDeptId(sessionform.getDeptid());
//			 areaid ="282601";	//测试人员

			String acheckAssignee = pnrTransferOfficeService.getDealingPeopleOfRepair(areaid,"6902");
			returnMap.put("approver", acheckAssignee);
			/*
			 * returnMap.put("approver",
			 * CommonUtils.getAuditorByUserid(CommonUtils.troubleRoleId,
			 * request));
			 */JSONArray returnArray = JSONArray.fromObject(returnMap);
			String returnStr = returnArray.toString();
			// System.out.println("工单信息" + returnStr);
			MobileCommonUtils.responseWrite(response, returnStr, "UTF-8");
			return;

		}else if("arteryPrecheck".equals(sheetType) || "arteryPrecheck".equals(allSheetType)){	//干线预检预修工单
			//预检预修-干线
			TaskService taskService = (TaskService) getBean("taskService");
			List<Task> workList = new ArrayList<Task>();
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request
			.getSession().getAttribute("sessionform");
			String userId = sessionform.getUserid();
			PnrTransferOfficeHandle pnrTransferOfficeHandle	 = null;
			
			workList = taskService.createTaskQuery().taskAssignee(userId)
			.processDefinitionKey("transferArteryPrecheckProcess").processInstanceId(processInstanceId).active().list();
			
			String taskDefinitionKey = "";
			String stepState ="";
			if(workList != null && workList.size() > 0){
				taskDefinitionKey= workList.get(0).getTaskDefinitionKey();
				if (taskDefinitionKey.equals("worker")){
					stepState = "14";
				}else if(taskDefinitionKey.equals("daiweiAudit")){ //区县抽检
					stepState = "25";
				}else if (taskDefinitionKey.equals("orderAudit")){
					stepState = "15";//抽查时不用获取下一步抽查人。
					
					IPnrTransferOfficeHandleService pnrTransferOfficeHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
					Search search2 = new Search();
					search2.addFilterEqual("processInstanceId", processInstanceId);
					search2.addFilterEqual("linkName","worker");
					search2.addSort("receiveTime", true);
					SearchResult thandleResult=pnrTransferOfficeHandleService.searchAndCount(search2);
					List<PnrTransferOfficeHandle> listHandle = thandleResult.getResult();
					if(thandleResult.getTotalCount()>0){
						pnrTransferOfficeHandle = listHandle.get(0);
					}
				}
			}
			 IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
				Search search = new Search();
				search.addFilterEqual("processInstanceId", processInstanceId);
				SearchResult t = pnrTransferOfficeService.searchAndCount(search);
				List<PnrTransferOffice> list = t.getResult();
				List<PnrTransferOffice> list2 = new ArrayList<PnrTransferOffice>();
				String userIdString = "";
				if (list.size() > 0) {

					//userIdString = list.get(0).getInitiator();
					userIdString = list.get(0).getOneInitiator();

				}
				
				//***事前照片的平均点（经度，纬度）-start
				Map<String, String> avgMap = pnrTransferOfficeService.getAvgByProcessInstanceId(processInstanceId);
				String latitude = "";
				String longitude ="";
				if(avgMap!=null){
					latitude =avgMap.get("latitude");
					longitude = avgMap.get("longitude");
				}
				//***事前照片的平均点（经度，纬度）--end
				
				// 将id转化成name --start
				for (int i = 0; i < list.size(); i++) {
					PnrTransferOffice pnrTransferOffice  = new PnrTransferOffice();
//					设置一些公共的属性
					PnrTransferOffice obj = list.get(i);
					pnrTransferOffice = setValue(obj,pnrTransferOffice);
					
					pnrTransferOffice.setAndroidCreateTime(list.get(i).getCreateTime()==null?null:dateFormat.format(list.get(i).getCreateTime()));
					pnrTransferOffice.setAndroidEndTime(list.get(i).getEndTime()==null?null:dateFormat.format(list.get(i).getEndTime()));
					pnrTransferOffice.setAndroidSendTime(list.get(i).getSendTime()==null?null:dateFormat.format(list.get(i).getSendTime()));
					//直接获取名称了，却不是userid
					pnrTransferOffice.setInitiator(CommonUtils.getId2NameString(list.get(i).getInitiator(), 4, ","));
					pnrTransferOffice.setTaskAssignee(CommonUtils.getId2NameString(list.get(i).getTaskAssignee(), 4, ","));
					
					pnrTransferOffice.setSubType(CommonUtils.getId2NameString(list.get(i).getSubType(),1, ","));
					pnrTransferOffice.setStepState(stepState);
					pnrTransferOffice.setAndroidApplicationTime(dateFormat.format(list.get(i).getSubmitApplicationTime()));
					pnrTransferOffice.setWorkOrderDegree(CommonUtils.getId2NameString(list.get(i).getWorkOrderDegree(),1, ","));
					pnrTransferOffice.setKeyWord(CommonUtils.getId2NameString(list.get(i).getKeyWord(),1, ","));
					pnrTransferOffice.setWorkOrderType(CommonUtils.getId2NameString(list.get(i).getWorkOrderType(),1, ","));
					
					pnrTransferOffice.setLongitude(longitude);
					pnrTransferOffice.setLatitude(latitude);
					
					list2.add(pnrTransferOffice);
				}
				// 将id转化成name --end
				Map<String, Object> returnMap = new HashMap<String, Object>();
				returnMap.put("datas", list2);
				returnMap.put("approver", userIdString);
				returnMap.put("pnrTransferOfficeHandle", pnrTransferOfficeHandle);
				JSONArray returnArray = JSONArray.fromObject(returnMap);
				String returnStr = returnArray.toString();
				MobileCommonUtils.responseWrite(response, returnStr, "UTF-8");
				return;
		}else if("maleGuest".equals(sheetType) || "maleGuest".equals(allSheetType)){	//公客故障工单
		//公客工单
			TaskService taskService = (TaskService) getBean("taskService");
			List<Task> workList = new ArrayList<Task>();
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request
			.getSession().getAttribute("sessionform");
			String userId = sessionform.getUserid();
			
			workList = taskService.createTaskQuery().taskAssignee(userId)
			.processDefinitionKey("myMaleGuestProcess").processInstanceId(processInstanceId).active().list();
			
			String taskDefinitionKey = "";
			String stepState ="";
			if(workList != null && workList.size() > 0){
				taskDefinitionKey= workList.get(0).getTaskDefinitionKey();
			}
			 if (taskDefinitionKey.equals("auditor")){
				stepState = "12";
			}else if(taskDefinitionKey.equals("twoSpotChecks")){				
				stepState = "22";
			}
			 
			 IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
				Search search = new Search();
				search.addFilterEqual("processInstanceId", processInstanceId);
				SearchResult t = pnrTransferOfficeService.searchAndCount(search);
				List<PnrTransferOffice> list = t.getResult();
				List<PnrTransferOffice> list2 =  new ArrayList<PnrTransferOffice>();

				String userIdString = "";
				if (list.size() > 0) {

					userIdString = list.get(0).getInitiator();

				}
				// 将id转化成name --start
				for (int i = 0; i < list.size(); i++) {
					PnrTransferOffice pnrTransferOffice  = new PnrTransferOffice();
//					设置一些公共的属性
					PnrTransferOffice obj = list.get(i);
					pnrTransferOffice = setValue(obj,pnrTransferOffice);
					
					pnrTransferOffice.setAndroidCreateTime(list.get(i).getCreateTime()==null?null:dateFormat.format(list.get(i).getCreateTime()));
					pnrTransferOffice.setAndroidEndTime(list.get(i).getEndTime()==null?null:dateFormat.format(list.get(i).getEndTime()));
					pnrTransferOffice.setAndroidSendTime(list.get(i).getSendTime()==null?null:dateFormat.format(list.get(i).getSendTime()));
					pnrTransferOffice.setInitiator(CommonUtils.getId2NameString(list.get(i).getInitiator(), 4, ","));
					pnrTransferOffice.setTaskAssignee(CommonUtils.getId2NameString(list.get(i).getTaskAssignee(), 4, ","));
					pnrTransferOffice.setSubType(CommonUtils.getId2NameString(list.get(i).getSubType(),1, ","));
					pnrTransferOffice.setStepState(stepState);
					
					if ("auditor".equals(taskDefinitionKey)){
						if(null==pnrTransferOffice.getMaleGuestState()||"0".equals(pnrTransferOffice.getMaleGuestState())){
							pnrTransferOffice.setLineType("0");
						}else if("1".equals(pnrTransferOffice.getMaleGuestState())){
							pnrTransferOffice.setLineType("1");
						}						
					}
					
					
					list2.add(pnrTransferOffice);
				}
				// 将id转化成name --end
				Map<String, Object> returnMap = new HashMap<String, Object>();
				returnMap.put("datas", list2);
				String areaid=com.boco.eoms.partner.process.util.CommonUtils.getAreaIdByDeptId(sessionform.getDeptid());
//				 areaid ="282601";	//测试人员

				String acheckAssignee = pnrTransferOfficeService.getDealingPeopleOfRepair(areaid,"6902");

				returnMap.put("approver", acheckAssignee);
				/*
				 * returnMap.put("approver",
				 * CommonUtils.getAuditorByUserid(CommonUtils.troubleRoleId,
				 * request));
				 */JSONArray returnArray = JSONArray.fromObject(returnMap);
				String returnStr = returnArray.toString();
				// System.out.println("工单信息" + returnStr);
				MobileCommonUtils.responseWrite(response, returnStr, "UTF-8");
				return;
		}else if("transferInterface".equals(sheetType) || "transferInterface".equals(allSheetType)){	//本地网预检预修工单
//			本地网-预检预修
			TaskService taskService = (TaskService) getBean("taskService");
			List<Task> workList = new ArrayList<Task>();
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request
			.getSession().getAttribute("sessionform");
			String userId = sessionform.getUserid();
			//获取该流程id号所对应的工单信息
			IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
			//***事前照片的平均点（经度，纬度）-start
			Map<String, String> avgMap = pnrTransferOfficeService.getAvgByProcessInstanceId(processInstanceId);
			String latitude = "";
			String longitude ="";
			if(avgMap!=null){
				latitude =avgMap.get("latitude");
				longitude = avgMap.get("longitude");
			}
			//***事前照片的平均点（经度，纬度）--end
			
			Search search = new Search();
			search.addFilterEqual("processInstanceId", processInstanceId);
			SearchResult t = pnrTransferOfficeService.searchAndCount(search);
			List<PnrTransferOffice> list = t.getResult();
			List<PnrTransferOffice> list2 = new ArrayList<PnrTransferOffice>();
			PnrTransferOffice oneData  = new PnrTransferOffice();
			if(list!=null&& list.size()>0){
				oneData = list.get(0);
			}
			String precheckFlag = oneData.getVersionFlag();
			PnrTransferOfficeHandle pnrTransferOfficeHandle	 = null;
			String stepState ="";
			String userIdString = "";
			if(precheckFlag==null || "".equals(precheckFlag)){//旧预检预修工单
				userIdString = list.get(0).getInitiator();
				workList = taskService.createTaskQuery().taskAssignee(userId)
				.processDefinitionKey("newTransferPrecheck").processInstanceId(processInstanceId).active().list();
				
				String taskDefinitionKey = "";
				if(workList != null && workList.size() > 0){
					taskDefinitionKey= workList.get(0).getTaskDefinitionKey();
				}
				 if (taskDefinitionKey.equals("worker")){
						stepState = "8";
					}
			}else if("1".equals(precheckFlag.trim())){//新预检预修工单
				
				userIdString = list.get(0).getOneInitiator();
				workList = taskService.createTaskQuery().taskAssignee(userId)
				.processDefinitionKey("newTwoTransferPrecheck").processInstanceId(processInstanceId).active().list();
				String taskDefinitionKey = "";
				if(workList != null && workList.size() > 0){
					taskDefinitionKey= workList.get(0).getTaskDefinitionKey();
					if (taskDefinitionKey.equals("worker")){
						stepState = "8";
					}else if (taskDefinitionKey.equals("orderAudit")){
						stepState = "13";//抽查时不用获取下一步抽查人。
						
						IPnrTransferOfficeHandleService pnrTransferOfficeHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
						Search search2 = new Search();
						search2.addFilterEqual("processInstanceId", processInstanceId);
						search2.addFilterEqual("linkName","worker");
						search2.addSort("receiveTime", true);
						SearchResult thandleResult=pnrTransferOfficeHandleService.searchAndCount(search2);
						List<PnrTransferOfficeHandle> listHandle = thandleResult.getResult();
						if(thandleResult.getTotalCount()>0){
							pnrTransferOfficeHandle = listHandle.get(0);
						}
					}
				}
				
			}else if("2".equals(precheckFlag.trim())){
				userIdString = list.get(0).getOneInitiator();
				workList = taskService.createTaskQuery().taskAssignee(userId)
				.processDefinitionKey("transferNewPrechechProcess").processInstanceId(processInstanceId).active().list();
				String taskDefinitionKey = "";
				if(workList != null && workList.size() > 0){
					taskDefinitionKey= workList.get(0).getTaskDefinitionKey();
					if (taskDefinitionKey.equals("worker")){
						stepState = "8";
					}else if(taskDefinitionKey.equals("daiweiAudit")){ //区县抽检
						stepState = "24";
					}else if (taskDefinitionKey.equals("orderAudit")){
						stepState = "13";//抽查时不用获取下一步抽查人。
						
						IPnrTransferOfficeHandleService pnrTransferOfficeHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
						Search search2 = new Search();
						search2.addFilterEqual("processInstanceId", processInstanceId);
						search2.addFilterEqual("linkName","worker");
						search2.addSort("receiveTime", true);
						SearchResult thandleResult=pnrTransferOfficeHandleService.searchAndCount(search2);
						List<PnrTransferOfficeHandle> listHandle = thandleResult.getResult();
						if(thandleResult.getTotalCount()>0){
							pnrTransferOfficeHandle = listHandle.get(0);
						}
					}
				}
				
			}
				// 将id转化成name --start
				for (int i = 0; i < list.size(); i++) {
					PnrTransferOffice pnrTransferOffice  = new PnrTransferOffice();
//					设置一些公共的属性
					PnrTransferOffice obj = list.get(i);
					pnrTransferOffice = setValue(obj,pnrTransferOffice);
					
					pnrTransferOffice.setAndroidCreateTime(list.get(i).getCreateTime()==null?null:dateFormat.format(list.get(i).getCreateTime()));
					pnrTransferOffice.setAndroidEndTime(list.get(i).getEndTime()==null?null:dateFormat.format(list.get(i).getEndTime()));
					pnrTransferOffice.setAndroidSendTime(list.get(i).getSendTime()==null?null:dateFormat.format(list.get(i).getSendTime()));
					//直接获取名称了，却不是userid
					pnrTransferOffice.setInitiator(CommonUtils.getId2NameString(list.get(i).getInitiator(), 4, ","));
					pnrTransferOffice.setTaskAssignee(CommonUtils.getId2NameString(list.get(i).getTaskAssignee(), 4, ","));
					
					pnrTransferOffice.setSubType(CommonUtils.getId2NameString(list.get(i).getSubType(),1, ","));
					pnrTransferOffice.setStepState(stepState);
					pnrTransferOffice.setAndroidApplicationTime(list.get(i).getSubmitApplicationTime()==null?null:dateFormat.format(list.get(i).getSubmitApplicationTime()));
					pnrTransferOffice.setWorkOrderDegree(CommonUtils.getId2NameString(list.get(i).getWorkOrderDegree(),1, ","));
					pnrTransferOffice.setKeyWord(CommonUtils.getId2NameString(list.get(i).getKeyWord(),1, ","));
					pnrTransferOffice.setWorkOrderType(CommonUtils.getId2NameString(list.get(i).getWorkOrderType(),1, ","));
					
					pnrTransferOffice.setLatitude(latitude);
					pnrTransferOffice.setLongitude(longitude);
					
					list2.add(pnrTransferOffice);
				}
				// 将id转化成name --end
				Map<String, Object> returnMap = new HashMap<String, Object>();
				returnMap.put("datas", list2);
				returnMap.put("approver", userIdString);
				returnMap.put("pnrTransferOfficeHandle", pnrTransferOfficeHandle);
				JSONArray returnArray = JSONArray.fromObject(returnMap);
				String returnStr = returnArray.toString();
				MobileCommonUtils.responseWrite(response, returnStr, "UTF-8");
				return;
			
		}else if ("faultDeal".equals(sheetType) || "faultDeal".equals(allSheetType)) {	//故障工单
//			故障工单
			IPnrTroubleTicketService pnrTroubleTicketService = (IPnrTroubleTicketService) getBean("pnrTroubleTicketService");
			Search search = new Search();
			search.addFilterEqual("processInstanceId", processInstanceId);
			SearchResult t = pnrTroubleTicketService.searchAndCount(search);
			List<PnrTroubleTicket> list = t.getResult();
			
			List<PnrTroubleTicket> list2 = new ArrayList<PnrTroubleTicket>();
			
			String userIdString = "";
			if (list.size() > 0) {

				userIdString = list.get(0).getInitiator();

			}
			// 将id转化成name --start
			for (int i = 0; i < list.size(); i++) {
				PnrTroubleTicket pnrTroubleTicket = new PnrTroubleTicket();
				
				pnrTroubleTicket.setId(list.get(i).getId());
				pnrTroubleTicket.setTheme(list.get(i).getTheme());
				pnrTroubleTicket.setProcessInstanceId(processInstanceId);
				pnrTroubleTicket.setConnectPerson(list.get(i).getConnectPerson());
				pnrTroubleTicket.setFaultDescription(list.get(i).getFaultDescription());
				pnrTroubleTicket.setFailedSite(list.get(i).getFailedSite());
				
				
				String strings = CommonUtils.getId2NameString(list.get(i)
						.getSpecialty(), 1, ",");
				pnrTroubleTicket.setSpecialty(strings);
				pnrTroubleTicket.setCity(
						CommonUtils.getId2NameString(list.get(i).getCity(), 2,
								","));
				pnrTroubleTicket.setCountry(
						CommonUtils.getId2NameString(list.get(i).getCountry(),
								2, ","));
				pnrTroubleTicket.setSubType(
						CommonUtils.getId2NameString(list.get(i).getSubType(),
								1, ","));
				pnrTroubleTicket.setAndroidCreateTime(
						dateFormat.format(list.get(i).getCreateTime()));
				pnrTroubleTicket.setAndroidEndTime(
						dateFormat.format(list.get(i).getEndTime()));
				pnrTroubleTicket.setAndroidSendTime(
						dateFormat.format(list.get(i).getSendTime()));
				pnrTroubleTicket.setInitiator(
						CommonUtils.getId2NameString(
								list.get(i).getInitiator(), 4, ","));
				pnrTroubleTicket.setTaskAssignee(
						CommonUtils.getId2NameString(list.get(i)
								.getTaskAssignee(), 4, ","));
				
				//工单处理时限
				pnrTroubleTicket.setProcessLimit(list.get(i).getProcessLimit());
				//是否集团客户
				pnrTroubleTicket.setIsCustomers(list.get(i).getIsCustomers());
				
				list2.add(pnrTroubleTicket);
			}
			// 将id转化成name --end
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap.put("datas", list2);
			returnMap.put("approver", userIdString);
			/*
			 * returnMap.put("approver",
			 * CommonUtils.getAuditorByUserid(CommonUtils.troubleRoleId,
			 * request));
			 */JSONArray returnArray = JSONArray.fromObject(returnMap);
			String returnStr = returnArray.toString();
			// System.out.println("工单信息" + returnStr);
			MobileCommonUtils.responseWrite(response, returnStr, "UTF-8");
			return;
		}else if("overHaul".equals(sheetType) || "overHaul".equals(allSheetType)){//大修改造---开始
			// 大修改造
			TaskService taskService = (TaskService) getBean("taskService");
			List<Task> workList = new ArrayList<Task>();
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request
					.getSession().getAttribute("sessionform");
			String userId = sessionform.getUserid();
			PnrTransferOfficeHandle pnrTransferOfficeHandle = null;
			workList = taskService.createTaskQuery().taskAssignee(userId)
					.processDefinitionKey("overHaulNewProcess")
					.processInstanceId(processInstanceId).active().list();
			String taskDefinitionKey = "";
			String stepState = "";
			if (workList != null && workList.size() > 0) {
				taskDefinitionKey = workList.get(0).getTaskDefinitionKey();
				if (taskDefinitionKey.equals("worker")) {
					stepState = "16";
				} else if (taskDefinitionKey.equals("orderAudit")) {
					stepState = "17";// 抽查时不用获取下一步审核人。
					IPnrTransferOfficeHandleService pnrTransferOfficeHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
					Search search2 = new Search();
					search2.addFilterEqual("processInstanceId",
							processInstanceId);
					search2.addFilterEqual("linkName", "worker");
					search2.addSort("receiveTime", true);
					SearchResult thandleResult = pnrTransferOfficeHandleService
							.searchAndCount(search2);
					List<PnrTransferOfficeHandle> listHandle = thandleResult
							.getResult();
					if (thandleResult.getTotalCount() > 0) {
						pnrTransferOfficeHandle = listHandle.get(0);
					}
				}
			}

			IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");

			Search search = new Search();
			search.addFilterEqual("processInstanceId", processInstanceId);
			SearchResult t = pnrTransferOfficeService.searchAndCount(search);
			List<PnrTransferOffice> list = t.getResult();
			List<PnrTransferOffice> list2 = new ArrayList<PnrTransferOffice>();
			String userIdString = "";
			if (list.size() > 0) {

				//userIdString = list.get(0).getInitiator();
				userIdString = list.get(0).getCityLineCharge();//工单发起人

			}
			//***事前照片的平均点（经度，纬度）-start
			Map<String, String> avgMap = pnrTransferOfficeService.getAvgByProcessInstanceId(processInstanceId);
			String latitude = "";
			String longitude ="";
			if(avgMap!=null){
				latitude =avgMap.get("LATITUDE");
				longitude = avgMap.get("LONGITUDE");
			}
//			System.out.println("latitude:"+latitude+"longtitude:"+longitude);
			//***事前照片的平均点（经度，纬度）--end
			
			// 将id转化成name --start
			for (int i = 0; i < list.size(); i++) {
				PnrTransferOffice pnrTransferOffice = new PnrTransferOffice();
				// 设置一些公共的属性
				PnrTransferOffice obj = list.get(i);
				pnrTransferOffice = setValue(obj, pnrTransferOffice);

				pnrTransferOffice.setAndroidCreateTime(list.get(i)
						.getCreateTime() == null ? null : dateFormat
						.format(list.get(i).getCreateTime()));
				pnrTransferOffice
						.setAndroidEndTime(list.get(i).getEndTime() == null ? null
								: dateFormat.format(list.get(i).getEndTime()));
				pnrTransferOffice
						.setAndroidSendTime(list.get(i).getSendTime() == null ? null
								: dateFormat.format(list.get(i).getSendTime()));
				// 直接获取名称了，却不是userid
				pnrTransferOffice.setInitiator(CommonUtils.getId2NameString(
						list.get(i).getInitiator(), 4, ","));
				
				pnrTransferOffice.setTaskAssignee(CommonUtils.getId2NameString(
						list.get(i).getTaskAssignee(), 4, ","));

				pnrTransferOffice.setSubType(CommonUtils.getId2NameString(list
						.get(i).getSubType(), 1, ","));
				pnrTransferOffice.setStepState(stepState);
				pnrTransferOffice.setAndroidApplicationTime(dateFormat
						.format(list.get(i).getSubmitApplicationTime()));
				pnrTransferOffice.setWorkOrderDegree(CommonUtils
						.getId2NameString(list.get(i).getWorkOrderDegree(), 1,
								","));
				pnrTransferOffice.setKeyWord(CommonUtils.getId2NameString(list
						.get(i).getKeyWord(), 1, ","));
				pnrTransferOffice.setWorkOrderType(CommonUtils
						.getId2NameString(list.get(i).getWorkOrderType(), 1,
								","));
				
				pnrTransferOffice.setLatitude(latitude);
				pnrTransferOffice.setLongitude(longitude);
				
				list2.add(pnrTransferOffice);
			}
			// 将id转化成name --end
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap.put("datas", list2);
			returnMap.put("approver", userIdString);
			returnMap.put("pnrTransferOfficeHandle", pnrTransferOfficeHandle);
			JSONArray returnArray = JSONArray.fromObject(returnMap);
			String returnStr = returnArray.toString();
			MobileCommonUtils.responseWrite(response, returnStr, "UTF-8");
			return;
			
		}else if("RoomDemolition".equals(sheetType) || "RoomDemolition".equals(allSheetType)){//机房拆除---开始
			// 机房拆除
			TaskService taskService = (TaskService) getBean("taskService");
			List<Task> workList = new ArrayList<Task>();
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request
					.getSession().getAttribute("sessionform");
			String userId = sessionform.getUserid();
			RoomDemolitionHandle roomDemolitionHandle = null;
			workList = taskService.createTaskQuery().taskAssignee(userId)
					.processDefinitionKey("roomDemolition")
					.processInstanceId(processInstanceId).active().list();
			String taskDefinitionKey = "";
			String stepState = "";
			if (workList != null && workList.size() > 0) {
				taskDefinitionKey = workList.get(0).getTaskDefinitionKey();
				if (taskDefinitionKey.equals("worker")) {
					stepState = "18";
				} else if (taskDefinitionKey.equals("orderAudit")) {
					stepState = "19";// 验收
					IRoomDemolitionHandleService roomDemolitionHandleService = (IRoomDemolitionHandleService) getBean("roomDemolitionHandleService");
					Search search2 = new Search();
					search2.addFilterEqual("processInstanceId",
							processInstanceId);
					search2.addFilterEqual("linkName", "worker");
					search2.addSort("receiveTime", true);
					SearchResult thandleResult = roomDemolitionHandleService
							.searchAndCount(search2);
					List<RoomDemolitionHandle> listHandle = thandleResult
							.getResult();
					if (thandleResult.getTotalCount() > 0) {
						roomDemolitionHandle = listHandle.get(0);
					}
				}
			}

			IRoomDemolitionService roomDemolitionService = (IRoomDemolitionService)getBean("roomDemolitionService");

			Search search = new Search();
			search.addFilterEqual("processInstanceId", processInstanceId);
			SearchResult t = roomDemolitionService.searchAndCount(search);
			List<RoomDemolition> list = t.getResult();
			List<RoomDemolition> list2 = new ArrayList<RoomDemolition>();
			String userIdString = "";
			if (list.size() > 0) {

				if(taskDefinitionKey.equals("worker")){
					
					userIdString = list.get(0).getCityManageCharge();// 市运维主管
				}else if(taskDefinitionKey.equals("orderAudit")){
					userIdString = list.get(0).getProvinceManageCharge();// 省运维主管

				}

			}
				
			// 将id转化成name --start
			for (int i = 0; i < list.size(); i++) {
				RoomDemolition roomDemolition = new RoomDemolition();
				// 设置一些公共的属性
				RoomDemolition obj = list.get(i);
				roomDemolition = setValueRoomDemolition(obj, roomDemolition);

				roomDemolition.setStepState(stepState);
//              机房拆除的事前照片经纬度，在主表中有记录
//				roomDemolition.setLatitude(latitude);
//				roomDemolition.setLongitude(longitude);
				
				roomDemolition.setCity(CommonUtils.getId2NameString(list
						.get(i).getCity(), 2, ","));
				roomDemolition.setCountry(CommonUtils.getId2NameString(list
						.get(i).getCountry(), 2, ","));
				roomDemolition.setStationType(CommonUtils.getId2NameString(list
						.get(i).getStationType(), 1, ","));
				roomDemolition.setStationEquity(CommonUtils.getId2NameString(list
						.get(i).getStationEquity(), 1, ","));
				roomDemolition.setOpticcableWay(CommonUtils.getId2NameString(list
						.get(i).getOpticcableWay(), 1, ","));
				roomDemolition.setEnergyIsstation(CommonUtils.getId2NameString(list
						.get(i).getEnergyIsstation(), 1, ","));
				
				
				list2.add(roomDemolition);
			}
			// 将id转化成name --end
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap.put("datas", list2);
			returnMap.put("approver", userIdString);
			returnMap.put("pnrTransferOfficeHandle", roomDemolitionHandle);
			JSONArray returnArray = JSONArray.fromObject(returnMap);
			String returnStr = returnArray.toString();
			MobileCommonUtils.responseWrite(response, returnStr, "UTF-8");
			return;
			
		}else if("oltBbu".equals(sheetType) || "oltBbu".equals(allSheetType)){//oltbbu机房---开始
			// 大修改造
			TaskService taskService = (TaskService) getBean("taskService");
			List<Task> workList = new ArrayList<Task>();
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request
					.getSession().getAttribute("sessionform");
			String userId = sessionform.getUserid();
			PnrTransferOfficeHandle pnrTransferOfficeHandle = null;
			workList = taskService.createTaskQuery().taskAssignee(userId)
					.processDefinitionKey("oltBbuProcess")
					.processInstanceId(processInstanceId).active().list();
			String taskDefinitionKey = "";
			String stepState = "";
			if (workList != null && workList.size() > 0) {
				taskDefinitionKey = workList.get(0).getTaskDefinitionKey();
				if (taskDefinitionKey.equals("worker")) {//施工队施工回单
					stepState = "20";
				} else if (taskDefinitionKey.equals("orderAudit")) {//区县公司验收
					stepState = "21";
					IPnrTransferOfficeHandleService pnrTransferOfficeHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
					Search search2 = new Search();
					search2.addFilterEqual("processInstanceId",
							processInstanceId);
					search2.addFilterEqual("linkName", "worker");
					search2.addSort("receiveTime", true);
					SearchResult thandleResult = pnrTransferOfficeHandleService
							.searchAndCount(search2);
					List<PnrTransferOfficeHandle> listHandle = thandleResult
							.getResult();
					if (thandleResult.getTotalCount() > 0) {
						pnrTransferOfficeHandle = listHandle.get(0);
					}
				}
			}
			
			//***事前照片的平均点（经度，纬度）-start
			Map<String, String> avgMap = pnrTransferOfficeService.getAvgByProcessInstanceId(processInstanceId);
			String latitude = "";
			String longitude ="";
			if(avgMap!=null){
				latitude =avgMap.get("LATITUDE");
				longitude = avgMap.get("LONGITUDE");
			}
			
			IPnrOltBbuOfficeRelationService pnrOltBbuOfficeRelationService = (IPnrOltBbuOfficeRelationService) getBean("pnrOltBbuOfficeRelationService");
			PnrOltBbuOfficeMainModel pnrTransferOffice = pnrOltBbuOfficeRelationService.findPnrOltBbuOfficeMainByProcessInstanceId(processInstanceId);
			String userIdString="";
			List<PnrOltBbuOfficeMainModel> list2 = new ArrayList<PnrOltBbuOfficeMainModel>();
			if(pnrTransferOffice!= null){
				if(taskDefinitionKey.equals("worker")){
					userIdString = pnrTransferOffice.getCityLineCharge();// 市线路
				}else if(taskDefinitionKey.equals("orderAudit")){
					userIdString = pnrTransferOffice.getCityManageCharge();// 市运维

				}
				pnrTransferOffice.setAndroidCreateTime(pnrTransferOffice.getCreateTime() == null ? null : dateFormat.format(pnrTransferOffice.getCreateTime()));
				pnrTransferOffice.setAndroidEndTime(pnrTransferOffice.getEndTime() == null ? null: dateFormat.format(pnrTransferOffice.getEndTime()));
				pnrTransferOffice.setAndroidSendTime(pnrTransferOffice.getSendTime() == null ? null: dateFormat.format(pnrTransferOffice.getSendTime()));
				// 直接获取名称了，却不是userid
				pnrTransferOffice.setInitiator(CommonUtils.getId2NameString(pnrTransferOffice.getInitiator(), 4, ","));
				
				pnrTransferOffice.setTaskAssignee(CommonUtils.getId2NameString(pnrTransferOffice.getTaskAssignee(), 4, ","));

				pnrTransferOffice.setSubType(CommonUtils.getId2NameString(pnrTransferOffice.getSubType(), 1, ","));
				pnrTransferOffice.setStepState(stepState);
				//pnrTransferOffice.setAndroidApplicationTime(dateFormat.format(pnrTransferOffice.getSubmitApplicationTime()));
				pnrTransferOffice.setWorkOrderDegree(CommonUtils.getId2NameString(pnrTransferOffice.getWorkOrderDegree(), 1,","));
				pnrTransferOffice.setKeyWord(CommonUtils.getId2NameString(pnrTransferOffice.getKeyWord(), 1, ","));
				pnrTransferOffice.setWorkOrderType(CommonUtils.getId2NameString(pnrTransferOffice.getWorkOrderType(), 1,","));
				pnrTransferOffice.setLatitude(latitude);
				pnrTransferOffice.setLongitude(longitude);
				
				list2.add(pnrTransferOffice);
			}
	
			// 将id转化成name --end
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap.put("datas", list2);
			returnMap.put("approver", userIdString);
			returnMap.put("pnrTransferOfficeHandle", pnrTransferOfficeHandle);
			JSONArray returnArray = JSONArray.fromObject(returnMap);
			String returnStr = returnArray.toString();
		//	System.out.println("AndroidWorkOrderAction-showDetailPage:returnStr-"+returnStr);
			MobileCommonUtils.responseWrite(response, returnStr, "UTF-8");
			return;
			
		}
		return;
	}
	//获取数据
	private RoomDemolition setValueRoomDemolition(RoomDemolition obj,RoomDemolition roomDemolition){
		roomDemolition.setId(obj.getId());
		roomDemolition.setProcessInstanceId(obj.getProcessInstanceId());
		roomDemolition.setTheme(obj.getTheme());
		roomDemolition.setState(obj.getState());
		roomDemolition.setCreateWork(obj.getCreateWork());
		roomDemolition.setDept(obj.getDept());
		roomDemolition.setStationName(obj.getStationName());
		roomDemolition.setStationLevel(obj.getStationLevel());
		roomDemolition.setStationArea(obj.getStationArea());
		roomDemolition.setAnnualRent(obj.getAnnualRent());
		roomDemolition.setHireDateStr(obj.getHireDate()==null?null:StaticMethod.date2Str(obj.getHireDate()));
		roomDemolition.setContractTimeStr(obj.getContractTime()==null?null:StaticMethod.date2Str(obj.getContractTime()));
		roomDemolition.setOpticcableNum(obj.getOpticcableNum());
		roomDemolition.setEquipmentRackNum(obj.getEquipmentRackNum());
		roomDemolition.setMaterialCost(obj.getMaterialCost());
		roomDemolition.setEnergyStationName(obj.getEnergyStationName());
		roomDemolition.setEnergyStationCode(obj.getEnergyStationCode());
		roomDemolition.setInitiateTime(obj.getInitiateTime());
		roomDemolition.setSendTimeStr(obj.getSendTime()==null?null:StaticMethod.date2Str(obj.getSendTime()));
		roomDemolition.setDescription(obj.getDescription());
		roomDemolition.setAttachmentsNum(obj.getAttachmentsNum());
		roomDemolition.setLongitude(obj.getPotoAvgLongitude());
		roomDemolition.setLatitude(obj.getPotoAvgLatitude());
		roomDemolition.setArchivingTime(obj.getArchivingTime());
		roomDemolition.setLastreplyTime(obj.getLastreplyTime());
		roomDemolition.setInitiator(obj.getInitiator());
		roomDemolition.setCityManageCharge(obj.getCityManageCharge());
		roomDemolition.setProvinceManageCharge(obj.getProvinceManageCharge());
		roomDemolition.setStationId(obj.getStationId());
		roomDemolition.setStationLevelId(obj.getStationLevelId());
		roomDemolition.setComparisonResults(obj.getComparisonResults());
		roomDemolition.setAmountTotal(obj.getAmountTotal());
		roomDemolition.setRoomLongitude(obj.getRoomLongitude());
		roomDemolition.setRoomLatitude(obj.getRoomLatitude());
		roomDemolition.setPotoAvgLongitude(obj.getPotoAvgLongitude());
		roomDemolition.setPotoAvgLatitude(obj.getPotoAvgLatitude());
		roomDemolition.setCountryCharge(obj.getCountryCharge());
		roomDemolition.setRollbackFlag(obj.getRollbackFlag());
		roomDemolition.setSheetId(obj.getSheetId());
		roomDemolition.setDistance(obj.getDistance());
		roomDemolition.setProvinceManageExamineTime(obj.getProvinceManageExamineTime());
		roomDemolition.setOrderAuditTime(obj.getOrderAuditTime());
		
		return roomDemolition;
	}
	//获取数据
	private PnrTransferOffice setValue(PnrTransferOffice obj,PnrTransferOffice pnrTransferOffice){
		pnrTransferOffice.setId(obj.getId());
		pnrTransferOffice.setTheme(obj.getTheme());
		pnrTransferOffice.setProcessInstanceId(obj.getProcessInstanceId());
		pnrTransferOffice.setFaultDescription(obj.getFaultDescription());
//		-------
		pnrTransferOffice.setCreateTime(obj.getCreateTime());
		pnrTransferOffice.setSendTime(obj.getSendTime());
		pnrTransferOffice.setConnectPerson(obj.getConnectPerson());
		pnrTransferOffice.setProcessLimit(obj.getProcessLimit());
		pnrTransferOffice.setFaultDescription(obj.getFaultDescription());
		//pnrTransferOffice.setFaultSource(obj.getFaultSource());
		pnrTransferOffice.setFaultSource(CommonUtils.getId2NameString(obj.getFaultSource(),1, ","));
		pnrTransferOffice.setSubType(obj.getSubType());
		pnrTransferOffice.setState(obj.getState());
		pnrTransferOffice.setArchivingTime(obj.getArchivingTime());
		pnrTransferOffice.setTaskAssigneeJSON(obj.getTaskAssigneeJSON());
		pnrTransferOffice.setLastReplyTime(obj.getLastReplyTime());
		pnrTransferOffice.setAttachmentsNum(obj.getAttachmentsNum());
		pnrTransferOffice.setEndTime(obj.getEndTime());
		pnrTransferOffice.setOneInitiator(obj.getOneInitiator());
		pnrTransferOffice.setSecondSendTime(obj.getSecondSendTime());
		pnrTransferOffice.setSecondInitiator(obj.getSecondInitiator());
		pnrTransferOffice.setThirdSendTime(obj.getThirdSendTime());
		pnrTransferOffice.setThemeInterface(obj.getThemeInterface());
		pnrTransferOffice.setEngineer(obj.getEngineer());
		pnrTransferOffice.setInstallAddress(obj.getInstallAddress());
		pnrTransferOffice.setPattern(obj.getPattern());
		pnrTransferOffice.setCity(obj.getCity());
		pnrTransferOffice.setStationName(obj.getStationName());
		pnrTransferOffice.setSpliceBoxName(obj.getSpliceBoxName());
		pnrTransferOffice.setCableNumber(obj.getCableNumber());
		pnrTransferOffice.setBranchBoxNumber(obj.getBranchBoxNumber());
		pnrTransferOffice.setFirstOpticalNumber(obj.getFirstOpticalNumber());
		pnrTransferOffice.setFirstOpticalPort(obj.getFirstOpticalPort());
		pnrTransferOffice.setSecondOpticalNumber(obj.getSecondOpticalNumber());
		pnrTransferOffice.setSecondOpticalPort(obj.getSecondOpticalPort());
		pnrTransferOffice.setSpliceBoxNumber(obj.getSpliceBoxNumber());
		pnrTransferOffice.setSpliceBoxPort(obj.getSpliceBoxPort());
		pnrTransferOffice.setMaleGuestNumber(obj.getMaleGuestNumber());
		pnrTransferOffice.setFaileSite(obj.getFaileSite());
		//pnrTransferOffice.setIsCustomers(obj.getIsCustomers());
		pnrTransferOffice.setIsCustomers(CommonUtils.getId2NameString(obj.getIsCustomers(),1, ","));
		//pnrTransferOffice.setSpecialty(obj.getSpecialty());
		pnrTransferOffice.setSpecialty(CommonUtils.getId2NameString(obj.getSpecialty(),1, ","));
		pnrTransferOffice.setBusiNbr(obj.getBusiNbr());
		pnrTransferOffice.setSiteCd(obj.getSiteCd());
		pnrTransferOffice.setCcpCd(obj.getCcpCd());
		pnrTransferOffice.setTransferOfficeId(obj.getTransferOfficeId());
		pnrTransferOffice.setOperator(obj.getOperator());
		pnrTransferOffice.setBarrierNumber(obj.getBarrierNumber());
		pnrTransferOffice.setSubmitApplicationTime(obj.getSubmitApplicationTime());
		pnrTransferOffice.setWorkOrderNumber(obj.getWorkOrderNumber());
		pnrTransferOffice.setCreateWork(obj.getCreateWork());
		pnrTransferOffice.setCountryCSJ(obj.getCountryCSJ());
		pnrTransferOffice.setCityCSJ(obj.getCityCSJ());
		pnrTransferOffice.setCityGS(obj.getCityGS());
		pnrTransferOffice.setSubTypeName(obj.getSubTypeName());
		pnrTransferOffice.setWorkOrderTypeName(obj.getWorkOrderTypeName());
		pnrTransferOffice.setRepeatState(obj.getRepeatState());
		pnrTransferOffice.setDoFlag(obj.getDoFlag());
		pnrTransferOffice.setSecondRepeatState(obj.getSecondRepeatState());
		pnrTransferOffice.setProjectAmount(obj.getProjectAmount());
		pnrTransferOffice.setWorkType(obj.getWorkType());
		pnrTransferOffice.setProvinceLine(obj.getProvinceLine());
		pnrTransferOffice.setProvinceManage(obj.getProvinceManage());
		pnrTransferOffice.setVersionFlag(obj.getVersionFlag());
		pnrTransferOffice.setCountry(obj.getCountry());
		pnrTransferOffice.setSecondCreateWork(obj.getSecondCreateWork());
		pnrTransferOffice.setCityLineCharge(obj.getCityLineCharge());
		pnrTransferOffice.setCityLineDirector(obj.getCityLineDirector());
		pnrTransferOffice.setCityManageCharge(obj.getCityManageCharge());
		pnrTransferOffice.setCityManageDirector(obj.getCityManageDirector());
		pnrTransferOffice.setProvinceLineCharge(obj.getProvinceLineCharge());
		pnrTransferOffice.setProvinceManageCharge(obj.getProvinceManageCharge());
		pnrTransferOffice.setBearService(obj.getBearService());
		pnrTransferOffice.setSheetId(obj.getSheetId());
		pnrTransferOffice.setRollbackFlag(obj.getRollbackFlag());
		pnrTransferOffice.setPrecheckFlag(obj.getPrecheckFlag());
		pnrTransferOffice.setCompensate(obj.getCompensate());
		
		return pnrTransferOffice;
	}
	/**
	 * 任务工单 -提交
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */

	public void performDeal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// 主题
		String title = request.getParameter("title");
		// 主题ID
		String titleId = request.getParameter("titleId");
		// 回单人
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		// 审核人
		String initiator = request.getParameter("initiator");
		// 回单时间
		Date createTime = new Date();

		// 完成情况
		String mainRemark = request.getParameter("mainRemark");

		// 交通方式
		String transport = request.getParameter("transport");
		// 里程
		String mileage = request.getParameter("mileage");
		// 处理人
		String dealPerformer2 = request.getParameter("dealPerformer2");
		// 处理开始时间
		String dealStartTime = request.getParameter("sheetAcceptLimit");
		// 处理结束时间
		String dealEndTime = request.getParameter("sheetCompleteLimit");

		// 工单流水号
		String processInstanceId = request.getParameter("processInstanceId");

		String taskId = request.getParameter("taskId");
		TaskService taskService = (TaskService) getBean("taskService");
		List<Task> taskList = taskService.createTaskQuery().taskId(taskId)
				.list();
		FormService formService = (FormService) getBean("formService");
		TaskFormData taskFormData = formService.getTaskFormData(taskId);
		Map<String, String> map = new HashMap<String, String>();
		for (FormProperty formProperty : taskFormData.getFormProperties()) {
			if (formProperty.isWritable()) {
				String name = formProperty.getId();
				map.put(name, request.getParameter(name));
			}
		}
		formService.submitTaskFormData(taskId, map);

		IPnrTaskTicketHandleService iPnrTaskTicketHandleService = (IPnrTaskTicketHandleService) getBean("pnrTaskTicketHandleService");

		PnrTaskTicketHandle entity = new PnrTaskTicketHandle();
		entity.setTheme(title);
		entity.setThemeId(titleId);
		entity.setTaskAssignee(userId);
		entity.setAuditor(initiator);
		entity.setDoPersons(dealPerformer2);
		entity.setReceiveTime(createTime);
		entity.setDealStartTime(sFormat.parse(dealStartTime));
		entity.setDealEndTime(sFormat.parse(dealEndTime));
		entity.setHandleDescription(mainRemark);
		entity.setTransport(transport);
		entity.setMileage(Double.parseDouble(mileage));
		entity.setProcessInstanceId(processInstanceId);
		IPnrTaskTicketService iPnrTaskTicketService = (IPnrTaskTicketService) getBean("pnrTaskTicketService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTaskTicket> pnrTaskTicketList = iPnrTaskTicketService
				.search(search);
		if (pnrTaskTicketList != null) {
			PnrTaskTicket pnrTaskTicket = pnrTaskTicketList.get(0);
			pnrTaskTicket.setLastReplyTime(new Date());
			iPnrTaskTicketService.save(pnrTaskTicket);
		}
		// 处理人关系表数据保存--start
		String[] personStrings = dealPerformer2.split(",");
		iPnrTaskTicketService.saveOrUpatePerson(processInstanceId,
				personStrings);
		// 处理人关系表数据保存--end

		iPnrTaskTicketHandleService.save(entity);
		String returnJson = "[{\"success\":\"true\"}]";
		/*
		 * response.setCharacterEncoding("UTF-8");
		 * response.setContentType("text/plian");
		 * response.getWriter().write(returnJson);
		 */
		// System.out.println("工单返回信息" + returnJson);
		MobileCommonUtils.responseWrite(response, returnJson, "UTF-8");

	}

	/**
	 * 故障工单 - 提交
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */

	public void perFaultDeal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 主题
		String title = request.getParameter("title");
		// 主题ID
		String titleId = request.getParameter("titleId");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		// 回单人
		String userId = sessionform.getUserid();

		// 审核人
		String auditor = request.getParameter("auditor");

		// 回单时间
		Date createTime = new Date();
		// 处理描述
		String mainRemark = request.getParameter("mainRemark");
		// 故障是否恢复
		String isRecover = request.getParameter("isRecover");
		// 处理人
		String dealPerformer2 = request.getParameter("dealPerformer2");
		// 交通方式
		String transport = request.getParameter("transport");
		// 里程
		String mileage = request.getParameter("mileage");
		String taskId = request.getParameter("taskId");
		TaskService taskService = (TaskService) getBean("taskService");
		List<Task> taskList = taskService.createTaskQuery().taskId(taskId)
				.list();
		String processInstanceId = request.getParameter("processInstanceId");
		FormService formService = (FormService) getBean("formService");
		TaskFormData taskFormData = formService.getTaskFormData(taskId);
		IPnrTroubleTicketHandleService pnrTroubleTicketHandleService = (IPnrTroubleTicketHandleService) getBean("pnrTroubleTicketHandleService");
		PnrTroubleTicketHandle entity = new PnrTroubleTicketHandle();
		entity.setTheme(title);
		entity.setThemeId(titleId);
		entity.setReceivePerson(userId);
		entity.setAuditor(auditor);
		entity.setDoPersons(dealPerformer2);
		entity.setReceiveTime(createTime);
		entity.setHandleDescription(mainRemark);
		entity.setIsRecover(isRecover);
		entity.setTransport(transport);
		entity.setMileage(Double.parseDouble(mileage));
		entity.setProcessInstanceId(processInstanceId);
		entity.setTaskId(taskId);
		IPnrTroubleTicketService pnrTroubleTicketService = (IPnrTroubleTicketService) getBean("pnrTroubleTicketService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTroubleTicket> pnrTroubleTicketList = pnrTroubleTicketService
				.search(search);
		if (pnrTroubleTicketList != null) {
			PnrTroubleTicket pnrTroubleTicket = pnrTroubleTicketList.get(0);
			pnrTroubleTicket.setLastReplyTime(new Date());
			pnrTroubleTicketService.save(pnrTroubleTicket);
		}
		// 处理人关系表数据保存--start
		String[] personStrings = dealPerformer2.split(",");
		pnrTroubleTicketService.saveOrUpatePerson(processInstanceId,
				personStrings);
		// 处理人关系表数据保存--end
		pnrTroubleTicketHandleService.save(entity);

		Map<String, String> map = new HashMap<String, String>();
		for (FormProperty formProperty : taskFormData.getFormProperties()) {
			if (formProperty.isWritable()) {
				String name = formProperty.getId();
					map.put(name, request.getParameter(name));
			}
		}
		if(pnrTroubleTicketList != null){
			if(pnrTroubleTicketList.get(0).getState()==6){				
				map.put("towHandleState", "handle");
				map.put("taskAssignee", auditor);
			}
		}
		formService.submitTaskFormData(taskId, map);
		
		String returnJson = "[{\"success\":\"true\"}]";
		
		// System.out.println("工单返回信息" + returnJson);
		MobileCommonUtils.responseWrite(response, returnJson, "UTF-8");
		
		//发短信		
		String mainResName="";
		if (pnrTroubleTicketList != null) {
			mainResName =  pnrTroubleTicketList.get(0).getFailedSite();
		}
		String msg = "故障工单号:"+processInstanceId+";主题:"+title+";站点:"+mainResName+";已处理请审核";
		CommonUtils.sendMsg(msg, auditor);

	}
	/**
	 * 传输局故障工单 - 抢修工单提交
	 * DENGYANMING@BOCO.COM.CN 2014.06.18 ADD
	 * */
	public void tranFaultSubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 主题
		String title = request.getParameter("title");
		// 主题ID
		String titleId = request.getParameter("titleId");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		// 回单人
		String userId = sessionform.getUserid();

		// 审核人 taskAssignee
		String auditor = request.getParameter("taskAssignee");
//		System.out.println("1------施工队auditor="+auditor);

		// 回单时间
		Date createTime = new Date();
		// 处理描述
		String mainRemark = request.getParameter("mainRemark");
		// 故障是否恢复
		String isRecover = request.getParameter("isRecover");
		// 处理人
		String dealPerformer2 = request.getParameter("dealPerformer2");
		String taskId = request.getParameter("taskId");
		String processInstanceId = request.getParameter("processInstanceId");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String faultType =StaticMethod.nullObject2String(request
					.getParameter("faultType"));
		 String troubleReason =StaticMethod.nullObject2String(request
					.getParameter("troubleReason"));
		
		String sceneTemplate = StaticMethod.nullObject2String(request.getParameter("sceneTemplate"));
		
		 String sceneName =StaticMethod.nullObject2String(request
					.getParameter("sceneName"));
		 String copyName =StaticMethod.nullObject2String(request
					.getParameter("copyName"));
		Map<String, Object> resultMap = null;
		if(!"".equals(sceneTemplate)){
			ISceneTemplateService sceneTemplateService = (ISceneTemplateService)getBean("sceneTemplateMaleGuestService");
			Map<String,String> param = new HashMap<String,String>();
			param.put("operateType", "android");
			param.put("processType", "transferOffice");//流程类型：非故障触发（抢修）
			resultMap = sceneTemplateService.saveSceneTemplate(request,processInstanceId, "auditor", param);
		}
		
		//msg-start
		String endTime="";
		String contact="";
		//msg-end
		FormService formService = (FormService) getBean("formService");
		TaskService taskService = (TaskService) getBean("taskService");
		List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstanceId).taskDefinitionKey("transferHandle").list();
		//在处理工单的环节
		if(taskList != null && taskList.size() > 0){
			
			IPnrTransferOfficeHandleService pnrTransferOfficeHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
			PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
			entity.setTheme(title);
			entity.setThemeId(titleId);
			entity.setReceivePerson(userId);
			entity.setAuditor(auditor);
//			System.out.println("2------施工队auditor="+auditor);
			entity.setDoPersons(dealPerformer2);
			entity.setReceiveTime(createTime);
			entity.setHandleDescription(mainRemark);
			entity.setIsRecover(isRecover);
			entity.setProcessInstanceId(processInstanceId);
			entity.setTaskId(taskId);
			entity.setCheckTime(createTime);
			entity.setLinkName("transferHandle");
			entity.setOperation("01");
			
			entity.setFaultType(faultType);//故障类型
			entity.setTroubleReason(troubleReason);//故障原因
			
			pnrTransferOfficeHandleService.save(entity);
			
			IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
			Search search = new Search();
			search.addFilterEqual("processInstanceId", processInstanceId);
			List<PnrTransferOffice> pnrTransferOfficeTicketList = pnrTransferOfficeService.search(search);
			if (pnrTransferOfficeTicketList != null) {
				PnrTransferOffice pnrTransferOffice = pnrTransferOfficeTicketList.get(0);
				Date date = null;//setReplyTime(processInstanceId);
				if(date == null){
					
					date = new Date();
				}
				pnrTransferOffice.setLastReplyTime(date);
				pnrTransferOffice.setWorkerProjectAmount(Double.parseDouble(resultMap.get("totalAmount").toString()));//场景模板金额--回单环节
				pnrTransferOffice.setTransferCopyScenesName(copyName);//场景模板相关数据
				pnrTransferOffice.setTransferMainScenesName(sceneName);
				pnrTransferOffice.setRecentCopyScenesName(copyName);
				pnrTransferOffice.setRecentMainScenesName(sceneName);
				pnrTransferOffice.setRollbackFlag("0");//0 通过 1 驳回
			
				//pnrTransferOfficeService.save(pnrTransferOffice);
				//msg-start
				endTime = pnrTransferOffice.getEndTime() == null ? "" : sFormat
						.format(pnrTransferOffice.getEndTime());
				contact= pnrTransferOffice.getConnectPerson();
				//msg-end
				
				Map<String, String> map = new HashMap<String, String>();		
				map.put("acheckAssignee", auditor);
//				System.out.println("3------施工队auditor="+auditor);
				map.put("handleState", "handle");
				
				
//				logger.info("当前的操作人："+userId+";当前的流程:抢修工单;当前的操作环节:施工队（手机端）;当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--start");
				formService.submitTaskFormData(taskId, map);
//				logger.info("当前的操作人："+userId+";当前的流程:抢修工单;当前的操作环节:施工队（手机端）;当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--end");
				
				processTaskService.setTvalueOfTask(processInstanceId, auditor, pnrTransferOffice, PnrTransferOffice.class, null, null,false);
				pnrTransferOfficeService.save(pnrTransferOffice);
				//发短信		
				String msg = "工单号:"+processInstanceId+";主题:"+title+";截止时间:"+endTime+";联系人及电话"+contact+";已处理请审核";		
				Thread thread = new Thread(new SendMsgThread(msg,auditor));
				thread.start();
			}
			
			String returnJson = "[{\"success\":\"true\"}]";
			//System.out.println("工单返回信息------" + returnJson+msg);
			MobileCommonUtils.responseWrite(response, returnJson, "UTF-8");
		}
	}
	/**
	 * 传输局公客接口工单处理提交方法
	  * @author wangyue
	  * @title: maleGuestSubmit
	  * @date Oct 8, 2014 3:48:31 PM
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @throws Exception void
	 */
	public void maleGuestSubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 发短信
		String deadLineTime = "", contact = "";

		// 主题
		String title = StaticMethod.nullObject2String(request
				.getParameter("title"));
		// 主题ID
		String titleId = StaticMethod.nullObject2String(request
				.getParameter("titleId"));
		// 回单人
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		// 审核人
		String auditor = StaticMethod.nullObject2String(request
				.getParameter("auditor"));

		// 回单时间
		Date createTime = new Date();
		// 处理描述
		String mainRemark = StaticMethod.nullObject2String(request
				.getParameter("mainRemark"));
		// 故障处理情况
		String faultHandle = request.getParameter("faultHandle");
		//故障原因
		String faultCause = request.getParameter("faultCause");
		// 处理人
		String dealPerformer2 = request.getParameter("dealPerformer2");

		String taskId = StaticMethod.nullObject2String(request
				.getParameter("taskId"));

		String processInstanceId = StaticMethod.nullObject2String(request
				.getParameter("processInstanceId"));

		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
		entity.setTheme(title);
		entity.setThemeId(titleId);
		entity.setReceivePerson(userId);
		entity.setAuditor(auditor);
		entity.setDoPersons(dealPerformer2);
		entity.setReceiveTime(createTime);
		entity.setHandleDescription(mainRemark);
		entity.setFaultHandle(faultHandle);
		entity.setFaultCause(faultCause);
		entity.setProcessInstanceId(processInstanceId);
		entity.setTaskId(taskId);
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
				.search(search);
		if (pnrTicketList != null) {
			PnrTransferOffice pnrTransferOffice = pnrTicketList.get(0);
			pnrTransferOffice.setLastReplyTime(new Date());
			//改变工单状态：改为8，达到隐藏工单的效果
			pnrTransferOffice.setState(8);
			pnrTransferOfficeService.save(pnrTransferOffice);

			deadLineTime = pnrTransferOffice.getEndTime() == null ? ""
					: sFormat.format(pnrTransferOffice.getEndTime());
			contact = pnrTransferOffice.getConnectPerson();
		}
		// 处理人关系表数据保存--start
		/*
		 * String[] personStrings = dealPerformer2.split(",");
		 * pnrTroubleTicketService.saveOrUpatePerson(processInstanceId,
		 * personStrings);
		 */
		// 处理人关系表数据保存--end
		transferHandleService.save(entity);
		/*
		 * //发短信 String mainResName=""; if (pnrTroubleTicketList != null) {
		 * mainResName = pnrTroubleTicketList.get(0).getFailedSite(); }
		 */
		// 流程提交到下一级
		FormService formService = (FormService) getBean("formService");
		TaskFormData taskFormData = formService.getTaskFormData(taskId);
		Map<String, String> map = new HashMap<String, String>();
		for (FormProperty formProperty : taskFormData.getFormProperties()) {
			if (formProperty.isWritable()) {
				String name = formProperty.getId();
				map.put(name, request.getParameter(name));
			}
		}
		//将处理完的工单停留在施工队处理状态
		//formService.submitTaskFormData(taskId, map);
		// 流程提交到下一级-end
		//判断是否是接口派单,是就进行接口通知
		String maleGuestNumber =  pnrTransferOfficeService.getMaleGuestNumberByThemeId(titleId);
		if(maleGuestNumber!=null && !"".equals(maleGuestNumber=maleGuestNumber.trim())){
			// 调用方法,查询接口需要的数据
			TransferMaleGuestReturn maleGuestReturn = pnrTransferOfficeService
					.getMaleGuestReturnData(maleGuestNumber);
			Thread aThread = new Thread(new MaleGuestReturnThead(sFormat
					.format(new Date()),
					CommonUtils.GK_MALE_GUEST_RECEIPT_METHOD, maleGuestReturn));
			aThread.start();
		}

		String msg = "工单号:" + processInstanceId + ";" + "主题:"
				+ title + ";";
		msg += "截止时间:" + deadLineTime + ";" + "联系人及电话:"
				+ contact + ";已处理请审核";
		CommonUtils.sendMsg(msg, auditor);

		
		String returnJson = "[{\"success\":\"true\"}]";
		
		
		//System.out.println("工单返回信息------" + returnJson+msg);
		
		MobileCommonUtils.responseWrite(response, returnJson, "UTF-8");

	}
	/**
	 * 传输局公客接口工单处理提交方法-改造后的
	  * @author chenbing
	  * @title: maleGuestSubmitNew
	  * @date  2016-03-31 <code>
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @throws Exception void
	 */
	public void maleGuestSubmitNew(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 发短信
		String deadLineTime = "", contact = "";		
		// 主题
		String title = StaticMethod.nullObject2String(request
				.getParameter("title"));
		// 主题ID
		String titleId = StaticMethod.nullObject2String(request
				.getParameter("titleId"));
		// 回单人
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		// 审核人
		String auditor = StaticMethod.nullObject2String(request
				.getParameter("auditor"));
		// 第一核验人
		String acheckAssignee = StaticMethod.nullObject2String(request
				.getParameter("acheckAssignee"));

		// 回单时间
		Date createTime = new Date();
		// 处理描述
		String mainRemark = StaticMethod.nullObject2String(request
				.getParameter("mainRemark"));
		// 故障原因描述
		String faultHandle = request.getParameter("faultHandle");
		//故障原因---固定：光缆故障 2044
		String faultCause = "2044";//request.getParameter("faultCause");
		// 处理人
		String dealPerformer2 = request.getParameter("dealPerformer2");

		String taskId = StaticMethod.nullObject2String(request
				.getParameter("taskId"));

		String processInstanceId = StaticMethod.nullObject2String(request
				.getParameter("processInstanceId"));
		 String faultType =StaticMethod.nullObject2String(request
					.getParameter("faultType"));
		 String troubleReason =StaticMethod.nullObject2String(request
					.getParameter("troubleReason"));

		// 场景模板数据
		String sceneTemplate = StaticMethod.nullObject2String(request
				.getParameter("sceneTemplate"));
		String sceneAmount = StaticMethod.nullObject2String(request
				.getParameter("sceneAmount"));
		
		 String sceneName =StaticMethod.nullObject2String(request
					.getParameter("sceneName"));
		 String copyName =StaticMethod.nullObject2String(request
					.getParameter("copyName"));
//		System.out.println("----------sceneAmount------------:"+sceneAmount);
//		System.out.println("----------sceneTemplate------------:"+sceneTemplate);
		Map<String, Object> resultMap = null;
		if(!"".equals(sceneTemplate)){
			ISceneTemplateService sceneTemplateService = (ISceneTemplateService)getBean("sceneTemplateMaleGuestService");
			Map<String,String> param = new HashMap<String,String>();
			param.put("operateType", "android");
			param.put("processType", "maleGuest");//流程类型：公客
			resultMap = sceneTemplateService.saveSceneTemplate(request,processInstanceId, "auditor", param);
		}

		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
		entity.setTheme(title);
		entity.setThemeId(titleId);
		entity.setReceivePerson(userId);
		entity.setAuditor(auditor);
		entity.setDoPersons(dealPerformer2);
		entity.setReceiveTime(createTime);
		entity.setHandleDescription(mainRemark);
		entity.setFaultHandle(faultHandle);
		entity.setFaultCause(faultCause);
		entity.setProcessInstanceId(processInstanceId);
		entity.setTaskId(taskId);
		
		entity.setCheckTime(createTime);
		entity.setLinkName("auditor");
		entity.setOperation("01");
		entity.setFaultType(faultType);//故障类型
		entity.setTroubleReason(troubleReason);//故障原因
		
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
				.search(search);
		PnrTransferOffice pnrTransferOffice=null;
		if (pnrTicketList != null) {
			 pnrTransferOffice = pnrTicketList.get(0);
			pnrTransferOffice.setLastReplyTime(new Date());
			//改变工单状态：改为8，达到隐藏工单的效果
//			pnrTransferOffice.setState(8);
			pnrTransferOffice.setWorkerProjectAmount(Double.parseDouble(resultMap.get("totalAmount").toString()));//场景模板金额--回单环节
			pnrTransferOffice.setTransferCopyScenesName(copyName);//场景模板相关数据
			pnrTransferOffice.setTransferMainScenesName(sceneName);
			pnrTransferOffice.setRecentCopyScenesName(copyName);
			pnrTransferOffice.setRecentMainScenesName(sceneName);

			pnrTransferOffice.setRollbackFlag("0");//0 通过 1 驳回

			deadLineTime = pnrTransferOffice.getEndTime() == null ? ""
					: sFormat.format(pnrTransferOffice.getEndTime());
			contact = pnrTransferOffice.getConnectPerson();
		}
		// 处理人关系表数据保存--start
		/*
		 * String[] personStrings = dealPerformer2.split(",");
		 * pnrTroubleTicketService.saveOrUpatePerson(processInstanceId,
		 * personStrings);
		 */
		// 处理人关系表数据保存--end
		transferHandleService.save(entity);
		/*
		 * //发短信 String mainResName=""; if (pnrTroubleTicketList != null) {
		 * mainResName = pnrTroubleTicketList.get(0).getFailedSite(); }
		 */
		// 流程提交到下一级
		FormService formService = (FormService) getBean("formService");
		TaskFormData taskFormData = formService.getTaskFormData(taskId);
		Map<String, String> map = new HashMap<String, String>();
		for (FormProperty formProperty : taskFormData.getFormProperties()) {
			if (formProperty.isWritable()) {
				String name = formProperty.getId();
				map.put(name, request.getParameter(name));
			}
		}
		//将处理完的工单停留在施工队处理状态
		formService.submitTaskFormData(taskId, map); //测试隐藏，正式需开放
		// 流程提交到下一级-end
		
		processTaskService.setTvalueOfTask(processInstanceId, acheckAssignee, pnrTransferOffice, PnrTransferOffice.class, null, null,false);
		pnrTransferOfficeService.save(pnrTransferOffice);
		//判断是否是接口派单,是就进行接口通知
		String maleGuestState = pnrTransferOffice.getMaleGuestState();	
		
		TransferMaleGuestReturn maleGuest  = new TransferMaleGuestReturn();			
		String fault_Name="光缆故障-宽带";
		maleGuest.setConfCRMTicketNo(pnrTransferOffice.getMaleGuestNumber());	
		// 回单标示
		maleGuest.setFlag("1");	
		// 回单时间
		maleGuest.setBack_dt(sFormat.format(createTime));
		// 处理人姓名id
		maleGuest.setBack_userid(userId);
		// 处理人姓名
		maleGuest.setBack_username(sessionform.getUsername());
		// 故障原因id
		maleGuest.setReason_id(faultCause);
		// 描述
		maleGuest.setBack_info(mainRemark);		
		// 故障原因
		maleGuest.setReason_name(fault_Name);
		
		pnrTransferOfficeService.maleGuestHandleInterfaceCall(maleGuest,maleGuestState,processInstanceId,titleId);
	/*	String maleGuestNumber =  pnrTransferOfficeService.getMaleGuestNumberByThemeId(titleId);
		if(maleGuestNumber!=null && !"".equals(maleGuestNumber=maleGuestNumber.trim())){
			// 调用方法,查询接口需要的数据
			TransferMaleGuestReturn maleGuestReturn = pnrTransferOfficeService
					.getMaleGuestReturnData(maleGuestNumber);
			Thread aThread = new Thread(new MaleGuestReturnThead(sFormat
					.format(new Date()),
					CommonUtils.GK_MALE_GUEST_RECEIPT_METHOD, maleGuestReturn));
			aThread.start();
		}*/

		String msg = "工单号:" + processInstanceId + ";" + "主题:"
				+ title + ";";
		msg += "截止时间:" + deadLineTime + ";" + "联系人及电话:"
				+ contact + ";已处理请审核";
		CommonUtils.sendMsg(msg, auditor);

		
		String returnJson = "[{\"success\":\"true\"}]";
				
		MobileCommonUtils.responseWrite(response, returnJson, "UTF-8");

	}
	/**
	 * 传输局--预检预修工单提交--工单处理
	  * @author wangyue
	  * @title: transferInterfaceSubmit
	  * @date Oct 22, 2014 4:14:02 PM
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @throws Exception void
	 */
	public void transferInterfaceSubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 主题
		String title = StaticMethod.nullObject2String(request.getParameter("title"));
		// 主题ID
		String titleId = StaticMethod.nullObject2String(request.getParameter("titleId"));
		// 回单人
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		// 审核人
		String auditor = StaticMethod.nullObject2String(request.getParameter("initiatorCheck"));
		//预检预修是否完成
		String isRecover = StaticMethod.nullObject2String(request.getParameter("isRecover"));
		// 回单时间
		Date createTime = new Date();
		// 处理描述
		String mainRemark = StaticMethod.nullObject2String(request.getParameter("mainRemark"));
		// 处理人
		String dealPerformer2 = request.getParameter("dealPerformer2");
		String taskId = StaticMethod.nullObject2String(request.getParameter("taskId"));
		//预检预修工单号
		String processInstanceId = StaticMethod.nullObject2String(request.getParameter("processInstanceId"));
		
		String latitude = StaticMethod.nullObject2String(request.getParameter("latitude"));
		String longitude = StaticMethod.nullObject2String(request.getParameter("longitude"));
		
		//获取下一步流程需要的处理标志和审核人
		String handleState = request.getParameter("workerHandleState");
		String nextPsrson = request.getParameter("initiatorCheck");
		
		
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService.search(search);
		//将部分处理信息,添加到工单主表中
		String msg="";
//		printlog("1AndroidWorkOrderAction-<transferInterfaceSubmit>", 96, "预检预修-本地网：施工队回单-开始操作<"+userId+processInstanceId+">"+new Date());
//		log.info(this, "预检预修-本地网：施工队回单，单号<"+processInstanceId+">---开始操作；"+new Date());
		BocoLog.info("本地网-预检预修流程-施工队回单手机端处理,服务器IP:"+request.getRemoteAddr()+",端口号:"+request.getRemotePort(), 109,"单号<"+processInstanceId+">---开始操作；"+new Date());
		if (pnrTicketList != null) {
			PnrTransferOffice pnrTransferOffice = pnrTicketList.get(0);
			String precheckFlag = pnrTransferOffice.getVersionFlag();
//			log.info(this, "预检预修-本地网：施工队回单，precheckFlag<"+precheckFlag+">；pnrTransferOffice.getWorkerSceneHandleFlag()<"+pnrTransferOffice.getWorkerSceneHandleFlag()+">"+new Date());
			BocoLog.info("本地网-预检预修流程-施工队回单手机端处理,服务器IP:"+request.getRemoteAddr()+",端口号:"+request.getRemotePort(), 109,"precheckFlag<"+precheckFlag+">；pnrTransferOffice.getWorkerSceneHandleFlag()<"+userId+pnrTransferOffice.getWorkerSceneHandleFlag()+">"+new Date());
//			printlog("2AndroidWorkOrderAction-<transferInterfaceSubmit>", 96, "预检预修-本地网：施工队回单，precheckFlag<"+precheckFlag+">；pnrTransferOffice.getWorkerSceneHandleFlag()<"+userId+pnrTransferOffice.getWorkerSceneHandleFlag()+">"+new Date());

			String handleFlag = pnrTransferOffice.getWorkerSceneHandleFlag();
			if("2".equals(precheckFlag.trim())&& (handleFlag==null||"1".equals(handleFlag))){
				pnrTransferOffice.setWorkerSceneHandleFlag("1");//手机端处理完，需要web端进行场景模板处理
				pnrTransferOfficeService.save(pnrTransferOffice);
//				log.info(this, "预检预修-本地网：施工队回单-进入需要web端回单的方法，setWorkerSceneHandleFlag=1<成功>；"+new Date());
				BocoLog.info("本地网-预检预修流程-施工队回单手机端处理,服务器IP:"+request.getRemoteAddr()+",端口号:"+request.getRemotePort(), 109,"-进入需要web端回单的方法，setWorkerSceneHandleFlag=1<成功>；"+userId+new Date());
//				printlog("3AndroidWorkOrderAction-<transferInterfaceSubmit>", 96,  "预检预修-本地网：施工队回单-进入需要web端回单的方法，setWorkerSceneHandleFlag=1<成功>；"+userId+new Date());


			}else{
				pnrTransferOffice.setLastReplyTime(new Date());
				pnrTransferOffice.setRollbackFlag("0");
				
				//将处理信息存入工单处理实体类中--start
				IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
				PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
				entity.setTheme(title);
				entity.setThemeId(titleId);
				entity.setReceivePerson(userId);
				entity.setAuditor(auditor);
				entity.setDoPersons(dealPerformer2);
				entity.setReceiveTime(createTime);
				entity.setHandleDescription(mainRemark);
				entity.setProcessInstanceId(processInstanceId);
				entity.setIsRecover(isRecover);
				entity.setTaskId(taskId);
				entity.setLatitude(latitude);
				entity.setLongitude(longitude);
				entity.setLinkName("worker");
				entity.setOperation("04");

				//派发流程
				FormService formService = (FormService) getBean("formService");
				TaskFormData taskFormData = formService.getTaskFormData(taskId);
				Map<String, String> map = new HashMap<String, String>();
				String tempKeyValue="";
				if (precheckFlag == null || "".equals(precheckFlag)) {// 旧预检预修工单--走旧工单流程
					map.put("handleState", "handle");
					map.put("taskAssignee", nextPsrson);
					BocoLog.info("本地网-预检预修流程-施工队回单手机端处理,服务器IP:"+request.getRemoteAddr()+",端口号:"+request.getRemotePort(), 109,"施工队回单-流程null提交taskAssignee<"+userId+processInstanceId+">"+nextPsrson+new Date());
//					printlog("4AndroidWorkOrderAction-<transferInterfaceSubmit>",96,"预检预修-本地网：施工队回单-流程null提交taskAssignee<"+userId+processInstanceId+">"+nextPsrson+new Date());
				} else if ("1".equals(precheckFlag.trim())) {// 新预检预修工单--走新工单流程
					map.put("workerHandleState", "handle");
					map.put("initiatorCheck", nextPsrson);
					BocoLog.info("本地网-预检预修流程-施工队回单手机端处理,服务器IP:"+request.getRemoteAddr()+",端口号:"+request.getRemotePort(), 109,"施工队回单-流程1提交initiatorCheck<"+userId+processInstanceId+">"+nextPsrson+new Date());
//					printlog("4AndroidWorkOrderAction-<transferInterfaceSubmit>", 96, "预检预修-本地网：施工队回单-流程1提交initiatorCheck<"+userId+processInstanceId+">"+nextPsrson+new Date());

				}else if("2".equals(precheckFlag.trim())){//第三版预检预修工单
					map.put("workerHandleState", "handle");
					map.put("initiatorCheck", nextPsrson);
					BocoLog.info("本地网-预检预修流程-施工队回单手机端处理,服务器IP:"+request.getRemoteAddr()+",端口号:"+request.getRemotePort(), 109,"施工队回单-流程2提交initiatorCheck<"+userId+processInstanceId+">"+nextPsrson+new Date());
//					printlog("4AndroidWorkOrderAction-<transferInterfaceSubmit>", 96, "预检预修-本地网：施工队回单-流程2提交initiatorCheck<"+userId+processInstanceId+">"+nextPsrson+new Date());

					
				}
				BocoLog.info("本地网-预检预修流程-施工队回单手机端处理,服务器IP:"+request.getRemoteAddr()+",端口号:"+request.getRemotePort(), 109,"施工队回单-开始提交流程<"+userId+processInstanceId+">taskId:"+taskId+new Date());
				formService.submitTaskFormData(taskId, map);
//				log.info(this, "预检预修-本地网：施工队回单，setWorkerSceneHandleFlag<成功>；"+new Date());
				BocoLog.info("本地网-预检预修流程-施工队回单手机端处理,服务器IP:"+request.getRemoteAddr()+",端口号:"+request.getRemotePort(), 109,"施工队回单-流程提交完成<"+userId+processInstanceId+">taskId:"+taskId+new Date());
//				printlog("5AndroidWorkOrderAction-<transferInterfaceSubmit>", 96, "预检预修-本地网：施工队回单-流程提交完成<"+userId+processInstanceId+">taskId:"+taskId+new Date());


				//在工单主表中添加流程信息
				processTaskService.setTvalueOfTask(processInstanceId, nextPsrson, pnrTransferOffice, PnrTransferOffice.class, null, null,false);
				pnrTransferOfficeService.save(pnrTransferOffice);
				transferHandleService.save(entity);
				BocoLog.info("本地网-预检预修流程-施工队回单手机端处理,服务器IP:"+request.getRemoteAddr()+",端口号:"+request.getRemotePort(), 109,"施工队回单-处理信息提交完成<"+userId+processInstanceId+">"+new Date());
//				printlog("6AndroidWorkOrderAction-<transferInterfaceSubmit>", 96, "预检预修-本地网：施工队回单-处理信息提交完成<"+userId+processInstanceId+">"+new Date());

				//发短信
				String degree = pnrTransferOffice.getWorkOrderDegree();
				if("101230901".equals(degree)){
					degree="特急";
				}else if("101230902".equals(degree)){
					degree="紧急";
					
				}else if("101230903".equals(degree)){
					degree="一般";
				}
				msg = "预检预修工单已派至您工位待处理： 工单号："+pnrTransferOffice.getProcessInstanceId()+",主题："+
				pnrTransferOffice.getTheme()+",紧急程度："+
				degree+",类型："+
				pnrTransferOffice.getWorkOrderTypeName()+",子类型："+
				pnrTransferOffice.getSubTypeName()+"。";
				CommonUtils.sendMsg(msg, auditor);
			}
		}
//		log.info(this, "预检预修-本地网：施工队回单，单号<"+processInstanceId+">---结束操作；"+new Date());
//		com.boco.eoms.common.log.BocoLog.info("7AndroidWorkOrderAction-<transferInterfaceSubmit>", 96, "预检预修-本地网：施工队回单-结束<"+userId+processInstanceId+">"+new Date());
		BocoLog.info("本地网-预检预修流程-施工队回单手机端处理,服务器IP:"+request.getRemoteAddr()+",端口号:"+request.getRemotePort(), 109,"施工队回单-结束<"+userId+processInstanceId+">"+new Date());
		String returnJson = "[{\"success\":\"true\"}]";
		MobileCommonUtils.responseWrite(response, returnJson, "UTF-8");
	}
	/**
	 * 机房拆除工单提交--工单处理
	 * 1、记录处理的信息
	 * 2、流程：回执taskId，流转方向，接收人
	 * 3、标示当前处理环节、处理的状态（驳回、处理、审批）
	 * 4、短信：通知下一步接收人
	 * 5、日志：先不用记录
	 * 6、返回手机端信息
	  * @title: roomDemolitionSubmit
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @throws Exception void
	 */
	public void roomDemolitionSubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 发短信
		String deadLineTime = "", contact = "";
		
//		* 1、记录处理的信息
		// 主题
		String title = StaticMethod.nullObject2String(request.getParameter("title"));
		// 主题ID
		String titleId = StaticMethod.nullObject2String(request.getParameter("titleId"));
		// 回单人
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		//是否完成
		String isRecover = StaticMethod.nullObject2String(request.getParameter("isRecover"));
		// 回单时间
		Date createTime = new Date();
		// 处理描述
		String mainRemark = StaticMethod.nullObject2String(request.getParameter("mainRemark"));
		// 处理人
		String dealPerformer2 = request.getParameter("dealPerformer2");
		String processInstanceId = StaticMethod.nullObject2String(request.getParameter("processInstanceId"));
		String latitude = StaticMethod.nullObject2String(request.getParameter("latitude"));
		String longitude = StaticMethod.nullObject2String(request.getParameter("longitude"));
		//是否管理
		String energyIsstation=StaticMethod.nullObject2String(request.getParameter("energyIsstation"));
		
//		2、流程：回执taskId，流转方向，接收人		
		String taskId = StaticMethod.nullObject2String(request.getParameter("taskId"));
				
		//获取下一步流程需要的处理标志和审核人
		String handleState = request.getParameter("workerState");
		String nextPsrson = request.getParameter("orderAuditCheck");
//		4、短信：通知下一步接收人
		String theme ="";
		String sheetId = "";
		String stationName ="";
		String sendTime ="";
		
//		 * 2、流程：回执taskId，流转方向，接收人	
		// 流程提交到下一级
		FormService formService = (FormService) getBean("formService");
		TaskFormData taskFormData = formService.getTaskFormData(taskId);
		Map<String, String> map = new HashMap<String, String>();		
	
	    map.put("workerState", handleState);
		map.put("orderAuditCheck", nextPsrson);	
		
		formService.submitTaskFormData(taskId, map);		
		
		// 流程提交到下一级-end
		
		//将部分处理信息,添加到工单主表中
		IRoomDemolitionService roomDemolitionService = (IRoomDemolitionService)getBean("roomDemolitionService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<RoomDemolition> pnrList = roomDemolitionService.search(search);
		String msg="";
		if (pnrList != null) {
			RoomDemolition roomDemolition = pnrList.get(0);
			roomDemolition.setEnergyIsstation(energyIsstation);
			roomDemolition.setLastreplyTime(new Date());
			//在工单主表中添加流程信息
			processTaskService.setTvalueOfTask(processInstanceId, nextPsrson, roomDemolition, RoomDemolition.class, null, null,false);
			roomDemolitionService.save(roomDemolition);
			
			theme = roomDemolition.getTheme();
			sheetId = roomDemolition.getSheetId();
			stationName = roomDemolition.getStationName();
			sendTime = sFormat.format(roomDemolition.getSendTime());
		}
		
		// 1、记录处理的信息
		//将处理信息存入工单处理实体类中--start
		IRoomDemolitionHandleService roomDemolitionHandleService = (IRoomDemolitionHandleService) getBean("roomDemolitionHandleService");
		RoomDemolitionHandle entity = new RoomDemolitionHandle();
		entity.setTheme(title);
		entity.setThemeId(titleId);
		entity.setReceivePerson(userId);
		entity.setAuditor(userId);
		entity.setDoPersons(dealPerformer2);
		entity.setReceiveTime(createTime);
		entity.setHandleDescription(mainRemark);
		entity.setProcessInstanceId(processInstanceId);
		entity.setIsRecover(isRecover);
		entity.setTaskId(taskId);
		entity.setLatitude(latitude);
		entity.setLongitude(longitude);
//      3、标示当前处理环节、处理的状态（驳回、处理、审批）		
		entity.setLinkName("worker");
		entity.setOperation("03");
		roomDemolitionHandleService.save(entity);
		//保存数据--end
			
//	    4、短信：通知下一步接收人	
		String msgStr = RoomDemolitionAction.TASK_MESSAGE + "；" + RoomDemolitionAction.TASK_NO_STR + sheetId
		+ ";" + RoomDemolitionAction.TASK_STATION_NAME + stationName + ";" + RoomDemolitionAction.TASK_SEND_TIME
		+ sendTime + "；回单完毕，请及时处理。" ;
		CommonUtils.sendMsg(msgStr, nextPsrson);
	
		
		String returnJson = "[{\"success\":\"true\"}]";
		MobileCommonUtils.responseWrite(response, returnJson, "UTF-8");
	}
	/**
	 * 传输局--干线预检预修工单提交--工单处理
	 * @author wangyue
	 * @title: transferInterfaceSubmit
	 * @date Oct 22, 2014 4:14:02 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception void
	 */
	public void transferArterySubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		//接收手机端传过来的数据
		// 发短信
		String deadLineTime = "", contact = "";
		
		// 主题
		String title = StaticMethod.nullObject2String(request.getParameter("title"));
		// 主题ID
		String titleId = StaticMethod.nullObject2String(request.getParameter("titleId"));
		// 回单人
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		// 审核人
		String auditor = StaticMethod.nullObject2String(request.getParameter("initiatorCheck"));
		//预检预修是否完成
		String isRecover = StaticMethod.nullObject2String(request.getParameter("isRecover"));
		// 回单时间
		Date createTime = new Date();
		// 处理描述
		String mainRemark = StaticMethod.nullObject2String(request.getParameter("mainRemark"));
		// 处理人
		String dealPerformer2 = request.getParameter("dealPerformer2");
		String taskId = StaticMethod.nullObject2String(request.getParameter("taskId"));
		//预检预修工单号
		String processInstanceId = StaticMethod.nullObject2String(request.getParameter("processInstanceId"));
		
		String latitude = StaticMethod.nullObject2String(request.getParameter("latitude"));
		String longitude = StaticMethod.nullObject2String(request.getParameter("longitude"));
		
		//获取下一步流程需要的处理标志和审核人
		String handleState = request.getParameter("workerHandleState");
		String nextPsrson = request.getParameter("initiatorCheck");
		//将处理信息存入工单处理实体类中--start
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
		entity.setTheme(title);
		entity.setThemeId(titleId);
		entity.setReceivePerson(userId);
		entity.setAuditor(auditor);
		entity.setDoPersons(dealPerformer2);
		entity.setReceiveTime(createTime);
		entity.setHandleDescription(mainRemark);
		entity.setProcessInstanceId(processInstanceId);
		entity.setIsRecover(isRecover);
		entity.setTaskId(taskId);
		entity.setLatitude(latitude);
		entity.setLongitude(longitude);
		entity.setLinkName("worker");
		entity.setOperation("04");
		entity.setCheckTime(new Date());
		
		//将部分处理信息,添加到工单主表中
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService.search(search);
		String msg="";
		if (pnrTicketList != null) {
			PnrTransferOffice pnrTransferOffice = pnrTicketList.get(0);
			pnrTransferOffice.setLastReplyTime(new Date());
			
			deadLineTime = pnrTransferOffice.getEndTime() == null ? ""
					: sFormat.format(pnrTransferOffice.getEndTime());
			contact = pnrTransferOffice.getConnectPerson();
			
			
			String degree = pnrTransferOffice.getWorkOrderDegree();
			if("101230901".equals(degree)){
				degree="特急";
			}else if("101230902".equals(degree)){
				degree="紧急";
				
			}else if("101230903".equals(degree)){
				degree="一般";
			}
			msg = "预检预修工单已派至您工位待处理： 工单号："+pnrTransferOffice.getProcessInstanceId()+",主题："+
			pnrTransferOffice.getTheme()+",紧急程度："+
			degree+",类型："+
			pnrTransferOffice.getWorkOrderTypeName()+",子类型："+
			pnrTransferOffice.getSubTypeName()+"。";
			
			// 流程提交到下一级
			FormService formService = (FormService) getBean("formService");
			TaskFormData taskFormData = formService.getTaskFormData(taskId);
			Map<String, String> map = new HashMap<String, String>();
			String tempKeyValue="";
			String precheckFlag = pnrTicketList.get(0).getVersionFlag();
			map.put("workerHandleState", "handle");
			map.put("initiatorCheck", nextPsrson);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			logger.info("当前的操作人："+userId+";当前的流程:预检预修;当前的操作环节:工单处理（手机端）;当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:{"+tempKeyValue+"};当前时间:"+sdf.format(new Date())+";--start");
			formService.submitTaskFormData(taskId, map);
//			logger.info("当前的操作人："+userId+";当前的流程:预检预修;当前的操作环节:工单处理（手机端）;当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:{"+tempKeyValue+"};当前时间:"+sdf.format(new Date())+";--end");
			
			//在工单主表中添加流程信息
			processTaskService.setTvalueOfTask(processInstanceId, nextPsrson, pnrTransferOffice, PnrTransferOffice.class, null, null,false); 
			pnrTransferOfficeService.save(pnrTransferOffice);
			transferHandleService.save(entity);
		}
		//保存数据--end
		
		// 流程提交到下一级-end
		CommonUtils.sendMsg(msg, auditor);
		String returnJson = "[{\"success\":\"true\"}]";
		
		MobileCommonUtils.responseWrite(response, returnJson, "UTF-8");
	}

	/**
	 * 机房拆除提交--工单验收
	 * 1、记录验收的信息
	 * 2、流程：回执taskId，流转方向，接收人
	 * 3、标示当前处理环节、处理的状态（驳回、处理、审批）
	 * 4、短信：通知下一步接收人
	 * 5、日志：先不用记录了
	 * 6、返回手机端信息
	 * @title: roomDemolitionSpotcheck
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception void
	 */
	public void roomDemolitionSpotcheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
//		 * 1、记录验收的信息
		
		//最近回单信息的id
		String handleId = StaticMethod.nullObject2String(request.getParameter("handleId"));
		// 主题ID
		String state = StaticMethod.nullObject2String(request.getParameter("state"));
		// 回单人
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		
		// 回单时间
		Date insertTime = new Date();
		String processInstanceId = StaticMethod.nullObject2String(request.getParameter("processInstanceId"));
		
		String latitude = StaticMethod.nullObject2String(request.getParameter("latitude"));
		String longitude = StaticMethod.nullObject2String(request.getParameter("longitude"));
		
//		4、短信：通知下一步接收人
		String theme ="";
		String sheetId = "";
		String stationName ="";
		String sendTime ="";
		
//		* 2、流程：回执taskId，流转方向，接收人
		String taskId = StaticMethod.nullObject2String(request.getParameter("taskId"));
		String orderAuditHandleState = request.getParameter("orderAuditHandleState");
		String daiweiAuditCheck = request.getParameter("daiweiAuditCheck");
//-------------------------------------------------------------------------------------------------------------------		
		// 流程提交到下一级
		FormService formService = (FormService) getBean("formService");
		TaskFormData taskFormData = formService.getTaskFormData(taskId);
		Map<String, String> map = new HashMap<String, String>();
		for (FormProperty formProperty : taskFormData.getFormProperties()) {
			if (formProperty.isWritable()) {
				String name = formProperty.getId();
				map.put(name, request.getParameter(name));
			}
		}
		
		formService.submitTaskFormData(taskId, map);				
		// 流程提交到下一级-end
		
		//将部分处理信息,添加到工单主表中
		IRoomDemolitionService roomDemolitionService = (IRoomDemolitionService)getBean("roomDemolitionService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<RoomDemolition> pnrList = roomDemolitionService.search(search);
		if (pnrList != null) {
			RoomDemolition roomDemolition = pnrList.get(0);
			//在工单主表中添加流程信息
			processTaskService.setTvalueOfTask(processInstanceId, daiweiAuditCheck, roomDemolition, RoomDemolition.class, null, null,false);
			roomDemolitionService.save(roomDemolition);
			theme = roomDemolition.getTheme();
			sheetId = roomDemolition.getSheetId();
			stationName = roomDemolition.getStationName();
			sendTime = sFormat.format(roomDemolition.getSendTime());
		}
		//将处理信息存入工单处理实体类中--start
		IRoomDemolitionSpotcheckService roomSpotcheckService = (IRoomDemolitionSpotcheckService) getBean("roomDemolitionSpotcheckService");
		RoomDemolitionSpotcheck entity = new RoomDemolitionSpotcheck();
		entity.setHandleId(handleId);
		entity.setInsertDate(insertTime);
		entity.setState(state);
		entity.setUserId(userId);
		entity.setTaskId(taskId);
		entity.setProcessInstanceId(processInstanceId);
		entity.setLatitude(latitude);
		entity.setLongitude(longitude);
		roomSpotcheckService.save(entity);
		//保存数据--end
		
		String msgStr = RoomDemolitionAction.TASK_MESSAGE + "；" + RoomDemolitionAction.TASK_NO_STR + sheetId
		+ ";" + RoomDemolitionAction.TASK_STATION_NAME + stationName + ";" + RoomDemolitionAction.TASK_SEND_TIME
		+ sendTime + "；验收完毕，请及时处理。" ;
		CommonUtils.sendMsg(msgStr, daiweiAuditCheck);
		
		String returnJson = "[{\"success\":\"true\"}]";		
		MobileCommonUtils.responseWrite(response, returnJson, "UTF-8");
	}
	/**
	 * 传输局--预检预修工单提交--工单抽查
	  * @author wangyue
	  * @title: transferInterfaceSubmit
	  * @date Oct 22, 2014 4:14:02 PM
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @throws Exception void
	 */
	public void transferSpotcheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//接收手机端传过来的数据
		String handleId = StaticMethod.nullObject2String(request.getParameter("handleId"));
		// 主题ID
		String state = StaticMethod.nullObject2String(request.getParameter("state"));
		// 回单人
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();

		// 回单时间
		Date insertTime = new Date();
		
		String taskId = StaticMethod.nullObject2String(request.getParameter("taskId"));
		String processInstanceId = StaticMethod.nullObject2String(request.getParameter("processInstanceId"));
		
		String latitude = StaticMethod.nullObject2String(request.getParameter("latitude"));
		String longitude = StaticMethod.nullObject2String(request.getParameter("longitude"));
		
		//保存流转信息
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		PnrTransferOfficeHandle handleEntity = new PnrTransferOfficeHandle();
		handleEntity.setReceiveTime(new Date());
		handleEntity.setReceivePerson(userId);
		handleEntity.setHandleDescription("手机端市公司抽检");
		handleEntity.setProcessInstanceId(processInstanceId);
		handleEntity.setTaskId(taskId);
		handleEntity.setAuditor(userId);
		handleEntity.setCheckTime(new Date());
		//handleEntity.setOpinion(samplingOpinion);//抽检意见
		handleEntity.setLinkName("orderAudit");
		handleEntity.setOperation("01");
		transferHandleService.save(handleEntity);
		
		//将处理信息存入工单处理实体类中--start
		IPnrTransferSpotcheckService transferSpotcheckService = (IPnrTransferSpotcheckService) getBean("pnrTransferSpotcheckService");
		PnrTransferSpotcheck entity = new PnrTransferSpotcheck();
		entity.setHandleId(handleId);
		entity.setInsertDate(insertTime);
		entity.setState(state);
		entity.setUserId(userId);
		entity.setTaskId(taskId);
		entity.setProcessInstanceId(processInstanceId);
		entity.setLatitude(latitude);
		entity.setLongitude(longitude);
		
		//将部分处理信息,添加到工单主表中
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService.search(search);
		String msg="";
		if (pnrTicketList != null) {
			PnrTransferOffice pnrTransferOffice = pnrTicketList.get(0);
			//pnrTransferOffice.setArchivingTime(new Date());
			//pnrTransferOffice.setState(5);
			pnrTransferOffice.setState(10);
			pnrTransferOffice.setWorkerSceneOrderAuditFlag("1");
			pnrTransferOfficeService.save(pnrTransferOffice);
			
			printlog("1AndroidWorkOrderAction-<transferSpotcheck>", 96, "预检预修-本地网：抽查回单-开始操作<"+userId+processInstanceId+">"+new Date());

			
		}
		transferSpotcheckService.save(entity);
		printlog("2AndroidWorkOrderAction-<transferSpotcheck>", 96, "预检预修-本地网：抽查回单-结束操作<"+userId+processInstanceId+">"+new Date());

		//保存数据--end
		
//		// 流程提交到下一级
//		FormService formService = (FormService) getBean("formService");
//		TaskFormData taskFormData = formService.getTaskFormData(taskId);
//		Map<String, String> map = new HashMap<String, String>();
//		String tempKeyValue="";
//		for (FormProperty formProperty : taskFormData.getFormProperties()) {
//			if (formProperty.isWritable()) {
//				String name = formProperty.getId();
//				map.put(name, request.getParameter(name));
//				tempKeyValue+=name+":"+request.getParameter(name)+";";
//			}
//		}
//		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		logger.info("当前的操作人："+userId+";当前的流程:预检预修;当前的操作环节:工单抽查（手机端）;当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:{"+tempKeyValue+"};当前时间:"+sdf.format(new Date())+";--start");
//		formService.submitTaskFormData(taskId, map);
//		logger.info("当前的操作人："+userId+";当前的流程:预检预修;当前的操作环节:工单抽查（手机端）;当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:{"+tempKeyValue+"};当前时间:"+sdf.format(new Date())+";--end");
//		
		
		// 流程提交到下一级-end
		String returnJson = "[{\"success\":\"true\"}]";

		MobileCommonUtils.responseWrite(response, returnJson, "UTF-8");
	}
	/**
	 * 传输局--干线预检预修工单提交--工单抽查
	 * @author wangyue
	 * @title: transferInterfaceSubmit
	 * @date Oct 22, 2014 4:14:02 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception void
	 */
	public void transferArterySpotcheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		//接收手机端传过来的数据
		
		String handleId = StaticMethod.nullObject2String(request.getParameter("handleId"));
		// 主题ID
		String state = StaticMethod.nullObject2String(request.getParameter("state"));
		// 回单人
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		
		// 回单时间
		Date insertTime = new Date();
		
		String taskId = StaticMethod.nullObject2String(request.getParameter("taskId"));
		String processInstanceId = StaticMethod.nullObject2String(request.getParameter("processInstanceId"));
		
		String latitude = StaticMethod.nullObject2String(request.getParameter("latitude"));
		String longitude = StaticMethod.nullObject2String(request.getParameter("longitude"));
		
		//保存流转信息
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		PnrTransferOfficeHandle handleEntity = new PnrTransferOfficeHandle();
		handleEntity.setReceiveTime(new Date());
		handleEntity.setReceivePerson(userId);
		handleEntity.setHandleDescription("手机端市公司抽检");
		handleEntity.setProcessInstanceId(processInstanceId);
		handleEntity.setTaskId(taskId);
		handleEntity.setAuditor(userId);
		handleEntity.setCheckTime(new Date());
		//handleEntity.setOpinion(samplingOpinion);//抽检意见
		handleEntity.setLinkName("orderAudit");
		handleEntity.setOperation("01");
		transferHandleService.save(handleEntity);
		
		//将处理信息存入工单处理实体类中--start
		IPnrTransferSpotcheckService transferSpotcheckService = (IPnrTransferSpotcheckService) getBean("pnrTransferSpotcheckService");
		PnrTransferSpotcheck entity = new PnrTransferSpotcheck();
		entity.setHandleId(handleId);
		entity.setInsertDate(insertTime);
		entity.setState(state);
		entity.setUserId(userId);
		entity.setTaskId(taskId);
		entity.setProcessInstanceId(processInstanceId);
		entity.setLatitude(latitude);
		entity.setLongitude(longitude);
		
		//将部分处理信息,添加到工单主表中
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService.search(search);
		String msg="";
		if (pnrTicketList != null) {
			PnrTransferOffice pnrTransferOffice = pnrTicketList.get(0);
			//pnrTransferOffice.setArchivingTime(new Date());
			//pnrTransferOffice.setState(5);
			pnrTransferOffice.setState(10);
			pnrTransferOffice.setWorkerSceneOrderAuditFlag("1");
			pnrTransferOfficeService.save(pnrTransferOffice);
			
		}
		transferSpotcheckService.save(entity);
		//保存数据--end
		
		// 流程提交到下一级
//		FormService formService = (FormService) getBean("formService");
//		TaskFormData taskFormData = formService.getTaskFormData(taskId);
//		Map<String, String> map = new HashMap<String, String>();
//		String tempKeyValue="";
//		for (FormProperty formProperty : taskFormData.getFormProperties()) {
//			if (formProperty.isWritable()) {
//				String name = formProperty.getId();
//				map.put(name, request.getParameter(name));
//				tempKeyValue+=name+":"+request.getParameter(name)+";";
//			}
//		}
//		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		logger.info("当前的操作人："+userId+";当前的流程:预检预修;当前的操作环节:工单抽查（手机端）;当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:{"+tempKeyValue+"};当前时间:"+sdf.format(new Date())+";--start");
//		formService.submitTaskFormData(taskId, map);
//		logger.info("当前的操作人："+userId+";当前的流程:预检预修;当前的操作环节:工单抽查（手机端）;当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:{"+tempKeyValue+"};当前时间:"+sdf.format(new Date())+";--end");
//		
		
		// 流程提交到下一级-end
		String returnJson = "[{\"success\":\"true\"}]";
		
		MobileCommonUtils.responseWrite(response, returnJson, "UTF-8");
	}
	
	/**
	 * 传输局--大修改造工单提交--工单处理
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void overHaulSubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		//接收手机端传过来的数据
		// 发短信
		String deadLineTime = "", contact = "";
		
		// 主题
		String title = StaticMethod.nullObject2String(request.getParameter("title"));
		// 主题ID
		String titleId = StaticMethod.nullObject2String(request.getParameter("titleId"));
		// 回单人
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		// 审核人
		String auditor = StaticMethod.nullObject2String(request.getParameter("initiatorCheck"));
		//预检预修是否完成
		String isRecover = StaticMethod.nullObject2String(request.getParameter("isRecover"));
		// 回单时间
		Date createTime = new Date();
		// 处理描述
		String mainRemark = StaticMethod.nullObject2String(request.getParameter("mainRemark"));
		// 处理人
		String dealPerformer2 = request.getParameter("dealPerformer2");
		String taskId = StaticMethod.nullObject2String(request.getParameter("taskId"));
		//预检预修工单号
		String processInstanceId = StaticMethod.nullObject2String(request.getParameter("processInstanceId"));
		
		String latitude = StaticMethod.nullObject2String(request.getParameter("latitude"));
		String longitude = StaticMethod.nullObject2String(request.getParameter("longitude"));
		
		//获取下一步流程需要的处理标志和审核人
		String handleState = request.getParameter("workerHandleState");
		String nextPsrson = request.getParameter("initiatorCheck");
		
		//将处理信息存入工单处理实体类中--start
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
		entity.setTheme(title);
		entity.setThemeId(titleId);
		entity.setReceivePerson(userId);
		entity.setAuditor(auditor);
		entity.setDoPersons(dealPerformer2);
		entity.setReceiveTime(createTime);
		entity.setHandleDescription(mainRemark);
		entity.setProcessInstanceId(processInstanceId);
		entity.setIsRecover(isRecover);
		entity.setTaskId(taskId);
		entity.setLatitude(latitude);
		entity.setLongitude(longitude);
		entity.setLinkName("worker");
		entity.setOperation("04");
		transferHandleService.save(entity);
		
		//将部分处理信息,添加到工单主表中
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService.search(search);
		String msg="";
		
		// 流程提交到下一级
		FormService formService = (FormService) getBean("formService");
		TaskFormData taskFormData = formService.getTaskFormData(taskId);
		Map<String, String> map = new HashMap<String, String>();
		String tempKeyValue="";
		
		if (pnrTicketList != null) {
			String precheckFlag = pnrTicketList.get(0).getVersionFlag();
				map.put("workerHandleState", "handle");
				map.put("initiatorCheck", nextPsrson);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		logger.info("当前的操作人："+userId+";当前的流程:大修改造;当前的操作环节:施工队现场处理（手机端）;当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:{"+tempKeyValue+"};当前时间:"+sdf.format(new Date())+";--start");
		formService.submitTaskFormData(taskId, map);
//		logger.info("当前的操作人："+userId+";当前的流程:大修改造;当前的操作环节:施工队现场处理（手机端）;当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:{"+tempKeyValue+"};当前时间:"+sdf.format(new Date())+";--end");
		if (pnrTicketList != null) {
			PnrTransferOffice pnrTransferOffice = pnrTicketList.get(0);
			pnrTransferOffice.setLastReplyTime(new Date());
			//在工单主表中添加流程信息
			processTaskService.setTvalueOfTask(processInstanceId, nextPsrson, pnrTransferOffice, PnrTransferOffice.class, null, null,false);
			pnrTransferOfficeService.save(pnrTransferOffice);
			
			deadLineTime = pnrTransferOffice.getEndTime() == null ? ""
					: sFormat.format(pnrTransferOffice.getEndTime());
			contact = pnrTransferOffice.getConnectPerson();
			
			String degree = CommonUtils.getId2NameString(pnrTransferOffice.getWorkOrderDegree(),1, ",");
			
			msg = "预检预修工单已派至您工位待处理： 工单号："+pnrTransferOffice.getProcessInstanceId()+",主题："+
			pnrTransferOffice.getTheme()+",紧急程度："+
			degree+",类型："+
			pnrTransferOffice.getWorkOrderTypeName()+",子类型："+
			pnrTransferOffice.getSubTypeName()+"。";
		}
		//保存数据--end
		
		// 流程提交到下一级-end
		CommonUtils.sendMsg(msg, auditor);
		String returnJson = "[{\"success\":\"true\"}]";
		
		MobileCommonUtils.responseWrite(response, returnJson, "UTF-8");
	}
	
	/**
	 * 传输局--大修改造工单提交--工单抽查
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void overHaulSpotcheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		//接收手机端传过来的数据		
		String handleId = StaticMethod.nullObject2String(request.getParameter("handleId"));
		// 主题ID
		String state = StaticMethod.nullObject2String(request.getParameter("state"));
		// 回单人
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		
		// 回单时间
		Date insertTime = new Date();
		
		String taskId = StaticMethod.nullObject2String(request.getParameter("taskId"));
		String processInstanceId = StaticMethod.nullObject2String(request.getParameter("processInstanceId"));
		
		String latitude = StaticMethod.nullObject2String(request.getParameter("latitude"));
		String longitude = StaticMethod.nullObject2String(request.getParameter("longitude"));
		
		//将处理信息存入工单处理实体类中--start
		IPnrTransferSpotcheckService transferSpotcheckService = (IPnrTransferSpotcheckService) getBean("pnrTransferSpotcheckService");
		PnrTransferSpotcheck entity = new PnrTransferSpotcheck();
		entity.setHandleId(handleId);
		entity.setInsertDate(insertTime);
		entity.setState(state);
		entity.setUserId(userId);
		entity.setTaskId(taskId);
		entity.setProcessInstanceId(processInstanceId);
		entity.setLatitude(latitude);
		entity.setLongitude(longitude);
		
		//将部分处理信息,添加到工单主表中
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService.search(search);
		String msg="";
		if (pnrTicketList != null) {
			PnrTransferOffice pnrTransferOffice = pnrTicketList.get(0);
			//pnrTransferOffice.setArchivingTime(new Date());
			//pnrTransferOffice.setState(5);
			pnrTransferOffice.setState(10);
			pnrTransferOfficeService.save(pnrTransferOffice);
			
		}
		transferSpotcheckService.save(entity);
		//保存数据--end
		
		// 流程提交到下一级
//		FormService formService = (FormService) getBean("formService");
//		TaskFormData taskFormData = formService.getTaskFormData(taskId);
//		Map<String, String> map = new HashMap<String, String>();
//		String tempKeyValue="";
//		for (FormProperty formProperty : taskFormData.getFormProperties()) {
//			if (formProperty.isWritable()) {
//				String name = formProperty.getId();
//				map.put(name, request.getParameter(name));
//				tempKeyValue+=name+":"+request.getParameter(name)+";";
//			}
//		}
//		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		logger.info("当前的操作人："+userId+";当前的流程:大修改造;当前的操作环节:市运维主管抽查（手机端）;当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:{"+tempKeyValue+"};当前时间:"+sdf.format(new Date())+";--start");
//		formService.submitTaskFormData(taskId, map);
//		logger.info("当前的操作人："+userId+";当前的流程:大修改造;当前的操作环节:运维主管抽查（手机端）;当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:{"+tempKeyValue+"};当前时间:"+sdf.format(new Date())+";--end");
		
		
		// 流程提交到下一级-end
		String returnJson = "[{\"success\":\"true\"}]";
		
		MobileCommonUtils.responseWrite(response, returnJson, "UTF-8");
	}

	/*
	 * 保存图片单张
	 */

	public void saveOnePhoto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DiskFileItemFactory difactory = new DiskFileItemFactory();
		difactory.setSizeThreshold(1024 * 1024);
		ServletFileUpload upload = new ServletFileUpload(difactory);
		upload.setSizeMax(1024 * 3024); // 设置上传文件的最大容量
		try {
			String picture_id = "";
			String createTime = "";
			String longitude = "";
			String latitude = "";
			String faultLocation = "";
			String faultDescription = "";
			String photoType = "";//照片类型
			/**
			 * 一级分类：work_order_type_id 
			   二级分类：second_work_order_type_id
			 */
			String photoSubType = "";//二级分类
			
		    String fileName = StaticMethod
    		.getCurrentDateTime("yyyyMMddHHmmss")+StaticMethod.getRandomCharAndNumr(4);
    		
    		String tPath = "repair"+ File.separatorChar + fileName.substring(0, 4) + File.separatorChar 
    		+ fileName.substring(4,6)+ File.separatorChar + fileName.substring(6, 8)+ File.separatorChar;
		    tPath = tPath.replace("\\", "/");

		    //String remotePhotoUrl = StaticVariable.IMG_WORKSHEET_PUBLIC_URL; //存放图片的共享目录  
		    String remotePhotoUrl = CommonUtils.getImgWorksheetPublicUrl(); //存放图片的共享目录  

		    String placePath = remotePhotoUrl+tPath;
		    
            String nameStr = "";// 文件名（带路径）
			List<FileItem> items = upload.parseRequest(request); // 取得表单全部数据
			PnrAndroidPhotoFile file = new PnrAndroidPhotoFile();
			for (FileItem item : items) {
				if (!item.isFormField()) {
					String comeFileName = item.getName();					
					nameStr = tPath+comeFileName;			
					InputStream in = null;  
					OutputStream out = null;  
						try {  
							    SmbFile remoteFile2 = new SmbFile(placePath);  
							    if(!remoteFile2.exists()){
							    	remoteFile2.mkdirs();
							    }
							    SmbFile remoteFile = new SmbFile(placePath+ item.getName());  
							    remoteFile.connect(); //尝试连接   
							    in = item.getInputStream();
						        out = new BufferedOutputStream(new SmbFileOutputStream(remoteFile));  
							 
							   byte[] buffer = new byte[4096];  
							   int len = 0; //读取长度   
							   while ((len = in.read(buffer, 0, buffer.length)) != -1) {  
								   out.write(buffer, 0, len);  
								   }  
							    out.flush(); //刷新缓冲的输出流   
	//							    System.out.println("--done-");
						} catch (Exception e) {  
						   String msg = "现场抢修上传-发生错误：" +placePath+ e.getLocalizedMessage();  
						    System.out.println(msg); 
						    MobileCommonUtils.responseWrite(response,
									MobileConstants.failureStr, "UTF-8");
						
							return;
						}  
						finally {  
						  try {  
						      if(out != null) {  
							          out.close();  
							       }  
						        if(in != null) {  
						           in.close();  
						        }  
						  	}  catch (Exception e) {}  
						}  
						//				
					
				} else {

					String name = item.getFieldName();
					if ("picture_id".equals(name)) {
						picture_id = item.getString();
					}else if ("longitude".equals(name)) {
						longitude = item.getString();
					}else if ("latitude".equals(name)) {
						latitude = item.getString();
					}else if ("faultLocation".equals(name)) {
						faultLocation = new String(item.getString().getBytes(
								"ISO8859_1"), "utf-8");
					}else if ("faultDescription".equals(name)) {
						faultDescription = new String(item.getString()
								.getBytes("ISO8859_1"), "utf-8");
					}else if ("work_order_type_id".equals(name)) {
						photoType = item.getString();
					}else if ("second_work_order_type_id".equals(name)) {
						photoSubType = item.getString();
					}else if ("photoCreateTime".equals(name)) {
						createTime = item.getString();
					}
				}
			}
			if ("".equals(picture_id)) {
				MobileCommonUtils.responseWrite(response,
						MobileConstants.failureStr, "UTF-8");
				/*if (tempFile.exists()) {
					tempFile.delete();
				}*/
				return;
			}
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request
			.getSession().getAttribute("sessionform");
	
			ITawSystemDeptManager deptSys = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
			String deptIdString = "";
			String userId = sessionform.getUserid();
			String deptId = sessionform.getDeptid();
			if (deptId.length() >= 5) {
				deptIdString = deptId.substring(0, 5);
			}
			TawSystemDept list = deptSys.getDeptinfobydeptid(deptIdString, "0");
			String areaId = "";
			if (list != null) {
				areaId = list.getAreaid();
			}
			
			//保存为照片上传时间 ----20160510修改----
			if (!"".equals(createTime)) {
				SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date parse = sFormat.parse(createTime);
		        file.setCreateTime(sFormat.format(parse)); //保存为照片上传时间
		    }else{
		    	file.setCreateTime(sFormat.format(new Date())); //保存为照片上传时间
		    }
//			System.out.println("***********************createTime:"+createTime);
			//file.setCreateTime(StaticMethod.getCurrentDateTime());
			file.setSystemTime(new Date());//系统时间 ----20160510修改----
			file.setPicture_id(picture_id);
			file.setLatitude(latitude);
			file.setLongitude(longitude);
			file.setFaultLocation(faultLocation);
			file.setFaultDescription(faultDescription);
			file.setUserId(userId);
			file.setDeptId(deptId);
			file.setCity(areaId);
		    file.setPath(nameStr);
		    file.setState("0");
		    file.setPhotoType(photoType);//工单类型
		    file.setPhotoSubType(photoSubType);//工单类型-二级

			if (CommonSqlHelper.isOracleDialect()) {
				PnrInspectTaskFileDaoHibernate dao = (PnrInspectTaskFileDaoHibernate) getBean("pnrInspectTaskFileDao");
				Session session = dao.getHibernateTemplate()
						.getSessionFactory().openSession();
				Transaction tx = session.beginTransaction();
				file.setFileData(null);
				session.saveOrUpdate(file);
				session.flush();
				session.refresh(file, LockMode.UPGRADE);
				/*SerializableClob cb = (SerializableClob) file.getFileData();
				Clob wrapClob = (Clob) cb.getWrappedClob();
				if (wrapClob instanceof CLOB) {
					CLOB clob = (CLOB) wrapClob;
					clob.putString(1, PartnerUtil
							.convertImageDataToBASE64(tempFile.getPath()));
				}*/
				tx.commit();
				session.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
			MobileCommonUtils.responseWrite(response,
					MobileConstants.failureStr, "UTF-8");
		}
		MobileCommonUtils.responseWrite(response, MobileConstants.successStr,
				"UTF-8");

	}

	/**
	 * 查询现场抢修列表
	 */
	public ActionForward gotoDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat sFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		String faultLocation = request.getParameter("faultLocation");
		String faultDescription = request.getParameter("faultDescription");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		if (beginTime == null || beginTime.equals("")) {
			//beginTime = sFormat.format(new Date());
			beginTime = sFormat1.format(new Date())+" 00:00:00";
		}
		if (endTime == null || endTime.equals("")) {
//			Calendar calendar = Calendar.getInstance();
//			calendar.add(Calendar.HOUR_OF_DAY, 1);
//			endTime = sFormat.format(calendar.getTime());
			endTime = sFormat1.format(new Date())+" 23:59:59";
		}
		IPnrAndroidMgr mgr = (IPnrAndroidMgr) ApplicationContextHolder
				.getInstance().getBean("ipnrAndroidMgrImpl");
		Search search = new Search();
		if (faultLocation != null && !faultLocation.equals("")) {
			search.addFilterILike("faultLocation", faultLocation);
		}
		if (faultDescription != null && !faultDescription.equals("")) {
			search.addFilterILike("faultDescription", faultDescription);
		}
		search.addFilterLessOrEqual("createTime", endTime);
		search.addFilterGreaterOrEqual("createTime", beginTime);
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		ITawSystemDeptManager deptSys = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
		String deptIdString = "";
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		if (deptId.length() >= 5) {
			deptIdString = deptId.substring(0, 5);
		}
		if (!userId.equals("admin")) {

			TawSystemDept list2 = deptSys
					.getDeptinfobydeptid(deptIdString, "0");
			String areaId = "";
			if (list2 != null) {
				areaId = list2.getAreaid();
			}
			search.addFilterEqual("city", areaId);
		}
		search.addSortDesc("createTime");

		SearchResult t = mgr.searchAndCount(search);
		int size = 0;
		List<PnrAndroidPhotoFile> list = null;
		if (t != null) {
			list = t.getResult();
			size = t.getTotalCount();
		}

		request.setAttribute("pnrAndroidPhotoFileList", list);
		request.setAttribute("listsize", size);
		request.setAttribute("pageSize", CommonUtils.PAGE_SIZE);
		request.setAttribute("beginTime", beginTime);
		request.setAttribute("endTime", endTime);
		request.setAttribute("faultLocation", faultLocation);
		request.setAttribute("faultDescription", faultDescription);

		return mapping.findForward("gotoDetail");

	}

	/**
	 * 手机工单 图片保存
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void saveWorkOderPhoto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DiskFileItemFactory difactory;
		try {
			difactory = new DiskFileItemFactory();
			difactory.setSizeThreshold(1024 * 1024);
			ServletFileUpload upload = new ServletFileUpload(difactory);
			upload.setSizeMax(1024 * 3024); // 设置上传文件的最大容量
			String picture_id = "", idType = "", createTime = "";
			String latitude = "";
	        String longitude = "";
	        String distance ="";
	        String photoType = "";//照片类型
			/**
			 * 一级分类：work_order_type_id 
			   二级分类：second_work_order_type_id
			 */
			String photoSubType = "";//二级分类

	        String fileName = StaticMethod
    		.getCurrentDateTime("yyyyMMddHHmmss")+StaticMethod.getRandomCharAndNumr(4);
    		
    		String tPath = "worksheet"+ File.separatorChar + fileName.substring(0, 4) + File.separatorChar 
    		+ fileName.substring(4,6)+ File.separatorChar + fileName.substring(6, 8)+ File.separatorChar;
		    tPath = tPath.replace("\\", "/");

		    //String remotePhotoUrl = StaticVariable.IMG_WORKSHEET_PUBLIC_URL; //存放图片的共享目录  
		    String remotePhotoUrl = CommonUtils.getImgWorksheetPublicUrl(); //存放图片的共享目录  

		    String placePath = remotePhotoUrl+tPath;
		    
            String nameStr = "";// 文件名（带路径）
  
			List<FileItem> items = upload.parseRequest(request); // 取得表单全部数据
			PnrAndroidWorkOderPhotoFile file = new PnrAndroidWorkOderPhotoFile();
			for (FileItem item : items) {
				if (!item.isFormField()) {
					String comeFileName = item.getName();					
					nameStr = tPath+comeFileName;			
					InputStream in = null;  
					OutputStream out = null;  
						try {  
							    SmbFile remoteFile2 = new SmbFile(placePath);  
							    if(!remoteFile2.exists()){
							    	remoteFile2.mkdirs();
							    }
							    SmbFile remoteFile = new SmbFile(placePath+ item.getName());  
							    remoteFile.connect(); //尝试连接   
							    in = item.getInputStream();
						        out = new BufferedOutputStream(new SmbFileOutputStream(remoteFile));  
							 
							   byte[] buffer = new byte[4096];  
							   int len = 0; //读取长度   
							   while ((len = in.read(buffer, 0, buffer.length)) != -1) {  
								   out.write(buffer, 0, len);  
								   }  
							    out.flush(); //刷新缓冲的输出流   
	//							    System.out.println("--done-");
						} catch (Exception e) {  
						   String msg = "手机工单上传-发生错误：" +placePath+ e.getLocalizedMessage();  
						    System.out.println(msg); 
						    MobileCommonUtils.responseWrite(response,
									MobileConstants.failureStr, "UTF-8");
						
							return;
						}  
						finally {  
						  try {  
						      if(out != null) {  
							          out.close();  
							       }  
						        if(in != null) {  
						           in.close();  
						        }  
						  	}  catch (Exception e) {}  
						}  
				} else {
					String name = item.getFieldName();
					if ("picture_id".equals(name)) {
						picture_id = item.getString();
					}else if ("idType".equals(name)) {
						idType = item.getString();
					}else if ("createTime".equals(name)) {
						createTime = item.getString();
					}else if ("latitude".equals(name)) {
						latitude = item.getString();
					}else if ("longitude".equals(name)) {
						longitude = item.getString();
					}else if ("distance".equals(name)) {
						distance = item.getString();
					}else if ("work_order_type_id".equals(name)) {
						photoType = item.getString();
					}else if ("second_work_order_type_id".equals(name)) {
						photoSubType = item.getString();
					}

				}
			}
			if ("".equals(picture_id)) {
				MobileCommonUtils.responseWrite(response,
						MobileConstants.failureStr, "UTF-8");
				
				return;
			}

			file.setPicture_id(picture_id);
			
			if (!("".equals(createTime))) {
		        Object sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        file.setCreateTime(((DateFormat)sFormat).parse(createTime));
		      }
//		  
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request
			.getSession().getAttribute("sessionform");
				String userId = sessionform.getUserid();
				
			  file.setUserId(userId);
		      file.setPath(nameStr);
		      file.setImgPath(nameStr);
		      file.setState(2);//代表 接口看图片的方式
		      file.setLatitude(latitude);
		      file.setLongitude(longitude);
		      file.setSystemTime(new Date());
		      file.setDistance(distance);
		      file.setPhotoType(photoType);
		      file.setPhotoSubType(photoSubType);
		      
			if (CommonSqlHelper.isOracleDialect()) {
				PnrInspectTaskFileDaoHibernate dao = (PnrInspectTaskFileDaoHibernate) getBean("pnrInspectTaskFileDao");
				Session session = dao.getHibernateTemplate()
						.getSessionFactory().openSession();
				Transaction tx = session.beginTransaction();
				file.setFileData(null);
				file.setId_type(idType);
				session.saveOrUpdate(file);
				session.flush();
				session.refresh(file, LockMode.UPGRADE);
//		        15-04-08-modify
				/*SerializableClob cb = (SerializableClob) file.getFileData();
				Clob wrapClob = (Clob) cb.getWrappedClob();
				if (wrapClob instanceof CLOB) {
					CLOB clob = (CLOB) wrapClob;
					clob.putString(1, PartnerUtil
							.convertImageDataToBASE64(tempFile.getPath()));
				}*/
				tx.commit();
				session.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		MobileCommonUtils.responseWrite(response, MobileConstants.successStr,
				"UTF-8");

	}

	/**
	 * 图片展示
	 */

	@SuppressWarnings("unchecked")
	public ActionForward showPicture(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		String pageIndexString = request.getParameter("curPage"); // 得到当前是第几张
		IPnrAndroidMgr mgr = (IPnrAndroidMgr) ApplicationContextHolder
				.getInstance().getBean("ipnrAndroidMgrImpl");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		PnrAndroidPhotoFile taskFile = new PnrAndroidPhotoFile();
		Map map = mgr.getResources(firstResult, 1, " where id='" + id + "'");
		List<PnrAndroidPhotoFile> list = (List<PnrAndroidPhotoFile>) map
				.get("result");
		if (list != null && list.size() > 0) {
			taskFile = (PnrAndroidPhotoFile) list.get(0);
		}
		
		String path = taskFile.getPath();
    	//path = "C:\\Users\\Administrator\\Desktop\\帅哥\\201412311116341.jpg";
    	//path = "C:\\Users\\Administrator\\"+path;
	    String patch = path;  
	    
	    /*//获取盘符
	    String strClassName = getClass().getName();
	    String strPackageName="";
	    if(getClass().getPackage()!=null){
	    	strPackageName = getClass().getPackage().getName();
	    }
	    String strFileName = "";
	    if(!"".equals(strPackageName)){
	    	strFileName = strClassName.substring(strPackageName.length()+1,strClassName.length());
	    }else{
	    	strFileName = strClassName;
	    }
	    URL url =null ;
	    url = getClass().getResource(strFileName+".class");
	    String strUrl = url.toString();
	    strUrl = strUrl.substring(strUrl.indexOf('/')+1,strUrl.lastIndexOf('W'));
	    
	    String flag = File.separator;
	    if("\\".equals(flag)){//windows环境下的分隔符是\\
	    	patch = strUrl.substring(0,2)+patch;
	    }else if("/".equals(flag)){//Linux环境下的分隔符是/
//	    	patch = "/"+strUrl+patch;
	    	patch = patch;
	    }
	    //肉哥思路
	    FileInputStream is = new FileInputStream(patch);  
		int i = is.available(); // 得到文件大小  
		byte data[] = new byte[i];  
		is.read(data); // 读数据  
		is.close();  
		response.setContentType("image/*"); // 设置返回的文件类型  
		OutputStream toClient = response.getOutputStream(); // 得到向客户端输出二进制数据的对象  
		toClient.write(data); // 输出数据  
		toClient.close(); */
	    
	    InputStream in = null ;
        ByteArrayOutputStream out = null ;
        try {
            //创建远程文件对象
            //String remotePhotoUrl = StaticVariable.IMG_WORKSHEET_PUBLIC_URL+patch;
        	String remotePhotoUrl = CommonUtils.getImgWorksheetPublicUrl()+patch;
            SmbFile remoteFile = new SmbFile(remotePhotoUrl);
            remoteFile.connect(); //尝试连接
            //创建文件流
            in = new BufferedInputStream(new SmbFileInputStream(remoteFile));
            out = new ByteArrayOutputStream((int)remoteFile.length());
            //读取文件内容
            byte[] buffer = new byte[4096];
            int len = 0; //读取长度
            while ((len = in.read(buffer, 0, buffer.length)) != - 1) {
                out.write(buffer, 0, len);
            }

            out.flush(); //刷新缓冲的输出流
           
            response.setContentType("image/*"); // 设置返回的文件类型  
    		OutputStream toClient = response.getOutputStream(); // 得到向客户端输出二进制数据的对象  
    		toClient.write(out.toByteArray()); // 输出数据  
    		toClient.close(); 
        }
        catch (Exception e) {
            String msg = "现场抢修-下载远程文件出错：" + e.getLocalizedMessage();
            System.out.println(msg);
        }
        finally {
            try {
                if(out != null) {
                    out.close();
                }
                if(in != null) {
                    in.close();
                }
            }
            catch (Exception e) {}
        }
		
		 return null;
		/*java.sql.Clob clob = taskFile.getFileData();

		if (clob != null) {
			File tempFile = File.createTempFile(new Date().getTime() + "",
					"tmp");
			try {
				response.setHeader("Pragma", "no-cache");
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0);
				response.setContentType("image/jpeg");
				int len;
				char[] buf = new char[255];
				Reader reader = clob.getCharacterStream();
				FileOutputStream fos = new FileOutputStream(tempFile);
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
						fos));
				while ((len = reader.read(buf)) > 0) {
					bw.write(buf, 0, len);
					bw.flush();
				}
				bw.close();
				FileInputStream fis = new FileInputStream(tempFile);
				BASE64Decoder base64 = new BASE64Decoder();
				base64.decodeBuffer(fis, response.getOutputStream());
			} finally {
				tempFile.deleteOnExit();
			}

		}
		return null;*/
	}
	//手机端回复时，设置回复时间
	private Date setReplyTime(String processInstanceId){
		 Date  date =null;
		 
		 androidMgr= (IPnrAndroidWorkOderPhotoFileService) getBean("pnrAndroidWorkOderPhotoFileService");
		 
		 Search search = new Search();		 
		 search.addFilterEqual("picture_id", processInstanceId);
		 search.addFilterNotNull("createTime");
		 search.addSortDesc("createTime");
		 
		 List<PnrAndroidWorkOderPhotoFile> fList = androidMgr.search(search);
		
		 if(fList !=null && fList.size()>0){
			 date = fList.get(0).getCreateTime();
		 }
		 		 		 
		 return date;
	}
	
	
	/**
	 * 通过当前登录人获取查询条件中的地市和区县的下拉选的值
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void getCityAndCountryByUserId(ActionMapping mapping, ActionForm form,HttpServletRequest request,
			HttpServletResponse response) {
		//1.获取当前登录用户地市的值
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		ITawSystemDeptManager deptSys = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
		TawSystemDept tawSystemDept = deptSys.getDeptinfobydeptid(sessionForm.getDeptid(), "0");
		String areaid = tawSystemDept.getAreaid();
		ITawSystemAreaManager mgr = (ITawSystemAreaManager) ApplicationContextHolder.getInstance().getBean("ItawSystemAreaManager");
		JSONObject areaJson = new JSONObject();
			JSONArray citys = new JSONArray();//保存地市
				JSONArray cityIds = new JSONArray();
				cityIds.put("");
				JSONArray cityNames = new JSONArray();
				cityNames.put("请选择");
	
			JSONArray countrys = new JSONArray();//保存区县
				JSONArray tempCountrys1 = new JSONArray();
				JSONArray countryIds1 = new JSONArray();
				JSONArray countryNames1 = new JSONArray();
				countryIds1.put("");
				countryNames1.put("请选择");
				tempCountrys1.put(countryNames1);
				tempCountrys1.put(countryIds1);
			countrys.put(tempCountrys1);
			
		if((StaticMethod.nullObject2String(areaid)).length()==2){//2.当用户为省用户时：显示全部的地市和地市下面对应的全部的区县。
			List list = (ArrayList) mgr.getSonAreaByAreaId(areaid);
			for (int i = 0; i < list.size(); i++) {
				TawSystemArea sysarea = (TawSystemArea) list.get(i);
				cityIds.put(sysarea.getAreaid());
				cityNames.put( sysarea.getAreaname());
				
				//查对应的区县
				List listCountry = (ArrayList) mgr.getSonAreaByAreaId(sysarea.getAreaid());
				JSONArray tempCountrys = new JSONArray();
				JSONArray countryIds = new JSONArray();
				JSONArray countryNames = new JSONArray();
				for(int k=0;k<listCountry.size();k++){
					TawSystemArea tempSysarea = (TawSystemArea) listCountry.get(k);
					countryIds.put(tempSysarea.getAreaid());
					countryNames.put( tempSysarea.getAreaname());
				}
				tempCountrys.put(countryNames);
				tempCountrys.put(countryIds);
				
				countrys.put(tempCountrys);
				
			}
		
		}else if((StaticMethod.nullObject2String(areaid)).length()==4||(StaticMethod.nullObject2String(areaid)).length()==6){//3.当用户为地市用户或者区县用户时：显示用户的当前地市和当前地市下全部的区县。
			String tempAreaid=areaid.substring(0,4);
			//ITawSystemAreaManager mgr = (ITawSystemAreaManager) getBean("ItawSystemAreaManager");
			TawSystemArea tempArea = mgr.getAreaByAreaId(tempAreaid);
			cityIds.put(tempAreaid);
			cityNames.put(tempArea.getAreaname());
			
			List listCountry = (ArrayList) mgr.getSonAreaByAreaId(tempAreaid);
			//查对应的区县
			JSONArray tempCountrys = new JSONArray();
			JSONArray countryIds = new JSONArray();
			JSONArray countryNames = new JSONArray();
			for(int k=0;k<listCountry.size();k++){
				TawSystemArea tempSysarea = (TawSystemArea) listCountry.get(k);
				countryIds.put(tempSysarea.getAreaid());
				countryNames.put( tempSysarea.getAreaname());
			}
			tempCountrys.put(countryNames);
			tempCountrys.put(countryIds);
			
			countrys.put(tempCountrys);
		}
		citys.put(cityNames);
		citys.put(cityIds);
		areaJson.put("city", citys);
		areaJson.put("country", countrys);
		System.out.println("---areaJson="+areaJson.toString());
		MobileCommonUtils.responseWrite(response, areaJson.toString(), "UTF-8");
		
//		String[][] type = {{"请选择","基站","接入网","直放站室分","WLAN","铁塔及天馈","重点客户机房"},{"","112250103","112250502","112250302","112250601","112250404","112250901"}};
//		
//		String[][][] level ={{{"请选择 "},{""}},{{"VIP基站","A类","B类","C类"},{"11225010301","11225010302","11225010303","11225010304"}},{{"A类","B类","C类"},{"11225050201","11225050202","11225050203"}},{{"标准","VIP","A类","B类"},{"11225030201","11225030202","11225030203","11225030204"}},{{"A类","B类","C类"},{"11225060101","11225060102","11225060103"}},{{"月标准","季度"},{"11225040401","11225040402"}},{{"VIP","A类","B类","C类"},{"11225090101","11225090102","11225090103","11225090104"}}};
//        Map<String, Object> map = new HashMap<String, Object>();
//		
//		map.put("type", type);
//		map.put("level",level);
//		JSONObject jsonObject = JSONObject.fromObject(map);
//	//	System.out.println("返回数组---------"+jsonObject.toString());
//		MobileCommonUtils.responseWrite(response, jsonObject.toString(), "UTF-8");
		
	}
	
	/**
	 * olt-bbu施工队施工回单-工单提交--工单处理
	  * @author chujingang
	  * @title: oltBbuSubmit
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @throws Exception void
	 */
	public void oltBbuSubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//接收手机端传过来的数据
		// 发短信
		String deadLineTime = "", contact = "";

		// 主题
		String title = StaticMethod.nullObject2String(request.getParameter("title"));
		// 主题ID
		String titleId = StaticMethod.nullObject2String(request.getParameter("titleId"));
		// 回单人
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		// 审核人
		String auditor = StaticMethod.nullObject2String(request.getParameter("initiatorCheck"));
		//预检预修是否完成
		String isRecover = StaticMethod.nullObject2String(request.getParameter("isRecover"));
		// 回单时间
		Date createTime = new Date();
		// 处理描述
		String mainRemark = StaticMethod.nullObject2String(request.getParameter("mainRemark"));
		// 处理人
		String dealPerformer2 = request.getParameter("dealPerformer2");
		String taskId = StaticMethod.nullObject2String(request.getParameter("taskId"));
		//预检预修工单号
		String processInstanceId = StaticMethod.nullObject2String(request.getParameter("processInstanceId"));
		
		String latitude = StaticMethod.nullObject2String(request.getParameter("latitude"));
		String longitude = StaticMethod.nullObject2String(request.getParameter("longitude"));
		
		//获取下一步流程需要的处理标志和审核人
		String handleState = request.getParameter("workerHandleState");
		//String nextPsrson = request.getParameter("initiatorCheck");
		String nextPsrson = "superUser";//找不到人，默认superUser，避免丢单
		
		
		//将处理信息存入工单处理实体类中--start
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
		entity.setTheme(title);
		entity.setThemeId(titleId);
		entity.setReceivePerson(userId);
		entity.setAuditor(auditor);
		entity.setDoPersons(dealPerformer2);
		entity.setReceiveTime(createTime);
		entity.setCheckTime(createTime);
		entity.setHandleDescription(mainRemark);
		entity.setProcessInstanceId(processInstanceId);
		entity.setIsRecover(isRecover);
		entity.setTaskId(taskId);
		entity.setLatitude(latitude);
		entity.setLongitude(longitude);
		entity.setLinkName("worker");
		entity.setOperation("04");
		
		
		//将部分处理信息,添加到工单主表中
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService.search(search);
		String msg="";
		if (pnrTicketList != null) {
			PnrTransferOffice pnrTransferOffice = pnrTicketList.get(0);
			if(pnrTransferOffice.getWorkerSceneHandleFlag()==null){
				pnrTransferOffice.setWorkerSceneHandleFlag("1");//手机端处理完，需要web端进行场景模板处理
				pnrTransferOfficeService.save(pnrTransferOffice);
			}else{
				pnrTransferOffice.setLastReplyTime(new Date());
				pnrTransferOffice.setRollbackFlag("0");
	
				deadLineTime = pnrTransferOffice.getEndTime() == null ? ""
						: sFormat.format(pnrTransferOffice.getEndTime());
				contact = pnrTransferOffice.getConnectPerson();
				
				if(!"".equals(pnrTransferOffice.getCountryCSJ())){
					nextPsrson=pnrTransferOffice.getCountryCSJ();//工单发起人
				}
				
				//保存数据--end
				
				// 流程提交到下一级
				FormService formService = (FormService) getBean("formService");
				TaskFormData taskFormData = formService.getTaskFormData(taskId);
				Map<String, String> map = new HashMap<String, String>();
				String tempKeyValue="";
					
				map.put("workerHandleState", "handle");
				map.put("initiatorCheck", nextPsrson);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	//			logger.info("当前的操作人："+userId+";当前的流程:OltBbu机房优化申请;当前的操作环节:施工队施工回单（手机端）;当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:{"+tempKeyValue+"};当前时间:"+sdf.format(new Date())+";--start");
				formService.submitTaskFormData(taskId, map);
	//			logger.info("当前的操作人："+userId+";当前的流程:OltBbu机房优化申请;当前的操作环节:施工队施工回单（手机端）;当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:{"+tempKeyValue+"};当前时间:"+sdf.format(new Date())+";--end");
				//在工单主表中添加流程信息
				processTaskService.setTvalueOfTask(processInstanceId, nextPsrson, pnrTransferOffice, PnrTransferOffice.class, null, null,false);
				pnrTransferOfficeService.save(pnrTransferOffice);
				transferHandleService.save(entity);
				
				// 流程提交到下一级-end
				msg = "Olt-Bbu机房优化申请工单已派至您工位待处理： 工单号："+pnrTransferOffice.getProcessInstanceId()+",主题："+
				pnrTransferOffice.getTheme()+",类型："+
				pnrTransferOffice.getJobOrderType()+",子类型："+
				pnrTransferOffice.getSubTypeName()+"。";
				CommonUtils.sendMsg(msg, auditor);
				
			}
			String returnJson = "[{\"success\":\"true\"}]";
			MobileCommonUtils.responseWrite(response, returnJson, "UTF-8");
		}
		
	}
	
	/**
	 * 传输局--oltBbu机房优化申请--区县公司验收
	  * @author chujingang
	  * @title: oltBbuSpotcheck
	  * @param mapping
	  * @param form
	  * @param request
	  * @param response
	  * @throws Exception void
	 */
	public void oltBbuSpotcheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//接收手机端传过来的数据
		//String handleId = StaticMethod.nullObject2String(request.getParameter("handleId"));
		// 是否在500内:0--否，1--在
		String state = StaticMethod.nullObject2String(request.getParameter("state"));
		// 回单人
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();

		// 回单时间
		Date insertTime = new Date();
		
		String taskId = StaticMethod.nullObject2String(request.getParameter("taskId"));
		String processInstanceId = StaticMethod.nullObject2String(request.getParameter("processInstanceId"));
		
		String latitude = StaticMethod.nullObject2String(request.getParameter("latitude"));
		String longitude = StaticMethod.nullObject2String(request.getParameter("longitude"));

		// 主题
		String title = StaticMethod.nullObject2String(request.getParameter("title"));
		// 主题ID
		String titleId = StaticMethod.nullObject2String(request.getParameter("titleId"));
		//验收描述
		String workDescription=StaticMethod.nullObject2String(request.getParameter("workDescription"));
		//验收结果
		String orderAuditHandleState=StaticMethod.nullObject2String(request.getParameter("workerHandleState"));
		System.out.println("---------orderAuditHandleState="+orderAuditHandleState);
		
		//将处理信息存入工单处理实体类中--start
//		IPnrTransferSpotcheckService transferSpotcheckService = (IPnrTransferSpotcheckService) getBean("pnrTransferSpotcheckService");
//		PnrTransferSpotcheck entity = new PnrTransferSpotcheck();
//		entity.setHandleId(handleId);
//		entity.setInsertDate(insertTime);
//		entity.setState(state);
//		entity.setUserId(userId);
//		entity.setTaskId(taskId);
//		entity.setProcessInstanceId(processInstanceId);
//		entity.setLatitude(latitude);
//		entity.setLongitude(longitude);
		
		//将处理信息存入工单处理实体类中--start
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
		entity.setTheme(title);
		entity.setThemeId(titleId);
		entity.setReceivePerson(userId);
		entity.setAuditor(userId);
		entity.setDoPersons(userId);
		entity.setReceiveTime(insertTime);
		entity.setCheckTime(insertTime);
		entity.setHandleDescription(workDescription);
		entity.setProcessInstanceId(processInstanceId);
		entity.setTaskId(taskId);
		entity.setLatitude(latitude);
		entity.setLongitude(longitude);
		entity.setIsFiveHundredInside(state);
		entity.setLinkName("orderAudit");
		if(orderAuditHandleState.equals("handle")){	//通过
			entity.setOperation("61");
		}else if(orderAuditHandleState.equals("rollback")){	//驳回
			entity.setOperation("62");
		}
		
		//将部分处理信息,添加到工单主表中
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService.search(search);
		PnrTransferOffice pnrTransferOffice =new PnrTransferOffice();
		String msg="";
		if (pnrTicketList != null) {
			pnrTransferOffice= pnrTicketList.get(0);
			if(pnrTransferOffice.getWorkerSceneOrderAuditFlag()==null && "handle".equals(orderAuditHandleState)){
				pnrTransferOffice.setWorkerSceneOrderAuditFlag("1");//手机端已处理完,到web端进行场景模板处理
				pnrTransferOfficeService.save(pnrTransferOffice);
			}else{
				pnrTransferOffice.setState(0);
				if(orderAuditHandleState.equals("handle")){	//通过
					pnrTransferOffice.setRollbackFlag("0");
				}else if(orderAuditHandleState.equals("rollback")){	//驳回
					pnrTransferOffice.setRollbackFlag("1");
				}
				
				//如果是驳回流程删除验收环节的场景模板
				if(orderAuditHandleState.equals("rollback")){
					// 清空区县公司验收环节的场景模板信息
					pnrTransferOffice.setSumOrderAuditCostOfPartyB(null);
					pnrTransferOffice.setOrderauditProjectAmount(null);
					pnrTransferOffice.setOrderauditIncomeRatio(null);
					pnrTransferOffice.setOrderauditChildIds("");
					pnrTransferOffice.setOrderauditChildNames("");
					pnrTransferOffice.setWorkerSceneOrderAuditFlag("");
					//删除区县公司验收环节场景模板
					ISceneTemplateService sceneTemplateService = (ISceneTemplateService)getBean("sceneTemplateService");
					sceneTemplateService.deleteSceneTemplate(processInstanceId, "orderAudit", null);
				}
				
				FormService formService = (FormService) getBean("formService");
				TaskFormData taskFormData = formService.getTaskFormData(taskId);
				Map<String, String> map = new HashMap<String, String>();
				String nextPerson="";	//下一步的处理人
				if(orderAuditHandleState.equals("handle")){	//通过
					nextPerson=pnrTransferOffice.getCityLineCharge();//市线路主管
				}else if(orderAuditHandleState.equals("rollback")){	//驳回
					nextPerson=pnrTransferOffice.getTaskAssignee();//施工队
				}else{
					nextPerson="superUser";	//找不到下一步的处理人时，默认superUser，避免丢单
				}
				map.put("orderAuditHandleState", orderAuditHandleState);
				map.put("cityLineChargeAduitor",nextPerson);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//提交流程
				formService.submitTaskFormData(taskId, map);
				//在工单主表中添加流程信息
				processTaskService.setTvalueOfTask(processInstanceId, nextPerson, pnrTransferOffice, PnrTransferOffice.class, null, null,false);
				//保存主表数据
				pnrTransferOfficeService.save(pnrTransferOffice);
				//保存流转信息数据
				transferHandleService.save(entity);
				
				//发短信
				msg = "Olt-Bbu机房优化申请工单已派至您工位待处理： 工单号："+pnrTransferOffice.getProcessInstanceId()+",主题："+
				pnrTransferOffice.getTheme()+",类型："+
				pnrTransferOffice.getJobOrderType()+",子类型："+
				pnrTransferOffice.getSubTypeName()+"。";
				CommonUtils.sendMsg(msg, nextPerson);
				
			}
			
		}
		String returnJson = "[{\"success\":\"true\"}]";
		MobileCommonUtils.responseWrite(response, returnJson, "UTF-8");
	}
	/**
	 * 记录日志的
	 */
	private void printlog(String className,int rownum,String msg){
		com.boco.eoms.common.log.BocoLog.info(className, rownum, msg);		
	}

	/**
	* 公客场景模板场信息 ---提供给手机端展示
	 */
	public void getSceneInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
//		sceneType    ganlu 杆路  gdl  光电缆  jjx  交接箱   guandao 管道
		String sceneNo = StaticMethod.nullObject2String(request.getParameter("sceneType"));
		String copyNo = "";
		String copyName = "";
		String flag = "";
	
		String returnJson="";
//		List item_noList = new ArrayList();
		
		/*String returnJson = "{\"sceneTemplateId\":\"场景模版id\",\"scene_data\":[" +
				"" +
				"" +
				"{\"sceneId\":\"子场景id2\",\"specialSceneFlag\":\"1\",\"sceneName\":\"子场景名称\"," +
				"\"sceneUnit\":\"单位\",\"sceneMeasure\":\"处理措施\",\"material_data\":[{\"materialId\":\"材料id4\"," +
				"\"materialName_data\":[\"材料名称1\",\"材料名称2\",\"材料名称3\"],\"materialPrice_data\":[\"5\",\"7\",\"8\"]," +
				"\"materialLimit\":\"22\",\"materialUnitName\":\"限额单位\" }]}," +
				
				
				
				"{\"sceneId\":\"子场景id\",\"sceneName\":\"子场景名称2\",\"specialSceneFlag\":\"0\"," +
				"\"sceneUnit\":\"单位\",\"sceneMeasure\":\"处理措施\",\"material_data\":[" +
				"" +
				"{\"materialId\":\"材料id2\"," +
				"\"materialName_data\":[\"材料名称1\",\"材料名称2\",\"材料名称3\"],\"materialPrice_data\":[\"23\",\"33\",\"44\"]," +
				"\"materialLimit\":\"4\",\"materialUnitName\":\"限额单位\" }," +
				"" +
				"" +
				"{\"materialId\":\"材料id\"," +
				"\"materialName_data\":[\"材料名称1\",\"材料名称2\",\"材料名称3\"],\"materialPrice_data\":[\"23\",\"33\",\"44\"]," +
				"\"materialLimit\":\"4\",\"materialUnitName\":\"限额单位\" }]}" +
				"" +
				"" +
				"" +
				"" +
				"]}";*/
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");

		String searchSql ="select m.scene_no,m.scene_name,'main' flag ,'' dispose,'' unit,'' is_have from master_male_scene m " +
		"where m.scene_no='" +sceneNo+"'";
		searchSql+=" union all";
		searchSql+=" select mc.copy_no,mc.copy_name,'copy',mc.dispose,mc.unit,mc.is_have from maste_male_copy_scene mc ";
		searchSql+=" where mc.scene_no='" +sceneNo+"'";

		List<ListOrderedMap> resultList = jdbcService.queryForList(searchSql);	

		String isHave = "0";
		for (ListOrderedMap listOrderedMap : resultList) {
			flag = (String) listOrderedMap.get("flag");
	
			if("main".equals(flag)){
				returnJson = "{\"sceneTemplateId\":\""+sceneNo+"\",\"sceneTemplateName\":\""+listOrderedMap.get("scene_name")+"\",\"scene_data\":[";
			}else if("copy".equals(flag)){
				copyNo= (String) listOrderedMap.get("scene_no");
				copyName= (String) listOrderedMap.get("scene_name");
				isHave= (String) listOrderedMap.get("is_have");
				
				String copyItemSql = "select m.item_no ,m.limit_num,m.unit from maste_male_item m where m.copy_no='"+copyNo+"'";
		    	List<ListOrderedMap> copyItemresultList = jdbcService.queryForList(copyItemSql);	
		    	
		    	returnJson+="{\"sceneId\":\""+copyNo+"\",\"specialSceneFlag\":\""+isHave+"\",\"sceneName\":\""+copyName+"\"," +
					"\"sceneUnit\":\"1\",\"sceneUnitName\":\""+listOrderedMap.get("unit")+"\",\"sceneMeasure\":\""+listOrderedMap.get("dispose")+"\",\"material_data\":[";
		    	
		    	for(ListOrderedMap copyItemMap : copyItemresultList){
//	        		item_noList.add(copyItemMap.get("item_no"));
	        		
	        		String itemNo = copyItemMap.get("item_no")+"";
	        		returnJson+="{\"materialId\":\""+itemNo+"\",";
	        		//一行材料对应的具体材料
	        		String dataItemSql = "select d.data_no,d.data_name,d.per_price from maste_male_item_rel_data dt left join maste_male_data d " +
	        				" on dt.data_no = d.data_no where dt.item_no='"+itemNo+"'";
	        		
			    	List<ListOrderedMap> dataItemresultList = jdbcService.queryForList(dataItemSql);	
			    	
			    	String materName ="";
			    	String materPrice = "";
			    	String materNo = "";
			    	String materStr ="";
			    	for(ListOrderedMap dataItemMap : dataItemresultList){
			    		materName+=dataItemMap.get("data_name")==null?"\"无\",":"\""+dataItemMap.get("data_name")+"\",";
			    		materPrice+=dataItemMap.get("per_price")==null?"\"0\",":"\""+dataItemMap.get("per_price")+"\",";
			    		materNo+=dataItemMap.get("data_no")==null?"\"无\",":"\""+dataItemMap.get("data_no")+"\",";
			    		
			    	}
			    	if("".equals(materName)){
			    		 materName ="\"无\"";
				    	 materPrice = "\"0\"";
				    	 materNo = "\"无\"";
			    	}else{
			    		materName = materName.substring(0, materName.length()-1);
			    		materPrice = materPrice.substring(0, materPrice.length()-1);
			    		materNo = materNo.substring(0, materNo.length()-1);
			    	}
			    	
			    	String limit_num = copyItemMap.get("limit_num")==null?"0":copyItemMap.get("limit_num")+"";
					materStr = "\"materialVersionId\":["+materNo+"],\"materialName_data\":["+materName+"],\"materialPrice_data\":["+materPrice+"],";
					returnJson +=materStr;
					returnJson+="\"materialLimit\":\""+limit_num+"\",\"materialUnitName\":\""+copyItemMap.get("unit")+"\" },";
	        	}
		    	
		    	returnJson= returnJson.substring(0, returnJson.length()-1);
		    	returnJson+="]},";	
	
			}
		}
		
		returnJson= returnJson.substring(0, returnJson.length()-1);
		returnJson +="]}";
		
		System.out.println("returnJson:----"+returnJson);
		MobileCommonUtils.responseWrite(response, returnJson, "UTF-8");
	}
	/**
	 * 公客工单--归集；未归集
	 */
	public void getTaskTogether(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
	request.setCharacterEncoding("utf-8");
		
		//回单距离限制
		 String handleDistance = "";
	    //验收或抽查距离限制
		 String spotcheckDistance = "";
		
		//总页数
		long count = 0;
		// 获取每页显示条数
		Integer pageSize = ((SheetAttributes) ApplicationContextHolder
				.getInstance().getBean("SheetAttributes")).getPageSize();

		// 当前页数
		String pageIndexTemp = StaticMethod.nullObject2String((request
				.getParameter("pageIndex")));
		Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(pageIndexTemp) ? 0 : (Integer
				.parseInt(pageIndexTemp) - 1));
		String beanName = request.getParameter("beanName");
		String sheetType = request.getParameter("sheetType");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = StaticMethod.nullObject2String(request.getParameter("userId"));// sessionform.getUserid();
		System.out.println("AndroidWorkOrderAction-getTaskTogether--userId="+userId);

		String areaid=com.boco.eoms.partner.process.util.CommonUtils.getAreaIdByDeptId(sessionform.getDeptid());
		String country = areaid.substring(0,areaid.length()>5?6:areaid.length());
		//2015-7-1手机工单条件查询-start
		//接收查询页面传递的字段值
		//工单主题
		String androidWorkOrderTheme = request.getParameter("androidWorkOrderTheme");
		System.out.println("AndroidWorkOrderAction-getTaskTogether--androidWorkOrderTheme="+androidWorkOrderTheme);
		//工单派单时间
		String androidSendTime = request.getParameter("androidSendTime");
		System.out.println("AndroidWorkOrderAction-getTaskTogether--androidSendTime="+androidSendTime);
		//工单状态:处理、抽查
		String androidWorkOrderState = request.getParameter("androidWorkOrderState");
		System.out.println("AndroidWorkOrderAction-getTaskTogether--androidWorkOrderState="+androidWorkOrderState);
		//地市
		String city = request.getParameter("city");
		System.out.println("AndroidWorkOrderAction-getTaskTogether--地市city="+city);
		//区县
//		String country = request.getParameter("country");
		System.out.println("AndroidWorkOrderAction-getTaskTogether--区县country="+country);
//		字段workOrderId   工单号
		String workOrderId = request.getParameter("workOrderId");
		System.out.println("AndroidWorkOrderAction-getTaskTogether--工单号workOrderId="+workOrderId);
//		字段maleGuestType   merge(归集)  alone(未归集)   addmerge(可加入归集)
		String maleGuestType = request.getParameter("maleGuestType");
		System.out.println("AndroidWorkOrderAction-getTaskTogether--归集状态maleGuestType="+maleGuestType);
		
		String siteCd = request.getParameter("siteCd");
		System.out.println("AndroidWorkOrderAction-getTaskTogether--siteCd="+siteCd);
		
		AndroidQuery androidQuery = new AndroidQuery();
		androidQuery.setTheme(androidWorkOrderTheme);
		androidQuery.setSendTime(androidSendTime);
		androidQuery.setWorkOrderState(androidWorkOrderState);
		androidQuery.setUserId(userId);
		androidQuery.setCity(city);//地市
		androidQuery.setCountry(country);//区县
		androidQuery.setWorkOrderId(workOrderId);
		androidQuery.setMaleGuestType(maleGuestType);
		androidQuery.setSiteCd(siteCd);
		//2015-7-1手机工单条件查询-end
		TaskService taskService = (TaskService) getBean("taskService");
		String processDefinitionKey = "";
		List<Task> workList = new ArrayList<Task>();
		List<AndroidWorkOrderTask> workOrderList = new ArrayList<AndroidWorkOrderTask>();
		List<Task> workOldList = new ArrayList<Task>();
		List<Task> workNewList = new ArrayList<Task>();
		List<TaskModel> PnrList = new ArrayList<TaskModel>();
	
		if("maleGuest".equals(sheetType)){//公客故障工单查询
			
			androidQuery.setProcessDefinitionKey("myMaleGuestProcess");
			androidQuery.setWorkOrderType(sheetType);
			
			String processMaleGuestKey = "myMaleGuestProcess";
			
			//jdbc查询待处理工单-start 2015-12-22注释
//		    count = pnrTransferOfficeService.getAndroidTaskCount(androidQuery);
		    workOrderList = pnrTransferOfficeService.getAndroidTaskListTogether(androidQuery, pageIndex, 1000);
		    
			IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
//			for (Task workOrderTask : workList) {
			for (AndroidWorkOrderTask workOrderTask : workOrderList) {// 2015-12-22备份
				Search search = new Search();
				search.addFilterEqual("processInstanceId", workOrderTask
						.getProcessInstanceId());
//				search.addSortAsc("sendTime");//按派单时间倒序
				SearchResult t = pnrTransferOfficeService.searchAndCount(search);
				List<PnrTransferOffice> list = t.getResult();
				if (list != null && list.size() != 0) {
					PnrTransferOffice pnrTransferOffice = list.get(0);
					TaskModel tranferOfficeModel = new TaskModel();
					tranferOfficeModel.setTaskId(workOrderTask.getTaskWworkOrderId());
//					tranferOfficeModel.setTaskId(workOrderTask.getId());
					tranferOfficeModel.setProcessInstanceId(pnrTransferOffice
							.getProcessInstanceId());
					tranferOfficeModel.setTheme(pnrTransferOffice.getTheme());
					
					if (workOrderTask.getTaskDefinitionKey().equals("auditor")){
						tranferOfficeModel.setStepState("12");
						if(null==pnrTransferOffice.getMaleGuestState()||"0".equals(pnrTransferOffice.getMaleGuestState())){
							tranferOfficeModel.setLineType("0");
						}else if("1".equals(pnrTransferOffice.getMaleGuestState())){
							tranferOfficeModel.setLineType("1");
						}						
					}else if(workOrderTask.getTaskDefinitionKey().equals("twoSpotChecks")){
						tranferOfficeModel.setStepState("22");

					}
					
					PnrList.add(tranferOfficeModel);
					
					}
				}
			}

		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		returnMap.put("datas", PnrList);
		returnMap.put("count", count);
		returnMap.put("handleDistance", handleDistance);
		returnMap.put("spotcheckDistance", spotcheckDistance);
		returnMap.put("approver", "");
		JSONArray returnArray = JSONArray.fromObject(returnMap);
		String returnStr = returnArray.toString();
		MobileCommonUtils.responseWrite(response, returnStr, "UTF-8");
		return;
	}
	
	/**
	 * 公客工单--加锁，
	 */
	public void taskChain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		String returnJson = "[{\"success\":\"true\"}]";
		
		String sunProcessInstanceId = request.getParameter("sunProcessInstanceId");
		String parentProcessInstanceId = request.getParameter("parentProcessInstanceId");
		String processMode = request.getParameter("processMode");
//		处理方式 processMode  minusOrder解锁    addOrder加锁   
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		
		search.addFilterEqual("processInstanceId", sunProcessInstanceId);
		PnrTransferOffice p = null;
		if("minusOrder".equals(processMode)){
			 p = pnrTransferOfficeService.searchUnique(search);   
			p.setMaleGuestState("3");
			pnrTransferOfficeService.save(p);
			
		}else if ("addOrder".equals(processMode)){
	        p = pnrTransferOfficeService.searchUnique(search);  
	        
	        if(p!=null&&!"2".equals(p.getMaleGuestState())){//防止重复加入
	        	p.setMaleGuestState("2");
		        p.setParentProcessInstanceId(parentProcessInstanceId);
		        pnrTransferOfficeService.save(p);
	        }else{
	        	
				 returnJson = "[{\"success\":\"repeat\"}]";

	        }
	        
		}else {
			 returnJson = "[{\"success\":\"false\"}]";
			
		}
		
  		MobileCommonUtils.responseWrite(response, returnJson, "UTF-8");

	}
	
	
	/**
	 * 公客工单--驳回，
	 */
	public void maleGustTaskReject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		// 发短信
		String deadLineTime = "", contact = "";

		// 主题
		String title = StaticMethod.nullObject2String(request
				.getParameter("theme"));
		// 主题ID
		String titleId = StaticMethod.nullObject2String(request
				.getParameter("themeId"));
		// 回单人
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		// 审核人
	/*	String auditor = StaticMethod.nullObject2String(request
				.getParameter("auditor"));*/

		// 回单时间
		Date createTime = new Date();
		// 处理描述
		/*String mainRemark = StaticMethod.nullObject2String(request
				.getParameter("mainRemark"));*/
		// 故障处理情况
//		String faultHandle = request.getParameter("faultHandle");
		//故障原因
//		驳回原因标志  rejectFlag
//		2    障碍非当前分局责任
//		3    障碍非传输局责任
//		String faultCause = request.getParameter("faultCause");
		String faultCause = request.getParameter("rejectFlag");
		// 处理人
//		String dealPerformer2 = request.getParameter("dealPerformer2");

	/*	String taskId = StaticMethod.nullObject2String(request
				.getParameter("taskId"));
*/
		String processInstanceId = StaticMethod.nullObject2String(request
				.getParameter("processInstanceId"));

		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
		entity.setTheme(title);
		entity.setThemeId(titleId);
		entity.setReceivePerson(userId);
//		entity.setAuditor(auditor);
//		entity.setDoPersons(dealPerformer2);
		entity.setReceiveTime(createTime);
//		entity.setHandleDescription(mainRemark);
//		entity.setFaultHandle(faultHandle);
		entity.setFaultCause(faultCause);
		entity.setProcessInstanceId(processInstanceId);
		
		entity.setCheckTime(createTime);
		entity.setLinkName("auditor");
		entity.setOperation("02");
		
//		entity.setTaskId(taskId);
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
				.search(search);
		PnrTransferOffice pnrTransferOffice =null;
		if (pnrTicketList != null) {
		    pnrTransferOffice = pnrTicketList.get(0);
			pnrTransferOffice.setLastReplyTime(new Date());
			//改变工单状态：改为8，达到隐藏工单的效果
			pnrTransferOffice.setState(8);
			
			pnrTransferOffice.setTransferCopyScenesName("");
			pnrTransferOffice.setTransferMainScenesName("");
			pnrTransferOffice.setRecentCopyScenesName("");
			pnrTransferOffice.setRecentMainScenesName("");
			pnrTransferOffice.setRollbackFlag("1");//0 通过 1 驳回

			pnrTransferOfficeService.save(pnrTransferOffice);

			deadLineTime = pnrTransferOffice.getEndTime() == null ? ""
					: sFormat.format(pnrTransferOffice.getEndTime());
			contact = pnrTransferOffice.getConnectPerson();
		}
		// 处理人关系表数据保存--start
		/*
		 * String[] personStrings = dealPerformer2.split(",");
		 * pnrTroubleTicketService.saveOrUpatePerson(processInstanceId,
		 * personStrings);
		 */
		// 处理人关系表数据保存--end
		transferHandleService.save(entity);
		/*
		 * //发短信 String mainResName=""; if (pnrTroubleTicketList != null) {
		 * mainResName = pnrTroubleTicketList.get(0).getFailedSite(); }
		 */
		// 流程提交到下一级
		/*FormService formService = (FormService) getBean("formService");
		TaskFormData taskFormData = formService.getTaskFormData(taskId);
		Map<String, String> map = new HashMap<String, String>();
		for (FormProperty formProperty : taskFormData.getFormProperties()) {
			if (formProperty.isWritable()) {
				String name = formProperty.getId();
				map.put(name, request.getParameter(name));
			}
		}*/
		//将处理完的工单停留在施工队处理状态
		//formService.submitTaskFormData(taskId, map);
		// 流程提交到下一级-end
		//判断是否是接口派单,是就进行接口通知
		/*String maleGuestNumber =  pnrTransferOfficeService.getMaleGuestNumberByThemeId(titleId);
		if(maleGuestNumber!=null && !"".equals(maleGuestNumber=maleGuestNumber.trim())){
			// 调用方法,查询接口需要的数据
			TransferMaleGuestReturn maleGuestReturn = pnrTransferOfficeService
					.getMaleGuestReturnData(maleGuestNumber);
			Thread aThread = new Thread(new MaleGuestReturnThead(sFormat
					.format(new Date()),
					CommonUtils.GK_MALE_GUEST_RECEIPT_METHOD, maleGuestReturn));
			aThread.start();
		}*/
		//判断是否是接口派单,是就进行接口通知
		String maleGuestState = pnrTransferOffice==null?"":pnrTransferOffice.getMaleGuestState();	
		
		TransferMaleGuestReturn maleGuest  = new TransferMaleGuestReturn();			
		String fault_Name="";
		if("2".equals(faultCause)){
			fault_Name="障碍非当前分局责任";
		}else if("3".equals(faultCause)){
			fault_Name="障碍非传输局责任";
		}
		maleGuest.setConfCRMTicketNo(pnrTransferOffice.getMaleGuestNumber());	
		// 回单标示
		maleGuest.setFlag(faultCause);	
		// 回单时间
		maleGuest.setBack_dt(sFormat.format(createTime));
		// 处理人姓名id
		maleGuest.setBack_userid(userId);
		// 处理人姓名
		maleGuest.setBack_username(sessionform.getUsername());
		// 故障原因id
		maleGuest.setReason_id(faultCause);
		// 描述
		maleGuest.setBack_info("");		
		// 故障原因
		maleGuest.setReason_name(fault_Name);
		
		pnrTransferOfficeService.maleGuestHandleInterfaceCall(maleGuest,maleGuestState,processInstanceId,titleId);

		String msg = "工单号:" + processInstanceId + ";" + "主题:"
				+ title + ";";
		msg += "截止时间:" + deadLineTime + ";" + "联系人及电话:"
				+ contact + ";驳回公客系统，不予处理";
		CommonUtils.sendMsg(msg, userId);

		
		String returnJson = "[{\"success\":\"true\"}]";
		
				
		MobileCommonUtils.responseWrite(response, returnJson, "UTF-8");


	}
	
	/**
	 * 公客工单--修改名称
	 */
	public void maleGustTaskUpdateTheme(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		
		// 主题
		String title = StaticMethod.nullObject2String(request
				.getParameter("newTheme"));

		String processInstanceId = StaticMethod.nullObject2String(request
				.getParameter("processInstanceId"));
	
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService
				.search(search);
		if (pnrTicketList != null) {
			PnrTransferOffice pnrTransferOffice = pnrTicketList.get(0);
			pnrTransferOffice.setTheme(title);
		    pnrTransferOfficeService.save(pnrTransferOffice);
		}

		String returnJson = "[{\"success\":\"true\"}]";	
				
		MobileCommonUtils.responseWrite(response, returnJson, "UTF-8");


	}
	
	/**
	 * 	 二次抽查 审批操作
	 */
	public void secondInspectionApprove2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String approvePId = StaticMethod.nullObject2String(request.getParameter("approvePId"));
		String approveTaskId = StaticMethod.nullObject2String(request.getParameter("approveTaskId"));
		String tcountry = StaticMethod.nullObject2String(request.getParameter("tcountry"));//区县
		String tdate = StaticMethod.nullObject2String(request.getParameter("tdate"));//日期
		String approveProcessType = StaticMethod.nullObject2String(request.getParameter("approveProcessType"));//流程标识		
		//获取主表信息
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", approvePId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService.search(search);
		if (pnrTicketList != null) {
			PnrTransferOffice pnrTransferOffice = pnrTicketList.get(0);
			if(pnrTransferOffice!=null){
				FormService formService = (FormService) getBean("formService");
				Map<String, String> map = new HashMap<String, String>();
				IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
				PnrTransferOfficeHandle entity = null;
				
				TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
			    String userId = sessionform.getUserid(); // 当前操作人
			    String areaid=com.boco.eoms.partner.process.util.CommonUtils.getAreaIdByDeptId(sessionform.getDeptid());
			    //取周期领用表人（即一次核验人）
				String dealingPeople = pnrTransferOfficeService.getDealingPeopleOfRepair(areaid,"6902");//取周期领用表人（即一次核验人）
				if("".equals(dealingPeople)){
					dealingPeople = "superUser";
				}
				String nextPerson="superUser";
				entity = new PnrTransferOfficeHandle();
				Date date = null;
				
				//流程流转参数
				map.put("auditReportAssignee",dealingPeople);//综合报表审核人人怎么取？存哪里？
				map.put("twoSpotChecksState","handle");
				nextPerson=dealingPeople;
				date = new Date();
				pnrTransferOffice.setSecondInspectionDate(date);//二次抽查审批通过时间
				pnrTransferOffice.setRollbackFlag("0"); //通过
				
				
				//流转信息表
				entity.setTheme(pnrTransferOffice.getTheme());
				entity.setThemeId(pnrTransferOffice.getId());
				entity.setReceivePerson(userId);
				entity.setAuditor(nextPerson);
////				entity.setDoPersons(dealPerformer2);
				entity.setReceiveTime(new Date());
				entity.setHandleDescription("手机端提交");
////				entity.setFaultHandle(faultHandle);
////				entity.setFaultCause(faultCause);
				entity.setCheckTime(new Date());//审批时间
				entity.setLinkName("twoSpotChecks");
				entity.setProcessInstanceId(approvePId);
				entity.setTaskId(approveTaskId);
//				
//				//工单主表
				
				transferHandleService.save(entity);
				formService.submitTaskFormData(approveTaskId, map);
				
				processTaskService.setTvalueOfTask(approvePId, nextPerson, pnrTransferOffice, PnrTransferOffice.class, null, null,false);
				pnrTransferOfficeService.save(pnrTransferOffice);
				
				
				//更新二次抽查报表中的审批状态
				Map<String,String> param=new HashMap<String,String>();
				param.put("approveFlag", "1");
				param.put("approvePId", approvePId);
				param.put("tcountry", tcountry);
				param.put("tdate", tdate);
				param.put("approveUserId", userId);
				param.put("approveProcessType", approveProcessType);
				param.put("reportType", "secondInspection");
				pnrTransferOfficeService.updateApproveFlag(param);
				
			}
		}

		String returnJson = "[{\"success\":\"true\"}]";	
				
		MobileCommonUtils.responseWrite(response, returnJson, "UTF-8");
	}
	
	/**
	 * 	 手机端 省公司抽检已标记的工单列表
	 	 * @author XuJinLiang
	 	 * @title: getAndroidWorkOrderList
	 	 * @date Aug 22, 2016 5:56:52 PM
	 	 * @param sampling_userid 标记人
	 	 * @param pageIndexTemp 
	 	 * @param pageIndex
	 	 * @return List<AndroidWorkOrderTask>
	 */
	public void seachWorkOrderList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//当前登录人
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String sampling_userid = sessionform.getUserid();
		
		//获取每页显示条数
		Integer pageSize = ((SheetAttributes) ApplicationContextHolder
				.getInstance().getBean("SheetAttributes")).getPageSize();

		// 当前页数
		String pageIndexTemp = StaticMethod.nullObject2String((request.getParameter("pageIndex")));
		Integer pageIndex = new Integer(GenericValidator.isBlankOrNull(pageIndexTemp) ? 0 : (Integer.parseInt(pageIndexTemp) - 1));

		//条数
		int count = 0;
	    count = pnrTransferOfficeService.getAndroidWorkOrderCount(sampling_userid);
	    
	    //明细
	    List<AndroidWorkOrderTask> workOrderList  = pnrTransferOfficeService.getAndroidWorkOrderList(sampling_userid,pageIndex,pageSize);
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("workOrderList", workOrderList);
		returnMap.put("count",count);
		//returnMap.put("pagesNum", 1);
		JSONArray returnArray = JSONArray.fromObject(returnMap);
		String returnStr = returnArray.toString();
		System.out.println("AndroidWorkOrderAction-showListendundo:returnStr:"+returnStr);
		MobileCommonUtils.responseWrite(response, returnStr, "UTF-8");
		return;
	}
	
	/**
	 * 	 手机端 省公司抽检保存抽检意见
	 	 * @author XuJinLiang
	 	 * @title: updateWorkOrderOpinion
	 	 * @date Aug 22, 2016 5:59:18 PM
	 	 * @param process_instance_id 工单号
	 	 * @param opinion 抽检意见
	 	 * @return int 更新的条数
	 */
	public void updateWorkOrderOpinion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//当前登录人
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		
		String processInstanceId = StaticMethod.nullObject2String(request.getParameter("processInstanceId")); //工单号
		String samplingJudgments = StaticMethod.nullObject2String(request.getParameter("sampling_opinion")); //抽检意见
		ParameterModel param = new ParameterModel();
		param.setProcessInstanceId(processInstanceId);
		param.setSamplingJudgments(samplingJudgments);
		param.setUserId(userId);
		
		String result = pnrTransferOfficeService.saveSamplingJudgments(param);
		String returnJson ="";
		if(!"".equals(result)){
			returnJson = "[{\"error\":\""+result+"\"}]";
		}else{
			returnJson = "[{\"success\":\"true\"}]";
		}
		
		//System.out.println("工单返回信息------" + returnJson+msg);
		
		MobileCommonUtils.responseWrite(response, returnJson, "UTF-8");
	  //  int updateWorkOrderOpinion = pnrTransferOfficeService.updateWorkOrderOpinion(process_instance_id,opinion);
		return;
	}

	/**
	 * 	 本地网区县抽检
	 	 * @author WangJun
	 	 * @title: transferDaiweiAudit
	 	 * @date Aug 25, 2016 10:24:51 AM
	 	 * @param mapping
	 	 * @param form
	 	 * @param request
	 	 * @param response
	 	 * @throws Exception void
	 */
	public void transferDaiweiAudit(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 回单人
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();

		String processInstanceId = StaticMethod.nullObject2String(request.getParameter("processInstanceId")); //流程号
		String taskId = StaticMethod.nullObject2String(request.getParameter("taskId")); //任务ID
		String samplingOpinion = StaticMethod.nullObject2String(request.getParameter("samplingOpinion")); //抽检意见
		
		//将部分处理信息,添加到工单主表中
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		List<PnrTransferOffice> pnrTicketList = pnrTransferOfficeService.search(search);
		String msg="";
		
		//保存流转信息
		IPnrTransferOfficeHandleService transferHandleService = (IPnrTransferOfficeHandleService) getBean("pnrTransferOfficeHandleService");
		PnrTransferOfficeHandle entity = new PnrTransferOfficeHandle();
		entity.setReceiveTime(new Date());
		entity.setReceivePerson(userId);
		entity.setHandleDescription("手机端区县抽检");
		entity.setProcessInstanceId(processInstanceId);
		entity.setTaskId(taskId);
		entity.setAuditor(userId);
		entity.setCheckTime(new Date());
		entity.setOpinion(samplingOpinion);//抽检意见
		entity.setLinkName("daiweiAudit");
		entity.setOperation("05");
		transferHandleService.save(entity);
		
		//修改工单主表中的状态值
		if (pnrTicketList != null) {
			PnrTransferOffice pnrTransferOffice = pnrTicketList.get(0);
			pnrTransferOffice.setDaiweiAuditSceneHandleFlag("1");
			pnrTransferOfficeService.save(pnrTransferOffice);
		}
		
		//给手机端返回结果
		String returnJson = "[{\"success\":\"true\"}]";
		MobileCommonUtils.responseWrite(response, returnJson, "UTF-8");
	}
}
