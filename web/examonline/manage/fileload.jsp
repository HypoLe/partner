<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" language="java" import="com.boco.eoms.examonline.service.*, 
com.boco.eoms.commons.system.session.form.TawSystemSessionForm, com.boco.eoms.common.util.StaticMethod,
java.io.File, java.io.IOException,java.util.*"%>

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=utf-8 ">
<TITLE>提示信息</TITLE>
</HEAD>
<link rel="stylesheet" type="text/css" href="css.css" />
<BODY>
<%
   String fileName = org.intellect.web.tag.FormatTool.decode(request.getParameter("fileName"));
   
  String specialtySel=StaticMethod.null2String(request.getParameter("specialtySel"));
  String    manufacturerSel=StaticMethod.null2String(request.getParameter("manufacturerSel"));
  String    contype=StaticMethod.null2String(request.getParameter("contype"));
  String issueType=StaticMethod.null2String(request.getParameter("issueType"));
   String s_value=StaticMethod.null2String(request.getParameter("s_value"));
   fileName = pageContext.getServletContext().getRealPath("/") + "/studyonline" + File.separator + "upload" + File.separator + fileName;
   String difficulty=StaticMethod.null2String(request.getParameter("difficulty"));
   String errMsg = "";
   String tip = null;
 TawSystemSessionForm sessionForm = (TawSystemSessionForm)
    request.getSession().getAttribute("sessionform");
  // SaveSessionBeanForm sessionForm = (SaveSessionBeanForm) session.getAttribute("SaveSessionBeanForm");
   String userName = sessionForm.getUsername();

   boolean flag = false;

   ExamService studyOnlineBO = new ExamService();
   String tip2 = null;

   try {
       tip2 = studyOnlineBO.importwordWarehouse( fileName ,Integer.valueOf(specialtySel).intValue(),Integer.valueOf(manufacturerSel).intValue(),
       Integer.valueOf(contype).intValue(),Integer.valueOf(issueType).intValue(),Integer.valueOf(s_value).intValue(),Integer.valueOf(difficulty).intValue(),userName );
       tip = "提示";
   } catch (Exception ex) {
       ex.printStackTrace();
       errMsg = ex.getMessage();
       tip = "警告";
   }

%>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr valign="top">
   <td>
<table width="98%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr valign=bottom>
    <td width=40><img src="./image/it.jpg" width="120" height="85"></td>
    <td nowrap><B><%=tip %></B></td>
    <td align=right><br></td>
  </tr>
</table>
<table width="98%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr><td height="2" bgcolor="#9BB7EB"></td></tr>
  <tr><td height="2" bgcolor="#9BB7EB"></td></tr>
</table>

<p>&nbsp;</p>
<%
   if(!tip2.equalsIgnoreCase("error")){
     if( tip2.equalsIgnoreCase("all") ){
%>
     <p align="center">数据已导入成功</p>
<%     }
     else{
%>
       <p align="center">数据已导入.</p>
       <p align="center"><font color="red"><%=tip2 %>成功.请查看数据缺失情况</font></p>
<%     }
   }
   else{
%>
<table width="98%" border="0" cellspacing="0" cellpadding="0" align="center">
  <tr><td height="2" width="30%">&nbsp;</td><td height="2" width="68%">导入数据发生错误，可能是以下原因</td></tr>
  <tr><td height="2" width="30%">&nbsp;</td><td height="2" width="68%">1、导入的excel文件模板不正确</td></tr>
  <tr><td height="2" width="30%">&nbsp;</td><td height="2" width="68%">2、导入的excel文件数据不正确</td></tr>
  <tr><td height="2" width="30%">&nbsp;</td><td height="2" width="68%">3、系统问题</td></tr>
  <tr><td height="2" width="30%">&nbsp;</td><td height="2" width="68%">返回的错误信息是：<%= errMsg%></td></tr>
</table>
<%
   }
%>
</BODY>
</HTML>
