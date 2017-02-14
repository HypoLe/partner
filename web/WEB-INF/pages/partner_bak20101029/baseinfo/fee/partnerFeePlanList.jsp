<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
	
<c:set var="buttons">
		  <br/>
	<a href='partnerFeePlans.do?method=add'><fmt:message key="button.add"/></a>	
</c:set>

<fmt:bundle basename="com/boco/eoms/parter/baseinfo/config/applicationResource-partner-fee">

<content tag="heading">
	<b>付款计划</b>
</content>

	<display:table name="partnerFeePlanList" cellspacing="0" cellpadding="0"
		id="partnerFeePlanList" pagesize="${pageSize}" class="table partnerFeePlanList"
		export="false"
		requestURI="${app}/fee/partnerFeePlans.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column property="name" sortable="true" 
			headerClass="sortable" titleKey="partnerFeePlan.name"  paramId="id" paramProperty="id"/>

	<display:column  sortable="true" headerClass="sortable" titleKey="partnerFeePlan.payUnit" >
		${partnerFeePlanList.payUnitName}
	</display:column>

	<display:column property="planPayDate" sortable="true"  format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="partnerFeePlan.planPayDate"  paramId="id" paramProperty="id"/>

	<display:column property="planPayFee" sortable="true"
			headerClass="sortable" title="计划付款金额(元)"/>

	<display:column sortable="true"	headerClass="sortable" title="是否已付款" >
			<eoms:dict key="dict-kmmanager" dictId="isOrNot"
				itemId="${partnerFeePlanList.payStatus}" beanId="id2nameXML" />
	</display:column>

	<display:column property="payStage" sortable="true"
			headerClass="sortable" titleKey="partnerFeePlan.payStage"  paramId="id" paramProperty="id"/>
	
	<display:column title="查看" headerClass="imageColumn">
			<a href="javascript:var id = '${partnerFeePlanList.id }';
		                        var url=eoms.appPath+'/fee/partnerFeePlans.do?method=searchDetail&flag=${flag}&id='+id;
		                        void(window.open(url));
		                        //location.href=url">
				<img src="${app}/images/icons/search.gif" /> </a>
		</display:column>
	
		<display:setProperty name="paging.banner.item_name" value="partnerFeePlan" />
		<display:setProperty name="paging.banner.items_name" value="partnerFeePlans" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>
