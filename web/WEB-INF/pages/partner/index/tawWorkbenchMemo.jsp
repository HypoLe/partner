<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<style type="text/css">
  	body{background-image:none;}
</style>
<div class="list">
<table  width="100%" height="100%">
<tr>
<td>
  <c:forEach var='workbenchMemo' items='${tawWorkbenchMemoList}' varStatus="stauts">
	${stauts.count}、${workbenchMemo.title}:${workbenchMemo.content}<br/><br/>
  </c:forEach>
  </td>
  </tr>
</table>
 </div>
<%@ include file="/common/footer_eoms.jsp"%>