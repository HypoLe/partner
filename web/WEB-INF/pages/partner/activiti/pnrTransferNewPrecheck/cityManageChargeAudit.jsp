<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript" src="${app}/scripts/widgets/TabPanel.js"></script>
<script type="text/javascript" src="${app}/scripts/Sheet.js"></script>
<script language="javaScript" type="text/javascript"
	src="${app}/scripts/module/partner/ajax.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript">
var jq=$.noConflict(); //jquery交出控制权，以免和EXTJS冲突
var roleTree;
var v;
  Ext.onReady(function()
  {
   	v = new eoms.form.Validation({form:'theform'});
    v.custom = function(){
      return validateBudgetAmount();   
    }
  });
  
   function changeType1()
	  {
	  	var _projectAmount=${pnrTransferOffice.projectAmount}
	  	//alert(jq("input:checkbox[name='files']"));
	  	//alert(jq(window.frames["UIFrame1-cityManageExamine"].document).find("input:checkbox[name='files']").length);
	  	if(_projectAmount>5000){
	  		if(jq(window.frames["UIFrame1-cityManageExamine"].document).find("input:checkbox[name='files']").length < 1){
	  			alert("请上传会议纪要");
	  			return false;
			}
	  	}
	  }
   
   //市运维主管审批的时候,对项目金额进行判断	
	function validateBudgetAmount(){
		var rel=false;
		var tempProcessInstanceId=jq("#processInstanceId").val();
		var tempPrecheckFlag=jq("#precheckFlag").val();
		jq.ajax({ 
			type:"POST",
			url:"${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=validateBudgetAmount",  
			async: false,//控制同步     
			//data:{"processInstanceId":pare.val(),"budgetAmount":newtxt},
			data:{"processInstanceId":tempProcessInstanceId,"precheckFlag":tempPrecheckFlag},
			dataType:"json",
			success:function(data){
				if(data.result=="exceed"){//超出	审核不通过
				   alert("无法审批通过，已超过预算金额封顶值");
				   rel= false;
				}
				else if(data.result=="notExceed"){//不超出 审核通过
				   //document.forms[0].submit();
					// var temp = changeType1();
					// if(temp!=false){
					  //	v.submit();
					  //eoms.$("theform").onsubmit();
					// }
					 rel= true;
				}
				else if(data.result=="error"){//其他情况
					alert(data.content);
					rel= false;
				}                           			
			},
	 		error: function (err) {
	           alert(err);
	           rel= false;
	        }	
		});
		return rel;
	}
	
  function selectRes(){
		//var url = '${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=openRejectView';
		var url = '${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=showCommonRejectPage&sign=under';
		//var _sHeight = 180;
		var _sHeight = 200;
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
	    window.location.href = "${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method="+method+condition;
  }
   function waitWorkOrder(){
   		var condition=document.getElementById("condition").value;
  		window.location.href="${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=waitWorkOrder&processInstanceId=${processInstanceId}"+condition;
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

<div style="height:10"></div>

<html:form action="/pnrTransferNewPrecheck.do?method=transferProgram" styleId="theform" >
			<!-- 带注释为驳回时需要的参数 -->
			<input type="hidden" id="marker" name="marker" value="cityManageExamine" /><!-- 流程标识 -->	
            <input type="hidden" id="taskId" name="taskId" value="${taskId}"><!-- 任务ID -->
            <input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}"><!-- 流程ID -->
            <input type="hidden" id="returnPerson" name="returnPerson" value="${pnrTransferOffice.cityLineDirector}"><!-- 驳回人 -->
            <input type="hidden" id="rejectState" name="rejectState" value="cityManageChargeState#rollback"><!--驳回状态名值对  -->
            
            <input type="hidden" name="userId" value="${userId}">
            <input type="hidden" id="title" name="title" value="${pnrTransferOffice.theme}"><!-- 工单主题 -->
            <input type="hidden" id="titleId" name="titleId" value="${pnrTransferOffice.id}"><!-- 流程ID -->
            <input type="hidden" name="cityManageChargeState" value="handle">
            <input type="hidden" name="step" value="5">
			<input type="hidden" name="cityManageDirectorAudit" value="${pnrTransferOffice.cityManageDirector}">
			<input type="hidden" name="nextPerson" value="${pnrTransferOffice.cityManageDirector}">
			<input type="hidden" name="linkName" value="cityManageExamine">
			<input type="hidden" id="handleId" name="handleId" value="${handleId}" />
			
			<input type="hidden" id="condition" name="condition" value="${condition}" />
			
			<input type="hidden" name="directList" value="${directList}" /><!--跳转到待回复/待办列表标识-->
			
			<input type="hidden" id="precheckFlag" name="precheckFlag" value="${pnrTransferOffice.precheckFlag}" /><!-- 应急/常规 -->
			
<!-- 审批结果 -->
    <fieldset style="border:1px solid #dfdfdf;padding-right:10px;padding-bottom:10px;padding-left:10px;">
		<legend>
			审批结果
		</legend>			
				<table id="sheet" class="formTable" >
								
				 		<tr>
							<td class="label">附件</td>
							<td class="content" >
								<eoms:attachment name="sheetMain" property="sheetAccessories" 
						            scope="request" idField="cityManageExamine" appCode="pnrActTransferOffice" alt="allowBlank:false;请选择保存的附件"/> 
							</td>
					 		<td class="label">
									审批人*
								</td>
								<td class="content" >
								<input class="text" type="text" name="assignee" value="${doPerson }" alt="allowBlank:false,maxLength:500,vtext:'请填写审批人'"/>
								</td>
							
						</tr>		
						<tr>
						<td class="label">简要描述*</td>
							<td class="content" >
								<textarea class="textarea max" name="dealPerformer2" 
									id="dealPerformer2" alt="allowBlank:false,maxLength:500,vtext:'请填写简要描述'">${handleDescription }</textarea>
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
						 	<html:submit styleClass="btn" property="method.save" onclick="return changeType1();" styleId="method.save">
								通过
							</html:submit>&nbsp;&nbsp; 
							<html:button property="" styleClass="btn" onclick="selectRes()">驳回</html:button>&nbsp;&nbsp;
							<c:if test="${directList ne 'waitWorkOrderList'}">
							<html:button property="" styleClass="btn" onclick="waitWorkOrder()">待办</html:button>
							</c:if>
						</div>
			</fieldset>
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