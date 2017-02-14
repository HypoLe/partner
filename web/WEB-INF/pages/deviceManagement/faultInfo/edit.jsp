<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">

    var jq=jQuery.noConflict();
	Ext.onReady(function(){
	
            v = new eoms.form.Validation({form:'faultInfoForm'});
            v.custom = function(){ 
           	generateTakeTime();
           		return true;
          		};
   });
 
function generateTakeTime() {
 	var startTime = jq("#sheetStartTime").val();
 	var endTime = jq("#sheeEndTime").val();
 	
    var reg ="^([0-9]{4})-([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2})$"; 
    var s = startTime.match(reg);
    var e = endTime.match(reg);
    
    var DValue = Math.floor((e[4]-s[4])*60) + (e[5]-s[5]);
    
    if(DValue < 0) {
    	alert("时间差为负值，请检查!");
    	return;
    }
    
    var timeDiffer = Math.floor((DValue/60)) +":"+ ((DValue%60)>9?(DValue%60):("0"+(DValue%60)))+":00";
    
 	jq("#takeTime").attr("value",timeDiffer);
 }
   
	function returnBack(){
		window.history.back();
	}
	
 </script>
 	<input type="button" style="margin-right: 5px" onclick="returnBack();"
		value="返回"  class="btn"/><br/><br/>
	<content tag="heading">
	<c:out value="基站故障记录修改页面" />
	</content><br/><br/>
