package com.boco.eoms.sheet.base.webapp.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.displaytag.decorator.TableDecorator;

import com.boco.eoms.base.util.StaticMethod;

/**
 *  
 */
public class ProcessListDisplaytagDecoratorHelper extends TableDecorator {
    /**修改此方式，区别同组查看模式和工单处理模式 modify by 秦敏 20090903**/
    @SuppressWarnings("unchecked")
	public String getSheetId() {
    	String returnUrl="";
        Map taskMap = (HashMap) getCurrentRowObject();
        String url = (String) this.getPageContext().getAttribute("url");
        String listType=StaticMethod.nullObject2String(this.getPageContext().getAttribute("listType"));
        if(url==null){
        	url = this.getUrl(taskMap);
        }
        String sheetKey = StaticMethod.nullObject2String(taskMap.get("sheetKey")).trim();
        String taskName = StaticMethod.nullObject2String(taskMap.get("taskName")).trim();
        String id = StaticMethod.nullObject2String(taskMap.get("id")).trim();
        String operateRoleId = StaticMethod.nullObject2String(taskMap.get("operateRoleId")).trim();
        String TKID = StaticMethod.nullObject2String(taskMap.get("id")).trim();
        String processId = StaticMethod.nullObject2String(taskMap.get("processId")).trim();
        String taskStatus = StaticMethod.nullObject2String(taskMap.get("taskStatus")).trim();
        String preLinkId = StaticMethod.nullObject2String(taskMap.get("preLinkId")).trim();
        String sheetId = StaticMethod.nullObject2String(taskMap.get("sheetId")).trim();
        if(listType.equals("teamList")){
          returnUrl="<a  href='" + url
			+ "?method=showMainDetailPage&sheetKey=" + sheetKey
			+ "'>" + sheetId + "</a>";
        }else {
         returnUrl="<a  href='" + url
                + "?method=showDetailPage&sheetKey=" + sheetKey
                + "&taskId=" + id + "&taskName=" + taskName
				+ "&operateRoleId="+operateRoleId+"&TKID="+TKID
				+ "&piid="+processId
                + "&taskStatus="+taskStatus+"&preLinkId="+preLinkId+"' >" + sheetId + "</a>";
        }
        return returnUrl;
    }
    
    @SuppressWarnings("unchecked")
	public String getTaskDisplayName(){
        Map taskMap = (HashMap) getCurrentRowObject();
    	String name = StaticMethod.nullObject2String(taskMap.get("taskDisplayName")).trim();
    	String subTaskFlag = StaticMethod.nullObject2String(taskMap.get("subTaskFlag")).trim();
    	if(subTaskFlag!=null && subTaskFlag.equals("true")){
    		name = name + "(子任务)";
    	}
    	return name;
    }
    
    @SuppressWarnings("unchecked")
	public Date getSendTime(){
        Map taskMap = (HashMap) getCurrentRowObject();
    	Date sendTime = (Date)taskMap.get("sendTime");    	
    	return sendTime;
    }
    
   
    @SuppressWarnings("unchecked")
	private String getUrl(Map taskMap){
    	String url = "";
    	return url;
    }
    public String getId(){
    	return ""; 
    }
    /**
     * 超时提醒
     */
    public String getProcessId(){
    	return ""; 
    }
    /**
     * 根据超时提醒改变行的样式
     */
    @SuppressWarnings("unchecked")
	@Override
	public String addRowClass(){
    	
        Map taskMap = (HashMap) getCurrentRowObject();
        String urlstr = "";
        /*try{
	        if(OvertimeTipUtil.ifSupportOverTime(task.getFlowName())){
		        String flowName = OvertimeTipUtil.getFlowNameBytaskFlowName(task.getFlowName());
		        
		        TawSystemSessionForm sessionform = (TawSystemSessionForm)this.getPageContext().getSession().getAttribute("sessionform");
		        IOvertimeTipManager service = (IOvertimeTipManager) ApplicationContextHolder.getInstance().getBean("iOvertimeTipManager");
		        
		        String overtimeTipLimit = "0";
		        HashMap map = new HashMap();
		        
		        //得到需要匹配超时提醒的字段
			    ITawSystemWorkflowManager fmgr=(ITawSystemWorkflowManager)ApplicationContextHolder.getInstance().getBean("ITawSystemWorkflowManager");
			    TawSystemWorkflow tmpWorkflow=fmgr.getTawSystemWorkflowByName(flowName);
			    String fBeanId=tmpWorkflow.getMainServiceBeanId();
			    IMainService fMainSerivce=(IMainService)ApplicationContextHolder.getInstance().getBean(fBeanId);
			
			    BaseMain main = fMainSerivce.getSingleMainPO(task.getSheetKey());
			    map = OvertimeTipUtil.getConditionByMapping(main,fMainSerivce.getFlowTemplateName());
		        
		        //查询超时提醒的时间
				overtimeTipLimit = service.getEffectOvertimeTip(flowName,	sessionform.getUserid(), map);
			        
			    //判断该任务是否超时
				if(overtimeTipLimit!=null&&!overtimeTipLimit.equals("")){
			        Date completeTime = task.getCompleteTimeLimit();
			        if(completeTime!=null){
				        if(completeTime.before(new Date())){
				        	urlstr="serious";
				        }else if((completeTime.getTime()-new Date().getTime())/(1000*60)<=Integer.parseInt(overtimeTipLimit)){
				        	urlstr="alert colorrow";
				        }
			        }
				}
	        }
        }catch(Exception e){
        	e.printStackTrace();
        }
        */
        String overtimeType=StaticMethod.nullObject2String(taskMap.get("overtimeType")).trim();
        if(overtimeType.equals("1")){ //已超时
        	urlstr="serious";
        }else if(overtimeType.equals("2")){ //即将超时
        	urlstr="alert colorrow";
        }
        return urlstr;
    }
   /** public String getTaskOwner() {
        ITask task = (ITask) getCurrentRowObject();
        ID2NameService service = (ID2NameService) ApplicationContextHolder
           .getInstance().getBean("ID2NameGetServiceCatch");
        String name = service.id2Name(task.getTaskOwner(), "tawSystemUserDao");
        return name;
    }*/
}
