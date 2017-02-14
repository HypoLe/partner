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
  <c:if test="${mark ne 1 && code ne 'city'}">
  <input type="button" onclick="javascript:history.go(-1);" value="返回"/>
  </c:if>
  <table class="table list" cellpadding="0" cellspacing="0">
  	<thead>
  		<tr>
  			
  			<th rowspan=2 >
  			<c:choose >
  				<c:when test="${code eq 'person'}">
  					处理人
  				</c:when>
  				<c:when test="${code eq 'country'}">
  				     区县
  				</c:when>
  				
  				<c:otherwise>
  					地市
  				</c:otherwise>
  			</c:choose>
  			</th>
  			  		
  			<th colspan=2 style="text-align:center;" >故障</th>  		
  				
  			<th colspan=2 style="text-align:center;">发电</th>
  			
  			<th colspan=2 style="text-align:center;">业务开通</th>
  			
  			<th colspan=2 style="text-align:center;">网络割接</th>
  		
  			<th colspan=2 style="text-align:center;">业务协调</th>
  			
  			<th colspan=2 style="text-align:center;">巡检</th>
  			
  			<th colspan=2 style="text-align:center;">工程验收</th>

            <th colspan=2 style="text-align:center;">缴费</th>

            <th colspan=2 style="text-align:center;">其它</th>
  			
  		</tr>  		
	  	<tr>	
	  	<th style="text-align:center;" >工单总数</th>  		
	  			<th style="text-align:center;" >超时率</th>  	
	  	<th style="text-align:center;" >工单总数</th>  		
	  			<th style="text-align:center;" >超时率</th>  	
	  	<th style="text-align:center;" >工单总数</th>  		
	  			<th style="text-align:center;" >超时率</th>  	
	  	<th style="text-align:center;" >工单总数</th>  		
	  			<th style="text-align:center;" >超时率</th>  	
	  	<th style="text-align:center;" >工单总数</th>  		
	  			<th style="text-align:center;" >超时率</th>  	
	  	<th style="text-align:center;" >工单总数</th>  		
	  			<th style="text-align:center;" >超时率</th>  	
	  	<th style="text-align:center;" >工单总数</th>  		
	  			<th style="text-align:center;" >超时率</th>
        <th style="text-align:center;" >工单总数</th>
        <th style="text-align:center;" >超时率</th>
        <th style="text-align:center;" >工单总数</th>
        <th style="text-align:center;" >超时率</th>
        </tr>
  	</thead>
  		

   <c:forEach var="list" items="${taskList}">
  <tr>
  <td>
 
  			<c:choose >
  				<c:when test="${code eq 'person'}">
  				<eoms:id2nameDB id="${list.city}" beanId="tawSystemUserDao"/>
  				</c:when>  				
  				<c:otherwise>
  					 <a href="${app}/activiti/statistics/pnrStatistics.do?method=workOrderStatistics2Country&city=${list.city}&code=${code}">
  						${list.cityName}
 				     </a>
  				</c:otherwise>
  			</c:choose>
  
  </td>
  <!-- 故障 -->
  <td style="text-align:center;">
 <a href="${app}/activiti/statistics/pnrStatistics.do?method=statisticsPartnerIndexDrill&flag=1&level=${code}&city=${list.city}&country=${country}&subType=101220101">
  ${list.faultSumNum}
 </a> 
  </td>
  <td style="text-align:center;">${list.faultOvertimeRate}</td>
 <!-- 发电 -->
  <td style="text-align:center;">
  <a href="${app}/activiti/statistics/pnrStatistics.do?method=statisticsPartnerIndexDrill&flag=1&level=${code}&city=${list.city}&country=${country}&subType=101220102">
  ${list.generateElectricitySumNum}
  </a>
  </td>
  <td style="text-align:center;">${list.generateElectricityOvertimeRate}</td>
 <!-- 业务开通 -->
  <td style="text-align:center;">
  <a href="${app}/activiti/statistics/pnrStatistics.do?method=statisticsPartnerIndexDrill&flag=2&level=${code}&city=${list.city}&country=${country}&subType=101110101">
  ${list.businessOpenedSumNum}
  </a>
  </td>
  <td style="text-align:center;">${list.businessOpenedOvertimeRate}</td>
 <!-- 网络割接 -->
  <td style="text-align:center;">
  <a href="${app}/activiti/statistics/pnrStatistics.do?method=statisticsPartnerIndexDrill&flag=2&level=${code}&city=${list.city}&country=${country}&subType=101110102">
  ${list.networkCutoverSumNum}
  </a>
  </td>
  <td style="text-align:center;">${list.networkCutoverOvertimeRate}</td>
 <!-- 业务协调 -->
  <td style="text-align:center;">
  <a href="${app}/activiti/statistics/pnrStatistics.do?method=statisticsPartnerIndexDrill&flag=2&level=${code}&city=${list.city}&country=${country}&subType=101110103">
  ${list.operationalCoordinationSumNum}
  </a>
  </td>
  <td style="text-align:center;">${list.operationalCoordinationOvertimeRate}</td>
 <!-- 巡检-->
  <td style="text-align:center;">
  <a href="${app}/activiti/statistics/pnrStatistics.do?method=statisticsPartnerIndexDrill&flag=2&level=${code}&city=${list.city}&country=${country}&subType=101110105">
  ${list.inspectionSumNum}
  </a>
  </td>
  <td style="text-align:center;">${list.inspectionOvertimeRate}</td>
  <!--工程验收 -->
  <td style="text-align:center;">
  <a href="${app}/activiti/statistics/pnrStatistics.do?method=statisticsPartnerIndexDrill&flag=2&level=${code}&city=${list.city}&country=${country}&subType=101110106">
  ${list.projectAcceptanceSumNum}
  </a>
  </td>
  <td style="text-align:center;">${list.projectAcceptanceOvertimeRate}</td>
  <!--缴费 -->
  <td style="text-align:center;">
      <a href="${app}/activiti/statistics/pnrStatistics.do?method=statisticsPartnerIndexDrill&flag=2&level=${code}&city=${list.city}&country=${country}&subType=101110104">
              ${list.paymentSumNum}
      </a>
  </td>
  <td style="text-align:center;">${list.paymentOvertimeRate}</td>
  <!--其它 -->
  <td style="text-align:center;">
      <a href="${app}/activiti/statistics/pnrStatistics.do?method=statisticsPartnerIndexDrill&flag=2&level=${code}&city=${list.city}&country=${country}&subType=101110107">
              ${list.otherSumNum}
      </a>
  </td>
  <td style="text-align:center;">${list.otherOvertimeRate}</td>
  </tr>
  	</c:forEach>

  </table>
<%@ include file="/common/footer_eoms.jsp"%>