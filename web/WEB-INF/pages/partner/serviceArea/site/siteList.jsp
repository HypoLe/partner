<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/header_eoms_ext.jsp"%>

<script language=javascript>
<!--
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
		
		
function newAdd(){

    window.location.href=eoms.appPath+'/partner/serviceArea/sites.do?method=newExpert';

}

//-->
</script>

<c:set var="buttons">
 <br/>
	<input type="button" style="margin-right: 5px"
		onclick="newAdd();"
		value="<fmt:message key="button.add"/>" />
	 
</c:set>
<fmt:bundle basename="com/boco/eoms/partner/serviceArea/config/applicationResource-partner-serviceArea">
<html:form  action="/sites.do" styleId="siteForm" method="post"> 
	<caption>
		<div class="header center"><fmt:message key="site.form.query"/></div>
	</caption>
	<table align="center">
		<tr>	
			<td>
				<fmt:message key="site.siteName" />
				<html:text property="siteName" styleId="siteName"
							styleClass="text medium"
							alt="allowBlank:false,vtext:'',maxLength:32" value="${siteForm.siteName}"/>
			</td>
			<td>	
				<fmt:message key="site.siteNo" />
				<html:text property="siteNo" styleId="siteNo"
							styleClass="text medium"
							alt="allowBlank:false,vtext:'',vtype:'number',maxLength:32" value="${siteForm.siteNo}" />
			</td>
			<td>
				<fmt:message key="site.coverType" />
					<eoms:comboBox name="coverType" id="coverType" initDicId="1121104" defaultValue=""
					    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
			</td>			
			<td>	
				<fmt:message key="site.siteType" />
				<eoms:comboBox name="siteType" id="siteType" initDicId="1121101" defaultValue=""
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
			</td>
		</tr>
		<tr>	

			<td>				
				<fmt:message key="site.region" />			
<!-- 			<html:text property="region" styleId="region"
							styleClass="text medium"
							alt="allowBlank:false,vtext:'',maxLength:32" value="${siteForm.region}" />
 -->								
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
			
			

			<td>	
				<fmt:message key="site.city" />	
<!-- 			<html:text property="city" styleId="city"
							styleClass="text medium"
							alt="allowBlank:false,vtext:'',maxLength:32" value="${siteForm.city}" />
 -->								
				<!-- 县区 -->			
				<select name="city" id="city" 
					alt="allowBlank:true,vtext:'请选择所在县区'"
					onchange="changePartner();">
					<option value="">
						--请选择所在县区--
					</option>				
				</select>
			</td>

				<td >
				  <fmt:message key="line.provider" />:
					<!-- 合作伙伴 -->			
					<select name="provider" id="provider" 
						alt="allowBlank:false,vtext:'请选择合作伙伴'" 
						onchange="changeGrid();">
						<option value="">
							--请选择合作伙伴--
						</option>				
					</select>
				</td>
			<td>	
				<fmt:message key="site.siteLevle" />
			<eoms:comboBox name="siteLevle" id="siteLevle" initDicId="1121106" defaultValue=""
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
			
			</td>
	
		</tr>
		<tr>	

			

			<td>	
				<fmt:message key="site.grid" />
				<!-- 所属网格 -->
				<select name="grid" id="grid" 
					alt="allowBlank:false,vtext:'请选择所属网格'" >
					<option value="">
						--请选择所属网格--
					</option>				
				</select>
			</td>
				
				
			<td>	
				<fmt:message key="site.frequencyBand" />
				<html:text property="frequencyBand" styleId="frequencyBand"
							styleClass="text medium"
							alt="maxLength:32" value="${siteForm.frequencyBand}" />
			</td>	
				
							
			<td>	
				<fmt:message key="site.cellulType" />
			<eoms:comboBox name="cellulType" id="cellulType" initDicId="1121105" defaultValue=""
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
			</td>
			<td>	
				<fmt:message key="site.maintainType" />
						<eoms:dict key="dict-partner-serviceArea" dictId="maintainType" isQuery="false"  alt="allowBlank:false,vtext:''"
				defaultId="${siteForm.maintainType}" selectId="maintainType" beanId="selectXML" />			
			</td>
			
					
		</tr>
	</table>
<table align="center">
    <tr>
		<td width="260">	
			<fmt:message key="site.roomType" />
			<eoms:comboBox name="roomType" id="roomType" initDicId="1121107" defaultValue=""
			    alt="allowBlank:false,vtext:'请选择(单选字典)...'"/>
			
		</td>
		<td width="260">	
			有效数据
			<select name="unconfig" id="unconfig">
					
						<option value="0">
							是
						</option>
					
						<option value="1">
							否
						</option>

					
				</select>
		

			
		</td>
	    <td width="200">
	    	<input type="submit" class="btn" value="<fmt:message key="button.query"/>" />&nbsp;&nbsp;
			<input type="reset"  class="btn" value="<fmt:message key="button.reset"/>" />&nbsp;&nbsp;
		</td>
	</tr>
