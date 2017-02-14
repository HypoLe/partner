<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript">

var myjs=jQuery.noConflict();
Ext.onReady(function(){
    v = new eoms.form.Validation({form:'taskOrderForm'});
    v.custom = function(){ 
    	return true;
    };
    
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

	<table id="taskOrderTable" class="formTable">
		<tr>
			<td class="label">
				巡检开始日期
			</td>
			<td class="content">
				<bean:write name="resChg" property="prePlanStartTime" format="yyyy-MM-dd"/>
			</td>
			
			<td class="label">
				变更后巡检开始日期
			</td>
			<td class="content">
				<bean:write name="resChg" property="planStartTime" format="yyyy-MM-dd"/>
			</td>
		</tr>
		<tr>
			<td class="label">
				巡检结束日期
			</td>
			<td class="content">
				<bean:write name="resChg" property="prePlanEndTime" format="yyyy-MM-dd"/>
			</td>
			
			<td class="label">
				变更后巡检结束日期
			</td>
			<td class="content">
				<bean:write name="resChg" property="planEndTime" format="yyyy-MM-dd"/>
			</td>
		</tr>
		<tr>
			<td class="label">
				代维小组
			</td>
			<td class="content">
				<eoms:id2nameDB id="${resChg.preExecuteObject}" beanId="partnerDeptDao"/>
			</td>
			
			<td class="label">
				变更后代维小组
			</td>
			<td class="content">
				<eoms:id2nameDB id="${resChg.executeObject}" beanId="partnerDeptDao"/>
			</td>
		</tr>
		
		<tr>
			<td class="label">
				是否本月执行
			</td>
			<td class="content">
				<c:if test="${resChg.preChangeType==1}">
					是
				</c:if>
				<c:if test="${resChg.preChangeType==0}">
					否
				</c:if>
			</td>
			
			<td class="label">
				变更后是否本月执行
			</td>
			<td class="content">
				<c:if test="${empty resChg.changeType}">
				</c:if>
				<c:if test="${resChg.changeType==1}">
					是
				</c:if>
				<c:if test="${resChg.changeType==0}">
					否
				</c:if>
			</td>
		</tr>
</table>


<%@ include file="/common/footer_eoms.jsp"%>