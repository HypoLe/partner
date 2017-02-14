package com.boco.eoms.sparepart.bo;

import java.util.List;
import java.sql.*;
import java.io.*;

import com.boco.eoms.common.bo.BO;
import com.boco.eoms.db.util.ConnectionPool;
import com.boco.eoms.sparepart.controller.TawOrderForm;
import com.boco.eoms.sparepart.dao.TawOrderDAO;
import com.boco.eoms.sparepart.dao.TawSparepartDAO;
import com.boco.eoms.sparepart.dao.TawQueryDAO;
import com.boco.eoms.sparepart.dao.TawTempPartDAO;
import com.boco.eoms.sparepart.model.TawOrder;
import com.boco.eoms.sparepart.model.TawOrderPart;
import com.boco.eoms.sparepart.dao.TawOrderPartDAO;
import com.boco.eoms.sparepart.util.*;
import com.boco.eoms.sparepart.dao.*;
import org.apache.struts.upload.*;
import org.apache.struts.action.*;

/**
 * <p>Title: EOMS</p>
 * <p>Description: ���ݵ�ҵ���</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: BOCO</p>
 * @author HAO
 * @version 2.0
 */

public class TawOrderBO extends BO{

    public TawOrderBO(ConnectionPool ds){
        super(ds);
    }

    public TawOrderBO(ConnectionPool ds,String str){
        super(ds,str);
    }

    /**
     * @see ���뵥�ݱ�����
     * @param TawOrder
     * @return orderId���ݵ�id������
     */
    public int insertOrder(TawOrderForm form){
        TawOrderDAO dao=new TawOrderDAO(ds);
        TawOrder tawOrder=new TawOrder();
        tawOrder.setOperater(form.getOperaterId());
        tawOrder.setNote(form.getNote());
        tawOrder.setPropDept(form.getProp_dept());
        tawOrder.setProposer(form.getProposerId());
        tawOrder.setPropTel(form.getProp_tel());
        tawOrder.setType(Integer.parseInt(form.getOrderType()));
        tawOrder.setStorageid(Integer.parseInt(form.getStorageid()));
        tawOrder.setAccessory("");
        tawOrder.setSheetid(form.getSheetid());
        //add by wqw 20070703
        tawOrder.setReason(form.getReason());
        tawOrder.setEname(form.getEname());
        tawOrder.setObjtype(form.getObjtype());
        tawOrder.setFixe(form.getFixe());
        tawOrder.setVersion(form.getVersion());
        tawOrder.setSerialno(form.getSerialno());
        tawOrder.setStation(form.getStation());

        int orderId=dao.insertOrder(tawOrder);
        return orderId;
    }
    /**
     * @see ���뵥�ݱ����ݺ͵��ݱ���������Ϣ��.
     * @param TawOrder
     * @return orderId���ݵ�id������
     */
    public int insertOrderandPart(TawOrderForm form){
        TawOrderDAO dao=new TawOrderDAO(ds);
        TawOrder tawOrder=new TawOrder();
        tawOrder.setOperater(form.getOperater());
        tawOrder.setNote(form.getNote());
        tawOrder.setPropDept(form.getProp_dept());
        tawOrder.setProposer(form.getProposer());
        tawOrder.setPropTel(form.getProp_tel());
        tawOrder.setType(Integer.parseInt(form.getOrderType()));
        tawOrder.setStorageid(Integer.parseInt(form.getStorageid()));
        tawOrder.setAccessory("");
        tawOrder.setSheetid(form.getSheetid());
        //add by wqw 20070703
        tawOrder.setReason(form.getReason());
        tawOrder.setEname(form.getEname());
        tawOrder.setObjtype(form.getObjtype());
        tawOrder.setFixe(form.getFixe());
        tawOrder.setVersion(form.getVersion());
        tawOrder.setSerialno(form.getSerialno());
        tawOrder.setStation(form.getStation());

        int orderId=dao.insertOrder(tawOrder);
        TawOrderPartDAO daopart=new TawOrderPartDAO(ds);
        daopart.insertOrderPart(String.valueOf(orderId), form.getSparepart_id(), "", "");//����ҵ�񵥾��뱸��������
        return orderId;
    }
    /**
     * @see ��ȡ���ݵ��б�
     * @param sql ����
     * @return  �����б�
     */
    public List getTawOrder(String sql){
        TawOrderDAO dao=new TawOrderDAO(ds);
        List list=null;
        try{
            list=dao.getTawOrder(sql);
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return list;
    }
    /**
     * @see ���ݵ��ݱ�Ż�ȡһ�����ݵ���Ϣ
     * @param order_id ����
     * @return  �����б�
     * dww
     */
    public TawOrder getTawOrder(int order_id){
        TawOrderDAO dao=new TawOrderDAO(ds);
        List list=null;
        String sql="where id="+order_id;
        TawOrder tawOrder=new TawOrder();
        try{
            list=dao.getTawOrder(sql);
            tawOrder=(TawOrder)list.get(0);
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return tawOrder;
    }    

    /**
     * @see ����orderId�õ�taw_sp_order_part�����б�����id�͵��ַ���
     * @param orderId ����id
     * @return �����б�
     */
    public List getSparepart(int orderId){
        String StrId=Integer.toString(orderId);
        String sql=" where state=1 and order_id='"+StrId+"'";
        TawOrderPartDAO tawOrderPartDAO=new TawOrderPartDAO(ds);
        String SumId=tawOrderPartDAO.getSparePartSumId(sql);

        String SQL=" where id in ("+SumId+")";
        TawPartDAO tawPartDAO=new TawPartDAO(ds);
        List list=null;
        try{
            list=tawPartDAO.getSparepart(SQL);
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return list;
    }
    /**
     * @see ����state�õ�taw_sp_order_part�����б����͵��ݹ���������
     * @param state ���ݵ�״̬ 1,����� 2,����ͨ�� 3,�����
     * @return �����б�
     * dww
     */
    public List getOrderPart(String  state){
        String sql=" where state='"+state+"'";
        TawOrderPartDAO tawOrderPartDAO=new TawOrderPartDAO(ds);
        
        List list=null;
        try{
            list=tawOrderPartDAO.getOrderPart(sql);
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return list;
    }
    /**
     * @see ��ʱidʱ����delete��
     * �ڲ�������֮ǰ��ɾ���������ݣ�һЩû����ɲ��������ݡ�
     * @param user_id �û�id
     * @param order_id ����id ����
     */
    public void deleteTempId(String user_id,String order_id){
        String sql=
              " where user_id='"+user_id+"' and order_id='"+order_id+"'";
        TawTempPartDAO dao=new TawTempPartDAO(ds);
        dao.deleteTempPart(sql);
    }
    /**
     * ����ϴ����ļ��Ƿ��ظ�
     */
    public boolean check(FormFile File,String FilePath){
        boolean flag = true;
        File theFile = new File(FilePath);
        String Filename[] = theFile.list();
        for(int i=0;i<Filename.length;i++){
          String aa = Filename[i];
          String bb = File.getFileName();
          if(aa.equals(bb)){
            flag = false;
            break;
          }
        }
        return flag;
     }
     public void updateOrder(String sql){
       TawOrderDAO dao=new TawOrderDAO(ds);
       dao.updateOrder(sql);
     }
   }
