package com.boco.eoms.sheet.base.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.commons.system.dict.exceptions.DictServiceException;
import com.boco.eoms.commons.system.dict.model.DictItemXML;
import com.boco.eoms.commons.system.dict.service.IDictService;
import com.boco.eoms.sheet.base.model.BaseMain;
import com.boco.eoms.sheet.base.qo.DataBaseTypeAdapterQO;
import com.boco.eoms.sheet.base.task.ITask;
import com.boco.eoms.sheet.overtimetip.util.OvertimeTipUtil;

/**
 * 
 * @author Administrator
 *
 */
public class QuerySqlInit {
	/**
	 * 返回待办工单的SQL语句，不含排序条件。
	 * @param condition
	 * @param flowName
	 * @return
	 */
    public static String getUndoTaskSql(Map condition,String flowName){
    	BaseMain mainObject = (BaseMain) condition.get("mainObject");
    	ITask taskObject = (ITask) condition.get("taskObject"); 
    	String userId = (String) condition.get("userId"); 
    	String deptId = (String) condition.get("deptId");       	
		HashMap cloumnMap = OvertimeTipUtil.getAllMainColumnByMapping(flowName);
		Iterator it = cloumnMap.keySet().iterator();
		String ruternColumnName = "";
		while(it.hasNext()){
			String tmpOvertimeClm = (String)it.next();
			ruternColumnName+=" main."+(String)cloumnMap.get(tmpOvertimeClm)+",";
		}
		if(!ruternColumnName.equals("")){
			ruternColumnName = ","+ruternColumnName.substring(0,ruternColumnName.length()-1);
		}
		ruternColumnName = ruternColumnName+"";
		String hql = "select distinct task" + ruternColumnName + " from "
		+ taskObject.getClass().getName()+ " as task,"
		+ mainObject.getClass().getName()+ " as main "
		+ "where ((task.taskOwner='" + userId + "' or task.taskOwner='" + deptId + "')"
		+ " or task.taskOwner in (select userrefrole.subRoleid from TawSystemUserRefRole as userrefrole where userrefrole.userid='" + userId + "'))"
		+ " and (task.taskStatus=" + Constants.TASK_STATUS_READY 
		+ " or task.taskStatus=" + Constants.TASK_STATUS_CLAIMED
		+ " or task.taskStatus=" + Constants.TASK_STATUS_INACTIVE + ")"
		+ " and task.taskDisplayName<>'草稿'"
		+ " and main.id=task.sheetKey and main.deleted<>'1' and main.status="+Constants.SHEET_RUN;
    	return hql;
    }
    /**
     *  返回草稿列表工单的SQL语句，不含排序条件
     * @param condition
     * @param flowName
     * @return
     */
    public static String getDraftTaskSql(Map condition,String flowName){
    	BaseMain mainObject = (BaseMain) condition.get("mainObject");
    	ITask taskObject = (ITask) condition.get("taskObject"); 
    	String userId = (String) condition.get("userId"); 
		HashMap cloumnMap = OvertimeTipUtil.getAllMainColumnByMapping(flowName);
		Iterator it = cloumnMap.keySet().iterator();
		String ruternColumnName = "";
		while(it.hasNext()){
			String tmpOvertimeClm = (String)it.next();
			ruternColumnName+=" main."+(String)cloumnMap.get(tmpOvertimeClm)+",";
		}
		if(!ruternColumnName.equals("")){
			ruternColumnName = ","+ruternColumnName.substring(0,ruternColumnName.length()-1);
		}
		String hql = "select distinct task" + ruternColumnName + " from "
		+ taskObject.getClass().getName()+ " as task,"
		+ mainObject.getClass().getName()+ " as main "
		+"where task.sheetKey=main.id "
		+" and ( task.taskStatus="+Constants.TASK_STATUS_READY+" or task.taskStatus="
		+ Constants.TASK_STATUS_CLAIMED + " or task.taskStatus="+Constants.TASK_STATUS_INACTIVE+" ) " 
		+" and task.taskDisplayName = '草稿'"
		+" and main.sendUserId = '" +  userId +"'"
		+" and task.ifWaitForSubTask='"+Constants.SUB_TASK_FLAG_F+"'"
		+" and main.deleted<>'1'";
    	return hql;
    }    
    /**
     * 返回处理工单的SQL语句
     * @param condition
     * @param flowName
     * @return
     */
    public static String getDoneTaskSql(Map condition){
    	BaseMain mainObject = (BaseMain) condition.get("mainObject");
    	ITask taskObject = (ITask) condition.get("taskObject"); 
    	String userId = (String) condition.get("userId"); 
    	String deptId = (String) condition.get("deptId");     
    	String beanName = StaticMethod.nullObject2String(condition.get("beanName"));         	
        String variables = QuerySqlInit.getAllDictItemsName(beanName);
		String hql=" select distinct "+variables+" from "+ mainObject.getClass().getName()+" as main, "
		+ taskObject.getClass().getName()+ " as task" 
		+ " where task.sheetKey=main.id and ((task.taskOwner='"	+ userId + "'" + " or task.taskOwner='" + deptId	+ "')"
		+ " or task.taskOwner in (select userrefrole.subRoleid from TawSystemUserRefRole as userrefrole where userrefrole.userid='" + userId + "'))"
		+ " and (task.taskStatus='"+Constants.TASK_STATUS_FINISHED+"' or (task.taskStatus<>'"+Constants.TASK_STATUS_FINISHED+"' and task.ifWaitForSubTask='true' and task.id not in (select task1.parentTaskId from "+taskObject.getClass().getName()+" as task1 where task1.sheetKey=main.id and task1.subTaskFlag='true' and (task1.subTaskDealFalg='false' or task1.subTaskDealFalg is null ) and task1.taskStatus='"+Constants.TASK_STATUS_FINISHED+"')))"
		+ " and main.deleted<>'1' and main.status="+Constants.SHEET_RUN + " ";
    	return hql;
    }  
    
