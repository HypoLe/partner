<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%response.setHeader("cache-control","public"); %>
<script language="javaScript" type="text/javascript"
	src="${app}/scripts/module/partner/ajax.js"></script>
<%@ page language="java"
	import="java.util.*,com.boco.eoms.partner.baseinfo.webapp.form.*;"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'PnrResConfigList.jsp' starting page</title>
    
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
  <script type="text/javascript">
  var jq=jQuery.noConflict();
  Ext.onReady(function(){
  		delAllOption("country");
		var city = document.getElementById("city").value;
		var url="<%=request.getContextPath()%>/partner/serviceArea/lines.do?method=changeCity&city="+city;
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
				var countyName = "country";
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
			},
			failure: function ( result, request) { 
				Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
			} 
		});
		
  });
  
  var checkflag = "false";
	function chooseAll(){	
	   var objs = document.getElementsByName("checkbox11");    
	    if(checkflag=="false"){
	        for(var i=0; i<objs.length; i++){
	           objs[i].checked="checked";
	        } 
	        checkflag="checked";
	    }
	    else if(checkflag=="checked"){ 	    	    
		    for(var i=0; i<objs.length; i++){
		           objs[i].checked=false;
		    } 
		    checkflag="false";
	    }
	}
	
	function delselcar(){
var string="";
 var objName= document.getElementsByName("checkbox11");
        for (var i = 0; i<objName.length; i++){
                if (objName[i].checked==true){ 
                string+=objName[i].value.trim();   
                string+="|";
                }
        }  
        if(confirm("确认要删除吗？")){
        	if(string == null || "" ==  string){
        		alert("请选择要删除的巡检资源");
        		return false;
        	}
		 	location.href="${app}/partner/res/PnrResConfig.do?method=remove&&seldelcar="+string;
		 }else{
		 return false;
		 }
}
  
 function delAllOption(elementid){
         var ddlObj = document.getElementById(elementid);//获取对象
          for(var i=ddlObj.length-1;i>=0;i--){
              ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
         }	
         
     }
	
