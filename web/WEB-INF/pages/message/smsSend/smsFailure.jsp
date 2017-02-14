<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms.jsp"%>

<div class="failurePage">
	<h1 class="header">${eoms:a2u('发生错误')}</h1>	  
	<div class="content">
	${eoms:a2u('语音发送成功，短信发送失败，请确认网关、网络或配置等是否正常。')}<br/>
	</div>
</div>

<%@ include file="/common/footer_eoms.jsp"%>

