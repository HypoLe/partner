<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<content tag="heading">线路故障记录 列表</content>

	<display:table name="faultCircuitList" cellspacing="0" cellpadding="0"
		id="faultCircuitList" pagesize="${pageSize}" class="table faultCircuitList"
		export="true" 
		requestURI="${app}/duty/faultCircuits.do?method=search"
		sort="list" partialList="true" size="resultSize">
	<display:column property="sequenceNo" sortable="true"
			headerClass="sortable" title="工单号" href="${app}/duty/faultCircuits.do?method=view" paramId="id" paramProperty="id"/>

	<display:column property="title" sortable="true"
			headerClass="sortable" title="主题" href="${app}/duty/faultCircuits.do?method=view" paramId="id" paramProperty="id"/>
	
	<display:column property="faultTypeName" sortable="true"
			headerClass="sortable" title="故障类别" href="${app}/duty/faultCircuits.do?method=view" paramId="id" paramProperty="id"/>

	<display:column sortable="true" headerClass="sortable" title="故障记录人" href="${app}/duty/faultCircuits.do?method=view" paramId="id" paramProperty="id">
     		<eoms:id2nameDB id="${faultCircuitList.greffier}" beanId="tawSystemUserDao" />
    </display:column>

	<display:column property="beginTime" sortable="true"
			headerClass="sortable" title="开始时间" format="{0,date,yyyy-MM-dd HH:mm:ss}" href="${app}/duty/faultCircuits.do?method=view" paramId="id" paramProperty="id"/>

	<display:column property="endTime" sortable="true"
			headerClass="sortable" title="结束时间" format="{0,date,yyyy-MM-dd HH:mm:ss}" href="${app}/duty/faultCircuits.do?method=view" paramId="id" paramProperty="id"/>
	
	<display:setProperty name="export.pdf" value="false"/>
	<display:setProperty name="export.xml" value="false"/>
	<display:setProperty name="export.csv" value="false"/>  
	<display:setProperty name="export.rtf" value="false"/>  
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />
<%@ include file="/common/footer_eoms.jsp"%>