package com.boco.activiti.partner.process.webapp.action;


import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.ServiceImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.activiti.partner.process.cmd.HistoryProcessInstanceDiagramCmd;
import com.boco.activiti.partner.process.model.PnrTaskTicket;
import com.boco.activiti.partner.process.model.PnrTaskTicketHandle;
import com.boco.activiti.partner.process.model.PnrTroubleTicket;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.activiti.partner.process.po.WorkOrderStatisticsModel;
import com.boco.activiti.partner.process.service.IPnrTaskTicketHandleService;
import com.boco.activiti.partner.process.service.IPnrTaskTicketService;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.process.util.CommonUtils;
import com.boco.eoms.partner.res.mgr.PnrResConfigMgr;
import com.boco.eoms.partner.res.model.PnrResConfig;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;


/**
 * Description:任务工单
 */

public class PnrTaskTicketAction extends BaseAction {
    String processDefinitionKey = "taskWorkOrder";

    
    /**
	 * 回退任务
	 * 
	 * @return
	 */
	public ActionForward rollback(ActionMapping mapping,
    ActionForm form, HttpServletRequest request,
    HttpServletResponse response) throws Exception {
	/*	
        String taskId = request.getParameter("taskId");

        CommandExecutor commandExecutor = (CommandExecutor) getBean("commandExecutor");

		Command<Integer> cmd = new RollbackTaskCmd(taskId);

		commandExecutor.execute(cmd);
        TaskService taskService = (TaskService) getBean("taskService");
        taskService.createTaskQuery().taskId(taskId);*/
		
		String taskId = request.getParameter("taskId");
		 TaskService taskService = (TaskService) getBean("taskService");
	        List<Task> taskList = taskService.createTaskQuery().taskId(taskId).list();
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
	        
	        return mapping.findForward("success");
	}

