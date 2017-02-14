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


<%@ include file="/WEB-INF/pages/partner/activiti/roomDemolition/transfer_basis.jsp"%>
<%@ include file="/WEB-INF/pages/partner/activiti/roomDemolition/showAapprove_basis.jsp"%>
<%@ include file="/WEB-INF/pages/partner/activiti/roomDemolition/showAapproveBack_basis.jsp"%>


<div style="height:20"></div>
<h6><font color="red">该环节请在手机端操作！</font></h6><br />

<html:form action="/roomDemolition.do?method=transferProgram" styleId="theform" >

	        <input type="hidden" id="marker" name="marker" value="orderAudit" /><!-- 流程标识 marker和linkName隐藏域的值应该保持一致-->	
            <input type="hidden" name="taskId" value="${taskId}">
            <input type="hidden" name="processInstanceId" value="${processInstanceId}">
            <input type="hidden" id="returnPerson" name="returnPerson" value="${roomDemolition.countryCharge}"><!-- 驳回人 -->
            <input type="hidden" name="userId" value="${userId}">
            <input type="hidden" name="title" value="${roomDemolition.theme}">
            <input type="hidden" name="titleId" value="${roomDemolition.id}">
            <input type="hidden" name="orderAuditHandleState" value="handle">
            <input type="hidden" name="step" value="5">
            <input type="hidden" name="daiweiAuditCheck" value="${roomDemolition.provinceManageCharge}">
			<input type="hidden" name="nextPerson" value="${roomDemolition.provinceManageCharge}">
            <input type="hidden" name="linkName" value="orderAudit">
            <input type="hidden" id="handleId" name="handleId" value="${handleId}" />
            
            <input type="hidden" id="condition" name="condition" value="${condition}" />
	
		<!-- buttons -->
<!-- 	 	<div class="form-btns" id="btns" style="display:block">
	
			<html:submit styleClass="btn" property="method.save" onclick="javascript:changeType1();" styleId="method.save">
				回复
			</html:submit>&nbsp;&nbsp;-->
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