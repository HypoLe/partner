<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/extlibs.jsp"%>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>

<script type="text/javascript" language="Javascript"> 
</script>
<script type="text/javascript" src="${app}/arcGis/js/fj/arcGisPing.js"></script>

<body>
<div id="divInput" >
<input id="txtURL" type="hidden"  value=""/>
<input id="txtTimeout" type="hidden" value="2000" />
<input id="btnSwitch" type="button" value="开始网络测试" onclick="handleBtnClick()" />
<input id="clear" type="button" value="清除" onclick="clearNet()" />
<hr/>
</div>
<div id="divContent" ></div>
<table class="formTable" style="width:100%" align="center" id="res">
	<caption>
		<div class="header center">代维资源数&nbsp;</div>
	</caption>
	<tr>
	 <c:forEach items="${specialtyList}"  var="specialtylist" >
	 	<td class="label">
	   ${specialtylist}
		</td>
	 </c:forEach>
	 </tr>
	 <tr>
	 <c:forEach items="${countSpecialtyList}"  var="countSpecialtylist" varStatus="i">
	 	<td>
	   	 <a href="${app }/partner/arcGis/arcGis.do?method=getCountryCountDetailForArcGis&type=res&iscity=city&special=${specialtyDictList[i.count-1]}">${countSpecialtylist}</a>
		</td>
	 </c:forEach>
	 </tr>
</table>
<table class="formTable" style="width:100%" align="center" id="user">
     <caption>
		<div class="header center">代维人员数&nbsp;</div>
	</caption>
	<tr>
	 <c:forEach items="${specialtyList}"  var="specialtylist" >
	 	<td class="label">
	   ${specialtylist}
		</td>
	 </c:forEach>
	 </tr>
	 <tr>
	 <c:forEach items="${countUserList}"  var="countUserlist" varStatus="i">
	 	<td>
	   		<a href="${app }/partner/arcGis/arcGis.do?method=getCountryCountDetailForArcGis&type=user&iscity=city&special=${specialtyDictList[i.count-1]}">${countUserlist}</a>
		</td>
	 </c:forEach>
	 </tr>
</table>
<c:if test="${carAreaList!=null }">
<table class="formTable" style="width:100%" align="center" id="car">
     <caption>
		<div class="header center">代维车辆数&nbsp;</div>
	</caption>
	<tr>
	 <c:forEach items="${carList}"  var="carArealist" >
	 	<td class="label">
	   ${carArealist.areaname}
		</td>
	 </c:forEach>
	 </tr>
	 <tr>
	 <c:forEach items="${carList}"  var="carCountlist" >
	 	<td>
	   
	   <a href="${app }/partner/arcGis/arcGis.do?method=getCountryCountDetailForArcGis&type=car&iscity=${carCountlist.areaid}">${carCountlist.count}</a>
		</td>
	 </c:forEach>
	 </tr>
</table>
</c:if>
<c:if test="${oilAreaList!=null }">
<table class="formTable" style="width:100%" align="center" id="oil">
     <caption>
		<div class="header center">代维油机数&nbsp;</div>
	</caption>
	<tr>
	 <c:forEach items="${oilList}"  var="oilArealist" >
	 	<td class="label">
	   ${oilArealist.areaname}
		</td>
	 </c:forEach>
	 </tr>
	 <tr>
	 <c:forEach items="${oilList}"  var="oilCountlist" >
	 	<td>
	   
	   <a href="${app }/partner/arcGis/arcGis.do?method=getCountryCountDetailForArcGis&type=oil&iscity=${oilCountlist.areaid}">${oilCountlist.count}</a>
		</td>
	 </c:forEach>
	 </tr>
</table>
</c:if>
<script type="text/javascript">
		//parent.removeblock();
		Ext.onReady(function(){
			if(parent.frames['arcGis-page'].map!=null&&parent.frames['arcGis-page'].map.loaded){
			parent.frames['arcGis-page'].mapCenterAndZoom(true,false);
			}
			var car=[];
			var carInformation=[];
			var oilInformation=[];
			var oil=[];
			if(Ext.isIE){
				 carInformation=document.getElementById("car").innerText.split(' ');
					if(carInformation.length!=0){
						for(var i=0;i<carInformation.length;i++){
							var oi=carInformation[i].trim();
							if(oi!=""&&oi!="\r\n"){
								car.push(oi);
							}
						}
					}
			 oilInformation=document.getElementById("oil").innerText.split(' ');
					if(oilInformation.length!=0){
						for(var i=0;i<oilInformation.length;i++){
							var oii=oilInformation[i].trim();
							if(oii!=""&&oii!="\r\n"){
								oil.push(oii);
							}
						}
					}
					/*
					if(0!=car.length){
						var fistCount=car[(car.length)/2];
						fistCount=fistCount.substring(fistCount.length-1,fistCount.length);
						car[(car.length)/2]=fistCount;
						var ccccc=null;
						}*/
			}
			else{
				carInformation=document.getElementById("car").textContent.trim().split('\n');
					if(carInformation.length!=0){
						for(var i=0;i<carInformation.length;i++){
							var oi=carInformation[i].trim();
							if(oi!=""){
								car.push(oi);
							}
						}
					}
					oilInformation=document.getElementById("oil").textContent.trim().split('\n');
					if(oilInformation.length!=0){
						for(var i=0;i<oilInformation.length;i++){
							var oii=oilInformation[i].trim();
							if(oii!=""){
								oil.push(oii);
							}
						}
					}
			}
			if(parent.frames['arcGis-page'].map!=null&&parent.frames['arcGis-page'].map.loaded){
			parent.frames['arcGis-page'].cityAdapter(car,oil,true);
			}
			parent.removeblock();
		});
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