    /**
     * 详细信息呈现
     */
    public ActionForward gotoDetail(ActionMapping mapping,
                                    ActionForm form, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {

        String processId = request.getParameter("processInstanceId");

        IPnrTaskTicketService pnrTaksTicketService = (IPnrTaskTicketService) getBean("pnrTaskTicketService");
        Search search = new Search();
        search.addFilterEqual("processInstanceId", processId);
        SearchResult t = pnrTaksTicketService.searchAndCount(search);
        List<PnrTaskTicket> list = t.getResult();
        
//      回复message
        IPnrTaskTicketHandleService taskHandleService = (IPnrTaskTicketHandleService) getBean("pnrTaskTicketHandleService");
        search.addSortDesc("receiveTime");
        List<PnrTaskTicketHandle> handlelist = taskHandleService.searchAndCount(search).getResult();
        int listsize = handlelist.size();
        String specialty="";
        String deptNames="";
        if(null != list.get(0))
        {
        	specialty =  CommonUtils.getId2NameString(list.get(0).getSpecialty(), 1, ",");
        	deptNames=CommonUtils.getId2NameString(list.get(0).getCandidateGroup(), 3, ",");

        }
        //attachments 
		PnrTaskTicket ticket = new PnrTaskTicket();
		String sheetAccessories = pnrTaksTicketService.getAttachmentNamesByProcessInstanceId(processId);
		ticket.setSheetAccessories(sheetAccessories);
		request.setAttribute("sheetMain",ticket);
		//attachments-end
		 showReplySetAttribute(request,handlelist);
        request.setAttribute("pnrTaskTicket", list.get(0));
        request.setAttribute("PnrTaskHandleList", handlelist);
        request.setAttribute("listsize", listsize);
        request.setAttribute("specialty", specialty);
        request.setAttribute("deptNames", deptNames);
        request.setAttribute("processInstanceId", processId);
        
        
        return mapping.findForward("gotoDetail");

    }

    /**
     * 任务处理保存
     */
    public ActionForward goTodo(ActionMapping mapping,
                                ActionForm form, HttpServletRequest request,
                                HttpServletResponse response) throws Exception {

        DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        //主题
        String title = request.getParameter("title").trim();
        //主题ID
        String titleId = request.getParameter("titleId").trim();
        //回单人
        String userId = request.getParameter("userId").trim();
        //审核人
        String initiator = request.getParameter("initiator").trim();
        //回单时间
        Date createTime = new Date();

//			完成情况
        String mainRemark = request.getParameter("mainRemark").trim();

//			交通方式
        String transport = request.getParameter("transport");
//			里程
        String mileage = request.getParameter("mileage").trim();
//        处理人
        String dealPerformer2 = request.getParameter("dealPerformer2").trim();
//        处理开始时间
        String dealStartTime = request.getParameter("sheetAcceptLimit");
//        处理结束时间
        String dealEndTime = request.getParameter("sheetCompleteLimit");
//        附件
        String sheetAccessories = request.getParameter("sheetAccessories");


//        工单流水号
        String  processInstanceId =StaticMethod.nullObject2String(request.getParameter("processInstanceId"));

        String taskId = request.getParameter("taskId");
        TaskService taskService = (TaskService) getBean("taskService");
        List<Task> taskList = taskService.createTaskQuery().taskId(taskId).list();
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
        entity.setDoPersons(dealPerformer2);
        entity.setAuditor(initiator);
        entity.setReceiveTime(createTime);
        if(!"".equals(dealStartTime)&&null!=dealStartTime){
        	
        	entity.setDealStartTime(sFormat.parse(dealStartTime));
        }
        if(!"".equals(dealEndTime)&&null!=dealEndTime){
        	
        	entity.setDealEndTime(sFormat.parse(dealEndTime));
        }
        entity.setHandleDescription(mainRemark);
        entity.setTransport(transport);
        entity.setMileage(Double.parseDouble(mileage));
        entity.setProcessInstanceId(processInstanceId);
        entity.setSheetAccessories(sheetAccessories);
        
        IPnrTaskTicketService iPnrTaskTicketService = (IPnrTaskTicketService) getBean("pnrTaskTicketService");
        Search search = new Search();
        search.addFilterEqual("processInstanceId", processInstanceId);
        List<PnrTaskTicket> pnrTaskTicketList = iPnrTaskTicketService.search(search);
        if (pnrTaskTicketList != null) {
            PnrTaskTicket pnrTaskTicket = pnrTaskTicketList.get(0);
            pnrTaskTicket.setLastReplyTime(new Date());
            iPnrTaskTicketService.save(pnrTaskTicket);
        }
//        处理人关系表数据保存--start
        String[] personStrings=dealPerformer2.split(",");
        iPnrTaskTicketService.saveOrUpatePerson(processInstanceId, personStrings);
//      处理人关系表数据保存--end

        iPnrTaskTicketHandleService.save(entity);

        return mapping.findForward("success");

    }

    /**
     * 任务审核保存
     */
    public ActionForward goTaskCheck(ActionMapping mapping,
                                     ActionForm form, HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {

//		    工单ID
        String themeId = request.getParameter("themeId");
//		    工单主题
        String theme = request.getParameter("theme");

//			审核时间
        Date createTime = new Date();

//			审核描述
        String mainRemark = request.getParameter("mainRemark").trim();

//			审核意见
        String auditState = request.getParameter("auditState").trim();
//			审核人
        String taskAssignee = request.getParameter("taskAssignee");


        String taskId = request.getParameter("taskId");
        TaskService taskService = (TaskService) getBean("taskService");
        List<Task> taskList = taskService.createTaskQuery().taskId(taskId).list();
        FormService formService = (FormService) getBean("formService");
        TaskFormData taskFormData = formService.getTaskFormData(taskId);
        Map<String, String> map = new HashMap<String, String>();
        for (FormProperty formProperty : taskFormData.getFormProperties()) {
            if (formProperty.isWritable()) {
                String name = formProperty.getId();
                map.put(name, request.getParameter(name));  
                
            }
        }
        
        DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.HOUR, 72);
        calendar.add(Calendar.HOUR, 72);
        String endTime = sFormat.format(calendar.getTime());
        
        map.put("dueDate", endTime);
        
        formService.submitTaskFormData(taskId, map);

        IPnrTaskTicketHandleService pnrService = (IPnrTaskTicketHandleService) getBean("pnrTaskTicketHandleService");
        PnrTaskTicketHandle entity = new PnrTaskTicketHandle();

        entity.setThemeId(themeId);
        entity.setTheme(theme);
        entity.setCheckTime(createTime);
        entity.setOpinion(mainRemark);
        entity.setState(auditState);
        entity.setTaskAssignee(taskAssignee);
        pnrService.save(entity);
        
        if (auditState != null && auditState.equals("auditDismissed")) {
            IPnrTaskTicketService iPnrTaskTicketService = (IPnrTaskTicketService) getBean("pnrTaskTicketService");
            Search search = new Search();
            search.addFilterEqual("processInstanceId", taskList.get(0).getParentTaskId());
            List<PnrTaskTicket> pnrTaskTicketList = iPnrTaskTicketService.search(search);
            if (pnrTaskTicketList != null) {
                PnrTaskTicket pnrTaskTicket = pnrTaskTicketList.get(0);
                pnrTaskTicket.setLastReplyTime(null);
                pnrTaskTicket.setState(3);
                iPnrTaskTicketService.save(pnrTaskTicket);
            }
        }
		request.setAttribute("success", "check");

        return mapping.findForward("success");
    }

   

    /**
     * 任务回复、审核
     */
    public ActionForward todo(ActionMapping mapping,
                              ActionForm form, HttpServletRequest request,
                              HttpServletResponse response) throws Exception {

    	//审核人
    	String dispatch ="";
    	
        TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
        String userId = sessionForm.getUserid();
        String deptId = sessionForm.getDeptid();
        if(deptId.length()>5){
        	deptId=deptId.substring(0,5);
        }
        request.setAttribute("country", deptId);
        request.setAttribute("userId", userId);
        String taskId = request.getParameter("taskId");
        TaskService taskService = (TaskService) getBean("taskService");
        List<Task> taskList = taskService.createTaskQuery().taskId(taskId).list();
        IPnrTaskTicketService pnrTaskTicketService = (IPnrTaskTicketService) getBean("pnrTaskTicketService");
        if (taskList != null && taskList.size() > 0) {
            Task task = taskList.get(0);
            Search search = new Search();
            search.addFilterEqual("processInstanceId", task.getProcessInstanceId());
            SearchResult t = pnrTaskTicketService.searchAndCount(search);
            List<PnrTaskTicket> list = t.getResult();
            PnrTaskTicket pnrTaskTicket = list.get(0);
            
            dispatch = pnrTaskTicket.getInitiator();
            
            String specialty = "";
            String deptNames = "";
            if(null!=pnrTaskTicket){
            	specialty=CommonUtils.getId2NameString(pnrTaskTicket.getSpecialty(), 1, ",");
            	deptNames=CommonUtils.getId2NameString(pnrTaskTicket.getCandidateGroup(), 3, ",");
            }
//          回复message
            IPnrTaskTicketHandleService taskHandleService = (IPnrTaskTicketHandleService) getBean("pnrTaskTicketHandleService");
            
            search.addSortDesc("receiveTime");
            List<PnrTaskTicketHandle> handlelist = taskHandleService.searchAndCount(search).getResult();
            int listsize = handlelist.size();
         
            
            request.setAttribute("processInstanceId", pnrTaskTicket.getProcessInstanceId());
            request.setAttribute("taskId", taskId);
            request.setAttribute("pnrTaskTicket", pnrTaskTicket);
            request.setAttribute("specialty", specialty);
            request.setAttribute("deptNames", deptNames);
            request.setAttribute("candidateGroup", pnrTaskTicket.getCandidateGroup());
          //attachments 
			PnrTaskTicket ticket = new PnrTaskTicket();
			String sheetAccessories = pnrTaskTicketService.getAttachmentNamesByProcessInstanceId(pnrTaskTicket.getProcessInstanceId());
			ticket.setSheetAccessories(sheetAccessories);
			request.setAttribute("sheetMain",ticket);
			//attachments-end
            if (task.getTaskDefinitionKey().equalsIgnoreCase("Audit")) {
                
            	request.setAttribute("PnrTaskHandleList", handlelist);
            	request.setAttribute("listsize", listsize);
                request.setAttribute("candidateGroup", pnrTaskTicket.getCandidateGroup());  
                
                showReplySetAttribute(request,handlelist);
                return mapping.findForward("taskCheck");
            }
            if (task.getTaskDefinitionKey().equalsIgnoreCase("processingReply")) {
            	
            	String initiator =  CommonUtils.getAuditorByUserid(CommonUtils.taskRoleId,request);
            	if("".equals(initiator)||null == initiator){
            		initiator =dispatch ;
            	}
                request.setAttribute("initiator",initiator);
            	return mapping.findForward("todo");
            }
        }
        return mapping.findForward("error");
    }
    
    /**
     * 新增工单初始化
     */
    public ActionForward showNewSheetPage(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response) throws Exception {
        //根据省ID获取地市
        PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
        String province = pnrBaseAreaIdList.getRootAreaId();
        request.setAttribute("city", PartnerCityByUser.getCityByProvince(province));
        CommonUtils.getCompetenceLimit(request);
        request.setAttribute("multiple", "multiple");
        return mapping.findForward("new");
    }

    /**
     * 待办任务列表
     *
     * @return
     */
    public ActionForward listBacklog(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response) throws Exception {
        int pageSize = CommonUtils.PAGE_SIZE;
        String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request, "taskList");
        int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
        int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer.valueOf(pageIndexString).intValue();
        TaskService taskService = (TaskService) getBean("taskService");
        HistoryService historyService = (HistoryService) getBean("historyService");

        TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
        String userId = sessionForm.getUserid();
        List<Task> taskList = taskService.createTaskQuery().taskAssignee(userId).processDefinitionKey(processDefinitionKey).active().listPage(firstResult * pageSize, endResult * pageSize);
        long total = taskService.createTaskQuery().taskAssignee(userId).processDefinitionKey(processDefinitionKey).active().count();
        List<TaskModel> rPnrTaskTicketList = new ArrayList<TaskModel>();
        IPnrTaskTicketService pnrTaskTicketService = (IPnrTaskTicketService) getBean("pnrTaskTicketService");
        for (Task task : taskList) {
            Search search = new Search();
            search.addFilterEqual("processInstanceId", task.getProcessInstanceId());
            SearchResult t = pnrTaskTicketService.searchAndCount(search);
            List<PnrTaskTicket> list = t.getResult();
            if (list != null && list.size() != 0) {
                PnrTaskTicket pnrTaskTicket = list.get(0);
                TaskModel taskTicketModel = new TaskModel();
                taskTicketModel.setTaskId(task.getId());
                taskTicketModel.setInitiator(pnrTaskTicket.getInitiator());
                taskTicketModel.setProcessInstanceId(pnrTaskTicket.getProcessInstanceId());
                taskTicketModel.setSendTime(pnrTaskTicket.getCreateTime());
                taskTicketModel.setTheme(pnrTaskTicket.getTheme());
//              所处环节
                List<HistoricTaskInstance> historicTasks = historyService.createHistoricTaskInstanceQuery().processInstanceId(task.getProcessInstanceId()).orderByTaskId().desc().listPage(0, 1);
                if (historicTasks != null & historicTasks.size() > 0) {
                    taskTicketModel.setStatusName(historicTasks.get(0).getName());
                }
                TawSystemUser pu =CommonUtils.getTawSystemUserByUserId(pnrTaskTicket.getInitiator(),"");
				if(pu!=null){
					taskTicketModel.setDeptId(pu.getDeptid());
					
				}
                rPnrTaskTicketList.add(taskTicketModel);
            }
        }
        request.setAttribute("taskList", rPnrTaskTicketList);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("total", (int) total);
        return mapping.findForward("backlogList");
    }

