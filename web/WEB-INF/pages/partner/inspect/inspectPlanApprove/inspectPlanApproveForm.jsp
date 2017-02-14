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
	
    
<form action="${app}/partner/inspect/inspectPlan.do?method=approveInspectPlan" method="post" id="taskOrderForm" name="taskOrderForm" >
	<table id="taskOrderTable" class="formTable">
		<input type="hidden" name="id" value="${planMain.id }">
		<tr>
			<td class="label">
			 巡检计划名称
			</td>
			<td class="content">
				${planMain.planName }
			</td>
			
			<td class="label">
			巡检专业
			</td>
			<td class="content">
				<eoms:id2nameDB id="${planMain.specialty}" beanId="ItawSystemDictTypeDao" />	
			</td>
		</tr>
		<tr>
			<td class="label">
				代维公司
			</td>
			<td class="content">
				<eoms:id2nameDB id="${planMain.partnerDeptId }" beanId="partnerDeptDao"/>
			</td>
			<td class="label">关联资源数</td>
				<td class="content">${planMain.resNum }</td>
			</tr>
		
		<tr>
			<td class="label">
				计划描述
			</td>
			<td colspan="3" class="content">${planMain.content}
			</td> 
		</tr>
		
		<tr>
			<td class="label">
				审批*
			</td>
			<td colspan="3" class="content">
				<select name="approveStatus" id="approveStatus">
					<option value="1">审批通过</option>
					<option value="0">审批驳回</option>
				</select>
			</td> 
		</tr>
		<tr>
			<td class="label">
				审批意见*
			</td>
			<td colspan="3" class="content">
			  <textarea class="textarea max" name="approveIdea"
					id="content" alt="allowBlank:false"></textarea>
			</td> 
		</tr>
</table>

	<html:submit styleClass="btn" property="method.save"
			styleId="method.save" value="提交"></html:submit>	

</form>

<%@ include file="/common/footer_eoms.jsp"%>