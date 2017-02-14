<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript" src="${app}/scripts/widgets/TabPanel.js"></script>
<script type="text/javascript" src="${app}/scripts/Sheet.js"></script>



<c:choose>
	<c:when test="${netResInspect.taskDefKey eq 'siteCheck'}">
		<%@ include file="/WEB-INF/pages/partner/activiti/netResInspect/siteCheck_basis.jsp"%>
	</c:when>
	<c:otherwise>
		<%@ include file="/WEB-INF/pages/partner/activiti/netResInspect/lineProcess_basis.jsp"%>
	</c:otherwise> 
</c:choose>


<%@ include file="/common/footer_eoms.jsp"%>
<script type="text/javascript">
 switcher = new detailSwitcher();
  switcher.init({
	container:'history',
  	handleEl:'div.history-item-title'
  });
</script>  
