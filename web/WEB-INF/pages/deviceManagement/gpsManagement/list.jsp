<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript">
 var myjs=jQuery.noConflict();
	function goadd(){
		window.open("${app}/gpsmanagement/gpsManagement.do?method=goToAddPage");
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
		if(confirm("确定要全部删除吗？")){
			$("deleteIds").value=idResult.toString();
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
</script>
<form action="gpsManagement.do?method=list" method="post">
		<fieldset>
			<legend>快速查询</legend>
			<table id="sheet" class="formTable">
				<tr>
					<td class="label">GSM号码:</td>
					<td>
						<input type="text" class="text" name="gsmidStringLike"/>
					</td>
					
					<td class="label">
						终端设备类型:
					</td>
					<td>
						<eoms:comboBox name="gpstypeStringEqual" id="gpstypeStringEqual"  initDicId="12103" defaultValue="" />
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<div align="left">
							<input type="submit" class="btn" value="查询" />
						</div>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
<c:set var="myTitleSelectBtn">
	<input type="checkbox" name="myTitleSelect" onclick="selectAll();" />
</c:set>

<logic:present name="gpsManagementList" scope="request">
	<display:table name="gpsManagementList" cellspacing="0" cellpadding="0"
		id="gpsManagementList" pagesize="${pagesize}"
		class="table gpsManagementList" export="false"
		requestURI="gpsManagement.do" sort="external"
		partialList="true" size="${size}">
		<display:column media="html" title="${myTitleSelectBtn}">
			<!-- Values stored in checkbox is for batch deleting. -->
			<input type="checkbox" name="cardNumber"
				value="${gpsManagementList.id}" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="GSM号码">
			${gpsManagementList.gsmid}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="终端设备类型">
			<eoms:id2nameDB id="${gpsManagementList.gpstype}" beanId="ItawSystemDictTypeDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="生产厂家">
			${gpsManagementList.factory}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="型号">
			${gpsManagementList.gpsmodel}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="机身序列号">
			${gpsManagementList.gpsselfnumber}
		</display:column>
		
		<display:column sortable="false" headerClass="sortable" title="编辑"
			 media="html">
			<a id="${gpsManagementList.id }"
				href="${app }/gpsmanagement/gpsManagement.do?method=goToEditPage&id=${gpsManagementList.id}"
				>
				<img src="${app }/images/icons/edit.gif">
				</a>
		</display:column>
		
		<display:column sortable="false" headerClass="sortable"
			title="详情" media="html">
			<a id="${faultSheetList.id }"
				href="${app }/gpsmanagement/gpsManagement.do?method=goToDetailPage&id=${gpsManagementList.id}"
				>
				<img src="${app }/images/icons/table.gif">
				</a>
		</display:column>		
		</display:table>
		</logic:present>
		<input type="button" name="addGps" id="addGps" value="新增设备信息" onclick="goadd();"/> 
		<input type="button" name="deleteGps" id="deleteGps" value="删除" onclick="deleteAll();"/> 
<form id="deleteAllForm" action="gpsManagement.do?method=delete" method="post">
	<!-- This hidden area is for batch deleting. -->
	<input type="hidden" name="deleteIds" id="deleteIds" />
</form>
<%@ include file="/common/footer_eoms.jsp"%>