<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'assCollectionConfigForm'});
});
</script>

<html:form action="/assCollectionConfigs.do?method=save" styleId="assCollectionConfigForm" method="post"> 

<table class="formTable">
	<caption>
		<div class="header center">采集配置</div>
	</caption>

	<tr>
		<td class="label">
			采集名称
		</td>
		<td class="content">
			<html:text property="name" styleId="name"
						styleClass="text medium" style="width:95%;"  
						alt="allowBlank:false,vtext:''" value="${assCollectionConfigForm.name}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			查询语句
		</td>
		<td class="content">
			<html:text property="sql" styleId="sql"
						styleClass="text medium" style="width:95%;"  
						alt="allowBlank:false,vtext:''" value="${assCollectionConfigForm.sql}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			地市对应字段
		</td>
		<td class="content">
			<html:text property="areaColumn" styleId="areaColumn"
						styleClass="text medium" style="width:95%;"  
						value="${assCollectionConfigForm.areaColumn}" />
		</td>
	</tr>
	
	<tr>
		<td class="label">
			专业对应字段
		</td>
		<td class="content">
			<html:text property="specialtyColumn" styleId="specialtyColumn"
						styleClass="text medium" style="width:95%;"  
						value="${assCollectionConfigForm.specialtyColumn}" />
		</td>
	</tr>
	
	<tr>
		<td class="label">
			合作伙伴对应字段
		</td>
		<td class="content">
			<html:text property="partnerColumn" styleId="partnerColumn"
						styleClass="text medium" style="width:95%;"  
						value="${assCollectionConfigForm.partnerColumn}" />
		</td>
	</tr>
	
	<tr>
		<td class="label">
			时间对应字段
		</td>
		<td class="content">
			<html:text property="timeColumn" styleId="timeColumn"
						styleClass="text medium" style="width:95%;"  
						value="${assCollectionConfigForm.timeColumn}" />
		</td>
	</tr>			
</table>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="保存" />
			<c:if test="${not empty assCollectionConfigForm.id}">
				<input type="button" class="btn" value="删除" 
					onclick="javascript:if(confirm('你确定删除?')){
						var url='${app}/partner/assess/assCollectionConfigs.do?method=remove&id=${assCollectionConfigForm.id}&nodeId=${assCollectionConfigForm.treeNodeId}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${assCollectionConfigForm.id}" />
<html:hidden property="treeNodeId" value="${assCollectionConfigForm.treeNodeId}" />
<html:hidden property="parentNodeId" value="${parentNodeId}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>