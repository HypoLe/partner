<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ include file="/common/extlibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script language="javaScript" type="text/javascript"
	src="${app}/scripts/module/partner/ajax.js"></script>
<%@ page language="java"
	import="java.util.*,com.boco.eoms.partner.baseinfo.webapp.form.*;"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

//String jsonList=request.getAttribute("jsonString").toString();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
<link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript" src="${app}/arcGis/js/arcGisHelp.js"></script>
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
  <script type="text/javascript">
     var jq=jQuery.noConflict();  
    
       var jsonList='<%=jsonList%>';
		jsonList = eval( '(' + jsonList + ')' );
	   window.parent.frames['arcGis-page'].dataJson=jsonList;	   
	   window.parent.frames['arcGis-page'].document.getElementById("dataJsonStr").click();  
	   window.parent.frames['arcGis-page'].map.setZoom(8);
	   
    Ext.onReady(function(){
	  
			
  		//初始的时候给区县默认值
		delAllOption("city");//地市选择更新后，重新刷新县区
			var region = document.getElementById("region").value;
			var url="${app}/partner/baseinfo/linkage.do?method=changeCity&city="+region;
			//var result=getResponseText(url);
			Ext.Ajax.request({
			url : url ,
			method: 'POST',
			success: function ( result, request ) { 
				res = result.responseText;
				if(res.indexOf("[{")!=0){
							res = "[{"+result.responseText;
				}
				if(res.indexOf("<\/SCRIPT>")>0){
			  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
				}
				
				var json = eval(res);
				
				var countyName = "city";
				var arrOptions = json[0].cb.split(",");
				var obj=$(countyName);
				var i=0;
				var j=0;
				for(i=0;i<arrOptions.length;i++){
					var opt=new Option(arrOptions[i+1],arrOptions[i]);
					obj.options[j]=opt;
					var country = "${pnrResConfigForm.country}";
					if(arrOptions[i]==country){
					obj.options[j].selected=true;
					}
					j++;
					i++;
				}
				
							
					var city = '${gridForm.city}';
					var partnerid = '${gridForm.partnerid}';
					if(city!=''){
						document.getElementById("city").value = city;
					}
					if(partnerid!=''){
						changePartner(1);								
                                }	
											
			},
			failure: function ( result, request) { 
				Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
			} 
		});//初始的时候给区县默认值结束
		
		 //--------------------------end
        jq("#list tbody tr").bind("mouseover",function(e) {
			jq(this).addClass("trMouseOver");
		});

		jq("#list tbody tr").bind("mouseout",function(e) {
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
		           
		          var id = jq(this).find("input:hidden").val();
			      var other=[];
			//parent.addblock();
			var pathUrl="${app}";
			var type="selected";
			var px="116.7028360958850";
			var py="36.5658468311636";
			var url="${app}/partner/arcGis/arcGis.do?method=siteSelected";
			Ext.Ajax.request({  
			       url : url ,
				   method: 'POST',
				   params:{id:id},
					success: function (result, request) { 
					var resjson = result.responseText;
					var json = eval( '(' + resjson + ')' ); //转换为json对象 
					if(json!=null&&json.length!=0){
					var specialty=json[0].specialty;
					if("传输线路"!=specialty){
						 px = json[0].resLongitude;
						 py = json[0].resLatitude;
						
						 var wgs = new SpatialReference({
             			 "wkid": 4326
           				 });		          
		                var p = new Point(px, py,wgs);		    
		              //  window.parent.frames['arcGis-page'].map.centerAndZoom(p,10);
		                
					    window.parent.frames['arcGis-page'].map.infoWindow.hide();
					    window.parent.frames['arcGis-page'].map.graphics.clear(); 					 
					    window.parent.frames['arcGis-page'].map.infoWindow.show(p);
					     
              		    var webMercator = webMercatorUtils.geographicToWebMercator(p);
					    var dataJhson ={"x":webMercator.x,"y":webMercator.y,"attributes":{"Speciality":json[0].specialty,"ResType":json[0].resType,"ResLevel":json[0].resLevel,"ResLevelId":json[0].resLevelId,"CityName":json[0].city,"CountryName":json[0].country,"Name":json[0].resName,"Link":"/partner"+json[0].resUrl}};
					    					    
					    window.parent.frames['arcGis-page'].singalPoint=dataJhson; 
					    window.parent.frames['arcGis-page'].document.getElementById("singalPoint").click();  
					     
					    window.parent.frames['arcGis-page'].map.centerAndZoom(p,10);
						//parent.frames['arcGis-page'].addMarker(json,pathUrl,type,false,other,true,true);
					}
					//else{
						//parent.frames['arcGis-page'].addTransLine(json,pathUrl,false,false);
					//}
					}
					else{
						alert("该资源数据错误！");
						parent.removeblock();
					}
					parent.removeblock();
					 },
					 failure: function ( result, request) { 
							 Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
							 parent.removeblock();
						 } 
				 });
		        
		        }  
		     
		         on(query("#list tbody tr"),"click",pclick);
		        
		         query("input[type='submit']").on("click", function(e){
		        		
					parent.addblock();
				//	window.parent.frames['arcGis-page'].location.href = '${app}/partner/arcGis/arcGis.do?method=goToArcGisMap';
				//	location.href = '${app}/partner/arcGis/arcGis.do?method=search';
										
				});
				
				 query("#pagesize").on("change", function(e){
				//	parent.addblock();
					changePage();
					
				//	window.parent.frames['arcGis-page'].location.href = '${app}/partner/arcGis/arcGis.do?method=goToArcGisMap';
				//	location.href = '${app}/partner/arcGis/arcGis.do?method=search';
				});
				
				 query(".pagelinks a").on("click", function(e){
					parent.addblock();
				//	console.log(e);
				//	 location.href = 'http://localhost:8080/partner/partner/arcGis/arcGis.do?method=search&type=site&d-49520-p=72';
					// window.parent.frames['arcGis-page'].dataJson=jsonList;
					// window.parent.frames['arcGis-page'].location.href = '${app}/partner/arcGis/arcGis.do?method=goToArcGisMap';
	
				});
				
				function changePage() {
						parent.addblock();
						
						var obj = document.getElementById("pagesize"); //定位id

						var index = obj.selectedIndex; // 选中索引
						
						var text = obj.options[index].text; // 选中文本
						
						var value = obj.options[index].value; // 选中值
						
						location.href = '${app}/partner/arcGis/arcGis.do?method=search&pagesize='+value;
		
					}
		        
		  });
		          
		       
				  
      	 });   
  
 parent.removeblock();
  
 function delAllOption(elementid){
         var ddlObj = document.getElementById(elementid);//获取对象
          for(var i=ddlObj.length-1;i>=0;i--){
              ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
         }	
         
     }
	
