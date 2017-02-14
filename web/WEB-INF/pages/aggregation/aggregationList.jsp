</table><%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<style type="text/css">
.small {
	width: 10px;
}

</style>

<script type="text/javascript">
Ext.onReady(function() {
	
	});
	function openImport(handler){
	var el = Ext.get('listQueryObject');
	if(el.isVisible()){
		el.slideOut('t',{useDisplay:true});
		handler.innerHTML = "打开查询界面";
	}
	else{
		el.slideIn();
		handler.innerHTML = "关闭查询界面";
	}
}
function remove() {
	if (confirm('您是否要删除该信息?')) {
		return true;
	} else {
		return false;
	}
}
function deleteAll() {
	var cardNumberList = document.getElementsByName("cardNumber");
	var idResult = "";
	for (i = 0 ; i < cardNumberList.length; i ++) {
		if (cardNumberList[i].checked) {
			var myTempStr=cardNumberList[i].value;
			idResult+=myTempStr.toString()+";"
		}
	}
	if (idResult == "") {
		alert("请选择需要删除的记录");
		return false;
	} else {
		if(confirm("确定要删除所选的记录吗？")){
			document.getElementById("deleteIds").value=idResult.toString();
			//var myParam=idResult.toString();
			var formObj = document.getElementById("deleteAllForm");
 			formObj.submit();
		}
		else{
			return false;
		}
	}
}
function selectAll(){
	var cardNumberList = document.getElementsByName("cardNumber");
	var temp=cardNumberList[0].checked;
	if(temp==null){
		for (i = 0 ; i < cardNumberList.length; i ++) {
		cardNumberList[i].checked='checked';
		}
	}else{
		for (i = 0 ; i < cardNumberList.length; i ++) {
		cardNumberList[i].checked=!cardNumberList[i].checked;
		}
	}
}
function returnBack() {
	window.history.back();
}
</script>
<table  class="formTable">
<caption>
        <div class="header center">聚合资源列表</div>
</caption>
</table>
<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">查询</span>
</div>

<div id="listQueryObject" 
	 style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">

	<content tag="heading">
	<c:out value="请输入查询条件" />
	</content>
	<form action="aggregation.do?method=list" method="post">
		<table id="sheet" class="formTable">
			<tr>
			<td class="label">模块名称*</td>
			<td class="content"><input class="text" type="text"
				name="moduleNameStringLike" id="moduleName" value="${moduleNameStringLike}"/></td>
			<td class="label">模块URL*</td>
			<td class="content" colspan="3"><input class="text" type="text"
				name="moduleUrlStringLike" id="moduleUrl" value="${moduleUrlStringLike}" /></td>
		</tr>
			
	</table>
	
		<input type="submit" class="btn" value="查询">
			
	</form>
	
	</div>




<c:set var="myTitleSelectBtn">
	<input type="checkbox" name="myTitleSelect" onclick="selectAll();" />
</c:set>
 <!-- Information hints area end-->
 <logic:present
	name="aggregationList" scope="request">
	<display:table name="aggregationList" cellspacing="0" cellpadding="0"
		id="aggregationList" pagesize="${pagesize}"
		class="table aggregationList" 
		requestURI="aggregation.do" sort="list" partialList="true"
		size="${size}">
		<display:column media="html" title="${myTitleSelectBtn}">
			<!-- Values stored in checkbox is for batch deleting. -->
			<input type="checkbox" name="cardNumber"
				value="${aggregationList.id}" />
		</display:column>
		<display:column  headerClass="sortable" title="模块名称">
		${aggregationList.moduleName}
		</display:column>
		<display:column  headerClass="sortable" title="模块连接">
			${aggregationList.moduleUrl}
		</display:column>
		<display:column  headerClass="sortable" title="备注">
			${aggregationList.remark}
		</display:column>
		<display:column  headerClass="sortable" title="编辑"
		media="html">
			<a id="${aggregationList.id }"
				href="${app }/aggregation/aggregation.do?method=goToEdit&id=${aggregationList.id}"
				shape="rect"> <img
				src="${app }/images/icons/edit.gif"> </a>
		</display:column>
		<display:column sortable="false" headerClass="sortable" title="查看"
		media="html">
			<a id="${aggregationList.id }"
				href="${app }/aggregation/aggregation.do?method=goToDetail&id=${aggregationList.id}"
				shape="rect"> <img
				src="${app }/images/icons/edit.gif"> </a>
		</display:column>
		<%-- 
		<display:column sortable="false" headerClass="sortable" title="删除"
		media="html">
			<a id="${aggregationList.id }"
				href="${app }/portal/aggregation/aggregation.do?method=delete&id=${aggregationList.id}"
				shape="rect" onclick="remove();"> <img
				src="${app }/images/icons/edit.gif"> </a>
		</display:column>
		 --%>
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:present></div>
</div>

<table  class="listTable" >
<form id="deleteAllForm" action="aggregation.do?method=deleteMyAll" method="post">
<tr>
<td>
	<!-- This hidden area is for batch deleting. -->
	<input type="hidden" name="deleteIds" id="deleteIds" />
	<input type="button" 
			onclick="return deleteAll();" value="删除" class="btn" /> 
			</td>
			</td>
			</tr>
</form>
</table>



<%@ include file="/common/footer_eoms.jsp"%>
