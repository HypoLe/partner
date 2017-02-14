<%@ page contentType="text/html;charset=gb2312"
import="com.boco.eoms.common.util.StaticMethod,com.boco.eoms.filemanager.extra.fileupload.*" %>
<%
        String temp = "";
        String nametemp = "";
        temp = request.getParameter("filepath");
        temp = "/workplan/tawwpfile/attmentfile/" + request.getParameter("filepath");
        nametemp = request.getParameter("filename");

	SmartUpload su = new SmartUpload();
	su.initialize(pageContext);
	su.setContentDisposition(null);

	su.downloadFile(temp,"",nametemp);
%>
