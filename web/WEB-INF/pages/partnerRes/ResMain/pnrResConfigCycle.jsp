<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
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

		<title>My JSP 'pnrResConfigAssign.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  <link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>

	</head>
	<script type="text/javascript">
	var jq=jQuery.noConflict();
	var condition =" where 1=1 ";
	Ext.onReady(function(){
	if("${assign}"=="yes"){
		jq("#yes").attr("selected","selected");
	}else if("${assign}"=="no"){
		jq("#no").attr("selected","selected");
	}else{
		jq("#all").attr("selected","selected");
	}
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
		
		if("${pnrResConfigForm.resName}"!=""){
			condition = condition+ " and resName='"+"${pnrResConfigForm.resName}"+"'";
		}
		if("${pnrResConfigForm.specialty}"!=""){
			condition = condition+" and specialty='"+"${pnrResConfigForm.specialty}"+"'";
		}
		if("${pnrResConfigForm.resType}"!=""){
			condition = condition+" and resType='"+"${pnrResConfigForm.resType}"+"'"
		}
		if("${pnrResConfigForm.resLevel}"!=""){
			condition = condition+" and resLevel='"+"${pnrResConfigForm.resLevel}"+"'"
		}
		if("${pnrResConfigForm.city}"!=""){
			condition = condition+" and city='"+"${pnrResConfigForm.city}"+"'";
		}
		if("${pnrResConfigForm.country}"!=""){
			condition = condition+" and country='"+"${pnrResConfigForm.country}"+"'";
		}
		
});

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
	
	function fenpei(){
		var str  = jq("#selectType").val();
		if("未分配"==str){
			jq("#assign").val("no");
		}else if("已分配"==str){
			jq("#assign").val("yes");
		}else{
			jq("#assign").val("all");
		}
	}

	function relation1(){
		 var string="";
		 var objName= document.getElementsByName("checkbox11");
		        for (var i = 0; i<objName.length; i++){
		                if (objName[i].checked==true){ 
		                string+=objName[i].value;   
		                string+="|";
		                }
		        } 
		        if(string==""){
		        	alert("请选择资源！");
		        	return ;
		        } 
	//	window.open("${app}/partner/res/PnrResConfig.do?method=window&&seldelcar="+string,"eduWin","height=530,width=480,top=100,left=400,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no");
	   /* var oForm = document.createElement("form");   
	    var url ="${app}/partner/res/PnrResConfig.do?method=cycleWindow";
	        //oForm.id="testid";   
	    oForm.method="post";   
	    oForm.action=url;   
	    oForm.target="preview_page";
	    
	    var hasitemsids_input = document.createElement("input");
	    hasitemsids_input.type="hidden";   
	    hasitemsids_input.name="seldelcar";   
	    hasitemsids_input.value=string;
	    oForm.appendChild(hasitemsids_input);
	    
	    
	    document.body.appendChild(oForm); 
	    var popupWin = window.open('',   'preview_page',   'height=250, width=480, toolbar=no ,top=180,left=400, menubar=no, scrollbars=no, resizable=no, location=no, status=no');   
	    oForm.submit();*/
	    
	    
	    var _sHeight = 280;
        var _sWidth = 480;
        var sTop=(window.screen.availHeight-_sHeight)/2;
        var sLeft=(window.screen.availWidth-_sWidth)/2;
        var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
    
	    var url ="${app}/partner/res/PnrResConfig.do?method=cycleWindow";
	    //var popupWin = window.open('',   'preview_page',   'height=530, width=480, toolbar=no ,top=100,left=400, menubar=no, scrollbars=no, resizable=no, location=no, status=no');   
	    //var pro=window.showModalDialog(url,window,'dialogHeight:230px,dialogWidth:260px,center:yes,100px,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no');
	    var pro =  window.showModalDialog(url,window,sFeatures);
	    if(pro==null){
	    	return;
	    }
	    Ext.Ajax.request({
		method:"post",
		url: "${app}/partner/res/PnrResConfig.do?method=updateCycle",
		params:{
			seldelcar:string,
			prov:pro
		},
		success: function(x){
			//刷新父页面
			alert("周期分配成功");	
			window.location.href=window.location.href;
		}
	 })
	    
	}

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
	
	function cancleCycle(){
		var string="";
		 var objName= document.getElementsByName("checkbox11");
		        for (var i = 0; i<objName.length; i++){
		                if (objName[i].checked==true){ 
		                string+=objName[i].value;   
		                string+="|";
		                var obj = document.getElementById(objName[i].value.trim()).value;
		                if(obj==''){
		                	alert("所选项部分未非配周期，不能取消！");
		                	return;
		                }
		                }
		        } 
		        if(string==""){
		        	alert("请选择资源！");
		        	return ;
		        } 
		         if(confirm("确定取消关联所选项？")==1){
					 Ext.Ajax.request({
			    	method:"post",
					url: "${app}/partner/res/PnrResConfig.do?method=updateCycle",
					params:{
						seldelcar:string,
						prov:''
					},
					success: function(x){
						//刷新父页面
						//document.URL=location.href;
						alert("取消成功");	
						//window.close();
						//window.location.reload();
						window.location.href=window.location.href;
					}
			    
			    });
				}
	}
	

	</script>

	<body>
		<html:form action="PnrResConfig.do?method=cycleCheck" styleId="myform" method="post">
			<input type="hidden" name="assign"  value="${assign}" id="assign" />
			<table class="formTable">
				<caption>
					<div class="header center">
						巡检周期分配
					</div>
				</caption>
				<tr>
					<td class="label">资源名称</td>
					<td class="content">
						<html:text property="resName" styleId="car_number"
							styleClass="text medium" alt="allowBlank:false,vtext:''"
							value="${pnrResConfigForm.resName}" />
					</td>
					<td class="label">巡检专业</td>
					<td class="content">
						<eoms:comboBox name="specialty" id="zhuanye" styleClass="select"
							sub="resourceTeype" defaultValue="${pnrResConfigForm.specialty}" initDicId="11225"
							alt="allowBlank:false" />
					</td>
				</tr>

				<tr>
					<td class="label">资源类别</td>
					<td class="content">
						<eoms:comboBox name="resType" defaultValue="${pnrResConfigForm.resType}" styleClass="select"
							id="resourceTeype" initDicId="${pnrResConfigForm.specialty}" alt="allowBlank:false"
							sub="resourceLevel" />
					</td>
					<td class="label">资源级别</td>
					<td class="content">
						<eoms:comboBox name="resLevel" defaultValue="${pnrResConfigForm.resLevel}" styleClass="select"
							id="resourceLevel" initDicId="${pnrResConfigForm.resType}" alt="allowBlank:false" />
					</td>
				</tr>
				
				<tr>
					<td class="label">地市</td>
					<td class="content">
						<select name="region" id="region" class="select"
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
				
				<tr>
					<td class="label">周期分配情况</td>
					<td class="content" colspan="3">
						<select name="assign" class="select" onchange="fenpei()" id="selectType">
							<option id="all">所有</option>
							<option id="no">未分配</option>
							<option id="yes">已分配</option>
						</select>
					</td>
				</tr>

				<tr>
					<td colspan="4" align="center">
						<html:submit styleClass="button" property="method.query">
						查询
					</html:submit>
					</td>
				</tr>
			</table>
		</html:form>
		
		<html:form action="PnrResConfig.do?method=calcleExecuteObject" method="post" styleId="form2" >
	<br/>
	
	<input type="button" class="btn" value="分配所选项" id="relation" onclick="relation1()" />
	<input type="button" class="btn" value="取消分配周期" id="calcleCycle" onclick="cancleCycle()" />		
	<input type="hidden" id="executeObject" name="seldelcar" /> 

	<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" pagesize="${pageSize}" class="table list" 
		export="false" 
		requestURI="${app}/partner/res/PnrResConfig.do"
		sort="list" partialList="true" size="${resultSize}">
	
		<display:column  title="<input type='checkbox' onclick='javascript:chooseAll();'>" >
        	<input type="checkbox" name="checkbox11" id="checkbox11"  value="${list.id} "/>
        	<input type="hidden" id="${list.id}" value="${list.inspectCycle}" />
    	</display:column>
	
		<display:column property="resName" sortable="true"  title="资源名称"
				headerClass="sortable"  paramId="id" paramProperty="id"/>
			
		<display:column sortable="true"  title="巡检周期"
				headerClass="sortable"  paramId="id" paramProperty="id">
				${cycleMap[list.inspectCycle]}
		</display:column>
		<display:column title="上次巡检时间" >
			<bean:write name="list" property="lastInspectTime" format="yyyy-MM-dd HH:mm:ss"/>
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
				
		 <display:column sortable="true" headerClass="sortable" title="详情">
	        <a href="${app}/partner/res/PnrResConfig.do?method=detial&&seldelcar=${list.id}" title="详情">
				<img src="${app}/images/icons/search.gif" />
			</a>
    	</display:column>
				
		</display:table>
		</html:form>
		
	</body>
</html>
