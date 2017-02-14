<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="com.boco.eoms.common.util.StaticMethod"%>
<%@ page import="java.util.List"%>
<%
String excelUrl = StaticMethod.nullObject2String(request.getAttribute("excelUrl"));
List userScoreMsgList = (List)request.getAttribute("monthUserScoreMsgList");
%>

<table class="formTable">
	<caption>
		<div class="header center">本月员工贡献信息</div>
	</caption>
  <tr>
  	
    <td class="label">用户名</td> 
    <td class="label">标题</td>
  </tr>
  <%
for(int i=0;i<userScoreMsgList.size();i++){
	Object [] objs = (Object[])userScoreMsgList.get(i);
%>
  <tr>

    <td class="content"><eoms:id2nameDB id="<%=objs[0].toString() %>" beanId="tawSystemUserDao" /></td>
    <td class="content"><%=objs[1].toString() %></td>
  </tr>

<%
}
%>

</table>
<%@ include file="/common/footer_eoms.jsp"%>