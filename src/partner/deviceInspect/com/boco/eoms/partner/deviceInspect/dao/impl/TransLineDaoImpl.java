package com.boco.eoms.partner.deviceInspect.dao.impl;

import org.joda.time.LocalDate;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;
import com.boco.eoms.partner.deviceInspect.dao.ITransLineDao;
import com.boco.eoms.partner.netresource.dao.hibernate.EomsDaoHibernateImpl;

@SuppressWarnings("unchecked")
public class TransLineDaoImpl extends EomsDaoHibernateImpl implements ITransLineDao{
	
	/**
	 * 产生巡检任务（和以前的IInspectPlanMgr中的generateInspectPlanRes一样）
	 * @param cycle 周期
	 * @param city  地市     
	 * @param cycleStartTime 周期开始时间
	 * @param cycleEndTime   周期结束时间      
	 * @param createTime     资源产生日期  
	 */
	public void generateInspectPlanRes(String cycle,String city,String cycleStartTime,
			String cycleEndTime,String createTime){
		cycleStartTime = CommonSqlHelper.formatDateTime(cycleStartTime);
		cycleEndTime = CommonSqlHelper.formatDateTime(cycleEndTime);
		
		String executeObjectNotEmpty = CommonSqlHelper.formatNotEmpty("c.execute_object");
		String executeDeptNotEmpty = CommonSqlHelper.formatNotEmpty("c.execute_Dept");
		String arrived_rate_id = CommonSqlHelper.formatNotEmpty("c.arrived_rate_id");
//		String location_Cycle_Id = CommonSqlHelper.formatNotEmpty("c.location_Cycle_Id");
		
		//插入数据到临时表
		String tmpTableName = "tmp_pnr_inspect_plan_res";
		String selectSql = " select seq_pnr_inspect_plan_res.NEXTVAL as id,c.id as res_cfg_id,c.specialty,c.res_type,c.res_name," +
									"c.res_level,c.city,c.country,c.inspect_cycle,"+cycleStartTime+" as cycle_start_time,"+cycleEndTime+" as cycle_end_time," + 
									"c.execute_object,c.execute_dept,c.execute_type,0 as change_state,c.res_longitude, c.res_latitude," +
									"c.end_longitude,c.end_latitude,'" + createTime + "' as create_time,0 as force_assign,0 as inspect_State,t.item_num,t.id as template_Id,c.last_inspect_Time,c.eographical_environment,c.region_type,c.tl_inspect_flag,c.tl_dis,c.tl_wire,c.tl_system_level,c.tl_seg,c.tl_lay_type,c.tl_fiber_count,c.tl_pa_name,c.tl_pa_lo,c.tl_pa_la,c.tl_pz_name,c.tl_pz_lo,c.tl_pz_la,c.tl_length,c.arrived_rate_id,c.location_cycle_id,c.tl_arrived_rate,c.tl_report_interval,c.tl_real_arrived_rate,c.tl_execute_type " +
							" from pnr_res_config c, pnr_inspect_template t ";
		String whereSql = "where " +
							" c.tl_dis='"+city+"' " +
							" and "+executeObjectNotEmpty+" and "+executeDeptNotEmpty+
							" and c.inspect_cycle='"+cycle+"'"+
							" and "+arrived_rate_id+
//							" and "+location_Cycle_Id+
							" and c.tl_inspect_flag = '1' and c.specialty='1122502' and t.specialty='1122502' and t.status=1 ";
		try{
			String tempTableSql = CommonSqlHelper.formatTempTableSql(selectSql, whereSql, tmpTableName);
			this.getJdbcTemplate().execute(tempTableSql);//如果是informix这个步骤能够直接往临时表插入数据
			if(CommonSqlHelper.isOracleDialect()){
				String insertTempSql = "insert into " + tmpTableName + selectSql + whereSql;
				this.getJdbcTemplate().execute(insertTempSql);
			}
			
			//复制临时表数据到巡检任务表
			String copyToMainTable = "insert into pnr_inspect_plan_res(id,res_cfg_id,specialty,res_type,res_name,res_level,city,country,inspect_cycle,cycle_start_time,cycle_end_time,execute_object,execute_dept,execute_type,change_state,res_longitude, res_latitude,end_longitude,end_latitude,create_time,force_assign,inspect_State,item_num,template_Id,last_inspect_Time,eographical_environment,region_type,tl_inspect_flag,tl_dis,tl_wire,tl_system_level,tl_seg,tl_lay_type,tl_fiber_count,tl_pa_name,tl_pa_lo,tl_pa_la,tl_pz_name,tl_pz_lo,tl_pz_la,tl_length,arrived_rate_id,location_cycle_id,tl_arrived_rate,tl_report_interval,tl_real_arrived_rate,tl_execute_type) select * from "+tmpTableName;
			this.getJdbcTemplate().execute(copyToMainTable);
			
			//生成PnrInspectTaskLink数据
			String PnrInspectTaskLinkSQL = "insert into pnr_inspect_task_link(id,plan_id,plan_res_id,inspect_id,inspect_link_id,inspect_mapping_id,device_specialty_name,device_type_name,netres_name,netres_table_name,netres_id,res_type,city,create_time,tlp_dis,tlp_wire,tlp_seg,tlp_pa_name,tlp_pa_lo,tlp_pa_la,tlp_pz_name,tlp_pz_lo,tlp_pz_la,tlp_type,tlp_sort_num,is_must_arrive,tlp_city,tlp_country,is_arrived,tlp_inspect_flag,tlp_source)  " +
											 " select seq_pnr_inspect_plan_res.NEXTVAL, "+
										        " '',"+
										        " a.id as plan_res_id,"+
										        " a.res_cfg_id as inspect_id,"+
										        " '' as inspect_link_id,"+
										        " b.inspect_mapping_id,"+
										        " '传输线路' as device_specialty_name,"+
										        " '光缆段' as device_type_name,"+
										        " b.tlp_pa_name as netres_name,"+
										        " 'pnr_trans_line_point' as netres_table_name,"+
										        " b.id as netres_id,"+
										        " a.res_type,"+
										        " a.city,"+
										        " a.create_time,"+
										        " b.tlp_dis,"+
										        " b.tlp_wire,"+
										        " b.tlp_seg,"+
										        " b.tlp_pa_name,"+
										        " b.tlp_pa_lo,"+
										        " b.tlp_pa_la,"+
										        " b.tlp_pz_name,"+
										        " b.tlp_pz_lo,"+
										        " b.tlp_pz_la,"+
										        " b.tlp_type,"+
										        " b.tlp_sort_num,"+
										        " b.is_must_arrive,"+
										        " b.city as tlp_city,"+
										        " b.country as tlp_country,"+
										        " '0' as is_arrived,"+
										        " '1' as tlp_inspect_flag,"+
										        " b.tlp_source"+
										" from "+tmpTableName+" a,pnr_trans_line_point b "+
										" where  "+
										    " a.tl_wire = b.tlp_wire and a.tl_seg = b.tlp_seg ";
			this.getJdbcTemplate().execute(PnrInspectTaskLinkSQL);
		}finally{//由于建表语句为DDL语句，不可回滚，如果前面的代码报错，需要在finally中删除临时表，否则下次再次执行会报临时表已经建立的错误
			//删除临时表
			String delTmpTable = "drop table "+tmpTableName;
			this.getJdbcTemplate().execute(delTmpTable);
		}
	}
	
