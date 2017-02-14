package com.boco.eoms.partner.deviceInspect.dao.impl;

import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;
import com.boco.eoms.partner.deviceInspect.dao.IDeviceInspectDao;
import com.boco.eoms.partner.inspect.util.InspectConstants;
import com.boco.eoms.partner.netresource.dao.hibernate.EomsDaoHibernateImpl;

@SuppressWarnings("unchecked")
public class DeviceInspectDaoImpl extends EomsDaoHibernateImpl implements IDeviceInspectDao{
	
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
		
		//插入数据到临时表
		String tmpTableName = "tmp_pnr_inspect_plan_res";
		String selectSql = " select seq_pnr_inspect_plan_res.NEXTVAL as id,c.id as res_cfg_id,c.specialty,c.res_type,c.res_name," +
									"c.res_level,c.city,c.country,c.inspect_cycle,"+cycleStartTime+" as cycle_start_time,"+cycleEndTime+" as cycle_end_time," + 
									"c.execute_object,c.execute_dept,c.execute_type,0 as change_state,c.res_longitude, c.res_latitude," +
									"c.end_longitude,c.end_latitude,'" + createTime + "' as create_time,0 as force_assign,0 as inspect_State,t.item_num,t.id as template_Id,c.last_inspect_Time,c.eographical_environment,c.region_type " +
							" from pnr_res_config c left join pnr_inspect_template t on c.res_type=t.res_type ";
		
		String whereSql = "where c.city='"+city+"' and c.inspect_cycle='"+cycle+"' and t.status=1 " +
							" and "+executeObjectNotEmpty+" and "+executeDeptNotEmpty ;
		
		if(InspectConstants.getSwitchConfig().isOpenTransLineInspect()){ //打开线路巡检
			selectSql += " and c.specialty<>'1122502'";
			whereSql += " and c.specialty<>'1122502'";
		}
		
		System.out.println("DeviceInspectDaoImpl-48:"+selectSql+whereSql);
		