//地区、区县连动
function changeCity(con)
		{    
		    delAllOption("city");//地市选择更新后，重新刷新县区
			var region = document.getElementById("region").value;
			var url="${app}/partner/baseinfo/linkage.do?method=changeCity&city="+region;
			//var result=getResponseText(url);
			Ext.Ajax.request({
				url : url ,
				method: 'POST',
				success: function ( result, request ) { 
					res = result.responseText;
					if(res.indexOf("[{")!=0){
								res = "[{"+result.responseText;
					}
					if(res.indexOf("<\/SCRIPT>")>0){
				  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
					}
					
					var json = eval(res);
					
					var countyName = "city";
					var arrOptions = json[0].cb.split(",");
					var obj=$(countyName);
					var i=0;
					var j=0;
					for(i=0;i<arrOptions.length;i++){
						var opt=new Option(arrOptions[i+1],arrOptions[i]);
						obj.options[j]=opt;
						j++;
						i++;
					}
					
					if(con==1){				
						var city = '${gridForm.city}';
						var partnerid = '${gridForm.partnerid}';
						if(city!=''){
							document.getElementById("city").value = city;
						}
						if(partnerid!=''){
							changePartner(1);								
		
		 
		                               }	
					}							
				},
				failure: function ( result, request) { 
					Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
				} 
			});
		}	



//导出界面的区县连动
 function changeCity1(con)
		{    
		  delAllOption("city1");//地市选择更新后，重新刷新县区
var region1 = document.getElementById("region1").value;
var url="${app}/partner/baseinfo/linkage.do?method=changeCity&city="+region1;
//var result=getResponseText(url);
Ext.Ajax.request({
					url : url ,
					method: 'POST',
					success: function ( result, request ) { 
						res = result.responseText;
						if(res.indexOf("[{")!=0){
 								res = "[{"+result.responseText;
						}
						if(res.indexOf("<\/SCRIPT>")>0){
					  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
						}
						
						var json = eval(res);
						
						var countyName = "city1";
						var arrOptions = json[0].cb.split(",");
						var obj=$(countyName);
						var i=0;
						var j=0;
						for(i=0;i<arrOptions.length;i++){
							var opt=new Option(arrOptions[i+1],arrOptions[i]);
							obj.options[j]=opt;
							j++;
							i++;
						}
						
						if(con==1){			
							var city = '${gridForm.city1}';
							var partnerid = '${gridForm.partnerid1}';
							if(city!=''){
								document.getElementById("city1").value = city;
							}
							if(partnerid!=''){
								changePartner(1);								

    
                                  }	
						}							
					},
					failure: function ( result, request) { 
						Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
					} 
				});
		}

 
