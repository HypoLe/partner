<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script language="javaScript" type="text/javascript"
	src="${app}/scripts/module/partner/ajax.js"></script>
<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">

	<display:table name="tawPartnerOilList" cellspacing="0" cellpadding="0"
		id="tawPartnerOilList" pagesize="${pageSize}" class="table tawPartnerOilList"
		export="false" decorator="com.boco.eoms.partner.baseinfo.util.PartnerOilDecorator"
		requestURI="${app}/partner/baseinfo/pnrStats.do?method=getOilList"
		sort="list" partialList="true" size="resultSize">

	<display:column property="oil_number" sortable="true"
			headerClass="sortable" titleKey="tawPartnerOil.oil_number" href="${app}/partner/baseinfo/tawPartnerOils.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column sortable="true"
			headerClass="sortable" titleKey="tawPartnerOil.factory" >
			<html:link
					href="${app}/partner/baseinfo/tawPartnerOils.do?method=edit"
					paramId="id" paramProperty="id" paramName="tawPartnerOilList">
					<eoms:id2nameDB id="${tawPartnerOilList.factory}" beanId="ItawSystemDictTypeDao" />
				</html:link>
			</display:column>

	<display:column property="dept_id" sortable="true"
			headerClass="sortable" titleKey="tawPartnerOil.dept_id" href="${app}/partner/baseinfo/tawPartnerOils.do?method=edit" paramId="id" paramProperty="id"/>

	<display:column  sortable="true"
			headerClass="sortable" titleKey="tawPartnerOil.type" >
			<html:link
					href="${app}/partner/baseinfo/tawPartnerOils.do?method=edit"
					paramId="id" paramProperty="id" paramName="tawPartnerOilList">
					<eoms:id2nameDB id="${tawPartnerOilList.type}" beanId="ItawSystemDictTypeDao" />
				</html:link>
			</display:column>

	<display:column sortable="true"
			headerClass="sortable" titleKey="tawPartnerOil.power" >
			<html:link
					href="${app}/partner/baseinfo/tawPartnerOils.do?method=edit"
					paramId="id" paramProperty="id" paramName="tawPartnerOilList">
					<eoms:id2nameDB id="${tawPartnerOilList.power}" beanId="ItawSystemDictTypeDao" />
				</html:link>
			</display:column>

	<display:column sortable="true"
			headerClass="sortable" titleKey="tawPartnerOil.kind" >
			<html:link
					href="${app}/partner/baseinfo/tawPartnerOils.do?method=edit"
					paramId="id" paramProperty="id" paramName="tawPartnerOilList">
					<eoms:id2nameDB id="${tawPartnerOilList.kind}" beanId="ItawSystemDictTypeDao" />
				</html:link>
			</display:column>

	<display:column  sortable="true"
			headerClass="sortable" titleKey="tawPartnerOil.state" >
			<html:link
					href="${app}/partner/baseinfo/tawPartnerOils.do?method=edit"
					paramId="id" paramProperty="id" paramName="tawPartnerOilList">
					<eoms:id2nameDB id="${tawPartnerOilList.state}" beanId="ItawSystemDictTypeDao" />
				</html:link>
			</display:column>

	<display:column property="storage" sortable="true"
			headerClass="sortable" titleKey="tawPartnerOil.storage" href="${app}/partner/baseinfo/tawPartnerOils.do?method=edit" paramId="id" paramProperty="id"/>

		<display:setProperty name="paging.banner.item_name" value="tawPartnerOil" />
		<display:setProperty name="paging.banner.items_name" value="tawPartnerOils" />
	</display:table>
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>
