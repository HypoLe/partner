<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">

<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<form action="${app}/partner/inspect/inspectPlanResQc.do?method=saveResQualityCheck" method="post" id="taskOrderForm" name="taskOrderForm" >
	<input type="hidden" name="planResId" value="${planRes.id}">
	<input type="hidden" name="planId" value="${planRes.planId}">
	<table id="taskOrderTable" class="formTable">
		<tr>
			<td class="label">
			 元任务名称 
			</td>
			<td colspan="3" class="content">
				${resname }
			</td>
		</tr>
		<tr>
		  	<td class="label">评分栏*</td>
		    <td colspan='3'>
		    	巡检及时率(10)<input type="text" name="scoreInTime" id="scoreInTime" size="5" value="${check.scoreInTime }" readonly="readonly" /> |
		    	巡检规范性(80)<input type="text" name="scoreStandard" id="scoreStandard" size="5" value="${check.scoreStandard }" readonly="readonly" />  |
		    	巡检问题通报及时性(5)<input type="text" name="scoreFinish" id="scoreFinish" size="5" value="${check.scoreFinish }" readonly="readonly" />  |
		    	现场抽查打分(5) <input type="text" name="scoreStatisfied" id="scoreStatisfied" size="5" value="${check.scoreStatisfied }" readonly="readonly" /> &nbsp;&nbsp;
		    </td>
	    </tr>
	    <tr>
			<td class="label">总分</td>
			<td colspan='3' class="content">
				<span id="scoreTotalShow"></span>
				${check.scoreTotal }
			</td>
		</tr>
		<tr>
			<td class="label">满意度</td>
			<td colspan='3' class="content">
				<eoms:id2nameDB id="${check.satisfaction}" beanId="ItawSystemDictTypeDao" />
			</td>
		</tr>
		<tr>
			<td class="label">
				备注*
			</td>
			<td colspan="3" class="content">
			<pre>${check.remark }</pre>		
			</td> 
		</tr>
</table>
</form>

<%@ include file="/common/footer_eoms.jsp"%>
