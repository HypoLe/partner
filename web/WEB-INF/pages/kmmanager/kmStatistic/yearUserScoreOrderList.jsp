<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="com.boco.eoms.common.util.StaticMethod"%>
<%@ page import="java.util.List"%>
<%
String excelUrl = StaticMethod.nullObject2String(request.getAttribute("excelUrl"));
List monthUserScoreOrderList = (List)request.getAttribute("monthUserScoreOrderList");
%>

<table class="formTable">
	<caption>
		<div class="header center">员工贡献本年排行</div>
	</caption>
  <tr>
  	<td class="label">排行</td>
    <td class="label">用户名</td>
    <td class="label">新建数</td>
  </tr>
  <%
for(int i=0;i<monthUserScoreOrderList.size();i++){
	Object [] objs = (Object[])monthUserScoreOrderList.get(i);
%>
  <tr>
  	<td class="content"><%=i+1 %></td>
    <td class="content"><eoms:id2nameDB id="<%=objs[0].toString() %>" beanId="tawSystemUserDao" /></td>
    <td class="content"><a href="/eomsMain/kmmanager/scoreOrderStatistic.do?method=yearUserScoreMsg&orderNumber=5&userName=<%=objs[0].toString()%>"><%=objs[1].toString() %></a></td>
  </tr>

<%
}
%>

</table>
<%@ include file="/common/footer_eoms.jsp"%>