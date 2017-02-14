<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@page import="java.util.List"%>

<script type="text/javascript">

Ext.onReady(function(){
	v = new eoms.form.Validation({form:'circuitLengthForm'});
	v.custom = function(){
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
			if(confirm("确定处理记录吗？")){
				$("dealIds").value=idResult.toString();
				return true;
			}else{
				return false;
			}
		}
	};	
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
				$("dealIds").value=idResult.toString();
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

function goToHistory(obj){
    var myId=obj.id;
	window.open('${app}/duty/circuitLength.do?method=goToHistory&id=myId',null,'left=0,top=0,height=600,width=1000,scrollbars=yes,resizable=yes')
}

function jumpUrl(){
	location.href='<html:rewrite page='/circuitLength.do?method=goToAdd'/>';
}

</script>

<c:set var="addBtn">
	<input type="button" style="margin-right: 5px"
		onclick="location.href='<html:rewrite page='/circuitLength.do?method=goToAdd'/>'"
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

<!-- Information hints area start-->
<div style="color: red;font: bold;"><c:out value="${msg}"></c:out></div>

<!-- Information hints area end-->
<logic:present name="circuitLengthList" scope="request">
	<display:table name="circuitLengthList" cellspacing="0" cellpadding="0"
		id="circuitLengthList" pagesize="15" class="table circuitLengthList"
		export="true" requestURI="/duty/circuitLength.do?method=list"
		sort="list" partialList="true" size="resultSize">

		<display:column media="html" title="${myTitleSelectBtn}">
			<!-- Values stored in checkbox is for batch deleting. -->
			<input type="checkbox" name="cardNumber"
				value="${circuitLengthList.id}" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="分公司">
			<eoms:id2nameDB id="${circuitLengthList.city}"
				beanId="tawSystemAreaDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="归属县公司">
			<eoms:id2nameDB id="${circuitLengthList.country}"
				beanId="tawSystemAreaDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="代维公司">
			<eoms:id2nameDB id="${circuitLengthList.monitorCompany}"
				beanId="tawSystemAreaDao" />
		</display:column>
		<display:column property="cableLength1" sortable="true"
			headerClass="sortable" title="架空光缆" />
		<display:column property="cableLength2" sortable="true"
			headerClass="sortable" title="管道（直埋）光缆" />
		<display:column property="cableLength3" sortable="true"
			headerClass="sortable" title="同路由架空光缆" />
		<display:column property="cableLength4" sortable="true"
			headerClass="sortable" title="同路由管道（直埋）光缆" />
		<display:column property="cableLength5" sortable="true"
			headerClass="sortable" title="空闲管道" />
		<display:column property="yearFlag" sortable="true"
			headerClass="sortable" title="年度" />
		<display:column property="monthFlag" sortable="true"
			headerClass="sortable" title="月度" />
		<display:column sortable="false" headerClass="sortable" title="编辑"
			paramProperty="id" url="/duty/circuitLength.do?method=goToEdit"
			paramId="id" media="html">
			<img src="${app}/images/icons/edit.gif">
		</display:column>
		<display:column sortable="false" headerClass="sortable" title="删除"
			url="/partner2/circuit/circuitLength.do?method=delete" paramProperty="id"
			paramId="id" media="html">
			<img src="${app }/images/icons/icon.gif"
				onclick="return(confirm('确定删除?'))" />
		</display:column>
		<display:column property="mainCityOption" sortable="true"
			headerClass="sortable" title="分公司意见" />
		<display:column property="mainMonitorOption" sortable="true"
			headerClass="sortable" title="代维公司意见" />
		<display:column sortable="false" headerClass="sortable"
			title="记录详情与历史" media="html">
			<a id="${circuitLengthList.id }"
				href="circuitLength.do?method=goToSingleDetail&id=${circuitLengthList.id}"
				target="blank" shape="rect">详情</a>
			<img src="${app}/images/icons/panel-new.gif">
			<a id="${circuitLengthList.id }"
				href="circuitLength.do?method=goToHistory&id=${circuitLengthList.id}"
				target="blank" shape="rect">处理过程</a>
		</display:column>
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />

	</display:table>
</logic:present>


<%
	List myList = (List) request.getAttribute("circuitLengthList");
	if (myList != null && myList.size() > 0) {
%> 
<html:form action="circuitLength.do?method=deal" method="post">
	<table id="sheet" class="formTable">
		<tr id="myFunctionCategory">
			<td class="label">代维公司意见*</td>
			<td colspan="3"><textarea id="myFunctionCategory"
				class="textarea max" name="linkCutOption" id="linkCutOption"
				alt="width:500,allowBlank:false,maxLength:500,vtext:'请填入审核意见，最多输入250汉字'"></textarea>
			</td>
		</tr>
	</table>
	<!-- This hidden area is for batch dealing. -->
	<input type="hidden" name="dealIds" id="dealIds" />
	<input type="hidden" name="operateType" id="operateType"
		value="SUBMIT_TO_CITY" />
	<div ID="analyzeTaskPhase">
	<fieldset><legend> 派发给:分公司(部门) </legend> <eoms:chooser
		id="test" type="dept" config="{returnJSON:true,showLeader:true}"
		category="[{id:'currentTaskOwner',text:'派发',childType:'dept',allowBlank:false,vtext:'请选择派发对象'}]" /></fieldset>
	</div>
	<html:submit styleClass="btn" property="method.save"
		styleId="method.save" value="处理记录"></html:submit>
	<html:button property="method.save" styleId="method.save"
		styleClass="btn" value="全部删除" onclick="deleteAll();"></html:button>
</html:form>

<%
}
%>
<br/>
<br/>
<html:button property="method.save" styleId="method.save"
	styleClass="btn" value="新增记录" onclick="jumpUrl()"></html:button>

<html:form action="circuitLength.do?method=deleteAll" method="post">
	<!-- This hidden area is for batch deleting. -->
	<input type="hidden" name="deleteIds" id="deleteIds" />
</html:form>
<%@ include file="/common/footer_eoms.jsp"%>
