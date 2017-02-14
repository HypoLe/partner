<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/extlibs.jsp"%>
<jsp:directive.page import="com.boco.eoms.eva.util.EvaConstants" />
<jsp:directive.page import="com.boco.eoms.base.util.StaticMethod"/>
<jsp:directive.page import="com.boco.eoms.commons.system.session.form.TawSystemSessionForm" />
  <!--[if IE]><link rel="stylesheet" type="text/css" href="${app}/styles/common/ie.css" /><![endif]-->
</head>
<body>
<style type="text/css">
span.pagebanner {
    display: none;
}
span.pagelinks {
    display: none;
}
.table{
	width: 100%;
	font-size: 12px;
	border: 0;
	cellspacing: 0;
	cellpadding: 0;
}
.listTable td, .listTable th, .table td, .table th{
			border: 0px solid #98c0f4;
			padding: 6px;
			background-color:#FFFFFF;
		}
.listTable tr.header td, .listTable thead td, .listTable thead th, .table thead th{
			background: url(images/background.gif);
			color:#006699;
			font-weight:bold;
		}
</style>

<%
int resultSize = StaticMethod.nullObject2int(request.getAttribute("resultSize"));
TawSystemSessionForm sessionform = (TawSystemSessionForm) request
.getSession().getAttribute("sessionform");
String userName = sessionform.getUsername();
String userId = sessionform.getUserid();
%>
<fmt:bundle basename="com/boco/eoms/eva/config/ApplicationResources-eva">
<div id="main">
<table  width="100%">
<tr>
<td>
<c:if test="${resultSize > 0}">
<a href="${app}/eva/evaTaskAudit.do?method=unAuditTaskList" target="pnrFrame" >
<img align=right src="${app}/partner/pnrIndex/pnrIndexImg/more.jpg" alt="更多" />
</a>
</c:if>
</td>
</tr>
<tr>
<td>
	<display:table name="taskAuditList" cellspacing="0" cellpadding="0" id="taskAuditList" pagesize="${pageSize}" class="table auditInfoList" export="false"
		requestURI="${app}/partner/baseinfo/index.do?method=evaUnAuditList" sort="list" partialList="true" size="resultSize">
		<display:column sortable="true" headerClass="sortable" title="模板名称">
		 	<a href="${app}/eva/evaTaskAudit.do?method=auditDetail&id=${taskAuditList.id}&taskId=${taskAuditList.taskId}&templateId=${taskAuditList.templateId}&time=${taskAuditList.auditTime}&timeType=${taskAuditList.auditCycle}&partner=${taskAuditList.partner}" target="_blank" >${taskAuditList.templateName}</a>
		</display:column>
		<display:column property="auditTimeStr"  sortable="false" headerClass="sortable" title="审核周期" />
		<display:column sortable="true" headerClass="sortable" title="厂商">
			<eoms:id2nameDB id="${taskAuditList.partner}" beanId="tawSystemDeptDao" />
		</display:column>
		<display:column property="totalScore"  sortable="sortable" headerClass="sortable" title="得分" />
		<display:column sortable="true" headerClass="sortable" titleKey="eva.auditInfo.status">
		 <eoms:dict key="dict-eva" dictId="auditFlag" itemId="${taskAuditList.auditResult}" beanId="id2nameXML" />
		</display:column>
				<display:column title="审核">
			<a href="javascript: var url=eoms.appPath+'/eva/evaTaskAudit.do?method=auditDetail&id=${taskAuditList.id}&taskId=${taskAuditList.taskId}&templateId=${taskAuditList.templateId}&time=${taskAuditList.auditTime}&timeType=${taskAuditList.auditCycle}&partner=${taskAuditList.partner}';
		                        void(window.open(url));
		                        //location.href=url"> 
				<img src="${app}/images/icons/edit.gif" /> </a>
		</display:column>
	</display:table>
	</td>
	</tr>
	</table>
	</div>
	</fmt:bundle>
</body>
</html>
