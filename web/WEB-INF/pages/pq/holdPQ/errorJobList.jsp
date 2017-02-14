<%@ page language="java" pageEncoding="UTF-8" import="com.boco.eoms.pq.util.PQConfigLocator"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<fmt:bundle basename="com/boco/eoms/pq/config/ApplicationResources-pq">

<div class="list-title">
	<fmt:message key="pq.hold.list.heading" />
</div>

<ul>
	<c:if test="${not empty note}">
	<li class="error">
		<img src="<c:url value="/images/icons/warning.gif"/>" 
			alt="<fmt:message key="icon.warning"/>" class="icon" />
		${note}
	</li>
	</c:if>
</ul>

<display:table name="jobList" cellspacing="0" cellpadding="0"
    id="jobList" pagesize="<%=PQConfigLocator.getPQConfig().getPageSize().intValue()%>" 
    class="table jobList" sort="list" 
    partialList="true" size="resultSize" requestURI="" >

	<display:column property="jobId" sortable="true" headerClass="sortable"
         titleKey="pq.hold.list.jobid" />
         
    <display:column property="clz" sortable="true" headerClass="sortable"
         titleKey="pq.hold.list.clz" />

    <display:column property="method" sortable="true" headerClass="sortable"
         titleKey="pq.hold.list.method" />

    <display:column property="insertTime" sortable="true" headerClass="sortable"
         titleKey="pq.hold.list.time" />

    <display:column property="status" sortable="true" headerClass="sortable"
         titleKey="pq.hold.list.status" />
         
    <display:column sortable="false" headerClass="sortable"
         titleKey="pq.hold.list.opers">
         <a href="${app}/pq/holdPQs.do?method=doJobManually&jobId=${jobList.jobId}">
         	<fmt:message key="pq.hold.list.opers.execute" />
         </a>
         <a href="${app}/pq/holdPQs.do?method=deleteErrorJob&jobId=${jobList.jobId}">
         	<fmt:message key="pq.hold.list.opers.delete" />
         </a>
    </display:column>
         
</display:table>
 
 </fmt:bundle>
 
<%@ include file="/common/footer_eoms.jsp"%>