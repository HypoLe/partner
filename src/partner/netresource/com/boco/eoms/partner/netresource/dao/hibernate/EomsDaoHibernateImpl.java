package com.boco.eoms.partner.netresource.dao.hibernate;

import org.hibernate.Query;

import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;
import com.boco.eoms.deviceManagement.common.dao.CommonGenericDaoImpl;
import com.boco.eoms.deviceManagement.common.pojo.EomsSearch;
import com.boco.eoms.partner.netresource.dao.IEomsDao;
import com.boco.eoms.partner.netresource.model.EomsModel;
import com.google.common.base.Preconditions;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.SearchResult;

public class EomsDaoHibernateImpl<T extends EomsModel> extends CommonGenericDaoImpl<T, String> 
	implements IEomsDao<T>,ID2NameDAO  {
	
	public String id2Name(String id) throws DictDAOException {
		return "";
	}

	public <RT> SearchResult<RT> searchAndCount(EomsSearch eomsSearch) {
		Preconditions.checkNotNull(eomsSearch);
		SearchResult<RT> result = new SearchResult<RT>();
		
		if(eomsSearch.getQueryHql() != null && !"".equals(eomsSearch.getQueryHql())) {
			Preconditions.checkNotNull(eomsSearch);
			Integer total = (Integer) getSession().createQuery(
					eomsSearch.getCountHql()).iterate().next();
			
			Query query = getSession().createQuery(eomsSearch.getQueryHql());
			query.setFirstResult(eomsSearch.getFirstResult());
			query.setMaxResults(eomsSearch.getMaxResults());
			
			result.setTotalCount(total.intValue());
			result.setResult(query.list());
		} else {
			result = this.searchAndCount((ISearch)eomsSearch);
		}
		return result;
	}
	
	public void setPersistentClass(Class persistentClass) {
		this.persistentClass = persistentClass;
	}
	
	public Class getPersistentClass() {
		return this.persistentClass;
	}
}
