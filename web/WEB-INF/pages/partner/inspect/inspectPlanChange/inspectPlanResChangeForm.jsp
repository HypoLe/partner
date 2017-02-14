<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<% 
	String path = request.getContextPath();
%>
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/scripts/My97DatePicker4.7.2/WdatePicker.js"></script>
<script type="text/javascript">

var myjs=jQuery.noConflict();
Ext.onReady(function(){
    v = new eoms.form.Validation({form:'taskOrderForm'});
    var changeType = myjs('#changeType').val();
    v.custom = function(){
    	myjs('#changeType').removeAttr('disabled');
    	if(validateDate()){
    		return false;
    	}
    	return true;
    };
    
    function validateDate(){
    	var isAssign = '${res.planId}';
    	var changeTypeVal = myjs('#changeType').val();
    	if(!isAssign && changeTypeVal==1){
    		if(myjs('#startTime').val() == ''){
    			alert('请设置变更后巡检开始日期 ');
    			return true;
    		}
    		if(myjs('#endTime').val() == ''){
    			alert('请设置变更后巡检结束日期 ');
    			return true;
    		}
    	}
    }
    
    //是否可以变更是否在本月执行
    var changeTypeRight='${changeTypeRight}';
    if(!changeTypeRight){
    	myjs("#changeType").attr("disabled","disabled");
    }
    
    var isAssignPlan = '${res.planId}';
    //如果资源未关联计划(即本月不执行)或者变更为不在本月执行
    if(!isAssignPlan && (changeType == 0 || changeType == -1)){
		myjs('#startTime').attr("disabled","disabled");
		myjs('#endTime').attr("disabled","disabled");
	}
	
	
	myjs('#changeType').change(function(){
		var changeType = myjs('#changeType').val();
		if(changeType == 0){//不执行
			myjs('#startTime').val('');
			myjs('#startTime').attr("disabled","disabled");
			myjs('#endTime').val('');
			myjs('#endTime').attr("disabled","disabled");
		}else{//执行
			myjs('#startTime').attr("disabled","");
			myjs('#endTime').attr("disabled","");
		}
	});
	
	myjs("#save").click(function(){
		myjs('#changeType').removeAttr('disabled');
		alert(1111);
		myjs("#taskOrderForm").submit();
	})
});
	function backList(){
		 window.location.href= '${app }/partner/inspect/inspectPlanChange.do?method=findInspectPlanResChangeList&planId=${planId}&id=${planChgId}';
	}
  
</script>
 
<br/>
	
<table id="taskOrderTable" class="formTable">
		<tr>
			<td class="label">巡检任务名称</td>
			<td class="content">${res.resName }</td>
			<td class="label">巡检专业</td>
			<td class="content">
				<eoms:id2nameDB id="${res.specialty }" beanId="ItawSystemDictTypeDao" />	
			</td>
		</tr>
		<tr>
			<td class="label">资源类型</td>
			<td class="content"><eoms:id2nameDB id="${res.resType}" beanId="ItawSystemDictTypeDao" /></td>
			<td class="label">地市</td>
			<td class="content">
				<eoms:id2nameDB id="${res.city}" beanId="tawSystemAreaDao" />
			</td>
		</tr>
		<tr>
			<td class="label">区县</td>
			<td class="content">
				<eoms:id2nameDB id="${res.country}" beanId="tawSystemAreaDao" />
			</td>
			<td class="label">巡检周期</td>
			<td class="content">
				${cycleMap[res.inspectCycle]}
			</td>
		</tr>
		<tr>
			<td class="label">周期开始日期</td>
			<td class="content">
				<bean:write name="res" property="cycleStartTime" format="yyyy-MM-dd"/>
			</td>
			<td class="label">周期结束日期</td>
			<td class="content">
				<bean:write name="res" property="cycleEndTime" format="yyyy-MM-dd"/>
			</td>
		</tr>
</table>	
    </br>
    </br>
