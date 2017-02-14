<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page import="com.boco.eoms.common.util.StaticMethod"%>

<fmt:bundle	basename="com/boco/eoms/partner/contract/config/applicationResource-partner-contract">

	<display:table name="ctContentsList" cellspacing="0" cellpadding="0"
		id="ctContentsList" pagesize="${pageSize}"
		class="table ctContentsList" export="false"
		requestURI="${app}/partner/contract/ctContentss.do?method=search" sort="list"
		partialList="true" size="resultSize"
		decorator="com.boco.eoms.partner.contract.contents.displaytag.support.CtContentsDisplaytabDecorator">

		<display:column sortable="true" headerClass="sortable" titleKey="ctContents.themeId">
			<eoms:id2nameDB id="${ctContentsList.themeId}" beanId="ctTableThemeDao" />
		</display:column>

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

		<display:column sortable="true" headerClass="sortable" title="状态">
			<eoms:dict key="dict-partner-contract" dictId="contractStatus" itemId="${ctContentsList.contractStatus}" beanId="id2nameXML" />
		</display:column>

		<display:column title="查看" headerClass="imageColumn">
			<a href="javascript:var id = '${ctContentsList.contentId }';
		                        var url='${app}/partner/contract/ctContentss.do?method=detailAgr&id='+ id;
		                        location.href=url" target="_blank">
				<img src="${app}/images/icons/search.gif" /> </a>
		</display:column>

		<display:setProperty name="paging.banner.item_name" value="ctContents" />
		<display:setProperty name="paging.banner.items_name" value="ctContentss" />

	</display:table>
</fmt:bundle>


<%@ include file="/common/footer_eoms.jsp"%>
