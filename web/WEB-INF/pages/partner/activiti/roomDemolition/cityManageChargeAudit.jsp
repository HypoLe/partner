<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript" src="${app}/scripts/widgets/TabPanel.js"></script>
<script type="text/javascript" src="${app}/scripts/Sheet.js"></script>
<script language="javaScript" type="text/javascript"
	src="${app}/scripts/module/partner/ajax.js"></script>

<script type="text/javascript">
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
		var url = '${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=openRejectView';
		var _sHeight = 180;
	    var _sWidth = 820;
	    var sTop=(window.screen.availHeight-_sHeight)/2;
	    var sLeft=(window.screen.availWidth-_sWidth)/2;
		var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
		window.showModalDialog(url,window,sFeatures);
	}
  
  function refresh(){//此刷新函数被弹出的子模态窗口调用。
  		var redirect ="${directList}";
  		var method ="listBacklog";
  		if ("waitWorkOrderList"==redirect){
  			method ="waitWorkOrderList";
  		}
	    window.location.href = "${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method="+method;
  }
   function waitWorkOrder(){
  		window.location.href="${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=waitWorkOrder&processInstanceId=${processInstanceId}";
  }
  
  function changeDegree(processInstanceId)
		{    
		    //delAllOption("country");//地市选择更新后，重新刷新县区
			var degree = document.getElementById("workOrderDegree").value;
			var url="${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=changeDegree&degree="+degree+"&processInstanceId="+processInstanceId;
			if(degree!=null && degree!=""){
			Ext.Ajax.request({
				url : url ,
				method: 'POST',
				success: function ( result, request ) { 
					alert(result.responseText);
				},
				failure: function ( result, request) { 
					alert("紧急程度修改失败！");
				} 
			});
			}
		}	
</script>
<%@ include file="/WEB-INF/pages/partner/activiti/pnrTransferNewPrecheck/transfer_basis.jsp"%>
<%@ include file="/WEB-INF/pages/partner/activiti/pnrTransferNewPrecheck/showAapprove_basis.jsp"%>
<%@ include file="/WEB-INF/pages/partner/activiti/pnrTransferNewPrecheck/showAapproveBack_basis.jsp"%>

<div style="height:20"></div>

<html:form action="/pnrTransferNewPrecheck.do?method=transferProgram" styleId="theform" >
			<!-- 带注释为驳回时需要的参数 -->
			<input type="hidden" id="marker" name="marker" value="cityManageExamine" /><!-- 流程标识 -->	
            <input type="hidden" id="taskId" name="taskId" value="${taskId}"><!-- 任务ID -->
            <input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}"><!-- 流程ID -->
            <input type="hidden" id="returnPerson" name="returnPerson" value="${pnrTransferOffice.cityLineDirector}"><!-- 驳回人 -->
            
            <input type="hidden" name="userId" value="${userId}">
            <input type="hidden" id="title" name="title" value="${pnrTransferOffice.theme}"><!-- 工单主题 -->
            <input type="hidden" id="titleId" name="titleId" value="${pnrTransferOffice.id}"><!-- 流程ID -->
            <input type="hidden" name="cityManageChargeState" value="handle">
            <input type="hidden" name="step" value="5">
			<input type="hidden" name="cityManageDirectorAudit" value="${pnrTransferOffice.cityManageDirector}">
			<input type="hidden" name="nextPerson" value="${pnrTransferOffice.cityManageDirector}">
			<input type="hidden" name="linkName" value="cityManageExamine">
			<input type="hidden" id="handleId" name="handleId" value="${handleId}" />
			
			<input type="hidden" name="directList" value="${directList}" /><!--跳转到待回复/待办列表标识-->
			
<table id="sheet" class="formTable" >
				
 		<tr>
			<td class="label">附件</td>
			<td class="content" >
				<eoms:attachment name="sheetMain" property="sheetAccessories" 
		            scope="request" idField="cityManageExamine" appCode="pnrActTransferOffice" alt="allowBlank:false;请选择保存的附件"/> 
			</td>
 		<td class="label">简要描述*</td>
			<td class="content" >
				<textarea class="textarea max" name="dealPerformer2" 
					id="dealPerformer2" alt="allowBlank:false,maxLength:500,vtext:'请填写简要描述'">${handleDescription }</textarea>
			</td>
			
		</tr>		
		<tr>
 			<td class="label">
				审批人*
			</td>
			<td class="content" >
			<input class="text" type="text" name="assignee" value="${doPerson }" alt="allowBlank:false,maxLength:500,vtext:'请填写审批人'"/>
			</td>
 			<td class="label">
				紧急程度
			</td>
			<td class="content">
			<eoms:comboBox name="workOrderDegree" id="workOrderDegree"
					defaultValue="${pnrTransferOffice.workOrderDegree}" initDicId="1012309"
					alt="allowBlank:false" styleClass="select" onchange="changeDegree(${processInstanceId})" />&nbsp;&nbsp;
			</td>
		</tr>
</table>
		<!-- buttons -->
		<div class="form-btns" id="btns" style="display:block">
	
			<html:submit styleClass="btn" property="method.save" onclick="javascript:changeType1();" styleId="method.save">
				通过
			</html:submit>&nbsp;&nbsp;
			<html:button property="" styleClass="btn" onclick="selectRes()">驳回</html:button>&nbsp;&nbsp;
			<html:button property="" styleClass="btn" onclick="waitWorkOrder()">待办</html:button>
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