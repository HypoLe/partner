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
		showChkFldId:'advertisementCity',saveChkFldId:'advertisementArea',returnJSON:false
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
	$("deleteIds").value=idResult.toString();
	$("checks").value=checkId.toString();
		if(idResult==""){
		alert("请至少选择一个统计项");
				return false;}
		document.getElementById("checkAndSearchFrom").submit();
	}
	
	function changeCheckBox(){
		var creatdept = myjs('#de0creatdeptT').val();
		if(creatdept){
		myjs('#de0creatdept').attr('checked',true);
		myjs('#de0creatdept').attr('disabled','disabled');
		}
		else{
			if((myjs('#de0creatdept').attr('disabled'))){
		myjs('#de0creatdept').attr('checked',false);
		myjs('#de0creatdept').attr('disabled','');
		}
		}
		
	}
	
	
	
	

	
	
	
</script>



<form id="checkAndSearchFrom"
	action="advertisementPlanAction.do?method=state" method="post">
<!--  
<fieldset><legend>请输入统计条件</legend>
<table class="formTable">
<tr>

		<td class="label">所属地市</td>	
			<td>		
				<input class="text" type="text" name="advertisementCity"
						id="advertisementCity" readonly="true" value="" onchange="changeCheckBox()"
						alt="allowBlank:false" /> 
						<input type="button" name="areatree" id="areatree" value="选择所属地市" class="btn" /> 
						<input type="hidden" name="advertisementArea" id="advertisementArea" />
				</td>
			</tr>
</table>
</fieldset>
-->
<fieldset><legend>请选择统计项目</legend>
<table class="formTable">
	<tr>

		<td class="label"><input id="ck_advertisementArea" type="checkbox" name="cardNumber"
			value="cityTypeLikedict"  checked="checked"/>所属地市</td>
		
			
		
	</tr>
	
</table>
		<input type="hidden" name="deleteIds" id="deleteIds" />
		<input type="hidden" name="checks" id="checks" />
		<input type="hidden" name="checkIds" id="checkIds"
			value="${checkList}" />
</fieldset>

<input type="button" name="统计" value="统计" onclick="sendBox()" class="btn"/> <!-- This hidden area is for batch deleting. -->
</form>
<br>

<div>
<table cellpadding="0" class="table contentStaffList" cellspacing="0">
	<thead>
		<c:forEach items="${headList}" var="headlist">
					<th>${headlist}</th>

				</c:forEach>
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