	/**
	 * 产生巡检计划执行项
	 * @param city 地市
	 * @param createTime 资源产生日期
	 */
	public void generateInspectPlanItem(String city,String createTime){
		String sql = "insert into pnr_inspect_plan_item(id,plan_res_id,res_type,inspect_item,content,input_type,default_value,normal_range,dict_id,inspect_cycle,template_Item_Id,bigitem_name,exception_flag,city,inspect_task_link_id,device_inspect_flag,picture_flag,picture_num) " +
				" select seq_pnr_inspect_plan_item.NEXTVAL,r.id,r.res_type,i.inspect_item,i.inspect_content,i.input_type,i.default_value,i.normal_range,i.dict_id,i.cycle,i.id,i.bigitem_name,1,'"+city+"','',0,i.picture_flag,i.picture_num" +
					" from pnr_inspect_plan_res r " +
					" left join pnr_inspect_template_item i " +
					" on i.template_Id=r.template_Id and device_inspect_flag<>1 and r.tl_inspect_flag='1' " +
					" where  r.city='"+city+"' and r.create_time='"+createTime+"' and r.tl_inspect_flag='1' and i.device_inspect_flag<>1 and i.status=1";
		this.getJdbcTemplate().execute(sql);
		
		//生成网络资源设备巡检项（核心由pnr_inspect_template_item中的inspect_mapping_id于pnr_inspect_task_link中的inspect_mapping_id，由于pnr_inspect_task_link无inspect_mapping_id，故由pnr_inspect_task_link与pnr_inspect_plan_res关联得到inspect_mapping_id）
		String sql2 = "insert into pnr_inspect_plan_item(id,plan_res_id,res_type,inspect_item,content,input_type,default_value,normal_range,dict_id,inspect_cycle,template_Item_Id,bigitem_name,exception_flag,city,inspect_task_link_id,device_inspect_flag,picture_flag,picture_num) " +
						" select seq_pnr_inspect_plan_item.NEXTVAL,r.plan_res_id,r.res_type,i.inspect_item,i.inspect_content,i.input_type,i.default_value,i.normal_range,i.dict_id,i.cycle,i.id,i.bigitem_name,1,'"+city+"',r.id,1,i.picture_flag,i.picture_num" +
						" from pnr_inspect_template_item i right join pnr_inspect_task_link r "+
						" on r.inspect_mapping_id = i.inspect_mapping_id and r.tlp_inspect_flag='1' "+
						" where i.status='1' and r.city='"+city+"' and r.create_time='"+createTime+"' and r.tlp_inspect_flag='1'";
		this.getJdbcTemplate().execute(sql2);
		
		String sql3 ="update pnr_inspect_plan_res set item_num=(" +
					" select cccc from (" +
						" select count(*) as cccc,plan_res_id from pnr_inspect_plan_item group by plan_res_id " +
					" ) tttt where tttt.plan_res_id=pnr_inspect_plan_res.id and pnr_inspect_plan_res.city='"+city+"' and pnr_inspect_plan_res.create_time='"+createTime+"')  where exists (select cccc from ( " +
					    " select count(*) as cccc,plan_res_id from pnr_inspect_plan_item  group by plan_res_id  " +
						" ) tttt where tttt.plan_res_id=pnr_inspect_plan_res.id and pnr_inspect_plan_res.city='"+city+"' and pnr_inspect_plan_res.create_time='"+createTime+"') and city='"+city+"' and create_time='"+createTime+"'";
					
		this.getJdbcTemplate().execute(sql3);
	}
	
