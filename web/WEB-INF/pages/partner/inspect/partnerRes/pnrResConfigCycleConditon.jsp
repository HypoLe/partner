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
});

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

function delAllOption(elementid){
         var ddlObj = document.getElementById(elementid);//获取对象
          for(var i=ddlObj.length-1;i>=0;i--){
              ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
         }	
         
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
		        	alert("请选择资源级别！");
		        	return ;
		        } 
	
	    var _sHeight = 280;
        var _sWidth = 480;
        var sTop=(window.screen.availHeight-_sHeight)/2;
        var sLeft=(window.screen.availWidth-_sWidth)/2;
        var sFeatures="dialogHeight: "+_sHeight+"px; dialogWidth: "+_sWidth+"px; dialogTop: "+sTop+"; dialogLeft: "+sLeft+"; center: Yes; scroll:Yes;help: No; resizable: No; status: No;";
    
	    var url ="${app}/partner/res/PnrResConfig.do?method=cycleWindowWeekTime";
	    var pro =  window.showModalDialog(url,window,sFeatures);
	    if(pro==null){
	    	return;
	    }
	    Ext.Ajax.request({
		method:"post",
		url: "${app}/partner/res/PnrResConfig.do?method=updateCycleWeekTime",
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
		        	alert("请选择资源级别！");
		        	return ;
		        } 
		         if(confirm("确定取消关联所选项？")==1){
					 Ext.Ajax.request({
			    	method:"post",
					url: "${app}/partner/res/PnrResConfig.do?method=deleteCycleWeekTime",
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
	 <div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">打开查询界面</span>
</div>

<div id="listQueryObject" 
	 style="display:block;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
		<html:form action="PnrResConfig.do?method=cycleCheckCondition" styleId="myform" method="post">
			<table class="listTable">
				<tr>
					<td class="label">巡检专业</td>
					<td class="content">
						
							<eoms:comboBox name="specialtyStringEqual" id="zhuanye" styleClass="select"
							sub="resourceTeype" defaultValue="${specialty}" initDicId="11225"
							alt="allowBlank:false" />
						
					</td>
					<td class="label">资源类别</td>
					<td class="content">
						<eoms:comboBox name="resTypeStringEqual" defaultValue="${resType}" styleClass="select"
							id="resourceTeype" initDicId="${specialty}" alt="allowBlank:false"
							sub="resourceLevel" />
					</td>
				</tr>

				<tr>
					<td class="label">资源级别</td>
					<td class="content">
						<eoms:comboBox name="resLevelStringEqual" defaultValue="${resLevel}" styleClass="select"
							id="resourceLevel" initDicId="${resType}" alt="allowBlank:false" />
					</td>
					<td class="label">周期分配情况</td>
					<td class="content">
						<select name="assign" class="select" onchange="fenpei()" id="selectType">
							<option id="all" value="all">所有</option>
							<option id="no" value="no">未分配</option>
							<option id="yes" value="yes">已分配</option>
						</select>
					</td>
				</tr>

			</table>
			<table>
			    <tr>
				    <td>
				    	<input type="submit" class="btn" value="<fmt:message key="button.query"/>" />&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</html:form>
		</div>
		<html:form action="PnrResConfig.do?method=calcleExecuteObject" method="post" styleId="form2" >
	<br/>	
	<input type="button" class="btn" value="分配所选项" id="relation" onclick="relation1()" />
	<input type="hidden" class="btn" value="取消分配周期" id="calcleCycle" onclick="cancleCycle()" />		
	<input type="hidden" id="executeObject" name="seldelcar" /> 
	  
    <display:table name="list" cellspacing="0" cellpadding="0"
		id="list" pagesize="${pageSize}" class="table list" 
		export="false" 
		requestURI="${app}/partner/res/PnrResConfig.do"
		sort="list" partialList="true" size="${resultSize}">
		
		<display:column  title="<input type='checkbox' onclick='javascript:chooseAll();'>" >
        	<input type="checkbox" name="checkbox11" id="checkbox11"  value="${list.cdictId}"/>
    	</display:column>
		<display:column sortable="true"  title="巡检周期"
				headerClass="sortable"  paramId="id" paramProperty="id">
				${list.weekname}
		</display:column>
		<display:column  sortable="true"  title="巡检专业"
				headerClass="sortable"  paramId="id" paramProperty="id">
				${list.specialty}
		</display:column>
		<display:column  sortable="true" title="资源类别" 
				headerClass="sortable"  >
				${list.resType}
		</display:column>
		<display:column  sortable="true" title="资源级别" 
				headerClass="sortable">
				${list.resLevel}
		</display:column>
			
	</display:table>
		
   	</html:form>
		
	</body>
</html>
