package com.boco.eoms.task.dao.hibernate;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.task.dao.ITaskAccessoriesDao;
import com.boco.eoms.task.model.TaskAccessories;

public class TaskAccessoriesDaoHibernate extends BaseDaoHibernate implements
		ITaskAccessoriesDao {

	public void delAccessories(String id) {
		getHibernateTemplate().delete(getAccessories(id));
	}

	public TaskAccessories getAccessories(String id) {
		return (TaskAccessories) getHibernateTemplate().get(
				TaskAccessories.class, id);
	}

	public String saveAccessories(TaskAccessories accessories) {
		if (null == accessories.getId() || "".equals(accessories.getId())) {
			getHibernateTemplate().save(accessories);
		} else {
			getHibernateTemplate().saveOrUpdate(accessories);
		}
		return accessories.getId();
	}

}
