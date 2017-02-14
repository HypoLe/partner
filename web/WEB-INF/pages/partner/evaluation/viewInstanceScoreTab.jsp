<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" href="${app}/nop3/jquery-ui-1.8.14.custom/development-bundle/demos/demos.css">
<link rel="stylesheet" type="text/css" href="${app}/nop3/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript" src="${app}/scripts/widgets/TabPanel.js"></script>

<span style="font-size:12px;color:#083772;font-weight:bold;line-height:150%;">	
     ${evaluationEntity.year}<c:if test="${evaluationEntity.month!='0'}">-${evaluationEntity.month}</c:if>-<eoms:id2nameDB id="${evaluationEntity.evaluationTarget}" beanId="partnerDeptDao" /> 		
     <br/>考核得分:${evaluationEntity.score}
</span>
<div id="info-page" style="width:100%;overflow-x:hidden;overflow-y:hidden;">
</div>
<br/>
<c:if test="${isConfirmed and isOpenEvaluationSwitch}">
   <form id="form1" name="form1" action="${app}">
     <table class="table">
       <tr><td colspan="${fn:length(auditLinkList)}">考核发起人签字: <eoms:id2nameDB id='${receiveLink.operateuserid}' beanId='tawSystemUserDao'/></td></tr>
       <tr><td colspan="${fn:length(auditLinkList)}">代维公司确认人签字: <eoms:id2nameDB id='${ScoreConfirmLink.operateuserid}' beanId='tawSystemUserDao'/></td></tr>
       <tr>
         <c:forEach var="auditLink" items="${auditLinkList}">
           <td>${auditLink.phasename}签字：<eoms:id2nameDB id='${auditLink.operateuserid}' beanId='tawSystemUserDao'/></td>
         </c:forEach>
       </tr>
     </table>
       <input type="button" value="导出考核结果" class="button" name="save" 
		onclick="javascript:location.href='${app}/partner/evaluation/exportAndImport.do?method=exportEvaluationData&id=${evaluationEntity.id}'" />								
   </form>
</c:if>

<script type="text/javascript">
   var items=[];
</script>
<c:forEach items="${evaluationEntityList}" var="evaluationEntityTemp" varStatus="status">
  <script type="text/javascript">
    items.push({
		          text : "${evaluationEntityTemp.usedTemplateName}",
				  url : "${app}/partner/evaluation/evaluationEntity.do?method=gotoViewScorePage&id=${evaluationEntityTemp.id}" ,
				  isIframe : true 
		       });	
  </script>
</c:forEach>
<script type="text/javascript">
var jq=jQuery.noConflict();
Ext.onReady(function() {
	setTabs();
});

	function setTabs() {
		var tabConfig = {
		          items : items
	         };
	    var tabs = new eoms.TabPanel('info-page', tabConfig);		
	}  
	
</script>		
<%@ include file="/common/footer_eoms.jsp"%>	