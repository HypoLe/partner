package com.boco.eoms.autosheet.servletoper;

import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import com.boco.eoms.common.util.*;
import com.boco.eoms.db.util.*;
import com.boco.eoms.autosheet.util.*;

public class SheetAttrOperator {
  /*****************************
   �������
   */
  private String attrName="",color="",align="",valign="",nowrap="";
  private int attrID,sheetID,isattach,index,newLine,parentID,level,
  colspan,rowspan,width,height;//������ʽ��ķ��,�������
  private SheetAttribute st;
 // private HttpSession session;
  private RecordSet rs;
  private ConnStatement cstmt;
  /*******************
   �������ݿ��װ����
   */
  private LogMan log=LogMan.getInstance();
  /*******************
   �������
   */
  public SheetAttrOperator() {

  }

  /**********��ȡ�������
  @param request http����
  @throws Exception
  */
 public void doInit(HttpServletRequest request) throws Exception{
   attrName=StaticMethod.null2String(request.getParameter("attrName"),"");
   isattach=StaticMethod.getIntValue(request.getParameter("isattach"),0);
   index=StaticMethod.getIntValue(request.getParameter("index"),0);
   newLine=StaticMethod.getIntValue(request.getParameter("newLine"),0);
   parentID=0;//StaticMethod.getIntValue(request.getParameter("parentID"),0);
   level=0;//StaticMethod.getIntValue(request.getParameter("level"),0);
   align=StaticMethod.null2String(request.getParameter("align"),"");
   valign=StaticMethod.null2String(request.getParameter("valign"),"");
   colspan=StaticMethod.getIntValue(request.getParameter("colspan"),0);
   rowspan=StaticMethod.getIntValue(request.getParameter("rowspan"),0);
   width=StaticMethod.getIntValue(request.getParameter("width"),0);
   height=StaticMethod.getIntValue(request.getParameter("height"),0);
   nowrap = StaticMethod.null2String(request.getParameter("nowrap"), "nowrap");
   color=StaticMethod.null2String(request.getParameter("color")," ");

   sheetID=StaticMethod.getIntValue((String)request.getSession().getAttribute("sheetID"),0);

   st=new SheetAttribute(sheetID+"");
 }
 /*************************************************************
  ������¼�¼
  *@throws Exception
  * @return boolean �������ݿ�ɹ�����true�����򷵻�false
  */
 public boolean doInsert() throws Exception{
   boolean flag=false;
   try{
     //����taw_sheetanmne
     cstmt=new ConnStatement();
     String sql="insert into taw_sheetattr(sheet_id,attr_name,isattach,index1,newLine,parent_id,level1,"+
               "align,valign,colspan,rowspan,width,height,nowrap,color) "+
                "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
     cstmt.setStatementSql(sql);
     cstmt.setInt(1,sheetID);
     cstmt.setString(2,attrName);
     cstmt.setInt(3,isattach);
     cstmt.setInt(4,index);
     cstmt.setInt(5,newLine);
     cstmt.setInt(6,parentID);
     cstmt.setInt(7,level);
     cstmt.setString(8,align);
     cstmt.setString(9,valign);
     cstmt.setInt(10,colspan);
     cstmt.setInt(11,rowspan);
     cstmt.setInt(12,width);
     cstmt.setInt(13,height);
     cstmt.setString(14,nowrap);
     cstmt.setString(15,color);
     cstmt.executeUpdate();
     cstmt.commit();
     st.removeOSCache(sheetID+"");

     flag=true;
   }catch(Exception e){
     try{
       cstmt.rollback();
     }catch(Exception ex){}
     log.writeLog("SheetAttrOperator",e);
      throw new Exception("���ݿ����ʧ��");
   }finally{
     cstmt.close();
   }
   return flag;
 }
 /**������¼�¼**/
 public boolean doUpdate(HttpServletRequest request) throws Exception{
  cstmt=new ConnStatement();
  boolean flag=false;
  int attr_id=StaticMethod.getIntValue(request.getParameter("attr_id"),0);
   try{
     //����taw_sheetanmne
     String sql="update taw_sheetattr set attr_name=?,isattach=?,index1=?,newLine=?,"+
         "parent_id=?,level1=?,align=?,valign=?,colspan=?,rowspan=?,width=?,height=?,"+
         "nowrap=?,color=? where attr_id=?";
     cstmt.setStatementSql(sql);
     cstmt.setString(1,attrName);
     cstmt.setInt(2,isattach);
     cstmt.setInt(3,index);
     cstmt.setInt(4,newLine);
     cstmt.setInt(5,parentID);
     cstmt.setInt(6,level);
     cstmt.setString(7,align);
     cstmt.setString(8,valign);
     cstmt.setInt(9,colspan);
     cstmt.setInt(10,rowspan);
     cstmt.setInt(11,width);
     cstmt.setInt(12,height);
     cstmt.setString(13,nowrap);
     cstmt.setString(14,color);
     cstmt.setInt(15,attr_id);
     cstmt.executeUpdate();
     cstmt.commit();
     flag=true;
   }catch(Exception e){
     try{
       cstmt.rollback();
     }catch(Exception ex){}

     log.writeLog("SheetAttrOperator",e);
      throw new Exception("�޸����ݿ����ʧ��");
   }finally{
     cstmt.close();
   }
   return flag;
 }
 /*����ɾ����¼*/
 public boolean doDelete(HttpServletRequest request,HttpServletResponse response){
   boolean flag = false;
   ConnStatement cst = new ConnStatement();
   String sheet_id = request.getParameter("sheet_id");
   String attr_id = request.getParameter("attr_id");
   String sql = "delete from taw_sheetattr where sheet_id=? and attr_id=?";
   try {
     cst.setStatementSql(sql);
     cst.setString(1, sheet_id);
     cst.setString(2, attr_id);
     cst.executeUpdate();
     cst.commit();
     flag=true;
   }
   catch (Exception e) {
     try {
       cst.rollback();
     }
     catch (Exception ex) {}

   }
   finally {
     cst.close();
   }
   return flag;
 }
}
