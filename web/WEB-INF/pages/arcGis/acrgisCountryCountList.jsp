<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header.jsp"%>
<%@ include file="/common/extlibs.jsp"%>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/arcGis/js/arcGisHelp.js"></script>
<script type="text/javascript" src="${app}/arcGis/js/arcGisPing.js"></script>
<body>

<c:if test="${type  eq 'cityres'}">
<table class="formTable" style="width:100%" align="center" id="cityRes">
	<caption>
		<div class="header center">代维资源数&nbsp;</div>
	</caption>
	<tr>
	 <c:forEach items="${list}"  var="specialtylist" >
	 	<td class="label">
	   		${specialtylist.areaname }
		</td>
	 </c:forEach>
	 </tr>
	 <tr>
	 <c:forEach items="${list}"  var="countSpecialtylist" varStatus="i">
	 	<td>
	 		 <a href="${app }/partner/arcGis/arcGis.do?method=getCountryCountDetailForArcGis&type=res&iscity=${countSpecialtylist.areaid }&special=${special}">${countSpecialtylist.count }</a>
		</td>
	 </c:forEach>
	 </tr>
</table>
</c:if>

<c:if test="${type  eq 'countryres'}">
<table class="formTable" style="width:100%" align="center" >
	<caption>
		<div class="header center">代维资源数&nbsp;</div>
	</caption>
	<tr>
	 <c:forEach items="${list}"  var="specialtylist" >
	 	<td class="label">
	   		${specialtylist.areaname }
		</td>
	 </c:forEach>
	 </tr>
	 <tr>
	 <c:forEach items="${list}"  var="countSpecialtylist" varStatus="i">
	 	<td>
	 		${countSpecialtylist.count }
		</td>
	 </c:forEach>
	 </tr>
</table>
</c:if>


<c:if test="${type  eq 'cityuser'}">
<table class="formTable" style="width:100%" align="center" id="cityRes">
	<caption>
		<div class="header center">代维人员数&nbsp;</div>
	</caption>
	<tr>
	 <c:forEach items="${list}"  var="specialtylist" >
	 	<td class="label">
	   		${specialtylist.areaname }
		</td>
	 </c:forEach>
	 </tr>
	 <tr>
	 <c:forEach items="${list}"  var="countSpecialtylist" varStatus="i">
	 	<td>
	 		<a href="${app }/partner/arcGis/arcGis.do?method=getCountryCountDetailForArcGis&type=user&iscity=${countSpecialtylist.areaid }&special=${special}">${countSpecialtylist.count }</a>
		</td>
	 </c:forEach>
	 </tr>
</table>
</c:if>

<c:if test="${type  eq 'countryuser'}">
<table class="formTable" style="width:100%" align="center" id="aa">
	<caption>
		<div class="header center">代维人员数&nbsp;</div>
	</caption>
	<tr>
	 <c:forEach items="${list}"  var="specialtylist" >
	 	<td class="label">
	   		${specialtylist.areaname }
		</td>
	 </c:forEach>
	 </tr>
	 <tr>
	 <c:forEach items="${list}"  var="countSpecialtylist" varStatus="i">
	 	<td>
	 		${countSpecialtylist.count }
		</td>
	 </c:forEach>
	 </tr>
</table>
</c:if>


<c:if test="${type  eq 'countrycal'}">
<table class="formTable" style="width:100%" align="center" id="aa">
	<caption>
		<div class="header center">代维车辆数&nbsp;</div>
	</caption>
	<tr>
	 <c:forEach items="${list}"  var="specialtylist" >
	 	<td class="label">
	   		${specialtylist.areaname }
		</td>
	 </c:forEach>
	 </tr>
	 <tr>
	 <c:forEach items="${list}"  var="countSpecialtylist" varStatus="i">
	 	<td>
	 		${countSpecialtylist.count }
		</td>
	 </c:forEach>
	 </tr>
</table>
</c:if>

<c:if test="${type  eq 'countryoil'}">
<table class="formTable" style="width:100%" align="center" id="aa"> 
	<caption>
		<div class="header center">代维油机数&nbsp;</div>
	</caption>
	<tr>
	 <c:forEach items="${list}"  var="specialtylist" >
	 	<td class="label">
	   		${specialtylist.areaname }
		</td>
	 </c:forEach>
	 </tr>
	 <tr>
	 <c:forEach items="${list}"  var="countSpecialtylist" varStatus="i">
	 	<td>
	 		${countSpecialtylist.count }
		</td>
	 </c:forEach>
	 </tr>
</table>
</c:if>

<input type="button" class="btn" onclick="javascript:window.history.back();" value="返回" />

</body>
<script type="text/javascript">
		//parent.removeblock();
		Ext.onReady(function(){
			/*if(parent.frames['arcGis-page'].map!=null){
			parent.frames['arcGis-page'].mapCenterAndZoom();
			}*/
				var cityRes=[];
				var cityResInformation=[];
			if(Ext.isIE){
					cityResInformation=document.getElementById("cityRes").innerText.split(' ');
						if(cityResInformation.length!=0){
							for(var i=0;i<cityResInformation.length;i++){
								var oi=cityResInformation[i].trim();
								if(oi!=""){
									cityRes.push(oi);
								}
							}
						}
			}
			else{
				cityResInformation=document.getElementById("cityRes").textContent.trim().split("\n");
					if(cityResInformation.length!=0){
						for(var i=0;i<cityResInformation.length;i++){
							var oi=cityResInformation[i].trim();
							if(oi!=""){
								cityRes.push(oi);
							}
						}
					}
			}
			if(parent.frames['arcGis-page'].map!=null){
				if(0!=cityRes.length){
					var a=[];
					parent.frames['arcGis-page'].cityAdapter(cityRes,a,true);
				}
			}
		
			parent.removeblock();
		});
		function clearNet(){
			document.getElementById("divContent").innerHTML = "";
		}
</script>



<%@ include file="/common/footer_eoms.jsp"%>