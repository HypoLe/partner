package com.boco.activiti.partner.process.dao.hibernate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.activiti.partner.process.dao.IPnrTransferArteryPrecheckJDBCDao;
import com.boco.activiti.partner.process.po.ConditionQueryModel;
import com.boco.activiti.partner.process.po.TaskModel;
/**
 * 
   * @author wangyue
   * @ClassName: PnrTransferArteryPrecheckDaoJDBC
   * @Version 版本 
   * @Copyright boco
   * @date Mar 6, 2015 9:16:50 AM
   * @description 干线预检预修工单--dao实现类
 */
public class PnrTransferArteryPrecheckDaoJDBC extends JdbcDaoSupport implements
		IPnrTransferArteryPrecheckJDBCDao {
	
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
		String selectSql = "select count(*) as total from (select * from act_ru_task t, transferartery_relation r where t.task_def_key_ = r.current_link and t.proc_def_id_ like '"+processDefinitionKey+":%:%' and t.task_def_key_ in ('workOrderCheck','cityLineExamine','cityLineDirectorAudit','provinceLineExamine')) ta,(select distinct h.proc_inst_id_, h.task_def_key_, h.assignee_ from act_hi_taskinst h) hi,pnr_act_transfer_office_main m where ta.proc_inst_id_ = hi.proc_inst_id_ and ta.before_link = hi.task_def_key_ and ta.proc_inst_id_ = m.process_instance_id and hi.assignee_ = '"+userId+"' and (m.state != 3 and m.state != 6 and m.state != 8)";
		
		String whereSql = "";
		if (conditionQueryModel.getWorkNumber() != null && !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and m.process_instance_id ='" + conditionQueryModel.getWorkNumber().trim() + "'";
		}
		if (conditionQueryModel.getTheme() != null && !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and m.theme like '%" + conditionQueryModel.getTheme().trim() + "%'";
		}
	
		sql = selectSql + whereSql;
		 
		System.out.println("--------------干线 预检预修 工单抓回 条数sql="+sql);

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
		String selectSql = "select ta.id_ as TaskId,m.initiator as Initiator,m.one_initiator as OneInitiator,m.process_instance_id as ProcessInstanceId,m.send_time as SendTime,m.theme as Theme,m.task_assignee,m.state as State,m.submit_application_time as SubmitTime,m.city,m.country,m.work_type,m.workorder_type_name,m.sub_type_name,m.key_word,m.work_order_degree,m.rollback_flag,m.precheck_flag,m.project_amount,decode(m.sheet_id, null, '无', m.sheet_id) as sheetId,ta.name_ as statusName,ta.task_def_key_ as taskDefKey from (select * from act_ru_task t, transferartery_relation r where t.task_def_key_ = r.current_link and t.proc_def_id_ like '"+processDefinitionKey+":%:%' and t.task_def_key_ in ('workOrderCheck','cityLineExamine','cityLineDirectorAudit','provinceLineExamine')) ta,(select distinct h.proc_inst_id_, h.task_def_key_, h.assignee_ from act_hi_taskinst h) hi,pnr_act_transfer_office_main m where ta.proc_inst_id_ = hi.proc_inst_id_ and ta.before_link = hi.task_def_key_ and ta.proc_inst_id_ = m.process_instance_id and hi.assignee_ = '"+userId+"' and (m.state != 3 and m.state != 6 and m.state != 8)";
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
		
		System.out.println("--------------干线 预检预修 工单抓回 明细sql="+sql);
		
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
//			if(map.get("SubmitTime")!=null && !"".equals(map.get("SubmitTime"))){
//				
//				model.setApplicationTime(map.get("SubmitTime").toString());
//			}
			
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
}
