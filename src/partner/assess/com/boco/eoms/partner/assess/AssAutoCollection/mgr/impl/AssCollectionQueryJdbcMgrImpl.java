package com.boco.eoms.partner.assess.AssAutoCollection.mgr.impl;

import com.boco.eoms.partner.assess.AssAutoCollection.dao.jdbc.AssCollectionQueryJDBC;
import com.boco.eoms.partner.assess.AssAutoCollection.mgr.AssCollectionQueryJdbcMgr;

/**
 * <p>
 * Title:得到自动采集结果
 * </p>
 * <p>
 * Description:得到自动采集结果
 * </p>
 * <p>
 * Thu Apr 07 14:49:20 CST 2011
 * </p>
 * 
 * @author 贲伟玮
 * @version 1.0
 * 
 */
public class AssCollectionQueryJdbcMgrImpl implements AssCollectionQueryJdbcMgr {
 	
	private AssCollectionQueryJDBC  assCollectionQueryJDBC;
 	
	public AssCollectionQueryJDBC getAssCollectionQueryJDBC() {
		return assCollectionQueryJDBC;
	}

	public void setAssCollectionQueryJDBC(AssCollectionQueryJDBC assCollectionQueryJDBC){ 
		this.assCollectionQueryJDBC = assCollectionQueryJDBC;
	}
	
    public float getAssCollectionResult(String jdbcConfig ,String username,String password,String dbDriver,String sql){
    	return assCollectionQueryJDBC.getCollectionResult(jdbcConfig, username, password, dbDriver, sql);
    }
	
}