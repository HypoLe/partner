<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms.jsp"%>

<div class="failurePage">
	<h1 class="header">${eoms:a2u('发生错误')}</h1>	  
	<div class="content">
	${eoms:a2u('彩信发送失败，请确认彩信网关及网络是否正常。')}<br/>
	</div>
</div>

<%@ include file="/common/footer_eoms.jsp"%>

