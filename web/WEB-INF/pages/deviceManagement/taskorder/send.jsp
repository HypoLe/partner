<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">

    var myjs=jQuery.noConflict();
	Ext.onReady(function(){
	            v = new eoms.form.Validation({form:'faultInfoForm'});
	            v.custom = function(){ 
	            	return true;
	            };
   });
   
	function returnBack(){
		window.history.back();
	}
	
 </script>
 	<input type="button" style="margin-right: 5px" onclick="returnBack();"
		value="返回"  class="btn"/><br/><br/>
	<content tag="heading">
	<c:out value="代维任务工单派送" />
	</content><br/><br/>
<form action="taskOrderAction.do?method=send" method="post"
	id="taskOrderForm" name="taskOrderForm">
	
	<!-- hidden area start -->
		<input type="hidden" name="id" value="${taskOrder.id}" />
		<input type="hidden" name="reportPerson" value="${taskOrder.number}" />
		<input type="hidden" name="deptId" value="${taskOrder.deptId}" />
		<input type="hidden" name="roleId" value="${taskOrder.roleId}" />
		<input type="hidden" name="contactNumber" value="${taskOrder.contactNumber}" />
		<input type="hidden" name="reportTime" value="${taskOrder.reportTime}" />
		<input type="hidden" name="isDeleted" value="${taskOrder.isDeleted}" />
		<input type="hidden" name="deleteTime" value="${taskOrder.deleteTime}" />
		<input type="hidden" name="remark1" value="${taskOrder.remark1}" />
		<input type="hidden" name="remark2" value="${taskOrder.remark2}" />
		<input type="hidden" name="remark3" value="${taskOrder.remark3}" />
		<input type="hidden" name="remark4" value="${taskOrder.remark4}" />
	<!-- hidden area end -->
	
		<table id="sheet" class="formTable">
	
		<tr>
			<td colspan="4"><div class="ui-widget-header" >基本信息</div></td>
		</tr>
		
		<tr>
			<td class="label">
			 工单编号* 
			</td>
			<td class="content">
				<input class="text" type="text" name="sheetId" value="${taskOrder.sheetId}"
					id="sheetId" alt="allowBlank:false" />
			</td>
			<td class="label">
				工单类别*
			</td>
			<td class="content">
				<select id="sheetType" name="sheetType" class="select">
					<option value="故障抢修" selected>故障抢修</option>
					<option value="故障抢修" >故障抢修</option>
					<option value="故障抢修" >故障抢修</option>
					<option value="故障抢修" >故障抢修</option>
					<option value="故障抢修" >故障抢修</option>
				</select>
			</td>
		</tr>
		
		<tr>
			<td class="label">
			 工单创建类别* 
			</td>
			<td class="content">
				<select id="sheetStartType" name="sheetStartType" class="select">
					<option value="关闭" selected>关闭</option>
					<option value="关闭" >关闭</option>
				</select>
			</td>
			<td class="label">
				工单任务信息*
			</td>
			<td class="content">
				<input class="text" type="text" name="sheetTaskInfor" id="sheetTaskInfor"
					value="${taskOrder.sheetTaskInfor}" alt="allowBlank:true" />
			</td>
		</tr>
		
		<tr>
			<td class="label">
			 工单创建时间* 
			</td>
			<td class="content">
					<input class="text"
					onclick="popUpCalendar(this, this,null,null,null,true,-1)"
					type="text" name="sheetStartTime" id="sheetStartTime"
					readonly="readonly"
					alt="vtype:'lessThen',link:'sheeEndTime',vtext:'创建时间不能晚于结束时间',allowBlank:false"
					value="${taskOrder.sheetStartTime}" />
			</td>
			<td class="label">
			 工单结束时间* 
			</td>
			<td class="content">
				<input class="text"
					onclick="popUpCalendar(this, this,null,null,null,true,-1)"
					type="text" name="sheeEndTime" id="sheeEndTime"
					readonly="readonly"
					alt="vtype:'moreThen',link:'sheetStartTime',vtext:'创建时间不能晚于结束时间',allowBlank:false"
					value="${taskOrder.sheeEndTime}" />
			</td>
		</tr>
		
		
		<tr>
			<td class="label">
				工单确认时间*
			</td>
			<td class="content">
				<input class="text"
					onclick="popUpCalendar(this, this,null,null,null,true,-1)"
					type="text" name="sheetConfirmTime" id="sheetConfirmTime"
					readonly="readonly"
					alt="vtext:'工单确认时间',allowBlank:false"
					value="${taskOrder.sheetConfirmTime}" />
			</td>
			<td class="label">
				历时*
			</td>
			<td class="content">
				<input class="text" type="text" name="takeTime" id="takeTime"
					value="${taskOrder.takeTime}" alt="allowBlank:false" />
			</td>
		</tr>
		
		<tr>
			<td class="label">
			 工单接收人员* 
			</td>
			<td class="content">
				<input class="text" type="text" name="sheetReceivePerson"
					value="${taskOrder.sheetReceivePerson}" 
					id="sheetReceivePerson" alt="allowBlank:false" />
			</td>
			<td class="label">
				工单结束人员*
			</td>
			<td class="content">
				<input class="text" type="text" name="sheetEndPerson" id="sheetEndPerson"
					value="${taskOrder.sheetEndPerson}"  alt="allowBlank:false" />
			</td>
		</tr>
		
		
		
		
		<tr>
			<td colspan="4">
				<div  class="ui-widget-header">故障信息</div>
			</td>
		</tr>
		
		
		
		<tr>
			<td class="label">
			 日期* 
			</td>
			<td class="content">
				<input class="text"
					onclick="popUpCalendar(this, this,null,null,null,true,-1)"
					type="text" name="bsfrDateTime" id="bsfrDateTime"
					readonly="readonly"
					alt="vtext:'日期',allowBlank:false" 
					value="${taskOrder.bsfrDateTime}"
					/>
			</td>
			<td class="label">
				驻点*
			</td>
			<td class="content">
				<input class="text" type="text" name="stagnationPoint" id="stagnationPoint"
					value="${taskOrder.stagnationPoint}" alt="allowBlank:false" />
			</td>
		</tr>
		
		<tr>
			<td class="label">
			 基站名称* 
			</td>
			<td class="content">
				<input class="text" type="text" name="baseStationName"
					value="${taskOrder.baseStationName}"
					id="baseStationName" alt="allowBlank:false" />
			</td>
			<td class="label">
				维护级别*
			</td>
			<td class="content">
				<select id="maintainLevel" name="maintainLevel" class="select">
					<option value="LEVEL1" selected>LEVEL1</option>
					<option value="LEVEL2" >LEVEL2</option>
					<option value="LEVEL3" >LEVEL3</option>
					<option value="LEVEL4" >LEVEL4</option>
					<option value="LEVEL5" >LEVEL5</option>
				</select>
			</td>
		</tr>
		
		
		
		<tr>
			<td colspan="4">
				<div  class="ui-widget-header">处理信息</div>
			</td>
		</tr>
		
		
		
		<tr>
			<td class="label">
			 处理结果* 
			</td>
			<td class="content">
				<input class="text" type="text" name="processingResults"
					value="${taskOrder.processingResults}"
					id="processingResults" alt="allowBlank:true" />
			</td>
			<td class="label">
				故障类别*
			</td>
			<td class="content">
				<select id="faultType" name="faultType" class="select">
					<option value="LEVEL1" selected>LEVEL1</option>
					<option value="LEVEL2" >LEVEL2</option>
					<option value="LEVEL3" >LEVEL3</option>
				</select>
			</td>
		</tr>
		
		<tr>
			<td class="label">
			 是否退服* 
			</td>
			<td class="content">
				<select id="isExit" name="isExit" class="select">
					<option value="yes" selected>是</option>
					<option value="no" >否</option>
				</select>
			</td>
			<td class="label">
				设备调整*
			</td>
			<td class="content">
				<input class="text" type="text" name="deviceAdjust" id="deviceAdjust"
					value="${taskOrder.deviceAdjust}" alt="allowBlank:true" />
			</td>
		</tr>
		
		<tr>
			<td class="label">
			 遗留问题* 
			</td>
			<td class="content">
				<textarea class="textarea max" name="residualProblem"
					value="${taskOrder.residualProblem}"
					id="residualProblem" alt="allowBlank:true">${taskOrder.residualProblem}</textarea>
			</td>
			</td>
			<td class="label">
				其它*
			</td>
			<td class="content">
				<textarea class="textarea max" name="other"
					value="${taskOrder.other}"
					id="other" alt="allowBlank:true">${taskOrder.other}</textarea>
			</td>
		</tr>
		
		
		</table>

		<html:submit styleClass="btn" property="method.save"
			styleId="method.save" value="保存修改" ></html:submit>

	<html:reset styleClass="btn" styleId="method.reset" value="重置"></html:reset>
</form>

<%@ include file="/common/footer_eoms.jsp"%>