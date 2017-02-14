<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>

<content tag="heading">
	采集结果
</content>

<display:table name="list" cellspacing="0" cellpadding="0"
	id="list" pagesize="${pageSize}" class="table assCollectionConfigList"
	export="false"
	requestURI="${app}/partnerassess/assAutoCollections.do?method=searchCollectionResult"
	sort="list" partialList="true" size="resultSize">
	<display:column  sortable="true"
			headerClass="sortable" title="合作伙伴" >
			<eoms:id2nameDB id="${list.partner}" beanId="partnerDeptDao"/> 
	</display:column>		
	<display:column  sortable="true"
			headerClass="sortable" title="对应指标" >
			<eoms:id2nameDB id="${list.kpiId}" beanId="tranAssKpiDao"/> 
	</display:column>		
	
	<display:column  sortable="true"
			headerClass="sortable" title="地市" >
			<eoms:id2nameDB id="${list.area}" beanId="tawSystemAreaDao"/> 
	</display:column>
	
	<display:column  sortable="true"
			headerClass="sortable" title="周期" >
			<eoms:dict key="dict-partner-assess" dictId="cycle" itemId="${list.cycle}" beanId="id2nameXML" />
	</display:column>	
	
	<display:column  sortable="true"property="time"
			headerClass="sortable" title="时间" />
			
	<display:column  sortable="true"property="result"
			headerClass="sortable" title="采集结果" />	
					
</display:table>


<%@ include file="/common/footer_eoms.jsp"%>