function excelExport(){
	var specialy = jq("#exportType").val();
	if(specialy==''){
		alert('请选择巡检专业');
		return;
	}

	new Ext.form.BasicForm('exportForm').submit({
	method : 'post',
		url : "${app}/partner/res/PnrAllRes.do?method=excelExport",
	    success : function(form, action) {
			alert(action.result.infor);
			jq("#importFile").val("");
		},
		failure : function(form, action) {
			alert(action.result.infor);
			jq("#importFile").val("");
		}
    });
} 
  
  
function openImport(){
	var handler = document.getElementById("openQuery");
	var el = Ext.get('listQueryObject');
	if(el.isVisible()){
		el.slideOut('t',{useDisplay:true});
		handler.innerHTML = "打开查询界面";
	}
	else{
		el.slideIn();
		handler.innerHTML = "关闭查询界面";
	}
}

//重置查询
function clearText(){
	jq("#car_number")[0].value="";
	jq("#zhuanye")[0].value="";
	jq("#resourceTeype")[0].value="";
	jq("#resourceLevel")[0].value="";
	jq("#region")[0].value="";
	jq("#city")[0].value="";
}

</script>
  
  <body>
<div style="border:1px solid #98c0f4;padding:5px;" id="titleInfo"> 
<span>基站：红色-VIP基站;蓝色-A类;绿色-B类;黄色-C类.</span><br>
<span>接入网：红色-A类;蓝色-B类;绿色-C类.</span><br>
<span>铁塔及天馈：红色-月标准;蓝色-季度.</span><br>
</div>
<div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">关闭查询界面</span>
</div>
<div id="listQueryObject" style="border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff">
  
  <form action="${app}/partner/arcGis/arcGis.do?method=search" method="post" >
  <input type="hidden" name="pagesize" value="${pageSize}">
   <table class="formTable">
	<caption>
		<div class="header center">巡检资源信息</div>
	</caption>
			<tr>
					<td class="label">资源名称</td>
					<td class="content" >
						<html:text property="resName" styleId="car_number" 
							styleClass="text medium" alt="allowBlank:false,vtext:''"
							value="${pnrResConfigForm.resName}" />
					</td>
					<td class="label">巡检专业</td>
					<td class="content">
						<eoms:comboBox name="specialty" id="zhuanye"
							sub="resourceTeype" defaultValue="${pnrResConfigForm.specialty}" initDicId="11225" 
							alt="allowBlank:false" styleClass="select" hiddenOption="1122502" />
					</td>
				</tr>

				<tr>
					<%--<td class="label">资源类别</td>
					<td class="content">
						<eoms:comboBox name="resType" defaultValue="${pnrResConfigForm.resType}"
							id="resourceTeype" initDicId="<%=attr%>" alt="allowBlank:false"
							sub="resourceLevel" styleClass="select" />
					</td>
					--%>
					<td class="label">资源类别</td>
					<td class="content">
						<eoms:comboBox name="resType" defaultValue="${pnrResConfigForm.resType}"
							id="resourceTeype" initDicId="${pnrResConfigForm.specialty}" alt="allowBlank:false"
							sub="resourceLevel" styleClass="select" />
					</td>
					<td class="label">资源级别</td>
					<td class="content">
						<eoms:comboBox name="resLevel" defaultValue="${pnrResConfigForm.resLevel}"
							id="resourceLevel" initDicId="${pnrResConfigForm.resType}" alt="allowBlank:false" styleClass="select" />
					</td>
				</tr>
				
				<tr>
					<td class="label">地市</td>
					<td class="content">
						<select name="region"  id="region" class="select"
							alt="allowBlank:false,vtext:'请选择所在地市'"
							onchange="changeCity(0);">
							<option value="">
								--请选择所在地市--
							</option>
							<logic:iterate id="city" name="city">
							<c:if test="${pnrResConfigForm.city==city.areaid}">
								<option value="${city.areaid}" selected="selected" >
									${city.areaname}
								</option>
							</c:if>
							<option value="${city.areaid}">
								${city.areaname}
							</option>
							</logic:iterate>
						</select>
					</td>
					<td class="label">区县</td>
					<td class="content">
						<select name="city" id="city" class="select"
							alt="allowBlank:false,vtext:'请选择所在县区'">
							<option value="">
							--请选择所在县区--
							</option>				
						</select>
					</td>
				</tr>
				<center>
	
			<tr>
				<td class="label">
				条数
				</td>
				<td colspan="3">
				设置每页显示条数
					<select id="pagesize" name="pagesize" style="width: 100%"  >
											<option value="15" ${pageSize == "15" ? 'selected="selected"':'' } >15</option>
											<option value="40" ${pageSize == "40" ? 'selected="selected"':'' } >40</option>
											<option value="80" ${pageSize == "80" ? 'selected="selected"':'' } >80</option>
											<option value="160" ${pageSize == "160" ? 'selected="selected"':'' } >160</option>
											<option value="320" ${pageSize == "320" ? 'selected="selected"':'' } >320</option>
					</select>
				</td>
			</tr>
		
				
		</table> 
				<input type="submit" name="submit" value="查询">
					&nbsp;&nbsp;
					<input type="button"  id="reset" onclick="clearText();" value="重置">
	</form>	
	</div>	
		    
	<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" pagesize="${pageSize}" class="table list" 
		export="false" 
		requestURI="${app}/partner/arcGis/arcGis.do"
		sort="list" partialList="true" size="${resultSize}">
	
		<display:column media="html" sortable="false"  headerClass="sortable" title="资源名称">
			${list.resName}
			<input type="hidden" id="dataId_${list_rowNum }" value="${list.id}"/>
		</display:column>
				
		<display:column  sortable="true"  title="巡检专业"
				headerClass="sortable"  paramId="id" paramProperty="id">
				<eoms:id2nameDB id="${list.specialty}" beanId="ItawSystemDictTypeDao" />
		</display:column>					
	
		<display:column  sortable="true" title="资源类别" 
				headerClass="sortable"  >
				<eoms:id2nameDB id="${list.resType}" beanId="ItawSystemDictTypeDao" />
		</display:column>		
	
		<display:column  sortable="true" title="资源级别" 
				headerClass="sortable">
				<eoms:id2nameDB id="${list.resLevel}" beanId="ItawSystemDictTypeDao" />
		</display:column>		
				
		<display:column  sortable="true"  title="所在地市"
				headerClass="sortable" >
				<eoms:id2nameDB id="${list.city}" beanId="tawSystemAreaDao" />
		</display:column>
				
		<display:column  sortable="区县" title="所在区县"
				headerClass="sortable"  >
			<eoms:id2nameDB id="${list.country}" beanId="tawSystemAreaDao" />
		</display:column>
		
		 <%--<display:column sortable="true" headerClass="sortable" title="详情">
	        <a href="${app}/partner/res/PnrResConfig.do?method=detial&&seldelcar=${list.id}" title="详情">
				<img src="${app}/images/icons/search.gif" />
			</a>
    	</display:column>
			
		<!-- 此处添加权限控制 -->
    <display:column sortable="true" headerClass="sortable" title="编辑">
       <c:if test="${sessionform.userid eq list.creator }">
       
        <a href="${app}/partner/res/PnrResConfig.do?method=edit&&seldelcar=${list.id}" title="编辑">
        	<img src="${app}/images/icons/edit.gif" />
        </a>
       </c:if>
       
    </display:column>
            
    <display:column sortable="true" headerClass="sortable" title="删除">
    
    	<c:if test="${sessionform.userid eq list.creator }">
       
	        <a href="javascript:if(confirm('您确定要删除您选择的数据？')) 
	        {window.location.href = '${app}/partner/res/PnrResConfig.do?method=remove&&seldelcar=${list.id}';}" title="删除"/>
	        	<img src="${app}/images/icons/icon.gif" />
	        </a>
        </c:if>
    
	</display:column>
	
	<display:setProperty name="export.rtf" value="false" />
	<display:setProperty name="export.pdf" value="false" />
	<display:setProperty name="export.xml" value="false" />
	<display:setProperty name="export.csv" value="false" />	
	--%>		
	</display:table>
	
 
	
  </body>
</html>
