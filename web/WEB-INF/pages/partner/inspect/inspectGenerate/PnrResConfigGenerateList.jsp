<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%response.setHeader("cache-control","public"); %>
<script language="javaScript" type="text/javascript"
	src="${app}/scripts/module/partner/ajax.js"></script>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
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

<c:if test="${transLine eq 'yes'}" var="result_1"></c:if>  
  
<c:if test="${!result_1}">
<script type="text/javascript">
Ext.onReady(function(){
		Ext.Ajax.timeout = 1800000;
  		//初始的时候给区县默认值
		delAllOption("city");//地市选择更新后，重新刷新县区
		var region = document.getElementById("region").value;
		var url="${app}/partner/baseinfo/linkage.do?method=changeCity&city="+region;
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
				if(city!=''){
					document.getElementById("city").value = city;
				}
			},
			failure: function ( result, request) { 
				Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
			} 
		});//初始的时候给区县默认值结束
  });
  </script>
  </c:if>
  
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
		var transLine = '${transLine}';
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
			Ext.get(document.body).mask('临时元任务生成中......');
			Ext.Ajax.request({
				url:"${app}/partner/inspect/inspectGenerateAction.do?method=generateInspectPlanres&queryGenerate=false&generate="+string+"&transLine="+transLine,
				success: function(response,options){		
					Ext.get(document.body).unmask();
					if(response.responseText == 'failure'){
						alert('没有符合查询条件的资源，无法生成');
					}else{
						alert('生成成功');
					}
				},
				failure: function(){
					Ext.get(document.body).unmask();
					Ext.Msg.show({
						title: '错误提示',
						msg: '生成发生未知错误',
						buttons: Ext.Msg.OK,
						icon: Ext.Msg.ERROR
					});
				}
			});
		 }else{
			 return false;
		 }
	}
  
 	function delAllOption(elementid){
         var ddlObj = document.getElementById(elementid);//获取对象
         if(ddlObj){
         	for(var i=ddlObj.length-1;i>=0;i--){
              ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
         	}	
         }
     }
	
	//地区、区县连动
	function changeCity(con){    
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
					}							
				},
				failure: function ( result, request) { 
					Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
				} 
			});
		}	

	//点击查询按钮设置查询标识
		function query(){
			var form1 = document.getElementById('form1');
			var isQuery = document.getElementById('isQuery');
			isQuery.value = 'true';
		}
		
		/**
		* 按查询条件生成
		*/
		function queryGenerate(){
			var form1 = document.getElementById('form1');
			var isQuery = document.getElementById('isQuery').value;
			if('true' == isQuery){
				if(confirm("确认按此查询条件生成巡检任务吗？")){
					var transLine = '${transLine}';
					Ext.get(document.body).mask('临时元任务生成中......');
		        	Ext.Ajax.request({
		        		
		        		params : Ext.lib.Ajax.serializeForm(form1),
						url:"${app}/partner/inspect/inspectGenerateAction.do?method=generateInspectPlanres&queryGenerate=true&transLine="+transLine,
						success: function(response,options){		
							Ext.get(document.body).unmask();
							if(response.responseText == 'failure'){
								alert('没有符合查询条件的资源，无法生成');
							}else{
								alert('生成成功');
							}
						},
						failure: function(){
							Ext.get(document.body).unmask();
							Ext.Msg.show({
								title: '错误提示',
								msg: '生成发生未知错误',
								buttons: Ext.Msg.OK,
								icon: Ext.Msg.ERROR
							});
						}
					});
				 }else{
					 return false;
				 }
			}else{
				alert('请先点击查询按钮进行查询');
			}
		}
  </script>
  <body>

  <form id="form1" action="${app}/partner/inspect/inspectGenerateAction.do?method=toPnrResConfigGenerate" method="post" >
  	<input type="hidden" name="isQuery" id="isQuery" value="${isQuery }"/>
  	<c:if test="${!result_1}">
	   <table class="listTable">
		<caption>
			<div class="header center">巡检资源信息</div>
		</caption>
				<tr>
						<td class="label">资源名称<input type="hidden" value="${transLine}" name="transLine" /></td>
						<td class="content">
							<html:text property="resNameStringLike" styleId="car_number"
								styleClass="text medium" alt="allowBlank:false,vtext:''"
								value="${pnrResConfigForm.resName}" />
						</td>
						<td class="label">巡检专业</td>
						<td class="content">
							<c:if test="${pnrInspect2SwitchConfig.openTransLineInspect eq true }">
								<eoms:comboBox name="specialtyStringEqual" id="zhuanye"
									sub="resourceTeype" defaultValue="${pnrResConfigForm.specialty}" initDicId="11225" 
									alt="allowBlank:false" styleClass="select" hiddenOption="1122502" />
							</c:if>
							<c:if test="${pnrInspect2SwitchConfig.openTransLineInspect ne true }">
								<eoms:comboBox name="specialtyStringEqual" id="zhuanye"
									sub="resourceTeype" defaultValue="${pnrResConfigForm.specialty}" initDicId="11225" 
									alt="allowBlank:false" styleClass="select" />
							</c:if>
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
					<tr>
					<td class="label">地市</td>
					<td class="content">
						<select name="cityStringEqual" id="region" class="select"
							alt="allowBlank:false,vtext:'请选择所在地市'"
							onchange="changeCity(0);">
							<option value="">
								--请选择所在地市--
							</option>
							<logic:iterate id="city" name="city">
							<c:if test="${pnrResConfigForm.city==city.areaid}" var="result">
								<option value="${city.areaid}" selected="selected" >
									${city.areaname}
								</option>
							</c:if>
							<c:if test="${!result}" >
							<option value="${city.areaid}">
								${city.areaname}
							</option>
							</c:if>
							</logic:iterate>
						</select>
					</td>
					<td class="label">区县</td>
					<td class="content">
						<select name="countryStringEqual" id="city" class="select"
							alt="allowBlank:false,vtext:'请选择所在县区'">
							<option value="">
							--请选择所在县区--
							</option>				
						</select>
					</td>
				</tr>
			</table>
		</c:if> 
		<c:if test="${result_1}">
			 <table class="listTable">
			 <caption>
				<div class="header center">巡检资源信息</div>
			</caption>
				<tr>
						<td class="label">资源名称 <input type="hidden" value="${transLine}" name="transLine" /></td>
						<td class="content">
							<html:text property="resNameStringLike" styleId="car_number"
								styleClass="text medium" alt="allowBlank:false,vtext:''"
								value="${pnrResConfigForm.resName}" /> 
						</td>
						
						<td class="label">区域</td>
						<td class="content">
							<html:text property="tlDisStringLike" styleId="car_number"
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
	
					<%-- <tr>
					
						<td class="label">区县</td>
						<td class="content">
							<select name="country" id="country" class="select"
								alt="allowBlank:false,vtext:'请选择所在县区'">
								<option value="">
									--请选择所在县区--
								</option>				
							</select>
						</td>
						<td class="label"></td>
						<td class="content">
						</td>
					</tr>--%>
					<tr>
						<td class="label">光缆系统</td>
						<td class="content">
							<html:text property="tlWireStringLike" styleId="car_number"
								styleClass="text medium" alt="allowBlank:false,vtext:''"
								value="${pnrResConfigForm.tlWire}" />
						</td>
						<td class="label">光缆段</td>
						<td class="content">
							<html:text property="tlSegStringLike" styleId="car_number"
								styleClass="text medium" alt="allowBlank:false,vtext:''"
								value="${pnrResConfigForm.tlSeg}" />
						</td>
					</tr>
			</table> 
		</c:if>
		<table>
		    <tr>
			    <td>
			    	<input type="submit" class="btn" value="<fmt:message key="button.query"/>" onclick="query()"/>&nbsp;&nbsp;
				</td>
			</tr>
		</table>
	</form>	
		
  </body>
</html>
</br>
<input type="button" value="按勾选项生成" class="btn" onclick="generate()" />

<input type="button" value="按查询条件生成" class="btn" onclick="queryGenerate()" />

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
		<c:choose>
			<c:when test="${transLine eq 'yes'}">
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
			</c:when>
			<c:otherwise>
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
			
			</c:otherwise>
		</c:choose>
				
		<display:column title="巡检小组" >
			<eoms:id2nameDB id="${list.executeObject}" beanId="partnerDeptDao"/>
		</display:column>
		<c:if test="${pnrInspect2SwitchConfig.sheetInspectSwitch eq false}">
			<display:column title="巡检周期" >
				${cycleMap[list.inspectCycle]}
			</display:column>
		</c:if>
		<c:if test="${transLine ne 'yes'}">
		 <display:column sortable="true" headerClass="sortable" title="详情">
	        <a href="${app}/partner/res/PnrResConfig.do?method=detial&&seldelcar=${list.id}" title="详情">
				<img src="${app}/images/icons/search.gif" />
			</a>
    	</display:column>
		</c:if>	
	</display:table>