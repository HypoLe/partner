/**
 *
 */
package com.boco.eoms.partner.interfaces.dao;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boco.eoms.partner.interfaces.common.init.RequestConfig;
import com.boco.eoms.partner.interfaces.bo.AreaDep;
import com.boco.eoms.partner.interfaces.bo.LoadingQueryBO;
import com.boco.eoms.partner.interfaces.bo.TaskStatus;
import com.boco.eoms.partner.interfaces.dao.JDBCManager;
/**
 * @author mooker
 * 
 */
public class PartnerConvertDAO{
	private static Log log = LogFactory.getLog(PartnerConvertDAO.class);
	private Connection conn = null;
	private PreparedStatement pst = null;
	private Statement st = null;
	private Map<String, TaskStatus> statusMap = new HashMap<String, TaskStatus>();
	
	public PartnerConvertDAO(){
		
	}
	
	public int insertResult(Map<String,Map<String,String>> resultMap){
		int success = 0;
		for(Map.Entry<String,Map<String,String>> entry1 : resultMap.entrySet()){
			Map<String,String> int_sql = entry1.getValue();
			String int_id = entry1.getKey();
			for(Map.Entry<String,String> et : int_sql.entrySet()){
				String sqlname = et.getKey();
				String sqlvalue = et.getValue();
				log.info("插入的objsql=" + sqlvalue);
				try {
					conn = JDBCManager.getDataSource(RequestConfig.getUrl(), RequestConfig.getUsername(), 
							                         RequestConfig.getPassword()).getConnection();
					st = conn.createStatement();
					int flag=st.executeUpdate(sqlvalue);
					if(flag>0){
						success = 1;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}finally{
					JDBCManager.CloseStatement(st);
					JDBCManager.CloseConn(conn);
				}
			}
		}
		return success;
	}
	
	public int updateResult(Map<String,Map<String,String>> resultMap){
		int success = 0;
//		File file=new File("c:\\logs\\partner.properties");
//		try {
//			RequestConfig.init(file);
//		} catch (IOException e1) {
//			// TODO 自动生成 catch 块
//			e1.printStackTrace();
//		}
		for(Map.Entry<String,Map<String,String>> entry1 : resultMap.entrySet()){
			Map<String,String> int_sql = entry1.getValue();
			for(Map.Entry<String,String> et : int_sql.entrySet()){
				String sqlname = et.getKey();
				String sqlvalue = et.getValue();
				log.info("插入的objsql=" + sqlvalue);
				try {
					conn = JDBCManager.getDataSource(RequestConfig.getUrl(), RequestConfig.getUsername(), 
							                         RequestConfig.getPassword()).getConnection();
					st = conn.createStatement();
					int flag=st.executeUpdate(sqlvalue);
					if(flag>0){
						success = 1;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}finally{
					JDBCManager.CloseStatement(st);
					JDBCManager.CloseConn(conn);
				}
			}
		}
		return success;
	}
	
	public void setTaskStatus(String taskCuid, TaskStatus taskStatus) {
		statusMap.put(taskCuid, taskStatus);
		log.info(taskCuid + "采集任务执行到：" + taskStatus.getStatusCode() + ","
				+ taskStatus.getStatusDescription());
	}

	public TaskStatus getTaskStatus(String taskcuid) {
		TaskStatus taskStatus = statusMap.get(taskcuid);
		if (taskStatus == null) {
			taskStatus.setStatusCode(TaskStatus.taskNoExist);
		}
		return taskStatus;
	}
	
	public void insertObjectTask(LoadingQueryBO bo)
			throws Exception {
		String sql = "insert into T_LOADINGQUERY(EVENTID, STATUSCODE, STATUSDESCRIPTION, CUID) values(?, ?, ?, ?)";
		try {
			conn = JDBCManager.getDataSource(RequestConfig.getUrl(),RequestConfig.getUsername(),RequestConfig.getPassword()).getConnection();
			pst = conn.prepareStatement(sql);
			pst.setString(1,bo.getEventid());
			pst.setLong(2, bo.getStatuscode());
			pst.setString(3,bo.getStatusdescription());
			pst.setString(4,bo.getCuid());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JDBCManager.ClosePreparedStatement(pst);
			JDBCManager.CloseConn(conn);
		}
	}
	
	public LoadingQueryBO selectTask(String cuid)
	throws Exception {
		String sql = "select * from t_loadingquery t where t.eventid = '"+ cuid +"'";
		ResultSet rs =null;
		LoadingQueryBO bo = null;
		try {
			conn = JDBCManager.getDataSource(RequestConfig.getUrl(),RequestConfig.getUsername(),RequestConfig.getPassword()).getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql.toString());
			while (rs.next()) {
				bo = new LoadingQueryBO();
				bo.setCuid(rs.getString("cuid"));
				bo.setEventid(rs.getString("eventid"));
				bo.setStatuscode(rs.getLong("statuscode"));
				bo.setStatusdescription(rs.getString("statusdescription"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JDBCManager.CloseResultSet(rs);
			JDBCManager.CloseStatement(st);
			JDBCManager.CloseConn(conn);
		}
		return bo;
	}
	
	public Map<String,String> selectAreaMap()
		throws Exception {
			String sql = "select t.areaid,t.areaname from taw_system_area t where t.areaid like '101%'";
			ResultSet rs =null;
			Map<String,String> areamap = new HashMap<String, String>();
			try {
				conn = JDBCManager.getDataSource(RequestConfig.getUrl(),RequestConfig.getUsername(),RequestConfig.getPassword()).getConnection();
				st = conn.createStatement();
				rs = st.executeQuery(sql.toString());
				while (rs.next()) {
					areamap.put(rs.getString("areaid"),rs.getString("areaname"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				JDBCManager.CloseResultSet(rs);
				JDBCManager.CloseStatement(st);
				JDBCManager.CloseConn(conn);
			}
			return areamap;
	}
	
	public Map<String,String> selectProviderMap()
	throws Exception {
		String sql = "select t.id,t.name from pnr_dept t";
		ResultSet rs =null;
		Map<String,String> providermap = new HashMap<String, String>();
		try {
			conn = JDBCManager.getDataSource(RequestConfig.getUrl(),RequestConfig.getUsername(),RequestConfig.getPassword()).getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql.toString());
			while (rs.next()) {
				providermap.put(rs.getString("name"),rs.getString("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JDBCManager.CloseResultSet(rs);
			JDBCManager.CloseStatement(st);
			JDBCManager.CloseConn(conn);
		}
		return providermap;
	}
	
	public Map<String,String> selectProviderNameMap()
	throws Exception {
		String sql = "select t.id,t.name from pnr_dept t";
		ResultSet rs =null;
		Map<String,String> providermap = new HashMap<String, String>();
		try {
			conn = JDBCManager.getDataSource(RequestConfig.getUrl(),RequestConfig.getUsername(),RequestConfig.getPassword()).getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql.toString());
			while (rs.next()) {
				providermap.put(rs.getString("id"),rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JDBCManager.CloseResultSet(rs);
			JDBCManager.CloseStatement(st);
			JDBCManager.CloseConn(conn);
		}
		return providermap;
	}
	
	public Map<String,String> selectstationNameMap()
	throws Exception {
		String sql = "select t.id,t.station_name from pnr_baseinfo_station t";
		ResultSet rs =null;
		Map<String,String> providermap = new HashMap<String, String>();
		try {
			conn = JDBCManager.getDataSource(RequestConfig.getUrl(),RequestConfig.getUsername(),RequestConfig.getPassword()).getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql.toString());
			while (rs.next()) {
				providermap.put(rs.getString("id"),rs.getString("station_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JDBCManager.CloseResultSet(rs);
			JDBCManager.CloseStatement(st);
			JDBCManager.CloseConn(conn);
		}
		return providermap;
	}
	
	public AreaDep selectAreaDep(String sql)
	throws Exception {
		AreaDep ad = new AreaDep();
		ResultSet rs =null;
		try {
			conn = JDBCManager.getDataSource(RequestConfig.getUrl(),RequestConfig.getUsername(),RequestConfig.getPassword()).getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql.toString());
			if(rs.next()) {
				ad.setAreaid(rs.getString("AREA_ID"));
				ad.setDepid(rs.getString("DEPT_ID"));
				ad.setTreenodid(rs.getString("tree_node_id"));
				ad.setCity(rs.getString("city"));
				ad.setRegion(rs.getString("region"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JDBCManager.CloseResultSet(rs);
			JDBCManager.CloseStatement(st);
			JDBCManager.CloseConn(conn);
		}
		return ad;
	}
}
