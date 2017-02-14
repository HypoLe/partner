<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<script type="text/javascript" src="${app}/scripts/widgets/TabPanel.js"></script>
<script type="text/javascript" src="${app}/scripts/Sheet.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<%@ include file="/WEB-INF/pages/partner/activiti/netResInspect/lineProcess_basis.jsp"%>
<script type="text/javascript">
</script>
<br/>
<html:form action="/netResInspect.do?method=worOrderProcessing" styleId="theform" >
<input type="hidden" id="processInstanceId" name="processInstanceId" value="${processInstanceId}" /><!-- 流程id -->
<input type="hidden" id="taskId" name="taskId" value="${taskId}" /><!-- 任务id -->
<input type="hidden" id="specialty" name="specialty" value="${netResInspect.specialty}" /><!-- 专业 -->
<table id="sheet" class="formTable" style="width:100%">
	<caption>工单处理</caption>
	<tr>
	  <td class="label" style="width:3%">是否处理完成*</td>
	  <td class="content" style="width:20%">
	  	 <select id="isFinish" name="isFinish">
	  	 	<option value='1'>是</option>
	  	 	<option value='0'>否</option>
	  	 </select>
	  </td>
	</tr>

	<tr>
	  <td class="label" style="width:3%">描述</td>
	  <td colspan="5" style="width:20%">
	  	<input type="text" class="text max" id="handleDescribe" name="handleDescribe"/>
	  </td>
	</tr>
</table>
<br/>
		<!-- buttons -->
		<div class="form-btns" id="btns" style="display:block">
	
			<html:submit styleClass="btn" property="method.save" styleId="method.save" >
				审批
			</html:submit>
		</div>
	</html:form>
<!--

//-->
</script>
<%@ include file="/common/footer_eoms.jsp"%>
<script type="text/javascript">
 approveBackSwitcher = new detailSwitcher();
  approveBackSwitcher.init({
	container:'approveBackHistory',
  	handleEl:'div.history-item-title-back'
  });
  
  
</script>