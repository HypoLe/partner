package com.boco.eoms.partner.deviceInspect.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;
import com.boco.eoms.partner.deviceInspect.dao.PnrInspectMappingJdbc;

public class PnrInspectMappingDaoImpl extends JdbcDaoSupport implements PnrInspectMappingJdbc  {

	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getAllTableName(String whereStr) {
		String sql = "select distinct(netres_table_name) from pnr_inspect_mapping "+whereStr;
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getAllDeviceTypeName(String whereStr) {
		String sql = "select id,device_type,device_type_name,device_specialty,device_specialty_name,netres_table_name,netres_field_name,netres_field_value from pnr_inspect_mapping "+whereStr;
		return this.getJdbcTemplate().queryForList(sql);
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getAllDevice(Integer curPage,Integer pageSize,String tableName,
			String filedName, String whereStr) {
//		String sql ="select skip "+curPage+" first "+pageSize+"  id,"+filedName+" as filedName from "+tableName+" "+whereStr;
		
		String sql = "select id,DEVICE_NUMBER as filedName from "+tableName+" "+whereStr;
		sql = CommonSqlHelper.formatPageSql(sql,curPage,pageSize);
		
		String sql2 = "select count(*) from "+tableName+" "+whereStr;
		List list = new ArrayList();
		List list1 = this.getJdbcTemplate().queryForList(sql);
		int count = this.getJdbcTemplate().queryForInt(sql2);
		list.add(count);
		list.add(list1);
		return list;
	}

	public void updateNetres(String tableName,String inspectId,String inspectName,String idStr) {
		String sql = " update "+tableName+" set inspect_id='"+inspectId+"',inspect_name='"+inspectName+"' where id in("+idStr+")";
		this.getJdbcTemplate().execute(sql);
		
	}

	public void cancleRelationInspectDevice(String idStr) {
		String sql = "delete pnr_inspect_link where id in ("+idStr+")";
		this.getJdbcTemplate().execute(sql);
		
	}

}
