package com.boco.eoms.sheet.base.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.user.model.TawSystemUserRefRole;
import com.boco.eoms.commons.system.user.service.ITawSystemUserRefRoleManager;
import com.boco.eoms.commons.util.xml.XmlManage;
import com.boco.eoms.sheet.base.dao.IEomsAllSheetListDAO;
import com.boco.eoms.sheet.base.service.IDownLoadSheetAccessoriesService;
import com.boco.eoms.sheet.base.service.IEomsAllSheetListService;

public class EomsAllSheetLisetServiceImpl implements IEomsAllSheetListService {

	private IEomsAllSheetListDAO taskDAO;

	/**
	 * @return Returns the taskDAO.
	 */
	public IEomsAllSheetListDAO getTaskDAO() {
		return taskDAO;
	}

	/**
	 * @param taskDAO
	 *            The taskDAO to set.
	 */
	public void setTaskDAO(IEomsAllSheetListDAO taskDAO) {
		this.taskDAO = taskDAO;
	}

	/**
	 * 获取当前角色待处理工单
	 * 
	 * @param userId
	 *            用户ID
	 * @param deptId
	 *            部门ID
	 * @param flowName
	 *            流程ID
	 * @param startIndex
	 * @param length
	 * @return
	 * @throws Exception
	 */
	public HashMap getUndoAllSheetTask(Map condition, String userId,
			String deptId, String flowName, Integer startIndex, Integer length)
			throws Exception {
		HashMap taskMap = new HashMap();
		String hqlDialect = ApplicationContextHolder.getInstance()
				.getHQLDialect().trim();

		
		if (hqlDialect.equals("org.hibernate.dialect.OracleDialect")
				|| hqlDialect.equals("org.hibernate.dialect.InformixDialect")) {

			Calendar c = Calendar.getInstance();
			String tmpViewName = c.get(Calendar.HOUR_OF_DAY) + "_"
					+ c.get(Calendar.MINUTE) + "_" + c.get(Calendar.SECOND)
					+ "_" + c.get(Calendar.MILLISECOND);

			String user_name = XmlManage.getFile("/config/EomsSheetView.xml")
					.getProperty("undoSheet");
			user_name = user_name.replaceAll("OCCU_USERID", userId);
			user_name = user_name.replaceAll("OCCU_DEPTID", deptId);
			user_name = user_name.replaceAll("TMP_VIEW_NAME", tmpViewName);
			ITawSystemUserRefRoleManager userMgr = (ITawSystemUserRefRoleManager) ApplicationContextHolder
			.getInstance().getBean("itawSystemUserRefRoleManager");
			List subroleList = userMgr.getUserRefRoleByuserid(userId);
			String subRoleId = "";
			for(int i=0;subroleList!=null && i<subroleList.size();i++){
				TawSystemUserRefRole subrole = (TawSystemUserRefRole)subroleList.get(i);
				String roleid = subrole.getSubRoleid();
				if(!subRoleId.equals("") && roleid!=null && !roleid.equals("")){
					subRoleId = subRoleId+",";
				}
				subRoleId = subRoleId + "'"+roleid+"'";
			}
			if(subRoleId.equals("")){
				subRoleId = "'norole'";
			}
			user_name = user_name.replaceAll("OCCU_SUBROLEID", subRoleId);
			IDownLoadSheetAccessoriesService service = (IDownLoadSheetAccessoriesService) ApplicationContextHolder
					.getInstance().getBean("IDownLoadSheetAccessoriesService");
			String viewName = "undo_" + tmpViewName;
			
			//service.executeProcedure(user_name);
			/**
			 * modify by chenyuanshu
			 * 2011-11--7 由于informix上create view容易出现锁systabauth 导致表的读取和插入都出现锁表
			 * 所以在保持效率的情况下，修改成使用临时表 begin
			 */
			
			if(hqlDialect.equals("org.hibernate.dialect.InformixDialect")){
				String sqlCreateTemptable = "create temp table "+viewName
					+"  ("
					+"    sheetType varchar(50),"
					+"    sheetTypeName varchar(50),"
					+"    id varchar(50) not null ,"
					+"    taskname varchar(50),"
					+"    taskdisplayname varchar(50),"
					+"    createtime datetime year to second,"
					+"    taskstatus varchar(10),"
					+"    processid varchar(50),"
					+"    sheetkey varchar(32),"
					+"    sheetid varchar(30),"
					+"    title varchar(255),"
					+"    accepttimelimit datetime year to second,"
					+"    completetimelimit datetime year to second,"
					+"    operateroleid varchar(32),"
					+"    taskowner varchar(50),"
					+"    prelinkid varchar(32),"
					+"    flowname varchar(50),"
					+"    currentlinkid varchar(32),"
					+"    subtaskflag varchar(10),"
					+"    operatetype varchar(25),"
					+"    parenttaskid varchar(50),"
					+"    ifwaitforsubtask varchar(10),"
					+"    subtaskdealfalg varchar(20),"
					+"    sendtime datetime year to second,"
					+"    primary key (id)"
					+"  ) with no log lock mode row;";
	
//				String sqlCreateTemptable = "create temp table "+viewName
//				+"  ("
//				+"    sheetType varchar(50),"
//				+"    sheetTypeName varchar(50),"
//				+"    id varchar(255) not null ,"
//				+"    taskname varchar(255),"
//				+"    taskdisplayname varchar(255),"
//				+"    createtime datetime year to second,"
//				+"    taskstatus varchar(255),"
//				+"    processid varchar(255),"
//				+"    sheetkey varchar(255),"
//				+"    sheetid varchar(255),"
//				+"    title varchar(255),"
//				+"    accepttimelimit datetime year to second,"
//				+"    completetimelimit datetime year to second,"
//				+"    operateroleid char(255),"
//				+"    taskowner varchar(255),"
//				+"    prelinkid varchar(255),"
//				+"    flowname varchar(255),"
//				+"    currentlinkid varchar(255),"
//				+"    subtaskflag varchar(10),"
//				+"    operatetype varchar(25),"
//				+"    parenttaskid varchar(50),"
//				+"    ifwaitforsubtask varchar(10),"
//				+"    subtaskdealfalg varchar(20),"
//				+"    sendtime datetime year to second,"
//				+"    primary key (id)"
//				+"  ) with no log lock mode row;";
				String index = "create index idx_"+viewName+" on "+viewName+" (createtime) using btree ;";
				service.executeProcedure(sqlCreateTemptable);
				service.executeProcedure(index);
//				if(user_name.indexOf(" as")!=-1){
//					user_name = user_name.substring(user_name.indexOf(" as")+2, user_name.length());
//				}
//				user_name = "insert into "+viewName+user_name;
//				user_name = user_name.replaceAll("union all", ";insert into "+viewName);
				String aaa[] = user_name.split(";");
				
				for(int i=0 ;i<aaa.length;i++){
					service.executeProcedure(aaa[i]);
				}
			}else{//oracle 支持全局临时表，所以只需要创建一个temp_eoms_undo_all_sheet表即可，需要预先建好这张表，并且是事务级的表，及回滚或提交均会清空数据
				//service.executeProcedure(user_name);
				
				
				user_name = user_name.replaceAll(viewName, "tmp_eoms_undo_all_sheet");
				viewName = "tmp_eoms_undo_all_sheet";
				String aaa[] = user_name.split(";");
			
				for(int i=0 ;i<aaa.length;i++){
					service.executeProcedure(aaa[i]);			
				}
			}
			/**
			 * modify by chenyuanshu
			 * 2011-11--7 由于informix上create view容易出现锁systabauth 导致表的读取和插入都出现锁表
			 * 所以在保持效率的情况下，修改成使用临时表 end
			 */
			
			String hql = "select t.* from " + viewName + " t ";
			String orderCondition = (String) condition.get("orderCondition");
			if (orderCondition != null && !orderCondition.equals("")) {
				hql = hql + "order by " + orderCondition;
			} else {
				hql = hql + "order by createTime desc";
			}
			try {
				taskMap = taskDAO.getAllTasksByHql(hql, startIndex, length);	
			} catch (Exception e) {				
				throw e;
			}finally {
				if(hqlDialect.equals("org.hibernate.dialect.InformixDialect")){//由于informix中改成了create temp table ，所以这里要删除表而不是删视图
					service.executeProcedure("drop table " + viewName+";");
				}
//				else{//由于oracle的临时表可以是事务级，提交或回滚后均会消除数据，所以无需
//					service.executeProcedure("drop table " + viewName);
//				}
			}

		} else {
			String hql = " select task from EomsUndoSheetView as task where task.taskOwner='"
					+ userId
					+ "' or task.taskOwner='"
					+ deptId
					+ "' or task.taskOwner in (select userrefrole.subRoleid from TawSystemUserRefRole as userrefrole where userrefrole.userid='"
					+ userId + "') ";
			String orderCondition = (String) condition.get("orderCondition");
			if (!orderCondition.equals("") && orderCondition != null) {
				hql = hql + "order by " + orderCondition;
			} else {
				hql = hql + "order by task.createTime desc";
			}
			taskMap = taskDAO.getTaskListByCondition(hql, startIndex, length);
		}
		return taskMap;
	}

