<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>

<c:set var="myTitleSelectBtn">
	<input type="checkbox" name="myTitleSelect" onclick="selectAll();" />
</c:set>

<div style="color: red;font: bold;"><c:out value="${msg}"></c:out></div>

<div id="searchDiv" class="x-layout-panel-hd" style="cursor:pointer;border: 1px solid rgb(152, 192, 244); padding: 5px; width: 98%;">快速查询</div>
<div id="searchForm" style="display:none;border-style: none solid solid; border-color:rgb(152, 192, 244); border-width: 0pt 1px 1px; padding: 5px; background-color: rgb(239, 246, 255); width: 98%;">
<form action="${URI }?method=list&listType=listOfCreateUser" method="post">
	<table id="sheet" class="formTable">
		<tr>
			<td class="label">年度</td>
			<td class="content"><select size='1' style="width:95%"
				name='yearFlagStringEqual' id='yearFlagStringEqual'
				class='select'>
				<option value=''>请选择</option>
				<option value='2008'>2008年</option>
				<option value='2009'>2009年</option>
				<option value='2010'>2010年</option>
				<option value='2011'>2011年</option>
				<option value='2012'>2012年</option>
				<option value='2013'>2013年</option>
				<option value='2014'>2014年</option>
			</select></td>
			<td class="label">月度</td>
			<td class="content"><select size='1' style="width:95%"
				name='monthFlagStringEqual' id='monthFlagStringEqual'
				class='select'>
				<option value=''>请选择</option>
				<option value='1'>1</option>
				<option value='2'>2</option>
				<option value='3'>3</option>
				<option value='4'>4</option>
				<option value='5'>5</option>
				<option value='6'>6</option>
				<option value='7'>7</option>
				<option value='8'>8</option>
				<option value='9'>9</option>
				<option value='10'>10</option>
				<option value='11'>11</option>
				<option value='12'>12</option>
			</select></td>
		</tr>
		<tr>
			<td colspan='4' align='center' class='label'>
			<html:submit styleClass="btn" property="method.send"
		styleId="method.save" value="提交查询结果"></html:submit>
		</td>
		</tr>
	</table>
</form>
</div>


<!-- Information hints area end-->
<logic:present name="${listName }" scope="request">
	<display:table name="${listName }" cellspacing="0" cellpadding="0"
		id="${listName }" pagesize="${pagesize }" class="table ${listName }"
		export="false" requestURI="${URI }?method=list"
		sort="list" partialList="true" size="${size }">
		<display:column media="html" title="${myTitleSelectBtn}">
			<!-- Values stored in checkbox is for batch deleting. -->
			<input type="checkbox" name="cardNumber" value="${supervisionConfigList.id}" id="${supervisionConfigList.id}" />
		</display:column>
			<display:column sortable="false" property="configName" title="督办项设置名称"></display:column>
		<display:column sortable="false"   title="督办项设置创建人">
			<eoms:id2nameDB id="${supervisionConfigList.createUserId}"
				beanId="tawSystemUserDao" />
		</display:column>
	
		<display:column sortable="false" property="createDate" title="督办项设置创建时间"></display:column>
		
		<display:column sortable="false"  title="详情">
			<a id="${supervisionConfigList.id }" href="${URI }?method=goToDetail&id=${supervisionConfigList.id}" >详情</a>
		</display:column>
		
		<display:column sortable="false" title="编辑" media="html">
			<a id="${supervisionConfigList.id }" href="${URI }?method=goToEdit&id=${supervisionConfigList.id}" >编辑</a>
		</display:column>
		
		<display:column sortable="false" title="删除" media="html">
			<a id="${supervisionConfigList.id }" href="${URI }?method=delete&id=${supervisionConfigList.id}" >删除</a>
		</display:column>
		
	</display:table>
	<div style="margin-top: 10px">
	<input type="button" class="btn" id="goToDeleteAll" onclick="deleteAll();" value="批量删除" />
	<input type="hidden" name="isRelated" value="${isRelated}"/>
	<div style="display: none;" id="relateBaseStation">
		<input type="button" class="btn" id="addBsToContract" value="确认关联" />
	</div>
	</div>
</logic:present>

<logic:notPresent name="${listName }" scope="request">
	无记录
</logic:notPresent>

<form action="${URI }?method=delete" method="post" id="patchDeleteForm">
	<!-- This hidden area is for batch deleting. -->
	<input type="hidden" name="id" id="id" />
</form>

<script type="text/javascript">
var myJ = jQuery.noConflict();
myJ(function() {
	
	
	myJ('div#searchDiv').bind('click',function(event){
		myJ('#searchForm').toggle();
	});
	
	myJ('input[type=button]').bind('click',function(event){
		var buttonId= event.target.id;
		if(buttonId == 'goToAddButton'){
			//myJ(this).attr('disabled','disabled');
			location.href = '${app}/partner2/baseStation/baseStationMain.do?method=goToAdd';
		}else if(buttonId=='goToImportExcelButton'){
			location.href = '${app}/partner2/baseStation/baseStationMain.do?method=goToImportExcel';
		}else if(buttonId=='goToExport'){
			location.href = '${app}/partner/feeInfo/pnrFeeInfoMains.do?method=exportProxyExcel';
		}else{
			//Do nothing.
		}
	});
	
});



function dealAll() {
	var cardNumberList = document.getElementsByName("cardNumber");
	var idResult = "";
	var calc=0;
	for (i = 0 ; i < cardNumberList.length; i ++) {
		if (cardNumberList[i].checked) {
			var myTempStr=cardNumberList[i].value;
			idResult+=myTempStr.toString()+",";
			calc++;
		}
	}
	var currentTaskOwner = document.getElementsByName("currentTaskOwner");
	if(!currentTaskOwner[0] || currentTaskOwner[0].value ==''){
		alert("请选择派发对象");
		return false;
	}
	if (idResult == "") {
		alert("请选择需要处理的记录");
		return false;
	} else {
		//如果只有1条记录的话，那么直接弹出隐藏域进行处理，如果有多条记录的话，则转入批处理页面
			if(confirm("确定处理记录吗？")){
				console.log(idResult.toString());
				$("id").value=idResult.toString();
				document.forms[1].submit();
			}else{
				return false;
			}
		}
	}

function deleteAll() {
	var cardNumberList = document.getElementsByName("cardNumber");
	var idResult = "";
	for (i = 0 ; i < cardNumberList.length; i ++) {
		if (cardNumberList[i].checked) {
			var myTempStr=cardNumberList[i].value;
			idResult+=myTempStr.toString()+","
		}
	}
	if (idResult == "") {
		alert("请选择需要删除的记录");
		return false;
	} else {
		if(confirm("确定要全部删除吗？")){
			myJ('input#id').val(idResult.toString());
			myJ('form#patchDeleteForm').submit();
		}
		else{
			return false;
		}
	}
}

function selectAll(){
	var cardNumberList = document.getElementsByName("cardNumber");
	//Judge whether it has been checked first. One element is enough for our decision.
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
	myJ("input:disabled").removeAttr("checked");
}

function goToHistory(obj){
    var myId=obj.id;
	window.open('${app}/duty/circuitLength.do?method=goToHistory&id=myId',null,'left=0,top=0,height=600,width=1000,scrollbars=yes,resizable=yes')
}

function jumpUrl(){
	location.href='<html:rewrite page='/circuitLength.do?method=goToAdd'/>';
}
</script>

<%@ include file="/common/footer_eoms.jsp"%>
