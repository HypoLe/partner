<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript" src="${app}/deviceManagement/scripts/jquery/jquery-1.5.js"></script>
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
		var area_name = myjs('#dt0nameT').val();
		var accName=myjs('#d0area_nameT').val();
		var aptitude=myjs('#d0nameT').val();
		//var d0diploma=myjs('#d0big_typeT').attr('checked');
		var d0diploma=myjs("[name='d0big_typeT']:checked").attr('checked');
		var d0selectedLevel=myjs('#d0selectedLevelT').val();
		var d0fund=myjs('#d0fundT').val();
		var d0type=myjs('#d0typeT').val();
		
		if(area_name){
		myjs('#dt0name').attr('checked',true);
		myjs('#dt0name').attr('disabled','disabled');
		}
		else{
			if((myjs('#dt0name').attr('disabled'))){
		myjs('#dt0name').attr('checked',false);
		myjs('#dt0name').attr('disabled','');
		}
		}
		if(accName){
		myjs('#d0area_name').attr('checked',true);
		myjs('#d0area_name').attr('disabled','disabled');
		}
		else{
			if((myjs('#d0area_name').attr('disabled'))){
		myjs('#d0area_name').attr('checked',false);
		myjs('#d0area_name').attr('disabled','');
		}
		}
		if(aptitude){
		myjs('#d0name').attr('checked',true);
		myjs('#d0name').attr('disabled','disabled');
		}
		else{
			if((myjs('#d0name').attr('disabled'))){
		myjs('#d0name').attr('checked',false);
		myjs('#d0name').attr('disabled','');
		}
		}
		if(d0diploma){
			myjs('#d0big_type').attr('checked',true);
			myjs('#d0big_type').attr('disabled','disabled');
			}
			else{
				if((myjs('#d0big_type').attr('disabled'))){
			myjs('#d0big_type').attr('checked',false);
			myjs('#d0big_type').attr('disabled','');
			}
			}
		
		if(d0selectedLevel!=''){
			myjs('#d0selectedLevel').attr('checked',true);
			myjs('#d0selectedLevel').attr('disabled','disabled');
			}
			else{
				if((myjs('#d0selectedLevel').attr('disabled'))){
			myjs('#d0selectedLevel').attr('checked',false);
			myjs('#d0selectedLevel').attr('disabled','');
			}
			}
		if(d0fund!=''&&d0fund!='0'){
			myjs('#d0fund').attr('checked',true);
			myjs('#d0fund').attr('disabled','disabled');
			}
			else{
				if((myjs('#d0fund').attr('disabled'))){
			myjs('#d0fund').attr('checked',false);
			myjs('#d0fund').attr('disabled','');
			}
			}
		if(d0type!=''){
			myjs('#d0type').attr('checked',true);
			myjs('#d0type').attr('disabled','disabled');
			}
			else{
				if((myjs('#d0type').attr('disabled'))){
					myjs('#d0type').attr('checked',false);
					myjs('#d0type').attr('disabled','');
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
<eoms:xbox id="tree1" dataUrl="${app}/xtree.do?method=area" rootId="24"
	rootText='所属地市' valueField="d0area_nameId" handler="d0area_nameT" textField="d0area_nameT"
	checktype="area" single="true"></eoms:xbox>
	<eoms:xbox id="tree2" dataUrl="${app}/partner/statistically/paternerStaff.do?method=dept&type=parent" rootId="2"
	rootText='组织' valueField="dt0nameTId" handler="dt0nameT" textField="dt0nameT"
	checktype="dept" single="true"></eoms:xbox>

<form  id="checkAndSearchFrom" action="paternerStaff.do?method=companyList" method="post">

<fieldset>
			<legend>请输入统计条件</legend>
<table class="formTable">
		<tr>
			<td class="label">省份</td>    
			<td>
				<input type="text" class="text"	value="${checkAndSearchFrom.d0area_nameT}" maxLength="100" 
				name="d0area_nameT" id="d0area_nameT" onblur="changeCheckBox()"/>
				<input type="hidden" name="d0area_nameT" id="d0area_nameId" value="${checkAndSearchFrom.d0area_nameT}" 
				maxLength="32"class="text medium" />
			</td>
			<td class="label">地市</td>    
			<td>
				<input type="text" class="text"	value="${checkAndSearchFrom.d0area_nameT}" maxLength="100" 
				name="d0area_nameT" id="d0area_nameT" onblur="changeCheckBox()"/>
				<input type="hidden" name="d0area_nameT" id="d0area_nameId" value="${checkAndSearchFrom.d0area_nameT}" 
				maxLength="32"class="text medium" />
			</td>
		</tr>
		<tr>
			<td class="label">区县</td>    
			<td  colspan="3">
				<input type="text" class="text"	value="${checkAndSearchFrom.d0area_nameT}" maxLength="100" 
				name="d0area_nameT" id="d0area_nameT" onblur="changeCheckBox()"/>
				<input type="hidden" name="d0area_nameT" id="d0area_nameId" value="${checkAndSearchFrom.d0area_nameT}" 
				maxLength="32"class="text medium" />
			</td>
		</tr>
		<tr>
			<td class="label">专业</td>
			<td colspan="3">
			    <c:forEach items="${specialtyList}" var="dictBigType" >
						<input type="checkbox" name="d0big_typeT" id="d0big_typeT" onchange="changeCheckBox()"
							value="${dictBigType.dictId}" />&nbsp;&nbsp;${dictBigType.dictName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</c:forEach>
			</td>
		</tr>
	</table>
	</fieldset>

<fieldset>
			<legend>请选择统计项目</legend>
<table class="formTable">
<tr>
				<td class="label"><input  id="d0area_name" type="checkbox" name="cardNumber" value="d0area_name" />&nbsp;&nbsp;省份</td>
				<td class="label"><input id="dt0name" type="checkbox" name="cardNumber" value="dt0name" />&nbsp;&nbsp;地市</td>
				<td class="label"><input  id="d0big_type" type="checkbox" name="cardNumber" />&nbsp;&nbsp;区县</td>
				<td class="label"><input  id="d0type" type="checkbox" name="cardNumber" value="d0typeTypeLikedict" />&nbsp;&nbsp;巡检专业</td>
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
     <a href="javascript:void(0);" onclick="window.open('${app}${td.url}');">${td.name}</a>
     </c:if>
     <c:if test="${empty td.url}">
      ${td.name}
     </c:if>
    
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