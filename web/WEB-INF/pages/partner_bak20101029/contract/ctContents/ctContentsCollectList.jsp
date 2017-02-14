<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<style type="text/css">
.small {
	width: 10px;
}
</style>

<fmt:bundle	basename="com/boco/eoms/partner/contract/config/applicationResource-partner-contract">

    <caption>
		<div class="header center"><b><fmt:message key="ctContents.form.heading"/>&nbsp;收藏</b></div>
	</caption>

	<display:table name="ctContentsCollectList" cellspacing="0"
		cellpadding="0" id="ctContentsList" pagesize="${pageSize}"
		class="table ctContentsCollectList" export="false"
		requestURI="${app}/partner/contract/ctContentsCollects.do">

		<display:column sortable="true" headerClass="sortable" titleKey="ctContents.themeId">
			<eoms:id2nameDB id="${ctContentsList.themeId}" beanId="ctTableThemeDao" />
		</display:column>
		
		<display:column sortable="true" property="contentTitle" titleKey="ctContents.contentTitle" 
		    paramId="id" paramProperty="id"
			headerClass="sortable" />

		<display:column sortable="true" headerClass="sortable" titleKey="ctContents.createUser">
			<eoms:id2nameDB id="${ctContentsList.createUser}" beanId="tawSystemUserDao" />
		</display:column>

		<display:column sortable="false" property="contentKeys" titleKey="ctContents.contentKeys" 
		    paramId="id" paramProperty="id"
			headerClass="sortable" />

		<display:column title="查看" headerClass="imageColumn">
			<a href="javascript:var id = '${ctContentsList.contentId }';
		                        var tableId = '${ctContentsList.tableId}';
		                        var themeId = '${ctContentsList.themeId}';
		                        var url='${app}/partner/contract/ctContentss.do?method=detail';
		                        url = url + '&ID=' + id + '&TABLE_ID=' + tableId + '&THEME_ID=' + themeId;
		                        location.href=url">
				<img src="${app}/images/icons/search.gif" />
			</a>
		</display:column>
	
		<display:column title="删除" headerClass="imageColumn">
			<a href="javascript:if(confirm('确定要删除该收藏?')){
		                        var id = '${ctContentsList.contentId }';
		                        var tableId = '${ctContentsList.tableId}';
		                        var themeId = '${ctContentsList.themeId}';
		                        var url='${app}/partner/contract/ctContentsCollects.do?method=remove';
		                        url = url + '&ID=' + id + '&TABLE_ID=' + tableId + '&THEME_ID=' + themeId;
		                        location.href=url}">
		                        
				<img src="${app}/images/icons/list-delete.gif" />
			</a>
		</display:column>

		<display:setProperty name="paging.banner.item_name"  value="ctContents" />
		<display:setProperty name="paging.banner.items_name" value="ctContentss" />

	</display:table>
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>
