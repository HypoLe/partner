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
  }
   );


  function changeType1()
  {
		
  }
  function selectRes(){
		//var url = '${app}/activiti/pnrHiddendangerReported/pnrHiddendangerReported.do?method=openRejectView';
		var url = '${app}/activiti/pnrHiddendangerReported/pnrHiddendangerReported.do?method=showCommonRejectPage&sign=under';
		//var _sHeight = 180;
		var _sHeight = 200;
	    var _sWidth = 820;
	    var sTop=(window.screen.availHeight-_sHeight)/2;
	    var sLeft=(window.screen.availWidth-_sWidth)/2;
		var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
		window.showModalDialog(url,window,sFeatures);
	}
  
  function refresh(){//此刷新函数被弹出的子模态窗口调用。
  		var condition=document.getElementById("condition").value;
	    window.location.href = "${app}/activiti/pnrHiddendangerReported/pnrHiddendangerReported.do?method=listBacklog"+condition;
  }
</script>
<%@ include file="/WEB-INF/pages/partner/activiti/pnrHiddendangerReported/transfer_basis.jsp"%>
<%@ include file="/WEB-INF/pages/partner/activiti/pnrHiddendangerReported/showAapprove_basis.jsp"%>
<%@ include file="/WEB-INF/pages/partner/activiti/pnrHiddendangerReported/showAapproveBack_basis.jsp"%>

<div style="height:10"></div>

<html:form action="/pnrHiddendangerReported.do?method=transferProgram" styleId="theform" >
			<!-- 带注释为驳回时需要的参数 -->
			<input type="hidden" id="marker" name="marker" value="workOrderCheck" /><!-- 流程标识 marker和linkName隐藏域的值应该保持一致-->	
            <input type="hidden" id="taskId" name="taskId" value="${taskId}"><!-- 任务ID -->
            <input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}"><!-- 流程ID -->
            <input type="hidden" id="returnPerson" name="returnPerson" value="${pnrTransferOffice.createWork}"><!-- 驳回人 -->
            <input type="hidden" id="rejectState" name="rejectState" value="workOrderState#rollback"><!--驳回状态名值对  -->
            
            <input type="hidden" name="userId" value="${userId}">
            <input type="hidden" id="title" name="title" value="${pnrTransferOffice.theme}"><!-- 工单主题 -->
            <input type="hidden" id="titleId" name="titleId" value="${pnrTransferOffice.id}"><!-- 流程ID -->
            <input type="hidden" name="workOrderState" value="handle">
            <input type="hidden" name="step" value="2">
			<input type="hidden" name="cityLineChargeAduit" value="${pnrTransferOffice.cityLineCharge}">
			<input type="hidden" name="nextPerson" value="${pnrTransferOffice.cityLineCharge}">
			<!--  <input type="hidden" name="linkName" value="workOrderAudit">-->
			<input type="hidden" name="linkName" value="workOrderCheck">
			<input type="hidden" id="handleId" name="handleId" value="${handleId}" />
			
			<input type="hidden" id="condition" name="condition" value="${condition}" />
			
			
			
	<!-- 审批结果 -->
    <fieldset style="border:1px solid #dfdfdf;padding-right:10px;padding-bottom:10px;padding-left:10px;">
		<legend>
			处理信息
		</legend>	
		<table id="sheet" class="formTable" >
	 		<tr>
				<td class="label">是否我方资源*</td>
				<td class="content" >
						<eoms:comboBox name="isMeRes" id="isMeRes"
						defaultValue="1030101" initDicId="10301"
						alt="allowBlank:false" styleClass="select" />
				</td>
				<td class="label">是否现场施工作业*</td>
				<td class="content" >
						<eoms:comboBox name="isScene" id="isScene"
						defaultValue="1030102" initDicId="10301"
						alt="allowBlank:false" styleClass="select" />
				</td>
	 		
	 			
			</tr>
			<tr>
				<td class="label">
					责任人*
				</td>
				<td class="content" colspan="3">
					<div class="x-form-item" >
					<eoms:chooser id="sendObject" panels="[{text:'部门与人员',dataUrl:'${app}/xtree.do?method=userFromDeptTask&dh=${country}',rootId:'${country}'}]"  config="{returnJSON:true,showLeader:true}"
					   category="[{id:'taskAssignee',text:'接收',allowBlank:false,childType:'user',limit:1,vtext:'请选择接收对象'}]"
					  data="" />
				</div>
				</td>			
			</tr>	
			<tr>
				<td class="label">备注</td>
				<td class="content" colspan="3">
					<textarea class="textarea max" name="remark" 
						id="remark" alt="allowBlank:true,maxLength:500,vtext:''"></textarea>
				</td>
			</tr>	
</table>
		<!-- buttons -->
		<div class="form-btns" id="btns" style="display:block">
	
			<html:submit styleClass="btn" property="method.save" onclick="javascript:changeType1();" styleId="method.save">
				派发
			</html:submit>&nbsp;&nbsp;
			<html:button property="" styleClass="btn" onclick="selectRes()">驳回</html:button>
			&nbsp;&nbsp;
			<html:button property="" styleClass="btn" onclick="window.history.back(-1);">取消</html:button>
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