<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>

<script type="text/javascript">
var tr = true;
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'siteForm'});
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
	var obj = document.getElementById("siteNo");
 var providerValue = document.getElementById("provider").value;
	if(!/^\d+$/.test(obj.value)){
		document.getElementById("message").innerHTML = "<font color='red'>对不起,基站站号只能是数字</font>";
		return document.getElementById("siteNo").value='';
	}

	var siteNo = encodeURIComponent(document.getElementById("siteNo").value);
	
	var url = eoms.appPath+"/partner/serviceArea/sites.do?method=validationSiteNo&siteId="+document.getElementById("id").value+"&siteNo="+siteNo;
	
	Ext.Ajax.request({
					url : url ,
					method: 'POST',
					params:{providerValue:providerValue},
					success: function ( result, request ) { 
					res = result.responseText;
					if(res.indexOf("<\/SCRIPT>")>0){
				  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
					}
						var json = eval(res);
						if(json[0].message == true){
		     				if(obj.value!=""){
		     					document.getElementById("message").innerHTML = "<font color='green'>此站号可以使用</font>";
		     					tr = true;
		     				}else{
		     					obj.focus();
		     				}
		     			}else{
		     				document.getElementById("message").innerHTML = "<font color='red'>对不起,此基站站号已存在</font>";
		     				tr = false;
	     				}		
					},
					failure: function ( result, request) { 
						Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
					} 
				});		
}

function sub(){
/*	if(tr){
	}else{
		alert("已存在的基站站号");
		return false;
	}
*/	
	if(v.check()){
       $("siteForm").submit();
	}	
}
window.onload = function(){
	//修改时，自动加载原来的地市县区显示在修改页面
    var region = '${siteForm.region}';
		
	if(region!=''){
 		document.getElementById("region").value = region;
		changeCity(1);
	}

    var idSite = '${siteForm.id}';
	var operType = '${operType}';
	if(idSite != '' && operType == 'save'){
	    parent.setId(idSite);
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
		    delAllOption("grid");
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
									
									var gridName = "grid";
									var arrOptionsG=json[0].gl.split(",");
									var objp=$(gridName);
									var m=0;
									var n=0;
									for(m=0;m<arrOptionsG.length;m++){
										var optp=new Option(arrOptionsG[m+1],arrOptionsG[m]);
										objp.options[n]=optp;
										n++;
										m++;
									}	
									
									if(con==1){
										var city = '${siteForm.city}';
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
		    delAllOption("grid");
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
								
								var gridName = "grid";
									var arrOptionsG=json[0].gl.split(",");
									var objp=$(gridName);
									var m=0;
									var n=0;
									for(m=0;m<arrOptionsG.length;m++){
										var optp=new Option(arrOptionsG[m+1],arrOptionsG[m]);
										objp.options[n]=optp;
										n++;
										m++;
									}	
								if(con==1){
										var provider = '${siteForm.provider}';
										if(provider!=''){
											document.getElementById("provider").value = provider;
										}
										
										changeGrid(con);
								}				
							},
							failure: function ( result, request) { 
								Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
							} 
						});
		}
		
		
		
//地市、县区、合作伙伴 联动 网格		
function changeGrid(con){
    var cityValue = document.getElementById("city").value;
    var providerValue = document.getElementById("provider").value;
    if(cityValue!=''&&providerValue!=''){
	    delAllOption("grid");//合作伙伴和县区选择更新后，重新刷新网格
	    var regionValue = document.getElementById("region").value;
			var url="<%=request.getContextPath()%>/partner/serviceArea/lines.do?method=changeGrid&city="+regionValue+"&cityValue="+cityValue;
		
			Ext.Ajax.request({
							url : url ,
							method: 'POST',
							params:{providerValue:providerValue},
							success: function ( result, request ) { 
							res = result.responseText;
							if(res.indexOf("<\/SCRIPT>")>0){
						  	res = res.substring(res.indexOf("<\/SCRIPT>")+9,res.length);
							}
								var json = eval(res);
		     					var countyName = "grid";
								var arrOptions = json[0].gl.split(",");
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
										var grid = '${siteForm.grid}';
										if(grid!=''){
											document.getElementById("grid").value = grid;
										}
								}				
							},
							failure: function ( result, request) { 
								Ext.MessageBox.alert('Failed', '操作失败'+result.responseText); 
							} 
						});
    }
    
}	
		

</script>

