package com.boco.activiti.partner.process.dao.hibernate;

import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.activiti.partner.process.dao.INetResInspectJDBCDao;
import com.boco.activiti.partner.process.model.NetResInspectDraft;
import com.boco.activiti.partner.process.model.NetResInspectTurnToSendModel;
import com.boco.activiti.partner.process.model.OptionUtil;
import com.boco.activiti.partner.process.model.PnrAndroidPhotoFile;
import com.boco.activiti.partner.process.po.ConditionQueryModel;
import com.boco.activiti.partner.process.po.NetResInspectModel;
import com.boco.activiti.partner.process.po.RoomDemolitionModel;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.eoms.commons.accessories.webapp.form.TawCommonsAccessoriesForm;

public class NetResInspectDaoJDBC extends JdbcDaoSupport implements
	INetResInspectJDBCDao {

	private final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //日期格式化
	
	@Override
	public int getTransferNewPrecheckCount(String userid, String flag,String processKey,
			ConditionQueryModel conditionQueryModel) {
		String sql = "";
		String selectSql =	"select count(m.id) as total" +
							"           from pnr_act_netresinspect_main m" + 
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
			whereSql += " and to_char(m.reported_date,'yyyy-mm-dd hh24:mi:ss') >=?";
			whereList.add(conditionQueryModel.getSendStartTime());
		}
		if (conditionQueryModel.getSendEndTime() != null && !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and to_char(m.reported_date,'yyyy-mm-dd hh24:mi:ss') <=?";
			whereList.add(conditionQueryModel.getSendEndTime());
		}
		if (conditionQueryModel.getCity() != null && !conditionQueryModel.getCity().equals("")) {
			whereSql += " and m.city =?";
			whereList.add(conditionQueryModel.getCity());
		}
		if (conditionQueryModel.getWorkNumber() != null && !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and m.process_instance_id =?";
			whereList.add(conditionQueryModel.getWorkNumber());
		}
		if (conditionQueryModel.getTheme() != null && !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and instr(m.theme,?)>0 ";
			whereList.add(conditionQueryModel.getTheme());
		}
		if (conditionQueryModel.getCountry() != null && !conditionQueryModel.getCountry().equals("")) {
			whereSql += " and m.county = ?";
			whereList.add(conditionQueryModel.getCountry());
			
		}
		if (conditionQueryModel.getResourceType() != null && !conditionQueryModel.getResourceType().equals("")) {
			whereSql += " and m.resource_type =?";
			whereList.add(conditionQueryModel.getResourceType());
		}
		if (conditionQueryModel.getQuestionType() != null && !conditionQueryModel.getQuestionType().equals("")) {
			whereSql += " and m.question_type =?";
			whereList.add(conditionQueryModel.getQuestionType());
		}
		if (conditionQueryModel.getSpecialty() != null && !conditionQueryModel.getSpecialty().equals("")) {
			whereSql += " and m.specialty =?";
			whereList.add(conditionQueryModel.getSpecialty());
		}

		sql = selectSql + whereSql;
		
		Object args[]=whereList.toArray();
		
		System.out.println("--------------网络资源巡检众筹 待回复列表总条数sql="+sql);


		int size = this.getJdbcTemplate().queryForInt(sql,args);
		return size;
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NetResInspectModel> getTransferNewPrecheckList(String userid, String flag,
			String processKey, ConditionQueryModel conditionQueryModel,
			int firstResult, int endResult, int pageSize) {

		//String sql = "";
		String sql ="select temp2.* from (select temp1.*, rownum num from (";
		String selectSql =	"select m.task_id," +
							"       m.task_def_key," + 
							"       m.task_def_key_name," + 
							"       m.assignee," + 
							"       m.process_instance_id," + 
							"       m.theme," + 
							"       m.reported_date," + 
							"       m.question_type," + 
							"       m.resource_type," + 
							"       m.city," + 
							"       m.county," + 
							"       m.specialty," + 
							"       nvl(m.auto_send_process,'-') as auto_send_process," + 
							"       decode(m.auto_send_process,0,'抢修','1','本地网','-') as sub_process_name," +
							"       nvl(m.sub_process_instance_id,'-') as sub_process_instance_id"+
							"  from pnr_act_netresinspect_main m "+
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
			whereSql += " and to_char(m.reported_date,'yyyy-mm-dd hh24:mi:ss') >=?";
			whereList.add(conditionQueryModel.getSendStartTime());
		}
		if (conditionQueryModel.getSendEndTime() != null && !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and to_char(m.reported_date,'yyyy-mm-dd hh24:mi:ss') <=?";
			whereList.add(conditionQueryModel.getSendEndTime());
		}
		if (conditionQueryModel.getCity() != null && !conditionQueryModel.getCity().equals("")) {
			whereSql += " and m.city =?";
			whereList.add(conditionQueryModel.getCity());
		}
		if (conditionQueryModel.getWorkNumber() != null && !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and m.process_instance_id =?";
			whereList.add(conditionQueryModel.getWorkNumber());
		}
		if (conditionQueryModel.getTheme() != null && !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and instr(m.theme,?)>0 ";
			whereList.add(conditionQueryModel.getTheme());
		}
		if (conditionQueryModel.getCountry() != null && !conditionQueryModel.getCountry().equals("")) {
			whereSql += " and m.county = ?";
			whereList.add(conditionQueryModel.getCountry());
			
		}
		if (conditionQueryModel.getResourceType() != null && !conditionQueryModel.getResourceType().equals("")) {
			whereSql += " and m.resource_type =?";
			whereList.add(conditionQueryModel.getResourceType());
		}
		if (conditionQueryModel.getQuestionType() != null && !conditionQueryModel.getQuestionType().equals("")) {
			whereSql += " and m.question_type =?";
			whereList.add(conditionQueryModel.getQuestionType());
		}
		if (conditionQueryModel.getSpecialty() != null && !conditionQueryModel.getSpecialty().equals("")) {
			whereSql += " and m.specialty =?";
			whereList.add(conditionQueryModel.getSpecialty());
		}
		
		sql += selectSql + whereSql
				+ " order by m.reported_date desc) temp1 where rownum <=?) temp2 where temp2.num >?";
		
		whereList.add(endResult * pageSize);
		whereList.add(firstResult * pageSize);
		
		Object values[]=whereList.toArray();
		
		System.out.println("------------------网络资源巡检众筹 待回复列表="+sql);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<NetResInspectModel> r = new ArrayList<NetResInspectModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,values);
		for (Map map : list) {
			NetResInspectModel model = new NetResInspectModel();
			//工单号
			if(map.get("process_instance_id")!=null && !"".equals(map.get("process_instance_id"))){
				
				model.setProcessInstanceId(map.get("process_instance_id").toString());
			}
			//工单名称
			if(map.get("theme")!=null && !"".equals(map.get("theme"))){
				
				model.setTheme(map.get("theme").toString());
			}
			//派单时间
			if (map.get("reported_date") != null) {
				if (!"".equals(map.get("reported_date"))) {
					try {
						model.setReportedDate(format.parse(map.get("reported_date")
								.toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				
			}
			//问题类型
			if(map.get("question_type")!=null && !"".equals(map.get("question_type"))){
				
				model.setQuestionType(map.get("question_type").toString());
			}
			//资源类型
			if(map.get("resource_type")!=null && !"".equals(map.get("resource_type"))){
				
				model.setResourceType(map.get("resource_type").toString());
			}
			//地市
			if(map.get("city")!=null && !"".equals(map.get("city"))){
				
				model.setCity(map.get("city").toString());
			}
			//区县
			if(map.get("county")!=null && !"".equals(map.get("county"))){
				
				model.setCounty(map.get("county").toString());
			}
			//专业
			if(map.get("specialty")!=null && !"".equals(map.get("specialty"))){
				
				model.setSpecialty(map.get("specialty").toString());
			}
			//环节
			if(map.get("task_def_key_name")!=null && !"".equals(map.get("task_def_key_name"))){
				
				model.setTaskDefKeyName(map.get("task_def_key_name").toString());
			}
			//任务id
			if(map.get("task_id")!=null && !"".equals(map.get("task_id"))){
				
				model.setTaskId(map.get("task_id").toString());
			}
			
			//派发流程标识
			if(map.get("auto_send_process")!=null && !"".equals(map.get("auto_send_process"))){
				
				model.setAutoSendProcess(map.get("auto_send_process").toString());
			}
			//子流程名
			if(map.get("sub_process_name")!=null && !"".equals(map.get("sub_process_name"))){
				
				model.setSubProcessName(map.get("sub_process_name").toString());
			}
			//子流程id号
			if(map.get("sub_process_instance_id")!=null && !"".equals(map.get("sub_process_instance_id"))){
				
				model.setSubProcessInstanceId(map.get("sub_process_instance_id").toString());
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
		String sql="";
		String selectSql="		  select count(processInstanceId) as total		"+
						 "		from (select to_char(ACTI.Proc_Inst_Id_) as processInstanceId,		"+
						 "		to_char(ACTI.task_def_key_) as taskDefKey,	    "+
						 "		to_char(ACTI.name_) as statusName,          	"+
						 "		decode(MAIN.auto_send_process,0,'抢修','1','本地网','-') as sub_process_name,          	"+
						 "		                  MAIN.*				    	"+
						 "		 from (select k.proc_inst_id_, k.name_, k.task_def_key_		"+
						 "		 from ACT_RU_TASK k         	                "+
						 "		 inner join ACT_RE_PROCDEF D    				"+
						 "		 on k.PROC_DEF_ID_ = D.ID_ 						"+
						 "		 where D.KEY_ = '"+processDefinitionKey+"'		"+
						 "		              and (exists						"+
						 "		       (select LINK.USER_ID_ 					"+
					     "			    from ACT_HI_IDENTITYLINK LINK			"+
						 "		              where USER_ID_ = '"+userId+"'		"+
						 "		     and LINK.PROC_INST_ID_ = k.proc_inst_id_))	"+
						 "		          and (exists (select kst.assignee_		"+
						 "		          from act_hi_taskinst kst      		"+
						 "		 where kst.assignee_ =  '"+userId+"'         	"+
						 "		  and kst.proc_inst_id_ = k.proc_inst_id_		"+
						 "		    and kst.end_time_ is not null))) ACTI,		"+
						 "		       pnr_act_netresinspect_main MAIN			"+
						 "		        WHERE ACTI.Proc_Inst_Id_ = MAIN.PROCESS_INSTANCE_ID(+)	"+
						 "		      union						                                "+
						 "		       select MAIN_1.Process_Instance_Id as processInstanceId,	"+
						 "		           'archive' as taskDefKey,								"+
						 "		        '已归档' as statusName,									"+
						 "		        decode(MAIN_1.auto_send_process,0,'抢修','1','本地网','-') as sub_process_name,"+
						 "		         MAIN_1.*												"+
						 "		       from pnr_act_netresinspect_main MAIN_1			        "+
						 "		         where MAIN_1.State = '5') ST							"+
						 "		       WHERE 1 = 1										        ";
		ArrayList whereList=new ArrayList();
		
		String whereSql=" ";
		if (conditionQueryModel.getSendStartTime() != null && !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and to_char(ST.reported_date,'yyyy-mm-dd hh24:mi:ss') >=?";
			whereList.add(conditionQueryModel.getSendStartTime());
		}
		if (conditionQueryModel.getSendEndTime() != null && !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and to_char(ST.reported_date,'yyyy-mm-dd hh24:mi:ss') <=?";
			whereList.add(conditionQueryModel.getSendEndTime());
		}
		if (conditionQueryModel.getCity() != null && !conditionQueryModel.getCity().equals("")) {
			whereSql += " and ST.city =?";
			whereList.add(conditionQueryModel.getCity());
		}
		if (conditionQueryModel.getWorkNumber() != null && !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and ST.process_instance_id =?";
			whereList.add(conditionQueryModel.getWorkNumber());
		}
		if (conditionQueryModel.getTheme() != null && !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and instr(ST.theme,?)>0 ";
			whereList.add(conditionQueryModel.getTheme());
		}
		if (conditionQueryModel.getCountry() != null && !conditionQueryModel.getCountry().equals("")) {
			whereSql += " and ST.county = ?";
			whereList.add(conditionQueryModel.getCountry());
			
		}
		if (conditionQueryModel.getResourceType() != null && !conditionQueryModel.getResourceType().equals("")) {
			whereSql += " and ST.resource_type =?";
			whereList.add(conditionQueryModel.getResourceType());
		}
		if (conditionQueryModel.getQuestionType() != null && !conditionQueryModel.getQuestionType().equals("")) {
			whereSql += " and ST.question_type =?";
			whereList.add(conditionQueryModel.getQuestionType());
		}
		if (conditionQueryModel.getSpecialty() != null && !conditionQueryModel.getSpecialty().equals("")) {
			whereSql += " and ST.specialty =?";
			whereList.add(conditionQueryModel.getSpecialty());
		}
		if (conditionQueryModel.getStatus() != null&& !conditionQueryModel.getStatus().equals("") && !conditionQueryModel.getStatus().equals("0")) {
			whereSql += " and ST.taskdefkey =?";
			whereList.add(conditionQueryModel.getStatus());
		}
		sql += selectSql + whereSql;
		
		Object args[]=whereList.toArray();

		System.out.println("--------------巡检众筹已处理工单条数sql=" + sql);

		int size = this.getJdbcTemplate().queryForInt(sql,args);
		return size;
	}
	
	/**
	 * 已处理工单明细
	 */
	public List<NetResInspectModel> getHaveProcessList(String processDefinitionKey,
			String userId,ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize){
		String sql="select temp2.* from (select temp1.*, rownum num from ( ";
		String selectSql="		  select *		    									"+
		 "		  from (select to_char(ACTI.Proc_Inst_Id_) as processInstanceId,		"+
		 "		   to_char(ACTI.task_def_key_) as taskDefKey,							"+
		 "		   to_char(ACTI.name_) as statusName,									"+
		 "		   decode(MAIN.auto_send_process,0,'抢修','1','本地网','-') as sub_process_name,								"+
		 "		    MAIN.*																"+
		 "		    from (select k.proc_inst_id_,										"+
		 "		                  k.name_,												"+
		 "		               k.task_def_key_											"+
		 "		               from ACT_RU_TASK k										"+
		 "		               inner join ACT_RE_PROCDEF D								"+
		 "		                 on k.PROC_DEF_ID_ = D.ID_								"+
		 "		                where D.KEY_ ='"+processDefinitionKey+"'				"+
	     "			            and (exists (select LINK.USER_ID_						"+
		 "		                from ACT_HI_IDENTITYLINK LINK							"+
		 "		         where USER_ID_ = '"+userId+"'						"+
		 "		               and LINK.PROC_INST_ID_ =						"+
		 "		                  k.proc_inst_id_))							"+
		 "		                  and (exists								"+
		 "		                 (select kst.assignee_					    "+
		 "		          from act_hi_taskinst kst							"+
		 "		            where kst.assignee_ ='"+userId+"'				"+
		 "		           and kst.proc_inst_id_ =							"+
		 "		        k.proc_inst_id_										"+
		 "		        and kst.end_time_ is not null))) ACTI,				"+
		 "		          pnr_act_netresinspect_main MAIN					"+
		 "		          WHERE ACTI.Proc_Inst_Id_ =						"+
		 "		        MAIN.PROCESS_INSTANCE_ID(+)							"+
		 "		       union												"+
		 "		          select MAIN_1.Process_Instance_Id as processInstanceId,			"+
		 "		          'archive' as taskDefKey,											"+
		 "		  '已归档' as statusName, 									"+
		 "		  decode(MAIN_1.auto_send_process,0,'抢修','1','本地网','-') as sub_process_name,"+
		 "		  MAIN_1.* 													"+
		 "		   from pnr_act_netresinspect_main MAIN_1 					"+
		 "		   where MAIN_1.State = '5') ST 							"+
		 "		    WHERE 1 = 1 ";
		
		ArrayList whereList=new ArrayList();
		
		String whereSql=" ";
		if (conditionQueryModel.getSendStartTime() != null && !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and to_char(ST.reported_date,'yyyy-mm-dd hh24:mi:ss') >=?";
			whereList.add(conditionQueryModel.getSendStartTime());
		}
		if (conditionQueryModel.getSendEndTime() != null && !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and to_char(ST.reported_date,'yyyy-mm-dd hh24:mi:ss') <=?";
			whereList.add(conditionQueryModel.getSendEndTime());
		}
		if (conditionQueryModel.getCity() != null && !conditionQueryModel.getCity().equals("")) {
			whereSql += " and ST.city =?";
			whereList.add(conditionQueryModel.getCity());
		}
		if (conditionQueryModel.getWorkNumber() != null && !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and ST.process_instance_id =?";
			whereList.add(conditionQueryModel.getWorkNumber());
		}
		if (conditionQueryModel.getTheme() != null && !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and instr(ST.theme,?)>0 ";
			whereList.add(conditionQueryModel.getTheme());
		}
		if (conditionQueryModel.getCountry() != null && !conditionQueryModel.getCountry().equals("")) {
			whereSql += " and ST.county = ?";
			whereList.add(conditionQueryModel.getCountry());
			
		}
		if (conditionQueryModel.getResourceType() != null && !conditionQueryModel.getResourceType().equals("")) {
			whereSql += " and ST.resource_type =?";
			whereList.add(conditionQueryModel.getResourceType());
		}
		if (conditionQueryModel.getQuestionType() != null && !conditionQueryModel.getQuestionType().equals("")) {
			whereSql += " and ST.question_type =?";
			whereList.add(conditionQueryModel.getQuestionType());
		}
		if (conditionQueryModel.getSpecialty() != null && !conditionQueryModel.getSpecialty().equals("")) {
			whereSql += " and ST.specialty =?";
			whereList.add(conditionQueryModel.getSpecialty());
		}
		if (conditionQueryModel.getStatus() != null&& !conditionQueryModel.getStatus().equals("") && !conditionQueryModel.getStatus().equals("0")) {
			whereSql += " and ST.taskdefkey =?";
			whereList.add(conditionQueryModel.getStatus());
		}
		
		sql += selectSql + whereSql
		+ " order by ST.REPORTED_DATE desc)temp1 where rownum <= ?) temp2 where temp2.num >?";
		
		whereList.add(endResult * pageSize);
		whereList.add(firstResult * pageSize);
		
		Object values[]=whereList.toArray();
		System.out.println("------------------巡检众筹已处理工单查询列表："+sql);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<NetResInspectModel> r = new ArrayList<NetResInspectModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,values);
		for (Map map : list) {
			NetResInspectModel model = new NetResInspectModel();
			//工单号
			if(map.get("process_instance_id")!=null && !"".equals(map.get("process_instance_id"))){
				
				model.setProcessInstanceId(map.get("process_instance_id").toString());
			}
			//工单名称
			if(map.get("theme")!=null && !"".equals(map.get("theme"))){
				
				model.setTheme(map.get("theme").toString());
			}
			//派单时间
			if (map.get("reported_date") != null) {
				if (!"".equals(map.get("reported_date"))) {
					try {
						model.setReportedDate(format.parse(map.get("reported_date")
								.toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				
			}
			//问题类型
			if(map.get("question_type")!=null && !"".equals(map.get("question_type"))){
				
				model.setQuestionType(map.get("question_type").toString());
			}
			//资源类型
			if(map.get("resource_type")!=null && !"".equals(map.get("resource_type"))){
				
				model.setResourceType(map.get("resource_type").toString());
			}
			//地市
			if(map.get("city")!=null && !"".equals(map.get("city"))){
				
				model.setCity(map.get("city").toString());
			}
			//区县
			if(map.get("county")!=null && !"".equals(map.get("county"))){
				
				model.setCounty(map.get("county").toString());
			}
			//专业
			if(map.get("specialty")!=null && !"".equals(map.get("specialty"))){
				
				model.setSpecialty(map.get("specialty").toString());
			}
			//环节
			if(map.get("task_def_key_name")!=null && !"".equals(map.get("task_def_key_name"))){
				
				model.setTaskDefKeyName(map.get("task_def_key_name").toString());
			}
			//任务id
			if(map.get("task_id")!=null && !"".equals(map.get("task_id"))){
				
				model.setTaskId(map.get("task_id").toString());
			}
			
			//派发流程标识
			if(map.get("auto_send_process")!=null && !"".equals(map.get("auto_send_process"))){
				
				model.setAutoSendProcess(map.get("auto_send_process").toString());
			}
			//子流程名
			if(map.get("sub_process_name")!=null && !"".equals(map.get("sub_process_name"))){
				
				model.setSubProcessName(map.get("sub_process_name").toString());
			}
			//子流程id号
			if(map.get("sub_process_instance_id")!=null && !"".equals(map.get("sub_process_instance_id"))){
				
				model.setSubProcessInstanceId(map.get("sub_process_instance_id").toString());
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
	public int getByCreatingWorkOrderCount(String processDefinitionKey, String userId,ConditionQueryModel conditionQueryModel){
		String sql="";
		String selectSql="  select count(MAIN.Process_Instance_Id) as total  "+
			"     from pnr_act_netresinspect_main MAIN                       "+
			"     WHERE MAIN.State <> 2                                      "+
			"    and MAIN.Reported_User_Id='"+userId+"' ";
		ArrayList whereList=new ArrayList();
		String whereSql = " ";
		if (conditionQueryModel.getSendStartTime() != null && !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and to_char(MAIN.reported_date,'yyyy-mm-dd hh24:mi:ss') >=?";
			whereList.add(conditionQueryModel.getSendStartTime());
		}
		if (conditionQueryModel.getSendEndTime() != null && !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and to_char(MAIN.reported_date,'yyyy-mm-dd hh24:mi:ss') <=?";
			whereList.add(conditionQueryModel.getSendEndTime());
		}
		if (conditionQueryModel.getCity() != null && !conditionQueryModel.getCity().equals("")) {
			whereSql += " and MAIN.city =?";
			whereList.add(conditionQueryModel.getCity());
		}
		if (conditionQueryModel.getWorkNumber() != null && !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and MAIN.process_instance_id =?";
			whereList.add(conditionQueryModel.getWorkNumber());
		}
		if (conditionQueryModel.getTheme() != null && !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and instr(MAIN.theme,?)>0 ";
			whereList.add(conditionQueryModel.getTheme());
		}
		if (conditionQueryModel.getCountry() != null && !conditionQueryModel.getCountry().equals("")) {
			whereSql += " and MAIN.county = ?";
			whereList.add(conditionQueryModel.getCountry());
			
		}
		if (conditionQueryModel.getResourceType() != null && !conditionQueryModel.getResourceType().equals("")) {
			whereSql += " and MAIN.resource_type =?";
			whereList.add(conditionQueryModel.getResourceType());
		}
		if (conditionQueryModel.getQuestionType() != null && !conditionQueryModel.getQuestionType().equals("")) {
			whereSql += " and MAIN.question_type =?";
			whereList.add(conditionQueryModel.getQuestionType());
		}
		if (conditionQueryModel.getSpecialty() != null && !conditionQueryModel.getSpecialty().equals("")) {
			whereSql += " and MAIN.specialty =?";
			whereList.add(conditionQueryModel.getSpecialty());
		}
		if (conditionQueryModel.getStatus() != null&& !conditionQueryModel.getStatus().equals("") && !conditionQueryModel.getStatus().equals("0")) {
			whereSql += " and MAIN.Task_Def_Key =?";
			whereList.add(conditionQueryModel.getStatus());
		}
		sql += selectSql + whereSql;
		
		Object args[]=whereList.toArray();

		System.out.println("--------------巡检众筹 由我创建的工单条数sql=" + sql);

		int size = this.getJdbcTemplate().queryForInt(sql,args);
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
	public List<NetResInspectModel> getByCreatingWorkOrderList(String processDefinitionKey,
			String userId, ConditionQueryModel conditionQueryModel, int firstResult,
			int endResult, int pageSize){
		String sql="select temp2.* from (select temp1.*, rownum num from ( ";
		String selectSql="		select         m.*,			"+
			"    decode(m.auto_send_process,0,'抢修','1','本地网','-') as sub_process_name   "+
			"    from pnr_act_netresinspect_main m   "+
			"	 where m.state<>2   and m.reported_user_id='"+userId+"'   ";
		
		ArrayList whereList=new ArrayList();
		
		String whereSql=" ";
		if (conditionQueryModel.getSendStartTime() != null && !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and to_char(m.reported_date,'yyyy-mm-dd hh24:mi:ss') >=?";
			whereList.add(conditionQueryModel.getSendStartTime());
		}
		if (conditionQueryModel.getSendEndTime() != null && !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and to_char(m.reported_date,'yyyy-mm-dd hh24:mi:ss') <=?";
			whereList.add(conditionQueryModel.getSendEndTime());
		}
		if (conditionQueryModel.getCity() != null && !conditionQueryModel.getCity().equals("")) {
			whereSql += " and m.city =?";
			whereList.add(conditionQueryModel.getCity());
		}
		if (conditionQueryModel.getWorkNumber() != null && !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and m.process_instance_id =?";
			whereList.add(conditionQueryModel.getWorkNumber());
		}
		if (conditionQueryModel.getTheme() != null && !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and instr(m.theme,?)>0 ";
			whereList.add(conditionQueryModel.getTheme());
		}
		if (conditionQueryModel.getCountry() != null && !conditionQueryModel.getCountry().equals("")) {
			whereSql += " and m.county = ?";
			whereList.add(conditionQueryModel.getCountry());
			
		}
		if (conditionQueryModel.getResourceType() != null && !conditionQueryModel.getResourceType().equals("")) {
			whereSql += " and m.resource_type =?";
			whereList.add(conditionQueryModel.getResourceType());
		}
		if (conditionQueryModel.getQuestionType() != null && !conditionQueryModel.getQuestionType().equals("")) {
			whereSql += " and m.question_type =?";
			whereList.add(conditionQueryModel.getQuestionType());
		}
		if (conditionQueryModel.getSpecialty() != null && !conditionQueryModel.getSpecialty().equals("")) {
			whereSql += " and m.specialty =?";
			whereList.add(conditionQueryModel.getSpecialty());
		}
		if (conditionQueryModel.getStatus() != null&& !conditionQueryModel.getStatus().equals("") && !conditionQueryModel.getStatus().equals("0")) {
			whereSql += " and m.Task_Def_Key =?";
			whereList.add(conditionQueryModel.getStatus());
		}
		
		sql += selectSql + whereSql
		+ " order by m.REPORTED_DATE desc)temp1 where rownum <= ?) temp2 where temp2.num >?";
		
		whereList.add(endResult * pageSize);
		whereList.add(firstResult * pageSize);
		
		Object values[]=whereList.toArray();
		System.out.println("------------------巡检众筹由我创建的工单列表："+sql);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<NetResInspectModel> r = new ArrayList<NetResInspectModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,values);
		for (Map map : list) {
			NetResInspectModel model = new NetResInspectModel();
			//工单号
			if(map.get("process_instance_id")!=null && !"".equals(map.get("process_instance_id"))){
				
				model.setProcessInstanceId(map.get("process_instance_id").toString());
			}
			//工单名称
			if(map.get("theme")!=null && !"".equals(map.get("theme"))){
				
				model.setTheme(map.get("theme").toString());
			}
			//派单时间
			if (map.get("reported_date") != null) {
				if (!"".equals(map.get("reported_date"))) {
					try {
						model.setReportedDate(format.parse(map.get("reported_date")
								.toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				
			}
			//问题类型
			if(map.get("question_type")!=null && !"".equals(map.get("question_type"))){
				
				model.setQuestionType(map.get("question_type").toString());
			}
			//资源类型
			if(map.get("resource_type")!=null && !"".equals(map.get("resource_type"))){
				
				model.setResourceType(map.get("resource_type").toString());
			}
			//地市
			if(map.get("city")!=null && !"".equals(map.get("city"))){
				
				model.setCity(map.get("city").toString());
			}
			//区县
			if(map.get("county")!=null && !"".equals(map.get("county"))){
				
				model.setCounty(map.get("county").toString());
			}
			//专业
			if(map.get("specialty")!=null && !"".equals(map.get("specialty"))){
				
				model.setSpecialty(map.get("specialty").toString());
			}
			//环节
			if(map.get("task_def_key_name")!=null && !"".equals(map.get("task_def_key_name"))){
				
				model.setTaskDefKeyName(map.get("task_def_key_name").toString());
			}
			//任务id
			if(map.get("task_id")!=null && !"".equals(map.get("task_id"))){
				
				model.setTaskId(map.get("task_id").toString());
			}
			
			//派发流程标识
			if(map.get("auto_send_process")!=null && !"".equals(map.get("auto_send_process"))){
				
				model.setAutoSendProcess(map.get("auto_send_process").toString());
			}
			//子流程名
			if(map.get("sub_process_name")!=null && !"".equals(map.get("sub_process_name"))){
				
				model.setSubProcessName(map.get("sub_process_name").toString());
			}
			//子流程id号
			if(map.get("sub_process_instance_id")!=null && !"".equals(map.get("sub_process_instance_id"))){
				
				model.setSubProcessInstanceId(map.get("sub_process_instance_id").toString());
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
		System.out.println("----------新建工单图片查看sql="+sql);
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
		String selectSql = "select ACTI.PROC_INST_ID_ as processInstanceId,MAIN.THEME AS theme,"+
			"  MAIN.REPORTED_DATE,"+
			"  MAIN.ARCHIVE_TIME,"+
			"  MAIN.REPORTED_USER_ID,"+
			"  MAIN.REPORTED_DEPT_ID,"+
			"  MAIN.REPORTED_PHONE_NUM,"+
			"  MAIN.SPECIALTY,"+
			"  MAIN.RESOURCE_TYPE,"+
			"  MAIN.QUESTION_TYPE,"+
			"  MAIN.CITY,"+
			"  MAIN.COUNTY,"+
			"  MAIN.TOWN,"+
			"  MAIN.REPORTED_DESCRIBE,"+
			"  MAIN.LOCATED_ADDRESS,"+
			"  MAIN.RESOURCE_NAME,"+
			"  MAIN.SUB_PROCESS_INSTANCE_ID,"+
			"decode(ACTI.Delete_Reason_, 'delete', '已删除', '已归档') as statusName,"+
			" ACTI.Delete_Reason_ from (select RES.*,decode(RES.Delete_Reason_,'delete','delete','archived') as tempStatusName "+
			" from ACT_HI_PROCINST RES inner join ACT_RE_PROCDEF DEF on RES.PROC_DEF_ID_ = DEF.ID_ "+
			" left join (select TAS.* from ACT_HI_TASKINST TAS where TAS.id_ in (select max(TAS.id_)"+
			"  from ACT_HI_TASKINST TAS group by TAS.proc_inst_id_)) TAS on RES.PROC_INST_ID_ = TAS.PROC_INST_ID_ "+
			" WHERE RES.PROC_DEF_ID_ like '"+processDefinitionKey+":%:%' and RES.END_TIME_ is not NULL "+
			" and (exists (select LINK.USER_ID_ from ACT_HI_IDENTITYLINK LINK where USER_ID_ = '"+userId+"' and LINK.PROC_INST_ID_ = RES.ID_))) ACTI,"+
			" PNR_ACT_NETRESINSPECT_MAIN MAIN where ACTI.Proc_Inst_Id_ = MAIN.PROCESS_INSTANCE_ID(+) ";
		String whereSql = "";
		if (sendStartTime != null && !sendStartTime.equals("")) {
			whereSql += " and MAIN.REPORTED_DATE >=to_date('" + sendStartTime
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}
		if (sendEndTime != null && !sendEndTime.equals("")) {
			whereSql += " and MAIN.ARCHIVE_TIME <= to_date('" + sendEndTime
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
				+ " order by MAIN.REPORTED_DATE desc) temp1 where rownum <="
				+ endResult * pageSize + ") temp2 where temp2.num > "
				+ firstResult * pageSize;

		System.out.println("--------众筹 已归档工单明细sql="+sql);
		
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
			if (map.get("REPORTED_DATE") != null) {
				if (!"".equals(map.get("REPORTED_DATE"))) {
					try {
						model.setSendTime(format.parse(map.get("REPORTED_DATE").toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

			}
			if (map.get("ARCHIVE_TIME") != null) {
				if (!"".equals(map.get("ARCHIVE_TIME"))) {
					try {
						model.setEndTime(format.parse(map.get("ARCHIVE_TIME").toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

			}
			if(map.get("statusName")!=null&&!"".equals(map.get("statusName"))){
				model.setStatusName(map.get("statusName").toString());
			}
            if(map.get("CITY")!=null && !"".equals(map.get("CITY"))){
				
				model.setCity(map.get("CITY").toString());
			}
			if(map.get("COUNTY")!=null && !"".equals(map.get("COUNTY"))){
				model.setCountry(map.get("COUNTY").toString());
			}
			if(map.get("QUESTION_TYPE")!=null && !"".equals(map.get("QUESTION_TYPE"))){
				
				model.setQuestionType(map.get("QUESTION_TYPE").toString());
			}
			if(map.get("RESOURCE_TYPE")!=null && !"".equals(map.get("RESOURCE_TYPE"))){
				model.setResourceType(map.get("RESOURCE_TYPE").toString());
			}
			
			if(map.get("SPECIALTY")!=null && !"".equals(map.get("SPECIALTY"))){
				model.setSpecialty(map.get("SPECIALTY").toString());
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
		String selectSql = "select count(ACTI.PROC_INST_ID_ ) as total from (select RES.*,decode(RES.Delete_Reason_,'delete','delete','archived') as tempStatusName from ACT_HI_PROCINST RES inner join ACT_RE_PROCDEF DEF on RES.PROC_DEF_ID_ = DEF.ID_ left join (select TAS.* from ACT_HI_TASKINST TAS where TAS.id_ in (select max(TAS.id_) from ACT_HI_TASKINST TAS group by TAS.proc_inst_id_)) TAS on RES.PROC_INST_ID_ = TAS.PROC_INST_ID_ WHERE RES.PROC_DEF_ID_ like '"+processDefinitionKey+":%:%' and RES.END_TIME_ is not NULL and (exists (select LINK.USER_ID_ from ACT_HI_IDENTITYLINK LINK where USER_ID_ = '"+userId+"' and LINK.PROC_INST_ID_ = RES.ID_))) ACTI,pnr_act_netresinspect_main MAIN where ACTI.Proc_Inst_Id_ = MAIN.PROCESS_INSTANCE_ID(+) ";
		String whereSql = "";
		if (sendStartTime != null && !sendStartTime.equals("")) {
			whereSql += " and MAIN.REPORTED_DATE >=to_date('" + sendStartTime
					+ "','yyyy-mm-dd hh24:mi:ss')";
		}
		if (sendEndTime != null && !sendEndTime.equals("")) {
			whereSql += " and MAIN.ARCHIVE_TIME <= to_date('" + sendEndTime
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
		
		System.out.println("--------众筹流程 已归档工单条数sql="+sql);
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
	public List<NetResInspectModel> getQueryWorkOrderList(String areaid, String flag,
			String processKey, ConditionQueryModel conditionQueryModel,
			int firstResult, int endResult, int pageSize) {

		String sql="select temp2.* from (select temp1.*, rownum num from ( ";
		String selectSql="		  select *		    			"+
		 "		  from (select m.*,	decode(m.auto_send_process,0,'抢修','1','本地网','-') as sub_process_name,							"+
		 "		               case								"+
		 "		                 when m.state = '3' then		"+
		 "		                  'waitOrder'					"+
		 "		                 else							"+
		 "		                  to_char(t.task_def_key_)		"+
		 "		               end as TaskDefKey,				"+
		 "		               case								"+
		 "		                 when m.state = '3' then		"+
		 "		                  '待办'							"+
		 "		                 else							"+
	     "			             to_char(t.name_)				"+
		 "		               end as StatusName				"+
		 "		          from pnr_act_netresinspect_main  m,		"+
		 "		               (select RES.*			"+
		 "		                  from ACT_RU_TASK RES			"+
		 "		                 inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_	"+
		 "		                 WHERE D.KEY_ = 'netResInspect') t 						    "+
		 "		         where m.process_instance_id = t.proc_inst_id_						"+
		 "		           and m.state != '1'												"+
		 "		           and m.state != '5'												"+
		 "		        union	all															"+
		 "		        select m.*,decode(m.auto_send_process,0,'抢修','1','本地网','-') as sub_process_name, 'archive' as TaskDefKey, '已归档' as StatusName			"+
		 "		          from pnr_act_netresinspect_main m										"+
		 "		         where m.state = '5'												"+
		 "		        union	all															"+
		 "		        select m.*,decode(m.auto_send_process,0,'抢修','1','本地网','-') as sub_process_name, 'delete' as TaskDefKey, '已删除' as StatusName			"+
		 "		          from pnr_act_netresinspect_main m										"+
		 "		         where m.state = '1') w												"+
		 "		 where   w.state <> 2 ";
		
		ArrayList whereList=new ArrayList();
		
		String whereSql=" ";
		if (conditionQueryModel.getSendStartTime() != null && !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and to_char(w.reported_date,'yyyy-mm-dd hh24:mi:ss') >=?";
			whereList.add(conditionQueryModel.getSendStartTime());
		}
		if (conditionQueryModel.getSendEndTime() != null && !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and to_char(w.reported_date,'yyyy-mm-dd hh24:mi:ss') <=?";
			whereList.add(conditionQueryModel.getSendEndTime());
		}
		if (conditionQueryModel.getCity() != null && !conditionQueryModel.getCity().equals("")) {
			whereSql += " and w.city =?";
			whereList.add(conditionQueryModel.getCity());
		}
		if (conditionQueryModel.getWorkNumber() != null && !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and w.process_instance_id =?";
			whereList.add(conditionQueryModel.getWorkNumber());
		}
		if (conditionQueryModel.getTheme() != null && !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and instr(w.theme,?)>0 ";
			whereList.add(conditionQueryModel.getTheme());
		}
		if (conditionQueryModel.getCountry() != null && !conditionQueryModel.getCountry().equals("")) {
			whereSql += " and w.county = ?";
			whereList.add(conditionQueryModel.getCountry());
			
		}
		if (conditionQueryModel.getResourceType() != null && !conditionQueryModel.getResourceType().equals("")) {
			whereSql += " and w.resource_type =?";
			whereList.add(conditionQueryModel.getResourceType());
		}
		if (conditionQueryModel.getQuestionType() != null && !conditionQueryModel.getQuestionType().equals("")) {
			whereSql += " and w.question_type =?";
			whereList.add(conditionQueryModel.getQuestionType());
		}
		if (conditionQueryModel.getSpecialty() != null && !conditionQueryModel.getSpecialty().equals("")) {
			whereSql += " and w.specialty =?";
			whereList.add(conditionQueryModel.getSpecialty());
		}
		if (conditionQueryModel.getStatus() != null&& !conditionQueryModel.getStatus().equals("") && !conditionQueryModel.getStatus().equals("0")) {
			whereSql += " and w.taskdefkey =?";
			whereList.add(conditionQueryModel.getStatus());
		}
		
		sql += selectSql + whereSql
		+ " order by w.reported_date desc)temp1 where rownum <= ?) temp2 where temp2.num >?";
		
		whereList.add(endResult * pageSize);
		whereList.add(firstResult * pageSize);
		
		Object values[]=whereList.toArray();
		System.out.println("------------------巡检众筹工单查询列表："+sql);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<NetResInspectModel> r = new ArrayList<NetResInspectModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,values);
		for (Map map : list) {
			NetResInspectModel model = new NetResInspectModel();
			//工单号
			if(map.get("process_instance_id")!=null && !"".equals(map.get("process_instance_id"))){
				
				model.setProcessInstanceId(map.get("process_instance_id").toString());
			}
			//工单名称
			if(map.get("theme")!=null && !"".equals(map.get("theme"))){
				
				model.setTheme(map.get("theme").toString());
			}
			//派单时间
			if (map.get("reported_date") != null) {
				if (!"".equals(map.get("reported_date"))) {
					try {
						model.setReportedDate(format.parse(map.get("reported_date")
								.toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				
			}
			//问题类型
			if(map.get("question_type")!=null && !"".equals(map.get("question_type"))){
				
				model.setQuestionType(map.get("question_type").toString());
			}
			//资源类型
			if(map.get("resource_type")!=null && !"".equals(map.get("resource_type"))){
				
				model.setResourceType(map.get("resource_type").toString());
			}
			//地市
			if(map.get("city")!=null && !"".equals(map.get("city"))){
				
				model.setCity(map.get("city").toString());
			}
			//区县
			if(map.get("county")!=null && !"".equals(map.get("county"))){
				
				model.setCounty(map.get("county").toString());
			}
			//专业
			if(map.get("specialty")!=null && !"".equals(map.get("specialty"))){
				
				model.setSpecialty(map.get("specialty").toString());
			}
			//环节
			if(map.get("task_def_key_name")!=null && !"".equals(map.get("task_def_key_name"))){
				
				model.setTaskDefKeyName(map.get("task_def_key_name").toString());
			}
			//任务id
			if(map.get("task_id")!=null && !"".equals(map.get("task_id"))){
				
				model.setTaskId(map.get("task_id").toString());
			}
			
			//派发流程标识
			if(map.get("auto_send_process")!=null && !"".equals(map.get("auto_send_process"))){
				
				model.setAutoSendProcess(map.get("auto_send_process").toString());
			}
			//子流程名
			if(map.get("sub_process_name")!=null && !"".equals(map.get("sub_process_name"))){
				
				model.setSubProcessName(map.get("sub_process_name").toString());
			}
			//子流程id号
			if(map.get("sub_process_instance_id")!=null && !"".equals(map.get("sub_process_instance_id"))){
				
				model.setSubProcessInstanceId(map.get("sub_process_instance_id").toString());
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
						 "		  from (select m.*,decode(m.auto_send_process,0,'抢修','1','本地网','-') as sub_process_name,								"+
						 "		               case								"+
						 "		                 when m.state = '3' then		"+
						 "		                  'waitOrder'					"+
						 "		                 else							"+
						 "		                  to_char(t.task_def_key_)		"+
						 "		               end as TaskDefKey,				"+
						 "		               case								"+
						 "		                 when m.state = '3' then		"+
						 "		                  '待办'							"+
						 "		                 else							"+
					     "			             to_char(t.name_)				"+
						 "		               end as StatusName				"+
						 "		          from pnr_act_netresinspect_main  m,		"+
						 "		               (select RES.*			"+
						 "		                  from ACT_RU_TASK RES			"+
						 "		                 inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_	"+
						 "		                 WHERE D.KEY_ = 'netResInspect') t 						    "+
						 "		         where m.process_instance_id = t.proc_inst_id_						"+
						 "		           and m.state != '1'												"+
						 "		           and m.state != '5'												"+
						 "		        union	all															"+
						 "		        select m.*,decode(m.auto_send_process,0,'抢修','1','本地网','-') as sub_process_name, 'archive' as TaskDefKey, '已归档' as StatusName			"+
						 "		          from pnr_act_netresinspect_main m										"+
						 "		         where m.state = '5'												"+
						 "		        union	all															"+
						 "		        select m.*,decode(m.auto_send_process,0,'抢修','1','本地网','-') as sub_process_name, 'delete' as TaskDefKey, '已删除' as StatusName			"+
						 "		          from pnr_act_netresinspect_main m										"+
						 "		         where m.state = '1') w												"+
						 "		 where   w.state <> 2 ";
		ArrayList whereList=new ArrayList();
		
		String whereSql=" ";
		if (conditionQueryModel.getSendStartTime() != null && !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and to_char(w.reported_date,'yyyy-mm-dd hh24:mi:ss') >=?";
			whereList.add(conditionQueryModel.getSendStartTime());
		}
		if (conditionQueryModel.getSendEndTime() != null && !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and to_char(w.reported_date,'yyyy-mm-dd hh24:mi:ss') <=?";
			whereList.add(conditionQueryModel.getSendEndTime());
		}
		if (conditionQueryModel.getCity() != null && !conditionQueryModel.getCity().equals("")) {
			whereSql += " and w.city =?";
			whereList.add(conditionQueryModel.getCity());
		}
		if (conditionQueryModel.getWorkNumber() != null && !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and w.process_instance_id =?";
			whereList.add(conditionQueryModel.getWorkNumber());
		}
		if (conditionQueryModel.getTheme() != null && !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and instr(w.theme,?)>0 ";
			whereList.add(conditionQueryModel.getTheme());
		}
		if (conditionQueryModel.getCountry() != null && !conditionQueryModel.getCountry().equals("")) {
			whereSql += " and w.county = ?";
			whereList.add(conditionQueryModel.getCountry());
			
		}
		if (conditionQueryModel.getResourceType() != null && !conditionQueryModel.getResourceType().equals("")) {
			whereSql += " and w.resource_type =?";
			whereList.add(conditionQueryModel.getResourceType());
		}
		if (conditionQueryModel.getQuestionType() != null && !conditionQueryModel.getQuestionType().equals("")) {
			whereSql += " and w.question_type =?";
			whereList.add(conditionQueryModel.getQuestionType());
		}
		if (conditionQueryModel.getSpecialty() != null && !conditionQueryModel.getSpecialty().equals("")) {
			whereSql += " and w.specialty =?";
			whereList.add(conditionQueryModel.getSpecialty());
		}
		if (conditionQueryModel.getStatus() != null&& !conditionQueryModel.getStatus().equals("") && !conditionQueryModel.getStatus().equals("0")) {
			whereSql += " and w.taskdefkey =?";
			whereList.add(conditionQueryModel.getStatus());
		}
		
		sql += selectSql + whereSql
				+ " order by w.reported_date ";
		
		Object args[]=whereList.toArray();
		
		System.out.println("--------------巡检众筹工单查询列表总条数sql："+sql);


		int size = this.getJdbcTemplate().queryForInt(sql,args);
		return size;
	
	}
	
	/**
	 * 	 根据区县和专业获取现场核实人
	 	 * @author WANGJUN
	 	 * @title: getChecker
	 	 * @date Jun 21, 2016 9:01:39 AM
	 	 * @param county
	 	 * @param specialty
	 	 * @return String
	 */
	public String getChecker(String county,String specialty){
		String checker = ""; //现场核验人
		String field = ""; //字段的名字
		if("1280101".equals(specialty)){ //设备专业
			field = "shebei_checker";
		}else if("1280102".equals(specialty)){ //线路专业
			field = "xianlu_checker";
		}else if("1280103".equals(specialty)){ //无线网专业
			field = "wuxian_checker";
		}else{
			return checker;
		}
		String sql = "select "+field+" as checker from pnr_act_netresinspect_checker where county_id = ? ";
		ArrayList list = new ArrayList();
		System.out.println("------county="+county);
		list.add(county);
		
		System.out.println("------根据区县和专业获取现场核实人sql="+sql);
		Object[] args = list.toArray();
		List<Map> list1 = this.getJdbcTemplate().queryForList(sql,args);
		if (list1 != null && !list1.isEmpty() && list1.size() > 0) {
			if (list1.get(0).get("checker") != null) {
				checker = list1.get(0).get("checker").toString();
			}
		}
		return checker;
	}
	
	/**
	 * 	 查询自动派发的子工单数量
	 	 * @author WANGJUN
	 	 * @title: getSubCount
	 	 * @date Jun 28, 2016 2:23:33 PM
	 	 * @param userId
	 	 * @param processType
	 	 * @param conditionQueryModel
	 	 * @return int
	 */
	public int getSubCount(String userId,String processType,ConditionQueryModel conditionQueryModel){
		ArrayList paramlist = new ArrayList();
		String sql =  "select count(d.process_instance_id)" +
							"  from pnr_act_netresinspect_draft d" + 
							" where 1=1 and d.status='0'" + 
							"   and d.process_type = ?"+
							"   and d.initiator = ?";
		paramlist.add(processType);
		paramlist.add(userId);
		
		String whereSql = "";
		if (conditionQueryModel.getSendStartTime() != null && !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and to_char(d.create_time,'yyyy-mm-dd hh24:mi:ss') >=?";
			paramlist.add(conditionQueryModel.getSendStartTime());
		}
		if (conditionQueryModel.getSendEndTime() != null && !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and to_char(d.create_time,'yyyy-mm-dd hh24:mi:ss') <= ?";
			paramlist.add(conditionQueryModel.getSendEndTime());
		}
		if (conditionQueryModel.getWorkNumber() != null && !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and d.process_instance_id =?";
			paramlist.add(conditionQueryModel.getWorkNumber().trim());
		}
		if (conditionQueryModel.getTheme() != null && !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and instr(d.theme,?)>0 ";
			paramlist.add(conditionQueryModel.getTheme().trim());
		}
		
		sql += whereSql;

		System.out.println("------查询自动派发的子工单数量="+sql);
		
		Object[] args = paramlist.toArray();
		int size  = this.getJdbcTemplate().queryForInt(sql, args);
		return  size;
	}
	
	/**
	 * 	 查询自动派发的子工单集合
	 	 * @author WANGJUN
	 	 * @title: getSubCount
	 	 * @date Jun 28, 2016 2:23:33 PM
	 	 * @param userId
	 	 * @param processType
	 	 * @param conditionQueryModel
	 	 * @return int
	 */
	public List<TaskModel> getSubList(String userid,String processKey,ConditionQueryModel conditionQueryModel, int firstResult,int endResult, int pageSize){
		ArrayList paramlist = new ArrayList(); 		
		String sql = "select temp2.* from (select temp1.*, rownum num from (";

		String selectSql =  "select d.process_instance_id,d.theme,d.create_time,d.process_limit,d.connect_person,d.fault_description,d.task_id," +
							" d.city,d.country, d.degree,d.importance "+
							"  from pnr_act_netresinspect_draft d" + 
							" where 1=1 and d.status='0'" +
							"	and d.process_type = ?" + 
							"   and d.initiator = ?";
		paramlist.add(processKey);
		paramlist.add(userid);
		
		String whereSql = "";
		if (conditionQueryModel.getSendStartTime() != null && !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and to_char(d.create_time,'yyyy-mm-dd hh24:mi:ss') >=?";
			paramlist.add(conditionQueryModel.getSendStartTime());
		}
		if (conditionQueryModel.getSendEndTime() != null && !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and to_char(d.create_time,'yyyy-mm-dd hh24:mi:ss') <= ?";
			paramlist.add(conditionQueryModel.getSendEndTime());
		}
		if (conditionQueryModel.getWorkNumber() != null && !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and d.process_instance_id =?";
			paramlist.add(conditionQueryModel.getWorkNumber().trim());
		}
		if (conditionQueryModel.getTheme() != null && !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and instr(d.theme,?)>0 ";
			paramlist.add(conditionQueryModel.getTheme().trim());
		}
		
		sql += selectSql + whereSql
				+ " order by d.create_time desc) temp1 where rownum <=? ) temp2 where temp2.num >? ";
		paramlist.add(endResult * pageSize);
		paramlist.add(firstResult * pageSize);
		
		System.out.println("------查询自动派发的子工单集合="+sql);

		Object[] args = paramlist.toArray();
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql, args);
		for (Map map : list) {
			TaskModel model = new TaskModel();
			if (map.get("process_instance_id") != null && !"".equals(map.get("process_instance_id").toString())) { //工单流水号
				model.setProcessInstanceId(map.get("process_instance_id").toString());
			}
			if (map.get("theme") != null && !"".equals(map.get("theme").toString())) { //工单主题
				model.setTheme(map.get("theme").toString());
			}
			if (map.get("create_time") != null && !"".equals(map.get("create_time").toString())) { //创建时间
				try {
					model.setSendTime(format.parse(map.get("create_time").toString()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			if (map.get("process_limit") != null && !"".equals(map.get("process_limit").toString())) { //工单主题
				model.setProcessLimit(map.get("process_limit").toString());
			}
			if (map.get("connect_person") != null && !"".equals(map.get("connect_person").toString())) { //联系人
				model.setConnectPerson(map.get("connect_person").toString());
			}
			if (map.get("fault_description") != null && !"".equals(map.get("fault_description").toString())) { //故障描述
				model.setFaultSource(map.get("fault_description").toString());
			}
			if (map.get("task_id") != null && !"".equals(map.get("task_id").toString())) { //任务ID
				model.setTaskId(map.get("task_id").toString());
			}
			if (map.get("city") != null && !"".equals(map.get("city").toString())) { //地市
				model.setCity(map.get("city").toString());
			}
			if (map.get("country") != null && !"".equals(map.get("country").toString())) { //区县
				model.setCountry(map.get("country").toString());
			}
			if (map.get("degree") != null && !"".equals(map.get("degree").toString())) { //紧急程度
				model.setWorkOrderDegree(map.get("degree").toString());
			}
			if (map.get("importance") != null && !"".equals(map.get("importance").toString())) { //重要程度
				model.setImportance(map.get("importance").toString());
			}
			
			r.add(model);
		}
		
		return r;
	}

	@Override
	public NetResInspectDraft getNetResInspectDraft(String processInstanceId) {
		String sql = "select * from pnr_act_netresinspect_draft d where 1=1 and d.process_instance_id='"+processInstanceId+"'";
		System.out.println("工单查询结果集：" + sql);
		
		List<Map> list = this.getJdbcTemplate().queryForList(sql.toString());
		NetResInspectDraft netResInspectDraft = new NetResInspectDraft();
		if(list!=null && list.size()>0){
			netResInspectDraft.setTheme(list.get(0).get("THEME")==null?null:list.get(0).get("THEME").toString());
			netResInspectDraft.setFaultDescription(list.get(0).get("fault_description")==null?null:list.get(0).get("fault_description").toString());
			netResInspectDraft.setInspectProcessInstanceId(list.get(0).get("inspect_process_instance_id")==null?null:list.get(0).get("inspect_process_instance_id").toString());
			netResInspectDraft.setProcessLimit(Double.parseDouble(list.get(0).get("process_limit")==null?"0":list.get(0).get("process_limit").toString()));
			if (list.get(0).get("create_time") != null && !"".equals(list.get(0).get("create_time"))) {
				try {
					netResInspectDraft.setCreateTime(format.parse(list.get(0).get("create_time").toString()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			netResInspectDraft.setConnectPerson(list.get(0).get("connect_person")==null?null:list.get(0).get("connect_person").toString());
		}else{
			netResInspectDraft = null;
		}
		return netResInspectDraft;
	}

	@Override
	public void updateNetResInspectDraft(String netResInspectId) {
		String sql="update pnr_act_netresinspect_draft p set p.status='1'  where p.inspect_process_instance_id='"+netResInspectId+"'";
		this.getJdbcTemplate().update(sql);
	}

	@Override
	public List<OptionUtil> getCountrySelect(String city) {
		String sql=" select * from taw_system_area where parentareaid='"+city+"'";
		List<OptionUtil> r = new ArrayList<OptionUtil>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		for (Map map : list) {
			OptionUtil model = new OptionUtil();
			if (map.get("areaid") != null && !"".equals(map.get("areaid").toString())) {
				model.setKey(map.get("areaid").toString());
				model.setValue(map.get("areaid").toString());
			}
			if (map.get("areaname") != null && !"".equals(map.get("areaname").toString())) { 
				model.setText(map.get("areaname").toString());
			}
			r.add(model);
		}
		return r;
	}
	public void saveTurnToSendInfo(NetResInspectTurnToSendModel netResInspectTurnToSendModel){
		
	}

	@Override
	public List<NetResInspectModel> searchListsendundo(String userid,
			int pageSize, int pageIndex) {
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		String selectSql =	"select m.task_id," +
				"       m.task_def_key," + 
				"       m.task_def_key_name," + 
				"       m.assignee," + 
				"       m.process_instance_id," + 
				"       m.theme," + 
				"       m.reported_date," + 
				"       m.question_type," + 
				"       m.resource_type," + 
				"       m.city," + 
				"       m.county," + 
				"       m.specialty," + 
				"       nvl(m.auto_send_process,'-') as auto_send_process," + 
				"       decode(m.auto_send_process,0,'抢修','1','本地网','-') as sub_process_name," +
				"       nvl(m.sub_process_instance_id,'-') as sub_process_instance_id"+
				"  from pnr_act_netresinspect_main m "+
				"  where m.assignee = ?";
		return null;
	}
	@Override
	public List<TawCommonsAccessoriesForm> getRoomDemolitionAccessoriesList(
			String processInstanceId,String flag) {
		List<TawCommonsAccessoriesForm> acessoriesList ;
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select tc.id,tc.accessoriescnname,");
		stringBuilder.append("tc.accessoriesuploadtime,pat.step");
		stringBuilder.append(" from pnr_act_netresinspect_main m");
		stringBuilder.append(" left join pnr_act_transfer_attachment pat on m.process_instance_id=pat.process_instance_id");
		stringBuilder.append(" left join taw_commons_accessories tc on pat.accessories_id=tc.id");
		stringBuilder.append(" where m.process_instance_id='").append(processInstanceId).append("'");
		stringBuilder.append(" order by tc.accessoriesuploadtime asc");
		System.out.println("-----------机房拆除附件列表显示sql="+stringBuilder.toString());
		List<Map> list = this.getJdbcTemplate().queryForList(stringBuilder.toString());
		if(list!=null && list.size()>0){
			acessoriesList = new ArrayList<TawCommonsAccessoriesForm>();
			for (Map map : list) {
				TawCommonsAccessoriesForm model = new TawCommonsAccessoriesForm();
				
				if(map.get("id")!=null){
					
					model.setId(map.get("id").toString());
				}
				
				if(map.get("accessoriescnname")!=null){
					
					model.setAccessoriesCnName(map.get("accessoriescnname").toString());
				}
				
				if(map.get("oper")!=null){
					
					model.setAccessoriesUploader(map.get("oper").toString());
				}
				if(map.get("accessoriesuploadtime")!=null){
					
					model.setAccessoriesUploadTime(map.get("accessoriesuploadtime").toString());
				}
				String step = "";
				if(map.get("step")!=null){
					if("1".equals(map.get("step").toString().trim())){
						step = "工单发起";
					}else if("2".equals(map.get("step").toString().trim())){
						step = "现场核实";
					}else if("3".equals(map.get("step").toString().trim())){
						step = "隐患处理";
					}else if("4".equals(map.get("step").toString().trim())){
						step = "线路抢修流程/预检预修流程";
					}
					model.setActiveTemplateId(step);
				}
				if(map.get("id")!=null){
					acessoriesList.add(model);
				}

			}

		}else{
			acessoriesList=null;
		}
				return acessoriesList;
	}
	
	 /**
     *  根据区县获取子流程的派单人
     	 * @author WANGJUN
     	 * @title: getSubProcessInitiator
     	 * @date Sep 8, 2016 4:45:36 PM
     	 * @param county
     	 * @param processType
     	 * @return String
     */
 	public String getSubProcessInitiator(String county,String processType){
 		String initiator = ""; //派发人
		String field = ""; //字段的名字
		if("interface".equals(processType)){ //本地网预检预修
			field = "yjyx_checker";
		}else if("transferOffice".equals(processType)){ //抢修
			field = "qiangxiu_checker";
		}else{
			return initiator;
		}
		String sql = "select "+field+" as checker from pnr_act_netresinspect_checker where county_id = ? ";
		ArrayList list = new ArrayList();
		System.out.println("------county="+county);
		list.add(county);
		
		System.out.println("------ 根据区县获取子流程的派单人sql="+sql);
		Object[] args = list.toArray();
		List<Map> list1 = this.getJdbcTemplate().queryForList(sql,args);
		if (list1 != null && !list1.isEmpty() && list1.size() > 0) {
			if (list1.get(0).get("checker") != null) {
				initiator = list1.get(0).get("checker").toString();
			}
		}
		return initiator;
 	}
}
