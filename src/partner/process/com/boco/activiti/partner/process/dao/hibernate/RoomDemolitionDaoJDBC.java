package com.boco.activiti.partner.process.dao.hibernate;

import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.activiti.partner.process.dao.IRoomDemolitionJDBCDao;
import com.boco.activiti.partner.process.model.PnrAndroidPhotoFile;
import com.boco.activiti.partner.process.po.ConditionQueryModel;
import com.boco.activiti.partner.process.po.RoomDemolitionModel;
import com.boco.activiti.partner.process.po.TaskModel;

public class RoomDemolitionDaoJDBC extends JdbcDaoSupport implements
      IRoomDemolitionJDBCDao {

	@Override
	public int getTransferNewPrecheckCount(String userid, String flag,String processKey,
			ConditionQueryModel conditionQueryModel) {
		String sql = "";
		String selectSql =	"select count(m.id) as total" +
							"           from pnr_act_roomdemolition m" + 
							"          where m.assignee = ?";

//		String selectSql = "select count(*) as total from (select t.id_ as TaskId,t.task_def_key_ as taskDefKey,t.name_ as statusName,m.* from (select RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_ WHERE RES.ASSIGNEE_ = "
//			+ "?"
//			+ " and D.KEY_ = "
//			+ "?"
//			+ " and RES.Suspension_State_ = 1) t,pnr_act_roomdemolition m where t.proc_inst_id_ = m.process_instance_id ";
		ArrayList whereList=new ArrayList();
		whereList.add(userid);
		//whereList.add(processKey);
		
		String whereSql = "";
		 
//		if(flag!=null && "backlog".equals(flag.trim())){
//			whereSql = " and (m.state!=8 and m.state!=3)";
//		}else if("wait".equals(flag.trim())){
//			whereSql = " and m.state=3";
//			
//		}
		if (conditionQueryModel.getSendStartTime() != null && !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >=?";
			whereList.add(conditionQueryModel.getSendStartTime());
		}
		if (conditionQueryModel.getSendEndTime() != null && !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <=?";
			whereList.add(conditionQueryModel.getSendEndTime());
		}
		if (conditionQueryModel.getWorkNumber() != null && !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and m.process_instance_id =?";
			whereList.add(conditionQueryModel.getWorkNumber());
		}
		if (conditionQueryModel.getTheme() != null && !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and instr(m.theme,?)>0 ";
			whereList.add(conditionQueryModel.getTheme());
		}
		if (conditionQueryModel.getStatus() != null && !conditionQueryModel.getStatus().equals("")) {
			whereSql += " and m.task_def_key =?";
			whereList.add(conditionQueryModel.getStatus());
		}
//		if (conditionQueryModel.getStatus() != null && !conditionQueryModel.getStatus().equals("")) {
//			whereSql += " and t.task_def_key_ =?";
//			whereList.add(conditionQueryModel.getStatus());
//		}
		if (conditionQueryModel.getCity() != null && !conditionQueryModel.getCity().equals("")) {
			whereSql += " and m.city =?";
			whereList.add(conditionQueryModel.getCity());
		}
		if (conditionQueryModel.getCountry() != null && !conditionQueryModel.getCountry().equals("")) {
			whereSql += " and m.country = ?";
			whereList.add(conditionQueryModel.getCountry());
			
		}
//		if (conditionQueryModel.getLineType() != null && !conditionQueryModel.getLineType().equals("")) {
//			whereSql += " and m.work_type = '"+conditionQueryModel.getLineType()+"'";
//			
//		}
//		if (conditionQueryModel.getWorkOrderType() != null && !conditionQueryModel.getWorkOrderType().equals("")) {
//			whereSql += " and m.workorder_type_name = '"+conditionQueryModel.getWorkOrderType()+"'";
//			
//		}
//		if (conditionQueryModel.getPrecheckFlag() != null && !conditionQueryModel.getPrecheckFlag().equals("")) {
//			whereSql += " and m.precheck_flag = '"+conditionQueryModel.getPrecheckFlag()+"'";
//			
//		}
		sql = selectSql + whereSql;
//		sql = selectSql + whereSql+")";
		
		Object args[]=whereList.toArray();
		
		System.out.println("--------------机房拆除流程待回复列表总条数sql："+sql);


		int size = this.getJdbcTemplate().queryForInt(sql,args);
		return size;
	
	}

	@Override
	public List<RoomDemolitionModel> getTransferNewPrecheckList(String userid, String flag,
			String processKey, ConditionQueryModel conditionQueryModel,
			int firstResult, int endResult, int pageSize) {

		//String sql = "";
		String sql ="select temp2.* from (select temp1.*, rownum num from (";
		String selectSql =	"select id," +
							"        m.task_def_key      as taskDefKey," + 
							"        m.task_id           as taskId," + 
							"        process_instance_id," + 
							"        sheet_id," + 
							"        theme," + 
							"        city," + 
							"        country," + 
							"        m.task_def_key_name as statusName," + 
							"        station_type," + 
							"        station_name," + 
							"        station_level," + 
							"        station_area," + 
							"        station_equity," + 
							"        annual_rent," + 
							"        hire_date," + 
							"        contract_time," + 
							"        opticcable_num," + 
							"        opticcable_way," + 
							"        equipment_rack_num," + 
							"        material_cost," + 
							"        energy_station_name," + 
							"        energy_station_code," + 
							"        send_time," + 
							"        rollback_flag" + 
							"   from pnr_act_roomdemolition m" + 
							"  where m.assignee = ?";

//		String selectSql = "select t.id_ as TaskId,t.task_def_key_ as taskDefKey,t.name_ as statusName,m.* from (select RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_ WHERE RES.ASSIGNEE_ = "
//			+ "?"
//			+ " and D.KEY_ = "
//			+ "?"
//			+ " and RES.Suspension_State_ = 1) t,pnr_act_roomdemolition m where t.proc_inst_id_ = m.process_instance_id ";
		
		ArrayList whereList=new ArrayList();
		whereList.add(userid);
		//whereList.add(processKey);
		
		String whereSql = "";
		 
//		if(flag!=null && "backlog".equals(flag.trim())){
//			whereSql = " and (m.state!=8 and m.state!=3)";
//		}else if("wait".equals(flag.trim())){
//			whereSql = " and m.state=3";
//			
//		}
		if (conditionQueryModel.getSendStartTime() != null && !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >=?";
			whereList.add(conditionQueryModel.getSendStartTime());
		}
		if (conditionQueryModel.getSendEndTime() != null && !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <=?";
			whereList.add(conditionQueryModel.getSendEndTime());
		}
		if (conditionQueryModel.getWorkNumber() != null && !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and m.process_instance_id =?";
			whereList.add(conditionQueryModel.getWorkNumber());
		}
		if (conditionQueryModel.getTheme() != null && !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and instr(m.theme,?)>0 ";
			whereList.add(conditionQueryModel.getTheme());
		}
		if (conditionQueryModel.getStatus() != null && !conditionQueryModel.getStatus().equals("")) {
			whereSql += " and m.task_def_key =?";
			whereList.add(conditionQueryModel.getStatus());
		}
//		if (conditionQueryModel.getStatus() != null && !conditionQueryModel.getStatus().equals("")) {
//			whereSql += " and t.task_def_key_ =?";
//			whereList.add(conditionQueryModel.getStatus());
//		}
		if (conditionQueryModel.getCity() != null && !conditionQueryModel.getCity().equals("")) {
			whereSql += " and m.city =?";
			whereList.add(conditionQueryModel.getCity());
		}
		if (conditionQueryModel.getCountry() != null && !conditionQueryModel.getCountry().equals("")) {
			whereSql += " and m.country = ?";
			whereList.add(conditionQueryModel.getCountry());
			
		}
//		if (conditionQueryModel.getLineType() != null && !conditionQueryModel.getLineType().equals("")) {
//			whereSql += " and m.work_type = '"+conditionQueryModel.getLineType()+"'";
//			
//		}
//		if (conditionQueryModel.getWorkOrderType() != null && !conditionQueryModel.getWorkOrderType().equals("")) {
//			whereSql += " and m.workorder_type_name = '"+conditionQueryModel.getWorkOrderType()+"'";
//			
//		}
//		if (conditionQueryModel.getPrecheckFlag() != null && !conditionQueryModel.getPrecheckFlag().equals("")) {
//			whereSql += " and m.precheck_flag = '"+conditionQueryModel.getPrecheckFlag()+"'";
//			
//		}
		
		sql += selectSql + whereSql
				+ " order by m.send_time ) temp1 where rownum <=?) temp2 where temp2.num >?";
		
		whereList.add(endResult * pageSize);
		whereList.add(firstResult * pageSize);
		
		Object values[]=whereList.toArray();
		
		System.out.println("------------------机房拆除待回复列表："+sql);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<RoomDemolitionModel> r = new ArrayList<RoomDemolitionModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,values);
		for (Map map : list) {
			RoomDemolitionModel model = new RoomDemolitionModel();
			//ID
			if(map.get("id")!=null && !"".equals(map.get("id"))){
				
				model.setId(map.get("id").toString());
			}
			
			//ID
			if(map.get("taskDefKey")!=null && !"".equals(map.get("taskDefKey"))){
				
				model.setTaskDefKey(map.get("taskDefKey").toString());
			}
			
			//任务ID
			if(map.get("TaskId")!=null && !"".equals(map.get("TaskId"))){
				
				model.setTaskId(map.get("TaskId").toString());
			}
			//流程实例号
			if(map.get("process_instance_id")!=null && !"".equals(map.get("process_instance_id"))){
				
				model.setProcessInstanceId(map.get("process_instance_id").toString());
			}
			//工单编码
			if(map.get("sheet_id")!=null && !"".equals(map.get("sheet_id"))){
				
				model.setSheetId(map.get("sheet_id").toString());
			}
			//工单名称
			if(map.get("theme")!=null && !"".equals(map.get("theme"))){
				
				model.setTheme(map.get("theme").toString());
			}
			//地市
			if(map.get("city")!=null && !"".equals(map.get("city"))){
				
				model.setCity(map.get("city").toString());
			}
			//区县
			if(map.get("country")!=null && !"".equals(map.get("country"))){
				
				model.setCountry(map.get("country").toString());
			}
			//工单状态
			if (map.get("statusName") != null && !"".equals(map.get("statusName"))) {
				
				model.setStatusName(map.get("statusName").toString());
			}
			//机房类型
			if (map.get("station_type") != null && !"".equals(map.get("station_type"))) {
				
				model.setStationType(map.get("station_type").toString());
			}
			//机房类型
			if (map.get("station_name") != null && !"".equals(map.get("station_name"))) {
				
				model.setStationName(map.get("station_name").toString());
			}
			//机房级别
			if (map.get("station_level") != null && !"".equals(map.get("station_level"))) {
				
				model.setStationLevel(map.get("station_level").toString());
			}
			//机房面积
			if (map.get("station_area") != null && !"".equals(map.get("station_area"))) {
				
				model.setStationArea(map.get("station_area").toString());
			}
			//机房产权
			if (map.get("station_equity") != null && !"".equals(map.get("station_equity"))) {
				
				model.setStationEquity(map.get("station_equity").toString());
			}
			//年租金
			if (map.get("annual_rent") != null && !"".equals(map.get("annual_rent"))) {
				
				model.setAnnualRent(map.get("annual_rent").toString());
			}
			//租用日期
			if (map.get("hire_date") != null) {
				if (!"".equals(map.get("hire_date"))) {
					try {
						model.setHireDate(format.parse(map.get("hire_date")
								.toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

			}
			//合同到期时间
			if (map.get("contract_time") != null) {
				if (!"".equals(map.get("contract_time"))) {
					try {
						model.setContractTime(format.parse(map.get("contract_time")
								.toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				
			}
			//光缆纤芯数
			if (map.get("opticcable_num") != null && !"".equals(map.get("opticcable_num"))) {
				
				model.setOpticcableNum(map.get("opticcable_num").toString());
			}
			//光缆改造方式
			if (map.get("opticcable_way") != null && !"".equals(map.get("opticcable_way"))) {
				
				model.setOpticcableWay(map.get("opticcable_way").toString());
			}
			//设备机架数
			if (map.get("equipment_rack_num") != null && !"".equals(map.get("equipment_rack_num"))) {
				
				model.setEquipmentRackNum(map.get("equipment_rack_num").toString());
			}
			//材料费用
			if (map.get("material_cost") != null && !"".equals(map.get("material_cost"))) {
				
				model.setMaterialCost(map.get("material_cost").toString());
			}
			//对应能耗系统机房名称
			if (map.get("energy_station_name") != null && !"".equals(map.get("energy_station_name"))) {
				
				model.setEnergyStationName(map.get("energy_station_name").toString());
			}
			//对应能耗系统机房编号
			if (map.get("energy_station_code") != null && !"".equals(map.get("energy_station_code"))) {
				
				model.setEnergyStationCode(map.get("energy_station_code").toString());
			}
			//发起时间
			if (map.get("send_time") != null) {
				if (!"".equals(map.get("send_time"))) {
					try {
						model.setSendTime(format.parse(map.get("send_time")
								.toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				
			}
			
			//驳回标识
			if (map.get("rollback_flag") != null && !"".equals(map.get("rollback_flag"))) {
				
				model.setRollbackFlag(map.get("rollback_flag").toString());
			}
			
			r.add(model);
		}
		return r;
	}

	@Override
	public List<Map> getPrecheckShipModelBycountryCharge(String countryCharge) {
		String sql ="select * from pnr_act_transfer_precheck_ship s where s.country_zg='"+countryCharge+"'";
		
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	/**
	 * 已处理工单条数
	 */ 
	public int getHaveProcessCount(String processDefinitionKey, String userId,
			ConditionQueryModel conditionQueryModel){
		String sql = "";
		//未添加归档工单
		//String selectSql = "select count(processInstanceId) as total from (select ACTI.Proc_Inst_Id_ as processInstanceId,ACTI.task_def_key_ as taskDefKey,ACTI.name_ as statusName,MAIN.* from (select distinct k.proc_inst_id_,k.name_,k.task_def_key_ from ACT_RU_TASK k inner join ACT_RE_PROCDEF D on k.PROC_DEF_ID_ = D.ID_ where D.KEY_ = '"+processDefinitionKey+"' and (exists (select LINK.USER_ID_ from ACT_HI_IDENTITYLINK LINK where USER_ID_ = '"+userId+"' and LINK.PROC_INST_ID_ = k.proc_inst_id_)) and (exists(select kst.assignee_ from act_hi_taskinst kst where kst.assignee_ = '"+userId+"' and kst.proc_inst_id_ = k.proc_inst_id_ and kst.end_time_ is not null))) ACTI,pnr_act_roomdemolition MAIN WHERE ACTI.Proc_Inst_Id_ = MAIN.PROCESS_INSTANCE_ID(+) ) ST";
		//添加归档工单
		String selectSql = "select count(processInstanceId) as total from (select ACTI.Proc_Inst_Id_ as processInstanceId,to_char(ACTI.task_def_key_) as taskDefKey,to_char(ACTI.name_) as statusName,MAIN.* from (select k.proc_inst_id_,k.name_,k.task_def_key_ from ACT_RU_TASK k inner join ACT_RE_PROCDEF D on k.PROC_DEF_ID_ = D.ID_ where D.KEY_ = ? and (exists (select LINK.USER_ID_ from ACT_HI_IDENTITYLINK LINK where USER_ID_ = ? and LINK.PROC_INST_ID_ = k.proc_inst_id_)) and (exists(select kst.assignee_ from act_hi_taskinst kst where kst.assignee_ = ? and kst.proc_inst_id_ = k.proc_inst_id_ and kst.end_time_ is not null))) ACTI,pnr_act_roomdemolition MAIN WHERE ACTI.Proc_Inst_Id_ = MAIN.PROCESS_INSTANCE_ID(+) " +
		"		 union													 "+
		"		 select MAIN_1.Process_Instance_Id as processInstanceId, "+
        "        'archive' as taskDefKey,								 "+					
        "         '已归档' as statusName,									 "+
        "          MAIN_1.*												 "+
        "          from pnr_act_roomdemolition MAIN_1					 "+
        "          where MAIN_1.State = '5'								 "+
		"					) ST";
ArrayList whereList =new ArrayList();
		
		whereList.add(processDefinitionKey);
		whereList.add(userId);
		whereList.add(userId);
		
		String whereSql = " WHERE 1=1";
		if (conditionQueryModel.getSendStartTime() != null
				&& !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and to_char(ST.send_time,'yyyy-mm-dd hh24:mi:ss') >= ?";
			whereList.add(conditionQueryModel.getSendStartTime());
		}
		if (conditionQueryModel.getSendEndTime() != null
				&& !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and to_char(ST.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?";
			whereList.add(conditionQueryModel.getSendEndTime());
		}
		if (conditionQueryModel.getWorkNumber() != null
				&& !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and ST.processInstanceId =?";
			whereList.add(conditionQueryModel.getWorkNumber());
		}
		if (conditionQueryModel.getTheme() != null
				&& !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and instr(ST.theme,?)>0 ";
			whereList.add(conditionQueryModel.getTheme());
		}
		if (conditionQueryModel.getStatus() != null
				&& !conditionQueryModel.getStatus().equals("")) {
			whereSql += " and ST.taskDefKey = ?";
			whereList.add(conditionQueryModel.getStatus());

		}
		sql += selectSql + whereSql;
		
		Object args[]=whereList.toArray();

		System.out.println("--------------机房拆除流程已处理工单条数sql=" + sql);

		int size = this.getJdbcTemplate().queryForInt(sql,args);
		return size;
	}
	
	/**
	 * 已处理工单明细
	 */
	public List<RoomDemolitionModel> getHaveProcessList(String processDefinitionKey,
			String userId,ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize){
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		//未添加归档工单
		//String selectSql = "select * from (select ACTI.Proc_Inst_Id_ as processInstanceId,ACTI.task_def_key_ as taskDefKey,ACTI.name_ as statusName,MAIN.* from (select distinct k.proc_inst_id_,k.name_,k.task_def_key_ from ACT_RU_TASK k inner join ACT_RE_PROCDEF D on k.PROC_DEF_ID_ = D.ID_ where D.KEY_ = '"+processDefinitionKey+"' and (exists (select LINK.USER_ID_ from ACT_HI_IDENTITYLINK LINK where USER_ID_ = '"+userId+"' and LINK.PROC_INST_ID_ = k.proc_inst_id_)) and (exists(select kst.assignee_ from act_hi_taskinst kst where kst.assignee_ = '"+userId+"' and kst.proc_inst_id_ = k.proc_inst_id_ and kst.end_time_ is not null))) ACTI,pnr_act_roomdemolition MAIN WHERE ACTI.Proc_Inst_Id_ = MAIN.PROCESS_INSTANCE_ID(+)) ST";
		//添加归档工单
		String selectSql = "select * from (select to_char(ACTI.Proc_Inst_Id_) as processInstanceId,to_char(ACTI.task_def_key_) as taskDefKey,to_char(ACTI.name_) as statusName,MAIN.* from (select k.proc_inst_id_,k.name_,k.task_def_key_ from ACT_RU_TASK k inner join ACT_RE_PROCDEF D on k.PROC_DEF_ID_ = D.ID_ where D.KEY_ = ? and (exists (select LINK.USER_ID_ from ACT_HI_IDENTITYLINK LINK where USER_ID_ = ? and LINK.PROC_INST_ID_ = k.proc_inst_id_)) and (exists(select kst.assignee_ from act_hi_taskinst kst where kst.assignee_ = ? and kst.proc_inst_id_ = k.proc_inst_id_ and kst.end_time_ is not null))) ACTI,pnr_act_roomdemolition MAIN WHERE ACTI.Proc_Inst_Id_ = MAIN.PROCESS_INSTANCE_ID(+)" +
		"		 union													 "+
		"		 select MAIN_1.Process_Instance_Id as processInstanceId, "+
        "        'archive' as taskDefKey,								 "+					
        "         '已归档' as statusName,									 "+
        "          MAIN_1.*												 "+
        "          from pnr_act_roomdemolition MAIN_1					 "+
        "          where MAIN_1.State = '5'								 "+
		"					) ST";
		
		ArrayList whereList =new ArrayList();
		
		whereList.add(processDefinitionKey);
		whereList.add(userId);
		whereList.add(userId);
		
		String whereSql = " WHERE 1=1";
		if (conditionQueryModel.getSendStartTime() != null
				&& !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and to_char(ST.send_time,'yyyy-mm-dd hh24:mi:ss') >= ?";
			whereList.add(conditionQueryModel.getSendStartTime());
		}
		if (conditionQueryModel.getSendEndTime() != null
				&& !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and to_char(ST.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?";
			whereList.add(conditionQueryModel.getSendEndTime());
		}
		if (conditionQueryModel.getWorkNumber() != null
				&& !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and ST.processInstanceId =?";
			whereList.add(conditionQueryModel.getWorkNumber());
		}
		if (conditionQueryModel.getTheme() != null
				&& !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and instr(ST.theme,?)>0 ";
			whereList.add(conditionQueryModel.getTheme());
		}
		if (conditionQueryModel.getStatus() != null
				&& !conditionQueryModel.getStatus().equals("")) {
			whereSql += " and ST.taskDefKey = ?";
			whereList.add(conditionQueryModel.getStatus());

		}
		sql += selectSql + whereSql
				+ " order by ST.Send_Time ) temp1 where rownum <=?) temp2 where temp2.num >?";
		
		whereList.add(endResult * pageSize);
		whereList.add(firstResult * pageSize);
		
		
		Object values[] =whereList.toArray();
		
		System.out.println("--------------机房拆除流程已处理工单明细sql=" + sql);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<RoomDemolitionModel> r = new ArrayList<RoomDemolitionModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,values);
		for (Map map : list) {
			RoomDemolitionModel model = new RoomDemolitionModel();
			
			//流程实例号
			if(map.get("processInstanceId")!=null && !"".equals(map.get("processInstanceId"))){
				
				model.setProcessInstanceId(map.get("processInstanceId").toString());
			}
			//USERTASKID
			if(map.get("taskDefKey")!=null && !"".equals(map.get("taskDefKey"))){
				
				model.setTaskDefKey(map.get("taskDefKey").toString());
			}
			
			//工单状态
			if (map.get("statusName") != null && !"".equals(map.get("statusName"))) {
				
				model.setStatusName(map.get("statusName").toString());
			}
			
			//ID
			if(map.get("id")!=null && !"".equals(map.get("id"))){
				
				model.setId(map.get("id").toString());
			}
			
			//工单编码
			if(map.get("sheet_id")!=null && !"".equals(map.get("sheet_id"))){
				
				model.setSheetId(map.get("sheet_id").toString());
			}
			//工单名称
			if(map.get("theme")!=null && !"".equals(map.get("theme"))){
				
				model.setTheme(map.get("theme").toString());
			}
			//地市
			if(map.get("city")!=null && !"".equals(map.get("city"))){
				
				model.setCity(map.get("city").toString());
			}
			//区县
			if(map.get("country")!=null && !"".equals(map.get("country"))){
				
				model.setCountry(map.get("country").toString());
			}
			
			//机房类型
			if (map.get("station_type") != null && !"".equals(map.get("station_type"))) {
				
				model.setStationType(map.get("station_type").toString());
			}
			//机房类型
			if (map.get("station_name") != null && !"".equals(map.get("station_name"))) {
				
				model.setStationName(map.get("station_name").toString());
			}
			//机房级别
			if (map.get("station_level") != null && !"".equals(map.get("station_level"))) {
				
				model.setStationLevel(map.get("station_level").toString());
			}
			//机房面积
			if (map.get("station_area") != null && !"".equals(map.get("station_area"))) {
				
				model.setStationArea(map.get("station_area").toString());
			}
			//机房产权
			if (map.get("station_equity") != null && !"".equals(map.get("station_equity"))) {
				
				model.setStationEquity(map.get("station_equity").toString());
			}
			//年租金
			if (map.get("annual_rent") != null && !"".equals(map.get("annual_rent"))) {
				
				model.setAnnualRent(map.get("annual_rent").toString());
			}
			//租用日期
			if (map.get("hire_date") != null) {
				if (!"".equals(map.get("hire_date"))) {
					try {
						model.setHireDate(format.parse(map.get("hire_date")
								.toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

			}
			//合同到期时间
			if (map.get("contract_time") != null) {
				if (!"".equals(map.get("contract_time"))) {
					try {
						model.setContractTime(format.parse(map.get("contract_time")
								.toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				
			}
			//光缆纤芯数
			if (map.get("opticcable_num") != null && !"".equals(map.get("opticcable_num"))) {
				
				model.setOpticcableNum(map.get("opticcable_num").toString());
			}
			//光缆改造方式
			if (map.get("opticcable_way") != null && !"".equals(map.get("opticcable_way"))) {
				
				model.setOpticcableWay(map.get("opticcable_way").toString());
			}
			//设备机架数
			if (map.get("equipment_rack_num") != null && !"".equals(map.get("equipment_rack_num"))) {
				
				model.setEquipmentRackNum(map.get("equipment_rack_num").toString());
			}
			//材料费用
			if (map.get("material_cost") != null && !"".equals(map.get("material_cost"))) {
				
				model.setMaterialCost(map.get("material_cost").toString());
			}
			//对应能耗系统机房名称
			if (map.get("energy_station_name") != null && !"".equals(map.get("energy_station_name"))) {
				
				model.setEnergyStationName(map.get("energy_station_name").toString());
			}
			//对应能耗系统机房编号
			if (map.get("energy_station_code") != null && !"".equals(map.get("energy_station_code"))) {
				
				model.setEnergyStationCode(map.get("energy_station_code").toString());
			}
			//发起时间
			if (map.get("send_time") != null) {
				if (!"".equals(map.get("send_time"))) {
					try {
						model.setSendTime(format.parse(map.get("send_time")
								.toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				
			}
			
			//驳回标识
			if (map.get("rollback_flag") != null && !"".equals(map.get("rollback_flag"))) {
				
				model.setRollbackFlag(map.get("rollback_flag").toString());
			}
			
			r.add(model);
		}

		return r;
	}

	
	
	/**
	 * 由我创建的工单条数
	 * @param processDefinitionKey
	 * @param userId
	 * @param sendStartTime
	 * @param sendEndTime
	 * @param wsNum
	 * @param wsTitle
	 * @param status
	 * @return
	 */ 
	public int getByCreatingWorkOrderCount(String processDefinitionKey, String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status){
		String sql="";
		String selectSql = "select count(processInstanceId) as total from (select ACTI.Proc_Inst_Id_ as processInstanceId,MAIN.Theme as theme,MAIN.city,MAIN.country,MAIN.work_type,MAIN.workorder_type_name,MAIN.sub_type_name,MAIN.key_word,MAIN.work_order_degree,MAIN.project_amount,MAIN.Initiator as initiator,MAIN.Submit_Application_Time as SubmitTime,MAIN.TASK_ASSIGNEE,MAIN.Send_Time as sendTime,MAIN.State,MAIN.One_Initiator as oneInitiator,MAIN.Second_Initiator as twoInitiator,ACTI.END_TIME_,ACTI.DELETE_REASON_,case when ACTI.END_TIME_ is not null and ACTI.DELETE_REASON_ is null then '已归档' when ACTI.END_TIME_ is not null and ACTI.DELETE_REASON_ is not null then '已删除' when MAIN.State = '8' then '等待接口调用' else to_char(ACTI.StatusName) end as StatusName,case when ACTI.END_TIME_ is not null and ACTI.DELETE_REASON_ is null then 'archive' when ACTI.END_TIME_ is not null and ACTI.DELETE_REASON_ is not null then 'delete' when MAIN.State = '8' then 'waitingCallInterface' else to_char(ACTI.TaskDefKey) end as TaskDefKey,ACTI.TaskId,decode(MAIN.sheet_id, null, '无', MAIN.sheet_id) as sheetId from (select A1.PROC_INST_ID_,A1.END_TIME_,A1.DELETE_REASON_,A2.NAME_ AS StatusName,A2.TASK_DEF_KEY_ AS TaskDefKey,A2.ID_ AS TaskId from (select RES.* from ACT_HI_PROCINST RES inner join ACT_RE_PROCDEF DEF on RES.PROC_DEF_ID_ = DEF.ID_ WHERE RES.PROC_DEF_ID_ like '"+processDefinitionKey+":%:%' and RES.START_USER_ID_ = '"+userId+"') A1,(select * from (select RES.* from ACT_HI_TASKINST RES) a,(select res.proc_inst_id_ as instId,max(res.start_time_) as maxStartTime from ACT_HI_TASKINST RES group by res.proc_inst_id_) b where a.proc_inst_id_ = b.instId and a.start_time_ = b.maxStartTime) A2 where a1.proc_inst_id_ = a2.proc_inst_id_) ACTI,PNR_ACT_TRANSFER_OFFICE_MAIN MAIN WHERE ACTI.Proc_Inst_Id_ = MAIN.PROCESS_INSTANCE_ID(+)) ST";
		String whereSql = " WHERE 1=1";
		if (sendStartTime != null && !sendStartTime.equals("")) {
			whereSql += " and ST.sendTime >=to_date('" + sendStartTime
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}
		if (sendEndTime != null && !sendEndTime.equals("")) {
			whereSql += " and ST.sendTime <= to_date('" + sendEndTime
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}
		if (wsNum != null && !wsNum.equals("")) {
			whereSql += " and ST.processInstanceId ='" + wsNum.trim() + "'";
		}
		if (wsTitle != null && !wsTitle.equals("")) {
			whereSql += " and ST.theme like '%" + wsTitle.trim() + "%'";
		}
		if (status != null && !status.equals("")) {
			whereSql += " and ST.taskDefKey = '"+status+"'";

		}
		sql += selectSql + whereSql;
		
		System.out.println("由我创建的工单条数sql="+sql);
		
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		return Integer.parseInt(list.get(0).get("total").toString());
	}
	
	/**
	 * 由我创建的工单明细
	 * 
	 * @param processDefinitionKey
	 * @param userId
	 * @param sendStartTime
	 * @param sendEndTime
	 * @param wsNum
	 * @param wsTitle
	 * @param status
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getByCreatingWorkOrderList(String processDefinitionKey,
			String userId, String sendStartTime, String sendEndTime,
			String wsNum, String wsTitle, String status, int firstResult,
			int endResult, int pageSize){
		String sql = "select temp2.* from (select temp1.*, rownum num from (";		
		String selectSql = "select * from (select ACTI.Proc_Inst_Id_ as processInstanceId,MAIN.Theme as theme,MAIN.city,MAIN.country,MAIN.work_type,MAIN.workorder_type_name,MAIN.sub_type_name,MAIN.key_word,MAIN.work_order_degree,MAIN.project_amount,MAIN.Initiator as initiator,MAIN.Submit_Application_Time as SubmitTime,MAIN.TASK_ASSIGNEE,MAIN.Send_Time as sendTime,MAIN.State,MAIN.One_Initiator as oneInitiator,MAIN.Second_Initiator as twoInitiator,ACTI.END_TIME_,ACTI.DELETE_REASON_,case when ACTI.END_TIME_ is not null and ACTI.DELETE_REASON_ is null then '已归档' when ACTI.END_TIME_ is not null and ACTI.DELETE_REASON_ is not null then '已删除' when MAIN.State = '8' then '等待接口调用' else to_char(ACTI.StatusName) end as StatusName,case when ACTI.END_TIME_ is not null and ACTI.DELETE_REASON_ is null then 'archive' when ACTI.END_TIME_ is not null and ACTI.DELETE_REASON_ is not null then 'delete' when MAIN.State = '8' then 'waitingCallInterface' else to_char(ACTI.TaskDefKey) end as TaskDefKey,ACTI.TaskId,decode(MAIN.sheet_id, null, '无', MAIN.sheet_id) as sheetId from (select A1.PROC_INST_ID_,A1.END_TIME_,A1.DELETE_REASON_,A2.NAME_ AS StatusName,A2.TASK_DEF_KEY_ AS TaskDefKey,A2.ID_ AS TaskId from (select RES.* from ACT_HI_PROCINST RES inner join ACT_RE_PROCDEF DEF on RES.PROC_DEF_ID_ = DEF.ID_ WHERE RES.PROC_DEF_ID_ like '"+processDefinitionKey+":%:%' and RES.START_USER_ID_ = '"+userId+"') A1,(select * from (select RES.* from ACT_HI_TASKINST RES) a,(select res.proc_inst_id_ as instId,max(res.start_time_) as maxStartTime from ACT_HI_TASKINST RES group by res.proc_inst_id_) b where a.proc_inst_id_ = b.instId and a.start_time_ = b.maxStartTime) A2 where a1.proc_inst_id_ = a2.proc_inst_id_) ACTI,PNR_ACT_TRANSFER_OFFICE_MAIN MAIN WHERE ACTI.Proc_Inst_Id_ = MAIN.PROCESS_INSTANCE_ID(+)) ST";
		String whereSql = " WHERE 1=1";
		if (sendStartTime != null && !sendStartTime.equals("")) {
			whereSql += " and ST.sendTime >=to_date('" + sendStartTime
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}
		if (sendEndTime != null && !sendEndTime.equals("")) {
			whereSql += " and ST.sendTime <= to_date('" + sendEndTime
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}
		if (wsNum != null && !wsNum.equals("")) {
			whereSql += " and ST.processInstanceId ='" + wsNum.trim() + "'";
		}
		if (wsTitle != null && !wsTitle.equals("")) {
			whereSql += " and ST.theme like '%" + wsTitle.trim() + "%'";
		}
		if (status != null && !status.equals("")) {
			whereSql += " and ST.taskDefKey = '"+status+"'";

		}
		sql += selectSql + whereSql
				+ " order by ST.sendTime desc) temp1 where rownum <="
				+ endResult * pageSize + ") temp2 where temp2.num > "
				+ firstResult * pageSize;

		System.out.println("由我创建的工单明细sql="+sql);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		for (Map map : list) {
			TaskModel model = new TaskModel();
			if(map.get("processInstanceId")!=null&&!"".equals(map.get("processInstanceId"))){
				model.setProcessInstanceId(map.get("processInstanceId").toString());
			}
			if(map.get("theme")!=null&&!"".equals(map.get("theme"))){
				model.setTheme(map.get("theme").toString());
			}
			if(map.get("city")!=null && !"".equals(map.get("city"))){
				
				model.setCity(map.get("city").toString());
			}
			if(map.get("country")!=null && !"".equals(map.get("country"))){
				model.setCountry(map.get("country").toString());
			}
			if(map.get("work_type")!=null && !"".equals(map.get("work_type"))){
				
				model.setWorkType(map.get("work_type").toString());
			}
			if(map.get("workorder_type_name")!=null && !"".equals(map.get("workorder_type_name"))){
				model.setWorkorderTypeName(map.get("workorder_type_name").toString());
			}
			
			if(map.get("sub_type_name")!=null && !"".equals(map.get("sub_type_name"))){
				model.setSubTypeName(map.get("sub_type_name").toString());
			}
			
			if(map.get("key_word")!=null && !"".equals(map.get("key_word"))){
				model.setKeyWord(map.get("key_word").toString());
			}
			if(map.get("work_order_degree")!=null && !"".equals(map.get("work_order_degree"))){
				model.setWorkOrderDegree(map.get("work_order_degree").toString());
			}
			
			if(map.get("project_amount")!=null && !"".equals(map.get("project_amount"))){				
				model.setProjectAmount(Double.parseDouble(map.get("project_amount").toString()));
			}
			if(map.get("initiator")!=null&&!"".equals(map.get("initiator"))){
				model.setInitiator(map.get("initiator").toString());
			}
			if (map.get("SubmitTime") != null) {
				if (!"".equals(map.get("SubmitTime"))) {
					try {
						model.setApplicationTime(format.parse(map.get("SubmitTime")
								.toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

			}
			if (map.get("sendTime") != null) {
				if (!"".equals(map.get("sendTime"))) {
					try {
						model.setSendTime(format.parse(map.get("sendTime")
								.toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				
			}
			if (map.get("State") != null) {
				if (!"".equals(map.get("State"))) {
					try {
						model.setState(Integer.parseInt(map.get("State").toString()));
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

			}
			
			if(map.get("oneInitiator")!=null&&!"".equals(map.get("oneInitiator"))){
				model.setOneInitiator(map.get("oneInitiator").toString());
			}
			if(map.get("twoInitiator")!=null&&!"".equals(map.get("twoInitiator"))){
				model.setTwoInitiator(map.get("twoInitiator").toString());
			}
			
			if(map.get("statusName")!=null&&!"".equals(map.get("statusName"))){
				model.setStatusName(map.get("statusName").toString());
			}
			if(map.get("StatusName")!=null&&!"".equals(map.get("StatusName"))){
				model.setStatusName(map.get("StatusName").toString());
			}
//			if (map.get("END_TIME_") != null) {
//				if (!"".equals(map.get("END_TIME_"))) {
//					if(map.get("DELETE_REASON_")==null||"".equals(map.get("DELETE_REASON_"))){
//						model.setStatusName("已归档");
//					}else{
//						model.setStatusName("已删除");
//					}
//				
//					}
//				}		
			if(map.get("TaskDefKey")!=null&&!"".equals(map.get("TaskDefKey"))){
				model.setTaskDefKey(map.get("TaskDefKey").toString());
			}
			if(map.get("TaskId")!=null&&!"".equals(map.get("TaskId"))){
				model.setTaskId(map.get("TaskId").toString());
			}
			 
			r.add(model);

		}	
		
		return r;
		
	}
	
	/**
	 * 工单抓回 条数
	 * @param processDefinitionKey 流程ID
	 * @param userTaskFlag	能抓回的环节ID集合
	 * @param userId	登录用户ID
	 * @param conditionQueryModel 查询条件
	 * @return
	 */
	public int getBackSheetCount(String processDefinitionKey,String userId,ConditionQueryModel conditionQueryModel){
		String sql = "";
		String selectSql = "select count(*) as total from (select * from act_ru_task t, transferNewPrechech_relation r where t.task_def_key_ = r.current_link and t.proc_def_id_ like '"+processDefinitionKey+":%:%' and t.task_def_key_ in ('workOrderCheck','cityLineExamine','cityLineDirectorAudit','cityManageExamine','cityManageDirectorAudit')) ta,(select distinct h.proc_inst_id_, h.task_def_key_, h.assignee_ from act_hi_taskinst h) hi,pnr_act_transfer_office_main m where ta.proc_inst_id_ = hi.proc_inst_id_ and ta.before_link = hi.task_def_key_ and ta.proc_inst_id_ = m.process_instance_id and hi.assignee_ = '"+userId+"' and (m.state != 3 and m.state != 6 and m.state != 8)";
		
		String whereSql = "";
		if (conditionQueryModel.getWorkNumber() != null && !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and m.process_instance_id ='" + conditionQueryModel.getWorkNumber().trim() + "'";
		}
		if (conditionQueryModel.getTheme() != null && !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and m.theme like '%" + conditionQueryModel.getTheme().trim() + "%'";
		}
	
		sql = selectSql + whereSql;
		 
		System.out.println("--------------本地网预检预修 工单抓回 条数sql="+sql);

		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		return Integer.parseInt(list.get(0).get("total").toString());
	}
	
	/**
	 * 工单抓回 明细
	 * @param processDefinitionKey 流程ID
	 * @param userTaskFlag	能抓回的环节ID集合
	 * @param userId	登录用户ID
	 * @param conditionQueryModel	查询条件
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getBackSheetList(String processDefinitionKey,String userId,ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize){
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		String selectSql = "select ta.id_ as TaskId,m.initiator as Initiator,m.one_initiator as OneInitiator,m.process_instance_id as ProcessInstanceId,m.send_time as SendTime,m.theme as Theme,m.task_assignee,m.state as State,m.submit_application_time as SubmitTime,m.city,m.country,m.work_type,m.workorder_type_name,m.sub_type_name,m.key_word,m.work_order_degree,m.rollback_flag,m.precheck_flag,m.project_amount,decode(m.sheet_id, null, '无', m.sheet_id) as sheetId,ta.name_ as statusName,ta.task_def_key_ as taskDefKey from (select * from act_ru_task t, transferNewPrechech_relation r where t.task_def_key_ = r.current_link and t.proc_def_id_ like '"+processDefinitionKey+":%:%' and t.task_def_key_ in ('workOrderCheck','cityLineExamine','cityLineDirectorAudit','cityManageExamine','cityManageDirectorAudit')) ta,(select distinct h.proc_inst_id_, h.task_def_key_, h.assignee_ from act_hi_taskinst h) hi,pnr_act_transfer_office_main m where ta.proc_inst_id_ = hi.proc_inst_id_ and ta.before_link = hi.task_def_key_ and ta.proc_inst_id_ = m.process_instance_id and hi.assignee_ = '"+userId+"' and (m.state != 3 and m.state != 6 and m.state != 8)";
		String whereSql = "";
		if (conditionQueryModel.getWorkNumber() != null && !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and m.process_instance_id ='" + conditionQueryModel.getWorkNumber().trim() + "'";
		}
		if (conditionQueryModel.getTheme() != null && !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and m.theme like '%" + conditionQueryModel.getTheme().trim() + "%'";
		}
		sql += selectSql + whereSql
				+ " order by ta.create_time_ desc) temp1 where rownum <="
				+ endResult * pageSize + ") temp2 where temp2.num > "
				+ firstResult * pageSize;
		
		System.out.println("--------------本地网预检预修 工单抓回 明细sql="+sql);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		for (Map map : list) {
			TaskModel model = new TaskModel();
			if(map.get("TaskId")!=null && !"".equals(map.get("TaskId"))){
				
				model.setTaskId(map.get("TaskId").toString());
			}
			if(map.get("Initiator")!=null && !"".equals(map.get("Initiator"))){
				
				model.setInitiator(map.get("Initiator").toString());
			}
			if(map.get("OneInitiator")!=null &&!"".equals(map.get("OneInitiator"))){
				
				model.setOneInitiator(map.get("OneInitiator").toString());
			}
			if(map.get("ProcessInstanceId")!=null && !"".equals(map.get("ProcessInstanceId"))){
				
				model.setProcessInstanceId(map.get("ProcessInstanceId").toString());
			}
			if (map.get("SubmitTime") != null) {
				if (!"".equals(map.get("SubmitTime"))) {
					try {
						model.setApplicationTime(format.parse(map.get("SubmitTime")
								.toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

			}
			if(map.get("Theme")!=null && !"".equals(map.get("Theme"))){
				
				model.setTheme(map.get("Theme").toString());
			}
			if(map.get("DeptId")!=null && !"".equals(map.get("DeptId"))){
				
				model.setDeptId(map.get("DeptId").toString());
			}
			if(map.get("State")!=null && !"".equals(map.get("State"))){
				
				model.setState(Integer.parseInt(map.get("State").toString()));
			}
			if(map.get("city")!=null && !"".equals(map.get("city"))){
				
				model.setCity(map.get("city").toString());
			}
			if(map.get("country")!=null && !"".equals(map.get("country"))){
				model.setCountry(map.get("country").toString());
			}
			if(map.get("work_type")!=null && !"".equals(map.get("work_type"))){
				
				model.setWorkType(map.get("work_type").toString());
			}
			if(map.get("workorder_type_name")!=null && !"".equals(map.get("workorder_type_name"))){
				model.setWorkorderTypeName(map.get("workorder_type_name").toString());
			}
			
			if(map.get("sub_type_name")!=null && !"".equals(map.get("sub_type_name"))){
				model.setSubTypeName(map.get("sub_type_name").toString());
			}
			
			if(map.get("key_word")!=null && !"".equals(map.get("key_word"))){
				model.setKeyWord(map.get("key_word").toString());
			}
			if(map.get("work_order_degree")!=null && !"".equals(map.get("work_order_degree"))){
				model.setWorkOrderDegree(map.get("work_order_degree").toString());
			}
			
			if(map.get("project_amount")!=null && !"".equals(map.get("project_amount"))){				
				model.setProjectAmount(Double.parseDouble(map.get("project_amount").toString()));
			}
			if(map.get("sheetId")!=null && !"".equals(map.get("sheetId"))){				
				model.setSheetId(map.get("sheetId").toString());
			}
			if(map.get("rollback_flag")!=null && !"".equals(map.get("rollback_flag"))){				
				model.setRollbackFlag(map.get("rollback_flag").toString());
			}
			if(map.get("precheck_flag")!=null && !"".equals(map.get("precheck_flag"))){				
				model.setPrecheckFlag(map.get("precheck_flag").toString());
			}
			if(map.get("statusName")!=null &&!"".equals(map.get("statusName"))){
				
				model.setStatusName(map.get("statusName").toString());
			}
			if(map.get("taskDefKey")!=null &&!"".equals(map.get("taskDefKey"))){
				
				model.setTaskDefKey(map.get("taskDefKey").toString());
			}
			r.add(model);
		}
		return r;
	}
	
	@Override
	public void judgeIsExitBypgohoIdAndProcessInstanceId(
			String processInstanceId) {
		StringBuilder sql = new StringBuilder("delete pnr_act_precheck_photo_ship p where p.processinstance_id='").append(processInstanceId).append("'");
		this.getJdbcTemplate().update(sql.toString());
	}

	@Override
	public List<PnrAndroidPhotoFile> getPrecheckPhotoes(String userId,String photoDescribe,String createTime,String photoCreate,String faultLocation) {
		StringBuilder sql = new StringBuilder("select * from pnr_android_photo p");
		sql.append("  where p.city=");
		sql.append(" (select substr(nvl(a.areaid,'0'),1,4) from pnr_user u");
		sql.append(" left join taw_system_dept d");
		sql.append(" on u.dept_id = d.deptid");
		sql.append(" left join taw_system_area a");
		sql.append(" on d.areaid=a.areaid");
		sql.append("  where 1=1 and d.deleted='0' ");
		String person = "";
		if(photoCreate!=null && !"".equals(photoCreate)){
			person = photoCreate;
		}else{
			person = userId;
		}
		sql.append(" and u.user_id='").append(person).append("')");
		sql.append(" and p.user_id = '").append(person).append("'");
		sql.append(" and nvl(p.state, '0') != '1'");
		if(photoDescribe!=null && !"".equals(photoDescribe)){
		sql.append(" and  p.faultdescription like '%").append(photoDescribe).append("%'");
		}
		if(faultLocation!=null && !"".equals(faultLocation)){
			sql.append(" and  p.faultLocation like '%").append(faultLocation).append("%'");
		}
		if(createTime!=null && !"".equals(createTime)){
			
			sql.append(" and to_date(p.createtime,'yyyy-mm-dd hh24:mi:ss')");
			sql.append(">=to_date('").append(createTime).append("','yyyy-mm-dd hh24:mi:ss')");
		}
		
		System.out.println("=====sql===="+sql.toString());
		List<Map> list =this.getJdbcTemplate().queryForList(sql.toString());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<PnrAndroidPhotoFile> photoList = new ArrayList<PnrAndroidPhotoFile>();
		if(list!=null && list.size()>0){
			for(Map map:list){
				PnrAndroidPhotoFile onePhoto = new PnrAndroidPhotoFile();
				onePhoto.setId(map.get("ID")==null?null:map.get("ID").toString());
				onePhoto.setPicture_id(map.get("PICUTRE_ID")==null?null:map.get("PICUTRE_ID").toString());
				//onePhoto.setFileData(map.get("FileData").toString());
				try {
					onePhoto.setCreateTime(map.get("CREATETIME")==null?null:map.get("CREATETIME").toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
				onePhoto.setLatitude(map.get("LATITUDE")==null?null:map.get("LATITUDE").toString());
				onePhoto.setLongitude(map.get("LONGITUDE")==null?null:map.get("LONGITUDE").toString());
				onePhoto.setFaultLocation(map.get("FAULTLOCATION")==null?null:map.get("FAULTLOCATION").toString() );
				onePhoto.setFaultDescription(map.get("FAULTDESCRIPTION")==null?null:map.get("FAULTDESCRIPTION").toString());
				onePhoto.setUserId(map.get("USER_ID")==null?null:map.get("USER_ID").toString());
				//取出数据库中图片存储的路径，并截取出图片名称
				//onePhoto.setPhotoName("照片名称");
				photoList.add(onePhoto);
			}
		}else{
			photoList = null;
		}
		return photoList;
	}

	
	@Override
	public List<PnrAndroidPhotoFile> showPhotoByProcessInstanceId(
			String processInstanceId) {
		StringBuilder sql = new StringBuilder("select * from pnr_act_precheck_photo_ship sp ");
		sql.append(" left join pnr_android_photo ap on sp.photo_id=ap.id ");
		sql.append(" where sp.processinstance_id='").append(processInstanceId).append("'");
		sql.append(" order by ap.system_time desc");
		System.out.println("----------新建工单图片查看sql="+sql);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<Map> list =this.getJdbcTemplate().queryForList(sql.toString());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<PnrAndroidPhotoFile> photoList = new ArrayList<PnrAndroidPhotoFile>();
		if(list!=null && list.size()>0){
			for(Map map:list){
				PnrAndroidPhotoFile onePhoto = new PnrAndroidPhotoFile();
				onePhoto.setId(map.get("ID")==null?null:map.get("ID").toString());
				onePhoto.setPicture_id(map.get("PICUTRE_ID")==null?null:map.get("PICUTRE_ID").toString());
				//onePhoto.setFileData((Clob)map.get("FILEDATA"));
				try {
					onePhoto.setCreateTime(map.get("CREATETIME")==null?null:map.get("CREATETIME").toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
				onePhoto.setLatitude(map.get("LATITUDE")==null?null:map.get("LATITUDE").toString());
				onePhoto.setLongitude(map.get("LONGITUDE")==null?null:map.get("LONGITUDE").toString());
				onePhoto.setFaultLocation(map.get("FAULTLOCATION")==null?null:map.get("FAULTLOCATION").toString() );
				onePhoto.setFaultDescription(map.get("FAULTDESCRIPTION")==null?null:map.get("FAULTDESCRIPTION").toString());
				onePhoto.setUserId(map.get("USER_ID")==null?null:map.get("USER_ID").toString());
				onePhoto.setPhotoSubType(map.get("PHOTO_SUB_TYPE")==null?null:map.get("PHOTO_SUB_TYPE").toString());
				onePhoto.setPath(map.get("PATH")==null?null:map.get("PATH").toString());
				if (map.get("SYSTEM_TIME") != null) {
					if (!"".equals(map.get("SYSTEM_TIME"))) {
						try {
							onePhoto.setSystemTime(format.parse(map.get("SYSTEM_TIME")
									.toString()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}

				}
				//取出数据库中图片存储的路径，并截取出图片名称
				//onePhoto.setPhotoName("照片名称");
				photoList.add(onePhoto);
			}
		}else{
			photoList = null;
		}
		return photoList;
	}
	

	@Override
	public PnrAndroidPhotoFile getOnePnrAndroidPhotoFileById(String id) {
		StringBuilder sql = new StringBuilder("select * from pnr_android_photo p where p.id='").append(id).append("'");
		List<Map> list = this.getJdbcTemplate().queryForList(sql.toString());
		PnrAndroidPhotoFile pnrAndroidPhotoFile = new PnrAndroidPhotoFile();
		if(list!=null && list.size()>0){
			pnrAndroidPhotoFile.setId(list.get(0).get("ID")==null?null:list.get(0).get("ID").toString());
			pnrAndroidPhotoFile.setPicture_id(list.get(0).get("PICTURE_ID")==null?null:list.get(0).get("PICTURE_ID").toString());
			pnrAndroidPhotoFile.setCreateTime(list.get(0).get("CREATETIME")==null?null:list.get(0).get("CREATETIME").toString());
			pnrAndroidPhotoFile.setFileData(list.get(0).get("FILEDATA")==null?null:(Clob)list.get(0).get("FILEDATA"));
			pnrAndroidPhotoFile.setFaultLocation(list.get(0).get("FAULTLOCATION")==null?null:list.get(0).get("FAULTLOCATION").toString());
			pnrAndroidPhotoFile.setFaultDescription(list.get(0).get("FAULTDESCRIPTION")==null?null:list.get(0).get("FAULTDESCRIPTION").toString());
			pnrAndroidPhotoFile.setLatitude(list.get(0).get("LATITUDE")==null?null:list.get(0).get("LATITUDE").toString());
			pnrAndroidPhotoFile.setLongitude(list.get(0).get("LONGITUDE")==null?null:list.get(0).get("LONGITUDE").toString());
			pnrAndroidPhotoFile.setCity(list.get(0).get("CITY")==null?null:list.get(0).get("CITY").toString());
			pnrAndroidPhotoFile.setDeptId(list.get(0).get("DEPT_ID")==null?null:list.get(0).get("DEPT_ID").toString());
			pnrAndroidPhotoFile.setUserId(list.get(0).get("USER_ID")==null?null:list.get(0).get("USER_ID").toString());
		}else{
			pnrAndroidPhotoFile = null;
		}
		return pnrAndroidPhotoFile;
	}

	@Override
	public String getPhotoPlth(String id) {
		StringBuilder sql = new StringBuilder(
				"select p.filedata from pnr_android_photo p where p.id='")
				.append(id).append("'");
		List<Map> list = this.getJdbcTemplate().queryForList(sql.toString());
		String plth = "";
		if (list != null && list.size() > 0) {
			plth = list.get(0).get("filedata") == null ? null : list.get(0)
					.get("filedata").toString();
		}
		return plth;
	}
	
	/**
	 * 已归档工单明细
	 * @param processDefinitionKey
	 * @param userId
	 * @param sendStartTime
	 * @param sendEndTime
	 * @param wsNum
	 * @param wsTitle
	 * @param status
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getArchivedList(String processDefinitionKey,
			String userId, String sendStartTime, String sendEndTime,
			String wsNum, String wsTitle, String status,String themeInterface, int firstResult,
			int endResult, int pageSize){
		String sql = "select temp2.* from (select temp1.*, rownum num from (";		
		String selectSql = "select ACTI.PROC_INST_ID_ as processInstanceId,MAIN.THEME AS theme,MAIN.city ,MAIN.country,MAIN.work_type,MAIN.workorder_type_name,MAIN.sub_type_name ,MAIN.key_word ,MAIN.work_order_degree,MAIN.project_amount,MAIN.INITIATOR AS initiator,MAIN.submit_application_time as SubmitTime,decode(ACTI.Delete_Reason_, 'delete', '已删除', '已归档') as statusName,ACTI.Delete_Reason_,MAIN.TASK_ASSIGNEE from (select RES.*,decode(RES.Delete_Reason_,'delete','delete','archived') as tempStatusName from ACT_HI_PROCINST RES inner join ACT_RE_PROCDEF DEF on RES.PROC_DEF_ID_ = DEF.ID_ left join (select TAS.* from ACT_HI_TASKINST TAS where TAS.id_ in (select max(TAS.id_) from ACT_HI_TASKINST TAS group by TAS.proc_inst_id_)) TAS on RES.PROC_INST_ID_ = TAS.PROC_INST_ID_ WHERE RES.PROC_DEF_ID_ like '"+processDefinitionKey+":%:%' and RES.END_TIME_ is not NULL and (exists (select LINK.USER_ID_ from ACT_HI_IDENTITYLINK LINK where USER_ID_ = '"+userId+"' and LINK.PROC_INST_ID_ = RES.ID_))) ACTI,PNR_ACT_TRANSFER_OFFICE_MAIN MAIN where ACTI.Proc_Inst_Id_ = MAIN.PROCESS_INSTANCE_ID(+)  and MAIN.THEMEINTERFACE = '"+themeInterface+"'";
		String whereSql = "";
		if (sendStartTime != null && !sendStartTime.equals("")) {
			whereSql += " and MAIN.Send_Time >=to_date('" + sendStartTime
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}
		if (sendEndTime != null && !sendEndTime.equals("")) {
			whereSql += " and MAIN.Send_Time <= to_date('" + sendEndTime
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}
		if (wsNum != null && !wsNum.equals("")) {
			whereSql += " and ACTI.PROC_INST_ID_ ='" + wsNum.trim() + "'";
		}
		if (wsTitle != null && !wsTitle.equals("")) {
			whereSql += " and MAIN.THEME like '%" + wsTitle.trim() + "%'";
		}
		if (status != null && !status.equals("")) {
			whereSql += " and tempStatusName = '"+status+"'";

		}
		sql += selectSql + whereSql
				+ " order by MAIN.Send_Time desc) temp1 where rownum <="
				+ endResult * pageSize + ") temp2 where temp2.num > "
				+ firstResult * pageSize;

		System.out.println("--------本地网或干线预检预修 已归档工单明细sql="+sql);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		for (Map map : list) {
			TaskModel model = new TaskModel();
			if(map.get("processInstanceId")!=null&&!"".equals(map.get("processInstanceId"))){
				model.setProcessInstanceId(map.get("processInstanceId").toString());
			}
			if(map.get("theme")!=null&&!"".equals(map.get("theme"))){
				model.setTheme(map.get("theme").toString());
			}
			if(map.get("initiator")!=null&&!"".equals(map.get("initiator"))){
				model.setInitiator(map.get("initiator").toString());
			}
			if(map.get("DEPTID")!=null&&!"".equals(map.get("DEPTID"))){
				model.setDeptId(map.get("DEPTID").toString());
			}
			if (map.get("SubmitTime") != null) {
				if (!"".equals(map.get("SubmitTime"))) {
					try {
						model.setApplicationTime(format.parse(map.get("SubmitTime")
								.toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

			}
			if(map.get("statusName")!=null&&!"".equals(map.get("statusName"))){
				model.setStatusName(map.get("statusName").toString());
			}
            if(map.get("city")!=null && !"".equals(map.get("city"))){
				
				model.setCity(map.get("city").toString());
			}
			if(map.get("country")!=null && !"".equals(map.get("country"))){
				model.setCountry(map.get("country").toString());
			}
			if(map.get("work_type")!=null && !"".equals(map.get("work_type"))){
				
				model.setWorkType(map.get("work_type").toString());
			}
			if(map.get("workorder_type_name")!=null && !"".equals(map.get("workorder_type_name"))){
				model.setWorkorderTypeName(map.get("workorder_type_name").toString());
			}
			
			if(map.get("sub_type_name")!=null && !"".equals(map.get("sub_type_name"))){
				model.setSubTypeName(map.get("sub_type_name").toString());
			}
			
			if(map.get("key_word")!=null && !"".equals(map.get("key_word"))){
				model.setKeyWord(map.get("key_word").toString());
			}
			if(map.get("work_order_degree")!=null && !"".equals(map.get("work_order_degree"))){
				model.setWorkOrderDegree(map.get("key_word").toString());
			}
			
			if(map.get("project_amount")!=null && !"".equals(map.get("project_amount"))){				
				model.setProjectAmount(Double.parseDouble(map.get("project_amount").toString()));
			}
		
			r.add(model);

		}	
		
		return r;
		
	}

	/**
	 * 已归档工单条数
	 * @param processDefinitionKey
	 * @param userId
	 * @param sendStartTime
	 * @param sendEndTime
	 * @param wsNum
	 * @param wsTitle
	 * @param status
	 * @return
	 */ 
	public int getArchivedCount(String processDefinitionKey, String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status,String themeInterface){
		String sql="";
		String selectSql = "select count(ACTI.PROC_INST_ID_ ) as total from (select RES.*,decode(RES.Delete_Reason_,'delete','delete','archived') as tempStatusName from ACT_HI_PROCINST RES inner join ACT_RE_PROCDEF DEF on RES.PROC_DEF_ID_ = DEF.ID_ left join (select TAS.* from ACT_HI_TASKINST TAS where TAS.id_ in (select max(TAS.id_) from ACT_HI_TASKINST TAS group by TAS.proc_inst_id_)) TAS on RES.PROC_INST_ID_ = TAS.PROC_INST_ID_ WHERE RES.PROC_DEF_ID_ like '"+processDefinitionKey+":%:%' and RES.END_TIME_ is not NULL and (exists (select LINK.USER_ID_ from ACT_HI_IDENTITYLINK LINK where USER_ID_ = '"+userId+"' and LINK.PROC_INST_ID_ = RES.ID_))) ACTI,PNR_ACT_TRANSFER_OFFICE_MAIN MAIN where ACTI.Proc_Inst_Id_ = MAIN.PROCESS_INSTANCE_ID(+) and MAIN.THEMEINTERFACE = '"+themeInterface+"'";
		String whereSql = "";
		if (sendStartTime != null && !sendStartTime.equals("")) {
			whereSql += " and MAIN.Send_Time >=to_date('" + sendStartTime
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}
		if (sendEndTime != null && !sendEndTime.equals("")) {
			whereSql += " and MAIN.Send_Time <= to_date('" + sendEndTime
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}
		if (wsNum != null && !wsNum.equals("")) {
			whereSql += " and ACTI.PROC_INST_ID_ ='" + wsNum.trim() + "'";
		}
		if (wsTitle != null && !wsTitle.equals("")) {
			whereSql += " and MAIN.THEME like '%" + wsTitle.trim() + "%'";
		}
		if (status != null && !status.equals("")) {
			whereSql += " and tempStatusName = '"+status+"'";
		}
		sql += selectSql + whereSql;
		
		System.out.println("--------本地网或干线预检预修 已归档工单条数sql="+sql);
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		return Integer.parseInt(list.get(0).get("total").toString());
	}
	
	@Override
	public void setStateByphotoId(String photoId, String state) {
		StringBuilder sql = new StringBuilder("update pnr_android_photo p set p.state=");
		sql.append(state);
		sql.append(" where p.id='");
		sql.append(photoId).append("'");
		
		this.getJdbcTemplate().update(sql.toString());
		
	}
	
	@Override
	public String[] getPhotoByProcessInstanceId(
			String processInstanceId) {
		StringBuilder sql = new StringBuilder("select po.*,pu.name from pnr_act_precheck_photo_ship p join");
		sql.append(" pnr_android_photo po on p.photo_id = po.id");
		sql.append(" left join pnr_user pu  on pu.user_id=po.user_id");
		sql.append(" where p.processinstance_id='").append(processInstanceId).append("'");
		System.out.println("------------------通过流程ID查询所对应的照片sql="+sql);
		List<Map> list = this.getJdbcTemplate().queryForList(sql.toString());
		String[] str = new String[2];
		String newTr ="";
		String photoIds = "";
		if(list!=null && list.size()>0){
			for(Map map:list){
				String photoId = map.get("ID").toString();
				photoIds+=photoId+",";
				String time = map.get("CREATETIME")==null?"":map.get("CREATETIME").toString();
				String longitude = map.get("LONGITUDE")==null?"无":map.get("LONGITUDE").toString();
				String latitude = map.get("LATITUDE")==null?"无":map.get("LATITUDE").toString();
				String userId = map.get("NAME")==null?"":map.get("NAME").toString();
				newTr += "<tr><td>拍照时间：</td><td>"+time+"</td><td>拍照人：</td><td>"+userId+"</td><td>经度：</td><td>"+longitude+"</td><td>纬度：</td><td>"+latitude+"</td></tr>";
			}
			str[0] = photoIds;
			str[1] = newTr;
		}
		
		return str;
	}
	
	/**
	 * 根据机房ID删除机房
	 * 
	 * @param id
	 */
	public String deleteRoomResourceById(String id) {
		String resultStr = "success";
		// 用机房ID在巡检资源表中查询出资源管理表的表名和表中的id
		String sql = "select sub_res_table,sub_res_id from pnr_res_config  where id = '"
				+ id + "'";
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		String subResTable = "";// 资源管理表名
		String subResId = "";
		if (list != null && list.size() > 0) {
			subResTable = list.get(0).get("sub_res_table") == null ? "" : list
					.get(0).get("sub_res_table").toString();
			subResId = list.get(0).get("sub_res_id") == null ? "" : list.get(0)
					.get("sub_res_id").toString();
		}
		if (!subResTable.equals("") && !subResId.equals("")) {
			try {
				// 1.将巡检资源表中的机房数据备份
				String inspectionResourcesBakSql = "insert into pnr_res_config_bak select * from pnr_res_config  where id = '"
						+ id + "' ";
				this.getJdbcTemplate().execute(inspectionResourcesBakSql);

				// 2.将资源管理表中的机房数据备份
				if (subResTable.equals("pnr_bsbt_resource")
						|| subResTable.equals("pnr_access_network_room")) {
					String resourceManagementBakSql = "insert into "
							+ subResTable + "_bak select * from " + subResTable
							+ "  where id = '" + subResId + "' ";
					this.getJdbcTemplate().execute(resourceManagementBakSql);
				}

				// 3.更新巡检任务表中的时间字段
				String updateSql = "update pnr_inspect_plan_res set cycle_end_time = cycle_end_time-1 where res_cfg_id = '"
						+ id + "' and cycle_end_time > sysdate";
				this.getJdbcTemplate().update(updateSql);

				// 4.删除巡检资源表中的机房数据
				String delInspectionResourcesSql = "delete from pnr_res_config where id ='"
						+ id + "'";
				this.getJdbcTemplate().execute(delInspectionResourcesSql,
						new PreparedStatementCallback() {
							public Object doInPreparedStatement(
									PreparedStatement ps) throws SQLException,
									DataAccessException {
								ps.addBatch();
								return ps.executeBatch();
							}
						});

				// 5.删除资源管理表中的机房数据
				if (subResTable.equals("pnr_bsbt_resource")
						|| subResTable.equals("pnr_access_network_room")) {
					String resourceManagementResourcesSql = "delete from "
							+ subResTable + " where id ='" + subResId + "'";
					this.getJdbcTemplate().execute(
							resourceManagementResourcesSql,
							new PreparedStatementCallback() {
								public Object doInPreparedStatement(
										PreparedStatement ps)
										throws SQLException,
										DataAccessException {
									ps.addBatch();
									return ps.executeBatch();
								}
							});
				}
			} catch (Exception e) {
				resultStr = "删除机房数据异常。";
				e.printStackTrace();
			}

			// resultStr = "success";
		} else {
			resultStr = "机房不存在。";
		}
		return resultStr;
	}
	
	@Override
	public List<RoomDemolitionModel> getQueryWorkOrderList(String areaid, String flag,
			String processKey, ConditionQueryModel conditionQueryModel,
			int firstResult, int endResult, int pageSize) {

		String sql="select temp2.* from (select temp1.*, rownum num from ( ";
		String selectSql="		  select w.*									"+
						 "		  from (select m.*,								"+
						 "		               case								"+
						 "		                 when m.state = '3' then		"+
						 "		                  'waitOrder'					"+
						 "		                 when s.id is not null then		"+
						 "		                  'spotChecks'					"+
						 "		                 else							"+
						 "		                  to_char(t.task_def_key_)		"+
						 "		               end as TaskDefKey,				"+
						 "		               case								"+
						 "		                 when m.state = '3' then		"+
						 "		                  '待办'							"+
						 "		                 when s.id is not null then		"+
						 "		                  '已验收'						"+
						 "		                 else							"+
					     "			             to_char(t.name_)				"+
						 "		               end as StatusName				"+
						 "		          from pnr_act_roomdemolition m,		"+
						 "		               (select RES.*			"+
						 "		                  from ACT_RU_TASK RES			"+
						 "		                 inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_	"+
						 "		                 WHERE D.KEY_ = 'roomDemolition') t, 						"+
						 "		               PNR_ROOMDEMOLITION_SPOTCHECK s								"+
						 "		         where m.process_instance_id = t.proc_inst_id_						"+
						 "		           and m.process_instance_id = s.process_instance_id(+)				"+
						 "		           and m.state != '1'												"+
						 "		           and m.state != '5'												"+
						 "		        union	all															"+
						 "		        select m.*, 'archive' as TaskDefKey, '已归档' as StatusName			"+
						 "		          from pnr_act_roomdemolition m										"+
						 "		         where m.state = '5'												"+
						 "		        union	all															"+
						 "		        select m.*, 'delete' as TaskDefKey, '已删除' as StatusName			"+
						 "		          from pnr_act_roomdemolition m										"+
						 "		         where m.state = '1') w												"+
						 "		 where w.city is not null 													"+
						 "		   and w.country is not null "+
						 "		   and w.state <> 2 ";	
		
		ArrayList whereList=new ArrayList();
		
		String whereSql=" ";
		
		if(areaid !=null && !"".equals(areaid)){
			if(areaid.trim().length()==2){
				
			}else if(areaid.trim().length()==4){
				whereSql+= " and w.city = ?";
				whereList.add(areaid);
			}else if(areaid.trim().length()==6){
				whereSql+= " and w.country =?";
				whereList.add(areaid);
			}
		}
		if (conditionQueryModel.getSendStartTime() != null && !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and to_char(w.send_time,'yyyy-mm-dd hh24:mi:ss') >=? ";
			whereList.add(conditionQueryModel.getSendStartTime());
		}
		
		if (conditionQueryModel.getSendEndTime() != null && !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and to_char(w.send_time,'yyyy-mm-dd hh24:mi:ss') <=? ";
			whereList.add(conditionQueryModel.getSendEndTime());
		}
		if (conditionQueryModel.getWorkNumber() != null && !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and w.process_instance_id =?";
			whereList.add(conditionQueryModel.getWorkNumber());
		}
		if (conditionQueryModel.getTheme() != null && !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and instr(w.theme,?)>0 ";
			whereList.add(conditionQueryModel.getTheme().trim());
		}
		if (conditionQueryModel.getStatus() != null && !conditionQueryModel.getStatus().equals("")) {
			whereSql += " and w.TaskDefKey = ?";
			whereList.add(conditionQueryModel.getStatus());

		}
		if (conditionQueryModel.getCity() != null && !conditionQueryModel.getCity().equals("")) {
			whereSql += " and w.city = ?";
			whereList.add(conditionQueryModel.getCity());
		}
		if (conditionQueryModel.getCountry() != null && !conditionQueryModel.getCountry().equals("")) {
			whereSql += " and w.country = ?";
			whereList.add(conditionQueryModel.getCountry());
		}
		if (conditionQueryModel.getRoomPici() != null && !conditionQueryModel.getRoomPici().equals("")) {
			whereSql += " and w.room_pici = ?";
			whereList.add(conditionQueryModel.getRoomPici());
		}
//		if (conditionQueryModel.getLineType() != null && !conditionQueryModel.getLineType().equals("")) {
//			whereSql += " and m.work_type = '"+conditionQueryModel.getLineType()+"'";
//			
//		}
//		if (conditionQueryModel.getWorkOrderType() != null && !conditionQueryModel.getWorkOrderType().equals("")) {
//			whereSql += " and m.workorder_type_name = '"+conditionQueryModel.getWorkOrderType()+"'";
//			
//		}
//		if (conditionQueryModel.getPrecheckFlag() != null && !conditionQueryModel.getPrecheckFlag().equals("")) {
//			whereSql += " and m.precheck_flag = '"+conditionQueryModel.getPrecheckFlag()+"'";
//			
//		}
		
		sql += selectSql + whereSql
		+ " order by w.SEND_TIME )temp1 where rownum <= ?) temp2 where temp2.num >?";
		
		whereList.add(endResult * pageSize);
		whereList.add(firstResult * pageSize);
		
		Object values[]=whereList.toArray();
		System.out.println("------------------机房拆除待回复列表："+sql);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<RoomDemolitionModel> r = new ArrayList<RoomDemolitionModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,values);
		for (Map map : list) {
			RoomDemolitionModel model = new RoomDemolitionModel();
			//ID
			if(map.get("id")!=null && !"".equals(map.get("id"))){
				
				model.setId(map.get("id").toString());
			}
			
			//ID
			if(map.get("taskDefKey")!=null && !"".equals(map.get("taskDefKey"))){
				
				model.setTaskDefKey(map.get("taskDefKey").toString());
			}
			
			//任务ID
			if(map.get("TaskId")!=null && !"".equals(map.get("TaskId"))){
				
				model.setTaskId(map.get("TaskId").toString());
			}
			//流程实例号
			if(map.get("process_instance_id")!=null && !"".equals(map.get("process_instance_id"))){
				
				model.setProcessInstanceId(map.get("process_instance_id").toString());
			}
			//工单编码
			if(map.get("sheet_id")!=null && !"".equals(map.get("sheet_id"))){
				
				model.setSheetId(map.get("sheet_id").toString());
			}
			//工单名称
			if(map.get("theme")!=null && !"".equals(map.get("theme"))){
				
				model.setTheme(map.get("theme").toString());
			}
			//地市
			if(map.get("city")!=null && !"".equals(map.get("city"))){
				
				model.setCity(map.get("city").toString());
			}
			//区县
			if(map.get("country")!=null && !"".equals(map.get("country"))){
				
				model.setCountry(map.get("country").toString());
			}
			//工单状态
			if (map.get("statusName") != null && !"".equals(map.get("statusName"))) {
				
				model.setStatusName(map.get("statusName").toString());
			}
			//机房类型
			if (map.get("station_type") != null && !"".equals(map.get("station_type"))) {
				
				model.setStationType(map.get("station_type").toString());
			}
			//机房类型
			if (map.get("station_name") != null && !"".equals(map.get("station_name"))) {
				
				model.setStationName(map.get("station_name").toString());
			}
			//机房级别
			if (map.get("station_level") != null && !"".equals(map.get("station_level"))) {
				
				model.setStationLevel(map.get("station_level").toString());
			}
			//机房面积
			if (map.get("station_area") != null && !"".equals(map.get("station_area"))) {
				
				model.setStationArea(map.get("station_area").toString());
			}
			//机房产权
			if (map.get("station_equity") != null && !"".equals(map.get("station_equity"))) {
				
				model.setStationEquity(map.get("station_equity").toString());
			}
			//年租金
			if (map.get("annual_rent") != null && !"".equals(map.get("annual_rent"))) {
				
				model.setAnnualRent(map.get("annual_rent").toString());
			}
			//租用日期
			if (map.get("hire_date") != null) {
				if (!"".equals(map.get("hire_date"))) {
					try {
						model.setHireDate(format.parse(map.get("hire_date")
								.toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

			}
			//合同到期时间
			if (map.get("contract_time") != null) {
				if (!"".equals(map.get("contract_time"))) {
					try {
						model.setContractTime(format.parse(map.get("contract_time")
								.toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				
			}
			//光缆纤芯数
			if (map.get("opticcable_num") != null && !"".equals(map.get("opticcable_num"))) {
				
				model.setOpticcableNum(map.get("opticcable_num").toString());
			}
			//光缆改造方式
			if (map.get("opticcable_way") != null && !"".equals(map.get("opticcable_way"))) {
				
				model.setOpticcableWay(map.get("opticcable_way").toString());
			}
			//设备机架数
			if (map.get("equipment_rack_num") != null && !"".equals(map.get("equipment_rack_num"))) {
				
				model.setEquipmentRackNum(map.get("equipment_rack_num").toString());
			}
			//材料费用
			if (map.get("material_cost") != null && !"".equals(map.get("material_cost"))) {
				
				model.setMaterialCost(map.get("material_cost").toString());
			}
			//对应能耗系统机房名称
			if (map.get("energy_station_name") != null && !"".equals(map.get("energy_station_name"))) {
				
				model.setEnergyStationName(map.get("energy_station_name").toString());
			}
			//对应能耗系统机房编号
			if (map.get("energy_station_code") != null && !"".equals(map.get("energy_station_code"))) {
				
				model.setEnergyStationCode(map.get("energy_station_code").toString());
			}
			//批次
			if (map.get("ROOM_PICI") != null && !"".equals(map.get("ROOM_PICI"))) {
				
				model.setRoomPici(map.get("ROOM_PICI").toString());
			}
			//发起时间
			if (map.get("send_time") != null) {
				if (!"".equals(map.get("send_time"))) {
					try {
						model.setSendTime(format.parse(map.get("send_time")
								.toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				
			}
			
			//驳回标识
			if (map.get("rollback_flag") != null && !"".equals(map.get("rollback_flag"))) {
				
				model.setRollbackFlag(map.get("rollback_flag").toString());
			}
			
			r.add(model);
		}
		return r;
	}
	
	@Override
	public int getQueryWorkOrderCount(String areaid, String flag,String processKey,
			ConditionQueryModel conditionQueryModel) {
		String sql="";
		String selectSql="		  select count(*) total									"+
						 "		  from (select m.*,								"+
						 "		               case								"+
						 "		                 when m.state = '3' then		"+
						 "		                  'waitOrder'					"+
						 "		                 when s.id is not null then		"+
						 "		                  'spotChecks'					"+
						 "		                 else							"+
						 "		                  to_char(t.task_def_key_)		"+
						 "		               end as TaskDefKey,				"+
						 "		               case								"+
						 "		                 when m.state = '3' then		"+
						 "		                  '待办'							"+
						 "		                 when s.id is not null then		"+
						 "		                  '已验收'						"+
						 "		                 else							"+
					     "			             to_char(t.name_)				"+
						 "		               end as StatusName				"+
						 "		          from pnr_act_roomdemolition m,		"+
						 "		               (select RES.*			"+
						 "		                  from ACT_RU_TASK RES			"+
						 "		                 inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_	"+
						 "		                 WHERE D.KEY_ = 'roomDemolition') t, 						"+
						 "		               PNR_ROOMDEMOLITION_SPOTCHECK s								"+
						 "		         where m.process_instance_id = t.proc_inst_id_						"+
						 "		           and m.process_instance_id = s.process_instance_id(+)				"+
						 "		           and m.state != '1'												"+
						 "		           and m.state != '5'												"+
						 "		        union	all															"+
						 "		        select m.*, 'archive' as TaskDefKey, '已归档' as StatusName			"+
						 "		          from pnr_act_roomdemolition m										"+
						 "		         where m.state = '5'												"+
						 "		        union	all															"+
						 "		        select m.*, 'delete' as TaskDefKey, '已删除' as StatusName			"+
						 "		          from pnr_act_roomdemolition m										"+
						 "		         where m.state = '1') w												"+
						 "		 where w.city is not null 													"+
						 "		   and w.country is not null "+
						 "		   and w.state <> 2 ";
ArrayList whereList=new ArrayList();
		
		String whereSql=" ";
		
		if(areaid !=null && !"".equals(areaid)){
			if(areaid.trim().length()==2){
				
			}else if(areaid.trim().length()==4){
				whereSql+= " and w.city = ?";
				whereList.add(areaid);
			}else if(areaid.trim().length()==6){
				whereSql+= " and w.country =?";
				whereList.add(areaid);
			}
		}
		if (conditionQueryModel.getSendStartTime() != null && !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and to_char(w.send_time,'yyyy-mm-dd hh24:mi:ss') >=? ";
			whereList.add(conditionQueryModel.getSendStartTime());
		}
		
		if (conditionQueryModel.getSendEndTime() != null && !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and to_char(w.send_time,'yyyy-mm-dd hh24:mi:ss') <=? ";
			whereList.add(conditionQueryModel.getSendEndTime());
		}
		if (conditionQueryModel.getWorkNumber() != null && !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and w.process_instance_id =?";
			whereList.add(conditionQueryModel.getWorkNumber());
		}
		if (conditionQueryModel.getTheme() != null && !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and instr(w.theme,?)>0 ";
			whereList.add(conditionQueryModel.getTheme().trim());
		}
		if (conditionQueryModel.getStatus() != null && !conditionQueryModel.getStatus().equals("")) {
			whereSql += " and w.TaskDefKey = ?";
			whereList.add(conditionQueryModel.getStatus());

		}
		if (conditionQueryModel.getCity() != null && !conditionQueryModel.getCity().equals("")) {
			whereSql += " and w.city = ?";
			whereList.add(conditionQueryModel.getCity());
		}
		if (conditionQueryModel.getCountry() != null && !conditionQueryModel.getCountry().equals("")) {
			whereSql += " and w.country = ?";
			whereList.add(conditionQueryModel.getCountry());
		}
		if (conditionQueryModel.getRoomPici() != null && !conditionQueryModel.getRoomPici().equals("")) {
			whereSql += " and w.room_pici = ?";
			whereList.add(conditionQueryModel.getRoomPici());
		}
//		if (conditionQueryModel.getLineType() != null && !conditionQueryModel.getLineType().equals("")) {
//			whereSql += " and m.work_type = '"+conditionQueryModel.getLineType()+"'";
//			
//		}
//		if (conditionQueryModel.getWorkOrderType() != null && !conditionQueryModel.getWorkOrderType().equals("")) {
//			whereSql += " and m.workorder_type_name = '"+conditionQueryModel.getWorkOrderType()+"'";
//			
//		}
//		if (conditionQueryModel.getPrecheckFlag() != null && !conditionQueryModel.getPrecheckFlag().equals("")) {
//			whereSql += " and m.precheck_flag = '"+conditionQueryModel.getPrecheckFlag()+"'";
//			
//		}
		
		sql += selectSql + whereSql
				+ " order by w.SEND_TIME ";
		
		Object args[]=whereList.toArray();
		
		System.out.println("--------------机房拆除工单查询列表总条数sql："+sql);


		int size = this.getJdbcTemplate().queryForInt(sql,args);
		return size;
	
	}

}
