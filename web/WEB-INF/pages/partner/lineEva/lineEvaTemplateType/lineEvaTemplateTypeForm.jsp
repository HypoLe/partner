<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'lineEvaTreeForm'});
});
</script>
<div class="list-title">
	考核模板分类
</div>

<html:form action="/lineEvaTemplateTypes.do?method=saveTemplateType" styleId="lineEvaTreeForm" method="post"> 
<table>
	<tr height="30">
		<td width="30%">
			模板分类名称
		</td>
		<td width="70%">
			<html:text property="nodeName" styleId="nodeName"
						styleClass="text medium" style="width:88%" 
						alt="allowBlank:false,vtext:'请输入模板分类名称'" value="${lineEvaTreeForm.nodeName}" />
		</td>
	</tr>
	<tr height="30">
		<td>
			备注
		</td>
		<td>
			<html:textarea property="nodeRemark" styleId="nodeRemark" style="width:88%" 
						styleClass="textarea"  value="${lineEvaTreeForm.nodeRemark}" />
		</td>
	</tr>
	<tr height="30">
		<td colspan="2">
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
		</td>
	</tr>
</table>

<html:hidden property="parentNodeId" value="${lineEvaTreeForm.parentNodeId}" />
<html:hidden property="id" value="${lineEvaTreeForm.id}" />
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>