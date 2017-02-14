<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<bean:define id="nodeId"    name="KmTableTheme" property="nodeId" />
<bean:define id="themeName" name="KmTableTheme" property="themeName" />

<script type="text/javascript">
var tree;
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'kmContentsForm'});
	var config = {
		btnId:'themeName',
		treeDataUrl:'${app}/kmmanager/kmTableThemes.do?method=getNodesRadioTree',
		treeRootId:'<%=nodeId%>',
		treeRootText:'<%=themeName%>',
		treeChkMode:'single',
		treeChkType:'forums',
		showChkFldId:'themeName',
		saveChkFldId:'THEME_ID'
	}
	tree = new xbox(config);
});

function onTableChg(table){
    var selValue = table.options[table.options.selectedIndex].value;
    var applyId = document.getElementById("applyId").value;
	var url = '${app}/kmmanager/kmContentss.do?method=add&TABLE_ID='+ selValue+'&applyId='+applyId;
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

<!-- 知识状态：1-草稿，2-有效，3-失效，4-删除 -->
<input type="hidden" name="TableInfo/CONTENT_STATUS" value="2" />	

<!-- 知识申请相关 -->
<input type="hidden" id="applyId" name="applyId" value="${applyId}"/>

*号为必填内容

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="kmContents.form.heading"/>&nbsp;新增</div>
	</caption>
	<tr>
		<td class="label">
			<fmt:message key="kmContents.tableId" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:select property="TableInfo/TABLE_ID" styleId="TABLE_ID"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'请选择知识库...'" value="${TABLE_ID}" onchange="onTableChg(this)">
			    <html:optionsCollection name="KmTableGeneralList" value="id" label="tableChname"></html:optionsCollection>
			</html:select>
		</td>

		<td class="label">
			<fmt:message key="kmContents.themeId" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">								
			<input type="text"   id="themeName" name="themeName" class="text" readonly="readonly" value="" alt="allowBlank:false,vtext:'请选择知识分类(字典)...'"/>
			<input type="hidden" id="THEME_ID"  name="TableInfo/THEME_ID" value="" />		
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="kmContents.rolestrFlag" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<eoms:dict key="dict-kmmanager" dictId="rolestrFlag" isQuery="false" 
			           defaultId="" selectId="TableInfo/ROLESTR_FLAG" beanId="selectXML" 
			           alt="allowBlank:false,vtext:'请选择知识等级(字典)...'"/>	
		</td>

		<td class="label">
			<fmt:message key="kmContents.levelFlag" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<eoms:dict key="dict-kmmanager" dictId="levelFlag" isQuery="false" 
			           defaultId="" selectId="TableInfo/LEVEL_FLAG" beanId="selectXML" 
			           alt="allowBlank:false,vtext:'请选择知识难易度(字典)...'"/>
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="kmContents.createUser" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		    <bean:write name="SessionUserName"/><!-- 创建人 -->
		    <input type="hidden" name="TableInfo/CREATE_USER" value="<bean:write name="SessionUserId"/>" />
		</td>

		<td class="label">
			<fmt:message key="kmContents.createDept" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
		    <bean:write name="SessionDeptName"/><!-- 创建人所在部门 -->
		    <input type="hidden" name="TableInfo/CREATE_DEPT" value="<bean:write name="SessionDeptId"/>" />	
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="kmContents.contentTitle" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		    <input type="text" name="TableInfo/CONTENT_TITLE" id="CONTENT_TITLE" value="" maxLength="100" class="text max" alt="allowBlank:false,vtext:'请输入知识标题...'" />		 
		</td>
	</tr>

<%@ include file="kmContentColAdd.jsp"%>

	<tr>
		<td class="label">
			<fmt:message key="kmContents.contentKeys" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content" colspan="3">
		    <input type="text" name="TableInfo/CONTENT_KEYS" id="CONTENT_KEYS" value="" maxLength="100" class="text max" alt="allowBlank:false,vtext:'请输入知识关键字...'" />								
		</td>
	</tr>
</table>

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

</fmt:bundle>
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>