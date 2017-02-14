<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'ctTableThemeForm'});
});
</script>

<html:form action="/ctTableThemes.do?method=save" styleId="ctTableThemeForm" method="post">

	<fmt:bundle	basename="com/boco/eoms/partner/contract/config/applicationResource-partner-contract">
		<table class="formTable">
			<caption>
				<div class="header center">
					<fmt:message key="ctTableTheme.form.heading" />
				</div>
			</caption>

			<tr>
				<td class="label">
					<fmt:message key="ctTableTheme.themeName" />
				</td>
				<td class="content">
					<html:text property="themeName" styleId="themeName"
						styleClass="text medium" alt="allowBlank:false,vtext:''"
						value="${ctTableThemeForm.themeName}" />
				</td>
			</tr>

			<tr>
				<td class="label">
					<fmt:message key="ctTableTheme.isOpen" />
				</td>
				<td class="content">
					<eoms:dict key="dict-partner-contract" dictId="isOrNot" isQuery="false"
						defaultId="${ctTableThemeForm.isOpen}" selectId="isOpen"
						beanId="selectXML" />
				</td>
			</tr>

			<tr>
				<td class="label">
					<fmt:message key="ctTableTheme.orderBy" />
				</td>
				<td class="content">
					<html:text property="orderBy" styleId="orderBy"
						styleClass="text medium"
						alt="vtype:'number',allowBlank:false,vtext:''"
						value="${ctTableThemeForm.orderBy}" />
				</td>
			</tr>
				
			<c:if test="${ctTableThemeForm.parentNodeId != 1}">
			<tr>
				<td class="label">
					 是否继承父节点操作
				</td>
				<td class="content">
					<eoms:dict key="dict-partner-contract" dictId="isOrNot"
						selectId="hasParentOperate" beanId="selectXML"
						alt="allowBlank:false,vtext:'请选择权限...'"
						defaultId="${ctTableThemeForm.hasParentOperate}" />
				</td>
			</tr>
			</c:if>
			<c:if test="${ctTableThemeForm.parentNodeId == 1}">
			    <html:hidden property="hasParentOperate" value="0" />
			</c:if>
		</table>

<br>

		<table>
			<tr>
				<td>
					<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
					<c:if test="${not empty ctTableThemeForm.id}">
						<input type="button" class="btn" 
						    value="<fmt:message key="button.delete"/>" 
						    onclick="javascript:if(confirm('<fmt:message key="ctMessage.delMessage"/>')){var url='${app}/partner/contract/ctTableThemes.do?method=remove&nodeId=${ctTableThemeForm.nodeId}';location.href=url}" />
					</c:if>
				</td>
			</tr>
		</table>

	</fmt:bundle>
			
	<html:hidden property="isDeleted" value="0" />
	<html:hidden property="id" value="${ctTableThemeForm.id}" />
	<html:hidden property="nodeId" value="${ctTableThemeForm.nodeId}" />
	<html:hidden property="parentNodeId"value="${ctTableThemeForm.parentNodeId}" />
	<html:hidden property="leaf" value="${ctTableThemeForm.leaf}" />
	<html:hidden property="isUsed" value="${ctTableThemeForm.isUsed}" />
	<html:hidden property="tableId" value="${ctTableThemeForm.tableId}" />

<br>
    <font color="red">
      <c:if test="${ctTableThemeForm.isUsed == 1}">
            注意：该合同分类已经被名称为“<eoms:id2nameDB id="${ctTableThemeForm.tableId}" beanId="ctTableGeneralDao" />”的合同模型使用！
      </c:if>            
      <c:if test="${ctTableThemeForm.isUsed == null || ctTableThemeForm.isUsed == '' || ctTableThemeForm.isUsed == 0}">
            注意：该合同分类未和合同模型绑定！ 
	  </c:if>
	</font>
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>