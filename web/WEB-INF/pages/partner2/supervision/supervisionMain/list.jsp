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
<form action="${URI }?method=list" method="post">
	
	<input type="hidden" value="${privilege }" name="privilege" />

	<table id="sheet" class="formTable">
		<tr>
			<td class="label">督办项业务类型</td>
			<td ><select  name='myTypeStringEqual' id='myTypeStringEqual' class='select'>
				<option value='' selected="selected">请选择</option>
				<option value='sheet'>故障工单</option>
				<option value='work'>作业计划</option>
				<option value='danger'>隐患</option>
				<option value='monitor'>盯防</option>
			</select></td>
			<td class="label">超时类型</td>
			<td ><select  name='listType' id='listType' class='select'>
				<option value='' selected="selected">请选择</option>
				<option value='onTime' >即将超时未完成</option>
				<option value='overTime'>已经超时未完成</option>
				<option value='onTimeFinish'>按时完成</option>
				<option value='overTimeFinish'>超时完成</option>
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
			<input type="checkbox" name="cardNumber" value="${supervisionMainList.id}" id="${supervisionMainList.id}" />
		</display:column>
		<display:column sortable="false" property="myTitle" title="督办项名称"></display:column>
		<display:column sortable="false"   title="督办项设置创建人">
			<eoms:id2nameDB id="${supervisionMainList.createUserId}"
				beanId="tawSystemUserDao" />
		</display:column>
		<display:column sortable="false" property="createDate" title="督办项设置创建时间"></display:column>
		<display:column sortable="false" property="onDateTime" title="即将超时时限"></display:column>
		<display:column sortable="false" property="overDateTime" title="超时时限"></display:column>
		<display:column sortable="false"  title="详情">
			<a id="${supervisionMainList.id }"
				href="${URI }?method=showDetail&id=${supervisionMainList.id}"
				target="_blank" shape="rect">详情</a>
		</display:column>
	</display:table>


<!-- 如果不是自己权限的，那么不显示所有待处理框体，开始 -->

<c:if test="${privilege == 'default' }">
<form action="${URI }?method=deal" method="post" id="patchDealForm" >

<div class="x-layout-panel-hd" style="border:1px solid #98c0f4;padding:5px;width:98%;margin-top: 15px;" >督办项处理</div>
<div style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;width:98%">
	<table class="formTable" >
		<tr>
			<td class="label" >督办项完成情况简述</td>
			<td class="content" colspan="3">
					<textarea  class="textarea max" alt="allowBlank:false"></textarea>	
			</td>
		</tr>
		<tr>
			<td class="label">督办项完成情况相关附件</td>
				<td class="content" colspan="3">
					<eoms:attachment name="sheetAccessories" scope="request" idField="sheetAccessories" appCode="partner2" alt="allowBlank:false" /></td>
				</td>
		</tr>
	</table>
</div>

<div style="margin-top: 10px">
	
		<input type="button" class="btn" id="goToDeleteAll" onclick="dealAll();" value="批量处理督办项" />
		<!-- 
		<input type="button" class="btn" id="goToDeleteAll" onclick="deleteAll();" value="批量删除" />
		 -->
	
</div>
<input type="hidden" name="dealIds" id="dealIds" />
</form>

<form action="${URI }?method=deleteAll&listType=list" method="post" id="patchDeleteForm">
	<!-- This hidden area is for batch deleting. -->
	<input type="hidden" name="deleteIds" id="deleteIds" />
</form>

</c:if>
<!-- 如果不是自己权限的，那么不显示所有待处理框体，结束 -->

</logic:present>

<logic:notPresent name="${listName }" scope="request">
	无记录
</logic:notPresent>

<script type="text/javascript">
var myJ = jQuery.noConflict();
myJ(function() {
	
	//初始化查询参数开始
	myJ('select#listType').val('${listType}');
	
	//初始化查询参数结束
	
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
	if (idResult == "") {
		alert("请选择需要处理的记录");
		return false;
	} else {
		//如果只有1条记录的话，那么直接弹出隐藏域进行处理，如果有多条记录的话，则转入批处理页面
			if(confirm("确定处理记录吗？")){
				$("dealIds").value=idResult.toString();
				myJ('form#patchDealForm').submit();
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
			$("deleteIds").value=idResult.toString();
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
