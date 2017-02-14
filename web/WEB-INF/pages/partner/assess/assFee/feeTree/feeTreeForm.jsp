<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'feeTreeForm'});
});
</script>

<html:form action="/feeTrees.do?method=save" styleId="feeTreeForm" method="post"> 

<table class="formTable">
	<caption>
		<div class="header center">设备信息</div>
	</caption>
	
	<tr>
		<td  class="label">
			名称
		</td>
		<td class="content" colspan="3">
			<html:text property="nodeName" styleId="nodeName"
						styleClass="text medium" style="width:95%;" 
						alt="allowBlank:false,vtext:''" value="${feeTreeForm.nodeName}" />
		</td>
	</tr>
	<c:if test="${feeTreeForm.parentNodeId >2 }">
		<tr>
			<td  class="label">
				单价
			</td>
			<td class="content">
				<html:text property="price" styleId="price"
							styleClass="text medium" style="width:95%;" 
							alt="allowBlank:false,vtext:''" value="${feeTreeForm.price}" />
			</td>
			<td  class="label">
				单位
			</td>
			<td class="content">
				<html:text property="unit" styleId="unit"
							styleClass="text medium" style="width:95%;" 
							alt="allowBlank:false,vtext:''" value="${feeTreeForm.unit}" />
			</td>		
		</tr>
	</c:if>
	<tr>
		<td  class="label">
			备注
		</td>
		<td class="content" colspan="3">
			<html:text property="nodeRemark" styleId="nodeRemark"
						styleClass="text medium" style="width:100%;" 
						alt="allowBlank:false,vtext:''" value="${feeTreeForm.nodeRemark}" />
		</td>
	</tr>


</table>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty feeTreeForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('你确认删除?')){
						var url='${app}/partner/assess/feeTrees.do?method=remove&nodeId=${feeTreeForm.nodeId}';
						location.href=url}"
						/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="nodeType" value="${feeTreeForm.nodeType}" />
<html:hidden property="id" value="${feeTreeForm.id}" />
<html:hidden property="nodeId" value="${feeTreeForm.nodeId}" />
<html:hidden property="parentNodeId" value="${feeTreeForm.parentNodeId}" />
<html:hidden property="leaf" value="${feeTreeForm.leaf}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>