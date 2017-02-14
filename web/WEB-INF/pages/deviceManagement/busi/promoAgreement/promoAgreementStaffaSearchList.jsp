<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>



<script type="text/javascript">


</script>


 
<!-- Information hints area end-->
<logic:present name="promoAgreementList" scope="request">
	<display:table name="promoAgreementList" cellspacing="0" cellpadding="0"
		id="promoAgreementList" pagesize="${pagesize}"
		class="table promoAgreementList" export="true"
		requestURI="promoagreement.do" sort="list"
		partialList="true" size="${size}">
		

   	
		<display:column sortable="true" headerClass="sortable" title="填报人">
			${promoAgreementList.personnelId}
		</display:column>
		<%--<display:column sortable="true" headerClass="sortable" title="填报时间">
			${promoAgreementList.greatTime}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="所属地市">
				<eoms:id2nameDB beanId="tawSystemAreaDao" id="${promoAgreementList.areaId}"/>
		</display:column>----%>
		<display:column sortable="true" headerClass="sortable" title="审核人">
		<eoms:id2nameDB beanId="tawSystemUserDao" id="${promoAgreementList.auditId}"/>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="项目名称">
			${promoAgreementList.itemName}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="中继段称名">
			${promoAgreementList.repeaterSection}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="中继段里程（公里）">
			${promoAgreementList.repeaterSectionMileage}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="中继段原有签订护线协议数量（份）">
			${promoAgreementList.agreementOldNumber}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="计划新签订护线协议数量（份）">
			${promoAgreementList.agreementNewNumber	}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="实际完成情况（份）">
			${promoAgreementList.actualCompletion	}
		</display:column>
		<%----<display:column sortable="true" headerClass="sortable" title="完成时间">
			${promoAgreementList.completionTime	}
		</display:column>---%>
		

		
		
		<display:column sortable="true" headerClass="sortable" title="状态" >					 
					<c:if test="${1==(promoAgreementList.status)}" >草稿</c:if>	
					<c:if test="${5==(promoAgreementList.status)}" >审核不通过</c:if>	
					<c:if test="${2==(promoAgreementList.status)}" >审核中</c:if>
					<c:if test="${3==(promoAgreementList.status)}" >审核通过</c:if>			
					<c:if test="${4==(promoAgreementList.status)}" >已归档</c:if>	
		</display:column>	
		
		
		
		
		<display:column  headerClass="sortable" title="详情">
			 
			<a id="${promoAgreementList.id }"
				href="${app }/deviceManagement/promoagreement/promoagreement.do?method=goToDetail&id=${promoAgreementList.id}&type=0"
				>
				<img src="${app }/images/icons/table.gif">
				</a>	
		
		</display:column>

	
		
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:present>
	


<%@ include file="/common/footer_eoms.jsp"%>
