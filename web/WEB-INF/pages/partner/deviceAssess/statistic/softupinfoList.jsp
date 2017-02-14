<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>


<fmt:bundle basename="com/boco/eoms/partner/deviceAssess/config/applicationResource-partner-deviceAssess">
<content tag="heading">
	<fmt:message key="softupinfo.list.heading" />
</content>
	<display:table name="softupinfoList" cellspacing="0" cellpadding="0"
		id="softupinfoList" pagesize="${pageSize}" class="table softupinfoList"
		export="false"
		requestURI="${app}/partner/deviceAssess/softupinfos.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column property="createTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="softupinfo.createTime" />

	<display:column property="pigeonholeTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" titleKey="softupinfo.pigeonholeTime" />

	<display:column property="affairName" sortable="true"
			headerClass="sortable" titleKey="softupinfo.affairName" />

	<display:column  sortable="true"
			headerClass="sortable" titleKey="softupinfo.affairLevel" >
			<eoms:id2nameDB id="${softupinfoList.affairLevel}"  beanId="ItawSystemDictTypeDao" />
	</display:column>
	<display:column property="province" sortable="true"
			headerClass="sortable" titleKey="softupinfo.province" />

	<display:column property="city" sortable="true"
			headerClass="sortable" titleKey="softupinfo.city" />

	<display:column  sortable="true"
			headerClass="sortable" titleKey="softupinfo.factory" >
			<eoms:id2nameDB id="${softupinfoList.factory}"  beanId="ItawSystemDictTypeDao" />
	</display:column>
	<display:column  sortable="true"
			headerClass="sortable" titleKey="softupinfo.speciality" >
			<eoms:id2nameDB id="${softupinfoList.speciality}"  beanId="ItawSystemDictTypeDao" />
	</display:column>
	<display:column  sortable="true"
			headerClass="sortable" titleKey="softupinfo.equipmentType" >
			<eoms:id2nameDB id="${softupinfoList.equipmentType}"  beanId="ItawSystemDictTypeDao" />
	</display:column>
	<display:column property="equipmentName" sortable="true"
			headerClass="sortable" titleKey="softupinfo.equipmentName" />

	<display:column property="nonceEdition" sortable="true"
			headerClass="sortable" titleKey="softupinfo.nonceEdition" />

	<display:column property="updateEdition" sortable="true"
			headerClass="sortable" titleKey="softupinfo.updateEdition" />

	<display:column property="upfixtureAmount" sortable="true"
			headerClass="sortable" titleKey="softupinfo.upfixtureAmount" />

	<display:column property="uphitAmount" sortable="true"
			headerClass="sortable" titleKey="softupinfo.uphitAmount" />

	<display:column property="satisfaction" sortable="true"
			headerClass="sortable" titleKey="softupinfo.satisfaction" />

	<display:column property="uphitRate" sortable="true"
			headerClass="sortable" titleKey="softupinfo.uphitRate" />

	<display:column property="takeCountOf" sortable="true"
			headerClass="sortable" titleKey="softupinfo.takeCountOf" />

		<display:setProperty name="paging.banner.item_name" value="softupinfo" />
		<display:setProperty name="paging.banner.items_name" value="softupinfos" />
	</display:table>

</fmt:bundle>


<div>
<div id="tabs-1" name="tabs-1">
	<logic:notEmpty name="softApplyRecordList" scope="request">
		<display:table name="softApplyRecordList" cellspacing="0"
			cellpadding="0" id="softApplyRecordList" pagesize="${pageSize}"
			class="table softApplyRecordList" export="false"
			requestURI="${app}/partner/deviceAssess/softApplyRecord.do?method=search"
			sort="list" partialList="true" size="resultSize">

			<display:column sortable="true" media="html" headerClass="sortable"
				title="事件名称">
				<a
					href="${app}/partner/deviceAssess/softApplyRecord.do?method=goToDetail&id=${softApplyRecordList.id }"
					target="blank" shape="rect"> ${softApplyRecordList.affairName }
				</a>
			</display:column>

			<display:column property="createTime" sortable="true"
				format="{0,date,yyyy-MM-dd HH:mm:ss}" headerClass="sortable"
				title="申请时间" />

			<display:column sortable="true" headerClass="sortable" title="厂家">
				<eoms:id2nameDB id="${softApplyRecordList.factory}"
					beanId="ItawSystemDictTypeDao" />
			</display:column>
			<display:column sortable="true" headerClass="sortable" title="专业">
				<eoms:id2nameDB id="${softApplyRecordList.speciality}"
					beanId="ItawSystemDictTypeDao" />
			</display:column>
			<display:column sortable="true" headerClass="sortable" title="设备类型">
				<eoms:id2nameDB id="${softApplyRecordList.equipmentType}"
					beanId="ItawSystemDictTypeDao" />
			</display:column>

			<display:column property="licenseProblem" sortable="true"
				headerClass="sortable" title="许可证问题" />

			<display:column property="schemeProblem" sortable="true"
				headerClass="sortable" title="方案问题" />

			<display:column property="amount" sortable="true"
				headerClass="sortable" title="计数" />

			<display:column sortable="false" media="html" title="详情">
				<a
					href="${app}/partner/deviceAssess/softApplyRecord.do?method=goToDetail&id=${softApplyRecordList.id }"
					target="blank" shape="rect"> 查看 </a>
			</display:column>

			<display:setProperty name="paging.banner.item_name"
				value="insideDispose" />
			<display:setProperty name="paging.banner.items_name"
				value="insideDisposes" />
		</display:table>

		<c:out value="${buttons}" escapeXml="false" />
	</logic:notEmpty>
</div>
<logic:empty name="softApplyRecordList" scope="request">
	软件申请事件信息，没有记录！
</logic:empty>
</div>
<%@ include file="/common/footer_eoms.jsp"%>