    /**
     * 快到期的 待办任务列表
     *
     * @return
     */
    public ActionForward listDueBacklog(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        int pageSize = CommonUtils.PAGE_SIZE;
        String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request, "taskList");
        int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
        int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer.valueOf(pageIndexString).intValue();
        TaskService taskService = (TaskService) getBean("taskService");
        TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
        String userId = sessionForm.getUserid();
        List<Task> taskList = taskService.createTaskQuery().taskAssignee(userId).processDefinitionKey(processDefinitionKey).dueBefore(calendar.getTime()).active().listPage(firstResult * pageSize, endResult * pageSize);
        long total = taskService.createTaskQuery().taskAssignee(userId).processDefinitionKey(processDefinitionKey).dueBefore(calendar.getTime()).active().count();
        List<TaskModel> rPnrTaskTicketList = new ArrayList<TaskModel>();
        IPnrTaskTicketService pnrTaskTicketService = (IPnrTaskTicketService) getBean("pnrTaskTicketService");
        for (Task task : taskList) {
            Search search = new Search();
            search.addFilterEqual("processInstanceId", task.getProcessInstanceId());
            SearchResult t = pnrTaskTicketService.searchAndCount(search);
            List<PnrTaskTicket> list = t.getResult();
            if (list != null && list.size() != 0) {
                PnrTaskTicket pnrTaskTicket = list.get(0);
                TaskModel taskTicketModel = new TaskModel();
                taskTicketModel.setTaskId(task.getId());
                taskTicketModel.setInitiator(pnrTaskTicket.getInitiator());
                taskTicketModel.setProcessInstanceId(pnrTaskTicket.getProcessInstanceId());
                taskTicketModel.setSendTime(pnrTaskTicket.getCreateTime());
                taskTicketModel.setTheme(pnrTaskTicket.getTheme());
                rPnrTaskTicketList.add(taskTicketModel);
            }
        }
        request.setAttribute("taskList", rPnrTaskTicketList);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("total", (int) total);
        return mapping.findForward("backlogList");
    }

    /**
     * 代领任务（组任务）
     *
     * @return
     */
    public ActionForward listCollectTasks(ActionMapping mapping, ActionForm form,
                                          HttpServletRequest request, HttpServletResponse response) throws Exception {
        int pageSize = CommonUtils.PAGE_SIZE;
        String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request, "taskList");
        int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
        int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer.valueOf(pageIndexString).intValue();
        TaskService taskService = (TaskService) getBean("taskService");
        TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
        String deptId = sessionForm.getDeptid();
        List<Task> taskList = taskService.createTaskQuery().processDefinitionKey(processDefinitionKey)
                .taskCandidateGroup(deptId).active()
                .listPage(firstResult * pageSize, endResult * pageSize);
        long total = taskService.createTaskQuery().processDefinitionKey(processDefinitionKey).taskCandidateGroup(deptId).active().count();
        List<TaskModel> rPnrTaskTicketList = new ArrayList<TaskModel>();
        IPnrTaskTicketService pnrTaskTicketService = (IPnrTaskTicketService) getBean("pnrTaskTicketService");
        for (Task task : taskList) {
            Search search = new Search();
            search.addFilterEqual("processInstanceId", task.getProcessInstanceId());
            List<PnrTaskTicket> list = pnrTaskTicketService.search(search);
            if (list != null && list.size() != 0) {
                PnrTaskTicket pnrTaskTicket = list.get(0);
                TaskModel taskTicketModel = new TaskModel();
                taskTicketModel.setTaskId(task.getId());
                taskTicketModel.setInitiator(pnrTaskTicket.getInitiator());
                taskTicketModel.setProcessInstanceId(pnrTaskTicket.getProcessInstanceId());
                taskTicketModel.setSendTime(pnrTaskTicket.getCreateTime());
                taskTicketModel.setTheme(pnrTaskTicket.getTheme());
                taskTicketModel.setEndTime(pnrTaskTicket.getEndTime());
                taskTicketModel.setStatusName(pnrTaskTicket.getState()+"");
                TawSystemUser pu =CommonUtils.getTawSystemUserByUserId(pnrTaskTicket.getInitiator(),"");
				if(pu!=null){
					taskTicketModel.setDeptId(pu.getDeptid());
					
				}
                rPnrTaskTicketList.add(taskTicketModel);
            }
        }
        request.setAttribute("taskList", rPnrTaskTicketList);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("total", (int) total);
        return mapping.findForward("collectTaskList");
    }

    /**
     * 认领任务（对应的是在组任务，即从组任务中领取任务）
     *
     * @return
     */
    public ActionForward claimTask(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response) {
        TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
        String userId = sessionForm.getUserid();
        String taskId = request.getParameter("taskId");
        TaskService taskService = (TaskService) getBean("taskService");
        taskService.claim(taskId, userId);
        FormService formService = (FormService) getBean("formService");
        Map<String, String> map = new HashMap<String, String>();
        map.put("taskAssignee", userId);
      
        DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 72);
