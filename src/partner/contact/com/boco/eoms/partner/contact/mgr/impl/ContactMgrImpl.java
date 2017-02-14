package com.boco.eoms.partner.contact.mgr.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcService;
import com.boco.eoms.message.service.MsgService;
import com.boco.eoms.message.service.impl.MsgServiceImpl;
import com.boco.eoms.partner.contact.mgr.ContactMainMgr;
import com.boco.eoms.partner.contact.mgr.ContactMgr;
import com.boco.eoms.partner.contact.mgr.ContactTaskMgr;
import com.boco.eoms.partner.contact.model.ContactMain;
import com.boco.eoms.partner.contact.model.ContactTask;
import com.boco.eoms.partner.contact.util.MyUtil;
import com.boco.eoms.partner.contact.util.Type;
import com.googlecode.genericdao.search.Search;
/**
 * <p>
 * Title:联系函 基本信息
 * </p>
 * <p>
 * Description:联系函 基本信息
 * </p>
 * <p>
 * Aug 24, 2012 10:53:19 AM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public class ContactMgrImpl  implements ContactMgr,Job{

	private ContactMainMgr contactMainMgr;
	private ContactTaskMgr contactTaskMgr;
    
	private Search search ;

 /**
  * 保存草稿
  * @param cMain
  * @param cTask
  */
  public void saveDrafts(ContactMain cMain){
//	  if(MyUtil.isEmpty(cMain.getId())){
//		  cMain.setType(0);//联系函转移状态
//		  contactMainMgr.save(cMain);
//	  }
//	  else{
//		  //修改草稿  -- 先删除已有的task
//			search = new Search();
//			search.addFilterEqual("mainId", cMain.getId());
//			List<ContactTask> taskList = contactTaskMgr.searchAndCount(search).getResult();
//			for (ContactTask contactTask : taskList) {
//				contactTaskMgr.remove(contactTask);
//			}
//	  }
//		  
	  
//	  for (ContactTask contactTask : cTask) {
//		  contactTask.setMainId(cMain.getId());
//		  contactTask.setTaskState(Type.NOSTART); //所有 任务 未开始 
//		  contactTaskMgr.save(contactTask);
//	}
	  contactMainMgr.save(cMain);
	  
 }
	/**
	 * 联系函 送审
	 */
	
  public void toAudit(ContactMain cMain, String linkId) {
//		if(MyUtil.isEmpty(cMain.getId())){
//			//新增页面 直接送审
//			this.saveDrafts(cMain);
//		}
		//送审数据处理
		//1.taks表：送审操作时候，根据审批人建立task数据
			ContactTask auditTask = new ContactTask();
			auditTask.setPreLinkId(linkId);
			auditTask.setTaskState(Type.RUNNING);
			auditTask.setTaskType(Type.AUDITING);
			auditTask.setTaskName("approver");
			auditTask.setTaskOwnerType(3);
			auditTask.setTaskOwnerId(StaticMethod.nullObject2String(cMain.getApprover()));
			auditTask.setTaskOwnerName(StaticMethod.nullObject2String(cMain.getapproverName()));
			auditTask.setMainId(StaticMethod.nullObject2String(cMain.getId()));
			auditTask.setTaskState(1);
			 if(MyUtil.isNum(auditTask.getTaskOwnerId())){//阅知部门2
				 auditTask.setTaskType(2);
				 auditTask.setTaskOwnerType(2);
			 }
			auditTask.setOperationTime(StaticMethod.getTimestamp());
			 if(cMain.getIsSendSMS()==1){//如果业务联系需要发送短信  发送短信给处理人员
			    // MyUtil.sendMessage(cMain, StaticMethod.nullObject2String(cMain.getApprover()));
				 MsgService msgService = new MsgServiceImpl();
				 //即时提醒
				 String instantServiceId=MyUtil.getServiceId(MyUtil.DEFAULT_workProcessName, MyUtil.SMS_SERVICE_INSTANT);
				 
				 String content =  "你好，你有编号:"+StaticMethod.nullObject2String(cMain.getCode())+"的业务涵要审批!";
				 String buzId=StaticMethod.nullObject2String(cMain.getId());
				 String receivers="1,"+StaticMethod.nullObject2String(cMain.getApprover());
				 Date currentTime = new Date();
		    	 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    	 String sendTime = formatter.format(currentTime);				
				 msgService.sendMsg(instantServiceId, content,  buzId, receivers, sendTime);
			 }
			contactTaskMgr.save(auditTask);
			
			//2.main表：修改信息为 审批中。
			cMain.setType(Type.AUDITING);				
			contactMainMgr.save(cMain);			
	}
  
  /**
   * 审批
   */
  
	public void audit(String taskId,String linkId, String auditContent,	String auditResult) {
		ContactTask auditTask = contactTaskMgr.find(taskId);
		ContactMain cMain = contactMainMgr.find(auditTask.getMainId());
		search = new Search();
		search.addFilterEqual("mainId", auditTask.getMainId());
		auditTask.setOperationContent(auditContent);
		auditTask.setOperationTime(StaticMethod.getTimestamp());
		auditTask.setTaskState(Type.END);
		contactTaskMgr.save(auditTask);
		
		MsgService msgService = new MsgServiceImpl();
		//即时提醒
		String instantServiceId=MyUtil.getServiceId(MyUtil.DEFAULT_workProcessName, MyUtil.SMS_SERVICE_INSTANT);
		String buzId=StaticMethod.nullObject2String(cMain.getId());
		Date currentTime = new Date();
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String sendTime = formatter.format(currentTime);				
		
		List<ContactTask> auditTaskList = new ArrayList<ContactTask>();
		String taskOwnerIds = cMain.getPublishedRange();
		String taskOwnerNames = cMain.getPublishedRangeName();
		int type = -10;
		if("1".equals(auditResult)){//审批通过
		//发布后，将通知发布范围的对象
			type = Type.PUBLISHING;
			List list = MyUtil.getTaskFromStr(cMain,taskOwnerIds, taskOwnerNames);
			auditTaskList.addAll(list);			
		}else{
			type = Type.REJECT;
			//给驳回添加短信提醒
			if(StaticMethod.nullObject2String(cMain.getIsSendSMS()).equals("1")){
				//	MyUtil.sendMessage(cMain, StaticMethod.nullObject2String(cMain.getPublisherId()));
				String content =  "你好，你有编号:"+StaticMethod.nullObject2String(cMain.getCode())+"的业务涵审批时被驳回!";
				String receivers="1,"+StaticMethod.nullObject2String(cMain.getApprover());
				msgService.sendMsg(instantServiceId, content,  buzId, receivers, sendTime);
			}
		}
		
		for (ContactTask contactTask : auditTaskList) {	
				contactTask.setMainId(cMain.getId());
				contactTask.setPreLinkId(linkId);
				contactTask.setTaskState(Type.RUNNING);
				contactTask.setTaskName("publishing");	 
		}
		ContactTask[] auditTaskArr=new ContactTask[auditTaskList.size()];
		auditTaskList.toArray(auditTaskArr);
		contactTaskMgr.save(auditTaskArr);
		
		//发布 或 转发，短息通知处理者（有部门和个人）处理
		String publishedRange=cMain.getPublishedRange();
		String[] publishedArr=publishedRange.split(",");
		
		//2 .  保存main表		
		cMain.setType(type);
		contactMainMgr.save(cMain);	
		//发布 或 转发，短息通知处理者（有部门和个人）处理
		if(StaticMethod.nullObject2String(cMain.getIsSendSMS()).equals("1")){
			String content =  "你好，你有编号:"+StaticMethod.nullObject2String(cMain.getCode())+"的业务涵需要处理!";
			String receivers="";
			//发送短信给的对象，可能有三种：人员、部门、角色，以下判断对象的类型. 此处现只有2种：人员、部门
			ID2NameService service = (ID2NameService) ApplicationContextHolder.getInstance().getBean("ID2NameGetServiceCatch");
			for(String published:publishedArr ){
				String name="";
				if(!"".equals((name=service.id2Name(published,"tawSystemUserDao")))){//用户
					receivers=receivers+"1,"+published+"#";
				}else{//部门
					receivers=receivers+"2,"+published+"#";
				}
			}
			receivers=receivers.substring(0, receivers.length());
			msgService.sendMsg(instantServiceId, content,  buzId, receivers, sendTime);
		}
	}
	  /**
	   * 直接发布
	   */
	  
	public void publish(ContactMain cMain, String linkId) {
			List<ContactTask> auditTaskList = new ArrayList<ContactTask>();
			String taskOwnerIds = cMain.getPublishedRange();
			String taskOwnerNames = cMain.getPublishedRangeName();
			List list = MyUtil.getTaskFromStr(cMain,taskOwnerIds, taskOwnerNames);
			auditTaskList.addAll(list);
			//发布后，将通知发布范围的对象
			for (ContactTask contactTask : auditTaskList) {
					contactTask.setMainId(cMain.getId());
					contactTask.setPreLinkId(linkId);
					contactTask.setTaskState(Type.RUNNING);
					contactTask.setTaskName("publishing");	 				
			}
			ContactTask[] auditTaskArr=new ContactTask[auditTaskList.size()];
			auditTaskList.toArray(auditTaskArr);
			contactTaskMgr.save(auditTaskArr);
			
			MsgService msgService = new MsgServiceImpl();
			//即时提醒
			String instantServiceId=MyUtil.getServiceId(MyUtil.DEFAULT_workProcessName, MyUtil.SMS_SERVICE_INSTANT);
			String buzId=StaticMethod.nullObject2String(cMain.getId());
			Date currentTime = new Date();
	    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	String sendTime = formatter.format(currentTime);					
			//发布 或 转发，短息通知处理者（有部门和个人）处理
			String publishedRange=cMain.getPublishedRange();
			String[] publishedArr=publishedRange.split(",");
			//发布 或 转发，短息通知处理者（有部门和个人）处理
			if(StaticMethod.nullObject2String(cMain.getIsSendSMS()).equals("1")){
				String content =  "你好，你有编号:"+StaticMethod.nullObject2String(cMain.getCode())+"的业务涵需要处理!";
				String receivers="";
				//发送短信给的对象，可能有三种：人员、部门、角色，以下判断对象的类型. 此处现只有2种：人员、部门
				ID2NameService service = (ID2NameService) ApplicationContextHolder.getInstance().getBean("ID2NameGetServiceCatch");
				for(String published:publishedArr ){
					String name="";
					if(!"".equals((name=service.id2Name(published,"tawSystemUserDao")))){//用户
						receivers=receivers+"1,"+published+"#";
					}else{//部门
						receivers=receivers+"2,"+published+"#";
					}
				}
				receivers=receivers.substring(0, receivers.length());
				msgService.sendMsg(instantServiceId, content,  buzId, receivers, sendTime);
			}
		}
	/**
	 * 转发
	 */
	//public void forward(String currentTaskId ,String linkId,String handleContent,List<ContactTask> cTask) {
	public void forward(String currentTaskId ,String linkId,String handleContent,
			ContactMain cMain,String taskOwnerIds,String taskOwnerNames) {	
		
		List<ContactTask> cTask=MyUtil.getTaskFromStr(cMain,taskOwnerIds, taskOwnerNames);	
		
		// 1.修改转发人的task
		ContactTask currentTask = contactTaskMgr.find(currentTaskId);
		currentTask.setOperationContent(handleContent);
		currentTask.setOperationTime(StaticMethod.getTimestamp());
		currentTask.setTaskState(Type.END);
		contactTaskMgr.save(currentTask);
		
		//2.添加新的该联系函的发布范围
		String mainid = currentTask.getMainId();
		 for (ContactTask contactTask : cTask) {
			  contactTask.setMainId(mainid);
			  contactTask.setPreLinkId(linkId);
			  contactTask.setTaskState(Type.RUNNING); 
			  contactTask.setTaskType(Type.PUBLISHING);//所有 任务 发布中
			  contactTask.setTaskName("publishing");
		}
		 ContactTask[] ctaskArr=new ContactTask[cTask.size()];
		 cTask.toArray(ctaskArr);
		 contactTaskMgr.save(ctaskArr);
		//3.修改main 状态为转发
		 cMain.setType(Type.FORWARD);	
		 contactMainMgr.save(cMain);
			
		//4.如果业务联系需要发送短信  发送短信给新增加的转发人员		
		 MsgService msgService = new MsgServiceImpl();
			//即时提醒
			String instantServiceId=MyUtil.getServiceId(MyUtil.DEFAULT_workProcessName, MyUtil.SMS_SERVICE_INSTANT);
			String buzId=StaticMethod.nullObject2String(cMain.getId());
			Date currentTime = new Date();
	    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	String sendTime = formatter.format(currentTime);					
			//发布 或 转发，短息通知处理者（有部门和个人）处理
			String publishedRange=taskOwnerIds;
			String[] publishedArr=publishedRange.split(",");
			//发布 或 转发，短息通知处理者（有部门和个人）处理
			if(StaticMethod.nullObject2String(cMain.getIsSendSMS()).equals("1")){
				String content =  "你好，你有编号:"+StaticMethod.nullObject2String(cMain.getCode())+"的业务涵需要处理!";
				String receivers="";
				//发送短信给的对象，可能有三种：人员、部门、角色，以下判断对象的类型. 此处现只有2种：人员、部门
				ID2NameService service = (ID2NameService) ApplicationContextHolder.getInstance().getBean("ID2NameGetServiceCatch");
				for(String published:publishedArr ){
					if(!"".equals((service.id2Name(published,"tawSystemUserDao")))){//用户
						receivers=receivers+"1,"+published+"#";
					}else{//部门
						receivers=receivers+"2,"+published+"#";
					}
				}
				receivers=receivers.substring(0, receivers.length());
				msgService.sendMsg(instantServiceId, content,  buzId, receivers, sendTime);
			} 
	}

	/**
	 * 终止流程
	 */
	public void endTask(String taskId,String handleContent) {
		// 当前task
		ContactTask currentTask = contactTaskMgr.find(taskId);
		currentTask.setOperationContent(handleContent);
		currentTask.setOperationTime(StaticMethod.getTimestamp());
		currentTask.setTaskState(Type.END);
//		currentTask.setTaskType(Type.DEATH);
		contactTaskMgr.save(currentTask);
		
		//该联系函 所有task
		search = new Search();
		search.addFilterEqual("mainId", currentTask.getMainId());
		int selct = 0;
		List<ContactTask> auditTaskList = contactTaskMgr.search(search);
		for (ContactTask contactTask : auditTaskList) {		
		  if("1".equals(contactTask.getTaskState()+"")){//taskState=1:运行中
			  selct = 1 ;
			  break;
		   }	
	    }
		 //如果所有contactTask都结束。 3.修改main 状态为终止
		if(selct == 0){
		 //0:未完成，未超时,1:未完成，已超时;  2:已完成，未超时,3:已完成，已超时	
		 ContactMain cMain = contactMainMgr.find(currentTask.getMainId());
			cMain.setType(Type.DEATH);	
			if( (new Date()).compareTo(cMain.getDeathTime())<=0){
				cMain.setOverTimeFlag(2);
			}else{
				cMain.setOverTimeFlag(3);
			}
			
			contactMainMgr.save(cMain);
		}		
     }
	
	/**
	 * 阅知
	 */
	public void finlishTask(String taskId,String handleContent) {
		// 当前task
		ContactTask currentTask = contactTaskMgr.find(taskId);
		currentTask.setOperationContent(handleContent);
		currentTask.setOperationTime(StaticMethod.getTimestamp());
		currentTask.setTaskState(Type.END);
//		currentTask.setTaskType(Type.DEATH);
		contactTaskMgr.save(currentTask);
		
		//该联系函 所有task
		search = new Search();
		search.addFilterEqual("mainId", currentTask.getMainId());
		int selct = 0;
		List<ContactTask> auditTaskList = contactTaskMgr.search(search);
		for (ContactTask contactTask : auditTaskList) {
			
		  if("1".equals(contactTask.getTaskState()+"")){
			  selct = 1 ;
			  break;
		   }
		
	}
		 //	 3.修改main 状态为终止
		if(selct == 0){
		 ContactMain cMain = contactMainMgr.find(currentTask.getMainId());
			cMain.setType(Type.DEATH);
			
			contactMainMgr.save(cMain);
		}		
}
		
	
	
	
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("联系函 过期数据整理，轮询开始");

		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
		String updateSql = "update contact_main set type = "+Type.END
									+" where valid <='"+MyUtil.getNowTime()+"' and type = 2 " ;
		System.out.println(updateSql);
		//配置 cron    0 0 3 * * ?  每天凌晨3点开始
		jdbcService.executeProcedure(updateSql);
		System.out.println("联系函 过期数据整理，轮询结束");
	}
	
	
	public void setContactMainMgr(ContactMainMgr contactMainMgr) {
		this.contactMainMgr = contactMainMgr;
	}

	public void setContactTaskMgr(ContactTaskMgr contactTaskMgr) {
		this.contactTaskMgr = contactTaskMgr;
	}
	public ContactMain findByMainId(String id) {
		return this.contactMainMgr.find(id);
	}

}

