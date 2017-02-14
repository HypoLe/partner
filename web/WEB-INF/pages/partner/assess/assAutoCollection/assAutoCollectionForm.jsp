<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'assAutoCollectionForm'});
});
</script>

<html:form action="/assAutoCollections.do?method=save" styleId="assAutoCollectionForm" method="post"> 

<table class="formTable">
	<caption>
		<div class="header center">采集树配置</div>
	</caption>

	<tr>
		<td  class="label">
			节点名称
		</td>
		<td class="content">
			<html:text property="nodeName" styleId="nodeName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${assAutoCollectionForm.nodeName}" />
		</td>
	</tr>

</table>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="保存" />
			<c:if test="${not empty assAutoCollectionForm.id}">
				<input type="button" class="btn" value="删除" 
					onclick="javascript:if(confirm('你确定要删除么?')){
						var url='${app}/assAutoCollection/assAutoCollections.do?method=remove&nodeId=${assAutoCollectionForm.nodeId}';
						location.href=url}"
						/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="nodeType" value="${assAutoCollectionForm.nodeType}" />
<html:hidden property="id" value="${assAutoCollectionForm.id}" />
<html:hidden property="nodeId" value="${assAutoCollectionForm.nodeId}" />
<html:hidden property="parentNodeId" value="${assAutoCollectionForm.parentNodeId}" />
<html:hidden property="leaf" value="${assAutoCollectionForm.leaf}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>