<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'pnrEvaKpiForm'});
});
</script>

<html:form action="/evaKpis.do?method=saveKpi" styleId="pnrEvaKpiForm" method="post"> 
<table class="formTable" id="form" style="width:88%" align="center">
	<caption>
		<div class="header center">编辑考核指标</div>
	</caption>
	<tr>
		<td class="label">
			指标名称
		</td>
		<td class="content">
			<html:text property="kpiName" styleId="kpiName" style="width:88%" 
						styleClass="text medium"
						alt="allowBlank:false,vtext:'请输入指标名称'" value="${pnrEvaKpiForm.kpiName}" />
		</td>
	</tr>
	<tr>
		<td class="label">
			备注
		</td>
		<td class="content">
			<html:textarea property="remark" styleId="remark" style="width:88%" 
						styleClass="textarea"  value="${pnrEvaKpiForm.remark}" />
		</td>
	</tr>
</table>
<table style="width:88%" align="center">
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
		</td>
	</tr>
</table>
<html:hidden property="parentNodeId" value="${requestScope.parentNodeId}" />
<html:hidden property="id" value="${pnrEvaKpiForm.id}" />
<html:hidden property="area" value="${areaId}" />
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>
