package com.boco.eoms.sheet.base.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.eoms.sheet.base.adapter.service.IBusinessFlowAdapterService;
import com.boco.eoms.sheet.base.service.IBusinessFlowService;
import com.boco.eoms.partner.sheet.base.process.model.*;

/**
 *  
 */
public class BusinessFlowServiceImpl implements IBusinessFlowService {

	private IBusinessFlowAdapterService businessFlowAdapterService;

	public IBusinessFlowAdapterService getBusinessFlowAdapterService() {
		return businessFlowAdapterService;
	}

	public void setBusinessFlowAdapterService(
			IBusinessFlowAdapterService businessFlowAdapterService) {
		this.businessFlowAdapterService = businessFlowAdapterService;
	}

	@SuppressWarnings("unchecked")
	public Map getVariable(String identifier, String variableName, HashMap<String,String> sessionMap) throws Exception {
		Map map = new HashMap();
		try {
			map = businessFlowAdapterService.getVariable(identifier, variableName, sessionMap);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public String triggerProcessByMap(String processTemplateName, String OperateName,
			HashMap map, HashMap<String,String> sessionMap) throws Exception {
		String sRutrn = "success";
		try {
			System.out.println("processTemplateName=" + processTemplateName);
			System.out.println("OperateName=" + OperateName);

			businessFlowAdapterService.triggerProcess(processTemplateName,
					OperateName, map, sessionMap);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("新增工单报错，请联系管理员！");
		}
		return sRutrn;
	}
	
	public String triggerProcess(String processTemplateName, String OperateName,
			TriggerProcessData triggerProcessData, HashMap<String,String> sessionMap) throws Exception {
		String sRutrn = "success";
		try {
			System.out.println("processTemplateName=" + processTemplateName);
			System.out.println("OperateName=" + OperateName);
			businessFlowAdapterService.triggerProcess(processTemplateName, OperateName, triggerProcessData, sessionMap);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("新增工单报错，请联系管理员！");
		}
		return sRutrn;
	}
	
	

	/**
	 * 完成人工任务的api，主要调用引擎中的具体方法，来完成人工任务
	 * @param activityId 人工任务对应的实例号，类似与3.0里的taskId
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String finishTaskByMap(String activityId, HashMap parameters,
			HashMap<String,String> sessionMap) throws Exception {
	
		String sRutrn = "success";
		try {
			businessFlowAdapterService.finishTask(activityId, parameters,
					sessionMap);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("处理工单报错，请联系管理员！");
		}
		return sRutrn;
	}



	/**
	 * 完成人工任务的api，主要调用引擎中的具体方法，来完成人工任务
	 * @param activityId  人工任务对应的实例号，类似与3.0里的taskId
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	public String finishTask(String activityId, FinishTaskData finishTaskData,
			HashMap<String,String> sessionMap) throws Exception {
		String sRutrn = "success";
		try {
			businessFlowAdapterService.finishTask(activityId, finishTaskData,
					sessionMap);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("处理工单报错，请联系管理员！");
		}
		return sRutrn;
	}

	/**
	 * 申明一个任务
	 */
	@SuppressWarnings("unchecked")
	public void claimTaskByMap(String tkid,HashMap parameters, HashMap<String,String> sessionMap) throws Exception {
		try {
			businessFlowAdapterService.claimTask(tkid,parameters, sessionMap);
		} catch (Exception ex) {
			throw new Exception("受理工单报错，请联系管理员！");
		}
	}
	
	/**
	 * 申明一个任务
	 */
	public void claimTask(String tkid,ClaimTaskData claimTaskData, HashMap<String,String> sessionMap) throws Exception {
		try {
			businessFlowAdapterService.claimTask(tkid,claimTaskData, sessionMap);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("受理工单报错，请联系管理员！");
		}
	}

	/**
	 * 创建一个子任务
	 */
	@SuppressWarnings("unchecked")
	public void createSubTaskByMap(String tkid,String parentTaskId,  String subTaskName, String taskNameSpace, String parentTaskName, HashMap parameters, HashMap<String,String> sessionMap) throws Exception {
		try {
			businessFlowAdapterService.createSubTask(tkid, parentTaskId, subTaskName, taskNameSpace, parentTaskName, parameters, sessionMap);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("工单分派报错，请联系管理员！");
		}

	}

	/**
	 * 获取节点事件接口列表
	 * 
	 * @param piid
	 * @param sessionMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getActiveEventHandlers(String piid, HashMap<String,String> sessionMap)
			throws Exception {
		List list = new ArrayList();
		try {
			list = businessFlowAdapterService.getActiveEventHandlers(piid, sessionMap);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("getActiveEventHandlers exception");
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public String triggerEvent(String piid,String processTemplateName, String OperateName,
			HashMap map, HashMap<String,String> sessionMap) throws Exception {
		String sRutrn = "success";
		try {
			System.out.println("processTemplateName=" + processTemplateName);
			System.out.println("OperateName=" + OperateName);

			businessFlowAdapterService.triggerEvent(piid,processTemplateName,
					OperateName, map, sessionMap);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("非流程操作报错，请联系管理员！");
		}
		return sRutrn;
	}
	/**
	 * 工单移交
	 * @param sessionMap
	 * @param tkiid
	 * @param task
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void transferWorkItemByMap(String taskId, String fromOwnerUserId,String fromOwnerRoleId,
			String toOwnerUserId,String toOwnerRoleId,String operOrgType, HashMap map,HashMap<String,String> sessionMap) throws Exception {
		try {
			 businessFlowAdapterService.transferWorkItem(taskId,fromOwnerUserId, fromOwnerRoleId, toOwnerUserId, toOwnerRoleId,operOrgType,map,sessionMap);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("工单移交报错，请联系管理员！");
		}
		
	}
	
	/**
	 * 工单移交
	 * @param sessionMap
	 * @param tkiid
	 * @param task
	 * @return
	 * @throws Exception
	 */
	public void transferWorkItem(String taskId, String fromOwnerUserId,String fromOwnerRoleId,
			String toOwnerUserId,String toOwnerRoleId,String operOrgType, TransferWorkItemData transferWorkItemData,HashMap<String,String> sessionMap) throws Exception {
		try {
			 businessFlowAdapterService.transferWorkItem(taskId, transferWorkItemData, sessionMap);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("工单移交报错，请联系管理员！");
		}
	}
	/**
	 * 流程回调
	 */
	@SuppressWarnings("unchecked")
	public void reInvokeProcess(String piid, String operationName,
                       HashMap parameters, HashMap<String,String> sessionMap) throws Exception {
		try {
			 businessFlowAdapterService.reInvokeProcess(piid,operationName, parameters, sessionMap);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("工单回调报错，请联系管理员！");
		}
	}
	/**
	 * 取消申请一个任务
	 * @param tkid
	 * @param sessionMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void cancelClaimTask(String tkid,HashMap parameters, HashMap<String,String> sessionMap) throws Exception{
		try {
			 businessFlowAdapterService.cancelClaimTask(tkid,parameters, sessionMap);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("工单同组受理报错，请联系管理员！");
		}
	}
}
