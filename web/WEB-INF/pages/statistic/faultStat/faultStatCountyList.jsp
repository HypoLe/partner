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
		startTime = startTime.replace(/-/g,"/");
		var endTime=document.getElementById("endTime").value;//结束时间
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
		<bean:define id="url" value="faultStat.do" />
		<form id="testform" name="testform" action="${app}/activiti/statistics/faultStat.do?method=faultStatByCounty" method="post">	
		<display:table name="faultStatCountyList" cellspacing="0" cellpadding="0"
			id="faultStatCountyList" pagesize="${pageSize}" class="listTable faultStatCountyList"
			export="true" requestURI="faultStat.do" sort="list"
			size="total" partialList="true">
			<display:column sortable="false" headerClass="sortable" title="地市">
				<c:choose>
					<c:when test="${faultStatCountyList.city eq '2828'}">
						合计
					</c:when>
					<c:otherwise>
						${faultStatCountyList.city}
					</c:otherwise>
				</c:choose>
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="报障工单数"  property="faultSheetNum" />
			<display:column sortable="false" headerClass="sortable" title="归集确认故障数"  property="imputationConfirmNum" />
			<display:column sortable="false" headerClass="sortable" title="平均故障历时"  property="averageFaultLast" />
			<display:column sortable="false" headerClass="sortable" title="公客平均派单时长"  property="maleGuestsLast" />
			<display:column sortable="false" headerClass="sortable" title="超时工单数量">
			<c:choose>
				<c:when test="${faultStatCountyList.city eq '2828'}">
					${faultStatCountyList.chaoshigongdanshu}
				</c:when>
				<c:otherwise>
					<a href="${app}/activiti/statistics/faultStat.do?method=timeoutGongdanList&amp;city=${faultStatCountyList.id}&amp;type=3&amp;startTime=${startTime}&amp;endTime=${endTime}&amp;cityId=${cityId}" target="_blank">
			              <font color="red">${faultStatCountyList.chaoshigongdanshu}</font> 
		            </a>
				</c:otherwise>
			</c:choose>
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="故障修复及时率"  property="guzhangxiufujishilv" />
			<display:column sortable="false" headerClass="sortable" title="故障重修数">
			<c:choose>
				<c:when test="${faultStatCountyList.city eq '2828'}">
					${faultStatCountyList.faultRebuildNum}
				</c:when>
				<c:otherwise>
					<a href="${app}/activiti/statistics/faultStat.do?method=timeoutGongdanList&amp;city=${faultStatCountyList.id}&amp;type=4&amp;startTime=${startTime}&amp;endTime=${endTime}&amp;cityId=${cityId}" target="_blank">
				<font color="red">${faultStatCountyList.faultRebuildNum}</font>
			        </a>
				</c:otherwise>
			</c:choose>
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="故障重修率"  property="faultRebuildRate" />
			<display:column sortable="false" headerClass="sortable" title="材料金额">
				<fmt:formatNumber value="${faultStatCountyList.materialMoney}" pattern="##.##" maxFractionDigits='2' minFractionDigits='0'  />
			</display:column>
			
			<display:setProperty name="export.rtf" value="false" />
			<display:setProperty name="export.pdf" value="false" />
			<display:setProperty name="export.xml" value="false" />
			<display:setProperty name="export.csv" value="false" />
		</display:table>
		</form>
		<%@ include file="/common/footer_eoms.jsp"%>