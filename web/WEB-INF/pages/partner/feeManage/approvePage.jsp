<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>



<!-- Information hints area end-->
<logic:present name="feePoolTaskList" scope="request">
	<display:table name="feePoolTaskList" cellspacing="0" cellpadding="0"
		id="feePoolTaskList" pagesize="${pagesize}"
		class="table feePoolTaskList" size="${size}"
		requestURI="feeManage.do" sort="list"
		partialList="true" >
		

		<display:column sortable="true" headerClass="sortable" title="操作人">
				<eoms:id2nameDB beanId="tawSystemUserDao" id="${feePoolTaskList.taskOwner}"/>
		</display:column>
				
		<display:column sortable="true" headerClass="sortable" title="操作人类型">
			
			<c:if test='${feePoolTaskList.taskOwnerType=="1"}'>费用填报人员</c:if>
			<c:if test='${feePoolTaskList.taskOwnerType=="2"}'>地市代维管理员</c:if>
			<c:if test='${feePoolTaskList.taskOwnerType=="3"}'>地市管理人员</c:if>
		</display:column>
		
		<display:column sortable="true" headerClass="sortable" title="审核意见">
			<pre>${feePoolTaskList.taskName}</pre>
		</display:column>
	    <display:column sortable="true" headerClass="sortable" title="操作时间">
			${feePoolTaskList.taskTime}
		</display:column>
		
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:present>
	





<%@ include file="/common/footer_eoms.jsp"%>
