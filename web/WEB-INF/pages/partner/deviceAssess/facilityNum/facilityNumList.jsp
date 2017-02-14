<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<c:set var="buttons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/facilityNums.do?method=add'/>'"
		value="<fmt:message key="button.add"/>" />
</c:set>

<fmt:bundle basename="com/boco/eoms/partner/deviceAssess/config/applicationResource-partner-deviceAssess">

<content tag="heading">
	<fmt:message key="facilityNum.list.heading" />
</content>

	<display:table name="facilityNumList" cellspacing="0" cellpadding="0"
		id="facilityNumList" pagesize="${pageSize}" class="table facilityNumList"
		export="false"
		requestURI="${app}/partner/deviceAssess/facilityNums.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column sortable="true"
			headerClass="sortable" titleKey="facilityNum.factory" href="${app}/partner/deviceAssess/facilityNums.do?method=edit" paramId="id" paramProperty="id">
		<eoms:id2nameDB id="${facilityNumList.factory}"  beanId="ItawSystemDictTypeDao" />	
	</display:column>		

	<display:column property="tmsc" sortable="true"
			headerClass="sortable" titleKey="facilityNum.tmsc" />

	<display:column property="mscserver" sortable="true"
			headerClass="sortable" titleKey="facilityNum.mscserver" />

	<display:column property="mgw" sortable="true"
			headerClass="sortable" titleKey="facilityNum.mgw" />

	<display:column property="hlrvlr" sortable="true"
			headerClass="sortable" titleKey="facilityNum.hlrvlr" />

	<display:column property="stp" sortable="true"
			headerClass="sortable" titleKey="facilityNum.stp" />

	<display:column property="bsc" sortable="true"
			headerClass="sortable" titleKey="facilityNum.bsc" />

	<display:column property="bts" sortable="true"
			headerClass="sortable" titleKey="facilityNum.bts" />

	<display:column property="rnc" sortable="true"
			headerClass="sortable" titleKey="facilityNum.rnc" />

	<display:column property="nodeB" sortable="true"
			headerClass="sortable" titleKey="facilityNum.nodeB" />

	<display:column property="cr" sortable="true"
			headerClass="sortable" titleKey="facilityNum.cr" />

	<display:column property="br" sortable="true"
			headerClass="sortable" titleKey="facilityNum.br" />

	<display:column property="ar" sortable="true"
			headerClass="sortable" titleKey="facilityNum.ar" />

	<display:column property="ce" sortable="true"
			headerClass="sortable" titleKey="facilityNum.ce" />

	<display:column property="bb" sortable="true"
			headerClass="sortable" titleKey="facilityNum.bb" />

	<display:column property="bc" sortable="true"
			headerClass="sortable" titleKey="facilityNum.bc" />

	<display:column property="bi" sortable="true"
			headerClass="sortable" titleKey="facilityNum.bi" />

	<display:column property="pb" sortable="true"
			headerClass="sortable" titleKey="facilityNum.pb" />

	<display:column property="sw" sortable="true"
			headerClass="sortable" titleKey="facilityNum.sw" />

	<display:column property="wdm" sortable="true"
			headerClass="sortable" titleKey="facilityNum.wdm" />

	<display:column property="otnason" sortable="true"
			headerClass="sortable" titleKey="facilityNum.otnason" />

	<display:column property="sdhmstp" sortable="true"
			headerClass="sortable" titleKey="facilityNum.sdhmstp" />

	<display:column property="ptn" sortable="true"
			headerClass="sortable" titleKey="facilityNum.ptn" />

	<display:column property="pon" sortable="true"
			headerClass="sortable" titleKey="facilityNum.pon" />

	<display:column property="ggsn" sortable="true"
			headerClass="sortable" titleKey="facilityNum.ggsn" />

	<display:column property="sgsn" sortable="true"
			headerClass="sortable" titleKey="facilityNum.sgsn" />

	<display:column property="note" sortable="true"
			headerClass="sortable" titleKey="facilityNum.note" />

	<display:column property="multimediaMes" sortable="true"
			headerClass="sortable" titleKey="facilityNum.multimediaMes" />

	<display:column property="coloringRing" sortable="true"
			headerClass="sortable" titleKey="facilityNum.coloringRing" />

	<display:column property="wap" sortable="true"
			headerClass="sortable" titleKey="facilityNum.wap" />

	<display:column property="scp" sortable="true"
			headerClass="sortable" titleKey="facilityNum.scp" />

	<display:column property="smp" sortable="true"
			headerClass="sortable" titleKey="facilityNum.smp" />

	<display:column property="vc" sortable="true"
			headerClass="sortable" titleKey="facilityNum.vc" />

		<display:setProperty name="paging.banner.item_name" value="facilityNum" />
		<display:setProperty name="paging.banner.items_name" value="facilityNums" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>