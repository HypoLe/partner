<%@ page language="java" pageEncoding="UTF-8" %>
<jsp:directive.page import="com.boco.eoms.partner.eva.util.PnrEvaConstants"/>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<table class="formTable" id="form">
	<caption>
		<div class="header left">提示信息</div>
	</caption>
	<tr>
		<c:if test="${ not empty message}">
		<td class="label">
			${message}
		</td>
		</c:if>
	</tr>
</table>
<table id="submit-btn" align="left">
	<tr>
		<td>
			<input type="submit" class="btn" value="返回" onClick="return back();"/>
		</td>
	</tr>
</table>
<script type="text/javascript" />
	function back() {
			window.location = "${app}/partner/eva/evaTasks.do";
	}
</script>
<%@ include file="/common/footer_eoms.jsp"%>
