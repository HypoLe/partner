<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
var tree;
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'kmContentsForm'});
});

function onTableChg(table){
    var selValue = table.options[table.options.selectedIndex].value;
	var url = '${app}/kmmanager/kmContentss.do?method=add&TABLE_ID='+ selValue;
	location.href = url;
}

function addHiddenValue(){
	var draftButton=document.getElementById("draft");
	draftButton.value="yes";
	return true;
}
</script>

<html:form action="/kmContentss.do?method=save" styleId="kmContentsForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/km/config/applicationResource-kmmanager">

<!-- 定义知识内容变量 -->
<c:set var="KmContentsMap" scope="page" value="${KmContents}"/>	

<!-- 知识ID -->
<input type="hidden" name="TableInfo/ID" value="${KmContentsMap.ID}" />
<!-- 修改人 -->
<input type="hidden" name="TableInfo/MODIFY_USER" value="<bean:write name="SessionUserId"/>" />
<!-- 修改人部门 -->
<input type="hidden" name="TableInfo/MODIFY_DEPT" value="<bean:write name="SessionDeptId"/>" />
<!-- 知识被修改的次数 -->
<input type="hidden" name="TableInfo/MODIFY_COUNT[@field='ADD']" value="1" />
<!-- 知识状态：1-草稿，2-有效，3-失效，4-删除 -->
<input type="hidden" name="TableInfo/CONTENT_STATUS" value="${KmContentsMap.CONTENT_STATUS}" />	

*号为必填内容

<table class="formTable">
	<caption>
		<div class="header center">知识内容草稿&nbsp;查看</div>
	</caption>
	<tr>
		<td class="label">
			<fmt:message key="kmContents.tableId" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<eoms:id2nameDB id="${KmContentsMap.TABLE_ID}" beanId="kmTableGeneralDao" /><!-- 知识所属知识库 -->
			<input type="hidden" name="TableInfo/TABLE_ID" value="${KmContentsMap.TABLE_ID}" />
		</td>

		<td class="label">
			<fmt:message key="kmContents.themeId" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">								
		  	<eoms:id2nameDB id="${KmContentsMap.THEME_ID}" beanId="kmTableThemeDao" /><!-- 知识所属分类 -->
		  	<input type="hidden" name="TableInfo/THEME_ID" value="${KmContentsMap.THEME_ID}" />		
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="kmContents.rolestrFlag" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<eoms:dict key="dict-kmmanager" dictId="rolestrFlag" isQuery="false" 
			           defaultId="${KmContentsMap.ROLESTR_FLAG}" selectId="TableInfo/ROLESTR_FLAG" beanId="selectXML" 
			           alt="allowBlank:false,vtext:'请选择知识等级(字典)...'"/>	
		</td>

		<td class="label">
			<fmt:message key="kmContents.levelFlag" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<eoms:dict key="dict-kmmanager" dictId="levelFlag" isQuery="false" 
			           defaultId="${KmContentsMap.LEVEL_FLAG}" selectId="TableInfo/LEVEL_FLAG" beanId="selectXML" 
			           alt="allowBlank:false,vtext:'请选择知识难易度(字典)...'"/>
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="kmContents.createUser" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<eoms:id2nameDB id="${KmContentsMap.CREATE_USER}" beanId="tawSystemUserDao" />
			<input type="hidden" name="TableInfo/CREATE_USER" value="${KmContentsMap.CREATE_USER}" />
		</td>

		<td class="label">
			<fmt:message key="kmContents.createDept" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<eoms:id2nameDB id="${KmContentsMap.CREATE_DEPT}" beanId="tawSystemDeptDao" />
			<input type="hidden" name="TableInfo/CREATE_DEPT" value="${KmContentsMap.CREATE_DEPT}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="kmContents.contentTitle" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		    <input type="text" name="TableInfo/CONTENT_TITLE" id="CONTENT_TITLE" value="${KmContentsMap.CONTENT_TITLE}" maxLength="100" class="text max" alt="allowBlank:false,vtext:'请输入知识标题...'" />		    		 
		</td>
	</tr>

<%@ include file="kmContentColAdd.jsp"%>

	<tr>
		<td class="label">
			<fmt:message key="kmContents.contentKeys" />&nbsp;<font color='red'>*</font>			
		</td>
		<td class="content" colspan="3">
		    <input type="text" name="TableInfo/CONTENT_KEYS" id="CONTENT_KEYS" value="${KmContentsMap.CONTENT_KEYS}" maxLength="100" class="text max" alt="allowBlank:false,vtext:'请输入知识关键字...'" />
		</td>
	</tr>
</table>

</fmt:bundle>

<br>

<table>
	<tr>
		<td>
		<input type="submit" class="btn" value="提交到知识库" />&nbsp;&nbsp;
		<input type="submit" class="btn" value="存到草稿箱" onclick="return addHiddenValue()"/>
		<input type="hidden" id="draft" name="draft" value=""/>
		</td>
	</tr>
</table>

</html:form>

<%@ include file="/common/footer_eoms.jsp"%>