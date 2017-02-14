<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<fmt:bundle basename="com/boco/eoms/duty/config/ApplicationResources-duty">
<display:table name="tawRmGuestFormList" cellspacing="0" cellpadding="0"
		id="tawRmGuestFormList"  class="table tawRmGuestFormList"
		pagesize="${pageSize}" export="true" 
		sort="list"  partialList="true" size="resultSize">
 	<display:column titleKey="TawRmGuestform.id">
	<%=pageContext.getAttribute("tawRmGuestFormList_rowNum")%>
	</display:column>
	<display:column property="inputdate" sortable="true"  sortName="inputdate"
			headerClass="sortable" titleKey="TawRmGuestform.inputdate" />
	
	<display:column property="guestname" sortable="true" sortName="guestname"
			headerClass="sortable" titleKey="TawRmGuestform.guestname"/>

	<display:column property="company" sortable="true" sortName="company"
			headerClass="sortable" titleKey="TawRmGuestform.company"/>

	<display:column property="dutyman" sortable="true" sortName="dutyman"
			headerClass="sortable" titleKey="TawRmGuestform.dutyman"/>
	
		<display:column property="starttime" sortable="true" sortName="starttime"
			headerClass="sortable" titleKey="TawRmGuestform.starttime"/>
	
		<display:column property="endtime" sortable="true" sortName="endtime"
			headerClass="sortable" titleKey="TawRmGuestform.endtime"/>
	
		<display:column  sortable="false"
			headerClass="sortable" titleKey="TawRmGuestform.flag">
			<c:choose>
				<c:when test="${tawRmGuestFormList.flag == 0}">
					<bean:message key="TawRmGuestform.nosubmit"/>
				</c:when>
				<c:otherwise>
					<bean:message key="TawRmGuestform.submit"/>
				</c:otherwise>
			</c:choose>
		</display:column>
	</display:table>
		<c:out value="${buttons}" escapeXml="false" />
</fmt:bundle>
<%@ include file="/common/footer_eoms.jsp"%>