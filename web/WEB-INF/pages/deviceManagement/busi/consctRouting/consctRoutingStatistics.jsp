<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

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
		var area_name = myjs('#projectName_c').val();
		var accName=myjs('#location_c').val();
		if(area_name){
		myjs('#projectName').attr('checked',true);
		myjs('#projectName').attr('disabled','disabled');
		}
		else{
			if((myjs('#projectName').attr('disabled'))){
		myjs('#projectName').attr('checked',false);
		myjs('#projectName').attr('disabled','');
		}
		}
		if(accName){
		myjs('#location').attr('checked',true);
		myjs('#location').attr('disabled','disabled');
		}
		else{
			if((myjs('#location').attr('disabled'))){
		myjs('#location').attr('checked',false);
		myjs('#location').attr('disabled','');
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
	action="consctRouting.do?method=statistics" method="post">

	<fieldset>
		<legend>
			请输入统计条件
		</legend>
		<table class="formTable">
			<tr>
				<td class="label">
					项目名称
				</td>
				<td>
					<input type="text" class="text" name="projectName" value="${projectName }"
						id="projectName_c" onblur="changeCheckBox()" />
				</td>
				<td class="label">
					施工位置
				</td>
				<td>
					<input type="text" class="text" name="location" id="location_c" value="${location}"
						onblur="changeCheckBox()" />
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
					项目名称
				</td>
				<td>
					<input id="projectName" type="checkbox" name="cardNumber"
						value="projectName" />
				</td>
				<td class="label">
					施工位置
				</td>
				<td>
					<input id="location" type="checkbox" name="cardNumber"
						value="location" />
				</td>
				<td class="label">
					施工路由中继段风险级别
				</td>
				<td>
					<input id="riskLevelTypeLikedict" type="checkbox" name="cardNumber"
						value="riskLevelTypeLikedict" />
				</td>
				<td class="label">
					维护级别
				</td>
				<td>
					<input id="maintainLevelTypeLikedict" type="checkbox"
						name="cardNumber" value="maintainLevelTypeLikedict" />
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
	<form>

		<div>
			<table cellpadding="0" class="table" cellspacing="0">
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
										<td rowspan="${td.rowspan}">

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