		try{
			String tempTableSql = CommonSqlHelper.formatTempTableSql(selectSql, whereSql, tmpTableName);
			this.getJdbcTemplate().execute(tempTableSql);//如果是informix这个步骤能够直接往临时表插入数据
			if(CommonSqlHelper.isOracleDialect()){
				String insertTempSql = "insert into " + tmpTableName + selectSql + whereSql;
				this.getJdbcTemplate().execute(insertTempSql);
			}
			
			//复制临时表数据到巡检任务表
			String copyToMainTable = "insert into pnr_inspect_plan_res(id,res_cfg_id,specialty,res_type,res_name,res_level,city,country,inspect_cycle,cycle_start_time,cycle_end_time,execute_object,execute_dept,execute_type,change_state,res_longitude, res_latitude,end_longitude,end_latitude,create_time,force_assign,inspect_State,item_num,template_Id,last_inspect_Time,eographical_environment,region_type) select * from "+tmpTableName;
			this.getJdbcTemplate().execute(copyToMainTable);
			
			//生成PnrInspectTaskLink数据
			String PnrInspectTaskLinkSQL = "insert into pnr_inspect_task_link(id,plan_id,plan_res_id,inspect_id,inspect_link_id,inspect_mapping_id,device_specialty_name,device_type_name,netres_name,netres_table_name,netres_id,res_type,city,create_time) " +
							" select seq_pnr_inspect_plan_res.NEXTVAL," +
								"''," +
						        "a.id as plan_res_id," +
						        "a.res_cfg_id as inspect_id," +
						        "b.id as inspect_link_id," +
						        "b.inspect_mapping_id," +
						        "b.device_specialty_name," +
						        "b.device_type_name," +
						        "b.netres_name," +
						        "b.netres_table_name," +
						        "b.netres_id," +
						        "a.res_type," +
						        "a.city," +
						        "a.create_time " +
							" from "+tmpTableName+" a " +
							" left join pnr_inspect_link b " +
							" on a.res_cfg_id = b.inspect_id " +
							" where a.res_cfg_id = b.inspect_id";
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
	 * @param createTime 时间
	 */
	public void generateInspectPlanItem(String city,String createTime){
		String sql = "insert into pnr_inspect_plan_item(id,plan_res_id,res_type,inspect_item,content,input_type,default_value,normal_range,dict_id,inspect_cycle,template_Item_Id,bigitem_name,exception_flag,city,inspect_task_link_id,device_inspect_flag,picture_flag,picture_num) " +
				" select seq_pnr_inspect_plan_item.NEXTVAL,r.id,r.res_type,i.inspect_item,i.inspect_content,i.input_type,i.default_value,i.normal_range,i.dict_id,i.cycle,i.id,i.bigitem_name,1,'"+city+"','',0,i.picture_flag,i.picture_num" +
					" from pnr_inspect_plan_res r " +
					" left join pnr_inspect_template_item i " +
					" on i.template_Id=r.template_Id and device_inspect_flag <>1" +
					" where  r.city='"+city+"' and r.create_time='"+createTime+"' and i.status=1";
		this.getJdbcTemplate().execute(sql);
		
		//生成网络资源设备巡检项（核心由pnr_inspect_template_item中的inspect_mapping_id于pnr_inspect_task_link中的inspect_mapping_id，由于pnr_inspect_task_link无inspect_mapping_id，故由pnr_inspect_task_link与pnr_inspect_plan_res关联得到inspect_mapping_id）
		String sql2 = "insert into pnr_inspect_plan_item(id,plan_res_id,res_type,inspect_item,content,input_type,default_value,normal_range,dict_id,inspect_cycle,template_Item_Id,bigitem_name,exception_flag,city,inspect_task_link_id,device_inspect_flag,picture_flag,picture_num) " +
						" select seq_pnr_inspect_plan_item.NEXTVAL,r.plan_res_id,r.res_type,i.inspect_item,i.inspect_content,i.input_type,i.default_value,i.normal_range,i.dict_id,i.cycle,i.id,i.bigitem_name,1,'"+city+"',r.id,1,i.picture_flag,i.picture_num" +
						" from pnr_inspect_template_item i right join pnr_inspect_task_link r "+
						" on r.inspect_mapping_id = i.inspect_mapping_id "+
						" where i.status='1' and r.city='"+city+"' and r.create_time='"+createTime+"'";
		this.getJdbcTemplate().execute(sql2);
		
		String sql3 ="update pnr_inspect_plan_res set item_num=(" +
					" select cccc from (" +
						" select count(*) as cccc,plan_res_id from pnr_inspect_plan_item group by plan_res_id " +
					" ) tttt where tttt.plan_res_id=pnr_inspect_plan_res.id and pnr_inspect_plan_res.city='"+city+"' and pnr_inspect_plan_res.create_time='"+createTime+"')  where exists (select cccc from ( " +
					    " select count(*) as cccc,plan_res_id from pnr_inspect_plan_item  group by plan_res_id  " +
						" ) tttt where tttt.plan_res_id=pnr_inspect_plan_res.id and pnr_inspect_plan_res.city='"+city+"' and pnr_inspect_plan_res.create_time='"+createTime+"') and city='"+city+"' and create_time='"+createTime+"'";
			/*System.out.println("dddddddd-"+sql);		
			System.out.println("dddddddd-"+sql2);		
			System.out.println("dddddddd-"+sql3);		*/
		this.getJdbcTemplate().execute(sql3);
	}
	
	/**
	 * 产生突发巡检任务
	 * @param ids
	 * @return
	 */
	public String generateBurstInspectPlanRes(String ids){
		String mainTableId ="";
		LocalDate date = new LocalDate();
		String createTime =StaticMethod.getCurrentDateTime(); 
		String cycleStartTime = date.dayOfMonth().withMinimumValue()+" 00:00:00";
		String cycleEndTime = date.dayOfMonth().withMaximumValue()+" 23:59:59";
		
		cycleStartTime = CommonSqlHelper.formatDateTime(cycleStartTime);
		cycleEndTime = CommonSqlHelper.formatDateTime(cycleEndTime);
		
		//插入数据到临时表
		String tmpTableName = "tmp_pnr_inspect_plan_res";
		String selectSql = " select seq_pnr_inspect_plan_res.NEXTVAL as id,c.id as res_cfg_id,c.specialty,c.res_type,c.res_name," +
									"c.res_level,c.city,c.country,c.inspect_cycle,"+cycleStartTime+" as cycle_start_time,"+cycleEndTime+" as cycle_end_time," + 
									"c.execute_object,c.execute_dept,c.execute_type,0 as change_state,c.res_longitude, c.res_latitude," +
									"c.end_longitude,c.end_latitude,'" + createTime + "' as create_time,0 as force_assign,0 as inspect_State,t.item_num,t.id as template_Id,c.last_inspect_Time,c.eographical_environment,c.region_type,1 as burst_flag " +
							" from pnr_res_config c left join pnr_inspect_template t on c.res_type=t.res_type and c.id in ("+ids+")  ";
		String whereSql = "where t.status=1  ";
		
		if(InspectConstants.getSwitchConfig().isOpenTransLineInspect()){ //打开线路巡检
			selectSql += " and c.specialty<>'1122502'";
			whereSql += " and c.specialty<>'1122502'";
		}
		
		String tempTableSql = CommonSqlHelper.formatTempTableSql(selectSql, whereSql, tmpTableName);
		
		try{
			this.getJdbcTemplate().execute(tempTableSql);//如果是informix这个步骤能够直接往临时表插入数据
			if(CommonSqlHelper.isOracleDialect()){
				String insertTempSql = "insert into " + tmpTableName + selectSql + whereSql;
				this.getJdbcTemplate().execute(insertTempSql);
			}
			
			//复制临时表数据到巡检任务表
			String copyToMainTable = "insert into pnr_inspect_plan_res(id,res_cfg_id,specialty,res_type,res_name,res_level,city,country,inspect_cycle,cycle_start_time,cycle_end_time,execute_object,execute_dept,execute_type,change_state,res_longitude, res_latitude,end_longitude,end_latitude,create_time,force_assign,inspect_State,item_num,template_Id,last_inspect_Time,eographical_environment,region_type,burst_flag) select * from "+tmpTableName;
			this.getJdbcTemplate().execute(copyToMainTable);
			//查看pnr_inspect_plan_res的id
			String mainTableSql = "select id from "+tmpTableName;
			Map m =this.getJdbcTemplate().queryForMap(mainTableSql);
			
				mainTableId= m.get("ID").toString();
			
			//生成PnrInspectTaskLink数据
			String PnrInspectTaskLinkSQL = "insert into pnr_inspect_task_link(id,plan_id,plan_res_id,inspect_id,inspect_link_id,inspect_mapping_id,device_specialty_name,device_type_name,netres_name,netres_table_name,netres_id,res_type,city,create_time) " +
							" select seq_pnr_inspect_plan_res.NEXTVAL," +
								"''," +
						        "a.id as plan_res_id," +
						        "a.res_cfg_id as inspect_id," +
						        "b.id as inspect_link_id," +
						        "b.inspect_mapping_id," +
						        "b.device_specialty_name," +
						        "b.device_type_name," +
						        "b.netres_name," +
						        "b.netres_table_name," +
						        "b.netres_id," +
						        "a.res_type," +
						        "a.city," +
						        "a.create_time " +
							" from "+tmpTableName+" a " +
							" left join pnr_inspect_link b " +
							" on a.res_cfg_id = b.inspect_id ";
			this.getJdbcTemplate().execute(PnrInspectTaskLinkSQL);
			
		}finally{ //由于建表语句为DDL语句，不可回滚，如果前面的代码报错，需要在finally中删除临时表，否则下次再次执行会报临时表已经建立的错误
			//删除临时表
			String delTmpTable = "drop table "+tmpTableName;
			this.getJdbcTemplate().execute(delTmpTable);
		}
		
		return mainTableId;
	}
	
	/**
	 * 产生突发巡检任务 2013-08-23加
	 * @param ids
	 * @param  cycleStartTime
	 * @param  cycleEndTime
	 * @param  novalue
	 * @return
	 */
	public String generateBurstInspectPlanRes(String ids,String cycleStartTime,String cycleEndTime,String novalue){
		String createTime =StaticMethod.getCurrentDateTime(); 
		String mainTableId ="";
		cycleStartTime = CommonSqlHelper.formatDateTime(cycleStartTime);
		cycleEndTime = CommonSqlHelper.formatDateTime(cycleEndTime);
		
		//插入数据到临时表
		String tmpTableName = "tmp_pnr_inspect_plan_res";
		String selectSql = " select seq_pnr_inspect_plan_res.NEXTVAL as id,c.id as res_cfg_id,c.specialty,c.res_type,c.res_name," +
		"c.res_level,c.city,c.country,c.inspect_cycle,"+cycleStartTime+" as cycle_start_time,"+cycleEndTime+" as cycle_end_time," + 
		"c.execute_object,c.execute_dept,c.execute_type,0 as change_state,c.res_longitude, c.res_latitude," +
		"c.end_longitude,c.end_latitude,'" + createTime + "' as create_time,0 as force_assign,0 as inspect_State,t.item_num,t.id as template_Id,c.last_inspect_Time,c.eographical_environment,c.region_type,1 as burst_flag " +
		" from pnr_res_config c left join pnr_inspect_template t on c.res_type=t.res_type and c.id in ("+ids+")  ";
		String whereSql = "where t.status=1  ";
		
		if(InspectConstants.getSwitchConfig().isOpenTransLineInspect()){ //打开线路巡检
			selectSql += " and c.specialty<>'1122502'";
			whereSql += " and c.specialty<>'1122502'";
		}
		
		String tempTableSql = CommonSqlHelper.formatTempTableSql(selectSql, whereSql, tmpTableName);
		
		try{
			this.getJdbcTemplate().execute(tempTableSql);//如果是informix这个步骤能够直接往临时表插入数据
			if(CommonSqlHelper.isOracleDialect()){
				String insertTempSql = "insert into " + tmpTableName + selectSql + whereSql;
				this.getJdbcTemplate().execute(insertTempSql);
			}
			
			//复制临时表数据到巡检任务表
			String copyToMainTable = "insert into pnr_inspect_plan_res(id,res_cfg_id,specialty,res_type,res_name,res_level,city,country,inspect_cycle,cycle_start_time,cycle_end_time,execute_object,execute_dept,execute_type,change_state,res_longitude, res_latitude,end_longitude,end_latitude,create_time,force_assign,inspect_State,item_num,template_Id,last_inspect_Time,eographical_environment,region_type,burst_flag) select * from "+tmpTableName;
			this.getJdbcTemplate().execute(copyToMainTable);
			
			
			//查看pnr_inspect_plan_res的id
			String mainTableSql = "select id from "+tmpTableName;
			Map m =this.getJdbcTemplate().queryForMap(mainTableSql);
			
				mainTableId= m.get("ID").toString();
			
			//生成PnrInspectTaskLink数据
			String PnrInspectTaskLinkSQL = "insert into pnr_inspect_task_link(id,plan_id,plan_res_id,inspect_id,inspect_link_id,inspect_mapping_id,device_specialty_name,device_type_name,netres_name,netres_table_name,netres_id,res_type,city,create_time) " +
			" select seq_pnr_inspect_plan_res.NEXTVAL," +
			"''," +
			"a.id as plan_res_id," +
			"a.res_cfg_id as inspect_id," +
			"b.id as inspect_link_id," +
			"b.inspect_mapping_id," +
			"b.device_specialty_name," +
			"b.device_type_name," +
			"b.netres_name," +
			"b.netres_table_name," +
			"b.netres_id," +
			"a.res_type," +
			"a.city," +
			"a.create_time " +
			" from "+tmpTableName+" a " +
			" left join pnr_inspect_link b " +
			" on a.res_cfg_id = b.inspect_id ";
			this.getJdbcTemplate().execute(PnrInspectTaskLinkSQL);
			
		}finally{ //由于建表语句为DDL语句，不可回滚，如果前面的代码报错，需要在finally中删除临时表，否则下次再次执行会报临时表已经建立的错误
			//删除临时表
			String delTmpTable = "drop table "+tmpTableName;
			this.getJdbcTemplate().execute(delTmpTable);
		}
		
		return mainTableId;
	}
	
	/**
	 * 产生突发巡检任务
	 * @param ids
	 * @return
	 */
	public String generateBurstInspectPlanRes(String ids,String planStartTime,String planEndTime){
		String createTime =StaticMethod.getCurrentDateTime(); 
		String mainTableId="";
		planStartTime = CommonSqlHelper.formatDateTime(planStartTime);
		planEndTime = CommonSqlHelper.formatDateTime(planEndTime);
		
		//插入数据到临时表
		String tmpTableName = "tmp_pnr_inspect_plan_res";
		String selectSql = " select seq_pnr_inspect_plan_res.NEXTVAL as id,c.id as res_cfg_id,c.specialty,c.res_type,c.res_name," +
		"c.res_level,c.city,c.country,"+planStartTime+" as cycle_start_time,"+planEndTime+" as cycle_end_time," + 
		"c.execute_object,c.execute_dept,c.execute_type,0 as change_state,c.res_longitude, c.res_latitude," +
		"c.end_longitude,c.end_latitude,'" + createTime + "' as create_time,0 as force_assign,0 as inspect_State,t.item_num,t.id as template_Id,c.last_inspect_Time,c.eographical_environment,c.region_type,1 as burst_flag " +
		" from pnr_res_config c left join pnr_inspect_template t on c.res_type=t.res_type and c.id in ("+ids+") ";
		String whereSql = "where t.status=1 ";
		
		if(InspectConstants.getSwitchConfig().isOpenTransLineInspect()){ //打开线路巡检
			selectSql += " and c.specialty<>'1122502'";
			whereSql += " and c.specialty<>'1122502'";
		}
		
		try{
			String tempTableSql = CommonSqlHelper.formatTempTableSql(selectSql, whereSql, tmpTableName);
			this.getJdbcTemplate().execute(tempTableSql);//如果是informix这个步骤能够直接往临时表插入数据
			if(CommonSqlHelper.isOracleDialect()){
				String insertTempSql = "insert into " + tmpTableName + selectSql + whereSql;
				this.getJdbcTemplate().execute(insertTempSql);
			}
			
			//复制临时表数据到巡检任务表
			String copyToMainTable = "insert into pnr_inspect_plan_res(id,res_cfg_id,specialty,res_type,res_name,res_level,city,country,plan_start_time,plan_end_time,execute_object,execute_dept,execute_type,change_state,res_longitude, res_latitude,end_longitude,end_latitude,create_time,force_assign,inspect_State,item_num,template_Id,last_inspect_Time,eographical_environment,region_type,burst_flag) select * from "+tmpTableName;
			this.getJdbcTemplate().execute(copyToMainTable);
			//查看pnr_inspect_plan_res的id
			String mainTableSql = "select id from "+tmpTableName;
			Map m =this.getJdbcTemplate().queryForMap(mainTableSql);
			
				mainTableId= m.get("ID").toString();
			
			//生成PnrInspectTaskLink数据
			String PnrInspectTaskLinkSQL = "insert into pnr_inspect_task_link(id,plan_id,plan_res_id,inspect_id,inspect_link_id,inspect_mapping_id,device_specialty_name,device_type_name,netres_name,netres_table_name,netres_id,res_type,city,create_time) " +
			" select seq_pnr_inspect_plan_res.NEXTVAL," +
			"''," +
			"a.id as plan_res_id," +
			"a.res_cfg_id as inspect_id," +
			"b.id as inspect_link_id," +
			"b.inspect_mapping_id," +
			"b.device_specialty_name," +
			"b.device_type_name," +
			"b.netres_name," +
			"b.netres_table_name," +
			"b.netres_id," +
			"a.res_type," +
			"a.city," +
			"a.create_time " +
			" from "+tmpTableName+" a " +
			" left join pnr_inspect_link b " +
			" on a.res_cfg_id = b.inspect_id " +
			" where a.res_cfg_id = b.inspect_id";
			this.getJdbcTemplate().execute(PnrInspectTaskLinkSQL);
		}finally{ //由于建表语句为DDL语句，不可回滚，如果前面的代码报错，需要在finally中删除临时表，否则下次再次执行会报临时表已经建立的错误
			//删除临时表
			String delTmpTable = "drop table "+tmpTableName;
			this.getJdbcTemplate().execute(delTmpTable);
		}
		return mainTableId;
	}
	
	/**
	 * 产生突发巡检计划执行项
	 * @param city 地市
	 * @param planResId 元任务ID
	 */
	public void generateBurstInspectPlanItem(String planResId){
		/*String sql = "insert into pnr_inspect_plan_item(id,plan_res_id,res_type,inspect_item,content,input_type,default_value,normal_range,dict_id,inspect_cycle,template_Item_Id,bigitem_name,exception_flag,city,inspect_task_link_id,device_inspect_flag,picture_flag,picture_num) " +
				" select seq_pnr_inspect_plan_item.NEXTVAL,r.id,r.res_type,i.inspect_item,i.inspect_content,i.input_type,i.default_value,i.normal_range,i.dict_id,i.cycle,i.id,i.bigitem_name,1,r.city,'',0,i.picture_flag,i.picture_num" +
					" from pnr_inspect_plan_res r " +
					" left join pnr_inspect_template_item i " +
					" on i.template_Id=r.template_Id and device_inspect_flag <>1" +
					" where r.create_time='"+createTime+"' and i.status=1";*/
		
		String sql = "insert into pnr_inspect_plan_item(id,plan_res_id,res_type,inspect_item,content,input_type,default_value,normal_range,dict_id,inspect_cycle,template_Item_Id,bigitem_name,exception_flag,city,inspect_task_link_id,device_inspect_flag,picture_flag,picture_num) " +
				" select seq_pnr_inspect_plan_item.NEXTVAL,u.rid,u.res_type,u.inspect_item,u.inspect_content,u.input_type,u.default_value,u.normal_range,u.dict_id,u.cycle,u.id,u.bigitem_name,1,u.city,'',0,u.picture_flag,u.picture_num from (" +
				" select r.id rid,r.res_type,i.inspect_item,i.inspect_content,i.input_type,i.default_value,i.normal_range,i.dict_id,i.cycle,i.id,i.bigitem_name,r.city,i.picture_flag,i.picture_num" +
					" from pnr_inspect_plan_res r " +
					" left join pnr_inspect_template_item i " +
					" on i.template_Id=r.template_Id and device_inspect_flag <>1" +
					" where r.id='"+planResId+"' and i.status=1  order by i.order_no asc,i.id asc) u";
		
		this.getJdbcTemplate().execute(sql);
		
		//生成网络资源设备巡检项（核心由pnr_inspect_template_item中的inspect_mapping_id于pnr_inspect_task_link中的inspect_mapping_id，由于pnr_inspect_task_link无inspect_mapping_id，故由pnr_inspect_task_link与pnr_inspect_plan_res关联得到inspect_mapping_id）
		String sql2 = "insert into pnr_inspect_plan_item(id,plan_res_id,res_type,inspect_item,content,input_type,default_value,normal_range,dict_id,inspect_cycle,template_Item_Id,bigitem_name,exception_flag,city,inspect_task_link_id,device_inspect_flag,picture_flag,picture_num) " +
						" select seq_pnr_inspect_plan_item.NEXTVAL,r.plan_res_id,r.res_type,i.inspect_item,i.inspect_content,i.input_type,i.default_value,i.normal_range,i.dict_id,i.cycle,i.id,i.bigitem_name,1,r.city,r.id,1,i.picture_flag,i.picture_num " +
						" from pnr_inspect_template_item i right join pnr_inspect_task_link r "+
						" on r.inspect_mapping_id = i.inspect_mapping_id "+
						" where i.status='1' and r.id='"+planResId+"'";
		this.getJdbcTemplate().execute(sql2);
		
		String sql3 ="update pnr_inspect_plan_res set item_num=(" +
						" select cccc from (" +
							" select count(*) as cccc,plan_res_id from pnr_inspect_plan_item group by plan_res_id " +
						" ) tttt where tttt.plan_res_id=pnr_inspect_plan_res.id  and pnr_inspect_plan_res.id='"+planResId+"')  where exists (select cccc from ( " +
						    " select count(*) as cccc,plan_res_id from pnr_inspect_plan_item  group by plan_res_id  " +
							" ) tttt where tttt.plan_res_id=pnr_inspect_plan_res.id and pnr_inspect_plan_res.id='"+planResId+"') and id='"+planResId+"'";
		/*System.out.println("111111111-----"+sql);
		System.out.println("2222222222222------"+sql2);
		System.out.println("33333333333_-----"+sql3);*/
		this.getJdbcTemplate().execute(sql3);
	}

	@Override
	public void insertPlanExcuteDate() {
		// TODO Auto-generated method stub
		String sql = "insert into pnr_inspect_plan_excute_record values(sysdate)";
		this.getJdbcTemplate().execute(sql);
	}

	@Override
	public int isPlanExcute(String date) {
		
		date =date.substring(0,7); 
		String sql = "select d.* from pnr_inspect_plan_excute_record d where to_char(d.excute_date,'yyyy-mm')='"+date+"'";
		
//		size =this.getJdbcTemplate().queryForInt(sql);	
		List list =this.getJdbcTemplate().queryForList(sql);	
//		System.out.println("select * ");
		return list.size();
	}
}
