<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>


<!-- Information hints area end-->
<logic:present name="feeApplicationMainList" scope="request">
	<display:table name="feeApplicationMainList" cellspacing="0" cellpadding="0"
		id="feeApplicationMainList" pagesize="${pagesize}"
		class="table feeApplicationMainList" export="true"
		requestURI="charge.do" sort="list"
		partialList="true" size="${size}">
		

	
		<display:column sortable="true" headerClass="sortable" title="申请人">
			${feeApplicationMainList.feeApplicationUser}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="申请日期">
			${feeApplicationMainList.feeApplicationGreatTime}
		</display:column>
			<display:column sortable="true" headerClass="sortable" title="所属地市">
            <eoms:id2nameDB beanId="tawSystemAreaDao" id="${feeApplicationMainList.feeApplicationCity}"/>
			</display:column>
		<display:column sortable="true" headerClass="sortable" title="费用类型">
			${feeApplicationMainList.feeApplicationType}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="费用金额">
			${feeApplicationMainList.feeApplicationMoney}
		</display:column>
		
	
		<display:column sortable="false" headerClass="sortable"
			title="详情" media="html">
			<a id="${feeApplicationMainList.id }"
				href="${app }/deviceManagement/chargeFeeAppli/charge.do?method=goToDetail&id=${feeApplicationMainList.id}"
				target="blank" shape="rect">
				<img src="${app }/images/icons/table.gif">
				</a>
		</display:column>
		
	
	
		<display:column sortable="true" headerClass="sortable" 
		   title="状态"  media="html" >					 
					<c:if test="${1==(feeApplicationMainList.feeApplicationStatus)}" >草稿</c:if>		
					<c:if test="${2==(feeApplicationMainList.feeApplicationStatus)}" >地市审批中</c:if>
					<c:if test="${3==(feeApplicationMainList.feeApplicationStatus)}" >代维公司审批中</c:if>			
					<c:if test="${4==(feeApplicationMainList.feeApplicationStatus)}" >审批通过</c:if>
					<c:if test="${5==(feeApplicationMainList.feeApplicationStatus)}" >已归档</c:if>		
		</display:column>	
		
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:present>
	
</div>



<%@ include file="/common/footer_eoms.jsp"%>
