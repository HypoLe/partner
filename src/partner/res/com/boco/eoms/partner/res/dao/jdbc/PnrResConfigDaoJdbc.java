package com.boco.eoms.partner.res.dao.jdbc;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.eoms.partner.res.dao.IPnrResConfigDaoJdbc;
import com.boco.eoms.partner.res.model.PnrResConfig;
import com.boco.eoms.partner.res.model.PnrResConfigStation;
import com.boco.eoms.partner.res.model.PnrResFamilyBroadband;
import com.boco.eoms.partner.res.model.PnrResIron;
import com.boco.eoms.partner.res.model.PnrResJieke;
import com.boco.eoms.partner.res.model.PnrResLine;
import com.boco.eoms.partner.res.model.PnrResRepeaters;
import com.boco.eoms.partner.res.model.PnrResWlan;
import com.google.common.collect.Lists;

/** 
 * Description: 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liaojiming 
 * @version:    1.0 
 */ 
public class PnrResConfigDaoJdbc extends JdbcDaoSupport implements IPnrResConfigDaoJdbc {

	/**
	 * Excel导入保存
	 */
	public void importData(List<Object> mainList,List<Object> subList){
		
		String insertMainSql = "insert into pnr_res_config (id, specialty,res_name , res_type,res_level ," +
		" res_longitude,res_latitude,sub_res_table ,sub_res_id,city ,country,  creator,create_time , street , company_name ,contact_name , phone,remarks,service_region,eographical_environment,region_type)";
		
		String insertSubSql = "";
		
		List<String> mainSqlList = Lists.newArrayList();
		
		List<String> subSqlList = Lists.newArrayList();
		//先保存子表，再保存主表
		
		//先得到子表是哪一张表，由于一次导入的都是一个子类
		String subResTable = ((PnrResConfig) mainList.get(0)).getSubResTable();
		if("pnr_res_station".equals(subResTable)){
			insertSubSql = "insert into  pnr_res_station (id,address ,property_type,sharing ,net_type,g2_open_time,td_open_time ,adjustment_message) ";
			
			for(int i=0;i<subList.size();i++){
				PnrResConfigStation pnr = (PnrResConfigStation) subList.get(i);
				String sql = insertSubSql+" values ('"+pnr.getId()+"','"+pnr.getAddress()+"',"+pnr.getPropertyType()+",'"+pnr.getSharing()

+"','"+pnr.getNetType()+"','" +
						pnr.getOpenTime()+"','" +pnr.getTDOpenTime()+"','"+pnr.getAdjustmentMessage()+"') ";
				subSqlList.add(sql);
			}
			String[] sqlArray = new String[subSqlList.size()];  
			subSqlList.toArray(sqlArray);
			getJdbcTemplate().batchUpdate(sqlArray);
		}else if("pnr_res_line".equals(subResTable)){
			insertSubSql = "insert into  pnr_res_line (id ,end_longitude,  end_latitude ,tube_side,single_routing ,like_routing)";
			for(int i=0;i<subList.size();i++){
				PnrResLine pnr = (PnrResLine) subList.get(i);
				String sql = insertSubSql+ " values ('"+pnr.getId()+"','"+pnr.getEndLongitude()+"','"+pnr.getEndLatitude()+"','" +pnr.getTubeSide()

+"','"+
				pnr.getSingleRouting()+"', '"+pnr.getLikeRouting()+"')";
				subSqlList.add(sql);
			}
			String[] sqlArray = new String[subSqlList.size()];  
			subSqlList.toArray(sqlArray);
			getJdbcTemplate().batchUpdate(sqlArray);
		}else if("pnr_res_repeaters".equals(subResTable)){
			insertSubSql = "insert into  pnr_res_repeaters (id,address,property_type ,sharing ,net_type ,g2_open_time ,td_open_time ,adjustment_message )";
			for(int i=0;i<subList.size();i++){
				PnrResRepeaters pnr = (PnrResRepeaters) subList.get(i);
				String sql = insertSubSql+" values ('"+pnr.getId()+"','"+pnr.getAddress()+"','"+pnr.getPropertyType()+"','" +pnr.getSharing()

+"','"+pnr.getNetType()+"','"+
				pnr.getOpenTime()+"','"+pnr.getTDOpenTime()+"',' "+pnr.getAdjustmentMessage()+"') ";
				subSqlList.add(sql);
			}
			String[] sqlArray = new String[subSqlList.size()];  
			subSqlList.toArray(sqlArray);
			getJdbcTemplate().batchUpdate(sqlArray);
		}else if("pnr_res_iron".equals(subResTable)){
			insertSubSql = "insert into  pnr_res_iron (id,iron_type ,indoor_number,outdoor_number, phase_number ,power ) ";
			for(int i=0;i<subList.size();i++){
				PnrResIron pnr = (PnrResIron) subList.get(i);
				String sql = insertSubSql +"values ('"+pnr.getId()+"','"+pnr.getIronType()+"','"+pnr.getIndoorNumber()+"','"+pnr.getOutdoorNumber()

+"','" 
				+pnr.getPhaseNumber()+"','"+pnr.getPower()+"')";
				subSqlList.add(sql);
			}
			String[] sqlArray = new String[subSqlList.size()];  
			subSqlList.toArray(sqlArray);
			getJdbcTemplate().batchUpdate(sqlArray);
		}else if("pnr_res_jieke".equals(subResTable)){
			insertSubSql = "insert into  pnr_res_jieke (id,client_number,client_type,station_name,station_number ) ";
			for(int i=0;i<subList.size();i++){
				PnrResJieke pnr = (PnrResJieke) subList.get(i);
				String sql = insertSubSql+" values ('"+pnr.getId()+"','"+pnr.getClientNumber()+"','"+pnr.getClientType()+"','" +pnr.getStationName()

+"','"+pnr.getStationNumber()+"')";
				subSqlList.add(sql);
			}
			String[] sqlArray = new String[subSqlList.size()];  
			subSqlList.toArray(sqlArray);
			getJdbcTemplate().batchUpdate(sqlArray);
		}else if("pnr_res_wlan".equals(subResTable)){
			insertSubSql = "insert into  pnr_res_wlan (id,address,property_type ,sharing ,net_type ,g2_open_time ,td_open_time ,adjustment_message )";
			for(int i=0;i<subList.size();i++){
				PnrResWlan pnr = (PnrResWlan) subList.get(i);
				String sql = insertSubSql+" values ('"+pnr.getId()+"','"+pnr.getAddress()+"','"+pnr.getPropertyType()+"','" +pnr.getSharing()

+"','"+pnr.getNetType()+"','"+
				pnr.getOpenTime()+"','"+pnr.getTDOpenTime()+"',' "+pnr.getAdjustmentMessage()+"') ";
				subSqlList.add(sql);
			}
			String[] sqlArray = new String[subSqlList.size()];  
			subSqlList.toArray(sqlArray);
			getJdbcTemplate().batchUpdate(sqlArray);
		}else if("pnr_res_family_broadband".equals(subResTable)){
			insertSubSql = "insert into  pnr_res_family_broadband (id,client_number,client_type,station_name,station_number ) ";
			for(int i=0;i<subList.size();i++){
				PnrResFamilyBroadband pnr = (PnrResFamilyBroadband) subList.get(i);
				String sql = insertSubSql+" values ('"+pnr.getId()+"','"+pnr.getClientNumber()+"','"+pnr.getClientType()+"','" +pnr.getStationName()

+"','"+pnr.getStationNumber()+"')";
				subSqlList.add(sql);
			}
			String[] sqlArray = new String[subSqlList.size()];  
			subSqlList.toArray(sqlArray);
			getJdbcTemplate().batchUpdate(sqlArray);
		}
		
		//保存主表inspect_cycle,city ,country, execute_dept , creator,create_time , street , company_name ,contact_name , phone 
		for(int i=0;i<mainList.size();i++){
			PnrResConfig pnr = (PnrResConfig) mainList.get(i);
			String sql = insertMainSql+" values('"+pnr.getId()+"','"+
					pnr.getSpecialty()+"','"+pnr.getResName()+"','"+pnr.getResType()+"','"+pnr.getResLevel()+"'," +pnr.getResLongitude()+","+
					pnr.getResLatitude()+",'"+pnr.getSubResTable()+"','"+pnr.getSubResId()+"','"+pnr.getCity()+
							"','"+pnr.getCountry()+"','"+pnr.getCreator()+"','"+pnr.getCreateTime()+"','"+pnr.getStreet()+"','" +
									pnr.getCompanyName()+"','"+pnr.getContactName()+"','"+pnr.getPhone()+"','"+pnr.getRemarks()

+"','"+pnr.getServiceRegion()+"','"+pnr.getEographicalEnvironment()+"','"+pnr.getRegionType()+"')";
			mainSqlList.add(sql);
		}
		String[] mainsqlArray = new String[mainSqlList.size()];  
		mainSqlList.toArray(mainsqlArray);
		getJdbcTemplate().batchUpdate(mainsqlArray);
	}

