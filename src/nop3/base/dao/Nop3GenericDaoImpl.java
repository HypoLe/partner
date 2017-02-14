package base.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import base.pojo.Nop3Search;

import com.google.common.base.Preconditions;
import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.googlecode.genericdao.search.SearchResult;
import org.hibernate.Session;

public class Nop3GenericDaoImpl<T, ID extends Serializable> extends
		GenericDAOImpl<T, String> implements Nop3GenericDao<T, String> {
	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	public <RT> SearchResult<RT> searchAndCount(Nop3Search nop3Search) {
		Preconditions.checkNotNull(nop3Search);
		SearchResult<RT> result = new SearchResult<RT>();

		Integer total = (Integer) getSession().createQuery(
				nop3Search.getCountHql()).iterate().next();

		Query query = getSession().createQuery(nop3Search.getQueryHql());
		query.setFirstResult(nop3Search.getFirstResult());
		query.setMaxResults(nop3Search.getMaxResults());

		result.setTotalCount(total.intValue());
		result.setResult(query.list());
		return result;
	}

	/**
	 * Remove all of the specified entities from the datastore.
	 */
	@SuppressWarnings("unchecked")
	public void remove(List<T> entities) {
		super.remove((T[]) entities.toArray());
	}

	@SuppressWarnings("unchecked")
	public boolean[] save(List<T> entities) {
		return super.save((T[]) entities.toArray());
	}

	public Session getSession() {
		return super.getSession();
	}

	public SessionFactory getSessionFactory() {
		return super.getSessionFactory();
	}
}
