package com.boco.eoms.partner.statistically.pojo;

import com.googlecode.genericdao.search.Search;

public class SqlSearch extends Search {

	private static final long serialVersionUID = 1L;

	private String countHql;
	private String queryHql;

	public String getCountHql() {
		return countHql;
	}

	public void setCountHql(String countHql) {
		this.countHql = countHql;
	}

	public String getQueryHql() {
		return queryHql;
	}

	public void setQueryHql(String queryHql) {
		this.queryHql = queryHql;
	}

}
