package com.boco.eoms.partner.netresource.dao;

import java.sql.PreparedStatement;
import java.util.List;

public interface IRoomResourceDaoJdbc {
	
	/**
	 *  导入 室分资源
	 * @param mainList
	 * @param st1
	 * @param flag 
	 * @throws Exception
	 */
	public void importData(List<Object> mainList,PreparedStatement st1,int flag) throws Exception;
	


}
