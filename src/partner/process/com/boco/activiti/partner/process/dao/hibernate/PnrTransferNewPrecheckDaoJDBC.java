package com.boco.activiti.partner.process.dao.hibernate;

import java.lang.reflect.Field;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.activiti.partner.process.dao.IPnrTransferNewPrecheckJDBCDao;
import com.boco.activiti.partner.process.model.PnrAndroidPhotoFile;
import com.boco.activiti.partner.process.model.RoomAssets;
import com.boco.activiti.partner.process.po.ChildSceneReportsModel;
import com.boco.activiti.partner.process.po.ConditionQueryDAMModel;
import com.boco.activiti.partner.process.po.ConditionQueryModel;
import com.boco.activiti.partner.process.po.DownAttachMentModel;
import com.boco.activiti.partner.process.po.DownWorkOrderModel;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.eoms.base.util.StaticMethod;

public class PnrTransferNewPrecheckDaoJDBC extends JdbcDaoSupport implements
		IPnrTransferNewPrecheckJDBCDao {

	@Override
	public int getTransferNewPrecheckCount(String userid, String flag,
			String processKey, ConditionQueryModel conditionQueryModel) {
		
		String themeinterface =com.boco.eoms.partner.process.util.CommonUtils.getTaskThemeinterfaceStr(processKey,"m");
		/*if("transferNewPrechechProcess".equals(processKey)){//本地网
			themeinterface="interface";
		}else if("transferArteryPrecheckProcess".equals(processKey)){//干线
			themeinterface="arteryPrecheck";

		}*/
		ArrayList list = new ArrayList(); 
	    
		String sql = "";
//		String selectSql = "select count(*) as total from (select  RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_ WHERE RES.ASSIGNEE_ = ? and D.KEY_ = ? and RES.Suspension_State_ = 1) t,pnr_act_transfer_office_main m where t.proc_inst_id_ = m.process_instance_id  and m.themeinterface='"+themeinterface+"'";
		String selectSql = "select count(*) as total from pnr_act_transfer_office_main m where m.themeinterface = '"+themeinterface+"' and m.assignee=? ";

		list.add(userid);
//		list.add(processKey);
		
		String whereSql = getWhereSql(flag,conditionQueryModel,list);
		
		sql = selectSql + whereSql;

        
		Object[] args = list.toArray();
		
		int size = this.getJdbcTemplate().queryForInt(sql, args);
		System.out.println("--------------新预检预修待回复工单或代办（总数）PnrTransferNewPrecheckDaoJDBC getTransferNewPrecheckCount=" + sql);
		System.out.println("新预检预修待回复工单或代办（总数）-----------------------------------"+size+"------------------------------------");
		return size;

	}
	
	private String getWhereSql(String flag,ConditionQueryModel conditionQueryModel,List list){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		String whereSql ="";
		if (flag != null && "backlog".equals(flag.trim())) {
			whereSql = " and (m.state!=? and m.state!=?)";
			list.add(8);
			list.add(3);
		} else if ("wait".equals(flag.trim())) {
			whereSql = " and m.state=?";			
			list.add(3);
		}
		if (conditionQueryModel.getSendStartTime() != null
				&& !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >=?";
			list.add(conditionQueryModel.getSendStartTime());
		
		}
		if (conditionQueryModel.getSendEndTime() != null
				&& !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?";
//			list.add("to_date('"+ conditionQueryModel.getSendEndTime()+ "','yyyy-mm-dd hh24:mi:ss')");
			list.add( conditionQueryModel.getSendEndTime());
		}
		if (conditionQueryModel.getWorkNumber() != null
				&& !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and m.process_instance_id =?";
			list.add(conditionQueryModel.getWorkNumber().trim());
		}
		if (conditionQueryModel.getTheme() != null
				&& !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and instr(m.theme,?)>0 ";
			list.add(conditionQueryModel.getTheme().trim());
		}
		if (conditionQueryModel.getStatus() != null
				&& !conditionQueryModel.getStatus().equals("")) {
			whereSql += " and m.task_def_key = ? ";
			list.add(conditionQueryModel.getStatus());

		}
		if (conditionQueryModel.getCity() != null
				&& !conditionQueryModel.getCity().equals("")) {
			whereSql += " and m.city = ?";
			list.add(conditionQueryModel.getCity());
		}
		if (conditionQueryModel.getCountry() != null
				&& !conditionQueryModel.getCountry().equals("")) {
			whereSql += " and m.country = ?";
			list.add(conditionQueryModel.getCountry());
		}
		if (conditionQueryModel.getLineType() != null
				&& !conditionQueryModel.getLineType().equals("")) {
			whereSql += " and m.work_type = ?";
			list.add(conditionQueryModel.getLineType());
		}
		if (conditionQueryModel.getWorkOrderType() != null
				&& !conditionQueryModel.getWorkOrderType().equals("")) {
			whereSql += " and m.workorder_type_name = ?";
			list.add(conditionQueryModel.getWorkOrderType());
		}
		if (conditionQueryModel.getPrecheckFlag() != null
				&& !conditionQueryModel.getPrecheckFlag().equals("")) {
			whereSql += " and m.precheck_flag = ?";
			list.add(conditionQueryModel.getPrecheckFlag());
		}
		if (conditionQueryModel.getMainSceneId() != null
				&& !conditionQueryModel.getMainSceneId().equals("")) {
			whereSql += " and m.main_scene_id = ?";
			list.add(conditionQueryModel.getMainSceneId());
		}

		return whereSql;
	}
	protected  class ItemMapper implements RowMapper {

		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			// TODO Auto-generated method stub
			TaskModel model = new TaskModel();

				model.setTaskId(rs.getString("TaskId"));	

				model.setTaskDefKey(rs.getString("taskDefKey"));
		
				model.setStatusName(rs.getString("statusName"));
			
				model.setInitiator(rs.getString("Initiator"));

				model.setOneInitiator(rs.getString("OneInitiator"));
			

				model.setProcessInstanceId(rs.getString("ProcessInstanceId"));
				//待验证
				model.setApplicationTime(rs.getDate("SubmitTime"));	
				model.setTheme(rs.getString("Theme"));

//				model.setDeptId(rs.getString("DeptId"));

				model.setState(rs.getInt("State"));

				model.setCity(rs.getString("city"));
				model.setCountry(rs.getString("country"));
				model.setWorkType(rs.getString("work_type"));
				model.setWorkorderTypeName(rs.getString("workorder_type_name"));

				model.setSubTypeName(rs.getString("sub_type_name"));
				model.setKeyWord(rs.getString("key_word"));
				model.setWorkOrderDegree(rs.getString("work_order_degree"));

				model.setProjectAmount(rs.getDouble("project_amount"));
				model.setSheetId(rs.getString("sheetId"));
				model.setRollbackFlag(rs.getString("rollback_flag"));
				model.setPrecheckFlag(rs.getString("precheck_flag"));
				model.setCompensate(rs.getDouble("compensate"));
				model.setCalculateIncomeRatio(rs.getString("calculate_income_ratio"));
				model.setLayingCable(rs.getDouble("laying_cable"));
				
				model.setExcavationTrench(rs.getDouble("excavation_trench"));
				
				model.setRepairPipeline(rs.getDouble("repair_pipeline"));
				model.setRightingDemolitionPole(rs.getDouble("righting_demolition_pole"));
				model.setWireLaying(rs.getDouble("wire_laying"));
				model.setFiberOpticCableConnector(rs.getDouble("fiber_optic_cable_connector"));
				model.setMainQuantityOther(rs.getString("main_quantity_other"));
				model.setConstructionReasons(rs.getString("construction_reasons"));
				model.setNetworkStatus(rs.getString("network_status"));
				model.setConstructionMainContent(rs.getString("construction_main_content"));
				model.setCreateStr(rs.getString("create_str")==null?"无":rs.getString("create_str"));
				model.setDeleteStr(rs.getString("delete_str")==null?"无":rs.getString("delete_str"));
			
			//工单处理-项目金额估算
				model.setWorkerProjectAmount(rs.getDouble("worker_project_amount"));
			//抽查-项目金额估算 ---验证会不会报错？？？？
				model.setOrderauditProjectAmount(rs.getDouble("orderaudit_project_amount"));
			//待回复列表显示的工费
				model.setTotalFee(rs.getString("totalFee"));
			
			//待回复列表显示的材料费
				model.setTotalMaterialCost(rs.getString("totalMaterialCost"));	
				//实物金额
				model.setKindRestitution(rs.getDouble("kind_restitution"));
				
				
			return model;
		} 
		
	}
	@Override
	public List<TaskModel> getTransferNewPrecheckList(String userid,
			String flag, String processKey,
			ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize) {
		
		ArrayList list = new ArrayList(); 		
		String themeinterface =com.boco.eoms.partner.process.util.CommonUtils.getTaskThemeinterfaceStr(processKey,"");
	
		String sql = "";
		if(pageSize != -1){ //计算总项目金额和导出共用
			sql = "select temp2.* from (select temp1.*, rownum num from (";
		}
		//String sql = "";
		String selectSql = "select /*+parallel(m 4) */ m.task_id as TaskId,m.task_def_key as taskDefKey,decode(m.state,6,'专家会审',m.task_def_key_name) as statusName,m.initiator as Initiator,m.one_initiator as OneInitiator," +
				 " m.process_instance_id as ProcessInstanceId,m.send_time as SendTime,m.theme as Theme,m.task_assignee,m.state as State,m.submit_application_time as SubmitTime,m.city ,m.country," +
				 " m.work_type,m.workorder_type_name,m.sub_type_name ,m.key_word ,m.work_order_degree,m.rollback_flag, m.precheck_flag,m.project_amount,m.compensate,to_char(m.calculate_income_ratio,'fm9999990.00') calculate_income_ratio,m.laying_cable, m.excavation_trench," +
				 " m.repair_pipeline, m.righting_demolition_pole, m.wire_laying, m.fiber_optic_cable_connector,m.main_quantity_other, m.construction_reasons, m.network_status, m.construction_main_content," +
				 "m.create_str,m.delete_str,m.worker_project_amount,m.orderaudit_project_amount,decode(m.sheet_id,null,'无',m.sheet_id) as sheetId,m.sum_need_cost_of_party_b as totalFee,m.project_amount-m.sum_need_cost_of_party_b as totalMaterialCost,m.kind_restitution from pnr_act_transfer_office_main m where m.themeinterface='"+themeinterface+"' and m.assignee=? ";
		list.add(userid);
		
		String whereSql = getWhereSql(flag,conditionQueryModel,list);
		
		sql += selectSql + whereSql
				+ " order by m.send_time";
		if(pageSize != -1){ ////计算总项目金额和导出共用
			sql += ") temp1 where rownum <=? ) temp2 where temp2.num >? ";
			list.add(endResult * pageSize);
			list.add(firstResult * pageSize);
		}
//		  sql += selectSql
//		  + whereSql
//		  + " order by m.send_time";
		System.out.println("新预检预修待回复工单或代办工单（明细）PnrTransferNewPrecheckDaoJDBC getTransferNewPrecheckList=" + sql);
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
		Object[] values = list.toArray();
		List<TaskModel> r = this.getJdbcTemplate().query(sql, values, new ItemMapper());
		
		return r;
	}

	@Override
	public List<Map> getPrecheckShipModelBycountryCharge(String countryCharge) {
		String sql = "select * from pnr_act_transfer_precheck_ship s where s.country_charge='"
				+ countryCharge + "'";

		return this.getJdbcTemplate().queryForList(sql);
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
	public List<TaskModel> getHaveProcessList(String processDefinitionKey,
			String userId, String sendStartTime, String sendEndTime,
			String wsNum, String wsTitle, String status, int firstResult,
			int endResult, int pageSize) {
		List listsql = new ArrayList();
		
		String themeinterface =com.boco.eoms.partner.process.util.CommonUtils.getTaskThemeinterfaceStr(processDefinitionKey,"MAIN");
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		String selectSql = "select MAIN.process_instance_id as processInstanceId,decode(MAIN.sheet_id, null, '无', MAIN.sheet_id) as sheetId," +
				"MAIN.Theme as theme,MAIN.city ,MAIN.country,MAIN.work_type," +
				"MAIN.workorder_type_name,MAIN.sub_type_name ,MAIN.key_word ,MAIN.work_order_degree," +
				"MAIN.project_amount,MAIN.Compensate,MAIN.Calculate_Income_Ratio,MAIN.Initiator as initiator," +
				"MAIN.Submit_Application_Time as SubmitTime,MAIN.distributed_interface_time," +
				"DECODE(MAIN.STATE,8,'省公司审批通过-等待商城',MAIN.Task_Def_Key_name) as statusName," +
				"DECODE(MAIN.STATE,8,'waitingCallInterface',MAIN.Task_Def_Key) as taskDefKey," +
				"MAIN.TASK_ASSIGNEE,MAIN.Send_Time as sendTime,MAIN.one_initiator,u.deptid " +
				"from PNR_ACT_TRANSFER_OFFICE_MAIN MAIN,taw_system_user u " +
				"  where MAIN.task_assignee = u.userid(+) and exists (select kst.assignee_ from act_hi_taskinst kst " +
				"where kst.assignee_ =?"
				+ " and kst.proc_inst_id_ = MAIN.Process_Instance_Id and kst.end_time_ is not null) " +
						" and MAIN.themeinterface='"+themeinterface+"' ";
		listsql.add(userId);
		
		String whereSql = "";
		if (sendStartTime != null && !sendStartTime.equals("")) {
			whereSql += " and to_char(MAIN.send_time,'yyyy-mm-dd hh24:mi:ss') >=?";
			listsql.add(sendStartTime);

		}
		if (sendEndTime != null && !sendEndTime.equals("")) {
			whereSql += " and to_char(MAIN.send_time,'yyyy-mm-dd hh24:mi:ss') <=?";
			listsql.add(sendEndTime);

		}
		if (wsNum != null && !wsNum.equals("")) {
			whereSql += " and MAIN.process_instance_id =?";
			listsql.add(wsNum.trim());
		}
		if (wsTitle != null && !wsTitle.equals("")) {
			whereSql += " and instr(MAIN.theme,?)>0";
			listsql.add(wsTitle.trim());
		}
		if (status != null && !status.equals("")) {
			whereSql += " and MAIN.task_def_key =?";
			listsql.add(status);

		}
		sql += selectSql
				+ whereSql
				+ " order by MAIN.send_time) temp1 where rownum <=?) temp2 where temp2.num >?";
		listsql.add(endResult * pageSize);
		listsql.add(firstResult * pageSize);

		System.out.println("PnrTransferNewPrecheckDaoJDBC-getHaveProcessList:已处理工单明细sql=" + sql);
		Object[] args = listsql.toArray();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql, args);
		for (Map map : list) {
			TaskModel model = new TaskModel();
			if (map.get("processInstanceId") != null
					&& !"".equals(map.get("processInstanceId"))) {
				model.setProcessInstanceId(map.get("processInstanceId").toString());
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
				if (!"".equals(map.get("SubmitTime").toString())) {
					try {
						model.setApplicationTime(format.parse(map.get(
								"SubmitTime").toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

			}
			if (map.get("distributed_interface_time") != null) {
				if (!"".equals(map.get("distributed_interface_time").toString())) {
					try {
						model.setDistributedInterfaceTime(format.parse(map.get(
								"distributed_interface_time").toString()));
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
			//派单时间
			if (map.get("SendTime") != null && !"".equals(map.get("SendTime").toString())) {
				try {
					model.setSendTime(format.parse(map.get("SendTime")
							.toString()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			//工单生成人
			if (map.get("one_initiator") != null
					&& !"".equals(map.get("one_initiator"))) {
				model.setOneInitiator(map.get("one_initiator").toString());
			}
			//所属部分
			if (map.get("deptid") != null&& !"".equals(map.get("deptid").toString())) {
				model.setDeptId(map.get("deptid").toString());
			}
			
			r.add(model);

		}

		return r;

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
	public int getHaveProcessCount(String processDefinitionKey, String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status) {
		List listsql = new ArrayList();
		String themeinterface =com.boco.eoms.partner.process.util.CommonUtils.getTaskThemeinterfaceStr(processDefinitionKey,"MAIN");
		String sql = "";
		String selectSql = "select count(MAIN.Process_Instance_Id) as total " +
				" from PNR_ACT_TRANSFER_OFFICE_MAIN MAIN,taw_system_user u" +
				" where MAIN.task_assignee = u.userid(+) and exists (select kst.assignee_ " +
				    " from act_hi_taskinst kst where kst.assignee_ = ?" +
				    " and kst.proc_inst_id_ = MAIN.Process_Instance_Id and kst.end_time_ is not null)  " +
				    " and MAIN.themeinterface='"+themeinterface+"'";
		
		listsql.add(userId);
		
		String whereSql = "";
		
		if (sendStartTime != null && !sendStartTime.equals("")) {
			whereSql += " and to_char(MAIN.send_time,'yyyy-mm-dd hh24:mi:ss') >=?";
			listsql.add(sendStartTime);

		}
		if (sendEndTime != null && !sendEndTime.equals("")) {
			whereSql += " and to_char(MAIN.send_time,'yyyy-mm-dd hh24:mi:ss') <=?";
			listsql.add(sendEndTime);

		}
		if (wsNum != null && !wsNum.equals("")) {
			whereSql += " and MAIN.Process_Instance_Id =?";
			listsql.add(wsNum.trim());
		}
		if (wsTitle != null && !wsTitle.equals("")) {
			whereSql += " and instr(MAIN.theme,?)>0";
			listsql.add(wsTitle.trim());
		}
		if (status != null && !status.equals("")) {
			whereSql += " and MAIN.task_Def_Key =?";
			listsql.add(status);

		}
		
		sql += selectSql + whereSql;

		System.out.println("预检预修-本地网-已处理工单（总数）PnrTransferNewPrecheckDaoJDBC-getHaveProcessCount=" + sql);
		
		Object[] args = listsql.toArray();
		int size  = this.getJdbcTemplate().queryForInt(sql, args);
		return  size;
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
	public int getByCreatingWorkOrderCount(String processDefinitionKey,
			String userId, String sendStartTime, String sendEndTime,
			String wsNum, String wsTitle, String status) {
		String themeinterface =com.boco.eoms.partner.process.util.CommonUtils.getTaskThemeinterfaceStr(processDefinitionKey,"MAIN");
		List listsql = new ArrayList();
		String sql = "";
		String selectSql = "select count(MAIN.Process_Instance_Id) as total from PNR_ACT_TRANSFER_OFFICE_MAIN MAIN " +
				"WHERE MAIN.State <> 2 and MAIN.themeinterface='"+themeinterface+"' and MAIN.One_Initiator=? ";
		
		listsql.add(userId);		
		String whereSql = "";
		if (sendStartTime != null && !sendStartTime.equals("")) {
			whereSql += " and to_char(MAIN.send_time,'yyyy-mm-dd hh24:mi:ss') >=?";
			listsql.add(sendStartTime);

		}
		if (sendEndTime != null && !sendEndTime.equals("")) {
			whereSql += " and to_char(MAIN.send_time,'yyyy-mm-dd hh24:mi:ss') <=?";
			listsql.add(sendEndTime);
		}
		if (wsNum != null && !wsNum.equals("")) {
			whereSql += " and MAIN.Process_Instance_Id =?";
			listsql.add(wsNum.trim());
		}
		if (wsTitle != null && !wsTitle.equals("")) {
			whereSql += " and instr(MAIN.theme,?)>0 ";
			listsql.add(wsTitle.trim());
		}
		if (status != null && !status.equals("")) {
			if(!"delete".equals(status)){
				whereSql += " and MAIN.task_def_key = ? ";
				listsql.add(status);
			}else{
				whereSql += " and MAIN.State = 1 ";
			}
		}
		
		sql += selectSql + whereSql;

		System.out.println("****PnrTransferNewPrecheckDaoJDBC-getByCreatingWorkOrderCount:由我创建的工单条数sql=" + sql);
		Object[] args = listsql.toArray();
		int size = this.getJdbcTemplate().queryForInt(sql, args);
		
		return size;
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
	public List<TaskModel> getByCreatingWorkOrderList(
			String processDefinitionKey, String userId, String sendStartTime,
			String sendEndTime, String wsNum, String wsTitle, String status,
			int firstResult, int endResult, int pageSize) {
		List listsql = new ArrayList();
		String themeinterface =com.boco.eoms.partner.process.util.CommonUtils.getTaskThemeinterfaceStr(processDefinitionKey,"MAIN");
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		String selectSql = "select MAIN.Process_Instance_Id as processInstanceId," +
				"MAIN.Theme as theme,MAIN.city,MAIN.country,MAIN.work_type," +
				"MAIN.workorder_type_name,MAIN.sub_type_name,MAIN.key_word," +
				"MAIN.work_order_degree,MAIN.project_amount,MAIN.Initiator as initiator," +
				"MAIN.Submit_Application_Time as SubmitTime,MAIN.distributed_interface_time," +
				"MAIN.TASK_ASSIGNEE,MAIN.Send_Time as sendTime,MAIN.State," +
				"MAIN.One_Initiator as oneInitiator,MAIN.Second_Initiator as twoInitiator," +
//				"ACTI.END_TIME_,ACTI.DELETE_REASON_," +
				"case when MAIN.State =5 then '已归档' when MAIN.State = 1 then '已删除' " +
				"when MAIN.State =8 then '省公司审批通过-等待商城' " +
				"else MAIN.task_def_key_name end as StatusName," +
				"case when MAIN.State =5 then 'archive' when MAIN.State =1 then 'delete' when MAIN.State = 8 then 'waitingCallInterface' " +
				"else MAIN.task_def_key end as TaskDefKey,decode(MAIN.sheet_id, null, '无',MAIN.sheet_id) as sheetId " +
				"from PNR_ACT_TRANSFER_OFFICE_MAIN MAIN " +
				" WHERE MAIN.State <> 2 and MAIN.themeinterface='"+themeinterface+"' and MAIN.One_Initiator=? ";
		
		listsql.add(userId);
	
		
		String whereSql = "";
		if (sendStartTime != null && !sendStartTime.equals("")) {
			whereSql += " and to_char(MAIN.send_time,'yyyy-mm-dd hh24:mi:ss') >=?";
			listsql.add(sendStartTime);

		}
		if (sendEndTime != null && !sendEndTime.equals("")) {
			whereSql += " and to_char(MAIN.send_time,'yyyy-mm-dd hh24:mi:ss') <=?";
			listsql.add(sendEndTime);
		}
		if (wsNum != null && !wsNum.equals("")) {
			whereSql += " and MAIN.Process_Instance_Id =?";
			listsql.add(wsNum.trim());
		}
		if (wsTitle != null && !wsTitle.equals("")) {
			whereSql += " and instr(MAIN.theme,?)>0 ";
			listsql.add(wsTitle.trim());
		}
		if (status != null && !status.equals("")) {
			if(!"delete".equals(status)){
				whereSql += " and MAIN.task_def_key = ? ";
				listsql.add(status);
			}else{
				whereSql += " and MAIN.State = 1 ";
			}
		}
		
		sql += selectSql
				+ whereSql
				+ " order by MAIN.send_time) temp1 where rownum <=?) temp2 where temp2.num >? ";
		listsql.add(endResult * pageSize);
		listsql.add(firstResult * pageSize);

		System.out.println("*****PnrTransferNewPrecheckDaoJDBC-getByCreatingWorkOrderList:由我创建的工单明细sql=" + sql);
		Object[] args = listsql.toArray();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,args);
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
			if (map.get("initiator") != null
					&& !"".equals(map.get("initiator"))) {
				model.setInitiator(map.get("initiator").toString());
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
			if (map.get("distributed_interface_time") != null) {
				if (!"".equals(map.get("distributed_interface_time"))) {
					try {
						model.setDistributedInterfaceTime(format.parse(map.get(
								"distributed_interface_time").toString()));
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
						model.setState(Integer.parseInt(map.get("State")
								.toString()));
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

			}

			if (map.get("oneInitiator") != null
					&& !"".equals(map.get("oneInitiator"))) {
				model.setOneInitiator(map.get("oneInitiator").toString());
			}
			if (map.get("twoInitiator") != null
					&& !"".equals(map.get("twoInitiator"))) {
				model.setTwoInitiator(map.get("twoInitiator").toString());
			}

			if (map.get("statusName") != null
					&& !"".equals(map.get("statusName"))) {
				model.setStatusName(map.get("statusName").toString());
			}
			if (map.get("sheetId") != null && !"".equals(map.get("sheetId"))) {
				model.setSheetId(map.get("sheetId").toString());
			}
			// if (map.get("END_TIME_") != null) {
			// if (!"".equals(map.get("END_TIME_"))) {
			// if(map.get("DELETE_REASON_")==null||"".equals(map.get("DELETE_REASON_"))){
			// model.setStatusName("已归档");
			// }else{
			// model.setStatusName("已删除");
			// }
			//				
			// }
			// }
			if (map.get("TaskDefKey") != null
					&& !"".equals(map.get("TaskDefKey"))) {
				model.setTaskDefKey(map.get("TaskDefKey").toString());
			}
			if (map.get("TaskId") != null && !"".equals(map.get("TaskId"))) {
				model.setTaskId(map.get("TaskId").toString());
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
		List listsql = new ArrayList();
		String themeinterface =com.boco.eoms.partner.process.util.CommonUtils.getTaskThemeinterfaceStr(processDefinitionKey,"m");
		String sql = "";
		String selectSql = "select count(*) as total from (" +
				"select * from (select  RES.* from ACT_RU_TASK RES " +
				"inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_ " +
				"WHERE D.KEY_ = ?) t, " +
						"transferNewPrechech_relation r " +
						"where t.task_def_key_ = r.current_link " +
						"and t.task_def_key_ in ('workOrderCheck','cityLineExamine','cityLineDirectorAudit','cityManageExamine','cityManageDirectorAudit')) ta,(select h.proc_inst_id_, h.task_def_key_, h.assignee_ from act_hi_taskinst h) hi," +
						"pnr_act_transfer_office_main m where ta.proc_inst_id_ = hi.proc_inst_id_ " +
						"and ta.before_link = hi.task_def_key_ and ta.proc_inst_id_ = m.process_instance_id " +
						"and hi.assignee_ = ? and (m.state != 3 and m.state != 6 and m.state != 8) and m.themeinterface='"+themeinterface+"'";


		listsql.add(processDefinitionKey);
				listsql.add(userId);
		String whereSql = "";
		if (conditionQueryModel.getWorkNumber() != null
				&& !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and m.process_instance_id =?";
			listsql.add(conditionQueryModel.getWorkNumber().trim());
		}
		if (conditionQueryModel.getTheme() != null
				&& !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and instr(m.theme,?)>0 ";
			listsql.add(conditionQueryModel.getTheme().trim());
		}

		sql = selectSql + whereSql;

		System.out.println("--------------本地网预检预修 工单抓回 条数sql=" + sql);

		Object[] args = listsql.toArray();
		int size= this.getJdbcTemplate().queryForInt(sql,args);
		return size;
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
		List listsql = new ArrayList();
		String themeinterface =com.boco.eoms.partner.process.util.CommonUtils.getTaskThemeinterfaceStr(processDefinitionKey,"m");
		
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		String selectSql = "select ta.id_ as TaskId,m.initiator as Initiator," +
				"m.one_initiator as OneInitiator,m.process_instance_id as ProcessInstanceId," +
				"m.send_time as SendTime,m.theme as Theme,m.task_assignee,m.state as State," +
				"m.submit_application_time as SubmitTime,m.city,m.country,m.work_type," +
				"m.workorder_type_name,m.sub_type_name,m.key_word,m.work_order_degree," +
				"m.rollback_flag,m.precheck_flag,m.project_amount,decode(m.sheet_id, null, '无', m.sheet_id) as sheetId," +
				"ta.name_ as statusName,ta.task_def_key_ as taskDefKey from (select * from (" +
				"select  RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D " +
				"on RES.PROC_DEF_ID_ = D.ID_ WHERE D.KEY_ = ?) t, " +
						"transferNewPrechech_relation r where t.task_def_key_ = r.current_link " +
						"and t.task_def_key_ in ('workOrderCheck','cityLineExamine','cityLineDirectorAudit','cityManageExamine','cityManageDirectorAudit')) ta,(select h.proc_inst_id_, h.task_def_key_, h.assignee_ from act_hi_taskinst h) hi," +
						"pnr_act_transfer_office_main m where ta.proc_inst_id_ = hi.proc_inst_id_ and ta.before_link = hi.task_def_key_ and ta.proc_inst_id_ = m.process_instance_id " +
						"and hi.assignee_ = ? and (m.state != 3 and m.state != 6 and m.state != 8) and m.themeinterface='"+themeinterface+"'";
		
		listsql.add(processDefinitionKey);
		listsql.add(userId);
		
		String whereSql = "";
		if (conditionQueryModel.getWorkNumber() != null
				&& !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and m.process_instance_id =?";
			listsql.add(conditionQueryModel.getWorkNumber().trim());
		}
		if (conditionQueryModel.getTheme() != null
				&& !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and instr(m.theme,?)>0 ";
			listsql.add(conditionQueryModel.getTheme().trim());
		}
		
		sql += selectSql
				+ whereSql
				+ " order by  m.send_time) temp1 where rownum <=?) temp2 where temp2.num >? ";

		listsql.add(endResult * pageSize);
		listsql.add(firstResult * pageSize);
		System.out.println("--------------本地网预检预修 工单抓回 明细sql=" + sql);
		Object[] args = listsql.toArray();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,args);
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
			sql.append(" and  p.faultdescription like '%")
					.append(photoDescribe).append("%'");
		}
		if (faultLocation != null && !"".equals(faultLocation)) {
			sql.append(" and  p.faultLocation like '%")
			.append(faultLocation).append("%'");
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
	
	/**
	 * 查询事情照片
	 * 
	 * @author WangJun 
	 * @param param	参数可以任意的封装
	 * @return
	 */
	public List<PnrAndroidPhotoFile> getPrecheckPhotoes(Map<String,String> param){
		
		List listobj = new ArrayList();
		//拍照人
		String person = "";
		if (param.get("photoCreate") != null && !"".equals(param.get("photoCreate"))) {
			person = param.get("photoCreate");
			
		} else {
			person = param.get("userId");
		}
		
		String sql="";//superUser
		if("superUser".equals(param.get("userId"))){
			sql="select * from pnr_android_photo p where p.user_id = 'superUser' and nvl(p.state, 0) != '1'";
		}else{
			sql="select *" +
			"  from pnr_android_photo p" + 
			" where p.city = (select substr(nvl(a.areaid, '0'), 1, 4)" + 
			"                   from pnr_user u" + 
			"                   left join taw_system_dept d" + 
			"                     on u.dept_id = d.deptid" + 
			"                   left join taw_system_area a" + 
			"                     on d.areaid = a.areaid" + 
			"                  where 1 = 1" + 
			"                    and d.deleted = '0'" + 
			"                    and u.user_id = ?)" + 
			"   and p.user_id = ?" + 
			"   and nvl(p.state, 0) != '1'" ;
			
			listobj.add(person);
			listobj.add(person);
		}
			
		//故障描述	
		if (param.get("photoDescribe") != null && !"".equals(param.get("photoDescribe"))) {
			sql+=" and instr(p.faultdescription,?)>0 ";
			
			listobj.add(param.get("photoDescribe"));
		}
		//故障地点
		if (param.get("faultLocation") != null && !"".equals(param.get("faultLocation"))) {
			sql+=" and instr(p.faultLocation,?)>0 ";
			listobj.add(param.get("faultLocation"));
		}
		//拍照时间
		if (param.get("createPhotoTime") != null && !"".equals(param.get("createPhotoTime"))) {
			sql+=" and to_date(p.createtime, 'yyyy-mm-dd hh24:mi:ss') >=  to_date(?, 'yyyy-mm-dd hh24:mi:ss')";
			listobj.add(param.get("createPhotoTime"));
		}
		
		sql+=" and (nvl(p.photo_type, 'otherProcess') = 'otherProcess'";
		
		//照片类型
		if(param.get("photoType") != null && !"".equals(param.get("photoType"))){
			sql+=" or (p.photo_type = ? ";
			listobj.add(param.get("photoType"));
			
			//照片子类型
			if(param.get("photoSubType") != null && !"".equals(param.get("photoSubType"))){
				sql+=" and p.photo_sub_type = ? ";
				listobj.add(param.get("photoSubType"));
			}
			sql+=")";
			
		}
		
		sql+=")";
		
		System.out.println("------事前图片查询sql=" + sql);
		Object[] args = listobj.toArray();
		List<Map> list = this.getJdbcTemplate().queryForList(sql.toString(),args);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<PnrAndroidPhotoFile> photoList = new ArrayList<PnrAndroidPhotoFile>();
		if (list != null && list.size() > 0) {
			for (Map map : list) {
				PnrAndroidPhotoFile onePhoto = new PnrAndroidPhotoFile();
				onePhoto.setId(map.get("ID") == null ? null : map.get("ID")
						.toString());
				onePhoto.setPicture_id(map.get("PICUTRE_ID") == null ? null
						: map.get("PICUTRE_ID").toString());
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
		Object[] args ={processInstanceId};
		
		StringBuilder sql = new StringBuilder(
				"select * from pnr_act_precheck_photo_ship sp ");
		sql.append(" left join pnr_android_photo ap on sp.photo_id=ap.id ");
		sql.append(" where sp.processinstance_id=?");
		System.out.println("----------新建工单图片查看sql=" + sql);
		List<Map> list = this.getJdbcTemplate().queryForList(sql.toString(),args);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<PnrAndroidPhotoFile> photoList = new ArrayList<PnrAndroidPhotoFile>();
		if (list != null && list.size() > 0) {
			for (Map map : list) {
				PnrAndroidPhotoFile onePhoto = new PnrAndroidPhotoFile();
				onePhoto.setId(map.get("ID") == null ? null : map.get("ID")
						.toString());
				onePhoto.setPicture_id(map.get("PICUTRE_ID") == null ? null
						: map.get("PICUTRE_ID").toString());
				onePhoto.setPath(map.get("PATH") == null ? null : map.get("PATH")
						.toString());
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
//				if(map.get("photo_type")!=null && !"".equals(map.get("photo_type"))){
//					if("start".equals(map.get("photo_type").toString())){
//						onePhoto.setPhotoType("新光缆段落起点照片");
//					}else if("end".equals(map.get("photo_type").toString())){
//						onePhoto.setPhotoType("新光缆段落终点照片");
//					}
//				}else{
//					onePhoto.setPhotoType(map.get("photo_type") == null ? null : map.get(
//					"photo_type").toString());
//				} 
				
				if(map.get("photo_flag")!=null && !"".equals(map.get("photo_flag"))){
					if("start".equals(map.get("photo_flag").toString())){
						onePhoto.setPhotoType("新光缆段落起点照片");
					}else if("end".equals(map.get("photo_flag").toString())){
						onePhoto.setPhotoType("新光缆段落终点照片");					
					}
				}else{
					onePhoto.setPhotoType(map.get("photo_flag") == null ? null : map.get(
					"photo_flag").toString());
				} 
				
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
		Object [] args ={state,photoId};
		StringBuilder sql = new StringBuilder("update pnr_android_photo p set p.state=?");
		sql.append(" where p.id=?");

		this.getJdbcTemplate().update(sql.toString(), args);

	}

	@Override
	public PnrAndroidPhotoFile getOnePnrAndroidPhotoFileById(String id) {
		Object[] args = {id};
		StringBuilder sql = new StringBuilder(
				"select * from pnr_android_photo p where p.id=?");
		List<Map> list = this.getJdbcTemplate().queryForList(sql.toString(),args);
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
		Object[] args ={id};
		StringBuilder sql = new StringBuilder(
				"select p.filedata from pnr_android_photo p where p.id=?");
		List<Map> list = this.getJdbcTemplate().queryForList(sql.toString(),args);
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

		List listsql = new ArrayList();
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
//		String selectSql = "select ACTI.PROC_INST_ID_ as processInstanceId,decode(MAIN.sheet_id, null, '无', MAIN.sheet_id) as sheetId,MAIN.THEME AS theme,MAIN.city ,MAIN.country,MAIN.work_type,MAIN.workorder_type_name,MAIN.sub_type_name ,MAIN.key_word ," +
//				"MAIN.work_order_degree,MAIN.project_amount,MAIN.INITIATOR AS initiator," +
//				"MAIN.submit_application_time as SubmitTime,decode(ACTI.Delete_Reason_, 'delete', '已删除', '已归档') as statusName," +
//				"ACTI.Delete_Reason_,MAIN.TASK_ASSIGNEE,MAIN.SEND_TIME as SEND_TIME,MAIN.one_initiator,u.deptid,nvl(MAIN.recent_main_scenes_name, '无') as recent_main_scenes_name,nvl(MAIN.recent_copy_scenes_name, '无') as recent_copy_scenes_name " +
//				"from (select RES.*,decode(RES.Delete_Reason_,'delete','delete','archived') as tempStatusName " +
//				"from (select K.* from ACT_HI_PROCINST K inner join ACT_RE_PROCDEF D " +
//				"on K.PROC_DEF_ID_ = D.ID_ WHERE D.KEY_ = ?) RES left join (select TAS.* from ACT_HI_TASKINST TAS where TAS.id_ in (select max(TAS.id_) from ACT_HI_TASKINST TAS group by TAS.proc_inst_id_)) TAS on RES.PROC_INST_ID_ = TAS.PROC_INST_ID_ WHERE RES.END_TIME_ is not NULL and (exists (select LINK.USER_ID_ from ACT_HI_IDENTITYLINK LINK " +
//				"where USER_ID_ = ? and LINK.PROC_INST_ID_ = RES.ID_))) ACTI,PNR_ACT_TRANSFER_OFFICE_MAIN MAIN,taw_system_user u where ACTI.Proc_Inst_Id_ = MAIN.PROCESS_INSTANCE_ID(+) and MAIN.task_assignee = u.userid(+) " +
//				"and MAIN.THEMEINTERFACE = '"+themeInterface+"'";
		String selectSql =  "select MAIN.process_instance_id as processInstanceId,\n" +
							"       decode(MAIN.sheet_id, null, '无', MAIN.sheet_id) as sheetId,\n" + 
							"       MAIN.THEME AS theme,\n" + 
							"       MAIN.city,\n" + 
							"       MAIN.country,\n" + 
							"       MAIN.work_type,\n" + 
							"       MAIN.workorder_type_name,\n" + 
							"       MAIN.sub_type_name,\n" + 
							"       MAIN.key_word,\n" + 
							"       MAIN.work_order_degree,\n" + 
							"       MAIN.project_amount,\n" + 
							"       MAIN.INITIATOR AS initiator,\n" + 
							"       MAIN.submit_application_time as SubmitTime,\n" + 
							"       '已归档' as statusName,\n" + 
							"       --   ACTI.Delete_Reason_,\n" + 
							"       MAIN.TASK_ASSIGNEE,\n" + 
							"       MAIN.SEND_TIME as SEND_TIME,\n" + 
							"       MAIN.one_initiator,\n" + 
							"       u.deptid,\n" + 
							"       nvl(MAIN.recent_main_scenes_name, '无') as recent_main_scenes_name,\n" + 
							"       nvl(MAIN.recent_copy_scenes_name, '无') as recent_copy_scenes_name\n" + 
							"  from PNR_ACT_TRANSFER_OFFICE_MAIN MAIN, taw_system_user u\n" + 
							" where MAIN.task_assignee = u.userid(+)\n" + 
							"   and MAIN.THEMEINTERFACE = ?\n" + 
							"   and main.state = 5\n" + 
							"   and main.task_def_key = 'archive'\n" + 
							"   and (exists (select 1\n" + 
							"                  from ACT_HI_IDENTITYLINK LINK\n" + 
							"                 where USER_ID_ = ?\n" + 
							"                   and LINK.PROC_INST_ID_ = MAIN.process_instance_id))";
		listsql.add(themeInterface);
		listsql.add(userId);
		
		String whereSql = "";
		
		if (sendStartTime != null && !sendStartTime.equals("")) {
			whereSql += " and to_char(MAIN.Send_Time,'yyyy-mm-dd hh24:mi:ss') >=?";
			listsql.add(sendStartTime);
		}
		if (sendEndTime != null && !sendEndTime.equals("")) {
			whereSql += " and to_char(MAIN.Send_Time,'yyyy-mm-dd hh24:mi:ss') <=?";
			listsql.add(sendEndTime);
		}
		if (wsNum != null && !wsNum.equals("")) {
			whereSql += " and MAIN.process_instance_id =?";
			listsql.add(wsNum.trim());
		}
//		if (wsNum != null && !wsNum.equals("")) {
//			whereSql += " and ACTI.PROC_INST_ID_ =?";
//			listsql.add(wsNum.trim());
//		}
		if (wsTitle != null && !wsTitle.equals("")) {
			whereSql += " and instr(MAIN.THEME,?)>0";
			listsql.add(wsTitle.trim());
		}
//		if (status != null && !status.equals("")) {
//			whereSql += " and tempStatusName = ?";
//			listsql.add(status);
//		}
		
		sql += selectSql
				+ whereSql
				+ " order by MAIN.SEND_TIME) temp1 where rownum <=?) temp2 where temp2.num >? ";
		
		listsql.add(endResult * pageSize);
		listsql.add(firstResult * pageSize);

		System.out.println("--------本地网或干线预检预修 已归档工单明细sql=" + sql);
		Object[] args = listsql.toArray();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,args);
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
			
			//派单时间
			if (map.get("SEND_TIME") != null && !"".equals(map.get("SEND_TIME").toString())) {
				try {
					model.setSendTime(format.parse(map.get("SEND_TIME")
							.toString()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			//工单生成人
			if (map.get("one_initiator") != null
					&& !"".equals(map.get("one_initiator"))) {
				model.setOneInitiator(map.get("one_initiator").toString());
			}
			
			//所属部分
			if (map.get("deptid") != null&& !"".equals(map.get("deptid").toString())) {
				model.setDeptId(map.get("deptid").toString());
			}
			
			//主场景
			if (map.get("recent_main_scenes_name") != null&& !"".equals(map.get("recent_main_scenes_name").toString())) {
				model.setRecentMainScenesName(map.get("recent_main_scenes_name").toString());
			}
			//子场景
			if (map.get("recent_copy_scenes_name") != null&& !"".equals(map.get("recent_copy_scenes_name").toString())) {
				model.setRecentCopyScenesName(map.get("recent_copy_scenes_name").toString());
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
		
		List listsql = new ArrayList();
		
		String sql = "";
//		String selectSql = "select count(ACTI.PROC_INST_ID_ ) as total " +
//				"from (select RES.*,decode(RES.Delete_Reason_,'delete','delete','archived') as tempStatusName " +
//				"from (select K.* from ACT_HI_PROCINST K inner join ACT_RE_PROCDEF D " +
//				"on K.PROC_DEF_ID_ = D.ID_ WHERE D.KEY_ = ?) RES " +
//						"left join (select TAS.* from ACT_HI_TASKINST TAS " +
//						"where TAS.id_ in (select max(TAS.id_) from ACT_HI_TASKINST TAS " +
//						"group by TAS.proc_inst_id_)) TAS on RES.PROC_INST_ID_ = TAS.PROC_INST_ID_ " +
//						"WHERE RES.END_TIME_ is not NULL and (exists (select LINK.USER_ID_ " +
//						"from ACT_HI_IDENTITYLINK LINK where USER_ID_ = ? and LINK.PROC_INST_ID_ = RES.ID_))) ACTI," +
//						"PNR_ACT_TRANSFER_OFFICE_MAIN MAIN,taw_system_user u " +
//						"where ACTI.Proc_Inst_Id_ = MAIN.PROCESS_INSTANCE_ID(+) and MAIN.task_assignee = u.userid(+) " +
//						"and MAIN.THEMEINTERFACE = '"+themeInterface+"'";
		
		String selectSql =  "select count(MAIN.process_instance_id) as total " + 
							"  from PNR_ACT_TRANSFER_OFFICE_MAIN MAIN, taw_system_user u\n" + 
							" where MAIN.task_assignee = u.userid(+)\n" + 
							"   and MAIN.THEMEINTERFACE = ?\n" + 
							"   and main.state = 5\n" + 
							"   and main.task_def_key = 'archive'\n" + 
							"   and (exists (select 1\n" + 
							"                  from ACT_HI_IDENTITYLINK LINK\n" + 
							"                 where USER_ID_ = ?\n" + 
							"                   and LINK.PROC_INST_ID_ = MAIN.process_instance_id))";
		System.out.println("themeInterface="+themeInterface);
		listsql.add(themeInterface);
		System.out.println("userId="+userId);
		listsql.add(userId);
		
		String whereSql = "";
		if (sendStartTime != null && !sendStartTime.equals("")) {
			whereSql += " and to_char(MAIN.Send_Time,'yyyy-mm-dd hh24:mi:ss') >=?";
			listsql.add(sendStartTime);
			System.out.println("sendStartTime="+sendStartTime);
		}
		if (sendEndTime != null && !sendEndTime.equals("")) {
			whereSql += " and to_char(MAIN.Send_Time,'yyyy-mm-dd hh24:mi:ss') <=?";
			listsql.add(sendEndTime);
			System.out.println("sendEndTime="+sendEndTime);
		}
		if (wsNum != null && !wsNum.equals("")) {
			whereSql += " and MAIN.process_instance_id =?";
			listsql.add(wsNum.trim());
			System.out.println("wsNum="+wsNum);
		}
//		if (wsNum != null && !wsNum.equals("")) {
//			whereSql += " and ACTI.PROC_INST_ID_ =?";
//			listsql.add(wsNum.trim());
//		}
		if (wsTitle != null && !wsTitle.equals("")) {
			whereSql += " and instr(MAIN.THEME,?)>0";
			listsql.add(wsTitle.trim());
			System.out.println("wsTitle="+wsTitle);
		}
//		if (status != null && !status.equals("")) {
//			whereSql += " and tempStatusName = ?";
//			listsql.add(status);
//		}
		sql += selectSql + whereSql;
		
		System.out.println("--------本地网或干线预检预修 已归档工单条数sql=" + sql);
		
		Object[] args = listsql.toArray();
		int size = this.getJdbcTemplate().queryForInt(sql,args);
		return size;
	}

	/**
	 * add by wyl 20150430
	 * 获取下载附件个数
	 */
	@Override
	public int getDownAttachMentCount(String userid,
			ConditionQueryDAMModel conditionQueryModel) {
		
		List listobj = new ArrayList();
		String sql = "";
		String selectSql = "select count(*) total from pnr_act_transfer_office_main m   left join pnr_act_transfer_attachment t on m.process_instance_id = t.process_instance_id   left join taw_commons_accessories tr on t.accessories_id = tr.id   left join act_ru_task k on m.process_instance_id = k.proc_inst_id_ where 1=1 ";

		String whereSql = "";

		//开始时间
		if (conditionQueryModel.getSendStartTime() != null
				&& !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and m.send_time >=to_date(?,'yyyy-mm-dd hh24:mi:ss')";
			listobj.add(conditionQueryModel.getSendStartTime());
		}
		//结束时间
		if (conditionQueryModel.getSendEndTime() != null
				&& !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and m.send_time <= to_date(?,'yyyy-mm-dd hh24:mi:ss')";
			
			listobj.add(conditionQueryModel.getSendEndTime());
		}

		//地市
		if (conditionQueryModel.getCity() != null
				&& !conditionQueryModel.getCity().equals("")) {
			whereSql += " and m.city =?";

			listobj.add(conditionQueryModel.getCity());
		}
		//区县
		if (conditionQueryModel.getCountry() != null
				&& !conditionQueryModel.getCountry().equals("")) {
			whereSql += " and m.country = ?";
			listobj.add(conditionQueryModel.getCountry());
		}
		
		//当前登陆人地市
		if (conditionQueryModel.getArea() != null
				&& !conditionQueryModel.getArea().equals("")) {
			if(conditionQueryModel.getArea().trim().length()==6){
				whereSql += " and m.country = ?";
				listobj.add(conditionQueryModel.getArea());
			}
			
		}
		
		//工单类型themeinterface  transferOffice抢修工单；maleGuest 公客；interface 本地网预检预修；arteryPrecheck 干线预检预修；
		//预检预修-本地网、预检预修-干线、大修改造
		//逗号分隔 '',''
		if (conditionQueryModel.getThemeinterface() != null
				&& !conditionQueryModel.getThemeinterface().equals("")) {
			whereSql += " and m.themeinterface in (select * from TABLE( cast(f_str2List(?) as varchar2TableType)))";
			listobj.add(conditionQueryModel.getThemeinterface());

		}
		
		//所属环节 
		//逗号分隔 '',''
		if (conditionQueryModel.getTaskdefkey() != null
				&& !conditionQueryModel.getTaskdefkey().equals("")) {
			whereSql += " and k.task_def_key_ in (select * from TABLE( cast(f_str2List(?) as varchar2TableType)))";
			listobj.add(conditionQueryModel.getTaskdefkey());

		}

		sql = selectSql + whereSql;

		System.out.println("--------------下载附件sql=" + sql);
		Object[] args = listobj.toArray();
		int size = this.getJdbcTemplate().queryForInt(sql,args);
		return size;

	}
	
	/**
	 * 通过照片ID删除事情照片
	 * @param id 照片ID
	 */
	public void deletePictureById(String id) {
		Object[] args = {id};
		String relationSql="delete from pnr_act_precheck_photo_ship s where s.photo_id =?";
		System.out.println("-----------------通过照片ID删除关联表pnr_act_precheck_photo_ship中的数据relationSql="+relationSql);
		this.getJdbcTemplate().update(relationSql,args);
		
		String sql = "delete from pnr_android_photo p where p.id =?";
		System.out.println("-----------------通过照片ID删除事情照片sql="+sql);
		
		this.getJdbcTemplate().update(sql,args);
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
		Object[] args = {processInstanceId};
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
		sql.append("   where pr.process_instance_id =?  order by pr.order_code asc");
		
		/*String sql= " select ra.* from pnr_act_room_assets_relation pr" +
					" left join  room_demolition_assets ra " +
					" on pr.room_assets_id = ra.id" +
					" where pr.process_instance_id='"+processInstanceId+"'";*/
		System.out.println("ssssssssssssssss"+sql.toString());
		List<Map> list = this.getJdbcTemplate().queryForList(sql.toString(),args);
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
	
	@Override
	public List<TaskModel> getLocalNetworkWorkOrderList(String areaid,String userid,
			String flag, String processKey,
			ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize) {

		List listsql = new ArrayList();
		
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		//String sql = "";
		String themeinterfaceWhere=""; 
		if("transferNewPrechechProcess".equals(processKey)){
			themeinterfaceWhere = " and m.themeinterface ='interface' ";
	
		}else if("transferArteryPrecheckProcess".equals(processKey)){
			themeinterfaceWhere = " and m.themeinterface ='arteryPrecheck' ";
		
		}else if("overHaulNewProcess".equals(processKey)){			
			themeinterfaceWhere = " and m.themeinterface in ('overhaul','reform') ";
		}
		
		String selectSql =  "select m.*,case  when m.state =3 then 'waitOrder' when m.State =8 then 'waitingCallInterface' when m.state = 5 then 'archive' when m.state = 1 then 'delete' " +
						   "when s.id is not null then 'spotChecks' else m.task_def_key end as TaskDefKey," +
						   "case  when m.state =3 then '待办' when m.State =8 then '省公司审批通过-等待商城' when m.State =5 then '已归档' when m.State =1 then '已删除' " +
						   "when s.id is not null then '已抽查' else m.task_def_key_name end as StatusName," +
						   "m.sum_need_cost_of_party_b as totalFee,m.project_amount-m.sum_need_cost_of_party_b as totalMaterialCost " +
						   "from pnr_act_transfer_office_main m left join " +
						   "( select sc.id,sc.process_instance_id ,row_number() over(partition by sc.process_instance_id order by sc.insert_date desc) num " +
						   "from  PNR_TRANSFER_SPOTCHECK sc) s on m.process_instance_id = s.process_instance_id and s.num =1 " +
						   " where m.state <> 2 " +themeinterfaceWhere;
		
		String whereSql = "";
		String finalSql = "";
		
		if(areaid!=null && !"".equals(areaid)){
			if((StaticMethod.nullObject2String(areaid)).length()==2){
				
			}else if((StaticMethod.nullObject2String(areaid)).length()==4){
				whereSql += " and m.city=? ";
				listsql.add(areaid);
			}else if((StaticMethod.nullObject2String(areaid)).length()==6){
				whereSql += " and m.country=? ";
				listsql.add(areaid);
			}
		}
		if (conditionQueryModel.getSendStartTime() != null
				&& !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >=?";
			listsql.add(conditionQueryModel.getSendStartTime());
			
		}
		if (conditionQueryModel.getSendEndTime() != null
				&& !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?";
			listsql.add(conditionQueryModel.getSendEndTime());
		}
		if (conditionQueryModel.getWorkNumber() != null
				&& !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and m.process_instance_id =?";
			listsql.add(conditionQueryModel.getWorkNumber().trim());
		}
		if (conditionQueryModel.getTheme() != null
				&& !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and instr(m.theme,?) >0";
			listsql.add(conditionQueryModel.getTheme().trim());
		}
		
		if (conditionQueryModel.getCity() != null
				&& !conditionQueryModel.getCity().equals("")) {
			whereSql += " and m.city = ?";
			listsql.add(conditionQueryModel.getCity());

		}
		if (conditionQueryModel.getCountry() != null
				&& !conditionQueryModel.getCountry().equals("")) {
			whereSql += " and m.country = ?";
			listsql.add(conditionQueryModel.getCountry());

		}
		if (conditionQueryModel.getLineType() != null
				&& !conditionQueryModel.getLineType().equals("")) {
			whereSql += " and m.work_type =?";
			listsql.add(conditionQueryModel.getLineType());

		}
		if (conditionQueryModel.getWorkOrderType() != null
				&& !conditionQueryModel.getWorkOrderType().equals("")) {
			whereSql += " and m.workorder_type_name =?";

			listsql.add(conditionQueryModel.getWorkOrderType());
		}
		if (conditionQueryModel.getPrecheckFlag() != null
				&& !conditionQueryModel.getPrecheckFlag().equals("")) {
			whereSql += " and m.precheck_flag =?";
			listsql.add(conditionQueryModel.getPrecheckFlag());

		}
		if (conditionQueryModel.getMainSceneId() != null
				&& !conditionQueryModel.getMainSceneId().equals("")) {
			whereSql += " and m.main_scene_id = ?";
			
			listsql.add(conditionQueryModel.getMainSceneId());
		}
		if (conditionQueryModel.getWorkOrderDegree() != null
				&& !conditionQueryModel.getWorkOrderDegree().equals("")) {
			whereSql += " and m.work_order_degree = ?";
			listsql.add(conditionQueryModel.getWorkOrderDegree());
			
		}
		if (conditionQueryModel.getKeyWord() != null
				&& !conditionQueryModel.getKeyWord().equals("")) {
			whereSql += " and m.key_word = ?";
			listsql.add(conditionQueryModel.getKeyWord());
			
		}
		if (conditionQueryModel.getProcessType() != null
				&& !conditionQueryModel.getProcessType().equals("")) {
			whereSql += " and m.themeinterface = ?";
			
			listsql.add(conditionQueryModel.getProcessType());
			
		}		
		if (conditionQueryModel.getSubTypeName() != null
				&& !conditionQueryModel.getSubTypeName().equals("")) {
			whereSql += " and m.sub_type_name = ?";			
			listsql.add(conditionQueryModel.getSubTypeName());			
		}
		//批复开始时间
		if (conditionQueryModel.getApproveStartTime() != null
				&& !conditionQueryModel.getApproveStartTime().equals("")) {
			whereSql += " and to_char(m.distributed_interface_time,'yyyy-mm-dd hh24:mi:ss') >=?";
			listsql.add(conditionQueryModel.getApproveStartTime());
			
		}
		//批复结束时间
		if (conditionQueryModel.getApproveEndTime() != null
				&& !conditionQueryModel.getApproveEndTime().equals("")) {
			whereSql += " and to_char(m.distributed_interface_time,'yyyy-mm-dd hh24:mi:ss') <= ?";
			listsql.add(conditionQueryModel.getApproveEndTime());
		}
		
		finalSql = " order by m.send_time ) temp1 where rownum <=?";
		listsql.add(endResult * pageSize);
		
		if (conditionQueryModel.getStatus() != null
				&& !conditionQueryModel.getStatus().equals("")) {
			finalSql += " and temp1.TaskDefKey = ? ";
			listsql.add(conditionQueryModel.getStatus());

		}
		
		finalSql +=") temp2 where temp2.num >? ";		
		listsql.add(firstResult * pageSize);
		
		sql += selectSql + whereSql+finalSql;	
		
		
		System.out.println("---------------本地网、干线、大修改造工单查询PnrTransferNewPrecheckDaoJDBC-getLocalNetworkWorkOrderList:sql=" + sql);

		Object[] args = listsql.toArray();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,args);
		for (Map map : list) {
			TaskModel model = new TaskModel();
			if (map.get("TaskId") != null && !"".equals(map.get("TaskId"))) {

				model.setTaskId(map.get("TaskId").toString());
			}

			if (map.get("taskDefKey") != null
					&& !"".equals(map.get("taskDefKey"))) {

				model.setTaskDefKey(map.get("taskDefKey").toString());
			}
			if (map.get("statusName") != null
					&& !"".equals(map.get("statusName"))) {

				model.setStatusName(map.get("statusName").toString());
			}

			if (map.get("Initiator") != null
					&& !"".equals(map.get("Initiator"))) {

				model.setInitiator(map.get("Initiator").toString());
			}
			if (map.get("OneInitiator") != null
					&& !"".equals(map.get("OneInitiator"))) {

				model.setOneInitiator(map.get("OneInitiator").toString());
			}
			if (map.get("process_instance_id") != null
					&& !"".equals(map.get("process_instance_id"))) {

				model.setProcessInstanceId(map.get("process_instance_id")
						.toString());
			}
			if (map.get("submit_application_time") != null) {
				if (!"".equals(map.get("submit_application_time"))) {
					try {
						model.setApplicationTime(format.parse(map.get(
								"submit_application_time").toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

			}
			if (map.get("distributed_interface_time") != null) {
				if (!"".equals(map.get("distributed_interface_time"))) {
					try {
						model.setDistributedInterfaceTime(format.parse(map.get(
								"distributed_interface_time").toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

			}
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
			if (map.get("sheet_Id") != null && !"".equals(map.get("sheet_Id"))) {
				model.setSheetId(map.get("sheet_Id").toString());
			}
			if (map.get("rollback_flag") != null
					&& !"".equals(map.get("rollback_flag"))) {
				model.setRollbackFlag(map.get("rollback_flag").toString());
			}
			if (map.get("precheck_flag") != null
					&& !"".equals(map.get("precheck_flag"))) {
				model.setPrecheckFlag(map.get("precheck_flag").toString());
			}
			if (map.get("compensate") != null
					&& !"".equals(map.get("compensate"))) {
				model.setCompensate(Double.parseDouble(map.get("compensate")
						.toString()));
			}
			
			
			if (map.get("laying_cable") != null
					&& !"".equals(map.get("laying_cable"))) {
				model.setLayingCable(Double.parseDouble(map.get("laying_cable")
						.toString()));
			}
			if (map.get("excavation_trench") != null
					&& !"".equals(map.get("excavation_trench"))) {
				model.setExcavationTrench(Double.parseDouble(map.get("excavation_trench")
						.toString()));
			}
			if (map.get("repair_pipeline") != null
					&& !"".equals(map.get("repair_pipeline"))) {
				model.setRepairPipeline(Double.parseDouble(map.get("repair_pipeline")
						.toString()));
			}
			if (map.get("righting_demolition_pole") != null
					&& !"".equals(map.get("righting_demolition_pole"))) {
				model.setRightingDemolitionPole(Double.parseDouble(map.get("righting_demolition_pole")
						.toString()));
			}
			if (map.get("wire_laying") != null
					&& !"".equals(map.get("wire_laying"))) {
				model.setWireLaying(Double.parseDouble(map.get("wire_laying")
						.toString()));
			}
			if (map.get("fiber_optic_cable_connector") != null
					&& !"".equals(map.get("fiber_optic_cable_connector"))) {
				model.setFiberOpticCableConnector(Double.parseDouble(map.get("fiber_optic_cable_connector")
						.toString()));
			}
			if (map.get("main_quantity_other") != null
					&& !"".equals(map.get("main_quantity_other"))) {
				model.setMainQuantityOther(map.get("main_quantity_other")
						.toString());
			}
			if (map.get("construction_reasons") != null
					&& !"".equals(map.get("construction_reasons"))) {
				model.setConstructionReasons(map.get("construction_reasons")
						.toString());
			}
			if (map.get("network_status") != null
					&& !"".equals(map.get("network_status"))) {
				model.setNetworkStatus(map.get("network_status")
						.toString());
			}
			if (map.get("construction_main_content") != null
					&& !"".equals(map.get("construction_main_content"))) {
				model.setConstructionMainContent(map.get("construction_main_content")
						.toString());
			}
			if (map.get("workorder_type_name") != null
					&& !"".equals(map.get("workorder_type_name"))) {
				model.setWorkorderTypeName(map.get("workorder_type_name")
						.toString());
			}
			if (map.get("sub_type_name") != null
					&& !"".equals(map.get("sub_type_name"))) {
				model.setSubTypeName(map.get("sub_type_name")
						.toString());
			}
			if (map.get("create_str") != null
					&& !"".equals(map.get("create_str"))) {
				model.setCreateStr(map.get("create_str")
						.toString());
			}else{
				model.setCreateStr("无");
			}
			if (map.get("delete_str") != null
					&& !"".equals(map.get("delete_str"))) {
				model.setDeleteStr(map.get("delete_str")
						.toString());
			}else{
				model.setDeleteStr("无");
			}
			if (map.get("totalFee") != null
					&& !"".equals(map.get("totalFee"))) {
				model.setTotalFee(map.get("totalFee")
						.toString());
			}
			if (map.get("totalMaterialCost") != null
					&& !"".equals(map.get("totalMaterialCost"))) {
				model.setTotalMaterialCost(map.get("totalMaterialCost")
						.toString());
			}
			if (map.get("CALCULATE_INCOME_RATIO") != null
					&& !"".equals(map.get("CALCULATE_INCOME_RATIO"))) {
				model.setCalculateIncomeRatio(map.get("CALCULATE_INCOME_RATIO")
						.toString());
			}
			
			//工单处理-项目金额估算
			if (map.get("worker_project_amount") != null
					&& !"".equals(map.get("worker_project_amount"))) {
				model.setWorkerProjectAmount(Double.valueOf(map.get("worker_project_amount").toString()));
			}
			
			//抽查-项目金额估算
			if (map.get("orderaudit_project_amount") != null
					&& !"".equals(map.get("orderaudit_project_amount"))) {
				model.setOrderauditProjectAmount(Double.valueOf(map.get("orderaudit_project_amount").toString()));
			}
			if (map.get("kind_restitution") != null && !"".equals(map.get("kind_restitution"))) {
				model.setKindRestitution(Double.parseDouble(map.get("kind_restitution").toString()));
			}else{
				model.setKindRestitution(Double.parseDouble("0.0"));
			}
			
			r.add(model);
		}
		return r;
	}
	@Override
	public int getLocalNetworkWorkOrderListCount(String areaid,String userid, String flag,
			String processKey, ConditionQueryModel conditionQueryModel) {
		List listsql = new ArrayList();
				
		String sql = "";
		String themeinterfaceWhere=""; 
		if("transferNewPrechechProcess".equals(processKey)){
			themeinterfaceWhere = " and m.themeinterface ='interface' ";
			
		}else if("transferArteryPrecheckProcess".equals(processKey)){
			themeinterfaceWhere = " and m.themeinterface ='arteryPrecheck' ";
			
		}else if("overHaulNewProcess".equals(processKey)){			
			themeinterfaceWhere = " and m.themeinterface in ('overhaul','reform') ";
		}
		String selectSql = "select count(w.process_instance_id) as total " +
						   " from (" +
						   "select m.process_instance_id,case  when m.state =3 then 'waitOrder' when m.State =8 then 'waitingCallInterface' when m.state = 5 then 'archive' when m.state = 1 then 'delete' " +
						   "when s.id is not null then 'spotChecks' else m.task_def_key end as TaskDefKey "+
		   "from pnr_act_transfer_office_main m left join " +
		   "( select sc.id,sc.process_instance_id ,row_number() over(partition by sc.process_instance_id order by sc.insert_date desc) num " +
		   "from  PNR_TRANSFER_SPOTCHECK sc) s on m.process_instance_id = s.process_instance_id and s.num =1 " +
		   " where m.state <> 2 " +themeinterfaceWhere;
						   
		String whereSql = "";
		String finalSql = "";
		
		
		if(areaid!=null && !"".equals(areaid)){
			if((StaticMethod.nullObject2String(areaid)).length()==2){
				
			}else if((StaticMethod.nullObject2String(areaid)).length()==4){
				whereSql += " and m.city=? ";
				listsql.add(areaid);
			}else if((StaticMethod.nullObject2String(areaid)).length()==6){
				whereSql += " and m.country=? ";
				listsql.add(areaid);
			}
		}
		if (conditionQueryModel.getSendStartTime() != null
				&& !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >=?";
			listsql.add(conditionQueryModel.getSendStartTime());
			
		}
		if (conditionQueryModel.getSendEndTime() != null
				&& !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?";
			listsql.add(conditionQueryModel.getSendEndTime());
		}
		if (conditionQueryModel.getWorkNumber() != null
				&& !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and m.process_instance_id =?";
			listsql.add(conditionQueryModel.getWorkNumber().trim());
		}
		if (conditionQueryModel.getTheme() != null
				&& !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and instr(m.theme,?) >0";
			listsql.add(conditionQueryModel.getTheme().trim());
		}
		
		if (conditionQueryModel.getCity() != null
				&& !conditionQueryModel.getCity().equals("")) {
			whereSql += " and m.city = ?";
			listsql.add(conditionQueryModel.getCity());

		}
		if (conditionQueryModel.getCountry() != null
				&& !conditionQueryModel.getCountry().equals("")) {
			whereSql += " and m.country = ?";
			listsql.add(conditionQueryModel.getCountry());

		}
		if (conditionQueryModel.getLineType() != null
				&& !conditionQueryModel.getLineType().equals("")) {
			whereSql += " and m.work_type =?";
			listsql.add(conditionQueryModel.getLineType());

		}
		if (conditionQueryModel.getWorkOrderType() != null
				&& !conditionQueryModel.getWorkOrderType().equals("")) {
			whereSql += " and m.workorder_type_name =?";

			listsql.add(conditionQueryModel.getWorkOrderType());
		}
		if (conditionQueryModel.getPrecheckFlag() != null
				&& !conditionQueryModel.getPrecheckFlag().equals("")) {
			whereSql += " and m.precheck_flag =?";
			listsql.add(conditionQueryModel.getPrecheckFlag());

		}
		if (conditionQueryModel.getMainSceneId() != null
				&& !conditionQueryModel.getMainSceneId().equals("")) {
			whereSql += " and m.main_scene_id = ?";
			
			listsql.add(conditionQueryModel.getMainSceneId());
		}
		if (conditionQueryModel.getWorkOrderDegree() != null
				&& !conditionQueryModel.getWorkOrderDegree().equals("")) {
			whereSql += " and m.work_order_degree = ?";
			listsql.add(conditionQueryModel.getWorkOrderDegree());
			
		}
		if (conditionQueryModel.getKeyWord() != null
				&& !conditionQueryModel.getKeyWord().equals("")) {
			whereSql += " and m.key_word = ?";
			listsql.add(conditionQueryModel.getKeyWord());
			
		}
		if (conditionQueryModel.getProcessType() != null
				&& !conditionQueryModel.getProcessType().equals("")) {
			whereSql += " and m.themeinterface = ?";			
			listsql.add(conditionQueryModel.getProcessType());			
		}
		if (conditionQueryModel.getSubTypeName() != null
				&& !conditionQueryModel.getSubTypeName().equals("")) {
			whereSql += " and m.sub_type_name = ?";			
			listsql.add(conditionQueryModel.getSubTypeName());			
		}
		//批复开始时间
		if (conditionQueryModel.getApproveStartTime() != null
				&& !conditionQueryModel.getApproveStartTime().equals("")) {
			whereSql += " and to_char(m.distributed_interface_time,'yyyy-mm-dd hh24:mi:ss') >=?";
			listsql.add(conditionQueryModel.getApproveStartTime());
			
		}
		//批复结束时间
		if (conditionQueryModel.getApproveEndTime() != null
				&& !conditionQueryModel.getApproveEndTime().equals("")) {
			whereSql += " and to_char(m.distributed_interface_time,'yyyy-mm-dd hh24:mi:ss') <= ?";
			listsql.add(conditionQueryModel.getApproveEndTime());
		}
		finalSql = ") w ";
		
		if (conditionQueryModel.getStatus() != null
				&& !conditionQueryModel.getStatus().equals("")) {
			finalSql += "where w.TaskDefKey = ? ";
			listsql.add(conditionQueryModel.getStatus());

		}
		  sql += selectSql+ whereSql+finalSql;
		  
		System.out.println("****PnrTransferNewPrecheckDaoJDBC-getLocalNetworkWorkOrderListCount:工单查询-" + sql);
		Object[] args = listsql.toArray();
		int size = this.getJdbcTemplate().queryForInt(sql,args);
		return size;

	}
	
	/**
	 * 根据环节的ID和工单号查询该环节的处理人
	 * 
	 * @param userTaskId	环节的userTask的Id
	 * @param processInstanceId	工单号
	 * @return
	 */
	public String getLinkProcessingUserId(String userTaskId,String processInstanceId){
		Object[] args ={processInstanceId};
		String sql="";
		String linkUserId="superUser";//找不到默认superUser,至少不丢单
		if("need".equals(userTaskId)){
			 sql="select m.initiator as linkUserId from pnr_act_transfer_office_main m where m.process_instance_id = ?";
		}else if("cityManageExamine".equals(userTaskId)){
			 sql="select m.city_manage_charge as linkUserId from pnr_act_transfer_office_main m where m.process_instance_id = ?";
		}
		List<Map> list = this.getJdbcTemplate().queryForList(sql,args);
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
	 * @param param	查询条件
	 * @return
	 */
	public String getLinkMonthTotalProjectAmount(Map<String,String> param){
		List listobj = new ArrayList();
		
		/*String sql=
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
			"                                  left join "+param.get("tableName")+" tk" + 
			"                                    on hi.task_def_key_ = tk.key" + 
			"                                 where p.key_ = ?) m" + 
			"                         where m.tnum = 1" + 
			"                           and m.cur_int >= ?)" + 
			"                   and hi.task_def_key_ = ?) r" + 
			"         where r.tnum = 1" + 
			"           and r.end_time_ >= to_date(?,'yyyy-mm-dd hh24:mi:ss')" +
			"           and r.end_time_ <= to_date(?,'yyyy-mm-dd hh24:mi:ss'))" + 
			"   and ma.city = ?";*/
	
//		listobj.add(param.get("tableName"));
/*		listobj.add(param.get("processType"));
		listobj.add(param.get("linkSerialNumber"));
		listobj.add(param.get("linkKey"));
		listobj.add(param.get("quarterStartTime"));
		listobj.add(param.get("currentTime"));
		listobj.add(param.get("cityId"));
		
		if("101231401".equals(param.get("precheckFlag"))){	//应急工单
			sql+=" and nvl(ma.precheck_flag,'other') = ?";
		}else{	//其他工单
			sql+=" and nvl(ma.precheck_flag,'other') != ?";
		}
		listobj.add("101231401");*/
		String precheckFlag="";
		if("101231401".equals(param.get("precheckFlag"))){	//应急工单
			precheckFlag=" and nvl(m.precheck_flag,'other') = ?";
		}else{	//其他工单
			precheckFlag=" and nvl(m.precheck_flag,'other') != ?";
		}
		
		String sql="select sum(ma.project_amount) as smountSum from (select mc.project_amount from (" +
		"select m.project_amount,row_number() over(partition by hi.proc_inst_id_ order by hi.start_time_ desc) tnum,nvl(tk.cur_int,7) cur_int" +
		" from pnr_act_transfer_office_main m left join act_hi_taskinst hi" +
		" on m.process_instance_id = hi.proc_inst_id_ and hi.task_def_key_ =?" +
		" left join act_re_procdef p on hi.proc_def_id_ = p.id_ " +
		" left join transfernew_key_relation tk on  m.task_def_key= tk.key" +
		" where p.key_ =?  and m.city =? "+precheckFlag +
		" and to_char(hi.end_time_,'yyyy-mm-dd hh24:mi:ss') >=?" +
		" and to_char(hi.end_time_,'yyyy-mm-dd hh24:mi:ss') <=?" +
		" ) mc where mc.tnum = 1  and mc.cur_int >=? )ma";
		
		listobj.add(param.get("linkKey"));
		listobj.add(param.get("processType"));
		listobj.add(param.get("cityId"));
		listobj.add("101231401");
		listobj.add(param.get("quarterStartTime"));
		listobj.add(param.get("currentTime"));
		listobj.add(param.get("linkSerialNumber"));
		
		System.out.println("------查询流程某个环节的某个季度已完成的预算金额总和sql="+sql);
		Object[] args = listobj.toArray();
		String smountSum="0";//默认总金额为0
		List<Map> list = this.getJdbcTemplate().queryForList(sql,args);
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
//		String sql = "select temp2.* from (select temp1.*, rownum num from (";
//		String selectSql = "select * from (select ACTI.Proc_Inst_Id_ as processInstanceId,decode(MAIN.sheet_id, null, '无', MAIN.sheet_id) as sheetId,MAIN.Theme as theme,MAIN.city ,MAIN.country,MAIN.work_type,MAIN.workorder_type_name,nvl(MAIN.is_remind,0) as remind,MAIN.state,MAIN.sub_type_name ,MAIN.key_word ,MAIN.work_order_degree,MAIN.project_amount,MAIN.Compensate,MAIN.Calculate_Income_Ratio,MAIN.Initiator as initiator,MAIN.Submit_Application_Time as SubmitTime,DECODE(MAIN.STATE,8,'等待接口调用',ACTI.Name_) as statusName,DECODE(MAIN.STATE,8,'waitingCallInterface',ACTI.Task_Def_Key_) as taskDefKey,MAIN.TASK_ASSIGNEE,MAIN.Send_Time as sendTime from ( select  k.proc_inst_id_, k.name_, k.task_def_key_ from ACT_RU_TASK k inner join ACT_RE_PROCDEF D on k.PROC_DEF_ID_ = D.ID_ where D.KEY_ = '"
//				+ processDefinitionKey
//				+ "' and (exists (select LINK.USER_ID_ from ACT_HI_IDENTITYLINK LINK where USER_ID_ = '"
//				+ userId
//				+ "' and LINK.PROC_INST_ID_ = k.proc_inst_id_)) and (exists (select kst.assignee_ from act_hi_taskinst kst where kst.assignee_ = '"
//				+ userId
//				+ "' and kst.proc_inst_id_ = k.proc_inst_id_ and kst.end_time_ is not null))) ACTI,PNR_ACT_TRANSFER_OFFICE_MAIN MAIN WHERE ACTI.Proc_Inst_Id_ = MAIN.PROCESS_INSTANCE_ID(+)) ST";
//		String whereSql = " WHERE 1=1 and ST.STATE = '8' and ST.INITIATOR="+"'"+userId+"' and ST.remind <> 1";
//		if (sendStartTime != null && !sendStartTime.equals("")) {
//			whereSql += " and ST.sendTime >=to_date('" + sendStartTime
//					+ "','yyyy-mm-dd hh24:mi:ss')";
//		}
//		if (sendEndTime != null && !sendEndTime.equals("")) {
//			whereSql += " and ST.sendTime <= to_date('" + sendEndTime
//					+ "','yyyy-mm-dd hh24:mi:ss')";
//		}
//		if (wsNum != null && !wsNum.equals("")) {
//			whereSql += " and ST.processInstanceId ='" + wsNum.trim() + "'";
//		}
//		if (wsTitle != null && !wsTitle.equals("")) {
//			whereSql += " and ST.theme like '%" + wsTitle.trim() + "%'";
//		}
//		if (status != null && !status.equals("")) {
//			whereSql += " and ST.taskDefKey = '" + status + "'";
//
//		}
//		sql += selectSql
//				+ whereSql
//				+ " order by ST.sendTime) temp1 where rownum <="
//				+ endResult * pageSize + ") temp2 where temp2.num > "
//				+ firstResult * pageSize;
		String type="无";
		if("transferNewPrechechProcess".equals(processDefinitionKey)){ //本地网
			type="interface";
		}else if("transferArteryPrecheckProcess".equals(processDefinitionKey)){//干线
			type="arteryPrecheck";
		}else if("overHaulNewProcess".equals(processDefinitionKey)){//大修改造
			type="overhaul";
		}
		
		List<Object> paramList = new ArrayList<Object>();
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
	    String selectSql =  "select MAIN.Process_Instance_Id as processInstanceId,\n" +
					    	"       decode(MAIN.sheet_id, null, '无', MAIN.sheet_id) as sheetId,\n" + 
					    	"       MAIN.Theme as theme,\n" + 
					    	"       MAIN.city,\n" + 
					    	"       MAIN.country,\n" + 
					    	"       MAIN.work_type,\n" + 
					    	"       MAIN.workorder_type_name,\n" + 
					    	"       nvl(MAIN.is_remind, 0) as remind,\n" + 
					    	"       MAIN.state,\n" + 
					    	"       MAIN.sub_type_name,\n" + 
					    	"       MAIN.key_word,\n" + 
					    	"       MAIN.work_order_degree,\n" + 
					    	"       MAIN.project_amount,\n" + 
					    	"       MAIN.Compensate,\n" + 
					    	"       MAIN.Calculate_Income_Ratio,\n" + 
					    	"       MAIN.Initiator as initiator,\n" + 
					    	"       MAIN.Submit_Application_Time as SubmitTime,\n" + 
					    	"       DECODE(MAIN.STATE, 8, '等待接口调用', MAIN.Task_Def_Key_Name) as statusName,\n" + 
					    	"       DECODE(MAIN.STATE, 8, 'waitingCallInterface', MAIN.Task_Def_Key) as taskDefKey,\n" + 
					    	"       MAIN.TASK_ASSIGNEE,\n" + 
					    	"       MAIN.Send_Time as sendTime"+
							"  from PNR_ACT_TRANSFER_OFFICE_MAIN MAIN\n" + 
							" WHERE 1=1\n" + 
							"   and MAIN.THEMEINTERFACE = ?\n" + 
							"   and MAIN.STATE = '8'\n" + 
							"   and MAIN.INITIATOR = ?\n" + 
							"   and nvl(MAIN.IS_REMIND, 0) <> 1";
		
	    paramList.add(type);
	    paramList.add(userId);
	    
		String whereSql = "";
		if (sendStartTime != null && !sendStartTime.equals("")) {
			whereSql += " and MAIN.Send_Time >= to_date(?,'yyyy-mm-dd hh24:mi:ss')";
			paramList.add(sendStartTime);
		}
		if (sendEndTime != null && !sendEndTime.equals("")) {
			whereSql += " and MAIN.Send_Time <= to_date(?,'yyyy-mm-dd hh24:mi:ss')";
			paramList.add(sendEndTime);
		}
		if (wsNum != null && !wsNum.equals("")) {
			whereSql += " and MAIN.Process_Instance_Id = ?";
			paramList.add(wsNum.trim());
		}
		if (wsTitle != null && !wsTitle.equals("")) {
			whereSql += " and instr(MAIN.theme,?)>0";
			paramList.add(wsTitle.trim());
		}
		if (status != null && !status.equals("")) {
			whereSql += " and MAIN.Task_Def_Key = ?";
			paramList.add(status);
		}
		sql += selectSql
				+ whereSql
				+ " order by MAIN.Send_Time) temp1 where rownum <=?) temp2 where temp2.num > ?";
		paramList.add(endResult * pageSize);
		paramList.add(firstResult * pageSize);
		Object[] args = paramList.toArray();
		System.out.println("待接口调用工单明细sql=" + sql);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,args);
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
//		String sql = "";
//		String selectSql = "select count(processInstanceId) as total from (select ACTI.Proc_Inst_Id_ as processInstanceId,MAIN.Theme as theme,MAIN.city ,MAIN.country,MAIN.state,MAIN.work_type,MAIN.workorder_type_name,MAIN.sub_type_name ,MAIN.key_word ,MAIN.work_order_degree,MAIN.project_amount,MAIN.Initiator as initiator,MAIN.Submit_Application_Time as SubmitTime,nvl(MAIN.IS_REMIND,0) as remind,DECODE(MAIN.STATE,8,'等待接口调用',ACTI.Name_) as statusName,DECODE(MAIN.STATE,8,'waitingCallInterface',ACTI.Task_Def_Key_) as taskDefKey,MAIN.TASK_ASSIGNEE,MAIN.Send_Time as sendTime from ( select  k.proc_inst_id_, k.name_, k.task_def_key_ from ACT_RU_TASK k inner join ACT_RE_PROCDEF D on k.PROC_DEF_ID_ = D.ID_ where D.KEY_ = '"
//				+ processDefinitionKey
//				+ "' and (exists (select LINK.USER_ID_ from ACT_HI_IDENTITYLINK LINK where USER_ID_ = '"
//				+ userId
//				+ "' and LINK.PROC_INST_ID_ = k.proc_inst_id_)) and (exists (select kst.assignee_ from act_hi_taskinst kst where kst.assignee_ = '"
//				+ userId
//				+ "' and kst.proc_inst_id_ = k.proc_inst_id_ and kst.end_time_ is not null))) ACTI,PNR_ACT_TRANSFER_OFFICE_MAIN MAIN WHERE ACTI.Proc_Inst_Id_ = MAIN.PROCESS_INSTANCE_ID(+)) ST";
//		String whereSql = " WHERE 1=1 and ST.STATE = '8' and ST.INITIATOR="+"'"+userId+"' and ST.remind <> 1";
//		if (sendStartTime != null && !sendStartTime.equals("")) {
//			whereSql += " and ST.sendTime >=to_date('" + sendStartTime
//					+ "','yyyy-mm-dd hh24:mi:ss')";
//		}
//		if (sendEndTime != null && !sendEndTime.equals("")) {
//			whereSql += " and ST.sendTime <= to_date('" + sendEndTime
//					+ "','yyyy-mm-dd hh24:mi:ss')";
//		}
//		if (wsNum != null && !wsNum.equals("")) {
//			whereSql += " and ST.processInstanceId ='" + wsNum.trim() + "'";
//		}
//		if (wsTitle != null && !wsTitle.equals("")) {
//			whereSql += " and ST.theme like '%" + wsTitle.trim() + "%'";
//		}
//		if (status != null && !status.equals("")) {
//			whereSql += " and ST.taskDefKey = '" + status + "'";
//
//		}
//		sql += selectSql + whereSql;
		
		String type="无";
		if("transferNewPrechechProcess".equals(processDefinitionKey)){ //本地网
			type="interface";
		}else if("transferArteryPrecheckProcess".equals(processDefinitionKey)){//干线
			type="arteryPrecheck";
		}else if("overHaulNewProcess".equals(processDefinitionKey)){//大修改造
			type="overhaul";
		}
		
		List<Object> paramList = new ArrayList<Object>();
		String sql = "";
		String selectSql =  "select count(MAIN.Process_Instance_Id) as total\n" +
							"  from PNR_ACT_TRANSFER_OFFICE_MAIN MAIN\n" + 
							" WHERE 1 = 1\n" + 
							"   and MAIN.THEMEINTERFACE = ?\n" + 
							"   and MAIN.STATE = '8'\n" + 
							"   and MAIN.INITIATOR = ?\n" + 
							"   and nvl(MAIN.IS_REMIND, 0) <> 1";
		paramList.add(type);
		paramList.add(userId);
		String whereSql = "";
		if (sendStartTime != null && !sendStartTime.equals("")) {
			whereSql += " and MAIN.Send_Time >= to_date(?,'yyyy-mm-dd hh24:mi:ss')";
			paramList.add(sendStartTime);
		}
		if (sendEndTime != null && !sendEndTime.equals("")) {
			whereSql += " and MAIN.Send_Time <= to_date(?,'yyyy-mm-dd hh24:mi:ss')";
			paramList.add(sendEndTime);
		}
		if (wsNum != null && !wsNum.equals("")) {
			whereSql += " and MAIN.Process_Instance_Id = ?";
			paramList.add(wsNum.trim());
		}
		if (wsTitle != null && !wsTitle.equals("")) {
			whereSql += " and instr(MAIN.theme,?)>0";
			paramList.add(wsTitle.trim());
		}
		if (status != null && !status.equals("")) {
			whereSql += " and MAIN.Task_Def_Key = ?";
			paramList.add(status);
		}
		sql += selectSql + whereSql;

		System.out.println("待接口调用工单条数sql=" + sql);
		Object[] args = paramList.toArray();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,args);
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
	                   "   from (select  k.proc_inst_id_,									"+
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
	
	/**
	 * 	查询抽检工单条数
	 	 * @author WANGJUN
	 	 * @title: querySamplingCount
	 	 * @date Aug 4, 2016 10:40:41 AM
	 	 * @param userId
	 	 * @param conditionQueryModel
	 	 * @return int
	 */
	public int querySamplingCount(String userId,ConditionQueryModel conditionQueryModel){
		List paramList = new ArrayList();
		String sql = "";
		String selectSql =  "select count(m.process_instance_id) as total\n" +
							"  from pnr_act_transfer_office_main m\n" + 
							" where m.themeinterface in ('interface', 'arteryPrecheck')\n" + 
							"   and m.state = 5\n" + 
							"   and m.task_def_key = 'archive'\n";
//							"   and to_char(m.send_time, 'yyyy-mm-dd') >= '2016-01-01'\n" + 
//							"   and to_char(m.send_time, 'yyyy-mm-dd') <= '2016-07-31'\n" + 
//							"   and m.city = '2823'";
		String whereSql = "";
		if (conditionQueryModel.getSendStartTime() != null && !"".equals(conditionQueryModel.getSendStartTime())) {
			whereSql += " and to_char(m.send_time, 'yyyy-mm-dd') >= ?";
			paramList.add(conditionQueryModel.getSendStartTime());
		
		}
		if (conditionQueryModel.getSendEndTime() != null && !"".equals(conditionQueryModel.getSendEndTime())) {
			whereSql += " and to_char(m.send_time, 'yyyy-mm-dd') <= ?";
			paramList.add( conditionQueryModel.getSendEndTime());
		}
		
		if (conditionQueryModel.getCity() != null && !"".equals(conditionQueryModel.getCity())) {
			whereSql += " and m.city = ?";
			paramList.add(conditionQueryModel.getCity());
		}
		if (conditionQueryModel.getCountry() != null && !"".equals(conditionQueryModel.getCountry())) { //区县
			whereSql += " and m.country = ?";
			paramList.add(conditionQueryModel.getCountry());
		}
		if (conditionQueryModel.getWorkNumber() != null && !"".equals(conditionQueryModel.getWorkNumber())) { //工单号
			whereSql += " and m.process_instance_id = ?";
			paramList.add(conditionQueryModel.getWorkNumber());
		}
		if (conditionQueryModel.getTheme() != null && !"".equals(conditionQueryModel.getTheme())) { //工单主题
			whereSql += "and instr(m.theme,?)>0 ";
			paramList.add(conditionQueryModel.getTheme());
		}
		if (conditionQueryModel.getStatus() != null && !"".equals(conditionQueryModel.getStatus())) { //状态
			whereSql += "and nvl(m.sampling_state,'0') = ?";
			paramList.add(conditionQueryModel.getStatus());
		}
		if (conditionQueryModel.getProcessType() != null && !"".equals(conditionQueryModel.getProcessType())) { //流程类型
			whereSql += "and m.themeinterface = ?";
			paramList.add(conditionQueryModel.getProcessType());
		}
		
		sql += selectSql + whereSql;
		Object[] args = paramList.toArray();
		int size = this.getJdbcTemplate().queryForInt(sql,args);
		return size;
	}
	
	/**
	 * 	 查询抽检工单明细
	 	 * @author WANGJUN
	 	 * @title: querySamplingList
	 	 * @date Aug 4, 2016 10:40:50 AM
	 	 * @param userId
	 	 * @param conditionQueryModel
	 	 * @param firstResult
	 	 * @param endResult
	 	 * @param pageSize
	 	 * @return List<TaskModel>
	 */
	public List<TaskModel> querySamplingList(String userId,ConditionQueryModel conditionQueryModel,int firstResult,int endResult,int pageSize){
		List<Object> paramList = new ArrayList<Object>();
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		String selectSql =  "select m.process_instance_id,\n" +
							"       decode(m.themeinterface, 'interface', '本地网', '干线') as themeinterfacename,\n" + 
							"       m.themeinterface," + 
							"       m.theme,\n" + 
							"       m.city,\n" + 
							"       m.country,\n" + 
							"       m.send_time,\n" + 
							"       m.key_word,\n" + 
							"       m.submit_application_time,\n" + 
							"       m.work_order_degree,\n" + 
							"       m.project_amount,\n" + 
							"       nvl(m.sampling_state,'0') as sampling_state" + 
							"  from pnr_act_transfer_office_main m\n" + 
							" where m.themeinterface in ('interface', 'arteryPrecheck')\n" + 
							"   and m.state = 5\n" + 
							"   and m.task_def_key = 'archive'\n";
//							"   and to_char(m.send_time, 'yyyy-mm-dd') >= '2016-01-01'\n" + 
//							"   and to_char(m.send_time, 'yyyy-mm-dd') <= '2016-07-31'\n" + 
//							"   and m.city = '2823'";

		String whereSql = "";
		if (conditionQueryModel.getSendStartTime() != null && !"".equals(conditionQueryModel.getSendStartTime())) {
			whereSql += " and to_char(m.send_time, 'yyyy-mm-dd') >= ?";
			paramList.add(conditionQueryModel.getSendStartTime());
		
		}
		if (conditionQueryModel.getSendEndTime() != null && !"".equals(conditionQueryModel.getSendEndTime())) {
			whereSql += " and to_char(m.send_time, 'yyyy-mm-dd') <= ?";
			paramList.add( conditionQueryModel.getSendEndTime());
		}
		
		if (conditionQueryModel.getCity() != null && !"".equals(conditionQueryModel.getCity())) {
			whereSql += " and m.city = ?";
			paramList.add(conditionQueryModel.getCity());
		}
		
		if (conditionQueryModel.getCountry() != null && !"".equals(conditionQueryModel.getCountry())) { //区县
			whereSql += " and m.country = ?";
			paramList.add(conditionQueryModel.getCountry());
		}
		if (conditionQueryModel.getWorkNumber() != null && !"".equals(conditionQueryModel.getWorkNumber())) { //工单号
			whereSql += " and m.process_instance_id = ?";
			paramList.add(conditionQueryModel.getWorkNumber());
		}
		if (conditionQueryModel.getTheme() != null && !"".equals(conditionQueryModel.getTheme())) { //工单主题
			whereSql += "and instr(m.theme,?)>0 ";
			paramList.add(conditionQueryModel.getTheme());
		}
		if (conditionQueryModel.getStatus() != null && !"".equals(conditionQueryModel.getStatus())) { //状态
			whereSql += "and nvl(m.sampling_state,'0') = ?";
			paramList.add(conditionQueryModel.getStatus());
		}
		if (conditionQueryModel.getProcessType() != null && !"".equals(conditionQueryModel.getProcessType())) { //流程类型
			whereSql += "and m.themeinterface = ?";
			paramList.add(conditionQueryModel.getProcessType());
		}
		sql += selectSql+ whereSql+ " order by m.send_time desc) temp1 where rownum <=?) temp2 where temp2.num >? ";
		paramList.add(endResult * pageSize);
		paramList.add(firstResult * pageSize);
		Object[] args = paramList.toArray();		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,args);
		for (Map map : list) {
			TaskModel model = new TaskModel();
			if (map.get("process_instance_id") != null && !"".equals(map.get("process_instance_id").toString())) {
				model.setProcessInstanceId(map.get("process_instance_id").toString());
			}
			if (map.get("themeinterface") != null && !"".equals(map.get("themeinterface").toString())) {
				model.setThemeInterface(map.get("themeinterface").toString());
			}
			if (map.get("themeinterfacename") != null && !"".equals(map.get("themeinterfacename").toString())) {
				model.setThemeInterfaceName(map.get("themeinterfacename").toString());
			}
			if (map.get("theme") != null && !"".equals(map.get("theme").toString())) { 
				model.setTheme(map.get("theme").toString());
			}
			if (map.get("city") != null && !"".equals(map.get("city").toString())) {
				model.setCity(map.get("city").toString());
			}
			if (map.get("country") != null && !"".equals(map.get("country"))) {
				model.setCountry(map.get("country").toString());
			}
			if (map.get("send_time") != null && !"".equals(map.get("send_time").toString())) {
				try {
					model.setSendTime(format.parse(map.get("send_time").toString()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			if (map.get("key_word") != null && !"".equals(map.get("key_word").toString())){
				model.setKeyWord(map.get("key_word").toString());
			}
			if (map.get("submit_application_time") != null && !"".equals(map.get("submit_application_time").toString())) {
				try {
					model.setApplicationTime(format.parse(map.get("submit_application_time").toString()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			if (map.get("work_order_degree") != null && !"".equals(map.get("work_order_degree").toString())){
				model.setWorkOrderDegree(map.get("work_order_degree").toString());
			}
			if (map.get("project_amount") != null && !"".equals(map.get("project_amount").toString())) {
					model.setProjectAmount(Double.parseDouble(map.get("project_amount").toString()));
		    }
			//抽查状态
			if (map.get("sampling_state") != null && !"".equals(map.get("sampling_state").toString())) {
				model.setSamplingState(map.get("sampling_state").toString());
			}
			
			r.add(model);
		}
		
		return r;
	}
}
