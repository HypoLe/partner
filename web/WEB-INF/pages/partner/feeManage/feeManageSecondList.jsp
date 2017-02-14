<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>



<!-- Information hints area end-->
<logic:present name="feePoolMainList" scope="request">
	<display:table name="feePoolMainList" cellspacing="0" cellpadding="0"
		id="feePoolMainList" pagesize="${pagesize}"
		class="table feePoolMainList" 
		requestURI="feeManage.do" sort="list"
		partialList="true" size="${size}">
		

		<display:column sortable="true" headerClass="sortable" title="所属地市">
				<eoms:id2nameDB beanId="tawSystemAreaDao" id="${feePoolMainList.region}"/>
		</display:column>
				
		<display:column sortable="true" headerClass="sortable" title="代维公司">
			<eoms:id2nameDB id="${feePoolMainList.company}" beanId="partnerDeptDao" />
		</display:column>
		
		<display:column sortable="true" headerClass="sortable" title="年份">
			${feePoolMainList.feeYear}
		</display:column>
		
		<display:column sortable="true" headerClass="sortable" title="月份">
			${feePoolMainList.feeMonth}
		</display:column>
		
		<display:column sortable="true" headerClass="sortable" title="操作">
			<a href="${app}/partner/feeManage/feeManage.do?method=goToSpecialtyList&pageType=${pageType}&id=${feePoolMainList.id}">审核代维费用信息</a>
		</display:column>
	
	
		
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:present>
	





<%@ include file="/common/footer_eoms.jsp"%>
