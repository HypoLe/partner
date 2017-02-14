package com.boco.eoms.sparepart.dao;

import com.boco.eoms.common.dao.DAO;
import com.boco.eoms.db.util.ConnectionPool;
import java.util.List;
import java.util.*;
import java.sql.*;
import com.boco.eoms.sparepart.model.*;
import com.boco.eoms.sparepart.model.TawTree;
import com.boco.eoms.sparepart.util.*;

/**
 * <p>Title: ��Ʒ����</p>
 * <p>Description: EOMS��ϵͳ</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: BOCO</p>
 * @author HAO
 * @version 2.7
 */

public class TawTreeDao extends DAO{

    public TawTreeDao(){
    }

    public TawTreeDao(ConnectionPool ds){
        super(ds);
    }

    /**
     * @see ����Ĭ�ϵ�רҵ������Ϣ
     * @param parentId  רҵ��Ĭ��������Ϣ��parentId��0
     * @return  רҵ������Ϣ�б�
     */
    public List getTree(int parentId,String type){
        List list=new ArrayList();
        com.boco.eoms.db.util.BocoConnection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try{
            conn=ds.getConnection();
            String sql=
                  "SELECT  id ,note,cname,ename,layer,radix,parent_id,deleted "+
                  ",code FROM taw_sp_tree where parent_id=? and cname like ?";
            pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1,parentId);
            pstmt.setString(2,type);

            rs=pstmt.executeQuery();
            while(rs.next()){
                TawTree tawTree=new TawTree();
                populate(tawTree,rs);
                list.add(tawTree);
            }
            close(rs);
            close(pstmt);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            close(conn);
        }
        return list;
    }
    /**
     * @see ����Ĭ�ϵ�רҵ������Ϣ
     * @param parentId  רҵ��Ĭ��������Ϣ��parentId��0
     * @return  רҵ������Ϣ�б�
     */
    public List getTree(int parentId){
        List list=new ArrayList();
        com.boco.eoms.db.util.BocoConnection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try{
            conn=ds.getConnection();
            String sql=
                  "SELECT  id ,note,cname,ename,layer,radix,parent_id,deleted "+
                  ",code FROM taw_sp_tree where parent_id=? ";
            pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1,parentId);

            rs=pstmt.executeQuery();
            while(rs.next()){
                TawTree tawTree=new TawTree();
                populate(tawTree,rs);
                list.add(tawTree);
            }
            close(rs);
            close(pstmt);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            close(conn);
        }
        return list;
    }    
    /**
     * @see ���ݵ�һ��ID���ص��ļ�cname��list
     * @param parentId  רҵ��Ĭ��������Ϣ��parentId��0
     * @return  רҵ������Ϣ�б�
     */
    public List getTreeForThree(int parentId){
        List list=new ArrayList();
        com.boco.eoms.db.util.BocoConnection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try{
            conn=ds.getConnection();
            String sql=
            	"select distinct cname from taw_sp_tree where parent_id in ("+
            			"select id from taw_sp_tree where parent_id in ("+
            			"select id from taw_sp_tree where taw_sp_tree.parent_id=?)" +
            			")";
            pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1,parentId);

            rs=pstmt.executeQuery();
            while(rs.next()){
                list.add(rs.getString(1));
            }
            close(rs);
            close(pstmt);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            close(conn);
        }
        return list;
    }     

    /**
     * @see ��id����Ψһ��TawTree
     * @param id ����
     * @return Ψһ��TawTree����Ϊ����Ϣ
     */
    public TawTree getParentMsg(int id){
        TawTree tawTree=null;
        com.boco.eoms.db.util.BocoConnection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try{
            conn=ds.getConnection();
            String sql=
                  "SELECT  id ,note,cname,ename,layer,radix,parent_id,deleted "+
                  ",code FROM taw_sp_tree where id=?";
            pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1,id);

            rs=pstmt.executeQuery();
            if(rs.next()){
                tawTree=new TawTree();
                populate(tawTree,rs);
            }
            close(rs);
            close(pstmt);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            close(conn);
        }
        return tawTree;
    }

    /**
     * @see ����������Ϣ����Ψһ��TawTree
     * @param dept һ������Ϣ��������
     * @param root ��������Ϣ��������
     * @return Ψһ��TawTree����Ϊ����Ϣ
     */
    public TawTree getParentMsg(String dept,String root){
        TawTree tawTree=null;
        com.boco.eoms.db.util.BocoConnection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try{
            conn=ds.getConnection();
            String sql=
                  "SELECT a.id ,a.note,a.cname,a.ename,a.layer,a.radix,"+
                  "a.parent_id,a.deleted,a.code FROM taw_sp_tree a,taw_sp_tree b "+
                  "where a.cname=? and a.parent_id=b.id and b.cname=?";
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1,root);
            pstmt.setString(2,dept);

            rs=pstmt.executeQuery();
            if(rs.next()){
                tawTree=new TawTree();
                populate(tawTree,rs);
            }
            close(rs);
            close(pstmt);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            close(conn);
        }
        return tawTree;
    }

    /**
     * @see ��BO�����װ������insert�����ݿ�
     * @param tawTree
     */
    public void insert(TawTree tawTree){
        com.boco.eoms.db.util.BocoConnection conn=null;
        conn=ds.getConnection();
        PreparedStatement pstmt=null;

        String sql=
              "INSERT INTO taw_sp_tree(parent_id,layer,radix,cname,note) "+
              " VALUES (?,?,?,?,?)";
        try{
            pstmt=conn.prepareStatement(sql);

            pstmt.setInt(1,tawTree.getParentId());
            pstmt.setString(2,tawTree.getLayer());
            pstmt.setInt(3,tawTree.getRadix());
            pstmt.setString(4,tawTree.getCname());
            pstmt.setString(5,tawTree.getNote());

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
     * @see �õ�ͬһ�������radix
     * @param parentId  ����ʶ
     * @return ���radix��1
     */
    public int getMaxRadix(int parentId){
        int maxRadix=0;
        com.boco.eoms.db.util.BocoConnection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try{
            conn=ds.getConnection();
            String sql=
                  "SELECT  max(radix)+1 FROM taw_sp_tree where parent_id=?";
            pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1,parentId);

            rs=pstmt.executeQuery();
            while(rs.next()){
                maxRadix=rs.getInt(1);
            }
            close(rs);
            close(pstmt);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            close(conn);
        }
        return maxRadix;
    }

    /**
     * @see �༶��������
     * @param layer �������ݵĲ���
     * @return
     */
    public myTreeMap getStrTree(int layer){
        myTreeMap myTree=new myTreeMap();
        com.boco.eoms.db.util.BocoConnection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try{
            conn=ds.getConnection();
            String sql;
            if(layer==2){
                sql="select layer,cname,radix from taw_sp_tree"+
                      " where PARENT_ID=0 or parent_id in (select id "+
                      " from taw_sp_tree where PARENT_ID=0) order by layer ,radix";
            }else  if(layer==3){      //ȡ���������ϵ����в˵���
                sql="select layer,cname,radix from taw_sp_tree  where length(layer)<=5  order by layer ,radix ";
            } else{
                sql= "select layer,cname,radix from taw_sp_tree order by layer ,radix";
            }
            pstmt=conn.prepareStatement(sql);

            rs=pstmt.executeQuery();
            while(rs.next()){
                myTree.init(rs.getString(1),rs.getString(2));
            }
            close(rs);
            close(pstmt);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            close(conn);
        }
        return myTree;
    }

    /**
     * @see ����id�޸�cname and note
     * @param id
     * @param cname
     * @param note
     */
    public void update(int id,String cname,String note){
        com.boco.eoms.db.util.BocoConnection conn=null;
        conn=ds.getConnection();
        PreparedStatement pstmt=null;

        String sql=
              "update taw_sp_tree set cname=?,note=? where id=?";
        try{
            pstmt=conn.prepareStatement(sql);

            pstmt.setString(1,cname);
            pstmt.setString(2,note);
            pstmt.setInt(3,id);

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

    public void drop(int id){
        com.boco.eoms.db.util.BocoConnection conn=null;
        conn=ds.getConnection();
        PreparedStatement pstmt=null;

        String sql="delete from taw_sp_tree where id=?";
        try{
            pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1,id);
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
    public String getTreeThreeId(String department,String subDept,String necode,
                                String objectname){
        String id="";
        System.out.print(department+"_"+subDept+"_"+necode+"_"+objectname );
        com.boco.eoms.db.util.BocoConnection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try{
            conn=ds.getConnection();
            String sql=
                  "select a.id,b.id,c.id,d.id from taw_sp_tree a,taw_sp_tree b, taw_sp_tree c,taw_sp_tree d where a.cname=? and a.id=b.parent_id and  b.cname=? and b.id=c.parent_id and c.cname=? and c.id=d.parent_id and d.cname=? ";
            System.out.println(sql);
            pstmt=conn.prepareStatement(sql);

            pstmt.setString(1,department);
            pstmt.setString(2,subDept);
            pstmt.setString(3,necode);
            pstmt.setString(4,objectname);

            rs=pstmt.executeQuery();
            while(rs.next()){
                String a=rs.getString(1);
                String b=rs.getString(2);
                String c=rs.getString(3);
                String d=rs.getString(4);
                id=a+"_"+b+"_"+c+"_"+d;
            }
            close(rs);
            close(pstmt);
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            close(conn);
        }
        return id;
    }
}
