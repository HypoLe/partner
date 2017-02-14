<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
var tr = true;
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'gridForm'});
});
function createRequest()
{
	var httpRequest = null;
	if(window.XMLHttpRequest){
     httpRequest=new XMLHttpRequest();
    }else if(window.ActiveXObject){
     httpRequest=new ActiveXObject("MIcrosoft.XMLHttp");
    }
    return httpRequest;
}
function valgrid()
{
	var obj = document.getElementById("gridName");
	var gridName = document.getElementById("gridName").value;
	gridName = encodeURIComponent(gridName);
	var url = "../serviceArea/grids.do?method=validationGridName&gridId="+document.getElementById("id").value+"&gridName="+gridName;

		Ext.Ajax.request({
					url : url ,
					method: 'POST',
					success: function ( result, request ) { 
						res = result.responseText;
						if(res.indexOf("<\/SCRIPT>")>0){
					  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
						}
						var json = eval(res);
							if(json[0].message == true){
			     				if(obj.value!=""){
			     					document.getElementById("message").innerHTML = "<font color='green'>此名称可以使用</font>";
			     					tr = true;
			     				}else{
			     					obj.focus();
			     				}
			     			}else{
			     				document.getElementById("message").innerHTML = "<font color='red'>对不起,此网格名称已存在</font>";
			     				tr = false;
			     			}	
						},
					failure: function ( result, request) { 
						Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
					} 
				});
}

function sub(){
	if(tr){
	}else{
		alert("存在的网格名称");
		return false;
	}
	if(v.check()){
       $("gridForm").submit();
	}	
}




function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
	            for(var i=ddlObj.length-1;i>=0;i--){
	                ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
            }
            
        }
function getResponseText(url) {
    var xmlhttp;
    if(eoms.isIE){
    	try{
    		xmlhttp = new ActiveXObject("Msxml2.XMLHTTP"); 
    	}catch(e){
    		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");    		
    	}
    }else{
    	xmlhttp = new XMLHttpRequest();
    }
    var method = "post";
    url=url+"&"+new Date();
    xmlhttp.open(method, url, false);
    xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8"); 
    xmlhttp.send(null);
    return xmlhttp.responseText;
}
//县区联动
function changeCity(con)
		{    
		    delAllOption("city");//地市选择更新后，重新刷新县区
		    delAllOption("provider");//地市选择更新后，重新刷新合作伙伴
			var region = document.getElementById("region").value;
			var url="<%=request.getContextPath()%>/partner/serviceArea/lines.do?method=changeCity&city="+region;
			//var result=getResponseText(url);
			Ext.Ajax.request({
								url : url ,
								method: 'POST',
								success: function ( result, request ) { 
									res = result.responseText;
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
									
									var providerName = "provider";
									var arrOptionsP=json[0].pb.split(",");
									var objp=$(providerName);
									var m=0;
									var n=0;
									for(m=0;m<arrOptionsP.length;m++){
										var optp=new Option(arrOptionsP[m+1],arrOptionsP[m]);
										objp.options[n]=optp;
										n++;
										m++;
										
									}
									
									
									if(con==1){
										var city = '${gridForm.city}';
										if(city!=''){
											document.getElementById("city").value = city;
										}	
									}	
									changePartner(con);							
								},
								failure: function ( result, request) { 
									Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
								} 
							});
		}
		
//地市、县区联动合作伙伴		
function changePartner(con)
		{    
		    delAllOption("provider");//地市选择更新后，重新刷新合作伙伴
			//地市
			var regionValue = document.getElementById("region").value;
			//县区
			var cityValue = document.getElementById("city").value;
			var url="<%=request.getContextPath()%>/partner/serviceArea/lines.do?method=changePartner&region="+regionValue+"&city="+cityValue;
			//var result=getResponseText(url);
			Ext.Ajax.request({
							url : url ,
							method: 'POST',
							//params:{providerValue:providerValue},
							success: function ( result, request ) { 
							res = result.responseText;
							if(res.indexOf("<\/SCRIPT>")>0){
						  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
							}
								var json = eval(res);
								
								var providerName = "provider";
								var arrOptionsP=json[0].pb.split(",");
								var objp=$(providerName);
								var m=0;
								var n=0;
								for(m=0;m<arrOptionsP.length;m++){
									var optp=new Option(arrOptionsP[m+1],arrOptionsP[m]);
									objp.options[n]=optp;
									n++;
									m++;
									
								}
								
								if(con==1){
										var provider = '${gridForm.provider}';
										if(provider!=''){
											document.getElementById("provider").value = provider;
										}
										
								}				
							},
							failure: function ( result, request) { 
								Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
							} 
						});
		}
		
		

		
