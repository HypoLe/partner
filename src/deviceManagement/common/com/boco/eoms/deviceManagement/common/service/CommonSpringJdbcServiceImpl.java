package com.boco.eoms.deviceManagement.common.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

public class CommonSpringJdbcServiceImpl implements CommonSpringJdbcService {
	
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public int queryForInt(String sql) {
		return this.jdbcTemplate.queryForInt(sql);
	}
	public List queryForList(String sql) {
		return this.jdbcTemplate.queryForList(sql);
	}
	public Map<String, Object> queryForMap(String sql) {
		return this.jdbcTemplate.queryForMap(sql);
	}
	
	//执行存储过程 add by WangGuangping 2012-2-28 15:56:04
	public boolean executeProcedure(String procedureSql) {
		try {
			this.jdbcTemplate.execute(procedureSql);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public String selChildFlag(String sql) {
		String flag="0";
		List<Map<String,String>> list = (List<Map<String,String>>)this.jdbcTemplate.queryForList(sql);
		if(list!=null && list.size()>0){
			flag = list.get(0).get("is_new");
		}
		return flag;
	}
	@Override
	public String addChildStr(String str, String childSql, String unitSql,
			double sum) {
		List<Map<String,String>> childList = (List<Map<String,String>>)this.jdbcTemplate.queryForList(childSql);
		List<Map<String,String>> unitList = (List<Map<String,String>>)this.jdbcTemplate.queryForList(unitSql);
		if(childList!=null && childList.size()>0 && unitList!=null && unitList.size()>0){
			if(!"".equals(str)){
				str = str+";"+childList.get(0).get("copy_name")+" "+sum+" "+unitList.get(0).get("tbody_name");
			}else{
				str = str+childList.get(0).get("copy_name")+" "+sum+" "+unitList.get(0).get("tbody_name");
			}
		}
		return str;
	}
	
	
}
