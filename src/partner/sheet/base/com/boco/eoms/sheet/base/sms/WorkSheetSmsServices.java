package com.boco.eoms.sheet.base.sms;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.boco.eoms.message.service.impl.MsgServiceImpl;
import com.boco.eoms.sheet.base.util.Constants;
import com.boco.eoms.sheet.base.util.SheetStaticMethod;

public class WorkSheetSmsServices {
	
   public WorkSheetSmsServices(){ }
     
     private static final String CONFIG_FILEPATH = "classpath:config/worksheet-sms-service-info.xml";//短信服务信息配置文件
       
   
   /**
    * @see 工单派发的时候，进行短信提醒；包括即时提醒、超时前提醒以及超时后提醒
    *      此方法一般用于普通人工任务生成时调用
    * @param workflowName 流程名称
    * @param sheetKey 工单主健ID
    * @param sheetId  工单流水号
    * @param title 工单主题
    * @param receiveType 短信接受者类型
    * @param receiverId 短信接收者Id
    * @param acceptLimitTime 接受时限
    * @param dealLimitTime 处理时限
    */
   public void workSM_T(String workflowName,String sheetKey,String sheetId,String title,int receiveType,
		                 String receiverId,String acceptLimitTime,String dealLimitTime) throws Exception {	   
     try {
    	 MsgServiceImpl   msgService = new MsgServiceImpl();
    	 String taskCnName="";
    	 //解析短信服务信息文件，获取工单中文名称，以及短信服务ID
    	 String nodeCnName=Constants.SMS_CONFIG+"."+workflowName+"."+Constants.SMS_PROCESS_CN_NAME;
    	 String nodeInstantName=Constants.SMS_CONFIG+"."+workflowName+"."+Constants.SMS_SERVICE_INSTANT;
    	 String nodePreOvertimeAccpetName=Constants.SMS_CONFIG+"."+workflowName+"."+Constants.SMS_SERVICE_PREOVERTIME_ACCEPT;
    	 String nodePreOvertimeDealName=Constants.SMS_CONFIG+"."+workflowName+"."+Constants.SMS_SERVICE_PREOVERTIME_DEAL;
    	 String nodePostOvertimeAcceptName=Constants.SMS_CONFIG+"."+workflowName+"."+Constants.SMS_SERVICE_POSTOVERTIME_ACCPT;
    	 String nodePostOvertimeDealName=Constants.SMS_CONFIG+"."+workflowName+"."+Constants.SMS_SERVICE_POSTOVERTIME_DEAL;
    	     	 
    	 String filePath = SheetStaticMethod.getFilePathForUrl(CONFIG_FILEPATH);
    	 String processCnName=SheetStaticMethod.getNodeName(filePath, nodeCnName);
    	 String instantServiceId=SheetStaticMethod.getNodeName(filePath, nodeInstantName);
    	 String preOvertimeAcceptServiceId=SheetStaticMethod.getNodeName(filePath, nodePreOvertimeAccpetName);
    	 String preOvertimeDealServiceId=SheetStaticMethod.getNodeName(filePath, nodePreOvertimeDealName);
    	 String postOvertimeAcceptServiceId=SheetStaticMethod.getNodeName(filePath, nodePostOvertimeAcceptName);
    	 String postOvertimeDealServiceId=SheetStaticMethod.getNodeName(filePath, nodePostOvertimeDealName);
    	 
    	 //从sheetKey参数中拆分中环节中文名称，modify by qinmin 2009-08-10
    	 if(!sheetKey.equals("")&&sheetKey.indexOf("#")>0) {
    		 taskCnName=sheetKey.substring(sheetKey.indexOf("#"),sheetKey.length());
    		 sheetKey=sheetKey.substring(0,sheetKey.indexOf("#"));
    	 }
    	 
    	 //拼接短信接受者
    	 String receivers="";
    	 if(receiveType==Constants.SMS_RECEIVE_TYPE_USER){
    		 receivers=receivers+ Constants.SMS_RECEIVE_TYPE_USER+","+receiverId+"#";
    	 }
    	 else if(receiveType==Constants.SMS_RECEIVE_TYPE_DEPT){
    		 receivers=receivers+ Constants.SMS_RECEIVE_TYPE_DEPT+","+receiverId+"#";
    	 }
    	 else if(receiveType==Constants.SMS_RECEIVE_TYPE_ROLE){
    		 receivers=receivers+ Constants.SMS_RECEIVE_TYPE_ROLE+","+receiverId+"#";
    	 }
    	 
    	 if(!receivers.equals("")) 
    		  receivers=receivers.substring(0,receivers.lastIndexOf("#"));
    	 
    	 System.out.println("receivers="+receivers);
	        
    	 //派发通知（即时提醒）
    	  if(!instantServiceId.equals("")){   	   
    	   //获得当前时间 
    	   Date currentTime = new Date();
    	   SimpleDateFormat formatter = new SimpleDateFormat(
    	     "yyyy-MM-dd HH:mm:ss");
    	   String sendTime = formatter.format(currentTime);
    	   
    	   //拼写发送信息
    	   String sendContent= "提醒您收取" + processCnName +":"+ sheetId + ",主题名:" + title;
    	   if(!taskCnName.equals("")) sendContent=sendContent+",处理环节："+taskCnName;
    	   if(!dealLimitTime.equals("")) sendContent=sendContent+",处理时限："+dealLimitTime;
    	 
    	   msgService.sendMsg(instantServiceId, sendContent,
    		     sheetKey, receivers, sendTime);
    	   
    	 }
    	 
    	//接单超时前提醒
    	 if(!preOvertimeAcceptServiceId.equals("")&&!acceptLimitTime.equals("")){    	        		
            String sendContent= "提醒您," + processCnName +":"+sheetId + "即将于"+acceptLimitTime+"超时，请及时接收！" ; 
    		msgService.sendMsg(preOvertimeAcceptServiceId, sendContent,
    	    		     sheetKey, receivers, acceptLimitTime);
    		
    	 }	
        //处理超时提醒	
    	 if(!preOvertimeDealServiceId.equals("")&&!dealLimitTime.equals("")){
    		   
    	    	String sendContent= "提醒您," + processCnName +":"+sheetId + "即将于"+dealLimitTime+"超时，请及时处理！" ; 
    		    msgService.sendMsg(preOvertimeDealServiceId, sendContent,
    	    		     sheetKey, receivers, dealLimitTime);
    		 
    	 }
    	
    	 //接单超时后提醒
    	 if(!postOvertimeAcceptServiceId.equals("")&&!acceptLimitTime.equals("")){    	     
     		   String sendContent= "提醒您," + processCnName +":"+sheetId + "已经超时，请及时接收！" ; 
     		   msgService.sendMsg(postOvertimeAcceptServiceId, sendContent,
     	    		     sheetKey, receivers, acceptLimitTime);
     	 }
    	 if(!postOvertimeDealServiceId.equals("")&&!dealLimitTime.equals("")){
     		   //处理即将超时提醒	
     	    	String sendContent= "提醒您," + processCnName +":"+sheetId + "已经超时，请及时处理！" ; 
     		    msgService.sendMsg(postOvertimeDealServiceId, sendContent,
     	    		     sheetKey, receivers, dealLimitTime);
     		 }   
      }
     catch (Exception e){
    	 throw new Exception("send message exception,error info is"+e.getMessage()); 
     }
    
   }   
	
