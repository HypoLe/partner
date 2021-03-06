<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/extlibs.jsp"%>
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
<div id="main">
<table  width="100%">
<!-- 
<tr>
<td>
<c:if test="${resultSize > 0}">
<a href="${app}/partner/agreement/pnrAgreementMains.do?method=getUnAuditList" target="pnrFrame" >
<img align=right src="${app}/partner/pnrIndex/pnrIndexImg/more.jpg" alt="更多" />
</a>
</c:if>
</td>
</tr>
-->
<c:if test="${resultSize==0}">
		<tr>
			<td  style="text-align:center;font-weight:bold;color:#006699;" >暂无记录</td>
		</tr>	
</c:if>
<c:if test="${resultSize!=0}">
<tr>
<td>
	<display:table name="unAuditList" cellspacing="0" cellpadding="0"
		id="unAuditList" pagesize="${pageSize}" class="table unAuditList"
		export="false"
		requestURI="${app}/partner/baseinfo/index.do?method=unAuditList"
		sort="list" partialList="true" size="resultSize">
		
	
	<display:column sortable="true"	headerClass="sortable" title="名称" >
			<a href="${app}${unAuditList.url}" target="_blank" >	
				${unAuditList.name}
			</a>
	</display:column>
	
	<display:column sortable="true"	headerClass="sortable" title="类型" >
			${unAuditList.module}
	</display:column>
	
	<display:column title="审核">
		<a href="javascript:
							var urlStr = '${unAuditList.url}';
	                        var url=eoms.appPath+urlStr;
	                        void(window.open(url));
	                        //location.href=url"> 
			<img src="${app}/images/icons/edit.gif" /> </a>
	</display:column>
	</display:table>
	</td>
	</tr>
	</c:if>
	</table>
</div>
</body>
</html>