	/**
	 * 产生突发巡检任务
	 * @param ids
	 * @return
	 */
	public String generateBurstInspectPlanRes(String ids){
		LocalDate date = new LocalDate();
		String createTime =StaticMethod.getCurrentDateTime(); 
		String cycleStartTime = date.dayOfMonth().withMinimumValue()+" 00:00:00";
		String cycleEndTime = date.dayOfMonth().withMaximumValue()+" 23:59:59";
		
		cycleStartTime = CommonSqlHelper.formatDateTime(cycleStartTime);
		cycleEndTime = CommonSqlHelper.formatDateTime(cycleEndTime);
		String arrived_rate_id = CommonSqlHelper.formatNotEmpty("c.arrived_rate_id");
//		String location_Cycle_Id = CommonSqlHelper.formatNotEmpty("c.location_Cycle_Id");
		
		//插入数据到临时表
		String tmpTableName = "tmp_pnr_inspect_plan_res";
//		String selectSql = " select seq_pnr_inspect_plan_res.NEXTVAL as id,c.id as res_cfg_id,c.specialty,c.res_type,c.res_name," +
//									"c.res_level,c.city,c.country,c.inspect_cycle,"+cycleStartTime+" as cycle_start_time,"+cycleEndTime+" as cycle_end_time," + 
//									"c.execute_object,c.execute_dept,c.execute_type,0 as change_state,c.res_longitude, c.res_latitude," +
//									"c.end_longitude,c.end_latitude,'" + createTime + "' as create_time,0 as force_assign,0 as inspect_State,t.item_num,t.id as template_Id,c.last_inspect_Time,c.eographical_environment,c.region_type,1 as burst_flag " +
//							" from pnr_res_config c left join pnr_inspect_template t on c.tl_inspect_flag = '0' and c.res_type=t.res_type and c.id in ("+ids+") ";
//		String whereSql = "where c.tl_inspect_flag = '0'  and t.status=1 ";
		
		String selectSql = " select seq_pnr_inspect_plan_res.NEXTVAL as id,c.id as res_cfg_id,c.specialty,c.res_type,c.res_name," +
									"c.res_level,c.city,c.country,c.inspect_cycle,"+cycleStartTime+" as cycle_start_time,"+cycleEndTime+" as cycle_end_time," + 
									"c.execute_object,c.execute_dept,c.execute_type,0 as change_state,c.res_longitude, c.res_latitude," +
									"c.end_longitude,c.end_latitude,'" + createTime + "' as create_time,0 as force_assign,0 as inspect_State,t.item_num,t.id as template_Id,c.last_inspect_Time,c.eographical_environment,c.region_type,c.tl_inspect_flag,c.tl_dis,c.tl_wire,c.tl_system_level,c.tl_seg,c.tl_lay_type,c.tl_fiber_count,c.tl_pa_name,c.tl_pa_lo,c.tl_pa_la,c.tl_pz_name,c.tl_pz_lo,c.tl_pz_la,c.tl_length,c.arrived_rate_id,c.location_cycle_id,c.tl_arrived_rate,c.tl_report_interval,c.tl_real_arrived_rate,c.tl_execute_type,1 as burst_flag " +
							" from pnr_res_config c, pnr_inspect_template t ";
		String whereSql = "where c.tl_inspect_flag = '1' and c.specialty='1122502' and t.specialty='1122502' and t.status=1 " +
		" and "+arrived_rate_id+
//		" and "+location_Cycle_Id+
							" and c.id in ("+ids+") ";
		try{
			String tempTableSql = CommonSqlHelper.formatTempTableSql(selectSql, whereSql, tmpTableName);
			this.getJdbcTemplate().execute(tempTableSql);//如果是informix这个步骤能够直接往临时表插入数据
			if(CommonSqlHelper.isOracleDialect()){
				String insertTempSql = "insert into " + tmpTableName + selectSql + whereSql;
				this.getJdbcTemplate().execute(insertTempSql);
			}
			
			//复制临时表数据到巡检任务表
//			String copyToMainTable = "insert into pnr_inspect_plan_res(id,res_cfg_id,specialty,res_type,res_name,res_level,city,country,inspect_cycle,cycle_start_time,cycle_end_time,execute_object,execute_dept,execute_type,change_state,res_longitude, res_latitude,end_longitude,end_latitude,create_time,force_assign,inspect_State,item_num,template_Id,last_inspect_Time,eographical_environment,region_type,burst_flag) select * from "+tmpTableName;
			String copyToMainTable = "insert into pnr_inspect_plan_res(id,res_cfg_id,specialty,res_type,res_name,res_level,city,country,inspect_cycle,cycle_start_time,cycle_end_time,execute_object,execute_dept,execute_type,change_state,res_longitude, res_latitude,end_longitude,end_latitude,create_time,force_assign,inspect_State,item_num,template_Id,last_inspect_Time,eographical_environment,region_type,tl_inspect_flag,tl_dis,tl_wire,tl_system_level,tl_seg,tl_lay_type,tl_fiber_count,tl_pa_name,tl_pa_lo,tl_pa_la,tl_pz_name,tl_pz_lo,tl_pz_la,tl_length,arrived_rate_id,location_cycle_id,tl_arrived_rate,tl_report_interval,tl_real_arrived_rate,tl_execute_type,burst_flag) select * from "+tmpTableName;
			this.getJdbcTemplate().execute(copyToMainTable);
			
			//生成PnrInspectTaskLink数据
//			String PnrInspectTaskLinkSQL = "insert into pnr_inspect_task_link(id,plan_id,plan_res_id,inspect_id,inspect_link_id,inspect_mapping_id,device_specialty_name,device_type_name,netres_name,netres_table_name,netres_id,res_type,city,create_time) " +
//							" select seq_pnr_inspect_plan_res.NEXTVAL," +
//								"''," +
//						        "a.id as plan_res_id," +
//						        "a.res_cfg_id as inspect_id," +
//						        "b.id as inspect_link_id," +
//						        "b.inspect_mapping_id," +
//						        "b.device_specialty_name," +
//						        "b.device_type_name," +
//						        "b.netres_name," +
//						        "b.netres_table_name," +
//						        "b.netres_id," +
//						        "a.res_type," +
//						        "a.city," +
//						        "a.create_time " +
//							" from "+tmpTableName+" a " +
//							" left join pnr_inspect_link b " +
//							" on a.res_cfg_id = b.inspect_id " +
//							" where a.res_cfg_id = b.inspect_id";
			String PnrInspectTaskLinkSQL = "insert into pnr_inspect_task_link(id,plan_id,plan_res_id,inspect_id,inspect_link_id,inspect_mapping_id,device_specialty_name,device_type_name,netres_name,netres_table_name,netres_id,res_type,city,create_time,tlp_dis,tlp_wire,tlp_seg,tlp_pa_name,tlp_pa_lo,tlp_pa_la,tlp_pz_name,tlp_pz_lo,tlp_pz_la,tlp_type,tlp_sort_num,is_must_arrive,tlp_city,tlp_country,is_arrived,tlp_inspect_flag,tlp_source )  " +
								 " select seq_pnr_inspect_plan_res.NEXTVAL, "+
							        " '',"+
							        " a.id as plan_res_id,"+
							        " a.res_cfg_id as inspect_id,"+
							        " '' as inspect_link_id,"+
							        " b.inspect_mapping_id,"+
							        " '传输线路' as device_specialty_name,"+
							        " '光缆段' as device_type_name,"+
							        " b.tlp_pa_name as netres_name,"+
							        " 'pnr_trans_line_point' as netres_table_name,"+
							        " b.id as netres_id,"+
							        " a.res_type,"+
							        " a.city,"+
							        " a.create_time,"+
							        " b.tlp_dis,"+
							        " b.tlp_wire,"+
							        " b.tlp_seg,"+
							        " b.tlp_pa_name,"+
							        " b.tlp_pa_lo,"+
							        " b.tlp_pa_la,"+
							        " b.tlp_pz_name,"+
							        " b.tlp_pz_lo,"+
							        " b.tlp_pz_la,"+
							        " b.tlp_type,"+
							        " b.tlp_sort_num,"+
							        " b.is_must_arrive,"+
							        " b.city as tlp_city,"+
							        " b.country as tlp_country,"+
							        " '0' as is_arrived,"+
							        " '1' as tlp_inspect_flag,"+
							        " b.tlp_source"+
							" from "+tmpTableName+" a,pnr_trans_line_point b "+
							" where  "+
							    " a.tl_wire = b.tlp_wire and a.tl_seg = b.tlp_seg ";
			this.getJdbcTemplate().execute(PnrInspectTaskLinkSQL);
			
		}finally{ //由于建表语句为DDL语句，不可回滚，如果前面的代码报错，需要在finally中删除临时表，否则下次再次执行会报临时表已经建立的错误
			//删除临时表
			String delTmpTable = "drop table "+tmpTableName;
			this.getJdbcTemplate().execute(delTmpTable);
		}
		
		return createTime;
	}
	
