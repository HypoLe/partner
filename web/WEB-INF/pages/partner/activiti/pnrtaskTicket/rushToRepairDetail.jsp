<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

  	<logic:present name="pnrTaskTicket" scope="request">
	 <!-- 工单基本信息start --> 
	 
<table class="formTable">
<tr>
	 	  <td class="label">工单主题</td>
		  <td class="content" colspan="3">
			${pnrTaskTicket.theme}
		  </td>
</tr>

<tr>
		   <td class="label">工单子类型</td>		
			
		   <td class="content">
		   <eoms:id2nameDB id="${pnrTaskTicket.subType}" beanId="ItawSystemDictTypeDao"/>
		   </td>  
		  
		   <td class="label">站点</td>
		   <td class="content">
		    ${pnrTaskTicket.site}
		   </td>	  
</tr>
<tr>
		   <td class="label">计划开始时间</td>
		   <td class="content">
		   ${eoms:date2String(pnrTaskTicket.startTime)}
		   </td>
		   <td class="label">计划结束时间</td>
		   <td class="content">
		   ${eoms:date2String(pnrTaskTicket.endTime)}
		   </td>		  
</tr>
<tr>
 			
			<td class="label">涉及专业</td>
			<td class="content" colspan="3">
				${specialty}
			
			</td>
</tr>
<tr>
 			<td class="label">
				工单内容
			</td>
			<td class="content" colspan="3">
			${pnrTaskTicket.content}
			</td>
</tr>	
	
</table>
</logic:present>
<%@ include file="/common/footer_eoms.jsp"%>