   /**
    * @see  此方法用于非流程操作时派发短信，例如：催办、阶段回复、阶段建议、抄送等
    * @param workflowName 流程名称
    * @param sheetKey 工单主健ID
    * @param sheetId  工单流水号
    * @param title 工单主题
    * @param receiveType 短信接受者类型
    * @param receiverId 短信接收者Id
    */
   public void workSM_NON_T (String workflowName,String sheetKey,String sheetId,String title,int receiveType,
		                     String receiverId) throws Exception {	   
     try {
    	 MsgServiceImpl   msgService = new MsgServiceImpl();
    	 
    	 //解析短信服务信息文件，获取工单中文名称，以及短信服务ID
    	 String nodeCnName=Constants.SMS_CONFIG+"."+workflowName+"."+Constants.SMS_PROCESS_CN_NAME;
    	 String nodeInstantName=Constants.SMS_CONFIG+"."+workflowName+"."+Constants.SMS_SERVICE_INSTANT;
    	 String filePath = SheetStaticMethod.getFilePathForUrl(CONFIG_FILEPATH);
    	 String processCnName=SheetStaticMethod.getNodeName(filePath, nodeCnName);
    	 String instantServiceId=SheetStaticMethod.getNodeName(filePath, nodeInstantName);
    	 //拼接短信接受者
    	 String receivers="";
    	 if(receiveType==Constants.SMS_RECEIVE_TYPE_USER){
    		 receivers=receivers+ Constants.SMS_RECEIVE_TYPE_USER+","+receiverId+"#";
    	 }
    	 else if(receiveType==Constants.SMS_RECEIVE_TYPE_DEPT){
    		 receivers=receivers+ Constants.SMS_RECEIVE_TYPE_DEPT+","+receiverId+"#";
    	 }
    	 else if(receiveType==Constants.SMS_RECEIVE_TYPE_ROLE){
    		 receivers=receivers+ Constants.SMS_RECEIVE_TYPE_ROLE+","+receiverId+"#";
    	 }
    	 
    	 if(!receivers.equals("")) 
    		  receivers=receivers.substring(0,receivers.lastIndexOf("#"));
    	 
    	 System.out.println("receivers="+receivers);
	        
    	 //派发通知（即时提醒）
    	  if(!instantServiceId.equals("")){   	   
    	   //获得当前时间 
    	   Date currentTime = new Date();
    	   SimpleDateFormat formatter = new SimpleDateFormat(
    	     "yyyy-MM-dd HH:mm:ss");
    	   String sendTime = formatter.format(currentTime);
    	   
    	   //拼写发送信息
    	   String sendContent= "请你及时处理" + processCnName +":"+ sheetId + "。";
    	   msgService.sendMsg(instantServiceId, sendContent,
    		     sheetKey, receivers, sendTime);
    	   
    	 }   	    
      }
     catch (Exception e){
    	 throw new Exception("send message exception,error info is"+e.getMessage()); 
     }    
   } 	

