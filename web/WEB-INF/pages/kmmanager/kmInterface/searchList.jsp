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
</script>

<content tag="heading">
</content>

<html:form action="/kmContentss.do?method=queryDo" method="post" styleId="kmContentsForm">
	<input type="hidden" name="status" id="status" />

	<display:table name="kmContentsList" cellspacing="0" cellpadding="0"
		id="kmContentsList" pagesize="${pageSize}" class="table kmContentsList"
		export="false"
		requestURI="${app}/kmmanager/kmContentss.do?method=search"
		sort="list" partialList="true" size="resultSize">

		<display:column property="contentTitle" title="知识标题" sortable="false"
			paramId="id" paramProperty="id" headerClass="sortable" />

		<display:column property="createUser" title="创建人" sortable="false"
			paramId="id" paramProperty="id" headerClass="sortable" />

		<display:column property="createDept" title="创建部门" sortable="false"
			paramId="id" paramProperty="id" headerClass="sortable" />

		<display:column property="contentKeys" title="知识关键字" sortable="false"
			paramId="id" paramProperty="id" headerClass="sortable" />
		
		<display:column title="查看" headerClass="imageColumn">
		    <a href="javascript:var id = '${kmContentsList.id }';
		                        var tableId = '${kmContentsList.tableId}';
		                        var themeId = '${kmContentsList.themeId}';
		                        var url='${app}/kmmanager/kmContentss.do?method=detail';
		                        url = url + '&ID=' + id + '&TABLE_ID=' + tableId + '&THEME_ID=' + themeId;
		                        location.href=url"><img src="${app}/images/icons/search.gif"/></a>		    
		</display:column>

		<display:setProperty name="paging.banner.item_name"  value="kmContents" />
		<display:setProperty name="paging.banner.items_name" value="kmContentss" />
	</display:table>


</html:form>
<%@ include file="/common/footer_eoms.jsp"%>