	/**
	 * 获取当前人员由我启动列表
	 * 
	 * @param userId
	 *            用户ID
	 * @param deptId
	 *            部门ID
	 * @param flowName
	 *            流程ID
	 * @param startIndex
	 * @param length
	 * @return
	 * @throws Exception
	 */
	public HashMap getAllSheetStartedByMe(Map condition, String userId,
			String deptId, String flowName, Integer startIndex, Integer length)
			throws Exception {
		HashMap taskMap = new HashMap();
		String hqlDialect = ApplicationContextHolder.getInstance()
				.getHQLDialect().trim();

		Calendar c = Calendar.getInstance();
		String tmpViewName = c.get(Calendar.HOUR_OF_DAY) + "_"
				+ c.get(Calendar.MINUTE) + "_" + c.get(Calendar.SECOND) + "_"
				+ c.get(Calendar.MILLISECOND);

		String ifAtom = StaticMethod.nullObject2String(condition.get("ifAtom"));
		String user_name = "";
		String beforeDays = "";

		if (!ifAtom.equals("true")) {
			user_name = XmlManage.getFile("/config/EomsSheetView.xml")
					.getProperty("startedByMe");

			beforeDays = XmlManage.getFile("/config/EomsSheetView.xml")
					.getProperty("startedByMeListBeforeDays");
		} else {
			user_name = XmlManage.getFile("/config/EomsSheetView.xml")
					.getProperty("startedByMeAtom");

			beforeDays = XmlManage.getFile("/config/EomsSheetView.xml")
					.getProperty("startedByMeListBeforeDaysAtom");

		}
		user_name = user_name.replaceAll("OCCU_USERID", userId);
		user_name = user_name.replaceAll("OCCU_DEPTID", deptId);
		user_name = user_name.replaceAll("TMP_VIEW_NAME", tmpViewName);
		ITawSystemUserRefRoleManager userMgr = (ITawSystemUserRefRoleManager) ApplicationContextHolder
		.getInstance().getBean("itawSystemUserRefRoleManager");
		List subroleList = userMgr.getUserRefRoleByuserid(userId);
		String subRoleId = "";
		for(int i=0;subroleList!=null && i<subroleList.size();i++){
			TawSystemUserRefRole subrole = (TawSystemUserRefRole)subroleList.get(i);
			String roleid = subrole.getSubRoleid();
			if(!subRoleId.equals("") && roleid!=null && !roleid.equals("")){
				subRoleId = subRoleId+",";
			}
			subRoleId = subRoleId + "'"+roleid+"'";
		}
		user_name = user_name.replaceAll("OCCU_SUBROLEID", subRoleId);
		String beforeTime = getTimeStringBeforeDays(beforeDays);
		if (!"".equals(beforeTime)) {
			user_name = user_name.replaceAll("_whereBeforeDayString_",
					" and main.sendTime >" + getDateTypeAdapter(beforeTime));
		} else {
			user_name = user_name.replaceAll("_whereBeforeDayString_", "");
		}
		IDownLoadSheetAccessoriesService service = (IDownLoadSheetAccessoriesService) ApplicationContextHolder
				.getInstance().getBean("IDownLoadSheetAccessoriesService");
		String viewName = "STARTEDBYME_" + tmpViewName;
		service.executeProcedure(user_name);
		String hql = "select t.* from " + viewName + " t ";
		String orderCondition = (String) condition.get("orderCondition");
		if (orderCondition != null && !orderCondition.equals("")) {
			hql = hql + "order by " + orderCondition;
		} else {
			hql = hql + "order by sendTime desc";
		}
		try {
			taskMap = taskDAO.getAllMainsByHql(hql, startIndex, length);
			service.executeProcedure("drop view " + viewName);
		} catch (Exception e) {
			service.executeProcedure("drop view " + viewName);
			throw e;
		}
		return taskMap;
	}

