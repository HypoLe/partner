<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<fmt:bundle basename="com/boco/eoms/partner/serviceArea/config/applicationResource-partner-serviceArea">
	<display:table name="siteList" cellspacing="0" cellpadding="0"
		id="siteList" pagesize="${pageSize}" class="table siteList"
		export="false"
		requestURI="${app}/partner/serviceArea/sites.do?method=search"
		sort="list" partialList="true" size="resultSize">
	<display:column property="provider" sortable="true"
			headerClass="sortable" titleKey="site.provider"  paramId="id" paramProperty="id"/>

	<!-- 所在地市 -->
	<display:column  sortable="true" headerClass="sortable" titleKey="line.region">
		<eoms:id2nameDB id="${siteList.region}" beanId="tawSystemAreaDao" />
	</display:column>			

	<!-- 所在县区 -->
	<display:column  sortable="true" headerClass="sortable" titleKey="line.city">
		<eoms:id2nameDB id="${siteList.city}" beanId="tawSystemAreaDao" />
	</display:column>			
	<display:column property="siteName" sortable="true"
			headerClass="sortable" titleKey="site.siteName" paramId="siteNo" paramProperty="siteNo"/>

	<display:column property="siteNo" sortable="true"
			headerClass="sortable" titleKey="site.siteNo"  paramId="id" paramProperty="id"/>			

	<display:column property="grid" sortable="true"
			headerClass="sortable" titleKey="site.grid"  paramId="id" paramProperty="id"/>
	<display:column sortable="true" headerClass="sortable" titleKey="site.siteType">
		<c:if test="${siteList.siteType!=null}">
			
			<eoms:id2nameDB id="${siteList.siteType}"  beanId="ItawSystemDictTypeDao" />
		</c:if>
		<c:if test="${siteList.siteType==null}">
			暂无
		</c:if>
	</display:column>

	<display:column sortable="true" headerClass="sortable" titleKey="site.coverType" >
		<c:if test="${siteList.coverType!=null}">
			<eoms:dict key="dict-partner-serviceArea" dictId="coverType" itemId="${siteList.coverType}" beanId="id2nameXML" />
		</c:if>
		<c:if test="${siteList.coverType==null}">
			暂无
		</c:if>
	</display:column>
	
	<display:column sortable="true" headerClass="sortable" titleKey="site.vendor" >
		<c:if test="${siteList.vendor!=null}">
			<eoms:dict key="dict-partner-serviceArea" dictId="vendor" itemId="${siteList.vendor}" beanId="id2nameXML" />
		</c:if>
		<c:if test="${siteList.vendor==null}">
			暂无
		</c:if>
	</display:column>
	
	<display:column property="longitude" sortable="true"
			headerClass="sortable" titleKey="site.longitude"  paramId="id" paramProperty="id"/>

	<display:column property="latitude" sortable="true"
			headerClass="sortable" titleKey="site.latitude"  paramId="id" paramProperty="id"/>

	<display:column sortable="true" headerClass="sortable" titleKey="site.cellulType" >
		<c:if test="${siteList.cellulType!=null}">
		<eoms:dict key="dict-partner-serviceArea" dictId="cellulType" itemId="${siteList.cellulType}" beanId="id2nameXML" />
		</c:if>
		<c:if test="${siteList.cellulType==null}">
			暂无
		</c:if>
	</display:column>
	
	<display:column sortable="true" headerClass="sortable" titleKey="site.siteLevle"  paramId="id" paramProperty="id">
		<eoms:dict key="dict-partner-serviceArea" dictId="siteLevle" itemId="${siteList.siteLevle}" beanId="id2nameXML" />
	</display:column>
	
	<display:column sortable="true" headerClass="sortable" titleKey="site.roomType"  paramId="id" paramProperty="id">
		<c:if test="${siteList.roomType!=null}">
			<eoms:dict key="dict-partner-serviceArea" dictId="roomType" itemId="${siteList.roomType}" beanId="id2nameXML" />
		</c:if>
		<c:if test="${siteList.roomType==null}">
			暂无
		</c:if>
	</display:column>
	
	<display:column sortable="true" headerClass="sortable" titleKey="site.maintainType"  paramId="id" paramProperty="id">
		<c:if test="${siteList.maintainType!=null}">
			<eoms:dict key="dict-partner-serviceArea" dictId="maintainType" itemId="${siteList.maintainType}" beanId="id2nameXML" />
		</c:if>
		<c:if test="${siteList.maintainType==null}">
			暂无
		</c:if>
	</display:column>
			
	<display:column property="frequencyBand" sortable="true"
			headerClass="sortable" titleKey="site.frequencyBand"  paramId="id" paramProperty="id">
	</display:column>	

		<display:setProperty name="paging.banner.item_name" value="site" />
		<display:setProperty name="paging.banner.items_name" value="sites" />
	</display:table>


</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>