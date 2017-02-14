package com.boco.eoms.partner.netresource.dao;

import java.sql.PreparedStatement;
import java.util.List;

public interface IAccessNetworkRoomDaoJdbc {
	
	/**
	 *  导入 接入网资源
	 * @param mainList
	 * @param st1
	 * @param flag  1是资源；2是设备
	 * @throws Exception
	 */
	public void importData(List<Object> mainList,PreparedStatement st1,int flag) throws Exception;
	

}
