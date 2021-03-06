<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<bean:define id="nodeId"    name="CtTableTheme" property="nodeId" />
<bean:define id="themeName" name="CtTableTheme" property="themeName" />

<script type="text/javascript">
var tree;
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'ctContentsForm'});

	var config = {
		btnId:'themeName',
		treeDataUrl:'${app}/partner/contract/ctTableThemes.do?method=getNodesRadioTreeForQuery',
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
    var minTime = document.getElementById("min").value;
    var maxTime = document.getElementById("max").value;
	var url = '${app}/partner/contract/ctContentss.do?method=query&TABLE_ID='+ selValue+'&minTime='+minTime+'&maxTime='+maxTime;
	location.href = url;
}
</script>

<html:form action="/ctContentss.do?method=queryDo" styleId="ctContentsForm" method="post"> 

<fmt:bundle	basename="com/boco/eoms/partner/contract/config/applicationResource-partner-contract">

<!-- 合同状态：1-草稿，2-有效，3-失效，4-删除 -->
<input type="hidden" id="CONTENT_STATUS" name="TableInfo/CONTENT_STATUS" value="2" />	

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="ctContents.form.heading"/>&nbsp;查询</div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="ctContents.createTime" />
		</td>
		<td class="content" colspan="3">
			从： <input type="text" size="20" readonly="true" class="text" 
			          name="QueryCond/CREATE_TIME/criteria/min"
			          id="min"  value="${minTime}" 
			          onclick="popUpCalendar(this, this,null,null,null,true,-1);"
			          alt="allowBlank:false,vtext:'请选择开始时间...'" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			到： <input type="text" size="20" readonly="true" class="text" 
			          name="QueryCond/CREATE_TIME/criteria/max" 
			          id="max"  value="${maxTime}"  
			          onclick="popUpCalendar(this, this,null,null,null,true,-1);" 
			          alt="allowBlank:false,vtext:'请选择结束时间...'" />        
			<input type="hidden" name="QueryCond/CREATE_TIME/criteria/operator" value="between">	
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctContents.tableId" />
		</td>
		<td class="content">
			<html:select property="QueryCond/TABLE_ID/criteria/value" styleId="TABLE_ID"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'请选择合同库...'" value="${ctContentsForm.id}" onchange="onTableChg(this)">
			    <html:optionsCollection name="CtTableGeneralList" value="id" label="tableChname"></html:optionsCollection>
			</html:select>
		</td>

		<td class="label">
			<fmt:message key="ctContents.themeId" />
		</td>
		<td class="content">								
			<input type="text"   id="themeName" name="themeName" class="text" readonly="readonly" value="" alt="allowBlank:true"/>
			<input type="hidden" id="THEME_ID"  name="QueryCond/THEME_ID/criteria/value" value="" />		
		</td>
	</tr>

<!--
	<tr>
		<td class="label">
			<fmt:message key="ctContents.createUser" />
		</td>
		<td class="content" colspan="3">
		    <input type="text"   name="QueryCond/CREATE_USER/criteria/value" id="createUser" value="" class="text" alt="allowBlank:true"/>
		    <input type="hidden" name="QueryCond/CREATE_USER/criteria/operator" value="like">
		    <input type="hidden" name="QueryCond/CREATE_USER/criteria/likeRule" value="center">
		</td>

		<td class="label">
			<fmt:message key="ctContents.createDept" />
		</td>
		<td class="content">
		    <input type="text" id="createDept" name="QueryCond/CREATE_DEPT/criteria/value" class="text" value="" alt="allowBlank:true"/>
		</td>

	</tr>
-->
	<tr>
		<td class="label">
			<fmt:message key="ctContents.contentTitle" />
			
		</td>
		<td class="content" colspan="3">
		    <input type="text"   name="QueryCond/CONTENT_TITLE/criteria/value" id="contentTitle" value="" class="text max" alt="allowBlank:true" />
		    <input type="hidden" name="QueryCond/CONTENT_TITLE/criteria/operator" value="like">
		    <input type="hidden" name="QueryCond/CONTENT_TITLE/criteria/likeRule" value="center">		    		 
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctContents.contentKeys" />			
		</td>
		<td class="content" colspan="3">
			<input type="text"   name="QueryCond/CONTENT_KEYS/criteria/value" id="contentKeys" value="" class="text" alt="allowBlank:true"/>
		    <input type="hidden" name="QueryCond/CONTENT_KEYS/criteria/operator" value="like">
		    <input type="hidden" name="QueryCond/CONTENT_KEYS/criteria/likeRule" value="center">							
		</td>
	</tr>
</table>

<br>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.search"/>" />&nbsp;&nbsp;
			<input type="reset"  class="btn" value="<fmt:message key="button.reset"/>" />&nbsp;&nbsp;
		</td>
	</tr>
</table>

</fmt:bundle>
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>