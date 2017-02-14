package com.boco.activiti.partner.process.dao.hibernate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.activiti.partner.process.dao.IPnrTransferOldPrecheckJDBCDao;
import com.boco.activiti.partner.process.po.TaskModel;

public class PnrTransferOldPrecheckDaoJDBC extends JdbcDaoSupport implements
IPnrTransferOldPrecheckJDBCDao{
	
	public int getToreplyJobOfEmergencyJobCount(String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status) {
		String sql = "";
		String selectSql = "select count(*) as total from (select  RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_ WHERE RES.ASSIGNEE_ = "
				+ "?"
				+ " and D.KEY_ = 'newTransferPrecheck' and RES.Suspension_State_ = 1) t,pnr_act_transfer_office_main m,taw_system_user u where t.proc_inst_id_ = m.process_instance_id and m.task_assignee = u.userid(+) and m.state!=8 ";
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
			whereList.add(wsNum);
		}
		if (wsTitle != null && !wsTitle.equals("")) {
			whereSql += " and   instr(m.theme,?)>0";
			whereList.add(wsTitle);
		}
		if (status != null && !status.equals("")) {
			//whereSql += " and m.state = '" + status + "'";
			whereSql += " and t.task_def_key_ =?";
			whereList.add(status);

		}
		sql = selectSql + whereSql;

		Object args[]=whereList.toArray();
		
		int size = this.getJdbcTemplate().queryForInt(sql,args);
		return size;

	}
	
	public List<TaskModel> getToreplyJobOfEmergencyJobList(String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status, int firstResult, int endResult,
			int pageSize) {
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		String selectSql = "select t.id_ as TaskId,m.initiator as Initiator,m.one_initiator as OneInitiator,m.process_instance_id as ProcessInstanceId,m.send_time as SendTime,m.theme as Theme,m.task_assignee,m.state as State,m.submit_application_time as SubmitTime,u.deptid as DeptId from (select  RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_ WHERE RES.ASSIGNEE_ = "
				+ "?"
				+ " and D.KEY_ = 'newTransferPrecheck' and RES.Suspension_State_ = 1) t,pnr_act_transfer_office_main m,taw_system_user u where t.proc_inst_id_ = m.process_instance_id and m.task_assignee = u.userid(+) and m.state!=8";
		
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
			whereList.add(wsNum);
		}
		if (wsTitle != null && !wsTitle.equals("")) {
			whereSql += " and   instr(m.theme,?)>0";
			whereList.add(wsTitle);
		}
		if (status != null && !status.equals("")) {
			//whereSql += " and m.state = '" + status + "'";
			whereSql += " and t.task_def_key_ =?";
			whereList.add(status);

		}
		sql += selectSql + whereSql
				+ " order by t.create_time_ desc) temp1 where rownum <=?) temp2 where temp2.num >?";
		
		whereList.add(endResult * pageSize);
		whereList.add(firstResult * pageSize);
		
		Object values[]=whereList.toArray();
		
		System.out.println("sql====++++"+sql);
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
				" and sr.deptid=( select a.parentareaid from taw_system_user u ,taw_system_dept d ,taw_system_area a" +
				"  where u.deptid=d.deptid" +
				"  and d.areaid = a.areaid" +
				" and u.userid='"+countryCSJ+"')";
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
	
	
	
	
	
}
