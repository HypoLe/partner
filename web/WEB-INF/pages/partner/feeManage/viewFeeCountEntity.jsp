<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%>




<table class="table">
	<tr>
		<td  class="label">
			模板名称
		</td>
		<td>${feeCountPrcTmplName}
		</td>
	




		<td  class="label">
		单价：
		</td>
		<td>${feePrice}
		</td>
	
		
	</tr>	
	<tr>
		<td  class="label">
			应付款总计：
		</td>
		<td>${shdMnyAmt}
		</td>
	




		<td  class="label">
		实付款总计：
		</td>
		<td>${realMnyAmt}
		</td>
	
		
	</tr>		
	

</table>
<c:forEach items='${tableList}' var="table">
<table  class="formTable">
<tbody>
<tr>
			<td colspan="15">
				<div class="ui-widget-header">
				${table.fce.cntTypName}
				<c:if test="${not empty table.fce.resourceType}">
				 -
				 <eoms:id2nameDB id="${table.fce.resourceType}"  beanId="ItawSystemDictTypeDao" />
				 </c:if>
				</div>
			</td>
		</tr>
		<tr>
		<td>专业</td><td>计次类型</td>
		
		<c:if test="${not empty table.feeList[0].template.resourceType}"><td>资源类别
		</td></c:if>
		<c:if test="${not empty table.feeList[0].template.field1text}"><td>${table.feeList[0].template.field1text}
		</td></c:if>
		<c:if test="${not empty table.feeList[0].template.field2text}"><td>${table.feeList[0].template.field2text}
		</td></c:if>
		<c:if test="${not empty table.feeList[0].template.field3text}"><td>${table.feeList[0].template.field3text}
		</td></c:if>
		<c:if test="${not empty table.feeList[0].template.field4text}"><td>${table.feeList[0].template.field4text}
		</td></c:if>
		<c:if test="${not empty table.feeList[0].template.field5text}"><td>${table.feeList[0].template.field5text}
		</td></c:if>
		
		<td>单价系数</td>
		<td>数量</td>
		</tr>
<c:forEach items='${table.feeList}' var="fee">
<tr><td>${majorName}</td><td>${table.fce.cntTypName}</td>
<c:if test="${not empty fee.template.resourceType}"><td><eoms:id2nameDB id="${fee.template.resourceType}"  beanId="ItawSystemDictTypeDao" /></td></c:if>
<c:if test="${not empty fee.template.field1text}"><td>${fee.template.field1dict}</td></c:if>
<c:if test="${not empty fee.template.field2text}"><td>${fee.template.field2dict}</td></c:if>
<c:if test="${not empty fee.template.field3text}"><td>${fee.template.field3dict}</td></c:if>
<c:if test="${not empty fee.template.field4text}"><td>${fee.template.field4dict}</td></c:if>
<c:if test="${not empty fee.template.field5text}"><td>${fee.template.field5dict}</td></c:if>

<td>${fee.template.dtlprc}</td>
<td>${fee.entity.count}</td>
</tr>
</c:forEach>
</tbody>
</table>
<table>
<tr>
		<td class="label">
				应付款（元）：
			</td>
		<td>
		${table.fce.shdMnyAmt}
		</td>
		<td class="label">
				实付款（元）：
			</td>
		<td>
		${table.fce.realMnyAmt}
		</td></tr></table>
</c:forEach>
<br/>
<br/>
<br/>
<table  class="formTable">
		<tr><td class="label"">备注:</td>
		<td colspan="3">
				<pre>${remark}</pre>
				</td>
				</tr>
				</table>
			
				<br/>

<%@ include file="/common/footer_eoms.jsp"%>