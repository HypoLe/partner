package com.boco.weixin.webapp.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.activiti.partner.process.model.NetResInspect;
import com.boco.activiti.partner.process.model.NetResInspectHandle;
import com.boco.activiti.partner.process.service.INetResInspectHandleService;
import com.boco.activiti.partner.process.service.INetResInspectService;
import com.boco.activiti.partner.process.service.IPnrTransferOfficeService;
import com.boco.activiti.partner.process.service.IProcessTaskService;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.partner.process.util.CommonUtils;
import com.boco.eoms.sheet.base.webapp.action.SheetAction;

public class WeiXinAction  extends SheetAction {
	private final String processDefinitionKey = "netResInspect";
	private final DateFormat sFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static Logger logger = Logger.getLogger(WeiXinAction.class);
	// 发信息用的常量
	public final static String TASK_MESSAGE = "巡检众筹";
	public final static String TASK_WORKORDERDEGREE = "紧急程度：";
	public final static String TASK_WORKORDERTYPE = "类型：";
	public final static String TASK_SUBTYPE = "子类型：";
	public final static String TASK_NO_STR = "工单编号:";
	public final static String TASK_TITLE_STR = "主题:";
	public final static String TASK_DEADSUBMIT_STR = "申请提交时间:";
	public final static String TASK_DEADLINE_STR = "截止时间:";
	public final static String TASK_CONTACT_STR = "联系人及电话:";
	public final static String TASK_STATION_NAME = "机房名称:";
	public final static String TASK_SEND_TIME = "派单时间:";
	public final static String TASK_RESOURCETYPE = "资源类型：";
	public final static String TASK_REPORTEDDESCRIBE = "描述：";
	
	private IProcessTaskService processTaskService = (IProcessTaskService) ApplicationContextHolder.getInstance().getBean("processTaskService");
	
	/**
	 * 隐患上报提交 Isabelle
	 */
	public ActionForward performAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		NetResInspect netResInspect=new NetResInspect();
		//1.隐患基本信息
		//工单名称
		String theme = StaticMethod.nullObject2String(request.getParameter("theme"));
		netResInspect.setTheme(theme);
		//资源类型
		String resourceType = StaticMethod.nullObject2String(request.getParameter("resourceType"));
		netResInspect.setResourceType(resourceType);
		//问题类型
		String questionType = StaticMethod.nullObject2String(request.getParameter("questionType"));
		netResInspect.setQuestionType(questionType);
		//地市
		String city = StaticMethod.nullObject2String(request.getParameter("region"));
		netResInspect.setCity(city);
		//区县
		String county = StaticMethod.nullObject2String(request.getParameter("country"));
		netResInspect.setCounty(county);
		//街道
		String street = StaticMethod.nullObject2String(request.getParameter("street"));
		netResInspect.setStreet(street);
		//描述
		String reportedDescribe = StaticMethod.nullObject2String(request.getParameter("reportedDescribe"));
		netResInspect.setReportedDescribe(reportedDescribe);
		//定位地址
		String locatedAddress = StaticMethod.nullObject2String(request.getParameter("locatedAddress"));
		netResInspect.setLocatedAddress(locatedAddress);
		//附件
		String attachments = request.getParameter("sheetAccessories");
		
		
		//2.上报人信息
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		//上报人
		String reportedUserId = sessionForm.getUserid();
		netResInspect.setReportedUserId(reportedUserId);
		//上报人部门
		String reportedDeptId = sessionForm.getDeptid();
		netResInspect.setReportedDeptId(reportedDeptId);
		//上报人电话
		String reportedPhoneNum = sessionForm.getContactMobile();
		netResInspect.setReportedPhoneNum(reportedPhoneNum);
		
		//3.专业判断，根据资源类型
		//获取专业
		String specialty = CommonUtils.getDictRemark(resourceType);
		netResInspect.setSpecialty(specialty);
		//4.保存照片（手机端）
		
