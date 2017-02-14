<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>

<c:set var="buttons">
		   <br/>
	  <input type="button" style="margin-right: 5px"
		onclick="newAdd();"
		value="<fmt:message key="button.add"/>" />
	
</c:set>

<script type="text/javascript">
function newAdd(){

    window.location.href=eoms.appPath+'/partner/baseinfo/vehicles.do?method=add';

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

<fmt:bundle basename="com/boco/eoms/partner/baseinfo/config/applicationResources-partner-baseinfo">

	<html:form action="/vehicles.do?method=searchX" styleId="vehicleForm" method="post" >
		<table align="center">
			<tr>
				<!-- 所属地市 -->
				<td>
					<fmt:message key="vehicle.region" />:
				</td>
				<td >
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
				
				<!-- 所属县区 -->
				<td>
					<fmt:message key="vehicle.city" />:
				</td>
				<td >
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
				<!-- 所属公司 -->
				<td>
					<fmt:message key="vehicle.provider" />
				</td>
				<td>
					<!-- 所属公司 -->			
					<select name="provider" id="provider" 
						alt="allowBlank:false,vtext:'请选择所属公司'">
						<option value="">
							--请选择所属公司--
						</option>				
					</select>
								
				</td>
				
				<!-- 维护专业-->
				<td>
					<fmt:message key="vehicle.serviceProfession" />
				</td>
				<td>
						
					<eoms:comboBox name="serviceProfession" id="serviceProfession" initDicId="1121201" defaultValue="${vehicleForm.serviceProfession}"
					    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
					
		
				</td>
				
			</tr>			

			<tr>
				<!-- 提交按钮 -->
				<td>
					<input type="submit" class="btn" value="<fmt:message key="button.search"/>" />
				</td>
			</tr>
			
		</table>
	</html:form>


<content tag="heading">
	<fmt:message key="vehicle.list.heading" />
</content>

	<display:table name="vehicleList" cellspacing="0" cellpadding="0"
		id="vehicleList" pagesize="${pageSize}" class="table vehicleList"
		export="false"
		requestURI="${app}/vehicle/vehicles.do?method=search"
		sort="list" partialList="true" size="resultSize">

			
	<!-- 所在地市 -->
	<display:column  sortable="true" headerClass="sortable" titleKey="vehicle.region">
		<eoms:id2nameDB id="${vehicleList.region}" beanId="tawSystemAreaDao" />
	</display:column>			

	<!-- 所在县区 -->
	<display:column  sortable="true" headerClass="sortable" titleKey="vehicle.city">
		<eoms:id2nameDB id="${vehicleList.city}" beanId="tawSystemAreaDao" />
	</display:column>			
			

	<display:column property="provider" sortable="true"
			headerClass="sortable" titleKey="vehicle.provider"  paramId="id" paramProperty="id"/>

			
	<display:column sortable="true" headerClass="sortable" titleKey="vehicle.serviceProfession">
		
		<eoms:id2nameDB id="${vehicleList.serviceProfession}"  beanId="ItawSystemDictTypeDao" />
	</display:column>
			

			
	<display:column sortable="true" headerClass="sortable" titleKey="vehicle.use">
		
		<eoms:id2nameDB id="${vehicleList.use}"  beanId="ItawSystemDictTypeDao" />
	</display:column>
			

	<display:column property="disposeNo" sortable="true"
			headerClass="sortable" titleKey="vehicle.disposeNo"  paramId="id" paramProperty="id"/>

			
		
	<c:choose>
		<c:when test="${param.flag!='search'}">
			<display:column title="查看" headerClass="imageColumn" paramId="id" paramProperty="id">
				<a href='${app}/partner/baseinfo/vehicles.do?method=detail&id=${vehicleList.id }' target='_blank'>
					<img src="${app}/images/icons/search.gif" /> </a>
			</display:column>
		
		</c:when>
		<c:otherwise>
			<display:column title="查看" headerClass="imageColumn" paramId="id" paramProperty="id">
				<a href='${app}/partner/baseinfo/vehicles.do?method=detail&flag=search&id=${vehicleList.id }' target='_blank'>
					<img src="${app}/images/icons/search.gif" /> </a>
			</display:column>
		
		</c:otherwise>
	</c:choose>		
			

		<display:setProperty name="paging.banner.item_name" value="vehicle" />
		<display:setProperty name="paging.banner.items_name" value="vehicles" />
	</display:table>



	<c:if test="${param.flag!='search'}">
		<c:out value="${buttons}" escapeXml="false" />
	</c:if>

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>