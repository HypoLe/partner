package com.boco.eoms.partner.statistically.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;

public class SqlPagingDaoImpl extends BaseDaoHibernate implements SqlPagingDao {

	public Map getCircuit(final Integer pageIndex, final Integer pageSize,final String countSql,
			final String searchSql) {
		HibernateCallback callback = new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session)
					throws HibernateException {
			
				Integer total = (Integer) session.createQuery(countSql)
						.iterate().next();
				Query query = session.createQuery(searchSql);
				query.setFirstResult(pageSize.intValue()
						* (pageIndex.intValue()));
				query.setMaxResults(pageSize.intValue());
				List result = query.list();
				HashMap map = new HashMap();
				map.put("total", total);
				map.put("result", result);
				return map;
			}
		};
		return (Map) getHibernateTemplate().execute(callback);
		
	}

}
