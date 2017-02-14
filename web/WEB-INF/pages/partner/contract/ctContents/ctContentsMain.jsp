<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>


<style type="text/css">
.small {
	width: 10px;
}
</style>


<fmt:bundle	basename="com/boco/eoms/ct/config/applicationResource-partner/contract">

	<display:table name="ctContentsList" cellspacing="0" cellpadding="0"
		id="ctContentsList" pagesize="${pageSize}"
		class="table ctContentsList" export="false"
		requestURI="${app}/partner/contract/ctContentsMains.do?method=searchAll" 
		partialList="true" size="resultSize"
		decorator="com.boco.eoms.partner.contract.contents.displaytag.support.CtContentsDisplaytabDecorator">

		<display:column  titleKey="ctContents.themeId">
			<script>
				if("${ctContentsList.themeId}"==""||"${ctContentsList.themeId}"==null)
					document.write('<eoms:id2nameDB beanId="ctFileTreeDao" id="${ctContentsList.tableId}" />');
				else
					document.write('<eoms:id2nameDB id="${ctContentsList.tableId}" beanId="ctTableGeneralDao" />');
			</script>
		</display:column>
			

		<display:column  property="contentTitle" titleKey="ctContents.contentTitle" 
		    paramId="id" paramProperty="id"
			headerClass="sortable" />
			
		<display:column property="createTime" titleKey="ctContents.createTime" format="{0,date,yyyy-MM-dd HH:mm:ss}"
		    paramId="id" paramProperty="id"
			headerClass="sortable" />
		
		<display:column  titleKey="ctContents.createUser">
			<eoms:id2nameDB id="${ctContentsList.createUser}" beanId="tawSystemUserDao" />
		</display:column>
		
		<display:column  titleKey="ctContents.createDept">
			<eoms:id2nameDB id="${ctContentsList.createDept}" beanId="tawSystemDeptDao" />
		</display:column>
		
		<display:column title="查看" headerClass="imageColumn">
			<a href="javascript:var id = '${ctContentsList.id }';
		                        var tableId = '${ctContentsList.tableId}';
		                        var themeId = '${ctContentsList.themeId}';
		                        var url;
		                        if(themeId==''||themeId==null)
		                        {
			                        url='${app}/partner/contract/files.do?method=detail&id='+id;
		                        }
		                        else
		                        {
		                        	url='${app}/partner/contract/ctContentss.do?method=detail';
			                        url = url + '&ID=' + id + '&TABLE_ID=' + tableId + '&THEME_ID=' + themeId;
		                        }
		                        location.href=url">
				<img src="${app}/images/icons/search.gif" /> </a>
		</display:column>

		
		<display:setProperty name="paging.banner.item_name" value="ctContents" />
		<display:setProperty name="paging.banner.items_name" value="ctContentss" />

	</display:table>
</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>
