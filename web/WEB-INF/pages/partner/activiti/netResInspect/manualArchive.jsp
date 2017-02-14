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
  	 if(confirm("确定手动归档吗？")==true){
		 	return true;
	   }else{
	   		return false;
	   }
		
  }
</script>


<%@ include file="/WEB-INF/pages/partner/activiti/roomDemolition/transfer_basis.jsp"%>
<%@ include file="/WEB-INF/pages/partner/activiti/roomDemolition/showAapprove_basis.jsp"%>
<%@ include file="/WEB-INF/pages/partner/activiti/roomDemolition/showAapproveBack_basis.jsp"%>



<html:form action="/roomDemolition.do?method=doOneWorkOrderArchiving" styleId="theform">

	        <input type="hidden" id="marker" name="marker" value="manualArchive" /><!-- 流程标识 marker和linkName隐藏域的值应该保持一致-->	
            <input type="hidden" name="taskId" value="${taskId}">
            <input type="hidden" name="processInstanceId" value="${processInstanceId}">
            <input type="hidden" name="userId" value="${userId}">
            <input type="hidden" name="title" value="${roomDemolition.theme}">
            <input type="hidden" name="titleId" value="${roomDemolition.id}">
            <input type="hidden" name="step" value="7">
            <input type="hidden" name="linkName" value="manualArchive">	
            
            <input type="hidden" id="condition" name="condition" value="${condition}" />
            <input type="hidden" id="flag" name="flag" value="manualview" />
            
		<!-- buttons -->
		<div class="form-btns" id="btns" style="display:block">
	
			<html:submit styleClass="btn" property="method.save" onclick="return changeType1();" styleId="method.save">
				归档
			</html:submit>&nbsp;&nbsp;
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