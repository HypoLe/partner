/**
 * @see
 * <p>功能描述：排班子表的DAO。</p>
 * @author 赵川
 * @version 2.0
 */


package com.boco.eoms.duty.dao;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.sql.*;

import com.boco.eoms.duty.model.*;
//import com.boco.eoms.duty.util.CacheManager;
import com.boco.eoms.common.util.CacheManager;

import com.boco.eoms.common.util.StaticMethod;
//import com.boco.eoms.common.controller.SaveSessionBeanForm;
import com.boco.eoms.duty.model.TawUserRoom;
import com.boco.eoms.common.dao.*;

public class TawUserRoomDAO extends DAO {

  public TawUserRoomDAO(com.boco.eoms.db.util.ConnectionPool  ds) {
    super(ds);
  }

  /**
   * @see  得到机房和标识对应的人员ID和姓名的列表
   * @param 机房ID room_id
   * @param 机房对应人员标识flag(是否参加机房排班，0参加，1不参加)
   * @return
   * @throws SQLException
   */
  public Vector getRoomUser(int room_id,int flag) throws SQLException {
    Vector vectorRoomUser = new Vector();
    com.boco.eoms.db.util.BocoConnection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();

      String sql = "SELECT r.user_id,u.username ,r.ordercode FROM taw_user_room r, taw_system_user u WHERE r.room_id = ? and r.user_id = u.userid and u.deleted=0 and r.flag=? order by r.ordercode ";
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, room_id);
      pstmt.setInt(2, flag);
      rs = pstmt.executeQuery();

