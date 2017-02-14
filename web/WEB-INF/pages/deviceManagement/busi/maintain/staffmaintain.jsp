<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%
request.setAttribute("province", "10");
%>
<script type="text/javascript"
	src="${app}/deviceManagement/scripts/jquery/jquery-1.5.js"></script>
<script type="text/javascript">
    var myjs=jQuery.noConflict();
    	Ext.onReady(function(){
	var check=document.getElementById("checkIds");
	if(check&&check.value!=""){
	checkV=check.value
	var checks=checkV.toString().split(";");
	var cardNumberList = document.getElementsByName("cardNumber");
	for(var i=0;i<checks.length-1 ;i++){
	checkValue ='#'+checks[i].toString();
	myjs(checkValue).attr('checked',true);
	}
	}
	var	userTreeAction=eoms.appPath+'/xtree.do?method=userFromDept';
/*
	new xbox({
		btnId:'areatree',
		treeDataUrl:'${app}/partner/baseinfo/xtree.do?method=area',
		treeRootId:'${province}',treeRootText:'黑龙江',treeChkMode:'',treeChkType:'area',single:true,
		showChkFldId:'publicizeArticlePlace',saveChkFldId:'de0creatdeptT',returnJSON:false
	});
*/
			
})
function openSelectAreas(){
    window.open (eoms.appPath+'/partner/baseinfo/areaDeptTrees.do?method=selectAreas','newwindow','height=400,width=600,top=300,left=500,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no'); 
}
    
    function sendBox() {
	var cardNumberList = document.getElementsByName("cardNumber");
	var idResult = "";
	var checkId="";
	for (i = 0 ; i < cardNumberList.length; i ++) {
		if (cardNumberList[i].checked) {
			var myTempStr=cardNumberList[i].value;
			idResult+=myTempStr.toString()+";" ;
			var tempId=cardNumberList[i].id;
			checkId +=tempId.toString()+";" ;
		}
	}
	$("deleteIdss").value=idResult.toString();
	$("checks").value=checkId.toString();
		if(idResult==""){
		alert("请至少选择一个统计项");
				return false;}
		document.getElementById("checkAndSearchFrom").submit();
	}
	
	function changeCheckBox(){
		//var creatdept = myjs('#de0maintainNameT').val();
		var maintainName=myjs('#de0maintainNameT').val();
		var fiberSection=myjs('#de0fiberSectionT').val();
		var influenceSystem=myjs('#de0influenceSystemT').val();
		var approvaluser=myjs('#de0approvaluserT').val();
		if(maintainName){
		myjs('#de0maintainName').attr('checked',true);
		myjs('#de0maintainName').attr('disabled','disabled');
		}
		else{
			if((myjs('#de0maintainName').attr('disabled'))){
		myjs('#de0maintainName').attr('checked',false);
		myjs('#de0maintainName').attr('disabled','');
		}
		}
		if(fiberSection){
		myjs('#de0fiberSection').attr('checked',true);
		myjs('#de0fiberSection').attr('disabled','disabled');
		}
		else{
			if((myjs('#de0fiberSection').attr('disabled'))){
		myjs('#de0fiberSection').attr('checked',false);
		myjs('#de0fiberSection').attr('disabled','');
		}
		}
		if(influenceSystem){
		myjs('#de0influenceSystem').attr('checked',true);
		myjs('#de0influenceSystem').attr('disabled','disabled');
		}
		else{
			if((myjs('#de0influenceSystem').attr('disabled'))){
		myjs('#de0influenceSystem').attr('checked',false);
		myjs('#de0influenceSystem').attr('disabled','');
		}
		}
		if(approvaluser){
		myjs('#de0approvaluser').attr('checked',true);
		myjs('#de0approvaluser').attr('disabled','disabled');
		}
		else{
			if((myjs('#de0approvaluser').attr('disabled'))){
		myjs('#de0approvaluser').attr('checked',false);
		myjs('#de0approvaluser').attr('disabled','');
		}
		}
	}
</script>
<form id="checkAndSearchFrom"
	action="${app }/deviceManagement/line/maintaine.do?method=staffContent" method="post">

<fieldset><legend>请输入统计条件</legend>
<table class="formTable">
<tr>
		<td class="label">割接名称</td>
		<td><input id="de0maintainNameT" type="text" name="de0maintainNameT"
			 onblur="changeCheckBox()"/></td>
		<td class="label">光缆线段</td>
				<td><input id="de0fiberSectionT" type="text" name="de0fiberSectionT"
			 onblur="changeCheckBox()"/></td>
		<td class="label">影响系统</td>
				<td><input id="de0influenceSystemT" type="text" name="de0influenceSystemT"
			 onblur="changeCheckBox()"/></td>
			 <td class="label">审批人</td>
		<td><input id="de0approvaluserT" type="text" name="de0approvaluserT"
			 onblur="changeCheckBox()"/></td>
			</tr>
</table>
</fieldset>

<fieldset><legend>请选择统计项目</legend>
<table class="formTable">
	<tr>
		<%--<td class="label">所属地市</td>
		<td><input id="de0publicizeArticleLikeArea" type="checkbox" name="cardNumber"
			value="de0publicizeArticleLikeArea" /></td>--%>
		<td class="label">割接名称</td>
		<td><input id="de0maintainName" type="checkbox" name="cardNumber"
			value="de0maintainName" /></td>
		<td class="label">光缆线段</td>
				<td><input id="de0fiberSection" type="checkbox" name="cardNumber"
			value="de0fiberSectionLikeArea" /></td>
       		<td class="label">影响系统</td>
				<td><input id="de0influenceSystem" type="checkbox" name="cardNumber"
			value="de0influenceSystemTypeLikedict" /></td>
			<td class="label">审批人</td>
		<td><input id="de0approvaluser" type="checkbox" name="cardNumber"
			value="de0approvaluser" /></td>
			
		<input type="hidden" name="deleteIdss" id="deleteIdss" />
		<input type="hidden" name="checks" id="checks" />
		<input type="hidden" name="checkIds" id="checkIds"
			value="${checkList}" />
	</tr>
</table>
</fieldset>

<input type="button" name="统计" value="统计" onclick="sendBox()" /> <!-- This hidden area is for batch deleting. -->
<form>
<div>
<table cellpadding="0" class="table contentStaffList" cellspacing="0">
	<thead>
		<tr>
			<logic-el:present name="headList">
				<c:forEach items="${headList}" var="headlist">
					<th>${headlist}</th>

				</c:forEach>

			</logic-el:present>
		</tr>
	</thead>
	<logic-el:present name="tableList">
		<tbody>
			<c:forEach items="${tableList}" var="tdList">
				<tr>
					<c:forEach items="${tdList}" var="td">
						<c:if test="${td.show}">
							<td rowspan="${td.rowspan} }"><c:if test="${!empty td.url}">
								<a href="javascript:void(0);"
									onclick="window.open('${app}${td.url}');">${td.name}</a>
							</c:if> <c:if test="${empty td.url}">
      ${td.name}
     </c:if></td>
						</c:if>
					</c:forEach>
				</tr>
			</c:forEach>
		</tbody>
	</logic-el:present>
</table>
</div>







<%@ include file="/common/footer_eoms.jsp"%>