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
		showChkFldId:'mediaPublicitytCity',saveChkFldId:'de0creatdeptT',returnJSON:false
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
		var creatdept = myjs('#mediaPublicitytCity').val();
		var medianame=myjs('#de0medianameT').val();
		var mediacontent=myjs('#de0mediacontentT').val();
		var approvaluser=myjs('#approvalUserName').val();
		if(creatdept){
		myjs('#de0creatdeptTypeLikeArea').attr('checked',true);
		myjs('#de0creatdeptTypeLikeArea').attr('disabled','disabled');
		}
		else{
			if((myjs('#mediaPublicitytCity').attr('disabled'))){
		myjs('#de0creatdeptTypeLikeArea').attr('checked',false);
		myjs('#de0creatdeptTypeLikeArea').attr('disabled','');
		}
		}
		if(medianame){
		myjs('#de0medianame').attr('checked',true);
		myjs('#de0medianame').attr('disabled','disabled');
		}
		else{
			if((myjs('#de0medianame').attr('disabled'))){
		myjs('#de0medianame').attr('checked',false);
		myjs('#de0medianame').attr('disabled','');
		}
		}
		if(mediacontent){
		myjs('#de0mediacontent').attr('checked',true);
		myjs('#de0mediacontent').attr('disabled','disabled');
		}
		else{
			if((myjs('#de0mediacontent').attr('disabled'))){
		myjs('#de0mediacontent').attr('checked',false);
		myjs('#de0mediacontent').attr('disabled','');
		}
		}
		if(approvaluser){
		myjs('#de0approvaluser').attr('checked',true);
		myjs('#de0approvaluser').attr('disabled','disabled');
		}
		else{
			if((myjs('#approvalUserName').attr('disabled'))){
		myjs('#de0approvaluser').attr('checked',false);
		myjs('#de0approvaluser').attr('disabled','');
		}
		}
		
	}
	
	
	
	

	
	
	
</script>



<form id="checkAndSearchFrom"
	action="mediaPublicity.do?method=staffMediaPublicity" method="post">

<fieldset><legend>请输入统计条件</legend>
<table class="formTable">
<tr>

		<td class="label">所属地市</td>
		<%-- 			<td><input id="de0creatdeptT" type="text" name=de0creatdeptT
			 onblur="changeCheckBox()"/></td>--%>
		 		<td><input class="text" type="text" name="mediaPublicitytCity"
				id="mediaPublicitytCity" readonly="true"  onblur="changeCheckBox()"
				alt="allowBlank:false" /> 
				<input type="button" name="areatree" id="areatree" value="选择地点" class="btn" /> 
				<input type="hidden" name="de0creatdeptT" id="de0creatdeptT" />
				
		<td class="label">项目名称</td>
		<td><input id="de0medianameT" type="text" name="de0medianameT"
			 onblur="changeCheckBox()"/></td>
       		<td class="label">媒体宣传内容</td>
		<td><input id="de0mediacontentT" type="text" name="de0mediacontentT"
			 onblur="changeCheckBox()" /></td>
			<td class="label">审批人</td>
		<td>

		
				<input type="text"  class="text"  name="approvalUserName" id="approvalUserName"  value="" alt="allowBlank:false" readonly="readonly" onblur="changeCheckBox()"/>
		   <input type="hidden" name="de0approvaluserT" id="de0approvaluserT"  value="" />
			<eoms:xbox id="approvalUserName" dataUrl="${app}/xtree.do?method=userFromDept"  
		rootId="2" rootText="用户树"  valueField="de0approvaluserT" handler="approvalUserName" 
		textField="approvalUserName" checktype="user" single="true" />
		</td>
		</td>
			</tr>
</table>
</fieldset>

<fieldset><legend>请选择统计项目</legend>
<table class="formTable">
	<tr>

		<td class="label">所属地市</td>
		<td><input id="de0creatdeptTypeLikeArea" type="checkbox" name="cardNumber"
			value="de0creatdeptTypeLikeArea" /></td>
		<td class="label">项目名称</td>
		<td><input id="de0medianame" type="checkbox" name="cardNumber"
			value="de0medianame" /></td>
		<td class="label">媒体宣传内容</td>
				<td><input id=de0mediacontent type="checkbox" name="cardNumber"
			value="de0mediacontent" /></td>
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