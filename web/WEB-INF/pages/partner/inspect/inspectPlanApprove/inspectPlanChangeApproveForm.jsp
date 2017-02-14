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
	
    
<form action="${app}/partner/inspect/inspectPlanChange.do?method=approveInspectPlanChange" method="post" id="taskOrderForm" name="taskOrderForm" >
	<table id="taskOrderTable" class="formTable">
		<input type="hidden" name="planChgId" value="${planChgId }">
		<input type="hidden" name="planId" value="${planId}">
		<tr>
			<td class="label">
			 变更标题
			</td>
			<td class="content">
				${planChange.changeTitle }
			</td>
			
			<td class="label">
			表更申请日期
			</td>
			<td class="content">
				<bean:write name="planChange" property="changeTime" format="yyyy-MM-dd HH:mm:ss"/>
			</td>
		</tr>
		<tr>
			<td class="label">
			 变更申请人
			</td>
			<td class="content">
				<eoms:id2nameDB id="${planChange.creator}" beanId="tawSystemUserDao" />
			</td>
		</tr>
		<tr>
			<td class="label">
			 变更说明
			</td>
			<td class="content" colspan="3">
				${planChange.changeOption }
			</td>
		</tr>
		
		<tr>
			<td class="label">
				审批
			</td>
			<td colspan="3" class="content">
				<select name="approveStatus">
					<option value="1">审批通过</option>
					<option value="0">审批驳回</option>
				</select>
			</td> 
		</tr>
		<tr>
			<td class="label">
				审批意见
			</td>
			<td colspan="3" class="content">
			  <textarea class="textarea max" name="approveIdea"
					id="content"></textarea>
			</td> 
		</tr>
</table>

	<html:submit styleClass="btn" property="method.save"
			styleId="method.save" value="提交" ></html:submit>	

</form>

<%@ include file="/common/footer_eoms.jsp"%>