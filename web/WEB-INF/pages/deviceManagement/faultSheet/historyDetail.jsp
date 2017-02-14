
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
<c:out value="黑龙江代维故障工单管理详情页面" />
</content>
<fieldset>
	<legend>
		接收故障工单详情
	</legend>
	<form name="faultSheetResponse" method="post" action="FaultSheetManagement.do?method=listHistory">
	<table id="sheet" class="formTable">
		<tr>
			<td class="label">
				主题
			</td>
			<td class="content">
				${faultSheetDetail.themess}
			</td>
			<td class="label">
				工单编号
			</td>
			<td class="content">
				${faultSheetDetail.work_OrderNo}
			</td>
		</tr>
		<tr>
			<td class="label">
				状态
			</td>
			<td class="content">
				${faultSheetDetail.faultState}
			</td>
			<td class="label">
				派单人
			</td>
			<td class="content">
				${faultSheetDetail.operatePerson}
			</td>
		</tr>
		<tr>
			<td class="label">
				派单人部门
			</td>
			<td class="content">
				${faultSheetDetail.operatePerson_Department}
			</td>
			<td class="label">
				派发人联系方式
			</td>
			<td class="content">
				${faultSheetDetail.operatePerson_Contact}
			</td>
		</tr>
		<tr>
			<td class="label">
				派发人当前角色
			</td>
			<td class="content">
				${faultSheetDetail.operatePerson_Rule}
			</td>
			<td class="label">
				派往对象
			</td>
			<td class="content">
			<eoms:id2nameDB id="${faultSheetDetail.detailment_Object}" beanId="tawSystemDeptDao" />
				
			</td>
		</tr>
		<tr>
			<td class="label">
				附件
			</td>
			<td class="content">
				<eoms:download ids="${faultSheetDetail.attachment}"></eoms:download>
			</td>
			<td class="label">
				操作时间
			</td>
			<td class="content">
				${faultSheetDetail.operateTime}
			</td>
		</tr>
		<tr>
			<td class="label">
				处理时限
			</td>
			<td class="content">
				${faultSheetDetail.processLimited}
			</td>
			<td class="label">
				归档时限
			</td>
			<td class="content">
				${faultSheetDetail.file_TimeLimit}
			</td>
		</tr>
		<tr>
			<td class="label">
				故障开始时间
			</td>
			<td class="content">
				${faultSheetDetail.faultStartTime}
			</td>
			<td class="label">
				故障结束时间
			</td>
			<td class="content">
				${faultSheetDetail.faultEndTime}
			</td>
		</tr>
		<tr>
			<td class="label">
				基站名称
			</td>
			<td class="content">
				${faultSheetDetail.base_Station_Name}
			</td>
			<td class="label">
				站号
			</td>
			<td>
			${faultSheetDetail.stationNo}
			</td>
		</tr>
		<tr>
			<td class="label">
				基站属地
			</td>
			<td class="content">
				${faultSheetDetail.base_Station_Location}
			</td>
			<td class="label">
				所属BSC名
			</td>
			<td class="content">
				${faultSheetDetail.bscNo}
			</td>
		</tr>
		<tr>
			<td class="label">
				BCF号
			</td>
			<td class="content">
				${faultSheetDetail.bcfNo}
			</td>
			<td class="label">
				任务来源
			</td>
			<td class="content">
				${faultSheetDetail.task_Sources}
			</td>
		</tr>
		<tr>
			<td class="label">
				任务专业
			</td>
			<td class="content">
				${faultSheetDetail.task_Profession}
			</td>
			<td class="label">
				任务子类
			</td>
			<td>
			${faultSheetDetail.task_Subclass}
			</td>
		</tr>
		<tr>
			<td class="label">
				重要程度
			</td>
			<td class="content">
				${faultSheetDetail.importance}
			</td>
			<td class="label">
				工单类型
			</td>
			<td class="content" colspan="3">
				${faultSheetDetail.work_Order_Type}
			</td>
		</tr>
		<tr>
			<td class="label">
				技术难度
			</td>
			<td class="content">
				${faultSheetDetail.techniqueDifficulty}
			</td>
			<td class="label">
				协调难度
			</td>
			<td class="content">
				${faultSheetDetail.coordinateDifficulty}
			</td>
		</tr>
		<tr>
			<td class="label">
				故障类型
			</td>
			<td class="content" colspan="3">
				${faultSheetDetail.faultType}
			</td>
		</tr>
		<tr>
			<td class="label">
				故障描述
			</td>
			<td class="content" colspan="3">
				${faultSheetDetail.faultDescription}
			</td>
		</tr>
		<tr>
			<td class="label">
				故障初步处理结果
			</td>
			<td class="content" colspan="3">
				${faultSheetDetail.recovery_Processing_Result}
			</td>
		</tr>
		<tr>
			<td class="label">
				故障处理建议
			</td>
			<td colspan="3">
				${faultSheetDetail.fault_Handling_Suggestions}
			</td>
		</tr>
	</table>
	  <input type="submit" value="确定">
</form>
</fieldset>



<%@ include file="/common/footer_eoms.jsp"%>