//        calendar.add(Calendar.MINUTE, 2);
        String endTime = sFormat.format(calendar.getTime());
        
        map.put("dueDate", endTime);
        formService.submitTaskFormData(taskId, map);
        return mapping.findForward("success");
    }


    /**
     * 派单的保存方法
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward performAdd(ActionMapping mapping, ActionForm form,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception {

        DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        //工单主题
        String title = request.getParameter("title").trim();
        //派单人
        String initiator = request.getParameter("initiator").trim();
        // 工单生成时间
        Date createTime = new Date();
//		工单子类型
        String mainFaultNet = request.getParameter("mainFaultNet").trim();
//		站点
        String mainResName = request.getParameter("mainResName").trim();
//		站点ID
        String mainResId = request.getParameter("mainResId").trim();
//		工单内容
        String mainRemark = request.getParameter("mainRemark").trim();

//		计划开始时间
        String sheetAcceptLimit = request.getParameter("sheetAcceptLimit");

//		计划结束时间
        String sheetCompleteLimit = request.getParameter("sheetCompleteLimit");

/*//		故障站点
        String faultResName = request.getParameter("faultResName");
//		故障站点ID
        String faultResId = request.getParameter("faultResId");*/
//		涉及专业
//        String mainSpecialty = request.getParameter("mainSpecialty");
        String[] specialtyStrings = request.getParameterValues("mainSpecialty");
        String mainSpecialty="";
        for(int i=0 ; i<specialtyStrings.length; i++)
        {	if(i==(specialtyStrings.length-1))
        	{
        		mainSpecialty+=specialtyStrings[i];
        	}else
        	{
        		mainSpecialty+=specialtyStrings[i]+",";
        	}
        }
        //接收人
        String candidateUser = request.getParameter("candidateUser");
