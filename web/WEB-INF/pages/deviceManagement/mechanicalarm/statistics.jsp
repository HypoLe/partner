
<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js">
</script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js">
</script>
<script type="text/javascript">

var myjs = jQuery.noConflict();
function sendBox() {
	var cardNumberList = document.getElementsByName("cardNumber");
	var idResult = "";
	var checkId = "";
	for (i = 0; i < cardNumberList.length; i++) {
		if (cardNumberList[i].checked) {
			var myTempStr = cardNumberList[i].value;
			idResult += myTempStr.toString() + ";";
			var tempId = cardNumberList[i].id;
			checkId += tempId.toString() + ";";
		}
	}
	$("deleteIds").value = idResult.toString();
	$("checks").value = checkId.toString();
	if (idResult == "") {
		alert("请至少选择一个统计项");
		return false;
	}
	document.getElementById("checkAndSearchFrom").submit();
}
function changeCheckBox(){
		var projectName = myjs('#projectName').val();
		var belongTheLocal=myjs('belongTheLocal').val();
		var constructionMachineryName=myjs('#constructionMachineryName').val();
		
		
		if(projectName&&projectName!=''){
		myjs('#projectName1').attr('checked',true);
		myjs('#projectName1').attr('disabled','disabled');
		}
		else{
			if((myjs('#projectName1').attr('disabled'))){
		myjs('#projectName1').attr('checked',false);
		myjs('#projectName1').attr('disabled','');
		}
		}
		if(constructionMachineryName&&constructionMachineryName!=''){
		myjs('#constructionMachineryName1').attr('checked',true);
		myjs('#constructionMachineryName1').attr('disabled','disabled');
		}
		else{
			if((myjs('#d0area_name').attr('disabled'))){
		myjs('#constructionMachineryName1').attr('checked',false);
		myjs('#constructionMachineryName1').attr('disabled','');
		}
		}
		
	}
Ext.onReady(function() {
	var check = document.getElementById("checkIds");
	if (check && check.value != "") {
		checkV = check.value
		var checks = checkV.toString().split(";");
		var cardNumberList = document.getElementsByName("cardNumber");
		for ( var i = 0; i < checks.length - 1; i++) {
			//alert(checks[i].toString());
		checkValue = '#' + checks[i].toString();
		myjs(checkValue).attr('checked', true);
	}
}
});
</script>
<form id="checkAndSearchFrom"
	action="mechanicalArmManagementStatistic.do?method=mechanicalArmList"
	method="post">
	<fieldset>
		<legend>
			请输入统计条件
		</legend>
		<table class="formTable">
			<tr>
<%---
				<td class="label">
					所属地市
				</td>
				<td>
					<input type="text" class="text"
						 maxLength="100"
						name="belongTheLocal" id="belongTheLocal"
						 />
				</td>---%>
				<td class="label">
					项目名称
				</td>
				<td>
					<input type="text" class="text"
						 maxLength="100"
						name="projectName" id="projectName" />
				</td>
				<td class="label">
					大型施工机械名称
				</td>
				<td>
					<input type="text" class="text"
						maxLength="100"
						name="constructionMachineryName" id="constructionMachineryName"
						 />
				</td>
			</tr>


		</table>
	</fieldset>

	<fieldset>
		<legend>
			请选择统计项目
		</legend>
		<table class="formTable">
		<tr>
		<td class="label">
					所属地市
				</td>
				<td>
					<input type="checkbox" 
						value="belongTheLocal"
						name="cardNumber" id="belongTheLocal"
						 />
				</td>
			

				<td class="label">
					项目名称
				</td>
				<td>
					<input id="projectName1" type="checkbox" name="cardNumber"
						value="projectName" />
						
				</td>

				<td class="label">
					大型施工 机械名称
				</td>
				<td>
					<input id="constructionMachineryName1" type="checkbox" name="cardNumber"
						value="constructionMachineryName" />
				</td>

				<td class="label">
					大型施工 机械施工地点
				</td>
				<td>
					<input id="mechanicalArm_workyard" type="checkbox" name="cardNumber"
						value="mechanicalArm_workyard" />
				</td>
				<input type="hidden" name="deleteIds" id="deleteIds" />
				<input type="hidden" name="checks" id="checks" />
				<input type="hidden" name="checkIds" id="checkIds"
					value="${checkList}" />
			</tr>
		</table>
	</fieldset>

	<input type="button" name="统计" value="统计" onclick="sendBox()" />
	<!-- This hidden area is for batch deleting. -->
</form>

<div>
	<table cellpadding="0" class="table protocolMainList" cellspacing="0">
		<thead>
			<tr>
				<logic-el:present name="headList">
					<c:forEach items="${headList}" var="headlist">
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
										<a href="javascript:void(0);"
											onclick="window.open('${app}${td.url}');">${td.name}</a>
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