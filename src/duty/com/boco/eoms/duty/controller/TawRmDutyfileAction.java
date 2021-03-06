/**
 * @see
 * <p>功能描述：用于值班日值附件管理功能的类。</p>
 * @author 赵川
 * @version 2.0
 */



package com.boco.eoms.duty.controller;

import javax.sql.*;
import java.util.*;

import org.apache.commons.logging.LogFactory;


import javax.servlet.http.*;
import javax.servlet.*;

import org.apache.struts.action.*;
import org.apache.struts.util.*;

import com.boco.eoms.duty.model.*;
import com.boco.eoms.duty.util.DutyMgrLocator;
import com.boco.eoms.duty.dao.*;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.common.util.*;
import com.boco.eoms.common.controller.*;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;

import org.apache.struts.upload.FormFile;
import java.io.*;
import java.text.SimpleDateFormat;

public class TawRmDutyfileAction extends Action {
  private com.boco.eoms.db.util.ConnectionPool ds=com.boco.eoms.db.util.ConnectionPool.getInstance();
  private static int PAGE_LENGTH = 20;
  //整合调整关于国际化
  static {
    ResourceBundle prop = ResourceBundle.getBundle("resources.application_zh_CN");
    try {
      PAGE_LENGTH = Integer.parseInt(prop.getString("list.page.length"));
    }
    catch (Exception e) {
    }
  }
/*
  static {
    ResourceBundle prop = ResourceBundle.getBundle("resources.application");
    try {
      PAGE_LENGTH = Integer.parseInt(prop.getString("list.page.length"));
    } catch (Exception e) {
    }
  }
*/
  public ActionForward execute(ActionMapping mapping, ActionForm form,
      HttpServletRequest request, HttpServletResponse response) {
    ActionForward myforward = null;
    String myaction = mapping.getParameter();

    if (isCancelled(request)) {
      return mapping.findForward("cancel");
    }

    if ("".equalsIgnoreCase(myaction)) {
      myforward = mapping.findForward("failure");
    } else if ("DUTYFILE".equalsIgnoreCase(myaction)) {
      myforward = performDutyfile(mapping, form, request, response);
    } else if ("SAVE".equalsIgnoreCase(myaction)) {
      myforward = performSave(mapping, form, request, response);
    } else if ("DELETE".equalsIgnoreCase(myaction)) {
      myforward = performDelete(mapping, form, request, response);
    } else {
      myforward = mapping.findForward("failure");
    }
    return myforward;
  }


  /**
   * @see 显示值班日值附件
   * @param mapping
   * @param actionForm
   * @param request
   * @param response
   * @return
   */
  private ActionForward performDutyfile(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
	    //	edit by wangheqi 2.7 to 3.5
	 	TawSystemSessionForm saveSessionBeanForm = (TawSystemSessionForm)
	      request.getSession().getAttribute("sessionform");
	    /*SaveSessionBeanForm saveSessionBeanForm = (SaveSessionBeanForm)
	        httpServletRequest.getSession().getAttribute("SaveSessionBeanForm");*/
    TawRmDutyfileDAO tawRmDutyfileDAO=null;
    Vector vecDutyFile=null;

    //saveSessionBeanForm = (SaveSessionBeanForm)request.getSession().getAttribute("SaveSessionBeanForm");
    if (saveSessionBeanForm==null)
    {
      return mapping.findForward("timeout");
    }
    try{
      TawRmDutyfileForm form = (TawRmDutyfileForm) actionForm;
      if (form.getWorkserial()==0){
        int intWorkserial = Integer.parseInt(request.getParameter("WORKSERIAL"));
        form.setWorkserial(intWorkserial);
      }
      if (form.getWorkserial()!=0){
        //应该已经有值了
        tawRmDutyfileDAO = new TawRmDutyfileDAO(ds);
        vecDutyFile = tawRmDutyfileDAO.getDutyFile(form.getWorkserial());
        request.setAttribute("vecDutyFile",vecDutyFile);
      }
    }
    catch (Exception e)
    {
      return mapping.findForward("failure");
    }finally{
      saveSessionBeanForm=null;
      tawRmDutyfileDAO=null;
      vecDutyFile=null;
    }
    return mapping.findForward("success");
  }

