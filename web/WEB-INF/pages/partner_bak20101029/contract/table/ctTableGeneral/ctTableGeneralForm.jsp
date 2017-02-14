<jsp:directive.page import="com.boco.eoms.partner.contract.table.util.CtTableGeneralConstants"/>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>

<html:form action="/ctTableGenerals.do?method=save" styleId="ctTableGeneralForm" method="post"> 

	<fmt:bundle	basename="com/boco/eoms/partner/contract/config/applicationResource-partner-contract">
<script type="text/javascript">
var tree;
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'ctTableGeneralForm'});

	var config = {
		btnId:'themeName',
		treeDataUrl:'${app}/partner/contract/ctTableThemes.do?method=getNodesRadioTreeFirst',
		treeRootId:'<%=CtTableGeneralConstants.TREE_ROOT_ID%>',
		treeRootText:'<fmt:message key="ctTableGeneral.themeId" />',
		treeChkMode:'single',
		treeChkType:'forums',
		showChkFldId:'themeName',
		saveChkFldId:'themeId'
	}
	tree = new xbox(config);	
});

</script>

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="ctTableGeneral.form.heading"/></div>
	</caption>

	<!--  tr>
		<td class="label">
			<fmt:message key="ctTableGeneral.id" />
		</td>
		<td class="content">
			<html:text property="id" styleId="id"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctTableGeneralForm.id}" />
		</td>
	</tr-->

	<tr>		
			<td class="label">
			<fmt:message key="ctTableGeneral.tableChname" />
		</td>
		<td class="content">
			<html:text property="tableChname" styleId="tableChname"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'请填写表中文名'" value="${ctTableGeneralForm.tableChname}" />
			<input type="hidden" id="tableName"   name="tableName" value="${ctTableGeneralForm.tableName}" />
		</td>
		<td class="label">
			<fmt:message key="ctTableGeneral.themeId" />
		</td>
		<td class="content">
		    <c:if test="${ctTableGeneralForm.isCreated != 1}">
		        <input type="text" id="themeName" name="themeName" class="text" readonly="readonly" value='<eoms:id2nameDB id="${ctTableGeneralForm.themeId}" beanId="ctTableThemeDao" />' alt="allowBlank:false'"/>			
			</c:if>
			<c:if test="${ctTableGeneralForm.isCreated == 1}">
			    <eoms:id2nameDB id="${ctTableGeneralForm.themeId}" beanId="ctTableThemeDao" />
			    <input type="hidden" id="themeName" name="themeName" value="<eoms:id2nameDB id="${ctTableGeneralForm.themeId}" beanId="ctTableThemeDao" />" />
			</c:if>
			    <input type="hidden" id="themeId" name="themeId" value="${ctTableGeneralForm.themeId}" />
		</td>
	</tr>

<!--
	<tr>
		<td class="label">
			<fmt:message key="ctTableGeneral.createUser" />
		</td>
		<td class="content">
			<html:text property="createUser" styleId="createUser"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctTableGeneralForm.createUser}" />
		</td>
			<td class="label">
			<fmt:message key="ctTableGeneral.createDept" />
		</td>
		<td class="content">
			<html:text property="createDept" styleId="createDept"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctTableGeneralForm.createDept}" />
		</td>
	</tr>
-->

<!--
	<tr>
		<td class="label">
			<fmt:message key="ctTableGeneral.createTime" />
		</td>
		<td class="content">
			<html:text property="createTime" styleId="createTime"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${ctTableGeneralForm.createTime}" />
		</td>
	</tr>
-->

	<tr>
		<td class="label">
			<fmt:message key="ctTableGeneral.orderBy" />
		</td>
		<td class="content">
			<html:text property="orderBy" styleId="orderBy"
						styleClass="text medium" alt="vtype:'number',allowBlank:false"
						 value="${ctTableGeneralForm.orderBy}" />
		</td>
			<td class="label">
			<fmt:message key="ctTableGeneral.isOpen" />
		</td>
		<td class="content">
		<eoms:dict key="dict-partner-contract" dictId="isOrNot" isQuery="false" alt="allowBlank:false,vtext:'请选择是否开放(字典)...'"
				defaultId="${ctTableGeneralForm.isOpen}" selectId="isOpen" beanId="selectXML" />
		</td>
	</tr>

<!--
	<tr>
		<td class="label">
			<fmt:message key="ctTableGeneral.isVisibl" />
		</td>
		<td class="content">	
			<eoms:dict key="dict-partner-contract" dictId="isOrNot" isQuery="false"
				defaultId="${ctTableGeneralForm.isVisibl}" selectId="isVisibl" beanId="selectXML" />		
			
		</td>
			<td class="label">
			<fmt:message key="ctTableGeneral.isAudit" />
		</td>
		<td class="content">
		<eoms:dict key="dict-partner-contract" dictId="isOrNot" isQuery="false"
				defaultId="${ctTableGeneralForm.isAudit}" selectId="isAudit" beanId="selectXML" />	
		</td>
	</tr>
-->
    <input type="hidden" name="isVisibl" value="1" />
    <input type="hidden" name="isAudit" value="0" />

<!--
	<tr>
		<td class="label">
			<fmt:message key="ctTableGeneral.isDeleted" />
		</td>
		<td class="content">		
			<eoms:comboBox name="isDeleted" id="isDeleted" initDicId="10301" defaultValue="1030102" form="ctTableGeneralForm"/>
		</td>
		
	</tr>
-->	
    <input type="hidden" name="isDeleted" value="0" />
    

	<tr>
		<td class="label">
			<fmt:message key="ctTableGeneral.remark" />
		</td>
		<td class="content" colspan="3">
		  <textarea name="remark" cols="50" id="remark" class="textarea max" alt="allowBlank:false" onkeyup="this.value=this.value.slice(0, 180)">${ctTableGeneralForm.remark}</textarea>
		</td>
	</tr>

</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="button" class="btn" value="<fmt:message key="button.next"/>" 
			        onclick="javascript:next()"/>			      
			<c:if test="${not empty ctTableGeneralForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('<fmt:message key="message.delMessage"/>')){
						var url='${app}/partner/contract/ctTableGenerals.do?method=remove&id=${ctTableGeneralForm.id}';
						location.href=url}"	/>				
			</c:if>
		</td>
	</tr>
</table>

<html:hidden property="id" value="${ctTableGeneralForm.id}" />
<html:hidden property="isCreated" value="${ctTableGeneralForm.isCreated}" />

</html:form>

<%@ include file="/common/footer_eoms.jsp"%>

<script>
 function next(){  
   document.forms[0].action="${app}/partner/contract/ctTableGenerals.do?method=tree";
   //document.forms[0].submit();
    var themeId = document.forms[0].themeId.value;
    if(v.check()){
      if (themeId.length <= 0) {
         alert("请填写合同分类");
         return false;
       }
    document.forms[0].submit()
    }    
   //return true;
 }
</script>
