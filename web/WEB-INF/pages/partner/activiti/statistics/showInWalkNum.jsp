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


<table class="table list" cellpadding="0" cellspacing="0">
  	<thead>
  		<tr>
  			
  			<th rowspan=2>地市</th>  			  		
  			<th style="text-align:center;" rowspan=2>工单类别</th>  		
  			<th style="text-align:center;" colspan=2>工单发起</th>  		
  			<th style="text-align:center;" colspan=2>工单发起审核</th>  	
  			<th style="text-align:center;" colspan=2>市线路主管审核</th>  				
  			<th style="text-align:center;" colspan=2>市线路主任审核</th>  		
  			<th style="text-align:center;" colspan=2>市运维主管审核</th>  
  			<th style="text-align:center;" colspan=2>市运维主任审核</th>  
			<th style="text-align:center;" colspan=2>市公司副总审核</th>  		
  			<th style="text-align:center;" colspan=2>省线路主管审核</th>  		
            <th style="text-align:center;" colspan=2>省线路总经理审核</th>  
			<th style="text-align:center;" colspan=2>省运维主管审核</th>  		
            <th style="text-align:center;" colspan=2>省运维总经理审批</th> 
  			
 </tr> 
 <tr>
  			
  		
			<th  >工单数量</th>  		
  			<th  >工单金额</th>  	
			<th  >工单数量</th>  		
  			<th  >工单金额</th>  
            <th  >工单数量</th>  		
  			<th  >工单金额</th>  	
			<th  >工单数量</th>  		
  			<th  >工单金额</th>  
            <th  >工单数量</th>  		
  			<th  >工单金额</th>  	
			<th  >工单数量</th>  		
  			<th  >工单金额</th>  
            <th  >工单数量</th>  		
  			<th  >工单金额</th>  	
			<th  >工单数量</th>  		
  			<th  >工单金额</th> 
			<th  >工单数量</th>  		
  			<th  >工单金额</th>
			<th  >工单数量</th>  		
  			<th  >工单金额</th>
			<th  >工单数量</th>  		
  			<th  >工单金额</th>
  </tr> 
  	</thead>
  		

   <c:forEach var="list" items="${taskList}">
  
 
 <tr>
	  <td rowspan=2>
			<eoms:id2nameDB id='${list.cityId}' beanId='tawSystemAreaDao'/>
	
	  </td>	  
	  
 	 <td style="text-align:center;">应急工单</td>	
 	 
 	 <td style="text-align:center;"><fmt:formatNumber value="${list.neednum_crash}" pattern="##.##" maxFractionDigits="0"  /></td>
 	 <td style="text-align:center;"><fmt:formatNumber value="${list.needmoney_crash}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" /></td>
 	 <td style="text-align:center;"><fmt:formatNumber value="${list.workchecknum_crash}" pattern="##.##" maxFractionDigits="0"  /></td>
 	 <td style="text-align:center;"><fmt:formatNumber value="${list.workcheckmoney_crash}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" /></td>
	  <td style="text-align:center;"><fmt:formatNumber value="${list.city_l_examinenum_crash}" pattern="##.##" maxFractionDigits="0"  /></td>
	  <td style="text-align:center;"><fmt:formatNumber value="${list.city_l_examinemoney_crash}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" /></td>
 	 <td style="text-align:center;"><fmt:formatNumber value="${list.city_l_auditnum_crash}" pattern="##.##" maxFractionDigits="0"  /></td>
 	 <td style="text-align:center;"><fmt:formatNumber value="${list.city_l_auditmoney_crash}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" /></td>
 	 <td style="text-align:center;"><fmt:formatNumber value="${list.city_m_examinenum_crash}" pattern="##.##" maxFractionDigits="0"  /></td>
 	 <td style="text-align:center;"><fmt:formatNumber value="${list.city_m_examinemoney_crash}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" /></td>
	 <td style="text-align:center;"><fmt:formatNumber value="${list.city_m_auditnum_crash}" pattern="##.##" maxFractionDigits="0"  /></td>
 	  <td style="text-align:center;"><fmt:formatNumber value="${list.city_m_auditmoney_crash}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" /></td>
 	 <td style="text-align:center;"><fmt:formatNumber value="${list.city_v_auditnum_crash}" pattern="##.##" maxFractionDigits="0"  /></td>
 	 <td style="text-align:center;"><fmt:formatNumber value="${list.city_v_auditmoney_crash}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" /></td>
	  <td style="text-align:center;"><fmt:formatNumber value="${list.pro_l_examinenum_crash}" pattern="##.##" maxFractionDigits="0"  /></td>
 	 <td style="text-align:center;"><fmt:formatNumber value="${list.pro_l_examinemoney_crash}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" /></td>
 	 <td style="text-align:center;"><fmt:formatNumber value="${list.pro_l_auditnum_crash}" pattern="##.##" maxFractionDigits="0"  /></td>
 	 <td style="text-align:center;"><fmt:formatNumber value="${list.pro_l_auditmoney_crash}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" /></td>
 	 <td style="text-align:center;"><fmt:formatNumber value="${list.pro_m_examinenum_crash}" pattern="##.##" maxFractionDigits="0"  /></td>
	 <td style="text-align:center;"><fmt:formatNumber value="${list.pro_m_examinemoney_crash}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" /></td>
	 <td style="text-align:center;"><fmt:formatNumber value="${list.pro_m_auditnum_crash}" pattern="##.##" maxFractionDigits="0"  /></td>
	 <td style="text-align:center;"><fmt:formatNumber value="${list.pro_m_auditmoney_crash}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" /></td>
 	

 	 
  <tr>
   <td style="text-align:center;">常规工单</td>	
  <td style="text-align:center;"><fmt:formatNumber value="${list.neednum}" pattern="##.##" maxFractionDigits="0"  /></td>
 	 <td style="text-align:center;"><fmt:formatNumber value="${list.needmoney}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" /></td>
 	 <td style="text-align:center;"><fmt:formatNumber value="${list.workchecknum}" pattern="##.##" maxFractionDigits="0"  /></td>
 	 <td style="text-align:center;"><fmt:formatNumber value="${list.workcheckmoney}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" /></td>
	  <td style="text-align:center;"><fmt:formatNumber value="${list.city_l_examinenum}" pattern="##.##" maxFractionDigits="0"  /></td>
	  <td style="text-align:center;"><fmt:formatNumber value="${list.city_l_examinemoney}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" /></td>
 	 <td style="text-align:center;"><fmt:formatNumber value="${list.city_l_auditnum}" pattern="##.##" maxFractionDigits="0"  /></td>
 	 <td style="text-align:center;"><fmt:formatNumber value="${list.city_l_auditmoney}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" /></td>
 	 <td style="text-align:center;"><fmt:formatNumber value="${list.city_m_examinenum}" pattern="##.##" maxFractionDigits="0"  /></td>
 	 <td style="text-align:center;"><fmt:formatNumber value="${list.city_m_examinemoney}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" /></td>
	 <td style="text-align:center;"><fmt:formatNumber value="${list.city_m_auditnum}" pattern="##.##" maxFractionDigits="0"  /></td>
 	  <td style="text-align:center;"><fmt:formatNumber value="${list.city_m_auditmoney}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" /></td>
 	 <td style="text-align:center;"><fmt:formatNumber value="${list.city_v_auditnum}" pattern="##.##" maxFractionDigits="0"  /></td>
 	 <td style="text-align:center;"><fmt:formatNumber value="${list.city_v_auditmoney}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" /></td>
	  <td style="text-align:center;"><fmt:formatNumber value="${list.pro_l_examinenum}" pattern="##.##" maxFractionDigits="0"  /></td>
 	 <td style="text-align:center;"><fmt:formatNumber value="${list.pro_l_examinemoney}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" /></td>
 	 <td style="text-align:center;"><fmt:formatNumber value="${list.pro_l_auditnum}" pattern="##.##" maxFractionDigits="0"  /></td>
 	 <td style="text-align:center;"><fmt:formatNumber value="${list.pro_l_auditmoney}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" /></td>
 	 <td style="text-align:center;"><fmt:formatNumber value="${list.pro_m_examinenum}" pattern="##.##" maxFractionDigits="0"  /></td>
	 <td style="text-align:center;"><fmt:formatNumber value="${list.pro_m_examinemoney}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" /></td>
	 <td style="text-align:center;"><fmt:formatNumber value="${list.pro_m_auditnum}" pattern="##.##" maxFractionDigits="0"  /></td>
	 <td style="text-align:center;"><fmt:formatNumber value="${list.pro_m_auditmoney}" pattern="##.##" maxFractionDigits="2" minFractionDigits="0" /></td>
   
  </tr>
  	</c:forEach>

  </table>

<%@ include file="/common/footer_eoms.jsp"%>