//		接收组
        String candidateGroup
                = request.getParameter("candidateGroup");
      //新增附件
		String attachments = request.getParameter("sheetAccessories");
		int attachmentsNum = attachments.split(",").length;
        //流程开始
		
		
        FormService formService = (FormService) getBean("formService");
        IdentityService identityService = (IdentityService) getBean("identityService");
        identityService.setAuthenticatedUserId(initiator);
        
        RuntimeService runtimeService = (RuntimeService) getBean("runtimeService");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
       
        String processInstanceId = processInstance.getId();
       
        TaskService taskService = (TaskService) getBean("taskService");
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(processInstanceId).active().list();
        String taskId = taskList.get(0).getId();
        TaskFormData taskFormData = formService.getTaskFormData(taskId);
        Map<String, String> map = new HashMap<String, String>();
      
        for (FormProperty formProperty : taskFormData.getFormProperties()) {
            if (formProperty.isWritable()) {
                String name = formProperty.getId();
                map.put(name, request.getParameter(name));
            }
        }
        formService.submitTaskFormData(taskId, map);
        
        
        
        IPnrTaskTicketService pnrService = (IPnrTaskTicketService) getBean("pnrTaskTicketService");
        PnrTaskTicket entity = new PnrTaskTicket();
        entity.setTheme(title);
        entity.setSubType(mainFaultNet);
        entity.setSite(mainResName);
        entity.setMainResId(mainResId);
        entity.setContent(mainRemark);
        entity.setCreateTime(createTime);
        entity.setStartTime(sFormat.parse(sheetAcceptLimit));
        entity.setEndTime(sFormat.parse(sheetCompleteLimit));
       /* entity.setFailedSite(faultResName);
        entity.setFaultResId(faultResId);*/
        //保存故障字符串
       entity.setSpecialty(mainSpecialty);
       //保存专业与工单的关系--start
        pnrService.saveOrUpateSpecialty(processInstanceId, specialtyStrings);
      //保存专业与工单的关系--end
        entity.setInitiator(initiator);
        entity.setProcessInstanceId(processInstanceId);
        entity.setCandidateUser(candidateUser);
        entity.setCandidateGroup(candidateGroup);
        PnrResConfigMgr pnrResConfigMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
        String[] mainResIds = mainResId.split(",");
        PnrResConfig pnrResConfig = pnrResConfigMgr.find(mainResIds[0]);
        if(pnrResConfig !=null){        	
        	entity.setCity(pnrResConfig.getCity());
        	entity.setCountry(pnrResConfig.getCountry());
        }
        entity.setState(1);
        //附件个数
        entity.setAttachmentsNum(attachmentsNum);
      //attachment--start
		if(!"".equals(attachments)){			
			pnrService.saveOrUpateAttachment(processInstanceId,attachments);
		}
		
        try {
            pnrService.save(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapping.findForward("success");
    }

    /**
     * 按照流程实例ID删除流程
     */
    public ActionForward removeHistoricProcessInstance(ActionMapping mapping, ActionForm form,
                                                       HttpServletRequest request, HttpServletResponse response) throws Exception {
        String processInstanceId = request.getParameter("processInstanceId");
        HistoryService historyService = (HistoryService) getBean("historyService");
        historyService.deleteHistoricProcessInstance(processInstanceId);
        return mapping.findForward("success");
    }
    /**
     * 按照流程实例ID删除流程
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward removeProcessInstance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String processInstanceId = request.getParameter("processInstanceId");
		RuntimeService runtimeService = (RuntimeService) getBean("runtimeService");
		runtimeService.deleteProcessInstance(processInstanceId, "delete");
		
		IPnrTaskTicketService pnrTaskTicketService = (IPnrTaskTicketService) getBean("pnrTaskTicketService");
		
		Search search  = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		SearchResult<PnrTaskTicket> tResult =pnrTaskTicketService.searchAndCount(search);
		List<PnrTaskTicket>  eList= tResult.getResult();
		PnrTaskTicket  entity =eList.get(0);
		
		entity.setState(4);
		pnrTaskTicketService.save(entity);
		
		return mapping.findForward("success");
	}
    /**
     * 查看历史【包含流程跟踪、任务列表（完成和未完成）、流程变量】
     *
     * @return
     */
    public ActionForward viewHistory(ActionMapping mapping, ActionForm form,
                                     HttpServletRequest request, HttpServletResponse response) throws Exception {
        int pageSize = CommonUtils.PAGE_SIZE;
        String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request, "historicTasks");
        int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
        int totalSize = 0;

        HistoryService historyService = (HistoryService) getBean("historyService");
        String processInstanceId = request.getParameter("processInstanceId");
        List<HistoricTaskInstance> historicTasks = historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).listPage(firstResult * pageSize, pageSize);
        totalSize = (int) historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).count();
        List<HistoricVariableInstance> historicVariableInstances = historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list();

        request.setAttribute("historicTasks", historicTasks);
        request.setAttribute("historicTasksPageSize", pageSize);
        request.setAttribute("historicTasksTotal", totalSize);
        request.setAttribute("historicVariableInstances", historicVariableInstances);
        request.setAttribute("historicVariableInstancesPageSize", pageSize);
        request.setAttribute("historicVariableInstancesTotal", historicVariableInstances.size());
        request.setAttribute("processInstanceId", processInstanceId);
        return mapping.findForward("viewHistory");
    }

    /**
     * 流程跟踪
     *
     * @throws Exception
     */
    public void graphHistoryProcessInstance(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse response) throws Exception {
        RepositoryService repositoryService = (RepositoryService) getBean("repositoryService");
        String processInstanceId = request.getParameter("processInstanceId");
        Command<InputStream> cmd = null;
        cmd = new HistoryProcessInstanceDiagramCmd(processInstanceId);

        InputStream is = ((ServiceImpl) repositoryService).getCommandExecutor().execute(cmd);
        response.setContentType("image/png");

        int len = 0;
        byte[] b = new byte[1024];

        while ((len = is.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }

    /**
     * 用户发起的流程（包含已经完成和未完成）
     *
     * @return
     */
    public ActionForward listRunningProcessInstances(ActionMapping mapping, ActionForm form,
                                                     HttpServletRequest request, HttpServletResponse response) {
        int pageSize = CommonUtils.PAGE_SIZE;
        String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request, "taskList");
        int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
        int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer.valueOf(pageIndexString).intValue();
        HistoryService historyService = (HistoryService) getBean("historyService");
        TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
        String userId = sessionForm.getUserid();
        List<HistoricProcessInstance> historicProcessInstancesList =
                historyService.createHistoricProcessInstanceQuery().processDefinitionKey(processDefinitionKey).startedBy(userId)
                        .listPage(firstResult * pageSize, endResult * pageSize);
        long total = historyService.createHistoricProcessInstanceQuery().processDefinitionKey(processDefinitionKey).startedBy(userId).count();
        IPnrTaskTicketService pnrTaskTicketService = (IPnrTaskTicketService) getBean("pnrTaskTicketService");
        List<TaskModel> rPnrTaskTicketList = new ArrayList<TaskModel>();
        for (HistoricProcessInstance historicProcessInstance : historicProcessInstancesList) {
            String processInstanceId = historicProcessInstance.getId();
            TaskModel taskTicketModel = new TaskModel();
            taskTicketModel.setProcessInstanceId(processInstanceId);
            if (historicProcessInstance.getEndTime() != null) {
            	if(historicProcessInstance.getDeleteReason()==null)
            	{
            		taskTicketModel.setStatusName("已归档");
            	}else
            	{
            		taskTicketModel.setStatusName("已删除");
            	}
            } else {
                List<HistoricTaskInstance> historicTasks = historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).orderByTaskId().desc().listPage(0,1);
                if (historicTasks != null & historicTasks.size() > 0) {
                    taskTicketModel.setStatusName(historicTasks.get(0).getName());
                }
            }
            Search search = new Search();
            search.addFilterEqual("processInstanceId", processInstanceId);
            List<PnrTaskTicket> list = pnrTaskTicketService.search(search);
            if (list != null && list.size() != 0) {
                taskTicketModel.setSendTime(list.get(0).getCreateTime());
                taskTicketModel.setTheme(list.get(0).getTheme());
            }
            rPnrTaskTicketList.add(taskTicketModel);
        }
        request.setAttribute("taskList", rPnrTaskTicketList);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("total", (int) total);
        return mapping.findForward("runningProcessInstancesList");
    }

    /**
     * 用户发起的流程（包含已经完成和未完成）
     *
     * @return
     */
    public ActionForward listDeptProcessInstances(ActionMapping mapping, ActionForm form,
                                                  HttpServletRequest request, HttpServletResponse response) {
        int pageSize = CommonUtils.PAGE_SIZE;
        String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request, "taskList");
        int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
        int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer.valueOf(pageIndexString).intValue();
        HistoryService historyService = (HistoryService) getBean("historyService");
        TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
        String involvedUser = sessionForm.getUserid();
        ITawSystemUserManager tawSystemUserManager = (ITawSystemUserManager) getBean("itawSystemUserManager");
        List<String> userIdList = tawSystemUserManager.getUserIdsBydeptid(sessionForm.getDeptid());
        for (String s : userIdList) {
            if (involvedUser == null) {
                involvedUser = s;
            } else {
                involvedUser = involvedUser + "','" + s;
            }
        }
        List<HistoricProcessInstance> historicProcessInstancesList = historyService.createNativeHistoricProcessInstanceQuery().sql("SELECT RES.* FROM ACT_HI_PROCINST RES where " +
                "(exists(select LINK.USER_ID_ from ACT_HI_IDENTITYLINK LINK where USER_ID_ in ('" + involvedUser + "') and LINK.PROC_INST_ID_ = RES.ID_))  and RES.PROC_DEF_ID_ like #{processDefinitionIdLike} and RES.END_TIME_ IS NULL").parameter("processDefinitionIdLike", processDefinitionKey + "%").listPage(firstResult * pageSize, endResult * pageSize);
        long total = historyService.createNativeHistoricProcessInstanceQuery().sql("SELECT count(*) FROM ACT_HI_PROCINST RES where " +
                "(exists(select LINK.USER_ID_ from ACT_HI_IDENTITYLINK LINK where USER_ID_ in ('" + involvedUser + "') and LINK.PROC_INST_ID_ = RES.ID_))  and RES.PROC_DEF_ID_ like #{processDefinitionIdLike} and RES.END_TIME_ IS NULL").parameter("processDefinitionIdLike", processDefinitionKey + "%").count();
        IPnrTaskTicketService pnrTaskTicketService = (IPnrTaskTicketService) getBean("pnrTaskTicketService");
        List<TaskModel> rPnrTaskTicketList = new ArrayList<TaskModel>();
        for (HistoricProcessInstance historicProcessInstance : historicProcessInstancesList) {
            String processInstanceId = historicProcessInstance.getId();
            TaskModel taskTicketModel = new TaskModel();
            taskTicketModel.setProcessInstanceId(processInstanceId);
            if (historicProcessInstance.getEndTime() != null) {
                taskTicketModel.setStatusName("已归档");
            } else {
                List<HistoricTaskInstance> historicTasks = historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).orderByTaskId().desc().listPage(0,1);
                if (historicTasks != null & historicTasks.size() > 0) {
                    taskTicketModel.setStatusName(historicTasks.get(0).getName());
                }
            }
            Search search = new Search();
            search.addFilterEqual("processInstanceId", processInstanceId);
            List<PnrTaskTicket> list = pnrTaskTicketService.search(search);
            if (list != null && list.size() != 0) {
                taskTicketModel.setSendTime(list.get(0).getCreateTime());
                taskTicketModel.setTheme(list.get(0).getTheme());
            }
            rPnrTaskTicketList.add(taskTicketModel);
        }
        request.setAttribute("taskList", rPnrTaskTicketList);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("total", (int) total);
        return mapping.findForward("runningProcessInstancesList");
    }

    /**
     * 用户参与的流程（包含已经完成和未完成）
     *
     * @return
     */
    public ActionForward listInvolvedFinishedProcessInstances(ActionMapping mapping, ActionForm form,
                                                              HttpServletRequest request, HttpServletResponse response) {
        int pageSize = CommonUtils.PAGE_SIZE;
        String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request, "taskList");
        int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
        int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer.valueOf(pageIndexString).intValue();
        HistoryService historyService = (HistoryService) getBean("historyService");
        TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
        String userId = sessionForm.getUserid();
        List<HistoricProcessInstance> historicProcessInstancesList =
                historyService.createHistoricProcessInstanceQuery().processDefinitionKey(processDefinitionKey).
                        involvedUser(userId).finished()
                        .listPage(firstResult * pageSize, endResult * pageSize);
        long total = historyService.createHistoricProcessInstanceQuery().processDefinitionKey(processDefinitionKey).
                involvedUser(userId).finished().count();
        IPnrTaskTicketService pnrTaskTicketService = (IPnrTaskTicketService) getBean("pnrTaskTicketService");
        List<TaskModel> rPnrTaskTicketList = new ArrayList<TaskModel>();
        for (HistoricProcessInstance historicProcessInstance : historicProcessInstancesList) {
            String processInstanceId = historicProcessInstance.getId();
            TaskModel taskTicketModel = new TaskModel();
            taskTicketModel.setProcessInstanceId(historicProcessInstance.getId());
            Search search = new Search();
            search.addFilterEqual("processInstanceId", historicProcessInstance.getId());
            List<HistoricTaskInstance> historicTasks = historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).orderByTaskId().desc().list();
            if (historicTasks != null & historicTasks.size() > 0) {
            	if(historicTasks.get(0).getDeleteReason().equals("delete")){
            		taskTicketModel.setStatusName(CommonUtils.taskDetele);
            		
            	}else{
            		
            		taskTicketModel.setStatusName(CommonUtils.taskComplete);
            	}
            }
            SearchResult t = pnrTaskTicketService.searchAndCount(search);
            List<PnrTaskTicket> list = t.getResult();
            if (list != null && list.size() != 0) {
                taskTicketModel.setSendTime(list.get(0).getCreateTime());
                taskTicketModel.setTheme(list.get(0).getTheme());
                taskTicketModel.setInitiator(list.get(0).getInitiator());
                TawSystemUser pu =CommonUtils.getTawSystemUserByUserId(list.get(0).getInitiator(),"");
                if(pu!=null){
                	taskTicketModel.setDeptId(pu.getDeptid());
                	
                }
            }

            rPnrTaskTicketList.add(taskTicketModel);
        }
        request.setAttribute("taskList", rPnrTaskTicketList);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("total", (int) total);
        return mapping.findForward("involvedProcessInstancesList");
    }

    /**
     * 用户参与的流程（包含已经完成和未完成）
     *
     * @return
     */
    public ActionForward listInvolvedUnfinishedProcessInstances(ActionMapping mapping, ActionForm form,
                                                                HttpServletRequest request, HttpServletResponse response) {
        int pageSize = CommonUtils.PAGE_SIZE;
        String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request, "taskList");
        int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
        int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer.valueOf(pageIndexString).intValue();
        HistoryService historyService = (HistoryService) getBean("historyService");
        TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
        String userId = sessionForm.getUserid();
        List<HistoricProcessInstance> historicProcessInstancesList =
                historyService.createHistoricProcessInstanceQuery().processDefinitionKey(processDefinitionKey)
                        .involvedUser(userId).unfinished()
                        .listPage(firstResult * pageSize, endResult * pageSize);
        long total = historyService.createHistoricProcessInstanceQuery().processDefinitionKey(processDefinitionKey)
                .involvedUser(userId).unfinished().count();
        IPnrTaskTicketService pnrTaskTicketService = (IPnrTaskTicketService) getBean("pnrTaskTicketService");
        List<TaskModel> rPnrTaskTicketList = new ArrayList<TaskModel>();
        for (HistoricProcessInstance historicProcessInstance : historicProcessInstancesList) {
            String processInstanceId = historicProcessInstance.getId();
            TaskModel taskTicketModel = new TaskModel();
            taskTicketModel.setProcessInstanceId(historicProcessInstance.getId());
            Search search = new Search();
            search.addFilterEqual("processInstanceId", historicProcessInstance.getId());
            List<HistoricTaskInstance> historicTasks = historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).orderByTaskId().desc().listPage(0, 1);
            if (historicTasks != null & historicTasks.size() > 0) {
                taskTicketModel.setStatusName(historicTasks.get(0).getName());
            }
            SearchResult t = pnrTaskTicketService.searchAndCount(search);
            List<PnrTaskTicket> list = t.getResult();
            if (list != null && list.size() != 0) {
                taskTicketModel.setSendTime(list.get(0).getCreateTime());
                taskTicketModel.setTheme(list.get(0).getTheme());
                taskTicketModel.setInitiator(list.get(0).getInitiator());
                TawSystemUser pu =CommonUtils.getTawSystemUserByUserId(list.get(0).getInitiator(),"");
                if(pu!=null){
                	taskTicketModel.setDeptId(pu.getDeptid());
                	
                }
            }
            rPnrTaskTicketList.add(taskTicketModel);
        }
        request.setAttribute("taskList", rPnrTaskTicketList);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("total", (int) total);
        return mapping.findForward("involvedProcessInstancesList");
    }

    /**
     * 工单查询统计
     */
    public ActionForward workOrderStatistics(ActionMapping mapping, ActionForm form,
                                             HttpServletRequest request, HttpServletResponse response) {
        DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String beginTime = request.getParameter("sheetAcceptLimit");
        String endTime = request.getParameter("sheetCompleteLimit");
        if (beginTime == null || beginTime.equals("")) {
            beginTime = sFormat.format(new Date());
        }
        if (endTime == null || endTime.equals("")) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR_OF_DAY, 1);
            endTime = sFormat.format(calendar.getTime());
        }
        String[] subType = request.getParameterValues("subType");
        IPnrTaskTicketService pnrTaskTicketService = (IPnrTaskTicketService) getBean("pnrTaskTicketService");
        List<WorkOrderStatisticsModel> workOrderStatisticsModels = pnrTaskTicketService.workOrderStatistics(beginTime, endTime, "");
        request.setAttribute("taskList", workOrderStatisticsModels);
        request.setAttribute("pageSize", workOrderStatisticsModels.size());
        request.setAttribute("total", workOrderStatisticsModels.size());
        return mapping.findForward("performStatisticsQuery");
    }

    /**
     * 工单查询方法
     *
     * @return
     */
    public ActionForward workOrderQuery(ActionMapping mapping, ActionForm form,
                                        HttpServletRequest request, HttpServletResponse response) {
        int pageSize = CommonUtils.PAGE_SIZE;
        String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request, "taskList");
        int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
        int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer.valueOf(pageIndexString).intValue();
        DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String beginTime = request.getParameter("sheetAcceptLimit");
        String endTime = request.getParameter("sheetCompleteLimit");
        String city = request.getParameter("mainCity");
        String theme = request.getParameter("title").trim();
