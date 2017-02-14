<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>



<script type="text/javascript">


</script>


 
<!-- Information hints area end-->
<logic:present name="warningBoardList" scope="request">
	<display:table name="warningBoardList" cellspacing="0" cellpadding="0"
		id="warningBoardList" pagesize="${pagesize}"
		class="table warningBoardList" 
		requestURI="warningboard.do" sort="list"
		partialList="true" size="${size}">
		

   	
		<display:column sortable="true" headerClass="sortable" title="填报人">
			${warningBoardList.personnelId}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="填报时间">
			${warningBoardList.greatTime}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="所属地市">
				<eoms:id2nameDB beanId="tawSystemAreaDao" id="${warningBoardList.areaId}"/>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="审核人">
		<eoms:id2nameDB beanId="tawSystemUserDao" id="${warningBoardList.auditId}"/>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="项目名称">
			${warningBoardList.itemName}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="增补警示牌/宣传牌中继段名称">
			${warningBoardList.repeaterSection}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="增补警示牌/宣传牌中继段里程（公里）">
			${warningBoardList.repeaterSectionMileage}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="中继段原有警示牌/宣传牌数量（块）">
			${warningBoardList.warningBoardOldNumber}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="计划增补警示牌/宣传牌数量（块）">
			${warningBoardList.warningBoardNewNumber}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="实际完成情况（块）">
			${warningBoardList.actualCompletion}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="完成时间">
			${warningBoardList.completionTime}
		</display:column>
		

		
		
		<display:column sortable="true" headerClass="sortable" title="状态" >					 
					<c:if test="${1==(warningBoardList.status)}" >草稿</c:if>	
					<c:if test="${5==(warningBoardList.status)}" >审核不通过</c:if>	
					<c:if test="${2==(warningBoardList.status)}" >审核中</c:if>
					<c:if test="${3==(warningBoardList.status)}" >审核通过</c:if>			
					<c:if test="${4==(warningBoardList.status)}" >已归档</c:if>	
		</display:column>	
		
		
		
		
		<display:column  headerClass="sortable" title="详情">
			 
			<a id="${warningBoardList.id }"
				href="${app }/deviceManagement/warningboard/warningboard.do?method=goToDetail&id=${warningBoardList.id}&type=0"
				>
				<img src="${app }/images/icons/search.gif">
				</a>	
		
		</display:column>

	

	</display:table>
</logic:present>
	


<%@ include file="/common/footer_eoms.jsp"%>
