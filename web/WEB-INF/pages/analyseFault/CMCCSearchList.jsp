<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript">

Ext.onReady(function(){
            v = new eoms.form.Validation({form:'lineForm'});
            });
function dealAll() {
	var cardNumberList = document.getElementsByName("cardNumber");
	var idResult = "";
	var calc=0;
	for (i = 0 ; i < cardNumberList.length; i ++) {
		if (cardNumberList[i].checked) {
			var myTempStr=cardNumberList[i].value;
			idResult+=myTempStr.toString()+";";
			calc++;
		}
	}
	if (idResult == "") {
		alert("请选择需要处理的记录");
		return false;
	} else {
		//如果只有1条记录的话，那么直接弹出隐藏域进行处理，如果有多条记录的话，则转入批处理页面
		if(calc==1){
			if(confirm("确定处理记录吗？")){
				$("dealIds").value=idResult.toString();
				document.forms[0].submit();
			}else{
				return false;
			}
		}else{
			if(confirm("确定批量处理记录吗？")){
				$("dealIds").value=idResult.toString();
				document.forms[0].submit();
			}else{
				return false;
			}
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
}
</script>
<c:if test="${type ne 1&&type ne 7&&type ne 5}">

<eoms:xbox id="ownerUser" dataUrl="${app}/xtree.do?method=userFromDept"  
		rootId="-1" rootText="对象"  valueField="checkUser" handler="checkUserText" 
		textField="checkUserText" checktype="user" single="true" />
		</c:if>
<c:set var="myTitleSelectBtn">
	<input type="checkbox" name="myTitleSelect" onclick="selectAll();" />
</c:set>
<logic:present name="analyseList" scope="request">
	<display:table name="analyseList" cellspacing="0" cellpadding="0"
		id="analyseList" pagesize="${pagesize}" class="table checkList" export="false"
		requestURI="/authorities/analyseFault.do?method=cmccSearch&type=${type-1}" sort="list"
		partialList="true" size="${size}">
			<c:if test="${type ne 7}">
		<display:column media="html" title="${myTitleSelectBtn}">
			<!-- Values stored in checkbox is for batch deleting. -->
			<input type="checkbox" name="cardNumber" value="${analyseList.id}" />
		</display:column>
		</c:if>
			<display:column sortable="true" headerClass="sortable" title="故障ID">
			<a href="${app}/authorities/analyseFault.do?method=detailAnalyse&id=${analyseList.id}">${analyseList.faultID}</a>
				
		</display:column>
		
		<display:column property="startDate" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" title="故障开始时间" />
		<display:column property="endDate" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}"
			headerClass="sortable" title="故障结束时间" />
			<display:column property="analyseText" sortable="true"
			headerClass="sortable" title="分析报告" />

	</display:table>
</logic:present>
<c:if test="${type eq 1}">
<form action="analyseFault.do?method=analyseApprove" method="post" name="lineForm" id="lineForm">
<table id="sheet" class="formTable">
	<input type="hidden" name="dealIds" id="dealIds" />
	<input type="hidden" name="type" value="${type}" />
	<tr>
			<td class="label">操作类型</td>
			<td class="content"><select id="operateType" name="operateType">
				<option value='2' selected="selected">通过</option>
				<option value='0'>驳回</option>
			</select></td>
		</tr>
	</table>
	
<form>
<c:set var="submitBtn">
	<input type="button" style="margin-right: 5px" onclick="dealAll();"
		value="提交" />
</c:set>
<c:out value="${submitBtn}" escapeXml="false" />
</c:if>



<c:if test="${type eq 2}">
<form action="analyseFault.do?method=analyseApprove" method="post" name="lineForm" id="lineForm">
<table id="sheet" class="formTable">
	<input type="hidden" name="dealIds" id="dealIds" />
	<input type="hidden" name="type" value="${type}" />
	
		<tr>
				<td class="label">地市公司审核人</td>
				<td ><input class="text" type="text" name="checkUserText"
					id="checkUserText" alt="allowBlank:false" />
		<input type="hidden" name="checkUser" id="checkUser"/>	</td>
	</tr>
	</table>
	
<form>
<c:set var="submitBtn">
	<input type="button" style="margin-right: 5px" onclick="dealAll();"
		value="提交" />
</c:set>
<c:out value="${submitBtn}" escapeXml="false" />
</c:if>

<c:if test="${type eq 3}">
<form action="analyseFault.do?method=analyseApprove" method="post" name="lineForm" id="lineForm">
<table id="sheet" class="formTable">
	<input type="hidden" name="dealIds" id="dealIds" />
	<input type="hidden" name="type" value="${type}" />
	<tr>
			<td class="label">操作类型</td>
			<td class="content"><select id="operateType" name="operateType">
				<option value='4' selected="selected">通过</option>
				<option value='2'>驳回</option>
			</select></td>
		</tr>
		<tr>
				<td class="label">地市联通公司审核人</td>
				<td ><input class="text" type="text" name="checkUserText"
					id="checkUserText" alt="allowBlank:false" />
		<input type="hidden" name="checkUser" id="checkUser"/>	</td>
	</tr>
	</table>
	
<form>
<c:set var="submitBtn">
	<input type="button" style="margin-right: 5px" onclick="dealAll();"
		value="提交" />
</c:set>
<c:out value="${submitBtn}" escapeXml="false" />
</c:if>

<c:if test="${type eq 4}">
<form action="analyseFault.do?method=analyseApprove" method="post" name="lineForm" id="lineForm">
<table id="sheet" class="formTable">
	<input type="hidden" name="dealIds" id="dealIds" />
	<input type="hidden" name="type" value="${type}" />
	<tr>
			<td class="label">操作类型</td>
			<td class="content"><select id="operateType" name="operateType">
				<option value='5' selected="selected">通过</option>
				<option value='3'>驳回</option>
			</select></td>
		</tr>
		<tr>
				<td class="label">抄送省联通</td>
				<td ><input class="text" type="text" name="checkUserText"
					id="checkUserText" />
		<input type="hidden" name="checkUser" id="checkUser"/>	</td>
	</tr>
	</table>
	
<form>
<c:set var="submitBtn">
	<input type="button" style="margin-right: 5px" onclick="dealAll();"
		value="提交" />
</c:set>
<c:out value="${submitBtn}" escapeXml="false" />
</c:if>


<c:if test="${type eq 5}">
<form action="analyseFault.do?method=analyseApprove" method="post" name="lineForm" id="lineForm">
<table id="sheet" class="formTable">
	<input type="hidden" name="dealIds" id="dealIds" />
	<input type="hidden" name="type" value="${type}" />
	</table>
	
<form>
<c:set var="submitBtn">
	<input type="button" style="margin-right: 5px" onclick="dealAll();"
		value="归档" />
</c:set>
<c:out value="${submitBtn}" escapeXml="false" />
</c:if>

<c:if test="${type eq 6}">
<form action="analyseFault.do?method=analyseApprove" method="post" name="lineForm" id="lineForm">
<table id="sheet" class="formTable">
	<input type="hidden" name="dealIds" id="dealIds" />
	<input type="hidden" name="type" value="${type}" />
	<tr>
	<td class="label">填写分析报告</td>
			<td colspan="3"><textarea 
				class="textarea max" name="checkReason" id="checkReason"
				alt="width:500,allowBlank:false"></textarea></td>
	</tr>
	</table>
	
<form>
<c:set var="submitBtn">
	<input type="button" style="margin-right: 5px" onclick="dealAll();"
		value="提交" />
</c:set>
<c:out value="${submitBtn}" escapeXml="false" />
</c:if>
<c:if test="${type eq 7}">
<form action="analyseFault.do?method=analyseApprove" method="post" name="lineForm" id="lineForm">
<table id="sheet" class="formTable">
	<input type="hidden" name="dealIds" id="dealIds" />
	<input type="hidden" name="type" value="${type}" />
	</table>
	
<form>
<c:set var="submitBtn">
	<input type="button" style="margin-right: 5px" onclick="dealAll();"
		value="归档" />
</c:set>
</c:if>

<%@ include file="/common/footer_eoms.jsp"%>