    /**
     * 返回延期申请的工单SQL语句
     * @param condition
     * @param flowName
     * @return
     */
    public static String deferAppListSql(Map condition){
    	BaseMain mainObject = (BaseMain) condition.get("mainObject");
    	ITask taskObject = (ITask) condition.get("taskObject"); 
    	String userId = (String) condition.get("userId"); 
    	String deptId = (String) condition.get("deptId");       	
		String hql=" select distinct main from "+ mainObject.getClass().getName()+" as main, "
		+ taskObject.getClass().getName()+ " as task" 
		+ " where task.sheetKey=main.id and ((task.taskOwner='"	+ userId + "'" + " or task.taskOwner='" + deptId	+ "')"
		+ " or task.taskOwner in (select userrefrole.subRoleid from TawSystemUserRefRole as userrefrole where userrefrole.userid='" + userId + "'))"
		+ " and main.deleted<>'1' and main.mainDelayFlag=1"
		+ " and main.t1maindeferappdate >= " + new DataBaseTypeAdapterQO ().getDateTypeAdapter(StaticMethod.date2String(new Date())) ;
    	return hql;
    }  
    
    /**
     * 返回本角色的已被组内其他人员抢单但未处理的工单的SQL
     * 修改为同组处理模式-返回本人、本人所属部门以及本人所属角色所有待办工单， modify by 秦敏 20090909
     * @param condition
     * @param flowName
     * @return
     */
    public static String getAcceptTaskByRole(Map condition){
    	BaseMain mainObject = (BaseMain) condition.get("mainObject");
    	ITask taskObject = (ITask) condition.get("taskObject"); 
    	String userId = (String) condition.get("userId");    
    	String deptId = StaticMethod.nullObject2String(condition.get("deptId"));  
    	String flowName = StaticMethod.nullObject2String(condition.get("flowName"));    
    	String taskName =  StaticMethod.nullObject2String(condition.get("taskName"));
    	String operateType = StaticMethod.nullObject2String(condition.get("operateType"));      
		HashMap cloumnMap = OvertimeTipUtil.getAllMainColumnByMapping(flowName);
		Iterator it = cloumnMap.keySet().iterator();
		String ruternColumnName = "";
		while(it.hasNext()){
			String tmpOvertimeClm = (String)it.next();
			ruternColumnName+=" main."+(String)cloumnMap.get(tmpOvertimeClm)+",";
		}
		if(!ruternColumnName.equals("")){
			ruternColumnName = ","+ruternColumnName.substring(0,ruternColumnName.length()-1);
		}      	
		String hql = "select distinct task" + ruternColumnName + " from "
		+ taskObject.getClass().getName()+ " as task,"
		+ mainObject.getClass().getName()+ " as main "
		+ " where task.sheetKey=main.id" 
		/**修改同组处理模式， modify by 秦敏 20090909 begin**/
//		+ " and (task.taskOwner<>'"+userId+"'" ;
//	
//		 if (operateType != null && operateType.equals("dept")) {
//			hql = hql + " and task.operateRoleId in (select deptid  from TawSystemUser as user where user.userid='" + userId + "'))";
//		} else {
//			hql = hql + " and task.operateRoleId in (select userrefrole.subRoleid from TawSystemUserRefRole as userrefrole where userrefrole.userid='" + userId + "'))";
//		}
//		hql = hql + " and task.taskStatus=" +Constants.TASK_STATUS_CLAIMED ;
		
		
		+ " and (task.operateRoleId='" + userId + "' or task.operateRoleId='" + deptId + "'"
		+ " or task.operateRoleId in (select userrefrole.subRoleid from TawSystemUserRefRole as userrefrole where userrefrole.userid='" + userId + "'))"
		+ " and (task.taskStatus=" + Constants.TASK_STATUS_READY 
		+ " or task.taskStatus=" + Constants.TASK_STATUS_CLAIMED
		+ " or task.taskStatus=" + Constants.TASK_STATUS_INACTIVE + ")";
		/**修改同组处理模式， modify by 秦敏 20090909 end**/
		if (taskName != null && !taskName.equals("")) {
			hql = hql + " and task.taskName='"+taskName+"'";
		}
		
		hql = hql + " and task.taskDisplayName<>'草稿'"
		+ " and main.deleted<>'1' and main.status="+Constants.SHEET_RUN
		+ " and (task.ifWaitForSubTask='false' or (task.ifWaitForSubTask='true' and task.id in (select task1.parentTaskId from "+taskObject.getClass().getName()+" as task1 where task1.sheetKey=main.id and task1.subTaskFlag='true' and (task1.subTaskDealFalg='false' or task1.subTaskDealFalg is null ) and task1.taskStatus='"+Constants.TASK_STATUS_FINISHED+"')))"
		;

    	return hql;
    }  

    
	/**
	 * 返回待归档工单的SQL语句，不含排序条件。
	 * @param condition
	 * @param flowName
	 * @return
	 */
    public static String getUnHoldTaskSql(Map condition,String flowName){
    	BaseMain mainObject = (BaseMain) condition.get("mainObject");
    	ITask taskObject = (ITask) condition.get("taskObject"); 
    	String userId = (String) condition.get("userId"); 
    	String deptId = (String) condition.get("deptId");     
    	String holdTaskName = StaticMethod.nullObject2String(condition.get("holdTaskName"));       	
		HashMap cloumnMap = OvertimeTipUtil.getAllMainColumnByMapping(flowName);
		Iterator it = cloumnMap.keySet().iterator();
		String ruternColumnName = "";
		while(it.hasNext()){
			String tmpOvertimeClm = (String)it.next();
			ruternColumnName+=" main."+(String)cloumnMap.get(tmpOvertimeClm)+",";
		}
		if(!ruternColumnName.equals("")){
			ruternColumnName = ","+ruternColumnName.substring(0,ruternColumnName.length()-1);
		}
		String hql = "select distinct task" + ruternColumnName + " from "
		+ taskObject.getClass().getName()+ " as task,"
		+ mainObject.getClass().getName()+ " as main "
		+ "where ((task.taskOwner='" + userId + "' or task.taskOwner='" + deptId + "')"
		+ " or task.taskOwner in (select userrefrole.subRoleid from TawSystemUserRefRole as userrefrole where userrefrole.userid='" + userId + "'))"
		+ " and (task.taskStatus=" + Constants.TASK_STATUS_READY 
		+ " or task.taskStatus=" + Constants.TASK_STATUS_CLAIMED
		+ " or task.taskStatus=" + Constants.TASK_STATUS_INACTIVE + ")"
		+ " and task.taskDisplayName<>'草稿'"		
		+ " and main.id=task.sheetKey and main.deleted<>'1' and main.status="+Constants.SHEET_RUN
		+ " and (task.ifWaitForSubTask='false' or (task.ifWaitForSubTask='true' and task.id in (select task1.parentTaskId from "+taskObject.getClass().getName()+" as task1 where task1.sheetKey=main.id and task1.subTaskFlag='true' and (task1.subTaskDealFalg='false' or task1.subTaskDealFalg is null ) and task1.taskStatus='"+Constants.TASK_STATUS_FINISHED+"'))) "
		;
		if(!holdTaskName.equals("")){
			if(holdTaskName.indexOf(",")>0){
				hql = hql+" and task.taskName in ('"+holdTaskName.replaceAll(",", "','")+"')";
			}else{
				hql = hql +" and task.taskName = '"+holdTaskName+"'";
			}
		}
    	return hql;
    } 

