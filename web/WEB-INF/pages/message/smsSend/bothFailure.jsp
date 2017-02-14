<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms.jsp"%>

<div class="failurePage">
	<h1 class="header">${eoms:a2u('发生错误')}</h1>	  
	<div class="content">
	${eoms:a2u('短信、语音均发送失败，请确认网关、网络或配置是否正常。')}<br/>
	</div>
</div>

<%@ include file="/common/footer_eoms.jsp"%>

