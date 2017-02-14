package com.boco.activiti.partner.process.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;

import com.boco.activiti.partner.process.service.IProcessTaskService;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;

public class ProcessTaskServiceImpl implements IProcessTaskService {
	
	private TaskService taskService = (TaskService) ApplicationContextHolder.getInstance().getBean("taskService");

	@Override
	public <T> T setTvalueOfTask(String processInstanceId, String person, T t, Class<T> classT,String taskDefKey,String taskDefKeyName,Boolean isOver) {
		// TODO Auto-generated method stub
		
		taskDefKey = StaticMethod.null2String(taskDefKey);
		taskDefKeyName = StaticMethod.null2String(taskDefKeyName);
		
		
		Task task = null;
		List<Task> taskList = taskService.createTaskQuery().processInstanceId(
				processInstanceId).taskAssignee(person).active().list();
		int size = taskList.size();
		
		List<Map> list = null;
		
		if (size > 0) {
			task = taskList.get(0);
			setValue("taskId",task.getId(),t,classT);
			setValue("taskDefKey",taskDefKey==""?task.getTaskDefinitionKey():taskDefKey,t,classT);
			setValue("taskDefKeyName",taskDefKeyName==""?task.getName():taskDefKeyName,t,classT);
			setValue("assignee",person,t,classT);
			
		}else{//若结束 isOver 是结束：代表流程删除或归档
			if(isOver){				
				setValue("taskId","-",t,classT);
				setValue("taskDefKey",taskDefKey==""?"-":taskDefKey,t,classT);
				setValue("taskDefKeyName",taskDefKeyName==""?"-":taskDefKeyName,t,classT);
				setValue("assignee","-",t,classT);
			}else{
//				setValue("taskId",task.getId(),t,classT);
				setValue("taskDefKey",taskDefKey==""?"-004":taskDefKey,t,classT);
				setValue("taskDefKeyName",taskDefKeyName==""?"-004":taskDefKeyName,t,classT);
				setValue("assignee","superUser",t,classT);
			}
		}
		
     	return t;
	}

	private <T> void setValue(String fieldName,String value,T t, Class<T> classT){
		
		Class[] parameterTypes = new Class[1];
		Field field;
		try {
			field = classT.getDeclaredField(fieldName);
			parameterTypes[0] = field.getType();
			
			StringBuffer sb = new StringBuffer();
			
			sb.append("set");
			
			sb.append(fieldName.substring(0, 1).toUpperCase());
			
			sb.append(fieldName.substring(1));
			
			Method method = classT.getMethod(sb.toString(), parameterTypes);
			
			method.invoke(t, new Object[] { value });     
			
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
