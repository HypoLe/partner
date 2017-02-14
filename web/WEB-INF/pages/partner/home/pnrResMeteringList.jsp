<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

<script type="text/javascript">
var jq=jQuery.noConflict();
jq(function() {
	pageAdjust();
});
/**计次费用界面修饰*/
function pageAdjust() {
		jq("#main br:eq(0)").remove();
		jq("#main br:eq(0)").remove();
		var pagelinks = jq(".pagelinks");
		var pagebanner = jq(".pagebanner");
		var table = jq("#pnrResMeteringList");
		pagelinks.remove();
		//table.after(page);
		//table.after("<br/><br/>");
		pagebanner.after(pagelinks);
		pagelinks.css({"text-align":"left","margin-top":"-5px"});	
		window.parent.Ext.get("jchzDIV").unmask();
} 
</script>

<logic:present name="pnrResMeteringList" scope="request">
	<display:table id="pnrResMeteringList"
					name="pnrResMeteringList" 
					pagesize="${pagesize}" size="${size}"
					requestURI="${app}/partner/mainPage/pnrHomePageAction.do" export="false" 
					sort="list" cellspacing="0" cellpadding="0" class="table pnrResMeteringList" partialList="true" >
				
				<display:column sortable="true" headerClass="sortable" title="年度">
					${pnrResMeteringList['s_year']}
				</display:column>                                
				<display:column sortable="true" headerClass="sortable" title="月份">
					${pnrResMeteringList['s_mon']}
				</display:column>                
				<%-- 
				<display:column sortable="true" headerClass="sortable" title="代维省公司名">
					${pnrResMeteringList['deptname_province']}
				</display:column>     
				<display:column sortable="true" headerClass="sortable" title="代维地州公司名">
					${pnrResMeteringList['deptname_region']}
				</display:column>       
				<display:column sortable="true" headerClass="sortable" title="区县代维中心名">
					${pnrResMeteringList['deptname_city']}
				</display:column>
				--%>
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
	</display:table>
</logic:present>
	
<%@ include file="/common/footer_eoms.jsp"%>
