package com.boco.eoms.partner.home.mgr.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.hibernate.Session;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import base.dao.Nop3GenericDaoImpl;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.ConvertUtil;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcService;
import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.home.mgr.LinkMgr;
import com.boco.eoms.partner.home.mgr.PublishInfoMgr;
import com.boco.eoms.partner.home.mgr.PublishMgr;
import com.boco.eoms.partner.home.mgr.TaskMgr;
import com.boco.eoms.partner.home.model.PublishInfo;
import com.boco.eoms.partner.home.model.PublishLink;
import com.boco.eoms.partner.home.model.PublishTask;
import com.boco.eoms.partner.home.util.MyUtil;
import com.boco.eoms.partner.home.util.StateType;
import com.boco.eoms.partner.home.webapp.form.PublishForm;
import com.boco.eoms.partner.netresource.service.IEomsService;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
/**
 * <p>
 * Title:公告基本信息
 * </p>
 * <p>
 * Description:公告基本信息
 * </p>
 * <p>
 * Aug 24, 2012 10:53:19 AM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public class PublishMgrImpl  implements PublishMgr,Job{

	private PublishInfoMgr publishInfoMgr;
	private TaskMgr taskMgr;
	private LinkMgr linkMgr;
	
	public void setPublishInfoMgr(PublishInfoMgr publishInfoMgr) {
		this.publishInfoMgr = publishInfoMgr;
	}

	public void setTaskMgr(TaskMgr taskMgr) {
		this.taskMgr = taskMgr;
	}

	public void setLinkMgr(LinkMgr linkMgr) {
		this.linkMgr = linkMgr;
	}

	public PublishInfo saveDrafts(HttpServletRequest request,ActionForm form){
		TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		PublishForm publishForm =  (PublishForm)form;
		Date time = new Date();
		
		PublishInfo pInfo=new PublishInfo();
		if(publishForm!=null&&! "".equals(Strings.nullToEmpty(publishForm.getId()))){
			   System.out.println("已存在");
			    pInfo =  publishInfoMgr.find(publishForm.getId());
			    pInfo.setFile(publishForm.getFile());
				pInfo.setIsAudit(publishForm.getIsAudit());
				pInfo.setAuditorid(publishForm.getAuditorid());
				pInfo.setAuditorname(publishForm.getAuditorname());
				pInfo.setPublishedRange(publishForm.getPublishedRange());
				pInfo.setPublishedRangeName(publishForm.getPublishedRangeName());
				pInfo.setPublishContent(publishForm.getPublishContent());
				pInfo.setSubject(publishForm.getSubject());
				pInfo.setType(StateType.INFO_TYPE_DRAFTS);
				pInfo.setValid(publishForm.getValid());   
			    pInfo.setPublishTime(publishForm.getPublishTime());  
		}else{
			System.out.println("不存在");
			try {
				pInfo =(PublishInfo) ConvertUtil.populateObject(pInfo,publishForm);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			pInfo.setPublisherDeptId(sessionInfo.getDeptid());
			pInfo.setPublisherDeptName(sessionInfo.getDeptname());
			pInfo.setPublisherId(sessionInfo.getUserid());
			pInfo.setPublisherName(sessionInfo.getUsername());
			pInfo.setPublishTime(time);
			pInfo.setType(StateType.INFO_TYPE_DRAFTS);	
			
		}
		System.out.println("aduditor:"+pInfo.getAuditorid());
		System.out.println("aduditor:"+pInfo.getAuditorname());
		publishInfoMgr.save(pInfo);		
		
		//保存操作记录.  有的操作由task导致而来（如审核），有的是直接而来（如保存草稿）
		PublishLink publishLink=new PublishLink();
		publishLink.setMainId(pInfo.getId());
		publishLink.setOperateType(StateType.OPERATE_DRAFTS);
		publishLink.setOperateName("存草稿");
		publishLink.setOperateContent(publishForm.getPublishContent());
		publishLink.setOperateTime(time);
		publishLink.setOperateUserid(sessionInfo.getUserid());
		publishLink.setOperateDeptid(sessionInfo.getDeptid());
		publishLink.setOperateDeptname(sessionInfo.getDeptname());
		linkMgr.save(publishLink);
		
		return pInfo;
	}

	public SearchResult<PublishInfo> getList(HttpServletRequest request,int listType,
	  int pageIndex,int  pageSize,Search publishInfoSearch) {
		TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String type = StaticMethod.nullObject2String(request.getAttribute("type"));
		String userid = sessionInfo.getUserid();
		String deptid = sessionInfo.getDeptid();
		pageIndex= pageIndex<=0?0:pageIndex;
		String subjectStringLike=StaticMethod.null2String(request.getParameter("subjectStringLike"));
		String publishTimeDateGreaterThan=StaticMethod.null2String(request.getParameter("publishTimeDateGreaterThan"));
		String publishTimeDateLessThan=StaticMethod.null2String(request.getParameter("publishTimeDateLessThan"));
		String validDateGreaterThan=StaticMethod.null2String(request.getParameter("validDateGreaterThan"));
		String validDateLessThan=StaticMethod.null2String(request.getParameter("validDateLessThan")); 
		
		String subSql=" ";
		String subSql2=" ";
		if(!"".equals(subjectStringLike)){
			subSql+=" and pi.subject like :subjectStringLike";
			subSql2+=" and pi.subject like '%"+subjectStringLike+"%' ";
			subjectStringLike="%"+subjectStringLike+"%";
			
		}
		if(!"".equals(publishTimeDateGreaterThan)){
			subSql+=" and pi.publishTime >= :publishTimeDateGreaterThan";
			subSql2+=" and pi.publishTime >= "+CommonSqlHelper.formatDateTime(publishTimeDateGreaterThan);
		}
		if(!"".equals(publishTimeDateLessThan)){
			subSql+=" and pi.publishTime <= :publishTimeDateLessThan";
			subSql2+=" and pi.publishTime <= "+CommonSqlHelper.formatDateTime(publishTimeDateLessThan);
		}
		if(!"".equals(validDateGreaterThan)){
			subSql+=" and pi.valid >= :validDateGreaterThan";
			subSql2+=" and pi.valid >= "+CommonSqlHelper.formatDateTime(validDateGreaterThan);
		}
		if(!"".equals(validDateLessThan)){
			subSql+=" and pi.valid <= :validDateLessThan";
			subSql2+=" and pi.valid <= "+CommonSqlHelper.formatDateTime(validDateLessThan);
		}
		
		Nop3GenericDaoImpl nop3Dao = (Nop3GenericDaoImpl) 
		  ApplicationContextHolder.getInstance().getBean("nop3GenericDao");
	 	Session session = nop3Dao.getSession();
		org.hibernate.Query query=null;
		org.hibernate.Query queryCount=null;
		
		publishInfoSearch.setMaxResults(pageSize);
		publishInfoSearch.setFirstResult(pageIndex * pageSize);
		 
		SearchResult<PublishInfo> result = null;
        if(listType==1){ //草稿列表  
        	publishInfoSearch.addFilterEqual("publisherId", userid);
			 Integer[] types={StateType.INFO_TYPE_DRAFTS,StateType.INFO_TYPE_REJECT};
			 publishInfoSearch.addFilterIn("type",types);
			 publishInfoSearch.addSortDesc("publishTime");
			 result =  publishInfoMgr.searchAndCount(publishInfoSearch);
        }else{
        	// query=session.createQuery("select new com.boco.eoms.partner.home.model.PublishInfo(pi.*) from PublishInfo pi,PublishTash pt "
			//query=session.createQuery("select pi.* from PublishInfo pi,PublishTash pt "
			 query=session.createQuery("select new com.boco.eoms.partner.home.model.PublishInfo(pi.id,pi.publisherId,pi.publisherName,pi.publisherDeptId,pi.publisherDeptName,pi.subject,pi.publishTime,pi.publishContent,pi.valid,pi.isAudit,pi.auditorid,pi.auditorname,pi.publishedRange,pi.publishedRangeName,pi.file,pi.type) from PublishInfo pi,PublishTask pt "		
			 +" where pt.mainId=pi.id and pt.taskOwnerId=:taskOwnerId and pt.taskType=:taskType and pt.taskState=:taskState "+subSql+" order by pi.publishTime desc , pi.valid asc ");
			 query.setParameter("taskOwnerId", userid);
			 
			 query.setParameter("taskState", StateType.TASK_RUNNING);
			 query.setFirstResult(pageIndex * pageSize);
			 query.setMaxResults(pageSize);
			 /////////
			 queryCount=session.createQuery("select count(*) from PublishInfo pi,PublishTask pt "		
			 +" where pt.mainId=pi.id and pt.taskOwnerId=:taskOwnerId and pt.taskType=:taskType and pt.taskState=:taskState "+subSql);
			 queryCount.setParameter("taskOwnerId", userid); 
			 queryCount.setParameter("taskState", StateType.TASK_RUNNING);
			 
        	 if(listType==2){ //待审批	
        		 query.setParameter("taskType", StateType.OPERATE_AUDIT);
        		 queryCount.setParameter("taskType", StateType.OPERATE_AUDIT);
             }else if(listType==3){//待查看（阅知）		
            	 query.setParameter("taskType", StateType.OPERATE_READ);
        		 queryCount.setParameter("taskType", StateType.OPERATE_READ);
             }else if(listType==4){ //与自己相关的公告：自己发布过的，或者自己处理过的
            	 
            	 String requestType = StaticMethod.nullObject2String(request.getParameter("type"));
         		 boolean mobileRequest= (!"".equals(requestType))&&"android".equalsIgnoreCase(requestType);
         		String entrySql="",countSql="";
            	 if(mobileRequest){
            		 entrySql="SELECT pi.* FROM "
                    	 +"( SELECT * FROM publicnotice_main WHERE publisherid='"+userid+"' and (type='2' or type='4')  " 
                    	 +"  UNION  SELECT pi1.* FROM publicnotice_main pi1,publicnotice_task pt WHERE  pi1.ID=pt.mainid and pt.taskownerid='"+userid+"' "
                    	 +" ) pi WHERE 1=1  "+subSql2+" order by pi.publishtime desc,pi.valid asc ";
            		 countSql="SELECT count(*) as count FROM "
                    	 +"( SELECT * FROM publicnotice_main WHERE publisherid='"+userid+"' and (type='2' or type='4')  "
                    	 +"  UNION  SELECT pi1.* FROM publicnotice_main pi1,publicnotice_task pt WHERE  pi1.ID=pt.mainid and pt.taskownerid='"+userid+"' "
                    	 +" ) pi WHERE 1=1  "+subSql2;
            	 }else{
            		 entrySql="SELECT pi.* FROM "
                    	 +"( SELECT * FROM publicnotice_main WHERE publisherid='"+userid+"' "
                    	 +"  UNION  SELECT pi1.* FROM publicnotice_main pi1,publicnotice_task pt WHERE pi1.ID=pt.mainid and pt.taskownerid='"+userid+"' "
                    	 +" ) pi WHERE 1=1  "+subSql2+" order by pi.publishtime desc,pi.valid asc ";
            		 countSql="SELECT count(*) as count FROM "
                    	 +"( SELECT * FROM publicnotice_main WHERE publisherid='"+userid+"' "
                    	 +"  UNION  SELECT pi1.* FROM publicnotice_main pi1,publicnotice_task pt WHERE pi1.ID=pt.mainid and pt.taskownerid='"+userid+"' "
                    	 +" ) pi WHERE 1=1  "+subSql2;
            	 }
            	 
            	 IEomsService eomsService = (IEomsService) ApplicationContextHolder.getInstance().getBean("eomsService");
     		 	 Map map = eomsService.getDataList(PublishInfo.class, "pi", 
     		 			  entrySql, 
     		 			  countSql, 
     		 			 pageIndex, pageSize);
     		 	result=new SearchResult<PublishInfo>();
   			    result.setTotalCount((Integer)map.get("totalCount"));
   			    result.setResult((List<PublishInfo>)map.get("list"));	
   			    
   			    return result;
             }else{
            	 return null;
             }
        	
        	 if(!"".equals(subjectStringLike)){
				 query.setParameter("subjectStringLike", subjectStringLike);
				 queryCount.setParameter("subjectStringLike", subjectStringLike);
			 }
			 if(!"".equals(publishTimeDateGreaterThan)){
				 query.setParameter("publishTimeDateGreaterThan", CommonUtils.toEomsStandardDate(publishTimeDateGreaterThan));
				 queryCount.setParameter("publishTimeDateGreaterThan", CommonUtils.toEomsStandardDate(publishTimeDateGreaterThan));
			 }
			 if(!"".equals(publishTimeDateLessThan)){
				 query.setParameter("publishTimeDateLessThan", CommonUtils.toEomsStandardDate(publishTimeDateLessThan));
				 queryCount.setParameter("publishTimeDateLessThan", CommonUtils.toEomsStandardDate(publishTimeDateLessThan));
			 }
			 if(!"".equals(validDateGreaterThan)){
				 query.setParameter("validDateGreaterThan", CommonUtils.toEomsStandardDate(validDateGreaterThan));
				 queryCount.setParameter("validDateGreaterThan", CommonUtils.toEomsStandardDate(validDateGreaterThan));
			 }
			 if(!"".equals(validDateLessThan)){
				 query.setParameter("validDateLessThan", CommonUtils.toEomsStandardDate(validDateLessThan));
				 queryCount.setParameter("validDateLessThan", CommonUtils.toEomsStandardDate(validDateLessThan));
			 }
			 
			 result=new SearchResult<PublishInfo>();
			 result.setTotalCount(Integer.parseInt(queryCount.uniqueResult().toString()));
			 result.setResult((List<PublishInfo>)query.list());	
        }	
		
		return result;
	}


	/**公告作废*/
	public void invalid(HttpServletRequest request,ActionForm form) {
		TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		PublishForm publishForm =  (PublishForm)form;
		String id = publishForm.getId();
		Date time = new Date();
		
		//保存操作记录.  有的操作由task导致而来（如审核），有的是直接而来（如保存草稿）
		PublishLink publishLink=new PublishLink();
		publishLink.setMainId(id);
		publishLink.setOperateType(StateType.OPERATE_INVALID);
		publishLink.setOperateName("作废");
		publishLink.setOperateContent(publishForm.getPublishContent());
		publishLink.setOperateTime(time);
		publishLink.setOperateUserid(sessionInfo.getUserid());
		publishLink.setOperateDeptid(sessionInfo.getDeptid());
		publishLink.setOperateDeptname(sessionInfo.getDeptname());
		linkMgr.save(publishLink);
		
		//2.更改已保存的 Task信息，Link信息状态
		Nop3GenericDaoImpl nop3Dao = (Nop3GenericDaoImpl) 
		  ApplicationContextHolder.getInstance().getBean("nop3GenericDao");
	 	Session session = nop3Dao.getSession();
		org.hibernate.Query query=session.createQuery("update PublishTask set taskState=:taskState1 where mainId=:mainId and taskState in (:taskState2) " );
		query.setParameter("taskState1",StateType.TASK_END);
		query.setParameter("mainId", id);
		Integer[] st={StateType.TASK_NOSTART,StateType.TASK_RUNNING};
		query.setParameterList("taskState2",st);
		query.executeUpdate();
		
		//3.修改公告信息状态
		PublishInfo pInfo = publishInfoMgr.find(id);
		pInfo.setType(StateType.INFO_TYPE_INVALID);
		publishInfoMgr.save(pInfo);
	}

	/**送审*/
	public void toAudit(HttpServletRequest request,ActionForm form) {
		TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		PublishForm publishForm =  (PublishForm)form;
		Date time = new Date();
		//公告信息
		String id = Strings.nullToEmpty(publishForm.getId()).trim();
		PublishInfo pInfo=null;
		boolean isToNextAudit=Boolean.valueOf(request.getParameter("isToNextAudit"));
		System.out.println("isToNextAudit:"+isToNextAudit);
		
		if(!isToNextAudit){//首次传递审批
			if(!"".equals(id)){//从保存的草稿送审
				System.out.println("从保存的草稿送审");
				 pInfo =  publishInfoMgr.find(publishForm.getId());
				 pInfo.setFile(publishForm.getFile());
				 pInfo.setIsAudit(publishForm.getIsAudit());
				 pInfo.setAuditorid(publishForm.getAuditorid());
				 pInfo.setAuditorname(publishForm.getAuditorname());
				 pInfo.setPublishedRange(publishForm.getPublishedRange());
				 pInfo.setPublishedRangeName(publishForm.getPublishedRangeName());
				 pInfo.setPublishContent(publishForm.getPublishContent());
				 pInfo.setSubject(publishForm.getSubject());
				 pInfo.setType(StateType.INFO_TYPE_AUDIT);
				 pInfo.setValid(publishForm.getValid());   
				 pInfo.setPublishTime(time);  			 
			}else{//直接送审
				System.out.println("直接送审");
				pInfo=new PublishInfo();
				try {
					pInfo =(PublishInfo) ConvertUtil.populateObject(pInfo,publishForm);
				} catch (Exception e) {
					e.printStackTrace();
					return ;
				}
				pInfo.setPublisherDeptId(sessionInfo.getDeptid());
				pInfo.setPublisherDeptName(sessionInfo.getDeptname());
				pInfo.setPublisherId(sessionInfo.getUserid());
				pInfo.setPublisherName(sessionInfo.getUsername());
				pInfo.setType(StateType.INFO_TYPE_AUDIT);
				pInfo.setPublishTime(time);
			}
			publishInfoMgr.save(pInfo);
		}else{//传递下次审批
			 pInfo =  publishInfoMgr.find(publishForm.getId());
			//更改已保存的 Task信息,此处为用户的此处审批任务
			Search search=new Search();
			search.addFilterEqual("mainId", pInfo.getId());
			search.addFilterEqual("taskOwnerId", sessionInfo.getUserid());
			search.addFilterEqual("taskType",StateType.OPERATE_AUDIT);
			search.addFilterEqual("taskState",StateType.TASK_RUNNING);
			PublishTask task=taskMgr.searchUnique(search);
			task.setOperateContent(publishForm.getTaskOperateContent());
			task.setOperateTime(time);
			task.setTaskState(StateType.TASK_END);
			taskMgr.save(task);
		}
		
		//保存操作记录.  有的操作由task导致而来（如审核），有的是直接而来（如保存草稿）
		PublishLink publishLink=new PublishLink();
		publishLink.setMainId(pInfo.getId());
		publishLink.setOperateType(StateType.OPERATE_TO_AUDIT);
		publishLink.setOperateName("送审");
		if(!isToNextAudit){
			publishLink.setOperateContent(publishForm.getPublishContent());
		}else{
			publishLink.setOperateContent(publishForm.getTaskOperateContent());
		}
		
		publishLink.setOperateTime(time);
		publishLink.setOperateUserid(sessionInfo.getUserid());
		publishLink.setOperateDeptid(sessionInfo.getDeptid());
		publishLink.setOperateDeptname(sessionInfo.getDeptname());
		linkMgr.save(publishLink);
		
		//生成任务
		System.out.println("需要审批");
		PublishTask publishTask=new PublishTask();
		publishTask.setMainId(pInfo.getId());
		publishTask.setTaskOwnerId(publishForm.getAuditorid());
		publishTask.setTaskOwnerName(publishForm.getAuditorname());
		publishTask.setTaskType(StateType.OPERATE_AUDIT);
		publishTask.setTaskName("审批");
		publishTask.setTaskState(StateType.TASK_RUNNING);
		publishTask.setOperateTime(time);
		publishTask.setPrelinkid(publishLink.getId());
			
		taskMgr.save(publishTask);
		
	}

	/**审批通过*/
	public void auditPass(HttpServletRequest request,ActionForm form) {
		TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		PublishForm publishForm =  (PublishForm)form;
		Date time = new Date();
		String id = publishForm.getId();
		 
		//1.生成操作记录
		PublishLink publishLink=new PublishLink();
		publishLink.setMainId(id);
		publishLink.setOperateType(StateType.OPERATE_AUDIT);
		publishLink.setOperateName("审核");
		publishLink.setOperateResult(StateType.OPERATERESULT_AUDITPASS);//通过
		publishLink.setOperateContent(publishForm.getTaskOperateContent());
		publishLink.setOperateTime(time);
		publishLink.setOperateUserid(sessionInfo.getUserid());
		publishLink.setOperateDeptid(sessionInfo.getDeptid());
		publishLink.setOperateDeptname(sessionInfo.getDeptname());
		linkMgr.save(publishLink);
		
		//2.更改已保存的 Task信息 (此处为审核task)
		Search search=new Search();
		search.addFilterEqual("mainId", id);
		search.addFilterEqual("taskOwnerId", sessionInfo.getUserid());
		search.addFilterEqual("taskType",StateType.OPERATE_AUDIT);
		search.addFilterEqual("taskState",StateType.TASK_RUNNING);
		PublishTask task=taskMgr.searchUnique(search);
		task.setOperateContent(publishForm.getTaskOperateContent());
		task.setOperateTime(time);
		task.setTaskState(StateType.TASK_END);
		taskMgr.save(task);
		
		//3.修改公告信息状态
		PublishInfo pInfo = publishInfoMgr.find(id);
		//pInfo.setPublishTime(pInfo.getPublishTime());
		pInfo.setValid(pInfo.getValid());
		pInfo.setType(StateType.INFO_TYPE_RELEASE);//审批通过进入发布
		publishInfoMgr.save(pInfo);
				 
		//生成阅读任务s
		String  publishedRange=pInfo.getPublishedRange();
		String  publishedRangeName=pInfo.getPublishedRangeName();
		List<PublishTask> list=MyUtil.getTaskFromStr(pInfo, publishLink, publishedRange, publishedRangeName);
		PublishTask[] publishTaskArr=new PublishTask[list.size()];
		list.toArray(publishTaskArr);
		taskMgr.save(publishTaskArr);
	}

	/*
	public void auditPassToNextAudit(HttpServletRequest request,ActionForm form) {
		TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		PublishForm publishForm =  (PublishForm)form;
		String id = publishForm.getId();
		Search search;
		//1.更改当前审批人 Task信息，Link信息
			search = new Search();
			search.addFilterEqual("mainId", id);
			search.addFilterEqual("taskOwnerId", sessionInfo.getUserid());
			search.addFilterEqual("taskOwnerType", "审批人");
			search.addFilterEqual("taskState",StateType.TASK_RUNNING);
			search.addFilterEqual("taskType",StateType.TASK_AUDIT);
			PublishTask oldTask = taskMgr.searchUnique(search);
			oldTask.setTaskState(StateType.TASK_END);
			oldTask.setTaskType(StateType.TASK_CANNOT_READ);
			taskMgr.save(oldTask);
			//LINK
				search = new Search();
				search.addFilterEqual("mainId", id);
				search.addFilterEqual("taskId", oldTask.getId());
				PublishLink oldLink = linkMgr.searchUnique(search);
				oldLink.setOperateTime(new Date());
				oldLink.setOperateType(StateType.LINK_NEXT_AUDIT);
				oldLink.setAuditaDvice(publishForm.getAuditaDvice());
				oldLink.setAuditResult(StateType.LINK_RESULT_AUDIT_PASS_NEXT);
			linkMgr.save(oldLink);	
		//2.添加一条新的审批人 Task信息	Link信息
			PublishTask newTask = new PublishTask();
				newTask.setMainId(id);
				newTask.setTaskName("公告审批Task");
				newTask.setTaskOwnerId(publishForm.getTaskOwnerId());
				newTask.setTaskOwnerName(publishForm.getTaskOwnerName());
				newTask.setTaskOwnerType("审批人");
				newTask.setTaskState(StateType.TASK_RUNNING);
				newTask.setTaskType(StateType.TASK_AUDIT);
			taskMgr.save(newTask);
			PublishLink	newLink = new PublishLink();
					newLink.setMainId(id);
					newLink.setTaskId(newTask.getId());
					newLink.setOperateType(StateType.LINK_DRAFTS);
				linkMgr.save(newLink);	
		//3.更改公告信息
			PublishInfo pInfo = publishInfoMgr.find(id);
			pInfo.setPublishTime(pInfo.getPublishTime());
			pInfo.setValid(pInfo.getValid());
			pInfo.setType(StateType.INFO_TYPE_AUDITING);
			publishInfoMgr.save(pInfo);
	}
    */
	
	public void auditReject(HttpServletRequest request,ActionForm form) {
		TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		PublishForm publishForm =  (PublishForm)form;
		Date time = new Date();
		String id = publishForm.getId();
		
		//1.生成操作记录
		PublishLink publishLink=new PublishLink();
		publishLink.setMainId(id);
		publishLink.setOperateType(StateType.OPERATE_AUDIT);
		publishLink.setOperateName("审核");
		publishLink.setOperateResult(StateType.OPERATERESULT_AUDITREJECT);//驳回
		publishLink.setOperateContent(publishForm.getTaskOperateContent());
		publishLink.setOperateTime(time);
		publishLink.setOperateUserid(sessionInfo.getUserid());
		publishLink.setOperateDeptid(sessionInfo.getDeptid());
		publishLink.setOperateDeptname(sessionInfo.getDeptname());
		linkMgr.save(publishLink);
		
		//2.更改已保存的 Task信息 (此处为审核task)
		Search search=new Search();
		search.addFilterEqual("mainId", id);
		search.addFilterEqual("taskOwnerId", sessionInfo.getUserid());
		search.addFilterEqual("taskType",StateType.OPERATE_AUDIT);
		search.addFilterEqual("taskState",StateType.TASK_RUNNING);
		PublishTask task=taskMgr.searchUnique(search);
		task.setOperateContent(publishForm.getTaskOperateContent());
		task.setOperateTime(time);
		task.setTaskState(StateType.TASK_END);
		taskMgr.save(task);
		
		//3.修改公告信息状态
		PublishInfo pInfo = publishInfoMgr.find(id);
		//pInfo.setPublishTime(pInfo.getPublishTime());
		pInfo.setValid(pInfo.getValid());
		pInfo.setType(StateType.INFO_TYPE_REJECT);
		publishInfoMgr.save(pInfo);
	}

	public void read(HttpServletRequest request,ActionForm form) {
		//调整for mobile android. 20130401
		TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		//PublishForm publishForm =(PublishForm)form;
		Date time = new Date();
		//String id = publishForm.getId();
		String id=Strings.nullToEmpty(request.getParameter("id"));
		String operateContent=Strings.nullToEmpty(request.getParameter("operateContent"));
		String taskId=Strings.nullToEmpty(request.getParameter("taskId"));
		
		//1.保存操作记录
		PublishLink publishLink=new PublishLink();
		publishLink.setMainId(id);
		publishLink.setOperateType(StateType.OPERATE_READ);
		publishLink.setOperateName("阅知");
		//publishLink.setOperateContent(publishForm.getOperateContent());
		publishLink.setOperateContent(operateContent);
		publishLink.setOperateTime(time);
		publishLink.setOperateUserid(sessionInfo.getUserid());
		publishLink.setOperateDeptid(sessionInfo.getDeptid());
		publishLink.setOperateDeptname(sessionInfo.getDeptname());
		linkMgr.save(publishLink);
		
		//2.更改已保存的 Task信息
		PublishTask task=taskMgr.find(taskId);
		task.setOperateContent(operateContent);
		task.setOperateTime(time);
		task.setTaskState(StateType.TASK_END);
		taskMgr.save(task);
							
		//3.修改公告信息状态
		PublishInfo pInfo = publishInfoMgr.find(id);
		pInfo.setType(StateType.INFO_TYPE_READ);
		publishInfoMgr.save(pInfo);				
	}

	/**直接发布，不需要审批*/
	public void directlyPublish(HttpServletRequest request, ActionForm form) {
		TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		PublishForm publishForm =  (PublishForm)form;
		Date time = new Date();
		
		//公告信息
		String id = Strings.nullToEmpty(publishForm.getId()).trim();
		PublishInfo pInfo=null;
	
		if(!"".equals(id)){//从保存的草稿发布
			System.out.println("从保存的草稿fabu");
			 pInfo =  publishInfoMgr.find(publishForm.getId());
			 pInfo.setFile(publishForm.getFile());
			 pInfo.setIsAudit(publishForm.getIsAudit());
			 pInfo.setAuditorid(publishForm.getAuditorid());
			 pInfo.setAuditorname(publishForm.getAuditorname());
			 pInfo.setPublishedRange(publishForm.getPublishedRange());
			 pInfo.setPublishedRangeName(publishForm.getPublishedRangeName());
			 pInfo.setPublishContent(publishForm.getPublishContent());
			 pInfo.setSubject(publishForm.getSubject());
			 pInfo.setType(StateType.INFO_TYPE_RELEASE);
			 pInfo.setValid(publishForm.getValid());   
			 pInfo.setPublishTime(time);  			 
		}else{//直接发布
			System.out.println("直接发布");
			pInfo=new PublishInfo();
			try {
				pInfo =(PublishInfo) ConvertUtil.populateObject(pInfo,publishForm);
			} catch (Exception e) {
				e.printStackTrace();
				return ;
			}
			pInfo.setPublisherDeptId(sessionInfo.getDeptid());
			pInfo.setPublisherDeptName(sessionInfo.getDeptname());
			pInfo.setPublisherId(sessionInfo.getUserid());
			pInfo.setPublisherName(sessionInfo.getUsername());
			pInfo.setType(StateType.INFO_TYPE_RELEASE);
			pInfo.setPublishTime(time);
		}
		publishInfoMgr.save(pInfo);
		
		//保存操作记录.  有的操作由task导致而来（如审核），有的是直接而来（如保存草稿，直接发布）
		PublishLink publishLink=new PublishLink();
		publishLink.setMainId(pInfo.getId());
		publishLink.setOperateType(StateType.OPERATE_PUBLISH);
		publishLink.setOperateName("发布");
		publishLink.setOperateContent(publishForm.getPublishContent());
		publishLink.setOperateTime(time);
		publishLink.setOperateUserid(sessionInfo.getUserid());
		publishLink.setOperateDeptid(sessionInfo.getDeptid());
		publishLink.setOperateDeptname(sessionInfo.getDeptname());
		linkMgr.save(publishLink);
		
		//生成阅读任务
		String  publishedRange=publishForm.getPublishedRange();
		String  publishedRangeName=publishForm.getPublishedRangeName();
		List<PublishTask> list=MyUtil.getTaskFromStr(pInfo, publishLink, publishedRange, publishedRangeName);
		PublishTask[] publishTaskArr=new PublishTask[list.size()];
		list.toArray(publishTaskArr);
		taskMgr.save(publishTaskArr);
	}

	/*
	public void directlyToAudit(HttpServletRequest request, ActionForm form) {
		TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		PublishForm publishForm =  (PublishForm)form;
		Date time =new Date();
 //1.添加一条公告基本信息
		 PublishInfo  pInfo = new PublishInfo();
			String id = publishForm.getId();
			if(id!=null)
			    pInfo.setId(id);
			pInfo.setFile(publishForm.getFile());
			pInfo.setIsAudit(publishForm.getIsAudit());
			pInfo.setPublishArea(publishForm.getPublishArea());
			pInfo.setPublishContent(publishForm.getPublishContent());
			pInfo.setPublisherDeptId(sessionInfo.getDeptid());
			pInfo.setPublisherDeptName(sessionInfo.getDeptname());
			pInfo.setPublisherId(sessionInfo.getUserid());
			pInfo.setPublisherName(sessionInfo.getUsername());
			pInfo.setPublishTime(time);
			pInfo.setSubject(publishForm.getSubject());
			pInfo.setType(StateType.INFO_TYPE_AUDITING);
			pInfo.setValid(publishForm.getValid());
		publishInfoMgr.save(pInfo);	
 //2.添加多条Task信息
		PublishTask task;
		PublishLink link;
		String pinfoId = pInfo.getId();
		//阅知信息 Task
		String publishAreaType = publishForm.getPublishArea();
		String[] areaIds = publishForm.getPublishAreaIdValues().split(",");
		String[] areaNames = publishForm.getPublishAreaNameValues().split(",");
			for (int i=0,k=areaIds.length;i<k;i++) {
				task = new PublishTask();
					task.setMainId(pinfoId);
					task.setTaskName("公告阅知Task");
					task.setTaskOwnerId(areaIds[i]);
					if("组织机构".equals(publishAreaType))
						task.setTaskOwnerType("阅知部门");
					else if("人员".equals(publishAreaType))
						task.setTaskOwnerType("阅知人");
					task.setTaskOwnerName(areaNames[i]);
					task.setTaskState(StateType.TASK_NOSTART);
					task.setTaskType(StateType.TASK_CANNOT_READ);
				taskMgr.save(task);
			}
		//审批信息Task
			if(StateType.INFO_NEEDAUDIT ==publishForm.getIsAudit()){
					task = new PublishTask();
						task.setMainId(pinfoId);
						task.setTaskName("公告审批Task");
						task.setTaskOwnerId(publishForm.getTaskOwnerId());
						task.setTaskOwnerName(publishForm.getTaskOwnerName());
						task.setTaskOwnerType("审批人");
						task.setTaskState(StateType.TASK_RUNNING);
						task.setTaskType(StateType.TASK_AUDIT);
					taskMgr.save(task);
					//3.添加一条 Link信息
					link = new PublishLink();
						link.setMainId(pinfoId);
						link.setTaskId(task.getId());
						link.setOperateType(StateType.LINK_TO_AUDIT);
					linkMgr.save(link);	
			}
	}
*/
	
	/**得到未过期的待查阅业务联系函
	 * */
	@SuppressWarnings("unchecked")
	public List<PublishInfo> getNewInfo(HttpServletRequest request) {
		TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String subjectStringLike=StaticMethod.null2String(request.getParameter("subjectStringLike"));
		String publishTimeDateGreaterThan=StaticMethod.null2String(request.getParameter("publishTimeDateGreaterThan"));
		String publishTimeDateLessThan=StaticMethod.null2String(request.getParameter("publishTimeDateLessThan"));
		String validDateGreaterThan=StaticMethod.null2String(request.getParameter("validDateGreaterThan"));
		String validDateLessThan=StaticMethod.null2String(request.getParameter("validDateLessThan")); 
		
		String subSql=" ";
		if(!"".equals(subjectStringLike)){
			subSql+=" and pi.subject like :subjectStringLike";
			subjectStringLike="%"+subjectStringLike+"%";			
		}
		if(!"".equals(publishTimeDateGreaterThan)){
			subSql+=" and pi.publishTime >= :publishTimeDateGreaterThan";
		}
		if(!"".equals(publishTimeDateLessThan)){
			subSql+=" and pi.publishTime <= :publishTimeDateLessThan";
		}
		if(!"".equals(validDateGreaterThan)){
			subSql+=" and pi.valid >= :validDateGreaterThan";
		}
		if(!"".equals(validDateLessThan)){
			subSql+=" and pi.valid <= :validDateLessThan";
		}
		
		Nop3GenericDaoImpl nop3Dao = (Nop3GenericDaoImpl) 
		  ApplicationContextHolder.getInstance().getBean("nop3GenericDao");
	 	Session session = nop3Dao.getSession();
		org.hibernate.Query query=null;
		org.hibernate.Query queryCount=null;
		
		int pageIndex=0,pageSize=5;
		
		 query=session.createQuery("select new com.boco.eoms.partner.home.model.PublishInfo(pi.id,pi.publisherId,pi.publisherName,pi.publisherDeptId,pi.publisherDeptName,pi.subject,pi.publishTime,pi.publishContent,pi.valid,pi.isAudit,pi.auditorid,pi.auditorname,pi.publishedRange,pi.publishedRangeName,pi.file,pi.type) from PublishInfo pi,PublishTask pt "		
				+" where pt.mainId=pi.id and pt.taskOwnerId=:taskOwnerId and pt.taskType=:taskType and pt.taskState=:taskState "+subSql+" order by pi.publishTime desc , pi.valid asc ");
				 query.setParameter("taskOwnerId", sessionInfo.getUserid());
				 
				 query.setParameter("taskState", StateType.TASK_RUNNING);
				 query.setFirstResult(pageIndex * pageSize);
				 query.setMaxResults(pageSize);
				 /////////
				 queryCount=session.createQuery("select count(*) from PublishInfo pi,PublishTask pt "		
				 +" where pt.mainId=pi.id and pt.taskOwnerId=:taskOwnerId and pt.taskType=:taskType and pt.taskState=:taskState "+subSql);
				 queryCount.setParameter("taskOwnerId", sessionInfo.getUserid()); 
				 queryCount.setParameter("taskState", StateType.TASK_RUNNING);
		
				 query.setParameter("taskType", StateType.OPERATE_READ);
        		 queryCount.setParameter("taskType", StateType.OPERATE_READ);		 
				
        		 if(!"".equals(subjectStringLike)){
    				 query.setParameter("subjectStringLike", subjectStringLike);
    				 queryCount.setParameter("subjectStringLike", subjectStringLike);
    			 }
    			 if(!"".equals(publishTimeDateGreaterThan)){
    				 query.setParameter("publishTimeDateGreaterThan", CommonUtils.toEomsStandardDate(publishTimeDateGreaterThan));
    				 queryCount.setParameter("publishTimeDateGreaterThan", CommonUtils.toEomsStandardDate(publishTimeDateGreaterThan));
    			 }
    			 if(!"".equals(publishTimeDateLessThan)){
    				 query.setParameter("publishTimeDateLessThan", CommonUtils.toEomsStandardDate(publishTimeDateLessThan));
    				 queryCount.setParameter("publishTimeDateLessThan", CommonUtils.toEomsStandardDate(publishTimeDateLessThan));
    			 }
    			 if(!"".equals(validDateGreaterThan)){
    				 query.setParameter("validDateGreaterThan", CommonUtils.toEomsStandardDate(validDateGreaterThan));
    				 queryCount.setParameter("validDateGreaterThan", CommonUtils.toEomsStandardDate(validDateGreaterThan));
    			 }
    			 if(!"".equals(validDateLessThan)){
    				 query.setParameter("validDateLessThan", CommonUtils.toEomsStandardDate(validDateLessThan));
    				 queryCount.setParameter("validDateLessThan", CommonUtils.toEomsStandardDate(validDateLessThan));
    			 }		 
        		 
		return 	query.list();
	}

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("公告过期数据整理，轮询开始");

		Date now=new Date();
	 	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 	String nowStr=df.format(now);	 	
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
		String updateSql = "update publicnotice_main set type = "+StateType.INFO_TYPE_EXPIRED 
									+" where valid <="+CommonSqlHelper.formatDateTime(nowStr) +" and type = 2 " ;
		System.out.println(updateSql);
		//配置 cron    0 0 3 * * ?  每天凌晨3点开始
		jdbcService.executeProcedure(updateSql);
		System.out.println("公告过期数据整理，轮询结束");
	}
}

