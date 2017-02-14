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
<tr>
<td>
<c:if test="${resultSize > 0}">
<a href="${app}/partner/feeInfo/pnrFeeInfoMains.do?method=getUnAuditList" target="pnrFrame" >
<img align=right src="${app}/partner/pnrIndex/pnrIndexImg/more.jpg" alt="更多" />
</a>
</c:if>
</td>
</tr>
<tr>
<td>

	<display:table name="unAuditList" cellspacing="0" cellpadding="0"
		id="unAuditList" pagesize="${pageSize}" class="table" 
		export="false"
		requestURI="${app}/partner/baseinfo/index.do?method=feeUnAuditList"
		sort="list" partialList="true" size="resultSize">
	<display:column sortable="true"	headerClass="sortable" title="协议名称" >
			<eoms:id2nameDB id="${unAuditList.feeId}" beanId="pnrFeeInfoMainDao" />
	</display:column>
	<display:column sortable="true"	headerClass="sortable" title="审核类型" >
		<c:if test="${unAuditList.auditType=='feeInfo'}">
			计划审核
	</c:if>
	<c:if test="${unAuditList.auditType=='feePay'}">
			付费审核
	</c:if>
	</display:column>
	<display:column property="createTime" sortable="true"  format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" title="提交时间" />

	<display:column sortable="true"	headerClass="sortable" title="提交人" >
			<eoms:id2nameDB id="${unAuditList.operateId}" beanId="tawSystemUserDao" />
	</display:column>

	<display:column title="审核">
	<c:if test="${unAuditList.auditType=='feeInfo'}">
			<a href="javascript:var feeId = '${unAuditList.feeId }';
								var id = '${unAuditList.id }';
		                        var url=eoms.appPath+'/partner/feeInfo/pnrFeeInfoMains.do?method=audit&feeId='+feeId+'&id='+id;
		                        void(window.open(url));
		                        //location.href=url"> 
				<img src="${app}/images/icons/edit.gif" /> </a>
	</c:if>
	<c:if test="${unAuditList.auditType=='feePay'}">
			<a href="javascript:var planId = '${unAuditList.planId }';
								var id = '${unAuditList.id }';
		                        var url=eoms.appPath+'/partner/feeInfo/pnrFeeInfoPays.do?method=planPay&auditId='+id+'&planId='+planId;
		                        void(window.open(url));
		                        //location.href=url"> 
				<img src="${app}/images/icons/edit.gif" /> </a>
	</c:if>
		</display:column>
		<display:setProperty name="paging.banner.item_name" value="partnerFeePlan" />
		<display:setProperty name="paging.banner.items_name" value="partnerFeePlans" />
	</display:table>
	</td>
	</tr>
	</table>
</div>
</body>
</html>