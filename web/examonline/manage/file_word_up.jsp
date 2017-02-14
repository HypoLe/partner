
<%@ page
language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"
%>
<%
String path1 = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1+"/";
%>
 <%@ page import="org.apache.commons.fileupload.servlet.ServletFileUpload" %>
<%@ page import="org.apache.commons.fileupload.FileItem,
org.apache.commons.fileupload.FileItemIterator,org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@ page import="org.apache.commons.fileupload.servlet.ServletRequestContext" %>
<%@ page import="org.apache.commons.fileupload.RequestContext,java.util.*" %>
<%@ page import="com.jspsmart.upload.*" %>
<%@ page import="com.boco.eoms.common.util.*" %>
<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META name="GENERATOR" content="IBM WebSphere Studio">
<TITLE>数据导入</TITLE>
<style type="text/css">
<!--
body {  font-family: "宋体"; font-size: 12px}
table {  font-family: "宋体"; font-size: 12px}
-->
</style>
<%

 %>
<%!
  private static final String ERR_MSG = "<strong>上传文件失败，可能是由于以下原因：</strong>"+
  										   "<div align='left'> 1. 网络忙。 </div> "+
  										   "<div align='left'> 2. 您上传的文件过大。系统允许的单个文件最大不超过8M。</div>" +
  										   "<div align='left'> 3. 您的文件格式不对。系统允许的文件格式为doc。</div>"+
  										   "<div align='left'> 4. 您的文件在服务器上已经存在。 </div>"+
  										   "<strong>请确认后重传，若仍有问题，请与系统管理员联系</strong>";

%>
<%   
    StringBuffer sb=new StringBuffer();
    DiskFileItemFactory factory = new DiskFileItemFactory();
ServletFileUpload upload = new ServletFileUpload(factory);
List items = upload.parseRequest(request);
Iterator iter = items.iterator();
Map map=new HashMap();
while (iter.hasNext()) {
    FileItem item = (FileItem) iter.next();
    String name = item.getFieldName();
    if (item.isFormField()) {
    sb.append("&").append(item.getFieldName()).append("=").append(item.getString());
        System.out.println("Form field " + name + " with value "+item.getString()+ " detected.");
    } else {
      String path=basePath+"studyonline/upload/";
      System.out.println("fpath:"+path);
      java.io.File dic = new java.io.File(path);
					boolean m=dic.exists()&&dic.isDirectory();
					if(!m)
					{
					m=dic.mkdirs();
					}
					
		String fileName=item.getName().substring(item.getName().lastIndexOf("\\")+1);	
		item.write(new java.io.File(path+java.io.File.separator+fileName));
		  sb.append("&fileName").append("=").append(org.intellect.web.tag.FormatTool.encode(fileName));
        System.out.println("File field " + name + " with file name "
            + item.getName() + " detected."+item);
    }
}
 sb.deleteCharAt(0);
 //response.setCharacterEncoding("utf-8");
%>
</HEAD>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" bgcolor="#ffffff" 
onload="location.replace('fileload.jsp?<%=sb.toString()%>')">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
  <tr>
     <td align="center" width="100%" valign="center" height="48">
      <FONT size=4 color="#0000FF" face="宋体"><b>数据导入</b></FONT>
    </td>
  </tr>
  <tr>
     <td align="center" width="100%" valign="center" height="100%">
      <p align="center"><img src="../../images/studyonline/wait.GIF" align="bottom" width="60" height="60"></p>
      <p align="center">　</p>
      <p align="center"><FONT size=4 color="#800040" face="宋体">正在导入数据,请稍候....</FONT></p>
    </td>
  </tr>
</table>
</BODY>
</HTML>




