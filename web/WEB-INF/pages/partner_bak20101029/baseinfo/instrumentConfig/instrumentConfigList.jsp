<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_form.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<script type="text/javascript">

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
										var city = '${instrumentConfigForm.city}';
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
										var provider = '${instrumentConfigForm.provider}';
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

    window.location.href=eoms.appPath+'/partner/baseinfo/instrumentConfigs.do?method=add';

}
</script>

<c:set var="buttons">

	 <br/>
	  <input type="button" style="margin-right: 5px"
		onclick="newAdd();"
		value="<fmt:message key="button.add"/>" />
	</c:set>

<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">
<html:form action="/instrumentConfigs.do" styleId="instrumentConfigForm" method="post"> 

<font color='red'>*</font>号为必填内容
<table class="formTable">
	<caption>
		<div class="header center"><fmt:message key="instrumentConfig.form.heading"/></div>
	</caption>

	<tr>
		<td class="label">
			<fmt:message key="instrumentConfig.region" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
							<!-- 地市 -->			
			<select name="region" id="region"
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
			<fmt:message key="instrumentConfig.city" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
					<!-- 县区 -->			
			<select name="city" id="city" 
			onchange="changePartner(0);">
				<option value="">
					--请选择所在县区--
				</option>				
			</select>
		</td>
	</tr>

	<tr>
		<td class="label">
			<fmt:message key="instrumentConfig.provider" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
									<!-- 所属公司 -->			
			<select name="provider" id="provider" >
				<option value="">
					--所属公司--
				</option>	
			</select>			
		</td>
		<td class="label">
			<fmt:message key="instrumentConfig.serviceProfessional" />&nbsp;<font color='red'>*</font>
		</td>
		<td class="content">
			<eoms:comboBox name="serviceProfessional" id="serviceProfessional" initDicId="1121201" defaultValue="${instrumentConfigForm.serviceProfessional}"
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
		</td>
	</tr>

</table>

<table>
	<tr>
		<td>
			<input type="submit" class="btn" value="<fmt:message key="button.query"/>" />&nbsp;&nbsp;
			<input type="reset"  class="btn" value="<fmt:message key="button.reset"/>" />&nbsp;&nbsp;
		</td>
	</tr>
</table>
</html:form>

	<display:table name="instrumentConfigList" cellspacing="0" cellpadding="0"
		id="instrumentConfigList" pagesize="${pageSize}" class="table instrumentConfigList"
		export="false"
		requestURI="${app}/partner/baseinfo/instrumentConfigs.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column sortable="true" headerClass="sortable" titleKey="instrumentConfig.region" >
		<eoms:id2nameDB id="${instrumentConfigList.region}" beanId="tawSystemAreaDao" />
	</display:column>

	<display:column  sortable="true" headerClass="sortable" titleKey="instrumentConfig.city">
		<eoms:id2nameDB id="${instrumentConfigList.city}" beanId="tawSystemAreaDao" />
	</display:column>
			
	<display:column property="provider" sortable="true"
			headerClass="sortable" titleKey="instrumentConfig.provider"  paramId="id" paramProperty="id"/>

	<display:column  sortable="true" headerClass="sortable" titleKey="instrumentConfig.serviceProfessional">
		<eoms:id2nameDB id="${instrumentConfigList.serviceProfessional}"  beanId="ItawSystemDictTypeDao" />
		
	</display:column>
					

	<display:column sortable="true" headerClass="sortable" titleKey="instrumentConfig.use">
		
		<eoms:id2nameDB id="${instrumentConfigList.use}"  beanId="ItawSystemDictTypeDao" />
	</display:column>
			

	<display:column sortable="true" headerClass="sortable" titleKey="instrumentConfig.type" >
		
		<eoms:id2nameDB id="${instrumentConfigList.type}"  beanId="ItawSystemDictTypeDao" />
	</display:column>

	<display:column property="disposeNo" sortable="true"
			headerClass="sortable" titleKey="instrumentConfig.disposeNo"  paramId="id" paramProperty="id"/>

	
	
	<c:choose>
		<c:when test="${param.flag!='search'}">
			<display:column title="查看" headerClass="imageColumn" paramId="id" paramProperty="id">
				<a href='${app}/partner/baseinfo/instrumentConfigs.do?method=detail&id=${instrumentConfigList.id}' target='_blank'>
					<img src="${app}/images/icons/search.gif" /> </a>
			</display:column>
			
		</c:when>
		<c:otherwise>
			<display:column title="查看" headerClass="imageColumn" paramId="id" paramProperty="id">
				<a href='${app}/partner/baseinfo/instrumentConfigs.do?method=detail&flag=search&id=${instrumentConfigList.id}' target='_blank'>
					<img src="${app}/images/icons/search.gif" /> </a>
			</display:column>
		
		</c:otherwise>
	</c:choose>

		<display:setProperty name="paging.banner.item_name" value="instrumentConfig" />
		<display:setProperty name="paging.banner.items_name" value="instrumentConfigs" />
	</display:table>

	<c:if test="${param.flag!='search'}">
		<c:out value="${buttons}" escapeXml="false" />
	</c:if>

</fmt:bundle>
<script type="text/javascript">
//修改时，自动加载原来的地市县区显示在修改页面	
window.onload = function(){
    var region = '${instrumentConfigForm.region}';
	if(region!=''){
		document.getElementById("region").value = region;
		changeCity(1);
	}
}
</script>
<%@ include file="/common/footer_eoms.jsp"%>