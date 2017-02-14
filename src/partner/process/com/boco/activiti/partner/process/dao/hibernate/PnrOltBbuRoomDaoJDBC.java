package com.boco.activiti.partner.process.dao.hibernate;

import java.sql.Clob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.activiti.partner.process.dao.IPnrOltBbuRoomJDBCDao;
import com.boco.activiti.partner.process.model.PnrAndroidPhotoFile;
import com.boco.activiti.partner.process.model.PnrOltBbuRoom;
import com.boco.activiti.partner.process.model.RoomAssets;
import com.boco.activiti.partner.process.po.ConditionQueryDAMModel;
import com.boco.activiti.partner.process.po.ConditionQueryModel;
import com.boco.activiti.partner.process.po.DownAttachMentModel;
import com.boco.activiti.partner.process.po.DownWorkOrderModel;
import com.boco.activiti.partner.process.po.PnrOltBbuOfficeMainModel;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.eoms.base.util.StaticMethod;

public class PnrOltBbuRoomDaoJDBC extends JdbcDaoSupport implements
		IPnrOltBbuRoomJDBCDao {

	@SuppressWarnings("unchecked")
	@Override
	public int getOltBbuCount(String userid, String flag,
			String processKey, ConditionQueryModel conditionQueryModel) {
		String themeinterface =com.boco.eoms.partner.process.util.CommonUtils.getTaskThemeinterfaceStr(processKey,"m");

		ArrayList list1 = new ArrayList();
		String sql = "";
		String selectSql ="select count(m.id) as total" +
						  "  from pnr_act_transfer_office_main m," + 
						  "       pnr_olt_bbu_room room," + 
						  "       pnr_olt_bbu_office_relation re" + 
						  " where m.process_instance_id = re.process_instance_id" + 
						  "   and re.room_id = room.id" + 
						  "   and m.themeinterface = '"+themeinterface+"'" + 
						  "   and m.assignee = ?";
//		String selectSql ="select  count(*) as total  from (select RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_ " +
//		" WHERE RES.ASSIGNEE_ = ?"+
//		" and D.KEY_ = ? "+
//		" and RES.Suspension_State_ = 1) t,pnr_act_transfer_office_main m,(select   room.olt_number,room.bbu_number,room.total_user_number,room.voice_user,room.broadband_user,room.iptv_user,re.is_need_rod_investment,re.is_need_cable_investment,re.new_built_rod_length,re.new_built_rod_investment,re.original_cable_route,re.new_cable_route,re.cable_cloth_core_number,re.cable_cloth_length,re.cable_investment,re.line_total_investment,re.board_demand,re.light_module_demand,re.equipment_investment,re.process_instance_id from  pnr_act_transfer_office_main main,pnr_olt_bbu_room room,pnr_olt_bbu_office_relation re "+
//		" where main.process_instance_id=re.process_instance_id and   re.room_id=room.id) r where t.proc_inst_id_ = m.process_instance_id and m.process_instance_id=r.process_instance_id and m.themeinterface='"+themeinterface+"' ";
		list1.add(userid);
		//list1.add(processKey);
		
		
		String whereSql = "";
		if (flag != null && "backlog".equals(flag.trim())) {
			whereSql = " and (m.state!=8 and m.state!=3)";
		} else if ("wait".equals(flag.trim())) {
			whereSql = " and m.state=3";
		}
		if (conditionQueryModel.getSendStartTime() != null
				&& !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >= ?";
			list1.add(conditionQueryModel.getSendStartTime());
		}
		if (conditionQueryModel.getSendEndTime() != null
				&& !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?";
			list1.add(conditionQueryModel.getSendEndTime());
		}
		if (conditionQueryModel.getWorkNumber() != null
				&& !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and m.process_instance_id = ?";
			list1.add(conditionQueryModel.getWorkNumber().trim());
		}
		if (conditionQueryModel.getTheme() != null
				&& !conditionQueryModel.getTheme().equals("")) {
			whereSql += "  and instr(m.theme,?) > 0 ";
			list1.add(conditionQueryModel.getTheme().trim());	
		}
		if (conditionQueryModel.getStatus() != null
				&& !conditionQueryModel.getStatus().equals("")) {
			whereSql += " and m.task_def_key = ?";
			list1.add(conditionQueryModel.getStatus());
		}
//		if (conditionQueryModel.getStatus() != null
//				&& !conditionQueryModel.getStatus().equals("")) {
//			whereSql += " and t.task_def_key_ = ?";
//			list1.add(conditionQueryModel.getStatus());
//		}
		if (conditionQueryModel.getCity() != null
				&& !conditionQueryModel.getCity().equals("")) {
			whereSql += " and m.city = ?";
			list1.add(conditionQueryModel.getCity());
		}
		if (conditionQueryModel.getCountry() != null
				&& !conditionQueryModel.getCountry().equals("")) {
			whereSql += " and m.country = ?";
			list1.add(conditionQueryModel.getCountry());

		}
		if (conditionQueryModel.getLineType() != null
				&& !conditionQueryModel.getLineType().equals("")) {
			whereSql += " and m.work_type = ?";
			list1.add(conditionQueryModel.getLineType());
		}
		if (conditionQueryModel.getWorkOrderType() != null
				&& !conditionQueryModel.getWorkOrderType().equals("")) {
			whereSql += " and m.workorder_type_name = ?";
			list1.add(conditionQueryModel.getWorkOrderType());
		}
		if (conditionQueryModel.getMainSceneId() != null
				&& !conditionQueryModel.getMainSceneId().equals("")) {
			whereSql += " and m.main_scene_id = ?";
			list1.add(conditionQueryModel.getMainSceneId());
		}
		if (conditionQueryModel.getJobOrderType() != null
				&& !conditionQueryModel.getJobOrderType().equals("")) {
			whereSql += " and m.job_order_type = ?";
			list1.add(conditionQueryModel.getJobOrderType());
		}
		if (conditionQueryModel.getBatch() != null
				&& !conditionQueryModel.getBatch().equals("")) {
			whereSql += " and m.batch = ?";
			list1.add(conditionQueryModel.getBatch());
		}
		sql = selectSql + whereSql;

		System.out.println("--------------OLT-BBU机房优化申请回复列表查询统计sql=" + sql);
		Object[] args = list1.toArray();
		int total = this.getJdbcTemplate().queryForInt(sql, args);
		return total;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PnrOltBbuOfficeMainModel> getOltBbuList(String userid,
			String flag, String processKey,
			ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize) {
		
		String themeinterface =com.boco.eoms.partner.process.util.CommonUtils.getTaskThemeinterfaceStr(processKey,"m");

		ArrayList list1 = new ArrayList();
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		//String sql = "";
		//String selectSql = "select  t.id_ as TaskId,t.task_def_key_ as taskDefKey,m.city,m.country,decode(m.state, 6, '专家会审' ，t.name_) as statusName,m.workorder_type_name,m.sub_type_name,m.initiator as Initiator,m.one_initiator as OneInitiator,m.process_instance_id as ProcessInstanceId,m.send_time as SendTime,m.theme as Theme,m.job_order_type,m.batch,m.rollback_flag,nvl(r.olt_number,'0') as olt_number,nvl(r.bbu_number,'0') as bbu_number,nvl(r.total_user_number,'0') as total_user_number,nvl(r.voice_user,'0') as voice_user,nvl(r.broadband_user,'0') as broadband_user,nvl(r.iptv_user,'0') as iptv_user,r.is_need_rod_investment,r.is_need_cable_investment,r.new_built_rod_length,r.new_built_rod_investment,"+
		 
		String selectSql =  "select m.task_id as TaskId," +
							"       m.task_def_key as taskDefKey," + 
							"       m.rollback_flag," + 
							"       m.city," + 
							"       m.country," + 
							"       decode(m.state, 6, '专家会审', m.task_def_key_name) as statusName," + 
							"       m.workorder_type_name," + 
							"       m.sub_type_name," + 
							"       m.initiator as Initiator," + 
							"       m.one_initiator as OneInitiator," + 
							"       m.process_instance_id as ProcessInstanceId," + 
							"       m.send_time as SendTime," + 
							"       m.theme as Theme," + 
							"       m.job_order_type," + 
							"       m.batch," + 
							"       nvl(room.olt_number, '0') as olt_number," + 
							"       nvl(room.bbu_number, '0') as bbu_number," + 
							"       nvl(room.total_user_number, '0') as total_user_number," + 
							"       nvl(room.voice_user, '0') as voice_user," + 
							"       nvl(room.broadband_user, '0') as broadband_user," + 
							"       nvl(room.iptv_user, '0') as iptv_user," + 
							"       re.is_need_rod_investment," + 
							"       re.is_need_cable_investment," + 
							"       re.new_built_rod_length," + 
							"       re.new_built_rod_investment," + 
							"       re.original_cable_route," + 
							"       re.new_cable_route," + 
							"       re.cable_cloth_core_number," + 
							"       re.cable_cloth_length," + 
							"       re.cable_investment," + 
							"       re.line_total_investment," + 
							"       re.board_demand," + 
							"       re.light_module_demand," + 
							"       re.equipment_investment," + 
							"       re.new_paragraph," + 
							"       decode(m.sheet_id, null, '无', m.sheet_id) as sheetId" + 
							"  from pnr_act_transfer_office_main m," + 
							"       pnr_olt_bbu_room             room," + 
							"       pnr_olt_bbu_office_relation  re" + 
							" where m.process_instance_id = re.process_instance_id" + 
							"   and re.room_id = room.id" + 
							"   and m.themeinterface = '"+themeinterface+"'" + 
							"   and m.assignee = ?";

//		String selectSql = "select  t.id_ as TaskId,t.task_def_key_ as taskDefKey,m.rollback_flag,m.city,m.country,decode(m.state, 6, '专家会审' ，t.name_) as statusName,m.workorder_type_name,m.sub_type_name,m.initiator as Initiator,m.one_initiator as OneInitiator,m.process_instance_id as ProcessInstanceId,m.send_time as SendTime,m.theme as Theme,m.job_order_type,m.batch,nvl(r.olt_number,'0') as olt_number,nvl(r.bbu_number,'0') as bbu_number,nvl(r.total_user_number,'0') as total_user_number,nvl(r.voice_user,'0') as voice_user,nvl(r.broadband_user,'0') as broadband_user,nvl(r.iptv_user,'0') as iptv_user,r.is_need_rod_investment,r.is_need_cable_investment,r.new_built_rod_length,r.new_built_rod_investment,"+
//		" r.original_cable_route,r.new_cable_route,r.cable_cloth_core_number,r.cable_cloth_length, r.cable_investment,r.line_total_investment, r.board_demand, r.light_module_demand,r.equipment_investment,r.new_paragraph,decode(m.sheet_id, null, '无', m.sheet_id) as sheetId "+
//		" from (select RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_ " +
//		" WHERE RES.ASSIGNEE_ = ?"+
//		" and D.KEY_ = ? "+
//		" and RES.Suspension_State_ = 1) t,pnr_act_transfer_office_main m,(select   room.olt_number,room.bbu_number,room.total_user_number,room.voice_user,room.broadband_user,room.iptv_user,re.is_need_rod_investment,re.is_need_cable_investment,re.new_built_rod_length,re.new_built_rod_investment,re.original_cable_route,re.new_cable_route,re.cable_cloth_core_number,re.cable_cloth_length,re.cable_investment,re.line_total_investment,re.board_demand,re.light_module_demand,re.equipment_investment,re.new_paragraph,re.process_instance_id from  pnr_act_transfer_office_main main,pnr_olt_bbu_room room,pnr_olt_bbu_office_relation re "+
//		" where main.process_instance_id=re.process_instance_id and   re.room_id=room.id) r where t.proc_inst_id_ = m.process_instance_id and m.process_instance_id=r.process_instance_id and m.themeinterface='"+themeinterface+"' ";
		list1.add(userid);
		//list1.add(processKey);          
		String whereSql = "";

		if (flag != null && "backlog".equals(flag.trim())) {
			whereSql = " and (m.state!=8 and m.state!=3)";
		} else if ("wait".equals(flag.trim())) {
			whereSql = " and m.state=3";

		}
		if (conditionQueryModel.getSendStartTime() != null
				&& !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >= ?";
			list1.add(conditionQueryModel.getSendStartTime());
		}
		if (conditionQueryModel.getSendEndTime() != null
				&& !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?";
			list1.add(conditionQueryModel.getSendEndTime());
		}
		if (conditionQueryModel.getWorkNumber() != null
				&& !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and m.process_instance_id = ?";
			list1.add(conditionQueryModel.getWorkNumber().trim());
		}
		if (conditionQueryModel.getTheme() != null
				&& !conditionQueryModel.getTheme().equals("")) {
			whereSql += "  and instr(m.theme,?) > 0 ";
			list1.add(conditionQueryModel.getTheme().trim());	
		}
		if (conditionQueryModel.getStatus() != null
				&& !conditionQueryModel.getStatus().equals("")) {
			whereSql += " and m.task_def_key = ?";
			list1.add(conditionQueryModel.getStatus());
		}
//		if (conditionQueryModel.getStatus() != null
//				&& !conditionQueryModel.getStatus().equals("")) {
//			whereSql += " and t.task_def_key_ = ?";
//			list1.add(conditionQueryModel.getStatus());
//		}
		if (conditionQueryModel.getCity() != null
				&& !conditionQueryModel.getCity().equals("")) {
			whereSql += " and m.city = ?";
			list1.add(conditionQueryModel.getCity());
		}
		if (conditionQueryModel.getCountry() != null
				&& !conditionQueryModel.getCountry().equals("")) {
			whereSql += " and m.country = ?";
			list1.add(conditionQueryModel.getCountry());

		}
		if (conditionQueryModel.getLineType() != null
				&& !conditionQueryModel.getLineType().equals("")) {
			whereSql += " and m.work_type = ?";
			list1.add(conditionQueryModel.getLineType());
		}
		if (conditionQueryModel.getWorkOrderType() != null
				&& !conditionQueryModel.getWorkOrderType().equals("")) {
			whereSql += " and m.workorder_type_name = ?";
			list1.add(conditionQueryModel.getWorkOrderType());
		}
		if (conditionQueryModel.getMainSceneId() != null
				&& !conditionQueryModel.getMainSceneId().equals("")) {
			whereSql += " and m.main_scene_id = ?";
			list1.add(conditionQueryModel.getMainSceneId());
		}
		if (conditionQueryModel.getJobOrderType() != null
				&& !conditionQueryModel.getJobOrderType().equals("")) {
			whereSql += " and m.job_order_type = ?";
			list1.add(conditionQueryModel.getJobOrderType());
		}
		if (conditionQueryModel.getBatch() != null
				&& !conditionQueryModel.getBatch().equals("")) {
			whereSql += " and m.batch = ?";
			list1.add(conditionQueryModel.getBatch());
		}

		sql += selectSql + whereSql
		+ " order by m.send_time) temp1 where rownum <= ?) temp2 where temp2.num > ?";
		list1.add(endResult * pageSize);
		list1.add(firstResult * pageSize);
//		  sql += selectSql
//		  + whereSql
//		  + " order by m.send_time";
		System.out.println("oltbbu机房优化待回复明细sql=" + sql);
		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Object[] args = list1.toArray();
		List<PnrOltBbuOfficeMainModel> r = new ArrayList<PnrOltBbuOfficeMainModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql, args);
		for (Map map : list) {
			PnrOltBbuOfficeMainModel model = new PnrOltBbuOfficeMainModel();
			if (map.get("TaskId") != null && !"".equals(map.get("TaskId"))) {

				model.setTaskId(map.get("TaskId").toString());
			}
			if (map.get("ProcessInstanceId") != null && !"".equals(map.get("ProcessInstanceId"))) {

				model.setProcessInstanceId(map.get("ProcessInstanceId").toString());
			}
			if (map.get("Theme") != null && !"".equals(map.get("Theme"))) {

				model.setTheme(map.get("Theme").toString());
			}
			if (map.get("job_order_type") != null && !"".equals(map.get("job_order_type"))) {

				model.setJobOrderType(map.get("job_order_type").toString());
			}
			if (map.get("batch") != null && !"".equals(map.get("batch"))) {

				model.setBatch(map.get("batch").toString());
			}
			if (map.get("olt_number") != null && !"".equals(map.get("olt_number"))) {

				model.setOltNumber(map.get("olt_number").toString());
			}
			if (map.get("bbu_number") != null && !"".equals(map.get("bbu_number"))) {

				model.setBbuNumber(map.get("bbu_number").toString());
			}
			if (map.get("total_user_number") != null && !"".equals(map.get("total_user_number"))) {

				model.setTotalUserNumber(map.get("total_user_number").toString());
			}
			if (map.get("voice_user") != null && !"".equals(map.get("voice_user"))) {

				model.setVoiceUser(map.get("voice_user").toString());
			}
			if (map.get("broadband_user") != null && !"".equals(map.get("broadband_user"))) {

				model.setBroadbandUser(map.get("broadband_user").toString());
			}
			if (map.get("iptv_user") != null && !"".equals(map.get("iptv_user"))) {

				model.setIptvUser(map.get("iptv_user").toString());
			}
			if (map.get("is_need_rod_investment") != null && !"".equals(map.get("is_need_rod_investment"))) {

				model.setIsNeedRodInvestment(map.get("is_need_rod_investment").toString());
			}
			if (map.get("is_need_cable_investment") != null && !"".equals(map.get("is_need_cable_investment"))) {

				model.setIsNeedCableInvestment(map.get("is_need_cable_investment").toString());
			}
			if (map.get("new_built_rod_length") != null && !"".equals(map.get("new_built_rod_length"))) {

				model.setNewBuiltRodLength(map.get("new_built_rod_length").toString());
			}
			if (map.get("new_built_rod_investment") != null && !"".equals(map.get("new_built_rod_investment"))) {

				model.setNewBuiltRodInvestment(map.get("new_built_rod_investment").toString());
			}
			if (map.get("original_cable_route") != null && !"".equals(map.get("original_cable_route"))) {

				model.setOriginalCableRoute(map.get("original_cable_route").toString());
			}
			if (map.get("new_cable_route") != null && !"".equals(map.get("new_cable_route"))) {

				model.setNewCableRoute(map.get("new_cable_route").toString());
			}
			if (map.get("cable_cloth_core_number") != null && !"".equals(map.get("cable_cloth_core_number"))) {

				model.setCableClothCoreNumber(map.get("cable_cloth_core_number").toString());
			}
			if (map.get("cable_cloth_length") != null && !"".equals(map.get("cable_cloth_length"))) {

				model.setCableClothLength(map.get("cable_cloth_length").toString());
			}
			if (map.get("cable_investment") != null && !"".equals(map.get("cable_investment"))) {

				model.setCableInvestment(map.get("cable_investment").toString());
			}
			if (map.get("line_total_investment") != null && !"".equals(map.get("line_total_investment"))) {

				model.setLineTotalInvestment(map.get("line_total_investment").toString());
			}
			if (map.get("board_demand") != null && !"".equals(map.get("board_demand"))) {

				model.setBoardDemand(map.get("board_demand").toString());
			}
			if (map.get("light_module_demand") != null && !"".equals(map.get("light_module_demand"))) {

				model.setLightModuleDemand(map.get("light_module_demand").toString());
			}
			if (map.get("equipment_investment") != null && !"".equals(map.get("equipment_investment"))) {

				model.setEquipmentInvestment(map.get("equipment_investment").toString());
			}
			if (map.get("sheetId") != null && !"".equals(map.get("sheetId"))) {

				model.setSheetId(map.get("sheetId").toString());
			}
			if (map.get("city") != null && !"".equals(map.get("city"))) {

				model.setCity(map.get("city").toString());
			}
			if (map.get("country") != null && !"".equals(map.get("country"))) {

				model.setCountry(map.get("country").toString());
			}
			if (map.get("new_paragraph") != null && !"".equals(map.get("new_paragraph"))) {

				model.setNewParagraph(map.get("new_paragraph").toString());
			}
			if (map.get("taskDefKey") != null && !"".equals(map.get("taskDefKey"))) {

				model.setTaskDefKey(map.get("taskDefKey").toString());
			}
			if (map.get("workorder_type_name") != null && !"".equals(map.get("workorder_type_name"))) {

				model.setWorkOrderTypeName(map.get("workorder_type_name").toString());
			}
			if (map.get("sub_type_name") != null && !"".equals(map.get("sub_type_name"))) {

				model.setSubTypeName(map.get("sub_type_name").toString());
			}
			//if (map.get("rollback_flag") != null && !"".equals(map.get("rollback_flag"))) {
				
				//model.setRollbackFlag(map.get("rollback_flag").toString());
			//}
			if (map.get("rollback_flag") != null
					&& !"".equals(map.get("rollback_flag"))) {
				model.setRollbackFlag(map.get("rollback_flag").toString());
			}
			
			r.add(model);
		}
		return r;
	}

	@Override
	public List<Map> getPrecheckShipModelBycountryCharge(String countryCharge) {
		String sql = "select * from pnr_act_transfer_precheck_ship s where s.country_charge='"
				+ countryCharge + "'";

		return this.getJdbcTemplate().queryForList(sql);
	}

	/**
	 * 已处理工单条数
	 * 
	 * @param processDefinitionKey
	 * @param userId
	 * @param sendStartTime
	 * @param sendEndTime
	 * @param wsNum
	 * @param wsTitle
	 * @param status
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getHaveProcessCount(String processDefinitionKey, String userId,
			ConditionQueryModel conditionQueryModel) {
		String themeinterface =com.boco.eoms.partner.process.util.CommonUtils.getTaskThemeinterfaceStr(processDefinitionKey,"m");
		
		ArrayList list1 = new ArrayList();
		String sql = "";
		String selectSql = "select count(processInstanceId) as total from (select ACTI.Proc_Inst_Id_ as processInstanceId,decode(MAIN.sheet_id, null, '无', MAIN.sheet_id) as sheetId, MAIN.Theme as theme,MAIN.city,MAIN.country,MAIN.work_type,MAIN.workorder_type_name,MAIN.sub_type_name,MAIN.key_word,MAIN.work_order_degree,MAIN.project_amount,MAIN.Compensate,MAIN.Calculate_Income_Ratio,MAIN.Initiator as initiator,MAIN.one_initiator as OneInitiator,MAIN.Submit_Application_Time as SubmitTime,MAIN.distributed_interface_time,DECODE(MAIN.STATE,8,'省公司审批通过-等待商城',ACTI.Name_) as statusName,DECODE(MAIN.STATE,8,'waitingCallInterface',ACTI.Task_Def_Key_) as taskDefKey,MAIN.TASK_ASSIGNEE,MAIN.Send_Time as sendTime,MAIN.job_order_type,MAIN.batch,"
                          +"l.id as relation_id,l.room_id,r.themeinterface as room_themeinterface,r.serial_number,r.jf_city,r.jf_country,r.jf_address_name,nvl(r.olt_number, '0') as olt_number,nvl(r.total_user_number, '0') as total_user_number,nvl(r.voice_user, '0') as voice_user,nvl(r.broadband_user, '0') as broadband_user,nvl(r.iptv_user, '0') as iptv_user,r.Is_No_Bbu,nvl(r.bbu_number, '0') as bbu_number,r.Line_Total_Investment,l.Is_Need_Rod_Investment,l.Is_Need_Cable_Investment,l.New_Built_Rod_Length,l.New_Built_Rod_Investment,l.Original_Cable_Route,l.New_Cable_Route,l.New_Paragraph,l.New_Line_Routing_Diagram,l.Cable_Cloth_Core_Number,l.Cable_Cloth_Length,l.Cable_Investment,l.Board_Demand,l.Light_Module_Demand,l.Trans_Board_Demand,l.Trans_Light_Module_Demand,l.Equipment_Investment,l.Jf_Remark"
                          +" from ( select k.proc_inst_id_, k.name_, k.task_def_key_ from ACT_RU_TASK k inner join ACT_RE_PROCDEF D on k.PROC_DEF_ID_ = D.ID_ where D.KEY_ = ? and (exists (select LINK.USER_ID_ from ACT_HI_IDENTITYLINK LINK where USER_ID_ = ? and LINK.PROC_INST_ID_ = k.proc_inst_id_)) and (exists (select kst.assignee_ from act_hi_taskinst kst where kst.assignee_ = ? and kst.proc_inst_id_ = k.proc_inst_id_ and kst.end_time_ is not null))) ACTI,PNR_ACT_TRANSFER_OFFICE_MAIN MAIN,pnr_olt_bbu_office_relation l,pnr_olt_bbu_room r WHERE ACTI.Proc_Inst_Id_ = MAIN.PROCESS_INSTANCE_ID and MAIN.Process_Instance_Id =l.process_instance_id(+) and l.room_id = r.id(+) and MAIN.themeinterface='"+themeinterface+"') ST";
		list1.add(processDefinitionKey);
		list1.add(userId);
		list1.add(userId);
		
		String whereSql = " WHERE 1=1";
		if (conditionQueryModel.getSendStartTime() != null
				&& !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and to_char(ST.sendTime,'yyyy-mm-dd hh24:mi:ss') >= ?";
			list1.add(conditionQueryModel.getSendStartTime());
		}
		if (conditionQueryModel.getSendEndTime() != null
				&& !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and to_char(ST.sendTime,'yyyy-mm-dd hh24:mi:ss') <= ?";
			list1.add(conditionQueryModel.getSendEndTime());
		}
		if (conditionQueryModel.getWorkNumber() != null
				&& !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and ST.processInstanceId = ?";
			list1.add(conditionQueryModel.getWorkNumber());
		}
		if (conditionQueryModel.getTheme() != null
				&& !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and instr(ST.theme,?)>0";
			list1.add(conditionQueryModel.getTheme());
		}
		if (conditionQueryModel.getStatus() != null
				&& !conditionQueryModel.getStatus().equals("")) {
			whereSql += " and ST.taskDefKey = ?";
			list1.add(conditionQueryModel.getStatus());

		}
		sql += selectSql + whereSql;

		System.out.println("---oltBbu已处理工单条数sql=" + sql);
		Object[] args = list1.toArray();

		int total = this.getJdbcTemplate().queryForInt(sql, args);
		return total;
	}
	
	/**
	 * 已处理工单明细
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
	@SuppressWarnings("unchecked")
	public List<PnrOltBbuOfficeMainModel> getHaveProcessList(String processDefinitionKey,
			String userId, ConditionQueryModel conditionQueryModel,
			int firstResult, int endResult, int pageSize) {
		String themeinterface =com.boco.eoms.partner.process.util.CommonUtils.getTaskThemeinterfaceStr(processDefinitionKey,"m");

		ArrayList list1 = new ArrayList();
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		String selectSql = "select * from (select ACTI.Proc_Inst_Id_ as processInstanceId,decode(MAIN.sheet_id, null, '无', MAIN.sheet_id) as sheetId, MAIN.Theme as theme,MAIN.city,MAIN.country,MAIN.work_type,MAIN.workorder_type_name,MAIN.sub_type_name,MAIN.key_word,MAIN.work_order_degree,MAIN.project_amount,MAIN.Compensate,MAIN.Calculate_Income_Ratio,MAIN.Initiator as initiator,MAIN.one_initiator as OneInitiator,MAIN.Submit_Application_Time as SubmitTime,MAIN.distributed_interface_time,DECODE(MAIN.STATE,8,'省公司审批通过-等待商城',ACTI.Name_) as statusName,DECODE(MAIN.STATE,8,'waitingCallInterface',ACTI.Task_Def_Key_) as taskDefKey,MAIN.TASK_ASSIGNEE,MAIN.Send_Time as sendTime,MAIN.job_order_type,MAIN.batch,"
                          +"l.id as relation_id,l.room_id,r.themeinterface as room_themeinterface,r.serial_number,r.jf_city,r.jf_country,r.jf_address_name,nvl(r.olt_number, '0') as olt_number,nvl(r.total_user_number, '0') as total_user_number,nvl(r.voice_user, '0') as voice_user,nvl(r.broadband_user, '0') as broadband_user,nvl(r.iptv_user, '0') as iptv_user,r.Is_No_Bbu,nvl(r.bbu_number, '0') as bbu_number,r.Line_Total_Investment,l.Is_Need_Rod_Investment,l.Is_Need_Cable_Investment,l.New_Built_Rod_Length,l.New_Built_Rod_Investment,l.Original_Cable_Route,l.New_Cable_Route,l.New_Paragraph,l.New_Line_Routing_Diagram,l.Cable_Cloth_Core_Number,l.Cable_Cloth_Length,l.Cable_Investment,l.Board_Demand,l.Light_Module_Demand,l.Trans_Board_Demand,l.Trans_Light_Module_Demand,l.Equipment_Investment,l.Jf_Remark"
                          +" from ( select k.proc_inst_id_, k.name_, k.task_def_key_ from ACT_RU_TASK k inner join ACT_RE_PROCDEF D on k.PROC_DEF_ID_ = D.ID_ where D.KEY_ = ? and (exists (select LINK.USER_ID_ from ACT_HI_IDENTITYLINK LINK where USER_ID_ = ? and LINK.PROC_INST_ID_ = k.proc_inst_id_)) and (exists (select kst.assignee_ from act_hi_taskinst kst where kst.assignee_ = ? and kst.proc_inst_id_ = k.proc_inst_id_ and kst.end_time_ is not null))) ACTI,PNR_ACT_TRANSFER_OFFICE_MAIN MAIN,pnr_olt_bbu_office_relation l,pnr_olt_bbu_room r WHERE ACTI.Proc_Inst_Id_ = MAIN.PROCESS_INSTANCE_ID and MAIN.Process_Instance_Id =l.process_instance_id(+) and l.room_id = r.id(+) and MAIN.themeinterface='"+themeinterface+"') ST";
		list1.add(processDefinitionKey);
		list1.add(userId);
		list1.add(userId);
		
		String whereSql = " WHERE 1=1";
		if (conditionQueryModel.getSendStartTime() != null
				&& !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and to_char(ST.sendTime,'yyyy-mm-dd hh24:mi:ss') >= ?";
			list1.add(conditionQueryModel.getSendStartTime());
		}
		if (conditionQueryModel.getSendEndTime() != null
				&& !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and to_char(ST.sendTime,'yyyy-mm-dd hh24:mi:ss') <= ?";
			list1.add(conditionQueryModel.getSendEndTime());
		}
		if (conditionQueryModel.getWorkNumber() != null
				&& !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and ST.processInstanceId = ?";
			list1.add(conditionQueryModel.getWorkNumber());
		}
		if (conditionQueryModel.getTheme() != null
				&& !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and instr(ST.theme,?)>0";
			list1.add(conditionQueryModel.getTheme());
		}
		if (conditionQueryModel.getStatus() != null
				&& !conditionQueryModel.getStatus().equals("")) {
			whereSql += " and ST.taskDefKey = ?";
			list1.add(conditionQueryModel.getStatus());

		}
		sql += selectSql
				+ whereSql
				+ " order by ST.sendTime) temp1 where rownum <= ?) temp2 where temp2.num > ?";
		
		list1.add(endResult * pageSize);
		list1.add(firstResult * pageSize);

		System.out.println("---oltBbu已处理工单明细sql=" + sql);
		Object[] args = list1.toArray();

		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<PnrOltBbuOfficeMainModel> r = new ArrayList<PnrOltBbuOfficeMainModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,args);
		for (Map map : list) {
			PnrOltBbuOfficeMainModel model = new PnrOltBbuOfficeMainModel();
			if (map.get("ProcessInstanceId") != null && !"".equals(map.get("ProcessInstanceId"))) {

				model.setProcessInstanceId(map.get("ProcessInstanceId").toString());
			}
			if (map.get("Theme") != null && !"".equals(map.get("Theme"))) {

				model.setTheme(map.get("Theme").toString());
			}
			if (map.get("job_order_type") != null && !"".equals(map.get("job_order_type"))) {

				model.setJobOrderType(map.get("job_order_type").toString());
			}
			if (map.get("batch") != null && !"".equals(map.get("batch"))) {

				model.setBatch(map.get("batch").toString());
			}
			if (map.get("olt_number") != null && !"".equals(map.get("olt_number"))) {

				model.setOltNumber(map.get("olt_number").toString());
			}
			if (map.get("bbu_number") != null && !"".equals(map.get("bbu_number"))) {

				model.setBbuNumber(map.get("bbu_number").toString());
			}
			if (map.get("total_user_number") != null && !"".equals(map.get("total_user_number"))) {

				model.setTotalUserNumber(map.get("total_user_number").toString());
			}
			if (map.get("voice_user") != null && !"".equals(map.get("voice_user"))) {

				model.setVoiceUser(map.get("voice_user").toString());
			}
			if (map.get("broadband_user") != null && !"".equals(map.get("broadband_user"))) {

				model.setBroadbandUser(map.get("broadband_user").toString());
			}
			if (map.get("iptv_user") != null && !"".equals(map.get("iptv_user"))) {

				model.setIptvUser(map.get("iptv_user").toString());
			}
			if (map.get("is_need_rod_investment") != null && !"".equals(map.get("is_need_rod_investment"))) {

				model.setIsNeedRodInvestment(map.get("is_need_rod_investment").toString());
			}
			if (map.get("is_need_cable_investment") != null && !"".equals(map.get("is_need_cable_investment"))) {

				model.setIsNeedCableInvestment(map.get("is_need_cable_investment").toString());
			}
			if (map.get("new_built_rod_length") != null && !"".equals(map.get("new_built_rod_length"))) {

				model.setNewBuiltRodLength(map.get("new_built_rod_length").toString());
			}
			if (map.get("new_built_rod_investment") != null && !"".equals(map.get("new_built_rod_investment"))) {

				model.setNewBuiltRodInvestment(map.get("new_built_rod_investment").toString());
			}
			if (map.get("original_cable_route") != null && !"".equals(map.get("original_cable_route"))) {

				model.setOriginalCableRoute(map.get("original_cable_route").toString());
			}
			if (map.get("new_cable_route") != null && !"".equals(map.get("new_cable_route"))) {

				model.setNewCableRoute(map.get("new_cable_route").toString());
			}
			if (map.get("cable_cloth_core_number") != null && !"".equals(map.get("cable_cloth_core_number"))) {

				model.setCableClothCoreNumber(map.get("cable_cloth_core_number").toString());
			}
			if (map.get("cable_cloth_length") != null && !"".equals(map.get("cable_cloth_length"))) {

				model.setCableClothLength(map.get("cable_cloth_length").toString());
			}
			if (map.get("cable_investment") != null && !"".equals(map.get("cable_investment"))) {

				model.setCableInvestment(map.get("cable_investment").toString());
			}
			if (map.get("line_total_investment") != null && !"".equals(map.get("line_total_investment"))) {

				model.setLineTotalInvestment(map.get("line_total_investment").toString());
			}
			if (map.get("board_demand") != null && !"".equals(map.get("board_demand"))) {

				model.setBoardDemand(map.get("board_demand").toString());
			}
			if (map.get("light_module_demand") != null && !"".equals(map.get("light_module_demand"))) {

				model.setLightModuleDemand(map.get("light_module_demand").toString());
			}
			if (map.get("equipment_investment") != null && !"".equals(map.get("equipment_investment"))) {

				model.setEquipmentInvestment(map.get("equipment_investment").toString());
			}
			if (map.get("sheetId") != null && !"".equals(map.get("sheetId"))) {

				model.setSheetId(map.get("sheetId").toString());
			}
			if (map.get("city") != null && !"".equals(map.get("city"))) {

				model.setCity(map.get("city").toString());
			}
			if (map.get("country") != null && !"".equals(map.get("country"))) {

				model.setCountry(map.get("country").toString());
			}
			if (map.get("new_paragraph") != null && !"".equals(map.get("new_paragraph"))) {

				model.setNewParagraph(map.get("new_paragraph").toString());
			}
			if (map.get("taskDefKey") != null && !"".equals(map.get("taskDefKey"))) {

				model.setTaskDefKey(map.get("taskDefKey").toString());
			}
			if (map.get("workorder_type_name") != null && !"".equals(map.get("workorder_type_name"))) {

				model.setWorkOrderTypeName(map.get("workorder_type_name").toString());
			}
			if (map.get("sub_type_name") != null && !"".equals(map.get("sub_type_name"))) {

				model.setSubTypeName(map.get("sub_type_name").toString());
			}
			if (map.get("statusName") != null
					&& !"".equals(map.get("statusName"))) {
				model.setStatusName(map.get("statusName").toString());
			}
			
			r.add(model);
		}

		return r;

	}

	/**
	 * 由我创建的工单条数
	 * 
	 * @param processDefinitionKey
	 * @param userId
	 * @param sendStartTime
	 * @param sendEndTime
	 * @param wsNum
	 * @param wsTitle
	 * @param status
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getByCreatingWorkOrderCount(String processDefinitionKey,
			String userId, String sendStartTime, String sendEndTime,
			String wsNum, String wsTitle, String status) {
		String themeinterface =com.boco.eoms.partner.process.util.CommonUtils.getTaskThemeinterfaceStr(processDefinitionKey,"");
		ArrayList list1 = new ArrayList();
		String sql = "";
		String selectSql = "select count(processInstanceId) as total from (select ACTI.Proc_Inst_Id_ as processInstanceId,MAIN.Theme as theme,MAIN.city,MAIN.country,MAIN.work_type,MAIN.workorder_type_name,MAIN.sub_type_name,MAIN.key_word,MAIN.work_order_degree,MAIN.project_amount,MAIN.Initiator as initiator,MAIN.Submit_Application_Time as SubmitTime,MAIN.TASK_ASSIGNEE,MAIN.Send_Time as sendTime,MAIN.State,MAIN.One_Initiator as oneInitiator,MAIN.Second_Initiator as twoInitiator,ACTI.END_TIME_,ACTI.DELETE_REASON_,case when ACTI.END_TIME_ is not null and ACTI.DELETE_REASON_ is null then '已归档' when ACTI.END_TIME_ is not null and ACTI.DELETE_REASON_ is not null then '已删除' when MAIN.State = '8' then '等待接口调用' else to_char(ACTI.StatusName) end as StatusName,case when ACTI.END_TIME_ is not null and ACTI.DELETE_REASON_ is null then 'archive' when ACTI.END_TIME_ is not null and ACTI.DELETE_REASON_ is not null then 'delete' when MAIN.State = '8' then 'waitingCallInterface' else to_char(ACTI.TaskDefKey) end as TaskDefKey,ACTI.TaskId,decode(MAIN.sheet_id, null, '无', MAIN.sheet_id) as sheetId,"
						   +"nvl(room.olt_number, '0') as olt_number,"
                           +"nvl(room.bbu_number, '0') as bbu_number,"
                           +"nvl(room.total_user_number, '0') as total_user_number,"
                           +"nvl(room.voice_user, '0') as voice_user,"
                           +"nvl(room.broadband_user, '0') as broadband_user,"
                           +"nvl(room.iptv_user, '0') as iptv_user,"
                           +"re.is_need_rod_investment,"
                           +"re.is_need_cable_investment,"
                           +"re.new_built_rod_length,"
                           +"re.new_built_rod_investment,"
                           +"re.original_cable_route,"
                           +"re.new_cable_route,"
                           +"re.cable_cloth_core_number,"
                           +"re.cable_cloth_length,"
                           +"re.cable_investment,"
                           +"re.line_total_investment,"
                           +"re.board_demand,"
                           +"re.light_module_demand,"
                           +"re.equipment_investment,"
                           +"re.new_paragraph,"
                           +"re.process_instance_id as pid from (" 
						   +"select RES.PROC_INST_ID_,RES.END_TIME_,RES.DELETE_REASON_,A2.NAME_ AS StatusName,A2.TASK_DEF_KEY_ AS TaskDefKey,A2.ID_ AS TaskId"
                           +" from ACT_HI_PROCINST RES,ACT_RE_PROCDEF DEF,(SELECT * FROM (SELECT ROW_NUMBER() OVER(PARTITION BY proc_inst_id_ ORDER BY start_time_ DESC) rn,HI.* FROM ACT_HI_TASKINST HI) WHERE rn = 1) A2"
                           +" where RES.PROC_DEF_ID_ = DEF.ID_ and RES.proc_inst_id_ = a2.proc_inst_id_ and DEF.KEY_ = ? and RES.START_USER_ID_ = ?) ACTI,PNR_ACT_TRANSFER_OFFICE_MAIN MAIN,pnr_olt_bbu_room room,pnr_olt_bbu_office_relation re WHERE ACTI.Proc_Inst_Id_ = MAIN.PROCESS_INSTANCE_ID(+) and MAIN.process_instance_id =re.process_instance_id(+) and re.room_id = room.id(+) and MAIN.State <> 2 and MAIN.themeinterface='"+themeinterface+"') ST";
		list1.add(processDefinitionKey);
		list1.add(userId);
		String whereSql = " WHERE 1=1";
		if (sendStartTime != null && !sendStartTime.equals("")) {
			whereSql += " and to_char(ST.sendTime,'yyyy-mm-dd hh24:mi:ss') >= ?";
			list1.add(sendStartTime);
		}
		if (sendEndTime != null && !sendEndTime.equals("")) {
			whereSql += " and to_char(ST.sendTime,'yyyy-mm-dd hh24:mi:ss') <= ?";
			list1.add(sendEndTime);
		}
		if (wsNum != null && !wsNum.equals("")) {
			whereSql += " and ST.processInstanceId = ?";
			list1.add(wsNum.trim());
		}
		if (wsTitle != null && !wsTitle.equals("")) {
			whereSql += " and instr(ST.theme,?)>0";
			list1.add(wsTitle.trim());
		}
		if (status != null && !status.equals("")) {
			whereSql += " and ST.taskDefKey = ?";
			list1.add(status);
		}
		sql += selectSql + whereSql;

		System.out.println("------oltbbu由我创建的工单条数sql=" + sql);
		Object[] args = list1.toArray();
		int total = this.getJdbcTemplate().queryForInt(sql, args);
		return total;
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
	@SuppressWarnings("unchecked")
	public List<PnrOltBbuOfficeMainModel> getByCreatingWorkOrderList(
			String processDefinitionKey, String userId, String sendStartTime,
			String sendEndTime, String wsNum, String wsTitle, String status,
			int firstResult, int endResult, int pageSize) {
		String themeinterface =com.boco.eoms.partner.process.util.CommonUtils.getTaskThemeinterfaceStr(processDefinitionKey,"");
		ArrayList list1 = new ArrayList();
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		String selectSql = "select * from (select ACTI.Proc_Inst_Id_ as processInstanceId,MAIN.Theme as theme,MAIN.city,MAIN.country, MAIN.Job_Order_Type,MAIN.workorder_type_name,MAIN.sub_type_name,MAIN.key_word,MAIN.work_order_degree,MAIN.project_amount,MAIN.Initiator as initiator,MAIN.Submit_Application_Time as SubmitTime,MAIN.distributed_interface_time,MAIN.TASK_ASSIGNEE,MAIN.Send_Time as sendTime,MAIN.State,MAIN.One_Initiator as oneInitiator,MAIN.Second_Initiator as twoInitiator,ACTI.END_TIME_,ACTI.DELETE_REASON_,case when ACTI.END_TIME_ is not null and ACTI.DELETE_REASON_ is null then '已归档' when ACTI.END_TIME_ is not null and ACTI.DELETE_REASON_ is not null then '已删除' when MAIN.State = '8' then '省运维主管审批通过-等待商城' else to_char(ACTI.StatusName) end as StatusName,case when ACTI.END_TIME_ is not null and ACTI.DELETE_REASON_ is null then 'archive' when ACTI.END_TIME_ is not null and ACTI.DELETE_REASON_ is not null then 'delete' when MAIN.State = '8' then 'waitingCallInterface' else to_char(ACTI.TaskDefKey) end as TaskDefKey,ACTI.TaskId,decode(MAIN.sheet_id, null, '无', MAIN.sheet_id) as sheetId,"
						   +"nvl(room.olt_number, '0') as olt_number,"
                           +"nvl(room.bbu_number, '0') as bbu_number,"
                           +"nvl(room.total_user_number, '0') as total_user_number,"
                           +"nvl(room.voice_user, '0') as voice_user,"
                           +"nvl(room.broadband_user, '0') as broadband_user,"
                           +"nvl(room.iptv_user, '0') as iptv_user,"
                           +"re.is_need_rod_investment,"
                           +"re.is_need_cable_investment,"
                           +"re.new_built_rod_length,"
                           +"re.new_built_rod_investment,"
                           +"re.original_cable_route,"
                           +"re.new_cable_route,"
                           +"re.cable_cloth_core_number,"
                           +"re.cable_cloth_length,"
                           +"re.cable_investment,"
                           +"re.line_total_investment,"
                           +"re.board_demand,"
                           +"re.light_module_demand,"
                           +"re.equipment_investment,"
                           +"re.new_paragraph,"
                           +"re.process_instance_id as pid from (" 
						   +"select RES.PROC_INST_ID_,RES.END_TIME_,RES.DELETE_REASON_,A2.NAME_ AS StatusName,A2.TASK_DEF_KEY_ AS TaskDefKey,A2.ID_ AS TaskId"
                           +" from ACT_HI_PROCINST RES,ACT_RE_PROCDEF DEF,(SELECT * FROM (SELECT ROW_NUMBER() OVER(PARTITION BY proc_inst_id_ ORDER BY start_time_ DESC) rn,HI.* FROM ACT_HI_TASKINST HI) WHERE rn = 1) A2"
                           +" where RES.PROC_DEF_ID_ = DEF.ID_ and RES.proc_inst_id_ = a2.proc_inst_id_ and DEF.KEY_ = ? and RES.START_USER_ID_ = ?) ACTI,PNR_ACT_TRANSFER_OFFICE_MAIN MAIN,pnr_olt_bbu_room room,pnr_olt_bbu_office_relation re WHERE ACTI.Proc_Inst_Id_ = MAIN.PROCESS_INSTANCE_ID(+) and MAIN.process_instance_id =re.process_instance_id(+) and re.room_id = room.id(+) and MAIN.State <> 2 and MAIN.themeinterface='"+themeinterface+"') ST";
		list1.add(processDefinitionKey);
		list1.add(userId);
		String whereSql = " WHERE 1=1 ";
		if (sendStartTime != null && !sendStartTime.equals("")) {
			whereSql += " and to_char(ST.sendTime,'yyyy-mm-dd hh24:mi:ss') >= ?";
			list1.add(sendStartTime);
		}
		if (sendEndTime != null && !sendEndTime.equals("")) {
			whereSql += " and to_char(ST.sendTime,'yyyy-mm-dd hh24:mi:ss') <= ?";
			list1.add(sendEndTime);
		}
		if (wsNum != null && !wsNum.equals("")) {
			whereSql += " and ST.processInstanceId = ?";
			list1.add(wsNum.trim());
		}
		if (wsTitle != null && !wsTitle.equals("")) {
			whereSql += " and instr(ST.theme,?)>0";
			list1.add(wsTitle.trim());
		}
		if (status != null && !status.equals("")) {
			whereSql += " and ST.taskDefKey = ?";
			list1.add(status);
		}
		sql += selectSql
				+ whereSql
				+ " order by ST.sendTime) temp1 where rownum <= ?) temp2 where temp2.num > ?";
		list1.add(endResult * pageSize);
		list1.add(firstResult * pageSize);

		System.out.println("-----oltbbu由我创建的工单明细sql=" + sql);
		Object[] args = list1.toArray();
		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<PnrOltBbuOfficeMainModel> r = new ArrayList<PnrOltBbuOfficeMainModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,args);
		for (Map map : list) {
			PnrOltBbuOfficeMainModel model = new PnrOltBbuOfficeMainModel();
			if (map.get("TaskId") != null && !"".equals(map.get("TaskId"))) {

				model.setTaskId(map.get("TaskId").toString());
			}
			if (map.get("processInstanceId") != null && !"".equals(map.get("processInstanceId"))) {

				model.setProcessInstanceId(map.get("processInstanceId").toString());
			}
			if (map.get("Theme") != null && !"".equals(map.get("Theme"))) {

				model.setTheme(map.get("Theme").toString());
			}
			if (map.get("job_order_type") != null && !"".equals(map.get("job_order_type"))) {

				model.setJobOrderType(map.get("job_order_type").toString());
			}
			if (map.get("batch") != null && !"".equals(map.get("batch"))) {

				model.setBatch(map.get("batch").toString());
			}
			if (map.get("olt_number") != null && !"".equals(map.get("olt_number"))) {

				model.setOltNumber(map.get("olt_number").toString());
			}
			if (map.get("bbu_number") != null && !"".equals(map.get("bbu_number"))) {

				model.setBbuNumber(map.get("bbu_number").toString());
			}
			if (map.get("total_user_number") != null && !"".equals(map.get("total_user_number"))) {

				model.setTotalUserNumber(map.get("total_user_number").toString());
			}
			if (map.get("voice_user") != null && !"".equals(map.get("voice_user"))) {

				model.setVoiceUser(map.get("voice_user").toString());
			}
			if (map.get("broadband_user") != null && !"".equals(map.get("broadband_user"))) {

				model.setBroadbandUser(map.get("broadband_user").toString());
			}
			if (map.get("iptv_user") != null && !"".equals(map.get("iptv_user"))) {

				model.setIptvUser(map.get("iptv_user").toString());
			}
			if (map.get("is_need_rod_investment") != null && !"".equals(map.get("is_need_rod_investment"))) {

				model.setIsNeedRodInvestment(map.get("is_need_rod_investment").toString());
			}
			if (map.get("is_need_cable_investment") != null && !"".equals(map.get("is_need_cable_investment"))) {

				model.setIsNeedCableInvestment(map.get("is_need_cable_investment").toString());
			}
			if (map.get("new_built_rod_length") != null && !"".equals(map.get("new_built_rod_length"))) {

				model.setNewBuiltRodLength(map.get("new_built_rod_length").toString());
			}
			if (map.get("new_built_rod_investment") != null && !"".equals(map.get("new_built_rod_investment"))) {

				model.setNewBuiltRodInvestment(map.get("new_built_rod_investment").toString());
			}
			if (map.get("original_cable_route") != null && !"".equals(map.get("original_cable_route"))) {

				model.setOriginalCableRoute(map.get("original_cable_route").toString());
			}
			if (map.get("new_cable_route") != null && !"".equals(map.get("new_cable_route"))) {

				model.setNewCableRoute(map.get("new_cable_route").toString());
			}
			if (map.get("cable_cloth_core_number") != null && !"".equals(map.get("cable_cloth_core_number"))) {

				model.setCableClothCoreNumber(map.get("cable_cloth_core_number").toString());
			}
			if (map.get("cable_cloth_length") != null && !"".equals(map.get("cable_cloth_length"))) {

				model.setCableClothLength(map.get("cable_cloth_length").toString());
			}
			if (map.get("cable_investment") != null && !"".equals(map.get("cable_investment"))) {

				model.setCableInvestment(map.get("cable_investment").toString());
			}
			if (map.get("line_total_investment") != null && !"".equals(map.get("line_total_investment"))) {

				model.setLineTotalInvestment(map.get("line_total_investment").toString());
			}
			if (map.get("board_demand") != null && !"".equals(map.get("board_demand"))) {

				model.setBoardDemand(map.get("board_demand").toString());
			}
			if (map.get("light_module_demand") != null && !"".equals(map.get("light_module_demand"))) {

				model.setLightModuleDemand(map.get("light_module_demand").toString());
			}
			if (map.get("equipment_investment") != null && !"".equals(map.get("equipment_investment"))) {

				model.setEquipmentInvestment(map.get("equipment_investment").toString());
			}
			if (map.get("sheetId") != null && !"".equals(map.get("sheetId"))) {

				model.setSheetId(map.get("sheetId").toString());
			}
			if (map.get("city") != null && !"".equals(map.get("city"))) {

				model.setCity(map.get("city").toString());
			}
			if (map.get("country") != null && !"".equals(map.get("country"))) {

				model.setCountry(map.get("country").toString());
			}
			if (map.get("new_paragraph") != null && !"".equals(map.get("new_paragraph"))) {

				model.setNewParagraph(map.get("new_paragraph").toString());
			}
			if (map.get("taskDefKey") != null && !"".equals(map.get("taskDefKey"))) {

				model.setTaskDefKey(map.get("taskDefKey").toString());
			}
			if (map.get("workorder_type_name") != null && !"".equals(map.get("workorder_type_name"))) {

				model.setWorkOrderTypeName(map.get("workorder_type_name").toString());
			}
			if (map.get("sub_type_name") != null && !"".equals(map.get("sub_type_name"))) {

				model.setSubTypeName(map.get("sub_type_name").toString());
			}
			//if (map.get("rollback_flag") != null && !"".equals(map.get("rollback_flag"))) {
				
				//model.setRollbackFlag(map.get("rollback_flag").toString());
			//}
			if (map.get("rollback_flag") != null
					&& !"".equals(map.get("rollback_flag"))) {
				model.setRollbackFlag(map.get("rollback_flag").toString());
			}
			if (map.get("statusName") != null
					&& !"".equals(map.get("statusName"))) {
				model.setStatusName(map.get("statusName").toString());
			}

			r.add(model);

		}

		return r;

	}

	/**
	 * 工单抓回 条数
	 * 
	 * @param processDefinitionKey
	 *            流程ID
	 * @param userTaskFlag
	 *            能抓回的环节ID集合
	 * @param userId
	 *            登录用户ID
	 * @param conditionQueryModel
	 *            查询条件
	 * @return
	 */
	public int getBackSheetCount(String processDefinitionKey, String userId,
			ConditionQueryModel conditionQueryModel) {
		String sql = "";
		String selectSql = "select count(*) as total from (select * from (select distinct RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_ WHERE D.KEY_ = '"+processDefinitionKey+"') t, transferNewPrechech_relation r where t.task_def_key_ = r.current_link and t.task_def_key_ in ('workOrderCheck','cityLineExamine','cityLineDirectorAudit','cityManageExamine','cityManageDirectorAudit')) ta,(select distinct h.proc_inst_id_, h.task_def_key_, h.assignee_ from act_hi_taskinst h) hi,pnr_act_transfer_office_main m where ta.proc_inst_id_ = hi.proc_inst_id_ and ta.before_link = hi.task_def_key_ and ta.proc_inst_id_ = m.process_instance_id and hi.assignee_ = '"
				+ userId
				+ "' and (m.state != 3 and m.state != 6 and m.state != 8)";

		String whereSql = "";
		if (conditionQueryModel.getWorkNumber() != null
				&& !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and m.process_instance_id ='"
					+ conditionQueryModel.getWorkNumber().trim() + "'";
		}
		if (conditionQueryModel.getTheme() != null
				&& !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and instr(m.theme ,'"
					+ conditionQueryModel.getTheme().trim() + "')>0";
		}

		sql = selectSql + whereSql;

		System.out.println("--------------本地网预检预修 工单抓回 条数sql=" + sql);

		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		return Integer.parseInt(list.get(0).get("total").toString());
	}

	/**
	 * 工单抓回 明细
	 * 
	 * @param processDefinitionKey
	 *            流程ID
	 * @param userTaskFlag
	 *            能抓回的环节ID集合
	 * @param userId
	 *            登录用户ID
	 * @param conditionQueryModel
	 *            查询条件
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getBackSheetList(String processDefinitionKey,
			String userId, ConditionQueryModel conditionQueryModel,
			int firstResult, int endResult, int pageSize) {
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		String selectSql = "select ta.id_ as TaskId,m.initiator as Initiator,m.one_initiator as OneInitiator,m.process_instance_id as ProcessInstanceId,m.send_time as SendTime,m.theme as Theme,m.task_assignee,m.state as State,m.submit_application_time as SubmitTime,m.city,m.country,m.work_type,m.workorder_type_name,m.sub_type_name,m.key_word,m.work_order_degree,m.rollback_flag,m.precheck_flag,m.project_amount,decode(m.sheet_id, null, '无', m.sheet_id) as sheetId,ta.name_ as statusName,ta.task_def_key_ as taskDefKey from (select * from (select distinct RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_ WHERE D.KEY_ = '"+processDefinitionKey+"') t, transferNewPrechech_relation r where t.task_def_key_ = r.current_link and t.task_def_key_ in ('workOrderCheck','cityLineExamine','cityLineDirectorAudit','cityManageExamine','cityManageDirectorAudit')) ta,(select distinct h.proc_inst_id_, h.task_def_key_, h.assignee_ from act_hi_taskinst h) hi,pnr_act_transfer_office_main m where ta.proc_inst_id_ = hi.proc_inst_id_ and ta.before_link = hi.task_def_key_ and ta.proc_inst_id_ = m.process_instance_id and hi.assignee_ = '"
				+ userId
				+ "' and (m.state != 3 and m.state != 6 and m.state != 8)";
		String whereSql = "";
		if (conditionQueryModel.getWorkNumber() != null
				&& !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and m.process_instance_id ='"
					+ conditionQueryModel.getWorkNumber().trim() + "'";
		}
		if (conditionQueryModel.getTheme() != null
				&& !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and instr(m.theme,'"+ conditionQueryModel.getTheme().trim() + "')>0";
		}
		sql += selectSql
				+ whereSql
				+ " order by  m.send_time) temp1 where rownum <="
				+ endResult * pageSize + ") temp2 where temp2.num > "
				+ firstResult * pageSize;

		System.out.println("--------------本地网预检预修 工单抓回 明细sql=" + sql);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		for (Map map : list) {
			TaskModel model = new TaskModel();
			if (map.get("TaskId") != null && !"".equals(map.get("TaskId"))) {

				model.setTaskId(map.get("TaskId").toString());
			}
			if (map.get("Initiator") != null
					&& !"".equals(map.get("Initiator"))) {

				model.setInitiator(map.get("Initiator").toString());
			}
			if (map.get("OneInitiator") != null
					&& !"".equals(map.get("OneInitiator"))) {

				model.setOneInitiator(map.get("OneInitiator").toString());
			}
			if (map.get("ProcessInstanceId") != null
					&& !"".equals(map.get("ProcessInstanceId"))) {

				model.setProcessInstanceId(map.get("ProcessInstanceId")
						.toString());
			}
			if (map.get("SubmitTime") != null) {
				if (!"".equals(map.get("SubmitTime"))) {
					try {
						model.setApplicationTime(format.parse(map.get(
								"SubmitTime").toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

			}
//			if (map.get("SubmitTime") != null
//					&& !"".equals(map.get("SubmitTime"))) {
//
//				model.setApplicationTime(map.get("SubmitTime")
//						.toString());
//			}
			if (map.get("Theme") != null && !"".equals(map.get("Theme"))) {

				model.setTheme(map.get("Theme").toString());
			}
			if (map.get("DeptId") != null && !"".equals(map.get("DeptId"))) {

				model.setDeptId(map.get("DeptId").toString());
			}
			if (map.get("State") != null && !"".equals(map.get("State"))) {

				model.setState(Integer.parseInt(map.get("State").toString()));
			}
			if (map.get("city") != null && !"".equals(map.get("city"))) {

				model.setCity(map.get("city").toString());
			}
			if (map.get("country") != null && !"".equals(map.get("country"))) {
				model.setCountry(map.get("country").toString());
			}
			if (map.get("work_type") != null
					&& !"".equals(map.get("work_type"))) {

				model.setWorkType(map.get("work_type").toString());
			}
			if (map.get("workorder_type_name") != null
					&& !"".equals(map.get("workorder_type_name"))) {
				model.setWorkorderTypeName(map.get("workorder_type_name")
						.toString());
			}

			if (map.get("sub_type_name") != null
					&& !"".equals(map.get("sub_type_name"))) {
				model.setSubTypeName(map.get("sub_type_name").toString());
			}

			if (map.get("key_word") != null && !"".equals(map.get("key_word"))) {
				model.setKeyWord(map.get("key_word").toString());
			}
			if (map.get("work_order_degree") != null
					&& !"".equals(map.get("work_order_degree"))) {
				model.setWorkOrderDegree(map.get("work_order_degree")
						.toString());
			}

			if (map.get("project_amount") != null
					&& !"".equals(map.get("project_amount"))) {
				model.setProjectAmount(Double.parseDouble(map.get(
						"project_amount").toString()));
			}
			if (map.get("sheetId") != null && !"".equals(map.get("sheetId"))) {
				model.setSheetId(map.get("sheetId").toString());
			}
			if (map.get("rollback_flag") != null
					&& !"".equals(map.get("rollback_flag"))) {
				model.setRollbackFlag(map.get("rollback_flag").toString());
			}
			if (map.get("precheck_flag") != null
					&& !"".equals(map.get("precheck_flag"))) {
				model.setPrecheckFlag(map.get("precheck_flag").toString());
			}
			if (map.get("statusName") != null
					&& !"".equals(map.get("statusName"))) {

				model.setStatusName(map.get("statusName").toString());
			}
			if (map.get("taskDefKey") != null
					&& !"".equals(map.get("taskDefKey"))) {

				model.setTaskDefKey(map.get("taskDefKey").toString());
			}
			r.add(model);
		}
		return r;
	}

	@Override
	public void judgeIsExitBypgohoIdAndProcessInstanceId(
			String processInstanceId) {
		StringBuilder sql = new StringBuilder(
				"delete pnr_act_precheck_photo_ship p where p.processinstance_id='")
				.append(processInstanceId).append("'");
		this.getJdbcTemplate().update(sql.toString());
	}

	@Override
	public List<PnrAndroidPhotoFile> getPrecheckPhotoes(String userId,
			String photoDescribe, String createTime, String photoCreate,String faultLocation) {
		StringBuilder sql = new StringBuilder(
				"select * from pnr_android_photo p");
		sql.append("  where p.city=");
		sql.append(" (select substr(nvl(a.areaid,'0'),1,4) from pnr_user u");
		sql.append(" left join taw_system_dept d");
		sql.append(" on u.dept_id = d.deptid");
		sql.append(" left join taw_system_area a");
		sql.append(" on d.areaid=a.areaid");
		sql.append("  where 1=1 and d.deleted='0' ");
		String person = "";
		if (photoCreate != null && !"".equals(photoCreate)) {
			person = photoCreate;
		} else {
			person = userId;
		}
		sql.append(" and u.user_id='").append(person).append("')");
		sql.append(" and p.user_id = '").append(person).append("'");
		sql.append(" and nvl(p.state, 0) != '1'");
		if (photoDescribe != null && !"".equals(photoDescribe)) {
			sql.append(" and  instr(p.faultdescription,'")
					.append(photoDescribe).append("')>0 ");
		}
		if (faultLocation != null && !"".equals(faultLocation)) {
			sql.append(" and  instr(p.faultLocation, '")
			.append(faultLocation).append("')>0");
		}
		if (createTime != null && !"".equals(createTime)) {

			sql.append(" and to_date(p.createtime,'yyyy-mm-dd hh24:mi:ss')");
			sql.append(">=to_date('").append(createTime).append(
					"','yyyy-mm-dd hh24:mi:ss')");
		}

		System.out.println("=====本地网预检预修事前图片查询sql=" + sql.toString());
		List<Map> list = this.getJdbcTemplate().queryForList(sql.toString());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<PnrAndroidPhotoFile> photoList = new ArrayList<PnrAndroidPhotoFile>();
		if (list != null && list.size() > 0) {
			for (Map map : list) {
				PnrAndroidPhotoFile onePhoto = new PnrAndroidPhotoFile();
				onePhoto.setId(map.get("ID") == null ? null : map.get("ID")
						.toString());
				onePhoto.setPicture_id(map.get("PICUTRE_ID") == null ? null
						: map.get("PICUTRE_ID").toString());
				// onePhoto.setFileData(map.get("FileData").toString());
				try {
					onePhoto.setCreateTime(map.get("CREATETIME") == null ? null
							: map.get("CREATETIME").toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
				onePhoto.setLatitude(map.get("LATITUDE") == null ? null : map
						.get("LATITUDE").toString());
				onePhoto.setLongitude(map.get("LONGITUDE") == null ? null : map
						.get("LONGITUDE").toString());
				onePhoto
						.setFaultLocation(map.get("FAULTLOCATION") == null ? null
								: map.get("FAULTLOCATION").toString());
				onePhoto
						.setFaultDescription(map.get("FAULTDESCRIPTION") == null ? null
								: map.get("FAULTDESCRIPTION").toString());
				onePhoto.setUserId(map.get("USER_ID") == null ? null : map.get(
						"USER_ID").toString());
				// 取出数据库中图片存储的路径，并截取出图片名称
				// onePhoto.setPhotoName("照片名称");
				photoList.add(onePhoto);
			}
		} else {
			photoList = null;
		}
		return photoList;
	}

	@Override
	public List<PnrAndroidPhotoFile> showPhotoByProcessInstanceId(
			String processInstanceId) {
		StringBuilder sql = new StringBuilder(
				"select * from pnr_act_precheck_photo_ship sp ");
		sql.append(" left join pnr_android_photo ap on sp.photo_id=ap.id ");
		sql.append(" where sp.processinstance_id='").append(processInstanceId)
				.append("'");
		System.out.println("----------新建工单图片查看sql=" + sql);
		List<Map> list = this.getJdbcTemplate().queryForList(sql.toString());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<PnrAndroidPhotoFile> photoList = new ArrayList<PnrAndroidPhotoFile>();
		if (list != null && list.size() > 0) {
			for (Map map : list) {
				PnrAndroidPhotoFile onePhoto = new PnrAndroidPhotoFile();
				onePhoto.setId(map.get("ID") == null ? null : map.get("ID")
						.toString());
				onePhoto.setPicture_id(map.get("PICUTRE_ID") == null ? null
						: map.get("PICUTRE_ID").toString());
				// onePhoto.setFileData((Clob)map.get("FILEDATA"));
				try {
					onePhoto.setCreateTime(map.get("CREATETIME") == null ? null
							: map.get("CREATETIME").toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
				onePhoto.setLatitude(map.get("LATITUDE") == null ? null : map
						.get("LATITUDE").toString());
				onePhoto.setLongitude(map.get("LONGITUDE") == null ? null : map
						.get("LONGITUDE").toString());
				onePhoto
						.setFaultLocation(map.get("FAULTLOCATION") == null ? null
								: map.get("FAULTLOCATION").toString());
				onePhoto
						.setFaultDescription(map.get("FAULTDESCRIPTION") == null ? null
								: map.get("FAULTDESCRIPTION").toString());
				onePhoto.setUserId(map.get("USER_ID") == null ? null : map.get(
						"USER_ID").toString());
				// 取出数据库中图片存储的路径，并截取出图片名称
				// onePhoto.setPhotoName("照片名称");
				photoList.add(onePhoto);
			}
		} else {
			photoList = null;
		}
		return photoList;
	}

	@Override
	public void setStateByphotoId(String photoId, String state) {
		StringBuilder sql = new StringBuilder(
				"update pnr_android_photo p set p.state=");
		sql.append(state);
		sql.append(" where p.id='");
		sql.append(photoId).append("'");

		this.getJdbcTemplate().update(sql.toString());

	}

	@Override
	public PnrAndroidPhotoFile getOnePnrAndroidPhotoFileById(String id) {
		StringBuilder sql = new StringBuilder(
				"select * from pnr_android_photo p where p.id='").append(id)
				.append("'");
		List<Map> list = this.getJdbcTemplate().queryForList(sql.toString());
		PnrAndroidPhotoFile pnrAndroidPhotoFile = new PnrAndroidPhotoFile();
		if (list != null && list.size() > 0) {
			pnrAndroidPhotoFile.setId(list.get(0).get("ID") == null ? null
					: list.get(0).get("ID").toString());
			pnrAndroidPhotoFile
					.setPicture_id(list.get(0).get("PICTURE_ID") == null ? null
							: list.get(0).get("PICTURE_ID").toString());
			pnrAndroidPhotoFile
					.setCreateTime(list.get(0).get("CREATETIME") == null ? null
							: list.get(0).get("CREATETIME").toString());
			pnrAndroidPhotoFile
					.setFileData(list.get(0).get("FILEDATA") == null ? null
							: (Clob) list.get(0).get("FILEDATA"));
			pnrAndroidPhotoFile.setFaultLocation(list.get(0).get(
					"FAULTLOCATION") == null ? null : list.get(0).get(
					"FAULTLOCATION").toString());
			pnrAndroidPhotoFile.setFaultDescription(list.get(0).get(
					"FAULTDESCRIPTION") == null ? null : list.get(0).get(
					"FAULTDESCRIPTION").toString());
			pnrAndroidPhotoFile
					.setLatitude(list.get(0).get("LATITUDE") == null ? null
							: list.get(0).get("LATITUDE").toString());
			pnrAndroidPhotoFile
					.setLongitude(list.get(0).get("LONGITUDE") == null ? null
							: list.get(0).get("LONGITUDE").toString());
			pnrAndroidPhotoFile.setCity(list.get(0).get("CITY") == null ? null
					: list.get(0).get("CITY").toString());
			pnrAndroidPhotoFile
					.setDeptId(list.get(0).get("DEPT_ID") == null ? null : list
							.get(0).get("DEPT_ID").toString());
			pnrAndroidPhotoFile
					.setUserId(list.get(0).get("USER_ID") == null ? null : list
							.get(0).get("USER_ID").toString());
		} else {
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
	public List<TaskModel> getArchivedList(String processDefinitionKey,
			String userId, String sendStartTime, String sendEndTime,
			String wsNum, String wsTitle, String status, String themeInterface,
			int firstResult, int endResult, int pageSize) {
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		String selectSql = "select ACTI.PROC_INST_ID_ as processInstanceId,decode(MAIN.sheet_id, null, '无', MAIN.sheet_id) as sheetId,MAIN.THEME AS theme,MAIN.city ,MAIN.country,MAIN.work_type,MAIN.workorder_type_name,MAIN.sub_type_name ,MAIN.key_word ,MAIN.work_order_degree,MAIN.project_amount,MAIN.INITIATOR AS initiator,MAIN.submit_application_time as SubmitTime,decode(ACTI.Delete_Reason_, 'delete', '已删除', '已归档') as statusName,ACTI.Delete_Reason_,MAIN.TASK_ASSIGNEE,MAIN.SEND_TIME as SEND_TIME from (select RES.*,decode(RES.Delete_Reason_,'delete','delete','archived') as tempStatusName from (select distinct K.* from ACT_HI_PROCINST K inner join ACT_RE_PROCDEF D on K.PROC_DEF_ID_ = D.ID_ WHERE D.KEY_ = '"+processDefinitionKey+"') RES left join (select TAS.* from ACT_HI_TASKINST TAS where TAS.id_ in (select max(TAS.id_) from ACT_HI_TASKINST TAS group by TAS.proc_inst_id_)) TAS on RES.PROC_INST_ID_ = TAS.PROC_INST_ID_ WHERE RES.END_TIME_ is not NULL and (exists (select LINK.USER_ID_ from ACT_HI_IDENTITYLINK LINK where USER_ID_ = '"
				+ userId
				+ "' and LINK.PROC_INST_ID_ = RES.ID_))) ACTI,PNR_ACT_TRANSFER_OFFICE_MAIN MAIN where ACTI.Proc_Inst_Id_ = MAIN.PROCESS_INSTANCE_ID(+)  and MAIN.THEMEINTERFACE = '"
				+ themeInterface + "'";
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
			whereSql += " and instr(MAIN.THEME , '" + wsTitle.trim() + "')>0";
		}
		if (status != null && !status.equals("")) {
			whereSql += " and tempStatusName = '" + status + "'";

		}
		sql += selectSql
				+ whereSql
				+ " order by MAIN.SEND_TIME) temp1 where rownum <="
				+ endResult * pageSize + ") temp2 where temp2.num > "
				+ firstResult * pageSize;

		System.out.println("--------本地网或干线预检预修 已归档工单明细sql=" + sql);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		for (Map map : list) {
			TaskModel model = new TaskModel();
			if (map.get("processInstanceId") != null
					&& !"".equals(map.get("processInstanceId"))) {
				model.setProcessInstanceId(map.get("processInstanceId")
						.toString());
			}
			if (map.get("sheetId") != null && !"".equals(map.get("sheetId"))) {
				model.setSheetId(map.get("sheetId").toString());
			}
			if (map.get("theme") != null && !"".equals(map.get("theme"))) {
				model.setTheme(map.get("theme").toString());
			}
			if (map.get("initiator") != null
					&& !"".equals(map.get("initiator"))) {
				model.setInitiator(map.get("initiator").toString());
			}
			if (map.get("DEPTID") != null && !"".equals(map.get("DEPTID"))) {
				model.setDeptId(map.get("DEPTID").toString());
			}
			if (map.get("SubmitTime") != null) {
				if (!"".equals(map.get("SubmitTime"))) {
					try {
						model.setApplicationTime(format.parse(map.get(
								"SubmitTime").toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

			}
//			if (map.get("SubmitTime") != null
//					&& !"".equals(map.get("SubmitTime"))) {
//
//				model.setApplicationTime(map.get("SubmitTime")
//						.toString());
//			}
			if (map.get("statusName") != null
					&& !"".equals(map.get("statusName"))) {
				model.setStatusName(map.get("statusName").toString());
			}
			if (map.get("city") != null && !"".equals(map.get("city"))) {

				model.setCity(map.get("city").toString());
			}
			if (map.get("country") != null && !"".equals(map.get("country"))) {
				model.setCountry(map.get("country").toString());
			}
			if (map.get("work_type") != null
					&& !"".equals(map.get("work_type"))) {

				model.setWorkType(map.get("work_type").toString());
			}
			if (map.get("workorder_type_name") != null
					&& !"".equals(map.get("workorder_type_name"))) {
				model.setWorkorderTypeName(map.get("workorder_type_name")
						.toString());
			}

			if (map.get("sub_type_name") != null
					&& !"".equals(map.get("sub_type_name"))) {
				model.setSubTypeName(map.get("sub_type_name").toString());
			}

			if (map.get("key_word") != null && !"".equals(map.get("key_word"))) {
				model.setKeyWord(map.get("key_word").toString());
			}
			if (map.get("work_order_degree") != null
					&& !"".equals(map.get("work_order_degree"))) {
				model.setWorkOrderDegree(map.get("key_word").toString());
			}

			if (map.get("project_amount") != null
					&& !"".equals(map.get("project_amount"))) {
				model.setProjectAmount(Double.parseDouble(map.get(
						"project_amount").toString()));
			}

			r.add(model);

		}

		return r;

	}

	/**
	 * 已归档工单条数
	 * 
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
			String wsTitle, String status, String themeInterface) {
		String sql = "";
		String selectSql = "select count(ACTI.PROC_INST_ID_ ) as total from (select RES.*,decode(RES.Delete_Reason_,'delete','delete','archived') as tempStatusName from (select distinct K.* from ACT_HI_PROCINST K inner join ACT_RE_PROCDEF D on K.PROC_DEF_ID_ = D.ID_ WHERE D.KEY_ = '"+processDefinitionKey+"') RES left join (select TAS.* from ACT_HI_TASKINST TAS where TAS.id_ in (select max(TAS.id_) from ACT_HI_TASKINST TAS group by TAS.proc_inst_id_)) TAS on RES.PROC_INST_ID_ = TAS.PROC_INST_ID_ WHERE RES.END_TIME_ is not NULL and (exists (select LINK.USER_ID_ from ACT_HI_IDENTITYLINK LINK where USER_ID_ = '"
				+ userId
				+ "' and LINK.PROC_INST_ID_ = RES.ID_))) ACTI,PNR_ACT_TRANSFER_OFFICE_MAIN MAIN where ACTI.Proc_Inst_Id_ = MAIN.PROCESS_INSTANCE_ID(+) and MAIN.THEMEINTERFACE = '"
				+ themeInterface + "'";
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
			whereSql += " and tempStatusName = '" + status + "'";
		}
		sql += selectSql + whereSql;

		System.out.println("--------本地网或干线预检预修 已归档工单条数sql=" + sql);
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		return Integer.parseInt(list.get(0).get("total").toString());
	}

	/**
	 * add by wyl 20150430
	 * 获取下载附件个数
	 */
	@Override
	public int getDownAttachMentCount(String userid,
			ConditionQueryDAMModel conditionQueryModel) {
		String sql = "";
		String selectSql = "select count(*) total from pnr_act_transfer_office_main m   left join pnr_act_transfer_attachment t on m.process_instance_id = t.process_instance_id   left join taw_commons_accessories tr on t.accessories_id = tr.id   left join act_ru_task k on m.process_instance_id = k.proc_inst_id_ where 1=1 ";

		String whereSql = "";

		//开始时间
		if (conditionQueryModel.getSendStartTime() != null
				&& !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and m.send_time >=to_date('"
					+ conditionQueryModel.getSendStartTime()
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}
		//结束时间
		if (conditionQueryModel.getSendEndTime() != null
				&& !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and m.send_time <= to_date('"
					+ conditionQueryModel.getSendEndTime()
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}

		//地市
		if (conditionQueryModel.getCity() != null
				&& !conditionQueryModel.getCity().equals("")) {
			whereSql += " and m.city = '" + conditionQueryModel.getCity() + "'";

		}
		//区县
		if (conditionQueryModel.getCountry() != null
				&& !conditionQueryModel.getCountry().equals("")) {
			whereSql += " and m.country = '" + conditionQueryModel.getCountry()
					+ "'";
		}
		
		//当前登陆人地市
		if (conditionQueryModel.getArea() != null
				&& !conditionQueryModel.getArea().equals("")) {
			if(conditionQueryModel.getArea().trim().length()==6){
				whereSql += " and m.country = '" + conditionQueryModel.getArea()
				+ "'";
			}
			
		}
		
		//工单类型themeinterface  transferOffice抢修工单；maleGuest 公客；interface 本地网预检预修；arteryPrecheck 干线预检预修；
		//预检预修-本地网、预检预修-干线、大修改造
		//逗号分隔 '',''
		if (conditionQueryModel.getThemeinterface() != null
				&& !conditionQueryModel.getThemeinterface().equals("")) {
			whereSql += " and m.themeinterface in (" + conditionQueryModel.getThemeinterface()
					+ ")";
		}
		
		//所属环节 
		//逗号分隔 '',''
		if (conditionQueryModel.getTaskdefkey() != null
				&& !conditionQueryModel.getTaskdefkey().equals("")) {
			whereSql += " and k.task_def_key_ in (" + conditionQueryModel.getTaskdefkey()
					+ ")";
		}

		sql = selectSql + whereSql;

		System.out.println("--------------下载附件sql=" + sql);

		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		return Integer.parseInt(list.get(0).get("total").toString());

	}
	
	/**
	 * 通过照片ID删除事情照片
	 * @param id 照片ID
	 */
	public void deletePictureById(String id) {
		String sql = "delete from pnr_android_photo p where p.id = '" + id
				+ "'";
		System.out.println("-----------------通过照片ID删除事情照片sql="+sql);
		
		this.getJdbcTemplate().update(sql);
	}
	
	@Override
	public List<DownAttachMentModel> getDownAttachMentList(String userid,
			ConditionQueryDAMModel conditionQueryModel, int firstResult,
			int endResult, int pageSize) {
		
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		
		String selectSql = " select tr.id accessoriesid, tr.accessoriespath, m.process_instance_id  processInstanceId ,m.sheet_id  sheetId ,m.theme  theme , m.themeinterface  themeinterface , k.task_def_key_  taskdefkey ,tr.accessoriescnname  accessoriescnname ,m.city  city ,m.country  country , m.send_time  sendTime   from pnr_act_transfer_office_main m   left join pnr_act_transfer_attachment t on m.process_instance_id = t.process_instance_id   left join taw_commons_accessories tr on t.accessories_id = tr.id   left join act_ru_task k on m.process_instance_id = k.proc_inst_id_ where 1=1 ";

		String whereSql = "";

		//开始时间
		if (conditionQueryModel.getSendStartTime() != null
				&& !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and m.send_time >=to_date('"
					+ conditionQueryModel.getSendStartTime()
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}
		//结束时间
		if (conditionQueryModel.getSendEndTime() != null
				&& !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and m.send_time <= to_date('"
					+ conditionQueryModel.getSendEndTime()
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}

		//地市
		if (conditionQueryModel.getCity() != null
				&& !conditionQueryModel.getCity().equals("")) {
			whereSql += " and m.city = '" + conditionQueryModel.getCity() + "'";

		}
		//区县
		if (conditionQueryModel.getCountry() != null
				&& !conditionQueryModel.getCountry().equals("")) {
			whereSql += " and m.country = '" + conditionQueryModel.getCountry()
					+ "'";
		}
		//当前登陆人地市
		if (conditionQueryModel.getArea() != null
				&& !conditionQueryModel.getArea().equals("")) {
			if(conditionQueryModel.getArea().trim().length()==6){
				whereSql += " and m.country = '" + conditionQueryModel.getArea()
				+ "'";
			}
			
		}
		
		//工单类型themeinterface  transferOffice抢修工单；maleGuest 公客；interface 本地网预检预修；arteryPrecheck 干线预检预修；
		//预检预修-本地网、预检预修-干线、大修改造
		//逗号分隔 '',''
		if (conditionQueryModel.getThemeinterface() != null
				&& !conditionQueryModel.getThemeinterface().equals("")) {
			whereSql += " and m.themeinterface in (" + conditionQueryModel.getThemeinterface()
					+ ")";
		}
		
		//所属环节 
		//逗号分隔 '',''
		if (conditionQueryModel.getTaskdefkey() != null
				&& !conditionQueryModel.getTaskdefkey().equals("")) {
			whereSql += " and k.task_def_key_ in (" + conditionQueryModel.getTaskdefkey()
					+ ")";
		}
		
		whereSql += " and tr.id is not null ";
		
		sql =sql + selectSql
				+ whereSql
				+ " order by m.process_instance_id) temp1 where rownum <="
				+ endResult * pageSize + ") temp2 where temp2.num > "
				+ firstResult * pageSize;
		
		System.out.println("sql======++++ " + sql);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		List<DownAttachMentModel> r = new ArrayList<DownAttachMentModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		for (Map map : list) {
			
			DownAttachMentModel model = new DownAttachMentModel();
			
			//工单号
			if (map.get("processInstanceId") != null && !"".equals(map.get("processInstanceId"))) {
				model.setProcessInstanceId(map.get("processInstanceId").toString());
			}
			//工单编号
			if (map.get("sheetId") != null && !"".equals(map.get("sheetId"))) {
				model.setSheetId(map.get("sheetId").toString());
			}
			//工单主题 项目名称
			if (map.get("theme") != null && !"".equals(map.get("theme"))) {
				model.setTheme(map.get("theme").toString());
			}
			//工单类型
			if (map.get("themeinterface") != null && !"".equals(map.get("themeinterface"))) {
				model.setThemeinterface(map.get("themeinterface").toString());
			}
			//所属环节
			if (map.get("taskdefkey") != null && !"".equals(map.get("taskdefkey"))) {
				model.setTaskdefkey(map.get("taskdefkey").toString());
			}
			//附件名称
			if (map.get("accessoriescnname") != null && !"".equals(map.get("accessoriescnname"))) {
				model.setAccessoriescnname(map.get("accessoriescnname").toString());
			}

			if (map.get("city") != null && !"".equals(map.get("city"))) {
				model.setCity(map.get("city").toString());
			}
			
			if (map.get("country") != null && !"".equals(map.get("country"))) {
				model.setCountry(map.get("country").toString());
			}
			
			if (map.get("sendTime") != null && !"".equals(map.get("sendTime"))) {
				try {
					
					model.setSendTime(format.parse(map.get("sendTime").toString()));
					
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			r.add(model);
		}
		return r;
	}

	@Override
	public List getDownAttachMentListAll(String userid,
			ConditionQueryDAMModel conditionQueryModel) {

		String sql = "";
		
		String selectSql = " select tr.id accessoriesid, tr.accessoriespath, tr.accessoriesname, tr.appcode,  m.process_instance_id  processInstanceId ,m.sheet_id  sheetId ,m.theme  theme , m.themeinterface  themeinterface , k.task_def_key_  taskdefkey ,tr.accessoriescnname  accessoriescnname ,m.city  city ,m.country  country , m.send_time  sendTime   from pnr_act_transfer_office_main m   left join pnr_act_transfer_attachment t on m.process_instance_id = t.process_instance_id   left join taw_commons_accessories tr on t.accessories_id = tr.id   left join act_ru_task k on m.process_instance_id = k.proc_inst_id_ where 1=1 ";

		String whereSql = "";

		//开始时间
		if (conditionQueryModel.getSendStartTime() != null
				&& !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and m.send_time >=to_date('"
					+ conditionQueryModel.getSendStartTime()
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}
		//结束时间
		if (conditionQueryModel.getSendEndTime() != null
				&& !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and m.send_time <= to_date('"
					+ conditionQueryModel.getSendEndTime()
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}

		//地市
		if (conditionQueryModel.getCity() != null
				&& !conditionQueryModel.getCity().equals("")) {
			whereSql += " and m.city = '" + conditionQueryModel.getCity() + "'";

		}
		//区县
		if (conditionQueryModel.getCountry() != null
				&& !conditionQueryModel.getCountry().equals("")) {
			whereSql += " and m.country = '" + conditionQueryModel.getCountry()
					+ "'";
		}
		//当前登陆人地市id
		if (conditionQueryModel.getArea() != null
				&& !conditionQueryModel.getArea().equals("")) {
			if(conditionQueryModel.getArea().trim().length()==6){
				whereSql += " and m.country = '" + conditionQueryModel.getArea()
				+ "'";
			}
			
		}
		
		//工单类型themeinterface  transferOffice抢修工单；maleGuest 公客；interface 本地网预检预修；arteryPrecheck 干线预检预修；
		//预检预修-本地网、预检预修-干线、大修改造
		//逗号分隔 '',''
		if (conditionQueryModel.getThemeinterface() != null
				&& !conditionQueryModel.getThemeinterface().equals("")) {
			whereSql += " and m.themeinterface in (" + conditionQueryModel.getThemeinterface()
					+ ")";
		}
		
		//所属环节 
		//逗号分隔 '',''
		if (conditionQueryModel.getTaskdefkey() != null
				&& !conditionQueryModel.getTaskdefkey().equals("")) {
			whereSql += " and k.task_def_key_ in (" + conditionQueryModel.getTaskdefkey()
					+ ")";
		}
		
		whereSql += " and tr.id is not null ";
		
		sql =sql + selectSql
				+ whereSql;
		
		System.out.println("sql======++++ " + sql);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		List<DownAttachMentModel> r = new ArrayList<DownAttachMentModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		for (Map map : list) {
			
			DownAttachMentModel model = new DownAttachMentModel();
			
			//工单号
			if (map.get("processInstanceId") != null && !"".equals(map.get("processInstanceId"))) {
				model.setProcessInstanceId(map.get("processInstanceId").toString());
			}
			//工单编号
			if (map.get("sheetId") != null && !"".equals(map.get("sheetId"))) {
				model.setSheetId(map.get("sheetId").toString());
			}
			//工单主题 项目名称
			if (map.get("theme") != null && !"".equals(map.get("theme"))) {
				model.setTheme(map.get("theme").toString());
			}
			//工单类型
			if (map.get("themeinterface") != null && !"".equals(map.get("themeinterface"))) {
				model.setThemeinterface(map.get("themeinterface").toString());
			}
			//所属环节
			if (map.get("taskdefkey") != null && !"".equals(map.get("taskdefkey"))) {
				model.setTaskdefkey(map.get("taskdefkey").toString());
			}
			//附件名称
			if (map.get("accessoriescnname") != null && !"".equals(map.get("accessoriescnname"))) {
				model.setAccessoriescnname(map.get("accessoriescnname").toString());
			}

			if (map.get("city") != null && !"".equals(map.get("city"))) {
				model.setCity(map.get("city").toString());
			}
			
			if (map.get("country") != null && !"".equals(map.get("country"))) {
				model.setCountry(map.get("country").toString());
			}
			
			if (map.get("sendTime") != null && !"".equals(map.get("sendTime"))) {
				try {
					
					model.setSendTime(format.parse(map.get("sendTime").toString()));
					
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			if (map.get("accessoriesid") != null && !"".equals(map.get("accessoriesid"))) {
				model.setAccessoriesid(map.get("accessoriesid").toString());
			}
			
			if (map.get("accessoriespath") != null && !"".equals(map.get("accessoriespath"))) {
				model.setAccessoriespath(map.get("accessoriespath").toString());
			}
			
			if (map.get("accessoriespath") != null && !"".equals(map.get("accessoriespath"))) {
				model.setAccessoriespath(map.get("accessoriespath").toString());
			}
			
			//accessoriesname
			if (map.get("accessoriesname") != null && !"".equals(map.get("accessoriesname"))) {
				model.setAccessoriesname(map.get("accessoriesname").toString());
			}
			
			if (map.get("appcode") != null && !"".equals(map.get("appcode"))) {
				model.setAppcode(map.get("appcode").toString());
			}
			
			r.add(model);
			
		}		
		
		return r;
	}

	private int getDownAttachMentLimitNum() {
		String sql = "select value,code,groupname from JL_DICCONFIG  where groupname = 'downattachment'";
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		String limitnum="";

		if (list != null && list.size() > 0) {
			limitnum = list.get(0).get("value") == null ? null : list.get(0)
					.get("value").toString();
		}
		
		return Integer.parseInt(limitnum);
	}
	private int getDownAttachMentCurrUserCount() {
		String sql = "select count(*) usercount from jl_downattachinfo where downtime > sysdate - interval '3' MINUTE";//3分钟内正在下载附件人数
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		String usercount="";

		if (list != null && list.size() > 0) {
			usercount = list.get(0).get("usercount") == null ? null : list.get(0)
					.get("usercount").toString();
		}
		
		return Integer.parseInt(usercount);
	}
	private void deleteDownAttachMentInfo() {
		String sql = " delete from jl_downattachinfo where downtime < sysdate - interval '1' hour";
        getJdbcTemplate().execute(sql);
	}
	
	/**
	 * 是否可以下载附件
	 */
	@Override
	public Boolean IsEnableDownAttachMent(String userid) {

		//deleteDownAttachMentInfo();
		
		int limitnum =getDownAttachMentLimitNum();
		
		int usercount =getDownAttachMentCurrUserCount();
		
		if (usercount>=limitnum)
			return false;
		else 
			return true;
	}

	/**
	 * 增加附件下载记录
	 * @param id
	 * @param userid
	 * @return
	 */
	@Override
	public void addDownAttachMentInfo(String id, String userid) {
		String sql = " insert into jl_downattachinfo(id,userid,downtime) values('"+id+"','"+userid+"', sysdate)";
        getJdbcTemplate().execute(sql);
	}

	/**
	 * 删除附件下载记录
	 * @param id
	 * @return
	 */
	@Override
	public void deleteDownAttachMentInfo(String id) {
		String sql = " delete from jl_downattachinfo where id ='"+id+"'";
        getJdbcTemplate().execute(sql);
	}

	
	/**
	 * add by wangchang 20150507
	 * 获取下载工单个数
	 */
	@Override
	public int getDownWorkOrderCount(String userid,
			ConditionQueryDAMModel conditionQueryModel) {
		String sql = "";
		String selectSql = "select count(*) total from pnr_act_transfer_office_main pm  left join act_ru_task kt on pm.process_instance_id   = kt.proc_inst_id_ where pm.themeinterface in ('interface','arteryPrecheck','overhaul','reform')";

		String whereSql = "";

		//开始时间
		if (conditionQueryModel.getSendStartTime() != null
				&& !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and pm.send_time >=to_date('"
					+ conditionQueryModel.getSendStartTime()
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}
		//结束时间
		if (conditionQueryModel.getSendEndTime() != null
				&& !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and pm.send_time <= to_date('"
					+ conditionQueryModel.getSendEndTime()
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}
		
		//地市
		if (conditionQueryModel.getCity() != null
				&& !conditionQueryModel.getCity().equals("")) {
			whereSql += " and pm.city = '" + conditionQueryModel.getCity() + "'";

		}
		//区县
		if (conditionQueryModel.getCountry() != null
				&& !conditionQueryModel.getCountry().equals("")) {
			whereSql += " and pm.country = '" + conditionQueryModel.getCountry()
					+ "'";
		}
		//根据当前登录人地市id查询列表
		if (conditionQueryModel.getArea() != null
				&& !conditionQueryModel.getArea().equals("")) {
			if(conditionQueryModel.getArea().trim().length()==6){//如果长度等于6只查询本区县的
				whereSql += " and pm.country = '" + conditionQueryModel.getArea()
				+ "'";
			}
			
		}
		//工单类型themeinterface  transferOffice抢修工单；maleGuest 公客；interface 本地网预检预修；arteryPrecheck 干线预检预修；
		//预检预修-本地网、预检预修-干线、大修改造
		//逗号分隔 '',''
		if (conditionQueryModel.getThemeinterface() != null
				&& !conditionQueryModel.getThemeinterface().equals("")) {
			whereSql += " and pm.themeinterface in (" + conditionQueryModel.getThemeinterface()
					+ ")";
		}
		
		//所属环节 
		//逗号分隔 '',''
		if (conditionQueryModel.getTaskdefkey() != null
				&& !conditionQueryModel.getTaskdefkey().equals("")) {
			whereSql += " and kt.task_def_key_ in (" + conditionQueryModel.getTaskdefkey()
					+ ")";
		}

		sql = selectSql + whereSql;

		System.out.println("--------------下载附件sql=" + sql);

		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		return Integer.parseInt(list.get(0).get("total").toString());

	}
	
	/**
	 * 获取工单列表
	 */
	@Override
	public List<DownWorkOrderModel> getDownWorkOrderList(String userid,
			ConditionQueryDAMModel conditionQueryModel, int firstResult,
			int endResult, int pageSize) {
		
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		
		String selectSql = " select pm.send_time sendTime,pm.themeinterface themeinterface,kt.task_def_key_  taskDefkey,pm.city city,pm.country country, pm.process_instance_id processInstanceId,pm.sheet_id sheetId,pm.theme theme,pm.bear_service bearService,pm.workorder_type_name workOrderType,pm.sub_type_name subType,pm.key_word keyWord,pm.work_order_degree workOrderDegree,pm.fault_description faultDescription,pm.project_amount projectAmount from pnr_act_transfer_office_main pm  left join act_ru_task kt on pm.process_instance_id   = kt.proc_inst_id_ where pm.themeinterface in ('interface','arteryPrecheck','overhaul','reform')";

		String whereSql = "";

		//开始时间
		if (conditionQueryModel.getSendStartTime() != null
				&& !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and pm.send_time >=to_date('"
					+ conditionQueryModel.getSendStartTime()
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}
		//结束时间
		if (conditionQueryModel.getSendEndTime() != null
				&& !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and pm.send_time <= to_date('"
					+ conditionQueryModel.getSendEndTime()
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}
		
		//地市
		if (conditionQueryModel.getCity() != null
				&& !conditionQueryModel.getCity().equals("")) {
			whereSql += " and pm.city = '" + conditionQueryModel.getCity() + "'";

		}
		//区县
		if (conditionQueryModel.getCountry() != null
				&& !conditionQueryModel.getCountry().equals("")) {
			whereSql += " and pm.country = '" + conditionQueryModel.getCountry()
					+ "'";
		}
		//根据当前登录人地市id查询列表
		if (conditionQueryModel.getArea() != null
				&& !conditionQueryModel.getArea().equals("")) {
			if(conditionQueryModel.getArea().trim().length()==6){//如果长度等于6只查询本区县的
				whereSql += " and pm.country = '" + conditionQueryModel.getArea()
				+ "'";
			}
			
		}
		
		//工单类型themeinterface  transferOffice抢修工单；maleGuest 公客；interface 本地网预检预修；arteryPrecheck 干线预检预修；
		//预检预修-本地网、预检预修-干线、大修改造
		//逗号分隔 '',''
		if (conditionQueryModel.getThemeinterface() != null
				&& !conditionQueryModel.getThemeinterface().equals("")) {
			whereSql += " and pm.themeinterface in (" + conditionQueryModel.getThemeinterface()
					+ ")";
		}
		
		//所属环节 
		//逗号分隔 '',''
		if (conditionQueryModel.getTaskdefkey() != null
				&& !conditionQueryModel.getTaskdefkey().equals("")) {
			whereSql += " and kt.task_def_key_ in (" + conditionQueryModel.getTaskdefkey()
					+ ")";
		}
		
		sql =sql + selectSql
		+ whereSql
		+ " order by pm.process_instance_id) temp1 where rownum <="
		+ endResult * pageSize + ") temp2 where temp2.num > "
		+ firstResult * pageSize;
		
		System.out.println("sql======++++ " + sql);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		List<DownWorkOrderModel> r = new ArrayList<DownWorkOrderModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		for (Map map : list) {
			
			DownWorkOrderModel model = new DownWorkOrderModel();
		    //工单号
			if ( !"".equals(map.get("processInstanceId")) && map.get("processInstanceId") != null) {
				model.setProcessInstanceId(map.get("processInstanceId").toString());
			}
			//项目编号
			if (!"".equals(map.get("sheetId")) && map.get("sheetId") != null ) {
				model.setSheetId(map.get("sheetId").toString());
			}
			//工单主题 项目名称
			if ( !"".equals(map.get("theme")) && map.get("theme") != null ) {
				model.setTheme(map.get("theme").toString());
			}
			//工单类型
			if ( !"".equals(map.get("themeinterface")) && map.get("themeinterface") != null) {
				model.setThemeinterface(map.get("themeinterface").toString());
			}
			//所属环节
			if (!"".equals(map.get("taskDefkey")) && map.get("taskDefkey") != null ) {
				model.setTaskDefkey(map.get("taskDefkey").toString());
			}
			// 承载业务级别
			if( !"".equals(map.get("bearService")) && map.get("bearService") !=null ){
				model.setBearService(map.get("bearService").toString());
			}
			// 工单类型
			 if(!"".equals(map.get("workOrderType"))  && map.get("workOrderType") !=null ){
				 model.setWorkOrderType(map.get("workOrderType").toString());
			 }
			// 工单子类型 	
			 if(!"".equals(map.get("subType"))  && map.get("subType") !=null ){
				 model.setSubType(map.get("subType").toString());
			 }
			// 关键字
			 if(!"".equals(map.get("keyWord"))  && map.get("keyWord") !=null ){
				 model.setKeyWord(map.get("keyWord").toString());
			 }
			//紧急程度
			 if(!"".equals(map.get("workOrderDegree"))  && map.get("workOrderDegree") !=null ){
				 model.setWorkOrderDegree(map.get("workOrderDegree").toString());
			 }
			//项目实施内容描述
			 if(!"".equals(map.get("faultDescription"))  && map.get("faultDescription") !=null ){
				 model.setFaultDescription(map.get("faultDescription").toString());
			 }
			// 项目金额（元）
			 if(!"".equals(map.get("projectAmount"))  && map.get("projectAmount") !=null ){
				 model.setProjectAmount(map.get("projectAmount").toString());
			 }
			 
			if ( !"".equals(map.get("city")) && map.get("city") != null) {
				model.setCity(map.get("city").toString());
			}
			
			if (!"".equals(map.get("country")) && map.get("country") != null ) {
				model.setCountry(map.get("country").toString());
			}
			
			if ( !"".equals(map.get("sendTime")) && map.get("sendTime") != null ) {
				try {
					
					model.setSendTime(format.parse(map.get("sendTime").toString()));
					
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			r.add(model);
		}
		return r;
	}
	
	/**
	 * 获取工单列表
	 * 
	 */
	public List getDownWorkOrderListAll(String userid, 
			ConditionQueryDAMModel conditionQueryModel){
		
		String sql = "";
		
		String selectSql = " select pm.send_time sendTime,pm.themeinterface themeinterface,kt.task_def_key_  taskDefkey,pm.city city,pm.country country, pm.process_instance_id processInstanceId,pm.sheet_id sheetId,pm.theme theme,pm.bear_service bearService,pm.work_order_type workOrderType,pm.sub_type subType,pm.key_word keyWord,pm.work_order_degree workOrderDegree,pm.fault_description faultDescription,pm.project_amount projectAmount from pnr_act_transfer_office_main pm  left join act_ru_task kt on pm.process_instance_id   = kt.proc_inst_id_ where pm.themeinterface in ('interface','arteryPrecheck','overhaul','reform')";

		String whereSql = "";

		//开始时间
		if (conditionQueryModel.getSendStartTime() != null
				&& !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and pm.send_time >=to_date('"
					+ conditionQueryModel.getSendStartTime()
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}
		//结束时间
		if (conditionQueryModel.getSendEndTime() != null
				&& !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and pm.send_time <= to_date('"
					+ conditionQueryModel.getSendEndTime()
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}

		
		//工单类型themeinterface  transferOffice抢修工单；maleGuest 公客；interface 本地网预检预修；arteryPrecheck 干线预检预修；
		//预检预修-本地网、预检预修-干线、大修改造
		//逗号分隔 '',''
		if (conditionQueryModel.getThemeinterface() != null
				&& !conditionQueryModel.getThemeinterface().equals("")) {
			whereSql += " and pm.themeinterface in (" + conditionQueryModel.getThemeinterface()
					+ ")";
		}
		
		//所属环节 
		//逗号分隔 '',''
		if (conditionQueryModel.getTaskdefkey() != null
				&& !conditionQueryModel.getTaskdefkey().equals("")) {
			whereSql += " and kt.task_def_key_ in (" + conditionQueryModel.getTaskdefkey()
					+ ")";
		}
		
		sql =sql + selectSql+ whereSql;
				
		
		System.out.println("sql======++++ " + sql);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		List<DownWorkOrderModel> r = new ArrayList<DownWorkOrderModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		for (Map map : list) {
			
			DownWorkOrderModel model = new DownWorkOrderModel();
		    //工单号
			if ( !"".equals(map.get("processInstanceId")) && map.get("processInstanceId") != null) {
				model.setProcessInstanceId(map.get("processInstanceId").toString());
			}
			//项目编号
			if (!"".equals(map.get("sheetId")) && map.get("sheetId") != null ) {
				model.setSheetId(map.get("sheetId").toString());
			}
			//工单主题 项目名称
			if ( !"".equals(map.get("theme")) && map.get("theme") != null ) {
				model.setTheme(map.get("theme").toString());
			}
			//工单类型
			if ( !"".equals(map.get("themeinterface")) && map.get("themeinterface") != null) {
				model.setThemeinterface(map.get("themeinterface").toString());
			}
			//所属环节
			if (!"".equals(map.get("taskDefkey")) && map.get("taskDefkey") != null ) {
				model.setTaskDefkey(map.get("taskDefkey").toString());
			}
			// 承载业务级别
			if( !"".equals(map.get("bearService")) && map.get("bearService") !=null ){
				model.setBearService(map.get("bearService").toString());
			}
			// 工单类型
			 if(!"".equals(map.get("workOrderType"))  && map.get("workOrderType") !=null ){
				 model.setWorkOrderType(map.get("workOrderType").toString());
			 }
			// 工单子类型 	
			 if(!"".equals(map.get("subType"))  && map.get("subType") !=null ){
				 model.setSubType(map.get("subType").toString());
			 }
			// 关键字
			 if(!"".equals(map.get("keyWord"))  && map.get("keyWord") !=null ){
				 model.setKeyWord(map.get("keyWord").toString());
			 }
			//紧急程度
			 if(!"".equals(map.get("workOrderDegree"))  && map.get("workOrderDegree") !=null ){
				 model.setWorkOrderDegree(map.get("workOrderDegree").toString());
			 }
			//项目实施内容描述
			 if(!"".equals(map.get("faultDescription"))  && map.get("faultDescription") !=null ){
				 model.setFaultDescription(map.get("faultDescription").toString());
			 }
			// 项目金额（元）
			 if(!"".equals(map.get("projectAmount"))  && map.get("projectAmount") !=null ){
				 model.setProjectAmount(map.get("projectAmount").toString());
			 }
			 
			if ( !"".equals(map.get("city")) && map.get("city") != null) {
				model.setCity(map.get("city").toString());
			}
			
			if (!"".equals(map.get("country")) && map.get("country") != null ) {
				model.setCountry(map.get("country").toString());
			}
			
			if ( !"".equals(map.get("sendTime")) && map.get("sendTime") != null ) {
				try {
					
					model.setSendTime(format.parse(map.get("sendTime").toString()));
					
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			r.add(model);
		}
		return r;
	}
	
	/**
	 * add by wangchang 20150507
	 * 获取下载附件个数
	 */
	public int getDownImageImportCount(String userid,
			ConditionQueryDAMModel conditionQueryModel) {
		String sql = "";
		String selectSql = "select count(*) total from pnr_act_transfer_office_main m   left join pnr_act_transfer_attachment t on m.process_instance_id = t.process_instance_id   left join taw_commons_accessories tr on t.accessories_id = tr.id   left join act_ru_task k on m.process_instance_id = k.proc_inst_id_ where 1=1 ";

		String whereSql = "";

		//开始时间
		if (conditionQueryModel.getSendStartTime() != null
				&& !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and m.send_time >=to_date('"
					+ conditionQueryModel.getSendStartTime()
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}
		//结束时间
		if (conditionQueryModel.getSendEndTime() != null
				&& !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and m.send_time <= to_date('"
					+ conditionQueryModel.getSendEndTime()
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}

		//地市
		if (conditionQueryModel.getCity() != null
				&& !conditionQueryModel.getCity().equals("")) {
			whereSql += " and m.city = '" + conditionQueryModel.getCity() + "'";

		}
		//区县
		if (conditionQueryModel.getCountry() != null
				&& !conditionQueryModel.getCountry().equals("")) {
			whereSql += " and m.country = '" + conditionQueryModel.getCountry()
					+ "'";
		}
		
		//工单类型themeinterface  transferOffice抢修工单；maleGuest 公客；interface 本地网预检预修；arteryPrecheck 干线预检预修；
		//预检预修-本地网、预检预修-干线、大修改造
		//逗号分隔 '',''
		if (conditionQueryModel.getThemeinterface() != null
				&& !conditionQueryModel.getThemeinterface().equals("")) {
			whereSql += " and m.themeinterface in (" + conditionQueryModel.getThemeinterface()
					+ ")";
		}
		
		//所属环节 
		//逗号分隔 '',''
		if (conditionQueryModel.getTaskdefkey() != null
				&& !conditionQueryModel.getTaskdefkey().equals("")) {
			whereSql += " and k.task_def_key_ in (" + conditionQueryModel.getTaskdefkey()
					+ ")";
		}

		sql = selectSql + whereSql;

		System.out.println("--------------下载附件sql=" + sql);

		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		return Integer.parseInt(list.get(0).get("total").toString());

	}
	
	/**
	 * 图片附件下载
	 */
	public List<DownAttachMentModel> getDownImageImportList(String userid,
			ConditionQueryDAMModel conditionQueryModel, int firstResult,
			int endResult, int pageSize) {
		
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		
		String selectSql = " select tr.id accessoriesid, tr.accessoriespath, m.process_instance_id  processInstanceId ,m.sheet_id  sheetId ,m.theme  theme , m.themeinterface  themeinterface , k.task_def_key_  taskdefkey ,tr.accessoriescnname  accessoriescnname ,m.city  city ,m.country  country , m.send_time  sendTime   from pnr_act_transfer_office_main m   left join pnr_act_transfer_attachment t on m.process_instance_id = t.process_instance_id   left join taw_commons_accessories tr on t.accessories_id = tr.id   left join act_ru_task k on m.process_instance_id = k.proc_inst_id_ where 1=1 ";

		String whereSql = "";

		//开始时间
		if (conditionQueryModel.getSendStartTime() != null
				&& !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and m.send_time >=to_date('"
					+ conditionQueryModel.getSendStartTime()
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}
		//结束时间
		if (conditionQueryModel.getSendEndTime() != null
				&& !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and m.send_time <= to_date('"
					+ conditionQueryModel.getSendEndTime()
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}

		//地市
		if (conditionQueryModel.getCity() != null
				&& !conditionQueryModel.getCity().equals("")) {
			whereSql += " and m.city = '" + conditionQueryModel.getCity() + "'";

		}
		//区县
		if (conditionQueryModel.getCountry() != null
				&& !conditionQueryModel.getCountry().equals("")) {
			whereSql += " and m.country = '" + conditionQueryModel.getCountry()
					+ "'";
		}
		
		//工单类型themeinterface  transferOffice抢修工单；maleGuest 公客；interface 本地网预检预修；arteryPrecheck 干线预检预修；
		//预检预修-本地网、预检预修-干线、大修改造
		//逗号分隔 '',''
		if (conditionQueryModel.getThemeinterface() != null
				&& !conditionQueryModel.getThemeinterface().equals("")) {
			whereSql += " and m.themeinterface in (" + conditionQueryModel.getThemeinterface()
					+ ")";
		}
		
		//所属环节 
		//逗号分隔 '',''
		if (conditionQueryModel.getTaskdefkey() != null
				&& !conditionQueryModel.getTaskdefkey().equals("")) {
			whereSql += " and k.task_def_key_ in (" + conditionQueryModel.getTaskdefkey()
					+ ")";
		}
		
		whereSql += " and tr.id is not null ";
		
		sql =sql + selectSql
				+ whereSql
				+ " order by to_number(m.process_instance_id)) temp1 where rownum <="
				+ endResult * pageSize + ") temp2 where temp2.num > "
				+ firstResult * pageSize;
		
		System.out.println("sql======++++ " + sql);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		List<DownAttachMentModel> r = new ArrayList<DownAttachMentModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		for (Map map : list) {
			
			DownAttachMentModel model = new DownAttachMentModel();
			
			//工单号
			if (map.get("processInstanceId") != null && !"".equals(map.get("processInstanceId"))) {
				model.setProcessInstanceId(map.get("processInstanceId").toString());
			}
			//工单编号
			if (map.get("sheetId") != null && !"".equals(map.get("sheetId"))) {
				model.setSheetId(map.get("sheetId").toString());
			}
			//工单主题 项目名称
			if (map.get("theme") != null && !"".equals(map.get("theme"))) {
				model.setTheme(map.get("theme").toString());
			}
			//工单类型
			if (map.get("themeinterface") != null && !"".equals(map.get("themeinterface"))) {
				model.setThemeinterface(map.get("themeinterface").toString());
			}
			//所属环节
			if (map.get("taskdefkey") != null && !"".equals(map.get("taskdefkey"))) {
				model.setTaskdefkey(map.get("taskdefkey").toString());
			}
			//附件名称
			if (map.get("accessoriescnname") != null && !"".equals(map.get("accessoriescnname"))) {
				model.setAccessoriescnname(map.get("accessoriescnname").toString());
			}

			if (map.get("city") != null && !"".equals(map.get("city"))) {
				model.setCity(map.get("city").toString());
			}
			
			if (map.get("country") != null && !"".equals(map.get("country"))) {
				model.setCountry(map.get("country").toString());
			}
			
			if (map.get("sendTime") != null && !"".equals(map.get("sendTime"))) {
				try {
					
					model.setSendTime(format.parse(map.get("sendTime").toString()));
					
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			r.add(model);
		}
		return r;
	}
	
	
	@Override
	public List<RoomAssets> showAssetsByProcessInstanceId(
			String processInstanceId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		StringBuilder sql = new StringBuilder("select ta.areaname,p.station_name,td.dictname,ra.*,decode(pr.exit_direction,1270502,'闲置-可利用',1270501,'闲置-待报废') as direction");
		sql.append("  from pnr_act_room_assets_relation pr");
		sql.append("  left join room_demolition_assets ra");
		sql.append("  on pr.room_assets_id = ra.id");
		sql.append("  left join pnr_act_roomdemolition p ");
		sql.append("  on p.process_instance_id=pr.process_instance_id");
		sql.append("  left join taw_system_area ta");
		sql.append("  on ta.areaid = p.city");
		sql.append("  left join taw_system_dicttype td");
		sql.append("  on td.dictid=p.station_type");
		sql.append("   where pr.process_instance_id = '").append(processInstanceId).append("'  order by pr.order_code asc");
		
		/*String sql= " select ra.* from pnr_act_room_assets_relation pr" +
					" left join  room_demolition_assets ra " +
					" on pr.room_assets_id = ra.id" +
					" where pr.process_instance_id='"+processInstanceId+"'";*/
		System.out.println("ssssssssssssssss"+sql.toString());
		List<Map> list = this.getJdbcTemplate().queryForList(sql.toString());
		List<RoomAssets> assetsList = new ArrayList<RoomAssets>();
		if(list!=null && list.size()>0){
			for(Map map:list){
				RoomAssets assets = new RoomAssets();
				//地市
				assets.setCity(map.get("areaname")==null?"":map.get("areaname").toString());
				//机房名称
				assets.setRoomName(map.get("station_name")==null?"":map.get("station_name").toString());
				//机房类型
				assets.setRoomType(map.get("dictname")==null?"":map.get("dictname").toString());
				//资产名称
				assets.setAssetName(map.get("assets_name")==null?"":map.get("assets_name").toString());
				//资产标签号
				assets.setAssetNumbers(map.get("assets_number")==null?"":map.get("assets_number").toString());
				//资产类别
				assets.setAssetsSort(map.get("assests_sort")==null?"":map.get("assests_sort").toString());
				//设备型号
				assets.setModelVersion(map.get("model_version")==null?"":map.get("model_version").toString());
				//资产启用日期
				String assetsStartDate = map.get("assets_start_date")==null?"":map.get("assets_start_date").toString();
				if(!"".equals(assetsStartDate)){
					try {
						assets.setAssetsStartTime(sdf.parse(assetsStartDate));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//资产使用月数（月）
				String assetsMonthNum = map.get("assets_month_num")==null?"":map.get("assets_month_num").toString();
				if(!"".equals(assetsMonthNum)){
					assets.setAssetsMonthNum(Integer.parseInt(assetsMonthNum));
				}
				//原值
				assets.setOriginalValue(map.get("original_value")==null?"":map.get("original_value").toString());
				//累计折旧
				assets.setAccumulatedDepreciation(map.get("accumulated_depreciation")==null?"":map.get("accumulated_depreciation").toString());
				//资产净值
				assets.setAssetsNet(map.get("assets_net")==null?"":map.get("assets_net").toString());
				//累计减值准备
				assets.setAssetsDevalue(map.get("assets_devalue")==null?"":map.get("assets_devalue").toString());
				//退网使用方向
				assets.setDirection(map.get("direction")==null?"":map.get("direction").toString());
				assetsList.add(assets);
			}
		}
		
		return assetsList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PnrOltBbuOfficeMainModel> getOltBbuWorkOrderList(String areaid,String userid,
			String flag, String processKey,
			ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize) {
		ArrayList list1 = new ArrayList();
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		String selectSql = "	select w.*, r.*,nvl(tal.total_fee, '0') as totalFee, nvl(tal.total_material_cost, '0') as totalMaterialCost "+
                  		   "	from (select m.*,decode(m.sheet_id, null, '无', m.sheet_id) as sheetId, "+
                           "     case 										" +
                           "		  when m.State = '8' then 'waitingCallInterface' "+ 
                           //"      	  when s.id is not null then 'spotChecks'		 "+
                           "      else to_char(t.task_def_key_) end as TaskDefKey, " +
                           "	  case 				"+
                           "      	   when m.State = '8' then  '省运维主管审批通过-等待商城' "+
                           //"      	   when s.id is not null then '已验收'				"+
                           "      else				"+
                           "       to_char(t.name_)			"+
                           "    end as StatusName	"+
                           "from pnr_act_transfer_office_main m,	"+
                           "    (select RES.*,d.key_		"+
                           "       from ACT_RU_TASK RES				"+
                           "     inner join ACT_RE_PROCDEF D		"+
                           "         on RES.PROC_DEF_ID_ = D.ID_	"+
                           "      WHERE D.KEY_ = ?) t,"+
                           "    PNR_TRANSFER_SPOTCHECK s			"+
                         "where m.process_instance_id = t.proc_inst_id_	"+
                         "  and m.process_instance_id =						"+
                         "      s.process_instance_id(+)					"+
                         "  and m.themeinterface = '"+processKey+"'	"+
                         "  and m.state != '1'								"+
                         "  and m.state != '5'								"+
                         "  union all										"+
                         "	select m.*,										"+
                         "      decode(m.sheet_id, null, '无', m.sheet_id) as sheetId,	"+
                         "      'archive' as TaskDefKey,								"+
                         "      '已归档' as StatusName									"+
                         "  from pnr_act_transfer_office_main m							"+
                         "  where m.state = '5'											"+
                         "  and m.themeinterface = '"+processKey+"'	"+
	                     "   union all													"+
	                     "   select m.*,												"+
	                     "         decode(m.sheet_id, null, '无', m.sheet_id) as sheetId,"+
	                     "         'delete' as TaskDefKey,								"+
	                     "         '已删除' as StatusName									"+           
	                     "     from pnr_act_transfer_office_main m						"+
	                     "    where m.state = '1'										"+
	                     "      and m.themeinterface = '"+processKey+"') w,				"+
	                     "  (select ma.process_instance_id,								"+
	                     "          sum(nvl(ma.cost_work, '0')) +						"+
	                     "          sum(nvl(ma.cost_no_material, '0')) as total_fee,	"+
	                     "          sum(nvl(ma.material_cost, '0')) as total_material_cost "+
	                     "    from maste_rel_task_item ma								"+
	                     "    group by ma.process_instance_id) tal,						"+
	                     "   (select 													"+
	                     " nvl(room.olt_number, '0') as olt_number,nvl(room.bbu_number, '0') as bbu_number,nvl(room.total_user_number, '0') as total_user_number,"+
	                     " nvl(room.voice_user, '0') as voice_user,nvl(room.broadband_user, '0') as broadband_user, nvl(room.iptv_user, '0') as iptv_user,"+
	                     " re.is_need_rod_investment,re.is_need_cable_investment,re.new_built_rod_length,re.new_built_rod_investment,"+
	                     " re.original_cable_route,re.new_cable_route,re.cable_cloth_core_number, re.cable_cloth_length,"+
	                     " re.cable_investment, re.line_total_investment, re.board_demand,re.light_module_demand,"+
	                     " re.equipment_investment,re.new_paragraph,re.process_instance_id as pid "+
	                     "     from pnr_act_transfer_office_main main, "+
	                     "          pnr_olt_bbu_room             room,"+
	                     "          pnr_olt_bbu_office_relation  re "+
	                     "    where main.process_instance_id =		"+
	                     "          re.process_instance_id			"+
	                     "      and re.room_id = room.id) r 		"+
	                 "where w.process_instance_id = tal.process_instance_id(+)	"+
	                 "  and w.process_instance_id = r.pid						"+
	                 "  and w.city is not null									"+
	                 "  and w.country is not null								"+
	                 "  and w.state <> 2";				
		
		list1.add(processKey);	
	
		
		String whereSql = "";
		if(areaid!=null && !"".equals(areaid)){
			if((StaticMethod.nullObject2String(areaid)).length()==2){
				
			}else if((StaticMethod.nullObject2String(areaid)).length()==4){
				whereSql += " and w.city = ? ";
				list1.add(areaid);
			}else if((StaticMethod.nullObject2String(areaid)).length()==6){
				whereSql += " and w.country= ? ";
				list1.add(areaid);
			}
		}
		if (conditionQueryModel.getSendStartTime() != null
				&& !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and to_char(w.send_time,'yyyy-mm-dd hh24:mi:ss') >= ?";
			list1.add(conditionQueryModel.getSendStartTime());
		}
		if (conditionQueryModel.getSendEndTime() != null
				&& !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and to_char(w.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?";
			list1.add(conditionQueryModel.getSendEndTime());
		}
		if (conditionQueryModel.getWorkNumber() != null
				&& !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and w.process_instance_id = ?";
			list1.add(conditionQueryModel.getWorkNumber().trim());
		}
		if (conditionQueryModel.getTheme() != null
				&& !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and instr(w.theme,?)>0";
			list1.add(conditionQueryModel.getTheme().trim());
		}
		if (conditionQueryModel.getStatus() != null
				&& !conditionQueryModel.getStatus().equals("")) {
			whereSql += " and w.TaskDefKey = ?";
			list1.add(conditionQueryModel.getStatus());
		}
		if (conditionQueryModel.getCity() != null
				&& !conditionQueryModel.getCity().equals("")) {
			whereSql += " and w.city = ?";
			list1.add(conditionQueryModel.getCity());

		}
		if (conditionQueryModel.getCountry() != null
				&& !conditionQueryModel.getCountry().equals("")) {
			whereSql += " and w.country = ?";
			list1.add(conditionQueryModel.getCountry());
		}
		if (conditionQueryModel.getJobOrderType() != null
				&& !conditionQueryModel.getJobOrderType().equals("")) {
			whereSql += " and w.job_order_type = ?";
			list1.add(conditionQueryModel.getJobOrderType());

		}
		if (conditionQueryModel.getBatch() != null
				&& !conditionQueryModel.getBatch().equals("")) {
			whereSql += " and w.batch = ?";
			list1.add(conditionQueryModel.getBatch());
		}
		if (conditionQueryModel.getPrecheckFlag() != null
				&& !conditionQueryModel.getPrecheckFlag().equals("")) {
			whereSql += " and w.precheck_flag = ?";
			list1.add(conditionQueryModel.getPrecheckFlag());
		}

		sql += selectSql + whereSql
		+ " order by w.send_time ) temp1 where rownum <= ?) temp2 where temp2.num > ?";
		
		list1.add(endResult * pageSize);
		list1.add(firstResult * pageSize);
		
		System.out.println("olt-bbu工单查询明细sql=" + sql);
		Object[] args = list1.toArray();
		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<PnrOltBbuOfficeMainModel> r = new ArrayList<PnrOltBbuOfficeMainModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,args);
		for (Map map : list) {
			PnrOltBbuOfficeMainModel model = new PnrOltBbuOfficeMainModel();
			if (map.get("TaskId") != null && !"".equals(map.get("TaskId"))) {

				model.setTaskId(map.get("TaskId").toString());
			}
			if (map.get("process_instance_id") != null && !"".equals(map.get("process_instance_id"))) {

				model.setProcessInstanceId(map.get("process_instance_id").toString());
			}
			if (map.get("Theme") != null && !"".equals(map.get("Theme"))) {

				model.setTheme(map.get("Theme").toString());
			}
			if (map.get("job_order_type") != null && !"".equals(map.get("job_order_type"))) {

				model.setJobOrderType(map.get("job_order_type").toString());
			}
			if (map.get("batch") != null && !"".equals(map.get("batch"))) {

				model.setBatch(map.get("batch").toString());
			}
			if (map.get("olt_number") != null && !"".equals(map.get("olt_number"))) {

				model.setOltNumber(map.get("olt_number").toString());
			}
			if (map.get("bbu_number") != null && !"".equals(map.get("bbu_number"))) {

				model.setBbuNumber(map.get("bbu_number").toString());
			}
			if (map.get("total_user_number") != null && !"".equals(map.get("total_user_number"))) {

				model.setTotalUserNumber(map.get("total_user_number").toString());
			}
			if (map.get("voice_user") != null && !"".equals(map.get("voice_user"))) {

				model.setVoiceUser(map.get("voice_user").toString());
			}
			if (map.get("broadband_user") != null && !"".equals(map.get("broadband_user"))) {

				model.setBroadbandUser(map.get("broadband_user").toString());
			}
			if (map.get("iptv_user") != null && !"".equals(map.get("iptv_user"))) {

				model.setIptvUser(map.get("iptv_user").toString());
			}
			if (map.get("is_need_rod_investment") != null && !"".equals(map.get("is_need_rod_investment"))) {

				model.setIsNeedRodInvestment(map.get("is_need_rod_investment").toString());
			}
			if (map.get("is_need_cable_investment") != null && !"".equals(map.get("is_need_cable_investment"))) {

				model.setIsNeedCableInvestment(map.get("is_need_cable_investment").toString());
			}
			if (map.get("new_built_rod_length") != null && !"".equals(map.get("new_built_rod_length"))) {

				model.setNewBuiltRodLength(map.get("new_built_rod_length").toString());
			}
			if (map.get("new_built_rod_investment") != null && !"".equals(map.get("new_built_rod_investment"))) {

				model.setNewBuiltRodInvestment(map.get("new_built_rod_investment").toString());
			}
			if (map.get("original_cable_route") != null && !"".equals(map.get("original_cable_route"))) {

				model.setOriginalCableRoute(map.get("original_cable_route").toString());
			}
			if (map.get("new_cable_route") != null && !"".equals(map.get("new_cable_route"))) {

				model.setNewCableRoute(map.get("new_cable_route").toString());
			}
			if (map.get("cable_cloth_core_number") != null && !"".equals(map.get("cable_cloth_core_number"))) {

				model.setCableClothCoreNumber(map.get("cable_cloth_core_number").toString());
			}
			if (map.get("cable_cloth_length") != null && !"".equals(map.get("cable_cloth_length"))) {

				model.setCableClothLength(map.get("cable_cloth_length").toString());
			}
			if (map.get("cable_investment") != null && !"".equals(map.get("cable_investment"))) {

				model.setCableInvestment(map.get("cable_investment").toString());
			}
			if (map.get("line_total_investment") != null && !"".equals(map.get("line_total_investment"))) {

				model.setLineTotalInvestment(map.get("line_total_investment").toString());
			}
			if (map.get("board_demand") != null && !"".equals(map.get("board_demand"))) {

				model.setBoardDemand(map.get("board_demand").toString());
			}
			if (map.get("light_module_demand") != null && !"".equals(map.get("light_module_demand"))) {

				model.setLightModuleDemand(map.get("light_module_demand").toString());
			}
			if (map.get("equipment_investment") != null && !"".equals(map.get("equipment_investment"))) {

				model.setEquipmentInvestment(map.get("equipment_investment").toString());
			}
			if (map.get("sheetId") != null && !"".equals(map.get("sheetId"))) {

				model.setSheetId(map.get("sheetId").toString());
			}
			if (map.get("city") != null && !"".equals(map.get("city"))) {

				model.setCity(map.get("city").toString());
			}
			if (map.get("country") != null && !"".equals(map.get("country"))) {

				model.setCountry(map.get("country").toString());
			}
			if (map.get("new_paragraph") != null && !"".equals(map.get("new_paragraph"))) {

				model.setNewParagraph(map.get("new_paragraph").toString());
			}
			if (map.get("taskDefKey") != null && !"".equals(map.get("taskDefKey"))) {

				model.setTaskDefKey(map.get("taskDefKey").toString());
			}
			if (map.get("workorder_type_name") != null && !"".equals(map.get("workorder_type_name"))) {

				model.setWorkOrderTypeName(map.get("workorder_type_name").toString());
			}
			if (map.get("sub_type_name") != null && !"".equals(map.get("sub_type_name"))) {

				model.setSubTypeName(map.get("sub_type_name").toString());
			}
			//if (map.get("rollback_flag") != null && !"".equals(map.get("rollback_flag"))) {
				
				//model.setRollbackFlag(map.get("rollback_flag").toString());
			//}
			if (map.get("rollback_flag") != null
					&& !"".equals(map.get("rollback_flag"))) {
				model.setRollbackFlag(map.get("rollback_flag").toString());
			}
			
			if (map.get("StatusName") != null
					&& !"".equals(map.get("StatusName"))) {
				model.setStatusName(map.get("StatusName").toString());
			}
			
			
			r.add(model);
		}
		return r;
	}
	@SuppressWarnings("unchecked")
	@Override
	public int getOltBbuWorkOrderListCount(String areaid,String userid, String flag,
			String processKey, ConditionQueryModel conditionQueryModel) {
		ArrayList list1 = new ArrayList();
		String sql = "";
		
		String selectSql = "	select count(*) total "+
		   "	from (select m.*,decode(m.sheet_id, null, '无', m.sheet_id) as sheetId, "+
        "     case when m.state = '3' then 'waitOrder' " +
        "		  when m.State = '8' then 'waitingCallInterface' "+ 
        //"      	  when s.id is not null then 'spotChecks'		 "+
        "      else to_char(t.task_def_key_) end as TaskDefKey, " +
        "	  case when m.state = '3' then  '待办'				"+
        "      	   when m.State = '8' then  '省运维主管审批通过-等待商城' "+
        //"      	   when s.id is not null then '已抽查'				"+
        "      else				"+
        "       to_char(t.name_)			"+
        "    end as StatusName	"+
        "from pnr_act_transfer_office_main m,	"+
        "    (select RES.*,d.key_		"+
        "       from ACT_RU_TASK RES				"+
        "     inner join ACT_RE_PROCDEF D		"+
        "         on RES.PROC_DEF_ID_ = D.ID_	"+
        "      WHERE D.KEY_ = ?) t,"+
        "    PNR_TRANSFER_SPOTCHECK s			"+
	      "where m.process_instance_id = t.proc_inst_id_	"+
	      "  and m.process_instance_id =						"+
	      "      s.process_instance_id(+)					"+
	      "  and m.themeinterface = '"+processKey+"'"+
	      "  and m.state != '1'								"+
	      "  and m.state != '5'								"+
	      "  union	all										"+
	      "	select m.*,										"+
	      "      decode(m.sheet_id, null, '无', m.sheet_id) as sheetId,	"+
	      "      'archive' as TaskDefKey,								"+
	      "      '已归档' as StatusName									"+
	      "  from pnr_act_transfer_office_main m							"+
	      "  where m.state = '5'											"+
	      "  and m.themeinterface = '"+processKey+"'"+
	      "   union	all													"+
	      "   select m.*,												"+
	      "         decode(m.sheet_id, null, '无', m.sheet_id) as sheetId,"+
	      "         'delete' as TaskDefKey,								"+
	      "         '已删除' as StatusName									"+           
	      "     from pnr_act_transfer_office_main m						"+
	      "    where m.state = '1'										"+
	      "      and m.themeinterface = '"+processKey+"') w,				"+
	      "  (select ma.process_instance_id,								"+
	      "          sum(nvl(ma.cost_work, '0')) +						"+
	      "          sum(nvl(ma.cost_no_material, '0')) as total_fee,	"+
	      "          sum(nvl(ma.material_cost, '0')) as total_material_cost "+
	      "    from maste_rel_task_item ma								"+
	      "    group by ma.process_instance_id) tal,						"+
	      "   (select 													"+
	      " nvl(room.olt_number, '0') as olt_number,nvl(room.bbu_number, '0') as bbu_number,nvl(room.total_user_number, '0') as total_user_number,"+
	      " nvl(room.voice_user, '0') as voice_user,nvl(room.broadband_user, '0') as broadband_user, nvl(room.iptv_user, '0') as iptv_user,"+
	      " re.is_need_rod_investment,re.is_need_cable_investment,re.new_built_rod_length,re.new_built_rod_investment,"+
	      " re.original_cable_route,re.new_cable_route,re.cable_cloth_core_number, re.cable_cloth_length,"+
	      " re.cable_investment, re.line_total_investment, re.board_demand,re.light_module_demand,"+
	      " re.equipment_investment,re.new_paragraph,re.process_instance_id as pid "+
	      "     from pnr_act_transfer_office_main main, "+
	      "          pnr_olt_bbu_room             room,"+
	      "          pnr_olt_bbu_office_relation  re "+
	      "    where main.process_instance_id =		"+
	      "          re.process_instance_id			"+
	      "      and re.room_id = room.id) r 		"+
	  "where w.process_instance_id = tal.process_instance_id(+)	"+
	  "  and w.process_instance_id = r.pid						"+
	  "  and w.city is not null									"+
	  "  and w.country is not null								"+
	  "  and w.state <> 2";			
	  list1.add(processKey);	
	  
		String whereSql = "";
		
		
		if(areaid!=null && !"".equals(areaid)){
			if((StaticMethod.nullObject2String(areaid)).length()==2){
				
			}else if((StaticMethod.nullObject2String(areaid)).length()==4){
				whereSql += " and w.city = ? ";
				list1.add(areaid);
			}else if((StaticMethod.nullObject2String(areaid)).length()==6){
				whereSql += " and w.country= ? ";
				list1.add(areaid);
			}
		}
		if (conditionQueryModel.getSendStartTime() != null
				&& !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and to_char(w.send_time,'yyyy-mm-dd hh24:mi:ss') >= ?";
			list1.add(conditionQueryModel.getSendStartTime());
		}
		if (conditionQueryModel.getSendEndTime() != null
				&& !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and to_char(w.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?";
			list1.add(conditionQueryModel.getSendEndTime());
		}
		if (conditionQueryModel.getWorkNumber() != null
				&& !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and w.process_instance_id = ?";
			list1.add(conditionQueryModel.getWorkNumber().trim());
		}
		if (conditionQueryModel.getTheme() != null
				&& !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and instr(w.theme,?)>0";
			list1.add(conditionQueryModel.getTheme().trim());
		}
		if (conditionQueryModel.getStatus() != null
				&& !conditionQueryModel.getStatus().equals("")) {
			whereSql += " and w.TaskDefKey = ?";
			list1.add(conditionQueryModel.getStatus());
		}
		if (conditionQueryModel.getCity() != null
				&& !conditionQueryModel.getCity().equals("")) {
			whereSql += " and w.city = ?";
			list1.add(conditionQueryModel.getCity());

		}
		if (conditionQueryModel.getCountry() != null
				&& !conditionQueryModel.getCountry().equals("")) {
			whereSql += " and w.country = ?";
			list1.add(conditionQueryModel.getCountry());
		}
		if (conditionQueryModel.getJobOrderType() != null
				&& !conditionQueryModel.getJobOrderType().equals("")) {
			whereSql += " and w.job_order_type = ?";
			list1.add(conditionQueryModel.getJobOrderType());

		}
		if (conditionQueryModel.getBatch() != null
				&& !conditionQueryModel.getBatch().equals("")) {
			whereSql += " and w.batch = ?";
			list1.add(conditionQueryModel.getBatch());
		}
		if (conditionQueryModel.getPrecheckFlag() != null
				&& !conditionQueryModel.getPrecheckFlag().equals("")) {
			whereSql += " and w.precheck_flag = ?";
			list1.add(conditionQueryModel.getPrecheckFlag());
		}
		sql += selectSql+ whereSql;
		System.out.println("oltbbu机房拆除工单查询统计数sql=" + sql);
		Object[] args = list1.toArray();
		int total = this.getJdbcTemplate().queryForInt(sql, args);
		return total;

	}
	
	/**
	 * 根据环节的ID和工单号查询该环节的处理人
	 * 
	 * @param userTaskId	环节的userTask的Id
	 * @param processInstanceId	工单号
	 * @return
	 */
	public String getLinkProcessingUserId(String userTaskId,String processInstanceId){
		String sql="";
		String linkUserId="superUser";//找不到默认superUser,至少不丢单
		if("need".equals(userTaskId)){
			 sql="select m.initiator as linkUserId from pnr_act_transfer_office_main m where m.process_instance_id = '"+processInstanceId+"'";
		}else if("cityManageExamine".equals(userTaskId)){
			 sql="select m.city_manage_charge as linkUserId from pnr_act_transfer_office_main m where m.process_instance_id = '"+processInstanceId+"'";
		}
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		if (list != null && list.size() > 0) {
			if(list.get(0).get("linkUserId")!=null&&!"".equals(list.get(0).get("linkUserId").toString())){
				linkUserId= list.get(0).get("linkUserId").toString();
			}
		}
		return linkUserId;
	}
	
	/**
	 * 查询流程某个环节的某个月的预算金额总和
	 * 
	 * @param conditionQueryModel	查询条件
	 * @return
	 */
	public String getLinkMonthTotalProjectAmount(ConditionQueryModel conditionQueryModel){
		String sql=
			"select sum(ma.project_amount) as smountSum" +
			"  from pnr_act_transfer_office_main ma" + 
			" where ma.process_instance_id in" + 
			"       (select r.proc_inst_id_" + 
			"          from (select hi.*," + 
			"                       row_number() over(partition by hi.proc_inst_id_ order by hi.start_time_ desc) tnum" + 
			"                  from act_hi_taskinst hi" + 
			"                 where hi.proc_inst_id_ in" + 
			"                       (select m.proc_inst_id_" + 
			"                          from (select hi.*," + 
			"                                       row_number() over(partition by hi.proc_inst_id_ order by hi.start_time_ desc) tnum," + 
			"                                       tk.cur_int" + 
			"                                  from act_hi_taskinst hi" + 
			"                                  left join act_re_procdef p" + 
			"                                    on hi.proc_def_id_ = p.id_" + 
			"                                  left join "+conditionQueryModel.getTableName()+" tk" + 
			"                                    on hi.task_def_key_ = tk.key" + 
			"                                 where p.key_ = '"+conditionQueryModel.getProcessType()+"') m" + 
			"                         where m.tnum = 1" + 
			"                           and m.cur_int >= "+conditionQueryModel.getLinkSerialNumber()+")" + 
			"                   and hi.task_def_key_ = '"+conditionQueryModel.getLinkKey()+"') r" + 
			"         where r.tnum = 1" + 
			"           and to_char(r.end_time_, 'yyyy-mm') = '"+conditionQueryModel.getMonth()+"')" + 
			"   and ma.city = '"+conditionQueryModel.getCity()+"'";
		
		System.out.println("------查询流程某个环节的某个月的预算金额总和sql="+sql);
		
		String smountSum="0";//默认总金额为0
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		if (list != null && list.size() > 0) {
			if(list.get(0).get("smountSum")!=null&&!"".equals(list.get(0).get("smountSum").toString())){
				smountSum= list.get(0).get("smountSum").toString();
			}
		}
		return smountSum;
	}
	
	/**
	 * 等待接口调用提醒
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
	public List<TaskModel> workOrderRemind(String processDefinitionKey,
			String userId, String sendStartTime, String sendEndTime,
			String wsNum, String wsTitle, String status, int firstResult,
			int endResult, int pageSize) {
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		String selectSql = "select * from (select ACTI.Proc_Inst_Id_ as processInstanceId,decode(MAIN.sheet_id, null, '无', MAIN.sheet_id) as sheetId,MAIN.Theme as theme,MAIN.city ,MAIN.country,MAIN.work_type,MAIN.workorder_type_name,nvl(MAIN.is_remind,0) as remind,MAIN.state,MAIN.sub_type_name ,MAIN.key_word ,MAIN.work_order_degree,MAIN.project_amount,MAIN.Compensate,MAIN.Calculate_Income_Ratio,MAIN.Initiator as initiator,MAIN.Submit_Application_Time as SubmitTime,DECODE(MAIN.STATE,8,'等待接口调用',ACTI.Name_) as statusName,DECODE(MAIN.STATE,8,'waitingCallInterface',ACTI.Task_Def_Key_) as taskDefKey,MAIN.TASK_ASSIGNEE,MAIN.Send_Time as sendTime from ( select distinct k.proc_inst_id_, k.name_, k.task_def_key_ from ACT_RU_TASK k inner join ACT_RE_PROCDEF D on k.PROC_DEF_ID_ = D.ID_ where D.KEY_ = '"
				+ processDefinitionKey
				+ "' and (exists (select LINK.USER_ID_ from ACT_HI_IDENTITYLINK LINK where USER_ID_ = '"
				+ userId
				+ "' and LINK.PROC_INST_ID_ = k.proc_inst_id_)) and (exists (select kst.assignee_ from act_hi_taskinst kst where kst.assignee_ = '"
				+ userId
				+ "' and kst.proc_inst_id_ = k.proc_inst_id_ and kst.end_time_ is not null))) ACTI,PNR_ACT_TRANSFER_OFFICE_MAIN MAIN WHERE ACTI.Proc_Inst_Id_ = MAIN.PROCESS_INSTANCE_ID(+)) ST";
		String whereSql = " WHERE 1=1 and ST.STATE = '8' and ST.INITIATOR="+"'"+userId+"' and ST.remind <> 1";
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
			whereSql += " and ST.taskDefKey = '" + status + "'";

		}
		sql += selectSql
				+ whereSql
				+ " order by ST.sendTime) temp1 where rownum <="
				+ endResult * pageSize + ") temp2 where temp2.num > "
				+ firstResult * pageSize;

		System.out.println("待接口调用工单明细sql=" + sql);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		for (Map map : list) {
			TaskModel model = new TaskModel();
			if (map.get("processInstanceId") != null
					&& !"".equals(map.get("processInstanceId"))) {
				model.setProcessInstanceId(map.get("processInstanceId")
						.toString());
			}
			if (map.get("theme") != null && !"".equals(map.get("theme"))) {
				model.setTheme(map.get("theme").toString());
			}
			if (map.get("initiator") != null
					&& !"".equals(map.get("initiator"))) {
				model.setInitiator(map.get("initiator").toString());
			}
			if (map.get("DEPTID") != null && !"".equals(map.get("DEPTID"))) {
				model.setDeptId(map.get("DEPTID").toString());
			}
			if (map.get("SubmitTime") != null) {
				if (!"".equals(map.get("SubmitTime"))) {
					try {
						model.setApplicationTime(format.parse(map.get(
								"SubmitTime").toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

			}
//			if (map.get("SubmitTime") != null
//					&& !"".equals(map.get("SubmitTime"))) {
//
//				model.setApplicationTime(map.get("SubmitTime")
//						.toString());
//			}
			if (map.get("statusName") != null
					&& !"".equals(map.get("statusName"))) {
				model.setStatusName(map.get("statusName").toString());
			}
			if (map.get("city") != null && !"".equals(map.get("city"))) {

				model.setCity(map.get("city").toString());
			}
			if (map.get("country") != null && !"".equals(map.get("country"))) {
				model.setCountry(map.get("country").toString());
			}
			if (map.get("work_type") != null
					&& !"".equals(map.get("work_type"))) {

				model.setWorkType(map.get("work_type").toString());
			}
			if (map.get("workorder_type_name") != null
					&& !"".equals(map.get("workorder_type_name"))) {
				model.setWorkorderTypeName(map.get("workorder_type_name")
						.toString());
			}

			if (map.get("sub_type_name") != null
					&& !"".equals(map.get("sub_type_name"))) {
				model.setSubTypeName(map.get("sub_type_name").toString());
			}

			if (map.get("key_word") != null && !"".equals(map.get("key_word"))) {
				model.setKeyWord(map.get("key_word").toString());
			}
			if (map.get("work_order_degree") != null
					&& !"".equals(map.get("work_order_degree"))) {
				model.setWorkOrderDegree(map.get("work_order_degree")
						.toString());
			}

			if (map.get("project_amount") != null
					&& !"".equals(map.get("project_amount"))) {
				model.setProjectAmount(Double.parseDouble(map.get(
						"project_amount").toString()));
			}
			if (map.get("Compensate") != null
					&& !"".equals(map.get("Compensate"))) {
				model.setCompensate(Double.parseDouble(map.get("Compensate")
						.toString()));
			}
			if (map.get("Calculate_Income_Ratio") != null
					&& !"".equals(map.get("Calculate_Income_Ratio"))) {
				model.setCalculateIncomeRatio(map.get("Calculate_Income_Ratio").toString());
			}
			if (map.get("sheetId") != null && !"".equals(map.get("sheetId"))) {
				model.setSheetId(map.get("sheetId").toString());
			}
			r.add(model);

		}

		return r;

	}
	/**
	 * 待接口调用工单条数
	 * 
	 * @param processDefinitionKey
	 * @param userId
	 * @param sendStartTime
	 * @param sendEndTime
	 * @param wsNum
	 * @param wsTitle
	 * @param status
	 * @return
	 */
	public int workOrderRemindCount(String processDefinitionKey, String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status) {
		String sql = "";
		String selectSql = "select count(processInstanceId) as total from (select ACTI.Proc_Inst_Id_ as processInstanceId,MAIN.Theme as theme,MAIN.city ,MAIN.country,MAIN.state,MAIN.work_type,MAIN.workorder_type_name,MAIN.sub_type_name ,MAIN.key_word ,MAIN.work_order_degree,MAIN.project_amount,MAIN.Initiator as initiator,MAIN.Submit_Application_Time as SubmitTime,nvl(MAIN.IS_REMIND,0) as remind,DECODE(MAIN.STATE,8,'等待接口调用',ACTI.Name_) as statusName,DECODE(MAIN.STATE,8,'waitingCallInterface',ACTI.Task_Def_Key_) as taskDefKey,MAIN.TASK_ASSIGNEE,MAIN.Send_Time as sendTime from ( select distinct k.proc_inst_id_, k.name_, k.task_def_key_ from ACT_RU_TASK k inner join ACT_RE_PROCDEF D on k.PROC_DEF_ID_ = D.ID_ where D.KEY_ = '"
				+ processDefinitionKey
				+ "' and (exists (select LINK.USER_ID_ from ACT_HI_IDENTITYLINK LINK where USER_ID_ = '"
				+ userId
				+ "' and LINK.PROC_INST_ID_ = k.proc_inst_id_)) and (exists (select kst.assignee_ from act_hi_taskinst kst where kst.assignee_ = '"
				+ userId
				+ "' and kst.proc_inst_id_ = k.proc_inst_id_ and kst.end_time_ is not null))) ACTI,PNR_ACT_TRANSFER_OFFICE_MAIN MAIN WHERE ACTI.Proc_Inst_Id_ = MAIN.PROCESS_INSTANCE_ID(+)) ST";
		String whereSql = " WHERE 1=1 and ST.STATE = '8' and ST.INITIATOR="+"'"+userId+"' and ST.remind <> 1";
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
			whereSql += " and ST.taskDefKey = '" + status + "'";

		}
		sql += selectSql + whereSql;

		System.out.println("待接口调用工单条数sql=" + sql);

		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		return Integer.parseInt(list.get(0).get("total").toString());
	}
	/**
	 * 省公司审批通过已40天工单明细 chujingang
	 * @return
	 */
	public List<TaskModel> InterfaceCallSmsAlertsList() {
		
			String sql="	    select 	*															"+
			           "        from (select ACTI.Proc_Inst_Id_ as processInstanceId,				"+
	                   "        MAIN.Theme as theme,												"+
	                   "        MAIN.workorder_type_name,											"+
	                   "        MAIN.Initiator as initiator 										"+
	                   "   from (select distinct k.proc_inst_id_,									"+
	                   "                         k.name_,											"+
	                   "                         k.task_def_key_									"+
	                   "           from ACT_RU_TASK k												"+
	                   "          inner join ACT_RE_PROCDEF D										"+
	                   "             on k.PROC_DEF_ID_ = D.ID_										"+
	                   "          where D.KEY_ in('transferArteryPrecheckProcess','overHaulNewProcess','transferNewPrechechProcess')	"+
	                   "            and (exists (select LINK.USER_ID_								"+
	                   "                           from ACT_HI_IDENTITYLINK LINK					"+
	                   "                          where LINK.PROC_INST_ID_ =						"+
	                   "                                k.proc_inst_id_))							"+
	                   "            and (exists														"+
	                   "                 (select kst.assignee_										"+
	                   "                    from act_hi_taskinst kst								"+
	                   "                    where kst.proc_inst_id_ =								"+
	                   "                         k.proc_inst_id_									"+
	                   "                     and kst.end_time_ is not null))) ACTI,					"+
	                   "        PNR_ACT_TRANSFER_OFFICE_MAIN MAIN									"+
	                   "  WHERE ACTI.Proc_Inst_Id_ =												"+
	                   "        MAIN.PROCESS_INSTANCE_ID(+)											"+
	                   "        and MAIN.STATE = '8'												"+
	                   "        and floor(sysdate - MAIN.DISTRIBUTED_INTERFACE_TIME) >=40			"+
	                   "        order by MAIN.End_Time												"+       
	                   "        ) ST																";
			               
		
		
		System.out.println("省公司审批通过已40天工单明细sql=" + sql);

		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		for (Map map : list) {
			TaskModel model = new TaskModel();
			if (map.get("processInstanceId") != null
					&& !"".equals(map.get("processInstanceId"))) {
				model.setProcessInstanceId(map.get("processInstanceId")
						.toString());
			}
			if (map.get("theme") != null && !"".equals(map.get("theme"))) {
				model.setTheme(map.get("theme").toString());
			}
			if (map.get("initiator") != null
					&& !"".equals(map.get("initiator"))) {
				model.setInitiator(map.get("initiator").toString());
			}
			if (map.get("workorder_type_name") != null
					&& !"".equals(map.get("workorder_type_name"))) {
				model.setWorkorderTypeName(map.get("workorder_type_name")
						.toString());
			}
			r.add(model);

		}

		return r;

	}
	
	@Override
	public int selectOltBbuRoomCount(String processKey, ConditionQueryModel conditionQueryModel) {
		String sql ="select count(*) as total from PNR_OLT_BBU_ROOM t where 1=1";

		String whereSql="";
		
		if(processKey!=null && !"".equals(processKey)){
			whereSql+=" and t.themeinterface='"+processKey+"'";
		}
		if(conditionQueryModel.getJfAddressName()!=null && !"".equals(conditionQueryModel.getJfAddressName())){
			whereSql+=" and t.JF_ADDRESS_NAME like '%"+conditionQueryModel.getJfAddressName()+"%'";
		}
		
		sql += whereSql;

		System.out.println("--------------机房清单集合数sql=" + sql);

		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		return Integer.parseInt(list.get(0).get("total").toString());

	}
	@Override
	public List<PnrOltBbuRoom> selectOltBbuRoolList(String processKey,
			ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize) {

		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		//String sql = "";
		String selectSql = "select * from PNR_OLT_BBU_ROOM t where 1=1";
		String whereSql="";
		
		if(processKey!=null && !"".equals(processKey)){
			whereSql+=" and t.themeinterface='"+processKey+"'";
		}
		
		if(conditionQueryModel.getJfAddressName()!=null && !"".equals(conditionQueryModel.getJfAddressName())){
			whereSql+=" and t.JF_ADDRESS_NAME like '%"+conditionQueryModel.getJfAddressName()+"%'";
		}
		sql += selectSql +whereSql
				+ ") temp1 where rownum <="
				+ endResult * pageSize + ") temp2 where temp2.num > "
				+ firstResult * pageSize;
//		  sql += selectSql
//		  + whereSql
//		  + " order by m.send_time";
		System.out.println("sql======++++" + sql);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<PnrOltBbuRoom> r = new ArrayList<PnrOltBbuRoom>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		for (Map map : list) {
			PnrOltBbuRoom model = new PnrOltBbuRoom();
			if (map.get("id") != null && !"".equals(map.get("id"))) {

				model.setId(map.get("id").toString());
			}
			if (map.get("themeInterface") != null && !"".equals(map.get("themeInterface"))) {

				model.setThemeInterface(map.get("themeInterface").toString());
			}
			if (map.get("serial_number") != null && !"".equals(map.get("serial_number"))) {

				model.setSerialNumber(map.get("serial_number").toString());
			}
			if (map.get("jf_city") != null && !"".equals(map.get("jf_city"))) {

				model.setJfCity(map.get("jf_city").toString());
			}
			if (map.get("jf_country") != null && !"".equals(map.get("jf_country"))) {

				model.setJfCountry(map.get("jf_country").toString());
			}
			if (map.get("jf_address_name") != null && !"".equals(map.get("jf_address_name"))) {

				model.setJfAddressName(map.get("jf_address_name").toString());
			}
			if (map.get("olt_number") != null && !"".equals(map.get("olt_number"))) {

				model.setOltNumber(map.get("olt_number").toString());
			}
			if (map.get("total_user_number") != null && !"".equals(map.get("total_user_number"))) {

				model.setTotalUserNumber(map.get("total_user_number").toString());
			}
			if (map.get("voice_user") != null && !"".equals(map.get("voice_user"))) {

				model.setVoiceUser(map.get("voice_user").toString());
			}
			if (map.get("broadband_user") != null && !"".equals(map.get("broadband_user"))) {

				model.setBroadbandUser(map.get("broadband_user").toString());
			}
			if (map.get("iptv_user") != null && !"".equals(map.get("iptv_user"))) {

				model.setIptvUser(map.get("iptv_user").toString());
			}
			if (map.get("is_no_bbu") != null && !"".equals(map.get("is_no_bbu"))) {

				model.setIsNoBbu(map.get("is_no_bbu").toString());
			}
			
			if (map.get("bbu_number") != null && !"".equals(map.get("bbu_number"))) {

				model.setBbuNumber(map.get("bbu_number").toString());
			}
			if (map.get("is_need_rod_investment") != null && !"".equals(map.get("is_need_rod_investment"))) {

				model.setIsNeedRodInvestment(map.get("is_need_rod_investment").toString());
			}
			if (map.get("is_need_cable_investment") != null && !"".equals(map.get("is_need_cable_investment"))) {

				model.setIsNeedCableInvestment(map.get("is_need_cable_investment").toString());
			}
			if (map.get("new_built_rod_length") != null && !"".equals(map.get("new_built_rod_length"))) {

				model.setNewBuiltRodLength(map.get("new_built_rod_length").toString());
			}
			if (map.get("new_built_rod_investment") != null && !"".equals(map.get("new_built_rod_investment"))) {

				model.setNewBuiltRodInvestment(map.get("new_built_rod_investment").toString());
			}
			if (map.get("line_total_investment") != null && !"".equals(map.get("line_total_investment"))) {

				model.setLineTotalInvestment(map.get("line_total_investment").toString());
			}
			r.add(model);
		}
		return r;
	}

	@Override
	public List<PnrOltBbuRoom> findOltBbuRoomByid(String id) {
		String sql = "select * from PNR_OLT_BBU_ROOM t where 1=1";
		String whereSql="";
		if(id!=null && !"".equals(id)){
			whereSql+=" and t.id='"+id+"'";
		}
		sql += whereSql;
		System.out.println("sql======++++" + sql);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<PnrOltBbuRoom> r = new ArrayList<PnrOltBbuRoom>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		for (Map map : list) {
			PnrOltBbuRoom model = new PnrOltBbuRoom();
			if (map.get("id") != null && !"".equals(map.get("id"))) {

				model.setId(map.get("id").toString());
			}
			if (map.get("themeInterface") != null && !"".equals(map.get("themeInterface"))) {

				model.setThemeInterface(map.get("themeInterface").toString());
			}
			if (map.get("serial_number") != null && !"".equals(map.get("serial_number"))) {

				model.setSerialNumber(map.get("serial_number").toString());
			}
			if (map.get("jf_city") != null && !"".equals(map.get("jf_city"))) {

				model.setJfCity(map.get("jf_city").toString());
			}
			if (map.get("jf_country") != null && !"".equals(map.get("jf_country"))) {

				model.setJfCountry(map.get("jf_country").toString());
			}
			if (map.get("jf_address_name") != null && !"".equals(map.get("jf_address_name"))) {

				model.setJfAddressName(map.get("jf_address_name").toString());
			}
			if (map.get("olt_number") != null && !"".equals(map.get("olt_number"))) {

				model.setOltNumber(map.get("olt_number").toString());
			}
			if (map.get("total_user_number") != null && !"".equals(map.get("total_user_number"))) {

				model.setTotalUserNumber(map.get("total_user_number").toString());
			}
			if (map.get("voice_user") != null && !"".equals(map.get("voice_user"))) {

				model.setVoiceUser(map.get("voice_user").toString());
			}
			if (map.get("broadband_user") != null && !"".equals(map.get("broadband_user"))) {

				model.setBroadbandUser(map.get("broadband_user").toString());
			}
			if (map.get("iptv_user") != null && !"".equals(map.get("iptv_user"))) {

				model.setIptvUser(map.get("iptv_user").toString());
			}
			if (map.get("is_no_bbu") != null && !"".equals(map.get("is_no_bbu"))) {

				model.setIsNoBbu(map.get("is_no_bbu").toString());
			}
			
			if (map.get("bbu_number") != null && !"".equals(map.get("bbu_number"))) {

				model.setBbuNumber(map.get("bbu_number").toString());
			}
			if (map.get("is_need_rod_investment") != null && !"".equals(map.get("is_need_rod_investment"))) {

				model.setIsNeedRodInvestment(map.get("is_need_rod_investment").toString());
			}
			if (map.get("is_need_cable_investment") != null && !"".equals(map.get("is_need_cable_investment"))) {

				model.setIsNeedCableInvestment(map.get("is_need_cable_investment").toString());
			}
			if (map.get("new_built_rod_length") != null && !"".equals(map.get("new_built_rod_length"))) {

				model.setNewBuiltRodLength(map.get("new_built_rod_length").toString());
			}
			if (map.get("new_built_rod_investment") != null && !"".equals(map.get("new_built_rod_investment"))) {

				model.setNewBuiltRodInvestment(map.get("new_built_rod_investment").toString());
			}
			if (map.get("line_total_investment") != null && !"".equals(map.get("line_total_investment"))) {

				model.setLineTotalInvestment(map.get("line_total_investment").toString());
			}
			r.add(model);
		}
		return r;
	}
	
	@Override
	public int getCurrDateSheetCountNum(String sequenceName) {
		int maxValue = 0;
		String sql = "select "+sequenceName+".NEXTVAL as A from dual";
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		
		if(list!=null && list.size()>0){
			 maxValue = Integer.parseInt( list.get(0).get("A").toString());
		
		}
		return maxValue;
	}
}
