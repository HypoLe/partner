<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

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
	$("deleteIds").value=idResult.toString();
	$("checks").value=checkId.toString();
		if(idResult==""){
		alert("请至少选择一个统计项");
				return false;}
		document.getElementById("checkAndSearchFrom").submit();
	}
	
	function changeCheckBox(){
		var area_name = myjs('#area_nameT').val();
		var accName=myjs('#accNameT').val();
		var aptitude=myjs('#aptitudeT').val();
		var city=myjs('#cityT').val();
		if(area_name){
		myjs('#area_name').attr('checked',true);
		myjs('#area_name').attr('disabled','disabled');
		}
		else{
			if((myjs('#area_name').attr('disabled'))){
		myjs('#area_name').attr('checked',false);
		myjs('#area_name').attr('disabled','');
		}
		}
		if(accName){
		myjs('#accName').attr('checked',true);
		myjs('#accName').attr('disabled','disabled');
		}
		else{
			if((myjs('#accName').attr('disabled'))){
		myjs('#accName').attr('checked',false);
		myjs('#accName').attr('disabled','');
		}
		}
		if(aptitude){
		myjs('#aptitude').attr('checked',true);
		myjs('#aptitude').attr('disabled','disabled');
		}
		else{
			if((myjs('#aptitude').attr('disabled'))){
		myjs('#aptitude').attr('checked',false);
		myjs('#aptitude').attr('disabled','');
		}
		}
		if(city){
		myjs('#city').attr('checked',true);
		myjs('#city').attr('disabled','disabled');
		}
		else{
			if((myjs('#city').attr('disabled'))){
		myjs('#city').attr('checked',false);
		myjs('#city').attr('disabled','');
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
<form  id="checkAndSearchFrom" action="paternerStatistics.do?method=list" method="post">

<fieldset>
			<legend>请输入统计条件</legend>
<table class="formTable">
		<tr>
			<td class="label">公司场址</td>
			<td><input type="text" class="text"
						name="area_nameT"  id="area_nameT" onblur="changeCheckBox()"/></td>
							<td class="label">开户银行名</td>    
			<td><input type="text" class="text"
						name="accNameT" id="accNameT" onblur="changeCheckBox()"/></td>
						</tr>
						<tr>
							<td class="label">合作伙伴资质</td>
			<td><input type="text" class="text"
						name="aptitudeT" id="aptitudeT" onblur="changeCheckBox()"/></td>
							<td class="label">维护区域</td>
			<td><input type="text" class="text"
						name="cityT" id="cityT" onblur="changeCheckBox()"/></td>
						
		</tr>
	</table>
	</fieldset>

<fieldset>
			<legend>请选择统计项目</legend>
<table class="formTable">
<tr>
				<td class="label" colspan="2">省下属公司</td>
				<td colspan="2"><input id="nameOld" type="checkbox" name="cardNumber"
				value="nameOld" /></td>
				<td class="label" colspan="2">合作伙伴公司</td>
				<td colspan="2"><input id="name" type="checkbox" name="cardNumber"
				value="name" /></td>
				</tr>
				<tr>
				<td class="label">公司地址</td>
				<td><input id="area_name" type="checkbox" name="cardNumber"
				value="area_name" /></td>
				<td class="label">开户银行名</td>
				<td><input  id="accName" type="checkbox" name="cardNumber"
				value="accName" /></td>
				<td class="label">合作伙伴资质</td>
				<td><input  id="aptitude" type="checkbox" name="cardNumber"
				value="aptitude" /></td>
				<td class="label">维护区域</td>
				<td><input  id="city" type="checkbox" name="cardNumber"
				value="city" /></td>
				
				<input type="hidden" name="deleteIds" id="deleteIds" />
				<input type="hidden" name="checks" id="checks" />
				<input type="hidden" name="checkIds" id="checkIds" value="${checkList}" />
</tr>
</table>
</fieldset>

<input type="button" name="统计" value="统计" onclick="sendBox()"/>
	<!-- This hidden area is for batch deleting. -->
<form>

<div>
 <table  cellpadding="0" class="table protocolMainList" cellspacing="0">
 	 <thead>
	 <tr >
	 <logic-el:present name="headList">
	 <c:forEach items="${headList}"  var="headlist" >
	 <th>
	 ${headlist}
	 </th>
	 
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
     <td rowspan="${td.rowspan} }">
  
     <c:if test="${!empty td.url}">
     <a href="${app}${td.url}">${td.name}</a>
     </c:if>
     <c:if test="${empty td.url}"> ${td.name}</c:if>
    
     </td>
      </c:if>
     </c:forEach>
     </tr>
     </c:forEach>
	  </tbody>
	  </logic-el:present>
 </table>
</div>






<%@ include file="/common/footer_eoms.jsp"%>