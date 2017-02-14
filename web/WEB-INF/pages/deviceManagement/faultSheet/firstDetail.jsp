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
<form action="FaultSheetManagement.do?method=listForm"
	styleId="FaultSheetManagementForm" method="post"
	id="FaultSheetManagementForm" name="FaultSheetManagementForm"
	styleid="FaultSheetManagementForm">
	<table id="sheet" class="formTable">
		<tr>
			<td colspan="4">
				<div class="ui-widget-header">
					工单基本信息
				</div>
			</td>
		</tr>
		<tr>
			<td class="label">
				主题
			</td>
			<td class="content" colspan="3">
				${faultSheetDetail.themess}
			</td>
			</tr>
			<tr>
 <td class="label">
				工单编号
			</td>
			<td class="content" >
				${faultSheetDetail.work_OrderNo}
			</td>
			 <td class="label">
				状态
			</td>
			<td class="content" >
			${faultSheetDetail.faultState}
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<div class="ui-widget-header">
					操作公共字段
				</div>
			</td>
		</tr>
		<tr>
			<td class="label">
				操作人
				<br>
			</td>
			<td class="content">
				${faultSheetDetail.operatePerson}		
			</td>
						<td class="label">
				操作人部门
				<br>
			</td>
			<td class="content">
				${faultSheetDetail.operatePerson_Department}
			</td>
		</tr>
				<tr>
			<td class="label">
				操作人联系方式
				<br>
			</td>
			<td class="content">
				${faultSheetDetail.operatePerson_Contact}
			</td>
						<td class="label">
				操作人当前角色
				<br>
			</td>
			<td class="content">
				${faultSheetDetail.operatePerson_Rule}
			</td>
		</tr>
		<tr>
			<td class="label">
				派往对象
				<br>
			</td>
			<td class="content">
			<eoms:id2nameDB id="${faultSheetDetail.detailment_Object}" beanId="tawSystemDeptDao" />
			</td>	
			<td class="label">
				附件
			</td>
			<td class="content">
			<eoms:download ids="${faultSheetDetail.attachment}"></eoms:download>
			</td>	
			    
		</tr>
			<tr>
			<td class="label">
				操作时间
				<br>
			</td>
			<td class="content">
				${faultSheetDetail.operateTime}
			</td>	
		</tr>	
		
		<tr>
			<td colspan="4">
				<div class="ui-widget-header">
					处理时限
				</div>
			</td>
		</tr>
		<tr>
			<td class="label">
				处理时限
				<br>
			</td>
			<td class="content">
				${faultSheetDetail.processLimited}"
				<br>
			</td>
			<td class="label">
				归档时限
				<br>
			</td>
			<td class="content"> 
				${faultSheetDetail.file_TimeLimit}
			</td>
		</tr>

		<tr>
			<td class="label">
				故障开始时间
				<br>
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
			<td colspan="4">
				<div class="ui-widget-header">
					基站信息
				</div>
			</td>
		</tr>
		<tr>
			<td class="label">
				基站名称
			</td>
			<td class="content" colspan="3">
				${faultSheetDetail.base_Station_Name}
				<br>
			</td>
		</tr>
		<tr>
			<td class="label">
				站号

			</td>
			<td class="content"> 
				${faultSheetDetail.stationNo}
			</td>
			<td class="label">
				基站属地

			</td>
			<td class="content">
				${faultSheetDetail.base_Station_Location}
			</td>
		</tr>
		<tr>
		<tr>
			<td class="label">
				所属BSC名

			</td>
			<td class="content">
				${faultSheetDetail.bscNo}
			</td>
			<td class="label">
				BCF号

			</td>
			<td class="content">
				${faultSheetDetail.bcfNo}
			</td>
		</tr>

		<tr>

			<td colspan="4">
				<div class="ui-widget-header">
					任务信息
				</div>
			</td>
		</tr>
		<tr>
			<td class="label">
				任务来源
				<br>
			</td>
			<td class="content">
				${faultSheetDetail.task_Sources}
			</td>
			<td class="label">
				任务专业
			</td>
			<td class="content">
				${faultSheetDetail.task_Profession}
			</td>
		</tr>

		<tr>
			<td class="label">
				任务子类
				<br>
			</td>
			<td class="content">
				${faultSheetDetail.task_Subclass}

			</td>
			<td class="label">
				重要程度
				<br>
			</td>
			<td class="content">
				${faultSheetDetail.importance}
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<div class="ui-widget-header">
					工单类型
				</div>
			</td>
		</tr>
		<tr>
			<td class="label">
				工单类型
				<br>
			</td>
			<td class="content" colspan="3">
				${faultSheetDetail.work_Order_Type}
			</td>

		</tr>
		<tr>
			<td colspan="4">
				<div class="ui-widget-header">
					故障难度信息
				</div>
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
			<td colspan="4">
				<div class="ui-widget-header">
					故障信息
				</div>
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
				<textarea class="textarea max" name="faultDescription" disabled= "true" id="faultDescription" 
					>${faultSheetDetail.faultDescription}</textarea>

			</td>
		</tr>
		<tr>

			<td class="label">
				故障初步处理结果
				<br>
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="recovery_Processing_Result" disabled= "true" 
					id="recovery_Processing_Result" >${faultSheetDetail.recovery_Processing_Result}</textarea>
			</td>
		</tr>
		<tr>
			<td class="label">
				故障处理建议
			</td>
			<td colspan="3">
				<textarea class="textarea max" name="fault_Handling_Suggestions" disabled= "true" 
					id="fault_Handling_Suggestions" >${faultSheetDetail.fault_Handling_Suggestions}</textarea>
				<br>
			</td>
		</tr>
  </table>
		<html:submit styleClass="btn" property="method.save"
			styleId="method.save" value="返回"></html:submit>
		
</form>

<%@ include file="/common/footer_eoms.jsp"%>