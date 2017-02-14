<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<script type="text/javascript">

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
		
	
function newAdd(){

    window.location.href=eoms.appPath+'/partner/baseinfo/stations.do?method=add';

}
</script>
<c:set var="buttons">
   <br/>
	  <input type="button" style="margin-right: 5px"
		onclick="newAdd();"
		value="<fmt:message key="button.add"/>" />
		
</c:set>

<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">
<html:form action="/stations.do?method=search" styleId="stationForm" method="post"> 
<font color='red'>*</font>号为必填内容
<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="station.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="station.stationName" />
		</td>
		<td class="content">
			<html:text property="stationName" styleId="stationName"
						styleClass="text medium"
						alt="allowBlank:false,vtext:''" value="${stationForm.stationName}" />
		</td>

		<td class="label">
			<fmt:message key="station.serviceProfessional" />
		</td>
		<td class="content">
			<eoms:comboBox name="serviceProfessional" id="serviceProfessional" initDicId="1121201" defaultValue="${stationForm.serviceProfessional}"
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
				
		</td>
	

	</tr>

	<tr>
	
		
		<td class="label">
			<fmt:message key="station.region" />
		</td>
		<td class="content">
					<!-- 地市 -->			
					<select name="region" id="region"
						alt="allowBlank:false,vtext:'请选择所在地市'"
						onchange="changeCity();">
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
			<fmt:message key="station.city" />
		</td>
		<td class="content">
					<!-- 县区 -->			
					<select name="city" id="city" 
						alt="allowBlank:true,vtext:'请选择所在县区'" 
						onchange="changePartner();">
						<option value="">
							--请选择所在县区--
						</option>				
					</select>
 
 
 		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="station.provider" />
		</td>
		<td class="content" colspan="3">
					<!-- 合作伙伴 -->			
					<select name="provider" id="provider" 
						alt="allowBlank:false,vtext:'请选择合作伙伴'" >
						<option value="">
							--请选择合作伙伴--
						</option>				
					</select>
 
 		</td>
	</tr>

</table>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.query"/>" />
			<input type="reset"  class="btn" value="<fmt:message key="button.reset"/>" />&nbsp;&nbsp;
		</td>
	</tr>
</table>
</html:form>

	
	<display:table name="stationList" cellspacing="0" cellpadding="0"
		id="stationList" pagesize="${pageSize}" class="table stationList"
		export="false"
		requestURI="${app}/partner/baseinfo/stations.do?method=search"
		sort="list" partialList="true" size="resultSize">



	<c:choose>
		<c:when test="${param.flag!='search'}">
			<display:column sortable="true" headerClass="sortable" titleKey="station.stationName" >
				<a href='${app}/partner/baseinfo/stations.do?method=edit&id=${stationList.id }' target='_blank'>
					${stationList.stationName} </a>
			</display:column>	
		</c:when>
		<c:otherwise>
			<display:column property="stationName" sortable="true"
					headerClass="sortable" titleKey="station.stationName"  paramId="id" paramProperty="id"/>
				
		
		</c:otherwise>
	</c:choose>




	<display:column sortable="true" headerClass="sortable" titleKey="station.region" >
			<eoms:id2nameDB id="${stationList.region}" beanId="tawSystemAreaDao" />
	</display:column>

	<display:column sortable="true" headerClass="sortable" titleKey="station.city" >
			<eoms:id2nameDB id="${stationList.city}" beanId="tawSystemAreaDao" />
	</display:column>
	<display:column property="provider" sortable="true"
			headerClass="sortable" titleKey="station.provider"  paramId="id" paramProperty="id"/>

	<display:column sortable="true"  headerClass="sortable" titleKey="station.serviceProfessional" >
		
		<eoms:id2nameDB id="${stationList.serviceProfessional}"  beanId="ItawSystemDictTypeDao" />
	</display:column>
	<display:column property="longitude" sortable="true"
			headerClass="sortable" titleKey="station.longitude"  paramId="id" paramProperty="id"/>

	<display:column property="latitude" sortable="true"
			headerClass="sortable" titleKey="station.latitude"  paramId="id" paramProperty="id"/>

	<display:column property="address" sortable="true"
			headerClass="sortable" titleKey="station.address"  paramId="id" paramProperty="id"/>

	<display:column property="area" sortable="true"
			headerClass="sortable" titleKey="station.area"  paramId="id" paramProperty="id"/>

	<display:column property="setTime" sortable="true" format="{0,date,yyyy-MM-dd HH:mm:ss}" 
			headerClass="sortable" titleKey="station.setTime"  paramId="id" paramProperty="id"/>



		<display:setProperty name="paging.banner.item_name" value="station" />
		<display:setProperty name="paging.banner.items_name" value="stations" />
	</display:table>

 

	<c:if test="${param.flag!='search'}">
		<c:out value="${buttons}" escapeXml="false" />
	</c:if>

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>