</table>	
</html:form>
	<display:table name="siteList" cellspacing="0" cellpadding="0"
		id="siteList" pagesize="${pageSize}" class="table siteList"
		export="false"
		requestURI="${app}/partner/serviceArea/sites.do?method=search"
		sort="list" partialList="true" size="resultSize">

	<display:column property="siteName" sortable="true"
			headerClass="sortable" titleKey="site.siteName" paramId="siteNo" paramProperty="siteNo"/>

	<display:column property="siteNo" sortable="true"
			headerClass="sortable" titleKey="site.siteNo"  paramId="id" paramProperty="id"/>
	<!-- 所在地市 -->
	<display:column  sortable="true" headerClass="sortable" titleKey="line.region">
		<eoms:id2nameDB id="${siteList.region}" beanId="tawSystemAreaDao" />
	</display:column>			

	<!-- 所在县区 -->
	<display:column  sortable="true" headerClass="sortable" titleKey="line.city">
		<eoms:id2nameDB id="${siteList.city}" beanId="tawSystemAreaDao" />
	</display:column>			
			

	<display:column property="grid" sortable="true"
			headerClass="sortable" titleKey="site.grid"  paramId="id" paramProperty="id"/>
	<display:column sortable="true" headerClass="sortable" titleKey="site.siteType">
		<c:if test="${siteList.siteType!=null}">
						<eoms:id2nameDB id="${siteList.siteType}"  beanId="ItawSystemDictTypeDao" />
		</c:if>
		<c:if test="${siteList.siteType==null}">
			暂无
		</c:if>
	</display:column>

	<display:column property="provider" sortable="true"
			headerClass="sortable" titleKey="site.provider"  paramId="id" paramProperty="id"/>

	<display:column sortable="true" headerClass="sortable" titleKey="site.coverType" >
		<c:if test="${siteList.coverType!=null}">
						<eoms:id2nameDB id="${siteList.coverType}"  beanId="ItawSystemDictTypeDao" />
		</c:if>
		<c:if test="${siteList.coverType==null}">
			暂无
		</c:if>
	</display:column>
	
	<display:column sortable="true" headerClass="sortable" titleKey="site.vendor" >
		<c:if test="${siteList.vendor!=null}">
					${siteList.vendor}
		</c:if>
		<c:if test="${siteList.vendor==null}">
			暂无
		</c:if>
	</display:column>
	
	
	<display:column property="longitude" sortable="true"
			headerClass="sortable" titleKey="site.longitude"  paramId="id" paramProperty="id"/>

	<display:column property="latitude" sortable="true"
			headerClass="sortable" titleKey="site.latitude"  paramId="id" paramProperty="id"/>

	<display:column sortable="true" headerClass="sortable" titleKey="site.cellulType" >
		<c:if test="${siteList.cellulType!=null}">
				<eoms:id2nameDB id="${siteList.cellulType}"  beanId="ItawSystemDictTypeDao" />
		</c:if>
		<c:if test="${siteList.cellulType==null}">
			暂无
		</c:if>
	</display:column>
	
	<display:column sortable="true" headerClass="sortable" titleKey="site.siteLevle"  paramId="id" paramProperty="id">
		
		<c:if test="${siteList.siteLevle!=null}">
					<eoms:id2nameDB id="${siteList.siteLevle}"  beanId="ItawSystemDictTypeDao" />
		</c:if>
		<c:if test="${siteList.siteLevle==null}">
			暂无
		</c:if>
		
	</display:column>
	
	<display:column sortable="true" headerClass="sortable" titleKey="site.roomType"  paramId="id" paramProperty="id">
		<c:if test="${siteList.roomType!=null}">
			<eoms:id2nameDB id="${siteList.roomType}"  beanId="ItawSystemDictTypeDao" />
		</c:if>
		<c:if test="${siteList.roomType==null}">
			暂无
		</c:if>
	</display:column>
	
	<display:column sortable="true" headerClass="sortable" titleKey="site.maintainType"  paramId="id" paramProperty="id">
		<c:if test="${siteList.maintainType!=null}">
			<eoms:dict key="dict-partner-serviceArea" dictId="maintainType" itemId="${siteList.maintainType}" beanId="id2nameXML" />
		</c:if>
		<c:if test="${siteList.maintainType==null}">
			暂无
		</c:if>
	</display:column>
			
	<display:column property="frequencyBand" sortable="true"
			headerClass="sortable" titleKey="site.frequencyBand"  paramId="id" paramProperty="id">
	</display:column>	
	
	<c:if test="${param.flag!='search'}">
		<display:column title="操作" headerClass="imageColumn">
			
			    <a href='${app}/partner/serviceArea/sites.do?method=newExpert&idSite=${siteList.id}' target='_blank'>
			         修改</a>
		    	<a href="javascript: if(confirm('确定要删除该文件?')){var id='${siteList.id} ';var nodeId='${nodeId}';var url=eoms.appPath+'/partner/serviceArea/sites.do?method=remove&id=' + id ;location.href=url}">
		    		删除
				</a>
				
					
		</display:column> 
	</c:if>	

		<display:setProperty name="paging.banner.item_name" value="site" />
		<display:setProperty name="paging.banner.items_name" value="sites" />
	</display:table>


<c:if test="${param.flag!='search'}">
	<c:out value="${buttons}" escapeXml="false" />
</c:if>

</fmt:bundle>

<%@ include file="/common/footer_eoms.jsp"%>