<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript"
	src="${app}/deviceManagement/scripts/jquery/jquery-1.5.js"></script>
<script type="text/javascript">
    var myjs=jQuery.noConflict();
    
    
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
		var area_name = myjs('#de0nameT').val();
		var accName=myjs('#ct0area_nameT').val();
		var aptitude=myjs('#ct0nameT').val();
		if(area_name){
		myjs('#de0name').attr('checked',true);
		myjs('#de0name').attr('disabled','disabled');
		}
		else{
			if((myjs('#de0name').attr('disabled'))){
		myjs('#de0name').attr('checked',false);
		myjs('#de0name').attr('disabled','');
		}
		}
		if(accName){
		myjs('#ct0area_name').attr('checked',true);
		myjs('#ct0area_name').attr('disabled','disabled');
		}
		else{
			if((myjs('#ct0area_name').attr('disabled'))){
		myjs('#ct0area_name').attr('checked',false);
		myjs('#ct0area_name').attr('disabled','');
		}
		}
		if(aptitude){
		myjs('#ct0name').attr('checked',true);
		myjs('#ct0name').attr('disabled','disabled');
		}
		else{
			if((myjs('#ct0name').attr('disabled'))){
		myjs('#ct0name').attr('checked',false);
		myjs('#ct0name').attr('disabled','');
		}
		}
	}
	
	
	
	
	Ext.onReady(function(){
	var check=document.getElementById("checkIds");
	if(check&&check.value!=""){
	checkV=check.value
	var checks=checkV.toString().split(";");
	var cardNumberList = document.getElementsByName("cardNumber");
	for(var i=0;i<checks.length-1 ;i++){
	//alert(checks[i].toString());
	checkValue ='#'+checks[i].toString();
	myjs(checkValue).attr('checked',true);
	}
	}
	});
	
	
	
</script>


<form id="checkAndSearchFrom"
	action="ctContentss.do?method=staffContent" method="post">

<fieldset><legend>请输入统计条件</legend>
<table class="formTable">
	<tr>
		<td class="label">合同创建部门</td>
		<td><input type="text" class="text" "
						name="de0nameT"
			id="de0nameT" onblur="changeCheckBox()" /></td>
		<td class="label">甲方</td>
		<td><input type="text" class="text" name="ct0area_nameT"
			id="ct0area_nameT" onblur="changeCheckBox()" /></td>

		<td class="label">乙方</td>
		<td><input type="text" class="text" name="ct0nameT" id="ct0nameT"
			onblur="changeCheckBox()" /></td>


	</tr>
</table>
</fieldset>

<fieldset><legend>请选择统计项目</legend>
<table class="formTable">
	<tr>

		<td class="label">合同创建部门</td>
		<td><input id="de0name" type="checkbox" name="cardNumber"
			value="de0deptname" /></td>
		<td class="label">甲方</td>
		<td><input id="ct0area_name" type="checkbox" name="cardNumber"
			value="ct0party_a" /></td>
		<td class="label">乙方</td>
		<td><input id="ct0name" type="checkbox" name="cardNumber"
			value="ct0party_b" /></td>

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