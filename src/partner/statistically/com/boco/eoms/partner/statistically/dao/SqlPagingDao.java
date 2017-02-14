package com.boco.eoms.partner.statistically.dao;

import java.util.Map;

import com.boco.eoms.base.dao.Dao;

public interface SqlPagingDao extends Dao {
	public Map getCircuit(Integer pageIndex, Integer pageSize,String countSql,String searchSql);
}
