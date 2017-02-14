package com.boco.eoms.partner.process.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.task.Task;

import com.boco.eoms.base.util.ApplicationContextHolder;

public class RejectToAnyTaskUtil {

	/**
	 * activiti驳回到任意节点的实现
	 * 
	 * @param processInstanceId
	 * @param toTaskKey
	 * @param rejectMessage
	 * @param variables
	 */
	public static void rejectToAnyTask(String processInstanceId,
			String toTaskKey, String rejectMessage,
			Map<String, Object> variables) {
		try {
			TaskService taskService = (TaskService) ApplicationContextHolder
					.getInstance().getBean("taskService");
			RepositoryService repositoryService = (RepositoryService) ApplicationContextHolder
					.getInstance().getBean("repositoryService");
			// TODO Auto-generated method stub
			// 获得当前任务的对应实列

			Task taskEntity = taskService.createTaskQuery().processInstanceId(
					processInstanceId).singleResult();
			// 当前任务key
			String taskDefKey = taskEntity.getTaskDefinitionKey();
			// 获得当前流程的定义模型

			ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
					.getDeployedProcessDefinition(taskEntity
							.getProcessDefinitionId());

			// 获得当前流程定义模型的所有任务节点

			List<ActivityImpl> activitilist = processDefinition.getActivities();
			// 获得当前活动节点和驳回的目标节点"draft"
			ActivityImpl currActiviti = null;// 当前活动节点
			ActivityImpl destActiviti = null;// 驳回目标节点
			int sign = 0;
			for (ActivityImpl activityImpl : activitilist) {
				// 确定当前活动activiti节点

				if (taskDefKey.equals(activityImpl.getId())) {
					currActiviti = activityImpl;

					sign++;
				} else if (toTaskKey.equals(activityImpl.getId())) {
					destActiviti = activityImpl;

					sign++;
				}
				// System.out.println("//-->activityImpl.getId():"+activityImpl.getId());
				if (sign == 2) {
					break;// 如果两个节点都获得,退出跳出循环
				}
			}
			System.out.println("//-->currActiviti activityImpl.getId():"
					+ currActiviti.getId());
			System.out.println("//-->destActiviti activityImpl.getId():"
					+ destActiviti.getId());
			// 保存当前活动节点的流程想参数

			List<PvmTransition> hisPvmTransitionList = new ArrayList<PvmTransition>(
					0);

			for (PvmTransition pvmTransition : currActiviti
					.getOutgoingTransitions()) {
				hisPvmTransitionList.add(pvmTransition);
			}
			// 清空当前活动几点的所有流出项

			currActiviti.getOutgoingTransitions().clear();
			System.out
					.println("//-->currActiviti.getOutgoingTransitions().clear():"
							+ currActiviti.getOutgoingTransitions().size());
			// 为当前节点动态创建新的流出项

			TransitionImpl newTransitionImpl = currActiviti
					.createOutgoingTransition();
			// 为当前活动节点新的流出目标指定流程目标
			newTransitionImpl.setDestination(destActiviti);
			// 保存驳回意见

			taskEntity.setDescription(rejectMessage);// 设置驳回意见
			taskService.saveTask(taskEntity);
			// 设定驳回标志

			// Map<String, Object> variables = new HashMap<String, Object>(0);
			// variables.put(WfConstant.WF_VAR_IS_REJECTED.value(),
			// WfConstant.IS_REJECTED.value());
			// variables.put("workOrderCheck", "superUser");
			// variables.put("cityManageChargeState", "rollback");
			// 执行当前任务驳回到目标任务draft

			taskService.complete(taskEntity.getId(), variables);

			// 清除目标节点的新流入项

			destActiviti.getIncomingTransitions().remove(newTransitionImpl);
			// 清除原活动节点的临时流程项

			currActiviti.getOutgoingTransitions().clear();
			// 还原原活动节点流出项参数

			currActiviti.getOutgoingTransitions().addAll(hisPvmTransitionList);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
