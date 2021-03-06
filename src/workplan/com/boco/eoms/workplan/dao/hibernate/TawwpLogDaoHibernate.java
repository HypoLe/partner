package com.boco.eoms.workplan.dao.hibernate;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.workplan.dao.ITawwpLogDao;
import com.boco.eoms.workplan.model.TawwpExecuteReport;
import com.boco.eoms.workplan.model.TawwpLog;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Date:Jun 25, 2008 11:45:37 PM
 * </p>
 * 
 * @author eoms
 * @version 3.5.1
 * 
 */
public class TawwpLogDaoHibernate extends BaseDaoHibernate implements
		ITawwpLogDao {
	/**
	 * 保存日志
	 * 
	 * @param _tawwpLog
	 * TawwpLog 日志类 @ 操作异常
	 */
	public void saveLog(TawwpLog _tawwpLog) {
		getHibernateTemplate().save(_tawwpLog);
	}

	/**
	 * 删除日志
	 * 
	 * @param _tawwpLog
	 * TawwpLog 日志类 @ 操作异常
	 */
	public void deleteLog(TawwpLog _tawwpLog) {
		getHibernateTemplate().delete(_tawwpLog);
	}

	/**
	 * 修改日志
	 * 
	 * @param _tawwpLog
	 * TawwpLog 日志类 @ 操作异常
	 */
	public void updateLog(TawwpLog _tawwpLog) {
		getHibernateTemplate().update(_tawwpLog);
	}

	/**
	 * 查询日志信息
	 * 
	 * @param id
	 * String 日志标识 @ 操作异常
	 * @return TawwpLog 日志类
	 */
	public TawwpLog loadLog(String id) {
		return (TawwpLog) getHibernateTemplate().get(TawwpLog.class, id);
	}

	public List listLog(final int[] pagePra) {
		 
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from TawwpLog as tawwplog";

				Query query = session.createQuery(queryStr);
				query.setFirstResult(pagePra[0]);
				query.setMaxResults(pagePra[1]);
				List result = query.list();
				return result;
			}
		};
		return (List) getHibernateTemplate().execute(callback);
	}

	/**
	 * 组合查询日志信息,分页
	 * 
	 * @param _map
	 *            Map 查询条件
	 * @param _pagePra
	 * int[] 分页信息 @ 操作异常
	 * @return List 日志信息列表
	 */
	public List searchLog(Map _map, int[] _pagePra) {
	 

		DetachedCriteria criteria = DetachedCriteria.forClass(TawwpLog.class);
		String condition = null;
		Set set = _map.keySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			condition = (String) iterator.next();
			if (condition != null) {
				// 查询条件
				if (condition.equals("startDate")) {
					criteria.add(Expression.ge(condition, _map.get(condition)));
				} else if (condition.equals("endDate")) {
					criteria.add(Expression.le(condition, _map.get(condition)));
				} else {
					criteria.add(Expression.eq(condition, _map.get(condition)));
				}
				// 排序
				criteria.addOrder(Order.desc("crtime"));

			}
		}
		return getHibernateTemplate().findByCriteria(criteria, _pagePra[0],
				_pagePra[1]);

	}

	/**
	 * 组合查询日志信息
	 * 
	 * @param _map
	 * Map 查询条件 @ 操作异常
	 * @return List 日志信息列表
	 */
	public List searchLog(Map _map) {
	 

		String condition = null;

		Set set = _map.keySet();
		Iterator iterator = set.iterator();

		DetachedCriteria criteria = DetachedCriteria.forClass(TawwpLog.class);

		while (iterator.hasNext()) {
			condition = (String) iterator.next();
			if (condition != null) {
				// 查询条件
				if (condition.equals("startDate")) {
					criteria.add(Expression.ge("crtime", _map.get(condition)+" 00:00:00"));
				} else if (condition.equals("endDate")) {
					criteria.add(Expression.le("crtime", _map.get(condition)+" 23:59:59"));
				} else {
					criteria.add(Expression.eq(condition, _map.get(condition)));
				}
				// 排序
				criteria.addOrder(Order.desc("crtime"));
			}
		}

		return getHibernateTemplate().findByCriteria(criteria);
	}

	/**
	 * 按日期查询日志信息
	 * 
	 * @param _startDate
	 *            String 开始时间
	 * @param _enddate
	 * String 结束时间 @ 操作异常
	 * @return List 日志信息列表
	 */
	public List searchLog(String _startDate, String _enddate) {
	 

		DetachedCriteria criteria = DetachedCriteria
				.forClass(TawwpExecuteReport.class);
		criteria.add(Expression.ge("startDate", _startDate));
		criteria.add(Expression.le("enddate", _enddate));

		return getHibernateTemplate().findByCriteria(criteria);

	}
}
