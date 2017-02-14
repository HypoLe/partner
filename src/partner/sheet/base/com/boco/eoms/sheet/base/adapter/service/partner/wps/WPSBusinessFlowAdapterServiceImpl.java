/*
 * Created on 2007-8-3
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.boco.eoms.sheet.base.adapter.service.partner.wps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.sheet.base.process.model.ClaimTaskData;
import com.boco.eoms.partner.sheet.base.process.model.CreateSubTaskData;
import com.boco.eoms.partner.sheet.base.process.model.FinishTaskData;
import com.boco.eoms.partner.sheet.base.process.model.TransferWorkItemData;
import com.boco.eoms.partner.sheet.base.process.model.TriggerProcessData;
import com.boco.eoms.partner.sheet.process.impl.PartnerProcessManager;
import com.boco.eoms.sheet.base.adapter.service.IBusinessFlowAdapterService;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: wps流程service
 * </p>
 * <p>
 * Date:2007-8-3 14:10:41
 * </p>
 * 
 * @author 曲静波
 * 
 * @version 1.0
 * 
 */
public class WPSBusinessFlowAdapterServiceImpl implements
		IBusinessFlowAdapterService {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.boco.eoms.sheet.base.adapter.service.IBusinessFlowAdapterService#finishTask(java.lang.String,
	 *      java.util.HashMap)
	 */
	public void finishTask(final String tkiid, final HashMap parameters,
			HashMap sessionMap) throws Exception {
		String userId = StaticMethod
				.nullObject2String(sessionMap.get("userId"));
		String password = StaticMethod.nullObject2String(sessionMap
				.get("password"));
		
		
		PartnerProcessManager p = new PartnerProcessManager();
		p.finishTask(userId, password, tkiid, parameters);
		String respone ="";
		if (respone == null) {
			throw new Exception("完成任务失败");
		} else {
			String szReturn = (String) respone;
			if (szReturn.equals("success")) {

			} else if (szReturn.equals("subtaskfail")) {
				throw new Exception("子任务尚未完成");
			}
		}
	}
	
	
	public void finishTask(final String tkiid, final FinishTaskData finishTaskData,
			HashMap sessionMap) throws Exception {
		String userId = StaticMethod
				.nullObject2String(sessionMap.get("userId"));
		String password = StaticMethod.nullObject2String(sessionMap
				.get("password"));
		
		
		PartnerProcessManager p = new PartnerProcessManager();
		
		p.finishTask(userId, password, tkiid, finishTaskData);
		String respone ="";
		if (respone == null) {
			throw new Exception("完成任务失败");
		} else {
			String szReturn = (String) respone;
			if (szReturn.equals("success")) {

			} else if (szReturn.equals("subtaskfail")) {
				throw new Exception("子任务尚未完成");
			}
		}
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.boco.eoms.sheet.base.adapter.service.IBusinessFlowAdapterService#triggerProcess(java.lang.String,
	 *      java.lang.String, java.util.HashMap)
	 */
	public String triggerProcess(final String processTemplateName,
			final String operationName, final HashMap parameters,
			HashMap sessionMap) throws Exception {
		String processInstanceId = null;
		String userId = StaticMethod
				.nullObject2String(sessionMap.get("userId"));
		String password = StaticMethod.nullObject2String(sessionMap
				.get("password"));
		PartnerProcessManager p = new PartnerProcessManager();
		processInstanceId = p.triggerProcess(userId, password, processTemplateName, operationName, parameters);
		return processInstanceId;

	}
	
	public String triggerProcess(final String processTemplateName,
			final String operationName, final TriggerProcessData triggerProcessData,
			HashMap sessionMap) throws Exception {
		String processInstanceId = null;
		String userId = StaticMethod
				.nullObject2String(sessionMap.get("userId"));
		String password = StaticMethod.nullObject2String(sessionMap
				.get("password"));
		PartnerProcessManager p = new PartnerProcessManager();
		processInstanceId = p.triggerProcess(userId, password, processTemplateName, operationName, triggerProcessData);
		return processInstanceId;

	}

	public Map getVariable(final String piid, final String variableName,
			HashMap sessionMap) throws Exception {
		String userId = StaticMethod
				.nullObject2String(sessionMap.get("userId"));
		String password = StaticMethod.nullObject2String(sessionMap
				.get("password"));
		PartnerProcessManager p = new PartnerProcessManager();
		String variableObject = p.getVariable(userId, password, piid, variableName);
		return null;
	}


	/*
	 * 申明一个任务
	 * 
	 * @see com.boco.eoms.sheet.base.adapter.service.IBusinessFlowAdapterService#claimTask(java.lang.String,
	 *      java.util.HashMap)
	 */
	public void claimTask(final String tkid, final HashMap parameters,
			HashMap sessionMap) throws Exception {
		// TODO Auto-generated method stub
		String userId = StaticMethod
				.nullObject2String(sessionMap.get("userId"));
		String password = StaticMethod.nullObject2String(sessionMap
				.get("password"));
		PartnerProcessManager p = new PartnerProcessManager();
		p.claimTask(userId, password, tkid, parameters);
	}
	
	/*
	 * 申明一个任务
	 * 
	 * @see com.boco.eoms.sheet.base.adapter.service.IBusinessFlowAdapterService#claimTask(java.lang.String,
	 *      java.util.HashMap)
	 */
	public void claimTask(final String tkid, final ClaimTaskData claimTaskData,
			HashMap sessionMap) throws Exception {
		// TODO Auto-generated method stub
		String userId = StaticMethod
				.nullObject2String(sessionMap.get("userId"));
		String password = StaticMethod.nullObject2String(sessionMap
				.get("password"));
		PartnerProcessManager p = new PartnerProcessManager();
		p.claimTask(userId, password, tkid, claimTaskData);
	}

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
	public void createSubTask(final String tkid, String parentTaskId, String subTaskName, String taskNameSpace, String parentTaskName,final HashMap parameters,
			HashMap sessionMap) throws Exception {
		// TODO Auto-generated method stub
		String userId = StaticMethod
				.nullObject2String(sessionMap.get("userId"));
		String password = StaticMethod.nullObject2String(sessionMap
				.get("password"));
		PartnerProcessManager p = new PartnerProcessManager();
		p.createSubTask(userId, password, parentTaskId, subTaskName, taskNameSpace, parentTaskName, parameters);
	}
	
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
	public void createSubTask(final CreateSubTaskData createSubTaskData,
			HashMap sessionMap) throws Exception {
		// TODO Auto-generated method stub
		String userId = StaticMethod
				.nullObject2String(sessionMap.get("userId"));
		String password = StaticMethod.nullObject2String(sessionMap
				.get("password"));
		PartnerProcessManager p = new PartnerProcessManager();
		p.createSubTask(userId, password, createSubTaskData);
	}


	/**
	 * 获取节点事件接口列表
	 * 
	 * @param tkid
	 * @param sessionMap
	 * @throws Exception
	 */
	public List getActiveEventHandlers(final String piid, HashMap sessionMap)
			throws Exception {
		// TODO Auto-generated method stub
		String userId = StaticMethod
				.nullObject2String(sessionMap.get("userId"));
		String password = StaticMethod.nullObject2String(sessionMap
				.get("password"));
		PartnerProcessManager p = new PartnerProcessManager();
		p.getActiveEventHandlers(userId, password, piid);
		return null;
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.boco.eoms.sheet.base.adapter.service.IBusinessFlowAdapterService#triggerProcess(java.lang.String,
	 *      java.lang.String, java.util.HashMap)
	 */
	public String triggerEvent(final String piid,
			final String processTemplateName, final String operationName,
			final HashMap parameters, HashMap sessionMap) throws Exception {
		String processInstanceId = null;
		// String userId = StaticMethod
		// .nullObject2String(sessionMap.get("userId"));
		// String password = StaticMethod.nullObject2String(sessionMap
		// .get("password"));
		// Subject subject = safeService.logIn(userId, password);
		String userId = StaticMethod
				.nullObject2String(sessionMap.get("userId"));
		String password = StaticMethod.nullObject2String(sessionMap
				.get("password"));
		PartnerProcessManager p = new PartnerProcessManager();
		p.triggerEvent(userId, password, piid, processTemplateName, operationName, parameters);
		return processInstanceId;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.boco.eoms.sheet.base.adapter.service.IBusinessFlowAdapterService#transferTask(java.lang.String,
	 *      java.lang.String, java.lang.String, java.util.HashMap)
	 */
	public void transferWorkItem(final String taskId,
			final String fromOwnerUserId, final String fromOwnerRoleId,
			final String toOwnerUserId, final String toOwnerRoleId,
			final String operOrgType, final HashMap map, HashMap sessionMap)
			throws Exception {
		String userId = StaticMethod
				.nullObject2String(sessionMap.get("userId"));
		String password = StaticMethod.nullObject2String(sessionMap
				.get("password"));
		PartnerProcessManager p = new PartnerProcessManager();
		p.transferWorkItem(userId, password, taskId, fromOwnerUserId, fromOwnerRoleId, toOwnerUserId, toOwnerRoleId, operOrgType, map);
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.boco.eoms.sheet.base.adapter.service.IBusinessFlowAdapterService#transferTask(java.lang.String,
	 *      java.lang.String, java.lang.String, java.util.HashMap)
	 */
	public void transferWorkItem(final String taskId,
			final TransferWorkItemData transferWorkItemData, HashMap sessionMap)
			throws Exception {
		String userId = StaticMethod
				.nullObject2String(sessionMap.get("userId"));
		String password = StaticMethod.nullObject2String(sessionMap
				.get("password"));
		PartnerProcessManager p = new PartnerProcessManager();
		p.transferWorkItem(userId, password, taskId, transferWorkItemData);
		
	}

	/**
	 * 流程回调
	 * 
	 * @param piid
	 *            回调流程的piid
	 * @param operationName
	 *            回调流程的taskName
	 * @param parameters
	 * @param sessionMap
	 * @throws Exception
	 */
	public void reInvokeProcess(final String piid, final String operationName,
			final HashMap parameters, HashMap sessionMap) throws Exception {
		// TODO Auto-generated method stub
		String userId = StaticMethod
				.nullObject2String(sessionMap.get("userId"));
		String password = StaticMethod.nullObject2String(sessionMap
				.get("password"));
		
		
	}
	/**
	 * 取消申请一个任务
	 * 
	 * @param tkid
	 * @param sessionMap
	 * @throws Exception
	 */
	public void cancelClaimTask(final String tkid,HashMap parameters, HashMap sessionMap) throws Exception{
		String userId = StaticMethod.nullObject2String(sessionMap.get("userId"));
        String password = StaticMethod.nullObject2String(sessionMap.get("password"));
       
      }
      
	
	/**
	 * @author yyk
	 * 结束流程实例
	 * @param processId
	 * @param 流程实例id
	 * @throws Exception
	 */	
	public void toTerminateProcess(final String piid, HashMap sessionMap) throws Exception {
		String userId = StaticMethod.nullObject2String(sessionMap.get("userId"));
        String password = StaticMethod.nullObject2String(sessionMap.get("password"));
        PartnerProcessManager p = new PartnerProcessManager();
        p.terminateProcess(userId, password, piid);
	}
	
	/**
	 * @author yyk
	 * 2009-11-17
	 * 是否存在和输入参数operationName名称相同的事件
	 * TriggerEventName
	 */
	public boolean ifExistTriggerEventName(final String piid, final String operationName, HashMap sessionMap) throws Exception {
		
		boolean bFlag = false;
		String userId = StaticMethod
				.nullObject2String(sessionMap.get("userId"));
		String password = StaticMethod.nullObject2String(sessionMap
				.get("password"));
		PartnerProcessManager p = new PartnerProcessManager();
		String eventNames = p.getActiveEventHandlers(userId, password, piid);
		return bFlag;	

	}	

}
