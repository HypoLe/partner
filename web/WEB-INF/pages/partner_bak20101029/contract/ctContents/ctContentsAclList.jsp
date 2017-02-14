<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page import="com.boco.eoms.common.util.StaticMethod"%>

<%
	String auditName = StaticMethod.nullObject2String(request.getAttribute("auditName"));
	String excelUrl = StaticMethod.nullObject2String(request.getAttribute("excelUrl"));
%>

<style type="text/css">
.small {
	width: 10px;
}
</style>

<script type="text/javascript">

<%	if(!"".equals(auditName)){ %>
		alert('该条记录已提交 <%=auditName%> 审核');
<%	} %>

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
	function recommend(tableId,id,isBest)
	{
		if(isBest=='1'){
			alert('已经是推荐合同！');
			return;
		}
		var url='${app}/partner/contract/ctContentss.do?method=recommend&tableId='+tableId+'&id='+id+'&isBest=1';
	 	var httpRequest = createRequest();
		if(httpRequest){
		     httpRequest.open("POST",url,true);
		     httpRequest.onreadystatechange=function()
		     {
		     	if(httpRequest.readyState==4)
			     	if(httpRequest.status==200){
			     		if(httpRequest.responseText=='fail'){
			     			alert('出现异常，推荐失败！');
			     			return;
			     		}
		                alert('推荐成功！');
					}	
		     }
		     httpRequest.send(null);
		}
	}
</script>

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

		<display:column sortable="true" headerClass="sortable" titleKey="ctContents.contentStatus">
			<eoms:dict key="dict-partner-contract" dictId="contractStatus" itemId="${ctContentsList.contractStatus}" beanId="id2nameXML" />
		</display:column>
		<display:column title="上传附件" headerClass="imageColumn">
			<a href="javascript:var id = '${ctContentsList.contentId }';
		                        var tableId = '${ctContentsList.tableId}';
		                        var themeId = '${ctContentsList.themeId}';
		                        var url='${app}/partner/contract/ctContentss.do?method=editFile';
		                        url = url + '&ID=' + id + '&TABLE_ID=' + tableId + '&THEME_ID=' + themeId;
		                        location.href=url" target="_blank">
				<img src="${app}/images/icons/icon1.gif"/> </a>
		</display:column>
		<display:column title="查看" headerClass="imageColumn">
			<a href="javascript:var id = '${ctContentsList.contentId }';
		                        var tableId = '${ctContentsList.tableId}';
		                        var themeId = '${ctContentsList.themeId}';
		                        var url='${app}/partner/contract/ctContentss.do?method=detail';
		                        url = url + '&ID=' + id + '&TABLE_ID=' + tableId + '&THEME_ID=' + themeId;
		                        location.href=url">
				<img src="${app}/images/icons/search.gif" /> </a>
		</display:column>
		<display:setProperty name="paging.banner.item_name" value="ctContents" />
		<display:setProperty name="paging.banner.items_name" value="ctContentss" />

	</display:table>
</fmt:bundle>


<%@ include file="/common/footer_eoms.jsp"%>