   /**
    * @see 关闭短信提醒
    * @param sheetKey   工单主键ID
    * @param workflowName 流程名称
    * @param closeMsgType 关闭短信类型
    */
   public void closeMsg(String sheetKey,String workflowName,int closeMsgType) throws Exception{
	   MsgServiceImpl   msgService = new MsgServiceImpl();
	   try{
		 //解析短信服务信息文件，获取工单中文名称，以及短信服务ID

	    	 String nodeInstantName=Constants.SMS_CONFIG+"."+workflowName+"."+Constants.SMS_SERVICE_INSTANT;
	    	 String nodePreOvertimeAccpetName=Constants.SMS_CONFIG+"."+workflowName+"."+Constants.SMS_SERVICE_PREOVERTIME_ACCEPT;
	    	 String nodePreOvertimeDealName=Constants.SMS_CONFIG+"."+workflowName+"."+Constants.SMS_SERVICE_PREOVERTIME_DEAL;
	    	 String nodePostOvertimeAcceptName=Constants.SMS_CONFIG+"."+workflowName+"."+Constants.SMS_SERVICE_POSTOVERTIME_ACCPT;
	    	 String nodePostOvertimeDealName=Constants.SMS_CONFIG+"."+workflowName+"."+Constants.SMS_SERVICE_POSTOVERTIME_DEAL;
	    	     	 
	    	 String filePath = SheetStaticMethod.getFilePathForUrl(CONFIG_FILEPATH);	    	 
	    	 String instantServiceId=SheetStaticMethod.getNodeName(filePath, nodeInstantName);
	    	 String preOvertimeAcceptServiceId=SheetStaticMethod.getNodeName(filePath, nodePreOvertimeAccpetName);
	    	 String preOvertimeDealServiceId=SheetStaticMethod.getNodeName(filePath, nodePreOvertimeDealName);
	    	 String postOvertimeAcceptServiceId=SheetStaticMethod.getNodeName(filePath, nodePostOvertimeAcceptName);
	    	 String postOvertimeDealServiceId=SheetStaticMethod.getNodeName(filePath, nodePostOvertimeDealName);
	    	 	    	 
	    	 switch (closeMsgType){
	    	  case Constants.SMS_TYPE_INSTANT: {
	    		if(!instantServiceId.equals(""))  msgService.closeMsg(instantServiceId, sheetKey);
	    		break;
	    	  }
	    	  case Constants.SMS_TYPE_ACCEPT :{
	    		  if(!preOvertimeAcceptServiceId.equals(""))  msgService.closeMsg(preOvertimeAcceptServiceId, sheetKey);
	    		  if(!postOvertimeAcceptServiceId.equals(""))  msgService.closeMsg(postOvertimeAcceptServiceId, sheetKey);
	    		  break;
	    	  }
	    	  case Constants.SMS_TYPE_DEAL :{
	    		  if(!preOvertimeDealServiceId.equals("")) msgService.closeMsg(preOvertimeDealServiceId, sheetKey);
	    		  if(!postOvertimeDealServiceId.equals("")) msgService.closeMsg(postOvertimeDealServiceId, sheetKey);
	    		  break;
	    	  }
	    	  default:{
	    		  if(!instantServiceId.equals("")) msgService.closeMsg(instantServiceId, sheetKey);
	    		  if(!preOvertimeAcceptServiceId.equals("")) msgService.closeMsg(preOvertimeAcceptServiceId, sheetKey);
	    		  if(!preOvertimeDealServiceId.equals("")) msgService.closeMsg(preOvertimeDealServiceId, sheetKey);
	    		  if(!postOvertimeAcceptServiceId.equals("")) msgService.closeMsg(postOvertimeAcceptServiceId, sheetKey);
	    		  if(!postOvertimeDealServiceId.equals("")) msgService.closeMsg(postOvertimeDealServiceId, sheetKey);
	    		  break;
	    	   }
	    	 }
	       
	       
	   }
	   catch (Exception e){
		  throw new Exception("close instant message exception,error info is"+e.getMessage()); 
	   }
   }
   /**
	 * @author yyk
    * 此方法用于新业务试点工单短信提醒（资源欲载(总共3个月)的第二个月结束时进行短信提醒）
    * @param workflowName 流程名称
    * @param sheetKey 工单主健ID
    * @param sheetId  工单流水号
    * @param title 工单主题
    * @param receiveType 短信接受者类型
    * @param receiverId 短信接收者Id
    */
   public void workSM_NON_T_new (String workflowName,String sheetKey,String sheetId,int receiveType,
		                     String receiverId,String content) throws Exception {	   
     try {
    	 MsgServiceImpl   msgService = new MsgServiceImpl();
    	 
    	 //解析短信服务信息文件，获取工单中文名称，以及短信服务ID
    	 //下面是解析你调用是import com.boco.eoms.common.properties.XMLProperties.XMLPropertyFile；
    	 String nodeCnName=Constants.SMS_CONFIG+"."+workflowName+"."+Constants.SMS_PROCESS_CN_NAME;
    	 String nodeInstantName=Constants.SMS_CONFIG+"."+workflowName+"."+Constants.SMS_SERVICE_INSTANT;
    	 String filePath = SheetStaticMethod.getFilePathForUrl(CONFIG_FILEPATH);
    	 String processCnName=SheetStaticMethod.getNodeName(filePath, nodeCnName);
    	 String instantServiceId=SheetStaticMethod.getNodeName(filePath, nodeInstantName);
    	 //拼接短信接受者
    	 String receivers="";
    	 if(receiveType==Constants.SMS_RECEIVE_TYPE_USER){
    		 receivers=receivers+ Constants.SMS_RECEIVE_TYPE_USER+","+receiverId+"#";
    	 }
    	 else if(receiveType==Constants.SMS_RECEIVE_TYPE_DEPT){
    		 receivers=receivers+ Constants.SMS_RECEIVE_TYPE_DEPT+","+receiverId+"#";
    	 }
    	 else if(receiveType==Constants.SMS_RECEIVE_TYPE_ROLE){
    		 receivers=receivers+ Constants.SMS_RECEIVE_TYPE_ROLE+","+receiverId+"#";
    	 }
    	 
    	 if(!receivers.equals("")) 
    		  receivers=receivers.substring(0,receivers.lastIndexOf("#"));
    	 
    	 //System.out.println("receivers==="+receivers);
	     //System.out.println("instantServiceId==="+instantServiceId);  
    	 //派发通知（即时提醒）
    	  if(!instantServiceId.equals("")){   	   
    	   //获得当前时间 
    	   Date currentTime = new Date();
    	   SimpleDateFormat formatter = new SimpleDateFormat(
    	     "yyyy-MM-dd HH:mm:ss");
    	   String sendTime = formatter.format(currentTime);
    	   
    	   //拼写发送信息
    	  //String sendContent="您启动的新业务试点工单" + sheetId + "调用的新产品编号尚没有启动相关的正式实施工单，请您核查!";
    	  String sendContent="包含这个"+content+"编号的新业务试点工单将要到期，工单主题:"+ sheetId;
    	  //System.out.println("sendContent====="+sendContent);
    	   msgService.sendMsg(instantServiceId, sendContent, sheetKey, receivers, sendTime);
    	   
    	 }   	    
      }
     catch (Exception e){
    	 throw new Exception("send message exception,error info is"+e.getMessage()); 
     }    
   } 	
}
