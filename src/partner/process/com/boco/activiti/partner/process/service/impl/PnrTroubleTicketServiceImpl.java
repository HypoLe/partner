package com.boco.activiti.partner.process.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import com.boco.activiti.partner.process.dao.IPnrTroubleTicketDao;
import com.boco.activiti.partner.process.dao.IPnrTroubleTicketJDBCDao;
import com.boco.activiti.partner.process.model.PnrSmsSendEntity;
import com.boco.activiti.partner.process.model.PnrTroubleTicket;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.activiti.partner.process.po.WorkOrderStatisticsModel;
import com.boco.activiti.partner.process.service.IPnrTroubleTicketService;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.process.util.CommonUtils;
import com.boco.eoms.partner.res.mgr.PnrResConfigMgr;
import com.boco.eoms.partner.res.model.PnrResConfig;

/**
 
 */
public class PnrTroubleTicketServiceImpl extends CommonGenericServiceImpl<PnrTroubleTicket> implements IPnrTroubleTicketService {

    private IPnrTroubleTicketDao pnrTroubleTicketDao;
    private IPnrTroubleTicketJDBCDao pnrTroubleTicketJDBCDao;

    public void setPnrTroubleTicketJDBCDao(IPnrTroubleTicketJDBCDao pnrTroubleTicketJDBCDao) {
        this.pnrTroubleTicketJDBCDao = pnrTroubleTicketJDBCDao;
    }

    public IPnrTroubleTicketDao getPnrTroubleTicketDao() {
		return pnrTroubleTicketDao;
	}

	public void setPnrTroubleTicketDao(IPnrTroubleTicketDao pnrTroubleTicketDao) {
		this.pnrTroubleTicketDao = pnrTroubleTicketDao;
		this.setCommonGenericDao(pnrTroubleTicketDao);
	}
    public List<WorkOrderStatisticsModel> workOrderStatistics(String beginTime,String endTime,String subType){
       return pnrTroubleTicketJDBCDao.workOrderStatistics(beginTime,endTime,subType);
    }
    public List<TaskModel> workOrderQuery(String deptId,String workerid,String beginTime, String endTime, String subType,String theme,String city,int beginNum,int endNum){
        return pnrTroubleTicketJDBCDao.workOrderQuery(deptId,workerid,beginTime,endTime,subType,theme,city,beginNum,endNum);
    }
    public int workOrderCount(String deptId,String workerid,String beginTime, String endTime, String subType,String theme,String city){
        return pnrTroubleTicketJDBCDao.workOrderCount(deptId,workerid,beginTime,endTime,subType,theme,city);
    }

	@Override
	public void saveOrUpatePerson(String processInstanceId,
			String[] personStrings) {
		// TODO Auto-generated method stub
		pnrTroubleTicketJDBCDao.saveOrUpatePerson(processInstanceId,personStrings);

	}

	@Override
	public void saveOrUpateSpecialty(String processInstanceId,
			String[] specialtyStrings) {
		// TODO Auto-generated method stub
		pnrTroubleTicketJDBCDao.saveOrUpateSpecialty(processInstanceId,specialtyStrings);

	}
	@Override
	public void saveSendContext(PnrSmsSendEntity p) {
		// TODO Auto-generated method stub
		pnrTroubleTicketJDBCDao.saveSendContext(p);
	}

	@Override
	public void saveOrUpateAttachment(String processInstanceId,
			String accessoriesNames) {
		// TODO Auto-generated method stub
		pnrTroubleTicketJDBCDao.saveOrUpateAttachment(processInstanceId, accessoriesNames);

	}
	