<html:form action="/sites.do?method=save" styleId="siteForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/serviceArea/config/applicationResource-partner-serviceArea">
<font color='red'>*</font>号为必填内容
<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="site.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="site.siteName" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="siteName" styleId="siteName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32" value="${siteForm.siteName}"/>
		</td>
		<td class="label">
			<fmt:message key="site.siteNo" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="siteNo" styleId="siteNo"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number',maxLength:32" value="${siteForm.siteNo}" />
						<span id="message"></span>
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="site.siteType" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
				<eoms:comboBox name="siteType" id="siteType" initDicId="1121101" defaultValue="${siteForm.siteType}"
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>

		</td>
		<td class="label">
			<fmt:message key="site.frequencyBand" />
		</td>
		<td class="content">
			<html:text property="frequencyBand" styleId="frequencyBand"
						styleClass="text medium"
						alt="maxLength:32" value="${siteForm.frequencyBand}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="site.vendor" />
		</td>
		<td class="content">
		<html:text property="vendor" styleId="vendor"
						styleClass="text medium"
						alt="allowBlank:true,vtext:'',maxLength:200" value="${siteForm.vendor}" />
			
		</td>

		
		<td class="label">
			<fmt:message key="site.maintainType" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<eoms:dict key="dict-partner-serviceArea" dictId="maintainType" isQuery="false"  alt="allowBlank:false,vtext:''"
				defaultId="${siteForm.maintainType}" selectId="maintainType" beanId="selectXML" />
		</td>		
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="site.coverType" />
		</td>
		<td class="content">
			<eoms:comboBox name="coverType" id="coverType" initDicId="1121104" defaultValue="${siteForm.coverType}" />
		</td>
		<td class="label">
			<fmt:message key="site.longitude" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="longitude" styleId="longitude"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32" value="${siteForm.longitude}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="site.latitude" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="latitude" styleId="latitude"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32" value="${siteForm.latitude}" />
		</td>
		<td class="label">
			<fmt:message key="site.cellulType" />
		</td>
		<td class="content">			
			<eoms:comboBox name="cellulType" id="cellulType" initDicId="1121105" defaultValue="${siteForm.cellulType}"/>
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="site.siteLevle" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<eoms:comboBox name="siteLevle" id="siteLevle" initDicId="1121106" defaultValue="${siteForm.siteLevle}"
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
				
		</td>
		<td class="label">
			<fmt:message key="site.roomType" />
		</td>
		<td class="content">
			<eoms:comboBox name="roomType" id="roomType" initDicId="1121107" defaultValue="${siteForm.roomType}"/>
		</td>
	</tr>

	<tr>

		<td class="label">
			<fmt:message key="site.region" />&nbsp;<font color='red'>*</font>
		</td>
<!-- 		<td class="content">
			<html:text property="region" styleId="region"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32" value="${siteForm.region}" />
		</td>
 -->		
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
		
		<td class="label">
			<fmt:message key="site.city" />&nbsp;<font color='red'>*</font>
		</td>
<!-- 	<td class="content">
			<html:text property="city" styleId="city"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32" value="${siteForm.city}" />
		</td>
 -->			
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
			<fmt:message key="site.provider" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<!-- 合作伙伴 -->			
			<select name="provider" id="provider" 
				alt="allowBlank:false,vtext:'请选择合作伙伴'"
				onchange="changeGrid(0);">
				<option value="">
					--请选择合作伙伴--
				</option>				
			</select>
						
		</td>		
		
		<td class="label">
			<fmt:message key="site.grid" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<!-- 所属网格 -->
			<select name="grid" id="grid" 
				alt="allowBlank:false,vtext:'请选择所属网格'" >
				<option value="">
					--请选择所属网格--
				</option>				
			</select>
		</td>
		
		
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="site.setDate" />
		</td>
		<td class="content">
	          <input type="text" size="20" readonly="true" class="text" 
                name="setDate" id="setDate"
                onclick="popUpCalendar(this,this,null,null,null,true,-1);"
                value="${siteForm.setDate}" />		
		</td>
		<td class="label">
			<fmt:message key="site.networkDate" />
		</td>
		<td class="content">
	          <input type="text" size="20" readonly="true" class="text" 
                name="networkDate" id="networkDate"
                onclick="popUpCalendar(this,this,null,null,null,true,-1);"
                value="${siteForm.networkDate}" />
		</td>
	</tr>
<!--
	<tr>
		<td class="label">
			<fmt:message key="site.addTime" />
		</td>
		<td class="content">
			<html:text property="addTime" styleId="addTime"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${siteForm.addTime}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="site.addUser" />
		</td>
		<td class="content">
			<html:text property="addUser" styleId="addUser"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${siteForm.addUser}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="site.updateTime" />
		</td>
		<td class="content">
			<html:text property="updateTime" styleId="updateTime"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${siteForm.updateTime}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="site.updateUser" />
		</td>
		<td class="content">
			<html:text property="updateUser" styleId="updateUser"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${siteForm.updateUser}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="site.delTime" />
		</td>
		<td class="content">
			<html:text property="delTime" styleId="delTime"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${siteForm.delTime}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="site.delUser" />
		</td>
		<td class="content">
			<html:text property="delUser" styleId="delUser"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${siteForm.delUser}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="site.isDel" />
		</td>
		<td class="content">
			<html:text property="isDel" styleId="isDel"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${siteForm.isDel}" />
		</td>
	</tr>
-->

</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="button" class="btn" value="<fmt:message key="button.save"/>" onclick=" sub();"/>
			<c:if test="${not empty siteForm.id}">
					<input type="button" class="btn" value='<fmt:message key="button.back"/>' onclick="javascript:parent.window.history.back();" />
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="addUser" value="${siteForm.addUser}" />
<html:hidden property="addTime" value="${siteForm.addTime}" />
<html:hidden property="updateUser" value="${siteForm.updateUser}" />
<html:hidden property="delTime" value="${siteForm.delTime}" />
<html:hidden property="delUser" value="${siteForm.delUser}" />
<html:hidden property="updateTime" value="${siteForm.updateTime}" />
<html:hidden property="id" styleId="id" value="${siteForm.id}" />
</html:form>

<%@ include file="/common/footer_eoms.jsp"%>