<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ page import="java.util.List"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
	Ext.onReady(function(){
		Ext.get('loadIndicator').setOpacity(0.0,{duration:2.0,callback:function(){this.hide();}});
	});
</script>
<div id="loadIndicator" class="loading-indicator">加载故障详细信息页面完毕...</div>
<div id="circuit"><logic:present name="circuit" scope="request">

	<table class="history-item-table" width="100%" cellpadding="0"
		cellspacing="0">
		<tr>
			<td class="label">分公司</td>
			<td><eoms:id2nameDB id="${circuit.city}"
				beanId="tawSystemAreaDao" /></td>
			<td class="label">归属县公司</td>
			<td><eoms:id2nameDB id="${circuit.country}"
				beanId="tawSystemAreaDao" /></td>
		</tr>
		<tr>
			<td class="label">故障段落</td>
			<td>${circuit.faultPart}</td>
			<td class="label">记录插入创建时间(服务器时间)</td>
			<td>${circuit.insertTime}</td>
		</tr>
		<tr>
			<td class="label">故障开始时间</td>
			<td>${circuit.faultBeginTime}</td>
			<td class="label">故障结束时间</td>
			<td class="content">${circuit.faultEndTime}</td>
		</tr>
		<tr>
			<td class="label">光缆类别</td>
			<td class="content">${circuit.cableSort}</td>
			<td class="label">代维公司</td>
			<td><eoms:id2nameDB id="${circuit.monitorCompany}"
				beanId="tawSystemAreaDao" /></td>
		</tr>
		<tr>
			<td class="label">光缆产权</td>
			<td class="content">${circuit.cableOwner}</td>
			<td class="label">是否影响业务*</td>
			<td>${circuit.ifEffect}</td>
		</tr>
		<tr>
			<td class="label">辖区内光缆长度</td>
			<td>${circuit.cableLength}</td>
			<td class="label">当前任务所有者</td>
			<td><eoms:id2nameDB id="${circuit.currentTaskOwner}"
				beanId="IItawSystemDictTypeDao" /></td>
		</tr>
		<tr>
			<td class="label">记录当前状态</td>
			<td>${circuit.statusCnName }</td>
		</tr>
		<tr>
			<td class="label">故障原因</td>
			<td colspan="3">${circuit.faultReason }</td>

		</tr>
		<tr>
			<td class="label">处理结果</td>
			<td colspan="3">${circuit.faultResult }</td>
			</td>
		</tr>
		<tr>
			<td class="label">备注</td>
			<td colspan="3">${circuit.faultRemark }</td>
			</td>
		</tr>
	</table>
	<br/>
	<table class="history-item-table" width="100%" cellpadding="0"
		cellspacing="0">
		<tr>
			<td class="label">代维公司意见</td>
			<td>${circuit.mainCutOption}</td>
		</tr>
		<tr>
			<td class="label">分公司意见</td>
			<td>${circuit.mainCityOption}</td>
		</tr>
		<tr>
			<td class="label">省公司意见</td>
			<td>${circuit.mainMasterOption}</td>
		</tr>

		
	</table>
	<br />
</logic:present> <logic:notPresent name="circuit" scope="request"> 
      无法显示故障历史记录信息页面
</logic:notPresent></div>