	/**
	 * 获取当前人员所有已处理列表
	 * 
	 * @param userId
	 *            用户ID
	 * @param deptId
	 *            部门ID
	 * @param flowName
	 *            流程ID
	 * @param startIndex
	 * @param length
	 * @return
	 * @throws Exception
	 */
	public HashMap getAllDoneSheet(Map condition, String userId, String deptId,
			String flowName, Integer startIndex, Integer length)
			throws Exception {
		HashMap taskMap = new HashMap();
		String hqlDialect = ApplicationContextHolder.getInstance()
				.getHQLDialect().trim();

		Calendar c = Calendar.getInstance();
		String tmpViewName = c.get(Calendar.HOUR_OF_DAY) + "_"
				+ c.get(Calendar.MINUTE) + "_" + c.get(Calendar.SECOND) + "_"
				+ c.get(Calendar.MILLISECOND);
		String ifAtom = StaticMethod.nullObject2String(condition.get("ifAtom"));
		String user_name = "";
		String beforeDays = "";

		if (!ifAtom.equals("true")) {
			user_name = XmlManage.getFile("/config/EomsSheetView.xml")
					.getProperty("doneSheet");
			beforeDays = XmlManage.getFile("/config/EomsSheetView.xml")
					.getProperty("doneSheetListBeforeDays");
		} else {
			user_name = XmlManage.getFile("/config/EomsSheetView.xml")
					.getProperty("doneSheetAtom");
			beforeDays = XmlManage.getFile("/config/EomsSheetView.xml")
					.getProperty("doneSheetListBeforeDaysAtom");
		}
		user_name = user_name.replaceAll("OCCU_USERID", userId);
		user_name = user_name.replaceAll("OCCU_DEPTID", deptId);
		user_name = user_name.replaceAll("TMP_VIEW_NAME", tmpViewName);
		ITawSystemUserRefRoleManager userMgr = (ITawSystemUserRefRoleManager) ApplicationContextHolder
		.getInstance().getBean("itawSystemUserRefRoleManager");
		List subroleList = userMgr.getUserRefRoleByuserid(userId);
		String subRoleId = "";
		for(int i=0;subroleList!=null && i<subroleList.size();i++){
			TawSystemUserRefRole subrole = (TawSystemUserRefRole)subroleList.get(i);
			String roleid = subrole.getSubRoleid();
			if(!subRoleId.equals("") && roleid!=null && !roleid.equals("")){
				subRoleId = subRoleId+",";
			}
			subRoleId = subRoleId + "'"+roleid+"'";
		}
		user_name = user_name.replaceAll("OCCU_SUBROLEID", subRoleId);

		String beforeTime = getTimeStringBeforeDays(beforeDays);
		if (!"".equals(beforeTime)) {
			user_name = user_name.replaceAll("_whereBeforeDayString_",
					" and main.sendTime >" + getDateTypeAdapter(beforeTime));
		} else {
			user_name = user_name.replaceAll("_whereBeforeDayString_", "");
		}
		IDownLoadSheetAccessoriesService service = (IDownLoadSheetAccessoriesService) ApplicationContextHolder
				.getInstance().getBean("IDownLoadSheetAccessoriesService");
		String viewName = "DONE_" + tmpViewName;
		service.executeProcedure(user_name);
		String hql = "select t.* from " + viewName + " t ";
		String orderCondition = (String) condition.get("orderCondition");
		if (orderCondition != null && !orderCondition.equals("")) {
			hql = hql + "order by " + orderCondition;
		} else {
			hql = hql + "order by sendTime desc";
		}
		try {
			taskMap = taskDAO.getAllMainsByHql(hql, startIndex, length);
			service.executeProcedure("drop view " + viewName);
		} catch (Exception e) {
			service.executeProcedure("drop view " + viewName);
			throw e;
		}
		return taskMap;
	}

	public String getTimeStringBeforeDays(String beforeDays) {
		String timeString = "";
		if (!"".equals(beforeDays)) {
			TimeZone zone = TimeZone.getTimeZone("GMT+8");
			Calendar cal = Calendar.getInstance(zone);
			int days = 0;
			try {
				days = Integer.parseInt(beforeDays);
			} catch (Exception e) {
				return "";
			}

			cal.add(Calendar.DATE, -days);
			Date date = cal.getTime();
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			timeString = format.format(date) + " 00:00:00";
			// cal.add(Calendar.da, amount)
		}
		return timeString;
	}

	public String getDateTypeAdapter(String columnNameValue) {
		String hqlDialect = ApplicationContextHolder.getInstance()
				.getHQLDialect().trim();

		if (hqlDialect.equals("org.hibernate.dialect.OracleDialect")) {
			return columnNameValue = "to_date('" + columnNameValue
					+ "','yyyy-MM-dd HH24:mi:ss')";
		} else {
			return "'" + columnNameValue + "'";
		}

	}
}