      while(rs.next()) {
        vectorRoomUser.add(rs.getString(1));
        vectorRoomUser.add(rs.getString(2));
      }
      rs.close();
      pstmt.close() ;
    } catch (SQLException e) {
      rs.close();
      pstmt.close() ;
      conn.rollback() ;
      e.printStackTrace();
    } finally {
      conn.close() ;
    }
    return vectorRoomUser;
  }


  /**
   * @see  得到机房和标识对应的人员ID和姓名的列表，不同于getRoomUser方法的是先插入一个空值
   * @param 机房ID room_id
   * @param 机房对应人员标识flag(是否参加机房排班，0参加，1不参加)
   * @return
   * @throws SQLException
   */
  public Vector getRoomUserWithNull(int room_id,int flag) throws SQLException {
    //different from TawUserRoomBO.getRoomUser for it insert a "null" first
    Vector vectorRoomUser = new Vector();
    com.boco.eoms.db.util.BocoConnection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    vectorRoomUser.add("");
    vectorRoomUser.add("空");
    try {
      conn = ds.getConnection();

      //String sql = "SELECT r.user_id,u.user_name ,r.ordercode FROM taw_user_room r, taw_rm_user u WHERE r.room_id = ? and r.user_id = u.user_id and u.deleted=0 and r.flag=? order by r.ordercode";
      String sql = "SELECT r.user_id,u.username ,r.ordercode FROM taw_user_room r, taw_system_user u WHERE r.room_id = ? and r.user_id = u.userid and u.deleted=0 and r.flag=? order by r.ordercode";
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, room_id);
      pstmt.setInt(2, flag);
      rs = pstmt.executeQuery();

      while(rs.next()) {
        vectorRoomUser.add(StaticMethod.null2String(rs.getString(1)));
        vectorRoomUser.add(StaticMethod.null2String(rs.getString(2)));
      }
      rs.close();
      pstmt.close() ;
    } catch (SQLException e) {
      rs.close();
      pstmt.close() ;
      conn.rollback() ;
      e.printStackTrace();
    } finally {
      conn.close() ;
    }
    return vectorRoomUser;
  }

  /**
   * @see 插入机房对应用户
   * @param tawUserRoom
   * @throws SQLException
   */
  public void insert(TawUserRoom tawUserRoom) throws SQLException {
    String sql;
    sql = "INSERT INTO taw_user_room (user_id, room_id, ordercode, flag) VALUES (?, ?, ?, ?)";
    com.boco.eoms.db.util.BocoConnection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, tawUserRoom.getUserId());
      pstmt.setInt(2, tawUserRoom.getRoomId());
      pstmt.setInt(3, tawUserRoom.getOrdercode());
      pstmt.setInt(4, tawUserRoom.getFlag());
      pstmt.executeUpdate();
      pstmt.close();
      conn.commit();
    } catch (SQLException sqle) {
      close(rs);
      close(pstmt);
      rollback(conn);
      sqle.printStackTrace();
      throw sqle;
    } finally {
      close(conn);
    }
  }

  /**
   * @see 修改机房对应用户
   * @param tawUserRoom
   * @throws SQLException
   */
  public void update(TawUserRoom tawUserRoom) throws SQLException {
    com.boco.eoms.db.util.BocoConnection conn = null;
    PreparedStatement pstmt = null;
    try {
      conn = ds.getConnection();
      String sql = "UPDATE taw_user_room SET ordercode=?, flag=? WHERE user_id=? AND room_id=?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, tawUserRoom.getOrdercode());
      pstmt.setInt(2, tawUserRoom.getFlag());
      pstmt.setString(3, tawUserRoom.getUserId());
      pstmt.setInt(4, tawUserRoom.getRoomId());
      pstmt.executeUpdate();
      close(pstmt);
      conn.commit();
    } catch (SQLException e) {
      close(pstmt);
      rollback(conn);
      e.printStackTrace();
    } finally {
      close(conn);
    }
  }

  /**
   * @see 删除该机房的该用户
   * @param userId
   * @param roomId
   * @throws SQLException
   */
  public void delete(String userId, int roomId) throws SQLException {
    com.boco.eoms.db.util.BocoConnection conn = null;
    PreparedStatement pstmt = null;
    try {
      conn = ds.getConnection();
      String sql = "DELETE FROM taw_user_room WHERE user_id=? AND room_id=?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, userId);
      pstmt.setInt(2, roomId);
      pstmt.executeUpdate();
      close(pstmt);
      conn.commit();
    } catch (SQLException e) {
      close(pstmt);
      rollback(conn);
      e.printStackTrace();
    } finally {
      close(conn);
    }
  }

  /**
   * @see 删除该机房所有用户
   * @param roomId
   * @throws SQLException
   */
  public void delete( int roomId) throws SQLException {
    com.boco.eoms.db.util.BocoConnection conn = null;
    PreparedStatement pstmt = null;
    try {
      conn = ds.getConnection();
      String sql = "DELETE FROM taw_user_room WHERE room_id=?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, roomId);
      pstmt.executeUpdate();
      close(pstmt);
      conn.commit();
    } catch (SQLException e) {
      close(pstmt);
      rollback(conn);
      e.printStackTrace();
    } finally {
      close(conn);
    }
  }

  /**
   * @see 根据机房和用户得到该记录
   * @param userId
   * @param roomId
   * @return
   * @throws SQLException
   */
  public TawUserRoom retrieve(String userId, int roomId) throws SQLException {
    TawUserRoom tawUserRoom = null;
    com.boco.eoms.db.util.BocoConnection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      String sql = "SELECT * FROM taw_user_room WHERE user_id=? AND room_id=?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, userId);
      pstmt.setInt(2, roomId);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        tawUserRoom = new TawUserRoom();
        populate(tawUserRoom, rs);
      }
      close(rs);
      close(pstmt);
    } catch (SQLException e) {
      close(rs);
      close(pstmt);
      rollback(conn);
      e.printStackTrace();
    } finally {
      close(conn);
    }
    return tawUserRoom;
  }

