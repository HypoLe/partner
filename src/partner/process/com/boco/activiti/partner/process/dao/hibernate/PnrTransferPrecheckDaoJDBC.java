package com.boco.activiti.partner.process.dao.hibernate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.activiti.partner.process.dao.IPnrTransferPrecheckJDBCDao;
import com.boco.activiti.partner.process.po.ConditionQueryModel;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.eoms.base.util.StaticMethod;

public class PnrTransferPrecheckDaoJDBC extends JdbcDaoSupport implements
IPnrTransferPrecheckJDBCDao{
	
	public int getToreplyJobOfEmergencyJobCount(String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status,String city,String country,String lineType,String workType) {
		String sql = "";
		String selectSql = "select count(*) as total from (select  RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_ WHERE RES.ASSIGNEE_ = "
				+ "?"
				+ " and D.KEY_ = 'newTwoTransferPrecheck' and RES.Suspension_State_ = 1) t,pnr_act_transfer_office_main m where t.proc_inst_id_ = m.process_instance_id  and m.state!=8 ";
		ArrayList whereList =new ArrayList();
		
		whereList.add(userId);
		
		String whereSql = "";
		if (sendStartTime != null && !sendStartTime.equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >=?";
			whereList.add(sendStartTime);
		}
		if (sendEndTime != null && !sendEndTime.equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <=?";
			whereList.add(sendEndTime);
		}
		if (wsNum != null && !wsNum.equals("")) {
			whereSql += " and m.process_instance_id =?";
			whereList.add(wsNum.trim());
		}
		if (wsTitle != null && !wsTitle.equals("")) {
			whereSql += " and instr(m.theme,?)>0 ";
			whereList.add(wsTitle);
		}
		if (status != null && !status.equals("")) {
			//whereSql += " and m.state = '" + status + "'";
			whereSql += " and t.task_def_key_ =?";
			whereList.add(status);

		}
		if (city != null && !city.equals("")) {
			whereSql += " and m.city =?";
			whereList.add(city);
			
		}
		if (country != null && !country.equals("")) {
			whereSql += " and m.country =?";
			whereList.add(country);
			
		}
		if (lineType != null && !lineType.equals("")) {
			whereSql += " and m.work_type =?";
			whereList.add(lineType);
			
		}
		if (workType != null && !workType.equals("")) {
			whereSql += " and m.workorder_type_name =?";
			whereList.add(workType);
			
		}
		sql = selectSql + whereSql;

		Object args[] =whereList.toArray();
		int size = this.getJdbcTemplate().queryForInt(sql,args);
		return size;

	}
	
	public List<TaskModel> getToreplyJobOfEmergencyJobList(String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status,String city,String country,String lineType,String workType, int firstResult, int endResult,
			int pageSize) {
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		String selectSql = "select t.id_ as TaskId,m.initiator as Initiator,m.one_initiator as OneInitiator,m.process_instance_id as ProcessInstanceId,m.send_time as SendTime,m.theme as Theme,m.task_assignee,m.state as State,m.submit_application_time as SubmitTime,m.city ,m.country,m.work_type,m.workorder_type_name,m.sub_type_name ,m.key_word ,m.work_order_degree,m.project_amount,t.name_,t.task_def_key_ from (select  RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_ WHERE RES.ASSIGNEE_ = "
				+ "?"
				+ " and D.KEY_ = 'newTwoTransferPrecheck' and RES.Suspension_State_ = 1) t,pnr_act_transfer_office_main m where t.proc_inst_id_ = m.process_instance_id  and m.state!=8";
		
		ArrayList whereList =new ArrayList();
		
		whereList.add(userId);
		
		String whereSql = "";
		if (sendStartTime != null && !sendStartTime.equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >=?";
			whereList.add(sendStartTime);
		}
		if (sendEndTime != null && !sendEndTime.equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <=?";
			whereList.add(sendEndTime);
		}
		if (wsNum != null && !wsNum.equals("")) {
			whereSql += " and m.process_instance_id =?";
			whereList.add(wsNum.trim());
		}
		if (wsTitle != null && !wsTitle.equals("")) {
			whereSql += " and instr(m.theme,?)>0 ";
			whereList.add(wsTitle);
		}
		if (status != null && !status.equals("")) {
			//whereSql += " and m.state = '" + status + "'";
			whereSql += " and t.task_def_key_ =?";
			whereList.add(status);

		}
		if (city != null && !city.equals("")) {
			whereSql += " and m.city =?";
			whereList.add(city);
			
		}
		if (country != null && !country.equals("")) {
			whereSql += " and m.country =?";
			whereList.add(country);
			
		}
		if (lineType != null && !lineType.equals("")) {
			whereSql += " and m.work_type =?";
			whereList.add(lineType);
			
		}
		if (workType != null && !workType.equals("")) {
			whereSql += " and m.workorder_type_name =?";
			whereList.add(workType);
			
		}
		
		sql += selectSql + whereSql
				+ " order by t.create_time_ desc) temp1 where rownum <=?) temp2 where temp2.num >?";
		
		whereList.add(endResult * pageSize);
		whereList.add(firstResult * pageSize);

		Object values[] =whereList.toArray();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,values);
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
				model.setWorkOrderDegree(map.get("key_word").toString());
			}
			
			if(map.get("project_amount")!=null && !"".equals(map.get("project_amount"))){				
				model.setProjectAmount(Double.parseDouble(map.get("project_amount").toString()));
			}
		
			if(map.get("name_")!=null && !"".equals(map.get("name_"))){				
				model.setStatusName(map.get("name_").toString());

			}
			
			if(map.get("task_def_key_")!=null && !"".equals(map.get("task_def_key_"))){				
				model.setTaskDefKey(map.get("task_def_key_").toString());
			}
			
			r.add(model);

		}

		return r;

	}

	@Override
	public List<Map> getSGSByCountryCSJ(String countryCSJ,String roleid) {
		String sql = "select t.userid from taw_system_userrefrole t" +
				" left join taw_system_sub_role sr on t.subroleid = sr.id" +
				" where" +
				" sr.roleid='"+roleid+"'" +
				" and sr.deptid=( select substr(a.areaid,1,4) from taw_system_user u ,taw_system_dept d ,taw_system_area a" +
				"  where u.deptid=d.deptid" +
				"  and d.areaid = a.areaid" +
				" and u.userid='"+countryCSJ+"' and d.deleted = '0')";
		System.out.println("sql===="+sql);
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		return list;
	}

	@Override
	public List<Map> getDaiWeiSGSByCountryCSJ(String countryCSJ) {
		String sql = "select p.daiwei_gs_id from pnr_act_transfer_relationship p where p.country_csj_id='"+countryCSJ+"'";
		List<Map> list =this.getJdbcTemplate().queryForList(sql);
		return list;
	}

	@Override
	public List<Map> getCityCSJByCountryCSJ(String countryCSJ) {
		String sql ="select p.city_csj_id from pnr_act_transfer_relationship p where p.country_csj_id='"+countryCSJ+"'";
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		return list;
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
			int endResult, int pageSize){
		String sql = "select temp2.* from (select temp1.*, rownum num from (";		
		String selectSql = "select * from (select ACTI.Proc_Inst_Id_ as processInstanceId,MAIN.Theme as theme,MAIN.city ,MAIN.send_time as sendTime,MAIN.country,MAIN.work_type,MAIN.workorder_type_name,MAIN.sub_type_name ,MAIN.key_word ,MAIN.work_order_degree,MAIN.project_amount,MAIN.Initiator as initiator,MAIN.Submit_Application_Time as SubmitTime,decode(MAIN.State, 8, '市公司审批完毕', ACTI.Name_) as statusName,decode(MAIN.State, 8, 'manageExamineCompleted', ACTI.Task_Def_Key_) as taskDefKey,MAIN.TASK_ASSIGNEE from (select RES.Proc_Inst_Id_, TAS.Name_,TAS.Task_Def_Key_ from ACT_HI_PROCINST RES inner join ACT_RE_PROCDEF DEF on RES.PROC_DEF_ID_ = DEF.ID_ left join (select TAS.* from ACT_HI_TASKINST TAS where TAS.id_ in (select max(TAS.id_) from ACT_HI_TASKINST TAS group by TAS.proc_inst_id_)) TAS on RES.PROC_INST_ID_ = TAS.PROC_INST_ID_ WHERE  RES.PROC_DEF_ID_ like ?||':%:%' and RES.END_TIME_ IS NULL and (exists (select LINK.USER_ID_ from ACT_HI_IDENTITYLINK LINK where USER_ID_ = ? and LINK.PROC_INST_ID_ = RES.ID_))) ACTI,PNR_ACT_TRANSFER_OFFICE_MAIN MAIN WHERE ACTI.Proc_Inst_Id_ = MAIN.PROCESS_INSTANCE_ID(+) ) ST";
		
		ArrayList whereList=new ArrayList();
		
		whereList.add(processDefinitionKey);
		whereList.add(userId);
		
		String whereSql = " WHERE 1=1";
		
		if (sendStartTime != null && !sendStartTime.equals("")) {
			whereSql += " and to_char(ST.sendTime,'yyyy-mm-dd hh24:mi:ss') >=?";
			whereList.add(sendStartTime);
		}
		if (sendEndTime != null && !sendEndTime.equals("")) {
			whereSql += " and to_char(ST.sendTime,'yyyy-mm-dd hh24:mi:ss') <=?";
			whereList.add(sendEndTime);
		}
		if (wsNum != null && !wsNum.equals("")) {
			whereSql += " and ST.processInstanceId =?";
			whereList.add(wsNum);
		}
		if (wsTitle != null && !wsTitle.equals("")) {
			whereSql += " and  instr(ST.theme,?)>0";
			whereList.add(wsTitle);
		}
		if (status != null && !status.equals("")) {
			whereSql += " and ST.taskDefKey = ?";
			whereList.add(status);

		}
		sql += selectSql + whereSql
				+ " order by ST.processInstanceId) temp1 where rownum <=?) temp2 where temp2.num > ?";
		
		whereList.add(endResult * pageSize);
		whereList.add(firstResult * pageSize);

		System.out.println("已处理工单明细sql="+sql);
		
		Object values [] =whereList.toArray();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,values);
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
	 * 已处理工单条数
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
			String wsTitle, String status){
		String sql="";
		String selectSql = "select count(processInstanceId) as total from (select ACTI.Proc_Inst_Id_ as processInstanceId,MAIN.Theme as theme,MAIN.Initiator as initiator,MAIN.Submit_Application_Time as sendTime,decode(MAIN.State, 8, '市公司审批完毕', ACTI.Name_) as statusName,decode(MAIN.State, 8, 'manageExamineCompleted', ACTI.Task_Def_Key_) as taskDefKey,MAIN.TASK_ASSIGNEE from (select RES.Proc_Inst_Id_, TAS.Name_,TAS.Task_Def_Key_ from ACT_HI_PROCINST RES inner join ACT_RE_PROCDEF DEF on RES.PROC_DEF_ID_ = DEF.ID_ left join (select TAS.* from ACT_HI_TASKINST TAS where TAS.id_ in (select max(TAS.id_) from ACT_HI_TASKINST TAS group by TAS.proc_inst_id_)) TAS on RES.PROC_INST_ID_ = TAS.PROC_INST_ID_ WHERE RES.PROC_DEF_ID_ like ?||':%:%' and RES.END_TIME_ IS NULL and (exists (select LINK.USER_ID_ from ACT_HI_IDENTITYLINK LINK where USER_ID_ = ? and LINK.PROC_INST_ID_ = RES.ID_))) ACTI,PNR_ACT_TRANSFER_OFFICE_MAIN MAIN WHERE ACTI.Proc_Inst_Id_ = MAIN.PROCESS_INSTANCE_ID(+) ) ST";
		ArrayList whereList=new ArrayList();
		whereList.add(processDefinitionKey);
		whereList.add(userId);
		
		String whereSql = " WHERE 1=1";
		
		if (sendStartTime != null && !sendStartTime.equals("")) {
			whereSql += " and to_char(ST.sendTime,'yyyy-mm-dd hh24:mi:ss') >=?";
			whereList.add(sendStartTime);
		}
		if (sendEndTime != null && !sendEndTime.equals("")) {
			whereSql += " and to_char(ST.sendTime,'yyyy-mm-dd hh24:mi:ss') <=?";
			whereList.add(sendEndTime);
		}
		if (wsNum != null && !wsNum.equals("")) {
			whereSql += " and ST.processInstanceId =?";
			whereList.add(wsNum);
		}
		if (wsTitle != null && !wsTitle.equals("")) {
			whereSql += " and  instr(ST.theme,?)>0";
			whereList.add(wsTitle);
		}
		if (status != null && !status.equals("")) {
			whereSql += " and ST.taskDefKey = ?";
			whereList.add(status);

		}
		sql += selectSql + whereSql;
		
		System.out.println("已处理工单条数sql="+sql);
		Object args[] =whereList.toArray();
		int size = this.getJdbcTemplate().queryForInt(sql,args);
		return size;
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
			String wsNum, String wsTitle, String status, int firstResult,
			int endResult, int pageSize){
		String sql = "select temp2.* from (select temp1.*, rownum num from (";		
		String selectSql = "select ACTI.PROC_INST_ID_ as processInstanceId,MAIN.THEME AS theme,MAIN.city ,MAIN.country,MAIN.work_type,MAIN.workorder_type_name,MAIN.sub_type_name ,MAIN.key_word ,MAIN.work_order_degree,MAIN.project_amount,MAIN.INITIATOR AS initiator,MAIN.submit_application_time as SubmitTime,decode(ACTI.Delete_Reason_, 'delete', '已删除', '已归档') as statusName,ACTI.Delete_Reason_,MAIN.TASK_ASSIGNEE from (select RES.* from ACT_HI_PROCINST RES inner join ACT_RE_PROCDEF DEF on RES.PROC_DEF_ID_ = DEF.ID_ left join (select TAS.* from ACT_HI_TASKINST TAS where TAS.id_ in (select max(TAS.id_) from ACT_HI_TASKINST TAS group by TAS.proc_inst_id_)) TAS on RES.PROC_INST_ID_ = TAS.PROC_INST_ID_ WHERE RES.PROC_DEF_ID_ like ?||':%:%' and RES.END_TIME_ is not NULL and (exists (select LINK.USER_ID_ from ACT_HI_IDENTITYLINK LINK where USER_ID_ = ? and LINK.PROC_INST_ID_ = RES.ID_))) ACTI,PNR_ACT_TRANSFER_OFFICE_MAIN MAIN where ACTI.Proc_Inst_Id_ = MAIN.PROCESS_INSTANCE_ID(+)  and MAIN.THEMEINTERFACE = 'interface'";
		
		ArrayList whereList = new ArrayList(); 
		whereList.add(processDefinitionKey);
		whereList.add(userId);
		
		String whereSql = "";
		if (sendStartTime != null && !sendStartTime.equals("")) {
			whereSql += " and to_char(MAIN.SUBMIT_APPLICATION_TIME,'yyyy-mm-dd hh24:mi:ss')>=?";
			whereList.add(sendStartTime);
		}
		if (sendEndTime != null && !sendEndTime.equals("")) {
			whereSql += " and to_char(MAIN.SUBMIT_APPLICATION_TIME,'yyyy-mm-dd hh24:mi:ss')<=?";
			whereList.add(sendEndTime);
		}
		if (wsNum != null && !wsNum.equals("")) {
			whereSql += " and ACTI.PROC_INST_ID_ =?";
			whereList.add(wsNum);
		}
		if (wsTitle != null && !wsTitle.equals("")) {
			whereSql += " and  instr(MAIN.THEME,?)>0";
			whereList.add(wsTitle);
		}
//		if (status != null && !status.equals("")) {
//			whereSql += " and ST.taskDefKey = '"+status+"'";
//
//		}
		sql += selectSql + whereSql
				+ " order by ACTI.PROC_INST_ID_ asc) temp1 where rownum <=?) temp2 where temp2.num >?";
		
		whereList.add(endResult * pageSize);
		whereList.add(firstResult * pageSize);

		System.out.println("已归档工单明细sql="+sql);
		
		Object values[] =whereList.toArray();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,values);
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
			String wsTitle, String status){
		String sql="";
		String selectSql = "select count(ACTI.PROC_INST_ID_ ) as total from (select RES.* from ACT_HI_PROCINST RES inner join ACT_RE_PROCDEF DEF on RES.PROC_DEF_ID_ = DEF.ID_ left join (select TAS.* from ACT_HI_TASKINST TAS where TAS.id_ in (select max(TAS.id_) from ACT_HI_TASKINST TAS group by TAS.proc_inst_id_)) TAS on RES.PROC_INST_ID_ = TAS.PROC_INST_ID_ WHERE RES.PROC_DEF_ID_ like ?||':%:%' and RES.END_TIME_ is not NULL and (exists (select LINK.USER_ID_ from ACT_HI_IDENTITYLINK LINK where USER_ID_ = ? and LINK.PROC_INST_ID_ = RES.ID_))) ACTI,PNR_ACT_TRANSFER_OFFICE_MAIN MAIN where ACTI.Proc_Inst_Id_ = MAIN.PROCESS_INSTANCE_ID(+) and MAIN.THEMEINTERFACE = 'interface'";
		ArrayList whereList = new ArrayList(); 
		whereList.add(processDefinitionKey);
		whereList.add(userId);
		
		String whereSql = "";
		if (sendStartTime != null && !sendStartTime.equals("")) {
			whereSql += " and to_char(MAIN.SUBMIT_APPLICATION_TIME,'yyyy-mm-dd hh24:mi:ss')>=?";
			whereList.add(sendStartTime);
		}
		if (sendEndTime != null && !sendEndTime.equals("")) {
			whereSql += " and to_char(MAIN.SUBMIT_APPLICATION_TIME,'yyyy-mm-dd hh24:mi:ss')<=?";
			whereList.add(sendEndTime);
		}
		if (wsNum != null && !wsNum.equals("")) {
			whereSql += " and ACTI.PROC_INST_ID_ =?";
			whereList.add(wsNum);
		}
		if (wsTitle != null && !wsTitle.equals("")) {
			whereSql += " and  instr(MAIN.THEME,?)>0";
			whereList.add(wsTitle);
		}
//		if (status != null && !status.equals("")) {
//			whereSql += " and ST.taskDefKey = '"+status+"'";
//
//		}
		sql += selectSql + whereSql;
		
		Object args[] =whereList.toArray();
		int size = this.getJdbcTemplate().queryForInt(sql,args);
		return size;
	}

	@Override
	public int getToreplyJobOfEmergencyJobForCountersignCount(String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status, String city, String country,
			String lineType, String workType) {
		String sql = "";
		String selectSql = "select count(*) as total from (select distinct m.* from pnr_act_transfer_office_main m"
				+ " left join pnr_act_review_staff f on m.city = f.city_id"
		        + " left join pnr_act_ru_countersign  g on m.process_instance_id = g.process_instance_id"
		+ " where m.state = 6 "+
		" and instr(f.user_id||',','"+userId+"'||',')>0"+
		" and decode(g.user_id,null,'''',g.user_id) <> '"+userId+"'";
		
		String whereSql = "";
		if (sendStartTime != null && !sendStartTime.equals("")) {
			whereSql += " and m.send_time >=to_date('" + sendStartTime
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}
		if (sendEndTime != null && !sendEndTime.equals("")) {
			whereSql += " and m.send_time <= to_date('" + sendEndTime
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}
		if (wsNum != null && !wsNum.equals("")) {
			whereSql += " and m.process_instance_id ='" + wsNum.trim() + "'";
		}
		if (wsTitle != null && !wsTitle.equals("")) {
			whereSql += " and m.theme like '%" + wsTitle.trim() + "%'";
		}
		if (status != null && !status.equals("")) {
			String[] strs = status.split(",");
			int size = strs.length;
			if(size>1){	
				String str="";
				for(int i=0; i<size; i++){
					str+="'"+strs[i]+"',";
				}
				str = str.substring(0,str.length()-1);
				whereSql += " and m.themeinterface in ("+str+")";
			}else{
				whereSql += " and m.themeinterface='"+status+"'";
				
			}

		}
		
		if (city != null && !city.equals("")) {
			whereSql += " and m.city = '"+city+"'";
			
		}
		if (country != null && !country.equals("")) {
			whereSql += " and m.country = '"+country+"'";
			
		}
		if (lineType != null && !lineType.equals("")) {
			whereSql += " and m.work_type = '"+lineType+"'";
			
		}
		if (workType != null && !workType.equals("")) {
			whereSql += " and m.workorder_type_name = '"+workType+"'";
			
		}
		whereSql +=")";
		sql = selectSql + whereSql;


		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		return Integer.parseInt(list.get(0).get("total").toString());
	}

	@Override
	public List<TaskModel> getToreplyJobOfEmergencyJobForCountersignList(
			String userId, String sendStartTime, String sendEndTime,
			String wsNum, String wsTitle, String status, String city,
			String country, String lineType, String workType, int firstResult,
			int endResult, int pageSize) {
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		String selectSql = "select m.initiator as Initiator,m.one_initiator as OneInitiator," +
				"m.process_instance_id as ProcessInstanceId,m.send_time as SendTime,m.theme as Theme," +
				"m.task_assignee,m.state as State,m.submit_application_time as SubmitTime,m.city ," +
				"m.country,m.work_type,m.workorder_type_name,m.sub_type_name ,m.key_word ,m.work_order_degree," +
				"m.project_amount from pnr_act_transfer_office_main m"+
				" left join pnr_act_review_staff f on m.city = f.city_id"+
				" left join pnr_act_ru_countersign  g on m.process_instance_id = g.process_instance_id"+
				" where m.state = 6 "+
				" and instr(f.user_id||',','"+userId+"'||',')>0"+
				" and decode(g.user_id,null,'''',g.user_id) <> '"+userId+"'";
		   
		String whereSql = "";
		if (sendStartTime != null && !sendStartTime.equals("")) {
			whereSql += " and m.send_time >=to_date('" + sendStartTime
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}
		if (sendEndTime != null && !sendEndTime.equals("")) {
			whereSql += " and m.send_time <= to_date('" + sendEndTime
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}
		if (wsNum != null && !wsNum.equals("")) {
			whereSql += " and m.process_instance_id ='" + wsNum.trim() + "'";
		}
		if (wsTitle != null && !wsTitle.equals("")) {
			whereSql += " and m.theme like '%" + wsTitle.trim() + "%'";
		}
		if (status != null && !status.equals("")) {
			String[] strs = status.split(",");
			int size = strs.length;
			if(size>1){	
				String str="";
				for(int i=0; i<size; i++){
					str+="'"+strs[i]+"',";
				}
				str = str.substring(0,str.length()-1);
				whereSql += " and m.themeinterface in ("+str+")";
			}else{
				whereSql += " and m.themeinterface='"+status+"'";
				
			}

		}
		if (city != null && !city.equals("")) {
			whereSql += " and m.city = '"+city+"'";
			
		}
		if (country != null && !country.equals("")) {
			whereSql += " and m.country = '"+country+"'";
			
		}
		if (lineType != null && !lineType.equals("")) {
			whereSql += " and m.work_type = '"+lineType+"'";
			
		}
		if (workType != null && !workType.equals("")) {
			whereSql += " and m.workorder_type_name = '"+workType+"'";
			
		}
		
		sql += selectSql + whereSql
				+ " order by m.send_time desc) temp1 where rownum <="
				+ endResult * pageSize + ") temp2 where temp2.num > "
				+ firstResult * pageSize;


		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		for (Map map : list) {
			TaskModel model = new TaskModel();
			
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
				model.setWorkOrderDegree(map.get("key_word").toString());
			}
			
			if(map.get("project_amount")!=null && !"".equals(map.get("project_amount"))){				
				model.setProjectAmount(Double.parseDouble(map.get("project_amount").toString()));
			}
		
			r.add(model);

		}

		return r;
	}

	/***********************************************大修改造工单**********************************************************/
	@Override
	public String getDeptByUserId(String userid) {
		String  sql = "select u.dept_id from pnr_user u where u.user_id='"+userid+"'";
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		String deptid="";
		if(list!=null &&list.size()>0){
			deptid = list.get(0).get("dept_id").toString();
		}
		return deptid;
	}

	@Override
	public int getOverhaulRemakCount(String userid,
			ConditionQueryModel conditionQueryModel) {
		String sql = "";
		String selectSql = "select count(*) as total from (select distinct RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_ WHERE RES.ASSIGNEE_ = '"
				+ userid
				+ "' and (D.KEY_ = 'overHaulProcess' or D.KEY_ = 'reformProcess') and RES.Suspension_State_ = 1) t,pnr_act_transfer_office_main m where t.proc_inst_id_ = m.process_instance_id  and m.state!=8 ";
		String whereSql = "";
		whereSql = getWhereSql(conditionQueryModel, whereSql);
		
		sql = selectSql + whereSql;
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		return Integer.parseInt(list.get(0).get("total").toString());
	}

	private String getWhereSql(ConditionQueryModel conditionQueryModel,
			String whereSql) {
		if(conditionQueryModel!=null){
			String sendStartTime = StaticMethod.nullObject2String(conditionQueryModel.getSendStartTime());
			if (sendStartTime != null && !sendStartTime.equals("")) {
				whereSql += " and m.send_time >=to_date('" + sendStartTime
				+ "','yyyy-mm-dd hh24:mi:ss')";
			}
			String sendEndTime = StaticMethod.nullObject2String(conditionQueryModel.getSendEndTime());
			if (sendEndTime != null && !sendEndTime.equals("")) {
				whereSql += " and m.send_time <= to_date('" + sendEndTime
				+ "','yyyy-mm-dd hh24:mi:ss')";
			}
			String wsNum = StaticMethod.nullObject2String(conditionQueryModel.getWorkNumber());
			if (wsNum != null && !wsNum.equals("")) {
				whereSql += " and m.process_instance_id ='" + wsNum.trim() + "'";
			}
			String wsTitle = StaticMethod.nullObject2String(conditionQueryModel.getTheme());
			if (wsTitle != null && !wsTitle.equals("")) {
				whereSql += " and m.theme like '%" + wsTitle.trim() + "%'";
			}
			String status = StaticMethod.nullObject2String(conditionQueryModel.getStatus());
			if (status != null && !status.equals("")) {
				whereSql += " and t.task_def_key_ = '"+status+"'";
			}
			String city = StaticMethod.nullObject2String(conditionQueryModel.getCity());
			if (city != null && !city.equals("")) {
				whereSql += " and m.city = '"+city+"'";
			}
			String country = StaticMethod.nullObject2String(conditionQueryModel.getCountry());
			if (country != null && !country.equals("")) {
				whereSql += " and m.country = '"+country+"'";
			}
			String lineType = StaticMethod.nullObject2String(conditionQueryModel.getLineType());
			if (lineType != null && !lineType.equals("")) {
				whereSql += " and m.work_type = '"+lineType+"'";
			}
			String workType = StaticMethod.nullObject2String(conditionQueryModel.getWorkOrderType());
			if (workType != null && !workType.equals("")) {
				whereSql += " and m.workorder_type_name = '"+workType+"'";
			}
		}
		return whereSql;
	}

	@Override
	public List<TaskModel> getOverhaulRemakJobList(String userId,
			ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize) {
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		String selectSql = "select t.id_ as TaskId,m.initiator as Initiator,m.one_initiator as OneInitiator,m.process_instance_id as ProcessInstanceId,m.send_time as SendTime,m.theme as Theme,m.task_assignee,m.state as State,m.submit_application_time as SubmitTime,m.city ,m.country,m.work_type,m.workorder_type_name,m.sub_type_name ,m.key_word ,m.work_order_degree,m.project_amount from (select distinct RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_ WHERE RES.ASSIGNEE_ = '"
				+ userId
				+ "' and (D.KEY_ = 'overHaulProcess' or D.KEY_ = 'reformProcess') and RES.Suspension_State_ = 1) t,pnr_act_transfer_office_main m where t.proc_inst_id_ = m.process_instance_id  and m.state!=8";
		String whereSql = "";
		whereSql = getWhereSql(conditionQueryModel, whereSql);
		sql += selectSql + whereSql
				+ " order by t.create_time_ desc) temp1 where rownum <="
				+ endResult * pageSize + ") temp2 where temp2.num > "
				+ firstResult * pageSize;

		System.out.println(sql);
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
				model.setWorkOrderDegree(map.get("key_word").toString());
			}
			
			if(map.get("project_amount")!=null && !"".equals(map.get("project_amount"))){				
				model.setProjectAmount(Double.parseDouble(map.get("project_amount").toString()));
			}
		
			r.add(model);

		}

		return r;
	}

	@Override
	public int getOverhaulRemakArchivedCount(String processOverhaulKey,
			String processRemakeKey,String userid, ConditionQueryModel conditionQueryModel) {
		String sql="";
		String selectSql="";
		String selectFirstSql = "select count(ACTI.PROC_INST_ID_ ) as total from (select RES.* from ACT_HI_PROCINST RES"
				+ " inner join ACT_RE_PROCDEF DEF on RES.PROC_DEF_ID_ = DEF.ID_"
				+ " left join (select TAS.* from ACT_HI_TASKINST TAS "
				+ " where TAS.id_ in (select max(TAS.id_) from ACT_HI_TASKINST TAS group by TAS.proc_inst_id_)) TAS "
				+ " on RES.PROC_INST_ID_ = TAS.PROC_INST_ID_  WHERE";

		String selectThredSql = " and RES.END_TIME_ is not NULL "
				+ " and (exists (select LINK.USER_ID_ from ACT_HI_IDENTITYLINK LINK where USER_ID_ = '"
				+ userid + "' and LINK.PROC_INST_ID_ = RES.ID_))) ACTI"
				+ ",PNR_ACT_TRANSFER_OFFICE_MAIN MAIN "
				+ " where ACTI.Proc_Inst_Id_ = MAIN.PROCESS_INSTANCE_ID(+) "; 
		
		// 条件查询--start
		String selectSecondSql = "";
		String selectFourSql = "";
		if (conditionQueryModel.getProcessType() == null
				|| "".equals(conditionQueryModel.getProcessType())) {
			selectSecondSql = " (RES.PROC_DEF_ID_ like '" + processOverhaulKey
					+ ":%:%' or RES.PROC_DEF_ID_ like '" + processRemakeKey
					+ ":%:%' )";
			selectFourSql = " and (MAIN.THEMEINTERFACE = 'overhaul' or MAIN.THEMEINTERFACE = 'reform')";
		} else if ("overhaul".equals(conditionQueryModel.getProcessType()
				.trim())) {
			selectSecondSql = " RES.PROC_DEF_ID_ like '" + processOverhaulKey
					+ ":%:%'  ";
			selectFourSql = " and MAIN.THEMEINTERFACE = 'overhaul'";
		} else if ("reform".equals(conditionQueryModel.getProcessType().trim())) {
			selectSecondSql = " RES.PROC_DEF_ID_ like '" + processRemakeKey
					+ ":%:%' ";
			selectFourSql = " and  MAIN.THEMEINTERFACE = 'reform'";
		}
		selectSql = selectFirstSql + selectSecondSql + selectThredSql + selectFourSql;
		
		
		String whereSql = "";
		if (conditionQueryModel.getSendStartTime() != null
				&& !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and MAIN.SUBMIT_APPLICATION_TIME >=to_date('"
					+ conditionQueryModel.getSendStartTime()
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}
		if (conditionQueryModel.getSendEndTime() != null
				&& !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and MAIN.SUBMIT_APPLICATION_TIME <= to_date('"
					+ conditionQueryModel.getSendEndTime()
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}
		if (conditionQueryModel.getWorkNumber() != null
				&& !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and ACTI.PROC_INST_ID_ ='"
					+ conditionQueryModel.getWorkNumber().trim() + "'";
		}
		if (conditionQueryModel.getTheme() != null
				&& !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and MAIN.THEME like '%"
					+ conditionQueryModel.getTheme().trim() + "%'";
		}
//		if (status != null && !status.equals("")) {
//			whereSql += " and ST.taskDefKey = '"+status+"'";
//
//		}
		sql += selectSql + whereSql;
		
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		return Integer.parseInt(list.get(0).get("total").toString());
	}

	@Override
	public List<TaskModel> getOverhaulRemakeArchivedList(
			String processOverhaulKey, String processRemakeKey, String userid,
			ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize) {
		String sql = "select temp2.* from (select temp1.*, rownum num from (";		
		String selectFirstSql = " select ACTI.PROC_INST_ID_ as processInstanceId,MAIN.THEME AS theme,MAIN.city ,"
				+ " MAIN.country,MAIN.work_type,MAIN.workorder_type_name,MAIN.sub_type_name ,"
				+ " MAIN.key_word ,MAIN.work_order_degree,MAIN.project_amount,MAIN.INITIATOR AS initiator,"
				+ " MAIN.submit_application_time as SubmitTime,decode(ACTI.Delete_Reason_, 'delete', '已删除', '已归档')"
				+ " as statusName,ACTI.Delete_Reason_,MAIN.TASK_ASSIGNEE from "
				+ " (select RES.* from ACT_HI_PROCINST RES inner join ACT_RE_PROCDEF DEF on RES.PROC_DEF_ID_ = DEF.ID_ "
				+ " left join (select TAS.* from ACT_HI_TASKINST TAS where TAS.id_ in (select max(TAS.id_) "
				+ " from ACT_HI_TASKINST TAS group by TAS.proc_inst_id_)) TAS on RES.PROC_INST_ID_ = TAS.PROC_INST_ID_ ";
		//流程条件
		String selectSql = "";
		String selectSecondSql = "";
		String selectFourSql = "";
		if(conditionQueryModel.getProcessType()==null||"".equals(conditionQueryModel.getProcessType())||"".equals(conditionQueryModel.getProcessType())){
			selectSecondSql = " WHERE (RES.PROC_DEF_ID_ like '"+ processOverhaulKey+ ":%:%' or RES.PROC_DEF_ID_ like '"+ processRemakeKey+ ":%:%')";
			selectFourSql = " and (MAIN.THEMEINTERFACE = 'overhaul' or MAIN.THEMEINTERFACE = 'reform')";
		}else if("overhaul".equals(conditionQueryModel.getProcessType())){
			selectSecondSql = " WHERE RES.PROC_DEF_ID_ like '"+ processOverhaulKey+ ":%:%'";
			selectFourSql = " and MAIN.THEMEINTERFACE = 'overhaul'";
		}else if("reform".equals(conditionQueryModel.getProcessType())){
			selectSecondSql = " WHERE RES.PROC_DEF_ID_ like '"+ processRemakeKey+ ":%:%'";
			selectFourSql = " and MAIN.THEMEINTERFACE = 'reform'";
		}
		String selectThredSql = " and RES.END_TIME_ is not NULL and (exists (select LINK.USER_ID_ from ACT_HI_IDENTITYLINK LINK where USER_ID_ = '"
				+ userid
				+ "' and LINK.PROC_INST_ID_ = RES.ID_))) ACTI,PNR_ACT_TRANSFER_OFFICE_MAIN MAIN where ACTI.Proc_Inst_Id_ = MAIN.PROCESS_INSTANCE_ID(+)";
		String whereSql = "";
		if (conditionQueryModel.getSendStartTime() != null && !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and MAIN.SUBMIT_APPLICATION_TIME >=to_date('" + conditionQueryModel.getSendStartTime()
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}
		if (conditionQueryModel.getSendEndTime() != null && !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and MAIN.SUBMIT_APPLICATION_TIME <= to_date('" + conditionQueryModel.getSendEndTime()
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}
		if (conditionQueryModel.getWorkNumber() != null && !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and ACTI.PROC_INST_ID_ ='" + conditionQueryModel.getWorkNumber().trim() + "'";
		}
		if (conditionQueryModel.getTheme() != null && !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and MAIN.THEME like '%" + conditionQueryModel.getTheme().trim() + "%'";
		}
//		if (status != null && !status.equals("")) {
//			whereSql += " and ST.taskDefKey = '"+status+"'";
//
//		}
		selectSql = selectFirstSql+selectSecondSql+selectThredSql+selectFourSql;
		sql += selectSql + whereSql
				+ " order by ACTI.PROC_INST_ID_ asc) temp1 where rownum <="
				+ endResult * pageSize + ") temp2 where temp2.num > "
				+ firstResult * pageSize;

		System.out.println("已归档工单明细sql="+sql);
		
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
	
	
}
