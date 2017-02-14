/*
 * Created on 2007-8-3
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.boco.eoms.sheet.base.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.boco.eoms.partner.sheet.base.process.model.*;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: wps
 * </p>
 * <p>
 * Date:2007-8-3 14:10:05
 * </p>
 * 
 * @author 曲静波
 * @version 1.0
 *  
 */
public interface IBusinessFlowService {
	/**
	 * 
	 * @param processTemplateName
	 * @param OperateName
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public abstract String triggerProcessByMap(String processTemplateName,
			String OperateName, HashMap map, HashMap<String,String> sessionMap)
			throws Exception;
	
	/**
	 * 
	 * @param processTemplateName
	 * @param OperateName
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public abstract String triggerProcess(String processTemplateName,
			String OperateName, TriggerProcessData triggerProcessData, HashMap<String,String> sessionMap)
			throws Exception;
	
	/**
	 * 
	 * @param identifier
	 * @param variableName
	 * @param sessionMap
	 * @return
	 * @throws Exception
	 */
	public Map getVariable(String identifier, String variableName, HashMap<String,String> sessionMap) throws Exception;

	/**
	 * 完成人工任务的api，主要调用引擎中的具体方法，来完成人工任务
	 * 
	 * @param activityId
	 *            人工任务对应的实例号，类似与3.0里的taskId
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	public abstract String finishTaskByMap(String activityId,
			HashMap parameters, HashMap<String,String> sessionMap) throws Exception;


	
	/**
	 * 完成人工任务的api，主要调用引擎中的具体方法，来完成人工任务
	 * 
	 * @param activityId
	 *            人工任务对应的实例号，类似与3.0里的taskId
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	public abstract String finishTask(String activityId,
			FinishTaskData finishTaskData, HashMap<String,String> sessionMap) throws Exception;


	/**
	 * 申明一个任务
	 * 
	 * @param tkid
	 * @param sessionMap
	 * @throws Exception
	 */
	public void claimTaskByMap(String tkid,HashMap parameters, HashMap<String,String> sessionMap) throws Exception;

	/**
	 * 申明一个任务
	 * @param tkid
	 * @param claimTaskData
	 * @param sessionMap
	 * @throws Exception
	 */
	public void claimTask(String tkid,ClaimTaskData claimTaskData, HashMap<String,String> sessionMap) throws Exception;
	
	/**
	 * 创建一个子任务
	 * 
	 * @param tkid
	 * @param parentTaskId TODO
	 * @param subTaskName TODO
	 * @param taskNameSpace TODO
	 * @param parentTaskName TODO
	 * @param sessionMap
	 * @throws Exception
	 */
	public void createSubTaskByMap(String tkid,String parentTaskId,String subTaskName, String taskNameSpace, String parentTaskName, HashMap parameters, HashMap<String,String> sessionMap) throws Exception;

	/**
	 * 获取节点事件接口列表
	 * 
	 * @param oiid
	 * @param sessionMap
	 * @throws Exception
	 */
	public List getActiveEventHandlers(String piid, HashMap<String,String> sessionMap)
			throws Exception;

	/**
	 * 触发EventHandle
	 * 
	 * @param oiid
	 * @param sessionMap
	 * @throws Exception
	 */
	public String triggerEvent(String piid, String processTemplateName,
			String OperateName, HashMap map, HashMap<String,String> sessionMap)
			throws Exception;
	
	/**
	 * Transfers the owner for the specified activity instance to the specified
	 * user using the activity instance ID.
	 * 
	 * The activity instance must be in the ready,claimed, waiting, ready, or
	 * stopped execution state. The associated process instance must be in the
	 * running, failing, or terminating execution state.
	 * 
	 * The caller must own the work item with an assignment reason "owner" or
	 * "potential owner" or be an administrator of the associated process
	 * instance.
	 * 
	 * If the caller is not an administrator, then the user that work item is
	 * transferred to must be an administrator or own the same work item with an
	 * assignment reason "potential owner".
	 * 
	 * @param fromOwner
	 *            the original owner
	 * @param toOwner
	 *            the delegated owner
	 * @param tkid
	 *            specified task id
	 * @param operOrgType
	 *            the operate org type
	 * @return the result of the operation
	 * @throws Exception
	 *             thrown by the method
	 */
	public void transferWorkItemByMap(String tkid, String fromOwnerUserId,String fromOwnerRoleId, 
			String toOwnerUserId,String toOwnerRoleId,String operOrgType,HashMap map, HashMap<String,String> sessionMap) throws Exception;
	
	
	/**
	 * Transfers the owner for the specified activity instance to the specified
	 * user using the activity instance ID.
	 * 
	 * The activity instance must be in the ready,claimed, waiting, ready, or
	 * stopped execution state. The associated process instance must be in the
	 * running, failing, or terminating execution state.
	 * 
	 * The caller must own the work item with an assignment reason "owner" or
	 * "potential owner" or be an administrator of the associated process
	 * instance.
	 * 
	 * If the caller is not an administrator, then the user that work item is
	 * transferred to must be an administrator or own the same work item with an
	 * assignment reason "potential owner".
	 * 
	 * @param fromOwner
	 *            the original owner
	 * @param toOwner
	 *            the delegated owner
	 * @param tkid
	 *            specified task id
	 * @param operOrgType
	 *            the operate org type
	 * @return the result of the operation
	 * @throws Exception
	 *             thrown by the method
	 */
	public void transferWorkItem(String tkid, String fromOwnerUserId,String fromOwnerRoleId, 
			String toOwnerUserId,String toOwnerRoleId,String operOrgType,TransferWorkItemData transferWorkItemData, HashMap<String,String> sessionMap) throws Exception;
	
	
	/**
	 * 流程互调
	 * @param piid
	 * @param operationName
	 * @param parameters
	 * @param sessionMap
	 * @throws Exception
	 */
	public void reInvokeProcess(String piid, String operationName,
			             HashMap parameters, HashMap<String,String> sessionMap) throws Exception ;
	/**
	 * 取消申请一个任务
	 * 
	 * @param tkid
	 * @param sessionMap
	 * @throws Exception
	 */
	public void cancelClaimTask(String tkid,HashMap parameters, HashMap<String,String> sessionMap) throws Exception;
}
