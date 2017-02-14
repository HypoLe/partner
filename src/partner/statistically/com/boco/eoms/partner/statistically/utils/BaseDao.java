package com.boco.eoms.partner.statistically.utils;

import java.io.Serializable;

import com.boco.eoms.deviceManagement.common.pojo.CommonSearch;
import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import com.googlecode.genericdao.search.SearchResult;




	/**
	 * <p>
	 * We have this base class for our BaseDAOs so that we don't have to override
	 * and author the sessionFactory property for each one. That is the only
	 * reason for having this class.
	 * 
	 * <p>
	 * The
	 * 
	 * @Autowired annotation tells Spring to inject the sessionFactory bean into
	 *            this setter method.
	 * 
	 * @author WuChunHui
	 */
	public interface BaseDao<T, ID extends Serializable> extends
			GenericDAO<T, String> {
		public <RT> SearchResult<RT> searchAndCount(CommonSearch commonSearch);
		
	}


