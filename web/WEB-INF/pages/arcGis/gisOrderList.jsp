<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" 	href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript"	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" 	src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript" src="${app}/arcGis/arcgis_js_v39_api/library/3.9/3.9compact/init.js"></script>

<style type="text/css">
	.trMouseOver {
		cursor:pointer;
	}
	tr.trMouseOver td {
		background-color:#CDE0FC;
	}
</style>
<%
String jsonList=request.getAttribute("jsonString").toString();
%>
<script type="text/javascript"><!--
var jq=jQuery.noConflict();

var imgpathPrefix ="http://localhost:8080/partner/";//需要改成图片服务器的地址
      var jsonList='<%=jsonList%>';
		jsonList = eval( '(' + jsonList + ')' );
	   window.parent.frames['arcGis-page'].dataJson=jsonList;	   
	   window.parent.frames['arcGis-page'].document.getElementById("dataJsonStr").click();  
	   
	   
Ext.onReady(function(){
	var pathUrl="${app}";
	
	jq("#gisOrderlist tbody tr").bind("mouseover",function(e) {
		jq(this).addClass("trMouseOver");
	});

	jq("#gisOrderlist tbody tr").bind("mouseout",function(e) {
		jq(this).removeClass("trMouseOver");
	});
	
});


require([        
        "dojo/ready",       
        "esri/geometry/Point",
        "esri/SpatialReference",
        "esri/graphic",
        "esri/symbols/SimpleMarkerSymbol",
        "esri/Color",
        "esri/dijit/PopupTemplate",
        "esri/InfoTemplate",
        "esri/geometry/webMercatorUtils",
        "dojo/on","dojo/query","dojo/request","dojo/dom-form",
        "dojo/dom-construct","dojo/dom-style"     
     
      ], function(
         ready, Point,SpatialReference,Graphic,SimpleMarkerSymbol,Color,PopupTemplate,InfoTemplate,webMercatorUtils,
         on,query,request,domForm,domConstruct,domStyle
      ) {
        ready(function() {   
		        
		        function pclick(){		       
			        var id= jq(this).find("input:hidden").val();
					var filename = jq(this).find("input[id^=datafile]").val();  //通过id模糊查找 id开头是datafile
					var username= jq(this).find("input[id^=datausername]").val();
					var phone = jq(this).find("input[id^=dataphone]").val();  
					var x = jq(this).find("input[id^=datax]").val();  
					var y = jq(this).find("input[id^=datay]").val(); 
			
					parent.addblock();
					var url="${app}/partner/arcGis/arcGis.do?method=gisOrderDetail";
					Ext.Ajax.request({  
					       url : url ,
						   method: 'POST',
						   params:{id:id,filename:filename,username:username,phone:phone,x:x,y:y},
							success: function (result, request) { 
							resjson = result.responseText;
							var json = eval( '(' + resjson + ')' ); //转换为json对象
							if(json!=null&&json.length!=0){
								 px = x;
								 py = y;
								
								 var wgs = new SpatialReference({
		             			 "wkid": 4326
		           				 });		          
				                var p = new Point(px, py,wgs);				                
							    window.parent.frames['arcGis-page'].map.infoWindow.hide();
							    window.parent.frames['arcGis-page'].map.graphics.clear(); 					 
							    window.parent.frames['arcGis-page'].map.infoWindow.show(p);
							     
		              		    var webMercator = webMercatorUtils.geographicToWebMercator(p);
							    var dataJhson ={"x":webMercator.x,"y":webMercator.y,"attributes":{"Longitude":x,"Latitude":y,"ResLevelId":json[0].resLevelId,"Link":imgpathPrefix+json[0].path}};
							    					    
							    window.parent.frames['arcGis-page'].singalPoint=dataJhson; 
							    window.parent.frames['arcGis-page'].document.getElementById("singalPoint").click();  
							     
							    window.parent.frames['arcGis-page'].map.centerAndZoom(p,10);
							
								//parent.frames['arcGis-page'].addGisOrder(json,pathUrl,false,true,true);
							}
							else{
								alert("该资源数据错误！");
							}
							parent.removeblock();
							 },
							 failure: function ( result, request) { 
								 parent.removeblock();
									 Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
								 } 
						 });
						 
						 
						  parent.removeblock();
		        
		        }  
		        
		     
		         on(query("#gisOrderlist tbody tr"),"click",pclick);
		        		        
		  });
		          
				  
});   
  

 parent.removeblock();

function lookimage(pathUrl,imagename){
	// alert(pathUrl);
	// alert(imagename);
  var objForm = document.applyForm;    
  var url= pathUrl+"/partner/arcGis/arcGis.do?method=showGisImage&imagename="+imagename;   //调用上面的java方法  
  var openStyle="dialogHeight:500px; dialogWidth:500px; status:no; help:no; scroll:auto";  
  var result = window.showModalDialog(url,window.document,openStyle);    
  return true;  
 }  

--></script>
<div style="border:1px solid #98c0f4;padding:5px;" id="titleInfo"> 
<span><b>事　前：<font color="#FF0000">红色标点</font></b></span><br>
<span><b>处理中：<font color="#d4dc08">黄色标点</font></b></span><br>
<span><b>抽　查：<font color="#3d00ac">蓝色标点</font></b></span><br>
</div>
<c:choose>
  <c:when test="${size != '0' }">
	<display:table name="gisOrderlist" cellspacing="0" cellpadding="0"	id="gisOrderlist" pagesize="${pageSize}" class="table" export="false"
				requestURI="${app}/partner/arcGis/arcGis.do?method=gisOrder" sort="list" partialList="true" size="${resultSize}">
		<display:column  sortable="true" headerClass="sortable" title="序号">${gisOrderlist_rowNum }	</display:column>
		<display:column  sortable="true" headerClass="sortable" title="图片">
			<img id='img' src='http://localhost:8080/partner/${gisOrderlist.IMAGE_FILE_NAME}' style='cursor:pointer'  width='20px' heigth='20px'/> 
		</display:column>
		<display:column  sortable="true" headerClass="sortable" title="经度">${gisOrderlist.x}
			<input type="hidden" id="datafile_${gisOrderlist_rowNum }"  value="${gisOrderlist.IMAGE_FILE_NAME}" />
			<input type="hidden" id="datax_${gisOrderlist_rowNum }"  value="${gisOrderlist.x}" />
		</display:column>	
		<display:column  sortable="true" headerClass="sortable" title="纬度">${gisOrderlist.y}
			<input type="hidden" id="datay_${gisOrderlist_rowNum }"  value="${gisOrderlist.y}" />
		</display:column>	
	</display:table>
	</c:when>
  <c:otherwise>
  <div>
  　
    <h3>此工单无记录。</h3>
    </div>
  </c:otherwise>
</c:choose>

<br><br>

<%@ include file="/common/footer_eoms.jsp"%>