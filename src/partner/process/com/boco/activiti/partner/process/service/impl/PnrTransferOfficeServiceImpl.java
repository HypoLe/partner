package com.boco.activiti.partner.process.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.boco.activiti.partner.process.dao.IPnrTransferOfficeDao;
import com.boco.activiti.partner.process.dao.IPnrTransferOfficeJDBCDao;
import com.boco.activiti.partner.process.model.PnrActReviewStaff;
import com.boco.activiti.partner.process.model.PnrActRuCountersign;
import com.boco.activiti.partner.process.model.PnrAndroidPhotoFile;
import com.boco.activiti.partner.process.model.PnrTransferOffice;
import com.boco.activiti.partner.process.model.TransferMaleGuestInform;
import com.boco.activiti.partner.process.po.AndroidQuery;
import com.boco.activiti.partner.process.po.AndroidWorkOrderTask;
import com.boco.activiti.partner.process.po.ConditionQueryModel;
import com.boco.activiti.partner.process.po.CycleCollarReportModel;
import com.boco.activiti.partner.process.po.FirstVerifyReportModel;
import com.boco.activiti.partner.process.po.MaleGuestSelectAttribute;
import com.boco.activiti.partner.process.po.MaleSceneDetailModel;
import com.boco.activiti.partner.process.po.MaleSceneStatisticsModel;
import com.boco.activiti.partner.process.po.MaterialQuantityModel;
import com.boco.activiti.partner.process.po.ParameterModel;
import com.boco.activiti.partner.process.po.SecondInspectionReportModel;
import com.boco.activiti.partner.process.po.StatisticsMaterialInforModel;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.activiti.partner.process.po.TransferMaleGuestFlag;
import com.boco.activiti.partner.process.po.TransferMaleGuestReturn;
import com.boco.activiti.partner.process.po.WorkOrderStatisticsModel;
import com.boco.activiti.partner.process.service.IPnrTransferOfficeService;
import com.boco.activiti.partner.process.service.IProcessTaskService;
import com.boco.activiti.partner.process.service.PnrActReviewStaffService;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.form.PersonNameUrlForm;
import com.boco.eoms.common.log.BocoLog;
import com.boco.eoms.commons.accessories.dao.TawCommonsAccessoriesDao;
import com.boco.eoms.commons.accessories.model.TawCommonsAccessories;
import com.boco.eoms.commons.accessories.webapp.form.TawCommonsAccessoriesForm;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.netresource.dao.IEomsDao;
import com.boco.eoms.partner.process.util.CommonUtils;
import com.boco.eoms.partner.process.util.MaleGuestReturnThead;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

/**
 * 
 */
