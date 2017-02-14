<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form_self.jsp"%>
<head>
<base target="_self" />
</head>
<!-- 根据后台数据呈现表格 -->
<c:forEach  items="${mainMaterialTableList}" var="mainMaterialTable" >
	<table class="formTable">
	<tr>
		<c:forEach items="${mainMaterialTable.tableHeader}" var="tableHeader">
			<td style="background-color: #f7f7f7;vertical-align: top;">${tableHeader}</td>
		</c:forEach>
	</tr>
	
	<c:forEach items="${mainMaterialTable.tableData}" var="ds">
		<tr>
			<c:forEach items="${ds}" var="d">
				<td>${d}</td>
			</c:forEach>
		</tr>
	</c:forEach>
	</table>	
</c:forEach>	


<%@ include file="/common/footer_eoms.jsp"%>
