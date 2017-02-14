<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="com.boco.eoms.common.util.StaticMethod"%>
<%@ page import="java.util.List"%>
<%
String excelUrl = StaticMethod.nullObject2String(request.getAttribute("excelUrl"));
List knowledgeCountOrder = (List)request.getAttribute("knowledgeCountOrder");

%>

<table class="formTable">
	<caption>
		<div class="header center">知识库总量排行</div>
	</caption>
  <tr>
  	<td class="label">排行</td>
    <td class="label">知识库名</td>
    <td class="label">数量</td>
  </tr>
  <%
for(int i=0;i<knowledgeCountOrder.size();i++){
	Object [] objs = (Object[])knowledgeCountOrder.get(i);
	List idList = (List)request.getAttribute("themeIds");
%>
  <tr>
  	<td class="content"><%=i+1 %></td>
    <td class="content"><eoms:id2nameDB id="<%=objs[0].toString() %>" beanId="kmTableGeneralDao" /></td>
    <td class="content"><a href="/eomsMain/kmmanager/kmContentss.do?method=search&node=<%=idList.get(i).toString() %>&nodeLeaf=false"><%=objs[1].toString() %></a></td>
  </tr>

<%
}
%>

</table>
<%@ include file="/common/footer_eoms.jsp"%>