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
import com.boco.eoms.materials.dao.StoreInputDao;
import com.boco.eoms.materials.model.StoreInput;

/**
 * 
 * 入库单(StoreInput) Dao实现类
 * 
 * @author wanghongliang
 * 
 */
public class StoreInputDaoHibernate extends BaseDaoHibernate implements
		StoreInputDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.boco.eoms.materials.dao.hibernate.StoreInputDao#getStoreInput()
	 */
	@SuppressWarnings("unchecked")
	public List<StoreInput> getStoreInput() {
		return getHibernateTemplate().find("from StoreInput");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.boco.eoms.materials.dao.hibernate.StoreInputDao#getStoreInput(java.lang.String)
	 */
	public StoreInput getStoreInput(String id) {
		StoreInput storeInput = (StoreInput) getHibernateTemplate().get(
				StoreInput.class, id);
		if (storeInput == null) {
			throw new ObjectRetrievalFailureException(StoreInput.class, id);
		}
		return storeInput;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.boco.eoms.materials.dao.hibernate.StoreInputDao#getStoreInput(java.lang.Integer,
	 *      java.lang.Integer, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Map getStoreInput(final Integer curPage, final Integer pageSize,
			final String hql) {

		// TODO 自动生成方法存根
		HibernateCallback callback = new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException {
				Map map = new HashMap();
				String whereStr = " from StoreInput storeInput ";
				String countStr = " select count(*) from StoreInput ";
				String end = " and delete_Flag = 1";
				if (hql != null && !"".equals(hql)) {
					whereStr = whereStr + hql + end;
					countStr = countStr + hql + end;
				}

				Query countQuery = session.createQuery(countStr);
				List countList = countQuery.list();
				Number totalCount = 0;
				if (countList != null && countList.size() > 0) {
					totalCount = (Number) countList.get(0);
				}
				Query query = session.createQuery(whereStr);
				// 分页查询条
				if (pageSize.intValue() != -1) {
					query.setFirstResult(pageSize.intValue()
							* (curPage.intValue()));
					query.setMaxResults(pageSize.intValue());
				}

				List result = query.list();
				map.put("total", totalCount.intValue());
				map.put("result", result);
				return map;
			}

		};
		return (HashMap) getHibernateTemplate().execute(callback);
	}

	/**
	 * 根据当前时间 获取有多少今天的 入库单据
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
				String countStr = " select count(*) from StoreInput " + hql;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.boco.eoms.materials.dao.hibernate.StoreInputDao#removeStoreInput(java.lang.String)
	 */
	public void removeStoreInput(String id) {
		getHibernateTemplate().delete(getStoreInput(id));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.boco.eoms.materials.dao.hibernate.StoreInputDao#saveStoreInput(com.boco.eoms.materials.model.StoreInput)
	 */
	public void saveStoreInput(StoreInput storeInput) {
		if ((storeInput.getId() == null) || (storeInput.getId().equals("")))
			getHibernateTemplate().save(storeInput);
		else
			getHibernateTemplate().saveOrUpdate(storeInput);
	}

	public void alterStoreInput(StoreInput storeInput) {
		getHibernateTemplate().update(storeInput);
	}
	/**
	 * 
	 * 无用
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<StoreInput> getStoreInputByTime(String start, String end) {
		StringBuffer sb = new StringBuffer();
		sb.append("from StoreInput storeInput ");
		// to_date('2006-04-08 00:00:01','yyyy-mm-dd hh24:mi:ss')
		if ("".equals(start)) {
			if ("".equals(end)) {

			} else {
				sb.append(" where storeInput.storeBillingDate <= to_date('")
						.append(end).append("','yyyy-mm-dd hh24:mi:ss')");
			}
		} else {
			if ("".equals(end)) {
				sb.append(" where storeInput.storeBillingDate >= to_date('")
						.append(start).append("','yyyy-mm-dd hh24:mi:ss')");
			} else {
				sb
						.append(
								" where storeInput.storeBillingDate >= to_date('")
						.append(start)
						.append(
								"','yyyy-mm-dd hh24:mi:ss') and storeInput.storeBillingDate <= to_date('")
						.append(end).append("','yyyy-mm-dd hh24:mi:ss')");
			}
		}
		/*
		 * if(!"".equals(start) && !"".equals(start)){ sb.append(" where
		 * storeInput.storeBillingDate > '").append(start).append("' and
		 * storeInput.storeBillingDate < '") .append(end).append("'"); }
		 * if("".equals(start) && !"".equals(end)){ sb.append(" where
		 * storeInput.storeBillingDate < '").append(end).append("'"); }
		 * if(!"".equals(start) && "".equals(end)){ sb.append(" where
		 * storeInput.storeBillingDate > '").append(start).append("'"); }
		 */
		String str = sb.toString();
		return getHibernateTemplate().find(str);
	}

	public void updateStoreInput(StoreInput storeInput) {
		getHibernateTemplate().update(storeInput);
	}
}
