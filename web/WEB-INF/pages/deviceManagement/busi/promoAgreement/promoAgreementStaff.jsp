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
	//alert(checks[i].toString());
	checkValue ='#'+checks[i].toString();
	myjs(checkValue).attr('checked',true);
	}
	}
	var	userTreeAction=eoms.appPath+'/xtree.do?method=userFromDept';

	new xbox({
		btnId:'areatree',
		treeDataUrl:'${app}/partner/baseinfo/xtree.do?method=area',
		treeRootId:'${province}',treeRootText:'黑龙江',treeChkMode:'',treeChkType:'area',single:true,
		showChkFldId:'de0areaNameT',saveChkFldId:'de0areaIdT',returnJSON:false
	});

			
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
		var areaName = myjs('#de0areaNameT').val();
		var itemName=myjs('#de0itemNameT').val();
		var repeaterSection=myjs('#de0repeaterSectionT').val();
		var auditId=myjs('#userName').val();
	
		if(areaName){
		myjs('#de0areaIdTypeLikeArea').attr('checked',true);
		myjs('#de0areaIdTypeLikeArea').attr('disabled','disabled');
		}
		else{
			if((myjs('#de0areaIdTypeLikeArea').attr('disabled'))){
		myjs('#de0areaIdTypeLikeArea').attr('checked',false);
		myjs('#de0areaIdTypeLikeArea').attr('disabled','');
		}
		}
		if(itemName){
		myjs('#de0itemName').attr('checked',true);
		myjs('#de0itemName').attr('disabled','disabled');
		}
		else{
			if((myjs('#de0itemName').attr('disabled'))){
		myjs('#de0itemName').attr('checked',false);
		myjs('#de0itemName').attr('disabled','');
		}
		}
		if(repeaterSection){
		myjs('#de0repeaterSection').attr('checked',true);
		myjs('#de0repeaterSection').attr('disabled','disabled');
		}
		else{
			if((myjs('#de0repeaterSection').attr('disabled'))){
		myjs('#de0repeaterSection').attr('checked',false);
		myjs('#de0repeaterSection').attr('disabled','');
		}
		}
		if(auditId){
		myjs('#de0auditId').attr('checked',true);
		myjs('#de0auditId').attr('disabled','disabled');
		}
		else{
			if((myjs('#de0auditId').attr('disabled'))){
		myjs('#de0auditId').attr('checked',false);
		myjs('#de0auditId').attr('disabled','');
		}
		}		
	}
	
</script>



<form id="checkAndSearchFrom"
	action="promoagreement.do?method=promoAgreementStaff" method="post">

<fieldset><legend>请输入统计条件</legend>
<table class="formTable">
<tr>
	
			<td class="label">
			所属地市
		</td>
		 <td>
            <input class="text" type="text"  id="de0areaNameT" readonly="true"        
                   onblur="changeCheckBox()"  alt="allowBlank:false" />
		<input type="button" name="areatree" id="areatree" value="选择地域" class="btn" onblur="changeCheckBox()"/>				 
		<input type="hidden" name="de0areaIdT" id="de0areaIdT"  onblur="changeCheckBox()"/> 			 
  		</td>

		<td class="label">项目名称</td>
		<td><input id="de0itemNameT" type="text" name="de0itemNameT"  class="text"
			 onblur="changeCheckBox()"/></td>
			 </tr>
			 <tr>
		<td class="label">中继段名称</td>
				<td><input  class="text" id="de0repeaterSectionT" type="text" name="de0repeaterSectionT"
			 onblur="changeCheckBox()"/></td>
			<td class="label">审批人</td>
		<td>
		<input type="text"  class="text"  name="userName" id="userName" 
	  alt="allowBlank:false" readonly="readonly"  onblur="changeCheckBox()"/>
		 <input type="hidden" name="de0auditIdT" id="de0auditIdT"  />
			<eoms:xbox id="userName" dataUrl="${app}/xtree.do?method=userFromDept"  
		rootId="2" rootText="用户树"  valueField="de0auditIdT" handler="userName" 
		textField="userName" checktype="user" single="true" />	
		</td>
			</tr>
</table>
</fieldset>

<fieldset><legend>请选择统计项目</legend>
<table class="formTable">
	<tr>

		<td class="label">所属地市</td>
		<td><input id="de0areaIdTypeLikeArea" type="checkbox" name="cardNumber"
			value="de0areaIdTypeLikeArea" /></td>
		<td class="label">项目名称</td>
		<td><input id="de0itemName" type="checkbox" name="cardNumber"
			value="de0itemName" /></td>
		<td class="label">中继段名称</td>
		<td><input id=de0repeaterSection type="checkbox" name="cardNumber"
			value="de0repeaterSection" /></td>
		<td class="label">审批人</td>
		<td><input id="de0auditIdTypeLikeUser" type="checkbox" name="cardNumber"
			value="de0auditIdTypeLikeUser" /></td>
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