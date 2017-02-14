<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<script language="javascript" src="${app}/gis/js/My97DatePicker/WdatePicker.js" refer='true'></script>

<form action="synchExceRecord.do?method=search" method="post">
	<center>
		<table class="formTable">

			<caption>
				<div class="header center">
					同步异常数据列表查询
				</div>
			</caption>
			<tr>
				<td class="label">异常类型</td>
				<td><input type="text" name="dataType" class="text medium" /></td>
				<td class="label">异常发生日期</td>
				<td><input name="createTime" type="text" onClick="WdatePicker()" class="text medium"/></td>
			</tr>
		</table>
	</center>
	<table>
		<tr>
			<td>
				<input type="submit" class="btn" value="查询" />
			</td>
		</tr>
	</table>
</form>


<display:table name="list" cellspacing="0" cellpadding="0" id="list"
	class="table list" export="false" sort="list" pagesize="${pageSize}"
	requestURI="synchExceRecord.do" partialList="true" size="${size}">
	<display:column property="cuid" sortable="true"
		headerClass="sortable" title="CUID" />
	<display:column property="dataType" sortable="true"
		headerClass="sortable" title="异常类型" />
	<display:column property="exceptionField" sortable="true"
		headerClass="sortable" title="异常字段" />
	<display:column property="exceptionReason" sortable="true"
		headerClass="sortable" title="异常原因" />
	<display:column property="createTime" sortable="true"
		headerClass="sortable" title="异常产生日期"
		format="{0,date,yyyy-MM-dd HH:mm:ss}" />
</display:table>

<c:out value="${buttons}" escapeXml="false" />

<%@ include file="/common/footer_eoms.jsp"%>