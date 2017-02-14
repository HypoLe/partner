<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'assCollectionTypeForm'});
});
</script>

<html:form action="/assCollectionTypes.do?method=save" styleId="assCollectionTypeForm" method="post"> 

<table class="formTable">
	<caption>
		<div class="header center">采集分类</div>
	</caption>

	<tr>
		<td class="label">
			分类名称
		</td>
		<td class="content">
			<html:text property="name" styleId="name"
						styleClass="text medium" style="width:95%;"  
						alt="allowBlank:false,vtext:''" value="${assCollectionTypeForm.name}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			服务地址
		</td>
		<td class="content">
			<html:text property="serviceAddr" styleId="serviceAddr"
						styleClass="text medium" style="width:95%;"  
						alt="allowBlank:false,vtext:''" value="${assCollectionTypeForm.serviceAddr}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			服务用户名
		</td>
		<td class="content">
			<html:text property="userName" styleId="userName"
						styleClass="text medium" style="width:95%;"  
						alt="allowBlank:false,vtext:''" value="${assCollectionTypeForm.userName}" />
		</td>
	</tr>
	
	<tr>
		<td class="label">
			服务密码
		</td>
		<td class="content">
			<html:text property="password" styleId="password"
						styleClass="text medium" style="width:95%;"  
						alt="allowBlank:false,vtext:''" value="${assCollectionTypeForm.password}" />
		</td>
	</tr>	
</table>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="保存" />
			<c:if test="${not empty assCollectionTypeForm.id}">
				<input type="button" class="btn" value="删除" 
					onclick="javascript:if(confirm('你确定删除?')){
						var url='${app}/partner/assess/assCollectionTypes.do?method=remove&id=${assCollectionTypeForm.id}&nodeId=${assCollectionTypeForm.treeNodeId}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${assCollectionTypeForm.id}" />
<html:hidden property="treeNodeId" value="${assCollectionTypeForm.treeNodeId}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>