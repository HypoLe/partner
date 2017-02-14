	/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 *  2012-10-25 上午11:25:50
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */

package com.boco.eoms.partner.contact.webapp.action;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UUIDHexGenerator;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;
import com.boco.eoms.partner.contact.mgr.ContactLinkMgr;
import com.boco.eoms.partner.contact.mgr.ContactMainMgr;
import com.boco.eoms.partner.contact.mgr.ContactMgr;
import com.boco.eoms.partner.contact.mgr.ContactTaskMgr;
import com.boco.eoms.partner.contact.model.ContactLink;
import com.boco.eoms.partner.contact.model.ContactMain;
import com.boco.eoms.partner.contact.model.ContactTask;
import com.boco.eoms.partner.contact.util.MyUtil;
import com.boco.eoms.partner.contact.util.PageData;
import com.boco.eoms.partner.contact.util.SearchUtil;
import com.boco.eoms.partner.contact.util.Type;
import com.boco.eoms.partner.contact.webapp.form.ContactMainForm;
import com.gargoylesoftware.htmlunit.html.ClickableElement;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public class ContactAction extends BaseAction {
	/**
	 * 取得Jsp页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward  getJsp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
			String pageName =  StaticMethod.null2String(request.getParameter("pageName"));
			
			/*  草稿修改 /  信息显示 start  */
			String pageType = StaticMethod.null2String(request.getParameter("pageType"));
			String id = StaticMethod.null2String(request.getParameter("mainId"));
			ContactMgr cMgr = (ContactMgr) getBean("refcontactMgr");
			ContactMain  contactMain = cMgr.findByMainId(id);
			if("edit".equals(pageType)){
				TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
				String currentTaskId = "";
				ContactTaskMgr taskMgr = (ContactTaskMgr) getBean("refcontactTaskMgr");
				List<ContactTask> tasks = taskMgr.searchAndCount(( new Search()).addFilterEqual("mainId", id)).getResult();
				for (ContactTask contactTask : tasks) {		
				    //得到当前用户对应的TaskId
					if("1".equals(contactTask.getTaskState()+"")){//正在运行
						if(sessionInfo.getUserid().equals(contactTask.getTaskOwnerId())
								||sessionInfo.getDeptid().equals(contactTask.getTaskOwnerId()))
							currentTaskId = contactTask.getId();
					}
			    }
 
				String taskOwnerIds =  contactMain.getApprover()+","+contactMain.getPublishedRange();
				String taskOwnerNames =	  contactMain.getapproverName()+","+contactMain.getPublishedRangeName();
				request.setAttribute("taskOwnerIds",taskOwnerIds);
     			request.setAttribute("taskOwnerNames",taskOwnerNames);
     			request.setAttribute("auditName", contactMain.getapproverName());
				
				request.setAttribute("currentTaskId", currentTaskId);
			}
			/*  草稿修改 /  信息显示   end  */
			if(contactMain==null){
				contactMain =  new ContactMain();
				contactMain.setPublishTime(StaticMethod.getTimestamp());
			}
			request.setAttribute("contactMain", contactMain);
			
			return mapping.findForward(pageName);
	}
	
	
	/**
	 * 联系函 查询 列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	//com.boco.eoms.partner.contact.util.Type
	//联系函状态0草稿  -1被驳回 1审批中 2发布中 3转发中 -2终止
	public ActionForward  search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		String type =  StaticMethod.null2String(request.getParameter("type"));
		//search.getDataList(pageIndex,pageSize)的pageIdex是指页Idx，而不是第x页（pageIndex+1）
		//int pageIndex = StaticMethod.nullObject2Integer(request.getParameter("pageIndex"));
		String pageIndexName = new org.displaytag.util.ParamEncoder("listTemp")
			.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		int  pageIndex = new Integer(GenericValidator.isBlankOrNull(request.getParameter(pageIndexName))
				  ? 0: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		
		Integer pageSize=UtilMgrLocator.getEOMSAttributes().getPageSize();
		TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String userid = sessionInfo.getUserid();
		String userdept = sessionInfo.getDeptid();
		String codeStringLike = StaticMethod.null2String(request.getParameter("codeStringLike"));
		String subjectStringLike = StaticMethod.null2String(request.getParameter("subjectStringLike"));
		String publishTimeDateGreaterThan = StaticMethod.null2String(request.getParameter("publishTimeDateGreaterThan"));
		String publishTimeDateLessThan = StaticMethod.null2String(request.getParameter("publishTimeDateLessThan"));
		String publiserNameStringLike = StaticMethod.null2String(request.getParameter("publiserNameStringLike"));
		String taskOnwerNameStringLike = StaticMethod.null2String(request.getParameter("taskOnwerNameStringLike"));
		String pageName = "";
		StringBuffer formWhere = new StringBuffer();

		if(!MyUtil.isEmpty(codeStringLike)){
			formWhere.append(" and main.code like '%"+codeStringLike+"%'");
		}
		if(!MyUtil.isEmpty(subjectStringLike)){
			formWhere.append(" and main.subject like '%"+subjectStringLike+"%'");
		}
		if(!MyUtil.isEmpty(publishTimeDateGreaterThan)){
			formWhere.append(" and main.publishTime>"+CommonSqlHelper.formatDateTime(publishTimeDateGreaterThan)+"");
		}
		if(!MyUtil.isEmpty(publishTimeDateLessThan)){
			formWhere.append(" and main.publishTime < "+CommonSqlHelper.formatDateTime(publishTimeDateLessThan)+"");
		}
		if(!MyUtil.isEmpty(publiserNameStringLike)){
			formWhere.append(" and main.publisherName like '%"+publiserNameStringLike+"%'");
		}
		if(!MyUtil.isEmpty(taskOnwerNameStringLike)){
			formWhere.append(" and task.taskOwnerName like '%"+taskOnwerNameStringLike+"%'");	
		}
		System.out.println("----------"+formWhere.toString());
		
		StringBuffer sql = new StringBuffer();
      if("draft".equals(type)){
    	  //1.草稿列表
    	  sql.append("select distinct main.* from contact_main  main where publisherid='"+userid+"' and type in (0,-1)")
    	  	  .append(formWhere.toString()).append("  order by isurgent desc,main.code desc ");
    	  pageName="listDraft";
      }
      else if("todo".equals(type)){
    	  //2.待办列表
    	  /*distinct main.* 还是有用的，因为当发布范围用用户A，转发时又有A，那么A就会 有2个task对应同一个联系函，
    	   * 那么在A处理这个业务联系函时与此用户此业务联系函相关的task全部都应设置终止。 现在代码的实现是：不是一次处理2个task，
    	   * 而是要处理两次 getJsp中
    	    for (ContactTask contactTask : tasks) {
  				if("1".equals(contactTask.getTaskState()+"")){//正在运行
    				if(sessionInfo.getUserid().equals(contactTask.getTaskOwnerId()) ||sessionInfo.getDeptid().equals(contactTask.getTaskOwnerId()))
							currentTaskId = contactTask.getId();
  				}
			}
    	  */
    	  sql.append( " select distinct main.* from contact_main  main,contact_task  task ")
    		   .append(" where  main.id = task.mainid  and main.type in (1,2,3) ")
    		   .append(" and ((task.taskownerid = '"+userid+"'  and task.taskstate = 1  )")
    		   .append(" or (task.taskownerid = '"+userdept+"' and task.taskstate = 1  )) " )
    	  	   .append(formWhere.toString()).append("  order by isurgent desc,main.PUBLISHTIME desc,main.code desc ");
    	  pageName="listTodo";
      }else if("done".equals(type)){
    	  //3.已处理列表
    	  sql.append( " select  distinct main.* from contact_main  main,contact_task  task ")
    		   .append(" where  main.id = task.mainid  and main.type in (1,2,3,-2) ")//已处理中添加处理完成的单子
    		   .append(" and (( task.taskownerid = '"+userid+"' and task.taskstate = 0  )")
    		   .append(" or (task.taskownerid = '"+userdept+"'  and task.taskstate = 0  )) " )
    	  	   .append(formWhere.toString()).append("  order by isurgent desc,main.PUBLISHTIME desc,main.code desc ");
    	   pageName="listDone";
      }else if("query".equals(type)){//才用左连接，因为当保存草稿/或审批前，即还未发布，此时联系函还没有关联的任务，要显示出来需使用join。 and task.taskOwnerName like
    	  //显示自己或自己同机构所创建的联系函，或者涉及自己处理的联系函	  
    	  sql.append( " select distinct main.* from contact_main main left join contact_task task on main.id=task.mainid ")
	  	     .append(" where (   (main.PUBLISHERID='"+userid+"' or main.PUBLISHERDEPTID='"+userdept+"') ")   
	  	            .append(" or (task.taskownerid = '"+userid+"' or task.taskownerid = '"+userdept+"') )")
    	     .append(formWhere.toString()).append("  order by isurgent desc,main.PUBLISHTIME desc,main.code desc ");
    	  pageName="listQuery";
      }

		if(sql!=null){	
			StringBuilder  countSql = new StringBuilder();
			countSql.append("select  count(*) as count from ")
						.append("( ").append(sql).append(" )");
//			String slcSql = " select skip " + pageIndex.intValue()*pageSize.intValue() + " first " + pageSize+" ";
			
			SearchUtil<ContactMain> search = new SearchUtil<ContactMain>(ContactMain.class,"main", sql.toString(),countSql.toString());
			PageData<ContactMain> pageData = search.getDataList(pageIndex,pageSize);
	
			
    		List contactMainList = pageData.getList();
    	    //对流程超时状态进行标识
    		for(int i=0;i<contactMainList.size();i++){
    			ContactMain cMain = (ContactMain) contactMainList.get(i);
	            Date deadTime = cMain.getDeathTime();
	            //0:未完成，未超时,1:未完成，已超时;  2:已完成，未超时,3:已完成，已超时
    			if(cMain.getType()==-2){//cMain.getType()==-2:已完成。 已完成的超时与否
	    			/*ContactLinkMgr cLgr = (ContactLinkMgr) getBean("refcontactLinkMgr");
	    			String hql = " from ContactLink link where link.mainId = '"+ cMain.getId()+"' order by  link.operateTime desc ";
	    			List links= cLgr.findHql(hql);
	    			if(links!=null&&links.size()>0){
	    				Date firstTime =((ContactLink)links.get(0)).getOperateTime();
	    			    if(firstTime.compareTo(deadTime)<=0){
	    			    	cMain.setOverTimeFlag(2);
	    			    }else{
	    			    	cMain.setOverTimeFlag(3);
	    			    }
	    			}else{
	    				cMain.setOverTimeFlag(3);
	    			}*/
	    			
	    			//在完成时，通过设置overTimeFlag来设置，完成的联系函是否是超时完成。
	    			//在main.type=-2即联系函完成时，起作用，持久化到数据库。 main.type!=-2时，即联系函没有完成时，不起作用，通过比较当前时间与deathTime，来临时设置但是不持久化
	    		}
    			else{//未完成。 cMain.getType()==1||cMain.getType()==0||cMain.getType()==-1 ||cMain.getType()==2||cMain.getType()==3
	    			if( (new Date()).compareTo(deadTime)<0){
	    				cMain.setOverTimeFlag(0);
	    			}else{
	    				cMain.setOverTimeFlag(1);
	    			}
    			}
    		}
    		
    		request.setAttribute("contactMainList", contactMainList);
    		request.setAttribute("type", type);
    		
    		//查询条件
    		if("query".equals(type)){
	    		request.setAttribute("codeStringLike",codeStringLike);
	    		request.setAttribute("subjectStringLike",subjectStringLike);
	    		request.setAttribute("publishTimeDateGreaterThan",publishTimeDateGreaterThan);
	    		request.setAttribute("publishTimeDateLessThan",publishTimeDateLessThan);
	    		request.setAttribute("publiserNameStringLike",publiserNameStringLike);
	    		request.setAttribute("taskOnwerNameStringLike",taskOnwerNameStringLike);
    		}
    		pageIndex = pageData.getpageIndex();
    		request.setAttribute("pageSize", pageData.getPageSize());
    		request.setAttribute("resultSize", pageData.getTotalCount());	
    		request.setAttribute("pageIndex",pageIndex); 
    		request.setAttribute("pages",pageData.getTotalPage());
    		
    		request.setAttribute("first",pageIndex*pageData.getPageSize()+1);
    		int end  = (pageIndex+1)*pageData.getPageSize();
    		if(end>pageData.getTotalCount()){
    			end = pageData.getTotalCount();
    		}
    		request.setAttribute("end",end);   		
		}   
		return mapping.findForward(pageName);
	} 
	
	
	
	
	
	/**
	 * 保存草稿
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward  saveDraft(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
	
		//记录业务联系函信息
		ContactMgr cMgr = (ContactMgr) getBean("refcontactMgr");
		ContactMain cMain = this.createFormContactMain(form, request);
		cMain.setType(0);
		cMgr.saveDrafts(cMain);
		
		//生成一条对联系函的操作的记录
		ContactLinkMgr linkMgr = (ContactLinkMgr)getBean("refcontactLinkMgr");
		ContactLink cLink =  new ContactLink();
		cLink = this.createFormContactLink(form, request);
		cLink.setDisplayLinkname("draft");
		cLink.setOperateType("0");//TYPE.DRAFT=0
		cLink.setMainId(cMain.getId());
		cLink.setPublishedRange(cMain.getPublishedRange());
		linkMgr.save(cLink);
			
		ActionForward aForward=new ActionForward();
		aForward.setPath("/contact.do?method=getJsp&pageName=draftsPage&pageType=edit&mainId="+cMain.getId());
		request.setAttribute("saveSuccess", true);
		request.setAttribute("saveMessage", "保存成功");
		aForward.setRedirect(false);
		return aForward;
		//return mapping.findForward("success");
	}
	/**
	 * 送审
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward  toAudit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
			ContactMgr cMgr = (ContactMgr) getBean("refcontactMgr");
			ContactMain cMain  = this.createFormContactMain(form, request);
			cMgr.saveDrafts(cMain);
			ContactLinkMgr linkMgr = (ContactLinkMgr)getBean("refcontactLinkMgr");
			ContactLink cLink =  new ContactLink();
			cLink = this.createFormContactLink(form, request);
			cLink.setDisplayLinkname("draft");
			cLink.setOperateType("1");
			cLink.setMainId(cMain.getId());
			cLink.setPublishedRange(cMain.getPublishedRange());
			linkMgr.save(cLink);
			cMgr.toAudit(cMain,cLink.getId());
		return mapping.findForward("success");
	}
	
	
	/**
	 * 进行审批
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward  audit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
			ContactMgr cMgr = (ContactMgr) getBean("refcontactMgr");
			
			String auditContent =  StaticMethod.null2String(request.getParameter("auditContent"));
		    String auditResult =  StaticMethod.null2String(request.getParameter("auditResult"));
		    String currentTaskId = StaticMethod.null2String(request.getParameter("currentTaskId"));
		    
		    ContactLinkMgr linkMgr = (ContactLinkMgr)getBean("refcontactLinkMgr");
			ContactLink cLink =  null;
			cLink = this.createFormContactLink(form, request);
			cLink.setDisplayLinkname("approver");
			cLink.setRemark(auditContent);
            if("1".equals(auditResult)){			
			    cLink.setOperateType("2");//联系函审批通过，进入状态2：发布
            }else{
            	 cLink.setOperateType("4");//驳回
            }
			linkMgr.save(cLink);
			
			cMgr.audit(currentTaskId,cLink.getId(), auditContent, auditResult);	
		return mapping.findForward("success");
	}
	
	/**
	 * 直接发布
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward  publish(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		//记录业务联系函信息
			ContactMgr cMgr = (ContactMgr) getBean("refcontactMgr");
			ContactMain cMain  = this.createFormContactMain(form, request);
			cMain.setType(Type.PUBLISHING);
			cMgr.saveDrafts(cMain);
			
			//生成一条对联系函的操作的记录
		    ContactLinkMgr linkMgr = (ContactLinkMgr)getBean("refcontactLinkMgr");
			ContactLink cLink =  new ContactLink();
			cLink = this.createFormContactLink(form, request);
			cLink.setDisplayLinkname("draft");
            cLink.setOperateType("2");//2:发布.  
            cLink.setMainId(cMain.getId());
            cLink.setPublishedRange(cMain.getPublishedRange());
			linkMgr.save(cLink);
			
			cMgr.publish(cMain, cLink.getId());
		return mapping.findForward("success");
	}
	
	
	
	/**
	 * 处理
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward  handle(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		ContactMgr cMgr = (ContactMgr) getBean("refcontactMgr");
		String mainId =  StaticMethod.null2String(request.getParameter("mainId"));
		String type =  StaticMethod.null2String(request.getParameter("type"));
		String handleContent = StaticMethod.null2String(request.getParameter("handleContent"));
		String currentTaskId = StaticMethod.null2String(request.getParameter("currentTaskId"));
		if("end".equals(type)){
			//终止
			ContactLinkMgr linkMgr = (ContactLinkMgr)getBean("refcontactLinkMgr");
			ContactLink cLink =  new ContactLink();
			cLink = this.createFormContactLink(form, request);
			cLink.setDisplayLinkname("end");
			cLink.setOperateType("46");
			linkMgr.save(cLink);
			cMgr.endTask(currentTaskId, handleContent);
			return mapping.findForward("success");
		}else if("finlish".equals(type)){
			//阅知
			ContactLinkMgr linkMgr = (ContactLinkMgr)getBean("refcontactLinkMgr");
			ContactLink cLink =  new ContactLink();
			cLink = this.createFormContactLink(form, request);
			cLink.setDisplayLinkname("publishing");
			cLink.setOperateType("8");
			linkMgr.save(cLink);
			cMgr.finlishTask(currentTaskId, handleContent);
			return mapping.findForward("success");
		}else if("forward".equals(type)){
			//转发
			ContactMainMgr cMainMgr = (ContactMainMgr) getBean("refcontactMainMgr");
			ContactMain cMain = cMainMgr.find(mainId);
			ContactLinkMgr linkMgr = (ContactLinkMgr)getBean("refcontactLinkMgr");
			ContactLink cLink =  new ContactLink();
			cLink = this.createFormContactLink(form, request);
			cLink.setDisplayLinkname("publishing");
			cLink.setOperateType("3");
			linkMgr.save(cLink);
			String taskOwnerIds = StaticMethod.null2String(request.getParameter("taskOwnerIds"));
			String taskOwnerNames = StaticMethod.null2String(request.getParameter("taskOwnerNames"));
			
			//cMgr.forward(currentTaskId,cLink.getId(), handleContent, MyUtil.getTaskFromStr(cMain,taskOwnerIds, taskOwnerNames));
			//转发
			cMgr.forward(currentTaskId,cLink.getId(), handleContent, 
					     cMain,taskOwnerIds, taskOwnerNames);
			return mapping.findForward("success");
		}
		return mapping.findForward("fail");
		
	}
	
	
	/**
	 * 根据保单数据 创建一个 业务联系函实体
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	//这个方法仅被saveDraft(),roAudit(),publish()调用，
	//saveDraft(),toAudit(),publish()这3个方法仅被创建业务联系函者调用
	//转发(当handle()为转发时)不能修改联系函的内容，因为其只负责转。
	private ContactMain createFormContactMain(ActionForm form,HttpServletRequest request) throws Exception{
		TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		
		ContactMainForm cForm = (ContactMainForm) form;
		ContactMain cMain = (ContactMain) convert(cForm);
		String id = StaticMethod.null2String(request.getParameter("mainId"));
		
		if(id==null||id.equals("")){
		    cMain.setId(UUIDHexGenerator.getInstance().getID());
			cMain.setCode(MyUtil.getCode());
		}else{
			ContactMgr cMgr = (ContactMgr) getBean("refcontactMgr");
			ContactMain  contactMain = cMgr.findByMainId(id);
			cMain.setId(id);
			cMain.setCode(contactMain.getCode());
		}
		cMain.setPublisherId(sessionInfo.getUserid());
		cMain.setPublisherName(sessionInfo.getUsername());
		cMain.setPublisherDeptId(sessionInfo.getDeptid());
		cMain.setPublisherDeptName(sessionInfo.getDeptname());
		if(cMain.getPublishTime()==null){
		   cMain.setPublishTime(StaticMethod.getTimestamp());
		}
		String taskOwnerIds = StaticMethod.null2String(request.getParameter("taskOwnerIds"));
		String taskOwnerNames = StaticMethod.null2String(request.getParameter("taskOwnerNames"));
		String publishedRange = taskOwnerIds.substring(taskOwnerIds.indexOf(",")+1);
		String publishedRangeName = taskOwnerNames.substring(taskOwnerNames.indexOf(",")+1);
		String approver =  taskOwnerIds.substring(0,taskOwnerIds.indexOf(","));
		String approverName =  taskOwnerNames.substring(0,taskOwnerNames.indexOf(","));
		cMain.setPublishedRange(publishedRange);
		cMain.setApprover(approver);
		cMain.setPublishedRangeName(publishedRangeName);
		cMain.setapproverName(approverName);
		//加急
		//if(cMain.getIsUrgent()==1&&(id==null||id.equals("")))
		//cMain.setSubject("加急:"+cMain.getSubject());
		cMain.setSubject(cMain.getSubject());
		
		return cMain;
		
	}
	/**
	 * 根据表单数据 创建一个 业务联系函实体的流转记录
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ContactLink createFormContactLink(ActionForm form,HttpServletRequest request) throws Exception{
		TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		ContactLink cLink = new ContactLink();
		
		String   id = UUIDHexGenerator.getInstance().getID();
		String mainid = StaticMethod.nullObject2String(request.getParameter("mainId"));
		String publishedRange = StaticMethod.nullObject2String(request.getParameter("taskOwnerIds"));
		String publishedRangeNmae = StaticMethod.null2String(request.getParameter("taskOwnerNames"));
		if(publishedRange.equals(",")){
			   publishedRange = "";
			   publishedRangeNmae = "";
		}
		String remark = StaticMethod.nullObject2String(request.getParameter("handleContent"));
		String results = StaticMethod.nullObject2String(request.getParameter("auditResult"));
		
		cLink.setId(id);
		cLink.setOperateDeptId(sessionInfo.getDeptid());
		cLink.setOperateDeptName(sessionInfo.getDeptname());
		cLink.setOperateTime(StaticMethod.getTimestamp());
		cLink.setOperateUserId(sessionInfo.getUserid());
	    if(mainid!=null&&!mainid.equals("")){
			cLink.setMainId(mainid);
		}
		cLink.setPublishedRange(publishedRange);
		cLink.setPublishedRangeNmae(publishedRangeNmae);
		cLink.setRemark(remark);
		cLink.setEffect(results);
		
		return cLink;
		
	}
	/**
	 * 查询流转信息查询
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public ActionForward  searchForLink(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		int pageIndex  =  StaticMethod.nullObject2Integer(request.getParameter("pageIndex"));
		int pageSize = UtilMgrLocator.getEOMSAttributes().getPageSize();
  		
		String pageName =  StaticMethod.null2String(request.getParameter("pageName"));
	    String mainId=StaticMethod.null2String(request.getParameter("mainId"));
		
		StringBuffer sql = new StringBuffer();
   
    	  //流转信息列表
    	  /*sql.append( " select link.* from contact_link link where link.mainId='"+mainId+ "'").append("  order by link.operateTime  ");
		  ContactMgr cMgr = (ContactMgr) getBean("refcontactMgr");
		  ContactMain  contactMain = cMgr.findByMainId(mainId);
		  StringBuilder  countSql = new StringBuilder();
		  countSql.append("select count(*) as count from ").append("( ").append(sql).append(" )");
			SearchUtil<ContactLink> search = new SearchUtil<ContactLink>(ContactLink.class,"link", sql.toString(),countSql.toString());
			PageData<ContactLink> pageData = search.getDataList(pageIndex,pageSize);
			request.setAttribute("contactMain", contactMain);
			request.setAttribute("pageSize", pageData.getPageSize());
    		request.setAttribute("resultSize", pageData.getTotalCount());
    		request.setAttribute("contactLinkList", pageData.getList());
    		int resultSize =  pageData.getTotalCount();
    		 pageIndex = pageData.getpageIndex();
    		int first  = (pageIndex-1)*pageSize+1;
    		request.setAttribute("first",first);
    		int end  = (pageIndex)*pageSize;
    		if(end>resultSize){
    			end = resultSize;
    		}
    		request.setAttribute("end",end);
    		request.setAttribute("pageIndex",pageIndex);
    		int pages = pageData.getTotalPage();
    		request.setAttribute("pages",pages);*/
		
		 //流转信息一次性取出来完
		 ContactMgr cMgr = (ContactMgr) getBean("refcontactMgr");
		 ContactMain  contactMain = cMgr.findByMainId(mainId);
		 request.setAttribute("contactMain", contactMain);
		 ContactLinkMgr contactLinkMgr= (ContactLinkMgr) getBean("refcontactLinkMgr");
		 Search search=(new Search()).addFilterEqual("mainId", mainId).addSortAsc("operateTime");
		 SearchResult<ContactLink> cLinkList= contactLinkMgr.searchAndCount(search);
 		 request.setAttribute("resultSize", cLinkList.getTotalCount());
 		 request.setAttribute("contactLinkList", cLinkList.getResult());
 		 
		return mapping.findForward(pageName);
	} 
	
	
	/**
	 * 导出待处理和已处理列表当前页的数据
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public ActionForward  export(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		String type =  StaticMethod.null2String(request.getParameter("type"));
		Integer pageSize =StaticMethod.nullObject2Integer(request.getParameter("pageSize"));
		Integer pageIndex=StaticMethod.nullObject2Integer(request.getParameter("pageIndex"));
		Boolean  allYN=Boolean.parseBoolean(request.getParameter("allYN"));
		TawSystemSessionForm sessionInfo = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String userid = sessionInfo.getUserid();
		String userdept = sessionInfo.getDeptid();
	
		StringBuffer sql = new StringBuffer();
		String fileName="";
        if("todo".equals(type)){
    	  //2.待办列表
    	  sql.append( " select distinct main.* from contact_main  main,contact_task  task ")
    		 .append(" where  main.id = task.mainid  and main.type in (1,2,3) ")
		     .append(" and ((task.taskownerid = '"+userid+"'  and task.taskstate = 1  )")
    		 .append(" or (task.taskownerid = '"+userdept+"' and task.taskstate = 1  )) " )
    	     .append("  order by isurgent desc,main.PUBLISHTIME desc,main.code desc  ");
    	  fileName="待办业务联系函";
        }else if("done".equals(type)){
    	  //3.已处理列表
    	  sql.append( " select  distinct main.* from contact_main  main,contact_task  task ")
		     .append(" where  main.id = task.mainid  and main.type in (1,2,3,-2) ")//已处理中添加处理完成的单子
		     .append(" and (( task.taskownerid = '"+userid+"' and task.taskstate = 0  )")
		     .append(" or (task.taskownerid = '"+userdept+"'  and task.taskstate = 0  )) " )
    	  	 .append("  order by isurgent desc,main.PUBLISHTIME desc,main.code desc  ");
    	  fileName="已处理业务联系函";
        }else{
        	return null;
        }
	
        if(sql!=null){	
			StringBuilder  countSql = new StringBuilder();
			countSql.append("select distinct count(*) as count from ")
						.append("( ").append(sql).append(" )");
			
			List<ContactMain> contactMainList=null;
			if(!allYN){
				SearchUtil<ContactMain> search = new SearchUtil<ContactMain>(ContactMain.class,"main", sql.toString(),countSql.toString());
				PageData<ContactMain> pageData = search.getDataList(pageIndex,pageSize);
	    		contactMainList = pageData.getList();
			}else{
				contactMainList=(List<ContactMain> )SearchUtil.getDataBySql(ContactMain.class,"main", sql.toString());
			}
			
    		response.reset();
			response.setContentType("application/x-msdownload;charset=GBK");
			response.setCharacterEncoding("GB2312");
    		try {
    			response.setHeader("Content-Disposition", "attachment; filename="
    					+ new String((fileName+".xls").getBytes("gbk"), "iso8859-1"));
    			//先设置response的属性，再response.getOutputStream()
    			MyUtil.WriteExcel(contactMainList,fileName,response.getOutputStream());
    		} catch (Exception e) { 
    			e.printStackTrace();
    		}
		}
		return null;
	}
	
}
