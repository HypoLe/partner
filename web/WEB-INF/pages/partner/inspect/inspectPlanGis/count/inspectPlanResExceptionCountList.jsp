<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/common/extlibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript">
var jq=jQuery.noConflict();
</script>

 <form  action="${app}/partner/inspect/inspectPlanGisAction.do?method=saveInspectPlanGis" method="post">
 <input type="hidden" value="提交"/>
  </form>
  
  
  <table class="table list" cellpadding="0" cellspacing="0">
  	<thead>
  		<tr>
  			<c:if test="${ordercode==1}">
  			<th>地市</th>
  			</c:if>
			<c:if test="${ordercode==2}">
  			<th>区县</th>
  			</c:if>
			<c:if test="${ordercode==3}">
  			<th>维护组</th>
  			</c:if>
  			<th>任务数</th>
  			<th>异常数</th>
  			<th>异常百分比</th>
  			<th>未处理数</th>
  			<th>已处理数</th>
  			<th>已处理百分比</th>
  		</tr>
  	</thead>
  
  	<c:forEach var="list" items="${resultList}">
  		<tr>
  			<td>
  				<c:if test="${ordercode==1}"><a href="${app}/partner/inspect/inspectPlanResCount.do?method=inspectPlanResExceptionCount&rootAreaId=${list.cityId}">${list.cityName }</a></c:if>
  				<c:if test="${ordercode==2}"><a href="${app}/partner/inspect/inspectPlanResCount.do?method=inspectPlanResExceptionCount&rootAreaId=${list.countryId}">${list.countryName }</a></c:if>
  				<c:if test="${ordercode==3}">${list.deptName }</c:if>
  			</td>
  			<td>
  				<c:if test="${ordercode==1}">
  					<a href="${app}/partner/inspect/inspectPlanResCount.do?method=inspectPlanResExceptionCountDetail&rootAreaId=${list.cityId}&ordercode=${ordercode}">${list.resNo }</a>
  				</c:if>
  				<c:if test="${ordercode==2}">
  					<a href="${app}/partner/inspect/inspectPlanResCount.do?method=inspectPlanResExceptionCountDetail&rootAreaId=${list.countryId}&ordercode=${ordercode}">${list.resNo }</a>
  				</c:if>
  				<c:if test="${ordercode==3}">
  					<a href="${app}/partner/inspect/inspectPlanResCount.do?method=inspectPlanResExceptionCountDetail&rootAreaId=${list.countryId}&ordercode=${ordercode}&deptId=${list.deptId}">${list.resNo }</a>
  				</c:if>
  			</td>
  			<td>
  				<c:if test="${ordercode==1}">	
  					<a href="${app}/partner/inspect/inspectPlanResCount.do?method=inspectPlanResExceptionCountDetail&rootAreaId=${list.cityId}&exceptionFlag=0&ordercode=${ordercode}">${list.exceptionNo }</a>
  				</c:if>
  				<c:if test="${ordercode==2}">
  					<a href="${app}/partner/inspect/inspectPlanResCount.do?method=inspectPlanResExceptionCountDetail&rootAreaId=${list.countryId}&exceptionFlag=0&ordercode=${ordercode}">${list.exceptionNo }</a>
  				</c:if>
  				<c:if test="${ordercode==3}">
  					<a href="${app}/partner/inspect/inspectPlanResCount.do?method=inspectPlanResExceptionCountDetail&rootAreaId=${list.countryId}&exceptionFlag=0&ordercode=${ordercode}&deptId=${list.deptId}">${list.exceptionNo }</a>
  				</c:if>
  			</td>
  			<td>	
			<c:choose>
				<c:when test="${empty list.exceptionPercentage}">
  					0%
  				</c:when>
				<c:otherwise>
					${list.exceptionPercentage }
				</c:otherwise>
			</c:choose>
  			</td>
  			<td>
				<c:if test="${ordercode==1}">
  					<a href="${app}/partner/inspect/inspectPlanResCount.do?method=inspectPlanResExceptionCountDetail&rootAreaId=${list.cityId}&exceptionFlag=0&handleFlag=0&ordercode=${ordercode}">${list.unhandledNo }</a>
  				</c:if>
  				<c:if test="${ordercode==2}">
  					<a href="${app}/partner/inspect/inspectPlanResCount.do?method=inspectPlanResExceptionCountDetail&rootAreaId=${list.countryId}&exceptionFlag=0&handleFlag=0&ordercode=${ordercode}">${list.unhandledNo }</a>
  				</c:if>
  				<c:if test="${ordercode==3}">
  					<a href="${app}/partner/inspect/inspectPlanResCount.do?method=inspectPlanResExceptionCountDetail&rootAreaId=${list.countryId}&exceptionFlag=0&handleFlag=0&ordercode=${ordercode}&deptId=${list.deptId}">${list.unhandledNo }</a>
  				</c:if>
  			</td>
  			<td>
  				<c:if test="${ordercode==1}">
  					<a href="${app}/partner/inspect/inspectPlanResCount.do?method=inspectPlanResExceptionCountDetail&rootAreaId=${list.cityId}&exceptionFlag=0&handleFlag=1&ordercode=${ordercode}">${list.handleNo }</a>
  				</c:if>
  				<c:if test="${ordercode==2}">
  					<a href="${app}/partner/inspect/inspectPlanResCount.do?method=inspectPlanResExceptionCountDetail&rootAreaId=${list.countryId}&exceptionFlag=0&handleFlag=1&ordercode=${ordercode}">${list.handleNo }</a>
  				</c:if>
  				<c:if test="${ordercode==3}">
  					<a href="${app}/partner/inspect/inspectPlanResCount.do?method=inspectPlanResExceptionCountDetail&rootAreaId=${list.countryId}&exceptionFlag=0&handleFlag=1&ordercode=${ordercode}&deptId=${list.deptId}">${list.handleNo }</a>
  				</c:if>
  			</td>
  				<td>
  				<c:choose>
				<c:when test="${empty list.handlePercentage}">
  					0%
  				</c:when>
				<c:otherwise>
					${list.handlePercentage }
				</c:otherwise>
			</c:choose>
  			</td>
  		</tr>
  	</c:forEach>

  </table>
 <c:if test="${ordercode!=1}">
 	<input type="button" class="btn" value="返回"  onclick="javascript:history.back();" /> 
 </c:if>
<%@ include file="/common/footer_eoms.jsp"%>