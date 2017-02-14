<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script language="JavaScript" type="text/JavaScript" src="${app}/scripts/module/partner/util.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/scripts/My97DatePicker4.7.2/WdatePicker.js"></script>
<script type="text/javascript">

var myjs=jQuery.noConflict();
Ext.onReady(function(){
    v = new eoms.form.Validation({form:'taskOrderForm'});
    v.custom = function(){ 
    	return true;
    };
    
    myjs("#save").click(function(){
		myjs("#taskOrderForm").submit();
	})
});

  
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
		</tr>
</table>	
    </br>
    </br>
不选择或者选择与变更前相同的值则代表不对该项进行变更
<form action="${app}/partner/inspect/inspectPlanChange.do?method=editInspectPlanResChangeBurst" method="post" id="taskOrderForm" name="taskOrderForm" >
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
				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d',maxDate:'#F{$dp.$D(\'endTime\')}',readOnly:true})" />
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
				onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')||\'%y-%M-%d\'}',readOnly:true})" />
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
				 <input name="chgExecuteObject" id="partnerDeptId"  value="" type="hidden" />
				 <eoms:xbox id="provTree" dataUrl="${app}/xtree.do?method=userFromComp&popedom=true&checktype=excludeBigNodAndLeaf&showPartnerLevelType=4"
						rootId="" rootText="公司树"  valueField="partnerDeptId" handler="partnerDeptName" 
						textField="partnerDeptName" checktype="dept" single="true" />
			</td>
		</tr>
		
		<tr>
			<td class="label">
				是否与本计划关联
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
				变更后是否与本计划关联
			</td>
			<td class="content">
				<select name="changeType" id="changeType" class="select" value="${resChg.changeType}">
					<option value="-1" <c:if test="${resChg.changeType== -1}"> selected</c:if>>请选择</option>
					<option value="1" <c:if test="${resChg.changeType== 1}"> selected</c:if>>是</option>
					<option value="0" <c:if test="${resChg.changeType== 0}"> selected</c:if>>否</option>
				</select>
			</td>
		</tr>
	</table>
	<html:submit styleClass="btn" property="method.save" styleId="method.save" value="保存"></html:submit>	
	<html:reset styleClass="btn" styleId="method.reset" value="重置"></html:reset>
</form>

<%@ include file="/common/footer_eoms.jsp"%>