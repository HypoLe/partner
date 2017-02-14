<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>


<content tag="heading">
<c:out value="黑龙江代维故障工单回复查看" />
</content>

<fieldset>
	<legend>
		故障回复工单查看
	</legend>
<form action="FaultSheetResponse.do?method=listResponse" method="post">
	<table id="sheet" class="formTable">
		<tr>
			<td class="label">
				工单编号
			</td>
			<td class="content">
				${responsedetailaa.work_OrderNo}
			</td>

			<td class="label">
				派发人
			</td>
			<td class="content">
				${responsedetailaa.operatePerson}

			</td>
		</tr>
		<tr>
			<td class="label">
				派发人部门
			</td>
			<td class="content">
				${responsedetailaa.operatePerson_Department}
			</td>
			<td class="label">
				派发人联系方式
			</td>
			<td class="content">
				${responsedetailaa.operatePerson_Contact}
			</td>
		</tr>
		<tr>
			<td class="label">
				派发人当前角色
			</td>
			<td class="content">
				${responsedetailaa.operatePerson_Rule}
			</td>
			<td class="label">
				派往对象
			</td>
			<td class="content">
				${responsedetailaa.detailment_Object}
			</td>
		</tr>
		<tr>
			
			<td class="label">
				附件下载
			</td>
			<td class="content">
				<eoms:download ids="${responsedetailaa.attachment}"></eoms:download>
			</td>
			<td class="label">
				操作时间
			</td>
			<td class="content">
				${responsedetailaa.operateTime}
			</td>
		</tr>
	</table>
	<input type="submit" styleClass="btn" property="method.save"
		id="submitIt" styleId="method.save" value="确定">
</form>
<%@ include file="/common/footer_eoms.jsp"%>