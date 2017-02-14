<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<content tag="heading">通用故障信息</content>

	<display:table name="faultCommontList" cellspacing="0" cellpadding="0"
		id="faultCommontList" pagesize="${pageSize}" class="table faultCommontList"
		export="true" 
		requestURI="${app}/duty/faultCommonts.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column property="sequenceNo" sortable="true"
			headerClass="sortable" title="故障编号" href="${app}/duty/faultCommonts.do?method=view" paramId="id" paramProperty="id"/>

	<display:column property="title" sortable="true"
			headerClass="sortable" title="标题" href="${app}/duty/faultCommonts.do?method=view" paramId="id" paramProperty="id"/>
	
	<display:column sortable="true" headerClass="sortable" title="故障类别" href="${app}/duty/faultCommonts.do?method=view" paramId="id" paramProperty="id">
     	<eoms:id2nameDB id="${faultCommontList.typeId}" beanId="tawSystemDictTypeDao" />
    </display:column>

	<display:column property="declarer" sortable="true"
			headerClass="sortable" title="上报人/部门" href="${app}/duty/faultCommonts.do?method=view" paramId="id" paramProperty="id"/>

	<display:column property="beginTime" sortable="true"
			headerClass="sortable" title="故障发生时间" format="{0,date,yyyy-MM-dd HH:mm:ss}" href="${app}/duty/faultCommonts.do?method=view" paramId="id" paramProperty="id"/>

	<display:column property="endTime" sortable="true"
			headerClass="sortable" title="故障恢复时间" format="{0,date,yyyy-MM-dd HH:mm:ss}" href="${app}/duty/faultCommonts.do?method=view" paramId="id" paramProperty="id"/>

	<display:column sortable="true" headerClass="sortable" title="录入人" href="${app}/duty/faultCommonts.do?method=view" paramId="id" paramProperty="id">
     	<eoms:id2nameDB id="${faultCommontList.inputUser}" beanId="tawSystemUserDao" />
    </display:column>
    <display:setProperty name="export.pdf" value="false"/>
	<display:setProperty name="export.xml" value="false"/>
	<display:setProperty name="export.csv" value="false"/>  
	<display:setProperty name="export.rtf" value="false"/>         
         
		<display:setProperty name="paging.banner.item_name" value="faultCommont" />
		<display:setProperty name="paging.banner.items_name" value="faultCommonts" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />
<%@ include file="/common/footer_eoms.jsp"%>