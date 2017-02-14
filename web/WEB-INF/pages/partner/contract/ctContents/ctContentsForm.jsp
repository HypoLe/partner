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
		treeDataUrl:'${app}/partner/contract/ctTableThemes.do?method=getNodesRadioTree',
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
	var url = '${app}/partner/contract/ctContentss.do?method=add&id='+ selValue;
	location.href = url;
}
</script>

<html:form action="/ctContentss.do?method=save" styleId="ctContentsForm" method="post"> 

<fmt:bundle	basename="com/boco/eoms/partner/contract/config/applicationResource-partner-contract">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="ctContents.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="ctContents.tableId" />
		</td>
		<td class="content">
			<html:select property="TABLE_ID" styleId="TABLE_ID"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'请选择合同库...'" value="${ctContentsForm.id}" onchange="onTableChg(this)">
			    <html:optionsCollection name="CtTableGeneralList" value="id" label="tableChname"></html:optionsCollection>
			</html:select>
		</td>

		<td class="label">
			<fmt:message key="ctContents.themeId" />
		</td>
		<td class="content">								
			<input type="text"   id="themeName" name="themeName" class="text" readonly="readonly" value="" alt="allowBlank:false,vtext:'请选择合同分类(字典)...'"/>
			<input type="hidden" id="THEME_ID"  name="THEME_ID" value="" />		
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="ctContents.createUser" />
		</td>
		<td class="content">
		    <bean:write name="SessionUserName"/>
		    <input type="hidden" id="CREATE_USER"  name="CREATE_USER" value="<bean:write name="SessionUserId"/>" />	
		</td>

		<td class="label">
			<fmt:message key="ctContents.createDept" />
		</td>
		<td class="content">
		    <bean:write name="SessionDeptName"/>
		    <input type="hidden" id="CREATE_DEPT"  name="CREATE_DEPT" value="<bean:write name="SessionDeptId"/>" />	
		</td>
	</tr>


	<tr>
		<td class="label">
			<fmt:message key="ctContents.contentTitle" />
			
		</td>
		<td class="content" colspan="3">
			<html:text property="CONTENT_TITLE" styleId="CONTENT_TITLE"
						styleClass="text max"
						alt="allowBlank:false,vtext:'请输入合同标题...'" value="${ctContentsForm.contentTitle}" />
		</td>
	</tr>
						
	<c:forEach items="${CtTableColumnList}" var="item">
	<tr>
		<td class="label">
			${item.colChname}
		</td>	
		<td class="content" colspan="3">
		<c:choose>
			<c:when test="${item.colType == 2}">
			<!-- 大文本域 -->
			<textarea name="props(${item.colName})" cols="50" id="${item.colName}" class="textarea max" alt="allowBlank:false,vtext:'请输入${item.colChname}...'" ></textarea>										
			</c:when>

			<c:when test="${item.colType == 3}">
			<!-- 数字类型 -->
			<input type="text" name="props(${item.colName})" id="${item.colName}" value="" class="text medium" alt="allowBlank:false,vtext:'请输入${item.colChname}(正整数类型)...',vtype:'number'" />
			</c:when>

			<c:when test="${item.colType == 4}">
			<!-- 日期时间 -->
			<input type="text" name="props(${item.colName})" size="20" onclick="popUpCalendar(this, this,null,null,null,true,-1);" readonly="true" class="text" alt="allowBlank:false,vtext:'请选择${item.colChname}...'" />
			</c:when>

			<c:when test="${item.colType == 5}">
			<!-- 单选字段 -->
			<eoms:comboBox name="props(${item.colName})" id="${item.colName}" initDicId="${item.colDictId}" defaultValue="" alt="allowBlank:false,vtext:'请选择${item.colChname}(单选字典)...'"/>
			</c:when>

			<c:when test="${item.colType == 6}">
			<!-- 多选字典 -->
			</c:when>

			<c:when test="${item.colType == 7}">
			<!-- 附件上传 -->
			    <c:choose>
			        <c:when test="${not empty ctContentsForm.id}">
			            <eoms:attachment name="" property="" scope="request" idField="${item.colName}" appCode="contract" />
			        </c:when>
			        <c:otherwise>
			            <eoms:attachment idList="" idField="${item.colName}" appCode="contract"/>
			        </c:otherwise>
			    </c:choose>		
			</c:when>

		    <c:otherwise>
			<input type="text" name="${item.colName}" value="" id="${item.colName}" class="text medium" alt="allowBlank:false,vtext:'${item.colChname}'" />
			</c:otherwise>
		</c:choose>
		</td>
	</tr>
	</c:forEach>

	<tr>
		<td class="label">
			<fmt:message key="ctContents.contentKeys" />			
		</td>
		<td class="content" colspan="3">
			<html:text property="CONTENT_KEYS" styleId="CONTENT_KEYS"
						styleClass="text max"
						alt="allowBlank:false,vtext:'请输入合同关键字...'" value="${ctContentsForm.contentKeys}" />
		</td>
	</tr>
</table>

</fmt:bundle>

<br>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty ctContentsForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('confirm?')){
						var url='${app}/ctContents/ctContentss.do?method=remove&id=${ctContentsForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${ctContentsForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>