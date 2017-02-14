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
	            new eoms.form.Validation({form:'FaultSheetManagementForm'});
				myjs('faultStartTime').value = new Date().format('Y-m-d H:i:s'); 
				myjs('faultEndTime').value = new Date().format('Y-m-d H:i:s'); 	
   });
   
 </script>

<content tag="heading">
<c:out value="黑龙江代维故障工单管理添加页面" />
</content>
<form action="FaultSheetManagement.do?method=add"
	id="FaultSheetManagementForm" method="post"  
name="FaultSheetManagementForm"
	>
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
				派往对象*
				<br>
			</td>
			<td class="content" colspan="3">
			<fieldset>
			<legend>派发到其他地方去 </legend> 
			<eoms:chooser id="test" type="dept" config="{returnJSON:true,showLeader:true}"
			category="[{id:'detailment_Object',text:'派发',childType:'dept,user',allowBlank:false,vtext:'请选择抄送对象'}]" />
		</fieldset>
		
			</td>
			
		</tr>
		<tr>
			<td class="label">
				附件*
				<br>
			</td>
			<td colspan="3">
				<eoms:attachment scope="request" idField="attachment"
					name="attachment" appCode="faultSheet" alt="allowBlank:true" />
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
					onclick="popUpCalendar(this, this,null,null,null,true,-1)"
					readonly="readonly" id="processLimited" alt="allowBlank:false" />
				<br>
			</td>
			<td class="label">
				归档时限*
				<br>
			</td>
			<td class="content">
				<input class="text" type="text" name="file_TimeLimit"
					onclick="popUpCalendar(this, this,null,null,null,true,-1)"
					readonly="readonly" id="file_TimeLimit" alt="allowBlank:false" />
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
					onclick="popUpCalendar(this, this,null,null,null,true,-1)"
					readonly="readonly" id="faultEndTime"
					alt="vtype:'moreThen',link:'faultStartTime',vtext:'故障结束时间不能早于故障开始时间',allowBlank:false" />
			</td>
		</tr>
		<tr>
			<td class="label">
				工单接收时限*
				<br>
			</td>
			<td class="content">
				<input class="text" type="text" name="receivedtimelimited"
					onclick="popUpCalendar(this, this,null,null,null,true,-1)"
					readonly="readonly" id="receivedtimelimited"
					alt="allowBlank:false"></input>
				<br>
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
					id="base_Station_Name" value="" alt="allowBlank:false" />
				<br>
			</td>
		</tr>
		<tr>
			<td class="label">
				站号*

			</td>
			<td class="content">
				<input class="text" type="text" name="stationNo" id="stationNo"
					value="" alt="allowBlank:false" />
				<br>
			</td>
			<td class="label">
				基站属地*

			</td>
			<td class="content">
				<input class="text" type="text" name="base_Station_Location"
					id="base_Station_Location" value="" alt="allowBlank:false" />
				<br>
			</td>
		</tr>
		
		<tr>
			<td class="label">
				所属BSC名*

			</td>
			<td class="content">
				<input class="text" type="text" name="bscNo" id="bscNo" value=""
					alt="allowBlank:false" />
				<br>
			</td>
			<td class="label">
				BCF号*

			</td>
			<td class="content">
				<input class="text" type="text" name="bcfNo" id="bcfNo" value=""
					alt="allowBlank:false" />
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
					id="task_Sources" alt="allowBlank:false"></input>

			</td>
			<td class="label">
				任务专业*
				<br>
			</td>
			<td class="content">
				<input class="text" type="text" name="task_Profession"
					id="task_Profession" alt="allowBlank:false"></input>

			</td>
		</tr>

		<tr>
			<td class="label">
				任务子类*
				<br>
			</td>
			<td class="content">
				<input class="text" type="text" name="task_Subclass"
					id="task_Subclass" alt="allowBlank:false"></input>

			</td>
			<td class="label">
				重要程度*
				<br>
			</td>
			<td class="content">
				<input class="text" type="text" name="importance" id="importance"
					alt="allowBlank:false"></input>
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
	<select id="work_Order_Type" name="work_Order_Type" class="text">
					<option value="一般" selected>一般工单</option>
					<option value="1级工单" >1级工单</option>
					<option value="2级工单" >2级工单</option>
					<option value="3级工单" >3级工单</option>
					<option value="4级工单" >4级工单</option>
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
					id="techniqueDifficulty" alt="allowBlank:false">
				</input>

				<br>
			</td>
			<td class="label">
				协调难度*
			</td>
			<td class="content">
				<input class="text" type="text" name="coordinateDifficulty"
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
					alt="allowBlank:false">
				</input>
			</td>
		</tr>
		<tr>
			<td class="label">
				故障描述*
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="faultDescription" id="faultDescription"
					alt="allowBlank:false"></textarea>               
			</td>
		</tr>
		<tr>

			<td class="label">
				故障初步处理结果*
				<br>
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="recovery_Processing_Result"
					id="recovery_Processing_Result" alt="allowBlank:false"></textarea>
			</td>
		</tr>
		<tr>
			<td class="label">
				故障处理建议*
			</td>
			<td colspan="3">
				<textarea class="textarea max" name="fault_Handling_Suggestions"
					id="fault_Handling_Suggestions" alt="allowBlank:false"></textarea>
				<br>
			</td>
		</tr>
	</table>
	<html:submit styleClass="btn" property="method.save"
		styleId="method.save" value="保存"></html:submit>
	<html:reset styleClass="btn" styleId="method.reset" value="重置"></html:reset>
</form>
<%@ include file="/common/footer_eoms.jsp"%>