<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
	
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
	Ext.onReady(function(){
		Ext.get('loadIndicator').setOpacity(0.0,{duration:2.0,callback:function(){this.hide();}});
	});
</script>

<style type="text/css">
  .labelpartner {
	background: #DCDCDC;
    border: 1px solid #000;
    color: #000000;
    font-family: Arial, Helvetica, sans-serif;
    font-weight: normal;
    margin: 10px auto;
    padding: 3px;
    text-align: left;
    vertical-align: bottom;
    }
</style>

<div id="loadIndicator" class="loading-indicator">加载详细信息页面完毕...</div>
 
	<content tag="heading">
	<c:out value="基站故障记录详情页面" />
	</content><br/><br/>
	
	<table id="sheet" class="formTable">
		<tr>
			<td colspan="4"><div class="ui-widget-header" >基本信息</div></td>
		</tr>
		
		<tr>
			<td class="label">
			 工单编号* 
			</td>
			<td class="content">
				${faultInfo.sheetId}
			</td>
			<td class="label">
				工单类别*
			</td>
			<td class="content">
				 <eoms:id2nameDB id="${faultInfo.sheetType}" beanId="IItawSystemDictTypeDao" />
			</td>
		</tr>
		
		<tr>
			<td class="label">
			 工单创建类别* 
			</td>
			<td class="content">
				 <eoms:id2nameDB id="${faultInfo.sheetStartType }" beanId="IItawSystemDictTypeDao" />
			</td>
			<td class="label"> 
				工单任务信息 
			</td>
			<td class="content">
				${faultInfo.sheetTaskInfor}
			</td>
		</tr>
		
		<tr>
			<td class="label">
			 工单创建时间* 
			</td>
			<td class="content">
					 ${faultInfo.sheetStartTime} 
			</td>
			<td class="label">
			 工单结束时间* 
			</td>
			<td class="content">
				 ${faultInfo.sheeEndTime} 
			</td>
		</tr>
		
		<tr>
			<td class="label">
				历时*
			</td>
			<td class="content"  colspan="3">
				 ${faultInfo.takeTime} 
			</td>
		</tr>
		
		<tr>
			<td class="label">
				工单确认时间*
			</td>
			<td class="content" colspan="3">
				 ${faultInfo.sheetConfirmTime} 
			</td>
		</tr>
		
		<tr>
			<td class="label">
			 工单接收人员* 
			</td>
			<td class="content">
				 ${faultInfo.sheetReceivePerson} 
			</td>
			<td class="label">
				工单结束人员*
			</td>
			<td class="content">
				 ${faultInfo.sheetEndPerson} 
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
				 ${faultInfo.bsfrDateTime} 
			</td>
			<td class="label">
				驻点*
			</td>
			<td class="content">
				 ${faultInfo.stagnationPoint} 
			</td>
		</tr>
		
		<tr>
			<td class="label">
			 基站名称* 
			</td>
			<td class="content">
				 ${faultInfo.baseStationName} 
			</td>
			<td class="label">
				维护级别*
			</td>
			<td class="content">
				 <eoms:id2nameDB id="${faultInfo.maintainLevel }" beanId="IItawSystemDictTypeDao" />
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
				 ${faultInfo.processingResults} 
			</td>
			<td class="label">
				故障类别*
			</td>
			<td class="content">
				<eoms:id2nameDB id="${faultInfo.faultType }" beanId="IItawSystemDictTypeDao" />
			</td>
		</tr>
		
		<tr>
			<td class="label">
			 是否退服* 
			</td>
			<td class="content">
				<eoms:id2nameDB id="${faultInfo.isExit }" beanId="IItawSystemDictTypeDao" />
			</td>
			<td class="label"> 
				设备调整 
			</td>
			<td class="content">
				 ${faultInfo.deviceAdjust} 
			</td>
		</tr>
		
		<tr>
			<td class="label"> 
			 遗留问题  
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="residualProblem"
					value="${faultInfo.residualProblem}"
					id="residualProblem" alt="allowBlank:true" disabled="disabled" >${faultInfo.residualProblem}</textarea>
			</td>
			</td>
		</tr>
		<tr>
			<td class="label"> 
				其它 
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="other"
					value="${faultInfo.other}"
					id="other" alt="allowBlank:true" disabled="disabled" >${faultInfo.other}</textarea>
			</td>
		</tr>
		</table>


</form>

<%@ include file="/common/footer_eoms.jsp"%>