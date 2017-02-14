package com.boco.eoms.examonline.dao;

import java.util.List;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;

public class ExamWarehouseDao extends BaseDaoHibernate{
	public List findByHql(String hql){
		return this.getHibernateTemplate().find(hql);
	}
}
