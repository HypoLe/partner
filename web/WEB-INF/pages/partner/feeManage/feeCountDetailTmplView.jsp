<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%>
<style type="text/css">
  .classOrder{
    width:10%
  }
  .classMajor{
    width:20%
  }
  .classFeeCountType{
    width:20%
  }
  .classResType{
    width:20%
  }
  .classPrcFilter{
    width:20%
  }
  .classPrcQuotiety{
    width:20%
  }
</style>

<table class="table">
	<tr>
		<td  class="label">
			模板名称
		</td>
		<td>${feeCountPrcTmplName}
		</td>
		<td  class="label">
			专业
		</td>
		<td>${majorName}
		</td>
		 
	</tr>	
	<tr>
	  <td  class="label">
			单价
		</td>
		<td>${feePrice}
		</td>
		<td  class="label">
			区域
		</td>
		<td><eoms:id2nameDB id='${areaId}' beanId='tawSystemAreaDao'/>
		</td>
		</tr>	
	   <tr>
		<td  class="label">
			公司
		</td>
		<td><eoms:id2nameDB id='${compId}' beanId='partnerDeptDao'/>
		</td>
	</tr>	
		
	

</table>
<c:forEach items='${tableList}' var="table">
<table  class="formTable">
<tbody>
		<tr>
			<td colspan="15">
				<div class="ui-widget-header">
				${table.cntTypName} &nbsp;&nbsp; ${table.resourceTypeName}
				</div>
			</td>
		</tr>
		<tr>
		<td class="classMajor">专业</td>
		<td class="classFeeCountType">计次类型</td>
		
		<c:if test="${not empty table.resourceTypeName}">
		  <td class="classResType">资源类别
		  </td>
		</c:if>
		<c:if test="${not empty table.feeList[0].field1text}">
		 <td class="classPrcFilter">${table.feeList[0].field1text}
		 </td>
		</c:if>
		<c:if test="${not empty table.feeList[0].field2text}">
		  <td class="classPrcFilter">${table.feeList[0].field2text}
		  </td>
		</c:if>
		<c:if test="${not empty table.feeList[0].field3text}">
		  <td class="classPrcFilter">${table.feeList[0].field3text}
		  </td>
		</c:if>
		<c:if test="${not empty table.feeList[0].field4text}">
		  <td class="classPrcFilter">${table.feeList[0].field4text}
		  </td>
		</c:if>
		<c:if test="${not empty table.feeList[0].field5text}">
		  <td class="classPrcFilter">${table.feeList[0].field5text}
		  </td>
		</c:if>
		
		<td class="classPrcQuotiety">单价系数</td>
		</tr>
<c:forEach items='${table.feeList}' var="fee">
<tr>
  <input type="hidden" id="feeCountDetailTemplateId_${fee.owncnttypid}" name="feeCountDetailTemplateId_${fee.owncnttypid}" value="${fee.id}">
  <td class="classMajor">${majorName}</td>
  <td class="classFeeCountType">${table.cntTypName}</td>
<c:if test="${not empty table.resourceTypeName}"><td class="classResType">${table.resourceTypeName}</td></c:if>
<c:if test="${not empty fee.field1text}"><td class="classPrcFilter">${fee.field1dict}</td></c:if>
<c:if test="${not empty fee.field2text}"><td class="classPrcFilter">${fee.field2dict}</td></c:if>
<c:if test="${not empty fee.field3text}"><td class="classPrcFilter">${fee.field3dict}</td></c:if>
<c:if test="${not empty fee.field4text}"><td class="classPrcFilter">${fee.field4dict}</td></c:if>
<c:if test="${not empty fee.field5text}"><td class="classPrcFilter">${fee.field5dict}</td></c:if>
<td class="classPrcQuotiety">${fee.dtlprc}</td>
</tr>
</c:forEach>
</tbody>
</table>
</c:forEach>
<br/>
<table class="table">
    <tr><td class="label">
		备注:</td>
		    <td>
		     <pre>${remark}</pre>
			</td>
       </tr>
</table>
<br/>

<%@ include file="/common/footer_eoms.jsp"%>