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
	
    
<form action="${app}/partner/taskManager/carApprove.do?method=saveEndCarApply" method="post" id="taskOrderForm" name="taskOrderForm" >
	<input type="hidden" name="carTaskId" value="${carTask.id }">
	<input type="hidden" name="carApproveId" value="${carApprove.id }">
	<table id="taskOrderTable" class="formTable">
		<caption>
			<div class="header center">车辆终止</div>
		</caption>
		<tr>
			<td class="label">
			 申请人
			</td>
			<td class="content">
				<eoms:id2nameDB id="${carApprove.applyUser}" beanId="tawSystemUserDao" />
			</td>
			
			<td class="label">
			申请人所在部门
			</td>
			<td class="content">
				<eoms:id2nameDB id="${carApprove.applyUserDept}" beanId="tawSystemDeptDao" />
			</td>
		</tr>
		<tr>
			<td class="label">
			申请时间
			</td>
			<td class="content">
				<fmt:formatDate value="${carApprove.applyTime}" type="both" dateStyle="medium"/>
			</td>
			<td class="label">
			车牌号
			</td>
			<td>
				${carApprove.carNum}
			</td>
			</tr>
		
		<tr>
			<td class="label">
			任务类型	
			</td>
			<td  class="content">
			<eoms:id2nameDB id="${carTask.taskType}" beanId="ItawSystemDictTypeDao" />
			</td>
			<td class="label">
			任务名称
			</td>
			<td  class="content">
			${carTask.taskName}
			</td> 
		</tr>
		<tr>
			<td class="label">
				终止备注
			</td>
			<td colspan="3" class="content">
			  <textarea class="textarea max" name="remark" alt="allowBlank:false"
					id="content"></textarea>
			</td> 
		</tr>
</table>

	<html:submit styleClass="btn" property="method.save"
			styleId="method.save" value="提交"></html:submit>	

</form>  
<%@ include file="/common/footer_eoms.jsp"%>
