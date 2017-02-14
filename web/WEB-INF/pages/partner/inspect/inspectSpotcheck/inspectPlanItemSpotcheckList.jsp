<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript">
var jq=jQuery.noConflict();
Ext.onReady(function(){
    v = new eoms.form.Validation({form:'taskOrderForm'});
    v.custom = function(){
    	return true;
    };
});
function backlist(planId){
	window.location="${app}/partner/inspect/inspectSpotcheckAction.do?method=findInspectPlanResSpotcheckList&planId="+planId;
}
</script>

<form action="${app}/partner/inspect/inspectSpotcheckAction.do?method=saveInspectSpotcheck" 
		method="post" id="taskOrderForm" name="taskOrderForm" >
	<input type="hidden" name="inspectPlanResId" value="${inspectPlanResId}">
	<input type="hidden" name="planId" value="${planId}">
<table class="table list" cellpadding="0" cellspacing="0">
	<thead>
		<tr>
			<th>设备类别</th>
			<th>扣分规则</th>
			<th>总分</th>
			<th>巡检项目</th>
			<th>巡检内容</th>
			<th>巡检结果</th>
			<th>是否异常</th>
			<th>异常内容</th>
			<th>扣分</th>
		</tr>
	</thead>
	<c:forEach items="${list}" var="map">
		<c:if test="${empty map.itemList }" var="result">
      		<tr>
				<td>
					${map.itemgroup}
				</td>
				<td>${map.markrule}</td>
				<td>${map.score}</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
      	</c:if>
      	<c:if test="${!result}">
      		<c:forEach items="${map.itemList}" var="obj" varStatus="status"> 
				<tr>
					<c:if test="${status.count==1}">
						<td rowspan="${map.count}">
							${map.itemgroup}
						</td>
						<td rowspan="${map.count}">${map.markrule}</td>
						<td rowspan="${map.count}">${map.score}</td>
					</c:if>
					
					<td>${obj.item}</td>
					<td>${obj.content}</td>
					
					<td>
						<c:if test="${obj.input_type eq 'radio'}">
							<eoms:id2nameDB id="${obj.item_result}" beanId="ItawSystemDictTypeDao" />
						</c:if>
						<c:if test="${obj.input_type eq 'multiple'}">
						    <c:forTokens items="${obj.item_result}" delims="|" var="default" varStatus="statue">
								<c:if test="${statue.last}" var="it">
									<eoms:id2nameDB beanId="ItawSystemDictTypeDao" id="${default}" /> 
								</c:if>
								<c:if test="${!it}">
								<eoms:id2nameDB beanId="ItawSystemDictTypeDao" id="${default}" />  | 
								</c:if>
							</c:forTokens>
						</c:if>
						<c:if test="${obj.input_type eq 'text'}">${obj.item_result}</c:if>
						<c:if test="${obj.input_type eq 'number'}">${obj.item_result}</c:if>
					</td>
					
					<td>
						<c:choose>
							<c:when test="${obj.expFlag == 1}">否</c:when>
							<c:when test="${obj.expFlag == 0}">是</c:when>
							<c:otherwise>
								无设备
							</c:otherwise>
						</c:choose>
						
					</td>
					<td>${obj.expContent}</td>
					<td>
						<c:if test="${detail=='yes'}" var="result">
							${obj.score}
						</c:if>
						<c:if test="${!result}">
							<input type="text" name="score_${obj.spotTmpId}_${obj.planItemId}" alt="re:/^(\d+)(\.\d+)?$/,re_vt:'请输入正整数或小数',maxLength:7"
							id="score" style="width:30px;" value="0"/>
						</c:if>
					</td>
				</tr>
	  		</c:forEach>
      	</c:if>
    </c:forEach>
</table>
	<c:if test="${detail!='yes'}" var="result">
		<html:submit styleClass="btn" property="method.save" styleId="method.save" value="保存"></html:submit>
	</c:if>
	 <input type="button" value="返回" onclick="backlist('${planId}');" class="btn">
</form>
</br>

<%@ include file="/common/footer_eoms.jsp"%>