public class PnrTransferOfficeServiceImpl extends
		CommonGenericServiceImpl<PnrTransferOffice> implements
		IPnrTransferOfficeService {
	private static Logger logger = Logger.getLogger(PnrTransferOfficeServiceImpl.class);
	private IProcessTaskService processTaskService = (IProcessTaskService) ApplicationContextHolder.getInstance().getBean("processTaskService");

	private IPnrTransferOfficeDao pnrTransferOfficeDao;
	private IPnrTransferOfficeJDBCDao pnrTransferOfficeDaoJDBC;
	private HistoryService historyService;
	private DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


	public IPnrTransferOfficeDao getPnrTransferOfficeDao() {
		return pnrTransferOfficeDao;
	}

	public void setPnrTransferOfficeDao(
			IPnrTransferOfficeDao pnrTransferOfficeDao) {
		this.pnrTransferOfficeDao = pnrTransferOfficeDao;
		setCommonGenericDao(pnrTransferOfficeDao);
	}

	public IPnrTransferOfficeJDBCDao getPnrTransferOfficeDaoJDBC() {
		return pnrTransferOfficeDaoJDBC;
	}

	public void setPnrTransferOfficeDaoJDBC(
			IPnrTransferOfficeJDBCDao pnrTransferOfficeDaoJDBC) {
		this.pnrTransferOfficeDaoJDBC = pnrTransferOfficeDaoJDBC;
	}

	public HistoryService getHistoryService() {
		return historyService;
	}

	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

	@Override
	public String getAttachmentNamesByProcessInstanceId(String processInstanceId) {
		// TODO Auto-generated method stub
		return pnrTransferOfficeDaoJDBC
				.getAttachmentNamesByProcessInstanceId(processInstanceId);
	}
	

	@Override
	public void saveOrUpateAttachment(String processInstanceId,
			String accessoriesNames) {
		// TODO Auto-generated method stub
		pnrTransferOfficeDaoJDBC.saveOrUpateAttachment(processInstanceId,
				accessoriesNames);
	}

	
	
	@Override
	public void saveAttachment(String processInstanceId, String accessoriesNames,String step) {
		// TODO Auto-generated method stub
		pnrTransferOfficeDaoJDBC.saveAttachment(processInstanceId, accessoriesNames,step);
	}
	
	//多个附件框，上传附件方法
	public void saveMultipleAttachments(String processInstanceId,
			String accessoriesNames,String step,String linkName,String attributeName){
		pnrTransferOfficeDaoJDBC.saveMultipleAttachments(processInstanceId,
				accessoriesNames,step,linkName,attributeName);
	}
	
	/**
	 * 多附件回显
	 * 
	 * @param processInstanceId
	 *            流程号
	 * @param step
	 *            所在环节步骤值
	 * @param linkName
	 *            所在环节KEY
	 * @param attributeName
	 *            界面中附件插件的 idField和name（保持一致）
	 * @return
	 */
	public Map<String, String> echoMultipleAttachments(
			String processInstanceId, String step, String linkName,
			String attributeName) {
		return pnrTransferOfficeDaoJDBC.echoMultipleAttachments(
				processInstanceId, step, linkName, attributeName);
	}

	

	@Override
	public List<TaskModel> workOrderQuery(String deptId, String workerid,
			String beginTime, String endTime, String subType, String theme,
			String city, int beginNum, int endNum) {
		return pnrTransferOfficeDaoJDBC.workOrderQuery(deptId, workerid,
				beginTime, endTime, subType, theme, city, beginNum, endNum);
	}

	public List<TaskModel> workOrderQuery1(String flag, String deptId,
			String workerid, String beginTime, String endTime, String subType,
			String theme, String city, int beginNum, int endNum) {
		return pnrTransferOfficeDaoJDBC.workOrderQuery1(flag, deptId, workerid,
				beginTime, endTime, subType, theme, city, beginNum, endNum);
	}

	public List<TaskModel> workOrderMaleGuestQuery(String searchRange,
			String userId, String flag, String deptId, String workerid,
			String beginTime, String endTime, String subType, String theme,
			String city, int beginNum, int endNum) {
		return pnrTransferOfficeDaoJDBC.workOrderMaleGuestQuery(searchRange,
				userId, flag, deptId, workerid, beginTime, endTime, subType,
				theme, city, beginNum, endNum);
	}

	public List<TaskModel> workOrderQueryAdmin(String userId, String flag,
			String deptId, String workerid, String beginTime, String endTime,
			String subType, String theme, String city, int beginNum, int endNum) {
		return pnrTransferOfficeDaoJDBC.workOrderQueryAdmin(userId, flag,
				deptId, workerid, beginTime, endTime, subType, theme, city,
				beginNum, endNum);
	}

	@Override
	public int workOrderCount(String deptId, String workerid, String beginTime,
			String endTime, String subType, String theme, String city) {
		return pnrTransferOfficeDaoJDBC.workOrderCount(deptId, workerid,
				beginTime, endTime, subType, theme, city);
	}

	@Override
	public int workOrderCount1(String flag, String deptId, String workerid,
			String beginTime, String endTime, String subType, String theme,
			String city) {
		return pnrTransferOfficeDaoJDBC.workOrderCount1(flag, deptId, workerid,
				beginTime, endTime, subType, theme, city);
	}

	@Override
	public int workOrderMaleGuestCount(String searchRange, String userId,
			String flag, String deptId, String workerid, String beginTime,
			String endTime, String subType, String theme, String city) {
		return pnrTransferOfficeDaoJDBC.workOrderMaleGuestCount(searchRange,
				userId, flag, deptId, workerid, beginTime, endTime, subType,
				theme, city);
	}

	@Override
	public int workOrderCountAdmin(String userId, String flag, String deptId,
			String workerid, String beginTime, String endTime, String subType,
			String theme, String city) {
		return pnrTransferOfficeDaoJDBC.workOrderCountAdmin(userId, flag,
				deptId, workerid, beginTime, endTime, subType, theme, city);
	}

	public String getLoginPersonStatus(String userId,
			PnrTransferOffice pnrTransferOffice) {
		return pnrTransferOfficeDaoJDBC.getLoginPersonStatus(userId,
				pnrTransferOffice);
	}
	

	@Override
	public String getLoginPersonStatusToPrecheck(String userId,
			PnrTransferOffice pnrTransferOffice) {
		return pnrTransferOfficeDaoJDBC.getLoginPersonStatusToPrecheck(userId, pnrTransferOffice);
	}
	
	@Override
	public String getLoginPersonStatusToNewPrecheck(String userId,
			PnrTransferOffice pnrTransferOffice) {
		
		return pnrTransferOfficeDaoJDBC.getLoginPersonStatusToNewPrecheck(userId, pnrTransferOffice);
	}

	public void performAdd(PnrTransferOffice maleGuest) {
		// 一切判断都是上层，这层单纯接收数据，存数据，发单！
		DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		// 施工队短信接收人
		String msgRecive = maleGuest.getTaskAssignee();
		//外包公司短信接收人
		String msgDaiwei = maleGuest.getInitiator();
		//传输局短信接收人
		String msgCSJ = maleGuest.getTransferOfficeId();

		String taskAssigneeJSON = "";
		String dueDateString = sFormat.format(maleGuest.getCreateTime()
				.getTime()
				+ maleGuest.getProcessLimit() * 60 * 60 * 1000);

		String processInstanceId = maleGuest.getProcessInstanceId();
		String initiator = maleGuest.getInitiator();

		taskAssigneeJSON = "[{id:'" + maleGuest.getTaskAssignee()
				+ "',nodeType:'user',categoryId:'taskAssignee'}]";

		FormService formService = (FormService) ApplicationContextHolder
				.getInstance().getBean("formService");
		IdentityService identityService = (IdentityService) ApplicationContextHolder
				.getInstance().getBean("identityService");
		identityService.setAuthenticatedUserId(initiator);
		RuntimeService runtimeService = (RuntimeService) ApplicationContextHolder
				.getInstance().getBean("runtimeService");
		TaskService taskService = (TaskService) ApplicationContextHolder
				.getInstance().getBean("taskService");

		if (processInstanceId == null || "".equals(processInstanceId)) {
			ProcessInstance processInstance = runtimeService
					.startProcessInstanceByKey("myMaleGuestProcess");
			processInstanceId = processInstance.getId();
		}

		List<Task> taskList = taskService.createTaskQuery().processInstanceId(
				processInstanceId).active().list();
		String taskId = taskList.get(0).getId();

		Map<String, String> map = new HashMap<String, String>();
		map.put("dueDate", dueDateString);//处理时限
		map.put("taskAssignee", maleGuest.getTaskAssignee());
		if (maleGuest.getTaskAssignee() != null
				&& !"".equals(maleGuest.getTaskAssignee())) {

			formService.submitTaskFormData(taskId, map);
			
			// 流程结束
			if (maleGuest.getState() == 6) {
				maleGuest.setOneInitiator(maleGuest.getInitiator());
				maleGuest.setInitiator(maleGuest.getTaskAssignee());
			}

			IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) ApplicationContextHolder
					.getInstance().getBean("pnrTransferOfficeService");

			maleGuest.setProcessInstanceId(processInstanceId);
			maleGuest.setTaskAssigneeJSON(taskAssigneeJSON);
			try {
				maleGuest.setSendTime(sFormat.parse(sFormat.format(new Date())));
				maleGuest.setEndTime(sFormat.parse(dueDateString));
			} catch (ParseException e) {
				e.printStackTrace();
			}

			//新增将流转环节添加到主表中 start
			processTaskService.setTvalueOfTask(processInstanceId, msgRecive, maleGuest, PnrTransferOffice.class, null, null,false);
			//新增将流转环节添加到主表中 end
			pnrTransferOfficeService.save(maleGuest);
			// 发短信的内容

			String msg = "公客工单号:" + maleGuest.getProcessInstanceId() + ";障碍号码:"
					+ maleGuest.getBarrierNumber() + ";审稿时间:" +sFormat.format( maleGuest.getCreateTime())+";派单时间："+sFormat.format(new Date())+";联系方式:"+
					maleGuest.getConnectPerson()+";地址："+maleGuest.getInstallAddress()+";局站："+maleGuest.getStationName()+
					";接入方式："+maleGuest.getPattern();
//			System.out.println(msg);
			 CommonUtils.sendMsg(msg, msgRecive);
			 CommonUtils.sendMsg(msg, msgDaiwei);
			 CommonUtils.sendMsg(msg, msgCSJ);
		}else{
			TransferMaleGuestReturn maleGuestReturn = new TransferMaleGuestReturn();
			maleGuestReturn.setConfCRMTicketNo(maleGuest.getMaleGuestNumber());
			maleGuestReturn.setFlag("4");
			maleGuestReturn.setReason_id("0");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			maleGuestReturn.setBack_dt(sdf.format(new Date()));
			maleGuestReturn.setBack_info("系统自动");
			maleGuestReturn.setBack_userid("系统自动");
			maleGuestReturn.setBack_username("系统自动");
			maleGuestReturn.setReason_name("系统自动");
	Thread aThread = new Thread(new MaleGuestReturnThead(sFormat
			.format(new Date()),
			CommonUtils.GK_MALE_GUEST_RECEIPT_METHOD, maleGuestReturn));
	aThread.start();
		}
		
		

	}

	@Override
	public TransferMaleGuestFlag getMaleGuestData(String workOrderNo) {

		return pnrTransferOfficeDaoJDBC.getMaleGuestFlagData(workOrderNo);
	}

	@Override
	public TransferMaleGuestReturn getMaleGuestReturnData(String workOrderNo) {

		return pnrTransferOfficeDaoJDBC.getMaleGuestReturnData(workOrderNo);
	}

	@Override
	public List<PersonNameUrlForm> getLoginRole(String userId) {

		return pnrTransferOfficeDaoJDBC.getLoginRole(userId);
	}

	@Override
	public List<WorkOrderStatisticsModel> transferOfficeStatistics() {

		return pnrTransferOfficeDaoJDBC.transferOfficeStatistics();
	}

	public String calculateThePercentage(Double a, Double b) {

		return pnrTransferOfficeDaoJDBC.calculateThePercentage(a, b);
	}

	@Override
	public String getMaleGuestNumberByThemeId(String themeId) {
		return pnrTransferOfficeDaoJDBC.getMaleGuestNumberByThemeId(themeId);
	}

	@Override
	public void precheckAdd(PnrTransferOffice precheck)
	  {
	    DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String taskAssigneeJSON = "";
	    String processInstanceId = precheck.getProcessInstanceId();
	    taskAssigneeJSON = "[{id:'" + precheck.getTaskAssignee() + 
	      "',nodeType:'user',categoryId:'taskAssignee'}]";

	    FormService formService = (FormService)
	      ApplicationContextHolder.getInstance().getBean("formService");
	    ProcessEngine processEngine = (ProcessEngine)
	      ApplicationContextHolder.getInstance().getBean("processEngine");

	    ProcessInstance instance = processEngine.getRuntimeService().startProcessInstanceByKey
	      ("myTransferInterfaceProcess");
	    processInstanceId = instance.getId();

	    TaskService taskService = processEngine.getTaskService();
	    List taskList = taskService.createTaskQuery().processInstanceId(
	      processInstanceId).active
	      ().list();
	    String taskId = ((Task)taskList.get(0)).getId();
	    taskService.setAssignee(taskId, precheck.getInitiator());

	    if (precheck.getState().intValue() == 6) {
	      precheck.setOneInitiator(precheck.getInitiator());
	      precheck.setInitiator(precheck.getTaskAssignee());
	    }
	    TaskFormData taskFormData = formService.getTaskFormData(taskId);
	    Map map = new HashMap();
	    String time = sFormat.format(new Date());
	    for (int i = 0; i < taskFormData.getFormProperties().size(); i++) {
	      String name = ((FormProperty)taskFormData.getFormProperties().get(i)).getId();
	      if (i == 0)
	        map.put(name, precheck.getTaskAssignee());
	      else if (i == 1)
	        map.put(name, time);

	    }

	    String epiboly = precheck.getTaskAssignee();
	    if ((epiboly != null) && (!("".equals(epiboly))))
	    {
	      formService.submitTaskFormData(taskId, map);
	    }
	    IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService)
	      ApplicationContextHolder.getInstance().getBean("pnrTransferOfficeService");

	    precheck.setProcessInstanceId(processInstanceId);
	    precheck.setTaskAssigneeJSON(taskAssigneeJSON);

	    precheck.setSendTime(new Date());

	    pnrTransferOfficeService.save(precheck);
	    
	    //附件处理 
	    String precheckId = precheck.getProcessInstanceId();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	    TawCommonsAccessoriesDao accessoriesDao = (TawCommonsAccessoriesDao)
	      ApplicationContextHolder.getInstance().getBean("tawCommonsAccessoriesDao");
	    List list = precheck.getList();
	    String timeNames = "";
	    int count = 0;
	    if ((list != null) && (list.size() > 0)) {
	      for (Iterator localIterator = list.iterator(); localIterator.hasNext(); ) {
	    	  Map oneData = (Map)localIterator.next();

	        String url = (String)oneData.get("url");

	        String name = (String)oneData.get("name");
	        String[] subUrl = name.split("/");
	        String realName = subUrl[(subUrl.length - 1)];
	        String timeName = sdf.format(new Date()) + 
	          realName.substring(realName.indexOf("."));
	        String length = (String)oneData.get("length");

	        TawCommonsAccessories accessories = new TawCommonsAccessories();
	        accessories.setAccessoriesCnName(realName);
	        accessories.setAccessoriesName(timeName);
	        accessories.setAccessoriesPath(url);
	        if ((length != null) && (!("".equals(length))))
	        {
	          accessories.setAccessoriesSize(Long.parseLong(length));
	        }
	        accessories.setAccessoriesUploadTime(
	          StaticMethod.getLocalTime());
	        accessories.setAppCode("pnrActTransferOffice");
	        accessoriesDao.saveTawCommonsAccessories(accessories);

	        if (count == 0)
	          timeNames = "'" + timeName + "'";
	        else
	          timeNames = timeNames + "," + "'" + timeName + "'";

	        ++count;
	      }

	      saveOrUpateAttachment(precheckId, timeNames);
	    }
	  }

	
	@Override
	public void newPrecheckAdd(PnrTransferOffice precheck)
	  {
		//短信接收人
		String messagePerson = "";
		String message="";
	    DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String taskAssigneeJSON = "";
	    String processInstanceId = precheck.getProcessInstanceId();
	    taskAssigneeJSON = "[{id:'" + precheck.getTaskAssignee() + 
	      "',nodeType:'user',categoryId:'auditor'}]";

	    FormService formService = (FormService)
	      ApplicationContextHolder.getInstance().getBean("formService");
	    ProcessEngine processEngine = (ProcessEngine)
	      ApplicationContextHolder.getInstance().getBean("processEngine");

	    ProcessInstance instance = processEngine.getRuntimeService().startProcessInstanceByKey
	      ("newTwoTransferPrecheck");

	    TaskService taskService = processEngine.getTaskService();
	    List taskList = taskService.createTaskQuery().processInstanceId(
	      processInstanceId).active
	      ().list();
	    Task task = ((Task)taskList.get(0));
	    String taskId = task.getId();
	    String taskDef= task.getTaskDefinitionKey();
	    String handleKey="";
	    String assignee="";
	    if(taskDef.equals("cityManageExamine")){
	    	handleKey="cityManageHandleState";
	    }else{
	    	handleKey="provinceManageHandleState";	    	
	    }
	    if( precheck.getTaskAssignee()!=null){
	    	assignee = precheck.getTaskAssignee();
	    }else{
	    	assignee="superUser";
	    	precheck.setTaskAssignee(assignee);
	    }
	    Map map = new HashMap();
	    String time = sFormat.format(new Date());
	    map.put("nextTaskAssignee",assignee);    
	    map.put(handleKey,"handle");
	    
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    formService.submitTaskFormData(taskId, map);	    
	  
	    IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService)
	      ApplicationContextHolder.getInstance().getBean("pnrTransferOfficeService");
	    
	    if(precheck.isWorkFlag()){
	    	Search search = new Search();
	    	search.addFilterEqual("processInstanceId", precheck.getProcessInstanceId());
	    	List<PnrTransferOffice> list = pnrTransferOfficeService.search(search);
	    	PnrTransferOffice pnrTransferOffice = null;
	    	if(list!=null && list.size()>0){
	    		pnrTransferOffice = list.get(0);
	    	}
	    	pnrTransferOffice.setConnectPerson(precheck.getConnectPerson());
	    	pnrTransferOffice.setInitiator(precheck.getInitiator());
	    	pnrTransferOffice.setOneInitiator(precheck.getOneInitiator());
	    	pnrTransferOffice.setTaskAssignee(precheck.getTaskAssignee());
	    	pnrTransferOffice.setFaultSource(precheck.getFaultSource());
	    	pnrTransferOffice.setCreateTime(precheck.getCreateTime());
	    	pnrTransferOffice.setSecondSendTime(new Date());
	    	pnrTransferOffice.setMaleGuestNumber(precheck.getMaleGuestNumber());
	    	pnrTransferOffice.setState(0);
	    	pnrTransferOffice.setIsDistribute("1");
	    	pnrTransferOfficeService.save(pnrTransferOffice);
	    	
	    	
	    	messagePerson = precheck.getTaskAssignee();
	    	//发短信
	    	String msg = "工单号："+pnrTransferOffice.getProcessInstanceId()+";主题："+pnrTransferOffice.getTheme()+";"+message;
	 	    
        	CommonUtils.sendMsg(msg, messagePerson);
	    }
	  }
	
	
	@Override
	public void threePrecheckAdd(PnrTransferOffice precheck) {
		//短信接收人
		String messagePerson = "";
		String message="";
	    DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String taskAssigneeJSON = "";
	    String processInstanceId = precheck.getProcessInstanceId();
	    taskAssigneeJSON = "[{id:'" + precheck.getTaskAssignee() + 
	      "',nodeType:'user',categoryId:'auditor'}]";

	    FormService formService = (FormService)
	      ApplicationContextHolder.getInstance().getBean("formService");
	    ProcessEngine processEngine = (ProcessEngine)
	      ApplicationContextHolder.getInstance().getBean("processEngine");

	    ProcessInstance instance = processEngine.getRuntimeService().startProcessInstanceByKey
	      ("transferNewPrechechProcess");

	    TaskService taskService = processEngine.getTaskService();
	    List taskList = taskService.createTaskQuery().processInstanceId(
	      processInstanceId).active
	      ().list();
	    Task task = ((Task)taskList.get(0));
	    String taskId = task.getId();
	    String taskDef= task.getTaskDefinitionKey();
	    String handleKey="";
	    String assignee="";
	    String handleFlag="";
	    if(taskDef.equals("cityManageDirectorAudit")){
	    	handleKey="cityManageDirectorState";
	    	handleFlag = "transfer";
	    }else if(taskDef.equals("cityViceAudit")){
	    	handleKey="cityDiveState";
	    	handleFlag = "transfer";
	    }else if(taskDef.equals("provinceManageViceAudit")){
	    	handleKey="provinceManageViceState";
	    	handleFlag = "handle";
	    }
	    if( precheck.getTaskAssignee()!=null){
	    	assignee = precheck.getTaskAssignee();
	    }else{
	    	assignee="superUser";
	    	precheck.setTaskAssignee(assignee);
	    }
	    Map<String, String> map = new HashMap<String, String>();
	    String time = sFormat.format(new Date());
	    map.put("nextTaskAssignee",assignee);    
	    map.put(handleKey,handleFlag);
	    
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    formService.submitTaskFormData(taskId, map);	    
	  
	    IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService)
	      ApplicationContextHolder.getInstance().getBean("pnrTransferOfficeService");
	    
	    if(precheck.isWorkFlag()){
	    	Search search = new Search();
	    	search.addFilterEqual("processInstanceId", precheck.getProcessInstanceId());
	    	List<PnrTransferOffice> list = pnrTransferOfficeService.search(search);
	    	PnrTransferOffice pnrTransferOffice = null;
	    	if(list!=null && list.size()>0){
	    		pnrTransferOffice = list.get(0);
	    	}
	    	pnrTransferOffice.setConnectPerson(precheck.getConnectPerson());
	    	pnrTransferOffice.setInitiator(precheck.getInitiator());
	    	pnrTransferOffice.setOneInitiator(precheck.getOneInitiator());
	    	pnrTransferOffice.setTaskAssignee(precheck.getTaskAssignee());
	    	pnrTransferOffice.setFaultSource(precheck.getFaultSource());
	    	pnrTransferOffice.setCreateTime(precheck.getCreateTime());
	    	pnrTransferOffice.setSecondSendTime(new Date());
	    	pnrTransferOffice.setMaleGuestNumber(precheck.getMaleGuestNumber());
	    	pnrTransferOffice.setState(0);
	    	pnrTransferOffice.setIsDistribute("1");
	    	
	    	//在工单主表中添加流程信息
	    	IProcessTaskService processTaskService = (IProcessTaskService) ApplicationContextHolder.getInstance().getBean("processTaskService");
	    	//processTaskService.setTvalueOfTask(processInstanceId, assignee, pnrTransferOffice, PnrTransferOffice.class, "sendOrder", "工单派发");
	    	processTaskService.setTvalueOfTask(processInstanceId, assignee, pnrTransferOffice, PnrTransferOffice.class, null, null,false);
	    	
	    	pnrTransferOfficeService.save(pnrTransferOffice);
	    	
	    	messagePerson = precheck.getTaskAssignee();
	    	//发短信
	    	String msg = "工单号："+pnrTransferOffice.getProcessInstanceId()+";主题："+pnrTransferOffice.getTheme()+";"+message;
	 	    
        	CommonUtils.sendMsg(msg, messagePerson);
	    }
		
	}

	@Override
	public void oldPrecheckAdd(PnrTransferOffice precheck) {
		DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String taskAssigneeJSON = "";
	    String processInstanceId = precheck.getProcessInstanceId();
	    taskAssigneeJSON = "[{id:'" + precheck.getTaskAssignee() + 
	      "',nodeType:'user',categoryId:'taskAssignee'}]";

	    FormService formService = (FormService)
	      ApplicationContextHolder.getInstance().getBean("formService");
	    ProcessEngine processEngine = (ProcessEngine)
	      ApplicationContextHolder.getInstance().getBean("processEngine");

	    ProcessInstance instance = processEngine.getRuntimeService().startProcessInstanceByKey(
	      "newTransferPrecheck");

	    TaskService taskService = processEngine.getTaskService();
	    List taskList = taskService.createTaskQuery().processInstanceId(
	      processInstanceId).active
	      ().list
	      ();
	    String taskId = ((Task)taskList.get(0)).getId();
	    taskService.setAssignee(taskId, precheck.getInitiator());

	    if (precheck.getState().intValue() == 6) {
	      precheck.setOneInitiator(precheck.getInitiator());
	      precheck.setInitiator(precheck.getTaskAssignee());
	    }
	    TaskFormData taskFormData = formService.getTaskFormData(taskId);
	    Map map = new HashMap();
	    String time = sFormat.format(new Date());
	    for (int i = 0; i < taskFormData.getFormProperties().size(); ++i) {
	      String name = ((FormProperty)taskFormData.getFormProperties().get(i)).getId();
	      if (i == 0)
	        map.put(name, "handle");
	      else if (i == 1)
	        map.put(name, precheck.getInitiator());
	    }

	    formService.submitTaskFormData(taskId, map);

	    String epiboly = precheck.getTaskAssignee();
	    if ((epiboly != null) && (!("".equals(epiboly))))
	    {
	      FormService formService2 = (FormService)
	        ApplicationContextHolder.getInstance().getBean("formService");
	      ProcessEngine processEngine2 = (ProcessEngine)
	        ApplicationContextHolder.getInstance().getBean("processEngine");

	      ProcessInstance instance2 = processEngine2.getRuntimeService().startProcessInstanceByKey(
	        "newTransferPrecheck");

	      TaskService taskService2 = processEngine.getTaskService();
	      List taskList2 = taskService2.createTaskQuery().processInstanceId(
	        processInstanceId).active
	        ().list
	        ();
	      String taskId2 = ((Task)taskList2.get(0)).getId();
	      taskService.setAssignee(taskId2, precheck.getInitiator());

	      TaskFormData taskFormData2 = formService.getTaskFormData(taskId2);
	      Map map2 = new HashMap();
	      String time2 = sFormat.format(new Date());
	      for (int i = 0; i < taskFormData2.getFormProperties().size(); ++i) {
	        String name = ((FormProperty)taskFormData2.getFormProperties().get(i)).getId();
	        if (i == 0)
	          map.put(name, precheck.getTaskAssignee());
	        else if (i == 1)
	          map.put(name, time2);
	      }

	      formService2.submitTaskFormData(taskId2, map);
	    }

	    IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService)
	      ApplicationContextHolder.getInstance().getBean("pnrTransferOfficeService");

	    precheck.setSendTime(new Date());

	    if (precheck.isWorkFlag()) {
	      Search search = new Search();
	      search.addFilterEqual("processInstanceId", precheck.getProcessInstanceId());
	      List list = pnrTransferOfficeService.search(search);
	      PnrTransferOffice pnrTransferOffice = null;
	      if ((list != null) && (list.size() > 0))
	        pnrTransferOffice = (PnrTransferOffice)list.get(0);

	      pnrTransferOffice.setConnectPerson(precheck.getConnectPerson());
	      pnrTransferOffice.setInitiator(precheck.getInitiator());
	      pnrTransferOffice.setOneInitiator(precheck.getOneInitiator());
	      pnrTransferOffice.setTaskAssignee(precheck.getTaskAssignee());
	      pnrTransferOffice.setFaultSource(precheck.getFaultSource());
	      pnrTransferOffice.setCreateTime(precheck.getCreateTime());
	      pnrTransferOffice.setSendTime(precheck.getSendTime());
	      pnrTransferOffice.setMaleGuestNumber(precheck.getMaleGuestNumber());
	      pnrTransferOffice.setState(Integer.valueOf(0));
	      pnrTransferOffice.setIsDistribute("1");
	      pnrTransferOfficeService.save(pnrTransferOffice);
	    }
	}

	/**
	 * 工单管理-传输局工单-抢修工单-待回复工单 集合条数
	 * 
	 * @param userId
	 *            用户ID
	 * @param sendStartTime
	 *            派单开始时间
	 * @param sendEndTime
	 *            派单结束时间
	 * @param wsNum
	 *            工单号
	 * @param wsTitle
	 *            工单主题
	 * @param status
	 *            当前状态
	 * @return
	 */
	public int getToreplyJobOfEmergencyJobCount(String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status) {
		return pnrTransferOfficeDaoJDBC.getToreplyJobOfEmergencyJobCount(
				userId, sendStartTime, sendEndTime, wsNum, wsTitle, status);

	}

	/**
	 * 工单管理-传输局工单-抢修工单-待回复工单 集合
	 * 
	 * @param userId
	 *            用户ID
	 * @param sendStartTime
	 *            派单开始时间
	 * @param sendEndTime
	 *            派单结束时间
	 * @param wsNum
	 *            工单号
	 * @param wsTitle
	 *            工单主题
	 * @param status
	 *            当前状态
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getToreplyJobOfEmergencyJobList(String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status, int firstResult, int endResult,
			int pageSize) {

		List<TaskModel> list = pnrTransferOfficeDaoJDBC
				.getToreplyJobOfEmergencyJobList(userId, sendStartTime,
						sendEndTime, wsNum, wsTitle, status, firstResult,
						endResult, pageSize);
		//Iterator<TaskModel> i = list.iterator();
//		while (i.hasNext()) {
//			TaskModel model = i.next();
//			List<HistoricTaskInstance> historicTasks = historyService
//					.createHistoricTaskInstanceQuery().processInstanceId(
//							model.getProcessInstanceId()).taskId(
//							model.getTaskId())
//					.orderByHistoricTaskInstanceStartTime().desc().listPage(0,
//							1);
//			if (historicTasks != null & historicTasks.size() > 0) {
//				model.setStatusName(historicTasks.get(0).getName());
//				String defKey = historicTasks.get(0).getTaskDefinitionKey();
//				model.setTaskDefKey(defKey);
//
//				if (defKey.equals("newTask") || defKey.equals("transferTask")
//						|| defKey.equals("transferAudit")
//						|| defKey.equals("audit")) {
//					model.setInitiator(model.getOneInitiator());
//				}
//			}
//
//		}

		return list;
	}

	/**
	 * 工单管理-传输局工单-预检预修工单-待回复工单 集合条数
	 * 
	 * @param userId
	 *            用户ID
	 * @param sendStartTime
	 *            派单开始时间
	 * @param sendEndTime
	 *            派单结束时间
	 * @param wsNum
	 *            工单号
	 * @param wsTitle
	 *            工单主题
	 * @param status
	 *            当前状态
	 * @return
	 */
	public int getToreplyJobOfPreflightPreparationJobCount(String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status) {
		return pnrTransferOfficeDaoJDBC
				.getToreplyJobOfPreflightPreparationJobCount(userId,
						sendStartTime, sendEndTime, wsNum, wsTitle, status);

	}

	/**
	 * 工单管理-传输局工单-预检预修工单-待回复工单 集合
	 * 
	 * @param userId
	 *            用户ID
	 * @param sendStartTime
	 *            派单开始时间
	 * @param sendEndTime
	 *            派单结束时间
	 * @param wsNum
	 *            工单号
	 * @param wsTitle
	 *            工单主题
	 * @param status
	 *            当前状态
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getToreplyJobOfPreflightPreparationJobList(
			String userId, String sendStartTime, String sendEndTime,
			String wsNum, String wsTitle, String status, int firstResult,
			int endResult, int pageSize) {

		List<TaskModel> list = pnrTransferOfficeDaoJDBC
				.getToreplyJobOfPreflightPreparationJobList(userId,
						sendStartTime, sendEndTime, wsNum, wsTitle, status,
						firstResult, endResult, pageSize);
		Iterator<TaskModel> i = list.iterator();
		while (i.hasNext()) {
			TaskModel model = i.next();
			// 所处环节
			List<HistoricTaskInstance> historicTasks = historyService
					.createHistoricTaskInstanceQuery().processInstanceId(
							model.getProcessInstanceId()).taskId(
							model.getTaskId()).orderByTaskId().desc().listPage(
							0, 1);
			if (historicTasks != null & historicTasks.size() > 0) {
				model.setStatusName(historicTasks.get(0).getName());
				model
						.setTaskDefKey(historicTasks.get(0)
								.getTaskDefinitionKey());
			}

		}

		return list;
	}

	/**
	 * 工单管理-"公客-传输局工单"-待回复工单 集合条数
	 * 
	 * @param userId
	 *            用户ID
	 * @param sendStartTime
	 *            派单开始时间
	 * @param sendEndTime
	 *            派单结束时间
	 * @param wsNum
	 *            工单号
	 * @param wsTitle
	 *            工单主题
	 * @param status
	 *            当前状态
	 * @return
	 */
	public int getToreplyJobOfMaleGuestTransmissionBureauJobCount(
			String userId, MaleGuestSelectAttribute selectAttribute) {
		return pnrTransferOfficeDaoJDBC
				.getToreplyJobOfMaleGuestTransmissionBureauJobCount(userId,
						selectAttribute);

	}

	/**
	 * 工单管理-"公客-传输局工单"-待回复工单 集合
	 * 
	 * @param userId
	 *            用户ID
	 * @param sendStartTime
	 *            派单开始时间
	 * @param sendEndTime
	 *            派单结束时间
	 * @param wsNum
	 *            工单号
	 * @param wsTitle
	 *            工单主题
	 * @param status
	 *            当前状态
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getToreplyJobOfMaleGuestTransmissionBureauJobList(
			String userId, MaleGuestSelectAttribute selectAttribute, int firstResult,
			int endResult, int pageSize) {

		List<TaskModel> list = pnrTransferOfficeDaoJDBC
				.getToreplyJobOfMaleGuestTransmissionBureauJobList(userId,
						selectAttribute,
						firstResult, endResult, pageSize);

		return list;
	}

	public List getTransferOfficePerson(String city)
	  {
	    return this.pnrTransferOfficeDaoJDBC.getTransferOfficePerson(city);
	  }

	  public List getEpiboly(String areaId, String country, String term) {
	    return this.pnrTransferOfficeDaoJDBC.getEpiboly(areaId, country, term);
	  }

	@Override
	public List judgeWorkOrderIsExit(String workOrderNumber) {
		
		return this.pnrTransferOfficeDaoJDBC.judgeWorkOrderIsExit(workOrderNumber);
	}

	@Override
	public void maleGuestReminder(TransferMaleGuestInform maleGuestFrom) {
		String workOrderNumber = maleGuestFrom.getMaleGuestId();
		List<Map> list = pnrTransferOfficeDaoJDBC.getWorkOrderByWorkNumber(workOrderNumber);
		if(list!=null && list.size()>0){
			//短信接收人：施工队、外包公司、传输分局
			String worker = StaticMethod.nullObject2String(list.get(0).get("TASK_ASSIGNEE"));
			String daiwei = StaticMethod.nullObject2String(list.get(0).get("INITIATOR"));
			String workerCSj = StaticMethod.nullObject2String(list.get(0).get("TRANSFER_OFFICE_ID"));
			
			//短信内容
			//工单号
			String processId = StaticMethod.nullObject2String(list.get(0).get("PROCESS_INSTANCE_ID"));
			//障碍号码
			String barrierNumber = StaticMethod.nullObject2String(list.get(0).get("BARRIER_NUMBER"));
			//联系方式
			String connectPerson = StaticMethod.nullObject2String(list.get(0).get("CONNECT_PERSON"));
			//说明
			String fieldContent = maleGuestFrom.getFieldContent();
			
			String msg = "公客工单号："+processId+";障碍号码："+barrierNumber+";联系方式："+connectPerson+";说明:"+fieldContent;
			
			 CommonUtils.sendMsg(msg, worker);
			 CommonUtils.sendMsg(msg, daiwei);
			 CommonUtils.sendMsg(msg, workerCSj);
		}
	}
	public void showSheetAccessoriesList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String processInstanceId = StaticMethod.null2String(request.getParameter("pid"));
		List<TawCommonsAccessoriesForm> list = pnrTransferOfficeDaoJDBC.getSheetAccessoriesByPid(processInstanceId);
		request.setAttribute("sheetAccessories", list);	
		
	}
	
	@Override
	public List<TawCommonsAccessoriesForm> newShowSheetAccessoriesList(String processInstanceId)
			throws Exception {
		List<TawCommonsAccessoriesForm> list = pnrTransferOfficeDaoJDBC.getSheetAccessoriesByPidToPrecheck(processInstanceId);
		return list;
	}
	
	/**
     * ol-bbu机房优化流程--附件显示
     * 
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public List<TawCommonsAccessoriesForm> showPnrOltBbuRoomSheetAccessoriesList(String processInstanceId)throws Exception{
    	List<TawCommonsAccessoriesForm> list = pnrTransferOfficeDaoJDBC.showPnrOltBbuRoomSheetAccessoriesList(processInstanceId);
		return list;
    }
	
	
	@Override
	public List<TawCommonsAccessoriesForm> getAccessoriesList(String processInstanceId,String flag)
			throws Exception {
		List<TawCommonsAccessoriesForm> list = pnrTransferOfficeDaoJDBC.getAccessoriesList(processInstanceId,flag);
		return list;
	}
	
	@Override
	/**
	 * 机房拆除流程--附件显示
	 */
	public List<TawCommonsAccessoriesForm> getRoomDemolitionAccessoriesList(String processInstanceId,String flag)
			throws Exception {
		List<TawCommonsAccessoriesForm> list = pnrTransferOfficeDaoJDBC.getRoomDemolitionAccessoriesList(processInstanceId,flag);
		return list;
	}

	@Override
	public void updateMaleGuestByWorkOrder(PnrTransferOffice maleGuest,String flag) {
		
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService)
	      ApplicationContextHolder.getInstance().getBean("pnrTransferOfficeService");
		Search search = new Search();
    	search.addFilterEqual("processInstanceId", maleGuest.getProcessInstanceId());
    	List<PnrTransferOffice> list = pnrTransferOfficeService.search(search);
    	PnrTransferOffice pnrTransferOffice = null;
    	if(list!=null && list.size()>0){
    		pnrTransferOffice = list.get(0);
    	}
    	if("1".equals(flag)){
    		if(pnrTransferOffice.getRepeatState()!=null && "2".equals(pnrTransferOffice.getRepeatState().trim())){
    			pnrTransferOffice.setSecondRepeatState("1");
    		}
    		pnrTransferOffice.setTheme(maleGuest.getTheme());
    		pnrTransferOffice.setState(maleGuest.getState());
    		pnrTransferOffice.setRepeatState(maleGuest.getRepeatState());
    	}else if("2".equals(flag)){
    		pnrTransferOffice.setDoFlag(maleGuest.getDoFlag());
    	}
    	
    	pnrTransferOfficeService.save(pnrTransferOffice);
	}

	@Override
	public String judgeOrderIsDo(String maleGuestNumber) {
		return this.pnrTransferOfficeDaoJDBC.judgeOrderIsDo(maleGuestNumber);
	}

	@Override
	public String doMaleGurstInterface(String maleGuestNumber) {
		// 调用方法,查询接口需要的数据
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService)
	      ApplicationContextHolder.getInstance().getBean("pnrTransferOfficeService");
		DateFormat sFormat = new SimpleDateFormat(
		"yyyy-MM-dd HH:mm:ss");
		TransferMaleGuestReturn maleGuestReturn = pnrTransferOfficeService
				.getMaleGuestReturnData(maleGuestNumber);
		String getMsg = CommonUtils.maleGuestFlagAndReturn(sFormat
				.format(new Date()),CommonUtils.GK_MALE_GUEST_RECEIPT_METHOD,maleGuestReturn);
		
		return getMsg;
	}

	@Override
	public List<TaskModel> getMaleGuestByInitiator(String userId,
			MaleGuestSelectAttribute selectAttribute, int firstResult,
			int endResult, int pageSize) {
		return this.pnrTransferOfficeDaoJDBC.getMaleGuestByInitiator(userId, selectAttribute, firstResult, endResult, pageSize);
	}

	@Override
	public int getMaleGuestCountByInitiator(String userId,
			MaleGuestSelectAttribute selectAttribute) {
		return this.pnrTransferOfficeDaoJDBC.getMaleGuestCountByInitiator(userId, selectAttribute);
	}

	@Override
	public List<TaskModel> getMaleGuestByIntiatorOrAssignee(String userId,
			MaleGuestSelectAttribute selectAttribute, int firstResult,
			int endResult, int pageSize) {
		return this.pnrTransferOfficeDaoJDBC.getMaleGuestByIntiatorOrAssignee(userId, selectAttribute, firstResult, endResult, pageSize);
	}

	@Override
	public int getMaleGuestCountByInitiatorOrAssignee(String userId,
			MaleGuestSelectAttribute selectAttribute) {
		return this.pnrTransferOfficeDaoJDBC.getMaleGuestCountByInitiatorOrAssignee(userId, selectAttribute);
	}

	@Override
	public int getMaleGeustCountToGetBack(String userId,
			MaleGuestSelectAttribute selectAttribute) {
		return this.pnrTransferOfficeDaoJDBC.getMaleGeustCountToGetBack(userId, selectAttribute);
	}

	@Override
	public List<TaskModel> getMaleGeustToGetBack(String userId,
			MaleGuestSelectAttribute selectAttribute, int firstResult,
			int endResult, int pageSize) {
		return this.pnrTransferOfficeDaoJDBC.getMaleGeustToGetBack(userId, selectAttribute, firstResult, endResult, pageSize);
	}

	@Override
	public Double getCountersignRatioByProcessInstanceId(String processInstanceId,String cityId) {
		
		Double ratio = 0d;
        int doneCount = getCountersignCountByProcessInstanceId(processInstanceId);
        //查看该地市的专家人数 --start
        int expertCount = 0;
    	PnrActReviewStaffService pnrActReviewStaffService = (PnrActReviewStaffService) ApplicationContextHolder.getInstance().getBean("pnrActReviewStaffService");
		String whereStr = " where 1=1";
		// 组装查询条件
		if (cityId != null && !cityId.equals("")) {
			whereStr += " and cityId = '"+cityId+"'";
		}
		
		Map mapList = (Map) pnrActReviewStaffService.queryPnrActReviewStaff(
				0, 1, whereStr);
		List list = (List) mapList.get("result");
		
		if(list.size() >0 ){
			PnrActReviewStaff pnrActReviewStaff = (PnrActReviewStaff)list.get(0);
			expertCount = pnrActReviewStaff.getUserId().split(",").length;
		}
		//查看该地市的专家人数 --end
		if(expertCount>0){
			ratio = (double)doneCount/expertCount;
			BigDecimal bg = new BigDecimal(ratio);
			ratio = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		
		return ratio;
	}

	@Override
	public boolean deleteCountersignInfo(String processInstanceId) {
		return this.pnrTransferOfficeDaoJDBC.deleteCountersignInfo(processInstanceId);
	}

	@Override
	public boolean insertCountersignInfo(PnrActRuCountersign pnrActRuCountersign)
			throws Exception {
		
		return this.pnrTransferOfficeDaoJDBC.insertCountersignInfo(pnrActRuCountersign);
	}

	@Override
	public int getCountersignCountByProcessInstanceId(String processInstanceId) {
		// TODO Auto-generated method stub
		return this.pnrTransferOfficeDaoJDBC.getCountersignCountByProcessInstanceId(processInstanceId);
	}

	@Override
	public void overHaulRemakeAdd(PnrTransferOffice precheck) {

		//短信接收人
		String messagePerson = "";
		String message="";
	    DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String taskAssigneeJSON = "";
	    String processInstanceId = precheck.getProcessInstanceId();
	    taskAssigneeJSON = "[{id:'" + precheck.getTaskAssignee() + 
	      "',nodeType:'user',categoryId:'auditor'}]";
	 
	    FormService formService = (FormService)
	      ApplicationContextHolder.getInstance().getBean("formService");
	    ProcessEngine processEngine = (ProcessEngine)
	      ApplicationContextHolder.getInstance().getBean("processEngine");

	    ProcessInstance instance = processEngine.getRuntimeService().startProcessInstanceByKey
	      ("overHaulProcess");

	    TaskService taskService = processEngine.getTaskService();
	    List taskList = taskService.createTaskQuery().processInstanceId(
	      processInstanceId).active
	      ().list();
	    Task task = ((Task)taskList.get(0));
	    String taskId = task.getId();
	    String assignee="";
	    if( precheck.getTaskAssignee()!=null){
	    	assignee = precheck.getTaskAssignee();
	    }else{
	    	assignee="superUser";
	    	precheck.setTaskAssignee(assignee);
	    }
	    Map<String, String> map = new HashMap<String, String>();
	    map.put("nextTaskAssignee",assignee);    
	    map.put("provinceManageViceState","handle");
	    
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    logger.info("当前的操作人："+precheck.getInitiator()+";当前的流程:预检预修;当前的操作环节:市公司审批提交自动转派;当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--start");
	    formService.submitTaskFormData(taskId, map);
	    logger.info("当前的操作人："+precheck.getInitiator()+";当前的流程:预检预修;当前的操作环节:市公司审批提交自动转派;当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--end");
	    
	  
	    IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService)
	      ApplicationContextHolder.getInstance().getBean("pnrTransferOfficeService");


//	    precheck.setSendTime(new Date());
	    
	    if(precheck.isWorkFlag()){
	    	Search search = new Search();
	    	search.addFilterEqual("processInstanceId", precheck.getProcessInstanceId());
	    	List<PnrTransferOffice> list = pnrTransferOfficeService.search(search);
	    	PnrTransferOffice pnrTransferOffice = null;
	    	if(list!=null && list.size()>0){
	    		pnrTransferOffice = list.get(0);
	    	}
	    	pnrTransferOffice.setConnectPerson(precheck.getConnectPerson());
	    	pnrTransferOffice.setTaskAssignee(precheck.getTaskAssignee());
	    	pnrTransferOffice.setFaultSource(precheck.getFaultSource());
	    	pnrTransferOffice.setCreateTime(precheck.getCreateTime());
	    	pnrTransferOffice.setSecondSendTime(new Date());
	    	pnrTransferOffice.setMaleGuestNumber(precheck.getMaleGuestNumber());
	    	pnrTransferOffice.setState(0);
	    	pnrTransferOffice.setIsDistribute("1");
	    	//在工单主表中添加流程信息
			processTaskService.setTvalueOfTask(processInstanceId, assignee, pnrTransferOffice, PnrTransferOffice.class, null, null,false);
	    	pnrTransferOfficeService.save(pnrTransferOffice);
	    	
	    	messagePerson = precheck.getTaskAssignee();
	    	//发短信
	    	String msg = "工单号："+pnrTransferOffice.getProcessInstanceId()+";主题："+pnrTransferOffice.getTheme()+";"+message;
	    	
	    	CommonUtils.sendMsg(msg, messagePerson);
	    }
	  
		
	}

	@Override
	public String getLoginPersonStatusToOverhaulRemake(String userId,
			PnrTransferOffice pnrTransferOffice) {
		return pnrTransferOfficeDaoJDBC.getLoginPersonStatusToOverhaulRemake(userId, pnrTransferOffice);
	}

	@Override
	public String getSheetId(String processKey, String initator, String type,
			String shortName) {
		
		String sheetId = "";
		int maxNumberValue =0;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();		
		
		maxNumberValue = pnrTransferOfficeDaoJDBC.getCurrDateSheetCountNum(processKey);
		
    	int instr =initator.lastIndexOf("-");
    	if(instr > 0){
    		
    		sheetId =type+"-"+initator.substring(0,instr)+"-"+shortName;
    	}else{
    		sheetId =type+"-SD-NO"+"-"+shortName;//没有找到账号的前缀
    	}    	
    	sheetId = sheetId.toUpperCase();
    	
    	sheetId =sheetId+"-"+dateFormat.format(date)+"-"+maxNumberValue;
		
		return sheetId;
	}

	@Override
	public void arteryPrecheckAdd(PnrTransferOffice precheck) {


		//短信接收人
		String messagePerson = "";
		String message="";
	    DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String taskAssigneeJSON = "";
	    String processInstanceId = precheck.getProcessInstanceId();
	    taskAssigneeJSON = "[{id:'" + precheck.getTaskAssignee() + 
	      "',nodeType:'user',categoryId:'auditor'}]";
	 
	    FormService formService = (FormService)
	      ApplicationContextHolder.getInstance().getBean("formService");
	    ProcessEngine processEngine = (ProcessEngine)
	      ApplicationContextHolder.getInstance().getBean("processEngine");

	    ProcessInstance instance = processEngine.getRuntimeService().startProcessInstanceByKey
	      ("transferArteryPrecheckProcess");

	    TaskService taskService = processEngine.getTaskService();
	    List taskList = taskService.createTaskQuery().processInstanceId(
	      processInstanceId).active
	      ().list();
	    Task task = ((Task)taskList.get(0));
	    String taskId = task.getId();
	    String taskDef= task.getTaskDefinitionKey();
	    String handleKey="";
	    String assignee="";
	    String nextPerson="";
	    String handle = "";
	    if(taskDef.equals("provinceLineViceAudit")){
	    	handleKey="provinceLineViceState";
	    	nextPerson = "provinceManageCharge";
	    	handle ="transfer";
	    }else if(taskDef.equals("provinceManageViceAudit")){
	    	handleKey="provinceManageViceState";
	    	nextPerson = "nextTaskAssignee";
	    	handle="handle";
	    }
	    if( precheck.getTaskAssignee()!=null){
	    	assignee = precheck.getTaskAssignee();
	    }else{
	    	assignee="superUser";
	    	precheck.setTaskAssignee(assignee);
	    }
	    Map<String, String> map = new HashMap<String, String>();
	    String time = sFormat.format(new Date());
	    map.put(nextPerson,assignee);    
	    map.put(handleKey,handle);
	    
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	    logger.info("当前的操作人："+precheck.getInitiator()+";当前的流程:干线预检预修;当前的操作环节:市公司审批提交自动转派;当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--start");
		if(!"".equals(handle)){			   
			formService.submitTaskFormData(taskId, map);
		}else{
			BocoLog.info("PnrTransferOfficeServiceImpl-arteryPrecheckAdd", 1385, processInstanceId+":调用\"" +
					"等待商城调用\"接口失败，原因<预检预修-干线：引擎表act_ru_task表字段TASK_DEF_KEY_值为provinceLineViceAudit或provinceManageViceAudit才能进入下一环节>");
		}
//	    logger.info("当前的操作人："+precheck.getInitiator()+";当前的流程:干线预检预修;当前的操作环节:市公司审批提交自动转派;当前的工单号:"+processInstanceId+";当前的taskid:"+taskId+";当前的map值:"+map.toString()+";当前时间:"+sdf.format(new Date())+";--end");
	    
	  
	    IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService)
	      ApplicationContextHolder.getInstance().getBean("pnrTransferOfficeService");