<form action="faultInfo.do?method=edit" method="post"
	id="faultInfoForm" name="faultInfoForm">
	
	<!-- hidden area start -->
		<input type="hidden" name="id" value="${faultInfo.id}" />
		<input type="hidden" name="reportPerson" value="${faultInfo.reportPerson}" />
		<input type="hidden" name="deptId" value="${faultInfo.deptId}" />
		<input type="hidden" name="roleId" value="${faultInfo.roleId}" />
		<input type="hidden" name="contactNumber" value="${faultInfo.contactNumber}" />
		<input type="hidden" name="reportTime" value="${faultInfo.reportTime}" />
		<input type="hidden" name="isDeleted" value="${faultInfo.isDeleted}" />
		<input type="hidden" name="deleteTime" value="${faultInfo.deleteTime}" />
		<input type="hidden" name="remark1" value="${faultInfo.remark1}" />
		<input type="hidden" name="remark2" value="${faultInfo.remark2}" />
		<input type="hidden" name="remark3" value="${faultInfo.remark3}" />
		<input type="hidden" name="remark4" value="${faultInfo.remark4}" />
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
				<input class="text" type="text" name="sheetId" value="${faultInfo.sheetId}"
					id="sheetId" alt="allowBlank:false" />
			</td>
			<td class="label">
				工单类别*
			</td>
			<td class="content">
				<eoms:comboBox name="sheetType" defaultValue="${faultInfo.sheetType}"
					id="sheetType" initDicId="11802" alt="allowBlank:false" styleClass="select" />
			</td>
		</tr>
		
		<tr>
			<td class="label">
			 工单创建类别* 
			</td>
			<td class="content">
				<eoms:comboBox name="sheetStartType" defaultValue="${faultInfo.sheetStartType}"
					id="sheetStartType" initDicId="11803" alt="allowBlank:false" styleClass="select" />
			</td>
			<td class="label"> 
				工单任务信息 
			</td>
			<td class="content">
				<input class="text" type="text" name="sheetTaskInfor" id="sheetTaskInfor"
					value="${faultInfo.sheetTaskInfor}" alt="allowBlank:true" />
			</td>
		</tr>
		
		<tr>
			<td class="label">
			 工单创建时间* 
			</td>
			<td class="content">
					<input class="text"
					onclick="popUpCalendar(this, this,'yyyy-mm-dd hh:ii',null,null,true,-1)"
					type="text" name="sheetStartTime" id="sheetStartTime"
					readonly="readonly"
					alt="vtype:'lessThen',link:'sheeEndTime',vtext:'创建时间不能晚于结束时间',allowBlank:false"
					value="${faultInfo.sheetStartTime}" />
			</td>
			<td class="label">
			 工单结束时间* 
			</td>
			<td class="content">
				<input class="text"
					onclick="popUpCalendar(this, this,'yyyy-mm-dd hh:ii',null,null,true,-1)"
					type="text" name="sheeEndTime" id="sheeEndTime"
					readonly="readonly"
					alt="vtype:'moreThen',link:'sheetStartTime',vtext:'创建时间不能晚于结束时间',allowBlank:false"
					value="${faultInfo.sheeEndTime}" />
			</td>
		</tr>
		
		<tr>
			<td class="label">
				历时*
			</td>
			<td class="content" colspan="3">
				<input readonly="readonly" class="text" type="text" name="takeTime" id="takeTime"
					value="${faultInfo.takeTime}" alt="allowBlank:false" />
				<input type="button" class="btn" onclick="generateTakeTime()" value="生成历时"/>
			</td>
		</tr>
		
		<tr>
			<td class="label">
				工单确认时间*
			</td>
			<td class="content" colspan="3">
				<input class="text"
					onclick="popUpCalendar(this, this,null,null,null,true,-1)"
					type="text" name="sheetConfirmTime" id="sheetConfirmTime"
					readonly="readonly"
					alt="vtext:'工单确认时间',allowBlank:false"
					value="${faultInfo.sheetConfirmTime}" />
			</td>
		</tr>
		
		<tr>
			<td class="label">
			 工单接收人员* 
			</td>
			<td class="content">
				<input class="text" type="text" name="sheetReceivePerson"
					value="${faultInfo.sheetReceivePerson}" 
					id="sheetReceivePerson" alt="allowBlank:false" />
			</td>
			<td class="label">
				工单结束人员*
			</td>
			<td class="content">
				<input class="text" type="text" name="sheetEndPerson" id="sheetEndPerson"
					value="${faultInfo.sheetEndPerson}"  alt="allowBlank:false" />
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
					value="${faultInfo.bsfrDateTime}"
					/>
			</td>
			<td class="label">
				驻点*
			</td>
			<td class="content">
				<input class="text" type="text" name="stagnationPoint" id="stagnationPoint"
					value="${faultInfo.stagnationPoint}" alt="allowBlank:false" />
			</td>
		</tr>
		
		<tr>
			<td class="label">
			 基站名称* 
			</td>
			<td class="content">
				<input class="text" type="text" name="baseStationName"
					value="${faultInfo.baseStationName}"
					id="baseStationName" alt="allowBlank:false" />
			</td>
			<td class="label">
				维护级别*
			</td>
			<td class="content">
				<eoms:comboBox name="maintainLevel" defaultValue="${faultInfo.maintainLevel}"
					id="maintainLevel" initDicId="11801" alt="allowBlank:false" styleClass="select" />
			</td>
		</tr>
		
		
		
		<tr>
			<td colspan="4">
				<div  class="ui-widget-header">处理信息</div>
			</td>
		</tr>
		
		
		
		<tr>
			<td class="label"> 
			 处理结果  
			</td>
			<td class="content">
				<input class="text" type="text" name="processingResults"
					value="${faultInfo.processingResults}"
					id="processingResults" alt="allowBlank:true" />
			</td>
			<td class="label"> 
				故障类别 
			</td>
			<td class="content">
				<eoms:comboBox name="faultType" defaultValue="${faultInfo.faultType}"
					id="faultType" initDicId="11804" alt="allowBlank:false" styleClass="select" />
			</td>
		</tr>
		
		<tr>
			<td class="label">
			 是否退服* 
			</td>
			<td class="content">
				<eoms:comboBox name="isExit" defaultValue="${faultInfo.isExit}"
					id="isExit" initDicId="10301" alt="allowBlank:false" styleClass="select" />
			</td>
			<td class="label">
				设备调整
			</td>
			<td class="content">
				<input class="text" type="text" name="deviceAdjust" id="deviceAdjust"
					value="${faultInfo.deviceAdjust}" alt="allowBlank:true" />
			</td>
		</tr>
		
		<tr>
			<td class="label">
			 遗留问题
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="residualProblem"
					value="${faultInfo.residualProblem}"
					id="residualProblem" alt="allowBlank:true">${faultInfo.residualProblem}</textarea>
			</td>
		</tr>
		<tr>
			<td class="label">
				其它
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="other"
					value="${faultInfo.other}"
					id="other" alt="allowBlank:true">${faultInfo.other}</textarea>
			</td>
		</tr>
		</table>

		<br/>
		
		<html:submit styleClass="btn" property="method.save"
			styleId="method.save" value="保存修改" ></html:submit>
		<html:reset styleClass="btn" styleId="method.reset" value="重置"></html:reset>
</form>

<%@ include file="/common/footer_eoms.jsp"%>