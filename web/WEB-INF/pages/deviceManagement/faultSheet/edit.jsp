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

<script type="text/javascript">
var myjs=jQuery.noConflict();
	Ext.onReady(function(){
	           // new eoms.form.Validation({form:'FaultSheetManagementForm'});
				myjs('faultStartTime').value = new Date().format('Y-m-d H:i:s'); 
				myjs('faultEndTime').value = new Date().format('Y-m-d H:i:s'); 	
   });
   
 </script>

<content tag="heading">
<c:out value="黑龙江代维故障工单管理编辑页面" />
</content>
<form action="FaultSheetManagement.do?method=edit1&id=${faultSheet.id}"
	method="post" id="FaultSheetManagementForm"
	name="FaultSheetManagementForm">
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
				主题*
			</td>
			<td class="content" colspan="3">
				<input class="text" type="text" name="themess" id="themess"
					value="${faultSheet.themess}" alt="allowBlank:false" />
			</td>
		</tr>
		<tr>
			<td class="label">
				工单编号*
			</td>
			<td class="content">
				<input class="text" type="text" disabled="disabled"
					name="work_OrderNo" id="work_OrderNo"
					value="${faultSheet.work_OrderNo}" alt="allowBlank:false" />
			</td>
			<td class="label">
				状态*
			</td>
			<td class="content">
				<input class="text" type="text" disabled="true" name="faultState"
					id="faultState" value="${faultSheet.faultState}"
					alt="allowBlank:false" />
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
				操作人*
				<br>
			</td>
			<td class="content">
				<input class="text" type="text" disabled="disabled"
					name="operatePerson" value="${faultSheet.operatePerson}"
					id="operatePerson" alt="allowBlank:false"></input>
			</td>
			<td class="label">
				操作人部门*
				<br>
			</td>
			<td class="content">
				<input class="text" type="text" disabled="disabled"
					name="operatePerson_Department"
					value="${faultSheet.operatePerson_Department}"
					id="operatePerson_Department" alt="allowBlank:false"></input>
			</td>
		</tr>
		<tr>
			<td class="label">
				操作人联系方式*
				<br>
			</td>
			<td class="content">
				<input class="text" type="text" disabled="true"
					name="operatePerson_Contact"
					value="${faultSheet.operatePerson_Contact}"
					id="operatePerson_Contact" alt="allowBlank:false"></input>
			</td>
			<td class="label">
				操作人当前角色*
				<br>
			</td>
			<td class="content">
				<input class="text" type="text" name="operatePerson_Rule"
					value="${faultSheet.operatePerson_Rule}" id="operatePerson_Rule"
					alt="allowBlank:false"></input>
			</td>
		</tr>
		<tr>
			<td class="label">
				派往对象*
				<br>
			</td>
			<td class="content">
				<input class="text" type="text" name="detailment_Object"
					value="${faultSheet.detailment_Object}" id="detailment_Object"
					alt="allowBlank:false"></input>
				<br>
			</td>
			<td class="label">
				附件*
				<br>
			</td>
			<td colspan="3">
				<eoms:attachment name="faultSheet" scope="request"
					idField="attachment" property="attachment" appCode="faultSheet"
					alt="allowBlank:true" />
			</td>
		</tr>
		<tr>
			<td class="label">
				操作时间*
				<br>
			</td>
			<td class="content">
				<input class="text" type="text" name="operateTime"
					value="${faultSheet.operateTime}" id="operateTime"
					alt="allowBlank:false"></input>
				<br>
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
				处理时限*
				<br>
			</td>
			<td class="content">
				<input class="text" type="text" name="processLimited"
					value="${faultSheet.processLimited}" />
				<br>
			</td>
			<td class="label">
				归档时限*
				<br>
			</td>
			<td class="content">
				<input class="text" type="text" name="file_TimeLimit"
					value="${faultSheet.file_TimeLimit}" id="file_TimeLimit"
					alt="allowBlank:false" />
				<br>
			</td>
		</tr>

		<tr>
			<td class="label">
				故障开始时间*
				<br>
			</td>
			<td class="content">
				<input class="text" type="text" name="faultStartTime"
					value="${faultSheet.faultStartTime}"
					onclick="popUpCalendar(this, this,null,null,null,true,-1)"
					readonly="readonly" id="faultStartTime"
					alt="vtype:'lessThen',link:'faultEndTime',vtext:'开始时间不能晚于结束时间',allowBlank:false"></input>
				<br>
			</td>
			<td class="label">
				故障结束时间*
				<br>
			</td>
			<td class="content">
				<input class="text" type="text" name="faultEndTime"
					value="${faultSheet.faultEndTime}"
					onclick="popUpCalendar(this, this,null,null,null,true,-1)"
					readonly="readonly" id="faultEndTime"
					alt="vtype:'moreThen',link:'faultStartTime',vtext:'故障结束时间不能早于故障开始时间',allowBlank:false" />
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
				<input class="text" type="text" name="base_Station_Name"
					value="${faultSheet.base_Station_Name}" id="base_Station_Name"
					value="" alt="allowBlank:false" />
				<br>
			</td>
		</tr>
		<tr>
			<td class="label">
				站号*

			</td>
			<td class="content">
				<input class="text" type="text" name="stationNo" id="stationNo"
					value="${faultSheet.stationNo}" value="" alt="allowBlank:false" />
				<br>
			</td>
			<td class="label">
				基站属地*

			</td>
			<td class="content">
				<input class="text" type="text" name="base_Station_Location"
					value="${faultSheet.base_Station_Location}"
					id="base_Station_Location" value="" alt="allowBlank:false" />
				<br>
			</td>
		</tr>
		<tr>
		<tr>
			<td class="label">
				所属BSC名*

			</td>
			<td class="content">
				<input class="text" type="text" name="bscNo" id="bscNo"
					value="${faultSheet.bscNo}" alt="allowBlank:false" />
				<br>
			</td>
			<td class="label">
				BCF号*

			</td>
			<td class="content">
				<input class="text" type="text" name="bcfNo" id="bcfNo"
					value="${faultSheet.bcfNo}" alt="allowBlank:false" />
				<br>
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
				任务来源*
				<br>
			</td>
			<td class="content">
				<input class="text" type="text" name="task_Sources"
					value="${faultSheet.task_Sources}" id="task_Sources"
					alt="allowBlank:false"></input>

			</td>
			<td class="label">
				任务专业*
				<br>
			</td>
			<td class="content">
				<input class="text" type="text" name="task_Profession"
					value="${faultSheet.task_Profession}" id="task_Profession"
					alt="allowBlank:false"></input>

			</td>
		</tr>

		<tr>
			<td class="label">
				任务子类*
				<br>
			</td>
			<td class="content">
				<input class="text" type="text" name="task_Subclass"
					value="${faultSheet.task_Subclass}" id="task_Subclass"
					alt="allowBlank:false"></input>

			</td>
			<td class="label">
				重要程度*
				<br>
			</td>
			<td class="content">
				<input class="text" type="text" name="importance" id="importance"
					value="${faultSheet.importance}" alt="allowBlank:false"></input>
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
				工单类型*
				<br>
			</td>

			<td class="content" colspan="3">
				<select id="work_Order_Type" value="${faultSheet.work_Order_Type}"
					name="work_Order_Type" class="text" alt="allowBlank:false">
					<option value="一般">
						一般工单
					</option>
					<option value="1级工单">
						1级工单
					</option>
					<option value="2级工单">
						2级工单
					</option>
					<option value="3级工单">
						3级工单
					</option>
					<option value="4级工单">
						4级工单
					</option>
				</select>
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
				技术难度*
			</td>
			<td class="content">
				<input class="text" type="text" name="techniqueDifficulty"
					value="${faultSheet.techniqueDifficulty}" id="techniqueDifficulty"
					alt="allowBlank:false">
				</input>

				<br>
			</td>
			<td class="label">
				协调难度*
			</td>
			<td class="content">
				<input class="text" type="text" name="coordinateDifficulty"
					value="${faultSheet.coordinateDifficulty}"
					id="coordinateDifficulty" alt="allowBlank:false">
				</input>

				<br>
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
				故障类型*
			</td>
			<td class="content" colspan="3">
				<input class="text" type="text" name="faultType" id="faultType"
					value="${faultSheet.faultType}" alt="allowBlank:false">
				</input>
			</td>
		</tr>
		<tr>
			<td class="label">
				故障描述*
			</td>
			<td class="content" colspan="3">
				<textarea name="faultDescription" id="faultDescription" class="textarea max"
					alt="allowBlank:false">${faultSheet.faultDescription}</textarea>

			</td>
		</tr>
		<tr>

			<td class="label">
				故障初步处理结果*
				<br>
			</td>
			<td class="content" colspan="3">
				<textarea name="recovery_Processing_Result" class="textarea max"
					id="recovery_Processing_Result" alt="allowBlank:false">${faultSheet.recovery_Processing_Result}</textarea>
			</td>
		</tr>
		<tr>
			<td class="label">
				故障处理建议*
				<br>
			</td>
			<td colspan="3">
				<textarea name="fault_Handling_Suggestions" class="textarea max"
					id="fault_Handling_Suggestions" alt="allowBlank:false">${faultSheet.fault_Handling_Suggestions}</textarea>
				<br>
			</td>
		</tr>
	</table>
	<html:submit styleClass="btn" property="method.save"
		styleId="method.save" value="保存"></html:submit>
	<html:reset styleClass="btn" styleId="method.reset" value="重置"></html:reset>
</form>
<%@ include file="/common/footer_eoms.jsp"%>