	/**
	 * 返回带过滤条件的待处理工单的SQL语句，不含排序条件。
	 * @param condition
	 * @param flowName
	 * @return
	 */
    public static String getUndoListByFilterSql(Map condition,String flowName){
    	BaseMain mainObject = (BaseMain) condition.get("mainObject");
    	ITask taskObject = (ITask) condition.get("taskObject"); 
    	String userId = (String) condition.get("userId"); 
    	String deptId = (String) condition.get("deptId");     
    	String filterName = StaticMethod.nullObject2String(condition.get("filterName"));       	
		HashMap cloumnMap = OvertimeTipUtil.getAllMainColumnByMapping(flowName);
		Iterator it = cloumnMap.keySet().iterator();
		String ruternColumnName = "";
		while(it.hasNext()){
			String tmpOvertimeClm = (String)it.next();
			ruternColumnName+=" main."+(String)cloumnMap.get(tmpOvertimeClm)+",";
		}
		if(!ruternColumnName.equals("")){
			ruternColumnName = ","+ruternColumnName.substring(0,ruternColumnName.length()-1);
		}
		String hql = "select distinct task" + ruternColumnName + " from "
		+ taskObject.getClass().getName()+ " as task,"
		+ mainObject.getClass().getName()+ " as main "
		+ "where ((task.taskOwner='" + userId + "' or task.taskOwner='" + deptId + "')"
		+ " or task.taskOwner in (select userrefrole.subRoleid from TawSystemUserRefRole as userrefrole where userrefrole.userid='" + userId + "'))"
		+ " and (task.taskStatus=" + Constants.TASK_STATUS_READY 
		+ " or task.taskStatus=" + Constants.TASK_STATUS_CLAIMED
		+ " or task.taskStatus=" + Constants.TASK_STATUS_INACTIVE + ")"
		+ " and task.taskDisplayName<>'草稿'"		
		+ " and main.id=task.sheetKey and main.deleted<>'1' and main.status="+Constants.SHEET_RUN
		+ " and (task.ifWaitForSubTask='false' or (task.ifWaitForSubTask='true' and task.id in (select task1.parentTaskId from "+taskObject.getClass().getName()+" as task1 where task1.sheetKey=main.id and task1.subTaskFlag='true' and (task1.subTaskDealFalg='false' or task1.subTaskDealFalg is null ) and task1.taskStatus='"+Constants.TASK_STATUS_FINISHED+"'))) "
		;
		if(!filterName.equals("")){
			if(filterName.indexOf(",")>0){
				hql = hql+" and task.taskName not in ('"+filterName.replaceAll(",", "','")+"')";
			}else{
				hql = hql +" and task.taskName <> '"+filterName+"'";
			}
		}
    	return hql;
    }     
    
    
	/**
	 * 获取派发给本角色的未被抢单的工单列表SQl。
	 * 修改此方法，改变成待处理工单同组查看模式，即是查看本人、本人所在部门以及本人所在角色所有待处理工单。
	 * modify by 秦敏 20090902
	 * @param condition
	 * @param flowName
	 * @return
	 */
    public static String getUndoTaskByRoleSql(Map condition,String flowName){
    	BaseMain mainObject = (BaseMain) condition.get("mainObject");
    	ITask taskObject = (ITask) condition.get("taskObject"); 
    	String userId = (String) condition.get("userId"); 
    	String deptId = (String) condition.get("deptId");       	
		HashMap cloumnMap = OvertimeTipUtil.getAllMainColumnByMapping(flowName);
		Iterator it = cloumnMap.keySet().iterator();
		String ruternColumnName = "";
		while(it.hasNext()){
			String tmpOvertimeClm = (String)it.next();
			ruternColumnName+=" main."+(String)cloumnMap.get(tmpOvertimeClm)+",";
		}
		if(!ruternColumnName.equals("")){
			ruternColumnName = ","+ruternColumnName.substring(0,ruternColumnName.length()-1);
		}
		String hql = "select distinct task" + ruternColumnName + " from "
		+ taskObject.getClass().getName()+ " as task,"
		+ mainObject.getClass().getName()+ " as main "	
        /**修改查询条件，modify by 秦敏 20090902 begin**/
//		+" where task.operateRoleId=task.taskOwner and task.operateRoleId in (select userrefrole.subRoleid from TawSystemUserRefRole as userrefrole where userrefrole.userid='" + userId + "')"
//		+" and task.taskStatus="+Constants.TASK_STATUS_READY 
//		+" and task.taskDisplayName<>'草稿'"
//		+" and main.id=task.sheetKey and main.deleted<>'1'";
		+ " where main.id=task.sheetKey and " 
		+ "( task.operateRoleId ='"+userId+"' or task.operateRoleId='"+deptId+"'"
		+ " or task.operateRoleId in (select userrefrole.subRoleid from TawSystemUserRefRole as userrefrole where userrefrole.userid='"+userId+"'))"
		+ " and (task.taskStatus=" + Constants.TASK_STATUS_READY 
		+ " or task.taskStatus=" + Constants.TASK_STATUS_CLAIMED
		+ " or task.taskStatus=" + Constants.TASK_STATUS_INACTIVE + ")"
		+" and task.taskDisplayName<>'草稿' and main.deleted<>'1' and main.status="+Constants.SHEET_RUN;
		/**修改查询条件，modify by 秦敏 20090902 end**/
		return hql;
    }  
    
