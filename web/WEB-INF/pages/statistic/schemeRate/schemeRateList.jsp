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
	function save(){
		var startTime=document.getElementById("startTime").value;//开始时间
		var endTime=document.getElementById("endTime").value;//结束时间
		if(startTime == "" || endTime == ""){
			alert("开始时间、结束时间不可为空，请选择！");
			return;
		}
		startTime = startTime.replace(/-/g,"/");
		endTime = endTime.replace(/-/g,"/");
		var sta_date = new Date(startTime);
		var end_date = new Date(endTime);
		var num = (end_date-sta_date)/(1000*3600*24);
		if(num<0){
			alert("结束时间不能早于开始时间，请重新选择！");
			return;
		}
		if(num>31){
			alert("查询时间范围不能大于一个31天，请重新选择！");
			return;
		}
		document.forms(0).submit();
	}
</script>
 
		<div id="sheetform">
			<html:form action="/schemeRateStat.do?method=schemeRateActionList&type=1"
				styleId="theform" >
				<table class="formTable"  style="width:100%">
					<!--时间 -->
					<tr>
						<td class="label" style="width:10%">
							开始时间
						</td>
						<td class="content" style="width:20%">
							<input type="text" class="text" name="startTime"
								readonly="readonly" id="startTime" value="${startTime}"
								onclick="popUpCalendar(this, this,'yyyy-mm-dd',null,null,false,-1,0)"
								alt="vtype:'lessThen',link:'sheetCompleteLimit',vtext:'计划开始时间不能晚于计划结束时间',allowBlank:true" />

						</td>
						<td class="label" style="width:10%">
							结束时间
						</td>
						<td class="content" style="width:20%">
							<input type="text" class="text" name="endTime"
								readonly="readonly" id="endTime" value="${endTime}"
								onclick="popUpCalendar(this, this,'yyyy-mm-dd',null,null,false,-1,0)"
								alt="vtype:'moreThen',link:'sheetAcceptLimit',vtext:'计划结束时间不能早于计划开始时间',allowBlank:true" />

						</td>
					</tr>

				</table>
				<!-- buttons -->
				<div class="form-btns">
				<!-- 
					<html:submit styleClass="btn" property="method.save"
						styleId="method.save">
                查询
                
                
            </html:submit> -->
            		<html:button property="" styleClass="btn" onclick="save()">查询</html:button>
					<!--<html:button property="" styleClass="btn" onclick="newReset()">重置</html:button>-->
				</div>
			</html:form>
		</div>
		<bean:define id="url" value="schemeRateStat.do" />
		<form id="testform" name="testform" action="${app}/activiti/statistics/schemeRateStat.do?method=schemeRateActionList" method="post">	
		<display:table name="schemeRatelist" cellspacing="0" cellpadding="0"
			id="schemeRatelist" pagesize="${pageSize}" class="listTable schemeRatelist"
			export="true" requestURI="schemeRateStat.do" sort="list"
			size="total" partialList="true">
			<display:column sortable="false" headerClass="sortable" title="地市">
				<c:choose>
					<c:when test="${schemeRatelist.county eq '2828'}">
						合计
					</c:when>
					<c:otherwise>
						<eoms:id2nameDB id="${schemeRatelist.county}" beanId="tawSystemAreaDao" />
					</c:otherwise>
				</c:choose>
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="本地网-审核工单数"  property="interface_audit_num" />
			<display:column sortable="false" headerClass="sortable" title="本地网-审核金额"  property="interface_audit_money" />
			<display:column sortable="false" headerClass="sortable" title="本地网-批复工单"  property="interface_approved_num" />
			<display:column sortable="false" headerClass="sortable" title="本地网-批复金额"  property="interface_approved_money" />
			<display:column sortable="false" headerClass="sortable" title="本地网-延缓批复工单数"  property="interface_delay_approved_num" />
			<display:column sortable="false" headerClass="sortable" title="本地网-延缓批复金额"  property="interface_delay_approved_money" />
			<display:column style="text-align:center;" sortable="false" headerClass="sortable" title="本地网-驳回工单数" media="html">
			<a href="${app}/activiti/statistics/schemeRateStat.do?method=schemeRateRejectList&amp;startTime=${startTime}&amp;endTime=${endTime}&amp;city=${schemeRatelist.county}&amp;themeinterface=interface" target="_blank">
				<font color="red">${schemeRatelist.interface_reject_num}</font>
			</a>
			</display:column>
			<display:column style="text-align:center;" sortable="false" headerClass="headerNew" title="工单总数" media="csv excel xml pdf">
				<font color="red">${schemeRatelist.interface_reject_num}</font>
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="本地网-驳回金额"  property="interface_reject_money" />
			<display:column sortable="false" headerClass="sortable" title="本地网-当月审批工单数"  property="interface_monthapproved_num" />
			<display:column sortable="false" headerClass="sortable" title="本地网-当月审批金额"  property="interface_monthapproved_money" />
			
			<display:column sortable="false" headerClass="sortable" title="一般干线-审核工单数"  property="artery_audit_num" />
			<display:column sortable="false" headerClass="sortable" title="一般干线-审核金额"  property="artery_audit_money" />
			<display:column sortable="false" headerClass="sortable" title="一般干线-批复工单"  property="artery_approved_num" />
			<display:column sortable="false" headerClass="sortable" title="一般干线-批复金额"  property="artery_approved_money" />
			<display:column sortable="false" headerClass="sortable" title="一般干线-延缓批复工单数"  property="artery_delay_approved_num" />
			<display:column sortable="false" headerClass="sortable" title="一般干线-延缓批复金额"  property="artery_delay_approved_money" />
			<display:column style="text-align:center;" sortable="false" headerClass="sortable" title="一般干线-驳回工单数" media="html">
			<a href="${app}/activiti/statistics/schemeRateStat.do?method=schemeRateRejectList&amp;startTime=${startTime}&amp;endTime=${endTime}&amp;city=${schemeRatelist.county}&amp;themeinterface=artery" target="_blank">
				<font color="red">${schemeRatelist.artery_reject_num}</font>
			</a>
			</display:column>
			<display:column style="text-align:center;" sortable="false" headerClass="headerNew" title="工单总数" media="csv excel xml pdf">
				${schemeRatelist.artery_reject_num}
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="一般干线-驳回金额"  property="artery_reject_money" />
			<display:column sortable="false" headerClass="sortable" title="一般干线-当月审批工单数"  property="artery_monthapproved_num" />
			<display:column sortable="false" headerClass="sortable" title="一般干线-当月审批金额"  property="artery_monthapproved_money" />
			
			<display:column sortable="false" headerClass="sortable" title="干线施工-审核工单数"  property="m_artery_audit_num" />
			<display:column sortable="false" headerClass="sortable" title="干线施工-审核金额"  property="m_artery_audit_money" />
			<display:column sortable="false" headerClass="sortable" title="干线施工-批复工单"  property="m_artery_approved_num" />
			<display:column sortable="false" headerClass="sortable" title="干线施工-批复金额"  property="m_artery_approved_money" />
			<display:column sortable="false" headerClass="sortable" title="干线施工-延缓批复工单数"  property="m_artery_delay_approved_num" />
			<display:column sortable="false" headerClass="sortable" title="干线施工-延缓批复金额"  property="m_artery_delay_approved_money" />
			<display:column style="text-align:center;" sortable="false" headerClass="sortable" title="干线施工-驳回工单数" media="html">
			<a href="${app}/activiti/statistics/schemeRateStat.do?method=schemeRateRejectList&amp;startTime=${startTime}&amp;endTime=${endTime}&amp;city=${schemeRatelist.county}&amp;themeinterface=m_artery" target="_blank">
				<font color="red">${schemeRatelist.m_artery_reject_num}</font>
			</a>
			</display:column>
			<display:column style="text-align:center;" sortable="false" headerClass="headerNew" title="工单总数" media="csv excel xml pdf">
				<font color="red">${schemeRatelist.m_artery_reject_num}</font>
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="干线施工-驳回金额"  property="m_artery_reject_money" />
			<display:column sortable="false" headerClass="sortable" title="干线施工-当月审批工单数"  property="m_artery_monthapproved_num" />
			<display:column sortable="false" headerClass="sortable" title="干线施工-当月审批金额"  property="m_artery_monthapproved_money" />
			
			<display:column sortable="false" headerClass="sortable" title="大修改造-审核工单数"  property="ro_audit_num" />
			<display:column sortable="false" headerClass="sortable" title="大修改造-审核金额"  property="ro_audit_money" />
			<display:column sortable="false" headerClass="sortable" title="大修改造-批复工单"  property="ro_approved_num" />
			<display:column sortable="false" headerClass="sortable" title="大修改造-批复金额"  property="ro_approved_money" />
			<display:column sortable="false" headerClass="sortable" title="大修改造-延缓批复工单数"  property="ro_delay_approved_num" />
			<display:column sortable="false" headerClass="sortable" title="大修改造-延缓批复金额"  property="ro_delay_approved_money" />
			<display:column style="text-align:center;" sortable="false" headerClass="sortable" title="大修改造-驳回工单数" media="html">
			<a href="${app}/activiti/statistics/schemeRateStat.do?method=schemeRateRejectList&amp;startTime=${startTime}&amp;endTime=${endTime}&amp;city=${schemeRatelist.county}&amp;themeinterface=ro" target="_blank">
				<font color="red">${schemeRatelist.ro_reject_num}</font>
			</a>
			</display:column>
			<display:column style="text-align:center;" sortable="false" headerClass="headerNew" title="工单总数" media="csv excel xml pdf">
				<font color="red">${schemeRatelist.ro_reject_num}</font>
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="大修改造-驳回金额"  property="ro_reject_money" />
			<display:column sortable="false" headerClass="sortable" title="大修改造-当月审批工单数"  property="ro_monthapproved_num" />
			<display:column sortable="false" headerClass="sortable" title="大修改造-当月审批金额"  property="ro_monthapproved_money" />
			
			<display:column sortable="false" headerClass="sortable" title="合格工单总数"  property="qualified_num" />
			<display:column sortable="false" headerClass="sortable" title="驳回工单总数"  property="reject_num" />
			<display:column sortable="false" headerClass="sortable" title="合格率"  property="qualified_rate" />
			
			<display:setProperty name="export.rtf" value="false" />
			<display:setProperty name="export.pdf" value="false" />
			<display:setProperty name="export.xml" value="false" />
			<display:setProperty name="export.csv" value="false" />
		</display:table>
		</form>
		<%@ include file="/common/footer_eoms.jsp"%>