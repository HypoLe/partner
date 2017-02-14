<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'kmExpertFruitForm'});
});
</script>

<html:form action="/kmExpertFruits.do?method=save" styleId="kmExpertFruitForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/km/config/applicationResource-kmmanager">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="kmExpertFruit.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="kmExpertFruit.courseName" />
		</td>
		<td class="content">
			${kmExpertFruitForm.courseName}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="kmExpertFruit.beginTime" />
		</td>
		<td class="content">
			${kmExpertFruitForm.beginTime}
		</td>
		
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="kmExpertFruit.endTime" />
		</td>
 		<td class="content">
 			${kmExpertFruitForm.endTime}
		</td>
		
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="kmExpertFruit.courseContent" />
		</td>
		<td class="content">
			${kmExpertFruitForm.courseContent}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="kmExpertFruit.accessories" />
		</td>
	    
	    <td class="content">
			<eoms:attachment name="kmExpertFruitForm" property="accessories" scope="request" idField="accessories" appCode="kmmanager" viewFlag="Y"/>
		</td>
		
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="kmExpertFruit.participant" />
		</td>
		<td class="content">
			${kmExpertFruitForm.participant}
		</td>
	</tr>
</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="button" class="btn" value="<fmt:message key="button.edit"/>" 
					onclick="javascript:
						var url='${app}/kmmanager/kmExpertFruits.do?method=edit&id=${kmExpertFruitForm.id}';
						location.href=url"	/>
			
			
			<c:if test="${not empty kmExpertFruitForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('确定要删除?')){
						var url='${app}/kmmanager/kmExpertFruits.do?method=remove&id=${kmExpertFruitForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${kmExpertFruitForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>