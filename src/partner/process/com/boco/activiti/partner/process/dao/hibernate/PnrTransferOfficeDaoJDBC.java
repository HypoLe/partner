package com.boco.activiti.partner.process.dao.hibernate;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.activiti.partner.process.dao.IPnrTransferOfficeJDBCDao;
import com.boco.activiti.partner.process.model.PnrActRuCountersign;
import com.boco.activiti.partner.process.model.PnrAndroidPhotoFile;
import com.boco.activiti.partner.process.model.PnrTransferOffice;
import com.boco.activiti.partner.process.po.AndroidQuery;
import com.boco.activiti.partner.process.po.AndroidWorkOrderTask;
import com.boco.activiti.partner.process.po.ConditionQueryModel;
import com.boco.activiti.partner.process.po.CycleCollarReportModel;
import com.boco.activiti.partner.process.po.MaleGuestSelectAttribute;
import com.boco.activiti.partner.process.po.MaleSceneDetailModel;
import com.boco.activiti.partner.process.po.MaleSceneStatisticsModel;
import com.boco.activiti.partner.process.po.MaterialQuantityModel;
import com.boco.activiti.partner.process.po.StatisticsMaterialInforModel;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.activiti.partner.process.po.TransferMaleGuestFlag;
import com.boco.activiti.partner.process.po.TransferMaleGuestReturn;
import com.boco.activiti.partner.process.po.WorkOrderStatisticsModel;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UUIDHexGenerator;
import com.boco.eoms.base.webapp.form.PersonNameUrlForm;
import com.boco.eoms.commons.accessories.dao.TawCommonsAccessoriesDao;
import com.boco.eoms.commons.accessories.model.TawCommonsAccessories;
import com.boco.eoms.commons.accessories.webapp.form.TawCommonsAccessoriesForm;
import com.boco.eoms.partner.process.util.CommonUtils;

/** 
 */
public class PnrTransferOfficeDaoJDBC extends JdbcDaoSupport implements
		IPnrTransferOfficeJDBCDao {
	private TaskService taskService;
	private HistoryService historyService;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public String getAttachmentNamesByProcessInstanceId(String processInstanceId) {

		String[] args = { processInstanceId };
		String sql = "select att.accessoriesname NAME from pnr_act_transfer_attachment att where att.process_instance_id=?";
		List<Map> list = this.getJdbcTemplate().queryForList(sql, args);

		int size = list.size();
		String attachmentNames = "";
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				if ("".equals(attachmentNames)) {
					attachmentNames += "'" + list.get(i).get("NAME") + "'";
				} else {
					attachmentNames += ",'" + list.get(i).get("NAME") + "'";
				}
			}

		}
		return attachmentNames;
	}

	/**
	 * 根据流程ID和环节的ID值查询附件
	 * @param processInstanceId 流程ID
	 * @param TaskDefinitionKey 环节的ID
	 * @return
	 */
	public String getAttachmentNamesByProcessInstanceIdAndUserTaskId(String processInstanceId,String userTaskId){
		String sql = "select att.accessoriesname NAME from pnr_act_transfer_attachment att where att.process_instance_id='"+processInstanceId+"' and att.step='"+userTaskId+"'";
		List<Map> list = this.getJdbcTemplate().queryForList(sql);

		int size = list.size();
		String attachmentNames = "";
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				if ("".equals(attachmentNames)) {
					attachmentNames += "'" + list.get(i).get("NAME") + "'";
				} else {
					attachmentNames += ",'" + list.get(i).get("NAME") + "'";
				}
			}

		}
		return attachmentNames;
	}
	
	
	
	@Override
	public void saveAttachment(String processInstanceId,
			String accessoriesNames,String step) {
		// TODO Auto-generated method stub
		final String accessorieS = accessoriesNames;
		final String process = processInstanceId;
		final String stepf = step;

		// 保存数据
		String sql = "insert into pnr_act_transfer_attachment(process_instance_id,accessories_id,accessoriesname,step) values(?,?,?,?)";
		TawCommonsAccessoriesDao tawCommonsAccessoriesDao = (TawCommonsAccessoriesDao) ApplicationContextHolder
				.getInstance().getBean("tawCommonsAccessoriesDao");
		final List<TawCommonsAccessories> list = tawCommonsAccessoriesDao
				.getAllFileByName(accessorieS);
		final int size = list.size();

		this.getJdbcTemplate().execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement ps)
					throws SQLException, DataAccessException {

				for (int i = 0; i < size; i++) {
					ps.setString(1, process);
					ps.setString(2, list.get(i).getId());
					ps.setString(3, list.get(i).getAccessoriesName());
					ps.setString(4, stepf);

					ps.addBatch();
				}
				return ps.executeBatch();
			}
		});
	}
	
	/**
	 * 多个附件框，上传附件方法
	 * @param processInstanceId
	 * @param accessoriesNames
	 * @param step
	 * @param linkName
	 * @param attributeName
	 */
	public void saveMultipleAttachments(String processInstanceId,
			String accessoriesNames,String step,String linkName,String attributeName){
		// TODO Auto-generated method stub
		final String accessorieS = accessoriesNames;
		final String process = processInstanceId;
		final String stepf = step;
		final String linkname=linkName;
		final String attributename=attributeName;

		// 保存数据
		String sql = "insert into pnr_act_transfer_attachment(process_instance_id,accessories_id,accessoriesname,step,link_name,attribute_name) values(?,?,?,?,?,?)";
		TawCommonsAccessoriesDao tawCommonsAccessoriesDao = (TawCommonsAccessoriesDao) ApplicationContextHolder
				.getInstance().getBean("tawCommonsAccessoriesDao");
		final List<TawCommonsAccessories> list = tawCommonsAccessoriesDao
				.getAllFileByName(accessorieS);
		final int size = list.size();

		this.getJdbcTemplate().execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement ps)
					throws SQLException, DataAccessException {

				for (int i = 0; i < size; i++) {
					ps.setString(1, process);
					ps.setString(2, list.get(i).getId());
					ps.setString(3, list.get(i).getAccessoriesName());
					ps.setString(4, stepf);
					ps.setString(5, linkname);
					ps.setString(6, attributename);
					ps.addBatch();
				}
				return ps.executeBatch();
			}
		});
	}
	
	/**
	 * 多附件回显
	 * 
	 * @param processInstanceId
	 *            流程号
	 * @param step
	 *            所在环节步骤值
	 * @param linkName
	 *            所在环节KEY
	 * @param attributeName
	 *            界面中附件插件的 idField和name（保持一致）
	 * @return
	 */
	public Map<String, String> echoMultipleAttachments(
			String processInstanceId, String step, String linkName,
			String attributeName) {
		Map<String, String> map=new HashMap<String, String>();
		String sql = " select attribute_name as attributeName , wmsys.wm_concat(''''||accessoriesname||'''') as accessoriesName from (select t.attribute_name, t.accessoriesname from pnr_act_transfer_attachment t where t.process_instance_id = '"
				+ processInstanceId
				+ "' and t.step = '"
				+ step
				+ "' and t.link_name = '"
				+ linkName+"'";
		if(attributeName!=null&&!"".equals(attributeName)){
			sql+="' and t.attribute_name = '"+attributeName+"'";
		}
		
		sql+=") t group by attribute_name";
		System.out.println("---------------多附件回显sql="+sql);			
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		int size = list.size();
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				map.put(list.get(i).get("attributeName").toString(), list.get(i).get("accessoriesName").toString());
			}

		}
		return map;
	}
	
	
	

	@Override
	public void saveOrUpateAttachment(String processInstanceId,
			String accessoriesNames) {
		// TODO Auto-generated method stub
		final String accessorieS = accessoriesNames;
		final String process = processInstanceId;
		// 先删除已存在的前一批
		String delSql = "delete from pnr_act_transfer_attachment where process_instance_id =?";
		this.getJdbcTemplate().execute(delSql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement ps)
					throws SQLException, DataAccessException {
				ps.setString(1, process);
				ps.addBatch();
				return ps.executeBatch();
			}
		});
		// 保存数据
		String sql = "insert into pnr_act_transfer_attachment(process_instance_id,accessories_id,accessoriesname) values(?,?,?)";
		TawCommonsAccessoriesDao tawCommonsAccessoriesDao = (TawCommonsAccessoriesDao) ApplicationContextHolder
				.getInstance().getBean("tawCommonsAccessoriesDao");
		final List<TawCommonsAccessories> list = tawCommonsAccessoriesDao
				.getAllFileByName(accessorieS);
		final int size = list.size();

		this.getJdbcTemplate().execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement ps)
					throws SQLException, DataAccessException {

				for (int i = 0; i < size; i++) {
					ps.setString(1, process);
					ps.setString(2, list.get(i).getId());
					ps.setString(3, list.get(i).getAccessoriesName());
					ps.addBatch();
				}
				return ps.executeBatch();
			}
		});
	}

	
	
	@Override
	public int workOrderCount(String deptId, String workerid, String beginTime,
			String endTime, String subType, String theme, String city) {
		historyService = (HistoryService) ApplicationContextHolder
				.getInstance().getBean("historyService");

		String whereSql = " where m.state = 5 ";
		if (beginTime != null && !beginTime.equals("")) {
			whereSql = whereSql + " and m.create_time > to_date('" + beginTime
					+ "','yyyy-MM-dd HH24:mi:ss')";
		}
		if (endTime != null && !endTime.equals("")) {
			whereSql = whereSql + " and m.create_time < to_date('" + endTime
					+ "','yyyy-MM-dd HH24:mi:ss')";
		}
		if (subType != null && !subType.equals("")) {
			whereSql = whereSql + " and m.sub_type=  '" + subType + "'";
		}
		if (theme != null && !theme.equals("")) {
			whereSql = whereSql + " and m.theme like  '%" + theme + "%'";
		}
		if (city != null && !city.equals("")) {

			whereSql = whereSql + " and dept.areaid like '" + city + "%'";

		}
		if (!"".equals(workerid) && null != workerid) {
			String process_instance_id = "";

			List<HistoricTaskInstance> listw = historyService
					.createHistoricTaskInstanceQuery().taskAssignee(workerid)
					.list();

			for (int j = 0; j < listw.size(); j++) {

				List<HistoricTaskInstance> historicTasks = historyService
						.createHistoricTaskInstanceQuery().processInstanceId(
								listw.get(j).getProcessInstanceId())
						.orderByTaskId().desc().listPage(0, 2);

				if ("completed".equals(historicTasks.get(0).getDeleteReason())) {
					if (historicTasks.get(0).getAssignee().equals(workerid)) {

						if ("".equals(process_instance_id)) {

							process_instance_id = listw.get(j)
									.getProcessInstanceId();
						} else {

							process_instance_id += ","
									+ listw.get(j).getProcessInstanceId();
						}
					}
				} else {
					if (historicTasks.get(1).getAssignee().equals(workerid)) {

						if ("".equals(process_instance_id)) {

							process_instance_id = listw.get(j)
									.getProcessInstanceId();
						} else {

							process_instance_id += ","
									+ listw.get(j).getProcessInstanceId();
						}
					}
				}

			}

			if (!process_instance_id.equals("")) {

				whereSql = whereSql + " and m.process_instance_id in ("
						+ process_instance_id + ")";
			}
		}

		String sql = "select count(*) as total from pnr_act_transfer_office_main m left join taw_system_user u on m.task_assignee = u.userid left join taw_system_dept dept on u.deptid = dept.deptid "
				+ whereSql;
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		return Integer.parseInt(list.get(0).get("total").toString());
	}

	@Override
	public int workOrderCount1(String flag, String deptId, String workerid,
			String beginTime, String endTime, String subType, String theme,
			String city) {
		historyService = (HistoryService) ApplicationContextHolder
				.getInstance().getBean("historyService");

		String whereSql = " where m.state = 5 and m.themeInterface='" + flag
				+ "'";
		if (beginTime != null && !beginTime.equals("")) {
			whereSql = whereSql + " and m.create_time > to_date('" + beginTime
					+ "','yyyy-MM-dd HH24:mi:ss')";
		}
		if (endTime != null && !endTime.equals("")) {
			whereSql = whereSql + " and m.create_time < to_date('" + endTime
					+ "','yyyy-MM-dd HH24:mi:ss')";
		}
		if (subType != null && !subType.equals("")) {
			whereSql = whereSql + " and m.sub_type=  '" + subType + "'";
		}
		if (theme != null && !theme.equals("")) {
			whereSql = whereSql + " and m.theme like  '%" + theme + "%'";
		}
		if (deptId != null && !deptId.equals("")) {

			/*
			 * if(deptId.equals("-1")){
			 * 
			 * whereSql=whereSql+" and u.deptid like '%'"; }else{
			 */
			whereSql = whereSql + " and u.deptid like '" + deptId + "%'";

			// }
		}
		if (!"".equals(workerid) && null != workerid) {
			String process_instance_id = "";

			List<HistoricTaskInstance> listw = historyService
					.createHistoricTaskInstanceQuery().taskAssignee(workerid)
					.list();

			for (int j = 0; j < listw.size(); j++) {

				List<HistoricTaskInstance> historicTasks = historyService
						.createHistoricTaskInstanceQuery().processInstanceId(
								listw.get(j).getProcessInstanceId())
						.orderByTaskId().desc().listPage(0, 2);

				if ("completed".equals(historicTasks.get(0).getDeleteReason())) {
					if (historicTasks.get(0).getAssignee().equals(workerid)) {

						if ("".equals(process_instance_id)) {

							process_instance_id = listw.get(j)
									.getProcessInstanceId();
						} else {

							process_instance_id += ","
									+ listw.get(j).getProcessInstanceId();
						}
					}
				} else {
					if (historicTasks.get(1).getAssignee().equals(workerid)) {

						if ("".equals(process_instance_id)) {

							process_instance_id = listw.get(j)
									.getProcessInstanceId();
						} else {

							process_instance_id += ","
									+ listw.get(j).getProcessInstanceId();
						}
					}
				}

			}

			if (!process_instance_id.equals("")) {

				whereSql = whereSql + " and m.process_instance_id in ("
						+ process_instance_id + ")";
			}
		}

		String sql = "select count(*) as total from pnr_act_transfer_office_main m left join taw_system_user u on m.task_assignee = u.userid "
				+ whereSql;
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		return Integer.parseInt(list.get(0).get("total").toString());
	}

	
	@SuppressWarnings("unchecked")
	public int workOrderMaleGuestCount(String searchRange, String userId,
			String flag, String deptId, String workerid, String beginTime,
			String endTime, String subType, String theme, String city) {
		ArrayList list1 = new ArrayList();
		historyService = (HistoryService) ApplicationContextHolder
				.getInstance().getBean("historyService");

		String whereSql = " where m.state != 1 and m.do_flag is null and m.themeInterface= ?";
		list1.add(flag);
		
		if (beginTime != null && !beginTime.equals("")) {
			whereSql = whereSql + " and to_char(m.create_time,'yyyy-mm-dd hh24:mi:ss') > ?";
			list1.add(beginTime);
		}
		if (endTime != null && !endTime.equals("")) {
			whereSql = whereSql + " and to_char(m.create_time,'yyyy-mm-dd hh24:mi:ss') < ?";
			list1.add(endTime);
		}
		if (subType != null && !subType.equals("")) {
			whereSql = whereSql + " and m.sub_type= ?";
			list1.add(subType);
		}
		if (theme != null && !theme.equals("")) {
			whereSql = whereSql + " and instr(m.theme,?)>0";
			list1.add(theme);
		}

		if ("all".equals(searchRange)) {

			if (deptId != null && !deptId.equals("")) {
				whereSql = whereSql + " and u.deptid like ?";
				list1.add(deptId+"%");
			}
		} else if ("mySelf".equals(searchRange)) {
			whereSql = whereSql + " and (m.initiator = ? or m.transfer_office_id = ?)";
			list1.add(userId);
			list1.add(userId);
		}
		if (!"".equals(workerid) && null != workerid) {
			String process_instance_id = "";

			List<HistoricTaskInstance> listw = historyService
					.createHistoricTaskInstanceQuery().taskAssignee(workerid)
					.list();

			for (int j = 0; j < listw.size(); j++) {

				List<HistoricTaskInstance> historicTasks = historyService
						.createHistoricTaskInstanceQuery().processInstanceId(
								listw.get(j).getProcessInstanceId())
						.orderByTaskId().desc().listPage(0, 2);

				if ("completed".equals(historicTasks.get(0).getDeleteReason())) {
					if (historicTasks.get(0).getAssignee().equals(workerid)) {

						if ("".equals(process_instance_id)) {

							process_instance_id = listw.get(j)
									.getProcessInstanceId();
						} else {

							process_instance_id += ","
									+ listw.get(j).getProcessInstanceId();
						}
					}
				} else {
					if (historicTasks.get(1).getAssignee().equals(workerid)) {

						if ("".equals(process_instance_id)) {

							process_instance_id = listw.get(j)
									.getProcessInstanceId();
						} else {

							process_instance_id += ","
									+ listw.get(j).getProcessInstanceId();
						}
					}
				}

			}

			if (!process_instance_id.equals("")) {
				whereSql = whereSql + " and m.process_instance_id in (select * from TABLE(cast(f_str2List(?) as varchar2TableType)))";
				list1.add(process_instance_id);
			}
		}

		String sql = "select count(*) as total from pnr_act_transfer_office_main m left join taw_system_user u on m.task_assignee = u.userid "
				+ whereSql;
		System.out.println("公客工单查询统计数sql="+sql);
		Object[] args = list1.toArray();
		int total=0;
		total = this.getJdbcTemplate().queryForInt(sql, args);
		return total;
	}

	@Override
	public int workOrderCountAdmin(String userId, String flag, String deptId,
			String workerid, String beginTime, String endTime, String subType,
			String theme, String city) {
		historyService = (HistoryService) ApplicationContextHolder
				.getInstance().getBean("historyService");

		String whereSql = " where m.state != 1 and m.themeInterface='" + flag
				+ "'";
		if (beginTime != null && !beginTime.equals("")) {
			whereSql = whereSql + " and m.create_time > to_date('" + beginTime
					+ "','yyyy-MM-dd HH24:mi:ss')";
		}
		if (endTime != null && !endTime.equals("")) {
			whereSql = whereSql + " and m.create_time < to_date('" + endTime
					+ "','yyyy-MM-dd HH24:mi:ss')";
		}
		if (subType != null && !subType.equals("")) {
			whereSql = whereSql + " and m.sub_type=  '" + subType + "'";
		}
		if (theme != null && !theme.equals("")) {
			whereSql = whereSql + " and m.theme like  '%" + theme + "%'";
		}
		if (deptId != null && !deptId.equals("")) {

			/*
			 * if(deptId.equals("-1")){
			 * 
			 * whereSql=whereSql+" and u.deptid like '%'"; }else{
			 */
			// 根据登录人查询出该人权限级别
			String orderCode = "";
			String areaId = "";
			List<Map> person = getOrderCodeByUserId(userId);
			if (person != null && person.size() > 0) {
				orderCode = person.get(0).get("ORDERCODE").toString();
				areaId = (String) person.get(0).get("AREAID").toString();
				if (orderCode != null
						&& !"".equals(orderCode = orderCode.trim())) {

					// orderCode=1
					if ("1".equals(orderCode)) {
						whereSql = whereSql
								+ " and ta.parentareaid in (select tsa.areaid from taw_system_area tsa where tsa.parentareaid="
								+ areaId + ")";
					}

					// ordercode=2
					if ("2".equals(orderCode)) {
						whereSql = whereSql + " and ta.parentareaid=" + areaId;
					}
					// ordercode=3
					if ("3".equals(orderCode)) {
						whereSql = whereSql + " and ta.areaid=" + areaId;
					}
				}
			}
			// }
		}
		if (!"".equals(workerid) && null != workerid) {
			String process_instance_id = "";

			List<HistoricTaskInstance> listw = historyService
					.createHistoricTaskInstanceQuery().taskAssignee(workerid)
					.list();

			for (int j = 0; j < listw.size(); j++) {

				List<HistoricTaskInstance> historicTasks = historyService
						.createHistoricTaskInstanceQuery().processInstanceId(
								listw.get(j).getProcessInstanceId())
						.orderByTaskId().desc().listPage(0, 2);

				if ("completed".equals(historicTasks.get(0).getDeleteReason())) {
					if (historicTasks.get(0).getAssignee().equals(workerid)) {

						if ("".equals(process_instance_id)) {

							process_instance_id = listw.get(j)
									.getProcessInstanceId();
						} else {

							process_instance_id += ","
									+ listw.get(j).getProcessInstanceId();
						}
					}
				} else {
					if (historicTasks.get(1).getAssignee().equals(workerid)) {

						if ("".equals(process_instance_id)) {

							process_instance_id = listw.get(j)
									.getProcessInstanceId();
						} else {

							process_instance_id += ","
									+ listw.get(j).getProcessInstanceId();
						}
					}
				}

			}

			if (!process_instance_id.equals("")) {

				whereSql = whereSql + " and m.process_instance_id in ("
						+ process_instance_id + ")";
			}
		}

		String sql = "select count(*) as total from pnr_act_transfer_office_main m left join taw_system_user u on m.task_assignee = u.userid "
				+ " left join taw_system_dept dt "
				+ " on u.deptid=dt.deptid "
				+ " left join taw_system_area ta "
				+ " on dt.areaid = ta.areaid " + whereSql;
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		return Integer.parseInt(list.get(0).get("total").toString());
	}

	@Override
	public List<TaskModel> workOrderQuery(String deptId, String workerid,
			String beginTime, String endTime, String subType, String theme,
			String city, int beginNum, int endNum) {
		historyService = (HistoryService) ApplicationContextHolder
				.getInstance().getBean("historyService");
		taskService = (TaskService) ApplicationContextHolder.getInstance()
				.getBean("taskService");

		List<TaskModel> r = new ArrayList<TaskModel>();
		String whereSql = " where m.state=5  ";
		if (beginTime != null && !beginTime.equals("")) {
			whereSql = whereSql + " and m.create_time > to_date('" + beginTime
					+ "','yyyy-MM-dd HH24:mi:ss')";
		}
		if (endTime != null && !endTime.equals("")) {
			whereSql = whereSql + " and m.create_time < to_date('" + endTime
					+ "','yyyy-MM-dd HH24:mi:ss')";
		}
		if (subType != null && !subType.equals("")) {
			whereSql = whereSql + " and m.sub_type=  '" + subType + "'";
		}
		if (theme != null && !theme.equals("")) {
			whereSql = whereSql + " and m.theme like  '%" + theme + "%'";
		}

		if (city != null && !city.equals("")) {

			whereSql = whereSql + " and dept.areaid like '" + city + "%'";

		}
		if (!"".equals(workerid) && null != workerid) {
			String process_instance_id = "";

			List<HistoricTaskInstance> listw = historyService
					.createHistoricTaskInstanceQuery().taskAssignee(workerid)
					.list();

			for (int j = 0; j < listw.size(); j++) {

				List<HistoricTaskInstance> historicTasks = historyService
						.createHistoricTaskInstanceQuery().processInstanceId(
								listw.get(j).getProcessInstanceId())
						.orderByTaskId().desc().listPage(0, 2);

				if ("completed".equals(historicTasks.get(0).getDeleteReason())) {
					if (historicTasks.get(0).getAssignee().equals(workerid)) {

						if ("".equals(process_instance_id)) {

							process_instance_id = listw.get(j)
									.getProcessInstanceId();
						} else {

							process_instance_id += ","
									+ listw.get(j).getProcessInstanceId();
						}
					}
				} else {
					if (historicTasks.get(1).getAssignee().equals(workerid)) {

						if ("".equals(process_instance_id)) {

							process_instance_id = listw.get(j)
									.getProcessInstanceId();
						} else {

							process_instance_id += ","
									+ listw.get(j).getProcessInstanceId();
						}
					}
				}

			}

			if (!process_instance_id.equals("")) {

				whereSql = whereSql + " and m.process_instance_id in ("
						+ process_instance_id + ")";
			}
		}
		String sql = "select process_instance_id,theme,initiator,deptid,send_time from (select m.process_instance_id,m.theme,m.initiator,u2.deptid,m.send_time, rownum rwn    from pnr_act_transfer_office_main m left join taw_system_user u on m.task_assignee = u.userid left join taw_system_dept dept on u.deptid = dept.deptid left join taw_system_user u2 on m.initiator = u2.userid "
				+ whereSql
				+ " and rownum <="
				+ endNum
				+ " order by m.create_time desc) where rwn>" + beginNum;

		List<Map> list = this.getJdbcTemplate().queryForList(sql);

		for (Map map : list) {
			TaskModel taskModel = new TaskModel();
			taskModel.setDeptId(map.get("DEPTID").toString());
			taskModel.setProcessInstanceId(map.get("process_instance_id")
					.toString());
			taskModel.setTheme(map.get("theme").toString());
			taskModel.setInitiator(map.get("INITIATOR").toString());
			try {
				DateFormat dateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				taskModel.setSendTime(dateFormat.parse(map.get("SEND_TIME")
						.toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<HistoricTaskInstance> historicTasks = historyService
					.createHistoricTaskInstanceQuery().processInstanceId(
							taskModel.getProcessInstanceId()).orderByTaskId()
					.desc().listPage(0, 2);

			List<Task> taskList = taskService.createTaskQuery()
					.processInstanceId(taskModel.getProcessInstanceId())
					.active().list();

			if (taskList != null && taskList.size() == 1) {
				Task task = taskList.get(0);
				taskModel.setStatusName(task.getName());
				taskModel.setTaskId(task.getId());
				taskModel.setExecutor(task.getAssignee());
			}

			if ("delete".equals(historicTasks.get(0).getDeleteReason())) {
				taskModel.setExecutor(historicTasks.get(1).getAssignee());
				taskModel.setStatusName(CommonUtils.taskDetele);
			} else if ("completed".equals(historicTasks.get(0)
					.getDeleteReason())) {

				taskModel.setExecutor(historicTasks.get(0).getAssignee());
				taskModel.setStatusName(CommonUtils.taskComplete);
			} else {
				taskModel.setExecutor(historicTasks.get(1).getAssignee());
			}

			r.add(taskModel);
		}
		return r;
	}

	@Override
	public List<TaskModel> workOrderQuery1(String flag, String deptId,
			String workerid, String beginTime, String endTime, String subType,
			String theme, String city, int beginNum, int endNum) {
		historyService = (HistoryService) ApplicationContextHolder
				.getInstance().getBean("historyService");
		taskService = (TaskService) ApplicationContextHolder.getInstance()
				.getBean("taskService");

		List<TaskModel> r = new ArrayList<TaskModel>();
		String whereSql = " where m.state!=1  and m.themeInterface = '" + flag
				+ "' ";
		if (beginTime != null && !beginTime.equals("")) {
			whereSql = whereSql + " and m.create_time > to_date('" + beginTime
					+ "','yyyy-MM-dd HH24:mi:ss')";
		}
		if (endTime != null && !endTime.equals("")) {
			whereSql = whereSql + " and m.create_time < to_date('" + endTime
					+ "','yyyy-MM-dd HH24:mi:ss')";
		}
		if (subType != null && !subType.equals("")) {
			whereSql = whereSql + " and m.sub_type=  '" + subType + "'";
		}
		if (theme != null && !theme.equals("")) {
			whereSql = whereSql + " and m.theme like  '%" + theme + "%'";
		}

		if (deptId != null && !deptId.equals("")) {

			/*
			 * if(deptId.equals("-1")){
			 * 
			 * whereSql=whereSql+" and u.deptid like '%'"; }else{
			 */
			whereSql = whereSql + " and u.deptid like '" + deptId + "%'";

			// }
		}
		if (!"".equals(workerid) && null != workerid) {
			String process_instance_id = "";

			List<HistoricTaskInstance> listw = historyService
					.createHistoricTaskInstanceQuery().taskAssignee(workerid)
					.list();

			for (int j = 0; j < listw.size(); j++) {

				List<HistoricTaskInstance> historicTasks = historyService
						.createHistoricTaskInstanceQuery().processInstanceId(
								listw.get(j).getProcessInstanceId())
						.orderByTaskId().desc().listPage(0, 2);

				if ("completed".equals(historicTasks.get(0).getDeleteReason())) {
					if (historicTasks.get(0).getAssignee().equals(workerid)) {

						if ("".equals(process_instance_id)) {

							process_instance_id = listw.get(j)
									.getProcessInstanceId();
						} else {

							process_instance_id += ","
									+ listw.get(j).getProcessInstanceId();
						}
					}
				} else {
					if (historicTasks.get(1).getAssignee().equals(workerid)) {

						if ("".equals(process_instance_id)) {

							process_instance_id = listw.get(j)
									.getProcessInstanceId();
						} else {

							process_instance_id += ","
									+ listw.get(j).getProcessInstanceId();
						}
					}
				}

			}

			if (!process_instance_id.equals("")) {

				whereSql = whereSql + " and m.process_instance_id in ("
						+ process_instance_id + ")";
			}
		}
		String sql = "select process_instance_id,theme,initiator,deptid,send_time from (select m.process_instance_id,m.theme,m.initiator,u2.deptid,m.send_time, rownum rwn    from pnr_act_transfer_office_main m left join taw_system_user u on m.task_assignee = u.userid left join taw_system_user u2 on m.initiator = u2.userid "
				+ whereSql
				+ " and rownum <="
				+ endNum
				+ " order by m.create_time desc) where rwn>" + beginNum;

		List<Map> list = this.getJdbcTemplate().queryForList(sql);

		for (Map map : list) {
			TaskModel taskModel = new TaskModel();
			taskModel.setDeptId(map.get("DEPTID").toString());
			taskModel.setProcessInstanceId(map.get("process_instance_id")
					.toString());
			taskModel.setTheme(map.get("theme").toString());
			taskModel.setInitiator(map.get("INITIATOR").toString());
			try {
				DateFormat dateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				taskModel.setSendTime(dateFormat.parse(map.get("SEND_TIME")
						.toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<HistoricTaskInstance> historicTasks = historyService
					.createHistoricTaskInstanceQuery().processInstanceId(
							taskModel.getProcessInstanceId()).orderByTaskId()
					.desc().listPage(0, 2);

			List<Task> taskList = taskService.createTaskQuery()
					.processInstanceId(taskModel.getProcessInstanceId())
					.active().list();

			if (taskList != null && taskList.size() == 1) {
				Task task = taskList.get(0);
				taskModel.setStatusName(task.getName());
				taskModel.setTaskId(task.getId());
				taskModel.setExecutor(task.getAssignee());
			}

			if ("delete".equals(historicTasks.get(0).getDeleteReason())) {
				taskModel.setExecutor(historicTasks.get(1).getAssignee());
				taskModel.setStatusName(CommonUtils.taskDetele);
			} else if ("completed".equals(historicTasks.get(0)
					.getDeleteReason())) {

				taskModel.setExecutor(historicTasks.get(0).getAssignee());
				taskModel.setStatusName(CommonUtils.taskComplete);
			} else {
				taskModel.setExecutor(historicTasks.get(1).getAssignee());
			}

			r.add(taskModel);
		}
		return r;
	}

	@SuppressWarnings("unchecked")
	public List<TaskModel> workOrderMaleGuestQuery(String searchRange,
			String userId, String flag, String deptId, String workerid,
			String beginTime, String endTime, String subType, String theme,
			String city, int beginNum, int endNum) {
		ArrayList list1 = new ArrayList();
		historyService = (HistoryService) ApplicationContextHolder
				.getInstance().getBean("historyService");
		taskService = (TaskService) ApplicationContextHolder.getInstance()
				.getBean("taskService");

		List<TaskModel> r = new ArrayList<TaskModel>();
		String whereSql = " where m.state!=1 and m.do_flag is null  and m.themeInterface = ?";
		list1.add(flag);
		if (beginTime != null && !beginTime.equals("")) {
			whereSql = whereSql + " and to_char(m.create_time,'yyyy-mm-dd hh24:mi:ss') > ?";
			list1.add(beginTime);
		}
		if (endTime != null && !endTime.equals("")) {
			whereSql = whereSql + " and to_char(m.create_time,'yyyy-mm-dd hh24:mi:ss') < ?";
			list1.add(endTime);
		}
		if (subType != null && !subType.equals("")) {
			whereSql = whereSql + " and m.sub_type= ?";
			list1.add(subType);
		}
		if (theme != null && !theme.equals("")) {
			whereSql = whereSql + " and instr(m.theme,?)>0";
			list1.add(theme);
		}

		if ("all".equals(searchRange)) {

			if (deptId != null && !deptId.equals("")) {
				whereSql = whereSql + " and u.deptid like ?";
				list1.add(deptId+"%");
			}
		} else if ("mySelf".equals(searchRange)) {
			whereSql = whereSql + " and (m.initiator = ? or m.transfer_office_id = ?)";
			list1.add(userId);
			list1.add(userId);
		}
		if (!"".equals(workerid) && null != workerid) {
			String process_instance_id = "";

			List<HistoricTaskInstance> listw = historyService
					.createHistoricTaskInstanceQuery().taskAssignee(workerid)
					.list();

			for (int j = 0; j < listw.size(); j++) {

				List<HistoricTaskInstance> historicTasks = historyService
						.createHistoricTaskInstanceQuery().processInstanceId(
								listw.get(j).getProcessInstanceId())
						.orderByTaskId().desc().listPage(0, 2);

				if ("completed".equals(historicTasks.get(0).getDeleteReason())) {
					if (historicTasks.get(0).getAssignee().equals(workerid)) {

						if ("".equals(process_instance_id)) {

							process_instance_id = listw.get(j)
									.getProcessInstanceId();
						} else {

							process_instance_id += ","
									+ listw.get(j).getProcessInstanceId();
						}
					}
				} else {
					if (historicTasks.get(1).getAssignee().equals(workerid)) {

						if ("".equals(process_instance_id)) {

							process_instance_id = listw.get(j)
									.getProcessInstanceId();
						} else {

							process_instance_id += ","
									+ listw.get(j).getProcessInstanceId();
						}
					}
				}

			}

			if (!process_instance_id.equals("")) {
				whereSql = whereSql + " and m.process_instance_id in (select * from TABLE(cast(f_str2List(?) as varchar2TableType)))";
				list1.add(process_instance_id);
			}
		}
		String sql = "select process_instance_id, theme, initiator, deptid, send_time ,id_,state,task_def_key_, assignee_ from"
				+ " (select rownum r, s.* from(select m.process_instance_id,m.theme,m.initiator,u2.deptid,m.send_time, t.task_def_key_,t.id_, t.assignee_, m.state,rownum rwn"
				+ " from pnr_act_transfer_office_main m left join taw_system_user u"
				+ " on m.task_assignee = u.userid"
				+ " left join taw_system_user u2"
				+ " on m.initiator = u2.userid" +
						" left join act_ru_task t " +
						" on m.process_instance_id = t.proc_inst_id_  "
				+ whereSql
				+ " order by m.create_time desc)s)"
				+ " where r > ? and r<= ?";
		list1.add(beginNum);
		list1.add(endNum);

		System.out.println("公客工单查询明细sql="+sql);	
		Object[] args = list1.toArray();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,args);
		for (Map map : list) {
			TaskModel taskModel = new TaskModel();
			taskModel.setDeptId(map.get("DEPTID").toString());
			taskModel.setProcessInstanceId(map.get("process_instance_id")
					.toString());
			taskModel.setTheme(map.get("theme").toString());
			taskModel.setInitiator(map.get("INITIATOR").toString());
			try {
				DateFormat dateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				taskModel.setSendTime(dateFormat.parse(map.get("SEND_TIME")
						.toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String state = map.get("STATE").toString().trim();
			String stateName="";
			if(map.get("task_def_key_")==null){
				stateName="处理完毕";
			}else if("8".equals(state)){
				stateName="处理完毕";
			}else if("newMaleGuest".equals(map.get("task_def_key_"))){
				stateName="工单派发";
			}else if("auditor".equals(map.get("task_def_key_"))){
				stateName="故障处理";
			}
			taskModel.setStatusName(stateName);
			if(map.get("ID_")!=null){
				taskModel.setTaskId(map.get("ID_").toString());
			}
			if(map.get("ASSIGNEE_")!=null){
				taskModel.setExecutor(map.get("ASSIGNEE_").toString());
			}
			
			/*List<HistoricTaskInstance> historicTasks = historyService
					.createHistoricTaskInstanceQuery().processInstanceId(
							taskModel.getProcessInstanceId()).orderByTaskId()
					.desc().listPage(0, 2);

			List<Task> taskList = taskService.createTaskQuery()
					.processInstanceId(taskModel.getProcessInstanceId())
					.active().list();

			if (taskList != null && taskList.size() == 1) {
				Task task = taskList.get(0);
				taskModel.setStatusName(task.getName());
				taskModel.setTaskId(task.getId());
				taskModel.setExecutor(task.getAssignee());
			}

			if ("delete".equals(historicTasks.get(0).getDeleteReason())) {
				taskModel.setExecutor(historicTasks.get(0).getAssignee());
				taskModel.setStatusName(CommonUtils.taskDetele);
			} else if ("completed".equals(historicTasks.get(0)
					.getDeleteReason())) {

				taskModel.setExecutor(historicTasks.get(0).getAssignee());
				taskModel.setStatusName(CommonUtils.taskComplete);
			} else {
				taskModel.setExecutor(historicTasks.get(0).getAssignee());
			}
*/
			r.add(taskModel);
		}
		return r;
	}

	@Override
	public List<TaskModel> workOrderQueryAdmin(String userId, String flag,
			String deptId, String workerid, String beginTime, String endTime,
			String subType, String theme, String city, int beginNum, int endNum) {
		historyService = (HistoryService) ApplicationContextHolder
				.getInstance().getBean("historyService");
		taskService = (TaskService) ApplicationContextHolder.getInstance()
				.getBean("taskService");

		List<TaskModel> r = new ArrayList<TaskModel>();
		String whereSql = " where m.state!=1  and m.themeInterface = '" + flag
				+ "' ";
		if (beginTime != null && !beginTime.equals("")) {
			whereSql = whereSql + " and m.create_time > to_date('" + beginTime
					+ "','yyyy-MM-dd HH24:mi:ss')";
		}
		if (endTime != null && !endTime.equals("")) {
			whereSql = whereSql + " and m.create_time < to_date('" + endTime
					+ "','yyyy-MM-dd HH24:mi:ss')";
		}
		if (subType != null && !subType.equals("")) {
			whereSql = whereSql + " and m.sub_type=  '" + subType + "'";
		}
		if (theme != null && !theme.equals("")) {
			whereSql = whereSql + " and m.theme like  '%" + theme + "%'";
		}

		if (deptId != null && !deptId.equals("")) {

			/*
			 * if(deptId.equals("-1")){
			 * 
			 * whereSql=whereSql+" and u.deptid like '%'";
			 * 
			 * }else{
			 */
			// 根据登录人查询出该人权限级别
			String orderCode = "";
			String areaId = "";
			List<Map> person = getOrderCodeByUserId(userId);
			if (person != null && person.size() > 0) {
				orderCode = person.get(0).get("ORDERCODE").toString();
				areaId = (String) person.get(0).get("AREAID").toString();
				if (orderCode != null
						&& !"".equals(orderCode = orderCode.trim())) {

					// orderCode=1
					if ("1".equals(orderCode)) {
						whereSql = whereSql
								+ " and ta.parentareaid in (select tsa.areaid from taw_system_area tsa where tsa.parentareaid="
								+ areaId + ")";
					}

					// ordercode=2
					if ("2".equals(orderCode)) {
						whereSql = whereSql + " and ta.parentareaid=" + areaId;
					}
					// ordercode=3
					if ("3".equals(orderCode)) {
						whereSql = whereSql + " and ta.areaid=" + areaId;
					}
				}
			}

			// }
		}
		if (!"".equals(workerid) && null != workerid) {
			String process_instance_id = "";

			List<HistoricTaskInstance> listw = historyService
					.createHistoricTaskInstanceQuery().taskAssignee(workerid)
					.list();

			for (int j = 0; j < listw.size(); j++) {

				List<HistoricTaskInstance> historicTasks = historyService
						.createHistoricTaskInstanceQuery().processInstanceId(
								listw.get(j).getProcessInstanceId())
						.orderByTaskId().desc().listPage(0, 2);

				if ("completed".equals(historicTasks.get(0).getDeleteReason())) {
					if (historicTasks.get(0).getAssignee().equals(workerid)) {

						if ("".equals(process_instance_id)) {

							process_instance_id = listw.get(j)
									.getProcessInstanceId();
						} else {

							process_instance_id += ","
									+ listw.get(j).getProcessInstanceId();
						}
					}
				} else {
					if (historicTasks.get(1).getAssignee().equals(workerid)) {

						if ("".equals(process_instance_id)) {

							process_instance_id = listw.get(j)
									.getProcessInstanceId();
						} else {

							process_instance_id += ","
									+ listw.get(j).getProcessInstanceId();
						}
					}
				}

			}

			if (!process_instance_id.equals("")) {

				whereSql = whereSql + " and m.process_instance_id in ("
						+ process_instance_id + ")";
			}
		}
		String sql = " select process_instance_id, theme, initiator, deptid, send_time,rownum from("
				+ " select rownum r,s.* from(select m.process_instance_id,m.theme, m.initiator, u2.deptid, m.send_time, rownum rwn"
				+ " from pnr_act_transfer_office_main m "
				+ " left join taw_system_user u on m.task_assignee = u.userid"
				+ " left join taw_system_user u2"
				+ " on m.initiator = u2.userid "
				+ " left join taw_system_dept dt "
				+ " on u.deptid=dt.deptid "
				+ " left join taw_system_area ta "
				+ " on dt.areaid = ta.areaid "
				+ whereSql
				+ " order by m.create_time desc ) s)where r >"
				+ beginNum
				+ " and r<=" + endNum;
		List<Map> list = this.getJdbcTemplate().queryForList(sql);

		for (Map map : list) {
			TaskModel taskModel = new TaskModel();
			taskModel.setDeptId(map.get("DEPTID").toString());
			taskModel.setProcessInstanceId(map.get("process_instance_id")
					.toString());
			taskModel.setTheme(map.get("theme").toString());
			taskModel.setInitiator(map.get("INITIATOR").toString());
			try {
				DateFormat dateFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				taskModel.setSendTime(dateFormat.parse(map.get("SEND_TIME")
						.toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<HistoricTaskInstance> historicTasks = historyService
					.createHistoricTaskInstanceQuery().processInstanceId(
							taskModel.getProcessInstanceId()).orderByTaskId()
					.desc().listPage(0, 2);

			List<Task> taskList = taskService.createTaskQuery()
					.processInstanceId(taskModel.getProcessInstanceId())
					.active().list();

			if (taskList != null && taskList.size() == 1) {
				Task task = taskList.get(0);
				taskModel.setStatusName(task.getName());
				taskModel.setTaskId(task.getId());
				taskModel.setExecutor(task.getAssignee());
			}

			if ("delete".equals(historicTasks.get(0).getDeleteReason())) {
				taskModel.setExecutor(historicTasks.get(1).getAssignee());
				taskModel.setStatusName(CommonUtils.taskDetele);
			} else if ("completed".equals(historicTasks.get(0)
					.getDeleteReason())) {

				taskModel.setExecutor(historicTasks.get(0).getAssignee());
				taskModel.setStatusName(CommonUtils.taskComplete);
			} else {
				taskModel.setExecutor(historicTasks.get(1).getAssignee());
			}

			r.add(taskModel);
		}
		return r;
	}

	public String getLoginPersonStatus(String userId,
			PnrTransferOffice pnrTransferOffice) {
		String firstPerson = pnrTransferOffice.getOneInitiator();
		String secondPerson = pnrTransferOffice.getSecondInitiator();
		String thirdPerson = pnrTransferOffice.getInitiator();
		String forthPerson = pnrTransferOffice.getTaskAssignee();
		if (userId.equals(firstPerson)) {
			return "第一派单人";
		} else if (userId.equals(secondPerson)) {
			return "第二派单人";
		} else if (userId.equals(thirdPerson)) {
			return "外包";
		} else if (userId.equals(forthPerson)) {
			return "施工队";
		}
		return "";
	}

	
	@Override
	public String getLoginPersonStatusToPrecheck(String userId,
			PnrTransferOffice pnrTransferOffice) {
		//需求发起人
		String createPerson = pnrTransferOffice.getCreateWork();
		//方案制定
		String coutryCSJ = pnrTransferOffice.getCountryCSJ();
		//市传输局
		String cityCSJ = pnrTransferOffice.getCityCSJ();
		//市公司
		String citySGS = pnrTransferOffice.getCityGS();
		//代维公司
		String daiweiPerson = pnrTransferOffice.getInitiator();
		
		if(userId.equals(createPerson)){
			return "需求发起";
		}else if(userId.equals(coutryCSJ)){
			return "方案制定";
		}else if(userId.equals(cityCSJ)){
			return "市传输局审批";
		}else if(userId.equals(citySGS)){
			return "市公司审批";
		}else if(userId.equals(daiweiPerson)){
			return "代维公司";
		}
		return "";
	}

	@Override
	public String getLoginPersonStatusToNewPrecheck(String userId,
			PnrTransferOffice pnrTransferOffice) {
		//发单人
		String createPerson = pnrTransferOffice.getOneInitiator();
		//市线路人员
		String cityLineExamine = pnrTransferOffice.getCityCSJ() ;
		//市运维人员
		String cityManageExamine=pnrTransferOffice.getCityGS();
		//省线路人员
		String provinceLineExamine= pnrTransferOffice.getProvinceLine();
		//省运维人员
		String provinceManageExamine=pnrTransferOffice.getProvinceManage();
		//代维公司人员
		String daiwei = pnrTransferOffice.getInitiator();
		if (createPerson != null && userId.equals(createPerson)) {
			return "createPerson";
		} else if (cityLineExamine != null && userId.equals(cityLineExamine)) {
			return "cityLineExamine";
		} else if (cityManageExamine != null && userId.equals(cityManageExamine)) {
			return "cityLineExamine";
		} else if (provinceLineExamine != null && userId.equals(provinceLineExamine)) {
			return "provinceLineExamine";
		} else if (provinceManageExamine != null && userId.equals(provinceManageExamine)) {
			return "provinceManageExamine";
		}else if (daiwei != null && userId.equals(daiwei)) {
			return "daiwei";
		}else{
			return "";
		}
	}

	@Override
	public TransferMaleGuestFlag getMaleGuestFlagData(String workOrderNo) {

		TransferMaleGuestFlag maleGuestFlag = new TransferMaleGuestFlag();
		List<Map> list = null;
		if (workOrderNo != null && !"".equals(workOrderNo)) {
			// 存工单编号
			maleGuestFlag.setConfCRMTicketNo(workOrderNo);
			String sql = "select * from pnr_act_transfer_office_main m where m.male_guest_number ='"
					+ workOrderNo + "'";
			list = this.getJdbcTemplate().queryForList(sql);

			if (list != null && !list.isEmpty() && list.size() != 0) {
				// 获得流程号
				String process = (String) list.get(0)
						.get("PROCESS_INSTANCE_ID");

				// 根据流程号确定工单现处节点和处理人
				if (process != null && !"".equals(process)) {
					String processSql = "select * from act_ru_task ar where ar.proc_inst_id_='"
							+ process + "'";
					List<Map> processList = this.getJdbcTemplate()
							.queryForList(processSql);
					if (!processList.isEmpty() && processList != null
							&& processList.size() != 0) {
						String processTask = (String) processList.get(0).get(
								"TASK_DEF_KEY_");
						// 审核工单、故障处理、派发公客工单
						if ("newMaleGuest".equals(processTask)) {
							maleGuestFlag.setFieldContent("待派发");
						} else if ("auditor".equals(processTask)) {
							maleGuestFlag.setFieldContent("待处理");
						} else if ("audit".equals(processTask)) {
							maleGuestFlag.setFieldContent("待审核");
						} else {
							maleGuestFlag.setFieldContent("已归档");
						}
						String userName = (String) processList.get(0).get(
								"ASSIGNEE_");
						String userSql = "select tu.username,ta.areaname,tu.mobile from taw_system_user tu ,taw_system_dept td,taw_system_area ta "
								+ " where tu.deptid = td.deptid"
								+ " and td.areaid = ta.areaid"
								+ " and tu.userid = '" + userName + "'";
						List<Map> userList = this.getJdbcTemplate()
								.queryForList(userSql);

						if (!userList.isEmpty() && userList != null
								&& userList.size() != 0) {
							String workSheetSymbol = userList.get(0).get(
									"AREANAME")
									+ "  " + userList.get(0).get("USERNAME");
							maleGuestFlag.setWorkSheetSymbol(workSheetSymbol);
							maleGuestFlag.setFieldFlag((String) userList.get(0)
									.get("MOBILE"));
						}
					}
				}
			}
		} else {
			maleGuestFlag = null;
		}

		return maleGuestFlag;
	}

	@Override
	public TransferMaleGuestReturn getMaleGuestReturnData(String workOrderNo) {
		TransferMaleGuestReturn maleGuest = new TransferMaleGuestReturn();

		if (workOrderNo != null && !"".equals(workOrderNo)) {
			String sql = "select h1.receive_time,h1.receive_person,u1.username,h1.fault_cause,h1.handle_description from pnr_act_transfer_office_main m1" +
					" left join pnr_act_transfer_office_handle h1 " +
					" on m1.process_instance_id=h1.process_instance_id" +
					" left join taw_system_user u1 on" +
					" h1.receive_person = u1.userid" +
					" where m1.male_guest_number='"+workOrderNo+"'  order by h1.receive_time desc";
			List<Map> list = this.getJdbcTemplate().queryForList(sql);
			if (list != null&&!list.isEmpty()   && list.size() != 0) {
				String returnResult = (String) list.get(0).get("RETURN_RESULT");
					String setReason_id = (String) list.get(0).get(
					"FAULT_CAUSE");
					String fault_Name = "";
					
					if("2043".equals(setReason_id)){
						fault_Name="主干电缆障碍-宽带";
					}else if("2042".equals(setReason_id)){
						fault_Name="交接分线设备障碍-宽带";
					}else if("2044".equals(setReason_id)){
						fault_Name="光缆故障-宽带";
					}else if("2048".equals(setReason_id)){
						fault_Name="电缆被盗-宽带";
					}else if("2046".equals(setReason_id)){
						fault_Name="线路割接影响-宽带";
					}else if("2041".equals(setReason_id)){
						fault_Name="配线电缆故障-宽带";
					}else if("2111".equals(setReason_id)){
						fault_Name="主干、配线铜线距离超长-宽带";
					}else if("507".equals(setReason_id)){
						fault_Name="分光器-宽带";
					}else if("509".equals(setReason_id)){
						fault_Name="光电缆-宽带";
					}else if("391".equals(setReason_id)){
						fault_Name="主干故障-固话";
					}else if("392".equals(setReason_id)){
						fault_Name="配线故障-固话";
					}else if("743".equals(setReason_id)){
						fault_Name="电缆被盗-固话";
					}else if("9".equals(setReason_id)){
						fault_Name="光电缆-固话";
					}else if("7".equals(setReason_id)){
						fault_Name="分光器-固话";
					}else if("2".equals(setReason_id)){
						fault_Name="障碍非当前分局责任";
					}else if("3".equals(setReason_id)){
						fault_Name="障碍非传输局责任";
					}else if("87".equals(setReason_id)){
						fault_Name="其他";
					}
					maleGuest.setConfCRMTicketNo(workOrderNo);
					if("2".equals(setReason_id)|| "3".equals(setReason_id)){
						// 回单标示
						maleGuest.setFlag(setReason_id);
					}else{
						// 回单标示
						maleGuest.setFlag("1");
					}
					// 回单时间
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						maleGuest.setBack_dt(list.get(0).get(
						"RECEIVE_TIME")==null?"":list.get(0).get(
								"RECEIVE_TIME").toString());
					// 处理人姓名id
					maleGuest.setBack_userid((String) list.get(0).get(
							"RECEIVE_PERSON"));
					// 处理人姓名
					maleGuest.setBack_username((String) list.get(0).get(
							"USERNAME"));
					// 故障原因id
					maleGuest.setReason_id(setReason_id);
					// 描述
					maleGuest.setBack_info((String) list.get(0).get(
							"HANDLE_DESCRIPTION"));
					
					// 故障原因
					maleGuest.setReason_name(fault_Name);
				} 
			} else {
				maleGuest = null;
		} 
		return maleGuest;
	}

	public List<PersonNameUrlForm> getLoginRole(String userId) {
		List<PersonNameUrlForm> roleList = new ArrayList<PersonNameUrlForm>();
		/*
		 * String sql = "select distinct tso.name , tso.url ,tso.orderby as
		 * orderby47_" + " from taw_system_priv_operation tso,
		 * taw_system_priv_menuitem tm" + " where tso.code = tm.code" + " and
		 * (tm.menuid in" + " (select distinct ta.privid" + " from
		 * taw_system_priv_assign ta, taw_system_priv_menu tu" + " where
		 * (ta.objectid = '"+userId+"' and ta.assigntype = '2' or" + "
		 * ta.objectid = '203030101' and ta.assigntype = '1' or" + " ta.objectid =
		 * '402881fb42c1e5320142c3177be306bf' and" + " ta.assigntype = '3')" + "
		 * and ta.privid = tu.privid" + " and (tu.nature is null or tu.nature =
		 * '1')))" + " and tso.parentcode = '9616'" + " and (tso.isApp = '1' or
		 * tso.isApp = '0')" + " and tso.hided <> '1'" + " and tso.deleted <>
		 * '1'" + " and (tso.loginType is null or tso.loginType = '0')" + "
		 * order by tso.orderby";
		 */
		String sql = "select distinct ppp.name,ppp.url , ppp.orderby from taw_system_priv_menuitem pitem "
				+ " left join taw_system_priv_operation ppp on pitem.code=ppp.code"
				+ " where ppp.parentcode="
				+ " (select tso.parentcode from taw_system_priv_operation tso"
				+ " where tso.url ='/activiti/pnrTransferOffice/pnrTransferOffice.do?method=newTask')"
				+ " and pitem.menuid in ("
				+ " select pg.privid from taw_system_priv_assign pg"
				+ " left join  taw_system_priv_menu pm on pg.privid = pm.privid"
				+ " where pg.objectid in ("
				+ " select u.deptid from taw_system_user u where u.userid='"
				+ userId
				+ "'"
				+ " union all"
				+ " select wce.subroleid from taw_system_userrefrole wce"
				+ " where wce.userid='"
				+ userId
				+ "'"
				+ " )"
				+ " )"
				+ " and (ppp.isApp = '1' or ppp.isApp = '0')"
				+ " and ppp.hided <> '1'"
				+ " and ppp.deleted <> '1'"
				+ " and (ppp.loginType is null or ppp.loginType = '0')"
				+ " order by ppp.orderby";
		if (userId == null || "".equals(userId)) {
			roleList = null;
		} else {
			List<Map> list = this.getJdbcTemplate().queryForList(sql);

			if (list != null && !list.isEmpty() && list.size() > 0) {
				for (Map map : list) {
					PersonNameUrlForm oneRole = new PersonNameUrlForm();
					String name = (String) map.get("NAME");
					String url = (String) map.get("URL");
					oneRole.setName(name);
					oneRole.setUrl(url);
					roleList.add(oneRole);
				}
			} else {
				roleList = null;
			}
		}

		return roleList;
	}

	public List<WorkOrderStatisticsModel> transferOfficeStatistics() {
		List<WorkOrderStatisticsModel> r = new ArrayList<WorkOrderStatisticsModel>();

		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.DAY_OF_MONTH, 1);
		String firstDay = (new SimpleDateFormat("yyyy-MM-dd").format(cal
				.getTime()));
		cal.roll(Calendar.DAY_OF_MONTH, -1);
		String lastDay = (new SimpleDateFormat("yyyy-MM-dd").format(cal
				.getTime()));
		String whereSql = " where to_char(create_time,'yyyy-mm-dd') >= '"
				+ firstDay + "' and to_char(create_time,'yyyy-mm-dd')<= '"
				+ lastDay
				+ "' and m1.themeinterface='transferOffice' and m1.state=5";

		String sumNumSql = "select a1.parentareaid, count(m1.id) as sumNum from pnr_act_transfer_office_main  m1  left join taw_system_user u on u.userid = m1.task_assignee left join taw_system_dept d1  on u.deptid = d1.deptid left join taw_system_area a1 on d1.areaid = a1.areaid";
		String unfiledNumSql = "select a1.parentareaid,sum(decode(archiving_time, '', 1, null, 1, 0)) as unfiledNum from  pnr_act_transfer_office_main  m1  left join taw_system_user u on u.userid = m1.task_assignee left join taw_system_dept d1  on u.deptid = d1.deptid left join taw_system_area a1 on d1.areaid = a1.areaid";
		String overtimeNumSql = "select a1.parentareaid,sum(decode(sign(nvl(last_reply_time, sysdate) - end_time),1,1,-1,0,0)) as overtimeNum  from pnr_act_transfer_office_main  m1  left join taw_system_user u on u.userid = m1.task_assignee left join taw_system_dept d1  on u.deptid = d1.deptid left join taw_system_area a1 on d1.areaid = a1.areaid";
		String endSelectSql1 = " group by a1.parentareaid";
		String sql = "select area.areaid,area.areaname,a.sumNum,b.unfiledNum,c.overtimeNum  from "
				+ " (select areaid,areaname from taw_system_area where parentareaid=28) area left join"
				+ " ("
				+ sumNumSql
				+ whereSql
				+ endSelectSql1
				+ ") a on area.areaid=a.parentareaid left join"
				+ " ("
				+ unfiledNumSql
				+ whereSql
				+ endSelectSql1
				+ ") b on area.areaid=b.parentareaid left join"
				+ " ("
				+ overtimeNumSql
				+ whereSql
				+ ""
				+ endSelectSql1
				+ ") c on area.areaid=c.parentareaid   order by nlssort(area.areaname,'NLS_SORT=SCHINESE_PINYIN_M')";
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		for (Map map : list) {
			WorkOrderStatisticsModel workOrderStatisticsModel = new WorkOrderStatisticsModel();
			workOrderStatisticsModel.setCity(map.get("areaid").toString());
			workOrderStatisticsModel
					.setCityName(map.get("areaname").toString());
			if (map.get("sumNum") == null) {
				workOrderStatisticsModel.setSumNum(0);
			} else {
				workOrderStatisticsModel.setSumNum(Integer.parseInt(map.get(
						"sumNum").toString()));
			}
			if (map.get("overtimeNum") == null) {
				workOrderStatisticsModel.setOvertimeNum(0);
			} else {
				workOrderStatisticsModel.setOvertimeNum(Integer.parseInt(map
						.get("overtimeNum").toString()));
			}
			if (map.get("unfiledNum") == null) {
				workOrderStatisticsModel.setUnfiledNumber(0);
			} else {
				workOrderStatisticsModel.setUnfiledNumber(Integer.parseInt(map
						.get("unfiledNum").toString()));
			}

			if (workOrderStatisticsModel.getSumNum() == 0) {
				workOrderStatisticsModel.setArchiveNumber(0);
			} else {
				if (workOrderStatisticsModel.getUnfiledNumber() == 0) {
					workOrderStatisticsModel
							.setArchiveNumber(workOrderStatisticsModel
									.getSumNum());
				} else {
					workOrderStatisticsModel
							.setArchiveNumber(workOrderStatisticsModel
									.getSumNum()
									- workOrderStatisticsModel
											.getUnfiledNumber());
				}
			}
			if (workOrderStatisticsModel.getSumNum() != 0
					&& workOrderStatisticsModel.getOvertimeNum() != 0) {
				workOrderStatisticsModel
						.setOvertimeRate(this.calculateThePercentage(
								new Double(workOrderStatisticsModel
										.getOvertimeNum()), new Double(
										workOrderStatisticsModel.getSumNum())));
			} else {
				workOrderStatisticsModel.setOvertimeRate("0%");
			}

			r.add(workOrderStatisticsModel);
		}
		WorkOrderStatisticsModel all = new WorkOrderStatisticsModel();
		all.setCityName("全省");
		all.setCity("28");
		for (WorkOrderStatisticsModel workOrderStatisticsModel : r) {
			all.setSumNum(all.getSumNum()
					+ workOrderStatisticsModel.getSumNum());
			all.setOvertimeNum(all.getOvertimeNum()
					+ workOrderStatisticsModel.getOvertimeNum());
			all.setUnfiledNumber(all.getUnfiledNumber()
					+ workOrderStatisticsModel.getUnfiledNumber());
			all.setArchiveNumber(all.getArchiveNumber()
					+ workOrderStatisticsModel.getArchiveNumber());
		}
		if (all.getSumNum() != 0 && all.getOvertimeNum() != 0) {
			all.setOvertimeRate(this.calculateThePercentage(new Double(all
					.getOvertimeNum()), new Double(all.getSumNum())));
		} else {
			all.setOvertimeRate("0%");
		}
		r.add(all);
		return r;
	}

	public String calculateThePercentage(Double a, Double b) {
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(2);
		NumberFormat nf1 = NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(0);
		if (a == null || a.equals(0) || a.equals(0.0) || a.equals(0.00)) {
			return nf1.format(0);
		}
		if (b == null || b.equals(0) || b.equals(0.0) || a.equals(0.00)) {
			return nf1.format(0);
		}
		Double d = a / b;
		if (d == null || d.equals(100) || d.equals(100.0) || a.equals(100.00)) {
			return nf1.format(100);
		}
		String r = nf.format(d);
		return r;
	}

	public String getMaleGuestNumberByThemeId(String themeId) {
		String maleGuestNumber = "";
		if (themeId != null && !"".equals(themeId = themeId.trim())) {
			String sql = "select pm.male_guest_number from pnr_act_transfer_office_main pm  where pm.id='"
					+ themeId + "'";
			List<Map> list = this.getJdbcTemplate().queryForList(sql);
			if (list != null && !list.isEmpty() && list.size() > 0) {
				if (list.get(0).get("MALE_GUEST_NUMBER") != null) {

					maleGuestNumber = list.get(0).get("MALE_GUEST_NUMBER")
							.toString();
				}
			}
		}
		return maleGuestNumber;
	}

	public List<Map> getOrderCodeByUserId(String userId) {
		String sql = "select a.ordercode,a.areaid from taw_system_user u, taw_system_dept d, taw_system_area a where u.deptid = d.deptid"
				+ " and   d.areaid = a.areaid"
				+ " and   u.userid = '"
				+ userId
				+ "'";
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		return list;
	}

	/**
	 * 工单管理-传输局工单-抢修工单-待回复工单 集合条数
	 * 
	 * @param userId
	 *            用户ID
	 * @param sendStartTime
	 *            派单开始时间
	 * @param sendEndTime
	 *            派单结束时间
	 * @param wsNum
	 *            工单号
	 * @param wsTitle
	 *            工单主题
	 * @param status
	 *            当前状态
	 * @return
	 */
	public int getToreplyJobOfEmergencyJobCount(String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status) {
		String sql = "";
//		String selectSql = "select count(*) as total from (select  RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_ WHERE RES.ASSIGNEE_ = "
//				+ "?"
//				+ " and D.KEY_ = 'myTransferProcess' and RES.Suspension_State_ = 1) t,pnr_act_transfer_office_main m,taw_system_user u where t.proc_inst_id_ = m.process_instance_id and m.task_assignee = u.userid(+)";
		String selectSql = 
			"select count(m.id) as total" +
			"  from pnr_act_transfer_office_main m, taw_system_user u" + 
			" where m.task_assignee = u.userid(+)" + 
			"   and m.themeinterface = 'transferOffice'" + 
			"   and m.assignee = ?";
		
		ArrayList whereList=new ArrayList();
		
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
			whereSql += " and instr(m.theme,?)>0";
			whereList.add(wsTitle);
		}
		if (status != null && !status.equals("")) {
			//whereSql += " and m.state = '" + status + "'";
			whereSql += " and m.task_def_key =?";
			whereList.add(status);

		}else{
			whereSql += " and m.task_def_key in('newTask','transferTask','epibolyTask','transferHandle')";
		}
//		if (status != null && !status.equals("")) {
//			//whereSql += " and m.state = '" + status + "'";
//			whereSql += " and t.task_def_key_ =?";
//			whereList.add(status);
//			
//		}
		sql = selectSql + whereSql;
		
		Object args[]=whereList.toArray();

		System.out.println("====="+sql);
		int size = this.getJdbcTemplate().queryForInt(sql,args);
		return size;

	}

	/**
	 * 工单管理-传输局工单-抢修工单-待回复工单 集合
	 * 
	 * @param userId
	 *            用户ID
	 * @param sendStartTime
	 *            派单开始时间
	 * @param sendEndTime
	 *            派单结束时间
	 * @param wsNum
	 *            工单号
	 * @param wsTitle
	 *            工单主题
	 * @param status
	 *            当前状态
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getToreplyJobOfEmergencyJobList(String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status, int firstResult, int endResult,
			int pageSize) {
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
//		String selectSql = "select t.id_ as TaskId,m.initiator as Initiator,m.one_initiator as OneInitiator,m.process_instance_id as ProcessInstanceId,m.send_time as SendTime,m.theme as Theme,m.task_assignee,m.state as State,u.deptid as DeptId, m.second_initiator as SecondInitiator " +
//				" from (select  RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_ WHERE RES.ASSIGNEE_ = "
//				+ "?"
//				+ " and D.KEY_ = 'myTransferProcess' and RES.Suspension_State_ = 1) t,pnr_act_transfer_office_main m,taw_system_user u where t.proc_inst_id_ = m.process_instance_id and m.task_assignee = u.userid(+)";
		String selectSql =
			"select m.task_id             as TaskId," +
			"       m.initiator           as Initiator," + 
			"       m.one_initiator       as OneInitiator," + 
			"       m.process_instance_id as ProcessInstanceId," + 
			"       m.send_time           as SendTime," + 
			"       m.theme               as Theme," + 
			"       m.task_assignee," + 
			"       m.state               as State," + 
			"       u.deptid              as DeptId," + 
			"       m.second_initiator    as SecondInitiator," + 
			"       m.task_def_key," + 
			"       m.task_def_key_name" + 
			"  from pnr_act_transfer_office_main m, taw_system_user u" + 
			" where m.task_assignee = u.userid(+)" + 
			"   and m.themeinterface = 'transferOffice'" + 
			"   and m.assignee = ?";

		ArrayList whereList=new ArrayList();
		
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
			whereSql += " and instr(m.theme,?)>0";
			whereList.add(wsTitle);
		}
		if (status != null && !status.equals("")) {
			//whereSql += " and m.state = '" + status + "'";
			whereSql += " and m.task_def_key =?";
			whereList.add(status);

		}else{
			whereSql += " and m.task_def_key in('newTask','transferTask','epibolyTask','transferHandle')";
		}
//		if (status != null && !status.equals("")) {
//			//whereSql += " and m.state = '" + status + "'";
//			whereSql += " and t.task_def_key_ =?";
//			whereList.add(status);
//			
//		}
		sql += selectSql + whereSql
				+ " order by m.send_time desc) temp1 where rownum <=?) temp2 where temp2.num >?";
//		sql += selectSql + whereSql
//		+ " order by t.create_time_ desc) temp1 where rownum <=?) temp2 where temp2.num >?";
		
		whereList.add(endResult * pageSize);
		whereList.add(firstResult * pageSize);
		
		Object values[]=whereList.toArray();
		
		System.out.println("---抢修工单="+sql);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,values);
		for (Map map : list) {
			TaskModel model = new TaskModel();
			if(map.get("TaskId")!=null){
				model.setTaskId(map.get("TaskId").toString());
			}
			if(map.get("Initiator")!=null){
				model.setInitiator(map.get("Initiator").toString());
			}
			if(map.get("OneInitiator")!=null){
				model.setOneInitiator(map.get("OneInitiator").toString());
			}
			if(map.get("SecondInitiator")!=null){
				model.setSecondInitiator(map.get("SecondInitiator").toString());
			}
			if(map.get("ProcessInstanceId")!=null){
				model.setProcessInstanceId(map.get("ProcessInstanceId").toString());
			}
			
			if (map.get("SendTime") != null) {
				if (!map.get("SendTime").toString().equals("")) {
					try {
						model.setSendTime(format.parse(map.get("SendTime")
								.toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

			}
			if(map.get("Theme")!=null){
				model.setTheme(map.get("Theme").toString());
			}
			if(map.get("DeptId")!=null){
				model.setDeptId(map.get("DeptId").toString());
			}
			if(map.get("State")!=null){
				model.setState(Integer.parseInt(map.get("State").toString()));
			}
			if(map.get("task_def_key")!=null){
				String defKey=map.get("task_def_key").toString();
				model.setTaskDefKey(defKey);
				if (defKey.equals("newTask") || defKey.equals("transferTask")
						|| defKey.equals("transferAudit")
						|| defKey.equals("audit")) {
					model.setInitiator(model.getOneInitiator()==null?"":model.getOneInitiator());
				}
			}
			if(map.get("task_def_key_name")!=null){
				model.setStatusName(map.get("task_def_key_name").toString());
			}
			
			r.add(model);

		}

		return r;

	}
	
	/**
	 * 工单管理-传输局工单-预检预修工单-待回复工单 集合条数
	 * 
	 * @param userId
	 *            用户ID
	 * @param sendStartTime
	 *            派单开始时间
	 * @param sendEndTime
	 *            派单结束时间
	 * @param wsNum
	 *            工单号
	 * @param wsTitle
	 *            工单主题
	 * @param status
	 *            当前状态
	 * @return
	 */
	public int getToreplyJobOfPreflightPreparationJobCount(String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status){
		String sql = "";
		String selectSql = "select count(*) as total from (select distinct RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_ WHERE RES.ASSIGNEE_ = '"
				+ userId
				+ "' and D.KEY_ = 'myTransferInterfaceProcess' and RES.Suspension_State_ = 1) t,pnr_act_transfer_office_main m,taw_system_user u where t.proc_inst_id_ = m.process_instance_id and m.task_assignee = u.userid(+) and m.Themeinterface = 'interface'";
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
			//whereSql += " and m.state = '" + status + "'";
			whereSql += " and t.task_def_key_ = '"+status+"'";

		}
		sql = selectSql + whereSql;


		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		return Integer.parseInt(list.get(0).get("total").toString());

	}

	/**
	 * 工单管理-传输局工单-预检预修工单-待回复工单 集合
	 * 
	 * @param userId
	 *            用户ID
	 * @param sendStartTime
	 *            派单开始时间
	 * @param sendEndTime
	 *            派单结束时间
	 * @param wsNum
	 *            工单号
	 * @param wsTitle
	 *            工单主题
	 * @param status
	 *            当前状态
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TaskModel> getToreplyJobOfPreflightPreparationJobList(String userId,
			String sendStartTime, String sendEndTime, String wsNum,
			String wsTitle, String status, int firstResult, int endResult,
			int pageSize){
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		String selectSql = "select t.id_ as TaskId,m.initiator as Initiator,m.one_initiator as OneInitiator,m.process_instance_id as ProcessInstanceId,m.send_time as SendTime,m.theme as Theme,m.task_assignee,m.state as State,u.deptid as DeptId from (select distinct RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_ WHERE RES.ASSIGNEE_ = '"
				+ userId
				+ "' and D.KEY_ = 'myTransferInterfaceProcess' and RES.Suspension_State_ = 1) t,pnr_act_transfer_office_main m,taw_system_user u where t.proc_inst_id_ = m.process_instance_id and m.task_assignee = u.userid(+) and m.Themeinterface = 'interface'";
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
			//whereSql += " and m.state = '" + status + "'";
			whereSql += " and t.task_def_key_ = '"+status+"'";

		}
		sql += selectSql + whereSql
				+ " order by t.create_time_ desc) temp1 where rownum <="
				+ endResult * pageSize + ") temp2 where temp2.num > "
				+ firstResult * pageSize;


		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		for (Map map : list) {
			TaskModel model = new TaskModel();
			model.setTaskId(map.get("TaskId").toString());
			model.setInitiator(map.get("Initiator").toString());
			model.setOneInitiator(map.get("OneInitiator").toString());
			model.setProcessInstanceId(map.get("ProcessInstanceId").toString());
			if (map.get("SendTime") != null) {
				if (!map.get("SendTime").toString().equals("")) {
					try {
						model.setSendTime(format.parse(map.get("SendTime")
								.toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

			}
			model.setTheme(map.get("Theme").toString());
			model.setDeptId(map.get("DeptId").toString());
			model.setState(Integer.parseInt(map.get("State").toString()));
			r.add(model);

		}

		return r;

	}
	
	/**
	 * 工单管理-"公客-传输局工单"-待回复工单 集合条数
	 * 
	 * @param userId
	 *            用户ID
	 * @param sendStartTime
	 *            派单开始时间
	 * @param sendEndTime
	 *            派单结束时间
	 * @param wsNum
	 *            工单号
	 * @param wsTitle
	 *            工单主题
	 * @param status
	 *            当前状态
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getToreplyJobOfMaleGuestTransmissionBureauJobCount(String userId,
			MaleGuestSelectAttribute selectAttribute){
		
		String siteCd = selectAttribute.getSiteCd();
		ArrayList list = new ArrayList();
		String sql = "";
		String selectSql =	"select count(m.id) as total" +
							"  from pnr_act_transfer_office_main m, taw_system_user u, taw_system_dept d" + 
							" where m.assignee = u.userid(+)" + 
							"   and u.deptid = d.deptid(+)" + 
							"   and m.Themeinterface = 'maleGuest'" + 
							"   and m.state != 8" + 
		"   and nvl(m.male_guest_state,'0') in ('0','3')" + 
		"   and m.task_def_key = 'auditor'" + 
		"   and m.assignee = ?";
//		String selectSql = "select count(*) as total from (select tu.username, RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_ left join taw_system_user tu on tu.userid = RES.ASSIGNEE_ WHERE RES.ASSIGNEE_ = ? and D.KEY_ = 'myMaleGuestProcess' and RES.Suspension_State_ = 1) t,(select k.initiator,k.one_initiator,k.process_instance_id,k.send_time,k.theme,k.barrier_number,k.install_address,decode(k.task_assignee,null,k.initiator,k.task_assignee) as task_assignee,k.state,k.Themeinterface from pnr_act_transfer_office_main k) m,taw_system_user u where t.proc_inst_id_ = m.process_instance_id and m.task_assignee = u.userid(+) and m.Themeinterface = 'maleGuest' and m.state!=8 ";
//		list.add(siteCd);
		list.add(userId);
		String whereSql = "";
		if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >=?";
			list.add(selectAttribute.getBeginTime());
		}
		if (selectAttribute.getEndTime() != null && !selectAttribute.getEndTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?";
			list.add(selectAttribute.getEndTime());
		}
		if (selectAttribute.getMaleGuestNumber() != null && !selectAttribute.getMaleGuestNumber().equals("")) {
			whereSql += " and m.process_instance_id =?";
			list.add(selectAttribute.getMaleGuestNumber().trim());
		}
		if (selectAttribute.getTheme() != null && !selectAttribute.getTheme().equals("")) {
			whereSql += " and instr(m.theme,?)>0 ";
			list.add(selectAttribute.getTheme().trim());
		}
		if (selectAttribute.getStatus() != null && !selectAttribute.getStatus().equals("")) {
			whereSql += " and m.task_def_key = ?";
			list.add(selectAttribute.getStatus());
		}
//		if (selectAttribute.getStatus() != null && !selectAttribute.getStatus().equals("")) {
//			whereSql += " and t.task_def_key_ = ?";
//			list.add(selectAttribute.getStatus());
//		}
		if(selectAttribute.getWsNum()!=null && !"".equals(selectAttribute.getWsNum())){
			whereSql+="  and m.barrier_number =?";
			list.add(selectAttribute.getWsNum());
		}
		if(selectAttribute.getInstallAddress()!=null && !"".equals(selectAttribute.getInstallAddress().trim())){
			whereSql += " and instr(m.install_address,?)>0 ";
			list.add(selectAttribute.getInstallAddress());
		}
		if(selectAttribute.getDept()!=null && !"".equals(selectAttribute.getDept().trim())){
			whereSql += " and instr(d.deptname,?)>0 ";
			list.add(selectAttribute.getDept());
		}
//		if(selectAttribute.getDept()!=null && !"".equals(selectAttribute.getDept().trim())){
//			whereSql += " and instr(u.deptname,?)>0 ";
//			list.add(selectAttribute.getDept());
//		}
		if(selectAttribute.getPerson()!=null && !"".equals(selectAttribute.getPerson().trim())){
			whereSql+="  and u.username =?";
			list.add(selectAttribute.getPerson());
		}
		if(selectAttribute.getCounty()!=null && !"".equals(selectAttribute.getCounty().trim())){
			whereSql+="  and m.country =?";
			list.add(selectAttribute.getCounty());
		}
//		if(selectAttribute.getPerson()!=null && !"".equals(selectAttribute.getPerson().trim())){
//			whereSql+="  and t.username =?";
//			list.add(selectAttribute.getPerson());
//		}
		sql = selectSql + whereSql;

		System.out.println("公客待回复统计数sql="+sql);
		
		Object[] args = list.toArray();
		
		int total = this.getJdbcTemplate().queryForInt(sql, args);
		return total;
	}
	
	public int getToreplyJobOfMaleGuestTransmissionBureauJobNoCount(String userId,
			MaleGuestSelectAttribute selectAttribute){
		
		String siteCd = selectAttribute.getSiteCd();
		ArrayList list = new ArrayList();
		String sql = "";
		String selectSql =	"select count(m.id) as total" +
							"  from pnr_act_transfer_office_main m, taw_system_user u, taw_system_dept d" + 
							" where m.assignee = u.userid(+)" + 
							"   and u.deptid = d.deptid(+)" + 
							"   and m.Themeinterface = 'maleGuest'" + 
//							"   and nvl(m.last_reply_time,sysdate) >=sysdate-4/24" + 
		"   and m.male_guest_state = '1'" + 
		"   and m.task_def_key = 'auditor'" ;
//		String selectSql = "select count(*) as total from (select tu.username, RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_ left join taw_system_user tu on tu.userid = RES.ASSIGNEE_ WHERE RES.ASSIGNEE_ = ? and D.KEY_ = 'myMaleGuestProcess' and RES.Suspension_State_ = 1) t,(select k.initiator,k.one_initiator,k.process_instance_id,k.send_time,k.theme,k.barrier_number,k.install_address,decode(k.task_assignee,null,k.initiator,k.task_assignee) as task_assignee,k.state,k.Themeinterface from pnr_act_transfer_office_main k) m,taw_system_user u where t.proc_inst_id_ = m.process_instance_id and m.task_assignee = u.userid(+) and m.Themeinterface = 'maleGuest' and m.state!=8 ";
//		list.add(siteCd);
		
		String whereSql = "";
		if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >=?";
			list.add(selectAttribute.getBeginTime());
		}
		if (selectAttribute.getEndTime() != null && !selectAttribute.getEndTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?";
			list.add(selectAttribute.getEndTime());
		}
		if (selectAttribute.getMaleGuestNumber() != null && !selectAttribute.getMaleGuestNumber().equals("")) {
			whereSql += " and m.process_instance_id =?";
			list.add(selectAttribute.getMaleGuestNumber().trim());
		}
		if (selectAttribute.getTheme() != null && !selectAttribute.getTheme().equals("")) {
			whereSql += " and instr(m.theme,?)>0 ";
			list.add(selectAttribute.getTheme().trim());
		}
		if (selectAttribute.getStatus() != null && !selectAttribute.getStatus().equals("")) {
			whereSql += " and m.task_def_key = ?";
			list.add(selectAttribute.getStatus());
		}
//		if (selectAttribute.getStatus() != null && !selectAttribute.getStatus().equals("")) {
//			whereSql += " and t.task_def_key_ = ?";
//			list.add(selectAttribute.getStatus());
//		}
		if(selectAttribute.getWsNum()!=null && !"".equals(selectAttribute.getWsNum())){
			whereSql+="  and m.barrier_number =?";
			list.add(selectAttribute.getWsNum());
		}
		if(selectAttribute.getInstallAddress()!=null && !"".equals(selectAttribute.getInstallAddress().trim())){
			whereSql += " and instr(m.install_address,?)>0 ";
			list.add(selectAttribute.getInstallAddress());
		}
		if(selectAttribute.getDept()!=null && !"".equals(selectAttribute.getDept().trim())){
			whereSql += " and instr(d.deptname,?)>0 ";
			list.add(selectAttribute.getDept());
		}
//		if(selectAttribute.getDept()!=null && !"".equals(selectAttribute.getDept().trim())){
//			whereSql += " and instr(u.deptname,?)>0 ";
//			list.add(selectAttribute.getDept());
//		}
		if(selectAttribute.getPerson()!=null && !"".equals(selectAttribute.getPerson().trim())){
			whereSql+="  and u.username =?";
			list.add(selectAttribute.getPerson());
		}
		if(selectAttribute.getCounty()!=null && !"".equals(selectAttribute.getCounty().trim())){
			whereSql+="  and m.country =?";
			list.add(selectAttribute.getCounty());
		}
//		if(selectAttribute.getPerson()!=null && !"".equals(selectAttribute.getPerson().trim())){
//			whereSql+="  and t.username =?";
//			list.add(selectAttribute.getPerson());
//		}
		sql = selectSql + whereSql;

		System.out.println("公客待回复统计数sql="+sql);
		
		Object[] args = list.toArray();
		
		int total = this.getJdbcTemplate().queryForInt(sql, args);
		return total;
	}

	/**
	 * 工单管理-"公客-传输局工单"-待回复工单 集合
	 * 
	 * @param userId
	 *            用户ID
	 * @param sendStartTime
	 *            派单开始时间
	 * @param sendEndTime
	 *            派单结束时间
	 * @param wsNum
	 *            工单号
	 * @param wsTitle
	 *            工单主题
	 * @param status
	 *            当前状态
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TaskModel> getToreplyJobOfMaleGuestTransmissionBureauJobList(String userId,
			MaleGuestSelectAttribute selectAttribute, int firstResult, int endResult,
			int pageSize){
		String siteCd= selectAttribute.getSiteCd();
		ArrayList list1 = new ArrayList();
		String sql = " select temp2.* from (select temp1.*, rownum num from (";
		 
		String selectSql =	"select m.task_id             as TaskId," +
							"       u.username," + 
							"       d.deptname," + 
							"       m.initiator           as Initiator," + 
							"       m.one_initiator       as OneInitiator," + 
							"       m.process_instance_id as ProcessInstanceId," + 
							"       m.send_time           as SendTime," + 
							"       m.theme               as Theme," + 
							"       m.task_assignee," + 
							"       m.state               as State," + 
							"       u.deptid              as DeptId," + 
							"       m.install_address     as Install_address," + 
							"       m.barrier_number      as Barrier_number," + 
							"       m.create_time," + 
							"       m.city," + 
							"       m.connect_person," + 
							"       m.process_limit," + 
							"       m.assignee," + 
							"       m.task_def_key," + 
							"       m.repeat_state" + 
							"  from pnr_act_transfer_office_main m, taw_system_user u, taw_system_dept d" + 
							" where m.assignee = u.userid(+)" + 
							"   and u.deptid = d.deptid(+)" + 
							"   and m.Themeinterface = 'maleGuest'" + 
							"   and m.state != 8" + 
							"   and nvl(m.male_guest_state,'0') in('0','3')" + 
							"   and m.task_def_key = 'auditor'" + 
							"   and m.assignee = ?";

//		String selectSql = " select t.id_ as TaskId,t.username,u.deptname,m.initiator as Initiator,m.one_initiator as OneInitiator,m.process_instance_id as ProcessInstanceId," +
//		" m.send_time as SendTime,m.theme as Theme,m.task_assignee,m.state as State,u.deptid as DeptId,m.install_address as Install_address," +
//		" m.barrier_number as Barrier_number,m.create_time,m.city,m.connect_person,m.process_limit, t.assignee_, t.task_def_key_, m.repeat_state from " +
//		" (select tu.username, RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_  left join taw_system_user tu  on tu.userid = RES.ASSIGNEE_" +
//		" WHERE RES.ASSIGNEE_ = ? and D.KEY_ = 'myMaleGuestProcess' and RES.Suspension_State_ = 1) t,(select k.initiator,k.one_initiator,k.process_instance_id,k.send_time," +
//		" k.theme,k.install_address,k.barrier_number, decode(k.task_assignee,null,k.initiator,k.task_assignee) as task_assignee,k.state,k.Themeinterface," +
//		" k.city, k.create_time, k.connect_person,k.process_limit,k.repeat_state from pnr_act_transfer_office_main k) m,( select tsu.deptid,tsd.deptname,tsu.userid from taw_system_user tsu " +
//		" left join taw_system_dept tsd  on tsu.deptid = tsd.deptid )u " +
//		" where t.proc_inst_id_ = m.process_instance_id and m.task_assignee = u.userid(+) and m.Themeinterface = 'maleGuest' and m.state!=8";
//		list1.add(siteCd);
		list1.add(userId);
		//拼接条件查询
		String whereSql = "";
		if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >=?";
			list1.add(selectAttribute.getBeginTime());
		}
		if (selectAttribute.getEndTime() != null && !selectAttribute.getEndTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?";
			list1.add(selectAttribute.getEndTime());
		}
		if (selectAttribute.getMaleGuestNumber() != null && !selectAttribute.getMaleGuestNumber().equals("")) {
			whereSql += " and m.process_instance_id =?";
			list1.add(selectAttribute.getMaleGuestNumber().trim());
		}
		if (selectAttribute.getTheme() != null && !selectAttribute.getTheme().equals("")) {
			whereSql += " and instr(m.theme,?)>0 ";
			list1.add(selectAttribute.getTheme().trim());
		}
		if (selectAttribute.getStatus() != null && !selectAttribute.getStatus().equals("")) {
			whereSql += " and m.task_def_key = ?";
			list1.add(selectAttribute.getStatus());
		}
//		if (selectAttribute.getStatus() != null && !selectAttribute.getStatus().equals("")) {
//			whereSql += " and t.task_def_key_ = ?";
//			list1.add(selectAttribute.getStatus());
//		}
		if(selectAttribute.getWsNum()!=null && !"".equals(selectAttribute.getWsNum())){
			whereSql+="  and m.barrier_number =?";
			list1.add(selectAttribute.getWsNum());
		}
		if(selectAttribute.getInstallAddress()!=null && !"".equals(selectAttribute.getInstallAddress().trim())){
			whereSql += " and instr(m.install_address,?)>0 ";
			list1.add(selectAttribute.getInstallAddress());
		}
		if(selectAttribute.getDept()!=null && !"".equals(selectAttribute.getDept().trim())){
			whereSql += " and instr(d.deptname,?)>0 ";
			list1.add(selectAttribute.getDept());
		}
//		if(selectAttribute.getDept()!=null && !"".equals(selectAttribute.getDept().trim())){
//			whereSql += " and instr(u.deptname,?)>0 ";
//			list1.add(selectAttribute.getDept());
//		}
		if(selectAttribute.getPerson()!=null && !"".equals(selectAttribute.getPerson().trim())){
			whereSql+="  and u.username =?";
			list1.add(selectAttribute.getPerson());
		}
		if(selectAttribute.getCounty()!=null && !"".equals(selectAttribute.getCounty().trim())){
			whereSql+="  and m.country =?";
			list1.add(selectAttribute.getCounty());
		}
//		if(selectAttribute.getPerson()!=null && !"".equals(selectAttribute.getPerson().trim())){
//			whereSql+="  and t.username =?";
//			list1.add(selectAttribute.getPerson());
//		}
		sql += selectSql + whereSql
				+ " order by m.send_time desc) temp1 where rownum <=?) temp2 where temp2.num > ?";
//		sql += selectSql + whereSql
//		+ " order by t.create_time_ desc) temp1 where rownum <=?) temp2 where temp2.num > ?";
		
		list1.add(endResult * pageSize);
		
		list1.add(firstResult * pageSize);
		Object[] values = list1.toArray();
		
		System.out.println("公客待回复统明细sql="+sql);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql, values);
		for (Map map : list) {
			TaskModel model = new TaskModel();
			if(map.get("TaskId")!=null && !"".equals(map.get("TaskId"))){
				
				model.setTaskId(map.get("TaskId").toString());
			}else{
				model.setTaskId("");
			}
			if(map.get("Initiator")!=null && !"".equals(map.get("Initiator"))){
				model.setInitiator(map.get("Initiator").toString());
			}else{
				model.setInitiator("");
			}
			
			//临时做为空判断，正常OneInitiator的值不可以为空
			if (map.get("OneInitiator") != null){
				model.setOneInitiator(map.get("OneInitiator").toString());
			}else{
				model.setOneInitiator("");
			}		
			model.setProcessInstanceId(map.get("ProcessInstanceId").toString());
			if (map.get("SendTime") != null) {
				if (!map.get("SendTime").toString().equals("")) {
					try {
						model.setSendTime(format.parse(map.get("SendTime")
								.toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

			}
			if(map.get("Theme")!=null && !"".equals(map.get("Theme"))){
				model.setTheme(map.get("Theme").toString());
			}else{
				model.setTheme("");
			}
			if(map.get("DeptId")!=null && !"".equals(map.get("DeptId"))){
				model.setDeptId(map.get("DeptId").toString());
			}else{
				model.setDeptId("");
			}
			if(map.get("State")!=null && !"".equals(map.get("State"))){
				model.setState(Integer.parseInt(map.get("State").toString()));
			}else{
				model.setState(0);
			}
			if(map.get("Install_address")!=null && !"".equals(map.get("Install_address"))){
				model.setInstallAddress(map.get("Install_address").toString());
			}else{
				model.setInstallAddress("");
			}
			if(map.get("Barrier_number")!=null && !"".equals(map.get("Barrier_number"))){
				model.setBarrierNumber(map.get("Barrier_number").toString());
			}else{
				model.setBarrierNumber("");
			}
			if(map.get("create_time")!=null && !"".equals(map.get("create_time"))){
				if (map.get("create_time") != null) {
					if (!map.get("create_time").toString().equals("")) {
						try {
							model.setCreateTime(format.parse(map.get("create_time")
									.toString()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}

				}
			}
			if(map.get("city")!=null && !"".equals(map.get("city"))){
				model.setCity(map.get("city").toString());
			}
			if(map.get("connect_person")!=null &&!"".equals(map.get("connect_person"))){
				model.setConnectPerson(map.get("connect_person").toString());
			}
			if(map.get("process_limit")!=null &&!"".equals(map.get("process_limit"))){
				model.setProcessLimit(map.get("process_limit").toString());
			}
			//工单历时 = 当前时间-故障发生时间：当工单历时大于处理时限，改变颜色
			if(map.get("create_time")!=null && !"".equals(map.get("create_time"))&& map.get("SendTime") != null){
				//两时间做差
				Date startTime = new Date();
				Date endTime = model.getCreateTime();
				long sortTime = startTime.getTime()-endTime.getTime();
				long hours = sortTime / (1000 * 60 * 60 );
				long minutes = sortTime/(1000*60)-hours*60;
				long second = sortTime/1000 - hours*60*60 - minutes*60;
				String newTime = hours+"小时"+minutes+"分钟"+second+"秒";
				model.setWorkTask(newTime);
				if(model.getProcessLimit()!=null&&!"".equals(model.getProcessLimit())){
					long processLimit = Long.parseLong(model.getProcessLimit());
					if(processLimit>hours){//没有超出时限
						model.setChangeColor(true);
					}else{//超出时限
						model.setChangeColor(false);
					}
				}
			}
			if(map.get("assignee")!=null && !"".equals(map.get("assignee"))){
				model.setExecutor(map.get("assignee").toString());
			}
			//状态存储--start
			String state = "";
			if(map.get("task_def_key")!=null && !"".equals(map.get("task_def_key"))&&"newMaleGuest".equals(map.get("task_def_key"))){
				state = "派发工单";
			}else if(map.get("task_def_key")!=null && !"".equals(map.get("task_def_key"))&&"auditor".equals(map.get("task_def_key"))){
				state="故障处理";
			}
			if(map.get("repeat_state")!=null && !"".equals(map.get("repeat_state"))&&"1".equals(map.get("repeat_state").toString().trim())){
				state = "(重修)"+state;
			}
			model.setStatusName(state);
			//--end
			
			r.add(model);

		}

		return r;

	}
	
	public List<TaskModel> getToreplyJobOfMaleGuestTransmissionBureauJobNoList(String userId,
			MaleGuestSelectAttribute selectAttribute, int firstResult, int endResult,
			int pageSize){
		String siteCd= selectAttribute.getSiteCd();
		ArrayList list1 = new ArrayList();
		String sql = " select temp2.* from (select temp1.*, rownum num from (";
		
		String selectSql =	"select m.task_id             as TaskId," +
		"       u.username," + 
		"       d.deptname," + 
		"       m.initiator           as Initiator," + 
		"       m.one_initiator       as OneInitiator," + 
		"       m.process_instance_id as ProcessInstanceId," + 
		"       m.send_time           as SendTime," + 
		"       m.theme               as Theme," + 
		"       m.task_assignee," + 
		"       m.state               as State," + 
		"       u.deptid              as DeptId," + 
		"       m.install_address     as Install_address," + 
		"       m.barrier_number      as Barrier_number," + 
		"       m.create_time," + 
		"       m.city," + 
		"       m.connect_person," + 
		"       m.process_limit," + 
		"       m.assignee," + 
		"       m.task_def_key," + 
		"       m.repeat_state" + 
		"  from pnr_act_transfer_office_main m, taw_system_user u, taw_system_dept d" + 
		" where m.assignee = u.userid(+)" + 
		"   and u.deptid = d.deptid(+)" + 
		"   and m.Themeinterface = 'maleGuest'" + 
//		"   and nvl(m.last_reply_time,sysdate) >=sysdate-4/24 " + 
		"   and m.male_guest_state = '1'" + 
		"   and m.task_def_key = 'auditor'";
		
//		String selectSql = " select t.id_ as TaskId,t.username,u.deptname,m.initiator as Initiator,m.one_initiator as OneInitiator,m.process_instance_id as ProcessInstanceId," +
//		" m.send_time as SendTime,m.theme as Theme,m.task_assignee,m.state as State,u.deptid as DeptId,m.install_address as Install_address," +
//		" m.barrier_number as Barrier_number,m.create_time,m.city,m.connect_person,m.process_limit, t.assignee_, t.task_def_key_, m.repeat_state from " +
//		" (select tu.username, RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_  left join taw_system_user tu  on tu.userid = RES.ASSIGNEE_" +
//		" WHERE RES.ASSIGNEE_ = ? and D.KEY_ = 'myMaleGuestProcess' and RES.Suspension_State_ = 1) t,(select k.initiator,k.one_initiator,k.process_instance_id,k.send_time," +
//		" k.theme,k.install_address,k.barrier_number, decode(k.task_assignee,null,k.initiator,k.task_assignee) as task_assignee,k.state,k.Themeinterface," +
//		" k.city, k.create_time, k.connect_person,k.process_limit,k.repeat_state from pnr_act_transfer_office_main k) m,( select tsu.deptid,tsd.deptname,tsu.userid from taw_system_user tsu " +
//		" left join taw_system_dept tsd  on tsu.deptid = tsd.deptid )u " +
//		" where t.proc_inst_id_ = m.process_instance_id and m.task_assignee = u.userid(+) and m.Themeinterface = 'maleGuest' and m.state!=8";
//		list1.add(siteCd);
		//拼接条件查询
		String whereSql = "";
		if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >=?";
			list1.add(selectAttribute.getBeginTime());
		}
		if (selectAttribute.getEndTime() != null && !selectAttribute.getEndTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?";
			list1.add(selectAttribute.getEndTime());
		}
		if (selectAttribute.getMaleGuestNumber() != null && !selectAttribute.getMaleGuestNumber().equals("")) {
			whereSql += " and m.process_instance_id =?";
			list1.add(selectAttribute.getMaleGuestNumber().trim());
		}
		if (selectAttribute.getTheme() != null && !selectAttribute.getTheme().equals("")) {
			whereSql += " and instr(m.theme,?)>0 ";
			list1.add(selectAttribute.getTheme().trim());
		}
		if (selectAttribute.getStatus() != null && !selectAttribute.getStatus().equals("")) {
			whereSql += " and m.task_def_key = ?";
			list1.add(selectAttribute.getStatus());
		}
//		if (selectAttribute.getStatus() != null && !selectAttribute.getStatus().equals("")) {
//			whereSql += " and t.task_def_key_ = ?";
//			list1.add(selectAttribute.getStatus());
//		}
		if(selectAttribute.getWsNum()!=null && !"".equals(selectAttribute.getWsNum())){
			whereSql+="  and m.barrier_number =?";
			list1.add(selectAttribute.getWsNum());
		}
		if(selectAttribute.getInstallAddress()!=null && !"".equals(selectAttribute.getInstallAddress().trim())){
			whereSql += " and instr(m.install_address,?)>0 ";
			list1.add(selectAttribute.getInstallAddress());
		}
		if(selectAttribute.getDept()!=null && !"".equals(selectAttribute.getDept().trim())){
			whereSql += " and instr(d.deptname,?)>0 ";
			list1.add(selectAttribute.getDept());
		}
//		if(selectAttribute.getDept()!=null && !"".equals(selectAttribute.getDept().trim())){
//			whereSql += " and instr(u.deptname,?)>0 ";
//			list1.add(selectAttribute.getDept());
//		}
		if(selectAttribute.getPerson()!=null && !"".equals(selectAttribute.getPerson().trim())){
			whereSql+="  and u.username =?";
			list1.add(selectAttribute.getPerson());
		}
		if(selectAttribute.getCounty()!=null && !"".equals(selectAttribute.getCounty().trim())){
			whereSql+="  and m.country =?";
			list1.add(selectAttribute.getCounty());
		}
//		if(selectAttribute.getPerson()!=null && !"".equals(selectAttribute.getPerson().trim())){
//			whereSql+="  and t.username =?";
//			list1.add(selectAttribute.getPerson());
//		}
		sql += selectSql + whereSql
		+ " order by m.send_time desc) temp1 where rownum <=?) temp2 where temp2.num > ?";
//		sql += selectSql + whereSql
//		+ " order by t.create_time_ desc) temp1 where rownum <=?) temp2 where temp2.num > ?";
		
		list1.add(endResult * pageSize);
		
		list1.add(firstResult * pageSize);
		Object[] values = list1.toArray();
		
		System.out.println("公客待回复统明细sql="+sql);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql, values);
		for (Map map : list) {
			TaskModel model = new TaskModel();
			if(map.get("TaskId")!=null && !"".equals(map.get("TaskId"))){
				
				model.setTaskId(map.get("TaskId").toString());
			}else{
				model.setTaskId("");
			}
			if(map.get("Initiator")!=null && !"".equals(map.get("Initiator"))){
				model.setInitiator(map.get("Initiator").toString());
			}else{
				model.setInitiator("");
			}
			
			//临时做为空判断，正常OneInitiator的值不可以为空
			if (map.get("OneInitiator") != null){
				model.setOneInitiator(map.get("OneInitiator").toString());
			}else{
				model.setOneInitiator("");
			}		
			model.setProcessInstanceId(map.get("ProcessInstanceId").toString());
			if (map.get("SendTime") != null) {
				if (!map.get("SendTime").toString().equals("")) {
					try {
						model.setSendTime(format.parse(map.get("SendTime")
								.toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				
			}
			if(map.get("Theme")!=null && !"".equals(map.get("Theme"))){
				model.setTheme(map.get("Theme").toString());
			}else{
				model.setTheme("");
			}
			if(map.get("DeptId")!=null && !"".equals(map.get("DeptId"))){
				model.setDeptId(map.get("DeptId").toString());
			}else{
				model.setDeptId("");
			}
			if(map.get("State")!=null && !"".equals(map.get("State"))){
				model.setState(Integer.parseInt(map.get("State").toString()));
			}else{
				model.setState(0);
			}
			if(map.get("Install_address")!=null && !"".equals(map.get("Install_address"))){
				model.setInstallAddress(map.get("Install_address").toString());
			}else{
				model.setInstallAddress("");
			}
			if(map.get("Barrier_number")!=null && !"".equals(map.get("Barrier_number"))){
				model.setBarrierNumber(map.get("Barrier_number").toString());
			}else{
				model.setBarrierNumber("");
			}
			if(map.get("create_time")!=null && !"".equals(map.get("create_time"))){
				if (map.get("create_time") != null) {
					if (!map.get("create_time").toString().equals("")) {
						try {
							model.setCreateTime(format.parse(map.get("create_time")
									.toString()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					
				}
			}
			if(map.get("city")!=null && !"".equals(map.get("city"))){
				model.setCity(map.get("city").toString());
			}
			if(map.get("connect_person")!=null &&!"".equals(map.get("connect_person"))){
				model.setConnectPerson(map.get("connect_person").toString());
			}
			if(map.get("process_limit")!=null &&!"".equals(map.get("process_limit"))){
				model.setProcessLimit(map.get("process_limit").toString());
			}
			//工单历时 = 当前时间-故障发生时间：当工单历时大于处理时限，改变颜色
			if(map.get("create_time")!=null && !"".equals(map.get("create_time"))&& map.get("SendTime") != null){
				//两时间做差
				Date startTime = new Date();
				Date endTime = model.getCreateTime();
				long sortTime = startTime.getTime()-endTime.getTime();
				long hours = sortTime / (1000 * 60 * 60 );
				long minutes = sortTime/(1000*60)-hours*60;
				long second = sortTime/1000 - hours*60*60 - minutes*60;
				String newTime = hours+"小时"+minutes+"分钟"+second+"秒";
				model.setWorkTask(newTime);
				if(model.getProcessLimit()!=null&&!"".equals(model.getProcessLimit())){
					long processLimit = Long.parseLong(model.getProcessLimit());
					if(processLimit>hours){//没有超出时限
						model.setChangeColor(true);
					}else{//超出时限
						model.setChangeColor(false);
					}
				}
			}
			if(map.get("assignee")!=null && !"".equals(map.get("assignee"))){
				model.setExecutor(map.get("assignee").toString());
			}
			//状态存储--start
			String state = "";
			if(map.get("task_def_key")!=null && !"".equals(map.get("task_def_key"))&&"newMaleGuest".equals(map.get("task_def_key"))){
				state = "派发工单";
			}else if(map.get("task_def_key")!=null && !"".equals(map.get("task_def_key"))&&"auditor".equals(map.get("task_def_key"))){
				state="故障处理";
			}
			if(map.get("repeat_state")!=null && !"".equals(map.get("repeat_state"))&&"1".equals(map.get("repeat_state").toString().trim())){
				state = "(重修)"+state;
			}
			model.setStatusName(state);
			//--end
			
			r.add(model);
			
		}
		
		return r;
		
	}
	
	public List<TaskModel> getToreplyJobOfMaleGuestTransmissionBureauJobParentList(String userId,
			MaleGuestSelectAttribute selectAttribute, int firstResult, int endResult,
			int pageSize){
		String processInstanceId= selectAttribute.getProcessInstanceId();
		String siteCd= selectAttribute.getSiteCd();
		ArrayList list1 = new ArrayList();
//		String sql = " select temp2.* from (select temp1.*, rownum num from (";
		String sql = " ";
		 
		String selectSql =	"select m.task_id             as TaskId," +
							"       u.username," + 
							"       d.deptname," + 
							"       m.initiator           as Initiator," + 
							"       m.one_initiator       as OneInitiator," + 
							"       m.process_instance_id as ProcessInstanceId," + 
							"       m.send_time           as SendTime," + 
							"       m.theme               as Theme," + 
							"       m.task_assignee," + 
							"       m.state               as State," + 
							"       u.deptid              as DeptId," + 
							"       m.install_address     as Install_address," + 
							"       m.barrier_number      as Barrier_number," + 
							"       m.create_time," + 
							"       m.city," + 
							"       m.connect_person," + 
							"       m.process_limit," + 
							"       m.assignee," + 
							"       m.task_def_key," + 
							"       m.repeat_state," + 
							"       m.male_guest_state," + 
							"       m.station_name " + 
							"  from pnr_act_transfer_office_main m, taw_system_user u, taw_system_dept d" + 
							" where m.assignee = u.userid(+)" + 
							"   and u.deptid = d.deptid(+)" + 
							"   and m.Themeinterface = 'maleGuest'" + 
							"   and m.state != 8" + 
							"   and m.male_guest_state in ('1','2')" + 
							"   and (m.process_instance_id =? or m.parent_process_instance_id =?)" + 
							"   and m.task_def_key = 'auditor'" ; 
							// +"   and m.assignee = ? ";

//		String selectSql = " select t.id_ as TaskId,t.username,u.deptname,m.initiator as Initiator,m.one_initiator as OneInitiator,m.process_instance_id as ProcessInstanceId," +
//		" m.send_time as SendTime,m.theme as Theme,m.task_assignee,m.state as State,u.deptid as DeptId,m.install_address as Install_address," +
//		" m.barrier_number as Barrier_number,m.create_time,m.city,m.connect_person,m.process_limit, t.assignee_, t.task_def_key_, m.repeat_state from " +
//		" (select tu.username, RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_  left join taw_system_user tu  on tu.userid = RES.ASSIGNEE_" +
//		" WHERE RES.ASSIGNEE_ = ? and D.KEY_ = 'myMaleGuestProcess' and RES.Suspension_State_ = 1) t,(select k.initiator,k.one_initiator,k.process_instance_id,k.send_time," +
//		" k.theme,k.install_address,k.barrier_number, decode(k.task_assignee,null,k.initiator,k.task_assignee) as task_assignee,k.state,k.Themeinterface," +
//		" k.city, k.create_time, k.connect_person,k.process_limit,k.repeat_state from pnr_act_transfer_office_main k) m,( select tsu.deptid,tsd.deptname,tsu.userid from taw_system_user tsu " +
//		" left join taw_system_dept tsd  on tsu.deptid = tsd.deptid )u " +
//		" where t.proc_inst_id_ = m.process_instance_id and m.task_assignee = u.userid(+) and m.Themeinterface = 'maleGuest' and m.state!=8";
		list1.add(processInstanceId);
		list1.add(processInstanceId); 
//		list1.add(siteCd);
		//list1.add(userId);
		//拼接条件查询
		String whereSql = "";
		if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >=?";
			list1.add(selectAttribute.getBeginTime());
		}
		if (selectAttribute.getEndTime() != null && !selectAttribute.getEndTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?";
			list1.add(selectAttribute.getEndTime());
		}
		if (selectAttribute.getCounty() != null && !"".equals(selectAttribute.getCounty())) {
			whereSql += " and m.country = ?";
			list1.add(selectAttribute.getCounty());
		}
		/*if (selectAttribute.getMaleGuestNumber() != null && !selectAttribute.getMaleGuestNumber().equals("")) {
			whereSql += " and m.process_instance_id =?";
			list1.add(selectAttribute.getMaleGuestNumber().trim());
		}*/
		/*if (selectAttribute.getTheme() != null && !selectAttribute.getTheme().equals("")) {
			whereSql += " and instr(m.theme,?)>0 ";
			list1.add(selectAttribute.getTheme().trim());
		}*/
		if (selectAttribute.getStatus() != null && !selectAttribute.getStatus().equals("")) {
			whereSql += " and m.task_def_key = ?";
			list1.add(selectAttribute.getStatus());
		}
//		if (selectAttribute.getStatus() != null && !selectAttribute.getStatus().equals("")) {
//			whereSql += " and t.task_def_key_ = ?";
//			list1.add(selectAttribute.getStatus());
//		}
		if(selectAttribute.getWsNum()!=null && !"".equals(selectAttribute.getWsNum())){
			whereSql+="  and m.barrier_number =?";
			list1.add(selectAttribute.getWsNum());
		}
		if(selectAttribute.getInstallAddress()!=null && !"".equals(selectAttribute.getInstallAddress().trim())){
			whereSql += " and instr(m.install_address,?)>0 ";
			list1.add(selectAttribute.getInstallAddress());
		}
		if(selectAttribute.getDept()!=null && !"".equals(selectAttribute.getDept().trim())){
			whereSql += " and instr(d.deptname,?)>0 ";
			list1.add(selectAttribute.getDept());
		}
//		if(selectAttribute.getDept()!=null && !"".equals(selectAttribute.getDept().trim())){
//			whereSql += " and instr(u.deptname,?)>0 ";
//			list1.add(selectAttribute.getDept());
//		}
		if(selectAttribute.getPerson()!=null && !"".equals(selectAttribute.getPerson().trim())){
			whereSql+="  and u.username =?";
			list1.add(selectAttribute.getPerson());
		}
//		if(selectAttribute.getPerson()!=null && !"".equals(selectAttribute.getPerson().trim())){
//			whereSql+="  and t.username =?";
//			list1.add(selectAttribute.getPerson());
//		}
		sql += selectSql + whereSql
				+ " order by m.male_guest_state";
//		sql += selectSql + whereSql
//		+ " order by t.create_time_ desc) temp1 where rownum <=?) temp2 where temp2.num > ?";
		
//		list1.add(endResult * pageSize);		
//		list1.add(firstResult * pageSize);
		Object[] values = list1.toArray();
		
		System.out.println("公客待回复统明细2566sql="+sql);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql, values);
		for (Map map : list) {
			TaskModel model = new TaskModel();
			if(map.get("TaskId")!=null && !"".equals(map.get("TaskId"))){
				
				model.setTaskId(map.get("TaskId").toString());
			}else{
				model.setTaskId("");
			}
			if(map.get("Initiator")!=null && !"".equals(map.get("Initiator"))){
				model.setInitiator(map.get("Initiator").toString());
			}else{
				model.setInitiator("");
			}
			
			if(map.get("male_guest_state")!=null && !"".equals(map.get("male_guest_state"))){
				model.setMaleGuestState(map.get("male_guest_state").toString());
			}else{
				model.setMaleGuestState("");
			}
			if(map.get("station_name")!=null && !"".equals(map.get("station_name"))){
				model.setStationName(map.get("station_name").toString());
			}else{
				model.setStationName("");
			}
			
			//临时做为空判断，正常OneInitiator的值不可以为空
			if (map.get("OneInitiator") != null){
				model.setOneInitiator(map.get("OneInitiator").toString());
			}else{
				model.setOneInitiator("");
			}		
			model.setProcessInstanceId(map.get("ProcessInstanceId").toString());
			if (map.get("SendTime") != null) {
				if (!map.get("SendTime").toString().equals("")) {
					try {
						model.setSendTime(format.parse(map.get("SendTime")
								.toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

			}
			if(map.get("Theme")!=null && !"".equals(map.get("Theme"))){
				model.setTheme(map.get("Theme").toString());
			}else{
				model.setTheme("");
			}
			if(map.get("DeptId")!=null && !"".equals(map.get("DeptId"))){
				model.setDeptId(map.get("DeptId").toString());
			}else{
				model.setDeptId("");
			}
			if(map.get("State")!=null && !"".equals(map.get("State"))){
				model.setState(Integer.parseInt(map.get("State").toString()));
			}else{
				model.setState(0);
			}
			if(map.get("Install_address")!=null && !"".equals(map.get("Install_address"))){
				model.setInstallAddress(map.get("Install_address").toString());
			}else{
				model.setInstallAddress("");
			}
			if(map.get("Barrier_number")!=null && !"".equals(map.get("Barrier_number"))){
				model.setBarrierNumber(map.get("Barrier_number").toString());
			}else{
				model.setBarrierNumber("");
			}
			if(map.get("create_time")!=null && !"".equals(map.get("create_time"))){
				if (map.get("create_time") != null) {
					if (!map.get("create_time").toString().equals("")) {
						try {
							model.setCreateTime(format.parse(map.get("create_time")
									.toString()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}

				}
			}
			if(map.get("city")!=null && !"".equals(map.get("city"))){
				model.setCity(map.get("city").toString());
			}
			if(map.get("connect_person")!=null &&!"".equals(map.get("connect_person"))){
				model.setConnectPerson(map.get("connect_person").toString());
			}
			if(map.get("process_limit")!=null &&!"".equals(map.get("process_limit"))){
				model.setProcessLimit(map.get("process_limit").toString());
			}
			//工单历时 = 当前时间-故障发生时间：当工单历时大于处理时限，改变颜色
			if(map.get("create_time")!=null && !"".equals(map.get("create_time"))&& map.get("SendTime") != null){
				//两时间做差
				Date startTime = new Date();
				Date endTime = model.getCreateTime();
				long sortTime = startTime.getTime()-endTime.getTime();
				long hours = sortTime / (1000 * 60 * 60 );
				long minutes = sortTime/(1000*60)-hours*60;
				long second = sortTime/1000 - hours*60*60 - minutes*60;
				String newTime = hours+"小时"+minutes+"分钟"+second+"秒";
				model.setWorkTask(newTime);
				if(model.getProcessLimit()!=null&&!"".equals(model.getProcessLimit())){
					long processLimit = Long.parseLong(model.getProcessLimit());
					if(processLimit>hours){//没有超出时限
						model.setChangeColor(true);
					}else{//超出时限
						model.setChangeColor(false);
					}
				}
			}
			if(map.get("assignee")!=null && !"".equals(map.get("assignee"))){
				model.setExecutor(map.get("assignee").toString());
			}
			//状态存储--start
			String state = "";
			if(map.get("task_def_key")!=null && !"".equals(map.get("task_def_key"))&&"newMaleGuest".equals(map.get("task_def_key"))){
				state = "派发工单";
			}else if(map.get("task_def_key")!=null && !"".equals(map.get("task_def_key"))&&"auditor".equals(map.get("task_def_key"))){
				state="故障处理";
			}else if(map.get("task_def_key")!=null && !"".equals(map.get("task_def_key"))&&"acheck".equals(map.get("task_def_key"))){
				state="区县维护中心一次核验";
			}else if(map.get("task_def_key")!=null && !"".equals(map.get("task_def_key"))&&"twoSpotChecks".equals(map.get("task_def_key"))){
				state="区县维护中心二次抽查";
			}else if(map.get("task_def_key")!=null && !"".equals(map.get("task_def_key"))&&"auditReport".equals(map.get("task_def_key"))){
				state="区县维护中心综合报表审核";
			}
			if(map.get("repeat_state")!=null && !"".equals(map.get("repeat_state"))&&"1".equals(map.get("repeat_state").toString().trim())){
				state = "(重修)"+state;
			}
			model.setStatusName(state);
			//--end
			
			r.add(model);

		}

		return r;

	}
	public List<TaskModel> getToreplyJobOfMaleGuestTransmissionBureauJobParentNoList(String userId,
			MaleGuestSelectAttribute selectAttribute, int firstResult, int endResult,
			int pageSize){
		String processInstanceId= selectAttribute.getProcessInstanceId();
		String siteCd= selectAttribute.getSiteCd();
		ArrayList list1 = new ArrayList();
//		String sql = " select temp2.* from (select temp1.*, rownum num from (";
		String sql = " ";
		 
		String selectSql =	"select m.task_id             as TaskId," +
							"       u.username," + 
							"       d.deptname," + 
							"       m.initiator           as Initiator," + 
							"       m.one_initiator       as OneInitiator," + 
							"       m.process_instance_id as ProcessInstanceId," + 
							"       m.send_time           as SendTime," + 
							"       m.theme               as Theme," + 
							"       m.task_assignee," + 
							"       m.state               as State," + 
							"       u.deptid              as DeptId," + 
							"       m.install_address     as Install_address," + 
							"       m.barrier_number      as Barrier_number," + 
							"       m.create_time," + 
							"       m.city," + 
							"       m.connect_person," + 
							"       m.process_limit," + 
							"       m.assignee," + 
							"       m.task_def_key," + 
							"       m.repeat_state," + 
							"       m.male_guest_state," + 
							"       m.station_name " + 
							"  from pnr_act_transfer_office_main m, taw_system_user u, taw_system_dept d" + 
							" where m.assignee = u.userid(+)" + 
							"   and u.deptid = d.deptid(+)" + 
							"   and m.Themeinterface = 'maleGuest'" + 
							"   and m.state != 8" + 
							"   and nvl(m.male_guest_state,'0') in ('0','3')" + 
							"   and m.process_instance_id =?" + 
							"   and m.site_cd = ?" + 
							"   and m.assignee = ? ";

//		String selectSql = " select t.id_ as TaskId,t.username,u.deptname,m.initiator as Initiator,m.one_initiator as OneInitiator,m.process_instance_id as ProcessInstanceId," +
//		" m.send_time as SendTime,m.theme as Theme,m.task_assignee,m.state as State,u.deptid as DeptId,m.install_address as Install_address," +
//		" m.barrier_number as Barrier_number,m.create_time,m.city,m.connect_person,m.process_limit, t.assignee_, t.task_def_key_, m.repeat_state from " +
//		" (select tu.username, RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_  left join taw_system_user tu  on tu.userid = RES.ASSIGNEE_" +
//		" WHERE RES.ASSIGNEE_ = ? and D.KEY_ = 'myMaleGuestProcess' and RES.Suspension_State_ = 1) t,(select k.initiator,k.one_initiator,k.process_instance_id,k.send_time," +
//		" k.theme,k.install_address,k.barrier_number, decode(k.task_assignee,null,k.initiator,k.task_assignee) as task_assignee,k.state,k.Themeinterface," +
//		" k.city, k.create_time, k.connect_person,k.process_limit,k.repeat_state from pnr_act_transfer_office_main k) m,( select tsu.deptid,tsd.deptname,tsu.userid from taw_system_user tsu " +
//		" left join taw_system_dept tsd  on tsu.deptid = tsd.deptid )u " +
//		" where t.proc_inst_id_ = m.process_instance_id and m.task_assignee = u.userid(+) and m.Themeinterface = 'maleGuest' and m.state!=8";
		list1.add(processInstanceId);
		list1.add(siteCd);
		list1.add(userId);
		//拼接条件查询
		String whereSql = "";
		if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >=?";
			list1.add(selectAttribute.getBeginTime());
		}
		if (selectAttribute.getEndTime() != null && !selectAttribute.getEndTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?";
			list1.add(selectAttribute.getEndTime());
		}
	/*	if (selectAttribute.getMaleGuestNumber() != null && !selectAttribute.getMaleGuestNumber().equals("")) {
			whereSql += " and m.process_instance_id =?";
			list1.add(selectAttribute.getMaleGuestNumber().trim());
		}*/
		/*if (selectAttribute.getTheme() != null && !selectAttribute.getTheme().equals("")) {
			whereSql += " and instr(m.theme,?)>0 ";
			list1.add(selectAttribute.getTheme().trim());
		}*/
		if (selectAttribute.getStatus() != null && !selectAttribute.getStatus().equals("")) {
			whereSql += " and m.task_def_key = ?";
			list1.add(selectAttribute.getStatus());
		}
//		if (selectAttribute.getStatus() != null && !selectAttribute.getStatus().equals("")) {
//			whereSql += " and t.task_def_key_ = ?";
//			list1.add(selectAttribute.getStatus());
//		}
		if(selectAttribute.getWsNum()!=null && !"".equals(selectAttribute.getWsNum())){
			whereSql+="  and m.barrier_number =?";
			list1.add(selectAttribute.getWsNum());
		}
		if(selectAttribute.getInstallAddress()!=null && !"".equals(selectAttribute.getInstallAddress().trim())){
			whereSql += " and instr(m.install_address,?)>0 ";
			list1.add(selectAttribute.getInstallAddress());
		}
		if(selectAttribute.getDept()!=null && !"".equals(selectAttribute.getDept().trim())){
			whereSql += " and instr(d.deptname,?)>0 ";
			list1.add(selectAttribute.getDept());
		}
//		if(selectAttribute.getDept()!=null && !"".equals(selectAttribute.getDept().trim())){
//			whereSql += " and instr(u.deptname,?)>0 ";
//			list1.add(selectAttribute.getDept());
//		}
		if(selectAttribute.getPerson()!=null && !"".equals(selectAttribute.getPerson().trim())){
			whereSql+="  and u.username =?";
			list1.add(selectAttribute.getPerson());
		}
//		if(selectAttribute.getPerson()!=null && !"".equals(selectAttribute.getPerson().trim())){
//			whereSql+="  and t.username =?";
//			list1.add(selectAttribute.getPerson());
//		}
		sql += selectSql + whereSql
				+ " order by m.male_guest_state";
//		sql += selectSql + whereSql
//		+ " order by t.create_time_ desc) temp1 where rownum <=?) temp2 where temp2.num > ?";
		
//		list1.add(endResult * pageSize);		
//		list1.add(firstResult * pageSize);
		Object[] values = list1.toArray();
		
		System.out.println("公客待回复统明细3132sql="+sql);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql, values);
		for (Map map : list) {
			TaskModel model = new TaskModel();
			if(map.get("TaskId")!=null && !"".equals(map.get("TaskId"))){
				
				model.setTaskId(map.get("TaskId").toString());
			}else{
				model.setTaskId("");
			}
			if(map.get("Initiator")!=null && !"".equals(map.get("Initiator"))){
				model.setInitiator(map.get("Initiator").toString());
			}else{
				model.setInitiator("");
			}
			
			if(map.get("male_guest_state")!=null && !"".equals(map.get("male_guest_state"))){
				model.setMaleGuestState(map.get("male_guest_state").toString());
			}else{
				model.setMaleGuestState("");
			}
			if(map.get("station_name")!=null && !"".equals(map.get("station_name"))){
				model.setStationName(map.get("station_name").toString());
			}else{
				model.setStationName("");
			}
			
			//临时做为空判断，正常OneInitiator的值不可以为空
			if (map.get("OneInitiator") != null){
				model.setOneInitiator(map.get("OneInitiator").toString());
			}else{
				model.setOneInitiator("");
			}		
			model.setProcessInstanceId(map.get("ProcessInstanceId").toString());
			if (map.get("SendTime") != null) {
				if (!map.get("SendTime").toString().equals("")) {
					try {
						model.setSendTime(format.parse(map.get("SendTime")
								.toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

			}
			if(map.get("Theme")!=null && !"".equals(map.get("Theme"))){
				model.setTheme(map.get("Theme").toString());
			}else{
				model.setTheme("");
			}
			if(map.get("DeptId")!=null && !"".equals(map.get("DeptId"))){
				model.setDeptId(map.get("DeptId").toString());
			}else{
				model.setDeptId("");
			}
			if(map.get("State")!=null && !"".equals(map.get("State"))){
				model.setState(Integer.parseInt(map.get("State").toString()));
			}else{
				model.setState(0);
			}
			if(map.get("Install_address")!=null && !"".equals(map.get("Install_address"))){
				model.setInstallAddress(map.get("Install_address").toString());
			}else{
				model.setInstallAddress("");
			}
			if(map.get("Barrier_number")!=null && !"".equals(map.get("Barrier_number"))){
				model.setBarrierNumber(map.get("Barrier_number").toString());
			}else{
				model.setBarrierNumber("");
			}
			if(map.get("create_time")!=null && !"".equals(map.get("create_time"))){
				if (map.get("create_time") != null) {
					if (!map.get("create_time").toString().equals("")) {
						try {
							model.setCreateTime(format.parse(map.get("create_time")
									.toString()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}

				}
			}
			if(map.get("city")!=null && !"".equals(map.get("city"))){
				model.setCity(map.get("city").toString());
			}
			if(map.get("connect_person")!=null &&!"".equals(map.get("connect_person"))){
				model.setConnectPerson(map.get("connect_person").toString());
			}
			if(map.get("process_limit")!=null &&!"".equals(map.get("process_limit"))){
				model.setProcessLimit(map.get("process_limit").toString());
			}
			//工单历时 = 当前时间-故障发生时间：当工单历时大于处理时限，改变颜色
			if(map.get("create_time")!=null && !"".equals(map.get("create_time"))&& map.get("SendTime") != null){
				//两时间做差
				Date startTime = new Date();
				Date endTime = model.getCreateTime();
				long sortTime = startTime.getTime()-endTime.getTime();
				long hours = sortTime / (1000 * 60 * 60 );
				long minutes = sortTime/(1000*60)-hours*60;
				long second = sortTime/1000 - hours*60*60 - minutes*60;
				String newTime = hours+"小时"+minutes+"分钟"+second+"秒";
				model.setWorkTask(newTime);
				if(model.getProcessLimit()!=null&&!"".equals(model.getProcessLimit())){
					long processLimit = Long.parseLong(model.getProcessLimit());
					if(processLimit>hours){//没有超出时限
						model.setChangeColor(true);
					}else{//超出时限
						model.setChangeColor(false);
					}
				}
			}
			if(map.get("assignee")!=null && !"".equals(map.get("assignee"))){
				model.setExecutor(map.get("assignee").toString());
			}
			//状态存储--start
			String state = "";
			if(map.get("task_def_key")!=null && !"".equals(map.get("task_def_key"))&&"newMaleGuest".equals(map.get("task_def_key"))){
				state = "派发工单";
			}else if(map.get("task_def_key")!=null && !"".equals(map.get("task_def_key"))&&"auditor".equals(map.get("task_def_key"))){
				state="故障处理";
			}
			if(map.get("repeat_state")!=null && !"".equals(map.get("repeat_state"))&&"1".equals(map.get("repeat_state").toString().trim())){
				state = "(重修)"+state;
			}
			model.setStatusName(state);
			//--end
			
			r.add(model);

		}

		return r;

	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TaskModel> getMaleGuestByInitiator(String userId,
			MaleGuestSelectAttribute selectAttribute, int firstResult,
			int endResult, int pageSize) {
		 ArrayList list1 = new ArrayList();
		 StringBuilder stringBuilder = new StringBuilder();
		 stringBuilder.append(" select temp2.* from (select temp1.*, rownum num  from (select t.id_ as TaskId,");
		 stringBuilder.append(" m.initiator as Initiator,m.one_initiator as OneInitiator, m.process_instance_id as ProcessInstanceId,");
		 stringBuilder.append(" m.send_time as SendTime,m.theme as Theme,m.task_assignee, m.state as State,tu.deptid ,td.deptname,");
		 stringBuilder.append(" m.install_address as Install_address, m.barrier_number as Barrier_number,m.create_time,m.city,");
		 stringBuilder.append(" m.connect_person, m.process_limit, t.assignee_, tu.username,t.task_def_key_,m.archiving_time,m.repeat_state");
		 stringBuilder.append(" from  pnr_act_transfer_office_main m  left join act_ru_task t  on m.process_instance_id = t.proc_inst_id_");
		 stringBuilder.append(" left join taw_system_user tu   on t.assignee_ = tu.userid left join taw_system_dept td  on tu.deptid = td.deptid");
		 stringBuilder.append(" where m.themeinterface='maleGuest'");
		 stringBuilder.append(" and m.initiator= ?");
		 stringBuilder.append("  and m.state!=5 and m.do_flag is null");
		 
		 list1.add(userId);
		 
		 //拼接条件字符串
		 	if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
			 	stringBuilder.append(" and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >= ?");
			 	list1.add(selectAttribute.getBeginTime());
			}
			if (selectAttribute.getEndTime() != null && !selectAttribute.getEndTime().equals("")) {
				stringBuilder.append(" and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?");
				list1.add(selectAttribute.getEndTime());
			}
			if (selectAttribute.getMaleGuestNumber() != null && !selectAttribute.getMaleGuestNumber().equals("")) {
				stringBuilder.append(" and m.process_instance_id = ?");
				list1.add(selectAttribute.getMaleGuestNumber().trim());
			}
			if (selectAttribute.getTheme() != null && !selectAttribute.getTheme().equals("")) {
				stringBuilder.append(" and instr(m.theme,?)>0");
				list1.add(selectAttribute.getTheme().trim());
			}
			if (selectAttribute.getStatus() != null && !selectAttribute.getStatus().equals("")) {
				stringBuilder.append(" and t.task_def_key_ = ?");
				list1.add(selectAttribute.getStatus());
			}
			if(selectAttribute.getWsNum()!=null && !"".equals(selectAttribute.getWsNum())){
				stringBuilder.append(" and m.barrier_number =?");
				list1.add(selectAttribute.getWsNum());
			}
			if(selectAttribute.getInstallAddress()!=null && !"".equals(selectAttribute.getInstallAddress().trim())){
				stringBuilder.append(" and instr(m.install_address,?)>0 ");
				list1.add(selectAttribute.getInstallAddress());
			}
			if(selectAttribute.getDept()!=null && !"".equals(selectAttribute.getDept().trim())){
				stringBuilder.append(" and instr(td.deptname,?)>0 ");
				list1.add(selectAttribute.getDept());
			}
			if(selectAttribute.getPerson()!=null && !"".equals(selectAttribute.getPerson().trim())){
				stringBuilder.append("and tu.username =?");
				list1.add(selectAttribute.getPerson());
			}
		 
		 stringBuilder.append(" order by t.create_time_ desc) temp1");
		 stringBuilder.append(" where rownum <= ?) temp2 where temp2.num > ?");
		 list1.add(endResult * pageSize);
		 list1.add(firstResult * pageSize);
		 System.out.println("公客由我创建明细sql="+stringBuilder.toString());
		 Object[] args = list1.toArray();
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<TaskModel> r = new ArrayList<TaskModel>();
			List<Map> list = this.getJdbcTemplate().queryForList(stringBuilder.toString(),args);
			
			if(list!=null && list.size()>0){
				for(Map map:list){
					TaskModel model = new TaskModel();
					if(map.get("TaskId")!=null && !"".equals(map.get("TaskId"))){
						
						model.setTaskId(map.get("TaskId").toString());
					}else{
						model.setTaskId("");
					}
					if(map.get("Initiator")!=null && !"".equals(map.get("Initiator"))){
						model.setInitiator(map.get("Initiator").toString());
					}else{
						model.setInitiator("");
					}
					
					//临时做为空判断，正常OneInitiator的值不可以为空
					if (map.get("OneInitiator") != null){
						model.setOneInitiator(map.get("OneInitiator").toString());
					}else{
						model.setOneInitiator("");
					}		
					model.setProcessInstanceId(map.get("ProcessInstanceId").toString());
					if (map.get("SendTime") != null) {
						if (!map.get("SendTime").toString().equals("")) {
							try {
								model.setSendTime(format.parse(map.get("SendTime")
										.toString()));
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}

					}
					if(map.get("Theme")!=null && !"".equals(map.get("Theme"))){
						model.setTheme(map.get("Theme").toString());
					}else{
						model.setTheme("");
					}
					if(map.get("DeptId")!=null && !"".equals(map.get("DeptId"))){
						model.setDeptId(map.get("DeptId").toString());
					}else{
						model.setDeptId("");
					}
					if(map.get("State")!=null && !"".equals(map.get("State"))){
						model.setState(Integer.parseInt(map.get("State").toString()));
					}else{
						model.setState(0);
					}
					if(map.get("Install_address")!=null && !"".equals(map.get("Install_address"))){
						model.setInstallAddress(map.get("Install_address").toString());
					}else{
						model.setInstallAddress("");
					}
					if(map.get("Barrier_number")!=null && !"".equals(map.get("Barrier_number"))){
						model.setBarrierNumber(map.get("Barrier_number").toString());
					}else{
						model.setBarrierNumber("");
					}
					if(map.get("create_time")!=null && !"".equals(map.get("create_time"))){
						if (map.get("create_time") != null) {
							if (!map.get("create_time").toString().equals("")) {
								try {
									model.setCreateTime(format.parse(map.get("create_time")
											.toString()));
								} catch (ParseException e) {
									e.printStackTrace();
								}
							}

						}
					}
					if(map.get("connect_person")!=null &&!"".equals(map.get("connect_person"))){
						model.setConnectPerson(map.get("connect_person").toString());
					}
					if(map.get("process_limit")!=null &&!"".equals(map.get("process_limit"))){
						model.setProcessLimit(map.get("process_limit").toString());
					}
					if(map.get("State")!=null && "8".equals(map.get("State").toString().trim())){
						//完成工单：工单历时=归档时间-故障发生时间 当工单历时大于处理时限，改变颜色
						if(map.get("create_time")!=null && !"".equals(map.get("create_time"))&& map.get("archiving_time") != null){
							//两时间做差
							Date startTime = new Date();
							try {
								startTime = format.parse(map.get("archiving_time").toString());
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Date endTime = model.getCreateTime();
							long sortTime = startTime.getTime()-endTime.getTime();
							long hours = sortTime / (1000 * 60 * 60 );
							long minutes = sortTime/(1000*60)-hours*60;
							long second = sortTime/1000 - hours*60*60 - minutes*60;
							String newTime = hours+"小时"+minutes+"分钟"+second+"秒";
							model.setWorkTask(newTime);
							if(model.getProcessLimit()!=null&&!"".equals(model.getProcessLimit())){
								long processLimit = Long.parseLong(model.getProcessLimit());
								if(processLimit>hours){//没有超出时限
									model.setChangeColor(true);
								}else{//超出时限
									model.setChangeColor(false);
								}
							}
						}
						model.setStatusName("处理完毕");
						
					}//工单历时 = 当前时间-故障发生时间：当工单历时大于处理时限，改变颜色--未处理完
					else if(map.get("create_time")!=null && !"".equals(map.get("create_time"))&& map.get("SendTime") != null){
						//两时间做差
						Date startTime = new Date();
						Date endTime = model.getCreateTime();
						long sortTime = startTime.getTime()-endTime.getTime();
						long hours = sortTime / (1000 * 60 * 60 );
						long minutes = sortTime/(1000*60)-hours*60;
						long second = sortTime/1000 - hours*60*60 - minutes*60;
						String newTime = hours+"小时"+minutes+"分钟"+second+"秒";
						model.setWorkTask(newTime);
						if(model.getProcessLimit()!=null&&!"".equals(model.getProcessLimit())){
							long processLimit = Long.parseLong(model.getProcessLimit());
							if(processLimit>hours){//没有超出时限
								model.setChangeColor(true);
							}else{//超出时限
								model.setChangeColor(false);
							}
						}
						String state = "";
						if(map.get("task_def_key_")!=null && "newMaleGuest".equals(map.get("task_def_key_"))){
							
							state = "工单派发";
						}else if(map.get("task_def_key_")!=null && "auditor".equals(map.get("task_def_key_"))){
							model.setStatusName("故障处理");
							state = "故障处理";
						}
						if(map.get("repeat_state")!=null && !"".equals(map.get("repeat_state"))&&"1".equals(map.get("repeat_state").toString().trim())){
							state = "(重修)"+state;
						}
						model.setStatusName(state);
					}
					if(map.get("assignee_")!=null && !"".equals(map.get("assignee_"))){
						model.setExecutor(map.get("assignee_").toString());
					}
					r.add(model);
				}
			}
		 return r;
	}
	 
	@SuppressWarnings("unchecked")
	@Override
	public int getMaleGuestCountByInitiator(String userId,
			MaleGuestSelectAttribute selectAttribute) {
		ArrayList list1 = new ArrayList();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select count(*) as total from (select t.id_  as TaskId, m.initiator as Initiator,");
		stringBuilder.append(" m.one_initiator  as OneInitiator, m.process_instance_id as ProcessInstanceId,");
		stringBuilder.append(" m.send_time as SendTime, m.theme as Theme,m.task_assignee,");
		stringBuilder.append(" m.state  as State,tu.deptid  as DeptId,td.deptname,m.install_address as Install_address,");
		stringBuilder.append(" m.barrier_number as Barrier_number, m.create_time, m.city,m.connect_person,");
		stringBuilder.append(" m.process_limit, t.assignee_,tu.username");
		stringBuilder.append(" from  pnr_act_transfer_office_main m left join act_ru_task t ");
		stringBuilder.append(" on m.process_instance_id = t.proc_inst_id_ left join taw_system_user tu ");
		stringBuilder.append(" on t.assignee_ = tu.userid left join taw_system_dept td  on tu.deptid = td.deptid");
		stringBuilder.append(" where m.themeinterface='maleGuest'");
		stringBuilder.append(" and m.initiator= ?");
		stringBuilder.append(" and m.state!=5 ");
		stringBuilder.append(" and m.do_flag is null ");
		
		list1.add(userId);
		
		//拼接条件查询
		if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
		 	stringBuilder.append(" and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >= ?");
		 	list1.add(selectAttribute.getBeginTime());
		}
		if (selectAttribute.getEndTime() != null && !selectAttribute.getEndTime().equals("")) {
			stringBuilder.append(" and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?");
			list1.add(selectAttribute.getEndTime());
		}
		if (selectAttribute.getMaleGuestNumber() != null && !selectAttribute.getMaleGuestNumber().equals("")) {
			stringBuilder.append(" and m.process_instance_id = ?");
			list1.add(selectAttribute.getMaleGuestNumber().trim());
		}
		if (selectAttribute.getTheme() != null && !selectAttribute.getTheme().equals("")) {
			stringBuilder.append(" and instr(m.theme,?)>0");
			list1.add(selectAttribute.getTheme().trim());
		}
		if (selectAttribute.getStatus() != null && !selectAttribute.getStatus().equals("")) {
			stringBuilder.append(" and t.task_def_key_ = ?");
			list1.add(selectAttribute.getStatus());
		}
		if(selectAttribute.getWsNum()!=null && !"".equals(selectAttribute.getWsNum())){
			stringBuilder.append(" and m.barrier_number =?");
			list1.add(selectAttribute.getWsNum());
		}
		if(selectAttribute.getInstallAddress()!=null && !"".equals(selectAttribute.getInstallAddress().trim())){
			stringBuilder.append(" and instr(m.install_address,?)>0 ");
			list1.add(selectAttribute.getInstallAddress());
		}
		if(selectAttribute.getDept()!=null && !"".equals(selectAttribute.getDept().trim())){
			stringBuilder.append(" and instr(td.deptname,?)>0 ");
			list1.add(selectAttribute.getDept());
		}
		if(selectAttribute.getPerson()!=null && !"".equals(selectAttribute.getPerson().trim())){
			stringBuilder.append("and tu.username =?");
			list1.add(selectAttribute.getPerson());
		}
		stringBuilder.append(")");
		System.out.println("公客由我创建统计数sql="+stringBuilder.toString());
		Object[] args = list1.toArray();
		int total = this.getJdbcTemplate().queryForInt(stringBuilder.toString(),args);
		return total;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TaskModel> getMaleGuestByIntiatorOrAssignee(String userId,
			MaleGuestSelectAttribute selectAttribute, int firstResult,
			int endResult, int pageSize) {
		 ArrayList list1 = new ArrayList();
		 StringBuilder stringBuilder = new StringBuilder();
//		 stringBuilder.append(" select temp2.* from (select temp1.*, rownum num  from (select t.id_ as TaskId,");
//		 stringBuilder.append(" m.initiator as Initiator,m.one_initiator as OneInitiator, m.process_instance_id as ProcessInstanceId,");
//		 stringBuilder.append(" m.send_time as SendTime,m.theme as Theme,m.task_assignee, m.state as State,tu.deptid ,td.deptname,");
//		 stringBuilder.append(" m.install_address as Install_address, m.barrier_number as Barrier_number,m.create_time,m.city,");
//		 stringBuilder.append(" m.connect_person, m.process_limit, t.assignee_, tu.username,t.task_def_key_,m.archiving_time,m.repeat_state");
//		 stringBuilder.append(" from  pnr_act_transfer_office_main m  left join act_ru_task t  on m.process_instance_id = t.proc_inst_id_");
//		 stringBuilder.append(" left join taw_system_user tu   on t.assignee_ = tu.userid left join taw_system_dept td  on tu.deptid = td.deptid");
//		 stringBuilder.append(" where m.themeinterface='maleGuest'");
//		 stringBuilder.append(" and (m.initiator = ? or m.task_assignee = ?)");
//		 stringBuilder.append(" and m.state!=5 and m.do_flag is null");
		 
		 stringBuilder.append("select temp2.* from (select temp1.*, rownum num  from (");
		 stringBuilder.append("select t.id_ as TaskId,");
		 stringBuilder.append("       m.initiator as Initiator,");
		 stringBuilder.append("       m.one_initiator as OneInitiator,");
		 stringBuilder.append("       m.process_instance_id as ProcessInstanceId,"); 
		 stringBuilder.append("       m.send_time as SendTime,"); 
		 stringBuilder.append("       m.theme as Theme,");
		 stringBuilder.append("       m.task_assignee,"); 
		 stringBuilder.append("       m.state as State,");
		 stringBuilder.append("       tu.deptid,"); 
		 stringBuilder.append("       td.deptname,");
		 stringBuilder.append("       m.install_address as Install_address,");
		 stringBuilder.append("       m.barrier_number as Barrier_number,");
		 stringBuilder.append("       m.create_time,");
		 stringBuilder.append("       m.city,");
		 stringBuilder.append("       m.connect_person,"); 
		 stringBuilder.append("       m.process_limit,"); 
		 stringBuilder.append("       t.assignee_,");
		 stringBuilder.append("       tu.username,");
		 stringBuilder.append("       t.task_def_key_,"); 
		 stringBuilder.append("       m.archiving_time,");
		 stringBuilder.append("       m.repeat_state,");
		 stringBuilder.append("       nvl(m.male_guest_state, '0') as male_guest_state,"); 
		 stringBuilder.append("       m.site_cd"); 
		 stringBuilder.append("  from pnr_act_transfer_office_main m");
		 stringBuilder.append("  left join act_ru_task t");
		 stringBuilder.append("    on m.process_instance_id = t.proc_inst_id_");
		 stringBuilder.append("  left join taw_system_user tu");
		 stringBuilder.append("    on t.assignee_ = tu.userid"); 
		 stringBuilder.append("  left join taw_system_dept td"); 
		 stringBuilder.append("    on tu.deptid = td.deptid");
		 stringBuilder.append(" where m.themeinterface = 'maleGuest'");
		 stringBuilder.append("   and m.state != 5"); 
		 stringBuilder.append("   and m.state != 8");
		 stringBuilder.append("   and m.do_flag is null");
		 stringBuilder.append("   and nvl(m.male_guest_state, '0') != '2'");
		 stringBuilder.append("   and exists (select kst.assignee_"); 
		 stringBuilder.append("          from act_hi_taskinst kst");
		 stringBuilder.append("         where kst.assignee_ = ?");
		 stringBuilder.append("           and kst.proc_inst_id_ = m.Process_Instance_Id"); 
		 stringBuilder.append("           and kst.end_time_ is not null)");
		 
		 //list1.add(userId);
		 list1.add(userId);
		 
		 //拼接条件字符串
		 if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
				stringBuilder.append(" and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >=?");
				list1.add(selectAttribute.getBeginTime());
			}
			if (selectAttribute.getEndTime() != null && !selectAttribute.getEndTime().equals("")) {
				stringBuilder.append(" and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?");
				list1.add(selectAttribute.getEndTime());
			}
			if (selectAttribute.getMaleGuestNumber() != null && !selectAttribute.getMaleGuestNumber().equals("")) {
				stringBuilder.append(" and m.process_instance_id =?");
				list1.add(selectAttribute.getMaleGuestNumber().trim());
			}
			if (selectAttribute.getTheme() != null && !selectAttribute.getTheme().equals("")) {
				stringBuilder.append(" and instr(m.theme,?)>0 ");
				list1.add(selectAttribute.getTheme().trim());	
			}
			if (selectAttribute.getStatus() != null && !selectAttribute.getStatus().equals("")) {
				stringBuilder.append(" and t.task_def_key_ = ?");
				list1.add(selectAttribute.getStatus());
			}
			if(selectAttribute.getWsNum()!=null && !"".equals(selectAttribute.getWsNum())){
				stringBuilder.append(" and m.barrier_number =?");
				list1.add(selectAttribute.getWsNum());
			}
			if(selectAttribute.getInstallAddress()!=null && !"".equals(selectAttribute.getInstallAddress().trim())){
				stringBuilder.append(" and instr(m.install_address,?)>0 ");
				list1.add(selectAttribute.getInstallAddress());
			}
			if(selectAttribute.getDept()!=null && !"".equals(selectAttribute.getDept().trim())){
				stringBuilder.append(" and instr(td.deptname,?)>0 ");
				list1.add(selectAttribute.getDept());
			}
			if(selectAttribute.getPerson()!=null && !"".equals(selectAttribute.getPerson().trim())){
				stringBuilder.append("and tu.username =?");
				list1.add(selectAttribute.getPerson());
			}
		 
		 stringBuilder.append(" order by t.create_time_ desc) temp1");
		 stringBuilder.append(" where rownum <= ?) temp2 where temp2.num > ?");
		 list1.add(endResult * pageSize);
		 list1.add(firstResult * pageSize);
		 
		 System.out.println("公客已处理明细sql="+stringBuilder.toString());
		 Object[] values = list1.toArray();
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<TaskModel> r = new ArrayList<TaskModel>();
			List<Map> list = this.getJdbcTemplate().queryForList(stringBuilder.toString(),values);
			if(list!=null && list.size()>0){
				for(Map map:list){
					TaskModel model = new TaskModel();
					if(map.get("TaskId")!=null && !"".equals(map.get("TaskId"))){
						
						model.setTaskId(map.get("TaskId").toString());
					}else{
						model.setTaskId("");
					}
					if(map.get("Initiator")!=null && !"".equals(map.get("Initiator"))){
						model.setInitiator(map.get("Initiator").toString());
					}else{
						model.setInitiator("");
					}
					
					//临时做为空判断，正常OneInitiator的值不可以为空
					if (map.get("OneInitiator") != null){
						model.setOneInitiator(map.get("OneInitiator").toString());
					}else{
						model.setOneInitiator("");
					}		
					model.setProcessInstanceId(map.get("ProcessInstanceId").toString());
					if (map.get("SendTime") != null) {
						if (!map.get("SendTime").toString().equals("")) {
							try {
								model.setSendTime(format.parse(map.get("SendTime")
										.toString()));
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}

					}
					if(map.get("Theme")!=null && !"".equals(map.get("Theme"))){
						model.setTheme(map.get("Theme").toString());
					}else{
						model.setTheme("");
					}
					if(map.get("DeptId")!=null && !"".equals(map.get("DeptId"))){
						model.setDeptId(map.get("DeptId").toString());
					}else{
						model.setDeptId("");
					}
					if(map.get("State")!=null && !"".equals(map.get("State"))){
						model.setState(Integer.parseInt(map.get("State").toString()));
					}else{
						model.setState(0);
					}
					if(map.get("Install_address")!=null && !"".equals(map.get("Install_address"))){
						model.setInstallAddress(map.get("Install_address").toString());
					}else{
						model.setInstallAddress("");
					}
					if(map.get("Barrier_number")!=null && !"".equals(map.get("Barrier_number"))){
						model.setBarrierNumber(map.get("Barrier_number").toString());
					}else{
						model.setBarrierNumber("");
					}
					if(map.get("create_time")!=null && !"".equals(map.get("create_time"))){
						if (map.get("create_time") != null) {
							if (!map.get("create_time").toString().equals("")) {
								try {
									model.setCreateTime(format.parse(map.get("create_time")
											.toString()));
								} catch (ParseException e) {
									e.printStackTrace();
								}
							}

						}
					}
					if(map.get("connect_person")!=null &&!"".equals(map.get("connect_person"))){
						model.setConnectPerson(map.get("connect_person").toString());
					}
					if(map.get("process_limit")!=null &&!"".equals(map.get("process_limit"))){
						model.setProcessLimit(map.get("process_limit").toString());
					}
					if(map.get("State")!=null && "8".equals(map.get("State").toString().trim())){
						//完成工单：工单历时=归档时间-故障发生时间 当工单历时大于处理时限，改变颜色
						if(map.get("create_time")!=null && !"".equals(map.get("create_time"))&& map.get("archiving_time") != null){
							//两时间做差
							Date startTime = new Date();
							try {
								startTime = format.parse(map.get("archiving_time").toString());
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Date endTime = model.getCreateTime();
							long sortTime = startTime.getTime()-endTime.getTime();
							long hours = sortTime / (1000 * 60 * 60 );
							long minutes = sortTime/(1000*60)-hours*60;
							long second = sortTime/1000 - hours*60*60 - minutes*60;
							String newTime = hours+"小时"+minutes+"分钟"+second+"秒";
							model.setWorkTask(newTime);
							if(model.getProcessLimit()!=null&&!"".equals(model.getProcessLimit())){
								long processLimit = Long.parseLong(model.getProcessLimit());
								if(processLimit>hours){//没有超出时限
									model.setChangeColor(true);
								}else{//超出时限
									model.setChangeColor(false);
								}
							}
						}
						model.setStatusName("处理完毕");
						
					}//工单历时 = 当前时间-故障发生时间：当工单历时大于处理时限，改变颜色--未处理完
					else if(map.get("create_time")!=null && !"".equals(map.get("create_time"))&& map.get("SendTime") != null){
						//两时间做差
						Date startTime = new Date();
						Date endTime = model.getCreateTime();
						long sortTime = startTime.getTime()-endTime.getTime();
						long hours = sortTime / (1000 * 60 * 60 );
						long minutes = sortTime/(1000*60)-hours*60;
						long second = sortTime/1000 - hours*60*60 - minutes*60;
						String newTime = hours+"小时"+minutes+"分钟"+second+"秒";
						model.setWorkTask(newTime);
						if(model.getProcessLimit()!=null&&!"".equals(model.getProcessLimit())){
							long processLimit = Long.parseLong(model.getProcessLimit());
							if(processLimit>hours){//没有超出时限
								model.setChangeColor(true);
							}else{//超出时限
								model.setChangeColor(false);
							}
						}
						String state ="";
						if(map.get("task_def_key_")!=null && "newMaleGuest".equals(map.get("task_def_key_"))){
							state = "工单派发";
						}else if(map.get("task_def_key_")!=null && "auditor".equals(map.get("task_def_key_"))){
							state="故障处理";
						}
						if(map.get("repeat_state")!=null && !"".equals(map.get("repeat_state"))&&"1".equals(map.get("repeat_state").toString().trim())){
							state = "(重修)"+state;
						}
						model.setStatusName(state);
					}
					if(map.get("assignee_")!=null && !"".equals(map.get("assignee_"))){
						model.setExecutor(map.get("assignee_").toString());
					}
					//归集工单标识
					if(map.get("male_guest_state")!=null && !"".equals(map.get("male_guest_state"))){
						model.setMaleGuestState(map.get("male_guest_state").toString());
					}
					//基站编码
					if(map.get("site_cd")!=null && !"".equals(map.get("site_cd"))){
						model.setSiteCd(map.get("site_cd").toString());
					}
					r.add(model);
				}
			}
		 return r;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getMaleGuestCountByInitiatorOrAssignee(String userId,
			MaleGuestSelectAttribute selectAttribute) {
		ArrayList list1 = new ArrayList();
		StringBuilder stringBuilder = new StringBuilder();
//		stringBuilder.append("select count(*) as total from (select t.id_  as TaskId, m.initiator as Initiator,");
//		stringBuilder.append(" m.one_initiator  as OneInitiator, m.process_instance_id as ProcessInstanceId,");
//		stringBuilder.append(" m.send_time as SendTime, m.theme as Theme,m.task_assignee,");
//		stringBuilder.append(" m.state  as State,tu.deptid  as DeptId,td.deptname,m.install_address as Install_address,");
//		stringBuilder.append(" m.barrier_number as Barrier_number, m.create_time, m.city,m.connect_person,");
//		stringBuilder.append(" m.process_limit, t.assignee_,tu.username");
//		stringBuilder.append(" from  pnr_act_transfer_office_main m left join act_ru_task t ");
//		stringBuilder.append(" on m.process_instance_id = t.proc_inst_id_ left join taw_system_user tu ");
//		stringBuilder.append(" on t.assignee_ = tu.userid left join taw_system_dept td  on tu.deptid = td.deptid");
//		stringBuilder.append(" where m.themeinterface='maleGuest'");
//		stringBuilder.append(" and (m.initiator = ? or m.task_assignee = ?)");
//		stringBuilder.append(" and m.state!=5 ");
//		stringBuilder.append(" and m.do_flag is null ");
		

		stringBuilder.append("select count(m.process_instance_id) as total");
		stringBuilder.append("  from pnr_act_transfer_office_main m");
		stringBuilder.append("  left join act_ru_task t" );
		stringBuilder.append("    on m.process_instance_id = t.proc_inst_id_");
		stringBuilder.append("  left join taw_system_user tu");
		stringBuilder.append("    on t.assignee_ = tu.userid");
		stringBuilder.append("  left join taw_system_dept td" );
		stringBuilder.append("    on tu.deptid = td.deptid" );
		stringBuilder.append(" where m.themeinterface = 'maleGuest'" );
		stringBuilder.append("   and m.state != 5");
		stringBuilder.append("   and m.state != 8" );
		stringBuilder.append("   and m.do_flag is null" );
		stringBuilder.append("   and nvl(m.male_guest_state, '0') != '2'" );
		stringBuilder.append("   and exists (select kst.assignee_");
		stringBuilder.append("          from act_hi_taskinst kst");
		stringBuilder.append("         where kst.assignee_ = ?");
		stringBuilder.append("           and kst.proc_inst_id_ = m.Process_Instance_Id" );
		stringBuilder.append("           and kst.end_time_ is not null)");
		
		//list1.add(userId);
		list1.add(userId);
		//拼接条件查询
		if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
			stringBuilder.append(" and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >=?");
			list1.add(selectAttribute.getBeginTime());
		}
		if (selectAttribute.getEndTime() != null && !selectAttribute.getEndTime().equals("")) {
			stringBuilder.append(" and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?");
			list1.add(selectAttribute.getEndTime());
		}
		if (selectAttribute.getMaleGuestNumber() != null && !selectAttribute.getMaleGuestNumber().equals("")) {
			stringBuilder.append(" and m.process_instance_id =?");
			list1.add(selectAttribute.getMaleGuestNumber().trim());
		}
		if (selectAttribute.getTheme() != null && !selectAttribute.getTheme().equals("")) {
			stringBuilder.append(" and instr(m.theme,?)>0 ");
			list1.add(selectAttribute.getTheme().trim());	
		}
		if (selectAttribute.getStatus() != null && !selectAttribute.getStatus().equals("")) {
			stringBuilder.append(" and t.task_def_key_ = ?");
			list1.add(selectAttribute.getStatus());
		}
		if(selectAttribute.getWsNum()!=null && !"".equals(selectAttribute.getWsNum())){
			stringBuilder.append(" and m.barrier_number =?");
			list1.add(selectAttribute.getWsNum());
		}
		if(selectAttribute.getInstallAddress()!=null && !"".equals(selectAttribute.getInstallAddress().trim())){
			stringBuilder.append(" and instr(m.install_address,?)>0 ");
			list1.add(selectAttribute.getInstallAddress());
		}
		if(selectAttribute.getDept()!=null && !"".equals(selectAttribute.getDept().trim())){
			stringBuilder.append(" and instr(td.deptname,?)>0 ");
			list1.add(selectAttribute.getDept());
		}
		if(selectAttribute.getPerson()!=null && !"".equals(selectAttribute.getPerson().trim())){
			stringBuilder.append("and tu.username =?");
			list1.add(selectAttribute.getPerson());
		}
	//	stringBuilder.append(")");
		System.out.println("公客已处理统计sql="+stringBuilder.toString());
		Object[] args = list1.toArray();
		int total = this.getJdbcTemplate().queryForInt(stringBuilder.toString(),args);
		return total;
	}

	
	
	@SuppressWarnings("unchecked")
	@Override
	public int getMaleGeustCountToGetBack(String userId,
			MaleGuestSelectAttribute selectAttribute) {
		ArrayList list1 = new ArrayList();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select count(*) as total from (select t.id_  as TaskId, m.initiator as Initiator,");
		stringBuilder.append(" m.one_initiator  as OneInitiator, m.process_instance_id as ProcessInstanceId,");
		stringBuilder.append(" m.send_time as SendTime, m.theme as Theme,m.task_assignee,");
		stringBuilder.append(" m.state  as State,tu.deptid  as DeptId,td.deptname,m.install_address as Install_address,");
		stringBuilder.append(" m.barrier_number as Barrier_number, m.create_time, m.city,m.connect_person,");
		stringBuilder.append(" m.process_limit, t.assignee_,tu.username");
		stringBuilder.append(" from  pnr_act_transfer_office_main m left join act_ru_task t ");
		stringBuilder.append(" on m.process_instance_id = t.proc_inst_id_ left join taw_system_user tu ");
		stringBuilder.append(" on t.assignee_ = tu.userid left join taw_system_dept td  on tu.deptid = td.deptid");
		stringBuilder.append(" where m.themeinterface='maleGuest'");
		stringBuilder.append(" and m.initiator = ?");
		stringBuilder.append(" and m.state!=5 ");
		stringBuilder.append(" and m.state!=8 ");
		stringBuilder.append(" and t.task_def_key_='auditor' ");
		stringBuilder.append(" and m.do_flag is null ");
		
		list1.add(userId);
		//拼接条件查询
		 if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
			 	stringBuilder.append(" and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >=?");
				list1.add(selectAttribute.getBeginTime());
			}
			if (selectAttribute.getEndTime() != null && !selectAttribute.getEndTime().equals("")) {
				stringBuilder.append(" and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?");
				list1.add(selectAttribute.getEndTime());
			}
			if (selectAttribute.getMaleGuestNumber() != null && !selectAttribute.getMaleGuestNumber().equals("")) {
				stringBuilder.append(" and m.process_instance_id =?");
				list1.add(selectAttribute.getMaleGuestNumber().trim());
			}
			if (selectAttribute.getTheme() != null && !selectAttribute.getTheme().equals("")) {
				stringBuilder.append(" and instr(m.theme,?)>0 ");
				list1.add(selectAttribute.getTheme().trim());	
			}
			if (selectAttribute.getStatus() != null && !selectAttribute.getStatus().equals("")) {
				stringBuilder.append(" and t.task_def_key_ = ?");
				list1.add(selectAttribute.getStatus());
			}
			if(selectAttribute.getWsNum()!=null && !"".equals(selectAttribute.getWsNum())){
				stringBuilder.append(" and m.barrier_number =?");
				list1.add(selectAttribute.getWsNum());
			}
			if(selectAttribute.getInstallAddress()!=null && !"".equals(selectAttribute.getInstallAddress().trim())){
				stringBuilder.append(" and instr(m.install_address,?)>0 ");
				list1.add(selectAttribute.getInstallAddress());
			}
			if(selectAttribute.getDept()!=null && !"".equals(selectAttribute.getDept().trim())){
				stringBuilder.append(" and instr(td.deptname,?)>0 ");
				list1.add(selectAttribute.getDept());
			}
			if(selectAttribute.getPerson()!=null && !"".equals(selectAttribute.getPerson().trim())){
				stringBuilder.append("and tu.username =?");
				list1.add(selectAttribute.getPerson());
			}
		stringBuilder.append(")");
		System.out.println("公客由我创建统计数sql="+stringBuilder.toString());
		Object[] args = list1.toArray();
		int total = 0;
		total = this.getJdbcTemplate().queryForInt(stringBuilder.toString(), args);
		return total;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TaskModel> getMaleGeustToGetBack(String userId,
			MaleGuestSelectAttribute selectAttribute, int firstResult,
			int endResult, int pageSize) {
		 ArrayList list1 = new ArrayList();
		 StringBuilder stringBuilder = new StringBuilder();
		 stringBuilder.append(" select temp2.* from (select temp1.*, rownum num  from (select t.id_ as TaskId,");
		 stringBuilder.append(" m.initiator as Initiator,m.one_initiator as OneInitiator, m.process_instance_id as ProcessInstanceId,");
		 stringBuilder.append(" m.send_time as SendTime,m.theme as Theme,m.task_assignee, m.state as State,tu.deptid ,td.deptname,");
		 stringBuilder.append(" m.install_address as Install_address, m.barrier_number as Barrier_number,m.create_time,m.city,");
		 stringBuilder.append(" m.connect_person, m.process_limit, t.assignee_, tu.username,t.task_def_key_,m.archiving_time,m.repeat_state");
		 stringBuilder.append(" from  pnr_act_transfer_office_main m  left join act_ru_task t  on m.process_instance_id = t.proc_inst_id_");
		 stringBuilder.append(" left join taw_system_user tu   on t.assignee_ = tu.userid left join taw_system_dept td  on tu.deptid = td.deptid");
		 stringBuilder.append(" where m.themeinterface='maleGuest'");
		 stringBuilder.append(" and m.initiator = ?");
		 stringBuilder.append(" and m.state!=8 ");
		 stringBuilder.append(" and t.task_def_key_='auditor' ");
		 stringBuilder.append(" and m.state!=5 and m.do_flag is null");
		 
		 list1.add(userId);
		 
		 //拼接条件字符串
		 if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
			 	stringBuilder.append(" and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >=?");
				list1.add(selectAttribute.getBeginTime());
			}
			if (selectAttribute.getEndTime() != null && !selectAttribute.getEndTime().equals("")) {
				stringBuilder.append(" and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?");
				list1.add(selectAttribute.getEndTime());
			}
			if (selectAttribute.getMaleGuestNumber() != null && !selectAttribute.getMaleGuestNumber().equals("")) {
				stringBuilder.append(" and m.process_instance_id =?");
				list1.add(selectAttribute.getMaleGuestNumber().trim());
			}
			if (selectAttribute.getTheme() != null && !selectAttribute.getTheme().equals("")) {
				stringBuilder.append(" and instr(m.theme,?)>0 ");
				list1.add(selectAttribute.getTheme().trim());	
			}
			if (selectAttribute.getStatus() != null && !selectAttribute.getStatus().equals("")) {
				stringBuilder.append(" and t.task_def_key_ = ?");
				list1.add(selectAttribute.getStatus());
			}
			if(selectAttribute.getWsNum()!=null && !"".equals(selectAttribute.getWsNum())){
				stringBuilder.append(" and m.barrier_number =?");
				list1.add(selectAttribute.getWsNum());
			}
			if(selectAttribute.getInstallAddress()!=null && !"".equals(selectAttribute.getInstallAddress().trim())){
				stringBuilder.append(" and instr(m.install_address,?)>0 ");
				list1.add(selectAttribute.getInstallAddress());
			}
			if(selectAttribute.getDept()!=null && !"".equals(selectAttribute.getDept().trim())){
				stringBuilder.append(" and instr(td.deptname,?)>0 ");
				list1.add(selectAttribute.getDept());
			}
			if(selectAttribute.getPerson()!=null && !"".equals(selectAttribute.getPerson().trim())){
				stringBuilder.append("and tu.username =?");
				list1.add(selectAttribute.getPerson());
			}
		 
		 stringBuilder.append(" order by t.create_time_ desc) temp1");
		 stringBuilder.append(" where rownum <= ?) temp2 where temp2.num > ?");
		 list1.add(endResult * pageSize);
		 list1.add(firstResult * pageSize);
		 System.out.println("公客工单抓回详情sql="+stringBuilder.toString());
		 Object[] values = list1.toArray();
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			List<TaskModel> r = new ArrayList<TaskModel>();
			List<Map> list = this.getJdbcTemplate().queryForList(stringBuilder.toString(),values);
			
			if(list!=null && list.size()>0){
				for(Map map:list){
					TaskModel model = new TaskModel();
					if(map.get("TaskId")!=null && !"".equals(map.get("TaskId"))){
						
						model.setTaskId(map.get("TaskId").toString());
					}else{
						model.setTaskId("");
					}
					if(map.get("Initiator")!=null && !"".equals(map.get("Initiator"))){
						model.setInitiator(map.get("Initiator").toString());
					}else{
						model.setInitiator("");
					}
					
					//临时做为空判断，正常OneInitiator的值不可以为空
					if (map.get("OneInitiator") != null){
						model.setOneInitiator(map.get("OneInitiator").toString());
					}else{
						model.setOneInitiator("");
					}		
					model.setProcessInstanceId(map.get("ProcessInstanceId").toString());
					if (map.get("SendTime") != null) {
						if (!map.get("SendTime").toString().equals("")) {
							try {
								model.setSendTime(format.parse(map.get("SendTime")
										.toString()));
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}

					}
					if(map.get("Theme")!=null && !"".equals(map.get("Theme"))){
						model.setTheme(map.get("Theme").toString());
					}else{
						model.setTheme("");
					}
					if(map.get("DeptId")!=null && !"".equals(map.get("DeptId"))){
						model.setDeptId(map.get("DeptId").toString());
					}else{
						model.setDeptId("");
					}
					if(map.get("State")!=null && !"".equals(map.get("State"))){
						model.setState(Integer.parseInt(map.get("State").toString()));
					}else{
						model.setState(0);
					}
					if(map.get("Install_address")!=null && !"".equals(map.get("Install_address"))){
						model.setInstallAddress(map.get("Install_address").toString());
					}else{
						model.setInstallAddress("");
					}
					if(map.get("Barrier_number")!=null && !"".equals(map.get("Barrier_number"))){
						model.setBarrierNumber(map.get("Barrier_number").toString());
					}else{
						model.setBarrierNumber("");
					}
					if(map.get("create_time")!=null && !"".equals(map.get("create_time"))){
						if (map.get("create_time") != null) {
							if (!map.get("create_time").toString().equals("")) {
								try {
									model.setCreateTime(format.parse(map.get("create_time")
											.toString()));
								} catch (ParseException e) {
									e.printStackTrace();
								}
							}

						}
					}
					if(map.get("connect_person")!=null &&!"".equals(map.get("connect_person"))){
						model.setConnectPerson(map.get("connect_person").toString());
					}
					if(map.get("process_limit")!=null &&!"".equals(map.get("process_limit"))){
						model.setProcessLimit(map.get("process_limit").toString());
					}
					//工单历时 = 当前时间-故障发生时间：当工单历时大于处理时限，改变颜色--未处理完
				if(map.get("create_time")!=null && !"".equals(map.get("create_time"))&& map.get("SendTime") != null){
						//两时间做差
						Date startTime = new Date();
						Date endTime = model.getCreateTime();
						long sortTime = startTime.getTime()-endTime.getTime();
						long hours = sortTime / (1000 * 60 * 60 );
						long minutes = sortTime/(1000*60)-hours*60;
						long second = sortTime/1000 - hours*60*60 - minutes*60;
						String newTime = hours+"小时"+minutes+"分钟"+second+"秒";
						model.setWorkTask(newTime);
						if(model.getProcessLimit()!=null&&!"".equals(model.getProcessLimit())){
							long processLimit = Long.parseLong(model.getProcessLimit());
							if(processLimit>hours){//没有超出时限
								model.setChangeColor(true);
							}else{//超出时限
								model.setChangeColor(false);
							}
						}
						String state="";
						 if(map.get("task_def_key_")!=null && "auditor".equals(map.get("task_def_key_"))){
							state = "故障处理";
						}
						 if(map.get("repeat_state")!=null && !"".equals(map.get("repeat_state"))&&"1".equals(map.get("repeat_state").toString().trim())){
								state = "(重修)"+state;
							}
							model.setStatusName(state);
					}
					if(map.get("assignee_")!=null && !"".equals(map.get("assignee_"))){
						model.setExecutor(map.get("assignee_").toString());
					}
					r.add(model);
				}
			}
		 return r;
	}

	public List getEpiboly(String areaId, String country, String term)
	  {
	    String sql = "select tu.userid,tu.username,td.deptid,ta.areaid,ta.areaname from taw_system_area ta ,taw_system_dept td ,taw_system_user tu  where ta.areaid=td.areaid and  td.deptid=tu.deptid and  ta.areaname='" + 
	      country + 
	      "'" + 
	      " and  ta.areaid like '" + 
	      areaId + 
	      "%'" + 
	      " and  tu.userid like '%" + term + "'";
	    return getJdbcTemplate().queryForList(sql);
	  }

	  public List getTransferOfficePerson(String city)
	  {
	    String sql = "select tu.userid,tu.username,td.deptid,ta.areaid,ta.areaname from taw_system_area ta ,taw_system_dept td ,taw_system_user tu  where ta.areaid=td.areaid and  td.deptid=tu.deptid and  ta.areaname='" + 
	      city + 
	      "'" + 
	      " and  tu.userid like '%csj'";

	    return getJdbcTemplate().queryForList(sql);
	  }

	@Override
	public List judgeWorkOrderIsExit(String workOrderNumber) {
		String sql = "select * from pnr_act_transfer_office_main m where  m.process_instance_id='"+workOrderNumber+"'";
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		return list;
	}

	@Override
	public List getWorkOrderByWorkNumber(String workOrderNumber) {
		String sql ="select * from pnr_act_transfer_office_main m where m.themeinterface='maleGuest' and m.male_guest_number='"+workOrderNumber+"'";
		
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		return list;
	}

	@Override
	public List<TawCommonsAccessoriesForm> getSheetAccessoriesByPid(
			String processInstanceId) {
		
		List<TawCommonsAccessoriesForm> acessoriesList = new ArrayList<TawCommonsAccessoriesForm>();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select tc.id,tc.accessoriescnname,");
		stringBuilder.append("tc.accessoriesuploadtime,pat.step,");
		stringBuilder.append("case pat.step when '1' then m.create_work");
		stringBuilder.append(" when '2' then m.country_csj");
		stringBuilder.append(" when '3' then m.city_csj");
		stringBuilder.append(" when '4' then m.city_gs");
		stringBuilder.append(" when '5' then m.province_line");
		stringBuilder.append(" when '6' then m.province_manage  end oper ");
		stringBuilder.append(" from pnr_act_transfer_office_main m");
		stringBuilder.append(" left join pnr_act_transfer_attachment pat on m.process_instance_id=pat.process_instance_id");
		stringBuilder.append(" left join taw_commons_accessories tc on pat.accessories_id=tc.id");
		stringBuilder.append(" where m.process_instance_id='").append(processInstanceId).append("'");
		
		List<Map> list = this.getJdbcTemplate().queryForList(stringBuilder.toString());
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
					step = "方案制作";
				}else if("3".equals(map.get("step").toString().trim())){
					step = "线路审批";
				}else if("4".equals(map.get("step").toString().trim())){
					step = "市公司审批";
				}
				model.setActiveTemplateId(step);
			}
			acessoriesList.add(model);

		}
		return acessoriesList;
	}

	@Override
	public List<TawCommonsAccessoriesForm> getSheetAccessoriesByPidToPrecheck(
			String processInstanceId) {
		List<TawCommonsAccessoriesForm> acessoriesList ;
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select tc.id,tc.accessoriescnname,");
		stringBuilder.append("tc.accessoriesuploadtime,pat.step,");
		stringBuilder.append("case pat.step when '1' then cast (m.create_work as nvarchar2(32))");
		stringBuilder.append(" when '2' then m.second_creatework");
		stringBuilder.append(" when '3' then m.city_line_charge");
		stringBuilder.append(" when '4' then m.city_line_director");
		stringBuilder.append(" when '5' then m.city_manage_charge");
		stringBuilder.append(" when '6' then m.city_manage_director");
		stringBuilder.append(" when '7' then cast (m.city_gs as nvarchar2(32))");
		stringBuilder.append(" when '8' then m.province_line_charge");
		stringBuilder.append(" when '9' then cast (m.province_line as nvarchar2(32))");
		stringBuilder.append(" when '10' then m.province_manage_charge");
		stringBuilder.append(" when '11' then cast (m.province_manage as nvarchar2(32))");
		stringBuilder.append(" when '14' then cast( m.daiwei_audit_person as nvarchar2(32))");
		stringBuilder.append(" when '15' then cast(m.order_audit_person as nvarchar2(32)) end oper ");
		stringBuilder.append(" from pnr_act_transfer_office_main m");
		stringBuilder.append(" left join pnr_act_transfer_attachment pat on m.process_instance_id=pat.process_instance_id");
		stringBuilder.append(" left join taw_commons_accessories tc on pat.accessories_id=tc.id");
		stringBuilder.append(" where m.process_instance_id='").append(processInstanceId).append("'");
		stringBuilder.append(" order by tc.accessoriesuploadtime asc");
		System.out.println("-----------附件列表显示sql="+stringBuilder.toString());
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
						step = "工单发起审核";
					}else if("3".equals(map.get("step").toString().trim())){
						step = "市线路主管审核";
					}else if("4".equals(map.get("step").toString().trim())){
						step = "市线路主任审核";
					}else if("5".equals(map.get("step").toString().trim())){
						step = "市运维主管审核";
					}else if("6".equals(map.get("step").toString().trim())){
						step = "市运维主任审核";
					}else if("7".equals(map.get("step").toString().trim())){
						step = "市公司副总审核";
					}else if("8".equals(map.get("step").toString().trim())){
						step = "省线路主管审核";
					}else if("9".equals(map.get("step").toString().trim())){
						step = "省线路总经理审核";
					}else if("10".equals(map.get("step").toString().trim())){
						step = "省运维主管审核";
					}else if("11".equals(map.get("step").toString().trim())){
						step = "省运维总经理审核";
					}else if("14".equals(map.get("step").toString().trim())){
						step = "审核";
					}else if("15".equals(map.get("step").toString().trim())){
						step = "抽查";
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
     * ol-bbu机房优化流程--附件显示
     * 
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    public List<TawCommonsAccessoriesForm> showPnrOltBbuRoomSheetAccessoriesList(String processInstanceId){
		List<TawCommonsAccessoriesForm> acessoriesList ;
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select tc.id,tc.accessoriescnname,");
		stringBuilder.append("tc.accessoriesuploadtime,pat.step,");
		stringBuilder.append("case pat.step when '1' then cast (m.create_work as nvarchar2(32))");
		stringBuilder.append(" when '2' then m.city_line_charge");
		stringBuilder.append(" when '3' then m.city_manage_charge");
		stringBuilder.append(" when '4' then m.province_line_charge");
		stringBuilder.append(" when '5' then m.province_manage_charge");
		stringBuilder.append(" when '6' then m.city_manage_director");
		stringBuilder.append(" when '7' then cast (m.city_gs as nvarchar2(32))");
		stringBuilder.append(" when '8' then m.province_line_charge");
		stringBuilder.append(" when '9' then cast (m.province_line as nvarchar2(32))");
		stringBuilder.append(" when '10' then m.province_manage_charge");
		stringBuilder.append(" when '11' then cast (m.province_manage as nvarchar2(32))");
		stringBuilder.append(" when '14' then cast( m.daiwei_audit_person as nvarchar2(32))");
		stringBuilder.append(" when '15' then cast(m.order_audit_person as nvarchar2(32)) end oper ");
		stringBuilder.append(" from pnr_act_transfer_office_main m");
		stringBuilder.append(" left join pnr_act_transfer_attachment pat on m.process_instance_id=pat.process_instance_id");
		stringBuilder.append(" left join taw_commons_accessories tc on pat.accessories_id=tc.id");
		stringBuilder.append(" where m.process_instance_id='").append(processInstanceId).append("'");
		stringBuilder.append(" order by tc.accessoriesuploadtime asc");
		System.out.println("-----------附件列表显示sql="+stringBuilder.toString());
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
						step = "区县公司发起";
					}else if("2".equals(map.get("step").toString().trim())){
						step = "市线路主管审核";
					}else if("3".equals(map.get("step").toString().trim())){
						step = "市运维主管审核";
					}else if("4".equals(map.get("step").toString().trim())){
						step = "省线路主管审核";
					}else if("5".equals(map.get("step").toString().trim())){
						step = "省运维主管审核";
					}else if("6".equals(map.get("step").toString().trim())){
						step = "等待接口调用";
					}else if("7".equals(map.get("step").toString().trim())){
						step = "派发代维";
					}else if("8".equals(map.get("step").toString().trim())){
						step = "施工队施工回单";
					}else if("9".equals(map.get("step").toString().trim())){
						step = "区县公司验收";
					}else if("10".equals(map.get("step").toString().trim())){
						step = "市线路主管审批";
					}else if("11".equals(map.get("step").toString().trim())){
						step = "市运维主管审核归档";
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
	
	
	@Override
	public List<TawCommonsAccessoriesForm> getAccessoriesList(
			String processInstanceId,String flag) {
		List<TawCommonsAccessoriesForm> acessoriesList ;
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select tc.id,tc.accessoriescnname,");
		stringBuilder.append("tc.accessoriesuploadtime,pat.step,");
		stringBuilder.append("case pat.step when '1' then cast (m.create_work as nvarchar2(32))");
		stringBuilder.append(" when '2' then m.second_creatework");
		stringBuilder.append(" when '3' then m.city_line_charge");
		stringBuilder.append(" when '4' then m.city_line_director");
		stringBuilder.append(" when '5' then m.city_manage_charge");
		stringBuilder.append(" when '6' then m.city_manage_director");
		stringBuilder.append(" when '7' then cast (m.city_gs as nvarchar2(32))");
		stringBuilder.append(" when '8' then m.province_line_charge");
		stringBuilder.append(" when '9' then cast (m.province_line as nvarchar2(32))");
		stringBuilder.append(" when '10' then m.province_manage_charge");
		stringBuilder.append(" when '11' then cast (m.province_manage as nvarchar2(32))  end oper ");
		stringBuilder.append(" from pnr_act_transfer_office_main m");
		stringBuilder.append(" left join pnr_act_transfer_attachment pat on m.process_instance_id=pat.process_instance_id");
		stringBuilder.append(" left join taw_commons_accessories tc on pat.accessories_id=tc.id");
		stringBuilder.append(" where m.process_instance_id='").append(processInstanceId).append("'");
		stringBuilder.append(" order by tc.accessoriesuploadtime asc");
		System.out.println("-----------大修改造 附件列表显示sql="+stringBuilder.toString());
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
						step = "工单发起审核";
					}else if("3".equals(map.get("step").toString().trim())){
						step = "市线路发起";
					}else if("4".equals(map.get("step").toString().trim())){
						step = "市线路主任审核";
					}else if("5".equals(map.get("step").toString().trim())){
						step = "市运维主管审核";
					}else if("6".equals(map.get("step").toString().trim())){
						step = "市运维主任审核";
					}else if("7".equals(map.get("step").toString().trim())){
						step = "市副总审核";
					}else if("8".equals(map.get("step").toString().trim())){
						step = "省线路主管审核";
					}else if("9".equals(map.get("step").toString().trim())){
						step = "省线路分管经理审核";
					}else if("10".equals(map.get("step").toString().trim())){
						step = "省运维主管审核";
					}else if("11".equals(map.get("step").toString().trim())){
						step = "省运维分管经理审批";
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
	
	@Override
	public List<TawCommonsAccessoriesForm> getRoomDemolitionAccessoriesList(
			String processInstanceId,String flag) {
		List<TawCommonsAccessoriesForm> acessoriesList ;
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("select tc.id,tc.accessoriescnname,");
		stringBuilder.append("tc.accessoriesuploadtime,pat.step,");
		stringBuilder.append("case pat.step when '1' then m.country_charge");
		stringBuilder.append(" when '2' then m.City_Manage_Charge");
		stringBuilder.append(" when '3' then m.province_Manage_Charge");
		stringBuilder.append(" when '4' then m.country_charge");
		stringBuilder.append(" when '5' then m.City_Manage_Charge");
		stringBuilder.append(" when '6' then m.province_Manage_Charge");
		stringBuilder.append(" when '7' then m.province_Manage_Charge end oper ");
		stringBuilder.append(" from pnr_act_roomdemolition m");
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
						step = "区县维护中心主管工单发起";
					}else if("2".equals(map.get("step").toString().trim())){
						step = "市公司运维部主管审批";
					}else if("3".equals(map.get("step").toString().trim())){
						step = "省公司运维部主管审批";
					}else if("4".equals(map.get("step").toString().trim())){
						step = "区县维护中心主管回单";
					}else if("5".equals(map.get("step").toString().trim())){
						step = "市公司运维部主管验收";
					}else if("6".equals(map.get("step").toString().trim())){
						step = "省公司运维部主管审核";
					}else if("7".equals(map.get("step").toString().trim())){
						step = "省公司运维部主管手动归档";
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
	
	

	@Override
	public String judgeOrderIsDo(String maleGuestNumber) {
		
		String sql = "select m.state from pnr_act_transfer_office_main m where m.male_guest_number = '"+maleGuestNumber+"' and m.do_flag is null";
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		String flag = "1";
		if(list!=null && list.size()>0){
			String state = list.get(0).get("STATE").toString();
			state = state.trim();
			if("0".equals(state)){
				flag = "2";
			}else if("8".equals(state)){
				flag = "3";
			}
		}
		return flag;
	}

	@Override
	public boolean deleteCountersignInfo(String processInstanceId) {
	
		String sql = "delete from pnr_act_ru_countersign pn where pn.process_instance_id=?";
	     final String processInstanceIdStr = processInstanceId;
			this.getJdbcTemplate().execute(sql, new PreparedStatementCallback(){
				@Override
				public Object doInPreparedStatement(PreparedStatement ps)
						throws SQLException, DataAccessException {
					
					ps.setString(1, processInstanceIdStr);
					
					return ps.execute();
				}
				
			});
		return false;
	}

	@Override
	public boolean insertCountersignInfo(PnrActRuCountersign pnrActRuCountersign) throws Exception {
		String sql = "insert into pnr_act_ru_countersign(ID,PROCESS_INSTANCE_ID,USER_ID) values(?,?,?)";
        final String uid =UUIDHexGenerator.getInstance().getID();
        final String processInstanceId = pnrActRuCountersign.getProcessInstanceId();
        final String userId = pnrActRuCountersign.getUserId();
		this.getJdbcTemplate().execute(sql, new PreparedStatementCallback(){

			@Override
			public Object doInPreparedStatement(PreparedStatement ps)
					throws SQLException, DataAccessException {
				ps.setString(1, uid);
				ps.setString(2, processInstanceId);
				ps.setString(3, userId);
				return ps.execute();
			}
			
		});
		return false;
	}

	@Override
	public int getCountersignCountByProcessInstanceId(String processInstanceId) {
		String sql = "select g.process_instance_id,g.user_id from pnr_act_ru_countersign g where g.process_instance_id=? group by g.process_instance_id,g.user_id";
        
        int num = 0;
        String[] args ={processInstanceId};
        List  list=this.getJdbcTemplate().queryForList(sql, args);
        num = list.size();
        
		return num;
	}

	@Override
	public String getLoginPersonStatusToOverhaulRemake(String userId,
			PnrTransferOffice pnrTransferOffice) {
		//市线路派发
		String cityLineExamine = pnrTransferOffice.getCreateWork();
		//市运维人员
		String cityManageExamine=pnrTransferOffice.getCityGS();
		String tempTask=pnrTransferOffice.getCityCSJ();
		//省线路人员
		String provinceLineExamine= pnrTransferOffice.getProvinceLine();
		//代维公司人员
		String daiwei = pnrTransferOffice.getInitiator();
		 if (cityLineExamine != null && userId.equals(cityLineExamine)) {
			return "cityLineExamine";
		} else if (cityManageExamine != null && userId.equals(cityManageExamine)) {
			return "cityOperationExamine";
		} else if (tempTask != null && userId.equals(tempTask)) {
			return "tempTask";
		} else if (provinceLineExamine != null && userId.equals(provinceLineExamine)) {
			return "provinceLineExamine";
		}else if (daiwei != null && userId.equals(daiwei)) {
			return "daiwei";
		}else{
			return "";
		}
	}

	@Override
	public synchronized int getCurrDateSheetCountNum(String sequenceName) {
		int maxValue = 0;
		String sql = "select "+sequenceName+".NEXTVAL as A from dual";
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		
		if(list!=null && list.size()>0){
			 maxValue = Integer.parseInt( list.get(0).get("A").toString());
		
		}
		return maxValue;
	}

	@Override
	public String getSheetAccessoriesByCity(String cityId) {
		String ids="";
//		cityId ="28";
       /* String sql = "select distinct s.id from pnr_act_transfer_office_main  n " +
        		" left join taw_system_area a on n.city=a.areaid " +
        		" left join pnr_act_transfer_attachment t on n.process_instance_id=t.process_instance_id" +
        		" left join taw_commons_accessories s on t.accessoriesname=s.accessoriesname" +
        		" left join act_ru_task k on n.process_instance_id=k.proc_inst_id_" +
        		" where k.assignee_ in ('sd-ywb','sd-ywb-fgzjl','sd-xlzx-fgzjl','wangyong','zhanghaiqiang')" +
        		" and n.city='"+cityId+"' and s.id is not null";    */    
        String sql = "select distinct s.id from pnr_act_transfer_office_main  n " +
        " left join taw_system_area a on n.city=a.areaid " +
        " left join pnr_act_transfer_attachment t on n.process_instance_id=t.process_instance_id" +
        " left join taw_commons_accessories s on t.accessoriesname=s.accessoriesname" +
        " left join act_ru_task k on n.process_instance_id=k.proc_inst_id_" +
        " where n.city='"+cityId+"' and s.id is not null" ;
      
        List<Map>  list=this.getJdbcTemplate().queryForList(sql);
//        List  list=this.getJdbcTemplate()
        int  num = list.size();
		
        for(int i=0;i<num;i++ ){
        	ids+=","+list.get(i).get("ID");
        }
		
//        ids = ids.substring(1,ids.length());
		return ids;
	}

	@Override
	public Map<String, String> getSheetAccessoriesByName(String cnames) {
		String sql = "select a.areaname CITYNAME,b.areaname,t.accessoriesname ,s.accessoriescnname,s.accessoriespath,n.process_instance_id from pnr_act_transfer_office_main  n" +
				" left join taw_system_area a on n.city=a.areaid" +
				" left join taw_system_area b on n.country=b.areaid"+
				" left join pnr_act_transfer_attachment t on n.process_instance_id=t.process_instance_id"+
				" left join taw_commons_accessories s on t.accessoriesname=s.accessoriesname"+
				" left join act_ru_task k on n.process_instance_id=k.proc_inst_id_"+
				" where k.assignee_ in ('sd-ywb','sd-ywb-fgzjl','sd-xlzx-fgzjl','wangyong','zhanghaiqiang')" +
				" and s.accessoriesname='"+cnames+"'" ;		
			
				List<Map> list = this.getJdbcTemplate().queryForList(sql);
				
				return list.size()>0?list.get(0):null;
	}
	
	public void deleteAttachmentNamesByProcessInstanceIdAndStep(String processInstanceId,
			String Step) {
		String delSql = "delete from pnr_act_transfer_attachment where process_instance_id ='"+processInstanceId+"' and step = '"+Step+"'";
		this.getJdbcTemplate().execute(delSql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement ps)
					throws SQLException, DataAccessException {
				ps.addBatch();
				return ps.executeBatch();
			}
		});
	}

	@Override
	public Map<String, String> getAvgByProcessInstanceId(
			String processInstanceId) {
		
		String[] args = { processInstanceId };
		String sql = "select cast (avg(ph.longitude) as varchar2(32)) longitude,cast(avg(ph.latitude) as varchar2(32)) latitude from pnr_act_precheck_photo_ship pi left join pnr_android_photo ph on pi.photo_id = ph.id where pi.processinstance_id =?";
		List<Map> list = this.getJdbcTemplate().queryForList(sql, args);

		int size = list.size();
		Map<String,String> avgMap = null;
		if (size > 0) {
			avgMap = list.get(0);
		}
		return avgMap;
	}

	@Override
	public Map<String, String> getAndroidLimitDistance(String processKey) {

		Map<String, String> map =null;
		String[] args = { processKey };
		String sql = "select dis.handle_distance,dis.spotcheck_distance from pnr_act_android_distance dis where dis.process_key=?";
		List<Map> list = this.getJdbcTemplate().queryForList(sql, args);

		int size = list.size();
		if (size > 0) {			
			map = list.get(0);
		}
		return map;
	}
	
	/**
	 * 施工队之后的审核环节界面上的距离显示，没有距离默认为"无"
	 * @param processInstanceId
	 * @param type
	 * @return
	 */
	public String getDistanceShow(String processInstanceId, String type) {
		String sql = "select avg(p.distance) as avgDistance from pnr_android_workorder_photo p where p.picture_id='"
				+ processInstanceId + "' and p.id_type='" + type + "'";
		System.out.println("---------------获取距离sql=" + sql);
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		String avgDistance = "无";
		if (list != null && list.size() > 0) {
			if (list.get(0).get("avgDistance") != null) {
				avgDistance = list.get(0).get("avgDistance").toString();
			}
		}
		return avgDistance;
	}

	@Override
	public int getAndroidTaskCount(AndroidQuery androidQuery) {
		
		
		String userId = androidQuery.getUserId();//用户名
		
		String processDefinitionKey=androidQuery.getProcessDefinitionKey();//流程key
		
		String theme = androidQuery.getTheme();//主题
		
		String sendTime= androidQuery.getSendTime();//派单时间
		
		String workOrderType=androidQuery.getWorkOrderType();//工单类型：大修改造工单、本地网预检预修工单、干线预检预修工单、公客故障工单、抢修工单、机房拆除工单
		
		String workOrderState = androidQuery.getWorkOrderState();//工单状态：处理、抽查等
		
		String city = androidQuery.getCity();//地市
		
		String country = androidQuery.getCountry();//区县
		
		if("maleGuest".equals(workOrderType)){
			return returnGKAndroidTaskCount(androidQuery);
		}else if("transferOffice".equals(workOrderType)){
			return returnTransferOfficeAndroidTaskCount(androidQuery);

		}
		
		ArrayList listargs = new ArrayList();
		
		//机房拆除还是其他工单
		String transferOfficeOrRoom = "pnr_act_transfer_office_main";//包括工单：大修改造工单、本地网预检预修工单、干线预检预修工单
		if("RoomDemolition".equals(workOrderType)){//由于机房拆除工单没有存在pnr_act_transfer_office_main表中，所以要和其他工单区别开
			transferOfficeOrRoom = "pnr_act_roomdemolition";
		}else if("maleGuest".equals(workOrderType)||"transferOffice".equals(workOrderType)){//包括工单：抢修工单、公客故障工单
			transferOfficeOrRoom = 
				"(select pm.process_instance_id," +
				"       pm.state," + 
				"       pm.task_def_key," + 
				"       pm.task_id," + 
				"       pm.ASSIGNEE," + 
				"       pm.themeInterface," + 
				"       pm.theme," + 
				"       pm.send_time," + 
				"       pm.do_flag," + 
				"       pm.male_guest_state," + 
				"       pm.worker_scene_handle_flag," + 
				"       (select ar.parentareaid" + 
				"          from taw_system_area ar" + 
				"         where ar.areaid = (select d.areaid" + 
				"                              from taw_system_dept d" + 
				"                             where d.deleted = 0" + 
				"                               and d.deptid = (select u.deptid" + 
				"                                                 from taw_system_user u" + 
				"                                                where u.deleted = 0" + 
				"                                                  and u.userid = pm.initiator" + 
				"                                               ))) city," + 
				"       (select d.areaid" + 
				"          from taw_system_dept d" + 
				"         where d.deleted = 0" + 
				"           and d.deptid = (select u.deptid" + 
				"                             from taw_system_user u" + 
				"                            where u.deleted = 0" + 
				"                              and u.userid = pm.initiator" + 
				"                           )) country" + 
				"  from pnr_act_transfer_office_main pm)";
		}

		String sql = "select count(id_) as total from (";
	
		String selectSql ="select m.task_id as id_, m.process_instance_id as proc_inst_id_, m.task_def_key as task_def_key_ from "+transferOfficeOrRoom+" m ";
			
		String whereSql = " where m.assignee = ?";//工单状态不为10,去掉手机端已抽查等待WEB端处理的工单
		listargs.add(userId);
		
		
		if("maleGuest".equals(workOrderType)){//流程类型	公客工单状态为8时是已处理完毕的
			whereSql = whereSql+" and m.state<>8 and m.do_flag is null and nvl(m.male_guest_state,'0') in ('0','1','3') ";
		}else{
			whereSql = whereSql+" and m.state<>10";
		}
		
		if("transferInterface".equals(workOrderType)){//流程类型 本地网有三个版本比较特殊
//			whereSql = whereSql+" and f.key_ in ("+processDefinitionKey+")";
			whereSql = whereSql+" and nvl(m.worker_scene_handle_flag,'-1')!= '1'";
			whereSql = whereSql+" and nvl(m.daiweiaudit_scene_handle_flag,'-1')!= '1'";
			whereSql = whereSql+" and nvl(m.worker_scene_order_audit_flag,'-1')!= '1'";
		}else if("oltBbu".equals(workOrderType)){
//			whereSql = whereSql+" and f.key_ = '"+processDefinitionKey+"'";
			whereSql = whereSql+" and nvl(m.worker_scene_handle_flag,'-1')!= '1'";
			whereSql = whereSql+" and nvl(m.worker_scene_order_audit_flag,'-1')!= '1'";
		}else if("arteryPrecheck".equals(workOrderType)){ //干线
			whereSql = whereSql+" and nvl(m.daiweiaudit_scene_handle_flag,'-1')!= '1'";
		}
		
		if(workOrderState!=null&&!"".equals(workOrderState)&&!"allState".equals(workOrderState)){//状态			
			if("merge".equals(workOrderState)){//merge 归集
				whereSql = whereSql+" and m.task_def_key ='auditor' and nvl(m.male_guest_state,'0')='1'";
			}else if("alone".equals(workOrderState)){//alone 未归集，
				whereSql = whereSql+" and m.task_def_key ='auditor' and nvl(m.male_guest_state,'0') in ('0','3')";
			}else{
				
				whereSql = whereSql+" and m.task_def_key ='"+workOrderState+"'";
			}
			
		}else{
			if("transferOffice".equals(workOrderType)){//抢修工单160608update
				whereSql = whereSql +" and m.task_def_key in ('transferHandle','transferTask','epibolyTask','twoSpotChecks') ";
//				whereSql = whereSql +" and m.themeInterface ='transferOffice' ";
				
			}else if("transferInterface".equals(workOrderType)){//本地网预检预修工单
				whereSql = whereSql +" and m.task_def_key in ('worker','daiweiAudit','orderAudit') ";//160824添加daiweiAudit环节
//				whereSql = whereSql +" and m.themeInterface ='interface' ";
				
			}else if("maleGuest".equals(workOrderType)){//公客故障工单
				whereSql = whereSql +" and m.task_def_key in ('auditor','twoSpotChecks') ";
//				whereSql = whereSql +" and m.themeInterface ='maleGuest' ";
				
			}else if("arteryPrecheck".equals(workOrderType)){//干线预检预修工单
				whereSql = whereSql +" and m.task_def_key in ('worker','daiweiAudit','orderAudit') ";//160824添加daiweiAudit环节
//				whereSql = whereSql +" and m.themeInterface ='arteryPrecheck' ";
				
			}else if("overHaul".equals(workOrderType)){//大修改造工单
				whereSql = whereSql +" and m.task_def_key in ('worker','orderAudit') ";
//				whereSql = whereSql +" and m.themeInterface ='overhaul' ";
				
			}else if("RoomDemolition".equals(workOrderType)){//机房拆除工单
				whereSql = whereSql +" and m.task_def_key in ('worker','orderAudit') ";
			}else if("oltBbu".equals(workOrderType)){//olt-bbu
				whereSql = whereSql +" and m.task_def_key in ('worker','orderAudit') ";
//				whereSql = whereSql +" and m.themeInterface ='oltBbuProcess' ";
				
			}
		}
		
		if("transferOffice".equals(workOrderType)||"maleGuest".equals(workOrderType)||"arteryPrecheck".equals(workOrderType)){
			whereSql = whereSql +" and m.themeInterface ='"+workOrderType+"' ";			
		}else if("transferInterface".equals(workOrderType)){
			whereSql = whereSql +" and m.themeInterface ='interface' ";
		}else if("oltBbu".equals(workOrderType)){
			whereSql = whereSql +" and m.themeInterface ='oltBbuProcess' ";
		}else if ("overHaul".equals(workOrderType)){
			whereSql = whereSql +" and m.themeInterface ='overhaul' ";
		}
		
		if(theme!=null&&!"".equals(theme)){//主题
			whereSql = whereSql+" and instr(m.theme,?) > 0";
			listargs.add(theme);

		}
		
		if(sendTime!=null&&!"".equals(sendTime)){//时间
			whereSql = whereSql+" and m.send_time>=to_date(?,'yyyy-mm-dd hh24:mi:ss')";
			listargs.add(sendTime);

		}
		
		if(city!=null&&!"".equals(city)){//地市
			whereSql = whereSql+" and m.city =?";
			listargs.add(city);

		}
		if(country!=null&&!"".equals(country)){//区县
			whereSql = whereSql+" and m.country =?";
			listargs.add(country);

		}	
			
		sql += selectSql + whereSql+")";
		
		Object[] args = listargs.toArray();
		System.out.println("--PnrTransferOfficeDaoJDBC--getAndroidTaskCount--手机工单查询条数sql="+sql);
		
		int size = this.getJdbcTemplate().queryForInt(sql,args);
		return size;
	
	}
/**   2015-12-22 改造工单
 * 	//机房拆除还是其他工单
		String transferOfficeOrRoom = "pnr_act_transfer_office_main";//包括工单：抢修工单、大修改造工单、本地网预检预修工单、干线预检预修工单、公客故障工单
		if("RoomDemolition".equals(workOrderType)){//由于机房拆除工单没有存在pnr_act_transfer_office_main表中，所以要和其他工单区别开
			transferOfficeOrRoom = "pnr_act_roomdemolition";
		}else if("maleGuest".equals(workOrderType)||"transferOffice".equals(workOrderType)){//包括工单：抢修工单、公客故障工单
			transferOfficeOrRoom = 
				"(select pm.process_instance_id," +
				"       pm.state," + 
				"       pm.theme," + 
				"       pm.send_time," + 
				"       pm.do_flag," + 
				"       pm.worker_scene_handle_flag," + 
				"       (select ar.parentareaid" + 
				"          from taw_system_area ar" + 
				"         where ar.areaid = (select d.areaid" + 
				"                              from taw_system_dept d" + 
				"                             where d.deleted = 0" + 
				"                               and d.deptid = (select u.deptid" + 
				"                                                 from taw_system_user u" + 
				"                                                where u.deleted = 0" + 
				"                                                  and u.userid = pm.initiator" + 
				"                                               ))) city," + 
				"       (select d.areaid" + 
				"          from taw_system_dept d" + 
				"         where d.deleted = 0" + 
				"           and d.deptid = (select u.deptid" + 
				"                             from taw_system_user u" + 
				"                            where u.deleted = 0" + 
				"                              and u.userid = pm.initiator" + 
				"                           )) country" + 
				"  from pnr_act_transfer_office_main pm)";
		}

		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		String selectSql =
			"select t.id_, t.proc_inst_id_, t.task_def_key_ " +
			"                  from act_ru_task t " + 
			"                  left join act_re_procdef f " + 
			"                    on t.proc_def_id_ = f.id_ " + 
			"                  left join "+transferOfficeOrRoom+" m " + 
			"                    on t.proc_inst_id_ = m.process_instance_id";
 */
	@Override
	public List<AndroidWorkOrderTask> getAndroidTaskList(
			AndroidQuery androidQuery, int pageIndex, int pageSize) {
		
		String userId = androidQuery.getUserId();//用户名
		
		String processDefinitionKey=androidQuery.getProcessDefinitionKey();//流程key
		
		String theme = androidQuery.getTheme();//主题
		
		String sendTime= androidQuery.getSendTime();//派单时间
		
		String workOrderType=androidQuery.getWorkOrderType();//工单类型：大修改造工单、本地网预检预修工单、干线预检预修工单、公客故障工单、抢修工单、机房拆除工单
		
		String workOrderState = androidQuery.getWorkOrderState();//工单状态：处理、抽查等
		
		String city = androidQuery.getCity();//地市
		
		String country = androidQuery.getCountry();//区县
		
		if("maleGuest".equals(workOrderType)){
			return returnGKAndroidTaskList(androidQuery,pageIndex,pageSize);
		}else if("transferOffice".equals(workOrderType)){
			return returnTransferOfficeAndroidTaskList(androidQuery,pageIndex,pageSize);

		}
		
		
		//机房拆除还是其他工单
		String transferOfficeOrRoom = "pnr_act_transfer_office_main";//包括工单：抢修工单、大修改造工单、本地网预检预修工单、干线预检预修工单、公客故障工单
		if("RoomDemolition".equals(workOrderType)){//由于机房拆除工单没有存在pnr_act_transfer_office_main表中，所以要和其他工单区别开
			transferOfficeOrRoom = "pnr_act_roomdemolition";
		}else if("maleGuest".equals(workOrderType)||"transferOffice".equals(workOrderType)){//包括工单：抢修工单、公客故障工单
			transferOfficeOrRoom = "pnr_act_transfer_office_main";
		}

		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		String selectSql =
			"select m.task_id as id_, m.process_instance_id as proc_inst_id_, m.task_def_key as task_def_key_ " +
			"                  from "+transferOfficeOrRoom+" m ";
			
		String whereSql = " where m.assignee = '"+userId+"'";//工单状态不为10,去掉手机端已抽查等待WEB端处理的工单
		
		if("maleGuest".equals(workOrderType)){//流程类型	公客工单状态为8时是已处理完毕的
			whereSql = whereSql+" and m.state<>8 and m.do_flag is null and nvl(m.male_guest_state,'0') in ('0','1','3')";
		}else{
			whereSql = whereSql+" and m.state<>10";
		}
		
		if("transferInterface".equals(workOrderType)){//流程类型 本地网有三个版本比较特殊
//			whereSql = whereSql+" and f.key_ in ("+processDefinitionKey+")";
			whereSql = whereSql+" and nvl(m.worker_scene_handle_flag,'-1')!= '1'";
			whereSql = whereSql+" and nvl(m.daiweiaudit_scene_handle_flag,'-1')!= '1'";
			whereSql = whereSql+" and nvl(m.worker_scene_order_audit_flag,'-1')!= '1'";
//			whereSql = whereSql+" and nvl(m.worker_scene_order_audit_flag,'-1')!= '1'";

		}else if("oltBbu".equals(workOrderType)){
//			whereSql = whereSql+" and f.key_ = '"+processDefinitionKey+"'";
			whereSql = whereSql+" and nvl(m.worker_scene_handle_flag,'-1')!= '1'";
			whereSql = whereSql+" and nvl(m.worker_scene_order_audit_flag,'-1')!= '1'";
		}else if("arteryPrecheck".equals(workOrderType)){ //干线
			whereSql = whereSql+" and nvl(m.daiweiaudit_scene_handle_flag,'-1')!= '1'";
			whereSql = whereSql+" and nvl(m.worker_scene_order_audit_flag,'-1')!= '1'";
		}
		
		if(workOrderState!=null&&!"".equals(workOrderState)&&!"allState".equals(workOrderState)){//状态
			
			if("merge".equals(workOrderState)){//merge 归集
				whereSql = whereSql+" and m.task_def_key ='auditor' and nvl(m.male_guest_state,'0')='1'";
			}else if("alone".equals(workOrderState)){//alone 未归集，
				whereSql = whereSql+" and m.task_def_key ='auditor' and nvl(m.male_guest_state,'0') in ('0','3')";
			}else{
				
				whereSql = whereSql+" and m.task_def_key ='"+workOrderState+"'";
			}
		}else{
			if("transferOffice".equals(workOrderType)){//抢修工单160608update
				whereSql = whereSql +" and m.task_def_key in ('transferHandle','transferTask','epibolyTask','twoSpotChecks') ";
//				whereSql = whereSql +" and m.themeInterface ='transferOffice' ";
				
			}else if("transferInterface".equals(workOrderType)){//本地网预检预修工单
				whereSql = whereSql +" and m.task_def_key in ('worker','daiweiAudit','orderAudit') ";//160824添加daiweiAudit环节
//				whereSql = whereSql +" and m.themeInterface ='interface' ";
				
			}else if("maleGuest".equals(workOrderType)){//公客故障工单
				whereSql = whereSql +" and m.task_def_key in ('auditor','twoSpotChecks') ";
//				whereSql = whereSql +" and m.themeInterface ='maleGuest' ";
				
			}else if("arteryPrecheck".equals(workOrderType)){//干线预检预修工单
				whereSql = whereSql +" and m.task_def_key in ('worker','daiweiAudit','orderAudit') ";//160824添加daiweiAudit环节
//				whereSql = whereSql +" and m.themeInterface ='arteryPrecheck' ";
				
			}else if("overHaul".equals(workOrderType)){//大修改造工单
				whereSql = whereSql +" and m.task_def_key in ('worker','orderAudit') ";
//				whereSql = whereSql +" and m.themeInterface ='overhaul' ";
				
			}else if("RoomDemolition".equals(workOrderType)){//机房拆除工单
				whereSql = whereSql +" and m.task_def_key in ('worker','orderAudit') ";
			}else if("oltBbu".equals(workOrderType)){//olt-bbu
				whereSql = whereSql +" and m.task_def_key in ('worker','orderAudit') ";
//				whereSql = whereSql +" and m.themeInterface ='oltBbuProcess' ";
				
			}
		}
		
		if("transferOffice".equals(workOrderType)||"maleGuest".equals(workOrderType)||"arteryPrecheck".equals(workOrderType)){
			whereSql = whereSql +" and m.themeInterface ='"+workOrderType+"' ";			
		}else if("transferInterface".equals(workOrderType)){
			whereSql = whereSql +" and m.themeInterface ='interface' ";
		}else if("oltBbu".equals(workOrderType)){
			whereSql = whereSql +" and m.themeInterface ='oltBbuProcess' ";
		}else if("overHaul".equals(workOrderType)){
			whereSql = whereSql +" and m.themeInterface ='overhaul' ";
		}
		
		if(theme!=null&&!"".equals(theme)){//主题
			whereSql = whereSql+" and instr(m.theme,'"+theme+"') > 0";
		}
		
		if(sendTime!=null&&!"".equals(sendTime)){//时间
			whereSql = whereSql+" and m.send_time>=to_date('"+sendTime+"','yyyy-mm-dd hh24:mi:ss')";
		}
		
		if(city!=null&&!"".equals(city)){//地市
			whereSql = whereSql+" and m.city ='"+city+"'";
		}
		if(country!=null&&!"".equals(country)){//区县
			whereSql = whereSql+" and m.country ='"+country+"'";
		}	
			
		sql += selectSql + whereSql
		+ " order by m.send_time) temp1 where rownum <="
		+ (pageIndex+1)*pageSize + ") temp2 where temp2.num > "
		+ pageIndex*pageSize;
		
		System.out.println("----PnrTransferOfficeDaoJDBC--getAndroidTaskList--手机工单查询sql="+sql);
		
		List<AndroidWorkOrderTask> workOrderTask = new ArrayList<AndroidWorkOrderTask>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		if(list!=null && list.size()>0){
			for(Map map:list){
				AndroidWorkOrderTask task = new AndroidWorkOrderTask();
				task.setTaskWworkOrderId(map.get("id_")!=null?map.get("id_").toString():"");
				task.setTaskDefinitionKey(map.get("task_def_key_")!=null?map.get("task_def_key_").toString():"");
				task.setProcessInstanceId(map.get("proc_inst_id_")!=null?map.get("proc_inst_id_").toString():"");
				workOrderTask.add(task);
			}
		}
		return workOrderTask;
	}
	/**
	 * 公客返回大小
	 * @param androidQuery
	 * @return
	 */
	private int returnGKAndroidTaskCount(AndroidQuery androidQuery){
		
		Map<String,Object>  map =returnGKAndroidTaskWhereCondition(androidQuery);
		String whereSql = map.get("whereSql")+"";
		ArrayList listargs =(ArrayList) map.get("listargs");
		
		String sql = "select count(id_) as total from (";
	
		String selectSql =" select m.task_id as id_, m.process_instance_id as proc_inst_id_, " +
		" m.task_def_key as task_def_key_,sysdate,m.country from pnr_act_transfer_office_main m " +
		" where m.task_def_key='auditor' " +whereSql+
		" union all" +
		" select m.task_id as id_, m.process_instance_id as proc_inst_id_, " +
		" m.task_def_key as task_def_key_ ,rt.report_date,rt.county  from pnr_act_transfer_office_main m" +
		" left join pnr_second_inspection_report rt on m.process_instance_id=rt.process_instance_id" +
		" where  m.task_def_key='twoSpotChecks' and rt.APPROVE_FLAG='0' and sysdate -rt.rise_time <= 3 "
		+whereSql;
		
		sql += selectSql+")";
		
		listargs.addAll(listargs);
		Object[] args = listargs.toArray();
		
		int size = this.getJdbcTemplate().queryForInt(sql,args);
		return size;
	}
	/**
	 * 公客系统条件
	 * @param androidQuery
	 * @return
	 */
	private Map<String,Object> returnGKAndroidTaskWhereCondition(AndroidQuery androidQuery){
	     	ArrayList listargs = new ArrayList();
	     	Map<String,Object> map = new HashMap<String,Object>();
	     	
		    String userId = androidQuery.getUserId();//用户名			
			String processDefinitionKey=androidQuery.getProcessDefinitionKey();//流程key
			
			String theme = androidQuery.getTheme();//主题
			
			String sendTime= androidQuery.getSendTime();//派单时间
			
			String workOrderType=androidQuery.getWorkOrderType();//工单类型：大修改造工单、本地网预检预修工单、干线预检预修工单、公客故障工单、抢修工单、机房拆除工单
			
			String workOrderState = androidQuery.getWorkOrderState();//工单状态：处理、抽查等
			
			String city = androidQuery.getCity();//地市
			
			String country = androidQuery.getCountry();//区县
			
		
		String whereSql = " and m.assignee =?";
		listargs.add(userId);
		whereSql = whereSql+" and m.state<>8 and m.do_flag is null and nvl(m.male_guest_state,'0') in ('0','1','3')";
		
		if(workOrderState!=null&&!"".equals(workOrderState)&&!"allState".equals(workOrderState)){//状态
			
			if("merge".equals(workOrderState)){//merge 归集
				whereSql = whereSql+" and m.task_def_key ='auditor' and nvl(m.male_guest_state,'0')='1'";
			}else if("alone".equals(workOrderState)){//alone 未归集，
				whereSql = whereSql+" and m.task_def_key ='auditor' and nvl(m.male_guest_state,'0') in ('0','3')";
			}else{
				
				whereSql = whereSql+" and m.task_def_key =?";
				listargs.add(workOrderState);
			}
		}
		
		whereSql = whereSql +" and m.themeInterface =? ";			
		listargs.add(workOrderType);

		
		if(theme!=null&&!"".equals(theme)){//主题
			whereSql = whereSql+" and instr(m.theme,?) > 0";
			listargs.add(theme);

		}
		
		if(sendTime!=null&&!"".equals(sendTime)){//时间
			whereSql = whereSql+" and m.send_time>=to_date(?,'yyyy-mm-dd hh24:mi:ss')";
			listargs.add(sendTime);

		}
		
		if(city!=null&&!"".equals(city)){//地市
			whereSql = whereSql+" and m.city ='"+city+"'";
			listargs.add(city);

		}
		if(country!=null&&!"".equals(country)){//区县
			whereSql = whereSql+" and m.country ='"+country+"'";
			listargs.add(country);

		}	
		map.put("whereSql", whereSql);
		map.put("listargs", listargs);

		return map;
	}
	
	
	/**
	 * 公客返回数据
	 * @param androidQuery
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	private List<AndroidWorkOrderTask> returnGKAndroidTaskList(AndroidQuery androidQuery, int pageIndex, int pageSize){
		
		Map<String,Object>  mapWhere =returnGKAndroidTaskWhereCondition(androidQuery);
		String whereSql = mapWhere.get("whereSql")+"";
		ArrayList listargs =(ArrayList) mapWhere.get("listargs");
		
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		
				
		String selectSql =" select m.send_time,m.task_id as id_, m.process_instance_id as proc_inst_id_, " +
		" m.task_def_key as task_def_key_,trunc(sysdate) tdate,m.country from pnr_act_transfer_office_main m " +
		" where m.task_def_key='auditor' " +whereSql+
		" union all" +
		" select m.send_time,m.task_id as id_, m.process_instance_id as proc_inst_id_, " +
		" m.task_def_key as task_def_key_ ,trunc(rt.report_date),rt.county  from pnr_act_transfer_office_main m" +
		" left join pnr_second_inspection_report rt on m.process_instance_id=rt.process_instance_id" +
		" where  m.task_def_key='twoSpotChecks' and rt.APPROVE_FLAG='0' and " +
		" sysdate -rt.rise_time <= 3"
		+whereSql;
		
		
		sql += selectSql
		+ " order by send_time) temp1 where rownum <=?) temp2 where temp2.num >? ";
		listargs.addAll(listargs);

		listargs.add((pageIndex+1)*pageSize);
		listargs.add(pageIndex*pageSize);

		System.out.println("----PnrTransferOfficeDaoJDBC--getAndroidTaskList--公客手机工单查询sql="+sql);
		Object[] args = listargs.toArray();
		
		List<AndroidWorkOrderTask> workOrderTask = new ArrayList<AndroidWorkOrderTask>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,args);
		if(list!=null && list.size()>0){
			for(Map map:list){
				AndroidWorkOrderTask task = new AndroidWorkOrderTask();
				task.setTaskWworkOrderId(map.get("id_")!=null?map.get("id_").toString():"");
				task.setTaskDefinitionKey(map.get("task_def_key_")!=null?map.get("task_def_key_").toString():"");
				task.setProcessInstanceId(map.get("proc_inst_id_")!=null?map.get("proc_inst_id_").toString():"");
				task.setTcountry(map.get("country")!=null?map.get("country").toString():"");
				task.setTdate(map.get("tdate")!=null?dateFormat.format((Date)map.get("tdate")):"");
				workOrderTask.add(task);
			}
		}
		return workOrderTask; 
	}
	/**
	 * 抢修大小
	 * @param androidQuery
	 * @return
	 */
	private int returnTransferOfficeAndroidTaskCount(AndroidQuery androidQuery){
		Map<String,Object>  map =returnTransferOfficeAndroidTaskWhereCondition(androidQuery);
		String whereSql = map.get("whereSql")+"";
		ArrayList listargs =(ArrayList) map.get("listargs");
		
		String sql = "select count(id_) as total from (";
	
		String selectSql ="select m.task_id as id_, m.process_instance_id as proc_inst_id_, m.task_def_key as task_def_key_,sysdate,m.country " +
		"                  from pnr_act_transfer_office_main m where m.task_def_key in ('transferHandle','transferTask','epibolyTask')  "+whereSql+
		" union all" +
		" select m.task_id as id_, m.process_instance_id as proc_inst_id_, " +
		" m.task_def_key as task_def_key_ ,rt.report_date,rt.county  from pnr_act_transfer_office_main m" +
		" left join pnr_second_inspection_report rt on m.process_instance_id=rt.process_instance_id" +
		" where  m.task_def_key='twoSpotChecks' and rt.APPROVE_FLAG='0' and " +
		" sysdate -rt.rise_time <= 3"
		+whereSql;
		
		sql += selectSql+")";
		
		listargs.addAll(listargs);
		Object[] args = listargs.toArray();
		
		int size = this.getJdbcTemplate().queryForInt(sql,args);
		return size;
	}
	/**
	 * 抢修条件
	 * @param androidQuery
	 * @return
	 */
	private Map<String,Object> returnTransferOfficeAndroidTaskWhereCondition(AndroidQuery androidQuery){
		ArrayList listargs = new ArrayList();
     	Map<String,Object> map = new HashMap<String,Object>();
     	
	    String userId = androidQuery.getUserId();//用户名			
		String processDefinitionKey=androidQuery.getProcessDefinitionKey();//流程key
		
		String theme = androidQuery.getTheme();//主题
		
		String sendTime= androidQuery.getSendTime();//派单时间
		
		String workOrderType=androidQuery.getWorkOrderType();//工单类型：大修改造工单、本地网预检预修工单、干线预检预修工单、公客故障工单、抢修工单、机房拆除工单
		
		String workOrderState = androidQuery.getWorkOrderState();//工单状态：处理、抽查等
		
		String city = androidQuery.getCity();//地市
		
		String country = androidQuery.getCountry();//区县
		
	
		String whereSql = " and m.assignee =?";//工单状态不为10,去掉手机端已抽查等待WEB端处理的工单
		listargs.add(userId);
		
	    whereSql = whereSql+" and m.state<>10";
		
		    

		if(workOrderState!=null&&!"".equals(workOrderState)&&!"allState".equals(workOrderState)){//状态			
	
		   whereSql = whereSql+" and m.task_def_key =?";
		   listargs.add(workOrderState);
		}
			
	    whereSql = whereSql +" and m.themeInterface =? ";			
	    listargs.add(workOrderType);
		
		if(theme!=null&&!"".equals(theme)){//主题
			whereSql = whereSql+" and instr(m.theme,?) > 0";
		    listargs.add(theme);

		}
		
		if(sendTime!=null&&!"".equals(sendTime)){//时间
			whereSql = whereSql+" and m.send_time>=to_date(?,'yyyy-mm-dd hh24:mi:ss')";
		    listargs.add(sendTime);

		}
		
		if(city!=null&&!"".equals(city)){//地市
			whereSql = whereSql+" and m.city =?";
		    listargs.add(city);

		}
		if(country!=null&&!"".equals(country)){//区县
			whereSql = whereSql+" and m.country =?";
		    listargs.add(country);

		}	
	map.put("whereSql", whereSql);
	map.put("listargs", listargs);

	return map;
	}
	/**
	 * 抢修返回数据
	 * @param androidQuery
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	
	private List<AndroidWorkOrderTask> returnTransferOfficeAndroidTaskList(AndroidQuery androidQuery, int pageIndex, int pageSize){
		

		Map<String,Object>  mapWhere =returnGKAndroidTaskWhereCondition(androidQuery);
		String whereSql = mapWhere.get("whereSql")+"";
		ArrayList listargs =(ArrayList) mapWhere.get("listargs");
		
		

		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		String selectSql ="select m.send_time, m.task_id as id_, m.process_instance_id as proc_inst_id_, " +
				"m.task_def_key as task_def_key_,trunc(sysdate) tdate,m.country " +
			"                  from pnr_act_transfer_office_main m where m.task_def_key in ('transferHandle','transferTask','epibolyTask')  "+whereSql+
			" union all" +
			" select m.send_time,m.task_id as id_, m.process_instance_id as proc_inst_id_, " +
			" m.task_def_key as task_def_key_ ,trunc(rt.report_date),rt.county  from pnr_act_transfer_office_main m" +
			" left join pnr_second_inspection_report rt on m.process_instance_id=rt.process_instance_id" +
			" where  m.task_def_key='twoSpotChecks' and rt.APPROVE_FLAG='0' and " +
			" sysdate -rt.rise_time <= 3"
			+whereSql;
			
		
			
		sql += selectSql 
		+ " order by send_time) temp1 where rownum <=?) temp2 where temp2.num >? ";
		listargs.addAll(listargs);
		listargs.add((pageIndex+1)*pageSize);
		listargs.add(pageIndex*pageSize);
		
		System.out.println("----PnrTransferOfficeDaoJDBC--getAndroidTaskList--抢修手机工单查询sql="+sql);
		Object[] args = listargs.toArray();
		
		List<AndroidWorkOrderTask> workOrderTask = new ArrayList<AndroidWorkOrderTask>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,args);
		if(list!=null && list.size()>0){
			for(Map map:list){
				AndroidWorkOrderTask task = new AndroidWorkOrderTask();
				task.setTaskWworkOrderId(map.get("id_")!=null?map.get("id_").toString():"");
				task.setTaskDefinitionKey(map.get("task_def_key_")!=null?map.get("task_def_key_").toString():"");
				task.setProcessInstanceId(map.get("proc_inst_id_")!=null?map.get("proc_inst_id_").toString():"");
				task.setTcountry(map.get("country")!=null?map.get("country").toString():"");
				task.setTdate(map.get("tdate")!=null?dateFormat.format((Date)map.get("tdate")):"");
				workOrderTask.add(task);
			}
		}
		return workOrderTask;

	}
	public List<AndroidWorkOrderTask> getAndroidTaskListTogether(
			AndroidQuery androidQuery, int pageIndex, int pageSize) {
		
		String userId = androidQuery.getUserId();//用户名
		
		String processDefinitionKey=androidQuery.getProcessDefinitionKey();//流程key
		
		String theme = androidQuery.getTheme();//主题
		
		String sendTime= androidQuery.getSendTime();//派单时间
		
		String workOrderType=androidQuery.getWorkOrderType();//工单类型：大修改造工单、本地网预检预修工单、干线预检预修工单、公客故障工单、抢修工单、机房拆除工单
		
		String workOrderState = androidQuery.getWorkOrderState();//工单状态：处理、抽查等
		
		String city = androidQuery.getCity();//地市
		
		String country = androidQuery.getCountry();//区县
		String maleGuestType = androidQuery.getMaleGuestType();//
		String workOrderId = androidQuery.getWorkOrderId();//
		String siteCd = androidQuery.getSiteCd();//
		
		//机房拆除还是其他工单
		String transferOfficeOrRoom = "pnr_act_transfer_office_main";//包括工单：抢修工单、大修改造工单、本地网预检预修工单、干线预检预修工单、公客故障工单
		if("RoomDemolition".equals(workOrderType)){//由于机房拆除工单没有存在pnr_act_transfer_office_main表中，所以要和其他工单区别开
			transferOfficeOrRoom = "pnr_act_roomdemolition";
		}else if("maleGuest".equals(workOrderType)||"transferOffice".equals(workOrderType)){//包括工单：抢修工单、公客故障工单
			transferOfficeOrRoom = 
				"(select pm.process_instance_id," +
				"       pm.state," + 
				"       pm.task_def_key," + 
				"       pm.task_id," + 
				"       pm.ASSIGNEE," + 
				"       pm.themeInterface," + 
				"       pm.theme," + 
				"       pm.send_time," + 
				"       pm.do_flag," + 
				"       pm.male_guest_state," + 
				"       pm.site_cd SITECD," + 
				"       pm.parent_process_instance_id," + 
				"       pm.worker_scene_handle_flag," + 
				"       pm.city," + 
				"       pm.country " + 
				"  from pnr_act_transfer_office_main pm)";
		}
		
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		String selectSql =
			"select m.task_id as id_, m.process_instance_id as proc_inst_id_, m.task_def_key as task_def_key_ " +
			"                  from "+transferOfficeOrRoom+" m ";
		
//		String whereSql = " where m.assignee = '"+userId+"'";//工单状态不为10,去掉手机端已抽查等待WEB端处理的工单
		String whereSql = " where 1=1 ";
		
		if(!"".equals(userId)&&null!=userId){
			whereSql += " and m.assignee = '"+userId+"'";
		}
		
		if("maleGuest".equals(workOrderType)){//流程类型	公客工单状态为8时是已处理完毕的
			whereSql = whereSql+" and m.state<>8 and m.do_flag is null ";
		}else{
			whereSql = whereSql+" and m.state<>10";
		}
		
		if("transferInterface".equals(workOrderType)){//流程类型 本地网有三个版本比较特殊
//			whereSql = whereSql+" and f.key_ in ("+processDefinitionKey+")";
			whereSql = whereSql+" and nvl(m.worker_scene_handle_flag,'-1')!= '1'";
//			whereSql = whereSql+" and nvl(m.worker_scene_order_audit_flag,'-1')!= '1'";
			
		}else if("oltBbu".equals(workOrderType)){
//			whereSql = whereSql+" and f.key_ = '"+processDefinitionKey+"'";
			whereSql = whereSql+" and nvl(m.worker_scene_handle_flag,'-1')!= '1'";
			whereSql = whereSql+" and nvl(m.worker_scene_order_audit_flag,'-1')!= '1'";
		}else{
//			whereSql = whereSql+" and f.key_ = '"+processDefinitionKey+"'";
			//whereSql = whereSql+" and nvl(m.worker_scene_handle_flag,'-1')!= '1'";
		}
		
		
		if(workOrderState!=null&&!"".equals(workOrderState)&&!"allState".equals(workOrderState)){//状态
			whereSql = whereSql+" and m.task_def_key ='"+workOrderState+"'";
		}else{
			if("transferOffice".equals(workOrderType)){//抢修工单
				whereSql = whereSql +" and m.task_def_key in ('transferHandle','transferTask','epibolyTask','epibolyAudit','transferAudit','audit') ";
				whereSql = whereSql +" and m.themeInterface ='transferOffice' ";
				
			}else if("transferInterface".equals(workOrderType)){//本地网预检预修工单
				whereSql = whereSql +" and m.task_def_key in ('worker','orderAudit') ";
				whereSql = whereSql +" and m.themeInterface ='interface' ";
				
			}else if("maleGuest".equals(workOrderType)){//公客故障工单
				whereSql = whereSql +" and m.task_def_key in ('auditor','twoSpotChecks') ";
				whereSql = whereSql +" and m.themeInterface ='maleGuest' ";
				
			}else if("arteryPrecheck".equals(workOrderType)){//干线预检预修工单
				whereSql = whereSql +" and m.task_def_key in ('worker','orderAudit') ";
				whereSql = whereSql +" and m.themeInterface ='arteryPrecheck' ";
				
			}else if("overHaul".equals(workOrderType)){//大修改造工单
				whereSql = whereSql +" and m.task_def_key in ('worker','orderAudit') ";
				whereSql = whereSql +" and m.themeInterface ='overhaul' ";
				
			}else if("RoomDemolition".equals(workOrderType)){//机房拆除工单
				whereSql = whereSql +" and m.task_def_key in ('worker','orderAudit') ";
			}else if("oltBbu".equals(workOrderType)){//olt-bbu
				whereSql = whereSql +" and m.task_def_key in ('worker','orderAudit') ";
				whereSql = whereSql +" and m.themeInterface ='oltBbuProcess' ";
				
			}
		}
		
		if(theme!=null&&!"".equals(theme)){//主题
			whereSql = whereSql+" and instr(m.theme,'"+theme+"') > 0";
		}
		
		if(sendTime!=null&&!"".equals(sendTime)){//时间
			whereSql = whereSql+" and m.send_time>=to_date('"+sendTime+"','yyyy-mm-dd hh24:mi:ss')";
		}
		
		if(city!=null&&!"".equals(city)){//地市
			whereSql = whereSql+" and m.city ='"+city+"'";
		}
		if(country!=null&&!"".equals(country)){//区县
			whereSql = whereSql+" and m.country ='"+country+"'";
		}	
		
		if(maleGuestType!=null&&!"".equals(maleGuestType)){//地市
			if("merge".equals(maleGuestType)){
		
		    whereSql = whereSql+" and m.parent_process_instance_id ='"+workOrderId+"' and m.male_guest_state='2'";
				
			}else if("alone".equals(maleGuestType)){
			    whereSql = whereSql+" and nvl(m.male_guest_state,'0') in('0','3')";

			}else if("addmerge".equals(maleGuestType)){
			    whereSql = whereSql+" and m.male_guest_state='1'";

			}
	
		}
	
	/*	if(siteCd !=null&&!"".equals(siteCd )){//区县
			whereSql = whereSql+" and m.siteCd  ='"+siteCd+"'";
		}*/
		
		sql += selectSql + whereSql
		+ " order by m.send_time) temp1 where rownum <="
		+ (pageIndex+1)*pageSize + ") temp2 where temp2.num > "
		+ pageIndex*pageSize;
		
		System.out.println("----PnrTransferOfficeDaoJDBC--getAndroidTaskListTogether--手机工单查询sql="+sql);
		
		List<AndroidWorkOrderTask> workOrderTask = new ArrayList<AndroidWorkOrderTask>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		if(list!=null && list.size()>0){
			for(Map map:list){
				AndroidWorkOrderTask task = new AndroidWorkOrderTask();
				task.setTaskWworkOrderId(map.get("id_")!=null?map.get("id_").toString():"");
				task.setTaskDefinitionKey(map.get("task_def_key_")!=null?map.get("task_def_key_").toString():"");
				task.setProcessInstanceId(map.get("proc_inst_id_")!=null?map.get("proc_inst_id_").toString():"");
				workOrderTask.add(task);
			}
		}
		return workOrderTask;
	}
	
	/**
	 * 工单管理-"公客-传输局工单"-手动归档工单 集合
	 * 
	 */
	public List<TaskModel> manualArchiveList(String userId,String startDate,String endDate,
			MaleGuestSelectAttribute selectAttribute, int firstResult, int endResult,
			int pageSize){
		ArrayList list1 = new ArrayList();
		String sql = " select temp2.* from (select temp1.*, rownum num from (";
		String selectSql = " select t.id_ as TaskId,t.username,u.deptname,m.initiator as Initiator,m.one_initiator as OneInitiator,m.process_instance_id as ProcessInstanceId," +
				" m.send_time as SendTime,m.theme as Theme,m.task_assignee,m.state as State,u.deptid as DeptId,m.install_address as Install_address," +
				" m.barrier_number as Barrier_number,m.create_time,m.city,m.connect_person,m.process_limit, t.assignee_, t.task_def_key_, m.repeat_state from " +
				" (select tu.username, RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_  left join taw_system_user tu  on tu.userid = RES.ASSIGNEE_" +
				" WHERE  D.KEY_ = 'myMaleGuestProcess' and RES.Suspension_State_ = 1) t,(select k.initiator,k.one_initiator,k.process_instance_id,k.send_time," +
						" k.theme,k.install_address,k.barrier_number, decode(k.task_assignee,null,k.initiator,k.task_assignee) as task_assignee,k.state,k.Themeinterface," +
						" k.city, k.create_time, k.connect_person,k.process_limit,k.repeat_state from pnr_act_transfer_office_main k) m,( select tsu.deptid,tsd.deptname,tsu.userid from taw_system_user tsu " +
						" left join taw_system_dept tsd  on tsu.deptid = tsd.deptid )u " +
						" where t.proc_inst_id_ = m.process_instance_id and m.task_assignee = u.userid(+) and m.Themeinterface = 'maleGuest' and m.state =8 and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >= ? and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?";
//		list1.add(userId);
		
		//拼接条件查询
		String whereSql = "";
		if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
			//whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >= ?";
			list1.add(selectAttribute.getBeginTime());
		}else{
			list1.add(startDate);
		}
		if (selectAttribute.getEndTime() != null && !selectAttribute.getEndTime().equals("")) {
			//whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?";
			list1.add(selectAttribute.getEndTime());
		}else{
			list1.add(endDate);
		}
		if (selectAttribute.getMaleGuestNumber() != null && !selectAttribute.getMaleGuestNumber().equals("")) {
			whereSql += " and m.process_instance_id = ?";
			list1.add(selectAttribute.getMaleGuestNumber().trim());
		}
		if (selectAttribute.getTheme() != null && !selectAttribute.getTheme().equals("")) {
			whereSql += " and instr(m.theme,?)>0 ";
			list1.add(selectAttribute.getTheme());
		}
		sql += selectSql + whereSql
				+ " order by t.create_time_ desc) temp1 where rownum <= ?) temp2 where temp2.num > ?";
		list1.add(endResult * pageSize);
		list1.add(firstResult * pageSize);
		System.out.println("公客手工归档工单查询sql="+sql);
		Object[] values = list1.toArray();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,values);
		for (Map map : list) {
			TaskModel model = new TaskModel();
			if(map.get("TaskId")!=null && !"".equals(map.get("TaskId"))){
				
				model.setTaskId(map.get("TaskId").toString());
			}else{
				model.setTaskId("");
			}
			if(map.get("Initiator")!=null && !"".equals(map.get("Initiator"))){
				model.setInitiator(map.get("Initiator").toString());
			}else{
				model.setInitiator("");
			}
			
			//临时做为空判断，正常OneInitiator的值不可以为空
			if (map.get("OneInitiator") != null){
				model.setOneInitiator(map.get("OneInitiator").toString());
			}else{
				model.setOneInitiator("");
			}		
			model.setProcessInstanceId(map.get("ProcessInstanceId").toString());
			if (map.get("SendTime") != null) {
				if (!map.get("SendTime").toString().equals("")) {
					try {
						model.setSendTime(format.parse(map.get("SendTime")
								.toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

			}
			if(map.get("Theme")!=null && !"".equals(map.get("Theme"))){
				model.setTheme(map.get("Theme").toString());
			}else{
				model.setTheme("");
			}
			if(map.get("DeptId")!=null && !"".equals(map.get("DeptId"))){
				model.setDeptId(map.get("DeptId").toString());
			}else{
				model.setDeptId("");
			}
			if(map.get("State")!=null && !"".equals(map.get("State"))){
				model.setState(Integer.parseInt(map.get("State").toString()));
			}else{
				model.setState(0);
			}
			if(map.get("Install_address")!=null && !"".equals(map.get("Install_address"))){
				model.setInstallAddress(map.get("Install_address").toString());
			}else{
				model.setInstallAddress("");
			}
			if(map.get("Barrier_number")!=null && !"".equals(map.get("Barrier_number"))){
				model.setBarrierNumber(map.get("Barrier_number").toString());
			}else{
				model.setBarrierNumber("");
			}
			if(map.get("create_time")!=null && !"".equals(map.get("create_time"))){
				if (map.get("create_time") != null) {
					if (!map.get("create_time").toString().equals("")) {
						try {
							model.setCreateTime(format.parse(map.get("create_time")
									.toString()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}

				}
			}
			if(map.get("city")!=null && !"".equals(map.get("city"))){
				model.setCity(map.get("city").toString());
			}
			if(map.get("connect_person")!=null &&!"".equals(map.get("connect_person"))){
				model.setConnectPerson(map.get("connect_person").toString());
			}
			if(map.get("process_limit")!=null &&!"".equals(map.get("process_limit"))){
				model.setProcessLimit(map.get("process_limit").toString());
			}
			//工单历时 = 当前时间-故障发生时间：当工单历时大于处理时限，改变颜色
			if(map.get("create_time")!=null && !"".equals(map.get("create_time"))&& map.get("SendTime") != null){
				//两时间做差
				Date startTime = new Date();
				Date endTime = model.getCreateTime();
				long sortTime = startTime.getTime()-endTime.getTime();
				long hours = sortTime / (1000 * 60 * 60 );
				long minutes = sortTime/(1000*60)-hours*60;
				long second = sortTime/1000 - hours*60*60 - minutes*60;
				String newTime = hours+"小时"+minutes+"分钟"+second+"秒";
				model.setWorkTask(newTime);
				if(model.getProcessLimit()!=null&&!"".equals(model.getProcessLimit())){
					long processLimit = Long.parseLong(model.getProcessLimit());
					if(processLimit>hours){//没有超出时限
						model.setChangeColor(true);
					}else{//超出时限
						model.setChangeColor(false);
					}
				}
			}
			if(map.get("assignee_")!=null && !"".equals(map.get("assignee_"))){
				model.setExecutor(map.get("assignee_").toString());
			}
			//状态存储--start
			String state = "";
			if(map.get("task_def_key_")!=null && !"".equals(map.get("task_def_key_"))&&"newMaleGuest".equals(map.get("task_def_key_"))){
				state = "派发工单";
			}else if(map.get("task_def_key_")!=null && !"".equals(map.get("task_def_key_"))&&"auditor".equals(map.get("task_def_key_"))){
				state="故障处理";
			}
			if(map.get("repeat_state")!=null && !"".equals(map.get("repeat_state"))&&"1".equals(map.get("repeat_state").toString().trim())){
				state = "(重修)"+state;
			}
			model.setStatusName(state);
			//--end
			
			r.add(model);

		}

		return r;

	}
	
	/**
	 * 工单管理-"公客-传输局工单"-待回复工单 集合条数
	 */
	public int manualArchiveCount(String userId,String startDate,String endDate,
			MaleGuestSelectAttribute selectAttribute){
		ArrayList list1 = new ArrayList();
		String sql = "";
		String selectSql = "select count(*) as total from (select RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_ WHERE  D.KEY_ = 'myMaleGuestProcess' and RES.Suspension_State_ = 1) t,(select k.initiator,k.one_initiator,k.process_instance_id,k.send_time,k.theme,k.barrier_number,decode(k.task_assignee,null,k.initiator,k.task_assignee) as task_assignee,k.state,k.Themeinterface from pnr_act_transfer_office_main k) m,taw_system_user u where t.proc_inst_id_ = m.process_instance_id and m.task_assignee = u.userid(+) and m.Themeinterface = 'maleGuest' and m.state =8 and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >= ? and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?";
//		 list1.add(userId);
		
		 
		
		String whereSql = "";
		if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
			//whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >= ?";
			list1.add(selectAttribute.getBeginTime());
		}else{
			list1.add(startDate);
		}
		if (selectAttribute.getEndTime() != null && !selectAttribute.getEndTime().equals("")) {
			//whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?";
			list1.add(selectAttribute.getEndTime());
		}else{
			list1.add(endDate);
		}
		if (selectAttribute.getMaleGuestNumber() != null && !selectAttribute.getMaleGuestNumber().equals("")) {
			whereSql += " and m.process_instance_id = ?";
			list1.add(selectAttribute.getMaleGuestNumber().trim());
		}
		if (selectAttribute.getTheme() != null && !selectAttribute.getTheme().equals("")) {
			whereSql += " and instr(m.theme,?)>0 ";
			list1.add(selectAttribute.getTheme());
		}
//		if (selectAttribute.getStatus() != null && !selectAttribute.getStatus().equals("")) {
//			whereSql += " and t.task_def_key_ = ?";
//			list1.add(selectAttribute.getStatus());
//		}
//		if(selectAttribute.getWsNum()!=null && !"".equals(selectAttribute.getWsNum())){
//			whereSql+=" and m.barrier_number = ?";
//			list1.add(selectAttribute.getWsNum());
//		}
		sql = selectSql + whereSql;

		System.out.println("公客手工归档工单统计数sql="+sql);
		Object[] values = list1.toArray();
		int total = 0;
		total = this.getJdbcTemplate().queryForInt(sql,values);
		return total;
	}
	/**
	 * 本地网预检预修-按工单号查询工单详情
	 * 
	 */
	public List<PnrTransferOffice> getWorkOrderDetails(String processInstanceId){
		String sql =   " select  "+
					   " main.theme,"+
					   " main.themeinterface,"+
					   " main.create_work as createWork,"+
					   " main.initiator_mobile_phone as initiatorMobilePhone,"+
					   " main.workorder_type_name as workOrderTypeName,"+
					   " main.sub_type_name as subTypeName,"+
					   " main.submit_application_time as submitApplicationTime,"+
					   " main.dept_head as deptHead,"+
					   " main.dept_head_mobile_phone as deptHeadMobilePhone,"+
					   " main.line_Name as lineName,"+
					   " main.PROCESS_INSTANCE_ID as processInstanceId,"+
					   " main.project_start_point as projectStartPoint,"+
					   " main.project_end_point as projectEndPoint,"+
					   " main.SPECIFIC_LOCATION as specificLocation,"+
					   " main.PROJECT_AMOUNT,"+
					   " main.compensate,"+
					   " main.calculate_income_ratio,"+
					   " main.construction_reasons,"+
					   " main.laying_cable,"+
					   " main.righting_demolition_pole,"+
					   " main.repair_pipeline,"+
					   " main.wire_laying,"+
					   " main.excavation_trench,"+
					   " main.fiber_optic_cable_connector,"+
					   " main.MAIN_QUANTITY_OTHER,"+
					   "(select dept.deptname from taw_system_dept dept where dept.deptid=main.initiator_dept) as initiatorDept,"+
					   " (select dict.dictname from taw_system_dicttype dict where dict.dictid = main.work_Type) as workType,"+
					   " (select dict.dictname from taw_system_dicttype dict where dict.dictid=main.bear_service) as bearService,"+
					   " (select dict.dictname from taw_system_dicttype dict where dict.dictid=main.work_order_degree) as workOrderDegree,"+
					   " (select dict.dictname from taw_system_dicttype dict where dict.dictid=main.key_word) as keyWord,"+
					   " (select dict.dictname from taw_system_dicttype dict where dict.dictid=main.precheck_flag) as precheckFlag"+
					   " from pnr_act_transfer_office_main main "+
					   " where "+ 
					   " main.process_instance_id='"+processInstanceId+"'";

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<PnrTransferOffice> r = new ArrayList<PnrTransferOffice>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		for (Map map : list) {
			PnrTransferOffice model = new PnrTransferOffice();
			
			
			
			if (map.get("createWork") != null && !"".equals(map.get("createWork"))) {

				model.setCreateWork(map.get("createWork").toString());
			}

			if (map.get("initiatorMobilePhone") != null
					&& !"".equals(map.get("initiatorMobilePhone"))) {

				model.setInitiatorMobilePhone(map.get("initiatorMobilePhone").toString());
			}
			if (map.get("workOrderTypeName") != null
					&& !"".equals(map.get("workOrderTypeName"))) {

				model.setWorkOrderTypeName(map.get("workOrderTypeName").toString());
			}

			if (map.get("subTypeName") != null
					&& !"".equals(map.get("subTypeName"))) {

				model.setSubTypeName(map.get("subTypeName").toString());
			}
			if (map.get("deptHead") != null
					&& !"".equals(map.get("deptHead"))) {

				model.setDeptHead(map.get("deptHead").toString());
			}
			if (map.get("ProcessInstanceId") != null
					&& !"".equals(map.get("ProcessInstanceId"))) {

				model.setProcessInstanceId(map.get("ProcessInstanceId")
						.toString());
			}
			if (map.get("submitApplicationTime") != null) {
				if (!"".equals(map.get("submitApplicationTime"))) {
					try {
						model.setSubmitApplicationTime(format.parse(map.get(
								"submitApplicationTime").toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

			}
				
			if (map.get("Theme") != null && !"".equals(map.get("Theme"))) {

				model.setTheme(map.get("Theme").toString());
			}
			if (map.get("themeinterface") != null && !"".equals(map.get("themeinterface"))) {

				model.setThemeInterface(map.get("themeinterface").toString());
			}
			if (map.get("deptHeadMobilePhone") != null && !"".equals(map.get("deptHeadMobilePhone"))) {

				model.setDeptHeadMobilePhone(map.get("deptHeadMobilePhone").toString());
			}
			if (map.get("initiatorDept") != null && !"".equals(map.get("initiatorDept"))) {

				model.setInitiatorDept(map.get("initiatorDept").toString());
			}
			if (map.get("bearService") != null && !"".equals(map.get("bearService"))) {

				model.setBearService(map.get("bearService").toString());
			}
			if (map.get("workOrderDegree") != null && !"".equals(map.get("workOrderDegree"))) {
				model.setWorkOrderDegree(map.get("workOrderDegree").toString());
			}
			if (map.get("workType") != null
					&& !"".equals(map.get("workType"))) {

				model.setWorkType(map.get("workType").toString());
			}

			if (map.get("specificLocation") != null
					&& !"".equals(map.get("specificLocation"))) {
				model.setSpecificLocation(map.get("specificLocation").toString());
			}

			if (map.get("keyWord") != null && !"".equals(map.get("keyWord"))) {
				model.setKeyWord(map.get("keyWord").toString());
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
			if (map.get("precheckFlag") != null
					&& !"".equals(map.get("precheckFlag"))) {
				model.setPrecheckFlag(map.get("precheckFlag").toString());
			}
			if (map.get("lineName") != null
					&& !"".equals(map.get("lineName"))) {
				model.setLineName(map.get("lineName")
						.toString());
			}
			
			
			if (map.get("projectStartPoint") != null
					&& !"".equals(map.get("projectStartPoint"))) {
				model.setProjectStartPoint(map.get("projectStartPoint")
						.toString());
			}
			if (map.get("projectEndPoint") != null
					&& !"".equals(map.get("projectEndPoint"))) {
				model.setProjectEndPoint(map.get("projectEndPoint")
						.toString());
			}
			if (map.get("repair_pipeline") != null
					&& !"".equals(map.get("repair_pipeline"))) {
				model.setRepairPipeline(Double.parseDouble(map.get("repair_pipeline")
						.toString()));
			}
			if (map.get("wire_laying") != null
					&& !"".equals(map.get("wire_laying"))) {
				model.setWireLaying(Double.parseDouble(map.get("wire_laying")
						.toString()));
			}
			if (map.get("excavation_trench") != null
					&& !"".equals(map.get("excavation_trench"))) {
				model.setExcavationTrench(Double.parseDouble(map.get("excavation_trench")
						.toString()));
			}
			if (map.get("fiber_optic_cable_connector") != null
					&& !"".equals(map.get("fiber_optic_cable_connector"))) {
				model.setFiberOpticCableConnector(Double.parseDouble(map.get("fiber_optic_cable_connector")
						.toString()));
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
			if (map.get("righting_demolition_pole") != null
					&& !"".equals(map.get("righting_demolition_pole"))) {
				model.setRightingDemolitionPole(Double.parseDouble(map.get("righting_demolition_pole")
						.toString()));
			}
			if (map.get("repair_pipeline") != null
					&& !"".equals(map.get("repair_pipeline"))) {
				model.setRepairPipeline(Double.parseDouble(map.get("repair_pipeline")
						.toString()));
			}
			if (map.get("calculate_income_ratio") != null
					&& !"".equals(map.get("calculate_income_ratio"))) {
				model.setCalculateIncomeRatio(Double.parseDouble(map.get("calculate_income_ratio")
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
			
			//--end
			
			r.add(model);

		}

		return r;

	}
	
	//归集工单列表统计数
	@SuppressWarnings("unchecked")
	public int querySingleImputationCount(String userId,MaleGuestSelectAttribute selectAttribute){
		ArrayList list = new ArrayList();
		String sql = "";
		String selectSql =	"select count(m.id) as total" +
							"  from pnr_act_transfer_office_main m, taw_system_user u, taw_system_dept d" + 
							" where m.assignee = u.userid(+)" + 
							"   and u.deptid = d.deptid(+)" + 
							"   and m.Themeinterface = 'maleGuest'" + 
							"   and m.state != 8" + 
							"   and m.male_guest_state='1'" + 
							"   and m.assignee = ?";

//		String selectSql = "select count(*) as total from (select tu.username, RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_ left join taw_system_user tu on tu.userid = RES.ASSIGNEE_ WHERE RES.ASSIGNEE_ = ? and D.KEY_ = 'myMaleGuestProcess' and RES.Suspension_State_ = 1) t,(select k.initiator,k.one_initiator,k.process_instance_id,k.send_time,k.theme,k.barrier_number,k.install_address,decode(k.task_assignee,null,k.initiator,k.task_assignee) as task_assignee,k.state,k.Themeinterface from pnr_act_transfer_office_main k) m,taw_system_user u where t.proc_inst_id_ = m.process_instance_id and m.task_assignee = u.userid(+) and m.Themeinterface = 'maleGuest' and m.state!=8 ";
		list.add(userId);
		String whereSql = "";
		if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >=?";
			list.add(selectAttribute.getBeginTime());
		}
		if (selectAttribute.getEndTime() != null && !selectAttribute.getEndTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?";
			list.add(selectAttribute.getEndTime());
		}
		if (selectAttribute.getMaleGuestNumber() != null && !selectAttribute.getMaleGuestNumber().equals("")) {
			whereSql += " and m.process_instance_id =?";
			list.add(selectAttribute.getMaleGuestNumber().trim());
		}
		if (selectAttribute.getTheme() != null && !selectAttribute.getTheme().equals("")) {
			whereSql += " and instr(m.theme,?)>0 ";
			list.add(selectAttribute.getTheme().trim());
		}
		if (selectAttribute.getStatus() != null && !selectAttribute.getStatus().equals("")) {
			whereSql += " and m.task_def_key = ?";
			list.add(selectAttribute.getStatus());
		}
//		if (selectAttribute.getStatus() != null && !selectAttribute.getStatus().equals("")) {
//			whereSql += " and t.task_def_key_ = ?";
//			list.add(selectAttribute.getStatus());
//		}
		if(selectAttribute.getWsNum()!=null && !"".equals(selectAttribute.getWsNum())){
			whereSql+="  and m.barrier_number =?";
			list.add(selectAttribute.getWsNum());
		}
		if(selectAttribute.getInstallAddress()!=null && !"".equals(selectAttribute.getInstallAddress().trim())){
			whereSql += " and instr(m.install_address,?)>0 ";
			list.add(selectAttribute.getInstallAddress());
		}
		if(selectAttribute.getDept()!=null && !"".equals(selectAttribute.getDept().trim())){
			whereSql += " and instr(d.deptname,?)>0 ";
			list.add(selectAttribute.getDept());
		}
//		if(selectAttribute.getDept()!=null && !"".equals(selectAttribute.getDept().trim())){
//			whereSql += " and instr(u.deptname,?)>0 ";
//			list.add(selectAttribute.getDept());
//		}
		if(selectAttribute.getPerson()!=null && !"".equals(selectAttribute.getPerson().trim())){
			whereSql+="  and u.username =?";
			list.add(selectAttribute.getPerson());
		}
//		if(selectAttribute.getPerson()!=null && !"".equals(selectAttribute.getPerson().trim())){
//			whereSql+="  and t.username =?";
//			list.add(selectAttribute.getPerson());
//		}
		sql = selectSql + whereSql;

		System.out.println("------------wj公客待回复统计数sql="+sql);
		
		Object[] args = list.toArray();
		
		int total = this.getJdbcTemplate().queryForInt(sql, args);
		return total;
	}
	
	//归集工单列表统计数
	@SuppressWarnings("unchecked")
	public List<TaskModel> querySingleImputationList(String userId,MaleGuestSelectAttribute selectAttribute, int firstResult, int endResult,int pageSize){
		ArrayList list1 = new ArrayList();
		String sql = " select temp2.* from (select temp1.*, rownum num from (";
		 
		String selectSql =	"select m.task_id             as TaskId," +
							"       u.username," + 
							"       d.deptname," + 
							"       m.initiator           as Initiator," + 
							"       m.one_initiator       as OneInitiator," + 
							"       m.process_instance_id as ProcessInstanceId," + 
							"       m.send_time           as SendTime," + 
							"       m.theme               as Theme," + 
							"       m.task_assignee," + 
							"       m.state               as State," + 
							"       u.deptid              as DeptId," + 
							"       m.install_address     as Install_address," + 
							"       m.barrier_number      as Barrier_number," + 
							"       m.create_time," + 
							"       m.city," + 
							"       m.connect_person," + 
							"       m.process_limit," + 
							"       m.assignee," + 
							"       m.task_def_key," + 
							"       m.site_cd," + 
							"       m.repeat_state," + 
							"       m.rollback_flag" + 
							"  from pnr_act_transfer_office_main m, taw_system_user u, taw_system_dept d" + 
							" where m.assignee = u.userid(+)" + 
							"   and u.deptid = d.deptid(+)" + 
							"   and m.Themeinterface = 'maleGuest'" + 
							"   and m.state != 8" + 
							"   and m.male_guest_state='1'" + 
							"   and m.assignee = ?";

//		String selectSql = " select t.id_ as TaskId,t.username,u.deptname,m.initiator as Initiator,m.one_initiator as OneInitiator,m.process_instance_id as ProcessInstanceId," +
//		" m.send_time as SendTime,m.theme as Theme,m.task_assignee,m.state as State,u.deptid as DeptId,m.install_address as Install_address," +
//		" m.barrier_number as Barrier_number,m.create_time,m.city,m.connect_person,m.process_limit, t.assignee_, t.task_def_key_, m.repeat_state from " +
//		" (select tu.username, RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_  left join taw_system_user tu  on tu.userid = RES.ASSIGNEE_" +
//		" WHERE RES.ASSIGNEE_ = ? and D.KEY_ = 'myMaleGuestProcess' and RES.Suspension_State_ = 1) t,(select k.initiator,k.one_initiator,k.process_instance_id,k.send_time," +
//		" k.theme,k.install_address,k.barrier_number, decode(k.task_assignee,null,k.initiator,k.task_assignee) as task_assignee,k.state,k.Themeinterface," +
//		" k.city, k.create_time, k.connect_person,k.process_limit,k.repeat_state from pnr_act_transfer_office_main k) m,( select tsu.deptid,tsd.deptname,tsu.userid from taw_system_user tsu " +
//		" left join taw_system_dept tsd  on tsu.deptid = tsd.deptid )u " +
//		" where t.proc_inst_id_ = m.process_instance_id and m.task_assignee = u.userid(+) and m.Themeinterface = 'maleGuest' and m.state!=8";
		list1.add(userId);
		System.out.println("-----userId="+userId);
		//拼接条件查询
		String whereSql = "";
		if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >=?";
			list1.add(selectAttribute.getBeginTime());
		}
		if (selectAttribute.getEndTime() != null && !selectAttribute.getEndTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?";
			list1.add(selectAttribute.getEndTime());
		}
		if (selectAttribute.getMaleGuestNumber() != null && !selectAttribute.getMaleGuestNumber().equals("")) {
			whereSql += " and m.process_instance_id =?";
			list1.add(selectAttribute.getMaleGuestNumber().trim());
		}
		if (selectAttribute.getTheme() != null && !selectAttribute.getTheme().equals("")) {
			whereSql += " and instr(m.theme,?)>0 ";
			list1.add(selectAttribute.getTheme().trim());
		}
		if (selectAttribute.getStatus() != null && !selectAttribute.getStatus().equals("")) {
			whereSql += " and m.task_def_key = ?";
			list1.add(selectAttribute.getStatus());
		}
//		if (selectAttribute.getStatus() != null && !selectAttribute.getStatus().equals("")) {
//			whereSql += " and t.task_def_key_ = ?";
//			list1.add(selectAttribute.getStatus());
//		}
		if(selectAttribute.getWsNum()!=null && !"".equals(selectAttribute.getWsNum())){
			whereSql+="  and m.barrier_number =?";
			list1.add(selectAttribute.getWsNum());
		}
		if(selectAttribute.getInstallAddress()!=null && !"".equals(selectAttribute.getInstallAddress().trim())){
			whereSql += " and instr(m.install_address,?)>0 ";
			list1.add(selectAttribute.getInstallAddress());
		}
		if(selectAttribute.getDept()!=null && !"".equals(selectAttribute.getDept().trim())){
			whereSql += " and instr(d.deptname,?)>0 ";
			list1.add(selectAttribute.getDept());
		}
//		if(selectAttribute.getDept()!=null && !"".equals(selectAttribute.getDept().trim())){
//			whereSql += " and instr(u.deptname,?)>0 ";
//			list1.add(selectAttribute.getDept());
//		}
		if(selectAttribute.getPerson()!=null && !"".equals(selectAttribute.getPerson().trim())){
			whereSql+="  and u.username =?";
			list1.add(selectAttribute.getPerson());
		}
//		if(selectAttribute.getPerson()!=null && !"".equals(selectAttribute.getPerson().trim())){
//			whereSql+="  and t.username =?";
//			list1.add(selectAttribute.getPerson());
//		}
		sql += selectSql + whereSql
				+ " order by m.send_time desc) temp1 where rownum <=?) temp2 where temp2.num > ?";
//		sql += selectSql + whereSql
//		+ " order by t.create_time_ desc) temp1 where rownum <=?) temp2 where temp2.num > ?";
		
		list1.add(endResult * pageSize);
		
		list1.add(firstResult * pageSize);
		Object[] values = list1.toArray();
		
		System.out.println("---------------------wj公客待回复统明细sql="+sql);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql, values);
		for (Map map : list) {
			TaskModel model = new TaskModel();
			if(map.get("TaskId")!=null && !"".equals(map.get("TaskId"))){
				
				model.setTaskId(map.get("TaskId").toString());
			}else{
				model.setTaskId("");
			}
			
			if(map.get("site_cd")!=null && !"".equals(map.get("site_cd"))){
				
				model.setSiteCd(map.get("site_cd").toString());
			}else{
				model.setSiteCd("");
			}
			
			if(map.get("Initiator")!=null && !"".equals(map.get("Initiator"))){
				model.setInitiator(map.get("Initiator").toString());
			}else{
				model.setInitiator("");
			}
			
			//临时做为空判断，正常OneInitiator的值不可以为空
			if (map.get("OneInitiator") != null){
				model.setOneInitiator(map.get("OneInitiator").toString());
			}else{
				model.setOneInitiator("");
			}		
			model.setProcessInstanceId(map.get("ProcessInstanceId").toString());
			if (map.get("SendTime") != null) {
				if (!map.get("SendTime").toString().equals("")) {
					try {
						model.setSendTime(format.parse(map.get("SendTime")
								.toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

			}
			if(map.get("Theme")!=null && !"".equals(map.get("Theme"))){
				model.setTheme(map.get("Theme").toString());
			}else{
				model.setTheme("");
			}
			if(map.get("DeptId")!=null && !"".equals(map.get("DeptId"))){
				model.setDeptId(map.get("DeptId").toString());
			}else{
				model.setDeptId("");
			}
			if(map.get("State")!=null && !"".equals(map.get("State"))){
				model.setState(Integer.parseInt(map.get("State").toString()));
			}else{
				model.setState(0);
			}
			if(map.get("Install_address")!=null && !"".equals(map.get("Install_address"))){
				model.setInstallAddress(map.get("Install_address").toString());
			}else{
				model.setInstallAddress("");
			}
			if(map.get("Barrier_number")!=null && !"".equals(map.get("Barrier_number"))){
				model.setBarrierNumber(map.get("Barrier_number").toString());
			}else{
				model.setBarrierNumber("");
			}
			if(map.get("create_time")!=null && !"".equals(map.get("create_time"))){
				if (map.get("create_time") != null) {
					if (!map.get("create_time").toString().equals("")) {
						try {
							model.setCreateTime(format.parse(map.get("create_time")
									.toString()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}

				}
			}
			if(map.get("city")!=null && !"".equals(map.get("city"))){
				model.setCity(map.get("city").toString());
			}
			if(map.get("connect_person")!=null &&!"".equals(map.get("connect_person"))){
				model.setConnectPerson(map.get("connect_person").toString());
			}
			if(map.get("process_limit")!=null &&!"".equals(map.get("process_limit"))){
				model.setProcessLimit(map.get("process_limit").toString());
			}
			//工单历时 = 当前时间-故障发生时间：当工单历时大于处理时限，改变颜色
			if(map.get("create_time")!=null && !"".equals(map.get("create_time"))&& map.get("SendTime") != null){
				//两时间做差
				Date startTime = new Date();
				Date endTime = model.getCreateTime();
				long sortTime = startTime.getTime()-endTime.getTime();
				long hours = sortTime / (1000 * 60 * 60 );
				long minutes = sortTime/(1000*60)-hours*60;
				long second = sortTime/1000 - hours*60*60 - minutes*60;
				String newTime = hours+"小时"+minutes+"分钟"+second+"秒";
				model.setWorkTask(newTime);
				if(model.getProcessLimit()!=null&&!"".equals(model.getProcessLimit())){
					long processLimit = Long.parseLong(model.getProcessLimit());
					if(processLimit>hours){//没有超出时限
						model.setChangeColor(true);
					}else{//超出时限
						model.setChangeColor(false);
					}
				}
			}
			if(map.get("assignee")!=null && !"".equals(map.get("assignee"))){
				model.setExecutor(map.get("assignee").toString());
			}
			//状态存储--start
			String state = "";
			if(map.get("task_def_key")!=null && !"".equals(map.get("task_def_key"))&&"newMaleGuest".equals(map.get("task_def_key"))){
				state = "派发工单";
			}else if(map.get("task_def_key")!=null && !"".equals(map.get("task_def_key"))&&"auditor".equals(map.get("task_def_key"))){
				state="故障处理";
			}
			if(map.get("repeat_state")!=null && !"".equals(map.get("repeat_state"))&&"1".equals(map.get("repeat_state").toString().trim())){
				state = "(重修)"+state;
			}
			model.setStatusName(state);
			//驳回标识
			if(map.get("rollback_flag")!=null && !"".equals(map.get("rollback_flag"))){
				model.setRollbackFlag(map.get("rollback_flag").toString());
			}
			//--end
			
			r.add(model);

		}

		return r;
	}
	
	//未归集工单列表统计数
	@SuppressWarnings("unchecked")
	public int queryNoSingleImputationCount(String userId,MaleGuestSelectAttribute selectAttribute){
		ArrayList list = new ArrayList();
		String sql = "";
		String selectSql =	"select count(m.id) as total" +
							"  from pnr_act_transfer_office_main m, taw_system_user u, taw_system_dept d" + 
							" where m.assignee = u.userid(+)" + 
							"   and u.deptid = d.deptid(+)" + 
							"   and m.Themeinterface = 'maleGuest'" + 
							"   and nvl(m.male_guest_state,'0') in('0','3') " + 
							"   and m.state != 8" + 
							"   and m.assignee = ?";

//		String selectSql = "select count(*) as total from (select tu.username, RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_ left join taw_system_user tu on tu.userid = RES.ASSIGNEE_ WHERE RES.ASSIGNEE_ = ? and D.KEY_ = 'myMaleGuestProcess' and RES.Suspension_State_ = 1) t,(select k.initiator,k.one_initiator,k.process_instance_id,k.send_time,k.theme,k.barrier_number,k.install_address,decode(k.task_assignee,null,k.initiator,k.task_assignee) as task_assignee,k.state,k.Themeinterface from pnr_act_transfer_office_main k) m,taw_system_user u where t.proc_inst_id_ = m.process_instance_id and m.task_assignee = u.userid(+) and m.Themeinterface = 'maleGuest' and m.state!=8 ";
		list.add(userId);
		String whereSql = "";
		if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >=?";
			list.add(selectAttribute.getBeginTime());
		}
		if (selectAttribute.getEndTime() != null && !selectAttribute.getEndTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?";
			list.add(selectAttribute.getEndTime());
		}
		if (selectAttribute.getMaleGuestNumber() != null && !selectAttribute.getMaleGuestNumber().equals("")) {
			whereSql += " and m.process_instance_id =?";
			list.add(selectAttribute.getMaleGuestNumber().trim());
		}
		if (selectAttribute.getTheme() != null && !selectAttribute.getTheme().equals("")) {
			whereSql += " and instr(m.theme,?)>0 ";
			list.add(selectAttribute.getTheme().trim());
		}
		if (selectAttribute.getStatus() != null && !selectAttribute.getStatus().equals("")) {
			whereSql += " and m.task_def_key = ?";
			list.add(selectAttribute.getStatus());
		}
//		if (selectAttribute.getStatus() != null && !selectAttribute.getStatus().equals("")) {
//			whereSql += " and t.task_def_key_ = ?";
//			list.add(selectAttribute.getStatus());
//		}
		if(selectAttribute.getWsNum()!=null && !"".equals(selectAttribute.getWsNum())){
			whereSql+="  and m.barrier_number =?";
			list.add(selectAttribute.getWsNum());
		}
		if(selectAttribute.getInstallAddress()!=null && !"".equals(selectAttribute.getInstallAddress().trim())){
			whereSql += " and instr(m.install_address,?)>0 ";
			list.add(selectAttribute.getInstallAddress());
		}
		if(selectAttribute.getDept()!=null && !"".equals(selectAttribute.getDept().trim())){
			whereSql += " and instr(d.deptname,?)>0 ";
			list.add(selectAttribute.getDept());
		}
//		if(selectAttribute.getDept()!=null && !"".equals(selectAttribute.getDept().trim())){
//			whereSql += " and instr(u.deptname,?)>0 ";
//			list.add(selectAttribute.getDept());
//		}
		if(selectAttribute.getPerson()!=null && !"".equals(selectAttribute.getPerson().trim())){
			whereSql+="  and u.username =?";
			list.add(selectAttribute.getPerson());
		}
//		if(selectAttribute.getPerson()!=null && !"".equals(selectAttribute.getPerson().trim())){
//			whereSql+="  and t.username =?";
//			list.add(selectAttribute.getPerson());
//		}
		sql = selectSql + whereSql;

		System.out.println("公客待回复统计数sql="+sql);
		
		Object[] args = list.toArray();
		
		int total = this.getJdbcTemplate().queryForInt(sql, args);
		return total;
	}
	
	//未归集工单列表统计数
	@SuppressWarnings("unchecked")
	public List<TaskModel> queryNoSingleImputationList(String userId,MaleGuestSelectAttribute selectAttribute, int firstResult, int endResult,int pageSize){
		ArrayList list1 = new ArrayList();
		String sql = " select temp2.* from (select temp1.*, rownum num from (";
		 
		String selectSql =  "select m.task_id             as TaskId," +
							"       u.username," + 
							"       d.deptname," + 
							"       m.initiator           as Initiator," + 
							"       m.one_initiator       as OneInitiator," + 
							"       m.process_instance_id as ProcessInstanceId," + 
							"       m.send_time           as SendTime," + 
							"       m.theme               as Theme," + 
							"       m.task_assignee," + 
							"       m.state               as State," + 
							"       u.deptid              as DeptId," + 
							"       m.install_address     as Install_address," + 
							"       m.barrier_number      as Barrier_number," + 
							"       m.create_time," + 
							"       m.city," + 
							"       m.connect_person," + 
							"       m.process_limit," + 
							"       m.assignee," + 
							"       m.task_def_key," + 
							"       m.repeat_state," + 
							"       m.site_cd," + 
							"       m.rollback_flag" + 
							"  from pnr_act_transfer_office_main m, taw_system_user u, taw_system_dept d" + 
							" where m.assignee = u.userid(+)" + 
							"   and u.deptid = d.deptid(+)" + 
							"   and m.Themeinterface = 'maleGuest'" + 
							"   and nvl(m.male_guest_state,'0') in('0','3') " + 
							"   and m.state != 8" + 
							"   and m.assignee = ?";

//		String selectSql = " select t.id_ as TaskId,t.username,u.deptname,m.initiator as Initiator,m.one_initiator as OneInitiator,m.process_instance_id as ProcessInstanceId," +
//		" m.send_time as SendTime,m.theme as Theme,m.task_assignee,m.state as State,u.deptid as DeptId,m.install_address as Install_address," +
//		" m.barrier_number as Barrier_number,m.create_time,m.city,m.connect_person,m.process_limit, t.assignee_, t.task_def_key_, m.repeat_state from " +
//		" (select tu.username, RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_  left join taw_system_user tu  on tu.userid = RES.ASSIGNEE_" +
//		" WHERE RES.ASSIGNEE_ = ? and D.KEY_ = 'myMaleGuestProcess' and RES.Suspension_State_ = 1) t,(select k.initiator,k.one_initiator,k.process_instance_id,k.send_time," +
//		" k.theme,k.install_address,k.barrier_number, decode(k.task_assignee,null,k.initiator,k.task_assignee) as task_assignee,k.state,k.Themeinterface," +
//		" k.city, k.create_time, k.connect_person,k.process_limit,k.repeat_state from pnr_act_transfer_office_main k) m,( select tsu.deptid,tsd.deptname,tsu.userid from taw_system_user tsu " +
//		" left join taw_system_dept tsd  on tsu.deptid = tsd.deptid )u " +
//		" where t.proc_inst_id_ = m.process_instance_id and m.task_assignee = u.userid(+) and m.Themeinterface = 'maleGuest' and m.state!=8";
		list1.add(userId);
		//拼接条件查询
		String whereSql = "";
		if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >=?";
			list1.add(selectAttribute.getBeginTime());
		}
		if (selectAttribute.getEndTime() != null && !selectAttribute.getEndTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?";
			list1.add(selectAttribute.getEndTime());
		}
		if (selectAttribute.getMaleGuestNumber() != null && !selectAttribute.getMaleGuestNumber().equals("")) {
			whereSql += " and m.process_instance_id =?";
			list1.add(selectAttribute.getMaleGuestNumber().trim());
		}
		if (selectAttribute.getTheme() != null && !selectAttribute.getTheme().equals("")) {
			whereSql += " and instr(m.theme,?)>0 ";
			list1.add(selectAttribute.getTheme().trim());
		}
		if (selectAttribute.getStatus() != null && !selectAttribute.getStatus().equals("")) {
			whereSql += " and m.task_def_key = ?";
			list1.add(selectAttribute.getStatus());
		}
//		if (selectAttribute.getStatus() != null && !selectAttribute.getStatus().equals("")) {
//			whereSql += " and t.task_def_key_ = ?";
//			list1.add(selectAttribute.getStatus());
//		}
		if(selectAttribute.getWsNum()!=null && !"".equals(selectAttribute.getWsNum())){
			whereSql+="  and m.barrier_number =?";
			list1.add(selectAttribute.getWsNum());
		}
		if(selectAttribute.getInstallAddress()!=null && !"".equals(selectAttribute.getInstallAddress().trim())){
			whereSql += " and instr(m.install_address,?)>0 ";
			list1.add(selectAttribute.getInstallAddress());
		}
		if(selectAttribute.getDept()!=null && !"".equals(selectAttribute.getDept().trim())){
			whereSql += " and instr(d.deptname,?)>0 ";
			list1.add(selectAttribute.getDept());
		}
//		if(selectAttribute.getDept()!=null && !"".equals(selectAttribute.getDept().trim())){
//			whereSql += " and instr(u.deptname,?)>0 ";
//			list1.add(selectAttribute.getDept());
//		}
		if(selectAttribute.getPerson()!=null && !"".equals(selectAttribute.getPerson().trim())){
			whereSql+="  and u.username =?";
			list1.add(selectAttribute.getPerson());
		}
//		if(selectAttribute.getPerson()!=null && !"".equals(selectAttribute.getPerson().trim())){
//			whereSql+="  and t.username =?";
//			list1.add(selectAttribute.getPerson());
//		}
		sql += selectSql + whereSql
				+ " order by m.send_time desc) temp1 where rownum <=?) temp2 where temp2.num > ?";
//		sql += selectSql + whereSql
//		+ " order by t.create_time_ desc) temp1 where rownum <=?) temp2 where temp2.num > ?";
		
		list1.add(endResult * pageSize);
		
		list1.add(firstResult * pageSize);
		Object[] values = list1.toArray();
		
		System.out.println("公客待回复统明细sql="+sql);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql, values);
		for (Map map : list) {
			TaskModel model = new TaskModel();
			if(map.get("TaskId")!=null && !"".equals(map.get("TaskId"))){
				
				model.setTaskId(map.get("TaskId").toString());
			}else{
				model.setTaskId("");
			}
			if(map.get("Initiator")!=null && !"".equals(map.get("Initiator"))){
				model.setInitiator(map.get("Initiator").toString());
			}else{
				model.setInitiator("");
			}
			if(map.get("site_cd")!=null && !"".equals(map.get("site_cd"))){
				model.setSiteCd(map.get("site_cd").toString());
			}else{
				model.setSiteCd("");
			}
			
			//临时做为空判断，正常OneInitiator的值不可以为空
			if (map.get("OneInitiator") != null){
				model.setOneInitiator(map.get("OneInitiator").toString());
			}else{
				model.setOneInitiator("");
			}		
			model.setProcessInstanceId(map.get("ProcessInstanceId").toString());
			if (map.get("SendTime") != null) {
				if (!map.get("SendTime").toString().equals("")) {
					try {
						model.setSendTime(format.parse(map.get("SendTime")
								.toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

			}
			if(map.get("Theme")!=null && !"".equals(map.get("Theme"))){
				model.setTheme(map.get("Theme").toString());
			}else{
				model.setTheme("");
			}
			if(map.get("DeptId")!=null && !"".equals(map.get("DeptId"))){
				model.setDeptId(map.get("DeptId").toString());
			}else{
				model.setDeptId("");
			}
			if(map.get("State")!=null && !"".equals(map.get("State"))){
				model.setState(Integer.parseInt(map.get("State").toString()));
			}else{
				model.setState(0);
			}
			if(map.get("Install_address")!=null && !"".equals(map.get("Install_address"))){
				model.setInstallAddress(map.get("Install_address").toString());
			}else{
				model.setInstallAddress("");
			}
			if(map.get("Barrier_number")!=null && !"".equals(map.get("Barrier_number"))){
				model.setBarrierNumber(map.get("Barrier_number").toString());
			}else{
				model.setBarrierNumber("");
			}
			if(map.get("create_time")!=null && !"".equals(map.get("create_time"))){
				if (map.get("create_time") != null) {
					if (!map.get("create_time").toString().equals("")) {
						try {
							model.setCreateTime(format.parse(map.get("create_time")
									.toString()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}

				}
			}
			if(map.get("city")!=null && !"".equals(map.get("city"))){
				model.setCity(map.get("city").toString());
			}
			if(map.get("connect_person")!=null &&!"".equals(map.get("connect_person"))){
				model.setConnectPerson(map.get("connect_person").toString());
			}
			if(map.get("process_limit")!=null &&!"".equals(map.get("process_limit"))){
				model.setProcessLimit(map.get("process_limit").toString());
			}
			//工单历时 = 当前时间-故障发生时间：当工单历时大于处理时限，改变颜色
			if(map.get("create_time")!=null && !"".equals(map.get("create_time"))&& map.get("SendTime") != null){
				//两时间做差
				Date startTime = new Date();
				Date endTime = model.getCreateTime();
				long sortTime = startTime.getTime()-endTime.getTime();
				long hours = sortTime / (1000 * 60 * 60 );
				long minutes = sortTime/(1000*60)-hours*60;
				long second = sortTime/1000 - hours*60*60 - minutes*60;
				String newTime = hours+"小时"+minutes+"分钟"+second+"秒";
				model.setWorkTask(newTime);
				if(model.getProcessLimit()!=null&&!"".equals(model.getProcessLimit())){
					long processLimit = Long.parseLong(model.getProcessLimit());
					if(processLimit>hours){//没有超出时限
						model.setChangeColor(true);
					}else{//超出时限
						model.setChangeColor(false);
					}
				}
			}
			if(map.get("assignee")!=null && !"".equals(map.get("assignee"))){
				model.setExecutor(map.get("assignee").toString());
			}
			//状态存储--start
			String state = "";
			if(map.get("task_def_key")!=null && !"".equals(map.get("task_def_key"))&&"newMaleGuest".equals(map.get("task_def_key"))){
				state = "派发工单";
			}else if(map.get("task_def_key")!=null && !"".equals(map.get("task_def_key"))&&"auditor".equals(map.get("task_def_key"))){
				state="故障处理";
			}
			if(map.get("repeat_state")!=null && !"".equals(map.get("repeat_state"))&&"1".equals(map.get("repeat_state").toString().trim())){
				state = "(重修)"+state;
			}
			model.setStatusName(state);
			//驳回标识
			if(map.get("rollback_flag")!=null && !"".equals(map.get("rollback_flag"))){
				model.setRollbackFlag(map.get("rollback_flag").toString());
			}
			//--end
			
			r.add(model);

		}

		return r;
	}
/**
 * 返回 tnum：归集工单的个数，
 *  subtnum：归集工单的子工单的个数，
 *  nortnum：正常的工单个数,
 *  process_instance_id:归集工单的工单号
 *  sublist：归集工单的子工单的工单号集合
 */
	@Override
	public Map mailGuestTogetherVerification(String siteCd) {
		
//		生成归集工单规则:按照局站编号，一定时间内(7*24)，在处理环节但未处理的。
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Map returnMap = new HashMap();
		StringBuffer sqlbuffer = new StringBuffer();
		ArrayList varList = new ArrayList();
		Date preDate = new Date(new Date().getTime()-7*24*60*60*1000);
		varList.add(format.format(preDate));		
		varList.add(siteCd);
		
		sqlbuffer.append(" select nvl(sum(case when nvl(m.male_guest_state,'-') ='1' then 1 else 0 end),0) tnum,");
		sqlbuffer.append(" nvl(sum(case when nvl(m.male_guest_state,'-') ='2' then 1 else 0 end),0) subtnum,");
		sqlbuffer.append(" nvl(sum(case when nvl(m.male_guest_state,'-') ='0' then 1 else 0 end),0) nortnum");
		sqlbuffer.append(" from pnr_act_transfer_office_main m where to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss')>=? ");
		sqlbuffer.append(" and m.task_def_key ='auditor'");
		sqlbuffer.append(" and m.state !=8");
		sqlbuffer.append(" and m.site_cd=? ");
		
		Object[] values = varList.toArray();
       
		List<Map> mapList = this.getJdbcTemplate().queryForList(sqlbuffer.toString(), values);
		int tnum= 0;
		for (Map map : mapList) {
			tnum = 	Integer.parseInt(map.get("tnum").toString());	
		    returnMap.put("tnum",tnum );
		    returnMap.put("subtnum", map.get("subtnum"));
		    returnMap.put("nortnum", map.get("nortnum"));
			
		}
		returnMap.put("process_instance_id","-");

		if(tnum>0){
			StringBuffer tsqlbuffer = new StringBuffer();
	        tsqlbuffer.append("select m.process_instance_id from  pnr_act_transfer_office_main m where m.male_guest_state='1'");
	        tsqlbuffer.append("and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss')>=? ");
	        tsqlbuffer.append("and m.task_def_key ='auditor'");
	        tsqlbuffer.append(" and m.state !=8");
	        tsqlbuffer.append("and m.site_cd=? order by m.send_time ");
			List<Map> pmapList = this.getJdbcTemplate().queryForList(tsqlbuffer.toString(), values);
			for (Map map : pmapList) {
			    returnMap.put("process_instance_id",(map.get("process_instance_id").toString()) );				
			}
			
		}else{
			StringBuffer tsqlbuffer = new StringBuffer();
	        tsqlbuffer.append("select m.process_instance_id from  pnr_act_transfer_office_main m where m.male_guest_state='0'");
	        tsqlbuffer.append("and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss')>=? ");
	        tsqlbuffer.append("and m.task_def_key ='auditor'");
	        tsqlbuffer.append(" and m.state !=8");
	        tsqlbuffer.append(" and m.site_cd=? ");
	        
			List<Map> pmapList = this.getJdbcTemplate().queryForList(tsqlbuffer.toString(), values);
			List sublist = new ArrayList();
			for (Map map : pmapList) {
				sublist.add(map.get("process_instance_id"));
			}
			returnMap.put("sublist",sublist);
		}
		return returnMap;
	}


	@Override
	public void updateProcessInstanceIdForSubprocess(List sublist,
			String tprocessInstanceId) {
			int size = sublist.size();
			String str ="";
			for(int i=0;i<size ;i++){
				str +="'"+sublist.get(i)+"',";
			}
	        str = str.substring(0, str.length()-1);
			
			String sql = "update pnr_act_transfer_office_main m set m.parent_process_instance_id='"+tprocessInstanceId+"',m.male_guest_state='2' where m.process_instance_id in ("+str+")";
			
			this.getJdbcTemplate().update(sql);
	}
//抢修工单审核 抢修材料周期领用表统计数
	@SuppressWarnings("unchecked")
	public int getRepairMaterialCycleTableCount(String userId,MaleGuestSelectAttribute selectAttribute){
		ArrayList list = new ArrayList();
		String sql = "";
		String selectSql =	"select count(r.process_instance_id) as total" +
							"  from pnr_second_inspection_report r," + 
							"       pnr_act_transfer_office_main m," + 
							"       taw_system_user              u," + 
							"       taw_system_dept              d" + 
							" where r.process_instance_id = m.process_instance_id" + 
							"   and m.assignee = u.userid(+)" + 
							"   and u.deptid = d.deptid(+)" +
							"   and r.approve_flag in ('0','1')" +
					        "   and r.approve_sign is not null";
//							"   and r.county = '281101'" + 
//							"   and trunc(r.submit_date) >= to_date('2016/3/23', 'yyyy-mm-dd')" + 
//							"   and trunc(r.submit_date) <= to_date('2016/3/25', 'yyyy-mm-dd')";
		//区县
		if (selectAttribute.getCounty() != null && !selectAttribute.getCounty().equals("")) {
			selectSql += " and r.county = ?" ;
			list.add(selectAttribute.getCounty().trim());
		}
		//开始时间
		if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
			selectSql += " and trunc(r.submit_date) >= to_date(?, 'yyyy-mm-dd')";
			list.add(selectAttribute.getBeginTime());
		}
		//结束时间
		if (selectAttribute.getEndTime() != null && !selectAttribute.getEndTime().equals("")) {
			selectSql +=  " and trunc(r.submit_date) <= to_date(?, 'yyyy-mm-dd')";
			list.add(selectAttribute.getEndTime());
		}

		sql = selectSql;

		System.out.println("---6517---抢修材料周期领用表统计数sql="+sql);
		
		Object[] args = list.toArray();
		
		int total = this.getJdbcTemplate().queryForInt(sql, args);
		return total;
	}
	
	//抢修工单审核 抢修材料周期领用表列表
	@SuppressWarnings("unchecked")
	public List<TaskModel> getRepairMaterialCycleTableList(String userId,MaleGuestSelectAttribute selectAttribute, int firstResult, int endResult,int pageSize){
		ArrayList list1 = new ArrayList();
		String sql = " select temp2.* from (select temp1.*, rownum num from (";
		String selectSql =	"select m.task_id             as TaskId," +
							"       u.username," + 
							"       d.deptname," + 
							"       m.initiator           as Initiator," + 
							"       m.one_initiator       as OneInitiator," + 
							"       m.process_instance_id as ProcessInstanceId," + 
							"       m.send_time           as SendTime," + 
							"       m.theme               as Theme," + 
							"       m.task_assignee," + 
							"       m.state               as State," + 
							"       u.deptid              as DeptId," + 
							"       m.install_address     as Install_address," + 
							"       m.barrier_number      as Barrier_number," + 
							"       m.create_time," + 
							"       m.city," + 
							"       m.country," + 
							"       m.site_cd," + 
							"       m.fault_source," + 
							"       nvl(m.male_guest_state,'0') as male_guest_state," + 
							"       decode(nvl(m.male_guest_state, '0'),'1','是','否') as male_guest_state_name," + 
							"       m.connect_person," + 
							"       m.process_limit," + 
							"       m.assignee," + 
							"       m.task_def_key," + 
							"       m.repeat_state," + 
							"       r.approve_flag," + 
							"       m.sub_type," + 
							"       m.station_name," + 
							"       m.Last_Reply_Time," + 
							"       r.county," + 
							"       to_char(trunc(r.submit_date),'yyyy-mm-dd') as home_date," + 
							"       r.process_type," + 
							"       decode(r.process_type,'maleGuest','pnrTransferMaleGuest','transferOffice','pnrTransferOffice','未知') as pathOne," + 
							"       decode(r.process_type,'maleGuest','pnrTransferOfficeMaleGuest','transferOffice','pnrTransferOffice','未知') as pathTwo," +
							"       nvl(m.recent_main_scenes_name,'无') as recent_main_scenes_name," + 
							"       nvl(m.recent_copy_scenes_name,'无') as recent_copy_scenes_name," +
							"		decode(r.process_type," +
							"              'maleGuest'," + 
							"              '公客'," + 
							"              'transferOffice'," + 
							"              '抢修'," + 
							"              '未知') as process_type_name"+
							"  from pnr_second_inspection_report r," + 
							"       pnr_act_transfer_office_main m," + 
							"       taw_system_user              u," + 
							"       taw_system_dept              d" + 
							" where r.process_instance_id = m.process_instance_id" + 
							"   and m.assignee = u.userid(+)" + 
							"   and u.deptid = d.deptid(+)" +
		                    "   and r.approve_flag in ('0','1')" +
		                    "   and r.approve_sign is not null";
//							"   and r.county = '281101'" + 
//							"   and trunc(r.submit_date) >= to_date('2016/3/23', 'yyyy-mm-dd')" + 
//							"   and trunc(r.submit_date) <= to_date('2016/3/25', 'yyyy-mm-dd')";
		
		//区县
		if (selectAttribute.getCounty() != null && !selectAttribute.getCounty().equals("")) {
			selectSql += " and r.county = ?" ;
			list1.add(selectAttribute.getCounty().trim());
		}
		//开始时间
		if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
			selectSql += " and trunc(r.submit_date) >= to_date(?, 'yyyy-mm-dd')";
			list1.add(selectAttribute.getBeginTime());
		}
		//结束时间
		if (selectAttribute.getEndTime() != null && !selectAttribute.getEndTime().equals("")) {
			selectSql +=  " and trunc(r.submit_date) <= to_date(?, 'yyyy-mm-dd')";
			list1.add(selectAttribute.getEndTime());
		}
	
		sql += selectSql + " order by to_number(m.process_instance_id) desc) temp1 where rownum <=?) temp2 where temp2.num > ?";
		
		list1.add(endResult * pageSize);
		
		list1.add(firstResult * pageSize);
		Object[] values = list1.toArray();
		
		System.out.println("--6590----抢修材料周期领用表列表sql="+sql);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql, values);
		for (Map map : list) {
			TaskModel model = new TaskModel();
			if(map.get("TaskId")!=null && !"".equals(map.get("TaskId"))){
				
				model.setTaskId(map.get("TaskId").toString());
			}else{
				model.setTaskId("");
			}
			if(map.get("Initiator")!=null && !"".equals(map.get("Initiator"))){
				model.setInitiator(map.get("Initiator").toString());
			}else{
				model.setInitiator("");
			}
			
			//临时做为空判断，正常OneInitiator的值不可以为空
			if (map.get("OneInitiator") != null){
				model.setOneInitiator(map.get("OneInitiator").toString());
			}else{
				model.setOneInitiator("");
			}		
			model.setProcessInstanceId(map.get("ProcessInstanceId").toString());
			if (map.get("SendTime") != null) {
				if (!map.get("SendTime").toString().equals("")) {
					try {
						model.setSendTime(format.parse(map.get("SendTime")
								.toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

			}
			if(map.get("Theme")!=null && !"".equals(map.get("Theme"))){
				model.setTheme(map.get("Theme").toString());
			}else{
				model.setTheme("");
			}
			if(map.get("DeptId")!=null && !"".equals(map.get("DeptId"))){
				model.setDeptId(map.get("DeptId").toString());
			}else{
				model.setDeptId("");
			}
			if(map.get("State")!=null && !"".equals(map.get("State"))){
				model.setState(Integer.parseInt(map.get("State").toString()));
			}else{
				model.setState(0);
			}
			if(map.get("Install_address")!=null && !"".equals(map.get("Install_address"))){
				model.setInstallAddress(map.get("Install_address").toString());
			}else{
				model.setInstallAddress("");
			}
			if(map.get("Barrier_number")!=null && !"".equals(map.get("Barrier_number"))){
				model.setBarrierNumber(map.get("Barrier_number").toString());
			}else{
				model.setBarrierNumber("");
			}
			if(map.get("create_time")!=null && !"".equals(map.get("create_time"))){
				if (map.get("create_time") != null) {
					if (!map.get("create_time").toString().equals("")) {
						try {
							model.setCreateTime(format.parse(map.get("create_time")
									.toString()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}

				}
			}
			//地市
			if(map.get("city")!=null && !"".equals(map.get("city").toString())){
				model.setCity(map.get("city").toString());
			}
			//区县
			if(map.get("country")!=null && !"".equals(map.get("country").toString())){
				model.setCountry(map.get("country").toString());
			}
			//站址码
			if(map.get("site_cd")!=null && !"".equals(map.get("site_cd").toString())){
				model.setSiteCd(map.get("site_cd").toString());
			}
			//故障原因
			if(map.get("fault_source")!=null && !"".equals(map.get("fault_source").toString())){
				model.setFaultSource(map.get("fault_source").toString());
			}
			//是否归集
			if(map.get("male_guest_state")!=null &&!"".equals(map.get("male_guest_state"))){
				model.setMaleGuestState(map.get("male_guest_state").toString());
			}
			//是否归集中文值
			if(map.get("male_guest_state_name")!=null &&!"".equals(map.get("male_guest_state_name"))){
				model.setMaleGuestStateName(map.get("male_guest_state_name").toString());
			}
			if(map.get("connect_person")!=null &&!"".equals(map.get("connect_person"))){
				model.setConnectPerson(map.get("connect_person").toString());
			}
			if(map.get("process_limit")!=null &&!"".equals(map.get("process_limit"))){
				model.setProcessLimit(map.get("process_limit").toString());
			}
			//工单历时 = 当前时间-故障发生时间：当工单历时大于处理时限，改变颜色
			if(map.get("create_time")!=null && !"".equals(map.get("create_time"))&& map.get("SendTime") != null){
				//两时间做差
				Date startTime = new Date();
				Date endTime = model.getCreateTime();
				long sortTime = startTime.getTime()-endTime.getTime();
				long hours = sortTime / (1000 * 60 * 60 );
				long minutes = sortTime/(1000*60)-hours*60;
				long second = sortTime/1000 - hours*60*60 - minutes*60;
				String newTime = hours+"小时"+minutes+"分钟"+second+"秒";
				model.setWorkTask(newTime);
//				if(model.getProcessLimit()!=null&&!"".equals(model.getProcessLimit())){
//					long processLimit = Long.parseLong(model.getProcessLimit());
//					if(processLimit>hours){//没有超出时限
//						model.setChangeColor(true);
//					}else{//超出时限
//						model.setChangeColor(false);
//					}
//				}
			}
			if(map.get("assignee")!=null && !"".equals(map.get("assignee"))){
				model.setExecutor(map.get("assignee").toString());
			}
			//流程类型
			String process_type = "";
			if(map.get("process_type")!=null && !"".equals(map.get("process_type"))){
				process_type = map.get("process_type").toString();
				model.setProcessType(map.get("process_type").toString());
			}
			//状态存储--start
			String state = "";
			if("maleGuest".equals(process_type)){
				if(map.get("task_def_key")!=null && !"".equals(map.get("task_def_key"))){
					if("newMaleGuest".equals(map.get("task_def_key"))){
						state = "派发工单";
					}else if("auditor".equals(map.get("task_def_key"))){
						state="故障处理";
					}else if("acheck".equals(map.get("task_def_key"))){
						state="区县维护中心一次核验";
					}else if("twoSpotChecks".equals(map.get("task_def_key"))){
						state="区县维护中心二次抽查";
					}else if("auditReport".equals(map.get("task_def_key"))){
						state="区县维护中心综合报表审核";
					}
				}
				if(map.get("repeat_state")!=null && !"".equals(map.get("repeat_state"))&&"1".equals(map.get("repeat_state").toString().trim())){
					state = "(重修)"+state;
				}
			}else if("transferOffice".equals(process_type)){
				if(map.get("task_def_key")!=null && !"".equals(map.get("task_def_key"))){
					if("newTask".equals(map.get("task_def_key"))){
						state = "派发工单";
					}else if("transferTask".equals(map.get("task_def_key"))){
						state="传输局";
					}else if("epibolyTask".equals(map.get("task_def_key"))){
						state="外包公司";
					}else if("transferHandle".equals(map.get("task_def_key"))){
						state="施工队";
					}else if("acheck".equals(map.get("task_def_key"))){
						state="一次核验";
					}else if("twoSpotChecks".equals(map.get("task_def_key"))){
						state="二次抽查";
					}else if("auditReport".equals(map.get("task_def_key"))){
						state="周期报表";
					}
				}
			}
			model.setStatusName(state);
			
			if(map.get("approve_flag")!=null && !"".equals(map.get("approve_flag"))){
				model.setApproveFlag(map.get("approve_flag").toString());
			}
			
			//工单子类型
			if(map.get("sub_type")!=null && !"".equals(map.get("sub_type"))){
				model.setSubType(map.get("sub_type").toString());
			}
			//地市
			if(map.get("city")!=null && !"".equals(map.get("city"))){
				model.setCity(map.get("city").toString());
			}
			//局站名称
			if(map.get("station_name")!=null && !"".equals(map.get("station_name"))){
				model.setStationName(map.get("station_name").toString());
			}
			//故障处理回复时间
			if(map.get("last_reply_time")!=null && !"".equals(map.get("last_reply_time"))){
				if (map.get("last_reply_time") != null) {
					if (!map.get("last_reply_time").toString().equals("")) {
						try {
							model.setLastReplyTime(format.parse(map.get("last_reply_time")
									.toString()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}

				}
			}
			
			//区县
			if(map.get("county")!=null && !"".equals(map.get("county"))){
				model.setCountry(map.get("county").toString());
			}
			
			//归属时间，先存在后备人员属性里。
			if(map.get("home_date")!=null && !"".equals(map.get("home_date"))){
				model.setTempTask(map.get("home_date").toString());
			}
			//流程类型
			if(map.get("process_type")!=null && !"".equals(map.get("process_type"))){
				model.setProcessType(map.get("process_type").toString());
			}
			//路径1
			if(map.get("pathOne")!=null && !"".equals(map.get("pathOne"))){
				model.setPathOne(map.get("pathOne").toString());
			}
			//路径2
			if(map.get("pathTwo")!=null && !"".equals(map.get("pathTwo"))){
				model.setPathTwo(map.get("pathTwo").toString());
			}
			//最新主场景
			if(map.get("recent_main_scenes_name")!=null && !"".equals(map.get("recent_main_scenes_name"))){
				model.setRecentMainScenesName(map.get("recent_main_scenes_name").toString());
			}
			//最新子场景
			if(map.get("recent_copy_scenes_name")!=null && !"".equals(map.get("recent_copy_scenes_name"))){
				model.setRecentCopyScenesName(map.get("recent_copy_scenes_name").toString());
			}
			//流程类型中文值
			if(map.get("process_type_name")!=null && !"".equals(map.get("process_type_name"))){
				model.setProcessTypeName(map.get("process_type_name").toString());
			}
			//--end
			
			r.add(model);

		}

		return r;
	}
	
	//更新报表审批状态
	public void updateApproveFlag(final Map<String,String> param){
		String reportType = param.get("reportType");
		if(reportType!=null&&!"".equals(reportType)){
			String tableName="";
			if("firstVerify".equals(reportType)){
				tableName="pnr_first_verify_report";
			}else if("secondInspection".equals(reportType)){
				tableName="pnr_second_inspection_report";
			}
//			String sql =
//				"update "+tableName+" r" +
//				"   set r.approve_flag = ?" + 
//				" where r.process_instance_id = ?" + 
//				"   and r.county = ?" + 
//				"   and r.report_date = to_date(?, 'yyyy-mm-dd')";
			String sql =
				"update "+tableName+" r" +
				"   set r.approve_flag = ?," + 
				"   r.approve_date = sysdate," + 
				"   r.approve_user_id = ?" + 
				" where r.process_instance_id = ?" +
			    "   and r.county = ?" + 
			    "   and r.report_date = to_date(?, 'yyyy-mm-dd')";

			this.getJdbcTemplate().update(  
					                sql,   
					                 new PreparedStatementSetter(){  
					                       @Override  
					                      public void setValues(PreparedStatement ps) throws SQLException {  
					                          ps.setString(1, param.get("approveFlag"));  
					                          ps.setString(2, param.get("approveUserId"));  
					                          ps.setString(3,  param.get("approvePId"));  
						                      ps.setString(4, param.get("tcountry"));  
						                      ps.setString(5, param.get("tdate"));  
					                      }  
					                 }  
					         );  
		}
	}
	
	//获取报表未审批的工单个数
	public int getNotApprovedSheetCount(MaleGuestSelectAttribute selectAttribute){
		String reportType = selectAttribute.getReportType();
		String tableName="";
		if("firstVerify".equals(reportType)){
			tableName="pnr_first_verify_report";
		}else if("secondInspection".equals(reportType)){
			tableName="pnr_second_inspection_report";
		}
		
		ArrayList list = new ArrayList();
		String sql = "";
		String selectSql = 	"select count(r.process_instance_id) as total" +
							"  from "+tableName+" r" + 
							" where r.approve_flag = '0'";
		String whereSql = "";
		//区县
		if (selectAttribute.getCounty() != null && !selectAttribute.getCounty().equals("")) {
			whereSql += " and r.county =?";
			list.add(selectAttribute.getCounty().trim());
		}
		//日期
		if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
			whereSql += " and r.report_date = to_date(?, 'yyyy-mm-dd')";
			list.add(selectAttribute.getBeginTime());
		}
		
		if("secondInspection".equals(reportType)){//超过72小时的是默认处理的
			whereSql+=" and (sysdate - to_date(to_char(trunc(r.rise_time), 'yyyy-mm-dd hh24:mi:ss')," + 
			"                'yyyy-mm-dd hh24:mi:ss')) * 24 <= 72";
		}
		
		sql = selectSql + whereSql;

		System.out.println("------报表未审批的工单个数sql="+sql);
		
		Object[] args = list.toArray();
		
		int total = this.getJdbcTemplate().queryForInt(sql, args);
		return total;
	}
	
	//查询审批人签字
	public String getApprovalSign(MaleGuestSelectAttribute selectAttribute){
		String reportType = selectAttribute.getReportType();
		String tableName="";
		if("firstVerify".equals(reportType)){
			tableName="pnr_first_verify_report";
		}else if("secondInspection".equals(reportType)){
			tableName="pnr_second_inspection_report";
		}
		
		String approvalSign = "";//审批人签字
		ArrayList list = new ArrayList();
		String sql = "";
		String selectSql =  "select r.approve_sign" +
							"  from "+tableName+" r" + 
							" where 1=1";
		String whereSql = "";
		//二次抽查审批率达到20%以上就可以提交审批人签字；一次抽检必须全部抽检才能提交审批人签字
		if("secondInspection".equals(reportType)){
			sql+=" and approve_flag !='0'";
		}
		//区县
		if (selectAttribute.getCounty() != null && !selectAttribute.getCounty().equals("")) {
			whereSql += " and r.county =?";
			list.add(selectAttribute.getCounty().trim());
		}
		//日期
		if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
			whereSql += " and r.report_date = to_date(?, 'yyyy-mm-dd')";
			list.add(selectAttribute.getBeginTime());
		}
		
		if("secondInspection".equals(reportType)){
			whereSql += " and r.approve_sign is not null";
		}
		
		whereSql+=" and rownum = 1";
		sql = selectSql + whereSql;
		System.out.println("------查询审批人签字sql="+sql);
		Object[] args = list.toArray();
		List<Map> list1 = this.getJdbcTemplate().queryForList(sql,args);
		if (list1 != null && !list1.isEmpty() && list1.size() > 0) {
			if (list1.get(0).get("approve_sign") != null) {
				approvalSign = list1.get(0).get("approve_sign").toString();
			}
		}
		return approvalSign;
	}
	
	//提交审批人签字
	public int submitApprovalSign(final MaleGuestSelectAttribute selectAttribute){
		String reportType = selectAttribute.getReportType();
		String tableName="";
		if("firstVerify".equals(reportType)){
			tableName="pnr_first_verify_report";
		}else if("secondInspection".equals(reportType)){
			tableName="pnr_second_inspection_report";
		}
	String sql = "update "+tableName+" r" +
				 "   set r.approve_sign = ?, r.submit_user_id = ?, r.submit_date = ?,r.approve_phone = ?" + 
				 " where 1 = 1" + 
				 "   and r.county = ?" + 
				 "   and r.report_date = to_date(?, 'yyyy-mm-dd')";
//	String sql = "update "+tableName+" r" +
//	"   set r.approve_sign = ?, r.submit_user_id = ?, r.submit_date = ?" + 
//	" where 1 = 1" ; 
//	
//	//二次抽查审批率达到20%以上就可以提交审批人签字；一次抽检必须全部抽检才能提交审批人签字
//	if("secondInspection".equals(reportType)){
//		sql+=" and approve_flag !='0'";
//	}
	
	final java.util.Date submitDate=new java.util.Date();
	this.getJdbcTemplate().update(  
			                sql,   
			                 new PreparedStatementSetter(){  
			                       @Override  
			                      public void setValues(PreparedStatement ps) throws SQLException {  
			                          ps.setString(1,selectAttribute.getApprovalSign());  
			                          ps.setString(2,selectAttribute.getPerson());  
			                          ps.setObject(3,new java.sql.Timestamp(submitDate.getTime()));
			                          ps.setString(4,selectAttribute.getApprovalPhone()); 
			                          ps.setString(5,selectAttribute.getCounty());  
			                          ps.setString(6,selectAttribute.getBeginTime()); 
			                      }  
			                 }  
			         );  
		
		return 0;
	}
	
	//插入周期领用表
	public void insertCycleCollarReport(final List<CycleCollarReportModel> reportList,final String userId,MaleGuestSelectAttribute selectAttribute){
		// 保存数据
		String sql ="insert into pnr_cycle_collar_report" +
					"  (rise_time," + 
					"   process_instance_id," + 
					"   county," + 
					"   start_date," + 
					"   end_date," + 
					"   home_date," + 
					"   report_number," + 
					"   report_create_date," + 
					"   report_create_user_id," +
					"   process_type)" + 
					"values" + 
					"  (sysdate, ?, ?, ?, ?, ?, ?, ?, ?,?)";
		
		final java.util.Date reportCreateDate=new java.util.Date();
		this.getJdbcTemplate().execute(sql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement ps)
					throws SQLException, DataAccessException {
				for (int i = 0; i < reportList.size(); i++) {
					ps.setString(1, reportList.get(i).getProcessInstanceId());
					ps.setString(2, reportList.get(i).getCounty());
					ps.setObject(3,new java.sql.Date(reportList.get(i).getStartDate().getTime()));
					ps.setObject(4,new java.sql.Date(reportList.get(i).getEndDate().getTime()));
					ps.setObject(5,new java.sql.Date(reportList.get(i).getHomeDate().getTime()));
					ps.setString(6, reportList.get(i).getReportNumber());
					ps.setObject(7,new java.sql.Timestamp(reportCreateDate.getTime()));
					ps.setString(8, userId);
					ps.setString(9, reportList.get(i).getProcessType());
					ps.addBatch();
				}
				return ps.executeBatch();
			}
		});
	}
	
	//获取处理人（公用）
	public String getDealingPeopleOfRepair(String areaId,String roleId){
		String dealingPeople = "";//处理人
		String sql= "select re.userid" +
					"  from taw_system_sub_role s, taw_system_userrefrole re" + 
					" where s.id = re.subroleid" + 
					"   and s.roleid = ?" + 
					"   and s.deleted = '0'" + 
					"   and s.deptid = ?";
		ArrayList list = new ArrayList();
		list.add(roleId.trim());
		list.add(areaId.trim());
//		System.out.println("------获取处理人sql="+sql);
		Object[] args = list.toArray();
		List<Map> list1 = this.getJdbcTemplate().queryForList(sql,args);
		if (list1 != null && !list1.isEmpty() && list1.size() > 0) {
			if (list1.get(0).get("userid") != null) {
				dealingPeople = list1.get(0).get("userid").toString();
			}
		}
		return dealingPeople;
	}
	
	//抢修工单审核 一次核验统计数
	@SuppressWarnings("unchecked")
	public int getFirstVerifyCount(String userId,MaleGuestSelectAttribute selectAttribute){
		ArrayList list = new ArrayList();
		String sql = "";
		String selectSql =  "select count(r.process_instance_id) as total" +
							"  from pnr_first_verify_report      r," + 
							"       pnr_act_transfer_office_main m," + 
							"       taw_system_user              u," + 
							"       taw_system_dept              d" + 
							" where r.process_instance_id = m.process_instance_id(+)" + 
							"   and m.assignee = u.userid(+)" + 
							"   and u.deptid = d.deptid(+)";
						//	"    and r.county ='"+selectAttribute.getCounty().trim()+"'"+
							//" and r.report_date = to_date('"+selectAttribute.getBeginTime()+"', 'yyyy-mm-dd')";
		
		
		String whereSql = "";
		//区县
		if (selectAttribute.getCounty()!= null && !selectAttribute.getCounty().equals("")) {
			whereSql += " and r.county =?";
			list.add(selectAttribute.getCounty().trim());
		}
		//日期
		if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
			whereSql += " and r.report_date = to_date(?,'yyyy-mm-dd')";
			list.add(selectAttribute.getBeginTime());
		}
		sql = selectSql + whereSql;

		System.out.println("------一次核验统计数sql="+sql);
		
		Object[] args = list.toArray();
//		int total = 0;
//		List<Map> list1 = this.getJdbcTemplate().queryForList(sql,args);
//		if (list1 != null && !list1.isEmpty() && list1.size() > 0) {
//			if (list1.get(0).get("total") != null) {
//				total = Integer.parseInt(list1.get(0).get("total").toString());
//			}
//		}
//	//	return val;
		
		
		int total = this.getJdbcTemplate().queryForInt(sql,args);
		return total;
	}
	
		//抢修工单审核 一次核验列表
	@SuppressWarnings("unchecked")
	public List<TaskModel> getFirstVerifyList(String userId,MaleGuestSelectAttribute selectAttribute, int firstResult, int endResult,int pageSize){
		ArrayList list1 = new ArrayList();
		String sql = " select temp2.* from (select temp1.*, rownum num from (";
		 
		String selectSql =  "select m.task_id             as TaskId," +
							"       u.username," + 
							"       d.deptname," + 
							"       m.initiator           as Initiator," + 
							"       m.one_initiator       as OneInitiator," + 
							"       m.process_instance_id as ProcessInstanceId," + 
							"       m.send_time           as SendTime," + 
							"       m.theme               as Theme," + 
							"       m.task_assignee," + 
							"       m.state               as State," + 
							"       u.deptid              as DeptId," + 
							"       m.install_address     as Install_address," + 
							"       m.barrier_number      as Barrier_number," + 
							"       m.create_time," + 
							"       m.city," + 
							"       m.country," + 
							"       m.site_cd," + 
							"       m.fault_source," + 
							"       nvl(m.male_guest_state,'0') as male_guest_state," + 
							"       decode(nvl(m.male_guest_state, '0'),'1','是','否') as male_guest_state_name," + 
							"       m.connect_person," + 
							"       m.process_limit," + 
							"       m.assignee," + 
							"       m.task_def_key," + 
							"       m.repeat_state," + 
							"       r.approve_flag," + 
							"       decode(r.approve_flag,'0','未审批','1','通过','2','驳回','未知') as approve_flag_name," + 
							"       decode(r.approve_flag,'0','N','Y') as handle_flag," + 
//							"       case when (sysdate - r.rise_time) * 24 > 24 and r.approve_flag = '0' then 'Y' else 'N' end as timeout_flag,m.sub_type,m.station_name,m.Last_Reply_Time" + 
							"       case when (sysdate - to_date(to_char(trunc(r.rise_time), 'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd hh24:mi:ss')) * 24 > 24 and r.approve_flag = '0' then 'Y' else 'N' end as timeout_flag,m.sub_type,m.station_name,m.Last_Reply_Time," + 
							"       r.process_type," + 
							"       decode(r.process_type,'maleGuest','pnrTransferMaleGuest','transferOffice','pnrTransferOffice','未知') as pathOne," + 
							"       decode(r.process_type,'maleGuest','pnrTransferOfficeMaleGuest','transferOffice','pnrTransferOffice','未知') as pathTwo," + 
							"       nvl(m.recent_main_scenes_name,'无') as recent_main_scenes_name," + 
							"       nvl(m.recent_copy_scenes_name,'无') as recent_copy_scenes_name," + 
							"		decode(r.process_type," +
							"              'maleGuest'," + 
							"              '公客'," + 
							"              'transferOffice'," + 
							"              '抢修'," + 
							"              '未知') as process_type_name"+
							"  from pnr_first_verify_report      r," + 
							"       pnr_act_transfer_office_main m," + 
							"       taw_system_user              u," + 
							"       taw_system_dept              d" + 
							" where r.process_instance_id = m.process_instance_id(+)" + 
							"   and m.assignee = u.userid(+)" + 
							"   and u.deptid = d.deptid(+)" ;

		//拼接条件查询
		String whereSql = "";
		//区县
		if (selectAttribute.getCounty() != null && !selectAttribute.getCounty().equals("")) {
			whereSql += " and r.county =?";
			list1.add(selectAttribute.getCounty().trim());
		}
		//日期
		if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
			whereSql += " and r.report_date = to_date(?, 'yyyy-mm-dd')";
			list1.add(selectAttribute.getBeginTime());
		}
	
		sql += selectSql + whereSql
				+ " order by to_number(m.process_instance_id) desc) temp1 where rownum <=?) temp2 where temp2.num > ?";
		
		list1.add(endResult * pageSize);
		
		list1.add(firstResult * pageSize);
		Object[] values = list1.toArray();
		
		System.out.println("------一次核验列表sql="+sql);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql, values);
		for (Map map : list) {
			TaskModel model = new TaskModel();
			if(map.get("TaskId")!=null && !"".equals(map.get("TaskId"))){
				
				model.setTaskId(map.get("TaskId").toString());
			}else{
				model.setTaskId("");
			}
			if(map.get("Initiator")!=null && !"".equals(map.get("Initiator"))){
				model.setInitiator(map.get("Initiator").toString());
			}else{
				model.setInitiator("");
			}
			
			//临时做为空判断，正常OneInitiator的值不可以为空
			if (map.get("OneInitiator") != null){
				model.setOneInitiator(map.get("OneInitiator").toString());
			}else{
				model.setOneInitiator("");
			}		
			model.setProcessInstanceId(map.get("ProcessInstanceId").toString());
			if (map.get("SendTime") != null) {
				if (!map.get("SendTime").toString().equals("")) {
					try {
						model.setSendTime(format.parse(map.get("SendTime")
								.toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

			}
			if(map.get("Theme")!=null && !"".equals(map.get("Theme"))){
				model.setTheme(map.get("Theme").toString());
			}else{
				model.setTheme("");
			}
			if(map.get("DeptId")!=null && !"".equals(map.get("DeptId"))){
				model.setDeptId(map.get("DeptId").toString());
			}else{
				model.setDeptId("");
			}
			if(map.get("State")!=null && !"".equals(map.get("State"))){
				model.setState(Integer.parseInt(map.get("State").toString()));
			}else{
				model.setState(0);
			}
			if(map.get("Install_address")!=null && !"".equals(map.get("Install_address"))){
				model.setInstallAddress(map.get("Install_address").toString());
			}else{
				model.setInstallAddress("");
			}
			if(map.get("Barrier_number")!=null && !"".equals(map.get("Barrier_number"))){
				model.setBarrierNumber(map.get("Barrier_number").toString());
			}else{
				model.setBarrierNumber("");
			}
			if(map.get("create_time")!=null && !"".equals(map.get("create_time"))){
				if (map.get("create_time") != null) {
					if (!map.get("create_time").toString().equals("")) {
						try {
							model.setCreateTime(format.parse(map.get("create_time")
									.toString()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}

				}
			}
			//地市
			if(map.get("city")!=null && !"".equals(map.get("city").toString())){
				model.setCity(map.get("city").toString());
			}
			//区县
			if(map.get("country")!=null && !"".equals(map.get("country").toString())){
				model.setCountry(map.get("country").toString());
			}
			//站址码
			if(map.get("site_cd")!=null && !"".equals(map.get("site_cd").toString())){
				model.setSiteCd(map.get("site_cd").toString());
			}
			//故障原因
			if(map.get("fault_source")!=null && !"".equals(map.get("fault_source").toString())){
				model.setFaultSource(map.get("fault_source").toString());
			}
			//是否归集
			if(map.get("male_guest_state")!=null &&!"".equals(map.get("male_guest_state"))){
				model.setMaleGuestState(map.get("male_guest_state").toString());
			}
			//是否归集中文值
			if(map.get("male_guest_state_name")!=null &&!"".equals(map.get("male_guest_state_name"))){
				model.setMaleGuestStateName(map.get("male_guest_state_name").toString());
			}
			//联系人
			if(map.get("connect_person")!=null &&!"".equals(map.get("connect_person"))){
				model.setConnectPerson(map.get("connect_person").toString());
			}
			if(map.get("process_limit")!=null &&!"".equals(map.get("process_limit"))){
				model.setProcessLimit(map.get("process_limit").toString());
			}
			//工单历时 = 当前时间-故障发生时间：当工单历时大于处理时限，改变颜色
			if(map.get("create_time")!=null && !"".equals(map.get("create_time"))&& map.get("SendTime") != null){
				//两时间做差
				Date startTime = new Date();
				Date endTime = model.getCreateTime();
				long sortTime = startTime.getTime()-endTime.getTime();
				long hours = sortTime / (1000 * 60 * 60 );
				long minutes = sortTime/(1000*60)-hours*60;
				long second = sortTime/1000 - hours*60*60 - minutes*60;
				String newTime = hours+"小时"+minutes+"分钟"+second+"秒";
				model.setWorkTask(newTime);
//				if(model.getProcessLimit()!=null&&!"".equals(model.getProcessLimit())){
//					long processLimit = Long.parseLong(model.getProcessLimit());
//					if(processLimit>hours){//没有超出时限
//						model.setChangeColor(true);
//					}else{//超出时限
//						model.setChangeColor(false);
//					}
//				}
			}
			if(map.get("assignee")!=null && !"".equals(map.get("assignee"))){
				model.setExecutor(map.get("assignee").toString());
			}
			//流程类型
			String process_type = "";
			if(map.get("process_type")!=null && !"".equals(map.get("process_type"))){
				process_type = map.get("process_type").toString();
				model.setProcessType(map.get("process_type").toString());
			}
			//状态存储--start
			String state = "";
			if("maleGuest".equals(process_type)){
				if(map.get("task_def_key")!=null && !"".equals(map.get("task_def_key"))){
					if("newMaleGuest".equals(map.get("task_def_key"))){
						state = "派发工单";
					}else if("auditor".equals(map.get("task_def_key"))){
						state="故障处理";
					}else if("acheck".equals(map.get("task_def_key"))){
						state="区县维护中心一次核验";
					}else if("twoSpotChecks".equals(map.get("task_def_key"))){
						state="区县维护中心二次抽查";
					}else if("auditReport".equals(map.get("task_def_key"))){
						state="区县维护中心综合报表审核";
					}else if("archive".equals(map.get("task_def_key"))){
						state="已归档";
					}
				}
				if(map.get("repeat_state")!=null && !"".equals(map.get("repeat_state"))&&"1".equals(map.get("repeat_state").toString().trim())){
					state = "(重修)"+state;
				}
			}else if("transferOffice".equals(process_type)){
				if(map.get("task_def_key")!=null && !"".equals(map.get("task_def_key"))){
					if("newTask".equals(map.get("task_def_key"))){
						state = "派发工单";
					}else if("transferTask".equals(map.get("task_def_key"))){
						state="传输局";
					}else if("epibolyTask".equals(map.get("task_def_key"))){
						state="外包公司";
					}else if("transferHandle".equals(map.get("task_def_key"))){
						state="施工队";
					}else if("acheck".equals(map.get("task_def_key"))){
						state="一次核验";
					}else if("twoSpotChecks".equals(map.get("task_def_key"))){
						state="二次抽查";
					}else if("auditReport".equals(map.get("task_def_key"))){
						state="周期报表";
					}else if("archive".equals(map.get("task_def_key"))){
						state="已归档";
					}
				}
			}
			model.setStatusName(state);
			
			if(map.get("approve_flag")!=null && !"".equals(map.get("approve_flag"))){
				model.setApproveFlag(map.get("approve_flag").toString());
			}
			//审批操作中文值
			if(map.get("approve_flag_name")!=null && !"".equals(map.get("approve_flag_name"))){
				model.setApproveFlagName(map.get("approve_flag_name").toString());
			}
			//单条工单处理还是没处理标识 N：未处理；Y：已处理
			if(map.get("handle_flag")!=null && !"".equals(map.get("handle_flag"))){
				model.setHandleFlag(map.get("handle_flag").toString());
			}
			//是否超时
			if(map.get("timeout_flag")!=null && !"".equals(map.get("timeout_flag"))){
				if("Y".equals(map.get("timeout_flag").toString())){
					model.setChangeColor(true);
				}else if("N".equals(map.get("timeout_flag").toString())){
					model.setChangeColor(false);
				}
			}
			//工单子类型
			if(map.get("sub_type")!=null && !"".equals(map.get("sub_type"))){
				model.setSubType(map.get("sub_type").toString());
			}
			//地市
			if(map.get("city")!=null && !"".equals(map.get("city"))){
				model.setCity(map.get("city").toString());
			}
			//局站名称
			if(map.get("station_name")!=null && !"".equals(map.get("station_name"))){
				model.setStationName(map.get("station_name").toString());
			}
			//故障处理回复时间
			if(map.get("last_reply_time")!=null && !"".equals(map.get("last_reply_time"))){
				if (map.get("last_reply_time") != null) {
					if (!map.get("last_reply_time").toString().equals("")) {
						try {
							model.setLastReplyTime(format.parse(map.get("last_reply_time")
									.toString()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}

				}
			}
			
			//流程类型
			if(map.get("pathOne")!=null && !"".equals(map.get("pathOne"))){
				model.setPathOne(map.get("pathOne").toString());
			}
			//流程类型
			if(map.get("pathTwo")!=null && !"".equals(map.get("pathTwo"))){
				model.setPathTwo(map.get("pathTwo").toString());
			}
			//最新主场景
			if(map.get("recent_main_scenes_name")!=null && !"".equals(map.get("recent_main_scenes_name"))){
				model.setRecentMainScenesName(map.get("recent_main_scenes_name").toString());
			}
			//最新子场景
			if(map.get("recent_copy_scenes_name")!=null && !"".equals(map.get("recent_copy_scenes_name"))){
				model.setRecentCopyScenesName(map.get("recent_copy_scenes_name").toString());
			}
			//流程类型中文值
			if(map.get("process_type_name")!=null && !"".equals(map.get("process_type_name"))){
				model.setProcessTypeName(map.get("process_type_name").toString());
			}
			//--end
			
			r.add(model);

		}

		return r;
	}
	//取周期领用表的报表编号
//	public List<String> getReportNumbers(String userId,MaleGuestSelectAttribute selectAttribute){
//		ArrayList list = new ArrayList();
//		String sql ="select distinct r.report_number" +
//					"  from pnr_cycle_collar_report r" + 
//					" where r.county = '281101'" + 
//					"   and r.home_date >= to_date('2016-03-22', 'yyyy-mm-dd')" + 
//					"   and r.home_date <= to_date('2016-03-24', 'yyyy-mm-dd')";
//		
//		//区县
//		if (selectAttribute.getCounty() != null && !selectAttribute.getCounty().equals("")) {
//			sql += " and r.county = ?" ;
//			list.add(selectAttribute.getCounty().trim());
//		}
//		//开始时间
//		if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
//			sql += " and r.home_date >= to_date(?,'yyyy-mm-dd')";
//			list.add(selectAttribute.getBeginTime());
//		}
//		//结束时间
//		if (selectAttribute.getEndTime() != null && !selectAttribute.getEndTime().equals("")) {
//			sql +=  " and r.home_date <= to_date(?, 'yyyy-mm-dd')";
//			list.add(selectAttribute.getEndTime());
//		}
//		Object[] values = list.toArray();
//		
//		System.out.println("------取周期领用表的报表编号sql="+sql);
//		
//		List<String> r = new ArrayList<String>();
//		List<Map> list1 = this.getJdbcTemplate().queryForList(sql, values);
//		for (Map map : list1) {
//			if(map.get("report_number")!=null && !"".equals(map.get("report_number"))){
//				r.add(map.get("report_number").toString());
//			}
//		}
//
//		return r;
//	}
//	
//	//生成周期领用表，报表编号+开始日期+结束日期+所属日期
//	public List<CycleCollarReportModel> getCycleCollarReportMsg(String reportNumber,String userId,MaleGuestSelectAttribute selectAttribute){
//		ArrayList list = new ArrayList();
//		String sql = "select distinct r.start_date, r.end_date, r.home_date" +
//					 "  from pnr_cycle_collar_report r" + 
//					 " where r.report_number = ?";
//		
//		list.add(reportNumber);
//		Object[] values = list.toArray();
//		
//		System.out.println("------报表编号+开始日期+结束日期+所属日期sql="+sql);
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		List<CycleCollarReportModel> r = new ArrayList<CycleCollarReportModel>();
//		List<Map> list1 = this.getJdbcTemplate().queryForList(sql, values);
//		for (Map map : list1) {
//			CycleCollarReportModel model=new CycleCollarReportModel();
//			//开始时间
//			if(map.get("start_date")!=null && !"".equals(map.get("start_date").toString())){
//				try {
//					model.setStartDate(format.parse(map.get("start_date").toString()));
//				} catch (ParseException e) {
//					e.printStackTrace();
//				}
//			}
//			//结束时间
//			if(map.get("end_date")!=null && !"".equals(map.get("end_date").toString())){
//				try {
//					model.setEndDate(format.parse(map.get("end_date").toString()));
//				} catch (ParseException e) {
//					e.printStackTrace();
//				}
//			}
//			//所属时间
//			if(map.get("home_date")!=null && !"".equals(map.get("home_date").toString())){
//				try {
//					model.setHomeDate(format.parse(map.get("home_date").toString()));
//				} catch (ParseException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//
//		return r;
//	}
	
	//根据开始日期和结束日期，查询这个开始日期和这个结束日期已经生成的周期领用表 条数
	public int getCycleCollarReportCountByStartDateAndEndDate(String userId,MaleGuestSelectAttribute selectAttribute){
		ArrayList list = new ArrayList();
		String sql = "";
		String selectSql =	"select count(r.process_instance_id) as total" +
							"  from pnr_cycle_collar_report      r," + 
							"       pnr_act_transfer_office_main m," + 
							"       taw_system_user              u," + 
							"       taw_system_dept              d" + 
							" where r.process_instance_id = m.process_instance_id" + 
							"   and m.assignee = u.userid(+)" + 
							"   and u.deptid = d.deptid(+)" ;

		//区县
		if (selectAttribute.getCounty() != null && !selectAttribute.getCounty().equals("")) {
			selectSql += " and r.county = ?" ;
			list.add(selectAttribute.getCounty().trim());
		}
		//开始时间
		if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
			selectSql += " and trunc(r.start_date) = to_date(?, 'yyyy-mm-dd')";
			list.add(selectAttribute.getBeginTime());
		}
		//结束时间
		if (selectAttribute.getEndTime() != null && !selectAttribute.getEndTime().equals("")) {
			selectSql +=  " and trunc(r.end_date) = to_date(?, 'yyyy-mm-dd')";
			list.add(selectAttribute.getEndTime());
		}

		sql = selectSql;

		System.out.println("------根据开始日期和结束日期，查询这个开始日期和这个结束日期已经生成的周期领用表 条数sql="+sql);
		
		Object[] args = list.toArray();
		
		int total = this.getJdbcTemplate().queryForInt(sql, args);
		return total;
	}
	//抢修工单审核 二次抽检统计数
	@SuppressWarnings("unchecked")
	public int getSecondInspectionCount(String userId,MaleGuestSelectAttribute selectAttribute){
		ArrayList list = new ArrayList();
		String sql = "";
		String selectSql =  "select count(r.process_instance_id) as total" +
							"  from pnr_second_inspection_report r," + 
							"       pnr_act_transfer_office_main m," + 
							"       taw_system_user              u," + 
							"       taw_system_dept              d" + 
							" where r.process_instance_id = m.process_instance_id(+)" + 
							"   and m.assignee = u.userid(+)" + 
							"   and u.deptid = d.deptid(+)";
		String whereSql = "";
		//区县
		if (selectAttribute.getCounty() != null && !selectAttribute.getCounty().equals("")) {
			whereSql += " and r.county =?";
			list.add(selectAttribute.getCounty().trim());
		}
		//日期
		if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
			whereSql += " and r.report_date = to_date(?, 'yyyy-mm-dd')";
			list.add(selectAttribute.getBeginTime());
		}
		sql = selectSql + whereSql;

		System.out.println("------二次抽检统计数sql="+sql);
		
		Object[] args = list.toArray();
		
		int total = this.getJdbcTemplate().queryForInt(sql, args);
		return total;
	}
	
	//根据开始日期和结束日期，查询这个开始日期和这个结束日期已经生成的周期领用表 集合
	public List<TaskModel> getCycleCollarReportListByStartDateAndEndDate(String userId,MaleGuestSelectAttribute selectAttribute,int firstResult,int endResult,int pageSize){
		ArrayList list1 = new ArrayList();
		String sql = " select temp2.* from (select temp1.*, rownum num from (";
		String selectSql =	"select m.task_id             as TaskId," +
							"       u.username," + 
							"       d.deptname," + 
							"       m.initiator           as Initiator," + 
							"       m.one_initiator       as OneInitiator," + 
							"       m.process_instance_id as ProcessInstanceId," + 
							"       m.send_time           as SendTime," + 
							"       m.theme               as Theme," + 
							"       m.task_assignee," + 
							"       m.state               as State," + 
							"       u.deptid              as DeptId," + 
							"       m.install_address     as Install_address," + 
							"       m.barrier_number      as Barrier_number," + 
							"       m.create_time," + 
							"       m.city," + 
							"       m.country," + 
							"       m.site_cd," + 
							"       m.fault_source," + 
							"       nvl(m.male_guest_state,'0') as male_guest_state," + 
							"       decode(nvl(m.male_guest_state, '0'),'1','是','否') as male_guest_state_name," + 
							"       m.connect_person," + 
							"       m.process_limit," + 
							"       m.assignee," + 
							"       m.task_def_key," + 
							"       m.repeat_state," + 
							"       m.sub_type," + 
							"       m.station_name," + 
							"       m.Last_Reply_Time," + 
							"       r.county," +
							"       r.process_type," + 
							" 		decode(r.process_type,'maleGuest','pnrTransferMaleGuest','transferOffice','pnrTransferOffice','未知') as pathOne," + 
							"       decode(r.process_type,'maleGuest','pnrTransferOfficeMaleGuest','transferOffice','pnrTransferOffice','未知') as pathTwo," + 
							"       nvl(m.recent_main_scenes_name,'无') as recent_main_scenes_name," + 
							"       nvl(m.recent_copy_scenes_name,'无') as recent_copy_scenes_name," + 
							"		decode(r.process_type," +
							"              'maleGuest'," + 
							"              '公客'," + 
							"              'transferOffice'," + 
							"              '抢修'," + 
							"              '未知') as process_type_name"+
							"  from pnr_cycle_collar_report      r," + 
							"       pnr_act_transfer_office_main m," + 
							"       taw_system_user              u," + 
							"       taw_system_dept              d" + 
							" where r.process_instance_id = m.process_instance_id" + 
							"   and m.assignee = u.userid(+)" + 
							"   and u.deptid = d.deptid(+)";
		
		//区县
		if (selectAttribute.getCounty() != null && !selectAttribute.getCounty().equals("")) {
			selectSql += " and r.county = ?" ;
			list1.add(selectAttribute.getCounty().trim());
		}
		//开始时间
		if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
			selectSql += " and trunc(r.start_date) = to_date(?, 'yyyy-mm-dd')";
			list1.add(selectAttribute.getBeginTime());
		}
		//结束时间
		if (selectAttribute.getEndTime() != null && !selectAttribute.getEndTime().equals("")) {
			selectSql +=  " and trunc(r.end_date) = to_date(?, 'yyyy-mm-dd')";
			list1.add(selectAttribute.getEndTime());
		}
	
		sql += selectSql + " order by to_number(m.process_instance_id) desc) temp1 where rownum <=?) temp2 where temp2.num > ?";
		
		list1.add(endResult * pageSize);
		
		list1.add(firstResult * pageSize);
		Object[] values = list1.toArray();
		
		System.out.println("------根据开始日期和结束日期，查询这个开始日期和这个结束日期已经生成的周期领用表 集合sql="+sql);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql, values);
		for (Map map : list) {
			TaskModel model = new TaskModel();
			if(map.get("TaskId")!=null && !"".equals(map.get("TaskId"))){
				
				model.setTaskId(map.get("TaskId").toString());
			}else{
				model.setTaskId("");
			}
			if(map.get("Initiator")!=null && !"".equals(map.get("Initiator"))){
				model.setInitiator(map.get("Initiator").toString());
			}else{
				model.setInitiator("");
			}
			
			//临时做为空判断，正常OneInitiator的值不可以为空
			if (map.get("OneInitiator") != null){
				model.setOneInitiator(map.get("OneInitiator").toString());
			}else{
				model.setOneInitiator("");
			}		
			model.setProcessInstanceId(map.get("ProcessInstanceId").toString());
			if (map.get("SendTime") != null) {
				if (!map.get("SendTime").toString().equals("")) {
					try {
						model.setSendTime(format.parse(map.get("SendTime")
								.toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

			}
			if(map.get("Theme")!=null && !"".equals(map.get("Theme"))){
				model.setTheme(map.get("Theme").toString());
			}else{
				model.setTheme("");
			}
			if(map.get("DeptId")!=null && !"".equals(map.get("DeptId"))){
				model.setDeptId(map.get("DeptId").toString());
			}else{
				model.setDeptId("");
			}
			if(map.get("State")!=null && !"".equals(map.get("State"))){
				model.setState(Integer.parseInt(map.get("State").toString()));
			}else{
				model.setState(0);
			}
			if(map.get("Install_address")!=null && !"".equals(map.get("Install_address"))){
				model.setInstallAddress(map.get("Install_address").toString());
			}else{
				model.setInstallAddress("");
			}
			if(map.get("Barrier_number")!=null && !"".equals(map.get("Barrier_number"))){
				model.setBarrierNumber(map.get("Barrier_number").toString());
			}else{
				model.setBarrierNumber("");
			}
			if(map.get("create_time")!=null && !"".equals(map.get("create_time"))){
				if (map.get("create_time") != null) {
					if (!map.get("create_time").toString().equals("")) {
						try {
							model.setCreateTime(format.parse(map.get("create_time")
									.toString()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}

				}
			}
			//地市
			if(map.get("city")!=null && !"".equals(map.get("city").toString())){
				model.setCity(map.get("city").toString());
			}
			//区县
			if(map.get("country")!=null && !"".equals(map.get("country").toString())){
				model.setCountry(map.get("country").toString());
			}
			//站址码
			if(map.get("site_cd")!=null && !"".equals(map.get("site_cd").toString())){
				model.setSiteCd(map.get("site_cd").toString());
			}
			//故障原因
			if(map.get("fault_source")!=null && !"".equals(map.get("fault_source").toString())){
				model.setFaultSource(map.get("fault_source").toString());
			}
			//是否归集 
			if(map.get("male_guest_state")!=null &&!"".equals(map.get("male_guest_state"))){
				model.setMaleGuestState(map.get("male_guest_state").toString());
			}
			//是否归集 中文值
			if(map.get("male_guest_state_name")!=null &&!"".equals(map.get("male_guest_state_name"))){
				model.setMaleGuestStateName(map.get("male_guest_state_name").toString());
			}
			if(map.get("connect_person")!=null &&!"".equals(map.get("connect_person"))){
				model.setConnectPerson(map.get("connect_person").toString());
			}
			if(map.get("process_limit")!=null &&!"".equals(map.get("process_limit"))){
				model.setProcessLimit(map.get("process_limit").toString());
			}
			//工单历时 = 当前时间-故障发生时间：当工单历时大于处理时限，改变颜色
			if(map.get("create_time")!=null && !"".equals(map.get("create_time"))&& map.get("SendTime") != null){
				//两时间做差
				Date startTime = new Date();
				Date endTime = model.getCreateTime();
				long sortTime = startTime.getTime()-endTime.getTime();
				long hours = sortTime / (1000 * 60 * 60 );
				long minutes = sortTime/(1000*60)-hours*60;
				long second = sortTime/1000 - hours*60*60 - minutes*60;
				String newTime = hours+"小时"+minutes+"分钟"+second+"秒";
				model.setWorkTask(newTime);
//				if(model.getProcessLimit()!=null&&!"".equals(model.getProcessLimit())){
//					long processLimit = Long.parseLong(model.getProcessLimit());
//					if(processLimit>hours){//没有超出时限
//						model.setChangeColor(true);
//					}else{//超出时限
//						model.setChangeColor(false);
//					}
//				}
			}
			if(map.get("assignee")!=null && !"".equals(map.get("assignee"))){
				model.setExecutor(map.get("assignee").toString());
			}
			//流程类型
			String process_type = "";
			if(map.get("process_type")!=null && !"".equals(map.get("process_type"))){
				process_type = map.get("process_type").toString();
				model.setProcessType(map.get("process_type").toString());
			}
			//状态存储--start
			String state = "";
			if("maleGuest".equals(process_type)){
				if(map.get("task_def_key")!=null && !"".equals(map.get("task_def_key"))){
					if("newMaleGuest".equals(map.get("task_def_key"))){
						state = "派发工单";
					}else if("auditor".equals(map.get("task_def_key"))){
						state="故障处理";
					}else if("acheck".equals(map.get("task_def_key"))){
						state="区县维护中心一次核验";
					}else if("twoSpotChecks".equals(map.get("task_def_key"))){
						state="区县维护中心二次抽查";
					}else if("auditReport".equals(map.get("task_def_key"))){
						state="区县维护中心综合报表审核";
					}else if("archive".equals(map.get("task_def_key"))){
						state="已归档";
					}
				}
				if(map.get("repeat_state")!=null && !"".equals(map.get("repeat_state"))&&"1".equals(map.get("repeat_state").toString().trim())){
					state = "(重修)"+state;
				}
			}else if("transferOffice".equals(process_type)){
				if(map.get("task_def_key")!=null && !"".equals(map.get("task_def_key"))){
					if("newTask".equals(map.get("task_def_key"))){
						state = "派发工单";
					}else if("transferTask".equals(map.get("task_def_key"))){
						state="传输局";
					}else if("epibolyTask".equals(map.get("task_def_key"))){
						state="外包公司";
					}else if("transferHandle".equals(map.get("task_def_key"))){
						state="施工队";
					}else if("acheck".equals(map.get("task_def_key"))){
						state="一次核验";
					}else if("twoSpotChecks".equals(map.get("task_def_key"))){
						state="二次抽查";
					}else if("auditReport".equals(map.get("task_def_key"))){
						state="周期报表";
					}else if("archive".equals(map.get("task_def_key"))){
						state="已归档";
					}
				}
			}
			model.setStatusName(state);

			//工单子类型
			if(map.get("sub_type")!=null && !"".equals(map.get("sub_type"))){
				model.setSubType(map.get("sub_type").toString());
			}
			//地市
			if(map.get("city")!=null && !"".equals(map.get("city"))){
				model.setCity(map.get("city").toString());
			}
			//局站名称
			if(map.get("station_name")!=null && !"".equals(map.get("station_name"))){
				model.setStationName(map.get("station_name").toString());
			}
			//故障处理回复时间
			if(map.get("last_reply_time")!=null && !"".equals(map.get("last_reply_time"))){
				if (map.get("last_reply_time") != null) {
					if (!map.get("last_reply_time").toString().equals("")) {
						try {
							model.setLastReplyTime(format.parse(map.get("last_reply_time")
									.toString()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}

				}
			}
			
			//区县
			if(map.get("county")!=null && !"".equals(map.get("county"))){
				model.setCountry(map.get("county").toString());
			}

			//流程类型
			if(map.get("pathOne")!=null && !"".equals(map.get("pathOne"))){
				model.setPathOne(map.get("pathOne").toString());
			}
			//流程类型
			if(map.get("pathTwo")!=null && !"".equals(map.get("pathTwo"))){
				model.setPathTwo(map.get("pathTwo").toString());
			}
			
			//最新主场景
			if(map.get("recent_main_scenes_name")!=null && !"".equals(map.get("recent_main_scenes_name"))){
				model.setRecentMainScenesName(map.get("recent_main_scenes_name").toString());
			}
			//最新子场景
			if(map.get("recent_copy_scenes_name")!=null && !"".equals(map.get("recent_copy_scenes_name"))){
				model.setRecentCopyScenesName(map.get("recent_copy_scenes_name").toString());
			}
			//流程类型中文值
			if(map.get("process_type_name")!=null && !"".equals(map.get("process_type_name"))){
				model.setProcessTypeName(map.get("process_type_name").toString());
			}
			//--end
			
			r.add(model);

		}

		return r;
	}

	//根据开始日期和结束日期，在这个时间范围内已经生成的周期领用表（用开始日期和结束日期查询不到数据的时候）
	public List<CycleCollarReportModel> getCycleCollarReportListByHomeDate(String userId,MaleGuestSelectAttribute selectAttribute){
		ArrayList list = new ArrayList();
		String sql ="select distinct r.report_number, r.start_date, r.end_date" +
					"  from pnr_cycle_collar_report r" + 
					" where 1=1 " ;
//					" r.county = '281101'" + 
//					"   and r.home_date >= to_date('2016-03-22', 'yyyy-mm-dd')" + 
//					"   and r.home_date <= to_date('2016-03-23', 'yyyy-mm-dd')" + 
//					" order by r.start_date, r.end_date";
		
		//区县
		if (selectAttribute.getCounty() != null && !selectAttribute.getCounty().equals("")) {
			sql += " and r.county = ?" ;
			list.add(selectAttribute.getCounty().trim());
		}
		//开始时间
		if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
			sql += "  and r.home_date >= to_date(?,'yyyy-mm-dd')";
			list.add(selectAttribute.getBeginTime());
		}
		//结束时间
		if (selectAttribute.getEndTime() != null && !selectAttribute.getEndTime().equals("")) {
			sql +=  " and r.home_date <= to_date(?, 'yyyy-mm-dd')";
			list.add(selectAttribute.getEndTime());
		}
		
		sql +=" order by r.start_date, r.end_date";
		
		Object[] values = list.toArray();
		
		System.out.println("------开始日期和结束日期查询不到数据的时候sql="+sql);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		List<CycleCollarReportModel> r = new ArrayList<CycleCollarReportModel>();
		List<Map> list1 = this.getJdbcTemplate().queryForList(sql, values);
		for (Map map : list1) {
			CycleCollarReportModel model=new CycleCollarReportModel();
			//报表编号
			if(map.get("report_number")!=null && !"".equals(map.get("report_number").toString())){
				model.setReportNumber(map.get("report_number").toString());
			}
			//开始日期
			if(map.get("start_date")!=null && !"".equals(map.get("start_date").toString())){
				try {
					model.setStartDate(format.parse(map.get("start_date").toString()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			//结束日期
			if(map.get("end_date")!=null && !"".equals(map.get("end_date").toString())){
				try {
					model.setEndDate(format.parse(map.get("end_date").toString()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			r.add(model);
		}
		return r;
	}
	
	//周期领用表，获取报表提交人
	public String getSubmitUserIdOfCycleCollarReport(String userId,MaleGuestSelectAttribute selectAttribute){
		String submitUserId="";
		ArrayList list1 = new ArrayList();
	    String sql ="select r.submit_user_id" +
					"  from pnr_cycle_collar_report r" + 
					" where r.submit_user_id is not null" ;
		
		//区县
		if (selectAttribute.getCounty() != null && !selectAttribute.getCounty().equals("")) {
			sql += " and r.county = ?" ;
			list1.add(selectAttribute.getCounty().trim());
		}
		//开始时间
		if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
			sql += " and trunc(r.start_date) = to_date(?, 'yyyy-mm-dd')";
			list1.add(selectAttribute.getBeginTime());
		}
		//结束时间
		if (selectAttribute.getEndTime() != null && !selectAttribute.getEndTime().equals("")) {
			sql +=  " and trunc(r.end_date) = to_date(?, 'yyyy-mm-dd')";
			list1.add(selectAttribute.getEndTime());
		}
	
		Object[] values = list1.toArray();
		
		System.out.println("------周期领用表，获取报表提交人sql="+sql);
		
		List<Map> list = this.getJdbcTemplate().queryForList(sql,values);
		if (list != null && !list.isEmpty() && list.size() > 0) {
			if (list.get(0).get("submit_user_id") != null) {
				submitUserId = list.get(0).get("submit_user_id").toString();
			}
		}
		return submitUserId;
	}
	//抢修工单审核 二次抽检列表
	@SuppressWarnings("unchecked")
	public List<TaskModel> getSecondInspectionList(String userId,MaleGuestSelectAttribute selectAttribute, int firstResult, int endResult,int pageSize){
		ArrayList list1 = new ArrayList();
		String sql = " select temp2.* from (select temp1.*, rownum num from (";
		 
		String selectSql =  "select m.task_id             as TaskId," +
							"       u.username," + 
							"       d.deptname," + 
							"       m.initiator           as Initiator," + 
							"       m.one_initiator       as OneInitiator," + 
							"       m.process_instance_id as ProcessInstanceId," + 
							"       m.send_time           as SendTime," + 
							"       m.theme               as Theme," + 
							"       m.task_assignee," + 
							"       m.state               as State," + 
							"       u.deptid              as DeptId," + 
							"       m.install_address     as Install_address," + 
							"       m.barrier_number      as Barrier_number," + 
							"       m.create_time," + 
							"       m.city," + 
							"       m.country," + 
							"       m.site_cd," + 
							"       m.fault_source," + 
							"       nvl(m.male_guest_state,'0') as male_guest_state," + 
							"       decode(nvl(m.male_guest_state, '0'),'1','是','否') as male_guest_state_name," + 
							"       m.connect_person," + 
							"       m.process_limit," + 
							"       m.assignee," + 
							"       m.task_def_key," + 
							"       m.repeat_state," + 
							"       r.approve_flag," + 
							"                       case" + 
							"                         when (sysdate -" + 
							"                              to_date(to_char(trunc(r.rise_time)," + 
							"                                               'yyyy-mm-dd hh24:mi:ss')," + 
							"                                       'yyyy-mm-dd hh24:mi:ss')) * 24 > 72 and" + 
							"                              r.approve_flag = '0' then" + 
							"                          '已超时，不能抽检'" + 
							"                         when (sysdate -" + 
							"                              to_date(to_char(trunc(r.rise_time)," + 
							"                                               'yyyy-mm-dd hh24:mi:ss')," + 
							"                                       'yyyy-mm-dd hh24:mi:ss')) * 24 <= 72 and" + 
							"                              r.approve_flag = '0' then" + 
							"                          '未审批'" + 
							"                         when r.approve_flag = '1' then" + 
							"                          '通过'" + 
							"                         when r.approve_flag = '2' then" + 
							"                          '驳回'" + 
							"                         else" + 
							"                          '未知'" + 
							"                       end as approve_flag_name," + 
							"                      case" +
							"                        when (sysdate -" + 
							"                             to_date(to_char(trunc(r.rise_time)," + 
							"                                              'yyyy-mm-dd hh24:mi:ss')," + 
							"                                      'yyyy-mm-dd hh24:mi:ss')) * 24 <= 72 and" + 
							"                             r.approve_flag = '0' then" + 
							"                         'N'" + 
							"                        else" + 
							"                         'Y'" + 
							"                      end as handle_flag,"+
							"       case when (sysdate - to_date(to_char(trunc(r.rise_time), 'yyyy-mm-dd hh24:mi:ss'),'yyyy-mm-dd hh24:mi:ss')) * 24 > 72 and r.approve_flag = '0' then 'Y' else 'N' end as timeout_flag,m.sub_type,m.station_name,m.Last_Reply_Time," +
							"       r.process_type," + 
							"       decode(r.process_type,'maleGuest','pnrTransferMaleGuest','transferOffice','pnrTransferOffice','未知') as pathOne," + 
							"       decode(r.process_type,'maleGuest','pnrTransferOfficeMaleGuest','transferOffice','pnrTransferOffice','未知') as pathTwo," + 
							"       nvl(m.recent_main_scenes_name,'无') as recent_main_scenes_name," + 
							"       nvl(m.recent_copy_scenes_name,'无') as recent_copy_scenes_name," + 
							"		decode(r.process_type," +
							"              'maleGuest'," + 
							"              '公客'," + 
							"              'transferOffice'," + 
							"              '抢修'," + 
							"              '未知') as process_type_name"+
							"  from pnr_second_inspection_report r," + 
							"       pnr_act_transfer_office_main m," + 
							"       taw_system_user              u," + 
							"       taw_system_dept              d" + 
							" where r.process_instance_id = m.process_instance_id(+)" + 
							"   and m.assignee = u.userid(+)" + 
							"   and u.deptid = d.deptid(+)" ;

		//拼接条件查询
		String whereSql = "";
		//区县
		if (selectAttribute.getCounty() != null && !selectAttribute.getCounty().equals("")) {
			whereSql += " and r.county =?";
			list1.add(selectAttribute.getCounty().trim());
		}
		//日期
		if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
			whereSql += " and r.report_date = to_date(?, 'yyyy-mm-dd')";
			list1.add(selectAttribute.getBeginTime());
		}
	
		sql += selectSql + whereSql
				+ " order by to_number(m.process_instance_id) desc) temp1 where rownum <=?) temp2 where temp2.num > ?";
		
		list1.add(endResult * pageSize);
		
		list1.add(firstResult * pageSize);
		Object[] values = list1.toArray();
		
		System.out.println("------二次抽检列表sql="+sql);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql, values);
		for (Map map : list) {
			TaskModel model = new TaskModel();
			if(map.get("TaskId")!=null && !"".equals(map.get("TaskId"))){
				
				model.setTaskId(map.get("TaskId").toString());
			}else{
				model.setTaskId("");
			}
			if(map.get("Initiator")!=null && !"".equals(map.get("Initiator"))){
				model.setInitiator(map.get("Initiator").toString());
			}else{
				model.setInitiator("");
			}
			
			//临时做为空判断，正常OneInitiator的值不可以为空
			if (map.get("OneInitiator") != null){
				model.setOneInitiator(map.get("OneInitiator").toString());
			}else{
				model.setOneInitiator("");
			}		
			model.setProcessInstanceId(map.get("ProcessInstanceId").toString());
			if (map.get("SendTime") != null) {
				if (!map.get("SendTime").toString().equals("")) {
					try {
						model.setSendTime(format.parse(map.get("SendTime")
								.toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

			}
			if(map.get("Theme")!=null && !"".equals(map.get("Theme"))){
				model.setTheme(map.get("Theme").toString());
			}else{
				model.setTheme("");
			}
			if(map.get("DeptId")!=null && !"".equals(map.get("DeptId"))){
				model.setDeptId(map.get("DeptId").toString());
			}else{
				model.setDeptId("");
			}
			if(map.get("State")!=null && !"".equals(map.get("State"))){
				model.setState(Integer.parseInt(map.get("State").toString()));
			}else{
				model.setState(0);
			}
			if(map.get("Install_address")!=null && !"".equals(map.get("Install_address"))){
				model.setInstallAddress(map.get("Install_address").toString());
			}else{
				model.setInstallAddress("");
			}
			if(map.get("Barrier_number")!=null && !"".equals(map.get("Barrier_number"))){
				model.setBarrierNumber(map.get("Barrier_number").toString());
			}else{
				model.setBarrierNumber("");
			}
			if(map.get("create_time")!=null && !"".equals(map.get("create_time"))){
				if (map.get("create_time") != null) {
					if (!map.get("create_time").toString().equals("")) {
						try {
							model.setCreateTime(format.parse(map.get("create_time")
									.toString()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}

				}
			}
			//地市
			if(map.get("city")!=null && !"".equals(map.get("city").toString())){
				model.setCity(map.get("city").toString());
			}
			//区县
			if(map.get("country")!=null && !"".equals(map.get("country").toString())){
				model.setCountry(map.get("country").toString());
			}
			//站址码
			if(map.get("site_cd")!=null && !"".equals(map.get("site_cd").toString())){
				model.setSiteCd(map.get("site_cd").toString());
			}
			//故障原因
			if(map.get("fault_source")!=null && !"".equals(map.get("fault_source").toString())){
				model.setFaultSource(map.get("fault_source").toString());
			}
			//是否归集
			if(map.get("male_guest_state")!=null &&!"".equals(map.get("male_guest_state"))){
				model.setMaleGuestState(map.get("male_guest_state").toString());
			}
			//是否归集中文值
			if(map.get("male_guest_state_name")!=null &&!"".equals(map.get("male_guest_state_name"))){
				model.setMaleGuestStateName(map.get("male_guest_state_name").toString());
			}
			if(map.get("connect_person")!=null &&!"".equals(map.get("connect_person"))){
				model.setConnectPerson(map.get("connect_person").toString());
			}
			if(map.get("process_limit")!=null &&!"".equals(map.get("process_limit"))){
				model.setProcessLimit(map.get("process_limit").toString());
			}
			//工单历时 = 当前时间-故障发生时间：当工单历时大于处理时限，改变颜色
			if(map.get("create_time")!=null && !"".equals(map.get("create_time"))&& map.get("SendTime") != null){
				//两时间做差
				Date startTime = new Date();
				Date endTime = model.getCreateTime();
				long sortTime = startTime.getTime()-endTime.getTime();
				long hours = sortTime / (1000 * 60 * 60 );
				long minutes = sortTime/(1000*60)-hours*60;
				long second = sortTime/1000 - hours*60*60 - minutes*60;
				String newTime = hours+"小时"+minutes+"分钟"+second+"秒";
				model.setWorkTask(newTime);
//				if(model.getProcessLimit()!=null&&!"".equals(model.getProcessLimit())){
//					long processLimit = Long.parseLong(model.getProcessLimit());
//					if(processLimit>hours){//没有超出时限
//						model.setChangeColor(true);
//					}else{//超出时限
//						model.setChangeColor(false);
//					}
//				}
			}
			if(map.get("assignee")!=null && !"".equals(map.get("assignee"))){
				model.setExecutor(map.get("assignee").toString());
			}
			
			//流程类型
			String process_type = "";
			if(map.get("process_type")!=null && !"".equals(map.get("process_type"))){
				process_type = map.get("process_type").toString();
				model.setProcessType(map.get("process_type").toString());
			}
			//状态存储--start
			String state = "";
			if("maleGuest".equals(process_type)){
				if(map.get("task_def_key")!=null && !"".equals(map.get("task_def_key"))){
					if("newMaleGuest".equals(map.get("task_def_key"))){
						state = "派发工单";
					}else if("auditor".equals(map.get("task_def_key"))){
						state="故障处理";
					}else if("acheck".equals(map.get("task_def_key"))){
						state="区县维护中心一次核验";
					}else if("twoSpotChecks".equals(map.get("task_def_key"))){
						state="区县维护中心二次抽查";
					}else if("auditReport".equals(map.get("task_def_key"))){
						state="区县维护中心综合报表审核";
					}else if("archive".equals(map.get("task_def_key"))){
						state="已归档";
					}
				}
				if(map.get("repeat_state")!=null && !"".equals(map.get("repeat_state"))&&"1".equals(map.get("repeat_state").toString().trim())){
					state = "(重修)"+state;
				}
			}else if("transferOffice".equals(process_type)){
				if(map.get("task_def_key")!=null && !"".equals(map.get("task_def_key"))){
					if("newTask".equals(map.get("task_def_key"))){
						state = "派发工单";
					}else if("transferTask".equals(map.get("task_def_key"))){
						state="传输局";
					}else if("epibolyTask".equals(map.get("task_def_key"))){
						state="外包公司";
					}else if("transferHandle".equals(map.get("task_def_key"))){
						state="施工队";
					}else if("acheck".equals(map.get("task_def_key"))){
						state="一次核验";
					}else if("twoSpotChecks".equals(map.get("task_def_key"))){
						state="二次抽查";
					}else if("auditReport".equals(map.get("task_def_key"))){
						state="周期报表";
					}else if("archive".equals(map.get("task_def_key"))){
						state="已归档";
					}
				}
			}
			model.setStatusName(state);
			
			if(map.get("approve_flag")!=null && !"".equals(map.get("approve_flag"))){
				model.setApproveFlag(map.get("approve_flag").toString());
			}
			//审批标识中文值
			if(map.get("approve_flag_name")!=null && !"".equals(map.get("approve_flag_name"))){
				model.setApproveFlagName(map.get("approve_flag_name").toString());
			}
			//单条工单处理还是没处理标识 N：未处理；Y：已处理
			if(map.get("handle_flag")!=null && !"".equals(map.get("handle_flag"))){
				model.setHandleFlag(map.get("handle_flag").toString());
			}
			//是否超时
			if(map.get("timeout_flag")!=null && !"".equals(map.get("timeout_flag"))){
				if("Y".equals(map.get("timeout_flag").toString())){
					model.setChangeColor(true);
				}else if("N".equals(map.get("timeout_flag").toString())){
					model.setChangeColor(false);
				}
			}
			//工单子类型
			if(map.get("sub_type")!=null && !"".equals(map.get("sub_type"))){
				model.setSubType(map.get("sub_type").toString());
			}
			//地市
			if(map.get("city")!=null && !"".equals(map.get("city"))){
				model.setCity(map.get("city").toString());
			}
			//局站名称
			if(map.get("station_name")!=null && !"".equals(map.get("station_name"))){
				model.setStationName(map.get("station_name").toString());
			}
			//故障处理回复时间
			if(map.get("last_reply_time")!=null && !"".equals(map.get("last_reply_time"))){
				if (map.get("last_reply_time") != null) {
					if (!map.get("last_reply_time").toString().equals("")) {
						try {
							model.setLastReplyTime(format.parse(map.get("last_reply_time")
									.toString()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}

				}
			}
			
			//流程类型
			if(map.get("pathOne")!=null && !"".equals(map.get("pathOne"))){
				model.setPathOne(map.get("pathOne").toString());
			}
			//流程类型
			if(map.get("pathTwo")!=null && !"".equals(map.get("pathTwo"))){
				model.setPathTwo(map.get("pathTwo").toString());
			}
			//最新主场景
			if(map.get("recent_main_scenes_name")!=null && !"".equals(map.get("recent_main_scenes_name"))){
				model.setRecentMainScenesName(map.get("recent_main_scenes_name").toString());
			}
			//最新子场景
			if(map.get("recent_copy_scenes_name")!=null && !"".equals(map.get("recent_copy_scenes_name"))){
				model.setRecentCopyScenesName(map.get("recent_copy_scenes_name").toString());
			}
			
			//流程类型中文值
			if(map.get("process_type_name")!=null && !"".equals(map.get("process_type_name"))){
				model.setProcessTypeName(map.get("process_type_name").toString());
			}
			//--end
			
			r.add(model);

		}

		return r;
	}
	
	//获取周期领用表的部分字段的值（公用）
	public String getAttributeValueOfCycleCollarReport(String userId,MaleGuestSelectAttribute selectAttribute,String flag){
		String val="";
		ArrayList list1 = new ArrayList();
		String sql="select distinct ";
		
		if("user".equals(flag)){
			sql +="r.submit_user_id";
		}else if("num".equals(flag)){
			sql +="r.report_number";
		}else if("time".equals(flag)){
			sql +="r.report_create_date";
		}else if("phone".equals(flag)){
			sql +="r.approve_phone";
		}
		
		sql+=" as val" +
			"  from pnr_cycle_collar_report r" + 
			" where 1=1";
		
		//区县
		if (selectAttribute.getCounty() != null && !selectAttribute.getCounty().equals("")) {
			sql += " and r.county = ?" ;
			list1.add(selectAttribute.getCounty().trim());
		}
		//开始时间
		if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
			sql += " and trunc(r.start_date) = to_date(?, 'yyyy-mm-dd')";
			list1.add(selectAttribute.getBeginTime());
		}
		//结束时间
		if (selectAttribute.getEndTime() != null && !selectAttribute.getEndTime().equals("")) {
			sql +=  " and trunc(r.end_date) = to_date(?, 'yyyy-mm-dd')";
			list1.add(selectAttribute.getEndTime());
		}
		
		if("user".equals(flag)){
			sql +=  " and r.submit_user_id is not null";
		}
	
		Object[] values = list1.toArray();
		
		System.out.println("------获取周期领用表的部分字段的值（公用）sql="+sql);
		
		List<Map> list = this.getJdbcTemplate().queryForList(sql,values);
		if (list != null && !list.isEmpty() && list.size() > 0) {
			if (list.get(0).get("val") != null) {
				val = list.get(0).get("val").toString();
			}
		}
		return val;
	}
	
	//提交周期领用表
	public void submitCreateCycleCollarReport(final String userId,final String reportNumber,String fuJianVal,MaleGuestSelectAttribute selectAttribute,final String accessorieId,final String accessorieName){
		String sql ="update pnr_cycle_collar_report r" +
					"   set r.submit_user_id  = ?," + 
					"       r.submit_date     = ?," + 
					"       r.accessorie_id   = ?," + 
					"       r.accessorie_name = ?," + 
					"       r.approve_phone = ?" + 
					" where r.report_number = ?";
		
		final String approvalPhone = selectAttribute.getApprovalPhone();
		final java.util.Date submitDate=new java.util.Date();
		this.getJdbcTemplate().update(  
				                sql,   
				                 new PreparedStatementSetter(){  
				                       @Override  
				                      public void setValues(PreparedStatement ps) throws SQLException {  
				                          ps.setString(1, userId);  
				                          ps.setObject(2,new java.sql.Timestamp(submitDate.getTime()));  
				                          ps.setString(3, accessorieId);  
					                      ps.setString(4, accessorieName);  
					                      ps.setString(5, approvalPhone);  
					                      ps.setString(6, reportNumber);   
				                      }  
				                 }  
				         );  
		}
			
	//取周期领用表上传的附件信息
	public String getAttachmentsOfCycleCollarReport(String reportNumber){
		String val="";
		ArrayList list1 = new ArrayList();
		String sql= "select distinct r.accessorie_name as val from pnr_cycle_collar_report r where r.report_number = ?";
		list1.add(reportNumber);
		Object[] values = list1.toArray();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,values);
		if (list != null && !list.isEmpty() && list.size() > 0) {
			if (list.get(0).get("val") != null) {
				val = list.get(0).get("val").toString();
			}
		}
		return val;
	}
	
	//取二次核验已提交报表中的超时工单
	public List<String> getTimeOverListOfSecond(MaleGuestSelectAttribute selectAttribute){
		ArrayList list1 = new ArrayList();
		String sql= "select r.process_instance_id" +
					"  from pnr_second_inspection_report r" + 
					" where r.approve_flag = '0'" + 
					"   and r.submit_user_id is not null";
//					"   and r.county = '281101'" + 
//					"   and r.report_date = to_date('2016-3-22', 'yyyy-mm-dd')";
		//区县
		if (selectAttribute.getCounty() != null && !selectAttribute.getCounty().equals("")) {
			sql += " and r.county =?";
			list1.add(selectAttribute.getCounty().trim());
		}
		//日期
		if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
			sql += " and r.report_date = to_date(?, 'yyyy-mm-dd')";
			list1.add(selectAttribute.getBeginTime());
		}

		System.out.println("------取二次核验已提交报表中的超时工单sql="+sql);
		Object[] args = list1.toArray();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,args);
		List<String> r = new ArrayList<String>();
		for (Map map : list) {
			if(map.get("process_instance_id")!=null && !"".equals(map.get("process_instance_id"))){
				r.add(map.get("process_instance_id").toString());
			}
		}
		return r;
	}
	
	//获取一次核验，二次抽检的部分字段的值（公用）
	public String getAttributeValueOfFirstAndSecond(String userId,MaleGuestSelectAttribute selectAttribute,String flag){
		String reportType = selectAttribute.getReportType();
		String tableName="";
		if("firstVerify".equals(reportType)){
			tableName="pnr_first_verify_report";
		}else if("secondInspection".equals(reportType)){
			tableName="pnr_second_inspection_report";
		}
		
		//取哪个字段
		String field="";
		if("user".equals(flag)){
			field = "r.approve_sign";
		}else if("phone".equals(flag)){
			field = "r.approve_phone";
		}
		
		String val = "";//结果
		ArrayList list = new ArrayList();
		String sql = "";
		String selectSql =  "select "+ field +
							" as val from "+ tableName +" r" + 
							" where 1=1";
		String whereSql = "";
		
		if("secondInspection".equals(reportType)){
			sql+=" and approve_flag !='0'";
		}
		//区县
		if (selectAttribute.getCounty() != null && !selectAttribute.getCounty().equals("")) {
			whereSql += " and r.county =?";
			list.add(selectAttribute.getCounty().trim());
		}
		//日期
		if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
			whereSql += " and r.report_date = to_date(?, 'yyyy-mm-dd')";
			list.add(selectAttribute.getBeginTime());
		}
		
		if("secondInspection".equals(reportType)){
			whereSql += " and r.approve_sign is not null";
		}
		
		whereSql+=" and rownum = 1";
		sql = selectSql + whereSql;
		System.out.println("------获取一次核验，二次抽检的部分字段的值（公用）sql="+sql);
		Object[] args = list.toArray();
		List<Map> list1 = this.getJdbcTemplate().queryForList(sql,args);
		if (list1 != null && !list1.isEmpty() && list1.size() > 0) {
			if (list1.get(0).get("val") != null) {
				val = list1.get(0).get("val").toString();
			}
		}
		return val;
	}
	
	//施工照片补录 照片总数
	public int getMakeupPhotosCount(String userId,MaleGuestSelectAttribute selectAttribute){
		String themeinterface = "未知";
		if("maleGuest".equals(selectAttribute.getReportType())||"transferOffice".equals(selectAttribute.getReportType())){
			themeinterface = selectAttribute.getReportType();
		}
		
		String siteCd = selectAttribute.getSiteCd();
		ArrayList list = new ArrayList();
		String sql = "";
		String selectSql =	"select count(m.id) as total" +
							"  from pnr_act_transfer_office_main m, taw_system_user u, taw_system_dept d" + 
							" where m.assignee = u.userid(+)" + 
							"   and u.deptid = d.deptid(+)" + 
							"   and m.Themeinterface = ?" + 
							"   and m.makeup_photo_flag = '1'";
							if("transferOffice".equals(themeinterface)){
								selectSql += " and m.project_start_point = ?" ; //只能看到自己回的工单 进行补录照片
							}else{
								selectSql += " and m.task_assignee = ?" ; //只能看到自己回的工单 进行补录照片
							}
		list.add(themeinterface);
		list.add(userId);
		String whereSql = "";
		if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >=?";
			list.add(selectAttribute.getBeginTime());
		}
		if (selectAttribute.getEndTime() != null && !selectAttribute.getEndTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?";
			list.add(selectAttribute.getEndTime());
		}
		if (selectAttribute.getMaleGuestNumber() != null && !selectAttribute.getMaleGuestNumber().equals("")) {
			whereSql += " and m.process_instance_id =?";
			list.add(selectAttribute.getMaleGuestNumber().trim());
		}
//		if (selectAttribute.getTheme() != null && !selectAttribute.getTheme().equals("")) {
//			whereSql += " and instr(m.theme,?)>0 ";
//			list.add(selectAttribute.getTheme().trim());
//		}
//		if (selectAttribute.getStatus() != null && !selectAttribute.getStatus().equals("")) {
//			whereSql += " and m.task_def_key = ?";
//			list.add(selectAttribute.getStatus());
//		}
//		if (selectAttribute.getStatus() != null && !selectAttribute.getStatus().equals("")) {
//			whereSql += " and t.task_def_key_ = ?";
//			list.add(selectAttribute.getStatus());
//		}
//		if(selectAttribute.getWsNum()!=null && !"".equals(selectAttribute.getWsNum())){
//			whereSql+="  and m.barrier_number =?";
//			list.add(selectAttribute.getWsNum());
//		}
//		if(selectAttribute.getInstallAddress()!=null && !"".equals(selectAttribute.getInstallAddress().trim())){
//			whereSql += " and instr(m.install_address,?)>0 ";
//			list.add(selectAttribute.getInstallAddress());
//		}
//		if(selectAttribute.getDept()!=null && !"".equals(selectAttribute.getDept().trim())){
//			whereSql += " and instr(d.deptname,?)>0 ";
//			list.add(selectAttribute.getDept());
//		}
//		if(selectAttribute.getDept()!=null && !"".equals(selectAttribute.getDept().trim())){
//			whereSql += " and instr(u.deptname,?)>0 ";
//			list.add(selectAttribute.getDept());
//		}
//		if(selectAttribute.getPerson()!=null && !"".equals(selectAttribute.getPerson().trim())){
//			whereSql+="  and u.username =?";
//			list.add(selectAttribute.getPerson());
//		}
//		if(selectAttribute.getPerson()!=null && !"".equals(selectAttribute.getPerson().trim())){
//			whereSql+="  and t.username =?";
//			list.add(selectAttribute.getPerson());
//		}
		sql = selectSql + whereSql;

		System.out.println("施工照片补录 照片总数sql="+sql);
		
		Object[] args = list.toArray();
		
		int total = this.getJdbcTemplate().queryForInt(sql, args);
		return total;
	}
	
	//施工照片补录 照片集合
	public List<TaskModel> getMakeupPhotosList(String userId,MaleGuestSelectAttribute selectAttribute, int firstResult, int endResult,int pageSize){
		String themeinterface = "未知";
		if("maleGuest".equals(selectAttribute.getReportType())||"transferOffice".equals(selectAttribute.getReportType())){
			themeinterface = selectAttribute.getReportType();
		}
		String siteCd= selectAttribute.getSiteCd();
		ArrayList list1 = new ArrayList();
		String sql = " select temp2.* from (select temp1.*, rownum num from (";
		 
		String selectSql =	"select m.task_id             as TaskId," +
							"       u.username," + 
							"       d.deptname," + 
							"       m.initiator           as Initiator," + 
							"       m.one_initiator       as OneInitiator," + 
							"       m.process_instance_id as ProcessInstanceId," + 
							"       m.send_time           as SendTime," + 
							"       m.theme               as Theme," + 
							"       m.task_assignee," + 
							"       m.state               as State," + 
							"       u.deptid              as DeptId," + 
							"       m.install_address     as Install_address," + 
							"       m.barrier_number      as Barrier_number," + 
							"       m.create_time," + 
							"       m.city," + 
							"       m.connect_person," + 
							"       m.process_limit," + 
							"       m.assignee," + 
							"       m.task_def_key," + 
							"       m.repeat_state" + 
							"  from pnr_act_transfer_office_main m, taw_system_user u, taw_system_dept d" + 
							" where m.assignee = u.userid(+)" + 
							"   and u.deptid = d.deptid(+)" + 
							"   and m.Themeinterface = ?" + 
							"   and m.makeup_photo_flag = '1'";
							if("transferOffice".equals(themeinterface)){
								selectSql += " and m.project_start_point = ?" ; //只能看到自己回的工单 进行补录照片
							}else{
								selectSql += " and m.task_assignee = ?" ; //只能看到自己回的工单 进行补录照片
							}
		list1.add(themeinterface);				
		list1.add(userId);	
		
		System.out.println("------themeinterface="+themeinterface);
		System.out.println("------userId="+userId);
		
		//拼接条件查询
		String whereSql = "";
		if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >=?";
			list1.add(selectAttribute.getBeginTime());
		}
		if (selectAttribute.getEndTime() != null && !selectAttribute.getEndTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?";
			list1.add(selectAttribute.getEndTime());
		}
		if (selectAttribute.getMaleGuestNumber() != null && !selectAttribute.getMaleGuestNumber().equals("")) {
			whereSql += " and m.process_instance_id =?";
			list1.add(selectAttribute.getMaleGuestNumber().trim());
		}
//		if (selectAttribute.getTheme() != null && !selectAttribute.getTheme().equals("")) {
//			whereSql += " and instr(m.theme,?)>0 ";
//			list1.add(selectAttribute.getTheme().trim());
//		}
//		if (selectAttribute.getStatus() != null && !selectAttribute.getStatus().equals("")) {
//			whereSql += " and m.task_def_key = ?";
//			list1.add(selectAttribute.getStatus());
//		}
//		if (selectAttribute.getStatus() != null && !selectAttribute.getStatus().equals("")) {
//			whereSql += " and t.task_def_key_ = ?";
//			list1.add(selectAttribute.getStatus());
//		}
//		if(selectAttribute.getWsNum()!=null && !"".equals(selectAttribute.getWsNum())){
//			whereSql+="  and m.barrier_number =?";
//			list1.add(selectAttribute.getWsNum());
//		}
//		if(selectAttribute.getInstallAddress()!=null && !"".equals(selectAttribute.getInstallAddress().trim())){
//			whereSql += " and instr(m.install_address,?)>0 ";
//			list1.add(selectAttribute.getInstallAddress());
//		}
//		if(selectAttribute.getDept()!=null && !"".equals(selectAttribute.getDept().trim())){
//			whereSql += " and instr(d.deptname,?)>0 ";
//			list1.add(selectAttribute.getDept());
//		}
//		if(selectAttribute.getDept()!=null && !"".equals(selectAttribute.getDept().trim())){
//			whereSql += " and instr(u.deptname,?)>0 ";
//			list1.add(selectAttribute.getDept());
//		}
//		if(selectAttribute.getPerson()!=null && !"".equals(selectAttribute.getPerson().trim())){
//			whereSql+="  and u.username =?";
//			list1.add(selectAttribute.getPerson());
//		}
//		if(selectAttribute.getPerson()!=null && !"".equals(selectAttribute.getPerson().trim())){
//			whereSql+="  and t.username =?";
//			list1.add(selectAttribute.getPerson());
//		}
		sql += selectSql + whereSql
				+ " order by m.send_time desc) temp1 where rownum <=?) temp2 where temp2.num > ?";
//		sql += selectSql + whereSql
//		+ " order by t.create_time_ desc) temp1 where rownum <=?) temp2 where temp2.num > ?";
		
		list1.add(endResult * pageSize);
		
		list1.add(firstResult * pageSize);
		Object[] values = list1.toArray();
		
		System.out.println("施工照片补录 照片集合sql="+sql);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql, values);
		for (Map map : list) {
			TaskModel model = new TaskModel();
			if(map.get("TaskId")!=null && !"".equals(map.get("TaskId"))){
				
				model.setTaskId(map.get("TaskId").toString());
			}else{
				model.setTaskId("");
			}
			if(map.get("Initiator")!=null && !"".equals(map.get("Initiator"))){
				model.setInitiator(map.get("Initiator").toString());
			}else{
				model.setInitiator("");
			}
			
			//临时做为空判断，正常OneInitiator的值不可以为空
			if (map.get("OneInitiator") != null){
				model.setOneInitiator(map.get("OneInitiator").toString());
			}else{
				model.setOneInitiator("");
			}		
			model.setProcessInstanceId(map.get("ProcessInstanceId").toString());
			if (map.get("SendTime") != null) {
				if (!map.get("SendTime").toString().equals("")) {
					try {
						model.setSendTime(format.parse(map.get("SendTime")
								.toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

			}
			if(map.get("Theme")!=null && !"".equals(map.get("Theme"))){
				model.setTheme(map.get("Theme").toString());
			}else{
				model.setTheme("");
			}
			if(map.get("DeptId")!=null && !"".equals(map.get("DeptId"))){
				model.setDeptId(map.get("DeptId").toString());
			}else{
				model.setDeptId("");
			}
			if(map.get("State")!=null && !"".equals(map.get("State"))){
				model.setState(Integer.parseInt(map.get("State").toString()));
			}else{
				model.setState(0);
			}
			if(map.get("Install_address")!=null && !"".equals(map.get("Install_address"))){
				model.setInstallAddress(map.get("Install_address").toString());
			}else{
				model.setInstallAddress("");
			}
			if(map.get("Barrier_number")!=null && !"".equals(map.get("Barrier_number"))){
				model.setBarrierNumber(map.get("Barrier_number").toString());
			}else{
				model.setBarrierNumber("");
			}
			if(map.get("create_time")!=null && !"".equals(map.get("create_time"))){
				if (map.get("create_time") != null) {
					if (!map.get("create_time").toString().equals("")) {
						try {
							model.setCreateTime(format.parse(map.get("create_time")
									.toString()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}

				}
			}
			if(map.get("city")!=null && !"".equals(map.get("city"))){
				model.setCity(map.get("city").toString());
			}
			if(map.get("connect_person")!=null &&!"".equals(map.get("connect_person"))){
				model.setConnectPerson(map.get("connect_person").toString());
			}
			if(map.get("process_limit")!=null &&!"".equals(map.get("process_limit"))){
				model.setProcessLimit(map.get("process_limit").toString());
			}
			//工单历时 = 当前时间-故障发生时间：当工单历时大于处理时限，改变颜色
			if(map.get("create_time")!=null && !"".equals(map.get("create_time"))&& map.get("SendTime") != null){
				//两时间做差
				Date startTime = new Date();
				Date endTime = model.getCreateTime();
				long sortTime = startTime.getTime()-endTime.getTime();
				long hours = sortTime / (1000 * 60 * 60 );
				long minutes = sortTime/(1000*60)-hours*60;
				long second = sortTime/1000 - hours*60*60 - minutes*60;
				String newTime = hours+"小时"+minutes+"分钟"+second+"秒";
				model.setWorkTask(newTime);
				if(model.getProcessLimit()!=null&&!"".equals(model.getProcessLimit())){
					long processLimit = Long.parseLong(model.getProcessLimit());
					if(processLimit>hours){//没有超出时限
						model.setChangeColor(true);
					}else{//超出时限
						model.setChangeColor(false);
					}
				}
			}
			if(map.get("assignee")!=null && !"".equals(map.get("assignee"))){
				model.setExecutor(map.get("assignee").toString());
			}
			//状态存储--start
			String state = "";
			if("maleGuest".equals(themeinterface)){
				if(map.get("task_def_key")!=null && !"".equals(map.get("task_def_key"))){
					if("newMaleGuest".equals(map.get("task_def_key"))){
						state = "派发工单";
					}else if("auditor".equals(map.get("task_def_key"))){
						state="故障处理";
					}else if("acheck".equals(map.get("task_def_key"))){
						state="区县维护中心一次核验";
					}else if("twoSpotChecks".equals(map.get("task_def_key"))){
						state="区县维护中心二次抽查";
					}else if("auditReport".equals(map.get("task_def_key"))){
						state="区县维护中心综合报表审核";
					}
				}
				if(map.get("repeat_state")!=null && !"".equals(map.get("repeat_state"))&&"1".equals(map.get("repeat_state").toString().trim())){
					state = "(重修)"+state;
				}
			}else if("transferOffice".equals(themeinterface)){
				if(map.get("task_def_key")!=null && !"".equals(map.get("task_def_key"))){
					if("newTask".equals(map.get("task_def_key"))){
						state = "派发工单";
					}else if("transferTask".equals(map.get("task_def_key"))){
						state="传输局";
					}else if("epibolyTask".equals(map.get("task_def_key"))){
						state="外包公司";
					}else if("transferHandle".equals(map.get("task_def_key"))){
						state="施工队";
					}else if("acheck".equals(map.get("task_def_key"))){
						state="一次核验";
					}else if("twoSpotChecks".equals(map.get("task_def_key"))){
						state="二次抽查";
					}else if("auditReport".equals(map.get("task_def_key"))){
						state="周期报表";
					}
				}
			}
			model.setStatusName(state);
			//--end
			
			r.add(model);

		}

		return r;
	}
	
	/**
	 * 查询事情照片
	 * 
	 * @author WangJun 
	 * @param param	参数可以任意的封装
	 * @return
	 */
	public List<PnrAndroidPhotoFile> getPrecheckPhotoes(Map<String,String> param){
		String photoType = param.get("photoType");
		List listobj = new ArrayList();
		String sql = "select *" +
					 "  from (select *" + 
					 "          from pnr_android_photo p" + 
					 "         where p.user_id = ?" ;
					 if("myMaleGuestProcess".equals(photoType)){ //公客
						sql+= " and p.photo_type in('maleGuest','myMaleGuestProcess')";
					 }else if("myTransferProcess".equals(photoType)){ //抢修
						sql+= " and p.photo_type = 'myTransferProcess'";
					 }else{
						sql+= " and p.photo_type = '未知'";
					 }
					 sql+="  and nvl(p.state, 0) != '1'" + 
					 "   and to_date(p.createtime, 'yyyy-mm-dd hh24:mi:ss') < = to_date(?, 'yyyy-mm-dd hh24:mi:ss'))" + 
					 " where 1 = 1" ;
		listobj.add(param.get("userId"));	
		System.out.println("userId="+param.get("userId"));
		//listobj.add(param.get("photoType"));
		//System.out.println("photoType="+param.get("photoType"));
		listobj.add(param.get("replyTime"));	
		System.out.println("replyTime="+param.get("replyTime"));
		
		String whereSql="";
		//开始时间
		if (param.get("sheetAcceptLimit") != null && !"".equals(param.get("sheetAcceptLimit"))) {
			whereSql+=" and to_date(createtime, 'yyyy-mm-dd hh24:mi:ss') > = to_date(?, 'yyyy-mm-dd hh24:mi:ss')";
			listobj.add(param.get("sheetAcceptLimit"));
			System.out.println("sheetAcceptLimit="+param.get("sheetAcceptLimit"));
		}
		
		//结束时间
		if (param.get("sheetCompleteLimit") != null && !"".equals(param.get("sheetCompleteLimit"))) {
			whereSql+=" and to_date(createtime, 'yyyy-mm-dd hh24:mi:ss') < = to_date(?, 'yyyy-mm-dd hh24:mi:ss')";
			listobj.add(param.get("sheetCompleteLimit"));
			System.out.println("sheetCompleteLimit="+param.get("sheetCompleteLimit"));
		}
		
		//故障描述
		if (param.get("photoDescribe") != null && !"".equals(param.get("photoDescribe"))) {
			whereSql+=" and instr(faultdescription,?)>0 ";
			listobj.add(param.get("photoDescribe"));
			System.out.println("photoDescribe="+param.get("photoDescribe"));
		}
		
		//故障地址
		if (param.get("faultLocation") != null && !"".equals(param.get("faultLocation"))) {
			whereSql+=" and instr(faultLocation,?)>0 ";
			listobj.add(param.get("faultLocation"));
			System.out.println("faultLocation="+param.get("faultLocation"));
		}
		
		sql=sql+whereSql;
		
		System.out.println("------查询 补录照片 事情照片sql=" + sql);
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
	
	//获取照片的最小时间
	public String getMinDateOfPhoto(String photoesIds,Map<String,String> param){
		String val = "";
		String sql ="select p.createtime" +
					"  from pnr_android_photo p" + 
					" where p.id in ("+photoesIds+")" + 
					"   and p.createtime is not null" + 
					"   and rownum = 1" + 
					" order by p.createtime";

		System.out.println("------获取照片的最小时间sql="+sql);
		List<Map> list1 = this.getJdbcTemplate().queryForList(sql);
		if (list1 != null && !list1.isEmpty() && list1.size() > 0) {
			if (list1.get(0).get("createtime") != null) {
				val = list1.get(0).get("createtime").toString();
			}
		}
		return val;
	}
	
	//查看现场照片列表
	public List<PnrAndroidPhotoFile> getLiveCameraList(Map<String,String> param){

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
		
		System.out.println("------查看现场照片列表sql=" + sql);
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
	
	//查看归集工单子工单
	public List<TaskModel> getSubWorkOrderOfSingleImputationList(String userId,MaleGuestSelectAttribute selectAttribute, int firstResult, int endResult,int pageSize){
		String processInstanceId= selectAttribute.getProcessInstanceId();
		String siteCd= selectAttribute.getSiteCd();
		ArrayList list1 = new ArrayList();
//		String sql = " select temp2.* from (select temp1.*, rownum num from (";
		String sql = " ";
		 
		String selectSql =	"select m.task_id             as TaskId," +
							"       u.username," + 
							"       d.deptname," + 
							"       m.initiator           as Initiator," + 
							"       m.one_initiator       as OneInitiator," + 
							"       m.process_instance_id as ProcessInstanceId," + 
							"       m.send_time           as SendTime," + 
							"       m.theme               as Theme," + 
							"       m.task_assignee," + 
							"       m.state               as State," + 
							"       u.deptid              as DeptId," + 
							"       m.install_address     as Install_address," + 
							"       m.barrier_number      as Barrier_number," + 
							"       m.create_time," + 
							"       m.city," + 
							"       m.connect_person," + 
							"       m.process_limit," + 
							"       m.assignee," + 
							"       m.task_def_key," + 
							"       m.repeat_state," + 
							"       m.male_guest_state," + 
							"       m.station_name " + 
							"  from pnr_act_transfer_office_main m, taw_system_user u, taw_system_dept d" + 
							" where m.assignee = u.userid(+)" + 
							"   and u.deptid = d.deptid(+)" + 
							"   and m.Themeinterface = 'maleGuest'" + 
							"   and m.state != 8" + 
							"   and m.male_guest_state ='2'" + 
							"   and m.parent_process_instance_id =?" + 
							"   and m.site_cd = ?"; 
							// +"   and m.assignee = ? ";

//		String selectSql = " select t.id_ as TaskId,t.username,u.deptname,m.initiator as Initiator,m.one_initiator as OneInitiator,m.process_instance_id as ProcessInstanceId," +
//		" m.send_time as SendTime,m.theme as Theme,m.task_assignee,m.state as State,u.deptid as DeptId,m.install_address as Install_address," +
//		" m.barrier_number as Barrier_number,m.create_time,m.city,m.connect_person,m.process_limit, t.assignee_, t.task_def_key_, m.repeat_state from " +
//		" (select tu.username, RES.* from ACT_RU_TASK RES inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_  left join taw_system_user tu  on tu.userid = RES.ASSIGNEE_" +
//		" WHERE RES.ASSIGNEE_ = ? and D.KEY_ = 'myMaleGuestProcess' and RES.Suspension_State_ = 1) t,(select k.initiator,k.one_initiator,k.process_instance_id,k.send_time," +
//		" k.theme,k.install_address,k.barrier_number, decode(k.task_assignee,null,k.initiator,k.task_assignee) as task_assignee,k.state,k.Themeinterface," +
//		" k.city, k.create_time, k.connect_person,k.process_limit,k.repeat_state from pnr_act_transfer_office_main k) m,( select tsu.deptid,tsd.deptname,tsu.userid from taw_system_user tsu " +
//		" left join taw_system_dept tsd  on tsu.deptid = tsd.deptid )u " +
//		" where t.proc_inst_id_ = m.process_instance_id and m.task_assignee = u.userid(+) and m.Themeinterface = 'maleGuest' and m.state!=8";
		//list1.add(processInstanceId);
		list1.add(processInstanceId);
		list1.add(siteCd);
		//list1.add(userId);
		//拼接条件查询
		String whereSql = "";
		if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >=?";
			list1.add(selectAttribute.getBeginTime());
		}
		if (selectAttribute.getEndTime() != null && !selectAttribute.getEndTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?";
			list1.add(selectAttribute.getEndTime());
		}
		if (selectAttribute.getMaleGuestNumber() != null && !selectAttribute.getMaleGuestNumber().equals("")) {
			whereSql += " and m.process_instance_id =?";
			list1.add(selectAttribute.getMaleGuestNumber().trim());
		}
		if (selectAttribute.getTheme() != null && !selectAttribute.getTheme().equals("")) {
			whereSql += " and instr(m.theme,?)>0 ";
			list1.add(selectAttribute.getTheme().trim());
		}
		if (selectAttribute.getStatus() != null && !selectAttribute.getStatus().equals("")) {
			whereSql += " and m.task_def_key = ?";
			list1.add(selectAttribute.getStatus());
		}
//		if (selectAttribute.getStatus() != null && !selectAttribute.getStatus().equals("")) {
//			whereSql += " and t.task_def_key_ = ?";
//			list1.add(selectAttribute.getStatus());
//		}
		if(selectAttribute.getWsNum()!=null && !"".equals(selectAttribute.getWsNum())){
			whereSql+="  and m.barrier_number =?";
			list1.add(selectAttribute.getWsNum());
		}
		if(selectAttribute.getInstallAddress()!=null && !"".equals(selectAttribute.getInstallAddress().trim())){
			whereSql += " and instr(m.install_address,?)>0 ";
			list1.add(selectAttribute.getInstallAddress());
		}
		if(selectAttribute.getDept()!=null && !"".equals(selectAttribute.getDept().trim())){
			whereSql += " and instr(d.deptname,?)>0 ";
			list1.add(selectAttribute.getDept());
		}
//		if(selectAttribute.getDept()!=null && !"".equals(selectAttribute.getDept().trim())){
//			whereSql += " and instr(u.deptname,?)>0 ";
//			list1.add(selectAttribute.getDept());
//		}
		if(selectAttribute.getPerson()!=null && !"".equals(selectAttribute.getPerson().trim())){
			whereSql+="  and u.username =?";
			list1.add(selectAttribute.getPerson());
		}
//		if(selectAttribute.getPerson()!=null && !"".equals(selectAttribute.getPerson().trim())){
//			whereSql+="  and t.username =?";
//			list1.add(selectAttribute.getPerson());
//		}
		sql += selectSql + whereSql
				+ " order by m.male_guest_state";
//		sql += selectSql + whereSql
//		+ " order by t.create_time_ desc) temp1 where rownum <=?) temp2 where temp2.num > ?";
		
//		list1.add(endResult * pageSize);		
//		list1.add(firstResult * pageSize);
		Object[] values = list1.toArray();
		
		System.out.println("公客待回复统明细2566sql="+sql);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql, values);
		for (Map map : list) {
			TaskModel model = new TaskModel();
			if(map.get("TaskId")!=null && !"".equals(map.get("TaskId"))){
				
				model.setTaskId(map.get("TaskId").toString());
			}else{
				model.setTaskId("");
			}
			if(map.get("Initiator")!=null && !"".equals(map.get("Initiator"))){
				model.setInitiator(map.get("Initiator").toString());
			}else{
				model.setInitiator("");
			}
			
			if(map.get("male_guest_state")!=null && !"".equals(map.get("male_guest_state"))){
				model.setMaleGuestState(map.get("male_guest_state").toString());
			}else{
				model.setMaleGuestState("");
			}
			if(map.get("station_name")!=null && !"".equals(map.get("station_name"))){
				model.setStationName(map.get("station_name").toString());
			}else{
				model.setStationName("");
			}
			
			//临时做为空判断，正常OneInitiator的值不可以为空
			if (map.get("OneInitiator") != null){
				model.setOneInitiator(map.get("OneInitiator").toString());
			}else{
				model.setOneInitiator("");
			}		
			model.setProcessInstanceId(map.get("ProcessInstanceId").toString());
			if (map.get("SendTime") != null) {
				if (!map.get("SendTime").toString().equals("")) {
					try {
						model.setSendTime(format.parse(map.get("SendTime")
								.toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

			}
			if(map.get("Theme")!=null && !"".equals(map.get("Theme"))){
				model.setTheme(map.get("Theme").toString());
			}else{
				model.setTheme("");
			}
			if(map.get("DeptId")!=null && !"".equals(map.get("DeptId"))){
				model.setDeptId(map.get("DeptId").toString());
			}else{
				model.setDeptId("");
			}
			if(map.get("State")!=null && !"".equals(map.get("State"))){
				model.setState(Integer.parseInt(map.get("State").toString()));
			}else{
				model.setState(0);
			}
			if(map.get("Install_address")!=null && !"".equals(map.get("Install_address"))){
				model.setInstallAddress(map.get("Install_address").toString());
			}else{
				model.setInstallAddress("");
			}
			if(map.get("Barrier_number")!=null && !"".equals(map.get("Barrier_number"))){
				model.setBarrierNumber(map.get("Barrier_number").toString());
			}else{
				model.setBarrierNumber("");
			}
			if(map.get("create_time")!=null && !"".equals(map.get("create_time"))){
				if (map.get("create_time") != null) {
					if (!map.get("create_time").toString().equals("")) {
						try {
							model.setCreateTime(format.parse(map.get("create_time")
									.toString()));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}

				}
			}
			if(map.get("city")!=null && !"".equals(map.get("city"))){
				model.setCity(map.get("city").toString());
			}
			if(map.get("connect_person")!=null &&!"".equals(map.get("connect_person"))){
				model.setConnectPerson(map.get("connect_person").toString());
			}
			if(map.get("process_limit")!=null &&!"".equals(map.get("process_limit"))){
				model.setProcessLimit(map.get("process_limit").toString());
			}
			//工单历时 = 当前时间-故障发生时间：当工单历时大于处理时限，改变颜色
			if(map.get("create_time")!=null && !"".equals(map.get("create_time"))&& map.get("SendTime") != null){
				//两时间做差
				Date startTime = new Date();
				Date endTime = model.getCreateTime();
				long sortTime = startTime.getTime()-endTime.getTime();
				long hours = sortTime / (1000 * 60 * 60 );
				long minutes = sortTime/(1000*60)-hours*60;
				long second = sortTime/1000 - hours*60*60 - minutes*60;
				String newTime = hours+"小时"+minutes+"分钟"+second+"秒";
				model.setWorkTask(newTime);
				if(model.getProcessLimit()!=null&&!"".equals(model.getProcessLimit())){
					long processLimit = Long.parseLong(model.getProcessLimit());
					if(processLimit>hours){//没有超出时限
						model.setChangeColor(true);
					}else{//超出时限
						model.setChangeColor(false);
					}
				}
			}
			if(map.get("assignee")!=null && !"".equals(map.get("assignee"))){
				model.setExecutor(map.get("assignee").toString());
			}
			//状态存储--start
			String state = "";
			if(map.get("task_def_key")!=null && !"".equals(map.get("task_def_key"))&&"newMaleGuest".equals(map.get("task_def_key"))){
				state = "派发工单";
			}else if(map.get("task_def_key")!=null && !"".equals(map.get("task_def_key"))&&"auditor".equals(map.get("task_def_key"))){
				state="故障处理";
			}
			if(map.get("repeat_state")!=null && !"".equals(map.get("repeat_state"))&&"1".equals(map.get("repeat_state").toString().trim())){
				state = "(重修)"+state;
			}
			model.setStatusName(state);
			//--end
			
			r.add(model);

		}

		return r;
	}
	
	//查询主场景名
	public String getMainScenesNameById(String id){
		String val = "";//结果
		String sql = "select s.scene_name from master_male_scene s where s.scene_no = ?";
		ArrayList list = new ArrayList();
		list.add(id);
		Object[] args = list.toArray();
		List<Map> list1 = this.getJdbcTemplate().queryForList(sql,args);
		if (list1 != null && !list1.isEmpty() && list1.size() > 0) {
			if (list1.get(0).get("scene_name") != null) {
				val = list1.get(0).get("scene_name").toString();
			}
		}
		return val;
	}
	
	//查询子场景名
	public String getCopyScenesNameById(String id){
		String val = "";//结果
		String sql = "select k.copy_name from maste_male_copy_scene k where k.copy_no = ?";
		ArrayList list = new ArrayList();
		list.add(id);
		Object[] args = list.toArray();
		List<Map> list1 = this.getJdbcTemplate().queryForList(sql,args);
		if (list1 != null && !list1.isEmpty() && list1.size() > 0) {
			if (list1.get(0).get("copy_name") != null) {
				val = list1.get(0).get("copy_name").toString();
			}
		}
		return val;
	}
	
	//审批位置统计信息（材料金额、电杆、光缆数量、接头盒数量）
	public List<StatisticsMaterialInforModel> statisticsMaterialInfor(String userId,MaleGuestSelectAttribute selectAttribute,String reportFlag){
		List<StatisticsMaterialInforModel> list = null;
		String sql = "";
		if("acheck".equals(reportFlag)||"twoSpotChecks".equals(reportFlag)||"auditReport".equals(reportFlag)){
			if("acheck".equals(reportFlag)){ //一次核验报表
				sql = "";
			}else if("twoSpotChecks".equals(reportFlag)){ //二次抽查报表
				
			}else if("auditReport".equals(reportFlag)){	//周期领用表
				
			}
		}
		
		return null;
	}
	
	//显示公客场景模板明细
	public List<MaleSceneDetailModel> getMaleSceneDetail(String processInstanceId,String linkFlag,Map<String,String> param){
		String task_key = "'auditor','acheck'";
		if("twoSpotChecks".equals(linkFlag) || "auditReport".equals(linkFlag)){
			task_key = "'auditor','acheck','twoSpotChecks'";
		}
		ArrayList list1 = new ArrayList();
//		String sql ="select t.scene_name," +
//					"       t.copy_name," + 
//					"       t.dispose," + 
//					"       decode(t.copy_no, 'DKAOB', nvl(t.num, '1') || t.unit, t.unit) as total_unit," + 
//					"       dat.data_name as material_name," + 
//					"       t2.num," + 
//					"       t2.unit," + 
//					"       t2.price," + 
//					"       t2.total_price as total_amount," + 
//					"       decode(nvl(t2.is_utilize, '0'), '0', '否', '1', '是') as is_utilize" + 
//					"  from maste_male_data_rel_task t2," + 
//					"       maste_male_item_rel_task t," + 
//					"       MASTE_male_DATA          dat" + 
//					" where t2.process_instance_id = t.process_instance_id(+)" + 
//					"   and t2.data_no = dat.data_no" + 
//					"   and t2.process_instance_id = ?" + 
//					"   and t2.task_key = ?" + 
//					"   and t.task_key = ?";
		
		String sql= "select t22.scene_name," +
					"       t22.copy_name," + 
					"       t22.dispose," + 
					"       decode(t22.copy_no, 'DKAOB', nvl(t22.num, '1') || t22.unit, t22.unit) as total_unit," + 
					"       dat.data_name as material_name," + 
					"       t11.num," + 
					"       t11.unit," + 
					"       t11.price," + 
					"       t11.total_price as total_amount," + 
					"       decode(nvl(t11.is_utilize, '0'), '0', '否', '1', '是') as is_utilize," + 
					"       t11.task_key" + 
					"  from (select p.*," + 
					"               row_number() over(partition by p.process_instance_id, p.task_key order by p.insert_time desc) as row_index" + 
					"          from maste_male_data_rel_task p" + 
					"         where p.process_instance_id = ?" + 
					"           and p.task_key in ("+task_key+")) t11," + 
					"       (select *" + 
					"          from (select t2.process_instance_id," + 
					"                       t2.task_key," + 
					"                       t2.scene_name," + 
					"                       t2.copy_name," + 
					"                       t2.dispose," + 
					"                       t2.copy_no," + 
					"                       t2.num," + 
					"                       t2.unit," + 
					"                       row_number() over(partition by t2.process_instance_id order by t2.insert_time desc) as row_index" + 
					"                  from maste_male_item_rel_task t2" + 
					"                 where t2.process_instance_id = ?" + 
					"                   and t2.task_key in ("+task_key+"))) t22," + 
					"       MASTE_male_DATA dat" + 
					" where t11.process_instance_id = t22.process_instance_id(+)" + 
					"   and t11.task_key = t22.task_key(+)" + 
					"   and t11.data_no = dat.data_no" + 
					"   and t22.row_index = 1";
		
		list1.add(processInstanceId);
		list1.add(processInstanceId);
		
		Object[] values = list1.toArray();
		System.out.println("-----------------公客场景模板明细sql="+sql);
		List<MaleSceneDetailModel> r = new ArrayList<MaleSceneDetailModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql, values);
		for (Map map : list) {
			MaleSceneDetailModel model = new MaleSceneDetailModel();
			//主场景名
			if(map.get("scene_name")!=null && !"".equals(map.get("scene_name").toString())){
				model.setSceneName(map.get("scene_name").toString());
			}else{
				model.setSceneName("");
			}
			//子场景名
			if(map.get("copy_name")!=null && !"".equals(map.get("copy_name").toString())){
				model.setCopyName(map.get("copy_name").toString());
			}else{
				model.setCopyName("");
			}
			//处理措施
			if(map.get("dispose")!=null && !"".equals(map.get("dispose").toString())){
				model.setDispose(map.get("dispose").toString());
			}else{
				model.setDispose("");
			}
			//总单位
			if(map.get("total_unit")!=null && !"".equals(map.get("total_unit").toString())){
				model.setTotalUnit(map.get("total_unit").toString());
			}else{
				model.setTotalUnit("");
			}
			//材料
			if(map.get("material_name")!=null && !"".equals(map.get("material_name").toString())){
				model.setMaterialName(map.get("material_name").toString());
			}else{
				model.setMaterialName("");
			}
			//数量
			if(map.get("num")!=null && !"".equals(map.get("num").toString())){
				model.setNum(map.get("num").toString());
			}else{
				model.setNum("");
			}
			//单位	
			if(map.get("unit")!=null && !"".equals(map.get("unit").toString())){
				model.setUnit(map.get("unit").toString());
			}else{
				model.setUnit("");
			}
			//单价
			if(map.get("price")!=null && !"".equals(map.get("price").toString())){
				model.setPrice(map.get("price").toString());
			}else{
				model.setPrice("");
			}
			//总额
			if(map.get("total_amount")!=null && !"".equals(map.get("total_amount").toString())){
				model.setTotalAmount(map.get("total_amount").toString());
			}else{
				model.setTotalAmount("");
			}
			//是否利旧
			if(map.get("is_utilize")!=null && !"".equals(map.get("is_utilize").toString())){
				model.setIsUtilize(map.get("is_utilize").toString());
			}else{
				model.setIsUtilize("");
			}
			
			r.add(model);
			
		}

		return r;
	}
	
	//汇总公客场景模板审批材料统计 总金额统计、电杆数、接头盒数、光缆长度 
	//当为一次核验列表显示时：有一次核验场景模板数据用一次核验的，没有取施工队的场景模板数据。
	//当为二次抽检列表显示时：有二次抽检场景模板数据用二次抽检的，没有找一次核验的，一次核验没有就找施工队的。
	//当为周期领用表，同二次抽检，因为周期领用表不允许修改场景模板。
	public MaleSceneStatisticsModel getMaleSceneStatistics(String processInstanceId,String reportFlag,String operationType,Map<String,String> param){
		String task_key = "'auditor','acheck'";
		if("twoSpotChecks".equals(reportFlag) || "auditReport".equals(reportFlag)){
			task_key = "'auditor','acheck','twoSpotChecks'";
		}
		String sql ="select nvl(sum(nvl(t11.total_price, '0')), '0') as total_amount,\n" +
					"       nvl(sum(case\n" + 
					"                 when t11.data_no in\n" + 
					"                      ('BHMAQR', 'HKTSZC', 'MGRGAQ', 'RSFUIX', 'NIKHNJ', 'FLGOCG') then\n" + 
					"                  t11.num\n" + 
					"                 else\n" + 
					"                  '0'\n" + 
					"               end),\n" + 
					"           '0') as pole_num, --电杆数\n" + 
					"       nvl(sum(case\n" + 
					"                 when t11.data_no in\n" + 
					"                      ('PRSOCR', 'QZHQFL', 'SJPFQY', 'SGWVOU', 'OUBYVD') then\n" + 
					"                  t11.num\n" + 
					"                 else\n" + 
					"                  '0'\n" + 
					"               end),\n" + 
					"           '0') as joint_box_num, --接头盒数\n" + 
					"       nvl(sum(case\n" + 
					"                 when t11.data_no in ('TJHREL',\n" + 
					"                                      'ZQIKEA',\n" + 
					"                                      'CKBRQI',\n" + 
					"                                      'OJRJES',\n" + 
					"                                      'EELTNY',\n" + 
					"                                      'UJIPIJ',\n" + 
					"                                      'WRAYQU',\n" + 
					"                                      'ZUQXME',\n" + 
					"                                      'DDDTIA',\n" + 
					"                                      'VXNTLR','XOFSKA') then\n" + 
					"                  t11.num\n" + 
					"                 else\n" + 
					"                  '0'\n" + 
					"               end),\n" + 
					"           '0') as optical_cable_length --光缆\n" + 
					"  from (select p.*,\n" + 
					"               row_number() over(partition by p.process_instance_id, p.task_key order by p.insert_time desc) as row_index\n" + 
					"          from maste_male_data_rel_task p\n" + 
					"         where p.process_instance_id in ("+processInstanceId+")\n" + 
					"           and p.task_key in ("+task_key+")) t11,\n" + 
					"       (select *\n" + 
					"          from (select t2.process_instance_id,\n" + 
					"                       t2.task_key,\n" + 
					"                       row_number() over(partition by t2.process_instance_id order by t2.insert_time desc) as row_index\n" + 
					"                  from maste_male_item_rel_task t2\n" + 
					"                 where t2.process_instance_id in ("+processInstanceId+")\n" + 
					"                   and t2.task_key in("+task_key+"))) t22\n" + 
					" where t11.process_instance_id = t22.process_instance_id(+)\n" + 
					"   and t11.task_key = t22.task_key(+)\n" + 
					"   and t22.row_index = 1";
		System.out.println("------公客场景模板审批材料统计sql="+sql);
		
		MaleSceneStatisticsModel model = new MaleSceneStatisticsModel();
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		if(list != null && list.size() > 0){
			Map map = list.get(0);
			//总金额
			if(map.get("total_amount")!=null && !"".equals(map.get("total_amount").toString())){
				model.setTotalAmount(map.get("total_amount").toString());
			}else{
				model.setTotalAmount("0");
			}
			//电杆数
			if(map.get("pole_num")!=null && !"".equals(map.get("pole_num").toString())){
				model.setPoleNum(map.get("pole_num").toString());
			}else{
				model.setPoleNum("0");
			}
			//接头盒数 
			if(map.get("joint_box_num")!=null && !"".equals(map.get("joint_box_num").toString())){
				model.setJointBoxNum(map.get("joint_box_num").toString());
			}else{
				model.setJointBoxNum("0");
			}
			//光缆 
			if(map.get("optical_cable_length")!=null && !"".equals(map.get("optical_cable_length").toString())){
				model.setOpticalCableLength(map.get("optical_cable_length").toString());
			}else{
				model.setOpticalCableLength("0");
			}
		}else{
			model.setTotalAmount("0");
			model.setPoleNum("0");
			model.setJointBoxNum("0");
			model.setOpticalCableLength("0");
		}
		return model;
	}
	
	//取公客归集工单子工单的最小时间（最小告警发生时间或者最小派单时间）
	public Date getMinDateForSubWorkOrder(String mainProcessInstanceId,List sublist,String flag,Map<String,String> param){
		ArrayList list1 = new ArrayList();
		String subProcessInstanceIds =""; //子工单号
		if(sublist != null && sublist.size() > 0){
			for(int i=0;i<sublist.size();i++){
				subProcessInstanceIds +="'"+sublist.get(i)+"',";
			}
			subProcessInstanceIds = subProcessInstanceIds.substring(0, subProcessInstanceIds.length()-1);
		}
		
		String parentProcessInstanceId = ""; //主工单号
		if(mainProcessInstanceId != null && !"".equals(mainProcessInstanceId)){
			parentProcessInstanceId = mainProcessInstanceId;
		}else{
			parentProcessInstanceId = "";
		}
		
		if(flag != null && !"".equals(flag)){
			//判断查询哪个时间
			String field = "";
			if("create".equals(flag)){ //告警发生时间
				field = "create_time";
			}else if("send".equals(flag)){ //派单时间
				field = "send_time";
			}else{
				return null;
			}
			
			//查询
			String sql = "select min("+field+") as "+field+" from pnr_act_transfer_office_main m where 1=1";
			if("".equals(parentProcessInstanceId) && "".equals(subProcessInstanceIds)){
				return null;
			}else{
				if(!"".equals(parentProcessInstanceId)){
					sql+=" and m.parent_process_instance_id = ?";
					list1.add(parentProcessInstanceId);
					sql+=" and m.male_guest_state <> '3'";
				}
				
				if(!"".equals(subProcessInstanceIds)){
					sql+=" and m.process_instance_id in ("+subProcessInstanceIds+")";
					
				}
			}
			System.out.println("------取公客归集工单子工单的最小时间（最小告警发生时间或者最小派单时间）sql="+sql);
			
			Object[] values = list1.toArray();
			List<Map> list = this.getJdbcTemplate().queryForList(sql, values);
			Date val = null ;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (list != null && !list.isEmpty() && list.size() > 0) {
				if (list.get(0).get(field) != null) {
					try {
						val = format.parse(list.get(0).get(field).toString());
					} catch (ParseException e) {
						e.printStackTrace();
						return null;
					}
				}
			}
			return val;
		}else{
			return null;
		}
	}
	
	//生成周期领用表的材料数量统计
	public MaterialQuantityModel getMaterialQuantityOfCycleReport(String userId,MaleGuestSelectAttribute selectAttribute,Map<String,String> param){
		ArrayList list1 = new ArrayList();
		String sql ="select dat.data_no, sum(nvl(t.num, '0')) as total_num" +
					"  from (select t11.*" + 
					"          from (select p.*," + 
					"                       row_number() over(partition by p.process_instance_id, p.task_key order by p.insert_time desc) as row_index" + 
					"                  from maste_male_data_rel_task p, pnr_cycle_collar_report r" + 
					"                 where p.process_instance_id = r.process_instance_id" + 
					"                   and r.county = ?" + 
					"                   and trunc(r.start_date) =" + 
					"                       to_date(?, 'yyyy-mm-dd')" + 
					"                   and trunc(r.end_date) = to_date(?, 'yyyy-mm-dd')" + 
					"                   and p.task_key in ('auditor', 'acheck', 'twoSpotChecks')) t11," + 
					"               (select *" + 
					"                  from (select t2.process_instance_id," + 
					"                               t2.task_key," + 
					"                               row_number() over(partition by t2.process_instance_id order by t2.insert_time desc) as row_index" + 
					"                          from maste_male_item_rel_task t2," + 
					"                               pnr_cycle_collar_report  r" + 
					"                         where t2.process_instance_id = r.process_instance_id" + 
					"                           and r.county = ?" + 
					"                           and trunc(r.start_date) =" + 
					"                               to_date(?, 'yyyy-mm-dd')" + 
					"                           and trunc(r.end_date) =" + 
					"                               to_date(?, 'yyyy-mm-dd')" + 
					"                           and t2.task_key in" + 
					"                               ('auditor', 'acheck', 'twoSpotChecks'))) t22" + 
					"         where t11.process_instance_id = t22.process_instance_id(+)" + 
					"           and t11.task_key = t22.task_key(+)" + 
					"           and t22.row_index = 1) t," + 
					"       maste_male_data dat" + 
					" where dat.data_no = t.data_no(+)" + 
					"   and dat.data_no <> 'XHWXIX'" + 
					" group by dat.data_no";
		
		//和maste_male_data_rel_task表关联的条件
		list1.add(selectAttribute.getCounty().trim());
		list1.add(selectAttribute.getBeginTime());
		list1.add(selectAttribute.getEndTime());
		
		//和maste_male_item_rel_task表关联的条件
		list1.add(selectAttribute.getCounty().trim());
		list1.add(selectAttribute.getBeginTime());
		list1.add(selectAttribute.getEndTime());
		
	    Object[] values = list1.toArray();
		
		System.out.println("------生成周期领用表的材料数量统计sql="+sql);
		MaterialQuantityModel model = new MaterialQuantityModel();
		List<Map> list = this.getJdbcTemplate().queryForList(sql, values);
		Field field = null;
		for (Map map : list) {
			String data_no = map.get("data_no").toString();
			//System.out.println("----data_no="+data_no);
			String total_num = map.get("total_num").toString();
			try {
				field = model.getClass().getDeclaredField(data_no);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
			field.setAccessible(true);
			try {
				field.set(model,total_num);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			
//			Field field;
//			try {
//				field = model.getClass().getDeclaredField(data_no);
//				field.setAccessible(true);
//				field.set(model,total_num);
//			} catch (SecurityException e) {
//				e.printStackTrace();
//			} catch (NoSuchFieldException e) {
//				e.printStackTrace();
//			} catch (IllegalArgumentException e) {
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				e.printStackTrace();
//			}
		}
		return model;
	}
	
	//抢修工单 工单查询条数
	public int getWorkOrderQueryOfTransferOfficeListCount(String areaid,String userid, String flag,String processKey,ConditionQueryModel conditionQueryModel){
		List listsql = new ArrayList();
		String sql = "";
		String selectSql =  "select count(m.process_instance_id) as total" +
							"            from pnr_act_transfer_office_main m, taw_system_user u" + 
							"           where m.task_assignee = u.userid(+)" + 
							"             and m.state <> 2" + 
							"             and m.themeinterface = 'transferOffice'";
		
		String whereSql = "";
		//判断当前登录人的地市权限
		if(areaid!=null && !"".equals(areaid)){
			if((StaticMethod.nullObject2String(areaid)).length()==2){
				whereSql += " and m.city is not null and m.country is not null ";
			}else if((StaticMethod.nullObject2String(areaid)).length()==4){
				whereSql += " and m.city=? and m.country is not null ";
				listsql.add(areaid);
			}else if((StaticMethod.nullObject2String(areaid)).length()==6){
				whereSql += " and m.city is not null and m.country=? ";
				listsql.add(areaid);
			}
		}
		//开始时间
		if (conditionQueryModel.getSendStartTime() != null
				&& !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >=?";
			listsql.add(conditionQueryModel.getSendStartTime());
			
		}
		//结束时间
		if (conditionQueryModel.getSendEndTime() != null
				&& !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?";
			listsql.add(conditionQueryModel.getSendEndTime());
		}
		//工单号
		if (conditionQueryModel.getWorkNumber() != null
				&& !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and m.process_instance_id =?";
			listsql.add(conditionQueryModel.getWorkNumber().trim());
		}
		//工单名称
		if (conditionQueryModel.getTheme() != null && !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and instr(m.theme,?) >0";
			listsql.add(conditionQueryModel.getTheme().trim());
		}
		//工单状态
		if (conditionQueryModel.getStatus() != null && !"".equals(conditionQueryModel.getStatus())) {
			if("archive".equals(conditionQueryModel.getStatus())){ //归档
				whereSql += " and m.state =?";
				listsql.add(5);
			}else if("delete".equals(conditionQueryModel.getStatus())){ //删除
				whereSql += " and m.state =?";
				listsql.add(1);
			}else{
				whereSql += " and m.task_def_key =?";
				listsql.add(conditionQueryModel.getStatus());
			}
		}
		  sql += selectSql+ whereSql;
		System.out.println("---------------抢修工单工单查询条数PnrTransferOfficeDaoJDBC-getWorkOrderQueryOfTransferOfficeListCount:sql=" + sql);
		Object[] args = listsql.toArray();
		int size = this.getJdbcTemplate().queryForInt(sql,args);
		return size;
	}
	
	//抢修工单 工单结果集
	public List<TaskModel> getWorkOrderQueryOfTransferOfficeList(String areaid,String userid,String flag, String processKey,ConditionQueryModel conditionQueryModel, 
			int firstResult,int endResult, int pageSize){
		List listsql = new ArrayList();
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		String selectSql =  "select m.*," +
							"                 u.deptid," + 
							"                 case" + 
							"                   when m.state = 5 then" + 
							"                    'archive'" + 
							"                   when m.state = 1 then" + 
							"                    'delete'" + 
							"                   else" + 
							"                    m.task_def_key" + 
							"                 end as TaskDefKey," + 
							"                 case" + 
							"                   when m.State = 5 then" + 
							"                    '已归档'" + 
							"                   when m.State = 1 then" + 
							"                    '已删除'" + 
							"                   else" + 
							"                    m.task_def_key_name" + 
							"                 end as StatusName" + 
							"            from pnr_act_transfer_office_main m, taw_system_user u" + 
							"           where m.task_assignee = u.userid(+)" + 
							"             and m.state <> 2" + 
							"             and m.themeinterface = 'transferOffice'";

		
		String whereSql = "";
		//判断当前登录人的地市权限
		if(areaid!=null && !"".equals(areaid)){
			if((StaticMethod.nullObject2String(areaid)).length()==2){
				whereSql += " and m.city is not null and m.country is not null ";
			}else if((StaticMethod.nullObject2String(areaid)).length()==4){
				whereSql += " and m.city=? and m.country is not null ";
				listsql.add(areaid);
			}else if((StaticMethod.nullObject2String(areaid)).length()==6){
				whereSql += " and m.city is not null and m.country=? ";
				listsql.add(areaid);
			}
		}
		//开始时间
		if (conditionQueryModel.getSendStartTime() != null
				&& !conditionQueryModel.getSendStartTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >=?";
			listsql.add(conditionQueryModel.getSendStartTime());
			
		}
		//结束时间
		if (conditionQueryModel.getSendEndTime() != null
				&& !conditionQueryModel.getSendEndTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?";
			listsql.add(conditionQueryModel.getSendEndTime());
		}
		//工单号
		if (conditionQueryModel.getWorkNumber() != null
				&& !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and m.process_instance_id =?";
			listsql.add(conditionQueryModel.getWorkNumber().trim());
		}
		//工单名称
		if (conditionQueryModel.getTheme() != null && !conditionQueryModel.getTheme().equals("")) {
			whereSql += " and instr(m.theme,?) >0";
			listsql.add(conditionQueryModel.getTheme().trim());
		}
		//工单状态
		if (conditionQueryModel.getStatus() != null && !"".equals(conditionQueryModel.getStatus())) {
			if("archive".equals(conditionQueryModel.getStatus())){ //归档
				whereSql += " and m.state =?";
				listsql.add(5);
			}else if("delete".equals(conditionQueryModel.getStatus())){ //删除
				whereSql += " and m.state =?";
				listsql.add(1);
			}else{
				whereSql += " and m.task_def_key =?";
				listsql.add(conditionQueryModel.getStatus());
			}
		}
		sql += selectSql
		+ whereSql
		+ " order by m.send_time) temp1 where rownum <=?) temp2 where temp2.num >?";
		listsql.add(endResult * pageSize);
		listsql.add(firstResult * pageSize);
		System.out.println("---------------抢修工单 工单查询PnrTransferOfficeDaoJDBC-getWorkOrderQueryOfTransferOfficeList:sql=" + sql);
		Object[] args = listsql.toArray();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,args);
		for (Map map : list) {
			TaskModel model = new TaskModel();
			if (map.get("process_instance_id") != null && !"".equals(map.get("process_instance_id").toString())) {
				model.setProcessInstanceId(map.get("process_instance_id").toString());
			}
			if (map.get("theme") != null && !"".equals(map.get("theme").toString())) {
				model.setTheme(map.get("theme").toString());
			}
			if (map.get("initiator") != null&& !"".equals(map.get("initiator").toString())) {
				model.setInitiator(map.get("initiator").toString());
			}
			//所属部门
			if (map.get("deptid") != null && !"".equals(map.get("deptid").toString())) {
				model.setDeptId(map.get("deptid").toString());
			}
			if (map.get("submit_application_time") != null && !"".equals(map.get("submit_application_time").toString())) {
				try {
					model.setApplicationTime(format.parse(map.get("submit_application_time").toString()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			if (map.get("distributed_interface_time") != null && !"".equals(map.get("distributed_interface_time").toString())) {
					try {
						model.setDistributedInterfaceTime(format.parse(map.get("distributed_interface_time").toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
			}
			//当前状态
			if (map.get("statusName") != null && !"".equals(map.get("statusName").toString())) {
				model.setStatusName(map.get("statusName").toString());
			}
			if (map.get("city") != null && !"".equals(map.get("city").toString())) {
				model.setCity(map.get("city").toString());
			}
			if (map.get("country") != null && !"".equals(map.get("country").toString())) {
				model.setCountry(map.get("country").toString());
			}
			if (map.get("work_type") != null && !"".equals(map.get("work_type").toString())) {
				model.setWorkType(map.get("work_type").toString());
			}
			if (map.get("workorder_type_name") != null && !"".equals(map.get("workorder_type_name").toString())) {
				model.setWorkorderTypeName(map.get("workorder_type_name").toString());
			}
			if (map.get("sub_type_name") != null && !"".equals(map.get("sub_type_name").toString())) {
				model.setSubTypeName(map.get("sub_type_name").toString());
			}
			if (map.get("key_word") != null && !"".equals(map.get("key_word").toString())) {
				model.setKeyWord(map.get("key_word").toString());
			}
			if (map.get("work_order_degree") != null && !"".equals(map.get("work_order_degree").toString())) {
				model.setWorkOrderDegree(map.get("work_order_degree").toString());
			}
			if (map.get("project_amount") != null && !"".equals(map.get("project_amount").toString())) {
				model.setProjectAmount(Double.parseDouble(map.get("project_amount").toString()));
			}
			if (map.get("compensate") != null && !"".equals(map.get("compensate").toString())) {
				model.setCompensate(Double.parseDouble(map.get("compensate").toString()));
			}
			if (map.get("calculate_income_ratio") != null && !"".equals(map.get("calculate_income_ratio").toString())) {
				model.setCalculateIncomeRatio(map.get("calculate_income_ratio").toString());
			}
			//派单时间
			if (map.get("send_time") != null && !"".equals(map.get("send_time").toString())) {
				try {
					model.setSendTime(format.parse(map.get("send_time").toString()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			//工单生成人
			if (map.get("one_initiator") != null && !"".equals(map.get("one_initiator").toString())) {
				model.setOneInitiator(map.get("one_initiator").toString());
			}
			r.add(model);
		}
		return r;
	}
		
	//查询 周期领用表提交审批后没有归档的工单 统计数
	public int manualBatchArchiveForCycleReportCount(String userId,MaleGuestSelectAttribute selectAttribute){
		ArrayList list1 = new ArrayList();
		String sql = "";
		String selectSql =  "select count(m.process_instance_id) as total" +
							"  from pnr_act_transfer_office_main m," + 
							"       (select *" + 
							"          from (select r.process_instance_id," + 
							"                       r.county," + 
							"                       r.submit_date," + 
							"                       r.submit_user_id," + 
							"                       row_number() over(partition by r.process_instance_id order by r.submit_date desc) as row_index" + 
							"                  from pnr_cycle_collar_report r" + 
							"                 where r.submit_date is not null" + 
							"                   and r.submit_user_id is not null)" + 
							"         where row_index = 1) k" + 
							" where m.process_instance_id = k.process_instance_id" + 
							"   and m.task_def_key = 'auditReport'";
		
		//拼接条件查询
		String whereSql = "";
		if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >= ?";
			list1.add(selectAttribute.getBeginTime());
		}
		if (selectAttribute.getEndTime() != null && !selectAttribute.getEndTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?";
			list1.add(selectAttribute.getEndTime());
		}
		if (selectAttribute.getMaleGuestNumber() != null && !selectAttribute.getMaleGuestNumber().equals("")) {
			whereSql += " and m.process_instance_id = ?";
			list1.add(selectAttribute.getMaleGuestNumber().trim());
		}
		if (selectAttribute.getTheme() != null && !selectAttribute.getTheme().equals("")) {
			whereSql += " and instr(m.theme,?)>0 ";
			list1.add(selectAttribute.getTheme().trim());
		}
		sql = selectSql + whereSql;

		System.out.println("------查询 周期领用表提交审批后没有归档的工单 统计数sql="+sql);
		Object[] values = list1.toArray();
		int total = 0;
		total = this.getJdbcTemplate().queryForInt(sql,values);
		return total;
	}
	
	//查询 周期领用表提交审批后没有归档的工单 列表
	public List<TaskModel> manualBatchArchiveForCycleReportList(String userId,MaleGuestSelectAttribute selectAttribute,int firstResult,int endResult,int pageSize){
		ArrayList list1 = new ArrayList();
		String sql = " select temp2.* from (select temp1.*, rownum num from (";
		String selectSql =  "select m.process_instance_id," +
							"       m.theme," + 
							"       m.city," + 
							"       m.country," + 
							"       m.task_id," + 
							"       m.task_def_key," + 
							"       m.task_def_key_name," + 
							"       m.assignee," + 
							"       m.send_time," + 
							"       k.submit_date," + 
							"       k.submit_user_id," + 
							"       k.report_number" + 
							"  from pnr_act_transfer_office_main m," + 
							"       (select *" + 
							"          from (select r.process_instance_id," + 
							"                       r.county," + 
							"                       r.submit_date," + 
							"                       r.submit_user_id," + 
							"                       r.report_number," + 
							"                       row_number() over(partition by r.process_instance_id order by r.submit_date desc) as row_index" + 
							"                  from pnr_cycle_collar_report r" + 
							"                 where r.submit_date is not null" + 
							"                   and r.submit_user_id is not null)" + 
							"         where row_index = 1) k" + 
							" where m.process_instance_id = k.process_instance_id" + 
							"   and m.task_def_key = 'auditReport'";
		
		//拼接条件查询
		String whereSql = "";
		if (selectAttribute.getBeginTime() != null && !selectAttribute.getBeginTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') >= ?";
			list1.add(selectAttribute.getBeginTime());
		}
		if (selectAttribute.getEndTime() != null && !selectAttribute.getEndTime().equals("")) {
			whereSql += " and to_char(m.send_time,'yyyy-mm-dd hh24:mi:ss') <= ?";
			list1.add(selectAttribute.getEndTime());
		}
		if (selectAttribute.getMaleGuestNumber() != null && !selectAttribute.getMaleGuestNumber().equals("")) {
			whereSql += " and m.process_instance_id = ?";
			list1.add(selectAttribute.getMaleGuestNumber().trim());
		}
		if (selectAttribute.getTheme() != null && !selectAttribute.getTheme().equals("")) {
			whereSql += " and instr(m.theme,?)>0 ";
			list1.add(selectAttribute.getTheme().trim());
		}
		sql += selectSql + whereSql
				+ " order by m.send_time desc) temp1 where rownum <= ?) temp2 where temp2.num > ?";
		list1.add(endResult * pageSize);
		list1.add(firstResult * pageSize);
		
		System.out.println("------查询 周期领用表提交审批后没有归档的工单 列表sql="+sql);
		Object[] values = list1.toArray();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,values);
		for (Map map : list) {
			TaskModel model = new TaskModel();
			//工单号
			if(map.get("process_instance_id")!=null && !"".equals(map.get("process_instance_id").toString())){
				model.setProcessInstanceId(map.get("process_instance_id").toString());
			}else{
				model.setProcessInstanceId("");
			}
			//工单主题
			if(map.get("theme")!=null && !"".equals(map.get("theme").toString())){
				model.setTheme(map.get("theme").toString());
			}else{
				model.setTheme("");
			}
			//地市
			if(map.get("city")!=null && !"".equals(map.get("city").toString())){
				model.setCity(map.get("city").toString());
			}else{
				model.setCity("");
			}
			//区县
			if(map.get("country")!=null && !"".equals(map.get("country").toString())){
				model.setCountry(map.get("country").toString());
			}else{
				model.setCountry("");
			}
			//任务号
			if(map.get("task_id")!=null && !"".equals(map.get("task_id").toString())){
				model.setTaskId(map.get("task_id").toString());
			}else{
				model.setTaskId("");
			}
			//环节值（英文）
			if(map.get("task_def_key")!=null && !"".equals(map.get("task_def_key").toString())){
				model.setTaskDefKey(map.get("task_def_key").toString());
			}else{
				model.setTaskDefKey("");
			}
			//环节值（中文）
			if(map.get("task_def_key_name")!=null && !"".equals(map.get("task_def_key_name").toString())){
				model.setStatusName(map.get("task_def_key_name").toString());
			}else{
				model.setStatusName("");
			}
			//当前环节人
			if(map.get("assignee")!=null && !"".equals(map.get("assignee").toString())){
				model.setOneInitiator(map.get("assignee").toString());
			}else{
				model.setOneInitiator("");
			}
			//派单时间
			if (map.get("send_time") != null && !"".equals(map.get("send_time").toString())) {
				try {
					model.setSendTime(format.parse(map.get("send_time").toString()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			//报表提交时间
			if (map.get("submit_date") != null && !"".equals(map.get("submit_date").toString())) {
				try {
					model.setApplicationTime(format.parse(map.get("submit_date").toString()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			//报表提交人
			if(map.get("submit_user_id")!=null && !"".equals(map.get("submit_user_id").toString())){
				model.setTwoInitiator(map.get("submit_user_id").toString());
			}else{
				model.setTwoInitiator("");
			}
			//报表编号
			if(map.get("report_number")!=null && !"".equals(map.get("report_number").toString())){
				model.setBarrierNumber(map.get("report_number").toString());
			}else{
				model.setBarrierNumber("");
			}
			
			r.add(model);
		}
		return r;
	}
	
	/**
	 * 	 通过报表编号查询周期领用表的条数
	 	 * @author WANGJUN
	 	 * @title: getCycleReportCountByReportNumber
	 	 * @date Jun 30, 2016 10:43:01 AM
	 	 * @param reportNumber 报表编号
	 	 * @return int
	 */
	public int getCycleReportCountByReportNumber(String reportNumber){
		ArrayList list = new ArrayList();
		String sql ="select count(r.process_instance_id) as total" +
					"  from pnr_cycle_collar_report r" + 
					" where r.report_number = ?";

		list.add(reportNumber);

		System.out.println("------ 通过报表编号查询周期领用表的条数sql="+sql);
		
		Object[] args = list.toArray();
		
		int total = this.getJdbcTemplate().queryForInt(sql,args);
		return total;
	}
	
	/**
	 * 	 通过报表编号查询验证表里是否有数据 pnr_verified_number
	 	 * @author WANGJUN
	 	 * @title: getVerifiedCountByReportNumber
	 	 * @date Jun 30, 2016 11:21:33 AM
	 	 * @param reportNumber 报表编号
	 	 * @return int
	 */
	public int getVerifiedCountByReportNumber(String reportNumber){
		ArrayList list = new ArrayList();
		String sql ="select count(n.verified_number) as total" +
					"  from pnr_verified_number n" + 
					" where n.verified_number = ?";
		
		list.add(reportNumber);

		System.out.println("------ 通过报表编号查询验证表里是否有数据sql="+sql);
		
		Object[] args = list.toArray();
		
		int total = this.getJdbcTemplate().queryForInt(sql,args);
		return total;
	}
	

	/**
	 *   插入验证信息
	 	 * @author WANGJUN
	 	 * @title: insertVerifiedNumber
	 	 * @date Jun 30, 2016 1:40:37 PM
	 	 * @param reportNumber
	 	 * @param type void
	 */
	public boolean insertVerifiedNumber(final String reportNumber,final String type){
		String sql = "insert into pnr_verified_number(verified_number,verified_type) values(?,?)";
		this.getJdbcTemplate().execute(sql, new PreparedStatementCallback(){
			@Override
			public Object doInPreparedStatement(PreparedStatement ps)
					throws SQLException, DataAccessException {
				ps.setString(1, reportNumber);
				ps.setString(2, type);
				return ps.execute();
			}
			
		});
		return false;
	}
	
	/**
	 * 	 用周期领用表报表编号查询对应的开始时间、结束时间、区县
	 	 * @author WANGJUN
	 	 * @title: getTimeCountyByReportNum
	 	 * @date Jun 30, 2016 3:26:12 PM
	 	 * @param reportNum
	 	 * @return Map<String,String>
	 */
	public Map<String,String> getTimeCountyByReportNum(String reportNum){
		ArrayList list1 = new ArrayList();
		String sql = "select distinct r.start_date,r.end_date,r.county from pnr_cycle_collar_report r where r.report_number = ?";
		list1.add(reportNum);
		
		System.out.println("------用周期领用表报表编号查询对应的开始时间、结束时间、区县sql="+sql);
		Object[] values = list1.toArray();
		
		Map<String,String> map = new HashMap<String,String>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,values);
		if(list !=null && list.size() > 0){
			Map map2 = list.get(0);
			if(map2.get("start_date")!=null && !"".equals(map2.get("start_date").toString())){
				map.put("sendStartTime", map2.get("start_date").toString());
			}
			if(map2.get("end_date")!=null && !"".equals(map2.get("end_date").toString())){
				map.put("endTime", map2.get("end_date").toString());
			}
			if(map2.get("county")!=null && !"".equals(map2.get("county").toString())){
				map.put("county", map2.get("county").toString());
			}
		}
		return map;
	}
	
	/**
	 * 	 用报表编号取提交时间
	 	 * @author WANGJUN
	 	 * @title: getSubmitDateByReportNum
	 	 * @date Jul 6, 2016 10:30:23 AM
	 	 * @param reportNum
	 	 * @return Date
	 */
	public Date getSubmitDateByReportNum(String reportNum){
		ArrayList list1 = new ArrayList();
		String sql = "select distinct r.submit_date from pnr_cycle_collar_report r where r.report_number = ? and r.submit_date is not null";
		list1.add(reportNum);
		System.out.println("------用报表编号取提交时间sql="+sql);
		Object[] values = list1.toArray();
		Date submitDate = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map> list = this.getJdbcTemplate().queryForList(sql,values);
		if(list != null && list.size() > 0){
			Map map2 = list.get(0);
			if(map2.get("submit_date")!=null && !"".equals(map2.get("submit_date").toString())){
				try {
					submitDate = format.parse(map2.get("submit_date").toString());
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		
		}
		return submitDate;
	}
	
	
	public int getAndroidWorkOrderCount(String sampling_userid) {
		List paramList = new ArrayList();
		String sql="select count(process_instance_id) as total from pnr_act_transfer_office_main where sampling_state = '1' and sampling_mark_userid = ? ";
		paramList.add(sampling_userid);
		Object[] args = paramList.toArray();
		int size = this.getJdbcTemplate().queryForInt(sql,args);
		return size;
	}
	
	public List<AndroidWorkOrderTask> getAndroidWorkOrderList(String sampling_userid,int pageIndex, int pageSize) {
		List<Object> paramList = new ArrayList<Object>();
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		sql += "select process_instance_id,theme from pnr_act_transfer_office_main where sampling_state='1' and sampling_mark_userid = ? ";
		paramList.add(sampling_userid);
		sql += " order by send_time desc) temp1 where rownum <=?) temp2 where temp2.num >? ";
		paramList.add((pageIndex+1)*pageSize);
		System.out.println("-------(pageIndex+1)*pageSize="+(pageIndex+1)*pageSize);
		paramList.add(pageIndex*pageSize);
		System.out.println("-------pageIndex*pageSize="+pageIndex*pageSize);
		System.out.println("----PnrTransferOfficeDaoJDBC--getAndroidTaskList--手机工单查询sql="+sql);
		Object[] args = paramList.toArray();
		List<AndroidWorkOrderTask> r = new ArrayList<AndroidWorkOrderTask>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,args);
		for (Map map : list) {
			AndroidWorkOrderTask model = new AndroidWorkOrderTask();
			if (map.get("process_instance_id") != null && !"".equals(map.get("process_instance_id").toString())) {
				model.setProcessInstanceId(map.get("process_instance_id").toString());
			}
			if (map.get("theme") != null && !"".equals(map.get("theme").toString())) {
				model.setTheme(map.get("theme").toString());
			}
			r.add(model);
		}
		return r;
	}
	
	public int updateWorkOrderOpinion(String process_instance_id,String opinion) {
		int rowsNum = 0; //更新的条数
		String sql = "update pnr_act_transfer_office_main  set sampling_judgments ='"+opinion+"',sampling_state='2' where process_instance_id='"+process_instance_id+"' and sampling_state='1'";
		rowsNum = this.getJdbcTemplate().update(sql);
		return rowsNum;
	}
	
	/**
	 *   公客工单查询 条数 20161222
	 	 * @author WangJun
	 	 * @title: getMaleGuestWorkOrderQueryCount
	 	 * @date Dec 22, 2016 3:34:28 PM
	 	 * @param areaid
	 	 * @param userid
	 	 * @param conditionQueryModel
	 	 * @return int
	 */
	public int getMaleGuestWorkOrderQueryCount(String areaid,String userid,ConditionQueryModel conditionQueryModel){
		List listsql = new ArrayList();
		String sql ="select count(t.process_instance_id) as total\n" +
					"  from (select m.send_time,\n" + 
					"               m.process_instance_id,\n" + 
					"               m.city,\n" + 
					"               m.country,\n" + 
					"               decode(m.male_guest_state,\n" + 
					"                      2,\n" + 
					"                      (select task_def_key\n" + 
					"                         from pnr_act_transfer_office_main\n" + 
					"                        where process_instance_id =\n" + 
					"                              m.parent_process_instance_id),\n" + 
					"                      m.task_def_key) as task_def_key\n" + 
					"          from pnr_act_transfer_office_main m\n" + 
					"         where m.themeinterface = 'maleGuest'\n" + 
					"           and nvl(m.male_guest_state, '0') in ('0', '2', '3')) t";

		
		String whereSql = " where 1 = 1";
		//开始时间
		if (conditionQueryModel.getSendStartTime() != null && !"".equals(conditionQueryModel.getSendStartTime())) {
			whereSql += "   and t.send_time >= to_date(?, 'yyyy-mm-dd')\n";
			listsql.add(conditionQueryModel.getSendStartTime());
		}
		//结束时间
		if (conditionQueryModel.getSendEndTime() != null && !"".equals(conditionQueryModel.getSendEndTime())) {
			whereSql += "   and t.send_time <= to_date(?, 'yyyy-mm-dd') -- 时间\n";
			listsql.add(conditionQueryModel.getSendEndTime());
		}
		//工单号
		if (conditionQueryModel.getWorkNumber() != null
				&& !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and t.process_instance_id =?";
			listsql.add(conditionQueryModel.getWorkNumber().trim());
		}
		// 当前登录人的区域权限
		if(areaid!=null && !"".equals(areaid)){
//			if((StaticMethod.nullObject2String(areaid)).length()==2){
//				
//			}else
			if((StaticMethod.nullObject2String(areaid)).length()==4){
				whereSql += " and t.city=? ";
				listsql.add(areaid);
			}else if((StaticMethod.nullObject2String(areaid)).length()==6){
				whereSql += " and t.country=? ";
				listsql.add(areaid);
			}
		}
		//地市
		if (conditionQueryModel.getCity() != null && !"".equals(conditionQueryModel.getCity())) {
			whereSql += " and t.city = ?";
			listsql.add(conditionQueryModel.getCity());

		}
		//区县
		if (conditionQueryModel.getCountry() != null && !"".equals(conditionQueryModel.getCountry())) {
			whereSql += " and t.country = ?";
			listsql.add(conditionQueryModel.getCountry());
		}
		//状态
		if (conditionQueryModel.getStatus() != null && !"".equals(conditionQueryModel.getStatus())) {
			whereSql += "   and t.task_def_key = ? -- 状态";;
			listsql.add(conditionQueryModel.getStatus());

		}
		
	    sql += whereSql;
		  
		System.out.println("****PnrTransferNewPrecheckDaoJDBC-getMaleGuestWorkOrderQueryCount:工单查询-" + sql);
		Object[] args = listsql.toArray();
		int size = this.getJdbcTemplate().queryForInt(sql,args);
		return size;
	}
	
	/**
	 *   公客工单查询 列表 20161222
	 	 * @author WangJun
	 	 * @title: getMaleGuestWorkOrderQueryList
	 	 * @date Dec 22, 2016 3:35:58 PM
	 	 * @param areaid
	 	 * @param userid
	 	 * @param conditionQueryModel
	 	 * @param firstResult
	 	 * @param endResult
	 	 * @param pageSize
	 	 * @return List<TaskModel>
	 */
	public List<TaskModel> getMaleGuestWorkOrderQueryList(String areaid,String userid,ConditionQueryModel conditionQueryModel, int firstResult,int endResult, int pageSize){
		List listsql = new ArrayList();
		String sql = "";
		if(pageSize != -1){ //查询和导出共用
			 sql = "select temp2.* from (select temp1.*, rownum num from (";
		}
		String selectSql =  "select *\n" +
							"  from (select m.process_instance_id,\n" + 
							"               m.theme,\n" + 
							"               m.city,\n" + 
							"               m.country,\n" + 
							"               m.station_name,\n" + 
							"               m.create_time,\n" + 
							"               m.send_time,\n" + 
							"               m.engineer,\n" + 
							"               m.assignee,\n" + 
							"               m.parent_process_instance_id,\n" + 
							"               decode(m.male_guest_state,\n" + 
							"                      2,\n" + 
							"                      (select task_def_key_name\n" + 
							"                         from pnr_act_transfer_office_main\n" + 
							"                        where process_instance_id =\n" + 
							"                              m.parent_process_instance_id),\n" + 
							"                      m.task_def_key_name) as task_def_key_name,\n" + 
							"               decode(m.male_guest_state,\n" + 
							"                      2,\n" + 
							"                      (select task_def_key\n" + 
							"                         from pnr_act_transfer_office_main\n" + 
							"                        where process_instance_id =\n" + 
							"                              m.parent_process_instance_id),\n" + 
							"                      m.task_def_key) as task_def_key,\n" + 
							"                                  decode(m.male_guest_state,\n" + 
							"                                       2,\n" + 
							"                                       (select last_reply_time\n" + 
							"                                          from pnr_act_transfer_office_main\n" + 
							"                                         where process_instance_id =\n" + 
							"                                               m.parent_process_instance_id),\n" + 
							"                                       m.last_reply_time) as last_reply_time\n"+
							"          from pnr_act_transfer_office_main m\n" + 
							"         where m.themeinterface = 'maleGuest'\n" + 
							"           and nvl(m.male_guest_state, '0') in\n" + 
							"               ('0', '2', '3')) t";
		
		String whereSql = " where 1=1 ";
		//开始时间
		if (conditionQueryModel.getSendStartTime() != null && !"".equals(conditionQueryModel.getSendStartTime())) {
			whereSql += "   and t.send_time >= to_date(?, 'yyyy-mm-dd')\n";
			listsql.add(conditionQueryModel.getSendStartTime());
		}
		//结束时间
		if (conditionQueryModel.getSendEndTime() != null && !"".equals(conditionQueryModel.getSendEndTime())) {
			whereSql += "   and t.send_time <= to_date(?, 'yyyy-mm-dd') -- 时间\n";
			listsql.add(conditionQueryModel.getSendEndTime());
		}
		//工单号
		if (conditionQueryModel.getWorkNumber() != null
				&& !conditionQueryModel.getWorkNumber().equals("")) {
			whereSql += " and t.process_instance_id =?";
			listsql.add(conditionQueryModel.getWorkNumber().trim());
		}
		// 当前登录人的区域权限
		if(areaid!=null && !"".equals(areaid)){
//			if((StaticMethod.nullObject2String(areaid)).length()==2){
//				
//			}else
			if((StaticMethod.nullObject2String(areaid)).length()==4){
				whereSql += " and t.city=? ";
				listsql.add(areaid);
			}else if((StaticMethod.nullObject2String(areaid)).length()==6){
				whereSql += " and t.country=? ";
				listsql.add(areaid);
			}
		}
		//地市
		if (conditionQueryModel.getCity() != null && !"".equals(conditionQueryModel.getCity())) {
			whereSql += " and t.city = ?";
			listsql.add(conditionQueryModel.getCity());

		}
		//区县
		if (conditionQueryModel.getCountry() != null && !"".equals(conditionQueryModel.getCountry())) {
			whereSql += " and t.country = ?";
			listsql.add(conditionQueryModel.getCountry());
		}
		//状态
		if (conditionQueryModel.getStatus() != null && !"".equals(conditionQueryModel.getStatus())) {
			whereSql += "   and t.task_def_key = ? -- 状态\n";;
			listsql.add(conditionQueryModel.getStatus());

		}
		
		sql += selectSql
		+ whereSql
		+ " order by to_number(t.process_instance_id) desc" ;
		
		if(pageSize != -1){ //查询和导出共用
			sql += ") temp1 where rownum <=?) temp2 where temp2.num >? ";
			listsql.add(endResult * pageSize);
			listsql.add(firstResult * pageSize);
		}
		
		System.out.println("--------getMaleGuestWorkOrderQueryListsql=" + sql);
		Object[] args = listsql.toArray();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<TaskModel> r = new ArrayList<TaskModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,args);
		for (Map map : list) {
			TaskModel model = new TaskModel();
			//工单号
			if (map.get("process_instance_id") != null && !"".equals(map.get("process_instance_id").toString())) {
				model.setProcessInstanceId(map.get("process_instance_id").toString());
			}
			//工单名称
			if (map.get("theme") != null && !"".equals(map.get("theme"))) {
				model.setTheme(map.get("theme").toString());
			}
			//地市
			if (map.get("city") != null && !"".equals(map.get("city"))) {
				model.setCity(map.get("city").toString());
			}
			//区县
			if (map.get("country") != null&& !"".equals(map.get("country"))) {
				model.setCountry(map.get("country").toString());
			}
			//局站名称
			if (map.get("station_name") != null && !"".equals(map.get("station_name"))) {
				model.setStationName(map.get("station_name").toString());
			}
			//公客发单时间
			if (map.get("create_time") != null) {
				if (!"".equals(map.get("create_time").toString())) {
					try {
						model.setCreateTime(format.parse(map.get("create_time").toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

			}
			//现场发单时间
			if (map.get("send_time") != null) {
				if (!"".equals(map.get("send_time").toString())) {
					try {
						model.setSendTime(format.parse(map.get("send_time").toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

			}
			
			//机线员
			if (map.get("engineer") != null && !"".equals(map.get("engineer").toString())) {
				model.setTempTask(map.get("engineer").toString());
			}
			//所在账号
			if (map.get("assignee") != null && !"".equals(map.get("assignee").toString())) {
				model.setExecutor(map.get("assignee").toString());
			}
			//归集工单号
			if (map.get("parent_process_instance_id") != null && !"".equals(map.get("parent_process_instance_id").toString())) {
				model.setSheetId(map.get("parent_process_instance_id").toString());
			}
			//当前状态
			if (map.get("task_def_key_name") != null && !"".equals(map.get("task_def_key_name").toString())) {
				model.setTaskDefKeyName(map.get("task_def_key_name").toString());
			}
			
			//施工队回单时间
			if (map.get("last_reply_time") != null) {
				if (!"".equals(map.get("last_reply_time").toString())) {
					try {
						model.setLastReplyTime(format.parse(map.get("last_reply_time").toString()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

			}

			r.add(model);
		}
		return r;
	}
}

