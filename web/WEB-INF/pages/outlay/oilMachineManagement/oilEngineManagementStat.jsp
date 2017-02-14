<%@ include file="/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%
request.setAttribute("province", "10");
%>
<script type="text/javascript"
	src="${app}/deviceManagement/scripts/jquery/jquery-1.5.js"></script>
<script type="text/javascript">
    var myjs=jQuery.noConflict();
    	Ext.onReady(function(){	
			
})

    function sendBox() {		
		document.getElementById("checkAndSearchFrom").submit();		
	}
	
	
	
</script>



<form id="checkAndSearchFrom"
	action="OilEngineManagement.do?method=oilEngineStat" method="post">

<fieldset><legend>请输入统计条件</legend>
<table class="formTable">
<tr>
	
		<td class="label">基站名称</td>
		<td><input id="powerStations" type="text" name="powerStations"  class="text" /></td>
		<td class="label">油机</td>
		<td><input id="oilEngine" type="text" name="oilEngine"  class="text" /></td>
			 </tr>
		<tr>
		<td class="label">
			油机开始使用时间范围从：
		</td>	
		<td class="content">
				<input type="text" class="text"
					name="startDate2search"
					onclick="popUpCalendar(this, this,null,null,null,false,-1);"
					alt="vtype:'lessThen',link:'endDate2search',vtext:'开始时间不能晚于结束时间',allowBlank:false"
					id="startDate2search" value="${startDate2search}"/>	
		</td>
		<td class="label">
			到：
		</td>	
		<td class="content">
				<input type="text" class="text"
					name="endDate2search"
					onclick="popUpCalendar(this, this,null,null,null,false,-1);"
					alt="vtype:'moreThen',link:'startDate2search',vtext:'结束时间不能早于开始时间',allowBlank:false"
					id="endDate2search" value="${endDate2search}"/>			
		</td>
		</tr>
</table>
</fieldset>

<input type="button" name="统计" value="统计" onclick="sendBox()" /> <!-- This hidden area is for batch deleting. -->
<form>

<div>
<table cellpadding="0" class="table contentStaffList" cellspacing="0">
	<thead>
		<tr>
			<logic-el:present name="headList">
				<c:forEach items="${headList}" var="headlist">
					<th>${headlist}</th>
				</c:forEach>
			</logic-el:present>
		</tr>
	</thead>
	<logic-el:present name="tableList">
		<tbody>
				<tr>
					<td width="50%">代维公司录入信息总数</td>
					<td>${tableList[0]}</td>
				</tr>
				<tr>
					<td >动环告警信息总数</td>
					<td>${tableList[1]}</td>
				</tr>
				<tr>
					<td >代维公司录入但动环告警信息未录入总数</td>
					<td>${tableList[2]}</td>
				</tr>
				<tr>
					<td >动环告警录入但代维公司未录入总数</td>
					<td>${tableList[3]}</td>
				</tr>												
		</tbody>
	</logic-el:present>
</table>
</div>







<%@ include file="/common/footer_eoms.jsp"%>