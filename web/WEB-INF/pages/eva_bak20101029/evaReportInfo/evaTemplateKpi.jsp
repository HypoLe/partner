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
	<div id="kpiContent">
		<html:form action="/evaKpis.do?method=saveKpi" styleId="evaKpiForm">
			<table align="center"  class="formTable" style="width:70%;" >
	<caption>
		<div class="header center">指标详细信息</div>
	</caption>			
				<tr>
					<td class="label" >
						指标名称
					</td>
					<td  class="content">
							${evaKpiForm.kpiName}
						<font style="color: red;">${fallure }</font>
					</td>
				</tr>
				<tr>
					<td class="label" >
						权重
					</td>
					<td class="content">
						${evaKpiForm.weight}
					</td>
				</tr>
				<tr>
					<td class="label" >
						算法描述
					</td>
					<td class="content">
						${evaKpiForm.algorithm}
					</td>
				</tr>
				<tr>
					<td class="label" >
						备注
					</td>
					<td class="content">
						${evaKpiForm.remark}
					</td>
				</tr>
			</table>
			<input type="hidden" id="id" name="id" value="${evaKpiForm.id}" />
			<input type="hidden" id="parentNodeId" name="parentNodeId"
				value="${requestScope.parentNodeId}" />
		</html:form>
	</div>
</div>
<%@ include file="/common/footer_eoms.jsp"%>
