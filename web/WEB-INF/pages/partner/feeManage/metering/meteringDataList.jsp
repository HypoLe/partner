<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@page import="java.util.List"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>




    
	<logic:present name="feePoolMeteringDataList" scope="request">
    <table class="formTable">

	<display:table name="feePoolMeteringDataList" cellspacing="0" cellpadding="0"
		id="feePoolMeteringDataList"  pagesize="${pagesize}"
		class="table" export="false"  size="${size}"
		sort="list" partialList="true"
		>
			
		<display:column sortable="false" headerClass="sortable" title="计次类型">
			${feePoolMeteringDataList.meteringTypeName}
		</display:column>
		
		<display:column sortable="false" headerClass="sortable" title="专业">
		<eoms:id2nameDB id="${feePoolMeteringDataList.major}"  beanId="ItawSystemDictTypeDao" />	
		</display:column>
		
		
		<display:column sortable="false" headerClass="sortable" title="年份">
			${feePoolMeteringDataList.yearFlag}
		</display:column>
		<display:column sortable="false" headerClass="sortable" title="月份">
			${feePoolMeteringDataList.mouthFlag}
		</display:column>	
		<display:column sortable="false" headerClass="sortable" title="代维公司">
			<eoms:id2nameDB id="${feePoolMeteringDataList.companyId}" beanId="partnerDeptDao" />
		</display:column>	
		<display:column sortable="false" headerClass="sortable" title="计次指标名称">
			${feePoolMeteringDataList.indexName}
		</display:column>
		<display:column sortable="false" headerClass="sortable" title="指标属性值">
			${feePoolMeteringDataList.indexSelected}
		</display:column>
		<display:column sortable="false" headerClass="sortable" title="次数">
			${feePoolMeteringDataList.times}
		</display:column>
	</display:table>
</table></logic:present>

<br />



<%@ include file="/common/footer_eoms.jsp"%>