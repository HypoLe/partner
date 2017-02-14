package com.boco.eoms.sparepart.bo;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import com.boco.eoms.common.bo.BO;
import com.boco.eoms.db.util.ConnectionPool;
import com.boco.eoms.sparepart.dao.TawClassMsgDAO;
import com.boco.eoms.sparepart.controller.TawRemindForm;
import com.boco.eoms.sparepart.model.TawRemind;
import com.boco.eoms.sparepart.dao.TawRemindDAO;
import com.boco.eoms.sparepart.dao.TawOrderDAO;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.sparepart.dao.TawCommonDAO;
import com.boco.eoms.sparepart.model.PartRemind;
import com.boco.eoms.sparepart.util.*;

/**
 * <p>Title: EOMS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: BOCO</p>
 * @author HAO
 * @version 2.0
 */

public class TawRemindBO extends BO {

    public TawRemindBO(){
    }

    public TawRemindBO(ConnectionPool ds){
        super(ds);
    }

    public TawRemindBO(ConnectionPool ds,String str){
        super(ds,str);
    }

    /**
     * @see ����department,necode,objectname�����ر������Ƶ�id
     * @param department  רҵ����
     * @param necode   ��Ԫ����
     * @param objectname  ��������
     * @return �������Ƶ�id Ψһ
     */
    public String getClassId(String department,String necode,String objectname){
        String id="";
        TawClassMsgDAO tawClassMsgDAO=new TawClassMsgDAO(ds);
        id=tawClassMsgDAO.getClassMsgId(department,necode,objectname);
        if(!id.equals("")){
            int i=id.length();
            int s=id.indexOf("_");
            int e=id.lastIndexOf("_");
            String ss=id.substring(0,s);
            String ms=id.substring(s+1,e);
            String es=id.substring(e+1,i);
            id=ss;
        }
        return id;
    }

    /**
     * @see ���������õ�����
     * @param tawRemindForm
     * @return �ɹ�����Ϣ
     */
    public String insertRemindPart(TawRemindForm tawRemindForm){
        TawRemind tawRemind=new TawRemind();
        tawRemind.setStorageid(tawRemindForm.getStorageid());
        tawRemind.setObject(tawRemindForm.getObjectname());
        tawRemind.setType(tawRemindForm.getType());
        tawRemind.setUpperlimit(tawRemindForm.getUpperlimit());
        tawRemind.setLowerlimit(tawRemindForm.getLowerlimit());

        TawRemindDAO tawRemindDAO=new TawRemindDAO(ds);

        tawRemindDAO.insertRemind(tawRemind);
        return StaticPart.SET_PART_OK;

    }

    /**
     * @see ���뵥�����õ�����
     * @param tawRemindForm
     * @return  �ɹ���Ϣ
     */
    public String insertRemindOrder(TawRemindForm tawRemindForm){
        TawRemind tawRemind=new TawRemind();
        tawRemind.setStorageid(tawRemindForm.getStorageid());
        tawRemind.setType(tawRemindForm.getType());
        tawRemind.setLimitdate(tawRemindForm.getLimitdate());
        TawRemindDAO tawRemindDAO=new TawRemindDAO(ds);
        tawRemindDAO.insertRemind(tawRemind);

        return StaticPart.SET_ORDER_OK;
    }

    /**
     * @see ���ݲֿ��id���ظ��൥�ݵ�������Ϣ�б�
     * @param storageid  �ֿ�id
     * @return ����������Ϣ�б�
     */
    public List getOrderRemind(String storageId){
        String sql=" where type!=10 and storageid="+storageId;
        TawRemindDAO tawRemindDAO=new TawRemindDAO(ds);
        List list=tawRemindDAO.getTawRemind(sql);

        return list;
    }

    /**
     * @see ���ݵ���������Ϣ�뵥����Ϣ�ıȽϣ���ȡ�������ڵĵ�����Ϣ
     * @param orderRemind ����������Ϣ�б�
     * @return �������ڵĵ�����Ϣ�б�
     */
    public List getOrderRemindMsg(String storageId){
        ArrayList list=new ArrayList();
        TawRemind tawRemind=new TawRemind();
        TawOrderDAO tawOrderDAO=new TawOrderDAO(ds);

        List orderRemind=getOrderRemind(storageId);
        for(int i=0;i<orderRemind.size();i++){
            tawRemind=(TawRemind)orderRemind.get(i);
            if(!tawRemind.getLimitdate().equals("")){
                List tawOrder=null;
                String nowDate=StaticMethod.getDateString(Integer.parseInt(
                      tawRemind.getLimitdate()));
                String sql=" where storageid="+tawRemind.getStorageid()+
                      " and type="+
                      tawRemind.getType()+" and "+nowDate+">startdate";
                try{
                    tawOrder=tawOrderDAO.getTawOrder(sql);
                } catch(SQLException ex){
                }
                list.add(tawOrder);
            }
        }
        return list;
    }

    /**
     * @see У���Ƿ��е��ݳ�������
     * @param storageId �ֿ�id
     * @return ����true�е��ݳ������ڣ�false,�޵��ݳ�������
     */
    public boolean checkOrderRemind(String storageId){
        boolean hor=false;

        List orderRemind=getOrderRemindMsg(storageId);
        if((orderRemind!=null)&&(orderRemind.size()>0)){
            hor=true;
        }
        return hor;
    }

    /**
     * @see ���ݲֿ�id���س������ڵĵ�����Ϣ
     * @param storageId �ֿ�id
     * @return �������ڵĵ�����Ϣ
     */
    public List OrderRemind(String storageId){
        List orderRemind=getOrderRemindMsg(storageId);

        return orderRemind;
    }

    /**
     * @see ���ݲֿ�id��ÿ�汸����Ϣ��������Ϣ
     * @param storageId �ֿ�id
     * @return ��汸����Ϣ��������Ϣ
     */
    public List getPartRemind(String storageId){
        TawCommonDAO tawCommonDAO=new TawCommonDAO(ds);
        List list=tawCommonDAO.getPartRemind(storageId);

        return list;
    }

    /**
     * @see �ڿ�汸����Ϣ��������Ϣ�У��ҳ������������ޣ����޵����ͱ�����Ϣ
     * @param partRemind ��汸����Ϣ��������Ϣ
     * @return �г������õ����ͱ�����Ϣ��ĳһ���͵ı���
     */
    public List getPartRemindMsg(String storageId){
        List partRemind=getPartRemind(storageId);
        ArrayList list=new ArrayList();
        for(int i=0;i<partRemind.size();i++){
            PartRemind aa=(PartRemind)partRemind.get(i);
            int upper=Integer.parseInt(aa.getUpperlimit());
            int lower=Integer.parseInt(aa.getLowerlimit());
            int now=Integer.parseInt(aa.getNowdata());
            if(now-upper>0){
                aa.setSendMsg(StaticPart.PART_MANY);
            }
            if(now-lower<0){
                aa.setSendMsg(StaticPart.PART_ABSENT);
            }
            if(aa.getSendMsg()!=""||!aa.getSendMsg().equals(null)){
                list.add(aa);
            }
        }
        return list;
    }

    /**
     * @see �ж��Ƿ��г������Ƶı�����Ϣ
     * @param storageId �ֿ�id
     * @return true �г������õı�����Ϣ�������޳������õı�����Ϣ
     */
    public boolean checkPartRemind(String storageId){
        boolean hpr=false;

        List partRemind=getPartRemindMsg(storageId);
        if((partRemind!=null)&&(partRemind.size()>0)){
            hpr=true;
        }
        return hpr;
    }

}
