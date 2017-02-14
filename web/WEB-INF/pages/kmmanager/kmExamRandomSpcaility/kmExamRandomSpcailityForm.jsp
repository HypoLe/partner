<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'kmExamRandomSpcailityForm'});
});
</script>

<html:form action="/kmExamRandomSpcailitys.do?method=save" styleId="kmExamRandomSpcailityForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/km/config/applicationResource-kmmanager">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="kmExamRandomSpcaility.form.heading"/></div>
	</caption>

	<tr>
		<td  class="label">
			<fmt:message key="kmExamRandomSpcaility.name" />
		</td>
		<td class="content">
			<html:text property="name" styleId="name"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${kmExamRandomSpcailityForm.name}" />
		</td>
	</tr>

	<tr>
		<td  class="label">
			<fmt:message key="kmExamRandomSpcaility.createUser" />
		</td>
		<td class="content">
			${sessionform.username }
		</td>
	</tr>

	<tr>
		<td  class="label">
			<fmt:message key="kmExamRandomSpcaility.createDept" />
		</td>
		<td class="content">
			${sessionform.deptname }
		</td>
	</tr>

</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty kmExamRandomSpcailityForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('确定要删除?')){
						var url='${app}/kmmanager/kmExamRandomSpcailitys.do?method=remove&nodeId=${kmExamRandomSpcailityForm.nodeId}';
						location.href=url}"
						/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${kmExamRandomSpcailityForm.id}" />
<html:hidden property="nodeId" value="${kmExamRandomSpcailityForm.nodeId}" />
<html:hidden property="parentNodeId" value="${kmExamRandomSpcailityForm.parentNodeId}" />
<html:hidden property="leaf" value="${kmExamRandomSpcailityForm.leaf}" />
<html:hidden property="isDeleted" value="0" />
<html:hidden property="createUser" value="${sessionform.userid }" />
<html:hidden property="createDept" value="${sessionform.deptid}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>