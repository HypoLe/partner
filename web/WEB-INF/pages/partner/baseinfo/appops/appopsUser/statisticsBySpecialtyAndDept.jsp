<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<style type="text/css">
.testClass{
text-align:center;
}
</style>

	<script type="text/javascript">
		
	</script>
<display:table name="taskList" id="taskList" class="table businessdesignMain" export="true" requestURI="partnerAppOpsUsers.do">
	<display:column title="部门" style="text-align:center;white-space:nowrap;" headerClass="testClass">
	<c:if test="${taskList.deptId eq '合计'}">
	合计
	</c:if>
	<c:if test="${taskList.deptId ne '合计'}">
	${taskList.deptId}
	</c:if>
	</display:column>
	<display:column title="运维主任" property="director" style="text-align:center;"/>
	<display:column title="移动核心网维护" property="coreNetWork" style="text-align:center;"/>
	<display:column title="无线网维护" property="infiniteNet" style="text-align:center;"/>
	<display:column title="传输网维护" property="transmission" style="text-align:center;"/>
	<display:column title="数据网维护" property="dataNetWork" style="text-align:center;"/>
	<display:column title="接入网维护" property="accessNetWork" style="text-align:center;"/>
	<display:column title="业务平台维护" property="businessPlatform" style="text-align:center;"/>
	<display:column title="交换网维护" property="exchangeNetWork" style="text-align:center;"/>
	<display:column title="线路维护" property="lineMaintenance" style="text-align:center;"/>
	<display:column title="IT系统维护" property="systemMaintenance" style="text-align:center;"/>
	<display:column title="网络服务" property="webService" style="text-align:center;"/>
	<display:column title="动力环境及配套" property="dynamicEnvironment" style="text-align:center;"/>
	<display:column title="资源管理" property="resourceAdministration" style="text-align:center;"/>
	<display:column title="网络信息安全管理" property="networkSecurity" style="text-align:center;"/>
	<display:column title="网络监控" property="networkMonitoring" style="text-align:center;"/>
	<display:column title="机线员" property="twister" style="text-align:center;"/>
	<display:column title="维护管理" property="maintenance" style="text-align:center;"/>
	<display:column title="现场综合维护" property="comprehensive" style="text-align:center;"/>
	<display:column property="rowSum"  title="合计" style="text-align:center;"/>
	
	<display:setProperty name="export.excel" value="true"/>
	<display:setProperty name="export.rtf" value="false"/>
	<display:setProperty name="export.pdf" value="false"/>
	<display:setProperty name="export.xml" value="false"/>
	<display:setProperty name="export.csv" value="false"/> 
</display:table>
<%@ include file="/common/footer_eoms.jsp"%>