//	    precheck.setSendTime(new Date());
	    
	    if(precheck.isWorkFlag()){
	    	Search search = new Search();
	    	search.addFilterEqual("processInstanceId", precheck.getProcessInstanceId());
	    	List<PnrTransferOffice> list = pnrTransferOfficeService.search(search);
	    	PnrTransferOffice pnrTransferOffice = null;
	    	if(list!=null && list.size()>0){
	    		pnrTransferOffice = list.get(0);
	    	}
	    	pnrTransferOffice.setConnectPerson(precheck.getConnectPerson());
	    	pnrTransferOffice.setTaskAssignee(precheck.getTaskAssignee());
	    	pnrTransferOffice.setFaultSource(precheck.getFaultSource());
	    	pnrTransferOffice.setCreateTime(precheck.getCreateTime());
	    	pnrTransferOffice.setSecondSendTime(new Date());
	    	pnrTransferOffice.setMaleGuestNumber(precheck.getMaleGuestNumber());
	    	pnrTransferOffice.setState(0);
	    	pnrTransferOffice.setIsDistribute("1");
	    	//在工单主表中添加流程信息
	    	IProcessTaskService processTaskService = (IProcessTaskService) ApplicationContextHolder.getInstance().getBean("processTaskService");
	    	processTaskService.setTvalueOfTask(processInstanceId, assignee, pnrTransferOffice, PnrTransferOffice.class, null, null,false); 
	    	pnrTransferOfficeService.save(pnrTransferOffice);
	    	
	    	
	    	messagePerson = precheck.getTaskAssignee();
	    	//发短信
	    	String msg = "工单号："+pnrTransferOffice.getProcessInstanceId()+";主题："+pnrTransferOffice.getTheme()+";"+message;
	    	
	    	CommonUtils.sendMsg(msg, messagePerson);
	    }
	  
		
	
		
	}

	/**
	 * 根据流程ID和环节的ID值查询附件
	 * @param processInstanceId 流程ID
	 * @param TaskDefinitionKey 环节的ID
	 * @return
	 */
	public String getAttachmentNamesByProcessInstanceIdAndUserTaskId(String processInstanceId,String userTaskId){
		// TODO Auto-generated method stub
		return pnrTransferOfficeDaoJDBC
				.getAttachmentNamesByProcessInstanceIdAndUserTaskId(processInstanceId,userTaskId);
	}
	
	/**
	 * 根据流程ID和步骤值删除附件
	 * @param processInstanceId 流程ID
	 * @param Step 步骤值
	 * @return
	 */
	public void deleteAttachmentNamesByProcessInstanceIdAndStep(String processInstanceId,
			String Step) {
		// TODO Auto-generated method stub
		 pnrTransferOfficeDaoJDBC
				.deleteAttachmentNamesByProcessInstanceIdAndStep(processInstanceId,Step);
	}

	@Override
	public Map<String, String> getAvgByProcessInstanceId(
			String processInstanceId) {
		// TODO Auto-generated method stub
		return pnrTransferOfficeDaoJDBC.getAvgByProcessInstanceId(processInstanceId);
	}

	@Override
	public Map<String, String> getAndroidLimitDistance(String processKey) {
		// TODO Auto-generated method stub
		return pnrTransferOfficeDaoJDBC.getAndroidLimitDistance(processKey);
	}
	

	/**
	 * 施工队之后的审核环节界面上的距离显示，没有距离默认为"无"
	 * @param processInstanceId
	 * @param type
	 * @return
	 */
	public String getDistanceShow(String processInstanceId,String type){
		return pnrTransferOfficeDaoJDBC.getDistanceShow(processInstanceId,type);
	}

	@Override
	public int getAndroidTaskCount(AndroidQuery androidQuery) {
		// TODO Auto-generated method stub
		return pnrTransferOfficeDaoJDBC.getAndroidTaskCount(androidQuery);
	}

	@Override
	public List<AndroidWorkOrderTask> getAndroidTaskList(
			AndroidQuery androidQuery, int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		return pnrTransferOfficeDaoJDBC.getAndroidTaskList(androidQuery, pageIndex, pageSize);
	}
	@Override
	public List<AndroidWorkOrderTask> getAndroidTaskListTogether(
			AndroidQuery androidQuery, int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		return pnrTransferOfficeDaoJDBC.getAndroidTaskListTogether(androidQuery, pageIndex, pageSize);
	}
	/**
	 * 工单管理-"公客-传输局工单"-手动归档工单 集合
	 * 
	 */
	public List<TaskModel> manualArchiveList(
			String userId,String startDate,String endDate, MaleGuestSelectAttribute selectAttribute, int firstResult,
			int endResult, int pageSize) {

		List<TaskModel> list = pnrTransferOfficeDaoJDBC
				.manualArchiveList(userId,startDate,endDate,
						selectAttribute,
						firstResult, endResult, pageSize);

		return list;
	}

	

	/**
	 * 工单管理-"公客-传输局工单"-手动归档工单 集合条数
	 */
	public int manualArchiveCount(
			String userId,String startDate,String endDate, MaleGuestSelectAttribute selectAttribute) {
		return pnrTransferOfficeDaoJDBC
				.manualArchiveCount(userId,startDate,endDate,
						selectAttribute);

	}

	/**
	 * 本地网-预检预修-按工单号查询工单详情
	 */
	public List<PnrTransferOffice> getWorkOrderDetails(String processInstanceId) {
		return pnrTransferOfficeDaoJDBC.getWorkOrderDetails(processInstanceId);
	}
	@Override
	public void oltBbuAdd(PnrTransferOffice precheck) {
		//短信接收人
		String messagePerson = "";
		String message="";
	    DateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String taskAssigneeJSON = "";
	    String processInstanceId = precheck.getProcessInstanceId();
	    taskAssigneeJSON = "[{id:'" + precheck.getTaskAssignee() + 
	      "',nodeType:'user',categoryId:'auditor'}]";

	    FormService formService = (FormService)
	      ApplicationContextHolder.getInstance().getBean("formService");
	    ProcessEngine processEngine = (ProcessEngine)
	      ApplicationContextHolder.getInstance().getBean("processEngine");

	    ProcessInstance instance = processEngine.getRuntimeService().startProcessInstanceByKey
	      ("oltBbuProcess");

	    TaskService taskService = processEngine.getTaskService();
	    List taskList = taskService.createTaskQuery().processInstanceId(
	      processInstanceId).active
	      ().list();
	    Task task = ((Task)taskList.get(0));
	    String taskId = task.getId();
	    String taskDef= task.getTaskDefinitionKey();
	   // String handleKey="";
	    String assignee="";
	    String handleFlag="";
//	    if(taskDef.equals("waitInterface")){
//	    	handleKey="provinceManageChargeState";
//	    	handleFlag = "handle";
//	    }
	    if( precheck.getTaskAssignee()!=null){
	    	assignee = precheck.getTaskAssignee();
	    }else{
	    	assignee="superUser";
	    	precheck.setTaskAssignee(assignee);
	    }
	    Map<String, String> map = new HashMap<String, String>();
	    String time = sFormat.format(new Date());
	    map.put("nextTaskAssignee",assignee);    
	   // map.put(handleKey,handleFlag);
	    
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    formService.submitTaskFormData(taskId, map);	    
	  
	    IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService)
	      ApplicationContextHolder.getInstance().getBean("pnrTransferOfficeService");
	    
	    if(precheck.isWorkFlag()){
	    	Search search = new Search();
	    	search.addFilterEqual("processInstanceId", precheck.getProcessInstanceId());
	    	List<PnrTransferOffice> list = pnrTransferOfficeService.search(search);
	    	PnrTransferOffice pnrTransferOffice = null;
	    	if(list!=null && list.size()>0){
	    		pnrTransferOffice = list.get(0);
	    	}
	    	pnrTransferOffice.setConnectPerson(precheck.getConnectPerson());
	    	pnrTransferOffice.setInitiator(precheck.getInitiator());
	    	//pnrTransferOffice.setOneInitiator(precheck.getOneInitiator());
	    	pnrTransferOffice.setTaskAssignee(precheck.getTaskAssignee());
	    	pnrTransferOffice.setFaultSource(precheck.getFaultSource());
	    	pnrTransferOffice.setCreateTime(precheck.getCreateTime());
	    	pnrTransferOffice.setSecondSendTime(new Date());
	    	pnrTransferOffice.setMaleGuestNumber(precheck.getMaleGuestNumber());
	    	pnrTransferOffice.setState(0);
	    	pnrTransferOffice.setIsDistribute("1");
	    	//在工单主表中添加流程信息
	    	processTaskService.setTvalueOfTask(processInstanceId, assignee, pnrTransferOffice, PnrTransferOffice.class, null, null,false);
	    	pnrTransferOfficeService.save(pnrTransferOffice);
	    	
	    	messagePerson = precheck.getTaskAssignee();
	    	//发短信
	    	String msg = "工单号："+pnrTransferOffice.getProcessInstanceId()+";主题："+pnrTransferOffice.getTheme()+";"+message;
	 	    
        	CommonUtils.sendMsg(msg, messagePerson);
	    }
		
	}
	
	/**
	 * 	 归集工单列表统计数
	 	 * @author WangJun
	 	 * @title: insertResourceInventoryData
	 	 * @date Nov 27, 2015 11:08:06 AM
	 	 * @param jkXcwhRoomList
	 	 * @return int
	 */
	public int querySingleImputationCount(String userId,MaleGuestSelectAttribute selectAttribute){
		return pnrTransferOfficeDaoJDBC.querySingleImputationCount(userId,selectAttribute);
	}
	

	//未归集工单列表统计数
	public int queryNoSingleImputationCount(String userId,MaleGuestSelectAttribute selectAttribute){
		return pnrTransferOfficeDaoJDBC.queryNoSingleImputationCount(userId,selectAttribute);
	}
	
	//未归集工单列表
	public List<TaskModel> queryNoSingleImputationList(String userId,MaleGuestSelectAttribute selectAttribute, int firstResult, int endResult,int pageSize){
		List<TaskModel> list = pnrTransferOfficeDaoJDBC.queryNoSingleImputationList(userId,selectAttribute,firstResult, endResult, pageSize);
		return list;
	}

	@Override
	public void performAddTogether(PnrTransferOffice maleGuest) {

		if (maleGuest.getTaskAssignee() != null
				&& !"".equals(maleGuest.getTaskAssignee())) {
						
			boolean isSendsms = false;
			//归集规则
			String siteCd = maleGuest.getSiteCd();
			
			if(siteCd!=null && !"".equals(siteCd)){
			
				PnrTransferOffice permaleGuest = maleGuest.clone();
				String mailGuestState = "0";

				Map returnMap = pnrTransferOfficeDaoJDBC.mailGuestTogetherVerification(siteCd);
				int tnum=StaticMethod.nullObject2int(returnMap.get("tnum").toString());//归集工单
				int subtnum=StaticMethod.nullObject2int(returnMap.get("subtnum").toString());//归集的子工单
				int nortnum=StaticMethod.nullObject2int(returnMap.get("nortnum").toString());//正常工单
				String tprocessInstanceId=returnMap.get("process_instance_id").toString();//归集工单号
				//取告警发生时间
				Date newcTime = maleGuest.getCreateTime();
                
			    if(tnum>0){//归属归集工单
//					List sublist = (List)returnMap.get("sublist");

//					pnrTransferOfficeDaoJDBC.updateProcessInstanceIdForSubprocess(sublist, tprocessInstanceId);	
			    	//取子工单最小的告警发生时间和最小派单时间
			    	Date minCreateDate = this.getMinDateForSubWorkOrder(tprocessInstanceId, null, "create", null);
					Date minSendDate = this.getMinDateForSubWorkOrder(tprocessInstanceId, null, "send", null);
					if(minCreateDate != null && minSendDate !=null ){
						//用最小的告警发生时间和该工单的告警发生时间进行比较，取最小的
						if(newcTime != null){
							if (minCreateDate.getTime() > newcTime.getTime()) {
								minCreateDate = newcTime;
							}
						}
						//更新主工单的告警发生时间和派单时间
						Search search = new Search();
						search.addFilterEqual("processInstanceId", tprocessInstanceId);
						SearchResult t = this.searchAndCount(search);
						List<PnrTransferOffice> list = t.getResult();
						if(list != null && list.size() > 0){
							PnrTransferOffice pnrTransferOffice = list.get(0);
							if(pnrTransferOffice != null){
								pnrTransferOffice.setCreateTime(minCreateDate);
								pnrTransferOffice.setSendTime(minSendDate);
								this.save(pnrTransferOffice);
							}
						}
					}
			    	mailGuestState="2";
				}else{
					if(nortnum>0){//有正常的工单---
						//取子工单最小的告警发生时间和最小派单时间
						List sublist = (List)returnMap.get("sublist");
						Date minCreateDate = this.getMinDateForSubWorkOrder(null, sublist, "create", null);
						Date minSendDate = this.getMinDateForSubWorkOrder(null, sublist, "send", null);
						if(minCreateDate != null && minSendDate !=null ){
							//用最小的告警发生时间和该工单的告警发生时间进行比较，取最小的
							if(newcTime != null){
								if (minCreateDate.getTime() > newcTime.getTime()) {
									minCreateDate = newcTime;
								}
							}
							//设置主工单的告警发生时间和派单时间
							permaleGuest.setCreateTime(minCreateDate); //告警发生时间
							permaleGuest.setSendTime(minSendDate); //告警发生时间
						}
						
						permaleGuest.setMaleGuestState("1");
						tprocessInstanceId=	generateProcess(permaleGuest,"-");//生成归集工单						
						//List sublist = (List)returnMap.get("sublist");		
						mailGuestState="2";
						pnrTransferOfficeDaoJDBC.updateProcessInstanceIdForSubprocess(sublist, tprocessInstanceId);						
						isSendsms = true;
					}else{
				    	isSendsms = true;

					}
				}
			    
			    maleGuest.setMaleGuestState(mailGuestState);
		    	generateProcess(maleGuest,tprocessInstanceId);//生成工单

			}else{
				
				performAdd(maleGuest);
			}
			
			if(isSendsms){
				// 施工队短信接收人
				String msgRecive = maleGuest.getTaskAssignee();
				//外包公司短信接收人
				String msgDaiwei = maleGuest.getInitiator();
				//传输局短信接收人
				String msgCSJ = maleGuest.getTransferOfficeId();
				
				// 发短信的内容
	
				String msg = "公客工单号:" + maleGuest.getProcessInstanceId() + ";障碍号码:"
						+ maleGuest.getBarrierNumber() + ";审稿时间:" +sFormat.format( maleGuest.getCreateTime())+";派单时间："+sFormat.format(new Date())+";联系方式:"+
						maleGuest.getConnectPerson()+";地址："+maleGuest.getInstallAddress()+";局站："+maleGuest.getStationName()+
						";接入方式："+maleGuest.getPattern();
	//			System.out.println(msg);
				 CommonUtils.sendMsg(msg, msgRecive);
				 CommonUtils.sendMsg(msg, msgDaiwei);
				 CommonUtils.sendMsg(msg, msgCSJ);			 
			}
		}else{
			TransferMaleGuestReturn maleGuestReturn = new TransferMaleGuestReturn();
			maleGuestReturn.setConfCRMTicketNo(maleGuest.getMaleGuestNumber());
			maleGuestReturn.setFlag("4");
			maleGuestReturn.setReason_id("0");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			maleGuestReturn.setBack_dt(sdf.format(new Date()));
			maleGuestReturn.setBack_info("系统自动");
			maleGuestReturn.setBack_userid("系统自动");
			maleGuestReturn.setBack_username("系统自动");
			maleGuestReturn.setReason_name("系统自动");
	Thread aThread = new Thread(new MaleGuestReturnThead(sFormat
			.format(new Date()),
			CommonUtils.GK_MALE_GUEST_RECEIPT_METHOD, maleGuestReturn));
	aThread.start();
		}
				
	}
