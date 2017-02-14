package com.boco.eoms.autosheet.util;


/**
 * <p>Title: ���ϵͳԼ��</p>
 * <p>Description: ���ϵͳԼ��</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
import com.boco.eoms.common.util.*;
import com.boco.eoms.db.util.*;
import java.io.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

public class CheckContraint {

  /********************************************************
   �����ڲ�����
   */
  private PrintWriter out;
  private HttpServletRequest request;
  private String sheetID="";
  private SheetValue shValue=null;

  /**���캯����ʼ������ֵ
   * @params sheetID �����
   * @params out printout
   * */
  public CheckContraint(String sheetID,PrintWriter out,HttpServletRequest request){
    this.out=out;
    this.sheetID=sheetID;
    this.request=request;
    this.shValue=new SheetValue(sheetID);
  }

  /**��֤������޸��������������*/
  public void checkConstraint(){
    Hashtable checkhash = new Hashtable();
    out.println("<script language=\"javascript\" src=\""+request.getContextPath()+"/autosheet/control.js\"></script>");
    out.println("<script language=\"javascript\">");
    out.println("function checkInput(){");
    shValue.reset();
    int i=0;
    while(shValue.next()){
      if((shValue.getVstoretype().substring(0,3).equals("int")||shValue.getVstoretype().substring(0,3).equals("flo"))&&(shValue.getShowtype().equals("0"))){
        out.println("var "+shValue.getVName()+"=document.form1."+shValue.getVName()+".value;");
        if(shValue.getVCtrl().equals("0")){
          checkhash.put(i+"","isNull("+shValue.getVName()+")&&");
          i++;
        }
        checkhash.put(i+"","isNumber("+shValue.getVName()+")&&");
        i++;
      }else if((shValue.getVstoretype().substring(0,3).equals("cha")||shValue.getVstoretype().substring(0,3).equals("var"))&&(shValue.getShowtype().equals("0")||shValue.getShowtype().equals("5"))){
        if(shValue.getVCtrl().equals("0")){
          out.println("var "+shValue.getVName()+"=document.form1."+shValue.getVName()+".value;");
          checkhash.put(i+"","isNull("+shValue.getVName()+")&&");
          i++;
        }
      }else if(shValue.getVstoretype().substring(0,3).equals("dat")){
        out.println("var "+shValue.getVName()+"Year1=document.form1."+shValue.getVName()+"Year1.value;");
        out.println("var "+shValue.getVName()+"Month1=document.form1."+shValue.getVName()+"Month1.value;");
        out.println("var "+shValue.getVName()+"Day1=document.form1."+shValue.getVName()+"Day1.value;");
        checkhash.put(i+"","isDateInput("+shValue.getVName()+"Year1,"+shValue.getVName()+"Month1,"+shValue.getVName()+"Day1)&&");
        i++;
      }
    }
    String ifStr="";
    for(int j=0;j<i;j++){
      ifStr+=(String)checkhash.get(j+"");
    }
    ifStr="if("+ifStr+"(1==1))";
    out.println(ifStr);
    out.println("{return true;}else{return false;}");
    out.println("  }");
    out.println("</script>");
  }

  /**��֤��ѯ�������������
   */
  public void checkConsQuery(){
    Hashtable checkhash = new Hashtable();
    out.println("<script language=\"javascript\" src=\""+request.getContextPath()+"/autosheet/control.js\"></script>");
    out.println(" <script language=\"javascript\">");
    out.println("function checkInput(){");
    shValue.reset();
    int i=0;
    while(shValue.next()){
    //  if(shValue.getIsQuery().equals("1")){
        if((shValue.getVstoretype().substring(0,3).equals("int")||shValue.getVstoretype().substring(0,3).equals("flo"))&&(shValue.getShowtype().equals("0"))){
          out.println("var "+shValue.getVName()+"1=document.form1."+shValue.getVName()+"1.value;");
          out.println("var "+shValue.getVName()+"2=document.form1."+shValue.getVName()+"2.value;");
        //  checkhash.put(i+"","isNull("+shValue.getVName()+"1)&&");
        //  i++;
       //   checkhash.put(i+"","isNull("+shValue.getVName()+"2)&&");
       //   i++;
          checkhash.put(i+"","isNumber("+shValue.getVName()+"1)&&");
          i++;
          checkhash.put(i+"","isNumber("+shValue.getVName()+"2)&&");
          i++;
          checkhash.put(i+"","compareTwoNum("+shValue.getVName()+"1,"+shValue.getVName()+"2)&&");
          i++;
        }else if((shValue.getVstoretype().substring(0,3).equals("cha")||shValue.getVstoretype().substring(0,3).equals("var"))&&(shValue.getShowtype().equals("0")||shValue.getShowtype().equals("5"))){
          if(shValue.getVCtrl().equals("0")){
            out.println("var "+shValue.getVName()+"=document.form1."+shValue.getVName()+".value;");
         //   checkhash.put(i+"","isNull("+shValue.getVName()+")&&");
       //     i++;
          }
        }else if(shValue.getVstoretype().substring(0,3).equals("dat")){
          out.println("var "+shValue.getVName()+"Year1=document.form1."+shValue.getVName()+"Year1.value;");
          out.println("var "+shValue.getVName()+"Month1=document.form1."+shValue.getVName()+"Month1.value;");
          out.println("var "+shValue.getVName()+"Day1=document.form1."+shValue.getVName()+"Day1.value;");
          out.println("var "+shValue.getVName()+"Year2=document.form1."+shValue.getVName()+"Year2.value;");
          out.println("var "+shValue.getVName()+"Month2=document.form1."+shValue.getVName()+"Month2.value;");
          out.println("var "+shValue.getVName()+"Day2=document.form1."+shValue.getVName()+"Day2.value;");
          checkhash.put(i+"","isDate("+shValue.getVName()+"Year1,"+shValue.getVName()+"Month1,"+shValue.getVName()+"Day1,"+shValue.getVName()+"Year2,"+shValue.getVName()+"Month2,"+shValue.getVName()+"Day2)&&");
          i++;
        }
      //}
    }
      String ifStr="";
      for(int j=0;j<i;j++){
        ifStr+=(String)checkhash.get(j+"");
      }
      ifStr="if("+ifStr+"(1==1))";
      out.println(ifStr);
      out.println(" return true;else return false;");
      out.println("  }");
      out.println("</script>");
  }
}
