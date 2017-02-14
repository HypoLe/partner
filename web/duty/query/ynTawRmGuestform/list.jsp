<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<content tag="heading">出入机房 列表</content>
        
<display:table name="tawRmGuestFormList" cellspacing="0" cellpadding="0"
		id="tawRmGuestFormList"  class="table tawRmGuestFormList"
		pagesize="${pageSize}" export="true"  requestURI=""
		sort="list"  partialList="true" size="resultSize">
 	<display:column title="编号" sortable="true" href="${app}/duty/TawRmGuestform.do?method=view" paramId="id" paramProperty="id">
 	<%=pageContext.getAttribute("tawRmGuestFormList_rowNum")%>
	</display:column>
	<display:column property="inputdate" format="{0,date,yyyy-MM-dd HH:mm:ss}" sortable="true"  sortName="inputdate"
			headerClass="sortable" title="日期" href="${app}/duty/TawRmGuestform.do?method=view" paramId="id" paramProperty="id"/>
	
	<display:column property="guestname" sortable="true" sortName="guestname"
			headerClass="sortable" title="访客姓名" href="${app}/duty/TawRmGuestform.do?method=view" paramId="id" paramProperty="id"/>

	<display:column property="company" sortable="true" sortName="company"
			headerClass="sortable" title="访客单位" href="${app}/duty/TawRmGuestform.do?method=view" paramId="id" paramProperty="id"/>

	<display:column property="dutyman" sortable="true" sortName="dutyman"
			headerClass="sortable" title="登记人" href="${app}/duty/TawRmGuestform.do?method=view" paramId="id" paramProperty="id"/>
	
		<display:column property="starttime" format="{0,date,yyyy-MM-dd HH:mm:ss}" sortable="true" sortName="starttime"
			headerClass="sortable" title="开始时间" href="${app}/duty/TawRmGuestform.do?method=view" paramId="id" paramProperty="id"/>
	
		<display:column property="endtime" format="{0,date,yyyy-MM-dd HH:mm:ss}" sortable="true" sortName="endtime"
			headerClass="sortable" title="结束时间" href="${app}/duty/TawRmGuestform.do?method=view" paramId="id" paramProperty="id"/>
	
		<display:column  sortable="false"
			headerClass="sortable" title="是否确认" href="${app}/duty/TawRmGuestform.do?method=view" paramId="id" paramProperty="id">
			<c:choose>
				<c:when test="${tawRmGuestFormList.flag == 0}">
					<bean:message key="TawRmGuestform.nosubmit"/>
				</c:when>
				<c:otherwise>
					<bean:message key="TawRmGuestform.submit"/>
				</c:otherwise>
			</c:choose>
		</display:column>
		<display:setProperty name="export.pdf" value="false"/>
		<display:setProperty name="export.xml" value="false"/>
		<display:setProperty name="export.csv" value="false"/>  
		<display:setProperty name="export.rtf" value="false"/> 
	</display:table>
		<c:out value="${buttons}" escapeXml="false" />

<%@ include file="/common/footer_eoms.jsp"%>