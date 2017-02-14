package com.boco.activiti.partner.process.dao.hibernate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.activiti.partner.process.dao.IPnrActCityBudgetAmountJDBCDao;
import com.boco.activiti.partner.process.po.BudgetAmountModel;

public class PnrActCityBudgetAmountDaoJDBC extends JdbcDaoSupport implements IPnrActCityBudgetAmountJDBCDao{
	
	/**
	 * 验证地市的审核人员是否已存在
	 * @param cityId
	 * @param id
	 * @return
	 */
	public int checkCityIdUnique(String cityId,String id){
		String sql="";
		if(id!=null&&!id.equals("")){
			sql ="select f.id from pnr_act_review_staff f where f.city_id = '"+cityId+"' and f.id !='"+id+"'";
		}else{
			sql ="select f.id from pnr_act_review_staff f where f.city_id = '"+cityId+"'";
		}	 
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		if(list!=null && list.size()>0){
			return 1;
		}else {
			return 0;
		}	
	}
	
	/**
	 * 校验唯一性
	 * 
	 * @param cityId
	 * @param budgetYear
	 * @param budgetQuarter
	 * @return
	 */
	public int checkOneUnique(String cityId, String budgetYear,
			String budgetQuarter) {
		String sql = "select * from pnr_act_city_budget_amount b where b.city_id = '"
				+ cityId
				+ "' and b.budget_year = '"
				+ budgetYear
				+ "' and b.budget_quarter = '" + budgetQuarter + "'";
		;
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		if (list != null && list.size() > 0) {
			return 1;
		} else {
			return 0;
		}
	}
	
	/**
	 * 查询预算金额
	 * 
	 * @param cityId	地市id
	 * @param budgetYear	年
	 * @param budgetQuarter	季度
	 * @return
	 */
	public String getCityBudgetAmount(String cityId,String budgetYear,String budgetQuarter){
		String budgetAmount="";
		String sql="select budget_amount from pnr_act_city_budget_amount where city_id = '"+cityId+"' and budget_year = '"+budgetYear+"' and budget_quarter = '"+budgetQuarter+"'";
		System.out.println("-----查询预算金额sql="+sql);
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		if (list != null && list.size() > 0) {
			if(list.get(0).get("budget_amount")!=null&&!"".equals(list.get(0).get("budget_amount").toString())){
				budgetAmount= list.get(0).get("budget_amount").toString();
			}
		}
		return budgetAmount;
	}
	
	/**
	 *   查询区县配置的预算金额
	 	 * @author WANGJUN
	 	 * @title: getCountyAmountByCityId
	 	 * @date Jan 16, 2017 2:40:18 PM
	 	 * @param cityId
	 	 * @param budgetYear
	 	 * @param budgetQuarter
	 	 * @return List<BudgetAmountModel>
	 */
	public List<BudgetAmountModel> getCountyAmountByCityId(String pId){
		List<Object> paramList = new ArrayList<Object>();
		String sql ="select b.id,\n" +
					"       b.county_id     as countyId,\n" + 
					"       b.county_name   as countyName,\n" + 
					"       b.budget_amount as budgetAmount\n" + 
					"  from pnr_act_county_budget_amount b\n" + 
					" where b.pid = ?\n" + 
					" order by b.sort";
		paramList.add(pId);
		Object[] args = paramList.toArray();		
		List<BudgetAmountModel> r = new ArrayList<BudgetAmountModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,args);
		for (Map map : list) {
			BudgetAmountModel model = new BudgetAmountModel();
			if (map.get("id") != null && !"".equals(map.get("id").toString())) {
				model.setId(map.get("id").toString());
			}
			if (map.get("countyId") != null && !"".equals(map.get("countyId").toString())) {
				model.setCountyId(map.get("countyId").toString());
			}
			if (map.get("countyName") != null && !"".equals(map.get("countyName").toString())) {
				model.setCountyName(map.get("countyName").toString());
			}
			if (map.get("budgetAmount") != null && !"".equals(map.get("budgetAmount").toString())) {
				model.setBudgetAmount(map.get("budgetAmount").toString());
			}
			r.add(model);
		}
		return r;
	}
	
	/**
	 *   更新区县配置金额
	 	 * @author WANGJUN
	 	 * @title: updateCountyBudgetAmount
	 	 * @date Jan 16, 2017 4:22:44 PM
	 	 * @param id
	 	 * @param budgetAmount
	 	 * @return int
	 */
	public int updateCountyBudgetAmount(final String id,final String budgetAmount){
		String sql ="update pnr_act_county_budget_amount b\n" +
					"   set b.budget_amount = ?\n" + 
					" where b.id = ?";
			return this.getJdbcTemplate().update(  
		                sql,   
		                 new PreparedStatementSetter(){  
		                       @Override  
		                      public void setValues(PreparedStatement ps) throws SQLException {  
		                          ps.setString(1,budgetAmount);  
		                          ps.setString(2,id);  
		                      }  
		                 }  
		         );  
		}
}

