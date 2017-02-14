<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@page import="java.util.List"%>
<%@ include file="/nop3/common/nop3_scripts_jquery.jsp"%>

<script type="text/javascript">
var myJ = jQuery.noConflict();
myJ(function(){
	//处理由合同关联基站规模数据的情况,如果windwo.opener存在则说明是由合同关联打开
	if(window.opener){
		myJ('tr').show();
		myJ('div#relateCircuit').show();
		var circuitIds = window.opener.document.getElementById('circuitIds').value;
		var myProxyModelsTable = myJ(window.opener.document.getElementById('myProxyModelsTable'));
		if(!circuitIds){
			var circuitIdArray=circuitIds.split(";");
			for(var i=0;i<circuitIdArray;i++){
				myJ('input#'+circuitIdArray[i]).parents('tr').hide();
			}
		}
		myJ("input#addCirToContract").click(function(){
			if(confirm("确认添加？")){
				var checkedId="";
				var city="";
				var country="";
				var monitorCompany="";
				myJ("input:checked").each(function(){
					checkedId+=myJ(this).val()+";";
					city=myJ.trim(myJ(this).parent().next().text());
					country=myJ.trim(myJ(this).parent().next().next().text());
					monitorCompany=myJ.trim(myJ(this).parent().next().next().next().text());
					myProtocolTable.append("<tr><td class='content'>"+city+"</td>"
	 				+"<td class='content serverType'>"+country+"</td>"
	 				+"<td class='content monitorCompanys'>"+monitorCompany+"</td>"
	 				+"<td class='content monitorCompanys'>线路类</td>"
	 				+"<td class='content'><a href="+"${app}/partner2/circuit/circuit.do?method=goToSingleDetail&id="+myJ(this).val()+" target='_blank'>"+"<img src='${app}/images/icons/search.gif'/></a></td>"
	 				+"<td class='content'><img src='${app}/nop3/images/icon.gif'"+"onmousedown='deleteCircuit(this);' style='cursor:pointer;'/>"+"</td>"+"<input type='hidden' value="+myJ(this).val()+" name='circuitIds'/>"+"</tr>");
				});
				if(checkedId!=""){
					window.opener.document.getElementById().value+=checkedId;
				}
				alert('添加成功！');
				window.close();
			}else{
				return false;
			}
		});
	}	
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
			var formObj = document.forms[0];
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
	window.open('${app}/duty/circuit.do?method=goToHistory&id=myId',null,'left=0,top=0,height=600,width=1000,scrollbars=yes,resizable=yes')
}
</script>

	<c:set var="addBtn">
		<input type="button" style="margin-right: 5px"
			onclick="location.href='<html:rewrite page='/circuit.do?method=goToAdd'/>'"
			value="新增" />
	</c:set>
	<c:set var="submitBtn">
		<input type="button" style="margin-right: 5px"
			onclick="dealAll();"
			value="提交" />
	</c:set>
	<c:set var="deleteBtn">
		<input type="button" style="margin-right: 5px"
			onclick="deleteAll();"
			value="删除" />
	</c:set>
	<c:set var="myTitleSelectBtn">
		<input type="checkbox" name="myTitleSelect" onclick="selectAll();" />
	</c:set>
	
	<c:set var="importButtons">
		 <input type="button" style="margin-right: 5px"
        onclick="location.href='<c:url value="circuit.do?method=goToImport"/>'"
        value="导入"/> 
      	<br/>
      	<br/>
    </c:set>


<!-- Information hints area start-->
<div style="color: red;font: bold;"><c:out value="${msg}"></c:out></div>

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
		<display:column sortable="true" headerClass="sortable" title="代维公司">
			<eoms:id2nameDB id="${circuitList.monitorCompany}"
				beanId="IItawSystemDictTypeDao" />
		</display:column>
		<display:column sortable="true" headerClass="sortable" title="光缆产权" property="cableOwner" />
		<display:column property="ifEffect" sortable="true"
			headerClass="sortable" title="是否影响业务" />
		<display:column sortable="false" headerClass="sortable" title="编辑"
			paramProperty="id" url="/duty/circuit.do?method=goToEdit"
			paramId="id" media="html">
			<img src="${app}/images/icons/edit.gif">
		</display:column>
		<display:column sortable="false" headerClass="sortable" title="删除"
			url="/duty/circuit.do?method=delete" paramProperty="id" paramId="id"
			media="html">
			<img src="${app}/images/icons/icon.gif"
				onclick="return(confirm('确定删除?'))" />
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
		<div id="relateCircuit" style="display: none;">
			<input type="button" name="circuitIds" id="addCirToContract" value="确认关联"/>
		</div>
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
<html:form action="circuit.do?method=deleteAll" method="post">
	<!-- This hidden area is for batch deleting. -->
	<input type="hidden" name="deleteIds" id="deleteIds" />
</html:form>

<html:form action="circuit.do?method=deal" method="post">
	<!-- This hidden area is for batch dealing. -->
	<input type="hidden" name="dealIds" id="dealIds" />
	<input type="hidden" name="operateType" id="operateType"
		value="FIRST_SUBMIT" />
	<table id="sheet" class="formTable">
	<tr>
			<td class="label">操作类型</td>
			<td class="content"><select>
				<option selected="selected">提交给代维公司审核</option>
			</select></td>
		</tr>
	</table>
	<div ID="analyzeTaskPhase">
	<fieldset><legend> 派发给:代维公司(部门) </legend> <eoms:chooser
		id="test" type="dept" config="{returnJSON:true,showLeader:true}"
		category="[{id:'currentTaskOwner',text:'派发',childType:'dept',allowBlank:false,vtext:'请选择派发对象'}]" /></fieldset>
	</div>
</html:form>
<c:out value="${submitBtn}" escapeXml="false" />
<c:out value="${deleteBtn}" escapeXml="false" />
<%
}
%>
<c:out value="${addBtn}" escapeXml="false" />
<c:out value="${importButtons}" escapeXml="false" />
<%@ include file="/common/footer_eoms.jsp"%>
