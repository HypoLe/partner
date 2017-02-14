<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
var jq=jQuery.noConflict();
</script>

<script type="text/javascript">

function openImport(){
	var handler = document.getElementById("openQuery");
	var el = Ext.get('listQueryObject');
	if(el.isVisible()){
		el.slideOut('t',{useDisplay:true});
		handler.innerHTML = "打开查询界面";
	}
	else{
		el.slideIn();
		handler.innerHTML = "关闭查询界面";
	}
}
</script>

<div align="center">
	计次费用明细表
</div><br><br/>

<logic:present name="pnrResMeteringList" scope="request">
	<display:table id="pnrResMeteringList"
					name="pnrResMeteringList" 
					pagesize="${pagesize}" size="${size}"
					requestURI="${app}/netresource/pnrResMeteringAction.do" export="true" 
					sort="list" cellspacing="0" cellpadding="0" class="table pnrResMeteringList" partialList="true" >
				
				<display:column sortable="true" headerClass="sortable" title="年度">
					${pnrResMeteringList['s_year']}
				</display:column>                                
				<display:column sortable="true" headerClass="sortable" title="月份">
					${pnrResMeteringList['s_mon']}
				</display:column>                
				<display:column sortable="true" headerClass="sortable" title="代维省公司名">
					${pnrResMeteringList['deptname_province']}
				</display:column>     
				<display:column sortable="true" headerClass="sortable" title="代维地州公司名">
					${pnrResMeteringList['deptname_region']}
				</display:column>       
				<display:column sortable="true" headerClass="sortable" title="区县代维中心名">
					${pnrResMeteringList['deptname_city']}
				</display:column>
				<display:column sortable="true" headerClass="sortable" title="资源名">
					${pnrResMeteringList['res_name']}
				</display:column>             
				<display:column sortable="true" headerClass="sortable" title="任务处理人">
					${pnrResMeteringList['username']}
				</display:column>         
				
				<c:if test="${not empty exportType}">
					<display:column property="sheetid" sortable="true" headerClass="sortable" title="工单编号" />
				</c:if>
				<c:if test="${empty exportType}">
					<display:column sortable="true" headerClass="sortable" title="工单编号">
						<c:if test="${pnrResMeteringList['metering_type'] == '故障管理'}">
							<a href="${app}/sheet/pnrfaultdeal/pnrfaultdeal.do?method=showMainDetailPage&sheetKey=${pnrResMeteringList['sheet_sysid']}" target="_blank">${pnrResMeteringList['sheetid']}</a>
						</c:if>
						<c:if test="${pnrResMeteringList['metering_type'] == '通用管理'}">
							<a href="${app}/sheet/pnrcommontask/pnrcommontask.do?method=showMainDetailPage&sheetKey=${pnrResMeteringList['sheet_sysid']}" target="_blank">${pnrResMeteringList['sheetid']}</a>
						</c:if>
						<c:if test="${pnrResMeteringList['metering_type'] == '投诉管理'}">
							<a href="${app}/sheet/pnrcomplaint/pnrcomplaint.do?method=showMainDetailPage&sheetKey=${pnrResMeteringList['sheet_sysid']}" target="_blank">${pnrResMeteringList['sheetid']}</a>
						</c:if>
					</display:column>
				</c:if>
				
				<display:column sortable="true" headerClass="sortable" title="计次类型名">
					${pnrResMeteringList['metering_type']}
				</display:column>         
				<display:column sortable="true" headerClass="sortable" title="专业名">
					${pnrResMeteringList['specialty_name']}
				</display:column>       
				<display:column sortable="true" headerClass="sortable" title="计量总次数">
					${pnrResMeteringList['tot_num']}
				</display:column>           
		<!-- Exclude the formats i do not need. -->
		<display:setProperty name="export.rtf" value="false" />
		<display:setProperty name="export.pdf" value="false" />
		<display:setProperty name="export.xml" value="false" />
		<display:setProperty name="export.csv" value="false" />
	</display:table>
</logic:present>
	
<%@ include file="/common/footer_eoms.jsp"%>