//地区、区县连动
function changeCity(con)
		{    
		     delAllOption("country");//地市选择更新后，重新刷新县区
				var city = document.getElementById("city").value;
				var url="<%=request.getContextPath()%>/partner/serviceArea/lines.do?method=changeCity&city="+city;
				Ext.Ajax.request({
					url : url,
					method: 'POST',
					success: function ( result, request ) { 
						res = result.responseText;
						if(res.indexOf("<\/SCRIPT>")>0){
					  		res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
						}
						var json = eval(res);
						var countyName = "country";
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
					},
					failure: function ( result, request) { 
						Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
					} 
				});
		}	

	function openImport(handler){
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

function relation(id){
	 var _sHeight = 300;
     var _sWidth = 480;
     var sTop=(window.screen.availHeight-_sHeight)/2;
     var sLeft=(window.screen.availWidth-_sWidth)/2;
     var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
    var url ="${app}/partner/res/pnrTransLineAction.do?method=gotoTransPointRate&id="+id+"&all=false";
    var pro= window.showModalDialog(url,window,sFeatures); 
    if(pro==null){
    	return;
    }
    if(pro == true){
    	 Ext.Msg.alert('提示','设置成功',function(){
				  window.location='${app }/partner/res/pnrTransLineAction.do?method=gotoTransLineList';
		}); 
    	//window.location.href=window.location.href;
    	
    }else{
    	 Ext.Msg.alert('提示','设置失败'); 
    }
}
 
 function setMustArrive(){
 	 var _sHeight = 300;
     var _sWidth = 480;
     var sTop=(window.screen.availHeight-_sHeight)/2;
     var sLeft=(window.screen.availWidth-_sWidth)/2;
     var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
    var url ="${app}/partner/res/pnrTransLineAction.do?method=gotoTransPointRate&id="+id+"&all=true";
    var pro= window.showModalDialog(url,window,sFeatures); 
    if(pro==null){
    	return;
    }
    var string="";
	var objName= document.getElementsByName("checkbox11");
        for (var i = 0; i<objName.length; i++){
                if (objName[i].checked==true){ 
                string+="'"+objName[i].value.trim()+"',";   
                //string+=",";
                }
        }  
    	
    	Ext.Ajax.request({
		    url: '${app }/partner/res/pnrTransLineAction.do?method=updateAllTransPointRate',
		    params:{ids:string,rate:pro[0],cycle:pro[1],inspectMethod:pro[2]},
		    success: function(response){
		   		 var jsonResult = Ext.util.JSON.decode(response.responseText);
		    	 if (jsonResult[0].success=='true'){
				    	 Ext.Msg.alert('提示','设置成功',function(){
				    	 	//window.location.href=window.location.href;
				    	 	window.location='${app }/partner/res/pnrTransLineAction.do?method=gotoTransLineList';
				    	 }); 
      			 }
		    	 if (jsonResult[0].success=='false'){
		    	 	Ext.Msg.alert('提示','设置失败'); 
      			 }
      			
		    },
	     	failure: function(response) {
	     			Ext.get(document.body).unmask();
                    Ext.Msg.alert('提示','设置失败'); 
            }
			});	
 
 }
 
 function resetMsg(){
	jq(".text").val('');
	jq(".select").val('');
	changeCity('')
}
 
  </script>
  
  <body>
  <div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">查询</span>
</div>

<div id="listQueryObject" 
	 style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
  <form action="${app}/partner/res/pnrTransLineAction.do?method=gotoTransLineList" method="post" >
   <table class="listTable">
			<tr>
					<td class="label">资源名称</td>
					<td class="content">
						<html:text property="resName" styleId="car_number"
							styleClass="text medium" alt="allowBlank:false,vtext:''"
							value="${pnrResConfigForm.resName}" />
					</td>
					
					<td class="label">区域</td>
					<td class="content">
						<html:text property="tlDis" styleId="car_number"
							styleClass="text medium" alt="allowBlank:false,vtext:''"
							value="${pnrResConfigForm.tlDis}" />
					</td>
					<%-- 
					<td class="label">地市</td>
					<td class="content">
					<select name="city" id="city" class="select" alt="allowBlank:false,vtext:'请选择所在地市'" onchange="changeCity(0);">
					<option value="">
						--请选择所在地市--
					</option>
					<logic:iterate id="city" name="city">
						<c:if test="${pnrResConfigForm.city==city.areaid}" var="result">
							<option value="${city.areaid}" selected="selected" >
								${city.areaname}
							</option>
						</c:if>
						<c:if test="${!result}">
							<option value="${city.areaid}" >
								${city.areaname}
							</option>
						</c:if>
					</logic:iterate>
				</select>
					</td>--%>
				</tr>

				 <tr>
					<td class="label">地市</td>
					<td class="content">
					<select name="city" id="city" class="select" alt="allowBlank:false,vtext:'请选择所在地市'" onchange="changeCity(0);">
					<option value="">
						--请选择所在地市--
					</option>
					<logic:iterate id="city" name="city">
						<c:if test="${pnrResConfigForm.city==city.areaid}" var="result">
							<option value="${city.areaid}" selected="selected" >
								${city.areaname}
							</option>
						</c:if>
						<c:if test="${!result}">
							<option value="${city.areaid}" >
								${city.areaname}
							</option>
						</c:if>
					</logic:iterate>
				</select>
					</td>
					<td class="label">区县</td>
					<td class="content">
						<select name="country" id="country" class="select"
							alt="allowBlank:false,vtext:'请选择所在县区'">
							<option value="">
								--请选择所在县区--
							</option>				
						</select>
					</td>
				</tr>
				<tr>
					<td class="label">光缆系统</td>
					<td class="content">
						<html:text property="tlWire" styleId="car_number"
							styleClass="text medium" alt="allowBlank:false,vtext:''"
							value="${pnrResConfigForm.tlWire}" />
					</td>
					<td class="label">光缆段</td>
					<td class="content">
						<html:text property="tlSeg" styleId="car_number"
							styleClass="text medium" alt="allowBlank:false,vtext:''"
							value="${pnrResConfigForm.tlSeg}" />
					</td>
				</tr>
		</table> 
		<table>
		    <tr>
			    <td>
			    	<input type="submit" class="btn" value="<fmt:message key="button.query"/>" />&nbsp;&nbsp;
			    	<input type="button" value="重置" class="btn" onclick="resetMsg();">
				</td>
			</tr>
		</table>	
	</form>	
	</div>
	
	<br/>
	<input type="button" class="btn" value="批量设置到点率" onclick="setMustArrive();" />   
	<html:form action="PnrResConfig" method="post" >
			    
	<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" pagesize="${pageSize}" class="table list" 
		export="false" 
		requestURI="${app}/partner/res/pnrTransLineAction.do"
		sort="list" partialList="true" size="${resultSize}">
	
		<display:column  title="<input type='checkbox' onclick='javascript:chooseAll();'>" >
        	<input type="checkbox" name="checkbox11" id="checkbox11"  value="${list.id} "/>
    	</display:column>
	
		<display:column property="resName" sortable="true"  title="资源名称"
				headerClass="sortable"  paramId="id" paramProperty="id"/>
	
				
		<display:column  sortable="true"  title="所在地市"
				headerClass="sortable" >
				<eoms:id2nameDB id="${list.city}" beanId="tawSystemAreaDao" />
		</display:column>
				
		<display:column  sortable="区县" title="所在区县"
				headerClass="sortable"  >
			<eoms:id2nameDB id="${list.country}" beanId="tawSystemAreaDao" />
		</display:column>
	
		
		<display:column  sortable="true"  title="区域"
				headerClass="sortable" >
				${list.tlDis }
		</display:column>
		
		<display:column  sortable="true"  title="光缆系统"
				headerClass="sortable" >
				${list.tlWire }
		</display:column>
		
		<display:column  sortable="true"  title="光缆段"
				headerClass="sortable" >
				${list.tlSeg }
		</display:column>
				
		<display:column  sortable="区县" title="系统级别"
				headerClass="sortable"  >
			${list.tlSystemLevel }
		</display:column>
		
		<display:column  sortable="区县" title="起点名字"
				headerClass="sortable"  >
			${list.tlPAName }
		</display:column>
				
		<display:column  sortable="区县" title="起点经度"
				headerClass="sortable"  >
			${list.tlPALo }
		</display:column>
				
		<display:column  sortable="" title="起点点纬度"
				headerClass="tlPALa"  >
			${list.tlPALa }
		</display:column>
				
		<display:column  sortable="" title="终点名字"
				headerClass="sortable"  >
			${list.tlPZName }
		</display:column>
		
		<display:column  sortable="" title="终点经度"
				headerClass="sortable"  >
			${list.tlPZLo }
		</display:column>
				
		<display:column  sortable="" title="终点纬度"
				headerClass="sortable"  >
			${list.tlPZLa }
		</display:column>
		
		<display:column  sortable="true"  title="标准到点率"
				headerClass="sortable"  >
			<c:choose>
				<c:when test="${empty list.tlArrivedRate}">
					<font color="red">待设置</font>
				</c:when>
				<c:otherwise>
					${list.tlArrivedRate }%
				</c:otherwise>
			</c:choose>
		</display:column>
		<%-- 
		<display:column  sortable="true"  title="巡检上报频率"
				headerClass="sortable"  >
			<c:choose>
				<c:when test="${empty list.tlReportInterval}">
					<font color="red">待设置</font>
				</c:when>
				<c:otherwise>
					${list.tlReportInterval }秒
				</c:otherwise>
			</c:choose>
		</display:column>
		
		<display:column  sortable="true"  title="巡检方式"
				headerClass="sortable"  >
			<c:choose>
				<c:when test="${empty list.tlExecuteType}">
					<font color="red">待设置</font>
				</c:when>
				<c:otherwise>
					<eoms:id2nameDB id="${list.tlExecuteType}" beanId="ItawSystemDictTypeDao" />
				</c:otherwise>
			</c:choose>
		</display:column>
		--%>
		<display:column  sortable="true"  title="是否关联敷设点"
				headerClass="sortable"  >
				<% int f = (int)(((com.boco.eoms.partner.res.model.PnrResConfig)list).getExecuteRecord()) & com.boco.eoms.partner.res.util.PRCERUtil.TLP2TL;if(f==com.boco.eoms.partner.res.util.PRCERUtil.TLP2TL) out.print("是"); else out.print("<font color=\"red\">否</font>"); %>
		</display:column>
		<%--
		<display:column  sortable="true"  title="是否具有必到敷设点"
				headerClass="sortable"  >
				<% int a = (int)(((com.boco.eoms.partner.res.model.PnrResConfig)list).getExecuteRecord()) & com.boco.eoms.partner.res.util.PRCERUtil.TLP_MUST_ARRIVED_SET;if(a==com.boco.eoms.partner.res.util.PRCERUtil.TLP_MUST_ARRIVED_SET) out.print("是"); else out.print("<font color=\"red\">否</font>"); %>
		</display:column>
		 --%>		
		<display:column sortable="true" headerClass="sortable" title="管理敷设点">
	        <a href="${app}/partner/res/pnrTransLineAction.do?method=gotoTransLinePointList&id=${list.id}">
				<img src="${app}/images/icons/add.png" />
			</a>
    	</display:column>	
    	
    	<display:column  sortable="到点率设置" title="到点率设置"
				headerClass="sortable"  >
			<a href="javascript:relation('${list.id}')">
				<img src="${app}/images/icons/edit.gif" />
			</a>
		</display:column>	
		
	<display:setProperty name="export.rtf" value="false" />
	<display:setProperty name="export.pdf" value="false" />
	<display:setProperty name="export.xml" value="false" />
	<display:setProperty name="export.csv" value="false" />			
	</display:table>
	<br>
	</html:form>
	<br/>
	
  </div>
	
  </body>
</html>
