package com.boco.eoms.partner.inspect.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.eoms.partner.inspect.dao.IInspectPlanStatisticDaoJdbc;
import com.boco.eoms.partner.inspect.model.InspectStatisticArea;
import com.boco.eoms.partner.inspect.model.InspectStatisticPartner;
import com.boco.eoms.partner.inspect.util.DateByMonth;
import com.boco.eoms.partner.inspect.webapp.form.InspectPlanResCountFromNew;
import com.boco.eoms.partner.inspect.webapp.form.InspectPlanResCountFromSpecialty;
import com.google.common.collect.Lists;

/** 
 * Description: 巡检统计 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Aug 1, 2012 3:50:04 PM 
 */
public class InspectPlanStatisticDaoJdbc extends JdbcDaoSupport 
								implements IInspectPlanStatisticDaoJdbc {
	@SuppressWarnings("unchecked")
	public List<InspectStatisticArea> findInspectPlanStatisticCity(){
		String sql = "select id,area,plan_res_num,done_rate,ave_time_on_site,res_Exception_Num,year,month" +
				" from pnr_inspect_Statis_city ";
		return this.getJdbcTemplate().query(sql, new InspectStatisticAreaMapper());
	}
	
	/**
	 * 统计历史地市巡检情况
	 * @param year
	 * @param month
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<InspectStatisticArea> findInspectPlanStatisticCityHis(String year,String month){
		String sql = "select id,area,plan_res_num,done_rate,ave_time_on_site,res_Exception_Num,year,month" +
			" from pnr_inspect_Statis_city_his where year='"+year+"' and month='"+month+"'";
		return this.getJdbcTemplate().query(sql, new InspectStatisticAreaMapper());
	}
	
	/**
	 * 巡检统计-按区域统计
	 * 
	 * 2014-09-21 17:38 
	 * 业务规则：权限---根据人所属的区域查看相应的数据。人的区域获取-按照人所在部门的区域归属。 
	 * 本次修改：用到的方法参数 ：countType:区分权限；areaId：区域的id；year；month 。其他参数暂时没用。
	 * @param year
	 * @param month
	 * @param areaId
	 * @author CHENBING
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<InspectPlanResCountFromNew> findInspectPlanStatisticCityHisNew(boolean isNowMonth,String countType,
			String areaId,String year,String month,String newMonth) {
		List<InspectPlanResCountFromNew> list1 = new ArrayList<InspectPlanResCountFromNew>();
		// 区域：省id长度为2；地市id长度为4；区县id长度为6；
		int areaIdLength = areaId.length();
		String city ="";
		if(areaIdLength ==6){
			city = areaId.substring(0,4);
		}
		 		
		StringBuffer selectSql = new StringBuffer();	
		StringBuffer contentsql = new StringBuffer();	
		StringBuffer commonBuffer = new StringBuffer();
		
			commonBuffer.append(" sum(case when fset.res_level = '11225010301' then fset.num else 0 end) v_stage_a,");
			commonBuffer.append(" sum(case when fset.res_level = '11225010301' then fset.cw else 0 end) v_stage_w,");
			commonBuffer.append(" sum(case when fset.res_level = '11225010301' then fset.cd else 0 end) v_stage_d,");
			commonBuffer.append(" sum(case when fset.res_level = '11225010302' then fset.num else 0 end) a_stage_a,");
			commonBuffer.append(" sum(case when fset.res_level = '11225010302' then fset.cw else 0 end) a_stage_w,");
			commonBuffer.append(" sum(case when fset.res_level = '11225010302' then fset.cd else 0 end) a_stage_d,");
			commonBuffer.append(" sum(case when fset.res_level = '11225010303' then fset.num else 0 end) b_stage_a,");
			commonBuffer.append(" sum(case when fset.res_level = '11225010303' then fset.cw else 0 end) b_stage_w,");
			commonBuffer.append(" sum(case when fset.res_level = '11225010303' then fset.cd else 0 end) b_stage_d,");
			commonBuffer.append(" sum(case when fset.res_level = '11225010304' then fset.num else 0 end) c_stage_a,");
			commonBuffer.append(" sum(case when fset.res_level = '11225010304' then fset.cw else 0 end) c_stage_w,");
			commonBuffer.append(" sum(case when fset.res_level = '11225010304' then fset.cd else 0 end) c_stage_d,");
			commonBuffer.append(" sum(case when fset.res_level = '11225050201' then fset.num else 0 end) a_accessNetwork_a,");
			commonBuffer.append(" sum(case when fset.res_level = '11225050201' then fset.cw else 0 end) a_accessNetwork_w,");
			commonBuffer.append(" sum(case when fset.res_level = '11225050201' then fset.cd else 0 end) a_accessNetwork_d,");
			commonBuffer.append(" sum(case when fset.res_level = '11225050202' then fset.num else 0 end) b_accessNetwork_a,");
			commonBuffer.append(" sum(case when fset.res_level = '11225050202' then fset.cw else 0 end) b_accessNetwork_w,");
			commonBuffer.append(" sum(case when fset.res_level = '11225050202' then fset.cd else 0 end) b_accessNetwork_d,");
			commonBuffer.append(" sum(case when fset.res_level = '11225050203' then fset.num else 0 end) c_accessNetwork_a,");
			commonBuffer.append(" sum(case when fset.res_level = '11225050203' then fset.cw else 0 end) c_accessNetwork_w,");
	        commonBuffer.append(" sum(case when fset.res_level = '11225050203' then fset.cd else 0 end) c_accessNetwork_d,");
	       
	       commonBuffer.append(" sum(case when fset.res_level = '11225040402' then fset.num else 0 end) j_tower_a,");
	       commonBuffer.append(" sum(case when fset.res_level = '11225040402' then fset.cw else 0 end) j_tower_w,");
	       commonBuffer.append(" sum(case when fset.res_level = '11225040402' then fset.cd else 0 end) j_tower_d,");
	       
	       commonBuffer.append(" sum(case when fset.res_level = '11225040401' then fset.num else 0 end) y_tower_a,");
	       commonBuffer.append(" sum(case when fset.res_level = '11225040401' then fset.cw else 0 end) y_tower_w,");
	       commonBuffer.append(" sum(case when fset.res_level = '11225040401' then fset.cd else 0 end) y_tower_d,");
	       
	       commonBuffer.append(" sum(case when fset.res_level = '11225030201' then fset.num else 0 end) z_distribution_a,");
	       commonBuffer.append(" sum(case when fset.res_level = '11225030201' then fset.cw else 0 end) z_distribution_w,");
	       commonBuffer.append(" sum(case when fset.res_level = '11225030201' then fset.cd else 0 end) z_distribution_d,");
	      

	       commonBuffer.append(" sum(case when fset.res_level = '11225030202' then fset.num else 0 end) v_distribution_a,");
	       commonBuffer.append(" sum(case when fset.res_level = '11225030202' then fset.cw else 0 end) v_distribution_w,");
	       commonBuffer.append(" sum(case when fset.res_level = '11225030202' then fset.cd else 0 end) v_distribution_d,");
	  
	       commonBuffer.append(" sum(case when fset.res_level = '11225030203' then fset.num else 0 end) a_distribution_a,");
	       commonBuffer.append(" sum(case when fset.res_level = '11225030203' then fset.cw else 0 end) a_distribution_w,");
	       commonBuffer.append(" sum(case when fset.res_level = '11225030203' then fset.cd else 0 end) a_distribution_d,");
	       
	       commonBuffer.append(" sum(case when fset.res_level = '11225030204' then fset.num else 0 end) b_distribution_a,");
	       commonBuffer.append(" sum(case when fset.res_level = '11225030204' then fset.cw else 0 end) b_distribution_w,");
	       commonBuffer.append(" sum(case when fset.res_level = '11225030204' then fset.cd else 0 end) b_distribution_d,");
	       
	       commonBuffer.append(" sum(case when fset.res_level = '11225060101' then fset.num else 0 end) a_wlan_a,");
	       commonBuffer.append(" sum(case when fset.res_level = '11225060101' then fset.cw else 0 end) a_wlan_w,");
	       commonBuffer.append(" sum(case when fset.res_level = '11225060101' then fset.cd else 0 end) a_wlan_d,");
	       
	       commonBuffer.append(" sum(case when fset.res_level = '11225060102' then fset.num else 0 end) b_wlan_a,");
	       commonBuffer.append(" sum(case when fset.res_level = '11225060102' then fset.cw else 0 end) b_wlan_w,");
	       commonBuffer.append(" sum(case when fset.res_level = '11225060102' then fset.cd else 0 end) b_wlan_d,");
	   
	       commonBuffer.append(" sum(case when fset.res_level = '11225060103' then fset.num else 0 end) c_wlan_a,");
	       commonBuffer.append(" sum(case when fset.res_level = '11225060103' then fset.cw else 0 end) c_wlan_w,");
	       commonBuffer.append(" sum(case when fset.res_level = '11225060103' then fset.cd else 0 end) c_wlan_d,");
	       
	       //重点机房统计VIP
	       commonBuffer.append(" sum(case when fset.res_level = '11225090101' then fset.num else 0 end) v_jifang_a,");
	       commonBuffer.append(" sum(case when fset.res_level = '11225090101' then fset.cw else 0 end) v_jifang_w,");
	       commonBuffer.append(" sum(case when fset.res_level = '11225090101' then fset.cd else 0 end) v_jifang_d,");
	       //重点机房统计A类
	       commonBuffer.append(" sum(case when fset.res_level = '11225090102' then fset.num else 0 end) a_jifang_a,");
	       commonBuffer.append(" sum(case when fset.res_level = '11225090102' then fset.cw else 0 end) a_jifang_w,");
	       commonBuffer.append(" sum(case when fset.res_level = '11225090102' then fset.cd else 0 end) a_jifang_d,");
	       //重点机房统计B类
	       commonBuffer.append(" sum(case when fset.res_level = '11225090103' then fset.num else 0 end) b_jifang_a,");
	       commonBuffer.append(" sum(case when fset.res_level = '11225090103' then fset.cw else 0 end) b_jifang_w,");
	       commonBuffer.append(" sum(case when fset.res_level = '11225090103' then fset.cd else 0 end) b_jifang_d,");
	       //重点机房统计C类
	       commonBuffer.append(" sum(case when fset.res_level = '11225090104' then fset.num else 0 end) c_jifang_a,");
	       commonBuffer.append(" sum(case when fset.res_level = '11225090104' then fset.cw else 0 end) c_jifang_w,");
	       commonBuffer.append(" sum(case when fset.res_level = '11225090104' then fset.cd else 0 end) c_jifang_d");
		
		//根据月份获取相应的巡检周期
	       DateByMonth db =new DateByMonth();
	       String[] oneMonth = db.getDateByMonth(month,year, "month");
	       String[] doubleMonth = db.getDateByMonth(month,year, "doubleMonth");
	       String[] quarter = db.getDateByMonth(month,year, "quarter");
	       String[] halfOfYear = db.getDateByMonth(month,year, "halfOfYear");
	       String[] yearTime = db.getDateByMonth(month,year, "year");
			if("1".equals(countType)){
				contentsql.append(" select lastSet.year,lastSet.city ,lastSet.res_level,");
				contentsql.append(" lastSet.num,lastSet.cw,");
				contentsql.append(" fun_inspect_choose(lastSet.num,lastSet.city ,'',lastSet.year,").append(month).append(",lastSet.res_level) cd");
				contentsql.append(" from(");
				contentsql.append(" select cm.year,cm.city ,cm.res_level,count(*) num ,");
				contentsql.append(" sum(case when cm.inspect_state = 1 and cm.month='").append(month).append("' then 1 else 0 end) cw  from (");
				contentsql.append("select res.city,res.res_level,res.inspect_state,pm1.year,pm1.month from (");
				//巡检周期为月
				contentsql.append("select rs.plan_id, rs.city,rs.res_level, rs.inspect_state from pnr_inspect_plan_res rs");
				contentsql.append(" where rs.inspect_cycle='month' ");
				contentsql.append(" and rs.plan_id is not null  ");
				contentsql.append(" and  to_char(rs.cycle_start_time,'yyyy/mm/dd')>='").append(oneMonth[0]).append("'");
				contentsql.append("  and  to_char(rs.cycle_end_time,'yyyy/mm/dd')<='").append(oneMonth[1]).append("'");
				contentsql.append(" union all ");
				//巡检周期为双月
				contentsql.append("select rs.plan_id, rs.city,rs.res_level, rs.inspect_state from pnr_inspect_plan_res rs");
				contentsql.append(" where rs.inspect_cycle='doubleMonth' ");
				contentsql.append(" and rs.plan_id is not null  ");
				contentsql.append(" and  to_char(rs.cycle_start_time,'yyyy/mm/dd')>='").append(doubleMonth[0]).append("'");
				contentsql.append("  and  to_char(rs.cycle_end_time,'yyyy/mm/dd')<='").append(doubleMonth[1]).append("'");
				contentsql.append(" union all ");
				//巡检周期为季度
				contentsql.append("select rs.plan_id, rs.city,rs.res_level, rs.inspect_state from pnr_inspect_plan_res rs");
				contentsql.append(" where rs.inspect_cycle='quarter' ");
				contentsql.append(" and rs.plan_id is not null  ");
				contentsql.append(" and  to_char(rs.cycle_start_time,'yyyy/mm/dd')>='").append(quarter[0]).append("'");
				contentsql.append("  and  to_char(rs.cycle_end_time,'yyyy/mm/dd')<='").append(quarter[1]).append("'");
				contentsql.append(" union all ");
				//巡检周期为半年
				contentsql.append("select rs.plan_id, rs.city,rs.res_level, rs.inspect_state from pnr_inspect_plan_res rs");
				contentsql.append(" where rs.inspect_cycle='halfOfYear' ");
				contentsql.append(" and rs.plan_id is not null  ");
				contentsql.append(" and  to_char(rs.cycle_start_time,'yyyy/mm/dd')>='").append(halfOfYear[0]).append("'");
				contentsql.append("  and  to_char(rs.cycle_end_time,'yyyy/mm/dd')<='").append(halfOfYear[1]).append("'");
				contentsql.append(" union all ");
				//巡检周期为年
				contentsql.append("select rs.plan_id, rs.city,rs.res_level, rs.inspect_state from pnr_inspect_plan_res rs");
				contentsql.append(" where rs.inspect_cycle='year' ");
				contentsql.append(" and rs.plan_id is not null  ");
				contentsql.append(" and  to_char(rs.cycle_start_time,'yyyy/mm/dd')>='").append(yearTime[0]).append("'");
				contentsql.append("  and  to_char(rs.cycle_end_time,'yyyy/mm/dd')<='").append(yearTime[1]).append("'");
				contentsql.append(") res left join  pnr_inspect_plan_main pm1 on res.plan_id = pm1.id");
				
				contentsql.append(" ) cm");					
				contentsql.append(" group by cm.year,cm.city ,cm.res_level");					
				contentsql.append(" ) lastSet");
			
				//------
				selectSql.append("select r.*,c.areaname name,c.areaname exportname from (");
				
				selectSql.append(" select fset.year,fset.city cityid,");
				selectSql.append(commonBuffer);
				selectSql.append(" from (").append(contentsql).append(" ) fset");
				selectSql.append(" group by fset.year,fset.city");
				
				selectSql.append(" ) r left join taw_system_area c on c.areaid = r.cityid");
				//-------
				
			}else if("2".equals(countType)){
				contentsql.append(" select lastSet.year,lastSet.city,lastSet.country ,lastSet.res_level,");
				contentsql.append(" lastSet.num,lastSet.cw,");
				contentsql.append(" fun_inspect_choose(lastSet.num,lastSet.city ,lastSet.country,lastSet.year,").append(month).append(",lastSet.res_level) cd");
				contentsql.append(" from(");
				contentsql.append(" select cm.year,cm.city,cm.country,cm.res_level,count(*) num ,");
				contentsql.append(" sum(case when cm.inspect_state = 1 and cm.month='").append(month).append("' then 1 else 0 end) cw  from (");
				
				contentsql.append(" select res.city,res.res_level, res.inspect_state,res.country, pm1.year,pm1.month from (");
				//巡检周期为月
				contentsql.append(" select rs.plan_id,rs.city,rs.res_level, rs.inspect_state, rs.country from pnr_inspect_plan_res rs ");
				contentsql.append(" where rs.inspect_cycle = 'month'");
				contentsql.append(" and rs.plan_id is not null");
				contentsql.append(" and to_char(rs.cycle_start_time,'yyyy/mm/dd') >='").append(oneMonth[0]).append("'");
				contentsql.append(" and to_char(rs.cycle_end_time,'yyyy/mm/dd') <='").append(oneMonth[1]).append("'");
				contentsql.append(" and rs.city='").append(areaId).append("'");
				contentsql.append(" union all ");
				//巡检周期为双月
				contentsql.append(" select rs.plan_id,rs.city,rs.res_level, rs.inspect_state, rs.country from pnr_inspect_plan_res rs ");
				contentsql.append(" where rs.inspect_cycle = 'doubleMonth'");
				contentsql.append(" and rs.plan_id is not null");
				contentsql.append(" and to_char(rs.cycle_start_time,'yyyy/mm/dd') >='").append(doubleMonth[0]).append("'");
				contentsql.append(" and to_char(rs.cycle_end_time,'yyyy/mm/dd') <='").append(doubleMonth[1]).append("'");
				contentsql.append(" and rs.city='").append(areaId).append("'");
				contentsql.append(" union all ");
				//巡检周期为季度
				contentsql.append(" select rs.plan_id,rs.city,rs.res_level, rs.inspect_state, rs.country from pnr_inspect_plan_res rs ");
				contentsql.append(" where rs.inspect_cycle = 'quarter'");
				contentsql.append(" and rs.plan_id is not null");
				contentsql.append(" and to_char(rs.cycle_start_time,'yyyy/mm/dd') >='").append(quarter[0]).append("'");
				contentsql.append(" and to_char(rs.cycle_end_time,'yyyy/mm/dd') <='").append(quarter[1]).append("'");
				contentsql.append(" and rs.city='").append(areaId).append("'");
				contentsql.append(" union all ");
				//巡检周期为半年
				contentsql.append(" select rs.plan_id,rs.city,rs.res_level, rs.inspect_state, rs.country from pnr_inspect_plan_res rs ");
				contentsql.append(" where rs.inspect_cycle = 'halfOfYear'");
				contentsql.append(" and rs.plan_id is not null");
				contentsql.append(" and to_char(rs.cycle_start_time,'yyyy/mm/dd') >='").append(halfOfYear[0]).append("'");
				contentsql.append(" and to_char(rs.cycle_end_time,'yyyy/mm/dd') <='").append(halfOfYear[1]).append("'");
				contentsql.append(" and rs.city='").append(areaId).append("'");
				contentsql.append(" union all ");
				//巡检周期为年
				contentsql.append(" select rs.plan_id,rs.city,rs.res_level, rs.inspect_state, rs.country from pnr_inspect_plan_res rs ");
				contentsql.append(" where rs.inspect_cycle = 'year'");
				contentsql.append(" and rs.plan_id is not null");
				contentsql.append(" and to_char(rs.cycle_start_time,'yyyy/mm/dd') >='").append(yearTime[0]).append("'");
				contentsql.append(" and to_char(rs.cycle_end_time,'yyyy/mm/dd') <='").append(yearTime[1]).append("'");
				contentsql.append(" and rs.city='").append(areaId).append("'");
				contentsql.append(") res left join  pnr_inspect_plan_main pm1 on res.plan_id = pm1.id");
				
	
				contentsql.append(" ) cm");					
				contentsql.append(" group by cm.year,cm.city,cm.country,cm.res_level");					
				contentsql.append(" ) lastSet");
				
				//------
				selectSql.append("select r.*,c.areaname name,c.areaname exportname from (");
				
				selectSql.append(" select fset.year,fset.city ,fset.country cityid,");
				selectSql.append(commonBuffer);
				selectSql.append(" from (").append(contentsql).append(" ) fset");
				selectSql.append(" group by fset.year,fset.city,fset.country");
				
				selectSql.append(" ) r left join taw_system_area c on c.areaid = r.cityid");
				//-------
				
			}else if("3".equals(countType)){
				
				contentsql.append(" select lastSet.year,lastSet.city,lastSet.country ,lastSet.res_level,");
				contentsql.append(" nvl(lastSet.charge_person,'无') username,");
				contentsql.append(" lastSet.num,lastSet.cw,");
				contentsql.append(" fun_inspect_choose(lastSet.num,lastSet.city ,lastSet.country,lastSet.year,").append(month).append(",lastSet.res_level) cd");
				contentsql.append(" from(");
				contentsql.append(" select cm.year,cm.city,cm.country,cm.res_level,cm.charge_person,count(*) num ,");
				contentsql.append(" sum(case when cm.inspect_state = 1 and cm.month='").append(month).append("' then 1 else 0 end) cw  from (");
				contentsql.append(" select res.city,res.res_level, res.inspect_state, res.country, nvl(g.charge_person, '无') charge_person, pm1.year,pm1.month from (");
				//巡检周期为月
				contentsql.append(" select rs.plan_id,rs.res_cfg_id,rs.city,rs.res_level, rs.country, rs.inspect_state from pnr_inspect_plan_res rs");
				contentsql.append(" where rs.inspect_cycle = 'month'");
				contentsql.append(" and rs.plan_id is not null");
				contentsql.append(" and to_char(rs.cycle_start_time,'yyyy/mm/dd') >='").append(oneMonth[0]).append("'");
				contentsql.append(" and to_char(rs.cycle_end_time,'yyyy/mm/dd') <='").append(oneMonth[1]).append("'");
				contentsql.append(" and rs.city='").append(city).append("'");
				contentsql.append(" and rs.country='").append(areaId).append("'");
				contentsql.append(" union all ");
				//巡检周期为双月
				contentsql.append(" select rs.plan_id,rs.res_cfg_id,rs.city,rs.res_level, rs.country, rs.inspect_state from pnr_inspect_plan_res rs");
				contentsql.append(" where rs.inspect_cycle = 'doubleMonth'");
				contentsql.append(" and rs.plan_id is not null");
				contentsql.append(" and to_char(rs.cycle_start_time,'yyyy/mm/dd') >='").append(doubleMonth[0]).append("'");
				contentsql.append(" and to_char(rs.cycle_end_time,'yyyy/mm/dd') <='").append(doubleMonth[1]).append("'");
				contentsql.append(" and rs.city='").append(city).append("'");
				contentsql.append(" and rs.country='").append(areaId).append("'");
				contentsql.append(" union all ");
				//巡检周期为季度
				contentsql.append(" select rs.plan_id,rs.res_cfg_id,rs.city,rs.res_level, rs.country, rs.inspect_state from pnr_inspect_plan_res rs");
				contentsql.append(" where rs.inspect_cycle = 'quarter'");
				contentsql.append(" and rs.plan_id is not null");
				contentsql.append(" and to_char(rs.cycle_start_time,'yyyy/mm/dd') >='").append(quarter[0]).append("'");
				contentsql.append(" and to_char(rs.cycle_end_time,'yyyy/mm/dd') <='").append(quarter[1]).append("'");
				contentsql.append(" and rs.city='").append(city).append("'");
				contentsql.append(" and rs.country='").append(areaId).append("'");
				contentsql.append(" union all ");
				//巡检周期为半年
				contentsql.append(" select rs.plan_id,rs.res_cfg_id,rs.city,rs.res_level, rs.country, rs.inspect_state from pnr_inspect_plan_res rs");
				contentsql.append(" where rs.inspect_cycle = 'halfOfYear'");
				contentsql.append(" and rs.plan_id is not null");
				contentsql.append(" and to_char(rs.cycle_start_time,'yyyy/mm/dd') >='").append(halfOfYear[0]).append("'");
				contentsql.append(" and to_char(rs.cycle_end_time,'yyyy/mm/dd') <='").append(halfOfYear[1]).append("'");
				contentsql.append(" and rs.city='").append(city).append("'");
				contentsql.append(" and rs.country='").append(areaId).append("'");
				contentsql.append(" union all ");
				//巡检周期为年
				contentsql.append(" select rs.plan_id,rs.res_cfg_id,rs.city,rs.res_level, rs.country, rs.inspect_state from pnr_inspect_plan_res rs");
				contentsql.append(" where rs.inspect_cycle = 'year'");
				contentsql.append(" and rs.plan_id is not null");
				contentsql.append(" and to_char(rs.cycle_start_time,'yyyy/mm/dd') >='").append(yearTime[0]).append("'");
				contentsql.append(" and to_char(rs.cycle_end_time,'yyyy/mm/dd') <='").append(yearTime[1]).append("'");
				contentsql.append(" and rs.city='").append(city).append("'");
				contentsql.append(" and rs.country='").append(areaId).append("'");
				
				contentsql.append(" ) res left join pnr_inspect_plan_main pm1 on res.plan_id = pm1.id ");
				contentsql.append(" left join pnr_res_config g on res.res_cfg_id = g.id ");
				
				contentsql.append(" ) cm");					
				contentsql.append(" group by cm.year,cm.city,cm.country,cm.res_level,cm.charge_person");					
				contentsql.append(" ) lastSet ");
				
				
				//------
				selectSql.append("select r.*,nvl(u.username,'无') exportname from (");
				
				selectSql.append(" select fset.year,fset.city ,fset.country cityid,fset.username name,");
				selectSql.append(commonBuffer);
				selectSql.append(" from (").append(contentsql).append(" ) fset ");
				selectSql.append(" group by fset.year,fset.city,fset.country,fset.username");
				
				selectSql.append(" ) r left join taw_system_user u on r.name=u.userid");
				//-------
				
		    }
					
				
				
		List<Map<String, Object>> list2 = this.getJdbcTemplate().queryForList(selectSql.toString());
		
		System.out.println("selectSql:"+selectSql);
		if (list2 != null) {
			Iterator<Map<String, Object>> iterator = list2.iterator();
			while (iterator.hasNext()) {
				Map<String, Object> o = iterator.next();
				InspectPlanResCountFromNew inspectPlanResCountFromNew = new InspectPlanResCountFromNew();
				inspectPlanResCountFromNew.setCityId(o.get("cityid").toString());
				inspectPlanResCountFromNew.setName(o.get("name").toString());
				inspectPlanResCountFromNew.setExportname(o.get("exportname").toString());
				inspectPlanResCountFromNew.setYear(o.get("year").toString());
				inspectPlanResCountFromNew.setMonth(month);
				inspectPlanResCountFromNew.setV_stage_a(o.get("v_stage_a").toString());
				inspectPlanResCountFromNew.setV_stage_w(o.get("v_stage_w").toString());
				inspectPlanResCountFromNew.setV_stage_d(o.get("v_stage_d").toString());
				inspectPlanResCountFromNew.setA_stage_a(o.get("a_stage_a").toString());
				inspectPlanResCountFromNew.setA_stage_w(o.get("a_stage_w").toString());
				inspectPlanResCountFromNew.setA_stage_d(o.get("a_stage_d").toString());
				inspectPlanResCountFromNew.setB_stage_a(o.get("b_stage_a").toString());
				inspectPlanResCountFromNew.setB_stage_w(o.get("b_stage_w").toString());
				inspectPlanResCountFromNew.setB_stage_d(o.get("b_stage_d").toString());
				inspectPlanResCountFromNew.setC_stage_a(o.get("c_stage_a").toString());
				inspectPlanResCountFromNew.setC_stage_w(o.get("c_stage_w").toString());
				inspectPlanResCountFromNew.setC_stage_d(o.get("c_stage_d").toString());
				inspectPlanResCountFromNew.setA_accessNetwork_a(o.get("a_accessNetwork_a").toString());
				inspectPlanResCountFromNew.setA_accessNetwork_w(o.get("a_accessNetwork_w").toString());
				inspectPlanResCountFromNew.setA_accessNetwork_d(o.get("a_accessNetwork_d").toString());
				inspectPlanResCountFromNew.setB_accessNetwork_a(o.get("b_accessNetwork_a").toString());
				inspectPlanResCountFromNew.setB_accessNetwork_w(o.get("b_accessNetwork_w").toString());
				inspectPlanResCountFromNew.setB_accessNetwork_d(o.get("b_accessNetwork_d").toString());
				inspectPlanResCountFromNew.setC_accessNetwork_a(o.get("c_accessNetwork_a").toString());
				inspectPlanResCountFromNew.setC_accessNetwork_w(o.get("c_accessNetwork_w").toString());
				inspectPlanResCountFromNew.setC_accessNetwork_d(o.get("c_accessNetwork_d").toString());
				inspectPlanResCountFromNew.setJ_tower_a(o.get("j_tower_a").toString());
				inspectPlanResCountFromNew.setJ_tower_w(o.get("j_tower_w").toString());
				inspectPlanResCountFromNew.setJ_tower_d(o.get("j_tower_d").toString());
				inspectPlanResCountFromNew.setY_tower_a(o.get("y_tower_a").toString());
				inspectPlanResCountFromNew.setY_tower_w(o.get("y_tower_w").toString());
				inspectPlanResCountFromNew.setY_tower_d(o.get("y_tower_d").toString());
				inspectPlanResCountFromNew.setZ_distribution_a(o.get("z_distribution_a").toString());
				inspectPlanResCountFromNew.setZ_distribution_w(o.get("z_distribution_w").toString());
				inspectPlanResCountFromNew.setZ_distribution_d(o.get("z_distribution_d").toString());
				inspectPlanResCountFromNew.setV_distribution_a(o.get("v_distribution_a").toString());
				inspectPlanResCountFromNew.setV_distribution_w(o.get("v_distribution_w").toString());
				inspectPlanResCountFromNew.setV_distribution_d(o.get("v_distribution_d").toString());
				inspectPlanResCountFromNew.setA_distribution_a(o.get("a_distribution_a").toString());
				inspectPlanResCountFromNew.setA_distribution_w(o.get("a_distribution_w").toString());
				inspectPlanResCountFromNew.setA_distribution_d(o.get("a_distribution_d").toString());
				inspectPlanResCountFromNew.setB_distribution_a(o.get("b_distribution_a").toString());
				inspectPlanResCountFromNew.setB_distribution_w(o.get("b_distribution_w").toString());
				inspectPlanResCountFromNew.setB_distribution_d(o.get("b_distribution_d").toString());
				inspectPlanResCountFromNew.setA_wlan_a(o.get("a_wlan_a").toString());
				inspectPlanResCountFromNew.setA_wlan_w(o.get("a_wlan_w").toString());
				inspectPlanResCountFromNew.setA_wlan_d(o.get("a_wlan_d").toString());
				inspectPlanResCountFromNew.setB_wlan_a(o.get("b_wlan_a").toString());
				inspectPlanResCountFromNew.setB_wlan_w(o.get("b_wlan_w").toString());
				inspectPlanResCountFromNew.setB_wlan_d(o.get("b_wlan_d").toString());
				inspectPlanResCountFromNew.setC_wlan_a(o.get("c_wlan_a").toString());
				inspectPlanResCountFromNew.setC_wlan_w(o.get("c_wlan_w").toString());
				inspectPlanResCountFromNew.setC_wlan_d(o.get("c_wlan_d").toString());
				
				inspectPlanResCountFromNew.setV_jifang_a(o.get("v_jifang_a").toString());
				inspectPlanResCountFromNew.setV_jifang_w(o.get("v_jifang_w").toString());
				inspectPlanResCountFromNew.setV_jifang_d(o.get("v_jifang_d").toString());
				inspectPlanResCountFromNew.setA_jifang_a(o.get("a_jifang_a").toString());
				inspectPlanResCountFromNew.setA_jifang_w(o.get("a_jifang_w").toString());
				inspectPlanResCountFromNew.setA_jifang_d(o.get("a_jifang_d").toString());
				inspectPlanResCountFromNew.setB_jifang_a(o.get("b_jifang_a").toString());
				inspectPlanResCountFromNew.setB_jifang_w(o.get("b_jifang_w").toString());
				inspectPlanResCountFromNew.setB_jifang_d(o.get("b_jifang_d").toString());
				inspectPlanResCountFromNew.setC_jifang_a(o.get("c_jifang_a").toString());
				inspectPlanResCountFromNew.setC_jifang_w(o.get("c_jifang_w").toString());
				inspectPlanResCountFromNew.setC_jifang_d(o.get("c_jifang_d").toString());
				
				list1.add(inspectPlanResCountFromNew);
			}
		}
		return list1;
	}
	public List<InspectPlanResCountFromSpecialty> findInspectPlanStatisticSpecialtyNew(String inspectState,String year , String month ,String countType,
			String areaId,String specialty,String person) {
		List<InspectPlanResCountFromSpecialty> list1 = new ArrayList<InspectPlanResCountFromSpecialty>();

		StringBuffer selectSql = new StringBuffer();	
		StringBuffer contentsql = new StringBuffer();	
		StringBuffer commonBuffer = new StringBuffer();
		
		contentsql.append("  where res.plan_id is not null ").append("  and m.year = '").append(year).append("'");
		contentsql.append(" and res.res_level = '").append(specialty).append("'");
		if("1".equals(countType)){
			contentsql.append(" and res.city = '").append(areaId).append("'");
		}else if("2".equals(countType)){
			contentsql.append(" and res.country = '").append(areaId).append("' ");
		}else if("3".equals(countType)){
			contentsql.append(" and res.country = ").append(areaId).append(" and decode(g.charge_person,null,'无',g.charge_person) = '").append(person).append("'");
		}
		if(!"4".equals(inspectState.trim())){
			contentsql.append(" and res.inspect_state = '").append(inspectState).append("'");
		}
		contentsql.append(" and m.month = '").append(month).append("')rs,");
		
		
		commonBuffer.append(" taw_system_dicttype   dic1, taw_system_dicttype   dic2,taw_system_dicttype   dic3,");
		commonBuffer.append(" taw_system_area       city1,taw_system_area  city2");
		commonBuffer.append(" where rs.res_level = dic1.dictid and rs.res_type = dic2.dictid");
		commonBuffer.append(" and rs.specialty = dic3.dictid  and rs.city = city1.areaid  and rs.country = city2.areaid");
		
		selectSql.append(" select  rs.res_name   res_name,dic3.dictname  res_specialty, dic2.dictname  res_type,dic1.dictname  res_level, city1.areaname city_name,");
		selectSql.append(" city2.areaname country_name from (select res.res_name,res.specialty,res.res_type,res.res_level,res.city,res.country from pnr_inspect_plan_res res");
		selectSql.append(" left join pnr_inspect_plan_main m on res.plan_id=m.id");
		selectSql.append(" left join pnr_res_config g on res.res_cfg_id = g.id");
		
		
		selectSql.append(contentsql).append(commonBuffer);
		 System.out.println(selectSql.toString());
		
		List<Map<String, Object>> list2 = this.getJdbcTemplate().queryForList(selectSql.toString());
		
		if (list2 != null) {
			Iterator<Map<String, Object>> iterator = list2.iterator();
			while (iterator.hasNext()) {
				Map<String, Object> o = iterator.next();
				InspectPlanResCountFromSpecialty inspectPlanResCountFromSpecialty = new InspectPlanResCountFromSpecialty();
				inspectPlanResCountFromSpecialty.setResName(o.get("res_name").toString());
				inspectPlanResCountFromSpecialty.setResSpecialty(o.get("res_specialty").toString());
				inspectPlanResCountFromSpecialty.setResType(o.get("res_type").toString());
				inspectPlanResCountFromSpecialty.setResLevel(o.get("res_level").toString());
				inspectPlanResCountFromSpecialty.setCityName(o.get("city_name").toString());
				inspectPlanResCountFromSpecialty.setCountryName(o.get("country_name").toString());
				
				list1.add(inspectPlanResCountFromSpecialty);
			}
		}
		
		return list1;
	}
	
	/**
	 * 统计当前月区县巡检情况
	 * @param city 地市
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<InspectStatisticArea> findInspectPlanStatisticCountry(String city){
		String sql = "select id,area,plan_res_num,done_rate,ave_time_on_site,res_Exception_Num,year,month" +
			" from pnr_inspect_Statis_country where city='"+city+"'";
		return this.getJdbcTemplate().query(sql, new InspectStatisticAreaMapper());
	}
	
	/**
	 * 统计历史区县巡检情况
	 * @param year
	 * @param month
	 * @param city 地市
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<InspectStatisticArea> findInspectPlanStatisticCountryHis(String year,String month,String city){
		String sql = "select id,area,plan_res_num,done_rate,ave_time_on_site,res_Exception_Num,year,month" +
			" from pnr_inspect_Statis_country_his where year='"+year+"' and month='"+month+"' and city='"+city+"'";
		return this.getJdbcTemplate().query(sql, new InspectStatisticAreaMapper());
	}
	
	private static final class InspectStatisticAreaMapper implements RowMapper{
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			InspectStatisticArea area = new InspectStatisticArea();
			area.setId(rs.getString("id"));   
			area.setArea(rs.getString("area"));
			area.setPlanResNum(rs.getInt("plan_res_num"));
			area.setDoneRate(rs.getFloat("done_rate"));
			area.setAveTimeOnSite(rs.getFloat("ave_time_on_site"));
			area.setResExceptionNum(rs.getInt("res_Exception_Num"));
			area.setYear(rs.getString("year"));
			area.setMonth(rs.getString("month"));
            return area;
		}
	}
	
	private static final class InspectStatisticPartnerMapper implements RowMapper{
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			InspectStatisticPartner partner = new InspectStatisticPartner();
			partner.setId(rs.getString("id"));   
			partner.setPartnerId(rs.getString("partner_Id"));
			partner.setPlanResNum(rs.getInt("plan_res_num"));
			partner.setDoneRate(rs.getFloat("done_rate"));
			partner.setAveTimeOnSite(rs.getFloat("ave_time_on_site"));
			partner.setResExceptionNum(rs.getInt("res_Exception_Num"));
			partner.setYear(rs.getString("year"));
			partner.setMonth(rs.getString("month"));
            return partner;
		}
	}
	
	/**
	 * 删除当前地市区县表的所有统计数据
	 */
	public void deleteStatisticAreaData(){
		String sqlCity = "delete from pnr_inspect_statis_city";
		getJdbcTemplate().execute(sqlCity);
		String sqlCountry = "delete from pnr_inspect_statis_country";
		getJdbcTemplate().execute(sqlCountry);
	}
	
	/**
	 * 移动当前地市区县表的统计数据到历史表
	 */
	public void moveStatisticAreaDataToHis(){
		String sqlCity = "insert into pnr_inspect_statis_city_his select * from pnr_inspect_statis_city";
		getJdbcTemplate().execute(sqlCity);
		String sqlCountry = "insert into pnr_inspect_statis_country_his select * from pnr_inspect_statis_country";
		getJdbcTemplate().execute(sqlCountry);
	}
	
	/**
	 * 按地市采集数据
	 * @param list
	 * @param year
	 * @param month
	 */
	public void saveStatisticCityData(final List<Map<String,String>> list,String year,String month){
		List<String> sqlList = Lists.newArrayList();
		for (Map<String, String> map : list) {
			String sql = "insert into pnr_inspect_Statis_city " +
				 "select '"+map.get("id")+"','"+map.get("city")+"',nvl(sum(res_num),0),nvl(sum(res_Done_Num)/sum(res_num),0)," +
					"nvl(sum(time_On_Site)/sum(res_num),0),nvl(sum(res_Exception_Num),0),'"+year+"','"+month+"'  " +
				 "from pnr_inspect_plan_main plan " +
				 	"left join pnr_dept dept on plan.partner_dept_id=dept.id " +
				 "where dept.area_id like '"+map.get("city")+"%' and year='"+year+"' and month='"+month+
				    "' and status=1 and res_Config=1 and approve_Status=1";
			sqlList.add(sql);
		}
		String[] sqlArray = new String[sqlList.size()];  
		sqlList.toArray(sqlArray);
		getJdbcTemplate().batchUpdate(sqlArray);
	}
	
	/**
	 * 按区县采集数据
	 * @param list
	 * @param year
	 * @param month
	 */
	public void saveStatisticCountryData(final List<Map<String,String>> list,String year,String month,String city){
		List<String> sqlList = Lists.newArrayList();
		for (Map<String, String> map : list) {
			String sql = "insert into pnr_inspect_Statis_country " +
				"select '"+map.get("id")+"','"+map.get("country")+"',nvl(sum(res_num),0),nvl(sum(res_Done_Num)/sum(res_num),0)," +
					"nvl(sum(time_On_Site)/sum(res_num),0),nvl(sum(res_Exception_Num),0),'"+year+"','"+month+"','"+city+"'" +
					"from pnr_inspect_plan_main plan " +
					"left join pnr_dept dept on plan.partner_dept_id=dept.id " +
					"where dept.area_id like '"+map.get("country")+"%' and year='"+year+"' and month='"+month+
					"' and status=1 and res_Config=1 and approve_Status=1";
			sqlList.add(sql);
		}
		String[] sqlArray = new String[sqlList.size()];  
		sqlList.toArray(sqlArray);
		getJdbcTemplate().batchUpdate(sqlArray);
	}
	
	/**
	 * 统计当月总代维公司巡检情况
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<InspectStatisticPartner> findInspectPlanStatisticHeadPnr(){
		String sql = "select id,partner_id,plan_res_num,done_rate,ave_time_on_site,res_Exception_Num,year,month" +
			" from pnr_inspect_Statis_headpnr ";
		return this.getJdbcTemplate().query(sql, new InspectStatisticPartnerMapper());
	}
	
	/**
	 * 统计历史总代维公司巡检情况
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<InspectStatisticPartner> findInspectPlanStatisticHeadPnrHis(String year,String month){
		String sql = "select id,partner_id,plan_res_num,done_rate,ave_time_on_site,res_Exception_Num,year,month" +
			" from pnr_inspect_Statis_headpnr_his where year='"+year+"' and month='"+month+"'";
		return this.getJdbcTemplate().query(sql, new InspectStatisticPartnerMapper());
	}
	
	/**
	 * 统计当月地市代维公司巡检情况
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<InspectStatisticPartner> findInspectPlanStatisticCityPnr(String headPartnerId){
		String sql = "select id,partner_id,plan_res_num,done_rate,ave_time_on_site,res_Exception_Num,year,month" +
			" from pnr_inspect_Statis_citypnr where parent_partner_id='"+headPartnerId+"'";
		return this.getJdbcTemplate().query(sql, new InspectStatisticPartnerMapper());
	}
	
	/**
	 * 统计历史地市代维公司巡检情况
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<InspectStatisticPartner> findInspectPlanStatisticCityPnrHis(String headPartnerId,String year,String month){
		String sql = "select id,partner_id,plan_res_num,done_rate,ave_time_on_site,res_Exception_Num,year,month" +
			" from pnr_inspect_Statis_citypnr_his where year='"+year+"' and month='"+month+"' and parent_partner_id='"+headPartnerId+"'";
		return this.getJdbcTemplate().query(sql, new InspectStatisticPartnerMapper());
	}
	
	/**
	 * 统计当月区县代维公司巡检情况
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<InspectStatisticPartner> findInspectPlanStatisticCountryPnr(String cityPartnerId){
		String sql = "select id,partner_id,plan_res_num,done_rate,ave_time_on_site,res_Exception_Num,year,month" +
			" from pnr_inspect_Statis_countrypnr where parent_partner_id='"+cityPartnerId+"'";
		return this.getJdbcTemplate().query(sql, new InspectStatisticPartnerMapper());
	}
	
	/**
	 * 统计历史区县代维公司巡检情况
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<InspectStatisticPartner> findInspectPlanStatisticCountryPnrHis(String cityPartnerId,String year,String month){
		String sql = "select id,partner_id,plan_res_num,done_rate,ave_time_on_site,res_Exception_Num,year,month" +
			" from pnr_inspect_Stat_country_his where year='"+year+"' and month='"+month+"' and parent_partner_id='"+cityPartnerId+"'";
		return this.getJdbcTemplate().query(sql, new InspectStatisticPartnerMapper());
	}
	
	/**
	 * 删除当前总、地市、区县表的所有统计数据
	 */
	public void deleteStatisticPartnerData(){
		String sqlHead = "delete from pnr_inspect_Statis_headpnr";
		String sqlCity = "delete from pnr_inspect_Statis_citypnr";
		String sqlCountry = "delete from pnr_inspect_Statis_countrypnr";
		getJdbcTemplate().execute(sqlHead);
		getJdbcTemplate().execute(sqlCity);
		getJdbcTemplate().execute(sqlCountry);
	}
	
	/**
	 * 移动当前总、地市、区县表的统计数据到历史表
	 */
	public void moveStatisticPartnerDataToHis(){
		String sqlHead = "insert into pnr_inspect_Statis_headpnr_his select * from pnr_inspect_Statis_headpnr";
		String sqlCity = "insert into pnr_inspect_Statis_citypnr_his select * from pnr_inspect_Statis_citypnr";
		String sqlCountry = "insert into pnr_inspect_Stat_country_his select * from pnr_inspect_Statis_countrypnr";
		getJdbcTemplate().execute(sqlHead);
		getJdbcTemplate().execute(sqlCity);
		getJdbcTemplate().execute(sqlCountry);
	}
	
	/**
	 * 采集所有总公司巡检数据
	 * @param year
	 * @param month
	 */
	public void saveStatisticHearPartnerData(final List<Map<String,String>> list,String year,String month){
		List<String> sqlList = Lists.newArrayList();
		for (Map<String, String> map : list) {
			String sql = "insert into pnr_inspect_Statis_headpnr " +
			 "select '"+map.get("id")+"','"+map.get("partnerId")+"',nvl(sum(res_num),0),nvl(sum(res_Done_Num)/sum(res_num),0)," +
				"nvl(sum(time_On_Site)/sum(res_num),0),nvl(sum(res_Exception_Num),0),'"+year+"','"+month+"'  " +
			 "from pnr_inspect_plan_main plan " +
			 	"left join pnr_dept dept on plan.partner_dept_id=dept.id " +
			 "where dept.dept_mag_id like '"+map.get("partnerDeptId")+"%' and year='"+year+"' and month='"+month+
			    "' and status=1 and res_Config=1 and approve_Status=1";
			sqlList.add(sql);
		}
		String[] sqlArray = new String[sqlList.size()];  
		sqlList.toArray(sqlArray);
		getJdbcTemplate().batchUpdate(sqlArray);
	}
	
	/**
	 * 采集所有地市公司巡检数据
	 * @param partnerList
	 * @param year
	 * @param month
	 * @param parentPartnerId
	 */
	public void saveStatisticCityPartnerData(final List<Map<String,String>> partnerList,String year,String month,String parentPartnerId){
		List<String> sqlList = Lists.newArrayList();
		for (Map<String, String> map : partnerList) {
			String sql = "insert into pnr_inspect_Statis_citypnr " +
			 "select '"+map.get("id")+"','"+map.get("partnerId")+"',nvl(sum(res_num),0),nvl(sum(res_Done_Num)/sum(res_num),0)," +
				"nvl(sum(time_On_Site)/sum(res_num),0),nvl(sum(res_Exception_Num),0),'"+year+"','"+month+"','"+parentPartnerId+"' " +
			 "from pnr_inspect_plan_main plan " +
			 	"left join pnr_dept dept on plan.partner_dept_id=dept.id " +
			 "where dept.dept_mag_id like '"+map.get("partnerDeptId")+"%' and year='"+year+"' and month='"+month+
			    "' and status=1 and res_Config=1 and approve_Status=1";
			sqlList.add(sql);
		}
		String[] sqlArray = new String[sqlList.size()];  
		sqlList.toArray(sqlArray);
		getJdbcTemplate().batchUpdate(sqlArray);
	}
	/**
	 * 采集所有区县公司巡检数据
	 * @param partnerList
	 * @param year
	 * @param month
	 * @param parentPartnerId
	 */
	public void saveStatisticCountryPartnerData(final List<Map<String,String>> partnerList,String year,String month,String parentPartnerId){
		List<String> sqlList = Lists.newArrayList();
		for (Map<String, String> map : partnerList) {
			String sql = "insert into pnr_inspect_Statis_countrypnr " +
			 "select '"+map.get("id")+"','"+map.get("partnerId")+"',nvl(sum(res_num),0),nvl(sum(res_Done_Num)/sum(res_num),0)," +
				"nvl(sum(time_On_Site)/sum(res_num),0),nvl(sum(res_Exception_Num),0),'"+year+"','"+month+"','"+parentPartnerId+"'  " +
			 "from pnr_inspect_plan_main plan " +
			 	"left join pnr_dept dept on plan.partner_dept_id=dept.id " +
			 "where dept.dept_mag_id like '"+map.get("partnerDeptId")+"%' and year='"+year+"' and month='"+month+
			    "' and status=1 and res_Config=1 and approve_Status=1";
			sqlList.add(sql);
		}
		String[] sqlArray = new String[sqlList.size()];  
		sqlList.toArray(sqlArray);
		getJdbcTemplate().batchUpdate(sqlArray);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String,String>> findAllHeadPartnerId(){
		String sql = "select id as partnerId,dept_mag_id as partnerDeptId " +
				"from pnr_dept where id=interface_head_id and deleted<>1";
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	/**
	 * 查询所有子代维公司
	 * @param parentPartnerId 上级代维公司ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,String>> findAllSubPartner(String parentPartnerId){
		String sql = "select id as partnerId,dept_mag_id as partnerDeptId,name as partnerName " +
				"from pnr_dept where interface_head_id='"+parentPartnerId+
				"' and id<>interface_head_id and deleted<>1";
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	
}
