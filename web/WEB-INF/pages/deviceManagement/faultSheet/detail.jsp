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
	            new eoms.form.Validation({form:'FaultSheetResponseForm'});
				myjs('faultStartTime').value = new Date().format('Y-m-d H:i:s'); 
				myjs('faultEndTime').value = new Date().format('Y-m-d H:i:s'); 	
   });
   
    function sucessResult(){
   myjs( "#dialog:ui-dialog" ).dialog( "destroy" );
		myjs( "#dialog-message" ).dialog({
			resizable: false,
			height:160,
			modal: true,
			buttons: {
				"确定": function() {
					myjs( this ).dialog( "close" );					
					myjs.ajax({
					  type:"POST",
					  url:"FaultSheetResponse.do?method=responseAddDeal&id="+'${faultSheetDetail.id}'+"&work_OrderNo="+'${faultSheetDetail.work_OrderNo}'+"&operatePerson="+'${faultSheetDetail.operatePerson}',
					success:reloadit,
					failure:fail		 	  
					 });	
				},
				取消: function() {
					myjs( this ).dialog( "close" );
				}
			}
		});
   };
function reloadit(){
window.location.href="/FaultSheetManagement.do?method=gotoSuccess1";

}
	function fail(){
	window.location.href="/FaultSheetManagement.do?method=gotoFail";
	}				  
 </script>

<content tag="heading">
<c:out value="黑龙江代维故障工单管理详情页面" />
</content>
<fieldset>
	<legend>
		接收故障工单详情
	</legend>
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
</fieldset>
<form
	action="FaultSheetResponse.do?method=responseAddDeal&themess=${faultSheetDetail.themess}&id=${faultSheetDetail.id}&work_OrderNo=${faultSheetDetail.work_OrderNo}&operatePerson=${faultSheetDetail.operatePerson}"
	method="post" styleId="FaultSheetResponseForm"
	id="FaultSheetResponseForm" name="FaultSheetResponseForm">
	<fieldset>
		<legend>
			回复故障工单
		</legend>
		<table id="sheet" class="formTable">
			<tr>
				<td class="label">
					故障结束时间
				</td>
				<td class="content">
					<input class="text"
						onclick="popUpCalendar(this, this,null,null,null,true,-1)"
						type="text" name="faultEndTime" id="faultEndTime"
						readonly="readonly" alt="allowBlank:false" />

				</td>
				<td class="label">
					故障处理结束时间
				</td>
				<td class="content">
					<input class="text"
						onclick="popUpCalendar(this, this,null,null,null,true,-1)"
						type="text" name="faultDealEndTime" id="faultDealEndTime"
						readonly="readonly" alt="allowBlank:false" />

				</td>
			<tr>
				<td class="label">
					处理结果
				</td>
				<td colspan="3">
				<textarea class="textarea max" name="faultDealResult"
					id="faultDealResult" alt="allowBlank:false"></textarea>

			</td>
			</tr>
			<tr>
				<td class="label">
					附件
					<br>
				</td>
				<td colspan="3">
					<eoms:attachment scope="request" idField="attachment"
						name="faultResponse" appCode="faultSheet" alt="allowBlank:true" />
				</td>
			</tr>
		</table>
	</fieldset>
  <input type="submit" value="回复">
	<input type="reset" value="重置" />

</form>
<%@ include file="/common/footer_eoms.jsp"%>