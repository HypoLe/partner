package com.boco.activiti.partner.process.dao.hibernate;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.activiti.partner.process.dao.IPnrActMaterialJDBCDao;

public class PnrActMaterialDaoJDBC extends JdbcDaoSupport implements IPnrActMaterialJDBCDao{
	
	public void deletePnrActMaterialsByProcessInstanceId(String processInstanceId){
		String delSql = "delete from pnr_act_material where process_instance_id ='"+processInstanceId+"'";
		this.getJdbcTemplate().execute(delSql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement ps)
					throws SQLException, DataAccessException {
				ps.addBatch();
				return ps.executeBatch();
			}
		});
	
	}
	
}
