<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<script type="text/javascript">
			 var contractNO = '${contractNO}';
	    	 var contractName = '${contractName}';
	    	 var contractId = '${feeInfoId}';
	         window.opener.document.all.compactNo.value=contractNO;
	         window.opener.document.all.compactName.value=contractName;
	         window.opener.document.all.contentId.value=contractId;
	         window.opener.setCompactNostr(contractNO);
	         self.close(); 
</script>

<%@ include file="/common/footer_eoms.jsp"%>