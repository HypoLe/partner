<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
	
<fmt:bundle	basename="com/boco/eoms/partner/contract/config/applicationResource-partner-contract">
<table class="formTable">
<caption>
		<div class="header center"><fmt:message key="ctContents.draft" />
		</div>
</caption>
</table>
    <display:table name="ctContentsDraftList" cellspacing="0" cellpadding="0"
		id="ctContentsDraftList" pagesize="${pageSize}" class="table ctContentsDraftList"
		export="false"
		requestURI="${app}/partner/contract/ctContentss.do?method=ctDraft"
		sort="list" partialList="true" size="resultSize">
		
		<display:column sortable="true" headerClass="sortable" titleKey="ctContents.themeId">
			<eoms:id2nameDB id="${ctContentsDraftList.themeId}" beanId="ctTableThemeDao" />
		</display:column>

		<display:column sortable="false" property="contractNO" titleKey="ctContents.contentKeys"
			paramId="id" paramProperty="id" headerClass="sortable" />

		<display:column sortable="true" property="contractTitle" titleKey="ctContents.contentTitle"
			paramId="id" paramProperty="id" headerClass="sortable" />

		<display:column sortable="true" headerClass="sortable" titleKey="ctContents.createUser">
			<eoms:id2nameDB id="${ctContentsDraftList.createUser}" beanId="tawSystemUserDao" />
		</display:column>

		<display:column sortable="true" headerClass="sortable" titleKey="ctContents.createDept">
			<eoms:id2nameDB id="${createDept}" beanId="tawSystemDeptDao" />
		</display:column>

		<display:column sortable="true" property="createTime" titleKey="ctContents.createTime" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			paramId="id" paramProperty="id" headerClass="sortable" />

		<display:column title="查看" headerClass="imageColumn">
		    <a href="javascript:var id = '${ctContentsDraftList.contentId }';
		                        var tableId = '${ctContentsDraftList.tableId}';
		                        var themeId = '${ctContentsDraftList.themeId}';
		                        var url='${app}/partner/contract/ctContentss.do?method=detailDraft';
		                        url = url + '&ID=' + id + '&TABLE_ID=' + tableId + '&THEME_ID=' + themeId;
		                        location.href=url">
		       <img src="${app}/images/icons/search.gif"/></a>		    
		</display:column> 
		</display:table>
</fmt:bundle>
<%@ include file="/common/footer_eoms.jsp"%>