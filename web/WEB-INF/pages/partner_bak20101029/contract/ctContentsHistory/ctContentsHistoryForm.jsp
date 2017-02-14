<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'ctContentsHistoryForm'});
});
</script>

<html:form action="/ctContentsHistorys.do?method=save" styleId="ctContentsHistoryForm" method="post"> 

<fmt:bundle	basename="com/boco/eoms/partner/contract/config/applicationResource-partner-contract">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="ctContentsHistory.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="ctContentsHistory.themeId" />
		</td>
		<td class="content">
			<html:text property="themeId" styleId="themeId"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctContentsHistoryForm.themeId}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctContentsHistory.tableId" />
		</td>
		<td class="content">
			<html:text property="tableId" styleId="tableId"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctContentsHistoryForm.tableId}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctContentsHistory.createUser" />
		</td>
		<td class="content">
			<html:text property="createUser" styleId="createUser"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctContentsHistoryForm.createUser}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctContentsHistory.createDept" />
		</td>
		<td class="content">
			<html:text property="createDept" styleId="createDept"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctContentsHistoryForm.createDept}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctContentsHistory.createTime" />
		</td>
		<td class="content">
			<html:text property="createTime" styleId="createTime"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctContentsHistoryForm.createTime}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctContentsHistory.modifyUser" />
		</td>
		<td class="content">
			<html:text property="modifyUser" styleId="modifyUser"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctContentsHistoryForm.modifyUser}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctContentsHistory.modifyDept" />
		</td>
		<td class="content">
			<html:text property="modifyDept" styleId="modifyDept"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctContentsHistoryForm.modifyDept}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctContentsHistory.modifyTime" />
		</td>
		<td class="content">
			<html:text property="modifyTime" styleId="modifyTime"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctContentsHistoryForm.modifyTime}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctContentsHistory.contentTitle" />
		</td>
		<td class="content">
			<html:text property="contentTitle" styleId="contentTitle"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctContentsHistoryForm.contentTitle}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctContentsHistory.contentKeys" />
		</td>
		<td class="content">
			<html:text property="contentKeys" styleId="contentKeys"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctContentsHistoryForm.contentKeys}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctContentsHistory.contentStatus" />
		</td>
		<td class="content">
			<html:text property="contentStatus" styleId="contentStatus"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctContentsHistoryForm.contentStatus}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctContentsHistory.auditFlag" />
		</td>
		<td class="content">
			<html:text property="auditFlag" styleId="auditFlag"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctContentsHistoryForm.auditFlag}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctContentsHistory.rolestrFlag" />
		</td>
		<td class="content">
			<html:text property="rolestrFlag" styleId="rolestrFlag"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctContentsHistoryForm.rolestrFlag}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctContentsHistory.levelFlag" />
		</td>
		<td class="content">
			<html:text property="levelFlag" styleId="levelFlag"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctContentsHistoryForm.levelFlag}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctContentsHistory.isBest" />
		</td>
		<td class="content">
			<html:text property="isBest" styleId="isBest"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctContentsHistoryForm.isBest}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctContentsHistory.isPublic" />
		</td>
		<td class="content">
			<html:text property="isPublic" styleId="isPublic"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctContentsHistoryForm.isPublic}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctContentsHistory.gradeOne" />
		</td>
		<td class="content">
			<html:text property="gradeOne" styleId="gradeOne"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctContentsHistoryForm.gradeOne}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctContentsHistory.gradeTwo" />
		</td>
		<td class="content">
			<html:text property="gradeTwo" styleId="gradeTwo"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctContentsHistoryForm.gradeTwo}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctContentsHistory.gradeThree" />
		</td>
		<td class="content">
			<html:text property="gradeThree" styleId="gradeThree"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctContentsHistoryForm.gradeThree}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctContentsHistory.gradeFour" />
		</td>
		<td class="content">
			<html:text property="gradeFour" styleId="gradeFour"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctContentsHistoryForm.gradeFour}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctContentsHistory.gradeFive" />
		</td>
		<td class="content">
			<html:text property="gradeFive" styleId="gradeFive"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctContentsHistoryForm.gradeFive}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctContentsHistory.readCount" />
		</td>
		<td class="content">
			<html:text property="readCount" styleId="readCount"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctContentsHistoryForm.readCount}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctContentsHistory.useCount" />
		</td>
		<td class="content">
			<html:text property="useCount" styleId="useCount"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctContentsHistoryForm.useCount}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctContentsHistory.contentXml" />
		</td>
		<td class="content">
			<html:text property="contentXml" styleId="contentXml"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctContentsHistoryForm.contentXml}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctContentsHistory.modifyCount" />
		</td>
		<td class="content">
			<html:text property="modifyCount" styleId="modifyCount"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctContentsHistoryForm.modifyCount}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctContentsHistory.version" />
		</td>
		<td class="content">
			<html:text property="version" styleId="version"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctContentsHistoryForm.version}" />
		</td>
	</tr>

</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty ctContentsHistoryForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('confirm?')){
						var url='${app}/ctContentsHistory/ctContentsHistorys.do?method=remove&id=${ctContentsHistoryForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${ctContentsHistoryForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>