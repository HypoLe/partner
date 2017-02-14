<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" import="java.util.*,com.boco.eoms.partner.baseinfo.webapp.form.*;"%>

<html>
<head>
 <base target="_self"/>
<%@ include file="/common/header_eoms_form.jsp"%>
<script language="javaScript" type="text/javascript"
	src="${app}/scripts/module/partner/ajax.js"></script>
		<%
			String path = request.getContextPath();
			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/"; 
		%>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>		
<script type="text/javascript">
var jq=$.noConflict(); //jquery交出控制权，以免和EXTJS冲突
</script>
 
		<div id="sheetform">
			<html:form action="/repairFault.do?method=repairFaultList"
				styleId="theform">
				<table class="formTable"  style="width:100%">
					<!--时间 -->
					<tr>
						<td class="label" style="width:10%">
							统计开始时间
						</td>
						<td class="content" style="width:20%">
							<input type="text" class="text" name="startTime"
								readonly="readonly" id="startTime" value="${startTime}"
								onclick="popUpCalendar(this, this,'yyyy-mm-dd',null,null,false,-1,0)"
								 />

						</td>
						<td class="label" style="width:10%">
							统计结束时间
						</td>
						<td class="content" style="width:20%">
							<input type="text" class="text" name="endTime"
								readonly="readonly" id="endTime" value="${endTime}"
								onclick="popUpCalendar(this, this,'yyyy-mm-dd',null,null,false,-1,0)"
								/>

						</td>
					</tr>

				</table>
				<!-- buttons -->
				<div class="form-btns">
					<html:submit styleClass="btn" property="method.save"
						styleId="method.save">
                查询
                
                
            </html:submit>
					<html:button property="" styleClass="btn" onclick="newReset()">重置</html:button>
				</div>
			</html:form>
		</div>
		<bean:define id="url" value="repairFault.do" />
		<form id="testform" name="testform" action="${app}/activiti/statistics/repairFault.do?method=repairFaultList" method="post">	
		<display:table name="schemeRatelist" cellspacing="0" cellpadding="0"
			id="schemeRatelist" pagesize="${pageSize}" class="listTable schemeRatelist"
			export="true" requestURI="repairFault.do" sort="list"
			size="total" partialList="false">
			
			<display:column sortable="false" headerClass="sortable" title="时间"  property="time_month" />
			<display:column sortable="false" headerClass="sortable" title="本地网-审核工单数"  property="interface_audit_num" />
			<display:column sortable="false" headerClass="sortable" title="本地网-审核金额"  property="interface_audit_money" />
			<display:column sortable="false" headerClass="sortable" title="本地网-批复工单"  property="interface_approved_num" />
			<display:column sortable="false" headerClass="sortable" title="本地网-批复金额"  property="interface_approved_money" />
			<display:column sortable="false" headerClass="sortable" title="本地网-延缓批复工单数"  property="interface_delay_approved_num" />
			<display:column sortable="false" headerClass="sortable" title="本地网-延缓批复金额"  property="interface_delay_approved_money" />
	
			<display:setProperty name="export.rtf" value="false" />
			<display:setProperty name="export.pdf" value="false" />
			<display:setProperty name="export.xml" value="false" />
			<display:setProperty name="export.csv" value="false" />
		</display:table>
		</form>
		<%@ include file="/common/footer_eoms.jsp"%>