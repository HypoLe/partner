<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>

<script type="text/javascript">
Ext.onReady(function() {
	v = new eoms.form.Validation({form:'vehicleForm'});
});


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
    xmlhttp.setRequestHeader("content-type", "text/html; charset=GBK");
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
									var cityName = "city";
						
									var arrOptions = json[0].cb.split(",");
									var obj=$(cityName);
											
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
										var city = '${vehicleForm.city}';
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
										var provider = '${vehicleForm.provider}';
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

<html:form action="/vehicles.do?method=save" styleId="vehicleForm" method="post"> 

<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">

<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="vehicle.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="vehicle.region" />&nbsp;<font color='red'>*</font>
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

		<td class="label">
			<fmt:message key="vehicle.city" />&nbsp;<font color='red'>*</font>
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
			<fmt:message key="vehicle.provider" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<!-- 所属公司 -->			
			<select name="provider" id="provider" 
				alt="allowBlank:false,vtext:'请选择所属公司'">
				<option value="">
					--请选择所属公司--
				</option>				
			</select>
						
		</td>
		

		<td class="label">
			<fmt:message key="vehicle.serviceProfession" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">

			<eoms:comboBox name="serviceProfession" id="serviceProfession" initDicId="1121201" defaultValue="${vehicleForm.serviceProfession}"
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>

		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="vehicle.use" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
				
			<eoms:comboBox name="use" id="use" initDicId="1121306" defaultValue="${vehicleForm.use}"
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
				
						
		</td>

		<td class="label">
			<fmt:message key="vehicle.disposeNo" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<html:text property="disposeNo" styleId="disposeNo"
						styleClass="text medium"
						alt="allowBlank:false,vtext:'',vtype:'number'" value="${vehicleForm.disposeNo}" />
		</td>
	</tr>


	
			<html:hidden property="userNameDel" value="${vehicleForm.userNameDel}" />

			<html:hidden property="timeDel" value="${vehicleForm.timeDel}" />
	

</table>

</fmt:bundle>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.save"/>" />
			<c:if test="${not empty vehicleForm.id}">
				<input type="button" class="btn" value="<fmt:message key="button.delete"/>" 
					onclick="javascript:if(confirm('是否删除？')){
						var url=eoms.appPath+'/partner/baseinfo/vehicles.do?method=remove&id=${vehicleForm.id}';
						location.href=url}"	/>
			</c:if>
		</td>
	</tr>
</table>
<html:hidden property="id" value="${vehicleForm.id}" />
</html:form>
<script type="text/javascript">
//修改时，自动加载原来的地市县区显示在修改页面	
window.onload = function(){
    var region = '${vehicleForm.region}';
	if(region!=''){
 		document.getElementById("region").value = region;
		changeCity(1);
	}
	
	//判断车辆配置是否已经存在（所属县区、所属公司、维护专业、用途）
	var failure = '${failure}';
	if(failure!=''){
		alert(failure);
	}
	
}
</script>

<%@ include file="/common/footer_eoms.jsp"%>