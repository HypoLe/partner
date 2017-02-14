<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header.jsp"%>
<script type="text/javascript" src="${app}/baidu/jquery-easyui-1.2.6/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${app}/baidu/js/baiduGisHelp.js"></script>
<script type="text/javascript" src="${app}/baidu/js/ping.js"></script>

<body>
<div id="divInput" >
<input id="txtURL" type="hidden"  value="www.baidu.com"/>
<input id="txtTimeout" type="hidden" value="2000" />
<input id="btnSwitch" type="button" value="开始网络测试" onclick="handleBtnClick()" />
<input id="clear" type="button" value="清除" onclick="clearNet()" />
<hr/>
</div>
<div id="divContent" ></div>
<table class="formTable" style="width:100%" align="center">
	<caption>
		<div class="header center">代维资源数</div>
	</caption>
	<tr>
	 <c:forEach items="${specialtyList}"  var="specialtylist" >
	 	<td class="label">
	   ${specialtylist}
		</td>
	 </c:forEach>
	 </tr>
	 <tr>
	 <c:forEach items="${countSpecialtyList}"  var="countSpecialtylist" >
	 	<td>
	   ${countSpecialtylist}
		</td>
	 </c:forEach>
	 </tr>
</table>
<table class="formTable" style="width:100%" align="center">
     <caption>
		<div class="header center">代维人员数</div>
	</caption>
	<tr>
	 <c:forEach items="${specialtyList}"  var="specialtylist" >
	 	<td class="label">
	   ${specialtylist}
		</td>
	 </c:forEach>
	 </tr>
	 <tr>
	 <c:forEach items="${countUserList}"  var="countUserlist" >
	 	<td>
	   ${countUserlist}
		</td>
	 </c:forEach>
	 </tr>
</table>
<table class="formTable" style="width:100%" align="center">
     <caption>
		<div class="header center">代维车辆数</div>
	</caption>
	<tr>
	 <c:forEach items="${carAreaList}"  var="carArealist" >
	 	<td class="label">
	   ${carArealist}
		</td>
	 </c:forEach>
	 </tr>
	 <tr>
	 <c:forEach items="${carCountList}"  var="carCountlist" >
	 	<td>
	   ${carCountlist}
		</td>
	 </c:forEach>
	 </tr>
</table>
<table class="formTable" style="width:100%" align="center">
     <caption>
		<div class="header center">代维油机数</div>
	</caption>
	<tr>
	 <c:forEach items="${oilAreaList}"  var="oilArealist" >
	 	<td class="label">
	   ${oilArealist}
		</td>
	 </c:forEach>
	 </tr>
	 <tr>
	 <c:forEach items="${oilCountList}"  var="oilCountlist" >
	 	<td>
	   ${oilCountlist}
		</td>
	 </c:forEach>
	 </tr>
</table>
<script type="text/javascript">
		parent.drawMaps();
		parent.removeblock();
		
		function clearNet(){
			document.getElementById("divContent").innerHTML = "";
		}
</script>
<%--<script type="text/javascript">
var jq=jQuery.noConflict();
	document.getElementById("divContent");
    	parent.blockUI({ message: $('#divContent') });
    	jq('#divContent');
		document.getElementById("btnSwitch").click();
		setTimeout(function(){
			document.getElementById("btnSwitch").click();
			parent.unblockUI();
			},10000);
		parent.removeblock();
</script>--%>

</body>



<%@ include file="/common/footer_eoms.jsp"%>