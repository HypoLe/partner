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
		var d0diploma=myjs('#u0diplomaT').val();
		var d0sex=myjs('#u0sexT').val();
		if(area_name&&area_name!=''){
		myjs('#dt0name').attr('checked',true);
		myjs('#dt0name').attr('disabled','disabled');
		}
		else{
			if((myjs('#dt0name').attr('disabled'))){
		myjs('#dt0name').attr('checked',false);
		myjs('#dt0name').attr('disabled','');
		}
		}
		if(accName&&accName!=''){
		myjs('#d0area_name').attr('checked',true);
		myjs('#d0area_name').attr('disabled','disabled');
		}
		else{
			if((myjs('#d0area_name').attr('disabled'))){
		myjs('#d0area_name').attr('checked',false);
		myjs('#d0area_name').attr('disabled','');
		}
		}
		if(aptitude&&aptitude!=''){
		myjs('#d0name').attr('checked',true);
		myjs('#d0name').attr('disabled','disabled');
		}
		else{
			if((myjs('#d0name').attr('disabled'))){
		myjs('#d0name').attr('checked',false);
		myjs('#d0name').attr('disabled','');
		}
		}
		if(d0diploma&&d0diploma!=''){
			myjs('#u0diploma').attr('checked',true);
			myjs('#u0diploma').attr('disabled','disabled');
			}
			else{
				if((myjs('#u0diploma').attr('disabled'))){
			myjs('#u0diploma').attr('checked',false);
			myjs('#u0diploma').attr('disabled','');
			}
			}
		if(d0sex&&d0sex!=''){
			myjs('#u0sex').attr('checked',true);
			myjs('#u0sex').attr('disabled','disabled');
			}
			else{
				if((myjs('#u0sex').attr('disabled'))){
			myjs('#u0sex').attr('checked',false);
			myjs('#u0sex').attr('disabled','');
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
	rootText='合作伙伴公司（省）' valueField="dt0nameTId" handler="dt0nameT" textField="dt0nameT"
	checktype="dept" single="true"></eoms:xbox>
	<eoms:xbox id="tree3" dataUrl="${app}/partner/statistically/paternerStaff.do?method=dept" rootId="2"
	rootText='合作伙伴（地市州）分公司' valueField="d0nameId" handler="d0nameT" textField="d0nameT"
	checktype="dept" single="true"></eoms:xbox>
	
	
<form  id="checkAndSearchFrom" action="paternerStaff.do?method=list" method="post">

<fieldset>
			<legend>请输入统计条件</legend>
<table class="formTable">
		<tr>
		
		<td class="label">所属地市</td>    
			<td><input type="text" class="text"
						value="${checkAndSearchFrom.d0area_nameT}" maxLength="100"
						name="d0area_nameT" id="d0area_nameT" onblur="changeCheckBox()"/></td>
			<input type="hidden" name="d0area_nameT" id="d0area_nameId"
						value="${checkAndSearchFrom.d0area_nameT}" maxLength="32"
						class="text medium" />
			<td class="label">合作伙伴公司（省）</td>
			<td><input type="text" class="text"
						value="${checkAndSearchFrom.dt0nameT}" maxLength="100"
						name="dt0nameT"  id="dt0nameT" onblur="changeCheckBox()"/></td>
						<input type="hidden" name="d0area_nameT" id="dt0nameTId"
						value="${checkAndSearchFrom.dt0nameT}" maxLength="32"
						class="text medium" />
						<td class="label">合作伙伴（地市州）分公司</td>
			<td><input type="text" class="text"
						value="${checkAndSearchFrom.d0nameT}" maxLength="100"
						name="d0nameT" id="d0nameT" onblur="changeCheckBox()"/></td>
						<input type="hidden" name="d0nameT" id="d0nameId"
						value="${checkAndSearchFrom.d0nameT}" maxLength="32"
						class="text medium" />
							
						</tr>
						<tr>
					<td class="label">性别</td>
			<td><select name="u0sexT" id="u0sexT" onchange="changeCheckBox()" class="select">
			<option  value="">请选择</option>
			<option  value="112020202">女</option>
			<option  value="112020201">男</option>
			</select></td>
						<td class="label">学历</td>
			<td>
			<eoms:comboBox name="u0diplomaT" id="u0diplomaT"  initDicId="1120203" defaultValue=""
			    alt="allowBlank:false" onchange="changeCheckBox()"  styleClass="select"/>
			</td>	
			
						
		</tr>
		
	</table>
	</fieldset>

<fieldset>
			<legend>请选择统计项目</legend>
<table class="formTable">
<tr>
			
			<td class="label">所属地市</td>
				<td><input  id="d0area_name" type="checkbox" name="cardNumber"
				value="d0area_name" /></td>
			
				<td class="label">合作伙伴公司（省）</td>
				<td><input id="dt0name" type="checkbox" name="cardNumber"
				value="dt0name" /></td>
				
				<td class="label">合作伙伴（地市州）分公司</td>
				<td><input  id="d0name" type="checkbox" name="cardNumber"
				value="d0name" /></td>
				<td class="label">学历</td>
				<td><input  id="u0diploma" type="checkbox" name="cardNumber"
				value="u0diplomaTypeLikedict" /></td>
				<td class="label">性别</td>
				<td><input  id="u0sex" type="checkbox" name="cardNumber" 
				value="u0sexTypeLikedict" /></td>
				
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