	/**
	 * 产生突发巡检任务
	 * @param ids
	 * @return
	 */
	public String generateBurstInspectPlanRes(String ids,String planStartTime,String planEndTime){
//		String createTime =StaticMethod.getCurrentDateTime(); 
//		planStartTime = CommonSqlHelper.formatDateTime(planStartTime);
//		planEndTime = CommonSqlHelper.formatDateTime(planEndTime);
//		
//		//插入数据到临时表
//		String tmpTableName = "tmp_pnr_inspect_plan_res";
//		String selectSql = " select seq_pnr_inspect_plan_res.NEXTVAL as id,c.id as res_cfg_id,c.specialty,c.res_type,c.res_name," +
//		"c.res_level,c.city,c.country,"+planStartTime+" as cycle_start_time,"+planEndTime+" as cycle_end_time," + 
//		"c.execute_object,c.execute_dept,c.execute_type,0 as change_state,c.res_longitude, c.res_latitude," +
//		"c.end_longitude,c.end_latitude,'" + createTime + "' as create_time,0 as force_assign,0 as inspect_State,t.item_num,t.id as template_Id,c.last_inspect_Time,c.eographical_environment,c.region_type,1 as burst_flag " +
//		" from pnr_res_config c left join pnr_inspect_template t on c.tl_inspect_flag = '0' and c.res_type=t.res_type and c.id in ("+ids+") ";
//		String whereSql = "where c.tl_inspect_flag = '0' and t.status=1 ";
//		
//		try{
//			String tempTableSql = CommonSqlHelper.formatTempTableSql(selectSql, whereSql, tmpTableName);
//			this.getJdbcTemplate().execute(tempTableSql);//如果是informix这个步骤能够直接往临时表插入数据
//			if(CommonSqlHelper.isOracleDialect()){
//				String insertTempSql = "insert into " + tmpTableName + selectSql + whereSql;
//				this.getJdbcTemplate().execute(insertTempSql);
//			}
//			
//			//复制临时表数据到巡检任务表
//			String copyToMainTable = "insert into pnr_inspect_plan_res(id,res_cfg_id,specialty,res_type,res_name,res_level,city,country,plan_start_time,plan_end_time,execute_object,execute_dept,execute_type,change_state,res_longitude, res_latitude,end_longitude,end_latitude,create_time,force_assign,inspect_State,item_num,template_Id,last_inspect_Time,eographical_environment,region_type,burst_flag) select * from "+tmpTableName;
//			this.getJdbcTemplate().execute(copyToMainTable);
//			
//			//生成PnrInspectTaskLink数据
//			String PnrInspectTaskLinkSQL = "insert into pnr_inspect_task_link(id,plan_id,plan_res_id,inspect_id,inspect_link_id,inspect_mapping_id,device_specialty_name,device_type_name,netres_name,netres_table_name,netres_id,res_type,city,create_time) " +
//			" select seq_pnr_inspect_plan_res.NEXTVAL," +
//			"''," +
//			"a.id as plan_res_id," +
//			"a.res_cfg_id as inspect_id," +
//			"b.id as inspect_link_id," +
//			"b.inspect_mapping_id," +
//			"b.device_specialty_name," +
//			"b.device_type_name," +
//			"b.netres_name," +
//			"b.netres_table_name," +
//			"b.netres_id," +
//			"a.res_type," +
//			"a.city," +
//			"a.create_time " +
//			" from "+tmpTableName+" a " +
//			" left join pnr_inspect_link b " +
//			" on a.res_cfg_id = b.inspect_id " +
//			" where a.res_cfg_id = b.inspect_id";
//			this.getJdbcTemplate().execute(PnrInspectTaskLinkSQL);
//		}finally{ //由于建表语句为DDL语句，不可回滚，如果前面的代码报错，需要在finally中删除临时表，否则下次再次执行会报临时表已经建立的错误
//			//删除临时表
//			String delTmpTable = "drop table "+tmpTableName;
//			this.getJdbcTemplate().execute(delTmpTable);
//		}
//		return createTime;
		LocalDate date = new LocalDate();
		String createTime =StaticMethod.getCurrentDateTime(); 
		String cycleStartTime = date.dayOfMonth().withMinimumValue()+" 00:00:00";
		String cycleEndTime = date.dayOfMonth().withMaximumValue()+" 23:59:59";
		
		cycleStartTime = CommonSqlHelper.formatDateTime(planStartTime);
		cycleEndTime = CommonSqlHelper.formatDateTime(planEndTime);
		
		String arrived_rate_id = CommonSqlHelper.formatNotEmpty("c.arrived_rate_id");
		
		//插入数据到临时表
		String tmpTableName = "tmp_pnr_inspect_plan_res";
//		String selectSql = " select seq_pnr_inspect_plan_res.NEXTVAL as id,c.id as res_cfg_id,c.specialty,c.res_type,c.res_name," +
//									"c.res_level,c.city,c.country,c.inspect_cycle,"+cycleStartTime+" as cycle_start_time,"+cycleEndTime+" as cycle_end_time," + 
//									"c.execute_object,c.execute_dept,c.execute_type,0 as change_state,c.res_longitude, c.res_latitude," +
//									"c.end_longitude,c.end_latitude,'" + createTime + "' as create_time,0 as force_assign,0 as inspect_State,t.item_num,t.id as template_Id,c.last_inspect_Time,c.eographical_environment,c.region_type,1 as burst_flag " +
//							" from pnr_res_config c left join pnr_inspect_template t on c.tl_inspect_flag = '0' and c.res_type=t.res_type and c.id in ("+ids+") ";
//		String whereSql = "where c.tl_inspect_flag = '0'  and t.status=1 ";
		
		String selectSql = " select seq_pnr_inspect_plan_res.NEXTVAL as id,c.id as res_cfg_id,c.specialty,c.res_type,c.res_name," +
									"c.res_level,c.city,c.country,c.inspect_cycle,"+cycleStartTime+" as cycle_start_time,"+cycleEndTime+" as cycle_end_time," + 
									"c.execute_object,c.execute_dept,c.execute_type,0 as change_state,c.res_longitude, c.res_latitude," +
									"c.end_longitude,c.end_latitude,'" + createTime + "' as create_time,0 as force_assign,0 as inspect_State,t.item_num,t.id as template_Id,c.last_inspect_Time,c.eographical_environment,c.region_type,c.tl_inspect_flag,c.tl_dis,c.tl_wire,c.tl_system_level,c.tl_seg,c.tl_lay_type,c.tl_fiber_count,c.tl_pa_name,c.tl_pa_lo,c.tl_pa_la,c.tl_pz_name,c.tl_pz_lo,c.tl_pz_la,c.tl_length,c.arrived_rate_id,c.location_cycle_id,c.tl_arrived_rate,c.tl_report_interval,c.tl_real_arrived_rate,c.tl_execute_type,1 as burst_flag " +
							" from pnr_res_config c, pnr_inspect_template t ";
		String whereSql = "where c.tl_inspect_flag = '1' and c.specialty='1122502' and t.specialty='1122502' and t.status=1 " +
							" and "+arrived_rate_id+
							" and c.id in ("+ids+") ";
		try{
			String tempTableSql = CommonSqlHelper.formatTempTableSql(selectSql, whereSql, tmpTableName);
			this.getJdbcTemplate().execute(tempTableSql);//如果是informix这个步骤能够直接往临时表插入数据
			if(CommonSqlHelper.isOracleDialect()){
				String insertTempSql = "insert into " + tmpTableName + selectSql + whereSql;
				this.getJdbcTemplate().execute(insertTempSql);
			}
			
			//复制临时表数据到巡检任务表
//			String copyToMainTable = "insert into pnr_inspect_plan_res(id,res_cfg_id,specialty,res_type,res_name,res_level,city,country,inspect_cycle,cycle_start_time,cycle_end_time,execute_object,execute_dept,execute_type,change_state,res_longitude, res_latitude,end_longitude,end_latitude,create_time,force_assign,inspect_State,item_num,template_Id,last_inspect_Time,eographical_environment,region_type,burst_flag) select * from "+tmpTableName;
			String copyToMainTable = "insert into pnr_inspect_plan_res(id,res_cfg_id,specialty,res_type,res_name,res_level,city,country,inspect_cycle,cycle_start_time,cycle_end_time,execute_object,execute_dept,execute_type,change_state,res_longitude, res_latitude,end_longitude,end_latitude,create_time,force_assign,inspect_State,item_num,template_Id,last_inspect_Time,eographical_environment,region_type,tl_inspect_flag,tl_dis,tl_wire,tl_system_level,tl_seg,tl_lay_type,tl_fiber_count,tl_pa_name,tl_pa_lo,tl_pa_la,tl_pz_name,tl_pz_lo,tl_pz_la,tl_length,arrived_rate_id,location_cycle_id,tl_arrived_rate,tl_report_interval,tl_real_arrived_rate,tl_execute_type,burst_flag) select * from "+tmpTableName;
			this.getJdbcTemplate().execute(copyToMainTable);
			
			//生成PnrInspectTaskLink数据
//			String PnrInspectTaskLinkSQL = "insert into pnr_inspect_task_link(id,plan_id,plan_res_id,inspect_id,inspect_link_id,inspect_mapping_id,device_specialty_name,device_type_name,netres_name,netres_table_name,netres_id,res_type,city,create_time) " +
//							" select seq_pnr_inspect_plan_res.NEXTVAL," +
//								"''," +
//						        "a.id as plan_res_id," +
//						        "a.res_cfg_id as inspect_id," +
//						        "b.id as inspect_link_id," +
//						        "b.inspect_mapping_id," +
//						        "b.device_specialty_name," +
//						        "b.device_type_name," +
//						        "b.netres_name," +
//						        "b.netres_table_name," +
//						        "b.netres_id," +
//						        "a.res_type," +
//						        "a.city," +
//						        "a.create_time " +
//							" from "+tmpTableName+" a " +
//							" left join pnr_inspect_link b " +
//							" on a.res_cfg_id = b.inspect_id " +
//							" where a.res_cfg_id = b.inspect_id";
			String PnrInspectTaskLinkSQL = "insert into pnr_inspect_task_link(id,plan_id,plan_res_id,inspect_id,inspect_link_id,inspect_mapping_id,device_specialty_name,device_type_name,netres_name,netres_table_name,netres_id,res_type,city,create_time,tlp_dis,tlp_wire,tlp_seg,tlp_pa_name,tlp_pa_lo,tlp_pa_la,tlp_pz_name,tlp_pz_lo,tlp_pz_la,tlp_type,tlp_sort_num,is_must_arrive,tlp_city,tlp_country,is_arrived,tlp_inspect_flag,tlp_source )  " +
								 " select seq_pnr_inspect_plan_res.NEXTVAL, "+
							        " '',"+
							        " a.id as plan_res_id,"+
							        " a.res_cfg_id as inspect_id,"+
							        " '' as inspect_link_id,"+
							        " b.inspect_mapping_id,"+
							        " '传输线路' as device_specialty_name,"+
							        " '光缆段' as device_type_name,"+
							        " b.tlp_pa_name as netres_name,"+
							        " 'pnr_trans_line_point' as netres_table_name,"+
							        " b.id as netres_id,"+
							        " a.res_type,"+
							        " a.city,"+
							        " a.create_time,"+
							        " b.tlp_dis,"+
							        " b.tlp_wire,"+
							        " b.tlp_seg,"+
							        " b.tlp_pa_name,"+
							        " b.tlp_pa_lo,"+
							        " b.tlp_pa_la,"+
							        " b.tlp_pz_name,"+
							        " b.tlp_pz_lo,"+
							        " b.tlp_pz_la,"+
							        " b.tlp_type,"+
							        " b.tlp_sort_num,"+
							        " b.is_must_arrive,"+
							        " b.city as tlp_city,"+
							        " b.country as tlp_country,"+
							        " '0' as is_arrived,"+
							        " '1' as tlp_inspect_flag,"+
							        " b.tlp_source"+
							" from "+tmpTableName+" a,pnr_trans_line_point b "+
							" where  "+
							    " a.tl_wire = b.tlp_wire and a.tl_seg = b.tlp_seg ";
			this.getJdbcTemplate().execute(PnrInspectTaskLinkSQL);
			
		}finally{ //由于建表语句为DDL语句，不可回滚，如果前面的代码报错，需要在finally中删除临时表，否则下次再次执行会报临时表已经建立的错误
			//删除临时表
			String delTmpTable = "drop table "+tmpTableName;
			this.getJdbcTemplate().execute(delTmpTable);
		}
		
		return createTime;
	}
	
