<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<fmt:bundle	basename="com/boco/eoms/partner/contract/config/applicationResource-partner-contract">

<!-- 知详细信息 -->
<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="ctContents.detail.title"/></div>
	</caption>
	
	<!-- 定义合同内容变量 -->
	<c:set var="CtContentsMap" scope="page" value="${CtContents}"/>


	<tr>
		<td class="label">
			<fmt:message key="ctContents.tableId" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${CtContentsMap.TABLE_ID}" beanId="ctTableGeneralDao" />
		</td>

		<td class="label">
			<fmt:message key="ctContents.themeId" />
		</td>
		<td class="content">
		  	<eoms:id2nameDB id="${CtContentsMap.THEME_ID}" beanId="ctTableThemeDao" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctContents.createUser" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${CtContentsMap.CREATE_USER}" beanId="tawSystemUserDao" />
		</td>

		<td class="label">
			<fmt:message key="ctContents.createDept" />
		</td>
		<td class="content">
			<eoms:id2nameDB id="${CtContentsMap.CREATE_DEPT}" beanId="tawSystemDeptDao" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctContents.contentTitle" />			
		</td>
		<td class="content" colspan="3">
			${CtContentsMap.CONTRACT_TITLE}
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="ctContents.contentKeys" />
		</td>
		<td class="content" colspan="3">
		    ${CtContentsMap.CONTRACT_NO}
		</td>
	</tr>
	<tr>	
		<td class="label">
			服务期限&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		    ${CtContentsMap.SERVICR_LIMIT}
		</td>
		<td class="label">
			合同总额&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		    ${CtContentsMap.AMOUNT}
		</td>
	</tr>
	<tr>
		<td class="label">
			甲方&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		    ${CtContentsMap.PARTY_A}
		</td>
		<td class="label">
			乙方&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		   ${CtContentsMap.PARTY_B}
		</td>
	</tr>
	<tr>
		<td class="label">
			甲方联系方式&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		    ${CtContentsMap.PARTY_A_CONTACT}
		</td>
		<td class="label">
			乙方联系方式&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		    ${CtContentsMap.PARTY_B_CONTACT}
		</td>
	</tr>
	<tr>
		<td class="label">
			甲方接口规范&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		    ${CtContentsMap.PARTY_A_INTERFACE}
		</td>
	</tr>
	<tr>
		<td class="label">
			乙方接口规范&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		    ${CtContentsMap.PARTY_B_INTERFACE}
		</td>
	</tr>
	<tr>
		<td class="label">
			责权描述&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		
		    ${CtContentsMap.DUTY_DESCRIBE}
		</td>
	</tr>
	<tr>
		<td class="label">
			质量考核内容&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		
		    ${CtContentsMap.QUALITY_CHECK}	 
		</td>
	</tr>
	<tr>
		<td class="label">
			质量考核方式&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		
		    ${CtContentsMap.QUALITY_CHECK_WAY}		 
		</td>
	</tr>	
	<tr>
		<td class="label">
			争议解决方案&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		
		    ${CtContentsMap.DERAIGN_WAY}	 
		</td>
	</tr>
	<tr>
		<td class="label">
			业务变更管理&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		
		    ${CtContentsMap.CHANGING_MANAGE}		 
		</td>
	</tr>
	<tr>
		<td class="label">
			附则&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		
		    ${CtContentsMap.OTHER_RULE}		 
		</td>
	</tr>	
	<tr>
		<td class="label">
			附件&nbsp;<font color='red'></font>
		</td>
		<td class="content" colspan="3">
		<eoms:attachment name="CtContents" property="ACCESSORIES" scope="request" idField="${CtContentsMap.ACCESSORIES}" appCode="contract" viewFlag="Y"/> 
		</td>
	</tr>	
	<tr>
		<td class="label">
			备注&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		
		    ${CtContentsMap.REMARK}		 
		</td>
	</tr>
	<tr>
		<td class="label" colspan="4">
			附加信息
		</td>
	</tr>
		<%@ include file="ctContentColDetail.jsp"%>
</table>
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>