    /**
     * 返回本角色处理工单的SQL语句
     * 修改此方法，改变成已处理工单同组查看/处理模式，即是查看本人、本人所在部门以及本人所在角色所有已处理工单。
     * 若taskName不为空 ，则是同组处理模式；若taskName为空，则是同组查看模式
	 * modify by 秦敏 20090902
     * @param condition
     * @param flowName
     * @return
     */
    public static String getDoneTaskByRoleSql(Map condition){
    	BaseMain mainObject = (BaseMain) condition.get("mainObject");
    	ITask taskObject = (ITask) condition.get("taskObject"); 
    	String userId = (String) condition.get("userId"); 
    	String deptId = (String) condition.get("deptId");  
    	String beanName = StaticMethod.nullObject2String(condition.get("beanName"));   
    	String taskName = StaticMethod.nullObject2String(condition.get("taskName"));
        String variables = QuerySqlInit.getAllDictItemsName(beanName);    	
        
        
        
		String hql=" select distinct "+variables+" from "+ mainObject.getClass().getName()+" as main,"
		           + taskObject.getClass().getName()+ " as task "    	
				   + " where task.sheetKey=main.id and " 
				   /**修改查询条件，modify by 秦敏 20090902 begin**/
//					//排除所有本人处理的工单
//					//+ " and (task.taskOwner<>'"+userId+"'" 
//					+ " and main.id not in (select task1.sheetKey from "+taskObject.getClass().getName()+" as task1 where task1.taskOwner = '" + userId + "')"
//					//由于建立了索引，将查询角色改为子查询方式 modify by jialei 2009-02-25
//					//+ " and task.operateRoleId=userrefrole.subRoleid and userrefrole.userid='"+userId+"')" 
//					+ " and task.operateRoleId in (select userrefrole.subRoleid from TawSystemUserRefRole as userrefrole where userrefrole.userid='" + userId + "')"
//					+ " and (task.taskStatus="+Constants.TASK_STATUS_FINISHED +" or task.taskStatus=" +Constants.TASK_STATUS_CLAIMED + ")"
//					+ " and task.taskDisplayName<>'草稿'"
//					+ " and main.deleted<>'1'";
				    + "( task.operateRoleId ='"+userId+"' or task.operateRoleId='"+deptId+"'"
					+ " or task.operateRoleId in (select userrefrole.subRoleid from TawSystemUserRefRole as userrefrole where userrefrole.userid='"+userId+"'))"
					+ " and task.taskStatus=" + Constants.TASK_STATUS_FINISHED 
					+" and task.taskDisplayName<>'草稿' and main.deleted<>'1' and  main.status="+Constants.SHEET_RUN;
					/**修改查询条件，modify by 秦敏 20090902 end**/
		  if (taskName != null && !taskName.equals("")) {
			 hql = hql + " and task.taskName='"+taskName+"'";
		  } 
    	 return hql;
    } 

