<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<table cellpadding="0" class="table" cellspacing="0">

	<thead>
		<tr>
			<logic-el:present name="headList">
				<c:forEach items="${headList}" var="head">
				<th colspan=${head.rowspan}>${head.display}</th>
				</c:forEach>
			</logic-el:present>
		</tr>
	</thead>
	 <logic-el:present name="tabelList">
     <tbody>
     <c:forEach items="${tabelList}" var="tdList">
     <tr>
     <c:forEach items="${tdList}" var="td">
     
     <td rowspan="${td.rowspan}" colspan="${td.colspan}">
     <c:if test="${ empty td.url}">
     ${td.display}
     </c:if>
     <c:if test="${!empty td.url}">
     <a href="${app}${td.url}">${td.display}</a>
     </c:if>
     </td>
     </c:forEach>
     </tr>
     </c:forEach>
	  </tbody>
	  </logic-el:present>
</table>


<%@ include file="/common/footer_eoms.jsp"%>
