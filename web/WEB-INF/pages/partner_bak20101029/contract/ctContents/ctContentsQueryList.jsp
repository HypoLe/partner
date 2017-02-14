<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<jsp:directive.page import="com.boco.eoms.workbench.infopub.util.InfopubConstants" />
<style type="text/css">
.small {
	width:10px;
}
</style>
<script type="text/javascript">
	function choose(checkbox){
		eoms.util.checkAll(checkbox,'ids');
	}
	var checkflag = "false";
	function choose() {
		var objs = document.getElementsByName("ids");
		if(checkflag=="false"){
			for(var i=0; i<objs.length; i++) {
    			if(objs[i].type.toLowerCase() == "checkbox" )
      			objs[i].checked = true;
      			checkflag="true";
			}
		}else if(checkflag=="true"){
			for(var i=0; i<objs.length; i++) {
    			if(objs[i].type.toLowerCase() == "checkbox" )
      			objs[i].checked = false;
      			checkflag="false"
			}
		}
	}
	
	function createRequest()
	{
		var httpRequest = null;
		if(window.XMLHttpRequest){
	     httpRequest=new XMLHttpRequest();
	    }else if(window.ActiveXObject){
	     httpRequest=new ActiveXObject("MIcrosoft.XMLHttp");
	    }
	    return httpRequest;
	}

</script>

<content tag="heading">
</content>

<html:form action="/ctContentss.do?method=queryDo" method="post" styleId="ctContentsForm">
<fmt:bundle	basename="com/boco/eoms/partner/contract/config/applicationResource-partner-contract">

	<input type="hidden" name="status" id="status" />

	<display:table name="ctContentsList" cellspacing="0" cellpadding="0"
		id="ctContentsList" pagesize="${pageSize}" class="table ctContentsList"
		export="false"
		requestURI="${app}/partner/contract/ctContentss.do?method=search"
		sort="list" partialList="true" size="resultSize">

		<display:column sortable="true" headerClass="sortable" titleKey="ctContents.themeId">
			<eoms:id2nameDB id="${ctContentsList.themeId}" beanId="ctTableThemeDao" />
		</display:column>

		<display:column sortable="true" property="contractTitle" titleKey="ctContents.contentTitle"
			paramId="id" paramProperty="id" headerClass="sortable" />

		<display:column sortable="true" headerClass="sortable" titleKey="ctContents.createUser">
			<eoms:id2nameDB id="${ctContentsList.createUser}" beanId="tawSystemUserDao" />
		</display:column>

		<display:column sortable="true" headerClass="sortable" titleKey="ctContents.createDept">
			<eoms:id2nameDB id="${ctContentsList.createDept}" beanId="tawSystemDeptDao" />
		</display:column>

		<display:column sortable="true" property="createTime" titleKey="ctContents.createTime" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			paramId="id" paramProperty="id" headerClass="sortable" />

		<display:column sortable="false" property="contractNO" titleKey="ctContents.contentKeys"
			paramId="id" paramProperty="id" headerClass="sortable" />
		
		<display:column title="查看" headerClass="imageColumn">
		    <a href="javascript:var id = '${ctContentsList.id }';
		                        var tableId = '${ctContentsList.tableId}';
		                        var themeId = '${ctContentsList.themeId}';
		                        var url='${app}/partner/contract/ctContentss.do?method=detail';
		                        url = url + '&ID=' + id + '&TABLE_ID=' + tableId + '&THEME_ID=' + themeId;
		                        location.href=url"><img src="${app}/images/icons/search.gif"/></a>		    
		</display:column>	
		<display:setProperty name="paging.banner.item_name"  value="ctContents" />
		<display:setProperty name="paging.banner.items_name" value="ctContentss" />
	</display:table>

</fmt:bundle>
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>