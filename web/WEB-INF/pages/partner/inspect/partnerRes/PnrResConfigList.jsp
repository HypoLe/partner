<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%response.setHeader("cache-control","public"); %>
<script language="javaScript" type="text/javascript" src="${app}/scripts/module/partner/ajax.js"></script>
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

//execl 导出
function openExport(handler){
	var el = Ext.get('listExport'); 
	if(el.isVisible()){
		el.slideOut('t',{useDisplay:true});
		handler.innerHTML = "打开导出界面";
	}
	else{
		el.slideIn();
		handler.innerHTML = "关闭导出界面";
	}
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
  
  </script>
  
  <body>
巡检资源周期以及巡检组分配情况
<display:table name="unAssignResList" cellspacing="0" cellpadding="0"
		id="unAssignResList"  class="table list" export="false" sort="list">
		<display:column  sortable="false"  title="巡检专业" headerClass="sortable">
				<eoms:id2nameDB id="${unAssignResList.specialty}" beanId="ItawSystemDictTypeDao" />
		</display:column>
		<display:column  sortable="false"  title="未分配周期" headerClass="sortable">
			<c:if test="${unAssignResList.specialty == '1122502'}" var="cycleResult">
				<a href = '${app}/partner/res/pnrTransLineAction.do?method=gotoCycle&assign=no'>${unAssignResList.cycleCount}</a>
			</c:if>
			<c:if test="${!cycleResult}">
				<a href = '${app}/partner/res/PnrResConfig.do?method=cycleCheck&assign=no&specialtyStringEqual=${unAssignResList.specialty}'>${unAssignResList.cycleCount}</a>
			</c:if>
		</display:column>
		<display:column  sortable="false"  title="未分配巡检组"
				headerClass="sortable">
			<c:if test="${unAssignResList.specialty == '1122502'}" var="cycleResult">
				<a href = '${app}/partner/res/pnrTransLineAction.do?method=assignCheck&assign=no'>${unAssignResList.executeObjectCount}</a>
			</c:if>
			<c:if test="${!cycleResult}">
				<a href = '${app}/partner/res/PnrResConfig.do?method=assignCheck&assign=no&specialtyStringEqual=${unAssignResList.specialty}'>	
					${unAssignResList.executeObjectCount}</a>
			</c:if>
			</display:column>
</display:table>
<br>  
  
  <div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/icons/search.gif"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openImport(this);">查询</span>
</div>

<div id="listQueryObject" 
	 style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
  <html:form action="PnrResConfig.do?method=search" method="post" >
   <table class="listTable">
			<tr>
					<td class="label">资源名称</td>
					<td class="content">
						<html:text property="resName" styleId="car_number"
							styleClass="text medium" alt="allowBlank:false,vtext:''"
							value="${pnrResConfigForm.resName}" />
					</td>
					<td class="label">巡检专业</td>
					<td class="content">
							<c:if test="${pnrInspect2SwitchConfig.openTransLineInspect eq true }">
								<eoms:comboBox name="specialty" id="zhuanye" styleClass="select"
									sub="resourceTeype" defaultValue="${pnrResConfigForm.specialty}" initDicId="11225"
									alt="allowBlank:false"  hiddenOption="1122502"/>
							</c:if>
							<c:if test="${pnrInspect2SwitchConfig.openTransLineInspect ne true }">
								<eoms:comboBox name="specialty" id="zhuanye" styleClass="select"
									sub="resourceTeype" defaultValue="${pnrResConfigForm.specialty}" initDicId="11225"
									alt="allowBlank:false"  />
							</c:if>
					</td>
				</tr>

				<tr>
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
	<html:form action="PnrResConfig" method="post" >

	巡检资源维护列表
	<display:table name="list" cellspacing="0" cellpadding="0"
		id="list" pagesize="${pageSize}" class="table list" 
		export="false" 
		requestURI="${app}/partner/res/PnrResConfig.do"
		sort="list" partialList="true" size="${resultSize}">
	
		<display:column  title="<input type='checkbox' onclick='javascript:chooseAll();'>" >
        	<input type="checkbox" name="checkbox11" id="checkbox11"  value="${list.id} "/>
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
		<display:column  sortable="true" title="地理环境" 
				headerClass="sortable"  >
				<eoms:id2nameDB id="${list.eographicalEnvironment}" beanId="ItawSystemDictTypeDao" />
		</display:column>
		<display:column  sortable="true" title="区域类型" 
				headerClass="sortable"  >
				<eoms:id2nameDB id="${list.regionType}" beanId="ItawSystemDictTypeDao" />
		</display:column>
		<c:if test="${pnrInspect2SwitchConfig.openMainSwitch eq true}">
		<display:column sortable="true" headerClass="sortable" title="配置网络资源">
	        <a href="${app}/partner/deviceInspect/inspectMapping.do?method=findRelationNetResourceList&id=${list.id}&specialty=${list.specialty}&resType=${list.resType}">
				<img src="${app}/images/icons/add.png" />
			</a>
    	</display:column>
		</c:if>
		 <display:column sortable="true" headerClass="sortable" title="详情">
	        <a href="${app}/partner/res/PnrResConfig.do?method=detial&&seldelcar=${list.id}" title="详情">
				<img src="${app}/images/icons/search.gif" />
			</a>
    	</display:column>
    	
		<!-- 此处添加权限控制 -->
    <display:column sortable="true" headerClass="sortable" title="编辑">
    <c:if test="${1 eq 1}">
       <c:choose>
       	<c:when test="${sessionform.userid eq list.creator }">
       		<a href="${app}/partner/res/PnrResConfig.do?method=edit&&seldelcar=${list.id}" title="编辑">
	        	<img src="${app}/images/icons/edit.gif" />
	        </a>
       	</c:when>
       	<c:otherwise>
       		<c:choose>
       			<c:when test="${isyd eq 'yes' and list.city eq dept }">
			       	<a href="${app}/partner/res/PnrResConfig.do?method=edit&&seldelcar=${list.id}" title="编辑">
			        	<img src="${app}/images/icons/edit.gif" />
			        </a>
		       	</c:when>
		       	<c:when test="${isyd eq 'yes' and list.country eq dept }">
		       		<a href="${app}/partner/res/PnrResConfig.do?method=edit&&seldelcar=${list.id}" title="编辑">
			        	<img src="${app}/images/icons/edit.gif" />
			        </a>
		       	</c:when>
		       	<c:otherwise>
		       		<c:choose>
		       			<c:when test="${sessionform.deptid eq '1' }">
		       				<a href="${app}/partner/res/PnrResConfig.do?method=edit&&seldelcar=${list.id}" title="编辑">
					        	<img src="${app}/images/icons/edit.gif" />
					        </a>
		       			</c:when>
		       			<c:otherwise>
		       				<c:if test="${sessionform.userid eq 'admin' }">
				       			<a href="${app}/partner/res/PnrResConfig.do?method=edit&&seldelcar=${list.id}" title="编辑">
						        	<img src="${app}/images/icons/edit.gif" />
						        </a>
				       		</c:if>
		       			</c:otherwise>
		       		</c:choose>
		       	</c:otherwise>
       		</c:choose>
       	</c:otherwise>
       	
       </c:choose>
       </c:if>
       
    </display:column>
            
    <display:column sortable="true" headerClass="sortable" title="删除">
        <c:if test="${1 eq 0}">
    	<c:choose>
       	<c:when test="${sessionform.userid eq list.creator }">
       		<a href="javascript:if(confirm('您确定要删除您选择的数据？')) 
	        {window.location.href = '${app}/partner/res/PnrResConfig.do?method=remove&&seldelcar=${list.id}';}" title="删除"/>
	        	<img src="${app}/images/icons/icon.gif" />
	        </a>
       	</c:when>
       	<c:otherwise>
       		<c:choose>
       			<c:when test="${isyd eq 'yes' and list.city eq dept }">
			       <a href="javascript:if(confirm('您确定要删除您选择的数据？')) 
			        {window.location.href = '${app}/partner/res/PnrResConfig.do?method=remove&&seldelcar=${list.id}';}" title="删除"/>
			        	<img src="${app}/images/icons/icon.gif" />
			        </a>
		       	</c:when>
		       	<c:when test="${isyd eq 'yes' and list.country eq dept }">
		       		<a href="javascript:if(confirm('您确定要删除您选择的数据？')) 
			        {window.location.href = '${app}/partner/res/PnrResConfig.do?method=remove&&seldelcar=${list.id}';}" title="删除"/>
			        	<img src="${app}/images/icons/icon.gif" />
			        </a>
		       	</c:when>
		       	<c:otherwise>
		       	
		       		<c:choose>
		       			<c:when test="${sessionform.deptid eq '1' }">
		       				<a href="javascript:if(confirm('您确定要删除您选择的数据？')) 
					        {window.location.href = '${app}/partner/res/PnrResConfig.do?method=remove&&seldelcar=${list.id}';}" title="删除"/>
					        	<img src="${app}/images/icons/icon.gif" />
					        </a>
		       			</c:when>
		       			<c:otherwise>
		       				<c:if test="${sessionform.userid eq 'admin' }">
				       			<a href="javascript:if(confirm('您确定要删除您选择的数据？')) 
						        {window.location.href = '${app}/partner/res/PnrResConfig.do?method=remove&&seldelcar=${list.id}';}" title="删除"/>
						        	<img src="${app}/images/icons/icon.gif" />
						        </a>
				       		</c:if>
		       			</c:otherwise>
		       		</c:choose>
		       	</c:otherwise>
       		</c:choose>
       	</c:otherwise>
       	
       </c:choose>
       </c:if>
       --
	</display:column>
	
	<display:setProperty name="export.rtf" value="false" />
	<display:setProperty name="export.pdf" value="false" />
	<display:setProperty name="export.xml" value="false" />
	<display:setProperty name="export.csv" value="false" />			
	</display:table>
	<br>
	<input class="button" type="button" onclick="delselcar()" value="删除所选信息"/>
	</html:form>
	<br/>
	<!--  导出界面  -->
  <table class="formTable">
	<caption>
		巡检资源批量导出
	</caption>
  </table>
  <div style="border:1px solid #98c0f4;padding:5px;"
	 class="x-layout-panel-hd"><img
	 src="${app}/images/ico_file_excel.png"
	 align="absmiddle"
	 style="cursor:pointer" /> 
	<span id="openQuery" style="cursor:pointer" onclick="openExport(this);">导出到Excel</span>
  </div>
  <div id="listExport" 
	 style="display:none;border:1px solid #98c0f4;border-top:0;padding:5px;background-color:#eff6ff;">
	 <html:form action="PnrAllRes.do?method=excelExport"
		method="post" styleId="exportForm"
		enctype="multipart/form-data" >
		<table border=0 cellspacing="1" cellpadding="1" class="listTable">
			<!--附加表以及XML文件基本属性表格：开始-->
			<tr class="tr_show">
				<td  class="label">请选择巡检专业</td>
				<td COLSPAN="18">
		        			<c:if test="${pnrInspect2SwitchConfig.openTransLineInspect eq true }">
		        				<eoms:comboBox  name="specialty" id="exportType"  defaultValue="" 
		        			initDicId="11225" alt="allowBlank:false" styleClass="select" hiddenOption="1122502"/>
							</c:if>
							<c:if test="${pnrInspect2SwitchConfig.openTransLineInspect ne true }">
								<eoms:comboBox  name="specialty" id="exportType"  defaultValue="" 
		        					initDicId="11225" alt="allowBlank:false" styleClass="select"/>
							</c:if>
		        </td>
		    </tr>    
			<tr class="tr_show">
				<td  class="label">
					地市
				</td>
				<td >
					<select name="region1" id="region1" class="select"
				alt="allowBlank:false,vtext:'请选择所在地市'"
				onchange="changeCity1(0);">
				<option value="">
					--请选择所在地市--
				</option>
				<logic:iterate id="city1" name="city1">
					<option value="${city1.areaid}">
						${city1.areaname}
					</option>
				</logic:iterate>
			</select>
				</td>
				<td COLSPAN="3" class="label">
					区县
				</td>
				<td >
					<select name="city1" id="city1" class="select"
				alt="allowBlank:false,vtext:'请选择所在县区'">
				<option value="">
					--请选择所在县区--
				</option>				
			</select>
				</td>
			</tr>
			<tr>
				<td COLSPAN="22">
					<input type="button" class="button" onclick="excelExport()" value="提交">
					<input type="hidden" name="partneridXls" id="partneridXls"value="" />
					<input type="hidden" name="formInterfaceHeadIdXls"
						id="formInterfaceHeadIdxls" value="" />
				</td>
			</tr>
		</table>
	</html:form>
  </div>
	
  </body>
</html>
