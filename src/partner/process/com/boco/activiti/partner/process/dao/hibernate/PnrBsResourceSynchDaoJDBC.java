package com.boco.activiti.partner.process.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.activiti.partner.process.dao.IPnrBsResourceSynchJDBCDao;

public class PnrBsResourceSynchDaoJDBC extends JdbcDaoSupport implements IPnrBsResourceSynchJDBCDao {

	@Override
	public String getBsNameByCityInfoAndBs(String gkCityName, String gkBsName) {
		
		String bsName="";
		String sql = "select t.bs_name from pnr_bs_resource_synch t where t.gk_city_info=? and t.gk_bs_name=? order by rowid desc";
		/*this.getJdbcTemplate().execute(sql, new PreparedStatementCallback(){
			  public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
			    
			    	ps.setString(1, gkCityNameStr);			     
			    	ps.setString(2, gkBsNameStr);
			
			      return ps.executeQuery();
			     }
		});*/
		String[] str ={gkCityName,gkBsName};
		List<Map> results=this.getJdbcTemplate().queryForList(sql, str);
		
		if(results.size()>0){
			bsName = results.get(0).get("BS_NAME").toString();
		}
		
		return bsName;
	}

}
