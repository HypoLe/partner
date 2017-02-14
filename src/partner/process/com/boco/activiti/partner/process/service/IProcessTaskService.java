package com.boco.activiti.partner.process.service;

import org.activiti.engine.task.Task;


public interface IProcessTaskService {

	/**
	 * 将流程环节信息，注入工单main表中
	 * @param <T>
	 * @param processInstanceId 工单号
	 * @param person 当前工单持有人
	 * @param t
	 * @param classT
	 * @param taskDefKey 当前key
	 * @param taskDefKeyName 当前环节中文描述
	 * @param isOver
	 * @return
	 */
	public <T>T setTvalueOfTask(String processInstanceId,String person,T t,Class<T> classT,String taskDefKey,String taskDefKeyName,Boolean isOver);

}
