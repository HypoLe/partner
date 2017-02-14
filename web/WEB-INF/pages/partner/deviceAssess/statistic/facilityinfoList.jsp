<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<div id="tabs-1" name="tabs-1">
<logic:notEmpty name="facilityinfoList" scope="request">
<content tag="heading">
厂家设备问题事件信息
</content>
	<display:table name="facilityinfoList" cellspacing="0" cellpadding="0"
		id="facilityinfoList" pagesize="${pageSize}" class="table facilityinfoList"
		export="false"
		requestURI="${app}/partner/deviceAssess/facilityinfos.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column sortable="true"
			headerClass="sortable" title="问题类别" >
		<eoms:id2nameDB id="${facilityinfoList.issueType}"  beanId="ItawSystemDictTypeDao" />
	</display:column>
	<display:column sortable="true"
			headerClass="sortable" title="级别" >
		<eoms:id2nameDB id="${facilityinfoList.facilityLevel}"  beanId="ItawSystemDictTypeDao" />
	</display:column>

	<display:column property="province" sortable="true"
			headerClass="sortable" title="发生省份" />

	<display:column property="city" sortable="true"
			headerClass="sortable" title="发生地市" />

	<display:column sortable="true"
			headerClass="sortable" title="厂家" >
			<eoms:id2nameDB id="${facilityinfoList.factory}"  beanId="ItawSystemDictTypeDao" />	
	</display:column>
	<display:column sortable="true"
			headerClass="sortable" title="专业" >
			<eoms:id2nameDB id="${facilityinfoList.speciality}"  beanId="ItawSystemDictTypeDao" />	
	</display:column>
	<display:column sortable="true"
			headerClass="sortable" title="设备类型" >
			<eoms:id2nameDB id="${facilityinfoList.equipmentType}"  beanId="ItawSystemDictTypeDao" />	
	</display:column>
	<display:column property="equipmentName" sortable="true"
			headerClass="sortable" title="设备名称" />

	<display:column property="equipmentVersion" sortable="true"
			headerClass="sortable" title="设备版本" />

	<display:column property="occurTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" title="发生时间" />

	<display:column sortable="true"
			headerClass="sortable" title="状态">
			<eoms:id2nameDB id="${facilityinfoList.state}"  beanId="ItawSystemDictTypeDao" />	
	</display:column>
	
	<display:column property="solveTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" title="解决时间" />

	
		<display:setProperty name="paging.banner.item_name" value="facilityinfo" />
		<display:setProperty name="paging.banner.items_name" value="facilityinfos" />
	</display:table>

	<c:out value="${buttons}" escapeXml="false" />
</logic:notEmpty>
</div>
<logic:empty name="facilityinfoList" scope="request">
	没有数据！
</logic:empty>
<%@ include file="/common/footer_eoms.jsp"%>