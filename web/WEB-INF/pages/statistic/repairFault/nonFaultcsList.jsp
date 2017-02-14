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
	//重置
	function newReset(){
		document.getElementById("startTime").value="";            // 派单开始时间
		document.getElementById("endTime").value="";		// 派单结束时间
	}
</script>
 
		<div id="sheetform">
			<html:form action="/repairFault.do?method=nonFaultcsList&type=1"
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
		<bean:define id="url" value="repairFault.do" />
		<form id="testform" name="testform" action="${app}/activiti/statistics/repairFault.do?method=nonFaultcsList" method="post">	
		<display:table name="nonFaultcsList" cellspacing="0" cellpadding="0"
			id="nonFaultcsList" pagesize="${pageSize}" class="listTable nonFaultcsList"
			export="true" requestURI="repairFault.do" sort="list"
			size="total" partialList="true">
			<display:column style="text-align:center;" sortable="false" headerClass="sortable" title="地市">
				<c:choose>
					<c:when test="${nonFaultcsList.city eq '合计'}">
						合计
					</c:when>
					<c:otherwise>
				<a href="${app}/activiti/statistics/repairFault.do?method=nonFaultqxList&amp;startTime=${startTime}&amp;endTime=${endTime}&amp;city=${nonFaultcsList.id}&amp;themeinterface=artery" target="_blank">
				 ${nonFaultcsList.city}
			      </a>
					</c:otherwise>
				</c:choose>
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="非故障触发工单数"  property="nonFaultNum" />
			<display:column sortable="false" headerClass="sortable" title="非故障触发材料金额"  property="nonFaultAmout" />
			<display:column sortable="false" headerClass="sortable" title="更换电杆数量"  property="genghuandianganNum" />
			<display:column sortable="false" headerClass="sortable" title="更换电杆占比"  property="genghuandianganRate" />
			<display:column sortable="false" headerClass="sortable" title="更换吊线数量"  property="genghuandiaoxianNum" />
			<display:column sortable="false" headerClass="sortable" title="更换吊线占比"  property="genghuandiaoxianRate" />
			<display:column sortable="false" headerClass="sortable" title="更换拉线数量"  property="genghuanlaxianNum" />
			<display:column sortable="false" headerClass="sortable" title="更换拉线占比"  property="genghuanlaxianRate" />
			<display:column sortable="false" headerClass="sortable" title="光缆脱落整治数量"  property="guanglantuoluoNum" />
			
			<display:column sortable="false" headerClass="sortable" title="光缆脱落整治占比"  property="guanglantuoluoRate" />
			<display:column sortable="false" headerClass="sortable" title="电力线防护数量"  property="dianlixianNum" />
			<display:column sortable="false" headerClass="sortable" title="电力线防护占比"  property="dianlixianRate" />
			<display:column sortable="false" headerClass="sortable" title="光交箱整治数量"  property="guangjiaoxiangNum" />
			<display:column sortable="false" headerClass="sortable" title="光交箱整治占比"  property="guangjiaoxiangRate" />
			<display:column sortable="false" headerClass="sortable" title="人井盖增补数量"  property="renjinggaiNum" />
			<display:column sortable="false" headerClass="sortable" title="人井盖增补占比"  property="renjinggaiRate" />
			<display:column sortable="false" headerClass="sortable" title="其他安全隐患处理数量"  property="otherNum" />
			
			<display:column sortable="false" headerClass="sortable" title="其他安全隐患处理占比"  property="otherRate" />
			<display:setProperty name="export.rtf" value="false" />
			<display:setProperty name="export.pdf" value="false" />
			<display:setProperty name="export.xml" value="false" />
			<display:setProperty name="export.csv" value="false" />
		</display:table>
		</form>
		<%@ include file="/common/footer_eoms.jsp"%>