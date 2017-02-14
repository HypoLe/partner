<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
  <title><fmt:message key="webapp.name" /></title>
 <base target="_self"/>
<%@ include file="/common/meta.jsp"%>
  <script type="text/javascript" charset="utf-8" src="${app}/scripts/local/zh_CN.js"></script>  
  <script type="text/javascript" charset="utf-8" src="${app}/scripts/base/eoms.js"></script>
  <script type="text/javascript">eoms.appPath = "${app}";</script>
  <link rel="stylesheet" type="text/css" href="${app}/styles/${theme}/theme.css" />
<%@ include file="/common/extlibs.jsp"%>
  <script type="text/javascript" src="${app}/scripts/form/Options.js"></script>
  <script type="text/javascript" src="${app}/scripts/form/combobox.js"></script>
  <script type="text/javascript" src="${app}/scripts/widgets/calendar/calendar.js"></script>
  <script type="text/javascript" src="${app}/scripts/form/validation.js"></script>
<%@ include file="/common/body.jsp"%>


<%@ page language="java" pageEncoding="UTF-8" %>
<%response.setHeader("cache-control","public"); %>
<script language="javaScript" type="text/javascript"
	src="${app}/scripts/module/partner/ajax.js"></script>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


    <link rel="stylesheet" type="text/css" href="${app}/deviceManagement/jquery-ui-1.8.14.custom/css/cupertino/jquery-ui-1.8.14.custom.css">
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-1.5.1.min.js"></script>
<script type="text/javascript" src="${app}/deviceManagement/jquery-ui-1.8.14.custom/js/jquery-ui-1.8.14.custom.min.js"></script>
  <script type="text/javascript">
  var jq=jQuery.noConflict();
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
			},
			failure: function ( result, request) { 
				Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
			} 
		});//初始的时候给区县默认值结束
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
					}							
				},
				failure: function ( result, request) { 
					Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
				} 
			});
		}	
		
		function chooseRes(){
			var res= document.getElementsByName("checkbox11");
        	for (var i = 0; i<res.length; i++){
                if (res[i].checked==true){ 
                
	                var resValue = res[i].value;
	                //if(eoms.isIE){
						window.dialogArguments.setRes(resValue);
					//}else{
						//window.opener.setRes(resValue);
					//}
					// alert('选择站点成功');
					window.close();
                }
        	}  
		}

  </script>
  
  <body>
  <html:form action="roomDemolition.do?method=chooseRoom" method="post" >
  <input type="hidden" id="specialty" name="specialty" value="${specialty}" />
  <input type="hidden" id="region" name="region" value="${region}" />
  <input type="hidden" id="executeDept" name="executeDept" value="${executeDept}" />
   <table class="formTable">
	<caption>
		<div class="header center">站点列表</div>
	</caption>
			<tr>
					<td class="label">资源名称</td>
					<td class="content" colspan="3">
						<input type="text" name="resName" class="text" id="wsNum"
								value="${resName}" />
					</td>
					
				</tr>
			
				<tr><td colspan="4" align="center">
				<html:submit styleClass="button" property="method.query">
						查询
					</html:submit>
					</td></tr>
		</table> 
	</html:form>	
			    
	<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" pagesize="${pageSize}" class="table list" 
		export="false" 
		requestURI="roomDemolition.do"
		sort="list" partialList="true" size="${resultSize}">
		<display:column  title="选择" >
        	<input type="radio" name="checkbox11" id="checkbox11"  value="${list.id},${list.resName },${list.resLevel},<eoms:id2nameDB id="${list.resLevel}" beanId="ItawSystemDictTypeDao" />,${list.resLongitude},${list.resLatitude }"/>
    	</display:column>
		<display:column property="resName" sortable="true"  title="资源名称"
				headerClass="sortable"  paramId="id" paramProperty="id"/>
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
		
	</display:table>
	</br>
	<input class="button" type="button" onclick="chooseRes()" value="选择站点"/>
	<br/>
	
  </body>
</html>
