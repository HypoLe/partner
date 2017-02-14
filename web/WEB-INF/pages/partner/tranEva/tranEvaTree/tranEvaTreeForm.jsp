<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'tranEvaTreeForm'});
});
</script>
<div class="list-title">
	编辑考核指标分类
</div>

<html:form action="/tranEvaTrees.do?method=saveNode" styleId="tranEvaTreeForm" method="post"> 
<table>
	<tr height="30">
		<td width="30%">
			指标分类名称
		</td>
		<td width="70%">
			<html:text property="nodeName" styleId="nodeName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'请输入指标分类名称'" value="${tranEvaTreeForm.nodeName}" />
		</td>
	</tr>
	<tr height="30">
		<td colspan="2">
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
		</td>
	</tr>
</table>

<html:hidden property="parentNodeId" value="${tranEvaTreeForm.parentNodeId}" />
<html:hidden property="id" value="${tranEvaTreeForm.id}" />
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>