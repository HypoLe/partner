<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/nop3/common/nop3_header.jsp"%>
<script>
Ext.onReady(function(){
		v = new eoms.form.Validation({form:'searchForm'});}
);
function ifSelectedDate(){
	var flag = false;
	if(document.getElementById("startDate2search").value==""){
		flag = true;
	};
	if(document.getElementById("endDate2search").value==""){
		flag = true;
	};
	if(flag){
		alert("请补全日期");
		document.getElementsByName("periodicalFlag")[0].selectedIndex = 1;
		return false;
	}
}
</script>
<div class="ui-widget">
		<div style="margin-top: 20px; padding: 0 .7em;"
			class="ui-state-highlight ui-corner-all">
			<p>
				<span style="float: left; margin-right: .3em;"
					class="ui-icon ui-icon-carat-1-e"></span> &nbsp;
		</div>
	</div>
<div id="qucikNavRef" style="margin-bottom: 15px">
	<span></span>	
</div>
	<form action="${app}/outlay/Outlay.do?method=statistics" method="post" id="searchForm">
		<table id="sheet" class="formTable">
			<tr>
				<td class="label">
					部门
				</td>
				<td>
				<input type="text" id="partnerName_CN" name="partnerName_CN" value="<eoms:id2nameDB id='${partnerName2search}' beanId='partnerDeptDao'/>" alt="allowBlank:true"/>
				<input type="hidden" id="partnerName2search" name ="partnerName2search" value="${partnerName2search}"/></td>
				 <eoms:xbox id="partnerNameTree" dataUrl="${app}/xtree.do?method=userFromComp&popedom=true"  
						rootId="" rootText="代维公司树"  valueField="partnerName2search" handler="partnerName_CN" 
						textField="partnerName_CN" checktype="dept" single="true" />
				</td>
			<td class="label">
				开始日期
			</td>
			<td>
				<input type="text" class="text"
					name="startDate2search"
					onclick="popUpCalendar(this, this,null,null,null,false,-1);"
					alt="vtype:'lessThen',link:'endDate2search',vtext:'开始时间不能晚于结束时间',allowBlank:true"
					id="startDate2search" value=""/>
			</td>
			<td class="label">
				结束日期
			</td>
			<td>
				<input type="text" class="text"
					name="endDate2search"
					onclick="popUpCalendar(this, this,null,null,null,false,-1);"
					alt="vtype:'moreThen',link:'startDate2search',vtext:'结束时间不能早于开始时间',allowBlank:true"
					id="endDate2search" value=""/>
			</td>
			<td class="label">
				进行同期对比
			</td>
			<td>
				<select name="periodicalFlag" onchange="ifSelectedDate();">
					<option value="yes">是</option>
					<option value="no" selected>否</option>
				</select>
			</td>
		</tr>
	</table>
		<input type="submit" value="统计"/>
	</form>
	<br/><br/><br/>
<table class="formTable">
	<caption>
		<div class="header center">
		费用支出情况 (${startDate2search} -- ${endDate2search})
		</div>
	</caption>
	<tr>
		<td class="label" style="vertical-align:middle;text-align:center" rowspan=2>
		日期
		</td>
		<td class="label" style="vertical-align:middle;text-align:center" rowspan=2>
		部门
		</td>
		<td class="label" style="vertical-align:middle;text-align:center" rowspan=2>
		费用类别
		</td>
		<td class="label" style="vertical-align:middle;text-align:center" colspan=2>
		费用金额
		</td>
	</tr>
	<tr>
		<td class="label" style="vertical-align:middle;text-align:center">
		已审批
		</td>
		<td class="label" style="vertical-align:middle;text-align:center">
		未审批
		</td>
	</tr>
	<c:forEach items="${dataList}" var="obj" varStatus="i">
		<tr>
			<td class="content" style="vertical-align:middle">
			${obj.date}
			</td>
			<td class="content" style="vertical-align:middle">
			<eoms:id2nameDB id="${obj.partnerName}" beanId="partnerDeptDao"/>
			</td>
			<td class="content" style="vertical-align:middle">
			${obj.feeType}
			</td>
			<td class="content" style="vertical-align:middle">
			${obj.feeY}
			</td>
			<td class="content" style="vertical-align:middle">
			${obj.feeN}
			</td>
		</tr>
	</c:forEach>
</table>
   <script language="JavaScript" src="${app}/FusionCharts/FusionCharts.js"></script>
   <br/><br/><br/>
   <div id="chartdiv1" align="center">图形报表1</div>
   <script type="text/javascript">
      var myChart = new FusionCharts("${app}/FusionCharts/MSColumn3DLineDY.swf", "myChartId", "100%", "300", "0", "0");
      myChart.setDataXML("${xmlData}");
      myChart.render("chartdiv1");
   </script>
  
<%@ include file="/common/footer_eoms.jsp"%>