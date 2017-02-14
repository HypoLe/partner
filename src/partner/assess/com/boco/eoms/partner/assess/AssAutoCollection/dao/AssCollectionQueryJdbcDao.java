package com.boco.eoms.partner.assess.AssAutoCollection.dao;

import com.boco.eoms.base.dao.Dao;

public interface AssCollectionQueryJdbcDao extends Dao{
//	获得自动采集结果
	public float getCollectionResult(String jdbcConfig ,String username,String password,String dbDriver,String sql);
}