	/**
	 * Excel导出
	 */
	public List<Map<String,Object>> excelExport(String specialty,List<String> condition) {
		
		String sql = "";
		
		String cityCondition = "";
		
		if("".equals(condition.get(0))){
			
		}else{
			cityCondition = " and m.city='"+condition.get(0)+"'";
		}
		if("".equals(condition.get(1))){
			
		}else{
			cityCondition= cityCondition+" and country='"+condition.get(1)+"'";
		}
		
		if("1122501".equals(specialty)){
			sql = "select s.id as sid,s.address,s.property_type,s.sharing,s.net_type,g2_open_time,s.td_open_time,s.adjustment_message,m.id as id,m.execute_object," +
					

"m.execute_type,m.specialty,m.res_name,m.res_type,m.res_level,m.res_longitude,m.res_latitude,m.city,m.country,m.street,m.company_name," +
					"m.contact_name,m.phone,m.remarks,m.service_region,m.eographical_environment,m.region_type from pnr_res_station s right join pnr_res_config m on s.id = m.sub_res_id " +
					" where specialty='1122501' "+cityCondition;
		}else if("1122502".equals(specialty)){
			sql = "select s.id,s.end_longitude,s.end_latitude,s.tube_side,s.single_routing,s.like_routing, m.id,m.execute_object,m.execute_type," +
					

"m.specialty,m.res_name,m.res_type,m.res_level,m.res_longitude,m.res_latitude,m.city,m.country,m.street,m.company_name,m.contact_name," +
					"m.phone,m.remarks,m.service_region,m.eographical_environment,m.region_type from pnr_res_line s right join pnr_res_config m on s.id = m.sub_res_id " +
					" where specialty='1122502' "+cityCondition;
		}else if("1122503".equals(specialty)){
			sql = "select s.id,s.address,s.property_type,s.sharing,s.net_type,g2_open_time,s.td_open_time,s.adjustment_message,m.id,m.execute_object," +
				"m.execute_type,m.specialty,m.res_name,m.res_type,m.res_level,m.res_longitude,m.res_latitude,m.city,m.country,m.street,m.company_name," 

+
				"m.contact_name,m.phone,m.remarks,m.service_region,m.eographical_environment,m.region_type from pnr_res_repeaters s right join pnr_res_config m on s.id = m.sub_res_id" +
				" where specialty='1122503' "+cityCondition;
		}else if("1122504".equals(specialty)){
			sql = "select s.id,s.iron_type,s.indoor_number,s.outdoor_number,s.phase_number,s.power,  m.execute_object,m.execute_type,m.specialty," +
					

"m.res_name,m.res_type,m.res_level,m.res_longitude,m.res_latitude,m.city,m.country,m.street,m.company_name,m.contact_name,m.phone," +
					"m.remarks,m.service_region,m.eographical_environment,m.region_type from pnr_res_iron s right join pnr_res_config m on s.id = m.sub_res_id" +
					" where specialty='1122504' "+cityCondition;
		}else if("1122505".equals(specialty)){
			sql = "select s.id,s.client_number,s.client_type,s.station_name,s.station_number,  m.execute_object,m.execute_type,m.specialty," +
					

"m.res_name,m.res_type,m.res_level,m.res_longitude,m.res_latitude,m.city,m.country,m.street,m.company_name,m.contact_name,m.phone," +
					"m.remarks,m.service_region,m.eographical_environment,m.region_type from pnr_res_jieke s right join pnr_res_config m on s.id = m.sub_res_id " +
					" where specialty='1122505' "+cityCondition;
		}else if("1122506".equals(specialty)){
			sql = "select s.id as sid,s.address,s.property_type,s.sharing,s.net_type,g2_open_time,s.td_open_time,s.adjustment_message,m.id as id,m.execute_object," +
					

"m.execute_type,m.specialty,m.res_name,m.res_type,m.res_level,m.res_longitude,m.res_latitude,m.city,m.country,m.street,m.company_name," +
					"m.contact_name,m.phone,m.remarks,m.service_region,m.eographical_environment,m.region_type from pnr_res_wlan s right join pnr_res_config m on s.id = m.sub_res_id " +
					" where specialty='1122506' "+cityCondition;
		}else if("1122507".equals(specialty)){
			sql = "select s.id,s.client_number,s.client_type,s.station_name,s.station_number,  m.execute_object,m.execute_type,m.specialty," +
			"m.res_name,m.res_type,m.res_level,m.res_longitude,m.res_latitude,m.city,m.country,m.street,m.company_name,m.contact_name,m.phone," +
			"m.remarks,m.service_region,m.eographical_environment,m.region_type from pnr_res_family_broadband s right join pnr_res_config m on s.id = m.sub_res_id " +
			" where specialty='1122507' "+cityCondition;
		}else{//此时只查询主表里面的数据
			sql = "select  m.execute_object,m.execute_type,m.specialty," +
			"m.res_name,m.res_type,m.res_level,m.res_longitude,m.res_latitude,m.city,m.country,m.street,m.company_name,m.contact_name,m.phone," +
			"m.remarks,m.service_region,m.eographical_environment,m.region_type from  pnr_res_config m  " +
			" where specialty='"+specialty+"' "+cityCondition;
		}
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	/**
	 * 查询未分配周期和执行对象的资源数
	 */
	public List findUnAssignCycleAndExecuteObject(String whereSql){
		String sql = "select specialty,count(specialty) count from pnr_res_config where "+whereSql+" group by specialty";
		List cycleList = this.getJdbcTemplate().queryForList(sql);
		return cycleList;
	}
	/**
	 * 
	 *@Description：statement批处理完成数据的插入
	 *@date May 14, 2013 9:23:14 AM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@param mainList
	 *@param subList
	 *@param st1:子表的statment
	 *@param st2:主表的statement
	 *@return
	 *@throws Exception
	 */
	public  void importData(List<Object> mainList, List<Object> subList,PreparedStatement st1,PreparedStatement st2) throws Exception{
		//先保存子表，再保存主表
		//先得到子表是哪一张表，由于一次导入的都是一个子类
		String subResTable = ((PnrResConfig) mainList.get(0)).getSubResTable();
		//"insert into  pnr_res_station (id,address ,property_type,sharing ,net_type,g2_open_time,td_open_time ,adjustment_message) "
		//+" values(?,?,?,?,?,?,?,?)"
		if("pnr_res_station".equals(subResTable)){
			for(int i=0;i<subList.size();i++){
				PnrResConfigStation pnr = (PnrResConfigStation) subList.get(i);
				st2.setString(1, pnr.getId());
				st2.setString(2, pnr.getAddress());
				st2.setObject(3, pnr.getPropertyType());
				st2.setString(4, pnr.getSharing());
				st2.setString(5, pnr.getNetType());
				st2.setString(6, pnr.getOpenTime());
				st2.setString(7, pnr.getTDOpenTime());
				st2.setString(8, pnr.getAdjustmentMessage());
				st2.addBatch();
			}
		}else if("pnr_res_line".equals(subResTable)){
			//insertSubSql = "insert into  pnr_res_line (id ,end_longitude,  end_latitude ,tube_side,single_routing ,like_routing)";
			for(int i=0;i<subList.size();i++){
				PnrResLine pnr = (PnrResLine) subList.get(i);
				st2.setString(1, pnr.getId());
				st2.setString(2, pnr.getEndLongitude());
				st2.setString(3, pnr.getEndLatitude());
				st2.setString(4, pnr.getTubeSide());
				st2.setString(5, pnr.getSingleRouting());
				st2.setString(6, pnr.getLikeRouting());
				st2.addBatch();
			}
		}else if("pnr_res_repeaters".equals(subResTable)){
			//insertSubSql = "insert into  pnr_res_repeaters (id,address,property_type ,sharing ,net_type ,g2_open_time ,td_open_time ,adjustment_message )";
			for(int i=0;i<subList.size();i++){
				PnrResRepeaters pnr = (PnrResRepeaters) subList.get(i);
				st2.setString(1, pnr.getId());
				st2.setString(2, pnr.getAddress());
				st2.setObject(3, pnr.getPropertyType());
				st2.setString(4, pnr.getSharing());
				st2.setString(5, pnr.getNetType());
				st2.setString(6, pnr.getOpenTime());
				st2.setString(7, pnr.getTDOpenTime());
				st2.setString(8, pnr.getAdjustmentMessage());
				st2.addBatch();
			}
		}else if("pnr_res_iron".equals(subResTable)){
			//insertSubSql = "insert into  pnr_res_iron (id,iron_type ,indoor_number,outdoor_number, phase_number ,power ) ";
			for(int i=0;i<subList.size();i++){
				PnrResIron pnr = (PnrResIron) subList.get(i);
				st2.setString(1, pnr.getId());
				st2.setString(2, pnr.getIronType());
				st2.setString(3, pnr.getIndoorNumber());
				st2.setString(4, pnr.getOutdoorNumber());
				st2.setString(5, pnr.getPhaseNumber());
				st2.setString(6, pnr.getPower());
				st2.addBatch();
			}
		}else if("pnr_res_jieke".equals(subResTable)){
			//insertSubSql = "insert into  pnr_res_jieke (id,client_number,client_type,station_name,station_number ) ";
			for(int i=0;i<subList.size();i++){
				PnrResJieke pnr = (PnrResJieke) subList.get(i);
				st2.setString(1, pnr.getId());
				st2.setString(2, pnr.getClientNumber());
				st2.setString(3, pnr.getClientType());
				st2.setString(4, pnr.getStationName());
				st2.setString(5, pnr.getStationNumber());
				st2.addBatch();
			}
		}else if("pnr_res_wlan".equals(subResTable)){
			//insertSubSql = "insert into  pnr_res_wlan (id,address,property_type ,sharing ,net_type ,g2_open_time ,td_open_time ,adjustment_message )";
			for(int i=0;i<subList.size();i++){
				PnrResWlan pnr = (PnrResWlan) subList.get(i);
				st2.setString(1, pnr.getId());
				st2.setString(2, pnr.getAddress());
				st2.setObject(3, pnr.getPropertyType());
				st2.setString(4, pnr.getSharing());
				st2.setString(5, pnr.getNetType());
				st2.setString(6, pnr.getOpenTime());
				st2.setString(7, pnr.getTDOpenTime());
				st2.setString(8, pnr.getAdjustmentMessage());
				st2.addBatch();
			}
		}else if("pnr_res_family_broadband".equals(subResTable)){
			//insertSubSql = "insert into  pnr_res_family_broadband (id,client_number,client_type,station_name,station_number ) ";
			for(int i=0;i<subList.size();i++){
				PnrResFamilyBroadband pnr = (PnrResFamilyBroadband) subList.get(i);
				st2.setString(1, pnr.getId());
				st2.setString(2, pnr.getClientNumber());
				st2.setString(3, pnr.getClientType());
				st2.setString(4, pnr.getStationName());
				st2.setString(5, pnr.getStationNumber());
				st2.addBatch();
			}
		}
		//String insertMainSql = "insert into pnr_res_config (id, specialty,res_name , res_type,res_level ," +
		//" res_longitude,res_latitude,sub_res_table ,sub_res_id,city ,country,  creator,create_time , street , company_name ,contact_name , phone,remarks,service_region,eographical_environment,region_type)";
		//String insertSubSql = "";
		for(int i=0;i<mainList.size();i++){
			PnrResConfig pnr = (PnrResConfig) mainList.get(i);
			st1.setString(1, pnr.getId());
			st1.setString(2, pnr.getSpecialty());
			st1.setString(3, pnr.getResName());
			st1.setString(4, pnr.getResType());
			st1.setString(5, pnr.getResLevel());
			st1.setString(6, pnr.getResLongitude());
			st1.setString(7, pnr.getResLatitude());
			st1.setString(8, pnr.getSubResTable());
			st1.setString(9, pnr.getSubResId());
			st1.setString(10, pnr.getCity());
			st1.setString(11, pnr.getCountry());
			st1.setString(12, pnr.getCreator());
			st1.setString(13, pnr.getCreateTime());
			st1.setString(14, pnr.getStreet());
			st1.setString(15, pnr.getCompanyName());
			st1.setString(16, pnr.getContactName());
			st1.setString(17, pnr.getPhone());
			st1.setString(18, pnr.getRemarks());
			st1.setString(19, pnr.getServiceRegion());
			st1.setString(20, pnr.getEographicalEnvironment());
			st1.setString(21, pnr.getRegionType());
			st1.addBatch();
		}
	}

	public List getResPersonFlag(){
		String sql = "SELECT * FROM taw_res_person_flag WHERE flag = 1";
		return this.getJdbcTemplate().queryForList(sql);
	}
	public List getPanResByUser(String userId,String deptId){
		String sql = "SELECT * FROM PNR_RES_CONFIG WHERE execute_dept = '"+deptId+"' AND charge_person = '"+userId+"'";
		return this.getJdbcTemplate().queryForList(sql);
	}
	public int updateResource(String sql){
		return this.getJdbcTemplate().update(sql);
	}
}
