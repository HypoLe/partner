package com.boco.activiti.partner.process.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.activiti.partner.process.dao.INetResInspectUserJDBCDao;

public class NetResInspectUserDaoJDBC extends JdbcDaoSupport implements
		INetResInspectUserJDBCDao {
	/**
	 * 通过地市ID获取到联通的区县ID
	 * 
	 * @author WANGJUN
	 * @title: getDeptIdByAreaId
	 * @date Sep 7, 2016 4:07:45 PM
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public String getDeptIdByAreaId(String areaId) {
		String deptId = "";// 处理人
		String sql = "select t.deptid" + "  from taw_system_dept t"
				+ " where t.deptname like '%联通'" + "   and t.areaid = ?";
		List list = new ArrayList();
		list.add(areaId);
		Object[] args = list.toArray();
		List<Map> list1 = this.getJdbcTemplate().queryForList(sql, args);
		if (list1 != null && !list1.isEmpty() && list1.size() > 0) {
			if (list1.get(0).get("deptid") != null) {
				deptId = list1.get(0).get("deptid").toString();
			}
		}
		return deptId;
	}
}
