package com.boco.activiti.partner.process.dao.hibernate;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.activiti.partner.process.dao.IPnrReviewResultsJDBCDao;

public class PnrReviewResultsDaoJDBC extends JdbcDaoSupport implements
		IPnrReviewResultsJDBCDao {

	/**
	 * 删除会审人员结果
	 * @param whereStr
	 */
	public void deletePnrReviewResults(String whereStr){
		String sql = "delete from pnr_review_results "+whereStr+"";
		System.out.println("--------------删除会审人员结果sql="+sql);
		this.getJdbcTemplate().execute(sql,
				new PreparedStatementCallback() {
					public Object doInPreparedStatement(
							PreparedStatement ps) throws SQLException,
							DataAccessException {
						ps.addBatch();
						return ps.executeBatch();
					}
				});
	}
}
