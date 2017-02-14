<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@page import="com.boco.eoms.base.util.*"%>
<%@page import="com.boco.eoms.assEva.mgr.*"%>
<%@page import="com.boco.eoms.assEva.util.*"%>
<%@page import="com.boco.eoms.assEva.model.*"%>
<style type="text/css">
body {
	background-image: none;
}
</style>
<div style="height: 400px">
	<a onclick="$('kpiContent').toggleClass('hide')"
		style="cursor: pointer">隐藏/显示编辑指标表单</a>
	<div id="kpiContent">
		<html:form action="/assEvaKpis.do?method=saveKpi" styleId="assEvaKpiForm">
			<table align="center">
				<tr>
					<td>
						指标名称
					</td>
					<td colspan="3">
						<input type="text" id="kpiName" name="kpiName" class="text medium"
							value="${assEvaKpiForm.kpiName}" />
						<font style="color: red;">${fallure }</font>
					</td>
				</tr>
				<tr>
					<td>
						权重
					</td>
					<td colspan="3">
						<input type="text" id="weight" name="weight" class="text medium"
							value="${assEvaKpiForm.weight}" />
						<font color="red">*可分配权重范围：${requestScope.minWt}~${requestScope.maxWt2String}分</font>
					</td>
				</tr>
				<tr>
					<td>
						算法描述
					</td>
					<td colspan="3">
						<input type="textarea" id="algorithm" name="algorithm"
							class="textarea" style="width: 88%"
							value="${assEvaKpiForm.algorithm}" />
					</td>
				</tr>
				<tr>
					<td>
						备注
					</td>
					<td colspan="3">
						<input type="textarea" id="remark" name="remark" class="textarea"
							style="width: 88%" value="${assEvaKpiForm.remark}" />
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<input type="button" class="btn"
							value="<fmt:message key="button.save"/>"
							onclick="validateForm();" />
					</td>
					<c:if test="${tranEvaKpiForm.id!=''}">
						<td><a href="${app}/partner/tranEva/tranKpiConfigs.do?method=tree&parentNodeId=${requestScope.parentNodeId}&kpiId=${tranEvaKpiForm.id}" target="_blank">打分配置</a></td>
					</c:if>
				</tr>
			</table>
			<input type="hidden" id="id" name="id" value="${assEvaKpiForm.id}" />
			<input type="hidden" id="parentNodeId" name="parentNodeId"
				value="${requestScope.parentNodeId}" />
		</html:form>
	</div>
	<table class="listTable" id="list-table">
		<caption>
			<div class="header center">
				<%
					String tempname = "";
					String nodeId = StaticMethod.null2String(request.getParameter("node"));
					String nodeType = StaticMethod.null2String(request
							.getParameter("nodeType"));
					IAssEvaTreeMgr assEvaTreeMgr = (IAssEvaTreeMgr) ApplicationContextHolder
							.getInstance().getBean("IassEvaTreeMgr");
					IAssEvaKpiMgr kpiMgr = (IAssEvaKpiMgr) ApplicationContextHolder
							.getInstance().getBean("IassEvaKpiMgr");
					String parentNodeId = (String) request.getAttribute("parentNodeId");
					
					//if (AssEvaConstants.NODETYPE_TEMPLATETYPE.equals(nodeType)) {
						tempname = assEvaTreeMgr.id2Name(parentNodeId);
					//}else{
					//	AssEvaTree assEvaTree = assEvaTreeMgr.getTreeNodeByNodeId(parentNodeId);
					//	AssEvaKpi kpi = new AssEvaKpi();
					//	kpi = kpiMgr.getKpi(assEvaTree.getObjectId());
					//	tempname = kpi.getKpiName();
					//}
				%>

				<%=tempname%>的下级指标列表
			</div>
		</caption>
		<thead>
			<tr>
				<td>
					编号
				</td>
				<td>
					指标名称
				</td>
				<td>
					权重
				</td>
				<td>
					创建人
				</td>
				<td>
					创建时间
				</td>
				<td class="imageColumn">
					打分配置
				</td>				
				<td class="imageColumn">
					编辑
				</td>
				<td class="imageColumn">
					删除
				</td>
			</tr>
		</thead>
		<tbody>
			<logic:iterate id="kpiIt" name="kpiIt" indexId="index">
				<tr>
					<td>
						${index + 1}
					</td>
					<td>
						<bean:write name="kpiIt" property="kpiName" />
					</td>
					<td>
						<bean:write name="kpiIt" property="weight" />
					</td>
					<td>
						<bean:write name="kpiIt" property="creator" />
					</td>
					<td>
						<bean:write name="kpiIt" property="createTime" />
					</td>
					<td>
						<a href="${app}/partner/tranEva/tranKpiConfigs.do?method=tree&parentNodeId=${requestScope.parentNodeId}&kpiId=${kpiIt.id}" target="_blank">打分配置</a>
					</td>					
					<td>
						<a
							href="${app}/assEva/assEvaKpis.do?method=editKpi&parentNodeId=${requestScope.parentNodeId}&kpiId=${kpiIt.id}"
							title="编辑这个项目"> <img src="${app}/images/icons/edit.gif" /> </a>
					</td>
					<td>
						<a
							href="javascript:if(confirm('确定要从模板中移除该指标?')){var url='${app}/assEva/assEvaKpis.do?method=delKpiFromTree&kpiId=${kpiIt.id}&parentNodeId=${requestScope.parentNodeId}';location.href=url}"
							title="删除这个项目"> <img
								src="${app}/images/icons/list-delete.gif" /> </a>
					</td>
				</tr>
			</logic:iterate>
		</tbody>
	</table>
</div>

<script type="text/javascript" />
	try{
		if (null != parent.parent.AppFrameTree) {
			parent.parent.AppFrameTree.reloadNode();
		}
		if (null != parent.AppFrameTree) {
			parent.AppFrameTree.reloadNode();
		}
	}catch(e){}
	function validateForm() {
		var kpiName = document.forms[0].kpiName.value;
		var weight = document.forms[0].weight.value;
		if (kpiName == '' || kpiName.length <= 0) {
			alert('请输入指标名称');
		} else if (weight == '' || weight.length <= 0) {
			alert('请输入权重');
		} else if (isNaN(weight)) {
			alert('权重值请输入数字');
		} else if (weight < ${requestScope.minWt}) {
			alert('您输入的权重不在可分配范围内');
		} else if (-1!=${requestScope.maxWt}&&weight > ${requestScope.maxWt}) {
			alert('您输入的权重不在可分配范围内');
		} else {
			document.forms[0].submit();
		}
	}
	document. getElementById('kpiName').focus() ;
</script>
<%@ include file="/common/footer_eoms.jsp"%>
