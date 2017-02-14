<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'stationForm'});
});
var tr = true;
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
function val()
{
	var obj = document.getElementById("stationName");
	var stationName = encodeURIComponent(document.getElementById("stationName").value);
	var providerName = encodeURIComponent(document.getElementById("provider").value);
	var url = "../baseinfo/stations.do?method=validationStation&staId="+document.getElementById("id").value+"&stationName="+stationName+"&providerName="+providerName;
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
						     					document.getElementById("message").innerHTML = "<font color='green'>驻点可以使用</font>";
						     					tr = true;
						     				}else{
						     					obj.focus();
						     				}
						     			}else{
						     				document.getElementById("message").innerHTML = "<font color='red'>对不起,驻点已存在</font>";
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
		alert("驻点已存在");
		return false;
	}
	if(v.check()){
       $("aptitudeForm").submit();
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
    xmlhttp.setRequestHeader("content-type", "text/html; charset=GBK");
    xmlhttp.send(null);
    return xmlhttp.responseText;
}


function delAllOption(elementid){
            var ddlObj = document.getElementById(elementid);//获取对象
            if(ddlObj.value!=null && ddlObj.value!="" ){
	            for(var i=ddlObj.length-1;i>=0;i--){
	                ddlObj.remove(i);//firefox不支持ddlCurSelectKey.options.remove(),IE两种均支持
	            }
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
										var city = '${stationForm.city}';
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
										var provider = '${stationForm.provider}';
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

<html:form action="/stations.do?method=save" styleId="stationForm" method="post"> 
<font color='red'>*</font>号为必填内容
<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">
<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="station.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="station.stationName" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="stationName" styleId="stationName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${stationForm.stationName}" onblur="val();"/>
						<span id="message"></span>
		</td>
		<td class="label">
			<fmt:message key="station.region" />&nbsp;<font color='red'>*</font>
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
			<fmt:message key="station.serviceProfessional" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
				
			<eoms:comboBox name="serviceProfessional" id="serviceProfessional" initDicId="1121201" defaultValue="${stationForm.serviceProfessional}"
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
				
		</td>
		<td class="label">
			<fmt:message key="station.city" />&nbsp;<font color='red'>*</font>
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
			<fmt:message key="station.longitude" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="longitude" styleId="longitude"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32" value="${stationForm.longitude}" />
		</td>
		<td class="label">
			<fmt:message key="station.provider" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
							<!-- 所属公司 -->			
			<select name="provider" id="provider" 
				alt="allowBlank:false,vtext:'请选择所属公司'" onchange="val();">
				<option value="">
					--所属公司--
				</option>				
			</select>
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="station.latitude" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="latitude" styleId="latitude"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',maxLength:32" value="${stationForm.latitude}" />
		</td>
		<td class="label">
			<fmt:message key="station.address" />
		</td>
		<td class="content">
			<html:text property="address" styleId="address"
						styleClass="text medium" style="width:200px"
						alt="maxLength:32" value="${stationForm.address}" />
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="station.area" />
		</td>
		<td class="content">
			<html:text property="area" styleId="area"
						styleClass="text medium" 
						alt="maxLength:32" value="${stationForm.area}" />
		</td>
		<td class="label">
			<fmt:message key="station.setTime" />
		</td>
		<td class="content">
			<input type="text" size="20" readonly="true" class="text" 
                name="setTime" id="setTime"
                onclick="popUpCalendar(this,this,null,null,null,true,-1);"
                value="${stationForm.setTime}" />
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="station.remark" />
		</td>
		<td class="content" colspan='3'>
			<html:textarea property="remark" styleId="remark"
						alt="maxLength:500" value="${stationForm.remark}" rows="3"/>
		</td>
	</tr>
	<tr>
		<td class="label">
			<fmt:message key="station.accessory" />
		</td>
		<td class="content" colspan='3'>
			<eoms:attachment name="stationForm" property="accessory" scope="request" idField="accessory" appCode="partnerBaseinfo" />	
		</td>
	</tr>



</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty stationForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('你确定删除?')){
						var url=eoms.appPath+'/partner/baseinfo/stations.do?method=remove&id=${stationForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="addUser" value="${stationForm.addUser}" />
<html:hidden property="addTime" value="${stationForm.addTime}" />
<html:hidden property="id" value="${stationForm.id}" />
</html:form>
<script type="text/javascript">
//修改时，自动加载原来的地市县区显示在修改页面	
window.onload = function(){
    var region = '${stationForm.region}';

	if(region!=''){
 		document.getElementById("region").value = region;
		changeCity(1);
	}
}
</script>
<%@ include file="/common/footer_eoms.jsp"%>