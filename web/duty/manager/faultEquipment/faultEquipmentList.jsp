<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<content tag="heading">设备故障记录 列表</content>

	<display:table name="faultEquipmentList" cellspacing="0" cellpadding="0"
		id="faultEquipmentList" pagesize="${pageSize}" class="table faultEquipmentList"
		export="true" 
		requestURI="${app}/duty/faultEquipments.do?method=search"
		sort="list" partialList="true" size="resultSize">
	<display:column property="sequenceNo" sortable="true"
			headerClass="sortable" title="故障编号" href="${app}/duty/faultEquipments.do?method=view" paramId="id" paramProperty="id"/>
	
	<display:column property="title" sortable="true"
			headerClass="sortable" title="标题" href="${app}/duty/faultEquipments.do?method=view" paramId="id" paramProperty="id"/>
	
	<display:column property="station" sortable="true"
			headerClass="sortable" title="系统/站点" href="${app}/duty/faultEquipments.do?method=view" paramId="id" paramProperty="id"/>

	<display:column sortable="true" headerClass="sortable" title="故障记录人" href="${app}/duty/faultEquipments.do?method=view" paramId="id" paramProperty="id">
     	<eoms:id2nameDB id="${faultEquipmentList.greffier}" beanId="tawSystemUserDao" />
    </display:column>

	<display:column property="beginTime" sortable="true"
			headerClass="sortable" title="开始时间" format="{0,date,yyyy-MM-dd HH:mm:ss}" href="${app}/duty/faultEquipments.do?method=view" paramId="id" paramProperty="id"/>

	<display:column property="endTime" sortable="true"
			headerClass="sortable" title="结束时间" format="{0,date,yyyy-MM-dd HH:mm:ss}" href="${app}/duty/faultEquipments.do?method=view" paramId="id" paramProperty="id"/>
	
	<display:setProperty name="export.pdf" value="false"/>
	<display:setProperty name="export.xml" value="false"/>
	<display:setProperty name="export.csv" value="false"/>  
	<display:setProperty name="export.rtf" value="false"/>  
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />

<%@ include file="/common/footer_eoms.jsp"%>