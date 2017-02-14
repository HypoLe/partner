package com.boco.eoms.sparepart.dao;

import java.util.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.boco.eoms.common.dao.DAO;
import com.boco.eoms.db.util.ConnectionPool;
import com.boco.eoms.sparepart.model.*;

/**
 * <p>Title: EOMS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: BOCO</p>
 * @author HAO
 * @version 2.0
 */

public class TawOrderPartDAO
      extends DAO{

    public TawOrderPartDAO(ConnectionPool ds){
        super(ds);
    }

    /**
     * @see
     * order
     * @param
     * @return
     */
    public List getOrderPart(String SQL) throws SQLException{
       ArrayList list=new ArrayList();
       com.boco.eoms.db.util.BocoConnection conn=null;
       PreparedStatement pstmt=null;
       ResultSet rs=null;
       try{
           conn=ds.getConnection();
           String sql=
                 "select id,order_id,sparepart_id,org_serial_no,new_serial_no,state from taw_sp_order_part "+
                 SQL;
           pstmt=conn.prepareStatement(sql);
           rs=pstmt.executeQuery();
           while(rs.next()){
               TawOrderPart tawOrderPart=new TawOrderPart();
               populate(tawOrderPart,rs);
               list.add(tawOrderPart);
           }
           close(rs);
           close(pstmt);
       }
       catch(SQLException e){
           close(rs);
           close(pstmt);
           e.printStackTrace();
       }
       finally{
           close(conn);
       }
       return list;
   }

    /**
     * @see ȷ��orderId��Ӧ�ı����Ƿ�ȫ��������������еı������ѱ��������ʱ��
     * order ��state���ı�Ϊ2���ص�������״̬��
     * @param orderId ���ݵ�id
     * @return
     */
    public boolean checkPartState(String orderId){

        boolean flag=false;

        com.boco.eoms.db.util.BocoConnection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try{
            conn=ds.getConnection();
            String sql=
                  "select state from taw_sp_order_part where order_id=?";
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1,orderId);

            rs=pstmt.executeQuery();
            while(rs.next()){
                String aa=rs.getString(1);
                //������ڣ��򵥾ݻ�û�������
                if(aa.equals("1")){
                    flag=true;
                }
            }
            close(rs);
            close(pstmt);
        }
        catch(SQLException e){
            close(rs);
            close(pstmt);
            e.printStackTrace();
        }
        finally{
            close(conn);
        }
        return flag;
    }

    /**
     * @see ���뵥�ݱ���������Ϣ
     * @param userId  �û���id
     * @param orderId ���ݵ�id
     */
    public void insertOrderPart(String orderId, String sparepartId,String orgSerialNo, String newSerialNo){
        com.boco.eoms.db.util.BocoConnection conn=null;
        conn=ds.getConnection();
        PreparedStatement pstmt=null;

        String sql=
              "insert into taw_sp_order_part(order_id,sparepart_id,state,org_serial_no,new_serial_no) values(?,?,?,?,?)";
        try{
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1,orderId);
            pstmt.setString(2,sparepartId);
            pstmt.setInt(3,1);
            pstmt.setString(4,orgSerialNo);
            pstmt.setString(5,newSerialNo);
            pstmt.executeUpdate();
            conn.commit();
        }
        catch(SQLException sqle){
            rollback(conn);
            sqle.printStackTrace();
        }
        finally{
            close(pstmt);
            close(conn);
        }
    }

    /**
     * @see ����ȡ��order_part�е�ĳһ�����ݵ����б�����id��
     * @param SQL bo�д��ݵĲ���
     * @return ����id�͵��ַ���
     */
    public String getSparePartSumId(String SQL){
        com.boco.eoms.db.util.BocoConnection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        String id="00";

        try{
            conn=ds.getConnection();
            String sql=
                  "select sparepart_id from taw_sp_order_part "+SQL;
            pstmt=conn.prepareStatement(sql);
            rs=pstmt.executeQuery();

            while(rs.next()){
                String ID=rs.getString(1);
                id=id+","+ID;
            }
            close(rs);
            close(pstmt);
        }
        catch(SQLException e){
            close(rs);
            close(pstmt);
            e.printStackTrace();
        }
        finally{
            close(conn);
        }
        return id;
    }

    /**
     *
     * @param SQL
     */
    public void updateOrderPart(String SQL){
        com.boco.eoms.db.util.BocoConnection conn=null;
        conn=ds.getConnection();
        PreparedStatement pstmt=null;

        String sql="update  taw_sp_order_part "+SQL;
        try{
            pstmt=conn.prepareStatement(sql);
            pstmt.executeUpdate();
            conn.commit();
        }
        catch(SQLException sqle){
            rollback(conn);
            sqle.printStackTrace();
        }
        finally{
            close(pstmt);
            close(conn);
        }
    }
    public void updateOrderPart(String partId,String orderId){
        com.boco.eoms.db.util.BocoConnection conn=null;
        conn=ds.getConnection();
        PreparedStatement pstmt=null;

        String sql=
              "update taw_sp_order_part set state=5 where order_id=? and sparepart_id=?";
        try{
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1,orderId);
            pstmt.setString(2,partId);
            pstmt.executeUpdate();
            conn.commit();
        }
        catch(SQLException sqle){
            rollback(conn);
            sqle.printStackTrace();
        }
        finally{
            close(pstmt);
            close(conn);
        }
    }
    public TawOrderPart getyOrderPartbyID(int id){
        com.boco.eoms.db.util.BocoConnection conn=null;
        conn=ds.getConnection();
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        TawOrderPart orderPart=new TawOrderPart();
        String sql=
              "select * from taw_sp_order_part where id="+id;
        try{
            pstmt=conn.prepareStatement(sql);
            rs=pstmt.executeQuery();
            rs.next();
            populate(orderPart,rs);
        }
        catch(SQLException sqle){
            rollback(conn);
            sqle.printStackTrace();
        }
        finally{
            close(pstmt);
            close(conn);
        }
		return orderPart;
    }

}
