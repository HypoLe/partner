<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<html:form action="/kmContentsOpinions.do?method=save" styleId="kmContentsOpinionForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/km/config/applicationResource-kmmanager">

<!-- 知详细信息 -->
<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="kmContents.detail.title"/></div>
	</caption>
	
	<!-- 定义知识内容变量 -->
	<c:set var="KmContentsMap" scope="page" value="${KmContents}"/>

	<tr>
		<td class="label">
			<fmt:message key="kmContents.tableId" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${KmContentsMap.TABLE_ID}" beanId="kmTableGeneralDao" />
		</td>

		<td class="label">
			<fmt:message key="kmContents.themeId" />
		</td>
		<td class="content">
		  	<eoms:id2nameDB id="${KmContentsMap.THEME_ID}" beanId="kmTableThemeDao" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="kmContents.rolestrFlag" />			
		</td>
		<td class="content">
			<eoms:dict key="dict-kmmanager" dictId="rolestrFlag" itemId="${KmContentsMap.ROLESTR_FLAG}" beanId="id2nameXML" />
		</td>

		<td class="label">
			<fmt:message key="kmContents.levelFlag" />			
		</td>
		<td class="content">
			<eoms:dict key="dict-kmmanager" dictId="levelFlag" itemId="${KmContentsMap.LEVEL_FLAG}" beanId="id2nameXML" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="kmContents.createUser" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${KmContentsMap.CREATE_USER}" beanId="tawSystemUserDao" />
		</td>

		<td class="label">
			<fmt:message key="kmContents.createDept" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${KmContentsMap.CREATE_DEPT}" beanId="tawSystemDeptDao" />
		</td>
	</tr>
	
	<tr>
		<td class="label">
			<fmt:message key="kmContents.contentTitle" />			
		</td>
		<td class="content" colspan="3">
			${KmContentsMap.CONTENT_TITLE}
		</td>
	</tr>
	
	<!-- 判断修改人是否为空 -->
	<c:if test="${not empty KmContentsMap.MODIFY_USER}">
	<tr>
		<td class="label">
			<fmt:message key="kmContents.modifyUser" />			
		</td>
		<td class="content">
			<eoms:id2nameDB id="${KmContentsMap.MODIFY_USER}" beanId="tawSystemUserDao" />
		</td>

		<td class="label">
			<fmt:message key="kmContents.modifyDept" />			
		</td>
		<td class="content">
			<eoms:id2nameDB id="${KmContentsMap.MODIFY_DEPT}" beanId="tawSystemDeptDao" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="kmContents.modifyTime" />			
		</td>
		<td class="content" colspan="3">
			${KmContentsMap.MODIFY_TIME}
		</td>
	</tr>
	</c:if>
	
	
	<tr>
		<td class="label">
			<fmt:message key="kmContents.contentStatus" />			
		</td>
		<td class="content">
			<eoms:dict key="dict-kmmanager" dictId="contentStatus" itemId="${KmContentsMap.CONTENT_STATUS}" beanId="id2nameXML" />
		</td>

		<td class="label">
			<fmt:message key="kmContents.auditFlag" />			
		</td>
		<td class="content">
			<eoms:dict key="dict-kmmanager" dictId="isOrNot" itemId="${KmContentsMap.AUDIT_FLAG}" beanId="id2nameXML" />
		</td>
	</tr>
	
	<tr>
		<td class="label">
			<fmt:message key="kmContents.isBest" />			
		</td>
		<td class="content">
		    <eoms:dict key="dict-kmmanager" dictId="isOrNot" itemId="${KmContentsMap.IS_BEST}" beanId="id2nameXML" />
		</td>

		<td class="label">
			<fmt:message key="kmContents.isPublic" />			
		</td>
		<td class="content">
		    <eoms:dict key="dict-kmmanager" dictId="isOrNot" itemId="${KmContentsMap.IS_PUBLIC}" beanId="id2nameXML" />
		</td>
	</tr>
	
	<tr>
		<td class="label">
			知识评价		
		</td>	
		<td class="content" colspan="3">
			<fmt:message key="kmContents.gradeOne" />   ${KmContentsMap.GRADE_ONE}   次 <b>|</b>
			<fmt:message key="kmContents.gradeTwo" />   ${KmContentsMap.GRADE_TWO}   次 <b>|</b>
			<fmt:message key="kmContents.gradeThree" /> ${KmContentsMap.GRADE_THREE} 次 <b>|</b>
			<fmt:message key="kmContents.gradeFour" />  ${KmContentsMap.GRADE_FOUR}  次 <b>|</b>
			<fmt:message key="kmContents.gradeFive" />  ${KmContentsMap.GRADE_FIVE}  次
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="kmContents.readCount" />			
		</td>
		<td class="content">
			${KmContentsMap.READ_COUNT}
		</td>

		<td class="label">
			<fmt:message key="kmContents.useCount" />			
		</td>
		<td class="content">
			${KmContentsMap.USE_COUNT}
		</td>
	</tr>
	
	<tr>
		<td class="label">
			<fmt:message key="kmContents.modifyCount" />			
		</td>
		<td class="content" colspan="3">
			${KmContentsMap.MODIFY_COUNT}
		</td>
	</tr>

<%@ include file="kmContentColDetail.jsp"%>

	<tr>
		<td class="label">
			<fmt:message key="kmContents.contentKeys" />			
		</td>
		<td class="content" colspan="3">
		    ${KmContentsMap.CONTENT_KEYS}
		</td>
	</tr>
</table>

</fmt:bundle>

</html:form>

<%@ include file="/common/footer_eoms.jsp"%>