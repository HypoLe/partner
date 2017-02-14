package com.boco.activiti.partner.process.dao;


public interface INetResInspectUserJDBCDao {
	/**
	 * 	 通过地市ID获取到联通的区县ID
	 	 * @author WANGJUN
	 	 * @title: getDeptIdByAreaId
	 	 * @date Sep 7, 2016 4:07:45 PM
	 	 * @return String
	 */
	public String getDeptIdByAreaId(String areaId);
}
