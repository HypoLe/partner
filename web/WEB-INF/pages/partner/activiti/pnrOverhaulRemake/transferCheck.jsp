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

<%@ include file="/WEB-INF/pages/partner/activiti/pnrTransferPrecheck/transfer_basis.jsp"%>
<%@ include file="/WEB-INF/pages/partner/activiti/common/transfer_do_msg.jsp"%>

<div style="TEXT-ALIGN: center; height:20"></div>
 <!-- 工单基本信息end --> 

	<html:form action="/pnrTransferPrecheck.do?method=goTransferCheck" styleId="theform" >	
	
		<input type="hidden" name="auditor" value="${userId}"/>
        <input type="hidden" name="taskId" value="${taskId}"/>
        
        <input type="hidden" name="theme" value="${pnrTransferOffice.theme}" />
        <input type="hidden" name="themeId" value="${pnrTransferOffice.id}" />
		 <!-- 工单基本信息 --> 
<table id="sheet" class="formTable" >
		<tr>
			
		  
		  <td class="label">审核状态*</td>
		  <td class="content" colspan="3">
		    <input type="radio" name="handleState" value="handle"  checked=”checked”/>通过		  
		    <input type="radio" name="handleState" value="rollback"/>不通过
		  </td>	
 			
		</tr>
			
		<tr>
 			<td class="label">
				审批意见*
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
 switcher = new detailSwitcher();
  switcher.init({
	container:'history',
  	handleEl:'div.history-item-title'
  });
</script> 