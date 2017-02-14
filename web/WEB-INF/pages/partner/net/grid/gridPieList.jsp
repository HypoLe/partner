<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<script type="text/javascript">

</script>

<fmt:bundle basename="com/boco/eoms/partner/serviceArea/config/applicationResource-partner-serviceArea">


<div style="width:1024">

	<display:table name="gridList" cellspacing="0" cellpadding="0"
		id="gridList" pagesize="${pageSize}" class="table gridList"
		export="false" 
		requestURI="${app}/partner/serviceArea/grids.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<!-- 广西-->
		<display:column property="gridNumber" sortable="true"
			headerClass="sortable" title="基站编号"  paramId="id" paramProperty="id"/>
			
	<display:column property="gridName" sortable="true"
			headerClass="sortable" title="基站名称"  paramId="id" paramProperty="id"/>
	
	<!-- 所在地市 -->
	<display:column  sortable="true" headerClass="sortable" titleKey="grid.region">
		<eoms:id2nameDB id="${gridList.region}" beanId="tawSystemAreaDao" />
	</display:column>			

	<display:column property="provider" sortable="true"
			headerClass="sortable" titleKey="grid.provider"  paramId="id" paramProperty="id"/>
	
	<display:column sortable="true" headerClass="sortable" titleKey="grid.addUser" >
			<eoms:id2nameDB id="${gridList.addUser}" beanId="tawSystemUserDao" />
	</display:column>

	<display:column property="addTime" sortable="true"  format="{0,date,yyyy-MM-dd HH:mm:ss}" 
			headerClass="sortable" titleKey="grid.addTime"  paramId="id" paramProperty="id"/>

		<display:setProperty name="paging.banner.item_name" value="grid" />
		<display:setProperty name="paging.banner.items_name" value="grids" />
	</display:table>

	

	


</div>
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>