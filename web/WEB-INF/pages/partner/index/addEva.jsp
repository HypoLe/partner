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

<fmt:bundle basename="com/boco/eoms/partner/agreement/config/applicationResources-partner-agreement">
<c:if test="${resultSize!='0'}">
	<div id="main">
		<table  width="100%">
			<tr>
				<td>
					<display:table name="pnrAgreementMainList" cellspacing="0" cellpadding="0"
						id="pnrAgreementMainList" pagesize="${pageSize}" class="table"
						export="false"
						requestURI="${app}/partner/baseinfo/index.do?method=uploadAgreement"
						sort="list" partialList="true" size="resultSize">
				
					<display:column property="agreementNO" sortable="true"
							headerClass="sortable" titleKey="pnrAgreementMain.agreementNO"  paramId="id" paramProperty="id"/>
				
					<display:column property="agreementName" sortable="true"
							headerClass="sortable" titleKey="pnrAgreementMain.agreementName"  paramId="id" paramProperty="id"/>
				
					<display:column title="查看" headerClass="imageColumn">
						<a href="${app}/partner/agreement/pnrAgreementMains.do?method=detail&id=${pnrAgreementMainList.id }" target="_blank">
							<img src="${app}/images/icons/search.gif" /> </a>
					</display:column>
					<display:column title="关联考核" headerClass="imageColumn">
						<a href="${app}/eva/evaTemplateTemps.do?method=newTemplateTemp&agreementId=${pnrAgreementMainList.id}&agrwor=agreement&partner=${pnrAgreementMainList.partyBId}&evaDeptId=${pnrAgreementMainList.partyAId}&confirmUser=${pnrAgreementMainList.partyBUser}" target="_blank">
							关联 </a>
					</display:column>
					</display:table>
				</td>
			</tr>
		</table>
	</div>
</c:if>
	<c:if test="${resultSize=='0'}">
		<table  class="formTable">
			<tr>
				<td style="text-align:center;font-weight:bold;color:#006699;background:#CAE8EA;border: 1px solid #98C0F4;" >暂无记录</td>
			</tr>
		</table>
	</c:if>
</fmt:bundle>
