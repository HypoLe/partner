<%@ page contentType="text/html; charset=GB2312" %>
<%@ page import ="com.boco.eoms.common.util.StaticMethod"%>
<HTML>
<HEAD>
<META NAME="GENERATOR" Content="Microsoft FrontPage 4.0">
<TITLE>

<%
out.println(StaticMethod.dbNull2String(request.getParameter("title")));
%>
</TITLE>
</HEAD>
<BODY leftmargin=0 topmargin=0 bottommargin=0 rightmargin=0>
<script>
a=dialogArguments;
</script>


<IFRAME NAME="myUrl" id="myUrl" src="<%=request.getParameter("myUrl")%>"
 STYLE="HEIGHT: 100%; width:100%; MARGIN-TOP: 8px;scroll: auto;scrollbar-face-color:#31CFF0;"
            frameborder=0>
</IFRAME>

</BODY>
</HTML>