/**
 * 生成工单的方法
 */
   private String generateProcess(PnrTransferOffice maleGuest,String tprocessInstanceId){
	   
	    maleGuest.setParentProcessInstanceId(tprocessInstanceId);
	// 一切判断都是上层，这层单纯接收数据，存数据，发单！
		// 施工队短信接收人
		String msgRecive = maleGuest.getTaskAssignee();
		//外包公司短信接收人
		String msgDaiwei = maleGuest.getInitiator();
		//传输局短信接收人
		String msgCSJ = maleGuest.getTransferOfficeId();

		String taskAssigneeJSON = "";
		String dueDateString = "";
		if(maleGuest.getCreateTime() != null){
			dueDateString = sFormat.format(maleGuest.getCreateTime()
					.getTime()
					+ maleGuest.getProcessLimit() * 60 * 60 * 1000);
		}

		String processInstanceId = maleGuest.getProcessInstanceId();
		String initiator = maleGuest.getInitiator();

		taskAssigneeJSON = "[{id:'" + maleGuest.getTaskAssignee()
				+ "',nodeType:'user',categoryId:'taskAssignee'}]";

		FormService formService = (FormService) ApplicationContextHolder
				.getInstance().getBean("formService");
		IdentityService identityService = (IdentityService) ApplicationContextHolder
				.getInstance().getBean("identityService");
		identityService.setAuthenticatedUserId(initiator);
		RuntimeService runtimeService = (RuntimeService) ApplicationContextHolder
				.getInstance().getBean("runtimeService");
		TaskService taskService = (TaskService) ApplicationContextHolder
				.getInstance().getBean("taskService");

		if (processInstanceId == null || "".equals(processInstanceId)) {
			ProcessInstance processInstance = runtimeService
					.startProcessInstanceByKey("myMaleGuestProcess");
			processInstanceId = processInstance.getId();
		}

		List<Task> taskList = taskService.createTaskQuery().processInstanceId(
				processInstanceId).active().list();
		String taskId = taskList.get(0).getId();

		Map<String, String> map = new HashMap<String, String>();
		map.put("dueDate", dueDateString);//处理时限
		map.put("taskAssignee", maleGuest.getTaskAssignee());
		if (maleGuest.getTaskAssignee() != null
				&& !"".equals(maleGuest.getTaskAssignee())) {

			formService.submitTaskFormData(taskId, map);
			
			// 流程结束
			if (maleGuest.getState() == 6) {
				maleGuest.setOneInitiator(maleGuest.getInitiator());
				maleGuest.setInitiator(maleGuest.getTaskAssignee());
			}

			IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) ApplicationContextHolder
					.getInstance().getBean("pnrTransferOfficeService");

			maleGuest.setProcessInstanceId(processInstanceId);
			maleGuest.setTaskAssigneeJSON(taskAssigneeJSON);
			try {
				if(!"1".equals(maleGuest.getMaleGuestState()) || maleGuest.getSendTime() == null){ //主工单的派单时间，取子工单的最早的派单时间
					maleGuest.setSendTime(sFormat.parse(sFormat.format(new Date())));
				}
				maleGuest.setEndTime(sFormat.parse(dueDateString));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			//新增将流转环节添加到主表中 start
			processTaskService.setTvalueOfTask(processInstanceId, msgRecive, maleGuest, PnrTransferOffice.class, null, null,false);
			//新增将流转环节添加到主表中 end
			pnrTransferOfficeService.save(maleGuest);
		
        }
		
		return processInstanceId;
   }

	/**
	 * 	 抢修工单审核 一次核验统计数
	 	 * @author WangJun
	 	 * @title: insertResourceInventoryData
	 	 * @date Nov 27, 2015 11:08:06 AM
	 	 * @param jkXcwhRoomList
	 	 * @return int
	 */
	public int getFirstVerifyCount(String userId,MaleGuestSelectAttribute selectAttribute){
		return pnrTransferOfficeDaoJDBC.getFirstVerifyCount(userId,selectAttribute);
	}
	
	/**
	 * 	 抢修工单审核 一次核验列表
	 	 * @author WangJun
	 	 * @title: insertResourceInventoryData
	 	 * @date Nov 27, 2015 11:08:06 AM
	 	 * @param jkXcwhRoomList
	 	 * @return int
	 */
	public List<TaskModel> getFirstVerifyList(String userId,MaleGuestSelectAttribute selectAttribute, int firstResult, int endResult,int pageSize){
		List<TaskModel> list = pnrTransferOfficeDaoJDBC.getFirstVerifyList(userId,selectAttribute,firstResult, endResult, pageSize);
		return list;
	}
	
	/**
	 * 	 抢修工单审核 二次抽检统计数
	 	 * @author WangJun
	 	 * @title: insertResourceInventoryData
	 	 * @date Nov 27, 2015 11:08:06 AM
	 	 * @param jkXcwhRoomList
	 	 * @return int
	 */
	public int getSecondInspectionCount(String userId,MaleGuestSelectAttribute selectAttribute){
		return pnrTransferOfficeDaoJDBC.getSecondInspectionCount(userId,selectAttribute);
	}
	
	/**
	 * 	 抢修工单审核 二次抽检列表
	 	 * @author WangJun
	 	 * @title: insertResourceInventoryData
	 	 * @date Nov 27, 2015 11:08:06 AM
	 	 * @param jkXcwhRoomList
	 	 * @return int
	 */
	public List<TaskModel> getSecondInspectionList(String userId,MaleGuestSelectAttribute selectAttribute, int firstResult, int endResult,int pageSize){
		List<TaskModel> list = pnrTransferOfficeDaoJDBC.getSecondInspectionList(userId,selectAttribute,firstResult, endResult, pageSize);
		return list;
	}
	
	/**
	 * 	 抢修工单审核 抢修材料周期领用表统计数
	 	 * @author WangJun
	 	 * @title: insertResourceInventoryData
	 	 * @date Nov 27, 2015 11:08:06 AM
	 	 * @param jkXcwhRoomList
	 	 * @return int
	 */
	public int getRepairMaterialCycleTableCount(String userId,MaleGuestSelectAttribute selectAttribute){
		return pnrTransferOfficeDaoJDBC.getRepairMaterialCycleTableCount(userId,selectAttribute);
	}
	
	/**
	 * 	 抢修工单审核 抢修材料周期领用表列表
	 	 * @author WangJun
	 	 * @title: insertResourceInventoryData
	 	 * @date Nov 27, 2015 11:08:06 AM
	 	 * @param jkXcwhRoomList
	 	 * @return int
	 */
	public List<TaskModel> getRepairMaterialCycleTableList(String userId,MaleGuestSelectAttribute selectAttribute, int firstResult, int endResult,int pageSize){
		List<TaskModel> list = pnrTransferOfficeDaoJDBC.getRepairMaterialCycleTableList(userId,selectAttribute,firstResult, endResult, pageSize);
		return list;
	}
	
	/**
	 * 	 生成一次核验报表
	 	 * @author WangJun
	 	 * @title: collectFirstVerifyData
	 	 * @date Nov 27, 2015 11:08:06 AM
	 	 * @param jkXcwhRoomList
	 	 * @return int
	 */
	public Map<String,Object> collectFirstVerifyData(Map<String,String> param){
		//1.查询前一天的0点到24点的施工队回单的公客工单数据
		List<FirstVerifyReportModel> list = this.getYesterdayMaleGuestCompletionOfConstruction(param);
		//2.将一次核验数据插入到表中
		int total = this.insertFirstVerifyData(list,param);
		
		//返回结果
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("total", total);
		return resultMap;
	}
	
	/**
	 * 	 查询前一天的0点到24点的施工队回单的公客工单数据
	 	 * @author WangJun
	 	 * @title: getYesterdayMaleGuestCompletionOfConstruction
	 	 * @date Nov 27, 2015 11:08:06 AM
	 	 * @param jkXcwhRoomList
	 	 * @return int
	 */
	private List<FirstVerifyReportModel> getYesterdayMaleGuestCompletionOfConstruction(Map<String,String> param){
		String processType = "maleGuest";//默认公客
		if(param.get("processType") != null && !"".equals(param.get("processType"))){
			processType = param.get("processType");
		}
		List<FirstVerifyReportModel> list= new ArrayList<FirstVerifyReportModel>();
		IEomsDao ed = (IEomsDao)ApplicationContextHolder.getInstance().getBean("eomsDao");
		Connection con=null;
	    PreparedStatement ps=null;
	    ResultSet rs=null;
	  
	    String sql= "select m.process_instance_id,m.country,m.themeinterface" +
			    	"  from pnr_act_transfer_office_main m" + 
			    	" where m.task_def_key = 'acheck'" + 
			    	"   and m.themeinterface = '"+processType+"'" + 
			    	"   and m.country is not null" + 
			    	"   and m.batch is null" + 
			    	"   and m.last_reply_time >=" + 
			    	"       to_date(to_char(trunc(sysdate - 1), 'yyyy-mm-dd hh24:mi:ss')," + 
			    	"               'yyyy-mm-dd hh24:mi:ss')" + 
			    	"   and m.last_reply_time <= trunc(sysdate - 1) + 1 - 1 / 86400";
	   
//	    String sql=
//	    	"select m.process_instance_id, m.country\n" +
//	    	"  from pnr_act_transfer_office_main m\n" + 
//	    	" where m.last_reply_time >=\n" + 
//	    	"       to_date(to_char(trunc(sysdate - 3), 'yyyy-mm-dd hh24:mi:ss'),\n" + 
//	    	"               'yyyy-mm-dd hh24:mi:ss')\n" + 
//	    	"   and m.last_reply_time <= trunc(sysdate - 3) + 1 - 1 / 86400";

	    
	  //  System.out.println("---------查询前一天的0点到24点的施工队回单的公客工单数据="+sql);
	    try {
			con = ed.getCon();
			ps=con.prepareStatement(sql);     
			rs=ps.executeQuery();
			while(rs.next()){
				FirstVerifyReportModel firstVerifyReportModel= new FirstVerifyReportModel();
				firstVerifyReportModel.setProcessInstanceId(rs.getString("PROCESS_INSTANCE_ID"));
				firstVerifyReportModel.setCounty(rs.getString("COUNTRY"));
				firstVerifyReportModel.setProcessType(rs.getString("themeinterface"));//流程类型
		        list.add(firstVerifyReportModel);
		    }
		}  catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	/**
	 * 	 将一次核验数据插入到表中
	 	 * @author WangJun
	 	 * @title: insertFirstVerifyData
	 	 * @date Nov 27, 2015 11:08:06 AM
	 	 * @param jkXcwhRoomList
	 	 * @return int
	 */
	private int insertFirstVerifyData(List<FirstVerifyReportModel> list,Map<String,String> param){
		int total=0;
		if(list.size()>0){
			int listSize=list.size();
			IEomsDao ed = (IEomsDao)ApplicationContextHolder.getInstance().getBean("eomsDao");
			Connection con =null;
			PreparedStatement ps = null; 
			//SimpleDateFormat dateformat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//String nowTime = dateformat.format(new Date());
			PreparedStatement ps2 = null; 
			try {
				con = ed.getCon();
				con.setAutoCommit(false);// 关闭自动提交
				String sql ="insert into pnr_first_verify_report(rise_time,report_date,approve_flag,process_instance_id,county,process_type) values (sysdate,trunc(sysdate-1),'0',?,?,?)";
				String sql2 ="update pnr_act_transfer_office_main m set m.batch = 'Y' where m.process_instance_id = ?";//先存在批次的字段里，存Y值代表已经生成过了
				ps = con.prepareStatement(sql);
				ps2 = con.prepareStatement(sql2);
				for (int i = 0; i <listSize; i++) {
					//插入一次核验数据
					ps.setString(1,list.get(i).getProcessInstanceId());
					ps.setString(2,list.get(i).getCounty());
					ps.setString(3,list.get(i).getProcessType());//流程类型
					ps.addBatch();
					
					//批量更新主表
					ps2.setString(1,list.get(i).getProcessInstanceId());
					ps2.addBatch();
					
					if (i% 100 == 0) {
						int[] executeBatch = ps.executeBatch();
						ps2.executeBatch();
						con.commit();
						total=total+executeBatch.length;
						ps.clearBatch();
						ps2.clearBatch();
					}
				}
				// 最后插入不足100条的数据
				int[] executeBatch2=ps.executeBatch();
				ps2.executeBatch();
				con.commit();	
				total=total+executeBatch2.length;
			} catch (Exception e) {
				e.printStackTrace();
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}finally {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				try {
					ps2.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return total;
	}
	
	/**
	 * 	 更新报表审批状态
	 	 * @author WangJun
	 	 * @title: updateApproveFlag
	 	 * @date Nov 27, 2015 11:08:06 AM
	 	 * @param jkXcwhRoomList
	 	 * @return int
	 */
	public void updateApproveFlag(Map<String,String> param){
		pnrTransferOfficeDaoJDBC.updateApproveFlag(param);
	}
	
	/**
	 * 	 获取报表未审批的工单个数
	 	 * @author WangJun
	 	 * @title: updateApproveFlag
	 	 * @date Nov 27, 2015 11:08:06 AM
	 	 * @param jkXcwhRoomList
	 	 * @return int
	 */
	public int getNotApprovedSheetCount(MaleGuestSelectAttribute selectAttribute){
		return pnrTransferOfficeDaoJDBC.getNotApprovedSheetCount(selectAttribute);
	}
	
	/**
	 * 	 查询审批人签字
	 	 * @author WangJun
	 	 * @title: updateApproveFlag
	 	 * @date Nov 27, 2015 11:08:06 AM
	 	 * @param jkXcwhRoomList
	 	 * @return int
	 */
	public String getApprovalSign(MaleGuestSelectAttribute selectAttribute){
		return pnrTransferOfficeDaoJDBC.getApprovalSign(selectAttribute);
	}
	
	/**
	 * 	 提交审批人签字
	 	 * @author WangJun
	 	 * @title: updateApproveFlag
	 	 * @date Nov 27, 2015 11:08:06 AM
	 	 * @param jkXcwhRoomList
	 	 * @return int
	 */
	public int submitApprovalSign(MaleGuestSelectAttribute selectAttribute){
		return pnrTransferOfficeDaoJDBC.submitApprovalSign(selectAttribute);
	}
	
	/**
	 * 	 生成二次抽查报表
	 	 * @author WangJun
	 	 * @title: getYesterdayMaleGuestCompletionOfConstruction
	 	 * @date Nov 27, 2015 11:08:06 AM
	 	 * @param jkXcwhRoomList
	 	 * @return int
	 */
	public Map<String,Object> collectSecondInspectionData(Map<String,String> param){
		//1.查询前一天的0点到24点的一次核验通过的工单数据 (一次核验提交审批人签字的工单)
		List<SecondInspectionReportModel> list = this.getYesterdayFirstVerifyCompletion(param);
		//2.将二次抽查数据插入到表中
		int total = this.insertSecondInspectionData(list,param);
		
		//返回结果
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("total", total);
		return resultMap;
	}
	
	/**
	 * 	 查询前一天的0点到24点的一次核验通过的工单数据
	 	 * @author WangJun
	 	 * @title: getYesterdayMaleGuestCompletionOfConstruction
	 	 * @date Nov 27, 2015 11:08:06 AM
	 	 * @param jkXcwhRoomList
	 	 * @return int
	 */
	private List<SecondInspectionReportModel> getYesterdayFirstVerifyCompletion(Map<String,String> param){
		List<SecondInspectionReportModel> list= new ArrayList<SecondInspectionReportModel>();
		IEomsDao ed = (IEomsDao)ApplicationContextHolder.getInstance().getBean("eomsDao");
		Connection con=null;
	    PreparedStatement ps=null;
	    ResultSet rs=null;
	    String sql ="select r.process_instance_id, r.county, r.process_type" +
			    	"  from pnr_first_verify_report r, pnr_act_transfer_office_main m" + 
			    	" where r.process_instance_id = m.process_instance_id" + 
			    	"   and m.task_def_key = 'twoSpotChecks'" + 
			    	"   and r.approve_flag = '1'" + 
			    	"   and m.job_order_type is null" + 
			    	"   and r.submit_date >=" + 
			    	"       to_date(to_char(trunc(sysdate - 1), 'yyyy-mm-dd hh24:mi:ss')," + 
			    	"               'yyyy-mm-dd hh24:mi:ss')" + 
			    	"   and r.submit_date <= trunc(sysdate - 1) + 1 - 1 / 86400";

	  //  System.out.println("---------查询前一天的0点到24点的一次核验通过的工单数据 (一次核验提交审批人签字的工单)="+sql);
	    try {
			con = ed.getCon();
			ps=con.prepareStatement(sql);     
			rs=ps.executeQuery();
			while(rs.next()){
				SecondInspectionReportModel secondInspectionReportModel= new SecondInspectionReportModel();
				secondInspectionReportModel.setProcessInstanceId(rs.getString("PROCESS_INSTANCE_ID"));
				secondInspectionReportModel.setCounty(rs.getString("county"));
				secondInspectionReportModel.setProcessType(rs.getString("process_type"));
		        list.add(secondInspectionReportModel);
		    }
		}  catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 	 将二次抽查数据插入到表中
	 	 * @author WangJun
	 	 * @title: insertFirstVerifyData
	 	 * @date Nov 27, 2015 11:08:06 AM
	 	 * @param jkXcwhRoomList
	 	 * @return int
	 */
	private int insertSecondInspectionData(List<SecondInspectionReportModel> list,Map<String,String> param){
		int total=0;
		if(list.size()>0){
			int listSize=list.size();
			IEomsDao ed = (IEomsDao)ApplicationContextHolder.getInstance().getBean("eomsDao");
			Connection con =null;
			PreparedStatement ps = null; 
			//SimpleDateFormat dateformat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//String nowTime = dateformat.format(new Date());
			PreparedStatement ps2 = null; 
			try {
				con = ed.getCon();
				con.setAutoCommit(false);// 关闭自动提交
				String sql ="insert into pnr_second_inspection_report(rise_time,report_date,approve_flag,process_instance_id,county,process_type) values (sysdate,trunc(sysdate-1),'0',?,?,?)";
				String sql2 ="update pnr_act_transfer_office_main m set m.job_order_type = 'Y' where m.process_instance_id = ?";//先存在m.job_order_type的字段里，存Y值代表已经生成过了
				ps = con.prepareStatement(sql);
				ps2 = con.prepareStatement(sql2);
				for (int i = 0; i <listSize; i++) {
					ps.setString(1,list.get(i).getProcessInstanceId());
					ps.setString(2,list.get(i).getCounty());
					ps.setString(3,list.get(i).getProcessType());
					ps.addBatch();
					
					//批量更新主表
					ps2.setString(1,list.get(i).getProcessInstanceId());
					ps2.addBatch();
					
					if (i% 100 == 0) {
						int[] executeBatch = ps.executeBatch();
						ps2.executeBatch();
						con.commit();
						total=total+executeBatch.length;
						ps.clearBatch();
						ps2.clearBatch();
					}
				}
				// 最后插入不足100条的数据
				int[] executeBatch2=ps.executeBatch();
				ps2.executeBatch();
				con.commit();	
				total=total+executeBatch2.length;
			} catch (Exception e) {
				e.printStackTrace();
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}finally {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				try {
					ps2.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return total;
	}
	
	/**
	 * 	 二次抽查报表审批率
	 	 * @author WangJun
	 	 * @title: insertFirstVerifyData
	 	 * @date Nov 27, 2015 11:08:06 AM
	 	 * @param jkXcwhRoomList
	 	 * @return int
	 */
	public double calApprovalRate(String userId,MaleGuestSelectAttribute selectAttribute){
		double result=0.0;
		//未审批的工单数
		int notApprovedSheetCount = pnrTransferOfficeDaoJDBC.getNotApprovedSheetCount(selectAttribute);
		//该报表的工单总数
		int total = pnrTransferOfficeDaoJDBC.getSecondInspectionCount(userId,selectAttribute);
		if(total > 0){
			//已审批的工单数
			int approvedSheetCount=total-notApprovedSheetCount;
			//审批率
		    result = CommonUtils.div(Double.valueOf(approvedSheetCount), Double.valueOf(total), 2);
		}
		return result;
	}
	
	/**
	 * 	 生成周期领用表
	 	 * @author WangJun
	 	 * @title: insertFirstVerifyData
	 	 * @date Nov 27, 2015 11:08:06 AM
	 	 * @param jkXcwhRoomList
	 	 * @return int
	 */
	public String createCycleCollarReport(String userId,String total,MaleGuestSelectAttribute selectAttribute){
		int firstResult=0;
		int endResult=Integer.parseInt(total);
		int pageSize=1;
		List<TaskModel> repairMaterialCycleTableList = this.getRepairMaterialCycleTableList(userId,selectAttribute,firstResult, endResult, pageSize);
		String reportNumber = this.insertCycleCollarReport(repairMaterialCycleTableList, userId, selectAttribute);
		return reportNumber;
	}
	
	/**
	 * 	 插入周期领用表
	 	 * @author WangJun
	 	 * @title: insertFirstVerifyData
	 	 * @date Nov 27, 2015 11:08:06 AM
	 	 * @param jkXcwhRoomList
	 	 * @return int
	 */
	private String insertCycleCollarReport(List<TaskModel> list,String userId,MaleGuestSelectAttribute selectAttribute){
		//报表编号
		//String reportNumber="1";
		String reportNumber = this.generateReportNumber(selectAttribute, "cycleCollarReport");
		System.out.println("------reportNumber="+reportNumber);
		SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");
		String startDate=selectAttribute.getBeginTime();
		String endDate=selectAttribute.getEndTime();
		List<CycleCollarReportModel> reportList=new ArrayList<CycleCollarReportModel>();
		Iterator<TaskModel> iterator = list.iterator(); 
	        while(iterator.hasNext()){ 
	        	TaskModel taskModel = iterator.next();   
	        	CycleCollarReportModel reportModel=new CycleCollarReportModel();
	        	reportModel.setProcessInstanceId(taskModel.getProcessInstanceId());//工单流程id
	        	reportModel.setCounty(taskModel.getCountry());//区县
	        	reportModel.setProcessType(taskModel.getProcessType());//工单类型
	        	try {
					reportModel.setStartDate(f.parse(startDate));//开始时间
					reportModel.setEndDate(f.parse(endDate));//结束时间
					reportModel.setHomeDate(f.parse(taskModel.getTempTask()));//归属日期
				} catch (ParseException e) {
					e.printStackTrace();
				}
				reportModel.setReportNumber(reportNumber);//报表编号
	        	reportList.add(reportModel);
	       }  
	    pnrTransferOfficeDaoJDBC.insertCycleCollarReport(reportList,userId,selectAttribute);
		return reportNumber;
	}
	
	/**
	 * 	 生成报表编号
	 	 * @author WangJun
	 	 * @title: insertFirstVerifyData
	 	 * @date Nov 27, 2015 11:08:06 AM
	 	 * @param jkXcwhRoomList
	 	 * @return int
	 */
	private String generateReportNumber(MaleGuestSelectAttribute selectAttribute,String flag){
		String reportNumber="";
		//1.地市缩写
		String regionStr="weizhi";//默认未知
		String region = StaticMethod.nullObject2String(selectAttribute.getRegion());
		if(!"".equals(region)){
			regionStr = this.getRegionAbbreviation(region);
		}
		//2.当前年月日
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();	
		String dateStr = dateFormat.format(date);
		
		//3.最后的编号
	    int maxNumberValue =0;
		maxNumberValue = pnrTransferOfficeDaoJDBC.getCurrDateSheetCountNum(flag);
		String numberValue=Integer.toString(maxNumberValue);
		if(numberValue.length()== 1){
			numberValue="00"+numberValue;
		}else if(numberValue.length()== 2){
			numberValue="0"+numberValue;
		}
		reportNumber=regionStr+dateStr+numberValue;
		return reportNumber;
	}
	
	/**
	 * 	 地市缩写转换
	 	 * @author WangJun
	 	 * @title: insertFirstVerifyData
	 	 * @date Nov 27, 2015 11:08:06 AM
	 	 * @param jkXcwhRoomList
	 	 * @return int
	 */
	private String getRegionAbbreviation(String region){
		String abb="weizhi";
		if("2811".equals(region)){//威海
			abb="wh";
		}else if("2812".equals(region)){//日照
			abb="rz";
		}else if("2813".equals(region)){//滨州
			abb="bz";
		}else if("2814".equals(region)){//德州
			abb="dz";
		}else if("2815".equals(region)){//聊城
			abb="lc";
		}else if("2816".equals(region)){//临沂
			abb="ly";
		}else if("2817".equals(region)){//菏泽
			abb="hz";
		}else if("2818".equals(region)){//莱芜
			abb="lw";
		}else if("2819".equals(region)){//济南
			abb="jn";
		}else if("2820".equals(region)){//青岛
			abb="qd";
		}else if("2821".equals(region)){//淄博
			abb="zb";
		}else if("2822".equals(region)){//枣庄
			abb="zz";
		}else if("2823".equals(region)){//东营
			abb="dy";
		}else if("2824".equals(region)){//烟台
			abb="yt";
		}else if("2825".equals(region)){//潍坊
			abb="wf";
		}else if("2826".equals(region)){//济宁
			abb="ji";
		}else if("2827".equals(region)){//泰安
			abb="ta";
		}
		return abb;
	}

	
	
	/**
	 * 	 获取处理人（公用）
	 	 * @author WangJun
	 	 * @title: insertFirstVerifyData
	 	 * @date Nov 27, 2015 11:08:06 AM
	 	 * @param jkXcwhRoomList
	 	 * @return int
	 */
	public String getDealingPeopleOfRepair(String areaId,String roleId){
		return pnrTransferOfficeDaoJDBC.getDealingPeopleOfRepair(areaId,roleId);
	}
	
//	//去周期领用表的报表编号
//	public List<String> getReportNumbers(String userId,MaleGuestSelectAttribute selectAttribute){
//		return pnrTransferOfficeDaoJDBC.getReportNumbers(userId,selectAttribute);
//	}
//	
//	//生成周期领用表，报表编号+开始日期+结束日期+所属日期
//	public String getCycleCollarReportMsg(List<String> list,String userId,MaleGuestSelectAttribute selectAttribute){
//	   String msg="";
//       Iterator<String> iterator = list.iterator(); 
//        while(iterator.hasNext()){
//        	String st="";
//        	String et="";
//        	String reportNumber = iterator.next();                   
//        	List<CycleCollarReportModel> model = pnrTransferOfficeDaoJDBC.getCycleCollarReportMsg(reportNumber, userId, selectAttribute);
//        	if(){
//        		
//        	}
//        	
//         
//       } 
//
//		return null;
//	}
	
	//根据开始日期和结束日期，查询这个开始日期和这个结束日期已经生成的周期领用表 条数
	public int getCycleCollarReportCountByStartDateAndEndDate(String userId,MaleGuestSelectAttribute selectAttribute){
		return pnrTransferOfficeDaoJDBC.getCycleCollarReportCountByStartDateAndEndDate(userId,selectAttribute);
	}
	
	//根据开始日期和结束日期，查询这个开始日期和这个结束日期已经生成的周期领用表 集合
	public List<TaskModel> getCycleCollarReportListByStartDateAndEndDate(String userId,MaleGuestSelectAttribute selectAttribute,int firstResult,int endResult,int pageSize){
		return pnrTransferOfficeDaoJDBC.getCycleCollarReportListByStartDateAndEndDate(userId,selectAttribute,firstResult,endResult,pageSize);
	}

	//根据开始日期和结束日期，在这个时间范围内已经生成的周期领用表（用开始日期和结束日期查询不到数据的时候）
	public List<CycleCollarReportModel> getCycleCollarReportListByHomeDate(String userId,MaleGuestSelectAttribute selectAttribute){
		return pnrTransferOfficeDaoJDBC.getCycleCollarReportListByHomeDate(userId,selectAttribute);
	}
	
	//周期领用表 拼接提示信息
	public String getCycleCollarReportMsg(List<CycleCollarReportModel> list){
		 String str="";
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		 Iterator<CycleCollarReportModel> iterator = list.iterator(); 
		 while(iterator.hasNext()){ 
			 CycleCollarReportModel model =  iterator.next();                   
			 str+="编号："+model.getReportNumber()+",开始日期："+format.format(model.getStartDate())+",结束日期："+format.format(model.getEndDate())+";";
		 }
		 return str;
	}
	
	//判断周期领用表是否已提交
	public String isSubmitOfCycleCollarReport(String userId,MaleGuestSelectAttribute selectAttribute){
		return pnrTransferOfficeDaoJDBC.getSubmitUserIdOfCycleCollarReport(userId,selectAttribute);
	}
	
	//获取周期领用表的部分字段的值（公用）
	public String getAttributeValueOfCycleCollarReport(String userId,MaleGuestSelectAttribute selectAttribute,String flag){
		return pnrTransferOfficeDaoJDBC.getAttributeValueOfCycleCollarReport(userId,selectAttribute,flag);
	}
	
	//提交周期领用表
	public void submitCreateCycleCollarReport(String userId,String reportNumber,String fuJianVal,MaleGuestSelectAttribute selectAttribute){
		TawCommonsAccessoriesDao tawCommonsAccessoriesDao = (TawCommonsAccessoriesDao) ApplicationContextHolder.getInstance().getBean("tawCommonsAccessoriesDao");	
	    List<TawCommonsAccessories> list = tawCommonsAccessoriesDao.getAllFileByName(fuJianVal);
	    String accessorieId="";
	    String accessorieName="";
	    Iterator<TawCommonsAccessories> iterator = list.iterator(); 
		while(iterator.hasNext()){ 
			 TawCommonsAccessories tawCommonsAccessories =  iterator.next();  
			 accessorieId+=tawCommonsAccessories.getId()+",";
			 accessorieName+=tawCommonsAccessories.getAccessoriesName()+",";
		 }
		accessorieId=accessorieId.substring(0, accessorieId.length()-1);
		accessorieName=accessorieName.substring(0, accessorieName.length()-1);
		
		pnrTransferOfficeDaoJDBC.submitCreateCycleCollarReport(userId,reportNumber,fuJianVal,selectAttribute,accessorieId,accessorieName);
	}  
		public int getToreplyJobOfMaleGuestTransmissionBureauJobNoCount(
			String userId, MaleGuestSelectAttribute selectAttribute) {
		return pnrTransferOfficeDaoJDBC
		.getToreplyJobOfMaleGuestTransmissionBureauJobNoCount(userId,
				selectAttribute);
		
	}
		public List<TaskModel> getToreplyJobOfMaleGuestTransmissionBureauJobNoList(
			String userId, MaleGuestSelectAttribute selectAttribute, int firstResult,
			int endResult, int pageSize) {
		
		List<TaskModel> list = pnrTransferOfficeDaoJDBC
		.getToreplyJobOfMaleGuestTransmissionBureauJobNoList(userId,
				selectAttribute,
				firstResult, endResult, pageSize);
		
		return list;
	}
	public List<TaskModel> getToreplyJobOfMaleGuestTransmissionBureauJobParentList(
			String userId, MaleGuestSelectAttribute selectAttribute, int firstResult,
			int endResult, int pageSize) {
		
		List<TaskModel> list = pnrTransferOfficeDaoJDBC
		.getToreplyJobOfMaleGuestTransmissionBureauJobParentList(userId,
				selectAttribute,
				firstResult, endResult, pageSize);
		
		return list;
	}
	public List<TaskModel> getToreplyJobOfMaleGuestTransmissionBureauJobParentNoList(
			String userId, MaleGuestSelectAttribute selectAttribute, int firstResult,
			int endResult, int pageSize) {
		
		List<TaskModel> list = pnrTransferOfficeDaoJDBC
		.getToreplyJobOfMaleGuestTransmissionBureauJobParentNoList(userId,
				selectAttribute,
				firstResult, endResult, pageSize);
		
		return list;
	}
	//归集工单列表统计数
	public List<TaskModel> querySingleImputationList(String userId,MaleGuestSelectAttribute selectAttribute, int firstResult, int endResult,int pageSize){
		List<TaskModel> list = pnrTransferOfficeDaoJDBC.querySingleImputationList(userId,selectAttribute,firstResult, endResult, pageSize);
		return list;
	}
	//取周期领用表上传的附件信息
	public String getAttachmentsOfCycleCollarReport(String userId,MaleGuestSelectAttribute selectAttribute, String flag){
		String accessories="";
		String val = this.getAttributeValueOfCycleCollarReport(userId, selectAttribute, flag);
		if(!"".equals(val)){
			String[] split = val.split(",");
			for(int i=0;i<split.length;i++){
				accessories="'"+split[i]+"',";
			}
			accessories=accessories.substring(0, accessories.length()-1);
		}
		return accessories;
	}
	
	//取周期领用表上传的附件信息
	public String getAttachmentsOfCycleCollarReport(String reportNumber){
		String accessories="";
		String val = pnrTransferOfficeDaoJDBC.getAttachmentsOfCycleCollarReport(reportNumber);
		if(!"".equals(val)){
			String[] split = val.split(",");
			for(int i=0;i<split.length;i++){
				accessories+="'"+split[i]+"',";
			}
			accessories=accessories.substring(0, accessories.length()-1);
		}
		System.out.println("------accessories="+accessories);
		return accessories;
	} 
	
	//取二次核验已提交报表中的超时工单
	public List<String> getTimeOverListOfSecond(MaleGuestSelectAttribute selectAttribute){
		return pnrTransferOfficeDaoJDBC.getTimeOverListOfSecond(selectAttribute);
	}
	
	//获取一次核验，二次抽检的部分字段的值（公用）
	public String getAttributeValueOfFirstAndSecond(String userId,MaleGuestSelectAttribute selectAttribute,String flag){
		return pnrTransferOfficeDaoJDBC.getAttributeValueOfFirstAndSecond(userId,selectAttribute,flag);
	}
	
	//用eoms表单导出的继承方法
	public List excelExportToProcess(Search search, String userId,
			String deptId, String queryFlag, String processKey, String flag,
			HttpServletRequest request) {
		List<TaskModel> list = null;
		//分页
		int firstResult = 0;
		int endResult = 1;
		int pageSize = 65000;
		
		//查询条件
		MaleGuestSelectAttribute selectAttribute = null;
		if("cycleInter".equals(queryFlag)){
			selectAttribute = this.getCycleInter(request);
		}else{
			selectAttribute = this.getMaleGuestSelectAttribute(request);
		}
		if("first".equals(queryFlag)){ //一次核验
			list = pnrTransferOfficeDaoJDBC.getFirstVerifyList(userId, selectAttribute, firstResult, endResult, pageSize);
		}else if("second".equals(queryFlag)){ //二次抽检
			list = pnrTransferOfficeDaoJDBC.getSecondInspectionList(userId, selectAttribute, firstResult, endResult, pageSize);
		}else if("cycle".equals(queryFlag) || "cycleInter".equals(queryFlag)){ //周期领用表
			list =  pnrTransferOfficeDaoJDBC.getCycleCollarReportListByStartDateAndEndDate(userId,selectAttribute,firstResult, endResult, pageSize);
		}
		return list;
	}
	
	private  MaleGuestSelectAttribute getCycleInter(HttpServletRequest request){
		MaleGuestSelectAttribute selectAttribute = new MaleGuestSelectAttribute();
        String country = StaticMethod.nullObject2String(request.getParameter("tCountry"));//区县
        selectAttribute.setCounty(country);
		String tStartTime = StaticMethod.nullObject2String(request.getParameter("tStartTime"));//开始时间
		selectAttribute.setBeginTime(tStartTime);
		String tEndTime = StaticMethod.nullObject2String(request.getParameter("tEndTime"));//结束时间
		selectAttribute.setEndTime(tEndTime);
		return selectAttribute;
	}
	
	//导出用的查询条件
	private MaleGuestSelectAttribute getMaleGuestSelectAttribute(HttpServletRequest request){
		MaleGuestSelectAttribute selectAttribute = new MaleGuestSelectAttribute();
		String region = StaticMethod.nullObject2String(request.getParameter("region"));// 地市
		String country = StaticMethod.nullObject2String(request.getParameter("country"));//区县
		//开始时间
		String sendStartTime = StaticMethod.nullObject2String(request.getParameter("sheetAcceptLimit"));
		//结束时间
		String endTime = StaticMethod.nullObject2String(request.getParameter("sheetCompleteLimit"));
		
		selectAttribute.setCounty(country);
		selectAttribute.setBeginTime(sendStartTime);
		selectAttribute.setEndTime(endTime);
		return selectAttribute;
	}
	
	//施工照片补录 照片总数
	public int getMakeupPhotosCount(String userId,MaleGuestSelectAttribute selectAttribute){
		return pnrTransferOfficeDaoJDBC.getMakeupPhotosCount(userId,selectAttribute);
	}
	
	//施工照片补录 照片集合
	public List<TaskModel> getMakeupPhotosList(String userId,MaleGuestSelectAttribute selectAttribute, int firstResult, int endResult,int pageSize){
		return pnrTransferOfficeDaoJDBC.getMakeupPhotosList(userId,selectAttribute,firstResult,endResult,pageSize);
	}
	
	//查询补录照片
	public List<PnrAndroidPhotoFile> getPrecheckPhotoes(Map<String,String> param){
		return pnrTransferOfficeDaoJDBC.getPrecheckPhotoes(param);
	}
	
	//获取照片的最小时间
	public String getMinDateOfPhoto(String photoesIds,Map<String,String> param){
		return pnrTransferOfficeDaoJDBC.getMinDateOfPhoto(photoesIds,param);
	}
	
	//查看现场照片列表
	public List<PnrAndroidPhotoFile> getLiveCameraList(Map<String,String> param){
		return pnrTransferOfficeDaoJDBC.getLiveCameraList(param);
	}
	
	//查看归集工单子工单
	public List<TaskModel> getSubWorkOrderOfSingleImputationList(String userId,MaleGuestSelectAttribute selectAttribute, int firstResult, int endResult,int pageSize){
		return pnrTransferOfficeDaoJDBC.getSubWorkOrderOfSingleImputationList(userId,selectAttribute,firstResult,endResult,pageSize);
	}
	
	//获取主场景中文值和子场景的中文值
	public Map<String,String> getMainScenesAndCopyScenesName(HttpServletRequest request,Map<String,String> param){
		String mainScenesName = ""; //主场景名
		String copyScenesName = ""; //子场景名
		
		String[] mainScenes=request.getParameterValues("mainScene");
		if(mainScenes != null && mainScenes.length > 0){
			String mainSceneId = mainScenes[0];
			mainScenesName = pnrTransferOfficeDaoJDBC.getMainScenesNameById(mainSceneId);
			String[] copyScenes=request.getParameterValues(mainSceneId+"-check");	
			if(copyScenes != null && copyScenes.length > 0){
				String copySceneId = copyScenes[0];
				copyScenesName = pnrTransferOfficeDaoJDBC.getCopyScenesNameById(copySceneId);
			}
		}
		//返回结果
		Map<String,String> result = new HashMap<String,String>();
		result.put("mainScenesName", mainScenesName);
		result.put("copyScenesName", copyScenesName);
		return result;
	}
	
	//审批位置统计信息（材料金额、电杆、光缆数量、接头盒数量）
	public List<StatisticsMaterialInforModel> statisticsMaterialInfor(String userId,MaleGuestSelectAttribute selectAttribute,String reportFlag){
		return pnrTransferOfficeDaoJDBC.statisticsMaterialInfor(userId,selectAttribute,reportFlag);
	}
	
	//显示公客场景模板明细
	public List<MaleSceneDetailModel> getMaleSceneDetail(String processInstanceId,String linkFlag,Map<String,String> param){
		return pnrTransferOfficeDaoJDBC.getMaleSceneDetail(processInstanceId,linkFlag,param);
	}
	
	//导出详情
	public HSSFWorkbook exportDetails(String userId,String reportFlag,MaleGuestSelectAttribute selectAttribute,Map<String,String> param){
		HSSFWorkbook wb = new HSSFWorkbook();
		//-------------创建sheet页--------------
		//第一个sheet页
		String sheetName0 = "列表";
		HSSFSheet sheet0 = wb.createSheet(sheetName0); 
		
		//第二个sheet页
		String sheetName1 = "子工单";
		HSSFSheet sheet1 = wb.createSheet(sheetName1);
		
		//第三个sheet页
		String sheetName2 = "场景模板";
		HSSFSheet sheet2 = wb.createSheet(sheetName2);
		
		//-------------创建表头------------------
		//第一个sheet页的表头
		List<String> header0 = Arrays.asList(new String[]{"审批","工单号","工单类型","工单主题","地市","区县","是否归集","归集工单查看","主场景","子场景","场景模板","公客系统申告时间","派单时间","处理时限(小时)","业务号码","工单子类型","局站名称","所属区域","代维公司","故障原因","故障发生时间","故障处理回复时间","工单历时","当前状态","联系人","地址","当前处理人"});
		if("auditReport".equals(reportFlag)){
			header0 = Arrays.asList(new String[]{"工单号","工单类型","工单主题","地市","区县","是否归集","归集工单查看","主场景","子场景","场景模板","公客系统申告时间","派单时间","处理时限(小时)","业务号码","工单子类型","局站名称","所属区域","代维公司","故障原因","故障发生时间","故障处理回复时间","工单历时","当前状态","联系人","地址","当前处理人"}); 
		}
	
		HSSFRow row0_0 = sheet0.createRow(0);
		for(int i=0;i <header0.size();i++){
			HSSFCell cell = row0_0.createCell((short) i);
			cell.setCellValue(new HSSFRichTextString(header0.get(i))); 
		}
		
		//第二个sheet页的表头
		String[] header1 = {"主工单号","子工单号","工单主题","业务号码","故障发生时间","派单时间","工单历时","处理时限(小时)","当前状态","联系人","地址","当前处理人","局站名称","所属区域","代维公司"};
		HSSFRow row1_0 = sheet1.createRow(0);
		for(int i=0;i <header1.length;i++){
			HSSFCell cell = row1_0.createCell((short) i);
			cell.setCellValue(new HSSFRichTextString(header1[i])); 
		}
		
		//第三个sheet页的表头
		String[] header2 = {"工单号","主场景","子场景","处理措施","单位","材料","数量","单位","单价","总额","是否利旧"};
		HSSFRow row2_0 = sheet2.createRow(0);
		for(int i=0;i <header2.length;i++){
			HSSFCell cell = row2_0.createCell((short) i);
			cell.setCellValue(new HSSFRichTextString(header2[i])); 
		}
		
		
		//-------------向第一个列表sheet0页写入数据------------------
		List<TaskModel> list0 = null;
		if(reportFlag.equals("acheck")){ //一次核验
			list0 = this.getFirstVerifyList(userId,selectAttribute,0, 1, 10000);
		}else if(reportFlag.equals("twoSpotChecks")){ //二次抽检
			list0 = this.getSecondInspectionList(userId,selectAttribute,0, 1, 10000);
		}else if(reportFlag.equals("auditReport")){ //周期表
			list0 = this.getCycleCollarReportListByStartDateAndEndDate(userId,selectAttribute,0, 1, 10000);
		}
		int rowNum0 = 1; //sheet0真实数据开始的行数
		int rowNum1 = 1; //sheet1真实数据开始的行数
		int linkRowNum1 = 2; //sheet1链接的起始行
		int mergedStartRowNum1 = 1;//sheet1中合并单元格起始行号
		
		int rowNum2 = 1; //sheet2真实数据开始的行数
		int linkRowNum2 = 2; //sheet2链接的起始行
		int mergedStartRowNum2 = 1;//sheet2中合并单元格起始行号
		
		ID2NameService service = (ID2NameService) ApplicationContextHolder.getInstance().getBean("ID2NameGetServiceCatch");
		//service.id2Name(id, "ItawSystemDictTypeDao");
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(list0 != null && list0.size() > 0){
			for(TaskModel model0:list0){
				int colNum = 0;
				HSSFRow row = sheet0.createRow(rowNum0);
				if(!"auditReport".equals(reportFlag)){
					row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model0.getApproveFlagName()));//审批
				}
				row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model0.getProcessInstanceId()));//工单号
				row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model0.getProcessTypeName()));//工单类型
				row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model0.getTheme()));//工单主题
				row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(service.id2Name(model0.getCity(),"tawSystemAreaDao")));//地市 //tawSystemAreaDao
				row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(service.id2Name(model0.getCountry(),"tawSystemAreaDao")));//区县 //tawSystemAreaDao
				row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model0.getMaleGuestStateName()));//是否归集
				//-------------查看子工单 开始-------------- //归集工单查看
				if("1".equals(model0.getMaleGuestState())){
					MaleGuestSelectAttribute selectAttribute1 = new MaleGuestSelectAttribute();
					selectAttribute1.setProcessInstanceId(model0.getProcessInstanceId());
					selectAttribute1.setSiteCd(model0.getSiteCd());
					List<TaskModel> subOrderList = this.getSubWorkOrderOfSingleImputationList(userId,selectAttribute1, 0, 0,0);
					if(subOrderList != null && subOrderList.size() > 0){
						HSSFCell cell2=row.createCell((short) colNum++);
						cell2.setCellValue(new HSSFRichTextString("查看")); //归集工单查看
						HSSFHyperlink link = cell2.getHyperlink();
						link = new HSSFHyperlink(HSSFHyperlink.LINK_DOCUMENT);
						//link.setAddress("'子工单'!A"+rowNum1);
						link.setAddress("'"+sheetName1+"'!A"+linkRowNum1);
						cell2.setHyperlink(link);
						
						HSSFCellStyle linkStyle = wb.createCellStyle();
						HSSFFont cellFont = wb.createFont();
						cellFont.setUnderline((byte) 1);
						cellFont.setColor(HSSFColor.BLUE.index);
						linkStyle.setFont(cellFont);
						//linkStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
						//linkStyle
							//	.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
						cell2.setCellStyle(linkStyle);
						
						
						mergedStartRowNum1 = rowNum1 ;
						for(TaskModel subModel:subOrderList){ //将归集工单写入sheet1
							int colNum1 = 0;
							HSSFRow row1 = sheet1.createRow(rowNum1);
							row1.createCell((short) colNum1++).setCellValue(new HSSFRichTextString(model0.getProcessInstanceId()));//主工单号
							row1.createCell((short) colNum1++).setCellValue(new HSSFRichTextString(subModel.getProcessInstanceId()));//子工单号
							row1.createCell((short) colNum1++).setCellValue(new HSSFRichTextString(subModel.getTheme()));//工单主题
							row1.createCell((short) colNum1++).setCellValue(new HSSFRichTextString(subModel.getBarrierNumber()));//业务号码
							row1.createCell((short) colNum1++).setCellValue(new HSSFRichTextString(format.format(subModel.getCreateTime())));//故障发生时间
							row1.createCell((short) colNum1++).setCellValue(new HSSFRichTextString(format.format(subModel.getSendTime())));//派单时间
							row1.createCell((short) colNum1++).setCellValue(new HSSFRichTextString(subModel.getWorkTask()));//工单历时
							row1.createCell((short) colNum1++).setCellValue(new HSSFRichTextString(subModel.getProcessLimit()));//处理时限(小时)
							row1.createCell((short) colNum1++).setCellValue(new HSSFRichTextString(subModel.getStatusName()));//当前状态
							row1.createCell((short) colNum1++).setCellValue(new HSSFRichTextString(subModel.getConnectPerson()));//联系人
							row1.createCell((short) colNum1++).setCellValue(new HSSFRichTextString(subModel.getInstallAddress()));//地址
							row1.createCell((short) colNum1++).setCellValue(new HSSFRichTextString(service.id2Name(subModel.getExecutor(),"tawSystemUserDao")));//当前处理人 tawSystemUserDao
							row1.createCell((short) colNum1++).setCellValue(new HSSFRichTextString(subModel.getStationName()));//局站名称
							row1.createCell((short) colNum1++).setCellValue(new HSSFRichTextString(service.id2Name(subModel.getDeptId(),"tawSystemDeptDao")));//所属区域 tawSystemDeptDao
							row1.createCell((short) colNum1++).setCellValue(new HSSFRichTextString(service.id2Name(subModel.getInitiator(),"tawSystemUserDao")));//代维公司 tawSystemUserDao
							rowNum1++;
							linkRowNum1++;
						}
						
						sheet1.addMergedRegion(new Region(mergedStartRowNum1,(short)0,(rowNum1-1),(short)0)); //合并主工单号单元格
					}else{
						row.createCell((short) colNum++).setCellValue(new HSSFRichTextString("未查询到子工单数据！")); 
					}
				}else{
					row.createCell((short) colNum++).setCellValue(new HSSFRichTextString("-")); 
				}
				//-------------查看子工单 结束--------------
				row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model0.getRecentMainScenesName())); //主场景
				row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model0.getRecentCopyScenesName())); //子场景
				//-------------查看场景模板 开始-------------- //场景模板
				//List<MaleSceneDetailModel> maleSceneDetailList = this.getMaleSceneDetail(model0.getProcessInstanceId(), "auditor", null); //导出施工队的场景模板
				List<MaleSceneDetailModel> maleSceneDetailList = this.getMaleSceneDetail(model0.getProcessInstanceId(),reportFlag,null); //导出施工队的场景模板
				if(maleSceneDetailList !=null && maleSceneDetailList.size() > 0){
					HSSFCell cell2=row.createCell((short) colNum++);
					cell2.setCellValue(new HSSFRichTextString("查看")); //归集工单查看
					HSSFHyperlink link = cell2.getHyperlink();
					link = new HSSFHyperlink(HSSFHyperlink.LINK_DOCUMENT);
					//link.setAddress("'子工单'!A"+rowNum1);
					link.setAddress("'"+sheetName2+"'!A"+linkRowNum2);
					cell2.setHyperlink(link);
					
					HSSFCellStyle linkStyle = wb.createCellStyle();
					HSSFFont cellFont = wb.createFont();
					cellFont.setUnderline((byte) 1);
					cellFont.setColor(HSSFColor.BLUE.index);
					linkStyle.setFont(cellFont);
					//linkStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
					//linkStyle
						//	.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
					cell2.setCellStyle(linkStyle);
					
					mergedStartRowNum2 = rowNum2 ;
					for(MaleSceneDetailModel subModel:maleSceneDetailList){ //将归集工单写入sheet1
						int colNum2 = 0;
						HSSFRow row1 = sheet2.createRow(rowNum2);
						row1.createCell((short) colNum2++).setCellValue(new HSSFRichTextString(model0.getProcessInstanceId()));//工单号
						row1.createCell((short) colNum2++).setCellValue(new HSSFRichTextString(subModel.getSceneName()));//主场景
						row1.createCell((short) colNum2++).setCellValue(new HSSFRichTextString(subModel.getCopyName()));//子场景
						row1.createCell((short) colNum2++).setCellValue(new HSSFRichTextString(subModel.getDispose()));//处理措施
						row1.createCell((short) colNum2++).setCellValue(new HSSFRichTextString(subModel.getTotalUnit()));//总单位
						row1.createCell((short) colNum2++).setCellValue(new HSSFRichTextString(subModel.getMaterialName()));//材料名
						row1.createCell((short) colNum2++).setCellValue(new HSSFRichTextString(subModel.getNum()));//数量
						row1.createCell((short) colNum2++).setCellValue(new HSSFRichTextString(subModel.getUnit()));//单位
						row1.createCell((short) colNum2++).setCellValue(new HSSFRichTextString(subModel.getPrice()));//单价
						row1.createCell((short) colNum2++).setCellValue(new HSSFRichTextString(subModel.getTotalAmount()));//总额
						row1.createCell((short) colNum2++).setCellValue(new HSSFRichTextString(subModel.getIsUtilize()));//是否利旧
						rowNum2++;
						linkRowNum2++;
					}
					sheet2.addMergedRegion(new Region(mergedStartRowNum2,(short)0,(rowNum2-1),(short)0)); //合并工单号
					sheet2.addMergedRegion(new Region(mergedStartRowNum2,(short)1,(rowNum2-1),(short)1)); //合并主场景
					sheet2.addMergedRegion(new Region(mergedStartRowNum2,(short)2,(rowNum2-1),(short)2)); //合并子场景
					sheet2.addMergedRegion(new Region(mergedStartRowNum2,(short)3,(rowNum2-1),(short)3)); //合并处理措施
					sheet2.addMergedRegion(new Region(mergedStartRowNum2,(short)4,(rowNum2-1),(short)4)); //合并单位
				}else{
					row.createCell((short) colNum++).setCellValue(new HSSFRichTextString("无")); //查看场景模板
				}
				//-------------查看场景模板 结束--------------
				row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(format.format(model0.getCreateTime()))); //公客系统申告时间
				row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(format.format(model0.getSendTime()))); //派单时间
				row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model0.getProcessLimit()));//处理时限(小时) 
				row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model0.getBarrierNumber())); //业务号码
				row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(service.id2Name(model0.getSubType(),"ItawSystemDictTypeDao")));//工单子类型 //ItawSystemDictTypeDao
				row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model0.getStationName())); //局站名称
				row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(service.id2Name(model0.getDeptId(),"tawSystemDeptDao")));//所属区域 //tawSystemDeptDao
				row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(service.id2Name(model0.getInitiator(),"tawSystemUserDao")));//代维公司 //tawSystemUserDao
				row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(service.id2Name(model0.getFaultSource(),"ItawSystemDictTypeDao")));//故障原因 //tawSystemUserDao
				row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(format.format(model0.getCreateTime()))); //故障发生时间
				row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(format.format(model0.getLastReplyTime())));// 故障处理回复时间
				row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model0.getWorkTask())); //工单历时
				row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model0.getStatusName())); //当前状态
				row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model0.getConnectPerson())); //联系人
				row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model0.getInstallAddress())); //地址
				row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(service.id2Name(model0.getExecutor(),"tawSystemUserDao")));//当前处理人 //tawSystemUserDao
				rowNum0++;
			}
		}else{
			HSSFRow row0_1 = sheet0.createRow(1);
			HSSFCell cell = row0_1.createCell((short) 0);
			cell.setCellValue(new HSSFRichTextString("没有要导出的数据！")); 
		}
		return wb;
	}
	
	//获取材料的统计信息