	@Override
	/**
	 * performAdd 保存故障工单
	 */
	public void performAdd(PnrTroubleTicket ticket,boolean dealFlag){
//		一切判断都是上层，这层单纯接收数据，存数据，发单！
		DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		FormService formService = (FormService)ApplicationContextHolder.getInstance().getBean("formService");
		IdentityService identityService = (IdentityService) ApplicationContextHolder.getInstance().getBean("identityService");
//		短信接收人
		String msgRecive = ticket.getTaskAssignee();
	
		// 涉及专业
		String[] specialty = ticket.getSpecialty().split(",");
		String taskAssigneeJSON ="";
		String dueDateString = sFormat.format(ticket.getCreateTime().getTime()+ticket.getProcessLimit()*60*60*1000);

		if(dealFlag){
			// 接收人字符串
			taskAssigneeJSON="[{id:'"+ticket.getTaskAssignee()+"',nodeType:'user',categoryId:'taskAssignee'}]";
			PnrResConfigMgr pnrResConfigMgr = (PnrResConfigMgr) ApplicationContextHolder.getInstance().getBean("PnrResConfigMgr");
			PnrResConfig pnrResConfig = pnrResConfigMgr.find(ticket.getMainResId());
			ticket.setCity(pnrResConfig.getCity());
			ticket.setCountry(pnrResConfig.getCountry());
		
		}
		String processInstanceId = ticket.getProcessInstanceId();
		
		if(dealFlag){
			
			//查看是否是二次调度
			TawSystemUser taskAssigneeUser = CommonUtils.getTawSystemUserByUserId(ticket.getTaskAssignee(), "");
			System.out.println("ticket.getInitiator()"+ticket.getInitiator());
			TawSystemUser initiatorUser = CommonUtils.getTawSystemUserByUserId(ticket.getInitiator(), "");
			String tawDeptId = taskAssigneeUser.getDeptid();
			String deptId = initiatorUser.getDeptid();
			System.out.println("deptId:"+deptId);
			String deptIdSub = deptId.substring(0, 3);//派发人部门-截取前3个字符
			System.out.println("deptIdSub:"+deptIdSub);
			String tawDeptIdSub = tawDeptId.substring(0, 3);//接收人部门-截取前3个字符
			int state =0;
			boolean sendFlag = false;
			boolean reciveFlag = false;
//	    首先派发人是101或201打头的部门
			if(deptIdSub.equals("201")||deptIdSub.equals("101")){
				sendFlag=true;//是自维
			}
//     接收是代维还是自维
			if(tawDeptIdSub.equals("201")||tawDeptIdSub.equals("101")){
				reciveFlag = true; //是自维
			}
			
			if(sendFlag && !reciveFlag){//需要二次调度
				ticket.setState(6);
			}else{
				ticket.setState(0);				
			}
			
			
			
			// 流程开始
			identityService.setAuthenticatedUserId(ticket.getInitiator());
			RuntimeService runtimeService = (RuntimeService) ApplicationContextHolder.getInstance().getBean("runtimeService");
			TaskService taskService = (TaskService) ApplicationContextHolder.getInstance().getBean("taskService");
			if (processInstanceId == null || "".equals(processInstanceId)) {
				ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKey(CommonUtils.processTroubleDefinitionKey);
				processInstanceId = processInstance.getId();
			}
			List<Task> taskList = taskService.createTaskQuery().processInstanceId(
					processInstanceId).active().list();
			String taskId = taskList.get(0).getId();
			Map<String, String> map = new HashMap<String, String>();
			
			map.put("taskAssignee", ticket.getTaskAssignee());
//		
			
			map.put("dueDate",dueDateString);
			formService.submitTaskFormData(taskId, map);
			// 流程结束
			
			if(ticket.getState()==6){
				ticket.setOneInitiator(ticket.getInitiator());
				ticket.setInitiator(ticket.getTaskAssignee());
			}

			
		}else{
			System.out.println("ticket.getInitiator()"+ticket.getInitiator());
//			没有的情况下，只发给派发人
			// 流程开始
			ProcessEngine processEngine = (ProcessEngine) ApplicationContextHolder.getInstance().getBean("processEngine");
			// 启动流程实例  
	        ProcessInstance instance = processEngine  
	                 .getRuntimeService().startProcessInstanceByKey(CommonUtils.processTroubleDefinitionKey);  
	        processInstanceId = instance.getId();  
          
	        // 获得第一个任务  
	        TaskService taskService = processEngine.getTaskService();  
			identityService.setAuthenticatedUserId(ticket.getInitiator());
	        List<Task> taskList = taskService.createTaskQuery().processInstanceId(
					processInstanceId).active().list();
			String taskId = taskList.get(0).getId();
	        taskService.setAssignee(taskId,ticket.getInitiator());	          
			// 流程结束
	        
	        msgRecive =ticket.getInitiator();
		}
		
		IPnrTroubleTicketService pnrTroubleTicketService = (IPnrTroubleTicketService)  ApplicationContextHolder.getInstance().getBean("pnrTroubleTicketService");
		
//		工单与专业关系 --start
		pnrTroubleTicketService.saveOrUpateSpecialty(processInstanceId, specialty);
//		工单与专业关系 --end
//		
		ticket.setTaskAssigneeJSON(taskAssigneeJSON);
		ticket.setProcessInstanceId(processInstanceId);
		try {
			ticket.setEndTime(sFormat.parse(dueDateString));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		pnrTroubleTicketService.save(ticket);
//		发短信
		
		String msg = "故障工单号:"+ticket.getProcessInstanceId()+";主题:"+ticket.getTheme()+";站点:"+ticket.getFailedSite()+";处理时间截止到:"+dueDateString;
		CommonUtils.sendMsg(msg, msgRecive);

	}

	@Override
	public String getAttachmentNamesByProcessInstanceId(String processInstanceId) {
		// TODO Auto-generated method stub
		return pnrTroubleTicketJDBCDao.getAttachmentNamesByProcessInstanceId(processInstanceId);
	}

	@Override
	public Map<String, String> getCityOrCoruntryIdByGkCountryName(
			String gkCountryName) {
		return pnrTroubleTicketJDBCDao.getCityOrCoruntryIdByGkCountryName(gkCountryName);
	}
}