	/**
	 * 产生突发巡检计划执行项
	 * @param city 地市
	 * @param createTime 资源产生日期
	 */
	public void generateBurstInspectPlanItem(String createTime){
//		String sql = "insert into pnr_inspect_plan_item(id,plan_res_id,res_type,inspect_item,content,input_type,default_value,normal_range,dict_id,inspect_cycle,template_Item_Id,bigitem_name,exception_flag,city,inspect_task_link_id,device_inspect_flag,picture_flag,picture_num) " +
//				" select seq_pnr_inspect_plan_item.NEXTVAL,r.id,r.res_type,i.inspect_item,i.inspect_content,i.input_type,i.default_value,i.normal_range,i.dict_id,i.cycle,i.id,i.bigitem_name,1,r.city,'',0,i.picture_flag,i.picture_num" +
//					" from pnr_inspect_plan_res r " +
//					" left join pnr_inspect_template_item i " +
//					" on i.template_Id=r.template_Id and i.device_inspect_flag <>1" +
//					" where r.create_time='"+createTime+"' and r.tl_inspect_flag = '1' and i.device_inspect_flag <>1 and i.status=1";
		String sql = "insert into pnr_inspect_plan_item(id,plan_res_id,res_type,inspect_item,content,input_type,default_value,normal_range,dict_id,inspect_cycle,template_Item_Id,bigitem_name,exception_flag,city,inspect_task_link_id,device_inspect_flag,picture_flag,picture_num) " +
				" select seq_pnr_inspect_plan_item.NEXTVAL,r.id,r.res_type,i.inspect_item,i.inspect_content,i.input_type,i.default_value,i.normal_range,i.dict_id,i.cycle,i.id,i.bigitem_name,1,r.city,'',0,i.picture_flag,i.picture_num" +
					" from pnr_inspect_plan_res r " +
					" left join pnr_inspect_template_item i " +
					" on i.template_Id=r.template_Id and device_inspect_flag<>1 and r.tl_inspect_flag='1' " +
					" where r.create_time='"+createTime+"' and r.tl_inspect_flag='1' and i.device_inspect_flag<>1 and i.status=1";
		this.getJdbcTemplate().execute(sql);
		
		//生成网络资源设备巡检项（核心由pnr_inspect_template_item中的inspect_mapping_id于pnr_inspect_task_link中的inspect_mapping_id，由于pnr_inspect_task_link无inspect_mapping_id，故由pnr_inspect_task_link与pnr_inspect_plan_res关联得到inspect_mapping_id）
//		String sql2 = "insert into pnr_inspect_plan_item(id,plan_res_id,res_type,inspect_item,content,input_type,default_value,normal_range,dict_id,inspect_cycle,template_Item_Id,bigitem_name,exception_flag,city,inspect_task_link_id,device_inspect_flag,picture_flag,picture_num) " +
//						" select seq_pnr_inspect_plan_item.NEXTVAL,r.plan_res_id,r.res_type,i.inspect_item,i.inspect_content,i.input_type,i.default_value,i.normal_range,i.dict_id,i.cycle,i.id,i.bigitem_name,1,r.city,r.id,1,i.picture_flag,i.picture_num " +
//						" from pnr_inspect_template_item i right join pnr_inspect_task_link r "+
//						" on r.inspect_mapping_id = i.inspect_mapping_id "+
//						" where i.status='1' and r.create_time='"+createTime+"'";
		String sql2 = "insert into pnr_inspect_plan_item(id,plan_res_id,res_type,inspect_item,content,input_type,default_value,normal_range,dict_id,inspect_cycle,template_Item_Id,bigitem_name,exception_flag,city,inspect_task_link_id,device_inspect_flag,picture_flag,picture_num) " +
						" select seq_pnr_inspect_plan_item.NEXTVAL,r.plan_res_id,r.res_type,i.inspect_item,i.inspect_content,i.input_type,i.default_value,i.normal_range,i.dict_id,i.cycle,i.id,i.bigitem_name,1,r.city,r.id,1,i.picture_flag,i.picture_num" +
						" from pnr_inspect_template_item i right join pnr_inspect_task_link r "+
						" on r.inspect_mapping_id = i.inspect_mapping_id and r.tlp_inspect_flag='1' "+
						" where i.status='1' and r.create_time='"+createTime+"' and r.tlp_inspect_flag='1'";
		this.getJdbcTemplate().execute(sql2);
		
		String sql3 ="update pnr_inspect_plan_res set item_num=(" +
						" select cccc from (" +
							" select count(*) as cccc,plan_res_id from pnr_inspect_plan_item group by plan_res_id " +
						" ) tttt where tttt.plan_res_id=pnr_inspect_plan_res.id  and pnr_inspect_plan_res.create_time='"+createTime+"')  where exists (select cccc from ( " +
						    " select count(*) as cccc,plan_res_id from pnr_inspect_plan_item  group by plan_res_id  " +
							" ) tttt where tttt.plan_res_id=pnr_inspect_plan_res.id and pnr_inspect_plan_res.create_time='"+createTime+"') and create_time='"+createTime+"'";
		
		this.getJdbcTemplate().execute(sql3);
	}
}
