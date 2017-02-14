<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page import="com.boco.eoms.common.util.StaticMethod"%>
<%@ page import="com.boco.eoms.commons.system.session.form.TawSystemSessionForm"%>

<script type="text/javascript" src="${app}/scripts/widgets/calendar/calendar.js"></script>

<%

	String auditName = StaticMethod.nullObject2String(request.getAttribute("auditName"));
	String excelUrl = StaticMethod.nullObject2String(request.getAttribute("excelUrl"));

	
%>

<style type="text/css">
.small {
	width: 10px;
}


</style>


<fmt:bundle	basename="com/boco/eoms/partner/contract/config/applicationResource-partner-contract">
<logic-el:present name="ctContentsList">
	<display:table name="ctContentsList" cellspacing="0" cellpadding="0"
		id="ctContentsList" pagesize="${pageSize}"
		class="table ctContentsList" export="false"
		requestURI="${app}/partner/contract/ctContentss.do?method=search" sort="list"
		partialList="true" size="resultSize"
		decorator="com.boco.eoms.partner.contract.contents.displaytag.support.CtContentsDisplaytabDecorator">
		
<display:column media="html" title="${myTitleSelectBtn}">
			<!-- Values stored in checkbox is for batch deleting. -->
			<input type="checkbox" name="cardNumber"
				value="${ctContentsList.id}" />
		</display:column>
<!-- 
		<display:column sortable="true" headerClass="sortable" titleKey="ctContents.themeId">
			<eoms:id2nameDB id="${ctContentsList.themeId}" beanId="ctTableThemeDao" />
		</display:column>
 -->
		<display:column sortable="true" property="contractTitle" titleKey="ctContents.contentTitle" 
		    paramId="id" paramProperty="id"
			headerClass="sortable" />

		<display:column sortable="true" headerClass="sortable" titleKey="ctContents.createUser">
			<eoms:id2nameDB id="${ctContentsList.createUser}" beanId="tawSystemUserDao" />
		</display:column>

		<display:column sortable="true" headerClass="sortable" titleKey="ctContents.createDept">
			<eoms:id2nameDB id="${ctContentsList.createDept}" beanId="tawSystemDeptDao" />
		</display:column>

		<display:column sortable="true" property="createTime" titleKey="ctContents.createTime" format="{0,date,yyyy-MM-dd HH:mm:ss}"
		    paramId="id" paramProperty="id"
			headerClass="sortable" />

		<display:column sortable="true" headerClass="sortable" titleKey="ctContents.contentStatus">
			<eoms:dict key="dict-partner-contract" dictId="contractStatus" itemId="${ctContentsList.contractStatus}" beanId="id2nameXML" />
		</display:column>
<%--<display:column title="上传附件" headerClass="imageColumn">
			<a href="javascript:var id = '${ctContentsList.contentId }';
		                        var tableId = '${ctContentsList.tableId}';
		                        var themeId = '${ctContentsList.themeId}';
		                        var url='${app}/partner/contract/ctContentss.do?method=editFile';
		                        url = url + '&ID=' + id + '&TABLE_ID=' + tableId + '&THEME_ID=' + themeId;
		                        location.href=url" target="_blank">
				<img src="${app}/images/icons/icon1.gif"/> </a>
		</display:column>--%>
		<display:column title="查看" headerClass="imageColumn">

		<c:if test="${ctContentsList.contractStatus!='1'}">
			<a href="javascript:var id = '${ctContentsList.contentId }';
		                        var tableId = '${ctContentsList.tableId}';
		                        var themeId = '${ctContentsList.themeId}';
		                        var url='${app}/partner/contract/ctContentss.do?method=contractSaffDetail';
		                        url = url + '&ID=' + id + '&TABLE_ID=' + tableId + '&THEME_ID=' + themeId;
		                        location.href=url"  >
				<img src="${app}/images/icons/search.gif" /> </a>
				</c:if>
		</display:column>
		<display:setProperty name="paging.banner.item_name" value="ctContents" />
		<display:setProperty name="paging.banner.items_name" value="ctContentss" />

	</display:table>
	</logic-el:present>
</fmt:bundle>
<%@ include file="/common/footer_eoms.jsp"%>
