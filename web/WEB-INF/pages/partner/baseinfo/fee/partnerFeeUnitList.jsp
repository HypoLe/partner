<%@page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<c:set var="buttons">
	 	  <br/>
	 <a href='partnerFeeUnits.do?method=add'><fmt:message key="button.add"/></a>	
</c:set>

<fmt:bundle basename="com/boco/eoms/parter/baseinfo/config/applicationResource-partner-fee">

<content tag="heading">
	<b>付费单位管理</b>
</content>

	<display:table name="partnerFeeUnitList" cellspacing="0" cellpadding="0"
		id="partnerFeeUnitList" pagesize="${pageSize}" class="table partnerFeeUnitList"
		export="false"
		requestURI="${app}/fee/partnerFeeUnits.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column property="name" sortable="true"
			headerClass="sortable" titleKey="partnerFeeUnit.name" href="${app}/fee/partnerFeeUnits.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column property="createDate" sortable="true"  format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="partnerFeeUnit.createDate"  paramId="id" paramProperty="id"/>

	<display:column sortable="true" headerClass="sortable" titleKey="partnerFeeUnit.createUser">
		<eoms:id2nameDB id="${partnerFeeUnitList.createUser}" beanId="tawSystemUserDao" />
	</display:column>

	<display:column  sortable="true" headerClass="sortable" titleKey="partnerFeeUnit.createDept" >
			<eoms:id2nameDB id="${partnerFeeUnitList.createDept}" beanId="tawSystemDeptDao" />
	</display:column>

		<display:setProperty name="paging.banner.item_name" value="partnerFeeUnit" />
		<display:setProperty name="paging.banner.items_name" value="partnerFeeUnits" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>
