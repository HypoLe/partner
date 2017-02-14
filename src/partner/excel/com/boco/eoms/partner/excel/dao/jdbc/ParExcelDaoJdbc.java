package com.boco.eoms.partner.excel.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;

import com.boco.eoms.base.dao.jdbc.BaseDaoJdbc;
import com.boco.eoms.partner.excel.dao.IParExcelDao;

/**
 * <p>Title: Excel的导入功能</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: </p>
 * @author benweiwei
 * @version 1.0(2009-10-23)
 */
public class ParExcelDaoJdbc  extends BaseDaoJdbc implements IParExcelDao{
	// Excel 增加
	public Integer insert(final String sql) {
		ConnectionCallback callback = new ConnectionCallback() {
			public Object doInConnection(Connection conn) throws SQLException, DataAccessException {
				PreparedStatement pstm = null;
				pstm = conn.prepareStatement(sql);
				int count = pstm.executeUpdate();
				pstm.close();				
				return new Integer(count);
			}
		};
		Object obj = this.getJdbcTemplate().execute(callback);
		return (Integer) Integer.valueOf(0);
	}

}
