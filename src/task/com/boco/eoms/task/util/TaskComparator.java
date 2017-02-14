package com.boco.eoms.task.util;

import java.util.Comparator;

import com.boco.eoms.task.model.Task;

public class TaskComparator implements Comparator {

	public int compare(Object obj1, Object obj2) {
		Task task1 = (Task) obj1;
		Task task2 = (Task) obj2;
		return task1.getId().compareTo(task2.getId());
	}

}
