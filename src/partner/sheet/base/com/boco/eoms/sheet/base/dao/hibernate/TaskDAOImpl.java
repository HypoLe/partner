/*
 * Created on 2008-4-1
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.boco.eoms.sheet.base.dao.hibernate;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.sheet.base.dao.ITaskDAO;
import com.boco.eoms.sheet.base.model.EomsUndoSheetView;
import com.boco.eoms.sheet.base.task.ITask;

/**
 * @author IBM_USER
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TaskDAOImpl extends BaseSheetDaoHibernate implements ITaskDAO {

	/**
	 * 根据查询条件查询任务信息, 并进行分页处理
	 * 
	 * @param hsql
	 *            查询语句
	 * @param curPage
	 *            分页起始
	 * @param pageSize
	 *            单页显示数量
	 * @return HashMap, 包括任务总量和任务列表
	 * @throws HibernateException
	 */
	public HashMap getTaskListByCondition(final String queryStr,
			final Integer curPage, final Integer pageSize)
			throws HibernateException {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				// 获取任务总数的查询sql
				HashMap map = new HashMap();
				try {
					int sql_distinct = queryStr.indexOf("distinct");

					int sql_index = queryStr.indexOf("from");
					int sql_orderby = queryStr.indexOf("order by");

					String queryCountStr;
					if (sql_distinct > 0) {
						int sql_comma = (queryStr.substring(sql_distinct,
								sql_index)).indexOf(",");
						if (sql_comma > 0) {
							queryCountStr = "select count("
									+ queryStr.substring(sql_distinct,
											sql_distinct + sql_comma) + ") ";
						} else {
							queryCountStr = "select count("
									+ queryStr.substring(sql_distinct,
											sql_index) + ") ";
						}
					} else {
						queryCountStr = "select count(*) ";
					}
					if (sql_orderby > 0)
						queryCountStr += queryStr.substring(sql_index,
								sql_orderby);
					else
						queryCountStr += queryStr.substring(sql_index);

					Integer totalCount;

					Query totalQuery = session.createQuery(queryCountStr);
					List result = totalQuery.list();
					if (result != null && !result.isEmpty()) {
						totalCount = (Integer) result.get(0);
					} else
						totalCount = new Integer(0);

					Query query = session.createQuery(queryStr);
					if (pageSize.intValue() != -1) {
						query.setFirstResult(pageSize.intValue()
								* (curPage.intValue()));
						query.setMaxResults(pageSize.intValue());
					}
					List resultList = query.list();

					map.put("taskTotal", totalCount);
					map.put("taskList", resultList);
				} catch (Exception e) {
					System.out.println("-------task list error!---------");
					e.printStackTrace();
					throw new HibernateException("task list error");
				}
				return map;
			}
		};
		return (HashMap) getHibernateTemplate().execute(callback);
	}

	/**
	 * 根据taskId获取task model对象
	 * 
	 * @param id
	 *            taskId
	 * @return
	 * @throws Exception
	 */
	public ITask loadSinglePO(final String id, final Object obj)
			throws HibernateException {
		ITask task = (ITask) getHibernateTemplate().get(obj.getClass(), id);
		return task;
	}

	public ITask loadTaskPO(final String hql) throws HibernateException {

		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				ITask task = null;
				try {
					Query query = session.createQuery(hql);

					List resultList = query.list();
					if (resultList.size() > 0)
						task = (ITask) resultList.get(0);
				} catch (HibernateException e) {
					System.out
							.println("-------task loadTaskPO error!---------");
					throw e;
				}
				return task;
			}
		};
		return (ITask) getHibernateTemplate().execute(callback);
	}

	public List loadTaskList(final String hql) throws HibernateException {

		return getHibernateTemplate().find(hql);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.boco.eoms.sheet.base.dao.ITaskDAO#getTasksBySheetKey(java.lang.String)
	 */
	public List getTasksBySheetKey(String hql) throws HibernateException {
		return getHibernateTemplate().find(hql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.boco.eoms.sheet.base.dao.ITaskDAO#getAllSubTask(java.lang.String)
	 */
	public List getAllSubTask(String hql) throws HibernateException {
		return getHibernateTemplate().find(hql);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.boco.eoms.sheet.base.dao.ITaskDAO#getTasksByHql(java.lang.String)
	 */
	public List getTasksByHql(String hql) throws HibernateException {
		return getHibernateTemplate().find(hql);
	}

	public HashMap getAllTasksByHql(final String hql, final Integer curPage,
			final Integer pageSize) throws HibernateException {

		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				// 获取任务总数的查询sql
				HashMap map = new HashMap();
				try {
					int sql_distinct = hql.indexOf("distinct");

					int sql_index = hql.indexOf("from");
					int sql_orderby = hql.indexOf("order by");

					String queryCountStr;
					if (sql_distinct > 0) {
						int sql_comma = (hql.substring(sql_distinct, sql_index))
								.indexOf(",");
						if (sql_comma > 0) {
							queryCountStr = "select count("
									+ hql.substring(sql_distinct, sql_distinct
											+ sql_comma) + ") ";
						} else {
							queryCountStr = "select count("
									+ hql.substring(sql_distinct, sql_index)
									+ ") ";
						}
					} else {
						queryCountStr = "select count(*) as c ";
					}
					if (sql_orderby > 0)
						queryCountStr += hql.substring(sql_index, sql_orderby);
					else
						queryCountStr += hql.substring(sql_index);

					Integer totalCount;

					SQLQuery totalQuery = session.createSQLQuery(queryCountStr);
					totalQuery.addScalar("c", Hibernate.INTEGER);
					List result = totalQuery.list();
					if (result != null && !result.isEmpty()) {
						totalCount = (Integer) result.get(0);
					} else
						totalCount = new Integer(0);

					SQLQuery query = session.createSQLQuery(hql);
					query.addEntity(EomsUndoSheetView.class);

					if (pageSize.intValue() != -1) {
						query.setFirstResult(pageSize.intValue()
								* (curPage.intValue()));
						query.setMaxResults(pageSize.intValue());
					}
					List resultList = query.list();

					map.put("taskTotal", totalCount);
					map.put("taskList", resultList);
				} catch (Exception e) {
					System.out.println("-------task list error!---------");
					e.printStackTrace();
					throw new HibernateException("task list error");
				}
				return map;
			}
		};
		return (HashMap) getHibernateTemplate().execute(callback);
	}

	@Override
	public int bulkUpdate(String arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int bulkUpdate(String arg0, Object[] arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List find(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List find(String arg0, Object[] arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List find(String arg0, List arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findByVarParams(String arg0, Object... arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageModel searchPaginated(String arg0, Object[] arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return null;
	}

}
