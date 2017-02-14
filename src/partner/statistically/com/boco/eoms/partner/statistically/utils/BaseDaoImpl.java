package com.boco.eoms.partner.statistically.utils;

import java.io.Serializable;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.boco.eoms.deviceManagement.common.pojo.CommonSearch;
import com.google.common.base.Preconditions;
import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.googlecode.genericdao.search.SearchResult;
import com.vladium.emma.rt.RT;

public class BaseDaoImpl  <T, ID extends Serializable> extends
GenericDAOImpl<T, String> implements BaseDao<T, String> {
	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public <RT> SearchResult<RT> searchAndCount(CommonSearch commonSearch) {
		Preconditions.checkNotNull(commonSearch);
		SearchResult<RT> result = new SearchResult<RT>();

		Integer total = (Integer) getSession().createQuery(
				commonSearch.getCountHql()).iterate().next();

		Query query = getSession().createQuery(commonSearch.getQueryHql());
		query.setFirstResult(commonSearch.getFirstResult());
		query.setMaxResults(commonSearch.getMaxResults());

		result.setTotalCount(total.intValue());
		result.setResult(query.list());
		return result;
	}
}