//        String workerid = "yt_admin";
        String workerid = request.getParameter("workerid");
        
        if (beginTime == null || beginTime.equals("")) {
            beginTime = sFormat.format(new Date());
        }
        if (endTime == null || endTime.equals("")) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR_OF_DAY, 1);
            endTime = sFormat.format(calendar.getTime());
        }
        String subType = request.getParameter("mainFaultNet");
        IPnrTaskTicketService pnrTaskTicketService = (IPnrTaskTicketService) getBean("pnrTaskTicketService");
      //  List<TaskModel> taskModels = pnrTaskTicketService.workOrderQuery(workerid,beginTime, endTime, subType, theme, city, firstResult * CommonUtils.PAGE_SIZE, endResult * CommonUtils.PAGE_SIZE);
        //int total = pnrTaskTicketService.workOrderCount(workerid,beginTime, endTime, subType, theme, city);
        //request.setAttribute("taskList", taskModels);
        request.setAttribute("pageSize", pageSize);
        //request.setAttribute("total", (int) total);
        return mapping.findForward("taskQueryList");
    }

    /**
     * 显示工单查询页面
     */
    public ActionForward showQueryPage(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        //根据省ID获取地市
        PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
        String province = pnrBaseAreaIdList.getRootAreaId();
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        String endTime = dateFormat.format(calendar.getTime());  
        
        request.setAttribute("startTime", dateFormat.format(date));
        request.setAttribute("endTime", endTime);
        request.setAttribute("isPartner", 1);
        request.setAttribute("city", PartnerCityByUser.getCityByProvince(province));
        return mapping.findForward("query");
    }

    
    /**
     * 显示工单统计详细信息呈现
     */
    public ActionForward performStatisticsQuery(ActionMapping mapping, ActionForm form,
                                                HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        request.setAttribute("taskList", "");
        request.setAttribute("pageSize", CommonUtils.PAGE_SIZE);
        request.setAttribute("total", CommonUtils.PAGE_SIZE);


        return mapping.findForward("performStatisticsQuery");
    }

    /**
     * 显示工单统计页面
     */
    public ActionForward showStatisticsPage(ActionMapping mapping, ActionForm form,
                                            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        ITawSystemDictTypeManager taw = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
        List<TawSystemDictType> taskList = taw.getDictSonsByDictid("1012201");
        List<TawSystemDictType> troubleList = taw.getDictSonsByDictid("1011101");

        request.setAttribute("taskList", taskList);
        request.setAttribute("troubleList", troubleList);


        return mapping.findForward("showStatisticsPage");
    }
    /**
     * 用于显示附件
     * @param request
     * @param handlist
     */
   private void showReplySetAttribute(HttpServletRequest request,List<PnrTaskTicketHandle> handlist){
	   
	   int handSize = handlist.size();
       for(int i=0;i<handSize;i++){
       	request.setAttribute("sheetReply"+i, handlist.get(i));
       }
   }
}
 



