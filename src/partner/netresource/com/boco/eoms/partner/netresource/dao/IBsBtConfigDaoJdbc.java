package com.boco.eoms.partner.netresource.dao;

import java.sql.PreparedStatement;
import java.util.List;

public interface IBsBtConfigDaoJdbc {
	
	/**
	 *  导入 基站机房子资源
	 * @param mainList
	 * @param st1
	 * @param flag  1 是基站机房，2是设备
	 * @throws Exception
	 */
	public void importData(List<Object> mainList,PreparedStatement st1,int flag) throws Exception;
	


}
