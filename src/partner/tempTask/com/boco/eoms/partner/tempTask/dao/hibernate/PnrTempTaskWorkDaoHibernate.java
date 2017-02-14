package com.boco.eoms.partner.tempTask.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;
import com.boco.eoms.partner.tempTask.dao.IPnrTempTaskWorkDao;
import com.boco.eoms.partner.tempTask.model.PnrTempTaskWork;

/**
 * <p>
 * Title:合作伙伴临时任务管理 dao的hibernate实现
 * </p>
 * <p>
 * Description:合作伙伴临时任务管理主表信息
 * </p>
 * <p>
 * Mon Apr 26 14:09:01 CST 2010
 * </p>
 * 
 * @author daizhigang
 * @version 1.0
 * 
 */
public class PnrTempTaskWorkDaoHibernate extends BaseDaoHibernate implements IPnrTempTaskWorkDao,
		ID2NameDAO {
	
    /**
	 * 
	 * @see com.boco.eoms.partner.tempTask.PnrTempTaskWorkDao#getPnrTempTaskWorks()
	 *      
	 */
	public List getPnrTempTaskWorks() {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrTempTaskWork pnrTempTaskWork";
				Query query = session.createQuery(queryStr);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
	public List getPnrTempTaskWorks(final String tempTaskId){
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrTempTaskWork pnrTempTaskWork where pnrTempTaskWork.tempTaskId=:tempTaskId ";
				Query query = session.createQuery(queryStr);
				query.setString("tempTaskId", tempTaskId);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);
	}
	/**
	 *
	 * @see com.boco.eoms.partner.tempTask.PnrTempTaskWorkDao#getPnrTempTaskWork(java.lang.String)
	 *
	 */
	public PnrTempTaskWork getPnrTempTaskWork(final String id) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrTempTaskWork pnrTempTaskWork where pnrTempTaskWork.id=:id";
				Query query = session.createQuery(queryStr);
				query.setString("id", id);
				query.setFirstResult(0);
				query.setMaxResults(1);
				List result = query.list();
				if (result != null && !result.isEmpty()) {
					return (PnrTempTaskWork) result.iterator().next();
				} else {
					return new PnrTempTaskWork();
				}
			}
		};
		return (PnrTempTaskWork) getHibernateTemplate().execute(callback);
	}
	
	/**
	 * 
	 * @see com.boco.eoms.partner.tempTask.PnrTempTaskWorkDao#savePnrTempTaskWorks(com.boco.eoms.partner.tempTask.PnrTempTaskWork)
	 *      
	 */
	public void savePnrTempTaskWork(final PnrTempTaskWork pnrTempTaskWork) {
		if ((pnrTempTaskWork.getId() == null) || (pnrTempTaskWork.getId().equals("")))
			getHibernateTemplate().save(pnrTempTaskWork);
		else
			getHibernateTemplate().saveOrUpdate(pnrTempTaskWork);
	}

	/**
	 * 
	 * @see com.boco.eoms.partner.tempTask.PnrTempTaskWorkDao#removePnrTempTaskWorks(java.lang.String)
	 *      
	 */
    public void removePnrTempTaskWork(final String id) {
		getHibernateTemplate().delete(getPnrTempTaskWork(id));
	}

	/**
	 * 
	 * @see com.boco.eoms.commons.system.dict.dao.ID2NameDAO#id2Name(java.lang.String)
	 *      
	 */
	public String id2Name(String id) throws DictDAOException {
		//TODO 请修改代码
		return null;
	}
 
 	/**
	 * 
	 * @see com.boco.eoms.partner.tempTask.PnrTempTaskWorkDao#getPnrTempTaskWorks(java.lang.Integer,java.lang.Integer,java.lang.String)
	 *      
	 */ 
	public Map getPnrTempTaskWorks(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from PnrTempTaskWork pnrTempTaskWork";
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
	
}