//	public MaleSceneStatisticsModel getMaleSceneStatistics(List<TaskModel> list,String reportFlag,Map<String,String> param){
//		MaleSceneStatisticsModel model = new MaleSceneStatisticsModel("0","0","0","0");
//		
//		double totalAmount = 0D ; //总金额
//		double poleNum = 0D ; //电杆数
//		double jointBoxNum = 0D ; //接头盒数
//		double opticalCableLength = 0D ; //光缆长度
//		
//		if(list != null && list.size() > 0 ){
//			int n = 500; //设置500条取后台取一次数据
//			int size = list.size();
//			int k = size%n==0?size/n:size/n+1;
//			//System.out.println("----k="+k);
//			for(int i=1;i<=k;i++){
//				
//				int s = (i-1)*n;
//				int e =i*n;
//				if(i==k){
//					e = size;
//				}
//				 
//				String ids = ""; //流程号
//				for(int j=s;j<e;j++){
//					if(!"2".equals(list.get(j).getApproveFlag())){
//						ids+="'"+list.get(j).getProcessInstanceId()+"',";
//					}
//				}
//				ids = ids.substring(0,ids.length()-1); //截取掉最后一位字符
//				MaleSceneStatisticsModel maleSceneStatistics = pnrTransferOfficeDaoJDBC.getMaleSceneStatistics(ids,reportFlag, "", null);
//				totalAmount += Double.parseDouble(maleSceneStatistics.getTotalAmount());
//				poleNum += Double.parseDouble(maleSceneStatistics.getPoleNum());
//				jointBoxNum += Double.parseDouble(maleSceneStatistics.getJointBoxNum());
//				opticalCableLength += Double.parseDouble(maleSceneStatistics.getOpticalCableLength());
//			}
//			
//			model.setTotalAmount(Double.toString(CommonUtils.reservedDecimalPlaces(totalAmount, 2)));
//			model.setPoleNum(Double.toString(CommonUtils.reservedDecimalPlaces(poleNum, 2)));
//			model.setJointBoxNum(Double.toString(CommonUtils.reservedDecimalPlaces(jointBoxNum, 2)));
//			model.setOpticalCableLength(Double.toString(CommonUtils.reservedDecimalPlaces(opticalCableLength, 2)));
//		}
//		return model;
//	}
	
	//获取材料的统计信息
	public MaleSceneStatisticsModel getMaleSceneStatistics(String userId,MaleGuestSelectAttribute selectAttribute,String reportFlag,Map<String,String> param){
		List<TaskModel> list = null;
		if("acheck".equals(reportFlag)){ //一次核验
			list = this.getFirstVerifyList(userId,selectAttribute,0,1,20000);
		}else if("twoSpotChecks".equals(reportFlag)){ //二次抽检
			list = this.getSecondInspectionList(userId,selectAttribute,0,1,20000);
		}else if("auditReport".equals(reportFlag)){ //周期领用
			list = this.getCycleCollarReportListByStartDateAndEndDate(userId,selectAttribute,0,1,20000);
		}
			
		MaleSceneStatisticsModel model = new MaleSceneStatisticsModel("0","0","0","0");
			
			double totalAmount = 0D ; //总金额
			double poleNum = 0D ; //电杆数
			double jointBoxNum = 0D ; //接头盒数
			double opticalCableLength = 0D ; //光缆长度
			
			if(list != null && list.size() > 0 ){
				int n = 500; //设置500条取后台取一次数据
				int size = list.size();
				int k = size%n==0?size/n:size/n+1;
				//System.out.println("----k="+k);
				for(int i=1;i<=k;i++){
					
					int s = (i-1)*n;
					int e =i*n;
					if(i==k){
						e = size;
					}
					 
					String ids = ""; //流程号
					for(int j=s;j<e;j++){
						if(!"2".equals(list.get(j).getApproveFlag())){
							ids+="'"+list.get(j).getProcessInstanceId()+"',";
						}
					}
					if(!"".equals(ids)){
						ids = ids.substring(0,ids.length()-1); //截取掉最后一位字符
						MaleSceneStatisticsModel maleSceneStatistics = pnrTransferOfficeDaoJDBC.getMaleSceneStatistics(ids,reportFlag, "", null);
						totalAmount += Double.parseDouble(maleSceneStatistics.getTotalAmount());
						poleNum += Double.parseDouble(maleSceneStatistics.getPoleNum());
						jointBoxNum += Double.parseDouble(maleSceneStatistics.getJointBoxNum());
						opticalCableLength += Double.parseDouble(maleSceneStatistics.getOpticalCableLength());
					}
				}
				
				DecimalFormat df = new DecimalFormat("###") ; //取整
				DecimalFormat df2 = new DecimalFormat("###.##") ; //保留两位小数
				
				model.setTotalAmount(df2.format(totalAmount));
				model.setPoleNum(df.format(poleNum));
				model.setJointBoxNum(df.format(jointBoxNum));
				model.setOpticalCableLength(df2.format(opticalCableLength));
			}
			return model;
	}
	
	//取公客归集工单子工单的最小时间（最小告警发生时间或者最小派单时间）
	public Date getMinDateForSubWorkOrder(String mainProcessInstanceId,List sublist,String flag,Map<String,String> param){
		return pnrTransferOfficeDaoJDBC.getMinDateForSubWorkOrder(mainProcessInstanceId,sublist,flag,param);
	}
	
	//生成周期领用表的材料数量统计
	public MaterialQuantityModel getMaterialQuantityOfCycleReport(String userId,MaleGuestSelectAttribute selectAttribute,Map<String,String> param){
		return pnrTransferOfficeDaoJDBC.getMaterialQuantityOfCycleReport(userId,selectAttribute,param);
	}
	
	//导出周期领用表的材料数量汇总
	public HSSFWorkbook exportMaterialQuantity(String userId,MaleGuestSelectAttribute selectAttribute,String reportFlag,Map<String,String> param){
		HSSFWorkbook wb = new HSSFWorkbook();
		String sheetName = "材料数量";
		HSSFSheet sheet = wb.createSheet(sheetName);
		
		//带背景色的样式
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 填充单元格
		cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		
		//不带背景色的样式
		HSSFCellStyle cellStyle2 = wb.createCellStyle();
		cellStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
		cellStyle2.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
		cellStyle2.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
		cellStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		
		
		//第一行显示材料金额
		HSSFRow row0 = sheet.createRow(0);
		HSSFCell cell0_0 = row0.createCell((short) 0);
		cell0_0.setCellValue(new HSSFRichTextString("材料金额"));
		cell0_0.setCellStyle(cellStyle);
		
		//取材料总金额
		MaleSceneStatisticsModel maleSceneStatisticsModel = this.getMaleSceneStatistics(userId, selectAttribute,reportFlag, null);
		String totalAmount = maleSceneStatisticsModel.getTotalAmount();
		
		//表示合并B1,L1  
        sheet.addMergedRegion(new Region(0,(short)1,0,(short)10));
        HSSFCell cell0_1 = row0.createCell((short) 1);
        cell0_1.setCellValue(new HSSFRichTextString(totalAmount+"元")); 
        cell0_1.setCellStyle(cellStyle2);
        
        //获取材料数量统计
		MaterialQuantityModel materialQuantityModel = this.getMaterialQuantityOfCycleReport(userId, selectAttribute, null);
		Class cla = materialQuantityModel.getClass();
		
        String materialType = "材料类型";
        String materialNum = "数量";
        
        //第二行 第三行
        String[] header1 = {"光缆接头盒_双端 12芯 4孔 架空","光缆接头盒_双端 24芯 4孔 架空","光缆接头盒_双端 48芯 4孔 架空","光缆接头盒_双端 96芯 4孔 架空","光缆接头盒_双端 144芯 4孔 架空"
				,"光缆_GYTA-4B1.3","光缆_GYTA-6B1.3","光缆_GYTA-8B1.3","光缆_GYTA-12B1.3","光缆_GYTA-24B1.3"};
        String[] field1 = {"PRSOCR","QZHQFL","SJPFQY","SGWVOU","OUBYVD","TJHREL","XOFSKA","ZQIKEA","CKBRQI","OJRJES"};
        this.createMaterialQuantityRow(wb,sheet, 1, materialType, header1, "header", materialQuantityModel,cellStyle,cellStyle2);
        this.createMaterialQuantityRow(wb,sheet, 2, materialNum, field1, "field", materialQuantityModel,cellStyle,cellStyle2);
        //第四行 第五行
        String[] header2 = {"光缆_GYTA-36B1.3","光缆_GYTA-48B1.3","光缆_GYTA-72B1.3","光缆_GYTA-96B1.3","光缆_GYTA-144B1.3","光缆_GYDTA-144B1.3"
        		,"光缆预留架_4*40*1000","二线担","担夹","U型穿钉"};
        String[] field2 = {"EELTNY","UJIPIJ","WRAYQU","ZUQXME","DDDTIA","VXNTLR","KUARAM","KZBBPH","YQHWJT","CODHHR"};
        this.createMaterialQuantityRow(wb,sheet, 3, materialType, header2, "header", materialQuantityModel,cellStyle,cellStyle2);
        this.createMaterialQuantityRow(wb,sheet, 4, materialNum, field2, "field", materialQuantityModel,cellStyle,cellStyle2);
        //第六行 第七行
        String[] header3 = {"塑料保护管","8米水泥杆","10米水泥杆","12米水泥杆","15米水泥杆","8米木杆"
        		,"10米木杆","电缆挂钩 35#","电力线保护管","地线保护管"};
        String[] field3 = {"XHHOMV","BHMAQR","HKTSZC","MGRGAQ","RSFUIX","NIKHNJ","FLGOCG","YKYXZH","QOQWIE","AMWAGC"};
        this.createMaterialQuantityRow(wb,sheet, 5, materialType, header3, "header", materialQuantityModel,cellStyle,cellStyle2);
        this.createMaterialQuantityRow(wb,sheet, 6, materialNum, field3, "field", materialQuantityModel,cellStyle,cellStyle2);
        //第八行 第九行
        String[] header4 = {"7/2.6钢绞线","7/2.2钢绞线","PVC套管","光分纤箱","光分器","法兰盘"
        		,"3.0铁丝","1.5铁丝","单槽夹板","16x60mm镀锌穿钉"};
        String[] field4 = {"IKQPEA","XLBSTE","BWZWKK","JCJWWQ","MTJMJB","GHPVVH","EYIBDZ","NTBFQU","WWWDQY","WCDLYM"};
        this.createMaterialQuantityRow(wb,sheet, 7, materialType, header4, "header", materialQuantityModel,cellStyle,cellStyle2);
        this.createMaterialQuantityRow(wb,sheet, 8, materialNum, field4, "field", materialQuantityModel,cellStyle,cellStyle2);
        
        //第十行 第十一行
        String[] header5 = {"16x80mm镀锌螺丝","地锚铁柄","地锚石","单槽夹板、16x60mm镀锌穿钉","二线担、担夹、U型穿钉","电缆接线子","电缆","开启式套管1#","开启式套管2#","开启式套管3#"};
        String[] field5 = {"QAMLSA","SZZVRS","CYYGII","BAVWBY","OMDOPG","UMFWWU","IHOJOB","KPUDEF","ADLKKF","XKXXEV"};
        this.createMaterialQuantityRow(wb,sheet, 9, materialType, header5, "header", materialQuantityModel,cellStyle,cellStyle2);
        this.createMaterialQuantityRow(wb,sheet, 10, materialNum, field5, "field", materialQuantityModel,cellStyle,cellStyle2);
        
        //第十二行 第十三行
        String[] header6 = {"开启式套管4#","开启式套管5#","电缆模块","电缆分线盒5对","电缆分线盒10对","电缆分线盒20对","电缆分线盒30对","电缆分线盒50对","架空跨路警示牌","拉线保护管"};
        String[] field6 = {"AUIKES","MXSMPE","MXFNIJ","ZYSQDU","FSWCUA","QJBWTS","UWMMPT","KMARYS","KYZSUF","LQIMRR"};
        this.createMaterialQuantityRow(wb,sheet, 11, materialType, header6, "header", materialQuantityModel,cellStyle,cellStyle2);
        this.createMaterialQuantityRow(wb,sheet, 12, materialNum, field6, "field", materialQuantityModel,cellStyle,cellStyle2);
        
        //第十四行 第十五行
        String[] header7 = {"拉线抱箍","拉线衬环","拉线衬环、16x80mm镀锌螺丝","热缩管","人井井盖","熔纤盘","室外电话皮线","松香蜡","跳纤","尾纤"};
        String[] field7 = {"QMAOFE","BTQRFA","TZNPVL","FFRYTI","KZWUXF","JDTDOZ","AHWXID","VBMPPD","UJNYVN","NJCKFK"};
        this.createMaterialQuantityRow(wb,sheet, 13, materialType, header7, "header", materialQuantityModel,cellStyle,cellStyle2);
        this.createMaterialQuantityRow(wb,sheet, 14, materialNum, field7, "field", materialQuantityModel,cellStyle,cellStyle2);
        
        //第十六行 第十七行
        String[] header8 = {"小三角旗（市管物资）"};
        String[] field8 = {"PWXMMA"};
        this.createMaterialQuantityRow(wb,sheet, 15, materialType, header8, "header", materialQuantityModel,cellStyle,cellStyle2);
        this.createMaterialQuantityRow(wb,sheet, 16, materialNum, field8, "field", materialQuantityModel,cellStyle,cellStyle2);
        
		return wb;
	}
	
	private void createMaterialQuantityRow(HSSFWorkbook wb,HSSFSheet sheet,int rowNum,String text,String[] s,String sFlag,MaterialQuantityModel materialQuantityModel,HSSFCellStyle cellStyle,HSSFCellStyle cellStyle2){
		
		  HSSFRow row = sheet.createRow(rowNum);
	      HSSFCell cell = row.createCell((short) 0);
	      cell.setCellValue(new HSSFRichTextString(text));
	      cell.setCellStyle(cellStyle);
	      if("field".equals(sFlag)){
	    	Class cla = materialQuantityModel.getClass();
	  	    for(int i=1;i <= s.length;i++){
		    	Method getMethod;
				HSSFCell c = row.createCell((short) i);
				try {
					getMethod = cla.getMethod("get"+s[i-1], new Class[] {});
					Object value = getMethod.invoke(materialQuantityModel, new Object[] {}); //这个对象字段get方法的值
					c.setCellValue(new HSSFRichTextString(value.toString())); 
					c.setCellStyle(cellStyle2);
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
	      }else{
	    	  for(int i=1;i <= s.length;i++){
					HSSFCell c = row.createCell((short) i);
					c.setCellValue(new HSSFRichTextString(s[i-1])); 
					c.setCellStyle(cellStyle);
			  }   
	      }	
	}
	
	//抢修工单 工单查询条数
	public int getWorkOrderQueryOfTransferOfficeListCount(String areaid,String userid, String flag,String processKey,ConditionQueryModel conditionQueryModel){
		return pnrTransferOfficeDaoJDBC.getWorkOrderQueryOfTransferOfficeListCount(areaid,userid,flag,processKey,conditionQueryModel);
	}
	
	//抢修工单 工单结果集
	public List<TaskModel> getWorkOrderQueryOfTransferOfficeList(String areaid,String userid,String flag, String processKey,ConditionQueryModel conditionQueryModel, 
			int firstResult,int endResult, int pageSize){
		return pnrTransferOfficeDaoJDBC.getWorkOrderQueryOfTransferOfficeList(areaid,userid,flag,processKey,conditionQueryModel,firstResult,endResult,pageSize);
	}
	
	//查询 周期领用表提交审批后没有归档的工单 统计数
	public int manualBatchArchiveForCycleReportCount(String userId,MaleGuestSelectAttribute selectAttribute){
		return pnrTransferOfficeDaoJDBC.manualBatchArchiveForCycleReportCount(userId,selectAttribute);
	}
	
	//查询 周期领用表提交审批后没有归档的工单 列表
	public List<TaskModel> manualBatchArchiveForCycleReportList(String userId,MaleGuestSelectAttribute selectAttribute,int firstResult,int endResult,int pageSize){
		return pnrTransferOfficeDaoJDBC.manualBatchArchiveForCycleReportList(userId,selectAttribute,firstResult,endResult,pageSize);
	}

	@Override
	public void maleGuestHandleInterfaceCall(TransferMaleGuestReturn maleGuestReturn,String maleGuestState,String processInstanceId ,String themeId) {

		//判断是否是接口派单,是就进行接口通知
		String maleGuestNumber =  getMaleGuestNumberByThemeId(themeId);
		if(maleGuestNumber!=null && !"".equals(maleGuestNumber=maleGuestNumber.trim())){
			// 调用方法,查询接口需要的数据
//			TransferMaleGuestReturn maleGuestReturn =getMaleGuestReturnData(maleGuestNumber);
			sendMsgToMaleGuestHandleInterface(maleGuestReturn);
			//有子工单，把子工单调用公客系统接口。
			if("1".equals(maleGuestState)){
				 List<PnrTransferOffice> plist =	getListByParentProcessInstanceId(processInstanceId);
				 for(PnrTransferOffice p :plist){
					 String maleGuestNumberSub = p.getMaleGuestNumber();
					
					 if(!maleGuestNumber.equals(maleGuestNumberSub)){
						 TransferMaleGuestReturn maleGuestReturnsub = new TransferMaleGuestReturn();
						 maleGuestReturnsub.setConfCRMTicketNo(maleGuestNumberSub);	
							// 回单标示
						 maleGuestReturnsub.setFlag(maleGuestReturn.getFlag());	
							// 回单时间
						 maleGuestReturnsub.setBack_dt(maleGuestReturn.getBack_dt());
							// 处理人姓名id
						 maleGuestReturnsub.setBack_userid(maleGuestReturn.getBack_userid());
							// 处理人姓名
						 maleGuestReturnsub.setBack_username(maleGuestReturn.getBack_username());
							// 故障原因id
						 maleGuestReturnsub.setReason_id(maleGuestReturn.getReason_id());
							// 描述
						 maleGuestReturnsub.setBack_info(maleGuestReturn.getBack_info());		
							// 故障原因
						 maleGuestReturnsub.setReason_name(maleGuestReturn.getReason_name());
							
						 sendMsgToMaleGuestHandleInterface(maleGuestReturnsub);
					 }
					 
				 }
			}
			
		
		}
	}
	private void sendMsgToMaleGuestHandleInterface(TransferMaleGuestReturn maleGuestReturn){
		Thread aThread = new Thread(new MaleGuestReturnThead(sFormat
				.format(new Date()),
				CommonUtils.GK_MALE_GUEST_RECEIPT_METHOD, maleGuestReturn));
		aThread.start();
	}
	
	private List<PnrTransferOffice> getListByParentProcessInstanceId(String parentProcessInstanceId){
		Search search = new Search();		
		search.addFilterEqual("parentProcessInstanceId", parentProcessInstanceId);
		search.addFilterNotEqual("maleGuestState", "3");
		List<PnrTransferOffice> pnrTicketList = this.search(search);
		return pnrTicketList;
	}
	
	/**
	 * 	 周期领用表报表编号是否存在
	 	 * @author WANGJUN
	 	 * @title: existsCycleReportNumber
	 	 * @date Jun 30, 2016 10:35:48 AM
	 	 * @param reportNumber
	 	 * @return boolean
	 */
	public boolean existsCycleReportNumber(String reportNumber){
		boolean result = false;
		if(reportNumber != null && !"".equals(reportNumber)){
			int total = pnrTransferOfficeDaoJDBC.getCycleReportCountByReportNumber(reportNumber.trim());
			if(total > 0){
				result = true;
			}
		}
		return result;
	}
	
	/**
	 *   通过报表编号查询验证表里是否有数据
	 	 * @author WANGJUN
	 	 * @title: isVerifiedCycleReportNumber
	 	 * @date Jun 30, 2016 11:44:26 AM
	 	 * @param reportNumber
	 	 * @return boolean true已验证;false未验证
	 */	
	public boolean isVerifiedCycleReportNumber(String reportNumber){
		boolean result = false;
		int total = pnrTransferOfficeDaoJDBC.getVerifiedCountByReportNumber(reportNumber.trim());
		if(total > 0){
			result = true;
		}
		return result;
	}
	
	/**
	 *   插入验证信息
	 	 * @author WANGJUN
	 	 * @title: insertVerifiedNumber
	 	 * @date Jun 30, 2016 1:40:37 PM
	 	 * @param reportNumber
	 	 * @param type void
	 */
	public boolean insertVerifiedNumber(String reportNumber,String type){
		return pnrTransferOfficeDaoJDBC.insertVerifiedNumber(reportNumber, type);
	}
	
	/**
	 * 	 用周期领用表报表编号查询对应的开始时间、结束时间、区县
	 	 * @author WANGJUN
	 	 * @title: getTimeCountyByReportNum
	 	 * @date Jun 30, 2016 3:26:12 PM
	 	 * @param reportNum
	 	 * @return Map<String,String>
	 */
	public Map<String,String> getTimeCountyByReportNum(String reportNum){
		return pnrTransferOfficeDaoJDBC.getTimeCountyByReportNum(reportNum);
	}
	
	/**
	 * 	 用报表编号取提交时间
	 	 * @author WANGJUN
	 	 * @title: getSubmitDateByReportNum
	 	 * @date Jul 6, 2016 10:30:23 AM
	 	 * @param reportNum
	 	 * @return Date
	 */
	public Date getSubmitDateByReportNum(String reportNum){
		return pnrTransferOfficeDaoJDBC. getSubmitDateByReportNum(reportNum);
	}
	

	/**
	 * 	 保存抽检结果
	 	 * @author WANGJUN
	 	 * @title: saveSamplingJudgments
	 	 * @date Aug 5, 2016 11:35:39 AM
	 	 * @param param
	 	 * @return String
	 */
	public String saveSamplingJudgments(ParameterModel param){
		String msg = "";
		String processInstanceId = param.getProcessInstanceId();
		String samplingJudgments = param.getSamplingJudgments();
		if(!"".equals(processInstanceId)){
			Search search = new Search();
		    search.addFilterEqual("processInstanceId", processInstanceId);
		    PnrTransferOffice p = this.searchUnique(search);  
		    if(p != null){
		    		try{
		    			p.setSamplingState("2"); //已抽检
						p.setSamplingJudgments(samplingJudgments);
						p.setSamplingDate(new Date());
						p.setSamplingUserId(param.getUserId());
					    this.save(p);
		    		}catch(Exception e){
		    			msg = "保存失败!";
		    		}
		    }else{
		    	msg = "未找到工单信息!";
		    }
		}else{
			msg = "工单号不能为空!";
		}
		return msg;
	}
	
	/**
	 *   拼接省公司抽检的查询条件
	 	 * @author WANGJUN
	 	 * @title: joinSamplingListCondition
	 	 * @date Aug 5, 2016 3:33:25 PM
	 	 * @param pageIndexString
	 	 * @param conditionQueryModel
	 	 * @return String
	 */
	public String joinSamplingListCondition(String pageIndexString,ConditionQueryModel conditionQueryModel){
		String msg = "";
		if(Strings.isNullOrEmpty(pageIndexString)){
			pageIndexString = "1";
		}
		msg += "indexStr,"+pageIndexString+";";
		if(!"".equals(conditionQueryModel.getSendStartTime())){
			msg += "sheetAcceptLimit,"+conditionQueryModel.getSendStartTime()+";";
		}
		if(!"".equals(conditionQueryModel.getSendEndTime())){
			msg += "sheetCompleteLimit,"+conditionQueryModel.getSendEndTime()+";";
		}
		if(!"".equals(conditionQueryModel.getCity())){
			msg += "region,"+conditionQueryModel.getCity();
		}
		return msg;
	}
	
	/**
	 *   解析省公司抽检的查询条件
	 	 * @author WANGJUN
	 	 * @title: joinSamplingListCondition
	 	 * @date Aug 5, 2016 3:33:25 PM
	 	 * @param pageIndexString
	 	 * @param conditionQueryModel
	 	 * @return String
	 */
	public String analysisSamplingListCondition(String condition){
		String msg = "";
		if(!"".equals(condition)){
			String[] split = condition.split(";");
			for(int i=0;i < split.length;i++){
				String[] split2 = split[i].split(",");
				msg+="&"+split2[0]+"="+split2[1];
			}
		}
		return msg;
	}
	

	@Override
	public int getAndroidWorkOrderCount(String sampling_userid) {
		return pnrTransferOfficeDaoJDBC.getAndroidWorkOrderCount(sampling_userid);
	}

	@Override
	public List<AndroidWorkOrderTask> getAndroidWorkOrderList(String sampling_userid, int pageIndex, int pageSize) {
		return pnrTransferOfficeDaoJDBC.getAndroidWorkOrderList(sampling_userid, pageIndex, pageSize);
	}
	
	@Override
	public int updateWorkOrderOpinion(String process_instance_id,String opinion) {
		return pnrTransferOfficeDaoJDBC.updateWorkOrderOpinion(process_instance_id, opinion);
	}
	
	/**
	 *   公客工单查询 条数 20161222
	 	 * @author WangJun
	 	 * @title: getMaleGuestWorkOrderQueryCount
	 	 * @date Dec 22, 2016 3:34:28 PM
	 	 * @param areaid
	 	 * @param userid
	 	 * @param conditionQueryModel
	 	 * @return int
	 */
	public int getMaleGuestWorkOrderQueryCount(String areaid,String userid,ConditionQueryModel conditionQueryModel){
		return pnrTransferOfficeDaoJDBC.getMaleGuestWorkOrderQueryCount(areaid,userid,conditionQueryModel);
	}
	
	/**
	 *   公客工单查询 列表 20161222
	 	 * @author WangJun
	 	 * @title: getMaleGuestWorkOrderQueryList
	 	 * @date Dec 22, 2016 3:35:58 PM
	 	 * @param areaid
	 	 * @param userid
	 	 * @param conditionQueryModel
	 	 * @param firstResult
	 	 * @param endResult
	 	 * @param pageSize
	 	 * @return List<TaskModel>
	 */
	public List<TaskModel> getMaleGuestWorkOrderQueryList(String areaid,String userid,ConditionQueryModel conditionQueryModel, int firstResult,int endResult, int pageSize){
		return pnrTransferOfficeDaoJDBC.getMaleGuestWorkOrderQueryList(areaid,userid,conditionQueryModel,firstResult,endResult,pageSize);
	}

}
