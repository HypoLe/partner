package com.boco.eoms.materials.dao.hibernate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.materials.dao.StoreOutDao;
import com.boco.eoms.materials.model.StoreOut;

/**
 * 
 * 出库单信息(StoreOut) Dao实现类
 * 
 * @author wanghongliang
 * 
 */
public class StoreOutDaoHibernate extends BaseDaoHibernate implements StoreOutDao {

	public void alterStoreOut(StoreOut storeOut) {
		getHibernateTemplate().update(storeOut);
	}
	
	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.dao.hibernate.StoreOutDao#getStoreOut()
	 */
	@SuppressWarnings("unchecked")
	public List<StoreOut> getStoreOut() {
		return getHibernateTemplate().find("from StoreOut");
	}

	
	/**
	 * 根据当前时间 获取有多少今天的 出库单据
	 * 
	 * @return Map
	 */
	@SuppressWarnings("unchecked")
	public Map getCountByTime() {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Map map = new HashMap();
				String hql = " where storeMakeBillDate = to_date('"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"','yyyy-mm-dd')";
				String countStr = " select count(*) from StoreOut " + hql;

				Query countQuery = session.createQuery(countStr);
				List countList = countQuery.list();
				Number totalCount = 0;
				if (countList != null && countList.size() > 0) {
					totalCount = (Number) countList.get(0);
				}

				map.put("total", totalCount.intValue());
				return map;
			}

		};
		return (HashMap) getHibernateTemplate().execute(callback);

	}
	
	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.dao.hibernate.StoreOutDao#getStoreOut(java.lang.String)
	 */
	public StoreOut getStoreOut(String id) {
		StoreOut storeOut = (StoreOut) getHibernateTemplate().get(
				StoreOut.class, id);
		if (storeOut == null) {
			throw new ObjectRetrievalFailureException(StoreOut.class, id);
		}
		return storeOut;
	}


	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.dao.hibernate.StoreOutDao#getStoreOut(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Map getStoreOut(final Integer curPage, final Integer pageSize,
			final String hql) {
		
	
			// TODO 自动生成方法存根
	        HibernateCallback callback = new HibernateCallback() {

	            public Object doInHibernate(Session session)
	                throws HibernateException
	            {
	                Map map = new HashMap();
	                String whereStr = " from StoreOut storeOut ";
	                String countStr = " select count(*) from StoreOut ";
	                String end = " and delete_Flag = 1";
					if (hql != null && !"".equals(hql)) {
						whereStr = whereStr + hql + end;
						countStr = countStr + hql + end;
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
	 * @see com.boco.eoms.materials.dao.hibernate.StoreOutDao#removeStoreOut(java.lang.String)
	 */
	public void removeStoreOut(String id) {
		getHibernateTemplate().delete(getStoreOut(id));

	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.dao.hibernate.StoreOutDao#saveStoreOut(com.boco.eoms.materials.model.StoreOut)
	 */
	public void saveStoreOut(StoreOut storeOut) {
		if ((storeOut.getId() == null) || (storeOut.getId().equals("")))
			getHibernateTemplate().save(storeOut);
		else
			getHibernateTemplate().saveOrUpdate(storeOut);
	}

	/**
	 *无用
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<StoreOut> getStoreOutByTime(String start, String end) {
		StringBuffer sb = new StringBuffer();
		sb.append("from StoreOut storeOut ");
		if("".equals(start)){
			if("".equals(end)){
				
			}else{
				sb.append(" where storeOut.storeBillingDate <= to_date('").append(end).append("','yyyy-mm-dd hh24:mi:ss')");
			}
		}else{
			if("".equals(end)){
				sb.append(" where storeOut.storeBillingDate >= to_date('").append(start).append("','yyyy-mm-dd hh24:mi:ss')");
			}else{
				sb.append(" where storeOut.storeBillingDate >= to_date('").append(start).append("','yyyy-mm-dd hh24:mi:ss') and storeOut.storeBillingDate <= to_date('")
				.append(end).append("','yyyy-mm-dd hh24:mi:ss')");
			}
		}
		String str = sb.toString();
		return getHibernateTemplate().find(str);
	}

	public void updateStoreOut(StoreOut entity) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(entity);
	}
	
}
