<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'pnrEvaTreeForm'});
});
</script>
<div class="list-title">
	编辑考核指标分类
</div>

<html:form action="/evaTrees.do?method=saveNode" styleId="pnrEvaTreeForm" method="post"> 
<table>
	<tr height="30">
		<td width="30%">
			指标分类名称
		</td>
		<td width="70%">
			<html:text property="nodeName" styleId="nodeName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'请输入指标分类名称'" value="${pnrEvaTreeForm.nodeName}" />
		</td>
	</tr>
	<tr height="30">
		<td colspan="2">
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
		</td>
	</tr>
</table>

<html:hidden property="parentNodeId" value="${pnrEvaTreeForm.parentNodeId}" />
<html:hidden property="isLock" value="${pnrEvaTreeForm.isLock}" />
<html:hidden property="id" value="${pnrEvaTreeForm.id}" />
<html:hidden property="areaId" value="${pnrEvaTreeForm.creatArea}" />
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>