</script>

<html:form  action="/grids.do?method=save" styleId="gridForm" method="post" > 

<fmt:bundle basename="com/boco/eoms/partner/serviceArea/config/applicationResource-partner-serviceArea">
<font color='red'>*</font>号为必填内容
<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="grid.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="grid.gridName" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="gridName" styleId="gridName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32" value="${gridForm.gridName}" onblur="valgrid()"/>
			<span id = "message"></span>			
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="grid.region" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<!-- 地市 -->			
			<select name="region" id="region"
				alt="allowBlank:false,vtext:'请选择所在地市'"
				onchange="changeCity(0);">
				<option value="">
					--请选择所在地市--
				</option>
				<logic:iterate id="city" name="city">
					<option value="${city.areaid}">
						${city.areaname}
					</option>
				</logic:iterate>
			</select>
						
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="grid.city" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<!-- 县区 -->			
			<select name="city" id="city" 
				alt="allowBlank:false,vtext:'请选择所在县区'"
				onchange="changePartner(0);">
				<option value="">
					--请选择所在县区--
				</option>				
			</select>
						
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="grid.provider" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<!-- 合作伙伴 -->			
			<select name="provider" id="provider" 
				alt="allowBlank:false,vtext:'请选择合作伙伴'">
				<option value="">
					--请选择合作伙伴--
				</option>				
			</select>
						
		</td>
	</tr>
<!-- 
	<tr>
		<td class="label">
			<fmt:message key="grid.addUser" />
		</td>
		<td class="content">
			<html:text property="addUser" styleId="addUser"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${gridForm.addUser}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="grid.addTime" />
		</td>
		<td class="content">
			<html:text property="addTime" styleId="addTime"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${gridForm.addTime}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="grid.updateUser" />
		</td>
		<td class="content">
			<html:text property="updateUser" styleId="updateUser"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${gridForm.updateUser}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="grid.updateTime" />
		</td>
		<td class="content">
			<html:text property="updateTime" styleId="updateTime"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${gridForm.updateTime}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="grid.delUser" />
		</td>
		<td class="content">
			<html:text property="delUser" styleId="delUser"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${gridForm.delUser}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="grid.delTime" />
		</td>
		<td class="content">
			<html:text property="delTime" styleId="delTime"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${gridForm.delTime}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="grid.isDel" />
		</td>
		<td class="content">
			<html:text property="isDel" styleId="isDel"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${gridForm.isDel}" />
		</td>
	</tr>
 -->

</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="button" class="btn" value="<fmt:message key="button.save"/>" onclick=" sub();"/>
			<c:if test="${not empty gridForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('你确定删除?')){
						var url=eoms.appPath+'/partner/serviceArea/grids.do?method=remove&id=${gridForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="addUser" value="${gridForm.addUser}" />
<html:hidden property="addTime" value="${gridForm.addTime}" />
<html:hidden property="updateUser" value="${gridForm.updateUser}" />
<html:hidden property="delTime" value="${gridForm.delTime}" />
<html:hidden property="delUser" value="${gridForm.delUser}" />
<html:hidden property="updateTime" value="${gridForm.updateTime}" />
<html:hidden property="id" value="${gridForm.id}" />
</html:form>

<script type="text/javascript">
//修改时，自动加载原来的地市县区显示在修改页面	
window.onload = function(){
    var region = '${gridForm.region}';
	if(region!=''){
 		document.getElementById("region").value = region;
		changeCity(1);
	}
}
</script>

<%@ include file="/common/footer_eoms.jsp"%>