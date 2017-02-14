<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="com.boco.eoms.common.util.StaticMethod"%>
<%@ page import="java.util.List"%>
<%
String excelUrl = StaticMethod.nullObject2String(request.getAttribute("excelUrl"));
List monthKnowledgeCountOrder = (List)request.getAttribute("monthKnowledgeCountOrder");
%>

<table class="formTable">
	<caption>
		<div class="header center">本月知识库总量排行</div>
	</caption>
  <tr>
  	<td class="label">排行</td>
    <td class="label">知识库名</td>
    <td class="label">数量</td>
  </tr>
<%
List themeIds = (List)request.getAttribute("themeIds");
for(int i=0;i<monthKnowledgeCountOrder.size();i++){
	Object [] objs = (Object[])monthKnowledgeCountOrder.get(i);
%>
  <tr>
  	<td class="content"><%=i+1 %></td>
    <td class="content"><eoms:id2nameDB id="<%=objs[0].toString() %>" beanId="kmTableGeneralDao" /></td>
    <td class="content"><a href="/eomsMain/kmmanager/kmContentss.do?method=searchLib&node=<%=themeIds.get(i).toString() %>&nodeLeaf=false"><%=objs[1].toString() %></a></td>
  </tr>

<%
}
%>

</table>
<%@ include file="/common/footer_eoms.jsp"%>