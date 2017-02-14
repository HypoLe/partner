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
  Ext.onReady(function(){
   v = new eoms.form.Validation({form:'theform'});
   
  });


  function changeType1()
  {
		
  }
  function selectRes(){
		var url = '${app}/activiti/pnrHiddendangerReported/pnrHiddendangerReported.do?method=openRejectView';
		var _sHeight = 180;
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
<%@ include file="/WEB-INF/pages/partner/activiti/pnrHiddendangerReported/transfer_do_msg.jsp"%>

<div style="height:10"></div>

<html:form action="/pnrHiddendangerReported.do?method=doOneWorkOrderArchiving" styleId="theform" >
			<input type="hidden" id="marker" name="marker" value="orderAudit" /><!-- 流程标识 marker和linkName隐藏域的值应该保持一致-->	
            <input type="hidden" id="returnPerson" name="returnPerson" value="${pnrTransferOffice.createWork}"><!-- 驳回人 -->
            <input type="hidden" id="title" name="title" value="${pnrTransferOffice.theme}"><!-- 工单主题 -->
            <input type="hidden" id="titleId" name="titleId" value="${pnrTransferOffice.id}"><!-- 流程ID -->

			<input type="hidden" id="isSpotCheck" name="isSpotCheck" value="${pnrTransferOffice.state}" /><!-- 是否抽查标识 -->
            <input type="hidden" id="taskId" name="taskId" value="${taskId}"><!-- 任务ID -->
            <input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}"><!-- 流程ID -->
            <input type="hidden" id="step" name="step" value="15"><!-- 附件用 -->
            <input type="hidden" id="userId" name="userId" value="${userId}">
            <input type="hidden" id="linkName" name="linkName" value="orderAudit">
            <input type="hidden" id="orderAuditHandleState" name="orderAuditHandleState" value="handle" /><!-- 归档标识 -->
            
            <input type="hidden" id="condition" name="condition" value="${condition}" />
            
	<!-- 审批结果 -->
    <fieldset>
    		<legend>
			 处理信息
			</legend>
			<table id="sheet" class="formTable">
				<tr>
				<td class="label">是否我方资源*</td>
				<td class="content" >
						是（假数据）
				</td>
				<td class="label">是否现场施工作业*</td>
				<td class="content" >
						是（假数据）
				</td>
			</tr>
			<tr>
				<td class="label">是否隐患*</td>
				<td class="content" >
					是（假数据）
				</td>
				<td class="label">是否线路隐患*</td>
				<td class="content" >
					是（假数据）
				</td>
	 		
	 			
			</tr>
			<tr>
				<td class="label">责任人</td>
				<td class="content" >
					（假数据）
				</td>
				<td class="label">是否处理完成*</td>
				<td class="content" >
					是（假数据）
				</td>
	 		
	 			
			</tr>
			
			<!-- 没走预检预修、抢修工单的巡检众筹工单显示 待判断-->
				<tr>
					<td class="label">处理描述</td>
						<td class="content" colspan="3">
							没走预检预修、抢修工单的巡检众筹工单显示 页面待判断
						</td>
				</tr>
			
			<!-- 走预检预修、抢修工单的巡检众筹工单显示 待判断-->
				<tr>
					<td class="label">关联工单号</td>
					<td class="content" >
						走预检预修、抢修工单的巡检众筹工单显示 页面待判断
					</td>
					<td class="label">关联流程</td>
					<td class="content" >
						走预检预修、抢修工单的巡检众筹工单显示 页面待判断
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
	</fieldset>
<!-- buttons -->
		<div class="form-btns" id="btns" style="display:block">	
			<html:submit styleClass="btn" property="method.save" onclick="javascript:changeType1();" styleId="method.save">
				归档
			</html:submit>&nbsp;&nbsp;
			<html:button styleClass="btn" property="" onclick="location.href='javascript:history.go(-1);'" >
				返回
			</html:button>&nbsp;&nbsp;
		</div>
	</html:form>

<%@ include file="/common/footer_eoms.jsp"%>
