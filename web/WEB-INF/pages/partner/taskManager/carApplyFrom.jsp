<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script language="JavaScript" type="text/JavaScript"
	src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript">

var jq=jQuery.noConflict();
Ext.onReady(function(){
    v = new eoms.form.Validation({form:'taskOrderForm'});
});

function selectTaskType(){
	var taskType = jq("#taskType").val();
	jq("#taskId").val('');
	jq("#taskName").val('');
	jq("#taskName").show();
	jq("#taskName").attr("readonly","readonly");
	jq("#taskSelect").show();
	jq("#taskName").attr("disabled",false);
	switch(taskType) {
		case "1123301":
			jq("#url").val('${app}/partner/inspect/inspectPlanExecute.do?method=findInspectPlanMainByCheckUserList&carApprove=yes');
			break;
			
		case "1123302":
			jq("#url").val('${app}/sheet/pnrfaultdeal/pnrfaultdeal.do?method=showListsendundo&carApprove=yes');
			break;
			
		case "1123303":
			jq("#url").val('${app}/sheet/pnrcomplaint/pnrcomplaint.do?method=showListsendundo&carApprove=yes');
			break;
			
		case "1123304":
			jq("#url").val('${app}/sheet/pnrhiddentrouble/pnrhiddentrouble.do?method=showListsendundo&carApprove=yes');
			break;
			
		case "1123305":
			jq("#url").val('${app}/sheet/pnrcommontask/pnrcommontask.do?method=showListsendundo&carApprove=yes');
			break;
			
		case "1123306":
			jq("#url").val('${app}/sheet/pnrgenerateelectricity/pnrgenerateelectricity.do?method=showListsendundo&carApprove=yes');
			break;
			
		case "1123307":
			jq("#url").val('${app}/sheet/pnrrescheck/pnrrescheck.do?method=showListsendundo&carApprove=yes');
			break;
			
		case "1123308":
			jq("#url").val('');
			jq("#taskSelect").hide();
			jq("#taskName").attr("readonly",false);
			break;
	}
}

function selectTask(){
	var taskType = jq("#taskType").val();
	if(taskType = null || taskType == ''){
		Ext.Msg.alert('提示','请选择任务类型');
		return;
	}
	var url = jq("#url").val();
	var _sHeight = 600;
    var _sWidth = 820;
    var sTop=(window.screen.availHeight-_sHeight)/2;
    var sLeft=(window.screen.availWidth-_sWidth)/2;
	var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
	var pro =  window.showModalDialog(url,window,sFeatures);
	if(pro==null){
    	return;
    }
    var arr = pro.split("#");
	jq("#taskName").val(arr[1]);;
	jq("#taskId").val(arr[0]);
}
  
</script>
 
<br/>
		

<form action="${app}/partner/taskManager/carApprove.do?method=saveAddCarApply" method="post" id="taskOrderForm" >
	<input type="hidden" value="" name="url" class="text" id="url">
	<table id="taskOrderTable" class="formTable">
		<caption>
			<div class="header center">车辆申请</div>
		</caption>
		<tr>
			<td class="label">
			 车辆选择
			</td>
			<td class="content">
			<c:choose>
				<c:when test="${carNumber!=null&&carNumber!=''}">  
					 ${carNumber}
			 		</c:when>
				 <c:otherwise>
				<select class="select" alt="allowBlank:false" name="carNum">
					<option>--请选择--</option>
					<c:forEach var="list" items="${carList}">
						<option value="${list.carNumber}">${list.carNumber}</option>
					</c:forEach>
				</select>
				</c:otherwise>
				</c:choose>
			</td>
			<td class="label">
			任务类型
			</td>
			<td class="content">
				<eoms:comboBox name="taskType" id="taskType" defaultValue="" styleClass="select" onchange="selectTaskType();"
					initDicId="11233" alt="allowBlank:false"/>
			</td>
		</tr>
		<tr>
			<td class="label">
				任务选择
			</td>
			<td class="content">
				<input type="hidden" id="taskId" class="text" name="taskId"  />		
				<input type="text" id="taskName" class="text" name="taskName" readonly="readonly" alt="allowBlank:false"/>		
				<input type="button" onclick="selectTask();"  value="选择" class="btn" id="taskSelect"  />		
			</td>
			<td class="label">
				审批人
			</td>
			<td class="content">
				<input type="text"  class="text"  name="checkStaffName" id="checkStaffName"  
					value="" alt="allowBlank:false" readonly="readonly"/>
		   		<input type="hidden" name="checkStaff" id="checkStaff"  value=""/>
				<eoms:xbox id="checkStaffName" dataUrl="${app}/xtree.do?method=userFromDept"  
				rootId="${rootid}" rootText="用户树"  valueField="checkStaff" handler="checkStaffName" 
				textField="checkStaffName" checktype="user" single="true" />
			
			</td>
		</tr>
		<tr>
			<td class="label">备注</td>
			<td class="content" colspan="3">
				<textarea class="textarea max" name="remark" 
					id="content">${planMain.content}</textarea>
			</td>
			
		</tr>
</table>
<br/>
<input type="submit" value="保存" class="btn" />

</form>	
	
  
<%@ include file="/common/footer_eoms.jsp"%>
