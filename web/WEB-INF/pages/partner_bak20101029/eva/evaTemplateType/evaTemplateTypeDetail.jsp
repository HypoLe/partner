<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<div class="list-title">
	考核模板分类
</div>


<table class="formTable">
	<tr >
		<td class="label">
			模板分类名称
		</td>
		<td class="content" colspan="3">
			${pnrEvaTreeForm.nodeName}
		</td>
	</tr>
	<tr>
		<td class="label">
			备注
		</td>
		<td class="content" colspan="3">
			${pnrEvaTreeForm.nodeRemark}
		</td>
	</tr>

</table>

<%@ include file="/common/footer_eoms.jsp"%>
