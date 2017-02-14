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
import com.boco.eoms.materials.dao.StoreAllotDao;
import com.boco.eoms.materials.model.StoreAllot;

/**
 * 
 * 调拨单表(StoreAllot) Dao实现类
 * 
 * @author wanghongliang
 * 
 */
public class StoreAllotDaoHibernate extends BaseDaoHibernate implements StoreAllotDao {

	public void alterStoreAllot(StoreAllot storeAllot) {
		getHibernateTemplate().update(storeAllot);
	}
	
	
	/**
	 * 根据当前时间 获取有多少今天的 调拨单据
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
				String countStr = " select count(*) from StoreAllot " + hql;

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
	 * @see com.boco.eoms.materials.dao.hibernate.StoreAllotDao#getStoreAllot()
	 */
	@SuppressWarnings("unchecked")
	public List<StoreAllot> getStoreAllot() {
		return getHibernateTemplate().find("from StoreAllot");
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.dao.hibernate.StoreAllotDao#getStoreAllot(java.lang.String)
	 */
	public StoreAllot getStoreAllot(String id) {
		StoreAllot storeAllot = (StoreAllot) getHibernateTemplate().get(
				StoreAllot.class, id);
		if (storeAllot == null) {
			throw new ObjectRetrievalFailureException(StoreAllot.class, id);
		}
		return storeAllot;
	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.dao.hibernate.StoreAllotDao#getStoreAllot(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Map getStoreAllot(final Integer curPage, final Integer pageSize,
			final String hql) {
		
	
			// TODO 自动生成方法存根
	        HibernateCallback callback = new HibernateCallback() {

	            public Object doInHibernate(Session session)
	                throws HibernateException
	            {
	                Map map = new HashMap();
	                String whereStr = " from StoreAllot storeAllot ";
	                String countStr = " select count(*) from StoreAllot ";
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
	 * @see com.boco.eoms.materials.dao.hibernate.StoreAllotDao#removeStoreAllot(java.lang.String)
	 */
	public void removeStoreAllot(String id) {
		getHibernateTemplate().delete(getStoreAllot(id));

	}

	/* (non-Javadoc)
	 * @see com.boco.eoms.materials.dao.hibernate.StoreAllotDao#saveStoreAllot(com.boco.eoms.materials.model.StoreAllot)
	 */
	public void saveStoreAllot(StoreAllot storeAllot) {
		if ((storeAllot.getId() == null) || (storeAllot.getId().equals("")))
			getHibernateTemplate().save(storeAllot);
		else
			getHibernateTemplate().saveOrUpdate(storeAllot);
	}

	/**
	 * 无用
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<StoreAllot> getStoreAllotByTime(String start, String end) {
		StringBuffer sb = new StringBuffer();
		sb.append("from StoreAllot storeAllot ");
		if("".equals(start)){
			if("".equals(end)){
				
			}else{
				sb.append(" where storeAllot.storeBillingDate <= to_date('").append(end).append("','yyyy-mm-dd hh24:mi:ss')");
			}
		}else{
			if("".equals(end)){
				sb.append(" where storeAllot.storeBillingDate >= to_date('").append(start).append("','yyyy-mm-dd hh24:mi:ss')");
			}else{
				sb.append(" where storeAllot.storeBillingDate >= to_date('").append(start).append("','yyyy-mm-dd hh24:mi:ss') and storeAllot.storeBillingDate <= to_date('")
				.append(end).append("','yyyy-mm-dd hh24:mi:ss')");
			}
		}

		String str = sb.toString();
		return getHibernateTemplate().find(str);
	}

	public void updateStoreAllot(StoreAllot storeAllot) {
		// TODO Auto-generated method stub
		getHibernateTemplate().update(storeAllot);
	}
}
