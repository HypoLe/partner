<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%>



<form action="${app}/partner/feeManage/feeCountManage.do?method=addFeeCountFilterValue"
	method="post" id="filrer" name="filter">
	<table class="table">
	<tr>
		<td>
			选择业务类型
		<td>
			<select id="columnName" name="columnName" class="text"
				>
 <c:forEach var="feeFilter" items="${feeFilterList}" >
		<option  value="${feeFilter.columnName}">${feeFilter.businessName} </option>
		</c:forEach>

			</select>
		</td>
		<td>添加内容</td>
		<td>
			<input class="text" type="text" name="value" id="value" /></td></tr>

	</table>
	
		<input type="submit" value="提交"  />


</form>









<%@ include file="/common/footer_eoms.jsp"%>