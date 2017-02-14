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
            
            v1 = new eoms.form.Validation({form:'importForm'});
            v1.custom = function() {
           		var reg = "(.xls|.xlsx)$";
            	var file = jq("#importFile").val();
            	var right = file.match(reg);
            	if(right == null) {
            		alert("请选择Excel文件!");
            		return false;
            	} else {
            		return true;
            	}
            }
            
			$('sheetStartTime').value = new Date().format('Y-m-d H:i'); 
			$('sheeEndTime').value = new Date().format('Y-m-d H:i'); 	
			$('sheetConfirmTime').value = new Date().format('Y-m-d H:i'); 	
			$('bsfrDateTime').value = new Date().format('Y-m-d H:i'); 	
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
 
 function timeDValue() {
 }
 
function openImport(handler){
	var el = Ext.get('listQueryObject'); 
	if(el.isVisible()){
		el.slideOut('t',{useDisplay:true});
		handler.innerHTML = "打开导入界面";
	}
	else{
		el.slideIn();
		handler.innerHTML = "关闭导入界面";
	}
}


  
function saveImport() {
	//表单验证
	if(!v1.check()) {
		return;
	}
	
	new Ext.form.BasicForm('importForm').submit({
	method : 'post',
	url : "${app}/faultInfo/faultInfo.do?method=importRecord",
	  	waitTitle : '请稍后...',
		waitMsg : '正在导入数据,请稍后...',
	    success : function(form, action) {
			alert(action.result.infor);
			jq("#importFile").val("");
		},
		failure : function(form, action) {
			alert(action.result.infor);
			jq("#importFile").val("");
		}
    });
	
}
</script>
 
<content tag="heading">
<c:out value="基站故障记录添加页面" />
</content>  <br/><br/>
	
<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/layout_add.png"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">从Excel导入</span>
</div>

<div id="listQueryObject" 
	 style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
	<form action="faultInfo.do?method=importRecord" method="post" enctype="multipart/form-data" id="importForm" name="importForm">
			<fieldset>
				<legend>从Excel导入</legend>
				<table id="sheet" class="formTable">
					<tr>
						<td class="label">选择Excel文件</td>
						<td width="35%">
							<input id="importFile" type="file" name="importFile" class="file"  alt="allowBlank:false"/>
							<input type="button" onclick="saveImport()" value="确定"/>
						</td>
						<td width="35%">
						</td>
					</tr>
				</table>
			</fieldset>
	</form>
</div>

<br/>
	
	
	
	
	
	
	
	     
<form action="faultInfo.do?method=add" method="post" id="faultInfoForm" name="faultInfoForm" >
	<table id="sheet" class="formTable">
	
		<tr>
			<td colspan="4"><div class="ui-widget-header" >基本信息</div></td>
		</tr>
		
		<tr>
			<td class="label">
			 工单编号* 
			</td>
			<td class="content">
				<input class="text" type="text" name="sheetId"
					id="sheetId" alt="allowBlank:false" />
			</td>
			<td class="label">
				工单类别*
			</td>
			<td class="content">
				<eoms:comboBox name="sheetType"
					id="sheetType" initDicId="11802" alt="allowBlank:false" styleClass="select" />
			</td>
		</tr>
		
		<tr>
			<td class="label">
			 工单创建类别* 
			</td>
			<td class="content">
				<eoms:comboBox name="sheetStartType"
					id="sheetStartType" initDicId="11803" alt="allowBlank:false" styleClass="select" />
			</td>
			<td class="label">
				工单任务信息
			</td>
			<td class="content">
				<input class="text" type="text" name="sheetTaskInfor" id="sheetTaskInfor"
					value="" alt="allowBlank:true" />
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
					alt="vtype:'lessThen',link:'sheeEndTime',vtext:'创建时间不能晚于结束时间',allowBlank:false" />
			</td>
			<td class="label">
			 工单结束时间* 
			</td>
			<td class="content">
				<input class="text"
					onclick="popUpCalendar(this, this,'yyyy-mm-dd hh:ii',null,null,true,-1)"
					type="text" name="sheeEndTime" id="sheeEndTime"
					readonly="readonly"
					alt="vtype:'moreThen',link:'sheetStartTime',vtext:'创建时间不能晚于结束时间',allowBlank:false"/>
			</td>
		</tr>
		
		<tr>
			<td class="label">
				历时*
			</td>
			<td class="content" colspan="3">
				<input readonly="readonly" class="text" type="text" name="takeTime" id="takeTime"
					value="" alt="allowBlank:false" />
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
					alt="vtext:'工单确认时间',allowBlank:false" />
			</td>
		</tr>
		
		<tr>
			<td class="label">
			 工单接收人员* 
			</td>
			<td class="content">
				<input class="text" type="text" name="sheetReceivePerson"
					id="sheetReceivePerson" alt="allowBlank:false" />
			</td>
			<td class="label">
				工单结束人员*
			</td>
			<td class="content">
				<input class="text" type="text" name="sheetEndPerson" id="sheetEndPerson"
					value="" alt="allowBlank:false" />
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
					alt="vtext:'日期',allowBlank:false" />
			</td>
			<td class="label">
				驻点*
			</td>
			<td class="content">
				<input class="text" type="text" name="stagnationPoint" id="stagnationPoint"
					value="" alt="allowBlank:false" />
			</td>
		</tr>
		
		<tr>
			<td class="label">
			 基站名称* 
			</td>
			<td class="content">
				<input class="text" type="text" name="baseStationName"
					id="baseStationName" alt="allowBlank:false" />
			</td>
			<td class="label">
				维护级别*
			</td>
			<td class="content">
				<eoms:comboBox name="maintainLevel"
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
					id="processingResults" alt="allowBlank:true" />
			</td>
			<td class="label">
				故障类别*
			</td>
			<td class="content">
				<eoms:comboBox name="faultType"
					id="faultType" initDicId="11804" alt="allowBlank:false" styleClass="select" />
			</td>
		</tr>
		
		<tr>
			<td class="label">
			 是否退服* 
			</td>
			<td class="content">
				<eoms:comboBox name="isExit"
					id="isExit" initDicId="10301" alt="allowBlank:false" styleClass="select" />
			</td>
			<td class="label"> 
				设备调整 
			</td>
			<td class="content">
				<input class="text" type="text" name="deviceAdjust" id="deviceAdjust"
					value="" alt="allowBlank:true" />
			</td>
		</tr>
		
		<tr>
			<td class="label"> 
			 遗留问题  
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="residualProblem"
					id="residualProblem" alt="allowBlank:true"></textarea>
			</td>
			</td>
		</tr>
		<tr>
			<td class="label"> 
				其它 
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="other"
					id="other" alt="allowBlank:true"></textarea>
			</td>
		</tr>
		</table>
		
		<br/>
		
		<html:submit styleClass="btn" property="method.save"
			styleId="method.save" value="保存信息" ></html:submit>

		<html:reset styleClass="btn" styleId="method.reset" value="重置"></html:reset>
</form>



<%@ include file="/common/footer_eoms.jsp"%>