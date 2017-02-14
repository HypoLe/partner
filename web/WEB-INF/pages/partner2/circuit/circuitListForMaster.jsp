<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@page import="java.util.List"%>
<script type="text/javascript">

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
			var formObj = document.forms[1];
 			formObj.submit();
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
}

</script>

<c:set var="addBtn">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/circuit.do?method=goToAdd'/>'"
		value="新增" />
</c:set>
<c:set var="submitBtn">
	<input type="button" style="margin-right: 5px" onclick="dealAll();"
		value="提交" />
</c:set>
<c:set var="deleteBtn">
	<input type="button" style="margin-right: 5px" onclick="deleteAll();"
		value="删除" />
</c:set>
<c:set var="myTitleSelectBtn">
	<input type="checkbox" name="myTitleSelect" onclick="selectAll();" />
</c:set>

<c:set var="importButtons">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<c:url value="circuit.do?method=goToImport"/>'"
		value="导入" />
	<br />
	<br />
</c:set>

<!-- Information hints area start-->
<c:out value="${msg}"></c:out>

<!-- Information hints area end-->


<logic:present name="circuitList" scope="request">
	<display:table name="circuitList" cellspacing="0" cellpadding="0"
		id="circuitList" pagesize="15" class="table circuitList" export="true"
		requestURI="/duty/circuit.do?method=list" sort="list"
		partialList="true" size="resultSize">

		<display:column media="html" title="${myTitleSelectBtn}">
			<!-- Values stored in checkbox is for batch deleting. -->
			<input type="checkbox" name="cardNumber" value="${circuitList.id}" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="分公司">
			<eoms:id2nameDB id="${circuitList.city}"
				beanId="tawSystemAreaDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="归属县公司">
			<eoms:id2nameDB id="${circuitList.country}"
				beanId="tawSystemAreaDao" />
		</display:column>
		<display:column property="faultPart" sortable="true"
			headerClass="sortable" title="故障段落" />
		<display:column property="faultBeginTime" sortable="true"
			headerClass="sortable" title="故障开始时间" />
		<display:column property="faultEndTime" sortable="true"
			headerClass="sortable" title="故障结束时间" />
		<display:column property="faultDuring" sortable="true"
			headerClass="sortable" title="故障历时" />
		<display:column sortable="true" headerClass="sortable" title="光缆类别" property="cableSort" />
		<display:column  sortable="true"
			headerClass="sortable" title="代维公司" >
				<eoms:id2nameDB id="${circuitList.monitorCompany}"
				beanId="IItawSystemDictTypeDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="光缆产权" property="cableOwner" />
		<display:column property="ifEffect" sortable="true"
			headerClass="sortable" title="是否影响业务" />
		<display:column property="monthFlag" sortable="true"
			headerClass="sortable" title="月度" />
		<display:column sortable="true" headerClass="sortable" title="代维公司意见">
			${circuitList.mainCheckIdea}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="分公司意见">
			${circuitList.mainCheckIdea_1}
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="省公司意见">
			${circuitList.mainCheckIdea_2}
		</display:column>
		<display:column sortable="false" headerClass="sortable"
			title="记录详情与历史" media="html">
			<a id="${circuitList.id }"
				href="circuit.do?method=goToSingleDetail&id=${circuitList.id}"
				target="blank" shape="rect">详情</a>
			<img src="${app}/images/icons/panel-new.gif">
			<a id="${circuitList.id }"
				href="circuit.do?method=goToHistory&id=${circuitList.id}"
				target="blank" shape="rect">处理过程</a>
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="是否修改" >
			<c:if test="${circuitList.ifModified == 'original' }">
				<img src="${app}/images/icons/sheet-icons/tick.png">未修改<br/>
			</c:if>
			<c:if test="${circuitList.ifModified == 'updated' }">
				<img src="${app}/images/icons/sheet-icons/exclamation.png">已修改<br/>
			</c:if>
		</display:column>
		<display:column sortable="false" headerClass="sortable" title="编辑"
			paramProperty="id" url="/duty/circuit.do?method=goToEdit"
			paramId="id" media="html">
			<img src="${app}/images/icons/edit.gif">
		</display:column>
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />

	</display:table>
</logic:present>

<%
	List myList = (List) request.getAttribute("circuitList");
	if (myList != null && myList.size() > 0) {
%>
<html:form action="circuit.do?method=deal" method="post">
	<table id="sheet" class="formTable">
		<!-- This hidden area is for batch dealing. -->
		<input type="hidden" name="dealIds" id="dealIds" />
		<tr>
			<td class="label">操作类型*</td>
			<td class="content"><select id="operateType" name="operateType">
				<option value='LAST_COMMOT_1' selected="selected">审核通过纳入考核</option>
				<option value='LAST_COMMOT_2'>审核通过不纳入考核</option>
				<option value='MASTER_TO_USER'>驳回给分公司</option>
			</select></td>
		</tr>
		<tr>
			<td class="label">省公司意见说明*</td>
			<td colspan="3"><textarea id="linkCityOption"
				class="textarea max" name="linkCityOption" id="linkCityOption"
				alt="width:500,allowBlank:false"></textarea></td>
		</tr>
	</table>
</html:form>
<c:out value="${submitBtn}" escapeXml="false" />
<%
}
%>
<%@ include file="/common/footer_eoms.jsp"%>
