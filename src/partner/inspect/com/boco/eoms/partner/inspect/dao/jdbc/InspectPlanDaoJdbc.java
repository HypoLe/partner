package com.boco.eoms.partner.inspect.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import utils.PartnerPrivUtils;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;
import com.boco.eoms.partner.inspect.dao.IInspectPlanDaoJdbc;
import com.boco.eoms.partner.inspect.model.InspectPlanRes;
import com.boco.eoms.partner.inspect.util.InspectConstants;
import com.boco.eoms.partner.inspect.webapp.form.InspectPlanResCountFrom;
import com.boco.eoms.partner.inspect.webapp.form.InspectPlanResCountFromNew;
import com.boco.eoms.partner.inspect.webapp.form.InspectPlanResCountFromSpecialty;
import com.google.common.collect.Lists;

/**
 * Description: Copyright: Copyright (c)2012 Company: BOCO
 * 
 * @author: Liuchang
 * @version: 1.0 Create at: Jul 12, 2012 2:50:58 PM
 */
public class InspectPlanDaoJdbc extends JdbcDaoSupport implements
		IInspectPlanDaoJdbc {

	/**
	 * 产生巡检资源
	 * 
	 * @param cycle
	 *            周期
	 * @param city
	 *            地市
	 * @param cycleStartTime
	 *            周期开始时间
	 * @param cycleEndTime
	 *            周期结束时间
	 * @param createTime
	 *            资源产生日期
	 */
	public void generateInspectPlanRes(String cycle, String city,
			String cycleStartTime, String cycleEndTime, String createTime) {
		cycleStartTime = CommonSqlHelper.formatDateTime(cycleStartTime);
		cycleEndTime = CommonSqlHelper.formatDateTime(cycleEndTime);

		String executeObjectNotEmpty = CommonSqlHelper
				.formatNotEmpty("c.execute_object");
		String executeDeptNotEmpty = CommonSqlHelper
				.formatNotEmpty("c.execute_Dept");

		String whereStr = "";
		if (InspectConstants.getSwitchConfig().isOpenTransLineInspect()) { // 打开线路巡检
			whereStr = " and c.specialty<>'1122502' ";
		}
		String sql = "insert into pnr_inspect_plan_res (id,res_cfg_id,specialty,res_type,res_name,res_level,city,"
				+ "country,inspect_cycle,cycle_start_time,cycle_end_time,execute_object,execute_dept,"
				+ "execute_type,change_state,res_longitude, res_latitude,end_longitude,end_latitude,"
				+ "create_time,force_assign,inspect_State,item_num,template_Id,last_inspect_Time,eographical_environment,region_type) "
				+ "select seq_pnr_inspect_plan_res.NEXTVAL,c.id,c.specialty,c.res_type,c.res_name,"
				+ "c.res_level,c.city,c.country,c.inspect_cycle,"
				+ cycleStartTime
				+ ","
				+ cycleEndTime
				+ ","
				+ "c.execute_object,c.execute_dept,c.execute_type,0,c.res_longitude, c.res_latitude,"
				+ "c.end_longitude,c.end_latitude,'"
				+ createTime
				+ "',0,0,t.item_num,t.id,c.last_inspect_Time,c.eographical_environment,c.region_type "
				+ "from pnr_res_config c left join pnr_inspect_template t on c.res_type=t.res_type "
				+ whereStr
				+ " "
				+ "where c.city='"
				+ city
				+ "' and c.inspect_cycle='"
				+ cycle
				+ "' and t.status=1 "
				+ "and "
				+ executeObjectNotEmpty
				+ " and "
				+ executeDeptNotEmpty + whereStr;

		System.out.println("产生巡检资源sql:" + sql);
		this.getJdbcTemplate().execute(sql);
	}

	/**
	 * 产生巡检计划执行项
	 * 
	 * @param city
	 *            地市
	 * @param createTime
	 *            资源产生日期
	 */
	public void generateInspectPlanItem(String city, String createTime) {
		String sql = "insert into pnr_inspect_plan_item(id,plan_res_id,res_type,inspect_item,content,input_type,default_value,normal_range,dict_id,inspect_cycle,template_Item_Id,bigitem_name,exception_flag,city,picture_flag,picture_num) "
				+ "select seq_pnr_inspect_plan_item.NEXTVAL,r.id,r.res_type,i.inspect_item,i.inspect_content,i.input_type,i.default_value,i.normal_range,i.dict_id,i.cycle,i.id,i.bigitem_name,1,'"
				+ city
				+ "',i.picture_flag,i.picture_num "
				+ "from pnr_inspect_template_item i "
				+ "left join pnr_inspect_plan_res r "
				+ "on i.template_Id=r.template_Id "
				+ "where  r.city='"
				+ city
				+ "' and r.create_time='" + createTime + "' and i.status=1";
		this.getJdbcTemplate().execute(sql);
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> findResConfigAllCity() {
		String sql = "select city from  pnr_res_config where tl_inspect_flag='0' group by city";
		return this.getJdbcTemplate().queryForList(sql);
	}

	/**
	 * 按地市、年月查询相关计划
	 * 
	 * @param city
	 * @param year
	 * @param month
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> findPlanIdWithPnrDept(String city,
			String year, String month) {
		String sql = "select p.id as id from pnr_inspect_plan_main p "
				+ "left join pnr_dept d on p.partner_dept_id=d.id "
				+ "where d.area_id like '" + city + "%' and p.year='" + year
				+ "' and p.month='" + month + "' "
				+ "and p.status=1 and p.res_Config=1 and p.approve_Status=1 and p.copy_flag =1";
		return this.getJdbcTemplate().queryForList(sql);
	}

	/**
	 * 根据上月计划批量产生新的计划
	 * 
	 * @param idList
	 * @param year
	 * @param month
	 */
	public void savePlanByOldPlanBatch(final List<Map<String, Object>> idList,
			String year, String month) {
		String now = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
		now = CommonSqlHelper.formatDateTime(now);

		String insertSql = "insert into pnr_inspect_plan_main (id,plan_name,specialty,partner_dept_id,"
				+ "dept_Mag_Id,content,creator,create_time,approve_status,status,res_Config,year,month,copy_flag,creator_dept_id) ";
		List<String> sqlList = Lists.newArrayList();
		for (Map<String, Object> map : idList) {
			String selectSql = "select '"
					+ map.get("newPlanId")
					+ "',plan_name,specialty,partner_dept_id,"
					+ "dept_Mag_Id,content,creator,"
					+ now
					+ ",3,1,0,'"
					+ year
					+ "','"
					+ month
					+ "', 1,creator_dept_id  from pnr_inspect_plan_main where id='"
					+ map.get("id") + "' and copy_flag=1";
			String sql = insertSql + selectSql;
			sqlList.add(sql);
		}
		String[] sqlArray = new String[sqlList.size()];
		sqlList.toArray(sqlArray);		
		getJdbcTemplate().batchUpdate(sqlArray);
	}

	/**
	 * 根据上月计划关联的资源将本月新产生的且必须本月执行的资源关联到本月计划
	 * 
	 * @param idList
	 * @param maxCycleEndTime
	 *            周期范围最大值 //本月最后一天
	 */
	public void updatePlanResAssignCurrentPlan(
			final List<Map<String, Object>> idList, String maxCycleEndTime) {
		maxCycleEndTime = CommonSqlHelper.formatDateTime(maxCycleEndTime);

		String sql = "update pnr_inspect_plan_res  set plan_id=?,force_assign=1 "
				+ "where res_cfg_id in (select r2.res_cfg_id from pnr_inspect_plan_res r2 where r2.plan_id=?) "
				+ "and (plan_id is null or plan_id = '' )"
				+ "and cycle_end_time<=" + maxCycleEndTime;
		getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			public int getBatchSize() {
				return idList.size();
			}

			public void setValues(PreparedStatement ps, int i)
					throws SQLException {
				Map<String, Object> idMap = idList.get(i);
				ps.setString(1, idMap.get("newPlanId").toString());
				ps.setString(2, idMap.get("id").toString());
			}
		});
	}

	/**
	 * 将本月必须执行的巡检资源关联到合适的计划上
	 * 
	 * @param year
	 * @param month
	 * @param minCycleEndTime 周期结束日期最早为本月第一天
	 * @param maxCycleEndTime 周期结束日期最晚为本月最后一天
	 */
	public void updatePlanResForceAssignMatchedPlan(String year, String month,
			String minCycleEndTime, String maxCycleEndTime) {
		int countyDeptLength = PartnerPrivUtils.getCountyDeptLength(); // 获取区县代维公司部门长度（陕西=7）
		minCycleEndTime = CommonSqlHelper.formatDateTime(minCycleEndTime);
		maxCycleEndTime = CommonSqlHelper.formatDateTime(maxCycleEndTime);

		String subSql = "select id from pnr_inspect_plan_main ma where  ma.year='"
				+ year
				+ "' and ma.month='"
				+ month
				+ "' and ma.dept_mag_id = substr(pnr_inspect_plan_res.execute_dept,0,"
				+ countyDeptLength
				+ ") and ma.specialty=pnr_inspect_plan_res.specialty ";
	/*	String sql = "update pnr_inspect_plan_res set plan_id = (" + subSql
				+ "),force_assign=1  " + "where exists (" + subSql
				+ ") and cycle_end_time>=" + minCycleEndTime
				+ " and cycle_end_time<=" + maxCycleEndTime
				+ " and (plan_id ='' or plan_id is null)";*/
		//2013-08-28改动
		String sql = "update pnr_inspect_plan_res set plan_id = (" + subSql
		+ "),force_assign=1  " + "where exists (" + subSql
		+ ") and cycle_end_time>=" + minCycleEndTime
		+ " and inspect_state=0"
		+ " and (plan_id ='' or plan_id is null)";
				
		this.getJdbcTemplate().execute(sql);
	}

	/**
	 * 更新未细化巡检时间的巡检计划资源的巡检开始、结束时间
	 * 
	 * @param planId
	 * @param planStartTime
	 *            巡检开始时间 
	 * @param planEndTime
	 *            巡检结束时间
	 * @param currentMonth
	 *            当前月
	 */
	public void updateInspectPlanResPlanTime(String planId,
			String planStartTime, String planEndTime, String currentMonth) {
		String timeWhere = " and (plan_Start_Time='' or plan_Start_Time is null) and (plan_End_Time='' or plan_End_Time is null)";

//		String cycleEndTimeMonthSql = CommonSqlHelper.getDateInfo(
//				"cycle_End_Time", "month");
		String planStartTimeSql = CommonSqlHelper.formatDateTime(planStartTime);
		String planEndTimeSql = CommonSqlHelper.formatDateTime(planEndTime);

		// 更新周期为周且没有跨月的资源的巡检开始、结束时间与周期开始、结束时间相同
		/*String weekSql = "update pnr_Inspect_Plan_Res set plan_Start_Time=cycle_Start_Time,plan_End_Time=cycle_End_Time "
				+ "where plan_Id='"
				+ planId
				+ "' and inspect_Cycle='"
				+ InspectConstants.PERIOD_OF_WEEK
				+ "' and "
				+ cycleEndTimeMonthSql + "=" + currentMonth + timeWhere;
		getJdbcTemplate().execute(weekSql);*/

		// 更新周期为周且跨月的资源的巡检开始、结束时间与周期开始、本月月末时间相同
		/*String weekSql2 = "update pnr_Inspect_Plan_Res set plan_Start_Time=cycle_Start_Time,plan_End_Time="
				+ planEndTimeSql
				+ " "
				+ "where plan_Id='"
				+ planId
				+ "' and inspect_Cycle='"
				+ InspectConstants.PERIOD_OF_WEEK
				+ "' and "
				+ cycleEndTimeMonthSql
				+ ">"
				+ currentMonth
				+ timeWhere;
		getJdbcTemplate().execute(weekSql2);*/

		// 更新周期不为周的资源的巡检开始、结束时间与给定开始、结束时间相同(一般为月初、月末)
	/*	String noWeekSql = "update pnr_Inspect_Plan_Res set plan_Start_Time="
				+ planStartTimeSql + ",plan_End_Time=" + planEndTimeSql
				+ " where plan_Id='" + planId + "' and inspect_Cycle<>'"
				+ InspectConstants.PERIOD_OF_WEEK + "' " + timeWhere;
		getJdbcTemplate().execute(noWeekSql);*/
		
		//修改计划开始，结束时间！
		
		String noWeekSql = "update pnr_Inspect_Plan_Res set plan_Start_Time=cycle_Start_Time,plan_End_Time=cycle_End_Time"
			+ " where plan_Id='" + planId + "'" +timeWhere ;
		getJdbcTemplate().execute(noWeekSql);
	}

	/**
	 * 更新巡检计划关联的资源数目
	 * 
	 * @param year
	 * @param month
	 */
	public void updatePlanResNum(String year, String month) {
		// 更新关联的资源数目
		String resNumSql = "update pnr_inspect_plan_main set res_num=(select count(*) from pnr_inspect_plan_res r where pnr_inspect_plan_main.id=r.plan_id) "
				+ "where year='" + year + "' and month='" + month + "'";
		// 更新是否关联资源
		String resCfgSql = "update pnr_inspect_plan_main set res_config=(case when res_num>0 then 1 else 0 end ) "
				+ "where year='" + year + "' and month='" + month + "'";

		getJdbcTemplate().execute(resNumSql);
		getJdbcTemplate().execute(resCfgSql);
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, String>> findSystemSubRole(String roleId,
			String city, String country) {
		String sql = "select id,subrolename from taw_system_sub_role where roleid='"
				+ roleId
				+ "' and deleted=0 and (deptid = '"
				+ city
				+ "' or deptid = '" + country + "')";
		return this.getJdbcTemplate().queryForList(sql);
	}

	@SuppressWarnings("unchecked")
	public void updateInspectPlanResDoneNum() {
		String current = CommonSqlHelper.getCurrentDateTimeFunc();
		LocalDate date = new LocalDate();
		String sql = "select nvl(planCount,0) as planCount,id from pnr_inspect_plan_main xxx left join "
				+ "(select count(*) as planCount,plan_id from pnr_inspect_plan_res where  plan_end_time<="
				+ current
				+ " group by plan_id)yyy "
				+ "on xxx.id=yyy.plan_id where xxx.status=1 and month="
				+ date.getMonthOfYear() + " and year=" + date.getYear() + "";
		List list = getJdbcTemplate().queryForList(sql);
		List<String> sqlList = Lists.newArrayList();
		for (int i = 0; i < list.size(); i++) {
			String num = ((Map) list.get(i)).get("planCount") + "";
			String id = ((Map) list.get(i)).get("id") + "";
			String sql2 = "update pnr_inspect_plan_main set res_plan_done_num="
					+ num + " where id='" + id + "'";
			sqlList.add(sql2);
		}
		String[] sqlArray = new String[sqlList.size()];
		sqlList.toArray(sqlArray);
		getJdbcTemplate().batchUpdate(sqlArray);
	}

	/**
	 * 更新资源的超时未完成状态 inspectState巡检状态为 2超时未关联未完成或者 3超时已关联未完成
	 * 
	 * @param city
	 * @param dateTime
	 */
	public void updateInspectPlanResState(String city, String dateTime) {
		dateTime = CommonSqlHelper.formatDateTime(dateTime);

		String sql = "update pnr_inspect_plan_res set inspect_state = "
				+ "(case when (plan_id is null or plan_id='') then 2 else 3 end ) "
				+ "where plan_end_time<=" + dateTime
				+ " and inspect_state=0 and city='" + city + "'";
		getJdbcTemplate().execute(sql);
	}

	/**
	 * 保存巡检模板历史表
	 * 
	 * @param year
	 * @param month
	 */
	public void saveInspectTemplateHis(String year, String month) {
		String sql = "insert into pnr_inspect_template_his (id,template_id,templatename,dept,specialty,content,add_time,add_user,res_type,status,item_num,year,month) "
				+ " select seq_inspect_template_his.NEXTVAL,id,templatename,dept,specialty,content,add_time,add_user,res_type,status,item_num,"
				+ year
				+ ","
				+ month
				+ " from "
				+ "pnr_inspect_template  where status=1";
		String sql2 = "insert into pnr_inspect_template_bitem_his (id,inspect_template_bigitem_id,template_id,name,order_no,item_num,status,month,year)"
				+ " select seq_inspect_template_his.NEXTVAL,id,template_id,name,nvl(order_no,0),item_num,status,"
				+ month
				+ ","
				+ year
				+ " "
				+ "from pnr_inspect_template_bigitem where status=1 and item_num>0";

		String sql3 = "insert into pnr_inspect_template_item_his (id,pnr_inspect_template_item_id,template_id,res_type,inspect_item,inspect_content,input_type,default_value,normal_range,"
				+ "dict_id,cycle,add_time,add_user,status,bigitem_name,bigitem_id,order_no,month,year)select seq_inspect_template_his.NEXTVAL,id,template_id,res_type,inspect_item,"
				+ "inspect_content,input_type,default_value,normal_range,dict_id,cycle,add_time,add_user,status,bigitem_name,bigitem_id,order_no,"
				+ month
				+ ","
				+ year
				+ " "
				+ "from pnr_inspect_template_item where status=1";
		getJdbcTemplate().execute(sql);

		this.getJdbcTemplate().execute(sql2);

		this.getJdbcTemplate().execute(sql3);
	}

	/**
	 * 无效资源查询
	 * 
	 * @param whereStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPlanErrorDistanceRes(String whereStr, int offset,
			int pagesize) {
		String sql = "select a.id,a.res_name,a.res_level,a.specialty,a.plan_id,a.res_longitude,a.res_latitude,a.city,a.country,a.res_type,a.execute_object,"
				+ "a.plan_start_time,a.plan_end_time,a.error_distance,b.sign_longitude,b.sign_latitude,nvl(c.distance_resource,0) as distance_resource ,c.deleteflag "
				+ "from pnr_inspect_plan_res  a join pnr_inspect_track b on a.id=b.plan_res_id "
				+ whereStr
				+ " "
				+ "join pnr_inspect_error_distance c on a.res_type = c.distance_resource and c.deleteflag <>1 "
				+ "into temp tmp1 with no log";
		// String sql2
		// ="select skip "+offset+" first "+pagesize+" * from tmp1 where error_distance>distance_resource";
		String sql2 = "select t.* from tmp1 t where error_distance>distance_resource";
		sql2 = CommonSqlHelper.formatPageSql(sql, offset, pagesize);

		String sql3 = " select count(*) from tmp1 where error_distance>distance_resource";
		try {
			this.getJdbcTemplate().execute(sql);
		} catch (Exception e) {
		}
		String sql4 = "drop table tmp1";
		int total = getJdbcTemplate().queryForInt(sql3);
		List list = new ArrayList();
		list.add(total);
		List resList = this.getJdbcTemplate().queryForList(sql2);
		list.add(resList);
		getJdbcTemplate().execute(sql4);
		return list;

	}

	/**
	 * 突发资源和巡检项的生成
	 */
	public void generatePlanRes(String ids) {
		LocalDate date = new LocalDate();
		String currentTime = StaticMethod.getCurrentDateTime();
		String whereStr = "";
		if (InspectConstants.getSwitchConfig().isOpenTransLineInspect()) { // 打开线路巡检
			whereStr = " and c.specialty<>'1122502' ";
		}
		String sql = "insert into pnr_inspect_plan_res (id,res_cfg_id,specialty,res_type,res_name,res_level,city,"
				+ "country,inspect_cycle,cycle_start_time,cycle_end_time,execute_object,execute_dept,"
				+ "execute_type,change_state,res_longitude, res_latitude,end_longitude,end_latitude,"
				+ "create_time,force_assign,inspect_State,item_num,template_Id,last_inspect_Time,burst_flag) "
				+ "select seq_pnr_inspect_plan_res.NEXTVAL,c.id,c.specialty,c.res_type,c.res_name,"
				+ "c.res_level,c.city,c.country,'month','"
				+ date.dayOfMonth().withMinimumValue()
				+ " 00:00:00','"
				+ date.dayOfYear().withMaximumValue()
				+ " 23:59:59',"
				+ "c.execute_object,c.execute_dept,c.execute_type,0,c.res_longitude, c.res_latitude,"
				+ "c.end_longitude,c.end_latitude,'"
				+ currentTime
				+ "',0,0,t.item_num,t.id,c.last_inspect_Time,1 "
				+ "from pnr_res_config c left join pnr_inspect_template t on and c.res_type=t.res_type and c.id in ("
				+ ids
				+ ") "
				+ whereStr
				+ " "
				+ "where t.status=1 "
				+ whereStr
				+ " ";
		String sql2 = "insert into pnr_inspect_plan_item(id,plan_res_id,res_type,inspect_item,content,input_type,default_value,normal_range,dict_id,inspect_cycle,template_Item_Id,bigitem_name,exception_flag,city) "
				+ "select seq_pnr_inspect_plan_item.NEXTVAL,r.id,r.res_type,i.inspect_item,i.inspect_content,i.input_type,i.default_value,i.normal_range,i.dict_id,i.cycle,i.id,i.bigitem_name,0,r.city "
				+ "from pnr_inspect_template_item i "
				+ "left join pnr_inspect_plan_res r "
				+ "on i.template_Id=r.template_Id and r.create_time='"
				+ currentTime + "'" + "where i.status=1";
		this.getJdbcTemplate().execute(sql);
		this.getJdbcTemplate().execute(sql2);
	}

	/**
	 * 查询所有的资源（与计划关联）
	 * 
	 * @param whereStr
	 * @param offset
	 * @param pagesize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getAllWaitInspectPlanRes(String whereStr, int offset,
			int pagesize) {
		List list1 = new ArrayList();
		// String sql1
		// ="select skip "+offset+" first "+pagesize+" res.res_name,res.plan_id,res.id,res.specialty,res.res_type,res.res_level,"
		// +
		// "res.city,res.country,res.inspect_user,dept_mag_id,res.execute_object,res.cycle_start_time,res.cycle_end_time,res.inspect_time"
		// +
		// " from pnr_inspect_plan_main main join pnr_inspect_plan_res res " +
		// "on main.id=res.plan_id and main.approve_status=1 and res.inspect_state=1 where (res.satisfaction is null or res.satisfaction='' ) "+whereStr;
		String sql1 = "select res.res_name,res.plan_id,res.id,res.specialty,res.res_type,res.res_level,"
				+ "res.city,res.country,res.inspect_user,dept_mag_id,res.execute_object,res.cycle_start_time,res.cycle_end_time,res.inspect_time"
				+ " from pnr_inspect_plan_main main join pnr_inspect_plan_res res "
				+ "on main.id=res.plan_id and main.approve_status=1 and res.inspect_state=1 where (res.satisfaction is null or res.satisfaction='' ) "
				+ whereStr;
		sql1 = CommonSqlHelper.formatPageSql(sql1, offset, pagesize);

		String sql2 = "select count(*) from pnr_inspect_plan_main main join pnr_inspect_plan_res res "
				+ "on main.id=res.plan_id and main.approve_status=1 and res.inspect_state=1 where (res.satisfaction is null or res.satisfaction='' ) "
				+ whereStr;
		int total = this.getJdbcTemplate().queryForInt(sql2);
		List list2 = this.getJdbcTemplate().queryForList(sql1);
		list1.add(total);
		list1.add(list2);
		return list1;

	}

	/**
	 * 查询已质检的资源
	 * 
	 * @param year
	 * @param month
	 * @param whereStr
	 * @param offset
	 * @param pagesize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getAllAleradyInspectPlanRes(int year, int month,
			String whereStr, int offset, int pagesize) {
		List list1 = new ArrayList();
		String selectSql = "select res.res_name,res.plan_id,res.id,res.specialty,res.res_type,res.res_level,"
				+ "res.city,res.country,res.inspect_user,dept_mag_id,res.execute_object,res.cycle_start_time,res.cycle_end_time,res.inspect_time";
		String fromSql = " from pnr_inspect_plan_main main join pnr_inspect_plan_res res "
				+ "on main.id=res.plan_id and main.approve_status=1 and res.inspect_state=1 and main.year='"
				+ year
				+ "' and main.month='"
				+ month
				+ "' where "
				+ CommonSqlHelper.formatNotEmpty("res.satisfaction")
				+ "  "
				+ whereStr;
		String sql1 = selectSql + fromSql;
		sql1 = CommonSqlHelper.formatPageSql(sql1, offset, pagesize);
		String sql2 = "select count(*) " + fromSql;
		int total = this.getJdbcTemplate().queryForInt(sql2);
		List list2 = this.getJdbcTemplate().queryForList(sql1);
		list1.add(total);
		list1.add(list2);
		return list1;
	}

	// @Override
	public List<InspectPlanResCountFrom> countInspectPlanRes(int countType,String isPersonnel,String personnelDeptId) {
		List<InspectPlanResCountFrom> list1 = new ArrayList<InspectPlanResCountFrom>();
		
		String selectSql = ",count(id) as resNo,sum( decode(p.inspect_state,1,1,0)) as completedNo ,sum(decode(p.inspect_state,0,1,0)) as unfinishedNo,sum(decode(p.exception_flag,0,1,0)) as exceptionNo from pnr_inspect_plan_res p where plan_Start_Time<sysdate  and plan_End_Time>=sysdate ";
		String beginSelectSql = "select  p.city as cityId";
		String endSelectSql = "group by p.city";
		if (isPersonnel.equals("y")) {// 说明是代维公司人查看，默认为只能查看自己部门以及其下部门的，所以应该是executeDept
			// like
			selectSql += "  and execute_Dept like '" + personnelDeptId+"%'";
		}
		if (countType == 2) {
			beginSelectSql += ",p.country as countryId";
			endSelectSql += ",p.country";
		}
		selectSql = beginSelectSql + selectSql + endSelectSql;
		List<Map> list2 = this.getJdbcTemplate().queryForList(selectSql);
		if (list2 != null) {
			Iterator<Map> iterator = list2.iterator();
			while (iterator.hasNext()) {
				Map o = iterator.next();
				InspectPlanResCountFrom inspectPlanResCountFrom = new InspectPlanResCountFrom();

				inspectPlanResCountFrom.setCityId(o.get("cityId").toString());
				if (o.size() == 6) {
					inspectPlanResCountFrom.setCountryId(o.get("countryId")
							.toString());
				}
				inspectPlanResCountFrom.setResNo(Integer.parseInt(o
						.get("resNo").toString()));
				inspectPlanResCountFrom.setCompletedNo(Integer.parseInt(o.get(
						"completedNo").toString()));
				inspectPlanResCountFrom.setUnfinishedNo(Integer.parseInt(o.get(
						"unfinishedNo").toString()));
				inspectPlanResCountFrom.setExceptionNo(Integer.parseInt(o.get(
						"exceptionNo").toString()));

				list1.add(inspectPlanResCountFrom);
			}
		}
		return list1;
	}

	// @Override
	public List<InspectPlanResCountFrom> countInspectPlanRes(int countType,
			String areaId,String isPersonnel,String personnelDeptId) {
		List<InspectPlanResCountFrom> list1 = new ArrayList<InspectPlanResCountFrom>();
		String selectSql = ",count(id) as resNo,sum( decode(p.inspect_state,1,1,0)) as completedNo ,sum(decode(p.inspect_state,0,1,0)) as unfinishedNo,sum(decode(p.exception_flag,0,1,0)) as exceptionNo from pnr_inspect_plan_res p where plan_Start_Time<sysdate  and plan_End_Time>=sysdate and country="
				+ areaId;
		String beginSelectSql = "select  p.city as cityId,p.country as countryId,p.execute_object as deptId";
		String endSelectSql = "group by p.city,p.country,p.execute_object";
		if (isPersonnel.equals("y")) {// 说明是代维公司人查看，默认为只能查看自己部门以及其下部门的，所以应该是executeDept
			// like
			selectSql += "  and execute_Dept like '" + personnelDeptId+"%'";
		}
		selectSql = beginSelectSql + selectSql + endSelectSql;
		List<Map<String, Object>> list2 = this.getJdbcTemplate().queryForList(
				selectSql);
		if (list2 != null) {
			Iterator<Map<String, Object>> iterator = list2.iterator();
			while (iterator.hasNext()) {
				Map<String, Object> o = iterator.next();
				InspectPlanResCountFrom inspectPlanResCountFrom = new InspectPlanResCountFrom();
				inspectPlanResCountFrom.setCityId(o.get("cityId").toString());
				inspectPlanResCountFrom.setDeptId(o.get("deptId").toString());
				inspectPlanResCountFrom.setResNo(Integer.parseInt(o
						.get("resNo").toString()));
				inspectPlanResCountFrom.setCompletedNo(Integer.parseInt(o.get(
						"completedNo").toString()));
				inspectPlanResCountFrom.setUnfinishedNo(Integer.parseInt(o.get(
						"unfinishedNo").toString()));
				inspectPlanResCountFrom.setExceptionNo(Integer.parseInt(o.get(
						"exceptionNo").toString()));
				list1.add(inspectPlanResCountFrom);
			}
		}
		return list1;
	}
	
	public List<InspectPlanResCountFromSpecialty> countInspectPlanResSpecialty(String countType,
			String areaId,String specialty,String person,String year,String month) {
		List<InspectPlanResCountFromSpecialty> list1 = new ArrayList<InspectPlanResCountFromSpecialty>();
		
		System.out.println(countType+"   "+areaId+"   "+specialty+"  "+person);
		String wheresql = "";
		if("1".equals(countType)){
			wheresql = "and res.city = "+areaId;
		}else if("2".equals(countType)){
			wheresql = "and res.country = "+areaId;
		}else if("3".equals(countType)){
			wheresql ="and res.country = "+areaId+" and decode(res.inspect_user,null,'无',res.inspect_user) = '"+person+"'";
		}
		
		String sql = "select res.res_name res_name,dic3.dictname res_specialty,dic2.dictname res_type,dic1.dictname res_level,city1.areaname city_name,city2.areaname country_name" +
				" from pnr_inspect_plan_res res,taw_system_dicttype dic1,taw_system_dicttype dic2," +
				" taw_system_dicttype dic3,taw_system_area city1,taw_system_area city2,pnr_inspect_plan_main m" +
				" where res.res_level = dic1.dictid" +
				" and res.res_type = dic2.dictid" +
				" and res.specialty = dic3.dictid" +
				" and res.city = city1.areaid" +
				" and res.country = city2.areaid" +
				" and res.plan_id is not null" +
                " and res.plan_id = m.id" +
				" and m.year = '"+year+"'" +
				" and m.month = '"+month+"'" +
				" and res.inspect_state = '0'" +
				" and res.res_level = "+specialty+" "+wheresql
				;
		
		System.out.println(sql);
		
		List<Map<String, Object>> list2 = this.getJdbcTemplate().queryForList(sql);
		
		if (list2 != null) {
			Iterator<Map<String, Object>> iterator = list2.iterator();
			while (iterator.hasNext()) {
				Map<String, Object> o = iterator.next();
				InspectPlanResCountFromSpecialty inspectPlanResCountFromSpecialty = new InspectPlanResCountFromSpecialty();
				inspectPlanResCountFromSpecialty.setResName(o.get("res_name").toString());
				inspectPlanResCountFromSpecialty.setResSpecialty(o.get("res_specialty").toString());
				inspectPlanResCountFromSpecialty.setResType(o.get("res_type").toString());
				inspectPlanResCountFromSpecialty.setResLevel(o.get("res_level").toString());
				inspectPlanResCountFromSpecialty.setCityName(o.get("city_name").toString());
				inspectPlanResCountFromSpecialty.setCountryName(o.get("country_name").toString());
				
				list1.add(inspectPlanResCountFromSpecialty);
			}
		}
		
		return list1;
	}
	
	
	public List<InspectPlanResCountFromNew> countInspectPlanResNew(String countType,
			String areaId,String isPersonnel,String personnelDeptId,String year,String month) {
		List<InspectPlanResCountFromNew> list1 = new ArrayList<InspectPlanResCountFromNew>();
		String cityLevel = "";
		String wheresql = "";
		String cityId = "";
		System.out.println(year+"   "+month);
		if("1".equals(countType)){
			cityId = "a.city";
	      cityLevel = "c.areaname";
	      wheresql = ",taw_system_area c where a.city = c.areaid and a.plan_id is not null and a.plan_id = m.id and m.year= '"+year+"' and m.month = '"+month+"' ";
		}else if("2".equals(countType)){
			cityId = "a.country";
		  cityLevel = "c.areaname";
		  wheresql = ",taw_system_area c where a.country = c.areaid and a.city = "+areaId+" and a.plan_id is not null and a.plan_id = m.id and m.year= '"+year+"' and m.month = '"+month+"' ";
		}else if("3".equals(countType)){
			cityId = "a.country";
	      cityLevel = "decode(a.inspect_user,null,'无',a.inspect_user)";
	      wheresql = " where a.country = "+areaId+" and a.plan_id is not null and a.plan_id = m.id and m.year= '"+year+"' and m.month = '"+month+"' ";
		}
		
		
		String selectSql = "sum(decode((case when a.res_level = '11225010301' then 'a' end),'a',1,0)) v_stage_a, " +
				"sum(decode((case when a.res_level = '11225010301' and a.inspect_state = '1' then 'w' end),'w',1,0)) v_stage_w," +
				"sum(decode((case when a.res_level = '11225010301' and a.inspect_state = '0' then 'd' end),'d',1,0)) v_stage_d," +
				"sum(decode((case when a.res_level = '11225010302' then 'a' end),'a',1,0)) a_stage_a, " +
				"sum(decode((case when a.res_level = '11225010302' and a.inspect_state = '1' then 'w' end),'w',1,0)) a_stage_w," +
				"sum(decode((case when a.res_level = '11225010302' and a.inspect_state = '0' then 'd' end),'d',1,0)) a_stage_d," +
				"sum(decode((case when a.res_level = '11225010303' then 'a' end),'a',1,0)) b_stage_a, " +
				"sum(decode((case when a.res_level = '11225010303' and a.inspect_state = '1' then 'w' end),'w',1,0)) b_stage_w," +
				"sum(decode((case when a.res_level = '11225010303' and a.inspect_state = '0' then 'd' end),'d',1,0)) b_stage_d," +
				"sum(decode((case when a.res_level = '11225010304' then 'a' end),'a',1,0)) c_stage_a, " +
				"sum(decode((case when a.res_level = '11225010304' and a.inspect_state = '1' then 'w' end),'w',1,0)) c_stage_w," +
				"sum(decode((case when a.res_level = '11225010304' and a.inspect_state = '0' then 'd' end),'d',1,0)) c_stage_d," +
				"sum(decode((case when a.res_level = '11225050201' then 'a' end),'a',1,0)) a_accessNetwork_a, " +
				"sum(decode((case when a.res_level = '11225050201' and a.inspect_state = '1' then 'w' end),'w',1,0)) a_accessNetwork_w," +
				"sum(decode((case when a.res_level = '11225050201' and a.inspect_state = '0' then 'd' end),'d',1,0)) a_accessNetwork_d," +
				"sum(decode((case when a.res_level = '11225050202' then 'a' end),'a',1,0)) b_accessNetwork_a, " +
				"sum(decode((case when a.res_level = '11225050202' and a.inspect_state = '1' then 'w' end),'w',1,0)) b_accessNetwork_w," +
				"sum(decode((case when a.res_level = '11225050202' and a.inspect_state = '0' then 'd' end),'d',1,0)) b_accessNetwork_d," +
				"sum(decode((case when a.res_level = '11225050203' then 'a' end),'a',1,0)) c_accessNetwork_a, " +
				"sum(decode((case when a.res_level = '11225050203' and a.inspect_state = '1' then 'w' end),'w',1,0)) c_accessNetwork_w," +
				"sum(decode((case when a.res_level = '11225050203' and a.inspect_state = '0' then 'd' end),'d',1,0)) c_accessNetwork_d," +
				"sum(decode((case when a.res_level = '11225040402' then 'a' end),'a',1,0)) j_tower_a, " +
				"sum(decode((case when a.res_level = '11225040402' and a.inspect_state = '1' then 'w' end),'w',1,0)) j_tower_w," +
				"sum(decode((case when a.res_level = '11225040402' and a.inspect_state = '0' then 'd' end),'d',1,0)) j_tower_d," +
				"sum(decode((case when a.res_level = '11225040401' then 'a' end),'a',1,0)) y_tower_a, " +
				"sum(decode((case when a.res_level = '11225040401' and a.inspect_state = '1' then 'w' end),'w',1,0)) y_tower_w," +
				"sum(decode((case when a.res_level = '11225040401' and a.inspect_state = '0' then 'd' end),'d',1,0)) y_tower_d," +
				"sum(decode((case when a.res_level = '11225030201' then 'a' end),'a',1,0)) z_distribution_a, " +
				"sum(decode((case when a.res_level = '11225030201' and a.inspect_state = '1' then 'w' end),'w',1,0)) z_distribution_w," +
				"sum(decode((case when a.res_level = '11225030201' and a.inspect_state = '0' then 'd' end),'d',1,0)) z_distribution_d," +
				"sum(decode((case when a.res_level = '11225030202' then 'a' end),'a',1,0)) v_distribution_a, " +
				"sum(decode((case when a.res_level = '11225030202' and a.inspect_state = '1' then 'w' end),'w',1,0)) v_distribution_w," +
				"sum(decode((case when a.res_level = '11225030202' and a.inspect_state = '0' then 'd' end),'d',1,0)) v_distribution_d," +
				"sum(decode((case when a.res_level = '11225030203' then 'a' end),'a',1,0)) a_distribution_a, " +
				"sum(decode((case when a.res_level = '11225030203' and a.inspect_state = '1' then 'w' end),'w',1,0)) a_distribution_w," +
				"sum(decode((case when a.res_level = '11225030203' and a.inspect_state = '0' then 'd' end),'d',1,0)) a_distribution_d," +
				"sum(decode((case when a.res_level = '11225030204' then 'a' end),'a',1,0)) b_distribution_a, " +
				"sum(decode((case when a.res_level = '11225030204' and a.inspect_state = '1' then 'w' end),'w',1,0)) b_distribution_w," +
				"sum(decode((case when a.res_level = '11225030204' and a.inspect_state = '0' then 'd' end),'d',1,0)) b_distribution_d," +
				"sum(decode((case when a.res_level = '11225060101' then 'a' end),'a',1,0)) a_wlan_a, " +
				"sum(decode((case when a.res_level = '11225060101' and a.inspect_state = '1' then 'w' end),'w',1,0)) a_wlan_w," +
				"sum(decode((case when a.res_level = '11225060101' and a.inspect_state = '0' then 'd' end),'d',1,0)) a_wlan_d," +
				"sum(decode((case when a.res_level = '11225060102' then 'a' end),'a',1,0)) b_wlan_a, " +
				"sum(decode((case when a.res_level = '11225060102' and a.inspect_state = '1' then 'w' end),'w',1,0)) b_wlan_w," +
				"sum(decode((case when a.res_level = '11225060102' and a.inspect_state = '0' then 'd' end),'d',1,0)) b_wlan_d," +
				"sum(decode((case when a.res_level = '11225060103' then 'a' end),'a',1,0)) c_wlan_a, " +
				"sum(decode((case when a.res_level = '11225060103' and a.inspect_state = '1' then 'w' end),'w',1,0)) c_wlan_w," +
				"sum(decode((case when a.res_level = '11225060103' and a.inspect_state = '0' then 'd' end),'d',1,0)) c_wlan_d ";
		
		String beginSelectSql = "select "+cityId+" cityid, "+cityLevel+" name, ";
		String endSelectSql = "from pnr_inspect_plan_res a ,pnr_inspect_plan_main m" +
				 wheresql+
				" group by "+cityId+","+cityLevel+" ";
/*		if (isPersonnel.equals("y")) {// 说明是代维公司人查看，默认为只能查看自己部门以及其下部门的，所以应该是executeDept
			// like
			selectSql += "  and execute_Dept like '" + personnelDeptId+"%'";
		}*/
		selectSql = beginSelectSql + selectSql + endSelectSql;
		List<Map<String, Object>> list2 = this.getJdbcTemplate().queryForList(
				selectSql);
		System.out.println(selectSql);
		if (list2 != null) {
			Iterator<Map<String, Object>> iterator = list2.iterator();
			while (iterator.hasNext()) {
				Map<String, Object> o = iterator.next();
				InspectPlanResCountFromNew inspectPlanResCountFromNew = new InspectPlanResCountFromNew();
				inspectPlanResCountFromNew.setCityId(o.get("cityid").toString());
				inspectPlanResCountFromNew.setName(o.get("name").toString());
				inspectPlanResCountFromNew.setV_stage_a(o.get("v_stage_a").toString());
				inspectPlanResCountFromNew.setV_stage_w(o.get("v_stage_w").toString());
				inspectPlanResCountFromNew.setV_stage_d(o.get("v_stage_d").toString());
				inspectPlanResCountFromNew.setA_stage_a(o.get("a_stage_a").toString());
				inspectPlanResCountFromNew.setA_stage_w(o.get("a_stage_w").toString());
				inspectPlanResCountFromNew.setA_stage_d(o.get("a_stage_d").toString());
				inspectPlanResCountFromNew.setB_stage_a(o.get("b_stage_a").toString());
				inspectPlanResCountFromNew.setB_stage_w(o.get("b_stage_w").toString());
				inspectPlanResCountFromNew.setB_stage_d(o.get("b_stage_d").toString());
				inspectPlanResCountFromNew.setC_stage_a(o.get("c_stage_a").toString());
				inspectPlanResCountFromNew.setC_stage_w(o.get("c_stage_w").toString());
				inspectPlanResCountFromNew.setC_stage_d(o.get("c_stage_d").toString());
				inspectPlanResCountFromNew.setA_accessNetwork_a(o.get("a_accessNetwork_a").toString());
				inspectPlanResCountFromNew.setA_accessNetwork_w(o.get("a_accessNetwork_w").toString());
				inspectPlanResCountFromNew.setA_accessNetwork_d(o.get("a_accessNetwork_d").toString());
				inspectPlanResCountFromNew.setB_accessNetwork_a(o.get("b_accessNetwork_a").toString());
				inspectPlanResCountFromNew.setB_accessNetwork_w(o.get("b_accessNetwork_w").toString());
				inspectPlanResCountFromNew.setB_accessNetwork_d(o.get("b_accessNetwork_d").toString());
				inspectPlanResCountFromNew.setC_accessNetwork_a(o.get("c_accessNetwork_a").toString());
				inspectPlanResCountFromNew.setC_accessNetwork_w(o.get("c_accessNetwork_w").toString());
				inspectPlanResCountFromNew.setC_accessNetwork_d(o.get("c_accessNetwork_d").toString());
				inspectPlanResCountFromNew.setJ_tower_a(o.get("j_tower_a").toString());
				inspectPlanResCountFromNew.setJ_tower_w(o.get("j_tower_w").toString());
				inspectPlanResCountFromNew.setJ_tower_d(o.get("j_tower_d").toString());
				inspectPlanResCountFromNew.setY_tower_a(o.get("y_tower_a").toString());
				inspectPlanResCountFromNew.setY_tower_w(o.get("y_tower_w").toString());
				inspectPlanResCountFromNew.setY_tower_d(o.get("y_tower_d").toString());
				inspectPlanResCountFromNew.setZ_distribution_a(o.get("z_distribution_a").toString());
				inspectPlanResCountFromNew.setZ_distribution_w(o.get("z_distribution_w").toString());
				inspectPlanResCountFromNew.setZ_distribution_d(o.get("z_distribution_d").toString());
				inspectPlanResCountFromNew.setV_distribution_a(o.get("v_distribution_a").toString());
				inspectPlanResCountFromNew.setV_distribution_w(o.get("v_distribution_w").toString());
				inspectPlanResCountFromNew.setV_distribution_d(o.get("v_distribution_d").toString());
				inspectPlanResCountFromNew.setA_distribution_a(o.get("a_distribution_a").toString());
				inspectPlanResCountFromNew.setA_distribution_w(o.get("a_distribution_w").toString());
				inspectPlanResCountFromNew.setA_distribution_d(o.get("a_distribution_d").toString());
				inspectPlanResCountFromNew.setB_distribution_a(o.get("b_distribution_a").toString());
				inspectPlanResCountFromNew.setB_distribution_w(o.get("b_distribution_w").toString());
				inspectPlanResCountFromNew.setB_distribution_d(o.get("b_distribution_d").toString());
				inspectPlanResCountFromNew.setA_wlan_a(o.get("a_wlan_a").toString());
				inspectPlanResCountFromNew.setA_wlan_w(o.get("a_wlan_w").toString());
				inspectPlanResCountFromNew.setA_wlan_d(o.get("a_wlan_d").toString());
				inspectPlanResCountFromNew.setB_wlan_a(o.get("b_wlan_a").toString());
				inspectPlanResCountFromNew.setB_wlan_w(o.get("b_wlan_w").toString());
				inspectPlanResCountFromNew.setB_wlan_d(o.get("b_wlan_d").toString());
				inspectPlanResCountFromNew.setC_wlan_a(o.get("c_wlan_a").toString());
				inspectPlanResCountFromNew.setC_wlan_w(o.get("c_wlan_w").toString());
				inspectPlanResCountFromNew.setC_wlan_d(o.get("c_wlan_d").toString());
				
				list1.add(inspectPlanResCountFromNew);
			}
		}
		return list1;
	}

	// @Override
	public List<InspectPlanResCountFrom> countInspectPlanResException(
			int countType,String isPersonnel,String personnelDeptId) {
		List<InspectPlanResCountFrom> list1 = new ArrayList<InspectPlanResCountFrom>();
		String selectSql = ",count(id) as resNo,sum(decode(p.exception_flag,0,1,0)) as exceptionNo,sum(decode(p.is_handle, 1, 1, 0)) as handleNo from pnr_inspect_plan_res p where plan_Start_Time<sysdate  and plan_End_Time>=sysdate ";
		String beginSelectSql = "select  p.city as cityId";
		String endSelectSql = "group by p.city";
		if (countType == 2) {
			beginSelectSql += ",p.country as countryId";
			endSelectSql += ",p.country";
		}
		if (isPersonnel.equals("y")) {// 说明是代维公司人查看，默认为只能查看自己部门以及其下部门的，所以应该是executeDept
			// like
			selectSql += "  and execute_Dept like '" + personnelDeptId+"%'";
		}
		selectSql = beginSelectSql + selectSql + endSelectSql;
		List<Map> list2 = this.getJdbcTemplate().queryForList(selectSql);
		if (list2 != null) {
			Iterator<Map> iterator = list2.iterator();
			while (iterator.hasNext()) {
				Map o = iterator.next();
				InspectPlanResCountFrom inspectPlanResCountFrom = new InspectPlanResCountFrom();
				inspectPlanResCountFrom.setCityId(o.get("cityId").toString());
				if (o.size() == 5) {
					inspectPlanResCountFrom.setCountryId(o.get("countryId")
							.toString());
				}
				inspectPlanResCountFrom.setResNo(Integer.parseInt(o
						.get("resNo").toString()));
				inspectPlanResCountFrom.setExceptionNo(Integer.parseInt(o.get(
						"exceptionNo").toString()));
				inspectPlanResCountFrom.setHandleNo(Integer.parseInt(o.get(
						"handleNo").toString()));
				inspectPlanResCountFrom.setUnhandledNo(inspectPlanResCountFrom
						.getExceptionNo()
						- inspectPlanResCountFrom.getHandleNo());
				list1.add(inspectPlanResCountFrom);
			}
		}
		return list1;
	}

	// @Override
	public List<InspectPlanResCountFrom> countInspectPlanResException(
			int countType, String areaId,String isPersonnel,String personnelDeptId) {
		List<InspectPlanResCountFrom> list1 = new ArrayList<InspectPlanResCountFrom>();
		String selectSql = ",count(id) as resNo,sum(decode(p.exception_flag,0,1,0)) as exceptionNo,sum(decode(p.is_handle, 1, 1, 0)) as handleNo from pnr_inspect_plan_res p where plan_Start_Time<sysdate  and plan_End_Time>=sysdate and country="
				+ areaId;
		String beginSelectSql = "select  p.city as cityId,p.country as countryId,p.execute_object as deptId";
		String endSelectSql = "group by p.city,p.country,p.execute_object";
		if (isPersonnel.equals("y")) {// 说明是代维公司人查看，默认为只能查看自己部门以及其下部门的，所以应该是executeDept
			// like
			selectSql += "  and execute_Dept like '" + personnelDeptId+"%'";
		}
		selectSql = beginSelectSql + selectSql + endSelectSql;
		List<Map<String, Object>> list2 = this.getJdbcTemplate().queryForList(
				selectSql);
		if (list2 != null) {
			Iterator<Map<String, Object>> iterator = list2.iterator();
			while (iterator.hasNext()) {
				Map<String, Object> o = iterator.next();
				InspectPlanResCountFrom inspectPlanResCountFrom = new InspectPlanResCountFrom();
				inspectPlanResCountFrom.setCityId(o.get("cityId").toString());
				inspectPlanResCountFrom.setDeptId(o.get("deptId").toString());
				inspectPlanResCountFrom.setResNo(Integer.parseInt(o
						.get("resNo").toString()));
				inspectPlanResCountFrom.setExceptionNo(Integer.parseInt(o.get(
						"exceptionNo").toString()));
				inspectPlanResCountFrom.setHandleNo(Integer.parseInt(o.get(
						"handleNo").toString()));
				inspectPlanResCountFrom.setUnhandledNo(inspectPlanResCountFrom
						.getExceptionNo()
						- inspectPlanResCountFrom.getHandleNo());
				list1.add(inspectPlanResCountFrom);
			}
		}
		return list1;
	}
	
	public void updateBeforeMonthInspectPlan(String year,String month,String day){
		
		String nowdate=year+"-"+month+"-"+day+" 00:00:00";		
//		String befMonString=(Integer.parseInt(month)-1)+"";
		
		StringBuffer sql = new StringBuffer();
		
	/*	sql.append(" update pnr_inspect_plan_main ma set ma.month='").append(month).append("'");
		sql.append(" where ma.id in (");
		sql.append(" select res.plan_id from pnr_inspect_plan_res res");
		sql.append(" where res.inspect_state=0");
		sql.append(" and res.cycle_end_time>=to_date('").append(nowdate).append("','yyyy-mm-dd')");
		sql.append(" and res.plan_id is not null");
		sql.append(" group by res.plan_id)");
		sql.append(" and ma.year='").append(year).append("'");
		sql.append(" and ma.month='").append(befMonString).append("'");
		System.out.println("更新未结束（跨月）的计划----"+sql);*/
		sql.append(" update pnr_inspect_plan_res res set res.plan_id=''");
		sql.append(" where  res.cycle_end_time>=to_date('").append(nowdate).append("','yyyy/mm/dd hh24:mi:ss')");
		
		sql.append(" and res.inspect_state=0 ");
		sql.append(" and res.plan_id is not null");
		
		System.out.println("将未完成的元任务计划id置空----"+sql);

		this.getJdbcTemplate().execute(sql.toString());

	}
	/**
	 * 资源周期被修改时,将相关联的最近地一个原任务周期修改了
	 * @param planResId 资源id
	 * @param times  要修改成的时间数组
	 */
	public void changePlanResCycle(String planResId,String[] times,String cycle){
		if(planResId!=null && !"".equals(planResId)){
			String sqlOld  = " select res.id from pnr_inspect_plan_res res  where res.res_cfg_id='"+planResId+"' and rownum=1 order by res.create_time desc";
			List<Map> list = (List<Map>)this.getJdbcTemplate().queryForList(sqlOld);
			StringBuffer sql = new StringBuffer();
			if( list!=null&&!list.isEmpty()&&list.size()>0 ){
				sql=sql.append("update pnr_inspect_plan_res s set s.inspect_cycle='");
				sql.append(cycle).append("' , s.cycle_start_time = to_date('").append(times[0]).append("','yyyy-mm-dd'),s.cycle_end_time=to_date('").append(times[1]).append("','yyyy-mm-dd hh24:mi:ss'),s.plan_start_time = to_date('").append(times[0]).append("','yyyy-mm-dd'),s.plan_end_time = to_date('").append(times[1]).append("','yyyy-mm-dd hh24:mi:ss') where s.id='").append(list.get(0).get("ID")).append("'");
				this.getJdbcTemplate().update(sql.toString());
			}
			
//		System.out.println("-----------sql--insert------"+sql.toString());
		}
	}
	/**
	 * 修改巡检资源--元任务经纬度
	 * @param sql
	 * @return
	 */
	public int updateResource(String sql){
		return this.getJdbcTemplate().update(sql); 
	}
}
