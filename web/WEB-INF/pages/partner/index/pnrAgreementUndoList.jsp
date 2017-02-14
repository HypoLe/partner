<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/extlibs.jsp"%>

<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>


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

<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/pnrAgreementMains.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<fmt:bundle basename="com/boco/eoms/partner/agreement/config/applicationResources-partner-agreement">

<content tag="heading">
	<div class="header center">
	<c:if test="${state == ''}">
		服务协议管理列表
	</c:if>
	<c:if test="${state == '2'}">
		有效服务协议管理列表
	</c:if>
	<c:if test="${state == '1'}">
		驳回服务协议管理列表
	</c:if>
	<c:if test="${state == '0'}">
		待审核服务协议管理列表
	</c:if>
	<c:if test="${state == '6'}">
		待总结服务协议管理列表
	</c:if>
	</div>
</content>

<div id="main">
<table  width="100%">
<tr>
<td>
<c:if test="${resultSize > 0}">
<c:if test="${state=='upload'}">
<a href="${app}/partner/agreement/pnrAgreementMains.do?state=upload" target="pnrFrame" >
<img align=right src="${app}/partner/pnrIndex/pnrIndexImg/more.jpg" alt="更多" />
</a>
</c:if>
<c:if test="${state=='toClose'}">
<a href="${app}/partner/agreement/pnrAgreementMains.do?state=toClose" target="pnrFrame" >
<img align=right src="${app}/partner/pnrIndex/pnrIndexImg/more.jpg" alt="更多" />
</a>
</c:if>
</c:if>
</td>
</tr>
<tr>
<td>
<c:if test="${resultSize!='0'}">
	<display:table name="pnrAgreementMainList" cellspacing="0" cellpadding="0"
		id="pnrAgreementMainList" pagesize="${pageSize}" class="table"
		export="false"
		requestURI="${app}/partner/baseinfo/index.do?method=uploadAgreement"
		sort="list" partialList="true" size="resultSize">

	<display:column property="agreementNO" sortable="true"
			headerClass="sortable" titleKey="pnrAgreementMain.agreementNO"  paramId="id" paramProperty="id"/>

	<display:column property="agreementName" sortable="true"
			headerClass="sortable" titleKey="pnrAgreementMain.agreementName"  paramId="id" paramProperty="id"/>

	<display:column sortable="true" headerClass="sortable" titleKey="pnrAgreementMain.state">
		<c:if test="${pnrAgreementMainList.state == '0'}">新建待确认</c:if>
		<c:if test="${pnrAgreementMainList.state == '1'}">新建驳回</c:if>
		<c:if test="${pnrAgreementMainList.state == '2'}">有效</c:if>
		<c:if test="${pnrAgreementMainList.state == '3'}">评分待审核</c:if>
		<c:if test="${pnrAgreementMainList.state == '4'}">综合评分</c:if>
		<c:if test="${pnrAgreementMainList.state == '5'}">评分驳回</c:if>
		<c:if test="${pnrAgreementMainList.state == '6'}">等待总结</c:if>
		<c:if test="${pnrAgreementMainList.state == '7'}">等待评分</c:if>
	</display:column>
	<display:column title="查看" headerClass="imageColumn">
		<a href="${app}/partner/agreement/pnrAgreementMains.do?method=detail&id=${pnrAgreementMainList.id }" target="_blank">
			<img src="${app}/images/icons/search.gif" /> </a>
	</display:column>
	<c:if test="${pnrAgreementMainList.state == '1'}">
	<display:column title="修改" headerClass="imageColumn">
		<a href="${app}/partner/agreement/pnrAgreementMains.do?method=edit&id=${pnrAgreementMainList.id }" target="_blank">
			<img src="${app}/images/icons/search.gif" /> </a>
	</display:column>
	</c:if>
	<c:if test="${pnrAgreementMainList.state == '6'}">
	<display:column title="总结" headerClass="imageColumn">
			<img src="${app}/images/icons/icon1.gif" onClick="javascript:var id = '${pnrAgreementMainList.id }';
		                												var url='${app}/partner/agreement/pnrAgreementMains.do?method=uploadSumUp';
		                												var win_props = 'toolbar=no,scrollBars=yes,width=800,height=450';
		                												url = url + '&id=' + id ;
		                												window.open(url,'',win_props);"/>
	</display:column>
	</c:if>
	<c:if test="${pnrAgreementMainList.state == '5'||pnrAgreementMainList.state == '7'}">
	<display:column title="评分" headerClass="imageColumn">
			<img src="${app}/images/icons/icon1.gif" onClick="javascript:var agreementId = '${pnrAgreementMainList.id }';
		                												var url='${app}/partner/agreement/pnrAgreementMains.do?method=closeAgreement';
		                												var win_props = '';
		                												url = url + '&agreementId=' + agreementId ;
		                												window.open(url,'',win_props);"/>
	</display:column>
	</td>
	</tr>
	</table>
</div>
	</c:if>
		<display:setProperty name="paging.banner.item_name" value="pnrAgreementMain" />
		<display:setProperty name="paging.banner.items_name" value="pnrAgreementMains" />
	</display:table>
	</c:if>
	<c:if test="${resultSize=='0'}">
	<p>暂无记录</p>
	</c:if>
	<c:if test="${state == ''||state == '2'||state == '5'}">
			<c:if test="${retPnr==''||retPnr==null}">
				<c:out value="${buttons}" escapeXml="false" />
			</c:if>
	</c:if>
</fmt:bundle>
