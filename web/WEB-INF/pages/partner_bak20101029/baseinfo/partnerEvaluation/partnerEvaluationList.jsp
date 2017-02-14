<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<c:set var="buttons">
		  <br/>
	<input type="button" class="btn" value="<fmt:message key="button.add" />"  
		onclick="javascript:
				var url=eoms.appPath+'/partner/baseinfo/partnerEvaluations.do?method=add';
				location.href=url"/>
</c:set>

<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">

<content tag="heading">
	<fmt:message key="partnerEvaluation.list.heading" />
</content>

	<display:table name="partnerEvaluationList" cellspacing="0" cellpadding="0"
		id="partnerEvaluationList" pagesize="${pageSize}" class="table partnerEvaluationList"
		export="false"
		requestURI="${app}/partner/baseinfo/partnerEvaluations.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column property="partnerId" sortable="true"
			headerClass="sortable" titleKey="partnerEvaluation.partnerId" href="${app}/partner/baseinfo/partnerEvaluations.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="contractId" sortable="true"
			headerClass="sortable" titleKey="partnerEvaluation.contractId" />

	<display:column property="feePlanId" sortable="true"
			headerClass="sortable" titleKey="partnerEvaluation.feePlanId" />

	<display:column property="evaTime" sortable="true"
			headerClass="sortable" titleKey="partnerEvaluation.evaTime" format="{0,date,yyyy-MM-dd HH:mm:ss}"/>

	<display:column property="evaResult" sortable="true"
			headerClass="sortable" titleKey="partnerEvaluation.evaResult" />

	<display:column property="evaUser" sortable="true"
			headerClass="sortable" titleKey="partnerEvaluation.evaUser" />

	<display:column property="evaDept" sortable="true"
			headerClass="sortable" titleKey="partnerEvaluation.evaDept" />
			
	<display:column property="unContent" sortable="true"
			headerClass="sortable" titleKey="partnerEvaluation.unContent" />

	<display:column property="propose" sortable="true"
			headerClass="sortable" titleKey="partnerEvaluation.propose" />


	<display:column title="查看" headerClass="imageColumn" paramId="id" paramProperty="id">
				<a href='${app}/partner/baseinfo/partnerEvaluations.do?method=detail&id=${partnerEvaluationList.id }'  target='_blank'>
					<img src="${app}/images/icons/search.gif" /> </a>
	</display:column>
		<display:setProperty name="paging.banner.item_name" value="partnerEvaluation" />
		<display:setProperty name="paging.banner.items_name" value="partnerEvaluations" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>