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
			<html:form action="/repairFault.do?method=faultTypeListPage&type=1"
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
		<form id="testform" name="testform" action="${app}/activiti/statistics/repairFault.do?method=faultTypeListPage" method="post">	
		<display:table name="repairFaultList" cellspacing="0" cellpadding="0"
			id="repairFaultList" pagesize="${pageSize}" class="listTable repairFaultList"
			export="true" requestURI="repairFault.do" sort="list"
			size="total" partialList="true">
			<display:column style="text-align:center;" sortable="false" headerClass="sortable" title="地市" >
			<c:choose>
					<c:when test="${repairFaultList.city eq '合计'}">
						合计
					</c:when>
					<c:otherwise>
				<a href="${app}/activiti/statistics/repairFault.do?method=repairFaultqxList&amp;startTime=${startTime}&amp;endTime=${endTime}&amp;city=${repairFaultList.id}&amp;themeinterface=artery" target="_blank">
				${repairFaultList.city}
			    </a>
					</c:otherwise>
				</c:choose>
			</display:column>
			<display:column sortable="false" headerClass="sortable" title="线路故障总数"  property="totalFaultNum" />
			<display:column sortable="false" headerClass="sortable" title="金额"  property="totalFaultAmout" />
			<display:column sortable="false" headerClass="sortable" title="车挂数量"  property="cheguaNum" />
			<display:column sortable="false" headerClass="sortable" title="车挂占比"  property="cheguaRate" />
			<display:column sortable="false" headerClass="sortable" title="外力施工数量"  property="wailiNum" />
			<display:column sortable="false" headerClass="sortable" title="外力施工占比"  property="wailiRate" />
			<display:column sortable="false" headerClass="sortable" title="火烧数量"  property="huoshaoNum" />
			<display:column sortable="false" headerClass="sortable" title="火烧占比"  property="huoshaoRate" />
			<display:column sortable="false" headerClass="sortable" title="被盗或人为破坏数量"  property="renweiNum" />
			
			<display:column sortable="false" headerClass="sortable" title="被盗或人为破坏占比"  property="renweiRate" />
			<display:column sortable="false" headerClass="sortable" title="自然断纤（纤芯裂化）数量"  property="duanxianNum" />
			<display:column sortable="false" headerClass="sortable" title="自然断纤（纤芯裂化）占比"  property="duanxianRate" />
			<display:column sortable="false" headerClass="sortable" title="接头盒进水数量"  property="jinshuiNum" />
			<display:column sortable="false" headerClass="sortable" title="接头盒进水占比"  property="jinshuiRate" />
			<display:column sortable="false" headerClass="sortable" title="尾纤及法兰盘数量"  property="weixianNum" />
			<display:column sortable="false" headerClass="sortable" title="尾纤及法兰盘占比"  property="weixianRate" />
			<display:column sortable="false" headerClass="sortable" title="鸟啄数量"  property="niaozhuoNum" />
			
			<display:column sortable="false" headerClass="sortable" title="鸟啄占比"  property="niaozhuoRate" />
			<display:column sortable="false" headerClass="sortable" title="鼠咬数量"  property="shuyaoNum" />
			<display:column sortable="false" headerClass="sortable" title="鼠咬占比"  property="shuyaoRate" />
			<display:column sortable="false" headerClass="sortable" title="自然灾害数量"  property="zaihaiNum" />
			<display:column sortable="false" headerClass="sortable" title="自然灾害占比"  property="zaihaiRate" />
			<display:column sortable="false" headerClass="sortable" title="其他数量"  property="otherNum" />
			<display:column sortable="false" headerClass="sortable" title="其他占比"  property="otherRate" />
			<display:setProperty name="export.rtf" value="false" />
			<display:setProperty name="export.pdf" value="false" />
			<display:setProperty name="export.xml" value="false" />
			<display:setProperty name="export.csv" value="false" />
		</display:table>
		</form>
		<%@ include file="/common/footer_eoms.jsp"%>