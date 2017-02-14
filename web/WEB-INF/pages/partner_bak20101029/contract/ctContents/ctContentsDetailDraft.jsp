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

function addHiddenValue(){
	var draftButton=document.getElementById("draft");
	draftButton.value="yes";
	return true;
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

*号为必填内容

<table class="formTable">
	<caption>
		<div class="header center">合同内容草稿&nbsp;查看</div>
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
			<fmt:message key="ctContents.createUser" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<eoms:id2nameDB id="${CtContentsMap.CREATE_USER}" beanId="tawSystemUserDao" />
			<input type="hidden" name="TableInfo/CREATE_USER" value="${CtContentsMap.CREATE_USER}" />
		</td>

		<td class="label">
			<fmt:message key="ctContents.createDept" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<eoms:id2nameDB id="${CtContentsMap.CREATE_DEPT}" beanId="tawSystemDeptDao" />
			<input type="hidden" name="TableInfo/CREATE_DEPT" value="${CtContentsMap.CREATE_DEPT}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctContents.contentTitle" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		    <input type="text" name="TableInfo/CONTRACT_TITLE" id="CONTRACT_TITLE" value="${CtContentsMap.CONTRACT_TITLE}" maxLength="100" class="text max" alt="allowBlank:false,vtext:'请输入合同标题...'" />		    		 
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctContents.contentKeys" />&nbsp;<font color='red'>*</font>			
		</td>
		<td class="content" colspan="3">
		    <input type="text" name="TableInfo/CONTRACT_NO" id="CONTRACT_NO" value="${CtContentsMap.CONTRACT_NO}" maxLength="100" class="text max" alt="allowBlank:false,vtext:'请输入合同关键字...'" />
		</td>
	</tr>
	
	
		<tr>	
		<td class="label">
			服务期限&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		    <input type="text" name="TableInfo/SERVICR_LIMIT" id="SERVICR_LIMIT" value="${CtContentsMap.SERVICR_LIMIT}" maxLength="32" class="text max" alt="allowBlank:false,vtext:'请输入合同标题...'" onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true" />		 
		</td>
		<td class="label">
			合同总额&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		    <input type="text" name="TableInfo/AMOUNT" id="AMOUNT" value="${CtContentsMap.AMOUNT}" maxLength="32" class="text max" alt="allowBlank:false,vtext:'请输入合同标题...'" />		 
		</td>
	</tr>
	<tr>
		<td class="label">
			甲方&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		    <input type="text" name="TableInfo/PARTY_A" id="PARTY_A" value="${CtContentsMap.PARTY_A}" maxLength="100" class="text max" alt="allowBlank:false,vtext:'请输入合同标题...'" />		 
		</td>
		<td class="label">
			乙方&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		    <input type="text" name="TableInfo/PARTY_B" id="PARTY_B" value="${CtContentsMap.PARTY_B}" maxLength="100" class="text max" alt="allowBlank:false,vtext:'请输入合同标题...'" />		 
		</td>
	</tr>
	<tr>
		<td class="label">
			甲方联系方式&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		    <input type="text" name="TableInfo/PARTY_A_CONTACT" id="PARTY_A_CONTACT" value="${CtContentsMap.PARTY_A_CONTACT}" maxLength="100" class="text max" alt="allowBlank:false,vtext:'请输入合同标题...'" />		 
		</td>
		<td class="label">
			乙方联系方式&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		    <input type="text" name="TableInfo/PARTY_B_CONTACT" id="PARTY_B_CONTACT" value="${CtContentsMap.PARTY_B_CONTACT}" maxLength="100" class="text max" alt="allowBlank:false,vtext:'请输入合同标题...'" />		 
		</td>
	</tr>
	<tr>
		<td class="label">
			甲方接口规范&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		    <input type="text" name="TableInfo/PARTY_A_INTERFACE" id="PARTY_A_INTERFACE" value="${CtContentsMap.PARTY_A_INTERFACE}" maxLength="500" class="text max" alt="allowBlank:false,vtext:'请输入合同标题...'" />		 
		</td>
	</tr>
	<tr>
		<td class="label">
			乙方接口规范&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		    <input type="text" name="TableInfo/PARTY_B_INTERFACE" id="PARTY_B_INTERFACE" value="${CtContentsMap.PARTY_B_INTERFACE}" maxLength="500" class="text max" alt="allowBlank:false,vtext:'请输入合同标题...'" />		 
		</td>
	</tr>
	<tr>
		<td class="label">
			责权描述&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		
		    <textarea name="TableInfo/DUTY_DESCRIBE" id="DUTY_DESCRIBE" maxLength="500" class="text max" alt="allowBlank:false,vtext:'请输入合同标题...'" >${CtContentsMap.DUTY_DESCRIBE}</textarea>		 
		</td>
	</tr>
	<tr>
		<td class="label">
			质量考核内容&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		
		    <textarea name="TableInfo/QUALITY_CHECK" id="QUALITY_CHECK" maxLength="500" class="text max" alt="allowBlank:false,vtext:'请输入合同标题...'" >${CtContentsMap.QUALITY_CHECK}</textarea>			 
		</td>
	</tr>
	<tr>
		<td class="label">
			质量考核方式&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		
		    <textarea name="TableInfo/QUALITY_CHECK_WAY" id="QUALITY_CHECK_WAY" maxLength="500" class="text max" alt="allowBlank:false,vtext:'请输入合同标题...'" >${CtContentsMap.QUALITY_CHECK_WAY}</textarea>			 
		</td>
	</tr>	
	<tr>
		<td class="label">
			争议解决方案&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		
		    <textarea name="TableInfo/DERAIGN_WAY" id="DERAIGN_WAY" maxLength="500" class="text max" alt="allowBlank:false,vtext:'请输入合同标题...'" >${CtContentsMap.DERAIGN_WAY}</textarea>			 
		</td>
	</tr>
	<tr>
		<td class="label">
			业务变更管理&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		
		    <textarea name="TableInfo/CHANGING_MANAGE" id="CHANGING_MANAGE" maxLength="500" class="text max" alt="allowBlank:false,vtext:'请输入合同标题...'" >${CtContentsMap.CHANGING_MANAGE}</textarea>			 
		</td>
	</tr>
	<tr>
		<td class="label">
			附则&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		
		    <textarea name="TableInfo/OTHER_RULE" id="OTHER_RULE" maxLength="500" class="text max" alt="allowBlank:false,vtext:'请输入合同标题...'" >${CtContentsMap.OTHER_RULE}</textarea>			 
		</td>
	</tr>	
	<tr>
		<td class="label">
			附件&nbsp;<font color='red'></font>
		</td>
		<td class="content" colspan="3">
		<eoms:attachment name="CtContents" property="ACCESSORIES"  scope="request"  idField="TableInfo/ACCESSORIES"  appCode="contract" startsWith="0" viewFlag="N"/> 
		</td>
	</tr>	
	<tr>
		<td class="label">
			备注&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		
		    <textarea name="TableInfo/REMARK" id="REMARK" maxLength="500" class="text max" alt="allowBlank:false,vtext:'请输入合同标题...'" >${CtContentsMap.REMARK}</textarea>			 
		</td>
	</tr>
	<tr>
		<td class="label" colspan="4">
			附加信息
		</td>
	</tr>
	
<%@ include file="ctContentColEdit.jsp"%>
</table>

</fmt:bundle>

<br>

<table>
	<tr>
		<td>
		<input type="submit" class="btn" value="提交到合同库" />&nbsp;&nbsp;
		<input type="submit" class="btn" value="存到草稿箱" onclick="return addHiddenValue()"/>
		<input type="hidden" id="draft" name="draft" value=""/>
		</td>
	</tr>
</table>

</html:form>

<%@ include file="/common/footer_eoms.jsp"%>