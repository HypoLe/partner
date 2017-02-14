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
	
	function generate(){
var string="";
 var objName= document.getElementsByName("checkbox11");
        for (var i = 0; i<objName.length; i++){
                if (objName[i].checked==true){ 
                string+="'"+objName[i].value.trim()+"',";   
                }
        }  
        if(confirm("确认生成巡检任务吗？")){
        	if(string == null || "" ==  string){
        		alert("请选择巡检资源");
        		return false;
        	}
		 	
		 Ext.Ajax.request({
					url:"${app}/partner/inspect/inspectGenerateAction.do?method=generateInspectPlanres&generate="+string,
					success: function(x){
					Ext.Msg.alert("提示","生成成功");
					}
				});
		 	
		 	//location.href="${app}/partner/inspect/inspectGenerateAction.do?method=generateInspectPlanres&generate="+string;
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

  </script>
  
  <body>
  <form action="${app}/partner/inspect/inspectGenerateAction.do?method=toPnrResConfigGenerate" method="post" >
   <table class="formTable">
	<caption>
		<div class="header center">巡检资源信息</div>
	</caption>
			<tr>
					<td class="label">资源名称</td>
					<td class="content">
						<html:text property="resNameStringLike" styleId="car_number"
							styleClass="text medium" alt="allowBlank:false,vtext:''"
							value="${pnrResConfigForm.resName}" />
					</td>
					<td class="label">巡检专业</td>
					<td class="content">
						<eoms:comboBox name="specialtyStringEqual" id="zhuanye"
							sub="resourceTeype" defaultValue="${pnrResConfigForm.specialty}" initDicId="11225" 
							alt="allowBlank:false" styleClass="select" />
					</td>
				</tr>

				<tr>
					<td class="label">资源类别</td>
					<td class="content">
						<eoms:comboBox name="resTypeStringEqual" defaultValue="${pnrResConfigForm.resType}"
							id="resourceTeype" initDicId="${pnrResConfigForm.specialty}" alt="allowBlank:false"
							sub="resourceLevel" styleClass="select" />
					</td>
					<td class="label">资源级别</td>
					<td class="content">
						<eoms:comboBox name="resLevelStringEqual" defaultValue="${pnrResConfigForm.resLevel}"
							id="resourceLevel" initDicId="${pnrResConfigForm.resType}" alt="allowBlank:false" styleClass="select" />
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
	</form>	
		
  </body>
</html>
</br>
<input type="button" value="生成临时元任务" class="btn" onclick="generate()" />

<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" pagesize="${pageSize}" class="table list" 
		export="false" 
		requestURI="${app}/partner/inspect/inspectGenerateAction.do"
		sort="list" partialList="true" size="${resultSize}">
	
		<display:column  title="<input type='checkbox' onclick='javascript:chooseAll();'>" >
        	<input type="checkbox" name="checkbox11" id="checkbox11"  value="${list.id} "/>
    	</display:column>
	
		<display:column property="resName" sortable="true"  title="资源名称"
				headerClass="sortable"  paramId="id" paramProperty="id"/>
		<%-- 
				
		<display:column  sortable="true"  title="所在地市"
				headerClass="sortable" >
				<eoms:id2nameDB id="${list.city}" beanId="tawSystemAreaDao" />
		</display:column>
				
		<display:column  sortable="区县" title="所在区县"
				headerClass="sortable"  >
			<eoms:id2nameDB id="${list.country}" beanId="tawSystemAreaDao" />
		</display:column>
		--%> 
		
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
		
		<display:column  sortable="区县" title="标准到点率"
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
		
		<display:column  sortable="区县" title="巡检上报频率"
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
		
		<display:column  sortable="区县" title="巡检方式"
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
			
	</display:table>