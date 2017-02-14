<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@page import="com.boco.eoms.base.util.*"%>
<%@page import="com.boco.eoms.eva.mgr.*"%>
<%@page import="com.boco.eoms.eva.util.*"%>
<%@page import="com.boco.eoms.eva.model.*"%>
<style type="text/css">
body {
	background-image: none;
}
</style>
<div style="height: 400px">
	<a onclick="$('kpiContent').toggleClass('hide')"
		style="cursor: pointer">隐藏/显示编辑指标表单</a>
	<div id="kpiContent">
		<html:form action="/evaKpis.do?method=saveKpi" styleId="evaKpiForm">
			<table align="center">
				<tr>
					<td>
						指标名称
					</td>
					<td colspan="3">
						<input type="text" id="kpiName" name="kpiName" class="text medium"
							value="${evaKpiForm.kpiName}" />
						<font style="color: red;">${fallure }</font>
					</td>
				</tr>
				<tr>
					<td>
						权重
					</td>
					<td colspan="3">
						<input type="text" id="weight" name="weight" class="text medium"
							value="${evaKpiForm.weight}" />
						<font color="red">*可分配权重范围：${requestScope.minWt}~${requestScope.maxWt2String}分</font>
					</td>
				</tr>
	<tr>
		<td>
			考核周期
		</td>
		<td colspan="3">
					<eoms:dict key="dict-eva" dictId="cycle" defaultId="${evaKpiForm.cycle}"
						 selectId="cycle" beanId="selectXML" alt="allowBlank:false,vtext:'请选择指标考核周期'" />
		</td>
	</tr>
<tr>
		<td>
			开始时间
		</td>
		<td colspan="3">
			<input type="text" name="evaStartTime" size="20" onclick="popUpCalendar(this, this,null,null,null,true,-1);" value="${evaKpiForm.evaStartTime}" readonly="true" class="text" alt="allowBlank:false,vtext:'请选择指标考核开始时间...'" />
		</td>
		</tr>
		<tr>
		<td>
			结束时间
		</td>
		<td colspan="3">
			<input type="text" name="evaEndTime" size="20" onclick="popUpCalendar(this, this,null,null,null,true,-1);" value="${evaKpiForm.evaEndTime}" readonly="true" class="text" alt="allowBlank:false,vtext:'请选择指标考核计划截止时间...'" />
		</td>
	</tr>
		<tr>
					<td>
						考核来源
					</td>
					<td colspan="3">
						<eoms:dict key="dict-eva" dictId="evaSource" defaultId="${evaKpiForm.evaSource}"
						 selectId="evaSource" beanId="selectXML" alt="allowBlank:false,vtext:'请选择指标考核来源'" />
					</td>
		</tr>
				<tr>
					<td>
						算法描述
					</td>
					<td colspan="3">
						<input type="textarea" id="algorithm" name="algorithm"
							class="textarea" style="width: 88%"
							value="${evaKpiForm.algorithm}" />
					</td>
				</tr>
				<tr>
					<td>
						备注
					</td>
					<td colspan="3">
						<input type="textarea" id="remark" name="remark" class="textarea"
							style="width: 88%" value="${evaKpiForm.remark}" />
					</td>
				</tr>
				<tr>
					<td colspan="4">
					<c:if test="${activated !=1}">
						<input type="button" class="btn"
							value="<fmt:message key="button.save"/>"
							onclick="validateForm();" />
						</c:if>
					</td>
				</tr>
			</table>
			<input type="hidden" id="id" name="id" value="${evaKpiForm.id}" />
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
					IEvaTreeMgr evaTreeMgr = (IEvaTreeMgr) ApplicationContextHolder
							.getInstance().getBean("IevaTreeMgr");
					IEvaKpiMgr kpiMgr = (IEvaKpiMgr) ApplicationContextHolder
							.getInstance().getBean("IevaKpiMgr");
					String parentNodeId = (String) request.getAttribute("parentNodeId");
					
					//if (EvaConstants.NODETYPE_TEMPLATETYPE.equals(nodeType)) {
						tempname = evaTreeMgr.id2Name(parentNodeId);
					//}else{
					//	EvaTree evaTree = evaTreeMgr.getTreeNodeByNodeId(parentNodeId);
					//	EvaKpi kpi = new EvaKpi();
					//	kpi = kpiMgr.getKpi(evaTree.getObjectId());
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
					周期
				</td>
				<td>
					开始时间
				</td>
				<td>
					结束时间
				</td>
				<c:if test="${activated !=1}">
				<td class="imageColumn">
					编辑
				</td>
				<td class="imageColumn">
					删除
				</td>
				</c:if>
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
						<eoms:dict key="dict-eva" dictId="cycle" itemId="${kpiIt.cycle}" beanId="id2nameXML" />
					</td>
					<td>
						<bean:write name="kpiIt" property="evaStartTime" />
					</td>
					<td>
						<bean:write name="kpiIt" property="evaEndTime" />
					</td>
					<c:if test="${activated !=1}">
					<td>
						<a
							href="${app}/eva/evaKpis.do?method=editKpi&parentNodeId=${requestScope.parentNodeId}&kpiId=${kpiIt.id}"
							title="编辑这个项目"> <img src="${app}/images/icons/edit.gif" /> </a>
					</td>
					<td>
						<a
							href="javascript:if(confirm('确定要从模板中移除该指标?')){var url='${app}/eva/evaKpis.do?method=delKpiFromTree&kpiId=${kpiIt.id}&parentNodeId=${requestScope.parentNodeId}';location.href=url}"
							title="删除这个项目"> <img
								src="${app}/images/icons/list-delete.gif" /> </a>
					</td>
					</c:if>
				</tr>
			</logic:iterate>
		</tbody>
	</table>
</div>

<script type="text/javascript" >
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
</script>
<%@ include file="/common/footer_eoms.jsp"%>