		//5.下一环节处理人（根据配置的角色）
//		String roleId="";
//		if("1280101".equals(specialty)){//设备专业
//			roleId=precheckRoleModel.getEquipmentDispatcher();
//		}else if("1280102 ".equals(specialty)){//线路专业
//			roleId=precheckRoleModel.getCircuitDispatcher();
//		}
//		
//		IPnrTransferPrecheckService pnrTransferPrecheckService = (IPnrTransferPrecheckService) getBean("pnrTransferPrecheckService");
//		List<Map> dispatcherList = pnrTransferPrecheckService.getSGSByCountryCSJ(reportedUserId, roleId);
//		String dispatcher = "superUser";//综合调度人
//		if (dispatcherList != null
//				&& dispatcherList.size() > 0) {
//			if (dispatcherList.get(0).get("USERID") != null
//					&& !"".equals(dispatcherList.get(0).get(
//							"USERID"))) {
//				dispatcher = dispatcherList.get(0).get(
//						"USERID").toString();
//			}
//		}
//		netResInspect.setDispatcher(dispatcher);
		//现场核实人
		INetResInspectService netResInspectService = (INetResInspectService) getBean("netResInspectService");
		String checker = netResInspectService.getChecker(county,specialty);//需求变更，人员先默认，后续再做逻辑
		netResInspect.setChecker(checker);
		netResInspect.setState(0);
		//6.派发流程
		FormService formService = (FormService) getBean("formService");
		IdentityService identityService = (IdentityService) getBean("identityService");
		identityService.setAuthenticatedUserId(reportedUserId);
		TaskService taskService = (TaskService) getBean("taskService");
		RuntimeService runtimeService = (RuntimeService) getBean("runtimeService");
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
		String processInstanceId = processInstance.getId();//工单流程号
		netResInspect.setProcessInstanceId(processInstanceId);
		List<Task> taskList = taskService.createTaskQuery().processInstanceId(
				processInstanceId).active().list();
		String taskId = taskList.get(0).getId();
		Map<String, String> map = new HashMap<String, String>();
		map.put("checker", checker);
		formService.submitTaskFormData(taskId, map);
		
		//7.保存主表信息
		processTaskService.setTvalueOfTask(processInstanceId, checker, netResInspect, NetResInspect.class, null, null,false);
		netResInspect.setReportedDate(new Date());//上报日期
		netResInspectService.save(netResInspect);
		
		if (!"".equals(attachments)) {
			IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) getBean("pnrTransferOfficeService");
			pnrTransferOfficeService.saveAttachment(processInstanceId,
					attachments, "1");
		}
		
		//8.流转信息
		NetResInspectHandle netResInspectHandle=new NetResInspectHandle();
		netResInspectHandle.setProcessInstanceId(processInstanceId);//工单流程id
		netResInspectHandle.setUserId(reportedUserId);//审批人
		netResInspectHandle.setLinkFlag("troubleFound");//环节标识
		netResInspectHandle.setRemark(reportedDescribe);//备注（处理描述）
		netResInspectHandle.setOperationFlag("web");//web端
		//netResInspectHandle.setOperationFlag("mobile");//手机端
		netResInspectHandle.setTaskId(taskId); //任务ID
		INetResInspectHandleService netResInspectHandleService = (INetResInspectHandleService) getBean("netResInspectHandleService");
		netResInspectHandleService.save(netResInspectHandle);
		// 发短信
		// 短信接收人
		String msg = TASK_MESSAGE + "  " + TASK_NO_STR + processInstanceId
				+ "," + TASK_TITLE_STR + theme + "," + TASK_RESOURCETYPE
				+ CommonUtils.getId2NameString(resourceType, 1, ",") + "," + TASK_REPORTEDDESCRIBE + reportedDescribe+ "。";
		CommonUtils.sendMsg(msg, checker);

		return mapping.findForward("success");
	}
}
