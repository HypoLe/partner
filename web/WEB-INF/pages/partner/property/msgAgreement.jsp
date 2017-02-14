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
					<a href="${app}/partner/property/remind.do?method=agreementRemindMsgList" target="pnrFrame" >
						<img align=right src="${app}/partner/pnrIndex/pnrIndexImg/more.jpg" alt="更多" />
					</a>
				</c:if>
			</td>
		</tr>
		<tr>
			<td>
				<display:table name="agreementList" cellspacing="0" cellpadding="0"	id="agreementList" pagesize="${pageSize}" class="table agreementList"
					export="false"	requestURI="${app}/partner/property/remind.do?method=agreementRemindMsgList"
					sort="list" partialList="true" size="resultSize">
					<display:column sortable="true"	headerClass="sortable" title="提醒内容" >
							${agreementList.content}
					</display:column>
					<display:column sortable="true"	headerClass="sortable" title="批阅" >
						<a href="${app}/partner/property/remind.do?method=readAgeementMsg&id=${agreementList.id}">已阅</a>
					</display:column>
					<display:column sortable="false" headerClass="sortable" title="详情" media="html">
						<a href="${app}/partner/property/remind.do?method=agreementRemindMsgDetail&id=${agreementList.id}" target="blank" shape="rect">
							<img src="${app}/images/icons/table.gif">
						</a>
					</display:column>
				</display:table>
			</td>
		</tr>
	</table>
</div>
</body>
</html>