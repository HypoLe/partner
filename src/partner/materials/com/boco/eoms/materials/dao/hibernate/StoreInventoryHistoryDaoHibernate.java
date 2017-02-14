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
import com.boco.eoms.materials.dao.StoreInventoryHistoryDao;
import com.boco.eoms.materials.model.StoreInventoryHistory;

/**
 * 
 * 库存历史记录信息(StoreInventoryHistory) Dao实现类
 * 
 * @author wanghongliang
 * 
 */
public class StoreInventoryHistoryDaoHibernate extends BaseDaoHibernate implements StoreInventoryHistoryDao {

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.dao.hibernate.StoreInventoryHistoryDao#getStoreInventoryHistory()
	 */
	@SuppressWarnings("unchecked")
	public List<StoreInventoryHistory> getStoreInventoryHistory() {
		return getHibernateTemplate().find("from StoreInventoryHistory");
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.dao.hibernate.StoreInventoryHistoryDao#getStoreInventoryHistory(java.lang.String)
	 */
	public StoreInventoryHistory getStoreInventoryHistory(String id) {
		StoreInventoryHistory storeInventoryHistory = (StoreInventoryHistory) getHibernateTemplate().get(
				StoreInventoryHistory.class, id);
		if (storeInventoryHistory == null) {
			throw new ObjectRetrievalFailureException(StoreInventoryHistory.class, id);
		}
		return storeInventoryHistory;
	}


	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.dao.hibernate.StoreInventoryHistoryDao#getStoreInventoryHistory(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, Integer> getStoreInventoryHistory(final Integer curPage,
			final Integer pageSize, final String hql) {
		// TODO 自动生成方法存根
        HibernateCallback callback = new HibernateCallback() {

            public Object doInHibernate(Session session)
                throws HibernateException
            {
                Map map = new HashMap();
                String whereStr = " from StoreInventoryHistory storeInventoryHistory ";
                String countStr = " select count(*) from StoreInventoryHistory ";
                if(hql != null && !"".equals(hql))
                {
                    whereStr = whereStr + hql;
                    countStr = countStr + hql;
                }
                
                 
                Query countQuery = session.createQuery(countStr);
                List countList = countQuery.list();
                Number totalCount = 0;
                if(countList != null && countList.size() > 0)
                {
                    totalCount = (Number)countList.get(0);
                }
                Query query = session.createQuery(whereStr);
				//分页查询条
				if(pageSize.intValue()!=-1){
					query.setFirstResult(pageSize.intValue() * (curPage.intValue()));
					query.setMaxResults(pageSize.intValue());
				}
                
                List result = query.list();
                map.put("total", totalCount.intValue());
                map.put("result", result);
                return map;
            }

        };
        return (HashMap)getHibernateTemplate().execute(callback);
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.dao.hibernate.StoreInventoryHistoryDao#removeStoreInventoryHistory(java.lang.String)
	 */
	public void removeStoreInventoryHistory(String id) {
		getHibernateTemplate().delete(getStoreInventoryHistory(id));

	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.dao.hibernate.StoreInventoryHistoryDao#saveStoreInventoryHistory(com.boco.eoms.materials.model.StoreInventoryHistory)
	 */
	public void saveStoreInventoryHistory(StoreInventoryHistory storeInventoryHistory) {
		if ((storeInventoryHistory.getId() == null) || (storeInventoryHistory.getId().equals("")))
			getHibernateTemplate().save(storeInventoryHistory);
		else
			getHibernateTemplate().saveOrUpdate(storeInventoryHistory);
	}

}
