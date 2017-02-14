package com.boco.eoms.partner.res.dao.jdbc;

import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class RenjingDaoJdbc extends JdbcDaoSupport{
	public List getGuanglan(String sql){
		return this.getJdbcTemplate().queryForList(sql);
	}
}
