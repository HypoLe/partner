<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript" src="${app}/scripts/widgets/TabPanel.js"></script>
<script type="text/javascript" src="${app}/scripts/Sheet.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>


<script type="text/javascript">
var jq=$.noConflict(); //jquery交出控制权，以免和EXTJS冲突
var roleTree;
var v;
  Ext.onReady(function()
  {
   v = new eoms.form.Validation({form:'theform'});
  }
   );


  function changeType1()
  {
		
  }
  
   function selectRes(){
		var url = '${app}/activiti/roomDemolition/roomDemolition.do?method=openRejectView';
		var _sHeight = 180;
	    var _sWidth = 820;
	    var sTop=(window.screen.availHeight-_sHeight)/2;
	    var sLeft=(window.screen.availWidth-_sWidth)/2;
		var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
		window.showModalDialog(url,window,sFeatures);
	}
  
  function refresh(){//此刷新函数被弹出的子模态窗口调用。
  		var condition=document.getElementById("condition").value;
	    window.location.href = "${app}/activiti/roomDemolition/roomDemolition.do?method=listBacklog"+condition;
  }
</script>
<script type="text/javascript">
jq(function(){
	var tempEnergyIsstation=${roomDemolition.energyIsstation};
	if(tempEnergyIsstation=="1270301"){
		jq("#energyIsstation").attr("disabled","disabled");
	}
});

function changeEnergyIsstation(processInstanceId)
		{    
			var energyIsstationValue = document.getElementById("energyIsstation").value;
			var url="${app}/activiti/roomDemolition/roomDemolition.do?method=changeEnergyIsstation&energyIsstation="+energyIsstationValue+"&processInstanceId="+processInstanceId;
			if(energyIsstationValue!=null && energyIsstationValue!=""){
				Ext.Ajax.request({
					url : url ,
					method: 'POST',
					success: function ( result, request ) { 
						alert(result.responseText);
					},
					failure: function ( result, request) { 
						alert("对应能耗系统是否关站修改失败！");
					} 
				});
			}else{
				alert("对应能耗系统是否关站为必填！");
			}
		}

</script>

<%@ include file="/WEB-INF/pages/partner/activiti/roomDemolition/transfer_basis.jsp"%>
<%@ include file="/WEB-INF/pages/partner/activiti/roomDemolition/showAapprove_basis.jsp"%>
<%@ include file="/WEB-INF/pages/partner/activiti/roomDemolition/showAapproveBack_basis.jsp"%>


<div style="height:20"></div>

<html:form action="/roomDemolition.do?method=transferProgram" styleId="theform" >

	        <input type="hidden" id="marker" name="marker" value="worker" /><!-- 流程标识 marker和linkName隐藏域的值应该保持一致-->	
            <input type="hidden" name="taskId" value="${taskId}">
            <input type="hidden" name="processInstanceId" value="${processInstanceId}">
            <input type="hidden" id="returnPerson" name="returnPerson" value="${roomDemolition.provinceManageCharge}"><!-- 驳回人 -->
            <input type="hidden" name="userId" value="${userId}">
            <input type="hidden" name="title" value="${roomDemolition.theme}">
            <input type="hidden" name="titleId" value="${roomDemolition.id}">
            <input type="hidden" name="workerState" value="handle">
            <input type="hidden" name="step" value="4">
            <input type="hidden" name="orderAuditCheck" value="${roomDemolition.cityManageCharge}">
			<input type="hidden" name="nextPerson" value="${roomDemolition.cityManageCharge}">
            <input type="hidden" name="linkName" value="worker">
            <input type="hidden" id="handleId" name="handleId" value="${handleId}" />
            
            <input type="hidden" id="condition" name="condition" value="${condition}" />

<table id="sheet" class="formTable" >
				
 		<tr>
 			<td class="label">对应能耗系统是否关站*</td>
			<td class="content">
				<eoms:comboBox name="energyIsstation" id="energyIsstation"
					defaultValue="${roomDemolition.energyIsstation}" initDicId="12703"
					alt="allowBlank:false" styleClass="select" onchange="changeEnergyIsstation(${processInstanceId})" />
			</td>
 		
			<td class="label">机房拆除是否完成*</td>
			<td class="content">
				<eoms:comboBox name="isRecover" id="isRecover" defaultValue=""
						initDicId="10301" alt="allowBlank:false" styleClass="select"  />
			</td>
		</tr>
		<tr>
			<td class="label">附件</td>
			<td class="content" colspan="3">
				<eoms:attachment name="sheetMain" property="sheetAccessories" 
		            scope="request" idField="worker" appCode="pnrActTransferOffice" alt="allowBlank:false;请选择保存的附件"/> 
			</td>
		</tr>
		<tr>
 			<td class="label">
				处理描述*
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="dealPerformer2" 
					id="dealPerformer2" alt="allowBlank:false,maxLength:2000,vtext:'请填入处理描述，最多输入 2000 字符'"></textarea>
			</td>
		</tr>
	<!-- 	<tr>
		
		 <td class="label">
				预检预修处理人+手机号*
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="dealPerformer2" 
					id="dealPerformer2" alt="allowBlank:false,maxLength:500,vtext:'请填入处理人'"></textarea>
	     </td>
		</tr> -->
</table>

	
		<!-- buttons -->
		<div class="form-btns" id="btns" style="display:block">
	
			<html:submit styleClass="btn" property="method.save" onclick="javascript:changeType1();" styleId="method.save">
				回复
			</html:submit>&nbsp;&nbsp;
			<html:button property="" styleClass="btn" onclick="selectRes()">驳回</html:button>
		</div>
	</html:form>

<%@ include file="/common/footer_eoms.jsp"%>
<script type="text/javascript">
 approveBackSwitcher = new detailSwitcher();
  approveBackSwitcher.init({
	container:'approveBackHistory',
  	handleEl:'div.history-item-title-back'
  });
  
  
</script>  
<script type="text/javascript">
 approveSwitcher= new detailSwitcher();
 	 approveSwitcher.init({
	container:'approveHistory',
  	handleEl:'div.history-item-title'
  });
 </script>   