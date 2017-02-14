<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<% request.setAttribute("province", "10"); %>
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
	$("deleteIdss").value=idResult.toString();
	$("checks").value=checkId.toString();
		if(idResult==""){
		alert("请至少选择一个统计项");
				return false;}
		document.getElementById("checkAndSearchFrom").submit();
	}
	
	
	
	function changeCheckBox(){
		var City = myjs('#sy0feeApplicationCityT').val();
		var CompanyName=myjs('#fe0feeApplicationCompanyNameT').val();
		var GreatTime=myjs('#fe0feeApplicationGreatTimeT').val();
		var Type = myjs('#fe0feeApplicationTypeT').val();
	
		
		if(City){
		myjs('#feeApplicationCity').attr('checked',true);
		myjs('#feeApplicationCity').attr('disabled','disabled');
		}
		else{
			if((myjs('#feeApplicationCity').attr('disabled'))){
		     myjs('#feeApplicationCity').attr('checked',false);
		     myjs('#feeApplicationCity').attr('disabled','');
		}
		}
		if(CompanyName){
		myjs('#feeApplicationCompanyName').attr('checked',true);
		myjs('#feeApplicationCompanyName').attr('disabled','disabled');
		}
		else{
			if((myjs('#feeApplicationCompanyName').attr('disabled'))){
		    myjs('#feeApplicationCompanyName').attr('checked',false);
		    myjs('#feeApplicationCompanyName').attr('disabled','');
		}
		}
		if(GreatTime){
		myjs('#feeApplicationGreatTime').attr('checked',true);
		myjs('#feeApplicationGreatTime').attr('disabled','disabled');
		}
		else{
			if((myjs('#feeApplicationGreatTime').attr('disabled'))){
		     myjs('#feeApplicationGreatTime').attr('checked',false);
		     myjs('#feeApplicationGreatTime').attr('disabled','');
		}
		}
				if(Type){
		myjs('#feeApplicationType').attr('checked',true);
		myjs('#feeApplicationType').attr('disabled','disabled');
		}
		else{
			if((myjs('#feeApplicationType').attr('disabled'))){
		     myjs('#feeApplicationType').attr('checked',false);
		     myjs('#feeApplicationType').attr('disabled','');
		}
		}
				
		
	}
	
	Ext.onReady(function(){
		var	userTreeAction=eoms.appPath+'/xtree.do?method=userFromDept';

	   
		new xbox({
			btnId:'areatree',
			treeDataUrl:'${app}/partner/baseinfo/xtree.do?method=area',
			treeRootId:'${province}',treeRootText:'黑龙江',treeChkMode:'',treeChkType:'area',single:true,
			showChkFldId:'feeApplicationArea',saveChkFldId:'sy0feeApplicationCityT',returnJSON:false
		});
				
	})
	function openSelectAreas(){
	    window.open (eoms.appPath+'/partner/baseinfo/areaDeptTrees.do?method=selectAreas','newwindow','height=400,width=600,top=300,left=500,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no'); 
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


<form  id="checkAndSearchFrom" action="charge.do?method=staffContent" method="post">

<fieldset>
			<legend>请输入统计条件</legend>
<table class="formTable">
		<tr>
			
			<td class="label">
			地市
		</td>
		 <td>
			

            <input class="text" type="text" name="feeApplicationArea" id="feeApplicationArea" readonly="true" 
					    value="${checkAndSearchFrom.feeApplicationArea}" onblur="changeCheckBox()"
					 alt="allowBlank:false" />
		<input type="button" name="areatree" id="areatree" value="选择地域" class="btn" onblur="changeCheckBox()"/>
					 
		<input type="hidden" name="sy0feeApplicationCityT" id="sy0feeApplicationCityT"  onblur="changeCheckBox()"/> 			 
	
  		</td>
			
			
			
			
			
			<td class="label">代维公司</td>    
			<td><input type="text" class="text"
						name="fe0feeApplicationCompanyNameT" id="fe0feeApplicationCompanyNameT" onblur="changeCheckBox()"/></td>
						</tr>
		<tr>
			<td class="label">时间段</td>
			<td><input type="text" class="text"
						name="fe0feeApplicationGreatTimeT" id="fe0feeApplicationGreatTimeT" onblur="changeCheckBox()"/></td>
							
			<td class="label">费用类型</td>
			<td><input type="text" class="text"
						name="fe0feeApplicationTypeT" id="fe0feeApplicationTypeT" onblur="changeCheckBox()"/></td>			
		</tr>
	</table>
	</fieldset>

<fieldset>
			<legend>请选择统计项目</legend>
<table class="formTable">
<tr>
			
				<td class="label">地市</td>
				<td><input  id="feeApplicationCity" type="checkbox" name="cardNumber"
				value="sy0areaname" /></td>
				<td class="label">代维公司</td>	
				<td><input  id="feeApplicationCompanyName" type="checkbox" name="cardNumber"
				value="fe0feeApplicationCompanyName" /></td>
				<td class="label">时间段</td>	
				<td><input  id="feeApplicationGreatTime" type="checkbox" name="cardNumber"
				value="fe0feeApplicationGreatTime" /></td>
				<td class="label">费用类型</td>	
				<td><input  id="feeApplicationType" type="checkbox" name="cardNumber"
				value="fe0feeApplicationType" /></td>
				
				<input type="hidden" name="deleteIdss" id="deleteIdss" />
				<input type="hidden" name="checks" id="checks" />
				<input type="hidden" name="checkIds" id="checkIds" value="${checkList}" />
</tr>
</table>
</fieldset>

<input type="button" name="统计" value="统计" onclick="sendBox()"/>
	<!-- This hidden area is for batch deleting. -->
</form>

<div>
 <table  cellpadding="0" class="table contentStaffList" cellspacing="0">
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