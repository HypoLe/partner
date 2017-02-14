<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	response.setHeader("cache-control", "public");
%>

<link rel="stylesheet" type="text/css"
	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<style type="text/css">
.text-center {
	text-align: center;
}
</style>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript"
	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
function query(){
	if(document.getElementById('year').value == ''){
		alert('请选择年份');
		return false;
	}
	if(document.getElementById('month').value == ''){
		alert('请选择季度');
		return false;
	}
	
}


</script>
<form action="pnrStatistics.do?method=showMoneySelect"
	id="gridForm" method="post">
	<table class="formTable">
		<tr>
			<td class="label">
				年份*
			</td>
			<td class="content">
				<eoms:dict key="dict-partner-task" selectId="year"
					dictId="yearflag" beanId="selectXML" cls="select" defaultId="${year}"/>
			</td>
			<td class="label">
				季度*
			</td>
			<td class="content">
			<eoms:dict key="dict-partner-task" selectId="month"
					dictId="monthflag" beanId="selectXML" cls="select" defaultId="${month}"/>
			</td>
		</tr>
		<tr></tr>
	</table>

	<table>
		<tr>
			<td>
				<input type="submit" class="btn" value="统计" onclick="return query()" />
				&nbsp;&nbsp;
			</td>
		</tr>
	</table>
</form>
<br />

<table class="table list" cellpadding="0" cellspacing="0">
  	<thead>
  		<tr>
  			
  			<th>地市</th>
  			<th style="text-align:center;" >工单类别</th>  			  		
  			<th style="text-align:center;" >预算金额</th>  		  			  		
  			<th style="text-align:center;" >市公司提交数量</th>  		
  			<th style="text-align:center;" >市公司提交金额</th>  		
  			<th style="text-align:center;" >省公司审批通过数量</th>  		
  			<th style="text-align:center;" >省公司审批通过金额</th>  		
  			<th style="text-align:center;" >预算差额</th>  		
  			
  		</tr> 
  	</thead>
  		

   <c:forEach var="list" items="${taskList}">
  <tr>
	  <td rowspan=3>
		<a href="${app}/activiti/statistics/pnrStatistics.do?method=showMoneySelectCountry&city=${list.cityId}&month=${month}&year=${year}">
		<eoms:id2nameDB id='${list.cityId}' beanId='tawSystemAreaDao'/>
		</a>
	  </td>	  
	  
 	 
 	 <td style="text-align:center;">预检预修-应急工单</td>	
 	 <td style="text-align:center;" rowspan=3><fmt:formatNumber value="${list.projectMoney}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" /></td>
 	 <td style="text-align:center;"><fmt:formatNumber value="${list.cityCountCrash}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" /></td>
 	 <td style="text-align:center;"><fmt:formatNumber value="${list.cityMoneyCrash}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" /></td>
 	 <td style="text-align:center;"><fmt:formatNumber value="${list.provinceCountCrash}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" /></td>
 	 <td style="text-align:center;"><fmt:formatNumber value="${list.provinceMoneyCrash}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" /></td>
 	 <td style="text-align:center;"><fmt:formatNumber value="${list.balanceCrash}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" /></td>
 	 
	  <tr>
	   <td style="text-align:center;">预检预修-常规工单</td>	
	    <td style="text-align:center;"><fmt:formatNumber value="${list.cityCount}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" /></td>
	 	 <td style="text-align:center;"><fmt:formatNumber value="${list.cityMoney}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" /></td>
	 	 <td style="text-align:center;"><fmt:formatNumber value="${list.provinceCount}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" /></td>
	 	 <td style="text-align:center;"><fmt:formatNumber value="${list.provinceMoney}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" /></td>
	 	 <td style="text-align:center;" rowspan=2><fmt:formatNumber value="${list.balance}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" /></td>  
	  </tr>
	  
	   <tr>
	   <td style="text-align:center;">收支比小于50%的大修改造工单</td>	
	    <td style="text-align:center;"><fmt:formatNumber value="${list.cityCountRepair}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" /></td>
	 	 <td style="text-align:center;"><fmt:formatNumber value="${list.cityMoneyRepair}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" /></td>
	 	 <td style="text-align:center;"><fmt:formatNumber value="${list.provinceCountRepair}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" /></td>
	 	 <td style="text-align:center;"><fmt:formatNumber value="${list.provinceMoneyRepair}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" /></td>  
	  </tr>
  	</c:forEach>

  </table>

<%@ include file="/common/footer_eoms.jsp"%>
