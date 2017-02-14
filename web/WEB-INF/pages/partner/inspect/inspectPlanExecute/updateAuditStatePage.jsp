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
  
</script>
 
<br/>
	
    
<form action="${app}/partner/inspect/inspectPlanExecute.do?method=updateAuditStatePage" method="post" id="taskOrderForm" name="taskOrderForm" >
	<input type="hidden" name="resId" value="${resId }">
	<table id="taskOrderTable" class="formTable">
		<tr>
			<td class="label">
				审批*
			</td>
			<td colspan="3" class="content">
				<select name="auditStatus" id="auditStatus">
					<option value="1">审批通过</option>
					<option value="2">审批驳回</option>
				</select>
			</td> 
		</tr>
		<tr>
			<td class="label">
				审批意见*
			</td>
			<td colspan="3" class="content">
			  <textarea class="textarea max" name="auditOpinion"
					id="auditOpinion" alt="allowBlank:false"></textarea>
			</td> 
		</tr>
</table>

	<html:submit styleClass="btn" property="method.save"
			styleId="method.save" value="提交"></html:submit>	

</form>

<%@ include file="/common/footer_eoms.jsp"%>