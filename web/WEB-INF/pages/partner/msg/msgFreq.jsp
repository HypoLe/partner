<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import ="com.boco.eoms.common.util.StaticMethod"%>
<script type="text/javascript" src="<%=request.getContextPath() %>/scripts/partner/msgwin.js"></script>

<%
String str = StaticMethod.nullObject2String(request.getAttribute("msg"));
%>
<html>
  <head>
  </head>
  <body>
 </body>
 <%
 if(!("").equals(str)){
 %>
<script type="text/javascript">
 popUpMsg("top","提醒窗","","<%=str %>",-1);
</script>
  <%
 }
 %>
</html>

