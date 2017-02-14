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
		var url = '${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=openRejectView';
		var _sHeight = 180;
	    var _sWidth = 820;
	    var sTop=(window.screen.availHeight-_sHeight)/2;
	    var sLeft=(window.screen.availWidth-_sWidth)/2;
		var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
		window.showModalDialog(url,window,sFeatures);
	}
  
  function refresh(){//此刷新函数被弹出的子模态窗口调用。
  		var condition=document.getElementById("condition").value;
	    window.location.href = "${app}/activiti/pnrTransferPrecheck/pnrTransferNewPrecheck.do?method=listBacklog"+condition;
  }
</script>

<%@ include file="/WEB-INF/pages/partner/activiti/pnrOltBbuRoom/transfer_basis.jsp"%>
<%@ include file="/WEB-INF/pages/partner/activiti/pnrOltBbuRoom/showAapprove_basis.jsp"%>
<%@ include file="/WEB-INF/pages/partner/activiti/pnrOltBbuRoom/transfer_do_msg.jsp"%>
<%@ include file="/WEB-INF/pages/partner/activiti/pnrOltBbuRoom/transfer_do_orderAudit.jsp"%>


<div style="height:10"></div>

<html:form action="/pnrOltBbuRoom.do?method=doOneWorkOrderArchiving" styleId="theform" >
			<input type="hidden" id="marker" name="marker" value="cityManagefile" /><!-- 流程标识 marker和linkName隐藏域的值应该保持一致-->	
            <input type="hidden" id="returnPerson" name="returnPerson" value="${pnrTransferOffice.cityLineCharge}"><!-- 驳回人 -->
            <input type="hidden" id="title" name="title" value="${pnrTransferOffice.theme}"><!-- 工单主题 -->
            <input type="hidden" id="titleId" name="titleId" value="${pnrTransferOffice.id}"><!-- 流程ID -->

			<input type="hidden" id="isSpotCheck" name="isSpotCheck" value="${pnrTransferOffice.state}" /><!-- 是否抽查标识 -->
            <input type="hidden" id="taskId" name="taskId" value="${taskId}"><!-- 任务ID -->
            <input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}"><!-- 流程ID -->
            <input type="hidden" id="step" name="step" value="11"><!-- 附件用 -->
            <input type="hidden" id="userId" name="userId" value="${userId}">
            <input type="hidden" id="linkName" name="linkName" value="cityManagefile">
          
            
            <input type="hidden" id="condition" name="condition" value="${condition}" />
            
		<div class="form-btns" id="btns" style="display:block">	
			<html:submit styleClass="btn" property="method.save" onclick="javascript:changeType1();" styleId="method.save">
								归档
			</html:submit>&nbsp;&nbsp;
		</div>
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>
<script type="text/javascript">
 approveSwitcher= new detailSwitcher();
 	 approveSwitcher.init({
	container:'approveHistory',
  	handleEl:'div.history-item-title'
  });
 </script>  
 
<script type="text/javascript">
 transferSitcher = new detailSwitcher();
  transferSitcher.init({
	container:'transferHistory',
  	handleEl:'div.history-item-title'
  });
</script> 

<script type="text/javascript">
 transferSitcher = new detailSwitcher();
  transferSitcher.init({
	container:'orderAuditHistory',
  	handleEl:'div.history-item-title'
  });
</script> 