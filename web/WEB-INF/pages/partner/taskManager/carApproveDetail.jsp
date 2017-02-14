<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script language="JavaScript" type="text/JavaScript"
	src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/scripts/My97DatePicker4.7.2/WdatePicker.js"></script>
<script type="text/javascript">
	var myjs=jQuery.noConflict();

</script>

基本信息
<br/>	
<table id="taskOrderTable" class="listTable">
	<tr>
		<td class="label">
			车牌号
		</td>
		<td class="content">
			${carApprove.carNum }
		</td>
		<td class="label">
			申请时间
		</td>
		<td class="content">
			<fmt:formatDate value="${carApprove.applyTime }" type="both" dateStyle="medium"/>
		</td>
		
	</tr>
	<tr>
		<td class="label">
			申请人所在部门
		</td>
		<td class="content">
			<eoms:id2nameDB id="${carApprove.applyUserDept}" beanId="tawSystemDeptDao" />
		</td>
		<td class="label">
			任务名称
		</td>
		<td class="content">
			${carTask.taskName}
		</td>
	</tr>
	<tr>
		<td class="label">
			任务类型
		</td>
		<td class="content">
			<eoms:id2nameDB id="${carTask.taskType}" beanId="ItawSystemDictTypeDao" />	
		</td>
		<td class="label">
			申请描述
		</td>
		<td class="content">
			<pre>${carApprove.remark }</pre>
		</td>
	</tr>			
</table>
<br/>

详细信息
<br/>

<c:forEach var="list" items="${list}">
<div class="history-item">&nbsp;   
<div class="history-item-title">
	 <fmt:formatDate value="${list.operateTime}" type="both" dateStyle="medium"/>  &nbsp&nbsp<eoms:id2nameDB id="${list.operateUser}" beanId="tawSystemUserDao" />&nbsp&nbsp${carApproveMap[list.operateType]}
	<table id="" class="listTable">
	<tr>
		<td class="label">
			操作人
		</td>
		<td class="content">
			<eoms:id2nameDB id="${list.operateUser}" beanId="tawSystemUserDao" />
		</td>
		<td class="label">
			操作时间
		</td>
		<td class="content">
			<fmt:formatDate value="${list.operateTime}" type="both" dateStyle="medium"/>
		</td>
	</tr>
	<tr>
		<td class="label">操作描述</td>
		<td class="content" colspan="3">
			<pre>${list.remark }</pre>
		</td>
	</tr>
</table>
</div>
</div>
</c:forEach>

<%@ include file="/common/footer_eoms.jsp"%>