不选择或者选择与变更前相同的值则代表不对该项进行变更
<form action="${app}/partner/inspect/inspectPlanChange.do?method=editInspectPlanResChange" method="post" id="taskOrderForm" name="taskOrderForm" >
	<table id="taskOrderTable" class="formTable">
		<input type="hidden" name="resId" value="${res.id }">
		<input type="hidden" name="resChgId" value="${resChg.id }">
		<input type="hidden" name="planChgId" value="${planChgId }" />
		<input type="hidden" name="planId" value="${res.planId }" />
		<input type="hidden" name="plan" value="${planId }" />
		<tr>
			<td class="label">
				巡检开始日期
			</td>
			<td class="content">
				<bean:write name="res" property="planStartTime" format="yyyy-MM-dd"/>
			</td>
			
			<td class="label">
				变更后巡检开始日期
			</td>
			<td class="content">
				<input class="Wdate text" type="text" value="<bean:write name="resChg" property="planStartTime" format="yyyy-MM-dd"/>"  name="chgPlanStartTime" id="startTime" 
				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'${minConfigDate}',maxDate:'#F{$dp.$D(\'endTime\')||\'${maxConfigDate}\'||\'%y-%M-%ld\'}',readOnly:true})" />
			</td>
		</tr>
		<tr>
			<td class="label">
				巡检结束日期
			</td>
			<td class="content">
				<bean:write name="res" property="planEndTime" format="yyyy-MM-dd"/>
			</td>
			
			<td class="label">
				变更后巡检结束日期
			</td>
			<td class="content">
				<input class="Wdate text" type="text" value="<bean:write name="resChg" property="planEndTime" format="yyyy-MM-dd"/>"  name="chgPlanEndTime" id="endTime" 
				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')||\'${minConfigDate}\'||\'%y-%M-01\'}',maxDate:'${maxConfigDate }',readOnly:true})" />
			</td>
		</tr>
		<tr>
			<td class="label">
				代维小组
			</td>
			<td class="content">
				<eoms:id2nameDB id="${res.executeObject}" beanId="partnerDeptDao"/>
			</td>
			
			<td class="label">
				变更后代维小组
			</td>
			<td class="content">
				<input type="text"  class="text"  name="partnerDeptName" id="partnerDeptName"  
					value="<eoms:id2nameDB id="${resChg.executeObject}" beanId="partnerDeptDao"/>" 
					alt="allowBlank:true" readonly="readonly"/>
				 <input name="chgExecuteObject" id="partnerDeptId"  value="${resChg.executeObject}" type="hidden" />
				 <eoms:xbox id="provTree" dataUrl="${app}/xtree.do?method=userFromComp&popedom=true&checktype=excludeBigNodAndLeaf&showPartnerLevelType=4"
						rootId="" rootText="公司树"  valueField="partnerDeptId" handler="partnerDeptName" 
						textField="partnerDeptName" checktype="dept" single="true" />
			</td>
		</tr>
		
		<tr>
			<td class="label">
				是否本月执行
			</td>
			<td class="content">
				<c:if test="${empty res.planId}" var="result">
					<font color="red">否</font>
				</c:if>
				<c:if test="${!result}">
					是
				</c:if>
			</td>
			
			<td class="label">
				变更后是否本月执行
			</td>
			<td class="content">
			
				<c:if test="${not empty res.planId}">
					<select name="changeType" id="changeType" class="select" value="${resChg.changeType}">
						<option value="-1" <c:if test="${resChg.changeType== -1}"> selected</c:if>>请选择</option>
						<option value="1" <c:if test="${resChg.changeType== 1}"> selected</c:if>>是</option>
						<option value="0" <c:if test="${resChg.changeType== 0}"> selected</c:if>>否</option>
					</select>
				</c:if>
				
				<c:if test="${empty res.planId}">
				<c:choose>
					<c:when test="${'week' eq res.inspectCycle or 'month' eq res.inspectCycle}">
						<select name="changeType" id="changeType" class="select" value="${resChg.changeType}" disabled="disabled">
							<option value="1" selected="selected" >是</option>
						</select><font color="red">变更后本月必须执行</font>
					</c:when>
					<c:otherwise>
						<select name="changeType" id="changeType" class="select" value="${resChg.changeType}">
							<option value="-1" <c:if test="${resChg.changeType== -1}"> selected</c:if>>请选择</option>
							<option value="1" <c:if test="${resChg.changeType== 1}"> selected</c:if>>是</option>
							<option value="0" <c:if test="${resChg.changeType== 0}"> selected</c:if>>否</option>
						</select>
					</c:otherwise>
				</c:choose>
				</c:if>
			</td>
		</tr>
	</table>
	<html:submit styleClass="btn" property="method.save" styleId="method.save" value="保存"></html:submit>	
	<html:reset styleClass="btn" styleId="method.reset" value="重置"></html:reset>
	<input type="button" value="返回" class="btn" onclick="backList();">
</form>

<%@ include file="/common/footer_eoms.jsp"%>