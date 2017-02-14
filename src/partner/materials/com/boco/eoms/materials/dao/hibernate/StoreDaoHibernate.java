package com.boco.eoms.materials.dao.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.materials.dao.StoreDao;
import com.boco.eoms.materials.model.Store;

/**
 * 
 * 仓库信息(Store) Dao实现类
 * 
 * @author wanghongliang
 * 
 */
public class StoreDaoHibernate extends BaseDaoHibernate implements StoreDao {

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.dao.hibernate.StoreDao#getStore()
	 */
	@SuppressWarnings("unchecked")
	public List<Store> getStore() {
		return getHibernateTemplate().find("from Store");
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.dao.hibernate.StoreDao#getStore(java.lang.String)
	 */
	public Store getStore(String id) {
		Store store = (Store) getHibernateTemplate().get(
				Store.class, id);
		if (store == null) {
			throw new ObjectRetrievalFailureException(Store.class, id);
		}
		return store;
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.dao.hibernate.StoreDao#getStore(java.lang.Integer, java.lang.Integer)
	 */
	public Map<Integer, Integer> getStore(Integer curPage,
			Integer pageSize) {
		return this.getStore(curPage, pageSize, null);
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.dao.hibernate.StoreDao#getStore(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, Integer> getStore(final Integer curPage,
			final Integer pageSize, final String whereStr) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from Store";
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
				List<Store> result = query.list();
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("total", new Integer(total));
				map.put("result", result);
				return map;
			}
		};
		return (Map) getHibernateTemplate().execute(callback);
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.dao.hibernate.StoreDao#removeStore(java.lang.String)
	 */
	public void removeStore(String id) {
		getHibernateTemplate().delete(getStore(id));

	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.dao.hibernate.StoreDao#saveStore(com.boco.eoms.materials.model.Store)
	 */
	public void saveStore(Store store) {
		if ((store.getId() == null) || (store.getId().equals("")))
			getHibernateTemplate().save(store);
		else
			getHibernateTemplate().saveOrUpdate(store);
	}

}