    public static String getAllDictItemsName(String  beanName){
		IDictService service = (IDictService) ApplicationContextHolder
		.getInstance().getBean("DictService");  
		List commonList = new ArrayList();
		List privateList = new ArrayList();		
		StringBuffer variables = new StringBuffer();

			 try {
				commonList = service.getDictItems("dict-mainlistquery-common#common");				
			} catch (DictServiceException e) {
				// TODO Auto-generated catch block
				BocoLog.info(QuerySqlInit.class.getClass(), "没有配置已处理工单列表公共查询字段！");				
			}
             for(int i =0;commonList!=null && !commonList.isEmpty() && i < commonList.size(); i ++)	{
            	 DictItemXML dictItemXML= (DictItemXML)commonList.get(i);
          		if(i==0){
          			variables.append(dictItemXML.getItemName());
          		}else{
          		variables.append(","+dictItemXML.getItemName());  
          		}         		
             }	
             
             
			if(! beanName.equals("")){
			 try {
				privateList = service.getDictItems("dict-mainlistquery-common#"+beanName+"");
			} catch (DictServiceException e) {
				// TODO Auto-generated catch block
				BocoLog.info(QuerySqlInit.class.getClass(), "该工单没有配置特殊选查询字段！");
			}		
             for(int i =0;privateList!=null &&!privateList.isEmpty()&& i < privateList.size(); i ++)	{	
            	 DictItemXML privatedictItemXML= (DictItemXML)privateList.get(i);            	 
          		variables.append(","+privatedictItemXML.getItemName());  

             }
			}	
			return variables.toString();
    }    
    
    public static String getHoldedListForUserSql(Map condition){
    	BaseMain mainObject = (BaseMain) condition.get("mainObject");
    	ITask taskObject = (ITask) condition.get("taskObject"); 
    	String userId = (String) condition.get("userId"); 
    	String deptId = (String) condition.get("deptId");     
    	String beanName = StaticMethod.nullObject2String(condition.get("beanName"));         	
        String variables = QuerySqlInit.getAllDictItemsName(beanName);
		String hql=" select distinct "+variables+" from "+ mainObject.getClass().getName()+" as main, "
		+ taskObject.getClass().getName()+ " as task" 
		+ " where task.sheetKey=main.id and ((task.taskOwner='"	+ userId + "'" + " or task.taskOwner='" + deptId	+ "')"
		+ " or task.taskOwner in (select userrefrole.subRoleid from TawSystemUserRefRole as userrefrole where userrefrole.userid='" + userId + "'))"
		+ " and main.deleted<>'1' and main.status="+Constants.SHEET_HOLD + " ";
    	return hql;
    }      
}