//得到此人的机房属性
  /**
   * @see 根据用户ID，得到该用户对应所有机房列表
   * @param userId
   * @return
   * @throws SQLException
   */
  public Vector retrieveRoom(String userId) throws SQLException {
    Vector vectorRoom = new Vector();
    int roomId;
    TawUserRoom tawUserRoom = null;
    com.boco.eoms.db.util.BocoConnection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      String sql = "Select Room_ID from taw_user_room where user_id = '" +
          userId + "' and Room_ID in (select id from taw_apparatusroom where deleted = 0) order by room_id";
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        vectorRoom.add(new Integer(rs.getInt(1)));
      }
      close(rs);
      close(pstmt);
    }
    catch (SQLException e) {
      close(rs);
      close(pstmt);
      rollback(conn);
      e.printStackTrace();
    }
    finally {
      close(conn);
    }
    return vectorRoom;
  }


  /**
   * @see 得到用户对应的机房ID和名称列表
   * @param userId
   * @return
   * @throws SQLException
   */
  public Vector retrieveRoomName(String userId) throws SQLException {
    Vector vectorRoom = new Vector();
    Vector vectorRoomName = new Vector();
    int roomId;
    com.boco.eoms.db.util.BocoConnection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    com.boco.eoms.db.util.BocoConnection connN = null;
    PreparedStatement pstmtN = null;
    ResultSet rsN = null;
    try {
      conn = ds.getConnection();
      String sql = "Select u.Room_ID,r.room_name from taw_user_room u,taw_apparatusroom r where u.user_id = '" +
          userId + "' and u.room_id = r.id and u.Room_ID in (select id from taw_apparatusroom where deleted = 0) order by u.room_id";
      pstmt = conn.prepareStatement(sql);
      //System.out.println(sql);
      rs = pstmt.executeQuery();
      connN = ds.getConnection();
      while (rs.next()) {
        vectorRoom.add(new Integer(rs.getInt(1)));
        vectorRoomName.add(StaticMethod.null2String(rs.getString(2)));
      }
      close(rs);
      close(pstmt);
      close(rsN);
      close(pstmtN);
    }
    catch (SQLException e) {
      close(rs);
      close(pstmt);
      rollback(conn);
      close(rsN);
      close(pstmtN);
      rollback(connN);
      e.printStackTrace();
    }
    finally {
      close(conn);
      close(connN);
    }
    return vectorRoomName;
  }

  /**
   * @see 得到用户对应的机房ID和名称列表
   * @param userId
   * @return
   * @throws SQLException
   */
  public Vector getUserRoom(String user_id) throws SQLException {
    Vector vectorUserRoom = new Vector();
    Vector vectorRoomId = new Vector();
    Vector vectorRoomName = new Vector();
    com.boco.eoms.db.util.BocoConnection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();

      String sql = "select u.user_id,u.room_id,r.id,r.room_name,r.deleted from taw_user_room u,taw_apparatusroom r where u.user_id = ? and u.room_id = r.id and r.deleted = 0";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, user_id);
      rs = pstmt.executeQuery();
      while(rs.next()) {
        vectorRoomId.add(StaticMethod.null2String(String.valueOf(rs.getInt(3))));
        vectorRoomName.add(StaticMethod.null2String(rs.getString(4)));
      }
      rs.close();
      pstmt.close() ;
    } catch (SQLException e) {
      rs.close();
      pstmt.close() ;
      conn.rollback() ;
      e.printStackTrace();
    } finally {
      conn.close() ;
    }
    vectorUserRoom.add(vectorRoomId);
    vectorUserRoom.add(vectorRoomName);
    return vectorUserRoom;
  }




  /**
   * @see 根据机房id得到机房name
   * @param roomId
   * @return
   * @throws SQLException
   */
  public String getRoomName(int roomId) throws SQLException {
    TawUserRoom tawUserRoom = null;
    com.boco.eoms.db.util.BocoConnection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String roomName = "";
    try {
      conn = ds.getConnection();
      String sql =
          "Select ID,roomname from taw_system_cptroom where id = " +roomId+ ""; //查询相应的机房名程，roomid转为roomname
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        roomName=StaticMethod.null2String(rs.getString(2));
      }
      close(rs);
      close(pstmt);
    }
    catch (SQLException e) {
      close(rs);
      close(pstmt);
      rollback(conn);
      e.printStackTrace();
    }
    finally {
      close(conn);
    }
    return roomName;
  }

  /**
   * @see 得到用户机房对象列表
   * @return
   * @throws SQLException
   */
  public List list() throws SQLException {
    ArrayList list = new ArrayList();
    com.boco.eoms.db.util.BocoConnection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      String sql = "SELECT ordercode, flag, user_id, room_id FROM taw_user_room";
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while(rs.next()) {
        TawUserRoom tawUserRoom = new TawUserRoom();
        populate(tawUserRoom, rs);
        list.add(tawUserRoom);
        tawUserRoom=null;
      }
      close(rs);
      close(pstmt);
    } catch (SQLException e) {
      close(rs);
      close(pstmt);
      rollback(conn);
      e.printStackTrace();
    } finally {
    	close(conn);
    }
    return list;
  }

  /**
   * @see 得到用户机房对象列表
   * @return
   * @throws SQLException
   */
  public List list(int offset, int limit) throws SQLException {
    ArrayList list = new ArrayList();
    com.boco.eoms.db.util.BocoConnection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    try {
      conn = ds.getConnection();
      String sql = "SELECT ordercode, flag, user_id, room_id FROM taw_user_room";
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      if(offset > 0) rs.absolute(offset);
      int recCount = 0;
      while((recCount++ < limit) && rs.next()) {
        TawUserRoom tawUserRoom = new TawUserRoom();
        populate(tawUserRoom, rs);
        list.add(tawUserRoom);
        tawUserRoom=null;
      }
      close(rs);
      close(pstmt);
    } catch (SQLException e) {
      close(rs);
      close(pstmt);
      rollback(conn);
      e.printStackTrace();
    } finally {
    	close(conn);
    }
    return list;
  }

}