  /**
   * @see 保存值班日值附件
   * @param mapping
   * @param actionForm
   * @param request
   * @param response
   * @return
   */
  private ActionForward performSave(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
	    //	edit by wangheqi 2.7 to 3.5
	 	TawSystemSessionForm saveSessionBeanForm = (TawSystemSessionForm)
	      request.getSession().getAttribute("sessionform");
	    /*SaveSessionBeanForm saveSessionBeanForm = (SaveSessionBeanForm)
	        httpServletRequest.getSession().getAttribute("SaveSessionBeanForm");*/
    String realPath=null;
    String separator=null;
    FormFile file=null;
    String fileName=null;
    SimpleDateFormat dateFormat=null;
    String date=null;
    java.util.Date puredate=null;
    InputStream stream=null;
    OutputStream bos=null;
    TawRmDutyfileDAO tawRmDutyfileDAO=null;
    TawRmDutyfile tawRmDutyfile=null;
    java.util.Date date1=null;
    SimpleDateFormat dateformat=null;
    String strTransTime=null;
    String strDutyMan=null;

    //saveSessionBeanForm = (SaveSessionBeanForm)request.getSession().getAttribute("SaveSessionBeanForm");
    if (saveSessionBeanForm==null)
    {
      return mapping.findForward("timeout");
    }

    TawRmDutyfileForm form = (TawRmDutyfileForm) actionForm;

    try {
      //获得系统路径和分割符
      realPath= saveSessionBeanForm.getRealPath();
      separator=File.separator;
      //取文件及名称
      file= form.getAttachName();
      fileName = form.getFilename();
      //时间
      puredate = new java.util.Date();
      dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
      date = dateFormat.format(puredate);
      //加时间戳
      //zc 2004-01-08 修改文件名从test2008-01-08改成2004-01-0832的形式
      int dotLocation=fileName.lastIndexOf('.');
      String fileName1="";
      String attend=String.valueOf(puredate.getTime());  //取得毫秒数
      attend=attend.substring(attend.length()-2,attend.length()); //取毫秒的后两位,避免有同时上传的可能性
      fileName1 = date+attend;
  		String timeTag = StaticMethod.getCurrentDateTime("yyyyMMddHHmmss");
	String timeDate = StaticMethod
			.getCurrentDateTime("yyyy-MM-dd HH:mm:ss");
	String uploadPath = DutyMgrLocator.getAttributes().getDutyRootPath()
			+ "/" ;
	String dbPath = uploadPath + timeTag + ".xls";
	String sysTemPaht = request.getRealPath("/"); // 取当前系统路径
	String path = sysTemPaht + uploadPath;
	String filePath = sysTemPaht + dbPath;
      if (dotLocation!=-1)
      {
        fileName1 = fileName1 +  fileName.substring(dotLocation);
      }
      //zc 2004-01-08
      //上传文件
      stream = file.getInputStream();
      bos = new FileOutputStream(separator+realPath + "/duty" + separator + "upload" + separator + StaticMethod.null2String(fileName1));

      int bytesRead = 0;
      byte[] buffer = new byte[8192];
      while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
        bos.write(buffer, 0, bytesRead);
      }
      bos.close();
      stream.close();

      //写数据库
      tawRmDutyfileDAO = new TawRmDutyfileDAO(ds);
      tawRmDutyfile = new TawRmDutyfile();
      //org.apache.commons.beanutils.BeanUtils.populate(tawRmDutyfile, org.apache.commons.beanutils.BeanUtils.describe(form));
      //要写入数据库的信息
      //班次
      tawRmDutyfile.setWorkserial(form.getWorkserial());
      //时间
      //java.util.Date date1 = new java.util.Date();
      //String strTransTime = date1.();
      //上面的时间在unix环境下将不能正确执行，改成以下的代码--update by dudajiang 2003-9-18
      date1 = new java.util.Date();
      dateformat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      strTransTime = dateformat.format(date1);
      tawRmDutyfile.setTranstime(strTransTime);
      //人
      strDutyMan = saveSessionBeanForm.getUserid();
      tawRmDutyfile.setDutyman(strDutyMan);
      //
 
 
     /* tawRmDutyfile.setFilename(new String(form.getFilename() .getBytes("ISO-8859-1"), "UTF-8"));*/
      tawRmDutyfile.setFilename(form.getFilename());
 
 
      tawRmDutyfile.setEncodename(fileName1);
      tawRmDutyfile.setNotes("");

      tawRmDutyfileDAO.insert(tawRmDutyfile);
    } catch (Exception e) {
      generalError(request, e);
      return mapping.findForward("failure");
    }finally{
      saveSessionBeanForm=null;
      realPath=null;
      separator=null;
      file=null;
      fileName=null;
      dateFormat=null;
      date=null;
      puredate=null;
      stream=null;
      bos=null;
      tawRmDutyfileDAO=null;
      tawRmDutyfile=null;
      date1=null;
      dateformat=null;
      strTransTime=null;
      strDutyMan=null;
    }
    return mapping.findForward("success");
  }

  /**
   * @see 删除值班日值附件
   * @param mapping
   * @param actionForm
   * @param request
   * @param response
   * @return
   */
  private ActionForward performDelete(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
	    //	edit by wangheqi 2.7 to 3.5
	 	TawSystemSessionForm saveSessionBeanForm = (TawSystemSessionForm)
	      request.getSession().getAttribute("sessionform");
	    /*SaveSessionBeanForm saveSessionBeanForm = (SaveSessionBeanForm)
	        httpServletRequest.getSession().getAttribute("SaveSessionBeanForm");*/
    String selected_id =null;
    TawRmDutyfileDAO tawRmDutyfileDAO=null;

    //saveSessionBeanForm = (SaveSessionBeanForm)request.getSession().getAttribute("SaveSessionBeanForm");
    if (saveSessionBeanForm==null)
    {
      return mapping.findForward("timeout");
    }

    try {
      selected_id = "("+String.valueOf(request.getParameter("selected_id"))+")";
      tawRmDutyfileDAO = new TawRmDutyfileDAO(ds);
      tawRmDutyfileDAO.delete(selected_id);
    } catch (Exception e) {
      generalError(request, e);
      return mapping.findForward("failure");
    }finally{
      saveSessionBeanForm=null;
      selected_id =null;
      tawRmDutyfileDAO=null;
    }
    return mapping.findForward("success");
  }


  private void sqlDuplicateError(HttpServletRequest request, String objName) {
    ActionErrors aes = new ActionErrors();
    aes.add(aes.GLOBAL_ERROR, new ActionError("errors.database.duplicate", objName));
    saveErrors(request, aes);

  }

  private void generalError(HttpServletRequest request, Exception e) {
    ActionErrors aes = new ActionErrors();
    aes.add(aes.GLOBAL_ERROR, new ActionError("error.general", e.getMessage()));
    saveErrors(request, aes);
    e.printStackTrace();

  }
}
