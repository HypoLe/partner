package com.boco.eoms.partner.netresource.dao;

import java.sql.PreparedStatement;
import java.util.List;

public interface ITowerAntennasDaoJdbc {
	
	/**
	 *  导入天塔及天馈资源
	 * @param mainList
	 * @param st1
	 * @param flag  1 是天塔及天馈，2是设备
	 * @throws Exception
	 */
	public void importData(List<Object> mainList,PreparedStatement st1,int flag) throws Exception;
	


}
