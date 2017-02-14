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
	checkValue ='#'+checks[i].toString();
	myjs(checkValue).attr('checked',true);
	}
	}
	});
	
	
	
</script>

<%--
	private String id; // 主键
	private String reportUser; //上报人，就是当前填写隐患信息的登录人员
	private String reportTime; //上报时间
	private String areaId;//地区
	private String hiddenTroubleType;//隐患类型
	private String isImportant;//是否重要隐患（0：否，1：是）
	private String majorType;//专业类型
	private String checkPoint;//巡检点
	private double longitude;//经度
	private double latitude;//纬度
	private String checkUser;//上报巡检员 pnr_user
	private String checkUserDept;//代维公司 （上报人所属代维公司）pnr_dept
	private String phone;//联系电话
	private String email;//email信息
	
	private String processStatus;//处理状态
	private String processUser;//处理人
	private String handlTime;//处理时间
	private String handleMsg;//处理信息
	
	private String deleted;//删除标记：0表示未删除，1表示逻辑删除
	private String deletedTime;//删除时间
	private String remark;//备注
--%>
<form id="checkAndSearchFrom"
	action="hiddenTrouble.do?method=statistics" method="post">
	<fieldset>
		<legend>
			请选择统计项目
		</legend>
		<table class="formTable">
			<tr>
				<td class="label">
					地区
				</td>
				<td>
					<input id="areaIdTypeLikeArea" type="checkbox" name="cardNumber"
						value="areaIdTypeLikeArea" />
				</td>
				<td class="label">
					隐患类型
				</td>
				<td>
					<input id="hiddenTroubleTypeTypeLikedict" type="checkbox" name="cardNumber"
						value="hiddenTroubleTypeTypeLikedict" />
				</td>
			</tr>
			<tr>
				<td class="label">
					是否重要隐患
				</td>
				<td>
					<input id="isImportantTypeLikedict" type="checkbox" name="cardNumber"
						value="isImportantTypeLikedict" />
				</td>
				<td class="label">
					专业类型
				</td>
				<td>
					<input id="majorTypeTypeLikedict" type="checkbox"
						name="cardNumber" value="majorTypeTypeLikedict" />
				</td>
			</tr>
			<tr>
				<td class="label">
					处理状态
				</td>
				<td colspan="3">
					<input id="processStatus" type="checkbox" name="cardNumber"
						value="processStatus" />
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
								<c:forEach items="${tdList}" var="td" varStatus="ss">
									<c:if test="${td.show}">
										<td rowspan="${td.rowspan}">
											${td.name}
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