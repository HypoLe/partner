/*
 * Created on 2007-8-3
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.boco.eoms.sheet.base.adapter.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.eoms.partner.sheet.base.process.model.*;

/**
 * <p>
 * Title:流程相关adapter API
 * 
 * </p>
 * <p>
 * Description:流程相关adapter API,由本地service(WPSBusinessFlowAdapterServiceImpl)调用
 * </p>
 * <p>
 * Date:2007-8-3 14:10:05
 * </p>
 * 
 * @author 曲静波
 * @version 1.0
 *  
 */
public interface IBusinessFlowAdapterService {
	

	/**
	 * 完成人工任务
	 * 
	 * @param activityId
	 * @param parameters
	 * @throws Exception
	 */
	public void finishTask(String activityId, HashMap parameters,
			HashMap sessionMap) throws Exception;


	/**
	 * 完成人工任务
	 * 
	 * @param activityId
	 * @param parameters
	 * @throws Exception
	 */
	public void finishTask(String activityId, FinishTaskData finishTaskData,
			HashMap sessionMap) throws Exception;

	/**
	 * 启动流程
	 * 
	 * @param processTemplateName
	 *            流程模板名称
	 * @param operationName
	 *            初始操作名称
	 * @param parameters
	 *            流程入口参数
	 * @return
	 * @throws Exception
	 */
	public String triggerProcess(String processTemplateName,
			String operationName, HashMap parameters, HashMap sessionMap)
			throws Exception;

	public String triggerProcess(String processTemplateName,
			String operationName, TriggerProcessData triggerProcessData, HashMap sessionMap) throws Exception;
	/**
	 * 
	 * @param identifier
	 * @param variableName
	 * @param sessionMap
	 * @return
	 * @throws Exception
	 */
	public Map getVariable(String piid, String variableName,HashMap sessionMap) throws Exception;
	
	/**
	 * 声明一个任务
	 * 
	 * @param tkid
	 * @param sessionMap
	 * @throws Exception
	 */
	public void claimTask(String tkid,HashMap parameters, HashMap sessionMap) throws Exception;
	
	/**
	 * 声明一个任务
	 * 
	 * @param tkid
	 * @param sessionMap
	 * @throws Exception
	 */
	public void claimTask(String tkid,ClaimTaskData claimTaskData, HashMap sessionMap) throws Exception;

	/**
	 * 创建一个子任务
	 * 
	 * @param tkid
	 *            任务模板的任务ID
	 * @param parameters
	 *            子任务的参数
	 * @param sessionMap
	 *            用户的登陆信息
	 * @throws Exception
	 */
	public void createSubTask(String tkid, String parentTaskId, String subTaskName, String taskNameSpace, String parentTaskName, HashMap parameters,
			HashMap sessionMap) throws Exception;
	
	/**
	 * 创建一个子任务
	 * 
	 * @param tkid
	 *            任务模板的任务ID
	 * @param parameters
	 *            子任务的参数
	 * @param sessionMap
	 *            用户的登陆信息
	 * @throws Exception
	 */
	public void createSubTask(CreateSubTaskData createSubTaskData,
			HashMap sessionMap) throws Exception;


	/**
	 * 获取节点事件接口列表
	 * 
	 * @param tkid
	 * @param sessionMap
	 * @throws Exception
	 */
	public List getActiveEventHandlers(String piid, HashMap sessionMap)
			throws Exception;

	
	/**
	 * 触发事件
	 * 
	 * @param taskId
	 *            任务ID
	 * @param task
	 *            taskModel对象
	 * @param sessionMap
	 * @throws Exception
	 */
	public String triggerEvent(String piid, String processTemplateName,
			String operationName, HashMap parameters, HashMap sessionMap)
			throws Exception;
	/**
	 * 工单移交
	 * 
	 * @param tkid
	 * @param sessionMap TODO
	 * @param sessionMap
	 * @throws Exception
	 */
	public void transferWorkItem(String tkid, String fromOwnerUserId,String fromOwnerRoleId,
			String toOwnerUserId,String toOwnerRoleId,String operOrgType,HashMap map,HashMap sessionMap) throws Exception;
	
	/**
	 * 工单移交
	 * 
	 * @param tkid
	 * @param sessionMap TODO
	 * @param sessionMap
	 * @throws Exception
	 */
	public void transferWorkItem(String tkid, TransferWorkItemData transferWorkItemData,HashMap sessionMap) throws Exception;
	
    /**
     * 流程回调
     * @param piid
     * @param operationName
     * @param parameters
     * @param sessionMap
     * @throws Exception
     */
	public void reInvokeProcess( String piid, String operationName,
			 HashMap parameters, HashMap sessionMap) throws Exception;
	/**
	 * 取消申请一个任务
	 * 
	 * @param tkid
	 * @param sessionMap
	 * @throws Exception
	 */
	public void cancelClaimTask(String tkid,HashMap parameters, HashMap sessionMap) throws Exception;
	
	
	/**
	 * @author yyk
	 * 结束流程实例
	 * @param processId
	 * @param 流程实例id
	 * @throws Exception
	 */
	public void toTerminateProcess(String piid, HashMap sessionMap) throws Exception;	

	/**
	 * @author yyk
	 * 2009-11-17
	 * 是否存在和输入参数operationName名称相同的事件
	 * TriggerEventName
	 */
	public boolean ifExistTriggerEventName(final String piid, final String operationName, HashMap sessionMap) throws Exception;	
}
