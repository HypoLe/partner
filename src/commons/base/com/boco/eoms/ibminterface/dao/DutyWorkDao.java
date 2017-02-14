package com.boco.eoms.ibminterface.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.boco.eoms.common.dao.DAO;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.commons.system.cptroom.model.TawSystemCptroom;
import com.boco.eoms.db.util.ConnectionPool;
import com.boco.eoms.ibminterface.webapp.form.DutyWork;

/**
 * @author 李晓明，龚玉峰
 * 
 */
public class DutyWorkDao extends DAO {

	public DutyWorkDao(ConnectionPool ds) {
		super(ds);
	}

	public DutyWorkDao() {
	}

	/**
	 * 取出所有機房信息
	 */
	public List getdutyWorkList() {
		ArrayList list = new ArrayList();
		com.boco.eoms.db.util.BocoConnection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		try {
			sql = "select * from taw_system_cptroom";
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				TawSystemCptroom file = new TawSystemCptroom();
				populate(file, rs);
				list.add(file);
			}
		} catch (SQLException e) {
			close(rs);
			close(pstmt);
			e.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					System.out.println("close connection");
					conn.close();
					System.out.println("close connection end");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return list;

	}

	/**
	 * 根据机房id ，当前时间取到当前班次信息
	 * 
	 * @param roomId
	 * @param time
	 * @return
	 */
	public List getdutyList(int roomId, String time) {
		ArrayList list = new ArrayList();
		com.boco.eoms.db.util.BocoConnection conn = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		try {
			sql = "select a.id,a.dutydate,a.starttime_defined,a.endtime_defined,b.username,b.mobile from taw_rm_assignwork a,taw_system_user b "
					+ "WHERE a.room_id = ? and a.starttime_defined <= ?   and a.endtime_defined >= ? "
					+ "and b.userid = a.dutymaster order by a.starttime_defined";
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, roomId);
			pstmt.setString(2, time);
			pstmt.setString(3, time);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				DutyWork file = new DutyWork();
				populate(file, rs);
				list.add(file);
			}
			conn.close();
		} catch (SQLException e) {
			close(rs);
			close(pstmt);
			e.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					System.out.println("close connection");
					conn.close();
					System.out.println("close connection end");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return list;

	}

	/**
	 * @see
	 * @param 排班班次号workserial
	 * @return 该班次排班人员姓名拼出来的字符串
	 * @throws SQLException
	 */
	public String getDutymanStr(int workserial) throws SQLException {
		String DutymanStr = "";
		com.boco.eoms.db.util.BocoConnection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// edit by wangheqi 2.7to3.5
		// TawSystemUserRoleBo userbo = TawSystemUserRoleBo.getInstance();
		// TawSystemUser tawRmUser = null;
		// edit end
		// TawRmUserDAO tawRmUserDAO=null;
		// tawRmUserDAO = new TawRmUserDAO(ds);
		try {
			conn = ds.getConnection();
			String sql = "SELECT b.username FROM taw_rm_assign_sub a,taw_system_user b WHERE workserial=? and b.userid=a.dutyman";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, workserial);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				if (!StaticMethod.null2String(rs.getString(1)).equals(""))
					DutymanStr = DutymanStr + "," + rs.getString(1).trim();
			}
			if (!"".equals(DutymanStr))
				DutymanStr = DutymanStr.substring(1, DutymanStr.length());
			close(rs);
			close(pstmt);
			conn.close();
		} catch (SQLException e) {
			close(rs);
			close(pstmt);
			rollback(conn);
			e.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					System.out.println("close connection");
					conn.close();
					System.out.println("close connection end");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return DutymanStr;
	}

	/**
	 * @param roomId
	 * @param startTime
	 * @return
	 * @throws SQLException
	 */
	public List getNextUpduty(String roomId, String startTime)
			throws SQLException {
		ArrayList list = new ArrayList();
		com.boco.eoms.db.util.BocoConnection conn = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		try {
			sql = "select a.id,a.dutydate,a.starttime_defined,a.endtime_defined,b.username,b.mobile from taw_rm_assignwork a,taw_system_user b "
					+ "WHERE a.room_id = ? and a.endtime_defined = ? "
					+ "and b.userid = a.dutymaster order by a.starttime_defined";
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, roomId);
			pstmt.setString(2, startTime);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				DutyWork file = new DutyWork();
				populate(file, rs);
				list.add(file);
			}
			conn.close();
		} catch (SQLException e) {
			close(rs);
			close(pstmt);
			e.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					System.out.println("close connection");
					conn.close();
					System.out.println("close connection end");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return list;
	}

	/**
	 * @param roomId
	 * @param endTime
	 * @return
	 * @throws SQLException
	 */
	public List getNextDownduty(String roomId, String endTime)
			throws SQLException {
		ArrayList list = new ArrayList();
		com.boco.eoms.db.util.BocoConnection conn = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		try {
			sql = "select a.id,a.dutydate,a.starttime_defined,a.endtime_defined,b.username,b.mobile from taw_rm_assignwork a,taw_system_user b "
					+ "WHERE a.room_id = ? and a.starttime_defined = ? "
					+ "and b.userid = a.dutymaster order by a.starttime_defined";
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, roomId);
			pstmt.setString(2, endTime);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				DutyWork file = new DutyWork();
				populate(file, rs);
				list.add(file);
			}
			conn.close();
		} catch (SQLException e) {
			close(rs);
			close(pstmt);
			e.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					System.out.println("close connection");
					conn.close();
					System.out.println("close connection end");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return list;
	}

	/**
	 * 去和当前时间最接近的班次信息
	 * 
	 * @param roomId
	 * @param time
	 * @return
	 * @throws SQLException
	 */
	public List getNextNowNullDownduty(String roomId, String time)
			throws SQLException {
		ArrayList list = new ArrayList();
		com.boco.eoms.db.util.BocoConnection conn = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		try {
			sql = "select a.id,a.dutydate,a.starttime_defined,a.endtime_defined,b.username,b.mobile from taw_rm_assignwork a,taw_system_user b "
					+ "WHERE a.room_id = ? and a.starttime_defined >= ? "
					+ "and b.userid = a.dutymaster order by a.starttime_defined";
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, roomId);
			pstmt.setString(2, time);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				DutyWork file = new DutyWork();
				populate(file, rs);
				list.add(file);
			}
			conn.close();
		} catch (SQLException e) {
			close(rs);
			close(pstmt);
			e.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					System.out.println("close connection");
					conn.close();
					System.out.println("close connection end");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return list;
	}

	/**
	 * 去当前时间之前最接近的班次信息
	 * 
	 * @param roomId
	 * @param time
	 * @return
	 * @throws SQLException
	 */
	public List getNextNowNullUpduty(String roomId, String time)
			throws SQLException {
		ArrayList list = new ArrayList();
		com.boco.eoms.db.util.BocoConnection conn = null;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		try {
			sql = "select a.id,a.dutydate,a.starttime_defined,a.endtime_defined,b.username,b.mobile from taw_rm_assignwork a,taw_system_user b "
					+ "WHERE a.room_id = ? and a.endtime_defined <= ? "
					+ "and b.userid = a.dutymaster order by a.endtime_defined";
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, roomId);
			pstmt.setString(2, time);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				DutyWork file = new DutyWork();
				populate(file, rs);
				list.add(file);
			}
			conn.close();
		} catch (SQLException e) {
			close(rs);
			close(pstmt);
			e.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					System.out.println("close connection");
					conn.close();
					System.out.println("close connection end");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return list;
	}

	/**
	 * 取出所有機房信息
	 */
	public List getdutyWorkList(String time) {
			ArrayList list = new ArrayList();
			ArrayList listmap = new ArrayList();
		com.boco.eoms.db.util.BocoConnection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		try {
			sql = "select r.roomname,r.id,count(distinct s.dutyman) from taw_rm_assignwork a,"
				 +"taw_system_cptroom r,"
         +"taw_rm_assign_sub s"
         +" where a.room_id=r.id"
         +" and s.workserial=a.id"
         +" and a.starttime_defined>='"+time+" 00:00:00'"
				 +" and a.endtime_defined<='"+time+" 23:59:59'"
				 +" group by r.roomname,r.id order by r.id";
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				if (rs.getInt(2) == 0) {
					continue;
				}
				TawSystemCptroom file = new TawSystemCptroom();
				file.setRoomname(rs.getString(1));
				file.setId(new Integer(rs.getInt(2)));
				file.setDutyUserCout(rs.getInt(3)+1);
				list.add(file);
			}
		} catch (SQLException e) {
			close(rs);
			close(pstmt);
			e.printStackTrace();
			
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					System.out.println("close connection");
					conn.close();
					System.out.println("close connection end");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return list;

	}

	public Map getdutyWorkMap(String time) {
		Map map = new java.util.HashMap();
		ArrayList listmap = new ArrayList();
	com.boco.eoms.db.util.BocoConnection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";
	try {
		sql = "select r.id,count(distinct s.dutyman) from taw_rm_assignwork a,"
			 +"taw_system_cptroom r,"
     +" taw_rm_assign_sub s"
     +" where a.room_id=r.id"
     +" and s.workserial=a.id"
     +" and a.starttime_defined<='"+time+"'"
			 +" and a.endtime_defined>='"+time+"'"
			 +" and a.dutydate='"+time.substring(0,10)+"'"
			 +" group by r.id order by r.id ";
		conn = ds.getConnection();
		pstmt = conn.prepareStatement(sql);
		System.out.println("sql==========>"+sql);
		rs = pstmt.executeQuery();
		while (rs.next()) {
			String tmp=String.valueOf(rs.getInt(1));
			String tmap=String.valueOf(rs.getInt(2));
			map.put(tmp,tmap);
		}
	} catch (SQLException e) {
		close(rs);
		close(pstmt);
		e.printStackTrace();
	} finally {
		try {
			if (conn != null && !conn.isClosed()) {
				System.out.println("close connection");
				conn.close();
				System.out.println("close connection end");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	return map;

}
}
