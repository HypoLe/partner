<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<style type="text/css">
.small {
	width:10px;
}
</style>


<content tag="heading">
	订阅合同内容
</content>

<html:form action="/ctContentsSubscribes.do?method=searchSubscribe" method="post" styleId="ctContentsForm">
	<input type="hidden" name="status" id="status" />

	<display:table name="ctContentsList" cellspacing="0" cellpadding="0"
		id="ctContentsList" pagesize="${pageSize}" class="table ctContentsList"
		export="false"
		requestURI="${app}/partner/contract/ctContentsSubscribes.do?method=searchSubscribe"
		>

		<display:column property="contentTitle" title="合同标题" sortable="true"
			paramId="id" paramProperty="id" headerClass="sortable" />


		<display:column property="contentKeys" title="合同关键字" sortable="true"
			paramId="id" paramProperty="id" headerClass="sortable" />

		<display:column sortable="true" headerClass="sortable" title="创建人">
			<eoms:id2nameDB id="${ctContentsList.createUser}" beanId="tawSystemUserDao" />
		</display:column>

		<display:column title="查看" headerClass="imageColumn">
		    <a href="javascript:var id = '${ctContentsList.contentId }';
		                        var tableId = '${ctContentsList.tableId}';
		                        var themeId = '${ctContentsList.themeId}';
		                        var url='${app}/partner/contract/ctContentss.do?method=detail';
		                        url = url + '&ID=' + id + '&TABLE_ID=' + tableId + '&THEME_ID=' + themeId;
		                        location.href=url"><img src="${app}/images/icons/search.gif"/></a>		    
		</display:column> 
		
		<display:column title="收藏" headerClass="imageColumn">
		    <a href="javascript:if(confirm('确定要收藏该合同?')){
		                        var id = '${ctContentsList.contentId }';
		                        var tableId = '${ctContentsList.tableId}';
		                        var themeId = '${ctContentsList.themeId}';
		                        var url='${app}/partner/contract/ctContentsCollects.do?method=collect';
		                        url = url + '&ID=' + id + '&TABLE_ID=' + tableId + '&THEME_ID=' + themeId;
		                        location.href=url}"><img src="${app}/images/icons/save.gif"/></a>		    
		</display:column>

		<display:setProperty name="paging.banner.item_name"  value="ctContents" />
		<display:setProperty name="paging.banner.items_name" value="ctContentss" />
	</display:table>


</html:form>
<%@ include file="/common/footer_eoms.jsp"%>
