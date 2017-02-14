<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript" src="${app}/scripts/widgets/TabPanel.js"></script>
<script type="text/javascript" src="${app}/scripts/Sheet.js"></script>

<script type="text/javascript">
var roleTree;
var v;
  Ext.onReady(function(){
   v = new eoms.form.Validation({form:'theform'});
   });
   
   
  function changeType1()
  {
		//$('phaseId').value = "DealTask";
   		//$('operateType').value = "0";
   }
   
</script>

<%@ include file="/WEB-INF/pages/partner/activiti/roomDemolition/transfer_basis.jsp"%>
<%@ include file="/WEB-INF/pages/partner/activiti/roomDemolition/showAapprove_basis.jsp"%>
<%@ include file="/WEB-INF/pages/partner/activiti/roomDemolition/showAapproveBack_basis.jsp"%>

<div style="TEXT-ALIGN: center; height:20"></div>
 <!-- 工单基本信息end --> 

	<html:form action="/roomDemolition.do?method=secondAudit" styleId="theform" >	
				<input type="hidden" name="processInstanceId" value="${processInstanceId}">
		        <input type="hidden" name="taskId" value="${taskId}">
		        <input type="hidden" id="returnPerson" name="returnPerson" value="${roomDemolition.cityManageCharge}"><!-- 驳回人 -->
		        <input type="hidden" name="userId" value="${userId}">
		        <input type="hidden" name="theme" value="${pnrTransferOffice.theme}">
		        <input type="hidden" name="themeId" value="${pnrTransferOffice.id}">
		       <!--  <input type="hidden" name="daiweiAuditHandleState" value="handle">  -->
		        <input type="hidden" name="step" value="6">
		        <input type="hidden" name="manualArchiveCheck" value="${roomDemolition.provinceManageCharge}">
			    <input type="hidden" name="nextPerson" value="${roomDemolition.provinceManageCharge}">
		        <input type="hidden" id="handleId" name="handleId" value="${handleId}" />
		        <input type="hidden" name="linkName" value="daiweiAudit">
		        
		        <input type="hidden" id="condition" name="condition" value="${condition}" />
		        
		 <!-- 工单基本信息 --> 
<table id="sheet" class="formTable" >
		<tr>
			
		  
		  <td class="label">审核状态*</td>
		  <td class="content" colspan="3">
		    <input type="radio" name="daiweiAuditHandleState" value="handle"  checked=”checked”/>通过
		    <input type="radio" name="daiweiAuditHandleState" value="rollback"/>不通过
		  </td>	
 			
		</tr>
			
		<tr>
 			<td class="label">
				审核意见*
			</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="mainRemark" 
					id="mainRemark" alt="allowBlank:false,maxLength:2000,vtext:'请填入审批意见，最多输入 2000 字符'"></textarea>
			</td>
		</tr>
</table>

	    <p/>	
		<!-- buttons -->
		<div class="form-btns" id="btns" style="display:block">
	
			<html:submit styleClass="btn" property="method.save" onclick="javascript:changeType1();" styleId="method.save">
				提交
			</html:submit>
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