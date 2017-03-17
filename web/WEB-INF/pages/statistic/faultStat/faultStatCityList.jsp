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
		function newReset(){
			document.getElementById("startTime").value="";//开始时间
			document.getElementById("endTime").value="";//开始时间	
	}
</script>
 
		<div id="sheetform">
			<html:form action="/faultStat.do?method=faultStatByCity&type=1"
				styleId="theform" >
				<table class="formTable"  style="width:100%">
					<!--时间 -->
					<tr>
						<td class="label" style="width:10%">
							派单开始时间
						</td>
						<td class="content" style="width:20%">
							<input type="text" class="text" name="startTime"
								readonly="readonly" id="startTime" value="${startTime}"
								onclick="popUpCalendar(this, this,'yyyy-mm-dd',null,null,false,-1,0)"
								alt="vtype:'lessThen',link:'sheetCompleteLimit',vtext:'计划开始时间不能晚于计划结束时间',allowBlank:true" />

						</td>
						<td class="label" style="width:10%">
							派单结束时间
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
		<bean:define id="url" value="faultStat.do" />
		<form id="testform" name="testform" action="${app}/activiti/statistics/faultStat.do?method=faultStatByCity" method="post">	
		<display:table name="faultStatCityList" cellspacing="0" cellpadding="0"
			id="faultStatCityList" pagesize="${pageSize}" class="listTable faultStatCityList"
			export="true" requestURI="faultStat.do" sort="list"
			size="total" partialList="true">
			<display:column sortable="false" headerClass="sortable" title="地市">
				<c:choose>
					<c:when test="${faultStatCityList.city eq '2828'}">
						合计
					</c:when>
					<c:otherwise>
						<a href="${app}/activiti/statistics/faultStat.do?method=faultStatByCounty&amp;city=${faultStatCityList.id}&amp;startTime=${startTime}&amp;endTime=${endTime}" target="_blank">
							${faultStatCityList.city}
						</a>
					</c:otherwise>
				</c:choose>
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="光缆线路设备量（百皮长公里）"  property="equipmentNum" />
			<display:column sortable="false" headerClass="sortable" title="报障工单数"  property="faultSheetNum" />
			<display:column sortable="false" headerClass="sortable" title="归集确认故障数"  property="imputationConfirmNum" />
			<display:column sortable="false" headerClass="sortable" title="故障率（次/百皮长公里）"  property="faultRate" />
			<display:column sortable="false" headerClass="sortable" title="平均故障历时"  property="averageFaultLast" />
			<display:column sortable="false" headerClass="sortable" title="公客平均派单时长"  property="maleGuestsLast" />
			<display:column sortable="false" headerClass="sortable" title="超时工单数量">
			<c:choose>
				<c:when test="${faultStatCityList.city eq '2828'}">
					${faultStatCityList.chaoshigongdanshu}
				</c:when>
				<c:otherwise>
				  <a href="${app}/activiti/statistics/faultStat.do?method=timeoutGongdanList&amp;city=${faultStatCityList.id}&amp;type=1&amp;startTime=${startTime}&amp;endTime=${endTime}" target="_blank">
				     <font color="red">${faultStatCityList.chaoshigongdanshu}</font>
			      </a>
				</c:otherwise>
			</c:choose>
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="故障修复及时率"  property="guzhangxiufujishilv" />
			<display:column sortable="false" headerClass="sortable" title="故障重修数">
			<c:choose>
				<c:when test="${faultStatCityList.city eq '2828'}">
					${faultStatCityList.faultRebuildNum}
				</c:when>
				<c:otherwise>
				   <a href="${app}/activiti/statistics/faultStat.do?method=timeoutGongdanList&amp;city=${faultStatCityList.id}&amp;type=2&amp;startTime=${startTime}&amp;endTime=${endTime}" target="_blank">
				       <font color="red">${faultStatCityList.faultRebuildNum}</font>
			       </a>
				</c:otherwise>
			</c:choose>
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="故障重修率"  property="faultRebuildRate" />
			<display:column sortable="false" headerClass="sortable" title="材料金额">
				<fmt:formatNumber value="${faultStatCityList.materialMoney}" pattern="##.##" maxFractionDigits='2' minFractionDigits='0'  />
			</display:column>
			
			<display:setProperty name="export.rtf" value="false" />
			<display:setProperty name="export.pdf" value="false" />
			<display:setProperty name="export.xml" value="false" />
			<display:setProperty name="export.csv" value="false" />
		</display:table>
		</form>
		<%@ include file="/common/footer_eoms.jsp"%>