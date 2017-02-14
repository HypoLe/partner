<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <logic:present name="querylist" scope="request">
   <display:table name="querylist" cellspacing="0" cellpadding="0"
      id="querylist"  pagesize="${pageSize}" class="table querylist" export="false" 
      requestURI="${app}/sheet/pnrgenerateelectricity/statistic.do?method=showIndexList"
      sort="list" partialList="true" size="resultSize">
		<display:column title="工单号"><a href="${app}/sheet/pnrgenerateelectricity/pnrgenerateelectricity.do?method=showMainDetailPage&sheetKey=${querylist.id}" >${querylist.sheetid}</a></display:column>
		<display:column title="工单主题">${querylist.title}</display:column>
		<display:column title="派单时间">${querylist.sendtime}</display:column>
		<display:column title="受理时限">${querylist.sheetacceptlimit}</display:column>
		<display:column title="完成时限">${querylist.sheetcompletelimit}</display:column>
    </display:table>
 </logic:present> 
</html>
