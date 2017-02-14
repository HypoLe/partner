package com.boco.eoms.task.util;

import org.displaytag.decorator.TableDecorator;

import com.boco.eoms.task.model.Task;

public class TaskDecorator extends TableDecorator{
	/**
	 * id属性的checkbox
	 * 
	 * @return 一个带有checkbox的属性
	 */
	public String getTaskId() {		
		Task task = (Task) getCurrentRowObject();
		return "<input type='checkbox' taskId='" + task.getTaskId()
				+ "' name='ids' value='" + task.getTaskId() + "'>";
		
	}
}
