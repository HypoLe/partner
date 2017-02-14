<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script language="JavaScript" type="text/JavaScript"
	src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript">

var myjs=jQuery.noConflict();
Ext.onReady(function(){
    v = new eoms.form.Validation({form:'taskOrderForm'});
    v.custom = function(){ 
    	return true;
    };
});

function deletePlanChange(){
	if (confirm("是否确认删除?")==true){
		var form = document.getElementById('taskOrderForm');
		form.action = '${app}/partner/inspect/inspectPlanChange.do?method=removeInspectPlanChange&id=${plan.id }&planId=${planId }';
		form.submit();
	}
}
</script>
<br/>
<form action="${app}/partner/inspect/inspectPlanChange.do?method=addInspectPlanChange" method="post" id="taskOrderForm" name="taskOrderForm" >
	<table id="taskOrderTable" class="formTable">
		<input type="hidden" name="id" value="${plan.id }">
		<input type="hidden" name="planId" value="${planId }">
		<tr>
			<td class="label">
			 变更标题* 
			</td>
			<td colspan="3" class="content">
				<input class="text" type="text" name="changeTitle"
					id="topic" alt="allowBlank:false" value="${plan.changeTitle }"/>
			</td>
		</tr>
		<tr>
			<td class="label">
				变更说明
			</td>
			<td colspan="3" class="content">
			  <textarea class="textarea max" name="changeOption"
					id="content">${plan.changeOption}</textarea>
			</td> 
		</tr>
</table>

	<html:submit styleClass="btn" property="method.save"
			styleId="method.save" value="保存" onclick="beforeSubmit()"></html:submit>	
	<html:reset styleClass="btn" styleId="method.reset" value="重置"></html:reset>
	<c:if test="${! empty plan.id }" var="result">
		<input type="button" class="btn" value="删除" onclick="deletePlanChange()"/>&nbsp;&nbsp;
	</c:if>
</form>

<%@ include file="/common/footer_eoms.jsp"%>