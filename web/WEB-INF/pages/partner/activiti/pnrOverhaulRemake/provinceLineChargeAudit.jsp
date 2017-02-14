<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript" src="${app}/scripts/widgets/TabPanel.js"></script>
<script type="text/javascript" src="${app}/scripts/Sheet.js"></script>

<script type="text/javascript">
var roleTree;
var v;
  Ext.onReady(function()
  {
   v = new eoms.form.Validation({form:'theform'});
   v.custom = function(){ 
	     if(eoms.$('processType').value.trim()==""){
	       alert("大修/改造不能为空"); return false; 
	     } 
	     return true;
	}
	
	//回显 大修/改造
  	var targetValue="${pnrTransferOffice.themeInterface}";
  	var obj = document.getElementById("processType");
  	if(obj){
    var options = obj.options;
    if(options){
      var len = options.length;
      for(var i=0;i<len;i++){
        if(options[i].value == targetValue){
          options[i].defaultSelected = true;
          options[i].selected = true;
          return true;
        }
      }
    } else {
      alert('missing element(s)!');
    }
  } else {
    alert('missing element(s)!');
  }
   
  }
   );


  function changeType1()
  {
		
  }
  function selectRes(){
		var url = '${app}/activiti/pnrTransferPrecheck/pnrOverhaulRemake.do?method=openRejectView';
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
  		var condition=document.getElementById("condition").value;
	    window.location.href = "${app}/activiti/pnrTransferPrecheck/pnrOverhaulRemake.do?method="+method+condition;
  }
  
  function waitWorkOrder(){
  		var condition=document.getElementById("condition").value;
  		window.location.href="${app}/activiti/pnrTransferPrecheck/pnrOverhaulRemake.do?method=waitWorkOrder&processInstanceId=${processInstanceId}"+condition;
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
		
	function changeProcessType(processInstanceId)
		{    
			var processTypeValue = document.getElementById("processType").value;
			var url="${app}/activiti/pnrTransferPrecheck/pnrOverhaulRemake.do?method=changeProcessType&processType="+processTypeValue+"&processInstanceId="+processInstanceId;
			if(processTypeValue!=null && processTypeValue!=""){
				Ext.Ajax.request({
					url : url ,
					method: 'POST',
					success: function ( result, request ) { 
						alert(result.responseText);
					},
					failure: function ( result, request) { 
						alert("大修/改造修改失败！");
					} 
				});
			}else{
				alert("大修/改造为必填！");
			}
		}
  
</script>
<%@ include file="/WEB-INF/pages/partner/activiti/pnrOverhaulRemake/transfer_basis.jsp"%>
<%@ include file="/WEB-INF/pages/partner/activiti/pnrOverhaulRemake/showAapprove_basis.jsp"%>
<%@ include file="/WEB-INF/pages/partner/activiti/pnrOverhaulRemake/showAapproveBack_basis.jsp"%>

<div style="height:20"></div>

<html:form action="/pnrOverhaulRemake.do?method=transferProgram" styleId="theform" >
			<!-- 带注释为驳回时需要的参数 -->
			<input type="hidden" id="marker" name="marker" value="provinceLineExamine" /><!-- 流程标识 -->	
            <input type="hidden" id="taskId" name="taskId" value="${taskId}"><!-- 任务ID -->
            <input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}"><!-- 流程ID -->
            <input type="hidden" id="returnPerson" name="returnPerson" value="${pnrTransferOffice.cityGS}"><!-- 驳回人 -->
            
            <input type="hidden" name="userId" value="${userId}">
            <input type="hidden" id="title" name="title" value="${pnrTransferOffice.theme}"><!-- 工单主题 -->
            <input type="hidden" id="titleId" name="titleId" value="${pnrTransferOffice.id}"><!-- 流程ID -->
            <input type="hidden" name="provinceLineChargeState" value="handle">
            <input type="hidden" name="step" value="8">
			<input type="hidden" name="provinceLineViceAudit" value="${pnrTransferOffice.provinceLine}">
			<input type="hidden" name="nextPerson" value="${pnrTransferOffice.provinceLine}">
			<input type="hidden" name="linkName" value="provinceLineExamine">

			<input type="hidden" name="directList" value="${directList}" /><!--跳转到待回复/待办列表标识-->
			
			<input type="hidden" id="condition" name="condition" value="${condition}" />
						
<table id="sheet" class="formTable" >
				
 		<tr>
			<td class="label">附件</td>
			<td class="content" >
				<eoms:attachment name="sheetMain" property="sheetAccessories" 
		            scope="request" idField="provinceLineExamine" appCode="pnrActTransferOffice" alt="allowBlank:false;请选择保存的附件"/> 
			</td>
 		<td class="label" align="center">简要描述</td>
			<td class="content" >
				<textarea class="textarea max" name="dealPerformer2" 
					id="dealPerformer2" alt="allowBlank:true,maxLength:500,vtext:'请填写简要描述'"></textarea>
			</td>
			
		</tr>		
		<tr>
 			<td class="label">
				审批人
			</td>
			<td class="content" >
			<input class="text" type="text" name="assignee"/>
			</td>
			<td class="label">
				紧急程度*
			</td>
			<td class="content">
			<eoms:comboBox name="workOrderDegree" id="workOrderDegree"
					defaultValue="${pnrTransferOffice.workOrderDegree}" initDicId="1012309"
					alt="allowBlank:false" styleClass="select" onchange="changeDegree(${processInstanceId})" />&nbsp;&nbsp;
			</td>
		</tr>
		<tr>
			<td class="label">
				大修/改造*
			</td>
			<td class="content" colspan="3">
				<select id="processType" name="processType" class="select" onchange="changeProcessType(${processInstanceId})" alt="allowBlank:false">
					<option value="">
						请选择
					</option>
					<option value="overhaul">
						大修工单
					</option>
					<option value="reform">
						改造工单
					</option>
			</select>
			</td>
		</tr>
		
</table>
		<!-- buttons -->
		<div class="form-btns" id="btns" style="display:block">
	
			<html:submit styleClass="btn" property="method.save" onclick="javascript:changeType1();" styleId="method.save">
				通过
			</html:submit>&nbsp;&nbsp;
			<html:button property="" styleClass="btn" onclick="selectRes()">驳回</html:button>&nbsp;&nbsp;
			<c:if test="${directList ne 'waitWorkOrderList'}">
			<html:button property="" styleClass="btn" onclick="waitWorkOrder()">待办</html:button>
			</c:if>
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