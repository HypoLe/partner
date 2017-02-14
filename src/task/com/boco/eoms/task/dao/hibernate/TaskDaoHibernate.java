package com.boco.eoms.task.dao.hibernate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.task.dao.ITaskDao;
import com.boco.eoms.task.model.Task;
import com.boco.eoms.task.util.TaskAppUtil;
import com.boco.eoms.task.util.TaskComparator;
import com.boco.eoms.task.util.TaskConstants;

public class TaskDaoHibernate extends BaseDaoHibernate implements ITaskDao {

	public void delTask(String taskId) {
		Task task = getTask(taskId);
		if (null != task) {
			getHibernateTemplate().delete(task);
		}
	}

	public Task getTask(String taskId) {
		return (Task) getHibernateTemplate().get(Task.class, taskId);
	}

	public List listNextLevelTask(String taskId) {
		List list = getHibernateTemplate().find(
				"from Task task where task.parentTaskId='" + taskId
						+ "' and task.deleted='" + TaskConstants.UNDELETED
						+ "'");
		TaskComparator comparator = new TaskComparator();
		Collections.sort(list, comparator);
		return list;
	}

	public void saveTask(Task task) {
		String currentTime = TaskAppUtil.changeTime2Long(StaticMethod
				.getCurrentDateTime());
		if (null != task.getEndTime() && !"".equals(task.getEndTime())) {
			if (Long.parseLong(currentTime) <= Long
					.parseLong(task.getEndTime())) {
				task.setInTimeProgress(task.getProgress());
			}
		}
		if (null == task.getTaskId() || "".equals(task.getTaskId())) {
			getHibernateTemplate().save(task);
		} else {
			getHibernateTemplate().saveOrUpdate(task);
		}
	}

	public List listTopTaskByDrafter(String drafter) {
		// 本周一零点
		String monday = TaskAppUtil.changeTime2Long(TaskAppUtil
				.getFirstDayOfMonth("yyyy-MM-dd hh:mm:ss"));
		// 下周一零点
		String sunday = TaskAppUtil.changeTime2Long(TaskAppUtil
				.getLastDayOfMonth("yyyy-MM-dd hh:mm:ss"));
		List list = getHibernateTemplate().find(
				"from Task task where task.drafter='" + drafter
						+ "' and task.parentTaskId='-1' and task.deleted='"
						+ TaskConstants.UNDELETED + "'"
						+ " and ((task.startTime >= '" + monday
						+ "' and task.startTime <= '" + sunday
						+ "') or (task.endTime >= '" + monday
						+ "' and task.endTime <= '" + sunday + "'))"
						+ " order by task.id desc");
		TaskComparator comparator = new TaskComparator();
		Collections.sort(list, comparator);
		return list;
	}

	public List searchTasks(String whereStr) {
		List list = getHibernateTemplate().find(
				"from Task task" + whereStr + " order by task.id asc");
		TaskComparator comparator = new TaskComparator();
		Collections.sort(list, comparator);
		return list;
	}

	public Map getTasks(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Task task where task.deleted='0'";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
				String queryCountStr = "select count(*) " + queryStr;
				int total = ((Integer) session.createQuery(queryCountStr)
						.iterate().next()).intValue();
				Query query = session.createQuery(queryStr);
				query
						.setFirstResult(pageSize.intValue()
								* (curPage.intValue()));
				query.setMaxResults(pageSize.intValue());
				List result = query.list();
				HashMap map = new HashMap();
				map.put("total", new Integer(total));
				map.put("result", result);
				return map;
			}
		};
		return (Map) getHibernateTemplate().execute(callback);
	}

	public List getChildTask(String taskId) {
		List list = getHibernateTemplate().find(
				"from Task task where task.parentTaskId ='" + taskId + "'");
		TaskComparator comparator = new TaskComparator();
		Collections.sort(list, comparator);
		return list;
	}

	public List listTopTaskByPrincipal(String principal) {
		// 本周一零点
		String monday = TaskAppUtil.changeTime2Long(TaskAppUtil
				.getFirstDayOfMonth("yyyy-MM-dd hh:mm:ss"));
		// 下周一零点
		String sunday = TaskAppUtil.changeTime2Long(TaskAppUtil
				.getLastDayOfMonth("yyyy-MM-dd hh:mm:ss"));
		List list = getHibernateTemplate().find(
				"from Task task where task.principal='" + principal
						+ "' and task.deleted='" + TaskConstants.UNDELETED
						+ "' and ((task.startTime >= '" + monday
						+ "' and task.startTime <= '" + sunday
						+ "') or (task.endTime >= '" + monday
						+ "' and task.endTime <= '" + sunday + "'))"
						+ " order by task.id asc");
		TaskComparator comparator = new TaskComparator();
		Collections.sort(list, comparator);
		return list;
	}

	public List listTaskConflict(String principal, String startTime,
			String endTime, String taskId) {
		List list = new ArrayList();
		String hql = "from Task task where task.principal = '" + principal
				+ "' and ((task.startTime > '" + startTime
				+ "' and task.startTime <='" + endTime
				+ "') or (task.startTime <= '" + startTime
				+ "' and task.endTime > '" + startTime
				+ "')) and task.progress<>'100'";
		if (null != taskId && !"".equals(taskId)) {
			hql = hql + " and task.taskId<>'" + taskId + "'";
		}
		list = getHibernateTemplate().find(hql);
		TaskComparator comparator = new TaskComparator();
		Collections.sort(list, comparator);
		return list;
	}

}
