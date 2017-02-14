package com.boco.activiti.partner.process.dao.hibernate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.activiti.partner.process.dao.IPnrTransferOverhaulRemakeJDBCDao;
import com.boco.activiti.partner.process.model.PnrAndroidPhotoFile;
import com.boco.activiti.partner.process.po.ConditionQueryModel;
import com.boco.activiti.partner.process.po.TaskModel;

public class PnrTransferOverhaulRemakeDaoJDBC extends JdbcDaoSupport implements
		IPnrTransferOverhaulRemakeJDBCDao {

	/**
	 * 根据区县账号ID值获取市环节操作人
	 * 
	 * @param areaCountyAccount
	 *            区县账号ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getCitylinkOfOperationPerson(String areaCountyAccount) {
		// String sql ="select distinct
		// s.city_line_director,s.city_line_director_name,s.city_manage_charge,s.city_manage_charge_name,s.city_manage_director,s.city_manage_director_name,s.city_company,s.city_company_name
		// from pnr_act_transfer_precheck_ship s where s.city_line_charge =
		// '"+sendUserId+"'";
		String sql = "select * from pnr_act_transfer_precheck_ship s where s.country_charge='"
				+ areaCountyAccount + "'";
		return this.getJdbcTemplate().queryForList(sql);
	}

	/**
	 * 根据区县账号ID值和角色ID值获取省环节操作人
	 * 
	 * @param areaCountyAccount
	 *            区县账号ID
	 * @param roleid
	 *            角色ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getProvincelinkOfOperationPerson(String areaCountyAccount,
			String roleid) {
		// String sql = "select t.userid from taw_system_userrefrole t" +
		// " left join taw_system_sub_role sr on t.subroleid = sr.id" +
		// " where" +
		// " sr.roleid='"+roleid+"'" +
		// " and sr.deptid=( select a.areaid from taw_system_user u
		// ,taw_system_dept d ,taw_system_area a" +
		// " where u.deptid=d.deptid" +
		// " and d.areaid = a.areaid" +
		// " and u.userid='"+sendUserId+"')";
		String sql = "select t.userid from taw_system_userrefrole t"
				+ " left join taw_system_sub_role sr on t.subroleid = sr.id"
				+ " where"
				+ " sr.roleid='"
				+ roleid
				+ "'"
				+ " and sr.deptid=( select a.parentareaid from taw_system_user u ,taw_system_dept d ,taw_system_area a"
				+ "  where u.deptid=d.deptid" + "  and d.areaid = a.areaid"
				+ " and u.userid='" + areaCountyAccount + "')";
		System.out.println("------大修改造根据派单人用户ID值和角色ID值获取省环节操作人sql=" + sql);
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		return list;
	}

	/**
	 * 获取区县账号下拉选的值
	 * 
	 * @param userId
	 *            建单人ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> getAreaCountyAccount(String userId) {
		String sql = "select s.country_charge as tempId, s.country_charge as tempName from pnr_act_transfer_precheck_ship s where s.city_line_charge = '"
				+ userId + "'";
		// String sql ="select s.country_charge as tempId, s.country_charge as
		// tempName from pnr_act_transfer_precheck_ship s where
		// s.city_line_charge = 'bz-xlzx-zg'";
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		List<Map<String, String>> lists = new ArrayList<Map<String, String>>();
		if (list != null && list.size() > 0) {
			for (Map map : list) {
				Map<String, String> maps = new HashMap<String, String>();
				maps.put("id", map.get("tempId").toString());
				maps.put("name", map.get("tempName").toString());
				lists.add(maps);
			}
		} else {
			Map<String, String> maps = new HashMap<String, String>();
			maps.put("id", "superUser");
			maps.put("name", "superUser");
			lists.add(maps);
		}
		return lists;

	}

	/**
	 * 待回复工单 条数
	 * 
	 * @param userid
	 * @param flag
	 * @param processKey
	 * @param conditionQueryModel
	 * @return
	 */
	@Override
	public int repairOrderCount(String userid, String flag, String processKey,
			ConditionQueryModel conditionQueryModel) {
		String sql = "";
		String selectSql =	"select count(m.id) as total" +
							"  from pnr_act_transfer_office_main m" + 
							" where m.themeinterface in ('overhaul', 'reform')" + 
							"   and m.assignee = ?";

//		String selectSql = "select count(*) as total from (select  RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_ WHERE RES.ASSIGNEE_ = ? "
//			+ " and D.KEY_ = ? "
//			+ " and RES.Suspension_State_ = 1) t,pnr_act_transfer_office_main m where t.proc_inst_id_ = m.process_instance_id ";

		ArrayList whereList=new ArrayList();
		whereList.add(userid);
	//	whereList.add(processKey);
		
		String whereSql="";
		
		if (flag != null && "backlog".equals(flag.trim())) {
			whereSql = " and (m.state!=? and m.state!=?)";
			whereList.add(8);
			whereList.add(3);
		} else if ("wait".equals(flag.trim())) {
			whereSql = " and m.state=?";			
			whereList.add(3);
		}
		if (conditionQueryModel.getSendStartTime() != null
				&& !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >=?";
			whereList.add(conditionQueryModel.getSendStartTime());
		
		}
		if (conditionQueryModel.getSendEndTime() != null
				&& !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?";
			whereList.add( conditionQueryModel.getSendEndTime());
		}
		if (conditionQueryModel.getWorkNumber() != null
				&& !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and m.process_instance_id =?";
			whereList.add(conditionQueryModel.getWorkNumber().trim());
		}
		if (conditionQueryModel.getTheme() != null
				&& !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and instr(m.theme,?)>0 ";
			whereList.add(conditionQueryModel.getTheme().trim());
		}
		if (conditionQueryModel.getStatus() != null
				&& !conditionQueryModel.getStatus().equals("")) {
			whereSql += " and m.task_def_key = ? ";
			whereList.add(conditionQueryModel.getStatus());

		}
//		if (conditionQueryModel.getStatus() != null
//				&& !conditionQueryModel.getStatus().equals("")) {
//			whereSql += " and t.task_def_key_ = ? ";
//			whereList.add(conditionQueryModel.getStatus());
//			
//		}
		if (conditionQueryModel.getCity() != null
				&& !conditionQueryModel.getCity().equals("")) {
			whereSql += " and m.city = ?";
			whereList.add(conditionQueryModel.getCity());
		}
		if (conditionQueryModel.getCountry() != null
				&& !conditionQueryModel.getCountry().equals("")) {
			whereSql += " and m.country = ?";
			whereList.add(conditionQueryModel.getCountry());
		}
		if (conditionQueryModel.getLineType() != null
				&& !conditionQueryModel.getLineType().equals("")) {
			whereSql += " and m.work_type = ?";
			whereList.add(conditionQueryModel.getLineType());
		}
		if (conditionQueryModel.getWorkOrderType() != null
				&& !conditionQueryModel.getWorkOrderType().equals("")) {
			whereSql += " and m.workorder_type_name = ?";
			whereList.add(conditionQueryModel.getWorkOrderType());
		}
		if (conditionQueryModel.getPrecheckFlag() != null
				&& !conditionQueryModel.getPrecheckFlag().equals("")) {
			whereSql += " and m.precheck_flag = ?";
			whereList.add(conditionQueryModel.getPrecheckFlag());
		}
		if (conditionQueryModel.getMainSceneId() != null
				&& !conditionQueryModel.getMainSceneId().equals("")) {
			whereSql += " and m.main_scene_id = ?";
			whereList.add(conditionQueryModel.getMainSceneId());
		}
		
		sql = selectSql + whereSql;

        
		Object[] args = whereList.toArray();
		
		System.out.println("--------------大修改造 待回复工单 条数sql=" + sql);
		int size = this.getJdbcTemplate().queryForInt(sql, args);
		
		return size;

	}

	/**
	 * 待回复工单 集合
	 * 
	 * @param userid
	 * @param flag
	 * @param processKey
	 * @param conditionQueryModel
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	@Override
	public List<TaskModel> repairOrderList(String userid, String flag,
			String processKey, ConditionQueryModel conditionQueryModel,
			int firstResult, int endResult, int pageSize) {
		String sql = "";
		if(pageSize != -1){ //计算项目预算总金额和导出用
			sql = "select temp2.* from (select temp1.*, rownum num from (";
		}
		String selectSql =	"select m.task_id as TaskId," +
							"        m.task_def_key as taskDefKey," + 
							"        decode(m.state, 6, '专家会审', m.task_def_key_name) as statusName," + 
							"        m.line_type," + 
							"        m.initiator as Initiator," + 
							"        m.one_initiator as OneInitiator," + 
							"        m.process_instance_id as ProcessInstanceId," + 
							"        m.send_time as SendTime," + 
							"        m.theme as Theme," + 
							"        m.task_assignee," + 
							"        m.state as State," + 
							"        m.submit_application_time as SubmitTime," + 
							"        m.city," + 
							"        m.country," + 
							"        m.work_type," + 
							"        m.workorder_type_name," + 
							"        m.sub_type_name," + 
							"        m.key_word," + 
							"        m.work_order_degree," + 
							"        m.rollback_flag," + 
							"        m.precheck_flag," + 
							"        m.project_amount," + 
							"        m.kind_restitution," + 
							"        m.compensate," + 
							"        m.calculate_income_ratio," + 
							"        decode(m.sheet_id, null, '无', m.sheet_id) as sheetId," + 
							"        m.themeinterface," + 
							"        decode(m.themeinterface," + 
							"               'overhaul'," + 
							"               '大修工单'," + 
							"               'reform'," + 
							"               '改造工单' ， '') as themeinterfacename" + 
							"   from pnr_act_transfer_office_main m" + 
							"  where m.themeinterface in ('overhaul', 'reform')" + 
							"    and m.assignee = ?";

//		String selectSql = "select t.id_ as TaskId,t.task_def_key_ as taskDefKey,decode(m.state,6,'专家会审'，t.name_) as statusName,m.line_type,m.initiator as Initiator,m.one_initiator as OneInitiator,m.process_instance_id as ProcessInstanceId,m.send_time as SendTime,m.theme as Theme,m.task_assignee,m.state as State,m.submit_application_time as SubmitTime,m.city ,m.country,m.work_type,m.workorder_type_name,m.sub_type_name ,m.key_word ,m.work_order_degree,m.rollback_flag, m.precheck_flag,m.project_amount,m.compensate,m.calculate_income_ratio,decode(m.sheet_id,null,'无',m.sheet_id) as sheetId,m.themeinterface,decode(m.themeinterface,'overhaul','大修工单','reform','改造工单'，'') as themeinterfacename from (select RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_ WHERE RES.ASSIGNEE_ = ? "
//			+ " and D.KEY_ = ? "
//			+ " and RES.Suspension_State_ = 1) t,pnr_act_transfer_office_main m where t.proc_inst_id_ = m.process_instance_id ";
		
		ArrayList whereList = new ArrayList(); 
		whereList.add(userid);
		//whereList.add(processKey);
		
		String whereSql="";
		
		if (flag != null && "backlog".equals(flag.trim())) {
			whereSql = " and (m.state!=? and m.state!=?)";
			whereList.add(8);
			whereList.add(3);
		} else if ("wait".equals(flag.trim())) {
			whereSql = " and m.state=?";			
			whereList.add(3);
		}
		if (conditionQueryModel.getSendStartTime() != null
				&& !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >=?";
			whereList.add(conditionQueryModel.getSendStartTime());
		
		}
		if (conditionQueryModel.getSendEndTime() != null
				&& !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?";
			whereList.add( conditionQueryModel.getSendEndTime());
		}
		if (conditionQueryModel.getWorkNumber() != null
				&& !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and m.process_instance_id =?";
			whereList.add(conditionQueryModel.getWorkNumber().trim());
		}
		if (conditionQueryModel.getTheme() != null
				&& !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and instr(m.theme,?)>0 ";
			whereList.add(conditionQueryModel.getTheme().trim());
		}
		if (conditionQueryModel.getStatus() != null
				&& !conditionQueryModel.getStatus().equals("")) {
			whereSql += " and m.task_def_key = ? ";
			whereList.add(conditionQueryModel.getStatus());

		}
//		if (conditionQueryModel.getStatus() != null
//				&& !conditionQueryModel.getStatus().equals("")) {
//			whereSql += " and t.task_def_key_ = ? ";
//			whereList.add(conditionQueryModel.getStatus());
//			
//		}
		if (conditionQueryModel.getCity() != null
				&& !conditionQueryModel.getCity().equals("")) {
			whereSql += " and m.city = ?";
			whereList.add(conditionQueryModel.getCity());
		}
		if (conditionQueryModel.getCountry() != null
				&& !conditionQueryModel.getCountry().equals("")) {
			whereSql += " and m.country = ?";
			whereList.add(conditionQueryModel.getCountry());
		}
		if (conditionQueryModel.getLineType() != null
				&& !conditionQueryModel.getLineType().equals("")) {
			whereSql += " and m.work_type = ?";
			whereList.add(conditionQueryModel.getLineType());
		}
		if (conditionQueryModel.getWorkOrderType() != null
				&& !conditionQueryModel.getWorkOrderType().equals("")) {
			whereSql += " and m.workorder_type_name = ?";
			whereList.add(conditionQueryModel.getWorkOrderType());
		}
		if (conditionQueryModel.getPrecheckFlag() != null
				&& !conditionQueryModel.getPrecheckFlag().equals("")) {
			whereSql += " and m.precheck_flag = ?";
			whereList.add(conditionQueryModel.getPrecheckFlag());
		}
		if (conditionQueryModel.getMainSceneId() != null
				&& !conditionQueryModel.getMainSceneId().equals("")) {
			whereSql += " and m.main_scene_id = ?";
			whereList.add(conditionQueryModel.getMainSceneId());
		}
		
		sql += selectSql + whereSql
				+ " order by m.send_time asc" ;
		
		if(pageSize != -1){
			sql+=") temp1 where rownum <=? ) temp2 where temp2.num >? ";
			whereList.add(endResult * pageSize);
			whereList.add(firstResult * pageSize);
		}
		
		Object[] values = whereList.toArray();
		
		System.out.println("--------------大修改造 待回复工单 集合sql=" + sql);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,values);
		for (Map map : list) {
			TaskModel model = new TaskModel();
			if (map.get("TaskId") != null && !"".equals(map.get("TaskId"))) {

				model.setTaskId(map.get("TaskId").toString());
			}
			
			if (map.get("taskDefKey") != null && !"".equals(map.get("taskDefKey"))) {
				
				model.setTaskDefKey(map.get("taskDefKey").toString());
			}
			if (map.get("statusName") != null && !"".equals(map.get("statusName"))) {
				
				model.setStatusName(map.get("statusName").toString());
			}
			
			if (map.get("Initiator") != null
					&& !"".equals(map.get("Initiator"))) {

				model.setInitiator(map.get("Initiator").toString());
			}
			if (map.get("line_type") != null
					&& !"".equals(map.get("line_type"))) {

				model.setLineType(map.get("line_type").toString());
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
//			if (map.get("SubmitTime") != null && !"".equals(map.get("SubmitTime"))) {
//
//				model.setApplicationTime(map.get("SubmitTime").toString());
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
			if (map.get("kind_restitution") != null && !"".equals(map.get("kind_restitution").toString())) {
				model.setKindRestitution(Double.parseDouble(map.get("kind_restitution").toString()));
			}else{
				model.setKindRestitution(Double.parseDouble("0.0"));
			}
			if (map.get("compensate") != null
					&& !"".equals(map.get("compensate"))) {
				model.setCompensate(Double.parseDouble(map.get("compensate")
						.toString()));
			}
			if (map.get("calculate_income_ratio") != null
					&& !"".equals(map.get("calculate_income_ratio"))) {

				model.setCalculateIncomeRatio(map.get("calculate_income_ratio").toString());
			}
			if (map.get("themeinterface") != null
					&& !"".equals(map.get("themeinterface"))) {

				model.setThemeInterface(map.get("themeinterface").toString());
			}
			if (map.get("themeinterfacename") != null
					&& !"".equals(map.get("themeinterfacename"))) {

				model.setThemeInterfaceName(map.get("themeinterfacename")
						.toString());
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
	 * @param conditionQueryModel
	 * @return
	 */
	public int getHaveProcessCount(String processDefinitionKey, String userId,
			ConditionQueryModel conditionQueryModel) {
		String sql = "";
		String selectSql = "select count(processInstanceId) as total from (select ACTI.Proc_Inst_Id_ as processInstanceId,MAIN.Theme as theme,MAIN.city ,MAIN.country,MAIN.work_type,MAIN.workorder_type_name,MAIN.sub_type_name ,MAIN.key_word ,MAIN.work_order_degree,MAIN.project_amount,MAIN.Initiator as initiator,MAIN.Submit_Application_Time as SubmitTime,DECODE(MAIN.STATE,8,'等待接口调用',ACTI.Name_) as statusName,DECODE(MAIN.STATE,8,'waitingCallInterface',ACTI.Task_Def_Key_) as taskDefKey,MAIN.TASK_ASSIGNEE,MAIN.Send_Time as sendTime,MAIN.themeinterface,decode(MAIN.themeinterface,'overhaul','大修工单','reform','改造工单'，'') as themeinterfacename from (select  k.proc_inst_id_,k.name_,k.task_def_key_ from ACT_RU_TASK k inner join ACT_RE_PROCDEF D on k.PROC_DEF_ID_ = D.ID_ where D.KEY_ = ? and (exists (select LINK.USER_ID_ from ACT_HI_IDENTITYLINK LINK where USER_ID_ = ? and LINK.PROC_INST_ID_ = k.proc_inst_id_)) and (exists (select kst.assignee_ from act_hi_taskinst kst where kst.assignee_ = ? and kst.proc_inst_id_ = k.proc_inst_id_ and kst.end_time_ is not null))) ACTI,PNR_ACT_TRANSFER_OFFICE_MAIN MAIN WHERE ACTI.Proc_Inst_Id_ = MAIN.PROCESS_INSTANCE_ID(+) ) ST";
		
		ArrayList whereList=new ArrayList();
		whereList.add(processDefinitionKey);
		whereList.add(userId);
		whereList.add(userId);
		
		
		String whereSql = " WHERE 1=1";
		if (conditionQueryModel.getSendStartTime() != null
				&& !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and to_char(ST.sendTime,'yyyy-mm-dd hh24:mi:ss') >=?";
			whereList.add(conditionQueryModel.getSendStartTime());
		
		}
		if (conditionQueryModel.getSendEndTime() != null
				&& !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and to_char(ST.sendTime,'yyyy-mm-dd hh24:mi:ss') <= ?";
			whereList.add( conditionQueryModel.getSendEndTime());
		}
		if (conditionQueryModel.getWorkNumber() != null
				&& !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and ST.processInstanceId =? ";
			whereList.add(conditionQueryModel.getWorkNumber().trim());
		}
		
		if (conditionQueryModel.getTheme() != null
				&& !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and instr(ST.theme,?)>0 ";
			whereList.add(conditionQueryModel.getTheme().trim());
		}
		if (conditionQueryModel.getStatus() != null
				&& !conditionQueryModel.getStatus().equals("")) {
			whereSql += " and ST.taskDefKey = ? ";
			whereList.add(conditionQueryModel.getStatus());

		}
		sql += selectSql + whereSql;
		
		Object args[] =whereList.toArray();
		
		System.out.println("--------------大修改造 已处理工单条数sql=" + sql);

		int size = this.getJdbcTemplate().queryForInt(sql,args);
		return size;
	}

	/**
	 * 已处理工单明细
	 * 
	 * @param processDefinitionKey
	 * @param userId
	 * @param conditionQueryModel
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getHaveProcessList(String processDefinitionKey,
			String userId, ConditionQueryModel conditionQueryModel,
			int firstResult, int endResult, int pageSize) {
		
		
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		String selectSql = "select * from (select ACTI.Proc_Inst_Id_ as processInstanceId,decode(MAIN.sheet_id, null, '无', MAIN.sheet_id) as sheetId,MAIN.Compensate,MAIN.Theme as theme,MAIN.city ,MAIN.country,MAIN.work_type,MAIN.workorder_type_name,MAIN.sub_type_name ,MAIN.key_word ,MAIN.work_order_degree,MAIN.project_amount,MAIN.Calculate_Income_Ratio,MAIN.Initiator as initiator,MAIN.Submit_Application_Time as SubmitTime,MAIN.distributed_interface_time,DECODE(MAIN.STATE,8,'省公司审批通过-等待商城',ACTI.Name_) as statusName,DECODE(MAIN.STATE,8,'waitingCallInterface',ACTI.Task_Def_Key_) as taskDefKey,MAIN.TASK_ASSIGNEE,MAIN.Send_Time as sendTime,MAIN.themeinterface,decode(MAIN.themeinterface,'overhaul','大修工单','reform','改造工单'，'') as themeinterfacename from (select  k.proc_inst_id_,k.name_,k.task_def_key_ from ACT_RU_TASK k inner join ACT_RE_PROCDEF D on k.PROC_DEF_ID_ = D.ID_ where D.KEY_ = ? and (exists (select LINK.USER_ID_ from ACT_HI_IDENTITYLINK LINK where USER_ID_ = ? and LINK.PROC_INST_ID_ = k.proc_inst_id_)) and (exists (select kst.assignee_ from act_hi_taskinst kst where kst.assignee_ = ? and kst.proc_inst_id_ = k.proc_inst_id_ and kst.end_time_ is not null))) ACTI,PNR_ACT_TRANSFER_OFFICE_MAIN MAIN WHERE ACTI.Proc_Inst_Id_ = MAIN.PROCESS_INSTANCE_ID(+) ) ST";
		
		ArrayList whereList=new ArrayList();
		whereList.add(processDefinitionKey);
		whereList.add(userId);
		whereList.add(userId);
		
		
		String whereSql = " WHERE 1=1";
		if (conditionQueryModel.getSendStartTime() != null
				&& !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and to_char(ST.sendTime,'yyyy-mm-dd hh24:mi:ss') >=?";
			whereList.add(conditionQueryModel.getSendStartTime());
		
		}
		if (conditionQueryModel.getSendEndTime() != null
				&& !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and to_char(ST.sendTime,'yyyy-mm-dd hh24:mi:ss') <= ?";
			whereList.add( conditionQueryModel.getSendEndTime());
		}
		if (conditionQueryModel.getWorkNumber() != null
				&& !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and ST.processInstanceId =? ";
			whereList.add(conditionQueryModel.getWorkNumber().trim());
		}
		
		if (conditionQueryModel.getTheme() != null
				&& !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and instr(ST.theme,?)>0 ";
			whereList.add(conditionQueryModel.getTheme().trim());
		}
		if (conditionQueryModel.getStatus() != null
				&& !conditionQueryModel.getStatus().equals("")) {
			whereSql += " and ST.taskDefKey = ? ";
			whereList.add(conditionQueryModel.getStatus());

		}
		
		sql += selectSql + whereSql
				+ " order by ST.sendTime ) temp1 where rownum <= ? ) temp2 where temp2.num > ? ";
		
		whereList.add(endResult * pageSize);
		
		whereList.add(firstResult * pageSize);

		Object[] values = whereList.toArray();
		System.out.println("--------------大修改造 已处理工单明细sql=" + sql);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,values);
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
//				model.setApplicationTime(map.get("SubmitTime").toString());
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
			if (map.get("Calculate_Income_Ratio") != null
					&& !"".equals(map.get("Calculate_Income_Ratio"))) {
				model.setCalculateIncomeRatio(map.get("Calculate_Income_Ratio").toString());
			}
			
			if (map.get("themeinterface") != null
					&& !"".equals(map.get("themeinterface"))) {

				model.setThemeInterface(map.get("themeinterface").toString());
			}
			if (map.get("themeinterfacename") != null
					&& !"".equals(map.get("themeinterfacename"))) {

				model.setThemeInterfaceName(map.get("themeinterfacename")
						.toString());
			}
			if (map.get("sheetId") != null
					&& !"".equals(map.get("sheetId"))) {
				
				model.setSheetId(map.get("sheetId")
						.toString());
			}
			if (map.get("Compensate") != null
					&& !"".equals(map.get("Compensate"))) {
				
				model.setCompensate(Double.parseDouble(map.get(
				"Compensate").toString()));
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
	 * @param conditionQueryModel
	 * @return
	 */
	public int getByCreatingWorkOrderCount(String processDefinitionKey,
			String userId, ConditionQueryModel conditionQueryModel) {
		String sql = "";
		String selectSql = "select count(processInstanceId) as total from (select ACTI.Proc_Inst_Id_ as processInstanceId,MAIN.Theme as theme,MAIN.city,MAIN.country,MAIN.work_type,MAIN.workorder_type_name,MAIN.sub_type_name,MAIN.key_word,MAIN.work_order_degree,MAIN.project_amount,MAIN.Initiator as initiator,MAIN.Submit_Application_Time as SubmitTime,MAIN.TASK_ASSIGNEE,MAIN.Send_Time as sendTime,MAIN.State,MAIN.One_Initiator as oneInitiator,MAIN.Second_Initiator as twoInitiator,ACTI.END_TIME_,ACTI.DELETE_REASON_,case when ACTI.END_TIME_ is not null and ACTI.DELETE_REASON_ is null then '已归档' when ACTI.END_TIME_ is not null and ACTI.DELETE_REASON_ is not null then '已删除' when MAIN.State = '8' then '等待接口调用' else to_char(ACTI.StatusName) end as StatusName,case when ACTI.END_TIME_ is not null and ACTI.DELETE_REASON_ is null then 'archive' when ACTI.END_TIME_ is not null and ACTI.DELETE_REASON_ is not null then 'delete' when MAIN.State = '8' then 'waitingCallInterface' else to_char(ACTI.TaskDefKey) end as TaskDefKey,ACTI.TaskId,decode(MAIN.sheet_id, null, '无', MAIN.sheet_id) as sheetId,MAIN.themeinterface,decode(MAIN.themeinterface,'overhaul','大修工单','reform','改造工单'，'') as themeinterfacename from (" 
						   +"select RES.PROC_INST_ID_,RES.END_TIME_,RES.DELETE_REASON_,A2.NAME_ AS StatusName,A2.TASK_DEF_KEY_ AS TaskDefKey,A2.ID_ AS TaskId"
                           +" from ACT_HI_PROCINST RES,ACT_RE_PROCDEF DEF,(SELECT * FROM (SELECT ROW_NUMBER() OVER(PARTITION BY proc_inst_id_ ORDER BY start_time_ DESC) rn,HI.* FROM ACT_HI_TASKINST HI) WHERE rn = 1) A2"
                           +" where RES.PROC_DEF_ID_ = DEF.ID_ and RES.proc_inst_id_ = a2.proc_inst_id_ and DEF.KEY_ = ? and RES.START_USER_ID_ = ?) ACTI,PNR_ACT_TRANSFER_OFFICE_MAIN MAIN WHERE ACTI.Proc_Inst_Id_ = MAIN.PROCESS_INSTANCE_ID(+) and MAIN.State <> 2) ST";
		
		ArrayList whereList = new ArrayList(); 
		
		whereList.add(processDefinitionKey);
		whereList.add(userId);
		
		
		String whereSql = " WHERE 1=1";
		
		
		if (conditionQueryModel.getSendStartTime() != null
				&& !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and to_char(ST.sendTime,'yyyy-mm-dd hh24:mi:ss') >=?";
			whereList.add(conditionQueryModel.getSendStartTime());
		
		}
		if (conditionQueryModel.getSendEndTime() != null
				&& !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and to_char(ST.sendTime,'yyyy-mm-dd hh24:mi:ss') <= ?";
			whereList.add( conditionQueryModel.getSendEndTime());
		}
		if (conditionQueryModel.getWorkNumber() != null
				&& !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and ST.processInstanceId =?";
			whereList.add(conditionQueryModel.getWorkNumber().trim());
		}
		if (conditionQueryModel.getTheme() != null
				&& !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and instr(ST.theme,?)>0 ";
			whereList.add(conditionQueryModel.getTheme().trim());
		}
		if (conditionQueryModel.getStatus() != null
				&& !conditionQueryModel.getStatus().equals("")) {
			whereSql += " and ST.taskDefKey = ? ";
			whereList.add(conditionQueryModel.getStatus());

		}
		sql += selectSql + whereSql;
		
		System.out.println("------------大修改造 由我创建的工单条数sql=" + sql);

		Object[] args = whereList.toArray();
		int size = this.getJdbcTemplate().queryForInt(sql,args);
		return size;
	}

	/**
	 * 由我创建的工单明细
	 * 
	 * @param processDefinitionKey
	 * @param userId
	 * @param conditionQueryModel
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getByCreatingWorkOrderList(
			String processDefinitionKey, String userId,
			ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize) {
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		String selectSql = "select * from (select ACTI.Proc_Inst_Id_ as processInstanceId,MAIN.Theme as theme,MAIN.city,MAIN.country,MAIN.work_type,MAIN.workorder_type_name,MAIN.sub_type_name,MAIN.key_word,MAIN.work_order_degree,MAIN.project_amount,MAIN.Initiator as initiator,MAIN.Submit_Application_Time as SubmitTime,MAIN.distributed_interface_time,MAIN.TASK_ASSIGNEE,MAIN.Send_Time as sendTime,MAIN.State,MAIN.One_Initiator as oneInitiator,MAIN.Second_Initiator as twoInitiator,ACTI.END_TIME_,ACTI.DELETE_REASON_,case when ACTI.END_TIME_ is not null and ACTI.DELETE_REASON_ is null then '已归档' when ACTI.END_TIME_ is not null and ACTI.DELETE_REASON_ is not null then '已删除' when MAIN.State = '8' then '省公司审批通过-等待商城' else to_char(ACTI.StatusName) end as StatusName,case when ACTI.END_TIME_ is not null and ACTI.DELETE_REASON_ is null then 'archive' when ACTI.END_TIME_ is not null and ACTI.DELETE_REASON_ is not null then 'delete' when MAIN.State = '8' then 'waitingCallInterface' else to_char(ACTI.TaskDefKey) end as TaskDefKey,ACTI.TaskId,decode(MAIN.sheet_id, null, '无', MAIN.sheet_id) as sheetId,MAIN.themeinterface,decode(MAIN.themeinterface,'overhaul','大修工单','reform','改造工单','') as themeinterfacename from (" 
						   +"select RES.PROC_INST_ID_,RES.END_TIME_,RES.DELETE_REASON_,A2.NAME_ AS StatusName,A2.TASK_DEF_KEY_ AS TaskDefKey,A2.ID_ AS TaskId"
                           +" from ACT_HI_PROCINST RES,ACT_RE_PROCDEF DEF,(SELECT * FROM (SELECT ROW_NUMBER() OVER(PARTITION BY proc_inst_id_ ORDER BY start_time_ DESC) rn,HI.* FROM ACT_HI_TASKINST HI) WHERE rn = 1) A2"
                           +" where RES.PROC_DEF_ID_ = DEF.ID_ and RES.proc_inst_id_ = a2.proc_inst_id_ and DEF.KEY_ = ? and RES.START_USER_ID_ = ? ) ACTI,PNR_ACT_TRANSFER_OFFICE_MAIN MAIN WHERE ACTI.Proc_Inst_Id_ = MAIN.PROCESS_INSTANCE_ID(+) and MAIN.State <> 2) ST";
		
		ArrayList whereList = new ArrayList(); 
		
		whereList.add(processDefinitionKey);
		whereList.add(userId);
		
		
		String whereSql = " WHERE 1=1";
		
		
		if (conditionQueryModel.getSendStartTime() != null
				&& !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and to_char(ST.sendTime,'yyyy-mm-dd hh24:mi:ss') >=?";
			whereList.add(conditionQueryModel.getSendStartTime());
		
		}
		if (conditionQueryModel.getSendEndTime() != null
				&& !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and to_char(ST.sendTime,'yyyy-mm-dd hh24:mi:ss') <= ?";
			whereList.add( conditionQueryModel.getSendEndTime());
		}
		if (conditionQueryModel.getWorkNumber() != null
				&& !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and ST.processInstanceId =?";
			whereList.add(conditionQueryModel.getWorkNumber().trim());
		}
		if (conditionQueryModel.getTheme() != null
				&& !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and instr(ST.theme,?)>0 ";
			whereList.add(conditionQueryModel.getTheme().trim());
		}
		if (conditionQueryModel.getStatus() != null
				&& !conditionQueryModel.getStatus().equals("")) {
			whereSql += " and ST.taskDefKey = ? ";
			whereList.add(conditionQueryModel.getStatus());

		}
		
		
		
		
		sql += selectSql + whereSql
				+ " order by ST.sendTime ) temp1 where rownum <= ? ) temp2 where temp2.num > ? ";

		whereList.add(endResult * pageSize);
		
		whereList.add(firstResult * pageSize);
		
		
		Object[] values = whereList.toArray();
		
		System.out.println("------------大修改造 由我创建的工单明细sql=" + sql);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,values);
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
//				model.setApplicationTime(map.get("SubmitTime").toString());
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
			if (map.get("sheetId") != null
					&& !"".equals(map.get("sheetId"))) {
				model.setSheetId(map.get("sheetId").toString());
			}
//			if (map.get("END_TIME_") != null) {
//				if (!"".equals(map.get("END_TIME_"))) {
//					if (map.get("DELETE_REASON_") == null
//							|| "".equals(map.get("DELETE_REASON_"))) {
//						model.setStatusName("已归档");
//					} else {
//						model.setStatusName("已删除");
//					}
//
//				}
//			}
			if (map.get("TaskDefKey") != null
					&& !"".equals(map.get("TaskDefKey"))) {
				model.setTaskDefKey(map.get("TaskDefKey").toString());
			}
			if (map.get("TaskId") != null && !"".equals(map.get("TaskId"))) {
				model.setTaskId(map.get("TaskId").toString());
			}
			
			if (map.get("themeinterface") != null
					&& !"".equals(map.get("themeinterface"))) {

				model.setThemeInterface(map.get("themeinterface").toString());
			}
			if (map.get("themeinterfacename") != null
					&& !"".equals(map.get("themeinterfacename"))) {

				model.setThemeInterfaceName(map.get("themeinterfacename")
						.toString());
			}

			r.add(model);

		}

		return r;
	}

	/**
	 * 工单抓回 查询条数
	 * 
	 * @param processDefinitionKey
	 * @param userId
	 * @param relationTableName
	 *            关系表名
	 * @param taskIds
	 *            能够抓回的taskId
	 * @param conditionQueryModel
	 * @return
	 */
	public int getBackSheetCount(String processDefinitionKey, String userId,
			String relationTableName, String taskIds,
			ConditionQueryModel conditionQueryModel) {
		String sql = "";
		String selectSql = "select count(ID_) as total from (select RES.*,r.*"
                      	   +" from ACT_RU_TASK                 RES,"
                           +"ACT_RE_PROCDEF              D,"
                           +"overHaulNewProcess_relation r"
                           +" where RES.PROC_DEF_ID_ = D.ID_"
                           +" and RES.task_def_key_ = r.current_link"
                           +" and D.KEY_ = ?"
                           +" and RES.task_def_key_ in ('cityLineExamine','cityLineDirectorAudit','cityManageExamine','cityManageDirectorAudit','cityViceAudit')) ta," +
                           		"(select  h.proc_inst_id_, h.task_def_key_, h.assignee_ from act_hi_taskinst h group by h.proc_inst_id_,h.task_def_key_,h.assignee_) hi," +
                           		"pnr_act_transfer_office_main m where ta.proc_inst_id_ = hi.proc_inst_id_ and ta.before_link = hi.task_def_key_ and ta.proc_inst_id_ = m.process_instance_id and hi.assignee_ = "
				+ "?"
				+ " and (m.state != 3 and m.state != 6 and m.state != 8)";

		ArrayList whereList = new ArrayList(); 
		whereList.add(processDefinitionKey);
//		whereList.add(relationTableName);
		//whereList.add(taskIds);
		whereList.add(userId);
		
		String whereSql = "";
		if (conditionQueryModel.getWorkNumber() != null
				&& !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and m.process_instance_id = ? ";
			whereList.add(conditionQueryModel.getWorkNumber().trim());
		}
		if (conditionQueryModel.getTheme() != null
				&& !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and  instr(m.theme,?)>0 ";
			whereList.add(conditionQueryModel.getTheme());
		}
		
		sql = selectSql + whereSql;

		System.out.println("--------------大修改造 工单抓回 条数sql=" + sql);
		
		Object[] args = whereList.toArray(); 
		int size = this.getJdbcTemplate().queryForInt(sql,args);
		return size;
	}

	/**
	 * 工单抓回 查询明细
	 * 
	 * @param processDefinitionKey
	 * @param userId
	 * @param relationTableName
	 *            关系表名
	 * @param taskIds
	 *            能够抓回的taskId
	 * @param conditionQueryModel
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getBackSheetList(String processDefinitionKey,
			String userId, String relationTableName, String taskIds,
			ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize) {
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		String selectSql = "select ta.id_ as TaskId,m.initiator as Initiator,m.one_initiator as OneInitiator,m.process_instance_id as ProcessInstanceId,m.send_time as SendTime,m.theme as Theme,m.task_assignee,m.state as State,m.submit_application_time as SubmitTime,m.city,m.country,m.work_type,m.workorder_type_name,m.sub_type_name,m.key_word,m.work_order_degree,m.rollback_flag,m.precheck_flag,m.project_amount,m.compensate,m.calculate_income_ratio,decode(m.sheet_id, null, '无', m.sheet_id) as sheetId,ta.name_ as statusName,ta.task_def_key_ as taskDefKey,m.themeinterface,decode(m.themeinterface,'overhaul','大修工单','reform','改造工单','') as themeinterfacename" +
				" from (select RES.*,r.* from ACT_RU_TASK RES,ACT_RE_PROCDEF D,overHaulNewProcess_relation r where RES.PROC_DEF_ID_ = D.ID_ and RES.task_def_key_ = r.current_link and D.KEY_ = ? and RES.task_def_key_ in ('cityLineExamine','cityLineDirectorAudit','cityManageExamine','cityManageDirectorAudit','cityViceAudit')) ta," +
				"(select  h.proc_inst_id_, h.task_def_key_, h.assignee_ from act_hi_taskinst h group by h.proc_inst_id_,h.task_def_key_,h.assignee_) hi," +
				"pnr_act_transfer_office_main m where ta.proc_inst_id_ = hi.proc_inst_id_ and ta.before_link = hi.task_def_key_ and ta.proc_inst_id_ = m.process_instance_id and hi.assignee_ = "
				+ "?"
				+ " and (m.state != 3 and m.state != 6 and m.state != 8)";
		
		ArrayList whereList = new ArrayList(); 
		whereList.add(processDefinitionKey);
//		whereList.add(relationTableName);
	//	whereList.add(taskIds);
		whereList.add(userId);
		
		String whereSql = "";
		if (conditionQueryModel.getWorkNumber() != null
				&& !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and m.process_instance_id = ? ";
			whereList.add(conditionQueryModel.getWorkNumber().trim());
		}
		if (conditionQueryModel.getTheme() != null
				&& !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and  instr(m.theme,?)>0 ";
			whereList.add(conditionQueryModel.getTheme());
		}
		sql += selectSql + whereSql
				+ " order by m.send_time ) temp1 where rownum <=?) temp2 where temp2.num >?";
		
		whereList.add(endResult * pageSize);
		whereList.add(firstResult * pageSize);
		
		
		Object[] values = whereList.toArray();

		System.out.println("--------------大修改造 工单抓回 明细sql=" + sql);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,values);
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
//			if (map.get("SubmitTime") != null && !"".equals(map.get("SubmitTime"))) {
//
//				model.setApplicationTime(map.get("SubmitTime").toString());
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
			if (map.get("themeinterface") != null
					&& !"".equals(map.get("themeinterface"))) {

				model.setThemeInterface(map.get("themeinterface").toString());
			}
			if (map.get("themeinterfacename") != null
					&& !"".equals(map.get("themeinterfacename"))) {

				model.setThemeInterfaceName(map.get("themeinterfacename")
						.toString());
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
	 * @param themeInterface
	 *            工单的类型
	 * @param onditionQueryModel
	 * @return
	 */
	public int getArchivedCount(String processDefinitionKey, String userId,
			String themeInterface, ConditionQueryModel conditionQueryModel) {
		String sql = "";
		String selectSql = "select count(ACTI.PROC_INST_ID_ ) as total from (select RES.*,decode(RES.Delete_Reason_,'delete','delete','archived') as tempStatusName from (select  K.* from ACT_HI_PROCINST K inner join ACT_RE_PROCDEF D on K.PROC_DEF_ID_ = D.ID_ WHERE D.KEY_ = ? ) RES left join (select TAS.* from ACT_HI_TASKINST TAS where TAS.id_ in (select max(TAS.id_) from ACT_HI_TASKINST TAS group by TAS.proc_inst_id_)) TAS on RES.PROC_INST_ID_ = TAS.PROC_INST_ID_ WHERE RES.END_TIME_ is not NULL and (exists (select LINK.USER_ID_ from ACT_HI_IDENTITYLINK LINK where USER_ID_ = ? "
				+ " and LINK.PROC_INST_ID_ = RES.ID_))) ACTI,PNR_ACT_TRANSFER_OFFICE_MAIN MAIN where ACTI.Proc_Inst_Id_ = MAIN.PROCESS_INSTANCE_ID(+) and MAIN.THEMEINTERFACE in ("
				+ " select * from table( cast(f_str2List(?)as varchar2TableType)";
		
		
		ArrayList whereList = new ArrayList(); 
		whereList.add(processDefinitionKey);
		whereList.add(userId);
		whereList.add(themeInterface);
		
		
		String whereSql = "";	
		if (conditionQueryModel.getSendStartTime() != null
				&& !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and to_char(MAIN.Send_Time,'yyyy-mm-dd hh24:mi:ss') >=?";
			whereList.add(conditionQueryModel.getSendStartTime());
		
		}
		if (conditionQueryModel.getSendEndTime() != null
				&& !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and to_char(MAIN.Send_Time,'yyyy-mm-dd hh24:mi:ss') <= ?";
			whereList.add( conditionQueryModel.getSendEndTime());
		}
		if (conditionQueryModel.getWorkNumber() != null
				&& !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and ACTI.PROC_INST_ID_ = ? ";
			whereList.add( conditionQueryModel.getWorkNumber().trim());
		}
		if (conditionQueryModel.getTheme() != null
				&& !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and instr(MAIN.THEME,?)>0 ";
			whereList.add(conditionQueryModel.getTheme().trim());
		}
		if (conditionQueryModel.getStatus() != null
				&& !conditionQueryModel.getStatus().equals("")) {
			whereSql += " and tempStatusName = ? ";
			whereList.add(conditionQueryModel.getStatus());

		}
		sql += selectSql + whereSql;
		
		Object[] args = whereList.toArray();
		
		System.out.println("--------大修改造 已归档工单条数sql=" + sql);
		
		int size = this.getJdbcTemplate().queryForInt(sql,args);
		return size;
	}

	/**
	 * 已归档工单明细
	 * 
	 * @param processDefinitionKey
	 * @param userId
	 * @param themeInterface
	 * @param onditionQueryModel
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getArchivedList(String processDefinitionKey,
			String userId, String themeInterface,
			ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize) {
		
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		String selectSql = "select ACTI.PROC_INST_ID_ as processInstanceId,decode(MAIN.sheet_id, null, '无', MAIN.sheet_id) as sheetId,MAIN.THEME AS theme,MAIN.city ,MAIN.country,MAIN.work_type,MAIN.workorder_type_name,MAIN.sub_type_name ,MAIN.key_word ,MAIN.work_order_degree,MAIN.project_amount,MAIN.INITIATOR AS initiator,MAIN.submit_application_time as SubmitTime,decode(ACTI.Delete_Reason_, 'delete', '已删除', '已归档') as statusName,ACTI.Delete_Reason_,MAIN.TASK_ASSIGNEE,MAIN.themeinterface,decode(MAIN.themeinterface,'overhaul','大修工单','reform','改造工单'，'') as themeinterfacename from (select RES.*,decode(RES.Delete_Reason_,'delete','delete','archived') as tempStatusName from (select  K.* from ACT_HI_PROCINST K inner join ACT_RE_PROCDEF D on K.PROC_DEF_ID_ = D.ID_ WHERE D.KEY_ = ? ) RES left join (select TAS.* from ACT_HI_TASKINST TAS where TAS.id_ in (select max(TAS.id_) from ACT_HI_TASKINST TAS group by TAS.proc_inst_id_)) TAS on RES.PROC_INST_ID_ = TAS.PROC_INST_ID_ WHERE RES.END_TIME_ is not NULL and (exists (select LINK.USER_ID_ from ACT_HI_IDENTITYLINK LINK where USER_ID_ = ? "
				+ " and LINK.PROC_INST_ID_ = RES.ID_))) ACTI,PNR_ACT_TRANSFER_OFFICE_MAIN MAIN where ACTI.Proc_Inst_Id_ = MAIN.PROCESS_INSTANCE_ID(+)  and MAIN.THEMEINTERFACE in ("
				+" select * from table( cast(f_str2List(?)as varchar2TableType)";
		ArrayList whereList = new ArrayList(); 
		whereList.add(processDefinitionKey);
		whereList.add(userId);
		whereList.add(themeInterface);
		
		
		String whereSql = "";	
		if (conditionQueryModel.getSendStartTime() != null
				&& !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and to_char(MAIN.Send_Time,'yyyy-mm-dd hh24:mi:ss') >=?";
			whereList.add(conditionQueryModel.getSendStartTime());
		
		}
		if (conditionQueryModel.getSendEndTime() != null
				&& !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and to_char(MAIN.Send_Time,'yyyy-mm-dd hh24:mi:ss') <= ?";
			whereList.add( conditionQueryModel.getSendEndTime());
		}
		if (conditionQueryModel.getWorkNumber() != null
				&& !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and ACTI.PROC_INST_ID_ = ? ";
			whereList.add( conditionQueryModel.getWorkNumber().trim());
		}
		if (conditionQueryModel.getTheme() != null
				&& !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and instr(MAIN.THEME,?)>0 ";
			whereList.add(conditionQueryModel.getTheme().trim());
		}
		if (conditionQueryModel.getStatus() != null
				&& !conditionQueryModel.getStatus().equals("")) {
			whereSql += " and tempStatusName = ? ";
			whereList.add(conditionQueryModel.getStatus());

		}
		
		sql += selectSql + whereSql
		+ " order by MAIN.Send_Time ) temp1 where rownum <=? ) temp2 where temp2.num >? ";
		
		whereList.add(endResult * pageSize);
		whereList.add(firstResult * pageSize);
		
		
		Object[] values = whereList.toArray();

		System.out.println("--------大修改造 已归档工单明细sql=" + sql);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,values);
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
				model.setWorkOrderDegree(map.get("work_order_degree").toString());
			}

			if (map.get("project_amount") != null
					&& !"".equals(map.get("project_amount"))) {
				model.setProjectAmount(Double.parseDouble(map.get(
						"project_amount").toString()));
			}
			
			if (map.get("themeinterface") != null
					&& !"".equals(map.get("themeinterface"))) {

				model.setThemeInterface(map.get("themeinterface").toString());
			}
			if (map.get("themeinterfacename") != null
					&& !"".equals(map.get("themeinterfacename"))) {

				model.setThemeInterfaceName(map.get("themeinterfacename")
						.toString());
			}
			if (map.get("sheetId") != null
					&& !"".equals(map.get("sheetId"))) {
				
				model.setSheetId(map.get("sheetId")
						.toString());
			}

			r.add(model);

		}

		return r;
	}
	
	public List<PnrAndroidPhotoFile> getPrecheckPhotoes(String userId,String photoDescribe,String createTime) {
		StringBuilder sql = new StringBuilder("select * from pnr_android_photo p");
		sql.append("  where p.city=");
		sql.append(" (select substr(nvl(a.areaid,'0'),1,4) from pnr_user u");
		sql.append(" left join taw_system_dept d");
		sql.append(" on u.dept_id = d.deptid");
		sql.append(" left join taw_system_area a");
		sql.append(" on d.areaid=a.areaid");
		sql.append(" where d.deleted='0' and u.user_id='").append(userId).append("')");
		sql.append(" and p.user_id = '").append(userId).append("'");
		sql.append(" and nvl(p.state, 0) != 1 ");
		if(photoDescribe!=null && !"".equals(photoDescribe)){
		sql.append(" and  p.faultdescription like '%").append(photoDescribe).append("%'");
		}
		if(createTime!=null && !"".equals(createTime)){
			
			sql.append(" and to_date(p.createtime,'yyyy-mm-dd hh24:mi:ss')");
			sql.append(">=to_date('").append(createTime).append("','yyyy-mm-dd hh24:mi:ss')");
		}
		
		System.out.println("--------------大修改造 新建工单页面图片集合sql="+sql.toString());
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
	public String[] getPhotoByProcessInstanceId(
			String processInstanceId) {
		StringBuilder sql = new StringBuilder("select po.*,pu.name from pnr_act_precheck_photo_ship p join");
		sql.append(" pnr_android_photo po on p.photo_id = po.id");
		sql.append(" left join pnr_user pu  on pu.user_id=po.user_id");
		sql.append(" where p.processinstance_id='").append(processInstanceId).append("'");
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
}
