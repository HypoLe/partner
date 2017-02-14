package com.boco.activiti.partner.process.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.activiti.partner.process.dao.IPnrActReviewStaffJDBCDao;

public class PnrActReviewStaffDaoJDBC extends JdbcDaoSupport implements IPnrActReviewStaffJDBCDao{
	
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

}
