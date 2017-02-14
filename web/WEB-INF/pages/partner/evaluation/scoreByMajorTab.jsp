<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page import="java.util.List,com.boco.eoms.evaluation.model.*"%>

<script type="text/javascript" src= "${app}/nop3/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src= "${app}/nop3/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript" src="${app}/scripts/widgets/TabPanel.js"></script>

<span style="font-size:12px;color:#083772;font-weight:bold;line-height:150%;">	
     ${evaluationEntity.year}<c:if test="${evaluationEntity.month!='0'}">-${evaluationEntity.month}</c:if>-<eoms:id2nameDB id="${evaluationEntity.evaluationTarget}" beanId="partnerDeptDao" /> 	
     <br/>	考核专业：<eoms:id2nameDB id="${zydictid}" beanId="ItawSystemDictTypeDao" /> 		
</span>
<br/>
<div id="info-page" style="width:100%;height:630px;overflow-x:hidden;overflow-y:hidden;">
</div>

<script type="text/javascript">
   var items=[];
</script>
<c:forEach items="${xmzyscorestatusList}" var="xmzyscorestatus" varStatus="status">
  <script type="text/javascript">
    items.push({
		          text : "考核项目：${xmzyscorestatus.xm}",
				  url : "${app}/partner/evaluation/evaluationEntity.do?method=gotoScorePage&id=${xmzyscorestatus.zyentityid}" ,
				  isIframe : true 
		       });	
  </script>
</c:forEach>
<script type="text/javascript">
  var deptLevel=${deptLevel} ;
  if(deptLevel<3){//1:省级；2：地市；3：区县
      items.push({
		          text : "下级机构考核情况",
				  url : "${app}/partner/evaluation/evaluStatAndQur.do?method=subDeptEvaluStatistics&evaluationEntityId=${evaluationEntity.id}" , 
				  isIframe : true
		       });	
  }
</script>

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