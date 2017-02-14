<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript">
var tree;
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'ctContentsForm'});
});

function onTableChg(table){
    var selValue = table.options[table.options.selectedIndex].value;
	var url = '${app}/partner/contract/ctContentss.do?method=add&TABLE_ID='+ selValue;
	location.href = url;
}
</script>

<html:form action="/ctContentss.do?method=save" styleId="ctContentsForm" method="post"> 

<fmt:bundle	basename="com/boco/eoms/partner/contract/config/applicationResource-partner-contract">

<!-- 定义合同内容变量 -->
<c:set var="CtContentsMap" scope="page" value="${CtContents}"/>	

<!-- 合同ID -->
<input type="hidden" name="TableInfo/ID" value="${CtContentsMap.ID}" />
<!-- 修改人 -->
<input type="hidden" name="TableInfo/MODIFY_USER" value="<bean:write name="SessionUserId"/>" />
<!-- 修改人部门 -->
<input type="hidden" name="TableInfo/MODIFY_DEPT" value="<bean:write name="SessionDeptId"/>" />
<!-- 合同被修改的次数 -->
<input type="hidden" name="TableInfo/MODIFY_COUNT[@field='ADD']" value="1" />
<!-- 合同状态：1-草稿，2-有效，3-失效，4-删除 -->
<input type="hidden" name="TableInfo/CONTRACT_STATUS" value="${CtContentsMap.CONTRACT_STATUS}" />	

<input type="hidden" name="TableInfo/CREATE_USER" value="${CtContentsMap.CREATE_USER}" />
<input type="hidden" name="TableInfo/CREATE_DEPT" value="${CtContentsMap.CREATE_DEPT}" />
<input type="hidden" name="TableInfo/CONTRACT_TITLE" id="CONTRACT_TITLE" value="${CtContentsMap.CONTRACT_TITLE}" />		    		 
<input type="hidden" name="TableInfo/CONTRACT_NO" id="CONTRACT_NO" value="${CtContentsMap.CONTRACT_NO}" />
<input type="hidden" name="TableInfo/SERVICR_LIMIT" id="SERVICR_LIMIT" value="${CtContentsMap.SERVICR_LIMIT}" />		 
<input type="hidden" name="TableInfo/AMOUNT" id="AMOUNT" value="${CtContentsMap.AMOUNT}" />		 
<input type="hidden" name="TableInfo/PARTY_A" id="PARTY_A" value="${CtContentsMap.PARTY_A}" />		 
<input type="hidden" name="TableInfo/PARTY_A_ID" id="PARTY_A_ID" value="${CtContentsMap.PARTY_A_ID}" />		 
<input type="hidden" name="TableInfo/PARTY_B" id="PARTY_B" value="${CtContentsMap.PARTY_B}" />		 
<input type="hidden" name="TableInfo/PARTY_B_ID" id="PARTY_B_ID" value="${CtContentsMap.PARTY_B_ID}" />		 
<input type="hidden" name="TableInfo/PARTY_A_CONTACT" id="PARTY_A_CONTACT" value="${CtContentsMap.PARTY_A_CONTACT}" />		 
<input type="hidden" name="TableInfo/PARTY_B_CONTACT" id="PARTY_B_CONTACT" value="${CtContentsMap.PARTY_B_CONTACT}" />		 
<input type="hidden" name="TableInfo/PARTY_A_INTERFACE" id="PARTY_A_INTERFACE" value="${CtContentsMap.PARTY_A_INTERFACE}" />		 
<input type="hidden" name="TableInfo/PARTY_B_INTERFACE" id="PARTY_B_INTERFACE" value="${CtContentsMap.PARTY_B_INTERFACE}" /> 
<input type="hidden" name="TableInfo/DUTY_DESCRIBE" id="DUTY_DESCRIBE" value="${CtContentsMap.DUTY_DESCRIBE}" />				 
<input type="hidden" name="TableInfo/QUALITY_CHECK" id="QUALITY_CHECK" value="${CtContentsMap.QUALITY_CHECK}" />				 
<input type="hidden" name="TableInfo/QUALITY_CHECK_WAY" id="QUALITY_CHECK_WAY" value="${CtContentsMap.QUALITY_CHECK_WAY}" />				 
<input type="hidden" name="TableInfo/DERAIGN_WAY" id="DERAIGN_WAY" value="${CtContentsMap.DERAIGN_WAY}" />				 
<input type="hidden" name="TableInfo/CHANGING_MANAGE" id="CHANGING_MANAGE" value="${CtContentsMap.CHANGING_MANAGE}" />				 
<input type="hidden" name="TableInfo/OTHER_RULE" id="OTHER_RULE" value="${CtContentsMap.OTHER_RULE}" />				 
<input type="hidden" name="TableInfo/REMARK" id="REMARK" value="${CtContentsMap.REMARK}" />		
*号为必填内容

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="ctContents.form.heading"/>&nbsp;修改</div>
	</caption>	
		<tr>
		<td class="label">
			<fmt:message key="ctContents.tableId" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<eoms:id2nameDB id="${CtContentsMap.TABLE_ID}" beanId="ctTableGeneralDao" /><!-- 合同所属合同库 -->
			<input type="hidden" name="TableInfo/TABLE_ID" value="${CtContentsMap.TABLE_ID}" />
		</td>

		<td class="label">
			<fmt:message key="ctContents.themeId" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">								
		  	<eoms:id2nameDB id="${CtContentsMap.THEME_ID}" beanId="ctTableThemeDao" /><!-- 合同所属分类 -->
		  	<input type="hidden" name="TableInfo/THEME_ID" value="${CtContentsMap.THEME_ID}" />		
		</td>
	</tr>
		<tr>
		<td class="label">
			<fmt:message key="ctContents.contentTitle" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		    ${CtContentsMap.CONTRACT_TITLE}
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctContents.contentKeys" />&nbsp;<font color='red'>*</font>			
		</td>
		<td class="content" colspan="3">
			${CtContentsMap.CONTRACT_NO}
		</td>
	</tr>
	<tr>
		<td class="label">
			附件&nbsp;<font color='red'></font>
		</td>
		<td class="content" colspan="3">
			<eoms:attachment name="CtContents" property="ACCESSORIES"  scope="request"  idField="TableInfo/ACCESSORIES"  appCode="contract" startsWith="0" viewFlag="N"/> 
		</td>
	</tr></table>

</fmt:bundle>

<br>

<table>
	<tr>
		<td>
		<c:if test="${CtContentsMap.CONTRACT_STATUS == 1}">
			<input type="submit" class="btn" value="重新提交" />
		</c:if>	
		<c:if test="${CtContentsMap.CONTRACT_STATUS != 1}">
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
		</c:if>		
		</td>
	</tr>
</table>

</html:form>

<%@ include file="/common/footer_eoms.jsp"%>