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

	Ext.onReady(function(){
	            new eoms.form.Validation({form:'FaultSheetManagementForm'});
				$('faultStartTime').value = new Date().format('Y-m-d H:i:s'); 
				$('faultEndTime').value = new Date().format('Y-m-d H:i:s'); 	
   });
   
 </script>

<content tag="heading">
<c:out value="黑龙江代维故障工单管理编辑页面" />
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
				主题*
			</td>
			<td class="content" colspan="3">
				<input class="text" type="text" disabled= "disabled" name="themess" disable= "true" id="themess" value="${faultSheetDetail.themess}"
					/>
			</td>
			</tr>
			<tr>
 <td class="label">
				工单编号*
			</td>
			<td class="content" >
				<input class="text" type="text" disabled= "disabled" name="work_OrderNo" id="work_OrderNo" value="${faultSheetDetail.work_OrderNo}"
					/>
			</td>
			 <td class="label">
				状态*
			</td>
			<td class="content" >
				<input class="text" type="text" disabled= "disabled" name="faultState" id="faultState" value="${faultSheetDetail.faultState}"
					/>
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
				<input class="text" type="text" disabled= "disabled" name="operatePerson" value="${faultSheetDetail.operatePerson}"
					id="operatePerson"></input>
			</td>
						<td class="label">
				操作人部门*
				<br>
			</td>
			<td class="content">
				<input class="text" type="text" disabled= "disabled" name="operatePerson_Department" value="${faultSheetDetail.operatePerson_Department}"
					id="operatePerson_Department" ></input>
			</td>
		</tr>
				<tr>
			<td class="label">
				操作人联系方式*
				<br>
			</td>
			<td class="content">
				<input class="text" type="text" disabled= "disabled" name="operatePerson_Contact" value="${faultSheetDetail.operatePerson_Contact}"
					id="operatePerson_Contact" ></input>
			</td>
						<td class="label">
				操作人当前角色*
				<br>
			</td>
			<td class="content">
				<input class="text" type="text" disabled= "disabled" name="operatePerson_Rule" value="${faultSheetDetail.operatePerson_Rule}"
					id="operatePerson_Rule" ></input>
			</td>
		</tr>
		<tr>
			<td class="label">
				派往对象*
				<br>
			</td>
			<td class="content">
				<input class="text" disabled= "disabled" type="text" name="detailment_Object" value="${faultSheetDetail.detailment_Object}"
					id="detailment_Object" ></input>
				<br>
			</td>	
			<td class="label">
				附件*
			</td>
			<td class="content">
				<eoms:attachment name="faultSheetDetail" scope="request" idField="attachment" property="attachment"
					 appCode="faultSheet"  alt="allowBlank:true" />
			</td>	
			    
		</tr>
			<tr>
			<td class="label">
				操作时间*
				<br>
			</td>
			<td class="content">
				<input class="text" type="text" disabled= "disabled"  name="operateTime" value="${faultSheetDetail.operateTime}"
					id="operateTime" ></input>
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
				<input class="text" disabled= "disabled"  type="text" name="processLimited"
					value="${faultSheetDetail.processLimited}" />
				<br>
			</td>
			<td class="label">
				归档时限*
				<br>
			</td>
			<td class="content"> 
				<input class="text"  disabled= "disabled"  type="text" name="file_TimeLimit"
					value="${faultSheetDetail.file_TimeLimit}"
					 id="file_TimeLimit" 
					 />
				<br>
			</td>
		</tr>

		<tr>
			<td class="label">
				故障开始时间*
				<br>
			</td> 
			<td class="content">
				<input class="text" disabled= "disabled"  type="text" name="faultStartTime" value="${faultSheetDetail.faultStartTime}"
				
					readonly="readonly" id="faultStartTime" alt="allowBlank:false"></input>
				<br>
			</td>
			<td class="label">
				故障结束时间*
				<br>
			</td>
			<td class="content">
				<input class="text" type="text" disabled= "disabled" name="faultEndTime" value="${faultSheetDetail.faultEndTime}"
					
					readonly="readonly" id="faultEndTime"  />
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
				<input class="text" type="text" disabled= "disabled" name="base_Station_Name" 
					id="base_Station_Name" value="${faultSheetDetail.base_Station_Name}" />
				<br>
			</td>
		</tr>
		<tr>
			<td class="label">
				站号*

			</td>
			<td class="content"> 
				<input class="text" type="text" disabled= "disabled" name="stationNo" id="stationNo" value="${faultSheetDetail.stationNo}"
					value="" />
				<br>
			</td>
			<td class="label">
				基站属地*

			</td>
			<td class="content">
				<input class="text" type="text" disabled= "disabled"  name="base_Station_Location" value="${faultSheetDetail.base_Station_Location}"
					id="base_Station_Location"  />
				<br>
			</td>
		</tr>
		<tr>
		<tr>
			<td class="label">
				所属BSC名*

			</td>
			<td class="content">
				<input class="text" type="text" disabled="disabled" name="bscNo" id="bscNo" value="${faultSheetDetail.bscNo}"
					 />
				<br>
			</td>
			<td class="label">
				BCF号*

			</td>
			<td class="content">
				<input class="text" type="text" disabled= "disabled" name="bcfNo" id="bcfNo" value="${faultSheetDetail.bcfNo}"
					 />
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
				<input class="text" type="text" disabled= "disabled" name="task_Sources" value="${faultSheetDetail.task_Sources}"
					id="task_Sources" ></input>

			</td>
			<td class="label">
				任务专业*
				<br>
			</td>
			<td class="content">
				<input class="text" type="text" disabled= "disabled"  name="task_Profession" value="${faultSheetDetail.task_Profession}"
					id="task_Profession" ></input>

			</td>
		</tr>

		<tr>
			<td class="label">
				任务子类*
				<br>
			</td>
			<td class="content">
				<input class="text" type="text" disabled= "disabled" name="task_Subclass" value="${faultSheetDetail.task_Subclass}"
					id="task_Subclass" ></input>

			</td>
			<td class="label">
				重要程度*
				<br>
			</td>
			<td class="content">
				<input class="text" type="text" disabled= "disabled" name="importance" id="importance" value="${faultSheetDetail.importance}"
					></input>
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
				<input class="text" type="text" disabled= "disabled" name="work_Order_Type" value="${faultSheetDetail.work_Order_Type}"
					id="work_Order_Type" ></input>

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
				<input class="text" type="text" disabled= "disabled" name="techniqueDifficulty" value="${faultSheetDetail.techniqueDifficulty}"
					id="techniqueDifficulty" >
				</input>

				<br>
			</td>
			<td class="label">
				协调难度*
			</td>
			<td class="content">
				<input class="text" type="text" disabled= "disabled" name="coordinateDifficulty" value="${faultSheetDetail.coordinateDifficulty}"
					id="coordinateDifficulty" >
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
				<input class="text" type="text" disabled= "disabled" name="faultType" id="faultType" value="${faultSheetDetail.faultType}"
					>
				</input>				
			</td>
		</tr>
		<tr>
			<td class="label">
				故障描述*
			</td>
			<td class="content" colspan="3">
				<textarea name="faultDescription" disabled= "disabled" id="faultDescription" 
					>${faultSheetDetail.faultDescription}</textarea>

			</td>
		</tr>
		<tr>

			<td class="label">
				故障初步处理结果*
				<br>
			</td>
			<td class="content" colspan="3">
				<textarea name="recovery_Processing_Result" disabled= "disabled" 
					id="recovery_Processing_Result" >${faultSheetDetail.recovery_Processing_Result}</textarea>
			</td>
		</tr>
		<tr>
			<td class="label">
				故障处理建议*
				<br>
			</td>
			<td colspan="3">
				<textarea name="fault_Handling_Suggestions" disabled= "disabled" 
					id="fault_Handling_Suggestions" >${faultSheetDetail.fault_Handling_Suggestions}</textarea>
				<br>
			</td>
		</tr>
  </table>
		<html:submit styleClass="btn" property="method.save"
			styleId="method.save" value="返回"></html:submit>
		
</form>

<%@ include file="/common/footer_eoms.jsp"%>